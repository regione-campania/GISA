
CHI: Rita Mele
COSA: Inserimento di liste di riscontro nuove 2014
QUANDO: 13/10


update lookup_chk_bns_mod set codice_specie = -2 where code = 16;
update lookup_chk_bns_mod  set nuova_gestione = false where code >= 1 and code <= 15
update lookup_chk_bns_mod set codice_specie = 1211 where code = 17;
--aggiornare la colonna classe_wc_ns
-----------------------QUA--------------------------------------------------------------

alter table lookup_chk_bns_mod add column nuova_gestione boolean default true;

insert into lookup_chk_bns_mod(code, description, level, enabled, codice_specie)  values(16,'ALLEGATO 1 ALTRE SPECIE',0, true, -1);

insert into chk_bns_cap(code,description, level, enabled, idmod) values (153,'1. PERSONALE',14,true,16);
insert into chk_bns_cap(code,description, level, enabled, idmod) values (154,'2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',14,true,16);
insert into chk_bns_cap(code,description, level, enabled, idmod) values (155,'3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',14,true,16);
insert into chk_bns_cap(code,description, level, enabled, idmod) values (156,'4. LIBERTA'' DI MOVIMENTO',14,true,16);
insert into chk_bns_cap(code,description, level, enabled, idmod) values (157,'5. EDIFICI E LOCALI DI STABULAZIONE',14,true,16);
insert into chk_bns_cap(code,description, level, enabled, idmod) values (158,'6. ATTREZZATURE AUTOMATICHE E MECCANICHE',14,true,16);
insert into chk_bns_cap(code,description, level, enabled, idmod) values (159,'7. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',14,true,16);
insert into chk_bns_cap(code,description, level, enabled, idmod) values (160,'8. MUTILAZIONI',14,true,16);
insert into chk_bns_cap(code,description, level, enabled, idmod) values (161,'9. PROCEDURE D''ALLEVAMENTO',14,true,16);


insert into lookup_chk_bns_mod(code, description, level, enabled, codice_specie)  values(17,'ALLEGATO 4 VITELLI',0, true, -1);
-- NUOVO ALLEGATO 4 VITELLI
insert into chk_bns_cap(code, description, level, enabled, idmod) values (162,'1. PERSONALE',15,true,17);
insert into chk_bns_cap(code, description, level, enabled, idmod) values (163,'2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',15,true,17);
insert into chk_bns_cap(code, description, level, enabled, idmod) values (164,'3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',15,true,17);
insert into chk_bns_cap(code, description, level, enabled, idmod) values (165,'4. LIBERTA'' DI MOVIMENTO',15,true,17);
insert into chk_bns_cap(code, description, level, enabled, idmod) values (166,'5. SPAZIO DISPONIBILE',15,true,17);
insert into chk_bns_cap(code, description, level, enabled, idmod) values (167,'6. EDIFICI E LOCALI DI STABULAZIONE',15,true,17);
insert into chk_bns_cap(code, description, level, enabled, idmod) values (168,'7. ILLUMINAZIONE MINIMA',15,true,17);
insert into chk_bns_cap(code, description, level, enabled, idmod) values (169,'8. ATTREZZATURE AUTOMATICHE E MECCANICHE',15,true,17);
insert into chk_bns_cap(code, description, level, enabled, idmod) values (170,'9. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',15,true,17);
insert into chk_bns_cap(code, description, level, enabled, idmod) values (171,'10. TASSO DI EMOGLOBINA (VITELLI)',15,true,17);
insert into chk_bns_cap(code, description, level, enabled, idmod) values (172,'11. MANGIMI CONTENENTI FIBRE',15,true,17);
insert into chk_bns_cap(code, description, level, enabled, idmod) values (173,'12. MUTILAZIONI',15,true,17);
insert into chk_bns_cap(code, description, level, enabled, idmod) values (174,'13. PROCEDURE D''ALLEVAMENTO',15,true,17);

insert into lookup_chk_bns_mod(code,description, level, enabled, codice_specie, nuova_gestione) values(18,'ALLEGATO 2 GALLINE OVAIOLE', 0, true, 131,true); 

insert into chk_bns_cap(code,description, level, enabled, idmod, classe_nc_ws) values(207,'1. PERSONALE',2,true,18,1);
insert into chk_bns_cap(code, description, level, enabled, idmod, classe_nc_ws) values(208,'2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',2,true,18,2);
insert into chk_bns_cap(code, description, level, enabled, idmod, classe_nc_ws) values(209,'3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',2,true,18,3);
insert into chk_bns_cap(code, description, level, enabled, idmod, classe_nc_ws) values(211,'4. SPAZIO DISPONIBILE',2,true,18,4);
insert into chk_bns_cap(code, description, level, enabled, idmod, classe_nc_ws) values(212,'5. EDIFICI E LOCALI DI STABULAZIONE',2,true,18,5);
insert into chk_bns_cap(code, description, level, enabled, idmod, classe_nc_ws) values(213,'6. ILLUMINAZIONE MINIMA',2,true,18,6);
insert into chk_bns_cap(code, description, level, enabled, idmod, classe_nc_ws) values(214,'7. ATTREZZATURE AUTOMATICHE E MECCANICHE',2,true,18,7);
insert into chk_bns_cap(code, description, level, enabled, idmod, classe_nc_ws) values(215,'8. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',2,true,18,8);
insert into chk_bns_cap(code, description, level, enabled, idmod, classe_nc_ws) values(216,'9. MUTILAZIONI',2,true,18,9);
insert into chk_bns_cap(code, description, level, enabled, idmod, classe_nc_ws) values(219,'10. PROCEDURE D''ALLEVAMENTO',2,true,18,10);

insert into lookup_chk_bns_mod(code, description, level, enabled, codice_specie, nuova_gestione) values(19, 'ALLEGATO 3 SUINI', 0, true, 122,true); 

insert into chk_bns_cap(code,description, level, enabled, idmod, classe_nc_ws) values(220,'1. PERSONALE',1,true,19,11);
insert into chk_bns_cap(code,description, level, enabled, idmod, classe_nc_ws) values(221,'2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',1,true,19,12);
insert into chk_bns_cap(code,description, level, enabled, idmod, classe_nc_ws) values(222,'3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',1,true,19,13);
insert into chk_bns_cap(code,description, level, enabled, idmod, classe_nc_ws) values(223,'4. LIBERTA'' DI MOVIMENTO',1,true,19,14);
insert into chk_bns_cap(code,description, level, enabled, idmod, classe_nc_ws) values(224,'5. SPAZIO DISPONIBILE',1,true,19,15);
insert into chk_bns_cap(code,description, level, enabled, idmod, classe_nc_ws) values(225,'6. EDIFICI E LOCALI DI STABULAZIONE',1,true,19,16);
insert into chk_bns_cap(code,description, level, enabled, idmod, classe_nc_ws) values(226,'7. ILLUMINAZIONE MINIMA',1,true,19,17);
insert into chk_bns_cap(code,description, level, enabled, idmod, classe_nc_ws) values(227,'8. PAVIMENTAZIONI',1,true,19,18);
insert into chk_bns_cap(code,description, level, enabled, idmod, classe_nc_ws) values(228,'9. MATERIALE MANIPOLABILE',1,true,19,19);
insert into chk_bns_cap(code,description, level, enabled, idmod, classe_nc_ws) values(229,'10. ALIMENTAZIONE, ABBEVERAGGIO ED ALTRE SOSTANZE',1,true,19,20);
insert into chk_bns_cap(code,description, level, enabled, idmod, classe_nc_ws) values(230,'11. MANGIMI CONTENENTI FIBRE',1,true,19,21);
insert into chk_bns_cap(code,description, level, enabled, idmod, classe_nc_ws) values(231,'12. MUTILAZIONI',1,true,19,22);
insert into chk_bns_cap(code,description, level, enabled, idmod, classe_nc_ws) values(232,'13. PROCEDURE D''ALLEVAMENTO',1,true,19,23);
insert into chk_bns_cap(code,description, level, enabled, idmod, classe_nc_ws) values(233,'14. ATTREZZATURE AUTOMATICHE E MECCANICHE',1,true,19,24);

update lookup_chk_bns_mod set codice_specie = -2 where code = 16;


update chk_bns_cap  set classe_nc_ws = 39 where code = 153;
update chk_bns_cap  set classe_nc_ws = 40 where code = 154;
update chk_bns_cap  set classe_nc_ws = 41 where code = 155;
update chk_bns_cap  set classe_nc_ws = 42 where code = 156;
update chk_bns_cap  set classe_nc_ws = 44 where code = 157;
update chk_bns_cap  set classe_nc_ws = 46 where code = 158;
update chk_bns_cap  set classe_nc_ws = 47 where code = 159;
update chk_bns_cap  set classe_nc_ws = 48 where code = 160;
update chk_bns_cap  set classe_nc_ws = 49 where code = 161;

update chk_bns_cap  set classe_nc_ws = 25 where code = 162;
update chk_bns_cap  set classe_nc_ws = 26 where code = 163;
update chk_bns_cap  set classe_nc_ws = 27 where code = 164;
update chk_bns_cap  set classe_nc_ws = 29 where code = 165;
update chk_bns_cap  set classe_nc_ws = 28 where code = 166;
update chk_bns_cap  set classe_nc_ws = 31 where code = 167;
update chk_bns_cap  set classe_nc_ws = 32 where code = 168;
update chk_bns_cap  set classe_nc_ws = 33 where code = 169;
update chk_bns_cap  set classe_nc_ws = 34 where code = 170;
update chk_bns_cap  set classe_nc_ws = 35 where code = 171;
update chk_bns_cap  set classe_nc_ws = 36 where code = 172;
update chk_bns_cap  set classe_nc_ws = 37 where code = 173;
update chk_bns_cap  set classe_nc_ws = 38 where code = 174;

update lookup_chk_bns_mod  set nuova_gestione = false where code >= 1 and code <= 15
update lookup_chk_bns_mod set codice_specie = 1211 where code = 17;



insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0,true,153);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative',1,true,153);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Indicare da chi sono stati organizzati i corsi
    (Regione, ASL, Associazioni di categoria ecc...)',3,true,153);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Sono previsti corsi di formazione specifici in materia 
incentrati in particolare sul benessere degli animali 
per il personale addetto agli animali. 
Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.).',2,true,153);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli animali sono ispezionati almeno 1 volta/di''.',4,true,154);
insert into chk_bns_dom (description, level, enabled, idcap) values ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',5,true,154);
insert into chk_bns_dom (description, level, enabled, idcap) values (' Sono presenti recinti/locali di isolamento con lettiera
asciutta e confortevole.',6,true,154);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli animali malati o feriti vengono isolati e ricevono
immediatamente un trattamento appropriato.',7,true,154);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Il recinto individuale di isolamento ha dimensioni
adeguate e permette all''animale di girarsi facilmente
e di avere contatti visivi ed olfattivi con gli altri
animali salvo nel caso in cui ci&ograve; non sia in
contraddizione con specifiche prescrizioni
veterinarie.',9,true,154);
insert into chk_bns_dom (description, level, enabled, idcap) values ('In caso di necessit&agrave; viene consultato un medico
veterinario.',8,true,154);
insert into chk_bns_dom (description, level, enabled, idcap) values ('E'' presente il registro dei trattamenti farmacologici
ed e'' conforme.',10,true,155);
insert into chk_bns_dom (description, level, enabled, idcap) values ('E'' presente il registro di carico e scarico e la
mortalita'' e'' regolarmente registrata.',11,true,155);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli animali sono correttamente identificati e registrati
(se previsto dalla normativa).',12,true,155);
insert into chk_bns_dom (description, level, enabled, idcap) values ('E'' presente un piano di autocontrollo/buone pratiche
di allevamento.',13,true,155);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',14,true,155);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Lo spazio a disposizione di ogni animale e'' sufficiente
per consentirgli un''adeguata liberta'' di movimenti ed
e'' tale da non causargli inutili sofferenze o lesioni.',15,true,156);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I locali di stabulazione sono costruiti in modo di
permettere agli animali di coricarsi, giacere in
decubito, alzarsi ed accudire se stessi senza
difficolta''..',16,true,156);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli attacchi eventualmente utilizzati non provocano
lesioni e consentono agli animali di assumere una
posizione confortevole, di giacere ed alzarsi, non
provocano strangolamenti o ferite, sono regolarmente
esaminati, aggiustati o sostituiti se danneggiati',17,true,156);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I recinti di isolamento hanno dimensioni adeguate e
conformi alle disposizioni vigenti.',18,true,156);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I materiali di costruzione, i recinti e le attrezzature
con i quali gli animali possono venire a contatto non
sono nocivi per gli animali stessi, non vi sono spigoli
taglienti o sporgenze, tutte le superfici sono
facilmente lavabili e disinfettabili.',19,true,157);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli animali stabulati all''aperto dispongono di un
riparo adeguato.',20,true,157);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Le apparecchiature e gli impianti elettrici sono
costruiti in modo da evitare scosse elettriche e sono
conformi alle norme vigenti in materia.',21,true,157);
insert into chk_bns_dom (description, level, enabled, idcap) values ('La circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa dell''aria e le
concentrazioni di gas sono mantenute entro limiti
non dannosi per gli animali - all''atto dell''ispezione
T&deg e UR sono adeguate alle esigenze etologiche della
specie e all''eta'' degli animali.',22,true,157);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Le attrezzature per l''alimentazione automatica sono
pulite regolarmente e frequentemente, smontando le
parti in cui si depositano residui di alimento.',23,true,157);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli escrementi, l''urina i foraggi non mangiati o caduti
sono rimossi con regolarita''.',24,true,157);
insert into chk_bns_dom (description, level, enabled, idcap) values ('E'' presente un locale/recinto infermeria chiaramente
identificato e con presenza permanente di lettiera
asciutta e acqua fresca in quantita'' sufficiente.',26,true,157);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario.',27,true,157);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli animali custoditi nei fabbricati non sono tenuti
costantemente al buio, ad essi sono garantiti un
adeguato periodo di luce (naturale o artificiale) ed
un adeguato periodo di riposo.',28,true,157);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I pavimenti non sono sdrucciolevoli e non hanno
asperit&agrave; tali da provocare lesioni, sono costruiti e
mantenuti in maniera tale da non arrecare sofferenza
o lesioni alle zampe e sono adeguati alle dimensioni
ed al peso dei vitelli.',25,true,157);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',29,true,158);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Sono presenti idonei dispositivi per la
somministrazione di acqua nei periodi di intenso
calore.',30,true,158);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Sono presenti impianti automatici per la
somministrazione del mangime.',31,true,158);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Sono prese misure adeguate per salvaguardare la
salute ed il benessere degli animali in caso di non
funzionamento degli impianti (es. metodi alternativi
di alimentazione).',32,true,158);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali in caso di guasto
all''impianto stesso.',33,true,158);
insert into chk_bns_dom (description, level, enabled, idcap) values ('E'' previsto un sistema di allarme che segnali
eventuali guasti.',34,true,158);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Sono presenti apparecchiature per il rilevamento
della T&deg e dell''UR.',35,true,158);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Non viene somministrata alcuna sostanza ad eccezione di quelle somministrate
ai fini terapeutici o profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',36,true,159);
insert into chk_bns_dom (description, level, enabled, idcap) values ('La modalita'' di somministrazione dell''acqua consente
una adeguata idratazione degli animali anche nei
periodi di intenso calore.',40,true,159);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',37,true,159);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Se non viene praticata l''alimentazione ad libitum o
con sistemi automatici e'' assicurato l''accesso agli
alimenti a tutti gli animali contemporaneamente per
evitare competizioni.',39,true,159);
insert into chk_bns_dom (description, level, enabled, idcap) values ('L''alimentazione &egrave; adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche dei vitelli.',38,true,159);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.Lgs. 146/2001, punto 19.',41,true,160);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti.',44,true,161);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni.',42,true,161);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I fabbricati, i recinti, le attrezzature e gli utensili sono
puliti e disinfettati regolarmente.',43,true,161);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0,true,162);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative',1,true,162);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2,true,162);
insert into chk_bns_dom (description, level, enabled, idcap) values ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',4,true,163);
insert into chk_bns_dom (description, level, enabled, idcap) values (' Sono presenti recinti/locali di isolamento con lettiera
asciutta e confortevole.',5,true,163);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli animali malati o feriti vengono isolati e ricevono
immediatamente un trattamento appropriato.',6,true,163);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Il recinto individuale di isolamento ha dimensioni
adeguate e permette all''animale di girarsi facilmente
e di avere contatti visivi ed olfattivi con gli altri
animali salvo nel caso in cui cio'' non sia in
contraddizione con specifiche prescrizioni
veterinarie.',8,true,163);
insert into chk_bns_dom (description, level, enabled, idcap) values ('In caso di necessit&agrave; viene consultato un medico
veterinario.',7,true,163);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli animali nei locali di stabulazione sono ispezionati
almeno 2 volte/d&igrave; (1 volta/d&igrave; se stabulati all''aperto).',3,true,163);
insert into chk_bns_dom (description, level, enabled, idcap) values ('E'' presente il registro dei trattamenti farmacologici
ed e'' conforme.',9,true,164);
insert into chk_bns_dom (description, level, enabled, idcap) values ('E'' presente il registro di carico e scarico e la
mortalita'' e'' regolarmente registrata.',10,true,164);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli animali sono correttamente identificati e registrati.',11,true,164);
insert into chk_bns_dom (description, level, enabled, idcap) values ('E'' presente un piano di autocontrollo/buone pratiche
di allevamento.',12,true,164);
insert into chk_bns_dom (description, level, enabled, idcap) values ('E'' tenuta una registrazione dei prelievi per il dosaggio dell''HB.',13,true,164);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',14,true,164);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Lo spazio a disposizione di ogni animale e'' sufficiente
per consentirgli un''adeguata liberta'' di movimenti ed
e'' tale da non causargli inutili sofferenze o lesioni.',15,true,165);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I locali di stabulazione sono costruiti in modo di
permettere agli animali di coricarsi, giacere in
decubito, alzarsi ed accudire se stessi senza
difficolta''',16,true,165);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I vitelli non vengono legati ad eccezione di quelli
allevati in gruppo al momento della
somministrazione del latte o suoi succedanei per un
periodo massimo di 1 ora.',17,true,165);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli attacchi eventualmente utilizzati non provocano
lesioni e consentono ai vitelli di assumere una
posizione confortevole, durante l''assunzione 
dell''alimento, di giacere ed alzarsi,
non provocano strangolamenti o ferite, sono regolarmente esaminati,
aggiustati o sostituiti se danneggiati',18,true,165);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I vitelli di eta'' superiore alle 8 settimane non sono
allevati in recinti individuali.',19,true,166);
insert into chk_bns_dom (description, level, enabled, idcap) values ('- mq 1,8 per vitelli di p. v. >220 Kg.',24,true,166);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Sono presenti vitelli di eta'' superiore alle 8 settimane
rinchiusi in recinti individuali per motivi sanitari o
comportamentali certificati da un medico veterinario
esclusivamente per il periodo necessario.',20,true,166);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I recinti individuali di isolamento hanno dimensioni
adeguate e conformi alle disposizioni vigenti, le
pareti divisorie non sono costituite da muri compatti,
ma sono traforate, salvo nel caso in cui sia necessario
isolare i vitelli.',21,true,166);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Lo spazio libero disponibile per ciascun vitello
allevato in gruppo e'' di almeno:
- mq 1,5 per vitelli di p. v. <150 Kg;',22,true,166);
insert into chk_bns_dom (description, level, enabled, idcap) values ('- mq 1,7 per vitelli di p. v. >150 Kg e < 220Kg;',23,true,166);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli animali stabulati all''aperto dispongono di un
riparo adeguato.',26,true,167);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I materiali di costruzione, i recinti e le attrezzature
con i quali gli animali possono venire a contatto non
sono nocivi per gli animali stessi, non vi sono spigoli
taglienti o sporgenze, tutte le superfici sono
facilmente lavabili e disinfettabili.',25,true,167);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Le apparecchiature e gli impianti elettrici sono
costruiti in modo da evitare scosse elettriche e sono
conformi alle norme vigenti in materia.',27,true,167);
insert into chk_bns_dom (description, level, enabled, idcap) values ('La circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa dell''aria e le
concentrazioni di gas sono mantenute entro limiti
non dannosi per gli animali - all''atto dell''ispezione
T&deg e UR sono adeguate alle esigenze etologiche della
specie e all''eta'' degli animali.',28,true,167);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I secchi, i poppatoi, le mangiatoie sono puliti dopo
ogni utilizzo e sottoposti a periodica disinfezione
ogni alimento avanzato viene rimosso.',29,true,167);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Le attrezzature per l''alimentazione automatica sono
pulite regolarmente e frequentemente, smontando le
parti in cui si depositano residui di alimento.',30,true,167);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli escrementi, l''urina i foraggi non mangiati o caduti
sono rimossi con regolarita''.',31,true,167);
insert into chk_bns_dom (description, level, enabled, idcap) values ('La zona in cui i vitelli si coricano e'' confortevole,
pulita e ben drenata.',33,true,167);
insert into chk_bns_dom (description, level, enabled, idcap) values ('E'' presente la lettiera (obbligatoria per vitelli < 2
settimane vita).',34,true,167);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario',36,true,167);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I pavimenti non sono sdrucciolevoli e non hanno
asperit&agrave; tali da provocare lesioni, sono costruiti e
mantenuti in maniera tale da non arrecare sofferenza
o lesioni alle zampe e sono adeguati alle dimensioni
ed al peso dei vitelli.',32,true,167);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli animali custoditi nei fabbricati non sono tenuti
costantemente al buio, ad essi sono garantiti un
adeguato periodo di luce (naturale o artificiale) ed
un adeguato periodo di riposo.',37,true,168);
insert into chk_bns_dom(description, level, enabled, idcap) values ('E'' garantita un''illuminazione adeguata naturale o artificiale, tra le ore 
9:00 e le ore 17:00',38,true,168);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',38,true,169);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Sono presenti idonei dispositivi per la
somministrazione di acqua nei periodi di intenso
calore.',39,true,169);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Sono presenti impianti automatici per la
somministrazione del mangime.',40,true,169);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali in caso di guasto
all''impianto stesso.',42,true,169);
insert into chk_bns_dom (description, level, enabled, idcap) values ('E'' previsto un sistema di allarme che segnali
eventuali guasti.',43,true,169);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Sono presenti apparecchiature per il rilevamento
della T&deg e dell''UR.',44,true,169);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Sono prese misure adeguate per salvaguardare la
salute ed il benessere dei vitelli in caso di non
funzionamento degli impianti (es. metodi alternativi
di alimentazione).',41,true,169);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Il colostro proviene da bovine sane della stessa
azienda.',54,true,170);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',45,true,170);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',46,true,170);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Se non viene praticata l''alimentazione ad libitum o
con sistemi automatici e'' assicurato l''accesso agli
alimenti a tutti i vitelli del gruppo contemporaneamente.',49,true,170);
insert into chk_bns_dom (description, level, enabled, idcap) values ('A partire dalla seconda settimana di eta'', ogni vitello
dispone di acqua fresca di qualita'' ed in quantita''
sufficiente o puo'' soddisfare il proprio fabbisogno di
liquidi con altre bevande.',50,true,170);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I vitelli ricevono il colostro entro le prime 6 ore di vita.',52,true,170);
insert into chk_bns_dom (description, level, enabled, idcap) values ('L''alimentazione &egrave; adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche dei vitelli.',47,true,170);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I vitelli sono nutriti almeno 2 volte al giorno',48,true,170);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Vengono effettuate verifiche sul grado di colostratura
e sulla qualita'' del colostro.',53,true,170);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Il colostro e'' sottoposto a trattamenti di risanamento
in caso di insufficiente stato sanitario delle bovine
presenti in azienda.',55,true,170);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Esiste una banca aziendale del colostro.',56,true,170);
insert into chk_bns_dom (description, level, enabled, idcap) values ('La modalit&agrave; di somministrazione dell''acqua consente
una adeguata idratazione degli animali anche nei
periodi di intenso calore.',51,true,170);
insert into chk_bns_dom (description, level, enabled, idcap) values ('L''alimentazione e'' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche dei
vitelli.',56,true,171);
insert into chk_bns_dom (description, level, enabled, idcap) values ('La razione alimentare ha un contenuto in ferro
sufficiente ad assicurare un tenore di HB di almeno
4,5 mmol/l (pari a 7,25 g/dl).',57,true,171);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Dalla seconda settimana di eta'' e'' somministrata una
quantita'' adeguata di alimenti fibrosi (quantitativo
portato da 50 a 250 grammi al giorno per i vitelli di
eta'' compresa tra 8 e 20 settimane).',58,true,172);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.Lgs. 146/2001, punto 19 sono praticate:
- la cauterizzazione dell''abbozzo corneale entro le
tre settimane di vita sotto controllo veterinario;',59,true,173);
insert into chk_bns_dom (description, level, enabled, idcap) values ('- il taglio della coda se necessario eseguito da un
medico veterinario esclusivamente a fini terapeutici
dei quali esiste idonea documentazione.',60,true,173);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni',61,true,174);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I fabbricati, i recinti, le attrezzature e gli utensili sono
puliti e disinfettati regolarmente.',62,true,174);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti.',63,true,174);
insert into chk_bns_dom (description, level, enabled, idcap) values ('e'' rispettato il divieto di mettere la museruola ai
vitelli.',64,true,174);
insert into chk_bns_dom (description, level, enabled, idcap) values ('In un numero significativo di soggetti si evidenziano
comportamenti anomali (succhiamentio reciproco, movimenti della lingua) ',66,true,174);
insert into chk_bns_dom (description, level, enabled, idcap) values ('I vitelli all''arrivo in azienda o in partenza da essa
hanno pi&ugrave; di 10 giorni di vita (cicatrizzazione
ombelico esterno completa).',65,true,174);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative.',1,true,207);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0,true,207);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2,true,207);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Tutte le galline ovaiole sono ispezionate dal proprietario o dalla persona responsabile almeno una volta al giorno.',3,true,208);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Gli impianti con pi&ugrave; piani di gabbie sono provvisti di dispositivi 
o di misure adeguate che consentono di ispezionare direttamente e agevolmente tutti i piani e che facilitano il ritiro delle galline.',4,true,208);
insert into chk_bns_dom(description, level, enabled, idcap) values ('E'' disponibile un''adeguata illuminazione che consente l''ispezione completa degli animali.',5,true,208);
insert into chk_bns_dom(description, level, enabled, idcap) values ('E'' presente il registro di carico e scarico/la mortalit&agrave;
&egrave; regolarmente registrata.',7,true,209);
insert into chk_bns_dom(description, level, enabled, idcap) values ('E'' presente il registro dei trattamenti.',6,true,209);
insert into chk_bns_dom(description, level, enabled, idcap) values ('E'' presente un piano di autocontrollo /GMP.',8,true,209);
insert into chk_bns_dom(description, level, enabled, idcap) values ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',9,true,209);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Lo spazio a disposizione di ogni gallina &egrave; sufficiente per consentirle un''adeguata libert&agrave; di movimento ed &egrave;
    tale da non causarle inutili sofferenze o lesioni, in tutte le tipologie di allevamento, ovvero:',10,true,211);
insert into chk_bns_dom(description, level, enabled, idcap) values ('GABBIE - ogni gallina ovaiola
dispone di almeno 750 cmq di superficie della gabbia, di cui 600 cmq di superficie utilizzabile,
fermo restando che l''altezza della gabbia diversa dall''altezza al di sopra della superficie utilizzabile
non deve essere inferiore a 20 cm in ogni punto e che la superficie totale di ogni gabbia non pu&ograve; essere inferiore a 2000 cmq',11,true,211);
insert into chk_bns_dom(description, level, enabled, idcap) values ('SISTEMI ALTERNATIVI - il coefficiente di densita''
non e'' superiore a 9 galline ovaiole per mq di zona
utilizzabile. Per gli allevamenti che applicavano
questo sistema al 3 agosto 1999, quando la zona
utilizzabile corrisponde alla superficie del suolo
disponibile il coefficiente non e'' superiore a 12
volatili per mq di superficie',13,true,211);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Ogni gabbia dispone di un sistema di abbeveraggio
appropriato tenuto conto in particolare della
dimensione del gruppo. Nel caso di abbeveraggio a
raccordo, almeno due tettarelle o coppette sono
raggiungibili da ciascuna ovaiola;',32,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Le file di gabbie (per agevolare l''ispezione, la
sistemazione e l''evacuazione dei volatili), sono
separate da passaggi aventi una larghezza minima di
90 cm e tra il pavimento dell''edificio e le gabbie
delle file inferiori lo spazio e'' di almeno 35 cm;',33,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Le gabbie sono provviste di adeguati dispositivi per
accorciare le unghie.',34,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('SISTEMI ALTERNATIVI - gli impianti di
allevamento sono attrezzati in modo da garantire che
tutte le galline ovaiole dispongano:',35,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('- il numero di livelli sovrapposti &egrave; limitato a 4;',43,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('- di mangiatoie lineari che offrono almeno 10 cm di
lunghezza per volatile o circolari che offrono almeno
4 cm di lunghezza per volatile;',36,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('- di una superficie di lettiera di almeno 250 cm2 per
ovaiola; la lettiera occupa almeno un terzo della
superficie al suolo.',40,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Se il sistema di allevamento consente alle galline
ovaiole di muoversi liberamente fra diversi livelli:',42,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('- le mangiatoie e gli abbeveratoi sono ripartiti in
modo da permettere a tutte le ovaiole un accesso
uniforme;',45,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('- i livelli sono installati in modo da impedire alle
deiezioni di cadere sui livelli inferiori.',46,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Se le galline ovaiole dispongono di un passaggio che
consente loro di uscire all''aperto:',47,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('- le diverse aperture del passaggio danno
direttamente accesso allo spazio all''aperto, hanno
un''altezza minima di 35 cm, una larghezza di 40 cm e
sono distribuite su tutta la lunghezza dell''edificio:',48,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('- sono provvisti di riparo dalle intemperie e dai
predatori e (se necessario) e di abbeveratoi
appropriati.',52,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('- di posatoi appropriati, privi di bordi aguzzi e che
offrono almeno 15 cm di spazio per ovaiola. I posatoi
non sovrastano le zone coperte di lettiera: la distanza
orizzontale fra posatoi non &egrave; inferiore a 30 cm e
quella tra i posatoi e le pareti non &egrave; inferiore a 20 cm;',39,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('- di almeno un nido per 7 ovaiole. Se sono utilizzati
nidi di gruppo &egrave; prevista una superficie di almeno 1
m2 per un massimo di 120 ovaiole;',38,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Gli spazi all''aperto:',50,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('L''isolamento termico della struttura, il riscaldamento
e la ventilazione sono adeguati e consentono di
mantenere entro limiti non dannosi per le galline la
circolazione dell''aria, la quantitaì'' di polvere, la
temperatura, l''umidita'' relativa e le concentrazioni di
gas: all''atto dell''ispezione T&deg e UR sono adeguate
alle esigenze etologiche della specie e all''eta'' degli
animali.',21,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Il tipo di pavimentazione consente agli animali di
coricarsi, giacere, alzarsi, muoversi ed accudire a se 
stessi senza difficolta'', secondo le esigenze 
fisiologiche della specie',18,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('- hanno (al fine di prevenire qualsiasi
contaminazione) una superficie adeguata alla densit&agrave;
di ovaiole allevate e alla natura del suolo;',51,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Il tipo di pavimentazione non &egrave; sdrucciolevole, non
ha asperit&agrave; tali da provocare lesioni e sostiene
adeguatamente ciascuna delle dita anteriori di
ciascuna zampa.',17,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Tutti i locali, le attrezzature e gli utensili con i quali
le galline sono in contatto sono completamente puliti
e disinfettati con regolarit&agrave; e comunque ogni volta
che viene praticato un vuoto sanitario e prima di
introdurre una nuova partita di galline. Quando i
locali sono occupati, tutte le superfici e le
attrezzature sono mantenute in condizioni di pulizia
soddisfacenti.',19,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('La mangiatoia &egrave; utilizzabile senza limitazioni ed ha
una lunghezza minima di 12 cm moltiplicata per il
numero di ovaiole nella gabbia;',31,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('- di abbeveratoi continui che offrono 2,5 cm di
lunghezza per ovaiola o circolari che offrono 1 cm di
lunghezza per ovaiola. In caso di utilizzazione di
abbeveratoio a tettarella o a coppetta &egrave; prevista
almeno una tettarella o una coppetta ogni 10 ovaiole.
Nel caso di abbeveratoio a raccordo, almeno due
tettarelle o due coppette devono essere raggiungibili
da ciascuna ovaiola;',37,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Il pavimento degli impianti &egrave; costruito in modo da
sostenere adeguatamente ciascuna delle unghie
anteriore di ciascuna zampa.',41,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('- l''altezza libera minima fra i vari livelli &egrave; di 45 cm;',44,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('- &egrave; comunque disponibile un''apertura totale di 2 m
ogni 1000 ovaiole;',49,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Modello e caratteristiche delle gabbie compresi i
materiali impiegati e gli utensili con i quali le galline
possono venire a contatto non sono nocivi per gli
animali, tutte le superfici sono facilmente lavabili e
disinfettabili, non vi sono spigoli taglienti o
sporgenze.',14,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('I sistemi di allevamento sono concepiti e le gabbie
sono sistemate in modo da impedire che le galline
possano scappare.',15,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('La gabbia e le dimensioni della relativa apertura
hanno forma e dimensioni tali da permettere di
estrarre una gallina adulta senza causarle sofferenze,
lesioni o ferite.',16,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Le galline morte sono rimosse giornalmente.',23,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario.',20,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Le deiezioni sono eliminate regolarmente.',22,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('GABBIE MODIFICATE: le galline ovaiole
dispongono di:
a) un nido (la cui area non entra a far parte della
superficie utilizzabile);
b) di una lettiera che consente loro di becchettare e
razzolare;
c) di posatoi appropriati che offrono almeno 15 cm di
spazio per ovaiola;',30,true,212);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Nel caso di illuminazione naturale, le aperture per la
luce sono disposte in modo da ripartirla
uniformemente nei locali di allevamento.',56,true,213);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Nei periodi di luce tutti gli edifici sono dotati di
un''illuminazione sufficiente per consentire alle
galline di vedersi e di essere viste chiaramente, di
guardarsi intorno e di muoversi normalmente.',53,true,213);
insert into chk_bns_dom(description, level, enabled, idcap) values ('In concomitanza con la diminuzione della luce e''
rispettato un periodo di penombra di durata sufficiente per consentire alle galline di sistemarsi
senza confusione o ferite',55,true,213);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Dopo i primi giorni di adattamento il regime previsto
&egrave; tale da evitare problemi di salute e di
comportamento, &egrave; pertanto seguito un ciclo di 24 ore
che comprendere un periodo di oscurit&agrave; sufficiente e
ininterrotto (a titolo indicativo tale periodo &egrave; pari a
circa un terzo della giornata, per consentire alle
galline di riposarsi ed evitare problemi quali
immunodepressione e anomalie oculari).',54,true,213);
insert into chk_bns_dom(description, level, enabled, idcap) values ('I sistemi produttivi sono sistemati in modo da ridurre
al minimo possibile il livello sonoro e da evitare
rumori di fondo od improvvisi.',57,true,214);
insert into chk_bns_dom(description, level, enabled, idcap) values ('La costruzione, l''installazione, la manutenzione e il
funzionamento dei ventilatori, dei dispositivi di
alimentazione e di altre attrezzature devono essere
tali da provocare il minimo rumore possibile.',59,true,214);
insert into chk_bns_dom(description, level, enabled, idcap) values ('In caso di guasto all''impianto e'' previsto un sistema di
allarme che segnali il guasto.',60,true,214);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',61,true,214);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Sono presenti apparecchiature per il rilevamento
della T&deg; e dell''UR.',58,true,214);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',62,true,215);
insert into chk_bns_dom(description, level, enabled, idcap) values ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',63,true,215);
insert into chk_bns_dom(description, level, enabled, idcap) values ('L''alimentazione e'' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche delle
ovaiole.',64,true,215);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Viene garantito ad ogni singolo soggetto l''accesso
agli alimenti contemporaneamente o con un sistema
di somministrazione dell''alimento tale da ridurre le
aggressioni anche in presenza di competitivita''.',65,true,215);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Viene fornita costantemente acqua fresca in quantit&agrave;
sufficiente e di qualit&agrave;.',66,true,215);
insert into chk_bns_dom(description, level, enabled, idcap) values ('7. il taglio del becco, consentito solo per
comprovate e documentate esigenze per evitare
plumofagia e cannibalismo, viene effettuato da
personale qualificato sotto la responsabilita'' di un
medico veterinario, su pulcini di eta'' inferiore a dieci
giorni.',72,true,216);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Rispetto delle pertinenti disposizioni di cui
all''allegato al D.L.gs. 146/2001:',67,true,216);
insert into chk_bns_dom(description, level, enabled, idcap) values ('a) non vengono praticati interventi che provocano o
possano provocare agli animali sofferenze o lesioni;',68,true,216);
insert into chk_bns_dom(description, level, enabled, idcap) values ('b) il taglio delle ali e la bruciatura dei tendini, se
necessari sono eseguiti esclusivamente a fini
terapeutici dei quali esiste idonea documentazione.',69,true,216);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Rispetto delle pertinenti disposizioni di cui al
D.L.gs. 267/2003 e succ integr. e modif, ovvero:',70,true,216);
insert into chk_bns_dom(description, level, enabled, idcap) values ('6. non vengono praticate mutilazioni.',71,true,216);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Non sono praticati, nell''allevamento naturale o
artificiale, procedimenti di allevamento ed interventi
che provochino o possano provocare agli animali
sofferenze o lesioni.',73,true,219);
insert into chk_bns_dom(description, level, enabled, idcap) values ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti',74,true,219);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Gli animali sono accuditi da un numero sufficientie di addetti. Indicare il numero di addetti',0,true,220);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Il personale addetto agli animali ha ricevuto istruzioni pratiche sulle pertinenti disposizioni normative
    (art.3 e allegato al D.Lgs. 53/2004).',1,true,220);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Sono previsti corsi di formazione specifici in materia incentrati in particolare sul benessere degli animali per il
personale addetto agli animali. Indicare la frequenza dei corsi (una volta l''anno, ogni sei mesi ecc.). Indicare da chi
sono stati organizzati i corsi (Regione, ASL, Associazioni di categoria ecc.)',2,true,220);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- vengono pulite se sistemate negli stalli da parto.',10,true,221);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Gli animali nei locali di stabulazione sono ispezionati
almeno 1 volta/di''.',3,true,221);
insert into chk_bns_dom(description,level,enabled, idcap) values ('E'' disponibile un''adeguata illuminazione che
consente l''ispezione completa degli animali.',4,true,221);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Sono presenti recinti individuali nei quali possono
essere temporaneamente tenuti i suini (soggetti con
problemi comportamentali, particolarmente aggressivi, 
che sono stati attaccati da altri suini, o che
sono malati o feriti ecc.)',5,true,221);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Gli animali malati o feriti ricevono immediatamente
un trattamento appropriato.',6,true,221);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Il recinto individuale di isolamento ha dimensioni
adeguate e permette all''animale di girarsi facilmente
e di avere contatti visivi ed olfattivi con gli altri
suini, salvo nel caso in cui cio'' non sia in
contraddizione con specifiche prescrizioni
veterinarie.',7,true,221);
insert into chk_bns_dom(description,level,enabled, idcap) values ('I suini sono divisi in gruppi omogenei per sesso eta'' e
categoria (verri, scrofe e scrofette, lattonzoli, suinetti
e suini all''ingrasso);',8,true,221);
insert into chk_bns_dom(description,level,enabled, idcap) values ('SCROFE E SCROFETTE:
- se necessario, sono sottoposte a trattamenti contro i
parassiti interni ed esterni;',9,true,221);
insert into chk_bns_dom(description,level,enabled, idcap) values ('SUINETTI E SUINI ALL''INGRASSO:
    - Quando sono tenuti in gruppo vengono prese
sufficienti misure per evitare lotte che vadano oltre il
comportamento normale;',11,true,221);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- La formazione dei gruppi avviene con il minimo
possibile di commistione (mescolamento di suini che
non si conoscono);',12,true,221);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- Qualora necessaria, la modificazione dei gruppi
avviene di preferenza prima dello svezzamento o
entro una settimana dallo svezzamento;',13,true,221);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- I suini dispongono di spazi adeguati per allontanarsi
e nascondersi dagli altri;',14,true,221);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- Sono state adottate idonee misure (ad es. fornire
agli animali abbondante paglia o altro materiale per
esplorazione) a seguito di manifesti segni di lotta
violenta;',15,true,221);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- Gli animali a rischio o particolarmente aggressivi
sono tenuti separati dal gruppo;',16,true,221);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- La somministrazione di tranquillanti avviene solo in
casi eccezionali e dietro prescrizione di un medico
veterinario.',17,true,221);
insert into chk_bns_dom(description,level,enabled, idcap) values ('E'' presente il registro dei trattamenti farmacologici
ed e'' conforme.',18,true,222);
insert into chk_bns_dom(description,level,enabled, idcap) values ('E'' presente il registro di carico e scarico e la
mortalita'' e'' regolarmente registrata.',19,true,222);
insert into chk_bns_dom(description,level,enabled, idcap) values ('E'' presente un piano di autocontrollo /buone pratiche
di allevamento.',20,true,222);
insert into chk_bns_dom(description,level,enabled, idcap) values ('I registri sono conservati per il periodo stabilito dalla
normativa vigente.',21,true,222);
insert into chk_bns_dom(description,level,enabled, idcap) values ('SCROFE E SCROFETTE:
- sono adottate misure per ridurre al minimo le
aggressioni nei gruppi.',25,true,223);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- Vi e'' una idonea fonte di calore;',30,true,223);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- Dietro alla scrofa o alla scrofetta e'' prevista una
zona libera che rende agevole il parto naturale o
assistito.',26,true,223);
insert into chk_bns_dom(description,level,enabled, idcap) values ('La liberta'' di movimento dell''animale non e'' limitata
in modo tale da causargli inutili sofferenze o lesioni.',22,true,223);
insert into chk_bns_dom(description,level,enabled, idcap) values ('E'' rispettato il divieto di utilizzo di attacchi per le
scrofe e le scrofette (in vigore in Italia dal 1&deg gennaio
2001).',23,true,223);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Gli attacchi eventualmente utilizzati per gli altri suini
non provocano lesioni e consentono ai suini di
assumere una posizione confortevole durante
l''assunzione dell''alimento, di giacere ed alzarsi, non
provocano strangolamenti o ferite, sono regolarmente
esaminati, aggiustati o sostituiti se danneggiati.',24,true,223);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- Gli stalli da parto, in cui le scrofe possono muoversi
liberamente, sono provvisti di strutture per
proteggere i lattonzoli ad es. apposite sbarre.',27,true,223);
insert into chk_bns_dom(description,level,enabled, idcap) values ('LATTONZOLI:
- una parte del pavimento e'' sufficientemente ampia
da consentire agli animali di coricarsi e riposare
contemporaneamente.',28,true,223);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- Questa superficie e'' piena o ricoperta da un
tappetino, da paglia o da altro materiale adeguato;',29,true,223);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- Nel caso si usi uno stallo da parto i lattonzoli
dispongono di spazio sufficiente per essere allattati
senza difficolta''.',31,true,223);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Le superfici libere totali a disposizione di ciascuna
SCROFETTA E SCROFA ALLEVATE IN
GRUPPO sono di:<br/>
- 1,64 mq per ciascuna scrofetta dopo la
fecondazione;<br/>
- 2,25 mq per ciascuna scrofa;',33,true,224);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Se le scrofette dopo la fecondazione e le scrofe sono
allevate in gruppi di:<br/>
- meno di sei animali le superfici libere disponibili
devono essere aumentate del 10%;<br/>
- 40 o pi&ugrave; animali le superfici libere disponibili
possono essere ridotte del 10 %;',34,true,224);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Nel periodo compreso tra quattro settimane dopo la
fecondazione e una settimana prima della data
prevista per il parto le scrofe e le scrofette sono
allevate in gruppo<br>
- i lati del recinto dove viene allevato il gruppo di
scrofe o di scrofette hanno una lunghezza superiore a
2,8 m<br>
- se sono allevati meno di 6 animali i lati del recinto
hanno una lunghezza superiore a 2,4 m.',35,true,224);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Deroga per la aziende con meno di 10 scrofe:<br/>
- le scrofe e le scrofette sono allevate
individualmente nel periodo compreso tra quattro
settimane dopo la fecondazione e una settimana
prima della data prevista per il parto;<br/>
- in tal caso gli animali possono girarsi facilmente nel
recinto;',36,true,224);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Le superfici libere a disposizione di ciascun
SUINETTO O SUINO ALL''INGRASSO
ALLEVATO IN GRUPPO (escluse le scrofette dopo
la fecondazione e le scrofe) corrispondono ad
almeno:<br/>
-----------------------------------<br/>
Peso vivo kgmq<br/>
-----------------------------------<br/>
Fino a 10&nbsp;&nbsp;&nbsp;&nbsp;0,15<br/>
Oltre 10 fino a 20&nbsp;&nbsp;&nbsp;0,20<br/>
Oltre a 20 fino a 30&nbsp;&nbsp;&nbsp;0,30<br/>
Oltre a 30 fino a 50&nbsp;&nbsp;&nbsp;0.40<br/>
Oltre a 50 fino a 85&nbsp;&nbsp;&nbsp;0,55<br/>
Oltre a 85 fino a 110&nbsp;&nbsp;&nbsp;0.65<br/>
Oltre 110&nbsp;&nbsp;&nbsp;&nbsp;1.00<br/>
-----------------------------------',32,true,224);
insert into chk_bns_dom(description,level,enabled, idcap) values ('I materiali e le attrezzature con i quali gli animali
possono venire a contatto non sono nocivi per gli
animali.',37,true,225);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Non vi sono spigoli taglienti o sporgenze.',38,true,225);
insert into chk_bns_dom(description,level,enabled, idcap) values ('La circolazione dell''aria, la quantita'' di polvere, la
temperatura, l''umidita'' relativa dell''aria e le
concentrazioni di gas sono mantenute entro limiti
non dannosi per gli animali:
- all''atto dell''ispezione T&deg e UR sono adeguate alle
esigenze etologiche della specie e all''eta'' degli
animali.',39,true,225);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Rumori - dove sono stabulati i suini sono evitati i
rumori continui di intensita'' pari a 85 dBA, i rumori
costanti ed improvvisi.',40,true,225);
insert into chk_bns_dom(description,level,enabled, idcap) values ('I locali di stabulazione sono costruiti in modo di
permettere agli animali di:
- avere accesso ad una zona in cui coricarsi,
confortevole dal punto di vista fisico e termico,
adeguatamente prosciugata e pulita ed in cui tutti gli
animali possono stare distesi contemporaneamente;',41,true,225);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- riposare ed alzarsi con movimenti normali, vedere
altri suini (scrofe e scrofette nella settimana che
precede il parto e durante il parto stesso possono
essere tenute fuori dalla vista degli altri animali).',42,true,225);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Il locale/recinto infermeria e'' chiaramente identificato
e con presenza permanente di lettiera asciutta e acqua
fresca in quantita'' sufficiente.',43,true,225);
insert into chk_bns_dom(description,level,enabled, idcap) values ('I locali adibiti alla preparazione/conservazione degli
alimenti sono adeguatamente separati e soddisfano i
requisiti minimi dal punto di vista igienico-sanitario.',44,true,225);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Dove sono stabulati i suini &egrave; assicurata la luce di
intensit&agrave; di almeno 40 lux per un periodo minimo di
8 ore al giorno.',45,true,226);
insert into chk_bns_dom(description,level,enabled, idcap) values ('I pavimenti:
- non sono sdrucciolevoli e non hanno asperita'' che
possono provocare lesioni ai suini;',46,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- sono costruiti e mantenuti in modo da non arrecare
lesioni o sofferenze agli animali;',47,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- sono adeguati alle dimensioni ed al peso dei suini;',48,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- se non e'' prevista una lettiera i pavimenti sono a
superficie rigida, piana e stabile;',49,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- gli escrementi, l''urina e i foraggi non mangiati o
caduti sono rimossi con regolarita'' per ridurre al
minimo gli odori e la presenza di mosche o roditori.',50,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- nel recinto il verro si puo'' girare ed avere contatti
uditivi, olfattivi e visivi con altri suini;',52,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- se il recinto viene utilizzato anche per
l''accoppiamento la superficie al suolo e'' di almeno 10
mq ed e'' libero da ostacoli.',53,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('2) di almeno 1,3 mq per ogni scrofa.',55,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Una parte di tale pavimento (non superiore al 15%) e''
riservata alle aperture di scarico (griglie, tombini
ecc.).',56,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('I pavimenti fessurati in calcestruzzo per SUINI
ALLEVATI IN GRUPPO hanno:
a) l''ampiezza massima delle aperture di:
- 11 mm per i lattonzoli;',57,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- 14 mm per i suinetti;',58,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- 18 mm per i suini all''ingrasso;',59,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- 20 mm per le scrofette dopo la
fecondazione e le scrofe;',60,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('b) l''ampiezza minima dei travetti:
- 50 mm per i lattonzoli e i suinetti;',61,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- 80 mm per i suini all''ingrasso, le scrofette
dopo la fecondazione e le scrofe.',62,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- la superficie libera al suolo minima del recinto per
VERRO ADULTO &egrave; di 6 mq;',51,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- SCROFETTE DOPO LA FECONDAZIONE E
SCROFE GRAVIDE - una parte della superficie
libera totale a disposizione per ciascuna &egrave; costituita
da pavimento pieno continuo:<br/>
1) di almeno 0,95 mq per ogni scrofetta;',54,true,227);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- indicare eventualmente il motivo dell''assenza del
materiale manipolabile...',64,true,228);
insert into chk_bns_dom(description,level,enabled, idcap) values ('SCROFE e SCROFETTE nella settimana precedente
il parto dispongono di lettiera adeguata in quantita''
sufficiente (tranne nel caso in cui sia tecnicamente
irrealizzabile per il sistema di eliminazione dei
liquami).',65,true,228);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- indicare eventualmente il motivo dell''assenza del
materiale manipolabile.',67,true,228);
insert into chk_bns_dom(description,level,enabled, idcap) values ('I suini (fermo restando quanto previsto all''art. 3,
comma 5 per scrofe e scrofette) hanno accesso ad
una quantit&agrave; sufficiente di materiale che consente
loro adeguate attivit&agrave; di esplorazione e
manipolazione (ad es. paglia, fieno, legno, segatura,
composti di funghi, torba o un loro miscuglio, etc.) -
salvo che il loro uso possa compromettere la salute o
il benessere degli animali.<br/><br/>
- indicare il materiale manipolabile utilizzato (paglia,
fieno, segatura, composti di funghi, torba, materiale
grossolano quale legnoo altro) specificare...',63,true,228);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Le SCROFE e SCROFETTE hanno accesso permanente al materiale 
- indicare il materiale manipolabile utilizzato (paglia,
fieno, segatura, composti di funghi, torba, materiale
grossolano quale legno o altro) specificare...',66,true,228);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Non viene somministrata alcuna sostanza, ad
eccezione di quelle somministrate a fini terapeutici o
profilattici o in vista di trattamenti zootecnici come
previsto dalla normativa vigente.',68,true,229);
insert into chk_bns_dom(description,level,enabled, idcap) values ('I trattamenti terapeutici e profilattici sono
regolarmente prescritti da un medico veterinario.',69,true,229);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Tutti i suini sono nutriti almeno una volta al giorno.',70,true,229);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Se sono alimentati in gruppo e non ad libitum o
mediante un sistema automatico di alimentazione
individuale, ciascun suino ha accesso agli alimenti
contemporaneamente agli altri suini del gruppo.',71,true,229);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Le SCROFE e le SCROFETTE ALLEVATE in
GRUPPO sono alimentate utilizzando un sistema
idoneo a garantire che ciascun animale ottenga
mangime a sufficienza senza essere aggredito, anche
in situazione di competitivita''.',72,true,229);
insert into chk_bns_dom(description,level,enabled, idcap) values ('L''alimentazione e'' adeguata in rapporto all''eta'', al peso
e alle esigenze comportamentali e fisiologiche delle
diverse categorie animali.',73,true,229);
insert into chk_bns_dom(description,level,enabled, idcap) values ('A partire dalla seconda settimana di eta'', ogni suino
dispone in permanenza di acqua fresca di qualita'' ed
in quantita'' sufficiente.',74,true,229);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- i secchi, i poppatoi, le mangiatoie sono puliti
dopo ogni utilizzo e sottoposti a periodica disinfezione;',75,true,229);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- ogni alimento avanzato viene rimosso regolarmente;',76,true,229);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- le attrezzature per l''alimentazione automatica 
sono pulite regolarmente e frequentemente,
smontando le parti in cui si depositano residui di
alimento.',77,true,229);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Per calmare la fame e tenuto conto del bisogno di
masticare tutte le SCROFE e le SCROFETTE
ASCIUTTE GRAVIDE ricevono mangime
riempitivo o ricco di fibre in quantita'' sufficiente ed
alimenti ad alto tenore energetico.',78,true,230);
insert into chk_bns_dom(description,level,enabled, idcap) values ('b. la riduzione delle zanne dei verri, se necessaria,
per evitare lesioni agli altri animali o per motivi di
sicurezza;',80,true,231);
insert into chk_bns_dom(description,level,enabled, idcap) values ('c. il mozzamento di una parte della coda entro i
primi 7 giorni di vita;',81,true,231);
insert into chk_bns_dom(description,level,enabled, idcap) values ('d. la castrazione dei suini di sesso maschile con
mezzi diversi dalla lacerazione dei tessuti entro i
primi 7 giorni di vita;',82,true,231);
insert into chk_bns_dom(description,level,enabled, idcap) values ('e. l''apposizione di un anello al naso, (ammesso solo
quando gli animali sono detenuti in allevamenti
all''aperto);',83,true,231);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Tutte queste operazioni sono praticate da un
veterinario o da altro personale specializzato (art. 5
bis) con tecniche e mezzi adeguati ed in condizioni
igieniche.',84,true,231);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Se la castrazione o il mozzamento della coda sono
praticati dopo il 7&deg giorno di vita, sono eseguiti sotto
anestesia e con somministrazione prolungata di
analgesici, unicamente da un medico veterinario.',85,true,231);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- il mozzamento della coda e la riduzione degli
incisivi dei lattonzoli non costituiscono operazioni di
routine, ma sono praticati soltanto se sono
comprovate lesioni ai capezzoli delle scrofe, agli
orecchi o alle code dei suinetti e dopo aver adottato
misure intese ad evitare le morsicature delle code ed
altri comportamenti anormali (tenendo conto delle
condizioni ambientali e della densita'').',86,true,231);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- e'' necessario che vi sia documentazione della
comprovata esigenza di tali pratiche (dichiarazione di
un medico veterinario).',87,true,231);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Sono praticate:<br/>
a. la riduzione uniforme degli incisivi dei lattonzoli
entro i primi 7 giorni di vita, mediante levigatura o
troncatura che lasci una superficie liscia intatta;',79,true,231);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Non sono praticati l''allevamento naturale o artificiale
o procedimenti di allevamento che provocano o
possano provocare agli animali sofferenze o lesioni.',88,true,232);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Vengono messe in atto azioni preventive e vengono
eseguiti interventi contro mosche, roditori e parassiti.',89,true,232);
insert into chk_bns_dom(description,level,enabled, idcap) values ('LATTONZOLI:
- nessuno di essi viene staccato dalla scrofa prima
dei 28 giorni d''eta''(tranne vi sia influenza negativa
per la madre o il lattonzolo stesso);',90,true,232);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- i lattonzoli sono svezzati prima dei 28 previsti
max 7 giorni prima (21 gg) ma vengono trasferiti in
impianti specializzati;',91,true,232);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- gli impianti specializzati vengono svuotati, puliti
e disinfettati prima dell''introduzione di un nuovo
gruppo ;',92,true,232);
insert into chk_bns_dom(description,level,enabled, idcap) values ('- gli impianti specializzati sono separati dagli
impianti in cui sono tenute le scrofe (per ridurre i
rischi di malattie ai piccoli).',93,true,232);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Se la salute e il benessere degli animali dipendono da
un impianto di ventilazione artificiale, e'' previsto un
adeguato impianto di riserva per garantire un
ricambio d''aria sufficiente a salvaguardare la salute e
il benessere degli animali.',94,true,233);
insert into chk_bns_dom(description,level,enabled, idcap) values ('In caso di guasto all''impianto e'' previsto un sistema di
allarme che segnali il guasto.',95,true,233);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Gli impianti automatici o meccanici sono ispezionati
almeno 1 volta al giorno.',96,true,233);
insert into chk_bns_dom(description,level,enabled, idcap) values ('Sono presenti apparecchiature per il rilevamento
della T&deg e dell''UR.',97,true,233);
--153--223

update chk_bns_dom set description = 'In concomitanza con la diminuzione della luce e''
    rispettato un periodo di penombra di durata sufficiente per consentire alle galline di sistemarsi
    senza confusione o ferite' where code = 56;
    
update chk_bns_dom set description = 'In caso di guasto all''impianto e'' previsto un sistema di
allarme che segnali il guasto.' where code = 61;


--condizionalita'
update chk_bns_dom set description = 'Presenza di un sistema di identificazione degli animali infetti, malati e/o sotto trattamento farmacologico' where code = 814;
update chk_bns_dom  set level = 5 where code = 802;
update chk_bns_dom  set level = 6 where code = 840;
update chk_bns_dom  set level = 7 where code = 803;

update chk_bns_dom  set level = 8 where code = 804;
update chk_bns_dom  set level = 9 where code = 805;
update chk_bns_dom  set level = 10 where code = 806;
update chk_bns_dom  set level = 11 where code = 807;
update chk_bns_dom  set level = 12 where code = 808;
update chk_bns_dom  set level = 13 where code = 809;
update chk_bns_dom  set level = 14 where code = 810;

update chk_bns_dom set description = 'Sempre considerata di livello medio 
Livello alto nel caso siano riscontrate carenze di tipo strutturale' where code = 837

---------------------22/06/2015------------------------------------------------------------------------------------------------------------------------------
insert into lookup_chk_bns_mod(code, description, level, enabled, codice_specie, nuova_gestione)  values(21,'ALLEGATO 1 POLLI DA CARNE',0, true, 1461,true);
insert into chk_bns_cap(code,description, level, enabled, idmod) values(239,'1. PERSONALE',20,true,21);
insert into chk_bns_cap(code,description, level, enabled, idmod) values(240,'2. ISPEZIONE (CONTROLLO DEGLI ANIMALI)',20,true,21);
insert into chk_bns_cap(code,description, level, enabled, idmod) values(241,'3. TENUTA DEI REGISTRI (REGISTRAZIONE DEI DATI)',20,true,21);
insert into chk_bns_cap(code,description, level, enabled, idmod) values(242,'4. LIBERTA'' DI MOVIMENTO',20,true,21);
insert into chk_bns_cap(code,description, level, enabled, idmod) values(243,'5. EDIFICI E LOCALI DI STABULAZIONE',20,true,21);
insert into chk_bns_cap(code,description, level, enabled, idmod) values(244,'6. ATTREZZATURA AUTOMATICA E MECCANICA',20,true,21);
insert into chk_bns_cap(code,description, level, enabled, idmod) values(245,'7. SOMMINISTRAZIONE DI SOSTANZE ALIMENTAZIONE, ABBEVERAGGIO',20,true,21);
insert into chk_bns_cap(code,description, level, enabled, idmod) values(246,'8. MUTILAZIONI',20,true,21);
insert into chk_bns_cap(code,description, level, enabled, idmod) values(247,'9. PROCEDURE OPERATIVE',20,true,21);
insert into chk_bns_cap(code,description, level, enabled, idmod) values(248,'10. CONTROLLO DA EFFETTUARSI IN ALLEVAMENTO PER IL RICORSO A DENSITA'' PIU'' ELEVATE - DA COMPILARE UNA VOLTA CONTROLLATA, VALIDATA E RITENUTA CORRETTA LA PARTE DEL SOPRALLUOGO RELATIVA AI PUNTI PRECEDENTI (DA 1 A 9)',20,true,21);




-- Chi: Bartolo Sansone
-- Cosa: Broiler
-- Quando: 23/06/15



insert into controlli_html_fields 
(id, specie_allevata, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, ordine_campo, obbligatorio)
VALUES
((SELECT MAX( id )+1 FROM controlli_html_fields ), 1461, 'data_ultima_ristrutturazione', 'data', 'Data ultima ristrutturazione', '', '', 1, true);

insert into controlli_html_fields 
(id, specie_allevata, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, ordine_campo, obbligatorio)
VALUES
((SELECT MAX( id )+1 FROM controlli_html_fields ),1461, 'num_totale_capannoni', 'text', 'Num. totale capannoni', '', '', 2, true);

insert into controlli_html_fields 
(id, specie_allevata, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, ordine_campo, obbligatorio)
VALUES
((SELECT MAX( id )+1 FROM controlli_html_fields ),1461, 'num_totale_capannoni_attivi', 'text', 'Num. totale capannoni attivi all''atto dell''ispezione', '', '', 3, true);

insert into controlli_html_fields 
(id, specie_allevata, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, ordine_campo, obbligatorio)
VALUES
((SELECT MAX( id )+1 FROM controlli_html_fields ),1461, 'superficie_allevabile', 'text', 'superficie allevabile totale m2', '', '', 4, true);

insert into controlli_html_fields 
(id, specie_allevata, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, ordine_campo, obbligatorio)
VALUES
((SELECT MAX( id )+1 FROM controlli_html_fields ),1461, 'num_totale_animali_presenti', 'text', 'Num. totale animali presenti', '', '', 5, true);

insert into controlli_html_fields 
(id, specie_allevata, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, ordine_campo, obbligatorio)
VALUES
((SELECT MAX( id )+1 FROM controlli_html_fields ),1461, 'densita_attuale', 'text', 'Densita'' attuale', '', '', 6, true);

insert into controlli_html_fields 
(id, specie_allevata, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, ordine_campo, obbligatorio)
VALUES
((SELECT MAX( id )+1 FROM controlli_html_fields ),1461, 'densita_prevista', 'text', 'Densita'' prevista', '', '', 7, true);

insert into controlli_html_fields 
(id, specie_allevata, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, ordine_campo, obbligatorio)
VALUES
((SELECT MAX( id )+1 FROM controlli_html_fields ),1461, 'num_max_33', 'text', 'Num. massimo di soggetti allevabile a 33Kg m2', '', '', 8, true);

insert into controlli_html_fields 
(id, specie_allevata, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, ordine_campo, obbligatorio)
VALUES
((SELECT MAX( id )+1 FROM controlli_html_fields ),1461, 'num_max_39', 'text', 'Num. massimo di soggetti allevabile a 39Kg m2', '', '', 9, true);

insert into controlli_html_fields 
(id, specie_allevata, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, ordine_campo, obbligatorio)
VALUES
((SELECT MAX( id )+1 FROM controlli_html_fields ),1461, 'num_max_42', 'text', 'Num. massimo di soggetti allevabile a 42Kg m2', '', '', 10, true);

insert into controlli_html_fields 
(id, specie_allevata, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, ordine_campo, obbligatorio)
VALUES
((SELECT MAX( id )+1 FROM controlli_html_fields ),1461, 'veterinario_aziendale', 'text', 'Veterinario aziendale (Se presente): Dott.', '', '', 11, true);
  
  
 insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'- Gli animali sono accuditi  da un adeguato numero di addetti <br/><br/> n. addetti .......... ',1,239);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'- Il detentore ha partecipato ad appositi corsi di formazione ed e'' in possesso di un certificato (art.4, comma 2 d.lgs 181/10) che attesta la formazione conseguita.',2,239);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Altra formazione:<br/><br/>
- indicare la partecipazione a corsi diversi da quelli 
sopra indicati<br/>
- frequenza dei corsi (una volta l''anno, ogni sei 
mesi ecc)................... <br/>
- Indicare da chi sono stati organizzati i corsi 
(Regione, ASL, Associazioni di categoria 
Ecc).................................',3,239);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Il personale addetto ad accudire,catturare o caricare i polli, ha ricevuto istruzioni scritte e orientamenti sulle norme applicabili in materia di benessere degli animali, comprese quelle relative ai metodi di abbattimento praticati negli stabilimenti ',4,239);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Tutti i polli presenti nello stabilimento sono ispezionati almeno due volte al giorno con particolare  attenzione  ai  segni  che rivelano un abbassamento del livello di benessere e/o di salute degli animali. ',1,240);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'I polli gravemente feriti o non sani, (es. con difficolta'' nel camminare o con ascite o malformazioni gravi) e che  probabilmente soffrono,  ricevono  una  terapia  appropriata o sono abbattuti immediatamente.<br/><br/> Un   veterinario   e''   contattato   ogniqualvolta   se   ne presenti la necessita''.',2,240);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Gli  animali  non  presentano  lesioni  o  sofferenze riconducibili  a  modalita''  di  allevamento  non  idonee o a pratiche di mutilazione non consentite.',3,240);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Gli animali morti vengono rimossi immediatamente e stoccati temporaneamente in apposite celle di congelamento in attesa dello smaltimento a fine ciclo.',4,240);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'E'' presente un piano di autocontrollo o un manuale buone pratiche di allevamento
.',5,240);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Il proprietario o il detentore registra, in formato cartaceo o elettronico, per ciascun capannone dello stabilimento, i dati di cui all''allegato I, punto 11 del d.lgs 181/2010: <br/><br/>
a) il numero di polli introdotti <br/><br/>
b) l''area utilizzabile (vd. piano salmonella oppure mappa dell''allevamento depositata per l''autorizzazione alla deroga) <br/><br/>
c)l''ibrido o la razza dei polli, se noti <br/><br/>
d) per ogni controllo, il numero di volatili trovati morti con indicazione delle cause, se note, nonche'' il numero di volatili abbattuti e la causa <br/><br/>
e) il numero di polli rimanenti nel gruppo una volta prelevati quelli destinati alla vendita o alla  macellazione. <br/><br/>
Tali registrazioni sono conservate per  un periodo  di almeno 3 anni<br/><br/>
IN CASO DI DENSITA'' DI ALLEVAMENTO SUPERIORE A 33 Kg/m2<br/>
La documentazione che accompagna il gruppo al macello include il tasso di mortalita'' giornaliera e il tasso di mortalita'' giornaliera cumulativo calcolati dal proprietario o detentore nonche'' l''ibrido o la razza dei polli (che devono essere noti)',1,241);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'E'' presente il registro dei trattamenti farmacologici compilato secondo le specifiche  del D.lgs. 193/06',2,241);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'I   trattamenti   farmacologici   sono   regolarmente prescritti da un medico veterinario.',3,241);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Vi e'' documentazione da parte del veterinario ufficiale del macello di ispezioni post mortem compatibili con condizioni di scarso benessere in allevamento (che devono essere comunicate dal veterinario ufficiale del macello all''allevatore e all''autorita'' sanitaria  nel cui ambito di competenza e'' ubicato lo stabilimento di allevamento)',4,241);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'La densita'' di allevamento degli animali e'' adeguata',1,242);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'I locali di stabulazione sono costruiti in modo di permettere agli animali di coricarsi, giacere in decubito, alzarsi ed accudire se stessi senza difficolta''',2,242);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'I  materiali  di  costruzione,  i  recinti  e  le  attrezzature con  i  quali  gli  animali  possono  venire  a  contatto non sono  nocivi,  non  vi  sono  spigoli  taglienti o sporgenze,   tutte   le   superfici   sono   facilmente lavabili e disinfettabili.',1,243);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Le  apparecchiature  e  gli  impianti  elettrici  sono conformi alle norme vigenti in materia',2,243);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'La   pavimentazione e'' adeguata, consente agli animali di non ferirsi, muoversi, giacere e accudire se stessi senza difficolta''',3,243);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'L''impianto  di  ventilazione  e'' concepito  e  fatto funzionare  in  modo  da  mantenere  i  valori  di  NH3, CO2 e T entro i parametri richiesti dal d.lgs 181/10',4,243);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Sono  presenti  apparecchiature  per  il  rilevamento della T e dell'' UR',5,243);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Le  concentrazioni  dei  gas  sono  mantenute  entro limiti non dannosi per gli animali',6,243);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'All''atto dell''ispezione T e UR sono adeguate alle esigenze  etologiche  della  specie  e  all''eta''  degli animali.<br/><br/> Indicare : T... UR...',7,243);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Le  attrezzature  per  l''alimentazione  automatica sono    pulite    regolarmente    e    frequentemente, smontando  le  parti  in  cui  si  depositano  residui  di alimento.',8,243);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Il livello sonoro deve essere il piu'' basso possibile. La costruzione, l''installazione, il funzionamento e la manutenzione  dei   ventilatori,   dei   dispositivi   di alimentazione  e  di  altre  attrezzature  sono  tali  da provocare la minore quantita'' possibile di rumore e che in ogni caso non arrechi danno agli animali ',9,243);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Tutti  i  polli hanno  accesso  in  modo  permanente  a una lettiera asciutta e friabile in superficie. ',10,243);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Le  attrezzature e le strutture adibite alla preparazione/conservazione   degli   alimenti   sono adeguatamente separate dalle unita'' d''allevamento e  soddisfano  i  requisiti  minimi  dal  punto  di  vista igienico-sanitario
.',11,243);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Tutti  gli  edifici  sono  illuminati  con  un''intensita''  di almeno  20  lux  (a  livello  dell''occhio  dell''animale)  e in   grado   di   illuminare   almeno   l''80  %   dell''area utilizzabile. ',12,243);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Entro sette giorni dal momento in cui i polli sono collocati nell''edificio e fino a tre giorni prima del momento previsto per la macellazione la luce segue un ritmo di 24 ore con periodi di oscurita'' di almeno 6h(di cui un periodo ininterrotto di almeno 4h) esclusi i periodi di attenuazione della luce. Una riduzione temporanea del livello di luce puo'' essere ammessa se ritenuta necessaria in seguito al parere di un veterinario. ',13,243);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Gli     impianti     automatici     o     meccanici     sono ispezionati almeno 1 volta al giorno ',1,244);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Sono prese misure adeguate per salvaguardare la salute ed il benessere degli animali in caso di non funzionamento degli impianti  (es. metodi alternativi di alimentazione)',2,244);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Se la salute e il benessere degli animali dipendono da un impianto di ventilazione artificiale, e'' previsto un  sistema  di allarme  che  segnali  eventuali  guasti nonche''   un   adeguato   impianto   di   riserva   per garantire     un     ricambio  d''aria     sufficiente     a salvaguardare   la   salute   e   il   benessere   degli animali.',3,244);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'E''  previsto   un   sistema   di   allarme   che   segnali l''eventuale  guasto  dell''impianto  elettrico o  delle strutture meccaniche         necessarie         alla sopravvivenza degli animali',4,244);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'L''alimentazione e'' adeguata in rapporto all''eta'', al peso     e     alle     esigenze     comportamentali     e fisiologiche dei polli',1,245);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Il  mangime  e''  disponibile  in  qualsiasi  momento  o soltanto ai pasti e non viene ritirato prima di 12 ore dal momento previsto per la macellazione.',2,245);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'La  modalita''  di  somministrazione  dell''acqua consente  una  adeguata  idratazione  degli  animali anche nei periodi di intenso calore.',3,245);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Gli  abbeveratoi  sono  posizionati  e  sottoposti  a manutenzione  in  modo  da  ridurre  al minimo  le perdite.',4,245);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Gli  abbeveratoi  e  le  strutture  per  l''alimentazione sono strutturate per evitare la competizione',5,245);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'L''esame clinico degli animali consente di stabilire che sono adeguatamente alimentati. ',6,245);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Non  vengono  praticati  interventi  chirurgici,  a  fini  diversi da  quelli  terapeutici  o  diagnostici,  che  recano  danno  o  perdita  di  una  parte  sensibile  del  corpo  o  alterazione della struttura ossea. <br/><br/>
Gli animali sono debeccati   [ ] NO  [ ] SI <br/><br/>
Se SI:<br/><br/>
- il debeccaggio e'' effettuato in allevamento a seguito di autorizzazione sanitaria<br/>
- il debeccaggio e'' effettuato in incubatoio  e l''allevamento ha l''autorizzazione sanitaria alla detenzione di polli debeccati',1,246);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Gli animali sono stati sottoposti a castrazione: <br/><br/>
[ ] si, nel qual caso e'' stata autorizzata dall''Autorita'' Sanitaria  competente  ed  e''  effettuata  soltanto  con la  supervisione  di  un  veterinario  e  ad  opera  di personale specificamente formato <br/>
[ ] no',2,246);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'A   fine   ciclo   in   ogni   capannone   viene   rimossa completamente la lettiera, le parti degli edifici, delle attrezzature o  degli  utensili  in  contatto  con  i  polli sono  pulite  e  disinfettate  accuratamente  e  viene predisposta  una  lettiera  pulita prima  di  introdurre nel capannone un nuovo gruppo di animali.',1,247);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'La lettiera viene rimossa a fine ciclo e:<br/><br/>
[ ] inviata  come  materiale  di  categoria  2  ad  uno stabilimento di trasformazione<br/>
[ ] stoccata  in  allevamento  in  platea  e/o  vasca conforme alle norme edilizie vigenti',2,247);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Le  modalita''   di   allevamento   sono   tali   da   non 
causare sofferenze agli animali',3,247);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Viene   attuato   un   piano   di   lotta   agli   animali infestanti.',4,247);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'Il proprietario o il detentore  tiene a disposizione nel capannone  la documentazione che descrive in dettaglio i sistemi di produzione. In particolare tale documentazione comprende informazioni relative a particolari tecnici del capannone e delle sue attrezzature, quali (allegato II, d.lgs 181/10): <br/><br/>
- Una mappa del capannone indicante le dimensioni delle superfici occupate dai polli<br/>
- Sistemi di ventilazione e ove pertinente di raffreddamento  e riscaldamento, comprese le rispettive ubicazioni, un piano della ventilazione indicante in dettaglio i parametri di qualita'' dell''aria prefissati come: flusso, velocita'' e temperatura dell''aria;<br/>
- Sistemi di alimentazione e approvvigionamento d''acqua e loro ubicazione<br/>
- Sistemi d''allarme di riserva in caso di guasti ad apparecchiature automatiche o meccaniche essenziali per la salute ed il benessere degli animali<br/>
- Procedure operative che assicurino interventi di riparazione urgenti in caso di guasti alle apparecchiature essenziali per la  salute e il benessere degli animali<br/>
- Tipo di pavimentazione e lettiera normalmente usate. ',1,248);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'- La documentazione e'' resa disponibile all''Autorita'' competente su sua richiesta ed e'' tenuta aggiornata. In particolare, sono registrate le ispezioni tecniche al sistema di ventilazione e di allarme.',2,248);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'- Il proprietario o il detentore comunica senza indugio all''autorita'' competente eventuali cambiamenti del capannone, delle attrezzature e delle procedure descritte che potrebbero influire sul benessere dei polli.',3,248);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'<b>NORME PER GLI STABILIMENTI A DENSITA'' SUPERIORE - CONTROLLO DEI PARAMETRI AMBIENTALI (allegato II,  d.lgs 181/10)</b><br/>
Ciascun capannone di uno stabilimento deve essere dotato di sistemi di ventilazione e, se necessario, di riscaldamento e raffreddamento concepiti, costruiti e fatti funzionare in modo che:<br/>
- La concentrazione di ammoniaca (NH3) non superi i 20 ppm e la concentrazione di anidride carbonica (CO2) non superi i 3000 ppm misurati all''altezza dei polli:<br/><br/>
All''atto dell''ispezione i valori riscontrati sono :  <br/>
NH3 _______  e CO2 ________<br/>
- La temperatura interna non superi quella esterna di piu'' di 3 gradi C quando la temperatura esterna all''ombra e'' superiore a 30 gradi C <br/>
All''atto dell''ispezione la temperatura esterna e'' ____________________<br/>
All''atto dell''ispezione la temperatura interna  e''____________________ <br/>
-L''umidita'' relativa media misurata all''interno del capannone durante 48 ore non superi il 70% quando la temperatura esterna e'' inferiore a 10 gradi C (si valutera'' quindi se  vi sia un sistema di registrazione dell''umidita'' e questa e'' adeguata ai parametri previsti.)',4,248);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'<b>SEZIONE AGGIUNTIVA PER LA DENSITA'' MASSIMA (allegato V, d.lgs 181/10)</b>',5,248);
insert into chk_bns_dom(code, description, level, idcap)values ((SELECT MAX( code )+1 FROM chk_bns_dom ),'- I controlli ufficiali effettuati in allevamento negli ultimi 2 anni non hanno rivelato carenze rispetto ai requisiti del decreto <br/>
- Il proprietario o il detentore effettua il monitoraggio utilizzando le guide alle buone pratiche di gestione dettagliate all''art 7 del D.Lgs.<br/>
Vi e'' una registrazione della mortalita'' che dettaglia che la mortalita'' giornaliera cumulativa e'' stata inferiore a 1% + 0,6% moltiplicato per l''eta'' alla macellazione  espressa in giorni.<br/><br/>
<b>IN ALTERNATIVA</b><br/><br/>
Se non e'' stato effettuato alcun monitoraggio nei 2 anni precedenti deve essere eseguito almeno un monitoraggio per controllare che non vi siano carenze rispetto al decreto',6,248);

  
  
  
        