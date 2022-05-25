-- Table: dati_utente_controlliufficiali

-- DROP TABLE dati_utente_controlliufficiali;

-- Table: dati_utente_controlliufficiali

-- DROP TABLE dati_utente_controlliufficiali;

CREATE TABLE dati_utente_controlliufficiali
(
  servizio text,
  id serial NOT NULL,
  id_controllo integer,
  uo text,
  via_amm text,
  presente_ispezione text,
  luogo_nascita text,
  giorno_nascita text,
  mese_nascita text,
  anno_nascita text,
  luogo_residenza text,
  via_ispezione text,
  civico_ispezione text,
  doc_identita text,
  strumenti_ispezione text,
  desc_risoluzione text,
  dichiarazione text,
  responsabile text,
  note text,
  n_copie text,
  flag text,
  desc_risoluzione_iniz text,
  CONSTRAINT id PRIMARY KEY (id)
)
WITH (OIDS=FALSE);
ALTER TABLE dati_utente_controlliufficiali OWNER TO postgres;


-------------------------------------------

ALTER TABLE ticket ADD COLUMN flag_mod5 text;
update ticket set flag_mod5  = 'true';
update dati_utente_controlliufficiali set flag = 'true'
-------------------------------------------------------

