CREATE TABLE lookup_tipo_ispezione_abusivi
(
  code serial NOT NULL,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_tipo_ispezione_abusivi_pkey PRIMARY KEY (code)
)
WITH (OIDS=FALSE);
ALTER TABLE lookup_tipo_ispezione_abusivi OWNER TO postgres;

INSERT INTO lookup_tipo_ispezione_abusivi(
            code, description, default_item, "level", enabled)
    VALUES (2, 'In Piano di Monitoraggio', FALSE, 0, TRUE);

INSERT INTO lookup_tipo_ispezione_abusivi(
            code, description, default_item, "level", enabled)
    VALUES (6, 'Per Reclami', FALSE, 1, TRUE);

INSERT INTO lookup_tipo_ispezione_abusivi(
            code, description, default_item, "level", enabled)
    VALUES (9, 'Sospetta presenza n.c.', FALSE, 2, TRUE);

INSERT INTO lookup_tipo_ispezione_abusivi(
            code, description, default_item, "level", enabled)
    VALUES (4, 'Altro', FALSE, 3, TRUE);
