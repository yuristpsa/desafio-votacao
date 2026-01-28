package com.yuri.desafio.desafio.repository;

import com.yuri.desafio.desafio.entity.Voto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VotoRepository extends ReactiveCrudRepository<Voto, Long> {
    Mono<Long> countByPautaIdAndAssociadoId(Long pautaId, String associadoId);

    Flux<Voto> findByPautaId(Long pautaId);
}
