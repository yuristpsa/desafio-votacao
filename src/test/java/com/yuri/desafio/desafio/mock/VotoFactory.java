package com.yuri.desafio.desafio.mock;

import com.yuri.desafio.desafio.entity.Voto;
import java.time.LocalDateTime;
import java.util.List;

public class VotoFactory {
    public static List<Voto> createList() {
        return List.of(
            Voto.builder().id(1L).pautaId(1L).associadoId("user1").voto(true).dataVoto(LocalDateTime.now()).build(),
            Voto.builder().id(2L).pautaId(1L).associadoId("user2").voto(false).dataVoto(LocalDateTime.now()).build()
        );
    }

    public static Voto createVoto(Long id, Long pautaId, String associadoId, Boolean voto) {
        return Voto.builder()
                .id(id)
                .pautaId(pautaId)
                .associadoId(associadoId)
                .voto(voto)
                .dataVoto(LocalDateTime.now())
                .build();
    }
}

