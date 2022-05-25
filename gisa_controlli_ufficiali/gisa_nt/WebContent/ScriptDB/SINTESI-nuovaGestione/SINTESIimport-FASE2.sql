alter table sintesis_stabilimenti_import add column recuperato_org_id boolean;
alter table sintesis_stabilimenti_import  add column org_id_da_convergere integer;

-- 61 record di CU e sottoattività associati a 25 stabilimenti che sono fuori mapping
select distinct(org_id)
--tipologia, id_stabilimento, id_apiario, * 
from ticket where trashed_date is null and org_id in (
select org_id
--s.description as stato_stabilimento, o.numaut as approval_number_gisa, o.entered, o.modified, 
--i.riferimento_org_id, o.org_id, 
--* 
from organization o
left join sintesis_stabilimenti_import i on o.numaut = i.approval_number
left join lookup_stato_lab s on s.code = o.stato_lab
where tipologia in (3,97) --and i.stato_import <> 0
and trashed_date is null and numaut is null 
and direct_bill <> true 
order by o.entered, o.modified ) 

-- 111 record di stabilimenti SINTESIS con stato IN DOMANDA o null e senza approval_number
select org_id, s.description as stato_stabilimento, o.numaut as approval_number_gisa, o.entered, o.modified,i.riferimento_org_id, i.riferimento_id_stabilimento, 
i.riferimento_org_id, o.org_id, * 
from organization o
left join sintesis_stabilimenti_import i on o.numaut = i.approval_number
left join lookup_stato_lab s on s.code = o.stato_lab
where tipologia in (3,97) --and i.stato_import <> 0
and trashed_date is null and numaut is null and (i.riferimento_org_id is null or riferimento_org_id < 0)
and direct_bill <> true 
order by o.entered, o.modified 

-- proviamo a recuperare gli stabilimenti in domanda in GISA che hanno CU e sottoattività 
select distinct (i.approval_number), i.riferimento_org_id, o.numaut as approval_number_gisa, s.description as stato_stabilimento_gisa, i.stato_sede_operativa, o.name,  i.ragione_sociale_impresa, 
o.partita_iva, i.partita_iva, o.codice_fiscale, 'update sintesis_stabilimenti_import set recuperato_org_id = true, riferimento_org_id = '||o.org_id||' where id='||i.id||';'
from organization o
left join sintesis_stabilimenti_import i on o.partita_iva = i.partita_iva and i.riferimento_org_id < 0
left join lookup_stato_lab s on s.code = o.stato_lab
where tipologia in (3,97) and o.org_id in (
select distinct(org_id)
--tipologia, id_stabilimento, id_apiario, * 
from ticket where trashed_date is null and org_id in (
select o.org_id
from organization o
left join sintesis_stabilimenti_import i on o.numaut = i.approval_number
left join lookup_stato_lab s on s.code = o.stato_lab
where tipologia in (3,97) --and i.stato_import <> 0
and trashed_date is null and numaut is null --and i.riferimento_org_id = -1
and direct_bill <> true )
)
and trashed_date is null and numaut is null 
and direct_bill <> true 

-- riverifica finale---------------------------25/07/2017

-- riverifica finale
select o.direct_bill, i.riferimento_org_id, i.riferimento_id_stabilimento, s.description as stato_stabilimento_gisa, o.name, o.partita_iva
from organization o
left join sintesis_stabilimenti_import i on o.partita_iva = i.partita_iva
left join lookup_stato_lab s on s.code = o.stato_lab
where tipologia in (3,97) and  trashed_date is null and o.org_id in (
1063206,
1090071,
1020153,
1050281,
1007099,
1081933,
1071755,
1062204,
1023418,
1072609,
1050273,
1063584,
1027988,
1080890,
1081765,
1071764,
1088127,
1064545,
1074041,
1073861,
1017771,
1048259,
1072048,
1046875,
1009911
)

-- cancellazione stabilimenti in domanda e senza CU
select 'update organization set note_hd=concat_ws(''***'',note_hd,''Cancellazione di stabilimento in domanda e senza CU via HD I livello.''), trashed_date=now() where org_id = '||org_id||';',org_id, s.description as stato_stabilimento, o.numaut as approval_number_gisa, o.entered, o.modified,i.riferimento_org_id, i.riferimento_id_stabilimento, 
i.riferimento_org_id, o.org_id, * 
from organization o
left join sintesis_stabilimenti_import i on o.numaut = i.approval_number
left join lookup_stato_lab s on s.code = o.stato_lab
where tipologia in (3,97) --and i.stato_import <> 0
and trashed_date is null and numaut is null and (i.riferimento_org_id is null or riferimento_org_id < 0)
and direct_bill <> true 
and o.org_id not in (

-- riverifica finale
select o.direct_bill, i.riferimento_org_id, i.riferimento_id_stabilimento, s.description as stato_stabilimento_gisa, o.name, o.partita_iva
from organization o
left join sintesis_stabilimenti_import i on o.partita_iva = i.partita_iva
left join lookup_stato_lab s on s.code = o.stato_lab
where tipologia in (3,97) and  trashed_date is null and o.org_id in (
1063206,
1090071,
1020153,
1050281,
1007099,
1081933,
1071755,
1062204,
1023418,
1072609,
1050273,
1063584,
1027988,
1080890,
1081765,
1071764,
1088127,
1064545,
1074041,
1073861,
1017771,
1048259,
1072048,
1046875,
1009911
)

----------------------------------- FASE 2 manuale ----------------------------
-- 16 stabilimenti in organization in domanda che non sono in SINTESIS ma che hanno CU ---> 5 sono SOLO in GISA
select distinct(o.org_id)
--o.trashed_date, l.description as stato, o.name, o.numaut,o.partita_iva, * 
from organization o
join ticket t on t.org_id = o.org_id
left join lookup_stato_lab l on l.code = o.stato_lab
where t.trashed_date is null and o.tipologia in (3,97) and o.direct_bill <> true and o.trashed_date is null
and l.description ilike 'in domanda' 
--
"saracco savino"
"Trapani carmine"
"il gioiello del sele SRL"
"salvati rizziero paolo"
"ge.ca. grassi di carrino gerardo e c. sas"
--

-- stabilimenti in organization con stato ATTIVO (ne sono 37) --> mapping su carta manuale
select o.name, o.numaut as approval_number, o.partita_iva,o.org_id
from organization o
--join ticket t on t.org_id = o.org_id
left join lookup_stato_lab l on l.code = o.stato_lab
where o.tipologia in (3,97) and o.direct_bill <> true and o.trashed_date is null
--t.trashed_date is null 
and l.description ilike 'attivo'

-- contemplo anche lo stato riconosciuto condizionato -- mapping su carta manuale
select o.name, o.numaut as approval_number, o.partita_iva
from organization o
--join ticket t on t.org_id = o.org_id
left join lookup_stato_lab l on l.code = o.stato_lab
where o.tipologia in (3,97) and o.direct_bill <> true and o.trashed_date is null
--t.trashed_date is null 
and l.description ilike '%riconosciuto%'

-- in definitiva stabilimenti che sono SOLO in GISA e non in SINTESIS sono:
"saracco savino"
"Trapani carmine"
"il gioiello del sele SRL"
"salvati rizziero paolo"
"ge.ca. grassi di carrino gerardo e c. sas"  -- in domanda e con almeno un CU
+
"FARGECO SRL";"2320";"06772491210" -- attivo con approval number 2320 e senza CU
"real grassi";"ABP 3619 TRANS 1 - 3";"03188970655"; -- attivo con approval number 3619 e con CU

--66 record finali--> 0
select 'update organization set note_hd=concat_ws(''***'',note_hd,''Cancellazione di stabilimento via HD I livello. Aggiornamento tabella di sintesis per la convergenza di eventuali CU e attività collegate.''), trashed_date = now() where org_id='||o.org_id||';',
'update sintesis_stabilimenti_import set org_id_da_convergere ='||o.org_id||' where id = '||i.id||';',
o.name, i.ragione_sociale_impresa, o.partita_iva, o.org_id, i.riferimento_org_id, i.riferimento_id_stabilimento, 
o.partita_iva, i.partita_iva ,o.numaut, i.approval_number, * from organization o 
join sintesis_stabilimenti_import i on i.ragione_sociale_impresa ilike o.name
where o.tipologia in (3,97) and trashed_date is null and direct_bill <> true
order by o.name

update sintesis_stabilimenti_import  set org_id_da_convergere = null where riferimento_org_id = org_id_da_convergere

-- CONCLUSIONE
select o.numaut, l.description as stato,o.partita_iva, *
from organization o 
left join lookup_stato_lab l on l.code = o.stato_lab
where o.tipologia in (3,97) and trashed_date is null and direct_bill <> true and l.description <> 'Revocato'
order by o.name
-- CONCLUSIONE
select o.numaut, l.description as stato,o.partita_iva, *
from organization o 
left join lookup_stato_lab l on l.code = o.stato_lab
where o.tipologia in (3,97) and trashed_date is null and direct_bill <> true and l.description <> 'Revocato'
order by o.name

------------------------------------------------------------ aggiornamenti manuali -------------------------------------------
update sintesis_stabilimenti_import set org_id_da_convergere = 1010490 where riferimento_org_id= 1018130; --ok
update sintesis_stabilimenti_import set org_id_da_convergere = 1073861 where riferimento_org_id= 903319; --ok
update sintesis_stabilimenti_import set org_id_da_convergere = 1007099 where riferimento_org_id= 1018181; --ok
update sintesis_stabilimenti_import set riferimento_org_id = 1095872, recuperato_org_id=true where id in (340,341,342); --ok
update sintesis_stabilimenti_import set riferimento_org_id = 1095914, recuperato_org_id=true where id in (1030,1031,1032); --ok
update sintesis_stabilimenti_import set org_id_da_convergere = 1071755 where riferimento_org_id= 51852; --ok
update sintesis_stabilimenti_import set org_id_da_convergere = 1071764 where riferimento_org_id= 997316; --ok doppio giro con 1074041
update sintesis_stabilimenti_import set riferimento_org_id = 1062230, recuperato_org_id=true where id in (1453,1454); --ok
update sintesis_stabilimenti_import set riferimento_org_id = 1021496, recuperato_org_id=true where id in (22); --ok
update sintesis_stabilimenti_import set riferimento_org_id = 1095374, recuperato_org_id=true where id in (1682); --ok
update sintesis_stabilimenti_import set riferimento_org_id = 1096721, recuperato_org_id=true where id in (1839); --ok
update sintesis_stabilimenti_import set org_id_da_convergere = 1064658 where riferimento_org_id= 1092707; --ok
update sintesis_stabilimenti_import set org_id_da_convergere = 1072609 where riferimento_org_id= 60112; --ok e doppio giro con 1072048
update sintesis_stabilimenti_import set riferimento_org_id = 1098160, recuperato_org_id=true where id in (2060); --ok
update sintesis_stabilimenti_import set org_id_da_convergere = 1048259 where riferimento_org_id=1018177 ; --ok
update sintesis_stabilimenti_import set riferimento_org_id = 1064240, recuperato_org_id=true where id in (2104); --ok
update sintesis_stabilimenti_import set riferimento_org_id = 1055252, recuperato_org_id=true where id in (2241); --ok
update sintesis_stabilimenti_import set org_id_da_convergere = 1005930 where riferimento_org_id=1018178 ; --ok
update sintesis_stabilimenti_import set riferimento_org_id = 1063622, recuperato_org_id=true where id in (2273,2274); --ok
update sintesis_stabilimenti_import set org_id_da_convergere = 1081765 where riferimento_org_id= 903359; --ok
update sintesis_stabilimenti_import set org_id_da_convergere = 1017771 where riferimento_org_id= 1018198; --ok
update sintesis_stabilimenti_import set riferimento_org_id = 1056357, recuperato_org_id=true where id in (32); --ok

update organization set note_hd=concat_ws('***',note_hd,'Cancellazione di stabilimento via HD I livello per aggiornamento di SINTESIS'), 
trashed_date= now() where org_id in (1010490,1073861,1007099,1095872,1095914,1071755,1071764,1062230,1021496,1095374,1096721,1018177,1005930, 1063622,1081765,1017771,1056357,
1064658,1072609,1098160,1048259,1064240,1055252,1005930,1063622,1081765,1017771,1056357)

update organization set note_hd=concat_ws('***',note_hd,'Cancellazione di stabilimento via HD I livello. Trattasi di stabilimento presente in GISA e non in SINTESIS con CU'), trashed_date = now() where org_id in (
1050281,
1063206,
1050273,
1062204,
1063584
) 

select 'update organization set note_hd=concat_ws(''***'',note_hd,''Cancellazione operatore mercato ittico. Trattasi di stabilimento importato come campo aggiuntivo nel modello SINTESIS''), trashed_date=now() where org_id='||o.org_id||';' ,o.numaut, l.description as stato,o.partita_iva, *
from organization o 
left join lookup_stato_lab l on l.code = o.stato_lab
where o.tipologia in (3,97) and trashed_date is null and direct_bill 
order by o.name


