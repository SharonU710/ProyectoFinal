package gestioninventario.persistence;

import gestioninventario.model.Producto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//  Genera archivo TXT con estadísticas sobre el inventario.
public class ReporteHandler
{
    private static final String FILE_PATH = "data/reporte_estadisticas.txt";

    public void exportarEstadisticas(List<Producto> productos)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH)))
        {
            writer.write("REPORTE DE ESTADÍSTICAS DEL INVENTARIO\n");
            writer.write("Generado en: " + LocalDateTime.now() + "\n");
            writer.write("===========================================\n\n");

            // Productos por tipo
            Map<String, Integer> conteoPorTipo = new HashMap<>();
            for (Producto p : productos)
            {
                String tipo = p.getClass().getSimpleName();
                conteoPorTipo.put(tipo, conteoPorTipo.getOrDefault(tipo, 0) + 1);
            }

            writer.write("Total de productos por tipo:\n");
            for (Map.Entry<String, Integer> entry : conteoPorTipo.entrySet())
            {
                writer.write("  - " + entry.getKey() + ": " + entry.getValue() + "\n");
            }

            // Stock por categoría
            writer.write("\nTotal de stock por categoría:\n");
            Map<String, Integer> stockPorCategoria = new HashMap<>();
            for (Producto p : productos)
            {
                String categoria = p.getCategoria().getNombre();
                stockPorCategoria.put(categoria, stockPorCategoria.getOrDefault(categoria, 0) + p.getStock());
            }
            for (Map.Entry<String, Integer> entry : stockPorCategoria.entrySet()) {
                writer.write("  - " + entry.getKey() + ": " + entry.getValue() + " unidades\n");
            }

            // Proveedor top
            writer.write("\nProveedor con más productos:\n");
            Map<String, Integer> productosPorProveedor = new HashMap<>();
            for (Producto p : productos)
            {
                String proveedor = p.getProveedor().getNombre();
                productosPorProveedor.put(proveedor, productosPorProveedor.getOrDefault(proveedor, 0) + 1);
            }

            if (!productosPorProveedor.isEmpty())
            {
                Map.Entry<String, Integer> top = Collections.max(productosPorProveedor.entrySet(), Map.Entry.comparingByValue());
                writer.write("  - " + top.getKey() + " (" + top.getValue() + " productos)\n");
            }

            writer.write("\n===========================================\n");
            writer.write("Fin del reporte.\n");

            System.out.println("Reporte exportado correctamente a " + FILE_PATH);

        }
        catch (IOException e)
        {
            System.err.println("Error al exportar reporte: " + e.getMessage());
        }
    }
}
