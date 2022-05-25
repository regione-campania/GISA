-- configurare le associazioni tra i piani del benessere e l'evento 
select code, codice_interno_univoco, 'insert into rel_motivi_eventi_cu(id_indicatore, id_evento_cu, entered, enteredby, cod_raggrup_ind) values('||code||',10,now(),6567,'||codice_interno_univoco||');' 
from lookup_piano_monitoraggio where codice_interno in(982) and enabled and anno = 2021;

--select * from rel_motivi_eventi_cu where id_evento_cu  = 10  
--select * from rel_motivi_eventi_cu where id_evento_cu  = 11  

select code, codice_interno_univoco, 'insert into rel_motivi_eventi_cu(id_indicatore, id_evento_cu, entered, enteredby, cod_raggrup_ind) values('||code||',11,now(),6567,'||codice_interno_univoco||');' 
from lookup_piano_monitoraggio where alias ilike '%B26_A%' and enabled and anno = 2021;

alter table lookup_chk_bns_mod  add column codice_checklist text;
update lookup_chk_bns_mod set codice_checklist ='isBoviniBufalini' where code = 29;
update lookup_chk_bns_mod set codice_checklist ='isSuini' where code = 24;
update lookup_chk_bns_mod set codice_checklist ='isGallus' where code = 27;
update lookup_chk_bns_mod set codice_checklist ='isVitelli' where code = 26;
update lookup_chk_bns_mod set codice_checklist ='isBroiler' where code = 28;
update lookup_chk_bns_mod set codice_checklist ='isAltreSpecie' where code = 25;

CREATE TABLE public.rel_indicatore_chk_bns
(
  id serial,
  id_indicatore integer,
  id_lookup_chk_bns integer,
  codice_raggruppamento integer,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.rel_indicatore_chk_bns
  OWNER TO postgres;

select *, 'insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns) values('||code||','||codice_interno_univoco||','||
case when description ilike '%SUINI%' then 24
when description ilike '%ovaiole%' or description ilike '%gallus%' then 27
when description ilike '%broiler%' then 28
when description ilike '%bovini da carne o da latte%' or description ilike '%bovini da carne o da latte con almeno 1 vitello%' or description ilike '%bufalini >50 capi con > 6 annutoli%' then 26
when description ilike '%vitelli a carne bianca%' then 26
when description ilike '%bufalini%' or description ilike '%bovini carne%' or description ilike '%bovini da latte aventi > di 50 capi%' or description ilike '%bovini da latte aventi < di 50%' or description ilike '%bovini >5 <50 con vitelli%' then 29
else 25
end
||');' 
from lookup_piano_monitoraggio  where codice_interno  = 982 and enabled and anno=2021
order by alias

update lookup_chk_bns_mod set end_date='2021-03-05' where code = 26;
insert into lookup_chk_bns_mod(code,description, default_item, level, enabled, codice_specie, nuova_gestione, tipo_allegato_bdn, versione, start_date, end_date, num_allegato, codice_checklist)
values (30,'ALLEGATO VITELLI 2021', false, 0, true, 1211,true, 30,5,'2021-03-06','3000-12-31', 30, 'isVitelli');

update lookup_chk_bns_mod set codice_checklist  ='isCondizionalita' where code=23;

-- Rimozione view
drop view bdn_candidati_sicurezza_alimentare_b11;
drop view bdn_cu_candidati_benessere_animale;
drop view bdn_cu_candidati_benessere_animale_org_opu;
drop view bdn_cu_chiusi_favorevoli_sicurezza_alimentare_b11;
drop view bdn_cu_chiusi_favorevoli_sicurezza_alimentare_b11_old;
drop view bdn_cu_da_cancellare_in_bdn;
drop view bdn_cu_inviati_ma_cancellati;
drop view bdn_candidati_sicurezza_alimentare_b11_org_opu;
DROP FUNCTION digemon.dbi_get_report_ba_allevamenti(timestamp without time zone, timestamp without time zone);
DROP FUNCTION digemon.dbi_get_report_b11_allevamenti(timestamp without time zone, timestamp without time zone);


alter table rel_indicatore_chk_bns add column data_inizio_relazione timestamp without time zone;
alter table rel_indicatore_chk_bns add column data_fine_relazione timestamp without time zone;

update rel_indicatore_chk_bns SET data_inizio_relazione ='2021-03-05';
update rel_indicatore_chk_bns SET data_fine_relazione ='3000-12-31';
update rel_indicatore_chk_bns set id_lookup_chk_bns =30 where id_lookup_chk_bns=26;


	update organization_address set city =null where trasheddate is null and city = 'null';
  	update organization_address set addrline1 =null where trasheddate is null and addrline1 = 'null';
	update rel_indicatore_chk_bns set id_lookup_chk_bns  = 29 where codice_raggruppamento = 5605; -- A13_EE deve essere associato a BOVINI/BUFALINI
	
	
update chk_bns_domande_v4 set quesito = replace (quesito, 'à', 'a''');
update chk_bns_domande_v4 set quesito = replace (quesito, 'è', 'e''');
update chk_bns_domande_v4 set quesito = replace (quesito, 'é', 'e''');
update chk_bns_domande_v4 set quesito = replace (quesito, 'ì', 'i''');
update chk_bns_domande_v4 set quesito = replace (quesito, 'ò', 'o''');
update chk_bns_domande_v4 set quesito = replace (quesito, 'ù', 'u''');
update chk_bns_domande_v4 set quesito = replace (quesito, '°', '.');

update chk_bns_domande_v4 set quesito = replace (quesito, 'È', 'E''');


select *, 'insert into rel_indicatore_chk_bns(id_indicatore, codice_raggruppamento, id_lookup_chk_bns,data_inizio_relazione, data_fine_relazione) values('||code||','||codice_interno_univoco||',23,''2021-03-05'',''3000-12-31'');' 
from lookup_piano_monitoraggio  where codice_interno  = 1483 and enabled and anno=2021;

update lookup_chk_bns_mod set description = 'ALLEGATO CONDIZIONALITA'' 2018' where code = 23;