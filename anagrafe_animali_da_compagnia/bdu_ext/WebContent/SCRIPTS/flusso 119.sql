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
select ut.username as login_utente_caricamento, concat(c.namefirst, ' ', c.namelast) as nominativo_utente_caricamento , m.microchip, m.id_animale is not null and m.id_animale > 0 as assegnato, m.enabled as abilitato, date_trunc('day', m.data_caricamento), an.data_registrazione, an.data_inserimento as data_inserimento_animale 
from microchips m  
left join access_ ut on ut.user_id = m.enteredby 
left join animale an on an.id = m.id_animale 
left join contact_ c on c.user_id = ut.user_id
left join role r on r.role_id = ut.role_id  
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

