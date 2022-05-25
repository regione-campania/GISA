--
-- PostgreSQL database dump
--

-- Dumped from database version 12.10
-- Dumped by pg_dump version 12.10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: dblink; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS dblink WITH SCHEMA public;


--
-- Name: EXTENSION dblink; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION dblink IS 'connect to other PostgreSQL databases from within a database';


--
-- Name: unaccent; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS unaccent WITH SCHEMA public;


--
-- Name: EXTENSION unaccent; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION unaccent IS 'text search dictionary that removes accents';


--
-- Name: aggregazione(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.aggregazione(_riferimento_id text DEFAULT NULL::text, _riferimento_id_nome_tab text DEFAULT NULL::text) RETURNS TABLE(aggregazione text, id_aggregazione text, id_macroarea text)
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN	
	FOR 
	    aggregazione,
	    id_aggregazione,
	    id_macroarea
    in
		select DISTINCT m.aggregazione,
    m.id_aggregazione,
    m.id_macroarea
   FROM ml10(_riferimento_id, _riferimento_id_nome_tab) m
  ORDER BY m.aggregazione
		LOOP
			RETURN NEXT;
		END LOOP;
	RETURN;
end;
$$;


ALTER FUNCTION public.aggregazione(_riferimento_id text, _riferimento_id_nome_tab text) OWNER TO postgres;

--
-- Name: get_checklist_by_idlinea(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.get_checklist_by_idlinea(_id_linea integer) RETURNS TABLE(code integer, description character varying, short_description character varying, enabled boolean, versione double precision)
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN	
	FOR 
		code, description, short_description, enabled, versione
    in
		SELECT t1.code, t1.description, t1.short_description, t1.enabled, t1.versione

   FROM dblink(public.getConfig(), 'SELECT code, description, short_description, enabled, versione  
		FROM giava.get_checklist_by_idlinea('||_id_linea||'::int) '::text) 
		t1(code integer, description character varying, short_description character varying, enabled boolean, versione double precision)
		LOOP
			RETURN NEXT;
		END LOOP;
	RETURN;
end;
$$;


ALTER FUNCTION public.get_checklist_by_idlinea(_id_linea integer) OWNER TO postgres;

--
-- Name: getconfig(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.getconfig() RETURNS text
    LANGUAGE plpgsql
    AS $$
declare
	connString text;
BEGIN	
	select ' host='||"host"||' dbname='||"dbname"||' user='||"user"||' password='||"password"||' port='||"port" into connString from public.config limit 1;
	return connString;
end;
$$;


ALTER FUNCTION public.getconfig() OWNER TO postgres;

--
-- Name: lda(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.lda(_riferimento_id text DEFAULT NULL::text, _riferimento_id_nome_tab text DEFAULT NULL::text) RETURNS TABLE(attivita text, id_linea text, id_aggregazione text)
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN	
	FOR 
	    attivita,
	    id_linea,
	    id_aggregazione
    in
		 SELECT DISTINCT m.attivita,
    m.id_linea,
    m.id_aggregazione
   FROM ml10(_riferimento_id, _riferimento_id_nome_tab) m
  ORDER BY m.attivita
		LOOP
			RETURN NEXT;
		END LOOP;
	RETURN;
end;
$$;


ALTER FUNCTION public.lda(_riferimento_id text, _riferimento_id_nome_tab text) OWNER TO postgres;

--
-- Name: ml10(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.ml10(_riferimento_id text DEFAULT NULL::text, _riferimento_id_nome_tab text DEFAULT NULL::text) RETURNS TABLE(codice_univoco text, norma text, id_norma text, macroarea text, id_macroarea text, aggregazione text, id_aggregazione text, attivita text, id_linea text, list_cl jsonb)
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN	
IF (_riferimento_id = 'null' and _riferimento_id_nome_tab = 'null') or (_riferimento_id is null and _riferimento_id_nome_tab is null)  then
	FOR 
		codice_univoco,
	    norma,
	    id_norma,
	    macroarea,
	    id_macroarea,
	    aggregazione,
	    id_aggregazione,
	    attivita,
	    id_linea,
	    list_cl
    in
		SELECT t1.codice_univoco,
    t1.codice_norma AS norma,
    t1.id_norma,
    t1.macroarea,
    t1.id_macroarea,
    t1.aggregazione,
    t1.id_aggregazione,
    t1.attivita,
    t1.id_linea,
    t1.list_cl
   FROM dblink(public.getConfig(), 'SELECT 
			codice_univoco, 
			codice_norma,  
			id_norma::text,  
			macroarea,
			id_macroarea::text, 
			aggregazione,
			id_aggregazione::text, 
			attivita,
			id_linea::text, 
			NULL::jsonb list_cl  
		FROM giava.get_info_masterlist() 
		WHERE rev=10 and categorizzabili
		ORDER BY 2,4,6, 8'::text) t1(codice_univoco text, codice_norma text, id_norma text, macroarea text, id_macroarea text, aggregazione text, id_aggregazione text, attivita text, id_linea text, list_cl jsonb)
		LOOP
			RETURN NEXT;
		END LOOP; 
	-- chiamo la dbi senza parametri
ELSE
	FOR 
		codice_univoco,
	    norma,
	    id_norma,
	    macroarea,
	    id_macroarea,
	    aggregazione,
	    id_aggregazione,
	    attivita,
	    id_linea,
	    list_cl
    in
		SELECT t1.codice_univoco,
    t1.codice_norma AS norma,
    t1.id_norma,
    t1.macroarea,
    t1.id_macroarea,
    t1.aggregazione,
    t1.id_aggregazione,
    t1.attivita,
    t1.id_linea,
    t1.list_cl
   FROM dblink(public.getConfig(), 'SELECT 
			codice_univoco, 
			codice_norma,  
			id_norma::text,  
			macroarea,
			id_macroarea::text, 
			aggregazione,
			id_aggregazione::text, 
			attivita,
			id_linea::text, 
			NULL::jsonb list_cl  
		FROM giava.get_info_masterlist('||_riferimento_id||'::int, '''||_riferimento_id_nome_tab||'''::text) 
		WHERE (rev=10 or rev=8 or rev=-1) and categorizzabili
		ORDER BY 2,4,6, 8'::text) t1(codice_univoco text, codice_norma text, id_norma text, macroarea text, id_macroarea text, aggregazione text, id_aggregazione text, attivita text, id_linea text, list_cl jsonb)
		LOOP
			RETURN NEXT;
		END LOOP;
	end if;
	RETURN;
end;
$$;


ALTER FUNCTION public.ml10(_riferimento_id text, _riferimento_id_nome_tab text) OWNER TO postgres;

--
-- Name: norma(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.norma(_riferimento_id text DEFAULT NULL::text, _riferimento_id_nome_tab text DEFAULT NULL::text) RETURNS TABLE(norma text, id_linea text, id_norma text)
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN	
	FOR 
	    norma,
	    id_norma
    in
		 SELECT DISTINCT m.norma,
    m.id_norma
   FROM ml10(_riferimento_id, _riferimento_id_nome_tab) m
  ORDER BY m.norma
		LOOP
			RETURN NEXT;
		END LOOP;
	RETURN;
end;
$$;


ALTER FUNCTION public.norma(_riferimento_id text, _riferimento_id_nome_tab text) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: log_access; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.log_access (
    id bigint NOT NULL,
    id_utente bigint,
    entered timestamp without time zone,
    id_asl text,
    ip text
);


ALTER TABLE public.log_access OWNER TO postgres;

--
-- Name: access_log_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.access_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.access_log_id_seq OWNER TO postgres;

--
-- Name: access_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.access_log_id_seq OWNED BY public.log_access.id;


--
-- Name: ml10; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.ml10 AS
 SELECT t1.codice_univoco,
    t1.codice_norma AS norma,
    t1.id_norma,
    t1.macroarea,
    t1.id_macroarea,
    t1.aggregazione,
    t1.id_aggregazione,
    t1.attivita,
    t1.id_linea,
    t1.list_cl
   FROM public.dblink('dbname=gisa'::text, 'SELECT 
			codice_univoco, 
			codice_norma,  
			id_norma::text,  
			macroarea,
			id_macroarea::text, 
			aggregazione,
			id_aggregazione::text, 
			attivita,
			id_linea::text, 
			NULL::jsonb list_cl  
		FROM digemon.get_info_masterlist() 
		WHERE rev=10 and categorizzabili
		ORDER BY 2,4,6, 8'::text) t1(codice_univoco text, codice_norma text, id_norma text, macroarea text, id_macroarea text, aggregazione text, id_aggregazione text, attivita text, id_linea text, list_cl jsonb);


ALTER TABLE public.ml10 OWNER TO postgres;

--
-- Name: aggregazione; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.aggregazione AS
 SELECT DISTINCT ml10.aggregazione,
    ml10.id_aggregazione,
    ml10.id_macroarea
   FROM public.ml10
  ORDER BY ml10.aggregazione;


ALTER TABLE public.aggregazione OWNER TO postgres;

--
-- Name: ml10_old; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.ml10_old AS
 SELECT t1.codice_univoco,
    t1.codice_norma AS norma,
    t1.id_norma,
    t1.macroarea,
    t1.id_macroarea,
    t1.aggregazione,
    t1.id_aggregazione,
    t1.attivita,
    t1.id_linea,
    t1.list_cl
   FROM public.dblink(public.getconfig(), 'SELECT 
			codice_univoco, 
			codice_norma,  
			id_norma::text,  
			macroarea,
			id_macroarea::text, 
			aggregazione,
			id_aggregazione::text, 
			attivita,
			id_linea::text, 
			NULL::jsonb list_cl  
		FROM digemon.get_info_masterlist() 
		WHERE rev=10 and categorizzabili
		ORDER BY 2,4,6, 8'::text) t1(codice_univoco text, codice_norma text, id_norma text, macroarea text, id_macroarea text, aggregazione text, id_aggregazione text, attivita text, id_linea text, list_cl jsonb);


ALTER TABLE public.ml10_old OWNER TO postgres;

--
-- Name: aggregazione_old; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.aggregazione_old AS
 SELECT DISTINCT ml10_old.aggregazione,
    ml10_old.id_aggregazione,
    ml10_old.id_macroarea
   FROM public.ml10_old
  ORDER BY ml10_old.aggregazione;


ALTER TABLE public.aggregazione_old OWNER TO postgres;

--
-- Name: appdocu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.appdocu (
    id integer NOT NULL,
    doc text,
    code text,
    ord text,
    tit text
);


ALTER TABLE public.appdocu OWNER TO postgres;

--
-- Name: appdocu_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.appdocu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.appdocu_id_seq OWNER TO postgres;

--
-- Name: appdocu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.appdocu_id_seq OWNED BY public.appdocu.id;


--
-- Name: audit; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.audit AS
 SELECT t1.id,
    t1.org_id,
    t1.livello_rischio,
    t1.numero_registrazione,
    t1.componenti_gruppo,
    t1.note,
    t1.data_1,
    t1.data_2,
    t1.livello_rischio_finale,
    t1.tipo_check,
    t1.punteggio_ultimi_anni,
    t1.id_controllo,
    t1.categoria,
    t1.last,
    t1.data_prossimo_controllo,
    t1.flag_bb,
    t1.trashed_date,
    t1.modified_by,
    t1.is_principale,
    t1.stato,
    t1.id_stabilimento,
    t1.id_apiario,
    t1.alt_id
   FROM public.dblink(public.getconfig(), 'SELECT 
id, 
org_id, 
livello_rischio, 
numero_registrazione, 
componenti_gruppo, 
note, 
data_1, 
data_2, 
livello_rischio_finale, 
tipo_check, 
punteggio_ultimi_anni, 
id_controllo, 
categoria, 
"last", 
data_prossimo_controllo, 
flag_bb, 
trashed_date, 
modified_by, 
is_principale, 
stato, 
id_stabilimento, 
id_apiario, 
alt_id 
  from audit'::text) t1(id integer, org_id integer, livello_rischio integer, numero_registrazione character varying(100), componenti_gruppo text, note text, data_1 timestamp without time zone, data_2 timestamp without time zone, livello_rischio_finale integer, tipo_check integer, punteggio_ultimi_anni integer, id_controllo character varying, categoria integer, last text, data_prossimo_controllo timestamp without time zone, flag_bb boolean, trashed_date timestamp without time zone, modified_by integer, is_principale boolean, stato text, id_stabilimento integer, id_apiario integer, alt_id integer);


ALTER TABLE public.audit OWNER TO postgres;

--
-- Name: audit_checklist; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.audit_checklist AS
 SELECT t1.checklist_id,
    t1.audit_id,
    t1.risposta,
    t1.punti,
    t1.stato
   FROM public.dblink(public.getconfig(), 'SELECT 
checklist_id, 
audit_id, 
risposta, 
punti, 
stato
  from audit_checklist'::text) t1(checklist_id integer, audit_id integer, risposta boolean, punti integer, stato text);


ALTER TABLE public.audit_checklist OWNER TO postgres;

--
-- Name: audit_checklist_type; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.audit_checklist_type AS
 SELECT t1.checklist_type_id,
    t1.audit_id,
    t1.valore_range,
    t1.operazione,
    t1.nota,
    t1.is_abilitato
   FROM public.dblink(public.getconfig(), 'SELECT 
checklist_type_id, 
audit_id, 
valore_range, 
operazione, nota, 
is_abilitato  
from audit_checklist_type'::text) t1(checklist_type_id integer, audit_id integer, valore_range integer, operazione character(1), nota text, is_abilitato boolean);


ALTER TABLE public.audit_checklist_type OWNER TO postgres;

--
-- Name: checklist; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.checklist AS
 SELECT t1.id,
    t1.parent_id,
    t1.checklist_type_id,
    t1.domanda,
    t1.descrizione,
    t1.punti_no,
    t1.punti_si,
    t1.level,
    t1.enabled,
    t1.grand_parents_id,
    t1.super_domanda
   FROM public.dblink(public.getconfig(), 'SELECT 
id, 
parent_id, 
checklist_type_id, 
domanda, descrizione, 
punti_no, 
punti_si, 
"level", 
enabled, 
grand_parents_id, 
super_domanda 
from checklist'::text) t1(id integer, parent_id integer, checklist_type_id integer, domanda text, descrizione text, punti_no integer, punti_si integer, level integer, enabled boolean, grand_parents_id integer, super_domanda boolean);


ALTER TABLE public.checklist OWNER TO postgres;

--
-- Name: checklist_type; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.checklist_type AS
 SELECT t1.code,
    t1.catrischio_id,
    t1.description,
    t1.range,
    t1.default_item,
    t1.level,
    t1.enabled,
    t1.is_disabilitato,
    t1.is_disabilitato_solo_xlaprima,
    t1.is_disabilitabile
   FROM public.dblink(public.getconfig(), 'SELECT 
code, 
catrischio_id, 
description, 
"range", 
default_item, 
"level", 
enabled, 
is_disabilitato, 
is_disabilitato_solo_xlaprima, 
is_disabilitabile
from checklist_type'::text) t1(code integer, catrischio_id integer, description character varying(300), range integer, default_item boolean, level integer, enabled boolean, is_disabilitato boolean, is_disabilitato_solo_xlaprima boolean, is_disabilitabile integer);


ALTER TABLE public.checklist_type OWNER TO postgres;

--
-- Name: lookup_org_catrischio; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.lookup_org_catrischio AS
 SELECT t1.code,
    t1.description,
    t1.short_description,
    t1.default_item,
    t1.level,
    t1.enabled,
    t1.versione
   FROM public.dblink(public.getconfig(), 'SELECT 
code, 
description, 
short_description, 
default_item, 
"level", 
enabled, 
versione
from lookup_org_catrischio'::text) t1(code integer, description character varying(300), short_description character varying(300), default_item boolean, level integer, enabled boolean, versione double precision);


ALTER TABLE public.lookup_org_catrischio OWNER TO postgres;

--
-- Name: cl_chapts; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.cl_chapts AS
 SELECT c.code AS c_id,
    c.description AS c_desc,
    c.range,
    c.level AS c_level,
    c.is_disabilitato AS c_is_disabilitato,
    l.level AS l_level,
    l.description AS l_desc,
    l.short_description AS l_short_desc,
    l.code AS l_id
   FROM (public.checklist_type c
     JOIN public.lookup_org_catrischio l ON ((c.catrischio_id = l.code)))
  WHERE (c.enabled AND l.enabled)
  ORDER BY l.description, c.description;


ALTER TABLE public.cl_chapts OWNER TO postgres;

--
-- Name: cl_quests; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.cl_quests AS
 SELECT d.id AS q_id,
    d.domanda,
    '-'::text AS sotto_domanda,
    d.parent_id,
    d.punti_no,
    d.punti_si,
    d.grand_parents_id,
    d.checklist_type_id AS id_chap
   FROM public.checklist d
  WHERE (d.enabled AND (d.parent_id = '-1'::integer))
UNION
 SELECT sd.id AS q_id,
    d.domanda,
    sd.domanda AS sotto_domanda,
    sd.parent_id,
    sd.punti_no,
    sd.punti_si,
    d.grand_parents_id,
    d.checklist_type_id AS id_chap
   FROM (public.checklist d
     JOIN public.checklist sd ON ((d.id = sd.parent_id)))
  WHERE (d.enabled AND sd.enabled AND (sd.parent_id > '-1'::integer))
  ORDER BY 1;


ALTER TABLE public.cl_quests OWNER TO postgres;

--
-- Name: cl_all; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.cl_all AS
 SELECT cl_quests.q_id,
    c.c_id,
    c.l_id,
    c.l_desc,
    c.c_desc,
    cl_quests.domanda,
    cl_quests.sotto_domanda,
    cl_quests.parent_id,
    cl_quests.punti_no,
    cl_quests.punti_si
   FROM (public.cl_chapts c
     LEFT JOIN public.cl_quests ON ((cl_quests.id_chap = c.c_id)));


ALTER TABLE public.cl_all OWNER TO postgres;

--
-- Name: cl_lists; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.cl_lists AS
 SELECT l.code AS l_id,
    l.level AS l_level,
    l.description AS l_desc,
    l.short_description AS l_short_desc,
    l.default_item AS def,
    l.versione AS ver
   FROM public.lookup_org_catrischio l
  WHERE l.enabled
  ORDER BY l.description, l.short_description;


ALTER TABLE public.cl_lists OWNER TO postgres;

--
-- Name: config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.config (
    host character varying,
    dbname character varying,
    "user" character varying,
    password character varying,
    port character varying
);


ALTER TABLE public.config OWNER TO postgres;

--
-- Name: lda; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.lda AS
 SELECT DISTINCT ml10.attivita,
    ml10.id_linea,
    ml10.id_aggregazione
   FROM public.ml10
  ORDER BY ml10.attivita;


ALTER TABLE public.lda OWNER TO postgres;

--
-- Name: lda_old; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.lda_old AS
 SELECT DISTINCT ml10_old.attivita,
    ml10_old.id_linea,
    ml10_old.id_aggregazione
   FROM public.ml10_old
  ORDER BY ml10_old.attivita;


ALTER TABLE public.lda_old OWNER TO postgres;

--
-- Name: lista_cl; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.lista_cl AS
 SELECT DISTINCT ml10_old.list_cl,
    ml10_old.id_linea
   FROM public.ml10_old
  ORDER BY ml10_old.list_cl;


ALTER TABLE public.lista_cl OWNER TO postgres;

--
-- Name: log_checklist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.log_checklist (
    id bigint NOT NULL,
    id_checklist bigint,
    id_utente bigint,
    entered timestamp without time zone
);


ALTER TABLE public.log_checklist OWNER TO postgres;

--
-- Name: log_checklist_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.log_checklist_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.log_checklist_id_seq OWNER TO postgres;

--
-- Name: log_checklist_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.log_checklist_id_seq OWNED BY public.log_checklist.id;


--
-- Name: macroarea; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.macroarea AS
 SELECT DISTINCT ml10_old.macroarea,
    ml10_old.id_macroarea,
    ml10_old.id_norma
   FROM public.ml10_old
  ORDER BY ml10_old.macroarea;


ALTER TABLE public.macroarea OWNER TO postgres;

--
-- Name: norma; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.norma AS
 SELECT DISTINCT ml10.norma,
    ml10.id_norma
   FROM public.ml10
  ORDER BY ml10.norma;


ALTER TABLE public.norma OWNER TO postgres;

--
-- Name: norma_old; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.norma_old AS
 SELECT DISTINCT ml10_old.norma,
    ml10_old.id_norma
   FROM public.ml10_old
  ORDER BY ml10_old.norma;


ALTER TABLE public.norma_old OWNER TO postgres;

--
-- Name: public_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.public_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.public_user_id_seq OWNER TO postgres;

--
-- Name: utente_risposta_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.utente_risposta_id_seq
    START WITH 881
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;


ALTER TABLE public.utente_risposta_id_seq OWNER TO postgres;

--
-- Name: utente_risposta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utente_risposta (
    id bigint DEFAULT nextval('public.utente_risposta_id_seq'::regclass),
    id_utente bigint,
    id_checklist bigint,
    id_domanda bigint,
    punteggio integer,
    risposta character varying,
    entered timestamp(0) without time zone
);


ALTER TABLE public.utente_risposta OWNER TO postgres;

--
-- Name: appdocu id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.appdocu ALTER COLUMN id SET DEFAULT nextval('public.appdocu_id_seq'::regclass);


--
-- Name: log_access id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_access ALTER COLUMN id SET DEFAULT nextval('public.access_log_id_seq'::regclass);


--
-- Name: log_checklist id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_checklist ALTER COLUMN id SET DEFAULT nextval('public.log_checklist_id_seq'::regclass);


--
-- Data for Name: appdocu; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.appdocu (id, doc, code, ord, tit) FROM stdin;
3	La view contenente le checklist si riduce dunque a poco più che id e descrizione.\r\n	\r\nCREATE OR REPLACE VIEW public.cl_lists AS \r\n SELECT l.code AS l_id,\r\n    l.level AS l_level,\r\n    l.description AS l_desc,\r\n    l.short_description AS l_short_desc,\r\n    l.default_item AS def,\r\n    l.versione AS ver\r\n   FROM lookup_org_catrischio l\r\n  WHERE l.enabled\r\n  ORDER BY l.description, l.short_description;	3	\N
4	Allo stesso modo la view contenente i capitoli si riduce a id, descrizione oltre alla ovvia chiave esterna verso le check list per mantenere il relazione.\r\n	\r\nCREATE OR REPLACE VIEW public.cl_chapts AS \r\n SELECT c.code AS c_id,\r\n    c.description AS c_desc,\r\n    c.range,\r\n    c.level AS c_level,\r\n    c.is_disabilitato AS c_is_disabilitato,\r\n    l.level AS l_level,\r\n    l.description AS l_desc,\r\n    l.short_description AS l_short_desc,\r\n    l.code AS l_id\r\n   FROM checklist_type c\r\n     JOIN lookup_org_catrischio l ON c.catrischio_id = l.code\r\n  WHERE c.enabled AND l.enabled\r\n  ORDER BY l.description, c.description;\r\n\r\n  	4	\N
5	La view delle domande appare invece leggermente più articolata. Lo schema record risultante è una parziale denormalizzazione rispetto alla relazione tra domande e sottodomande che viene linearizzata in maniera molto semplice e intuitiva, e soprattuto molto corrispondente alla modalità di visualizzazione scelta. Da notare che oltre alla struttura domanda-sottodomanda e alla chiave esterna verso il capitolo, viene conservato l'id del record padre. Nel caso delle domande sarà ovviamente non significativo e settato al valore -1. Altra cosa da notare è l'id del record nel caso di domande senza sottodomande è ovviamente univoco mentre nel caso di record relativo alla sottodomanda, poichè come descrizione compare sia la sottodomanda che la domanda parent, l'ambiguità potrebbe nascere. In questo caso va tenuto presente che l'id del record è quello della sottodomanda mentre l'id della domanda viene mantenuto nel capo id parent. Ultima cosa: i punteggi da attribure in caso di risposta SI o NO.	\r\nCREATE OR REPLACE VIEW public.cl_quests AS \r\n SELECT d.id AS q_id,\r\n    d.domanda,\r\n    '-'::text AS sotto_domanda,\r\n    d.parent_id,\r\n    d.punti_no,\r\n    d.punti_si,\r\n    d.grand_parents_id,\r\n    d.checklist_type_id AS id_chap\r\n   FROM checklist d\r\n  WHERE d.enabled AND d.parent_id = '-1'::integer\r\nUNION\r\n SELECT sd.id AS q_id,\r\n    d.domanda,\r\n    sd.domanda AS sotto_domanda,\r\n    sd.parent_id,\r\n    d.punti_no,\r\n    d.punti_si,\r\n    d.grand_parents_id,\r\n    d.checklist_type_id AS id_chap\r\n   FROM checklist d\r\n     JOIN checklist sd ON d.id = sd.parent_id\r\n  WHERE d.enabled AND sd.enabled AND sd.parent_id > '-1'::integer\r\n  ORDER BY 1;\r\n  	5	\N
6	Esiste infine un terzo livello, utilizzato per semplificare la gestione di una singola checklist nella sua completa struttura gerarchica. \r\nIn questa view compaiono le info strettamente necessarie per la generazione della GUI: le varie chiavi, le varie descrizioni e i punteggi SI e NO. A partire da questo tracciato viene generato l''array di oggetti JSON utilizzati per interagire, client side, con la parte html e javascript	\r\nCREATE OR REPLACE VIEW public.cl_all AS \r\n SELECT cl_quests.q_id,\r\n    c.c_id,\r\n    c.l_id,\r\n    c.l_desc,\r\n    c.c_desc,\r\n    cl_quests.domanda,\r\n    cl_quests.sotto_domanda,\r\n    cl_quests.parent_id,\r\n    cl_quests.punti_no,\r\n    cl_quests.punti_si\r\n   FROM cl_chapts c\r\n     LEFT JOIN cl_quests ON cl_quests.id_chap = c.c_id;\r\n  	6	\N
7	Il view-model, o presentation model o comunque la struttura dati fondamentale lato client è dunque la cheklist intesa come lista ordinata di capitoli, domande e sottodomande, con relativi punteggi di risposta affermativa e negativa.   	[\r\n  {\r\n    "l_id": "2022",\r\n    "l_desc": "Esercizio Vendita (rev 3)",\r\n    "c_id": "15188",\r\n    "c_desc": "CAPITOLO I: LOCALI,IMPIANTI ED ATTREZZATURE",\r\n    "parent_id": "-1",\r\n    "q_id": "410208",\r\n    "domanda": "L''azienda è regolarmente registrata?",\r\n    "punti_si": "0",\r\n    "punti_no": "20"\r\n  },\r\n  {\r\n    "l_id": "2022",\r\n    "l_desc": "Esercizio Vendita (rev 3)",\r\n    "c_id": "15188",\r\n    "c_desc": "CAPITOLO I: LOCALI,IMPIANTI ED ATTREZZATURE",\r\n    "parent_id": "-1",\r\n    "q_id": "410209",\r\n    "domanda": "Il o i varchi di accesso per la clientela ed altri eventuali varchi che danno all''esterno,sono chiudibili tramite una o più porte d''ingresso?",\r\n    "punti_si": "0",\r\n    "punti_no": "2"\r\n  },\r\n  {\r\n    "l_id": "2022",\r\n    "l_desc": "Esercizio Vendita (rev 3)",\r\n    "c_id": "15188",\r\n    "c_desc": "CAPITOLO I: LOCALI,IMPIANTI ED ATTREZZATURE",\r\n    "parent_id": "410209",\r\n    "q_id": "410210",\r\n    "domanda": "Il o i varchi di accesso per la clientela ed altri eventuali varchi che danno all''esterno,sono chiudibili tramite una o più porte d''ingresso?",\r\n    "punti_si": "0",\r\n    "punti_no": "2"\r\n  }\r\n]	7	JSON
2	Per evitare approcci invasivi, viene utilizzato un DB esterno come wrapper verso il DB Gisa. \r\nOgnuna delle tabelle interessate (sorv, sorv_checklist, sorv_checklist_type) viene replicata tramite una view su tabella remota basata su dblink.\r\nPer evidenziare la corrispondenza 1-a-1 tra dati locali e dati remoti, le view replicano esattamente anche il nome delle tabelle a cui si riferiscono. Questo rappresenta il primo livello dei dati. A partire da questo livello, viene utilizzato un secondo strato di view per modellare in maniera più semplice possibile le entità gerarchiche Checklist, Capitoli, Domande, Sottodomande. \r\nLe view di livello successivo al primo sono identificabili dal prefisso "cl_". 	\r\nCREATE OR REPLACE VIEW public.sorv AS\r\nSELECT\r\n  t1.id\r\n, t1.org_id\r\n, t1.livello_rischio\r\n, t1.numero_registrazione\r\n, t1.componenti_gruppo\r\n, t1.note\r\n, t1.data_1\r\n, t1.data_2\r\n, t1.livello_rischio_finale\r\n, t1.tipo_check\r\n, t1.punteggio_ultimi_anni\r\n, t1.id_controllo\r\n, t1.categoria\r\n, t1.last\r\n, t1.data_prossimo_controllo\r\n, t1.flag_bb\r\n, t1.trashed_date\r\n, t1.modified_by\r\n, t1.is_principale\r\n, t1.stato\r\n, t1.id_stabilimento\r\n, t1.id_apiario\r\n, t1.alt_id\r\nFROM\r\n  dblink('dbname=gisa'::text, 'SELECT\r\nid,\r\norg_id,\r\nlivello_rischio,\r\nnumero_registrazione,\r\ncomponenti_gruppo,\r\nnote,\r\ndata_1,\r\ndata_2,\r\nlivello_rischio_finale,\r\ntipo_check,\r\npunteggio_ultimi_anni,\r\nid_controllo,\r\ncategoria,\r\n"last",\r\ndata_prossimo_controllo,\r\nflag_bb,\r\ntrashed_date,\r\nmodified_by,\r\nis_principale,\r\nstato,\r\nid_stabilimento,\r\nid_apiario,\r\nalt_id  \r\nfrom sorv'::text) \r\nt1(\r\n  id integer, \r\n  org_id integer, \r\n  livello_rischio integer, \r\n  numero_registrazione character varying(100), \r\n  componenti_gruppo text, \r\n  note text, \r\n  data_1 timestamp without time zone, \r\n  data_2 timestamp without time zone, \r\n  livello_rischio_finale integer, \r\n  tipo_check integer, \r\n  punteggio_ultimi_anni integer, \r\n  id_controllo character varying, \r\n  categoria integer, \r\n  last text, \r\n  data_prossimo_controllo timestamp without time zone, \r\n  flag_bb boolean, \r\n  trashed_date timestamp without time zone, \r\n  modified_by integer, \r\n  is_principale boolean, \r\n  stato text, \r\n  id_stabilimento integer, \r\n  id_apiario integer, \r\n  alt_id integer\r\n);\r\n	2	Database
4	Allo stesso modo la view contenente i capitoli si riduce a id, descrizione oltre alla ovvia chiave esterna verso le check list per mantenere il relazione.\r\n	\r\nCREATE OR REPLACE VIEW public.cl_chapts AS \r\n SELECT c.code AS c_id,\r\n    c.description AS c_desc,\r\n    c.range,\r\n    c.level AS c_level,\r\n    c.is_disabilitato AS c_is_disabilitato,\r\n    l.level AS l_level,\r\n    l.description AS l_desc,\r\n    l.short_description AS l_short_desc,\r\n    l.code AS l_id\r\n   FROM checklist_type c\r\n     JOIN lookup_org_catrischio l ON c.catrischio_id = l.code\r\n  WHERE c.enabled AND l.enabled\r\n  ORDER BY l.description, c.description;\r\n\r\n  	4	\N
9	 \r\nLe Gui dell'applicazione sono pagine HTML generate in risposta ad una chiamata client. In generale sono tabelle dinamiche ottenute a partire da template che iterano su un array di righe    \r\n	\r\n  <table class="table table-striped table-bordered table-sm table-condensed">\r\n        \r\n    <thead>\r\n      <tr>\r\n\r\n        <th class="small font-weight-bold">Domanda</th>\r\n        <th class="small font-weight-bold">Sottodomanda</th>\r\n        <th class="small font-weight-bold" colspan="2">Punteggio</th>\r\n      </tr>\r\n    </thead>\r\n\r\n    <tbody>\r\n  {{$chp:=""}}\r\n  {{$dprec:=""}}\r\n  {{$idparent:=""}}\r\n {{range $i, $qst :=   .Qsts_1 }}\r\n\t{{ if ne $chp  $qst.C_desc }} {{$chp = $qst.C_desc}} <tr><td class="text-center chphdr" colspan="5">{{$qst.C_desc}}</td></tr>{{end}}\r\n\t<tr>\r\n        <td class="small {{ if eq $dprec $qst.Domanda }} font-weight-light font-italic{{end}}">{{ $qst.Domanda }}</td>\r\n        <td class="small">{{ $qst.SottoDomanda }}</td>\r\n        <td class="small text-center text-monospace" >\r\n\t\t\t<label>\r\n\t\t\t<input {{ if eq $dprec $qst.Domanda }} disabled data-idparent="{{ $idparent }}" {{else}} {{$idparent = $i}} data-id="{{$i}}"{{end}} onclick="updTot(this);" type="radio" name="q{{$i}}" value="{{ $qst.Punti_si }}" /> SI {{$qst.Punti_si|printf "%03d"}}\r\n\t\t\t</label>\r\n\t\t</td>\r\n        <td class="small text-center text-monospace">\r\n\t\t\t<label>\r\n\t\t\t<input {{ if eq $dprec $qst.Domanda }} disabled data-idparent="{{ $idparent }}" {{else}} data-id="{{$i}}" {{end}} onclick="updTot(this);"type="radio" name="q{{$i}}" value="{{ $qst.Punti_no }}" /> NO {{$qst.Punti_no|printf "%03d"}}\r\n\t\t\t</label>\r\n\t\t</td>\r\n    </tr>\r\n\t{{$dprec = $qst.Domanda}} \t\r\n {{end}}\r\n\t</tbody>\r\n\t</table>\r\n\r\n	9	View basata su HTML templates
1	Scopo dell'applicazione è fornire uno strumento semplificato di accesso alle checklist di valutazione del rischio. Le checklist sono abitualmente utilizzate nell'ambito dei controlli ufficiali di sorveglianza del sistema Gisa e la loro gestione richiede una ampia conoscenza dell'operatività del sistema. A pertire dall'accesso semplificato, diventa possibile eseguire rapide simulazioni finalizzate eventualmente a disposizione degli OSA per auto-valutazione. Un altro utilizzo potrebbe riguardare i referenti regionali addetti alla manutenzione evolutiva delle check list. 	\r\n   \r\n                              \\\\\\\\\\\\\\\r\n                            \\\\\\\\\\\\\\\\\\\\\\\\\r\n                          \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\r\n  -----------,-|           |C>   // )\\\\\\\\|\r\n           ,','|          /    || ,'/////|\r\n---------,','  |         (,    ||   /////\r\n         ||    |          \\\\  ||||//''''|\r\n         ||    |           |||||||     _|\r\n         ||    |______      `````\\____/ \\\r\n         ||    |     ,|         _/_____/ \\\r\n         ||  ,'    ,' |        /          |\r\n         ||,'    ,'   |       |         \\  |\r\n_________|/    ,'     |      /           | |\r\n_____________,'      ,',_____|      |    | |\r\n             |     ,','      |      |    | |\r\n             |   ,','    ____|_____/    /  |\r\n             | ,','  __/ |             /   |\r\n_____________|','   ///_/-------------/   |\r\n              |===========,'\r\n\r\n   	1	Obiettivi perseguiti
8	 La componente di backend è organizzata secondo il pattern architetturale Microservices, le chiamate saranno quindi molto simili a invocazioni di Api REST. Questo livello si occupa sostanzialmente di fare il router istradando le chiamate del client verso il DB e generando le Gui di risposta.  \r\n	\r\napp.Get("/get_chp/{idcl:int}", func(ctx iris.Context) {\t\r\n\t\r\n\tChps := []Chp{}\t\r\n\tid,_ := ctx.Params().GetInt("idcl")\t\r\n\tdb.Raw("select c_id, c_desc from cl_chapts where l_id=? AND not c_is_disabilitato order by 1", id).Find(&Chps)\r\n\tctx.ViewData("Chps", Chps)\r\n\tctx.View("chp.html")\t\r\n})\r\n\r\napp.Get("/get_qst/{idchp:int}", func(ctx iris.Context) {\r\n\t\t\r\n\tQsts := []Qst{}\t\r\n\tid,_ := ctx.Params().GetInt("idchp")\r\n\tdb.Raw("select q_id, domanda, sotto_domanda, punti_si, punti_no from cl_quests cq where id_chap=? order by 1", id).Find(&Qsts)\r\n\tctx.ViewData("Qsts", Qsts)\r\n\tctx.View("qst.html")\t\r\n\t})\r\n\r\napp.Get("/get_chp1/{idcl:int}", func(ctx iris.Context) {\r\n\t\t\r\n\tQsts_1 := []Qst_1{}\r\n\t\r\n\tid,_ := ctx.Params().GetInt("idcl")\r\n\t\r\n\tapp.Logger().Infof(" chapter of l_id: %d", id)\r\n\r\n\tdb.Raw("select q_id, c_desc, domanda, sotto_domanda, punti_si, punti_no from cl_all cq where l_id=? order by 1", id).Find(&Qsts_1)\r\n\r\n\tctx.ViewData("Qsts_1", Qsts_1)\r\n\tctx.View("qst_1.html")\r\n\t\r\n})\r\n\r\n   	8	Controller basato su Microservices
2	Per evitare approcci invasivi, viene utilizzato un DB esterno come wrapper verso il DB Gisa. \r\nOgnuna delle tabelle interessate (audit, audit_checklist, audit_checklist_type) viene replicata tramite una view su tabella remota basata su dblink.\r\nPer evidenziare la corrispondenza 1-a-1 tra dati locali e dati remoti, le view replicano esattamente anche il nome delle tabelle a cui si riferiscono. Questo rappresenta il primo livello dei dati. A partire da questo livello, viene utilizzato un secondo strato di view per modellare in maniera più semplice possibile le entità gerarchiche Checklist, Capitoli, Domande, Sottodomande. \r\nLe view di livello successivo al primo sono identificabili dal prefisso "cl_". 	\r\nCREATE OR REPLACE VIEW public.audit AS\r\nSELECT\r\n  t1.id\r\n, t1.org_id\r\n, t1.livello_rischio\r\n, t1.numero_registrazione\r\n, t1.componenti_gruppo\r\n, t1.note\r\n, t1.data_1\r\n, t1.data_2\r\n, t1.livello_rischio_finale\r\n, t1.tipo_check\r\n, t1.punteggio_ultimi_anni\r\n, t1.id_controllo\r\n, t1.categoria\r\n, t1.last\r\n, t1.data_prossimo_controllo\r\n, t1.flag_bb\r\n, t1.trashed_date\r\n, t1.modified_by\r\n, t1.is_principale\r\n, t1.stato\r\n, t1.id_stabilimento\r\n, t1.id_apiario\r\n, t1.alt_id\r\nFROM\r\n  dblink('dbname=gisa'::text, 'SELECT\r\nid,\r\norg_id,\r\nlivello_rischio,\r\nnumero_registrazione,\r\ncomponenti_gruppo,\r\nnote,\r\ndata_1,\r\ndata_2,\r\nlivello_rischio_finale,\r\ntipo_check,\r\npunteggio_ultimi_anni,\r\nid_controllo,\r\ncategoria,\r\n"last",\r\ndata_prossimo_controllo,\r\nflag_bb,\r\ntrashed_date,\r\nmodified_by,\r\nis_principale,\r\nstato,\r\nid_stabilimento,\r\nid_apiario,\r\nalt_id  \r\nfrom audit'::text) \r\nt1(\r\n  id integer, \r\n  org_id integer, \r\n  livello_rischio integer, \r\n  numero_registrazione character varying(100), \r\n  componenti_gruppo text, \r\n  note text, \r\n  data_1 timestamp without time zone, \r\n  data_2 timestamp without time zone, \r\n  livello_rischio_finale integer, \r\n  tipo_check integer, \r\n  punteggio_ultimi_anni integer, \r\n  id_controllo character varying, \r\n  categoria integer, \r\n  last text, \r\n  data_prossimo_controllo timestamp without time zone, \r\n  flag_bb boolean, \r\n  trashed_date timestamp without time zone, \r\n  modified_by integer, \r\n  is_principale boolean, \r\n  stato text, \r\n  id_stabilimento integer, \r\n  id_apiario integer, \r\n  alt_id integer\r\n);\r\n	2	Database
3	La view contenente le checklist si riduce dunque a poco più che id e descrizione.\r\n	\r\nCREATE OR REPLACE VIEW public.cl_lists AS \r\n SELECT l.code AS l_id,\r\n    l.level AS l_level,\r\n    l.description AS l_desc,\r\n    l.short_description AS l_short_desc,\r\n    l.default_item AS def,\r\n    l.versione AS ver\r\n   FROM lookup_org_catrischio l\r\n  WHERE l.enabled\r\n  ORDER BY l.description, l.short_description;	3	\N
1	Scopo dell'applicazione è fornire uno strumento semplificato di accesso alle checklist di valutazione del rischio. Le checklist sono abitualmente utilizzate nell'ambito dei controlli ufficiali di sorveglianza del sistema Gisa e la loro gestione richiede una ampèia conoscenza dell'operatività del sistema. A pertire dall'accesso semplificato, diventa possibile eseguire rapide simulazioni finalizzate eventualmente a disposizione degli OSA per auto-valutazione. Un altro utilizzo potrebbe riguardare i referenti regionali addetti alla manutenzione evolutiva delle check list. 	\r\n   \r\n                              \\\\\\\\\\\\\\\r\n                            \\\\\\\\\\\\\\\\\\\\\\\\\r\n                          \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\r\n  -----------,-|           |C>   // )\\\\\\\\|\r\n           ,','|          /    || ,'/////|\r\n---------,','  |         (,    ||   /////\r\n         ||    |          \\\\  ||||//''''|\r\n         ||    |           |||||||     _|\r\n         ||    |______      `````\\____/ \\\r\n         ||    |     ,|         _/_____/ \\\r\n         ||  ,'    ,' |        /          |\r\n         ||,'    ,'   |       |         \\  |\r\n_________|/    ,'     |      /           | |\r\n_____________,'      ,',_____|      |    | |\r\n             |     ,','      |      |    | |\r\n             |   ,','    ____|_____/    /  |\r\n             | ,','  __/ |             /   |\r\n_____________|','   ///_/-------------/   |\r\n              |===========,'\r\n\r\n   	1	Obiettivi perseguiti
5	La view delle domande appare invece leggermente più articolata. Lo schema record risultante è una parziale denormalizzazione rispetto alla relazione tra domande e sottodomande che viene linearizzata in maniera molto semplice e intuitiva, e soprattuto molto corrispondente alla modalità di visualizzazione scelta. Da notare che oltre alla struttura domanda-sottodomanda e alla chiave esterna verso il capitolo, viene conservato l'id del record padre. Nel caso delle domande sarà ovviamente non significativo e settato al valore -1. Altra cosa da notare è l'id del record nel caso di domande senza sottodomande è ovviamente univoco mentre nel caso di record relativo alla sottodomanda, poichè come descrizione compare sia la sottodomanda che la domanda parent, l'ambiguità potrebbe nascere. In questo caso va tenuto presente che l'id del record è quello della sottodomanda mentre l'id della domanda viene mantenuto nel capo id parent. Ultima cosa: i punteggi da attribure in caso di risposta SI o NO.	\r\nCREATE OR REPLACE VIEW public.cl_quests AS \r\n SELECT d.id AS q_id,\r\n    d.domanda,\r\n    '-'::text AS sotto_domanda,\r\n    d.parent_id,\r\n    d.punti_no,\r\n    d.punti_si,\r\n    d.grand_parents_id,\r\n    d.checklist_type_id AS id_chap\r\n   FROM checklist d\r\n  WHERE d.enabled AND d.parent_id = '-1'::integer\r\nUNION\r\n SELECT sd.id AS q_id,\r\n    d.domanda,\r\n    sd.domanda AS sotto_domanda,\r\n    sd.parent_id,\r\n    d.punti_no,\r\n    d.punti_si,\r\n    d.grand_parents_id,\r\n    d.checklist_type_id AS id_chap\r\n   FROM checklist d\r\n     JOIN checklist sd ON d.id = sd.parent_id\r\n  WHERE d.enabled AND sd.enabled AND sd.parent_id > '-1'::integer\r\n  ORDER BY 1;\r\n  	5	\N
6	Esiste infine un terzo livello, utilizzato per semplificare la gestione di una singola checklist nella sua completa struttura gerarchica. \r\nIn questa view compaiono le info strettamente necessarie per la generazione della GUI: le varie chiavi, le varie descrizioni e i punteggi SI e NO. A partire da questo tracciato viene generato l''array di oggetti JSON utilizzati per interagire, client side, con la parte html e javascript	\r\nCREATE OR REPLACE VIEW public.cl_all AS \r\n SELECT cl_quests.q_id,\r\n    c.c_id,\r\n    c.l_id,\r\n    c.l_desc,\r\n    c.c_desc,\r\n    cl_quests.domanda,\r\n    cl_quests.sotto_domanda,\r\n    cl_quests.parent_id,\r\n    cl_quests.punti_no,\r\n    cl_quests.punti_si\r\n   FROM cl_chapts c\r\n     LEFT JOIN cl_quests ON cl_quests.id_chap = c.c_id;\r\n  	6	\N
7	Il view-model, o presentation model o comunque la struttura dati fondamentale lato client è dunque la cheklist intesa come lista ordinata di capitoli, domande e sottodomande, con relativi punteggi di risposta affermativa e negativa.   	[\r\n  {\r\n    "l_id": "2022",\r\n    "l_desc": "Esercizio Vendita (rev 3)",\r\n    "c_id": "15188",\r\n    "c_desc": "CAPITOLO I: LOCALI,IMPIANTI ED ATTREZZATURE",\r\n    "parent_id": "-1",\r\n    "q_id": "410208",\r\n    "domanda": "L''azienda è regolarmente registrata?",\r\n    "punti_si": "0",\r\n    "punti_no": "20"\r\n  },\r\n  {\r\n    "l_id": "2022",\r\n    "l_desc": "Esercizio Vendita (rev 3)",\r\n    "c_id": "15188",\r\n    "c_desc": "CAPITOLO I: LOCALI,IMPIANTI ED ATTREZZATURE",\r\n    "parent_id": "-1",\r\n    "q_id": "410209",\r\n    "domanda": "Il o i varchi di accesso per la clientela ed altri eventuali varchi che danno all''esterno,sono chiudibili tramite una o più porte d''ingresso?",\r\n    "punti_si": "0",\r\n    "punti_no": "2"\r\n  },\r\n  {\r\n    "l_id": "2022",\r\n    "l_desc": "Esercizio Vendita (rev 3)",\r\n    "c_id": "15188",\r\n    "c_desc": "CAPITOLO I: LOCALI,IMPIANTI ED ATTREZZATURE",\r\n    "parent_id": "410209",\r\n    "q_id": "410210",\r\n    "domanda": "Il o i varchi di accesso per la clientela ed altri eventuali varchi che danno all''esterno,sono chiudibili tramite una o più porte d''ingresso?",\r\n    "punti_si": "0",\r\n    "punti_no": "2"\r\n  }\r\n]	7	JSON
8	 La componente di backend è organizzata secondo il pattern architetturale Microservices, le chiamate saranno quindi molto simili a invocazioni di Api REST. Questo livello si occupa sostanzialmente di fare il router istadando le chiamate del client verso il DB e generando le Gui di risposta.  \r\n	\r\napp.Get("/get_chp/{idcl:int}", func(ctx iris.Context) {\t\r\n\t\r\n\tChps := []Chp{}\t\r\n\tid,_ := ctx.Params().GetInt("idcl")\t\r\n\tdb.Raw("select c_id, c_desc from cl_chapts where l_id=? AND not c_is_disabilitato order by 1", id).Find(&Chps)\r\n\tctx.ViewData("Chps", Chps)\r\n\tctx.View("chp.html")\t\r\n})\r\n\r\napp.Get("/get_qst/{idchp:int}", func(ctx iris.Context) {\r\n\t\t\r\n\tQsts := []Qst{}\t\r\n\tid,_ := ctx.Params().GetInt("idchp")\r\n\tdb.Raw("select q_id, domanda, sotto_domanda, punti_si, punti_no from cl_quests cq where id_chap=? order by 1", id).Find(&Qsts)\r\n\tctx.ViewData("Qsts", Qsts)\r\n\tctx.View("qst.html")\t\r\n\t})\r\n\r\napp.Get("/get_chp1/{idcl:int}", func(ctx iris.Context) {\r\n\t\t\r\n\tQsts_1 := []Qst_1{}\r\n\t\r\n\tid,_ := ctx.Params().GetInt("idcl")\r\n\t\r\n\tapp.Logger().Infof(" chapter of l_id: %d", id)\r\n\r\n\tdb.Raw("select q_id, c_desc, domanda, sotto_domanda, punti_si, punti_no from cl_all cq where l_id=? order by 1", id).Find(&Qsts_1)\r\n\r\n\tctx.ViewData("Qsts_1", Qsts_1)\r\n\tctx.View("qst_1.html")\r\n\t\r\n})\r\n\r\n   	8	Controller basato su Microservices
9	 \r\nLe Gui dell'applicazione sono pagine HTML generate in risposta ad una chiamata client. In generale sono tabelle dinamiche ottenute a partire da template che iterano su un array di righe    \r\n	\r\n  <table class="table table-striped table-bordered table-sm table-condensed">\r\n        \r\n    <thead>\r\n      <tr>\r\n\r\n        <th class="small font-weight-bold">Domanda</th>\r\n        <th class="small font-weight-bold">Sottodomanda</th>\r\n        <th class="small font-weight-bold" colspan="2">Punteggio</th>\r\n      </tr>\r\n    </thead>\r\n\r\n    <tbody>\r\n  {{$chp:=""}}\r\n  {{$dprec:=""}}\r\n  {{$idparent:=""}}\r\n {{range $i, $qst :=   .Qsts_1 }}\r\n\t{{ if ne $chp  $qst.C_desc }} {{$chp = $qst.C_desc}} <tr><td class="text-center chphdr" colspan="5">{{$qst.C_desc}}</td></tr>{{end}}\r\n\t<tr>\r\n        <td class="small {{ if eq $dprec $qst.Domanda }} font-weight-light font-italic{{end}}">{{ $qst.Domanda }}</td>\r\n        <td class="small">{{ $qst.SottoDomanda }}</td>\r\n        <td class="small text-center text-monospace" >\r\n\t\t\t<label>\r\n\t\t\t<input {{ if eq $dprec $qst.Domanda }} disabled data-idparent="{{ $idparent }}" {{else}} {{$idparent = $i}} data-id="{{$i}}"{{end}} onclick="updTot(this);" type="radio" name="q{{$i}}" value="{{ $qst.Punti_si }}" /> SI {{$qst.Punti_si|printf "%03d"}}\r\n\t\t\t</label>\r\n\t\t</td>\r\n        <td class="small text-center text-monospace">\r\n\t\t\t<label>\r\n\t\t\t<input {{ if eq $dprec $qst.Domanda }} disabled data-idparent="{{ $idparent }}" {{else}} data-id="{{$i}}" {{end}} onclick="updTot(this);"type="radio" name="q{{$i}}" value="{{ $qst.Punti_no }}" /> NO {{$qst.Punti_no|printf "%03d"}}\r\n\t\t\t</label>\r\n\t\t</td>\r\n    </tr>\r\n\t{{$dprec = $qst.Domanda}} \t\r\n {{end}}\r\n\t</tbody>\r\n\t</table>\r\n\r\n	9	View basata su HTML templates
10	 \r\nE' possibile salvare le checklist compilate esportandole su file. Il formato JSON garantisce interoperabilità verso eventuali applicazioni di terze parti. Tutto il processo viene gestito client side in javascript.     \r\n	\r\nfunction exportjson() {\r\n\r\n\tvar j=JSON.stringify(jsonContent, null, 2);\r\n\tvar ar=[];\r\n\tar.push(j);\r\n\tvar blob = new Blob(ar, {type: "text/plain;charset=utf-8"});\r\n\r\n\tif (confirm(ar)) {\r\n\t\tsaveAs(blob, "json.txt");\r\n\t}\r\n}\r\n	10	Export checklist
\.


--
-- Data for Name: config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.config (host, dbname, "user", password, port) FROM stdin;
\.


--
-- Data for Name: log_access; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.log_access (id, id_utente, entered, id_asl, ip) FROM stdin;
27	10000006	2021-05-06 16:09:29.443676	\N	\N
61	10000740	2021-05-07 13:05:54.323337	203	\N
1	1	2021-05-06 09:03:26.101453	-1	\N
131	-5	2021-05-11 15:02:20.802776	-1	\N
132	-6	2021-05-11 15:08:30.793394	-1	\N
133	-7	2021-05-11 15:14:46.841275	-1	\N
134	-11	2021-05-11 15:32:03.694559	-1	93.42.222.106
135	-12	2021-05-11 15:34:48.004737	-1	93.42.222.106
136	-13	2021-05-11 15:35:48.930258	-1	93.42.222.106
137	-14	2021-05-11 15:37:38.518745	-1	93.42.222.106
138	-15	2021-05-11 15:38:13.742494	-1	93.42.222.106
139	-16	2021-05-11 15:39:56.197041	-1	93.42.222.106
140	-17	2021-05-11 15:40:51.148835	-1	79.8.233.73
141	-18	2021-05-11 15:59:16.559946	-1	93.42.222.106
142	10008600	2021-05-11 16:00:19.728737	206	93.42.222.106
143	1	2021-05-11 16:00:30.829783	-1	93.42.222.106
144	-19	2021-05-11 16:04:28.875382	-1	93.42.222.106
145	10000740	2021-05-11 16:05:07.369126	203	93.42.222.106
146	10000740	2021-05-11 16:32:17.589753	203	93.42.222.106
147	10000740	2021-05-11 16:34:11.199529	203	79.8.233.73
148	-20	2021-05-11 16:35:00.405501	-1	79.8.233.73
149	-21	2021-05-11 16:35:29.06594	-1	93.42.222.106
150	-22	2021-05-11 16:35:54.443526	-1	93.42.222.106
151	10000740	2021-05-11 16:36:07.619032	203	93.42.222.106
152	-23	2021-05-11 16:37:28.6132	-1	93.42.222.106
153	10000740	2021-05-11 16:37:34.284416	203	93.42.222.106
154	10000740	2021-05-11 16:39:45.774828	203	93.42.222.106
155	-24	2021-05-11 16:40:07.049383	-1	93.42.222.106
156	10000740	2021-05-11 16:40:42.715803	203	93.42.222.106
62	10000784	2021-05-07 13:07:11.291214	207	\N
63	10008600	2021-05-07 13:20:06.485277	206	\N
157	10000740	2021-05-11 16:42:41.007495	203	79.8.233.73
158	10000740	2021-05-11 16:43:17.837448	203	79.8.233.73
159	10000740	2021-05-11 16:48:06.74998	203	79.8.233.73
160	10000740	2021-05-11 16:48:33.287354	203	79.8.233.73
161	10000740	2021-05-11 16:49:27.894486	203	79.8.233.73
162	10000740	2021-05-11 16:51:55.042578	203	93.42.222.106
163	-25	2021-05-11 16:51:58.200853	-1	93.42.222.106
164	-26	2021-05-11 16:52:40.373643	-1	93.42.222.106
165	-27	2021-05-11 16:52:44.800693	-1	93.42.222.106
166	-28	2021-05-11 16:53:10.805779	-1	93.42.222.106
167	-29	2021-05-11 16:55:59.384911	-1	93.42.222.106
168	10000740	2021-05-11 16:56:08.459792	203	93.42.222.106
169	-30	2021-05-11 16:56:11.409286	-1	93.42.222.106
170	-31	2021-05-11 16:57:22.207727	-1	79.8.233.73
171	-32	2021-05-11 16:58:49.110446	-1	93.42.222.106
172	-33	2021-05-11 16:58:54.454205	-1	93.42.222.106
173	10000740	2021-05-11 16:59:02.233431	203	93.42.222.106
174	-34	2021-05-11 17:07:10.126381	-1	79.8.233.73
175	10000740	2021-05-11 17:07:17.429741	203	79.8.233.73
176	10000784	2021-05-11 17:09:12.465159	207	79.8.233.73
177	10000740	2021-05-11 17:12:35.153177	203	93.42.222.106
178	1	2021-05-11 17:12:57.965342	-1	93.42.222.106
179	-35	2021-05-11 17:13:10.51359	-1	93.42.222.106
180	-36	2021-05-11 17:18:15.03676	-1	93.42.222.106
181	1	2021-05-11 17:23:12.931827	-1	93.42.222.106
182	-37	2021-05-13 12:47:44.137654	-1	93.42.222.106
183	-38	2021-05-13 12:52:24.470518	-1	93.42.222.106
184	-39	2021-05-13 12:54:31.723987	-1	93.42.222.106
185	-40	2021-05-13 12:54:36.79447	-1	93.42.222.106
186	10000740	2021-05-13 12:54:54.021317	203	93.42.222.106
187	1	2021-05-14 12:23:51.792279	-1	2.232.192.227
188	-41	2021-05-14 12:24:15.870535	-1	2.232.192.227
189	-42	2021-05-14 12:24:19.655718	-1	2.232.192.227
190	-43	2021-05-14 12:49:34.29698	-1	93.42.222.106
191	1	2021-05-14 12:50:46.731162	-1	93.42.222.106
192	1	2021-05-14 13:01:14.223663	-1	93.42.222.106
193	1	2021-05-14 13:03:09.005216	-1	93.42.222.106
194	-44	2021-05-14 13:03:17.583631	-1	93.42.222.106
195	10010280	2021-05-14 13:06:16.903103	206	93.42.222.106
196	-45	2021-05-20 13:04:34.432491	-1	93.42.222.106
197	-46	2021-06-11 11:11:38.300419	-1	93.42.222.106
198	-47	2021-06-11 11:12:32.182076	-1	93.42.222.106
199	1	2021-06-14 17:10:50.162134	-1	93.42.222.106
200	-48	2021-06-14 17:11:08.879362	-1	93.42.222.106
201	1	2021-06-14 17:12:12.156842	-1	93.42.222.106
202	-49	2021-06-14 17:22:32.944256	-1	93.42.222.106
203	-50	2021-06-14 17:25:28.263157	-1	93.42.222.106
204	1	2021-06-14 17:26:37.163604	-1	93.42.222.106
205	-51	2021-06-14 17:26:51.683949	-1	93.42.222.106
206	1	2021-06-14 17:30:52.009337	-1	93.42.222.106
207	-52	2021-06-15 08:24:31.167449	-1	2.232.192.227
208	10010280	2021-06-15 08:30:38.260155	206	2.232.192.227
209	10010280	2021-06-15 09:01:50.666775	206	93.42.222.106
210	-53	2021-06-15 09:03:18.276543	-1	93.42.222.106
211	-54	2021-06-15 09:07:43.539834	-1	93.42.222.106
212	-55	2021-06-15 09:07:49.642484	-1	93.42.222.106
213	-56	2021-06-15 09:08:24.268635	-1	93.42.222.106
214	-57	2021-06-15 09:08:38.349316	-1	93.42.222.106
215	-58	2021-06-15 09:09:54.730962	-1	93.42.222.106
216	-59	2021-06-15 09:10:04.12075	-1	93.42.222.106
217	-60	2021-06-15 09:12:25.553609	-1	93.42.222.106
218	-61	2021-06-15 09:12:40.143225	-1	93.42.222.106
219	-62	2021-06-15 09:12:48.11851	-1	93.42.222.106
220	-63	2021-06-15 09:13:06.720835	-1	93.42.222.106
221	-64	2021-06-15 09:13:49.448692	-1	93.42.222.106
222	-65	2021-06-15 09:13:56.566093	-1	93.42.222.106
223	-66	2021-06-15 09:14:29.905739	-1	93.42.222.106
224	-67	2021-06-15 09:15:36.340659	-1	93.42.222.106
225	-68	2021-06-15 09:15:42.258318	-1	93.42.222.106
226	-69	2021-06-15 09:17:41.788432	-1	93.42.222.106
227	-70	2021-06-15 09:17:42.846897	-1	93.42.222.106
228	-71	2021-06-15 09:18:28.427803	-1	93.42.222.106
229	-72	2021-06-15 09:19:30.507969	-1	93.42.222.106
230	-73	2021-06-15 09:19:50.844239	-1	93.42.222.106
231	-74	2021-06-15 09:19:57.807759	-1	93.42.222.106
232	-75	2021-06-15 09:21:34.959045	-1	93.42.222.106
233	-76	2021-06-15 09:21:43.16588	-1	93.42.222.106
234	-77	2021-06-15 09:21:48.502602	-1	93.42.222.106
235	-78	2021-06-15 09:22:33.850293	-1	93.42.222.106
236	-79	2021-06-15 09:22:47.676733	-1	93.42.222.106
237	-80	2021-06-15 09:23:03.029374	-1	93.42.222.106
238	10010280	2021-06-15 09:25:55.452684	206	93.42.222.106
240	10010280	2021-06-15 09:36:51.505764	206	93.42.222.106
241	-82	2021-06-15 09:39:27.052037	-1	93.42.222.106
242	10010280	2021-06-15 09:39:53.104605	206	93.42.222.106
239	-81	2021-06-15 09:34:31.781648	-1	151.73.210.134
243	10010280	2021-06-15 09:40:20.552874	206	93.42.222.106
244	10010280	2021-06-15 09:42:45.279678	206	93.42.222.106
245	10010285	2021-06-15 09:44:03.808999	207	93.42.222.106
246	10010286	2021-06-15 10:03:26.658169	201	93.42.222.106
247	-83	2021-06-15 10:03:31.377791	-1	93.42.222.106
248	10010280	2021-06-15 10:18:08.200286	206	93.42.222.106
249	-84	2021-06-15 10:21:21.825511	-1	93.42.222.106
250	-85	2021-06-15 10:22:06.216948	-1	93.42.222.106
251	10010280	2021-06-15 10:33:26.829792	206	93.42.222.106
252	10010286	2021-06-15 10:34:00.272615	201	93.42.222.106
253	10010280	2021-06-15 10:37:05.477246	206	93.42.222.106
254	10010280	2021-06-15 10:38:59.848697	206	93.42.222.106
255	10010280	2021-06-15 10:40:03.088795	206	93.42.222.106
256	10010280	2021-06-15 10:44:44.525367	206	93.42.222.106
257	10010286	2021-06-15 10:52:09.365243	201	93.42.222.106
258	10010286	2021-06-15 11:45:16.480264	201	93.42.222.106
259	10010286	2021-06-15 11:50:03.384746	201	93.42.222.106
260	10010286	2021-06-15 12:04:47.740277	201	151.73.210.134
261	10010286	2021-06-15 12:05:05.056421	201	151.73.210.134
262	-86	2021-06-15 14:26:27.876856	-1	93.42.222.106
263	-87	2021-06-15 14:26:34.149356	-1	93.42.222.106
264	10010286	2021-06-15 14:26:39.736908	201	93.42.222.106
265	10010286	2021-06-16 09:01:05.374087	201	93.42.222.106
266	1	2021-06-16 09:01:12.805617	-1	93.42.222.106
267	10010280	2021-06-16 09:02:16.180625	206	93.42.222.106
268	10010280	2021-06-16 09:05:48.560529	206	93.42.222.106
269	10010280	2021-06-16 09:06:03.146871	206	93.42.222.106
270	10010280	2021-06-16 09:06:29.268564	206	93.42.222.106
271	10010280	2021-06-16 09:06:56.938252	206	93.42.222.106
272	10010280	2021-06-16 09:07:16.120481	206	93.42.222.106
273	10010280	2021-06-16 09:09:40.540531	206	93.42.222.106
274	-88	2021-06-16 09:12:03.029002	-1	93.42.222.106
275	-89	2021-06-16 09:12:17.460452	-1	93.42.222.106
276	-90	2021-06-16 09:12:19.066459	-1	93.42.222.106
277	-91	2021-06-16 09:13:38.777726	-1	93.42.222.106
278	-92	2021-06-16 09:14:01.050789	-1	93.42.222.106
279	-93	2021-06-16 09:14:13.215768	-1	93.42.222.106
280	-94	2021-06-16 09:14:19.066213	-1	93.42.222.106
281	-95	2021-06-16 09:14:29.332491	-1	93.42.222.106
282	-96	2021-06-16 09:14:39.657688	-1	93.42.222.106
283	-97	2021-06-16 09:15:03.426385	-1	93.42.222.106
284	-98	2021-06-16 09:15:21.671621	-1	93.42.222.106
285	-99	2021-06-16 09:21:02.913397	-1	93.42.222.106
286	-100	2021-06-16 09:23:08.645662	-1	93.42.222.106
287	-101	2021-06-16 09:33:22.4168	-1	93.42.222.106
288	-102	2021-06-16 09:33:35.721899	-1	93.42.222.106
289	10010286	2021-06-16 09:34:21.264633	201	93.42.222.106
290	10010286	2021-06-16 09:35:56.552809	201	93.42.222.106
291	10010286	2021-06-16 09:36:03.356789	201	93.42.222.106
292	10010280	2021-06-16 15:29:19.229851	206	93.42.222.106
293	10010285	2021-06-16 15:45:28.510789	207	93.42.222.106
294	10010279	2021-06-16 15:45:51.880354	202	93.42.222.106
295	10010279	2021-06-16 15:53:08.769346	202	93.42.222.106
296	10010285	2021-06-16 15:53:17.08225	207	93.42.222.106
297	10010286	2021-06-16 17:24:13.339089	201	93.42.222.106
298	10010286	2021-06-16 17:24:38.988124	201	93.42.222.106
299	10010285	2021-06-17 08:57:16.131984	207	93.42.222.106
300	10010285	2021-06-17 08:57:46.648117	207	93.42.222.106
301	10010285	2021-06-17 09:14:53.160113	207	93.42.222.106
302	10010279	2021-06-17 09:15:01.297752	202	93.42.222.106
303	10010285	2021-06-17 09:44:55.203113	207	93.42.222.106
304	10010285	2021-06-17 09:55:40.400076	207	93.40.228.221
305	10010285	2021-06-17 09:55:57.349057	207	93.40.228.221
306	-103	2021-06-17 09:56:40.985888	-1	93.40.228.221
307	10010285	2021-06-17 10:19:55.55127	207	93.40.228.221
308	-104	2021-06-17 10:46:22.686134	-1	93.40.228.221
309	-105	2021-06-17 15:18:02.849599	-1	93.42.222.106
310	-106	2021-06-17 15:20:19.268059	-1	93.42.222.106
311	10010286	2021-06-17 15:22:16.020266	201	93.42.222.106
312	-107	2021-06-17 15:43:20.109181	-1	79.8.233.73
313	10010285	2021-06-17 15:51:41.569015	207	93.42.222.106
314	-108	2021-06-18 17:08:12.252419	-1	79.18.159.211
315	-109	2021-06-21 09:02:08.13824	-1	93.42.222.106
316	10010286	2021-06-21 09:02:58.726953	201	93.42.222.106
317	-110	2021-06-21 09:04:34.247029	-1	93.42.222.106
318	-111	2021-06-21 11:39:45.982651	-1	79.8.233.73
319	-112	2021-06-21 11:50:14.084081	-1	93.42.222.106
320	-113	2021-06-24 12:29:07.993567	-1	79.18.159.211
321	-114	2021-06-25 15:49:30.036613	-1	93.42.222.106
322	10010286	2021-06-25 15:49:51.716325	201	93.42.222.106
323	10010286	2021-07-28 12:36:07.892781	201	93.42.222.106
324	10010286	2021-07-28 12:46:58.59391	201	93.42.222.106
325	-115	2021-07-28 12:47:01.994469	-1	93.42.222.106
326	10010286	2021-07-28 13:55:02.127063	201	93.42.222.106
327	10010286	2021-07-28 14:49:19.872319	201	93.42.222.106
328	1	2021-07-28 14:49:34.741732	-1	93.42.222.106
329	-116	2021-07-28 15:19:14.433505	-1	93.42.222.106
330	10010286	2021-07-28 15:19:32.981016	201	93.42.222.106
331	-117	2021-07-28 16:23:12.414665	-1	93.42.222.106
332	10010286	2021-07-28 16:23:18.712098	201	93.42.222.106
333	10010286	2021-07-28 16:25:38.834287	201	93.42.222.106
334	10010286	2021-07-28 16:27:42.879975	201	93.42.222.106
335	10010286	2021-07-28 16:33:57.806445	201	93.42.222.106
336	10010286	2021-07-28 16:36:11.993353	201	93.42.222.106
337	10010286	2021-07-28 16:46:01.145617	201	93.42.222.106
338	10010286	2021-07-28 16:52:37.035959	201	93.42.222.106
339	10010286	2021-07-28 16:54:09.262624	201	93.42.222.106
340	10010286	2021-07-28 17:07:51.691277	201	93.42.222.106
341	10010286	2021-07-28 17:11:00.655488	201	93.42.222.106
342	10010286	2021-07-28 17:11:37.406016	201	93.42.222.106
343	10010286	2021-07-28 17:15:47.302771	201	93.42.222.106
344	10010286	2021-07-29 09:39:51.786641	201	93.42.222.106
345	10010286	2021-07-29 10:09:52.185618	201	93.42.222.106
346	10010286	2021-07-29 10:28:58.883137	201	93.42.222.106
347	10010286	2021-07-29 10:32:36.238454	201	93.42.222.106
348	10010286	2021-07-29 10:33:23.171846	201	93.42.222.106
349	10010286	2021-07-29 10:36:16.974897	201	93.42.222.106
350	10010286	2021-07-29 10:37:00.398748	201	93.42.222.106
351	10010286	2021-07-29 10:37:35.220565	201	93.42.222.106
352	10010286	2021-07-29 10:39:13.967835	201	93.42.222.106
353	10010286	2021-07-29 10:39:43.479179	201	93.42.222.106
354	10010286	2021-07-29 10:42:14.737367	201	93.42.222.106
355	10010286	2021-07-29 10:48:52.739377	201	93.42.222.106
356	10010286	2021-07-29 10:49:52.068843	201	93.42.222.106
357	10010286	2021-07-29 10:52:12.82863	201	93.42.222.106
358	10010286	2021-07-29 10:52:45.829175	201	93.42.222.106
359	10010286	2021-07-29 10:55:30.667008	201	93.42.222.106
360	-118	2021-07-29 10:59:14.967485	-1	93.42.222.106
361	-119	2021-07-29 11:07:21.510098	-1	79.8.233.73
362	10010286	2021-07-29 11:10:07.16998	201	93.42.222.106
363	10010286	2021-07-29 11:11:17.545031	201	93.42.222.106
364	10010286	2021-07-29 11:53:46.129034	201	79.8.233.73
365	10010286	2021-07-29 13:15:43.946543	201	93.42.222.106
366	10010286	2021-07-30 09:02:16.278099	201	93.42.222.106
367	10010286	2021-07-30 09:04:19.403152	201	93.42.222.106
368	10010286	2021-07-30 09:04:31.713306	201	93.42.222.106
369	10010286	2021-07-30 09:04:47.606699	201	93.42.222.106
370	10010286	2021-07-30 09:05:12.426618	201	93.42.222.106
371	10010286	2021-07-30 09:05:34.274782	201	93.42.222.106
372	10010286	2021-07-30 09:06:21.586132	201	93.42.222.106
373	-120	2021-07-30 09:40:16.059708	-1	93.42.222.106
374	10010286	2021-09-01 14:20:07.669532	201	93.42.222.106
375	10010286	2021-09-01 14:23:49.520321	201	93.42.222.106
376	10010286	2021-09-01 14:24:37.934978	201	93.42.222.106
377	-121	2021-09-01 14:25:34.475565	-1	93.42.222.106
378	10010286	2021-09-01 14:25:39.926496	201	93.42.222.106
379	-122	2021-09-01 14:41:16.381193	-1	93.42.222.106
380	-123	2021-09-15 09:45:19.719096	-1	93.42.222.106
381	-124	2021-09-15 10:15:08.118949	-1	93.42.222.106
382	10010286	2021-09-15 10:16:02.790246	201	93.42.222.106
383	-125	2021-09-15 10:20:48.81727	-1	93.42.222.106
384	-126	2021-09-15 10:21:27.664355	-1	93.42.222.106
385	-127	2021-09-15 11:11:13.587462	-1	93.42.222.106
386	-128	2021-09-15 14:01:42.251515	-1	93.42.222.106
387	10010298	2021-09-15 14:36:02.695815	-1	93.42.222.106
388	10010298	2021-09-15 14:36:14.119495	-1	93.42.222.106
389	10010298	2021-09-15 14:39:26.867253	-1	93.42.222.106
390	10010298	2021-09-15 14:41:19.191417	201	93.42.222.106
391	10010298	2021-09-15 14:41:59.846881	201	93.42.222.106
392	10010298	2021-09-15 15:14:34.620804	201	93.42.222.106
393	-129	2021-09-15 15:26:45.208044	-1	93.42.222.106
394	-130	2021-09-15 15:33:29.299284	-1	93.42.222.106
395	10010298	2021-09-15 16:32:23.009813	201	93.42.222.106
396	10010298	2021-09-15 16:33:31.8413	201	93.42.222.106
397	10010298	2021-09-15 16:34:14.923211	201	93.42.222.106
398	-131	2021-09-15 16:38:27.888277	-1	93.42.222.106
399	-132	2021-09-15 16:41:41.79606	-1	93.42.222.106
400	-133	2021-09-15 16:43:44.79604	-1	93.42.222.106
401	10010298	2021-09-15 16:43:50.658407	201	93.42.222.106
402	-134	2021-09-15 16:43:53.951315	-1	93.42.222.106
403	-135	2021-09-15 16:51:56.343456	-1	93.42.222.106
404	10010298	2021-09-15 16:52:02.399129	201	93.42.222.106
405	10010298	2021-09-15 16:52:56.846075	201	93.42.222.106
406	10010298	2021-09-15 16:54:55.151706	201	93.42.222.106
407	10010298	2021-09-15 17:21:23.294672	201	93.42.222.106
408	-136	2021-09-15 17:21:26.795345	-1	93.42.222.106
409	10010298	2021-09-16 08:15:05.173388	201	2.232.192.227
410	10010298	2021-09-16 08:16:08.541176	201	2.232.192.227
411	10010298	2021-09-16 08:16:16.493525	201	2.232.192.227
412	-137	2021-09-16 08:16:20.805062	-1	2.232.192.227
413	10010298	2021-09-16 14:10:38.752788	201	93.42.222.106
414	-138	2021-09-16 14:11:02.573277	-1	93.42.222.106
415	-139	2021-09-17 00:23:21.011611	-1	159.65.88.214
416	10010298	2021-09-17 08:36:40.626153	201	93.42.222.106
417	10010298	2021-09-19 18:38:18.512689	201	79.8.233.73
418	10010298	2021-09-19 18:39:55.275108	201	93.42.222.106
419	10010298	2021-09-20 13:00:59.341846	201	93.42.222.106
420	10010298	2021-09-21 08:40:00.315698	201	2.232.192.227
421	10010298	2021-09-22 11:03:52.440552	201	93.42.222.106
422	-140	2021-09-22 15:36:54.292485	-1	93.42.222.106
423	-141	2021-09-22 15:37:04.984859	-1	93.42.222.106
424	-142	2021-09-22 15:42:30.472329	-1	93.42.222.106
425	-143	2021-09-22 15:42:42.788426	-1	93.42.222.106
426	-144	2021-09-22 15:43:40.347805	-1	93.42.222.106
427	10010298	2021-09-22 15:43:46.640005	201	93.42.222.106
428	10010300	2021-09-22 15:43:58.039073	204	93.42.222.106
429	10010302	2021-09-22 15:44:15.160568	206	93.42.222.106
430	10010301	2021-09-22 15:45:01.50604	205	93.42.222.106
431	10010299	2021-09-22 15:45:10.865367	202	93.42.222.106
432	10010302	2021-09-22 15:45:25.480877	206	93.42.222.106
433	-145	2021-09-22 15:46:14.308206	-1	93.42.222.106
434	10010299	2021-09-22 15:51:08.940921	202	93.42.222.106
435	10010302	2021-09-22 15:51:17.088842	206	93.42.222.106
436	10010298	2021-09-22 15:54:30.258194	201	93.42.222.106
437	10010301	2021-09-22 15:54:45.665174	205	93.42.222.106
438	10010302	2021-09-22 15:57:59.604087	206	93.42.222.106
439	10010300	2021-09-22 16:01:20.023826	204	93.42.222.106
440	-146	2021-09-22 16:04:30.027872	-1	93.42.222.106
441	10010298	2021-09-22 16:07:33.064775	201	93.42.222.106
442	10010300	2021-09-22 16:07:55.39336	204	93.42.222.106
443	10010298	2021-09-22 16:10:17.80506	201	93.42.222.106
444	10010300	2021-09-22 16:14:15.678175	204	93.42.222.106
445	10010298	2021-09-22 16:15:53.965224	201	93.42.222.106
446	10010300	2021-09-22 16:19:38.346376	204	93.42.222.106
447	10010300	2021-09-22 16:28:52.601275	204	93.42.222.106
448	-147	2021-09-22 16:29:08.62324	-1	93.42.222.106
449	10010298	2021-09-22 16:30:13.202168	201	93.42.222.106
450	10010298	2021-09-22 16:30:28.504098	201	93.42.222.106
451	-148	2021-09-22 16:35:43.890587	-1	93.42.222.106
452	10010300	2021-09-22 16:38:06.237322	204	93.42.222.106
453	-149	2021-09-22 16:39:36.949424	-1	93.42.222.106
454	-150	2021-09-22 16:42:28.498051	-1	93.42.222.106
455	-151	2021-09-22 16:42:40.413729	-1	93.42.222.106
456	-152	2021-09-22 16:43:03.10234	-1	93.42.222.106
457	-153	2021-09-22 16:43:32.870489	-1	93.42.222.106
458	-154	2021-09-22 16:43:44.544824	-1	93.42.222.106
459	-155	2021-09-22 16:45:15.9668	-1	93.42.222.106
460	-156	2021-09-22 16:46:11.578201	-1	93.42.222.106
462	10010300	2021-09-22 16:46:39.050864	204	93.42.222.106
464	10010302	2021-09-22 17:04:38.194037	206	93.42.222.106
465	10010301	2021-09-22 17:06:25.147678	205	93.42.222.106
466	10010300	2021-09-22 17:10:21.39009	204	93.42.222.106
467	10010301	2021-09-22 17:12:01.740533	205	93.42.222.106
468	10010302	2021-09-22 17:20:29.515767	206	93.42.222.106
469	10010299	2021-09-22 17:22:31.236414	202	93.42.222.106
471	10010298	2021-09-22 17:46:36.990479	201	93.42.222.106
473	10010299	2021-09-22 17:53:48.37957	202	93.42.222.106
476	10010302	2021-09-22 17:59:25.652285	206	93.42.222.106
477	10010301	2021-09-22 18:00:07.866015	205	93.42.222.106
478	-159	2021-09-22 18:10:54.124907	-1	93.42.222.106
479	-160	2021-09-22 18:11:20.81016	-1	93.42.222.106
461	-157	2021-09-22 16:46:19.50151	-1	93.42.222.106
463	-158	2021-09-22 16:46:54.529433	-1	93.42.222.106
470	10010301	2021-09-22 17:46:20.723884	205	93.42.222.106
472	10010302	2021-09-22 17:51:27.518098	206	93.42.222.106
474	10010301	2021-09-22 17:55:28.29246	205	93.42.222.106
475	10010302	2021-09-22 17:57:28.816194	206	93.42.222.106
480	-161	2021-09-23 08:18:44.162514	-1	79.8.233.73
481	-162	2021-09-23 08:18:48.339148	-1	79.8.233.73
482	-163	2021-09-23 08:19:01.741347	-1	93.42.222.106
483	10010299	2021-09-23 08:34:05.55745	202	151.70.214.213
484	-164	2021-09-23 08:36:37.097051	-1	93.42.222.106
485	-165	2021-09-23 08:36:42.084681	-1	93.42.222.106
486	10010301	2021-09-23 08:41:21.486355	205	151.70.214.213
487	-166	2021-09-23 08:42:56.426976	-1	79.8.233.73
488	-167	2021-09-23 08:43:18.831049	-1	93.42.222.106
489	-168	2021-09-23 08:43:19.05696	-1	79.8.233.73
490	-169	2021-09-23 08:43:19.268856	-1	93.42.222.106
491	-170	2021-09-23 08:43:19.413263	-1	93.42.222.106
492	-171	2021-09-23 08:43:19.579115	-1	93.42.222.106
493	-172	2021-09-23 08:43:36.821661	-1	93.42.222.106
494	-173	2021-09-23 08:49:15.445547	-1	79.8.233.73
495	10010301	2021-09-23 09:05:36.541775	205	93.42.222.106
496	10010301	2021-09-23 09:09:07.381149	205	93.42.222.106
497	10010301	2021-09-23 09:09:07.686333	205	93.42.222.106
498	10010301	2021-09-23 09:09:07.887137	205	93.42.222.106
499	10010301	2021-09-23 09:09:08.019525	205	79.8.233.73
500	10010301	2021-09-23 09:09:08.211804	205	93.42.222.106
501	10010301	2021-09-23 09:09:08.301846	205	93.42.222.106
502	10010301	2021-09-23 09:09:08.438429	205	93.42.222.106
503	10010301	2021-09-23 09:09:08.601558	205	93.42.222.106
504	10010301	2021-09-23 09:09:08.778656	205	79.8.233.73
505	10010301	2021-09-23 09:09:08.899495	205	93.42.222.106
506	10010301	2021-09-23 09:09:09.024804	205	93.42.222.106
507	10010301	2021-09-23 09:09:09.180093	205	93.42.222.106
508	10010301	2021-09-23 09:09:09.288528	205	93.42.222.106
509	10010301	2021-09-23 09:09:09.449465	205	79.8.233.73
510	10010301	2021-09-23 09:09:09.625642	205	93.42.222.106
511	10010301	2021-09-23 09:09:09.755974	205	93.42.222.106
512	10010301	2021-09-23 09:09:09.97173	205	93.42.222.106
513	10010301	2021-09-23 09:09:10.089242	205	93.42.222.106
514	10010301	2021-09-23 09:09:10.281203	205	79.8.233.73
515	10010301	2021-09-23 09:09:10.403435	205	93.42.222.106
516	10010298	2021-09-23 09:13:06.680756	201	93.42.222.106
517	10010298	2021-09-23 09:24:51.889606	201	93.42.222.106
518	10010298	2021-09-23 09:27:18.108299	201	93.42.222.106
519	10010299	2021-09-23 09:28:21.472433	202	93.42.222.106
520	10010302	2021-09-23 09:29:52.351839	206	79.8.233.73
521	-174	2021-09-23 09:30:05.242865	-1	93.42.222.106
522	-175	2021-09-23 09:30:23.92412	-1	93.42.222.106
523	-176	2021-09-23 09:31:42.267807	-1	93.42.222.106
524	-177	2021-09-23 09:32:41.873309	-1	79.8.233.73
525	-178	2021-09-23 09:33:35.546977	-1	93.42.222.106
526	10010300	2021-09-23 09:48:56.595364	204	93.42.222.106
527	-179	2021-09-23 10:14:09.847349	-1	79.8.233.73
528	-180	2021-09-23 10:14:34.43133	-1	93.40.225.109
529	10010301	2021-09-23 10:22:39.10273	205	93.42.222.106
530	-181	2021-09-23 10:31:23.687267	-1	93.40.225.109
531	10010303	2021-09-23 10:36:46.764652	207	93.42.222.106
532	10010301	2021-09-23 10:54:47.154774	205	93.42.222.106
533	10010303	2021-09-23 10:58:27.819493	207	93.42.222.106
534	10010300	2021-09-23 10:59:14.136363	204	93.42.222.106
535	-182	2021-09-23 11:17:33.148557	-1	93.42.222.106
536	-183	2021-09-23 11:17:52.403904	-1	93.42.222.106
537	-184	2021-09-23 11:27:20.655109	-1	93.42.222.106
538	10010303	2021-09-23 11:46:27.781761	207	93.42.222.106
539	-185	2021-09-27 09:22:48.356429	-1	93.42.222.106
540	-186	2021-09-27 09:22:58.647062	-1	93.42.222.106
541	-187	2021-09-27 11:20:50.435864	-1	93.42.222.106
542	-188	2021-09-27 11:22:23.285422	-1	93.42.222.106
543	-189	2021-09-28 04:22:57.606569	-1	2.232.192.227
544	-190	2021-09-28 14:22:03.28284	-1	2.232.192.227
545	10010304	2021-09-28 14:32:41.174036	206	2.232.192.227
546	10010304	2021-09-28 14:33:09.354331	206	2.232.192.227
547	10010304	2021-09-28 14:33:18.60527	206	2.232.192.227
548	10010305	2021-09-28 14:33:53.977989	205	2.232.192.227
549	10010304	2021-09-28 14:34:53.104966	206	2.232.192.227
550	10010306	2021-09-28 14:36:06.851746	201	2.232.192.227
551	10010306	2021-09-28 14:38:37.569043	201	2.232.192.227
552	10010304	2021-09-28 14:39:18.341094	206	2.232.192.227
553	10010307	2021-09-28 14:43:08.824012	205	2.232.192.227
554	10010307	2021-09-28 14:44:13.789131	205	2.232.192.227
555	10010306	2021-09-28 14:47:04.974961	201	151.70.214.213
556	10010307	2021-09-28 14:47:10.6091	205	93.42.222.106
557	10010307	2021-09-28 14:47:38.395193	205	93.42.222.106
558	10010307	2021-09-28 14:47:40.530591	205	93.42.222.106
559	10010307	2021-09-28 14:48:21.981211	205	93.42.222.106
560	10010306	2021-09-28 14:49:32.858832	201	151.70.214.213
561	10010304	2021-09-28 14:51:14.426966	206	93.42.222.106
562	10010306	2021-09-28 15:03:03.853037	201	93.42.222.106
563	10010307	2021-09-28 15:05:28.775063	205	93.42.222.106
564	10010305	2021-09-28 15:05:51.246411	205	93.42.222.106
565	10010305	2021-09-28 15:06:42.857248	205	93.42.222.106
566	10010304	2021-09-28 15:06:50.755447	206	93.42.222.106
567	10010304	2021-09-28 15:35:15.203375	206	2.232.192.227
568	-191	2021-09-29 11:36:06.156063	-1	93.42.222.106
569	10010306	2021-09-29 11:37:55.517322	201	93.42.222.106
570	10010306	2021-09-29 11:43:35.439111	201	93.42.222.106
571	10010308	2021-09-29 11:46:36.087489	205	93.42.222.106
572	10010309	2021-09-29 12:05:43.036132	204	93.42.222.106
573	10010309	2021-09-29 14:12:18.019978	204	93.42.222.106
574	10010306	2021-09-29 14:12:42.216544	201	93.42.222.106
575	10010309	2021-09-29 14:13:14.540811	204	93.42.222.106
576	10010309	2021-09-29 15:07:28.895441	204	93.42.222.106
577	10010306	2021-09-29 15:54:01.05684	201	93.42.222.106
578	10010306	2021-09-29 15:55:06.883476	201	93.42.222.106
579	10010310	2021-09-29 15:57:16.837715	204	93.42.222.106
580	-192	2021-09-29 15:58:08.477237	-1	93.42.222.106
581	10010309	2021-09-29 15:59:20.548161	204	93.42.222.106
582	-193	2021-09-29 16:06:17.638202	-1	93.42.222.106
583	10010309	2021-09-29 16:06:36.48015	204	93.42.222.106
584	10010298	2021-09-29 16:13:25.527736	201	93.42.222.106
585	10010309	2021-09-29 17:34:29.887968	204	93.42.222.106
586	10010308	2021-09-30 11:25:23.893148	205	93.42.222.106
587	-194	2021-09-30 11:44:57.210143	-1	93.42.222.106
588	10010314	2021-09-30 11:45:15.680412	207	151.70.214.213
589	-195	2021-09-30 11:46:45.78824	-1	93.42.222.106
590	10010313	2021-09-30 11:46:47.224793	205	151.70.214.213
591	-196	2021-09-30 11:47:17.444376	-1	93.42.222.106
592	-197	2021-09-30 11:47:47.831678	-1	93.42.222.106
593	10010313	2021-09-30 11:49:47.689022	205	93.42.222.106
594	10010311	2021-09-30 11:53:25.660163	201	93.42.222.106
595	10010313	2021-09-30 11:54:39.704729	205	93.42.222.106
596	10010313	2021-09-30 11:56:59.277113	205	79.8.233.73
597	10010313	2021-09-30 11:59:18.614497	205	93.42.222.106
598	10010313	2021-09-30 12:00:41.137342	205	93.42.222.106
599	10010313	2021-09-30 12:02:52.872666	205	93.42.222.106
600	-198	2021-09-30 12:08:53.491643	-1	93.42.222.106
601	-199	2021-09-30 12:09:25.936169	-1	93.42.222.106
602	10010315	2021-09-30 12:10:40.432326	203	93.42.222.106
603	10010315	2021-09-30 12:11:51.646059	203	93.42.222.106
604	-200	2021-09-30 12:20:24.524972	-1	93.42.222.106
605	-201	2021-09-30 12:20:32.324631	-1	93.42.222.106
606	-202	2021-09-30 12:27:09.29163	-1	93.42.222.106
607	-203	2021-09-30 12:27:29.948232	-1	93.42.222.106
608	-204	2021-09-30 12:39:38.089142	-1	93.42.222.106
609	10010316	2021-09-30 12:59:18.00807	201	93.42.222.106
610	10010316	2021-09-30 13:00:37.899382	201	93.42.222.106
611	-205	2021-09-30 14:06:07.61742	-1	93.42.222.106
612	-206	2021-09-30 14:13:24.84391	-1	93.42.222.106
613	-207	2021-09-30 14:13:33.196232	-1	93.42.222.106
614	10010315	2021-09-30 14:14:30.317973	203	93.42.222.106
615	-208	2021-09-30 14:14:50.166431	-1	93.42.222.106
616	10010315	2021-09-30 14:15:06.236154	203	79.8.233.73
617	-209	2021-09-30 14:35:50.610807	-1	79.8.233.73
618	10010315	2021-09-30 14:43:28.805128	203	93.42.222.106
619	10010316	2021-09-30 14:45:18.293486	201	93.42.222.106
620	10010315	2021-09-30 14:45:39.289738	203	93.42.222.106
621	-210	2021-09-30 15:00:53.665704	-1	93.42.222.106
622	10010316	2021-09-30 15:01:08.852027	201	93.42.222.106
623	10010313	2021-09-30 15:01:35.805489	205	93.42.222.106
624	-211	2021-09-30 15:11:17.484975	-1	151.70.214.213
625	10010317	2021-09-30 15:27:44.580295	206	79.8.233.73
626	10010314	2021-09-30 15:30:27.025445	207	93.42.222.106
627	10010311	2021-09-30 15:31:56.949686	201	79.8.233.73
628	10010314	2021-09-30 15:35:02.432762	207	93.42.222.106
629	10010314	2021-09-30 15:37:11.48523	207	93.42.222.106
630	10010314	2021-09-30 15:38:21.321227	207	93.42.222.106
631	10010318	2021-09-30 15:41:28.776724	206	93.42.222.106
632	10010317	2021-09-30 15:42:28.575424	206	93.42.222.106
633	10010318	2021-09-30 15:43:50.324524	206	93.42.222.106
634	10010318	2021-09-30 15:46:59.512234	206	93.42.222.106
635	10010311	2021-09-30 15:48:17.592495	201	93.42.222.106
636	10010319	2021-09-30 15:51:59.616508	201	93.42.222.106
637	10010318	2021-09-30 15:53:12.819699	206	93.42.222.106
638	10010319	2021-09-30 15:53:33.803746	201	93.42.222.106
639	10010314	2021-09-30 15:55:35.612633	207	93.42.222.106
640	10010314	2021-09-30 15:56:46.777204	207	93.42.222.106
641	10010313	2021-09-30 15:57:49.304409	205	93.42.222.106
642	10010313	2021-09-30 16:00:15.811155	205	93.42.222.106
643	10010313	2021-09-30 16:00:15.996547	205	93.42.222.106
644	10010313	2021-09-30 16:01:11.520836	205	93.42.222.106
645	-212	2021-09-30 16:09:57.803145	-1	93.42.222.106
646	10010320	2021-09-30 17:25:51.918911	206	93.42.222.106
647	10010326	2021-09-30 17:38:45.052673	206	79.8.233.73
648	10010323	2021-09-30 17:38:58.67491	203	93.42.222.106
649	10010321	2021-09-30 17:39:31.706896	204	79.8.233.73
650	10010320	2021-09-30 17:41:05.683397	206	79.8.233.73
651	10010320	2021-09-30 17:49:13.373596	206	93.42.222.106
652	10010326	2021-09-30 17:49:39.315625	206	93.42.222.106
653	10010321	2021-10-01 08:39:47.381984	204	93.42.222.106
654	10010325	2021-10-01 08:40:31.128129	206	93.42.222.106
655	10010320	2021-10-01 08:41:23.467379	206	93.42.222.106
656	-213	2021-10-01 12:09:59.094009	-1	93.42.222.106
657	10010325	2021-10-01 12:16:47.184489	206	93.42.222.106
658	10010320	2021-10-01 12:22:03.612797	206	93.42.222.106
659	10010325	2021-10-01 12:29:09.285756	206	93.42.222.106
660	10010315	2021-10-01 14:44:53.737192	203	93.42.222.106
661	10010315	2021-10-01 14:48:13.602755	203	93.42.222.106
662	10010321	2021-10-01 14:48:22.48068	204	93.42.222.106
663	10010325	2021-10-01 14:48:56.935554	206	93.42.222.106
664	10010318	2021-10-01 14:49:19.433263	206	93.42.222.106
665	10010318	2021-10-01 14:50:22.019255	206	93.42.222.106
666	10010326	2021-10-01 14:50:32.457329	206	93.42.222.106
667	10010314	2021-10-01 14:50:48.249308	207	93.42.222.106
668	10010319	2021-10-04 08:41:33.543554	201	93.42.222.106
669	10010326	2021-10-04 08:41:46.976418	206	93.42.222.106
670	10010326	2021-10-04 08:41:52.967623	206	93.42.222.106
671	10010319	2021-10-04 08:41:58.619634	201	93.42.222.106
672	10010314	2021-10-04 08:42:05.821554	207	93.42.222.106
673	-214	2021-10-04 09:51:12.24672	-1	93.42.222.106
674	10010314	2021-10-04 10:11:29.741335	207	93.42.222.106
675	10010329	2021-10-04 10:56:58.957447	207	93.42.222.106
676	10010325	2021-10-04 10:59:23.146925	206	93.42.222.106
677	10010320	2021-10-04 11:06:09.791133	206	2.45.142.210
678	10010322	2021-10-04 11:19:25.621654	207	93.42.222.106
679	10010314	2021-10-04 11:19:42.293071	207	93.42.222.106
680	10010331	2021-10-04 11:40:16.935977	203	93.42.222.106
681	10010315	2021-10-04 12:18:06.145007	203	93.42.222.106
682	10010331	2021-10-04 12:37:08.69451	203	93.42.222.106
683	10010321	2021-10-04 12:53:06.698213	204	93.42.222.106
684	10010324	2021-10-04 12:53:34.903624	201	93.42.222.106
685	10010321	2021-10-04 12:57:03.059055	204	93.42.222.106
686	10010323	2021-10-04 12:57:37.034716	203	93.42.222.106
687	10010325	2021-10-04 14:39:37.011733	206	109.115.186.2
688	10010325	2021-10-04 14:40:31.815409	206	109.115.186.2
689	10010325	2021-10-04 14:40:55.939303	206	109.115.186.2
690	10010325	2021-10-04 15:11:47.480623	206	109.115.186.2
691	10010325	2021-10-04 15:12:21.686257	206	109.115.186.2
692	10010320	2021-10-04 15:13:53.023239	206	2.45.142.210
693	10010320	2021-10-04 15:14:31.055237	206	2.45.142.210
697	-215	2021-10-04 21:11:25.451651	-1	146.56.129.233
698	10010315	2021-10-05 05:41:07.438624	203	2.232.192.227
694	10010331	2021-10-04 15:15:01.808036	203	93.42.222.106
696	10010321	2021-10-04 15:16:37.494728	204	93.42.222.106
695	10010313	2021-10-04 15:15:35.49168	205	93.42.222.106
699	-216	2021-10-11 10:52:03.229912	-1	93.42.222.106
700	-217	2021-10-11 15:04:45.313285	-1	93.42.222.106
701	-218	2021-10-11 15:05:50.21263	-1	93.42.222.106
702	10010317	2021-10-11 15:06:21.057034	206	93.42.222.106
703	10010317	2021-10-11 15:10:03.72186	206	93.42.222.106
704	10010317	2021-10-11 15:16:48.866264	206	93.42.222.106
705	-219	2021-10-11 19:10:56.824811	-1	2.232.192.227
706	-220	2021-10-12 10:42:07.096553	-1	93.42.222.106
707	-221	2021-10-12 10:42:17.342025	-1	93.42.222.106
708	10010315	2021-10-12 11:04:59.807841	203	79.8.233.73
709	10010315	2021-10-12 11:05:02.75866	203	79.8.233.73
710	10010315	2021-10-12 11:05:24.428059	203	93.42.222.106
711	10010315	2021-10-12 11:17:45.867375	203	93.42.222.106
712	10010315	2021-10-12 11:20:17.096615	203	93.42.222.106
713	10010315	2021-10-12 11:22:29.426549	203	93.42.222.106
714	10010315	2021-10-12 11:34:46.722967	203	93.42.222.106
715	10010315	2021-10-12 11:37:17.711809	203	93.42.222.106
716	-222	2021-10-12 11:38:25.37492	-1	93.42.222.106
717	10010315	2021-10-12 11:39:13.638293	203	93.42.222.106
718	10010315	2021-10-12 11:40:06.102102	203	93.42.222.106
719	10010315	2021-10-12 11:41:32.140973	203	93.42.222.106
720	10010315	2021-10-12 12:01:05.286648	203	93.42.222.106
721	-223	2021-10-12 12:01:15.329435	-1	93.42.222.106
722	-224	2021-10-12 12:16:43.907995	-1	93.42.222.106
723	-225	2021-10-12 12:17:35.02206	-1	93.42.222.106
724	-226	2021-10-12 12:31:29.155829	-1	93.42.222.106
725	-227	2021-10-12 12:32:28.970708	-1	93.42.222.106
726	-228	2021-10-12 14:31:58.492831	-1	93.42.222.106
727	-229	2021-10-12 15:07:52.300517	-1	79.8.233.73
728	-230	2021-10-12 15:08:03.816926	-1	79.8.233.73
729	-231	2021-10-12 15:10:16.359084	-1	79.8.233.73
730	-232	2021-10-12 15:13:17.292817	-1	93.42.222.106
731	-233	2021-10-12 15:13:18.72231	-1	93.42.222.106
732	-234	2021-10-12 15:13:51.425169	-1	93.42.222.106
733	-235	2021-10-12 15:14:07.402945	-1	93.42.222.106
734	-236	2021-10-12 15:28:31.440565	-1	79.8.233.73
735	-237	2021-10-12 15:44:40.023482	-1	79.8.233.73
736	-238	2021-10-12 15:45:52.54711	-1	79.8.233.73
737	-239	2021-10-12 15:47:35.104654	-1	79.8.233.73
738	-240	2021-10-12 15:50:10.686447	-1	79.8.233.73
739	-241	2021-10-12 15:50:38.545054	-1	79.8.233.73
740	-242	2021-10-12 16:02:18.081785	-1	93.42.222.106
741	-243	2021-10-12 16:03:01.486671	-1	93.42.222.106
742	-244	2021-10-12 16:10:09.065412	-1	93.42.222.106
743	-245	2021-10-12 16:43:29.029575	-1	93.42.222.106
744	-246	2021-10-12 16:46:34.234417	-1	93.42.222.106
745	10010315	2021-10-12 16:58:22.103811	203	93.42.222.106
746	10010317	2021-10-12 17:09:35.266751	206	93.42.222.106
747	-247	2021-10-13 15:11:02.845793	-1	93.42.222.106
748	-248	2021-10-13 16:01:02.395143	-1	93.42.222.106
749	-249	2021-10-13 16:01:09.269317	-1	93.42.222.106
750	-250	2021-10-13 16:21:26.128155	-1	93.42.222.106
751	-251	2021-10-13 16:29:20.944518	-1	93.42.222.106
752	-252	2021-10-13 16:56:28.046506	-1	93.42.222.106
753	-253	2021-10-13 16:56:38.944072	-1	93.42.222.106
754	-254	2021-10-13 17:17:24.579798	-1	93.42.222.106
755	-255	2021-10-13 17:39:29.687476	-1	93.42.222.106
756	-256	2021-10-14 08:25:49.349041	-1	2.232.192.227
757	-257	2021-10-14 08:28:35.525507	-1	93.42.222.106
758	10010315	2021-10-14 08:30:47.577956	203	93.42.222.106
759	10010315	2021-10-14 09:08:43.636674	203	93.42.222.106
760	10010317	2021-10-14 09:10:09.08708	206	93.42.222.106
761	10010317	2021-10-14 09:10:12.690175	206	93.42.222.106
762	10010315	2021-10-14 09:10:15.505656	203	93.42.222.106
763	10010317	2021-10-14 09:10:18.539313	206	93.42.222.106
764	10010317	2021-10-14 09:10:24.43781	206	93.42.222.106
765	10010315	2021-10-14 09:10:28.097887	203	93.42.222.106
766	10010315	2021-10-14 09:10:29.957769	203	93.42.222.106
767	10010321	2021-10-14 09:10:40.639459	204	93.42.222.106
768	10010323	2021-10-14 09:10:43.862204	203	93.42.222.106
769	10010321	2021-10-14 09:11:00.378513	204	93.42.222.106
770	10010317	2021-10-14 09:16:55.294523	206	93.42.222.106
771	10010315	2021-10-14 09:53:00.409559	203	93.42.222.106
772	10010315	2021-10-14 10:11:27.607905	203	79.8.233.73
773	-258	2021-10-14 11:09:34.713878	-1	93.42.222.106
774	-259	2021-10-21 09:55:37.055793	-1	79.52.10.109
775	10010315	2021-10-22 12:43:54.224202	203	93.42.222.106
776	10010315	2021-10-26 16:16:05.01225	203	93.42.222.106
777	10010321	2021-10-26 16:17:03.974945	204	93.42.222.106
778	10010321	2021-10-26 16:20:40.753611	204	93.42.222.106
779	10010331	2021-10-26 16:22:59.480422	203	93.42.222.106
780	10010315	2021-10-26 16:25:01.200072	203	93.42.222.106
781	10010331	2021-10-26 16:26:33.774453	203	93.42.222.106
782	-260	2021-10-26 16:27:09.489652	-1	93.42.222.106
783	10010321	2021-10-26 16:34:28.5511	204	93.42.222.106
784	10010329	2021-10-26 16:46:28.756762	207	79.8.233.73
785	-261	2021-10-26 16:48:13.888578	-1	93.42.222.106
786	-262	2021-10-26 16:54:30.663989	-1	93.42.222.106
787	-263	2021-10-26 16:55:46.640391	-1	93.42.222.106
788	10010329	2021-10-26 17:01:30.255456	207	93.42.222.106
789	-264	2021-10-26 17:04:22.252268	-1	93.42.222.106
790	-265	2021-10-26 17:04:56.469312	-1	93.42.222.106
791	10010329	2021-10-27 10:20:46.698014	207	93.42.222.106
792	10010315	2021-10-27 12:17:30.651333	203	93.42.222.106
793	10010315	2021-10-27 12:19:22.46727	203	93.42.222.106
794	-266	2021-10-27 14:10:00.604043	-1	93.42.222.106
795	-267	2021-10-27 14:13:48.765457	-1	93.42.222.106
796	-268	2021-10-27 15:27:41.129715	-1	93.42.222.106
797	-269	2021-10-27 15:28:27.248011	-1	93.42.222.106
798	10010315	2021-10-27 15:29:57.399312	203	93.42.222.106
799	-270	2021-10-27 15:32:00.590935	-1	93.42.222.106
800	-271	2021-10-27 17:01:40.960385	-1	151.73.245.230
801	10010321	2021-10-27 17:29:43.431597	204	151.73.245.230
802	-272	2021-10-28 08:40:40.772405	-1	87.17.146.119
803	-273	2021-10-28 09:57:32.259059	-1	87.17.146.119
804	-274	2021-10-28 10:07:58.109337	-1	87.17.146.119
805	-275	2021-10-28 10:19:24.374932	-1	87.17.146.119
806	-276	2021-10-28 10:52:12.280056	-1	82.55.101.26
807	10010315	2021-10-28 11:08:19.508683	203	93.42.222.106
847	10006775	2021-10-28 17:25:48.241259	206	5.90.136.66
848	10006775	2021-10-28 17:28:50.771829	206	5.90.136.66
857	10010315	2021-10-28 20:24:10.372376	203	2.232.192.227
858	-319	2021-10-29 08:00:50.731315	-1	151.73.245.230
859	10010315	2021-10-29 08:58:58.908123	203	93.42.222.106
860	10010322	2021-10-29 11:53:13.538886	207	109.115.186.2
864	-323	2021-10-29 16:20:38.304705	-1	93.42.222.106
865	-324	2021-10-29 16:29:47.815257	-1	93.42.222.106
866	-326	2021-10-31 20:31:27.652763	-1	51.158.21.238
808	10010315	2021-10-28 11:09:00.678969	203	93.42.222.106
809	-277	2021-10-28 11:09:08.720036	-1	93.42.222.106
850	-314	2021-10-28 17:30:19.555709	-1	5.90.136.66
851	-315	2021-10-28 17:35:12.244998	-1	5.90.136.66
855	-317	2021-10-28 17:58:26.645126	-1	93.42.222.106
856	-318	2021-10-28 20:23:30.239124	-1	2.232.192.227
861	-320	2021-10-29 15:51:54.769291	-1	93.42.222.106
862	-321	2021-10-29 16:02:36.123925	-1	93.42.222.106
863	-322	2021-10-29 16:20:21.468346	-1	93.42.222.106
810	-278	2021-10-28 11:09:17.476612	-1	93.42.222.106
811	-279	2021-10-28 11:10:28.643417	-1	93.42.222.106
813	-281	2021-10-28 11:11:53.493366	-1	93.42.222.106
816	-284	2021-10-28 11:13:39.127321	-1	93.42.222.106
817	-285	2021-10-28 11:14:06.099075	-1	93.42.222.106
819	-287	2021-10-28 11:14:31.744528	-1	93.42.222.106
820	-288	2021-10-28 12:08:41.463001	-1	93.42.222.106
822	10010315	2021-10-28 12:15:43.71519	203	93.42.222.106
823	-290	2021-10-28 12:31:29.375956	-1	93.42.222.106
824	-291	2021-10-28 12:31:34.89943	-1	79.8.233.73
825	-292	2021-10-28 12:32:03.225093	-1	93.42.222.106
826	-293	2021-10-28 12:33:05.035588	-1	93.42.222.106
827	-294	2021-10-28 14:16:07.199086	-1	93.42.222.106
831	-298	2021-10-28 14:58:09.391026	-1	79.8.233.73
832	-299	2021-10-28 14:58:34.37959	-1	79.8.233.73
834	-301	2021-10-28 14:59:34.006703	-1	79.8.233.73
840	-307	2021-10-28 15:39:09.883255	-1	93.42.222.106
843	-309	2021-10-28 16:06:14.835229	-1	93.42.222.106
845	-311	2021-10-28 16:07:45.980724	-1	93.42.222.106
852	-316	2021-10-28 17:39:05.483147	-1	93.42.222.106
853	10010319	2021-10-28 17:42:08.708899	201	93.42.222.106
812	-280	2021-10-28 11:11:39.159085	-1	93.42.222.106
814	-282	2021-10-28 11:12:04.951648	-1	93.42.222.106
815	-283	2021-10-28 11:13:13.343409	-1	93.42.222.106
818	-286	2021-10-28 11:14:24.180376	-1	93.42.222.106
821	-289	2021-10-28 12:14:33.164077	-1	93.42.222.106
828	-295	2021-10-28 14:17:36.803256	-1	93.42.222.106
829	-296	2021-10-28 14:48:34.046198	-1	93.42.222.106
830	-297	2021-10-28 14:57:19.8627	-1	79.8.233.73
833	-300	2021-10-28 14:58:43.082131	-1	79.8.233.73
835	-302	2021-10-28 14:59:53.881105	-1	79.8.233.73
836	-303	2021-10-28 15:06:06.636663	-1	93.42.222.106
837	-304	2021-10-28 15:06:15.484064	-1	93.42.222.106
838	-305	2021-10-28 15:06:52.882806	-1	93.42.222.106
839	-306	2021-10-28 15:33:39.764752	-1	93.42.222.106
841	-308	2021-10-28 15:44:13.198434	-1	93.42.222.106
842	10010315	2021-10-28 16:03:23.322637	203	93.42.222.106
844	-310	2021-10-28 16:07:07.180469	-1	93.42.222.106
846	-312	2021-10-28 16:09:58.863442	-1	93.42.222.106
849	-313	2021-10-28 17:30:13.804594	-1	93.42.222.106
854	10010329	2021-10-28 17:49:19.233004	207	93.42.222.106
867	-327	2021-11-08 13:14:50.366836	-1	109.115.186.2
868	-328	2021-11-08 15:55:05.647821	-1	109.115.186.2
869	-329	2021-11-08 16:01:52.724681	-1	109.115.186.2
870	-330	2021-11-08 16:42:47.887278	-1	109.115.186.2
871	-331	2021-11-08 16:47:17.260191	-1	109.115.186.2
872	-332	2021-11-10 13:34:00.10027	-1	27.115.124.37
873	-333	2021-11-16 14:56:28.594879	-1	109.115.186.2
874	-334	2021-11-16 14:57:34.728646	-1	109.115.186.2
875	-335	2021-11-16 15:02:32.893455	-1	109.115.186.2
876	-336	2021-11-17 14:11:47.496141	-1	93.42.222.106
877	-337	2021-11-17 14:20:21.71859	-1	93.42.222.106
878	-338	2021-11-17 14:46:08.389029	-1	93.42.222.106
879	-339	2021-11-17 16:25:54.678063	-1	93.42.222.106
880	10010321	2021-11-18 09:51:54.377619	204	109.115.186.2
881	-340	2021-11-18 09:53:05.770584	-1	109.115.186.2
995	-411	2021-11-24 16:23:46.215667	-1	127.0.0.1
882	-341	2021-11-18 11:21:46.900338	-1	93.42.222.106
883	-342	2021-11-18 11:23:48.465266	-1	151.14.95.136
884	-343	2021-11-18 11:24:14.830031	-1	93.42.222.106
885	-344	2021-11-18 11:27:28.906455	-1	93.42.222.106
886	-345	2021-11-18 11:27:35.425358	-1	93.42.222.106
887	-346	2021-11-18 11:28:45.121668	-1	93.42.222.106
888	-347	2021-11-18 11:28:57.072468	-1	93.42.222.106
889	-348	2021-11-18 11:29:08.149507	-1	93.42.222.106
890	-349	2021-11-18 11:32:19.322904	-1	79.8.233.73
891	-350	2021-11-18 11:48:07.349521	-1	93.42.222.106
892	-351	2021-11-18 11:51:37.87467	-1	93.42.222.106
893	-352	2021-11-18 11:57:29.200684	-1	93.42.222.106
894	-353	2021-11-18 11:57:39.4219	-1	93.42.222.106
895	-354	2021-11-18 11:58:09.137361	-1	93.42.222.106
896	-355	2021-11-18 23:04:27.044979	-1	151.70.13.242
897	10010315	2021-11-19 16:10:35.40485	203	93.42.222.106
898	10010315	2021-11-19 16:12:55.170435	203	93.42.222.106
899	10010315	2021-11-19 16:13:13.188436	203	93.42.222.106
900	10010315	2021-11-19 16:13:25.703304	203	93.42.222.106
901	10010315	2021-11-19 16:14:22.438412	203	93.42.222.106
902	10010315	2021-11-19 16:14:40.372703	203	93.42.222.106
903	10010315	2021-11-19 16:14:53.864747	203	93.42.222.106
904	10010315	2021-11-19 16:16:51.878417	203	93.42.222.106
905	10010315	2021-11-19 16:17:15.695041	203	93.42.222.106
906	10010315	2021-11-19 16:18:21.941977	203	93.42.222.106
907	10010315	2021-11-19 16:25:31.320074	203	93.42.222.106
908	10010315	2021-11-19 16:27:19.824953	203	93.42.222.106
909	10010315	2021-11-19 16:33:40.285498	203	93.42.222.106
910	10010315	2021-11-19 16:39:50.0429	203	93.42.222.106
911	10010315	2021-11-19 16:40:11.623513	203	93.42.222.106
912	10010315	2021-11-19 16:41:05.21792	203	93.42.222.106
913	-356	2021-11-19 16:41:35.875541	-1	93.42.222.106
914	10010315	2021-11-19 16:42:16.448827	203	93.42.222.106
915	10010315	2021-11-19 16:44:32.414566	203	93.42.222.106
916	10010315	2021-11-19 16:44:47.862291	203	93.42.222.106
917	10010315	2021-11-19 16:44:54.895844	203	93.42.222.106
918	10010315	2021-11-19 16:46:53.98903	203	93.42.222.106
919	10010315	2021-11-19 16:50:53.190791	203	93.42.222.106
920	10010315	2021-11-19 16:59:02.003529	203	93.42.222.106
921	10010315	2021-11-19 16:59:53.136147	203	93.42.222.106
922	10010315	2021-11-19 17:00:06.52028	203	93.42.222.106
923	10010315	2021-11-19 17:01:20.128209	203	93.42.222.106
924	10010315	2021-11-19 17:02:36.648733	203	93.42.222.106
925	10010315	2021-11-19 17:03:44.291945	203	93.42.222.106
926	10010315	2021-11-19 17:05:25.75136	203	93.42.222.106
927	10010315	2021-11-19 17:06:06.817411	203	93.42.222.106
928	10010315	2021-11-19 17:11:09.89487	203	93.42.222.106
929	10010315	2021-11-19 17:11:21.072082	203	93.42.222.106
930	10010315	2021-11-19 17:19:46.824671	203	93.42.222.106
931	10010315	2021-11-19 17:19:56.601055	203	93.42.222.106
932	10010315	2021-11-19 17:20:30.747677	203	93.42.222.106
933	10010315	2021-11-19 17:20:39.710164	203	93.42.222.106
934	-357	2021-11-22 06:47:17.144206	-1	151.57.207.250
935	-358	2021-11-22 14:04:51.532617	-1	93.42.222.106
936	-359	2021-11-22 14:05:03.160647	-1	93.42.222.106
937	-360	2021-11-22 14:07:06.441396	-1	127.0.0.1
938	-361	2021-11-22 14:10:14.609672	-1	93.42.222.106
939	-362	2021-11-22 14:35:08.830989	-1	127.0.0.1
940	10010646	2021-11-22 15:10:50.633609	204	127.0.0.1
941	10010646	2021-11-22 15:27:07.544397	204	127.0.0.1
942	10010646	2021-11-22 15:50:21.041762	204	93.42.222.106
943	10010646	2021-11-22 15:53:13.313662	204	93.42.222.106
944	-363	2021-11-22 16:05:47.56065	-1	127.0.0.1
945	-364	2021-11-22 16:06:25.489352	-1	127.0.0.1
946	-365	2021-11-22 16:10:01.484282	-1	127.0.0.1
947	-366	2021-11-22 16:16:55.198909	-1	127.0.0.1
948	-367	2021-11-22 16:19:00.232686	-1	127.0.0.1
949	-368	2021-11-22 16:23:30.760585	-1	127.0.0.1
950	-369	2021-11-22 16:31:04.535496	-1	127.0.0.1
951	-370	2021-11-22 16:35:45.294908	-1	127.0.0.1
952	-371	2021-11-22 16:39:45.863078	-1	127.0.0.1
953	-372	2021-11-22 16:42:12.815665	-1	127.0.0.1
954	10010646	2021-11-22 16:43:54.331026	204	127.0.0.1
955	10010646	2021-11-22 16:46:26.457175	204	127.0.0.1
956	-373	2021-11-22 17:05:31.535766	-1	127.0.0.1
957	10010646	2021-11-22 17:06:14.098624	204	127.0.0.1
958	-374	2021-11-22 17:07:07.385976	-1	127.0.0.1
959	-375	2021-11-22 17:07:40.822731	-1	127.0.0.1
960	-376	2021-11-22 17:08:30.442099	-1	127.0.0.1
961	-377	2021-11-22 17:08:43.503404	-1	127.0.0.1
962	-378	2021-11-22 17:09:00.439061	-1	127.0.0.1
963	-379	2021-11-22 17:09:58.942964	-1	127.0.0.1
964	-380	2021-11-22 17:10:09.119887	-1	127.0.0.1
965	-381	2021-11-22 17:11:42.381755	-1	93.42.222.106
966	-382	2021-11-22 17:13:22.485947	-1	93.42.222.106
967	-383	2021-11-22 17:13:32.817849	-1	127.0.0.1
968	-384	2021-11-22 17:14:23.76359	-1	127.0.0.1
969	-385	2021-11-22 17:14:46.219415	-1	93.42.222.106
970	-386	2021-11-22 17:15:32.573854	-1	127.0.0.1
971	-387	2021-11-22 17:18:01.354913	-1	127.0.0.1
972	-388	2021-11-22 17:18:32.394573	-1	127.0.0.1
973	-389	2021-11-22 17:23:39.917276	-1	127.0.0.1
974	-390	2021-11-22 17:24:15.406303	-1	127.0.0.1
975	-391	2021-11-22 17:24:56.969089	-1	127.0.0.1
976	-392	2021-11-22 17:25:09.252814	-1	127.0.0.1
977	-393	2021-11-22 17:30:43.144984	-1	127.0.0.1
978	-394	2021-11-22 18:11:14.941929	-1	127.0.0.1
979	-395	2021-11-23 08:06:13.56446	-1	127.0.0.1
980	-396	2021-11-23 08:17:19.879425	-1	127.0.0.1
981	-397	2021-11-23 08:44:03.311564	-1	127.0.0.1
982	-398	2021-11-23 09:08:45.298787	-1	127.0.0.1
983	-399	2021-11-23 09:33:05.755486	-1	127.0.0.1
984	-400	2021-11-23 09:38:37.087542	-1	127.0.0.1
985	-401	2021-11-23 10:05:36.147301	-1	127.0.0.1
986	-402	2021-11-23 10:05:40.417024	-1	127.0.0.1
987	-403	2021-11-23 15:58:30.990215	-1	127.0.0.1
988	-404	2021-11-23 16:16:40.154632	-1	127.0.0.1
989	-405	2021-11-23 18:01:43.057212	-1	127.0.0.1
990	-406	2021-11-24 15:58:22.066935	-1	127.0.0.1
991	-407	2021-11-24 16:19:18.327462	-1	127.0.0.1
992	-408	2021-11-24 16:20:03.244322	-1	127.0.0.1
993	-409	2021-11-24 16:20:47.131076	-1	127.0.0.1
994	-410	2021-11-24 16:20:47.886757	-1	127.0.0.1
996	-412	2021-11-24 16:26:49.330145	-1	127.0.0.1
997	-413	2021-11-24 16:44:37.707547	-1	127.0.0.1
998	-414	2021-11-25 16:38:39.229166	-1	127.0.0.1
999	-415	2021-11-25 16:46:16.479956	-1	127.0.0.1
1000	-416	2021-11-26 09:56:52.852388	-1	127.0.0.1
1001	-417	2021-11-26 09:59:17.138035	-1	127.0.0.1
1002	-418	2021-11-26 11:20:06.27446	-1	127.0.0.1
1003	-419	2021-11-26 13:08:09.120168	-1	127.0.0.1
1004	-420	2021-11-26 13:14:32.394829	-1	127.0.0.1
1005	-421	2021-11-26 13:15:19.876832	-1	127.0.0.1
1006	-422	2021-11-26 13:16:24.671511	-1	127.0.0.1
1007	-423	2021-11-27 09:04:34.090781	-1	127.0.0.1
1008	-424	2021-11-27 10:05:42.522213	-1	127.0.0.1
1009	-425	2021-11-27 10:28:53.893595	-1	127.0.0.1
1010	-426	2021-11-27 10:52:40.555735	-1	127.0.0.1
1011	-427	2021-11-27 11:20:25.120507	-1	127.0.0.1
1012	-428	2021-11-27 14:53:33.846034	-1	127.0.0.1
1013	-429	2021-11-27 15:04:40.461355	-1	127.0.0.1
1014	-430	2021-11-27 21:57:26.408142	-1	127.0.0.1
1015	-431	2021-11-28 15:47:10.192997	-1	127.0.0.1
1016	10010646	2021-11-29 15:37:37.296163	204	127.0.0.1
1017	10010646	2021-11-29 16:09:53.539489	204	127.0.0.1
1018	-432	2021-12-01 11:11:07.119517	-1	127.0.0.1
1019	-433	2021-12-01 12:44:13.641637	-1	127.0.0.1
1020	-434	2021-12-01 12:58:48.765105	-1	127.0.0.1
1021	-435	2021-12-01 16:33:00.403971	-1	127.0.0.1
1022	-436	2021-12-01 16:36:45.452436	-1	127.0.0.1
1023	-437	2021-12-01 16:37:04.053075	-1	127.0.0.1
1024	-438	2021-12-02 10:01:26.903577	-1	127.0.0.1
1025	-439	2021-12-02 10:01:27.095781	-1	127.0.0.1
1026	-440	2021-12-02 10:34:57.525428	-1	127.0.0.1
1027	-441	2021-12-02 10:44:50.89357	-1	127.0.0.1
1028	-442	2021-12-02 10:50:07.726733	-1	127.0.0.1
1029	-443	2021-12-02 10:52:37.834738	-1	127.0.0.1
1030	-444	2021-12-02 10:52:40.315843	-1	127.0.0.1
1031	-445	2021-12-02 10:52:46.880381	-1	127.0.0.1
1032	-446	2021-12-02 10:52:52.347616	-1	127.0.0.1
1033	-447	2021-12-02 12:03:46.516042	-1	127.0.0.1
1034	-448	2021-12-03 12:34:49.6476	-1	127.0.0.1
1035	-449	2021-12-05 13:05:25.588359	-1	127.0.0.1
1036	-450	2021-12-06 13:14:19.098339	-1	127.0.0.1
1037	-451	2021-12-06 19:15:18.346589	-1	127.0.0.1
1038	-452	2021-12-06 20:36:31.725816	-1	127.0.0.1
1039	-453	2021-12-07 11:08:32.594354	-1	127.0.0.1
1040	-454	2021-12-07 13:58:50.420536	-1	127.0.0.1
1041	-455	2021-12-07 14:49:36.238856	-1	127.0.0.1
1042	-456	2021-12-09 10:07:17.297826	-1	127.0.0.1
1043	-457	2021-12-09 11:59:46.12947	-1	127.0.0.1
1044	-458	2021-12-09 14:32:33.294824	-1	127.0.0.1
1045	-459	2021-12-09 14:34:18.646983	-1	127.0.0.1
1046	-460	2021-12-10 09:50:45.501867	-1	127.0.0.1
1047	-461	2021-12-10 11:07:50.377292	-1	127.0.0.1
1048	-462	2021-12-10 11:09:11.074642	-1	127.0.0.1
1049	-463	2021-12-10 11:22:51.547692	-1	127.0.0.1
1050	-464	2021-12-10 14:25:43.078664	-1	127.0.0.1
1051	-465	2021-12-13 10:39:25.367139	-1	127.0.0.1
1052	-466	2021-12-13 14:44:18.27261	-1	127.0.0.1
1053	-467	2021-12-15 10:02:58.149029	-1	127.0.0.1
1054	-468	2021-12-15 10:54:36.90825	-1	127.0.0.1
1055	-469	2021-12-16 09:50:01.936226	-1	127.0.0.1
1056	-470	2021-12-17 13:38:20.353731	-1	127.0.0.1
1057	-471	2021-12-19 11:28:10.181179	-1	127.0.0.1
1058	-472	2021-12-19 11:30:19.797609	-1	127.0.0.1
1059	-473	2021-12-19 16:33:38.606193	-1	127.0.0.1
1060	-474	2021-12-20 19:43:49.708307	-1	127.0.0.1
1061	-475	2021-12-21 10:59:05.666664	-1	127.0.0.1
1062	-476	2021-12-21 13:21:29.581392	-1	127.0.0.1
1063	-477	2021-12-22 10:15:58.569567	-1	127.0.0.1
1064	-478	2021-12-22 11:22:49.658841	-1	127.0.0.1
1065	-479	2021-12-22 12:06:21.447285	-1	127.0.0.1
1066	-480	2021-12-22 12:29:17.047272	-1	127.0.0.1
1067	-481	2021-12-22 13:08:37.606586	-1	127.0.0.1
1068	-482	2021-12-23 12:08:27.147711	-1	127.0.0.1
1069	-483	2021-12-26 11:19:57.715584	-1	127.0.0.1
1070	-484	2021-12-29 10:48:52.76727	-1	127.0.0.1
1071	-485	2022-01-03 15:28:30.190225	-1	127.0.0.1
1072	-486	2022-01-03 15:29:41.158738	-1	127.0.0.1
1073	-487	2022-01-04 14:17:22.339871	-1	127.0.0.1
1074	-488	2022-01-05 08:14:57.654854	-1	127.0.0.1
1075	-489	2022-01-05 22:16:35.152734	-1	127.0.0.1
1076	-490	2022-01-05 22:18:30.272267	-1	127.0.0.1
1077	-491	2022-01-06 14:52:59.226615	-1	127.0.0.1
1078	-492	2022-01-06 14:52:59.597783	-1	127.0.0.1
1079	-493	2022-01-06 14:52:59.675794	-1	127.0.0.1
1080	-494	2022-01-06 14:52:59.901406	-1	127.0.0.1
1081	-495	2022-01-06 14:53:00.401973	-1	127.0.0.1
1082	-496	2022-01-06 14:53:09.794428	-1	127.0.0.1
1083	-497	2022-01-06 15:06:59.420756	-1	127.0.0.1
1084	-498	2022-01-07 09:59:29.930755	-1	127.0.0.1
1085	-499	2022-01-13 09:31:02.950757	-1	127.0.0.1
1086	-500	2022-01-13 11:12:07.199096	-1	127.0.0.1
1087	-501	2022-01-13 14:00:51.057677	-1	127.0.0.1
1088	-502	2022-01-13 22:14:51.741871	-1	127.0.0.1
1089	-503	2022-01-14 09:22:58.946086	-1	127.0.0.1
1090	-504	2022-01-14 13:07:52.018913	-1	127.0.0.1
1091	-505	2022-01-17 12:26:18.903479	-1	127.0.0.1
1092	-506	2022-01-17 14:11:56.509774	-1	127.0.0.1
1093	-507	2022-01-17 14:12:00.05402	-1	127.0.0.1
1094	-508	2022-01-17 21:31:19.325133	-1	127.0.0.1
1095	-509	2022-01-18 18:36:02.279971	-1	127.0.0.1
1096	-510	2022-01-19 11:08:33.182023	-1	127.0.0.1
1097	-511	2022-01-20 17:47:05.934105	-1	127.0.0.1
1098	-512	2022-01-21 11:03:07.142789	-1	127.0.0.1
1099	-513	2022-01-21 11:03:10.766187	-1	127.0.0.1
1100	-514	2022-01-21 16:12:46.052186	-1	127.0.0.1
1101	-515	2022-01-21 19:32:27.255276	-1	127.0.0.1
1102	-516	2022-01-24 07:50:14.856373	-1	127.0.0.1
1103	-517	2022-01-24 07:51:46.120625	-1	127.0.0.1
1104	-518	2022-01-24 07:51:59.69715	-1	127.0.0.1
1105	-519	2022-01-24 09:15:13.40466	-1	127.0.0.1
1106	-520	2022-01-24 09:23:59.439161	-1	127.0.0.1
1107	-521	2022-01-24 09:24:47.050747	-1	127.0.0.1
1108	-522	2022-01-24 11:54:33.050029	-1	127.0.0.1
1109	-523	2022-01-24 11:55:19.033005	-1	127.0.0.1
1110	-524	2022-01-25 08:42:13.679273	-1	127.0.0.1
1111	-525	2022-01-25 08:43:15.168617	-1	127.0.0.1
1112	-526	2022-01-25 10:49:45.347115	-1	127.0.0.1
1113	-527	2022-01-25 10:49:46.155104	-1	127.0.0.1
1114	-528	2022-01-25 10:49:46.386676	-1	127.0.0.1
1115	-529	2022-01-25 10:49:46.394244	-1	127.0.0.1
1116	-530	2022-01-25 10:54:01.744595	-1	127.0.0.1
1117	-531	2022-01-25 13:21:01.645231	-1	127.0.0.1
1118	-532	2022-01-25 14:56:42.994752	-1	127.0.0.1
1119	-533	2022-01-25 14:59:03.391173	-1	127.0.0.1
1120	-534	2022-01-25 15:00:33.248162	-1	127.0.0.1
1121	-535	2022-01-25 16:58:23.205532	-1	127.0.0.1
1123	-537	2022-01-26 16:45:37.365678	-1	127.0.0.1
1124	-538	2022-01-27 10:40:42.470289	-1	127.0.0.1
1125	-539	2022-01-27 10:43:22.655332	-1	127.0.0.1
1133	-547	2022-01-31 12:01:42.289345	-1	127.0.0.1
1134	-548	2022-01-31 12:01:44.671433	-1	127.0.0.1
1137	-551	2022-02-02 09:26:47.595017	-1	127.0.0.1
1138	-552	2022-02-02 09:29:58.638107	-1	127.0.0.1
1139	-553	2022-02-02 09:37:14.917326	-1	127.0.0.1
1140	-554	2022-02-02 10:14:35.889592	-1	127.0.0.1
1143	-557	2022-02-02 12:32:19.916124	-1	127.0.0.1
1145	-559	2022-02-03 09:13:22.835067	-1	127.0.0.1
1146	-560	2022-02-03 09:16:10.425975	-1	127.0.0.1
1147	-561	2022-02-03 09:20:44.923822	-1	127.0.0.1
1149	-563	2022-02-03 12:38:52.298516	-1	127.0.0.1
1122	-536	2022-01-26 11:32:29.219795	-1	127.0.0.1
1126	-540	2022-01-27 15:25:38.621715	-1	127.0.0.1
1127	-541	2022-01-28 08:24:40.217525	-1	127.0.0.1
1128	-542	2022-01-28 09:53:21.107889	-1	127.0.0.1
1129	-543	2022-01-31 09:27:13.22302	-1	127.0.0.1
1130	-544	2022-01-31 10:58:36.767399	-1	127.0.0.1
1131	-545	2022-01-31 11:30:22.067537	-1	127.0.0.1
1132	-546	2022-01-31 12:00:09.342407	-1	127.0.0.1
1135	-549	2022-01-31 12:47:16.179954	-1	127.0.0.1
1136	-550	2022-01-31 12:47:16.987693	-1	127.0.0.1
1141	-555	2022-02-02 10:17:58.182714	-1	127.0.0.1
1142	-556	2022-02-02 11:05:01.991305	-1	127.0.0.1
1144	-558	2022-02-02 12:37:51.480586	-1	127.0.0.1
1148	-562	2022-02-03 09:23:54.488137	-1	127.0.0.1
1150	-564	2022-02-07 09:05:38.133863	-1	127.0.0.1
1151	-565	2022-02-07 09:06:40.219085	-1	127.0.0.1
1152	-566	2022-02-07 10:23:03.133594	-1	127.0.0.1
1153	-567	2022-02-07 13:00:24.369025	-1	127.0.0.1
1154	-568	2022-02-07 13:15:05.309195	-1	127.0.0.1
1155	-569	2022-02-07 13:19:15.265067	-1	127.0.0.1
1156	-570	2022-02-07 14:10:37.449428	-1	127.0.0.1
1157	-571	2022-02-07 14:16:59.045589	-1	127.0.0.1
1158	-572	2022-02-07 14:27:48.145185	-1	127.0.0.1
1159	-573	2022-02-07 14:42:11.053995	-1	127.0.0.1
1160	-574	2022-02-09 14:28:14.534082	-1	127.0.0.1
1161	-575	2022-02-10 05:33:29.215785	-1	127.0.0.1
1162	-576	2022-02-10 10:32:34.469576	-1	127.0.0.1
1163	-577	2022-02-10 12:57:42.836514	-1	127.0.0.1
1164	-578	2022-02-10 15:13:49.241528	-1	127.0.0.1
1165	-579	2022-02-11 18:49:15.115086	-1	127.0.0.1
1166	-580	2022-02-14 11:02:13.843794	-1	127.0.0.1
1167	-581	2022-02-14 14:28:33.107618	-1	127.0.0.1
1168	-582	2022-02-14 19:20:27.882263	-1	127.0.0.1
1169	-583	2022-02-16 08:30:23.605097	-1	127.0.0.1
1170	-584	2022-02-16 09:03:03.488034	-1	127.0.0.1
1171	-585	2022-02-16 17:04:54.870692	-1	127.0.0.1
1172	-586	2022-02-16 19:52:01.896488	-1	127.0.0.1
1173	-587	2022-02-17 07:45:50.191838	-1	127.0.0.1
1174	-588	2022-02-17 09:05:04.022719	-1	127.0.0.1
1175	-589	2022-02-17 09:06:22.58554	-1	127.0.0.1
1176	-590	2022-02-17 20:21:16.435152	-1	127.0.0.1
1177	-591	2022-02-18 12:32:06.745423	-1	127.0.0.1
1178	-592	2022-02-18 22:32:51.549342	-1	127.0.0.1
1179	-593	2022-02-19 10:18:36.593909	-1	127.0.0.1
1180	-594	2022-02-19 11:34:28.3303	-1	127.0.0.1
1181	-595	2022-02-19 11:34:48.943129	-1	127.0.0.1
1182	-596	2022-02-19 21:09:42.651644	-1	127.0.0.1
1183	-597	2022-02-22 16:16:01.007642	-1	127.0.0.1
1184	-598	2022-02-23 19:33:51.060958	-1	127.0.0.1
1185	-599	2022-02-24 17:40:07.719049	-1	127.0.0.1
1186	-600	2022-02-24 20:49:01.317864	-1	127.0.0.1
1187	-601	2022-02-25 09:40:25.97746	-1	127.0.0.1
1188	-602	2022-02-25 09:40:27.998886	-1	127.0.0.1
1189	-603	2022-02-25 12:00:18.290836	-1	127.0.0.1
1190	-604	2022-02-25 12:03:45.926645	-1	127.0.0.1
1191	-605	2022-02-25 12:05:48.519538	-1	127.0.0.1
1192	-606	2022-02-25 12:06:45.867871	-1	127.0.0.1
1193	-607	2022-02-25 12:06:56.862318	-1	127.0.0.1
1194	-608	2022-02-25 12:08:42.67559	-1	127.0.0.1
1195	-609	2022-02-25 14:25:20.593999	-1	127.0.0.1
1196	-610	2022-02-25 14:48:12.335594	-1	127.0.0.1
1197	-611	2022-02-25 14:48:15.719524	-1	127.0.0.1
1198	-612	2022-02-25 14:50:45.56864	-1	127.0.0.1
1199	-613	2022-02-25 14:51:59.807849	-1	127.0.0.1
1200	-614	2022-02-25 14:53:31.78964	-1	127.0.0.1
1201	-615	2022-02-25 16:18:16.970309	-1	127.0.0.1
1202	-616	2022-02-25 16:56:25.146199	-1	127.0.0.1
1203	-617	2022-02-25 16:56:27.731461	-1	127.0.0.1
1204	-618	2022-02-25 16:56:34.158951	-1	127.0.0.1
1205	-619	2022-02-26 12:48:26.099676	-1	127.0.0.1
1206	-620	2022-02-26 12:52:10.853943	-1	127.0.0.1
1207	-621	2022-02-28 04:19:16.512666	-1	127.0.0.1
1208	-622	2022-03-01 12:30:55.420508	-1	127.0.0.1
1209	-623	2022-03-01 18:59:44.626641	-1	127.0.0.1
1210	-624	2022-03-02 23:17:21.086559	-1	127.0.0.1
1211	-625	2022-03-04 02:54:02.857468	-1	127.0.0.1
1212	-626	2022-03-04 16:29:22.011079	-1	127.0.0.1
1213	-627	2022-03-05 03:34:56.543465	-1	127.0.0.1
1214	-628	2022-03-07 12:21:43.862699	-1	127.0.0.1
1215	-629	2022-03-08 06:06:46.739814	-1	127.0.0.1
1216	-630	2022-03-09 09:56:47.458607	-1	127.0.0.1
1217	-631	2022-03-10 15:45:40.047272	-1	127.0.0.1
1218	-632	2022-03-11 02:51:20.547012	-1	127.0.0.1
1219	-633	2022-03-15 09:58:30.203131	-1	127.0.0.1
1220	-634	2022-03-16 10:22:25.501294	-1	127.0.0.1
1221	-635	2022-03-16 10:26:26.600254	-1	127.0.0.1
1222	-636	2022-03-16 10:42:34.400561	-1	127.0.0.1
1223	-637	2022-03-16 13:59:32.226207	-1	127.0.0.1
1224	-638	2022-03-17 23:09:06.093333	-1	127.0.0.1
1225	-639	2022-03-18 09:06:51.790131	-1	127.0.0.1
1226	-640	2022-03-18 09:14:35.077581	-1	127.0.0.1
1227	-641	2022-03-18 09:15:28.087846	-1	127.0.0.1
1228	-642	2022-03-18 11:35:57.873525	-1	127.0.0.1
1229	-643	2022-03-20 12:40:26.97557	-1	127.0.0.1
1230	-644	2022-03-20 18:21:35.588932	-1	127.0.0.1
1231	-645	2022-03-21 08:22:03.614543	-1	127.0.0.1
1232	-646	2022-03-21 13:14:39.626961	-1	127.0.0.1
1233	-647	2022-03-22 11:07:20.97854	-1	127.0.0.1
1234	-648	2022-03-23 15:23:36.587174	-1	127.0.0.1
1235	-649	2022-03-23 15:24:51.63465	-1	127.0.0.1
1236	-650	2022-03-23 15:26:44.994975	-1	127.0.0.1
1237	-651	2022-03-25 07:21:44.851972	-1	127.0.0.1
1238	-652	2022-03-25 11:45:49.479599	-1	127.0.0.1
1239	-653	2022-03-25 11:46:40.067483	-1	127.0.0.1
1240	-654	2022-03-25 11:47:28.699597	-1	127.0.0.1
1241	-655	2022-03-25 11:51:20.966682	-1	127.0.0.1
1242	-656	2022-03-25 11:53:13.148238	-1	127.0.0.1
1243	-657	2022-03-26 17:55:31.620106	-1	127.0.0.1
1244	-658	2022-03-28 16:21:20.613397	-1	127.0.0.1
1245	-659	2022-03-30 03:31:07.003995	-1	127.0.0.1
1246	-660	2022-03-30 14:43:27.960828	-1	127.0.0.1
1247	-663	2022-04-01 08:51:45.033069	-1	93.42.222.106
1248	-664	2022-04-01 08:59:25.692507	-1	93.42.222.106
\.


--
-- Data for Name: log_checklist; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.log_checklist (id, id_checklist, id_utente, entered) FROM stdin;
1	2022	10000740	2021-05-10 09:20:15.722825
2	2022	10000740	2021-05-10 10:45:37.936603
3	2022	10000740	2021-05-10 10:45:42.75421
4	2023	-7	2021-05-11 15:19:02.847656
5	2022	-12	2021-05-11 15:35:09.046941
6	2022	-16	2021-05-11 15:40:54.659375
7	2022	-27	2021-05-11 16:53:00.11086
8	2022	10000740	2021-05-11 17:07:30.532558
9	2022	10000740	2021-05-11 17:07:43.999023
10	2048	-49	2021-06-14 17:24:18.598193
11	2022	1	2021-06-14 17:31:17.876124
12	2022	1	2021-06-14 17:31:20.708098
13	2022	10010280	2021-06-15 09:43:40.915025
14	2023	10010286	2021-06-15 10:08:14.465205
15	2023	10010286	2021-06-15 10:08:25.485768
16	2023	10010286	2021-06-15 10:08:41.434142
17	2022	10010280	2021-06-15 10:33:42.167735
18	2022	10010280	2021-06-15 10:33:47.424668
19	2022	10010280	2021-06-15 10:34:01.5246
20	2022	10010280	2021-06-15 10:34:36.239166
21	2022	10010280	2021-06-15 10:34:53.744668
22	2022	10010280	2021-06-15 10:36:42.529212
23	2022	10010280	2021-06-15 10:36:45.136211
24	2022	10010280	2021-06-15 10:37:16.180053
25	2022	10010280	2021-06-15 10:37:18.772143
26	2022	10010280	2021-06-15 10:37:24.160887
27	2022	10010280	2021-06-15 10:37:49.846563
28	2022	10010280	2021-06-15 10:37:53.800519
29	2022	10010280	2021-06-15 10:40:24.801074
30	2023	10010280	2021-06-15 10:46:36.646059
31	2023	10010280	2021-06-15 10:47:04.851749
32	2023	10010286	2021-06-15 11:24:28.583822
33	2023	10010286	2021-06-15 11:24:38.967221
34	2023	10010286	2021-06-15 11:25:54.153501
35	2023	10010286	2021-06-15 11:26:43.628866
36	2023	10010286	2021-06-15 11:31:06.472893
37	2023	10010286	2021-06-15 11:31:37.004502
38	2023	10010286	2021-06-15 11:32:14.372252
39	2023	10010286	2021-06-15 11:32:28.356416
40	2023	10010286	2021-06-15 11:32:45.208923
41	2023	10010286	2021-06-15 11:34:21.249148
42	2023	10010286	2021-06-15 11:34:35.816119
43	2023	10010286	2021-06-15 11:34:49.809789
44	2023	10010286	2021-06-15 11:35:03.513206
45	2023	10010286	2021-06-15 11:35:19.415574
46	2023	10010286	2021-06-15 11:35:32.237159
47	2023	10010286	2021-06-15 11:35:42.695809
48	2023	10010286	2021-06-15 11:35:50.981101
49	2023	10010286	2021-06-15 11:35:58.355728
50	2023	10010286	2021-06-15 11:36:11.89167
51	2023	10010286	2021-06-15 11:36:21.256188
52	2023	10010286	2021-06-15 11:36:34.252713
53	2023	10010286	2021-06-15 11:36:42.850577
54	2023	10010286	2021-06-15 11:36:50.464743
55	2023	10010286	2021-06-15 11:39:06.798117
56	2023	10010286	2021-06-15 11:39:33.818325
57	2023	10010286	2021-06-15 11:39:55.532574
58	2023	10010286	2021-06-15 11:40:04.908376
59	2023	10010286	2021-06-15 11:40:17.597476
60	2023	10010286	2021-06-15 11:40:25.575858
61	2023	10010286	2021-06-15 11:40:35.039514
62	2023	10010286	2021-06-15 11:40:42.875444
63	2023	10010286	2021-06-15 11:40:50.401191
64	2023	10010286	2021-06-15 11:45:33.022372
65	2023	10010286	2021-06-15 11:46:41.790042
66	2023	10010286	2021-06-15 11:49:08.664276
67	2023	10010286	2021-06-15 11:49:23.600788
68	2022	10010286	2021-06-15 11:50:14.719754
69	2023	10010286	2021-06-15 11:50:18.379998
70	2024	10010286	2021-06-15 11:50:33.680482
71	2023	10010286	2021-06-15 11:50:37.803534
72	2023	10010286	2021-06-15 11:51:28.437665
73	2022	10010286	2021-06-15 12:04:55.350554
74	2023	10010286	2021-06-15 12:05:21.833181
75	2022	10010285	2021-06-17 08:57:29.880859
76	2022	10010285	2021-06-17 09:03:48.451275
77	2022	10010279	2021-06-17 09:16:07.257411
78	2023	10010286	2021-06-17 15:40:24.452368
79	2023	10010286	2021-06-17 15:42:09.942659
80	2022	10010285	2021-06-17 15:51:49.904747
81	2022	10010285	2021-06-17 15:51:55.808827
82	2023	10010286	2021-09-15 10:16:18.866292
83	2058	10010300	2021-09-22 16:14:39.631718
84	2058	10010300	2021-09-22 16:21:37.249196
85	2058	10010300	2021-09-22 16:21:54.353831
86	2064	10010300	2021-09-22 16:26:08.222501
87	2058	10010300	2021-09-22 16:39:45.064706
88	2058	10010300	2021-09-22 16:39:49.820113
89	2030	10010300	2021-09-22 16:42:19.970197
90	2058	10010300	2021-09-22 17:02:53.814244
91	2058	10010299	2021-09-22 17:24:44.943985
92	2042	10010301	2021-09-22 17:55:53.958016
93	2042	10010301	2021-09-22 18:06:48.321953
94	2058	10010303	2021-09-23 10:39:52.862901
95	2022	10010304	2021-09-28 14:51:48.515278
96	2022	10010306	2021-09-29 11:44:12.872435
97	2022	10010306	2021-09-29 11:44:17.843077
98	2022	10010306	2021-09-29 11:44:21.180234
99	2022	10010306	2021-09-29 11:44:25.307007
100	2022	-209	2021-09-30 14:37:33.865673
101	2022	-209	2021-09-30 14:38:47.037646
102	2022	-209	2021-09-30 14:38:59.545756
103	2022	10010315	2021-09-30 14:43:45.601024
104	2022	10010318	2021-09-30 15:47:23.884139
105	2037	10010313	2021-09-30 15:58:06.755474
106	2023	10010331	2021-10-04 12:40:04.607387
107	2041	10010325	2021-10-04 14:58:05.340451
108	2048	10010325	2021-10-04 15:07:23.358465
109	2022	-228	2021-10-12 14:50:28.721336
110	2022	-228	2021-10-12 14:50:47.99095
111	2022	-228	2021-10-12 14:52:03.202403
112	2022	-228	2021-10-12 14:53:53.488693
113	2022	-228	2021-10-12 14:58:15.603123
114	2022	-228	2021-10-12 15:00:18.66612
115	2022	-228	2021-10-12 15:01:22.697559
116	2022	-228	2021-10-12 15:02:27.682003
117	2022	-228	2021-10-12 15:02:52.251621
118	2027	-241	2021-10-12 15:54:11.931129
119	2027	-241	2021-10-12 15:54:25.78844
120	2022	-247	2021-10-13 15:12:59.570334
121	2040	-247	2021-10-13 15:25:07.286916
122	2022	-247	2021-10-13 15:25:28.485244
123	2022	-247	2021-10-13 15:27:18.14536
124	2022	\N	2021-10-13 15:31:37.082411
125	2022	\N	2021-10-13 15:33:26.473795
126	2022	-249	2021-10-13 16:01:27.772707
127	2022	\N	2021-10-13 16:03:16.70268
128	2022	-250	2021-10-13 16:21:38.691148
129	2022	\N	2021-10-13 16:22:54.555218
130	2022	\N	2021-10-13 16:24:44.982598
131	2022	\N	2021-10-13 16:25:48.772003
132	2022	\N	2021-10-13 16:27:13.745821
133	2022	\N	2021-10-13 16:27:48.844133
134	2022	-251	2021-10-13 16:29:30.064878
135	2022	-251	2021-10-13 16:29:55.377499
136	2022	-251	2021-10-13 16:32:35.119081
137	2022	-251	2021-10-13 16:32:47.532467
138	2022	-253	2021-10-13 16:56:51.579756
139	2022	\N	2021-10-13 16:58:37.836881
140	2022	\N	2021-10-13 16:59:22.165321
141	2022	\N	2021-10-13 17:15:00.534681
142	2022	-254	2021-10-13 17:17:36.093738
143	2022	\N	2021-10-13 17:21:34.313664
144	2022	\N	2021-10-13 17:38:09.092666
145	2022	-255	2021-10-13 17:39:43.67349
146	2022	10010315	2021-10-14 08:31:55.858662
147	2022	-258	2021-10-14 11:09:52.174742
148	2022	-258	2021-10-14 11:10:12.35899
149	2022	-258	2021-10-14 11:10:32.284074
150	2022	-258	2021-10-14 11:11:30.446547
151	2022	-258	2021-10-14 11:16:36.132545
152	2022	-258	2021-10-14 11:17:13.666158
153	2022	-258	2021-10-14 11:17:22.202559
154	2023	10010331	2021-10-26 16:23:09.638964
155	2023	10010331	2021-10-26 16:23:23.338887
156	2022	10010321	2021-10-26 16:34:48.583137
157	2022	10010329	2021-10-27 10:21:25.806882
158	2022	10010329	2021-10-27 10:52:09.591227
159	2030	10010329	2021-10-27 10:52:29.723856
160	2022	-269	2021-10-27 15:28:52.534606
161	2022	-271	2021-10-27 17:20:56.656508
162	2022	10010321	2021-10-27 17:31:15.806982
163	2022	-287	2021-10-28 11:57:13.977372
164	2022	-287	2021-10-28 11:58:18.299021
165	2022	-287	2021-10-28 11:58:31.807419
166	2022	-287	2021-10-28 12:00:20.261224
167	2022	-287	2021-10-28 12:00:47.454955
168	2022	-287	2021-10-28 12:01:27.707951
169	2022	-287	2021-10-28 12:05:03.115068
170	2022	-288	2021-10-28 12:08:53.713654
171	2022	-288	2021-10-28 12:09:44.305519
172	2022	-288	2021-10-28 12:10:25.085394
173	2022	-288	2021-10-28 12:10:58.136841
174	2022	-288	2021-10-28 12:11:41.635688
175	2022	10010315	2021-10-28 12:16:18.103419
176	2022	10010315	2021-10-28 12:16:51.164951
177	2022	-295	2021-10-28 15:05:52.987758
178	2022	-305	2021-10-28 15:07:04.835393
179	2022	-305	2021-10-28 15:08:59.287365
180	2022	-305	2021-10-28 15:27:14.947793
181	2022	-305	2021-10-28 15:29:35.611106
182	2022	-305	2021-10-28 15:30:03.886811
183	2022	-305	2021-10-28 15:31:04.59874
184	2022	-306	2021-10-28 15:34:38.95422
185	2022	-306	2021-10-28 15:34:57.155778
186	2022	-306	2021-10-28 15:36:41.905732
187	2022	-306	2021-10-28 15:38:35.729629
188	2022	-306	2021-10-28 15:38:59.891464
189	2022	-307	2021-10-28 15:39:17.237153
190	2022	-307	2021-10-28 15:40:00.461481
191	2022	-307	2021-10-28 15:40:03.485596
192	2022	-307	2021-10-28 15:40:09.735861
193	2022	-307	2021-10-28 15:40:14.614971
194	2022	10010315	2021-10-28 16:03:45.99562
195	2022	-317	2021-10-28 18:00:00.911769
196	2022	-372	2021-11-22 16:42:22.398456
197	2058	10010646	2021-11-22 16:46:42.246793
198	2058	10010646	2021-11-22 16:51:04.101585
199	2030	-406	2021-11-24 16:04:21.920416
200	2042	-423	2021-11-27 09:26:03.521484
201	2042	-500	2022-01-13 12:15:26.138911
202	2041	\N	2022-01-25 16:46:17.262168
203	2041	\N	2022-01-25 16:53:52.663753
204	2042	-544	2022-01-31 11:51:10.818804
205	2042	-545	2022-01-31 11:56:24.247621
206	2046	-586	2022-02-16 20:11:21.054572
\.


--
-- Data for Name: utente_risposta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.utente_risposta (id, id_utente, id_checklist, id_domanda, punteggio, risposta, entered) FROM stdin;
862	1	2023	410495	0	SI	2021-05-04 09:34:06
1247	1	2022	410222	1	NO	2021-06-14 17:31:21
1248	1	2022	410223	3	NO	2021-06-14 17:31:21
1249	1	2022	410224	1	SI	2021-06-14 17:31:21
1250	1	2022	410225	1	SI	2021-06-14 17:31:21
1251	1	2022	410226	1	SI	2021-06-14 17:31:21
6820	10010286	2022	410208	20	NO	2021-06-15 12:04:55
1141	10000006	2022	410208	20	NO	2021-05-07 12:13:33
1142	10000006	2022	410209	0	SI	2021-05-07 12:13:33
1143	10000006	2022	410210	0	SI	2021-05-07 12:13:33
7859	10010325	2048	416334	6	SI	2021-10-04 15:07:23
7861	10010325	2048	416336	0	SI	2021-10-04 15:07:24
7863	10010325	2048	416338	3	SI	2021-10-04 15:07:24
7865	10010325	2048	416340	5	NO	2021-10-04 15:07:24
7558	-209	2022	410208	0	SI	2021-09-30 14:39:00
7560	-209	2022	410210	0	SI	2021-09-30 14:39:00
1158	10000784	2022	410208	0	SI	2021-05-07 16:16:54
1159	10000784	2022	410209	0	SI	2021-05-07 16:16:54
1160	10000784	2022	410210	2	NO	2021-05-07 16:16:54
1161	10000784	2022	410211	0	SI	2021-05-07 16:16:54
1162	10000784	2022	410212	2	NO	2021-05-07 16:16:54
1163	10000784	2022	410213	0	SI	2021-05-07 16:16:54
1164	10000784	2022	410214	0	SI	2021-05-07 16:16:54
1165	10000784	2022	410215	0	SI	2021-05-07 16:16:54
1166	10000784	2022	410216	1	NO	2021-05-07 16:16:54
1167	10000784	2022	410217	4	NO	2021-05-07 16:16:54
7386	10010300	2064	419562	0	NO	2021-09-22 16:26:08
7387	10010300	2064	419563	0	NO	2021-09-22 16:26:08
1184	10008600	2043	414908	0	SI	2021-05-07 16:33:59
1185	10008600	2043	414909	0	SI	2021-05-07 16:33:59
926	1	2024	410823	3	SI	2021-05-04 12:51:59
927	1	2024	410824	9	SI	2021-05-04 12:51:59
928	1	2024	410825	18	SI	2021-05-04 12:51:59
929	1	2024	410826	30	SI	2021-05-04 12:51:59
1186	10008600	2043	414910	0	SI	2021-05-07 16:33:59
1187	10008600	2043	414911	6	NO	2021-05-07 16:33:59
1188	10000740	2024	410634	2	SI	2021-05-07 17:08:14
1189	10000740	2024	410635	3	SI	2021-05-07 17:08:14
1190	10000740	2024	410636	5	SI	2021-05-07 17:08:14
1191	10000740	2024	410637	0	SI	2021-05-07 17:08:14
1192	10000740	2024	410638	1	NO	2021-05-07 17:08:14
1193	10000740	2024	410639	1	NO	2021-05-07 17:08:14
7388	10010300	2064	419564	6	NO	2021-09-22 16:26:08
7389	10010300	2064	419566	0	NO	2021-09-22 16:26:08
7390	10010300	2064	419568	0	NO	2021-09-22 16:26:09
7391	10010300	2064	419570	10	NO	2021-09-22 16:26:09
7392	10010300	2064	419571	3	NO	2021-09-22 16:26:09
7393	10010300	2064	419572	8	NO	2021-09-22 16:26:09
7394	10010300	2064	419573	4	NO	2021-09-22 16:26:09
7395	10010300	2064	419574	3	NO	2021-09-22 16:26:09
1199	-7	2023	410495	0	SI	2021-05-11 15:19:03
1200	-7	2023	410496	0	NO	2021-05-11 15:19:04
1201	-7	2023	410498	0	SI	2021-05-11 15:19:04
1202	-7	2023	410499	25	NO	2021-05-11 15:19:04
1203	-12	2022	410208	0	SI	2021-05-11 15:35:10
1204	-12	2022	410209	2	NO	2021-05-11 15:35:10
1205	-16	2022	410208	0	SI	2021-05-11 15:40:55
1206	-27	2022	410208	0	SI	2021-05-11 16:53:01
1207	-27	2022	410209	2	NO	2021-05-11 16:53:01
1210	10000740	2022	410208	0	SI	2021-05-11 17:07:44
1211	10000740	2022	410209	2	NO	2021-05-11 17:07:45
1212	10000740	2022	410217	0	SI	2021-05-11 17:07:45
1213	10000740	2022	410218	4	NO	2021-05-11 17:07:45
1214	10000740	2022	410219	0	SI	2021-05-11 17:07:45
1215	-49	2048	416334	6	SI	2021-06-14 17:24:19
1216	-49	2048	416335	0	NO	2021-06-14 17:24:19
1217	-49	2048	416336	0	SI	2021-06-14 17:24:19
1218	-49	2048	416356	0	SI	2021-06-14 17:24:19
1219	-49	2048	416357	0	SI	2021-06-14 17:24:19
1236	1	2022	410208	20	NO	2021-06-14 17:31:21
1237	1	2022	410209	0	SI	2021-06-14 17:31:21
1238	1	2022	410210	2	NO	2021-06-14 17:31:21
1239	1	2022	410211	2	NO	2021-06-14 17:31:21
1240	1	2022	410212	2	NO	2021-06-14 17:31:21
1241	1	2022	410213	0	SI	2021-06-14 17:31:21
1242	1	2022	410217	4	NO	2021-06-14 17:31:21
1243	1	2022	410218	4	NO	2021-06-14 17:31:21
1244	1	2022	410219	2	NO	2021-06-14 17:31:21
1245	1	2022	410220	3	NO	2021-06-14 17:31:21
1246	1	2022	410221	0	SI	2021-06-14 17:31:21
7860	10010325	2048	416335	0	NO	2021-10-04 15:07:23
7862	10010325	2048	416337	3	SI	2021-10-04 15:07:24
7864	10010325	2048	416339	0	SI	2021-10-04 15:07:24
7866	10010325	2048	416341	5	NO	2021-10-04 15:07:24
7559	-209	2022	410209	0	SI	2021-09-30 14:39:00
7561	-209	2022	410211	2	NO	2021-09-30 14:39:00
7223	10010285	2022	410208	0	SI	2021-06-17 15:51:56
7318	10010286	2023	410596	30	NO	2021-09-15 10:16:24
7319	10010286	2023	410597	30	SI	2021-09-15 10:16:24
7320	10010286	2023	410598	0	NO	2021-09-15 10:16:24
7321	10010286	2023	410600	0	SI	2021-09-15 10:16:24
7322	10010286	2023	410601	0	NO	2021-09-15 10:16:24
7323	10010286	2023	410606	8	NO	2021-09-15 10:16:24
7324	10010286	2023	410607	0	SI	2021-09-15 10:16:24
7325	10010286	2023	410608	5	NO	2021-09-15 10:16:24
7326	10010286	2023	410609	5	SI	2021-09-15 10:16:24
7327	10010286	2023	410610	0	NO	2021-09-15 10:16:24
7328	10010286	2023	410611	4	SI	2021-09-15 10:16:24
7329	10010286	2023	410613	5	NO	2021-09-15 10:16:24
7330	10010286	2023	410614	0	SI	2021-09-15 10:16:24
7331	10010286	2023	410615	4	NO	2021-09-15 10:16:24
7332	10010286	2023	410616	0	SI	2021-09-15 10:16:24
7333	10010286	2023	410618	0	NO	2021-09-15 10:16:24
7334	10010286	2023	410619	60	SI	2021-09-15 10:16:24
7335	10010286	2023	410620	0	NO	2021-09-15 10:16:24
7336	10010286	2023	410621	120	SI	2021-09-15 10:16:24
7337	10010286	2023	410622	0	NO	2021-09-15 10:16:24
7338	10010286	2023	410623	1	SI	2021-09-15 10:16:24
7339	10010286	2023	410624	0	NO	2021-09-15 10:16:25
7340	10010286	2023	410625	3	SI	2021-09-15 10:16:25
7341	10010286	2023	410626	0	NO	2021-09-15 10:16:25
7342	10010286	2023	410628	250	SI	2021-09-15 10:16:25
7343	10010286	2023	410629	0	NO	2021-09-15 10:16:25
7344	10010286	2023	410630	25	SI	2021-09-15 10:16:25
7345	10010286	2023	410631	0	NO	2021-09-15 10:16:25
7346	10010286	2023	420831	0	SI	2021-09-15 10:16:25
7347	10010286	2023	420832	0	NO	2021-09-15 10:16:25
7348	10010286	2023	420833	1	SI	2021-09-15 10:16:25
7349	10010286	2023	420834	0	NO	2021-09-15 10:16:25
7350	10010286	2023	420835	1	SI	2021-09-15 10:16:25
7351	10010286	2023	420836	0	NO	2021-09-15 10:16:25
7352	10010286	2023	420837	3	SI	2021-09-15 10:16:25
7353	10010286	2023	420838	0	NO	2021-09-15 10:16:25
7354	10010286	2023	420839	3	SI	2021-09-15 10:16:25
7355	10010286	2023	420840	0	NO	2021-09-15 10:16:25
7356	10010286	2023	420841	12	SI	2021-09-15 10:16:25
7357	10010286	2023	420842	0	NO	2021-09-15 10:16:25
7508	10010301	2042	414640	50	NO	2021-09-22 18:06:48
7510	10010301	2042	414644	0	NO	2021-09-22 18:06:49
7512	10010301	2042	414650	5	SI	2021-09-22 18:06:49
7517	10010304	2022	410233	1	SI	2021-09-28 14:51:49
7519	10010304	2022	410235	1	SI	2021-09-28 14:51:49
7454	10010300	2030	411861	0	NO	2021-09-22 16:42:20
7455	10010300	2030	411862	0	NO	2021-09-22 16:42:20
7456	10010300	2030	411863	4	NO	2021-09-22 16:42:20
7457	10010300	2030	411864	3	NO	2021-09-22 16:42:20
7224	10010285	2022	410209	0	SI	2021-06-17 15:51:56
7458	10010300	2030	411865	25	NO	2021-09-22 16:42:20
7459	10010300	2030	411866	0	NO	2021-09-22 16:42:20
7460	10010300	2030	412052	4	NO	2021-09-22 16:42:20
7461	10010300	2030	412054	0	NO	2021-09-22 16:42:20
7462	10010300	2030	412055	0	NO	2021-09-22 16:42:21
7463	10010300	2030	412056	0	NO	2021-09-22 16:42:21
7464	10010300	2030	412057	0	NO	2021-09-22 16:42:21
7465	10010300	2030	412058	0	NO	2021-09-22 16:42:21
7466	10010300	2058	418071	13	NO	2021-09-22 17:02:54
1704	10010280	2022	410208	0	SI	2021-06-15 10:40:25
1705	10010280	2022	410209	0	SI	2021-06-15 10:40:25
1706	10010280	2022	410210	2	NO	2021-06-15 10:40:25
1707	10010280	2022	410211	2	NO	2021-06-15 10:40:25
1708	10010280	2022	410212	0	SI	2021-06-15 10:40:25
1709	10010280	2022	410213	0	SI	2021-06-15 10:40:25
1710	10010280	2022	410214	1	NO	2021-06-15 10:40:25
7467	10010300	2058	418073	6	NO	2021-09-22 17:02:54
7468	10010300	2058	418074	8	NO	2021-09-22 17:02:54
7469	10010300	2058	418075	0	NO	2021-09-22 17:02:54
7470	10010300	2058	418076	4	NO	2021-09-22 17:02:54
7471	10010300	2058	418077	0	NO	2021-09-22 17:02:54
7472	10010300	2058	418080	10	NO	2021-09-22 17:02:54
7473	10010300	2058	418081	0	SI	2021-09-22 17:02:54
7474	10010300	2058	418082	4	NO	2021-09-22 17:02:54
7475	10010300	2058	418083	9	NO	2021-09-22 17:02:54
7476	10010300	2058	418084	5	NO	2021-09-22 17:02:54
7477	10010300	2058	418085	1	SI	2021-09-22 17:02:55
7478	10010300	2058	418086	0	NO	2021-09-22 17:02:55
7479	10010300	2058	418087	0	NO	2021-09-22 17:02:55
7480	10010300	2058	418088	0	NO	2021-09-22 17:02:55
7481	10010300	2058	418089	0	NO	2021-09-22 17:02:55
7482	10010300	2058	418092	4	NO	2021-09-22 17:02:55
7483	10010300	2058	418093	1	NO	2021-09-22 17:02:55
7484	10010300	2058	418095	4	NO	2021-09-22 17:02:55
7485	10010300	2058	418098	0	NO	2021-09-22 17:02:55
7486	10010300	2058	418099	30	NO	2021-09-22 17:02:55
7487	10010300	2058	418100	0	NO	2021-09-22 17:02:55
7488	10010300	2058	418101	17	NO	2021-09-22 17:02:55
7489	10010300	2058	418102	0	NO	2021-09-22 17:02:55
7490	10010300	2058	418103	0	NO	2021-09-22 17:02:55
7491	10010300	2058	418104	0	NO	2021-09-22 17:02:55
7492	10010300	2058	418105	0	NO	2021-09-22 17:02:55
7493	10010300	2058	418106	0	NO	2021-09-22 17:02:55
7494	10010300	2058	418107	0	NO	2021-09-22 17:02:55
7495	10010300	2058	418409	100	SI	2021-09-22 17:02:56
7496	10010300	2058	418410	250	SI	2021-09-22 17:02:56
7497	10010300	2058	418411	25	SI	2021-09-22 17:02:56
7498	10010300	2058	418412	25	SI	2021-09-22 17:02:56
7499	10010300	2058	418413	0	NO	2021-09-22 17:02:56
7567	10010318	2022	410213	1	NO	2021-09-30 15:47:24
7518	10010304	2022	410234	1	SI	2021-09-28 14:51:49
7520	10010304	2022	410236	0	SI	2021-09-28 14:51:49
7867	10010325	2048	416343	3	NO	2021-10-04 15:07:24
7869	10010325	2048	416345	6	SI	2021-10-04 15:07:24
7871	10010325	2048	416347	5	NO	2021-10-04 15:07:24
7873	10010325	2048	416349	5	NO	2021-10-04 15:07:24
7875	10010325	2048	416351	5	SI	2021-10-04 15:07:24
7877	10010325	2048	416353	5	NO	2021-10-04 15:07:24
7879	10010325	2048	416355	0	SI	2021-10-04 15:07:24
7868	10010325	2048	416344	0	SI	2021-10-04 15:07:24
7870	10010325	2048	416346	10	NO	2021-10-04 15:07:24
7509	10010301	2042	414643	6	NO	2021-09-22 18:06:48
7511	10010301	2042	414649	6	SI	2021-09-22 18:06:49
7513	10010301	2042	414651	22	NO	2021-09-22 18:06:49
7872	10010325	2048	416348	0	NO	2021-10-04 15:07:24
7874	10010325	2048	416350	0	SI	2021-10-04 15:07:24
7876	10010325	2048	416352	4	NO	2021-10-04 15:07:24
7878	10010325	2048	416354	0	SI	2021-10-04 15:07:24
7880	10010325	2048	416356	0	SI	2021-10-04 15:07:24
7225	10010286	2023	410495	400	NO	2021-09-15 10:16:19
7226	10010286	2023	410496	3	SI	2021-09-15 10:16:19
7227	10010286	2023	410497	0	NO	2021-09-15 10:16:19
7228	10010286	2023	410498	0	SI	2021-09-15 10:16:19
7229	10010286	2023	410499	25	NO	2021-09-15 10:16:19
7230	10010286	2023	410500	0	SI	2021-09-15 10:16:19
7231	10010286	2023	410501	25	NO	2021-09-15 10:16:19
7232	10010286	2023	410502	0	SI	2021-09-15 10:16:19
7233	10010286	2023	410503	25	NO	2021-09-15 10:16:19
7234	10010286	2023	410504	0	SI	2021-09-15 10:16:19
7235	10010286	2023	410505	0	SI	2021-09-15 10:16:19
7236	10010286	2023	410506	0	SI	2021-09-15 10:16:20
7237	10010286	2023	410507	25	NO	2021-09-15 10:16:20
7238	10010286	2023	410508	6	NO	2021-09-15 10:16:20
7239	10010286	2023	410509	0	SI	2021-09-15 10:16:20
7240	10010286	2023	410510	10	NO	2021-09-15 10:16:20
7241	10010286	2023	410512	3	SI	2021-09-15 10:16:20
7242	10010286	2023	410513	0	NO	2021-09-15 10:16:20
7243	10010286	2023	410514	0	SI	2021-09-15 10:16:20
7244	10010286	2023	410515	0	NO	2021-09-15 10:16:20
7245	10010286	2023	410516	0	SI	2021-09-15 10:16:20
7246	10010286	2023	410517	20	NO	2021-09-15 10:16:20
7247	10010286	2023	410518	0	SI	2021-09-15 10:16:20
7248	10010286	2023	410519	20	NO	2021-09-15 10:16:20
7249	10010286	2023	410520	0	SI	2021-09-15 10:16:20
7250	10010286	2023	410521	20	NO	2021-09-15 10:16:20
7251	10010286	2023	410522	0	SI	2021-09-15 10:16:20
7252	10010286	2023	410523	25	NO	2021-09-15 10:16:20
7253	10010286	2023	410524	0	SI	2021-09-15 10:16:20
7254	10010286	2023	410525	25	NO	2021-09-15 10:16:20
7255	10010286	2023	410526	0	SI	2021-09-15 10:16:20
7256	10010286	2023	410527	20	NO	2021-09-15 10:16:21
7257	10010286	2023	410528	0	SI	2021-09-15 10:16:21
7258	10010286	2023	410529	8	NO	2021-09-15 10:16:21
7259	10010286	2023	410530	0	SI	2021-09-15 10:16:21
7260	10010286	2023	410531	8	NO	2021-09-15 10:16:21
7261	10010286	2023	410532	0	SI	2021-09-15 10:16:21
7262	10010286	2023	410533	9	NO	2021-09-15 10:16:21
7263	10010286	2023	410534	4	SI	2021-09-15 10:16:21
7264	10010286	2023	410535	0	NO	2021-09-15 10:16:21
7265	10010286	2023	410536	0	SI	2021-09-15 10:16:21
7266	10010286	2023	410537	7	NO	2021-09-15 10:16:21
7267	10010286	2023	410538	0	SI	2021-09-15 10:16:21
7268	10010286	2023	410539	6	NO	2021-09-15 10:16:21
7269	10010286	2023	410541	0	SI	2021-09-15 10:16:21
7270	10010286	2023	410542	6	NO	2021-09-15 10:16:21
7271	10010286	2023	410543	0	SI	2021-09-15 10:16:21
7272	10010286	2023	410545	50	NO	2021-09-15 10:16:21
7273	10010286	2023	410546	0	SI	2021-09-15 10:16:21
7274	10010286	2023	410547	12	NO	2021-09-15 10:16:21
7275	10010286	2023	410548	8	SI	2021-09-15 10:16:21
7276	10010286	2023	410549	7	NO	2021-09-15 10:16:21
7277	10010286	2023	410550	3	SI	2021-09-15 10:16:22
7278	10010286	2023	410551	12	NO	2021-09-15 10:16:22
7279	10010286	2023	410553	4	SI	2021-09-15 10:16:22
7280	10010286	2023	410554	15	NO	2021-09-15 10:16:22
7281	10010286	2023	410556	0	SI	2021-09-15 10:16:22
7282	10010286	2023	410557	8	NO	2021-09-15 10:16:22
7283	10010286	2023	410558	10	SI	2021-09-15 10:16:22
7284	10010286	2023	410559	0	NO	2021-09-15 10:16:22
7285	10010286	2023	410561	0	SI	2021-09-15 10:16:22
7286	10010286	2023	410562	3	NO	2021-09-15 10:16:22
7287	10010286	2023	410563	0	SI	2021-09-15 10:16:22
7288	10010286	2023	410564	3	NO	2021-09-15 10:16:22
7289	10010286	2023	410565	0	SI	2021-09-15 10:16:22
7290	10010286	2023	410566	3	NO	2021-09-15 10:16:22
7291	10010286	2023	410567	0	SI	2021-09-15 10:16:22
7292	10010286	2023	410568	3	NO	2021-09-15 10:16:22
7293	10010286	2023	410569	0	SI	2021-09-15 10:16:22
7294	10010286	2023	410570	3	NO	2021-09-15 10:16:22
7295	10010286	2023	410571	0	SI	2021-09-15 10:16:22
7296	10010286	2023	410572	3	NO	2021-09-15 10:16:22
7297	10010286	2023	410573	0	SI	2021-09-15 10:16:23
7298	10010286	2023	410574	3	NO	2021-09-15 10:16:23
7299	10010286	2023	410575	5	SI	2021-09-15 10:16:23
7300	10010286	2023	410576	0	NO	2021-09-15 10:16:23
7301	10010286	2023	410577	1	SI	2021-09-15 10:16:23
7302	10010286	2023	410578	0	NO	2021-09-15 10:16:23
7303	10010286	2023	410579	1	SI	2021-09-15 10:16:23
7304	10010286	2023	410580	0	NO	2021-09-15 10:16:23
7305	10010286	2023	410581	1	SI	2021-09-15 10:16:23
7306	10010286	2023	410582	0	NO	2021-09-15 10:16:23
7307	10010286	2023	410583	1	SI	2021-09-15 10:16:23
7308	10010286	2023	410584	0	NO	2021-09-15 10:16:23
7309	10010286	2023	410585	3	SI	2021-09-15 10:16:23
7310	10010286	2023	410586	0	NO	2021-09-15 10:16:23
7311	10010286	2023	410587	1	SI	2021-09-15 10:16:23
7312	10010286	2023	410588	0	NO	2021-09-15 10:16:23
7313	10010286	2023	410590	5	SI	2021-09-15 10:16:23
7314	10010286	2023	410591	50	NO	2021-09-15 10:16:23
7315	10010286	2023	410592	0	SI	2021-09-15 10:16:23
7316	10010286	2023	410593	17	NO	2021-09-15 10:16:23
7317	10010286	2023	410595	0	SI	2021-09-15 10:16:23
7897	-228	2022	410208	0	SI	2021-10-12 15:02:52
7898	-228	2022	410209	0	SI	2021-10-12 15:02:52
7899	-241	2027	411205	4	SI	2021-10-12 15:54:26
7900	-241	2027	411206	4	SI	2021-10-12 15:54:26
7901	-241	2027	411207	4	SI	2021-10-12 15:54:26
7902	-241	2027	411208	4	SI	2021-10-12 15:54:26
7905	-247	2022	419848	0	SI	2021-10-13 15:27:18
7911	-258	2022	410208	0	SI	2021-10-14 11:17:22
8018	10010331	2023	410495	400	NO	2021-10-26 16:23:23
8019	10010331	2023	410496	0	NO	2021-10-26 16:23:23
8020	10010331	2023	410498	70	NO	2021-10-26 16:23:24
8021	10010331	2023	410499	25	NO	2021-10-26 16:23:24
8022	10010331	2023	410500	25	NO	2021-10-26 16:23:24
8023	10010331	2023	410505	25	NO	2021-10-26 16:23:24
8024	10010331	2023	410508	6	NO	2021-10-26 16:23:24
8025	10010331	2023	410509	6	NO	2021-10-26 16:23:24
8026	10010331	2023	410510	10	NO	2021-10-26 16:23:24
8027	10010331	2023	410512	0	NO	2021-10-26 16:23:24
8028	10010331	2023	410514	5	NO	2021-10-26 16:23:24
8029	10010331	2023	410515	0	NO	2021-10-26 16:23:24
8030	10010331	2023	410516	20	NO	2021-10-26 16:23:24
8031	10010331	2023	410518	20	NO	2021-10-26 16:23:24
8032	10010331	2023	410520	20	NO	2021-10-26 16:23:24
8033	10010331	2023	410522	25	NO	2021-10-26 16:23:24
8034	10010331	2023	410526	20	NO	2021-10-26 16:23:24
8035	10010331	2023	410528	8	NO	2021-10-26 16:23:24
8036	10010331	2023	410530	8	NO	2021-10-26 16:23:24
8037	10010331	2023	410532	9	NO	2021-10-26 16:23:24
8038	10010331	2023	410534	0	NO	2021-10-26 16:23:24
8039	10010331	2023	410536	7	NO	2021-10-26 16:23:24
8040	10010331	2023	410538	4	NO	2021-10-26 16:23:24
8041	10010331	2023	410539	6	NO	2021-10-26 16:23:24
8042	10010331	2023	410541	6	NO	2021-10-26 16:23:24
8043	10010331	2023	410543	4	NO	2021-10-26 16:23:24
8044	10010331	2023	410545	50	NO	2021-10-26 16:23:24
8045	10010331	2023	410546	7	NO	2021-10-26 16:23:25
8046	10010331	2023	410547	12	NO	2021-10-26 16:23:25
8047	10010331	2023	410548	0	NO	2021-10-26 16:23:25
8048	10010331	2023	410549	7	NO	2021-10-26 16:23:25
8049	10010331	2023	410550	0	NO	2021-10-26 16:23:25
8050	10010331	2023	410551	12	NO	2021-10-26 16:23:25
8051	10010331	2023	410553	0	NO	2021-10-26 16:23:25
8052	10010331	2023	410554	15	NO	2021-10-26 16:23:25
8053	10010331	2023	410556	10	NO	2021-10-26 16:23:25
8054	10010331	2023	410557	0	SI	2021-10-26 16:23:25
8055	10010331	2023	410558	0	NO	2021-10-26 16:23:25
8056	10010331	2023	410561	3	NO	2021-10-26 16:23:25
8057	10010331	2023	410562	3	NO	2021-10-26 16:23:25
8058	10010331	2023	410563	3	NO	2021-10-26 16:23:25
8059	10010331	2023	410564	3	NO	2021-10-26 16:23:25
8060	10010331	2023	410565	3	NO	2021-10-26 16:23:25
8061	10010331	2023	410566	3	NO	2021-10-26 16:23:25
8062	10010331	2023	410567	3	NO	2021-10-26 16:23:25
8063	10010331	2023	410568	3	NO	2021-10-26 16:23:25
8064	10010331	2023	410569	3	NO	2021-10-26 16:23:25
8065	10010331	2023	410570	3	NO	2021-10-26 16:23:25
8066	10010331	2023	410571	0	SI	2021-10-26 16:23:25
8067	10010331	2023	410572	0	SI	2021-10-26 16:23:25
8068	10010331	2023	410573	0	SI	2021-10-26 16:23:26
8069	10010331	2023	410574	0	SI	2021-10-26 16:23:26
8070	10010331	2023	410575	0	NO	2021-10-26 16:23:26
8071	10010331	2023	410576	0	NO	2021-10-26 16:23:26
8072	10010331	2023	410577	0	NO	2021-10-26 16:23:26
8073	10010331	2023	410578	0	NO	2021-10-26 16:23:26
8074	10010331	2023	410579	0	NO	2021-10-26 16:23:26
8075	10010331	2023	410580	0	NO	2021-10-26 16:23:26
8076	10010331	2023	410581	0	NO	2021-10-26 16:23:26
8077	10010331	2023	410582	0	NO	2021-10-26 16:23:26
8078	10010331	2023	410583	0	NO	2021-10-26 16:23:26
8079	10010331	2023	410584	0	NO	2021-10-26 16:23:26
8080	10010331	2023	410585	0	NO	2021-10-26 16:23:26
8081	10010331	2023	410586	0	NO	2021-10-26 16:23:26
8082	10010331	2023	410587	0	NO	2021-10-26 16:23:26
8083	10010331	2023	410588	0	NO	2021-10-26 16:23:26
8084	10010331	2023	410590	5	SI	2021-10-26 16:23:26
8085	10010331	2023	410591	50	NO	2021-10-26 16:23:26
8086	10010331	2023	410592	9	NO	2021-10-26 16:23:26
8087	10010331	2023	410593	17	NO	2021-10-26 16:23:26
8088	10010331	2023	410595	3	NO	2021-10-26 16:23:26
8089	10010331	2023	410596	30	NO	2021-10-26 16:23:26
8090	10010331	2023	410597	0	NO	2021-10-26 16:23:26
8091	10010331	2023	410598	0	NO	2021-10-26 16:23:26
8092	10010331	2023	410600	12	NO	2021-10-26 16:23:27
8093	10010331	2023	410601	0	NO	2021-10-26 16:23:27
8094	10010331	2023	410606	8	NO	2021-10-26 16:23:27
8095	10010331	2023	410607	5	NO	2021-10-26 16:23:27
8096	10010331	2023	410609	0	NO	2021-10-26 16:23:27
8097	10010331	2023	410611	0	NO	2021-10-26 16:23:27
8098	10010331	2023	410613	5	NO	2021-10-26 16:23:27
8099	10010331	2023	410614	5	NO	2021-10-26 16:23:27
8100	10010331	2023	410615	4	NO	2021-10-26 16:23:27
8101	10010331	2023	410616	3	NO	2021-10-26 16:23:27
8102	10010331	2023	410618	0	NO	2021-10-26 16:23:27
8103	10010331	2023	410619	0	NO	2021-10-26 16:23:27
8104	10010331	2023	410620	0	NO	2021-10-26 16:23:27
8105	10010331	2023	410621	0	NO	2021-10-26 16:23:27
8106	10010331	2023	410622	0	NO	2021-10-26 16:23:27
8107	10010331	2023	410623	0	NO	2021-10-26 16:23:27
8108	10010331	2023	410625	0	NO	2021-10-26 16:23:27
8109	10010331	2023	410628	0	NO	2021-10-26 16:23:27
8110	10010331	2023	410629	0	NO	2021-10-26 16:23:27
8111	10010331	2023	410630	0	NO	2021-10-26 16:23:27
8112	10010331	2023	410631	0	NO	2021-10-26 16:23:27
8113	10010331	2023	420831	15	NO	2021-10-26 16:23:27
8114	10010331	2023	420832	0	NO	2021-10-26 16:23:27
8115	10010331	2023	420833	0	NO	2021-10-26 16:23:27
8116	10010331	2023	420834	0	NO	2021-10-26 16:23:28
8117	10010331	2023	420835	0	NO	2021-10-26 16:23:28
8118	10010331	2023	420836	0	NO	2021-10-26 16:23:28
8119	10010331	2023	420837	0	NO	2021-10-26 16:23:28
8120	10010331	2023	420838	0	NO	2021-10-26 16:23:28
8121	10010331	2023	420839	0	NO	2021-10-26 16:23:28
8122	10010331	2023	420841	0	NO	2021-10-26 16:23:28
8123	10010331	2023	420842	0	NO	2021-10-26 16:23:28
8350	-406	2030	411847	0	SI	2021-11-24 16:04:22
8351	-406	2030	411848	0	SI	2021-11-24 16:04:22
8352	-406	2030	411849	0	SI	2021-11-24 16:04:22
8353	-406	2030	411850	0	NO	2021-11-24 16:04:22
8354	-406	2030	411852	0	SI	2021-11-24 16:04:22
8355	-406	2030	411853	0	SI	2021-11-24 16:04:22
8356	-406	2030	411854	10	NO	2021-11-24 16:04:22
8357	-406	2030	411856	8	NO	2021-11-24 16:04:22
8358	-406	2030	411857	0	SI	2021-11-24 16:04:22
8359	-406	2030	411858	6	SI	2021-11-24 16:04:22
8360	-406	2030	411859	0	SI	2021-11-24 16:04:22
8361	-406	2030	411860	12	SI	2021-11-24 16:04:22
8362	-406	2030	411861	4	SI	2021-11-24 16:04:22
8363	-406	2030	411862	0	NO	2021-11-24 16:04:22
8364	-406	2030	411863	4	NO	2021-11-24 16:04:22
8365	-406	2030	411864	3	NO	2021-11-24 16:04:22
8366	-406	2030	411865	0	SI	2021-11-24 16:04:22
8367	-406	2030	411866	2	SI	2021-11-24 16:04:22
8368	-406	2030	411867	10	NO	2021-11-24 16:04:22
8369	-406	2030	411868	0	SI	2021-11-24 16:04:22
8370	-406	2030	411869	0	SI	2021-11-24 16:04:22
8371	-406	2030	411870	3	SI	2021-11-24 16:04:22
8372	-406	2030	411871	22	NO	2021-11-24 16:04:22
8373	-406	2030	411873	0	SI	2021-11-24 16:04:22
8374	-406	2030	411874	17	SI	2021-11-24 16:04:22
8375	-406	2030	411875	0	NO	2021-11-24 16:04:22
8376	-406	2030	411878	0	SI	2021-11-24 16:04:22
8377	-406	2030	411879	2	NO	2021-11-24 16:04:22
8378	-406	2030	411880	0	SI	2021-11-24 16:04:22
8379	-406	2030	411881	4	NO	2021-11-24 16:04:22
8380	-406	2030	411882	0	SI	2021-11-24 16:04:22
8381	-406	2030	411883	0	NO	2021-11-24 16:04:22
8382	-406	2030	411884	0	NO	2021-11-24 16:04:22
8383	-406	2030	411888	0	NO	2021-11-24 16:04:22
8384	-406	2030	411891	0	SI	2021-11-24 16:04:22
8385	-406	2030	411892	0	SI	2021-11-24 16:04:22
8386	-406	2030	411893	0	SI	2021-11-24 16:04:22
8387	-406	2030	411894	0	SI	2021-11-24 16:04:22
8388	-406	2030	411895	2	NO	2021-11-24 16:04:22
8389	-406	2030	411896	0	SI	2021-11-24 16:04:22
8390	-406	2030	411897	0	SI	2021-11-24 16:04:22
8391	-406	2030	411898	2	SI	2021-11-24 16:04:22
8392	-406	2030	411899	0	SI	2021-11-24 16:04:22
8393	-406	2030	411900	0	SI	2021-11-24 16:04:22
8394	-406	2030	411901	0	SI	2021-11-24 16:04:22
8395	-406	2030	411902	0	SI	2021-11-24 16:04:22
8396	-406	2030	411903	3	NO	2021-11-24 16:04:22
8397	-406	2030	411906	0	NO	2021-11-24 16:04:22
8398	-406	2030	411908	4	SI	2021-11-24 16:04:22
8399	-406	2030	411909	0	SI	2021-11-24 16:04:22
8400	-406	2030	411910	0	SI	2021-11-24 16:04:22
8401	-406	2030	411911	0	SI	2021-11-24 16:04:22
8402	-406	2030	411912	4	SI	2021-11-24 16:04:22
8403	-406	2030	411913	6	SI	2021-11-24 16:04:22
8404	-406	2030	411914	2	SI	2021-11-24 16:04:22
8405	-406	2030	411915	0	SI	2021-11-24 16:04:22
8406	-406	2030	411916	8	NO	2021-11-24 16:04:22
8407	-406	2030	411917	8	NO	2021-11-24 16:04:22
8408	-406	2030	411918	8	NO	2021-11-24 16:04:22
8409	-406	2030	411919	0	NO	2021-11-24 16:04:22
8410	-406	2030	411920	3	SI	2021-11-24 16:04:22
8411	-406	2030	411921	7	SI	2021-11-24 16:04:22
8412	-406	2030	411922	5	NO	2021-11-24 16:04:22
8413	-406	2030	411930	0	NO	2021-11-24 16:04:22
8414	-406	2030	411936	0	NO	2021-11-24 16:04:22
8415	-406	2030	411938	0	NO	2021-11-24 16:04:22
8416	-406	2030	411940	0	NO	2021-11-24 16:04:22
8417	-406	2030	411941	12	NO	2021-11-24 16:04:22
8418	-406	2030	411942	12	SI	2021-11-24 16:04:22
8419	-406	2030	411943	0	SI	2021-11-24 16:04:22
8420	-406	2030	411944	0	SI	2021-11-24 16:04:22
8421	-406	2030	411945	0	SI	2021-11-24 16:04:22
8422	-406	2030	411946	0	SI	2021-11-24 16:04:22
8423	-406	2030	411947	12	SI	2021-11-24 16:04:22
8424	-406	2030	411948	5	NO	2021-11-24 16:04:22
8425	-406	2030	411950	0	SI	2021-11-24 16:04:22
8426	-406	2030	411951	0	NO	2021-11-24 16:04:22
8427	-406	2030	411952	12	SI	2021-11-24 16:04:22
8428	-406	2030	411953	0	NO	2021-11-24 16:04:22
8429	-406	2030	411954	0	NO	2021-11-24 16:04:22
8430	-406	2030	411955	0	SI	2021-11-24 16:04:22
8431	-406	2030	411956	0	SI	2021-11-24 16:04:22
8432	-406	2030	411957	0	SI	2021-11-24 16:04:22
8433	-406	2030	411958	0	SI	2021-11-24 16:04:22
8434	-406	2030	411959	9	SI	2021-11-24 16:04:22
8435	-406	2030	411960	2	SI	2021-11-24 16:04:22
8436	-406	2030	411961	12	NO	2021-11-24 16:04:22
8437	-406	2030	411962	0	SI	2021-11-24 16:04:22
8438	-406	2030	411963	0	NO	2021-11-24 16:04:22
8439	-406	2030	411966	2	SI	2021-11-24 16:04:22
8440	-406	2030	411967	0	NO	2021-11-24 16:04:22
8441	-406	2030	411969	0	SI	2021-11-24 16:04:22
8442	-406	2030	411970	9	NO	2021-11-24 16:04:22
8443	-406	2030	411971	10	SI	2021-11-24 16:04:22
8444	-406	2030	411972	10	SI	2021-11-24 16:04:22
8445	-406	2030	411973	15	SI	2021-11-24 16:04:22
8446	-406	2030	411974	2	SI	2021-11-24 16:04:22
8447	-406	2030	411975	0	SI	2021-11-24 16:04:22
8448	-406	2030	411976	3	NO	2021-11-24 16:04:22
8449	-406	2030	411977	2	SI	2021-11-24 16:04:22
8450	-406	2030	411978	4	SI	2021-11-24 16:04:22
8451	-406	2030	411979	7	SI	2021-11-24 16:04:22
8452	-406	2030	411980	15	SI	2021-11-24 16:04:22
8453	-406	2030	411981	30	SI	2021-11-24 16:04:22
8454	-406	2030	411983	8	NO	2021-11-24 16:04:22
8455	-406	2030	411988	0	SI	2021-11-24 16:04:22
8456	-406	2030	411989	0	SI	2021-11-24 16:04:22
8457	-406	2030	411990	8	NO	2021-11-24 16:04:22
8458	-406	2030	411991	6	NO	2021-11-24 16:04:22
8459	-406	2030	411992	12	SI	2021-11-24 16:04:22
8460	-406	2030	411993	5	SI	2021-11-24 16:04:22
8461	-406	2030	411995	0	SI	2021-11-24 16:04:22
8462	-406	2030	411996	0	SI	2021-11-24 16:04:22
8463	-406	2030	411997	0	SI	2021-11-24 16:04:22
8464	-406	2030	411998	25	NO	2021-11-24 16:04:22
8465	-406	2030	412001	0	SI	2021-11-24 16:04:22
8466	-406	2030	412002	0	SI	2021-11-24 16:04:22
8467	-406	2030	412003	0	SI	2021-11-24 16:04:22
8468	-406	2030	412004	0	SI	2021-11-24 16:04:22
8469	-406	2030	412005	0	SI	2021-11-24 16:04:22
8470	-406	2030	412006	2	SI	2021-11-24 16:04:22
8471	-406	2030	412007	0	SI	2021-11-24 16:04:22
8472	-406	2030	412008	0	NO	2021-11-24 16:04:22
8473	-406	2030	412010	1	NO	2021-11-24 16:04:22
8474	-406	2030	412012	5	NO	2021-11-24 16:04:22
8475	-406	2030	412013	0	SI	2021-11-24 16:04:22
8476	-406	2030	412014	0	SI	2021-11-24 16:04:22
8477	-406	2030	412015	0	SI	2021-11-24 16:04:22
8478	-406	2030	412016	0	SI	2021-11-24 16:04:22
8479	-406	2030	412018	0	SI	2021-11-24 16:04:22
8480	-406	2030	412019	2	SI	2021-11-24 16:04:22
8481	-406	2030	412020	0	SI	2021-11-24 16:04:22
8482	-406	2030	412021	10	NO	2021-11-24 16:04:22
8483	-406	2030	412028	0	SI	2021-11-24 16:04:22
8484	-406	2030	412029	0	SI	2021-11-24 16:04:22
8485	-406	2030	412030	2	NO	2021-11-24 16:04:22
8486	-406	2030	412033	0	SI	2021-11-24 16:04:22
8487	-406	2030	412034	0	SI	2021-11-24 16:04:22
8488	-406	2030	412035	3	SI	2021-11-24 16:04:22
8489	-406	2030	412036	10	SI	2021-11-24 16:04:22
8490	-406	2030	412037	0	SI	2021-11-24 16:04:22
8491	-406	2030	412038	3	SI	2021-11-24 16:04:22
8492	-406	2030	412039	2	SI	2021-11-24 16:04:22
8493	-406	2030	412041	20	SI	2021-11-24 16:04:22
8494	-406	2030	412042	40	SI	2021-11-24 16:04:22
8495	-406	2030	412043	0	NO	2021-11-24 16:04:22
8496	-406	2030	412044	0	NO	2021-11-24 16:04:22
8497	-406	2030	412045	55	SI	2021-11-24 16:04:22
8498	-406	2030	412046	0	NO	2021-11-24 16:04:22
8499	-406	2030	412047	35	SI	2021-11-24 16:04:22
8500	-406	2030	412048	25	SI	2021-11-24 16:04:22
8501	-406	2030	412049	15	SI	2021-11-24 16:04:22
8502	-406	2030	412050	0	SI	2021-11-24 16:04:22
8503	-406	2030	412051	0	SI	2021-11-24 16:04:22
8504	-406	2030	412052	0	SI	2021-11-24 16:04:22
8505	-406	2030	412054	0	NO	2021-11-24 16:04:22
8506	-406	2030	412055	0	NO	2021-11-24 16:04:22
8507	-406	2030	412056	25	SI	2021-11-24 16:04:22
8508	-406	2030	412057	25	SI	2021-11-24 16:04:22
8509	-406	2030	412058	25	SI	2021-11-24 16:04:22
8510	-423	2042	414640	0	SI	2021-11-27 09:26:04
8511	-423	2042	414641	6	SI	2021-11-27 09:26:04
8512	-423	2042	414643	6	NO	2021-11-27 09:26:04
8513	-423	2042	414644	2	SI	2021-11-27 09:26:04
8514	-423	2042	414645	7	NO	2021-11-27 09:26:04
8515	-423	2042	414646	0	SI	2021-11-27 09:26:04
8516	-423	2042	414647	0	NO	2021-11-27 09:26:04
8517	-423	2042	414648	0	NO	2021-11-27 09:26:04
8518	-423	2042	414649	0	NO	2021-11-27 09:26:04
8519	-423	2042	414650	0	NO	2021-11-27 09:26:04
8520	-423	2042	414651	22	NO	2021-11-27 09:26:04
8521	-423	2042	414652	8	NO	2021-11-27 09:26:04
8522	-423	2042	414667	5	NO	2021-11-27 09:26:04
8523	-423	2042	414672	0	NO	2021-11-27 09:26:04
8524	-423	2042	414673	22	NO	2021-11-27 09:26:04
8525	-423	2042	414674	8	NO	2021-11-27 09:26:04
8526	-423	2042	414688	5	NO	2021-11-27 09:26:04
8527	-423	2042	414693	0	NO	2021-11-27 09:26:04
8528	-423	2042	414695	0	SI	2021-11-27 09:26:04
8529	-423	2042	414696	2	NO	2021-11-27 09:26:04
8530	-423	2042	414697	0	SI	2021-11-27 09:26:04
8531	-423	2042	414698	4	NO	2021-11-27 09:26:04
8532	-423	2042	414699	5	NO	2021-11-27 09:26:04
8533	-423	2042	414700	0	NO	2021-11-27 09:26:04
8534	-423	2042	414701	1	SI	2021-11-27 09:26:04
8535	-423	2042	414702	0	SI	2021-11-27 09:26:04
8536	-423	2042	414703	3	SI	2021-11-27 09:26:04
8537	-423	2042	414704	0	NO	2021-11-27 09:26:04
8538	-423	2042	414705	1	SI	2021-11-27 09:26:04
8539	-423	2042	414706	0	SI	2021-11-27 09:26:04
8540	-423	2042	414707	5	NO	2021-11-27 09:26:04
8541	-423	2042	414708	0	SI	2021-11-27 09:26:04
8542	-423	2042	414709	0	SI	2021-11-27 09:26:04
8543	-423	2042	414710	0	SI	2021-11-27 09:26:04
8544	-423	2042	414711	4	NO	2021-11-27 09:26:04
8545	-423	2042	414712	0	SI	2021-11-27 09:26:04
8546	-423	2042	414713	0	SI	2021-11-27 09:26:04
8547	-423	2042	414714	0	SI	2021-11-27 09:26:04
8548	-423	2042	414715	0	NO	2021-11-27 09:26:04
8549	-423	2042	414717	0	NO	2021-11-27 09:26:04
8550	-423	2042	414718	0	SI	2021-11-27 09:26:04
8551	-423	2042	414719	0	SI	2021-11-27 09:26:04
8552	-423	2042	414720	0	SI	2021-11-27 09:26:04
8553	-423	2042	414721	0	SI	2021-11-27 09:26:04
8554	-423	2042	414722	10	NO	2021-11-27 09:26:04
8555	-423	2042	414723	0	SI	2021-11-27 09:26:04
8556	-423	2042	414726	0	NO	2021-11-27 09:26:04
8557	-423	2042	414728	0	NO	2021-11-27 09:26:04
8558	-423	2042	414731	0	NO	2021-11-27 09:26:04
8559	-423	2042	414732	2	NO	2021-11-27 09:26:04
8560	-423	2042	414733	0	NO	2021-11-27 09:26:04
8561	-423	2042	414734	5	NO	2021-11-27 09:26:04
8562	-423	2042	414736	0	NO	2021-11-27 09:26:04
8563	-423	2042	414741	0	NO	2021-11-27 09:26:04
8564	-423	2042	414742	3	NO	2021-11-27 09:26:04
8565	-423	2042	414743	2	NO	2021-11-27 09:26:04
8566	-423	2042	414745	0	NO	2021-11-27 09:26:04
8567	-423	2042	414746	8	NO	2021-11-27 09:26:04
8568	-423	2042	414748	2	NO	2021-11-27 09:26:04
8569	-423	2042	414749	18	NO	2021-11-27 09:26:04
8570	-423	2042	414750	0	NO	2021-11-27 09:26:04
8571	-423	2042	414751	0	SI	2021-11-27 09:26:04
8572	-423	2042	414752	8	NO	2021-11-27 09:26:04
8573	-423	2042	414754	0	NO	2021-11-27 09:26:04
8574	-423	2042	414755	0	NO	2021-11-27 09:26:04
8575	-423	2042	414757	0	NO	2021-11-27 09:26:04
8576	-423	2042	414758	0	SI	2021-11-27 09:26:04
8577	-423	2042	414759	0	SI	2021-11-27 09:26:04
8578	-423	2042	414760	0	SI	2021-11-27 09:26:04
8579	-423	2042	414761	5	NO	2021-11-27 09:26:04
8580	-423	2042	414762	5	NO	2021-11-27 09:26:04
8581	-423	2042	414763	0	SI	2021-11-27 09:26:04
8582	-423	2042	414764	0	NO	2021-11-27 09:26:04
8583	-423	2042	414765	0	NO	2021-11-27 09:26:04
8584	-423	2042	414766	0	NO	2021-11-27 09:26:04
8585	-423	2042	414767	0	SI	2021-11-27 09:26:04
8586	-423	2042	414768	0	SI	2021-11-27 09:26:04
8587	-423	2042	414769	0	SI	2021-11-27 09:26:04
8588	-423	2042	414770	3	NO	2021-11-27 09:26:04
8589	-423	2042	414771	0	SI	2021-11-27 09:26:04
8590	-423	2042	414772	0	SI	2021-11-27 09:26:04
8591	-423	2042	414773	7	SI	2021-11-27 09:26:04
8592	-423	2042	414774	5	SI	2021-11-27 09:26:04
8593	-423	2042	414775	0	SI	2021-11-27 09:26:04
8594	-423	2042	414776	0	SI	2021-11-27 09:26:04
8595	-423	2042	414777	0	NO	2021-11-27 09:26:04
8596	-423	2042	414778	0	NO	2021-11-27 09:26:04
8597	-423	2042	414779	0	NO	2021-11-27 09:26:04
8598	-423	2042	414780	5	NO	2021-11-27 09:26:04
8599	-423	2042	414781	5	NO	2021-11-27 09:26:04
8600	-423	2042	414782	3	NO	2021-11-27 09:26:04
8601	-423	2042	414783	3	NO	2021-11-27 09:26:04
8602	-423	2042	414784	5	NO	2021-11-27 09:26:04
8603	-423	2042	414785	0	NO	2021-11-27 09:26:04
8604	-423	2042	414786	0	NO	2021-11-27 09:26:04
8605	-423	2042	414787	5	SI	2021-11-27 09:26:04
8606	-423	2042	414788	0	NO	2021-11-27 09:26:04
8607	-423	2042	414789	0	NO	2021-11-27 09:26:04
8608	-423	2042	414790	0	SI	2021-11-27 09:26:04
8609	-423	2042	414791	0	SI	2021-11-27 09:26:04
8610	-423	2042	414792	0	SI	2021-11-27 09:26:04
8611	-423	2042	414793	2	SI	2021-11-27 09:26:04
8612	-423	2042	414794	0	SI	2021-11-27 09:26:04
8613	-423	2042	414795	0	SI	2021-11-27 09:26:04
8614	-423	2042	414796	2	SI	2021-11-27 09:26:04
8615	-423	2042	414797	0	NO	2021-11-27 09:26:04
8616	-423	2042	414798	0	NO	2021-11-27 09:26:04
8617	-423	2042	414804	0	NO	2021-11-27 09:26:04
8618	-423	2042	414805	0	NO	2021-11-27 09:26:04
8619	-423	2042	414806	30	NO	2021-11-27 09:26:04
8620	-423	2042	414807	0	NO	2021-11-27 09:26:04
8621	-423	2042	414808	0	NO	2021-11-27 09:26:04
8622	-423	2042	414809	0	NO	2021-11-27 09:26:04
8623	-423	2042	414810	0	NO	2021-11-27 09:26:04
8624	-423	2042	414811	0	SI	2021-11-27 09:26:04
8625	-423	2042	414812	0	SI	2021-11-27 09:26:04
8626	-423	2042	414813	0	NO	2021-11-27 09:26:04
8627	-423	2042	414814	0	NO	2021-11-27 09:26:04
8628	-423	2042	414815	0	NO	2021-11-27 09:26:04
8629	-423	2042	414816	0	NO	2021-11-27 09:26:04
8630	-423	2042	414817	0	NO	2021-11-27 09:26:04
8631	-423	2042	414818	0	NO	2021-11-27 09:26:04
8632	-423	2042	414820	0	SI	2021-11-27 09:26:04
8633	-423	2042	414821	0	SI	2021-11-27 09:26:04
8634	-423	2042	414822	0	NO	2021-11-27 09:26:04
8635	-423	2042	414823	0	SI	2021-11-27 09:26:04
8636	-423	2042	414824	0	SI	2021-11-27 09:26:04
8637	-423	2042	414825	0	NO	2021-11-27 09:26:04
8638	-423	2042	414826	6	NO	2021-11-27 09:26:04
8639	-423	2042	414830	0	NO	2021-11-27 09:26:04
8640	-423	2042	414832	0	SI	2021-11-27 09:26:04
8641	-423	2042	414833	0	SI	2021-11-27 09:26:04
8642	-423	2042	414834	0	SI	2021-11-27 09:26:04
8643	-423	2042	414835	0	SI	2021-11-27 09:26:04
8644	-423	2042	414838	0	SI	2021-11-27 09:26:04
8645	-423	2042	414839	0	SI	2021-11-27 09:26:04
8646	-423	2042	414840	0	SI	2021-11-27 09:26:04
8647	-423	2042	414841	0	SI	2021-11-27 09:26:04
8648	-423	2042	414842	3	NO	2021-11-27 09:26:04
8649	-423	2042	414843	0	NO	2021-11-27 09:26:04
8650	-423	2042	414844	0	SI	2021-11-27 09:26:04
8651	-423	2042	414845	0	NO	2021-11-27 09:26:04
8652	-423	2042	414847	0	SI	2021-11-27 09:26:04
8653	-423	2042	414848	0	SI	2021-11-27 09:26:04
8654	-423	2042	414849	0	SI	2021-11-27 09:26:04
8655	-423	2042	414850	0	SI	2021-11-27 09:26:04
8656	-423	2042	414851	0	SI	2021-11-27 09:26:04
8657	-423	2042	414852	0	SI	2021-11-27 09:26:04
8658	-423	2042	414853	0	SI	2021-11-27 09:26:04
8659	-423	2042	414855	20	NO	2021-11-27 09:26:04
8660	-423	2042	414856	5	SI	2021-11-27 09:26:04
8661	-423	2042	414857	0	SI	2021-11-27 09:26:04
8662	-423	2042	414858	0	SI	2021-11-27 09:26:04
8663	-423	2042	414859	0	SI	2021-11-27 09:26:04
8664	-423	2042	414860	2	NO	2021-11-27 09:26:04
8665	-423	2042	414861	0	SI	2021-11-27 09:26:04
8666	-423	2042	414862	0	SI	2021-11-27 09:26:04
8667	-423	2042	414863	0	SI	2021-11-27 09:26:04
8668	-423	2042	414865	0	SI	2021-11-27 09:26:04
8669	-423	2042	414866	0	SI	2021-11-27 09:26:04
8670	-423	2042	414867	2	NO	2021-11-27 09:26:04
8671	-423	2042	414870	0	SI	2021-11-27 09:26:04
8672	-423	2042	414871	0	SI	2021-11-27 09:26:04
8673	-423	2042	414872	0	NO	2021-11-27 09:26:04
8674	-423	2042	414873	0	NO	2021-11-27 09:26:04
8675	-423	2042	414874	0	SI	2021-11-27 09:26:04
8676	-423	2042	414875	0	NO	2021-11-27 09:26:04
8677	-423	2042	414876	0	NO	2021-11-27 09:26:04
8678	-423	2042	414878	0	NO	2021-11-27 09:26:04
8679	-423	2042	414880	35	SI	2021-11-27 09:26:04
8680	-423	2042	414881	0	NO	2021-11-27 09:26:04
8681	-423	2042	414882	0	NO	2021-11-27 09:26:04
8682	-423	2042	414883	0	SI	2021-11-27 09:26:04
8683	-423	2042	414884	10	SI	2021-11-27 09:26:04
8684	-423	2042	414885	0	NO	2021-11-27 09:26:04
8685	-423	2042	414887	0	NO	2021-11-27 09:26:04
8686	-423	2042	414889	0	NO	2021-11-27 09:26:04
8687	-423	2042	414893	0	NO	2021-11-27 09:26:04
8688	-423	2042	414898	2	NO	2021-11-27 09:26:04
8689	-423	2042	414899	4	NO	2021-11-27 09:26:04
8690	-423	2042	414901	0	NO	2021-11-27 09:26:04
8691	-423	2042	414902	0	NO	2021-11-27 09:26:04
8692	-423	2042	414903	0	NO	2021-11-27 09:26:04
8693	-423	2042	414904	0	NO	2021-11-27 09:26:04
8694	-423	2042	414905	0	NO	2021-11-27 09:26:04
8695	-423	2042	419819	0	SI	2021-11-27 09:26:04
8696	-423	2042	419820	10	NO	2021-11-27 09:26:04
8697	-423	2042	419821	4	SI	2021-11-27 09:26:04
8698	-423	2042	419822	0	SI	2021-11-27 09:26:04
8699	-423	2042	419823	10	NO	2021-11-27 09:26:04
8700	-423	2042	419824	0	SI	2021-11-27 09:26:04
8701	-423	2042	419825	0	NO	2021-11-27 09:26:04
8702	-423	2042	419859	0	NO	2021-11-27 09:26:04
8703	-500	2042	414640	0	SI	2022-01-13 12:15:26
8704	-500	2042	414641	6	SI	2022-01-13 12:15:26
8705	-500	2042	414643	6	NO	2022-01-13 12:15:26
8706	-500	2042	414644	2	SI	2022-01-13 12:15:26
8707	-500	2042	414645	7	NO	2022-01-13 12:15:26
8708	-500	2042	414646	0	SI	2022-01-13 12:15:26
8709	-500	2042	414647	0	NO	2022-01-13 12:15:26
8710	-500	2042	414648	0	NO	2022-01-13 12:15:26
8711	-500	2042	414649	0	NO	2022-01-13 12:15:26
8712	-500	2042	414650	5	SI	2022-01-13 12:15:26
8713	-500	2042	414651	0	SI	2022-01-13 12:15:26
8714	-500	2042	414652	0	SI	2022-01-13 12:15:26
8715	-500	2042	414653	0	SI	2022-01-13 12:15:26
8716	-500	2042	414654	0	SI	2022-01-13 12:15:26
8717	-500	2042	414655	4	NO	2022-01-13 12:15:26
8718	-500	2042	414656	3	NO	2022-01-13 12:15:26
8719	-500	2042	414657	0	SI	2022-01-13 12:15:26
8720	-500	2042	414658	0	NO	2022-01-13 12:15:26
8721	-500	2042	414659	0	NO	2022-01-13 12:15:26
8722	-500	2042	414660	0	NO	2022-01-13 12:15:26
8723	-500	2042	414661	0	SI	2022-01-13 12:15:26
8724	-500	2042	414662	0	SI	2022-01-13 12:15:26
8725	-500	2042	414663	0	SI	2022-01-13 12:15:26
8726	-500	2042	414664	0	SI	2022-01-13 12:15:26
8727	-500	2042	414665	0	SI	2022-01-13 12:15:26
8728	-500	2042	414666	0	SI	2022-01-13 12:15:26
8729	-500	2042	414667	0	SI	2022-01-13 12:15:26
8730	-500	2042	414668	0	SI	2022-01-13 12:15:26
8731	-500	2042	414669	0	SI	2022-01-13 12:15:26
8732	-500	2042	414670	0	SI	2022-01-13 12:15:26
8733	-500	2042	414671	0	SI	2022-01-13 12:15:26
8734	-500	2042	414672	5	SI	2022-01-13 12:15:26
8735	-500	2042	414673	0	SI	2022-01-13 12:15:26
8736	-500	2042	414674	0	SI	2022-01-13 12:15:26
8737	-500	2042	414675	0	SI	2022-01-13 12:15:26
8738	-500	2042	414676	0	SI	2022-01-13 12:15:26
8739	-500	2042	414677	0	SI	2022-01-13 12:15:26
8740	-500	2042	414678	0	SI	2022-01-13 12:15:26
8741	-500	2042	414679	0	SI	2022-01-13 12:15:26
8742	-500	2042	414680	0	NO	2022-01-13 12:15:26
8743	-500	2042	414681	0	NO	2022-01-13 12:15:26
8744	-500	2042	414682	0	NO	2022-01-13 12:15:26
8745	-500	2042	414683	0	SI	2022-01-13 12:15:26
8746	-500	2042	414684	0	SI	2022-01-13 12:15:26
8747	-500	2042	414685	0	SI	2022-01-13 12:15:26
8748	-500	2042	414686	0	SI	2022-01-13 12:15:26
8749	-500	2042	414687	0	SI	2022-01-13 12:15:26
8750	-500	2042	414688	0	SI	2022-01-13 12:15:26
8751	-500	2042	414689	0	SI	2022-01-13 12:15:26
8752	-500	2042	414690	0	SI	2022-01-13 12:15:26
8753	-500	2042	414691	0	SI	2022-01-13 12:15:26
8754	-500	2042	414692	0	SI	2022-01-13 12:15:26
8755	-500	2042	414693	0	NO	2022-01-13 12:15:26
8756	-500	2042	414695	0	SI	2022-01-13 12:15:26
8757	-500	2042	414696	2	NO	2022-01-13 12:15:26
8758	-500	2042	414697	2	NO	2022-01-13 12:15:26
8759	-500	2042	414701	1	SI	2022-01-13 12:15:26
8760	-500	2042	414702	0	SI	2022-01-13 12:15:26
8761	-500	2042	414703	3	SI	2022-01-13 12:15:26
8762	-500	2042	414704	2	SI	2022-01-13 12:15:26
8763	-500	2042	414705	1	SI	2022-01-13 12:15:26
8764	-500	2042	414706	0	SI	2022-01-13 12:15:26
8765	-500	2042	414707	0	SI	2022-01-13 12:15:26
8766	-500	2042	414708	0	SI	2022-01-13 12:15:26
8767	-500	2042	414709	0	SI	2022-01-13 12:15:26
8768	-500	2042	414710	0	SI	2022-01-13 12:15:26
8769	-500	2042	414711	0	SI	2022-01-13 12:15:26
8770	-500	2042	414712	0	SI	2022-01-13 12:15:26
8771	-500	2042	414713	0	SI	2022-01-13 12:15:26
8772	-500	2042	414714	0	SI	2022-01-13 12:15:26
8773	-500	2042	414715	3	SI	2022-01-13 12:15:26
8774	-500	2042	414716	0	SI	2022-01-13 12:15:26
8775	-500	2042	414717	5	SI	2022-01-13 12:15:26
8776	-500	2042	414718	0	SI	2022-01-13 12:15:26
8777	-500	2042	414719	0	SI	2022-01-13 12:15:26
8778	-500	2042	414720	0	SI	2022-01-13 12:15:26
8779	-500	2042	414721	0	SI	2022-01-13 12:15:26
8780	-500	2042	414722	0	SI	2022-01-13 12:15:26
8781	-500	2042	414723	0	SI	2022-01-13 12:15:26
8782	-500	2042	414726	0	NO	2022-01-13 12:15:26
8783	-500	2042	414728	5	SI	2022-01-13 12:15:26
8784	-500	2042	414730	0	SI	2022-01-13 12:15:26
8785	-500	2042	414731	0	NO	2022-01-13 12:15:26
8786	-500	2042	414732	2	NO	2022-01-13 12:15:26
8787	-500	2042	414733	0	NO	2022-01-13 12:15:26
8788	-500	2042	414734	5	NO	2022-01-13 12:15:26
8789	-500	2042	414736	13	SI	2022-01-13 12:15:26
8790	-500	2042	414737	0	SI	2022-01-13 12:15:26
8791	-500	2042	414738	0	SI	2022-01-13 12:15:26
8792	-500	2042	414739	0	SI	2022-01-13 12:15:26
8793	-500	2042	414740	0	NO	2022-01-13 12:15:26
8794	-500	2042	414741	0	NO	2022-01-13 12:15:26
8795	-500	2042	414742	0	SI	2022-01-13 12:15:26
8796	-500	2042	414743	0	SI	2022-01-13 12:15:26
8797	-500	2042	414744	0	SI	2022-01-13 12:15:26
8798	-500	2042	414745	0	NO	2022-01-13 12:15:26
8799	-500	2042	414746	0	SI	2022-01-13 12:15:26
8800	-500	2042	414747	0	NO	2022-01-13 12:15:26
8801	-500	2042	414748	0	SI	2022-01-13 12:15:26
8802	-500	2042	414749	0	SI	2022-01-13 12:15:26
8803	-500	2042	414750	0	NO	2022-01-13 12:15:26
8804	-500	2042	414751	0	SI	2022-01-13 12:15:26
8805	-500	2042	414752	0	SI	2022-01-13 12:15:26
8806	-500	2042	414753	0	NO	2022-01-13 12:15:26
8807	-500	2042	414754	0	NO	2022-01-13 12:15:26
8808	-500	2042	414755	0	NO	2022-01-13 12:15:26
8809	-500	2042	414757	0	NO	2022-01-13 12:15:26
8810	-500	2042	414758	0	SI	2022-01-13 12:15:26
8811	-500	2042	414759	0	SI	2022-01-13 12:15:26
8812	-500	2042	414760	0	SI	2022-01-13 12:15:26
8813	-500	2042	414761	0	SI	2022-01-13 12:15:26
8814	-500	2042	414762	0	SI	2022-01-13 12:15:26
8815	-500	2042	414763	0	SI	2022-01-13 12:15:26
8816	-500	2042	414764	0	NO	2022-01-13 12:15:26
8817	-500	2042	414765	0	NO	2022-01-13 12:15:26
8818	-500	2042	414766	0	NO	2022-01-13 12:15:26
8819	-500	2042	414767	0	SI	2022-01-13 12:15:26
8820	-500	2042	414768	0	SI	2022-01-13 12:15:26
8821	-500	2042	414769	0	SI	2022-01-13 12:15:26
8822	-500	2042	414770	0	SI	2022-01-13 12:15:26
8823	-500	2042	414771	0	SI	2022-01-13 12:15:26
8824	-500	2042	414772	0	SI	2022-01-13 12:15:26
8825	-500	2042	414773	0	NO	2022-01-13 12:15:26
8826	-500	2042	414774	0	NO	2022-01-13 12:15:26
8827	-500	2042	414775	15	NO	2022-01-13 12:15:26
8828	-500	2042	414785	0	NO	2022-01-13 12:15:26
8829	-500	2042	414786	0	NO	2022-01-13 12:15:26
8830	-500	2042	414787	0	NO	2022-01-13 12:15:26
8831	-500	2042	414789	0	NO	2022-01-13 12:15:26
8832	-500	2042	414790	0	SI	2022-01-13 12:15:26
8833	-500	2042	414791	0	SI	2022-01-13 12:15:26
8834	-500	2042	414792	0	SI	2022-01-13 12:15:26
8835	-500	2042	414793	0	NO	2022-01-13 12:15:26
8836	-500	2042	414794	0	SI	2022-01-13 12:15:26
8837	-500	2042	414795	0	SI	2022-01-13 12:15:26
8838	-500	2042	414796	0	NO	2022-01-13 12:15:26
8839	-500	2042	414797	0	NO	2022-01-13 12:15:27
8840	-500	2042	414798	6	SI	2022-01-13 12:15:27
8841	-500	2042	414799	0	SI	2022-01-13 12:15:27
8842	-500	2042	414800	0	NO	2022-01-13 12:15:27
8843	-500	2042	414801	0	NO	2022-01-13 12:15:27
8844	-500	2042	414802	0	NO	2022-01-13 12:15:27
8845	-500	2042	414804	0	NO	2022-01-13 12:15:27
8846	-500	2042	414805	0	NO	2022-01-13 12:15:27
8847	-500	2042	414806	0	SI	2022-01-13 12:15:27
8848	-500	2042	414807	0	NO	2022-01-13 12:15:27
8849	-500	2042	414808	0	NO	2022-01-13 12:15:27
8850	-500	2042	414809	0	NO	2022-01-13 12:15:27
8851	-500	2042	414810	0	NO	2022-01-13 12:15:27
8852	-500	2042	414811	7	NO	2022-01-13 12:15:27
8853	-500	2042	414812	3	NO	2022-01-13 12:15:27
8854	-500	2042	414813	0	NO	2022-01-13 12:15:27
8855	-500	2042	414814	0	NO	2022-01-13 12:15:27
8856	-500	2042	414815	0	NO	2022-01-13 12:15:27
8857	-500	2042	414816	0	NO	2022-01-13 12:15:27
8858	-500	2042	414817	0	NO	2022-01-13 12:15:27
8859	-500	2042	414818	0	NO	2022-01-13 12:15:27
8860	-500	2042	414820	0	SI	2022-01-13 12:15:27
8861	-500	2042	414821	0	SI	2022-01-13 12:15:27
8862	-500	2042	414822	0	NO	2022-01-13 12:15:27
8863	-500	2042	414823	0	SI	2022-01-13 12:15:27
8864	-500	2042	414824	0	SI	2022-01-13 12:15:27
8865	-500	2042	414825	0	NO	2022-01-13 12:15:27
8866	-500	2042	414826	0	SI	2022-01-13 12:15:27
8867	-500	2042	414827	0	SI	2022-01-13 12:15:27
8868	-500	2042	414828	0	SI	2022-01-13 12:15:27
8869	-500	2042	414829	0	NO	2022-01-13 12:15:27
8870	-500	2042	414830	0	NO	2022-01-13 12:15:27
8871	-500	2042	414832	0	SI	2022-01-13 12:15:27
8872	-500	2042	414833	0	SI	2022-01-13 12:15:27
8873	-500	2042	414834	0	SI	2022-01-13 12:15:27
8874	-500	2042	414835	0	SI	2022-01-13 12:15:27
8875	-500	2042	414838	0	SI	2022-01-13 12:15:27
8876	-500	2042	414839	0	SI	2022-01-13 12:15:27
8877	-500	2042	414840	0	SI	2022-01-13 12:15:27
8878	-500	2042	414841	0	SI	2022-01-13 12:15:27
8879	-500	2042	414842	3	NO	2022-01-13 12:15:27
8880	-500	2042	414843	0	NO	2022-01-13 12:15:27
8881	-500	2042	414844	0	SI	2022-01-13 12:15:27
8882	-500	2042	414845	1	SI	2022-01-13 12:15:27
8883	-500	2042	414846	0	SI	2022-01-13 12:15:27
8884	-500	2042	414847	0	SI	2022-01-13 12:15:27
8885	-500	2042	414848	0	SI	2022-01-13 12:15:27
8886	-500	2042	414849	0	SI	2022-01-13 12:15:27
8887	-500	2042	414850	0	SI	2022-01-13 12:15:27
8888	-500	2042	414851	0	SI	2022-01-13 12:15:27
8889	-500	2042	414852	0	SI	2022-01-13 12:15:27
8890	-500	2042	414853	0	SI	2022-01-13 12:15:27
8891	-500	2042	414855	20	NO	2022-01-13 12:15:27
8892	-500	2042	414856	0	NO	2022-01-13 12:15:27
8893	-500	2042	414857	0	SI	2022-01-13 12:15:27
8894	-500	2042	414858	0	SI	2022-01-13 12:15:27
8895	-500	2042	414859	0	SI	2022-01-13 12:15:27
8896	-500	2042	414860	0	SI	2022-01-13 12:15:27
8897	-500	2042	414861	0	SI	2022-01-13 12:15:27
8898	-500	2042	414862	0	SI	2022-01-13 12:15:27
8899	-500	2042	414863	0	SI	2022-01-13 12:15:27
8900	-500	2042	414865	0	SI	2022-01-13 12:15:27
8901	-500	2042	414866	0	SI	2022-01-13 12:15:27
8902	-500	2042	414867	0	SI	2022-01-13 12:15:27
8903	-500	2042	414868	0	SI	2022-01-13 12:15:27
8904	-500	2042	414869	0	SI	2022-01-13 12:15:27
8905	-500	2042	414870	0	SI	2022-01-13 12:15:27
8906	-500	2042	414871	0	SI	2022-01-13 12:15:27
8907	-500	2042	414872	0	NO	2022-01-13 12:15:27
8908	-500	2042	414873	0	NO	2022-01-13 12:15:27
8909	-500	2042	414874	0	SI	2022-01-13 12:15:27
8910	-500	2042	414875	0	NO	2022-01-13 12:15:27
8911	-500	2042	414876	0	NO	2022-01-13 12:15:27
8912	-500	2042	414878	0	NO	2022-01-13 12:15:27
8913	-500	2042	414880	0	NO	2022-01-13 12:15:27
8914	-500	2042	414881	0	NO	2022-01-13 12:15:27
8915	-500	2042	414882	0	NO	2022-01-13 12:15:27
8916	-500	2042	414883	0	SI	2022-01-13 12:15:27
8917	-500	2042	414884	0	NO	2022-01-13 12:15:27
8918	-500	2042	414885	0	NO	2022-01-13 12:15:27
8919	-500	2042	414887	0	NO	2022-01-13 12:15:27
8920	-500	2042	414889	0	NO	2022-01-13 12:15:27
8921	-500	2042	414893	0	NO	2022-01-13 12:15:27
8922	-500	2042	414898	2	NO	2022-01-13 12:15:27
8923	-500	2042	414899	4	NO	2022-01-13 12:15:27
8924	-500	2042	414901	0	NO	2022-01-13 12:15:27
8925	-500	2042	414902	0	NO	2022-01-13 12:15:27
8926	-500	2042	414903	0	NO	2022-01-13 12:15:27
8927	-500	2042	414904	0	NO	2022-01-13 12:15:27
8928	-500	2042	414905	0	NO	2022-01-13 12:15:27
8929	-500	2042	419819	0	SI	2022-01-13 12:15:27
8930	-500	2042	419820	0	SI	2022-01-13 12:15:27
8931	-500	2042	419821	4	SI	2022-01-13 12:15:27
8932	-500	2042	419822	2	NO	2022-01-13 12:15:27
8933	-500	2042	419823	10	NO	2022-01-13 12:15:27
8934	-500	2042	419824	0	SI	2022-01-13 12:15:27
8935	-500	2042	419825	0	NO	2022-01-13 12:15:27
8936	-500	2042	419859	12	SI	2022-01-13 12:15:27
8937	\N	2041	414344	0	SI	2022-01-25 16:46:17
8938	\N	2041	414345	0	SI	2022-01-25 16:46:17
8939	\N	2041	414346	0	NO	2022-01-25 16:46:17
8940	\N	2041	414347	0	SI	2022-01-25 16:46:17
8941	\N	2041	414348	0	SI	2022-01-25 16:46:17
8942	\N	2041	414349	0	SI	2022-01-25 16:46:17
8943	\N	2041	414350	0	SI	2022-01-25 16:46:17
8944	\N	2041	414351	0	NO	2022-01-25 16:46:17
8945	\N	2041	414356	0	NO	2022-01-25 16:46:17
8946	\N	2041	414357	0	SI	2022-01-25 16:46:17
8947	\N	2041	414358	0	SI	2022-01-25 16:46:17
8948	\N	2041	414359	0	SI	2022-01-25 16:46:17
8949	\N	2041	414360	0	SI	2022-01-25 16:46:17
8950	\N	2041	414361	0	NO	2022-01-25 16:46:17
8951	\N	2041	414362	0	SI	2022-01-25 16:46:17
8952	\N	2041	414363	0	SI	2022-01-25 16:46:17
8953	\N	2041	414364	0	NO	2022-01-25 16:46:17
8954	\N	2041	414368	3	SI	2022-01-25 16:46:17
8955	\N	2041	414369	0	SI	2022-01-25 16:46:17
8956	\N	2041	414370	3	SI	2022-01-25 16:46:17
8957	\N	2041	414371	0	NO	2022-01-25 16:46:17
8958	\N	2041	414375	0	NO	2022-01-25 16:46:17
8959	\N	2041	414377	0	SI	2022-01-25 16:46:17
8960	\N	2041	414378	0	SI	2022-01-25 16:46:17
8961	\N	2041	414379	2	SI	2022-01-25 16:46:17
8962	\N	2041	414380	0	SI	2022-01-25 16:46:17
8963	\N	2041	414381	0	SI	2022-01-25 16:46:17
8964	\N	2041	414382	0	NO	2022-01-25 16:46:17
8965	\N	2041	414383	0	NO	2022-01-25 16:46:17
8966	\N	2041	414384	0	NO	2022-01-25 16:46:17
8967	\N	2041	414385	5	SI	2022-01-25 16:46:17
8968	\N	2041	414386	0	SI	2022-01-25 16:46:17
8969	\N	2041	414387	0	SI	2022-01-25 16:46:17
8970	\N	2041	414388	0	SI	2022-01-25 16:46:17
8971	\N	2041	414389	0	SI	2022-01-25 16:46:17
8972	\N	2041	414390	4	NO	2022-01-25 16:46:17
8973	\N	2041	414391	0	SI	2022-01-25 16:46:17
8974	\N	2041	414392	0	SI	2022-01-25 16:46:17
8975	\N	2041	414393	0	NO	2022-01-25 16:46:17
8976	\N	2041	414394	0	NO	2022-01-25 16:46:17
8977	\N	2041	414395	0	NO	2022-01-25 16:46:17
8978	\N	2041	414396	0	SI	2022-01-25 16:46:17
8979	\N	2041	414397	0	SI	2022-01-25 16:46:17
8980	\N	2041	414398	0	SI	2022-01-25 16:46:18
8981	\N	2041	414399	0	SI	2022-01-25 16:46:18
8982	\N	2041	414400	0	SI	2022-01-25 16:46:18
8983	\N	2041	414401	0	SI	2022-01-25 16:46:18
8984	\N	2041	414402	5	NO	2022-01-25 16:46:18
8985	\N	2041	414407	0	SI	2022-01-25 16:46:18
8986	\N	2041	414408	0	NO	2022-01-25 16:46:18
8987	\N	2041	414409	0	NO	2022-01-25 16:46:18
8988	\N	2041	414410	0	NO	2022-01-25 16:46:18
8989	\N	2041	414412	0	NO	2022-01-25 16:46:18
8990	\N	2041	414413	0	NO	2022-01-25 16:46:18
8991	\N	2041	414416	2	NO	2022-01-25 16:46:18
8992	\N	2041	414418	0	NO	2022-01-25 16:46:18
8993	\N	2041	414421	0	NO	2022-01-25 16:46:18
8994	\N	2041	414422	8	NO	2022-01-25 16:46:18
8995	\N	2041	414424	0	NO	2022-01-25 16:46:18
8996	\N	2041	414425	0	NO	2022-01-25 16:46:18
8997	\N	2041	414426	0	SI	2022-01-25 16:46:18
8998	\N	2041	414427	0	SI	2022-01-25 16:46:18
8999	\N	2041	414428	0	SI	2022-01-25 16:46:18
9000	\N	2041	414429	3	SI	2022-01-25 16:46:18
9001	\N	2041	414430	0	SI	2022-01-25 16:46:18
9002	\N	2041	414431	0	NO	2022-01-25 16:46:18
9003	\N	2041	414432	0	SI	2022-01-25 16:46:18
9004	\N	2041	414433	0	SI	2022-01-25 16:46:18
9005	\N	2041	414434	0	NO	2022-01-25 16:46:18
9006	\N	2041	414435	0	NO	2022-01-25 16:46:18
9007	\N	2041	414436	0	NO	2022-01-25 16:46:18
9008	\N	2041	414438	0	NO	2022-01-25 16:46:18
9009	\N	2041	414442	0	SI	2022-01-25 16:46:18
9010	\N	2041	414443	0	SI	2022-01-25 16:46:18
9011	\N	2041	414444	0	NO	2022-01-25 16:46:18
9012	\N	2041	414445	0	NO	2022-01-25 16:46:18
9013	\N	2041	414446	1	SI	2022-01-25 16:46:18
9014	\N	2041	414447	0	SI	2022-01-25 16:46:18
9015	\N	2041	414448	5	SI	2022-01-25 16:46:18
9017	\N	2041	414450	0	SI	2022-01-25 16:46:18
9019	\N	2041	414452	0	SI	2022-01-25 16:46:18
9021	\N	2041	414454	0	SI	2022-01-25 16:46:18
9023	\N	2041	414456	0	NO	2022-01-25 16:46:18
9025	\N	2041	414458	0	SI	2022-01-25 16:46:18
9027	\N	2041	414460	0	SI	2022-01-25 16:46:18
9029	\N	2041	414462	0	SI	2022-01-25 16:46:18
9031	\N	2041	414464	0	NO	2022-01-25 16:46:18
9033	\N	2041	414466	15	NO	2022-01-25 16:46:18
9035	\N	2041	414478	0	NO	2022-01-25 16:46:18
9037	\N	2041	414480	0	SI	2022-01-25 16:46:18
9039	\N	2041	414482	0	SI	2022-01-25 16:46:18
9041	\N	2041	414484	2	SI	2022-01-25 16:46:18
9043	\N	2041	414486	0	NO	2022-01-25 16:46:18
9045	\N	2041	414488	2	SI	2022-01-25 16:46:18
9047	\N	2041	414490	0	SI	2022-01-25 16:46:18
9049	\N	2041	414492	0	SI	2022-01-25 16:46:18
9051	\N	2041	414494	0	SI	2022-01-25 16:46:18
9053	\N	2041	414496	0	SI	2022-01-25 16:46:18
9055	\N	2041	414498	0	NO	2022-01-25 16:46:18
9057	\N	2041	414501	0	SI	2022-01-25 16:46:18
9059	\N	2041	414503	0	SI	2022-01-25 16:46:18
9061	\N	2041	414505	0	SI	2022-01-25 16:46:18
9063	\N	2041	414507	0	SI	2022-01-25 16:46:18
9065	\N	2041	414510	0	SI	2022-01-25 16:46:18
9067	\N	2041	414512	0	NO	2022-01-25 16:46:18
9069	\N	2041	414514	0	NO	2022-01-25 16:46:18
9071	\N	2041	414517	0	NO	2022-01-25 16:46:18
9073	\N	2041	414519	0	SI	2022-01-25 16:46:18
9075	\N	2041	414521	0	NO	2022-01-25 16:46:18
9077	\N	2041	414523	0	SI	2022-01-25 16:46:18
9079	\N	2041	414525	0	NO	2022-01-25 16:46:18
9081	\N	2041	414528	0	NO	2022-01-25 16:46:18
9083	\N	2041	414531	0	NO	2022-01-25 16:46:18
9085	\N	2041	414533	0	NO	2022-01-25 16:46:18
9087	\N	2041	414535	0	NO	2022-01-25 16:46:18
9089	\N	2041	414537	0	SI	2022-01-25 16:46:18
9091	\N	2041	414539	0	NO	2022-01-25 16:46:18
9093	\N	2041	414541	0	NO	2022-01-25 16:46:18
9095	\N	2041	414543	0	NO	2022-01-25 16:46:18
9097	\N	2041	414545	0	NO	2022-01-25 16:46:18
9099	\N	2041	414548	0	SI	2022-01-25 16:46:18
9101	\N	2041	414550	0	SI	2022-01-25 16:46:18
9103	\N	2041	414552	2	SI	2022-01-25 16:46:18
9105	\N	2041	414554	0	SI	2022-01-25 16:46:18
9107	\N	2041	414557	0	SI	2022-01-25 16:46:18
9109	\N	2041	414559	0	SI	2022-01-25 16:46:18
9111	\N	2041	414561	0	NO	2022-01-25 16:46:18
9113	\N	2041	414563	0	SI	2022-01-25 16:46:18
9115	\N	2041	414565	0	SI	2022-01-25 16:46:18
9117	\N	2041	414567	0	NO	2022-01-25 16:46:18
9119	\N	2041	414569	1	SI	2022-01-25 16:46:18
9121	\N	2041	414571	1	NO	2022-01-25 16:46:18
9123	\N	2041	414574	0	SI	2022-01-25 16:46:18
9125	\N	2041	414576	0	SI	2022-01-25 16:46:18
9127	\N	2041	414579	0	SI	2022-01-25 16:46:18
9129	\N	2041	414581	0	SI	2022-01-25 16:46:18
9131	\N	2041	414583	0	SI	2022-01-25 16:46:18
9133	\N	2041	414585	0	SI	2022-01-25 16:46:18
9135	\N	2041	414588	0	SI	2022-01-25 16:46:18
9137	\N	2041	414590	0	SI	2022-01-25 16:46:18
9139	\N	2041	414592	0	SI	2022-01-25 16:46:18
9141	\N	2041	414594	0	SI	2022-01-25 16:46:18
9143	\N	2041	414596	0	NO	2022-01-25 16:46:18
9145	\N	2041	414598	0	NO	2022-01-25 16:46:18
9147	\N	2041	414601	10	SI	2022-01-25 16:46:18
9149	\N	2041	414603	0	NO	2022-01-25 16:46:18
9151	\N	2041	414605	0	NO	2022-01-25 16:46:18
9153	\N	2041	414609	0	NO	2022-01-25 16:46:18
9155	\N	2041	414611	0	NO	2022-01-25 16:46:18
9157	\N	2041	414613	0	NO	2022-01-25 16:46:18
9159	\N	2041	414615	0	NO	2022-01-25 16:46:18
9161	\N	2041	414617	0	NO	2022-01-25 16:46:18
9163	\N	2041	414619	65	SI	2022-01-25 16:46:18
9165	\N	2041	414622	0	NO	2022-01-25 16:46:18
9167	\N	2041	414624	0	NO	2022-01-25 16:46:18
9169	\N	2041	414631	4	NO	2022-01-25 16:46:18
9171	\N	2041	414634	0	NO	2022-01-25 16:46:18
9173	\N	2041	414636	0	NO	2022-01-25 16:46:18
9175	\N	2041	419809	0	SI	2022-01-25 16:46:18
9016	\N	2041	414449	0	SI	2022-01-25 16:46:18
9018	\N	2041	414451	0	SI	2022-01-25 16:46:18
9020	\N	2041	414453	5	NO	2022-01-25 16:46:18
9022	\N	2041	414455	0	NO	2022-01-25 16:46:18
9024	\N	2041	414457	0	NO	2022-01-25 16:46:18
9026	\N	2041	414459	0	SI	2022-01-25 16:46:18
9028	\N	2041	414461	0	SI	2022-01-25 16:46:18
9030	\N	2041	414463	0	SI	2022-01-25 16:46:18
9032	\N	2041	414465	0	NO	2022-01-25 16:46:18
9034	\N	2041	414476	0	NO	2022-01-25 16:46:18
9036	\N	2041	414479	0	SI	2022-01-25 16:46:18
9038	\N	2041	414481	0	SI	2022-01-25 16:46:18
9040	\N	2041	414483	0	NO	2022-01-25 16:46:18
9042	\N	2041	414485	0	SI	2022-01-25 16:46:18
9044	\N	2041	414487	0	NO	2022-01-25 16:46:18
9046	\N	2041	414489	0	SI	2022-01-25 16:46:18
9048	\N	2041	414491	0	SI	2022-01-25 16:46:18
9050	\N	2041	414493	0	SI	2022-01-25 16:46:18
9052	\N	2041	414495	0	SI	2022-01-25 16:46:18
9054	\N	2041	414497	0	SI	2022-01-25 16:46:18
9056	\N	2041	414500	0	SI	2022-01-25 16:46:18
9058	\N	2041	414502	0	SI	2022-01-25 16:46:18
9060	\N	2041	414504	0	SI	2022-01-25 16:46:18
9062	\N	2041	414506	0	NO	2022-01-25 16:46:18
9064	\N	2041	414508	0	NO	2022-01-25 16:46:18
9066	\N	2041	414511	0	SI	2022-01-25 16:46:18
9068	\N	2041	414513	0	NO	2022-01-25 16:46:18
9070	\N	2041	414516	0	NO	2022-01-25 16:46:18
9072	\N	2041	414518	0	SI	2022-01-25 16:46:18
9074	\N	2041	414520	0	SI	2022-01-25 16:46:18
9076	\N	2041	414522	0	SI	2022-01-25 16:46:18
9078	\N	2041	414524	0	NO	2022-01-25 16:46:18
9080	\N	2041	414527	0	NO	2022-01-25 16:46:18
9082	\N	2041	414529	0	NO	2022-01-25 16:46:18
9084	\N	2041	414532	0	SI	2022-01-25 16:46:18
9086	\N	2041	414534	0	NO	2022-01-25 16:46:18
9088	\N	2041	414536	0	NO	2022-01-25 16:46:18
9090	\N	2041	414538	0	SI	2022-01-25 16:46:18
9092	\N	2041	414540	0	NO	2022-01-25 16:46:18
9094	\N	2041	414542	0	NO	2022-01-25 16:46:18
9096	\N	2041	414544	0	NO	2022-01-25 16:46:18
9098	\N	2041	414547	0	SI	2022-01-25 16:46:18
9100	\N	2041	414549	0	NO	2022-01-25 16:46:18
9102	\N	2041	414551	0	SI	2022-01-25 16:46:18
9104	\N	2041	414553	0	SI	2022-01-25 16:46:18
9106	\N	2041	414556	0	SI	2022-01-25 16:46:18
9108	\N	2041	414558	0	SI	2022-01-25 16:46:18
9110	\N	2041	414560	0	SI	2022-01-25 16:46:18
9112	\N	2041	414562	0	SI	2022-01-25 16:46:18
9114	\N	2041	414564	0	SI	2022-01-25 16:46:18
9116	\N	2041	414566	0	SI	2022-01-25 16:46:18
9118	\N	2041	414568	0	SI	2022-01-25 16:46:18
9120	\N	2041	414570	0	SI	2022-01-25 16:46:18
9122	\N	2041	414573	0	SI	2022-01-25 16:46:18
9124	\N	2041	414575	0	SI	2022-01-25 16:46:18
9126	\N	2041	414577	0	SI	2022-01-25 16:46:18
9128	\N	2041	414580	0	SI	2022-01-25 16:46:18
9130	\N	2041	414582	0	SI	2022-01-25 16:46:18
9132	\N	2041	414584	0	SI	2022-01-25 16:46:18
9134	\N	2041	414586	0	SI	2022-01-25 16:46:18
9136	\N	2041	414589	0	SI	2022-01-25 16:46:18
9138	\N	2041	414591	0	SI	2022-01-25 16:46:18
9140	\N	2041	414593	0	SI	2022-01-25 16:46:18
9142	\N	2041	414595	0	NO	2022-01-25 16:46:18
9144	\N	2041	414597	0	SI	2022-01-25 16:46:18
9146	\N	2041	414599	0	NO	2022-01-25 16:46:18
9148	\N	2041	414602	10	SI	2022-01-25 16:46:18
9150	\N	2041	414604	3	SI	2022-01-25 16:46:18
9152	\N	2041	414606	0	NO	2022-01-25 16:46:18
9154	\N	2041	414610	0	NO	2022-01-25 16:46:18
9156	\N	2041	414612	0	NO	2022-01-25 16:46:18
9158	\N	2041	414614	11	SI	2022-01-25 16:46:18
9160	\N	2041	414616	0	NO	2022-01-25 16:46:18
9162	\N	2041	414618	0	SI	2022-01-25 16:46:18
9164	\N	2041	414621	0	NO	2022-01-25 16:46:18
9166	\N	2041	414623	0	NO	2022-01-25 16:46:18
9168	\N	2041	414630	2	NO	2022-01-25 16:46:18
9170	\N	2041	414633	0	NO	2022-01-25 16:46:18
9172	\N	2041	414635	0	NO	2022-01-25 16:46:18
9174	\N	2041	414637	0	NO	2022-01-25 16:46:18
9176	\N	2041	414344	0	SI	2022-01-25 16:53:53
9177	\N	2041	414345	0	SI	2022-01-25 16:53:53
9178	\N	2041	414346	0	NO	2022-01-25 16:53:53
9179	\N	2041	414347	0	SI	2022-01-25 16:53:53
9180	\N	2041	414348	0	SI	2022-01-25 16:53:53
9181	\N	2041	414349	0	SI	2022-01-25 16:53:53
9182	\N	2041	414350	0	SI	2022-01-25 16:53:53
9183	\N	2041	414351	0	NO	2022-01-25 16:53:53
9184	\N	2041	414356	0	NO	2022-01-25 16:53:53
9185	\N	2041	414357	0	SI	2022-01-25 16:53:53
9186	\N	2041	414358	0	SI	2022-01-25 16:53:53
9187	\N	2041	414359	0	SI	2022-01-25 16:53:53
9188	\N	2041	414360	0	SI	2022-01-25 16:53:53
9189	\N	2041	414361	0	NO	2022-01-25 16:53:53
9190	\N	2041	414362	0	SI	2022-01-25 16:53:53
9191	\N	2041	414363	0	SI	2022-01-25 16:53:53
9192	\N	2041	414364	0	NO	2022-01-25 16:53:53
9193	\N	2041	414368	3	SI	2022-01-25 16:53:53
9194	\N	2041	414369	0	SI	2022-01-25 16:53:53
9195	\N	2041	414370	3	SI	2022-01-25 16:53:53
9196	\N	2041	414371	0	NO	2022-01-25 16:53:53
9197	\N	2041	414375	0	NO	2022-01-25 16:53:53
9198	\N	2041	414377	0	SI	2022-01-25 16:53:53
9199	\N	2041	414378	0	SI	2022-01-25 16:53:53
9200	\N	2041	414379	2	SI	2022-01-25 16:53:53
9201	\N	2041	414380	0	SI	2022-01-25 16:53:53
9202	\N	2041	414381	0	SI	2022-01-25 16:53:53
9203	\N	2041	414382	0	NO	2022-01-25 16:53:53
9204	\N	2041	414383	0	NO	2022-01-25 16:53:53
9205	\N	2041	414384	0	NO	2022-01-25 16:53:53
9206	\N	2041	414385	5	SI	2022-01-25 16:53:53
9207	\N	2041	414386	0	SI	2022-01-25 16:53:53
9208	\N	2041	414387	0	SI	2022-01-25 16:53:53
9209	\N	2041	414388	0	SI	2022-01-25 16:53:53
9210	\N	2041	414389	0	SI	2022-01-25 16:53:53
9211	\N	2041	414390	4	NO	2022-01-25 16:53:53
9212	\N	2041	414391	0	SI	2022-01-25 16:53:53
9213	\N	2041	414392	0	SI	2022-01-25 16:53:53
9214	\N	2041	414393	0	NO	2022-01-25 16:53:53
9215	\N	2041	414394	0	NO	2022-01-25 16:53:53
9216	\N	2041	414395	0	NO	2022-01-25 16:53:53
9218	\N	2041	414397	0	SI	2022-01-25 16:53:53
9220	\N	2041	414399	0	SI	2022-01-25 16:53:53
9222	\N	2041	414401	0	SI	2022-01-25 16:53:53
9224	\N	2041	414407	0	SI	2022-01-25 16:53:53
9226	\N	2041	414409	0	NO	2022-01-25 16:53:53
9228	\N	2041	414412	0	NO	2022-01-25 16:53:53
9230	\N	2041	414416	2	NO	2022-01-25 16:53:53
9232	\N	2041	414421	0	NO	2022-01-25 16:53:53
9234	\N	2041	414424	0	NO	2022-01-25 16:53:53
9236	\N	2041	414426	0	SI	2022-01-25 16:53:53
9238	\N	2041	414428	0	SI	2022-01-25 16:53:53
9240	\N	2041	414430	0	SI	2022-01-25 16:53:53
9242	\N	2041	414432	0	SI	2022-01-25 16:53:53
9244	\N	2041	414434	0	NO	2022-01-25 16:53:53
9246	\N	2041	414436	0	NO	2022-01-25 16:53:53
9248	\N	2041	414442	0	SI	2022-01-25 16:53:53
9250	\N	2041	414444	0	NO	2022-01-25 16:53:53
9252	\N	2041	414446	1	SI	2022-01-25 16:53:53
9254	\N	2041	414448	5	SI	2022-01-25 16:53:53
9256	\N	2041	414450	0	SI	2022-01-25 16:53:53
9258	\N	2041	414452	0	SI	2022-01-25 16:53:53
9260	\N	2041	414454	0	SI	2022-01-25 16:53:53
9262	\N	2041	414456	0	NO	2022-01-25 16:53:53
9264	\N	2041	414458	0	SI	2022-01-25 16:53:53
9266	\N	2041	414460	0	SI	2022-01-25 16:53:53
9268	\N	2041	414462	0	SI	2022-01-25 16:53:53
9270	\N	2041	414464	0	NO	2022-01-25 16:53:53
9272	\N	2041	414466	15	NO	2022-01-25 16:53:53
9274	\N	2041	414478	0	NO	2022-01-25 16:53:53
9276	\N	2041	414480	0	SI	2022-01-25 16:53:53
9278	\N	2041	414482	0	SI	2022-01-25 16:53:53
9280	\N	2041	414484	2	SI	2022-01-25 16:53:53
9282	\N	2041	414486	0	NO	2022-01-25 16:53:53
9284	\N	2041	414488	2	SI	2022-01-25 16:53:53
9286	\N	2041	414490	0	SI	2022-01-25 16:53:53
9288	\N	2041	414492	0	SI	2022-01-25 16:53:53
9290	\N	2041	414494	0	SI	2022-01-25 16:53:53
9292	\N	2041	414496	0	SI	2022-01-25 16:53:53
9294	\N	2041	414498	0	NO	2022-01-25 16:53:53
9296	\N	2041	414501	0	SI	2022-01-25 16:53:53
9298	\N	2041	414503	0	SI	2022-01-25 16:53:53
9300	\N	2041	414505	0	SI	2022-01-25 16:53:53
9302	\N	2041	414507	0	SI	2022-01-25 16:53:53
9304	\N	2041	414510	0	SI	2022-01-25 16:53:53
9306	\N	2041	414512	0	NO	2022-01-25 16:53:53
9308	\N	2041	414514	0	NO	2022-01-25 16:53:53
9310	\N	2041	414517	0	NO	2022-01-25 16:53:53
9312	\N	2041	414519	0	SI	2022-01-25 16:53:53
9314	\N	2041	414521	0	NO	2022-01-25 16:53:53
9316	\N	2041	414523	0	SI	2022-01-25 16:53:53
9318	\N	2041	414525	0	NO	2022-01-25 16:53:53
9320	\N	2041	414528	0	NO	2022-01-25 16:53:53
9322	\N	2041	414531	0	NO	2022-01-25 16:53:53
9324	\N	2041	414533	0	NO	2022-01-25 16:53:53
9326	\N	2041	414535	0	NO	2022-01-25 16:53:53
9328	\N	2041	414537	0	SI	2022-01-25 16:53:53
9330	\N	2041	414539	0	NO	2022-01-25 16:53:53
9332	\N	2041	414541	0	NO	2022-01-25 16:53:53
9334	\N	2041	414543	0	NO	2022-01-25 16:53:53
9336	\N	2041	414545	0	NO	2022-01-25 16:53:53
9338	\N	2041	414548	0	SI	2022-01-25 16:53:53
9340	\N	2041	414550	0	SI	2022-01-25 16:53:53
9342	\N	2041	414552	2	SI	2022-01-25 16:53:53
9344	\N	2041	414554	0	SI	2022-01-25 16:53:53
9346	\N	2041	414557	0	SI	2022-01-25 16:53:53
9348	\N	2041	414559	0	SI	2022-01-25 16:53:53
9350	\N	2041	414561	0	NO	2022-01-25 16:53:53
9352	\N	2041	414563	0	SI	2022-01-25 16:53:53
9354	\N	2041	414565	0	SI	2022-01-25 16:53:53
9356	\N	2041	414567	0	NO	2022-01-25 16:53:53
9358	\N	2041	414569	1	SI	2022-01-25 16:53:53
9360	\N	2041	414571	1	NO	2022-01-25 16:53:53
9362	\N	2041	414574	0	SI	2022-01-25 16:53:53
9364	\N	2041	414576	0	SI	2022-01-25 16:53:53
9366	\N	2041	414579	0	SI	2022-01-25 16:53:53
9368	\N	2041	414581	0	SI	2022-01-25 16:53:53
9370	\N	2041	414583	0	SI	2022-01-25 16:53:53
9372	\N	2041	414585	0	SI	2022-01-25 16:53:53
9374	\N	2041	414588	0	SI	2022-01-25 16:53:53
9376	\N	2041	414590	0	SI	2022-01-25 16:53:53
9378	\N	2041	414592	0	SI	2022-01-25 16:53:53
9380	\N	2041	414594	0	SI	2022-01-25 16:53:53
9382	\N	2041	414596	0	NO	2022-01-25 16:53:53
9384	\N	2041	414598	0	NO	2022-01-25 16:53:53
9386	\N	2041	414601	10	SI	2022-01-25 16:53:53
9388	\N	2041	414603	0	NO	2022-01-25 16:53:53
9390	\N	2041	414605	0	NO	2022-01-25 16:53:53
9392	\N	2041	414609	0	NO	2022-01-25 16:53:53
9394	\N	2041	414611	0	NO	2022-01-25 16:53:53
9396	\N	2041	414613	0	NO	2022-01-25 16:53:53
9398	\N	2041	414615	0	NO	2022-01-25 16:53:53
9400	\N	2041	414617	0	NO	2022-01-25 16:53:53
9402	\N	2041	414619	65	SI	2022-01-25 16:53:53
9404	\N	2041	414622	0	NO	2022-01-25 16:53:53
9406	\N	2041	414624	0	NO	2022-01-25 16:53:53
9408	\N	2041	414631	4	NO	2022-01-25 16:53:53
9410	\N	2041	414634	0	NO	2022-01-25 16:53:53
9412	\N	2041	414636	0	NO	2022-01-25 16:53:53
9414	\N	2041	419809	0	SI	2022-01-25 16:53:53
9217	\N	2041	414396	0	SI	2022-01-25 16:53:53
9219	\N	2041	414398	0	SI	2022-01-25 16:53:53
9221	\N	2041	414400	0	SI	2022-01-25 16:53:53
9223	\N	2041	414402	5	NO	2022-01-25 16:53:53
9225	\N	2041	414408	0	NO	2022-01-25 16:53:53
9227	\N	2041	414410	0	NO	2022-01-25 16:53:53
9229	\N	2041	414413	0	NO	2022-01-25 16:53:53
9231	\N	2041	414418	0	NO	2022-01-25 16:53:53
9233	\N	2041	414422	8	NO	2022-01-25 16:53:53
9235	\N	2041	414425	0	NO	2022-01-25 16:53:53
9237	\N	2041	414427	0	SI	2022-01-25 16:53:53
9239	\N	2041	414429	3	SI	2022-01-25 16:53:53
9241	\N	2041	414431	0	NO	2022-01-25 16:53:53
9243	\N	2041	414433	0	SI	2022-01-25 16:53:53
9245	\N	2041	414435	0	NO	2022-01-25 16:53:53
9247	\N	2041	414438	0	NO	2022-01-25 16:53:53
9249	\N	2041	414443	0	SI	2022-01-25 16:53:53
9251	\N	2041	414445	0	NO	2022-01-25 16:53:53
9253	\N	2041	414447	0	SI	2022-01-25 16:53:53
9255	\N	2041	414449	0	SI	2022-01-25 16:53:53
9257	\N	2041	414451	0	SI	2022-01-25 16:53:53
9259	\N	2041	414453	5	NO	2022-01-25 16:53:53
9261	\N	2041	414455	0	NO	2022-01-25 16:53:53
9263	\N	2041	414457	0	NO	2022-01-25 16:53:53
9265	\N	2041	414459	0	SI	2022-01-25 16:53:53
9267	\N	2041	414461	0	SI	2022-01-25 16:53:53
9269	\N	2041	414463	0	SI	2022-01-25 16:53:53
9271	\N	2041	414465	0	NO	2022-01-25 16:53:53
9273	\N	2041	414476	0	NO	2022-01-25 16:53:53
9275	\N	2041	414479	0	SI	2022-01-25 16:53:53
9277	\N	2041	414481	0	SI	2022-01-25 16:53:53
9279	\N	2041	414483	0	NO	2022-01-25 16:53:53
9281	\N	2041	414485	0	SI	2022-01-25 16:53:53
9283	\N	2041	414487	0	NO	2022-01-25 16:53:53
9285	\N	2041	414489	0	SI	2022-01-25 16:53:53
9287	\N	2041	414491	0	SI	2022-01-25 16:53:53
9289	\N	2041	414493	0	SI	2022-01-25 16:53:53
9291	\N	2041	414495	0	SI	2022-01-25 16:53:53
9293	\N	2041	414497	0	SI	2022-01-25 16:53:53
9295	\N	2041	414500	0	SI	2022-01-25 16:53:53
9297	\N	2041	414502	0	SI	2022-01-25 16:53:53
9299	\N	2041	414504	0	SI	2022-01-25 16:53:53
9301	\N	2041	414506	0	NO	2022-01-25 16:53:53
9303	\N	2041	414508	0	NO	2022-01-25 16:53:53
9305	\N	2041	414511	0	SI	2022-01-25 16:53:53
9307	\N	2041	414513	0	NO	2022-01-25 16:53:53
9309	\N	2041	414516	0	NO	2022-01-25 16:53:53
9311	\N	2041	414518	0	SI	2022-01-25 16:53:53
9313	\N	2041	414520	0	SI	2022-01-25 16:53:53
9315	\N	2041	414522	0	SI	2022-01-25 16:53:53
9317	\N	2041	414524	0	NO	2022-01-25 16:53:53
9319	\N	2041	414527	0	NO	2022-01-25 16:53:53
9321	\N	2041	414529	0	NO	2022-01-25 16:53:53
9323	\N	2041	414532	0	SI	2022-01-25 16:53:53
9325	\N	2041	414534	0	NO	2022-01-25 16:53:53
9327	\N	2041	414536	0	NO	2022-01-25 16:53:53
9329	\N	2041	414538	0	SI	2022-01-25 16:53:53
9331	\N	2041	414540	0	NO	2022-01-25 16:53:53
9333	\N	2041	414542	0	NO	2022-01-25 16:53:53
9335	\N	2041	414544	0	NO	2022-01-25 16:53:53
9337	\N	2041	414547	0	SI	2022-01-25 16:53:53
9339	\N	2041	414549	0	NO	2022-01-25 16:53:53
9341	\N	2041	414551	0	SI	2022-01-25 16:53:53
9343	\N	2041	414553	0	SI	2022-01-25 16:53:53
9345	\N	2041	414556	0	SI	2022-01-25 16:53:53
9347	\N	2041	414558	0	SI	2022-01-25 16:53:53
9349	\N	2041	414560	0	SI	2022-01-25 16:53:53
9351	\N	2041	414562	0	SI	2022-01-25 16:53:53
9353	\N	2041	414564	0	SI	2022-01-25 16:53:53
9355	\N	2041	414566	0	SI	2022-01-25 16:53:53
9357	\N	2041	414568	0	SI	2022-01-25 16:53:53
9359	\N	2041	414570	0	SI	2022-01-25 16:53:53
9361	\N	2041	414573	0	SI	2022-01-25 16:53:53
9363	\N	2041	414575	0	SI	2022-01-25 16:53:53
9365	\N	2041	414577	0	SI	2022-01-25 16:53:53
9367	\N	2041	414580	0	SI	2022-01-25 16:53:53
9369	\N	2041	414582	0	SI	2022-01-25 16:53:53
9371	\N	2041	414584	0	SI	2022-01-25 16:53:53
9373	\N	2041	414586	0	SI	2022-01-25 16:53:53
9375	\N	2041	414589	0	SI	2022-01-25 16:53:53
9377	\N	2041	414591	0	SI	2022-01-25 16:53:53
9379	\N	2041	414593	0	SI	2022-01-25 16:53:53
9381	\N	2041	414595	0	NO	2022-01-25 16:53:53
9383	\N	2041	414597	0	SI	2022-01-25 16:53:53
9385	\N	2041	414599	0	NO	2022-01-25 16:53:53
9387	\N	2041	414602	10	SI	2022-01-25 16:53:53
9389	\N	2041	414604	3	SI	2022-01-25 16:53:53
9391	\N	2041	414606	0	NO	2022-01-25 16:53:53
9393	\N	2041	414610	0	NO	2022-01-25 16:53:53
9395	\N	2041	414612	0	NO	2022-01-25 16:53:53
9397	\N	2041	414614	11	SI	2022-01-25 16:53:53
9399	\N	2041	414616	0	NO	2022-01-25 16:53:53
9401	\N	2041	414618	0	SI	2022-01-25 16:53:53
9403	\N	2041	414621	0	NO	2022-01-25 16:53:53
9405	\N	2041	414623	0	NO	2022-01-25 16:53:53
9407	\N	2041	414630	2	NO	2022-01-25 16:53:53
9409	\N	2041	414633	0	NO	2022-01-25 16:53:53
9411	\N	2041	414635	0	NO	2022-01-25 16:53:53
9413	\N	2041	414637	0	NO	2022-01-25 16:53:53
9415	-544	2042	414640	0	SI	2022-01-31 11:51:11
9416	-544	2042	414641	0	NO	2022-01-31 11:51:11
9417	-544	2042	414643	0	SI	2022-01-31 11:51:11
9418	-544	2042	414644	2	SI	2022-01-31 11:51:11
9419	-544	2042	414645	0	SI	2022-01-31 11:51:11
9420	-544	2042	414646	0	SI	2022-01-31 11:51:11
9421	-544	2042	414647	5	SI	2022-01-31 11:51:11
9422	-544	2042	414648	0	NO	2022-01-31 11:51:11
9423	-544	2042	414649	6	SI	2022-01-31 11:51:11
9424	-544	2042	414650	0	NO	2022-01-31 11:51:11
9425	-544	2042	414651	22	NO	2022-01-31 11:51:11
9426	-544	2042	414652	8	NO	2022-01-31 11:51:11
9427	-544	2042	414667	5	NO	2022-01-31 11:51:11
9428	-544	2042	414672	0	NO	2022-01-31 11:51:11
9429	-544	2042	414673	22	NO	2022-01-31 11:51:11
9430	-544	2042	414674	8	NO	2022-01-31 11:51:11
9431	-544	2042	414688	5	NO	2022-01-31 11:51:11
9432	-544	2042	414693	0	NO	2022-01-31 11:51:11
9433	-544	2042	414695	0	SI	2022-01-31 11:51:11
9435	-544	2042	414697	0	SI	2022-01-31 11:51:11
9437	-544	2042	414699	0	SI	2022-01-31 11:51:11
9439	-544	2042	414701	1	SI	2022-01-31 11:51:11
9441	-544	2042	414703	3	SI	2022-01-31 11:51:11
9443	-544	2042	414705	1	SI	2022-01-31 11:51:11
9445	-544	2042	414707	0	SI	2022-01-31 11:51:11
9447	-544	2042	414709	0	SI	2022-01-31 11:51:11
9449	-544	2042	414711	0	SI	2022-01-31 11:51:11
9451	-544	2042	414713	0	SI	2022-01-31 11:51:11
9453	-544	2042	414715	0	NO	2022-01-31 11:51:11
9455	-544	2042	414718	0	SI	2022-01-31 11:51:11
9457	-544	2042	414720	0	SI	2022-01-31 11:51:11
9459	-544	2042	414722	10	NO	2022-01-31 11:51:11
9461	-544	2042	414726	4	SI	2022-01-31 11:51:11
9463	-544	2042	414728	0	NO	2022-01-31 11:51:11
9465	-544	2042	414732	0	SI	2022-01-31 11:51:11
9467	-544	2042	414734	5	NO	2022-01-31 11:51:11
9469	-544	2042	414741	0	NO	2022-01-31 11:51:11
9471	-544	2042	414743	2	NO	2022-01-31 11:51:11
9473	-544	2042	414746	8	NO	2022-01-31 11:51:11
9475	-544	2042	414749	18	NO	2022-01-31 11:51:11
9477	-544	2042	414751	0	SI	2022-01-31 11:51:11
9479	-544	2042	414754	0	NO	2022-01-31 11:51:11
9481	-544	2042	414756	0	SI	2022-01-31 11:51:11
9483	-544	2042	414758	20	NO	2022-01-31 11:51:11
9485	-544	2042	414760	0	SI	2022-01-31 11:51:11
9487	-544	2042	414762	5	NO	2022-01-31 11:51:11
9489	-544	2042	414764	0	NO	2022-01-31 11:51:11
9491	-544	2042	414766	0	NO	2022-01-31 11:51:11
9493	-544	2042	414768	5	NO	2022-01-31 11:51:11
9495	-544	2042	414770	0	SI	2022-01-31 11:51:11
9497	-544	2042	414772	0	SI	2022-01-31 11:51:11
9499	-544	2042	414774	5	SI	2022-01-31 11:51:11
9501	-544	2042	414776	0	SI	2022-01-31 11:51:11
9503	-544	2042	414778	0	NO	2022-01-31 11:51:11
9505	-544	2042	414780	5	NO	2022-01-31 11:51:11
9507	-544	2042	414782	0	SI	2022-01-31 11:51:11
9509	-544	2042	414784	0	SI	2022-01-31 11:51:11
9511	-544	2042	414786	0	NO	2022-01-31 11:51:11
9513	-544	2042	414789	0	NO	2022-01-31 11:51:11
9515	-544	2042	414791	0	SI	2022-01-31 11:51:11
9517	-544	2042	414793	0	NO	2022-01-31 11:51:11
9519	-544	2042	414795	0	SI	2022-01-31 11:51:11
9521	-544	2042	414797	0	NO	2022-01-31 11:51:11
9523	-544	2042	414804	0	NO	2022-01-31 11:51:11
9525	-544	2042	414806	0	SI	2022-01-31 11:51:11
9527	-544	2042	414808	0	NO	2022-01-31 11:51:11
9529	-544	2042	414810	0	NO	2022-01-31 11:51:11
9531	-544	2042	414812	0	SI	2022-01-31 11:51:11
9533	-544	2042	414814	0	NO	2022-01-31 11:51:11
9535	-544	2042	414816	0	NO	2022-01-31 11:51:11
9537	-544	2042	414818	0	NO	2022-01-31 11:51:11
9539	-544	2042	414821	0	SI	2022-01-31 11:51:11
9541	-544	2042	414823	0	SI	2022-01-31 11:51:11
9543	-544	2042	414825	0	NO	2022-01-31 11:51:11
9545	-544	2042	414830	0	NO	2022-01-31 11:51:11
9547	-544	2042	414833	0	SI	2022-01-31 11:51:11
9549	-544	2042	414835	0	SI	2022-01-31 11:51:11
9551	-544	2042	414839	0	SI	2022-01-31 11:51:11
9553	-544	2042	414841	0	SI	2022-01-31 11:51:11
9555	-544	2042	414843	0	NO	2022-01-31 11:51:11
9557	-544	2042	414845	0	NO	2022-01-31 11:51:11
9559	-544	2042	414848	0	SI	2022-01-31 11:51:11
9561	-544	2042	414850	0	SI	2022-01-31 11:51:11
9563	-544	2042	414852	3	NO	2022-01-31 11:51:11
9565	-544	2042	414855	0	SI	2022-01-31 11:51:11
9567	-544	2042	414857	0	SI	2022-01-31 11:51:11
9569	-544	2042	414859	0	SI	2022-01-31 11:51:11
9571	-544	2042	414861	0	SI	2022-01-31 11:51:11
9573	-544	2042	414863	0	SI	2022-01-31 11:51:11
9575	-544	2042	414866	0	SI	2022-01-31 11:51:11
9577	-544	2042	414868	0	SI	2022-01-31 11:51:11
9579	-544	2042	414870	0	SI	2022-01-31 11:51:11
9581	-544	2042	414872	0	NO	2022-01-31 11:51:11
9583	-544	2042	414874	0	SI	2022-01-31 11:51:11
9585	-544	2042	414876	0	NO	2022-01-31 11:51:11
9587	-544	2042	414880	0	NO	2022-01-31 11:51:11
9589	-544	2042	414882	0	NO	2022-01-31 11:51:11
9591	-544	2042	414884	10	SI	2022-01-31 11:51:11
9593	-544	2042	414887	20	SI	2022-01-31 11:51:11
9595	-544	2042	414889	0	NO	2022-01-31 11:51:11
9597	-544	2042	414898	0	SI	2022-01-31 11:51:11
9599	-544	2042	414901	0	NO	2022-01-31 11:51:11
9601	-544	2042	414903	0	NO	2022-01-31 11:51:11
9603	-544	2042	414905	0	NO	2022-01-31 11:51:11
9605	-544	2042	419820	10	NO	2022-01-31 11:51:11
9607	-544	2042	419822	0	SI	2022-01-31 11:51:11
9609	-544	2042	419824	0	SI	2022-01-31 11:51:11
9611	-544	2042	419859	12	SI	2022-01-31 11:51:11
9613	-545	2042	414641	0	NO	2022-01-31 11:56:24
9615	-545	2042	414644	2	SI	2022-01-31 11:56:24
9617	-545	2042	414646	0	SI	2022-01-31 11:56:24
9619	-545	2042	414648	0	NO	2022-01-31 11:56:24
9621	-545	2042	414650	0	NO	2022-01-31 11:56:24
9623	-545	2042	414652	8	NO	2022-01-31 11:56:24
9625	-545	2042	414672	0	NO	2022-01-31 11:56:24
9627	-545	2042	414674	8	NO	2022-01-31 11:56:24
9629	-545	2042	414693	0	NO	2022-01-31 11:56:24
9631	-545	2042	414696	0	SI	2022-01-31 11:56:24
9633	-545	2042	414698	0	SI	2022-01-31 11:56:24
9635	-545	2042	414700	0	NO	2022-01-31 11:56:24
9637	-545	2042	414702	0	SI	2022-01-31 11:56:24
9639	-545	2042	414704	0	NO	2022-01-31 11:56:24
9641	-545	2042	414706	0	SI	2022-01-31 11:56:24
9643	-545	2042	414708	0	SI	2022-01-31 11:56:24
9645	-545	2042	414710	0	SI	2022-01-31 11:56:24
9434	-544	2042	414696	0	SI	2022-01-31 11:51:11
9436	-544	2042	414698	0	SI	2022-01-31 11:51:11
9438	-544	2042	414700	0	NO	2022-01-31 11:51:11
9440	-544	2042	414702	0	SI	2022-01-31 11:51:11
9442	-544	2042	414704	2	SI	2022-01-31 11:51:11
9444	-544	2042	414706	0	SI	2022-01-31 11:51:11
9446	-544	2042	414708	0	SI	2022-01-31 11:51:11
9448	-544	2042	414710	0	SI	2022-01-31 11:51:11
9450	-544	2042	414712	0	SI	2022-01-31 11:51:11
9452	-544	2042	414714	0	SI	2022-01-31 11:51:11
9454	-544	2042	414717	0	NO	2022-01-31 11:51:11
9456	-544	2042	414719	0	SI	2022-01-31 11:51:11
9458	-544	2042	414721	0	SI	2022-01-31 11:51:11
9460	-544	2042	414723	0	SI	2022-01-31 11:51:11
9462	-544	2042	414727	0	SI	2022-01-31 11:51:11
9464	-544	2042	414731	0	NO	2022-01-31 11:51:11
9466	-544	2042	414733	0	NO	2022-01-31 11:51:11
9468	-544	2042	414736	0	NO	2022-01-31 11:51:11
9470	-544	2042	414742	3	NO	2022-01-31 11:51:11
9472	-544	2042	414745	0	NO	2022-01-31 11:51:11
9474	-544	2042	414748	2	NO	2022-01-31 11:51:11
9476	-544	2042	414750	0	NO	2022-01-31 11:51:11
9478	-544	2042	414752	8	NO	2022-01-31 11:51:11
9480	-544	2042	414755	1	SI	2022-01-31 11:51:11
9482	-544	2042	414757	0	NO	2022-01-31 11:51:11
9484	-544	2042	414759	0	SI	2022-01-31 11:51:11
9486	-544	2042	414761	5	NO	2022-01-31 11:51:11
9488	-544	2042	414763	0	SI	2022-01-31 11:51:11
9490	-544	2042	414765	0	NO	2022-01-31 11:51:11
9492	-544	2042	414767	0	SI	2022-01-31 11:51:11
9494	-544	2042	414769	5	NO	2022-01-31 11:51:11
9496	-544	2042	414771	0	SI	2022-01-31 11:51:11
9498	-544	2042	414773	0	NO	2022-01-31 11:51:11
9500	-544	2042	414775	0	SI	2022-01-31 11:51:11
9502	-544	2042	414777	0	NO	2022-01-31 11:51:11
9504	-544	2042	414779	0	NO	2022-01-31 11:51:11
9506	-544	2042	414781	5	NO	2022-01-31 11:51:11
9508	-544	2042	414783	0	SI	2022-01-31 11:51:11
9510	-544	2042	414785	0	NO	2022-01-31 11:51:11
9512	-544	2042	414787	0	NO	2022-01-31 11:51:11
9514	-544	2042	414790	0	SI	2022-01-31 11:51:11
9516	-544	2042	414792	0	SI	2022-01-31 11:51:11
9518	-544	2042	414794	0	SI	2022-01-31 11:51:11
9520	-544	2042	414796	0	NO	2022-01-31 11:51:11
9522	-544	2042	414798	0	NO	2022-01-31 11:51:11
9524	-544	2042	414805	0	NO	2022-01-31 11:51:11
9526	-544	2042	414807	0	NO	2022-01-31 11:51:11
9528	-544	2042	414809	0	NO	2022-01-31 11:51:11
9530	-544	2042	414811	0	SI	2022-01-31 11:51:11
9532	-544	2042	414813	0	NO	2022-01-31 11:51:11
9534	-544	2042	414815	0	NO	2022-01-31 11:51:11
9536	-544	2042	414817	0	NO	2022-01-31 11:51:11
9538	-544	2042	414820	0	SI	2022-01-31 11:51:11
9540	-544	2042	414822	0	NO	2022-01-31 11:51:11
9542	-544	2042	414824	0	SI	2022-01-31 11:51:11
9544	-544	2042	414826	6	NO	2022-01-31 11:51:11
9546	-544	2042	414832	0	SI	2022-01-31 11:51:11
9548	-544	2042	414834	0	SI	2022-01-31 11:51:11
9550	-544	2042	414838	0	SI	2022-01-31 11:51:11
9552	-544	2042	414840	0	SI	2022-01-31 11:51:11
9554	-544	2042	414842	3	NO	2022-01-31 11:51:11
9556	-544	2042	414844	0	SI	2022-01-31 11:51:11
9558	-544	2042	414847	0	SI	2022-01-31 11:51:11
9560	-544	2042	414849	0	SI	2022-01-31 11:51:11
9562	-544	2042	414851	0	SI	2022-01-31 11:51:11
9564	-544	2042	414853	0	SI	2022-01-31 11:51:11
9566	-544	2042	414856	0	NO	2022-01-31 11:51:11
9568	-544	2042	414858	0	SI	2022-01-31 11:51:11
9570	-544	2042	414860	0	SI	2022-01-31 11:51:11
9572	-544	2042	414862	0	SI	2022-01-31 11:51:11
9574	-544	2042	414865	0	SI	2022-01-31 11:51:11
9576	-544	2042	414867	0	SI	2022-01-31 11:51:11
9578	-544	2042	414869	0	SI	2022-01-31 11:51:11
9580	-544	2042	414871	0	SI	2022-01-31 11:51:11
9582	-544	2042	414873	0	NO	2022-01-31 11:51:11
9584	-544	2042	414875	0	NO	2022-01-31 11:51:11
9586	-544	2042	414878	55	SI	2022-01-31 11:51:11
9588	-544	2042	414881	0	NO	2022-01-31 11:51:11
9590	-544	2042	414883	0	SI	2022-01-31 11:51:11
9592	-544	2042	414885	0	NO	2022-01-31 11:51:11
9594	-544	2042	414888	12	SI	2022-01-31 11:51:11
9596	-544	2042	414893	0	NO	2022-01-31 11:51:11
9598	-544	2042	414899	4	NO	2022-01-31 11:51:11
9600	-544	2042	414902	0	NO	2022-01-31 11:51:11
9602	-544	2042	414904	0	NO	2022-01-31 11:51:11
9604	-544	2042	419819	6	NO	2022-01-31 11:51:11
9606	-544	2042	419821	4	SI	2022-01-31 11:51:11
9608	-544	2042	419823	10	NO	2022-01-31 11:51:11
9610	-544	2042	419825	0	NO	2022-01-31 11:51:11
9612	-545	2042	414640	0	SI	2022-01-31 11:56:24
9614	-545	2042	414643	0	SI	2022-01-31 11:56:24
9616	-545	2042	414645	0	SI	2022-01-31 11:56:24
9618	-545	2042	414647	0	NO	2022-01-31 11:56:24
9620	-545	2042	414649	0	NO	2022-01-31 11:56:24
9622	-545	2042	414651	22	NO	2022-01-31 11:56:24
9624	-545	2042	414667	5	NO	2022-01-31 11:56:24
9626	-545	2042	414673	22	NO	2022-01-31 11:56:24
9628	-545	2042	414688	5	NO	2022-01-31 11:56:24
9630	-545	2042	414695	0	SI	2022-01-31 11:56:24
9632	-545	2042	414697	0	SI	2022-01-31 11:56:24
9634	-545	2042	414699	0	SI	2022-01-31 11:56:24
9636	-545	2042	414701	1	SI	2022-01-31 11:56:24
9638	-545	2042	414703	3	SI	2022-01-31 11:56:24
9640	-545	2042	414705	1	SI	2022-01-31 11:56:24
9642	-545	2042	414707	0	SI	2022-01-31 11:56:24
9644	-545	2042	414709	0	SI	2022-01-31 11:56:24
9646	-545	2042	414711	0	SI	2022-01-31 11:56:24
9647	-545	2042	414712	0	SI	2022-01-31 11:56:24
9649	-545	2042	414714	0	SI	2022-01-31 11:56:24
9651	-545	2042	414717	0	NO	2022-01-31 11:56:24
9653	-545	2042	414719	0	SI	2022-01-31 11:56:24
9655	-545	2042	414721	10	NO	2022-01-31 11:56:24
9657	-545	2042	414723	0	SI	2022-01-31 11:56:24
9659	-545	2042	414728	5	SI	2022-01-31 11:56:24
9661	-545	2042	414731	0	NO	2022-01-31 11:56:24
9663	-545	2042	414733	0	NO	2022-01-31 11:56:24
9665	-545	2042	414736	0	NO	2022-01-31 11:56:24
9667	-545	2042	414742	3	NO	2022-01-31 11:56:24
9669	-545	2042	414745	0	NO	2022-01-31 11:56:24
9671	-545	2042	414748	2	NO	2022-01-31 11:56:24
9673	-545	2042	414750	0	NO	2022-01-31 11:56:24
9675	-545	2042	414752	0	SI	2022-01-31 11:56:24
9677	-545	2042	414754	0	NO	2022-01-31 11:56:24
9679	-545	2042	414756	0	SI	2022-01-31 11:56:24
9681	-545	2042	414758	20	NO	2022-01-31 11:56:24
9683	-545	2042	414774	5	SI	2022-01-31 11:56:24
9685	-545	2042	414776	0	SI	2022-01-31 11:56:24
9687	-545	2042	414778	0	NO	2022-01-31 11:56:24
9689	-545	2042	414780	5	NO	2022-01-31 11:56:24
9691	-545	2042	414782	0	SI	2022-01-31 11:56:24
9693	-545	2042	414784	0	SI	2022-01-31 11:56:24
9695	-545	2042	414786	0	NO	2022-01-31 11:56:24
9697	-545	2042	414789	0	NO	2022-01-31 11:56:24
9699	-545	2042	414791	0	SI	2022-01-31 11:56:24
9701	-545	2042	414793	0	NO	2022-01-31 11:56:24
9703	-545	2042	414795	0	SI	2022-01-31 11:56:25
9705	-545	2042	414797	0	NO	2022-01-31 11:56:25
9707	-545	2042	414804	0	NO	2022-01-31 11:56:25
9709	-545	2042	414806	0	SI	2022-01-31 11:56:25
9711	-545	2042	414808	0	NO	2022-01-31 11:56:25
9713	-545	2042	414810	0	NO	2022-01-31 11:56:25
9715	-545	2042	414812	0	SI	2022-01-31 11:56:25
9717	-545	2042	414814	0	NO	2022-01-31 11:56:25
9719	-545	2042	414816	0	NO	2022-01-31 11:56:25
9721	-545	2042	414818	0	NO	2022-01-31 11:56:25
9723	-545	2042	414821	0	SI	2022-01-31 11:56:25
9725	-545	2042	414823	0	SI	2022-01-31 11:56:25
9727	-545	2042	414825	0	NO	2022-01-31 11:56:25
9729	-545	2042	414830	0	NO	2022-01-31 11:56:25
9731	-545	2042	414833	0	SI	2022-01-31 11:56:25
9733	-545	2042	414835	0	SI	2022-01-31 11:56:25
9735	-545	2042	414839	0	SI	2022-01-31 11:56:25
9737	-545	2042	414841	0	SI	2022-01-31 11:56:25
9739	-545	2042	414843	2	SI	2022-01-31 11:56:25
9741	-545	2042	414845	0	NO	2022-01-31 11:56:25
9743	-545	2042	414848	0	SI	2022-01-31 11:56:25
9745	-545	2042	414850	0	SI	2022-01-31 11:56:25
9747	-545	2042	414852	0	SI	2022-01-31 11:56:25
9749	-545	2042	414855	0	SI	2022-01-31 11:56:25
9751	-545	2042	414857	0	SI	2022-01-31 11:56:25
9753	-545	2042	414859	0	SI	2022-01-31 11:56:25
9755	-545	2042	414861	0	SI	2022-01-31 11:56:25
9757	-545	2042	414863	0	SI	2022-01-31 11:56:25
9759	-545	2042	414866	0	SI	2022-01-31 11:56:25
9761	-545	2042	414868	0	SI	2022-01-31 11:56:25
9763	-545	2042	414870	0	SI	2022-01-31 11:56:25
9765	-545	2042	414872	0	NO	2022-01-31 11:56:25
9767	-545	2042	414874	0	SI	2022-01-31 11:56:25
9769	-545	2042	414876	0	NO	2022-01-31 11:56:25
9771	-545	2042	414880	35	SI	2022-01-31 11:56:25
9773	-545	2042	414882	0	NO	2022-01-31 11:56:25
9775	-545	2042	414884	10	SI	2022-01-31 11:56:25
9777	-545	2042	414887	0	NO	2022-01-31 11:56:25
9779	-545	2042	414893	0	NO	2022-01-31 11:56:25
9781	-545	2042	414899	4	NO	2022-01-31 11:56:25
9783	-545	2042	414902	0	NO	2022-01-31 11:56:25
9785	-545	2042	414904	0	NO	2022-01-31 11:56:25
9787	-545	2042	419819	0	SI	2022-01-31 11:56:25
9789	-545	2042	419821	4	SI	2022-01-31 11:56:25
9791	-545	2042	419823	0	SI	2022-01-31 11:56:25
9793	-545	2042	419825	0	NO	2022-01-31 11:56:25
9648	-545	2042	414713	0	SI	2022-01-31 11:56:24
9650	-545	2042	414715	0	NO	2022-01-31 11:56:24
9652	-545	2042	414718	0	SI	2022-01-31 11:56:24
9654	-545	2042	414720	0	SI	2022-01-31 11:56:24
9656	-545	2042	414722	10	NO	2022-01-31 11:56:24
9658	-545	2042	414726	0	NO	2022-01-31 11:56:24
9660	-545	2042	414730	0	SI	2022-01-31 11:56:24
9662	-545	2042	414732	0	SI	2022-01-31 11:56:24
9664	-545	2042	414734	5	NO	2022-01-31 11:56:24
9666	-545	2042	414741	0	NO	2022-01-31 11:56:24
9668	-545	2042	414743	2	NO	2022-01-31 11:56:24
9670	-545	2042	414746	8	NO	2022-01-31 11:56:24
9672	-545	2042	414749	18	NO	2022-01-31 11:56:24
9674	-545	2042	414751	3	NO	2022-01-31 11:56:24
9676	-545	2042	414753	0	NO	2022-01-31 11:56:24
9678	-545	2042	414755	1	SI	2022-01-31 11:56:24
9680	-545	2042	414757	0	NO	2022-01-31 11:56:24
9682	-545	2042	414759	6	NO	2022-01-31 11:56:24
9684	-545	2042	414775	0	SI	2022-01-31 11:56:24
9686	-545	2042	414777	0	NO	2022-01-31 11:56:24
9688	-545	2042	414779	0	NO	2022-01-31 11:56:24
9690	-545	2042	414781	5	NO	2022-01-31 11:56:24
9692	-545	2042	414783	0	SI	2022-01-31 11:56:24
9694	-545	2042	414785	10	SI	2022-01-31 11:56:24
9696	-545	2042	414787	0	NO	2022-01-31 11:56:24
9698	-545	2042	414790	0	SI	2022-01-31 11:56:24
9700	-545	2042	414792	0	SI	2022-01-31 11:56:24
9702	-545	2042	414794	0	SI	2022-01-31 11:56:24
9704	-545	2042	414796	2	SI	2022-01-31 11:56:25
9706	-545	2042	414798	0	NO	2022-01-31 11:56:25
9708	-545	2042	414805	0	NO	2022-01-31 11:56:25
9710	-545	2042	414807	0	NO	2022-01-31 11:56:25
9712	-545	2042	414809	0	NO	2022-01-31 11:56:25
9714	-545	2042	414811	7	NO	2022-01-31 11:56:25
9716	-545	2042	414813	0	NO	2022-01-31 11:56:25
9718	-545	2042	414815	0	NO	2022-01-31 11:56:25
9720	-545	2042	414817	0	NO	2022-01-31 11:56:25
9722	-545	2042	414820	0	SI	2022-01-31 11:56:25
9724	-545	2042	414822	0	NO	2022-01-31 11:56:25
9726	-545	2042	414824	0	SI	2022-01-31 11:56:25
9728	-545	2042	414826	6	NO	2022-01-31 11:56:25
9730	-545	2042	414832	0	SI	2022-01-31 11:56:25
9732	-545	2042	414834	0	SI	2022-01-31 11:56:25
9734	-545	2042	414838	0	SI	2022-01-31 11:56:25
9736	-545	2042	414840	0	SI	2022-01-31 11:56:25
9738	-545	2042	414842	0	SI	2022-01-31 11:56:25
9740	-545	2042	414844	0	SI	2022-01-31 11:56:25
9742	-545	2042	414847	0	SI	2022-01-31 11:56:25
9744	-545	2042	414849	0	SI	2022-01-31 11:56:25
9746	-545	2042	414851	0	SI	2022-01-31 11:56:25
9748	-545	2042	414853	0	SI	2022-01-31 11:56:25
9750	-545	2042	414856	0	NO	2022-01-31 11:56:25
9752	-545	2042	414858	0	SI	2022-01-31 11:56:25
9754	-545	2042	414860	0	SI	2022-01-31 11:56:25
9756	-545	2042	414862	0	SI	2022-01-31 11:56:25
9758	-545	2042	414865	0	SI	2022-01-31 11:56:25
9760	-545	2042	414867	0	SI	2022-01-31 11:56:25
9762	-545	2042	414869	0	SI	2022-01-31 11:56:25
9764	-545	2042	414871	0	SI	2022-01-31 11:56:25
9766	-545	2042	414873	0	NO	2022-01-31 11:56:25
9768	-545	2042	414875	0	NO	2022-01-31 11:56:25
9770	-545	2042	414878	55	SI	2022-01-31 11:56:25
9772	-545	2042	414881	0	NO	2022-01-31 11:56:25
9774	-545	2042	414883	0	SI	2022-01-31 11:56:25
9776	-545	2042	414885	0	NO	2022-01-31 11:56:25
9778	-545	2042	414889	0	NO	2022-01-31 11:56:25
9780	-545	2042	414898	0	SI	2022-01-31 11:56:25
9782	-545	2042	414901	0	NO	2022-01-31 11:56:25
9784	-545	2042	414903	0	NO	2022-01-31 11:56:25
9786	-545	2042	414905	0	NO	2022-01-31 11:56:25
9788	-545	2042	419820	10	NO	2022-01-31 11:56:25
9790	-545	2042	419822	0	SI	2022-01-31 11:56:25
9792	-545	2042	419824	0	SI	2022-01-31 11:56:25
9794	-545	2042	419859	12	SI	2022-01-31 11:56:25
9795	-586	2046	415898	0	SI	2022-02-16 20:11:21
9796	-586	2046	415899	1	SI	2022-02-16 20:11:21
9797	-586	2046	415900	0	SI	2022-02-16 20:11:21
9798	-586	2046	415901	0	SI	2022-02-16 20:11:21
9799	-586	2046	415902	2	SI	2022-02-16 20:11:21
9800	-586	2046	415903	0	NO	2022-02-16 20:11:21
9801	-586	2046	415904	0	NO	2022-02-16 20:11:21
9802	-586	2046	415905	1	SI	2022-02-16 20:11:21
9803	-586	2046	415906	0	SI	2022-02-16 20:11:21
9804	-586	2046	415907	0	SI	2022-02-16 20:11:21
9805	-586	2046	415908	0	SI	2022-02-16 20:11:21
9806	-586	2046	415909	0	SI	2022-02-16 20:11:21
9807	-586	2046	415910	0	NO	2022-02-16 20:11:21
9808	-586	2046	415911	0	NO	2022-02-16 20:11:21
9809	-586	2046	415912	0	NO	2022-02-16 20:11:21
9810	-586	2046	415913	0	SI	2022-02-16 20:11:21
9811	-586	2046	415914	0	NO	2022-02-16 20:11:21
9812	-586	2046	415915	0	NO	2022-02-16 20:11:21
9813	-586	2046	415916	0	SI	2022-02-16 20:11:21
9814	-586	2046	415917	0	SI	2022-02-16 20:11:21
9815	-586	2046	415918	0	SI	2022-02-16 20:11:21
9816	-586	2046	415919	0	SI	2022-02-16 20:11:21
9817	-586	2046	415920	0	SI	2022-02-16 20:11:21
9818	-586	2046	415921	0	SI	2022-02-16 20:11:21
9819	-586	2046	415922	0	SI	2022-02-16 20:11:21
9820	-586	2046	415923	1	SI	2022-02-16 20:11:21
9821	-586	2046	415924	0	SI	2022-02-16 20:11:21
9822	-586	2046	415925	3	NO	2022-02-16 20:11:21
9823	-586	2046	415936	0	SI	2022-02-16 20:11:21
9824	-586	2046	415937	0	SI	2022-02-16 20:11:21
9825	-586	2046	415938	0	SI	2022-02-16 20:11:21
9826	-586	2046	415939	0	SI	2022-02-16 20:11:21
9827	-586	2046	415940	0	SI	2022-02-16 20:11:21
9828	-586	2046	415941	0	NO	2022-02-16 20:11:21
9829	-586	2046	415942	0	SI	2022-02-16 20:11:21
9830	-586	2046	415943	0	SI	2022-02-16 20:11:21
9831	-586	2046	415944	0	SI	2022-02-16 20:11:21
9832	-586	2046	415945	0	SI	2022-02-16 20:11:21
9833	-586	2046	415946	0	SI	2022-02-16 20:11:21
9834	-586	2046	415947	0	SI	2022-02-16 20:11:21
9835	-586	2046	415948	2	SI	2022-02-16 20:11:21
9836	-586	2046	415949	1	SI	2022-02-16 20:11:21
9837	-586	2046	415950	0	SI	2022-02-16 20:11:21
9838	-586	2046	415951	1	SI	2022-02-16 20:11:21
9839	-586	2046	415952	0	NO	2022-02-16 20:11:21
9840	-586	2046	415953	0	NO	2022-02-16 20:11:21
9841	-586	2046	415956	3	NO	2022-02-16 20:11:21
9842	-586	2046	415957	0	SI	2022-02-16 20:11:21
9843	-586	2046	415958	0	SI	2022-02-16 20:11:21
9844	-586	2046	415959	0	SI	2022-02-16 20:11:21
9845	-586	2046	415960	3	SI	2022-02-16 20:11:21
9846	-586	2046	415961	0	SI	2022-02-16 20:11:21
9847	-586	2046	415962	0	NO	2022-02-16 20:11:21
9848	-586	2046	415963	0	SI	2022-02-16 20:11:21
9849	-586	2046	415964	0	SI	2022-02-16 20:11:21
9850	-586	2046	415965	0	SI	2022-02-16 20:11:21
9851	-586	2046	415966	0	SI	2022-02-16 20:11:21
9852	-586	2046	415967	0	SI	2022-02-16 20:11:21
9853	-586	2046	415968	0	NO	2022-02-16 20:11:21
9854	-586	2046	415969	0	SI	2022-02-16 20:11:21
9855	-586	2046	415970	0	NO	2022-02-16 20:11:21
9856	-586	2046	415972	0	SI	2022-02-16 20:11:21
9857	-586	2046	415973	0	SI	2022-02-16 20:11:21
9858	-586	2046	415974	0	NO	2022-02-16 20:11:21
9859	-586	2046	415975	0	NO	2022-02-16 20:11:21
9860	-586	2046	415976	0	SI	2022-02-16 20:11:21
9861	-586	2046	415977	0	NO	2022-02-16 20:11:21
9862	-586	2046	415984	0	NO	2022-02-16 20:11:21
9863	-586	2046	415986	0	SI	2022-02-16 20:11:21
9864	-586	2046	415987	0	SI	2022-02-16 20:11:21
9865	-586	2046	415988	0	SI	2022-02-16 20:11:21
9866	-586	2046	415989	1	SI	2022-02-16 20:11:21
9867	-586	2046	415990	0	SI	2022-02-16 20:11:21
9868	-586	2046	415991	0	SI	2022-02-16 20:11:21
9869	-586	2046	415992	0	NO	2022-02-16 20:11:21
9870	-586	2046	415993	0	NO	2022-02-16 20:11:21
9871	-586	2046	415995	0	NO	2022-02-16 20:11:21
9872	-586	2046	415996	0	NO	2022-02-16 20:11:21
9873	-586	2046	415997	0	SI	2022-02-16 20:11:21
9874	-586	2046	415998	0	NO	2022-02-16 20:11:21
9875	-586	2046	415999	0	NO	2022-02-16 20:11:21
9876	-586	2046	416000	0	NO	2022-02-16 20:11:21
9877	-586	2046	416001	0	NO	2022-02-16 20:11:21
9878	-586	2046	416002	0	SI	2022-02-16 20:11:21
9879	-586	2046	416003	0	NO	2022-02-16 20:11:21
9880	-586	2046	416004	0	NO	2022-02-16 20:11:21
9881	-586	2046	416005	0	NO	2022-02-16 20:11:21
9882	-586	2046	416006	0	NO	2022-02-16 20:11:21
9883	-586	2046	416007	0	NO	2022-02-16 20:11:21
9884	-586	2046	416008	0	NO	2022-02-16 20:11:21
9885	-586	2046	416009	0	NO	2022-02-16 20:11:21
9886	-586	2046	416013	0	NO	2022-02-16 20:11:21
9887	-586	2046	416014	0	SI	2022-02-16 20:11:21
9888	-586	2046	416015	0	SI	2022-02-16 20:11:21
9889	-586	2046	416017	0	SI	2022-02-16 20:11:21
9890	-586	2046	416018	0	SI	2022-02-16 20:11:21
9891	-586	2046	416019	0	NO	2022-02-16 20:11:21
9892	-586	2046	416020	0	SI	2022-02-16 20:11:21
9893	-586	2046	416021	0	SI	2022-02-16 20:11:21
9894	-586	2046	416022	1	SI	2022-02-16 20:11:21
9895	-586	2046	416023	0	SI	2022-02-16 20:11:21
9896	-586	2046	416025	17	SI	2022-02-16 20:11:21
9897	-586	2046	416026	0	SI	2022-02-16 20:11:21
9898	-586	2046	416027	0	SI	2022-02-16 20:11:21
9899	-586	2046	416028	0	SI	2022-02-16 20:11:21
9900	-586	2046	416029	0	SI	2022-02-16 20:11:21
9901	-586	2046	416030	0	NO	2022-02-16 20:11:21
9902	-586	2046	416031	0	SI	2022-02-16 20:11:21
9903	-586	2046	416032	0	SI	2022-02-16 20:11:21
9904	-586	2046	416033	0	SI	2022-02-16 20:11:21
9905	-586	2046	416034	0	SI	2022-02-16 20:11:21
9906	-586	2046	416035	0	SI	2022-02-16 20:11:21
9907	-586	2046	416036	0	NO	2022-02-16 20:11:21
9908	-586	2046	416037	0	SI	2022-02-16 20:11:21
9909	-586	2046	416038	0	SI	2022-02-16 20:11:21
9910	-586	2046	416039	1	NO	2022-02-16 20:11:21
9911	-586	2046	416040	0	SI	2022-02-16 20:11:21
9912	-586	2046	416041	0	SI	2022-02-16 20:11:21
9913	-586	2046	416042	0	SI	2022-02-16 20:11:21
9914	-586	2046	416043	0	SI	2022-02-16 20:11:21
9915	-586	2046	416044	0	SI	2022-02-16 20:11:21
9916	-586	2046	416045	0	SI	2022-02-16 20:11:21
9917	-586	2046	416046	0	SI	2022-02-16 20:11:21
9918	-586	2046	416048	0	NO	2022-02-16 20:11:21
9919	-586	2046	416049	0	SI	2022-02-16 20:11:21
9920	-586	2046	416050	0	SI	2022-02-16 20:11:21
9921	-586	2046	416051	0	SI	2022-02-16 20:11:21
9922	-586	2046	416052	0	SI	2022-02-16 20:11:21
9923	-586	2046	416054	0	SI	2022-02-16 20:11:21
9924	-586	2046	416055	2	SI	2022-02-16 20:11:21
9925	-586	2046	416056	0	SI	2022-02-16 20:11:21
9926	-586	2046	416057	0	SI	2022-02-16 20:11:21
9927	-586	2046	416058	0	SI	2022-02-16 20:11:21
9928	-586	2046	416059	0	SI	2022-02-16 20:11:21
9929	-586	2046	416060	0	SI	2022-02-16 20:11:21
9930	-586	2046	416061	0	SI	2022-02-16 20:11:21
9931	-586	2046	416062	0	SI	2022-02-16 20:11:21
9932	-586	2046	416064	0	SI	2022-02-16 20:11:21
9933	-586	2046	416065	0	SI	2022-02-16 20:11:21
9934	-586	2046	416066	0	SI	2022-02-16 20:11:21
9935	-586	2046	416067	0	SI	2022-02-16 20:11:21
9936	-586	2046	416068	2	SI	2022-02-16 20:11:21
9937	-586	2046	416069	0	NO	2022-02-16 20:11:21
9938	-586	2046	416070	0	SI	2022-02-16 20:11:21
9939	-586	2046	416071	0	NO	2022-02-16 20:11:21
9940	-586	2046	416073	8	SI	2022-02-16 20:11:21
9941	-586	2046	416074	0	NO	2022-02-16 20:11:21
9942	-586	2046	416075	0	NO	2022-02-16 20:11:21
9943	-586	2046	416076	0	NO	2022-02-16 20:11:21
9944	-586	2046	416077	0	SI	2022-02-16 20:11:21
9945	-586	2046	416078	0	NO	2022-02-16 20:11:21
9946	-586	2046	416079	0	NO	2022-02-16 20:11:21
9947	-586	2046	416085	0	NO	2022-02-16 20:11:21
9948	-586	2046	416086	0	NO	2022-02-16 20:11:21
9949	-586	2046	416087	0	NO	2022-02-16 20:11:21
9950	-586	2046	416088	0	NO	2022-02-16 20:11:21
9951	-586	2046	419806	8	NO	2022-02-16 20:11:21
\.


--
-- Name: access_log_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.access_log_id_seq', 1248, true);


--
-- Name: appdocu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.appdocu_id_seq', 10, true);


--
-- Name: log_checklist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.log_checklist_id_seq', 206, true);


--
-- Name: public_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.public_user_id_seq', 664, true);


--
-- Name: utente_risposta_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.utente_risposta_id_seq', 9951, true);


--
-- PostgreSQL database dump complete
--

