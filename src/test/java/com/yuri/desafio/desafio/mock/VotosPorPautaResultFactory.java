package com.yuri.desafio.desafio.mock;

import com.yuri.desafio.desafio.dto.VotosPorPautaResultDto;
import com.yuri.desafio.desafio.dto.VotoResultadoEnum;

public class VotosPorPautaResultFactory {
    public static VotosPorPautaResultDto createEmpate() {
        return VotosPorPautaResultDto.builder()
                .pautaId(1L)
                .totalVotos(2L)
                .totalVotosSim(1L)
                .totalVotosNao(1L)
                .vencedor(VotoResultadoEnum.EMPATE)
                .build();
    }
    // Métodos para outros cenários podem ser adicionados aqui
}

