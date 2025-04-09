package gestioninventario.service;

import gestioninventario.interfaces.Gestionable;
import gestioninventario.model.Proveedor;
import java.util.ArrayList;
import java.util.List;

// Servicio para gestionar proveedores.
public class ProveedorService implements Gestionable<Proveedor>
{
    private final List<Proveedor> proveedores;

    public ProveedorService()
    {
        this.proveedores = new ArrayList<>();
    }
    
    // Metodo para validar asociaciones proveedor-producto
    public boolean asociado(String nombre, List<?> productos)
    {
        return productos.stream().anyMatch(p -> {
            try
            {
                Object proveedor = p.getClass().getMethod("getProveedor").invoke(p);
                if (proveedor instanceof Proveedor)
                {
                    return ((Proveedor) proveedor).getNombre().equalsIgnoreCase(nombre);
                }
            }
            catch (Exception e) {}
            
            return false;
        });
    }

    @Override
    public void agregar(Proveedor proveedor)
    {
        proveedores.add(proveedor);
    }

    @Override
    public boolean eliminarPorId(String nombre)
    {
        return proveedores.removeIf(p -> p.getNombre().equalsIgnoreCase(nombre));
    }

    @Override
    public Proveedor buscarPorId(String nombre)
    {
        return proveedores.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Proveedor> listarTodos()
    {
        return new ArrayList<>(proveedores);
    }
}
