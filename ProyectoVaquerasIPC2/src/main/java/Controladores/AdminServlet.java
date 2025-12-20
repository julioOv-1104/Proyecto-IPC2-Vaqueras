package Controladores;

import Logica.LogicaEmpresa;
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

            String juego = (String) datos.get("juego");
            String categoriaVieja = (String) datos.get("categoriaVieja");
            String categoriaNueva = (String) datos.get("categoriaNueva");

            logicaE.cambiarCategoriaJuego(juego, categoriaNueva, categoriaVieja);
            
            response.getWriter().print("{\"mensaje\":\"Categoria cambiada de "+juego+"\"}");

        } catch (Exception e) {
            System.out.println("ERROR AL CAMBIAR CAMBIAR CATEGORIA DESDE SERVLET " + e.getMessage());
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");

        //Accion define si se está cambiano una comision especifica o global
        String accionRecibida = request.getParameter("accion");

        if (accionRecibida == null) {
            response.getWriter().print("{\"error\": \"Acción no especificada\"}");
            return;
        }

        try {
            switch (accionRecibida) {
                case "comisionEspecifica":
                    cambiarComisionEspecifics(request, response, om);
                    break;

                case "comisionGlobal":
                    actualizarComisionGlobal(request, response, om);
                    break;

                default:

                    response.getWriter().print("{\"error\": \"Acción no válida\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void cambiarComisionEspecifics(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {
            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            String empresa = (String) datos.get("nombre_empresa");

            Double comision = Double.valueOf(datos.get("comision").toString());

            boolean todoBien = logicaE.cambiarComisionAEspecifica(comision, empresa);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\": \"Comision especifica ctualizada para \" " + empresa + "}");
            } else {

                response.getWriter().print("{\"error\": \"La comisión es mayor a la global o empresa no existe\"}");
            }

        } catch (Exception e) {
            System.out.println("ERROR AL CAMBIAR COMISION ESPECIFICA DESDE SERVLET " + e.getMessage());
        }

    }

    private void actualizarComisionGlobal(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {
            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            Double comision = Double.valueOf(datos.get("comision").toString());

            boolean todoBien = logicaE.actualizarComision(comision);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\": \"Comision global actualizada \"}");
            } else {

                response.getWriter().print("{\"error\": \"La comisión es invalida\"}");
            }

        } catch (Exception e) {
            System.out.println("ERROR AL CAMBIAR COMISION GLOBAL DESDE SERVLET " + e.getMessage());
        }

    }
}
