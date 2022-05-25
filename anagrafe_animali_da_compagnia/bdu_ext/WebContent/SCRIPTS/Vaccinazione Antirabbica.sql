CREATE SEQUENCE public.lookup_tipologia_vaccino_inoculato_code_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.lookup_tipologia_vaccino_inoculato_code_seq
  OWNER TO postgres;

CREATE TABLE public.lookup_tipologia_vaccino_inoculato
(
  code integer NOT NULL DEFAULT nextval('lookup_tipologia_vaccino_inoculato_code_seq'::regclass),
  description character varying(300),
  default_item boolean DEFAULT false,
  level integer,
  enabled boolean DEFAULT true,
  entered timestamp without time zone NOT NULL DEFAULT now(),
  modified timestamp without time zone NOT NULL DEFAULT now(),
  CONSTRAINT lookup_tipologia_vaccino_inoculato_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_tipologia_vaccino_inoculato
  OWNER TO postgres;
GRANT ALL ON TABLE public.lookup_tipologia_vaccino_inoculato TO postgres;
GRANT SELECT ON TABLE public.lookup_tipologia_vaccino_inoculato TO usr_ro;
GRANT SELECT ON TABLE public.lookup_tipologia_vaccino_inoculato TO report;



INSERT INTO public.evento_html_fields(
            id_tipologia_evento, nome_campo,                 tipo_campo, label_campo, 
            tabella_lookup,  
            ordine_campo)
    VALUES (36, 'idTipologiaVaccinoInoculato', 'select','Tipo di vaccino inoculato', 
            'lookup_tipologia_vaccino_inoculato', 
            8);



INSERT INTO public.lookup_tipologia_vaccino_inoculato(description, default_item, level, enabled, entered, modified)
VALUES ('Un Anno', false, 1, true, now(), now());
INSERT INTO public.lookup_tipologia_vaccino_inoculato(description, default_item, level, enabled, entered, modified)
VALUES ('Due Anni', false, 2, true, now(), now());
INSERT INTO public.lookup_tipologia_vaccino_inoculato(description, default_item, level, enabled, entered, modified)
VALUES ('Tre Anni', false, 3, true, now(), now());


alter table evento_inserimento_vaccino add column id_tipologia_vaccino_inoculato integer;
ALTER TABLE public.evento_inserimento_vaccino
  ADD CONSTRAINT evento_inserimento_vaccino_id_tipologia_vaccino_inoculato_fkey FOREIGN KEY (id_tipologia_vaccino_inoculato)
      REFERENCES public.lookup_tipologia_vaccino_inoculato (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

      
alter table lookup_tipologia_vaccino_inoculato add column days integer;
update lookup_tipologia_vaccino_inoculato set days = 365 where code = 1;
update lookup_tipologia_vaccino_inoculato set days = 365*2 where code = 2;
update lookup_tipologia_vaccino_inoculato set days = 365*3 where code = 3;

update evento_html_fields set javascript_function = 'onchange=disattivaTipoInoculato()' where id = 169;
