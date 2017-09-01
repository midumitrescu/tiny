package de.testo.tiny.model.stats;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TinyURLStats {
    private final int redirects;
    private final int creates;
}
