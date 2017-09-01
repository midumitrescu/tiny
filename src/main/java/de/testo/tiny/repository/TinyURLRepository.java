package de.testo.tiny.repository;

import de.testo.tiny.model.url.TinyURL;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface TinyURLRepository extends Repository<TinyURL, Long> {

    List<TinyURL> findAll();
    Optional<TinyURL> findOneByTargetURL(String targetURL);
    Optional<TinyURL> findOneByTinyURL(String tinyURL);
    TinyURL save(TinyURL tinyURL);
    int count();
}
