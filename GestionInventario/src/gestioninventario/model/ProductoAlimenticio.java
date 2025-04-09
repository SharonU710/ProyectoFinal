package  gestioninventario.model;

/*
    Producto del tipo alimenticio
    Incluye fecha de vencimiento como atributo adicional
*/
public class ProductoAlimenticio extends Producto
{
    private String fechaVencimiento;

    public ProductoAlimenticio(String codigo, String nombre, int stock, Categoria categoria, Proveedor proveedor, String fechaVencimiento) {
        super(codigo, nombre, stock, categoria, proveedor);
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaVencimiento()
    {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento)
    {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public void mostrarDetalles()
    {
        System.out.println("Producto Alimenticio: " + getNombre() +
                " | Stock: " + getStock() +
                " | Vence: " + fechaVencimiento +
                " | Proveedor: " + getProveedor().getNombre());
    }
}
