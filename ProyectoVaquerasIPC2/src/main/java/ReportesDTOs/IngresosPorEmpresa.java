
package ReportesDTOs;

public class IngresosPorEmpresa {
    
    private String nombre_empresa;
    private double total_ventas,comision_retenida,monto_neto;

    public IngresosPorEmpresa() {
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public double getTotal_ventas() {
        return total_ventas;
    }

    public void setTotal_ventas(double total_ventas) {
        this.total_ventas = total_ventas;
    }

    public double getComision_retenida() {
        return comision_retenida;
    }

    public void setComision_retenida(double comision_retenida) {
        this.comision_retenida = comision_retenida;
    }

    public double getMonto_neto() {
        return monto_neto;
    }

    public void setMonto_neto(double monto_neto) {
        this.monto_neto = monto_neto;
    }
    
    
    
}
