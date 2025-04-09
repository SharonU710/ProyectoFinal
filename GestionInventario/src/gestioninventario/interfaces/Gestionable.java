package gestioninventario.interfaces;

import java.util.List;

// Interfaz para objetos que pueden ser gestionados (agregar, eliminar, listar, buscar).
public interface Gestionable<T>
{
    void agregar(T entidad);

    boolean eliminarPorId(String id);

    T buscarPorId(String id);

    List<T> listarTodos();
}
