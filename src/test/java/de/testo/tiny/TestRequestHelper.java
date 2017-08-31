package de.testo.tiny;

import org.hamcrest.Matcher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;

public class TestRequestHelper {

    public static HttpHeaders forMediaType(MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return headers;
    }

    public static MultiValueMap<String, String> withForm(String parameter, String value) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(parameter, value);
        return map;
    }

    public static Matcher<? super Object> isProblemForStatus(HttpStatus status) {
        return allOf(
                hasJsonPath("$.status", equalTo(status.value())),
                hasJsonPath("$.title", equalTo(status.getReasonPhrase())));
    }

}
