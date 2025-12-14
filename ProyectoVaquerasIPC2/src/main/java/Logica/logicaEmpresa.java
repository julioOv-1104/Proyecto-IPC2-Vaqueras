package Logica;

import DAOs.*;
import Entidades.*;

public class logicaEmpresa {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();



    public Empresa registrar(Empresa nuevo) {       
            return empresaDAO.registrarEmpresa(nuevo);
    }
    
    public boolean cambiarComisionAEspecifica(double nuevaComision, String empresa){
    
        if (nuevaComision > empresaDAO.obtenerComisionGlobal()) {
            return false;
            //cambia la comision a una especifica
            
        }else{
        return empresaDAO.cambiarComisionEspecifica(nuevaComision, empresa);
        }
    }
}
