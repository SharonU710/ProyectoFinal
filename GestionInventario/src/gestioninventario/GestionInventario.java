
package gestioninventario;

import gestioninventario.model.Usuario;
import gestioninventario.persistence.*;
import gestioninventario.service.*;
import gestioninventario.utils.Login;
import gestioninventario.utils.Menu;

public class GestionInventario
{
    public static void main(String[] args)
    {
        // Inicializar Handlers
        UsuarioHandler usuarioHandler = new UsuarioHandler();
        ProveedorHandler proveedorHandler = new ProveedorHandler();
        ProductoHandler productHandler = new ProductoHandler(proveedorHandler);
        ReporteHandler reporteHandler = new ReporteHandler();
        
        // Servicios
        UsuarioService usuarioService = new UsuarioService();
        ProveedorService proveedorService = new ProveedorService();
        InventarioService inventarioService = new InventarioService();
        
         // Cargar usuarios (en memoria)
        usuarioHandler.cargarUsuarios();
        
        // Login
        Login login = new Login(usuarioHandler);
        Usuario usuario = login.iniciarSesion();
        
        // Cargar datos desde archivos CSV
        proveedorHandler.cargarProveedores();
        productHandler.cargarProductosDesdeCsv(inventarioService);

        // Cargar Datos (en memoria)
        usuarioHandler.getUsuarios().forEach(usuarioService::agregar);
        proveedorHandler.getTodos().forEach(proveedorService::agregar);
        
        // Iniciar Menu interactivo por consola
        Menu menu = new Menu(inventarioService, productHandler, proveedorHandler, usuarioHandler, usuarioService, proveedorService);
        menu.mostrarMenu();
    }
}