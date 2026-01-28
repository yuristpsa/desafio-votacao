package com.yuri.desafio.desafio.handler;

import com.yuri.desafio.desafio.dto.PautaDto;
import com.yuri.desafio.desafio.service.PautaService;
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
public class PautaHandler {
    private final PautaService pautaService;

    public Mono<ServerResponse> save(ServerRequest request) {
        log.info("Recebendo requisição para criar pauta");
        return request.bodyToMono(PautaDto.class)
                .map(PautaDto::toEntity)
                .flatMap(pautaService::save)
                .map(PautaDto::fromEntity)
                .doOnSuccess(dto -> log.info("Pauta criada com sucesso: {}", dto))
                .doOnError(e -> log.error("Erro ao criar pauta: {}", e.getMessage()))
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dto));
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        log.info("Buscando pauta por id: {}", id);
        return pautaService.findById(id)
                .map(PautaDto::fromEntity)
                .doOnSuccess(dto -> log.info("Pauta encontrada: {}", dto))
                .doOnError(e -> log.error("Erro ao buscar pauta: {}", e.getMessage()))
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        log.info("Listando todas as pautas");
        Flux<PautaDto> dtos = pautaService.findAll().map(PautaDto::fromEntity)
            .doOnNext(dto -> log.debug("Pauta listada: {}", dto));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dtos, PautaDto.class);
    }
}
