DROP FUNCTION public.inserisci_pratica(integer, text, integer, integer, text, integer, text);

-- Function: public.inserisci_pratica(integer, text, integer, integer, text, integer, text, boolean)

-- DROP FUNCTION public.inserisci_pratica(integer, text, integer, integer, text, integer, text, boolean);

CREATE OR REPLACE FUNCTION public.inserisci_pratica(
    _user_id integer,
    _data_richiesta text,
    _id_comune_ric integer,
    _id_tipo_pratica integer,
    _num_pratica text,
    _id_causale integer,
    _note_utente text,
    _flag_apicoltura boolean DEFAULT NULL::boolean)
  RETURNS text AS
$BODY$
DECLARE

	_numero_pratica text;
  
BEGIN	

	_numero_pratica := _num_pratica;

	IF length(trim(coalesce(_numero_pratica,''))) = 0 THEN

		select genera_num_pratica into _numero_pratica from genera_num_pratica();
	
	END IF;

	IF trim(_data_richiesta) = '' THEN
		_data_richiesta := null;
	END IF;

	_numero_pratica := trim(_numero_pratica);

	IF _flag_apicoltura THEN
		INSERT INTO pratiche_gins(entered, enteredby, data_richiesta, id_comune_richiedente, id_tipo_operazione, numero_pratica, id_causale, note, apicoltura)
		VALUES (now(), _user_id, _data_richiesta::timestamp without time zone , _id_comune_ric, _id_tipo_pratica, _numero_pratica, _id_causale, _note_utente, true);
	ELSE 
		INSERT INTO pratiche_gins(entered, enteredby, data_richiesta, id_comune_richiedente, id_tipo_operazione, numero_pratica, id_causale, note)
		VALUES (now(), _user_id, _data_richiesta::timestamp without time zone , _id_comune_ric, _id_tipo_pratica, _numero_pratica, _id_causale, _note_utente);
	END IF;
	
	return _numero_pratica;
	
END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.inserisci_pratica(integer, text, integer, integer, text, integer, text, boolean)
  OWNER TO postgres;
