
package Entidades;

import Utilidades.TipoClasificacion;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.sql.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Juego {
    
    private String titulo;
    private String descripcion;
    private double precio;
    private TipoClasificacion clasificacion;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    private Date fecha_lanzamiento;

    private String tipo_multimedia;
    private String multimedia;
    private boolean habilitado = true; 
    private String nombre_empresa;

    public Juego() {
    }

    public Juego(String titulo, String descripcion, double precio, TipoClasificacion clasificacion, Date fecha_lanzamiento, String nombre_empresa, String tipo_multimedia, String multi) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.clasificacion = clasificacion;
        this.fecha_lanzamiento = fecha_lanzamiento;
        this.nombre_empresa = nombre_empresa;
        this.tipo_multimedia = tipo_multimedia;
        this.multimedia = multi;
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

    public String getTipo_multimedia() {
        return tipo_multimedia;
    }

    public void setTipo_multimedia(String tipo_multimedia) {
        this.tipo_multimedia = tipo_multimedia;
    }

    public String getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(String multimedia) {
        this.multimedia = multimedia;
    }
  
    
}
