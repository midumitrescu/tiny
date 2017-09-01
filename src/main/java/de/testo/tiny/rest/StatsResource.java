package de.testo.tiny.rest;

import de.testo.tiny.model.stats.TinyURLStats;
import de.testo.tiny.model.url.TinyURL;
import de.testo.tiny.service.StatsService;
import de.testo.tiny.service.TinyURLService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StatsResource {

    private final StatsService metricsService;
    private final TinyURLService tinyURLService;

    @Autowired
    public StatsResource(StatsService metricsService, TinyURLService tinyURLService) {
        this.metricsService = metricsService;
        this.tinyURLService = tinyURLService;
    }

    @GetMapping(value = "/{tiny-url}/stats", produces = "application/stats+json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TinyURLStats getStatsFor(@PathVariable("tiny-url") String tinyUrl) {

        TinyURL tinyURL = tinyURLService.findTinyUrlOf(tinyUrl);
        return metricsService.getStatsFor(tinyURL);
    }
}
