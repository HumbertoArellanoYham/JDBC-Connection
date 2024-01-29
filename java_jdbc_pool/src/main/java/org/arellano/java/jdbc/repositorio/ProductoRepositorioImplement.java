package org.arellano.java.jdbc.repositorio;

import org.arellano.java.jdbc.modelo.Categoria;
import org.arellano.java.jdbc.modelo.Productos;
import org.arellano.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImplement implements Repositorio<Productos> {

    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getConnection();
    }

    @Override
    public List<Productos> listar() {
        List<Productos> productosList = new ArrayList<>();

        try(Connection connection = getConnection();

            CallableStatement callableStatement = connection.prepareCall("{call listar_productos()}")) {
           //ResultSet resultSet = statement.executeQuery("SELECT p.*, c.nombre as categoria FROM productos as p inner join categorias as c on (p.categoria_id = c.id)")) {

            callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();

            while(resultSet.next()){
                Productos producto = llenarProductos(resultSet);
                productosList.add(producto);
            }

            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productosList;
    }

    @Override
    public Productos porId(Long id) {
        Productos producto = null;

        try (Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call por_id(?) }")){
                //PreparedStatement preparedStatement = connection
                //.prepareStatement("select p.*, c.nombre as categoria from productos as p inner join categorias as c on (p.categoria_id = c.id) where p.id = ?")){
            callableStatement.setLong(1, id);

            ResultSet resultSet = callableStatement.executeQuery();

            if(resultSet.next()){
                producto = llenarProductos(resultSet);
            }

            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return producto;
    }

    @Override
    public void guardar(Productos productos) {
        String resultSql = "";

        if(productos.getId() > 0){
            //resultSql = "update productos set nombre = ?, precio = ?, categoria_id = ? where id = ?";
            resultSql = "{call actualizar_producto(?, ?, ?, ?)}";
        } else {
            //resultSql = "Insert into productos(nombre, precio, categoria_id, fecha_registro) values (?, ?, ?, ?)";
            resultSql = "{call guardar_producto(?, ?, ?, ?)}";
        }

        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall(resultSql)){
            //PreparedStatement preparedStatement = connection.prepareStatement(resultSql)){
            callableStatement.setString(1, productos.getNombre());
            callableStatement.setLong(2, productos.getPrecio());
            callableStatement.setLong(3, productos.getCategoria().getId());

            if(productos.getId() > 0){
                callableStatement.setLong(4, productos.getId());
            } else {
                callableStatement.setDate(4, new Date(productos.getFecha_registro().getTime()));
            }

            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void eliminar(Long id) {
        try(Connection connection = getConnection();
            CallableStatement callableStatement = connection.prepareCall("{call eliminar_producto(?)}")){
                //PreparedStatement preparedStatement = connection.prepareStatement("delete from productos where id = ?")){
            callableStatement.setLong(1, id);

            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Productos llenarProductos(ResultSet resultSet) throws SQLException {
        Productos producto = new Productos();
        producto.setId(resultSet.getInt("id"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setPrecio(resultSet.getInt("precio"));
        producto.setFecha_registro(resultSet.getDate("fecha_registro"));

        Categoria categoria = new Categoria();
        categoria.setId(resultSet.getLong("categoria_id"));
        categoria.setNombre(resultSet.getString("categoria"));

        producto.setCategoria(categoria);
        return producto;
    }
}
