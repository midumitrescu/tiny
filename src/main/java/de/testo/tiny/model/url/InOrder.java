package de.testo.tiny.model.url;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, URLValidationsOrder.class})
public interface InOrder {
}
