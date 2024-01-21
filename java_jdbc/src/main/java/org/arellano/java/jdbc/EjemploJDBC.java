package org.arellano.java.jdbc;

import org.arellano.java.jdbc.modelo.Productos;
import org.arellano.java.jdbc.repositorio.ProductoRepositorioImplement;
import org.arellano.java.jdbc.repositorio.Repositorio;
import org.arellano.java.jdbc.util.ConexionBaseDatos;
import org.w3c.dom.ls.LSOutput;

import java.sql.*;
import java.util.Date;


/* Zona horaria
   String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=UTC";
*/

public class EjemploJDBC {
    public static void main(String[] args) {
        try (Connection connection = ConexionBaseDatos.getInstancia()) {

            Repositorio<Productos> consulta = new ProductoRepositorioImplement();


            System.out.println("============== Listar los productos ============");
            consulta.listar().forEach(System.out::println);

            System.out.println("============== Buscar por Id ============");
            System.out.println(consulta.porId(1L));

            System.out.println("============== Guardar un productos ============");
            Productos productoNuevo = new Productos();
            productoNuevo.setId(4);
            productoNuevo.setNombre("Magic apple 2");
            productoNuevo.setPrecio(2000);
            consulta.guardar(productoNuevo);
            System.out.println("Se guardo con exito!");

            System.out.println("============== Listar los productos ============");
            consulta.listar().forEach(System.out::println);

            System.out.println("============== Eliminar un producto ============");
            consulta.eliminar(4L);
            System.out.println("Se elimino con exito!");

            System.out.println("============== Listar los productos ============");
            consulta.listar().forEach(System.out::println);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
