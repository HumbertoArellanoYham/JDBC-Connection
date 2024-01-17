package org.arellano.java.jdbc;

import org.arellano.java.jdbc.modelo.Productos;
import org.arellano.java.jdbc.repositorio.ProductoRepositorioImplement;
import org.arellano.java.jdbc.repositorio.Repositorio;
import org.arellano.java.jdbc.util.ConexionBaseDatos;
import org.w3c.dom.ls.LSOutput;

import java.sql.*;


/* Zona horaria
   String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC";
*/

public class EjemploJDBC {
    public static void main(String[] args) {
        try (Connection connection = ConexionBaseDatos.getInstancia()) {
            Repositorio<Productos> consulta = new ProductoRepositorioImplement();
            consulta.listar().forEach(item -> System.out.println(item.getNombre()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
