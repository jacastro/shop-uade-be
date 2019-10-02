package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.Address;
import ar.edu.uade.integracion.shop.entity.User;
import ar.edu.uade.integracion.shop.entity.UserDto;
import ar.edu.uade.integracion.shop.exception.UserNotFoundException;
import ar.edu.uade.integracion.shop.repository.AddressRepository;
import ar.edu.uade.integracion.shop.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping(value = "user")
@Api(value = "/user", tags = "User", description = "User Management")
public class UserController {
    private UserRepository repository;
    private AddressRepository addressRepository;
    private MapperFacade mapper;

    public UserController(UserRepository repository, AddressRepository addressRepository) {
        this.repository = repository;
        this.addressRepository = addressRepository;

        createMapper();
    }

    private void createMapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.classMap(User.class, UserDto.class).byDefault().register();
        mapper = mapperFactory.getMapperFacade();
    }


    @ApiOperation(value = "Retrieves the information of a user")
    @GetMapping("{id}")
    public UserDto getUser(@PathVariable String id) {
        return mapper.map(repository.findById(id).orElseThrow(UserNotFoundException::new), UserDto.class);
    }

    @PostMapping
    @ApiOperation(value = "Creates a user")
    public void createUser(UserDto dto) {
        User model = mapper.map(dto, User.class);

        repository.save(model);
    }

    @PostMapping(path = "/{userId}/address")
    @ApiOperation(value = "Adds an address to a user")
    public UserDto createAddress(@PathVariable String userId, @RequestBody Address address) {
        Optional<User> user = repository.findById(userId);
        checkUser(user);

        user.get().addAddress(address);
        addressRepository.save(address);
        return mapper.map(repository.save(user.get()), UserDto.class);
    }

    @GetMapping(path = "/{userId}/address")
    @ApiOperation(value = "Gets a user address list")
    public List<Address> getUserAddress(@PathVariable String userId) {
        Optional<User> user = repository.findById(userId);
        checkUser(user);
        return user.get().getAddresses();
    }

    private void checkUser(Optional<User> user) {
        if (!user.isPresent()) {
            throw new UserNotFoundException();
        }
    }
}
