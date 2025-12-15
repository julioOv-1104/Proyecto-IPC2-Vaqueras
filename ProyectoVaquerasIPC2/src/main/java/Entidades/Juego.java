
package Entidades;

import Utilidades.TipoClasificacion;
import java.util.Date;

public class Juego {
    
    private String titulo;
    private String descripcion;
    private double precio;
    private TipoClasificacion clasificacion;
    private Date fecha_lanzamiento;
    private String multimedia; 
    private boolean habilitado = true; 
    private String nombre_empresa;

    public Juego() {
    }

    public Juego(String titulo, String descripcion, double precio, TipoClasificacion clasificacion, Date fecha_lanzamiento, String multimedia, boolean habilitado, String nombre_empresa) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.clasificacion = clasificacion;
        this.fecha_lanzamiento = fecha_lanzamiento;
        this.multimedia = multimedia;
        this.habilitado = habilitado;
        this.nombre_empresa = nombre_empresa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public TipoClasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(TipoClasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Date getFecha_lanzamiento() {
        return fecha_lanzamiento;
    }

    public void setFecha_lanzamiento(Date fecha_lanzamiento) {
        this.fecha_lanzamiento = fecha_lanzamiento;
    }

    public String getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(String multimedia) {
        this.multimedia = multimedia;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }
  
}
