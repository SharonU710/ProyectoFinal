package gestioninventario.model;

// Objeto abstracto que representa a una Persona.
public abstract class Persona
{
    protected String nombre;
    protected String contacto;

    public Persona(String nombre, String contacto)
    {
        this.nombre = nombre;
        this.contacto = contacto;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getContacto()
    {
        return contacto;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public void setContacto(String contacto)
    {
        this.contacto = contacto;
    }

    @Override
    public String toString()
    {
        return "Nombre: " + nombre + ", Contacto: " + contacto;
    }
}
