
package Controladores;

import Logica.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;



@WebServlet(name = "EmpresaServlet", urlPatterns = {"/EmpresaServlet"})
public class EmpresaServlet extends HttpServlet {
    
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
        
        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            String empresa = (String) datos.get("nombre_empresa");

            boolean todoBien = logicaE.desactivarActivarComenatrio(empresa);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Se cambio la visibilidad de los comentarios de la empresa\"}");

            } else {

                response.getWriter().print("{\"mensaje\":\"Error al intentar cambiar la visibilidad los comentarios de la empresa\"}");
            }

        } catch (Exception e) {

            System.out.println("ERROR AL CAMBIAR VISIBILIDAD de los comentarios de la empresa" + e.getMessage());
        }
        
        
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         ObjectMapper om = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");
        
        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            String titulo = (String) datos.get("titulo");
            String descripcion = (String) datos.get("descripcion");
            double precio = Double.parseDouble(datos.get("precio").toString());

            boolean todoBien = logicaE.editarInformacion(titulo, descripcion, precio);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Se edito lainformacion del juego\"}");

            } else {

                response.getWriter().print("{\"mensaje\":\"Error al intentar editar informacion del juego\"}");
            }

        } catch (Exception e) {

            System.out.println("ERROR AL EDITAR INFORMACION" + e.getMessage());
        }
        
        
    }
    

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
