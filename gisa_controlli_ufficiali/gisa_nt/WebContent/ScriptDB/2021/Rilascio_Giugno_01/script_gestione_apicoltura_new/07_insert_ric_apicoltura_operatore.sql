-- Function: public.insert_ric_apicoltura_operatore(text, text, text, integer, text, integer, integer)

-- DROP FUNCTION public.insert_ric_apicoltura_operatore(text, text, text, integer, text, integer, integer);

CREATE OR REPLACE FUNCTION public.insert_ric_apicoltura_operatore(
    _codice_fiscale_impresa text,
    _partita_iva text,
    _ragione_sociale text,
    _enteredby integer,
    _domicilio_digitale text,
    _tipo_impresa integer,
    _id_indirizzo integer)
  RETURNS integer AS
$BODY$
DECLARE 
	id_impresa integer;
	_tipo_impresa_soc integer;
        
BEGIN	
	select level into _tipo_impresa_soc from lookup_tipo_impresa_societa where code = _tipo_impresa and enabled;

	insert into suap_ric_scia_operatore(codice_fiscale_impresa, partita_iva, ragione_sociale, enteredby, modifiedby, entered, modified, 
					    domicilio_digitale, tipo_impresa, tipo_societa, id_indirizzo, id_tipo_richiesta, validato) 
	values (_codice_fiscale_impresa, _partita_iva, _ragione_sociale, _enteredby, _enteredby, now(), now(), _domicilio_digitale, 
		_tipo_impresa_soc, _tipo_impresa, _id_indirizzo, 1, false) returning id into id_impresa;

	return id_impresa;
	      
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_ric_apicoltura_operatore(text, text, text, integer, text, integer, integer)
  OWNER TO postgres;
