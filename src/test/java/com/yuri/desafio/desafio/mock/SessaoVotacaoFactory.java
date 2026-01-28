package com.yuri.desafio.desafio.mock;

import com.yuri.desafio.desafio.entity.SessaoVotacao;
import java.time.LocalDateTime;
import java.util.List;

public class SessaoVotacaoFactory {
    public static List<SessaoVotacao> createList() {
        return List.of(
            SessaoVotacao.builder().id(1L).pautaId(1L).inicio(LocalDateTime.now()).minutosAtiva(10).build(),
            SessaoVotacao.builder().id(2L).pautaId(2L).inicio(LocalDateTime.now()).minutosAtiva(5).build()
        );
    }

    public static SessaoVotacao createSessao(Long id, Long pautaId, LocalDateTime inicio, Integer minutosAtiva) {
        return SessaoVotacao.builder()
                .id(id)
                .pautaId(pautaId)
                .inicio(inicio)
                .minutosAtiva(minutosAtiva)
                .build();
    }
}

