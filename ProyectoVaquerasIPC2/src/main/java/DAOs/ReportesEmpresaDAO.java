package DAOs;

import ReportesDTOs.*;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ReportesEmpresaDAO extends DAO {

    ConexionDB conexion = new ConexionDB();

    public ArrayList<VentasPropias> obtenerVentasPropias(String empresa) {

        try (Connection conn = conexion.conectar()) {

            ArrayList<VentasPropias> ventas = new ArrayList<>();

            String sql = "SELECT \n"
                    + "    j.titulo, \n"
                    + "    SUM(c.monto) AS monto_bruto,\n"
                    + "    SUM(c.monto * c.porcentaje/100) AS comision_plataforma,\n"
                    + "    SUM(c.monto * (1 - c.porcentaje/100)) AS ingreso_neto\n"
                    + "FROM juego j\n"
                    + "INNER JOIN compra c ON j.titulo = c.titulo\n"
                    + "WHERE j.nombre_empresa = ?\n"
                    + "GROUP BY j.titulo\n"
                    + "ORDER BY monto_bruto DESC";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, empresa);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                VentasPropias venta = new VentasPropias();

                venta.setTitulo(rs.getString("titulo"));
                venta.setMonto_bruto(rs.getDouble("monto_bruto"));
                venta.setIngreso_neto(rs.getDouble("ingreso_neto"));
                venta.setComision_plataforma(rs.getDouble("comision_plataforma"));

                ventas.add(venta);
            }
            return ventas;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public ArrayList<CalificacionPromedio> obtenerCalificacionPromedio() {

        try (Connection conn = conexion.conectar()) {

            ArrayList<CalificacionPromedio> calificaciones = new ArrayList<>();

            String sql = "SELECT titulo, IFNULL(AVG(estrellas), 0) AS promedio_estrellas \n"
                    + "FROM calificacion \n"
                    + "GROUP BY titulo \n"
                    + "ORDER BY promedio_estrellas DESC";
            PreparedStatement stm = conn.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                CalificacionPromedio calificacion = new CalificacionPromedio();

                calificacion.setTitulo(rs.getString("titulo"));
                calificacion.setPromedio_estrellas(rs.getDouble("promedio_estrellas"));

                calificaciones.add(calificacion);
            }
            return calificaciones;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public ArrayList<Top5Juegos> obtenerTopJuegos(String nombre_empresa, Date fecha1, Date fecha2) {

        try (Connection conn = conexion.conectar()) {

            ArrayList<Top5Juegos> top = new ArrayList<>();

            String sql = "SELECT \n"
                    + "    j.titulo, \n"
                    + "    COUNT(c.titulo) AS total_ventas, \n"
                    + "    SUM(c.monto) AS ingresos_brutos \n"
                    + "FROM juego j\n"
                    + "INNER JOIN compra c ON j.titulo = c.titulo\n"
                    + "WHERE j.nombre_empresa = ? \n"
                    + "  AND (? IS NULL OR c.fecha_compra >= ?) \n"
                    + "  AND (? IS NULL OR c.fecha_compra <= ?)\n"
                    + "GROUP BY j.titulo\n"
                    + "ORDER BY total_ventas DESC\n"
                    + "LIMIT 5";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, nombre_empresa);
            
            stm.setDate(2, fecha1);
            stm.setDate(3, fecha1);
            
            stm.setDate(4, fecha2);
            stm.setDate(5, fecha2);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Top5Juegos juegos = new Top5Juegos();

                juegos.setTitulo(rs.getString("titulo"));
                juegos.setTotal_ventas(rs.getInt("total_ventas"));
                juegos.setIngresos_brutos(rs.getDouble("ingresos_brutos"));

                top.add(juegos);
            }
            return top;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
