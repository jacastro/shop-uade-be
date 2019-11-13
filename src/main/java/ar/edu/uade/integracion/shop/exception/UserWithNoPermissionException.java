package ar.edu.uade.integracion.shop.exception;

import org.springframework.http.HttpStatus;

public class UserWithNoPermissionException extends ShopException {
    public UserWithNoPermissionException() {
        super("You cannot run actions for ohter users", HttpStatus.FORBIDDEN);
    }

}
