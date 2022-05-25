--Creazione tabella contenente le checklist raggruppate per specie
CREATE TABLE lookup_chk_bns_mod
(
  code integer NOT NULL,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  codice_specie integer,
  CONSTRAINT pkey PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_chk_bns_mod
  OWNER TO postgres;

-- Creazione tabella contenente i capitoli
CREATE TABLE chk_bns_cap
(
  code serial NOT NULL,
  description character varying(350) NOT NULL,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  idmod integer,
  CONSTRAINT chk_bns_cap_pkey PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE chk_bns_cap
  OWNER TO postgres;
  
-- Creazione tabella contenente le domande
CREATE TABLE chk_bns_dom
(
  code serial NOT NULL,
  description text NOT NULL,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  idcap integer,
  CONSTRAINT chk_bns_dom_pkey PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE chk_bns_dom
  OWNER TO postgres;

-- Creazione tabella contenente l'istanza della checklist

CREATE TABLE chk_bns_mod_ist
(
  id serial NOT NULL,
  id_alleg integer,
  idcu integer,
  entered timestamp(3) without time zone NOT NULL DEFAULT now(),
  enteredby integer NOT NULL,
  modified timestamp(3) without time zone NOT NULL DEFAULT now(),
  modifiedby integer NOT NULL,
  trashed_date timestamp(3) without time zone,
  data_chk timestamp(3) without time zone,
  orgid integer,
  CONSTRAINT chk_bns_mod_ist_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE chk_bns_mod_ist
  OWNER TO postgres;
  
-- Creazione tabella per le risposte


-- DROP TABLE chk_bns_risposte;

CREATE TABLE chk_bns_risposte
(
  id serial NOT NULL,
  idmodist integer,
  idcap integer,
  iddom integer,
  desccap text,
  descdom text,
  esito boolean,
  osservazioni text,
  punteggioa integer,
  punteggiob integer,
  punteggioc integer,
  punteggiototale integer,
  CONSTRAINT chk_bns_risposte_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE chk_bns_risposte
  OWNER TO postgres;

  
-- Inserimento dati nella lookup_chk_bns_mod

INSERT INTO lookup_chk_bns_mod (code,description,codice_specie) VALUES ('1','ALLEGATO 1 GALLINE OVAIOLE','131');
INSERT INTO lookup_chk_bns_mod (code,description,codice_specie) VALUES ('2','ALLEGATO 2 SUINI','122');
INSERT INTO lookup_chk_bns_mod (code,description,codice_specie) VALUES ('3','ALLEGATO 3 VITELLI','121');
INSERT INTO lookup_chk_bns_mod (code,description,codice_specie) VALUES ('4','ALLEGATO 4 FAGIANI','139');
INSERT INTO lookup_chk_bns_mod (code,description,codice_specie) VALUES ('5','ALLEGATO 4 CAPRINI','125');
INSERT INTO lookup_chk_bns_mod (code,description,codice_specie) VALUES ('6','ALLEGATO 4 AVICOLI MISTI','146');
INSERT INTO lookup_chk_bns_mod (code,description,codice_specie) VALUES ('7','ALLEGATO 4 BUFALINI','129');
INSERT INTO lookup_chk_bns_mod (code,description,codice_specie) VALUES ('8','ALLEGATO 4 BOVINI','121');
INSERT INTO lookup_chk_bns_mod (code,description,codice_specie) VALUES ('9','ALLEGATO 4 CAVALLI','126');
INSERT INTO lookup_chk_bns_mod (code,description,codice_specie) VALUES ('10','ALLEGATO 4 OVINI','124');
INSERT INTO lookup_chk_bns_mod (code,description,codice_specie) VALUES ('11','ALLEGATO 4 QUAGLIE','134');
INSERT INTO lookup_chk_bns_mod (code,description,codice_specie) VALUES ('12','ALLEGATO 4 PESCI','160');
INSERT INTO lookup_chk_bns_mod (code,description,codice_specie) VALUES ('13','ALLEGATO 5 POLLI DA CARNE ','146');
INSERT INTO lookup_chk_bns_mod (code,description,codice_specie) VALUES ('14','ALLEGATO 4 CONIGLI','128');

-- Inserimento dati nella tabella chk_bns_cap

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('1. PERSONALE',0,true,1);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',0, true, 1);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',0, true, 1);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('4. SPAZIO DISPONIBILE',0, true, 1);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('5. EDIFICI E LOCALI DI STABULAZIONE',0, true, 1);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('6. ILLUMINAZIONE MINIMA',0, true, 1);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('7. ATTREZZATURE AUTOMATICHE E MECCANICHE',0, true, 1);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('8. ALIMENTAZIONE. ABBEVERAGGIO ED ALTRE SOSTANZE',0, true, 1);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('9. MUTILAZIONI',0, true, 1);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('10. PROCEDURE D''ALLEVAMENTO',0, true, 1);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('1. PERSONALE',1,true,2);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',1, true, 2);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',1, true, 2);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('4. LIBERTA'' DI MOVIMENTO',1, true, 2);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('5. SPAZIO DISPONIBILE',1, true, 2);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('6. EDIFICI E LOCALI DI STABULAZIONE',1, true, 2);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('7. ILLUMINAZIONE MINIMA',1, true, 2);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('8. PAVIMENTAZIONI',1, true, 2);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('9. MATERIALE MANIPOLABILE',1, true, 2);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('10. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',1, true, 2);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('11. MANGIMI CONTENENTI FIBRE',1, true, 2);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('12. MUTILAZIONI',1, true, 2);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('13. PROCEDURE D''ALLEVAMENTO',1, true, 2);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('14. ATTREZZATURE AUTOMATICHE E MECCANICHE',1, true, 2);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('1. PERSONALE',2,true,3);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',2, true, 3);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',2, true, 3);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('4. LIBERTA'' DI MOVIMENTO',2, true, 3);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('5. SPAZIO DISPONIBILE',2, true, 3);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('6. EDIFICI E LOCALI DI STABULAZIONE',2, true, 3);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('7. ILLUMINAZIONE MINIMA',2, true, 3);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('8. ATTREZZATURE AUTOMATICHE E MECCANICHE',2, true, 3);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('9. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',2, true, 3);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('10. TASSO DI EMOGLOBINA (VITELLI)',2, true, 3);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('11. MANGIMI CONTENENTI FIBRE',2, true, 3);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('12. MUTILAZIONI',2, true, 3);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('13. PROCEDURE D''ALLEVAMENTO',2, true, 3);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('1. PERSONALE',3,true,4);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',3, true, 4);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',3, true, 4);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('4. LIBERTA'' DI MOVIMENTO',3, true, 4);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('5. EDIFICI E LOCALI DI STABULAZIONE',3, true, 4);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('6. ATTREZZATURE AUTOMATICHE E MECCANICHE',3, true, 4);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('7. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',3, true, 4);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('8. MUTILAZIONI',3, true, 4);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('9. PROCEDURE D''ALLEVAMENTO',3, true, 4);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('1. PERSONALE',4,true,5);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',4, true, 5);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',4, true, 5);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('4. LIBERTA'' DI MOVIMENTO',4, true, 5);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('5. EDIFICI E LOCALI DI STABULAZIONE',4, true, 5);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('6. ATTREZZATURE AUTOMATICHE E MECCANICHE',4, true, 5);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('7. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',4, true, 5);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('8. MUTILAZIONI',4, true, 5);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('1. PERSONALE',5,true,6);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',5, true, 6);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',5, true, 6);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('4. LIBERTA'' DI MOVIMENTO',5, true, 6);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('5. EDIFICI E LOCALI DI STABULAZIONE',5, true, 6);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('6. ATTREZZATURE AUTOMATICHE E MECCANICHE',5, true, 6);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('7. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',5, true, 6);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('8. MUTILAZIONI',5, true, 6);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('9. PROCEDURE D''ALLEVAMENTO',5, true, 6);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('1. PERSONALE',6,true,7);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',6, true, 7);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',6, true, 7);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('4. LIBERTA'' DI MOVIMENTO',6, true, 7);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('5. EDIFICI E LOCALI DI STABULAZIONE',6, true, 7);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('6. ATTREZZATURE AUTOMATICHE E MECCANICHE',6, true, 7);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('7. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',6, true, 7);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('8. MUTILAZIONI',6, true, 7);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('9. PROCEDURE D''ALLEVAMENTO',6, true, 7);



INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('1. PERSONALE',7,true,8);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',7, true, 8);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',7, true, 8);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('4. LIBERTA'' DI MOVIMENTO',7, true, 8);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('5. EDIFICI E LOCALI DI STABULAZIONE',7, true, 8);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('6. ATTREZZATURE AUTOMATICHE E MECCANICHE',7, true, 8);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('7. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',7, true, 8);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('8. MUTILAZIONI',7, true, 8);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('9. PROCEDURE D''ALLEVAMENTO',7, true, 8);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('1. PERSONALE',8,true,9);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',8, true, 9);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',8, true, 9);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('4. LIBERTA'' DI MOVIMENTO',8, true, 9);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('5. EDIFICI E LOCALI DI STABULAZIONE',8, true, 9);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('6. ATTREZZATURE AUTOMATICHE E MECCANICHE',8, true, 9);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('7. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',8, true, 9);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('8. MUTILAZIONI',8, true, 9);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('9. PROCEDURE D''ALLEVAMENTO',8, true, 9);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('1. PERSONALE',9,true,10);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',9, true, 10);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',9, true, 10);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('4. LIBERTA'' DI MOVIMENTO',9, true, 10);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('5. EDIFICI E LOCALI DI STABULAZIONE',9, true, 10);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('6. ATTREZZATURE AUTOMATICHE E MECCANICHE',9, true, 10);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('7. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',9, true, 10);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('8. MUTILAZIONI',9, true, 10);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('9. PROCEDURE D''ALLEVAMENTO',9, true, 10);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('1. PERSONALE',10,true,11);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',10, true, 11);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',10, true, 11);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('4. LIBERTA'' DI MOVIMENTO',10, true, 11);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('5. EDIFICI E LOCALI DI STABULAZIONE',10, true, 11);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('6. ATTREZZATURE AUTOMATICHE E MECCANICHE',10, true, 11);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('7. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',10, true, 11);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('8. MUTILAZIONI',10, true, 11);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('9. PROCEDURE D''ALLEVAMENTO',10, true, 11);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('1. PERSONALE',11,true,12);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',11, true, 12);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',11, true, 12);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('4. LIBERTA'' DI MOVIMENTO',11, true, 12);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('5. EDIFICI E LOCALI DI STABULAZIONE',11, true, 12);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('6. ATTREZZATURE AUTOMATICHE E MECCANICHE',11, true, 12);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('7. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',11, true, 12);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('8. MUTILAZIONI',11, true, 12);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('9. PROCEDURE D''ALLEVAMENTO',11, true, 12);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('1. PERSONALE',12,true,13);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',12, true, 13);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',12, true, 13);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('4. LIBERTA'' DI MOVIMENTO',12, true, 13);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('5. EDIFICI E LOCALI DI STABULAZIONE',12, true, 13);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('6. ATTREZZATURE AUTOMATICHE E MECCANICHE',12, true, 13);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('7. SOMMINISTRAZIONE DI SOSTANZE, ALIMENTAZIONE, ABBEVERAGGIO',12, true, 13);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('8. MUTILAZIONI',12, true, 13);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('9. PROCEDURE OPERATIVE',12, true, 13);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('1. PERSONALE',13,true,14);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',13, true, 14);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',13, true, 14);

INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
    VALUES ('4. LIBERTA'' DI MOVIMENTO',13, true, 14);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('5. EDIFICI E LOCALI DI STABULAZIONE',13, true, 14);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('6. ATTREZZATURE AUTOMATICHE E MECCANICHE',13, true, 14);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('7. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',13, true, 14);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('8. MUTILAZIONI',13, true, 14);


INSERT INTO chk_bns_cap(
            description, level, enabled, idmod)
VALUES ('9. PROCEDURE D''ALLEVAMENTO',13, true, 14);

-- Inserimento dati nella tabella chk_bns_dom

----------------------------------------MODULO 1 GALLINE OVAIOLE --------------------------------

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0 , true, 1);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative.',1, true, 1);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2, true, 1);
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Tutte le galline ovaiole sono ispezionate dal proprietario o dalla persona responsabile almeno una volta al giorno.',3,true,2);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli impianti con piu'' piani di gabbie sono provvisti di dispositivi o di misure adeguate che consentono di ispezionare direttamente
    e agevolmente tutti i piani e che facilitano il ritiro delle galline.',4, true, 2);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' disponibile un''adeguata illuminazione che consente l''ispezione completa degli animali.',5,true,2);    
    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro dei trattamenti.',6,true,3);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro di carico e scarico/la mortalita''
e'' regolarmente registrata.',7,true,3);
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un piano di autocontrollo /GMP.',8,true,3);        
        
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',9,true,3);  


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Lo spazio a disposizione di ogni gallina e'' sufficiente per consentirle un''adeguata liberta'' di movimento ed e''
    tale da non causarle inutili sofferenze o lesioni, in tutte le tipologie di allevamento, ovvero:',10,true,4);
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('GABBIE NON MODIFICATE - ogni gallina ovaiola
dispone di almeno 550 cmq di superficie della gabbia
che deve essere misurata su un piano orizzontale e
utilizzabile senza limitazioni, (sono esclusi dal
calcolo eventuali bordi deflettori antispreco);',11,true,4);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('GABBIE MODIFICATE - ogni gallina ovaiola
dispone di almeno 750 cmq di superficie della gabbia, di cui 600 cmq di superficie utilizzabile,
fermo restando che l''altezza della gabbia diversa dall''altezza al di sopra della superficie utilizzabile
non deve essere inferiore a 20 cm in ogni punto e che la superficie totale di ogni gabbia non puo'' essere
inferiore a 2000 cmq;',12,true,4);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('SISTEMI ALTERNATIVI - il coefficiente di densita''
non e'' superiore a 9 galline ovaiole per mq di zona
utilizzabile. Per gli allevamenti che applicavano
questo sistema al 3 agosto 1999, quando la zona
utilizzabile corrisponde alla superficie del suolo
disponibile il coefficiente non e'' superiore a 12
volatili per mq di superficie',13,true,4);    
 
 INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Modello e caratteristiche delle gabbie compresi i
materiali impiegati e gli utensili con i quali le galline
possono venire a contatto non sono nocivi per gli
animali, tutte le superfici sono facilmente lavabili e
disinfettabili, non vi sono spigoli taglienti o
sporgenze.',14,true,5);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I sistemi di allevamento sono concepiti e le gabbie
sono sistemate in modo da impedire che le galline
possano scappare.',15,true,5);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La gabbia e le dimensioni della relativa apertura
hanno forma e dimensioni tali da permettere di
estrarre una gallina adulta senza causarle sofferenze,
lesioni o ferite.',16,true,5);  
 
 INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il tipo di pavimentazione non e'' sdrucciolevole, non
ha asperita'' tali da provocare lesioni e sostiene
adeguatamente ciascuna delle dita anteriori di
ciascuna zampa.',17,true,5); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il tipo di pavimentazione consentendo agli animali di
coricarsi, giacere, alzarsi, muoversi ed accudire a se
stessi senza difficolta'', secondo le esigenze
fisiologiche della specie.',18,true,5);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Tutti i locali, le attrezzature e gli utensili con i quali
le galline sono in contatto sono completamente puliti
e disinfettati con regolarita'' e comunque ogni volta
che viene praticato un vuoto sanitario e prima di
introdurre una nuova partita di galline. Quando i
locali sono occupati, tutte le superfici e le
attrezzature sono mantenute in condizioni di pulizia
soddisfacenti.',19,true,5);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario.',20,true,5); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('L''isolamento termico della struttura, il riscaldamento
e la ventilazione sono adeguati e consentono di
mantenere entro limiti non dannosi per le galline la
circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa e le concentrazioni di
gas: all''atto dell''ispezione T&deg; e UR sono adeguate
alle esigenze etologiche della specie e all''eta'' degli
animali.',21,true,5);   

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le deiezioni sono eliminate regolarmente.',22,true,5);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le galline morte sono rimosse giornalmente.',23,true,5);  
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('GABBIE NON MODIFICATE (vietate a decorrere
dal 1&deg; gennaio 2012):',24,true,5);  


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('a) La mangiatoia e'' utilizzabile senza limitazioni ed
ha una lunghezza minima di 10 cm moltiplicata per il
numero di ovaiole nella gabbia;',25,true,5);

  INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('b) Ogni gabbia, in mancanza di tettarelle o coppette,
dispone di un abbeveratoio contiNnuo della
medesima lunghezza della mangiatoia. Nel caso di
abbeveratoi a raccordo, almeno due tettarelle o
coppette sono raggiungibili da ciascuna gabbia;',26,true,5); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('L''altezza minima delle gabbie non e'' inferiore a 40
cm per il 65% della superficie e non e'' inferiore a 35
cm in ogni punto;',27,true,5);    
  
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il pavimento delle gabbie e'' costruito in modo da
sostenere adeguatamente ciascuna delle unghie
anteriori di ciascuna zampa. La pendenza del
pavimento non supera il 14% ovvero 8 gradi
(pendenze superiori sono consentite solo per i
pavimenti diversi da quelli provvisti di rete metallica
rettangolare);',28,true,5);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le gabbie sono provviste di adeguati dispositivi per
accorciare le unghie.',29,true,5); 
   
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('GABBIE MODIFICATE: le galline ovaiole
dispongono di:
a) un nido (la cui area non entra a far parte della
superficie utilizzabile);
b) di una lettiera che consente loro di becchettare e
razzolare;
c) di posatoi appropriati che offrono almeno 15 cm di
spazio per ovaiola;',30,true,5);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La mangiatoia e'' utilizzabile senza limitazioni ed ha
una lunghezza minima di 12 cm moltiplicata per il
numero di ovaiole nella gabbia;',31,true,5);    


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Ogni gabbia dispone di un sistema di abbeveraggio
appropriato tenuto conto in particolare della
dimensione del gruppo. Nel caso di abbeveraggio a
raccordo, almeno due tettarelle o coppette sono
raggiungibili da ciascuna ovaiola;',32,true,5);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le file di gabbie (per agevolare l''ispezione, la
sistemazione e l''evacuazione dei volatili), sono
separate da passaggi aventi una larghezza minima di
90 cm e tra il pavimento dell''edificio e le gabbie
delle file inferiori lo spazio e'' di almeno 35 cm;',33,true,5);    


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le gabbie sono provviste di adeguati dispositivi per
accorciare le unghie.',34,true,5);    


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('SISTEMI ALTERNATIVI - gli impianti di
allevamento sono attrezzati in modo da garantire che
tutte le galline ovaiole dispongano:',35,true,5);    


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- di mangiatoie lineari che offrono almeno 10 cm di
lunghezza per volatile o circolari che offrono almeno
4 cm di lunghezza per volatile;',36,true,5);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- di abbeveratoi continui che offrono 2,5 cm di
lunghezza per ovaiola o circolari che offrono 1 cm di
lunghezza per ovaiola. In caso di utilizzazione di
abbeveratoio a tettarella o a coppetta e'' prevista
almeno una tettarella o una coppetta ogni 10 ovaiole.
Nel caso di abbeveratoio a raccordo, almeno due
tettarelle o due coppette devono essere raggiungibili
da ciascuna ovaiola;',37,true,5);   

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- di almeno un nido per 7 ovaiole. Se sono utilizzati
nidi di gruppo e'' prevista una superficie di almeno 1
m2 per un massimo di 120 ovaiole;',38,true,5);   


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- di posatoi appropriati, privi di bordi aguzzi e che
offrono almeno 15 cm di spazio per ovaiola. I posatoi
non sovrastano le zone coperte di lettiera: la distanza
orizzontale fra posatoi non e'' inferiore a 30 cm e
quella tra i posatoi e le pareti non e'' inferiore a 20 cm;',39,true,5);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- di una superficie di lettiera di almeno 250 cm2 per
ovaiola; la lettiera occupa almeno un terzo della
superficie al suolo.',40,true,5);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il pavimento degli impianti e'' costruito in modo da
sostenere adeguatamente ciascuna delle unghie
anteriore di ciascuna zampa.',41,true,5);   


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se il sistema di allevamento consente alle galline
ovaiole di muoversi liberamente fra diversi livelli:',42,true,5);  


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- il numero di livelli sovrappostie'' limitato a 4;',43,true,5);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- l''altezza libera minima fra i vari livelli e'' di 45 cm;',44,true,5); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- le mangiatoie e gli abbeveratoi sono ripartiti in
modo da permettere a tutte le ovaiole un accesso
uniforme;',45,true,5); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- i livelli sono installati in modo da impedire alle
deiezioni di cadere sui livelli inferiori.',46,true,5);      

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se le galline ovaiole dispongono di un passaggio che
consente loro di uscire all''aperto:',47,true,5);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- le diverse aperture del passaggio danno
direttamente accesso allo spazio all''aperto, hanno
un''altezza minima di 35 cm, una larghezza di 40 cm e
sono distribuite su tutta la lunghezza dell''edificio:',48,true,5); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- e'' comunque disponibile un''apertura totale di 2 m
ogni 1000 ovaiole;',49,true,5); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli spazi all''aperto:',50,true,5); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- hanno (al fine di prevenire qualsiasi
contaminazione) una superficie adeguata alla densita''
di ovaiole allevate e alla natura del suolo;',51,true,5); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- sono provvisti di riparo dalle intemperie e dai
predatori e (se necessario) e di abbeveratoi
appropriati.',52,true,5); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Nei periodi di luce tutti gli edifici sono dotati di
un''illuminazione sufficiente per consentire alle
galline di vedersi e di essere viste chiaramente, di
guardarsi intorno e di muoversi normalmente.',53,true,6);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Dopo i primi giorni di adattamento il regime previsto
e'' tale da evitare problemi di salute e di
comportamento, e'' pertanto seguito un ciclo di 24 ore
che comprendere un periodo di oscurita'' sufficiente e
ininterrotto (a titolo indicativo tale periodo e'' pari a
circa un terzo della giornata, per consentire alle
galline di riposarsi ed evitare problemi quali
immunodepressione e anomalie oculari).',54,true,6);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In concomitanza con la diminuzione della luce e'' 
    rispettato un periodo di penombra di durata sufficiente per consentire alle galline di sistemarsi
    senza confusione o ferie',55,true,6);
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Nel caso di illuminazione naturale, le aperture per la
luce sono disposte in modo da ripartirla
uniformemente nei locali di allevamento.',56,true,6); 

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I sistemi produttivi sono sistemati in modo da ridurre
al minimo possibile il livello sonoro e da evitare
rumori di fondo od improvvisi.',57,true,7); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti apparecchiature per il rilevamento
della T&deg; e dell''UR.',58,true,7); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La costruzione, l''installazione, la manutenzione e il
funzionamento dei ventilatori, dei dispositivi di
alimentazione e di altre attrezzature devono essere
tali da provocare il minimo rumore possibile.',59,true,7); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In caso di guasto all''impianto e'' previsto un sistema di
allarme che segnali il guasto.
Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',60,true,7); 
        
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',61,true,7); 


--iniziare capitolo 8    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',62,true,8); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',63,true,8); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('L''alimentazione e'' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche delle
ovaiole.',64,true,8); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Viene garantito ad ogni singolo soggetto l''accesso
agli alimenti contemporaneamente o con un sistema
di somministrazione dell''alimento tale da ridurre le
aggressioni anche in presenza di competitivita''.',65,true,8); 


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Viene fornita costantemente acqua fresca in quantita''
sufficiente e di qualita''.',66,true,8); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.L.gs. 146/2001:',67,true,9); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('a) non vengono praticati interventi che provocano o
possano provocare agli animali sofferenze o lesioni;',68,true,9); 


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('b) il taglio delle ali e la bruciatura dei tendini, se
necessari sono eseguiti esclusivamente a fini
terapeutici dei quali esiste idonea documentazione.',69,true,9); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Rispetto delle pertinenti disposizioni di cui al
D.L.gs. 267/2003 e succ integr. e modif, ovvero:',70,true,9); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('6. non vengono praticate mutilazioni.',71,true,9); 


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('7. il taglio del becco, consentito solo per
comprovate e documentate esigenze per evitare
plumofagia e cannibalismo, viene effettuato da
personale qualificato sotto la responsabilita'' di un
medico veterinario, su pulcini di eta'' inferiore a dieci
giorni.',72,true,9); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non sono praticati, nell''allevamento naturale o
artificiale, procedimenti di allevamento ed interventi
che provochino o possano provocare agli animali
sofferenze o lesioni.',73,true,10); 

---------------------------------MODULO SUINI ALLEGATO 2 --------------------------------------

--11-24 gli id dei capitoli
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0 , true, 11);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative
    (art.3 e allegato al D.Lgs. 53/2004).',1, true, 11);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2, true, 11);
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali nei locali di stabulazione sono ispezionati
almeno 1 volta/di''.',3,true,12);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',4, true, 12);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti recinti individuali nei quali possono
essere temporaneamente tenuti i suini (soggetti con
problemi comportamentali, particolarmente aggressivi, 
che sono stati attaccati da altri suini, o che
sono malati o feriti ecc.)',5,true,12);    
    


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali malati o feriti ricevono immediatamente
un trattamento appropriato.',6,true,12);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il recinto individuale di isolamento ha dimensioni
adeguate e permette all''animale di girarsi facilmente
e di avere contatti visivi ed olfattivi con gli altri
suini, salvo nel caso in cui cio'' non sia in
contraddizione con specifiche prescrizioni
veterinarie.',7,true,12);
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I suini sono divisi in gruppi omogenei per sesso eta'' e
categoria (verri, scrofe e scrofette, lattonzoli, suinetti
e suini all''ingrasso);',8,true,12);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('SCROFE E SCROFETTE:
- se necessario, sono sottoposte a trattamenti contro i
parassiti interni ed esterni;',9,true,12);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- vengono pulite se sistemate negli stalli da parto.',10,true,12);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('SUINETTI E SUINI ALL''INGRASSO:
    - Quando sono tenuti in gruppo vengono prese
sufficienti misure per evitare lotte che vadano oltre il
comportamento normale;',11,true,12);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- La formazione dei gruppi avviene con il minimo
possibile di commistione (mescolamento di suini che
non si conoscono);',12,true,12);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- Qualora necessaria, la modificazione dei gruppi
avviene di preferenza prima dello svezzamento o
entro una settimana dallo svezzamento;',13,true,12);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- I suini dispongono di spazi adeguati per allontanarsi
e nascondersi dagli altri;',14,true,12);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- Sono state adottate idonee misure (ad es. fornire
agli animali abbondante paglia o altro materiale per
esplorazione) a seguito di manifesti segni di lotta
violenta;',15,true,12);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- Gli animali a rischio o particolarmente aggressivi
sono tenuti separati dal gruppo;',16,true,12);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- La somministrazione di tranquillanti avviene solo in
casi eccezionali e dietro prescrizione di un medico
veterinario.',17,true,12);       

--cap 3
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro dei trattamenti farmacologici
ed e'' conforme.',18,true,13);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro di carico e scarico e la
mortalita'' e'' regolarmente registrata.',19,true,13);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un piano di autocontrollo /buone pratiche
di allevamento.',20,true,13);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',21,true,13);     

--cap 4
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La liberta'' di movimento dell''animale non e'' limitata
in modo tale da causargli inutili sofferenze o lesioni.',22,true,14);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' rispettato il divieto di utilizzo di attacchi per le
scrofe e le scrofette (in vigore in Italia dal 1&deg gennaio
2001).',23,true,14);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli attacchi eventualmente utilizzati per gli altri suini
non provocano lesioni e consentono ai suini di
assumere una posizione confortevole durante
l''assunzione dell''alimento, di giacere ed alzarsi, non
provocano strangolamenti o ferite, sono regolarmente
esaminati, aggiustati o sostituiti se danneggiati.',24,true,14);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('SCROFE E SCROFETTE:
- sono adottate misure per ridurre al minimo le
aggressioni nei gruppi.',25,true,14);      

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- Dietro alla scrofa o alla scrofetta e'' prevista una
zona libera che rende agevole il parto naturale o
assistito.',26,true,14); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- Gli stalli da parto, in cui le scrofe possono muoversi
liberamente, sono provvisti di strutture per
proteggere i lattonzoli ad es. apposite sbarre.',27,true,14); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('LATTONZOLI:
- una parte del pavimento e'' sufficientemente ampia
da consentire agli animali di coricarsi e riposare
contemporaneamente.',28,true,14); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- Questa superficie e'' piena o ricoperta da un
tappetino, da paglia o da altro materiale adeguato;',29,true,14); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- Vi e'' una idonea fonte di calore;',30,true,14); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- Nel caso si usi uno stallo da parto i lattonzoli
dispongono di spazio sufficiente per essere allattati
senza difficolta''.',31,true,14); 

--cap 5
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le superfici libere a disposizione di ciascun
SUINETTO O SUINO ALL''INGRASSO
ALLEVATO IN GRUPPO (escluse le scrofette dopo
la fecondazione e le scrofe) corrispondono ad
almeno:
-----------------------------------
Peso vivo kg mq
-----------------------------------
Fino a 10 0,15
Oltre 10 fino a 20 0,20
Oltre a 20 fino a 30 0,30
Oltre a 30 fino a 50 0.40
Oltre a 50 fino a 85 0,55
Oltre a 85 fino a 110 0.65
Oltre 110 1.00
-----------------------------------',32,true,15); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Disposizioni applicabili a partire dal 15 marzo 2004
nelle aziende nuove o ricostruite o adibite
all''allevamento del suino per la prima volta dopo
l''entrata in vigore del D.Lgs. 53/2004 e dal 1&deg
gennaio 2013 in tutte le aziende:
Le superfici libere totali a disposizione di ciascuna
SCROFETTA E SCROFA ALLEVATE IN
GRUPPO sono di:
- 1,64 mq per ciascuna scrofetta dopo la
fecondazione;
- 2,25 mq per ciascuna scrofa;',33,true,15); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se le scrofette dopo la fecondazione e le scrofe sono
allevate in gruppi di:
- meno di sei animali le superfici libere disponibili
devono essere aumentate del 10%;
- 40 o piu'' animali le superfici libere disponibili
possono essere ridotte del 10 %;',34,true,15);     

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Nel periodo compreso tra quattro settimane dopo la
fecondazione e una settimana prima della data
prevista per il parto le scrofe e le scrofette sono
allevate in gruppo
- i lati del recinto dove viene allevato il gruppo di
scrofe o di scrofette hanno una lunghezza superiore a
2,8 m
- se sono allevati meno di 6 animali i lati del recinto
hanno una lunghezza superiore a 2,4 m.',35,true,15);   

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Deroga per la aziende con meno di 10 scrofe:
- le scrofe e le scrofette sono allevate
individualmente nel periodo compreso tra quattro
settimane dopo la fecondazione e una settimana
prima della data prevista per il parto;
- in tal caso gli animali possono girarsi facilmente nel
recinto;',36,true,15);   

--cap 6
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I materiali e le attrezzature con i quali gli animali
possono venire a contatto non sono nocivi per gli
animali.',37,true,16);   
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non vi sono spigoli taglienti o sporgenze.',38,true,16);   

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa dell''aria e le
concentrazioni di gas sono mantenute entro limiti
non dannosi per gli animali:
- all''atto dell''ispezione T&deg e UR sono adeguate alle
esigenze etologiche della specie e all''eta'' degli
animali.',39,true,16);   

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Rumori - dove sono stabulati i suini sono evitati i
rumori continui di intensita'' pari a 85 dBA, i rumori
costanti ed improvvisi.',40,true,16);   

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali di stabulazione sono costruiti in modo di
permettere agli animali di:
- avere accesso ad una zona in cui coricarsi,
confortevole dal punto di vista fisico e termico,
adeguatamente prosciugata e pulita ed in cui tutti gli
animali possono stare distesi contemporaneamente;',41,true,16);   

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- riposare ed alzarsi con movimenti normali, vedere
altri suini (scrofe e scrofette nella settimana che
precede il parto e durante il parto stesso possono
essere tenute fuori dalla vista degli altri animali).',42,true,16);   

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il locale/recinto infermeria e'' chiaramente identificato
e con presenza permanente di lettiera asciutta e acqua
fresca in quantita'' sufficiente.',43,true,16);   

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario.',44,true,16);   

--cap 7
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Dove sono stabulati i suini e'' assicurata la luce di
intensita'' di almeno 40 lux per un periodo minimo di
8 ore al giorno.',45,true,17);   

--cap 8

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I pavimenti:
- non sono sdrucciolevoli e non hanno asperita'' che
possono provocare lesioni ai suini;',46,true,18);   

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- sono costruiti e mantenuti in modo da non arrecare
lesioni o sofferenze agli animali;',47,true,18);   

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- sono adeguati alle dimensioni ed al peso dei suini;',48,true,18);   

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- se non e'' prevista una lettiera i pavimenti sono a
superficie rigida, piana e stabile;',49,true,18);   

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- gli escrementi, l''urina e i foraggi non mangiati o
caduti sono rimossi con regolarita'' per ridurre al
minimo gli odori e la presenza di mosche o roditori.',50,true,18);   

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- la superficie libera al suolo minima del recinto per
VERRO ADULTO e'' di 6 mq;',51,true,18);   


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- nel recinto il verro si puo'' girare ed avere contatti
uditivi, olfattivi e visivi con altri suini;',52,true,18);   

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- se il recinto viene utilizzato anche per
l''accoppiamento la superficie al suolo e'' di almeno 10
mq ed e'' libero da ostacoli.',53,true,18);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Disposizioni applicabili a partire dal 15 marzo 2004
nelle aziende nuove o ricostruite o adibite
all''allevamento del suino per la prima volta dopo
l''entrata in vigore del D.Lgs. 53/2004 e dal 1&deg
gennaio 2013 in tutte le aziende:
- SCROFETTE DOPO LA FECONDAZIONE E
SCROFE GRAVIDE - una parte della superficie
libera totale a disposizione per ciascuna e'' costituita
da pavimento pieno continuo:
1) di almeno 0,95 mq per ogni scrofetta;',54,true,18);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('2) di almeno 1,3 mq per ogni scrofa.',55,true,18); 
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Una parte di tale pavimento (non superiore al 15%) e''
riservata alle aperture di scarico (griglie, tombini
ecc.).',56,true,18); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I pavimenti fessurati in calcestruzzo per SUINI
ALLEVATI IN GRUPPO hanno:
a) l''ampiezza massima delle aperture di:
- 11 mm per i lattonzoli;',57,true,18); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- 14 mm per i suinetti;',58,true,18); 
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- 18 mm per i suini all''ingrasso;',59,true,18); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- 20 mm per le scrofette dopo la
fecondazione e le scrofe;',60,true,18);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('b) l''ampiezza minima dei travetti:
- 50 mm per i lattonzoli e i suinetti;',61,true,18);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- 80 mm per i suini all''ingrasso, le scrofette
dopo la fecondazione e le scrofe.',62,true,18);

--cap 9
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I suini (fermo restando quanto previsto all''art. 3,
comma 5 per scrofe e scrofette) hanno accesso ad
una quantita'' sufficiente di materiale che consente
loro adeguate attivita'' di esplorazione e
manipolazione (ad es. paglia, fieno, legno, segatura,
composti di funghi, torba o un loro miscuglio, etc.) -
salvo che il loro uso possa compromettere la salute o
il benessere degli animali.
- indicare il materiale manipolabile utilizzato (paglia,
fieno, segatura, composti di funghi, torba, materiale
grossolano quale legnoo altro) specificare...',63,true,19);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- indicare eventualmente il motivo dell''assenza del
materiale manipolabile...',64,true,19);
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('SCROFE e SCROFETTE nella settimana precedente
il parto dispongono di lettiera adeguata in quantita''
sufficiente (tranne nel caso in cui sia tecnicamente
irrealizzabile per il sistema di eliminazione dei
liquami).',65,true,19);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Disposizioni applicabili a partire dal 15 marzo 2004
nelle aziende nuove o ricostruite o adibite
all''allevamento del suino per la prima volta dopo
l''entrata in vigore del D.Lgs. 53/2004 e dal 1&deg
gennaio 2013 in tutte le aziende.
Le SCROFE e SCROFETTE hanno accesso
permanente al materiale manipolabile che soddisfi
almeno i pertinenti requisiti elencati nell''allegato al
D.Lgs. 53/2004.
- indicare il materiale manipolabile utilizzato (paglia,
fieno, segatura, composti di funghi, torba, materiale
grossolano quale legno o altro) specificare...',66,true,19);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- indicare eventualmente il motivo dell''assenza del
materiale manipolabile.',67,true,19);

--cap 10
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',68,true,20);
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',69,true,20);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Tutti i suini sono nutriti almeno una volta al giorno.',70,true,20);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se sono alimentati in gruppo e non ad libitum o
mediante un sistema automatico di alimentazione
individuale, ciascun suino ha accesso agli alimenti
contemporaneamente agli altri suini del gruppo.',71,true,20);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le SCROFE e le SCROFETTE ALLEVATE in
GRUPPO sono alimentate utilizzando un sistema
idoneo a garantire che ciascun animale ottenga
mangime a sufficienza senza essere aggredito, anche
in situazione di competitivita''.',72,true,20);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('L''alimentazionee'' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche delle
diverse categorie animali.',73,true,20);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('A partire dalla seconda settimana di eta'', ogni suino
dispone in permanenza di acqua fresca di qualita'' ed
in quantita'' sufficiente.',74,true,20);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- i secchi, i poppatoi, le mangiatoie sono puliti
dopo ogni utilizzo e sottoposti a periodica disinfezione;',75,true,20);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- ogni alimento avanzato viene rimosso regolarmente;',76,true,20);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- le attrezzature per l''alimentazione automatica 
sono pulite regolarmente e frequentemente,
smontando le parti in cui si depositano residui di
alimento.',77,true,20);

--cap 11
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Per calmare la fame e tenuto conto del bisogno di
masticare tutte le SCROFE e le SCROFETTE
ASCIUTTE GRAVIDE ricevono mangime
riempitivo o ricco di fibre in quantita'' sufficiente ed
alimenti ad alto tenore energetico.',78,true,21);

--cap 12
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.Lgs. 146/2001, punto 19 ed
all''allegato al D.Lgs. 534/1992, Capitolo I, punto 8.
Sono praticate:
a. la riduzione uniforme degli incisivi dei lattonzoli
entro i primi 7 giorni di vita, mediante levigatura o
troncatura che lasci una superficie liscia intatta;',79,true,22);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('b. la riduzione delle zanne dei verri, se necessaria,
per evitare lesioni agli altri animali o per motivi di
sicurezza;',80,true,22);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('c. il mozzamento di una parte della coda entro i
primi 7 giorni di vita;',81,true,22);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('d. la castrazione dei suini di sesso maschile con
mezzi diversi dalla lacerazione dei tessuti entro i
primi 7 giorni di vita;',82,true,22);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('e. l''apposizione di un anello al naso, (ammesso solo
quando gli animali sono detenuti in allevamenti
all''aperto);',83,true,22);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Tutte queste operazioni sono praticate da un
veterinario o da altro personale specializzato (art. 5
bis) con tecniche e mezzi adeguati ed in condizioni
igieniche.',84,true,22);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se la castrazione o il mozzamento della coda sono
praticati dopo il 7&deg giorno di vita, sono eseguiti sotto
anestesia e con somministrazione prolungata di
analgesici, unicamente da un medico veterinario.',85,true,22);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- il mozzamento della coda e la riduzione degli
incisivi dei lattonzoli non costituiscono operazioni di
routine, ma sono praticati soltanto se sono
comprovate lesioni ai capezzoli delle scrofe, agli
orecchi o alle code dei suinetti e dopo aver adottato
misure intese ad evitare le morsicature delle code ed
altri comportamenti anormali (tenendo conto delle
condizioni ambientali e della densita'').',86,true,22);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- e'' necessario che vi sia documentazione della
comprovata esigenza di tali pratiche (dichiarazione di
un medico veterinario).',87,true,22);

 --cap 13   
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni
(questa disposizione non impedisce il ricorso a taluni
procedimenti che possono causare sofferenze o ferite
minime o momentanee o richiedere interventi che
non causano lesioni durevoli, se consentiti dalle
disposizioni vigenti).',88,true,23);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti.',89,true,23);

 INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('LATTONZOLI:
- nessuno di essi viene staccato dalla scrofa prima
dei 28 giorni d''eta''(tranne vi sia influenza negativa
per la madre o il lattonzolo stesso);',90,true,23);

 INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- i lattonzoli sono svezzati prima dei 28 previsti
max 7 giorni prima (21 gg) ma vengono trasferiti in
impianti specializzati;',91,true,23);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- gli impianti specializzati vengono svuotati, puliti
e disinfettati prima dell''introduzione di un nuovo
gruppo ;',92,true,23);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- gli impianti specializzati sono separati dagli
impianti in cui sono tenute le scrofe (per ridurre i
rischi di malattie ai piccoli).',93,true,23);

--cap.14
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali.',94,true,24);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In caso di guasto all''impianto e'' previsto un sistema di
allarme che segnali il guasto.',95,true,24);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',96,true,24);

 INSERT INTO chk_bns_dom(
         description, level, enabled, idcap)
    VALUES ('Sono presenti apparecchiature per il rilevamento
della T&deg e dell''UR.',97,true,24);


--------------------ALLEGATO 3 MODULO VITELLI----------------------------

--25-37 gli id dei capitoli
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0, true, 25);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative',1, true, 25);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2, true, 25);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali nei locali di stabulazione sono ispezionati
almeno 2 volte/di'' (1 volta/di'' se stabulati all''aperto).',3, true, 26);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',4, true, 26);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES (' Sono presenti recinti/locali di isolamento con lettiera
asciutta e confortevole.',5,true,26);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali malati o feriti vengono isolati e ricevono
immediatamente un trattamento appropriato.',6,true,26);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In caso di necessita' viene consultato un medico
veterinario.',7,true,26);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il recinto individuale di isolamento ha dimensioni
adeguate e permette all''animale di girarsi facilmente
e di avere contatti visivi ed olfattivi con gli altri
animali salvo nel caso in cui cio'' non sia in
contraddizione con specifiche prescrizioni
veterinarie.',8,true,26);
    
--cap 27

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro dei trattamenti farmacologici
ed e'' conforme.',9,true,27);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro di carico e scarico e la
mortalita'' e'' regolarmente registrata.',10,true,27);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono correttamente identificati e registrati.',11,true,27);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un piano di autocontrollo/buone pratiche
di allevamento.',12,true,27);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' tenuta una registrazione dei prelievi per il dosaggio dell''HB.',13,true,27); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',14,true,27);

--cap 28
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Lo spazio a disposizione di ogni animale e'' sufficiente
per consentirgli un''adeguata liberta'' di movimenti ed
e'' tale da non causargli inutili sofferenze o lesioni.',15,true,28);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali di stabulazione sono costruiti in modo di
permettere agli animali di coricarsi, giacere in
decubito, alzarsi ed accudire se stessi senza
difficolta''',16,true,28);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I vitelli non vengono legati ad eccezione di quelli
allevati in gruppo al momento della
somministrazione del latte o suoi succedanei per un
periodo massimo di 1 ora.',17,true,28);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli attacchi eventualmente utilizzati non provocano
lesioni e consentono agli animali di assumere una
posizione confortevole, di giacere ed alzarsi, non
provocano strangolamenti o ferite, sono regolarmente
esaminati, aggiustati o sostituiti se danneggiati',18,true,28);       

--cap 29
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I vitelli di eta'' superiore alle 8 settimane non sono
allevati in recinti individuali.',19,true,29);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti vitelli di eta'' superiore alle 8 settimane
rinchiusi in recinti individuali per motivi sanitari o
comportamentali certificati da un medico veterinario
esclusivamente per il periodo necessario.',20,true,29);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I recinti individuali di isolamento hanno dimensioni
adeguate e conformi alle disposizioni vigenti, le
pareti divisorie non sono costituite da muri compatti,
ma sono traforate, salvo nel caso in cui sia necessario
isolare i vitelli.',21,true,29);
           
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Lo spazio libero disponibile per ciascun vitello
allevato in gruppo e'' di almeno:
- mq 1,5 per vitelli di p. v. <150 Kg;',22,true,29);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- mq 1,7 per vitelli di p. v. >150 Kg e < 220Kg;',23,true,29);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- mq 1,8 per vitelli di p. v. >220 Kg.',24,true,29);       
    
--cap 30
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I materiali di costruzione, i recinti e le attrezzature
con i quali gli animali possono venire a contatto non
sono nocivi per gli animali stessi, non vi sono spigoli
taglienti o sporgenze, tutte le superfici sono
facilmente lavabili e disinfettabili.',25,true,30);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali stabulati all''aperto dispongono di un
riparo adeguato.',26,true,30);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le apparecchiature e gli impianti elettrici sono
costruiti in modo da evitare scosse elettriche e sono
conformi alle norme vigenti in materia.',27,true,30);     

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa dell''aria e le
concentrazioni di gas sono mantenute entro limiti
non dannosi per gli animali - all''atto dell''ispezione
T&deg e UR sono adeguate alle esigenze etologiche della
specie e all''eta'' degli animali.',28,true,30);  

--
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I secchi, i poppatoi, le mangiatoie sono puliti dopo
ogni utilizzo e sottoposti a periodica disinfezione
ogni alimento avanzato viene rimosso.',29,true,30);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le attrezzature per l''alimentazione automatica sono
pulite regolarmente e frequentemente, smontando le
parti in cui si depositano residui di alimento.',30,true,30);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli escrementi, l''urina i foraggi non mangiati o caduti
sono rimossi con regolarita''.',31,true,30);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I pavimenti non sono sdrucciolevoli e non hanno
asperita' tali da provocare lesioni, sono costruiti e
mantenuti in maniera tale da non arrecare sofferenza
o lesioni alle zampe e sono adeguati alle dimensioni
ed al peso dei vitelli.',32,true,30);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La zona in cui i vitelli si coricano e'' confortevole,
pulita e ben drenata.',33,true,30);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente la lettiera (obbligatoria per vitelli < 2
settimane vita).',34,true,30);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il locale/recinto infermeria e'' chiaramente identificato
e con presenza permanente di lettiera asciutta e acqua
fresca in quantita'' sufficiente.',35,true,30);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario',36,true,30);

--cap 7 31
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali custoditi nei fabbricati non sono tenuti
costantemente al buio, ad essi sono garantiti un
adeguato periodo di luce (naturale o artificiale) ed
un adeguato periodo di riposo.',37,true,31);

--cap.32(6)
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',38,true,32);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti idonei dispositivi per la
somministrazione di acqua nei periodi di intenso
calore.',39,true,32);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti impianti automatici per la
somministrazione del mangime.',40,true,32);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono prese misure adeguate per salvaguardare la
salute ed il benessere degli animali in caso di non
funzionamento degli impianti (es. metodi alternativi
di alimentazione).',41,true,32);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali in caso di guasto
all''impianto stesso.',42,true,32);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' previsto un sistema di allarme che segnali
eventuali guasti.',43,true,32);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti apparecchiature per il rilevamento
della T&deg e dell''UR.',44,true,32);

--cap 33---------------------------------------------------------------------da qui!
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',45,true,33);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',46,true,33);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
   VALUES ('L''alimentazione e'' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche dei vitelli.',47,true,33);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se non viene praticata l''alimentazione ad libitum o
con sistemi automatici e'' assicurato l''accesso agli
alimenti a tutti i vitelli del gruppo contemporaneamente.',48,true,33);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('A partire dalla seconda settimana di eta'', ogni vitello
dispone di acqua fresca di qualita'' ed in quantita''
sufficiente o puo'' soddisfare il proprio fabbisogno di
liquidi con altre bevande.',49,true,33);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La modalita' di somministrazione dell''acqua consente
una adeguata idratazione degli animali anche nei
periodi di intenso calore.',50,true,33);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I vitelli ricevono il colostro entro le prime 6 ore di vita.',51,true,33);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Vengono effettuate verifiche sul grado di colostratura
e sulla qualita'' del colostro.',52,true,33);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il colostro proviene da bovine sane della stessa
azienda.',53,true,33);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il colostro e'' sottoposto a trattamenti di risanamento
in caso di insufficiente stato sanitario delle bovine
presenti in azienda.',54,true,33);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Esiste una banca aziendale del colostro.',55,true,33);

--cap 34    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('L''alimentazione e'' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche dei
vitelli.',56,true,34);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La razione alimentare ha un contenuto in ferro
sufficiente ad assicurare un tenore di HB di almeno
4,5 mmol/l (pari a 7,25 g/dl).',57,true,34);

--cap 35
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Dalla seconda settimana di eta'' e'' somministrata una
quantita'' adeguata di alimenti fibrosi (quantitativo
portato da 50 a 250 grammi al giorno per i vitelli di
eta'' compresa tra 8 e 20 settimane).',58,true,35);    

--cap 36    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.Lgs. 146/2001, punto 19 sono praticate:
- la cauterizzazione dell''abbozzo corneale entro le
tre settimane di vita sotto controllo veterinario;',59,true,36);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('- il taglio della coda se necessario eseguito da un
medico veterinario esclusivamente a fini terapeutici
dei quali esiste idonea documentazione.',60,true,36);

--cap 37
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni
(questa disposizione non impedisce il ricorso a taluni
procedimenti che possono causare sofferenze o ferite
minime o momentanee o richiedere interventi che
non causano lesioni durevoli, se consentiti dalle
disposizioni vigenti).',61,true,37);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I fabbricati, i recinti, le attrezzature e gli utensili sono
puliti e disinfettati regolarmente.',62,true,37);    
        
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti.',63,true,37);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' rispettato il divieto di mettere la museruola ai
vitelli.',64,true,37); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I vitelli all''arrivo in azienda o in partenza da essa
hanno piu'' di 10 giorni di vita (cicatrizzazione
ombelico esterno completa).',65,true,37); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In un numero significativo di soggetti si evidenziano
comportamenti anomali (succhiamentio reciproco,
movimenti della lingua) o fenomeni di meteorismo.',66,true,37); 
          
---------------------ALLEGATO 4 MODULO FAGIANI-------------------------


--38-46 gli id dei capitoli
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0, true, 38);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative',1, true, 38);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2, true, 38);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Indicare da chi sono stati organizzati i corsi
    (Regione, ASL, Associazioni di categoria ecc...)',3, true, 38);


--cap 39    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono ispezionati almeno 1 volta/di''.',4,true,39);



INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',5, true, 39);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES (' Sono presenti recinti/locali di isolamento con lettiera
asciutta e confortevole.',6,true,39);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali malati o feriti vengono isolati e ricevono
immediatamente un trattamento appropriato.',7,true,39);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In caso di necessita' viene consultato un medico
veterinario.',8,true,39);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il recinto individuale di isolamento ha dimensioni
adeguate e permette all''animale di girarsi facilmente
e di avere contatti visivi ed olfattivi con gli altri
animali salvo nel caso in cui cio non sia in
contraddizione con specifiche prescrizioni
veterinarie.',9,true,39);
    
--cap 40

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro dei trattamenti farmacologici
ed e'' conforme.',10,true,40);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro di carico e scarico e la
mortalita'' e'' regolarmente registrata.',11,true,40);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono correttamente identificati e registrati
(se previsto dalla normativa).',12,true,40);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un piano di autocontrollo/buone pratiche
di allevamento.',13,true,40);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',14,true,40);

--cap41

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Lo spazio a disposizione di ogni animale e'' sufficiente
per consentirgli un''adeguata liberta'' di movimenti ed
e'' tale da non causargli inutili sofferenze o lesioni.',15,true,41);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali di stabulazione sono costruiti in modo di
permettere agli animali di coricarsi, giacere in
decubito, alzarsi ed accudire se stessi senza
difficolta''..',16,true,41);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli attacchi eventualmente utilizzati non provocano
lesioni e consentono agli animali di assumere una
posizione confortevole, di giacere ed alzarsi, non
provocano strangolamenti o ferite, sono regolarmente
esaminati, aggiustati o sostituiti se danneggiati',17,true,41);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I recinti di isolamento hanno dimensioni adeguate e
conformi alle disposizioni vigenti.',18,true,41);       

--cap 42

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I materiali di costruzione, i recinti e le attrezzature
con i quali gli animali possono venire a contatto non
sono nocivi per gli animali stessi, non vi sono spigoli
taglienti o sporgenze, tutte le superfici sono
facilmente lavabili e disinfettabili.',19,true,42);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali stabulati all''aperto dispongono di un
riparo adeguato.',20,true,42);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le apparecchiature e gli impianti elettrici sono
costruiti in modo da evitare scosse elettriche e sono
conformi alle norme vigenti in materia.',21,true,42);     

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa dell''aria e le
concentrazioni di gas sono mantenute entro limiti
non dannosi per gli animali - all''atto dell''ispezione
T&deg e UR sono adeguate alle esigenze etologiche della
specie e all''eta'' degli animali.',22,true,42);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le attrezzature per l''alimentazione automatica sono
pulite regolarmente e frequentemente, smontando le
parti in cui si depositano residui di alimento.',23,true,42);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli escrementi, l''urina i foraggi non mangiati o caduti
sono rimossi con regolarita''.',24,true,42);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I pavimenti non sono sdrucciolevoli e non hanno
asperita' tali da provocare lesioni, sono costruiti e
mantenuti in maniera tale da non arrecare sofferenza
o lesioni alle zampe e sono adeguati alle dimensioni
ed al peso dei vitelli.',25,true,42);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un locale/recinto infermeria chiaramente
identificato e con presenza permanente di lettiera
asciutta e acqua fresca in quantita'' sufficiente.',26,true,42);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario.',27,true,42);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali custoditi nei fabbricati non sono tenuti
costantemente al buio, ad essi sono garantiti un
adeguato periodo di luce (naturale o artificiale) ed
un adeguato periodo di riposo.',28,true,42);

--cap.43 (6)
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',29,true,43);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti idonei dispositivi per la
somministrazione di acqua nei periodi di intenso
calore.',30,true,43);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti impianti automatici per la
somministrazione del mangime.',31,true,43);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono prese misure adeguate per salvaguardare la
salute ed il benessere degli animali in caso di non
funzionamento degli impianti (es. metodi alternativi
di alimentazione).',32,true,43);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali in caso di guasto
all''impianto stesso.',33,true,43);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' previsto un sistema di allarme che segnali
eventuali guasti.',34,true,43);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti apparecchiature per il rilevamento
della T&deg e dell''UR.',35,true,43);

--cap 44
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',36,true,44);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',37,true,44);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
   VALUES ('L''alimentazione e' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche dei vitelli.',38,true,44);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se non viene praticata l''alimentazione ad libitum o
con sistemi automatici e'' assicurato l''accesso agli
alimenti a tutti gli animali contemporaneamente per
evitare competizioni.',39,true,44);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La modalita'' di somministrazione dell''acqua consente
una adeguata idratazione degli animali anche nei
periodi di intenso calore.',40,true,44);

--cap 45
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.Lgs. 146/2001, punto 19.',41,true,45);    
    
--cap 46    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni
(questa disposizione non impedisce il ricorso a taluni
procedimenti che possono causare sofferenze o ferite
minime o momentanee o richiedere interventi che
non causano lesioni durevoli, se consentiti dalle
disposizioni vigenti).',42,true,46);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I fabbricati, i recinti, le attrezzature e gli utensili sono
puliti e disinfettati regolarmente.',43,true,46);    
        
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti.',44,true,46);    
         
------------------------------------------ MODULO 4 CAPRINI ---------------------------------------

--47-55 gli id dei capitoli
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0, true, 47);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative',1, true, 47);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2, true, 47);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Indicare da chi sono stati organizzati i corsi
    (Regione, ASL, Associazioni di categoria ecc...)',3, true, 47);


--cap 48    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono ispezionati almeno 1 volta/di''.',4,true,48);



INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',5, true, 48);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES (' Sono presenti recinti/locali di isolamento con lettiera
asciutta e confortevole.',6,true,48);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali malati o feriti vengono isolati e ricevono
immediatamente un trattamento appropriato.',7,true,48);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In caso di necessita' viene consultato un medico
veterinario.',8,true,48);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il recinto individuale di isolamento ha dimensioni
adeguate e permette all''animale di girarsi facilmente
e di avere contatti visivi ed olfattivi con gli altri
animali salvo nel caso in cui cio non sia in
contraddizione con specifiche prescrizioni
veterinarie.',9,true,48);
    
--cap 49

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro dei trattamenti farmacologici
ed e'' conforme.',10,true,49);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro di carico e scarico e la
mortalita'' e'' regolarmente registrata.',11,true,49);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono correttamente identificati e registrati
(se previsto dalla normativa).',12,true,49);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un piano di autocontrollo/buone pratiche
di allevamento.',13,true,49);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',14,true,49);

--cap50

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Lo spazio a disposizione di ogni animale e'' sufficiente
per consentirgli un''adeguata liberta'' di movimenti ed
e'' tale da non causargli inutili sofferenze o lesioni.',15,true,50);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali di stabulazione sono costruiti in modo di
permettere agli animali di coricarsi, giacere in
decubito, alzarsi ed accudire se stessi senza
difficolta''..',16,true,50);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli attacchi eventualmente utilizzati non provocano
lesioni e consentono agli animali di assumere una
posizione confortevole, di giacere ed alzarsi, non
provocano strangolamenti o ferite, sono regolarmente
esaminati, aggiustati o sostituiti se danneggiati',17,true,50);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I recinti di isolamento hanno dimensioni adeguate e
conformi alle disposizioni vigenti.',18,true,50);       

--cap 51

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I materiali di costruzione, i recinti e le attrezzature
con i quali gli animali possono venire a contatto non
sono nocivi per gli animali stessi, non vi sono spigoli
taglienti o sporgenze, tutte le superfici sono
facilmente lavabili e disinfettabili.',19,true,51);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali stabulati all''aperto dispongono di un
riparo adeguato.',20,true,51);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le apparecchiature e gli impianti elettrici sono
costruiti in modo da evitare scosse elettriche e sono
conformi alle norme vigenti in materia.',21,true,51);     

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa dell''aria e le
concentrazioni di gas sono mantenute entro limiti
non dannosi per gli animali - all''atto dell''ispezione
T&deg e UR sono adeguate alle esigenze etologiche della
specie e all''eta'' degli animali.',22,true,51);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le attrezzature per l''alimentazione automatica sono
pulite regolarmente e frequentemente, smontando le
parti in cui si depositano residui di alimento.',23,true,51);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli escrementi, l''urina i foraggi non mangiati o caduti
sono rimossi con regolarita''.',24,true,51);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I pavimenti non sono sdrucciolevoli e non hanno
asperita' tali da provocare lesioni, sono costruiti e
mantenuti in maniera tale da non arrecare sofferenza
o lesioni alle zampe e sono adeguati alle dimensioni
ed al peso dei vitelli.',25,true,51);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un locale/recinto infermeria chiaramente
identificato e con presenza permanente di lettiera
asciutta e acqua fresca in quantita'' sufficiente.',26,true,51);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario.',27,true,51);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali custoditi nei fabbricati non sono tenuti
costantemente al buio, ad essi sono garantiti un
adeguato periodo di luce (naturale o artificiale) ed
un adeguato periodo di riposo.',28,true,51);

--cap.52 (6)
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',29,true,52);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti idonei dispositivi per la
somministrazione di acqua nei periodi di intenso
calore.',30,true,52);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti impianti automatici per la
somministrazione del mangime.',31,true,52);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono prese misure adeguate per salvaguardare la
salute ed il benessere degli animali in caso di non
funzionamento degli impianti (es. metodi alternativi
di alimentazione).',32,true,52);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali in caso di guasto
all''impianto stesso.',33,true,52);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' previsto un sistema di allarme che segnali
eventuali guasti.',34,true,52);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti apparecchiature per il rilevamento
della T&deg e dell''UR.',35,true,52);

--cap 53
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',36,true,53);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',37,true,53);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
   VALUES ('L''alimentazione e' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche dei vitelli.',38,true,53);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se non viene praticata l''alimentazione ad libitum o
con sistemi automatici e'' assicurato l''accesso agli
alimenti a tutti gli animali contemporaneamente per
evitare competizioni.',39,true,53);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La modalita'' di somministrazione dell''acqua consente
una adeguata idratazione degli animali anche nei
periodi di intenso calore.',40,true,53);

--cap 54
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.Lgs. 146/2001, punto 19.',41,true,54);    
    
--cap 55    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni
(questa disposizione non impedisce il ricorso a taluni
procedimenti che possono causare sofferenze o ferite
minime o momentanee o richiedere interventi che
non causano lesioni durevoli, se consentiti dalle
disposizioni vigenti).',42,true,55);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I fabbricati, i recinti, le attrezzature e gli utensili sono
puliti e disinfettati regolarmente.',43,true,55);    
        
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti.',44,true,55);    

------------------------------------------ MODULO 4 AVICOLI ---------------------------------------

--56-64 gli id dei capitoli
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0, true, 56);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative',1, true, 56);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2, true, 56);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Indicare da chi sono stati organizzati i corsi
    (Regione, ASL, Associazioni di categoria ecc...)',3, true, 56);


--cap 57
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono ispezionati almeno 1 volta/di''.',4,true,57);



INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',5, true, 57);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES (' Sono presenti recinti/locali di isolamento con lettiera
asciutta e confortevole.',6,true,57);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali malati o feriti vengono isolati e ricevono
immediatamente un trattamento appropriato.',7,true,57);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In caso di necessita' viene consultato un medico
veterinario.',8,true,57);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il recinto individuale di isolamento ha dimensioni
adeguate e permette all''animale di girarsi facilmente
e di avere contatti visivi ed olfattivi con gli altri
animali salvo nel caso in cui cio non sia in
contraddizione con specifiche prescrizioni
veterinarie.',9,true,57);
    
--cap 58

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro dei trattamenti farmacologici
ed e'' conforme.',10,true,58);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro di carico e scarico e la
mortalita'' e'' regolarmente registrata.',11,true,58);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono correttamente identificati e registrati
(se previsto dalla normativa).',12,true,58);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un piano di autocontrollo/buone pratiche
di allevamento.',13,true,58);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',14,true,58);

--cap59
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Lo spazio a disposizione di ogni animale e'' sufficiente
per consentirgli un''adeguata liberta'' di movimenti ed
e'' tale da non causargli inutili sofferenze o lesioni.',15,true,59);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali di stabulazione sono costruiti in modo di
permettere agli animali di coricarsi, giacere in
decubito, alzarsi ed accudire se stessi senza
difficolta''..',16,true,59);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli attacchi eventualmente utilizzati non provocano
lesioni e consentono agli animali di assumere una
posizione confortevole, di giacere ed alzarsi, non
provocano strangolamenti o ferite, sono regolarmente
esaminati, aggiustati o sostituiti se danneggiati',17,true,59);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I recinti di isolamento hanno dimensioni adeguate e
conformi alle disposizioni vigenti.',18,true,59);       

--cap 60

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I materiali di costruzione, i recinti e le attrezzature
con i quali gli animali possono venire a contatto non
sono nocivi per gli animali stessi, non vi sono spigoli
taglienti o sporgenze, tutte le superfici sono
facilmente lavabili e disinfettabili.',19,true,60);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali stabulati all''aperto dispongono di un
riparo adeguato.',20,true,60);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le apparecchiature e gli impianti elettrici sono
costruiti in modo da evitare scosse elettriche e sono
conformi alle norme vigenti in materia.',21,true,60);     

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa dell''aria e le
concentrazioni di gas sono mantenute entro limiti
non dannosi per gli animali - all''atto dell''ispezione
T&deg e UR sono adeguate alle esigenze etologiche della
specie e all''eta'' degli animali.',22,true,60);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le attrezzature per l''alimentazione automatica sono
pulite regolarmente e frequentemente, smontando le
parti in cui si depositano residui di alimento.',23,true,60);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli escrementi, l''urina i foraggi non mangiati o caduti
sono rimossi con regolarita''.',24,true,60);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I pavimenti non sono sdrucciolevoli e non hanno
asperita' tali da provocare lesioni, sono costruiti e
mantenuti in maniera tale da non arrecare sofferenza
o lesioni alle zampe e sono adeguati alle dimensioni
ed al peso dei vitelli.',25,true,60);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un locale/recinto infermeria chiaramente
identificato e con presenza permanente di lettiera
asciutta e acqua fresca in quantita'' sufficiente.',26,true,60);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario.',27,true,60);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali custoditi nei fabbricati non sono tenuti
costantemente al buio, ad essi sono garantiti un
adeguato periodo di luce (naturale o artificiale) ed
un adeguato periodo di riposo.',28,true,60);

--cap.61 (6)
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',29,true,61);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti idonei dispositivi per la
somministrazione di acqua nei periodi di intenso
calore.',30,true,61);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti impianti automatici per la
somministrazione del mangime.',31,true,61);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono prese misure adeguate per salvaguardare la
salute ed il benessere degli animali in caso di non
funzionamento degli impianti (es. metodi alternativi
di alimentazione).',32,true,61);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali in caso di guasto
all''impianto stesso.',33,true,61);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' previsto un sistema di allarme che segnali
eventuali guasti.',34,true,61);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti apparecchiature per il rilevamento
della T&deg e dell''UR.',35,true,61);

--cap 62
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',36,true,62);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',37,true,62);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
   VALUES ('L''alimentazione e' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche dei vitelli.',38,true,62);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se non viene praticata l''alimentazione ad libitum o
con sistemi automatici e'' assicurato l''accesso agli
alimenti a tutti gli animali contemporaneamente per
evitare competizioni.',39,true,62);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La modalita'' di somministrazione dell''acqua consente
una adeguata idratazione degli animali anche nei
periodi di intenso calore.',40,true,62);

--cap 63
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.Lgs. 146/2001, punto 19.',41,true,63);    
    
--cap 64
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni
(questa disposizione non impedisce il ricorso a taluni
procedimenti che possono causare sofferenze o ferite
minime o momentanee o richiedere interventi che
non causano lesioni durevoli, se consentiti dalle
disposizioni vigenti).',42,true,64);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I fabbricati, i recinti, le attrezzature e gli utensili sono
puliti e disinfettati regolarmente.',43,true,64);    
        
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti.',44,true,64);    
  
------------------------------------------ MODULO 4 BUFALINI ---------------------------------------

--65-73 gli id dei capitoli
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0, true, 65);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative',1, true, 65);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2, true, 65);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Indicare da chi sono stati organizzati i corsi
    (Regione, ASL, Associazioni di categoria ecc...)',3, true, 65);


--cap 66
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono ispezionati almeno 1 volta/di''.',4,true,66);



INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',5, true, 66);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES (' Sono presenti recinti/locali di isolamento con lettiera
asciutta e confortevole.',6,true,66);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali malati o feriti vengono isolati e ricevono
immediatamente un trattamento appropriato.',7,true,66);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In caso di necessita' viene consultato un medico
veterinario.',8,true,66);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il recinto individuale di isolamento ha dimensioni
adeguate e permette all''animale di girarsi facilmente
e di avere contatti visivi ed olfattivi con gli altri
animali salvo nel caso in cui cio non sia in
contraddizione con specifiche prescrizioni
veterinarie.',9,true,66);
    
--cap 67

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro dei trattamenti farmacologici
ed e'' conforme.',10,true,67);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro di carico e scarico e la
mortalita'' e'' regolarmente registrata.',11,true,67);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono correttamente identificati e registrati
(se previsto dalla normativa).',12,true,67);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un piano di autocontrollo/buone pratiche
di allevamento.',13,true,67);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',14,true,67);

--cap68
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Lo spazio a disposizione di ogni animale e'' sufficiente
per consentirgli un''adeguata liberta'' di movimenti ed
e'' tale da non causargli inutili sofferenze o lesioni.',15,true,68);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali di stabulazione sono costruiti in modo di
permettere agli animali di coricarsi, giacere in
decubito, alzarsi ed accudire se stessi senza
difficolta''..',16,true,68);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli attacchi eventualmente utilizzati non provocano
lesioni e consentono agli animali di assumere una
posizione confortevole, di giacere ed alzarsi, non
provocano strangolamenti o ferite, sono regolarmente
esaminati, aggiustati o sostituiti se danneggiati',17,true,68);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I recinti di isolamento hanno dimensioni adeguate e
conformi alle disposizioni vigenti.',18,true,68);       

--cap 69

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I materiali di costruzione, i recinti e le attrezzature
con i quali gli animali possono venire a contatto non
sono nocivi per gli animali stessi, non vi sono spigoli
taglienti o sporgenze, tutte le superfici sono
facilmente lavabili e disinfettabili.',19,true,69);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali stabulati all''aperto dispongono di un
riparo adeguato.',20,true,69);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le apparecchiature e gli impianti elettrici sono
costruiti in modo da evitare scosse elettriche e sono
conformi alle norme vigenti in materia.',21,true,69);     

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa dell''aria e le
concentrazioni di gas sono mantenute entro limiti
non dannosi per gli animali - all''atto dell''ispezione
T&deg e UR sono adeguate alle esigenze etologiche della
specie e all''eta'' degli animali.',22,true,69);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le attrezzature per l''alimentazione automatica sono
pulite regolarmente e frequentemente, smontando le
parti in cui si depositano residui di alimento.',23,true,69);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli escrementi, l''urina i foraggi non mangiati o caduti
sono rimossi con regolarita''.',24,true,69);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I pavimenti non sono sdrucciolevoli e non hanno
asperita' tali da provocare lesioni, sono costruiti e
mantenuti in maniera tale da non arrecare sofferenza
o lesioni alle zampe e sono adeguati alle dimensioni
ed al peso dei vitelli.',25,true,69);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un locale/recinto infermeria chiaramente
identificato e con presenza permanente di lettiera
asciutta e acqua fresca in quantita'' sufficiente.',26,true,69);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario.',27,true,69);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali custoditi nei fabbricati non sono tenuti
costantemente al buio, ad essi sono garantiti un
adeguato periodo di luce (naturale o artificiale) ed
un adeguato periodo di riposo.',28,true,69);

--cap.70 (6)
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',29,true,70);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti idonei dispositivi per la
somministrazione di acqua nei periodi di intenso
calore.',30,true,70);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti impianti automatici per la
somministrazione del mangime.',31,true,70);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono prese misure adeguate per salvaguardare la
salute ed il benessere degli animali in caso di non
funzionamento degli impianti (es. metodi alternativi
di alimentazione).',32,true,70);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali in caso di guasto
all''impianto stesso.',33,true,70);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' previsto un sistema di allarme che segnali
eventuali guasti.',34,true,70);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti apparecchiature per il rilevamento
della T&deg e dell''UR.',35,true,70);

--cap 71
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',36,true,71);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',37,true,71);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
   VALUES ('L''alimentazione e' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche dei vitelli.',38,true,71);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se non viene praticata l''alimentazione ad libitum o
con sistemi automatici e'' assicurato l''accesso agli
alimenti a tutti gli animali contemporaneamente per
evitare competizioni.',39,true,71);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La modalita'' di somministrazione dell''acqua consente
una adeguata idratazione degli animali anche nei
periodi di intenso calore.',40,true,71);

--cap 72
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.Lgs. 146/2001, punto 19.',41,true,72);    
    
--cap 73
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni
(questa disposizione non impedisce il ricorso a taluni
procedimenti che possono causare sofferenze o ferite
minime o momentanee o richiedere interventi che
non causano lesioni durevoli, se consentiti dalle
disposizioni vigenti).',42,true,73);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I fabbricati, i recinti, le attrezzature e gli utensili sono
puliti e disinfettati regolarmente.',43,true,73);    
        
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti.',44,true,73);    
             
------------------------------------------ MODULO 4 BOVINI ---------------------------------------


--74-82 gli id dei capitoli
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0, true, 74);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative',1, true, 74);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2, true, 74);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Indicare da chi sono stati organizzati i corsi
    (Regione, ASL, Associazioni di categoria ecc...)',3, true, 74);


--cap 75
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono ispezionati almeno 1 volta/di''.',4,true,75);



INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',5, true, 75);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES (' Sono presenti recinti/locali di isolamento con lettiera
asciutta e confortevole.',6,true,75);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali malati o feriti vengono isolati e ricevono
immediatamente un trattamento appropriato.',7,true,75);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In caso di necessita' viene consultato un medico
veterinario.',8,true,75);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il recinto individuale di isolamento ha dimensioni
adeguate e permette all''animale di girarsi facilmente
e di avere contatti visivi ed olfattivi con gli altri
animali salvo nel caso in cui cio non sia in
contraddizione con specifiche prescrizioni
veterinarie.',9,true,75);
    
--cap 76
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro dei trattamenti farmacologici
ed e'' conforme.',10,true,76);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro di carico e scarico e la
mortalita'' e'' regolarmente registrata.',11,true,76);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono correttamente identificati e registrati
(se previsto dalla normativa).',12,true,76);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un piano di autocontrollo/buone pratiche
di allevamento.',13,true,76);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',14,true,76);

--cap 77
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Lo spazio a disposizione di ogni animale e'' sufficiente
per consentirgli un''adeguata liberta'' di movimenti ed
e'' tale da non causargli inutili sofferenze o lesioni.',15,true,77);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali di stabulazione sono costruiti in modo di
permettere agli animali di coricarsi, giacere in
decubito, alzarsi ed accudire se stessi senza
difficolta''..',16,true,77);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli attacchi eventualmente utilizzati non provocano
lesioni e consentono agli animali di assumere una
posizione confortevole, di giacere ed alzarsi, non
provocano strangolamenti o ferite, sono regolarmente
esaminati, aggiustati o sostituiti se danneggiati',17,true,77);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I recinti di isolamento hanno dimensioni adeguate e
conformi alle disposizioni vigenti.',18,true,77);       

--cap 78
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I materiali di costruzione, i recinti e le attrezzature
con i quali gli animali possono venire a contatto non
sono nocivi per gli animali stessi, non vi sono spigoli
taglienti o sporgenze, tutte le superfici sono
facilmente lavabili e disinfettabili.',19,true,78);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali stabulati all''aperto dispongono di un
riparo adeguato.',20,true,78);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le apparecchiature e gli impianti elettrici sono
costruiti in modo da evitare scosse elettriche e sono
conformi alle norme vigenti in materia.',21,true,78);     

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa dell''aria e le
concentrazioni di gas sono mantenute entro limiti
non dannosi per gli animali - all''atto dell''ispezione
T&deg e UR sono adeguate alle esigenze etologiche della
specie e all''eta'' degli animali.',22,true,78);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le attrezzature per l''alimentazione automatica sono
pulite regolarmente e frequentemente, smontando le
parti in cui si depositano residui di alimento.',23,true,78);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli escrementi, l''urina i foraggi non mangiati o caduti
sono rimossi con regolarita''.',24,true,78);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I pavimenti non sono sdrucciolevoli e non hanno
asperita' tali da provocare lesioni, sono costruiti e
mantenuti in maniera tale da non arrecare sofferenza
o lesioni alle zampe e sono adeguati alle dimensioni
ed al peso dei vitelli.',25,true,78);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un locale/recinto infermeria chiaramente
identificato e con presenza permanente di lettiera
asciutta e acqua fresca in quantita'' sufficiente.',26,true,78);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario.',27,true,78);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali custoditi nei fabbricati non sono tenuti
costantemente al buio, ad essi sono garantiti un
adeguato periodo di luce (naturale o artificiale) ed
un adeguato periodo di riposo.',28,true,78);

--cap.79 (6)
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',29,true,79);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti idonei dispositivi per la
somministrazione di acqua nei periodi di intenso
calore.',30,true,79);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti impianti automatici per la
somministrazione del mangime.',31,true,79);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono prese misure adeguate per salvaguardare la
salute ed il benessere degli animali in caso di non
funzionamento degli impianti (es. metodi alternativi
di alimentazione).',32,true,79);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali in caso di guasto
all''impianto stesso.',33,true,79);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' previsto un sistema di allarme che segnali
eventuali guasti.',34,true,79);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti apparecchiature per il rilevamento
della T&deg e dell''UR.',35,true,79);

--cap 80
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',36,true,80);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',37,true,80);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
   VALUES ('L''alimentazione e' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche dei vitelli.',38,true,80);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se non viene praticata l''alimentazione ad libitum o
con sistemi automatici e'' assicurato l''accesso agli
alimenti a tutti gli animali contemporaneamente per
evitare competizioni.',39,true,80);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La modalita'' di somministrazione dell''acqua consente
una adeguata idratazione degli animali anche nei
periodi di intenso calore.',40,true,80);

--cap 81
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.Lgs. 146/2001, punto 19.',41,true,81);    
    
--cap 82
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni
(questa disposizione non impedisce il ricorso a taluni
procedimenti che possono causare sofferenze o ferite
minime o momentanee o richiedere interventi che
non causano lesioni durevoli, se consentiti dalle
disposizioni vigenti).',42,true,82);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I fabbricati, i recinti, le attrezzature e gli utensili sono
puliti e disinfettati regolarmente.',43,true,82);    
        
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti.',44,true,82);                
           
------------------------------------------ MODULO 4 CAVALLI ---------------------------------------

--108-116 gli id dei capitoli
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0, true, 108);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative',1, true, 108);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2, true, 108);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Indicare da chi sono stati organizzati i corsi
    (Regione, ASL, Associazioni di categoria ecc...)',3, true, 108);


--cap 109
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono ispezionati almeno 1 volta/di''.',4,true,109);



INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',5, true, 109);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES (' Sono presenti recinti/locali di isolamento con lettiera
asciutta e confortevole.',6,true,109);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali malati o feriti vengono isolati e ricevono
immediatamente un trattamento appropriato.',7,true,109);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In caso di necessita' viene consultato un medico
veterinario.',8,true,109);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il recinto individuale di isolamento ha dimensioni
adeguate e permette all''animale di girarsi facilmente
e di avere contatti visivi ed olfattivi con gli altri
animali salvo nel caso in cui cio non sia in
contraddizione con specifiche prescrizioni
veterinarie.',9,true,109);
    
--cap 110
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro dei trattamenti farmacologici
ed e'' conforme.',10,true,110);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro di carico e scarico e la
mortalita'' e'' regolarmente registrata.',11,true,110);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono correttamente identificati e registrati
(se previsto dalla normativa).',12,true,110);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un piano di autocontrollo/buone pratiche
di allevamento.',13,true,110);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',14,true,110);

--cap 111
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Lo spazio a disposizione di ogni animale e'' sufficiente
per consentirgli un''adeguata liberta'' di movimenti ed
e'' tale da non causargli inutili sofferenze o lesioni.',15,true,111);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali di stabulazione sono costruiti in modo di
permettere agli animali di coricarsi, giacere in
decubito, alzarsi ed accudire se stessi senza
difficolta''..',16,true,111);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli attacchi eventualmente utilizzati non provocano
lesioni e consentono agli animali di assumere una
posizione confortevole, di giacere ed alzarsi, non
provocano strangolamenti o ferite, sono regolarmente
esaminati, aggiustati o sostituiti se danneggiati',17,true,111);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I recinti di isolamento hanno dimensioni adeguate e
conformi alle disposizioni vigenti.',18,true,111);       

--cap 112
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I materiali di costruzione, i recinti e le attrezzature
con i quali gli animali possono venire a contatto non
sono nocivi per gli animali stessi, non vi sono spigoli
taglienti o sporgenze, tutte le superfici sono
facilmente lavabili e disinfettabili.',19,true,112);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali stabulati all''aperto dispongono di un
riparo adeguato.',20,true,112);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le apparecchiature e gli impianti elettrici sono
costruiti in modo da evitare scosse elettriche e sono
conformi alle norme vigenti in materia.',21,true,112);     

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa dell''aria e le
concentrazioni di gas sono mantenute entro limiti
non dannosi per gli animali - all''atto dell''ispezione
T&deg e UR sono adeguate alle esigenze etologiche della
specie e all''eta'' degli animali.',22,true,112);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le attrezzature per l''alimentazione automatica sono
pulite regolarmente e frequentemente, smontando le
parti in cui si depositano residui di alimento.',23,true,112);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli escrementi, l''urina i foraggi non mangiati o caduti
sono rimossi con regolarita''.',24,true,112);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I pavimenti non sono sdrucciolevoli e non hanno
asperita' tali da provocare lesioni, sono costruiti e
mantenuti in maniera tale da non arrecare sofferenza
o lesioni alle zampe e sono adeguati alle dimensioni
ed al peso dei vitelli.',25,true,112);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un locale/recinto infermeria chiaramente
identificato e con presenza permanente di lettiera
asciutta e acqua fresca in quantita'' sufficiente.',26,true,112);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario.',27,true,112);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali custoditi nei fabbricati non sono tenuti
costantemente al buio, ad essi sono garantiti un
adeguato periodo di luce (naturale o artificiale) ed
un adeguato periodo di riposo.',28,true,112);

--cap.113 (6)
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',29,true,113);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti idonei dispositivi per la
somministrazione di acqua nei periodi di intenso
calore.',30,true,113);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti impianti automatici per la
somministrazione del mangime.',31,true,113);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono prese misure adeguate per salvaguardare la
salute ed il benessere degli animali in caso di non
funzionamento degli impianti (es. metodi alternativi
di alimentazione).',32,true,113);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali in caso di guasto
all''impianto stesso.',33,true,113);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' previsto un sistema di allarme che segnali
eventuali guasti.',34,true,113);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti apparecchiature per il rilevamento
della T&deg e dell''UR.',35,true,113);

--cap 80
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',36,true,114);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',37,true,114);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
   VALUES ('L''alimentazione e' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche dei vitelli.',38,true,114);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se non viene praticata l''alimentazione ad libitum o
con sistemi automatici e'' assicurato l''accesso agli
alimenti a tutti gli animali contemporaneamente per
evitare competizioni.',39,true,114);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La modalita'' di somministrazione dell''acqua consente
una adeguata idratazione degli animali anche nei
periodi di intenso calore.',40,true,114);

--cap 115
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.Lgs. 146/2001, punto 19.',41,true,115);    
    
--cap 116
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni
(questa disposizione non impedisce il ricorso a taluni
procedimenti che possono causare sofferenze o ferite
minime o momentanee o richiedere interventi che
non causano lesioni durevoli, se consentiti dalle
disposizioni vigenti).',42,true,116);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I fabbricati, i recinti, le attrezzature e gli utensili sono
puliti e disinfettati regolarmente.',43,true,116);    
        
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti.',44,true,116);   

------------------------------------------ MODULO 4 OVINI ---------------------------------------

--117-125 gli id dei capitoli
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0, true, 117);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative',1, true, 117);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2, true, 117);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Indicare da chi sono stati organizzati i corsi
    (Regione, ASL, Associazioni di categoria ecc...)',3, true, 117);


--cap 118
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono ispezionati almeno 1 volta/di''.',4,true,118);



INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',5, true, 118);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES (' Sono presenti recinti/locali di isolamento con lettiera
asciutta e confortevole.',6,true,118);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali malati o feriti vengono isolati e ricevono
immediatamente un trattamento appropriato.',7,true,118);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In caso di necessita' viene consultato un medico
veterinario.',8,true,118);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il recinto individuale di isolamento ha dimensioni
adeguate e permette all''animale di girarsi facilmente
e di avere contatti visivi ed olfattivi con gli altri
animali salvo nel caso in cui cio non sia in
contraddizione con specifiche prescrizioni
veterinarie.',9,true,118);
    
--cap 119
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro dei trattamenti farmacologici
ed e'' conforme.',10,true,119);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro di carico e scarico e la
mortalita'' e'' regolarmente registrata.',11,true,119);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono correttamente identificati e registrati
(se previsto dalla normativa).',12,true,119);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un piano di autocontrollo/buone pratiche
di allevamento.',13,true,119);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',14,true,119);

--cap 120
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Lo spazio a disposizione di ogni animale e'' sufficiente
per consentirgli un''adeguata liberta'' di movimenti ed
e'' tale da non causargli inutili sofferenze o lesioni.',15,true,120);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali di stabulazione sono costruiti in modo di
permettere agli animali di coricarsi, giacere in
decubito, alzarsi ed accudire se stessi senza
difficolta''..',16,true,120);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli attacchi eventualmente utilizzati non provocano
lesioni e consentono agli animali di assumere una
posizione confortevole, di giacere ed alzarsi, non
provocano strangolamenti o ferite, sono regolarmente
esaminati, aggiustati o sostituiti se danneggiati',17,true,120);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I recinti di isolamento hanno dimensioni adeguate e
conformi alle disposizioni vigenti.',18,true,120);       

--cap 121
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I materiali di costruzione, i recinti e le attrezzature
con i quali gli animali possono venire a contatto non
sono nocivi per gli animali stessi, non vi sono spigoli
taglienti o sporgenze, tutte le superfici sono
facilmente lavabili e disinfettabili.',19,true,121);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali stabulati all''aperto dispongono di un
riparo adeguato.',20,true,121);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le apparecchiature e gli impianti elettrici sono
costruiti in modo da evitare scosse elettriche e sono
conformi alle norme vigenti in materia.',21,true,121);     

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa dell''aria e le
concentrazioni di gas sono mantenute entro limiti
non dannosi per gli animali - all''atto dell''ispezione
T&deg e UR sono adeguate alle esigenze etologiche della
specie e all''eta'' degli animali.',22,true,121);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le attrezzature per l''alimentazione automatica sono
pulite regolarmente e frequentemente, smontando le
parti in cui si depositano residui di alimento.',23,true,121);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli escrementi, l''urina i foraggi non mangiati o caduti
sono rimossi con regolarita''.',24,true,121);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I pavimenti non sono sdrucciolevoli e non hanno
asperita' tali da provocare lesioni, sono costruiti e
mantenuti in maniera tale da non arrecare sofferenza
o lesioni alle zampe e sono adeguati alle dimensioni
ed al peso dei vitelli.',25,true,121);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un locale/recinto infermeria chiaramente
identificato e con presenza permanente di lettiera
asciutta e acqua fresca in quantita'' sufficiente.',26,true,121);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario.',27,true,121);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali custoditi nei fabbricati non sono tenuti
costantemente al buio, ad essi sono garantiti un
adeguato periodo di luce (naturale o artificiale) ed
un adeguato periodo di riposo.',28,true,121);

--cap.122 (6)
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',29,true,122);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti idonei dispositivi per la
somministrazione di acqua nei periodi di intenso
calore.',30,true,122);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti impianti automatici per la
somministrazione del mangime.',31,true,122);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono prese misure adeguate per salvaguardare la
salute ed il benessere degli animali in caso di non
funzionamento degli impianti (es. metodi alternativi
di alimentazione).',32,true,122);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali in caso di guasto
all''impianto stesso.',33,true,122);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' previsto un sistema di allarme che segnali
eventuali guasti.',34,true,122);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti apparecchiature per il rilevamento
della T&deg e dell''UR.',35,true,122);

--cap 80
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',36,true,114);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',37,true,123);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
   VALUES ('L''alimentazione e' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche dei vitelli.',38,true,123);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se non viene praticata l''alimentazione ad libitum o
con sistemi automatici e'' assicurato l''accesso agli
alimenti a tutti gli animali contemporaneamente per
evitare competizioni.',39,true,123);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La modalita'' di somministrazione dell''acqua consente
una adeguata idratazione degli animali anche nei
periodi di intenso calore.',40,true,123);

--cap 124
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.Lgs. 146/2001, punto 19.',41,true,124);    
    
--cap 125
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni
(questa disposizione non impedisce il ricorso a taluni
procedimenti che possono causare sofferenze o ferite
minime o momentanee o richiedere interventi che
non causano lesioni durevoli, se consentiti dalle
disposizioni vigenti).',42,true,125);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I fabbricati, i recinti, le attrezzature e gli utensili sono
puliti e disinfettati regolarmente.',43,true,125);    
        
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti.',44,true,125);    
          
------------------------------------------ MODULO 4 QUAGLIE ---------------------------------------

--126-134 gli id dei capitoli
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0, true, 126);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative',1, true, 126);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2, true, 126);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Indicare da chi sono stati organizzati i corsi
    (Regione, ASL, Associazioni di categoria ecc...)',3, true, 126);


--cap 127
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono ispezionati almeno 1 volta/di''.',4,true,127);



INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',5, true, 127);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES (' Sono presenti recinti/locali di isolamento con lettiera
asciutta e confortevole.',6,true,127);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali malati o feriti vengono isolati e ricevono
immediatamente un trattamento appropriato.',7,true,127);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In caso di necessita' viene consultato un medico
veterinario.',8,true,127);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il recinto individuale di isolamento ha dimensioni
adeguate e permette all''animale di girarsi facilmente
e di avere contatti visivi ed olfattivi con gli altri
animali salvo nel caso in cui cio non sia in
contraddizione con specifiche prescrizioni
veterinarie.',9,true,127);
    
--cap 128
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro dei trattamenti farmacologici
ed e'' conforme.',10,true,128);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro di carico e scarico e la
mortalita'' e'' regolarmente registrata.',11,true,128);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono correttamente identificati e registrati
(se previsto dalla normativa).',12,true,128);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un piano di autocontrollo/buone pratiche
di allevamento.',13,true,128);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',14,true,128);

--cap 129
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Lo spazio a disposizione di ogni animale e'' sufficiente
per consentirgli un''adeguata liberta'' di movimenti ed
e'' tale da non causargli inutili sofferenze o lesioni.',15,true,129);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali di stabulazione sono costruiti in modo di
permettere agli animali di coricarsi, giacere in
decubito, alzarsi ed accudire se stessi senza
difficolta''..',16,true,129);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli attacchi eventualmente utilizzati non provocano
lesioni e consentono agli animali di assumere una
posizione confortevole, di giacere ed alzarsi, non
provocano strangolamenti o ferite, sono regolarmente
esaminati, aggiustati o sostituiti se danneggiati',17,true,129);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I recinti di isolamento hanno dimensioni adeguate e
conformi alle disposizioni vigenti.',18,true,129);       

--cap 130
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I materiali di costruzione, i recinti e le attrezzature
con i quali gli animali possono venire a contatto non
sono nocivi per gli animali stessi, non vi sono spigoli
taglienti o sporgenze, tutte le superfici sono
facilmente lavabili e disinfettabili.',19,true,130);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali stabulati all''aperto dispongono di un
riparo adeguato.',20,true,130);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le apparecchiature e gli impianti elettrici sono
costruiti in modo da evitare scosse elettriche e sono
conformi alle norme vigenti in materia.',21,true,130);     

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa dell''aria e le
concentrazioni di gas sono mantenute entro limiti
non dannosi per gli animali - all''atto dell''ispezione
T&deg e UR sono adeguate alle esigenze etologiche della
specie e all''eta'' degli animali.',22,true,130);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le attrezzature per l''alimentazione automatica sono
pulite regolarmente e frequentemente, smontando le
parti in cui si depositano residui di alimento.',23,true,130);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli escrementi, l''urina i foraggi non mangiati o caduti
sono rimossi con regolarita''.',24,true,130);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I pavimenti non sono sdrucciolevoli e non hanno
asperita' tali da provocare lesioni, sono costruiti e
mantenuti in maniera tale da non arrecare sofferenza
o lesioni alle zampe e sono adeguati alle dimensioni
ed al peso dei vitelli.',25,true,130);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un locale/recinto infermeria chiaramente
identificato e con presenza permanente di lettiera
asciutta e acqua fresca in quantita'' sufficiente.',26,true,130);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario.',27,true,130);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali custoditi nei fabbricati non sono tenuti
costantemente al buio, ad essi sono garantiti un
adeguato periodo di luce (naturale o artificiale) ed
un adeguato periodo di riposo.',28,true,130);

--cap.131 (6)
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',29,true,131);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti idonei dispositivi per la
somministrazione di acqua nei periodi di intenso
calore.',30,true,131);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti impianti automatici per la
somministrazione del mangime.',31,true,131);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono prese misure adeguate per salvaguardare la
salute ed il benessere degli animali in caso di non
funzionamento degli impianti (es. metodi alternativi
di alimentazione).',32,true,131);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali in caso di guasto
all''impianto stesso.',33,true,131);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' previsto un sistema di allarme che segnali
eventuali guasti.',34,true,131);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti apparecchiature per il rilevamento
della T&deg e dell''UR.',35,true,131);

--cap 80
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',36,true,114);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',37,true,132);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
   VALUES ('L''alimentazione e' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche dei vitelli.',38,true,132);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se non viene praticata l''alimentazione ad libitum o
con sistemi automatici e'' assicurato l''accesso agli
alimenti a tutti gli animali contemporaneamente per
evitare competizioni.',39,true,132);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La modalita'' di somministrazione dell''acqua consente
una adeguata idratazione degli animali anche nei
periodi di intenso calore.',40,true,132);

--cap 133
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.Lgs. 146/2001, punto 19.',41,true,133);    
    
--cap 134
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni
(questa disposizione non impedisce il ricorso a taluni
procedimenti che possono causare sofferenze o ferite
minime o momentanee o richiedere interventi che
non causano lesioni durevoli, se consentiti dalle
disposizioni vigenti).',42,true,134);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I fabbricati, i recinti, le attrezzature e gli utensili sono
puliti e disinfettati regolarmente.',43,true,134);    
        
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti.',44,true,134);    

------------------------------------------ MODULO 4 PESCI ---------------------------------------

--183-191 gli id dei capitoli
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0, true, 183);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative',1, true, 183);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2, true, 183);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Indicare da chi sono stati organizzati i corsi
    (Regione, ASL, Associazioni di categoria ecc...)',3, true, 183);


--cap 184
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono ispezionati almeno 1 volta/di''.',4,true,184);



INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',5, true, 184);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES (' Sono presenti recinti/locali di isolamento con lettiera
asciutta e confortevole.',6,true,184);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali malati o feriti vengono isolati e ricevono
immediatamente un trattamento appropriato.',7,true,184);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In caso di necessita' viene consultato un medico
veterinario.',8,true,184);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il recinto individuale di isolamento ha dimensioni
adeguate e permette all''animale di girarsi facilmente
e di avere contatti visivi ed olfattivi con gli altri
animali salvo nel caso in cui cio non sia in
contraddizione con specifiche prescrizioni
veterinarie.',9,true,184);
    
--cap 185
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro dei trattamenti farmacologici
ed e'' conforme.',10,true,185);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro di carico e scarico e la
mortalita'' e'' regolarmente registrata.',11,true,185);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono correttamente identificati e registrati
(se previsto dalla normativa).',12,true,185);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un piano di autocontrollo/buone pratiche
di allevamento.',13,true,185);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',14,true,185);

--cap 186
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Lo spazio a disposizione di ogni animale e'' sufficiente
per consentirgli un''adeguata liberta'' di movimenti ed
e'' tale da non causargli inutili sofferenze o lesioni.',15,true,186);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali di stabulazione sono costruiti in modo di
permettere agli animali di coricarsi, giacere in
decubito, alzarsi ed accudire se stessi senza
difficolta''..',16,true,186);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli attacchi eventualmente utilizzati non provocano
lesioni e consentono agli animali di assumere una
posizione confortevole, di giacere ed alzarsi, non
provocano strangolamenti o ferite, sono regolarmente
esaminati, aggiustati o sostituiti se danneggiati',17,true,186);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I recinti di isolamento hanno dimensioni adeguate e
conformi alle disposizioni vigenti.',18,true,186);       

--cap 187
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I materiali di costruzione, i recinti e le attrezzature
con i quali gli animali possono venire a contatto non
sono nocivi per gli animali stessi, non vi sono spigoli
taglienti o sporgenze, tutte le superfici sono
facilmente lavabili e disinfettabili.',19,true,187);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali stabulati all''aperto dispongono di un
riparo adeguato.',20,true,187);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le apparecchiature e gli impianti elettrici sono
costruiti in modo da evitare scosse elettriche e sono
conformi alle norme vigenti in materia.',21,true,187);     

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa dell''aria e le
concentrazioni di gas sono mantenute entro limiti
non dannosi per gli animali - all''atto dell''ispezione
T&deg e UR sono adeguate alle esigenze etologiche della
specie e all''eta'' degli animali.',22,true,187);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le attrezzature per l''alimentazione automatica sono
pulite regolarmente e frequentemente, smontando le
parti in cui si depositano residui di alimento.',23,true,187);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli escrementi, l''urina i foraggi non mangiati o caduti
sono rimossi con regolarita''.',24,true,187);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I pavimenti non sono sdrucciolevoli e non hanno
asperita' tali da provocare lesioni, sono costruiti e
mantenuti in maniera tale da non arrecare sofferenza
o lesioni alle zampe e sono adeguati alle dimensioni
ed al peso dei vitelli.',25,true,187);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un locale/recinto infermeria chiaramente
identificato e con presenza permanente di lettiera
asciutta e acqua fresca in quantita'' sufficiente.',26,true,187);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario.',27,true,187);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali custoditi nei fabbricati non sono tenuti
costantemente al buio, ad essi sono garantiti un
adeguato periodo di luce (naturale o artificiale) ed
un adeguato periodo di riposo.',28,true,187);

--cap.188 (6)
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',29,true,188);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti idonei dispositivi per la
somministrazione di acqua nei periodi di intenso
calore.',30,true,188);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti impianti automatici per la
somministrazione del mangime.',31,true,188);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono prese misure adeguate per salvaguardare la
salute ed il benessere degli animali in caso di non
funzionamento degli impianti (es. metodi alternativi
di alimentazione).',32,true,188);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali in caso di guasto
all''impianto stesso.',33,true,188);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' previsto un sistema di allarme che segnali
eventuali guasti.',34,true,188);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti apparecchiature per il rilevamento
della T&deg e dell''UR.',35,true,188);

--cap 80
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',36,true,114);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',37,true,189);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
   VALUES ('L''alimentazione e' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche dei vitelli.',38,true,189);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se non viene praticata l''alimentazione ad libitum o
con sistemi automatici e'' assicurato l''accesso agli
alimenti a tutti gli animali contemporaneamente per
evitare competizioni.',39,true,189);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La modalita'' di somministrazione dell''acqua consente
una adeguata idratazione degli animali anche nei
periodi di intenso calore.',40,true,189);

--cap 190
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.Lgs. 146/2001, punto 19.',41,true,190);    
    
--cap 191
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni
(questa disposizione non impedisce il ricorso a taluni
procedimenti che possono causare sofferenze o ferite
minime o momentanee o richiedere interventi che
non causano lesioni durevoli, se consentiti dalle
disposizioni vigenti).',42,true,191);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I fabbricati, i recinti, le attrezzature e gli utensili sono
puliti e disinfettati regolarmente.',43,true,191);    
        
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti.',44,true,191);    

------------------------------------------ MODULO 4 CONIGLI ---------------------------------------

--135-143 gli id dei capitoli
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0, true, 135);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative',1, true, 135);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2, true, 135);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Indicare da chi sono stati organizzati i corsi
    (Regione, ASL, Associazioni di categoria ecc...)',3, true, 135);


--cap 136
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono ispezionati almeno 1 volta/di''.',4,true,136);



INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',5, true, 136);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES (' Sono presenti recinti/locali di isolamento con lettiera
asciutta e confortevole.',6,true,136);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali malati o feriti vengono isolati e ricevono
immediatamente un trattamento appropriato.',7,true,136);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In caso di necessita' viene consultato un medico
veterinario.',8,true,136);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il recinto individuale di isolamento ha dimensioni
adeguate e permette all''animale di girarsi facilmente
e di avere contatti visivi ed olfattivi con gli altri
animali salvo nel caso in cui cio non sia in
contraddizione con specifiche prescrizioni
veterinarie.',9,true,136);
    
--cap 137
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro dei trattamenti farmacologici
ed e'' conforme.',10,true,137);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro di carico e scarico e la
mortalita'' e'' regolarmente registrata.',11,true,137);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono correttamente identificati e registrati
(se previsto dalla normativa).',12,true,137);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un piano di autocontrollo/buone pratiche
di allevamento.',13,true,137);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',14,true,137);

--cap 138
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Lo spazio a disposizione di ogni animale e'' sufficiente
per consentirgli un''adeguata liberta'' di movimenti ed
e'' tale da non causargli inutili sofferenze o lesioni.',15,true,138);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali di stabulazione sono costruiti in modo di
permettere agli animali di coricarsi, giacere in
decubito, alzarsi ed accudire se stessi senza
difficolta''..',16,true,138);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli attacchi eventualmente utilizzati non provocano
lesioni e consentono agli animali di assumere una
posizione confortevole, di giacere ed alzarsi, non
provocano strangolamenti o ferite, sono regolarmente
esaminati, aggiustati o sostituiti se danneggiati',17,true,138);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I recinti di isolamento hanno dimensioni adeguate e
conformi alle disposizioni vigenti.',18,true,138);       

--cap 139
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I materiali di costruzione, i recinti e le attrezzature
con i quali gli animali possono venire a contatto non
sono nocivi per gli animali stessi, non vi sono spigoli
taglienti o sporgenze, tutte le superfici sono
facilmente lavabili e disinfettabili.',19,true,139);       


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali stabulati all''aperto dispongono di un
riparo adeguato.',20,true,139);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le apparecchiature e gli impianti elettrici sono
costruiti in modo da evitare scosse elettriche e sono
conformi alle norme vigenti in materia.',21,true,139);     

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa dell''aria e le
concentrazioni di gas sono mantenute entro limiti
non dannosi per gli animali - all''atto dell''ispezione
T&deg e UR sono adeguate alle esigenze etologiche della
specie e all''eta'' degli animali.',22,true,139);  

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le attrezzature per l''alimentazione automatica sono
pulite regolarmente e frequentemente, smontando le
parti in cui si depositano residui di alimento.',23,true,139);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli escrementi, l''urina i foraggi non mangiati o caduti
sono rimossi con regolarita''.',24,true,139);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I pavimenti non sono sdrucciolevoli e non hanno
asperita' tali da provocare lesioni, sono costruiti e
mantenuti in maniera tale da non arrecare sofferenza
o lesioni alle zampe e sono adeguati alle dimensioni
ed al peso dei vitelli.',25,true,139);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un locale/recinto infermeria chiaramente
identificato e con presenza permanente di lettiera
asciutta e acqua fresca in quantita'' sufficiente.',26,true,139);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario.',27,true,139);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali custoditi nei fabbricati non sono tenuti
costantemente al buio, ad essi sono garantiti un
adeguato periodo di luce (naturale o artificiale) ed
un adeguato periodo di riposo.',28,true,139);

--cap.140 (6)
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',29,true,140);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti idonei dispositivi per la
somministrazione di acqua nei periodi di intenso
calore.',30,true,140);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti impianti automatici per la
somministrazione del mangime.',31,true,140);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono prese misure adeguate per salvaguardare la
salute ed il benessere degli animali in caso di non
funzionamento degli impianti (es. metodi alternativi
di alimentazione).',32,true,140);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali in caso di guasto
all''impianto stesso.',33,true,140);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' previsto un sistema di allarme che segnali
eventuali guasti.',34,true,140);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti apparecchiature per il rilevamento
della T&deg e dell''UR.',35,true,140);

--cap 80
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',36,true,114);    

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',37,true,141);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
   VALUES ('L''alimentazione e' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche dei vitelli.',38,true,141);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se non viene praticata l''alimentazione ad libitum o
con sistemi automatici e'' assicurato l''accesso agli
alimenti a tutti gli animali contemporaneamente per
evitare competizioni.',39,true,141);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La modalita'' di somministrazione dell''acqua consente
una adeguata idratazione degli animali anche nei
periodi di intenso calore.',40,true,141);

--cap 142
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.Lgs. 146/2001, punto 19.',41,true,142);    
    
--cap 143
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni
(questa disposizione non impedisce il ricorso a taluni
procedimenti che possono causare sofferenze o ferite
minime o momentanee o richiedere interventi che
non causano lesioni durevoli, se consentiti dalle
disposizioni vigenti).',42,true,143);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I fabbricati, i recinti, le attrezzature e gli utensili sono
puliti e disinfettati regolarmente.',43,true,143);    
        
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti.',44,true,143);    

------------------------------------------ MODULO 5 POLLI DA CARNE ---------------------------------------

--198-206 gli id dei capitoli polli da carne
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0, true, 198);
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il personale addetto agli animali ha ricevuto
istruzioni scritte sulle disposizioni normative in
materia di benessere animale, comprese quelle
relative ai metodi di abbattimento',1, true, 198);

    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono previsti corsi di formazione specifici in materia
incentrati in particolare sul benessere degli animali
per il personale addetto agli animali
indicare la frequenza dei corsi (una volta l''anno, ogni
sei mesi ecc).
Indicare da chi sono stati organizzati i corsi
(Regione, ASL, Associazioni di categoria
ecc)',2, true, 198);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il detentore ha partecipato ad appositi corsi di
formazione sul benessere animale ed e'' in possesso di
un certificato che attesta la formazione conseguita',3, true,198);


--cap 199
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali sono ispezionati almeno 2 volte al giorno.',4,true,199);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',5, true, 199);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli animali malati o feriti ricevono una terapia
appropriata o sono abbattuti immediatamente ',6,true,199);    
    
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('In caso di necessita'' viene consultato un medico
veterinario',7,true,199);

--cap 200
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente il registro dei trattamenti farmacologici ed
e'' conforme',8,true,200);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il proprietario o il detentore registra per ciascun
capannone:
- il numero di polli introdotti;
- l''area utilizzabile;
- l''ibrido o la razza dei polli, se noti;
- il numero di volatili trovati morti, con l''indicazione
delle cause se note, nonché il numero di volatili
abbattuti e la causa;
- il numero di polli rimanenti nel gruppo una volta
prelevati quelli destinati alla vendita o alla
macellazione;',9,true,200);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I registri sono conservati per un periodo di almeno 3
anni',10,true,200);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' presente un piano di autocontrollo/buone pratiche
di allevamento',11,true,200);
    
--cap 201
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('La densita'' di allevamento degli animali, autorizzata
dalla ASL competente per il territorio, e'' rispettata',12,true,201);       

--cap 202
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I materiali di costruzione, i recinti e le attrezzature
con i quali gli animali possono venire a contatto non
sono nocivi per gli animali stessi, non vi sono spigoli
taglienti o sporgenze, tutte le superfici sono
facilmente lavabili e disinfettabili',13,true,202);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('le apparecchiature e gli impianti elettrici sono
costruiti in modo da evitare scosse elettriche e sono
conformi alle norme vigenti in materia',14,true,202);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Le attrezzature per l''alimentazione automatica sono
pulite regolarmente e frequentemente, smontando le
parti in cui si depositano residui di alimento',15,true,202);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Tutti i polli hanno accesso in modo permanente a una
lettiera asciutta e friabile in superficie',16,true,202);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('I capannoni sono illuminati con un''intensita'' di
almeno 20 lux, misurata all''altezza della testa dei
polli e in grado di illuminare almeno l''80% dell''area
utilizzabile',17,true,202);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il sistema di ventilazione di ciascun capannone e
l''eventuale sistema di riscaldamento e
raffreddamento sono fatti funzionare in modo che :
- la concentrazione di ammoniaca (NH3) non supera i
20 ppm, misurata all''altezza della testa dei polli;
- la concentrazione di anidride carbonica non supera i
3000 ppm, misurata all''altezza della testa dei polli;
- la temperatura interna non supera quella esterna di
piu di 3&deg C quando la temperatura esterna all''ombra e'
superiore a 30&deg;
- l''umidita'' relativa media misurata all''interno del
capannone durante 48 ore non supera il 70% quando
la temperatura esterna e'' inferiore a 10&degC.',18,true,202);


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il ciclo di luce segue un ritmo di 24 ore e comprende
periodi di oscurita'' di almeno 6 ore totali, con un
periodo ininterrotto di oscurita'' di almeno 4 ore',19,true,202);


--cap 203
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno',20,true,203);       

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti impianti automatici per la
somministrazione del mangime',21,true,203);     

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il livello sonoro degli impianti automatici e'' tale da
provocare la minore quantita'' di rumore possibile e
non reca, in ogni caso, danno agli animali',22,true,203);  


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono prese misure adeguate per salvaguardare la
salute ed il benessere degli animali in caso di non
funzionamento degli impianti (es. metodi alternativi
di alimentazione)',23,true,203);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali in caso di guasto
all''impianto stesso',24,true,203);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('E'' previsto un sistema di allarme che segnali eventuali
guasti',25,true,203);

--cap 204
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non viene somministrata alcuna sostanza, ad 
eccezione di quelle somministrate a fini terapeutici o 
profilattici o in vista di trattamenti zootecnici come 
previsto dalla normativa vigente',26,true,204);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('i trattamenti terapeutici e profilattici sono 
    regolarmente prescritti da un medico veterinario.',27,true,204);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('L''alimentazione e'' adeguata in rapporto all''eta'', al peso
    e alle esigenze comportamentali e fisiologiche.',28,true,204);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Il mangime e'' disponibile in qualsiasi momento o 
    soltanto ai pasti e non viene ritirato prima di 12 ore 
    dal momento previsto per la macellazione',29,true,204);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Se non viene praticata l''alimentazione ad libitum o 
    con sistemi automatici e'' assicurato l''accesso agli 
    alimenti a tutti gli animali contemporaneamente per 
    evitare competizioni.',30,true,204);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Sono presenti idonei dispositivi per la somministrazione
    di acqua posizionati e sottoposti a manutenzione in modo da 
    ridurre al minimo le perdite.',31,true,204);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('la modalita'' di somministrazione dell''acqua 
    consente una adeguata idratazione degli animali 
    anche nei periodi di intenso calore.',32,true,204);

--205
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Viene effettuata la troncatura del becco autorizzata
dalla ASL competente per il territorio',33,true,205);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Viene effettuata la castrazione degli animali
autorizzata dalla ASL competente per il territorio',34,true,205);

--cap 206
INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni
(questa disposizione non impedisce il ricorso a taluni
procedimenti che possono causare sofferenze o ferite
minime o momentanee o richiedere interventi che
non causano lesioni durevoli, se consentiti dalle
disposizioni vigenti)',35,true,206);

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Dopo il depopolamento definitivo i fabbricati, i
recinti, le attrezzature e gli utensili sono puliti e
disinfettati accuratamente prima di introdurre nel
capannone un nuovo gruppo di animali',36,true,206);    


INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Dopo il depopolamento definitivo di un capannone la
lettiera e'' rimossa e viene predisposta una lettiera
pulita.',37,true,206); 

INSERT INTO chk_bns_dom(
            description, level, enabled, idcap)
    VALUES ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti',38,true,206); 
  
-- Funzione per recuperare i punteggi per capitolo dato il controllo ufficiale in input

-- DROP FUNCTION get_punteggio_per_capitoli_chk_bns_animale(integer);

CREATE OR REPLACE FUNCTION get_punteggio_per_capitoli_chk_bns_animale(IN idcontrollo integer)
  RETURNS TABLE(capitolo text, idcapitolo integer, punteggioa integer, punteggiob integer, punteggioc integer) AS
$BODY$
DECLARE
	r RECORD;	
BEGIN
	FOR capitolo, idCapitolo, punteggioA, punteggioB, punteggioC
	in

	select distinct(r.description) as capitolo, r.code as id_capitolo, v.punteggioA, v.punteggioB, v.punteggioC from (
	SELECT sum(punteggioa) as punteggioA, sum(punteggiob) as punteggioB, sum(punteggioc)as punteggioC, 
	idcap as idCapitolo 
	from chk_bns_risposte risp
	left join chk_bns_mod_ist ist on risp.idmodist = ist.id
	where ist.idcu = idcontrollo 
	group by risp.idcap
	order by risp.idcap) v
	left join chk_bns_cap r on r.code = v.idCapitolo
	order by r.code

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION get_punteggio_per_capitoli_chk_bns_animale(integer)
  OWNER TO postgres;
  