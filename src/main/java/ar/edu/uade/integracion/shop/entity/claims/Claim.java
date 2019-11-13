package ar.edu.uade.integracion.shop.entity.claims;

import java.time.LocalDateTime;

public class Claim {
    private LocalDateTime date;
    private String description;
    private ClaimStatus statusList;

    public Claim(LocalDateTime date, String description, ClaimStatus statusList) {
        this.date = date;
        this.description = description;
        this.statusList = statusList;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClaimStatus getStatusList() {
        return statusList;
    }

    public void setStatusList(ClaimStatus statusList) {
        this.statusList = statusList;
    }
}
