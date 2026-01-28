package com.yuri.desafio.desafio.handler;

import com.yuri.desafio.desafio.dto.SessaoVotacaoDto;
import com.yuri.desafio.desafio.service.SessaoVotacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Log4j2
public class SessaoVotacaoHandler {
    private final SessaoVotacaoService service;

    public Mono<ServerResponse> save(ServerRequest request) {
        log.info("Recebendo requisição para criar sessão de votação");
        return request.bodyToMono(SessaoVotacaoDto.class)
                .map(SessaoVotacaoDto::toEntity)
                .flatMap(service::save)
                .map(SessaoVotacaoDto::fromEntity)
                .doOnSuccess(dto -> log.info("Sessão criada com sucesso: {}", dto))
                .doOnError(e -> log.error("Erro ao criar sessão: {}", e.getMessage()))
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dto));
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        log.info("Buscando sessão por id: {}", id);
        return service.findById(id)
                .map(SessaoVotacaoDto::fromEntity)
                .doOnSuccess(dto -> log.info("Sessão encontrada: {}", dto))
                .doOnError(e -> log.error("Erro ao buscar sessão: {}", e.getMessage()))
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        log.info("Listando todas as sessões de votação");
        Flux<SessaoVotacaoDto> dtos = service.findAll().map(SessaoVotacaoDto::fromEntity)
            .doOnNext(dto -> log.debug("Sessão listada: {}", dto));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dtos, SessaoVotacaoDto.class);
    }
}
