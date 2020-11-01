package course.spring.rentacar.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolation;
import java.util.Set;
@Getter
@Setter
@ToString
public class ValidationException extends RuntimeException{
    private Errors errors;
    private Set<ConstraintViolation<?>> violations;
    public ValidationException() {
    }
    public ValidationException(Errors errors) {
        this.errors = errors;
    }
    public ValidationException(Set<ConstraintViolation<?>> violations) {
        this.violations = violations;
    }
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
