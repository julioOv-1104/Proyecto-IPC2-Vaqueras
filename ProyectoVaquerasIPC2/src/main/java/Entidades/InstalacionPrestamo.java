
package Entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;

public class InstalacionPrestamo {
    
    private String correo_usuario;
    private String titulo;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    private Date fecha_instalacion;
    private boolean estado;

    public InstalacionPrestamo() {
    }

    public String getCorreo_usuario() {
        return correo_usuario;
    }

    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha_instalacion() {
        return fecha_instalacion;
    }

    public void setFecha_instalacion(Date fecha_instalacion) {
        this.fecha_instalacion = fecha_instalacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    
    
}
