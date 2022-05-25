select count(ticketid), s.org_id from ticket t
join scarti_operatore s on s.org_id=t.org_id
where t.trashed_date is null
group by s.org_id
having count(ticketid)=0

union

select ticketid from ticket t
join scarti_operatore s on s.org_id=t.id_stabilimento
where t.trashed_date is null and s.tipologia != 'OSM'
group by ticketid
having count(ticketid)=0

--561 OSM scartati con CU
select distinct(org_id) from scarti_operatore where org_id  in(select org_id from ticket where trashed_date is null) and tipologia='OSM'
select distinct(org_id) from scarti_operatore where org_id  in(select id_stabilimento from ticket where trashed_date is null) and tipologia!='OSM'

alter table scarti_operatore add column id_stabilimento integer;

select 'update scarti_operatore set id_stabilimento='||id||' where org_id='||id_operatore||' ;', * 
from opu_stabilimento_  where id_operatore in (select org_id from scarti_operatore where tipologia='AZIENDA AGRICOLA')

select * from opu_operatore_  where id= 76510
select * from opu_stabilimento_  where id_operatore  = 76510