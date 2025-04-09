package gestioninventario.service;

import gestioninventario.interfaces.Gestionable;
import gestioninventario.model.Usuario;
import java.util.ArrayList;
import java.util.List;

// Servicio para gestionar usuarios del sistema.

public class UsuarioService implements Gestionable<Usuario>
{

    private final List<Usuario> usuarios;

    public UsuarioService()
    {
        this.usuarios = new ArrayList<>();
    }
    
    public boolean autenticar(String username, String password)
    {
        return usuarios.stream()
                .anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
    }

    public boolean existe(String username)
    {
        return usuarios.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
    }

    @Override
    public void agregar(Usuario usuario)
    {
        usuarios.add(usuario);
    }

    @Override
    public boolean eliminarPorId(String username)
    {
        return usuarios.removeIf(u -> u.getUsername().equalsIgnoreCase(username));
    }

    @Override
    public Usuario buscarPorId(String username)
    {
        return usuarios.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Usuario> listarTodos()
    {
        return new ArrayList<>(usuarios);
    }
}