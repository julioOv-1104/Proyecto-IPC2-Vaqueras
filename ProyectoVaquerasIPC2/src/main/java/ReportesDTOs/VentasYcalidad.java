
package ReportesDTOs;

public class VentasYcalidad {
    
    private String titulo, clasificacion;
    private int total_ventas;
    private double promedio_calificacion;

    public VentasYcalidad() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public int getTotal_ventas() {
        return total_ventas;
    }

    public void setTotal_ventas(int total_ventas) {
        this.total_ventas = total_ventas;
    }

    public double getPromedio_calificacion() {
        return promedio_calificacion;
    }

    public void setPromedio_calificacion(double promedio_calificacion) {
        this.promedio_calificacion = promedio_calificacion;
    }
    
    
    
    
}
