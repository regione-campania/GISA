-- dalle condizioni del vincolo unique sul mc
select count(microchip),microchip
from animale
WHERE data_cancellazione IS NULL AND (trashed_date IS NULL or trashed_date='1970-01-01')AND microchip IS NOT NULL AND microchip::text <> ''::text 
group by microchip
having count(microchip) > 1

-- dalle condizioni del vincolo unique sul tatuaggio
select count(tatuaggio),tatuaggio
from animale
WHERE data_cancellazione IS NULL AND trashed_date IS NULL AND tatuaggio IS NOT NULL AND tatuaggio::text <> ''::text
group by tatuaggio
having count(tatuaggio) > 1

------------------------ 2 step verificare doppioni tra quelli cancellati in questa fase e aggiornamento
update animale set note_internal_use_only= note_internal_use_only || '. DA RECUPERARE' where id in (
select id from 
(
select * 
from animale
WHERE trashed_date ='1970-01-01' AND microchip IS NOT NULL AND microchip::text <> ''::text 
--group by microchip
--having count(microchip) > 1
)a where microchip not in (
	select distinct(microchip) from animale 
	where trashed_date IS NULL AND microchip IS NOT NULL AND microchip::text <> ''::text 
	)
);

-- costruzione dei duplicati incrociati con update
with lista as (
select animale.id as id_mc, animale1.id, animale.microchip as microchip1, animale1.tatuaggio
from animale, animale animale1 
where animale.microchip = animale1.tatuaggio 
and
animale.data_cancellazione is null and animale1.data_cancellazione is null and
animale.trashed_date is null and animale1.trashed_date is null 
and trim(animale.microchip) <> ''  and animale.microchip is not null
)
--select id from lista union select id_mc from lista
update animale set trashed_date = '1970-01-01' , note_internal_use_only ='.Cancellato temporaneamente in data 20/01/2016 per gestire i ###duplicati incrociati###. DA RECUPERARE'
where id in (select id from lista union select id_mc from lista); 

create unique index on animale ((least(microchip, tatuaggio)))
 WHERE data_cancellazione IS NULL AND trashed_date IS NULL and tatuaggio IS not NULL and tatuaggio <> ''::text AND microchip IS not NULL and microchip <> ''::text
;
create unique index on animale ((greatest(microchip, tatuaggio)))
 WHERE data_cancellazione IS NULL AND trashed_date IS NULL and tatuaggio IS not NULL and tatuaggio <> ''::text AND microchip IS not NULL and microchip <> ''::text

--estrazione per Valentino
select * from (
select a.id, a.microchip, 'primo mc',  a.data_nascita, s.description as specie, t.description as taglia, m.description as mantello, r.description as razza, 
asl.description as asl, prop.ragione_sociale as proprietario, det.ragione_sociale as detentore, a.data_inserimento, u.username as inserito_da
from animale a
left join lookup_specie s on s.code = a.id_specie
left join lookup_taglia t on t.code = a.id_taglia
left join lookup_mantello m on m.code = a.id_tipo_mantello
left join lookup_razza r on r.code = a.id_razza
left join lookup_site_id asl on asl.code = a.id_asl_riferimento
left join opu_relazione_stabilimento_linee_produttive relprop on relprop.id = a.id_proprietario
left join opu_stabilimento staprop on staprop.id = relprop.id_stabilimento
left join opu_operatore prop on prop.id = staprop.id_operatore
left join opu_relazione_stabilimento_linee_produttive reldet on reldet.id = a.id_detentore
left join opu_stabilimento stadet on stadet.id = reldet.id_stabilimento
left join opu_operatore det on det.id = stadet.id_operatore
left join access u on u.user_id = a.utente_inserimento
where a.id in (
select id from
animale
WHERE note_internal_use_only ilike '%da recuperare%'
) union all
select a.id, a.tatuaggio, 'secondo mc',  a.data_nascita, s.description as specie, t.description as taglia, m.description as mantello, r.description as razza, 
asl.description as asl, prop.ragione_sociale as proprietario, det.ragione_sociale as detentore, a.data_inserimento, u.username as inserito_da
from animale a
left join lookup_specie s on s.code = a.id_specie
left join lookup_taglia t on t.code = a.id_taglia
left join lookup_mantello m on m.code = a.id_tipo_mantello
left join lookup_razza r on r.code = a.id_razza
left join lookup_site_id asl on asl.code = a.id_asl_riferimento
left join opu_relazione_stabilimento_linee_produttive relprop on relprop.id = a.id_proprietario
left join opu_stabilimento staprop on staprop.id = relprop.id_stabilimento
left join opu_operatore prop on prop.id = staprop.id_operatore
left join opu_relazione_stabilimento_linee_produttive reldet on reldet.id = a.id_detentore
left join opu_stabilimento stadet on stadet.id = reldet.id_stabilimento
left join opu_operatore det on det.id = stadet.id_operatore
left join access u on u.user_id = a.utente_inserimento
where a.id in (
select id from
animale
WHERE note_internal_use_only ilike '%da recuperare%'
))a where microchip <> ''::text and microchip is not null
order by 2

