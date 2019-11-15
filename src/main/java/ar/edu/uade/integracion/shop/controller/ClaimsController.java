package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.Order;
import ar.edu.uade.integracion.shop.repository.OrderRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Api(value = "/claims", tags = "Claims admin endpoint", description = "All services for claims project are here")
public class ClaimsController {
    private OrderRepository repository;

    public ClaimsController(OrderRepository repository) {
        this.repository = repository;
    }

    @ApiOperation(value = "Retrieves order for claims admin")
    @RequestMapping(value = "/claims/orders/{id}", method = RequestMethod.GET)
    public ResponseEntity<Order> getOrder(@PathVariable Integer id) {
        return repository.findById(id).map(o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
