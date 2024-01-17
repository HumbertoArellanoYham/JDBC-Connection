package org.arellano.java.jdbc.repositorio;

import org.arellano.java.jdbc.modelo.Productos;
import org.arellano.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                Productos producto = new Productos();
                producto.setId(resultSet.getInt("id"));
                producto.setNombre(resultSet.getString("nombre"));
                producto.setPrecio(resultSet.getInt("precio"));
                producto.setFecha_registro(resultSet.getDate("fecha_registro"));

                productosList.add(producto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productosList;
    }

    @Override
    public Productos porId(Productos productos) {
        return null;
    }

    @Override
    public void guardar(Productos productos) {

    }

    @Override
    public void eliminar(Long id) {

    }
}
