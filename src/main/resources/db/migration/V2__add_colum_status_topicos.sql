USE forumhub;

ALTER TABLE topicos ADD COLUMN status VARCHAR(50);
UPDATE topicos SET status = 'NAO_RESOLVIDO';
ALTER TABLE topicos MODIFY COLUMN status VARCHAR(50) NOT NULL;