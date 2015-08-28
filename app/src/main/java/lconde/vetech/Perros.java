package lconde.vetech;

public class Perros
{
    String nombre;
    String photo;
    String raza;
    String nacimiento;
    boolean sexo;
    String alimentacion;
    String idPerro;
    String descripcion;
    boolean perdido;
    boolean adoptado;




    public Perros(String nombre, String photo, String raza, String nacimiento, boolean sexo, String alimentacion, String idPerro, String descripcion, boolean adoptado, boolean perdido) {
        this.nombre = nombre;
        this.photo = photo;
        this.raza = raza;
        this.nacimiento = nacimiento;
        this.sexo = sexo;
        this.alimentacion = alimentacion;
        this.idPerro = idPerro;
        this.descripcion = descripcion;
        this.adoptado = adoptado;
        this.perdido = perdido;
    }

    public Perros(String photo, String nombre, String id) {
        this.photo = photo;
        this.idPerro=id;
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public void setAlimentacion(String alimentacion) {
        this.alimentacion = alimentacion;
    }

    public void setIdPerro(String idPerro) {
        this.idPerro = idPerro;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public boolean isSexo() {
        return sexo;
    }

    public String getAlimentacion() {
        return alimentacion;
    }

    public String getIdPerro() {
        return idPerro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getSexo(){
        if(sexo)
            return "Macho";
        else
            return "Hembra";
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {

        return photo;
    }

    public boolean isPerdido() {
        return perdido;
    }

    public boolean isAdoptado() {
        return adoptado;
    }

    public void setPerdido(boolean perdido) {
        this.perdido = perdido;
    }

    public void setAdoptado(boolean adoptado) {
        this.adoptado = adoptado;
    }
}
