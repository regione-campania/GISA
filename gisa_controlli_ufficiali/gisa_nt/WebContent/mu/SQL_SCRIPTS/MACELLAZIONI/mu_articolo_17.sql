-- Table: mu_sedute

-- DROP TABLE mu_sedute;

CREATE TABLE mu_articolo_17
(
  id serial NOT NULL,
  id_esercente integer,
  nome_esercente character varying,
  data_creazione timestamp without time zone,
  id_stato integer,
  entered timestamp without time zone,
  enteredby integer,
  CONSTRAINT mu_articolo_17_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_articolo_17
  OWNER TO postgres;



create table mu_articolo_17_lista_capi
(
id serial not null,
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



alter table mu_articolo_17
add descrizione character varying,
add data_cancellazione timestamp without time zone,
add id_macello integer


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