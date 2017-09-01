package de.testo.tiny.rest;

import de.testo.tiny.model.metrics.TinyURLMetrics;
import de.testo.tiny.model.url.TinyURL;
import de.testo.tiny.service.MetricsService;
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

    private final MetricsService metricsService;
    private final TinyURLService tinyURLService;

    @Autowired
    public StatsResource(MetricsService metricsService, TinyURLService tinyURLService) {
        this.metricsService = metricsService;
        this.tinyURLService = tinyURLService;
    }

    @GetMapping(value = "/{tiny-url}/stats")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TinyURLMetrics getStatsFor(@PathVariable("tiny-url") String tinyUrl) {

        TinyURL tinyURL = tinyURLService.findTinyUrlOf(tinyUrl);
        return metricsService.getMetricsFor(tinyURL);
    }
}
