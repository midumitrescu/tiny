package de.testo.tiny.stats;

import de.testo.tiny.DomainObjectTestMother;
import de.testo.tiny.model.metrics.TinyURLMetrics;
import de.testo.tiny.model.url.TinyURL;
import de.testo.tiny.repository.TinyURLRepository;
import de.testo.tiny.service.MetricsService;
import de.testo.tiny.service.TinyURLService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class StatsTest {

    @Autowired
    private DomainObjectTestMother domainObjectTestMother;

    @Autowired
    private TinyURLService tinyURLService;

    @Autowired
    private MetricsService metricsService;

    @Autowired
    private TinyURLRepository urlRepository;

    @Test
    public void creationOfURLsAudited() {
        TinyURL tinyURL = domainObjectTestMother.givenRandomTinyURL();

        int randomTrials = new Random().nextInt(1000);

        IntStream.range(0, randomTrials).forEach(
                index -> tinyURLService.register(tinyURL.getTargetURL())
        );

        TinyURLMetrics metricsFor = metricsService.getMetricsFor(tinyURL);
        assertThat(metricsFor.getCreates()).isEqualTo(randomTrials);
        assertThat(metricsFor.getReads()).isEqualTo(0);
    }

    @Test
    public void creationOfAlreadyExistingURLDownNotAddNewRecord() {
        int initialCount = 10;
        List<TinyURL> existing = domainObjectTestMother.givenExistingAbbreviations(initialCount);

        assertThat(urlRepository.count()).isEqualTo(initialCount);

        existing.forEach(
                tinyUrl ->
                        tinyURLService.register(tinyUrl.getTargetURL())
        );
        assertThat(urlRepository.count()).isEqualTo(initialCount);
    }

}
