--
-- PostgreSQL database dump
--

-- Started on 2010-08-04 13:12:51

SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

--
-- TOC entry 5749 (class 0 OID 0)
-- Dependencies: 5368
-- Name: lookup_orientamento_produttivo_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('lookup_orientamento_produttivo_code_seq', 1, false);


--
-- TOC entry 5746 (class 0 OID 1513827)
-- Dependencies: 5369
-- Data for Name: lookup_orientamento_produttivo; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (43, 'DA RIPRODUZIONE', false, 42, true, false, false, true, false, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (44, 'PRODUZIONE DA AUTOCONSUMO', false, 43, true, false, false, true, false, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (45, 'PRODUZIONE DA INGRASSO', false, 44, true, false, false, true, false, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (42, 'LANA', false, 41, true, false, false, false, true, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (40, 'IPPODROMO', false, 39, true, false, true, false, false, false, 'IPPODROMO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (41, 'MANEGGIO', false, 40, true, false, true, false, false, false, 'MANEGGIO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (38, 'PRODUZIONE (SENZA RIPRODUTTORE)', false, 37, true, false, true, false, false, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (2, 'CARNE', false, 1, true, true, false, false, true, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (39, 'RIPRODUZIONE (CON FATTRICI)', false, 38, true, false, true, false, false, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (37, 'LAVORO', false, 36, true, false, true, false, false, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (1, 'MISTO', false, 0, true, true, false, false, true, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (3, 'LATTE', false, 2, true, true, false, false, true, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (4, 'CENTRO RACCOLTA SPERMA', false, 3, true, true, true, true, true, false, 'CENTRO MATERIALE GENETICO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (5, 'GRUPPO RACCOLTA EMBRIONI', false, 4, true, true, true, true, true, false, 'CENTRO MATERIALE GENETICO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (6, 'CENTRO MAGAZZINAGGIO', false, 5, true, true, true, true, true, false, 'CENTRO MATERIALE GENETICO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (30, 'CARNE CON FATTRICI', false, 29, true, false, true, false, false, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (7, 'CENTRO GENETICO', false, 6, true, true, true, true, true, false, 'CENTRO MATERIALE GENETICO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (8, 'CENTRO GENETICO E QUARANTENA', false, 7, true, true, true, true, true, false, 'CENTRO MATERIALE GENETICO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (9, 'CENTRO QUARANTENA', false, 8, true, true, true, true, true, false, 'CENTRO MATERIALE GENETICO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (10, 'CENTRO RACCOLTA', false, 9, true, true, true, true, true, false, 'CENTRO RACCOLTA');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (11, 'DA ALLEVAMENTO', false, 10, true, true, true, true, true, false, 'STALLA DI SOSTA');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (12, 'DA MACELLO', false, 11, true, true, true, true, true, false, 'STALLA DI SOSTA');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (31, 'CARNE SENZA FATTRICI', false, 30, true, false, true, false, false, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (32, 'DIPORTO - IPPICO SPORTIVI', false, 31, true, false, true, false, false, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (15, 'CENTRO DI RACCOLTA', false, 14, true, true, false, false, true, false, 'CENTRO DI RACCOLTA');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (13, 'DA ALLEVAMENTO/MACELLO
', false, 12, true, true, false, true, true, false, 'STALLA DI SOSTA');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (14, 'STABULARIO
', false, 13, true, true, false, true, true, false, 'STABULARIO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (16, 'PRODUZIONE MIELE', false, 15, true, false, false, false, false, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (17, 'LINEA DA CARNE', false, 16, true, false, false, false, false, true, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (33, 'EQUESTRE CON FATTRICI', false, 32, true, false, true, false, false, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (34, 'EQUESTRE SENZA FATTRICI', false, 33, true, false, true, false, false, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (36, 'IPPICO SENZA FATTRICI', false, 35, true, false, true, false, false, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (35, 'IPPICO CON FATTRICI', false, 34, true, false, true, false, false, false, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (29, 'PUNTO DI SOSTA', false, 28, true, true, false, true, true, true, 'PUNTO DI SOSTA');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (18, 'LINEA DA UOVA', false, 17, true, false, false, false, false, true, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (26, 'TITOLARE POSTAZIONE FISSA', false, 25, true, false, false, false, false, true, 'RICHIAMI VIVI');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (27, 'SVEZZAMENTO', false, 26, true, false, false, false, false, true, 'CENTRO SVEZZAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (24, 'AZIENDA FAUNISTICA VENATORIA', false, 23, true, false, false, false, false, true, 'RICHIAMI VIVI');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (25, 'INDIVIDUALE APPOSTAMENTO MOBILE', false, 24, true, false, false, false, false, true, 'RICHIAMI VIVI');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (23, 'INCUBATOI', false, 22, true, false, false, false, false, true, 'INCUBATOIO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (22, 'COMMERCIO AL DETTAGLIO', false, 21, true, false, false, false, false, true, 'COMMERCIANTE AL DETTAGLIO SEDE FISSA');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (28, 'NON INDICATIVO', false, 27, true, false, false, false, false, true, 'GRUPPO VOLATILI SENTINELLA');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (19, 'RIPOPOLAMENTO SELVAGGINA', false, 18, true, false, false, false, false, true, 'ALLEVAMENTO');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (20, 'COMMERCIO AL DETTAGLIO', false, 19, true, false, false, false, false, true, 'COMMERCIANTE AL DETTAGLIO AMBULANTE');
INSERT INTO lookup_orientamento_produttivo (code, description, default_item, level, enabled, enabled_bovini_bufalini, enabled_equini, enabled_suini, enabled_ovini_caprini, enabled_avicoli, tipo_struttura) VALUES (21, 'COMMERCIO INGROSSO', false, 20, true, false, false, false, false, true, 'COMMERCIANTE INGROSSO');


-- Completed on 2010-08-04 13:13:02

--
-- PostgreSQL database dump complete
--

