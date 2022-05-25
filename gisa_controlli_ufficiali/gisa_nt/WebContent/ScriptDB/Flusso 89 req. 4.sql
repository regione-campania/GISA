-- Sequence: public.apicoltura_movimentazioni_id_seq

-- DROP SEQUENCE public.apicoltura_movimentazioni_id_seq;

CREATE SEQUENCE public.apicoltura_movimentazioni_id_storico_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9999999
  START 1
  CACHE 1;
ALTER TABLE public.apicoltura_movimentazioni_id_seq
  OWNER TO postgres;


-- Table: public.apicoltura_movimentazioni

-- DROP TABLE public.apicoltura_movimentazioni;

CREATE TABLE public.apicoltura_movimentazioni_storico
(
  id integer not null default nextval('apicoltura_movimentazioni_id_storico_seq'::regclass),
  id_movimentazione integer NOT NULL,
  data_movimentazione timestamp without time zone,
  data_modello timestamp without time zone,
  numero_modello text,
  id_tipo_movimentazione integer,
  num_apiari_spostati integer,
  id_asl_apicoltore_origine integer,
  codice_azienda_origine text,
  progressivo_apiario_origine text,
  id_stabilimento_apiario_origine integer,
  id_stabilimento_apiario_destinazione integer,
  id_bda_apiario_origine integer,
  num_apiari_origine integer,
  id_bda_apiario_destinazione integer,
  codice_azienda_destinazione text,
  progressivo_apiario_destinazione text,
  comune_dest text,
  sigla_prov_comune_dest text,
  indirizzo_dest text,
  latitudine_dest double precision,
  longitudine_dest double precision,
  num_apiari_destinazione integer,
  entered timestamp without time zone,
  entered_by integer,
  trashed_date timestamp without time zone,
  note text,
  stato integer,
  sincronizzato_bdn boolean,
  data_sincronizzazione timestamp without time zone,
  sincronizzato_da integer,
  id_bdn integer,
  errore_sincronizzazione text,
  num_alveari_da_spostare text,
  num_sciami_da_spostare text,
  num_pacchi_da_spostare text,
  num_regine_da_spostare text,
  CONSTRAINT id_mov_storico_ PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.apicoltura_movimentazioni_storico
  OWNER TO postgres;
GRANT ALL ON TABLE public.apicoltura_movimentazioni_storico TO postgres;
GRANT SELECT ON TABLE public.apicoltura_movimentazioni_storico TO report;
