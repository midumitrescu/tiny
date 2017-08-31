package de.testo.tiny.service;

import de.testo.tiny.model.TinyURL;
import de.testo.tiny.model.TinyURLNotFoundException;
import de.testo.tiny.repository.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TinyUrlService {

    private final URLRepository urlRepository;
    private final MetricsService metricsService;
    private final Abbreviations abbreviations;

    @Autowired
    public TinyUrlService(URLRepository urlRepository, MetricsService metricsService, Abbreviations abbreviations) {
        this.urlRepository = urlRepository;
        this.metricsService = metricsService;
        this.abbreviations = abbreviations;
    }

    public TinyURL findTinyUrlOf(String tinyUrl) {
        String desired = tinyUrl;
        return urlRepository.findOneByTinyURL(tinyUrl).orElseThrow(() -> new TinyURLNotFoundException(desired));
    }

    public TinyURL register(String url) {

        Optional<TinyURL> tinyUrl = urlRepository.findOne(url);
        TinyURL result = tinyUrl.orElseGet(() -> newTiny(url));
        markPlusOneCreate(result);
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

    private void markPlusOneCreate(TinyURL tinyURL) {
        metricsService.incrementCreateCounter(tinyURL);
    }
}
