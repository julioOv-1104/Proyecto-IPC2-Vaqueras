package Controladores;

import DAOs.ReportesAdminDAO;
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

@WebServlet(name = "ReportesAdminServlet", urlPatterns = {"/ReportesAdminServlet"})
public class ReportesAdminServlet extends HttpServlet {

    private ReportesAdminDAO reportesDao = new ReportesAdminDAO();

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
                case "gananciasGlobales":
                    obtenerGananciasGlobales(request, response, objectMapper);
                    break;

                case "ventasYcalidad":
                    obtenerVentasYcalidad(request, response, objectMapper);
                    break;

                case "ingresosPorEmpresa":
                    obtenerIngresosoPorEmpresa(request, response, objectMapper);
                    break;

                case "rankingUsuarios":
                    obtenerRankingUsuarios(request, response, objectMapper);
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

    private void obtenerGananciasGlobales(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) {

        try {

            GananciasGlobales reporte = reportesDao.obtenerGananciasGlobales();

            if (reporte == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Ocurrio un error al obtener las ganancias globales\"}");

            } else {
                String json = objectMapper.writeValueAsString(reporte);
                response.getWriter().print(json);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR AL OBTENER GANANCIAS GLOBALES DESDE SERVLET" + e.getMessage());
        }

    }

    private void obtenerVentasYcalidad(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) {

        try {

            Map<String, Object> datos = objectMapper.readValue(request.getInputStream(), Map.class);

            String categoria = (String) datos.get("categoria");
            String clasificacion = (String) datos.get("clasificacion");

            if (categoria.isBlank()) {
                categoria = null;
            }

            if (clasificacion.isBlank()) {
                clasificacion = null;
            }

            ArrayList<VentasYcalidad> reporte = reportesDao.obtenerVentasYcalidad(categoria, clasificacion);

            if (reporte == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Ocurrio un error al obtener las ventas y calidad\"}");

            } else {
                String json = objectMapper.writeValueAsString(reporte);
                response.getWriter().print(json);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR AL OBTENER VENTAS Y CALIDAD DESDE SERVLET" + e.getMessage());
        }

    }

    private void obtenerIngresosoPorEmpresa(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) {

        try {

            ArrayList<IngresosPorEmpresa> reporte = reportesDao.obtenerIngresosPorEmpresa();

            if (reporte.isEmpty()) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Ocurrio un error al obtener los ingresos por empresa\"}");

            } else {
                String json = objectMapper.writeValueAsString(reporte);
                response.getWriter().print(json);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR AL OBTENER INGRESOSO POR EMPRESA DESDE SERVLET" + e.getMessage());
        }

    }

    private void obtenerRankingUsuarios(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) {

        try {

            ArrayList<RankingUusarios> reporte = reportesDao.obtenerRankingUsuarios();

            if (reporte.isEmpty()) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Ocurrio un error al obtener el ranking de usuarios\"}");

            } else {
                String json = objectMapper.writeValueAsString(reporte);
                response.getWriter().print(json);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR AL OBTENER RANKING DE USUARIOS DESDE SERVLET" + e.getMessage());
        }

    }

}
