package de.testo.tiny.url.matchers;

import lombok.AllArgsConstructor;
import org.hamcrest.MatcherAssert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;

@AllArgsConstructor(staticName = "on")
public class RedirectResponseMatcher {

    private ResponseEntity<String> assertOn;

    public RedirectResponseMatcher assertStatusCodeFound() {
        assertThat(assertOn.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        return this;
    }

    public RedirectResponseMatcher assertNoBody() {
        MatcherAssert.assertThat(assertOn.getBody(), isEmptyOrNullString());
        return this;
    }
}
