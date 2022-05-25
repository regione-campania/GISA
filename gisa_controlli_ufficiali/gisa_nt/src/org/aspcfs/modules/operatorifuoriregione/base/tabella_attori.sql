CREATE TABLE attori_opfuoriasl
(
  id serial NOT NULL,
  org_id integer NOT NULL,
  entered_by integer NOT NULL,
  entered timestamp without time zone,
  modified_by integer NOT NULL,
  modified timestamp without time zone,
  tipologia integer NOT NULL,
  ragione_sociale character varying(300),
  cognome character varying(300),
  nome character varying(300),
  data_nascita timestamp(3) without time zone,
  luogo_nascita character varying(300),
  comune text,
  indirizzo text,
  provincia text,
  documento text,
  note text,
  CONSTRAINT attori_opfuoriasl_pkey PRIMARY KEY (id),
  CONSTRAINT attori_opfuoriasl_org_id_fkey FOREIGN KEY (org_id)
      REFERENCES organization (org_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (OIDS=FALSE);
ALTER TABLE attori_opfuoriasl OWNER TO postgres;

alter table attori_opfuoriasl add merce text;