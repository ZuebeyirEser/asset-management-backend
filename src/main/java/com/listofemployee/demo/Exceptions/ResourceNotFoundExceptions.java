package com.listofemployee.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class represents a custom exception for when a resource is not found.
 * It extends `RuntimeException` and is annotated with `@ResponseStatus(value = HttpStatus.NOT_FOUND)`
 * to automatically return a 404 Not Found status code when this exception is thrown.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundExceptions extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new `ResourceNotFoundExceptions` with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public ResourceNotFoundExceptions(String message) {
        super(message);
    }
}
