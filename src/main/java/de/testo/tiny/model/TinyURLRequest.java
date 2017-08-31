package de.testo.tiny.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.validation.groups.Default;

@Value
@Validated
public class TinyURLRequest {

    @JsonProperty
    @NotEmpty(message = "URL cannot be empty", groups = Default.class)
    @ValidUrl(groups = BusinessValidationsGroup.class)
    private String url;
}
