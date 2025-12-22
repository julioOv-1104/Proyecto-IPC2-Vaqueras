package Controladores;

import DAOs.ReportesEmpresaDAO;
import ReportesDTOs.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "ReportesEmpresaServlet", urlPatterns = {"/ReportesEmpresaServlet"})
public class ReportesEmpresaServlet extends HttpServlet {

    ReportesEmpresaDAO reportesDao = new ReportesEmpresaDAO();

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
                case "ventasPropias":
                    obtenerVentasPropias(request, response, objectMapper);
                    break;

                case "calificacionPromedio":
                    obtenercalificacionPromedio(request, response, objectMapper);
                    break;

                case "topJuegos":
                    obtenerTopJuegos(request, response, objectMapper);
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

    private void obtenerVentasPropias(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) {

        try {

            Map<String, Object> datos = objectMapper.readValue(request.getInputStream(), Map.class);

            String empresa = (String) datos.get("nombre_empresa");

            ArrayList<VentasPropias> reporte = reportesDao.obtenerVentasPropias(empresa);

            if (reporte == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Ocurrio un error al obtener las ventas propias\"}");

            } else {
                String json = objectMapper.writeValueAsString(reporte);
                response.getWriter().print(json);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR AL OBTENER VENTAS PROPIAS DESDE SERVLET" + e.getMessage());
        }

    }

    private void obtenercalificacionPromedio(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) {

        try {

            ArrayList<CalificacionPromedio> reporte = reportesDao.obtenerCalificacionPromedio();

            if (reporte == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Ocurrio un error al obtener las calificaciones promedio\"}");

            } else {
                String json = objectMapper.writeValueAsString(reporte);
                response.getWriter().print(json);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR AL OBTENER CALIFICACIONES PROMEDIO DESDE SERVLET" + e.getMessage());
        }

    }

    private void obtenerTopJuegos(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) {

        try {

            Map<String, Object> datos = objectMapper.readValue(request.getInputStream(), Map.class);

            String nombre_empresa = (String) datos.get("nombre_empresa");
            String fechaStr1 = (String) datos.get("fecha1");
            String fechaStr2 = (String) datos.get("fecha2");

            Date fecha1;
            Date fecha2;

            if (fechaStr1.isBlank()) {
                fecha1 = null;
            } else {
                fecha1 = Date.valueOf(fechaStr1);
            }
            
            if (fechaStr2.isBlank()) {
                fecha2 = null;
            } else {
                fecha2 = Date.valueOf(fechaStr2);
            }

            ArrayList<Top5Juegos> top = reportesDao.obtenerTopJuegos(nombre_empresa, fecha1, fecha2);

            if (top == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Ocurrio un error al obtener el top 5 de juegos\"}");

            } else {
                String json = objectMapper.writeValueAsString(top);
                response.getWriter().print(json);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR AL OBTENER TOP 5 DE JUEGOS DESDE SERVLET" + e.getMessage());
        }

    }
}
