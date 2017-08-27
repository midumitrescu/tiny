package de.testo.tiny;

import de.testo.tiny.model.TinyUrlRequest;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TinyUrlResourceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void creationOfNullURL() throws IOException {
        ResponseEntity<String> nullURLCreateResponse = restTemplate.postForEntity("/", null, String.class);
        assertThat(nullURLCreateResponse.getStatusCode()).isEqualTo(BAD_REQUEST);

        assertThat(nullURLCreateResponse.getBody(), isProblemForStatus(BAD_REQUEST));
        assertThat(nullURLCreateResponse.getBody(), hasJsonPath("$.detail", containsString("Required request body is missing")));
    }

    @Test
    public void creationOfEmptyURL() throws IOException {
        ResponseEntity<String> emptyURLCreateResponse = restTemplate.postForEntity("/", new TinyUrlRequest(""), String.class);
        assertThat(emptyURLCreateResponse.getStatusCode()).isEqualTo(BAD_REQUEST);

        assertThat(emptyURLCreateResponse.getBody(), isProblemForStatus(BAD_REQUEST));
        assertThat(emptyURLCreateResponse.getBody(), hasJsonPath("$.detail", containsString("URL cannot be empty")));
    }

    @Test
    public void creationOfInvalidURL() throws IOException {
        ResponseEntity<String> invalidURLCreateResponse = restTemplate.postForEntity("/", new TinyUrlRequest("some-url"), String.class);
        assertThat(invalidURLCreateResponse.getStatusCode()).isEqualTo(BAD_REQUEST);

        assertThat(invalidURLCreateResponse.getBody(), isProblemForStatus(BAD_REQUEST));
        assertThat(invalidURLCreateResponse.getBody(), hasJsonPath("$.detail", equalTo("some-url is not a valid URL")));
    }

    private Matcher<? super Object> isProblemForStatus(HttpStatus status) {
        return allOf(
                hasJsonPath("$.status", equalTo(status.value())),
                hasJsonPath("$.title", equalTo(status.getReasonPhrase())));
    }
}