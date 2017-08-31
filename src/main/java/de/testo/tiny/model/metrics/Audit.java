package de.testo.tiny.model.metrics;

import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Value
public class Audit {

    @Id
    private String targetUrl;

    private Type type;
    private UUID trace;
}
