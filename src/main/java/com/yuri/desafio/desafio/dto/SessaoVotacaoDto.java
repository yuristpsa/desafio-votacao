package com.yuri.desafio.desafio.dto;

import com.yuri.desafio.desafio.entity.SessaoVotacao;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SessaoVotacaoDto {

    private Long id;
    private Long pautaId;
    private LocalDateTime inicio;
    private Integer minutosAtiva;

    public static SessaoVotacaoDto fromEntity(SessaoVotacao sessao) {
        return SessaoVotacaoDto.builder()
                .id(sessao.getId())
                .pautaId(sessao.getPautaId())
                .inicio(sessao.getInicio())
                .minutosAtiva(sessao.getMinutosAtiva())
                .build();
    }

    public SessaoVotacao toEntity() {
        return SessaoVotacao.builder()
                .id(this.id)
                .pautaId(this.pautaId)
                .inicio(this.inicio)
                .minutosAtiva(this.minutosAtiva)
                .build();
    }
}
