package Controladores;

import Logica.LogicaUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ReportesEmpresarioServlet", urlPatterns = {"/ReportesEmpresarioServlet"})
public class ReportesEmpresarioServlet extends HttpServlet {

    private LogicaUsuario logicaU = new LogicaUsuario();

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

                case "ventasYcalidad":
                    // obtenerVentasYcalidad(request, response, objectMapper);
                    break;

                case "ingresosPorEmpresa":
                    // obtenerIngresosoPorEmpresa(request, response, objectMapper);
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

    }
}
