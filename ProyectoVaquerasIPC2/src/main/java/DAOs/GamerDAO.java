package DAOs;

import Entidades.*;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.sql.Date;

public class GamerDAO extends DAO {

    ConexionDB conexion = new ConexionDB();

    public double obtenerCartera(String correo) {

        double cartera = 0;

        try (Connection conn = conexion.conectar()) {

            //Se busca cuanto dinero tiene el usuario en su cartera
            String sql = "SELECT cartera FROM usuario_comun WHERE correo_usuario = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, correo);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                cartera = rs.getDouble("cartera");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER LA CARTERA DEL USUARIO " + e.getMessage());
        }

        return cartera;
    }

    public boolean recargarCartera(String correo, double recarga) {

        try (Connection conn = conexion.conectar()) {

            double dineroActual = obtenerCartera(correo);

            recarga += dineroActual;

            String sql = "UPDATE usuario_comun SET cartera = ? WHERE correo_usuario = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setDouble(1, recarga);
            stm.setString(2, correo);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL RECARGAR CARTERA " + e.getMessage());
        }

        return false;
    }

    private Date obtenerFechaNacimiento(String correo) {

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT fecha_nacimiento FROM usuario WHERE correo_usuario = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, correo);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getDate("fecha_nacimiento");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL RECARGAR CARTERA " + e.getMessage());
        }
        return null;
    }

    private String obtenerClasificacionJuego(String titulo) {

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT clasificacion FROM juego WHERE titulo = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, titulo);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getString("clasificacion");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER CLASIFICACION DEL JUEGO " + e.getMessage());
        }
        return null;
    }
    
    private double obtenerPrecioJuego(String titulo) {

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT precio FROM juego WHERE titulo = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, titulo);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getDouble("precio");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER PRECIO DEL JUEGO " + e.getMessage());
        }
        return 0;
    }

    private boolean comprobarEdad(String correo, String titulo, Date fecha_compra) {

        Date fechaN = obtenerFechaNacimiento(correo);
        int edad = Period.between(fechaN.toLocalDate(), fecha_compra.toLocalDate()).getYears();

        String clasificacion = obtenerClasificacionJuego(titulo);

        if (clasificacion.equals("M") && (edad >= 17)) {//si tien 17 o mas puede comprar el juego M
            return true;

        } else if (clasificacion.equals("T") && (edad >= 12)) {//si tiene 12 o mas puede comprar el jugo T
            return true;

        } else if (clasificacion.equals("E")) {//cualquiera puede comprar estos juegos
            return true;

        } else {
            System.out.println("NO tiene edad para este juego");
            return false;
        }

    }

    public boolean comprarJuego(String correo, String titulo, Date fecha_compra) {
        
        EmpresaDAO empreDao = new EmpresaDAO();

        try (Connection conn = conexion.conectar()) {
            
            double cartera = obtenerCartera(correo);
            double precioJuego = obtenerPrecioJuego(titulo);
            double porcentaje = empreDao.obtenerComision(titulo);
            

            //si tiene la edad y dinero suficientes puede comprar el juego
            if (comprobarEdad(correo, titulo, fecha_compra) && (cartera >=precioJuego)) {

                String sql = "INSERT INTO compra (correo_usuario, titulo, fecha_compra, monto, porcentaje) VALUES (?,?,?,?,?)";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, correo);
                stm.setString(2, titulo);
                stm.setDate(3, fecha_compra);
                stm.setDouble(4, precioJuego);
                stm.setDouble(5, porcentaje);
                

                stm.executeUpdate();
                
                recargarCartera(correo, (precioJuego*-1));

                return true;

            }

        } catch (SQLException e) {
            System.out.println("ERROR AL COMPRAR JUEGO " + e.getMessage());
        }

        return false;
    }
    
    
    public Compra exportarCompra(String correo_usuario, String titulo){
    

        Compra compra = new Compra();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT * FROM compra WHERE correo_usuario = ? AND titulo = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, correo_usuario);
            stm.setString(2, titulo);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                
                String dinero = rs.getString("monto");
                double monto = Double.parseDouble(dinero);
                String fecha = rs.getString("fecha_compra");
                Date fecha_compra = Date.valueOf(fecha);
                
                compra.setCorreo_usuario(rs.getString("correo_usuario"));
                compra.setTitulo(rs.getString("titulo"));
                compra.setMonto(monto);
                compra.setFecha_compra(fecha_compra);
                
                return compra;
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }

}
