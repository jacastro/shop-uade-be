package ar.edu.uade.integracion.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public class ShopException extends RuntimeException {
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public ShopException(HttpStatus status) {
        this.status = status;
    }

    public ShopException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ShopException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public ShopException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    public ShopException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }

    public ShopException() {
    }

    public HttpStatus getStatus() {
        return status;
    }
}
