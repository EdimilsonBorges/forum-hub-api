
USE forumhub;

SET SQL_SAFE_UPDATES = 0;

##################TOPICOS###################

ALTER TABLE topicos ADD COLUMN uuid BINARY(16);
UPDATE topicos SET uuid = UUID();

ALTER TABLE respostas ADD COLUMN topico_uuid BINARY(16);
UPDATE respostas r
JOIN topicos t ON r.topico_id = t.id
SET r.topico_uuid = t.uuid;

ALTER TABLE respostas DROP FOREIGN KEY fk_respostas_topico;

ALTER TABLE topicos CHANGE COLUMN id old_id BIGINT;
ALTER TABLE topicos CHANGE COLUMN uuid id BINARY(16);

ALTER TABLE respostas CHANGE COLUMN topico_id old_topico_id BIGINT;
ALTER TABLE respostas CHANGE COLUMN topico_uuid topico_id BINARY(16);

ALTER TABLE topicos DROP COLUMN old_id;
ALTER TABLE respostas DROP COLUMN old_topico_id;

##################RESPOSTAS###################

ALTER TABLE respostas ADD COLUMN uuid BINARY(16);
UPDATE respostas SET uuid = UUID();

ALTER TABLE respostas CHANGE COLUMN id old_id BIGINT;
ALTER TABLE respostas CHANGE COLUMN uuid id BINARY(16);

ALTER TABLE respostas DROP COLUMN old_id;

##################CURSOS###################

ALTER TABLE cursos ADD COLUMN uuid BINARY(16);
UPDATE cursos SET uuid = UUID();

ALTER TABLE topicos ADD COLUMN curso_uuid BINARY(16);
UPDATE topicos t
JOIN cursos c ON t.curso_id = c.id
SET t.curso_uuid = c.uuid;

ALTER TABLE topicos DROP FOREIGN KEY fk_topicos_cursos;

ALTER TABLE cursos CHANGE COLUMN id old_id BIGINT;
ALTER TABLE cursos CHANGE COLUMN uuid id BINARY(16);

ALTER TABLE topicos CHANGE COLUMN curso_id old_curso_id BIGINT;
ALTER TABLE topicos CHANGE COLUMN curso_uuid curso_id BINARY(16);

ALTER TABLE cursos DROP COLUMN old_id;
ALTER TABLE topicos DROP COLUMN old_curso_id;

###################USUARIOS###################

ALTER TABLE usuarios ADD COLUMN uuid BINARY(16);
UPDATE usuarios SET uuid = UUID();

ALTER TABLE topicos ADD COLUMN usuario_uuid BINARY(16);
UPDATE topicos t
JOIN usuarios u ON t.usuario_id = u.id
SET t.usuario_uuid = u.uuid;

ALTER TABLE respostas ADD COLUMN autor_uuid BINARY(16);
UPDATE respostas r
JOIN usuarios u ON r.autor_id = u.id
SET r.autor_uuid = u.uuid;

ALTER TABLE topicos DROP FOREIGN KEY fk_topicos_usuarios;
ALTER TABLE respostas DROP FOREIGN KEY fk_respostas_autor;

ALTER TABLE usuarios CHANGE COLUMN id old_id BIGINT;
ALTER TABLE usuarios CHANGE COLUMN uuid id BINARY(16);

ALTER TABLE topicos CHANGE COLUMN usuario_id old_usuario_id BIGINT;
ALTER TABLE topicos CHANGE COLUMN usuario_uuid usuario_id BINARY(16);
ALTER TABLE respostas CHANGE COLUMN autor_id old_autor_id BIGINT;
ALTER TABLE respostas CHANGE COLUMN autor_uuid autor_id BINARY(16);

ALTER TABLE usuarios DROP COLUMN old_id;
ALTER TABLE topicos DROP COLUMN old_usuario_id;
ALTER TABLE respostas DROP COLUMN old_autor_id;

SET SQL_SAFE_UPDATES = 1;
###################CRIAR NOVAMENTE OS RELACIONAMENTOS###################
USE forumhub;
ALTER TABLE usuarios ADD UNIQUE (id);
ALTER TABLE cursos ADD UNIQUE (id);
ALTER TABLE topicos ADD UNIQUE (id);
ALTER TABLE respostas ADD UNIQUE (id);

ALTER TABLE topicos
    ADD CONSTRAINT fk_topicos_usuarios
    FOREIGN KEY (usuario_id)
    REFERENCES usuarios (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE topicos
    ADD CONSTRAINT fk_topicos_cursos
    FOREIGN KEY (curso_id)
    REFERENCES cursos (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE respostas
    ADD CONSTRAINT fk_respostas_topico
    FOREIGN KEY (topico_id)
    REFERENCES topicos (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

ALTER TABLE respostas
    ADD CONSTRAINT fk_respostas_autor
    FOREIGN KEY (autor_id)
    REFERENCES usuarios (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;

 ###################COLICANDO OS IDS NA PRIMEIRA POSICAO DA TABELA ###################
 USE forumhub;
 ALTER TABLE usuarios MODIFY COLUMN id BINARY(16) FIRST;
 ALTER TABLE respostas MODIFY COLUMN id BINARY(16) FIRST;
 ALTER TABLE topicos MODIFY COLUMN id BINARY(16) FIRST;
 ALTER TABLE usuarios MODIFY COLUMN id BINARY(16) FIRST;