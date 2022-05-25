--CASERTA 203 id 5 è caserta
INSERT INTO dpat(anno, id_asl, enabled) VALUES (2014, 203, true);
INSERT INTO dpat(anno, id_asl, enabled) VALUES (2014, 204, true);
INSERT INTO dpat(anno, id_asl, enabled) VALUES (2014, 205, true);
INSERT INTO dpat(anno, id_asl, enabled) VALUES (2014, 206, true);
INSERT INTO dpat(anno, id_asl, enabled) VALUES (2014, 207, true);
--verifica chiave
select * from dpat
--
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ. A DEL DPAR', 5, TRUE);
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ. A DEL DPAR', 6, TRUE);
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ. A DEL DPAR', 7, TRUE);
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ. A DEL DPAR', 8, TRUE);
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ. A DEL DPAR', 9, TRUE);
select * from dpat_sezione
--
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 1 (TBC) PIANO 2 (BRC) PIANO 4 (LEB)', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 3', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 5', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 6', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 7', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 8', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 9', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 10', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 11', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 12', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 13', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 14', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 15', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 16', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 17', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 18', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 19', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 20', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'PIANO 21', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'ATTIVITA'' 1', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'ATTIVITA'' 2', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'ATTIVITA'' 3', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (5, 'ATTIVITA'' 39', TRUE);

INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 1 (TBC) PIANO 2 (BRC) PIANO 4 (LEB)', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 3', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 5', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 6', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 7', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 8', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 9', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 10', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 11', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 12', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 13', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 14', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 15', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 16', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 17', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 18', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 19', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 20', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'PIANO 21', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'ATTIVITA'' 1', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'ATTIVITA'' 2', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'ATTIVITA'' 3', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (6, 'ATTIVITA'' 39', TRUE);
--
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 1 (TBC) PIANO 2 (BRC) PIANO 4 (LEB)', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 3', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 5', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 6', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 7', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 8', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 9', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 10', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 11', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 12', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 13', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 14', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 15', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 16', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 17', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 18', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 19', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 20', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'PIANO 21', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'ATTIVITA'' 1', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'ATTIVITA'' 2', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'ATTIVITA'' 3', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (7, 'ATTIVITA'' 39', TRUE);
--
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 1 (TBC) PIANO 2 (BRC) PIANO 4 (LEB)', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 3', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 5', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 6', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 7', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 8', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 9', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 10', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 11', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 12', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 13', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 14', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 15', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 16', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 17', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 18', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 19', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 20', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'PIANO 21', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'ATTIVITA'' 1', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'ATTIVITA'' 2', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'ATTIVITA'' 3', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (8, 'ATTIVITA'' 39', TRUE);
--
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 1 (TBC) PIANO 2 (BRC) PIANO 4 (LEB)', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 3', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 5', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 6', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 7', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 8', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 9', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 10', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 11', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 12', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 13', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 14', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 15', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 16', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 17', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 18', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 19', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 20', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'PIANO 21', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'ATTIVITA'' 1', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'ATTIVITA'' 2', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'ATTIVITA'' 3', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (9, 'ATTIVITA'' 39', TRUE);
select * from dpat_piano

select * from dpat_attivita
--52-74 blocco I
--75-97 blocco II
--98-120 blocco III
--121-143 blocco IV
--144-166 blocco V

INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (52, 0, 'Piani di monitoraggio finalizzati all’eradicazione della TBC, BRC e LEB nei bovini e bufalini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (53, 0, 'Piano di monitoraggio finalizzato all’eradicazione della BRC negli ovicaprini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (54, 0, 'Piano di monitoraggio per la ricerca della salmonella nei riproduttori', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (55, 0, 'Piano di monitoraggio per la ricerca della salmonella nelle ovaiole', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (56, 0, 'Piano di monitoraggio per la ricerca della salmonella nei polli da carne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (57, 0, 'Piano di monitoraggio per la ricerca della salmonella nei tacchini da riproduzione e ingrasso', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (58, 0, 'Piano di monitoraggio finalizzato all’eradicazione della BSE – Reg. CE 999/2001', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (59, 0, 'Piano di monitoraggio finalizzato all’eradicazione della Scrapie – Dec CE 677/2002', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (60, 0, 'Piano di monitoraggio finalizzato all''eradicazione delle TSE', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (61, 0, 'Piano di monitoraggio della malattia di Aujeszky (1 suino campionato = 1 UBA)', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (62, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione dei suini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (63, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione degli ovicaprini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (64, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione dei bovini e bufalini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (65, 0, 'Piano  di monitoraggio Nazionale Residui', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (66, 0, 'Piano Nazionale di Monitoraggio OGM', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (67, 0, 'Piano di Monitoraggio sui residui di fitosanitari negli alimenti di origine vegetale ed animale (DM 23/12/1992)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (68, 0, 'Piano Nazionale di Monitoraggio Alimentazione Animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (69, 0, 'Piano Nazionale di Monitoraggio Benessere Animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (70, 0, 'Piano di Monitoraggio Farmacosorveglianza', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (71, 0, 'Audit negli stabilimenti riconosciuti ex sez. IX Reg CE 853/04  (latte crudo e derivati)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (72, 0, 'Ispezioni effettuate per sistemi d''allarme rapido', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (73, 0, 'Iscrizione cani in BDR e movimentazione anagrafe canina', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (74, 0, 'Supervisioni', TRUE, TRUE);
--
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (75, 0, 'Piani di monitoraggio finalizzati all’eradicazione della TBC, BRC e LEB nei bovini e bufalini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (76, 0, 'Piano di monitoraggio finalizzato all’eradicazione della BRC negli ovicaprini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (77, 0, 'Piano di monitoraggio per la ricerca della salmonella nei riproduttori', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (78, 0, 'Piano di monitoraggio per la ricerca della salmonella nelle ovaiole', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (79, 0, 'Piano di monitoraggio per la ricerca della salmonella nei polli da carne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (80, 0, 'Piano di monitoraggio per la ricerca della salmonella nei tacchini da riproduzione e ingrasso', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (81, 0, 'Piano di monitoraggio finalizzato all’eradicazione della BSE – Reg. CE 999/2001', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (82, 0, 'Piano di monitoraggio finalizzato all’eradicazione della Scrapie – Dec CE 677/2002', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (83, 0, 'Piano di monitoraggio finalizzato all''eradicazione delle TSE', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (84, 0, 'Piano di monitoraggio della malattia di Aujeszky (1 suino campionato = 1 UBA)', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (85, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione dei suini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (86, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione degli ovicaprini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (87, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione dei bovini e bufalini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (88, 0, 'Piano  di monitoraggio Nazionale Residui', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (89, 0, 'Piano Nazionale di Monitoraggio OGM', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (90, 0, 'Piano di Monitoraggio sui residui di fitosanitari negli alimenti di origine vegetale ed animale (DM 23/12/1992)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (91, 0, 'Piano Nazionale di Monitoraggio Alimentazione Animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (92, 0, 'Piano Nazionale di Monitoraggio Benessere Animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (93, 0, 'Piano di Monitoraggio Farmacosorveglianza', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (94, 0, 'Audit negli stabilimenti riconosciuti ex sez. IX Reg CE 853/04  (latte crudo e derivati)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (95, 0, 'Ispezioni effettuate per sistemi d''allarme rapido', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (96, 0, 'Iscrizione cani in BDR e movimentazione anagrafe canina', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (97, 0, 'Supervisioni', TRUE, TRUE);
--
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (98, 0, 'Piani di monitoraggio finalizzati all’eradicazione della TBC, BRC e LEB nei bovini e bufalini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (99, 0, 'Piano di monitoraggio finalizzato all’eradicazione della BRC negli ovicaprini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (100, 0, 'Piano di monitoraggio per la ricerca della salmonella nei riproduttori', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (101, 0, 'Piano di monitoraggio per la ricerca della salmonella nelle ovaiole', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (102, 0, 'Piano di monitoraggio per la ricerca della salmonella nei polli da carne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (103, 0, 'Piano di monitoraggio per la ricerca della salmonella nei tacchini da riproduzione e ingrasso', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (104, 0, 'Piano di monitoraggio finalizzato all’eradicazione della BSE – Reg. CE 999/2001', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (105, 0, 'Piano di monitoraggio finalizzato all’eradicazione della Scrapie – Dec CE 677/2002', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (106, 0, 'Piano di monitoraggio finalizzato all''eradicazione delle TSE', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (107, 0, 'Piano di monitoraggio della malattia di Aujeszky (1 suino campionato = 1 UBA)', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (108, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione dei suini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (109, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione degli ovicaprini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (110, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione dei bovini e bufalini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (111, 0, 'Piano  di monitoraggio Nazionale Residui', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (112, 0, 'Piano Nazionale di Monitoraggio OGM', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (113, 0, 'Piano di Monitoraggio sui residui di fitosanitari negli alimenti di origine vegetale ed animale (DM 23/12/1992)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (114, 0, 'Piano Nazionale di Monitoraggio Alimentazione Animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (115, 0, 'Piano Nazionale di Monitoraggio Benessere Animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (116, 0, 'Piano di Monitoraggio Farmacosorveglianza', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (117, 0, 'Audit negli stabilimenti riconosciuti ex sez. IX Reg CE 853/04  (latte crudo e derivati)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (118, 0, 'Ispezioni effettuate per sistemi d''allarme rapido', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (119, 0, 'Iscrizione cani in BDR e movimentazione anagrafe canina', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (120, 0, 'Supervisioni', TRUE, TRUE);
--

INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (121, 0, 'Piani di monitoraggio finalizzati all’eradicazione della TBC, BRC e LEB nei bovini e bufalini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (122, 0, 'Piano di monitoraggio finalizzato all’eradicazione della BRC negli ovicaprini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (123, 0, 'Piano di monitoraggio per la ricerca della salmonella nei riproduttori', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (124, 0, 'Piano di monitoraggio per la ricerca della salmonella nelle ovaiole', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (125, 0, 'Piano di monitoraggio per la ricerca della salmonella nei polli da carne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (126, 0, 'Piano di monitoraggio per la ricerca della salmonella nei tacchini da riproduzione e ingrasso', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (127, 0, 'Piano di monitoraggio finalizzato all’eradicazione della BSE – Reg. CE 999/2001', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (128, 0, 'Piano di monitoraggio finalizzato all’eradicazione della Scrapie – Dec CE 677/2002', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (129, 0, 'Piano di monitoraggio finalizzato all''eradicazione delle TSE', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (130, 0, 'Piano di monitoraggio della malattia di Aujeszky (1 suino campionato = 1 UBA)', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (131, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione dei suini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (132, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione degli ovicaprini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (133, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione dei bovini e bufalini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (134, 0, 'Piano  di monitoraggio Nazionale Residui', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (135, 0, 'Piano Nazionale di Monitoraggio OGM', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (136, 0, 'Piano di Monitoraggio sui residui di fitosanitari negli alimenti di origine vegetale ed animale (DM 23/12/1992)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (137, 0, 'Piano Nazionale di Monitoraggio Alimentazione Animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (138, 0, 'Piano Nazionale di Monitoraggio Benessere Animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (139, 0, 'Piano di Monitoraggio Farmacosorveglianza', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (140, 0, 'Audit negli stabilimenti riconosciuti ex sez. IX Reg CE 853/04  (latte crudo e derivati)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (141, 0, 'Ispezioni effettuate per sistemi d''allarme rapido', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (142, 0, 'Iscrizione cani in BDR e movimentazione anagrafe canina', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (143, 0, 'Supervisioni', TRUE, TRUE);
--144-166
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (144, 0, 'Piani di monitoraggio finalizzati all’eradicazione della TBC, BRC e LEB nei bovini e bufalini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (145, 0, 'Piano di monitoraggio finalizzato all’eradicazione della BRC negli ovicaprini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (146, 0, 'Piano di monitoraggio per la ricerca della salmonella nei riproduttori', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (147, 0, 'Piano di monitoraggio per la ricerca della salmonella nelle ovaiole', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (148, 0, 'Piano di monitoraggio per la ricerca della salmonella nei polli da carne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (149, 0, 'Piano di monitoraggio per la ricerca della salmonella nei tacchini da riproduzione e ingrasso', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (150, 0, 'Piano di monitoraggio finalizzato all’eradicazione della BSE – Reg. CE 999/2001', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (151, 0, 'Piano di monitoraggio finalizzato all’eradicazione della Scrapie – Dec CE 677/2002', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (152, 0, 'Piano di monitoraggio finalizzato all''eradicazione delle TSE', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (153, 0, 'Piano di monitoraggio della malattia di Aujeszky (1 suino campionato = 1 UBA)', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (154, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione dei suini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (155, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione degli ovicaprini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (156, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione dei bovini e bufalini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (157, 0, 'Piano  di monitoraggio Nazionale Residui', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (158, 0, 'Piano Nazionale di Monitoraggio OGM', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (159, 0, 'Piano di Monitoraggio sui residui di fitosanitari negli alimenti di origine vegetale ed animale (DM 23/12/1992)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (160, 0, 'Piano Nazionale di Monitoraggio Alimentazione Animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (161, 0, 'Piano Nazionale di Monitoraggio Benessere Animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (162, 0, 'Piano di Monitoraggio Farmacosorveglianza', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (163, 0, 'Audit negli stabilimenti riconosciuti ex sez. IX Reg CE 853/04  (latte crudo e derivati)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (164, 0, 'Ispezioni effettuate per sistemi d''allarme rapido', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (165, 0, 'Iscrizione cani in BDR e movimentazione anagrafe canina', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (166, 0, 'Supervisioni', TRUE, TRUE);


INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (61,'Numero ovicaprini da controllare di allevamenti U.I. o I.(n. 40 UBA per U.I. x 1 accesso)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (61,'Numero allevamenti ovicaprini U.I. o I. (0,2 U.I. x 1 accesso x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (61,'Numero ovicaprini da controllare di allevamenti NON U.I. o I.  (n. 40 UBA per U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (61,'Numero allevamenti ovicaprini NON U.I. o I.(0,2 U.I. x 2 accessi x allevamento)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (84,'Numero ovicaprini da controllare di allevamenti U.I. o I.(n. 40 UBA per U.I. x 1 accesso)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (84,'Numero allevamenti ovicaprini U.I. o I. (0,2 U.I. x 1 accesso x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (84,'Numero ovicaprini da controllare di allevamenti NON U.I. o I.  (n. 40 UBA per U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (84,'Numero allevamenti ovicaprini NON U.I. o I.(0,2 U.I. x 2 accessi x allevamento)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (107,'Numero ovicaprini da controllare di allevamenti U.I. o I.(n. 40 UBA per U.I. x 1 accesso)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (107,'Numero allevamenti ovicaprini U.I. o I. (0,2 U.I. x 1 accesso x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (107,'Numero ovicaprini da controllare di allevamenti NON U.I. o I.  (n. 40 UBA per U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (107,'Numero allevamenti ovicaprini NON U.I. o I.(0,2 U.I. x 2 accessi x allevamento)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (130,'Numero ovicaprini da controllare di allevamenti U.I. o I.(n. 40 UBA per U.I. x 1 accesso)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (130,'Numero allevamenti ovicaprini U.I. o I. (0,2 U.I. x 1 accesso x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (130,'Numero ovicaprini da controllare di allevamenti NON U.I. o I.  (n. 40 UBA per U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (130,'Numero allevamenti ovicaprini NON U.I. o I.(0,2 U.I. x 2 accessi x allevamento)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (153,'Numero ovicaprini da controllare di allevamenti U.I. o I.(n. 40 UBA per U.I. x 1 accesso)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (153,'Numero allevamenti ovicaprini U.I. o I. (0,2 U.I. x 1 accesso x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (153,'Numero ovicaprini da controllare di allevamenti NON U.I. o I.  (n. 40 UBA per U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (153,'Numero allevamenti ovicaprini NON U.I. o I.(0,2 U.I. x 2 accessi x allevamento)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (62,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (85,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (108,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (131,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (154,'Numero aziende da controllare', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (63,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (86,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (109,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (132,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (155,'Numero aziende da controllare', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (64,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (87,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (110,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (133,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (156,'Numero aziende da controllare', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (65,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (88,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (111,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (134,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (158,'Numero aziende da controllare', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (66,'Numero animali morti da controllare (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (89,'Numero animali morti da controllare (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (112,'Numero animali morti da controllare (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (135,'Numero animali morti da controllare (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (158,'Numero animali morti da controllare (numero presunto in base ai dati storici)', 0, TRUE);


INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (67,'Numero animali morti da controllare (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (90,'Numero animali morti da controllare (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (113,'Numero animali morti da controllare (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (136,'Numero animali morti da controllare (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (159,'Numero animali morti da controllare (numero presunto in base ai dati storici)', 0, TRUE);


INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (69,'N. animali da controllare  in allevamenti da riproduzione a ciclo chiuso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (69,'N. allevamenti da riproduzione a ciclo chiuso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (69,'N. animali da controllare in allevamenti da riproduzione a ciclo aperto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (69,'n. allevamenti da riproduzione a ciclo aperto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (69,'N. animali da controllare in allevamenti da ingrasso da macello', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (69,'n. allevamenti da ingrasso da macello', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (69,'N. animali da controllare in allevamenti da ingrasso da vita e stalle di sosta.', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (69,'n. allevamenti da ingrasso da vita e stalle di sosta', 0, TRUE);


INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (92,'N. animali da controllare  in allevamenti da riproduzione a ciclo chiuso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (92,'N. allevamenti da riproduzione a ciclo chiuso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (92,'N. animali da controllare in allevamenti da riproduzione a ciclo aperto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (92,'n. allevamenti da riproduzione a ciclo aperto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (92,'N. animali da controllare in allevamenti da ingrasso da macello', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (92,'n. allevamenti da ingrasso da macello', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (92,'N. animali da controllare in allevamenti da ingrasso da vita e stalle di sosta.', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (92,'n. allevamenti da ingrasso da vita e stalle di sosta', 0, TRUE);


INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (115,'N. animali da controllare  in allevamenti da riproduzione a ciclo chiuso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (115,'N. allevamenti da riproduzione a ciclo chiuso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (115,'N. animali da controllare in allevamenti da riproduzione a ciclo aperto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (115,'n. allevamenti da riproduzione a ciclo aperto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (115,'N. animali da controllare in allevamenti da ingrasso da macello', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (115,'n. allevamenti da ingrasso da macello', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (115,'N. animali da controllare in allevamenti da ingrasso da vita e stalle di sosta.', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (115,'n. allevamenti da ingrasso da vita e stalle di sosta', 0, TRUE);


INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (138,'N. animali da controllare  in allevamenti da riproduzione a ciclo chiuso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (138,'N. allevamenti da riproduzione a ciclo chiuso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (138,'N. animali da controllare in allevamenti da riproduzione a ciclo aperto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (138,'n. allevamenti da riproduzione a ciclo aperto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (138,'N. animali da controllare in allevamenti da ingrasso da macello', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (138,'n. allevamenti da ingrasso da macello', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (138,'N. animali da controllare in allevamenti da ingrasso da vita e stalle di sosta.', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (138,'n. allevamenti da ingrasso da vita e stalle di sosta', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (161,'N. animali da controllare  in allevamenti da riproduzione a ciclo chiuso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (161,'N. allevamenti da riproduzione a ciclo chiuso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (161,'N. animali da controllare in allevamenti da riproduzione a ciclo aperto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (161,'n. allevamenti da riproduzione a ciclo aperto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (161,'N. animali da controllare in allevamenti da ingrasso da macello', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (161,'n. allevamenti da ingrasso da macello', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (161,'N. animali da controllare in allevamenti da ingrasso da vita e stalle di sosta.', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (161,'n. allevamenti da ingrasso da vita e stalle di sosta', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (70,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (93,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (116,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (139,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (162,'Numero aziende da controllare', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (71,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (94,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (117,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (140,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (163,'Numero aziende da controllare', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (72,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (95,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (118,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (141,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (164,'Numero aziende da controllare', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (73,'N. campioni in allevamenti', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (73,'N.  campioni al macello (vengono impiegate il 50 % delle U.I. rispetto agli altri sottopiani in quanto si presume che almeno nel 50% dei casi le attività siano effettuate come normale attività al macello)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (73,'N. campioni in stabilimenti', 0, TRUE); 

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (96,'N. campioni in allevamenti', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (96,'N.  campioni al macello (vengono impiegate il 50 % delle U.I. rispetto agli altri sottopiani in quanto si presume che almeno nel 50% dei casi le attività siano effettuate come normale attività al macello)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (96,'N. campioni in stabilimenti', 0, TRUE); 

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (119,'N. campioni in allevamenti', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (119,'N.  campioni al macello (vengono impiegate il 50 % delle U.I. rispetto agli altri sottopiani in quanto si presume che almeno nel 50% dei casi le attività siano effettuate come normale attività al macello)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (119,'N. campioni in stabilimenti', 0, TRUE); 

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (142,'N. campioni in allevamenti', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (142,'N.  campioni al macello (vengono impiegate il 50 % delle U.I. rispetto agli altri sottopiani in quanto si presume che almeno nel 50% dei casi le attività siano effettuate come normale attività al macello)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (142,'N. campioni in stabilimenti', 0, TRUE); 

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (165,'N. campioni in allevamenti', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (165,'N.  campioni al macello (vengono impiegate il 50 % delle U.I. rispetto agli altri sottopiani in quanto si presume che almeno nel 50% dei casi le attività siano effettuate come normale attività al macello)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (165,'N. campioni in stabilimenti', 0, TRUE); 

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (74,'Campioni di soia', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (74,'Campioni di soia, riso o mais di produzione biologica', 0, TRUE); 
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (74,'Campioni di mais', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (74,'Campioni di riso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (74,'Campioni di alimenti per bambini a base di cereali', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (74,'Campioni di altri prodotti alimentari finiti NON di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (74,'Campioni effettuati su disposizione dell''USMAF', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (74,'N. ispezioni senza campionamento su iniziativa dell''ASL', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (74,'N. ispezioni senza campionamento su iniziativa dell''USMAF', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (97,'Campioni di soia', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (97,'Campioni di soia, riso o mais di produzione biologica', 0, TRUE); 
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (97,'Campioni di mais', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (97,'Campioni di riso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (97,'Campioni di alimenti per bambini a base di cereali', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (97,'Campioni di altri prodotti alimentari finiti NON di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (97,'Campioni effettuati su disposizione dell''USMAF', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (97,'N. ispezioni senza campionamento su iniziativa dell''ASL', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (97,'N. ispezioni senza campionamento su iniziativa dell''USMAF', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (120,'Campioni di soia', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (120,'Campioni di soia, riso o mais di produzione biologica', 0, TRUE); 
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (120,'Campioni di mais', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (120,'Campioni di riso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (120,'Campioni di alimenti per bambini a base di cereali', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (120,'Campioni di altri prodotti alimentari finiti NON di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (120,'Campioni effettuati su disposizione dell''USMAF', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (120,'N. ispezioni senza campionamento su iniziativa dell''ASL', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (120,'N. ispezioni senza campionamento su iniziativa dell''USMAF', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (143,'Campioni di soia', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (143,'Campioni di soia, riso o mais di produzione biologica', 0, TRUE); 
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (143,'Campioni di mais', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (143,'Campioni di riso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (143,'Campioni di alimenti per bambini a base di cereali', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (143,'Campioni di altri prodotti alimentari finiti NON di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (143,'Campioni effettuati su disposizione dell''USMAF', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (143,'N. ispezioni senza campionamento su iniziativa dell''ASL', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (143,'N. ispezioni senza campionamento su iniziativa dell''USMAF', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (166,'Campioni di soia', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (166,'Campioni di soia, riso o mais di produzione biologica', 0, TRUE); 
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (166,'Campioni di mais', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (166,'Campioni di riso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (166,'Campioni di alimenti per bambini a base di cereali', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (166,'Campioni di altri prodotti alimentari finiti NON di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (166,'Campioni effettuati su disposizione dell''USMAF', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (166,'N. ispezioni senza campionamento su iniziativa dell''ASL', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (166,'N. ispezioni senza campionamento su iniziativa dell''USMAF', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (75,'N. campioni  alimenti vegetali prodotti in Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (75,'N. campioni alimenti vegetali prodotti fuori Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (75,'N. campioni alimenti di o.a. prodotti in Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (75,'N. campioni alimenti di o.a. prodotti fuori Regione', 0, TRUE);  

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (98,'N. campioni  alimenti vegetali prodotti in Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (98,'N. campioni alimenti vegetali prodotti fuori Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (98,'N. campioni alimenti di o.a. prodotti in Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (98,'N. campioni alimenti di o.a. prodotti fuori Regione', 0, TRUE);  

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (121,'N. campioni  alimenti vegetali prodotti in Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (121,'N. campioni alimenti vegetali prodotti fuori Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (121,'N. campioni alimenti di o.a. prodotti in Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (121,'N. campioni alimenti di o.a. prodotti fuori Regione', 0, TRUE);  

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (144,'N. campioni  alimenti vegetali prodotti in Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (144,'N. campioni alimenti vegetali prodotti fuori Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (144,'N. campioni alimenti di o.a. prodotti in Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (144,'N. campioni alimenti di o.a. prodotti fuori Regione', 0, TRUE);  

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (167,'N. campioni  alimenti vegetali prodotti in Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (167,'N. campioni alimenti vegetali prodotti fuori Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (167,'N. campioni alimenti di o.a. prodotti in Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (167,'N. campioni alimenti di o.a. prodotti fuori Regione', 0, TRUE);  
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. 1A (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. 1C (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. 1D (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. 1E (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. 1F (BSE', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. 2.1 (additivi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. 2.2 (sostanze farmacologiche)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. 3A (diossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. 3B (diossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. 1-4 (micotossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. 2 (micotossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. 1-5 (contaminanti)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. A-5 (radionuclidi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. 6.1 (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. 6.2  (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. 6.2 pet food (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. OGM (sorvegl.)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. campioni della tab. OGM (monitor.) ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (76,'N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. 1A (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. 1C (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. 1D (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. 1E (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. 1F (BSE', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. 2.1 (additivi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. 2.2 (sostanze farmacologiche)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. 3A (diossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. 3B (diossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. 1-4 (micotossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. 2 (micotossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. 1-5 (contaminanti)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. A-5 (radionuclidi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. 6.1 (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. 6.2  (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. 6.2 pet food (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. OGM (sorvegl.)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. campioni della tab. OGM (monitor.) ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (99,'N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. 1A (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. 1C (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. 1D (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. 1E (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. 1F (BSE', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. 2.1 (additivi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. 2.2 (sostanze farmacologiche)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. 3A (diossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. 3B (diossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. 1-4 (micotossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. 2 (micotossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. 1-5 (contaminanti)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. A-5 (radionuclidi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. 6.1 (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. 6.2  (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. 6.2 pet food (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. OGM (sorvegl.)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. campioni della tab. OGM (monitor.) ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (122,'N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. 1A (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. 1C (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. 1D (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. 1E (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. 1F (BSE', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. 2.1 (additivi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. 2.2 (sostanze farmacologiche)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. 3A (diossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. 3B (diossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. 1-4 (micotossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. 2 (micotossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. 1-5 (contaminanti)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. A-5 (radionuclidi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. 6.1 (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. 6.2  (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. 6.2 pet food (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. OGM (sorvegl.)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. campioni della tab. OGM (monitor.) ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (145,'N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. 1A (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. 1C (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. 1D (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. 1E (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. 1F (BSE', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. 2.1 (additivi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. 2.2 (sostanze farmacologiche)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. 3A (diossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. 3B (diossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. 1-4 (micotossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. 2 (micotossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. 1-5 (contaminanti)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. A-5 (radionuclidi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. 6.1 (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. 6.2  (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. 6.2 pet food (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. OGM (sorvegl.)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. campioni della tab. OGM (monitor.) ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (168,'N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. allevamenti da controllare con vitelli a carni bianche ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. allevamenti da controllare con vitelli ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. allevamenti suini da controllare aventi >40 capi oppure >6 scrofe', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. allevamenti ovaiole da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. allevamenti broiler da controllare aventi >500 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. allevamenti bovini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. allevamenti struzzi da controllare aventi >10 capi ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. allevamenti tacchini ed altri avicoli da controllare aventi >250 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. allevamenti conigli da controllare aventi >250 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. allevamenti ovini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. allevamenti caprini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. allevamenti bufalini da controllare aventi >10 capi ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. allevamenti equini da controllare aventi >10 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. allevamenti animali da pelliccia da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. allevamenti pesci da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. mezzi di trasporto da controllare durante il trasporto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. mezzi di trasporto da controllare c/o posti di controllo', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. mezzi di trasporto da controllare presso il luogo di partenza ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. mezzi di trasporto da controllare presso luoghi di destinazione diversi dai macelli ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. mezzi di trasporto autorizzati per il trasporto superiore alle 8 ore da controllare c/o il macello di destinazione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. mezzi di trasporto autorizzati per il trasporto inferiore alle 8 ore da controllare c/o il macello di destinazione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (77,'N. liste di riscontro da redigere durante la macellazione', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. allevamenti da controllare con vitelli a carni bianche ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. allevamenti da controllare con vitelli ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. allevamenti suini da controllare aventi >40 capi oppure >6 scrofe', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. allevamenti ovaiole da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. allevamenti broiler da controllare aventi >500 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. allevamenti bovini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. allevamenti struzzi da controllare aventi >10 capi ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. allevamenti tacchini ed altri avicoli da controllare aventi >250 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. allevamenti conigli da controllare aventi >250 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. allevamenti ovini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. allevamenti caprini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. allevamenti bufalini da controllare aventi >10 capi ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. allevamenti equini da controllare aventi >10 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. allevamenti animali da pelliccia da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. allevamenti pesci da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. mezzi di trasporto da controllare durante il trasporto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. mezzi di trasporto da controllare c/o posti di controllo', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. mezzi di trasporto da controllare presso il luogo di partenza ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. mezzi di trasporto da controllare presso luoghi di destinazione diversi dai macelli ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. mezzi di trasporto autorizzati per il trasporto superiore alle 8 ore da controllare c/o il macello di destinazione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. mezzi di trasporto autorizzati per il trasporto inferiore alle 8 ore da controllare c/o il macello di destinazione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (100,'N. liste di riscontro da redigere durante la macellazione', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. allevamenti da controllare con vitelli a carni bianche ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. allevamenti da controllare con vitelli ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. allevamenti suini da controllare aventi >40 capi oppure >6 scrofe', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. allevamenti ovaiole da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. allevamenti broiler da controllare aventi >500 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. allevamenti bovini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. allevamenti struzzi da controllare aventi >10 capi ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. allevamenti tacchini ed altri avicoli da controllare aventi >250 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. allevamenti conigli da controllare aventi >250 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. allevamenti ovini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. allevamenti caprini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. allevamenti bufalini da controllare aventi >10 capi ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. allevamenti equini da controllare aventi >10 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. allevamenti animali da pelliccia da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. allevamenti pesci da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. mezzi di trasporto da controllare durante il trasporto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. mezzi di trasporto da controllare c/o posti di controllo', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. mezzi di trasporto da controllare presso il luogo di partenza ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. mezzi di trasporto da controllare presso luoghi di destinazione diversi dai macelli ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. mezzi di trasporto autorizzati per il trasporto superiore alle 8 ore da controllare c/o il macello di destinazione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. mezzi di trasporto autorizzati per il trasporto inferiore alle 8 ore da controllare c/o il macello di destinazione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (123,'N. liste di riscontro da redigere durante la macellazione', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. allevamenti da controllare con vitelli a carni bianche ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. allevamenti da controllare con vitelli ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. allevamenti suini da controllare aventi >40 capi oppure >6 scrofe', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. allevamenti ovaiole da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. allevamenti broiler da controllare aventi >500 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. allevamenti bovini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. allevamenti struzzi da controllare aventi >10 capi ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. allevamenti tacchini ed altri avicoli da controllare aventi >250 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. allevamenti conigli da controllare aventi >250 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. allevamenti ovini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. allevamenti caprini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. allevamenti bufalini da controllare aventi >10 capi ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. allevamenti equini da controllare aventi >10 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. allevamenti animali da pelliccia da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. allevamenti pesci da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. mezzi di trasporto da controllare durante il trasporto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. mezzi di trasporto da controllare c/o posti di controllo', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. mezzi di trasporto da controllare presso il luogo di partenza ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. mezzi di trasporto da controllare presso luoghi di destinazione diversi dai macelli ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. mezzi di trasporto autorizzati per il trasporto superiore alle 8 ore da controllare c/o il macello di destinazione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. mezzi di trasporto autorizzati per il trasporto inferiore alle 8 ore da controllare c/o il macello di destinazione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (146,'N. liste di riscontro da redigere durante la macellazione', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. allevamenti da controllare con vitelli a carni bianche ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. allevamenti da controllare con vitelli ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. allevamenti suini da controllare aventi >40 capi oppure >6 scrofe', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. allevamenti ovaiole da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. allevamenti broiler da controllare aventi >500 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. allevamenti bovini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. allevamenti struzzi da controllare aventi >10 capi ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. allevamenti tacchini ed altri avicoli da controllare aventi >250 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. allevamenti conigli da controllare aventi >250 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. allevamenti ovini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. allevamenti caprini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. allevamenti bufalini da controllare aventi >10 capi ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. allevamenti equini da controllare aventi >10 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. allevamenti animali da pelliccia da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. allevamenti pesci da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. mezzi di trasporto da controllare durante il trasporto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. mezzi di trasporto da controllare c/o posti di controllo', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. mezzi di trasporto da controllare presso il luogo di partenza ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. mezzi di trasporto da controllare presso luoghi di destinazione diversi dai macelli ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. mezzi di trasporto autorizzati per il trasporto superiore alle 8 ore da controllare c/o il macello di destinazione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. mezzi di trasporto autorizzati per il trasporto inferiore alle 8 ore da controllare c/o il macello di destinazione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (169,'N. liste di riscontro da redigere durante la macellazione', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (78,'Ispezioni effettuate nei grossisti di medicinali veterinari NON autorizzati alla vendita diretta (art 66, Dlvo 193/2006)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (78,'Ispezioni effettuate nei grossisti autorizzati alla vendita diretta di medicinali veterinari (art 70, Dlvo 193/2006)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (101,'Ispezioni effettuate nei grossisti di medicinali veterinari NON autorizzati alla vendita diretta (art 66, Dlvo 193/2006)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (101,'Ispezioni effettuate nei grossisti autorizzati alla vendita diretta di medicinali veterinari (art 70, Dlvo 193/2006)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (124,'Ispezioni effettuate nei grossisti di medicinali veterinari NON autorizzati alla vendita diretta (art 66, Dlvo 193/2006)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (124,'Ispezioni effettuate nei grossisti autorizzati alla vendita diretta di medicinali veterinari (art 70, Dlvo 193/2006)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (147,'Ispezioni effettuate nei grossisti di medicinali veterinari NON autorizzati alla vendita diretta (art 66, Dlvo 193/2006)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (147,'Ispezioni effettuate nei grossisti autorizzati alla vendita diretta di medicinali veterinari (art 70, Dlvo 193/2006)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (170,'Ispezioni effettuate nei grossisti di medicinali veterinari NON autorizzati alla vendita diretta (art 66, Dlvo 193/2006)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (170,'Ispezioni effettuate nei grossisti autorizzati alla vendita diretta di medicinali veterinari (art 70, Dlvo 193/2006)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (208,'Ispezioni effettuate nei grossisti di medicinali veterinari NON autorizzati alla vendita diretta (art 66, Dlvo 193/2006)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (208,'Ispezioni effettuate nei grossisti autorizzati alla vendita diretta di medicinali veterinari (art 70, Dlvo 193/2006)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (79,'N. AUDIT', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (102,'N. AUDIT', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (125,'N. AUDIT', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (148,'N. AUDIT', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (171,'N. AUDIT', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (80,'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (103,'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (126,'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (149,'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (172,'N. ispezioni', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (81,'N. cani da microchippare e iscrivere in BDR', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (104,'N. cani da microchippare e iscrivere in BDR', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (127,'N. cani da microchippare e iscrivere in BDR', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (150,'N. cani da microchippare e iscrivere in BDR', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (173,'N. cani da microchippare e iscrivere in BDR', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (82,'N. supervisioni tipo 1.1. (su controlli ufficiali svolti nei sette giorni precedenti dal personale incaricato con esito favorevole o con il rilievo di non conformita'' ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (82,'N. supervisioni tipo 1.2. (per la verifica del livello di know how del personale)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (82,'N. supervisioni tipo 2. (documentale - non vengono impiegate U.I. in quanto è una attività svolta in ufficio)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (82,'N. supervisioni tipo 3. (mediante simulazioni)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (105,'N. supervisioni tipo 1.1. (su controlli ufficiali svolti nei sette giorni precedenti dal personale incaricato con esito favorevole o con il rilievo di non conformita'' ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (105,'N. supervisioni tipo 1.2. (per la verifica del livello di know how del personale)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (105,'N. supervisioni tipo 2. (documentale - non vengono impiegate U.I. in quanto è una attività svolta in ufficio)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (105,'N. supervisioni tipo 3. (mediante simulazioni)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (128,'N. supervisioni tipo 1.1. (su controlli ufficiali svolti nei sette giorni precedenti dal personale incaricato con esito favorevole o con il rilievo di non conformita'' ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (128,'N. supervisioni tipo 1.2. (per la verifica del livello di know how del personale)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (128,'N. supervisioni tipo 2. (documentale - non vengono impiegate U.I. in quanto è una attività svolta in ufficio)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (128,'N. supervisioni tipo 3. (mediante simulazioni)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (151,'N. supervisioni tipo 1.1. (su controlli ufficiali svolti nei sette giorni precedenti dal personale incaricato con esito favorevole o con il rilievo di non conformita'' ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (151,'N. supervisioni tipo 1.2. (per la verifica del livello di know how del personale)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (151,'N. supervisioni tipo 2. (documentale - non vengono impiegate U.I. in quanto è una attività svolta in ufficio)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (151,'N. supervisioni tipo 3. (mediante simulazioni)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (174,'N. supervisioni tipo 1.1. (su controlli ufficiali svolti nei sette giorni precedenti dal personale incaricato con esito favorevole o con il rilievo di non conformita'' ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (174,'N. supervisioni tipo 1.2. (per la verifica del livello di know how del personale)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (174,'N. supervisioni tipo 2. (documentale - non vengono impiegate U.I. in quanto è una attività svolta in ufficio)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (174,'N. supervisioni tipo 3. (mediante simulazioni)', 0, TRUE);
----------------------------------------------------------------------sezione B


select * from dpat order by id_asl
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ. B DEL DPAR', 5, TRUE);
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ. B DEL DPAR', 6, TRUE);
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ. B DEL DPAR', 7, TRUE);
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ. B DEL DPAR', 8, TRUE);
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ. B DEL DPAR', 9, TRUE);
select * from dpat_sezione where id_dpat = 5

INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 22', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 23', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 24', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 25', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 26', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 27', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 63', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 28', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 102', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 29', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 30', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 31', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 32', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 34', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 35', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 36', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 37', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 39', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 40', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'PIANO 100', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'ATTIVITA'' 5', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'ATTIVITA'' 6', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'ATTIVITA'' 7', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'ATTIVITA'' 8', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'ATTIVITA'' 9', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (11, 'ATTIVITA'' 18', TRUE);

INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 22', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 23', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 24', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 25', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 26', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 27', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 63', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 28', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 102', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 29', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 30', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 31', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 32', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 34', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 35', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 36', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 37', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 39', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 40', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'PIANO 100', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'ATTIVITA'' 5', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'ATTIVITA'' 6', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'ATTIVITA'' 7', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'ATTIVITA'' 8', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'ATTIVITA'' 9', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (12, 'ATTIVITA'' 18', TRUE);


INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 22', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 23', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 24', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 25', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 26', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 27', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 63', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 28', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 102', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 29', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 30', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 31', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 32', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 34', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 35', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 36', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 37', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 39', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 40', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'PIANO 100', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'ATTIVITA'' 5', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'ATTIVITA'' 6', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'ATTIVITA'' 7', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'ATTIVITA'' 8', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'ATTIVITA'' 9', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (13, 'ATTIVITA'' 18', TRUE);


INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 22', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 23', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 24', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 25', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 26', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 27', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 63', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 28', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 102', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 29', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 30', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 31', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 32', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 34', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 35', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 36', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 37', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 39', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 40', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'PIANO 100', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'ATTIVITA'' 5', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'ATTIVITA'' 6', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'ATTIVITA'' 7', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'ATTIVITA'' 8', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'ATTIVITA'' 9', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (14, 'ATTIVITA'' 18', TRUE);


INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 22', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 23', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 24', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 25', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 26', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 27', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 63', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 28', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 102', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 29', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 30', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 31', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 32', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 34', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 35', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 36', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 37', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 39', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 40', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'PIANO 100', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'ATTIVITA'' 5', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'ATTIVITA'' 6', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'ATTIVITA'' 7', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'ATTIVITA'' 8', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'ATTIVITA'' 9', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (15, 'ATTIVITA'' 18', TRUE);


INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (209, 0,'Piano di monitoraggio per l’eradicazione della MVS nelle aziende da riproduzione e ingrasso (1 suino campionato = 1 UBA)', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (210, 0,'Piano di monitoraggio per la verifica dei requisiti di biosicurezza negli allevamenti suini', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (211, 0,'Piano di monitoraggio nazionale per la sorveglianza della bluetongue in animali sentinella', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (212, 0,'Piano di monitoraggio influenza aviaria', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (213, 0,'Piano di monitoraggio west nile disease', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (214, 0,'Piano di monitoraggio arterite virale equina', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (215, 0,'Piano di monitoraggio fauna selvatica', TRUE, FALSE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (216, 0,'Piano di monitoraggio sugli alimenti destinati ad un''alimentazione particolare, sugli alimenti arricchiti di vitamine e minerali, novel food, integratori alimentari', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (217, 0,'Piano di monitoraggio novel food', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (218, 0,'Piano di monitoraggio sui requisiti microbiologici dei pasti di origine animale prodotti nei centri di produzione alimenti destinati alla ristorazione collettiva', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (219, 0,'Piano di monitoraggio sui requisiti microbiologici dei pasti di origine NON animale prodotti nei centri di produzione alimenti destinati alla ristorazione collettiva', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (220, 0,'Piano di monitoraggio sui requisiti microbiologici dei prodotti alimentari di origine animale prodotti e/o somministrati nelle imprese di ristorazione pubblica', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (221, 0,'Piano di monitoraggio sui requisiti microbiologici dei prodotti alimentari NON di origine animale prodotti e/o somministrati nelle imprese di ristorazione', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (222, 0,'Piano di monitoraggio sui requisiti microbiologici dei prodotti alimentari NON di origine animale', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (223, 0,'Piano di monitoraggio dell''applicazione delle disposizioni finalizzate alla prevenzione del gozzo endemico e di altre patologie da carenza iodica', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (224, 0,'Piano  di monitoraggio sui prodotti di origine animale introdotti da paesi comunitari', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (225, 0,'Piano di monitoraggio sulla corrispondenza tra i cani detenuti nei canili e quelli registrati in BDR', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (226, 0,'Piano di monitoraggio contaminanti chimici in prodotti alimentari NON di origine  animale', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (227, 0,'Piano di monitoraggio sull’immissione in commercio e l’utilizzazione dei prodotti fitosanitari', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (228, 0,'Piano di monitoraggio Terra dei Fuochi', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (229, 0,'Audit negli stabilimenti riconosciuti ex  Reg. CE 852/04, ex Reg. CE 1069/09 ed ex Reg CE 853/04 (ad esclusione della sez. IX già contemplati nell''attivita'' n. 1)', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (230, 0,'Ispezioni effettuate per la verifica della risoluzione di non conformita'' significative e gravi', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (231, 0,'Diagnostica cadaverica dei sinantropi, dei cani e dei gatti', TRUE, FALSE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (232, 0,'Diagnostica cadaverica dei grandi animali effettuata direttamente in allevamento', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (233, 0,'Sterilizzazione animali senza padrone', TRUE, FALSE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (234, 0,'Ispezioni con la tecnica della sorveglianza in tutti i tipi di stabilimento', TRUE, TRUE);

INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (235, 0,'Piano di monitoraggio per l’eradicazione della MVS nelle aziende da riproduzione e ingrasso (1 suino campionato = 1 UBA)', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (236, 0,'Piano di monitoraggio per la verifica dei requisiti di biosicurezza negli allevamenti suini', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (237, 0,'Piano di monitoraggio nazionale per la sorveglianza della bluetongue in animali sentinella', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (238, 0,'Piano di monitoraggio influenza aviaria', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (239, 0,'Piano di monitoraggio west nile disease', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (240, 0,'Piano di monitoraggio arterite virale equina', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (241, 0,'Piano di monitoraggio fauna selvatica', TRUE, FALSE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (242, 0,'Piano di monitoraggio sugli alimenti destinati ad un''alimentazione particolare, sugli alimenti arricchiti di vitamine e minerali, novel food, integratori alimentari', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (243, 0,'Piano di monitoraggio novel food', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (244, 0,'Piano di monitoraggio sui requisiti microbiologici dei pasti di origine animale prodotti nei centri di produzione alimenti destinati alla ristorazione collettiva', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (245, 0,'Piano di monitoraggio sui requisiti microbiologici dei pasti di origine NON animale prodotti nei centri di produzione alimenti destinati alla ristorazione collettiva', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (246, 0,'Piano di monitoraggio sui requisiti microbiologici dei prodotti alimentari di origine animale prodotti e/o somministrati nelle imprese di ristorazione pubblica', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (247, 0,'Piano di monitoraggio sui requisiti microbiologici dei prodotti alimentari NON di origine animale prodotti e/o somministrati nelle imprese di ristorazione', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (248, 0,'Piano di monitoraggio sui requisiti microbiologici dei prodotti alimentari NON di origine animale', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (249, 0,'Piano di monitoraggio dell''applicazione delle disposizioni finalizzate alla prevenzione del gozzo endemico e di altre patologie da carenza iodica', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (250, 0,'Piano  di monitoraggio sui prodotti di origine animale introdotti da paesi comunitari', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (251, 0,'Piano di monitoraggio sulla corrispondenza tra i cani detenuti nei canili e quelli registrati in BDR', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (252, 0,'Piano di monitoraggio contaminanti chimici in prodotti alimentari NON di origine  animale', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (253, 0,'Piano di monitoraggio sull’immissione in commercio e l’utilizzazione dei prodotti fitosanitari', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (254, 0,'Piano di monitoraggio Terra dei Fuochi', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (255, 0,'Audit negli stabilimenti riconosciuti ex  Reg. CE 852/04, ex Reg. CE 1069/09 ed ex Reg CE 853/04 (ad esclusione della sez. IX già contemplati nell''attivita'' n. 1)', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (256, 0,'Ispezioni effettuate per la verifica della risoluzione di non conformita'' significative e gravi', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (257, 0,'Diagnostica cadaverica dei sinantropi, dei cani e dei gatti', TRUE, FALSE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (258, 0,'Diagnostica cadaverica dei grandi animali effettuata direttamente in allevamento', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (259, 0,'Sterilizzazione animali senza padrone', TRUE, FALSE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (260, 0,'Ispezioni con la tecnica della sorveglianza in tutti i tipi di stabilimento', TRUE, TRUE);

INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (261, 0,'Piano di monitoraggio per l’eradicazione della MVS nelle aziende da riproduzione e ingrasso (1 suino campionato = 1 UBA)', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (262, 0,'Piano di monitoraggio per la verifica dei requisiti di biosicurezza negli allevamenti suini', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (263, 0,'Piano di monitoraggio nazionale per la sorveglianza della bluetongue in animali sentinella', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (264, 0,'Piano di monitoraggio influenza aviaria', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (265, 0,'Piano di monitoraggio west nile disease', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (266, 0,'Piano di monitoraggio arterite virale equina', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (267, 0,'Piano di monitoraggio fauna selvatica', TRUE, FALSE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (268, 0,'Piano di monitoraggio sugli alimenti destinati ad un''alimentazione particolare, sugli alimenti arricchiti di vitamine e minerali, novel food, integratori alimentari', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (269, 0,'Piano di monitoraggio novel food', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (270, 0,'Piano di monitoraggio sui requisiti microbiologici dei pasti di origine animale prodotti nei centri di produzione alimenti destinati alla ristorazione collettiva', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (271, 0,'Piano di monitoraggio sui requisiti microbiologici dei pasti di origine NON animale prodotti nei centri di produzione alimenti destinati alla ristorazione collettiva', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (272, 0,'Piano di monitoraggio sui requisiti microbiologici dei prodotti alimentari di origine animale prodotti e/o somministrati nelle imprese di ristorazione pubblica', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (273, 0,'Piano di monitoraggio sui requisiti microbiologici dei prodotti alimentari NON di origine animale prodotti e/o somministrati nelle imprese di ristorazione', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (274, 0,'Piano di monitoraggio sui requisiti microbiologici dei prodotti alimentari NON di origine animale', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (275, 0,'Piano di monitoraggio dell''applicazione delle disposizioni finalizzate alla prevenzione del gozzo endemico e di altre patologie da carenza iodica', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (276, 0,'Piano  di monitoraggio sui prodotti di origine animale introdotti da paesi comunitari', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (277, 0,'Piano di monitoraggio sulla corrispondenza tra i cani detenuti nei canili e quelli registrati in BDR', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (278, 0,'Piano di monitoraggio contaminanti chimici in prodotti alimentari NON di origine  animale', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (279, 0,'Piano di monitoraggio sull’immissione in commercio e l’utilizzazione dei prodotti fitosanitari', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (280, 0,'Piano di monitoraggio Terra dei Fuochi', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (281, 0,'Audit negli stabilimenti riconosciuti ex  Reg. CE 852/04, ex Reg. CE 1069/09 ed ex Reg CE 853/04 (ad esclusione della sez. IX già contemplati nell''attivita'' n. 1)', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (282, 0,'Ispezioni effettuate per la verifica della risoluzione di non conformita'' significative e gravi', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (283, 0,'Diagnostica cadaverica dei sinantropi, dei cani e dei gatti', TRUE, FALSE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (284, 0,'Diagnostica cadaverica dei grandi animali effettuata direttamente in allevamento', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (285, 0,'Sterilizzazione animali senza padrone', TRUE, FALSE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (286, 0,'Ispezioni con la tecnica della sorveglianza in tutti i tipi di stabilimento', TRUE, TRUE);


INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (287, 0,'Piano di monitoraggio per l’eradicazione della MVS nelle aziende da riproduzione e ingrasso (1 suino campionato = 1 UBA)', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (288, 0,'Piano di monitoraggio per la verifica dei requisiti di biosicurezza negli allevamenti suini', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (289, 0,'Piano di monitoraggio nazionale per la sorveglianza della bluetongue in animali sentinella', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (290, 0,'Piano di monitoraggio influenza aviaria', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (291, 0,'Piano di monitoraggio west nile disease', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (292, 0,'Piano di monitoraggio arterite virale equina', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (293, 0,'Piano di monitoraggio fauna selvatica', TRUE, FALSE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (294, 0,'Piano di monitoraggio sugli alimenti destinati ad un''alimentazione particolare, sugli alimenti arricchiti di vitamine e minerali, novel food, integratori alimentari', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (295, 0,'Piano di monitoraggio novel food', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (296, 0,'Piano di monitoraggio sui requisiti microbiologici dei pasti di origine animale prodotti nei centri di produzione alimenti destinati alla ristorazione collettiva', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (297, 0,'Piano di monitoraggio sui requisiti microbiologici dei pasti di origine NON animale prodotti nei centri di produzione alimenti destinati alla ristorazione collettiva', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (298, 0,'Piano di monitoraggio sui requisiti microbiologici dei prodotti alimentari di origine animale prodotti e/o somministrati nelle imprese di ristorazione pubblica', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (299, 0,'Piano di monitoraggio sui requisiti microbiologici dei prodotti alimentari NON di origine animale prodotti e/o somministrati nelle imprese di ristorazione', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (300, 0,'Piano di monitoraggio sui requisiti microbiologici dei prodotti alimentari NON di origine animale', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (301, 0,'Piano di monitoraggio dell''applicazione delle disposizioni finalizzate alla prevenzione del gozzo endemico e di altre patologie da carenza iodica', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (302, 0,'Piano  di monitoraggio sui prodotti di origine animale introdotti da paesi comunitari', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (303, 0,'Piano di monitoraggio sulla corrispondenza tra i cani detenuti nei canili e quelli registrati in BDR', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (304, 0,'Piano di monitoraggio contaminanti chimici in prodotti alimentari NON di origine  animale', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (305, 0,'Piano di monitoraggio sull’immissione in commercio e l’utilizzazione dei prodotti fitosanitari', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (306, 0,'Piano di monitoraggio Terra dei Fuochi', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (307, 0,'Audit negli stabilimenti riconosciuti ex  Reg. CE 852/04, ex Reg. CE 1069/09 ed ex Reg CE 853/04 (ad esclusione della sez. IX già contemplati nell''attivita'' n. 1)', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (308, 0,'Ispezioni effettuate per la verifica della risoluzione di non conformita'' significative e gravi', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (309, 0,'Diagnostica cadaverica dei sinantropi, dei cani e dei gatti', TRUE, FALSE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (310, 0,'Diagnostica cadaverica dei grandi animali effettuata direttamente in allevamento', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (311, 0,'Sterilizzazione animali senza padrone', TRUE, FALSE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (312, 0,'Ispezioni con la tecnica della sorveglianza in tutti i tipi di stabilimento', TRUE, TRUE);

INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (313, 0,'Piano di monitoraggio per l’eradicazione della MVS nelle aziende da riproduzione e ingrasso (1 suino campionato = 1 UBA)', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (314, 0,'Piano di monitoraggio per la verifica dei requisiti di biosicurezza negli allevamenti suini', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (315, 0,'Piano di monitoraggio nazionale per la sorveglianza della bluetongue in animali sentinella', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (316, 0,'Piano di monitoraggio influenza aviaria', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (317, 0,'Piano di monitoraggio west nile disease', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (318, 0,'Piano di monitoraggio arterite virale equina', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (319, 0,'Piano di monitoraggio fauna selvatica', TRUE, FALSE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (320, 0,'Piano di monitoraggio sugli alimenti destinati ad un''alimentazione particolare, sugli alimenti arricchiti di vitamine e minerali, novel food, integratori alimentari', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (321, 0,'Piano di monitoraggio novel food', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (322, 0,'Piano di monitoraggio sui requisiti microbiologici dei pasti di origine animale prodotti nei centri di produzione alimenti destinati alla ristorazione collettiva', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (323, 0,'Piano di monitoraggio sui requisiti microbiologici dei pasti di origine NON animale prodotti nei centri di produzione alimenti destinati alla ristorazione collettiva', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (324, 0,'Piano di monitoraggio sui requisiti microbiologici dei prodotti alimentari di origine animale prodotti e/o somministrati nelle imprese di ristorazione pubblica', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (325, 0,'Piano di monitoraggio sui requisiti microbiologici dei prodotti alimentari NON di origine animale prodotti e/o somministrati nelle imprese di ristorazione', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (326, 0,'Piano di monitoraggio sui requisiti microbiologici dei prodotti alimentari NON di origine animale', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (327, 0,'Piano di monitoraggio dell''applicazione delle disposizioni finalizzate alla prevenzione del gozzo endemico e di altre patologie da carenza iodica', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (328, 0,'Piano  di monitoraggio sui prodotti di origine animale introdotti da paesi comunitari', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (329, 0,'Piano di monitoraggio sulla corrispondenza tra i cani detenuti nei canili e quelli registrati in BDR', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (330, 0,'Piano di monitoraggio contaminanti chimici in prodotti alimentari NON di origine  animale', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (331, 0,'Piano di monitoraggio sull’immissione in commercio e l’utilizzazione dei prodotti fitosanitari', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (332, 0,'Piano di monitoraggio Terra dei Fuochi', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (333, 0,'Audit negli stabilimenti riconosciuti ex  Reg. CE 852/04, ex Reg. CE 1069/09 ed ex Reg CE 853/04 (ad esclusione della sez. IX già contemplati nell''attivita'' n. 1)', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (334, 0,'Ispezioni effettuate per la verifica della risoluzione di non conformita'' significative e gravi', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (335, 0,'Diagnostica cadaverica dei sinantropi, dei cani e dei gatti', TRUE, FALSE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (336, 0,'Diagnostica cadaverica dei grandi animali effettuata direttamente in allevamento', TRUE, TRUE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (337, 0,'Sterilizzazione animali senza padrone', TRUE, FALSE);
INSERT INTO dpat_attivita(id_piano, ui, description, enabled, ui_calcolabile) VALUES (338, 0,'Ispezioni con la tecnica della sorveglianza in tutti i tipi di stabilimento', TRUE, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (217, 'N. animali da controllare in allevamenti da riproduzione a ciclo chiuso  (n. 52 UBA x U.I. x 1 accesso)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (217, 'N. allevamenti da riproduzione a ciclo chiuso (0,125 U.I. x 1 accesso x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (217, 'N. animali da controllare in allevamenti da riproduzione a ciclo aperto (n. 52 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (217, 'n. allevamenti da riproduzione a ciclo aperto (0,125 U.I. x 2 accessi x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (217, 'N. animali da controllare in allevamenti da ingrasso da macello (n. 52 UBA x U.I. x 2 accessi) ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (217, 'n. allevamenti da ingrasso da macello (0,125 U.I. x 2 accessi x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (217, 'N. animali da controllare in allevamenti da ingrasso da vita e stalle di sosta (n. 52 UBA U.I. x 12 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (217, 'n. allevamenti da ingrasso da vita e stalle di sosta (0,125 U.I. x 12 accessi x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (243, 'N. animali da controllare in allevamenti da riproduzione a ciclo chiuso  (n. 52 UBA x U.I. x 1 accesso)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (243, 'N. allevamenti da riproduzione a ciclo chiuso (0,125 U.I. x 1 accesso x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (243, 'N. animali da controllare in allevamenti da riproduzione a ciclo aperto (n. 52 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (243, 'n. allevamenti da riproduzione a ciclo aperto (0,125 U.I. x 2 accessi x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (243, 'N. animali da controllare in allevamenti da ingrasso da macello (n. 52 UBA x U.I. x 2 accessi) ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (243, 'n. allevamenti da ingrasso da macello (0,125 U.I. x 2 accessi x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (243, 'N. animali da controllare in allevamenti da ingrasso da vita e stalle di sosta (n. 52 UBA U.I. x 12 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (243, 'n. allevamenti da ingrasso da vita e stalle di sosta (0,125 U.I. x 12 accessi x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (269, 'N. animali da controllare in allevamenti da riproduzione a ciclo chiuso  (n. 52 UBA x U.I. x 1 accesso)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (269, 'N. allevamenti da riproduzione a ciclo chiuso (0,125 U.I. x 1 accesso x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (269, 'N. animali da controllare in allevamenti da riproduzione a ciclo aperto (n. 52 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (269, 'n. allevamenti da riproduzione a ciclo aperto (0,125 U.I. x 2 accessi x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (269, 'N. animali da controllare in allevamenti da ingrasso da macello (n. 52 UBA x U.I. x 2 accessi) ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (269, 'n. allevamenti da ingrasso da macello (0,125 U.I. x 2 accessi x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (269, 'N. animali da controllare in allevamenti da ingrasso da vita e stalle di sosta (n. 52 UBA U.I. x 12 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (269, 'n. allevamenti da ingrasso da vita e stalle di sosta (0,125 U.I. x 12 accessi x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (295, 'N. animali da controllare in allevamenti da riproduzione a ciclo chiuso  (n. 52 UBA x U.I. x 1 accesso)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (295, 'N. allevamenti da riproduzione a ciclo chiuso (0,125 U.I. x 1 accesso x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (295, 'N. animali da controllare in allevamenti da riproduzione a ciclo aperto (n. 52 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (295, 'n. allevamenti da riproduzione a ciclo aperto (0,125 U.I. x 2 accessi x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (295, 'N. animali da controllare in allevamenti da ingrasso da macello (n. 52 UBA x U.I. x 2 accessi) ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (295, 'n. allevamenti da ingrasso da macello (0,125 U.I. x 2 accessi x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (295, 'N. animali da controllare in allevamenti da ingrasso da vita e stalle di sosta (n. 52 UBA U.I. x 12 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (295, 'n. allevamenti da ingrasso da vita e stalle di sosta (0,125 U.I. x 12 accessi x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (321, 'N. animali da controllare in allevamenti da riproduzione a ciclo chiuso  (n. 52 UBA x U.I. x 1 accesso)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (321, 'N. allevamenti da riproduzione a ciclo chiuso (0,125 U.I. x 1 accesso x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (321, 'N. animali da controllare in allevamenti da riproduzione a ciclo aperto (n. 52 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (321, 'n. allevamenti da riproduzione a ciclo aperto (0,125 U.I. x 2 accessi x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (321, 'N. animali da controllare in allevamenti da ingrasso da macello (n. 52 UBA x U.I. x 2 accessi) ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (321, 'n. allevamenti da ingrasso da macello (0,125 U.I. x 2 accessi x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (321, 'N. animali da controllare in allevamenti da ingrasso da vita e stalle di sosta (n. 52 UBA U.I. x 12 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (321, 'n. allevamenti da ingrasso da vita e stalle di sosta (0,125 U.I. x 12 accessi x allevamento con una media di n. 5 animali controllabili per allevamento)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (218, 'N. ispezioni effettuate negli allevamenti di nuovo insediamento (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (218, 'N. ispezioni effettuate negli allevamenti RCA e ingrasso per vita (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (244, 'N. ispezioni effettuate negli allevamenti di nuovo insediamento (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (244, 'N. ispezioni effettuate negli allevamenti RCA e ingrasso per vita (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (270, 'N. ispezioni effettuate negli allevamenti di nuovo insediamento (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (270, 'N. ispezioni effettuate negli allevamenti RCA e ingrasso per vita (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (296, 'N. ispezioni effettuate negli allevamenti di nuovo insediamento (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (296, 'N. ispezioni effettuate negli allevamenti RCA e ingrasso per vita (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (322, 'N. ispezioni effettuate negli allevamenti di nuovo insediamento (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (322, 'N. ispezioni effettuate negli allevamenti RCA e ingrasso per vita (numero presunto in base ai dati storici)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (219, 'n. interventi per sorveglianza entomologica ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (219, 'N. bovini e bufalini sentinella (n. 16 UBA = 1 U.I.)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (219, 'N. ovini sentinella (n. 16 UBA = 1 U.I.) (n. 3 ovini = n. 1 UBA)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (245, 'n. interventi per sorveglianza entomologica ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (245, 'N. bovini e bufalini sentinella (n. 16 UBA = 1 U.I.)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (245, 'N. ovini sentinella (n. 16 UBA = 1 U.I.) (n. 3 ovini = n. 1 UBA)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (271, 'n. interventi per sorveglianza entomologica ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (271, 'N. bovini e bufalini sentinella (n. 16 UBA = 1 U.I.)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (271, 'N. ovini sentinella (n. 16 UBA = 1 U.I.) (n. 3 ovini = n. 1 UBA)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (297, 'n. interventi per sorveglianza entomologica ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (297, 'N. bovini e bufalini sentinella (n. 16 UBA = 1 U.I.)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (297, 'N. ovini sentinella (n. 16 UBA = 1 U.I.) (n. 3 ovini = n. 1 UBA)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (323, 'n. interventi per sorveglianza entomologica ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (323, 'N. bovini e bufalini sentinella (n. 16 UBA = 1 U.I.)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (323, 'N. ovini sentinella (n. 16 UBA = 1 U.I.) (n. 3 ovini = n. 1 UBA)', 0, TRUE);

----------------------------------------

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (220, 'N. ispezioni negli allevamenti di  polli riproduttori', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (220, 'N. ispezioni negli allevamenti di  galline ovaiole', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (220, 'N. ispezioni negli allevamenti di  galline ovaiole free-range', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (220, 'N. ispezioni in allevamenti di selvaggina', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (220, 'N. ispezioni in svezzatori/commercianti (D.M. 25/6/10)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (246, 'N. ispezioni negli allevamenti di  polli riproduttori', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (246, 'N. ispezioni negli allevamenti di  galline ovaiole', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (246, 'N. ispezioni negli allevamenti di  galline ovaiole free-range', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (246, 'N. ispezioni in allevamenti di selvaggina', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (246, 'N. ispezioni in svezzatori/commercianti (D.M. 25/6/10)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (272, 'N. ispezioni negli allevamenti di  polli riproduttori', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (272, 'N. ispezioni negli allevamenti di  galline ovaiole', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (272, 'N. ispezioni negli allevamenti di  galline ovaiole free-range', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (272, 'N. ispezioni in allevamenti di selvaggina', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (272, 'N. ispezioni in svezzatori/commercianti (D.M. 25/6/10)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (298, 'N. ispezioni negli allevamenti di  polli riproduttori', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (298, 'N. ispezioni negli allevamenti di  galline ovaiole', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (298, 'N. ispezioni negli allevamenti di  galline ovaiole free-range', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (298, 'N. ispezioni in allevamenti di selvaggina', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (298, 'N. ispezioni in svezzatori/commercianti (D.M. 25/6/10)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (324, 'N. ispezioni negli allevamenti di  polli riproduttori', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (324, 'N. ispezioni negli allevamenti di  galline ovaiole', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (324, 'N. ispezioni negli allevamenti di  galline ovaiole free-range', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (324, 'N. ispezioni in allevamenti di selvaggina', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (324, 'N. ispezioni in svezzatori/commercianti (D.M. 25/6/10)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (221, 'n. aziende avicole da controllare mensilmente', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (221, 'N. equini sentinella', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (247, 'n. aziende avicole da controllare mensilmente', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (247, 'N. equini sentinella', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (273, 'n. aziende avicole da controllare mensilmente', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (273, 'N. equini sentinella', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (299, 'n. aziende avicole da controllare mensilmente', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (299, 'N. equini sentinella', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (325, 'n. aziende avicole da controllare mensilmente', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (325, 'N. equini sentinella', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (222, 'n. aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (222, 'N. equini da controllare', 0, TRUE); 

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (248, 'n. aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (248, 'N. equini da controllare', 0, TRUE); 

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (274, 'n. aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (274, 'N. equini da controllare', 0, TRUE); 

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (300, 'n. aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (300, 'N. equini da controllare', 0, TRUE); 

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (326, 'n. aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (326, 'N. equini da controllare', 0, TRUE); 

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (223, 'N. campioni inviati all''IZSM', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (249, 'N. campioni inviati all''IZSM', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (275, 'N. campioni inviati all''IZSM', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (301, 'N. campioni inviati all''IZSM', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (327, 'N. campioni inviati all''IZSM', 0, TRUE);


INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (224, 'N. campioni 28 A', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (224, 'N. campioni 28 B', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (224, 'N. campioni 28 C', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (224, 'N. campioni 28 D', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (224, 'N. campioni 28 E', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (224, 'N. campioni 28 F', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (224, 'N. campioni 28 G', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (224, 'N. campioni 28 H', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (224, 'N. campioni 28 I', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (224, 'N. campioni 28 L', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (224, 'N. campioni 28 M', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (224, 'N. campioni 28 N', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (224, 'N. ispezioni in cui si effettua il controllo dell''etichetta', 0, TRUE);


INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (250, 'N. campioni 28 A', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (250, 'N. campioni 28 B', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (250, 'N. campioni 28 C', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (250, 'N. campioni 28 D', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (250, 'N. campioni 28 E', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (250, 'N. campioni 28 F', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (250, 'N. campioni 28 G', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (250, 'N. campioni 28 H', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (250, 'N. campioni 28 I', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (250, 'N. campioni 28 L', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (250, 'N. campioni 28 M', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (250, 'N. campioni 28 N', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (250, 'N. ispezioni in cui si effettua il controllo dell''etichetta', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (276, 'N. campioni 28 A', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (276, 'N. campioni 28 B', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (276, 'N. campioni 28 C', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (276, 'N. campioni 28 D', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (276, 'N. campioni 28 E', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (276, 'N. campioni 28 F', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (276, 'N. campioni 28 G', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (276, 'N. campioni 28 H', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (276, 'N. campioni 28 I', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (276, 'N. campioni 28 L', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (276, 'N. campioni 28 M', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (276, 'N. campioni 28 N', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (276, 'N. ispezioni in cui si effettua il controllo dell''etichetta', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (302, 'N. campioni 28 A', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (302, 'N. campioni 28 B', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (302, 'N. campioni 28 C', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (302, 'N. campioni 28 D', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (302, 'N. campioni 28 E', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (302, 'N. campioni 28 F', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (302, 'N. campioni 28 G', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (302, 'N. campioni 28 H', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (302, 'N. campioni 28 I', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (302, 'N. campioni 28 L', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (302, 'N. campioni 28 M', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (302, 'N. campioni 28 N', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (302, 'N. ispezioni in cui si effettua il controllo dell''etichetta', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (328, 'N. campioni 28 A', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (328, 'N. campioni 28 B', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (328, 'N. campioni 28 C', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (328, 'N. campioni 28 D', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (328, 'N. campioni 28 E', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (328, 'N. campioni 28 F', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (328, 'N. campioni 28 G', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (328, 'N. campioni 28 H', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (328, 'N. campioni 28 I', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (328, 'N. campioni 28 L', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (328, 'N. campioni 28 M', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (328, 'N. campioni 28 N', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (328, 'N. ispezioni in cui si effettua il controllo dell''etichetta', 0, TRUE);



INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (225, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (251, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (277, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (303, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (329, 'N. campioni', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (226, 'N. campioni da effettuare in stabilimenti a', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (226, 'N. campioni da effettuare in stabilimenti b', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (252, 'N. campioni da effettuare in stabilimenti a', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (252, 'N. campioni da effettuare in stabilimenti b', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (278, 'N. campioni da effettuare in stabilimenti a', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (278, 'N. campioni da effettuare in stabilimenti b', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (304, 'N. campioni da effettuare in stabilimenti a', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (304, 'N. campioni da effettuare in stabilimenti b', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (330, 'N. campioni da effettuare in stabilimenti a', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (330, 'N. campioni da effettuare in stabilimenti b', 0, TRUE);

---------------------
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (227, 'N. campioni da effettuare in stabilimenti a', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (227, 'N. campioni da effettuare in stabilimenti b', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (253, 'N. campioni da effettuare in stabilimenti a', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (253, 'N. campioni da effettuare in stabilimenti b', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (279, 'N. campioni da effettuare in stabilimenti a', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (279, 'N. campioni da effettuare in stabilimenti b', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (305, 'N. campioni da effettuare in stabilimenti a', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (305, 'N. campioni da effettuare in stabilimenti b', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (331, 'N. campioni da effettuare in stabilimenti a', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (331, 'N. campioni da effettuare in stabilimenti b', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (228, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (254, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (280, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (306, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (332, 'N. campioni', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (229, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (255, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (281, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (307, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (333, 'N. campioni', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (230, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (256, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (282, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (308, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (334, 'N. campioni', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (231, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (257, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (283, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (309, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (335, 'N. campioni', 0, TRUE);

-------------------------------------
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (232, 'Piano "ad operatività ASL" - N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (232, 'Piano "ad operatività ASL" - N. ispezioni senza campionamento', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (232, 'Piano "ad operatività UVAC" - N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (232, 'Piano "ad operatività UVAC" - N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (258, 'Piano "ad operatività ASL" - N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (258, 'Piano "ad operatività ASL" - N. ispezioni senza campionamento', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (258, 'Piano "ad operatività UVAC" - N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (258, 'Piano "ad operatività UVAC" - N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (284, 'Piano "ad operatività ASL" - N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (284, 'Piano "ad operatività ASL" - N. ispezioni senza campionamento', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (284, 'Piano "ad operatività UVAC" - N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (284, 'Piano "ad operatività UVAC" - N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (310, 'Piano "ad operatività ASL" - N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (310, 'Piano "ad operatività ASL" - N. ispezioni senza campionamento', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (310, 'Piano "ad operatività UVAC" - N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (310, 'Piano "ad operatività UVAC" - N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (336, 'Piano "ad operatività ASL" - N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (336, 'Piano "ad operatività ASL" - N. ispezioni senza campionamento', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (336, 'Piano "ad operatività UVAC" - N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (336, 'Piano "ad operatività UVAC" - N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (233, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (259, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (285, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (311, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (337, 'N. ispezioni', 0, TRUE);


INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (234, 'N. campioni per metalli pesanti', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (234, 'N. campioni per nitrati', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (234, 'N. campioni per micotossine', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (260, 'N. campioni per metalli pesanti', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (260, 'N. campioni per nitrati', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (260, 'N. campioni per micotossine', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (286, 'N. campioni per metalli pesanti', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (286, 'N. campioni per nitrati', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (286, 'N. campioni per micotossine', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (312, 'N. campioni per metalli pesanti', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (312, 'N. campioni per nitrati', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (312, 'N. campioni per micotossine', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (338, 'N. campioni per metalli pesanti', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (338, 'N. campioni per nitrati', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (338, 'N. campioni per micotossine', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (235,'n. ispezioni presso rivendite all''ingrosso o dettaglio di fitofarmaci', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (235,'n. ispezioni presso aziende agricole', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (235,'n. ispezioni presso aziende florovivaistiche', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (261,'n. ispezioni presso aziende agricole', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (261,'n. ispezioni presso rivendite all''ingrosso o dettaglio di fitofarmaci', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (261,'n. ispezioni presso aziende florovivaistiche', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (287,'n. ispezioni presso aziende agricole', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (287,'n. ispezioni presso rivendite all''ingrosso o dettaglio di fitofarmaci', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (287,'n. ispezioni presso aziende florovivaistiche', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (313,'n. ispezioni presso aziende agricole', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (313,'n. ispezioni presso rivendite all''ingrosso o dettaglio di fitofarmaci', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (313,'n. ispezioni presso aziende florovivaistiche', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (339,'n. ispezioni presso aziende agricole', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (339,'n. ispezioni presso rivendite all''ingrosso o dettaglio di fitofarmaci', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (339,'n. ispezioni presso aziende florovivaistiche', 0, TRUE);

---
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (236, 'n.  campioni di latte di massa', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (236, 'n. campioni di mangime', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (236, 'n. campioni di alimenti vegetali', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (236, 'n. campioni di uova', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (236, 'n. campioni di frattaglie ovine', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (236, 'n. campioni di fegato e reni di sinantropi', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (262, 'n.  campioni di latte di massa', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (262, 'n. campioni di mangime', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (262, 'n. campioni di alimenti vegetali', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (262, 'n. campioni di uova', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (262, 'n. campioni di frattaglie ovine', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (262, 'n. campioni di fegato e reni di sinantropi', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (288, 'n.  campioni di latte di massa', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (288, 'n. campioni di mangime', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (288, 'n. campioni di alimenti vegetali', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (288, 'n. campioni di uova', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (288, 'n. campioni di frattaglie ovine', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (288, 'n. campioni di fegato e reni di sinantropi', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (314, 'n.  campioni di latte di massa', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (314, 'n. campioni di mangime', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (314, 'n. campioni di alimenti vegetali', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (314, 'n. campioni di uova', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (314, 'n. campioni di frattaglie ovine', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (314, 'n. campioni di fegato e reni di sinantropi', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (340, 'n.  campioni di latte di massa', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (340, 'n. campioni di mangime', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (340, 'n. campioni di alimenti vegetali', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (340, 'n. campioni di uova', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (340, 'n. campioni di frattaglie ovine', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (340, 'n. campioni di fegato e reni di sinantropi', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (237, 'N. AUDIT', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (263, 'N. AUDIT', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (289, 'N. AUDIT', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (315, 'N. AUDIT', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (341, 'N. AUDIT', 0, TRUE);

----------------------------------

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (238, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (264, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (290, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (316, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (342, 'N. ispezioni', 0, TRUE);



INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (239, 'N. necroscopie', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (265, 'N. necroscopie', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (291, 'N. necroscopie', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (317, 'N. necroscopie', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (343, 'N. necroscopie', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (240, 'N. necroscopie (n. 1 necroscopia = 1,5 U.I.)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (266, 'N. necroscopie (n. 1 necroscopia = 1,5 U.I.)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (292, 'N. necroscopie (n. 1 necroscopia = 1,5 U.I.)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (318, 'N. necroscopie (n. 1 necroscopia = 1,5 U.I.)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (344, 'N. necroscopie (n. 1 necroscopia = 1,5 U.I.)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (241, 'N. sterilizzazioni cani e gatti', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (267, 'N. sterilizzazioni cani e gatti', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (293, 'N. sterilizzazioni cani e gatti', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (319, 'N. sterilizzazioni cani e gatti', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (345, 'N. sterilizzazioni cani e gatti', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (242, 'N. Ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (268, 'N. Ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (294, 'N. Ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (320, 'N. Ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (346, 'N. Ispezioni', 0, TRUE);

---------------------------------------------------------sezione C-----------------------------------------------------
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ C DEL DPAR', 5, TRUE);
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ C DEL DPAR', 6, TRUE);
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ C DEL DPAR', 7, TRUE);
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ C DEL DPAR', 8, TRUE);
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ C DEL DPAR', 9, TRUE);

INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 41', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 38', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 42', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 103', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 43', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 44', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 46', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 47', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 48', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 49', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 104', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 105', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 33', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 51', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 52', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 53', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 54', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 55', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 56', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 58', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 59', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 60', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 61', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 62', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 106', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 88', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 117', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 81', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 107', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 108', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 109', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 110', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 111', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 21_bis', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 118', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'PIANO 119', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'ATTIVITA'' 10', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'ATTIVITA'' 11', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'ATTIVITA'' 12', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'ATTIVITA'' 13', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'ATTIVITA'' 14', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (17, 'ATTIVITA'' 15', TRUE);

INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 41', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 38', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 42', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 103', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 43', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 44', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 46', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 47', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 48', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 49', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 104', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 105', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 33', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 51', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 52', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 53', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 54', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 55', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 56', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 58', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 59', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 60', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 61', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 62', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 106', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 88', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 117', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 81', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 107', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 108', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 109', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 110', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 111', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 21_bis', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 118', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'PIANO 119', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'ATTIVITA'' 10', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'ATTIVITA'' 11', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'ATTIVITA'' 12', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'ATTIVITA'' 13', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'ATTIVITA'' 14', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (18, 'ATTIVITA'' 15', TRUE);


INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 41', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 38', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 42', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 103', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 43', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 44', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 46', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 47', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 48', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 49', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 104', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 105', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 33', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 51', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 52', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 53', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 54', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 55', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 56', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 58', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 59', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 60', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 61', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 62', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 106', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 88', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 117', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 81', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 107', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 108', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 109', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 110', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 111', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 21_bis', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 118', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'PIANO 119', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'ATTIVITA'' 10', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'ATTIVITA'' 11', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'ATTIVITA'' 12', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'ATTIVITA'' 13', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'ATTIVITA'' 14', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (19, 'ATTIVITA'' 15', TRUE);


INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 41', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 38', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 42', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 103', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 43', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 44', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 46', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 47', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 48', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 49', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 104', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 105', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 33', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 51', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 52', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 53', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 54', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 55', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 56', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 58', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 59', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 60', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 61', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 62', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 106', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 88', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 117', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 81', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 107', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 108', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 109', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 110', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 111', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 21_bis', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 118', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'PIANO 119', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'ATTIVITA'' 10', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'ATTIVITA'' 11', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'ATTIVITA'' 12', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'ATTIVITA'' 13', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'ATTIVITA'' 14', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (20, 'ATTIVITA'' 15', TRUE);

INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 41', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 38', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 42', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 103', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 43', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 44', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 46', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 47', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 48', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 49', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 104', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 105', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 33', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 51', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 52', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 53', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 54', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 55', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 56', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 58', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 59', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 60', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 61', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 62', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 106', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 88', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 117', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 81', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 107', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 108', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 109', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 110', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 111', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 21_bis', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 118', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'PIANO 119', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'ATTIVITA'' 10', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'ATTIVITA'' 11', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'ATTIVITA'' 12', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'ATTIVITA'' 13', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'ATTIVITA'' 14', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (21, 'ATTIVITA'' 15', TRUE);

INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (456,0,'Piano di monitoraggio sulle malattie dei pesci e dei molluschi (autorizzazioni e categorie sanitarie ex D.l.vo 148/08 e del D.D. 31/13)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (457,0,'Piano di monitoraggio idoneità materiali a contatto con gli alimenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (458,0,'Piano di monitoraggio per la ricerca dell''acrilammide negli alimenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (459,0,'Piano di monitoraggio celiachia', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (460,0,'Piano di monitoraggio comunitario dei residui di antiparassitari nei prod. alimentari di origine vegetale e animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (461,0,'Piano di monitoraggio sulla conformità degli alimenti di origine NON animale importati da paesi terzi ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (462,0,'Piano di monitoraggio scambi intracomunitari di animali vivi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (463,0,'Piano di monitoraggio per l’eradicazione e la sorveglianza della peste suina classica', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (464,0,'Piano di monitoraggio della rabbia su animali domestici morsicatori o con sintomatologia clinica ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (465,0,'Piano di Monitoraggio Nazionale Benessere Animale EXTRA PIANO', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (466,0,'Piano di monitoraggio Nazionale Alimentazione Animale EXTRAPIANO', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (467,0,'Piano di monitoraggio mangimi provenienti da Paesi Terzi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (468,0,'Piano di monitoraggio sulla radioattività dei prodotti alimentari  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (469,0,'Piano di monitoraggio sugli alimenti e loro ingredienti trattati con radiazioni ionizzanti ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (470,0,'Piano di monitoraggio per la verifica della presenza di salmonelle e listerie in stabilimenti abilitati all''esportazione in USA  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (471,0,'Piano di monitoraggio sul sistema di identificazione e registrazione degli equidi ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (472,0,'Piano di monitoraggio dei centri raccolta sperma adibiti agli scambi comunitari ed alle esportazioni', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (473,0,'Piano di monitoraggio sulle staz. di fecond. pubblica, dei centri di prod. di materiale sem., dei gruppi di racc. embrioni, dei gruppi di prod. embrioni e dei recapiti  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (474,0,'Piano di monitoraggio dei requisiti dei molluschi bivalvi vivi nelle zone di produzione, stabulazione e banchi naturali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (475,0,'Piano di monitoraggio per la verifica dei criteri microbiologici per la vendita di latte crudo in azienda e distributori automatici  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (476,0,'Piano di monitoraggio acque destinate al consumo umano ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (477,0,'Piano di monitoraggio sull''utilizzazione e commercio delle acque minerali riconosciute', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (478,0,'Piano di monitoraggio della trichinellosi negli animali domestici', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (479,0,'Piano di monitoraggio mediante test istologico (sottosezione del PNR) ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (480,0,'Piano di monitoraggio export Russia', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (481,0,'Piano di monitoraggio per la selezione genetica degli ovicaprini resistenti alla Scrapie', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (482,0,'Piano di monitoraggio delle zoonosi e degli agenti zoonotici diversi dai Piani di monitoraggio specifici', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (483,0,'Piano di monitoraggio per la verifica dei requisiti dei prodotti negli stabilimenti di trasformazione e magazzinaggio SOA', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (484,0,'Piano di monitoraggio per la verifica dei requisiti degli stabilimenti produttori di MSR', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (485,0,'Piano di monitoraggio MPCD e glicidil esteri  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (486,0,'Piano di monitoraggio tossine T-2 e HT-2 in alimenti e mangimi a base di cereali  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (487,0,'Piano di monitoraggio metalli pesanti nei pesci pescati lungo le coste campane ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (488,0,'Piano di monitoraggio diossine, pcb diossino-simili e pcb non diossino-simili  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (489,0,'Piano di Monitoraggio Farmacosorveglianza in tutti gli stabilimenti esclusi i depositi all''ingrosso (quando non altrimenti specificato, effettuazione nel quadriennio di almeno una ispezione nel 100% delle strutture esistenti  - 25% all''anno)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (490,0,'Piano di monitoraggio per la verifica della contaminazione da tessuto del sistema nervoso centrale nelle carni provenienti dalla testa dei bovini (carni di spolpo)', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (491,0,'Piano di monitoraggio sull''uso fraudolento di carni equine in preparazioni carnee e prodotti a base di carni bovine', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (492,0,'Adempimenti inerenti i sospetti avvelenamenti di animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (493,0,'Malattia di Aujeszky - Acquisizione qualifica di indenne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (494,0,'Indagini epidemiologiche per zoonosi, tossinfezioni alimentari, malattie infettive e non degli animali ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (495,0,'Attivita'' di soccorso agli animali senza padrone', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (496,0,'Ispezioni per riconoscimenti CE', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (497,0,'Identificazione elettronica dei bufalini, bovini e ovicaprini (sia nei casi previsti che non previsti dall''O.M. 9/8/12)', TRUE, TRUE);


--498-539
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (498,0,'Piano di monitoraggio sulle malattie dei pesci e dei molluschi (autorizzazioni e categorie sanitarie ex D.l.vo 148/08 e del D.D. 31/13)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (499,0,'Piano di monitoraggio idoneità materiali a contatto con gli alimenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (500,0,'Piano di monitoraggio per la ricerca dell''acrilammide negli alimenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (501,0,'Piano di monitoraggio celiachia', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (502,0,'Piano di monitoraggio comunitario dei residui di antiparassitari nei prod. alimentari di origine vegetale e animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (503,0,'Piano di monitoraggio sulla conformità degli alimenti di origine NON animale importati da paesi terzi ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (504,0,'Piano di monitoraggio scambi intracomunitari di animali vivi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (505,0,'Piano di monitoraggio per l’eradicazione e la sorveglianza della peste suina classica', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (506,0,'Piano di monitoraggio della rabbia su animali domestici morsicatori o con sintomatologia clinica ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (507,0,'Piano di Monitoraggio Nazionale Benessere Animale EXTRA PIANO', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (508,0,'Piano di monitoraggio Nazionale Alimentazione Animale EXTRAPIANO', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (509,0,'Piano di monitoraggio mangimi provenienti da Paesi Terzi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (510,0,'Piano di monitoraggio sulla radioattività dei prodotti alimentari  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (511,0,'Piano di monitoraggio sugli alimenti e loro ingredienti trattati con radiazioni ionizzanti ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (512,0,'Piano di monitoraggio per la verifica della presenza di salmonelle e listerie in stabilimenti abilitati all''esportazione in USA  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (513,0,'Piano di monitoraggio sul sistema di identificazione e registrazione degli equidi ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (514,0,'Piano di monitoraggio dei centri raccolta sperma adibiti agli scambi comunitari ed alle esportazioni', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (515,0,'Piano di monitoraggio sulle staz. di fecond. pubblica, dei centri di prod. di materiale sem., dei gruppi di racc. embrioni, dei gruppi di prod. embrioni e dei recapiti  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (516,0,'Piano di monitoraggio dei requisiti dei molluschi bivalvi vivi nelle zone di produzione, stabulazione e banchi naturali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (517,0,'Piano di monitoraggio per la verifica dei criteri microbiologici per la vendita di latte crudo in azienda e distributori automatici  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (518,0,'Piano di monitoraggio acque destinate al consumo umano ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (519,0,'Piano di monitoraggio sull''utilizzazione e commercio delle acque minerali riconosciute', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (520,0,'Piano di monitoraggio della trichinellosi negli animali domestici', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (521,0,'Piano di monitoraggio mediante test istologico (sottosezione del PNR) ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (522,0,'Piano di monitoraggio export Russia', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (523,0,'Piano di monitoraggio per la selezione genetica degli ovicaprini resistenti alla Scrapie', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (524,0,'Piano di monitoraggio delle zoonosi e degli agenti zoonotici diversi dai Piani di monitoraggio specifici', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (525,0,'Piano di monitoraggio per la verifica dei requisiti dei prodotti negli stabilimenti di trasformazione e magazzinaggio SOA', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (526,0,'Piano di monitoraggio per la verifica dei requisiti degli stabilimenti produttori di MSR', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (527,0,'Piano di monitoraggio MPCD e glicidil esteri  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (528,0,'Piano di monitoraggio tossine T-2 e HT-2 in alimenti e mangimi a base di cereali  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (529,0,'Piano di monitoraggio metalli pesanti nei pesci pescati lungo le coste campane ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (530,0,'Piano di monitoraggio diossine, pcb diossino-simili e pcb non diossino-simili  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (531,0,'Piano di Monitoraggio Farmacosorveglianza in tutti gli stabilimenti esclusi i depositi all''ingrosso (quando non altrimenti specificato, effettuazione nel quadriennio di almeno una ispezione nel 100% delle strutture esistenti  - 25% all''anno)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (532,0,'Piano di monitoraggio per la verifica della contaminazione da tessuto del sistema nervoso centrale nelle carni provenienti dalla testa dei bovini (carni di spolpo)', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (533,0,'Piano di monitoraggio sull''uso fraudolento di carni equine in preparazioni carnee e prodotti a base di carni bovine', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (534,0,'Adempimenti inerenti i sospetti avvelenamenti di animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (535,0,'Malattia di Aujeszky - Acquisizione qualifica di indenne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (536,0,'Indagini epidemiologiche per zoonosi, tossinfezioni alimentari, malattie infettive e non degli animali ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (537,0,'Attivita'' di soccorso agli animali senza padrone', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (538,0,'Ispezioni per riconoscimenti CE', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (539,0,'Identificazione elettronica dei bufalini, bovini e ovicaprini (sia nei casi previsti che non previsti dall''O.M. 9/8/12)', TRUE, TRUE);

--540-581
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (540,0,'Piano di monitoraggio sulle malattie dei pesci e dei molluschi (autorizzazioni e categorie sanitarie ex D.l.vo 148/08 e del D.D. 31/13)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (541,0,'Piano di monitoraggio idoneità materiali a contatto con gli alimenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (542,0,'Piano di monitoraggio per la ricerca dell''acrilammide negli alimenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (543,0,'Piano di monitoraggio celiachia', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (544,0,'Piano di monitoraggio comunitario dei residui di antiparassitari nei prod. alimentari di origine vegetale e animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (545,0,'Piano di monitoraggio sulla conformità degli alimenti di origine NON animale importati da paesi terzi ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (546,0,'Piano di monitoraggio scambi intracomunitari di animali vivi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (547,0,'Piano di monitoraggio per l’eradicazione e la sorveglianza della peste suina classica', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (548,0,'Piano di monitoraggio della rabbia su animali domestici morsicatori o con sintomatologia clinica ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (549,0,'Piano di Monitoraggio Nazionale Benessere Animale EXTRA PIANO', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (550,0,'Piano di monitoraggio Nazionale Alimentazione Animale EXTRAPIANO', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (551,0,'Piano di monitoraggio mangimi provenienti da Paesi Terzi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (552,0,'Piano di monitoraggio sulla radioattività dei prodotti alimentari  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (553,0,'Piano di monitoraggio sugli alimenti e loro ingredienti trattati con radiazioni ionizzanti ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (554,0,'Piano di monitoraggio per la verifica della presenza di salmonelle e listerie in stabilimenti abilitati all''esportazione in USA  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (555,0,'Piano di monitoraggio sul sistema di identificazione e registrazione degli equidi ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (556,0,'Piano di monitoraggio dei centri raccolta sperma adibiti agli scambi comunitari ed alle esportazioni', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (557,0,'Piano di monitoraggio sulle staz. di fecond. pubblica, dei centri di prod. di materiale sem., dei gruppi di racc. embrioni, dei gruppi di prod. embrioni e dei recapiti  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (558,0,'Piano di monitoraggio dei requisiti dei molluschi bivalvi vivi nelle zone di produzione, stabulazione e banchi naturali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (559,0,'Piano di monitoraggio per la verifica dei criteri microbiologici per la vendita di latte crudo in azienda e distributori automatici  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (560,0,'Piano di monitoraggio acque destinate al consumo umano ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (561,0,'Piano di monitoraggio sull''utilizzazione e commercio delle acque minerali riconosciute', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (562,0,'Piano di monitoraggio della trichinellosi negli animali domestici', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (563,0,'Piano di monitoraggio mediante test istologico (sottosezione del PNR) ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (564,0,'Piano di monitoraggio export Russia', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (565,0,'Piano di monitoraggio per la selezione genetica degli ovicaprini resistenti alla Scrapie', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (566,0,'Piano di monitoraggio delle zoonosi e degli agenti zoonotici diversi dai Piani di monitoraggio specifici', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (567,0,'Piano di monitoraggio per la verifica dei requisiti dei prodotti negli stabilimenti di trasformazione e magazzinaggio SOA', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (568,0,'Piano di monitoraggio per la verifica dei requisiti degli stabilimenti produttori di MSR', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (569,0,'Piano di monitoraggio MPCD e glicidil esteri  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (570,0,'Piano di monitoraggio tossine T-2 e HT-2 in alimenti e mangimi a base di cereali  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (571,0,'Piano di monitoraggio metalli pesanti nei pesci pescati lungo le coste campane ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (572,0,'Piano di monitoraggio diossine, pcb diossino-simili e pcb non diossino-simili  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (573,0,'Piano di Monitoraggio Farmacosorveglianza in tutti gli stabilimenti esclusi i depositi all''ingrosso (quando non altrimenti specificato, effettuazione nel quadriennio di almeno una ispezione nel 100% delle strutture esistenti  - 25% all''anno)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (574,0,'Piano di monitoraggio per la verifica della contaminazione da tessuto del sistema nervoso centrale nelle carni provenienti dalla testa dei bovini (carni di spolpo)', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (575,0,'Piano di monitoraggio sull''uso fraudolento di carni equine in preparazioni carnee e prodotti a base di carni bovine', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (576,0,'Adempimenti inerenti i sospetti avvelenamenti di animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (577,0,'Malattia di Aujeszky - Acquisizione qualifica di indenne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (578,0,'Indagini epidemiologiche per zoonosi, tossinfezioni alimentari, malattie infettive e non degli animali ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (579,0,'Attivita'' di soccorso agli animali senza padrone', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (580,0,'Ispezioni per riconoscimenti CE', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (581,0,'Identificazione elettronica dei bufalini, bovini e ovicaprini (sia nei casi previsti che non previsti dall''O.M. 9/8/12)', TRUE, TRUE);

--582-623
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (582,0,'Piano di monitoraggio sulle malattie dei pesci e dei molluschi (autorizzazioni e categorie sanitarie ex D.l.vo 148/08 e del D.D. 31/13)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (583,0,'Piano di monitoraggio idoneità materiali a contatto con gli alimenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (584,0,'Piano di monitoraggio per la ricerca dell''acrilammide negli alimenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (585,0,'Piano di monitoraggio celiachia', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (586,0,'Piano di monitoraggio comunitario dei residui di antiparassitari nei prod. alimentari di origine vegetale e animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (587,0,'Piano di monitoraggio sulla conformità degli alimenti di origine NON animale importati da paesi terzi ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (588,0,'Piano di monitoraggio scambi intracomunitari di animali vivi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (589,0,'Piano di monitoraggio per l’eradicazione e la sorveglianza della peste suina classica', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (590,0,'Piano di monitoraggio della rabbia su animali domestici morsicatori o con sintomatologia clinica ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (591,0,'Piano di Monitoraggio Nazionale Benessere Animale EXTRA PIANO', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (592,0,'Piano di monitoraggio Nazionale Alimentazione Animale EXTRAPIANO', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (593,0,'Piano di monitoraggio mangimi provenienti da Paesi Terzi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (594,0,'Piano di monitoraggio sulla radioattività dei prodotti alimentari  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (595,0,'Piano di monitoraggio sugli alimenti e loro ingredienti trattati con radiazioni ionizzanti ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (596,0,'Piano di monitoraggio per la verifica della presenza di salmonelle e listerie in stabilimenti abilitati all''esportazione in USA  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (597,0,'Piano di monitoraggio sul sistema di identificazione e registrazione degli equidi ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (598,0,'Piano di monitoraggio dei centri raccolta sperma adibiti agli scambi comunitari ed alle esportazioni', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (599,0,'Piano di monitoraggio sulle staz. di fecond. pubblica, dei centri di prod. di materiale sem., dei gruppi di racc. embrioni, dei gruppi di prod. embrioni e dei recapiti  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (600,0,'Piano di monitoraggio dei requisiti dei molluschi bivalvi vivi nelle zone di produzione, stabulazione e banchi naturali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (601,0,'Piano di monitoraggio per la verifica dei criteri microbiologici per la vendita di latte crudo in azienda e distributori automatici  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (602,0,'Piano di monitoraggio acque destinate al consumo umano ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (603,0,'Piano di monitoraggio sull''utilizzazione e commercio delle acque minerali riconosciute', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (604,0,'Piano di monitoraggio della trichinellosi negli animali domestici', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (605,0,'Piano di monitoraggio mediante test istologico (sottosezione del PNR) ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (606,0,'Piano di monitoraggio export Russia', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (607,0,'Piano di monitoraggio per la selezione genetica degli ovicaprini resistenti alla Scrapie', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (608,0,'Piano di monitoraggio delle zoonosi e degli agenti zoonotici diversi dai Piani di monitoraggio specifici', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (609,0,'Piano di monitoraggio per la verifica dei requisiti dei prodotti negli stabilimenti di trasformazione e magazzinaggio SOA', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (610,0,'Piano di monitoraggio per la verifica dei requisiti degli stabilimenti produttori di MSR', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (611,0,'Piano di monitoraggio MPCD e glicidil esteri  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (612,0,'Piano di monitoraggio tossine T-2 e HT-2 in alimenti e mangimi a base di cereali  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (613,0,'Piano di monitoraggio metalli pesanti nei pesci pescati lungo le coste campane ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (614,0,'Piano di monitoraggio diossine, pcb diossino-simili e pcb non diossino-simili  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (615,0,'Piano di Monitoraggio Farmacosorveglianza in tutti gli stabilimenti esclusi i depositi all''ingrosso (quando non altrimenti specificato, effettuazione nel quadriennio di almeno una ispezione nel 100% delle strutture esistenti  - 25% all''anno)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (616,0,'Piano di monitoraggio per la verifica della contaminazione da tessuto del sistema nervoso centrale nelle carni provenienti dalla testa dei bovini (carni di spolpo)', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (617,0,'Piano di monitoraggio sull''uso fraudolento di carni equine in preparazioni carnee e prodotti a base di carni bovine', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (618,0,'Adempimenti inerenti i sospetti avvelenamenti di animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (619,0,'Malattia di Aujeszky - Acquisizione qualifica di indenne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (620,0,'Indagini epidemiologiche per zoonosi, tossinfezioni alimentari, malattie infettive e non degli animali ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (621,0,'Attivita'' di soccorso agli animali senza padrone', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (622,0,'Ispezioni per riconoscimenti CE', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (623,0,'Identificazione elettronica dei bufalini, bovini e ovicaprini (sia nei casi previsti che non previsti dall''O.M. 9/8/12)', TRUE, TRUE);

--624-665
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (624,0,'Piano di monitoraggio sulle malattie dei pesci e dei molluschi (autorizzazioni e categorie sanitarie ex D.l.vo 148/08 e del D.D. 31/13)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (625,0,'Piano di monitoraggio idoneità materiali a contatto con gli alimenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (626,0,'Piano di monitoraggio per la ricerca dell''acrilammide negli alimenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (627,0,'Piano di monitoraggio celiachia', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (628,0,'Piano di monitoraggio comunitario dei residui di antiparassitari nei prod. alimentari di origine vegetale e animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (629,0,'Piano di monitoraggio sulla conformità degli alimenti di origine NON animale importati da paesi terzi ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (630,0,'Piano di monitoraggio scambi intracomunitari di animali vivi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (631,0,'Piano di monitoraggio per l’eradicazione e la sorveglianza della peste suina classica', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (632,0,'Piano di monitoraggio della rabbia su animali domestici morsicatori o con sintomatologia clinica ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (633,0,'Piano di Monitoraggio Nazionale Benessere Animale EXTRA PIANO', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (634,0,'Piano di monitoraggio Nazionale Alimentazione Animale EXTRAPIANO', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (635,0,'Piano di monitoraggio mangimi provenienti da Paesi Terzi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (636,0,'Piano di monitoraggio sulla radioattività dei prodotti alimentari  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (637,0,'Piano di monitoraggio sugli alimenti e loro ingredienti trattati con radiazioni ionizzanti ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (638,0,'Piano di monitoraggio per la verifica della presenza di salmonelle e listerie in stabilimenti abilitati all''esportazione in USA  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (639,0,'Piano di monitoraggio sul sistema di identificazione e registrazione degli equidi ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (640,0,'Piano di monitoraggio dei centri raccolta sperma adibiti agli scambi comunitari ed alle esportazioni', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (641,0,'Piano di monitoraggio sulle staz. di fecond. pubblica, dei centri di prod. di materiale sem., dei gruppi di racc. embrioni, dei gruppi di prod. embrioni e dei recapiti  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (642,0,'Piano di monitoraggio dei requisiti dei molluschi bivalvi vivi nelle zone di produzione, stabulazione e banchi naturali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (643,0,'Piano di monitoraggio per la verifica dei criteri microbiologici per la vendita di latte crudo in azienda e distributori automatici  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (644,0,'Piano di monitoraggio acque destinate al consumo umano ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (645,0,'Piano di monitoraggio sull''utilizzazione e commercio delle acque minerali riconosciute', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (646,0,'Piano di monitoraggio della trichinellosi negli animali domestici', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (647,0,'Piano di monitoraggio mediante test istologico (sottosezione del PNR) ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (648,0,'Piano di monitoraggio export Russia', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (649,0,'Piano di monitoraggio per la selezione genetica degli ovicaprini resistenti alla Scrapie', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (650,0,'Piano di monitoraggio delle zoonosi e degli agenti zoonotici diversi dai Piani di monitoraggio specifici', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (651,0,'Piano di monitoraggio per la verifica dei requisiti dei prodotti negli stabilimenti di trasformazione e magazzinaggio SOA', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (652,0,'Piano di monitoraggio per la verifica dei requisiti degli stabilimenti produttori di MSR', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (653,0,'Piano di monitoraggio MPCD e glicidil esteri  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (654,0,'Piano di monitoraggio tossine T-2 e HT-2 in alimenti e mangimi a base di cereali  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (655,0,'Piano di monitoraggio metalli pesanti nei pesci pescati lungo le coste campane ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (656,0,'Piano di monitoraggio diossine, pcb diossino-simili e pcb non diossino-simili  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (657,0,'Piano di Monitoraggio Farmacosorveglianza in tutti gli stabilimenti esclusi i depositi all''ingrosso (quando non altrimenti specificato, effettuazione nel quadriennio di almeno una ispezione nel 100% delle strutture esistenti  - 25% all''anno)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (658,0,'Piano di monitoraggio per la verifica della contaminazione da tessuto del sistema nervoso centrale nelle carni provenienti dalla testa dei bovini (carni di spolpo)', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (659,0,'Piano di monitoraggio sull''uso fraudolento di carni equine in preparazioni carnee e prodotti a base di carni bovine', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (660,0,'Adempimenti inerenti i sospetti avvelenamenti di animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (661,0,'Malattia di Aujeszky - Acquisizione qualifica di indenne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (662,0,'Indagini epidemiologiche per zoonosi, tossinfezioni alimentari, malattie infettive e non degli animali ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (663,0,'Attivita'' di soccorso agli animali senza padrone', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (664,0,'Ispezioni per riconoscimenti CE', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (665,0,'Identificazione elettronica dei bufalini, bovini e ovicaprini (sia nei casi previsti che non previsti dall''O.M. 9/8/12)', TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (402, 'N. allevamenti da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (444, 'N. allevamenti da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (486, 'N. allevamenti da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (528, 'N. allevamenti da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (570, 'N. allevamenti da controllare', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (403, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (445, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (487, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (529, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (571, 'N. campioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (404, 'N. campioni 1.3.1.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (404, 'N. campioni 1.3.2.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (404, 'N. campioni 1.3.3.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (404, 'N. campioni 1.3.4.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (404, 'N. campioni 1.3.5.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (404, 'N. campioni 1.3.6.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (404, 'N. campioni 1.3.7.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (404, 'N. campioni 1.3.8.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (404, 'N. campioni 1.3.9.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (404, 'N. campioni 1.3.10.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (404, 'N. ispezioni senza campionamento', 0, TRUE);


INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (446, 'N. campioni 1.3.1.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (446, 'N. campioni 1.3.2.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (446, 'N. campioni 1.3.3.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (446, 'N. campioni 1.3.4.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (446, 'N. campioni 1.3.5.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (446, 'N. campioni 1.3.6.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (446, 'N. campioni 1.3.7.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (446, 'N. campioni 1.3.8.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (446, 'N. campioni 1.3.9.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (446, 'N. campioni 1.3.10.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (446, 'N. ispezioni senza campionamento', 0, TRUE);


INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (488, 'N. campioni 1.3.1.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (488, 'N. campioni 1.3.2.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (488, 'N. campioni 1.3.3.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (488, 'N. campioni 1.3.4.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (488, 'N. campioni 1.3.5.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (488, 'N. campioni 1.3.6.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (488, 'N. campioni 1.3.7.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (488, 'N. campioni 1.3.8.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (488, 'N. campioni 1.3.9.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (488, 'N. campioni 1.3.10.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (488, 'N. ispezioni senza campionamento', 0, TRUE);


INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (530, 'N. campioni 1.3.1.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (530, 'N. campioni 1.3.2.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (530, 'N. campioni 1.3.3.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (530, 'N. campioni 1.3.4.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (530, 'N. campioni 1.3.5.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (530, 'N. campioni 1.3.6.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (530, 'N. campioni 1.3.7.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (530, 'N. campioni 1.3.8.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (530, 'N. campioni 1.3.9.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (530, 'N. campioni 1.3.10.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (530, 'N. ispezioni senza campionamento', 0, TRUE);


INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (572, 'N. campioni 1.3.1.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (572, 'N. campioni 1.3.2.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (572, 'N. campioni 1.3.3.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (572, 'N. campioni 1.3.4.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (572, 'N. campioni 1.3.5.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (572, 'N. campioni 1.3.6.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (572, 'N. campioni 1.3.7.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (572, 'N. campioni 1.3.8.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (572, 'N. campioni 1.3.9.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (572, 'N. campioni 1.3.10.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (572, 'N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (405, 'N. ispezioni.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (447, 'N. ispezioni.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (489, 'N. ispezioni.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (531, 'N. ispezioni.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (573, 'N. ispezioni.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (406, 'N. campioni di alimenti NON di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (406, 'N. campioni di alimenti di o.a.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (448, 'N. campioni di alimenti NON di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (448, 'N. campioni di alimenti di o.a.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (490, 'N. campioni di alimenti NON di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (490, 'N. campioni di alimenti di o.a.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (532, 'N. campioni di alimenti NON di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (532, 'N. campioni di alimenti di o.a.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (574, 'N. campioni di alimenti NON di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (574, 'N. campioni di alimenti di o.a.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (407, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (407, 'N. ispezioni senza campionamento (n. 1 ispezione = 1 U.I.)', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (449, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (449, 'N. ispezioni senza campionamento (n. 1 ispezione = 1 U.I.)', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (491, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (491, 'N. ispezioni senza campionamento (n. 1 ispezione = 1 U.I.)', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (533, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (533, 'N. ispezioni senza campionamento (n. 1 ispezione = 1 U.I.)', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (575, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (575, 'N. ispezioni senza campionamento (n. 1 ispezione = 1 U.I.)', 0, TRUE);


INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (408, ' Piano "ad operatività ASL" - N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (408, ' Piano "ad operatività ASL" - N. ispezioni senza campionamento', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (408, ' Piano "ad operatività UVAC" - N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (408, ' Piano "ad operatività UVAC" - N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (450, ' Piano "ad operatività ASL" - N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (450, ' Piano "ad operatività ASL" - N. ispezioni senza campionamento', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (450, ' Piano "ad operatività UVAC" - N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (450, ' Piano "ad operatività UVAC" - N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (492, ' Piano "ad operatività ASL" - N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (492, ' Piano "ad operatività ASL" - N. ispezioni senza campionamento', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (492, ' Piano "ad operatività UVAC" - N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (492, ' Piano "ad operatività UVAC" - N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (534, ' Piano "ad operatività ASL" - N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (534, ' Piano "ad operatività ASL" - N. ispezioni senza campionamento', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (534, ' Piano "ad operatività UVAC" - N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (534, ' Piano "ad operatività UVAC" - N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (576, ' Piano "ad operatività ASL" - N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (576, ' Piano "ad operatività ASL" - N. ispezioni senza campionamento', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (576, ' Piano "ad operatività UVAC" - N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (576, ' Piano "ad operatività UVAC" - N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (410, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (410, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (410, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (410, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (410, 'N. ispezioni', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (452, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (452, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (452, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (452, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (452, 'N. ispezioni', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (494, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (494, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (494, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (494, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (494, 'N. ispezioni', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (536, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (536, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (536, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (536, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (536, 'N. ispezioni', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (578, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (578, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (578, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (578, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (578, 'N. ispezioni', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (411, 'N. ispezioni in allevamenti il cui controllo era previsto dal PNBA, ma che al momento del controllo risultano non possedere più le caratteristiche richieste e pertanto non possono essere annoverati tra i controlli previsti dalla sua programmazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (411, 'N. ispezioni in allevamenti che non fanno parte della programmazione del PNBA in quanto non hanno le caratteristiche  richieste', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (453, 'N. ispezioni in allevamenti il cui controllo era previsto dal PNBA, ma che al momento del controllo risultano non possedere più le caratteristiche richieste e pertanto non possono essere annoverati tra i controlli previsti dalla sua programmazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (453, 'N. ispezioni in allevamenti che non fanno parte della programmazione del PNBA in quanto non hanno le caratteristiche  richieste', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (495, 'N. ispezioni in allevamenti il cui controllo era previsto dal PNBA, ma che al momento del controllo risultano non possedere più le caratteristiche richieste e pertanto non possono essere annoverati tra i controlli previsti dalla sua programmazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (495, 'N. ispezioni in allevamenti che non fanno parte della programmazione del PNBA in quanto non hanno le caratteristiche  richieste', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (537, 'N. ispezioni in allevamenti il cui controllo era previsto dal PNBA, ma che al momento del controllo risultano non possedere più le caratteristiche richieste e pertanto non possono essere annoverati tra i controlli previsti dalla sua programmazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (537, 'N. ispezioni in allevamenti che non fanno parte della programmazione del PNBA in quanto non hanno le caratteristiche  richieste', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (579, 'N. ispezioni in allevamenti il cui controllo era previsto dal PNBA, ma che al momento del controllo risultano non possedere più le caratteristiche richieste e pertanto non possono essere annoverati tra i controlli previsti dalla sua programmazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (579, 'N. ispezioni in allevamenti che non fanno parte della programmazione del PNBA in quanto non hanno le caratteristiche  richieste', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (412, 'N. campioni (al momento non programmati dal Ministero)', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (412, 'N. ispezioni senza campionamento (al momento non programmati dal Ministero)', 0, TRUE, FALSE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (454, 'N. campioni (al momento non programmati dal Ministero)', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (454, 'N. ispezioni senza campionamento (al momento non programmati dal Ministero)', 0, TRUE, FALSE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (496, 'N. campioni (al momento non programmati dal Ministero)', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (496, 'N. ispezioni senza campionamento (al momento non programmati dal Ministero)', 0, TRUE, FALSE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (538, 'N. campioni (al momento non programmati dal Ministero)', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (538, 'N. ispezioni senza campionamento (al momento non programmati dal Ministero)', 0, TRUE, FALSE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (580, 'N. campioni (al momento non programmati dal Ministero)', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (580, 'N. ispezioni senza campionamento (al momento non programmati dal Ministero)', 0, TRUE, FALSE);


INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (413, 'n. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (413, 'n.  ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (455, 'n. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (455, 'n.  ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (497, 'n. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (497, 'n.  ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (539, 'n. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (539, 'n.  ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (581, 'n. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (581, 'n.  ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (414, 'N. campioni di alimenti di o.a. prelevati nella fase di produzione o commercializzazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (414, 'N. campioni di alimenti di o.a. prelevati nella ristorazione collettiva', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (414, 'N. campioni di alimenti di origine NON animale prelevati nella fase di produzione o commercializzazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (414, 'N. campioni di alimenti di origine NON animale prelevati nella ristorazione collettiva', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (456, 'N. campioni di alimenti di o.a. prelevati nella fase di produzione o commercializzazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (456, 'N. campioni di alimenti di o.a. prelevati nella ristorazione collettiva', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (456, 'N. campioni di alimenti di origine NON animale prelevati nella fase di produzione o commercializzazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (456, 'N. campioni di alimenti di origine NON animale prelevati nella ristorazione collettiva', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (498, 'N. campioni di alimenti di o.a. prelevati nella fase di produzione o commercializzazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (498, 'N. campioni di alimenti di o.a. prelevati nella ristorazione collettiva', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (498, 'N. campioni di alimenti di origine NON animale prelevati nella fase di produzione o commercializzazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (498, 'N. campioni di alimenti di origine NON animale prelevati nella ristorazione collettiva', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (540, 'N. campioni di alimenti di o.a. prelevati nella fase di produzione o commercializzazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (540, 'N. campioni di alimenti di o.a. prelevati nella ristorazione collettiva', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (540, 'N. campioni di alimenti di origine NON animale prelevati nella fase di produzione o commercializzazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (540, 'N. campioni di alimenti di origine NON animale prelevati nella ristorazione collettiva', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (582, 'N. campioni di alimenti di o.a. prelevati nella fase di produzione o commercializzazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (582, 'N. campioni di alimenti di o.a. prelevati nella ristorazione collettiva', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (582, 'N. campioni di alimenti di origine NON animale prelevati nella fase di produzione o commercializzazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (582, 'N. campioni di alimenti di origine NON animale prelevati nella ristorazione collettiva', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (415, 'N. campioni di alimenti di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (415, 'N. campioni di alimenti NON di o.a.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (457, 'N. campioni di alimenti di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (457, 'N. campioni di alimenti NON di o.a.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (499, 'N. campioni di alimenti di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (499, 'N. campioni di alimenti NON di o.a.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (541, 'N. campioni di alimenti di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (541, 'N. campioni di alimenti NON di o.a.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (583, 'N. campioni di alimenti di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (583, 'N. campioni di alimenti NON di o.a.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (416, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (416, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (416, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (416, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (416, 'N. campioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (458, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (458, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (458, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (458, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (458, 'N. campioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (500, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (500, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (500, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (500, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (500, 'N. campioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (542, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (542, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (542, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (542, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (542, 'N. campioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (584, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (584, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (584, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (584, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (584, 'N. campioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (417, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (417, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (417, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (417, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (417, 'Numero aziende da controllare', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (459, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (459, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (459, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (459, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (459, 'Numero aziende da controllare', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (501, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (501, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (501, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (501, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (501, 'Numero aziende da controllare', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (543, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (543, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (543, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (543, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (543, 'Numero aziende da controllare', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (585, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (585, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (585, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (585, 'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (585, 'Numero aziende da controllare', 0, TRUE);


INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (418, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (460, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (502, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (544, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (586, 'N. ispezioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (419, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (461, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (503, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (545, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (587, 'N. ispezioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (420, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (462, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (504, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (546, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (588, 'N. campioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (421, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (463, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (505, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (547, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (589, 'N. campioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (422, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (464, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (506, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (548, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (590, 'N. campioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (423, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (465, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (507, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (549, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (591, 'N. campioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (424, 'n. controlli delle trichine in suini e cinghiali macellati per uso domestico privato (non vengono impiegate U.I. in quanto attività contestuale all''ispezione delle carni)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (424, 'n. controlli delle trichine in suidi e cavalli macellati nei macelli (non vengono impiegate U.I. in quanto attività svolte nei macelli)', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (466, 'n. controlli delle trichine in suini e cinghiali macellati per uso domestico privato (non vengono impiegate U.I. in quanto attività contestuale all''ispezione delle carni)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (466, 'n. controlli delle trichine in suidi e cavalli macellati nei macelli (non vengono impiegate U.I. in quanto attività svolte nei macelli)', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (508, 'n. controlli delle trichine in suini e cinghiali macellati per uso domestico privato (non vengono impiegate U.I. in quanto attività contestuale all''ispezione delle carni)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (508, 'n. controlli delle trichine in suidi e cavalli macellati nei macelli (non vengono impiegate U.I. in quanto attività svolte nei macelli)', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (550, 'n. controlli delle trichine in suini e cinghiali macellati per uso domestico privato (non vengono impiegate U.I. in quanto attività contestuale all''ispezione delle carni)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (550, 'n. controlli delle trichine in suidi e cavalli macellati nei macelli (non vengono impiegate U.I. in quanto attività svolte nei macelli)', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (592, 'n. controlli delle trichine in suini e cinghiali macellati per uso domestico privato (non vengono impiegate U.I. in quanto attività contestuale all''ispezione delle carni)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (592, 'n. controlli delle trichine in suidi e cavalli macellati nei macelli (non vengono impiegate U.I. in quanto attività svolte nei macelli)', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (425, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (467, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (509, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (551, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (593, 'N. campioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (426, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (468, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (510, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (552, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (594, 'N. campioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (427, 'Piano 1 Regionale - n.  Campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (427, 'Piano 2 Nazionale - n. campioni', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (469, 'Piano 1 Regionale - n.  Campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (469, 'Piano 2 Nazionale - n. campioni', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (511, 'Piano 1 Regionale - n.  Campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (511, 'Piano 2 Nazionale - n. campioni', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (553, 'Piano 1 Regionale - n.  Campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (553, 'Piano 2 Nazionale - n. campioni', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (595, 'Piano 1 Regionale - n.  Campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (595, 'Piano 2 Nazionale - n. campioni', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (428, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (428, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (428, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (428, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (428, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (470, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (470, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (470, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (470, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (470, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (512, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (512, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (512, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (512, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (512, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (554, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (554, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (554, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (554, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (554, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (596, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (596, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (596, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (596, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (596, 'Attivita'' già  espletate in altri piani ed attivita'' ', 0, TRUE, FALSE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (429, 'N.  campioni di SOA cat. 1 in impianti di trasformazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (429, 'N. campioni di PD cat. 1 in magazzini', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (429, 'N. campioni di SOA cat. 3 in impianti di trasformazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (429, 'N. campioni di PD cat. 3 in magazzini', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (471, 'N.  campioni di SOA cat. 1 in impianti di trasformazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (471, 'N. campioni di PD cat. 1 in magazzini', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (471, 'N. campioni di SOA cat. 3 in impianti di trasformazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (471, 'N. campioni di PD cat. 3 in magazzini', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (513, 'N.  campioni di SOA cat. 1 in impianti di trasformazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (513, 'N. campioni di PD cat. 1 in magazzini', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (513, 'N. campioni di SOA cat. 3 in impianti di trasformazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (513, 'N. campioni di PD cat. 3 in magazzini', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (555, 'N.  campioni di SOA cat. 1 in impianti di trasformazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (555, 'N. campioni di PD cat. 1 in magazzini', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (555, 'N. campioni di SOA cat. 3 in impianti di trasformazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (555, 'N. campioni di PD cat. 3 in magazzini', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (597, 'N.  campioni di SOA cat. 1 in impianti di trasformazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (597, 'N. campioni di PD cat. 1 in magazzini', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (597, 'N. campioni di SOA cat. 3 in impianti di trasformazione', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (597, 'N. campioni di PD cat. 3 in magazzini', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (430, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (472, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (514, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (556, 'N. ispezioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (598, 'N. ispezioni', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (431, 'n. campioni di alimenti di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (431, 'n. campioni di alimenti NON di o.a.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (473, 'n. campioni di alimenti di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (473, 'n. campioni di alimenti NON di o.a.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (515, 'n. campioni di alimenti di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (515, 'n. campioni di alimenti NON di o.a.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (557, 'n. campioni di alimenti di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (557, 'n. campioni di alimenti NON di o.a.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (599, 'n. campioni di alimenti di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (599, 'n. campioni di alimenti NON di o.a.', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (432, 'n. campioni di prodotti per mangimi e mangimi composti costituiti da cereali ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (432, 'n. campioni di alimenti costituiti da cereali o da prodotti a base di cereali', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (474, 'n. campioni di prodotti per mangimi e mangimi composti costituiti da cereali ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (474, 'n. campioni di alimenti costituiti da cereali o da prodotti a base di cereali', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (516, 'n. campioni di prodotti per mangimi e mangimi composti costituiti da cereali ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (516, 'n. campioni di alimenti costituiti da cereali o da prodotti a base di cereali', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (558, 'n. campioni di prodotti per mangimi e mangimi composti costituiti da cereali ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (558, 'n. campioni di alimenti costituiti da cereali o da prodotti a base di cereali', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (600, 'n. campioni di prodotti per mangimi e mangimi composti costituiti da cereali ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (600, 'n. campioni di alimenti costituiti da cereali o da prodotti a base di cereali', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (433, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (475, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (517, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (559, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (601, 'N. campioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (434, 'n. campioni di uova da allevamento all’aperto e uova biologiche', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (434, 'n. campioni di fegato di agnelli e pecore al macello', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (434, 'n. campioni di granchi guantati (Eriocheir sinensis)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (434, 'n. campioni di erbe aromatiche essiccate utilizzate come mangime', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (434, 'n. campioni di erbe aromatiche essiccate utilizzate come alimento umano', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (434, 'n. campioni di argille vendute come integratore alimentare umano', 0, TRUE);


INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (476, 'n. campioni di uova da allevamento all’aperto e uova biologiche', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (476, 'n. campioni di fegato di agnelli e pecore al macello', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (476, 'n. campioni di granchi guantati (Eriocheir sinensis)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (476, 'n. campioni di erbe aromatiche essiccate utilizzate come mangime', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (476, 'n. campioni di erbe aromatiche essiccate utilizzate come alimento umano', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (476, 'n. campioni di argille vendute come integratore alimentare umano', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (518, 'n. campioni di uova da allevamento all’aperto e uova biologiche', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (518, 'n. campioni di fegato di agnelli e pecore al macello', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (518, 'n. campioni di granchi guantati (Eriocheir sinensis)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (518, 'n. campioni di erbe aromatiche essiccate utilizzate come mangime', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (518, 'n. campioni di erbe aromatiche essiccate utilizzate come alimento umano', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (518, 'n. campioni di argille vendute come integratore alimentare umano', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (560, 'n. campioni di uova da allevamento all’aperto e uova biologiche', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (560, 'n. campioni di fegato di agnelli e pecore al macello', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (560, 'n. campioni di granchi guantati (Eriocheir sinensis)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (560, 'n. campioni di erbe aromatiche essiccate utilizzate come mangime', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (560, 'n. campioni di erbe aromatiche essiccate utilizzate come alimento umano', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (560, 'n. campioni di argille vendute come integratore alimentare umano', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (602, 'n. campioni di uova da allevamento all’aperto e uova biologiche', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (602, 'n. campioni di fegato di agnelli e pecore al macello', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (602, 'n. campioni di granchi guantati (Eriocheir sinensis)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (602, 'n. campioni di erbe aromatiche essiccate utilizzate come mangime', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (602, 'n. campioni di erbe aromatiche essiccate utilizzate come alimento umano', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (602, 'n. campioni di argille vendute come integratore alimentare umano', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '1. n. ispezioni negli stabilimenti che producono medicinali veterinari', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '2. n. ispezioni nei fabbricanti di premiscele vendita diretta (art 70, Dlvo 193/2006)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '3. n. ispezioni negli esercizi di vendita di medicinali veterinari ex art. 90 Dlvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '4. n. ispezioni negli ambulatori/ cliniche veterinarie', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '5. n. ispezioni per medici veterinari autorizzati a detenere scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '6. n. ispezioni negli allevamenti bovini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '7. n. ispezioni negli allevamenti bovini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '8. n. ispezioni negli allevamenti suini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '9.n. ispezioni negli allevamenti suini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '10. n. ispezioni negli allevamenti equini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '11. n. ispezioni negli allevamenti equini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '12. n. ispezioni negli allevamenti ittici autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '13. n. ispezioni negli allevamenti ittici senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '14. n. ispezioni negli allevamenti avicoli autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '15. n. ispezioni negli allevamenti avicoli senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '16. n. ispezioni negli allevamenti cunicoli autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '17. n. ispezioni negli allevamenti cunicoli senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '18. n. ispezioni negli allevamenti ovicaprini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '19. n. ispezioni negli allevamenti ovicaprini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '20. n. ispezioni negli ippodromi, maneggi, scuderie autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '21. n. ispezioni negli ippodromi, maneggi, scuderie senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '22. n. ispezioni nei canili / gattili/altre specie animali non DPA', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '23. n. ispezioni negli apiari', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '24. n. ispezioni negli allevamenti di altre specie animali destinate alla produzione di alimenti autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '25. n. ispezioni negli allevamenti di altre specie animali destinate alla produzione di alimenti senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '26. n. ispezioni nelle farmacie che effettuano vendita di medicinali veterinari ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (435, '27. n. ispezioni nelle parafarmacie che effettuano vendita di medicinali veterinari ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '1. n. ispezioni negli stabilimenti che producono medicinali veterinari', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '2. n. ispezioni nei fabbricanti di premiscele vendita diretta (art 70, Dlvo 193/2006)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '3. n. ispezioni negli esercizi di vendita di medicinali veterinari ex art. 90 Dlvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '4. n. ispezioni negli ambulatori/ cliniche veterinarie', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '5. n. ispezioni per medici veterinari autorizzati a detenere scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '6. n. ispezioni negli allevamenti bovini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '7. n. ispezioni negli allevamenti bovini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '8. n. ispezioni negli allevamenti suini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '9.n. ispezioni negli allevamenti suini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '10. n. ispezioni negli allevamenti equini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '11. n. ispezioni negli allevamenti equini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '12. n. ispezioni negli allevamenti ittici autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '13. n. ispezioni negli allevamenti ittici senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '14. n. ispezioni negli allevamenti avicoli autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '15. n. ispezioni negli allevamenti avicoli senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '16. n. ispezioni negli allevamenti cunicoli autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '17. n. ispezioni negli allevamenti cunicoli senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '18. n. ispezioni negli allevamenti ovicaprini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '19. n. ispezioni negli allevamenti ovicaprini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '20. n. ispezioni negli ippodromi, maneggi, scuderie autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '21. n. ispezioni negli ippodromi, maneggi, scuderie senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '22. n. ispezioni nei canili / gattili/altre specie animali non DPA', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '23. n. ispezioni negli apiari', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '24. n. ispezioni negli allevamenti di altre specie animali destinate alla produzione di alimenti autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '25. n. ispezioni negli allevamenti di altre specie animali destinate alla produzione di alimenti senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '26. n. ispezioni nelle farmacie che effettuano vendita di medicinali veterinari ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (477, '27. n. ispezioni nelle parafarmacie che effettuano vendita di medicinali veterinari ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '1. n. ispezioni negli stabilimenti che producono medicinali veterinari', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '2. n. ispezioni nei fabbricanti di premiscele vendita diretta (art 70, Dlvo 193/2006)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '3. n. ispezioni negli esercizi di vendita di medicinali veterinari ex art. 90 Dlvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '4. n. ispezioni negli ambulatori/ cliniche veterinarie', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '5. n. ispezioni per medici veterinari autorizzati a detenere scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '6. n. ispezioni negli allevamenti bovini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '7. n. ispezioni negli allevamenti bovini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '8. n. ispezioni negli allevamenti suini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '9.n. ispezioni negli allevamenti suini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '10. n. ispezioni negli allevamenti equini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '11. n. ispezioni negli allevamenti equini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '12. n. ispezioni negli allevamenti ittici autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '13. n. ispezioni negli allevamenti ittici senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '14. n. ispezioni negli allevamenti avicoli autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '15. n. ispezioni negli allevamenti avicoli senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '16. n. ispezioni negli allevamenti cunicoli autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '17. n. ispezioni negli allevamenti cunicoli senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '18. n. ispezioni negli allevamenti ovicaprini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '19. n. ispezioni negli allevamenti ovicaprini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '20. n. ispezioni negli ippodromi, maneggi, scuderie autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '21. n. ispezioni negli ippodromi, maneggi, scuderie senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '22. n. ispezioni nei canili / gattili/altre specie animali non DPA', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '23. n. ispezioni negli apiari', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '24. n. ispezioni negli allevamenti di altre specie animali destinate alla produzione di alimenti autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '25. n. ispezioni negli allevamenti di altre specie animali destinate alla produzione di alimenti senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '26. n. ispezioni nelle farmacie che effettuano vendita di medicinali veterinari ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (519, '27. n. ispezioni nelle parafarmacie che effettuano vendita di medicinali veterinari ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '1. n. ispezioni negli stabilimenti che producono medicinali veterinari', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '2. n. ispezioni nei fabbricanti di premiscele vendita diretta (art 70, Dlvo 193/2006)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '3. n. ispezioni negli esercizi di vendita di medicinali veterinari ex art. 90 Dlvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '4. n. ispezioni negli ambulatori/ cliniche veterinarie', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '5. n. ispezioni per medici veterinari autorizzati a detenere scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '6. n. ispezioni negli allevamenti bovini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '7. n. ispezioni negli allevamenti bovini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '8. n. ispezioni negli allevamenti suini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '9.n. ispezioni negli allevamenti suini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '10. n. ispezioni negli allevamenti equini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '11. n. ispezioni negli allevamenti equini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '12. n. ispezioni negli allevamenti ittici autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '13. n. ispezioni negli allevamenti ittici senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '14. n. ispezioni negli allevamenti avicoli autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '15. n. ispezioni negli allevamenti avicoli senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '16. n. ispezioni negli allevamenti cunicoli autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '17. n. ispezioni negli allevamenti cunicoli senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '18. n. ispezioni negli allevamenti ovicaprini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '19. n. ispezioni negli allevamenti ovicaprini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '20. n. ispezioni negli ippodromi, maneggi, scuderie autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '21. n. ispezioni negli ippodromi, maneggi, scuderie senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '22. n. ispezioni nei canili / gattili/altre specie animali non DPA', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '23. n. ispezioni negli apiari', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '24. n. ispezioni negli allevamenti di altre specie animali destinate alla produzione di alimenti autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '25. n. ispezioni negli allevamenti di altre specie animali destinate alla produzione di alimenti senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '26. n. ispezioni nelle farmacie che effettuano vendita di medicinali veterinari ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (561, '27. n. ispezioni nelle parafarmacie che effettuano vendita di medicinali veterinari ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '1. n. ispezioni negli stabilimenti che producono medicinali veterinari', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '2. n. ispezioni nei fabbricanti di premiscele vendita diretta (art 70, Dlvo 193/2006)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '3. n. ispezioni negli esercizi di vendita di medicinali veterinari ex art. 90 Dlvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '4. n. ispezioni negli ambulatori/ cliniche veterinarie', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '5. n. ispezioni per medici veterinari autorizzati a detenere scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '6. n. ispezioni negli allevamenti bovini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '7. n. ispezioni negli allevamenti bovini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '8. n. ispezioni negli allevamenti suini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '9.n. ispezioni negli allevamenti suini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '10. n. ispezioni negli allevamenti equini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '11. n. ispezioni negli allevamenti equini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '12. n. ispezioni negli allevamenti ittici autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '13. n. ispezioni negli allevamenti ittici senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '14. n. ispezioni negli allevamenti avicoli autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '15. n. ispezioni negli allevamenti avicoli senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '16. n. ispezioni negli allevamenti cunicoli autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '17. n. ispezioni negli allevamenti cunicoli senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '18. n. ispezioni negli allevamenti ovicaprini autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006 ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '19. n. ispezioni negli allevamenti ovicaprini senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '20. n. ispezioni negli ippodromi, maneggi, scuderie autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '21. n. ispezioni negli ippodromi, maneggi, scuderie senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '22. n. ispezioni nei canili / gattili/altre specie animali non DPA', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '23. n. ispezioni negli apiari', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '24. n. ispezioni negli allevamenti di altre specie animali destinate alla produzione di alimenti autorizzati alla scorta di medicinali veterinari ex art. 80 DLvo 193/2006', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '25. n. ispezioni negli allevamenti di altre specie animali destinate alla produzione di alimenti senza scorte', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '26. n. ispezioni nelle farmacie che effettuano vendita di medicinali veterinari ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (603, '27. n. ispezioni nelle parafarmacie che effettuano vendita di medicinali veterinari ', 0, TRUE);


INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (436, 'Effettuazione di n. 51 campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (478, 'Effettuazione di n. 51 campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (520, 'Effettuazione di n. 51 campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (562, 'Effettuazione di n. 51 campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (604, 'Effettuazione di n. 51 campioni', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (437, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (479, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (521, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (563, 'N. campioni', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (605, 'N. campioni', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (438, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (480, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (522, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (564, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (606, 'N. ispezioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (439, 'N. animali da controllare (presupponendo come media che vengano sottoposti 8 UBA in una U.I.) (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (481, 'N. animali da controllare (presupponendo come media che vengano sottoposti 8 UBA in una U.I.) (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (523, 'N. animali da controllare (presupponendo come media che vengano sottoposti 8 UBA in una U.I.) (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (565, 'N. animali da controllare (presupponendo come media che vengano sottoposti 8 UBA in una U.I.) (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (607, 'N. animali da controllare (presupponendo come media che vengano sottoposti 8 UBA in una U.I.) (numero presunto in base ai dati storici)', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (440, 'N. indagini epidemiologiche per zoonosi', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (440, 'N. indagini epidemiologiche per tossinfezioni alimentari', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (440, 'N. indagini epidemiologiche per malattie infettive degli animali', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (440, 'N. indagini epidemiologiche per malattie NON infettive degli animali', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (482, 'N. indagini epidemiologiche per zoonosi', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (482, 'N. indagini epidemiologiche per tossinfezioni alimentari', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (482, 'N. indagini epidemiologiche per malattie infettive degli animali', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (482, 'N. indagini epidemiologiche per malattie NON infettive degli animali', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (524, 'N. indagini epidemiologiche per zoonosi', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (524, 'N. indagini epidemiologiche per tossinfezioni alimentari', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (524, 'N. indagini epidemiologiche per malattie infettive degli animali', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (524, 'N. indagini epidemiologiche per malattie NON infettive degli animali', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (566, 'N. indagini epidemiologiche per zoonosi', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (566, 'N. indagini epidemiologiche per tossinfezioni alimentari', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (566, 'N. indagini epidemiologiche per malattie infettive degli animali', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (566, 'N. indagini epidemiologiche per malattie NON infettive degli animali', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (608, 'N. indagini epidemiologiche per zoonosi', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (608, 'N. indagini epidemiologiche per tossinfezioni alimentari', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (608, 'N. indagini epidemiologiche per malattie infettive degli animali', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (608, 'N. indagini epidemiologiche per malattie NON infettive degli animali', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (441, 'N. interventi da effettuare all''esterno delle strutture ambulatoriali (si presume che, tra personale tecnico specializzato e veterinari inseriti negli ambulatori, solo il 66% degli interventi venga effettuato da veterinari o TPAL non inseriti negli ambulatori con conseguente impiego di U.I.)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (483, 'N. interventi da effettuare all''esterno delle strutture ambulatoriali (si presume che, tra personale tecnico specializzato e veterinari inseriti negli ambulatori, solo il 66% degli interventi venga effettuato da veterinari o TPAL non inseriti negli ambulatori con conseguente impiego di U.I.)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (525, 'N. interventi da effettuare all''esterno delle strutture ambulatoriali (si presume che, tra personale tecnico specializzato e veterinari inseriti negli ambulatori, solo il 66% degli interventi venga effettuato da veterinari o TPAL non inseriti negli ambulatori con conseguente impiego di U.I.)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (567, 'N. interventi da effettuare all''esterno delle strutture ambulatoriali (si presume che, tra personale tecnico specializzato e veterinari inseriti negli ambulatori, solo il 66% degli interventi venga effettuato da veterinari o TPAL non inseriti negli ambulatori con conseguente impiego di U.I.)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (609, 'N. interventi da effettuare all''esterno delle strutture ambulatoriali (si presume che, tra personale tecnico specializzato e veterinari inseriti negli ambulatori, solo il 66% degli interventi venga effettuato da veterinari o TPAL non inseriti negli ambulatori con conseguente impiego di U.I.)', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (442, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (484, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (526, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (568, 'N. ispezioni ', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (610, 'N. ispezioni ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (443, 'Numero bovini da controllare (n. 40 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (443, 'Numero allevamenti bovini (0,3 U.I. x 2 accessi x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (443, 'Numero bufalini da controllare (n. 42 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (443, 'Numero allevamenti bufalini (0,1 U.I. x 2 accessi x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (443, 'Numero ovicaprini da controllare (n. 40 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (443, 'Numero allevamenti ovicaprini (0,2 U.I. x 2 accessi x allevamento con una media di n. 60 animali controllabili per allevamento) ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (485, 'Numero bovini da controllare (n. 40 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (485, 'Numero allevamenti bovini (0,3 U.I. x 2 accessi x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (485, 'Numero bufalini da controllare (n. 42 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (485, 'Numero allevamenti bufalini (0,1 U.I. x 2 accessi x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (485, 'Numero ovicaprini da controllare (n. 40 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (485, 'Numero allevamenti ovicaprini (0,2 U.I. x 2 accessi x allevamento con una media di n. 60 animali controllabili per allevamento) ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (527, 'Numero bovini da controllare (n. 40 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (527, 'Numero allevamenti bovini (0,3 U.I. x 2 accessi x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (527, 'Numero bufalini da controllare (n. 42 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (527, 'Numero allevamenti bufalini (0,1 U.I. x 2 accessi x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (527, 'Numero ovicaprini da controllare (n. 40 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (527, 'Numero allevamenti ovicaprini (0,2 U.I. x 2 accessi x allevamento con una media di n. 60 animali controllabili per allevamento) ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (569, 'Numero bovini da controllare (n. 40 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (569, 'Numero allevamenti bovini (0,3 U.I. x 2 accessi x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (569, 'Numero bufalini da controllare (n. 42 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (569, 'Numero allevamenti bufalini (0,1 U.I. x 2 accessi x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (569, 'Numero ovicaprini da controllare (n. 40 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (569, 'Numero allevamenti ovicaprini (0,2 U.I. x 2 accessi x allevamento con una media di n. 60 animali controllabili per allevamento) ', 0, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (611, 'Numero bovini da controllare (n. 40 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (611, 'Numero allevamenti bovini (0,3 U.I. x 2 accessi x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (611, 'Numero bufalini da controllare (n. 42 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (611, 'Numero allevamenti bufalini (0,1 U.I. x 2 accessi x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (611, 'Numero ovicaprini da controllare (n. 40 UBA x U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled) VALUES (611, 'Numero allevamenti ovicaprini (0,2 U.I. x 2 accessi x allevamento con una media di n. 60 animali controllabili per allevamento) ', 0, TRUE);
-----------------------------------------------------------sezione D----------------------------
INSERT INTO dpat_sezione(description, id_dpat, enabled) VALUES ('SEZ D DEL DPAR', 5, TRUE); 
INSERT INTO dpat_sezione(description, id_dpat, enabled) VALUES ('SEZ D DEL DPAR', 6, TRUE); 
INSERT INTO dpat_sezione(description, id_dpat, enabled) VALUES ('SEZ D DEL DPAR', 7, TRUE); 
INSERT INTO dpat_sezione(description, id_dpat, enabled) VALUES ('SEZ D DEL DPAR', 8, TRUE); 
INSERT INTO dpat_sezione(description, id_dpat, enabled) VALUES ('SEZ D DEL DPAR', 9, TRUE); 

INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 64', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 112', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 65', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 66', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 67', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 68', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 69', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 70', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 71', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 72', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 73', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 74', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 75', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 76', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 77', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 78', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 79', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 80', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 82', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 83', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 84', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 85', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 86', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 87', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 113', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 89', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 90', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 91', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 114', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 115', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 116', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 120', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'PIANO 121', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 16', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 17', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 43', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 20', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 21', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 22', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 23', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 24', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 25', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 26', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 27', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 28', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 29', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 30', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 31', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 32', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 33', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 36', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 37', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 40', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 41', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (23, 'ATTIVITA'' 42', TRUE);

INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 64', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 112', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 65', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 66', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 67', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 68', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 69', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 70', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 71', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 72', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 73', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 74', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 75', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 76', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 77', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 78', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 79', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 80', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 82', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 83', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 84', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 85', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 86', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 87', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 113', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 89', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 90', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 91', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 114', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 115', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 116', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 120', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'PIANO 121', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 16', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 17', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 43', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 20', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 21', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 22', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 23', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 24', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 25', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 26', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 27', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 28', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 29', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 30', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 31', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 32', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 33', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 36', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 37', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 40', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 41', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (24, 'ATTIVITA'' 42', TRUE);

INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 64', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 112', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 65', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 66', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 67', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 68', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 69', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 70', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 71', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 72', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 73', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 74', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 75', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 76', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 77', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 78', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 79', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 80', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 82', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 83', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 84', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 85', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 86', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 87', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 113', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 89', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 90', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 91', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 114', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 115', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 116', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 120', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'PIANO 121', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 16', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 17', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 43', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 20', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 21', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 22', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 23', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 24', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 25', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 26', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 27', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 28', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 29', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 30', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 31', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 32', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 33', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 36', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 37', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 40', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 41', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (25, 'ATTIVITA'' 42', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 64', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 112', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 65', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 66', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 67', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 68', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 69', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 70', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 71', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 72', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 73', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 74', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 75', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 76', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 77', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 78', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 79', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 80', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 82', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 83', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 84', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 85', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 86', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 87', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 113', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 89', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 90', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 91', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 114', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 115', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 116', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 120', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'PIANO 121', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 16', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 17', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 43', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 20', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 21', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 22', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 23', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 24', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 25', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 26', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 27', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 28', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 29', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 30', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 31', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 32', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 33', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 36', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 37', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 40', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 41', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (26, 'ATTIVITA'' 42', TRUE);

INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 64', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 112', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 65', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 66', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 67', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 68', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 69', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 70', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 71', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 72', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 73', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 74', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 75', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 76', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 77', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 78', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 79', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 80', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 82', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 83', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 84', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 85', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 86', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 87', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 113', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 89', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 90', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 91', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 114', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 115', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 116', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 120', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'PIANO 121', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 16', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 17', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 43', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 20', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 21', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 22', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 23', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 24', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 25', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 26', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 27', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 28', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 29', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 30', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 31', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 32', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 33', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 36', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 37', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 40', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 41', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (27, 'ATTIVITA'' 42', TRUE);

INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (677, 0, 'Piano di monitoraggio Leishmaniosi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (678, 0, 'Piano di monitoraggio trichinellosi teso al riconoscimento e mantenimento della qualifica di aziende suine esenti da trichinella ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (679, 0, 'Piano di monitoraggio sull''incidenza dell''IBR nei bovini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (680, 0, 'Piano di monitoraggio anemia infettiva equina', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (681, 0, 'Piano di monitoraggio sul quadro infettivo e parassitologico in cani ospitati nei canili', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (682, 0, 'Piano di monitoraggio sull''anagrafe dei cani padronali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (683, 0, 'Piano di monitoraggio sul benessere degli animali nei canili', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (684, 0, 'Piano di monitoraggio sugli esercizi di vendita e somministrazione a vocazione etnica', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (685, 0, 'Piano di monitoraggio sulla verifica dei parametri del latte crudo nelle aziende zootecniche', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (686, 0, 'Piano di monitoraggio per diossine e pcb diossino-simili in latte e mangimi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (687, 0, 'Piano di monitoraggio sull''illecita produzione e pesca di molluschi bivalvi.', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (688, 0, 'Piano di monitoraggio sulla presenza dell’ostreopsis ovata e delle sue tossine nei molluschi, crostacei e gasteropodi ed echinodermi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (689, 0, 'Piano di monitoraggio per la ricerca degli allergeni negli alimenti di origine animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (690, 0, 'Piano di monitoraggio sui punti di sbarco', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (691, 0, 'Piano di monitoraggio sulla presenza di istamina nelle conserve e semiconserve di prodotti ittici', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (692, 0, 'Piano di monitoraggio sugli additivi, i coloranti e gli aromi.', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (693, 0, 'Piano di monitoraggio tracciabilita'' selvaggina cacciata', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (694, 0, 'Piano di monitoraggio sulle acque potabili sia da approvvigionamento autonomo sia di rete utilizzate negli stabilimenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (695, 0, 'Piano di monitoraggio  degli inconvenienti igienico-sanitari degli animali sinantropi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (696, 0, 'Piano di monitoraggio stato sanitario delle colonie feline', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (697, 0, 'Piano di monitoraggio per la verifica della quantità di nitriti presenti nei prodotti a base di carne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (698, 0, 'Piano di monitoraggio per la verifica di aflatossina nei mangimi, latte e prodotti a base di latte', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (699, 0, 'Piano di monitoraggio per la verifica della contaminazione superficiale delle carcasse animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (700, 0, 'Piano di monitoraggio prodotti a base di latte', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (701, 0, 'Piano di monitoraggio sulle modalità della vendita ambulante di prodotti alimentari', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (702, 0, 'Piano di monitoraggio funghi ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (703, 0, 'Piano di monitoraggio sulle cause di mortalità degli ovicaprini negli allevamenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (704, 0, 'Piano di monitoraggio sulle malattie delle api', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (705, 0, 'Piano di monitoraggio benessere e corretta movimentazione animali sportivi  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (706, 0, 'Piano di monitoraggio sulle modalità di trasporto di alimenti, SOA e mangimi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (707, 0, 'Piano di monitoraggio paratubercolosi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (708, 0, 'Piano di monitoraggio del mantenimento dei requisiti dei laboratori che effettuano analisi nell''ambito dell''autocontrollo delle imprese alimentari', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (709, 0, 'Piano di monitoraggio sul commercio elettronico di alimenti, animali vivi e mangimi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (710, 0, 'Audit su tutti i tipi di stabilimenti/aziende (ad esclusione di quelli già contemplati nell''Attività 1 e 5)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (711, 0, 'Audit interni effettuati dalle AA.SS.LL. ai sensi dell''art. 4 punto 6 del Reg CE 882/04 (gli audit interni sono solo a carico dei Servizi Dipartimentali. Se un auditor svolge la propria normale attività in una U.O., egli deve essere inquadrato in percentuale sia nella U.O.C. che nella U.O.; per la U.O.C. espleterà gli audit interni, per la U.O. il normale lavoro. Alle U.O.territoriali devono essere annerite le celle corrispondenti agli audit interni. Un audit interno può essere svolto sia per l''882 sia per la ISO 9001:2008 avendo cura di differenziare le due procedure)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (712, 0, 'Audit interni effettuati dalle AA.SS.LL. ai sensi della norma ISO 9001:2008 (gli audit interni sono solo a carico dei Servizi Dipartimentali. Se un auditor svolge la propria normale attività in una U.O., egli deve essere inquadrato in percentuale sia nella U.O.C. che nella U.O.; per la U.O.C. espleterà gli audit interni, per la U.O. il normale lavoro. Alle U.O.territoriali devono essere annerite le celle corrispondenti agli audit interni. Un audit interno può essere svolto sia per l''882 sia per la 19011 avendo cura di differenziare le due procedure)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (713, 0, 'Ispezioni effettuate in base alla categoria di rischio', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (714, 0, 'Ispezioni per attività a favore di imprese o privati (ad es. certificati esportazione, sopralluoghi preventivi, etc)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (715, 0, 'Attività di macellazione di suini a domicilio', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (716, 0, 'Attività di macellazione d''urgenza', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (717, 0, 'Iscrizione gatti in BDR', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (718, 0, 'Controllo dei programmi di AAT/PTT ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (719, 0, 'Ispezioni effettuate per spostamento e/o compravendite animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (720, 0, 'Ispezioni effettuate per il controllo dei focolai di malattie infettive degli animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (721, 0, 'Ispezioni effettuate su richiesta forze dell''ordine e altre autorità ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (722, 0, 'Ispezioni effettuate a seguito di campioni/tamponi non conformi  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (723, 0, 'Ispezioni effettuate per dissequestri/distruzioni   ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (724, 0, 'Ispezioni effettuate per svincoli sanitari', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (725, 0, 'Controllo SCIA', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (726, 0, 'Ispezioni per reclami/segnalazioni', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (727, 0, 'Sospetto di presenza n.c.  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (728, 0, 'Programmi di educazione alla salute', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (729, 0, 'Identificazione, registrazione e destino delle carcasse di cani e gatti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (730, 0, 'Anagrafe attiva itinerante dei cani', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (731, 0, 'Accertamenti sulle cause di mortalità degli animali negli allevamenti (esclusi gli ovicaprini già inseriti nel Piano 90)', TRUE, TRUE);

INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (732, 0, 'Piano di monitoraggio Leishmaniosi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (733, 0, 'Piano di monitoraggio trichinellosi teso al riconoscimento e mantenimento della qualifica di aziende suine esenti da trichinella ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (734, 0, 'Piano di monitoraggio sull''incidenza dell''IBR nei bovini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (735, 0, 'Piano di monitoraggio anemia infettiva equina', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (736, 0, 'Piano di monitoraggio sul quadro infettivo e parassitologico in cani ospitati nei canili', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (737, 0, 'Piano di monitoraggio sull''anagrafe dei cani padronali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (738, 0, 'Piano di monitoraggio sul benessere degli animali nei canili', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (739, 0, 'Piano di monitoraggio sugli esercizi di vendita e somministrazione a vocazione etnica', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (740, 0, 'Piano di monitoraggio sulla verifica dei parametri del latte crudo nelle aziende zootecniche', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (741, 0, 'Piano di monitoraggio per diossine e pcb diossino-simili in latte e mangimi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (742, 0, 'Piano di monitoraggio sull''illecita produzione e pesca di molluschi bivalvi.', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (743, 0, 'Piano di monitoraggio sulla presenza dell’ostreopsis ovata e delle sue tossine nei molluschi, crostacei e gasteropodi ed echinodermi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (744, 0, 'Piano di monitoraggio per la ricerca degli allergeni negli alimenti di origine animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (745, 0, 'Piano di monitoraggio sui punti di sbarco', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (746, 0, 'Piano di monitoraggio sulla presenza di istamina nelle conserve e semiconserve di prodotti ittici', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (747, 0, 'Piano di monitoraggio sugli additivi, i coloranti e gli aromi.', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (748, 0, 'Piano di monitoraggio tracciabilita'' selvaggina cacciata', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (749, 0, 'Piano di monitoraggio sulle acque potabili sia da approvvigionamento autonomo sia di rete utilizzate negli stabilimenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (750, 0, 'Piano di monitoraggio  degli inconvenienti igienico-sanitari degli animali sinantropi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (751, 0, 'Piano di monitoraggio stato sanitario delle colonie feline', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (752, 0, 'Piano di monitoraggio per la verifica della quantità di nitriti presenti nei prodotti a base di carne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (753, 0, 'Piano di monitoraggio per la verifica di aflatossina nei mangimi, latte e prodotti a base di latte', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (754, 0, 'Piano di monitoraggio per la verifica della contaminazione superficiale delle carcasse animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (755, 0, 'Piano di monitoraggio prodotti a base di latte', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (756, 0, 'Piano di monitoraggio sulle modalità della vendita ambulante di prodotti alimentari', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (757, 0, 'Piano di monitoraggio funghi ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (758, 0, 'Piano di monitoraggio sulle cause di mortalità degli ovicaprini negli allevamenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (759, 0, 'Piano di monitoraggio sulle malattie delle api', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (760, 0, 'Piano di monitoraggio benessere e corretta movimentazione animali sportivi  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (761, 0, 'Piano di monitoraggio sulle modalità di trasporto di alimenti, SOA e mangimi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (762, 0, 'Piano di monitoraggio paratubercolosi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (763, 0, 'Piano di monitoraggio del mantenimento dei requisiti dei laboratori che effettuano analisi nell''ambito dell''autocontrollo delle imprese alimentari', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (764, 0, 'Piano di monitoraggio sul commercio elettronico di alimenti, animali vivi e mangimi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (765, 0, 'Audit su tutti i tipi di stabilimenti/aziende (ad esclusione di quelli già contemplati nell''Attività 1 e 5)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (766, 0, 'Audit interni effettuati dalle AA.SS.LL. ai sensi dell''art. 4 punto 6 del Reg CE 882/04 (gli audit interni sono solo a carico dei Servizi Dipartimentali. Se un auditor svolge la propria normale attività in una U.O., egli deve essere inquadrato in percentuale sia nella U.O.C. che nella U.O.; per la U.O.C. espleterà gli audit interni, per la U.O. il normale lavoro. Alle U.O.territoriali devono essere annerite le celle corrispondenti agli audit interni. Un audit interno può essere svolto sia per l''882 sia per la ISO 9001:2008 avendo cura di differenziare le due procedure)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (767, 0, 'Audit interni effettuati dalle AA.SS.LL. ai sensi della norma ISO 9001:2008 (gli audit interni sono solo a carico dei Servizi Dipartimentali. Se un auditor svolge la propria normale attività in una U.O., egli deve essere inquadrato in percentuale sia nella U.O.C. che nella U.O.; per la U.O.C. espleterà gli audit interni, per la U.O. il normale lavoro. Alle U.O.territoriali devono essere annerite le celle corrispondenti agli audit interni. Un audit interno può essere svolto sia per l''882 sia per la 19011 avendo cura di differenziare le due procedure)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (768, 0, 'Ispezioni effettuate in base alla categoria di rischio', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (769, 0, 'Ispezioni per attività a favore di imprese o privati (ad es. certificati esportazione, sopralluoghi preventivi, etc)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (770, 0, 'Attività di macellazione di suini a domicilio', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (771, 0, 'Attività di macellazione d''urgenza', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (772, 0, 'Iscrizione gatti in BDR', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (773, 0, 'Controllo dei programmi di AAT/PTT ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (774, 0, 'Ispezioni effettuate per spostamento e/o compravendite animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (775, 0, 'Ispezioni effettuate per il controllo dei focolai di malattie infettive degli animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (776, 0, 'Ispezioni effettuate su richiesta forze dell''ordine e altre autorità ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (777, 0, 'Ispezioni effettuate a seguito di campioni/tamponi non conformi  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (778, 0, 'Ispezioni effettuate per dissequestri/distruzioni   ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (779, 0, 'Ispezioni effettuate per svincoli sanitari', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (780, 0, 'Controllo SCIA', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (781, 0, 'Ispezioni per reclami/segnalazioni', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (782, 0, 'Sospetto di presenza n.c.  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (783, 0, 'Programmi di educazione alla salute', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (784, 0, 'Identificazione, registrazione e destino delle carcasse di cani e gatti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (785, 0, 'Anagrafe attiva itinerante dei cani', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (786, 0, 'Accertamenti sulle cause di mortalità degli animali negli allevamenti (esclusi gli ovicaprini già inseriti nel Piano 90)', TRUE, TRUE);

INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (787, 0, 'Piano di monitoraggio Leishmaniosi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (788, 0, 'Piano di monitoraggio trichinellosi teso al riconoscimento e mantenimento della qualifica di aziende suine esenti da trichinella ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (789, 0, 'Piano di monitoraggio sull''incidenza dell''IBR nei bovini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (790, 0, 'Piano di monitoraggio anemia infettiva equina', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (791, 0, 'Piano di monitoraggio sul quadro infettivo e parassitologico in cani ospitati nei canili', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (792, 0, 'Piano di monitoraggio sull''anagrafe dei cani padronali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (793, 0, 'Piano di monitoraggio sul benessere degli animali nei canili', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (794, 0, 'Piano di monitoraggio sugli esercizi di vendita e somministrazione a vocazione etnica', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (795, 0, 'Piano di monitoraggio sulla verifica dei parametri del latte crudo nelle aziende zootecniche', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (796, 0, 'Piano di monitoraggio per diossine e pcb diossino-simili in latte e mangimi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (797, 0, 'Piano di monitoraggio sull''illecita produzione e pesca di molluschi bivalvi.', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (798, 0, 'Piano di monitoraggio sulla presenza dell’ostreopsis ovata e delle sue tossine nei molluschi, crostacei e gasteropodi ed echinodermi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (799, 0, 'Piano di monitoraggio per la ricerca degli allergeni negli alimenti di origine animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (800, 0, 'Piano di monitoraggio sui punti di sbarco', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (801, 0, 'Piano di monitoraggio sulla presenza di istamina nelle conserve e semiconserve di prodotti ittici', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (802, 0, 'Piano di monitoraggio sugli additivi, i coloranti e gli aromi.', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (803, 0, 'Piano di monitoraggio tracciabilita'' selvaggina cacciata', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (804, 0, 'Piano di monitoraggio sulle acque potabili sia da approvvigionamento autonomo sia di rete utilizzate negli stabilimenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (805, 0, 'Piano di monitoraggio  degli inconvenienti igienico-sanitari degli animali sinantropi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (806, 0, 'Piano di monitoraggio stato sanitario delle colonie feline', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (807, 0, 'Piano di monitoraggio per la verifica della quantità di nitriti presenti nei prodotti a base di carne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (808, 0, 'Piano di monitoraggio per la verifica di aflatossina nei mangimi, latte e prodotti a base di latte', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (809, 0, 'Piano di monitoraggio per la verifica della contaminazione superficiale delle carcasse animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (810, 0, 'Piano di monitoraggio prodotti a base di latte', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (811, 0, 'Piano di monitoraggio sulle modalità della vendita ambulante di prodotti alimentari', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (812, 0, 'Piano di monitoraggio funghi ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (813, 0, 'Piano di monitoraggio sulle cause di mortalità degli ovicaprini negli allevamenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (814, 0, 'Piano di monitoraggio sulle malattie delle api', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (815, 0, 'Piano di monitoraggio benessere e corretta movimentazione animali sportivi  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (816, 0, 'Piano di monitoraggio sulle modalità di trasporto di alimenti, SOA e mangimi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (817, 0, 'Piano di monitoraggio paratubercolosi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (818, 0, 'Piano di monitoraggio del mantenimento dei requisiti dei laboratori che effettuano analisi nell''ambito dell''autocontrollo delle imprese alimentari', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (819, 0, 'Piano di monitoraggio sul commercio elettronico di alimenti, animali vivi e mangimi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (821, 0, 'Audit su tutti i tipi di stabilimenti/aziende (ad esclusione di quelli già contemplati nell''Attività 1 e 5)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (822, 0, 'Audit interni effettuati dalle AA.SS.LL. ai sensi dell''art. 4 punto 6 del Reg CE 882/04 (gli audit interni sono solo a carico dei Servizi Dipartimentali. Se un auditor svolge la propria normale attività in una U.O., egli deve essere inquadrato in percentuale sia nella U.O.C. che nella U.O.; per la U.O.C. espleterà gli audit interni, per la U.O. il normale lavoro. Alle U.O.territoriali devono essere annerite le celle corrispondenti agli audit interni. Un audit interno può essere svolto sia per l''882 sia per la ISO 9001:2008 avendo cura di differenziare le due procedure)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (823, 0, 'Audit interni effettuati dalle AA.SS.LL. ai sensi della norma ISO 9001:2008 (gli audit interni sono solo a carico dei Servizi Dipartimentali. Se un auditor svolge la propria normale attività in una U.O., egli deve essere inquadrato in percentuale sia nella U.O.C. che nella U.O.; per la U.O.C. espleterà gli audit interni, per la U.O. il normale lavoro. Alle U.O.territoriali devono essere annerite le celle corrispondenti agli audit interni. Un audit interno può essere svolto sia per l''882 sia per la 19011 avendo cura di differenziare le due procedure)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (824, 0, 'Ispezioni effettuate in base alla categoria di rischio', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (825, 0, 'Ispezioni per attività a favore di imprese o privati (ad es. certificati esportazione, sopralluoghi preventivi, etc)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (826, 0, 'Attività di macellazione di suini a domicilio', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (826, 0, 'Attività di macellazione d''urgenza', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (827, 0, 'Iscrizione gatti in BDR', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (828, 0, 'Controllo dei programmi di AAT/PTT ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (829, 0, 'Ispezioni effettuate per spostamento e/o compravendite animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (830, 0, 'Ispezioni effettuate per il controllo dei focolai di malattie infettive degli animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (831, 0, 'Ispezioni effettuate su richiesta forze dell''ordine e altre autorità ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (832, 0, 'Ispezioni effettuate a seguito di campioni/tamponi non conformi  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (833, 0, 'Ispezioni effettuate per dissequestri/distruzioni   ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (834, 0, 'Ispezioni effettuate per svincoli sanitari', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (835, 0, 'Controllo SCIA', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (836, 0, 'Ispezioni per reclami/segnalazioni', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (837, 0, 'Sospetto di presenza n.c.  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (838, 0, 'Programmi di educazione alla salute', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (839, 0, 'Identificazione, registrazione e destino delle carcasse di cani e gatti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (840, 0, 'Anagrafe attiva itinerante dei cani', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (841, 0, 'Accertamenti sulle cause di mortalità degli animali negli allevamenti (esclusi gli ovicaprini già inseriti nel Piano 90)', TRUE, TRUE);

INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (842, 0, 'Piano di monitoraggio Leishmaniosi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (843, 0, 'Piano di monitoraggio trichinellosi teso al riconoscimento e mantenimento della qualifica di aziende suine esenti da trichinella ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (844, 0, 'Piano di monitoraggio sull''incidenza dell''IBR nei bovini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (845, 0, 'Piano di monitoraggio anemia infettiva equina', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (846, 0, 'Piano di monitoraggio sul quadro infettivo e parassitologico in cani ospitati nei canili', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (847, 0, 'Piano di monitoraggio sull''anagrafe dei cani padronali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (848, 0, 'Piano di monitoraggio sul benessere degli animali nei canili', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (849, 0, 'Piano di monitoraggio sugli esercizi di vendita e somministrazione a vocazione etnica', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (850, 0, 'Piano di monitoraggio sulla verifica dei parametri del latte crudo nelle aziende zootecniche', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (851, 0, 'Piano di monitoraggio per diossine e pcb diossino-simili in latte e mangimi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (852, 0, 'Piano di monitoraggio sull''illecita produzione e pesca di molluschi bivalvi.', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (853, 0, 'Piano di monitoraggio sulla presenza dell’ostreopsis ovata e delle sue tossine nei molluschi, crostacei e gasteropodi ed echinodermi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (854, 0, 'Piano di monitoraggio per la ricerca degli allergeni negli alimenti di origine animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (855, 0, 'Piano di monitoraggio sui punti di sbarco', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (856, 0, 'Piano di monitoraggio sulla presenza di istamina nelle conserve e semiconserve di prodotti ittici', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (857, 0, 'Piano di monitoraggio sugli additivi, i coloranti e gli aromi.', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (858, 0, 'Piano di monitoraggio tracciabilita'' selvaggina cacciata', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (859, 0, 'Piano di monitoraggio sulle acque potabili sia da approvvigionamento autonomo sia di rete utilizzate negli stabilimenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (860, 0, 'Piano di monitoraggio  degli inconvenienti igienico-sanitari degli animali sinantropi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (861, 0, 'Piano di monitoraggio stato sanitario delle colonie feline', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (862, 0, 'Piano di monitoraggio per la verifica della quantità di nitriti presenti nei prodotti a base di carne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (863, 0, 'Piano di monitoraggio per la verifica di aflatossina nei mangimi, latte e prodotti a base di latte', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (864, 0, 'Piano di monitoraggio per la verifica della contaminazione superficiale delle carcasse animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (865, 0, 'Piano di monitoraggio prodotti a base di latte', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (866, 0, 'Piano di monitoraggio sulle modalità della vendita ambulante di prodotti alimentari', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (867, 0, 'Piano di monitoraggio funghi ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (868, 0, 'Piano di monitoraggio sulle cause di mortalità degli ovicaprini negli allevamenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (869, 0, 'Piano di monitoraggio sulle malattie delle api', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (870, 0, 'Piano di monitoraggio benessere e corretta movimentazione animali sportivi  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (871, 0, 'Piano di monitoraggio sulle modalità di trasporto di alimenti, SOA e mangimi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (872, 0, 'Piano di monitoraggio paratubercolosi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (873, 0, 'Piano di monitoraggio del mantenimento dei requisiti dei laboratori che effettuano analisi nell''ambito dell''autocontrollo delle imprese alimentari', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (874, 0, 'Piano di monitoraggio sul commercio elettronico di alimenti, animali vivi e mangimi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (875, 0, 'Audit su tutti i tipi di stabilimenti/aziende (ad esclusione di quelli già contemplati nell''Attività 1 e 5)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (876, 0, 'Audit interni effettuati dalle AA.SS.LL. ai sensi dell''art. 4 punto 6 del Reg CE 882/04 (gli audit interni sono solo a carico dei Servizi Dipartimentali. Se un auditor svolge la propria normale attività in una U.O., egli deve essere inquadrato in percentuale sia nella U.O.C. che nella U.O.; per la U.O.C. espleterà gli audit interni, per la U.O. il normale lavoro. Alle U.O.territoriali devono essere annerite le celle corrispondenti agli audit interni. Un audit interno può essere svolto sia per l''882 sia per la ISO 9001:2008 avendo cura di differenziare le due procedure)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (877, 0, 'Audit interni effettuati dalle AA.SS.LL. ai sensi della norma ISO 9001:2008 (gli audit interni sono solo a carico dei Servizi Dipartimentali. Se un auditor svolge la propria normale attività in una U.O., egli deve essere inquadrato in percentuale sia nella U.O.C. che nella U.O.; per la U.O.C. espleterà gli audit interni, per la U.O. il normale lavoro. Alle U.O.territoriali devono essere annerite le celle corrispondenti agli audit interni. Un audit interno può essere svolto sia per l''882 sia per la 19011 avendo cura di differenziare le due procedure)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (878, 0, 'Ispezioni effettuate in base alla categoria di rischio', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (879, 0, 'Ispezioni per attività a favore di imprese o privati (ad es. certificati esportazione, sopralluoghi preventivi, etc)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (880, 0, 'Attività di macellazione di suini a domicilio', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (881, 0, 'Attività di macellazione d''urgenza', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (882, 0, 'Iscrizione gatti in BDR', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (883, 0, 'Controllo dei programmi di AAT/PTT ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (884, 0, 'Ispezioni effettuate per spostamento e/o compravendite animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (885, 0, 'Ispezioni effettuate per il controllo dei focolai di malattie infettive degli animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (886, 0, 'Ispezioni effettuate su richiesta forze dell''ordine e altre autorità ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (887, 0, 'Ispezioni effettuate a seguito di campioni/tamponi non conformi  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (888, 0, 'Ispezioni effettuate per dissequestri/distruzioni   ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (889, 0, 'Ispezioni effettuate per svincoli sanitari', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (890, 0, 'Controllo SCIA', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (891, 0, 'Ispezioni per reclami/segnalazioni', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (892, 0, 'Sospetto di presenza n.c.  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (893, 0, 'Programmi di educazione alla salute', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (894, 0, 'Identificazione, registrazione e destino delle carcasse di cani e gatti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (895, 0, 'Anagrafe attiva itinerante dei cani', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (896, 0, 'Accertamenti sulle cause di mortalità degli animali negli allevamenti (esclusi gli ovicaprini già inseriti nel Piano 90)', TRUE, TRUE);

INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (897, 0, 'Piano di monitoraggio Leishmaniosi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (898, 0, 'Piano di monitoraggio trichinellosi teso al riconoscimento e mantenimento della qualifica di aziende suine esenti da trichinella ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (899, 0, 'Piano di monitoraggio sull''incidenza dell''IBR nei bovini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (900, 0, 'Piano di monitoraggio anemia infettiva equina', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (901, 0, 'Piano di monitoraggio sul quadro infettivo e parassitologico in cani ospitati nei canili', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (902, 0, 'Piano di monitoraggio sull''anagrafe dei cani padronali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (903, 0, 'Piano di monitoraggio sul benessere degli animali nei canili', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (904, 0, 'Piano di monitoraggio sugli esercizi di vendita e somministrazione a vocazione etnica', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (905, 0, 'Piano di monitoraggio sulla verifica dei parametri del latte crudo nelle aziende zootecniche', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (906, 0, 'Piano di monitoraggio per diossine e pcb diossino-simili in latte e mangimi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (907, 0, 'Piano di monitoraggio sull''illecita produzione e pesca di molluschi bivalvi.', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (908, 0, 'Piano di monitoraggio sulla presenza dell’ostreopsis ovata e delle sue tossine nei molluschi, crostacei e gasteropodi ed echinodermi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (909, 0, 'Piano di monitoraggio per la ricerca degli allergeni negli alimenti di origine animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (910, 0, 'Piano di monitoraggio sui punti di sbarco', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (911, 0, 'Piano di monitoraggio sulla presenza di istamina nelle conserve e semiconserve di prodotti ittici', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (912, 0, 'Piano di monitoraggio sugli additivi, i coloranti e gli aromi.', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (913, 0, 'Piano di monitoraggio tracciabilita'' selvaggina cacciata', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (914, 0, 'Piano di monitoraggio sulle acque potabili sia da approvvigionamento autonomo sia di rete utilizzate negli stabilimenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (915, 0, 'Piano di monitoraggio  degli inconvenienti igienico-sanitari degli animali sinantropi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (916, 0, 'Piano di monitoraggio stato sanitario delle colonie feline', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (917, 0, 'Piano di monitoraggio per la verifica della quantità di nitriti presenti nei prodotti a base di carne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (918, 0, 'Piano di monitoraggio per la verifica di aflatossina nei mangimi, latte e prodotti a base di latte', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (919, 0, 'Piano di monitoraggio per la verifica della contaminazione superficiale delle carcasse animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (920, 0, 'Piano di monitoraggio prodotti a base di latte', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (921, 0, 'Piano di monitoraggio sulle modalità della vendita ambulante di prodotti alimentari', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (922, 0, 'Piano di monitoraggio funghi ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (923, 0, 'Piano di monitoraggio sulle cause di mortalità degli ovicaprini negli allevamenti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (924, 0, 'Piano di monitoraggio sulle malattie delle api', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (925, 0, 'Piano di monitoraggio benessere e corretta movimentazione animali sportivi  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (926, 0, 'Piano di monitoraggio sulle modalità di trasporto di alimenti, SOA e mangimi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (927, 0, 'Piano di monitoraggio paratubercolosi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (928, 0, 'Piano di monitoraggio del mantenimento dei requisiti dei laboratori che effettuano analisi nell''ambito dell''autocontrollo delle imprese alimentari', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (929, 0, 'Piano di monitoraggio sul commercio elettronico di alimenti, animali vivi e mangimi', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (930, 0, 'Audit su tutti i tipi di stabilimenti/aziende (ad esclusione di quelli già contemplati nell''Attività 1 e 5)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (931, 0, 'Audit interni effettuati dalle AA.SS.LL. ai sensi dell''art. 4 punto 6 del Reg CE 882/04 (gli audit interni sono solo a carico dei Servizi Dipartimentali. Se un auditor svolge la propria normale attività in una U.O., egli deve essere inquadrato in percentuale sia nella U.O.C. che nella U.O.; per la U.O.C. espleterà gli audit interni, per la U.O. il normale lavoro. Alle U.O.territoriali devono essere annerite le celle corrispondenti agli audit interni. Un audit interno può essere svolto sia per l''882 sia per la ISO 9001:2008 avendo cura di differenziare le due procedure)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (932, 0, 'Audit interni effettuati dalle AA.SS.LL. ai sensi della norma ISO 9001:2008 (gli audit interni sono solo a carico dei Servizi Dipartimentali. Se un auditor svolge la propria normale attività in una U.O., egli deve essere inquadrato in percentuale sia nella U.O.C. che nella U.O.; per la U.O.C. espleterà gli audit interni, per la U.O. il normale lavoro. Alle U.O.territoriali devono essere annerite le celle corrispondenti agli audit interni. Un audit interno può essere svolto sia per l''882 sia per la 19011 avendo cura di differenziare le due procedure)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (933, 0, 'Ispezioni effettuate in base alla categoria di rischio', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (934, 0, 'Ispezioni per attività a favore di imprese o privati (ad es. certificati esportazione, sopralluoghi preventivi, etc)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (935, 0, 'Attività di macellazione di suini a domicilio', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (936, 0, 'Attività di macellazione d''urgenza', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (937, 0, 'Iscrizione gatti in BDR', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (938, 0, 'Controllo dei programmi di AAT/PTT ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (939, 0, 'Ispezioni effettuate per spostamento e/o compravendite animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (940, 0, 'Ispezioni effettuate per il controllo dei focolai di malattie infettive degli animali', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (941, 0, 'Ispezioni effettuate su richiesta forze dell''ordine e altre autorità ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (942, 0, 'Ispezioni effettuate a seguito di campioni/tamponi non conformi  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (943, 0, 'Ispezioni effettuate per dissequestri/distruzioni   ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (944, 0, 'Ispezioni effettuate per svincoli sanitari', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (945, 0, 'Controllo SCIA', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (946, 0, 'Ispezioni per reclami/segnalazioni', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (947, 0, 'Sospetto di presenza n.c.  ', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (948, 0, 'Programmi di educazione alla salute', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (949, 0, 'Identificazione, registrazione e destino delle carcasse di cani e gatti', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (950, 0, 'Anagrafe attiva itinerante dei cani', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (951, 0, 'Accertamenti sulle cause di mortalità degli animali negli allevamenti (esclusi gli ovicaprini già inseriti nel Piano 90)', TRUE, TRUE);



INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (614, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (614, 'N. ispezioni in canili', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (614, 'N. prelievi effettuati in ambulatorio (non vengono impiegate U.I. in quanto le attività sono effettuate negli ambulatori)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (669, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (669, 'N. ispezioni in canili', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (669, 'N. prelievi effettuati in ambulatorio (non vengono impiegate U.I. in quanto le attività sono effettuate negli ambulatori)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (724, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (724, 'N. ispezioni in canili', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (724, 'N. prelievi effettuati in ambulatorio (non vengono impiegate U.I. in quanto le attività sono effettuate negli ambulatori)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (779, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (779, 'N. ispezioni in canili', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (779, 'N. prelievi effettuati in ambulatorio (non vengono impiegate U.I. in quanto le attività sono effettuate negli ambulatori)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (834, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (834, 'N. ispezioni in canili', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (834, 'N. prelievi effettuati in ambulatorio (non vengono impiegate U.I. in quanto le attività sono effettuate negli ambulatori)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (615, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (670, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (725, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (780, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (835, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (616, 'Numero animali da controllare (n. 40 UBA per U.I. x 1 accesso)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (616, 'Numero allevamenti (0,3 U.I. x 1 accesso x allevamento)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (671, 'Numero animali da controllare (n. 40 UBA per U.I. x 1 accesso)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (671, 'Numero allevamenti (0,3 U.I. x 1 accesso x allevamento)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (726, 'Numero animali da controllare (n. 40 UBA per U.I. x 1 accesso)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (726, 'Numero allevamenti (0,3 U.I. x 1 accesso x allevamento)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (781, 'Numero animali da controllare (n. 40 UBA per U.I. x 1 accesso)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (781, 'Numero allevamenti (0,3 U.I. x 1 accesso x allevamento)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (836, 'Numero animali da controllare (n. 40 UBA per U.I. x 1 accesso)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (836, 'Numero allevamenti (0,3 U.I. x 1 accesso x allevamento)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (617, 'Numero animali da controllare (muli, asini bardotti) (n. 50 UBA per U.I. x 1 accesso)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (617, 'Numero ispezioni da effettuare su richiesta per il controllo di cavalli (numero presunto in base ai dati storici)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (672, 'Numero animali da controllare (muli, asini bardotti) (n. 50 UBA per U.I. x 1 accesso)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (672, 'Numero ispezioni da effettuare su richiesta per il controllo di cavalli (numero presunto in base ai dati storici)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (727, 'Numero animali da controllare (muli, asini bardotti) (n. 50 UBA per U.I. x 1 accesso)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (727, 'Numero ispezioni da effettuare su richiesta per il controllo di cavalli (numero presunto in base ai dati storici)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (782, 'Numero animali da controllare (muli, asini bardotti) (n. 50 UBA per U.I. x 1 accesso)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (782, 'Numero ispezioni da effettuare su richiesta per il controllo di cavalli (numero presunto in base ai dati storici)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (837, 'Numero animali da controllare (muli, asini bardotti) (n. 50 UBA per U.I. x 1 accesso)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (837, 'Numero ispezioni da effettuare su richiesta per il controllo di cavalli (numero presunto in base ai dati storici)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (618, 'Numero canili da controllare', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (673, 'Numero canili da controllare', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (728, 'Numero canili da controllare', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (783, 'Numero canili da controllare', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (838, 'Numero canili da controllare', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (619, 'N. cani da controllare condotti per strada ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (619, 'N. cani da controllare oggetto di commercio', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (619, 'N. cani da controllare detenuti da privati nelle proprie abitazioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (619, 'N. cani da controllare detenuti in aziende zootecniche', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (724, 'N. cani da controllare condotti per strada ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (724, 'N. cani da controllare oggetto di commercio', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (724, 'N. cani da controllare detenuti da privati nelle proprie abitazioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (724, 'N. cani da controllare detenuti in aziende zootecniche', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (729, 'N. cani da controllare condotti per strada ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (729, 'N. cani da controllare oggetto di commercio', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (729, 'N. cani da controllare detenuti da privati nelle proprie abitazioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (729, 'N. cani da controllare detenuti in aziende zootecniche', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (784, 'N. cani da controllare condotti per strada ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (784, 'N. cani da controllare oggetto di commercio', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (784, 'N. cani da controllare detenuti da privati nelle proprie abitazioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (784, 'N. cani da controllare detenuti in aziende zootecniche', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (839, 'N. cani da controllare condotti per strada ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (839, 'N. cani da controllare oggetto di commercio', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (839, 'N. cani da controllare detenuti da privati nelle proprie abitazioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (839, 'N. cani da controllare detenuti in aziende zootecniche', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (620, 'Numero canili da controllare', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (675, 'Numero canili da controllare', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (730, 'Numero canili da controllare', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (785, 'Numero canili da controllare', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (840, 'Numero canili da controllare', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (621, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (676, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (731, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (786, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (841, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (622, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (677, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (732, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (787, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (842, 'N. campioni ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (623, 'N. campioni di latte di massa', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (623, 'N. campioni di mangime', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (678, 'N. campioni di latte di massa', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (678, 'N. campioni di mangime', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (733, 'N. campioni di latte di massa', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (733, 'N. campioni di mangime', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (788, 'N. campioni di latte di massa', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (788, 'N. campioni di mangime', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (843, 'N. campioni di latte di massa', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (843, 'N. campioni di mangime', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (624, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (679, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (734, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (789, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (844, 'N. ispezioni ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (625, 'N. campioni da effettuare negli allevamenti e banchi naturali', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (680, 'N. campioni da effettuare negli allevamenti e banchi naturali', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (735, 'N. campioni da effettuare negli allevamenti e banchi naturali', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (790, 'N. campioni da effettuare negli allevamenti e banchi naturali', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (845, 'N. campioni da effettuare negli allevamenti e banchi naturali', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (626, 'N. campioni di carni macinate o preparazioni di carni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (626, 'N. campioni di alimenti per bambini a base di carne', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (626, 'N. campioni di prodotti a base di pesce che non riportano in etichetta la presenza di crostacei', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (681, 'N. campioni di carni macinate o preparazioni di carni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (681, 'N. campioni di alimenti per bambini a base di carne', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (681, 'N. campioni di prodotti a base di pesce che non riportano in etichetta la presenza di crostacei', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (736, 'N. campioni di carni macinate o preparazioni di carni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (736, 'N. campioni di alimenti per bambini a base di carne', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (736, 'N. campioni di prodotti a base di pesce che non riportano in etichetta la presenza di crostacei', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (791, 'N. campioni di carni macinate o preparazioni di carni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (791, 'N. campioni di alimenti per bambini a base di carne', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (791, 'N. campioni di prodotti a base di pesce che non riportano in etichetta la presenza di crostacei', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (846, 'N. campioni di carni macinate o preparazioni di carni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (846, 'N. campioni di alimenti per bambini a base di carne', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (846, 'N. campioni di prodotti a base di pesce che non riportano in etichetta la presenza di crostacei', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (627, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (682, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (737, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (792, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (847, 'N. ispezioni ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (628, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (683, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (738, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (793, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (848, 'N. campioni ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (629, 'N. campioni di vino', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (629, 'N. campioni di frutta secca', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (629, 'N. campioni di conserve vegetali e succhi di frutta ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (629, 'N. campioni di bevande analcoliche ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (684, 'N. campioni di vino', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (684, 'N. campioni di frutta secca', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (684, 'N. campioni di conserve vegetali e succhi di frutta ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (684, 'N. campioni di bevande analcoliche ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (739, 'N. campioni di vino', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (739, 'N. campioni di frutta secca', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (739, 'N. campioni di conserve vegetali e succhi di frutta ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (739, 'N. campioni di bevande analcoliche ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (794, 'N. campioni di vino', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (794, 'N. campioni di frutta secca', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (794, 'N. campioni di conserve vegetali e succhi di frutta ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (794, 'N. campioni di bevande analcoliche ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (849, 'N. campioni di vino', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (849, 'N. campioni di frutta secca', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (849, 'N. campioni di conserve vegetali e succhi di frutta ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (849, 'N. campioni di bevande analcoliche ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (630, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (685, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (740, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (795, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (850, 'N. ispezioni ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (631, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (686, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (741, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (796, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (851, 'N. campioni ', 0, TRUE, TRUE);
-------------------------------
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (632, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (687, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (742, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (797, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (852, 'N. ispezioni ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (633, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (688, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (743, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (798, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (853, 'N. ispezioni ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (634, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (689, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (744, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (799, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (854, 'N. campioni ', 0, TRUE, TRUE);


INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (635, 'n. campioni di mais', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (635, 'n. campioni di latte', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (635, 'n. campioni di prodotti a base di latte', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (690, 'n. campioni di mais', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (690, 'n. campioni di latte', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (690, 'n. campioni di prodotti a base di latte', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (745, 'n. campioni di mais', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (745, 'n. campioni di latte', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (745, 'n. campioni di prodotti a base di latte', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (800, 'n. campioni di mais', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (800, 'n. campioni di latte', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (800, 'n. campioni di prodotti a base di latte', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (855, 'n. campioni di mais', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (855, 'n. campioni di latte', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (855, 'n. campioni di prodotti a base di latte', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (636, 'n. campioni ricerca salmonella ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (636, 'n. campioni Conteggio delle colonie aerobiche', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (636, 'n. campioni ricerca Enterobatteriacee ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (691, 'n. campioni ricerca salmonella ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (691, 'n. campioni Conteggio delle colonie aerobiche', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (691, 'n. campioni ricerca Enterobatteriacee ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (746, 'n. campioni ricerca salmonella ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (746, 'n. campioni Conteggio delle colonie aerobiche', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (746, 'n. campioni ricerca Enterobatteriacee ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (801, 'n. campioni ricerca salmonella ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (801, 'n. campioni Conteggio delle colonie aerobiche', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (801, 'n. campioni ricerca Enterobatteriacee ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (856, 'n. campioni ricerca salmonella ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (856, 'n. campioni Conteggio delle colonie aerobiche', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (856, 'n. campioni ricerca Enterobatteriacee ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (637, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (692, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (747, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (802, 'N. campioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (857, 'N. campioni ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (638, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (693, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (748, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (803, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (858, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (639, 'A) N. verifiche dell’edibilità di funghi freschi spontanei (non comporta l''utilizzazione di U.I. in quanto fatte in ufficio)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (639, 'B) N. ispezioni negli stabilimenti', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (639, 'C) N. controlli ufficiali all’importazione da Paesi Terzi', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (639, 'D) N. controlli ufficiali richiesti da altre Autorità sull’edibilità dei funghi', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (639, 'E) N. consulenze in presunti casi di intossicazione', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (639, 'F) N. campioni di funghi', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (639, 'G) N. interventi formativi e educativi diretti alla popolazione', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (694, 'A) N. verifiche dell’edibilità di funghi freschi spontanei (non comporta l''utilizzazione di U.I. in quanto fatte in ufficio)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (694, 'B) N. ispezioni negli stabilimenti', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (694, 'C) N. controlli ufficiali all’importazione da Paesi Terzi', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (694, 'D) N. controlli ufficiali richiesti da altre Autorità sull’edibilità dei funghi', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (694, 'E) N. consulenze in presunti casi di intossicazione', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (694, 'F) N. campioni di funghi', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (694, 'G) N. interventi formativi e educativi diretti alla popolazione', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (749, 'A) N. verifiche dell’edibilità di funghi freschi spontanei (non comporta l''utilizzazione di U.I. in quanto fatte in ufficio)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (749, 'B) N. ispezioni negli stabilimenti', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (749, 'C) N. controlli ufficiali all’importazione da Paesi Terzi', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (749, 'D) N. controlli ufficiali richiesti da altre Autorità sull’edibilità dei funghi', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (749, 'E) N. consulenze in presunti casi di intossicazione', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (749, 'F) N. campioni di funghi', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (749, 'G) N. interventi formativi e educativi diretti alla popolazione', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (804, 'A) N. verifiche dell’edibilità di funghi freschi spontanei (non comporta l''utilizzazione di U.I. in quanto fatte in ufficio)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (804, 'B) N. ispezioni negli stabilimenti', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (804, 'C) N. controlli ufficiali all’importazione da Paesi Terzi', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (804, 'D) N. controlli ufficiali richiesti da altre Autorità sull’edibilità dei funghi', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (804, 'E) N. consulenze in presunti casi di intossicazione', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (804, 'F) N. campioni di funghi', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (804, 'G) N. interventi formativi e educativi diretti alla popolazione', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (859, 'A) N. verifiche dell’edibilità di funghi freschi spontanei (non comporta l''utilizzazione di U.I. in quanto fatte in ufficio)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (859, 'B) N. ispezioni negli stabilimenti', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (859, 'C) N. controlli ufficiali all’importazione da Paesi Terzi', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (859, 'D) N. controlli ufficiali richiesti da altre Autorità sull’edibilità dei funghi', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (859, 'E) N. consulenze in presunti casi di intossicazione', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (859, 'F) N. campioni di funghi', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (859, 'G) N. interventi formativi e educativi diretti alla popolazione', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (640, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (640, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (640, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (640, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (640, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (695, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (695, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (695, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (695, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (695, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (750, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (750, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (750, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (750, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (750, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (805, 'N. mezzi ispezionati', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (805, 'N. mezzi ispezionati', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (805, 'N. mezzi ispezionati', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (805, 'N. mezzi ispezionati', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (805, 'N. mezzi ispezionati', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (860, 'N. mezzi ispezionati', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (860, 'N. mezzi ispezionati', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (860, 'N. mezzi ispezionati', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (860, 'N. mezzi ispezionati', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (860, 'N. mezzi ispezionati', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (641, 'Numero animali da controllare (n. 40 UBA per U.I. x 1 accesso)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (641, 'Numero allevamenti (0,3 U.I. x 1 accesso x allevamento)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (696, 'Numero animali da controllare (n. 40 UBA per U.I. x 1 accesso)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (696, 'Numero allevamenti (0,3 U.I. x 1 accesso x allevamento)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (751, 'Numero animali da controllare (n. 40 UBA per U.I. x 1 accesso)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (751, 'Numero allevamenti (0,3 U.I. x 1 accesso x allevamento)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (806, 'Numero animali da controllare (n. 40 UBA per U.I. x 1 accesso)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (806, 'Numero allevamenti (0,3 U.I. x 1 accesso x allevamento)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (861, 'Numero animali da controllare (n. 40 UBA per U.I. x 1 accesso)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (861, 'Numero allevamenti (0,3 U.I. x 1 accesso x allevamento)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (642, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (697, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (752, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (807, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (862, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (643, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (698, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (753, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (808, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (863, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (644, 'N. AUDIT', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (699, 'N. AUDIT', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (754, 'N. AUDIT', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (809, 'N. AUDIT', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (864, 'N. AUDIT', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (645, 'N. AUDIT', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (700, 'N. AUDIT', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (755, 'N. AUDIT', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (810, 'N. AUDIT', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (865, 'N. AUDIT', 0, TRUE, TRUE);


INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (646, 'N. AUDIT', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (701, 'N. AUDIT', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (756, 'N. AUDIT', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (811, 'N. AUDIT', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (866, 'N. AUDIT', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (647, 'N. ispezioni (n. 1 ispezione = 1 U.I.)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (702, 'N. ispezioni (n. 1 ispezione = 1 U.I.)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (757, 'N. ispezioni (n. 1 ispezione = 1 U.I.)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (812, 'N. ispezioni (n. 1 ispezione = 1 U.I.)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (867, 'N. ispezioni (n. 1 ispezione = 1 U.I.)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (648, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (703, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (758, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (813, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (868, 'N. ispezioni ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (649, 'N. suini macellati ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (704, 'N. suini macellati ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (759, 'N. suini macellati ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (814, 'N. suini macellati ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (869, 'N. suini macellati ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (650, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (705, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (760, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (815, 'N. ispezioni ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (870, 'N. ispezioni ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (654, 'N. gatti microchippati ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (709, 'N. gatti microchippati ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (764, 'N. gatti microchippati ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (819, 'N. gatti microchippati ', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (874, 'N. gatti microchippati ', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (655, 'N. interventi (n. 1 intervento = 1 U.I.)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (710, 'N. interventi (n. 1 intervento = 1 U.I.)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (765, 'N. interventi (n. 1 intervento = 1 U.I.)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (820, 'N. interventi (n. 1 intervento = 1 U.I.)', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (875, 'N. interventi (n. 1 intervento = 1 U.I.)', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (656, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (711, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (766, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (821, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (876, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (657, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (712, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (767, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (822, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (877, 'N. ispezioni', 0, TRUE, TRUE);
--------------------------------------------------------------
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (658, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (713, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (768, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (823, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (878, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (659, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (714, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (769, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (824, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (879, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (660, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (715, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (770, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (825, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (880, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (661, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (716, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (771, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (826, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (881, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (662, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (717, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (772, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (827, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (882, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (663, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (718, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (773, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (828, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (883, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (664, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (719, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (774, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (829, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (884, 'N. ispezioni', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (665, 'N. eventi programmati', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (720, 'N. eventi programmati', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (775, 'N. eventi programmati', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (830, 'N. eventi programmati', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (885, 'N. eventi programmati', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (666, 'N. carcasse lavorate', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (721, 'N. carcasse lavorate', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (776, 'N. carcasse lavorate', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (831, 'N. carcasse lavorate', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (886, 'N. carcasse lavorate', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (667, 'N. attività', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (722, 'N. attività', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (777, 'N. attività', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (832, 'N. attività', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (887, 'N. attività', 0, TRUE, TRUE);

INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (668, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (723, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (778, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (833, 'N. ispezioni', 0, TRUE, TRUE);
INSERT INTO dpat_indicatore (id_attivita, description, obiettivo_in_cu, enabled, ui_calcolabile) VALUES (888, 'N. ispezioni', 0, TRUE, TRUE);
-----------------------------------------nuovo dpat AVELLINO-------------------------------------------------------------------------------
--AVELLINO 201 
INSERT INTO dpat(anno, id_asl, enabled) VALUES (2014, 201, true);

--chiave id 10
INSERT INTO dpat_sezione (description, id_dpat, enabled) VALUES ('SEZ. A DEL DPAR', 10, TRUE);

select * from dpat_sezione where id_dpat = 10


INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 1 (TBC) PIANO 2 (BRC) PIANO 4 (LEB)', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 3', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 5', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 6', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 7', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 8', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 9', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 10', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 11', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 12', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 13', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 14', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 15', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 16', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 17', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 18', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 19', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 20', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'PIANO 21', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'ATTIVITA'' 1', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'ATTIVITA'' 2', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'ATTIVITA'' 3', TRUE);
INSERT INTO dpat_piano(id_sezione, description, enabled) VALUES (10, 'ATTIVITA'' 39', TRUE);


select * from dpat_piano  where id_sezione  = 33

--962-984

INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (962, 0, 'Piani di monitoraggio finalizzati all’eradicazione della TBC, BRC e LEB nei bovini e bufalini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (963, 0, 'Piano di monitoraggio finalizzato all’eradicazione della BRC negli ovicaprini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (964, 0, 'Piano di monitoraggio per la ricerca della salmonella nei riproduttori', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (965, 0, 'Piano di monitoraggio per la ricerca della salmonella nelle ovaiole', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (966, 0, 'Piano di monitoraggio per la ricerca della salmonella nei polli da carne', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (967, 0, 'Piano di monitoraggio per la ricerca della salmonella nei tacchini da riproduzione e ingrasso', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (968, 0, 'Piano di monitoraggio finalizzato all’eradicazione della BSE – Reg. CE 999/2001', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (969, 0, 'Piano di monitoraggio finalizzato all’eradicazione della Scrapie – Dec CE 677/2002', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (970, 0, 'Piano di monitoraggio finalizzato all''eradicazione delle TSE', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (971, 0, 'Piano di monitoraggio della malattia di Aujeszky (1 suino campionato = 1 UBA)', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (972, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione dei suini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (973, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione degli ovicaprini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (974, 0, 'Piano di monitoraggio sul sistema di identificazione e registrazione dei bovini e bufalini', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (975, 0, 'Piano  di monitoraggio Nazionale Residui', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (976, 0, 'Piano Nazionale di Monitoraggio OGM', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (977, 0, 'Piano di Monitoraggio sui residui di fitosanitari negli alimenti di origine vegetale ed animale (DM 23/12/1992)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (978, 0, 'Piano Nazionale di Monitoraggio Alimentazione Animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (979, 0, 'Piano Nazionale di Monitoraggio Benessere Animale', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (980, 0, 'Piano di Monitoraggio Farmacosorveglianza', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (981, 0, 'Audit negli stabilimenti riconosciuti ex sez. IX Reg CE 853/04  (latte crudo e derivati)', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (982, 0, 'Ispezioni effettuate per sistemi d''allarme rapido', TRUE, TRUE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (983, 0, 'Iscrizione cani in BDR e movimentazione anagrafe canina', TRUE, FALSE);
INSERT INTO dpat_attivita (id_piano, ui, description, enabled, ui_calcolabile) VALUES (984, 0, 'Supervisioni', TRUE, TRUE);



INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (899,'Numero bovini da controllare di allevamenti U.I. o I. (n. 40 UBA per U.I. x 3 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (899,'Numero allevamenti bovini U.I. o I. (0,3 U.I. x 3 accessi x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (899,'Numero bovini da controllare di allevamenti NON U.I. o I.  (n. 40 UBA per U.I. x 4 accessi) ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (899,'Numero allevamenti bovini NON U.I. o I. (0,3 U.I. x 4 accessi x allevamento) ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (899,'Numero bufalini da controllare di allevamenti U.I. o I.(n. 42 UBA per U.I. x 3 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (899,'Numero allevamenti bufalini U.I. o I. (0,1 U.I. x 3 accessi x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (899,'Numero bufalini da controllare di allevamenti NON U.I. o I. (n. 42 UBA per U.I. x 4 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (899,'Numero allevamenti bufalini NON U.I. o I. (0,1 U.I. x 4 accessi x allevamento)', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (900,'Numero ovicaprini da controllare di allevamenti U.I. o I.(n. 40 UBA per U.I. x 1 accesso)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (900,'Numero allevamenti ovicaprini U.I. o I. (0,2 U.I. x 1 accesso x allevamento)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (900,'Numero ovicaprini da controllare di allevamenti NON U.I. o I.  (n. 40 UBA per U.I. x 2 accessi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (900,'Numero allevamenti ovicaprini NON U.I. o I.(0,2 U.I. x 2 accessi x allevamento)', 0, TRUE);


INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (901,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (902,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (903,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (904,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (905,'Numero animali morti da controllare (numero presunto in base ai dati storici)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (906,'Numero animali morti da controllare (numero presunto in base ai dati storici)', 0, TRUE);


INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (908,'N. animali da controllare  in allevamenti da riproduzione a ciclo chiuso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (908,'N. allevamenti da riproduzione a ciclo chiuso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (908,'N. animali da controllare in allevamenti da riproduzione a ciclo aperto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (908,'n. allevamenti da riproduzione a ciclo aperto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (908,'N. animali da controllare in allevamenti da ingrasso da macello', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (908,'n. allevamenti da ingrasso da macello', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (908,'N. animali da controllare in allevamenti da ingrasso da vita e stalle di sosta.', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (908,'n. allevamenti da ingrasso da vita e stalle di sosta', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (909,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (910,'Numero aziende da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (911,'Numero aziende da controllare', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (912,'N. campioni in allevamenti', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (912,'N.  campioni al macello (vengono impiegate il 50 % delle U.I. rispetto agli altri sottopiani in quanto si presume che almeno nel 50% dei casi le attività siano effettuate come normale attività al macello)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (912,'N. campioni in stabilimenti', 0, TRUE); 

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (913,'Campioni di soia', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (913,'Campioni di soia, riso o mais di produzione biologica', 0, TRUE); 
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (913,'Campioni di mais', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (913,'Campioni di riso', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (913,'Campioni di alimenti per bambini a base di cereali', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (913,'Campioni di altri prodotti alimentari finiti NON di o.a.', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (913,'Campioni effettuati su disposizione dell''USMAF', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (913,'N. ispezioni senza campionamento su iniziativa dell''ASL', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (913,'N. ispezioni senza campionamento su iniziativa dell''USMAF', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (914,'N. campioni  alimenti vegetali prodotti in Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (914,'N. campioni alimenti vegetali prodotti fuori Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (914,'N. campioni alimenti di o.a. prodotti in Regione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (914,'N. campioni alimenti di o.a. prodotti fuori Regione', 0, TRUE);  

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. 1A (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. 1C (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. 1D (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. 1E (BSE)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. 1F (BSE', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. 2.1 (additivi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. 2.2 (sostanze farmacologiche)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. 3A (diossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. 3B (diossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. 1-4 (micotossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. 2 (micotossine)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. 1-5 (contaminanti)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. A-5 (radionuclidi)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. 6.1 (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. 6.2  (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. 6.2 pet food (salmonella)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. OGM (sorvegl.)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. campioni della tab. OGM (monitor.) ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (915,'N. ispezioni senza campionamento', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. allevamenti da controllare con vitelli a carni bianche ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. allevamenti da controllare con vitelli ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. allevamenti suini da controllare aventi >40 capi oppure >6 scrofe', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. allevamenti ovaiole da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. allevamenti broiler da controllare aventi >500 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. allevamenti bovini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. allevamenti struzzi da controllare aventi >10 capi ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. allevamenti tacchini ed altri avicoli da controllare aventi >250 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. allevamenti conigli da controllare aventi >250 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. allevamenti ovini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. allevamenti caprini da controllare aventi >50 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. allevamenti bufalini da controllare aventi >10 capi ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. allevamenti equini da controllare aventi >10 capi', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. allevamenti animali da pelliccia da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. allevamenti pesci da controllare', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. mezzi di trasporto da controllare durante il trasporto', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. mezzi di trasporto da controllare c/o posti di controllo', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. mezzi di trasporto da controllare presso il luogo di partenza ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. mezzi di trasporto da controllare presso luoghi di destinazione diversi dai macelli ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. mezzi di trasporto autorizzati per il trasporto superiore alle 8 ore da controllare c/o il macello di destinazione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. mezzi di trasporto autorizzati per il trasporto inferiore alle 8 ore da controllare c/o il macello di destinazione', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (916,'N. liste di riscontro da redigere durante la macellazione', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (917,'Ispezioni effettuate nei grossisti di medicinali veterinari NON autorizzati alla vendita diretta (art 66, Dlvo 193/2006)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (917,'Ispezioni effettuate nei grossisti autorizzati alla vendita diretta di medicinali veterinari (art 70, Dlvo 193/2006)', 0, TRUE);


INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (918,'N. AUDIT', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (919,'N. ispezioni', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (920,'N. cani da microchippare e iscrivere in BDR', 0, TRUE);

INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (921,'N. supervisioni tipo 1.1. (su controlli ufficiali svolti nei sette giorni precedenti dal personale incaricato con esito favorevole o con il rilievo di non conformita'' ', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (921,'N. supervisioni tipo 1.2. (per la verifica del livello di know how del personale)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (921,'N. supervisioni tipo 2. (documentale - non vengono impiegate U.I. in quanto è una attività svolta in ufficio)', 0, TRUE);
INSERT INTO dpat_indicatore(id_attivita, description, obiettivo_in_cu, enabled) VALUES (921,'N. supervisioni tipo 3. (mediante simulazioni)', 0, TRUE);


/*script permessi bottoni riapri e aggiorna oia in SDC*/

INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, level, enabled, 
            active, viewpoints)
    VALUES (nextval('permission_permission_id_seq'), 113,'dpat-fdc-riapri',true,false,false,false,'Bottone Riapri in SDC',0,true,true,false);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, level, enabled, 
            active, viewpoints)
    VALUES (nextval('permission_permission_id_seq'), 113,'dpat-fdc-aggiornaoia',true,false,false,false,'Bottone Aggiorna Elenco OIA',0,true,true,false);


