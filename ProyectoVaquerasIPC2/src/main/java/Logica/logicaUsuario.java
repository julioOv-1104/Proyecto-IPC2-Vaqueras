package Logica;

import DAOs.UsuarioDAO;
import Entidades.Usuario;
import Utilidades.TipoUsuarioENUM;

public class logicaUsuario {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario login(String correo_usuario, String contrasenna) {
        return usuarioDAO.login(correo_usuario, contrasenna);
    }

    public Usuario registrar(Usuario nuevo) {

        if (nuevo.getTipo_usuario().equals(TipoUsuarioENUM.ADMIN)) {
            return usuarioDAO.registrarAdmin(nuevo);
            
        } else if (nuevo.getTipo_usuario().equals(TipoUsuarioENUM.USUARIO_COMUN)) {
            return usuarioDAO.registrarUsuario(nuevo);
        }
        return null;
    }
}
