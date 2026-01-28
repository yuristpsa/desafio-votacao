package com.yuri.desafio.desafio.router;

import com.yuri.desafio.desafio.handler.PautaHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
@Log4j2
public class PautaRouter {
    @Bean
    public RouterFunction<ServerResponse> pautaRoutes(PautaHandler pautaHandler) {
        return route(POST("/api/1.0/pautas").and(accept(MediaType.APPLICATION_JSON)), pautaHandler::save)
                .andRoute(GET("/api/1.0/pautas/{id}").and(accept(MediaType.APPLICATION_JSON)), pautaHandler::findById)
                .andRoute(GET("/api/1.0/pautas").and(accept(MediaType.APPLICATION_JSON)), pautaHandler::findAll);
    }
}
