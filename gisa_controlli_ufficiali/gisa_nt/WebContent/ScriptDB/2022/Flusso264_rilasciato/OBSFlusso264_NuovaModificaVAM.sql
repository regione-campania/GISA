-- DB VAM

-- Function: public.dbi_elimina_utente(text, timestamp without time zone)

-- DROP FUNCTION public.dbi_elimina_utente(text, timestamp without time zone);

CREATE OR REPLACE FUNCTION public.dbi_elimina_utente(
    _username text,
    _data_scadenza timestamp without time zone)
  RETURNS text AS
$BODY$
   DECLARE
esito text ;
errore text;
idSuperUtente int;
BEGIN

esito := '';
errore := '';

RAISE INFO '[dbi_elimina_utente] _username %', _username;
RAISE INFO '[dbi_elimina_utente] _data_scadenza %', _data_scadenza;

select us.id into idSuperUtente from utenti_super_ us 
where us.username = _username and us.trashed_date is null and us.enabled and us.data_scadenza is null;

RAISE INFO '[dbi_elimina_utente] idSuperUtente %', idSuperUtente;
	
	IF (idSuperUtente is null or idSuperUtente < 0) THEN
		esito='KO';
		errore='Non e'' stato trovato nessun utente attivo con i dati indicati.';
	ELSE

	UPDATE utenti_super_ SET modified = now(), modified_by = 964, data_scadenza = _data_scadenza, note = concat_ws(';', note, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente') WHERE id = idSuperUtente;

	UPDATE utenti_ SET modified = now(), modified_by = 964, data_scadenza = _data_scadenza, note_internal_use_only_hd = concat_ws(';', note_internal_use_only_hd, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente') WHERE superutente = idSuperUtente;
	
		esito = 'OK';
	END IF;
	RETURN '{"Esito" : "'||esito||'", "DescrizioneErrore": "'||errore||'"}';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_elimina_utente(text, timestamp without time zone)
  OWNER TO postgres;
