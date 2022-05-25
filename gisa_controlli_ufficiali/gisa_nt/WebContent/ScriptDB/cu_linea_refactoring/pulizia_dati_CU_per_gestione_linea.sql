-- lavoro di pulizia per i controlli ufficiali
--1. Gestione dei campi di servizio nelle tabelle di istanza CU
--2. Verfiche JAVA
--3. Passaggio da ric a OPU deve cancellare il riferimento alle richieste

alter table linee_attivita_controlli_ufficiali add column id_vecchio_tipo_operatore integer;

update suap_ric_linee_attivita_controlli_ufficiali set note = concat_ws('***',note,'Controlli associati alle anagrafiche di tipo OPU. Record cancellato per pulizia dati da HD II livello.'), 
trashed_date = now() where id_controllo_ufficiale in (
select id_controllo_ufficiale from opu_linee_attivita_controlli_ufficiali  where id_controllo_ufficiale in  (select id_controllo_ufficiale  from suap_ric_linee_attivita_controlli_ufficiali where id_linea_attivita > 0
and id_linea_attivita not in (
select  id_linea from ricerche_anagrafiche_old_materializzata r
where r.riferimento_id_nome_tab  = 'suap_ric_scia_stabilimento')
)
);

-- cancellare i CU che risultano associati ad anagrafiche cancellate.
update suap_ric_linee_attivita_controlli_ufficiali set note = concat_ws('***',note,'Controlli associati a richieste cancellate. Record cancellato per pulizia dati da HD II livello.'), 
trashed_date = now() where id_controllo_ufficiale in 
(
select ticketid 
from ticket t
join suap_ric_scia_stabilimento s on s.alt_id = t.alt_id
join suap_ric_scia_operatore o on o.id = s.id_operatore
where (s.trashed_date is not null or o.trashed_date is not null) and t.ticketid in (
select id_controllo_ufficiale  from suap_ric_linee_attivita_controlli_ufficiali where id_linea_attivita > 0
and id_linea_attivita not in (
select  id_linea from ricerche_anagrafiche_old_materializzata r
where r.riferimento_id_nome_tab  = 'suap_ric_scia_stabilimento')  and trashed_date is null
))

-- CU cancellati dalle richieste. Trattasi di sorveglianze
update suap_ric_linee_attivita_controlli_ufficiali set note = concat_ws('***',note,'Controlli associati a richieste cancellate. Record cancellato per pulizia dati da HD II livello.'), 
trashed_date = now() where id_controllo_ufficiale in (
select t.ticketid
from ticket t
where ticketid in (
select id_controllo_ufficiale  from suap_ric_linee_attivita_controlli_ufficiali where id_linea_attivita > 0
and id_linea_attivita not in (
select  id_linea from ricerche_anagrafiche_old_materializzata r
where r.riferimento_id_nome_tab  = 'suap_ric_scia_stabilimento')  and trashed_date is null
)and t.provvedimenti_prescrittivi = 5
)

-- CU cancellati perchè associati a richieste non più disponibili nel sistema. 
update suap_ric_linee_attivita_controlli_ufficiali set note = concat_ws('***',note,'Controlli associati a richieste cancellate. Record cancellato per pulizia dati da HD II livello.'), 
trashed_date = now() where id_controllo_ufficiale in (
select t.ticketid
from ticket t
--join suap_ric_scia_stabilimento s on s.alt_id = t.alt_id
--join suap_ric_scia_operatore o on o.id = s.id_operatore
--where (s.trashed_date is not null or o.trashed_date is not null) and t.ticketid in (
where ticketid in (
select id_controllo_ufficiale  from suap_ric_linee_attivita_controlli_ufficiali where id_linea_attivita > 0
and id_linea_attivita not in (
select  id_linea from ricerche_anagrafiche_old_materializzata r
where r.riferimento_id_nome_tab  = 'suap_ric_scia_stabilimento')  and trashed_date is null
)and t.provvedimenti_prescrittivi <> 5
)

-- stabilimenti (PRIVATI) tutto ok
-- query di verifica
select id_controllo_ufficiale  
from anagrafica.linee_attivita_controlli_ufficiali where id_linea_attivita > 0
and id_linea_attivita not in (
select  id_linea from ricerche_anagrafiche_old_materializzata r
where r.riferimento_id_nome_tab  = 'stabilimenti')

--sintesis_stabilimento -- query di verifica 32 record
select *  
from sintesis_linee_attivita_controlli_ufficiali where id_linea_attivita > 0
and id_linea_attivita not in (
select  id_linea from ricerche_anagrafiche_old_materializzata r
where r.riferimento_id_nome_tab  = 'sintesis_stabilimento')

-- aggiornamento id_linea
select 'update sintesis_linee_attivita_controlli_ufficiali set id_linea_attivita = '||r.id||' 
where id_controllo_ufficiale = '||s.id_controllo_ufficiale||' and id_linea_attivita = '||s.id_linea_attivita||';',
 r.*, s.*
from sintesis_linee_attivita_controlli_ufficiali s
join ticket t on t.ticketid = s.id_controllo_ufficiale
join sintesis_relazione_stabilimento_linee_produttive r on r.id_linea_produttiva = s.id_linea_attivita and r.id_stabilimento = (t.alt_id -100000000)
where id_linea_attivita > 0 and s.id_linea_attivita in (40099,40120,40106,40214)
and r.enabled

-- apicoltura imprese (al momento nn ha linee di attività)
-- opu_stabilimento (da avviare)
-- pulizia record in opu_linee x controlli cancellati
select  'update opu_linee_attivita_controlli_ufficiali set note = concat_ws(''***'',note,''Record cancellati per controlli ufficiali cancellati. Pulizia dati effettuata da HD II livello.''), 
trashed_date = '''||t.trashed_date||''' where id_controllo_ufficiale='||t.ticketid||';' 
from ticket t
join opu_linee_attivita_controlli_ufficiali o on o.id_controllo_ufficiale = t.ticketid
where t.trashed_date is not null and t.tipologia = 3
-- verifico quanti linee di OPU non matchano tra la tabella dei CU e la ricerca (25445 di cui 18580 sono sintesis, 5557 sono associati ad istanze cancellate,
-- 721 sono presenti in sintesis, 8 associati ad ORG e 619 ad opu cancellati)
)
select 'update opu_linee_attivita_controlli_ufficiali set 
note = concat_ws(''***'',note,''Record cancellati per controlli ufficiali spostati in SINTESIS. Pulizia dati effettuata da HD II livello.''), 
trashed_date = now() where id_controllo_ufficiale='||id_controllo_ufficiale||';'
from opu_linee_attivita_controlli_ufficiali  
where flag_spostamento and trashed_date is null 
and trashed_date is null and note ilike '%importato in sintesis_linee_attivita_controlli_ufficiali%'

-- 5557 casi da pulire associati a relazioni disabilitate
select 'update opu_linee_attivita_controlli_ufficiali set 
note = concat_ws(''***'',note,''Record cancellati per controlli ufficiali associati a istanze DISABILITATE. Pulizia dati effettuata da HD II livello.''), 
trashed_date = now() where id_controllo_ufficiale='||id_controllo_ufficiale||';'
from opu_linee_attivita_controlli_ufficiali  o
join opu_relazione_stabilimento_linee_produttive rel on rel.id = o.id_linea_attivita
where rel.enabled = false and o.trashed_date is null and 
id_linea_attivita not in (
select distinct r.id_linea_attivita --linee sottoposte a CU per OPU
from ricerche_anagrafiche_old_materializzata o
join opu_linee_attivita_controlli_ufficiali r on o.id_linea = r.id_linea_attivita
where r.trashed_date is null and o.riferimento_id_nome_tab = 'opu_stabilimento') 

--721 associati a SINTESIS
select 'update opu_linee_attivita_controlli_ufficiali set 
note = concat_ws(''***'',note,''Record cancellati per controlli ufficiali associati a SINTESIS. Pulizia dati effettuata da HD II livello.''), 
trashed_date = now() where id_controllo_ufficiale='||O.id_controllo_ufficiale||';'
from opu_linee_attivita_controlli_ufficiali o
join sintesis_linee_attivita_controlli_ufficiali s on s.id_controllo_ufficiale = o.id_controllo_ufficiale
where o.trashed_date is null and 
o.id_linea_attivita not in (
select distinct r.id_linea_attivita --linee sottoposte a CU per OPU
from ricerche_anagrafiche_old_materializzata o
join opu_linee_attivita_controlli_ufficiali r on o.id_linea = r.id_linea_attivita
where r.trashed_date is null and o.riferimento_id_nome_tab = 'opu_stabilimento') 

-- 8 associati ad ORG
select 'update opu_linee_attivita_controlli_ufficiali set 
note = concat_ws(''***'',note,''Record cancellati per controlli ufficiali associati a ORGANIZATION. Pulizia dati effettuata da HD II livello.''), 
trashed_date = now() where id_controllo_ufficiale='||o.id_controllo_ufficiale||';'
from opu_linee_attivita_controlli_ufficiali o
join linee_attivita_controlli_ufficiali s on s.id_controllo_ufficiale = o.id_controllo_ufficiale
where o.trashed_date is null and 
o.id_linea_attivita not in (
select distinct r.id_linea_attivita --linee sottoposte a CU per OPU
from ricerche_anagrafiche_old_materializzata o
join opu_linee_attivita_controlli_ufficiali r on o.id_linea = r.id_linea_attivita
where r.trashed_date is null and o.riferimento_id_nome_tab = 'opu_stabilimento') 

--619 associati ad OPU
select 'update opu_linee_attivita_controlli_ufficiali set 
note = concat_ws(''***'',note,''Record cancellato per controllo ufficiale associato a OPU cancellato. Pulizia dati effettuata da HD II livello.''), 
trashed_date = now() where id_controllo_ufficiale='||o.id_controllo_ufficiale||';',
op.trashed_date, os.trashed_date, * 
from opu_linee_attivita_controlli_ufficiali o
join opu_relazione_stabilimento_linee_produttive rel on rel.id = o.id_linea_attivita
join opu_stabilimento os on os.id=rel.id_stabilimento
join opu_operatore op on op.id = os.id_operatore
where 
o.trashed_date is null and 
o.id_linea_attivita not in (
select distinct r.id_linea_attivita --linee sottoposte a CU per OPU
from ricerche_anagrafiche_old_materializzata o
join opu_linee_attivita_controlli_ufficiali r on o.id_linea = r.id_linea_attivita
where r.trashed_date is null and o.riferimento_id_nome_tab = 'opu_stabilimento') 

--verifica finale
select *
from opu_linee_attivita_controlli_ufficiali o
where 
o.trashed_date is null and 
o.id_linea_attivita not in (
select distinct r.id_linea_attivita --linee sottoposte a CU per OPU
from ricerche_anagrafiche_old_materializzata o
join opu_linee_attivita_controlli_ufficiali r on o.id_linea = r.id_linea_attivita
where r.trashed_date is null and o.riferimento_id_nome_tab = 'opu_stabilimento') 

-- ORGANIZATION
-- pulizia per cu cancellati
select 'update linee_attivita_controlli_ufficiali set 
note = concat_ws(''***'',note,''Record cancellato per controllo ufficiale cancellato. Pulizia dati effettuata da HD II livello.''), 
trashed_date = '''||t.trashed_date||''' where id_controllo_ufficiale='||t.ticketid||';',
from ticket t 
join linee_attivita_controlli_ufficiali l on t.ticketid =  l.id_controllo_ufficiale
where t.trashed_date is not null and l.trashed_date is null

-- pulizia di CU associati a SINTESIS
select 'update linee_attivita_controlli_ufficiali set 
note = concat_ws(''***'',note,''Record cancellato per controllo ufficiale associato a SINTESIS. Pulizia dati effettuata da HD II livello.''), 
trashed_date = now() where id_controllo_ufficiale='||l.id_controllo_ufficiale||';'
from linee_attivita_controlli_ufficiali l 
join sintesis_linee_attivita_controlli_ufficiali s on s.id_controllo_ufficiale = l.id_controllo_ufficiale
where l.trashed_date is null 

-- pulizia di CU associati a OPU (psql)
select l.*,
'update linee_attivita_controlli_ufficiali set 
note = concat_ws(''***'',note,''Record cancellato per controllo ufficiale associato a OPU. Pulizia dati effettuata da HD II livello.''), 
trashed_date = now() where id_controllo_ufficiale='||l.id_controllo_ufficiale||';'
from linee_attivita_controlli_ufficiali l 
join opu_linee_attivita_controlli_ufficiali s on s.id_controllo_ufficiale = l.id_controllo_ufficiale
where l.trashed_date is null and s.trashed_date is null

-- ORGANIZATION
-- pulizia di CU associati ad anagrafiche cancellate
select 'update linee_attivita_controlli_ufficiali set 
note = concat_ws(''***'',note,''Record cancellato per controllo ufficiale associato ad anagrafica cancellata. Pulizia dati effettuata da HD II livello.''), 
trashed_date = '''||o.trashed_date||''' where id_controllo_ufficiale='||l.id_controllo_ufficiale||';' ,
o.trashed_date, t.id_stabilimento, t.org_id, t.alt_id, t.id_apiario
from ticket t 
join linee_attivita_controlli_ufficiali l on t.ticketid =  l.id_controllo_ufficiale
join organization o on o.org_id = t.org_id
where t.trashed_date is null and l.trashed_date is null and o.tipologia = 1 and o.trashed_date <> '1970-01-01'
and id_linea_attivita > 0
and id_stabilimento is null 
and id_linea_attivita not in (
select distinct id_linea from ricerche_anagrafiche_old_materializzata r
where r.riferimento_id_nome_tab  = 'organization' and tipologia_operatore =1)

alter table linee_attivita_controlli_ufficiali add column codice_linea varchar;
alter table linee_attivita_controlli_ufficiali add column riferimento_nome_tab varchar;

----------------------------------------da lanciare in UFFICIALE---------------------------------------------------
--query per privati (assicurati che tutti i CU siano non cancellati) 12242
select *,
'insert into linee_attivita_controlli_ufficiali (id_controllo_ufficiale, id_linea_attivita, note, riferimento_nome_tab, codice_linea) 
values ('||id_controllo_ufficiale||','||p.id_linea_attivita||',concat_ws(''***'','''||coalesce(p.note,'')||''',''Migrazione controllo anagrafica privati''),
''anagrafica.rel_stabilimenti_linee_attivita'',
''OPR-OPR-X'');' 
from anagrafica.linee_attivita_controlli_ufficiali p 
where trashed_date is null;

--query per sintesis 135345 (psql)
select 
'insert into linee_attivita_controlli_ufficiali (id_controllo_ufficiale, id_linea_attivita, note, riferimento_nome_tab, codice_linea) 
values ('||id_controllo_ufficiale||','||p.id_linea_attivita||',concat_ws(''***'','''||coalesce(p.note,'')||''',''Migrazione controllo SINTESIS''),
''sintesis_relazione_stabilimento_linee_produttive'', 
concat_ws(''-'','''||codice_macroarea||''','''||codice_aggregazione||''','''||codice_attivita||'''));' 
from sintesis_linee_attivita_controlli_ufficiali p 
join ricerche_anagrafiche_old_materializzata r on r.id_linea = p.id_linea_attivita
where trashed_date is null and r.riferimento_id_nome_tab ilike '%sintesis%';

--query per richieste (psql)
select 
'insert into linee_attivita_controlli_ufficiali (id_controllo_ufficiale, id_linea_attivita, note, riferimento_nome_tab, codice_linea) 
values ('||id_controllo_ufficiale||','||p.id_linea_attivita||',concat_ws(''***'','''||coalesce(p.note,'')||''',''Migrazione controllo RICHIESTE''),''suap_ric_linee_attivita_controlli_ufficiali'',
concat_ws(''-'','''||codice_macroarea||''','''||codice_aggregazione||''','''||codice_attivita||'''));'  
from suap_ric_linee_attivita_controlli_ufficiali p 
join ricerche_anagrafiche_old_materializzata r on r.id_linea = p.id_linea_attivita
where trashed_date is null and r.riferimento_id_nome_tab ilike '%suap_ric%'

-- query per opu
select 
'insert into linee_attivita_controlli_ufficiali (id_controllo_ufficiale, id_linea_attivita, note, riferimento_nome_tab, codice_linea) 
values ('||id_controllo_ufficiale||','||p.id_linea_attivita||',concat_ws(''***'','''||coalesce(p.note,'')||''',''Migrazione controllo OPU''),''opu_linee_attivita_controlli_ufficiali'',
concat_ws(''-'','''||codice_macroarea||''','''||codice_aggregazione||''','''||codice_attivita||'''));'  
from opu_linee_attivita_controlli_ufficiali p 
join ricerche_anagrafiche_old_materializzata r on r.id_linea = p.id_linea_attivita
where trashed_date is null and r.riferimento_id_nome_tab ilike '%opu_%'

-- verificare se ci sono duplicati per id_CU
select 'delete from linee_attivita_controlli_ufficiali where id_controllo_ufficiale ='||id_controllo_ufficiale||';
insert into linee_attivita_controlli_ufficiali (id_controllo_ufficiale, id_linea_attivita, note, riferimento_nome_tab, codice_linea) 
values ('||id_controllo_ufficiale||','||id_linea_attivita||',concat_ws(''***'','''||coalesce(note,'')||''',''''),'''||riferimento_nome_tab||''',  
'''||codice_linea||''');',
count(*), id_controllo_ufficiale, id_linea_attivita, note,riferimento_nome_tab
from linee_attivita_controlli_ufficiali  
where trashed_date is null
group by id_controllo_ufficiale, id_linea_attivita, note, riferimento_nome_tab ,codice_linea
having count(id_controllo_ufficiale) > 1
order by 1

select 'delete from linee_attivita_controlli_ufficiali where id_controllo_ufficiale ='||id_controllo_ufficiale||';
insert into linee_attivita_controlli_ufficiali (id_controllo_ufficiale, id_linea_attivita, riferimento_nome_tab, codice_linea) 
values ('||id_controllo_ufficiale||','||id_linea_attivita||','''||riferimento_nome_tab||''',  
'''||codice_linea||''');',
count(*), id_controllo_ufficiale, id_linea_attivita,riferimento_nome_tab, codice_linea
from linee_attivita_controlli_ufficiali  
where trashed_date is null
group by id_controllo_ufficiale, id_linea_attivita,riferimento_nome_tab, codice_linea
having count(id_controllo_ufficiale) > 1
order by 1

update linee_attivita_controlli_ufficiali set trashed_date = now() where id_controllo_ufficiale = 330178 and id_linea_attivita_old = 495179;
update linee_attivita_controlli_ufficiali set trashed_date = now() where id_controllo_ufficiale = 696009 and id_linea_attivita_old = 557671;
update linee_attivita_controlli_ufficiali set trashed_date = now() where id_controllo_ufficiale = 1063300 and id_linea_attivita_old = 511190;
update linee_attivita_controlli_ufficiali set trashed_date = now() where id_controllo_ufficiale = 1138280 and id_linea_attivita_old = 345870;
update linee_attivita_controlli_ufficiali set trashed_date = now() where id_controllo_ufficiale = 1138280 and id_linea_attivita_old = 345871;
update linee_attivita_controlli_ufficiali  set riferimento_nome_tab = 'opu_relazione_stabilimento_linee_produttive' where riferimento_nome_tab = 'opu_linee_attivita_controlli_ufficiali';
update linee_attivita_controlli_ufficiali  set riferimento_nome_tab = 'suap_ric_scia_relazione_stabilimento_linee_produttive' where riferimento_nome_tab = 'suap_ric_linee_attivita_controlli_ufficiali';

select 
distinct 'update linee_attivita_controlli_ufficiali set note = concat_ws(''***'',note,''Recuperato codice linea attivita''), 
codice_linea = concat_ws(''-'','''||r.codice_macroarea||''','''||r.codice_aggregazione||''','''||r.codice_attivita||''') 
where id_linea_attivita = '||r.id_linea||' and riferimento_nome_tab is null;',  r.id_linea, r.codice_macroarea, r.codice_aggregazione, r.codice_attivita 
from linee_attivita_controlli_ufficiali  l
join ricerche_anagrafiche_old_materializzata r on r.id_linea = l.id_linea_attivita 
where trashed_date is null and codice_linea is null and r.codice_macroarea is not null
and l.riferimento_nome_tab is null
