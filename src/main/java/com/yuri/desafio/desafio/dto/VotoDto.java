package com.yuri.desafio.desafio.dto;

import com.yuri.desafio.desafio.entity.Voto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VotoDto {

    private Long id;
    private Long pautaId;
    private String associadoId;
    private Boolean voto;
    private LocalDateTime dataVoto;

    public static VotoDto fromEntity(Voto voto) {
        return VotoDto.builder()
                .id(voto.getId())
                .pautaId(voto.getPautaId())
                .associadoId(voto.getAssociadoId())
                .voto(voto.getVoto())
                .dataVoto(voto.getDataVoto())
                .build();
    }

    public Voto toEntity() {
        return Voto.builder()
                .id(this.id)
                .pautaId(this.pautaId)
                .associadoId(this.associadoId)
                .voto(this.voto)
                .build();
    }
}
