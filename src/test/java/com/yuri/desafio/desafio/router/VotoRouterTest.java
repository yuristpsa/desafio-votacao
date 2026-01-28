package com.yuri.desafio.desafio.router;

import com.yuri.desafio.desafio.dto.VotoDto;
import com.yuri.desafio.desafio.dto.VotosPorPautaResultDto;
import com.yuri.desafio.desafio.entity.Voto;
import com.yuri.desafio.desafio.mock.VotoFactory;
import com.yuri.desafio.desafio.mock.VotosPorPautaResultFactory;
import com.yuri.desafio.desafio.service.VotoService;
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
public class VotoRouterTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private VotoService votoService;

    @Test
    void whenCallGetAllVotos_thenShouldReturnAll() {
        List<Voto> lista = VotoFactory.createList();
        Mockito.when(votoService.findAll()).thenReturn(Flux.fromIterable(lista));
        client.get().uri("/api/1.0/votos").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBodyList(VotoDto.class)
                .value(result -> assertEquals(result.size(), lista.size()));
    }

    @Test
    void whenCallGetVotoById_thenShouldReturnVoto() {
        Voto voto = VotoFactory.createVoto(1L, 1L, "user1", true);
        Mockito.when(votoService.findById(1L)).thenReturn(Mono.just(voto));
        client.get().uri("/api/1.0/votos/1").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody(VotoDto.class)
                .value(result -> assertEquals(result.getId(), voto.getId()));
    }

    @Test
    void whenCallPostVoto_thenShouldCreate() {
        Voto voto = VotoFactory.createVoto(1L, 1L, "user1", true);
        VotoDto dto = VotoDto.fromEntity(voto);
        Mockito.when(votoService.save(Mockito.any(Voto.class))).thenReturn(Mono.just(voto));
        client.post().uri("/api/1.0/votos").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(VotoDto.class)
                .value(result -> assertEquals(result.getAssociadoId(), voto.getAssociadoId()));
    }

    @Test
    void whenCallCountVotosByPautaId_thenShouldReturnResult() {
        VotosPorPautaResultDto resultDto = VotosPorPautaResultFactory.createEmpate();
        Mockito.when(votoService.countVotosByPautaId(1L)).thenReturn(Mono.just(resultDto));
        client.get().uri("/api/1.0/votos/contagem/1").accept(MediaType.APPLICATION_JSON).exchange()
                .expectStatus().isOk()
                .expectBody(VotosPorPautaResultDto.class)
                .value(result -> assertEquals(result.getTotalVotos(), resultDto.getTotalVotos()));
    }
}
