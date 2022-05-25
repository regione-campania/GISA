

CREATE TABLE mu_macellazioni
(
id serial not null,
data_inserimento timestamp without time zone,
data_modifica timestamp without time zone,
id_utente_inserimento integer,
id_utente_modifica integer,
id_path_macellazione integer,
va_data_visita timestamp without time zone,
va_id_esito integer,
vp_data_visita timestamp without time zone,
vp_id_esito integer

)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_macellazioni
  OWNER TO postgres;
GRANT ALL ON TABLE mu_macellazioni TO postgres;
GRANT SELECT ON TABLE mu_macellazioni TO report;
GRANT SELECT ON TABLE mu_macellazioni TO usr_ro;

alter table mu_macellazioni
add progressivo_macellazione_pm character varying

alter table mu_macellazioni 
add id_seduta integer


CREATE TABLE mu_lookup_stati_seduta
(
  code serial NOT NULL,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT mu_lookup_stati_seduta_pkey PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_lookup_stati_seduta
  OWNER TO postgres;


  alter table mu_sedute
  add numero_capi_totale integer,
  add numero_capi_macellati integer



update mu_sedute set stato = -1 where stato is null

