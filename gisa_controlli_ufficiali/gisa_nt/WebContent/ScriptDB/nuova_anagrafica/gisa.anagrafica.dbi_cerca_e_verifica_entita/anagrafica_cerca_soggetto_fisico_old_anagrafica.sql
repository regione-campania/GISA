--chi: Antonio Riviezzo
--cosa: dbi per la ricerca di un soggetto fisico nella vecchia anagrafica
--quando: 30/03/2018

-- Function: anagrafica.anagrafica_cerca_soggetto_fisico_old_anagrafica(integer, character, text, text, text, character varying, timestamp without time zone, timestamp without time zone, text, text, timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION anagrafica.anagrafica_cerca_soggetto_fisico_old_anagrafica(integer, character, text, text, text, character varying, timestamp without time zone, timestamp without time zone, text, text, timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION anagrafica.anagrafica_cerca_soggetto_fisico_old_anagrafica(
    IN _id integer DEFAULT NULL::integer,
    IN _titolo character DEFAULT NULL::character(1),
    IN _cognome text DEFAULT NULL::text,
    IN _nome text DEFAULT NULL::text,
    IN _comune_nascita text DEFAULT NULL::text,
    IN _codice_fiscale character varying DEFAULT NULL::character varying,
    IN _data_nascita_1 timestamp without time zone DEFAULT NULL::timestamp without time zone,
    IN _data_nascita_2 timestamp without time zone DEFAULT NULL::timestamp without time zone,
    IN _documento_identita text DEFAULT NULL::text,
    IN _provincia_nascita text DEFAULT NULL::text,
    IN _data_inserimento_1 timestamp without time zone DEFAULT NULL::timestamp without time zone,
    IN _data_inserimento_2 timestamp without time zone DEFAULT NULL::timestamp without time zone)
  RETURNS TABLE(id integer, titolo character, cognome text, nome text, comune_nascita text, codice_fiscale character varying, enteredby integer, modifiedby integer, ipenteredby character, ipmodifiedby character, sesso character, telefono character, fax character, email character varying, telefono1 character, data_nascita timestamp without time zone, documento_identita text, provenienza_estera boolean, provincia_nascita text, data_inserimento timestamp without time zone, id_comune_nascita integer, comune_nascita_nome text, id_nazionalita_nascita integer, nazionalita_nascita text, id_provinica_nascita integer, provincia_nascita_nome text, id_toponimo_residenza integer, toponimo_residenza text, via_residenza text, civico_residenza text, cap_residenza text, id_comune_residenza integer, comune_residenza_nome text, id_provincia_residenza integer, provincia_residenza text, id_nazione_residenza integer, nazione_residenza text) AS
$BODY$
DECLARE


BEGIN


FOR id, titolo, cognome, nome, comune_nascita, codice_fiscale, enteredby, 
	modifiedby, ipenteredby, ipmodifiedby, sesso, telefono, fax, 
	email, telefono1, data_nascita, documento_identita, provenienza_estera, provincia_nascita,  
	data_inserimento, id_comune_nascita,comune_nascita_nome, id_nazionalita_nascita, 
	nazionalita_nascita, id_provinica_nascita, provincia_nascita_nome, 
	id_toponimo_residenza, toponimo_residenza, via_residenza,
	civico_residenza, cap_residenza, id_comune_residenza, comune_residenza_nome,
	id_provincia_residenza, provincia_residenza, id_nazione_residenza, nazione_residenza
in 

    select distinct(osf.id), 

	   CASE
	    WHEN o.titolo_rappresentante is not null THEN o.titolo_rappresentante::character   
	    WHEN osf.titolo is not null THEN osf.titolo 
		  ELSE null
           END,

           CASE
	    WHEN o.cognome_rappresentante is not null THEN o.cognome_rappresentante   
	    WHEN osf.cognome is not null THEN osf.cognome
		  ELSE null
           END,

           CASE
	    WHEN o.nome_rappresentante is not null THEN o.nome_rappresentante   
	    WHEN osf.nome is not null THEN osf.nome
		  ELSE null
           END,

           CASE
	    WHEN o.luogo_nascita_rappresentante is not null THEN o.luogo_nascita_rappresentante   
	    WHEN osf.comune_nascita is not null THEN osf.comune_nascita
		  ELSE null
           END,

           ram.codice_fiscale_rappresentante,
           osf.enteredby, 
	   osf.modifiedby, 
	   osf.ipenteredby, 
	   osf.ipmodifiedby, 
	   osf.sesso, 

	   CASE
	    WHEN o.telefono_rappresentante is not null THEN o.telefono_rappresentante   
	    WHEN osf.telefono is not null THEN osf.telefono
		  ELSE null
           END,

           CASE
	    WHEN o.fax is not null THEN o.fax   
	    WHEN osf.fax is not null THEN osf.fax
		  ELSE null
           END,

	   CASE
	    WHEN o.email_rappresentante is not null THEN o.email_rappresentante
	    WHEN osf.email is not null THEN osf.email
		  ELSE null
           END,
	   
	   osf.telefono1, 

	   CASE
	    WHEN o.data_nascita_rappresentante is not null THEN o.data_nascita_rappresentante
	    WHEN osf.data_nascita is not null THEN osf.data_nascita
		  ELSE null
           END,

	   CASE
	    WHEN o.documento_identita is not null THEN o.documento_identita
	    WHEN osf.documento_identita is not null THEN osf.documento_identita
		  ELSE null
           END,
	   
	   osf.provenienza_estera,
	   osf.provincia_nascita, 
	   null, 
	   cnascita.id, 

	   CASE
	    WHEN o.luogo_nascita_rappresentante is not null THEN o.luogo_nascita_rappresentante
	    WHEN osf.comune_nascita is not null THEN osf.comune_nascita
		  ELSE null
           END,
           
	   cnascita.cod_nazione, 
	   lnNascita.description,
	   (cnascita.cod_provincia::integer), 
	   osf.provincia_nascita, 
	   lt.code, 
	   lt.description,

	   CASE
	    WHEN o.address_legale_rapp is not null THEN o.address_legale_rapp
	    WHEN oi.via is not null THEN oi.via
		  ELSE null
           END,

	   oi.civico,
	   oi.cap, 
	   oi.comune, 

	   CASE
	    WHEN o.city_legale_rapp is not null THEN o.city_legale_rapp
	    WHEN c1.nome is not null THEN c1.nome
		  ELSE null
           END,
	   
	   (c1.cod_provincia::integer), 

	   CASE
	    WHEN o.prov_legale_rapp is not null THEN o.prov_legale_rapp
	    WHEN lp.description is not null THEN lp.description
		  ELSE null
           END,
	   
	   c1.cod_nazione,
	   lnResidenza.description   


        from ricerche_anagrafiche_old_materializzata ram
	
	      --join per organization
	      left join organization o on o.org_id = ram.riferimento_id and trim(ram.riferimento_id_nome_tab) = 'organization' 
	      
	      --join per opu
	      left join opu_soggetto_fisico osf on osf.id = ram.id_soggetto_fisico and trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento'
	      left join opu_indirizzo oi on osf.indirizzo_id = oi.id
	      left join lookup_toponimi lt on oi.toponimo = lt.code
	      left join comuni1 c1 on oi.comune = c1.id
	      left join lookup_nazioni lnResidenza on c1.cod_nazione = lnResidenza.code
	      left join comuni1 cnascita on osf.comune_nascita ilike cnascita.nome
	      left join lookup_nazioni lnNascita on cnascita.cod_nazione = lnNascita.code
	      left join lookup_province lp on (c1.cod_provincia::integer) = lp.code

    where 
    (_id is null or osf.id = _id) 
    and ((_titolo is null or osf.titolo ilike _titolo || '%') or o.titolo_rappresentante::character ilike _titolo || '%')
    and ((_cognome is null or osf.cognome ilike _cognome || '%') or o.cognome_rappresentante ilike _cognome || '%')
    and ((_nome is null or osf.nome ilike _nome || '%') or o.nome_rappresentante ilike _nome || '%')
    and (_codice_fiscale is null or  trim(lower(ram.codice_fiscale_rappresentante))=trim(lower(_codice_fiscale))) 
    and ((_data_nascita_1 is null or osf.data_nascita >= to_timestamp(to_char(_data_nascita_1,'YYYY-MM-DD') || ' 00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US'))
	or o.data_nascita_rappresentante >= to_timestamp(to_char(_data_nascita_1,'YYYY-MM-DD') || ' 00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US'))
    and ((_data_nascita_2 is null or osf.data_nascita <= to_timestamp(to_char(_data_nascita_2,'YYYY-MM-DD') || ' 23:59:59.999999','YYYY-MM-DD HH24:MI:SS.US'))
	or o.data_nascita_rappresentante <= to_timestamp(to_char(_data_nascita_2,'YYYY-MM-DD') || ' 23:59:59.999999','YYYY-MM-DD HH24:MI:SS.US'))
    and ((_documento_identita is null or osf.documento_identita = _documento_identita) or o.documento_identita = _documento_identita)
    and (_provincia_nascita is null or osf.provincia_nascita = _provincia_nascita)

    and (osf.trashed_date is null or trim(ram.riferimento_id_nome_tab) = 'organization')
    and (o.enabled or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
    and (o.trashed_date is null or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
    and (lt.enabled or trim(ram.riferimento_id_nome_tab) = 'organization')
    and (lp.enabled or trim(ram.riferimento_id_nome_tab) = 'organization')
    and (lnResidenza.enabled or trim(ram.riferimento_id_nome_tab) = 'organization')
    and (lnNascita.enabled or trim(ram.riferimento_id_nome_tab) = 'organization')

    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION anagrafica.anagrafica_cerca_soggetto_fisico_old_anagrafica(integer, character, text, text, text, character varying, timestamp without time zone, timestamp without time zone, text, text, timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;
