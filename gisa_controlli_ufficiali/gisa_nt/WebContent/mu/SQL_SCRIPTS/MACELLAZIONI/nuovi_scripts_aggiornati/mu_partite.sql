-- Table: mu_partite

-- DROP TABLE mu_partite;

CREATE TABLE mu_partite
(
  id serial NOT NULL,
  id_macello integer,
  codice_azienda_provenienza text,
  numero text,
  proprietario_animali text,
  codice_azienda_nascita text,
  vincolo_sanitario boolean,
  motivo_vincolo_sanitario text,
  mod4 text,
  data_mod4 timestamp without time zone,
  macellazione_differita integer,
  info_catena_alimentare boolean,
  data_arrivo_macello timestamp without time zone,
  data_arrivo_macello_dichiarata boolean,
  mezzo_tipo text,
  mezzo_targa text,
  trasporto_superiore boolean,
  veterinario1 text,
  veterinario2 text,
  veterinario3 text,
  entered timestamp without time zone,
  enteredby integer,
  CONSTRAINT mu_partite_pkey PRIMARY KEY (id ),
  CONSTRAINT mu_partite_id_macello_fkey FOREIGN KEY (id_macello)
      REFERENCES organization (org_id) MATCH Unknown
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_partite
  OWNER TO postgres;
GRANT ALL ON TABLE mu_partite TO postgres;
GRANT ALL ON TABLE mu_partite TO report;
