-- Table: opu_indirizzo

-- DROP TABLE opu_indirizzo;

CREATE TABLE opu_indirizzo
(
  id serial NOT NULL,
  via character varying(300),
  cap character(20),
  provincia character(500),
  nazione character varying(500),
  latitudine double precision,
  longitudine double precision,
  comune integer,
  riferimento_org_id integer,
  riferimento_address_id integer,
  address_type integer,
  CONSTRAINT opu_indirizzo_pkey PRIMARY KEY (id ),
  CONSTRAINT opu_indirizzo_comune_fkey FOREIGN KEY (comune)
      REFERENCES comuni1 (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_indirizzo
  OWNER TO postgres;
-- Table: opu_info_853_veicoli

-- DROP TABLE opu_info_853_veicoli;

CREATE TABLE opu_info_853_veicoli
(
  id serial NOT NULL,
  id_stabilimento integer,
  id_tipo_struttura integer,
  tipo_veicolo text,
  targa text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_info_853_veicoli
  OWNER TO postgres;

-- Table: opu_soggetto_fisico

-- DROP TABLE opu_soggetto_fisico;

CREATE TABLE opu_soggetto_fisico
(
  id serial NOT NULL,
  titolo character(20),
  cognome text,
  nome text,
  comune_nascita text,
  codice_fiscale character varying,
  enteredby integer,
  modifiedby integer,
  ipenteredby character(100),
  ipmodifiedby character(100),
  sesso character(1),
  telefono character(50),
  fax character(50),
  email character varying(100),
  telefono1 character(50),
  data_nascita timestamp without time zone,
  documento_identita text,
  indirizzo_id integer,
  provenienza_estera boolean DEFAULT false,
  riferimento_org_id integer,
  provincia_nascita text,
  CONSTRAINT opu_soggetto_fisico_pkey PRIMARY KEY (id ),
  CONSTRAINT opu_soggetto_fisico_indirizzo_id_fkey FOREIGN KEY (indirizzo_id)
      REFERENCES opu_indirizzo (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_soggetto_fisico
  OWNER TO postgres;

  -- Table: opu_informazioni_852


-- Table: opu_soggetto_fisico_mail

-- DROP TABLE opu_soggetto_fisico_mail;

CREATE TABLE opu_soggetto_fisico_mail
(
  id serial NOT NULL,
  id_soggetto_fisico integer,
  mail text,
  trashed_date timestamp without time zone,
  CONSTRAINT opu_soggetto_fisico_mail_pkey PRIMARY KEY (id ),
  CONSTRAINT opu_soggetto_fisico_mail_id_soggetto_fisico_fkey FOREIGN KEY (id_soggetto_fisico)
      REFERENCES opu_soggetto_fisico (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_soggetto_fisico_mail
  OWNER TO postgres;


  -- Table: opu_soggetto_fisico_storico

-- DROP TABLE opu_soggetto_fisico_storico;

CREATE TABLE opu_soggetto_fisico_storico
(
  id serial NOT NULL,
  titolo character(20),
  cognome text,
  nome text,
  comune_nascita text,
  provincia_nascita character(50),
  codice_fiscale character varying,
  indirizzo_residenza character varying(100),
  cap_residenza character varying(5),
  comune_residenza character varying(50),
  provincia_residenza character(50),
  enteredby integer,
  modifiedby integer,
  entered timestamp without time zone,
  modified timestamp without time zone,
  ipenteredby character(100),
  ipmodifiedby character(100),
  sesso character(1),
  telefono character(50),
  fax character(50),
  email character varying(100),
  telefono1 character(50),
  data_nascita timestamp without time zone,
  documento_identita text,
  indirizzo_id integer,
  id_opu_soggetto_fisico integer,
  CONSTRAINT opu_soggetto_fisico_storico_pkey PRIMARY KEY (id ),
  CONSTRAINT opu_soggetto_fisico_storico_indirizzo_id_fkey FOREIGN KEY (indirizzo_id)
      REFERENCES opu_indirizzo (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_soggetto_fisico_storico
  OWNER TO postgres;



CREATE TABLE opu_lookup_macrocategorie_linee_produttive
(
  code serial NOT NULL,
  description text NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  tipo_iter integer,
  entered timestamp without time zone,
  modified timestamp without time zone,
  CONSTRAINT opu_lookup_macrocategorie_linee_produttive_pkey PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_lookup_macrocategorie_linee_produttive
  OWNER TO postgres;

CREATE TABLE opu_lookup_attivita_linee_produttive_aggregazioni
(
  code serial NOT NULL,
  description text NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  id_aggregazione integer,
  codice text,
  entered timestamp without time zone,
  modified timestamp without time zone,
  CONSTRAINT lookup_attivita_linee_produttive_aggregazioni_pkey1 PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_lookup_attivita_linee_produttive_aggregazioni
  OWNER TO postgres;


CREATE TABLE opu_lookup_aggregazioni_linee_produttive
(
  code serial NOT NULL,
  description text NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  tipo_iter integer,
  id_macrocategorie_linee_produttive integer,
  entered timestamp without time zone,
  modified timestamp without time zone,
  CONSTRAINT lookup_aggregazioni_linee_produttive_pkey PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_lookup_aggregazioni_linee_produttive
  OWNER TO postgres;
-- Table: opu_relazione_attivita_produttive_aggregazioni


CREATE TABLE opu_linee_attivita_controlli_ufficiali
(
  id_controllo_ufficiale integer,
  id_linea_attivita integer,
  CONSTRAINT linee_attivita_controlli_ufficiali_id_controllo_ufficiale_fkey FOREIGN KEY (id_controllo_ufficiale)
      REFERENCES ticket (ticketid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT opu_relazione_stabilimento_linee_produttive_fkey FOREIGN KEY (id_linea_attivita)
      REFERENCES opu_relazione_stabilimento_linee_produttive (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_linee_attivita_controlli_ufficiali
  OWNER TO postgres;


CREATE TABLE opu_relazione_attivita_produttive_aggregazioni
(
  id serial NOT NULL,
  id_attivita_aggregazione integer,
  id_aggregazione integer,
  riferimento_la_linee_attivita integer,
  CONSTRAINT relazione_attivita_produttive_aggregazioni_pkey PRIMARY KEY (id ),
  CONSTRAINT relazione_attivita_produttive_agg_id_attivita_aggregazione_fkey FOREIGN KEY (id_attivita_aggregazione)
      REFERENCES opu_lookup_attivita_linee_produttive_aggregazioni (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT relazione_attivita_produttive_aggregazioni_id_aggregazione_fkey FOREIGN KEY (id_aggregazione)
      REFERENCES opu_lookup_aggregazioni_linee_produttive (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_relazione_attivita_produttive_aggregazioni
  OWNER TO postgres;

CREATE TABLE opu_lookup_stato_ruolo_soggetto
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT opu_lookup_stato_ruolo_soggetto_pkey PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_lookup_stato_ruolo_soggetto
  OWNER TO postgres;
logia_sede;

CREATE TABLE opu_lookup_tipologia_sede
(
  code serial NOT NULL,
  description character varying(255) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  entered timestamp(3) without time zone NOT NULL DEFAULT now(),
  modified timestamp(3) without time zone NOT NULL DEFAULT now(),
  CONSTRAINT opu_lookup_tipologia_sede_pkey PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_lookup_tipologia_sede
  OWNER TO postgres;


-- Table: opu_soggetto_fisico_telefono

-- DROP TABLE opu_soggetto_fisico_telefono;

CREATE TABLE opu_soggetto_fisico_telefono
(
  id serial NOT NULL,
  id_soggetto_fisico integer,
  telefono text,
  trashed_date timestamp without time zone,
  CONSTRAINT opu_soggetto_fisico_telefono_pkey PRIMARY KEY (id ),
  CONSTRAINT opu_soggetto_fisico_telefono_id_soggetto_fisico_fkey FOREIGN KEY (id_soggetto_fisico)
      REFERENCES opu_soggetto_fisico (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_soggetto_fisico_telefono
  OWNER TO postgres;

-- DRO

-- Table: opu_operatore

-- DROP TABLE opu_operatore;

CREATE TABLE opu_operatore
(
  id serial NOT NULL,
  codice_fiscale_impresa character varying,
  note text,
  partita_iva character varying(255),
  ragione_sociale character varying(300),
  enteredby integer,
  modifiedby integer,
  entered timestamp without time zone,
  modified timestamp without time zone,
  trashed_date timestamp without time zone,
  riferimento_org_id integer,
  CONSTRAINT opu_operatore_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_operatore
  OWNER TO postgres;


-- Table: opu_rel_operatore_soggetto_fisico

-- DROP TABLE opu_rel_operatore_soggetto_fisico;

CREATE TABLE opu_rel_operatore_soggetto_fisico
(
  id_operatore integer NOT NULL,
  id_soggetto_fisico integer NOT NULL,
  tipo_soggetto_fisico integer NOT NULL,
  data_fine timestamp without time zone,
  id serial NOT NULL,
  data_inizio timestamp without time zone,
  stato_ruolo integer,
  CONSTRAINT opu_rel_operatore_soggetto_fisico_pkey PRIMARY KEY (id ),
  CONSTRAINT rel_operatore_soggetto_fisico_id_operatore_fkey FOREIGN KEY (id_operatore)
      REFERENCES opu_operatore (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT rel_operatore_soggetto_fisico_id_soggetto_fisico_fkey FOREIGN KEY (id_soggetto_fisico)
      REFERENCES opu_soggetto_fisico (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT rel_operatore_soggetto_fisico_stato_ruolo_fkey FOREIGN KEY (stato_ruolo)
      REFERENCES opu_lookup_stato_ruolo_soggetto (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_rel_operatore_soggetto_fisico
  OWNER TO postgres;




-- Table: opu_relazione_operatore_sede

-- DROP TABLE opu_relazione_operatore_sede;

CREATE TABLE opu_relazione_operatore_sede
(
  id serial NOT NULL,
  id_operatore integer,
  id_indirizzo integer,
  tipologia_sede integer,
  data_inizio timestamp without time zone,
  data_fine timestamp without time zone,
  stato_sede integer,
  CONSTRAINT relazione_operatore_sede_pkey PRIMARY KEY (id ),
  CONSTRAINT relazione_operatore_sede_id_indirizzo_fkey FOREIGN KEY (id_indirizzo)
      REFERENCES opu_indirizzo (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT relazione_operatore_sede_id_operatore_fkey FOREIGN KEY (id_operatore)
      REFERENCES opu_operatore (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT relazione_operatore_sede_stato_ruolo_fkey FOREIGN KEY (stato_sede)
      REFERENCES opu_lookup_stato_ruolo_soggetto (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_relazione_operatore_sede
  OWNER TO postgres;


  -- Table: opu_stabilimento

-- DROP TABLE opu_stabilimento;

CREATE TABLE opu_stabilimento
(
  id serial NOT NULL,
  entered timestamp without time zone,
  modified timestamp without time zone,
  entered_by integer,
  id_operatore integer,
  modified_by integer,
  id_asl integer,
  id_soggetto_fisico integer,
  id_indirizzo integer,
  flag_fuori_regione boolean DEFAULT false,
  flag_modifica_residenza_fuori_asl_in_corso boolean,
  id_tipologia_sede integer,
  riferimento_org_id integer,
  trashed_date timestamp without time zone,
  CONSTRAINT stabilimento_pkey PRIMARY KEY (id ),
  CONSTRAINT stabilimento_id_soggetto_fisico_fkey FOREIGN KEY (id_soggetto_fisico)
      REFERENCES opu_soggetto_fisico (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_stabilimento
  OWNER TO postgres;



-- Table: opu_relazione_stabilimento_linee_produttive

-- DROP TABLE opu_relazione_stabilimento_linee_produttive;

CREATE TABLE opu_relazione_stabilimento_linee_produttive
(
  id serial NOT NULL,
  id_linea_produttiva integer,
  id_stabilimento integer,
  stato integer DEFAULT 1,
  data_inizio timestamp without time zone,
  data_fine timestamp without time zone,
  autorizzazione text,
  note character varying,
  telefono1 text,
  telefono2 text,
  mail1 text,
  mail2 text,
  fax text,
  tipo_attivita_produttiva integer,
  CONSTRAINT relazione_stabilimento_linee_produttive_pkey PRIMARY KEY (id ),
  CONSTRAINT relazione_stabilimento_linee_produttiv_id_linea_produttiva_fkey FOREIGN KEY (id_linea_produttiva)
      REFERENCES opu_relazione_attivita_produttive_aggregazioni (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT relazione_stabilimento_linee_produttive_id_stabilimento_fkey FOREIGN KEY (id_stabilimento)
      REFERENCES opu_stabilimento (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_relazione_stabilimento_linee_produttive
  OWNER TO postgres;


P TABLE opu_informazioni_852;

CREATE TABLE opu_informazioni_852
(
  id serial NOT NULL,
  id_stabilimento integer,
  codice_registrazione text,
  codice_interno text,
  id_opu_lookup_tipo_attivita integer,
  flag_vendita_canili boolean,
  domicilio_digitale text,
  servizio_competente integer,
  CONSTRAINT info_852_pkey PRIMARY KEY (id ),
  CONSTRAINT info_stabilimento_fkey FOREIGN KEY (id_stabilimento)
      REFERENCES opu_stabilimento (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opu_informazioni_852
  OWNER TO postgres;



-- View: opu_indirizzo_view

-- DROP VIEW opu_indirizzo_view;

CREATE OR REPLACE VIEW opu_indirizzo_view AS 
         SELECT 
                CASE
                    WHEN oa.addrline1 IS NOT NULL AND oa.addrline1::text <> ''::text THEN oa.addrline1
                    ELSE 'N.D'::character varying
                END AS via, 
                CASE
                    WHEN oa.postalcode IS NOT NULL AND oa.postalcode::text <> ''::text THEN oa.postalcode
                    ELSE 'N.D'::character varying
                END AS cap, COALESCE(COALESCE(lp.description, oa.state::text), 'N.D'::text) AS provincia, 
                CASE
                    WHEN oa.country IS NOT NULL AND oa.country::text <> ''::text THEN oa.country
                    ELSE 'N.D'::character varying
                END AS country, oa.latitude, oa.longitude, comuni.id AS id_comune, o.org_id, oa.address_id, oa.address_type
           FROM organization o
      JOIN organization_address oa ON oa.org_id = o.org_id
   LEFT JOIN comuni1 comuni ON oa.city::text ~~* comuni.nome::text
   LEFT JOIN lookup_province lp ON lp.code = comuni.cod_provincia::integer
  WHERE o.trashed_date IS NULL AND o.tipologia = 1 AND o.tipo_dest::text ~~* 'Autoveicolo'::text
UNION 
         SELECT 
                CASE
                    WHEN o.address_legale_rapp IS NOT NULL AND o.address_legale_rapp <> ''::text THEN o.address_legale_rapp::character varying
                    ELSE 'N.D'::character varying
                END AS via, 'N.D'::character varying AS cap, COALESCE(COALESCE(lp.description, 'N.D'::text), 'N.D'::text) AS provincia, 'N.D'::character varying AS country, 0 AS latitude, 0 AS longitude, comuni.id AS id_comune, o.org_id, (-1) AS address_id, (-1) AS address_type
           FROM organization o
      LEFT JOIN comuni1 comuni ON btrim(o.city_legale_rapp) ~~* comuni.nome::text
   LEFT JOIN lookup_province lp ON lp.code = comuni.cod_provincia::integer
  WHERE o.trashed_date IS NULL AND o.tipologia = 1 AND o.tipo_dest::text ~~* 'Autoveicolo'::text;

ALTER TABLE opu_indirizzo_view
  OWNER TO postgres;


-- View: opu_linee_attivita_stabilimenti_view

-- DROP VIEW opu_linee_attivita_stabilimenti_view;

CREATE OR REPLACE VIEW opu_linee_attivita_stabilimenti_view AS 
 SELECT stab.id, a33.description AS categoria, a22.description AS linea_attivita, stab.id_stabilimento AS org_id
   FROM opu_relazione_stabilimento_linee_produttive stab
   JOIN opu_relazione_attivita_produttive_aggregazioni a1 ON stab.id_linea_produttiva = a1.id
   JOIN opu_lookup_attivita_linee_produttive_aggregazioni a22 ON a1.id_attivita_aggregazione = a22.code
   JOIN opu_lookup_aggregazioni_linee_produttive a33 ON a33.code = a22.id_aggregazione;

ALTER TABLE opu_linee_attivita_stabilimenti_view
  OWNER TO postgres;



-- View: opu_operatore_mobili_view

-- DROP VIEW opu_operatore_mobili_view;

CREATE OR REPLACE VIEW opu_operatore_mobili_view AS 
 SELECT DISTINCT 
        CASE
            WHEN prop.codice_fiscale IS NOT NULL AND prop.codice_fiscale <> ''::bpchar THEN upper(btrim(prop.codice_fiscale::text))::bpchar
            ELSE 'N.D'::bpchar
        END AS codice_fiscale_impresa, 
        CASE
            WHEN prop.codice_fiscale IS NOT NULL AND prop.codice_fiscale <> ''::bpchar THEN upper(btrim(prop.codice_fiscale::text))::bpchar
            ELSE 'N.D'::bpchar
        END AS partita_iva, upper(btrim(prop.name::text)) AS ragione_sociale, prop.enteredby, prop.modifiedby, prop.entered, prop.modified, prop.org_id
   FROM organization prop
  WHERE prop.trashed_date IS NULL AND prop.tipologia = 1 AND prop.tipo_dest::text ~~* 'autoveicolo'::text
  ORDER BY upper(btrim(prop.name::text));

ALTER TABLE opu_operatore_mobili_view
  OWNER TO postgres;

-- View: opu_operatori_denormalizzati_view

-- DROP VIEW opu_operatori_denormalizzati_view;

CREATE OR REPLACE VIEW opu_operatori_denormalizzati_view AS 
 SELECT DISTINCT o.id AS id_opu_operatore, o.ragione_sociale, o.partita_iva, o.codice_fiscale_impresa, o.note, o.entered, o.modified, o.enteredby, o.modifiedby, sogg.nome AS nome_rapp_sede_legale, sogg.cognome AS cognome_rapp_sede_legale, sogg.codice_fiscale AS cf_rapp_sede_legale, i.comune, stab.id_asl, stab.id AS id_stabilimento, soggettostab.cognome AS cognome_rapp_stab, soggettostab.nome AS nome_rapp_stab, soggettostab.codice_fiscale AS cf_rapp_stab, comuni1.nome AS comune_stab, info.id_opu_lookup_tipo_attivita, info.codice_registrazione
   FROM opu_operatore o
   LEFT JOIN opu_stabilimento stab ON stab.id_operatore = o.id
   LEFT JOIN opu_indirizzo sedestab ON sedestab.id = stab.id_indirizzo
   LEFT JOIN comuni1 ON sedestab.comune = comuni1.id
   LEFT JOIN opu_informazioni_852 info ON info.id_stabilimento = stab.id
   LEFT JOIN opu_soggetto_fisico soggettostab ON soggettostab.id = stab.id_soggetto_fisico
   LEFT JOIN opu_indirizzo i ON i.id = stab.id_indirizzo
   LEFT JOIN opu_rel_operatore_soggetto_fisico rels ON rels.id_operatore = o.id
   LEFT JOIN opu_soggetto_fisico sogg ON sogg.id = rels.id_soggetto_fisico
  WHERE o.trashed_date IS NULL;

ALTER TABLE opu_operatori_denormalizzati_view
  OWNER TO postgres;




-- View: opu_rel_operatore_soggetto_fisico_view

-- DROP VIEW opu_rel_operatore_soggetto_fisico_view;

CREATE OR REPLACE VIEW opu_rel_operatore_soggetto_fisico_view AS 
 SELECT DISTINCT operatore.id AS id_operatore, soggetto_fisico.id AS id_soggetto_fisico, 2 AS tipo_soggetto_fisico, 1 AS stato_ruolo
   FROM organization prop
   JOIN opu_soggetto_fisico soggetto_fisico ON soggetto_fisico.riferimento_org_id = prop.org_id
   JOIN opu_operatore operatore ON operatore.riferimento_org_id = prop.org_id
  WHERE prop.trashed_date IS NULL AND prop.tipologia = 1 AND prop.tipo_dest::text ~~* 'autoveicolo'::text;

ALTER TABLE opu_rel_operatore_soggetto_fisico_view
  OWNER TO postgres;



-- View: opu_relazione_operatore_sede_view

-- DROP VIEW opu_relazione_operatore_sede_view;

CREATE OR REPLACE VIEW opu_relazione_operatore_sede_view AS 
 SELECT DISTINCT operatore.id AS id_operatore, 
        CASE
            WHEN indirizzo.id > 0 THEN indirizzo.id
            ELSE (-1)
        END AS id_indirizzo, 1, 1 AS stato_sede
   FROM organization prop
   LEFT JOIN opu_operatore operatore ON operatore.riferimento_org_id = prop.org_id
   LEFT JOIN opu_indirizzo indirizzo ON indirizzo.riferimento_org_id = prop.org_id AND indirizzo.address_type = 1
  WHERE prop.trashed_date IS NULL AND prop.tipologia = 1 AND prop.tipo_dest::text ~~* 'autoveicolo'::text;

ALTER TABLE opu_relazione_operatore_sede_view
  OWNER TO postgres;



-- View: opu_soggetto_fisico_view

-- DROP VIEW opu_soggetto_fisico_view;

CREATE OR REPLACE VIEW opu_soggetto_fisico_view AS 
 SELECT DISTINCT prop.cognome_rappresentante AS cognome, prop.nome_rappresentante AS nome, 
        CASE
            WHEN prop.luogo_nascita_rappresentante IS NOT NULL AND prop.luogo_nascita_rappresentante::text <> ''::text THEN prop.luogo_nascita_rappresentante
            ELSE 'N.D'::character varying
        END AS comune_nascita, 
        CASE
            WHEN prop.codice_fiscale_rappresentante IS NOT NULL AND prop.codice_fiscale_rappresentante::bpchar <> ''::bpchar THEN prop.codice_fiscale_rappresentante::bpchar
            ELSE 'N.D'::bpchar
        END AS codice_fiscale, prop.enteredby, prop.modifiedby, 
        CASE
            WHEN prop.sesso = true THEN 'M'::text
            ELSE 'F'::text
        END AS sesso, 
        CASE
            WHEN prop.data_nascita_rappresentante IS NOT NULL THEN prop.data_nascita_rappresentante
            ELSE NULL::timestamp without time zone
        END AS data_nascita, 
        CASE
            WHEN prop.documento_identita IS NOT NULL AND prop.documento_identita::text <> ''::text THEN prop.documento_identita
            ELSE 'N.D'::character varying
        END AS documento_identita, indirizzo.id AS id_indirizzo, prop.org_id
   FROM organization prop
   LEFT JOIN organization_address indirizzi ON indirizzi.org_id = prop.org_id AND indirizzi.address_type = 1
   LEFT JOIN opu_indirizzo indirizzo ON indirizzo.riferimento_org_id = prop.org_id AND indirizzo.address_type = (-1)
  WHERE prop.trashed_date IS NULL AND prop.tipologia = 1 AND prop.tipo_dest::text ~~* 'autoveicolo'::text;

ALTER TABLE opu_soggetto_fisico_view
  OWNER TO postgres;


alter table ticket add id_Stabilimento integer ;
alter table organization add id_stabilimento integer ;


CHI: Rita Mele
COSA: Modificata la vista degli operatori denormalizzati
QUANDO: 07/04/2014

alter table opu_stabilimento add column cun text;
alter table opu_stabilimento add column id_sinvsa integer;
alter table opu_stabilimento add column data_import timestamp without time zone;
alter table opu_stabilimento add column esito_import text;
alter table opu_stabilimento add column descrizione_errore text;

--ANAGRAFICHE OSM
CREATE OR REPLACE VIEW tracciato_record_osm_opu_sinvsa AS
select v.ragione_sociale, coalesce(v.codice_fiscale_impresa, v.partita_iva) as codice_fiscale, v.indirizzo_sede_legale, v.istat_legale, v.prov_sede_legale,
v.cap_sede_legale, '...' as telefono_legale, v.cf_rapp_sede_legale, v.nome_rapp_sede_legale, v.cognome_rapp_sede_legale, 
'M' as settore_sede_produttiva, v.indirizzo_stab as indirizzo_sede_operativa, v.istat_operativo, v.prov_stab as provincia_sede_operativa, 
v.cap_stab as cap_sede_operativa, 
CASE
   WHEN v.id_asl = 201 THEN 'R201'::text
   WHEN v.id_asl = 202 THEN 'R103'::text
   WHEN v.id_asl = 203 THEN 'R203'::text
   WHEN v.id_asl = 204 THEN 'R106'::text
   WHEN v.id_asl = 205 THEN 'R205'::text
   WHEN v.id_asl = 206 THEN 'R206'::text
   WHEN v.id_asl = 207 THEN 'R111'::text
ELSE NULL::text
END AS asl_stabilimento, '...' as telefono_operativo, '183/2005' as codice_norma,
v.codice_registrazione as numero, 'N' as flag_riconoscimento,  ol.codice_macroarea as codice_sezione, ol.macroarea as sezione, 
ol.codice_aggreazione as codice_attivita, ol.aggregazione as attivita, ol.codice_attivita as codice_prodotto_specie, ol.attivita as prodotto_specie,
'S' as flag_attivita_principale, os.data_inizio as data_decorrenza, v.data_import, v.esito_import, v.descrizione_errore, v.id_sinvsa,v.cun
from opu_operatori_denormalizzati_view v
left join opu_relazione_stabilimento_linee_produttive os on os.id_stabilimento = v.id_stabilimento 
left join opu_linee_attivita_nuove ol on ol.id_nuova_linea_attivita = os.id_linea_produttiva
where norma = '183/2005'

--ANAGRAFICA SOA
CREATE OR REPLACE VIEW tracciato_record_soa_opu_sinvsa AS
select v.ragione_sociale, coalesce(v.codice_fiscale_impresa, v.partita_iva) as codice_fiscale, v.indirizzo_sede_legale, v.istat_legale, v.prov_sede_legale,
v.cap_sede_legale, '...' as telefono_legale, v.cf_rapp_sede_legale, v.nome_rapp_sede_legale, v.cognome_rapp_sede_legale, 
'M' as settore_sede_produttiva, v.indirizzo_stab as indirizzo_sede_operativa, v.istat_operativo, v.prov_stab as provincia_sede_operativa, 
v.cap_stab as cap_sede_operativa, 
CASE
   WHEN v.id_asl = 201 THEN 'R201'::text
   WHEN v.id_asl = 202 THEN 'R103'::text
   WHEN v.id_asl = 203 THEN 'R203'::text
   WHEN v.id_asl = 204 THEN 'R106'::text
   WHEN v.id_asl = 205 THEN 'R205'::text
   WHEN v.id_asl = 206 THEN 'R206'::text
   WHEN v.id_asl = 207 THEN 'R111'::text
ELSE NULL::text
END AS asl_stabilimento, '...' as telefono_operativo, norma as codice_norma,
v.codice_registrazione as numero, 'N' as flag_riconoscimento,  ol.codice_macroarea as codice_sezione, ol.macroarea as sezione, 
ol.codice_aggreazione as codice_attivita, ol.aggregazione as attivita, ol.codice_attivita as codice_prodotto_specie, ol.attivita as prodotto_specie,
'S' as flag_attivita_principale, os.data_inizio as data_decorrenza, v.data_import, v.esito_import, v.descrizione_errore, v.id_sinvsa,v.cun
from opu_operatori_denormalizzati_view v
left join opu_relazione_stabilimento_linee_produttive os on os.id_stabilimento = v.id_stabilimento 
left join opu_linee_attivita_nuove ol on ol.id_nuova_linea_attivita = os.id_linea_produttiva
where norma = '1069/2009'

--INSERIMENTO NUOVO QD 15/04/2014
insert into lookup_tipo_ispezione(description, codice_esame) values ('VERIFICA CONFORMITA'' PRODOTTI VEGETALI - TERRA DEI FUOCHI D.M. 11/3/2014','DMTDF')



-- Modifiche visualizzazione scheda verbale PNAA per nuovo operatore
CREATE OR REPLACE FUNCTION public.opu_can_pnaa(id_cu integer)
  RETURNS boolean AS
$BODY$
   DECLARE
org_id          int;
id_stabilimento_var int;
tipologia       int;   
BEGIN
	org_id := (select t.org_id 
	        from ticket t, organization o 
	        where o.org_id = t.org_id and 
	              t.ticketid = id_cu and 
	              t.tipologia = 3 and
	              t.trashed_date is null and
	              o.trashed_date is null
               );
               
       tipologia := (select o.tipologia 
	        from ticket t, organization o 
	        where o.org_id = t.org_id and 
	              t.ticketid = id_cu and 
	              t.tipologia = 3 and
	              t.trashed_date is null and
	              o.trashed_date is null
               );

       id_stabilimento_var := (select t.id_stabilimento
	        from ticket t, opu_stabilimento o 
	        where o.id = t.id_stabilimento and 
	              t.ticketid = id_cu and 
	              t.tipologia = 3 and
	              t.trashed_date is null and
	              o.trashed_date is null
               );
               
IF (org_id is not null and org_id>0 and tipologia in (1,800,801,97,2) )  THEN
    return true;
ELSIF (org_id is not null and org_id>0 and tipologia not in (1,800,801,97,2) ) THEN
    return false;
ELSIF(id_stabilimento_var is not null and (select m.flag_pnaa from opu_relazione_stabilimento_linee_produttive t 
                                           join master_list_suap m on m.id = t.id_linea_produttiva
                                           where id_stabilimento = id_stabilimento_var)) THEN
    return true;
ELSIF(id_stabilimento_var is not null and (select m.flag_pnaa from opu_relazione_stabilimento_linee_produttive t 
                                           join master_list_suap m on m.id = t.id_linea_produttiva
                                           where id_stabilimento = id_stabilimento_var)=false) THEN
    return true;
ELSE
    return false;
END IF;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.opu_can_pnaa(integer)
  OWNER TO postgres;




  CREATE OR REPLACE FUNCTION opu_can_pnaa_solo_opu(id_cu integer)
  RETURNS boolean AS
$BODY$
DECLARE
id_stabilimento_var int;
conta integer;
risultato integer;
r RECORD;	
BEGIN
       id_stabilimento_var := (select t.id_stabilimento
	        from ticket t, opu_stabilimento o 
	        where o.id = t.id_stabilimento and 
	              t.ticketid = id_cu and 
	              t.tipologia = 3 and
	              t.trashed_date is null and
	              o.trashed_date is null

                 );
       --qui c'è il problema...stat non è una variabile ma un record..quindi non si può fare sta=true...
       conta := (select count(*) from opu_linee_attivita_controlli_ufficiali ol
					   join opu_relazione_stabilimento_linee_produttive ol1 on ol1.id = ol.id_linea_attivita
					   join opu_linee_attivita_nuove o on o.id_nuova_linea_attivita = ol1.id_linea_produttiva
			                   join master_list_suap m on m.id = o.id_macroarea
					   where ol.id_controllo_ufficiale = id_cu and m.flag_pnaa);                 
        
IF(id_stabilimento_var > 0 and conta > 0) THEN
   risultato:= 1;
ELSE
   risultato:= 0;
END IF;

if(risultato=1) then
	return true;
else
	return false;
end if;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION opu_can_pnaa_solo_opu(integer)
  OWNER TO postgres;






CREATE OR REPLACE VIEW public.view_operatori_pnaa_modificata AS 
 SELECT DISTINCT t.org_id,
    t.location AS num_verbale,
        CASE
            WHEN t.motivazione_piano_campione IS NOT NULL THEN lpm.description
            ELSE lti.description
        END AS motivazione,
        CASE
            WHEN o.tipologia = 97 OR o.tipologia = 3 THEN o.numaut
            ELSE o.account_number
        END AS numero_registrazione_osa,
        CASE
            WHEN t.motivazione_piano_campione IS NOT NULL THEN lpm.codice_esame
            ELSE lti.codice_esame
        END AS barcode_motivazione,
    t.ticketid AS id_campione,
    t2.ticketid AS id_controllo,
    l.description AS ente_appartenenza,
    ut.descrizione_lunga AS unita_territoriale,
    date_part('years'::text, t2.assigned_date) AS anno,
    date_part('day'::text, t2.assigned_date) AS giorno,
    date_part('month'::text, t2.assigned_date) AS mese,
    ((((((((
        CASE
            WHEN t2.componente_nucleo IS NOT NULL THEN t2.componente_nucleo || ' '::text
            ELSE ''::text
        END ||
        CASE
            WHEN t2.componente_nucleo_due IS NOT NULL THEN t2.componente_nucleo_due || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_tre IS NOT NULL THEN t2.componente_nucleo_tre || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_quattro IS NOT NULL THEN t2.componente_nucleo_quattro || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_cinque IS NOT NULL THEN t2.componente_nucleo_cinque || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_sei IS NOT NULL THEN t2.componente_nucleo_sei || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_sette IS NOT NULL THEN t2.componente_nucleo_sette || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_otto IS NOT NULL THEN t2.componente_nucleo_otto || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_nove IS NOT NULL THEN t2.componente_nucleo_nove || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_dieci IS NOT NULL THEN t2.componente_nucleo_dieci || ' '::text
            ELSE ''::text
        END AS componenti_nucleo_ispettivo,
        CASE
            WHEN lpm.description::text ~~* '%SORV%'::text THEN '007'::text
            WHEN lpm.description::text ~~* '%MON%'::text THEN '003'::text
            WHEN lpm.description::text ~~* '%EXTRAPIANO%'::text THEN '005'::text
            WHEN lti.description::text ~~* '%SOSPETT%'::text THEN '001'::text
            ELSE 'N.D.'::text
        END AS a1,
        CASE
            WHEN lpm.description::text ~~* '%BSE%'::text THEN '1'::text
            WHEN lpm.description::text ~~* '%SOSTANZE FARMACOLOGICHE%CARRY OVER)'::text THEN '8'::text
            WHEN lpm.description::text ~~* '%SOSTANZE FARMACOLOGICHE)'::text OR lpm.description::text ~~* '%MONIT. OLIGOELEMENTI%'::text THEN '2'::text
            WHEN lpm.description::text ~~* '%contaminanti%'::text THEN '3'::text
            WHEN lpm.description::text ~~* '%DIOSSINE%'::text THEN '4'::text
            WHEN lpm.description::text ~~* '%MICOTOSSINE%'::text THEN '5'::text
            WHEN lpm.description::text ~~* '%SALMONELLA%'::text THEN '6'::text
            WHEN lpm.description::text ~~* '%OGM%'::text THEN '7'::text
            ELSE '9'::text
        END AS a3,
    ((((((((
        CASE
            WHEN t2.componente_nucleo IS NOT NULL THEN t2.componente_nucleo || ' '::text
            ELSE ''::text
        END ||
        CASE
            WHEN t2.componente_nucleo_due IS NOT NULL THEN t2.componente_nucleo_due || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_tre IS NOT NULL THEN t2.componente_nucleo_tre || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_quattro IS NOT NULL THEN t2.componente_nucleo_quattro || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_cinque IS NOT NULL THEN t2.componente_nucleo_cinque || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_sei IS NOT NULL THEN t2.componente_nucleo_sei || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_sette IS NOT NULL THEN t2.componente_nucleo_sette || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_otto IS NOT NULL THEN t2.componente_nucleo_otto || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_nove IS NOT NULL THEN t2.componente_nucleo_nove || ' '::text
            ELSE ''::text
        END) ||
        CASE
            WHEN t2.componente_nucleo_dieci IS NOT NULL THEN t2.componente_nucleo_dieci || ' '::text
            ELSE ''::text
        END AS a4,
        CASE
            WHEN o.tipologia = 3 OR o.tipologia = 97 THEN COALESCE(oa5.addrline1, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.addrline1, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.addrline1, 'N.D.'::character varying)
            WHEN o.tipologia = 2 THEN COALESCE(az.indrizzo_azienda, 'N.D.'::character varying::text)::character varying
            ELSE COALESCE(oa5.addrline1, oa1.addrline1, oa7.addrline1, 'N.D.'::character varying)
        END AS a8,
        CASE
            WHEN o.tipologia = 3 OR o.tipologia = 97 THEN COALESCE(oa5.city, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.city, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.city, 'N.D.'::character varying)
            WHEN o.tipologia = 2 THEN COALESCE(c.comune, 'N.D.'::character varying)
            ELSE COALESCE(oa5.city, oa1.city, oa7.city, 'N.D.'::character varying)
        END AS a9,
        CASE
            WHEN o.tipologia = 3 OR o.tipologia = 97 THEN COALESCE(oa5.state, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.state, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.state, 'N.D.'::character varying)
            WHEN o.tipologia = 2 THEN COALESCE(az.prov_sede_azienda, 'N.D.'::character varying::text)::character varying
            ELSE COALESCE(oa5.state, oa1.state, oa7.state, 'N.D.'::character varying)
        END AS a10,
        CASE
            WHEN o.tipologia = 3 OR o.tipologia = 97 THEN oa5.latitude
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.latitude
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN oa1.latitude
            WHEN o.tipologia = 2 THEN COALESCE(az.latitudine_geo, 0::double precision)
            ELSE COALESCE(oa5.latitude, oa1.latitude, oa7.latitude)
        END AS a11_1,
        CASE
            WHEN o.tipologia = 3 OR o.tipologia = 97 THEN oa5.longitude
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN oa5.longitude
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN oa1.longitude
            WHEN o.tipologia = 2 THEN COALESCE(az.longitudine_geo, 0::double precision)
            ELSE COALESCE(oa5.longitude, oa1.longitude, oa7.longitude)
        END AS a11_2,
        CASE
            WHEN o.tipologia = 2 THEN op_prop.nominativo::character varying
            ELSE o.name
        END AS a12,
    o.name AS a12b,
        CASE
            WHEN o.tipologia = 2 THEN op_prop.nominativo
            WHEN o.nome_rappresentante IS NULL AND o.cognome_rappresentante IS NULL THEN ''::text
            WHEN o.nome_rappresentante::text = ' '::text AND o.cognome_rappresentante::text = ' '::text THEN ''::text
            ELSE (o.nome_rappresentante::text || ' '::text) || o.cognome_rappresentante::text
        END AS a13,
        CASE
            WHEN o.tipologia = 2 AND op_prop.cf IS NOT NULL THEN op_prop.cf
            ELSE o.codice_fiscale_rappresentante::text
        END AS a14,
        CASE
            WHEN o.tipologia = 2 THEN op_detentore.nominativo
            WHEN o.nome_rappresentante IS NULL AND o.cognome_rappresentante IS NULL THEN ''::text
            WHEN o.nome_rappresentante::text = ' '::text AND o.cognome_rappresentante::text = ' '::text THEN ''::text
            ELSE (o.nome_rappresentante::text || ' '::text) || o.cognome_rappresentante::text
        END AS a15,
        CASE
            WHEN o.tipologia = 2 AND op_detentore.cf IS NOT NULL THEN op_detentore.cf
            ELSE o.codice_fiscale_rappresentante::text
        END AS a15b,
    t.check_circuito_ogm AS b7
   FROM organization o
     LEFT JOIN ticket t ON t.org_id = o.org_id AND t.tipologia = 2 AND t.trashed_date IS NULL
     LEFT JOIN ticket t2 ON t2.ticketid = t.id_controllo_ufficiale::integer AND t2.tipologia = 3
     LEFT JOIN tipocontrolloufficialeimprese tcu ON tcu.idcontrollo = t2.ticketid AND tcu.enabled AND tcu.pianomonitoraggio = t.motivazione_piano_campione
     LEFT JOIN strutture_asl ut ON ut.id = tcu.id_unita_operativa
     LEFT JOIN matrici_campioni mc ON mc.id_campione = t.ticketid
     LEFT JOIN matrici m ON m.matrice_id = mc.id_matrice
     LEFT JOIN lookup_tipo_ispezione lti ON lti.code = t.motivazione_campione
     LEFT JOIN lookup_piano_monitoraggio lpm ON lpm.code = t.motivazione_piano_campione
     LEFT JOIN lookup_tipologia_operatore lto ON lto.code = o.tipologia
     LEFT JOIN aziende az ON az.cod_azienda = o.account_number::text AND o.tipologia = 2
     LEFT JOIN comuni_old c ON c.codiceistatcomune::text = az.cod_comune_azienda
     LEFT JOIN operatori_allevamenti op_detentore ON o.cf_correntista::text = op_detentore.cf
     LEFT JOIN operatori_allevamenti op_prop ON o.codice_fiscale_rappresentante::text = op_prop.cf
     LEFT JOIN lookup_site_id l ON l.code = t.site_id
     LEFT JOIN organization_address oa5 ON oa5.org_id = o.org_id AND oa5.address_type = 5
     LEFT JOIN organization_address oa1 ON oa1.org_id = o.org_id AND oa1.address_type = 1
     LEFT JOIN organization_address oa7 ON oa7.org_id = o.org_id AND oa7.address_type = 7
  WHERE o.trashed_date IS NULL AND o.org_id <> 0 AND o.tipologia <> 0 AND t.trashed_date IS NULL AND t2.trashed_date IS NULL AND (lpm.codice_interno = 370 OR lti.description::text ~~* '%SOSPETT%'::text);

ALTER TABLE public.view_operatori_pnaa_modificata
  OWNER TO postgres;
GRANT ALL ON TABLE public.view_operatori_pnaa_modificata TO postgres;
GRANT SELECT ON TABLE public.view_operatori_pnaa_modificata TO usr_ro;
GRANT SELECT ON TABLE public.view_operatori_pnaa_modificata TO report;
