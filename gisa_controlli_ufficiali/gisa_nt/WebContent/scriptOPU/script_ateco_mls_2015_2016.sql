
/*************************************************************************/
/*************************************************************************/
/******* mapping codice ateco --> master list 2015/2016 (21/12/2015) *********/
/*************************************************************************/
/*************************************************************************/


CREATE TABLE mapping_codice_ateco_master_list_2015_2016
(
  id serial NOT NULL,
  codice_ateco character varying(30) NOT NULL,
  id_macroarea integer,
  id_aggregazione integer,
  id_attivita integer,
  id_nuova_linea integer,
  id_caratterizzazione_specifica integer,
  livello integer,
  macroarea text,
  aggregazione text,
  attivita text,
  descrizione text,
  dettaglio text,
  note text,
  orgid integer,
  entered timestamp(3) without time zone NOT NULL DEFAULT now(),
  enabled boolean DEFAULT true,
  CONSTRAINT mapping_codice_ateco_master_list_2015_2016_pkey PRIMARY KEY (codice_ateco)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mapping_codice_ateco_master_list_2015_2016
  OWNER TO postgres;
GRANT ALL ON TABLE mapping_codice_ateco_master_list_2015_2016 TO postgres;
GRANT SELECT ON TABLE mapping_codice_ateco_master_list_2015_2016 TO report;
GRANT SELECT ON TABLE mapping_codice_ateco_master_list_2015_2016 TO usr_ro;
-----------------------------------------------------------------------------------------------------------------------------
-- 01.70.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('01.70.00', 22, 23, 25, 25, 4,'PRODUZIONE PRIMARIA REGISTRABILI','CACCIA - ATTIVITà REGISTRATE 852','PUNTO DI RACCOLTA PER LA CACCIA ','PUNTO DI RACCOLTA PER LA CACCIA ');
-- 02.30.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('02.30.00', 22, 28, 32, 32, 4,'PRODUZIONE PRIMARIA REGISTRABILI','RACCOLTA VEGETALI SPONTANEI PER LA COMMERCIALIZZAZIONE','RACCOLTA DI VEGETALI SELVATICI PER LA COMMERCIALIZZAZIONE, ESCLUSI FUNGHI E TARTUFI','RACCOLTA DI VEGETALI SELVATICI PER LA COMMERCIALIZZAZIONE, ESCLUSI FUNGHI E TARTUFI');
-- 10.31.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.31.00', 22, 30, 36, 36, 4,'PRODUZIONE PRIMARIA REGISTRABILI','COLTIVAZIONI NON PERMANENTI AD USO ALIMENTARE UMANO ','ALTRE COLTIVAZIONI ESCLUSI FUNGHI','ALTRE COLTIVAZIONI ESCLUSI FUNGHI');
-- 11.06.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('11.06.00', 22, 30, 36, 36, 4,'PRODUZIONE PRIMARIA REGISTRABILI','COLTIVAZIONI NON PERMANENTI AD USO ALIMENTARE UMANO ','ALTRE COLTIVAZIONI ESCLUSI FUNGHI','ALTRE COLTIVAZIONI ESCLUSI FUNGHI');
-- 01.28.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('01.28.00', 22, 30, 36, 36, 4,'PRODUZIONE PRIMARIA REGISTRABILI','COLTIVAZIONI NON PERMANENTI AD USO ALIMENTARE UMANO ','ALTRE COLTIVAZIONI ESCLUSI FUNGHI','ALTRE COLTIVAZIONI ESCLUSI FUNGHI');
-- 01.47.00(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('01.47.00', 22, 37, 71, null, 3,'PRODUZIONE PRIMARIA REGISTRABILI','ATTIVITA'' INERENTI GLI ANIMALI','ALLEVAMENTO AVICOLI',null);
-- 01.43.00(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('01.43.00', 22, 37, 89, null, 3,'PRODUZIONE PRIMARIA REGISTRABILI','ATTIVITA'' INERENTI GLI ANIMALI','ALLEVAMENTO EQUIDI',null);
-- 01.45.00(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('01.45.00', 22, 37, 108, null, 3,'PRODUZIONE PRIMARIA REGISTRABILI','ATTIVITA'' INERENTI GLI ANIMALI','ALLEVAMENTO   OVINI E/O CAPRINI',null);
-- 01.46.00(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('01.46.00', 22, 37, 111, null, 3,'PRODUZIONE PRIMARIA REGISTRABILI','ATTIVITA'' INERENTI GLI ANIMALI','ALLEVAMENTO  SUINI',null);
-- 01.49.10
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('01.49.10', 22, 37, 118, 118, 4,'PRODUZIONE PRIMARIA REGISTRABILI','ATTIVITA'' INERENTI GLI ANIMALI','ALLEVAMENTO CUNICOLO','ALLEVAMENTO CUNICOLO');
-- 01.49.30(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('01.49.30', 22, 37, 542, null, 3,'PRODUZIONE PRIMARIA REGISTRABILI','ATTIVITA'' INERENTI GLI ANIMALI','APICOLTURA',null);
-- 01.50.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('01.50.00', 556, 561, 569, 569, 4,'MANGIMISTICA IN GENERE (STABILIMENTI REGISTRATI)','OPERATORI PRIMARI REGISTRATI AI SENSI DEL REG. CE 183/2005 ART. 5, COMMA 1','AGRICOLTORI CHE PRODUCONO PRODOTTI PRIMARI PER L''ALIMENTAZIONE ANIMALE (COLTIVAZIONE, RACCOLTA, ESSICAZIONE NATURALE,STOCCAGGIO IN AZIENDA  E TRASPORTO FINO AL PRIMO STABILIMENTO)               ','AGRICOLTORI CHE PRODUCONO PRODOTTI PRIMARI PER L''ALIMENTAZIONE ANIMALE (COLTIVAZIONE, RACCOLTA, ESSICAZIONE NATURALE,STOCCAGGIO IN AZIENDA  E TRASPORTO FINO AL PRIMO STABILIMENTO)               ');
-- 10.39.00(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.39.00', 655, 656, null, null, 2,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','LAVORAZIONE DI FRUTTA E DI ORTAGGI (ESCLUSI I SUCCHI DI FRUTTA E DI ORTAGGI)',null,null);
-- 10.84.00(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.84.00', 655, 656, null, null, 2,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','LAVORAZIONE DI FRUTTA E DI ORTAGGI (ESCLUSI I SUCCHI DI FRUTTA E DI ORTAGGI)',null,null);
-- 10.32.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.32.00', 655, 668, 674, 674, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','PRODUZIONE DI BEVANDE DI FRUTTA/ORTAGGI','PRODUZIONE DI SUCCHI DI FRUTTA E DI ORTAGGI','PRODUZIONE DI SUCCHI DI FRUTTA E DI ORTAGGI');
-- 10.41.10
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.41.10', 655, 671, 677, 685, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','PRODUZIONE DI OLII E GRASSI VEGETALI','PRODUZIONE DI OLIO','PRODUZIONE DI OLIO D''OLIVA');
-- 10.41.20
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.41.20', 655, 671, 677, 686, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','PRODUZIONE DI OLII E GRASSI VEGETALI','PRODUZIONE DI OLIO','PRODUZIONE DI ALTRI OLII');
-- 10.42.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.42.00', 655, 671, 680, 680, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','PRODUZIONE DI OLII E GRASSI VEGETALI','PRODUZIONE DI GRASSI VEGETALI','PRODUZIONE DI GRASSI VEGETALI');
-- 11.01.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('11.01.00', 655, 675, 682, 682, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','PRODUZIONE DI BEVANDE ALCOLICHE','DISTILLAZIONE, RETTIFICA E MISCELATURA DEGLI ALCOLICI','DISTILLAZIONE, RETTIFICA E MISCELATURA DEGLI ALCOLICI');
-- 11.02.10
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('11.02.10', 655, 675, 687, 687, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','PRODUZIONE DI BEVANDE ALCOLICHE','PRODUZIONE DI VINI, MOSTI, ACETI','PRODUZIONE DI VINI, MOSTI, ACETI');
-- 11.02.20
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('11.02.20', 655, 675, 687, 687, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','PRODUZIONE DI BEVANDE ALCOLICHE','PRODUZIONE DI VINI, MOSTI, ACETI','PRODUZIONE DI VINI, MOSTI, ACETI');
-- 11.03.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('11.03.00', 655, 675, 688, 688, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','PRODUZIONE DI BEVANDE ALCOLICHE','PRODUZIONE DI SIDRO E DI ALTRI VINI A BASE DI FRUTTA','PRODUZIONE DI SIDRO E DI ALTRI VINI A BASE DI FRUTTA');
-- 11.05.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('11.05.00', 655, 675, 690, 690, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','PRODUZIONE DI BEVANDE ALCOLICHE','PRODUZIONE DI BIRRA, MALTO E ALTRE BEVANDE FERMENTATE NON DISTILLATE','PRODUZIONE DI BIRRA, MALTO E ALTRE BEVANDE FERMENTATE NON DISTILLATE');
-- 11.04.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('11.04.00', 655, 675, 690, 690, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','PRODUZIONE DI BEVANDE ALCOLICHE','PRODUZIONE DI BIRRA, MALTO E ALTRE BEVANDE FERMENTATE NON DISTILLATE','PRODUZIONE DI BIRRA, MALTO E ALTRE BEVANDE FERMENTATE NON DISTILLATE');
-- 10.61.10
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.61.10', 655, 683, 692, 692, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','LAVORAZIONE DI CEREALI, SEMI, LEGUMI E TUBERI','MOLITURA DI FRUMENTO E ALTRI CEREALI','MOLITURA DI FRUMENTO E ALTRI CEREALI');
-- 10.61.20
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.61.20', 655, 683, 692, 692, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','LAVORAZIONE DI CEREALI, SEMI, LEGUMI E TUBERI','MOLITURA DI FRUMENTO E ALTRI CEREALI','MOLITURA DI FRUMENTO E ALTRI CEREALI');
-- 10.61.30
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.61.30', 655, 683, 694, 694, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','LAVORAZIONE DI CEREALI, SEMI, LEGUMI E TUBERI','LAVORAZIONE DEL RISO','LAVORAZIONE DEL RISO');
-- 10.61.40
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.61.40', 655, 683, 696, 696, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','LAVORAZIONE DI CEREALI, SEMI, LEGUMI E TUBERI','ALTRE LAVORAZIONI DI SEMI, GRANAGLIE E LEGUMI','ALTRE LAVORAZIONI DI SEMI, GRANAGLIE E LEGUMI');
-- 10.62.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.62.00', 655, 683, 698, 698, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','LAVORAZIONE DI CEREALI, SEMI, LEGUMI E TUBERI','PRODUZIONE DI AMIDI E DI PRODOTTI AMIDACEI','PRODUZIONE DI AMIDI E DI PRODOTTI AMIDACEI');
-- 10.81.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.81.00', 655, 691, 700, 700, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','PRODUZIONE DI ZUCCHERO ','PRODUZIONE DI ZUCCHERO','PRODUZIONE DI ZUCCHERO');
-- 10.83.01
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.83.01', 655, 693, 702, 702, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','LAVORAZIONE DEL  CAFFè','LAVORAZIONE DEL CAFFè','LAVORAZIONE DEL CAFFè');
-- 10.83.02
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.83.02', 655, 695, 704, 704, 4,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI ','LAVORAZIONE DEL Tè ED ALTRI VEGETALI PER INFUSI','LAVORAZIONE DEL  Tè E DI ALTRI PREPARATI PER INFUSI','LAVORAZIONE DEL  Tè E DI ALTRI PREPARATI PER INFUSI');
-- 10.73.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.73.00', 689, 697, 706, 706, 4,'PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO','PRODUZIONE DI PASTA SECCA E/O FRESCA, CUSCUS E ALTRI PRODOTTI FARINACEI SIMILI','PRODUZIONE DI PASTA SECCA E/O FRESCA, CUSCUS E ALTRI PRODOTTI FARINACEI SIMILI','PRODUZIONE DI PASTA SECCA E/O FRESCA, CUSCUS E ALTRI PRODOTTI FARINACEI SIMILI');
-- 10.71.10
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.71.10', 689, 699, 708, 708, 4,'PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO','PRODUZIONE DI PANE, PIZZA E PRODOTTI DA FORNO E DI PASTICCERIA - FRESCHI E SECCHI','PRODUZIONE DI PANE, PIZZA E PRODOTTI DA FORNO E DI PASTICCERIA - FRESCHI E SECCHI','PRODUZIONE DI PANE, PIZZA E PRODOTTI DA FORNO E DI PASTICCERIA - FRESCHI E SECCHI');
-- 10.71.20
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.71.20', 689, 699, 708, 711, 4,'PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO','PRODUZIONE DI PANE, PIZZA E PRODOTTI DA FORNO E DI PASTICCERIA - FRESCHI E SECCHI','PRODUZIONE DI PANE, PIZZA E PRODOTTI DA FORNO E DI PASTICCERIA - FRESCHI E SECCHI','PRODUZIONE DI PRODOTTI DI PASTICCERIA FRESCHI E SECCHI');
-- 10.72.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.72.00', 689, 699, 708, 711, 4,'PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO','PRODUZIONE DI PANE, PIZZA E PRODOTTI DA FORNO E DI PASTICCERIA - FRESCHI E SECCHI','PRODUZIONE DI PANE, PIZZA E PRODOTTI DA FORNO E DI PASTICCERIA - FRESCHI E SECCHI','PRODUZIONE DI PRODOTTI DI PASTICCERIA FRESCHI E SECCHI');
-- 56.10.30
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('56.10.30', 689, 703, 713, 713, 4,'PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO','PRODUZIONE DI PRODOTTI DI GELATERIA (IN IMPIANTI NON RICONOSCIUTI)','PRODUZIONE DI PRODOTTI DI GELATERIA (IN IMPIANTI NON RICONOSCIUTI)','PRODUZIONE DI PRODOTTI DI GELATERIA (IN IMPIANTI NON RICONOSCIUTI)');
-- 56.10.41
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('56.10.41', 689, 703, 713, 713, 4,'PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO','PRODUZIONE DI PRODOTTI DI GELATERIA (IN IMPIANTI NON RICONOSCIUTI)','PRODUZIONE DI PRODOTTI DI GELATERIA (IN IMPIANTI NON RICONOSCIUTI)','PRODUZIONE DI PRODOTTI DI GELATERIA (IN IMPIANTI NON RICONOSCIUTI)');
-- 10.52.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.52.00', 689, 703, 713, 713, 4,'PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO','PRODUZIONE DI PRODOTTI DI GELATERIA (IN IMPIANTI NON RICONOSCIUTI)','PRODUZIONE DI PRODOTTI DI GELATERIA (IN IMPIANTI NON RICONOSCIUTI)','PRODUZIONE DI PRODOTTI DI GELATERIA (IN IMPIANTI NON RICONOSCIUTI)');
-- 10.82.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.82.00', 689, 705, 714, 714, 4,'PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO','PRODUZIONE E LAVORAZIONE DEL CIOCCOLATO, PRODUZIONE PASTIGLIAGGI, GOMME, CONFETTI,CARAMELLE, ECC.','PRODUZIONE E LAVORAZIONE DEL CIOCCOLATO, PRODUZIONE PASTIGLIAGGI, GOMME, CONFETTI,CARAMELLE, ECC.','PRODUZIONE E LAVORAZIONE DEL CIOCCOLATO, PRODUZIONE PASTIGLIAGGI, GOMME, CONFETTI,CARAMELLE, ECC.');
-- 10.85.01
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.85.01', 689, 707, 716, 716, 4,'PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)');
-- 10.85.02
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.85.02', 689, 707, 716, 716, 4,'PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)');
-- 10.85.03
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.85.03', 689, 707, 716, 716, 4,'PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)');
-- 10.85.04
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.85.04', 689, 707, 716, 716, 4,'PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)');
-- 10.85.05
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.85.05', 689, 707, 716, 716, 4,'PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)');
-- 10.85.09
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.85.09', 689, 707, 716, 716, 4,'PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)');
-- 56.10.20
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('56.10.20', 689, 707, 716, 716, 4,'PRODOTTI DA FORNO E DI PASTICCERIA, GELATI E PIATTI PRONTI - PRODUZIONE, TRASFORMAZIONE E CONGELAMENTO','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)','PRODUZIONE DI CIBI PRONTI IN GENERE (PRODOTTI DI GASTRONOMIA, DI ROSTICCERIA, DI FRIGGITORIA, ECC.)');
-- 11.07.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('11.07.00', 701, 709, 717, 717, 4,'ALTRI ALIMENTI - INDUSTRIE DI PROD. E TRASF.  ','PRODUZIONE DELLE BIBITE ANALCOLICHE E DELLE ACQUE','PRODUZIONE DELLE BIBITE ANALCOLICHE','PRODUZIONE DELLE BIBITE ANALCOLICHE');
-- 10.12.00(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.12.00', 712, 722, null, null, 2,'ALIMENTI DI ORIGINE ANIMALE - IMPRESE REGISTRATE PER PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO ','MACELLAZIONE DI AVICUNICOLI (POLLAME, LAGOMORFI E PICCOLA SELVAGGINA ALLEVATA) PRESSO AZIENDE AGRICOLE ',null,null);
-- 10.13.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.13.00', 712, 726, 729, 729, 4,'ALIMENTI DI ORIGINE ANIMALE - IMPRESE REGISTRATE PER PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO ','LABORATORI DI PRODOTTI A BASE NON RICONOSCIUTI DI ALIMENTI DI O.A. FUNZIONALMENTE ANNESSI AD ESERCIZI DI VENDITA','LAVORAZIONE E TRASFORMAZIONE DI CARNE, PRODOTTI A BASE DI CARNE E PREPARAZIONI DI CARNE IN IMPIANTI NON RICONOSCIUTI FUNZIONALMENTE ANNESSI A ESERCIZIO DI VENDITA, CONTIGUI O MENO AD ESSI ','LAVORAZIONE E TRASFORMAZIONE DI CARNE, PRODOTTI A BASE DI CARNE E PREPARAZIONI DI CARNE IN IMPIANTI NON RICONOSCIUTI FUNZIONALMENTE ANNESSI A ESERCIZIO DI VENDITA, CONTIGUI O MENO AD ESSI ');
-- 10.20.00
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.20.00', 712, 726, 731, 731, 4,'ALIMENTI DI ORIGINE ANIMALE - IMPRESE REGISTRATE PER PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO ','LABORATORI DI PRODOTTI A BASE NON RICONOSCIUTI DI ALIMENTI DI O.A. FUNZIONALMENTE ANNESSI AD ESERCIZI DI VENDITA','LAVORAZIONE E TRASFORMAZIONE DI PRODOTTI DELLA PESCA IN IMPIANTI NON RICONOSCIUTI FUNZIONALMENTE ANNESSI A ESERCIZIO DI  VENDITA, CONTIGUI O MENO AD ESSI ','LAVORAZIONE E TRASFORMAZIONE DI PRODOTTI DELLA PESCA IN IMPIANTI NON RICONOSCIUTI FUNZIONALMENTE ANNESSI A ESERCIZIO DI  VENDITA, CONTIGUI O MENO AD ESSI ');
-- 10.51.20
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('10.51.20', 712, 733, 736, 736, 4,'ALIMENTI DI ORIGINE ANIMALE - IMPRESE REGISTRATE PER PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO ','PRODUZIONE DI PRODOTTI A BASE DI LATTE (IN IMPIANTI NON RICONOSCIUTI)','PRODUZIONE DI PRODOTTI A BASE DI LATTE (IN IMPIANTI NON RICONOSCIUTI)','PRODUZIONE DI PRODOTTI A BASE DI LATTE (IN IMPIANTI NON RICONOSCIUTI)');
-- 56.21.00(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('56.21.00', 724, 734, 737, null, 3,'RISTORAZIONE','RISTORAZIONE COLLETTIVA (COMUNITà ED EVENTI)','PRODUZIONE PASTI PRONTI PER RISTORAZIONE COLLETTIVA (CATERING CONTINUATIVO E PER EVENTI)',null);
-- 56.29.20(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('56.29.20', 724, 734, 737, null, 3,'RISTORAZIONE','RISTORAZIONE COLLETTIVA (COMUNITà ED EVENTI)','PRODUZIONE PASTI PRONTI PER RISTORAZIONE COLLETTIVA (CATERING CONTINUATIVO E PER EVENTI)',null);
-- 56.29.10
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('56.29.10', 724, 734, 738, 738, 4,'RISTORAZIONE','RISTORAZIONE COLLETTIVA (COMUNITà ED EVENTI)','SOMMINISTRAZIONE PASTI IN RISTORAZIONE COLLETTIVA (MENSE,  TERMINALI DI DISTRIBUZIONE E SEDI PER EVENTI E BANQUETING)','SOMMINISTRAZIONE PASTI IN RISTORAZIONE COLLETTIVA (MENSE,  TERMINALI DI DISTRIBUZIONE E SEDI PER EVENTI E BANQUETING)');
-- 56.10.11(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('56.10.11', 724, 735, 739, null, 3,'RISTORAZIONE','RISTORAZIONE PUBBLICA','RISTORAZIONE  CON SOMMIMISTRAZIONE DIRETTA ANCHE CONNESSA CON AZIENDE AGRICOLE ',null);
-- 56.10.12(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('56.10.12', 724, 735, 739, null, 3,'RISTORAZIONE','RISTORAZIONE PUBBLICA','RISTORAZIONE  CON SOMMIMISTRAZIONE DIRETTA ANCHE CONNESSA CON AZIENDE AGRICOLE ',null);
-- 56.30.00(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('56.30.00', 724, 735, 741, null, 3,'RISTORAZIONE','RISTORAZIONE PUBBLICA','BAR E ALTRI ESERCIZI SIMILI',null);
-- 93.21.00(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('93.21.00', 724, 735, 744, null, 3,'RISTORAZIONE','RISTORAZIONE PUBBLICA','ESERCIZI IN CUI LA SOMMINISTRAZIONE DI ALIMENTI E DI BEVANDE VIENE EFFETTUATA CONGIUNTAMENTE AD ATTIVITà DI TRATTENIMENTO E SVAGO, IN SALE DA BALLO, SALE DA GIOCO, LOCALI NOTTURNI, STABILIMENTI BALNEARI ED ESERCIZI SIMILARI
',null);
-- 93.29.10(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('93.29.10', 724, 735, 744, null, 3,'RISTORAZIONE','RISTORAZIONE PUBBLICA','ESERCIZI IN CUI LA SOMMINISTRAZIONE DI ALIMENTI E DI BEVANDE VIENE EFFETTUATA CONGIUNTAMENTE AD ATTIVITà DI TRATTENIMENTO E SVAGO, IN SALE DA BALLO, SALE DA GIOCO, LOCALI NOTTURNI, STABILIMENTI BALNEARI ED ESERCIZI SIMILARI
',null);
-- 93.29.20(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('93.29.20', 724, 735, 744, null, 3,'RISTORAZIONE','RISTORAZIONE PUBBLICA','ESERCIZI IN CUI LA SOMMINISTRAZIONE DI ALIMENTI E DI BEVANDE VIENE EFFETTUATA CONGIUNTAMENTE AD ATTIVITà DI TRATTENIMENTO E SVAGO, IN SALE DA BALLO, SALE DA GIOCO, LOCALI NOTTURNI, STABILIMENTI BALNEARI ED ESERCIZI SIMILARI
',null);
-- 55.20.40(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('55.20.40', 724, 735, 744, null, 3,'RISTORAZIONE','RISTORAZIONE PUBBLICA','ESERCIZI IN CUI LA SOMMINISTRAZIONE DI ALIMENTI E DI BEVANDE VIENE EFFETTUATA CONGIUNTAMENTE AD ATTIVITà DI TRATTENIMENTO E SVAGO, IN SALE DA BALLO, SALE DA GIOCO, LOCALI NOTTURNI, STABILIMENTI BALNEARI ED ESERCIZI SIMILARI
',null);
-- 93.12.00(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('93.12.00', 724, 735, 739, null, 3,'RISTORAZIONE','RISTORAZIONE PUBBLICA','RISTORAZIONE  CON SOMMIMISTRAZIONE DIRETTA ANCHE CONNESSA CON AZIENDE AGRICOLE ',null);
-- 55.10.00(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('55.10.00', 724, 735, 748, null, 3,'RISTORAZIONE','RISTORAZIONE PUBBLICA','ALBERGHI CON PREPARAZIONE E SOMMINISTRAZIONE DI PASTI',null);
-- 55.20.10(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('55.20.10', 724, 735, 748, null, 3,'RISTORAZIONE','RISTORAZIONE PUBBLICA','ALBERGHI CON PREPARAZIONE E SOMMINISTRAZIONE DI PASTI',null);
-- 55.20.51
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('55.20.51', 724, 735, 754, 754, 4,'RISTORAZIONE','RISTORAZIONE PUBBLICA','ESERCIZI DI AFFITTACAMERE - BED AND BREAKFAST','ESERCIZI DI AFFITTACAMERE - BED AND BREAKFAST');
-- 55.90.20
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('55.90.20', 724, 735, 755, 755, 4,'RISTORAZIONE','RISTORAZIONE PUBBLICA','OSTELLI PER LA GIOVENTù','OSTELLI PER LA GIOVENTù');
-- 55.20.52
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('55.20.52', 724, 735, 756, 756, 4,'RISTORAZIONE','RISTORAZIONE PUBBLICA','ATTIVITà RICETTIVE IN RESIDENZE RURALI','ATTIVITà RICETTIVE IN RESIDENZE RURALI');
-- 55.20.30
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('55.20.30', 724, 735, 757, 757, 4,'RISTORAZIONE','RISTORAZIONE PUBBLICA','RIFUGI DI MONTAGNA','RIFUGI DI MONTAGNA');
-- 46.21.10(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.21.10', 749, 762, 765, null, 3,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY','CASH & CARRY',null);
-- 46.31.10(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.31.10', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 46.31.20(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.31.20', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 46.32.10(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.32.10', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 46.32.20(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.32.20', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 46.33.10(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.33.10', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 46.33.20(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.33.20', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 46.34.10(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.34.10', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 46.34.20(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.34.20', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 46.36.00(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.36.00', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 46.37.01(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.37.01', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 46.37.02(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.37.02', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 46.38.10(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.38.10', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 46.38.20(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.38.20', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 46.38.30(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.38.30', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 46.38.90(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.38.90', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 46.39.10(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('46.39.10', 749, 762, null, null, 2,'COMMERCIO ','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY',null,null);
-- 47.11.40(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.11.40', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 47.11.50(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.11.50', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 47.21.01(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.21.01', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 47.21.02(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.21.02', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 47.22.00(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.22.00', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 47.23.00(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.23.00', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 47.24.10(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.24.10', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 47.24.20(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.24.20', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 47.25.00(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.25.00', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 47.26.00(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.26.00', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 47.29.10(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.29.10', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 47.29.20(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.29.20', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 47.29.30(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.29.30', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 47.29.90(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.29.90', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 47.75.20(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.75.20', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 52.29.10(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('52.29.10', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 52.29.21(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('52.29.21', 749, 764, 769, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN  ESERCIZI DI VICINATO DEL SETTORE ALIMENTARE (EV) ',null);
-- 47.11.20(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.11.20', 749, 764, 772, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN ATTIVITà COMMERCIALI AVENTI LE CARATTERISTICHE DI MEDIA STRUTTURA DI VENDITA (MA/M)',null);
-- 47.11.30(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.11.30', 749, 764, 772, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN ATTIVITà COMMERCIALI AVENTI LE CARATTERISTICHE DI MEDIA STRUTTURA DI VENDITA (MA/M)',null);
-- 47.11.10(L)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.11.10', 749, 764, 774, null, 3,'COMMERCIO ','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE IN ATTIVITà COMMERCIALI AVENTI LE CARATTERISTICHE DI GRANDE STRUTTURA DI VENDITA (G1A/M)',null);
-- 47.81.01(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.81.01', 749, 782, null, null, 2,'COMMERCIO ','COMMERCIO AMBULANTE ',null,null);
-- 47.81.02(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.81.02', 749, 782, null, null, 2,'COMMERCIO ','COMMERCIO AMBULANTE ',null,null);
-- 47.81.03(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.81.03', 749, 782, null, null, 2,'COMMERCIO ','COMMERCIO AMBULANTE ',null,null);
-- 47.81.09(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.81.09', 749, 782, null, null, 2,'COMMERCIO ','COMMERCIO AMBULANTE ',null,null);
-- 47.89.09(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.89.09', 749, 782, null, null, 2,'COMMERCIO ','COMMERCIO AMBULANTE ',null,null);
-- 56.10.42(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('56.10.42', 749, 782, null, null, 2,'COMMERCIO ','COMMERCIO AMBULANTE ',null,null);
-- 47.99.20(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('47.99.20', 749, 788, null, null, 2,'COMMERCIO ','DISTRIBUTORI AUTOMATICI',null,null);
-- 52.10.20
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('52.10.20', 786, 795, 803, 806, 4,'DEPOSITO ALIMENTI E BEVANDE CONTO TERZI ','DEPOSITO/PIATTAFORMA ALIMENTARE NON SOGGETTO A RICONOSCIMENTO','DEPOSITO CONTO TERZI DI ALIMENTI NON SOGGETTO A RICONOSCIMENTO','DEPOSITO CONTO TERZI DI ALIMENTI IN REGIME DI TEMPERATURA ');
-- 52.10.10
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('52.10.10', 786, 795, 803, 807, 4,'DEPOSITO ALIMENTI E BEVANDE CONTO TERZI ','DEPOSITO/PIATTAFORMA ALIMENTARE NON SOGGETTO A RICONOSCIMENTO','DEPOSITO CONTO TERZI DI ALIMENTI NON SOGGETTO A RICONOSCIMENTO','DEPOSITO CONTO TERZI DI ALIMENTI NON IN REGIME DI TEMPERATURA ');
-- 52.21.40
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('52.21.40', 786, 795, 805, 805, 4,'DEPOSITO ALIMENTI E BEVANDE CONTO TERZI ','DEPOSITO/PIATTAFORMA ALIMENTARE NON SOGGETTO A RICONOSCIMENTO','PIATTAFORMA DI DISTRIBUZIONE ALIMENTI','PIATTAFORMA DI DISTRIBUZIONE ALIMENTI');
-- 52.29.22
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('52.29.22', 786, 795, 805, 805, 4,'DEPOSITO ALIMENTI E BEVANDE CONTO TERZI ','DEPOSITO/PIATTAFORMA ALIMENTARE NON SOGGETTO A RICONOSCIMENTO','PIATTAFORMA DI DISTRIBUZIONE ALIMENTI','PIATTAFORMA DI DISTRIBUZIONE ALIMENTI');
-- 49.20.00(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('49.20.00', 797, 804, null, null, 2,'TRASPORTO CONTO TERZI','TRASPORTO DI ALIMENTI E  BEVANDE',null,null);
-- 49.41.00(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('49.41.00', 797, 804, null, null, 2,'TRASPORTO CONTO TERZI','TRASPORTO DI ALIMENTI E  BEVANDE',null,null);
-- 50.40.00(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('50.40.00', 797, 804, null, null, 2,'TRASPORTO CONTO TERZI','TRASPORTO DI ALIMENTI E  BEVANDE',null,null);
-- 51.21.00(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('51.21.00', 797, 804, null, null, 2,'TRASPORTO CONTO TERZI','TRASPORTO DI ALIMENTI E  BEVANDE',null,null);
-- 52.24.30(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('52.24.30', 797, 804, null, null, 2,'TRASPORTO CONTO TERZI','TRASPORTO DI ALIMENTI E  BEVANDE',null,null);
-- 52.24.40(A)
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  VALUES ('52.24.40', 797, 804, null, null, 2,'TRASPORTO CONTO TERZI','TRASPORTO DI ALIMENTI E  BEVANDE',null,null);
-- 01.64.09
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  
VALUES ('01.64.09', 655, 683, 696,696, 3,'VEGETALI - PRODUZIONE, TRASFORMAZIONE E CONFEZIONAMENTO DI VEGETALI','LAVORAZIONE DI CEREALI, SEMI, LEGUMI E TUBERI','ALTRE LAVORAZIONI DI SEMI, GRANAGLIE E LEGUMI','');
-- 01.13.10
INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  
VALUES ('01.13.10', 22, 30, 36, 36, 4,'PRODUZIONE PRIMARIA REGISTRABILI','COLTIVAZIONI NON PERMANENTI AD USO ALIMENTARE UMANO ','ALTRE COLTIVAZIONI ESCLUSI FUNGHI','ALTRE COLTIVAZIONI ESCLUSI FUNGHI');

alter table mapping_codice_ateco_master_list_2015_2016  add column descrizione_ateco character varying(500);


update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Villaggi turistici' where codice_ateco='55.20.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Colonie marine e montane' where codice_ateco='55.20.40';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di bevande non alcoliche' where codice_ateco='46.34.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di vini da tavola e v.p.q.r.d.' where codice_ateco='11.02.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di vino spumante e altri vini speciali' where codice_ateco='11.02.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di ortaggi (inclusi i meloni) in foglia, a fusto, a frutto, in radici, bulbi e tuberi in colture protette (escluse barbabietola da zucchero e patate)' where codice_ateco='01.13.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di sidro e di altri vini a base di frutta' where codice_ateco='11.03.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di caffè torrefatto' where codice_ateco='47.29.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di patate' where codice_ateco='01.13.40';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di prodotti di salumeria' where codice_ateco='46.32.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di sementi e alimenti per il bestiame (mangimi), piante officinali, semi oleosi, patate da semina' where codice_ateco='46.21.22';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Acquacoltura in acque dolci e servizi connessi' where codice_ateco='03.22.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di carne non di volatili e di prodotti della macellazione (attivita'' dei mattatoi)' where codice_ateco='10.11.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di carni e di prodotti a base di carne' where codice_ateco='47.22.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di pasti e piatti pronti di altri prodotti alimentari' where codice_ateco='10.85.09';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di piante da foraggio e di altre colture non permanenti' where codice_ateco='01.19.90';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di generi di monopolio' where codice_ateco='47.26.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di birra' where codice_ateco='11.05.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di margarina e di grassi commestibili simili' where codice_ateco='10.42.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di prodotti a base di carne (inclusa la carne di volatili)' where codice_ateco='10.13.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di olio di oliva da olive prevalentemente non di produzione propria' where codice_ateco='10.41.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di prodotti vari, mediante l''intervento di un dimostratore o di un incaricato alla vendita (porta a porta)' where codice_ateco='47.99.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Trasporto ferroviario di merci' where codice_ateco='49.20.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di frutta e verdura fresca' where codice_ateco='47.21.01';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Discoteche, sale da ballo night-club e simili' where codice_ateco='93.29.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio ambulante di altri prodotti nca' where codice_ateco='47.89.09';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di succhi di frutta e di ortaggi' where codice_ateco='10.32.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio effettuato per mezzo di distributori automatici' where codice_ateco='47.99.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di pesci, crostacei e molluschi' where codice_ateco='47.23.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Allevamento di bovini e bufalini da carne' where codice_ateco='01.42.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di pasti e piatti pronti' where codice_ateco='46.38.30';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di piante per la produzione di bevande' where codice_ateco='01.27.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Acquacoltura in acqua di mare, salmastra o lagunare e servizi connessi' where codice_ateco='03.21.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Mense' where codice_ateco='56.29.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Gelaterie e pasticcerie' where codice_ateco='56.10.30';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di pomacee e frutta a nocciolo' where codice_ateco='01.24.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Allevamento di bovini e bufale da latte, produzione di latte crudo' where codice_ateco='01.41.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Lavorazione e conservazione delle patate' where codice_ateco='10.31.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Allevamento di altri animali nca' where codice_ateco='01.49.90';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Catering per eventi, banqueting' where codice_ateco='56.21.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Supermercati' where codice_ateco='47.11.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di altre bevande fermentate non distillate' where codice_ateco='11.04.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di prodotti macrobiotici e dietetici' where codice_ateco='47.29.30';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di frutta e ortaggi conservati' where codice_ateco='46.31.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di pizza confezionata' where codice_ateco='10.85.04';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Intermediari dei trasporti' where codice_ateco='52.29.21';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Trattamento igienico del latte' where codice_ateco='10.51.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Caccia, cattura di animali e servizi connessi' where codice_ateco='01.70.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione dei derivati del latte' where codice_ateco='10.51.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di carne fresca, congelata e surgelata' where codice_ateco='46.32.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Rifugi di montagna' where codice_ateco='55.20.30';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di riso' where codice_ateco='01.12.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di estratti e succhi di carne' where codice_ateco='10.89.01';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di gelati senza vendita diretta al pubblico' where codice_ateco='10.52.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso non specializzato di prodotti alimentari, bevande e tabacco' where codice_ateco='46.39.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di prodotti della pesca congelati, surgelati, conservati, secchi' where codice_ateco='46.38.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Altre lavorazioni di semi e granaglie' where codice_ateco='10.61.40';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Alberghi' where codice_ateco='55.10.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di altri prodotti alimentari in esercizi specializzati nca' where codice_ateco='47.29.90';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di fette biscottate, biscotti, prodotti di pasticceria conservati' where codice_ateco='10.72.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Movimento merci relativo ad altri trasporti terrestri' where codice_ateco='52.24.40';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di condimenti e spezie' where codice_ateco='10.84.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di zucchero, cioccolato, dolciumi e prodotti da forno' where codice_ateco='46.36.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di piatti pronti a base di carne e pollame' where codice_ateco='10.85.01';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Ipermercati' where codice_ateco='47.11.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di caffÃ¨' where codice_ateco='46.37.01';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Erboristerie' where codice_ateco='47.75.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di agrumi' where codice_ateco='01.23.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Raccolta di prodotti selvatici non legnosi' where codice_ateco='02.30.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di pasticceria fresca' where codice_ateco='10.71.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Raccolta, trattamento e fornitura di acqua' where codice_ateco='36.00.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di altri alberi da frutta, frutti di bosco e frutta in guscio' where codice_ateco='01.25.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Discount di alimentari' where codice_ateco='47.11.30';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Allevamento di conigli' where codice_ateco='01.49.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di uva' where codice_ateco='01.21.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di carne di volatili e prodotti della loro macellazione (attivita'' dei mattatoi)' where codice_ateco='10.12.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio ambulante di prodotti ortofrutticoli' where codice_ateco='47.81.01';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di paste alimentari, di cuscus e di prodotti farinacei simili' where codice_ateco='10.73.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di pane' where codice_ateco='47.24.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di prodotti surgelati' where codice_ateco='47.11.50';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di qualsiasi tipo di prodotto per corrispondenza, radio, telefono' where codice_ateco='47.91.30';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Ristorazione ambulante' where codice_ateco='56.10.42';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Magazzini frigoriferi per conto terzi' where codice_ateco='52.10.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Pesca in acque marine e lagunari e servizi connessi' where codice_ateco='03.11.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di mangimi per l''alimentazione degli animali da allevamento' where codice_ateco='10.91.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Ristorazione su treni e navi' where codice_ateco='56.10.50';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di piatti pronti a base di pasta' where codice_ateco='10.85.05';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Parafarmacie' where codice_ateco='47.73.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di qualsiasi tipo di prodotto effettuato via internet' where codice_ateco='47.91.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Farmacie' where codice_ateco='47.73.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Magazzini di custodia e deposito per conto terzi' where codice_ateco='52.10.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazioni agricole associate all''allevamento di animali: attivita'' mista' where codice_ateco='01.50.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio ambulante di carne' where codice_ateco='47.81.03';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Apicoltura' where codice_ateco='01.49.30';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di qualsiasi tipo di prodotto effettuato per televisione' where codice_ateco='47.91.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Gelaterie e pasticcerie ambulanti' where codice_ateco='56.10.41';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Gestione di centri di movimentazione merci (interporti)' where codice_ateco='52.21.40';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Trasporto di merci per vie d''acqua interne' where codice_ateco='50.40.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Attivita'' di club sportivi' where codice_ateco='93.12.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Movimento merci relativo a trasporti ferroviari ' where codice_ateco='52.24.30';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso non specializzato di prodotti surgelati' where codice_ateco='46.39.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di altri prodotti alimentari' where codice_ateco='46.38.90';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Molitura del frumento' where codice_ateco='10.61.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Trasporto di merci su strada' where codice_ateco='49.41.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Industria delle bibite analcoliche, delle acque minerali e di altre acque in bottiglia' where codice_ateco='11.07.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di barbabietola da zucchero' where codice_ateco='01.13.30';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Ristorazione con somministrazione' where codice_ateco='56.10.11';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Attivita'' di alloggio connesse alle aziende agricole' where codice_ateco='55.20.52';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Lavorazione e conservazione di frutta e di ortaggi (esclusi i succhi di frutta e di ortaggi)' where codice_ateco='10.39.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di preparati omogeneizzati e di alimenti dietetici' where codice_ateco='10.86.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Gestione di stabilimenti balneari: marittimi, lacuali e fluviali' where codice_ateco='93.29.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Imballaggio e confezionamento di generi alimentari' where codice_ateco='82.92.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Attivita'' di ristorazione connesse alle aziende agricole' where codice_ateco='56.10.12';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Trasporto marittimo e costiero di merci' where codice_ateco='50.20.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di bevande alcoliche' where codice_ateco='46.34.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di frutta di origine tropicale e subtropicale' where codice_ateco='01.22.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Lavorazione del tè e di altri preparati per infusi' where codice_ateco='10.83.02';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Affittacamere per brevi soggiorni, case ed appartamenti per vacanze, bed and breakfast, residence' where codice_ateco='55.20.51';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di prodotti della pesca freschi' where codice_ateco='46.38.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Minimercati ed altri esercizi non specializzati di alimentari vari' where codice_ateco='47.11.40';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di piatti pronti a base di pesce, inclusi fish and chips' where codice_ateco='10.85.02';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di cacao in polvere, cioccolato, caramelle e confetterie' where codice_ateco='10.82.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Allevamento di cavalli e altri equini' where codice_ateco='01.43.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Ristorazione senza somministrazione con preparazione di cibi da asporto' where codice_ateco='56.10.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Pulitura e cernita di semi e granaglie' where codice_ateco='01.64.01';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di piatti pronti a base di ortaggi' where codice_ateco='10.85.03';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Alloggi per studenti e lavoratori con servizi accessori di tipo alberghiero' where codice_ateco='55.90.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di cereali (escluso il riso)' where codice_ateco='01.11.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Spedizionieri e agenzie di operazioni doganali' where codice_ateco='52.29.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Allevamento di ovini e caprini' where codice_ateco='01.45.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di prodotti lattiero-caseari e di uova' where codice_ateco='46.33.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di prodotti per l''alimentazione degli animali da compagnia' where codice_ateco='10.92.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di tè, cacao e spezie' where codice_ateco='46.37.02';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di frutti oleosi' where codice_ateco='01.26.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di semi oleosi' where codice_ateco='01.11.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Bar e altri esercizi simili senza cucina' where codice_ateco='56.30.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazioni miste di cereali, legumi da granella e semi oleosi' where codice_ateco='01.11.40';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Parchi di divertimento e parchi tematici' where codice_ateco='93.21.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Trasporto aereo di merci' where codice_ateco='51.21.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di animali vivi' where codice_ateco='46.23.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Non Definito' where codice_ateco='00.00.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Lavorazione del riso' where codice_ateco='10.61.30';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di bevande' where codice_ateco='47.25.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di malto' where codice_ateco='11.06.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di zucchero' where codice_ateco='10.81.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di altri prodotti alimentari nca' where codice_ateco='10.89.09';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di olio raffinato o grezzo da semi oleosi o frutti oleosi prevalentemente non di produzione propria' where codice_ateco='10.41.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di frutta e ortaggi freschi' where codice_ateco='46.31.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di oli e grassi alimentari di origine vegetale o animale' where codice_ateco='46.33.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio ambulante di prodotti ittici' where codice_ateco='47.81.02';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Allevamento di suini' where codice_ateco='01.46.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Pesca in acque dolci e servizi connessi' where codice_ateco='03.12.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Distillazione, rettifica e miscelatura degli alcolici' where codice_ateco='11.01.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di latte e di prodotti lattiero-caseari' where codice_ateco='47.29.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di spezie, piante farmaceutiche' where codice_ateco='01.28.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di oli e grassi animali grezzi o raffinati' where codice_ateco='10.41.30';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio ambulante di altri prodotti alimentari e bevande nca' where codice_ateco='47.81.09';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di legumi da granella' where codice_ateco='01.11.30';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Mediatori in materie prime agricole, materie prime e semilavorati tessili; pelli grezze' where codice_ateco='46.11.06';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Molitura di altri cereali' where codice_ateco='10.61.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Attivita'' che seguono la raccolta' where codice_ateco='01.63.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Servizi logistici relativi alla distribuzione delle merci' where codice_ateco='52.29.22';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Lavorazione del caffÃ¨' where codice_ateco='10.83.01';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di frutta e verdura preparata e conservata' where codice_ateco='47.21.02';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Catering continuativo su base contrattuale' where codice_ateco='56.29.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio all''ingrosso di cereali e legumi secchi' where codice_ateco='46.21.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di canna da zucchero' where codice_ateco='01.14.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di prodotti di panetteria freschi' where codice_ateco='10.71.10';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Allevamento di pollame' where codice_ateco='01.47.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Commercio al dettaglio di torte, dolciumi, confetteria' where codice_ateco='47.24.20';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Produzione di amidi e di prodotti amidacei (inclusa produzione di olio di mais)' where codice_ateco='10.62.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Altre lavorazioni delle sementi per la semina' where codice_ateco='01.64.09';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Lavorazione e conservazione di pesce, crostacei e molluschi mediante surgelamento, salatura eccetera' where codice_ateco='10.20.00';
update mapping_codice_ateco_master_list_2015_2016 set descrizione_ateco='Coltivazione di ortaggi (inclusi i meloni) in foglia, a fusto, a frutto, in radici, bulbi e tuberi in piena aria (escluse barbabietola da zucchero e patate)' where codice_ateco='01.13.10';


INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  
VALUES ('46.39.20', 749, 762,765,765, 3,'COMMERCIO','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY','COMMERCIO ALL''INGROSSO DI ALIMENTI E BEVANDE, CASH AND CARRY','Commercio all''ingrosso non specializzato di prodotti alimentari, bevande e tabacco');
-- 01.13.10

INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  
VALUES ('47.91.30',749, 764,777, 777, 3,'COMMERCIO','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE PER CORRISPONDENZA/INTERNET O TRAMITE TELEVISIONE O ALTRI SISTEMI DI
COMUNICAZIONE','Commercio al dettaglio di qualsiasi tipo di prodotto per corrispondenza, radio, telefono');

INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  
VALUES ('47.99.10',749, 764,784,784, 3,'COMMERCIO','COMMERCIO AL DETTAGLIO','VENDITA AL DETTAGLIO O RACCOLTA DI ORDINATIVI DI ACQUISTO PRESSO IL DOMICILIO DEI CONSUMATORI','Commercio al dettaglio di prodotti vari, mediante l''intervento di un dimostratore o di un incaricato alla vendita (porta a porta)');


INSERT INTO mapping_codice_ateco_master_list_2015_2016(codice_ateco, id_macroarea, id_aggregazione, id_attivita, id_nuova_linea, livello,macroarea , aggregazione, attivita, descrizione )  
VALUES ('47.91.10',749, 764,777, 777, 3,'COMMERCIO','COMMERCIO AL DETTAGLIO','COMMERCIO AL DETTAGLIO DI ALIMENTI E BEVANDE PER CORRISPONDENZA/INTERNET O TRAMITE TELEVISIONE O ALTRI SISTEMI DI
COMUNICAZIONE','Commercio al dettaglio di qualsiasi tipo di prodotto per corrispondenza, radio, telefono');
