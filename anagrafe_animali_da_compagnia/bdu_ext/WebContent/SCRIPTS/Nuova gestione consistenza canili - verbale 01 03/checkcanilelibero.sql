-- Function: public_functions.checkcanilelibero(integer, double precision, integer, integer)

-- DROP FUNCTION public_functions.checkcanilelibero(integer, double precision, integer, integer);

CREATE OR REPLACE FUNCTION public_functions.checkcanilelibero(
    id_canile integer,
    idtagliadaaggiungere double precision,
    idrelazioneattivita integer,
    soglia integer,
    data_nascita timestamp without time zone)
  RETURNS boolean AS
$BODY$
DECLARE  
 ret boolean; 
 occupazioneAttuale float;
 nuovaOccupazione float;
 occupazioneTagliaDaAggiungere float;
 nuovaSoglia float;
 cursore refcursor; 
 info_canile public_functions.info_canile;  
BEGIN 
occupazioneAttuale = 0;
ret = true;

if(data_nascita + '8 months' >= now()) then idTagliaDaAggiungere := 1 ;
end if;
	
select occupazione from lookup_taglia
into occupazioneTagliaDaAggiungere
where code = idTagliaDaAggiungere;

--Calcolo occupazione attuale
select * from canili_occupazione
	into info_canile
where id_rel_stab_lp = id_canile;
				                   
if(info_canile.occupazioneAttuale is null) then
	info_canile.occupazioneAttuale = 0;
end if;
--Fine Calcolo occupazione attuale

--Se è un canile
if (idRelazioneAttivita = 5) then
	if (info_canile.mq_disponibili > 0) then	
		nuovaOccupazione = info_canile.occupazioneAttuale + occupazioneTagliaDaAggiungere;
		nuovaSoglia = info_canile.mq_disponibili + soglia;
		
		if (nuovaOccupazione > nuovaSoglia) then
			ret = false;
		end if;
	end if;
end if;

				                   
return ret;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.checkcanilelibero(integer, double precision, integer, integer)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.checkcanilelibero(integer, double precision, integer, integer) TO public;
GRANT EXECUTE ON FUNCTION public_functions.checkcanilelibero(integer, double precision, integer, integer) TO postgres;
