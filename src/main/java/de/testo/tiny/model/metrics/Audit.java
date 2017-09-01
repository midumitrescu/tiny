package de.testo.tiny.model.metrics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Audit {

    @Id
    private String uuidTrace;

    private String targetURL;
    private Type type;
}
