CREATE TABLE conf_cu_tree
(
  id serial NOT NULL,
  id_tipologia_operatore integer,
  id_ruolo integer,
  id_root_tecnica integer,
  CONSTRAINT id_pkey_conf_tree PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE conf_cu_tree
  OWNER TO postgres;