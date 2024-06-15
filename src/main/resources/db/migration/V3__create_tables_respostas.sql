USE forumhub;

CREATE TABLE respostas (
  id BIGINT NOT NULL AUTO_INCREMENT,
  mensagem VARCHAR(500) NOT NULL UNIQUE,
  data_criacao DATETIME NOT NULL,
  solucao TINYINT NOT NULL,
  autor_id BIGINT NOT NULL,
  topico_id BIGINT NOT NULL,
  PRIMARY KEY(id),
  CONSTRAINT fk_respostas_topico
      FOREIGN KEY (topico_id)
      REFERENCES topicos (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT fk_respostas_autor
      FOREIGN KEY (autor_id)
      REFERENCES usuarios (id)
      ON DELETE CASCADE
      ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
