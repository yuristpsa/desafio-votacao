package com.yuri.desafio.desafio.service;

import com.yuri.desafio.desafio.entity.Pauta;
import com.yuri.desafio.desafio.mock.PautaFactory;
import com.yuri.desafio.desafio.repository.PautaRepository;
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
@ContextConfiguration(classes = {PautaService.class})
public class PautaServiceTest {

    @MockBean
    private PautaRepository pautaRepository;

    @Autowired
    private PautaService pautaService;

    @Test
    void whenSave_thenReturnSavedPauta() {
        Pauta pauta = PautaFactory.createPauta(1L, "Pauta Teste", "Descricao Teste");
        Mockito.when(pautaRepository.save(any(Pauta.class))).thenReturn(Mono.just(pauta));
        Pauta result = pautaService.save(pauta).block();
        assertEquals(pauta, result);
    }

    @Test
    void whenFindById_thenReturnPauta() {
        Pauta pauta = PautaFactory.createPauta(1L, "Pauta Teste", "Descricao Teste");
        Mockito.when(pautaRepository.findById(1L)).thenReturn(Mono.just(pauta));
        Pauta result = pautaService.findById(1L).block();
        assertEquals(pauta, result);
    }

    @Test
    void whenFindAll_thenReturnAllPautas() {
        List<Pauta> pautaList = PautaFactory.createList();
        Mockito.when(pautaRepository.findAll()).thenReturn(Flux.fromIterable(pautaList));
        List<Pauta> result = pautaService.findAll().collectList().block();
        assertEquals(pautaList, result);
    }
}

