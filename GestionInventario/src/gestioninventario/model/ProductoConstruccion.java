package gestioninventario.model;

/*
    Producto del tipo construcción.
    Incluye garantia y licencia como atributo adicional.
*/
public class ProductoConstruccion extends ProductoElectronico
{
    private boolean requiereLicencia;

    public ProductoConstruccion(String codigo, String nombre, int stock, Categoria categoria, Proveedor proveedor, int garantiaMeses, boolean requiereLicencia)
    {
        super(codigo, nombre, stock, categoria, proveedor, garantiaMeses);
        this.requiereLicencia = requiereLicencia;
    }

    public boolean isRequiereLicencia()
    {
        return requiereLicencia;
    }

    public void setRequiereLicencia(boolean requiereLicencia)
    {
        this.requiereLicencia = requiereLicencia;
    }

    @Override
    public void mostrarDetalles()
    {
        System.out.println("Producto Construcción: " + getNombre() +
                " | Stock: " + getStock() +
                " | Garantía: " + getGarantiaMeses() + " meses" +
                " | Licencia: " + (requiereLicencia ? "Sí" : "No") +
                " | Proveedor: " + getProveedor().getNombre());
    }
}
