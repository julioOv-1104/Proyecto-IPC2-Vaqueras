package Logica;

import DAOs.*;
import Entidades.*;
import Utilidades.TipoUsuarioENUM;
import java.sql.Date;
import java.util.ArrayList;

public class LogicaUsuario {

    private  UsuarioDAO usuarioDAO = new UsuarioDAO();
    private GamerDAO gamerDao = new GamerDAO();

    public Usuario login(String correo_usuario, String contrasenna) {
        return usuarioDAO.login(correo_usuario, contrasenna);
    }
    
    public Usuario obtenerUsuario(String correo_usuario){
     return usuarioDAO.exportarUsuario(correo_usuario);
    }
    
     public ArrayList<Compra> obtenerCompra(String correo_usuario){
     return gamerDao.exportarCompra(correo_usuario);
    }

    public Usuario registrar(Usuario nuevo) {

        if (nuevo.getTipo_usuario().equals(TipoUsuarioENUM.ADMIN)) {
            return usuarioDAO.registrarAdmin(nuevo);
            
        } else if (nuevo.getTipo_usuario().equals(TipoUsuarioENUM.USUARIO_COMUN)) {
            return usuarioDAO.registrarUsuario(nuevo);
            
        }else if (nuevo.getTipo_usuario().equals(TipoUsuarioENUM.EMPRESARIO)) {
            return usuarioDAO.registrarEmpresario(nuevo);
        }
        return null;
    }
    
    public boolean recargarCarteraGamer(String correo,double recarga){
     return gamerDao.recargarCartera(correo, recarga);
     
    }
    
    public boolean comprarJuego(String correo, String titulo, Date fecha_compra){
        return gamerDao.comprarJuego(correo, titulo, fecha_compra);
    
    }
}
