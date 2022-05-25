--
-- PostgreSQL database dump
--

-- Started on 2010-08-04 15:21:30

SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

--
-- TOC entry 4865 (class 0 OID 0)
-- Dependencies: 4521
-- Name: lookup_specie_allevata_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('lookup_specie_allevata_code_seq', 1, false);


--
-- TOC entry 4862 (class 0 OID 3556842)
-- Dependencies: 4005
-- Data for Name: lookup_specie_allevata; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (128, 'CONIGLI', 'CONIGLI', false, 6, false, NULL);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (130, 'API', 'API', false, 6, false, NULL);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (131, 'GALLUS', 'GALLUS', false, 6, true, 4);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (132, 'TACCHINI', 'TACCHINI', false, 6, true, 4);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (133, 'PERNICI', 'PERNICI', false, 6, true, 4);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (134, 'QUAGLIE', 'QUAGLIE', false, 6, true, 4);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (135, 'STARNE', 'STARNE', false, 6, true, 4);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (136, 'PICCIONI', 'PICCIONI', false, 6, true, 4);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (137, 'OCHE', 'OCHE', false, 6, true, 4);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (138, 'FARAONE', 'FARAONE', false, 6, true, 4);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (139, 'FAGIANI', 'FAGIANI', false, 6, true, 4);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (140, 'STRUZZI', 'STRUZZI', false, 6, true, 4);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (141, 'ANATRE', 'ANATRE', false, 6, true, 4);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (142, 'COLOMBE', 'COLOMBE', false, 6, true, 4);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (143, 'EMU', 'EMU', false, 6, true, 4);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (146, 'AVICOLI MISTI', 'AVICOLI MISTI', false, 6, true, 4);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (148, 'BARDOTTI', 'BARDOTTI', false, 6, true, 5);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (147, 'MULI', 'MULI', false, 6, true, 5);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (144, 'VOLATILI PER RICHIAMI VIVI', 'VOLATILI PER RICHIAMI VIVI', false, 6, true, 4);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (111, 'ALTRE SPECI', 'ALTRE SPECI', false, 7, false, NULL);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (150, 'ERMELLINI', 'ERMELLINI', false, 6, true, 6);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (151, 'RANE', 'RANE', false, 6, true, 6);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (152, 'LUMACHE', 'LUMACHE', false, 6, true, 6);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (160, 'PESCI', 'PESCI', false, 6, true, 6);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (121, 'BOVINI', 'BOVINI', false, 0, true, 1);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (122, 'SUINI', 'SUINI', false, 5, true, 3);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (124, 'OVINI', 'OVINI', false, 4, true, 2);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (125, 'CAPRINI', 'CAPRINI', false, 2, true, 2);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (126, 'CAVALLI', 'CAVALLI', false, 3, true, 5);
INSERT INTO lookup_specie_allevata (code, description, short_description, default_item, level, enabled, codice_categoria) VALUES (129, 'BUFALINI', 'BUFALINI', false, 1, true, 1);


-- Completed on 2010-08-04 15:21:30

--
-- PostgreSQL database dump complete
--

