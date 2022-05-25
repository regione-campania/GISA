
INSERT INTO schema_anagrafica
SELECT nextval('schema_anagrafica_id_seq'),'PCF-PCF-PCF' as codice_univoco_ml,id_gruppo_template, id_campo_configuratore, campo_esteso, enabled, data_scadenza FROM schema_anagrafica
WHERE  codice_univoco_ml = 'FARM-FARM-FARMVET';
ALTER TABLE master_list_flag_linee_attivita ADD COLUMN incompatibilita text;
-- configura la tabella dei flag per la nuova linea inserita
INSERT INTO master_list_flag_linee_attivita(id, codice_univoco,mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam,no_scia, categorizzabili, operatore_mercato, compatibilita_noscia, rev, entered, enteredby, id_linea, visibilita_asl, visibilita_regione, incompatibilita)
	SELECT nextval('master_list_flag_linee_attivita_id_seq'),'PCF-PCF-PCF' as codice_univoco,
	mobile, fisso, apicoltura, registrabili, riconoscibili, sintesis, bdu, vam,no_scia, categorizzabili, operatore_mercato, compatibilita_noscia, 10, now(), 6567, id_linea, visibilita_asl, visibilita_regione, incompatibilita 
	FROM master_list_flag_linee_attivita
	WHERE  codice_univoco = 'FARM-FARM-FARMVET' and rev=10;
update master_list_flag_linee_attivita set categoria_rischio_default  = 2 where codice_univoco = 'PCF-PCF-PCF'; 
	
-- CONFIGURAZIONE NUOVE LINEE 
--select code from opu_lookup_norme_master_list where description = 'Altra normativa' 
insert into master_list_macroarea(codice_sezione, codice_norma, norma, macroarea, id_norma,rev, entered, enteredby) values ('PCF', 'Altro','Altra normativa','POSTI DI CONTROLLO FRONTALIERI',
(select code from opu_lookup_norme_master_list where description = 'Altra normativa'),10,now(),6567) returning id;
insert into master_list_aggregazione (id_macroarea, codice_attivita, aggregazione, id_flusso_originale,rev,entered, enteredby) values (86, 'PCF', 'POSTI DI CONTROLLO FRONTALIERI', 5,10,now(),6567) returning id;
insert into master_list_linea_attivita (id_aggregazione, codice_prodotto_specie, linea_attivita, codice_univoco,rev,entered, enteredby) values (20351, 'PCF', 'POSTI DI CONTROLLO FRONTALIERI', 'PCF-PCF-PCF',10,now(),6567);

update  master_list_flag_linee_attivita set  id_linea=(select id from master_list_linea_attivita where codice_prodotto_specie ='PCF') where codice_univoco='PCF-PCF-PCF';
update  master_list_flag_linee_attivita set operatore_mercato=false, compatibilita_noscia =null where codice_univoco='PCF-PCF-PCF';

-- refresh linea in ml8
select * from master_list_view  where codice  = 'PCF-PCF-PCF';
select * from public.refresh_ml_materializzata(?)
select * from ml8_linee_attivita_nuove_materializzata where rev=10