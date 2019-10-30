package ar.edu.uade.integracion.shop.entity.claims;

public class NewClaim {
    private String email;
    private String descripcion;
    private Integer nroOrden;

    public NewClaim(String email, String descripcion, Integer nroOrden) {
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
