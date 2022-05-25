-- Table: mu_lookup_esito_visita_am

-- DROP TABLE mu_lookup_esito_visita_am;

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
GRANT ALL ON TABLE mu_lookup_esito_visita_am TO postgres;
GRANT ALL ON TABLE mu_lookup_esito_visita_am TO report;


INSERT INTO mu_lookup_esito_visita_am(
            code, description, default_item, level, enabled)
    VALUES (1,'Favorevole',FALSE,0,TRUE);
INSERT INTO mu_lookup_esito_visita_am(
            code, description, default_item, level, enabled)
    VALUES (2,'Favorevole con riserva',FALSE,0,TRUE);
INSERT INTO mu_lookup_esito_visita_am(
            code, description, default_item, level, enabled)
    VALUES (3,'Non favorevole',FALSE,0,TRUE);
	
	
	
-- Table: mu_lookup_stati

-- DROP TABLE mu_lookup_stati;

CREATE TABLE mu_lookup_stati
(
  code serial NOT NULL,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT mu_lookup_stati_pkey PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_lookup_stati
  OWNER TO postgres;
GRANT ALL ON TABLE mu_lookup_stati TO postgres;
GRANT ALL ON TABLE mu_lookup_stati TO report;


INSERT INTO mu_lookup_stati(
            code, description, default_item, level, enabled)
    VALUES (1,'Controllo Documentale',FALSE,0,TRUE);
INSERT INTO mu_lookup_stati(
            code, description, default_item, level, enabled)
    VALUES (2,'Macellato',FALSE,0,TRUE);
INSERT INTO mu_lookup_stati(
            code, description, default_item, level, enabled)
    VALUES (3,'Morte Ante Macellazione',FALSE,0,TRUE);
INSERT INTO mu_lookup_stati(
            code, description, default_item, level, enabled)
    VALUES (4,'Arrivato Deceduto',FALSE,0,TRUE);
	
	
	
	-- Table: mu_lookup_stati_seduta

-- DROP TABLE mu_lookup_stati_seduta;

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
GRANT ALL ON TABLE mu_lookup_stati_seduta TO postgres;
GRANT ALL ON TABLE mu_lookup_stati_seduta TO report;


INSERT INTO mu_lookup_stati_seduta(
            code, description, default_item, level, enabled)
    VALUES (1,'Da Macellare',FALSE,0,TRUE);
INSERT INTO mu_lookup_stati_seduta(
            code, description, default_item, level, enabled)
    VALUES (2,'Macellazione in corso ',FALSE,0,TRUE);
INSERT INTO mu_lookup_stati_seduta(
            code, description, default_item, level, enabled)
    VALUES (3,'Macellato',FALSE,0,TRUE);



