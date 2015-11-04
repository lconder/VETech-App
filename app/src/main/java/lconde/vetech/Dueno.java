package lconde.vetech;


public class Dueno
{
    String idDueno;
    String nombre;
    String direccion;
    String telefono;
    String email;

    public Dueno(String idDueno, String nombre, String direccion, String telefono, String email) {
        this.idDueno = idDueno;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }


    public String getIdDueno() {
        return idDueno;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setIdDueno(String idDueno) {
        this.idDueno = idDueno;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
