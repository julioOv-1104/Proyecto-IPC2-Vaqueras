package Controladores;

import Entidades.Usuario;
import Logica.LogicaUsuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

@WebServlet(name = "CompraServlet", urlPatterns = {"/CompraServlet"})
public class CompraServlet extends HttpServlet {

    private LogicaUsuario logicaU = new LogicaUsuario();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

        Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

        String correo = (String) datos.get("correo_usuario");
        String titulo = (String) datos.get("titulo");
        String fechaStr = (String) datos.get("fecha_compra");

        LocalDate fecha = LocalDate.parse(fechaStr); // yyyy-MM-dd
        Date fecha_compra = Date.valueOf(fecha);

        boolean todoBien = logicaU.comprarJuego(correo, titulo, fecha_compra);

        if (todoBien) {

            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Compra realizada con exito\"}");
        } else {
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al intentar la compra\"}");
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

        Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

        String correo = (String) datos.get("correo_usuario");
        double recarga = Double.parseDouble(datos.get("recarga").toString());

        boolean todoBien = logicaU.recargarCarteraGamer(correo, recarga);

        if (todoBien) {

            response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Cartera recargada se acreditaron " + recarga + "\"}");
        } else {
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al intentar recargar cartera\"}");
        }

    }

}
