
package Entidades;

public class Empresa {
    
    private String nombre_empresa;
    private String descripcion;
    private double comision;

    public Empresa(String nombre_empresa, String descripcion, double comision) {
        this.nombre_empresa = nombre_empresa;
        this.descripcion = descripcion;
        this.comision = comision;
    }

    public Empresa() {
    }

    public String getNombre_empresa() {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) {
        this.nombre_empresa = nombre_empresa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }
    
    
}
