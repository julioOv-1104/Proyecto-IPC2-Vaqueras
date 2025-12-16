package Controladores;

import Logica.LogicaOpinion;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "OpinionServlet", urlPatterns = {"/OpinionServlet"})
public class OpinionServlet extends HttpServlet {

    private LogicaOpinion logicaO = new LogicaOpinion();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");
        String accion = request.getParameter("accion");

        switch (accion) {
            case "comentar":
                comentar(request, response, om);
                break;

            case "responder":
                responder(request, response, om);
                break;
                
                case "calificar":
                calificar(request, response, om);
                break;
            default:
                throw new AssertionError();
        }

    }

    private void comentar(HttpServletRequest request, HttpServletResponse response, ObjectMapper om)
            throws ServletException, IOException {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            //Recibe los datos de el comentario
            String correo = (String) datos.get("correo_usuario");
            String texto = (String) datos.get("texto");
            String titulo = (String) datos.get("titulo");

            boolean todoBien = logicaO.comentar(correo, titulo, texto);;

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Se comentó con exito\"}");

            } else {

                response.getWriter().print("{\"mensaje\":\"Error al intentar comentar\"}");
            }
        } catch (Exception e) {

            System.out.println("ERROR AL INTENTAR COMENTAR " + e.getMessage());
        }

    }

    private void responder(HttpServletRequest request, HttpServletResponse response, ObjectMapper om)
            throws ServletException, IOException {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            //Recibe los datos de la respuesta
            String correo = (String) datos.get("correo_usuario");
            String texto = (String) datos.get("texto");
            int id_padre = Integer.parseInt(datos.get("id_comentario_padre").toString());

            boolean todoBien = logicaO.responder(correo, texto, id_padre);;

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Se comentó la respuesta con exito\"}");

            } else {

                response.getWriter().print("{\"mensaje\":\"Error al intentar responder\"}");
            }
        } catch (Exception e) {

            System.out.println("ERROR AL INTENTAR RESPONDER " + e.getMessage());
        }

    }
    
    private void calificar(HttpServletRequest request, HttpServletResponse response, ObjectMapper om)
            throws ServletException, IOException{
        
        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            //Recibe los datos de la calificacion
            String correo = (String) datos.get("correo_usuario");
            String titulo = (String) datos.get("titulo");
            double estrellas = Double.parseDouble(datos.get("estrellas").toString());

            boolean todoBien = logicaO.calificar(correo, titulo, estrellas);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Se publico la calificacion con exito\"}");

            } else {

                response.getWriter().print("{\"mensaje\":\"Error al intentar calificar\"}");
            }
        } catch (Exception e) {

            System.out.println("ERROR AL INTENTAR CALIFICAR DESDE SERVLET " + e.getMessage());
        }
    
    }

}
