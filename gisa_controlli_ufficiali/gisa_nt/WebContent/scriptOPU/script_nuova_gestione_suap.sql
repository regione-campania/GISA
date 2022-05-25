insert into  lookup_opu_tipo_impresa_societa values (-1,'',false,0,false,1,null,null,null,null)

/*CREAZIONE TABELLE RICHIESTE PER SUAP*/

-- Table: suap_lookup_tipo_richiesta

-- DROP TABLE suap_lookup_tipo_richiesta;

CREATE TABLE suap_lookup_tipo_richiesta
(
  code serial NOT NULL,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT suap_lookup_tipo_richiesta_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE suap_lookup_tipo_richiesta
  OWNER TO postgres;
GRANT ALL ON TABLE suap_lookup_tipo_richiesta TO postgres;
GRANT SELECT ON TABLE suap_lookup_tipo_richiesta TO report;
GRANT SELECT ON TABLE suap_lookup_tipo_richiesta TO usr_ro;

INSERT INTO suap_lookup_tipo_richiesta VALUES(1,'SCIA NUOVO STABILIMENTO',FALSE,0,TRUE);
INSERT INTO suap_lookup_tipo_richiesta VALUES(2,'SCIA AMPLIAMENTO',FALSE,0,TRUE);
INSERT INTO suap_lookup_tipo_richiesta VALUES(3,'SCIA CESSAZIONE',FALSE,0,TRUE);
INSERT INTO suap_lookup_tipo_richiesta VALUES(4,'SCIA VARIAZIONE TITOLARITA',FALSE,0,TRUE);



-- Table: suap_ric_scia_operatore

-- DROP TABLE suap_ric_scia_operatore;

CREATE TABLE suap_ric_scia_operatore
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
  domicilio_digitale text,
  tipo_impresa integer,
  tipo_societa integer DEFAULT (-1),
  note_internal_use_only_hd text,
  id_indirizzo integer,
  id_tipo_richiesta integer,
  validato boolean,
  CONSTRAINT suap_ric_scia_op_pkey PRIMARY KEY (id),
  CONSTRAINT suap_ric_scia_operatore_id_indirizzo_fkey FOREIGN KEY (id_indirizzo)
      REFERENCES opu_indirizzo (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT suap_ric_scia_operatore_tipo_impresa_fkey FOREIGN KEY (tipo_impresa)
      REFERENCES lookup_opu_tipo_impresa (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT suap_ric_scia_operatore_tipo_societa_fkey FOREIGN KEY (tipo_societa)
      REFERENCES lookup_opu_tipo_impresa_societa (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE suap_ric_scia_operatore
  OWNER TO postgres;
GRANT ALL ON TABLE suap_ric_scia_operatore TO postgres;
GRANT SELECT ON TABLE suap_ric_scia_operatore TO report;
GRANT SELECT ON TABLE suap_ric_scia_operatore TO usr_ro;

-- Table: suap_ric_scia_soggetto_fisico

-- DROP TABLE suap_ric_scia_soggetto_fisico;

CREATE TABLE suap_ric_scia_soggetto_fisico
(
  id serial NOT NULL,
  cognome text,
  nome text,
  comune_nascita text,
  codice_fiscale character varying,
  enteredby integer,
  modifiedby integer,
  sesso character(1),
  telefono character(50),
  fax character(50),
  email character varying(100),
  telefono1 character(50),
  data_nascita timestamp without time zone,
  documento_identita text,
  indirizzo_id integer,
  provenienza_estera boolean DEFAULT false,
  provincia_nascita text,
  trashed_date timestamp without time zone,
  CONSTRAINT suap_ric_scia_soggetto_fisico_pkey PRIMARY KEY (id),
  CONSTRAINT suap_ric_scia_soggetto_fisico_indirizzo_id_fkey FOREIGN KEY (indirizzo_id)
      REFERENCES opu_indirizzo (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE suap_ric_scia_soggetto_fisico
  OWNER TO postgres;
GRANT ALL ON TABLE suap_ric_scia_soggetto_fisico TO postgres;
GRANT SELECT ON TABLE suap_ric_scia_soggetto_fisico TO report;
GRANT SELECT ON TABLE suap_ric_scia_soggetto_fisico TO usr_ro;


-- Table: suap_ric_scia_rel_operatore_soggetto_fisico

-- DROP TABLE suap_ric_scia_rel_operatore_soggetto_fisico;

CREATE TABLE suap_ric_scia_rel_operatore_soggetto_fisico
(
  id_operatore integer NOT NULL,
  id_soggetto_fisico integer NOT NULL,
  tipo_soggetto_fisico integer NOT NULL,
  data_fine timestamp without time zone,
  id serial NOT NULL,
  data_inizio timestamp without time zone,
  stato_ruolo integer,
  enabled boolean,
  CONSTRAINT suap_ric_scia__rel_operatore_soggetto_fisico_pkey PRIMARY KEY (id),
  CONSTRAINT suap_ric_scia_rel_operatore_soggetto_fi_id_soggetto_fisico_fkey FOREIGN KEY (id_soggetto_fisico)
      REFERENCES suap_ric_scia_soggetto_fisico (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT suap_ric_scia_rel_operatore_soggetto_fisico_id_operatore_fkey FOREIGN KEY (id_operatore)
      REFERENCES suap_ric_scia_operatore (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE suap_ric_scia_rel_operatore_soggetto_fisico
  OWNER TO postgres;
GRANT ALL ON TABLE suap_ric_scia_rel_operatore_soggetto_fisico TO postgres;
GRANT SELECT ON TABLE suap_ric_scia_rel_operatore_soggetto_fisico TO report;
GRANT SELECT ON TABLE suap_ric_scia_rel_operatore_soggetto_fisico TO usr_ro;


-- Table: suap_ric_scia_stabilimento

-- DROP TABLE suap_ric_scia_stabilimento;

CREATE TABLE suap_ric_scia_stabilimento
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
  stato integer,
  numero_registrazione text,
  data_generazione_numero timestamp without time zone,
  denominazione text,
  trashed_date timestamp without time zone,
  notes_hd text,
  tipo_attivita integer,
  tipo_carattere integer,
  data_inizio_attivita timestamp without time zone,
  data_fine_attivita timestamp without time zone,
  note text,
  numero_registrazione_variazione text,
  cessazione_stabilimento boolean,
  CONSTRAINT suap_ric_sciastabilimento_pkey PRIMARY KEY (id),
  CONSTRAINT suap_ric_scia_stabilimento_id_operatore_fkey FOREIGN KEY (id_operatore)
      REFERENCES suap_ric_scia_operatore (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT suap_ric_sciasede_operativa_fkey FOREIGN KEY (id_indirizzo)
      REFERENCES opu_indirizzo (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE suap_ric_scia_stabilimento
  OWNER TO postgres;
GRANT ALL ON TABLE suap_ric_scia_stabilimento TO postgres;
GRANT SELECT ON TABLE suap_ric_scia_stabilimento TO report;
GRANT SELECT ON TABLE suap_ric_scia_stabilimento TO usr_ro;


-- Table: suap_ric_scia_relazione_stabilimento_linee_produttive

-- DROP TABLE suap_ric_scia_relazione_stabilimento_linee_produttive;

CREATE TABLE suap_ric_scia_relazione_stabilimento_linee_produttive
(
  id serial NOT NULL,
  id_linea_produttiva integer,
  id_stabilimento integer,
  stato integer DEFAULT 1,
  data_inizio timestamp without time zone,
  data_fine timestamp without time zone,
  primario boolean,
  enabled boolean,
  modified timestamp without time zone,
  modifiedby integer,
  numero_registrazione text,
  data_generazione_numero timestamp without time zone,
  tipo_attivita_produttiva integer,
  CONSTRAINT suap_ric_scia_relazione_stabilimento_linee_produttive_pkey PRIMARY KEY (id),
  CONSTRAINT suap_ric_scia_relazione_stabilimento_l_id_linea_produttiva_fkey FOREIGN KEY (id_linea_produttiva)
      REFERENCES master_list_suap (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT suap_ric_scia_relazione_stabilimento_linee_produttive_id_stabil FOREIGN KEY (id_stabilimento)
      REFERENCES suap_ric_scia_stabilimento (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT suap_ric_scia_stato_fkey FOREIGN KEY (stato)
      REFERENCES lookup_stato_lab (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE suap_ric_scia_relazione_stabilimento_linee_produttive
  OWNER TO postgres;
GRANT ALL ON TABLE suap_ric_scia_relazione_stabilimento_linee_produttive TO postgres;
GRANT SELECT ON TABLE suap_ric_scia_relazione_stabilimento_linee_produttive TO report;
GRANT SELECT ON TABLE suap_ric_scia_relazione_stabilimento_linee_produttive TO usr_ro;

-- Table: suap_ric_scia_mobile

-- DROP TABLE suap_ric_scia_mobile;

CREATE TABLE suap_ric_scia_mobile
(
  id serial NOT NULL,
  id_stabilimento integer,
  targa text,
  tipo integer,
  carta text,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE suap_ric_scia_mobile
  OWNER TO postgres;
GRANT ALL ON TABLE suap_ric_scia_mobile TO postgres;
GRANT SELECT ON TABLE suap_ric_scia_mobile TO report;

/*FINE CREAZIONE TABELLE*/

/*DBI CENTRALIZZATE SU INDIRIZZI*/

-- Function: suap_dbi_cerca_indirizzo_per_comune_indirizzo(text, text)

-- DROP FUNCTION suap_dbi_cerca_indirizzo_per_comune_indirizzo(text, text);

CREATE OR REPLACE FUNCTION suap_dbi_cerca_indirizzo_per_comune_indirizzo(IN searchcomune text, IN searchvia text)
  RETURNS TABLE(id integer, via text, cap text, provincia text, nazione text, latitudine double precision, longitudine double precision, comune integer, riferimento_org_id integer, riferimento_address_id integer, address_type integer, comune_testo text, toponimo text, civico text, code integer, description text, cod_provincia text, descrizione_comune text, descrizione_provincia text) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR 
		id  , via  , cap  ,provincia  , nazione ,latitudine  , longitudine ,comune ,
riferimento_org_id  , riferimento_address_id ,address_type ,comune_testo , toponimo  , civico  ,code ,

description ,cod_provincia ,descrizione_comune , descrizione_provincia 
		in

select 
ind.id , ind.via , ind.cap , ind.provincia  , ind.nazione ,ind.latitudine  , ind.longitudine ,ind.comune ,
ind.riferimento_org_id  , ind.riferimento_address_id ,ind.address_type ,ind.comune_testo as com , ind.toponimo  , ind.civico
,asl.code , asl.description ,c.cod_provincia,coalesce (c.nome,ind.comune_testo) as descrizionecomune,
lp.description as descrizioneprovincia  
from opu_indirizzo ind 
join comuni1 c on c.id =ind.comune 
left join lookup_site_id asl on (c.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true 
left join lookup_province lp on lp.code = c.cod_provincia::int   where c.nome ilike searchcomune and ind.via  ilike searchvia		

		
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION suap_dbi_cerca_indirizzo_per_comune_indirizzo(text, text)
  OWNER TO postgres;
-- Function: suap_dbi_cerca_indirizzo_per_id(integer)

-- DROP FUNCTION suap_dbi_cerca_indirizzo_per_id(integer);

CREATE OR REPLACE FUNCTION suap_dbi_cerca_indirizzo_per_id(IN iddindirizzo integer)
  RETURNS TABLE(id integer, via text, cap text, provincia text, nazione text, latitudine double precision, longitudine double precision, comune integer, riferimento_org_id integer, riferimento_address_id integer, address_type integer, comune_testo text, toponimo text, civico text, code integer, description text, cod_provincia text, descrizione_comune text, descrizione_provincia text) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR 
		id  , via  , cap  ,provincia  , nazione ,latitudine  , longitudine ,comune ,
riferimento_org_id  , riferimento_address_id ,address_type ,comune_testo , toponimo  , civico  ,code ,

description ,cod_provincia ,descrizione_comune , descrizione_provincia 
		in

select 
ind.id , ind.via , ind.cap , ind.provincia  , ind.nazione ,ind.latitudine  , ind.longitudine ,ind.comune ,
ind.riferimento_org_id  , ind.riferimento_address_id ,ind.address_type ,ind.comune_testo as com , ind.toponimo  , ind.civico
,asl.code , asl.description ,c.cod_provincia,coalesce (c.nome,ind.comune_testo) as descrizionecomune,
lp.description as descrizioneprovincia  
from opu_indirizzo ind 
join comuni1 c on c.id =ind.comune 
left join lookup_site_id asl on (c.codiceistatasl)::int = asl.codiceistat::int   and asl.enabled=true 
left join lookup_province lp on lp.code = c.cod_provincia::int   where ind.id = iddindirizzo	

		
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION suap_dbi_cerca_indirizzo_per_id(integer)
  OWNER TO postgres;
-- Function: suap_dbi_verifica_esistenza_indirizzo(integer, text, text, integer, text, text)

-- DROP FUNCTION suap_dbi_verifica_esistenza_indirizzo(integer, text, text, integer, text, text);

CREATE OR REPLACE FUNCTION suap_dbi_verifica_esistenza_indirizzo(comunein integer, provinciain text, viain text, toponimoin integer, civicoin text, capin text)
  RETURNS integer AS
$BODY$
DECLARE
	idIndirizzo integer;
	
	 	
BEGIN
idIndirizzo =-2;
idIndirizzo:= (select id from opu_indirizzo where comune = comuneIn and trim(lower(provincia)) = trim(lower(provinciaIn)) and trim(lower(via)) = trim(lower(viaIn)) and toponimo =toponimoIn and civico =civicoIn and cap =capIn limit 1);

		return idIndirizzo ;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION suap_dbi_verifica_esistenza_indirizzo(integer, text, text, integer, text, text)
  OWNER TO postgres;
-- Function: suap_get_lista_richieste(text, text, timestamp without time zone, integer)

-- DROP FUNCTION suap_get_lista_richieste(text, text, timestamp without time zone, integer);

CREATE OR REPLACE FUNCTION suap_get_lista_richieste(IN piva text, IN codicefiscaleimpresa text, IN datainizioattivita timestamp without time zone, IN stato integer)
  RETURNS TABLE(idoperatore integer, codice_fiscale_impresa text, note text, partita_iva text, ragione_sociale text, enteredby integer, odifiedby integer, entered timestamp without time zone, modified timestamp without time zone, trashed_date timestamp without time zone, domicilio_digitale text, tipo_impresa integer, tipo_societa integer, id_indirizzo integer, id_tipo_richiesta integer, validato boolean) AS
$BODY$
   DECLARE
 
   BEGIN
	
FOR idoperatore ,codice_fiscale_impresa  ,  note , partita_iva, ragione_sociale , enteredby ,odifiedby ,
  entered  , modified ,trashed_date ,domicilio_digitale ,tipo_impresa ,tipo_societa ,
  id_indirizzo ,id_tipo_richiesta,validato

in select ric.id,
ric.codice_fiscale_impresa  ,  ric.note , ric.partita_iva, ric.ragione_sociale , ric.enteredby ,ric.modifiedby ,
  ric.entered  , ric.modified ,ric.trashed_date ,ric.domicilio_digitale ,ric.tipo_impresa ,ric.tipo_societa ,
  ric.id_indirizzo 

 from suap_ric_scia_operatore ric where ric.partita_iva = piva  and ric.trashed_date is null 
	LOOP
	return next ;

     
     END LOOP;

 
     
   END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION suap_get_lista_richieste(text, text, timestamp without time zone, integer)
  OWNER TO postgres;

-- Function: opu_get_lista_imprese(text, text, timestamp without time zone, integer)

-- DROP FUNCTION opu_get_lista_imprese(text, text, timestamp without time zone, integer);

CREATE OR REPLACE FUNCTION opu_get_lista_imprese(IN piva text, IN codicefiscaleimpresa text, IN datainizioattivita timestamp without time zone, IN stato integer)
  RETURNS TABLE(idoperatore integer, flag_clean boolean) AS
$BODY$
   DECLARE
 
   BEGIN
	
FOR idOperatore ,flag_clean in select id,opu_operatore.flag_clean from opu_operatore where partita_iva = piva  and trashed_date is null 
	LOOP
	return next ;

     
     END LOOP;

 
     
   END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION opu_get_lista_imprese(text, text, timestamp without time zone, integer)
  OWNER TO postgres;


-- View: suap_ric_scia_operatori_denormalizzati_view

-- DROP VIEW suap_ric_scia_operatori_denormalizzati_view;

CREATE OR REPLACE VIEW suap_ric_scia_operatori_denormalizzati_view AS 
 SELECT DISTINCT o.id AS id_opu_operatore, 
    o.ragione_sociale, 
    o.partita_iva, 
    o.codice_fiscale_impresa, 
    (((
        CASE
            WHEN topsedeop.description IS NOT NULL THEN topsedeop.description
            ELSE ''::character varying
        END::text || ' '::text) || sedeop.via::text) || ' '::text) || 
        CASE
            WHEN sedeop.civico IS NOT NULL THEN sedeop.civico
            ELSE ''::text
        END AS indirizzo_sede_legale, 
    comunisl.nome AS comune_sede_legale, 
    comunisl.istat AS istat_legale, 
    sedeop.cap AS cap_sede_legale, 
    provsedeop.description AS prov_sede_legale, 
    o.note, 
    o.entered, 
    o.modified, 
    o.enteredby, 
    o.modifiedby, 
    o.domicilio_digitale, 
    i.comune, 
    stab.id_asl, 
    stab.id AS id_stabilimento, 
    comuni1.nome AS comune_stab, 
    comuni1.istat AS istat_operativo, 
    (((
        CASE
            WHEN topsedestab.description IS NOT NULL THEN topsedestab.description
            ELSE ''::character varying
        END::text || ' '::text) || sedestab.via::text) || ' '::text) || 
        CASE
            WHEN sedestab.civico IS NOT NULL THEN sedestab.civico
            ELSE ''::text
        END AS indirizzo_stab, 
    sedestab.cap AS cap_stab, 
    provsedestab.description AS prov_stab, 
    soggsl.codice_fiscale AS cf_rapp_sede_legale, 
    soggsl.nome AS nome_rapp_sede_legale, 
    soggsl.cognome AS cognome_rapp_sede_legale, 
    stab.numero_registrazione AS codice_registrazione, 
    latt.id_norma, 
    latt.codice_attivita AS cf_correntista, 
    latt.codice_attivita, 
    lps.primario, 
    (((latt.macroarea || '->'::text) || latt.aggregazione) || '->'::text) || latt.attivita AS attivita, 
    lps.data_inizio, 
    lps.data_fine, 
    stab.numero_registrazione, 
    (((((((
        CASE
            WHEN topsoggind.description IS NOT NULL THEN topsoggind.description
            ELSE ''::character varying
        END::text || ' '::text) || 
        CASE
            WHEN soggind.via IS NOT NULL THEN soggind.via
            ELSE ''::character varying
        END::text) || ', '::text) || 
        CASE
            WHEN soggind.civico IS NOT NULL THEN soggind.civico
            ELSE ''::text
        END) || ' '::text) || 
        CASE
            WHEN comunisoggind.nome IS NOT NULL THEN comunisoggind.nome
            ELSE ''::character varying
        END::text) || ' '::text) || 
        CASE
            WHEN provsoggind.description IS NOT NULL THEN provsoggind.description
            ELSE ''::text
        END AS indirizzo_rapp_sede_legale, 
    stati.description AS stato, 
    latt.attivita AS solo_attivita, 
    stab.data_inizio_attivita, 
    stab.data_fine_attivita, 
    stab.stato AS id_stato, 
    latt.path_descrizione AS path_attivita_completo, 
    stab.id_indirizzo, 
    latt.flag_nuova_gestione, 
    loti.description AS tipo_impresa, 
    lotis.description AS tipo_societa, 
    stab.id_asl AS id_asl_stab, 
    lps.id AS id_linea_attivita, 
    lps.modified AS data_mod_attivita, 
    stab.entered AS stab_entered, 
    lps.numero_registrazione AS linea_numero_registrazione, 
    lps.stato AS linea_stato, 
    statilinea.description AS linea_stato_text, 
    sedeop.id AS id_indirizzo_operatore, 
    lps.id_linea_produttiva AS id_linea_attivita_stab, 
    o.note AS note_operatore, 
    stab.note AS note_stabilimento, 
    latt.flag_pnaa, 
    (con.namefirst::text || ' '::text) || con.namelast::text AS stab_inserito_da, 
    stab.tipo_attivita AS stab_id_attivita, 
    lsa.description AS stab_descrizione_attivita, 
    stab.tipo_carattere AS stab_id_carattere, 
    lsc.description AS stab_descrizione_carattere, 
    o.tipo_impresa AS impresa_id_tipo_impresa, 
    asl.description AS stab_asl
   FROM suap_ric_scia_operatore o
   JOIN suap_ric_scia_rel_operatore_soggetto_fisico rels ON rels.id_operatore = o.id AND rels.enabled
   JOIN suap_ric_scia_soggetto_fisico soggsl ON soggsl.id = rels.id_soggetto_fisico
   LEFT JOIN opu_indirizzo soggind ON soggind.id = soggsl.indirizzo_id
   LEFT JOIN comuni1 comunisoggind ON comunisoggind.id = soggind.comune
   LEFT JOIN opu_indirizzo sedeop ON sedeop.id = o.id_indirizzo
   LEFT JOIN comuni1 comunisl ON sedeop.comune = comunisl.id
   JOIN suap_ric_scia_stabilimento stab ON stab.id_operatore = o.id
   LEFT JOIN suap_ric_scia_relazione_stabilimento_linee_produttive lps ON lps.id_stabilimento = stab.id AND lps.enabled
   LEFT JOIN lookup_stato_lab stati ON stati.code = stab.stato
   LEFT JOIN opu_linee_attivita_nuove latt ON latt.id_nuova_linea_attivita = lps.id_linea_produttiva
   JOIN opu_indirizzo sedestab ON sedestab.id = stab.id_indirizzo
   LEFT JOIN comuni1 ON sedestab.comune = comuni1.id
   LEFT JOIN opu_indirizzo i ON i.id = stab.id_indirizzo
   LEFT JOIN lookup_province provsedestab ON provsedestab.code::text = sedestab.provincia::text
   LEFT JOIN lookup_province provsedeop ON provsedeop.code::text = sedeop.provincia::text
   LEFT JOIN lookup_province provsoggind ON provsoggind.code::text = soggind.provincia::text
   LEFT JOIN lookup_toponimi topsedeop ON sedeop.toponimo = topsedeop.code
   LEFT JOIN lookup_toponimi topsedestab ON sedestab.toponimo = topsedestab.code
   LEFT JOIN lookup_toponimi topsoggind ON soggind.toponimo = topsoggind.code
   LEFT JOIN lookup_opu_tipo_impresa loti ON loti.code = o.tipo_impresa
   LEFT JOIN lookup_opu_tipo_impresa_societa lotis ON lotis.code = o.tipo_societa
   LEFT JOIN lookup_stato_lab statilinea ON statilinea.code = lps.stato
   LEFT JOIN access acc ON acc.user_id = stab.entered_by
   LEFT JOIN contact con ON con.contact_id = acc.contact_id
   LEFT JOIN opu_lookup_tipologia_attivita lsa ON lsa.code = stab.tipo_attivita
   LEFT JOIN opu_lookup_tipologia_carattere lsc ON lsc.code = stab.tipo_carattere
   LEFT JOIN lookup_site_id asl ON asl.code = stab.id_asl
  WHERE o.trashed_date IS NULL AND stab.trashed_date IS NULL;

ALTER TABLE suap_ric_scia_operatori_denormalizzati_view
  OWNER TO postgres;
GRANT ALL ON TABLE suap_ric_scia_operatori_denormalizzati_view TO postgres;
GRANT SELECT ON TABLE suap_ric_scia_operatori_denormalizzati_view TO usr_ro;
GRANT SELECT ON TABLE suap_ric_scia_operatori_denormalizzati_view TO report;

/*FINE FUNZIONI CON CREAZIONE DELLA VISTA*/

/*SCRIPT PER ACCREDITAMENTO SUAP*/

alter table access_ext_ add istat_comune text ;
alter table access_ext_ add ip_address_suap text ;
alter table whitelist_ip add shared_key_suap text  ;
INSERT INTO ROLE_EXT (role,descriptionenteredby,entered,modifiedby,modified,enabled,role_type,super_ruolo,descrizione_super_ruolo,in_access)
values('SUAP','UTENTI PROVENIENTI DAI SUAP DEI COMUNI',current_timestamp,291,current_timestamp,291,0,2,'GRUPPO_ALTRE_AUTORITA',true);
-- View: access_ext

-- DROP VIEW access_ext;

CREATE OR REPLACE VIEW access_ext AS 
 SELECT DISTINCT ON (a.username) a.username AS username_, 
    a.user_id, 
    a.username, 
    a.password, 
    a.contact_id, 
    a.role_id, 
    a.manager_id, 
    a.startofday, 
    a.endofday, 
    a.locale, 
    a.timezone, 
    a.last_ip, 
    a.last_login, 
    a.enteredby, 
    a.entered, 
    a.modifiedby, 
    a.modified, 
    a.expires, 
    a.alias, 
    a.assistant, 
    a.enabled, 
    a.currency, 
    a.language, 
    a.webdav_password, 
    a.hidden, 
    a.site_id, 
    a.allow_webdav_access, 
    a.allow_httpapi_access, 
    a.last_interaction_time, 
    a.action, 
    a.command, 
    a.object_id, 
    a.table_name, 
    a.access_position_lat, 
    a.access_position_lon, 
    a.access_position_err, 
    a.trashed_date, 
    a.in_dpat, 
    a.in_nucleo_ispettivo, 
    a.in_access, 
    a.codice_suap, 
    a.data_scadenza, 
    a.istat_comune, 
    a.ip_address_suap
   FROM ( SELECT access_ext_.user_id, 
            access_ext_.username, 
            access_ext_.password, 
            access_ext_.contact_id, 
            access_ext_.role_id, 
            access_ext_.manager_id, 
            access_ext_.startofday, 
            access_ext_.endofday, 
            access_ext_.locale, 
            access_ext_.timezone, 
            access_ext_.last_ip, 
            access_ext_.last_login, 
            access_ext_.enteredby, 
            access_ext_.entered, 
            access_ext_.modifiedby, 
            access_ext_.modified, 
            access_ext_.expires, 
            access_ext_.alias, 
            access_ext_.assistant, 
            access_ext_.enabled, 
            access_ext_.currency, 
            access_ext_.language, 
            access_ext_.webdav_password, 
            access_ext_.hidden, 
            access_ext_.site_id, 
            access_ext_.allow_webdav_access, 
            access_ext_.allow_httpapi_access, 
            access_ext_.last_interaction_time, 
            access_ext_.action, 
            access_ext_.command, 
            access_ext_.object_id, 
            access_ext_.table_name, 
            access_ext_.access_position_lat, 
            access_ext_.access_position_lon, 
            access_ext_.access_position_err, 
            access_ext_.trashed_date, 
            access_ext_.in_dpat, 
            access_ext_.in_nucleo_ispettivo, 
            access_ext_.in_access, 
            access_ext_.codice_suap, 
            access_ext_.data_scadenza, 
            access_ext_.ip_address_suap, 
            access_ext_.istat_comune
           FROM access_ext_
          WHERE access_ext_.trashed_date IS NULL AND (access_ext_.data_scadenza > 'now'::text::date OR access_ext_.data_scadenza IS NULL)) a
  ORDER BY a.username, a.data_scadenza;

ALTER TABLE access_ext
  OWNER TO postgres;
GRANT ALL ON TABLE access_ext TO postgres;
GRANT ALL ON TABLE access_ext TO report;

-- Rule: access_ext_insert ON access_ext

-- DROP RULE access_ext_insert ON access_ext;

CREATE OR REPLACE RULE access_ext_insert AS
    ON INSERT TO access_ext DO INSTEAD  INSERT INTO access_ext_ (user_id, username, password, contact_id, site_id, role_id, enteredby, modifiedby, timezone, currency, language, enabled, expires, in_access, in_dpat, in_nucleo_ispettivo) 
  VALUES (new.user_id, new.username, new.password, new.contact_id, new.site_id, new.role_id, new.enteredby, new.modifiedby, new.timezone, new.currency, new.language, new.enabled, new.expires, new.in_access, new.in_dpat, new.in_nucleo_ispettivo);

-- Rule: access_ext_update ON access_ext

-- DROP RULE access_ext_update ON access_ext;

CREATE OR REPLACE RULE access_ext_update AS
    ON UPDATE TO access_ext DO INSTEAD  UPDATE access_ext_ SET access_position_err = new.access_position_err, access_position_lon = new.access_position_lon, access_position_lat = new.access_position_lat, action = new.action, command = new.command, object_id = new.object_id, last_interaction_time = new.last_interaction_time, last_login = new.last_login, last_ip = new.last_ip, modifiedby = new.modifiedby, modified = new.modified
  WHERE access_ext_.user_id = new.user_id;

-- View: contact_ext

-- DROP VIEW contact_ext;

CREATE OR REPLACE VIEW contact_ext AS 
 SELECT contact_ext_.contact_id, 
    contact_ext_.user_id, 
    contact_ext_.org_id, 
    contact_ext_.company, 
    contact_ext_.title, 
    contact_ext_.department, 
    contact_ext_.super, 
    contact_ext_.namesalutation, 
    contact_ext_.namelast, 
    contact_ext_.namefirst, 
    contact_ext_.namemiddle, 
    contact_ext_.namesuffix, 
    contact_ext_.assistant, 
    contact_ext_.birthdate, 
    contact_ext_.notes, 
    contact_ext_.site, 
    contact_ext_.locale, 
    contact_ext_.employee_id, 
    contact_ext_.employmenttype, 
    contact_ext_.startofday, 
    contact_ext_.endofday, 
    contact_ext_.entered, 
    contact_ext_.enteredby, 
    contact_ext_.modified, 
    contact_ext_.modifiedby, 
    contact_ext_.enabled, 
    contact_ext_.owner, 
    contact_ext_.custom1, 
    contact_ext_.url, 
    contact_ext_.primary_contact, 
    contact_ext_.employee, 
    contact_ext_.org_name, 
    contact_ext_.access_type, 
    contact_ext_.status_id, 
    contact_ext_.import_id, 
    contact_ext_.information_update_date, 
    contact_ext_.lead, 
    contact_ext_.lead_status, 
    contact_ext_.source, 
    contact_ext_.rating, 
    contact_ext_.comments, 
    contact_ext_.conversion_date, 
    contact_ext_.additional_names, 
    contact_ext_.nickname, 
    contact_ext_.role, 
    contact_ext_.trashed_date, 
    contact_ext_.secret_word, 
    contact_ext_.account_number, 
    contact_ext_.revenue, 
    contact_ext_.industry_temp_code, 
    contact_ext_.potential, 
    contact_ext_.no_email, 
    contact_ext_.no_mail, 
    contact_ext_.no_phone, 
    contact_ext_.no_textmessage, 
    contact_ext_.no_im, 
    contact_ext_.no_fax, 
    contact_ext_.site_id, 
    contact_ext_.assigned_date, 
    contact_ext_.lead_trashed_date, 
    contact_ext_.employees, 
    contact_ext_.duns_type, 
    contact_ext_.duns_number, 
    contact_ext_.business_name_two, 
    contact_ext_.sic_code, 
    contact_ext_.year_started, 
    contact_ext_.sic_description, 
    contact_ext_.site_id_old, 
    contact_ext_.codice_fiscale, 
    contact_ext_.luogo, 
    contact_ext_.visibilita_delega
   FROM contact_ext_
  WHERE (contact_ext_.user_id IN ( SELECT aa.user_id
           FROM ( SELECT DISTINCT ON (a.username) a.username AS username_, 
                    a.user_id, 
                    a.username, 
                    a.password, 
                    a.contact_id, 
                    a.role_id, 
                    a.manager_id, 
                    a.startofday, 
                    a.endofday, 
                    a.locale, 
                    a.timezone, 
                    a.last_ip, 
                    a.last_login, 
                    a.enteredby, 
                    a.entered, 
                    a.modifiedby, 
                    a.modified, 
                    a.expires, 
                    a.alias, 
                    a.assistant, 
                    a.enabled, 
                    a.currency, 
                    a.language, 
                    a.webdav_password, 
                    a.hidden, 
                    a.site_id, 
                    a.allow_webdav_access, 
                    a.allow_httpapi_access, 
                    a.last_interaction_time, 
                    a.action, 
                    a.command, 
                    a.object_id, 
                    a.table_name, 
                    a.access_position_lat, 
                    a.access_position_lon, 
                    a.access_position_err, 
                    a.trashed_date, 
                    a.data_scadenza
                   FROM ( SELECT access_ext_.user_id, 
                            access_ext_.username, 
                            access_ext_.password, 
                            access_ext_.contact_id, 
                            access_ext_.role_id, 
                            access_ext_.manager_id, 
                            access_ext_.startofday, 
                            access_ext_.endofday, 
                            access_ext_.locale, 
                            access_ext_.timezone, 
                            access_ext_.last_ip, 
                            access_ext_.last_login, 
                            access_ext_.enteredby, 
                            access_ext_.entered, 
                            access_ext_.modifiedby, 
                            access_ext_.modified, 
                            access_ext_.expires, 
                            access_ext_.alias, 
                            access_ext_.assistant, 
                            access_ext_.enabled, 
                            access_ext_.currency, 
                            access_ext_.language, 
                            access_ext_.webdav_password, 
                            access_ext_.hidden, 
                            access_ext_.site_id, 
                            access_ext_.allow_webdav_access, 
                            access_ext_.allow_httpapi_access, 
                            access_ext_.last_interaction_time, 
                            access_ext_.action, 
                            access_ext_.command, 
                            access_ext_.object_id, 
                            access_ext_.table_name, 
                            access_ext_.access_position_lat, 
                            access_ext_.access_position_lon, 
                            access_ext_.access_position_err, 
                            access_ext_.trashed_date, 
                            access_ext_.data_scadenza, 
                            contact_ext_.visibilita_delega
                           FROM access_ext_
                          WHERE access_ext_.data_scadenza > 'now'::text::date OR access_ext_.data_scadenza IS NULL) a
                  ORDER BY a.username, a.data_scadenza) aa));

ALTER TABLE contact_ext
  OWNER TO postgres;
GRANT ALL ON TABLE contact_ext TO postgres;
GRANT SELECT ON TABLE contact_ext TO report;
ALTER TABLE contact_ext ALTER COLUMN contact_id SET DEFAULT nextval('contact_ext_contact_id_seq'::regclass);


-- Rule: contact_ext_insert ON contact_ext

-- DROP RULE contact_ext_insert ON contact_ext;

CREATE OR REPLACE RULE contact_ext_insert AS
    ON INSERT TO contact_ext DO INSTEAD  INSERT INTO contact_ext_ (contact_id, user_id, namefirst, namelast, enteredby, modifiedby, site_id, codice_fiscale, notes, enabled, luogo, nickname, visibilita_delega) 
  VALUES (new.contact_id, new.user_id, new.namefirst, new.namelast, new.enteredby, new.modifiedby, new.site_id, new.codice_fiscale, new.notes, new.enabled, new.luogo, new.nickname, new.visibilita_delega);

/*FINE CAMPI DI ACCREDITAMENTO*/



SELECT * from role_Ext
