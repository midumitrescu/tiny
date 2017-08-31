package de.testo.tiny.service;

import de.testo.tiny.DomainObjectTestMother;
import de.testo.tiny.model.TinyURL;
import de.testo.tiny.repository.URLRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@Transactional
public class TinyUrlServiceTest {

    @Autowired
    private DomainObjectTestMother domainObjectTestMother;

    @Autowired
    private TinyUrlService objectUnderTest;

    @Autowired
    private URLRepository urlRepository;

    @Test
    public void creationOfNewURLDownIncreasesRecord() {
        int initialCount = 10;
        domainObjectTestMother.givenExistingAbbreviations(initialCount);

        objectUnderTest.register(domainObjectTestMother.randomURL());

        assertThat(urlRepository.count()).isEqualTo(initialCount + 1);
    }

    @Test
    public void creationOfAlreadyExistingURLDownNotAddNewRecord() {
        int initialCount = 10;
        List<TinyURL> existing = domainObjectTestMother.givenExistingAbbreviations(initialCount);

        existing.forEach(
                tinyUrl ->
                        objectUnderTest.register(tinyUrl.getTargetURL())
        );
        assertThat(urlRepository.count()).isEqualTo(initialCount);
    }
}