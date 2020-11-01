package course.spring.rentacar.web.handlers;

import course.spring.rentacar.exception.InvalidEntityDataException;
import course.spring.rentacar.exception.NonexistingEntityException;
import course.spring.rentacar.exception.ValidationException;
import course.spring.rentacar.exception.util.ExceptionHandlingUtils;
import course.spring.rentacar.exception.util.ExceptionResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;


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

    @ExceptionHandler({TransactionSystemException.class, ValidationException.class})
    public ResponseEntity<ExceptionResponse> handleValidationErrors(RuntimeException rtex) {
        ValidationException ex = ExceptionHandlingUtils.extractConstraintViolationException(rtex);
        ExceptionResponse exceptionResponse = new ExceptionResponse(400,"Validation error");
        if (ex.getErrors() != null) {
            ex.getErrors().getAllErrors().forEach(err -> {
                if (err.contains(ConstraintViolation.class)) {
                    ConstraintViolation cv = err.unwrap(ConstraintViolation.class);
                    String vStr = String.format("%s[%s]: '%s' - %s", cv.getLeafBean().getClass().getSimpleName(), cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage());
                    exceptionResponse.getConstraintViolations().add(vStr);
                } else if (err.contains(Exception.class)) {
                    exceptionResponse.getExceptionMessages().add(err.unwrap(Exception.class).getMessage());
                }
            });
        } else {
            ex.getViolations().forEach(cv -> {
                String vStr = String.format("%s[%s]: '%s' - %s", cv.getLeafBean().getClass().getSimpleName(), cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage());
                exceptionResponse.getConstraintViolations().add(vStr);
            });
        }
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    //TODO implement integrity handling
//    @ExceptionHandler({DataIntegrityViolationException.class})
//    public ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(RuntimeException ex){
//
//        ValidationException exception = ExceptionHandlingUtils.extractConstraintViolationException(ex);
//
//        ExceptionResponse exceptionResponse = new ExceptionResponse(400,"Constraint violation error");
//        exceptionResponse.getExceptionMessages().add(ex.getMessage());
//
//        return ResponseEntity.badRequest().body(exceptionResponse);
//    }
}