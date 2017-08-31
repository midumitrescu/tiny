package de.testo.tiny.repository;

import de.testo.tiny.model.TinyURL;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface URLRepository extends Repository<TinyURL, String> {

    Optional<TinyURL> findOne(String id);
    Optional<TinyURL> findOneByTinyURL(String tinyURL);
    List<TinyURL> findAll();
    TinyURL save(TinyURL tinyURL);
    int count();
}
