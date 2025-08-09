package de.testo.tiny.url;

import de.testo.tiny.url.matchers.CreationResponseMatcher;
import de.testo.tiny.DomainObjectTestMother;
import de.testo.tiny.model.url.TinyURL;
import de.testo.tiny.model.url.TinyURLRequest;
import de.testo.tiny.service.TinyURLService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.util.MultiValueMap;

import static de.testo.tiny.TestRequestHelper.forMediaType;
import static de.testo.tiny.TestRequestHelper.withForm;
import static org.mockito.Mockito.reset;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CreateTinyURLTest {

    @Autowired
    private DomainObjectTestMother domainObjectTestMother;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockitoBean
    private TinyURLService urlService;

    @AfterEach
    public void resetMocks() {
        reset(urlService);
    }

    @Test
    public void creationOfNewValidURL() {

        TinyURL newUrl = givenServiceReturnsTinyUrl();

        CreationResponseMatcher.on(restTemplate.postForEntity("/", new TinyURLRequest(newUrl.getTargetURL()), String.class))
                .assertStatusCodeCreated()
                .assertNoBody()
                .assertTinyTargetIs(newUrl.getTinyURL());

    }

    @Test
    public void creationViaFormSubmit() {

        TinyURL newUrl = givenServiceReturnsTinyUrl();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(withForm("url", newUrl.getTargetURL()),
                forMediaType(MediaType.APPLICATION_FORM_URLENCODED));

        CreationResponseMatcher.on(restTemplate.postForEntity("/", request, String.class))
                .assertStatusCodeCreated()
                .assertNoBody()
                .assertTinyTargetIs(newUrl.getTinyURL());
    }

    private TinyURL givenServiceReturnsTinyUrl() {
        TinyURL newUrl = domainObjectTestMother.givenRandomTinyURL();
        Mockito.when(urlService.register(newUrl.getTargetURL())).thenReturn(newUrl);
        return newUrl;
    }
}