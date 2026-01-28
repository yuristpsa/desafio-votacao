package com.yuri.desafio.desafio.service;

import com.yuri.desafio.desafio.entity.Pauta;
import com.yuri.desafio.desafio.repository.PautaRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class PautaService {
    private final PautaRepository pautaRepository;

    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public Mono<Pauta> save(Pauta pauta) {
        log.info("Recebendo requisição para criar pauta: {}", pauta);
        return pautaRepository.save(pauta)
                .doOnSuccess(saved -> log.info("Pauta criada com sucesso: {}", saved))
                .doOnError(e -> log.error("Erro ao criar pauta: {}", e.getMessage()));
    }

    public Mono<Pauta> findById(Long id) {
        log.info("Buscando pauta por id: {}", id);
        return pautaRepository.findById(id)
                .doOnSuccess(pauta -> log.info("Pauta encontrada: {}", pauta))
                .doOnError(e -> log.error("Erro ao buscar pauta por id: {}", e.getMessage()));
    }

    public Flux<Pauta> findAll() {
        log.info("Listando todas as pautas");
        return pautaRepository.findAll()
                .doOnNext(pauta -> log.debug("Pauta listada: {}", pauta));
    }
}
