package com.yuri.desafio.desafio.service;

import com.yuri.desafio.desafio.entity.SessaoVotacao;
import com.yuri.desafio.desafio.repository.SessaoVotacaoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Objects;

@Log4j2
@Service
public class SessaoVotacaoService {
    private final SessaoVotacaoRepository repository;
    private final PautaService pautaService;
    private static final int DEFAULT_MINUTOS_ATIVA = 1;

    public SessaoVotacaoService(SessaoVotacaoRepository repository, PautaService pautaService) {
        this.repository = repository;
        this.pautaService = pautaService;
    }

    public Mono<SessaoVotacao> save(SessaoVotacao sessaoVotacao) {
        log.info("Recebendo requisição para criar sessão de votação: {}", sessaoVotacao);
        if (Objects.isNull(sessaoVotacao.getMinutosAtiva())) {
            sessaoVotacao.setMinutosAtiva(DEFAULT_MINUTOS_ATIVA);
        }
        return pautaService.findById(sessaoVotacao.getPautaId())
                .switchIfEmpty(Mono.error(new IllegalStateException("Pauta não encontrada.")))
                .flatMap(pauta ->
                    findSessaoAndamentoByPautaId(sessaoVotacao.getPautaId())
                        .flatMap(sessaoExistente -> {
                            log.warn("Já existe uma sessão de votação ativa para a pauta {}", sessaoVotacao.getPautaId());
                            return Mono.<SessaoVotacao>error(new IllegalStateException("Já existe uma sessão de votação ativa para esta pauta"));
                        })
                        .switchIfEmpty(Mono.defer(() -> {
                            log.info("Sessão de votação criada com sucesso: {}", sessaoVotacao);
                            return repository.save(sessaoVotacao);
                        }))
                )
                .doOnError(e -> log.error("Erro ao criar sessão de votação: {}", e.getMessage()));
    }

    public Mono<SessaoVotacao> findSessaoAndamentoByPautaId(Long pautaId) {
        log.info("Buscando sessão de votação em andamento para pauta: {}", pautaId);
        return repository.findByPautaId(pautaId)
                .filter(sessao -> !sessao.isEncerrada())
                .sort(Comparator.comparing(SessaoVotacao::getId))
                .next()
                .doOnSuccess(sessao -> log.info("Sessão em andamento encontrada: {}", sessao))
                .doOnError(e -> log.error("Erro ao buscar sessão em andamento: {}", e.getMessage()));
    }

    public Mono<SessaoVotacao> findById(Long id) {
        log.info("Buscando sessão de votação por id: {}", id);
        return repository.findById(id)
                .doOnSuccess(sessao -> log.info("Sessão encontrada: {}", sessao))
                .doOnError(e -> log.error("Erro ao buscar sessão por id: {}", e.getMessage()));
    }

    public Flux<SessaoVotacao> findAll() {
        log.info("Listando todas as sessões de votação");
        return repository.findAll()
                .doOnNext(sessao -> log.debug("Sessão listada: {}", sessao));
    }


}
