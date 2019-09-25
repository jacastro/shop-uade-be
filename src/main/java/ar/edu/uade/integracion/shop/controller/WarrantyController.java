package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.Category;
import ar.edu.uade.integracion.shop.entity.Warranty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "warranties")
public class WarrantyController {

    @GetMapping("/")
    public List<Warranty> getCategories() {
        return Arrays.asList(Warranty.values());
    }
}
