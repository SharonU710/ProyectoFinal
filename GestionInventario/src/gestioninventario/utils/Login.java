package gestioninventario.utils;

import gestioninventario.model.Usuario;
import gestioninventario.persistence.UsuarioHandler;
import java.util.Scanner;

// Login desde consola
public class Login
{

    private final UsuarioHandler usuarioHandler;

    public Login(UsuarioHandler usuarioHandler)
    {
        this.usuarioHandler = usuarioHandler;
    }

    public Usuario iniciarSesion()
    {
        Scanner scanner = new Scanner(System.in);
        Usuario usuario = null;

        System.out.println("===== SISTEMA DE INVENTARIO - LOGIN =====");

        for (int intentos = 0; intentos < 3 && usuario == null; intentos++)
        {
            System.out.print("Usuario: ");
            String username = scanner.nextLine();

            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            usuario = usuarioHandler.login(username, password);

            if (usuario == null)
            {
                System.out.println("Usuario o contraseña incorrectos.");
            }
        }

        if (usuario != null)
        {
            System.out.println("Bienvenido, " + usuario.getNombre() + " [" + usuario.getRol() + "]");
        }
        else
        {
            System.out.println("Demasiados intentos fallidos. Saliendo...");
            System.exit(0);
        }

        return usuario;
    }
}