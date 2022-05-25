
CREATE TABLE lookup_stato_tras
(
  code serial NOT NULL,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_stato_tras_pkey PRIMARY KEY (code)
)
WITH (OIDS=FALSE);
ALTER TABLE lookup_stato_tras OWNER TO postgres;