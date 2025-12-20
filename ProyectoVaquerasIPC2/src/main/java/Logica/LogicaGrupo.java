
package Logica;

import DAOs.GrupoDAO;
import Entidades.GrupoFamiliar;

public class LogicaGrupo {
    
    GrupoDAO grupoDao = new GrupoDAO();
    
    public GrupoFamiliar crearGrupo(GrupoFamiliar nuevo){
    
        GrupoFamiliar creado = grupoDao.crearGrupoFamiliar(nuevo);
       int id = grupoDao.obtenerIDgrupo(nuevo.getCorreo_encargado(), nuevo.getNombre());//crea el grupo
        grupoDao.unirseAgrupo(id, nuevo.getCorreo_encargado());//lo agrega al grupo
        return creado;
    }
    
    public boolean unirseAgrupo(int id, String correo){
    
        return grupoDao.unirseAgrupo(id, correo);
    
    }
    
    public boolean SalirDeGrupo(int id, String correo){
    
        return grupoDao.salirDeGrupo(id, correo);
    
    }
    
    public boolean borrarGrupo(int id){
    
        return grupoDao.borrarYsacarGrupo(id);
    
    }
    
    public GrupoFamiliar obtenerGrupo(int id){
    return grupoDao.exportarGrupo(id);
    
    }
    
}
