package gestioninventario.model;

//AObjeto de lerta de stock bajo en un producto
public class AlertaStock
{
    private Producto producto;
    private int stockMinimo;

    public AlertaStock(Producto producto, int stockMinimo)
    {
        this.producto = producto;
        this.stockMinimo = stockMinimo;
    }

    public Producto getProducto()
    {
        return producto;
    }

    public int getStockMinimo()
    {
        return stockMinimo;
    }

    public boolean estaEnAlerta()
    {
        return producto.getStock() < stockMinimo;
    }

    @Override
    public String toString()
    {
        return "Alerta de Stock: " +
                producto.getNombre() + " (Stock actual: " + producto.getStock() + ", mínimo requerido: " + stockMinimo + ")";
    }
}
