-- Chi: Bartolo Sansone
-- Cosa: Flusso 242 Nuova Gestione OSM
-- Quando: 13/05/21

-- Vista stabilimenti


CREATE OR REPLACE VIEW public.sinvsa_osm_stabilimenti_view AS 
 SELECT distinct  r.riferimento_id,
    r.riferimento_id_nome_tab,
    r.ragione_sociale AS impresa_ragione_sociale,
    r.codice_fiscale AS impresa_cf,
    r.partita_iva AS impresa_partita_iva,
        CASE
            WHEN r.riferimento_id_nome_tab = 'opu_stabilimento'::text THEN opu_impresa_prov.cod_provincia::character varying
            ELSE org_impresa_ind.state
        END AS impresa_prov_sigla,
        CASE
            WHEN r.riferimento_id_nome_tab = 'opu_stabilimento'::text THEN opu_impresa_com.cod_comune
            ELSE ''::character varying
        END AS impresa_com_istat,
    r.indirizzo_leg AS impresa_indirizzo,
    r.cap_leg AS impresa_cap,
    'M'::text AS impresa_tipo,
    '0123456789'::text AS impresa_telefono,
    r.codice_fiscale_rappresentante AS soggetto_cf,
    r.nominativo_rappresentante AS soggetto_nome,
    r.nominativo_rappresentante AS soggetto_cognome,
    concat('R', r.asl_rif) AS stabilimento_asl,
        CASE
            WHEN r.riferimento_id_nome_tab = 'opu_stabilimento'::text THEN opu_stabilimento_prov.cod_provincia::character varying
            ELSE org_stabilimento_ind.state
        END AS stabilimento_prov_sigla,
        CASE
            WHEN r.riferimento_id_nome_tab = 'opu_stabilimento'::text THEN opu_stabilimento_com.cod_comune
            ELSE ''::character varying
        END AS stabilimento_com_istat,
    r.indirizzo AS stabilimento_indirizzo,
    r.cap_stab AS stabilimento_cap,
    r.n_reg AS stabilimento_numreg,
    to_char(r.data_inizio_attivita, 'yyyy-mm-dd'::text) AS stabilimento_data_inizio,
    '0123456789'::text AS stabilimento_telefono,
        CASE
            WHEN r.riferimento_id_nome_tab = 'opu_stabilimento'::text THEN opu_stabilimento.entered
            ELSE organization.entered
        END AS data_inserimento,
        CASE
            WHEN r.riferimento_id_nome_tab = 'opu_stabilimento'::text THEN opu_stabilimento.modified
            ELSE organization.modified
        END AS data_ultima_modifica
   FROM ricerche_anagrafiche_old_materializzata r
     LEFT JOIN opu_stabilimento opu_stabilimento ON opu_stabilimento.id = r.riferimento_id AND r.riferimento_id_nome_tab = 'opu_stabilimento'::text
     LEFT JOIN organization organization ON organization.org_id = r.riferimento_id AND r.riferimento_id_nome_tab = 'organization'::text
     LEFT JOIN opu_indirizzo opu_impresa_ind ON opu_impresa_ind.id = r.id_indirizzo_impresa AND r.riferimento_id_nome_tab = 'opu_stabilimento'::text
     LEFT JOIN comuni1 opu_impresa_com ON opu_impresa_com.id = opu_impresa_ind.comune
     LEFT JOIN lookup_province opu_impresa_prov ON opu_impresa_prov.code = opu_impresa_com.cod_provincia::integer
     LEFT JOIN organization_address org_impresa_ind ON org_impresa_ind.address_id = r.id_indirizzo_impresa AND r.riferimento_id_nome_tab = 'organization'::text
     LEFT JOIN opu_indirizzo opu_stabilimento_ind ON opu_stabilimento_ind.id = r.id_sede_operativa AND r.riferimento_id_nome_tab = 'opu_stabilimento'::text
     LEFT JOIN comuni1 opu_stabilimento_com ON opu_stabilimento_com.id = opu_stabilimento_ind.comune
     LEFT JOIN lookup_province opu_stabilimento_prov ON opu_stabilimento_prov.code = opu_stabilimento_com.cod_provincia::integer
     LEFT JOIN organization_address org_stabilimento_ind ON org_stabilimento_ind.address_id = r.id_sede_operativa AND r.riferimento_id_nome_tab = 'organization'::text
  WHERE 1 = 1 AND ((r.tipologia_operatore = ANY (ARRAY[800, 801])) OR r.tipologia_operatore = 999 AND (r.codice_macroarea = ANY (ARRAY['MG'::text, 'MR'::text])));

ALTER TABLE public.sinvsa_osm_stabilimenti_view
  OWNER TO postgres;

    -- Vista linee
    

CREATE OR REPLACE VIEW public.sinvsa_osm_attivita_view AS 
 SELECT r.id_linea AS id_istanza,
    r.riferimento_id,
    r.riferimento_id_nome_tab,
    r.path_attivita_completo as linea_descrizione,
  '183/2005'::text AS linea_norma,
    r.codice_macroarea AS linea_macroarea,
    r.codice_aggregazione AS linea_aggregazione,
    r.codice_attivita AS linea_linea,
        CASE
            WHEN r.codice_macroarea = 'MG'::text OR r.tipologia_operatore = 801 THEN 'N'::text
            WHEN r.codice_macroarea = 'MR'::text OR r.tipologia_operatore = 800 THEN 'S'::text
            ELSE NULL::text
        END AS linea_ric,
    'S'::text AS flag_attr_pr,
 CASE
            WHEN r.riferimento_id_nome_tab = 'opu_stabilimento'::text THEN to_char(opu_stabilimento_rel.data_inizio, 'yyyy-mm-dd'::text)
            ELSE to_char(organization.date2, 'yyyy-mm-dd'::text)
        END AS linea_data_inizio,
  CASE
            WHEN r.riferimento_id_nome_tab = 'opu_stabilimento'::text THEN to_char(opu_stabilimento_rel.data_fine, 'yyyy-mm-dd'::text)
            ELSE to_char(organization.date1, 'yyyy-mm-dd'::text)
        END AS linea_data_fine,   
             CASE
            WHEN r.riferimento_id_nome_tab = 'opu_stabilimento'::text THEN opu_stabilimento_rel.modified
            ELSE organization.modified
        END AS data_ultima_modifica
   FROM ricerche_anagrafiche_old_materializzata r
     LEFT JOIN opu_stabilimento opu_stabilimento ON opu_stabilimento.id = r.riferimento_id AND r.riferimento_id_nome_tab = 'opu_stabilimento'::text
     LEFT JOIN opu_relazione_stabilimento_linee_produttive opu_stabilimento_rel ON opu_stabilimento_rel.id = r.id_linea
     LEFT JOIN organization organization ON organization.org_id = r.riferimento_id AND r.riferimento_id_nome_tab = 'organization'::text
  WHERE 1 = 1 AND ((r.tipologia_operatore = ANY (ARRAY[800, 801])) OR r.tipologia_operatore = 999 AND (r.codice_macroarea = ANY (ARRAY['MG'::text, 'MR'::text])));

ALTER TABLE public.sinvsa_osm_attivita_view
  OWNER TO postgres;


  -- Esiti stabilimenti
  
  
CREATE TABLE public.sinvsa_osm_stabilimenti_esiti
(
  id SERIAL PRIMARY KEY,
  data timestamp without time zone DEFAULT now(),
  riferimento_id integer,
  riferimento_id_nome_tab text,
  cun_sinvsa text,
  id_persona integer,
  id_sede_legale integer,
  id_sede_operativa integer,
  id_impresa integer,
  esito_persona text,
  esito_sede_operativa text,
  esito_sede_legale text,
  esito_impresa text,
  id_utente integer,
  trashed_date timestamp without time zone,
  id_import_massivo integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.sinvsa_osm_stabilimenti_esiti
  OWNER TO postgres;

  
  -- Esiti linee
  
  
CREATE TABLE public.sinvsa_osm_attivita_esiti
(
  id SERIAL PRIMARY KEY,
  data timestamp without time zone DEFAULT now(),
  riferimento_id integer,
  riferimento_id_nome_tab text,
  id_istanza integer,
  id_attivita integer,
  esito_attivita text,
  id_utente integer,
  trashed_date timestamp without time zone,
  id_import_massivo integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.sinvsa_osm_attivita_esiti
  OWNER TO postgres;
  
  
  -- gestione persona
  
  -- Function: public.get_chiamata_ws_search_osm_persona(text, text, integer, text)

-- DROP FUNCTION public.get_chiamata_ws_search_osm_persona(text, text, integer, text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_search_osm_persona(
    _username text,
    _password text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:anag="http://sinsa.izs.it/services/anagrafica"> <soapenv:Header><anag:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></anag:SOAPAutorizzazione><anag:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></anag:SOAPAutenticazione>   </soapenv:Header>
   <soapenv:Body>
      <anag:searchPersone>
      <searchparams>
      <osaCodFiscale>', soggetto_cf, '</osaCodFiscale>
      </searchparams>
      </anag:searchPersone>
   </soapenv:Body>
</soapenv:Envelope>') into ret
from sinvsa_osm_stabilimenti_view
where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab limit 1;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_search_osm_persona(text, text, integer, text)
  OWNER TO postgres;

  -- Function: public.get_chiamata_ws_insert_osm_persona(text, text, integer, text)

-- DROP FUNCTION public.get_chiamata_ws_insert_osm_persona(text, text, integer, text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_osm_persona(
    _username text,
    _password text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:anag="http://sinsa.izs.it/services/anagrafica"> <soapenv:Header><anag:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></anag:SOAPAutorizzazione><anag:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></anag:SOAPAutenticazione></soapenv:Header>
   <soapenv:Body>
      <anag:insertPersona>
         <persona>
            <osaCodFiscale>', soggetto_cf, '</osaCodFiscale>
            <osaNome>', soggetto_nome, '</osaNome>
            <osaCognome>', soggetto_cognome, '</osaCognome>
         </persona>
      </anag:insertPersona>
   </soapenv:Body>
</soapenv:Envelope>') into ret
from sinvsa_osm_stabilimenti_view
where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab limit 1;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_osm_persona(text, text, integer, text)
  OWNER TO postgres;

-- Function: public.get_chiamata_ws_update_osm_persona(text, text, integer, text, integer)

-- DROP FUNCTION public.get_chiamata_ws_update_osm_persona(text, text, integer, text, integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_update_osm_persona(
    _username text,
    _password text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text,
    _id integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:anag="http://sinsa.izs.it/services/anagrafica"> <soapenv:Header><anag:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></anag:SOAPAutorizzazione><anag:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></anag:SOAPAutenticazione></soapenv:Header>
   <soapenv:Body>
      <anag:updatePersona>
         <persona>
         <osaId>', _id, '</osaId>
    <osaCodFiscale>', soggetto_cf, '</osaCodFiscale>
            <osaNome>', soggetto_nome, '</osaNome>
            <osaCognome>', soggetto_cognome, '</osaCognome>
         </persona>
      </anag:updatePersona>
   </soapenv:Body>
</soapenv:Envelope>') into ret
from sinvsa_osm_stabilimenti_view
where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab limit 1;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_update_osm_persona(text, text, integer, text, integer)
  OWNER TO postgres;

--gestione sede legale

  -- Function: public.get_chiamata_ws_search_osm_sede_legale(text, text, integer, text)

-- DROP FUNCTION public.get_chiamata_ws_search_osm_sede_legale(text, text, integer, text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_search_osm_sede_legale(
    _username text,
    _password text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:anag="http://sinsa.izs.it/services/anagrafica"> <soapenv:Header><anag:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></anag:SOAPAutorizzazione><anag:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></anag:SOAPAutenticazione></soapenv:Header>
   <soapenv:Body>
            <anag:searchSediLegali>
         <searchparams>
            <osaCodFiscale>', soggetto_cf, '</osaCodFiscale>
            <proSigla>', impresa_prov_sigla, '</proSigla>
            <sliCap>', impresa_cap, '</sliCap>
            <sliCodFiscale>', impresa_cf, '</sliCodFiscale>
            <sliComCodice>', impresa_com_istat, '</sliComCodice>
            <sliIndirizzo>', impresa_indirizzo, '</sliIndirizzo>
            <sliTelefono>', impresa_telefono, '</sliTelefono>
         </searchparams>
          </anag:searchSediLegali>
         </soapenv:Body>
</soapenv:Envelope>') into ret
from sinvsa_osm_stabilimenti_view
where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab limit 1;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_search_osm_sede_legale(text, text, integer, text)
  OWNER TO postgres;


-- Function: public.get_chiamata_ws_insert_osm_sede_legale(text, text, integer, text)

-- DROP FUNCTION public.get_chiamata_ws_insert_osm_sede_legale(text, text, integer, text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_osm_sede_legale(
    _username text,
    _password text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:anag="http://sinsa.izs.it/services/anagrafica"> <soapenv:Header><anag:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></anag:SOAPAutorizzazione><anag:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></anag:SOAPAutenticazione></soapenv:Header>
   <soapenv:Body>
      <anag:insertSedeLegale>
         <sedelegale>
            <osaCodFiscale>', soggetto_cf, '</osaCodFiscale>
            <proSigla>', impresa_prov_sigla, '</proSigla>
            <sliCap>', impresa_cap, '</sliCap>
            <sliCodFiscale>', impresa_cf, '</sliCodFiscale>
            <sliComCodice>', impresa_com_istat, '</sliComCodice>
            <sliId>0</sliId>
            <sliIndirizzo>', impresa_indirizzo, '</sliIndirizzo>
            <sliRagioneSociale>', replace(impresa_ragione_sociale, '&', '&amp;'), '</sliRagioneSociale>
            <sliTelefono>', impresa_telefono, '</sliTelefono>
           </sedelegale>
      </anag:insertSedeLegale>
   </soapenv:Body>
</soapenv:Envelope>') into ret
from sinvsa_osm_stabilimenti_view
where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab limit 1;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_osm_sede_legale(text, text, integer, text)
  OWNER TO postgres;

-- Function: public.get_chiamata_ws_update_osm_sede_legale(text, text, integer, text, integer)

-- DROP FUNCTION public.get_chiamata_ws_update_osm_sede_legale(text, text, integer, text, integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_update_osm_sede_legale(
    _username text,
    _password text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text,
    _id integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:anag="http://sinsa.izs.it/services/anagrafica"> <soapenv:Header><anag:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></anag:SOAPAutorizzazione><anag:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></anag:SOAPAutenticazione></soapenv:Header>
   <soapenv:Body>
      <anag:updateSedeLegale>
         <sedelegale>
            <osaCodFiscale>', soggetto_cf, '</osaCodFiscale>
            <proSigla>', impresa_prov_sigla, '</proSigla>
            <sliCap>', impresa_cap, '</sliCap>
            <sliCodFiscale>', impresa_cf, '</sliCodFiscale>
            <sliComCodice>', impresa_com_istat, '</sliComCodice>
            <sliId>', _id, '</sliId>
            <sliIndirizzo>', impresa_indirizzo, '</sliIndirizzo>
            <sliRagioneSociale>', replace(impresa_ragione_sociale, '&', '&amp;'), '</sliRagioneSociale>
            <sliTelefono>', impresa_telefono, '</sliTelefono>
           </sedelegale>
      </anag:updateSedeLegale>
   </soapenv:Body>
</soapenv:Envelope>') into ret
from sinvsa_osm_stabilimenti_view
where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab limit 1;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_update_osm_sede_legale(text, text, integer, text, integer)
  OWNER TO postgres;

  
  -- gestione sede operativa
  
  -- Function: public.get_chiamata_ws_search_osm_sede_operativa(text, text, integer, text)

-- DROP FUNCTION public.get_chiamata_ws_search_osm_sede_operativa(text, text, integer, text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_search_osm_sede_operativa(
    _username text,
    _password text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:anag="http://sinsa.izs.it/services/anagrafica"> <soapenv:Header><anag:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></anag:SOAPAutorizzazione><anag:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></anag:SOAPAutenticazione></soapenv:Header>
  <soapenv:Body>
      <anag:searchSedioperative>
         <searchparams>
            <sliCodFiscale>', impresa_cf, '</sliCodFiscale>
            <tipoImpresa>', impresa_tipo, '</tipoImpresa>
         </searchparams>
      </anag:searchSedioperative>
   </soapenv:Body>
</soapenv:Envelope>') into ret
from sinvsa_osm_stabilimenti_view
where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab limit 1;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_search_osm_sede_operativa(text, text, integer, text)
  OWNER TO postgres;

-- Function: public.get_chiamata_ws_insert_osm_sede_operativa(text, text, integer, text)

-- DROP FUNCTION public.get_chiamata_ws_insert_osm_sede_operativa(text, text, integer, text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_osm_sede_operativa(
    _username text,
    _password text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:anag="http://sinsa.izs.it/services/anagrafica"> <soapenv:Header><anag:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></anag:SOAPAutorizzazione><anag:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></anag:SOAPAutenticazione></soapenv:Header>
  <soapenv:Body>
      <anag:insertSedeOperativa>
         <sedeoperativa>
            <sliCodFiscale>', impresa_cf, '</sliCodFiscale>
            <spiAslCodice>', stabilimento_asl, '</spiAslCodice>
            <spiCap>', stabilimento_cap, '</spiCap>
            <spiComCodice>', stabilimento_com_istat, '</spiComCodice>
            <spiIndirizzo>', stabilimento_indirizzo, '</spiIndirizzo>
            <spiProSigla>', stabilimento_prov_sigla, '</spiProSigla>
	    <spiTelefono>', stabilimento_telefono, '</spiTelefono>
            <tipoImpresa>', impresa_tipo, '</tipoImpresa>
		</sedeoperativa>
      </anag:insertSedeOperativa>
   </soapenv:Body>
</soapenv:Envelope>') into ret
from sinvsa_osm_stabilimenti_view
where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab limit 1;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_osm_sede_operativa(text, text, integer, text)
  OWNER TO postgres;


-- Function: public.get_chiamata_ws_update_osm_sede_operativa(text, text, integer, text, integer)

-- DROP FUNCTION public.get_chiamata_ws_update_osm_sede_operativa(text, text, integer, text, integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_update_osm_sede_operativa(
    _username text,
    _password text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text,
    _id integer,
    _cun_sinvsa text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:anag="http://sinsa.izs.it/services/anagrafica"> <soapenv:Header><anag:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></anag:SOAPAutorizzazione><anag:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></anag:SOAPAutenticazione></soapenv:Header>
  <soapenv:Body>
      <anag:updateSedeOperativa>
         <sedeoperativa>
         <spiId>', _id, '</spiId>
            <sliCodFiscale>', impresa_cf, '</sliCodFiscale>
            <spiAslCodice>', stabilimento_asl, '</spiAslCodice>
            <spiCap>', stabilimento_cap, '</spiCap>
            <spiComCodice>', stabilimento_com_istat, '</spiComCodice>
            <spiIndirizzo>', stabilimento_indirizzo, '</spiIndirizzo>
            <spiProSigla>', stabilimento_prov_sigla, '</spiProSigla>
	    <spiTelefono>', stabilimento_telefono, '</spiTelefono>
            <tipoImpresa>', impresa_tipo, '</tipoImpresa>
            <spiCun>', _cun_sinvsa, '</spiCun>
		</sedeoperativa>
      </anag:updateSedeOperativa>
   </soapenv:Body>
</soapenv:Envelope>') into ret
from sinvsa_osm_stabilimenti_view
where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab limit 1;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_update_osm_sede_operativa(text, text, integer, text, integer, text)
  OWNER TO postgres;

  
  -- Function: public.get_chiamata_ws_search_osm_sede_operativa_by_pk(text, text, integer)

-- DROP FUNCTION public.get_chiamata_ws_search_osm_sede_operativa_by_pk(text, text, integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_search_osm_sede_operativa_by_pk(
    _username text,
    _password text,
    _id_sede_operativa integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:anag="http://sinsa.izs.it/services/anagrafica"> <soapenv:Header><anag:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></anag:SOAPAutorizzazione><anag:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></anag:SOAPAutenticazione></soapenv:Header>
  <soapenv:Body>
      <anag:searchSedeoperativaByPk>
            <id>', _id_sede_operativa, '</id>
      </anag:searchSedeoperativaByPk>
   </soapenv:Body>
</soapenv:Envelope>') into ret;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_search_osm_sede_operativa_by_pk(text, text, integer)
  OWNER TO postgres;

  

-- gestione impresa

  -- Function: public.get_chiamata_ws_search_osm_impresa(text, text, integer, text)

-- DROP FUNCTION public.get_chiamata_ws_search_osm_impresa(text, text, integer, text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_search_osm_impresa(
    _username text,
    _password text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text,
    _cun_sinvsa text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:anag="http://sinsa.izs.it/services/anagrafica"> <soapenv:Header><anag:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></anag:SOAPAutorizzazione><anag:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></anag:SOAPAutenticazione></soapenv:Header>
 <soapenv:Body>
  <anag:searchRegistrazioniimprese>
	<searchparams>
		<sliCodFiscale>', s.impresa_cf, '</sliCodFiscale>
		<registrNumero>', s.stabilimento_numreg, '</registrNumero>
		<normaCodice>', a.linea_norma, '</normaCodice>
		<cun>', _cun_sinvsa, '</cun>
    </searchparams>
  </anag:searchRegistrazioniimprese>
 </soapenv:Body>
</soapenv:Envelope>') into ret
from sinvsa_osm_stabilimenti_view s
join sinvsa_osm_attivita_view a on s.riferimento_id = a.riferimento_id and a.riferimento_id_nome_tab = s.riferimento_id_nome_tab
where s.riferimento_id = _riferimento_id and s.riferimento_id_nome_tab = _riferimento_id_nome_tab limit 1;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_search_osm_impresa(text, text, integer, text, text)
  OWNER TO postgres;

-- Function: public.get_chiamata_ws_insert_osm_impresa(text, text, integer, text, text)

-- DROP FUNCTION public.get_chiamata_ws_insert_osm_impresa(text, text, integer, text, text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_osm_impresa(
    _username text,
    _password text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text,
    _cun_sinvsa text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:anag="http://sinsa.izs.it/services/anagrafica"> <soapenv:Header><anag:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></anag:SOAPAutorizzazione><anag:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></anag:SOAPAutenticazione></soapenv:Header>
 <soapenv:Body>
  <anag:insertRegistrazioniimprese>
	<registrazioniimprese>
		<spiNumRegistrazione>', _cun_sinvsa, '</spiNumRegistrazione>
		<registrNumero>', s.stabilimento_numreg, '</registrNumero>
		<normaCodice>', a.linea_norma, '</normaCodice>
		<flagRiconoscimento>', a.linea_ric, '</flagRiconoscimento>
    </registrazioniimprese>
  </anag:insertRegistrazioniimprese>
 </soapenv:Body>
</soapenv:Envelope>') into ret
from sinvsa_osm_stabilimenti_view s
join sinvsa_osm_attivita_view a on s.riferimento_id = a.riferimento_id and a.riferimento_id_nome_tab = s.riferimento_id_nome_tab
where s.riferimento_id = _riferimento_id and s.riferimento_id_nome_tab = _riferimento_id_nome_tab limit 1;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_osm_impresa(text, text, integer, text, text)
  OWNER TO postgres;


-- gestione attivita

  -- Function: public.get_chiamata_ws_search_osm_attivita(text, text, integer, text, text)

-- DROP FUNCTION public.get_chiamata_ws_search_osm_attivita(text, text, integer, text, text);


CREATE OR REPLACE FUNCTION public.get_chiamata_ws_search_osm_attivita(
    _username text,
    _password text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text,
    _id_istanza integer,
    _cun_sinvsa text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:anag="http://sinsa.izs.it/services/anagrafica"> <soapenv:Header><anag:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></anag:SOAPAutorizzazione><anag:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></anag:SOAPAutenticazione></soapenv:Header>
   <soapenv:Body>
      <anag:searchSezioniattivitaimpresa>
         <searchparams>
            <spiNumRegistrazione>', _cun_sinvsa, '</spiNumRegistrazione>
            <normaCodice>', a.linea_norma, '</normaCodice>
            <registrNumero>', s.stabilimento_numreg, '</registrNumero>
            </searchparams>
      </anag:searchSezioniattivitaimpresa>
   </soapenv:Body>
</soapenv:Envelope>	') into ret
from sinvsa_osm_attivita_view a
join sinvsa_osm_stabilimenti_view s on s.riferimento_id = a.riferimento_id and a.riferimento_id_nome_tab = s.riferimento_id_nome_tab
where a.riferimento_id = _riferimento_id and a.riferimento_id_nome_tab = _riferimento_id_nome_tab and a.id_istanza = _id_istanza;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_search_osm_attivita(text, text, integer, text, integer, text)
  OWNER TO postgres;


-- Function: public.get_chiamata_ws_insert_osm_attivita(text, text, integer, text, text)

-- DROP FUNCTION public.get_chiamata_ws_insert_osm_attivita(text, text, integer, text, text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_osm_attivita(
    _username text,
    _password text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text,
    _id_istanza integer,
    _cun_sinvsa text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:anag="http://sinsa.izs.it/services/anagrafica"> <soapenv:Header><anag:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></anag:SOAPAutorizzazione><anag:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></anag:SOAPAutenticazione></soapenv:Header>
   <soapenv:Body>
      <anag:insertSezioniattivitaimpresa>
         <sezioniattivitaimpresa>
            <spiNumRegistrazione>', _cun_sinvsa, '</spiNumRegistrazione>
            <normaCodice>', a.linea_norma, '</normaCodice>
            <registrNumero>', s.stabilimento_numreg, '</registrNumero>
            <saiDtRiferimento>', a.linea_data_inizio, '</saiDtRiferimento>
             <saiDtFineAttivita>', a.linea_data_fine, '</saiDtFineAttivita>
            <saiTipoOperazione>A</saiTipoOperazione>
            <saiFlAttivitaPrinc>', a.flag_attr_pr, '</saiFlAttivitaPrinc>
	    <sezCodice>', a.linea_macroarea, '</sezCodice>
	    <attCodice>', a.linea_aggregazione, '</attCodice>
         </sezioniattivitaimpresa>
      </anag:insertSezioniattivitaimpresa>
   </soapenv:Body>
</soapenv:Envelope>	') into ret
from sinvsa_osm_attivita_view a
join sinvsa_osm_stabilimenti_view s on s.riferimento_id = a.riferimento_id and a.riferimento_id_nome_tab = s.riferimento_id_nome_tab
where a.riferimento_id = _riferimento_id and a.riferimento_id_nome_tab = _riferimento_id_nome_tab and a.id_istanza = _id_istanza;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_osm_attivita(text, text, integer, text, integer, text)
  OWNER TO postgres;

  -- Function: public.get_chiamata_ws_update_osm_attivita(text, text, integer, text, integer, text)

-- DROP FUNCTION public.get_chiamata_ws_update_osm_attivita(text, text, integer, text, integer, text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_update_osm_attivita(
    _username text,
    _password text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text,
_id_istanza integer,
    _id integer,
    _cun_sinvsa text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:anag="http://sinsa.izs.it/services/anagrafica"> <soapenv:Header><anag:SOAPAutorizzazione><ruoloCodice>WSREG</ruoloCodice><ruoloValoreCodice>150</ruoloValoreCodice></anag:SOAPAutorizzazione><anag:SOAPAutenticazione><password>', _password, '</password><username>', _username, '</username></anag:SOAPAutenticazione></soapenv:Header>
   <soapenv:Body>
      <anag:updateSezioniattivitaimpresa>
         <sezioniattivitaimpresa>
         <saiId>', _id, '</saiId>
            <spiNumRegistrazione>', _cun_sinvsa, '</spiNumRegistrazione>
            <normaCodice>', a.linea_norma, '</normaCodice>
            <registrNumero>', s.stabilimento_numreg, '</registrNumero>
            <saiDtRiferimento>', a.linea_data_inizio, '</saiDtRiferimento>
            <saiDtFineAttivita>', a.linea_data_fine, '</saiDtFineAttivita>
            <saiTipoOperazione>A</saiTipoOperazione>
            <saiFlAttivitaPrinc>', a.flag_attr_pr, '</saiFlAttivitaPrinc>
	    <sezCodice>', a.linea_macroarea, '</sezCodice>
	    <attCodice>', a.linea_aggregazione, '</attCodice>
         </sezioniattivitaimpresa>
      </anag:updateSezioniattivitaimpresa>
   </soapenv:Body>
</soapenv:Envelope>	') into ret
from sinvsa_osm_attivita_view a
join sinvsa_osm_stabilimenti_view s on s.riferimento_id = a.riferimento_id and a.riferimento_id_nome_tab = s.riferimento_id_nome_tab
where a.riferimento_id = _riferimento_id and a.riferimento_id_nome_tab = _riferimento_id_nome_tab and a.id_istanza = _id_istanza;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_update_osm_attivita(text, text, integer, text, integer, integer, text)
  OWNER TO postgres;


  
  

  
  