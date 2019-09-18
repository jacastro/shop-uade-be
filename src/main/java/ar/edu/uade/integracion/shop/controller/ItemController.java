package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.Item;
import ar.edu.uade.integracion.shop.entity.ItemDto;
import ar.edu.uade.integracion.shop.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ItemController {


    @RequestMapping(value = "/items", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ItemDto>> getItems(
            @RequestParam(value = "limit", required = false, defaultValue = "100") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset
    ) {
        ArrayList<ItemDto> list = new ArrayList<>();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ItemDto>> getItem(
            @RequestParam(value = "limit", required = false, defaultValue = "100") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset
    ) {
        User user = new User();
        ArrayList<ItemDto> list = new ArrayList<>();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/seller/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ItemDto>> getUserItems(
            @PathVariable("id") int id,
            @RequestParam(value = "limit", required = false, defaultValue = "100") int limit,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset
    ) {
        ArrayList<ItemDto> list = new ArrayList<>();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/items", method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<String>  createItem(@RequestBody ItemDto item) {
        return new ResponseEntity<>("Created", HttpStatus.OK);
    }
}
