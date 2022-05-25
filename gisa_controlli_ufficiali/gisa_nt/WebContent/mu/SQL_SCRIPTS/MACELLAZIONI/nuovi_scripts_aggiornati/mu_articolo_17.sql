-- Table: mu_articolo_17

-- DROP TABLE mu_articolo_17;

CREATE TABLE mu_articolo_17
(
  id serial NOT NULL,
  id_esercente integer,
  nome_esercente character varying,
  data_creazione timestamp without time zone,
  id_stato integer,
  entered timestamp without time zone,
  enteredby integer,
  descrizione character varying,
  data_cancellazione timestamp without time zone,
  id_macello integer,
  progressivo integer,
  anno integer,
  id_seduta integer,
  modifiedby integer,
  modified timestamp without time zone,
  CONSTRAINT mu_articolo_17_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_articolo_17
  OWNER TO postgres;
GRANT ALL ON TABLE mu_articolo_17 TO postgres;
GRANT ALL ON TABLE mu_articolo_17 TO report;




CREATE TABLE mu_articolo_17_lista_capi
(
  id serial NOT NULL,
  id_articolo_17 integer,
  id_capo integer,
  data_macellazione timestamp without time zone,
  CONSTRAINT mu_articolo_17_lista_capi_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_articolo_17_lista_capi
  OWNER TO postgres;
GRANT ALL ON TABLE mu_articolo_17_lista_capi TO postgres;
GRANT ALL ON TABLE mu_articolo_17_lista_capi TO report;



CREATE TABLE mu_lookup_stati_articoli_17
(
  code serial NOT NULL,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT mu_lookup_stati_articoli_17_pkey PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_lookup_stati_articoli_17
  OWNER TO postgres;
GRANT ALL ON TABLE mu_lookup_stati_articoli_17 TO postgres;
GRANT ALL ON TABLE mu_lookup_stati_articoli_17 TO report;

INSERT INTO mu_lookup_stati_articoli_17(
            code, description, default_item, level, enabled)
    VALUES (1,'Aperto',FALSE,0,TRUE)
	
	
	INSERT INTO mu_lookup_stati_articoli_17(
            code, description, default_item, level, enabled)
    VALUES (1,'Chiuso',FALSE,0,TRUE)