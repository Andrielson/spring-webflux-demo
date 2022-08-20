package com.github.andrielson.webfluxdemo.webclient;

import com.github.andrielson.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class Lec02GetMultiResponseTest extends BaseTest {

    @Test
    public void fluxTest() {
        var responseFlux = this.webClient
                .get()
                .uri("/reactive-math/table/{number}", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void fluxStreamTest() {
        var responseFlux = this.webClient
                .get()
                .uri("/reactive-math/table/{number}/stream", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(10)
                .verifyComplete();
    }
}
