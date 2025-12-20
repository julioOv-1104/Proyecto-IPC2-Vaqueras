package DAOs;

import Entidades.*;
import java.sql.Connection;
import java.sql.Date;
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

            //Solo puede calificar si compro el juego y si tiene como maximo 5 estrellas
            if (comprobarSiUsuarioTienenJuego(titulo, correo) && (estrellas <= 5)) {

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
    
    public Comentario exportarComentario(int id){
    

        Comentario comentario = new Comentario();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM comentario WHERE id_comentario = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, id);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                
                String id2 = rs.getString("id_comentario_padre");
                int id_padre = 0;
                if (id2!=null) {
                    id_padre = Integer.parseInt(id2);
                }
                
                
                comentario.setCorreo_usuario(rs.getString("correo_usuario"));
                comentario.setTitulo(rs.getString("titulo"));
                comentario.setId_comentario(id);
                comentario.setId_comentario_padre(id_padre);
                comentario.setTexto_comentario(rs.getString("texto_comentario"));
                
                return comentario;   
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }
    
    public Calificacion exportarCalificacion(String correo_usuario, String titulo){
    

        Calificacion calificacion = new Calificacion();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM calificacion WHERE correo_usuario = ? AND titulo = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, correo_usuario);
            stm.setString(2, titulo);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                
                String puntaje = rs.getString("estrellas");
                double estrellas = Double.parseDouble(puntaje);
                
                calificacion.setCorreo_usuario(rs.getString("correo_usuario"));
                calificacion.setTitulo(titulo);
                calificacion.setEstrellas(estrellas);
                
                return calificacion;   
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }

}
