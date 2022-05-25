-- Chi: Bartolo Sansone
-- Cosa: Flusso 208 Gestione Invio ClassyFarm (Farmacosorveglianza)
-- Quando: 19/10/21

alter table farmacosorveglianza_domande add column id_classyfarm integer;

 -- Aggiornamento domande
 
update farmacosorveglianza_domande set domanda = '2. Sono rispettati i tempi di registrazione per lo scarico dei farmaci' where id = 2;
update farmacosorveglianza_domande set domanda = '3. Le movimentazioni di farmaci registrate corrispondono alla giacenza presente in armadietto ' where id = 3;
update farmacosorveglianza_domande set domanda = '9. Assenza di  farmaci riservati ad uso esclusivo del veterinario' where id = 9;
update farmacosorveglianza_domande set domanda = '16. Esiste apposito registro prenumerato e vidimato dall''AUSL (Registro trattamenti ormonali, art. 4 comma 3)' where id = 16;
update farmacosorveglianza_domande set domanda = '19. Il veterinario comunica i trattamenti effettuati al Servizio Veterinario dellAUSL competente entro 3 giorni dalla somministrazione' where id = 19;
update farmacosorveglianza_domande set domanda = '1. Il ricorso a profilassi con antimicrobici e conforme alle Linee Guida sull''utilizzo appropriato dei farmaci veterinari o, se non ancora redatte, e'' giustificabile?' where id = 20;
update farmacosorveglianza_domande set domanda = '2. Il ricorso a metafilassi con antimicrobici e'' conforme alle Linee Guida sull''utilizzo appropriato dei farmaci veterinari o, se non ancora redatte, e'' giustificabile?' where id = 21;
update farmacosorveglianza_domande set domanda = '9. L''utilizzo di antimicrobici all''interno dei protocolli terapeutici avviene secondo quanto indicato nel MANUALE OPERATIVO della REV?' where id = 28;
update farmacosorveglianza_domande set domanda = '13. Sono state effettuate segnalazioni di farmacovigilanza, per segnalare eventuali effetti collaterali e/o sospette diminuzioni di efficacia?' where id = 32;
update farmacosorveglianza_domande set domanda = '22. I medicinali prescritti sono coerenti (per quantita e tipologia) alla realta'' zootecnica, alle condizioni di management e alla situazione epidemiologica presenti nell''allevamento?' where id = 41;

-- Aggiornamento id classyfarm domande

update farmacosorveglianza_domande set id_classyfarm =2549 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('1.  I medicinali sono conservati in un locale/armadietto chiaramente identificato, il cui accesso e consentito alle persone autorizzate, e nel rispetto delle indicazioni riportate sul foglietto illustrativo', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2550 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('2. Sono rispettati i tempi di registrazione per lo scarico dei farmaci', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2551 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('3. Le movimentazioni di farmaci registrate corrispondono alla giacenza presente in armadietto ', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2553 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('4. E presente il registro dei trattamenti veterinari di animali destinati alla produzione di alimenti (art.79 D.lgs. 193/2006 e art.15 D.lgs 158/2006)', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2554 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('5. Le registrazioni rispettano quanto previsto dalla normativa', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2555 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('6. I medicinali veterinari presenti sono correlati ad una prescrizione veterinaria', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2556 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('7. Gli animali in corso di trattamento sono identificati e corrispondono a quelli indicati nel registro', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2557 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('8. Il veterinario registra i trattamenti effettuati con medicinali veterinari della propria scorta', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2558 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('9. Assenza di  farmaci riservati ad uso esclusivo del veterinario', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2559 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('10. Sono indicati sul Mod. 4 gli eventuali trattamenti effettuati nei 90 giorni precedenti, in caso di spostamento degli animali al macello o stalle di sosta', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2560 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('11. Vengono registrati i tempi di attesa per gli animali trattati inviati al macello ', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2561 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('12. Nella scelta terapeutica vengono rispettati i principi previsti dallart. 11 del D.lgs 193/2006 relativamente al trattamento in deroga', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2562 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('13. I medicinali scaduti sono debitamente identificati e correttamente smaltiti', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2563 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('14. Le rimanenze, se presenti, sono correttamente conservate e gestite', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2564 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('15. Sono assenti medicinali veterinari non autorizzati o altre sostanze farmacologicamente attive non in forma di medicinale ', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2565 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('Si intende compilare lArea C TRATTAMENTI ORMONALI?', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2566 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('16. Esiste apposito registro prenumerato e vidimato dallAUSL (Registro trattamenti ormonali, art. 4 comma 3)', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2567 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('17. Le registrazioni sono complete ed effettuate nei tempi corretti (contestualmente alla somministrazione da parte del veterinario)', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2568 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('18. Le categorie trattate sono quelle consentite dalla AIC e gli animali trattati sono adeguatamente identificati', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2569 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('19. Il veterinario comunica i trattamenti effettuati al Servizio Veterinario dellAUSL competente entro 3 giorni dalla somministrazione', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2571 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('1. Il ricorso a profilassi con antimicrobici e conforme alle Linee Guida sullutilizzo appropriato dei farmaci veterinari o, se non ancora redatte, e giustificabile?', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2572 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('2. Il ricorso a metafilassi con antimicrobici e conforme alle Linee Guida sullutilizzo appropriato dei farmaci veterinari o, se non ancora redatte, e giustificabile?', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2573 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('3. In caso di uso profilattico/metafilattico degli antimicrobici questi sono utilizzati conformemente alle indicazioni, patologie e specie di destinazione riportate nei foglietti illustrativi?', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2574 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('4. Le terapie con sostanze ad azione antimicrobica si utilizzano PRINCIPALMENTE in seguito a diagnosi SOLO CLINICA?', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2575 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('5. Le terapie con sostanze ad azione antimicrobica si utilizzano, di norma, in seguito a diagnosi CLINICA e di LABORATORIO', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2576 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('6. Con quale frequenza vengono eseguiti antibiogrammi?', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2577 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('7. I trattamenti di massa o di gruppo con sostanze ad azione antimicrobica sono utilizzati', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2578 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('8. Sono adottati sistemi aggiuntivi di identificazione per gli animali in corso di trattamento?', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2579 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('9. Lutilizzo di antimicrobici allinterno dei protocolli terapeutici avviene secondo quanto indicato nel MANUALE OPERATIVO della REV?', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2580 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('10. In caso di trattamento antibiotico degli animali in lattazione, indicare le modalita di smaltimento del latte', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2581 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('11. Considerando i trattamenti con antimicrobici, si evidenzia difformita nella durata delle terapie, rispetto alle indicazioni riportate nel foglietto illustrativo dei relativi prodotti?', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2582 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('12. Considerando i trattamenti con antimicrobici si evidenzia difformita nel dosaggio, rispetto alle indicazioni riportate in foglietto illustrativo dei relativi prodotti?', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2583 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('13. Sono state effettuate segnalazioni di farmacovigilanza, per segnalare eventuali effetti collaterali e/o sospette diminuzioni di efficacia?', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2584 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('14. Vengono effettuati ADEGUATI trattamenti con antiparassitari?', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2585 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('15. Interventi di profilassi vaccinale - ESCLUSI GLI OBBLIGATORI', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2586 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('16. Con che frequenza viene fatto  ricorso ai mangimi medicati?', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2587 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('17. Vengono utilizzati medicinali omeopatici-fitoterapici?', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2588 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('18. Presenza e applicazione di Procedure Operative che evidenziano che la somministrazione dei medicinali veterinari in acqua da bere o nei mangimi liquidi avvenga in maniera conforme alle indicazioni previste dal foglietto illustrativo', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2589 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('19. Presenza e applicazione di Procedure Operative che evidenziano che la somministrazione di medicinali veterinari per via parenterale avvenga in maniera conforme alle indicazioni previste dal foglietto illustrativo ', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2590 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('20. Presenza e applicazione di Procedure Operative che evidenziano che i mangimi medicati vengano gestiti in maniera tale da evitare la cross contamination', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2591 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('21. Patologie (e agenti eziologici) piu frequentemente riscontrate nellallevamento', ' ', ''), '''', ''));
update farmacosorveglianza_domande set id_classyfarm =2592 where trim(replace(replace(domanda, ' ', ''), '''', '')) ilike trim(replace(replace('22. I medicinali prescritti sono coerenti (per quantita e tipologia) alla realta zootecnica, alle condizioni di management e alla situazione epidemiologica presenti nellallevamento?', ' ', ''), '''', ''));

alter table farmacosorveglianza_giudizi add column id_classyfarm integer;

update farmacosorveglianza_giudizi set giudizio = 'NA - Autoconsumo' where giudizio = 'NA (autoconsumo)';
update farmacosorveglianza_giudizi set giudizio = 'In parte' where giudizio = 'In Parte';
update farmacosorveglianza_giudizi set giudizio = 'su specifica diagnosi' where giudizio = 'Su specifica diagnosi';

insert into farmacosorveglianza_giudizi (id_domanda, giudizio, tipo)
select id, 'NOTE/EVIDENZE', 'textarea' from farmacosorveglianza_domande

update farmacosorveglianza_giudizi set id_classyfarm = 7203 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2549 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7209 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2550 ) and giudizio = 'no';
update farmacosorveglianza_giudizi set id_classyfarm = 7213 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2551 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7223 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2553 ) and giudizio = 'Tipologia registro dei trattamenti:';
update farmacosorveglianza_giudizi set id_classyfarm = 7225 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2554 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7229 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2555 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7233 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2556 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7237 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2557 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7241 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2558 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7245 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2559 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7249 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2560 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7253 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2561 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7257 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2562 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7261 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2563 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7265 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2564 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7269 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2565 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7275 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2566 ) and giudizio = 'Tipo di registro dei trattamenti ormonali:';
update farmacosorveglianza_giudizi set id_classyfarm = 7280 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2567 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7284 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2568 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7285 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2569 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7292 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2571 ) and giudizio = 'No';
update farmacosorveglianza_giudizi set id_classyfarm = 7301 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2572 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7302 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2573 ) and giudizio = 'No';
update farmacosorveglianza_giudizi set id_classyfarm = 7306 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2574 ) and giudizio = 'Si';
update farmacosorveglianza_giudizi set id_classyfarm = 7310 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2575 ) and giudizio = 'Mai';
update farmacosorveglianza_giudizi set id_classyfarm = 7318 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2576 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7320 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2577 ) and giudizio = 'In maniera sistematica';
update farmacosorveglianza_giudizi set id_classyfarm = 7326 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2578 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7329 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2579 ) and giudizio = 'A volte';
update farmacosorveglianza_giudizi set id_classyfarm = 7337 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2580 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7338 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2581 ) and giudizio = 'Si';
update farmacosorveglianza_giudizi set id_classyfarm = 7344 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2582 ) and giudizio = 'Si, con segnalazione di farmacovigilanza';
update farmacosorveglianza_giudizi set id_classyfarm = 7348 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2583 ) and giudizio = 'No';
update farmacosorveglianza_giudizi set id_classyfarm = 7352 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2584 ) and giudizio = 'No';
update farmacosorveglianza_giudizi set id_classyfarm = 7356 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2585 ) and giudizio = 'No';
update farmacosorveglianza_giudizi set id_classyfarm = 7360 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2586 ) and giudizio = '> 1 volta per ciclo produttivo';
update farmacosorveglianza_giudizi set id_classyfarm = 7368 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2587 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7372 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2588 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7376 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2589 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7380 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2590 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7383 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2591 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7385 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2592 ) and giudizio = 'In parte';

update farmacosorveglianza_giudizi set id_classyfarm = 7206 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2549 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7208 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2550 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7216 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2551 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7220 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2553 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7228 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2554 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7232 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2555 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7236 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2556 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7240 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2557 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7244 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2558 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7248 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2559 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7252 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2560 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7256 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2561 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7260 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2562 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7264 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2563 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7268 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2564 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7270 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2565 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7272 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2566 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7279 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2567 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7283 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2568 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7288 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2569 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7296 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2571 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7299 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2572 ) and giudizio = 'Si';
update farmacosorveglianza_giudizi set id_classyfarm = 7305 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2573 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7309 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2574 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7314 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2575 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7315 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2576 ) and giudizio = 'Mai/raramente';
update farmacosorveglianza_giudizi set id_classyfarm = 7322 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2577 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7325 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2578 ) and giudizio = 'Si';
update farmacosorveglianza_giudizi set id_classyfarm = 7330 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2579 ) and giudizio = 'Sempre';
update farmacosorveglianza_giudizi set id_classyfarm = 7336 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2580 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7340 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2581 ) and giudizio = 'No';
update farmacosorveglianza_giudizi set id_classyfarm = 7343 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2582 ) and giudizio = 'Si';
update farmacosorveglianza_giudizi set id_classyfarm = 7351 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2583 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7355 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2584 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7359 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2585 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7364 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2586 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7367 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2587 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7371 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2588 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7375 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2589 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7379 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2590 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7382 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2591 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7384 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2592 ) and giudizio = 'No';

update farmacosorveglianza_giudizi set id_classyfarm = 7204 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2549 ) and giudizio = 'no';
update farmacosorveglianza_giudizi set id_classyfarm = 7210 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2550 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7215 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2551 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7224 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2553 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7227 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2554 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7231 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2555 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7235 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2556 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7239 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2557 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7243 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2558 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7247 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2559 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7251 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2560 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7255 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2561 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7259 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2562 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7263 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2563 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7267 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2564 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7271 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2565 ) and giudizio = 'In caso di risposta NO motivare:';
update farmacosorveglianza_giudizi set id_classyfarm = 7274 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2566 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7278 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2567 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7282 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2568 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7287 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2569 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7295 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2571 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7300 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2572 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7303 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2573 ) and giudizio = 'Si';
update farmacosorveglianza_giudizi set id_classyfarm = 7308 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2574 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7313 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2575 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7316 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2576 ) and giudizio = 'A volte/saltuariamente';
update farmacosorveglianza_giudizi set id_classyfarm = 7321 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2577 ) and giudizio = 'su specifica diagnosi';
update farmacosorveglianza_giudizi set id_classyfarm = 7327 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2578 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7332 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2579 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7335 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2580 ) and giudizio = 'Smaltimento come materiale di categoria 2 ai sensi del Reg. 1069/2009';
update farmacosorveglianza_giudizi set id_classyfarm = 7339 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2581 ) and giudizio = 'Si, con segnalazione di farmacovigilanza';
update farmacosorveglianza_giudizi set id_classyfarm = 7347 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2582 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7350 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2583 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7353 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2584 ) and giudizio = 'Si';
update farmacosorveglianza_giudizi set id_classyfarm = 7358 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2585 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7363 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2586 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7366 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2587 ) and giudizio = 'Si, in via esclusiva';
update farmacosorveglianza_giudizi set id_classyfarm = 7370 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2588 ) and giudizio = 'Si';
update farmacosorveglianza_giudizi set id_classyfarm = 7374 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2589 ) and giudizio = 'Si';
update farmacosorveglianza_giudizi set id_classyfarm = 7378 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2590 ) and giudizio = 'Si';
update farmacosorveglianza_giudizi set id_classyfarm = 7381 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2591 ) and giudizio = 'Patologie riscontrate:';
update farmacosorveglianza_giudizi set id_classyfarm = 7388 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2592 ) and giudizio = 'NA';


update farmacosorveglianza_giudizi set id_classyfarm = 7205 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2549 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7212 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2550 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7214 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2551 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7222 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2553 ) and giudizio = 'NA - Autoconsumo';
update farmacosorveglianza_giudizi set id_classyfarm = 7226 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2554 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7230 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2555 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7234 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2556 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7238 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2557 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7242 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2558 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7246 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2559 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7250 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2560 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7254 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2561 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7258 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2562 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7262 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2563 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7266 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2564 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7276 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2566 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7277 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2567 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7281 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2568 ) and giudizio = 'SI';
update farmacosorveglianza_giudizi set id_classyfarm = 7286 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2569 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7293 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2571 ) and giudizio = 'In parte';
update farmacosorveglianza_giudizi set id_classyfarm = 7298 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2572 ) and giudizio = 'In parte';
update farmacosorveglianza_giudizi set id_classyfarm = 7304 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2573 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7307 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2574 ) and giudizio = 'No';
update farmacosorveglianza_giudizi set id_classyfarm = 7312 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2575 ) and giudizio = 'Sempre';
update farmacosorveglianza_giudizi set id_classyfarm = 7319 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2576 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7323 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2577 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7324 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2578 ) and giudizio = 'No';
update farmacosorveglianza_giudizi set id_classyfarm = 7331 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2579 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7334 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2580 ) and giudizio = 'Smaltimento in concimaia';
update farmacosorveglianza_giudizi set id_classyfarm = 7342 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2581 ) and giudizio = 'NOTE/EVIDENZE';
update farmacosorveglianza_giudizi set id_classyfarm = 7346 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2582 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7349 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2583 ) and giudizio = 'Si';
update farmacosorveglianza_giudizi set id_classyfarm = 7354 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2584 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7357 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2585 ) and giudizio = 'Si';
update farmacosorveglianza_giudizi set id_classyfarm = 7362 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2586 ) and giudizio = '< 1 volta per ciclo produttivo';
update farmacosorveglianza_giudizi set id_classyfarm = 7365 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2587 ) and giudizio = 'Non in via esclusiva';
update farmacosorveglianza_giudizi set id_classyfarm = 7369 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2588 ) and giudizio = 'No';
update farmacosorveglianza_giudizi set id_classyfarm = 7373 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2589 ) and giudizio = 'No';
update farmacosorveglianza_giudizi set id_classyfarm = 7377 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2590 ) and giudizio = 'No';
update farmacosorveglianza_giudizi set id_classyfarm = 7387 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2592 ) and giudizio = 'NOTE/EVIDENZE';


update farmacosorveglianza_giudizi set id_classyfarm = 7207 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2549 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7211 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2550 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7221 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2553 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7273 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2566 ) and giudizio = 'NO';
update farmacosorveglianza_giudizi set id_classyfarm = 7294 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2571 ) and giudizio = 'Si';
update farmacosorveglianza_giudizi set id_classyfarm = 7297 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2572 ) and giudizio = 'No';
update farmacosorveglianza_giudizi set id_classyfarm = 7311 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2575 ) and giudizio = 'A volte';
update farmacosorveglianza_giudizi set id_classyfarm = 7317 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2576 ) and giudizio = 'Sempre/sistematicamente';
update farmacosorveglianza_giudizi set id_classyfarm = 7328 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2579 ) and giudizio = 'Mai';
update farmacosorveglianza_giudizi set id_classyfarm = 7333 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2580 ) and giudizio = 'Alimentazione di vitelli, agnelli o capretti';
update farmacosorveglianza_giudizi set id_classyfarm = 7341 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2581 ) and giudizio = 'NA';
update farmacosorveglianza_giudizi set id_classyfarm = 7345 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2582 ) and giudizio = 'No';
update farmacosorveglianza_giudizi set id_classyfarm = 7361 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2586 ) and giudizio = '1 volta per ciclo produttivo';
update farmacosorveglianza_giudizi set id_classyfarm = 7386 where id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2592 ) and giudizio = 'Si';


update farmacosorveglianza_giudizi set id_classyfarm = 7381 where giudizio in ('Respiratorie',
'Gastroenteriche',
'Sfera riproduttiva',
'Mastite',
'Zoppia',
'Cutanee e annessi cutanei'
);


update farmacosorveglianza_giudizi set id_classyfarm = 7223 where giudizio in ('Cartaceo',
'Elettronico') and id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2553);


update farmacosorveglianza_giudizi set id_classyfarm = 7275 where giudizio in ('Cartaceo',
'Elettronico') and id_domanda in (select id from farmacosorveglianza_domande where id_classyfarm = 2566);

-- Gestisco domande e risposte estese

-- Creo domande ext
CREATE TABLE farmacosorveglianza_domande_ext (id serial, domanda text, codice text, trashed_date timestamp without time zone, id_classyfarm integer);
alter sequence farmacosorveglianza_domande_ext_id_seq RESTART with 10000000;

CREATE TABLE farmacosorveglianza_risposte_ext (id serial, id_domanda integer, risposta text, tipo text, trashed_date timestamp without time zone, id_classyfarm integer);
alter sequence farmacosorveglianza_risposte_ext_id_seq RESTART with 10000000;

insert into farmacosorveglianza_domande_ext(domanda, codice, id_classyfarm) values ('N. check list (numerazione interna ASL)', 'NUM_CHECKLIST', 2534);
insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'NUM_CHECKLIST'), '', 'text', 7165);

insert into farmacosorveglianza_domande_ext(domanda, codice, id_classyfarm) values ('Veterinario ufficiale', 'VET_UFFICIALE', 2535);
insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'VET_UFFICIALE'), '', 'text', 7167);

insert into farmacosorveglianza_domande_ext(domanda, codice, id_classyfarm) values ('Ndeg totale animali', 'NDEG_TOT', 2545);
insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'NDEG_TOT'), '', 'text', 7190);


insert into farmacosorveglianza_domande_ext(domanda, codice, id_classyfarm) values ('La scorta di medicinali veterinari e presente?', 'SCORTA_PRESENTE', 2547);
insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'SCORTA_PRESENTE'), 'Si', 'text', 7194);
insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'SCORTA_PRESENTE'), 'No', 'text', 7195);
insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'SCORTA_PRESENTE'), 'Se presente, indicare Ndeg', 'text', 7196);
insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'SCORTA_PRESENTE'), 'data aut.:', 'text', 7197);

insert into farmacosorveglianza_domande_ext(domanda, codice, id_classyfarm) values (
'Riassunto Area D - valutazione del rischio antibiotico-resistenza:',
'RISCHIO' ,
2597);

insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'RISCHIO'), 'RISCHIO ALTO (> 90)', 'text', 7400);
insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'RISCHIO'), 'RISCHIO BASSO (fino a 60)', 'text', 7398);
insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'RISCHIO'), 'RISCHIO MEDIO (da 61 a 90)', 'text', 7399);

insert into farmacosorveglianza_domande_ext(domanda, codice, id_classyfarm) values (
'Punteggio di rischio ottenuto', 
'PUNTEGGIO_RISCHIO', 
2598);

insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'PUNTEGGIO_RISCHIO'), '', 'text', 7402);


insert into farmacosorveglianza_domande_ext(domanda, codice, id_classyfarm) values (
'ESITO DEL CONTROLLO', 
'ESITO_CONTROLLO', 
2599);

insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'ESITO_CONTROLLO'), 'FAVOREVOLE', 'text', 7404);
insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'ESITO_CONTROLLO'), 'SFAVOREVOLE', 'text', 7405);

insert into farmacosorveglianza_domande_ext(domanda, codice, id_classyfarm) values (
'NOTE/OSSERVAZIONI DEL CONTROLLORE :', 
'NOTE_CONTROLLORE', 
2602);

insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'NOTE_CONTROLLORE'), '', 'text', 7413);

insert into farmacosorveglianza_domande_ext(domanda, codice, id_classyfarm) values (
'NOTE/OSSERVAZIONI DEL PROPRIETARIO:', 
'NOTE_PROPRIETARIO', 
2603);

insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'NOTE_PROPRIETARIO'), '', 'text', 7415);

insert into farmacosorveglianza_domande_ext(domanda, codice, id_classyfarm) values (
'PRESCRIZIONI - Sono state assegnate prescrizioni?',
'PRESCRIZIONI' ,
2600);

insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'PRESCRIZIONI'), 'Entro quale data dovranno essere eseguite?', 'text', 7409);
insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'PRESCRIZIONI'), 'Se si, quali:', 'text', 7408);
insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'PRESCRIZIONI'), 'No', 'text', 7407);
insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'PRESCRIZIONI'), 'Si', 'text', 7406);

insert into farmacosorveglianza_domande_ext(domanda, codice, id_classyfarm) values (
'PRESCRIZIONI ESEGUITE:',
'PRESCRIZIONI_ESEGUITE',
1567);

insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'PRESCRIZIONI_ESEGUITE'), 'Descrizione:', 'text', 4157);

insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'PRESCRIZIONI_ESEGUITE'), 'Si', 'text', 4155);

insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'PRESCRIZIONI_ESEGUITE'), 'No', 'text', 4156);

insert into farmacosorveglianza_domande_ext(domanda, codice, id_classyfarm) values (
'DATA VERIFICA PRESCRIZIONI', 
'PRESCRIZIONI_DATA_VERIFICA', 
2604);


insert into farmacosorveglianza_domande_ext(domanda, codice, id_classyfarm) values (
'SANZIONI APPLICATE - Sono state applicate sanzioni?',
'SANZIONI' ,
2601);

insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'SANZIONI'), 'Se altro specificare:', 'text', 7411);
insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'SANZIONI'), 'Nessuna sanzione - 0; Blocco Movimentazioni - 2; Abbattimento capi - 3; Amministrativa / pecuniaria - 1; Sequestro capi - 1; Informativa in Procura - 1; Altro (specificare) - 1', 'list', 7410);

insert into farmacosorveglianza_risposte_ext(id_domanda, risposta, tipo, id_classyfarm) values ((select id from farmacosorveglianza_domande_ext where codice = 'PRESCRIZIONI_DATA_VERIFICA'), '', 'text', 7417);

insert into lookup_chk_classyfarm_mod(description, codice_gisa, codice_classyfarm)
values ('CONTROLLI DI FARMACOSORVEGLIANZA IN AZIENDA ZOOTECNICA', 'farmacosorveglianza-02', '60');


-- Costruisco json di lista domande e risposte per setchecklist 

SELECT 1, '{"IDCLCheckList": "60","IDCheckList": "farmacosorveglianza-02","IDRegione": "U150","IDASL": "R201","DataValidità": "2021-01-01T07:47:46.559Z","Domande": ['

UNION

(select 2, 
'{"IDCLDomanda": '||d.id_classyfarm||',"IDDomanda": '||d.id||',"DescrDomanda": "'|| regexp_replace(replace(d.domanda, '''', ''), E'[\\n\\r]+', ' ', 'g' )||'","Risposte": ['
|| string_agg('{"IDRisposta": '||r.id||',"Valore": "'||r.giudizio||'", "IDCLRisposta": '||r.id_classyfarm||'}', ',')
||']},'

from farmacosorveglianza_domande d
join farmacosorveglianza_giudizi r on d.id = r.id_domanda
group by d.id_classyfarm, d.id, d.domanda
order by d.ordine)

UNION

(select 3, 
'{"IDCLDomanda": '||d.id_classyfarm||',"IDDomanda": '||d.id||',"DescrDomanda": "'|| regexp_replace(replace(d.domanda, '''', ''), E'[\\n\\r]+', ' ', 'g' )||'","Risposte": ['
|| string_agg('{"IDRisposta": '||r.id||',"Valore": "'||r.risposta||'", "IDCLRisposta": '||r.id_classyfarm||'}', ',')
||']},'

from farmacosorveglianza_domande_ext d
join farmacosorveglianza_risposte_ext r on d.id = r.id_domanda
group by d.id_classyfarm, d.id, d.domanda
order by d.id)

UNION

SELECT 4, ']}'

order by 1

--Importante: cancellare l'ultima virgola nell'ultimo pezzo
-- ID ASL CABLATA A R201 FINO A QUANDO NON LEVANO OBBLIGATORIETA
-- inviare questo json a setChecklist

-------------------------------------- GESTIONE INVIO

alter table farmacosorveglianza_istanze add column data_invio timestamp without time zone;
alter table farmacosorveglianza_istanze add column id_esito_classyfarm integer;
alter table farmacosorveglianza_istanze add column descrizione_errore_classyfarm text;
alter table farmacosorveglianza_istanze add column descrizione_messaggio_classyfarm text;

CREATE OR REPLACE FUNCTION public.get_info_chk_farmacosorveglianza_istanza(
    IN _idcu integer)
  RETURNS TABLE(id integer, bozza boolean, id_esito_classyfarm integer, descrizione_errore_classyfarm text, descrizione_messaggio_classyfarm text, data_invio timestamp without time zone) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
RETURN QUERY
select ist.id, ist.bozza, ist.id_esito_classyfarm, ist.descrizione_errore_classyfarm, ist.descrizione_messaggio_classyfarm, ist.data_invio from farmacosorveglianza_istanze ist 
where ist.id_controllo = _idcu and ist.trashed_date is null;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_info_chk_farmacosorveglianza_istanza(integer)
  OWNER TO postgres;
  
  
alter table farmacosorveglianza_istanze add column note text;

CREATE TABLE log_invio_farmacosorveglianza(id serial primary key, data_invio timestamp without time zone default now(), id_utente integer, tipo_invio text, id_invio_massivo integer, id_esito_classyfarm integer, descrizione_errore_classyfarm text, descrizione_messaggio_classyfarm text, id_istanza integer, id_controllo integer);


CREATE OR REPLACE FUNCTION public.farmacosorveglianza_ist_aggiorna_invio(
    _id integer,
    _id_esito_classyfarm integer,
    _descrizione_errore_classyfarm text,
    _descrizione_messaggio_classyfarm text,
    _id_invio_massivo integer,
    _id_utente integer)
  RETURNS boolean AS
$BODY$
DECLARE
idAlleg integer;

BEGIN

update farmacosorveglianza_istanze set note = concat_ws('*',note,'Aggiornamento dello stato tramite dbi farmacosorveglianza_ist_aggiorna_invio(integer,integer, text, text, integer)'),
id_esito_classyfarm = _id_esito_classyfarm, descrizione_errore_classyfarm =_descrizione_errore_classyfarm, descrizione_messaggio_classyfarm= _descrizione_messaggio_classyfarm, modifiedby = _id_utente, modified=now(), data_invio=now() where id=_id;

insert into log_invio_farmacosorveglianza (id_utente, tipo_invio, id_invio_massivo, id_esito_classyfarm, descrizione_errore_classyfarm, descrizione_messaggio_classyfarm, id_istanza, id_controllo) values (_id_utente, case when _id_invio_massivo > 0 then 'massivo' else 'puntuale' end, _id_invio_massivo, _id_esito_classyfarm, _descrizione_errore_classyfarm, _descrizione_messaggio_classyfarm, _id, (select id_controllo from farmacosorveglianza_istanze where id = _id))  ;

return true;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
  
  
CREATE OR REPLACE FUNCTION public.farmacosorveglianza_ist_aggiorna_stato(
    _id integer,
    _bozza boolean,
    _id_utente integer)
  RETURNS boolean AS
$BODY$

BEGIN
update farmacosorveglianza_istanze set note = concat_ws('*',note,'Aggiornamento dello stato tramite dbi farmacosorveglianza_ist_aggiorna_stato(integer,boolean, integer)'),
bozza= _bozza, modifiedby = _id_utente, modified=now() where id=_id;

return true;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;

  

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_farmacosorveglianza(_id integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select
concat(
'{ "IDCLCheckList": "', (select codice_classyfarm from lookup_chk_classyfarm_mod where codice_gisa = 'farmacosorveglianza-02') , '",
  "IDCL": "', 'farmacosorveglianza-02' , '",
  "IDRegione": "', 'U150' , '",
  "IDASL": "', 'R'||t.site_id , '",
  "DataVisita": "', t.assigned_date , '",
  "DataCompilazione": "', i.entered , '",
  "IDAzienda": "', o.account_number , '",
  "IDFiscale": "', o.codice_fiscale_rappresentante , '",
  "CodiceSpecie": "', o.specie_allevamento , '",
  "TpOperazione": 0,
  "ListaRisposte": [' , (select * from get_chiamata_ws_insert_farmacosorveglianza_risposte (_id)) ,'
  ]}') into ret
  
  from farmacosorveglianza_istanze i
  left join ticket t on t.ticketid = i.id_controllo
  left join organization o on o.org_id = t.org_id
  where i.id = _id;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
  
  
  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_farmacosorveglianza_risposte(_id integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select

string_agg(concat('{"IDDomanda": ', risp.id_domanda, ',
      "DescrDomanda": "', regexp_replace(replace(risp.descr_domanda, '''', ''), E'[\\n\\r]+', ' ', 'g' ), '",
      "IDRisposta": ', risp.id_risposta, ',
      "Valore": "', risp.valore, '"}'), ',') into ret
      
FROM (

SELECT

d.id as id_domanda,
d.domanda as descr_domanda,
g.id as id_risposta,
g.giudizio as valore
from farmacosorveglianza_istanze ist
left join farmacosorveglianza_risposte_giudizi ris on ris.id_istanza = ist.id
left join farmacosorveglianza_domande d on d.id = ris.id_domanda
left join farmacosorveglianza_giudizi g on g.id = ris.id_giudizio
where ist.id = _id and ist.trashed_date is null and ris.trashed_date is null

UNION

SELECT

d.id as id_domanda,
d.domanda as descr_domanda,
gevi.id as id_risposta,
evi.evidenze as valore
from farmacosorveglianza_istanze ist
left join farmacosorveglianza_risposte_giudizi ris on ris.id_istanza = ist.id
left join farmacosorveglianza_domande d on d.id = ris.id_domanda
left join farmacosorveglianza_giudizi gevi on gevi.id_domanda = ris.id_domanda and gevi.giudizio = 'NOTE/EVIDENZE'
left join farmacosorveglianza_risposte_evidenze evi on evi.id_istanza = ris.id_istanza and evi.id_domanda = ris.id_domanda
where ist.id = _id and ist.trashed_date is null and ris.trashed_date is null

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, d.num_checklist as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'NUM_CHECKLIST' and re.risposta = ''

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, d.veterinario_ispettore as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'VET_UFFICIALE' and re.risposta = ''

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, '10' as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'NDEG_TOT' and re.risposta = ''


UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, re.risposta as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'SCORTA_PRESENTE' and (case when d.presente_scorta = 'S' then re.risposta = 'Si' when d.presente_scorta = 'N' then re.risposta = 'No' end)

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, d.veterinario_responsabile as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'SCORTA_PRESENTE' and re.risposta = 'Se presente, indicare Ndeg'

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, d.num_data_aut as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'SCORTA_PRESENTE' and re.risposta = 'data aut.:'

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, re.risposta as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'RISCHIO' and (case when d.rischio = 'A' then re.risposta = 'RISCHIO ALTO (> 90)' when d.rischio = 'M' then re.risposta = 'RISCHIO MEDIO (da 61 a 90)'  when d.rischio = 'B' then re.risposta = 'RISCHIO BASSO (fino a 60)' end)

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, d.punteggio_totale::text as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'PUNTEGGIO_RISCHIO' and re.risposta = ''

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, re.risposta as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'ESITO_CONTROLLO' and (case when d.esito_controllo = 'S' then re.risposta = 'FAVOREVOLE' when d.esito_controllo = 'N' then re.risposta = 'SFAVOREVOLE' end)

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta,
concat(
case when d.sanzioni_blocco<>'S' and d.sanzioni_abbattimento <>'S' and d.sanzioni_amministrativa <>'S' and d.sanzioni_sequestro <>'S' and d.sanzioni_informativa <>'S' and d.sanzioni_altro <>'S' then split_part(re.risposta, ';', 1)||';' else '' end,
case when d.sanzioni_blocco ='S' then split_part(re.risposta, ';', 2)||';' else '' end,
case when d.sanzioni_abbattimento='S' then split_part(re.risposta, ';', 3)||';' else '' end,
case when d.sanzioni_amministrativa='S' then split_part(re.risposta, ';', 4)||';' else '' end,
case when d.sanzioni_sequestro='S' then split_part(re.risposta, ';', 5)||';' else '' end,
case when d.sanzioni_informativa='S' then split_part(re.risposta, ';', 6)||';' else '' end,
case when d.sanzioni_altro='S' then split_part(re.risposta, ';', 7)||';' else '' end
) as valore

from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'SANZIONI' and re.risposta ilike '%blocco movimentazioni%'

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, d.sanzioni_altro_descrizione as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'SANZIONI' and re.risposta = 'Se altro specificare:'

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, d.note_controllore as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'NOTE_CONTROLLORE' and re.risposta = ''

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, d.note_proprietario as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'NOTE_PROPRIETARIO' and re.risposta = ''

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, re.risposta as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'PRESCRIZIONI' and (case when d.prescrizioni_assegnate = 'S' then re.risposta = 'Si' when d.prescrizioni_assegnate = 'N' then re.risposta = 'No' end)

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, d.prescrizioni_data as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'PRESCRIZIONI' and re.risposta = 'Entro quale data dovranno essere eseguite?'

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, d.prescrizioni_descrizione as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'PRESCRIZIONI' and re.risposta = 'Se si, quali:'

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, re.risposta as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'PRESCRIZIONI_ESEGUITE' and (case when d.prescrizioni_verifica_eseguite = 'S' then re.risposta = 'Si' when d.prescrizioni_verifica_eseguite = 'N' then re.risposta = 'No' end)

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, d.prescrizioni_verifica_descrizione as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'PRESCRIZIONI_ESEGUITE' and re.risposta = 'Descrizione:'

UNION

select de.id as id_domanda, de.domanda as descr_domanda, re.id as id_risposta, d.prescrizioni_verifica_data as valore
from farmacosorveglianza_domande_ext de
left join farmacosorveglianza_risposte_ext re on de.id = re.id_domanda
left join farmacosorveglianza_dati d on d.id_istanza = _id
where de.codice = 'PRESCRIZIONI_DATA_VERIFICA' and re.risposta = ''

order by 1 asc
) risp;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
