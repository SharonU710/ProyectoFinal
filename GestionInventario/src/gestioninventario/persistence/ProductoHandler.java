package gestioninventario.persistence;

import gestioninventario.model.*;
import gestioninventario.service.InventarioService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

// Maneja importacion/exportacion de productos desde/hacia archivo CSV.
public class ProductoHandler
{
    private static final String FILE_PATH = "data/productos.csv";
    private static final String SEPARATOR = ",";
    private final ProveedorHandler proveedorHandler;

    public ProductoHandler(ProveedorHandler proveedorHandler)
    {
        this.proveedorHandler = proveedorHandler;
    }

    public void cargarProductosDesdeCsv(InventarioService inventarioService)
    {
        File archivo = new File(FILE_PATH);
        if (!archivo.exists())
        {
            System.out.println("Archivo productos.csv no encontrado. Se creará uno nuevo.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null)
            {
                String[] datos = linea.split(SEPARATOR);

                String tipo = datos[0];
                String codigo = datos[1];
                String nombre = datos[2];
                int stock = Integer.parseInt(datos[3]);
                String categoriaStr = datos[4];
                String proveedorNombre = datos[5];
                String proveedorContacto = datos[6];

                Categoria categoria = new Categoria(categoriaStr);
                Proveedor proveedor = proveedorHandler.getProveedorPorNombre(proveedorNombre);
                
                if (proveedor == null)
                {
                    proveedor = new Proveedor(proveedorNombre, proveedorContacto);
                    proveedorHandler.agregarProveedor(proveedor);
                }

                Producto producto = null;

                switch (tipo.toUpperCase())
                {
                    case "ALIMENTICIO":
                        String fechaVenc = datos[7];
                        producto = new ProductoAlimenticio(codigo, nombre, stock, categoria, proveedor, fechaVenc);
                        break;
                    case "ELECTRONICO":
                        int garantia = Integer.parseInt(datos[7]);
                        producto = new ProductoElectronico(codigo, nombre, stock, categoria, proveedor, garantia);
                        break;
                    case "MASCOTA_ALIMENTICIO":
                        fechaVenc = datos[7];
                        String animal1 = datos[8];
                        producto = new ProductoMascotaAlimenticio(codigo, nombre, stock, categoria, proveedor, fechaVenc, animal1);
                        break;
                    case "MASCOTA_ELECTRONICO":
                        garantia = Integer.parseInt(datos[7]);
                        String animal2 = datos[8];
                        producto = new ProductoMascotaElectronico(codigo, nombre, stock, categoria, proveedor, garantia, animal2);
                        break;
                    case "MASCOTA_GENERAL":
                        String animal3 = datos[7];
                        producto = new ProductoMascotaGeneral(codigo, nombre, stock, categoria, proveedor, animal3);
                        break;
                    case "CONSTRUCCION":
                        garantia = Integer.parseInt(datos[7]);
                        boolean requiereLicencia = Boolean.parseBoolean(datos[8]);
                        producto = new ProductoConstruccion(codigo, nombre, stock, categoria, proveedor, garantia, requiereLicencia);
                        break;
                    case "JUGUETERIA":
                        garantia = Integer.parseInt(datos[7]);
                        int edad = Integer.parseInt(datos[8]);
                        producto = new ProductoJugueteria(codigo, nombre, stock, categoria, proveedor, garantia, edad);
                        break;
                    case "MISCELANEO":
                        producto = new ProductoMiscelaneo(codigo, nombre, stock, categoria, proveedor);
                        break;
                    default:
                        System.out.println("Tipo de producto desconocido: " + tipo);
                }

                if (producto != null)
                {
                    inventarioService.agregar(producto);
                }
            }

            System.out.println("Productos cargados desde productos.csv");

        }
        catch (Exception e)
        {
            System.err.println("Error al cargar productos: " + e.getMessage());
        }
    }

    public void exportarProductosACsv(List<Producto> productos)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH)))
        {
            for (Producto p : productos)
            {
                writer.write(convertirProductoACsv(p));
                writer.newLine();
            }
            System.out.println("Productos exportados a productos.csv");
        } catch (IOException e)
        {
            System.err.println("Error al exportar productos: " + e.getMessage());
        }
    }

    private String convertirProductoACsv(Producto p)
    {
        StringBuilder sb = new StringBuilder();
        String tipo = "MISCELANEO";

        if (p instanceof ProductoAlimenticio && !(p instanceof ProductoMascotaAlimenticio))
        {
            tipo = "ALIMENTICIO";
            sb.append(tipo).append(SEPARATOR)
              .append(p.getCodigo()).append(SEPARATOR)
              .append(p.getNombre()).append(SEPARATOR)
              .append(p.getStock()).append(SEPARATOR)
              .append(p.getCategoria().getNombre()).append(SEPARATOR)
              .append(p.getProveedor().getNombre()).append(SEPARATOR)
              .append(p.getProveedor().getContacto()).append(SEPARATOR)
              .append(((ProductoAlimenticio) p).getFechaVencimiento());
        }
        else if (p instanceof ProductoElectronico && !(p instanceof ProductoMascotaElectronico) && !(p instanceof ProductoConstruccion) && !(p instanceof ProductoJugueteria))
        {
            tipo = "ELECTRONICO";
            sb.append(tipo).append(SEPARATOR)
              .append(p.getCodigo()).append(SEPARATOR)
              .append(p.getNombre()).append(SEPARATOR)
              .append(p.getStock()).append(SEPARATOR)
              .append(p.getCategoria().getNombre()).append(SEPARATOR)
              .append(p.getProveedor().getNombre()).append(SEPARATOR)
              .append(p.getProveedor().getContacto()).append(SEPARATOR)
              .append(((ProductoElectronico) p).getGarantiaMeses());
        }
        else if (p instanceof ProductoMascotaAlimenticio)
        {
            tipo = "MASCOTA_ALIMENTICIO";
            ProductoMascotaAlimenticio m = (ProductoMascotaAlimenticio) p;
            sb.append(tipo).append(SEPARATOR)
              .append(m.getCodigo()).append(SEPARATOR)
              .append(m.getNombre()).append(SEPARATOR)
              .append(m.getStock()).append(SEPARATOR)
              .append(m.getCategoria().getNombre()).append(SEPARATOR)
              .append(m.getProveedor().getNombre()).append(SEPARATOR)
              .append(m.getProveedor().getContacto()).append(SEPARATOR)
              .append(m.getFechaVencimiento()).append(SEPARATOR)
              .append(m.getTipoAnimal());
        }
        else if (p instanceof ProductoMascotaElectronico)
        {
            tipo = "MASCOTA_ELECTRONICO";
            ProductoMascotaElectronico m = (ProductoMascotaElectronico) p;
            sb.append(tipo).append(SEPARATOR)
              .append(m.getCodigo()).append(SEPARATOR)
              .append(m.getNombre()).append(SEPARATOR)
              .append(m.getStock()).append(SEPARATOR)
              .append(m.getCategoria().getNombre()).append(SEPARATOR)
              .append(m.getProveedor().getNombre()).append(SEPARATOR)
              .append(m.getProveedor().getContacto()).append(SEPARATOR)
              .append(m.getGarantiaMeses()).append(SEPARATOR)
              .append(m.getTipoAnimal());
        }
        else if (p instanceof ProductoMascotaGeneral)
        {
            tipo = "MASCOTA_GENERAL";
            ProductoMascotaGeneral m = (ProductoMascotaGeneral) p;
            sb.append(tipo).append(SEPARATOR)
              .append(m.getCodigo()).append(SEPARATOR)
              .append(m.getNombre()).append(SEPARATOR)
              .append(m.getStock()).append(SEPARATOR)
              .append(m.getCategoria().getNombre()).append(SEPARATOR)
              .append(m.getProveedor().getNombre()).append(SEPARATOR)
              .append(m.getProveedor().getContacto()).append(SEPARATOR)
              .append(m.getTipoAnimal());
        }
        else if (p instanceof ProductoConstruccion)
        {
            tipo = "CONSTRUCCION";
            ProductoConstruccion c = (ProductoConstruccion) p;
            sb.append(tipo).append(SEPARATOR)
              .append(c.getCodigo()).append(SEPARATOR)
              .append(c.getNombre()).append(SEPARATOR)
              .append(c.getStock()).append(SEPARATOR)
              .append(c.getCategoria().getNombre()).append(SEPARATOR)
              .append(c.getProveedor().getNombre()).append(SEPARATOR)
              .append(c.getProveedor().getContacto()).append(SEPARATOR)
              .append(c.getGarantiaMeses()).append(SEPARATOR)
              .append(c.isRequiereLicencia());
        }
        else if (p instanceof ProductoJugueteria)
        {
            tipo = "JUGUETERIA";
            ProductoJugueteria j = (ProductoJugueteria) p;
            sb.append(tipo).append(SEPARATOR)
              .append(j.getCodigo()).append(SEPARATOR)
              .append(j.getNombre()).append(SEPARATOR)
              .append(j.getStock()).append(SEPARATOR)
              .append(j.getCategoria().getNombre()).append(SEPARATOR)
              .append(j.getProveedor().getNombre()).append(SEPARATOR)
              .append(j.getProveedor().getContacto()).append(SEPARATOR)
              .append(j.getGarantiaMeses()).append(SEPARATOR)
              .append(j.getEdadRecomendada());
        }
        else if (p instanceof ProductoMiscelaneo)
        {
            tipo = "MISCELANEO";
            sb.append(tipo).append(SEPARATOR)
              .append(p.getCodigo()).append(SEPARATOR)
              .append(p.getNombre()).append(SEPARATOR)
              .append(p.getStock()).append(SEPARATOR)
              .append(p.getCategoria().getNombre()).append(SEPARATOR)
              .append(p.getProveedor().getNombre()).append(SEPARATOR)
              .append(p.getProveedor().getContacto()).append(SEPARATOR);
        }

        return sb.toString();
    }
}