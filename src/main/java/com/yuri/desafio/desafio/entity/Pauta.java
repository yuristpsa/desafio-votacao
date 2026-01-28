package com.yuri.desafio.desafio.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("pauta")
public class Pauta {
    @Id
    private Long id;
    private String titulo;
    private String descricao;
}
