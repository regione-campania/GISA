--chi: Antonio Riviezzo
--cosa: dbi per la ricerca di un impresa nella vecchia anagrafica
--quando: 30/03/2018

-- Function: anagrafica.anagrafica_cerca_impresa_old_anagrafica(integer, character varying, character varying, character varying, integer, date, date, character varying)

-- DROP FUNCTION anagrafica.anagrafica_cerca_impresa_old_anagrafica(integer, character varying, character varying, character varying, integer, date, date, character varying);

CREATE OR REPLACE FUNCTION anagrafica.anagrafica_cerca_impresa_old_anagrafica(
    IN _id integer DEFAULT NULL::integer,
    IN _ragione_sociale character varying DEFAULT NULL::character varying,
    IN _codfisc character varying DEFAULT NULL::character varying,
    IN _piva character varying DEFAULT NULL::character varying,
    IN _id_tipo_impresa_societa integer DEFAULT NULL::integer,
    IN _data_inserimento_1 date DEFAULT NULL::date,
    IN _data_inserimento_2 date DEFAULT NULL::date,
    IN _pec character varying DEFAULT NULL::character varying)
  RETURNS TABLE(id integer, ragione_sociale character varying, codfisc character varying, piva character varying, id_tipo_impresa_societa integer, descrizione_tipo_impresa_societa text, data_inserimento timestamp without time zone, pec text, note text, toponimo_legale text, via_legale text, civico_legale text, cap_legale text, comune_legale text, provincia_legale text, latitudine double precision, longitudine double precision, nazione text, id_nazione integer, id_provincia integer, id_toponimo integer, id_comune integer, id_regione integer) AS
$BODY$
DECLARE


BEGIN

FOR id, ragione_sociale, codfisc, piva, id_tipo_impresa_societa, descrizione_tipo_impresa_societa, data_inserimento, pec, 
	note, toponimo_legale, via_legale, civico_legale, cap_legale, comune_legale, provincia_legale, latitudine, longitudine,
	nazione, id_nazione, id_provincia, id_toponimo, id_comune, id_regione
in 
       select 

	 distinct(CASE
	    WHEN o.org_id is not null THEN o.org_id
	    WHEN oo.id is not null THEN  oo.id
		  ELSE null
           END), 
           ram.ragione_sociale, 
           ram.codice_fiscale, 
           ram.partita_iva, 
	   oo.tipo_impresa,
           loti.description,
	   oo.entered,

           CASE
	    WHEN o.domicilio_digitale is not null THEN o.domicilio_digitale
	    WHEN oo.domicilio_digitale is not null THEN oo.domicilio_digitale
		  ELSE null
           END,

           CASE
	    WHEN o.notes is not null THEN o.notes
	    WHEN oo.note is not null THEN oo.note
		  ELSE null
           END,  

           CASE
	    WHEN address_impresa.topon is not null THEN coalesce(address_impresa.topon,'')
	    WHEN lt.description is not null THEN coalesce(lt.description,'')
		  ELSE null
           END, 

           CASE
	    WHEN address_impresa.addrline1 is not null THEN coalesce(address_impresa.addrline1,'')
	    WHEN oi.via is not null THEN coalesce(oi.via,'')
		  ELSE null
           END,

           CASE
	    WHEN address_impresa.civico is not null THEN coalesce(address_impresa.civico,'')
	    WHEN oi.civico is not null THEN coalesce(oi.civico,'')
		  ELSE null
           END, 

           CASE
	    WHEN address_impresa.postalcode is not null THEN coalesce(address_impresa.postalcode,'')
	    WHEN oi.cap is not null THEN coalesce(oi.cap,'')
		  ELSE null
           END,

	    CASE
	    WHEN address_impresa.city is not null THEN coalesce(address_impresa.city,'')
	    WHEN c1.nome is not null THEN coalesce(c1.nome,'')
		  ELSE null
           END,

           CASE
	    WHEN lpo.description is not null THEN coalesce(lpo.description,'')
	    WHEN lp.description is not null THEN coalesce(lp.description,'') 
		  ELSE null
           END, 

           ram.latitudine_leg,
           ram.longitudine_leg,

           CASE
	    WHEN address_impresa.country is not null THEN address_impresa.country
	    WHEN oi.nazione is not null THEN oi.nazione 
		  ELSE null
           END,

           CASE
	    WHEN comuneOrganization.cod_nazione is not null THEN comuneOrganization.cod_nazione
	    WHEN c1.cod_nazione is not null THEN c1.cod_nazione
		  ELSE null
           END,

           CASE
	    WHEN lpo.code is not null THEN lpo.code
	    WHEN lp.code is not null THEN lp.code 
		  ELSE null
           END,

	   CASE
	    WHEN address_impresa.toponimo is not null THEN address_impresa.toponimo
	    WHEN oi.toponimo is not null THEN oi.toponimo 
		  ELSE null
           END,

	   CASE
	    WHEN comuneOrganization.id is not null THEN comuneOrganization.id
	    WHEN oi.comune is not null THEN oi.comune 
		  ELSE null
           END,

           CASE
	    WHEN lpo.id_regione is not null THEN lpo.id_regione
	    WHEN lp.id_regione is not null THEN lp.id_regione
		  ELSE null
           END


    from ricerche_anagrafiche_old_materializzata ram
	
	      --join per organization
	      left join organization o on o.org_id = ram.riferimento_id and trim(ram.riferimento_id_nome_tab) = 'organization'
	      left join organization_address address_impresa ON address_impresa.address_id = ram.id_indirizzo_impresa and trim(ram.riferimento_id_nome_tab) = 'organization'
	      left join lookup_province lpo on lpo.cod_provincia ilike address_impresa.state
	      left join comuni1 comuneOrganization on trim(comuneOrganization.nome) ilike address_impresa.city 	 

	      --join per opu
	      left join opu_operatore oo on ram.id_impresa = oo.id and trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento'	
	      left join lookup_opu_tipo_impresa loti on oo.tipo_impresa = loti.code
	      left join opu_indirizzo oi on oo.id_indirizzo = oi.id
	      left join lookup_toponimi lt on oi.toponimo = lt.code
	      left join comuni1 c1 on oi.comune = c1.id
	      left join lookup_province lp on (oi.provincia:: integer) = lp.code
	 
    where 
	(_id is null or oo.id = _id) 
    and (_ragione_sociale is null or ram.ragione_sociale ilike _ragione_sociale || '%')
    and (_codfisc is null or trim(ram.codice_fiscale) ilike trim(_codfisc))
    and (_piva is null or trim(ram.partita_iva) ilike trim(_piva))
    and (_id_tipo_impresa_societa is null or oo.tipo_impresa = _id_tipo_impresa_societa)
    and ((_data_inserimento_1 is null or oo.entered >= to_timestamp( to_char(_data_inserimento_1,'YYYY-MM-DD') || ' 00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')) 
	  or o.entered >= to_timestamp( to_char(_data_inserimento_1,'YYYY-MM-DD') || ' 00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US'))
    and ((_data_inserimento_2 is null or oo.entered <= to_timestamp(to_char(_data_inserimento_2,'YYYY-MM-DD') || ' 23:59:59.999999','YYYY-MM-DD HH24:MI:SS.US'))
	  or o.entered <= to_timestamp( to_char(_data_inserimento_2,'YYYY-MM-DD') || ' 23:59:59.999999','YYYY-MM-DD HH24:MI:SS.US'))
    and ((_pec is null or oo.domicilio_digitale = _pec) or o.domicilio_digitale = _pec)
    and (oo.trashed_date is null or trim(ram.riferimento_id_nome_tab) = 'organization')
    and (o.enabled or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
    and (o.trashed_date is null or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
    and (address_impresa.trasheddate is null or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
    and (lpo.enabled or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
    and (loti.enabled or trim(ram.riferimento_id_nome_tab) = 'organization')
    and (lt.enabled or trim(ram.riferimento_id_nome_tab) = 'organization')
    and (lp.enabled or trim(ram.riferimento_id_nome_tab) = 'organization')
    
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
   
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION anagrafica.anagrafica_cerca_impresa_old_anagrafica(integer, character varying, character varying, character varying, integer, date, date, character varying)
  OWNER TO postgres;
