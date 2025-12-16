
package Logica;

import DAOs.OpinionDAO;

public class LogicaOpinion {
    
    private OpinionDAO opinionDao = new OpinionDAO();
    
    public boolean comentar(String correo, String titulo, String texto){
    return opinionDao.comentar(correo, titulo, texto);
    
    }
    
    public boolean responder(String correo, String texto, int id_padre){
    return opinionDao.responder(correo, texto, id_padre);
    
    }
    
    public boolean calificar(String correo, String titulo, double estrellas){
    return opinionDao.calificar(correo, titulo, estrellas);
    
    }
}
