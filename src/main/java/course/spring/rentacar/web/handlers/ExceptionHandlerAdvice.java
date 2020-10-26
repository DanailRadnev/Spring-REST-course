package course.spring.rentacar.web.handlers;

import course.spring.rentacar.exception.InvalidEntityDataException;
import course.spring.rentacar.exception.NonexistingEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({NonexistingEntityException.class, InvalidEntityDataException.class})
    public ResponseEntity<ExceptionResponse> handleInvalidEntityData(Exception ex) {
        if (ex instanceof InvalidEntityDataException) {
            return ResponseEntity.badRequest().body(new ExceptionResponse(400, ex.getMessage()));
        } else {
            return ResponseEntity.status(404).body(new ExceptionResponse(404, ex.getMessage()));
        }
    }
}