package gestioninventario.model;

/*
    Objeto proveedor representa un proveedor asociado a productos del inventario.
*/
public class Proveedor extends Persona
{

    public Proveedor(String nombre, String contacto)
    {
        super(nombre, contacto);
    }

    @Override
    public String toString()
    {
        return "Proveedor{" +
                "nombre='" + nombre + '\'' +
                ", contacto='" + contacto + '\'' +
                '}';
    }
}
