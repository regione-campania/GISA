CREATE TABLE soa_sottoattivita
(
  	id serial NOT NULL PRIMARY KEY,
	id_soa integer,
	codice_sezione  integer,
	codice_impianto integer,
	stato_attivita  integer,
	imballata       integer,
	tipo_autorizzazione integer,
	
	attivita text,
	descrizione_stato_attivita text,
	
	data_inizio_attivita timestamp,
	data_fine_attivita timestamp,
	
	riti_religiosi boolean,
	
	entered timestamp default CURRENT_TIMESTAMP,
	modified timestamp,
	trashed_date timestamp,
	entered_by integer,
	modified_by integer,
	trashed_by integer,
	enabled boolean default true
);

CREATE TABLE lookup_sottoattivita_imballata_soa
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_sottoattivita_imballata_pkey_soa PRIMARY KEY (code)
);

CREATE TABLE lookup_sottoattivita_tipoautorizzazione_soa
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_sottoattivita_tipoautorizzazione_pkey_soa PRIMARY KEY (code)
);

INSERT INTO lookup_sottoattivita_imballata_soa( code, description, level, enabled ) VALUES( 0, 'Imballata', 10, true );
INSERT INTO lookup_sottoattivita_imballata_soa( code, description, level, enabled ) VALUES( 1, 'Non Imballata', 20, true );
INSERT INTO lookup_sottoattivita_imballata_soa( code, description, level, enabled ) VALUES( 2, 'Entrambi', 30, true );

INSERT INTO lookup_sottoattivita_tipoautorizzazione_soa( code, description, level, enabled ) VALUES( 1, 'Definitiva', 10, true );
INSERT INTO lookup_sottoattivita_tipoautorizzazione_soa( code, description, level, enabled ) VALUES( 2, 'Condizionata', 20, true );

-- Table: lookup_soa

-- DROP TABLE lookup_soa;

CREATE TABLE lookup_soa
(
  id_contributo integer NOT NULL,
  id_categoria integer NOT NULL,
  stabilimento character varying(300),
  CONSTRAINT lookup_soa_id_categoria_fkey FOREIGN KEY (id_categoria)
      REFERENCES categoria_prodotto (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT lookup_soa_id_contributo_fkey FOREIGN KEY (id_contributo)
      REFERENCES contributo (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITHOUT OIDS;
ALTER TABLE lookup_soa OWNER TO postgres;

-- Table: lookup_soa_size

-- DROP TABLE lookup_soa_size;

CREATE TABLE lookup_soa_size
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_soa_size_pkey PRIMARY KEY (code)
) 
WITHOUT OIDS;
ALTER TABLE lookup_soa_size OWNER TO postgres;

-- Table: lookup_soa_stage

-- DROP TABLE lookup_soa_stage;

CREATE TABLE lookup_soa_stage
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  entered timestamp(3) without time zone NOT NULL DEFAULT now(),
  modified timestamp(3) without time zone NOT NULL DEFAULT now(),
  CONSTRAINT lookup_soa_stage_pkey PRIMARY KEY (code)
) 
WITHOUT OIDS;
ALTER TABLE lookup_soa_stage OWNER TO postgres;

-- Table: lookup_soa_types

-- DROP TABLE lookup_soa_types;

CREATE TABLE lookup_soa_types
(
  code serial NOT NULL,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_soa_types_pkey PRIMARY KEY (code)
) 
WITHOUT OIDS;
ALTER TABLE lookup_soa_types OWNER TO postgres;

-- Table: soa_type_levels

-- DROP TABLE soa_type_levels;

CREATE TABLE soa_type_levels
(
  org_id integer NOT NULL,
  type_id integer NOT NULL,
  "level" integer NOT NULL,
  entered timestamp(3) without time zone NOT NULL DEFAULT now(),
  modified timestamp(3) without time zone NOT NULL DEFAULT now(),
  CONSTRAINT soa_type_levels_org_id_fkey FOREIGN KEY (org_id)
      REFERENCES organization (org_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT soa_type_levels_type_id_fkey FOREIGN KEY (type_id)
      REFERENCES lookup_soa_types (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITHOUT OIDS;
ALTER TABLE soa_type_levels OWNER TO postgres;

CREATE TABLE lookup_categoria_soa
(
  code integer,
  description text,
  default_item boolean,
  "level" integer,
  enabled boolean
)
WITH (OIDS=FALSE);
ALTER TABLE lookup_categoria_soa OWNER TO postgres;

CREATE TABLE lookup_impianto_soa
(
  code serial NOT NULL,
  description character varying(70) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_impianto_soa_pkey PRIMARY KEY (code)
)
WITH (OIDS=FALSE);
ALTER TABLE lookup_impianto_soa OWNER TO postgres;
