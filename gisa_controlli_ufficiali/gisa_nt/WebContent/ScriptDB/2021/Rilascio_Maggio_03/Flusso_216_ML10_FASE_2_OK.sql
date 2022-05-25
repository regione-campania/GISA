
--9 Aggiornamento id linea per campi estesi rev 10
insert into linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly)
select f10.id_nuova_linea_attivita,
nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, l.enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly
from linee_mobili_html_fields l 
join ml8_linee_attivita_nuove_materializzata f on f.id_nuova_linea_attivita = l.id_linea
join ml8_linee_attivita_nuove_materializzata f10 on f10.codice = f.codice and f10.rev=10
where l.enabled ;


--28/04 recupero dati aggiuntivi
insert into linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly)
select (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CODAC-DET'),--dev 40559
nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, l.enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly
from linee_mobili_html_fields l 
where l.id_linea=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CODAC-VED') and enabled; 

insert into linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly)
select (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-COIAC-INGINF'),--dev 40560
nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, l.enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly
from linee_mobili_html_fields l 
where l.id_linea=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-COIAC-VEI') and enabled; --40027

insert into linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly)
select (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-COIAC-INGSUP'),--dev 40561
nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, l.enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly
from linee_mobili_html_fields l 
where l.id_linea=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-COIAC-VEI') and enabled; --40027

-- TUTTI I CANILI
--IUV-CAN-ALLGAT
--IUV-CAN-ALLCAN
--IUV-CAN-RIFRIC
--IUV-CAN-RIFSAN
--IUV-CAN-PEN
--IUV-CAN-DEG
--IUV-CAN-AAF

insert into linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly)
select (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CAN-ALLGAT'),--dev 40561
nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, l.enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly
from linee_mobili_html_fields l 
where l.id_linea=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CAN-CAN') and enabled; --40017

insert into linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly)
select (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CAN-ALLCAN'),--dev 40561
nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, l.enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly
from linee_mobili_html_fields l 
where l.id_linea=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CAN-CAN') and enabled; --40017

insert into linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly)
select (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CAN-RIFRIC'),--dev 40561
nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, l.enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly
from linee_mobili_html_fields l 
where l.id_linea=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CAN-CAN') and enabled; --40017

insert into linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly)
select (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CAN-RIFSAN'),--dev 40561
nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, l.enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly
from linee_mobili_html_fields l 
where l.id_linea=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CAN-CAN') and enabled; --40017

insert into linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly)
select (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CAN-PEN'),--dev 40561
nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, l.enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly
from linee_mobili_html_fields l 
where l.id_linea=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CAN-CAN') and enabled; --40017

insert into linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly)
select (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CAN-DEG'),--dev 40561
nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, l.enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly
from linee_mobili_html_fields l 
where l.id_linea=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CAN-CAN') and enabled; --40017

insert into linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly)
select (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CAN-AAF'),--dev 40561
nome_campo, tipo_campo, label_campo, tabella_lookup, javascript_function, javascript_function_evento, link_value, 
only_hd, label_link, l.enabled, dbi_generazione, regex_expr, messaggio_se_invalid, codice_raggruppamento, maxlength, multiple, gestione_interna,ordine,readonly
from linee_mobili_html_fields l 
where l.id_linea=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice  = 'IUV-CAN-CAN') and enabled; --40017

--10 aggiornamento visibilita asl e visibilta regione
select 'update master_list_flag_linee_attivita set visibilita_asl=false, visibilita_regione=true where rev=10 and codice_univoco='''||codice_univoco||''';',
* from ugp_gruppi_permessi_linee where id_gruppo  = 2; --43
select 'update master_list_flag_linee_attivita set visibilita_asl=true, visibilita_regione=false where rev=10 and codice_univoco='''||codice_univoco||''';',
* from ugp_gruppi_permessi_linee where id_gruppo  = 1; --306
update master_list_flag_linee_attivita set visibilita_asl  = true where rev=10 and visibilita_regione is null --170 linee

-- rendere OBS le seguenti funzioni **************da fare post rilascio **************
--gestisci_mapping --> obs
--get_esistenza_allevamento--> obs
--proponi_candidatimaxrankmapping_dalinea --> obs
-- *************************************************************************************
 
select 'update ml8_linee_attivita_nuove_materializzata set codice_norma = ''' || n.codice_norma || ''' where id_nuova_linea_attivita = ' || m.id_nuova_linea_attivita || ';'
from ml8_linee_attivita_nuove_materializzata m
left join opu_lookup_norme_master_list n on m.id_norma = n.code

-- recupero codice_norma *********************************************** è necessario????*************************************************
-- lanciare per rev 8 (tra i risultati è corretta la norma 26-14 per id 38)
select distinct 'update ml8_linee_attivita_nuove_materializzata set codice_norma='''||codice_norma||''' where id_norma='||id_norma||' and rev=8;', id_norma
from master_list_macroarea where trashed_date is null and rev=8
order by id_norma
--********************************************************************************************************************************************

--16 record ml8 rev 8
update ml8_linee_attivita_nuove_materializzata set id_norma=47, codice_norma='Altro' where  codice_norma is null

update master_list_aggregazione set aggregazione=concat(aggregazione,' (canile)') where rev=10 and codice_attivita='CAN';
update master_list_aggregazione set aggregazione=concat(aggregazione,' (commercio ingrosso)') where rev=10 and codice_attivita='COIAC';
update master_list_aggregazione set aggregazione=concat(aggregazione,' (commercio dettaglio)') where rev=10 and codice_attivita='CODAC';
update master_list_aggregazione set aggregazione=concat(aggregazione,' (addrestramento)') where rev=10 and codice_attivita='ADCA';
update master_list_aggregazione set aggregazione = 'DISTRIBUTORI AUTOMATICI' WHERE CODICE_ATTIVITA='MS.060.400' AND REV=10;
update master_list_aggregazione set trashed_date = now() where   CODICE_ATTIVITA='HORE' AND REV=10;
update master_list_linea_attivita set id_aggregazione= (select id from master_list_aggregazione  where codice_attivita ='MS.050.200' and rev=10 and trashed_date is null) where codice_univoco='MS.050-HORE-009' and rev=10;

-- categoria di default per no-scia=2
update master_list_flag_linee_attivita set categoria_rischio_default = 2 where no_scia  and categorizzabili and categoria_rischio_default is null and rev=10;

-- propagazioni vam e bdu
update  master_list_flag_linee_attivita set bdu=true  where codice_univoco = 'IUV-CAN-ALLGAT' and rev=10;
update  master_list_flag_linee_attivita set bdu=true  where codice_univoco = 'IUV-CAN-ALLCAN' and rev=10;
update  master_list_flag_linee_attivita set bdu=true  where codice_univoco = 'IUV-ADCA-ADCA' and rev=10;
update  master_list_flag_linee_attivita set bdu=true  where codice_univoco = 'IUV-CODAC-DET' and rev=10;
update  master_list_flag_linee_attivita set bdu=true  where codice_univoco = 'IUV-COIAC-INGINF' and rev=10;
update  master_list_flag_linee_attivita set bdu=true  where codice_univoco = 'IUV-COIAC-INGSUP' and rev=10;
update  master_list_flag_linee_attivita set bdu=true  where codice_univoco = 'IUV-CAN-RIFRIC' and rev=10;
update  master_list_flag_linee_attivita set bdu=true, vam=true  where codice_univoco = 'IUV-CAN-RIFSAN' and rev=10;
update  master_list_flag_linee_attivita set bdu=true  where codice_univoco = 'IUV-CAN-PEN' and rev=10;
update  master_list_flag_linee_attivita set bdu=true  where codice_univoco = 'IUV-CAN-AAF' and rev=10;
update  master_list_flag_linee_attivita set bdu=true, vam=true  where codice_univoco = 'VET-AMBV-PU' and rev=10;
update  master_list_flag_linee_attivita set bdu=true, vam=true  where codice_univoco = 'VET-CLIV-PU' and rev=10;
update  master_list_flag_linee_attivita set bdu=true, vam=true  where codice_univoco = 'VET-OSPV-PU' and rev=10;
update  master_list_flag_linee_attivita set bdu=false, vam=true  where codice_univoco = 'IUV-CAN-DEG' and rev=10;

-- aggiornamento codice_attivita per linee ml8 (erroneamente codice del terzo livello =  codice completo)
select 'update ml8_linee_attivita_nuove_materializzata set codice_attivita='''||codice_prodotto_specie||''' where id_attivita='||id||' and rev=8;',* 
from master_list_linea_attivita where rev=8;


alter table master_list_flag_linee_attivita add column note_hd text;

update master_list_flag_linee_attivita  set note_hd ='Aggiornamento flag categorizzazione per linee di "Corsi di formazione" come da mail di Diletta del 28/04/2021 ore 12:00',
 categorizzabili  = false where  rev=10 and codice_univoco in (
'ALT-FORM-OPF',
'ALT-FORM-BANA',
'ALT-FORM-OPS',
'ALT-FORM-ALI',
'ALT-FORM-BENM',
'ALT-FORM-MIC',
'ALT-FORM-TRAS',
'ALT-FORM-IAA',
'ALT-FORM-CIN',
'ALT-FORM-ECIN'
);

-- Refreshare tutto
 select * from public.refresh_ml_materializzata()