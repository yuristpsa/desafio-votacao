package com.yuri.desafio.desafio.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@Table("sessao_votacao")
public class SessaoVotacao {
    @Id
    private Long id;
    private Long pautaId;
    private LocalDateTime inicio;
    private Integer minutosAtiva;

    public LocalDateTime getFim() {
        if (Objects.isNull(inicio) || Objects.isNull(minutosAtiva)) return null;
        return inicio.plusMinutes(minutosAtiva);
    }

    public boolean isEncerrada() {
        LocalDateTime fim = getFim();
        return Objects.nonNull(fim) && LocalDateTime.now().isBefore(fim);
    }
}
