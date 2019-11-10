package ar.edu.uade.integracion.shop.service;

import ar.edu.uade.integracion.shop.entity.claims.Claim;
import ar.edu.uade.integracion.shop.entity.claims.ClaimStatus;
import ar.edu.uade.integracion.shop.entity.claims.NewClaim;
import ar.edu.uade.integracion.shop.exception.ClaimException;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClaimService.class);
    private final static String URL = "http://integracion-aplicaciones.herokuapp.com/reclamo/tienda";
    private final static String GET_URL = "http://integracion-aplicaciones.herokuapp.com/reclamo/orden/{id}";
    private RestTemplate restTemplate;

    public ClaimService() {
        restTemplate = new RestTemplate();
    }

    public void createClaim(Integer orderId, String userEmail, String claim) {
        NewClaim serviceNewClaim = new NewClaim(userEmail, claim, orderId);
        try {
            restTemplate.postForEntity(URL, claim, Object.class);
        } catch (Exception e) {
            LOGGER.error("Exception while posting claim to external system: {}", e);
            throw new ClaimException();
        }
    }

    public List<Claim> getOrderClaims(Integer orderId) {
        try {
            List<ExternalClaim> externalClaims = Lists.newArrayList(restTemplate
                    .getForEntity(URL, ExternalClaim[].class, orderId).getBody());

            return externalClaims.stream().map(ec -> {
                return new Claim(ec.getFecha(), ec.getDescripcion(),
                        ec.getEstado().stream().map(e ->
                                new ClaimStatus(e.getId(), e.getDescripcion())
                        ).collect(Collectors.toList()));
            }).collect(Collectors.toList());

        } catch (Exception e) {
            LOGGER.error("Exception while getting claims: {}", e);
            return new ArrayList<>();
        }
    }

}

final class ExternalClaim {
    private LocalDateTime fecha;
    private String descripcion;
    private List<ExternalCLaimStatus> estado;

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ExternalCLaimStatus> getEstado() {
        return estado;
    }

    public void setEstado(List<ExternalCLaimStatus> estado) {
        this.estado = estado;
    }
}

final class ExternalCLaimStatus {
    private Integer id;
    private String descripcion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}