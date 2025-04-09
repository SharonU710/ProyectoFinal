package gestioninventario.model;

/*
    Objeto abstracto que representa un producto generico.
*/
public abstract class Producto
{
    private String codigo;
    private String nombre;
    private int stock;
    private Categoria categoria;
    private Proveedor proveedor;

    public Producto(String codigo, String nombre, int stock, Categoria categoria, Proveedor proveedor)
    {
        this.codigo = codigo;
        this.nombre = nombre;
        this.stock = stock;
        this.categoria = categoria;
        this.proveedor = proveedor;
    }

    public String getCodigo()
    {
        return codigo;
    }

    public String getNombre()
    {
        return nombre;
    }

    public int getStock()
    {
        return stock;
    }

    public Categoria getCategoria()
    {
        return categoria;
    }

    public Proveedor getProveedor()
    {
        return proveedor;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public void setStock(int stock)
    {
        this.stock = stock;
    }

    public void setCategoria(Categoria categoria)
    {
        this.categoria = categoria;
    }

    public void setProveedor(Proveedor proveedor)
    {
        this.proveedor = proveedor;
    }

    /*
        Metodo abstracto para cada tipo de producto.
    */
    public abstract void mostrarDetalles();

    @Override
    public String toString()
    {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", stock=" + stock +
                ", categoria=" + categoria +
                ", proveedor=" + proveedor.getNombre() +
                '}';
    }
}
