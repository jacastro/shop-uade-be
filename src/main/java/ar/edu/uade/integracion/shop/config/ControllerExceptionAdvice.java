package ar.edu.uade.integracion.shop.config;

import ar.edu.uade.integracion.shop.exception.ShopException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ShopException.class})
    public ResponseEntity<Object> ShopExceptionHandler(Exception ex, WebRequest request) {
        ShopException exception = (ShopException) ex;
        return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
    }
}
