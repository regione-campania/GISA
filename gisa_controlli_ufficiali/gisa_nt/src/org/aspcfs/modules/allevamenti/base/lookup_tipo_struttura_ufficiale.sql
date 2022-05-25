--
-- PostgreSQL database dump
--

-- Started on 2010-08-04 13:08:35

SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

--
-- TOC entry 5749 (class 0 OID 0)
-- Dependencies: 5370
-- Name: lookup_tipologia_struttura_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('lookup_tipologia_struttura_code_seq', 1, true);


--
-- TOC entry 5746 (class 0 OID 1513837)
-- Dependencies: 5371
-- Data for Name: lookup_tipologia_struttura; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO lookup_tipologia_struttura (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli) VALUES (6, 'STALLA DI SOSTA', false, 5, true, true, true, true, true, false);
INSERT INTO lookup_tipologia_struttura (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli) VALUES (1, 'ALLEVAMENTO', false, 0, true, true, true, true, true, true);
INSERT INTO lookup_tipologia_struttura (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli) VALUES (7, 'COMMERCIANTE AL DETTAGLIO AMBULANTE', false, 6, true, false, false, false, false, true);
INSERT INTO lookup_tipologia_struttura (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli) VALUES (8, 'COMMERCIANTE AL DETTAGLIO SEDE FISSA', false, 7, true, false, false, false, false, true);
INSERT INTO lookup_tipologia_struttura (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli) VALUES (9, 'COMMERCIANTE INGROSSO', false, 8, true, false, false, false, false, true);
INSERT INTO lookup_tipologia_struttura (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli) VALUES (10, ' INCUBATOIO', false, 9, true, false, false, false, false, true);
INSERT INTO lookup_tipologia_struttura (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli) VALUES (11, ' RICHIAMI VIVI', false, 10, true, false, false, false, false, true);
INSERT INTO lookup_tipologia_struttura (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli) VALUES (12, 'CENTRO SVEZZAMENTO', false, 11, true, false, false, false, false, true);
INSERT INTO lookup_tipologia_struttura (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli) VALUES (13, 'GRUPPO VOLATILI SENTINELLA', false, 12, true, false, false, false, false, true);
INSERT INTO lookup_tipologia_struttura (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli) VALUES (14, 'IPPODROMO', false, 13, true, false, true, false, false, false);
INSERT INTO lookup_tipologia_struttura (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli) VALUES (15, 'MANEGGIO', false, 14, true, false, true, false, false, false);
INSERT INTO lookup_tipologia_struttura (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli) VALUES (2, 'CENTRO MATERIALE GENETICO', false, 1, true, true, true, true, true, false);
INSERT INTO lookup_tipologia_struttura (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli) VALUES (3, 'CENTRO RACCOLTA', false, 2, true, true, true, true, true, false);
INSERT INTO lookup_tipologia_struttura (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli) VALUES (4, 'PUNTO DI SOSTA', false, 3, true, true, false, true, true, false);
INSERT INTO lookup_tipologia_struttura (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli) VALUES (5, 'STABULARIO', false, 4, true, true, false, true, true, false);


-- Completed on 2010-08-04 13:08:44

--
-- PostgreSQL database dump complete
--

