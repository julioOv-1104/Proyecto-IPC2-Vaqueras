package Logica;

import DAOs.*;
import Entidades.*;

public class logicaEmpresa {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();



    public Empresa registrar(Empresa nuevo) {       
            return empresaDAO.registrarEmpresa(nuevo);

    }
}
