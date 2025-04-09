package gestioninventario.model;

/*
   Producto general para mascotas.
   No es alimenticio ni electrónico, pero o orientado a tipo de producto animal.
*/
public class ProductoMascotaGeneral extends Producto
{
    private String tipoAnimal;

    public ProductoMascotaGeneral(String codigo, String nombre, int stock, Categoria categoria, Proveedor proveedor, String tipoAnimal)
    {
        super(codigo, nombre, stock, categoria, proveedor);
        this.tipoAnimal = tipoAnimal;
    }

    public String getTipoAnimal()
    {
        return tipoAnimal;
    }

    public void setTipoAnimal(String tipoAnimal)
    {
        this.tipoAnimal = tipoAnimal;
    }

    @Override
    public void mostrarDetalles()
    {
        System.out.println("Producto Mascota (General): " + getNombre() +
                " | Tipo: " + tipoAnimal +
                " | Stock: " + getStock() +
                " | Categoría: " + getCategoria().getNombre() +
                " | Proveedor: " + getProveedor().getNombre());
    }
}
