package ar.edu.uade.integracion.shop.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ShopException{
    public UserNotFoundException() {
        super("User not found", HttpStatus.NOT_FOUND);
    }
}
