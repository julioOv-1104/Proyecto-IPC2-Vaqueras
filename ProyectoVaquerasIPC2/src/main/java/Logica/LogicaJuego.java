
package Logica;

import DAOs.JuegoDAO;
import Entidades.Juego;

public class LogicaJuego {
    
    private JuegoDAO juegoDao = new JuegoDAO();
    
    public Juego registrarJuegoNuevo(Juego nuevo){
    
        return juegoDao.registrarJuego(nuevo);
        
    }
    
    public boolean agregarCategoria(String titulo, String categoria){
    
        return juegoDao.agregarCategoriasJuego(titulo, categoria);
        
    }
    
    public boolean agregarRecursos(String titulo,String[] valor){
    
        return juegoDao.agregarRecursosMinimos(titulo, valor);
        
    }
    
    public boolean desactivarActivarVenta(String titulo){
    
        return juegoDao.cambiarVisibilidadJuego(titulo);
        
    }
    
    public boolean desactivarActivarComenatrio(String titulo){
    
        return juegoDao.cambiarVisibilidadComentario(titulo);
        
    }
}
