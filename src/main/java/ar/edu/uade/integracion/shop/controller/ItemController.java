package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.Item;
import ar.edu.uade.integracion.shop.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ItemController {


    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getItems(
            @RequestParam(value = "limit", required = false, defaultValue = "100") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset
    ) {
        User user = new User();
        ArrayList<Item> list = new ArrayList<>();
        ArrayList<String> photos = new ArrayList<>();
        Item item = new Item(1, user, 1,"test", "test description", photos );
        list.add(item);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/user", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getUserItems(
            @RequestParam(value = "userId", required = true) int id,
            @RequestParam(value = "limit", required = false, defaultValue = "100") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset
    ) {
        ArrayList<Item> list = new ArrayList<>();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public ResponseEntity<String>  createItem(@RequestBody Item item) {
        return new ResponseEntity<>("Created", HttpStatus.OK);
    }

}
