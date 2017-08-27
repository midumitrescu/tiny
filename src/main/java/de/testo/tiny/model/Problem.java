package de.testo.tiny.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

// RFC 7807
@Value
@Builder
public class Problem {

    private int status;
    private String title;
    private String detail;

    public static class ProblemBuilder {

        public ProblemBuilder statusCode(HttpStatus status) {
            return this.status(status.value()).title(status.getReasonPhrase());
        }
    }

}
