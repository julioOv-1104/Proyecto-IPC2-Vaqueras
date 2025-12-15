
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
}
