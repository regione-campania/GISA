-- Function: public.insert_ric_apicoltura_sogg_fis(text, text, integer, text, text, integer, text, text, integer, text, timestamp without time zone, text)

-- DROP FUNCTION public.insert_ric_apicoltura_sogg_fis(text, text, integer, text, text, integer, text, text, integer, text, timestamp without time zone, text);

CREATE OR REPLACE FUNCTION public.insert_ric_apicoltura_sogg_fis(
    _nome text,
    _cognome text,
    _nazione_nascita integer,
    _codice_fiscale text,
    _sesso text,
    _enteredby integer DEFAULT NULL::integer,
    _telefono text DEFAULT NULL::text,
    _email text DEFAULT NULL::text,
    _id_indirizzo integer DEFAULT NULL::integer,
    _comune_nascita text DEFAULT NULL::text,
    _data_nascita timestamp without time zone DEFAULT NULL::timestamp without time zone,
    _documento_identita text DEFAULT NULL::text)
  RETURNS integer AS
$BODY$
DECLARE 

	_id_sogg integer;
	_comune_nascita_testo text;
	_provenienza_estera boolean;
        
BEGIN	

	IF _nazione_nascita = 106 THEN
		select nome into _comune_nascita_testo from comuni1 where id = _comune_nascita::integer limit 1;
		_provenienza_estera := false;
		
		insert into suap_ric_scia_soggetto_fisico(cognome, nome, comune_nascita, codice_fiscale, enteredby, modifiedby, sesso, telefono, email, data_nascita, indirizzo_id, provenienza_estera)
		values(_cognome, _nome, _comune_nascita_testo, _codice_fiscale, _enteredby, _enteredby, _sesso, _telefono, _email, _data_nascita, _id_indirizzo, _provenienza_estera) returning id into _id_sogg;

	ELSE
		_comune_nascita_testo := _comune_nascita;
		_provenienza_estera := true;
		insert into suap_ric_scia_soggetto_fisico(cognome, nome, comune_nascita, codice_fiscale, enteredby, modifiedby, sesso, telefono, email, data_nascita, indirizzo_id, provenienza_estera)
		values(_cognome, _nome, _comune_nascita_testo, _codice_fiscale, _enteredby, _enteredby, _sesso, _telefono, _email, _data_nascita, _id_indirizzo, _provenienza_estera) returning id into _id_sogg;
	END IF;

	return _id_sogg;
	      
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_ric_apicoltura_sogg_fis(text, text, integer, text, text, integer, text, text, integer, text, timestamp without time zone, text)
  OWNER TO postgres;
