package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.Category;
import ar.edu.uade.integracion.shop.entity.Item;
import ar.edu.uade.integracion.shop.entity.ItemDto;
import ar.edu.uade.integracion.shop.repository.ItemRepository;
import ar.edu.uade.integracion.shop.repository.UserRepository;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
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

    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<ItemDto> getItem(@PathVariable Integer id) {
        Optional<Item> item = repository.findById(id);
        if (item.isPresent()) {
            return new ResponseEntity<>(mapper.map(item.get(), ItemDto.class), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/items/category/{category}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ItemDto>> getUserItems(
            @RequestParam(required = false, defaultValue = "100") int limit,
            @RequestParam(required = false, defaultValue = "0") int page,
            @PathVariable Category category
    ) {
        return new ResponseEntity<>(mapper.mapAsList(repository.findByCategory(category, PageRequest.of(page, limit)),
                ItemDto.class), HttpStatus.OK);
    }

    @RequestMapping(value = "/items/seller/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<ItemDto>> getUserItems(
            @RequestParam(required = false, defaultValue = "100") int limit,
            @RequestParam(required = false, defaultValue = "0") int page,
            @PathVariable Integer id
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        final List<Item> items = new ArrayList<>();
        userRepository.findById(id).ifPresent(u -> items.addAll(repository.findBySeller(u, pageable)));
        return new ResponseEntity<>(mapper.mapAsList(items, ItemDto.class), HttpStatus.OK);
    }

    @RequestMapping(value = "/items", method = RequestMethod.POST, produces = "application/json")
    public ItemDto createItem(@RequestBody ItemDto item) {
        Item model = mapper.map(item, Item.class);
        model.setId(null);
        return mapper.map(repository.save(model), ItemDto.class);
    }

    @RequestMapping(value = "/items", method = RequestMethod.PUT, produces = "application/json")
    public ItemDto updateItem(@RequestBody ItemDto item) {
        return mapper.map(repository.save(mapper.map(item, Item.class)), ItemDto.class);
    }
}
