package Controladores;

import Entidades.Usuario;
import Logica.LogicaUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistroServlet", urlPatterns = {"/RegistroServlet"})
public class RegistroServlet extends HttpServlet {
    
    private LogicaUsuario logicaU = new LogicaUsuario();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ObjectMapper om = new ObjectMapper();
        
        response.setContentType("application/json; charset=UTF-8");
        
        Usuario recibido = om.readValue(request.getInputStream(), Usuario.class);
        
        
        Usuario nuevo = logicaU.registrar(recibido);
        
        if (nuevo == null) {
            
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al intentar registrar usuario\"}");
        } else {
            String json = om.writeValueAsString(nuevo);
            response.getWriter().print(json);
        }
    }
    
}
