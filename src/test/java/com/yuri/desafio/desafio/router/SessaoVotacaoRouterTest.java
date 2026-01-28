package com.yuri.desafio.desafio.router;

import com.yuri.desafio.desafio.dto.SessaoVotacaoDto;
import com.yuri.desafio.desafio.entity.SessaoVotacao;
import com.yuri.desafio.desafio.mock.SessaoVotacaoFactory;
import com.yuri.desafio.desafio.service.SessaoVotacaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
public class SessaoVotacaoRouterTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private SessaoVotacaoService sessaoVotacaoService;

    @Test
    void whenCallGetAllSessoes_thenShouldReturnAll() {
        List<SessaoVotacao> lista = SessaoVotacaoFactory.createList();
        Mockito.when(sessaoVotacaoService.findAll()).thenReturn(Flux.fromIterable(lista));
        client.get().uri("/api/1.0/sessao-votacao").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBodyList(SessaoVotacaoDto.class)
                .value(result -> assertEquals(result.size(), lista.size()));
    }

    @Test
    void whenCallGetSessaoById_thenShouldReturnSessao() {
        SessaoVotacao sessao = SessaoVotacaoFactory.createSessao(1L, 1L, LocalDateTime.now(), 10);
        Mockito.when(sessaoVotacaoService.findById(1L)).thenReturn(Mono.just(sessao));
        client.get().uri("/api/1.0/sessao-votacao/1").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody(SessaoVotacaoDto.class)
                .value(result -> assertEquals(result.getId(), sessao.getId()));
    }

    @Test
    void whenCallPostSessao_thenShouldCreate() {
        SessaoVotacao sessao = SessaoVotacaoFactory.createSessao(1L, 1L, LocalDateTime.now(), 10);
        SessaoVotacaoDto dto = SessaoVotacaoDto.fromEntity(sessao);
        Mockito.when(sessaoVotacaoService.save(Mockito.any(SessaoVotacao.class))).thenReturn(Mono.just(sessao));
        client.post().uri("/api/1.0/sessao-votacao").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(SessaoVotacaoDto.class)
                .value(result -> assertEquals(result.getPautaId(), sessao.getPautaId()));
    }
}
