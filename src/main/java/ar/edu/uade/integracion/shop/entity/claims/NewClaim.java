package ar.edu.uade.integracion.shop.entity.claims;

public class NewClaim {
    private String email;
    private String description;
    private Integer orderId;
    private String name;

    public NewClaim(String email, String description, Integer orderId, String name) {
        this.email = email;
        this.description = description;
        this.orderId = orderId;
        this.name = name;
    }

    public NewClaim() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
