package com.yuri.desafio.desafio.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.yuri.desafio.desafio.entity.SessaoVotacao;
import reactor.core.publisher.Flux;

public interface SessaoVotacaoRepository extends ReactiveCrudRepository<SessaoVotacao, Long> {
    Flux<SessaoVotacao> findByPautaId(Long pautaId);
}
