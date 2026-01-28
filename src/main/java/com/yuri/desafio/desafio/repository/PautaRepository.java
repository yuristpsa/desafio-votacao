package com.yuri.desafio.desafio.repository;

import com.yuri.desafio.desafio.entity.Pauta;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PautaRepository extends ReactiveCrudRepository<Pauta, Long> {}