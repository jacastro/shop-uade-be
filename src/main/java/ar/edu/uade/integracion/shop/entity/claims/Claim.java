package ar.edu.uade.integracion.shop.entity.claims;

import java.time.LocalDateTime;
import java.util.List;

public class Claim {
    private LocalDateTime date;
    private String description;
    private List<ClaimStatus> statusList;

    public Claim(LocalDateTime date, String description, List<ClaimStatus> statusList) {
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

    public List<ClaimStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<ClaimStatus> statusList) {
        this.statusList = statusList;
    }
}
