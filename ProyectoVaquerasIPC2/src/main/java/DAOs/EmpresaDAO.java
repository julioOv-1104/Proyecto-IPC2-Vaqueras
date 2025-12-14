
package DAOs;

import Entidades.*;
import Utilidades.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class EmpresaDAO extends DAO{
    ConexionDB conexion = new ConexionDB();
    
    
    public Empresa registrarEmpresa(Empresa nueva) {

        try (Connection conn = conexion.conectar()) {

            if (buscarPorParametros("empresa","nombre_empresa",nueva.getNombre_empresa())) {
               
                
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
}
