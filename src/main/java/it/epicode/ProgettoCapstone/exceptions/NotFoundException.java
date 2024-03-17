package it.epicode.ProgettoCapstone.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("Element with id " + id + " not found");
    }

    public NotFoundException(UUID id) {
        super("Element with uuid " + id + " not found");
    }

    public NotFoundException(String element) {
        super("Element: " + element + " not found");
    }
}
