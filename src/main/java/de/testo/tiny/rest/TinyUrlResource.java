package de.testo.tiny.rest;


import de.testo.tiny.model.InOrder;
import de.testo.tiny.model.TinyUrlRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class TinyUrlResource {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createNewTinyUrl(@Validated(InOrder.class) @RequestBody TinyUrlRequest body) {

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{tiny-url}").buildAndExpand("testing").toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> createNewTinyUrl(@Validated @RequestBody MultiValueMap<String, String> body) {

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{tiny-url}").buildAndExpand("testing").toUri();
        return ResponseEntity.created(location).build();
    }
}
