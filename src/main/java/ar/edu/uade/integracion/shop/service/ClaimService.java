package ar.edu.uade.integracion.shop.service;

import ar.edu.uade.integracion.shop.entity.Claim;
import ar.edu.uade.integracion.shop.exception.ClaimException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.http.HTTPException;

@Service
public class ClaimService {
    private final static String URL = "http://integracion-aplicaciones.herokuapp.com/reclamo/tienda";
    private RestTemplate restTemplate;

    public ClaimService() {
        restTemplate = new RestTemplate();
    }

    public void createClaim(Integer orderId, String userEmail, String claim){
        Claim serviceClaim = new Claim(userEmail,claim, orderId);
        try {
            restTemplate.postForEntity(URL, claim, Object.class);
        }catch (Exception e){
            throw new ClaimException();
        }
    }
}
