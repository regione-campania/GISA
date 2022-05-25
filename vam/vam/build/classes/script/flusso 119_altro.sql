-- Function: public_functions.dbi_bdu_caricamenti_mc_lp(integer)

-- DROP FUNCTION public_functions.dbi_bdu_caricamenti_mc_lp(integer);

CREATE OR REPLACE FUNCTION public_functions.dbi_bdu_caricamenti_mc_lp(IN anno integer)
  RETURNS TABLE(login_utente_caricamento text, nominativo_utente_caricamento text, microchip text, assegnato boolean, abilitato boolean, data_caricamento timestamp without time zone, data_registrazione timestamp without time zone, data_inserimento timestamp without time zone) AS
$BODY$
BEGIN
	FOR login_utente_caricamento, 
	nominativo_utente_caricamento, 
	microchip, 
	assegnato , 
	abilitato, 
	data_caricamento, 
	data_registrazione, 
	data_inserimento
		in
select ut.username as login_utente_caricamento, concat(c.namefirst, ' ', c.namelast) as nominativo_utente_caricamento , m.microchip, m.id_animale > 0 as assegnato, m.enabled as abilitato, m.data_caricamento, an.data_registrazione, an.data_inserimento as data_inserimento_animale 
from microchips m  
left join access_ ut on ut.user_id = m.enteredby 
left join animale an on an.id = m.id_animale 
left join contact_ c on c.user_id = ut.user_id
left join role r on r.role_id = ut.role_id  
left join lookup_site_id a on a.code = ut.user_id 
where ((anno>-1 AND date_part('year', m.data_caricamento) = anno) OR (anno=-1))  and
      r.role_id = 24 and
      an.trashed_date is null and 
      an.data_cancellazione is null and
      m.trashed_date is null
order by concat(c.namefirst, ' ', c.namelast) , m.microchip

  
  LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_bdu_caricamenti_mc_lp(integer)
  OWNER TO postgres;





select 'update microchips set  note_internal_use_only = concat (note_internal_use_only, '. 12/02/2019 - data_caricamento aggironata da hd di II Livello'), data_caricamento = where import_id = and data_caricamento is null' data, parametri from bdu_storico_operazioni_utenti 
where parametri ilike '%importId%' and path ilike '%MicrochipImports%'  limit 1



select * from bdu_storico_operazioni_utenti
where parametri ilike '%380260000560337%' order by id and path ilike '%MicrochipImports%'  



 select 'update microchips set  note_internal_use_only = concat (note_internal_use_only, ''. 12/02/2019 - data_caricamento aggiornata da hd di II Livello perchè null. ''), data_caricamento = timestamp ''' ||  data_registrazione  || ''' - interval ''' || random() * 14000 || ' hours ''  where microchip = ''' ||  microchip || ''' and data_caricamento is null;'
from public_functions.dbi_bdu_caricamenti_mc_lp(-1) where data_caricamento is null and data_registrazione is not null  
limit 10000 


select count(*) from public_functions.dbi_bdu_caricamenti_mc_lp(-1) where data_caricamento is null and data_registrazione is not null  limit 1


