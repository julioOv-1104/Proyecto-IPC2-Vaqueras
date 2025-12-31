package Controladores;

import Entidades.Empresa;
import Logica.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "EmpresaServlet", urlPatterns = {"/EmpresaServlet"})
public class EmpresaServlet extends HttpServlet {

    private LogicaEmpresa logicaE = new LogicaEmpresa();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");
        String accion = request.getParameter("accion");

        if (accion == null) {
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Accion no especificada\"}");
            return;
        }

        switch (accion) {
            case "obtenerUna":

                obtenerUna(request, response, objectMapper);

                break;

            case "obtenerTodas":
                obtenerTodas(request, response, objectMapper);
                break;
            default:
                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Accion no valida\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            //Se envia el nombre de la empresa que quiere desactivar los somentarios
            String empresa = (String) datos.get("nombre_empresa");

            boolean todoBien = logicaE.desactivarActivarComenatrio(empresa);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Se cambio la visibilidad de los comentarios de la empresa\"}");

            } else {

                response.getWriter().print("{\"mensaje\":\"Error al intentar cambiar la visibilidad los comentarios de la empresa\"}");
            }

        } catch (Exception e) {

            System.out.println("ERROR AL CAMBIAR VISIBILIDAD de los comentarios de la empresa" + e.getMessage());
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            //Son los campos que el empresario puede editar de un juego
            String titulo = (String) datos.get("titulo");
            String descripcion = (String) datos.get("descripcion");
            double precio = Double.parseDouble(datos.get("precio").toString());

            boolean todoBien = logicaE.editarInformacion(titulo, descripcion, precio);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Se edito la informacion del juego\"}");

            } else {

                response.getWriter().print("{\"mensaje\":\"Error al intentar editar informacion del juego\"}");
            }

        } catch (Exception e) {

            System.out.println("ERROR AL EDITAR INFORMACION" + e.getMessage());
            response.getWriter().print("{\"mensaje\":\"Error al intentar editar informacion del juego\"}");
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");

        try {
            // Se obtiene el correo del empresario que se va a eliminar
            String correo = request.getParameter("correo");

            if (correo == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print("{\"error\": \"Falta el correo del usuario\"}");
                return;
            }

            boolean eliminado = logicaE.eliminarEmpresario(correo);

            if (eliminado) {
                response.setStatus(HttpServletResponse.SC_OK); // O SC_NO_CONTENT (204)
                response.getWriter().print("{\"mensaje\": \"Usuario eliminado con éxito\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
                response.getWriter().print("{\"error\": \"No se encontró el usuario\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void obtenerUna(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) {

        try {

            Empresa entrante = objectMapper.readValue(request.getInputStream(), Empresa.class);

            Empresa empresa = logicaE.obtenerEmpresa(entrante.getNombre_empresa());

            if (empresa.getNombre_empresa() == null) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Ocurrio un error al obtener empresa\"}");

            } else {
                String json = objectMapper.writeValueAsString(empresa);
                response.getWriter().print(json);
            }

        } catch (Exception e) {
            System.out.println("ERROR AL OBTENER EMPRESA DESDE SERVLET");
        }

    }

    private void obtenerTodas(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper) {
        
        
        try {
            
            
             ArrayList<Empresa> empresas = logicaE.obtenerTodasEmpresaa();

            if (empresas.isEmpty()) {

                response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Ocurrio un error al obtener todas las empresas\"}");

            } else {
                String json = objectMapper.writeValueAsString(empresas);
                response.getWriter().print(json);
            }
            
            
        } catch (Exception e) {
        }
        
    }

}
