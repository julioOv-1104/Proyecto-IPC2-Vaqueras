
package ReportesDTOs;

public class VentasPropias {
    
    private String titulo;
    private double monto_bruto, comision_plataforma, ingreso_neto;

    public VentasPropias() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getMonto_bruto() {
        return monto_bruto;
    }

    public void setMonto_bruto(double monto_bruto) {
        this.monto_bruto = monto_bruto;
    }

    public double getComision_plataforma() {
        return comision_plataforma;
    }

    public void setComision_plataforma(double comision_plataforma) {
        this.comision_plataforma = comision_plataforma;
    }

    public double getIngreso_neto() {
        return ingreso_neto;
    }

    public void setIngreso_neto(double ingreso_neto) {
        this.ingreso_neto = ingreso_neto;
    }
    
    
    
}
