package com.github.andrielson.webfluxdemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
@RequiredArgsConstructor
public class CalculatorRouter {

    private final CalculatorHandler calculatorHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .path("calculator/{x}/{y}", this::serverResponseRouterFunction)
                .build();
    }

    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET(isOperation("+"), calculatorHandler::add)
                .GET(isOperation("-"), calculatorHandler::subtract)
                .GET(isOperation("*"), calculatorHandler::multiply)
                .GET(isOperation("/"), calculatorHandler::divide)
                .build();
    }

    private RequestPredicate isOperation(final String operation) {
        return RequestPredicates.headers(headers -> operation.equals(headers.firstHeader("OP")));
    }
}
