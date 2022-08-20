package com.github.andrielson.webfluxdemo.webtestclient;

import com.github.andrielson.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.test.StepVerifier;

public class Lec01SimpleWebTestClientTest extends BaseTest {

    @Test
    public void stepVerifierTest() {
        var responseFlux = this.webClient
                .get()
                .uri("/reactive-math/square/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Response.class)
                .getResponseBody();

        StepVerifier.create(responseFlux)
                .expectNextMatches(response -> response.getOutput() == 25)
                .verifyComplete();
    }
}
