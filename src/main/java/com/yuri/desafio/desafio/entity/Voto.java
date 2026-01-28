package com.yuri.desafio.desafio.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Data
@Builder
@Table("voto")
public class Voto {
    @Id
    private Long id;
    private Long pautaId;
    private String associadoId;
    private Boolean voto;
    private LocalDateTime dataVoto;
}
