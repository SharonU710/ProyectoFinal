package gestioninventario.model;

/*
   Producto del tipo electrónico.
   Incluye garantia  como atributo adicional.
*/
public class ProductoElectronico extends Producto
{
    private int garantiaMeses;

    public ProductoElectronico(String codigo, String nombre, int stock, Categoria categoria, Proveedor proveedor, int garantiaMeses)
    {
        super(codigo, nombre, stock, categoria, proveedor);
        this.garantiaMeses = garantiaMeses;
    }

    public int getGarantiaMeses()
    {
        return garantiaMeses;
    }

    public void setGarantiaMeses(int garantiaMeses)
    {
        this.garantiaMeses = garantiaMeses;
    }

    @Override
    public void mostrarDetalles()
    {
        System.out.println("Producto Electrónico: " + getNombre() +
                " | Stock: " + getStock() +
                " | Garantía: " + garantiaMeses + " meses" +
                " | Proveedor: " + getProveedor().getNombre());
    }
}