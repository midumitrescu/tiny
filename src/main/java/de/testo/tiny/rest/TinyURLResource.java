package de.testo.tiny.rest;


import de.testo.tiny.model.url.InOrder;
import de.testo.tiny.model.url.TinyURL;
import de.testo.tiny.model.url.TinyURLRequest;
import de.testo.tiny.model.url.ValidUrl;
import de.testo.tiny.service.TinyURLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
public class TinyURLResource {

    private final TinyURLService urlService;

    @Autowired
    public TinyURLResource(TinyURLService urlService) {
        this.urlService = urlService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> shortenURL(@Validated(InOrder.class) @RequestBody TinyURLRequest body) {
        return handleShortenURLFor(body.getUrl());
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Void> shortenURL(@NotNull @ValidUrl @RequestParam("url") String url) {
        return handleShortenURLFor(url);
    }

    @GetMapping("{tiny-url}")
    public RedirectView redirectToTargetURL(@NotNull @PathVariable("tiny-url") String tinyUrlParam) throws IOException {

        TinyURL tinyUrl = urlService.findTargetOf(tinyUrlParam);
        return new RedirectView(tinyUrl.getTargetURL());
    }

    private ResponseEntity<Void> handleShortenURLFor(String target) {
        TinyURL register = urlService.register(target);
        URI location = fromCurrentRequest().path(
                "/{tiny-url}").buildAndExpand(register.getTinyURL()).toUri();
        return ResponseEntity.created(location).build();
    }
}
