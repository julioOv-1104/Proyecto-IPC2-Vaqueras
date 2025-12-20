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
import java.util.Map;

@WebServlet(name = "GrupoServlet", urlPatterns = {"/GrupoServlet"})
public class GrupoServlet extends HttpServlet {

    private LogicaGrupo logicaG = new LogicaGrupo();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

         GrupoFamiliar entrante = objectMapper.readValue(request.getInputStream(), GrupoFamiliar.class);

        GrupoFamiliar nuevo  = logicaG.obtenerGrupo(entrante.getId_grupo());

        if (nuevo.getId_grupo() ==0) {

            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\" Ocurrio un error al obtener el grupo\"}");

        } else {
            String json = objectMapper.writeValueAsString(nuevo);
            response.getWriter().print(json);
        }
        
        
    }

    @Override//CREA UN GRUPO
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

        Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

        String correo_encargado = (String) datos.get("correo_encargado");
        String nombre = (String) datos.get("nombre");

        GrupoFamiliar recibido = new GrupoFamiliar(correo_encargado, nombre);

        GrupoFamiliar nuevo = logicaG.crearGrupo(recibido);

        if (nuevo == null) {

            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"Error al intentar registrar grupo\"}");
        } else {
            String json = om.writeValueAsString(nuevo);
            response.getWriter().print(json);
        }

    }

    @Override //UNE A UN USUARIO AL GRUPO
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ObjectMapper om = new ObjectMapper();

        response.setContentType("application/json; charset=UTF-8");

        Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

        String correo_miembro = (String) datos.get("correo_miembro");
        int id_grupo = Integer.parseInt(datos.get("id_grupo").toString());

        boolean todoBien = logicaG.unirseAgrupo(id_grupo, correo_miembro);

        if (todoBien) {

            response.getWriter().print("{\"status\":\"exito\",\"mensaje\":\"Se unio al grupo\"}");
        } else {
            response.getWriter().print("{\"status\":\"error\",\"mensaje\":\"No se pudo unir al grupo\"}");
        }

    }

    @Override//BOORA Y/O SACA A LOS MIEMBROS
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
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
                case "salir":
                    salirGrupo(request, response, om);
                    break;

                case "borrar":
                    borrarGrupo(request, response, om);
                    break;

                default:

                    response.getWriter().print("{\"error\": \"Acción no válida\"}");
            }
        } catch (Exception e) {
            System.out.println("ERROR EN doDelte EN GrupoServlet");
            e.printStackTrace();

        }

    }

    private void salirGrupo(HttpServletRequest request, HttpServletResponse response, ObjectMapper om)
            throws ServletException, IOException {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            String correo_miembro = (String) datos.get("correo_miembro");
            int id_grupo = Integer.parseInt(datos.get("id_grupo").toString());
            
            boolean todoBien = logicaG.SalirDeGrupo(id_grupo, correo_miembro);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Ha salido del grupo con exito\"}");

            } else {
                response.getWriter().print("{\"mensaje\":\"Error al salir de grupo\"}");
            }

        } catch (Exception e) {
            System.out.println("ERROR AL SALIR DE GRUPO" + e.getMessage());
        }

    }

    private void borrarGrupo(HttpServletRequest request, HttpServletResponse response, ObjectMapper om)
            throws ServletException, IOException {

        try {

            Map<String, Object> datos = om.readValue(request.getInputStream(), Map.class);

            int id_grupo = Integer.parseInt(datos.get("id_grupo").toString());
            
            boolean todoBien = logicaG.borrarGrupo(id_grupo);

            if (todoBien) {

                response.getWriter().print("{\"mensaje\":\"Ha eliminado el grupo con exito\"}");

            } else {
                response.getWriter().print("{\"mensaje\":\"Error al eliminar el grupo\"}");
            }

        } catch (Exception e) {
            System.out.println("ERROR AL ELIMINAR EL GRUPO" + e.getMessage());
        }
        
    }

}
