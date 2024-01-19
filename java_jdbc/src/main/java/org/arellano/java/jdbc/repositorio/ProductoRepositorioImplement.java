package org.arellano.java.jdbc.repositorio;

import org.arellano.java.jdbc.modelo.Productos;
import org.arellano.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioImplement implements Repositorio<Productos> {

    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getInstancia();
    }

    @Override
    public List<Productos> listar() {
        List<Productos> productosList = new ArrayList<>();

        try(Statement statement = getConnection().createStatement();
           ResultSet resultSet = statement.executeQuery("SELECT * FROM productos")) {

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

        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("select * from productos where id = ?")){
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

    }

    @Override
    public void eliminar(Long id) {

    }

    private Productos llenarProductos(ResultSet resultSet) throws SQLException {
        Productos producto = new Productos();
        producto.setId(resultSet.getInt("id"));
        producto.setNombre(resultSet.getString("nombre"));
        producto.setPrecio(resultSet.getInt("precio"));
        producto.setFecha_registro(resultSet.getDate("fecha_registro"));

        return producto;
    }
}
