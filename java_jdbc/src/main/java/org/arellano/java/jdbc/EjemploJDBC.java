package org.arellano.java.jdbc;

import org.arellano.java.jdbc.modelo.Categoria;
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
        long start = System.currentTimeMillis();
        try (Connection connection = ConexionBaseDatos.getInstancia()) {

            Repositorio<Productos> consulta = new ProductoRepositorioImplement();

            System.out.println("============== Listar los productos ============");
            consulta.listar().forEach(System.out::println);

            System.out.println("============== Buscar por Id ============");
            System.out.println(consulta.porId(1L));

            System.out.println("============== Guardar un productos ============");
            Productos productoNuevo = new Productos();
            productoNuevo.setNombre("Teclado Red Dragon");
            productoNuevo.setPrecio(450);
            productoNuevo.setFecha_registro(new Date());

            // Asignando la categoria al producto si no lanzara una exception en tiempo de ejecucion.
            Categoria categoria = new Categoria();
            categoria.setId(3L);
            productoNuevo.setCategoria(categoria);

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

        long endTime = System.currentTimeMillis() - start;
        System.out.println("\n" + start);
    }
}
