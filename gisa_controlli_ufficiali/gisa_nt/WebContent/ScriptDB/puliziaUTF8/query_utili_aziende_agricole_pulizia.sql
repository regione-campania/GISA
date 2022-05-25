--creo una tabella temporanea dove memorizzare le aziende agricole senza linea aggiornata

--72851 aziende
create table aziende_agricole_temp as (
select o.tipologia,o.name,op.ragione_sociale,o.org_id,st.id as id_stabilimento,st.id_asl,asl.description,lcodi.description as istat, latt.categoria,latt.linea_attivita as linea, o.notes
from opu_relazione_stabilimento_linee_produttive
join opu_stabilimento st  on st.id = opu_relazione_stabilimento_linee_produttive.id_stabilimento
join opu_operatore op on op.id =st.id_operatore
join opu_stabilimento_ st_ on st_.id = st.riferimento_org_id
 join organization o on o.org_id = st_.riferimento_org_id
 left join lookup_site_id asl on asl.code=st.id_asl
 left join la_imprese_linee_attivita lai on lai.org_id =o.org_id
 left join la_rel_ateco_attivita lar on lar.id = lai.id_rel_ateco_attivita
 left join lookup_codistat lcodi on lcodi.code=lar.id_lookup_codistat
 left join la_linee_attivita latt on latt.id = lar.id_linee_attivita
where id_linea_produttiva =4902 and tipologia = 7)

alter table aziende_agricole_temp ADD column tipo_ml varchar;
--script di update per attività non permanenti

--32747 non permanenti +3811+1
update aziende_agricole_temp  set tipo_ml = 'non permanenti' where (notes ilike '%cr_ali%'
 or notes ilike '%cereal%' 
 or notes ilike '%semi oleosi%' 
 or notes ilike '%barbabietola%' 
 or notes ilike '%legumi%' 
 or notes ilike '%ortaggi%'
 or notes ilike '%or_taggi%'
 or notes ilike '%tuberi%'
 or notes ilike '%bulbi%'
 or notes ilike '%patate%'
 or notes ilike '%orticol%'
 or notes ilike '%grano%'
 or notes ilike '%seminativ%'
 or notes ilike '%fiori%'
 or notes ilike '%floric%'
 or notes ilike '%produzione di bevande%'
 or notes ilike '%riso%'
 or notes ilike '%ortiv%'
 or notes ilike '%foragg%'
 or notes ilike '%fragol%'
 or notes ilike '%tabacc%'
 or notes ilike '%pomodor%' ) and tipo_ml is null


--script di update per attività permanenti
--21989 permanenti +19+522+60
 update aziende_agricole_temp  set tipo_ml = 'permanenti' where (notes ilike '%uva%' 
 or notes ilike '%agrumi%' 
 or notes ilike '%pomacee%' 
 or notes ilike '%viticol%'
 or notes ilike '%vite%'
 or notes ilike '%uva%'
 or notes ilike '%uve%'
 or notes ilike '%viti%'
 or notes ilike '%alberi da melo%'
 or notes ilike '%vign%'
 or notes ilike '%oliv%'
 or notes ilike '%olio%'
 or notes ilike '%uliv%'
 or notes ilike '%frutticol%'
 or notes ilike '%frutt%'
 or notes ilike '%vinicol%'
 or notes ilike '%vinivol%'
 or notes ilike '%nocci_ol%'
 or notes ilike '%nocciol%'
 or notes ilike '%casta_n%'
 or notes ilike '%noce%'
 or notes ilike '%noci%'
 or notes ilike '%mele%'
 or notes ilike '%pesche%'
 or notes ilike '%limon%'
 or notes ilike '%tropicale%') and tipo_ml is null
 
--update permanente e non
select * from aziende_agricole_temp  where notes ilike '%permanenti%' and tipo_ml is null and notes not ilike '%non%permanenti%'
update aziende_agricole_temp  set tipo_ml = 'permanenti' where notes ilike '%permanenti%' and tipo_ml is null and notes not ilike '%non%permanenti%'

select * from aziende_agricole_temp  where notes ilike '%non%permanenti%' and tipo_ml is null 
update aziende_agricole_temp  set tipo_ml = 'non permanenti' where notes ilike '%non%permanenti%' and tipo_ml is null 

--13702---arrivata qui
select * from aziende_agricole_temp where tipo_ml  is null order by notes

--quante aziende agricole non hanno cu tra quelle non mappabili
-- 13587 righe senza CU 
select distinct(az.org_id), 'update opu_stabilimento set trashed_date=now(), notes_hd=''ARCHIVIATA AZIENDA AGRICOLA CHE NON HA UNA LINEA DI ATTIVITA'''' MAPPABILE E NON HA CU'' where id ='||az.id_stabilimento||';',name 
from aziende_agricole_temp  az
left join ticket t on t.org_id = az.org_id
where tipo_ml is null and t.ticketid is null
--order by notes

--102
select distinct(az.org_id), name, notes from aziende_agricole_temp  az
--aziende agricole con controlli ufficiali tra quelli con linee non mappati
left join ticket t on t.org_id = az.org_id
where tipo_ml is null and t.ticketid >0

select * from opu_relazione_stabilimento_linee_produttive where id_linea_produttiva in (33,36) and enabled
select * from master_list_suap  where descrizione ilike '%produzione primaria%' and flag_nuova_gestione and enabled
select * from master_list_suap  where id_padre  = 22
select * from master_list_suap where id_padre in (29,30)

alter table aziende_agricole_temp add column id_nuova_linea integer;
alter table opu_relazione_stabilimento_linee_produttive add column id_nuova_linea_azienda_agricole integer;

update aziende_agricole_temp set id_nuova_linea =33 where  tipo_ml ='permanenti'
update aziende_agricole_temp set id_nuova_linea =36 where  tipo_ml ='non permanenti'

--57111 righe
select distinct(id_stabilimento), 'UPDATE opu_relazione_stabilimento_linee_produttive set id_nuova_linea_azienda_agricole = '||id_nuova_linea||' where id_stabilimento='||id_stabilimento||' and id_linea_produttiva=4902;'
from aziende_agricole_temp  where tipo_ml  is not null

select * from opu_relazione_stabilimento_linee_produttive where id_stabilimento  = 91804

select 'update opu_stabilimento set linee_pregresse=false where id ='||o.id||';',* 
from opu_stabilimento o
join opu_relazione_stabilimento_linee_produttive p on p.id_stabilimento = o.id
where p.id_nuova_linea_azienda_agricole > 0

--57256 viene updatat
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva = id_nuova_linea_azienda_agricole where id_nuova_linea_azienda_agricole > 0

select * from opu_relazione_stabilimento_linee_produttive  where id_nuova_linea_azienda_agricole  > 0

select * 
from opu_stabilimento o
join opu_stabilimento_ oo on o.riferimento_org_id = oo.id
