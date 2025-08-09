package de.testo.tiny.url;

import de.testo.tiny.model.url.TinyURLRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static de.testo.tiny.TestRequestHelper.forMediaType;
import static de.testo.tiny.TestRequestHelper.isProblemForStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class URLValidationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void creationOfNullURL() {
        ResponseEntity<String> nullURLCreateResponse =
                restTemplate.postForEntity("/", new HttpEntity<TinyURLRequest>(null, forMediaType(APPLICATION_JSON)), String.class);
        assertThat(nullURLCreateResponse.getStatusCode()).isEqualTo(BAD_REQUEST);

        assertThat(nullURLCreateResponse.getBody(), isProblemForStatus(BAD_REQUEST));
        assertThat(nullURLCreateResponse.getBody(), hasJsonPath("$.detail", containsString("Required request body is missing")));
    }

    @Test
    public void creationOfEmptyURL() {
        ResponseEntity<String> emptyURLCreateResponse = restTemplate.postForEntity("/", new TinyURLRequest(""), String.class);
        assertThat(emptyURLCreateResponse.getStatusCode()).isEqualTo(BAD_REQUEST);

        assertThat(emptyURLCreateResponse.getBody(), isProblemForStatus(BAD_REQUEST));
        assertThat(emptyURLCreateResponse.getBody(), hasJsonPath("$.detail", containsString("URL cannot be empty")));
    }

    @Test
    public void creationOfInvalidURL() {
        ResponseEntity<String> invalidURLCreateResponse = restTemplate.postForEntity("/", new TinyURLRequest("some-url"), String.class);
        assertThat(invalidURLCreateResponse.getStatusCode()).isEqualTo(BAD_REQUEST);

        assertThat(invalidURLCreateResponse.getBody(), isProblemForStatus(BAD_REQUEST));
        assertThat(invalidURLCreateResponse.getBody(), hasJsonPath("$.detail", equalTo("some-url is not a valid URL")));
    }
}