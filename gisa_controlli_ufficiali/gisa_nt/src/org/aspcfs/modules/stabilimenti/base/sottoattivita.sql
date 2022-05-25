CREATE TABLE stabilimenti_sottoattivita
(
  	id serial NOT NULL PRIMARY KEY,
	id_stabilimento integer,
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

CREATE TABLE lookup_sottoattivita_imballata
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_sottoattivita_imballata_pkey PRIMARY KEY (code)
);

CREATE TABLE lookup_sottoattivita_tipoautorizzazione
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_sottoattivita_tipoautorizzazione_pkey PRIMARY KEY (code)
);

INSERT INTO lookup_sottoattivita_imballata( code, description, level, enabled ) VALUES( 0, 'Imballata', 10, true );
INSERT INTO lookup_sottoattivita_imballata( code, description, level, enabled ) VALUES( 1, 'Non Imballata', 20, true );
INSERT INTO lookup_sottoattivita_imballata( code, description, level, enabled ) VALUES( 2, 'Entrambi', 30, true );

INSERT INTO lookup_sottoattivita_tipoautorizzazione( code, description, level, enabled ) VALUES( 1, 'Definitiva', 10, true );
INSERT INTO lookup_sottoattivita_tipoautorizzazione( code, description, level, enabled ) VALUES( 2, 'Condizionata', 20, true );
