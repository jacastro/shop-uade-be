package ar.edu.uade.integracion.shop.service;

import ar.edu.uade.integracion.shop.entity.Order;
import ar.edu.uade.integracion.shop.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShippingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClaimService.class);
    private final static String URL = "http://http://logistica-integracion.herokuapp.com/order/tienda/receive";
    private RestTemplate restTemplate;
    private OrderRepository orderRepository;

    public ShippingService(OrderRepository orderRepository) {
        restTemplate=new RestTemplate();
        this.orderRepository = orderRepository;
    }

    public void sendOrder(Order order){
        ResponseEntity responseEntity = restTemplate.postForEntity(URL, order, Object.class);
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            //save status new
        }
    }
}
