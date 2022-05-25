-- Tabella indirizzi unici

CREATE TABLE indirizzi_unici AS

(select address_id as id, addrline1 as via, postalcode as cap,  COALESCE(lookup_province.code, -1) as provincia,  COALESCE(lookup_nazioni.code, -1) as nazione, latitude, longitude,  COALESCE(comuni1.id, -1) as comune, null as riferimento_org_id, null as riferimento_address_id, address_type, city as comune_testo, null as tiponimo, toponimo, civico, clean, topon, addrline1_old as via_old, notes as note_hd, null as id_soggetto_fisico_update_indirizzo, latitude_old, longitude_old, address_id as old_id from organization_address
left join lookup_nazioni on lookup_nazioni.description ilike organization_address.country
left join comuni1 on comuni1.nome ilike organization_address.city
left join lookup_province on (lookup_province.description ilike organization_address.state or lookup_province.cod_provincia ilike organization_address.state)
)

UNION

(select id+20000000, via, cap, case when provincia similar to '%(1|2|3|4|5|6|7|8|9|0)%' then provincia::int else -1 end as provincia, case when nazione similar to '%(1|2|3|4|5|6|7|8|9|0)%' then nazione::int 
else COALESCE(lookup_nazioni.code, -1) end as nazione, latitudine, longitudine, comune, riferimento_org_id, riferimento_address_id, address_type, comune_testo, tiponimo, toponimo, civico, clean, topon, via_old, note_hd, id_soggetto_fisico_update_indirizzo, lat_old, long_old, id as old_id from opu_indirizzo 
left join lookup_nazioni on lookup_nazioni.description ilike opu_indirizzo.nazione)

UNION

(select id+40000000, via, cap, provincia::int, nazione, latitudine, longitudine, comune, riferimento_org_id, riferimento_address_id, address_type, comune_testo, tiponimo, toponimo, civico, clean, topon, via_old, note_hd, id_soggetto_fisico_update_indirizzo, null as lat_old, null as long_old, id as old_id from sintesis_indirizzo)

UNION

(select id+60000000, via, cap, provincia, nazione, latitudine, longitudine, comune, riferimento_org_id, riferimento_address_id, address_type, comune_testo, tiponimo, toponimo, civico, clean, topon, via_old, note_hd, id_soggetto_fisico_update_indirizzo, null as lat_old, null as long_old, id as old_id  from anagrafica.indirizzi)


-- Tabella backup

create table indirizzi_unici_backup as select * from indirizzi_unici

-- Tabella duplicati: Contiene l'id indirizzo (uno a caso) di ogni indirizzo che figura più di una volta nella tabella degli indirizzi unici
--drop table indirizzi_unici_duplicati; 
create table indirizzi_unici_duplicati as
select * from (
  SELECT id, old_id,
  ROW_NUMBER() OVER(PARTITION BY via, comune, toponimo, civico, cap, provincia, latitude, longitude, address_type ORDER BY id asc) AS Row
  FROM indirizzi_unici 
  where id >= 20000000 and id < 40000000
) dups
where 
dups.Row > 1

-- Tabella dati duplicati: Contiene i dati degli indirizzi della tabella precedente
-- drop table indirizzi_unici_dati_duplicati;
create table indirizzi_unici_dati_duplicati as
select * from indirizzi_unici where id >= 20000000 and id < 40000000 and (COALESCE(via, ''), COALESCE(comune, -1), COALESCE(toponimo, -1), COALESCE(civico, ''), COALESCE(cap, ''), COALESCE(provincia, -1), COALESCE(latitude, 0), COALESCE(longitude, 0), 
COALESCE(address_type, -1)) in ( select COALESCE(via, ''), COALESCE(comune, -1), COALESCE(toponimo, -1), COALESCE(civico, ''), COALESCE(cap, ''), COALESCE(provincia, -1), 
COALESCE(latitude, 0), COALESCE(longitude, 0), COALESCE(address_type, -1) 
from indirizzi_unici where id in (select id from indirizzi_unici_duplicati )  
)

-- Estrazione indirizzi con lista di id associati : Per ogni indirizzo duplicato contiene la lista di id associati a quei dati

SELECT distinct via, comune, toponimo, civico, cap, provincia, latitude, longitude, address_type, string_agg(id::text, ', ') AS id_list, string_agg(old_id::text, ', ') AS old_id_list
FROM  indirizzi_unici_dati_duplicati
GROUP  BY via, comune, toponimo, civico, cap, provincia, latitude, longitude, address_type;

-- Seleziono il mapping indirizzi da conservare / indirizzi da convergere
select a[1]::int+20000000 as id_indirizzo_da_conservare, SUBSTRING (id_list from length(a[1])+3 for length(id_list) ) as id_indirizzi_da_far_convergere
from (
    select regexp_split_to_array(aa.id_list, ','), id_list from (
SELECT distinct via, comune, toponimo, civico, cap, provincia, latitude, longitude, address_type, string_agg(old_id::text, ', ') AS id_list
FROM  indirizzi_unici_dati_duplicati
GROUP  BY via, comune, toponimo, civico, cap, provincia, latitude, longitude, address_type
) aa
) as dt(a)

-- ne creo un bkp
create table convergenza_indirizzi_opu_bkp as (
select a[1]::int+20000000 as id_indirizzo_da_conservare, SUBSTRING (id_list from length(a[1])+3 for length(id_list) ) as id_indirizzi_da_far_convergere
from (
    select regexp_split_to_array(aa.id_list, ','), id_list from (
SELECT distinct via, comune, toponimo, civico, cap, provincia, latitude, longitude, address_type, string_agg(old_id::text, ', ') AS id_list
FROM  indirizzi_unici_dati_duplicati
GROUP  BY via, comune, toponimo, civico, cap, provincia, latitude, longitude, address_type
) aa
) as dt(a)
)