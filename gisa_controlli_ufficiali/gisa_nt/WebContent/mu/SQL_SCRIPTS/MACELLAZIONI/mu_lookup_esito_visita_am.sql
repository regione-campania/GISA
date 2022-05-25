CREATE TABLE mu_lookup_esito_visita_am
(
  code serial NOT NULL,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT mu_lookup_esito_visita_am_pkey PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_lookup_esito_visita_am
  OWNER TO postgres;



  alter table mu_macellazioni
  add id_tipo_macellazione_pm integer


  update mu_macellazioni set id_tipo_macellazione_pm = 1