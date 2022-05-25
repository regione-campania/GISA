CREATE TABLE public.lookup_apicoltura_stati_movimentazione
(
  code integer NOT NULL DEFAULT nextval('lookup_apicoltura_stati_apiario_code_seq'::regclass),
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_apicoltura_stati_movimentazione_pk PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_apicoltura_stati_movimentazione
  OWNER TO postgres;
GRANT ALL ON TABLE public.lookup_apicoltura_stati_movimentazione TO postgres;
GRANT SELECT ON TABLE public.lookup_apicoltura_stati_movimentazione TO report;

INSERT INTO lookup_apicoltura_stati_movimentazione(
            code, description, default_item, level, enabled)
    VALUES (1, 'Movimentazione in attesa di validazione', false, 10, true);

    INSERT INTO lookup_apicoltura_stati_movimentazione(
            code, description, default_item, level, enabled)
    VALUES (2, 'Movimentazione validata', false, 20, true);

    INSERT INTO lookup_apicoltura_stati_movimentazione(
            code, description, default_item, level, enabled)
    VALUES (3, 'Validazione della movimentazione non richiesta', false, 30, true);

alter table apicoltura_movimentazioni add column num_alveari_da_spostare text;
alter table apicoltura_movimentazioni add column num_sciami_da_spostare text;
alter table apicoltura_movimentazioni add column num_pacchi_da_spostare text;
alter table apicoltura_movimentazioni add column num_regine_da_spostare text;