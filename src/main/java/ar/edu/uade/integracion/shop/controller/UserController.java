package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.entity.User;
import ar.edu.uade.integracion.shop.entity.UserDto;
import ar.edu.uade.integracion.shop.exception.UserNotFoundException;
import ar.edu.uade.integracion.shop.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "user")
@Api(value = "/user", tags = "User", description = "User Management")
public class UserController {
    private UserRepository repository;
    private MapperFacade mapper;

    public UserController(UserRepository repository) {
        this.repository = repository;

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
    public void createUser(UserDto dto){
        User model = mapper.map(dto, User.class);

        repository.save(model);
    }
}
