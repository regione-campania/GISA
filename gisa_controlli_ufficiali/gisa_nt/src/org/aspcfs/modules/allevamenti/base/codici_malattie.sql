--
-- PostgreSQL database dump
--

-- Started on 2010-08-04 15:39:13

SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 3580 (class 1259 OID 3554695)
-- Dependencies: 4856 6
-- Name: codici_malattie_allevamenti; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE codici_malattie_allevamenti (
    code text NOT NULL,
    description character varying(50) NOT NULL,
    enabled boolean DEFAULT true
);


ALTER TABLE public.codici_malattie_allevamenti OWNER TO postgres;

--
-- TOC entry 4859 (class 0 OID 3554695)
-- Dependencies: 3580
-- Data for Name: codici_malattie_allevamenti; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO codici_malattie_allevamenti (code, description, enabled) VALUES ('AUJ', 'AUJESKY', true);
INSERT INTO codici_malattie_allevamenti (code, description, enabled) VALUES ('BRC', 'BRUCELLOSI', true);
INSERT INTO codici_malattie_allevamenti (code, description, enabled) VALUES ('LEU', 'LEUCOSI', true);
INSERT INTO codici_malattie_allevamenti (code, description, enabled) VALUES ('MO1', 'NON DISPONIBILE', true);
INSERT INTO codici_malattie_allevamenti (code, description, enabled) VALUES ('PSA', 'PESTE SUINA AFRICANA', true);
INSERT INTO codici_malattie_allevamenti (code, description, enabled) VALUES ('PES', 'PESTE SUINA CLASSICA', true);
INSERT INTO codici_malattie_allevamenti (code, description, enabled) VALUES ('TUB', 'TUBERCOLOSI', true);
INSERT INTO codici_malattie_allevamenti (code, description, enabled) VALUES ('VES', 'VESCICOLARE', true);


--
-- TOC entry 4858 (class 2606 OID 3601111)
-- Dependencies: 3580 3580
-- Name: codici_malattie_allevamenti_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY codici_malattie_allevamenti
    ADD CONSTRAINT codici_malattie_allevamenti_pkey PRIMARY KEY (description);


-- Completed on 2010-08-04 15:39:14

--
-- PostgreSQL database dump complete
--

