package ar.edu.uade.integracion.shop.controller;

import ar.edu.uade.integracion.shop.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "user")
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public void login(String username, String password){
        service.loginUser(username, password);
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public String logout(){
        //hay servicio de logout?
        return "logout";
    }

}
