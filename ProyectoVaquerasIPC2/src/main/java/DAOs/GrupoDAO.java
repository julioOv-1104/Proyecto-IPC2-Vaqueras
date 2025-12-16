package DAOs;

import Entidades.GrupoFamiliar;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GrupoDAO extends DAO {

    ConexionDB conexion = new ConexionDB();

    public int obtenerIDgrupo(String correo) {

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT id_grupo FROM grupo_familiar WHERE correo_encargado = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, correo);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_grupo");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER EL ID DEL GRUPO " + e.getMessage());
        }
        return 0;
    }

    public GrupoFamiliar crearGrupoFamiliar(GrupoFamiliar nuevo) {

        try (Connection conn = conexion.conectar()) {

            int idGrupo = obtenerIDgrupo(nuevo.getCorreo_encargado());

            //se asegura que no existan dos grupos con el mismo nombre pertenecientes al mismo usuario
            if (buscarPorParametros("grupo_familiar", "nombre", nuevo.getNombre())) {

            } else {

                //Se crea el grupo en la DB
                String sql = "INSERT INTO grupo_familiar (correo_encargado, nombre) VALUES (?,?)";
                PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1, nuevo.getCorreo_encargado());
                stm.setString(2, nuevo.getNombre());

                stm.executeUpdate();

                return nuevo;

            }
        } catch (SQLException e) {
            System.out.println("ERROR AL REGISTRAR GRUPO Y A SU ENCARGADO " + e.getMessage());
        }

        return null;
    }

    public boolean unirseAgrupo(int idGrupo, String correo) {

        try (Connection conn = conexion.conectar()) {

            //Se agrega al usuario a su grupo
            String sql2 = "INSERT INTO miembro_grupo (correo_miembro, id_grupo) VALUES (?,?)";
            PreparedStatement stm2 = conn.prepareStatement(sql2);
            stm2.setString(1, correo);
            stm2.setInt(2, idGrupo);

            stm2.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL INTENTAR UNIRSE AL GRUPO " + e.getMessage());
        }

        return false;
    }

    public boolean salirDeGrupo(int idGrupo, String correo) {

        try (Connection conn = conexion.conectar()) {

            //Saca al usuario del grupo por el id_grupo
            String sql = "DELETE FROM miembro_grupo WHERE id_grupo = ? AND correo_miembro = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, idGrupo);
            stm.setString(2, correo);

            stm.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL INTENTAR SALIR DEL GRUPO " + e.getMessage());
        }

        return false;
    }

    public boolean borrarYsacarGrupo(int idGrupo) {

        if (sacarMiembroDeGrupo(idGrupo) && borrarGrupo(idGrupo)) {
            return true;
        }

        return false;

    }

    private boolean borrarGrupo(int idGrupo) {

        try (Connection conn = conexion.conectar()) {

            //Se borra el grupo
            String sql = "DELETE FROM grupo_familiar WHERE id_grupo = ?;";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, idGrupo);

            stm.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL INTENTAR BORRAR AL GRUPO" + e.getMessage());
        }

        return false;
    }

    private boolean sacarMiembroDeGrupo(int idGrupo) {

        try (Connection conn = conexion.conectar()) {

            //Se borra a los miembros 
            String sql = "DELETE FROM miembro_grupo WHERE id_grupo = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, idGrupo);

            stm.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("ERROR AL INTENTAR SACAR A SUS MIEMBROS " + e.getMessage());
        }

        return false;

    }

}
