package de.testo.tiny.model;

import lombok.Value;

@Value
public class TinyURLNotFoundException extends RuntimeException {
    private final String notExistingTinyURL;
}
