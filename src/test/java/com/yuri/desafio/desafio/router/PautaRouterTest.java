package com.yuri.desafio.desafio.router;

import com.yuri.desafio.desafio.dto.PautaDto;
import com.yuri.desafio.desafio.entity.Pauta;
import com.yuri.desafio.desafio.service.PautaService;
import com.yuri.desafio.desafio.mock.PautaFactory;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
public class PautaRouterTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private PautaService pautaService;

    @Test
    void whenCallGetAllPautas_thenShouldReturnAll() {
        List<Pauta> pautaList = PautaFactory.createList();
        Mockito.when(pautaService.findAll()).thenReturn(Flux.fromIterable(pautaList));
        client.get().uri("/api/1.0/pautas").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBodyList(PautaDto.class)
                .value(result -> assertEquals(result.size(), pautaList.size()));
    }

    @Test
    void whenCallGetPautaById_thenShouldReturnPauta() {
        Pauta pauta = PautaFactory.createPauta(1L, "Pauta 1", "Desc 1");
        Mockito.when(pautaService.findById(1L)).thenReturn(Mono.just(pauta));
        client.get().uri("/api/1.0/pautas/1").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody(PautaDto.class)
                .value(result -> assertEquals(result.getId(), pauta.getId()));
    }

    @Test
    void whenCallPostPauta_thenShouldCreate() {
        Pauta pauta = PautaFactory.createPauta(1L, "Pauta 1", "Desc 1");
        PautaDto dto = PautaDto.fromEntity(pauta);
        Mockito.when(pautaService.save(Mockito.any(Pauta.class))).thenReturn(Mono.just(pauta));
        client.post().uri("/api/1.0/pautas").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PautaDto.class)
                .value(result -> assertEquals(result.getTitulo(), pauta.getTitulo()));
    }
}
