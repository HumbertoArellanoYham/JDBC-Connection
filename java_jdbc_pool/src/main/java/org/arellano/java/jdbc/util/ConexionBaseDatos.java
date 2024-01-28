package org.arellano.java.jdbc.util;

/*Utilizar el patron singleton
* Se basa en hacer la conexion de manera separada
* Esto provoca que se genere una instancia por cada peticion
* Y todos los campos se mantienen privados*/

/*Pool de conexiones
* Un pool de conexiones nos permite tener varias conexiones abiertas, esto significa que
* mientras un hilo este utilizando una conexion, otro no tiene que esperar a que este la desocupe
* simplemente toma la otra conexion y sigue con el proceso
*
* Al finalizar la tarea simplemente dejan la conexion (se libera) y listo no es tan costoso como estar abriendo
* y cerrando cada conexion al mismo tiempo */

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private static final String url = "jdbc:mysql://localhost:3306/java_curso";
    private static final String user = "root";
    private static final String password = "rootsasa";
    private static BasicDataSource pool;

    public static BasicDataSource getInstancia() throws SQLException {
        if(pool == null){
            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(user);
            pool.setPassword(password);
            pool.setInitialSize(3);
            pool.setMinIdle(3);
            pool.setMaxIdle(8);
            pool.setMaxTotal(8);
        }

        return pool;
    }

    public static Connection getConnection() throws SQLException {
        return getInstancia().getConnection();
    }
}
