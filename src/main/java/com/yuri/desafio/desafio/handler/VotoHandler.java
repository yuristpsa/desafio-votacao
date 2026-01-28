package com.yuri.desafio.desafio.handler;

import com.yuri.desafio.desafio.dto.VotoDto;
import com.yuri.desafio.desafio.service.VotoService;
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
public class VotoHandler {
    private final VotoService service;

    public Mono<ServerResponse> save(ServerRequest request) {
        log.info("Recebendo requisição para registrar voto");
        return request.bodyToMono(VotoDto.class)
                .map(VotoDto::toEntity)
                .flatMap(service::save)
                .map(VotoDto::fromEntity)
                .doOnSuccess(dto -> log.info("Voto registrado com sucesso: {}", dto))
                .doOnError(e -> log.error("Erro ao registrar voto: {}", e.getMessage()))
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dto));
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        log.info("Buscando voto por id: {}", id);
        return service.findById(id)
                .map(VotoDto::fromEntity)
                .doOnSuccess(dto -> log.info("Voto encontrado: {}", dto))
                .doOnError(e -> log.error("Erro ao buscar voto: {}", e.getMessage()))
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        log.info("Listando todos os votos");
        Flux<VotoDto> dtos = service.findAll().map(VotoDto::fromEntity)
            .doOnNext(dto -> log.debug("Voto listado: {}", dto));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dtos, VotoDto.class);
    }

    public Mono<ServerResponse> countVotosByPautaId(ServerRequest request) {
        Long pautaId = Long.valueOf(request.pathVariable("pautaId"));
        log.info("Contando votos para pauta: {}", pautaId);
        return service.countVotosByPautaId(pautaId)
                .doOnSuccess(result -> log.info("Contagem de votos: {}", result))
                .doOnError(e -> log.error("Erro ao contar votos: {}", e.getMessage()))
                .flatMap(result -> ServerResponse.ok().bodyValue(result));
    }
}
