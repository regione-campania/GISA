--chi: Antonio Riviezzo
--cosa: dbi per la ricerca di una relazione tra impresa mobile e una linea di attivita sulla vecchia anagrafica
--quando: 30/03/2018

-- Function: anagrafica.anagrafica_cerca_rel_impresa_mobile_linea_attivita_old_anag(integer, integer, integer, integer, text, integer, character varying, character varying, date, date, text)

-- DROP FUNCTION anagrafica.anagrafica_cerca_rel_impresa_mobile_linea_attivita_old_anag(integer, integer, integer, integer, text, integer, character varying, character varying, date, date, text);

CREATE OR REPLACE FUNCTION anagrafica.anagrafica_cerca_rel_impresa_mobile_linea_attivita_old_anag(
    IN _id_macroarea integer DEFAULT NULL::integer,
    IN _id_aggregazione integer DEFAULT NULL::integer,
    IN _id_attivita integer DEFAULT NULL::integer,
    IN _id_impresa integer DEFAULT NULL::integer,
    IN _attivita_non_mappata text DEFAULT NULL::text,
    IN _id_stato integer DEFAULT NULL::integer,
    IN _numero_registrazione character varying DEFAULT NULL::character varying,
    IN _cun character varying DEFAULT NULL::character varying,
    IN _data_inserimento_1 date DEFAULT NULL::date,
    IN _data_inserimento_2 date DEFAULT NULL::date,
    IN _codice_univoco text DEFAULT NULL::text)
  RETURNS TABLE(id integer, id_macroarea integer, id_aggregazione integer, id_attivita integer, id_impresa integer, telefono character varying, fax character varying, numero_registrazione character varying, cun character varying, data_inserimento timestamp without time zone, id_stato integer, codice_univoco text) AS
$BODY$
DECLARE

BEGIN
FOR id, id_macroarea, id_aggregazione, id_attivita, id_impresa, 
       numero_registrazione, cun, data_inserimento, id_stato, codice_univoco
in 
    select opurel.id, opulinee.id_macroarea, opulinee.id_aggregazione, opulinee.id_attivita, os.id_operatore,
	   opurel.numero_registrazione, opurel.codice_nazionale, opurel.entered, opurel.stato, opurel.codice_univoco_ml
        
    from opu_relazione_stabilimento_linee_produttive opurel 
	 join opu_linee_attivita_nuove opulinee on opurel.id_linea_produttiva = opulinee.id_nuova_linea_attivita 
	 join opu_stabilimento os on os.id = opurel.id_stabilimento
      
    where 
    opurel.data_fine is null
    and (_id_macroarea is null or opulinee.id_macroarea = _id_macroarea)
    and (_id_aggregazione is null or opulinee.id_aggregazione = _id_aggregazione)
    and (_id_attivita is null or opulinee.id_attivita = _id_attivita)
    and (_id_impresa is null or os.id_operatore = _id_impresa)
    and (_id_stato is null or opurel.stato = _id_stato)
    and (_numero_registrazione is null or opurel.numero_registrazione = _numero_registrazione)
    and (_cun is null or opurel.codice_nazionale = _cun)
    and (_codice_univoco is null or opurel.codice_univoco_ml = _codice_univoco)
    and (_data_inserimento_1 is null or opurel.entered >= to_timestamp(to_char(_data_inserimento_1,'YYYY-MM-DD') || ' 00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US'))
    and (_data_inserimento_2 is null or opurel.entered <= to_timestamp(to_char(_data_inserimento_2,'YYYY-MM-DD') || ' 23:59:59.999999','YYYY-MM-DD HH24:MI:SS.US'))
    and os.trashed_date is null
    and opulinee.enabled
    and opurel.enabled
   
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION anagrafica.anagrafica_cerca_rel_impresa_mobile_linea_attivita_old_anag(integer, integer, integer, integer, text, integer, character varying, character varying, date, date, text)
  OWNER TO postgres;
