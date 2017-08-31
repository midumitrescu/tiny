package de.testo.tiny.config;

import de.testo.tiny.repository.URLRepository;
import de.testo.tiny.service.Abbreviations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AbbreviationConfig {

    @Autowired
    private URLRepository repository;

    @Bean
    public Abbreviations abbreviations() {
        return new Abbreviations(repository.count());
    }
}
