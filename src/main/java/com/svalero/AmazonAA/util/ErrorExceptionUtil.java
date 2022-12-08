package com.svalero.AmazonAA.util;

import com.svalero.AmazonAA.exception.ErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

public class ErrorExceptionUtil {
    public static ResponseEntity<ErrorException> getErrorExceptionResponseEntity(ConstraintViolationException cve) {
        Map<String, String> errors = new HashMap<>();
        cve.getConstraintViolations().forEach(error -> {
            String fieldName = error.getMessage();
            String name = error.toString();
            errors.put(fieldName, name);
        });
        ErrorException errorException = new ErrorException(500, "Internal Server Error", errors);
        return new ResponseEntity<>(errorException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
