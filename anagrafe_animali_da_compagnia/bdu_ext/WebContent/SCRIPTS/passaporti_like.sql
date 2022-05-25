update evento_rilascio_passaporto set numero_passaporto = upper(numero_passaporto);
alter table passaporto add column note_hd text;
select distinct 'update passaporto set data_utilizzo='''||e.entered||''', id_utente_utilizzo='||e.id_utente_inserimento||', id_specie = '||a.id_specie||' , id_animale = '||e.id_animale||',note_hd=''Passaporto assegnato da HD in data 22-11-2018'' where  id = '||p.id||';',
p.id, p.nr_passaporto, ep.numero_passaporto,e.id_animale,a.id_specie
from passaporto p
left join evento_rilascio_passaporto ep on p.nr_passaporto = ep.numero_passaporto
left join evento e on e.id_evento = ep.id_evento
left join animale a on a.id = e.id_animale
where ep.id > 0 and p.id_animale < 0
order by p.nr_passaporto ;



drop function get_passaporto_priori(    IN id_in integer,    IN numero_passaporto_in text);
  drop type passaporto_priori;

CREATE TYPE passaporto_priori AS
   (id integer, nr_passaporto character varying, data_precaricamento timestamp without time zone, data_utilizzo timestamp without time zone,  
                data_modifica timestamp without time zone, id_asl_appartenenza integer , id_utente_precaricamento integer ,id_utente_utilizzo integer , id_import integer ,
                flag_abilitato boolean , data_disabilitazione timestamp without time zone, id_specie integer, id_animale integer, note_hd text,data_cancellazione timestamp without time zone);


  CREATE OR REPLACE FUNCTION get_passaporto_priori(
    IN id_in integer,
    IN numero_passaporto_in text)
  RETURNS SETOF passaporto_priori AS
$BODY$SELECT id , nr_passaporto, data_precaricamento, data_utilizzo,  data_modifica , id_asl_appartenenza  , id_utente_precaricamento  ,id_utente_utilizzo  , id_import  ,
                flag_abilitato  , data_disabilitazione , id_specie , id_animale , note_hd, data_cancellazione  
                FROM passaporto p  
                WHERE (p.nr_passaporto like trim(numero_passaporto_in) or numero_passaporto_in is null) and 
                      (id_in is null or id_in = p.id  ) and 
                      p.data_cancellazione IS NULL$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION get_passaporto_priori(integer,text)
  OWNER TO postgres;


  CREATE OR REPLACE FUNCTION public_functions.check_passaporto(
    text,
    integer,
    integer)
  RETURNS esito_controllo_mc_priori AS
$BODY$
DECLARE
	cursore 				refcursor ;
	idPassaporto integer;
	idAnimale integer;
	idAsl integer;
	enabled boolean;
	esito_controllo				esito_controllo_mc_priori ;

BEGIN


	
	open cursore for SELECT id, id_animale, id_asl_appartenenza, flag_abilitato from passaporto where trim(upper(nr_passaporto)) = trim(upper($1));
	--and id_asl_appartenenza = $2	;

	fetch cursore into idPassaporto, idAnimale, idAsl, enabled;
	
close cursore ;
	if( idPassaporto < 0 or idPassaporto is null)
	then	
		esito_controllo.esito:=1 ;
		esito_controllo.descrizione:='- Passaporto non precaricato in banca dati e non utilizzabile' ;
	else
	if (idPassaporto > 0 and enabled is false)
	then
		esito_controllo.esito:=1 ;
		esito_controllo.descrizione:='- Passaporto disabilitato e non utilizzabile' ;
		
	else
		if(idPassaporto > 0 and idAnimale > 0 and idAnimale <> $3)
		then
			esito_controllo.esito:=2 ;
			esito_controllo.descrizione := '- Passaporto utilizzato per altro animale' ;
			
		else

			if(idPassaporto > 0 and idAnimale > 0 and idAnimale = $3)
		then
			esito_controllo.esito := 4 ;
			esito_controllo.descrizione := '- Passaporto utilizzabile' ;
			
		else

			
		
		if(idPassaporto > 0 and idAsl <> $2 and -1 <> $2 )
		then
			esito_controllo.esito:=3 ;
			esito_controllo.descrizione := '- Passaporto assegnato ad asl differente' ;
			
		else	
			esito_controllo.esito := 4 ;
			esito_controllo.descrizione := '- Passaporto utilizzabile' ;

			
		
		end if;
	end if;
	end if;

end if;

end if;

return esito_controllo ;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public_functions.check_passaporto(text, integer, integer)
  OWNER TO postgres;



