package Controladores;

import Logica.logicaEmpresa;
import Logica.logicaUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "AdminServlet", urlPatterns = {"/AdminServlet"})
public class AdminServlet extends HttpServlet {

    private logicaEmpresa logicaE = new logicaEmpresa();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");

        try {
            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            String empresa = (String) datos.get("nombre_empresa");
            // Ojo: Jackson suele leer números como Double o Integer
            Double comision = Double.valueOf(datos.get("comision").toString());

            boolean todoBien = logicaE.cambiarComisionAEspecifica(comision, empresa);

            if (todoBien) {
                //response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().print("{\"mensaje\": \"Actualizado\"}");
            } else {
                //response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // O 409
                response.getWriter().print("{\"error\": \"La comisión es mayor a la global o empresa no existe\"}");
            }

        } catch (Exception e) {
        }

    }
}
