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

/*En este proyecto se implementa una manera diferente para la connexion con la base de datos
* Cada vez que se abre un petition esta se cierra en el mismo método y asi para cada una de las
* peticiones.
*
* A diferencia del patron singelton que devuelve una conexion que ya se cerro pero, es muy similar solo
* diferente implementacion*/

public class EjemploJDBC {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Repositorio<Productos> consulta = new ProductoRepositorioImplement();

        //System.out.println("============== Listar los productos ============");
        //consulta.listar().forEach(System.out::println);

/*
        System.out.println("============== Buscar por Id ============");
        System.out.println(consulta.porId(1L));
        long endTime = System.currentTimeMillis();
        System.out.println("\n" + endTime);
*/
        /*
        System.out.println("============== Buscar por Id ============");
        System.out.println(consulta.porId(1L));
        */

        /*
        System.out.println("============== Guardar un productos ============");
        Productos productoNuevo = new Productos();
        productoNuevo.setId(23);
        productoNuevo.setNombre("Apple watch intelligent 4");
        productoNuevo.setPrecio(6000);
        productoNuevo.setFecha_registro(new Date());

        // Asignando la categoria al producto si no lanzara una exception en tiempo de ejecucion.
        Categoria categoria = new Categoria();
        categoria.setId(3L);
        productoNuevo.setCategoria(categoria);

        consulta.guardar(productoNuevo);
        System.out.println("Se guardo con exito!");


        System.out.println("============== Listar los productos ============");
        consulta.listar().forEach(System.out::println);
         */

        System.out.println("============== Eliminar un producto ============");
        consulta.eliminar(23L);
        System.out.println("Se elimino con exito!");

        System.out.println("============== Listar los productos ============");
        consulta.listar().forEach(System.out::println);

        /*
        long endTime = System.currentTimeMillis() - start;
        System.out.println("\n" + endTime);

         */
    }
}
