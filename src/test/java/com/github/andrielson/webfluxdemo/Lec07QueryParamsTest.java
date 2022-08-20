package com.github.andrielson.webfluxdemo;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class Lec07QueryParamsTest extends BaseTest {

    @Test
    public void queryParamsTest() {

        var responseFlux = this.webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("jobs/search").query("count={count}&page={page}").build(10, 20))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}
