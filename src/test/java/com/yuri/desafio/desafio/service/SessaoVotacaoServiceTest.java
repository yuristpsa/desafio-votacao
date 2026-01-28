package com.yuri.desafio.desafio.service;

import com.yuri.desafio.desafio.entity.SessaoVotacao;
import com.yuri.desafio.desafio.mock.PautaFactory;
import com.yuri.desafio.desafio.mock.SessaoVotacaoFactory;
import com.yuri.desafio.desafio.repository.SessaoVotacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {SessaoVotacaoService.class, PautaService.class})
public class SessaoVotacaoServiceTest {

    @MockBean
    private SessaoVotacaoRepository sessaoVotacaoRepository;
    @MockBean
    private PautaService pautaService;

    @Autowired
    private SessaoVotacaoService sessaoVotacaoService;

    @Test
    void whenSave_thenReturnSavedSessao() {
        SessaoVotacao sessao = SessaoVotacaoFactory.createSessao(1L, 1L, LocalDateTime.now(), 10);
        Mockito.when(pautaService.findById(1L)).thenReturn(Mono.just(PautaFactory.createPauta(1L, "Pauta 1", "Desc 1")));
        Mockito.when(sessaoVotacaoRepository.save(any(SessaoVotacao.class))).thenReturn(Mono.just(sessao));
        Mockito.when(sessaoVotacaoRepository.findByPautaId(1L)).thenReturn(Flux.empty());
        SessaoVotacao result = sessaoVotacaoService.save(sessao).block();
        assertEquals(sessao, result);
    }

    @Test
    void whenFindById_thenReturnSessao() {
        SessaoVotacao sessao = SessaoVotacaoFactory.createSessao(1L, 1L, LocalDateTime.now(), 10);
        Mockito.when(sessaoVotacaoRepository.findById(1L)).thenReturn(Mono.just(sessao));
        SessaoVotacao result = sessaoVotacaoService.findById(1L).block();
        assertEquals(sessao, result);
    }

    @Test
    void whenFindAll_thenReturnAllSessoes() {
        List<SessaoVotacao> lista = SessaoVotacaoFactory.createList();
        Mockito.when(sessaoVotacaoRepository.findAll()).thenReturn(Flux.fromIterable(lista));
        List<SessaoVotacao> result = sessaoVotacaoService.findAll().collectList().block();
        assertEquals(lista, result);
    }
}

