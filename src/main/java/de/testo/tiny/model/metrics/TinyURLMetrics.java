package de.testo.tiny.model.metrics;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TinyURLMetrics {
    private final int reads;
    private final int creates;
}
