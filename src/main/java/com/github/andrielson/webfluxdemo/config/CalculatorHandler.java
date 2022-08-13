package com.github.andrielson.webfluxdemo.config;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.BiFunction;

@Service
public class CalculatorHandler {
    public Mono<ServerResponse> add(ServerRequest serverRequest) {
        return process(serverRequest, (x, y) -> ServerResponse.ok().bodyValue(response(x + y)));
    }

    public Mono<ServerResponse> subtract(ServerRequest serverRequest) {
        return process(serverRequest, (x, y) -> ServerResponse.ok().bodyValue(response(x - y)));
    }

    public Mono<ServerResponse> multiply(ServerRequest serverRequest) {
        return process(serverRequest, (x, y) -> ServerResponse.ok().bodyValue(response(x * y)));
    }

    public Mono<ServerResponse> divide(ServerRequest serverRequest) {
        return process(serverRequest, (x, y) -> y != 0 ? ServerResponse.ok().bodyValue(response(x / y)) : ServerResponse.badRequest().bodyValue("y can not be 0"));
    }

    private int getValue(ServerRequest serverRequest, String key) {
        return Integer.parseInt(serverRequest.pathVariable(key));
    }

    private Mono<ServerResponse> process(ServerRequest serverRequest, BiFunction<Integer, Integer, Mono<ServerResponse>> operation) {
        var x = getValue(serverRequest, "x");
        var y = getValue(serverRequest, "y");
        return operation.apply(x, y);
    }

    private Map<String, Integer> response(final Integer value) {
        return Map.of("value", value);
    }
}
