package gestioninventario.service;

import gestioninventario.model.AlertaStock;
import gestioninventario.model.Producto;
import java.util.ArrayList;
import java.util.List;

// Servicio para gestionar alertas de productos con stock bajo
public class AlertaService
{

    private final List<AlertaStock> alertas = new ArrayList<>();
    private final int stockMinimo = 10; // 10 en stock sera considerado bajo en stock

    public void verificarAlertas(Producto producto)
    {
        if (producto.getStock() < stockMinimo)
        {
            AlertaStock alerta = new AlertaStock(producto, stockMinimo);
            alertas.add(alerta);
            System.out.println(alerta);
        }
    }

    public List<AlertaStock> obtenerAlertas()
    {
        return new ArrayList<>(alertas);
    }

    public void limpiarAlertas()
    {
        alertas.clear();
    }
}
