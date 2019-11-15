package ar.edu.uade.integracion.shop.exception;

import org.springframework.http.HttpStatus;

public class ItemCreationException extends ShopException {
    public ItemCreationException() {
        super("User must have at least 1 address in order to create items", HttpStatus.BAD_REQUEST);
    }
}
