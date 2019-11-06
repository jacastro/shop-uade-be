package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.Category;
import ar.edu.uade.integracion.shop.entity.Item;
import ar.edu.uade.integracion.shop.entity.ItemDto;
import ar.edu.uade.integracion.shop.repository.ItemRepository;
import ar.edu.uade.integracion.shop.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@Api(value = "/items", tags = "Items", description = "Items management")
public class ItemController {
    private ItemRepository repository;
    private UserRepository userRepository;
    private MapperFacade mapper;

    public ItemController(ItemRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.classMap(Item.class, ItemDto.class).byDefault().register();
        mapper = mapperFactory.getMapperFacade();
    }


    @ApiOperation(value = "Retrieves items that are available for sale")
    @RequestMapping(value = "/items", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ItemDto>> getItems(
            @RequestParam(required = false, defaultValue = "100") int limit,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) String q
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        List<Item> items;
        if (q != null) {
            items = repository.findByNameContainsOrDescriptionContains(q, q, pageable);
        } else {
            items = repository.findAll(pageable).getContent();
        }
        return new ResponseEntity<>(mapper.mapAsList(items, ItemDto.class), HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves a specific item", response = ItemDto.class)
    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ItemDto> getItem(@PathVariable Integer id) {
        Optional<Item> item = repository.findById(id);
        if (item.isPresent()) {
            return new ResponseEntity<>(mapper.map(item.get(), ItemDto.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Retrieves the items of a specific category")
    @RequestMapping(value = "/items/category/{category}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ItemDto>> getUserItems(
            @RequestParam(required = false, defaultValue = "100") int limit,
            @RequestParam(required = false, defaultValue = "0") int page,
            @PathVariable Category category
    ) {
        return new ResponseEntity<>(mapper.mapAsList(repository.findByCategory(category, PageRequest.of(page, limit)),
                ItemDto.class), HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves the items published by a specific user")
    @RequestMapping(value = "/items/seller/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ItemDto>> getUserItems(
            @RequestParam(required = false, defaultValue = "100") int limit,
            @RequestParam(required = false, defaultValue = "0") int page,
            @PathVariable String id
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        final List<Item> items = new ArrayList<>();
        userRepository.findById(id).ifPresent(u -> items.addAll(repository.findBySeller(u, pageable)));
        return new ResponseEntity<>(mapper.mapAsList(items, ItemDto.class), HttpStatus.OK);
    }

    @ApiOperation(value = "Create a item")
    @RequestMapping(value = "/items", method = RequestMethod.POST, produces = "application/json")
    public ItemDto createItem(@RequestBody ItemDto item) {
        // TODO : Valiadte user modifies his items.
        Item model = mapper.map(item, Item.class);
        model.setId(null);
        return mapper.map(repository.save(model), ItemDto.class);
    }

    @ApiOperation(value = "Updates a item")
    @RequestMapping(value = "/items", method = RequestMethod.PUT, produces = "application/json")
    public ItemDto updateItem(@RequestBody ItemDto item) {
        // TODO : Valiadte user modifies his items.

        return mapper.map(repository.save(mapper.map(item, Item.class)), ItemDto.class);
    }
}
