
package Entidades;


public class GrupoFamiliar {
    
    private String correo_encargado;
    private String nombre;
    private String[] miembros = new String[6];

    public GrupoFamiliar() {
    }

    public GrupoFamiliar( String correo_encargado, String nombre) {
        this.correo_encargado = correo_encargado;
        this.nombre = nombre;
    }

    public String getCorreo_encargado() {
        return correo_encargado;
    }

    public void setCorreo_encargado(String correo_encargado) {
        this.correo_encargado = correo_encargado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
