
package Logica;

import DAOs.JuegoDAO;
import Entidades.Categoria;
import Entidades.Juego;
import java.io.InputStream;
import java.util.ArrayList;

public class LogicaJuego {
    
    private JuegoDAO juegoDao = new JuegoDAO();
    
    public Juego registrarJuegoNuevo(Juego nuevo, InputStream is){
    
        return juegoDao.registrarJuego(nuevo,is);
        
    }
    
    public Juego obtenerJuego(String titulo){
    
        return juegoDao.exportarJuego(titulo);
        
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
    
    public ArrayList<Categoria> obtenerCategorias(){
    return juegoDao.exportarCategorias();
    }
    
    public ArrayList<Juego> obtenerJuegos(){
    return juegoDao.exportarTodosLosJuegos();
    }
}
