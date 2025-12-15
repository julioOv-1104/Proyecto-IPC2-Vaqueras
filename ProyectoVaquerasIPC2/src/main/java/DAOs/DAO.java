package DAOs;

import Entidades.Empresa;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {

    ConexionDB conexion = new ConexionDB();

    public boolean buscarPorParametros(String tabla, String columna, String dato) {

        try (Connection conn = conexion.conectar()) {

            String sql = "SELECT 1 FROM " + tabla + " WHERE " + columna + "=?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, dato);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                System.out.println(columna + ":" + dato + " existente");
                return true;//Devuelve true porque el si existe
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR AL BUSCAR " + tabla);
        }
        return false;
    }

    public double obtenerComisionGlobal() {

        double comision = 0;
        
        try (Connection conn = conexion.conectar()) {

            //Se busca cual es la comision global vigente en la DB
            String sql = "SELECT porcentaje FROM comision";
            PreparedStatement stm = conn.prepareStatement(sql);

            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
                comision = rs.getDouble("porcentaje");
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER LA COMISION GLOBAL " + e.getMessage());
        }

        return comision;
    }
    
    

}
