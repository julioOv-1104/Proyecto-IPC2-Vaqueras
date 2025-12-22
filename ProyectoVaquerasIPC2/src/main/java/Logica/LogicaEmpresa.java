package Logica;

import DAOs.*;
import Entidades.*;

public class LogicaEmpresa {

    private  EmpresaDAO empresaDAO = new EmpresaDAO();



    public Empresa registrar(Empresa nuevo) {       
            return empresaDAO.registrarEmpresa(nuevo);
    }
    
    public boolean cambiarComisionAEspecifica(double nuevaComision, String empresa){
    
        if (nuevaComision > empresaDAO.obtenerComisionGlobal()) {
            return false;
            
            
        }else{
        return empresaDAO.cambiarComisionEspecifica(nuevaComision, empresa);
        }
    }
    
    public boolean actualizarComision(double nuevaComision){
    
        if (nuevaComision >0) {
            return empresaDAO.cambiarComisionGlobalParaEmpresas(nuevaComision);
        }else{
        return false;
        }
        
    }
    
    public void cambiarCategoriaJuego(String juego, String nueva, String vieja){
    
        empresaDAO.cambiarCategoriaJuego(juego, nueva, vieja);
        
    }
    
    public boolean desactivarActivarComenatrio(String empresa){
    
        return empresaDAO.cambiarVisibilidadComentariosEmpresa(empresa);
        
    }
    
    public Empresa obtenerEmpresa(String empresa){
    
        return empresaDAO.exportarEmpresa(empresa);
        
    }
    
    public boolean editarInformacion(String titulo, String descripcion, double precio){
    
        return empresaDAO.editarInformacion(titulo, descripcion, precio);
        
    }
    
     public boolean eliminarEmpresario(String correo){
    
        return empresaDAO.eliminarEmpresario(correo);
        
    }
    
}
