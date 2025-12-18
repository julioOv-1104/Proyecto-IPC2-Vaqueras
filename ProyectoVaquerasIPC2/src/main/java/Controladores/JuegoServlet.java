package Controladores;

import Entidades.*;
import Logica.LogicaJuego;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "JuegoServlet", urlPatterns = {"/JuegoServlet"})
public class JuegoServlet extends HttpServlet {

    private LogicaJuego logicaJ = new LogicaJuego();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

        String accionRecibida = request.getParameter("accion");

        if (accionRecibida == null) {
            response.getWriter().print("{\"error\": \"Acción no especificada\"}");
            return;
        }

        try {
            switch (accionRecibida) {
                case "registrarJuego":
                    crearJuego(request, response, om);
                    break;

                case "agregarCategoria":
                    agregarCategoria(request, response, om);
                    break;

                case "agregarRecursos":
                    agregarRecursos(request, response, om);
                    break;

                case "desactivarActivar":
                    desactivarActivarVenta(request, response, om);
                    break;

                case "cambiarVisibilidad":
                    cambiarVisibilidadComentario(request, response, om);
                    break;

                default:

                    response.getWriter().print("{\"error\": \"Acción no válida\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void crearJuego(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Juego nuevo = om.readValue(request.getInputStream(), Juego.class);

            Juego juegoNuevo = logicaJ.registrarJuegoNuevo(nuevo);

            if (juegoNuevo == null) {

                response.getWriter().print("{\"mensaje\":\"Error al intentar publicar un juego\"}");

            } else {
                String json = om.writeValueAsString(juegoNuevo);
                response.getWriter().print(json);
            }

        } catch (Exception e) {
            System.out.println("ERROR AL REGISTRAR JUEVO" + e.getMessage());
        }

    }

    private void agregarCategoria(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            String titulo = (String) datos.get("titulo");
            String categoria = (String) datos.get("categoria");

            boolean todoBien = logicaJ.agregarCategoria(titulo, categoria);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Se asignó categoria a juego\"}");

            } else {

                response.getWriter().print("{\"mensaje\":\"Error al intentar asignar categoria a juego\"}");
            }

        } catch (Exception e) {
            System.out.println("ERROR AL REGISTRAR JUEGO" + e.getMessage());
        }

    }

    private void agregarRecursos(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            String titulo = (String) datos.get("titulo");

            String[] valor = new String[4];//un arrelo strings para guardar los valores de cada recurso

            valor[0] = (String) datos.get("valor_almacenamiento");
            valor[1] = (String) datos.get("valor_ram");
            valor[2] = (String) datos.get("valor_procesador");
            valor[3] = (String) datos.get("valor_sistema");

            boolean todoBien = logicaJ.agregarRecursos(titulo, valor);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Se asignaron recursos al juego\"}");

            } else {

                response.getWriter().print("{\"mensaje\":\"Error al intentar asignar recursos al juego\"}");
            }

        } catch (Exception e) {

            System.out.println("ERROR AL AGREGAR RECURSOS " + e.getMessage());
        }

    }

    private void desactivarActivarVenta(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            String titulo = (String) datos.get("titulo");

            boolean todoBien = logicaJ.desactivarActivarVenta(titulo);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Se cambio la venta del juego\"}");

            } else {

                response.getWriter().print("{\"mensaje\":\"Error al intentar cambiar la visibilidad del juego\"}");
            }

        } catch (Exception e) {

            System.out.println("ERROR AL CAMBIAR VISIBILIDAD " + e.getMessage());
        }

    }
    
    private void cambiarVisibilidadComentario(HttpServletRequest request, HttpServletResponse response, ObjectMapper om) {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            String titulo = (String) datos.get("titulo");

            boolean todoBien = logicaJ.desactivarActivarComenatrio(titulo);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Se cambio la visibilidad de los comentarios del juego\"}");

            } else {

                response.getWriter().print("{\"mensaje\":\"Error al intentar cambiar la visibilidad los comentarios del juego\"}");
            }

        } catch (Exception e) {

            System.out.println("ERROR AL CAMBIAR VISIBILIDAD de los comentarios" + e.getMessage());
        }

    }

}
