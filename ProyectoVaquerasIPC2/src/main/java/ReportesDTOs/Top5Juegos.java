
package ReportesDTOs;

public class Top5Juegos {
    
    private String titulo;
    private int total_ventas;
    private double ingresos_brutos;

    public Top5Juegos() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getTotal_ventas() {
        return total_ventas;
    }

    public void setTotal_ventas(int total_ventas) {
        this.total_ventas = total_ventas;
    }

    public double getIngresos_brutos() {
        return ingresos_brutos;
    }

    public void setIngresos_brutos(double ingresos_brutos) {
        this.ingresos_brutos = ingresos_brutos;
    }
    
    
    
}
