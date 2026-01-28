package com.yuri.desafio.desafio.router;

import com.yuri.desafio.desafio.handler.SessaoVotacaoHandler;
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
public class SessaoVotacaoRouter {
    @Bean
    public RouterFunction<ServerResponse> sessaoVotacaoRoutes(SessaoVotacaoHandler handler) {
        return route(POST("/api/1.0/sessao-votacao").and(accept(MediaType.APPLICATION_JSON)), handler::save)
                .andRoute(GET("/api/1.0/sessao-votacao/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::findById)
                .andRoute(GET("/api/1.0/sessao-votacao").and(accept(MediaType.APPLICATION_JSON)), handler::findAll);
    }
}

