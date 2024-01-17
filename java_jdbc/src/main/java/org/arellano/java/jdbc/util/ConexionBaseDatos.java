package org.arellano.java.jdbc.util;

/*Utilizar el patron singleton
* Se basa en hacer la conexion de manera separada
* Esto provoca que se genere una instancia por cada peticion
* Y todos los campos se mantienen privados*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private static String url = "jdbc:mysql://localhost:3306/java_curso";
    private static String user = "root";
    private static String password = "rootsasa";
    private static Connection connection;

    public static Connection getInstancia() throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection(url, user, password);
        }

        return connection;
    }
}
