
package Controladores;

import Entidades.Usuario;
import Logica.logicaUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson;



@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {
    
    private logicaUsuario logicaU = new logicaUsuario();
    private Gson gson = new Gson();

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
        //String json = gson.toJson(usuario);
        response.getWriter().print(json);
        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
