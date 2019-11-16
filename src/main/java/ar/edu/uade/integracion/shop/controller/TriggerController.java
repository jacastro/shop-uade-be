package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.service.ShippingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TriggerController {
    private ShippingService shippingService;

    public TriggerController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @GetMapping(value = "/trigger")
    public void trigger(){
        shippingService.getOrderStatus();
    }
}
