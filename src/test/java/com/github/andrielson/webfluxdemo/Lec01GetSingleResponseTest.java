package com.github.andrielson.webfluxdemo;

import com.github.andrielson.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class Lec01GetSingleResponseTest extends BaseTest {

    @Test
    public void blockTest() {
        var response = this.webClient
                .get()
                .uri("/reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .block();

        Assertions.assertEquals(response.getOutput(), 25);
    }

    @Test
    public void subscribeTest() {
        this.webClient
                .get()
                .uri("/reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .subscribe(response -> Assertions.assertEquals(response.getOutput(), 25));
    }

    @Test
    public void stepVerifierTest() {
        var responseMono = this.webClient
                .get()
                .uri("/reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class);

        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.getOutput() == 25)
                .verifyComplete();
    }
}
