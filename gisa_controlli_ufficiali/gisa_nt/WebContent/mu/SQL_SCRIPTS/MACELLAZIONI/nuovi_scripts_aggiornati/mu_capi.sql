-- Table: mu_capi

-- DROP TABLE mu_capi;

CREATE TABLE mu_capi
(
  id serial NOT NULL,
  id_partita integer,
  id_seduta integer,
  specie integer,
  num_capi integer,
  matricola text,
  categoria_capo integer,
  categoria_bovina integer,
  categoria_bufalina integer,
  razza_bovina integer,
  sesso text,
  data_nascita timestamp without time zone,
  categoria_rischio integer,
  entered timestamp without time zone,
  data_cancellazione timestamp without time zone,
  flag_arrivato_deceduto boolean,
  nr_parti integer,
  nr_parti_assegnate integer,
  id_stato integer,
  info_azienda_provenienza text,
  ragione_sociale_azienda_provenienza text,
  denominazione_asl_azienda_provenienza text,
  id_asl_azienda_provenienza integer,
  cod_asl_azienda_prov text,
  id_path_macellazione integer DEFAULT (-1),
  numero_parti_assegnate integer,
  numero_parti integer,
  CONSTRAINT mu_capi_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_capi
  OWNER TO postgres;
GRANT ALL ON TABLE mu_capi TO postgres;
GRANT ALL ON TABLE mu_capi TO report;
