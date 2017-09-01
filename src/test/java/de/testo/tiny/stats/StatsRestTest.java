package de.testo.tiny.stats;

import de.testo.tiny.DomainObjectTestMother;
import de.testo.tiny.model.url.TinyURLRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
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
                .map(stringResponseEntity -> stringResponseEntity.getHeaders().get(HttpHeaders.LOCATION))
                .flatMap(List::stream)
                .collect(Collectors.toSet());

        assertThat(allReturnedLocations, hasSize(1));

        String location = allReturnedLocations.iterator().next();

        ResponseEntity<String> forEntity = restTemplate.getForEntity(location + "/stats", String.class);

        assertThat(forEntity.getBody(), hasJsonPath("$.creates", equalTo(randomTrials)));
    }

}
