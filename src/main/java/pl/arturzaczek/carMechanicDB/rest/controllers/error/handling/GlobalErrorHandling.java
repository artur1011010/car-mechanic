package pl.arturzaczek.carMechanicDB.rest.controllers.error.handling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.arturzaczek.carMechanicDB.rest.error.ApiErrorResponse;

@ControllerAdvice
public class GlobalErrorHandling extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(ApiErrorResponse
                .builder()
                .code(HttpStatus.BAD_REQUEST.name())
                .message("Request validation failed")
                .details(ex.getMessage())
                .build());
    }
}
