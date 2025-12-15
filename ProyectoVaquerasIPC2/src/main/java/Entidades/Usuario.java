package Entidades;

import Utilidades.TipoUsuarioENUM;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.sql.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usuario {

    private String correo_usuario;
    private String contrasenna;
    private String nickname;
    private String telefono;
    private String pais;
    private String nombre;
    private String nombre_empresa;
    private boolean biblioteca_visible = true;
    private double cartera= 0;
    private TipoUsuarioENUM tipo_usuario;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    private Date fecha_nacimiento;

    public Usuario(String correo_usuario, String contrasenna, String nickname, String telefono, String pais, String nombre, String nombre_empresa, double cartera, TipoUsuarioENUM tipo_usuario, Date fecha_nacimiento) {
        this.correo_usuario = correo_usuario;
        this.contrasenna = contrasenna;
        this.nickname = nickname;
        this.telefono = telefono;
        this.pais = pais;
        this.nombre = nombre;
        this.nombre_empresa = nombre_empresa;
        this.cartera = cartera;
        this.tipo_usuario = tipo_usuario;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    

    

    public Usuario() {
    }

    public Usuario(String correo_usuario, String nickname, String pais, Date fecha) {
        this.correo_usuario = correo_usuario;
        this.nickname = nickname;
        this.pais = pais;
        this.fecha_nacimiento = fecha;
    }

    public String getCorreo_usuario() {
        return correo_usuario;
    }

    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public boolean isBiblioteca_visible() {
        return biblioteca_visible;
    }

    public void setBiblioteca_visible(boolean biblioteca_visible) {
        this.biblioteca_visible = biblioteca_visible;
    }

    public double getCartera() {
        return cartera;
    }

    public void setCartera(double cartera) {
        this.cartera = cartera;
    }

    public TipoUsuarioENUM getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(TipoUsuarioENUM tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    
    
    
}
