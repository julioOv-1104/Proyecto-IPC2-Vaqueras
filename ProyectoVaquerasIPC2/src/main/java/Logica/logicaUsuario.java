
package Logica;

import DAOs.UsuarioDAO;
import Entidades.Usuario;

public class logicaUsuario {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario login(String correo_usuario, String contrasenna) {
        return usuarioDAO.login(correo_usuario, contrasenna);
    }
}
