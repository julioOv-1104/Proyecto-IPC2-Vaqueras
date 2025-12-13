package DAOs;

import Entidades.Usuario;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    ConexionDB conexion = new ConexionDB();

    public Usuario login(String correo_usuario, String contrasenna) {

        String correo = correo_usuario.trim();
        String contra = contrasenna.trim();
        
        Usuario user = null;

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT correo_usuario, nickname, pais, fecha_nacimiento FROM usuario WHERE correo_usuario=? AND contrasenna=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, correo);
            stm.setString(2, contra);
            
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
                user = new Usuario
        (rs.getString("correo_usuario"), rs.getString("nickname"), rs.getString("pais"), rs.getDate("fecha_nacimiento"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}
