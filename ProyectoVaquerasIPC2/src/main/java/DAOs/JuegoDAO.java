package DAOs;

import Entidades.*;
import Utilidades.ConexionDB;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JuegoDAO extends DAO {

    ConexionDB conexion = new ConexionDB();

    public Juego registrarJuego(Juego nuevo, InputStream is) {

        try (Connection conn = conexion.conectar()) {

            if (buscarPorParametros("juego", "titulo", nuevo.getTitulo())) {

            } else if (buscarPorParametros("empresa", "nombre_empresa", nuevo.getNombre_empresa())) {

                //Se crea el juego en la DB
                String sql = "INSERT INTO juego (titulo, descripcion, precio, clasificacion, fecha_lanzamiento, nombre_empresa, multimedia) \n"
                        + "VALUES (?,?,?,?,?,?,?)";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, nuevo.getTitulo());
                stm.setString(2, nuevo.getDescripcion());
                stm.setDouble(3, nuevo.getPrecio());
                stm.setString(4, nuevo.getClasificacion().name());
                stm.setDate(5, new java.sql.Date(nuevo.getFecha_lanzamiento().getTime()));
                stm.setString(6, nuevo.getNombre_empresa());
                stm.setBlob(7, is);

                stm.executeUpdate();

                return nuevo;
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR UN NUEVO JUEGO " + e.getMessage());
        }

        return null;
    }

    private boolean buscarCategoriaDuplicada(String titulo, String categoria) {

        try (Connection conn = conexion.conectar()) {

            //Se asegura que no existan dos filas con la misma informacion
            String sql = "SELECT 1 FROM juego_categoria WHERE titulo = ? AND nombre_categoria = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, titulo);
            stm.setString(2, categoria);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                System.out.println("Ya está registrado este juego con esta categoria");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL CONSULTAR JUEGO Y CATEGORIA " + titulo + e.getMessage());
        }

        return false;
    }

    public boolean agregarCategoriasJuego(String titulo, String categoria) {

        try (Connection conn = conexion.conectar()) {

            if (!buscarCategoriaDuplicada(titulo, categoria)) {

                //Se agrega la categoria
                String sql = "INSERT INTO juego_categoria (titulo, nombre_categoria) VALUES (?,?)";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, titulo);
                stm.setString(2, categoria);

                stm.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL AGREGAR UNA CATEGORIA AL JUEGO " + titulo + e.getMessage());
        }
        return false;
    }

    public boolean agregarRecursosMinimos(String titulo, String[] valor) {

        try (Connection conn = conexion.conectar()) {

            //Se agrega el recurso
            String sql = "INSERT INTO juego_recurso (titulo, nombre_recurso, valor) VALUES (?,'almacenamiento',?), "
                    + "(?,'memoria RAM',?), (?,'procesador',?), (?,'sistema operativo',?)";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, titulo);
            stm.setString(2, valor[0]);

            stm.setString(3, titulo);
            stm.setString(4, valor[1]);

            stm.setString(5, titulo);
            stm.setString(6, valor[2]);

            stm.setString(7, titulo);
            stm.setString(8, valor[3]);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL AGREGAR RECURSOS AL JUEGO " + titulo + e.getMessage());
        }
        return false;
    }

     public boolean cambiarVisibilidadJuego(String titulo) {

        try (Connection conn = conexion.conectar()) {

            //cambia el estado de habilitado si es true lo vuelve false y al reves
            String sql = "UPDATE juego SET habilitado = NOT habilitado WHERE titulo = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, titulo);


            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL CAMBIAR VISIBILIDAD DEL JUEGO " + titulo + e.getMessage());
        }
        return false;
    }
    
     
     public boolean cambiarVisibilidadComentario(String titulo) {

        try (Connection conn = conexion.conectar()) {

            //cambia el estado de habilitado si es true lo vuelve false y al reves
            String sql = "UPDATE comentario SET visible = NOT visible WHERE titulo = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, titulo);


            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL CAMBIAR VISIBILIDAD DEL COMENTARIO DEL JUEGO " + titulo + e.getMessage());
        }
        return false;
    }
}
