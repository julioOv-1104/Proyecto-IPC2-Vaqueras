
package Controladores;

import Entidades.*;
import Logica.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;




@WebServlet(name = "GrupoServlet", urlPatterns = {"/GrupoServlet"})
public class GrupoServlet extends HttpServlet {
    
    private LogicaGrupo logicaG = new LogicaGrupo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ObjectMapper om = new ObjectMapper();
        
        response.setContentType("application/json; charset=UTF-8");
        
         Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

        String correo_encargado = (String) datos.get("correo_encargado");
        String nombre = (String) datos.get("nombre");
        
        GrupoFamiliar recibido = new GrupoFamiliar( correo_encargado, nombre);
        
        
        GrupoFamiliar nuevo = logicaG.crearGrupo(recibido);
        
        if (nuevo == null) {
            
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al intentar registrar grupo\"}");
        } else {
            String json = om.writeValueAsString(nuevo);
            response.getWriter().print(json);
        }
        
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ObjectMapper om = new ObjectMapper();
        
        response.setContentType("application/json; charset=UTF-8");
        
         Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

        String correo_miembro = (String) datos.get("correo_miembro");
        int id_grupo = Integer.parseInt(datos.get("id_grupo").toString());

        boolean todoBien = logicaG.unirseAgrupo(id_grupo, correo_miembro);
        
        if (todoBien) {
            
            response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Se unio al grupo\"}");
        } else {
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se pudo unir al grupo\"}");
        }
        
    }

}
