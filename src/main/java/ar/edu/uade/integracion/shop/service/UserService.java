package ar.edu.uade.integracion.shop.service;

import ar.edu.uade.integracion.shop.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    //mock
    public void loginUser(String username, String password){
        //call user service
        throw  new UserNotFoundException();
    }
}
