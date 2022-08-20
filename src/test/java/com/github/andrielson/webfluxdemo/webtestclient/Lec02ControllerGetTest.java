package com.github.andrielson.webfluxdemo.webtestclient;

import com.github.andrielson.webfluxdemo.controller.ReactiveMathController;
import com.github.andrielson.webfluxdemo.dto.Response;
import com.github.andrielson.webfluxdemo.service.ReactiveMathService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathController.class)
public class Lec02ControllerGetTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ReactiveMathService reactiveMathService;

    @Test
    public void singleResponseTest() {

        Mockito.when(reactiveMathService.findSquare(Mockito.anyInt())).thenReturn(Mono.just(new Response(25)));

        this.webClient
                .get()
                .uri("/reactive-math/square/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(response -> Assertions.assertThat(response.getOutput()).isEqualTo(25));
    }

    @Test
    public void listResponseTest() {

        var flux = Flux.range(1, 3).map(Response::new);

        Mockito.when(reactiveMathService.multiplicationTable(Mockito.anyInt())).thenReturn(flux);

        this.webClient
                .get()
                .uri("/reactive-math/table/{number}", 5)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Response.class)
                .hasSize(3);
    }
}
