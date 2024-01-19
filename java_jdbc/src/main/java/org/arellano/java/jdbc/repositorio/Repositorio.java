package org.arellano.java.jdbc.repositorio;

import org.arellano.java.jdbc.modelo.Productos;

import java.util.List;

public interface Repositorio<T> {
    List<T> listar();

    Productos porId(Long id);

    void guardar(T t);

    void eliminar(Long id);
}
