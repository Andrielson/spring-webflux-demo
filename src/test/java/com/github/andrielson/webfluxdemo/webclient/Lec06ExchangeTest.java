package com.github.andrielson.webfluxdemo.webclient;

import com.github.andrielson.webfluxdemo.dto.InputFailedValidationResponse;
import com.github.andrielson.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06ExchangeTest extends BaseTest {

    @Test
    public void badRequestTest() {
        var responseMono = this.webClient
                .get()
                .uri("reactive-math/square/{number}/throw", 5)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    private Mono<Object> exchange(ClientResponse clientResponse) {
        return (clientResponse.rawStatusCode() == 400)
                ? clientResponse.bodyToMono(InputFailedValidationResponse.class)
                : clientResponse.bodyToMono(Response.class);
    }
}
