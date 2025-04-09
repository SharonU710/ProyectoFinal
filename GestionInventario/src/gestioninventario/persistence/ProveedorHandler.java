package gestioninventario.persistence;

import gestioninventario.model.Proveedor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// Maneja la importacion/exportacion de proveedores desde/hacia CSV.
public class ProveedorHandler
{
    private static final String FILE_PATH = "data/proveedores.csv";
    private final Map<String, Proveedor> proveedores = new HashMap<>();

    public void cargarProveedores()
    {
        proveedores.clear();
        File archivo = new File(FILE_PATH);

        if (!archivo.exists())
        {
            System.out.println("Archivo proveedores.csv no encontrado.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo)))
        {
            String linea;
            while ((linea = br.readLine()) != null)
            {
                String[] datos = linea.split(",");
                if (datos.length == 2)
                {
                    Proveedor proveedor = new Proveedor(datos[0], datos[1]);
                    proveedores.put(proveedor.getNombre(), proveedor);
                }
            }
            System.out.println("Proveedores cargados.");
        }
        catch (IOException e)
        {
            System.err.println("Error al leer proveedores: " + e.getMessage());
        }
    }

    public void exportarProveedores()
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH)))
        {
            for (Proveedor p : proveedores.values())
            {
                bw.write(p.getNombre() + "," + p.getContacto());
                bw.newLine();
            }
        }
        catch (IOException e)
        {
            System.err.println("Error al exportar proveedores: " + e.getMessage());
        }
    }

    public Proveedor getProveedorPorNombre(String nombre)
    {
        return proveedores.get(nombre);
    }

    public void agregarProveedor(Proveedor proveedor)
    {
        proveedores.put(proveedor.getNombre(), proveedor);
    }

    public boolean eliminarProveedor(String nombre)
    {
        return proveedores.remove(nombre) != null;
    }

    public Collection<Proveedor> getTodos()
    {
        return proveedores.values();
    }
}
