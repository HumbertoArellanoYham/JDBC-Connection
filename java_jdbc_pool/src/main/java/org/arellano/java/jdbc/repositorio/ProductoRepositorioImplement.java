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
            Statement statement = connection.createStatement();

           ResultSet resultSet = statement.executeQuery("SELECT p.*, c.nombre as categoria FROM productos as p inner join categorias as c on (p.categoria_id = c.id)")) {

            while(resultSet.next()){
                Productos producto = llenarProductos(resultSet);
                productosList.add(producto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productosList;
    }

    @Override
    public Productos porId(Long id) {
        Productos producto = null;

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection
                .prepareStatement("select p.*, c.nombre as categoria from productos as p inner join categorias as c on (p.categoria_id = c.id) where p.id = ?")){
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

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
        String resultSql;

        if(productos.getId() > 0){
            resultSql = "update productos set nombre = ?, precio = ?, categoria_id = ? where id = ?";
        } else {
            resultSql = "Insert into productos(nombre, precio, categoria_id, fecha_registro) values (?, ?, ?, ?)";
        }

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(resultSql)){
            preparedStatement.setString(1, productos.getNombre());
            preparedStatement.setLong(2, productos.getPrecio());
            preparedStatement.setLong(3, productos.getCategoria().getId());

            if(productos.getId() > 0){
                preparedStatement.setLong(4, productos.getId());
            } else {
                preparedStatement.setDate(4, new Date(productos.getFecha_registro().getTime()));
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void eliminar(Long id) {
        try(Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("delete from productos where id = ?")){
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
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
