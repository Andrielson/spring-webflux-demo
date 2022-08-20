package com.github.andrielson.webfluxdemo.webclient;

import com.github.andrielson.webfluxdemo.dto.MultiplyRequestDto;
import com.github.andrielson.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class Lec08AttributesTest extends BaseTest {

    @Test
    public void basicAuthTest() {
        var responseMono = this.webClient
                .post()
                .uri("/reactive-math/multiply")
                .bodyValue(buildRequestDto(5, 2))
                .attribute("auth", "basic")
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void oAuthTest() {
        var responseMono = this.webClient
                .post()
                .uri("/reactive-math/multiply")
                .bodyValue(buildRequestDto(5, 2))
                .attribute("auth", "oauth")
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void noAuthTest() {
        var responseMono = this.webClient
                .post()
                .uri("/reactive-math/multiply")
                .bodyValue(buildRequestDto(5, 2))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(System.out::println);

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    private MultiplyRequestDto buildRequestDto(int a, int b) {
        MultiplyRequestDto dto = new MultiplyRequestDto();
        dto.setFirst(a);
        dto.setSecond(b);
        return dto;
    }
}
