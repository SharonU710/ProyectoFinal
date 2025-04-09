package gestioninventario.model;

/*
   Producto del tipo juguetería.
*/
public class ProductoJugueteria extends ProductoElectronico
{
    private int edadRecomendada;

    public ProductoJugueteria(String codigo, String nombre, int stock, Categoria categoria, Proveedor proveedor, int garantiaMeses, int edadRecomendada)
    {
        super(codigo, nombre, stock, categoria, proveedor, garantiaMeses);
        this.edadRecomendada = edadRecomendada;
    }

    public int getEdadRecomendada()
    {
        return edadRecomendada;
    }

    public void setEdadRecomendada(int edadRecomendada)
    {
        this.edadRecomendada = edadRecomendada;
    }

    @Override
    public void mostrarDetalles()
    {
        System.out.println("Producto Juguetería: " + getNombre() +
                " | Stock: " + getStock() +
                " | Garantía: " + getGarantiaMeses() + " meses" +
                " | Edad recomendada: " + edadRecomendada + " años" +
                " | Proveedor: " + getProveedor().getNombre());
    }
}
