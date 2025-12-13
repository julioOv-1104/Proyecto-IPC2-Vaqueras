package Entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usuario {

    private String correo_usuario;
    private String contrasenna;
    private String nickname;
    private String telefono;
    private String pais;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    private Date fecha_nacimiento;

    public Usuario(String correo_usuario, String contrasenna, String nickname, Date fecha_nacimiento, String telefono, String pais) {
        this.correo_usuario = correo_usuario;
        this.contrasenna = contrasenna;
        this.nickname = nickname;
        this.fecha_nacimiento = fecha_nacimiento;
        this.telefono = telefono;
        this.pais = pais;
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

}
