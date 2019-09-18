package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.Address;
import ar.edu.uade.integracion.shop.entity.Item;
import ar.edu.uade.integracion.shop.entity.Order;
import ar.edu.uade.integracion.shop.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {


    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    public ResponseEntity<Order> getItem() {
        Item item = new Item(1, new User(), 1, "name", "desc", 1);
        return new ResponseEntity<>(new Order(item, 1, new Address(), new User()), HttpStatus.OK);
    }

    @RequestMapping(value = "/orders/", method = RequestMethod.POST)
    public ResponseEntity<String> createItem(@RequestBody Order order) {
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
}