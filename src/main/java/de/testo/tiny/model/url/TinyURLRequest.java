package de.testo.tiny.model.url;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Value;
import org.springframework.validation.annotation.Validated;


@Value
@Validated
public class TinyURLRequest {

    @JsonProperty
    @NotEmpty(message = "URL cannot be empty", groups = jakarta.validation.groups.Default.class)
    @ValidUrl(groups = URLValidationsOrder.class)
    private String url;
}
