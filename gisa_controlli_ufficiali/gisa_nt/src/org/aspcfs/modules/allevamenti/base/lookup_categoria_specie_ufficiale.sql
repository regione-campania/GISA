--
-- PostgreSQL database dump
--

-- Started on 2010-08-04 15:20:49

SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

--
-- TOC entry 4865 (class 0 OID 0)
-- Dependencies: 4550
-- Name: lookup_categoria_specie_allevata_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('lookup_categoria_specie_allevata_code_seq', 1, false);


--
-- TOC entry 4862 (class 0 OID 3614280)
-- Dependencies: 4551
-- Data for Name: lookup_categoria_specie_allevata; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO lookup_categoria_specie_allevata (code, description, short_description, default_item, level, enabled) VALUES (1, 'BOVINI / BUFALINI', NULL, false, 0, true);
INSERT INTO lookup_categoria_specie_allevata (code, description, short_description, default_item, level, enabled) VALUES (2, 'OVINI / CAPRINI', NULL, false, 1, true);
INSERT INTO lookup_categoria_specie_allevata (code, description, short_description, default_item, level, enabled) VALUES (3, 'SUINI', NULL, false, 2, true);
INSERT INTO lookup_categoria_specie_allevata (code, description, short_description, default_item, level, enabled) VALUES (4, 'AVICOLI', NULL, false, 3, true);
INSERT INTO lookup_categoria_specie_allevata (code, description, short_description, default_item, level, enabled) VALUES (6, 'ALTRE SPECI', NULL, false, 5, true);
INSERT INTO lookup_categoria_specie_allevata (code, description, short_description, default_item, level, enabled) VALUES (5, 'EQUIDI', NULL, false, 4, true);


-- Completed on 2010-08-04 15:20:50

--
-- PostgreSQL database dump complete
--

