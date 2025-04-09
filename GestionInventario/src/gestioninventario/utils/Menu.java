package gestioninventario.utils;

import gestioninventario.model.*;
import gestioninventario.persistence.*;
import gestioninventario.service.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.lang.reflect.Field;

/*
    Menu para Navegar en el Sistema
*/
public class Menu
{

    private final InventarioService inventarioService;
    private final ProductoHandler productHandler;
    private final ProveedorHandler proveedorHandler;
    private final UsuarioHandler usuarioHandler;
    private final UsuarioService usuarioService;
    private final ProveedorService proveedorService;
    private final AlertaService alertaService;
    private final Scanner scanner;

    public Menu(InventarioService inventarioService, ProductoHandler productHandler, ProveedorHandler proveedorHandler, UsuarioHandler usuarioHandler, UsuarioService usuarioService, ProveedorService proveedorService)
    {

        this.inventarioService = inventarioService;
        this.productHandler = productHandler;
        this.proveedorHandler = proveedorHandler;
        this.usuarioHandler = usuarioHandler;
        this.usuarioService = usuarioService;
        this.proveedorService = proveedorService;
        this.alertaService = new AlertaService();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu()
    {
        int opcion;
        do
        {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Agregar producto alimenticio");
            System.out.println("2. Agregar producto electrónico");
            System.out.println("3. Listar productos");
            System.out.println("4. Eliminar producto");
            System.out.println("5. Modificar producto");
            System.out.println("6. Buscar producto por proveedor");
            System.out.println("7. Mostrar productos por proveedor");
            System.out.println("8. Ver estadísticas");
            System.out.println("9. Exportar estadísticas a TXT");
            System.out.println("10. Agregar producto misceláneo");
            System.out.println("11. Crear proveedor");
            System.out.println("12. Eliminar proveedor");
            System.out.println("13. Crear usuario");
            System.out.println("14. Eliminar usuario");
            System.out.println("15. Listar usuarios");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion)
            {
                case 0: System.out.println("¡Hasta luego!"); break;
                case 1: agregarProductoAlimenticio(); break;
                case 2: agregarProductoElectronico(); break;
                case 3: listarProductos(); break;
                case 4: eliminarProducto(); break;
                case 5: modificarProducto(); break;
                case 6: buscarPorProveedor(); break;
                case 7: mostrarAgrupadosPorProveedor(); break;
                case 8: mostrarEstadisticas(); break;
                case 9: exportarEstadisticas(); break;
                case 10: agregarProductoMiscelaneo(); break;
                case 11: crearProveedor(); break;
                case 12: eliminarProveedor(); break;
                case 13: crearUsuario(); break;
                case 14: eliminarUsuario(); break;
                case 15: listarUsuarios(); break;
                default: System.err.println("Opcion invalida.");
            }

        }
        while (opcion != 0);
    }
    
    private String pedir(String mensaje)
    {
        System.out.print(mensaje + ": ");
        return scanner.nextLine();
    }
    
    private Object convertir(Class<?> tipo, String valor)
    {
        if (tipo == int.class) return Integer.valueOf(valor);
        if (tipo == boolean.class) return Boolean.valueOf(valor);
        if (tipo == String.class) return valor;
        if (tipo == Categoria.class) return new Categoria(valor);
        if (tipo == Proveedor.class) return proveedorHandler.getProveedorPorNombre(valor);
        return null;
    }

    // Funcionalidades:
    
    // ----------- GESTION DE PRODUCTOS -----------
    
        private void agregarProductoAlimenticio() {
        Producto producto = new ProductoAlimenticio(
                pedir("Codigo"),
                pedir("Nombre"),
                Integer.parseInt(pedir("Stock")),
                new Categoria(pedir("Categoría")),
                seleccionarProveedor(),
                pedir("Fecha de vencimiento (YYYY-MM-DD)")
        );
        agregarYGuardar(producto);
    }

    private void agregarProductoElectronico() {
        Producto producto = new ProductoElectronico(
                pedir("Codigo"),
                pedir("Nombre"),
                Integer.parseInt(pedir("Stock")),
                new Categoria(pedir("Categoría")),
                seleccionarProveedor(),
                Integer.parseInt(pedir("Garantía (meses)"))
        );
        agregarYGuardar(producto);
    }

    private void agregarProductoMiscelaneo()
    {
        Producto producto = new ProductoMiscelaneo(
                pedir("Codigo"),
                pedir("Nombre"),
                Integer.parseInt(pedir("Stock")),
                new Categoria(pedir("Categoría")),
                seleccionarProveedor()
        );
        agregarYGuardar(producto);
    }

    private void agregarYGuardar(Producto producto)
    {
        inventarioService.agregar(producto);
        alertaService.verificarAlertas(producto);
        productHandler.exportarProductosACsv(inventarioService.listarTodos());
        System.out.println("Producto agregado.");
    }

    private void listarProductos()
    {
        for (Producto p : inventarioService.listarTodos()) {
            p.mostrarDetalles();
        }
    }

    private void eliminarProducto()
    {
        String codigo = pedir("Codigo del producto a eliminar");
        if (inventarioService.eliminarPorId(codigo))
        {
            productHandler.exportarProductosACsv(inventarioService.listarTodos());
            System.out.println("Producto eliminado.");
        }
        else
        {
            System.out.println("Producto no encontrado.");
        }
    }

    private void modificarProducto()
    {
        String codigo = pedir("Codigo del producto a modificar");
        Producto producto = inventarioService.buscarPorId(codigo);
        if (producto == null)
        {
            System.out.println("Producto no encontrado.");
            return;
        }

        List<Field> campos = new ArrayList<>();
        Class<?> clase = producto.getClass();
        while (clase != null && clase != Object.class)
        {
            campos.addAll(Arrays.asList(clase.getDeclaredFields()));
            clase = clase.getSuperclass();
        }

        for (int i = 0; i < campos.size(); i++)
        {
            System.out.println((i + 1) + ". " + campos.get(i).getName());
        }

        int opcion = Integer.parseInt(pedir("Seleccione atributo a modificar"));
        if (opcion < 1 || opcion > campos.size())
        {
            System.out.println("Opción inválida.");
            return;
        }

        Field campo = campos.get(opcion - 1);
        campo.setAccessible(true);
        
        try
        {
            String nuevoValor = pedir("Nuevo valor para " + campo.getName());
            Object valorConvertido = convertir(campo.getType(), nuevoValor);
            campo.set(producto, valorConvertido);
            productHandler.exportarProductosACsv(inventarioService.listarTodos());
            System.out.println("Producto modificado.");
        }
        catch (Exception e)
        {
            System.err.println("Error al modificar: " + e.getMessage());
        }
    }
    
    // ------- 
    
    private Proveedor seleccionarProveedor()
    {
        List<Proveedor> lista = new ArrayList<>(proveedorHandler.getTodos());
        
        for (int i = 0; i < lista.size(); i++)
        {
            System.out.println((i + 1) + ". " + lista.get(i).getNombre());
        }
        
        int idx = Integer.parseInt(pedir("Seleccione proveedor")) - 1;
        
        return lista.get(idx);
    }
    
    private void buscarPorProveedor()
    {
        String nombre = pedir("Nombre del proveedor");
        
        for (Producto p : inventarioService.listarTodos())
        {
            if (p.getProveedor().getNombre().equalsIgnoreCase(nombre))
            {
                p.mostrarDetalles();
            }
        }
    }

    private void mostrarAgrupadosPorProveedor()
    {
        Map<String, List<Producto>> agrupado = new HashMap<>();
        
        for (Producto p : inventarioService.listarTodos())
        {
            String prov = p.getProveedor().getNombre();
            agrupado.computeIfAbsent(prov, k -> new ArrayList<>()).add(p);
        }

        for (var entry : agrupado.entrySet())
        {
            System.out.println("\nProveedor: " + entry.getKey());
            entry.getValue().forEach(Producto::mostrarDetalles);
        }
    }

    // ----------- GESTION DE PROVEEDORES -----------

    private void crearProveedor()
    {
        Proveedor p = new Proveedor(pedir("Nombre del proveedor"), pedir("Contacto"));
        proveedorService.agregar(p);
        proveedorHandler.exportarProveedores();
        System.out.println("Proveedor creado.");
    }

    private void eliminarProveedor()
    {
        String nombre = pedir("Nombre del proveedor a eliminar");

        if (proveedorService.asociado(nombre, inventarioService.listarTodos()))
        {
            System.out.println("Proveedor tiene productos asociados.");
            return;
        }

        if (proveedorService.eliminarPorId(nombre))
        {
            proveedorHandler.eliminarProveedor(nombre);
            proveedorHandler.exportarProveedores();
            System.out.println("Proveedor eliminado.");
        }
        else
        {
            System.out.println("Proveedor no encontrado.");
        }
    }

    // ----------- GESTION DE USUARIOS -----------

    private void crearUsuario()
    {
        String username = pedir("Username");
        if (usuarioService.existe(username))
        {
            System.out.println("Usuario ya existe.");
            return;
        }

        Usuario nuevo = new Usuario(
                pedir("Nombre"),
                pedir("Contacto"),
                username,
                pedir("Contraseña"),
                pedir("Rol")
        );

        usuarioService.agregar(nuevo);
        usuarioHandler.agregarUsuario(nuevo);
        System.out.println("Usuario creado.");
    }

    private void eliminarUsuario()
    {
        String username = pedir("Username del usuario a eliminar");
        
        if (usuarioService.eliminarPorId(username))
        {
            usuarioHandler.eliminarUsuario(username);
            System.out.println("Usuario eliminado.");
        }
        else
        {
            System.out.println("Usuario no encontrado.");
        }
    }

    private void listarUsuarios()
    {
        for (Usuario u : usuarioService.listarTodos())
        {
            System.out.println(u.getUsername() + " | " + u.getRol() + " | " + u.getNombre());
        }
    }

 
    private void mostrarEstadisticas()
    {
        List<Producto> productos = inventarioService.listarTodos();

        System.out.println("\nESTADÍSTICAS DEL INVENTARIO");

        // 1. Total de productos por tipo
        Map<String, Integer> productosPorTipo = new HashMap<>();
        
        for (Producto p : productos) {
            String tipo = p.getClass().getSimpleName();
            productosPorTipo.put(tipo, productosPorTipo.getOrDefault(tipo, 0) + 1);
        }

        System.out.println("\nTotal de productos por tipo:");
        
        for (Map.Entry<String, Integer> entry : productosPorTipo.entrySet()) {
            System.out.println(" - " + entry.getKey() + ": " + entry.getValue());
        }

        // Total de stock por categoría
        Map<String, Integer> stockPorCategoria = new HashMap<>();
        for (Producto p : productos)
        {
            String categoria = p.getCategoria().getNombre();
            stockPorCategoria.put(categoria, stockPorCategoria.getOrDefault(categoria, 0) + p.getStock());
        }

        System.out.println("\nTotal de stock por categoria:");
        
        for (Map.Entry<String, Integer> entry : stockPorCategoria.entrySet())
        {
            System.out.println(" - " + entry.getKey() + ": " + entry.getValue() + " unidades");
        }

        // Proveedor con mas productos
        Map<String, Integer> productosPorProveedor = new HashMap<>();
        
        for (Producto p : productos)
        {
            String prov = p.getProveedor().getNombre();
            productosPorProveedor.put(prov, productosPorProveedor.getOrDefault(prov, 0) + 1);
        }

        if (!productosPorProveedor.isEmpty())
        {
            Map.Entry<String, Integer> top = Collections.max(productosPorProveedor.entrySet(), Map.Entry.comparingByValue());
            System.out.println("\nProveedor con mas productos: " + top.getKey() + " (" + top.getValue() + " productos)");
        }
    }

    private void exportarEstadisticas()
    {
        List<Producto> productos = inventarioService.listarTodos();
        
        ReporteHandler reporteHandler = new ReporteHandler();
        reporteHandler.exportarEstadisticas(productos);
    }
}
