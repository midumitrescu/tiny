package de.testo.tiny;


import de.testo.tiny.model.TinyURL;
import de.testo.tiny.repository.URLRepository;
import de.testo.tiny.service.Abbreviations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DomainObjectTestMother {

    @Autowired(required = false)
    private URLRepository urlRepository;

    @Autowired
    private Abbreviations abbreviations;

    public List<TinyURL> givenExistingAbbreviations(int count) {
            return IntStream.rangeClosed(1, count)
                    .mapToObj(
                        index -> urlRepository.save(randomTinyUrl()))
                    .collect(Collectors.toList());
        }

    public TinyURL randomTinyUrl() {
        return TinyURL.builder().targetURL(randomURL()).tinyURL(abbreviations.next()).build();
    }

    public TinyURL randomPersistedTinyUrl() {
        TinyURL random = randomTinyUrl();
        urlRepository.save(random);
        return random;
    }

    public String randomURL() {
        return "http://" + UUID.randomUUID() + ".test.com";
    }


}
