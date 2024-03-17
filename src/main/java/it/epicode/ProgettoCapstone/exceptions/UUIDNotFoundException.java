package it.epicode.ProgettoCapstone.exceptions;

import java.util.UUID;

public class UUIDNotFoundException extends RuntimeException {
    public UUIDNotFoundException(UUID id) {
        super("User with  " + id + " was not found");
    }
}