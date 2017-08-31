package de.testo.tiny.url.matchers;

import lombok.AllArgsConstructor;
import org.hamcrest.MatcherAssert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;

@AllArgsConstructor(staticName = "on")
public class CreationResponseMatcher {

    private ResponseEntity<String> assertOn;

    public CreationResponseMatcher assertStatusCodeCreated() {
        assertThat(assertOn.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        return this;
    }

    public CreationResponseMatcher assertNoBody() {
        MatcherAssert.assertThat(assertOn.getBody(), isEmptyOrNullString());
        return this;
    }

   public CreationResponseMatcher assertTinyTargetIs(String target) {
       assertThat(assertOn.getHeaders().getLocation()).hasHost("localhost");
       assertThat(assertOn.getHeaders().getLocation()).hasPath("/" + target);
       return this;
   }
}
