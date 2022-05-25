-- Aggiunta colonna identificativo

alter table coordinate_zone_produzione add column identificativo text;

-- Recupero pregresso (lanciare la query nei message)

DO $$
DECLARE addressId integer;
DECLARE query text;
DECLARE listaQuery text;

BEGIN

 FOR addressId IN
        SELECT distinct address_id FROM coordinate_zone_produzione
    LOOP
       -- raise info 'addressId %', addressId;

query := (select string_agg('UPDATE coordinate_zone_produzione SET identificativo = '''||lettera||''' WHERE id = '||id||';', '
') from (

select id
,

case when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 1 order by id asc limit 1) then 'A' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 1 order by id asc offset 1 limit 1) then 'B' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 1 order by id asc offset 2 limit 1) then 'C' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 1 order by id asc offset 3 limit 1) then 'D' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 1 order by id asc offset 4 limit 1) then 'E' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 1 order by id asc offset 5 limit 1) then 'F' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 1 order by id asc offset 6 limit 1) then 'G' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 1 order by id asc offset 7 limit 1) then 'H' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 1 order by id asc offset 8 limit 1) then 'I' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 1 order by id asc offset 9 limit 1) then 'L' 

else '' end as lettera

from coordinate_zone_produzione 
where address_id = addressId and tipologia = 1
order by tipologia asc, id asc

) aa );

-- raise info 'query %', query;
 select concat(listaQuery, query) into listaQuery;

 query := (select string_agg('UPDATE coordinate_zone_produzione SET identificativo = '''||lettera||''' WHERE id = '||id||';', '
') from (

select id
,

case when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 2 order by id asc limit 1) then 'A' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 2 order by id asc offset 1 limit 1) then 'B' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 2 order by id asc offset 2 limit 1) then 'C' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 2 order by id asc offset 3 limit 1) then 'D' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 2 order by id asc offset 4 limit 1) then 'E' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 2 order by id asc offset 5 limit 1) then 'F' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 2 order by id asc offset 6 limit 1) then 'G' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 2 order by id asc offset 7 limit 1) then 'H' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 2 order by id asc offset 8 limit 1) then 'I' 

when id in (
select id from coordinate_zone_produzione
where address_id = addressId and tipologia = 2 order by id asc offset 9 limit 1) then 'L' 

else '' end as lettera

from coordinate_zone_produzione 
where address_id = addressId and tipologia = 2
order by tipologia asc, id asc

) aa );

-- raise info 'query %', query;
 select concat(listaQuery, query) into listaQuery;
        
 END LOOP;

    raise info 'listaQuery %', listaQuery;

END $$;

-- Aggiunta colonna identificativo

alter table coordinate_molluschi add column identificativo text;

-- Estrazione campioni pregressi

select 
o.name as ragione_sociale,
cu.ticketid as id_controllo,
ca.ticketid as id_campione,
ca.identificativo as identificativo_campione,
cu.assigned_date as data_inizio_controllo,
ca.assigned_date as data_prelievo,
string_agg('LAT: '||czp.latitude||' LON: '||czp.longitude, '; ') as coordinate_punto_prelievo 


from 
ticket cu
join ticket ca on ca.id_controllo_ufficiale = cu.ticketid::text and ca.tipologia = 2
join organization o on o.org_id = cu.org_id
left join coordinate_molluschi cm on cm.id_campione = ca.ticketid
left join organization_address oa on oa.org_id = o.org_id and oa.address_type = 5
left join coordinate_zone_produzione czp on czp.address_id = oa.address_id and czp.latitude>0 and czp.longitude > 0 and czp.tipologia = 2
where 
o.tipologia = 201 
and cu.tipologia = 3 
and o.trashed_date is null 
and cu.trashed_date is null 
and ca.trashed_date is null
and cm.id is null

group by o.name,cu.ticketid,ca.ticketid

order by cu.assigned_date desc

