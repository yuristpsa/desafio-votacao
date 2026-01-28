package com.yuri.desafio.desafio.router;

import com.yuri.desafio.desafio.handler.VotoHandler;
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
public class VotoRouter {
    @Bean
    public RouterFunction<ServerResponse> votoRoutes(VotoHandler handler) {
        return route(POST("/api/1.0/votos").and(accept(MediaType.APPLICATION_JSON)), handler::save)
                .andRoute(GET("/api/1.0/votos/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::findById)
                .andRoute(GET("/api/1.0/votos").and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
                .andRoute(GET("/api/1.0/votos/contagem/{pautaId}").and(accept(MediaType.APPLICATION_JSON)), handler::countVotosByPautaId);
    }
}
