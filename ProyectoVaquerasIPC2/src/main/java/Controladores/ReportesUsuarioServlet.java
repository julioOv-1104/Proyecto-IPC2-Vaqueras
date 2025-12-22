package Controladores;

import DAOs.ReportesUsuarioDAO;
import ReportesDTOs.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "ReportesUsuarioServlet", urlPatterns = {"/ReportesUsuarioServlet"})
public class ReportesUsuarioServlet extends HttpServlet {

    private ReportesUsuarioDAO reportesDao = new ReportesUsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

        String accionRecibida = request.getParameter("accion");

        if (accionRecibida == null) {
            response.getWriter().print("{\"error\": \"Acción no especificada\"}");
            return;
        }

        try {
            switch (accionRecibida) {
                case "historialGastos":
                    obtenerHistorialGastos(request, response, objectMapper);
                    break;

                case "mayorValoracion":
                    obtenerMayotValoracion(request, response, objectMapper);
                    break;

                case "categoriasFavoritas":
                    obtenerCategoriasFavoritas(request, response, objectMapper);
                    break;

                default:

                    response.getWriter().print("{\"error\": \"Acción no válida\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    private void obtenerHistorialGastos(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) {

        try {

            Map<String, Object> datos = objectMapper.readValue(request.getInputStream(), Map.class);

            String correo = (String) datos.get("correo_usuario");

            ArrayList<HistorialGastos> reporte = reportesDao.obtenerHistorialGastos(correo);

            if (reporte == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Ocurrio un error al obtener el historial de gastos\"}");

            } else {
                String json = objectMapper.writeValueAsString(reporte);
                response.getWriter().print(json);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR AL OBTENER EL HISTORIAL DE GASTOS DESDE SERVLET" + e.getMessage());
        }

    }

    private void obtenerMayotValoracion(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) {

        try {

            Map<String, Object> datos = objectMapper.readValue(request.getInputStream(), Map.class);

            String correo = (String) datos.get("correo_usuario");

            ArrayList<JuegosConValoracion> reporte = reportesDao.obtenerJuegosConValoracion(correo);

            if (reporte == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Ocurrio un error al obtener los juegos y sus valoraciones\"}");

            } else {
                String json = objectMapper.writeValueAsString(reporte);
                response.getWriter().print(json);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR AL OBTENER JUEGOS Y SUS VALORACIONES DESDE SERVLET" + e.getMessage());
        }

    }

    private void obtenerCategoriasFavoritas(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) {

        try {

            Map<String, Object> datos = objectMapper.readValue(request.getInputStream(), Map.class);

            String correo = (String) datos.get("correo_usuario");

            ArrayList<CategoriasFavoritas> reporte = reportesDao.obtenerCategoriasFavoritas(correo);

            if (reporte == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Ocurrio un error al obtener las categorias favoritas\"}");

            } else {
                String json = objectMapper.writeValueAsString(reporte);
                response.getWriter().print(json);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR AL OBTENER CATEGORIAS FAVORITAS DESDE SERVLET" + e.getMessage());
        }

    }

}
