
package Controladores;

import Entidades.Usuario;
import Logica.LogicaUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    
    private LogicaUsuario logicaU = new LogicaUsuario();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ObjectMapper objectMapper = new ObjectMapper();
       
        response.setContentType("application/json; charset=UTF-8");
        
        Usuario loginData = objectMapper.readValue(request.getInputStream(), Usuario.class);
        
        System.out.println("email "+loginData.getCorreo_usuario());
        Usuario usuario = logicaU.login(loginData.getCorreo_usuario(), loginData.getContrasenna());
        
        if (usuario == null) {
            
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Credenciales incorrectas\"}");
            
        }else{
        String json = objectMapper.writeValueAsString(usuario);
        response.getWriter().print(json);
        }
    }    
}
