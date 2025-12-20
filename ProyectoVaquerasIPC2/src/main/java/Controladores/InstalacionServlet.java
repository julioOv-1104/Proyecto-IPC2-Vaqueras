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
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "InstalacionServlet", urlPatterns = {"/InstalacionServlet"})
public class InstalacionServlet extends HttpServlet {

    private LogicaInstalacion logicaIns = new LogicaInstalacion();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

         InstalacionPrestamo entrante = objectMapper.readValue(request.getInputStream(), InstalacionPrestamo.class);

        ArrayList<InstalacionPrestamo> nuevo  = logicaIns.obtenerInstalaciones(entrante.getCorreo_usuario());

        if (nuevo.isEmpty()) {//si está vacio

            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Ocurrio un error al obtener las instalaciones de juegos prestados\"}");

        } else {
            String json = objectMapper.writeValueAsString(nuevo);
            response.getWriter().print(json);
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            String titulo = (String) datos.get("titulo");
            String correo = (String) datos.get("correo_usuario");
            String fechaStr = (String) datos.get("fecha_instalacion");

            LocalDate fecha = LocalDate.parse(fechaStr); // yyyy-MM-dd
            Date fecha_instalacion = Date.valueOf(fecha);

            boolean todoBien = logicaIns.instalarJuegoPrestado(titulo, correo, fecha_instalacion);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Se Instalo el juego prestado\"}");

            } else {

                response.getWriter().print("{\"mensaje\":\"Error al intentar Instalar el juego prestado\"}");
            }

        } catch (Exception e) {
            System.out.println("ERROR AL INSTALAR EL JUEGO PRESTADO" + e.getMessage());
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");
        String accion = request.getParameter("accion");

        if (accion == null) {
            response.getWriter().print("{\"error\": \"Acción no especificada\"}");
            return;
        }

        try {
            switch (accion) {
                case "propio":
                    propios(request, response, om);
                    break;

                case "prestado":
                    prestados(request, response, om);
                    break;
                default:
                    response.getWriter().print("{\"error\": \"Acción no válida\"}");
            }
        } catch (Exception e) {
            System.out.println("ERROR EN doPut EN InstalacionServlet");
            e.printStackTrace();
        }
    }

    private void propios(HttpServletRequest request, HttpServletResponse response, ObjectMapper om)
            throws ServletException, IOException  {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            String titulo = (String) datos.get("titulo");
            String correo = (String) datos.get("correo_usuario");

            boolean todoBien = logicaIns.instalarDesinstalarPropios(titulo, correo);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Se Instalo/Desinstalo el juego\"}");

            } else {

                response.getWriter().print("{\"mensaje\":\"Error al intentar Instalar/Desinstalar el juego\"}");
            }

        } catch (Exception e) {
            System.out.println("ERROR AL INSTALAR/DESINSTALAR EL JUEGO" + e.getMessage());
        }

    }
    
    private void prestados(HttpServletRequest request, HttpServletResponse response, ObjectMapper om)
            throws ServletException, IOException  {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            String titulo = (String) datos.get("titulo");
            String correo = (String) datos.get("correo_usuario");

            boolean todoBien = logicaIns.desinstalarJuegoPrestado(titulo, correo);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Se Desinstalo el juego prestado\"}");

            } else {

                response.getWriter().print("{\"mensaje\":\"Error al intentar Desinstalar el juego prestado\"}");
            }

        } catch (Exception e) {
            System.out.println("ERROR AL DESINSTALAR EL JUEGO PRESTADO" + e.getMessage());
        }

    }

}
