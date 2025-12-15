package Controladores;

import Entidades.*;
import Logica.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistroEmpresaServlet", urlPatterns = {"/RegistroEmpresaServlet"})
public class RegistroEmpresaServlet extends HttpServlet {
    
    private LogicaEmpresa logicaE = new LogicaEmpresa();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper om = new ObjectMapper();
        
        response.setContentType("application/json; charset=UTF-8");
        
        Empresa recibido = om.readValue(request.getInputStream(), Empresa.class);
        
        
        Empresa nueva = logicaE.registrar(recibido);
        
        if (nueva == null) {
            
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al intentar registrar empresa\"}");
        } else {
            String json = om.writeValueAsString(nueva);
            response.getWriter().print(json);
        }
    }
    
}
