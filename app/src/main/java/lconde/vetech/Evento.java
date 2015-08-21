package lconde.vetech;


public class Evento
{
    private Integer id;
    private String titulo;
    private String lugar;
    private String fecha;
    private String descripcion;
    private String link;
    private String imagen;

    public String getImagen() {return imagen;}

    public void setImagen(String imagen) {this.imagen = imagen;}

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getId() {return id;}

    public String getTitulo() {return titulo;}

    public String getLugar() {
        return lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getLink() {
        return link;
    }
}
