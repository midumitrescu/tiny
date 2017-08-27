package de.testo.tiny.model;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, BusinessValidationsGroup.class})
public interface InOrder {
}
