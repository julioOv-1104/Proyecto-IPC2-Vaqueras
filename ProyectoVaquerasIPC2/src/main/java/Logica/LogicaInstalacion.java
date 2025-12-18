
package Logica;

import DAOs.InstalacionDAO;
import java.sql.Date;

public class LogicaInstalacion {
    
    InstalacionDAO instaDao = new InstalacionDAO();
    
    public boolean instalarDesinstalarPropios(String titulo, String correo){
    
        return instaDao.instalarDesinstalarJuegoPropio(titulo, correo);
        
    }
    
    public boolean instalarJuegoPrestado(String titulo, String correo, Date fecha_instalacion){
    
        return instaDao.instalarJuegosPrestados(titulo, correo, fecha_instalacion);
        
    }
    
    public boolean desinstalarJuegoPrestado(String titulo, String correo){
    
        return instaDao.desinstalarJuegoPrestado(titulo, correo);
        
    }
    
}
