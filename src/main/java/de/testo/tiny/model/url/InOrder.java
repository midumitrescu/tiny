package de.testo.tiny.model.url;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;

@GroupSequence({Default.class, URLValidationsOrder.class})
public interface InOrder {
}
