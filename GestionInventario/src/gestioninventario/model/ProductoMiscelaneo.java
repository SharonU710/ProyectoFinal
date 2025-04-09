package gestioninventario.model;

/*
   Producto del tip miscelaneo.
   No tiene atributos adicionales.
*/
public class ProductoMiscelaneo extends Producto
{

    public ProductoMiscelaneo(String codigo, String nombre, int stock,
                               Categoria categoria, Proveedor proveedor)
    {
        super(codigo, nombre, stock, categoria, proveedor);
    }

    @Override
    public void mostrarDetalles()
    {
        System.out.println("Producto Miscelaneo: " + getNombre() +
                " | Stock: " + getStock() +
                " | Categoría: " + getCategoria().getNombre() +
                " | Proveedor: " + getProveedor().getNombre());
    }
}
