package com.yuri.desafio.desafio.service;

import com.yuri.desafio.desafio.dto.VotosPorPautaResultDto;
import com.yuri.desafio.desafio.entity.Pauta;
import com.yuri.desafio.desafio.entity.SessaoVotacao;
import com.yuri.desafio.desafio.entity.Voto;
import com.yuri.desafio.desafio.mock.PautaFactory;
import com.yuri.desafio.desafio.mock.SessaoVotacaoFactory;
import com.yuri.desafio.desafio.mock.VotoFactory;
import com.yuri.desafio.desafio.mock.VotosPorPautaResultFactory;
import com.yuri.desafio.desafio.repository.VotoRepository;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {VotoService.class, PautaService.class, SessaoVotacaoService.class})
public class VotoServiceTest {

    @MockBean
    private VotoRepository votoRepository;
    @MockBean
    private PautaService pautaService;
    @MockBean
    private SessaoVotacaoService sessaoVotacaoService;

    @Autowired
    private VotoService votoService;

    @Test
    void whenSave_thenReturnSavedVoto() {
        Voto voto = VotoFactory.createVoto(1L, 1L, "user1", true);
        Pauta pauta = PautaFactory.createPauta(1L, "Pauta 1", "Desc 1");
        SessaoVotacao sessao = SessaoVotacaoFactory.createSessao(1L, 1L, java.time.LocalDateTime.now(), 10);
        Mockito.when(pautaService.findById(1L)).thenReturn(Mono.just(pauta));
        Mockito.when(sessaoVotacaoService.findSessaoAndamentoByPautaId(1L)).thenReturn(Mono.just(sessao));
        Mockito.when(votoRepository.countByPautaIdAndAssociadoId(1L, "user1")).thenReturn(Mono.just(0L));
        Mockito.when(votoRepository.save(any(Voto.class))).thenReturn(Mono.just(voto));
        Voto result = votoService.save(voto).block();
        assertEquals(voto, result);
    }

    @Test
    void whenFindById_thenReturnVoto() {
        Voto voto = VotoFactory.createVoto(1L, 1L, "user1", true);
        Mockito.when(votoRepository.findById(1L)).thenReturn(Mono.just(voto));
        Voto result = votoService.findById(1L).block();
        assertEquals(voto, result);
    }

    @Test
    void whenFindAll_thenReturnAllVotos() {
        List<Voto> lista = VotoFactory.createList();
        Mockito.when(votoRepository.findAll()).thenReturn(Flux.fromIterable(lista));
        List<Voto> result = votoService.findAll().collectList().block();
        assertEquals(lista, result);
    }

    @Test
    void whenCountVotosByPautaId_thenReturnResult() {
        List<Voto> lista = VotoFactory.createList();
        Mockito.when(votoRepository.findByPautaId(1L)).thenReturn(Flux.fromIterable(lista));
        VotosPorPautaResultDto expected = VotosPorPautaResultFactory.createEmpate();
        VotosPorPautaResultDto result = votoService.countVotosByPautaId(1L).block();
        assertEquals(expected.getTotalVotos(), result.getTotalVotos());
        assertEquals(expected.getVencedor(), result.getVencedor());
    }
}

