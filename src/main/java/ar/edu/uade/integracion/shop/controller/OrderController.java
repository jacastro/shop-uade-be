package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.Order;
import ar.edu.uade.integracion.shop.entity.OrderDto;
import ar.edu.uade.integracion.shop.entity.User;
import ar.edu.uade.integracion.shop.exception.UserNotFoundException;
import ar.edu.uade.integracion.shop.exception.UserWithNoPermissionException;
import ar.edu.uade.integracion.shop.repository.AddressRepository;
import ar.edu.uade.integracion.shop.repository.ItemRepository;
import ar.edu.uade.integracion.shop.repository.OrderRepository;
import ar.edu.uade.integracion.shop.repository.UserRepository;
import ar.edu.uade.integracion.shop.security.JwtAuthFilter;
import ar.edu.uade.integracion.shop.service.ClaimService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@Api(value = "/orders", tags = "Orders", description = "Orders Management")
public class OrderController {

    private OrderRepository repository;
    private ItemRepository itemRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private ClaimService claimService;

    public OrderController(OrderRepository repository, ItemRepository itemRepository, UserRepository userRepository,
                           AddressRepository addressRepository, ClaimService claimService) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.claimService = claimService;
    }

    @ApiOperation(value = "Retrieves a specific order")
    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    public ResponseEntity<OrderDto> getItem(@PathVariable Integer id) {
        return repository.findById(id).map(o -> new ResponseEntity<>(map(o), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @ApiOperation(value = "Retrieves the items purchased by a user")
    @RequestMapping(value = "/orders/buyer/{id}", method = RequestMethod.GET)
    public List<OrderDto> getByBuyer(@PathVariable String id) {
        if (JwtAuthFilter.isLoggedUser(id)) {
            User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
            return repository.findByBuyer(user).stream().map(o -> map(o)).collect(Collectors.toList());
        }
        throw new UserWithNoPermissionException();
    }

    @ApiOperation(value = "Retrieves the items sold by a user")
    @RequestMapping(value = "/orders/seller/{id}", method = RequestMethod.GET)
    public List<OrderDto> getBySeller(@PathVariable String id) {
        if (JwtAuthFilter.isLoggedUser(id)) {
            User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
            return repository.findByItemSeller(user).stream().map(o -> map(o)).collect(Collectors.toList());
        }
        throw new UserWithNoPermissionException();
    }

    @ApiOperation(value = "Creates a order")
    @RequestMapping(value = "/orders/", method = RequestMethod.POST)
    public OrderDto createItem(@RequestBody OrderDto order) {
        if (JwtAuthFilter.isLoggedUser(order.getBuyerId())) {
            return map(repository.save(map(order)));
        }
        throw new UserWithNoPermissionException();
    }

    @ApiOperation(value = "Creates a claim in the claim system (external)")
    @RequestMapping(value = "/orders/{id}/claim", method = RequestMethod.POST)
    public ResponseEntity createClaim(@RequestBody String claim, @PathVariable Integer id) {
        Optional<Order> order = repository.findById(id);
        if (!order.isPresent())  {
            // Order does not exist.
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }  else if (JwtAuthFilter.isLoggedUser(order.get().getBuyer().getId())) {
            // Order existsis and claim created.
            order.ifPresent(o -> claimService.createClaim(id, o.getBuyer().getEmail(), claim, o.getBuyer().getName()));
            return new ResponseEntity(HttpStatus.OK);
        } else {
            // Order exisits but belongs to other user.
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @ApiOperation(value = "Gets claim from the claim system (external)")
    @RequestMapping(value = "/orders/{id}/claim", method = RequestMethod.GET)
    public ResponseEntity getClaim(@PathVariable Integer id) {
        Optional<Order> order = repository.findById(id);
        //if (!order.isPresent()) return new ResponseEntity(HttpStatus.NOT_FOUND);
        //if (JwtAuthFilter.isLoggedUser(order.get().getBuyer().getId())) {
            return ResponseEntity.of(Optional.of(claimService.getOrderClaims(id)));
        /*}else{
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }*/
    }
    private Order map(OrderDto dto) {
        Order model = new Order();
        if (dto.getAddress() != null) {
            model.setAddress(addressRepository.findById(dto.getAddress()).orElseThrow(
                    RuntimeException::new));
        }
        model.setBuyer(userRepository.findById(dto.getBuyerId()).orElseThrow(
                RuntimeException::new));
        model.setDate(new Date());
        model.setItem(itemRepository.findById(dto.getItemId()).orElseThrow(
                RuntimeException::new));
        model.setQuantity(dto.getQuantity());
        //model.setShippingId(); -> hay que llamar al servicio de los otros pibes
        model.setTotal(model.getItem().getPrice() * model.getQuantity());

        return model;
    }

    private OrderDto map(Order model) {
        OrderDto dto = new OrderDto();
        if (model.getAddress() != null) {
            dto.setAddress(model.getAddress().getId());
        }
        dto.setDate(model.getDate());
        dto.setId(model.getId());
        dto.setItemId(model.getItem().getId());
        dto.setQuantity(model.getQuantity());
        dto.setShippingId(model.getShippingId());
        dto.setTotal(model.getTotal());
        dto.setBuyerId(model.getBuyer().getId());
        dto.setClaims(claimService.getOrderClaims(model.getId()));
        return dto;
    }

}