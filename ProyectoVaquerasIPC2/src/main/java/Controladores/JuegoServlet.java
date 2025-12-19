package Controladores;

import Entidades.*;
import Logica.LogicaJuego;
import Utilidades.TipoClasificacion;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Map;

@MultipartConfig
@WebServlet(name = "JuegoServlet", urlPatterns = {"/JuegoServlet"})
public class JuegoServlet extends HttpServlet {

    private LogicaJuego logicaJ = new LogicaJuego();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

        Juego entrante = objectMapper.readValue(request.getInputStream(), Juego.class);

        Juego juego = logicaJ.obtenerJuego(entrante.getTitulo());

        if (juego.getTitulo()== null) {

            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Ocurrio un error al obtener juego\"}");

        } else {
            String json = objectMapper.writeValueAsString(juego);
            response.getWriter().print(json);
        }
        
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

            Part archivo = request.getPart("multimedia");

            InputStream inputStream = archivo.getInputStream();
            String tipo = archivo.getContentType();

            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");
            String precio = request.getParameter("precio");
            double precioJuego = Double.parseDouble(precio);
            String clasificacion = request.getParameter("clasificacion");
            String fecha = request.getParameter("fecha_lanzamiento");
            Date fecha_lanzamiento = Date.valueOf(fecha);
            String nombre_empresa = request.getParameter("nombre_empresa");

            //Juego nuevo = om.readValue(request.getInputStream(), Juego.class);
            Juego nuevo = new Juego();
            
            nuevo.setTitulo(titulo);
            nuevo.setDescripcion(descripcion);
            nuevo.setPrecio(precioJuego);
            nuevo.setClasificacion(TipoClasificacion.valueOf(clasificacion));
            nuevo.setFecha_lanzamiento(fecha_lanzamiento);
            nuevo.setNombre_empresa(nombre_empresa);
            nuevo.setTipo_multimedia(tipo);
            

            Juego juegoNuevo = logicaJ.registrarJuegoNuevo(nuevo, inputStream);

            if (juegoNuevo == null) {

                response.getWriter().print("{\"mensaje\":\"Error al intentar publicar un juego\"}");

            } else {
                String json = om.writeValueAsString(juegoNuevo);
                response.getWriter().print(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR AL REGISTRAR JUEGO DESDE SERVLET" + e.getMessage());
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
