package gestioninventario.service;

import gestioninventario.interfaces.Gestionable;
import gestioninventario.model.Producto;
import java.util.ArrayList;
import java.util.List;

// Servicio que gestiona el inventario de productos
public class InventarioService implements Gestionable<Producto>
{
    private final List<Producto> productos;

    public InventarioService()
    {
        this.productos = new ArrayList<>();
    }
    
    public void limpiarInventario()
    {
        productos.clear();
    }

    public boolean existeCodigo(String codigo)
    {
        return productos.stream().anyMatch(p -> p.getCodigo().equalsIgnoreCase(codigo));
    }

    public int totalProductos()
    {
        return productos.size();
    }

    @Override
    public void agregar(Producto producto)
    {
        productos.add(producto);
    }

    @Override
    public boolean eliminarPorId(String codigo)
    {
        return productos.removeIf(p -> p.getCodigo().equalsIgnoreCase(codigo));
    }

    @Override
    public Producto buscarPorId(String codigo)
    {
        return productos.stream()
                .filter(p -> p.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Producto> listarTodos()
    {
        return new ArrayList<>(productos);
    }
}