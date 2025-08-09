package de.testo.tiny.model.url;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TinyURL {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    @Column(unique = true)
    private String targetURL;

    @Column(unique = true)
    private String tinyURL;
}
