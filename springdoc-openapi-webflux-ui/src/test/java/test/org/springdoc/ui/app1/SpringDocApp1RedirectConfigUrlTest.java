package test.org.springdoc.ui.app1;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springdoc.ui.SwaggerWelcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;


@RunWith(SpringRunner.class)
@WebFluxTest(properties = {
        "springdoc.swagger-ui.configUrl=/foo/bar",
        "springdoc.swagger-ui.url=/batz" // ignored since configUrl is configured
})
@ActiveProfiles("test")
@ContextConfiguration(classes = {SwaggerWelcome.class, SwaggerUiConfigProperties.class})
public class SpringDocApp1RedirectConfigUrlTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldRedirectWithConfigUrlIgnoringQueryParams() throws Exception {

        WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/swagger-ui.html").exchange()
                .expectStatus().isTemporaryRedirect();
        responseSpec.expectHeader()
                .value("Location", Matchers.is("/webjars/swagger-ui/index.html?configUrl=/foo/bar"));

    }

}