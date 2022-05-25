CREATE TABLE sca_storico_operazioni_utenti
(
  user_id integer NOT NULL,
  username character varying(255) NOT NULL,
  ip character varying(20) NOT NULL,
  data timestamp without time zone NOT NULL,
  path text NOT NULL,
  automatico boolean NOT NULL DEFAULT false,
  id serial NOT NULL,
  parametri text,
  browser text,
  CONSTRAINT pk_sca PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE sca_storico_operazioni_utenti
  OWNER TO postgres;
  
alter table guc_ruoli add column codice_raggruppamento integer;  

CHI: Rita Mele
COSA: Aggiunta nuova colonna in guc_utenti
QUANDO: 30/04/2015

alter table guc_utenti add column password2 character varying(255);
alter table login_fallite add column n_attempts int;
alter table login_fallite add column last_attempts timestamp with time zone;

-- Aggiornare il campo password2 tramite la funzione dblink tra gisa e guc
 alter table login_fallite add column blocked boolean
