package de.testo.tiny.service;

import de.testo.tiny.model.url.TinyURL;
import de.testo.tiny.model.url.TinyURLNotFoundException;
import de.testo.tiny.repository.TinyURLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TinyURLService {

    private final TinyURLRepository urlRepository;
    private final StatsService metricsService;
    private final Abbreviations abbreviations;

    @Autowired
    public TinyURLService(TinyURLRepository urlRepository, StatsService metricsService, Abbreviations abbreviations) {
        this.urlRepository = urlRepository;
        this.metricsService = metricsService;
        this.abbreviations = abbreviations;
    }

    public TinyURL findTinyUrlOf(String tinyUrl) {
        return urlRepository.findOneByTinyURL(tinyUrl).orElseThrow(() -> new TinyURLNotFoundException(tinyUrl));
    }

    public TinyURL findTargetOf(String tinyUrl) {
        TinyURL tinyURL = findTinyUrlOf(tinyUrl);
        this.incrementReads(tinyURL);
        return tinyURL;
    }

    public TinyURL register(String url) {

        Optional<TinyURL> tinyUrl = urlRepository.findOneByTargetURL(url);
        TinyURL result = tinyUrl.orElseGet(() -> newTiny(url));
        incrementWrites(result);
        return result;
    }

    private TinyURL newTiny(String url) {
        TinyURL newTiny = TinyURL.builder()
                .targetURL(url)
                .tinyURL(abbreviations.next())
                .build();
        urlRepository.save(newTiny);
        return newTiny;
    }

    private void incrementWrites(TinyURL tinyURL) {
        metricsService.incrementCreateCounter(tinyURL);
    }

    private void incrementReads(TinyURL tinyURL) {
        metricsService.incrementReadCounter(tinyURL);
    }
}
