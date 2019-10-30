package ar.edu.uade.integracion.shop.exception;

import org.springframework.http.HttpStatus;

public class ClaimException extends ShopException {
    public ClaimException() {
        super(HttpStatus.BAD_GATEWAY);
    }
}
