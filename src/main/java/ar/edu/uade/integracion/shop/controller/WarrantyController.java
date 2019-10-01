package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.Warranty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "warranties")
@Api(value = "/warranties", tags = "warranties", description = "Warranties")
public class WarrantyController {

    @GetMapping("/")
    @ApiOperation(value = "Retrieves the warranty types")
    public List<Warranty> getWarranties() {
        return Arrays.asList(Warranty.values());
    }
}
