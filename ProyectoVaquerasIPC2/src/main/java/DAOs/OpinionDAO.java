package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OpinionDAO extends DAO {

    public boolean comentar(String correo, String titulo, String texto) {

        try (Connection conn = conexion.conectar()) {

            if (comprobarSiUsuarioTienenJuego(titulo, correo)) {//Solo puede comentar si compro el juego

                //Se agrega al usuario a su grupo
                String sql = "INSERT INTO comentario (correo_usuario, titulo, texto_comentario)"
                        + " VALUES (?,?,?)";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, correo);
                stm.setString(2, titulo);
                stm.setString(3, texto);

                stm.executeUpdate();

                return true;
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL INTENTAR COMENTAR " + e.getMessage());

        }

        return false;
    }

    public boolean responder(String correo, String texto, int id_padre) {

        try (Connection conn = conexion.conectar()) {

            //Se agrega al usuario a su grupo
            String sql = "INSERT INTO comentario (correo_usuario, id_comentario_padre, texto_comentario)"
                    + " VALUES (?,?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, correo);
            stm.setInt(2, id_padre);
            stm.setString(3, texto);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL INTENTAR RESPONDER COMENTARIO " + e.getMessage());

        }

        return false;
    }
    
    public boolean calificar(String correo, String titulo, double estrellas) {

        try (Connection conn = conexion.conectar()) {

            if (comprobarSiUsuarioTienenJuego(titulo, correo)) {//Solo puede calificar si compro el juego

                //Se agrega al usuario a su grupo
                String sql = "INSERT INTO calificacion (correo_usuario, titulo, estrellas)"
                        + " VALUES (?,?,?)";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, correo);
                stm.setString(2, titulo);
                stm.setDouble(3, estrellas);

                stm.executeUpdate();

                return true;
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL INTENTAR CALIFICAR EN DAO " + e.getMessage());

        }

        return false;
    }
    

    private boolean comprobarSiUsuarioTienenJuego(String titulo, String correo) {

        try (Connection conn = conexion.conectar()) {

            //Se agrega al usuario a su grupo
            String sql = "SELECT 1 FROM compra WHERE correo_usuario = ? AND titulo = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, correo);
            stm.setString(2, titulo);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return true;
            }

            System.out.println("NO TIENE EL JUEGO COMPRADO");
            return false;

        } catch (SQLException e) {
            System.out.println("ERROR AL INTENTAR COMPROBAR ADQUISISION " + e.getMessage());
            return false;

        }

    }

}
