
package Entidades;

import java.util.ArrayList;


public class GrupoFamiliar {
    
    private String correo_encargado;
    private String nombre;
    private ArrayList<String> miembros = new ArrayList<>();
    private int id_grupo;

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

    public ArrayList<String> getMiembros() {
        return miembros;
    }

    public void setMiembros(ArrayList<String> miembros) {
        this.miembros = miembros;
    }

    public int getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(int id_grupo) {
        this.id_grupo = id_grupo;
    }
    
    
}
