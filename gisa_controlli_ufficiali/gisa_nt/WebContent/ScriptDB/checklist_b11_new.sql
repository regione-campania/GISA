
insert into lookup_chk_bns_mod(code, description, level, enabled, codice_specie, nuova_gestione)  values(20,'CONDIZIONALITA''-ATTO B-11',0, true, -1,true);

insert into chk_bns_cap(code,description, level, enabled, idmod,tipo_capitolo) values (234,'1. AZIENDE ZOOTECNICHE',0,true,20,'');
insert into chk_bns_cap(code,description, level, enabled, idmod,tipo_capitolo) values (235,'2. AZIENDE CHE PRODUCONO LATTE ALIMENTARE',1,true,20,'');
insert into chk_bns_cap(code,description, level, enabled, idmod,tipo_capitolo) values (236,'3. AZIENDE CHE PRODUCONO LATTE DESTINATO ALLA FILIERA ALIMENTARE DEL LATTE FRESCO',2,true,20,'');
insert into chk_bns_cap(code,description, level, enabled, idmod,tipo_capitolo) values (237,'4. AZIENDE CHE PRODUCONO UOVA',3,true,20,'');
insert into chk_bns_cap(code,description, level, enabled, idmod,tipo_capitolo) values (238,'5. AZIENDE CHE PRODUCONO MANGIMI O ALIMENTARI PER ANIMALI',4,true,20,'');

--INSERT DOMANDE CAPITOLO 1
insert into chk_bns_dom (description, level, enabled, idcap) values ('Se l''azienda impiega mangimi medicati, i mangimi medicati (distinguibili-etichettati) sono stoccati separatamente dai mangimi non medicati.
(Se l''azienda non impiega mangimi medicati, selezionare ''S'')',1,true,234);

insert into chk_bns_dom (description, level, enabled, idcap) values ('L''azienda acquista mangimi e/o foraggi',2,true,234);

insert into chk_bns_dom (description, level, enabled, idcap) values ('Presenza di documenti in grado di identificare i fornitori di alimenti per animali compresi additivi e premiscele di additivi.
Tali documenti permettono all''autorita'' di controllo una facile verifica dei fornitori',3,true,234);

insert into chk_bns_dom (description, level, enabled, idcap) values ('Presenza del registro dei trattamenti veterinari e/o con sostanze ad azione ormonica, tireostatica e betagoniste',4,true,234);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Puntuale registrazione dei trattamenti veterinari eseguiti e ordinata tenuta delle prescrizioni e delle fatture di acquisto',5,true,234);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Presenza armadietto farmaceutico: autorizzazione scorte medicinali (D.Lgs. 193/06)',6,true,234);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Rispetto di appropriati tempi di attesa nell''utilizzo di farmaci veterinari',7,true,234);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Lo stoccaggio e la manipolazione delle sostanze pericolose sono eseguiti con cura ed attenzione, al fine di prevenire ogni contaminazione',8,true,234);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Viene assicurato un corretto uso degli additivi dei mangimi e dei prodotti medicinali veterinari, cosi'' come previsto dalla norma',9,true,234);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Viene tenuta opportuna registrazione* di natura e origine degli alimenti e mangimi somministrati agli animali',10,true,234);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Viene tenuta opportuna registrazione* di prodotti medicinali veterinari o altri trattamenti curativi somministrati agli animali',11,true,234);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Viene tenuta opportuna registrazione* dei risultati di ogni analisi effettuata sugli animali, che abbia una rilevanza ai fini della sicurezza alimentare',12,true,234);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Viene tenuta opportuna registrazione* di ogni rapporto o controllo effettuato sugli animali o sui prodotti di origine animale',13,true,234);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli alimenti destinati agli animali sono immagazzinati separatamente da prodotti chimici o da altri prodotti o sostanze proibite per l''alimentazione animale',14,true,234);

INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Presenza registro delle vendite dirette e/o delle consegne del latte (solo bovini)',0, true, 235);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Aggiornamento del registro delle vendite dirette e/o delle consegne',1, true, 235);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('In caso di non conformita'' del latte, l''operatore e'' in grado di avviare immediatamente procedure per ritirarlo e informarne immediatamente le autorita'' competenti',2, true, 235);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Presenza di un sistema di identificazione degli animali infetti, malati e/o sotto trattamento farmacologico',3, true, 235);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Presenza di spazi idonei per l''isolamento degli animali malati o feriti',4, true, 235);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Azienda ufficialmente indenne da brucellosi/tubercolosi',5, true, 235);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Se sono stati introdotti animali per i quali e'' prevista certificazione sanitaria, vi e'' presenza della certificazione sanitaria. (Se non sono stati introdotti animali per i quali e'' prevista certificazione sanitaria, selezionare ''S'')',6, true, 235);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Se sono stati introdotti animali per i quali e'' prevista certificazione sanitaria, vi e'' corretta tenuta delle segnalazioni/analisi dei controlli effettuati sugli animali o sui loro prodotti. (Se non sono stati introdotti animali per i quali e'' prevista certificazione sanitaria, selezionare ''S'')',7, true, 235);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Presenza di locali, impianti, attrezzature di mungitura e stoccaggio idonei a prevenire la contaminazione del latte',8, true, 235);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Presenza di animali alla mungitura adeguatamente puliti',9, true, 235);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Il latte prodotto e'' protetto da contaminazioni',10, true, 235);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Il personale e'' a conoscenza delle norme e dei requisiti minimi di igiene',11, true, 235);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Disponibilita'' delle schede tecniche dei presidi chimici impiegati per la pulizia e la disinfezione degli impianti/attrezzature',12, true, 235);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Rispetto delle modalita'' di refrigerazione per lo stoccaggio del latte',13, true, 235);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Presenza del manuale aziendale di tracciabilita'' del latte',0, true, 236);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Lo stoccaggio delle uova avviene in maniera corretta',0, true, 237);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Lo stoccaggio e la manipolazione delle sostanze pericolose sono eseguiti con cura ed attenzione, al fine di prevenire ogni contaminazione',0, true, 238);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('I risultati delle analisi realizzate su campioni prelevati su prodotti primari e altri campioni rilevanti ai fini della sicurezza dei mangimi sono tenuti nella giusta considerazione',1, true, 238);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Viene tenuta opportuna registrazione* di ogni uso di prodotti fitosanitari',2, true, 238);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Viene tenuta opportuna registrazione* dell''uso di semente geneticamente modificata',3, true, 238);
INSERT INTO chk_bns_dom(description, level, enabled, idcap) VALUES ('Viene tenuta opportuna registrazione* della provenienza e la quantita'' di ogni elemento costitutivo del mangime e la destinazione e quantita'' di ogni output di mangime',4, true, 238);
