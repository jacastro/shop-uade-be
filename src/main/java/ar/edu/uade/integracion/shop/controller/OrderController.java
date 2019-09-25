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

    @RequestMapping(value = "/orders/", method = RequestMethod.GET)
    public ResponseEntity<Order> getOrders(
            @RequestParam(value = "userId",required = true) int userId,
            @RequestParam(value = "limit", required = false, defaultValue = "100") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset
    ) {
        Item item = new Item(1, new User(), 1, "name", "desc", 1);
        return new ResponseEntity<>(new Order(item, 1, new Address(), new User()), HttpStatus.OK);
    }

}