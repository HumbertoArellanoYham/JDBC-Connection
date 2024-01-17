package org.arellano.java.jdbc;

import org.arellano.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;


/* Zona horaria
   String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC";
*/

public class EjemploJDBC {
    public static void main(String[] args) {
        try (Connection connection = ConexionBaseDatos.getInstancia();
             Statement statement = connection.createStatement();
             ResultSet conjuntoDatosBD = statement.executeQuery("SELECT * FROM productos")) {

            while(conjuntoDatosBD.next()){
                System.out.print(conjuntoDatosBD.getInt("id"));
                System.out.print(" | ");
                System.out.print(conjuntoDatosBD.getString("nombre"));
                System.out.print(" | ");
                System.out.print(conjuntoDatosBD.getInt("precio"));
                System.out.print(" | ");
                System.out.println(conjuntoDatosBD.getDate("fecha_registro"));
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}
