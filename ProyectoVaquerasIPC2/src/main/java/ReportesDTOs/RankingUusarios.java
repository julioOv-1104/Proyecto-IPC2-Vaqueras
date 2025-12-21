
package ReportesDTOs;

public class RankingUusarios {
    
    private String correo_usuario;
    private int total_resennas, total_juegos;

    public RankingUusarios() {
    }

    public String getCorreo_usuario() {
        return correo_usuario;
    }

    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
    }

    public int getTotal_resennas() {
        return total_resennas;
    }

    public void setTotal_resennas(int total_resennas) {
        this.total_resennas = total_resennas;
    }

    public int getTotal_juegos() {
        return total_juegos;
    }

    public void setTotal_juegos(int total_juegos) {
        this.total_juegos = total_juegos;
    }
    
    
    
}
