package gestioninventario.persistence;

import gestioninventario.model.Usuario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// Maneja la autenticación y la importacion/exportacion de usuarios desde/hacia CSV.
public class UsuarioHandler
{
    private static final String FILE_PATH = "data/usuarios.csv";
    private final List<Usuario> usuarios = new ArrayList<>();

    public void cargarUsuarios()
    {   
        usuarios.clear();
        
        File archivo = new File(FILE_PATH);
        
        if (!archivo.exists())
        {
            System.out.println("Archivo usuarios.csv no encontrado.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo)))
        {   
            String linea;
            while ((linea = br.readLine()) != null)
            {
                String[] datos = linea.split(",");
                if (datos.length == 5)
                {
                    Usuario usuario = new Usuario(datos[0], datos[1], datos[2], datos[3], datos[4]);
                    usuarios.add(usuario);
                }
            }
            System.out.println("Usuarios cargados.");
        }
        catch (IOException e)
        {
            System.err.println("Error al leer usuarios: " + e.getMessage());
        }
    }

    public void exportarUsuarios()
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH)))
        {
            for (Usuario u : usuarios)
            {
                bw.write(u.getNombre() + "," + u.getContacto() + "," + u.getUsername() + "," + u.getPassword() + "," + u.getRol());
                bw.newLine();
            }
        } catch (IOException e)
        {
            System.err.println("Error al exportar usuarios: " + e.getMessage());
        }
    }

    public Usuario login(String username, String password)
    {
        return usuarios.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public boolean agregarUsuario(Usuario usuario)
    {
        if (usuarios.stream().anyMatch(u -> u.getUsername().equals(usuario.getUsername())))
        {
            return false;
        }
        
        usuarios.add(usuario);
        exportarUsuarios();
        
        return true;
    }

    public boolean eliminarUsuario(String username)
    {
        boolean eliminado = usuarios.removeIf(u -> u.getUsername().equals(username));
        
        if (eliminado)
        {
            exportarUsuarios();
        }
        
        return eliminado;
    }

    public List<Usuario> getUsuarios()
    {
        return usuarios;
    }
}
