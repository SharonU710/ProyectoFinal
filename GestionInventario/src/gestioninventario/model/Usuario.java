package gestioninventario.model;

/*
    Objeto Usuario representa un usuario del sistema con credenciales de acceso.
*/
public class Usuario extends Persona
{
    private String username;
    private String password;
    private String rol;

    public Usuario(String nombre, String contacto, String username, String password, String rol)
    {
        super(nombre, contacto);
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getRol()
    {
        return rol;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setRol(String rol)
    {
        this.rol = rol;
    }

    @Override
    public String toString()
    {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", rol='" + rol + '\'' +
                ", nombre='" + nombre + '\'' +
                ", contacto='" + contacto + '\'' +
                '}';
    }
}

