package com.yuri.desafio.desafio.mock;

import com.yuri.desafio.desafio.entity.Pauta;
import java.util.List;

public class PautaFactory {
    public static List<Pauta> createList() {
        return List.of(
            Pauta.builder().id(1L).titulo("Pauta 1").descricao("Desc 1").build(),
            Pauta.builder().id(2L).titulo("Pauta 2").descricao("Desc 2").build()
        );
    }

    public static Pauta createPauta(Long id, String titulo, String descricao) {
        return Pauta.builder()
                .id(id)
                .titulo(titulo)
                .descricao(descricao)
                .build();
    }
}

