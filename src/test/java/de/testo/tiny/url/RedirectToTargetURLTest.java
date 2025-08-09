package de.testo.tiny.url;


import de.testo.tiny.DomainObjectTestMother;
import de.testo.tiny.model.url.TinyURL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static de.testo.tiny.TestRequestHelper.isProblemForStatus;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RedirectToTargetURLTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DomainObjectTestMother domainObjectTestMother;

    @Test
    public void callingNotExistingTinyURL() throws Exception {

        TinyURL existing = domainObjectTestMother.randomPersistedTinyUrl();
        TinyURL notExisting = domainObjectTestMother.givenRandomTinyURL();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/{tiny-url}", String.class, notExisting.getTinyURL());

        assertThat(responseEntity.getStatusCode(), equalTo(NOT_FOUND));
        assertThat(responseEntity.getBody(), isProblemForStatus(NOT_FOUND));
    }
}
