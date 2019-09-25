package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.Address;
import ar.edu.uade.integracion.shop.entity.Item;
import ar.edu.uade.integracion.shop.entity.Order;
import ar.edu.uade.integracion.shop.entity.User;
import ar.edu.uade.integracion.shop.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private OrderRepository repository;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    public ResponseEntity<Order> getItem(@PathVariable Integer id) {
        return repository.findById(id).map(o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/orders/", method = RequestMethod.POST)
    public void createItem(@RequestBody Order order) {
        repository.save(order);
    }
}