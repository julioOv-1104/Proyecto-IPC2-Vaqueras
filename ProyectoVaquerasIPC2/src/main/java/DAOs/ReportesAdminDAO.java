package DAOs;

import ReportesDTOs.*;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ReportesAdminDAO extends DAO {

    ConexionDB conexion = new ConexionDB();

    public GananciasGlobales obtenerGananciasGlobales() {

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT \n"
                    + "    SUM(monto) AS total_ingresado,\n"
                    + "    SUM(monto * porcentaje/100) AS comision_plataforma,\n"
                    + "    SUM(monto * (1 - porcentaje/100)) AS ganancia_total_empresas\n"
                    + "FROM compra";
            PreparedStatement stm = conn.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {

                GananciasGlobales ganancias = new GananciasGlobales();

                ganancias.setComision_plataforma(rs.getDouble("comision_plataforma"));
                ganancias.setGanancia_total_empresas(rs.getDouble("ganancia_total_empresas"));
                ganancias.setTotal_ingresado(rs.getDouble("total_ingresado"));
                return ganancias;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public ArrayList<VentasYcalidad> obtenerVentasYcalidad(String categoria, String clasificacion) {

        try (Connection conn = conexion.conectar()) {

            ArrayList<VentasYcalidad> ventas = new ArrayList<>();

            String sql = "SELECT \n"
                    + "    j.titulo, \n"
                    + "    j.clasificacion,\n"
                    + "    (SELECT COUNT(*) FROM compra c WHERE c.titulo = j.titulo) AS total_ventas,\n"
                    + "    (SELECT IFNULL(AVG(estrellas), 0) FROM calificacion cal WHERE cal.titulo = j.titulo) AS promedio_calificacion\n"
                    + "FROM juego j\n"
                    + "WHERE (? IS NULL OR j.clasificacion = ?)\n"
                    + "  AND (? IS NULL OR EXISTS (\n"
                    + "      SELECT 1 FROM juego_categoria jc \n"
                    + "      WHERE jc.titulo = j.titulo AND jc.nombre_categoria = ?\n"
                    + "  ))\n"
                    + "ORDER BY total_ventas DESC;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, clasificacion);
            stm.setString(2, clasificacion);
            stm.setString(3, categoria);
            stm.setString(4, categoria);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                VentasYcalidad venta = new VentasYcalidad();

                venta.setTitulo(rs.getString("titulo"));
                venta.setTotal_ventas(rs.getInt("total_ventas"));
                venta.setClasificacion(rs.getString("clasificacion"));
                venta.setPromedio_calificacion(rs.getDouble("promedio_calificacion"));

                ventas.add(venta);
            }
            return ventas;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public ArrayList<IngresosPorEmpresa> obtenerIngresosPorEmpresa() {

        try (Connection conn = conexion.conectar()) {

            ArrayList<IngresosPorEmpresa> ingresos = new ArrayList<>();

            String sql = "SELECT \n"
                    + "    e.nombre_empresa, \n"
                    + "    IFNULL(SUM(c.monto), 0) AS total_ventas,\n"
                    + "    IFNULL(SUM(c.monto * c.porcentaje/100), 0) AS comision_retenida,\n"
                    + "    IFNULL(SUM(c.monto * (1 - c.porcentaje/100)), 0) AS monto_neto\n"
                    + "FROM empresa e\n"
                    + "LEFT JOIN juego j ON e.nombre_empresa = j.nombre_empresa\n"
                    + "LEFT JOIN compra c ON j.titulo = c.titulo\n"
                    + "GROUP BY e.nombre_empresa\n"
                    + "ORDER BY total_ventas DESC";
            PreparedStatement stm = conn.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                IngresosPorEmpresa ingreso = new IngresosPorEmpresa();

                ingreso.setComision_retenida(rs.getDouble("comision_retenida"));
                ingreso.setMonto_neto(rs.getDouble("monto_neto"));
                ingreso.setNombre_empresa(rs.getString("nombre_empresa"));
                ingreso.setTotal_ventas(rs.getDouble("total_ventas"));
                ingresos.add(ingreso);
            }
            return ingresos;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public ArrayList<RankingUusarios> obtenerRankingUsuarios() {

        try (Connection conn = conexion.conectar()) {

            ArrayList<RankingUusarios> usuarios = new ArrayList<>();

            String sql = "SELECT \n"
                    + "    u.correo_usuario,\n"
                    + "    (SELECT COUNT(*) FROM compra c WHERE c.correo_usuario = u.correo_usuario) AS total_juegos,\n"
                    + "    (SELECT COUNT(*) FROM calificacion cal WHERE cal.correo_usuario = u.correo_usuario) AS total_resennas\n"
                    + "FROM usuario u\n"
                    + "ORDER BY total_juegos DESC, total_resennas DESC";
            PreparedStatement stm = conn.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                RankingUusarios usuario = new RankingUusarios();

                usuario.setCorreo_usuario(rs.getString("correo_usuario"));
                usuario.setTotal_juegos(rs.getInt("total_juegos"));
                usuario.setTotal_resennas(rs.getInt("total_resennas"));
                usuarios.add(usuario);
            }
            return usuarios;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
