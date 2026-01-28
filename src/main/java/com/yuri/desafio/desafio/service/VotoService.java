package com.yuri.desafio.desafio.service;

import com.yuri.desafio.desafio.dto.VotosPorPautaResultDto;
import com.yuri.desafio.desafio.entity.Voto;
import com.yuri.desafio.desafio.dto.VotoResultadoEnum;
import com.yuri.desafio.desafio.repository.VotoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.Objects;

@Log4j2
@Service
public class VotoService {
    private final VotoRepository repository;
    private final PautaService pautaService;
    private final SessaoVotacaoService sessaoVotacaoService;

    public VotoService(VotoRepository repository, PautaService pautaService, SessaoVotacaoService sessaoVotacaoService) {
        this.repository = repository;
        this.pautaService = pautaService;
        this.sessaoVotacaoService = sessaoVotacaoService;
    }

    public Mono<Voto> save(Voto voto) {
        log.info("Recebendo requisição para registrar voto: {}", voto);
        if (Objects.isNull(voto.getVoto())) {
            log.warn("Voto nulo recebido");
            return Mono.error(new IllegalArgumentException("O campo 'voto' não pode ser nulo."));
        }
        if (Objects.isNull(voto.getAssociadoId()) || voto.getAssociadoId().isBlank()) {
            log.warn("AssociadoId nulo ou vazio recebido");
            return Mono.error(new IllegalArgumentException("O campo 'associadoId' não pode ser nulo ou vazio."));
        }
        if (Objects.isNull(voto.getPautaId())) {
            log.warn("PautaId nulo recebido");
            return Mono.error(new IllegalArgumentException("O campo 'pautaId' é obrigatório."));
        }

        return pautaService.findById(voto.getPautaId())
            .switchIfEmpty(Mono.error(new IllegalStateException("Pauta não encontrada.")))
            .flatMap(pauta ->
                sessaoVotacaoService.findSessaoAndamentoByPautaId(voto.getPautaId())
                    .switchIfEmpty(Mono.error(new IllegalStateException("Nenhuma sessão de votação em aberto encontrada para esta pauta.")))
                    .flatMap(sessao ->
                        repository.countByPautaIdAndAssociadoId(voto.getPautaId(), voto.getAssociadoId())
                            .flatMap(count -> {
                                if (count > 0) {
                                    log.warn("Associado {} já votou na pauta {}", voto.getAssociadoId(), voto.getPautaId());
                                    return Mono.error(new IllegalStateException("Associado já votou nesta sessão."));
                                }
                                voto.setDataVoto(LocalDateTime.now());
                                log.info("Voto registrado com sucesso: {}", voto);
                                return repository.save(voto);
                            })
                    )
            )
            .doOnError(e -> log.error("Erro ao registrar voto: {}", e.getMessage()));
    }

    public Mono<Voto> findById(Long id) {
        log.info("Buscando voto por id: {}", id);
        return repository.findById(id)
                .doOnSuccess(voto -> log.info("Voto encontrado: {}", voto))
                .doOnError(e -> log.error("Erro ao buscar voto por id: {}", e.getMessage()));
    }

    public Flux<Voto> findAll() {
        log.info("Listando todos os votos");
        return repository.findAll()
                .doOnNext(voto -> log.debug("Voto listado: {}", voto));
    }

    public Mono<VotosPorPautaResultDto> countVotosByPautaId(Long pautaId) {
        log.info("Contando votos para pauta: {}", pautaId);
        return repository.findByPautaId(pautaId)
                .collectList()
                .flatMap(votos -> {
                    if (votos.isEmpty()) {
                        log.warn("Nenhum voto encontrado para a pauta: {}", pautaId);
                        return Mono.error(new IllegalStateException("Nenhum voto encontrado para a pauta informada."));
                    }
                    long total = votos.size();
                    long sim = votos.stream().filter(v -> Boolean.TRUE.equals(v.getVoto())).count();
                    long nao = votos.stream().filter(v -> Boolean.FALSE.equals(v.getVoto())).count();
                    VotoResultadoEnum vencedor = calcularVencedor(sim, nao);
                    log.info("Contagem de votos para pauta {}: total={}, sim={}, nao={}, vencedor={}", pautaId, total, sim, nao, vencedor);
                    return Mono.just(VotosPorPautaResultDto.builder()
                        .pautaId(pautaId)
                        .totalVotos(total)
                        .totalVotosSim(sim)
                        .totalVotosNao(nao)
                        .vencedor(vencedor)
                        .build());
                })
                .doOnError(e -> log.error("Erro ao contar votos: {}", e.getMessage()));
    }

    private VotoResultadoEnum calcularVencedor(long sim, long nao) {
        if (sim > nao) return VotoResultadoEnum.SIM;
        if (nao > sim) return VotoResultadoEnum.NAO;
        return VotoResultadoEnum.EMPATE;
    }
}
