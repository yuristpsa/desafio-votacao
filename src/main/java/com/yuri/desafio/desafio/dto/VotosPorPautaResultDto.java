package com.yuri.desafio.desafio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class VotosPorPautaResultDto {
    private Long pautaId;
    private Long totalVotos;
    private Long totalVotosSim;
    private Long totalVotosNao;
    private VotoResultadoEnum vencedor;
}
