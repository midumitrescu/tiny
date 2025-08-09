package de.testo.tiny.stats;

import de.testo.tiny.DomainObjectTestMother;
import de.testo.tiny.model.url.TinyURLRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatsRestTest {

    @Autowired
    private DomainObjectTestMother domainObjectTestMother;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void creationOfURLsAudited() {
        String url = domainObjectTestMother.randomURL();

        int randomTrials = new Random().nextInt(1000);
        Set<String> allReturnedLocations = IntStream.range(0, randomTrials)
                .mapToObj(index -> restTemplate.postForEntity("/", new TinyURLRequest(url), String.class))
                .map(this::extractLocationHeader)
                .collect(toSet());

        assertThat(allReturnedLocations, hasSize(1));

        String location = allReturnedLocations.iterator().next();

        ResponseEntity<String> forEntity = restTemplate.getForEntity(location + "/stats", String.class);

        assertThat(forEntity.getBody(), hasJsonPath("$.creates", equalTo(randomTrials)));
    }

    @Test
    public void readsOfURLsAudited() {
        String url = domainObjectTestMother.randomURL();

        ResponseEntity<String> createdTinyUrl = restTemplate.postForEntity("/", new TinyURLRequest(url), String.class);
        String location = extractLocationHeader(createdTinyUrl);

        int numberOfReads = new Random().nextInt(1000);
        IntStream.range(0, numberOfReads)
                .forEach(index -> restTemplate.getForEntity(location, String.class));


        ResponseEntity<String> forEntity = restTemplate.getForEntity(location + "/stats", String.class);

        assertThat(forEntity.getBody(), hasJsonPath("$.creates", equalTo(1)));
        assertThat(forEntity.getBody(), hasJsonPath("$.redirects", equalTo(numberOfReads)));
    }

    private String extractLocationHeader(ResponseEntity<String> createResponse) {
        return createResponse.getHeaders().get(HttpHeaders.LOCATION).get(0);
    }

}
