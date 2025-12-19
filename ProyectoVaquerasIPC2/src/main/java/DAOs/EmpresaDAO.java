package DAOs;

import Entidades.*;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpresaDAO extends DAO {

    ConexionDB conexion = new ConexionDB();

    public Empresa registrarEmpresa(Empresa nueva) {

        try (Connection conn = conexion.conectar()) {

            if (buscarPorParametros("empresa", "nombre_empresa", nueva.getNombre_empresa())) {

            } else {

                //Se crea la empresa en la DB
                nueva.setComision(obtenerComisionGlobal());
                String sql = "INSERT INTO empresa (nombre_empresa, descripcion, comision) VALUES (?,?,?)";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, nueva.getNombre_empresa());
                stm.setString(2, nueva.getDescripcion());
                stm.setDouble(3, nueva.getComision());

                stm.executeUpdate();

                return nueva;

            }
        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR EMPRESA " + e.getMessage());
        }

        return null;
    }

    public double obtenerComision(String titulo) {

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT empresa.comision, juego.precio FROM empresa "
                    + "INNER JOIN juego ON empresa.nombre_empresa = juego.nombre_empresa WHERE juego.titulo = ?;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, titulo);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getDouble("empresa.comision");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER PRECIO DEL JUEGO " + e.getMessage());
        }
        return 0;
    }

    private void actualizarComisionGlobal(double comision) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE comision SET porcentaje = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setDouble(1, comision);

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERROR AL ACTUALIZAR PORCENTAJE DE COMISION GLOBAL\n" + e.getMessage());
        }

    }

    public boolean cambiarComisionEspecifica(double comision, String empresa) {

        try (Connection conn = conexion.conectar()) {

            if (buscarPorParametros("empresa", "nombre_empresa", empresa)) {

                String sql = "UPDATE empresa SET comision = ? WHERE nombre_empresa = ?";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setDouble(1, comision);
                stm.setString(2, empresa);

                stm.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL CAMBIAR COMISION ESPECIFICA PARA " + empresa + "\n" + e.getMessage());
        }
        return false;
    }

    public boolean cambiarComisionGlobalParaEmpresas(double comision) {

        double comisionVieja = obtenerComisionGlobal();
        actualizarComisionGlobal(comision);

        try (Connection conn = conexion.conectar()) {

            if (comision > comisionVieja) {

                String sql = "UPDATE empresa SET comision = ? WHERE comision = ?";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setDouble(1, comision);
                stm.setDouble(2, comisionVieja);

                stm.executeUpdate();
                return true;

            } else if (comision < comisionVieja) {

                String sql = "UPDATE empresa SET comision = ? WHERE comision >= ?";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setDouble(1, comision);
                stm.setDouble(2, comision);

                stm.executeUpdate();
                return true;

            }

        } catch (SQLException e) {
            System.out.println("ERROR AL CAMBIAR COMISION GLOBAL \n" + e.getMessage());
        }
        return false;
    }

    public void cambiarCategoriaJuego(String juego, String nueva, String vieja) {

        try (Connection conn = conexion.conectar()) {

            String sql = "UPDATE juego_categoria SET nombre_categoria = ? "
                    + "WHERE titulo = ? AND nombre_categoria = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, nueva);
            stm.setString(2, juego);
            stm.setString(3, vieja);

            stm.executeUpdate();

        } catch (SQLException e) {
            System.out.println("ERROR AL CAMBIAR LA CATEGORIA DE" + juego + "\n" + e.getMessage());
        }

    }

    public boolean cambiarVisibilidadComentariosEmpresa(String nombre_empresa) {

        try (Connection conn = conexion.conectar()) {

            //cambia el estado de visible si es true lo vuelve false y al reves
            String sql = "UPDATE comentario INNER JOIN juego ON comentario.titulo = juego.titulo "
                    + "SET comentario.visible = NOT comentario.visible WHERE juego.nombre_empresa = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, nombre_empresa);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL CAMBIAR VISIBILIDAD DE LOS COMENTARIOS DE LA EMPRESA " + nombre_empresa + e.getMessage());
        }
        return false;
    }

    public boolean editarInformacion(String titulo, String descripcion, double precio) {

        try (Connection conn = conexion.conectar()) {

            //cambia el estado de visible si es true lo vuelve false y al reves
            String sql = " UPDATE juego SET descripcion = ? , precio = ? WHERE titulo = ? ";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, descripcion);
            stm.setDouble(2, precio);
            stm.setString(3, titulo);

            stm.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL EDITAR INFORMACION DEL JUEGO" + titulo + e.getMessage());
        }
        return false;
    }

    public boolean eliminarEmpresario(String correo) {

        try (Connection conn = conexion.conectar()) {

            //elimina al usuario de la tabla "empresario"
            String sql = " DELETE FROM empresario WHERE correo_usuario = ?";

            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, correo);

            //lo elimina tambien de la tabla "usuario"
            String sql2 = " DELETE FROM usuario WHERE correo_usuario = ?";

            PreparedStatement stm2 = conn.prepareStatement(sql2);
            stm2.setString(1, correo);

            stm.executeUpdate();
            stm2.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL ELIMNAR EMPRESARIO " + correo + e.getMessage());
        }
        return false;
    }

    public Empresa exportarEmpresa(String nombre_empresa) {

        Empresa empresa = new Empresa();

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT empresa.nombre_empresa, empresa.descripcion, empresa.comision, juego.titulo "
                    + "FROM empresa INNER JOIN juego ON empresa.nombre_empresa = juego.nombre_empresa "
                    + "WHERE empresa.nombre_empresa = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, nombre_empresa);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                
                String porcentaje = rs.getString("comision");
                double comision = Double.parseDouble(porcentaje);
                
                empresa.setNombre_empresa(nombre_empresa);
                empresa.setDescripcion(rs.getString("descripcion"));
                empresa.getTitulos().add(rs.getString("titulo"));
                empresa.setComision(comision);

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return empresa;

    }

}
