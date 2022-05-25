---VAF
-- Function: public_functions.anagrafica_cerca_rel_stabilimento_soggetto_fisico(integer, integer, integer, date, date)
-- DROP FUNCTION public_functions.anagrafica_cerca_rel_stabilimento_soggetto_fisico(integer, integer, integer, date, date);

CREATE OR REPLACE FUNCTION public_functions.anagrafica_cerca_rel_stabilimento_soggetto_fisico(
    IN _id_stabilimento integer DEFAULT NULL::integer,
    IN _id_soggetto_fisico integer DEFAULT NULL::integer,
    IN _id_tipo_relazione integer DEFAULT NULL::integer,
    IN _data_inserimento_1 date DEFAULT NULL::date,
    IN _data_inserimento_2 date DEFAULT NULL::date)
  RETURNS TABLE(id_stabilimento integer, id_soggetto_fisico integer, id_tipo_relazione integer, data_inserimento date) AS
$BODY$
DECLARE
BEGIN
FOR id_stabilimento ,id_soggetto_fisico ,id_tipo_relazione ,data_inserimento 
in 
    select rel_stabilimenti_soggetti_fisici.id_stabilimento ,rel_stabilimenti_soggetti_fisici.id_soggetto_fisico ,
	rel_stabilimenti_soggetti_fisici.id_tipo_relazione ,rel_stabilimenti_soggetti_fisici.data_inserimento 
    from rel_stabilimenti_soggetti_fisici
    where 
    rel_stabilimenti_soggetti_fisici.data_scadenza is null and rel_stabilimenti_soggetti_fisici.data_cancellazione is null
    and (_id_stabilimento is null or rel_stabilimenti_soggetti_fisici.id_stabilimento = _id_stabilimento)
    and (_id_soggetto_fisico is null or rel_stabilimenti_soggetti_fisici.id_soggetto_fisico = _id_soggetto_fisico)
    and (_id_tipo_relazione is null or rel_stabilimenti_soggetti_fisici.id_tipo_relazione = _id_tipo_relazione)
    and (_data_inserimento_1 is null or rel_stabilimenti_soggetti_fisici.data_inserimento >= to_timestamp(to_char(_data_inserimento_1,'YYYY-MM-DD') || ' 00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US'))
    and (_data_inserimento_2 is null or rel_stabilimenti_soggetti_fisici.data_inserimento <= to_timestamp(to_char(_data_inserimento_2,'YYYY-MM-DD') || ' 23:59:59.999999','YYYY-MM-DD HH24:MI:SS.US'))
  
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.anagrafica_cerca_rel_stabilimento_soggetto_fisico(integer, integer, integer, date, date)
  OWNER TO postgres;


  
  
  ---VAF
 -- Function: public_functions.anagrafica_inserisci_rel_stabilimento_soggetto_fisico(integer, integer, integer, integer)

-- DROP FUNCTION public_functions.anagrafica_inserisci_rel_stabilimento_soggetto_fisico(integer, integer, integer, integer);

CREATE OR REPLACE FUNCTION public_functions.anagrafica_inserisci_rel_stabilimento_soggetto_fisico(
    _id_stabilimento integer,
    _id_soggetto_fisico integer,
    _id_tipo_relazione integer,
    _utente integer)
  RETURNS text AS
$BODY$

 
  BEGIN
   
    INSERT INTO rel_stabilimenti_soggetti_fisici(id_stabilimento, id_soggetto_fisico, id_tipo_relazione, enteredby)
		VALUES (_id_stabilimento, _id_soggetto_fisico, _id_tipo_relazione, _utente);
	  
  RETURN 'OK';
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.anagrafica_inserisci_rel_stabilimento_soggetto_fisico(integer, integer, integer, integer)
  OWNER TO postgres;


  
  ---VAF
  -- Function: public_functions.anagrafica_modifica_rel_stabilimento_soggetto_fisico(integer, integer, integer, integer, boolean, boolean)

-- DROP FUNCTION public_functions.anagrafica_modifica_rel_stabilimento_soggetto_fisico(integer, integer, integer, integer, boolean, boolean);

CREATE OR REPLACE FUNCTION public_functions.anagrafica_modifica_rel_stabilimento_soggetto_fisico(
    _id_stabilimento integer,
    _id_soggetto_fisico integer,
    _id_tipo_relazione integer,
    _utente integer,
    _flag_data_scadenza boolean,
    _flag_data_cancellazione boolean)
  RETURNS text AS
$BODY$

DECLARE 
	val_data_scadenza timestamp without time zone;
	val_data_cancellazione timestamp without time zone;
BEGIN
	val_data_scadenza:=null;
	val_data_cancellazione:=null;
	
	if(_flag_data_scadenza) then
		val_data_scadenza=now();
	end if;
	if(_flag_data_cancellazione) then
		val_data_cancellazione=now();
	end if;
	
	update rel_stabilimento_soggetto_fisico set 
		data_scadenza=val_data_scadenza,
		data_cancellazione=val_data_cancellazione,
		modifiedby=_utente 
	where
		data_cancellazione is null and data_scadenza is null
		and (_id_stabilimento is null or rel_stabilimento_soggetto_fisico.id_stabilimento = _id_stabilimento)
		and (_id_soggetto_fisico is null or rel_stabilimento_soggetto_fisico.id_soggetto_fisico = _id_soggetto_fisico)
		and (_id_tipo_relazione is null or rel_stabilimento_soggetto_fisico.id_tipo_relazione = _id_tipo_relazione);
		
	INSERT INTO rel_stabilimento_soggetto_fisico(id_stabilimento, id_soggetto_fisico, id_tipo_relazione, enteredby)
	VALUES (_id_stabilimento, _id_soggetto_fisico, _id_tipo_relazione, _utente);
	
	
	RETURN 'OK';
	
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.anagrafica_modifica_rel_stabilimento_soggetto_fisico(integer, integer, integer, integer, boolean, boolean)
  OWNER TO postgres;

  
  
 
  
  
  ---VAF
ALTER TABLE rel_stabilimenti_soggetti_fisici DROP COLUMN enteredby;
ALTER TABLE rel_stabilimenti_soggetti_fisici DROP COLUMN modifiedby;
ALTER TABLE rel_stabilimenti_soggetti_fisici ADD COLUMN enteredby INTEGER;
ALTER TABLE rel_stabilimenti_soggetti_fisici ADD COLUMN modifiedby INTEGER;
alter table rel_stabilimenti_soggetti_fisici rename column id_tipo_relalzione to id_tipo_relazione


---VAF
CREATE OR REPLACE FUNCTION public_functions.anagrafica_verifica_esistenza_soggetto_fisico_cf_null(_nome character varying, _cognome character varying, _comune_nascita character varying, _data_nascita timestamp without time zone)
  RETURNS integer AS
$BODY$
DECLARE
	idSoggettoFisico integer;
	 	
BEGIN
	select id into idSoggettoFisico from soggetti_fisici 
	where 
	trim(lower(nome))=trim(lower(_nome)) 
	and trim(lower(cognome))=trim(lower(_cognome)) 
	and trim(lower(comune_nascita))=trim(lower(_comune_nascita)) 
	and data_nascita=_data_nascita  
	AND
	data_cancellazione is null 
	limit 1;
	return idSoggettoFisico ;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.anagrafica_verifica_esistenza_soggetto_fisico_cf_null(character varying,character varying,character varying,timestamp without time zone)
  OWNER TO postgres;

  
  --VAF
  -- Function: public_functions.anagrafica_inserisci_soggetto_fisico(character, text, text, text, text, character, text, text, text, text, timestamp without time zone, text, boolean, text, integer)

-- DROP FUNCTION public_functions.anagrafica_inserisci_soggetto_fisico(character, text, text, text, text, character, text, text, text, text, timestamp without time zone, text, boolean, text, integer);

CREATE OR REPLACE FUNCTION public_functions.anagrafica_inserisci_soggetto_fisico(
    _titolo character,
    _cognome text,
    _nome text,
    _comune_nascita text,
    _codice_fiscale text,
    _sesso character,
    _telefono text,
    _fax text,
    _email text,
    _telefono1 text,
    _data_nascita timestamp without time zone,
    _documento_identita text,
    _provenienza_estera boolean,
    _provincia_nascita text,
    _utente integer)
  RETURNS integer AS
$BODY$

  DECLARE ret_id integer;
 
  BEGIN

IF _cognome is null and _nome is null THEN
ret_id = -1;
else
      
    INSERT INTO soggetti_fisici(titolo, cognome, nome, comune_nascita, codice_fiscale, 
				        sesso, telefono, fax, 
				       email, telefono1, data_nascita, documento_identita,  
				       provenienza_estera,  provincia_nascita,  
				        enteredby)
		VALUES (_titolo, _cognome, _nome, _comune_nascita, _codice_fiscale,  
				        _sesso, _telefono, _fax, 
				       _email, _telefono1, _data_nascita, _documento_identita,  
				       _provenienza_estera,  _provincia_nascita,  
				         _utente)
		RETURNING id into ret_id;
END IF;
  
  RETURN ret_id;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.anagrafica_inserisci_soggetto_fisico(character, text, text, text, text, character, text, text, text, text, timestamp without time zone, text, boolean, text, integer)
  OWNER TO postgres;

