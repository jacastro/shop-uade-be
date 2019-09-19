package ar.edu.uade.integracion.shop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/private/")
public class TestController {
    @RequestMapping(value = "test")
    private String getPP(){
        return "pepe";
    }
}
