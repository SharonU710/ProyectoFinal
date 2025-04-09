package gestioninventario.model;

/*
   Producto electronico especializado para animales.
   Atributo adicional tipo de animal.
*/
public class ProductoMascotaElectronico extends ProductoElectronico
{
    private String tipoAnimal;

    public ProductoMascotaElectronico(String codigo, String nombre, int stock, Categoria categoria, Proveedor proveedor, int garantiaMeses, String tipoAnimal)
    {
        super(codigo, nombre, stock, categoria, proveedor, garantiaMeses);
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
        System.out.println("Producto Mascota (Electrónico): " + getNombre() +
                " | Tipo: " + tipoAnimal +
                " | Stock: " + getStock() +
                " | Garantía: " + getGarantiaMeses() + " meses" +
                " | Proveedor: " + getProveedor().getNombre());
    }
}
