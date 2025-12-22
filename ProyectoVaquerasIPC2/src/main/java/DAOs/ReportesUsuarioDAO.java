package DAOs;

import ReportesDTOs.*;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ReportesUsuarioDAO extends DAO {

    ConexionDB conexion = new ConexionDB();

    public ArrayList<HistorialGastos> obtenerHistorialGastos(String correo) {

        try (Connection conn = conexion.conectar()) {

            ArrayList<HistorialGastos> historial = new ArrayList<>();

            String sql = "SELECT fecha_compra,titulo,monto "
                    + "FROM compra "
                    + "WHERE correo_usuario = ? "
                    + "ORDER BY fecha_compra DESC";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, correo);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                HistorialGastos gastos = new HistorialGastos();

                gastos.setTitulo(rs.getString("titulo"));
                gastos.setFecha_compra(rs.getDate("fecha_compra"));
                gastos.setMonto(rs.getDouble("monto"));

                historial.add(gastos);
            }
            return historial;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public ArrayList<JuegosConValoracion> obtenerJuegosConValoracion(String correo) {

        try (Connection conn = conexion.conectar()) {

            ArrayList<JuegosConValoracion> juegos = new ArrayList<>();

            String sql = "SELECT \n"
                    + "    c.titulo,\n"
                    + "    (SELECT IFNULL(AVG(estrellas), 0) \n"
                    + "     FROM calificacion \n"
                    + "     WHERE titulo = c.titulo) AS promedio_comunidad,\n"
                    + "    IFNULL(cal.estrellas, 0) AS mi_valoracion\n"
                    + "FROM compra c\n"
                    + "LEFT JOIN calificacion cal ON c.titulo = cal.titulo \n"
                    + "    AND c.correo_usuario = cal.correo_usuario\n"
                    + "WHERE c.correo_usuario = ?\n"
                    + "ORDER BY promedio_comunidad DESC";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, correo);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                JuegosConValoracion valoracion = new JuegosConValoracion();

                valoracion.setTitulo(rs.getString("titulo"));
                valoracion.setMi_valoracion(rs.getDouble("mi_valoracion"));
                valoracion.setPromedio_comunidad(rs.getDouble("promedio_comunidad"));

                juegos.add(valoracion);
            }
            return juegos;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public ArrayList<CategoriasFavoritas> obtenerCategoriasFavoritas(String correo) {

        try (Connection conn = conexion.conectar()) {

            ArrayList<CategoriasFavoritas> categorias = new ArrayList<>();

            String sql = "SELECT \n"
                    + "    jc.nombre_categoria, \n"
                    + "    COUNT(c.titulo) AS cantidad_comprada\n"
                    + "FROM compra c\n"
                    + "INNER JOIN juego_categoria jc ON c.titulo = jc.titulo\n"
                    + "WHERE c.correo_usuario = ?\n"
                    + "GROUP BY jc.nombre_categoria\n"
                    + "ORDER BY cantidad_comprada DESC;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, correo);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                CategoriasFavoritas favoritas = new CategoriasFavoritas();

                favoritas.setNombre_categoria(rs.getString("nombre_categoria"));
                favoritas.setCantidad_comprada(rs.getInt("cantidad_comprada"));

                categorias.add(favoritas);
            }
            return categorias;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
