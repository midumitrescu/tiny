package de.testo.tiny.model.stats;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Event {

    @Id
    private UUID trace;

    private String targetURL;
    private Type type;
}
