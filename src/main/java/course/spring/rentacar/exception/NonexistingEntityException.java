package course.spring.rentacar.exception;

public class NonexistingEntityException extends RuntimeException {
    public NonexistingEntityException() {
    }

    public NonexistingEntityException(String message) {
        super(message);
    }
}
