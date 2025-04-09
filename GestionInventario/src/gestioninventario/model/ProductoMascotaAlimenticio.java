package gestioninventario.model;

/*
  Producto alimenticio para animal
  Hereda de ProductoAlimenticio y agrega el tipo de animal
*/
public class ProductoMascotaAlimenticio extends ProductoAlimenticio
{
    private String tipoAnimal;

    public ProductoMascotaAlimenticio(String codigo, String nombre, int stock, Categoria categoria, Proveedor proveedor, String fechaVencimiento, String tipoAnimal)
    {
        super(codigo, nombre, stock, categoria, proveedor, fechaVencimiento);
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
        System.out.println("Producto Mascota (Alimenticio): " + getNombre() +
                " | Tipo: " + tipoAnimal +
                " | Stock: " + getStock() +
                " | Vence: " + getFechaVencimiento() +
                " | Proveedor: " + getProveedor().getNombre());
    }
}
