-- CHI: Bartolo Sansone
--  COSA: Cancellazione sistemata privati
--  QUANDO: 31/10/2018

CREATE OR REPLACE FUNCTION funzioni_hd_per_segnalazioni.dbi_cancellazione_privato_nuova_anagrafica(
    _altid integer,
    _note text)
  RETURNS text AS
$BODY$
DECLARE
	idStabilimento integer;
	refresh boolean;
BEGIN

select id into idStabilimento from anagrafica.stabilimenti where alt_id = _altid and data_cancellazione is null;
update anagrafica.rel_stabilimenti_linee_attivita set data_cancellazione = now(),note = concat_ws(';', note, _note) where id_stabilimento = idStabilimento and data_cancellazione is null;
update anagrafica.rel_imprese_stabilimenti set data_cancellazione = now() where id_stabilimento = idStabilimento and data_cancellazione is null;
update anagrafica.rel_stabilimenti_indirizzi set data_cancellazione = now() where id_stabilimento = idStabilimento and data_cancellazione is null;
update anagrafica.rel_stabilimenti_soggetti_fisici set data_cancellazione = now() where id_stabilimento = idStabilimento and data_cancellazione is null;
update anagrafica.stabilimenti set data_cancellazione = now(), note = concat_ws(';', note, _note) where id = idStabilimento and data_cancellazione is null; 
refresh:= (select * from refresh_anagrafica(_altid, 'anagrafica'));
return 'Esito cancellazione e refresh: ' || refresh;
		
	
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION funzioni_hd_per_segnalazioni.dbi_cancellazione_privato_nuova_anagrafica(integer, text)
  OWNER TO postgres;

  
  
  
CREATE OR REPLACE FUNCTION public.is_anagrafica_cancellabile(_altid integer)
  RETURNS boolean AS
$BODY$
DECLARE
	esito boolean;
	totcu integer;
BEGIN

esito:= true;
totcu:= 0;

select count(ticketid) into totcu from ticket where alt_id = _altid and trashed_date is null and tipologia = 3;

if totcu > 0 THEN
esito = false;
END IF;

 RETURN esito;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.is_anagrafica_cancellabile(integer)
  OWNER TO postgres;
  
  
  
CREATE OR REPLACE FUNCTION public.anagrafica_delete_centralizzato(_altid integer, _note text, _userid integer)
  RETURNS boolean AS
$BODY$
DECLARE
trasheddate timestamp without time zone;
idStabilimento integer;	
refresh boolean;
BEGIN

trasheddate := null;
idStabilimento := -1;

select data_cancellazione into trasheddate from anagrafica.stabilimenti where alt_id = _altid;

if trasheddate is not null then
return false;
END IF;

select id into idStabilimento from anagrafica.stabilimenti where alt_id = _altid and data_cancellazione is null;
update anagrafica.rel_stabilimenti_linee_attivita set data_cancellazione = now(),modifiedby = _userid, note = concat_ws(';', note, _note) where id_stabilimento = idStabilimento and data_cancellazione is null;
update anagrafica.rel_imprese_stabilimenti set data_cancellazione = now(), modifiedby = _userid  where id_stabilimento = idStabilimento and data_cancellazione is null;
update anagrafica.rel_stabilimenti_indirizzi set data_cancellazione = now(), modifiedby = _userid where id_stabilimento = idStabilimento and data_cancellazione is null;
update anagrafica.rel_stabilimenti_soggetti_fisici set data_cancellazione = now(),modifiedby = _userid where id_stabilimento = idStabilimento and data_cancellazione is null;
update anagrafica.stabilimenti set data_cancellazione = now(), modifiedby = _userid, note = concat_ws(';', note, _note) where id = idStabilimento and data_cancellazione is null; 
refresh:= (select * from refresh_anagrafica(_altid, 'anagrafica'));

return true;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;

  
