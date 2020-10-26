package course.spring.rentacar.exception;

public class InvalidEntityDataException extends RuntimeException{
    public InvalidEntityDataException(String message) {
        super(message);
    }
}
