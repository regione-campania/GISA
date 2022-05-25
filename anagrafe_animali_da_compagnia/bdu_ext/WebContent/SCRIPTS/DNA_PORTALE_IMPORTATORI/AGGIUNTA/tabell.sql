-- Table: dati_convocazione

-- DROP TABLE dati_convocazione;

CREATE TABLE dati_convocazione
(
  id serial NOT NULL,
  denominazione character varying(255),
  nome_file character varying,
  data_inizio timestamp without time zone,
  data_fine timestamp without time zone,
  id_comune integer,
  id_circoscrizione integer,
  data_inserimento timestamp without time zone NOT NULL DEFAULT now(),
  data_modifica timestamp without time zone NOT NULL DEFAULT now(),
  utente_inserimento integer,
  utente_modifica integer,
  data_cancellazione timestamp without time zone,
  note character varying,
  stato integer,
  CONSTRAINT dati_convocazione_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE dati_convocazione
  OWNER TO postgres;




CREATE TABLE convocazioni
(
  id serial NOT NULL,
  codice_fiscale character varying(255),
  nome character varying,
  cognome character varying,
  indirizzo character varying,
  microchip character varying,
  id_lista_convocazione integer,
  id_stato_presentazione integer,
  data_inserimento timestamp without time zone NOT NULL DEFAULT now(),
  data_modifica timestamp without time zone NOT NULL DEFAULT now(),
  utente_inserimento integer,
  utente_modifica integer,
  data_cancellazione timestamp without time zone,
  note character varying,
  CONSTRAINT convocazioni_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE convocazioni
  OWNER TO postgres;


  alter table convocazioni
  add data_nascita timestamp without time zone;



CREATE TABLE circoscrizioni
(
  id serial NOT NULL,
  id_comune integer,
  nome_circoscrizione character varying,
  CONSTRAINT circoscrizioni_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE circoscrizioni
  OWNER TO postgres;


  CREATE TABLE dati_convocazione_temporale
(
  id serial NOT NULL,
  denominazione character varying(255),
  --nome_file character varying,
 -- data_inizio timestamp without time zone,
  data_fine timestamp without time zone,
  --id_comune integer,
--  id_circoscrizione integer,
  data_inserimento timestamp without time zone NOT NULL DEFAULT now(),
  data_modifica timestamp without time zone NOT NULL DEFAULT now(),
  utente_inserimento integer,
  utente_modifica integer,
  data_cancellazione timestamp without time zone,
  note character varying,
  stato integer,
  id_lista_convocazione integer,
  CONSTRAINT dati_convocazione_temporale_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE dati_convocazione_temporale
  OWNER TO postgres;

  id_convocato, id_lista_convocazione, enabled, id_stato_presentazione
drop table relazione_convocazione_convocati
CREATE TABLE relazione_convocazione_convocati
(  id serial NOT NULL,
   id_convocato integer,
   id_lista_convocazione_temporale integer,
   enabled boolean,
   id_stato_presentazione integer,
   CONSTRAINT relazione_convocazione_convocati_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE relazione_convocazione_convocati
  OWNER TO postgres;

CREATE TABLE storico_convocazioni
(  id serial NOT NULL,
   id_convocato integer,
   id_lista_convocazione integer,
   CONSTRAINT storico_convocazioni_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE storico_convocazioni
  OWNER TO postgres;


  ALTER TABLE CONVOCAZIONI 
  ADD id_lista_convocazione_temporale_ultima integer;
   

ALTER TABLE dati_convocazione
add id_asl integer;


ALTER TABLE access
add comune integer;