package DAOs;

import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InstalacionDAO extends DAO {

    ConexionDB conexion = new ConexionDB();

    public boolean instalarDesinstalarJuegoPropio(String titulo, String correo) {

        try (Connection conn = conexion.conectar()) {

            //cambia el estado de "estado" si es true lo vuelve false y al reves
            String sql = "UPDATE compra SET estado = NOT estado WHERE titulo = ? AND correo_usuario = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, titulo);
            stm.setString(2, correo);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL INSTALAR/DESINSTALAR EL JUEGO " + titulo + e.getMessage());
        }
        return false;
    }

    private boolean comprobarPosesionDeJuego(String titulo, String correo) {//verifica si el usuario tiene comprado el juego

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT 1 FROM compra WHERE titulo = ? AND correo_usuario = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, titulo);
            stm.setString(2, correo);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                System.out.println("El usuario es propietario de este juego: " + titulo);
                return true;
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL BUSCAR POSESION DE " + titulo + e.getMessage());
        }

        return false;

    }

    //verifica si el usuario ya tiene instalado un juego prestado
    private boolean comprobarInstalacionPrestado(String correo) {

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT 1 FROM instalacion_prestamo WHERE estado = true AND correo_usuario = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, correo);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                System.out.println("El usuario ya tiene istalado un juego prestado");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL BUSCAR SI EL USUARIO YA INSTALO UN JUEGO PRESTADP " + e.getMessage());
        }

        return false;

    }

    public boolean instalarJuegosPrestados(String titulo, String correo, Date fecha_instalacion) {

        try (Connection conn = conexion.conectar()) {

            if (comprobarPosesionDeJuego(titulo, correo)) {
                instalarDesinstalarJuegoPropio(titulo, correo);//si si compro el juego lo instala normalmente
                //si no es porque el juego es prestado
                return true;

            } else if (comprobarInstalacionPrestado(correo)) {
                return false;
            } else {

                //cambia el estado de "estado" si es true lo vuelve false y al reves
                String sql = "INSERT INTO instalacion_prestamo (correo_usuario, titulo, fecha_instalacion) "
                        + "VALUES (?,?,?)";

                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, correo);
                stm.setString(2, titulo);
                stm.setDate(3, fecha_instalacion);

                stm.executeUpdate();

                return true;
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL INSTALAR EL JUEGO PRESTADO " + titulo + e.getMessage());
        }
        return false;
    }
    
    public boolean desinstalarJuegoPrestado(String titulo, String correo) {

        try (Connection conn = conexion.conectar()) {

            if (comprobarPosesionDeJuego(titulo, correo)) {
                instalarDesinstalarJuegoPropio(titulo, correo);//si si compro el juego lo desinstala normalmente
                //si no es porque el juego es prestado
                return true;

            } else {

                //cambia el estado de "estado" si es true lo vuelve false y al reves
                String sql = "UPDATE instalacion_prestamo SET estado = NOT estado "
                        + "WHERE estado = true AND correo_usuario = ? AND titulo = ?";

                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, correo);
                stm.setString(2, titulo);

                stm.executeUpdate();

                return true;
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL DESINSTALAR EL JUEGO PRESTADO " + titulo + e.getMessage());
        }
        return false;
    }

}
