package de.testo.tiny.model.url;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
