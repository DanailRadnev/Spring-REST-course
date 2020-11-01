package course.spring.rentacar.exception.util;

import course.spring.rentacar.exception.ValidationException;

import javax.validation.ConstraintViolationException;

public class ExceptionHandlingUtils {
    public static void handleConstraintViolationException(RuntimeException e) throws RuntimeException {
        ValidationException ex = extractConstraintViolationException(e);
        if(ex  != null) {
            throw ex;
        } else {
            throw e;
        }
    }
    public static ValidationException extractConstraintViolationException(RuntimeException e) throws RuntimeException {
        if(e instanceof  ValidationException) {
            return (ValidationException) e;
        }
        Throwable ex = e;
        while(ex.getCause() != null && !(ex instanceof ConstraintViolationException) ) {
            ex = ex.getCause();
        }
        if(ex instanceof ConstraintViolationException) {
            ConstraintViolationException cvex = (ConstraintViolationException) ex;
            return new ValidationException(cvex.getConstraintViolations());
        } else {
            return null;
        }
    }

    public static void handleDataIntegrityViolationException(RuntimeException e) throws RuntimeException {
        ValidationException ex = extractDataIntegrityViolationException(e);
        if(ex  != null) {
            throw ex;
        } else {
            throw e;
        }
    }

    public static ValidationException extractDataIntegrityViolationException(RuntimeException e) throws RuntimeException {
        if(e instanceof  ValidationException) {
            return (ValidationException) e;
        }
        Throwable ex = e;
        while(ex.getCause() != null && !(ex instanceof org.hibernate.exception.ConstraintViolationException) ) {
            ex = ex.getCause();
        }
        if(ex instanceof org.hibernate.exception.ConstraintViolationException) {
            ConstraintViolationException cvex = (ConstraintViolationException) ex;
            return new ValidationException(cvex.getConstraintViolations());
        } else {
            return null;
        }
    }
}
