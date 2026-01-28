CREATE TABLE pauta (
    id IDENTITY PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao VARCHAR(1000)
);

CREATE TABLE sessao_votacao (
    id IDENTITY PRIMARY KEY,
    pauta_id BIGINT NOT NULL,
    inicio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    minutos_ativa INT NOT NULL,
    FOREIGN KEY (pauta_id) REFERENCES pauta(id)
);

CREATE TABLE voto (
    id IDENTITY PRIMARY KEY,
    pauta_id BIGINT NOT NULL,
    associado_id VARCHAR(100) NOT NULL,
    voto BOOLEAN NOT NULL,
    data_voto TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (pauta_id, associado_id),
    FOREIGN KEY (pauta_id) REFERENCES pauta(id)
);