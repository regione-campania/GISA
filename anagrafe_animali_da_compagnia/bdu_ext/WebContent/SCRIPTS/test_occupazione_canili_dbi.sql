--drop type public_functions.info_canile
CREATE TYPE public_functions.info_canile AS
   (id_rel_stab_lp integer,
    asl_id integer,
    asl_description text,
    ragione_sociale text,
    mq_disponibili float,
    occupazioneAttuale float,
    indiceOccupazione float
    );
ALTER TYPE public_functions.info_canile
  OWNER TO postgres;

--drop function  public_functions.checkcanilelibero(integer,float,integer,integer) 
CREATE OR REPLACE FUNCTION public_functions.checkcanilelibero(
    id_canile integer,
    idtagliadaaggiungere double precision,
    idrelazioneattivita integer,
    soglia integer)
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
else
	raise exception 'Attenzione, Il soggetto da controllare non è un canile';
end if;

				                   
return ret;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.checkcanilelibero(integer, double precision, integer, integer)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.checkcanilelibero(integer, double precision, integer, integer) TO public;
GRANT EXECUTE ON FUNCTION public_functions.checkcanilelibero(integer, double precision, integer, integer) TO postgres;



-- Vista
create view canili_occupazione as 
select distinct o.id_rel_stab_lp, asl.code, asl.description, o.ragione_sociale, c.mq_disponibili, 
	         sum(t.occupazione), sum(t.occupazione)/c.mq_disponibili*100 as indice 
from animale a 
	left join lookup_taglia t on t.code = a.id_taglia 
	left join opu_operatori_denormalizzati_view o on o.id_rel_stab_lp =a.id_detentore 
	left join opu_informazioni_canile c on c.id_relazione_stabilimento_linea_produttiva = o.id_rel_stab_lp 
	left join lookup_site_id asl on asl.code = o.id_asl
where o.id_linea_produttiva = 5 
	and a.trashed_date is null 
	and a.flag_decesso is not true 
	and  a.flag_smarrimento is not true 
	and c.abusivo is not true 
	and a.id>0 
	and a.id_specie = 1 
	and c.mq_disponibili>0 
group by o.id_rel_stab_lp, asl.code, asl.description, o.ragione_sociale, c.mq_disponibili  


