package com.yuri.desafio.desafio.dto;

import lombok.Data;
import lombok.Builder;
import com.yuri.desafio.desafio.entity.Pauta;

@Data
@Builder
public class PautaDto {
    private Long id;
    private String titulo;
    private String descricao;

    public static PautaDto fromEntity(Pauta pauta) {
        return PautaDto.builder()
                .id(pauta.getId())
                .titulo(pauta.getTitulo())
                .descricao(pauta.getDescricao())
                .build();
    }

    public Pauta toEntity() {
        return Pauta.builder()
                .id(this.id)
                .titulo(this.titulo)
                .descricao(this.descricao)
                .build();
    }
}
