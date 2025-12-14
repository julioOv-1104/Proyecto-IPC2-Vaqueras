package DAOs;

import Entidades.Usuario;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO extends DAO {

    ConexionDB conexion = new ConexionDB();

    public Usuario login(String correo_usuario, String contrasenna) {

        String correo = correo_usuario.trim();//le quita los espacios sobrantes
        String contra = contrasenna.trim();

        Usuario user = null;

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT correo_usuario, nickname, pais, fecha_nacimiento FROM usuario WHERE correo_usuario=? AND contrasenna=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, correo);
            stm.setString(2, contra);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                user = new Usuario(rs.getString("correo_usuario"), rs.getString("nickname"), rs.getString("pais"), rs.getDate("fecha_nacimiento"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public Usuario registrarUsuario(Usuario nuevo) {

        try (Connection conn = conexion.conectar()) {

            if (buscarPorParametros("usuario", "correo_usuario", nuevo.getCorreo_usuario())
                    || buscarPorParametros("usuario", "nickname", nuevo.getNickname())) {

            } else {

                //Se crea el usuario en la DB
                String sql = "INSERT INTO usuario (correo_usuario, contrasenna, nickname, fecha_nacimiento, telefono, pais)"
                        + " VALUES (?,?,?,?,?,?)";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, nuevo.getCorreo_usuario());
                stm.setString(2, nuevo.getContrasenna());
                stm.setString(3, nuevo.getNickname());
                stm.setDate(4, new java.sql.Date(nuevo.getFecha_nacimiento().getTime()));
                stm.setString(5, nuevo.getTelefono());
                stm.setString(6, nuevo.getPais());

                //Se agrega al usuario a la tabla de ususrio_comun
                String sql2 = "INSERT INTO usuario_comun (correo_usuario) VALUES (?)";
                PreparedStatement stm2 = conn.prepareStatement(sql2);
                stm2.setString(1, nuevo.getCorreo_usuario());

                stm.executeUpdate();
                stm2.executeUpdate();

                return nuevo;

            }
        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR USUARIO COMUN " + e.getMessage());
        }

        return null;
    }

    public Usuario registrarAdmin(Usuario nuevo) {

        try (Connection conn = conexion.conectar()) {

            if (buscarPorParametros("usuario", "correo_usuario", nuevo.getCorreo_usuario())) {

            } else {

                //Se crea el usuario en la DB
                String sql = "INSERT INTO usuario (correo_usuario, contrasenna) VALUES (?,?)";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, nuevo.getCorreo_usuario());
                stm.setString(2, nuevo.getContrasenna());

                //Se agrega al usuario a la tabla de ususrio_comun
                String sql2 = "INSERT INTO admin (correo_usuario) VALUES (?)";
                PreparedStatement stm2 = conn.prepareStatement(sql2);
                stm2.setString(1, nuevo.getCorreo_usuario());

                stm.executeUpdate();
                stm2.executeUpdate();

                return nuevo;

            }
        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR ADMIN " + e.getMessage());
        }

        return null;
    }

    public Usuario registrarEmpresario(Usuario nuevo) {

        try (Connection conn = conexion.conectar()) {

            //Revisa que el correo no este en uso
            if (buscarPorParametros("usuario", "correo_usuario", nuevo.getCorreo_usuario())) {
            } else {

                //Revisa que la empresa exista
                if (buscarPorParametros("empresa", "nombre_empresa", nuevo.getNombre_empresa())) {
                    
                    //Se crea el usuario en la DB
                    String sql = "INSERT INTO usuario (correo_usuario, fecha_nacimiento, contrasenna)"
                            + " VALUES (?,?,?)";
                    PreparedStatement stm = conn.prepareStatement(sql);
                    stm.setString(1, nuevo.getCorreo_usuario());
                    stm.setDate(2, new java.sql.Date(nuevo.getFecha_nacimiento().getTime()));
                    stm.setString(3, nuevo.getContrasenna());

                    //Se agrega al usuario a la tabla de ususrio_comun
                    String sql2 = "INSERT INTO empresario (correo_usuario, nombre, nombre_empresa) VALUES (?,?,?)";
                    PreparedStatement stm2 = conn.prepareStatement(sql2);
                    stm2.setString(1, nuevo.getCorreo_usuario());
                    stm2.setString(2, nuevo.getNombre());
                    stm2.setString(3, nuevo.getNombre_empresa());

                    stm.executeUpdate();
                    stm2.executeUpdate();

                    return nuevo;
                }else{
                    System.out.println("LA EMPRESA NO EXISTE");
                }

            }
        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR EMPRESARIO " + e.getMessage());
        }

        return null;
    }

}
