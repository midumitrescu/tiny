package de.testo.tiny.url;


import de.testo.tiny.DomainObjectTestMother;
import de.testo.tiny.model.url.TinyURL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class RedirectToTargetURLMockMvcTest {

    @Autowired
    private MockMvc withoutFollowRedirect;

    @Autowired
    private DomainObjectTestMother domainObjectTestMother;

    // use mock mvc to avoid following redirects on random strings
    @Test
    public void callingGetExistingTinyURL() throws Exception {

        TinyURL existing = domainObjectTestMother.randomPersistedTinyUrl();
        withoutFollowRedirect.perform(
                get("/{tiny-url}", existing.getTinyURL()))
                .andExpect(status().isFound())
                .andExpect(
                        header().string("location", existing.getTargetURL()));
    }
}
