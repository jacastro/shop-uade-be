package ar.edu.uade.integracion.shop.entity;

public class Claim {
    private String email;
    private String descripcion;
    private Integer nroOrden;

    public Claim(String email, String descripcion, Integer nroOrden) {
        this.email = email;
        this.descripcion = descripcion;
        this.nroOrden = nroOrden;
    }

    public String getEmail() {
        return email;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getNroOrden() {
        return nroOrden;
    }
}
