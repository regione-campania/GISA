--
-- PostgreSQL database dump
--

-- Started on 2010-08-04 15:39:32

SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 3581 (class 1259 OID 3554702)
-- Dependencies: 4856 6
-- Name: codici_qualifica_sanitaria_allevamenti; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE codici_qualifica_sanitaria_allevamenti (
    code text NOT NULL,
    description character varying(100) NOT NULL,
    enabled boolean DEFAULT true
);


ALTER TABLE public.codici_qualifica_sanitaria_allevamenti OWNER TO postgres;

--
-- TOC entry 4859 (class 0 OID 3554702)
-- Dependencies: 3581
-- Data for Name: codici_qualifica_sanitaria_allevamenti; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('A', 'ACCREDITATO', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('B', 'NON UFFICIALMENTE INDENNE - ULTIMO CAMPIONE POSITIVO', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('C', 'INDENNE O UFFICIALMENTE INDENNE - SOSPESO', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('CO', 'INDENNE O UFFICIALMENTE INDENNE - SOSPESO', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('D', 'NON DISPONIBILE', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('E', 'ESENTE', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('F', 'NON DISPONIBILE', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('I', 'INDENNE', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('M', 'NON INDENNE - NON UFFICIALMENTE INDENNE - ULTIMO CAMPIONE POSITIVO', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('N', 'NON ACCREDITATO', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('NE', 'NON ESENTE', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('O', 'UFFICIALMENTE INDENNE', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('P', 'INDENNE', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('R', 'NON DISPONIBILE', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('S', 'SCONOSCIUTO', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('SO', 'SCONOSCIUTO', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('T', 'NON DISPONIBILE', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('TO', 'NON DISPONIBILE', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('U', 'UFFICIALMENTE INDENNE', true);
INSERT INTO codici_qualifica_sanitaria_allevamenti (code, description, enabled) VALUES ('Z', 'NON INDENNE - NON UFFICIALMENTE INDENNE - ULTIMO CAMPIONE POSITIVO', true);


--
-- TOC entry 4858 (class 2606 OID 3601113)
-- Dependencies: 3581 3581
-- Name: codici_qualifica_sanitaria_allevamenti_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY codici_qualifica_sanitaria_allevamenti
    ADD CONSTRAINT codici_qualifica_sanitaria_allevamenti_pkey PRIMARY KEY (code);


-- Completed on 2010-08-04 15:39:33

--
-- PostgreSQL database dump complete
--

