--chi: Antonio Riviezzo
--cosa: dbi per la ricerca di un soggetto fisico a partire dai dati dell'impresa (codice_fiscale e partita_iva) sulla vecchia anagrafica
--quando: 30/03/2018

-- Function: anagrafica.anagrafica_cerca_soggetto_fisico_da_impresa_old_anagrafica(character varying, character varying)

-- DROP FUNCTION anagrafica.anagrafica_cerca_soggetto_fisico_da_impresa_old_anagrafica(character varying, character varying);

CREATE OR REPLACE FUNCTION anagrafica.anagrafica_cerca_soggetto_fisico_da_impresa_old_anagrafica(
    IN _codice_fiscale_impresa character varying DEFAULT NULL::character varying,
    IN _p_iva character varying DEFAULT NULL::character varying)
  RETURNS TABLE(ragione_sociale character varying, codfisc character varying, piva character varying, pec text, note text, "soggettofisico.cognome" text, "soggettofisico.nome" text, "soggettofisico.codice_fiscale" character varying, "indirizzo.toponimo.description" text, "indirizzo.via" text, "indirizzo.civico" text, "indirizzo.cap" text, "indirizzo.comune.nome" text, "indirizzo.provincia.description" text, "indirizzo.provincia.id_regione" integer, "indirizzo.toponimo.code" integer, "indirizzo.comune.id" integer, "indirizzo.latitudine" double precision, "indirizzo.longitudine" double precision, "indirizzo.nazione.description" text, "indirizzo.nazione.code" integer, "indirizzo.provincia.code" integer, "soggettofisico.comune_nascita.nome" text, "soggettofisico.indirizzo.via" character varying, "soggettofisico.indirizzo.civico" text, "soggettofisico.indirizzo.provincia.description" text, "soggettofisico.indirizzo.comune.nome" character varying, "tipo_impresa.code" integer, "tipo_impresa.description" text) AS
$BODY$
DECLARE

BEGIN

IF trim(_codice_fiscale_impresa) ilike '' THEN
	_codice_fiscale_impresa := null;
END IF;

IF trim(_p_iva) ilike '' THEN
	_p_iva := null;
END IF;

FOR ragione_sociale, codfisc, piva , pec, note, "soggettofisico.cognome", "soggettofisico.nome", "soggettofisico.codice_fiscale", 
    "indirizzo.toponimo.description", "indirizzo.via", "indirizzo.civico", "indirizzo.cap", "indirizzo.comune.nome", 
    "indirizzo.provincia.description", "indirizzo.provincia.id_regione", "indirizzo.toponimo.code", "indirizzo.comune.id", 
    "indirizzo.latitudine", "indirizzo.longitudine", "indirizzo.nazione.description", "indirizzo.nazione.code",
    "indirizzo.provincia.code", "soggettofisico.comune_nascita.nome", "soggettofisico.indirizzo.via", 
    "soggettofisico.indirizzo.civico", "soggettofisico.indirizzo.provincia.description", 
    "soggettofisico.indirizzo.comune.nome", "tipo_impresa.code", "tipo_impresa.description"

    in
    select distinct(ram.ragione_sociale), ram.codice_fiscale, ram.partita_iva , 
	  CASE
	    WHEN o.domicilio_digitale is not null THEN o.domicilio_digitale
	    WHEN opuope.domicilio_digitale is not null THEN opuope.domicilio_digitale
		  ELSE null
           END, 
	   CASE
	    WHEN o.notes is not null THEN o.notes
	    WHEN opuope.note is not null THEN opuope.note
		  ELSE null
           END,  
	   CASE
	    WHEN o.cognome_rappresentante is not null THEN coalesce(o.cognome_rappresentante,'')
	    WHEN opusf.cognome is not null THEN coalesce(opusf.cognome,'')
		  ELSE null
           END,
           CASE
	    WHEN o.nome_rappresentante is not null THEN coalesce(o.nome_rappresentante,'')
	    WHEN opusf.nome is not null THEN coalesce(opusf.nome,'')
		  ELSE null
           END,
           CASE
	    WHEN o.codice_fiscale_rappresentante is not null THEN coalesce(o.codice_fiscale_rappresentante,'')
	    WHEN opusf.codice_fiscale is not null THEN coalesce(opusf.codice_fiscale,'')
		  ELSE null
           END, 
         CASE
	    WHEN address_impresa.topon is not null THEN coalesce(address_impresa.topon,'')
	    WHEN lt.description is not null THEN coalesce(lt.description,'')
		  ELSE null
           END,
           CASE
	    WHEN address_impresa.addrline1 is not null THEN coalesce(address_impresa.addrline1,'')
	    WHEN opuind.via is not null THEN coalesce(opuind.via,'')
		  ELSE null
           END, 
           CASE
	    WHEN address_impresa.civico is not null THEN coalesce(address_impresa.civico,'')
	    WHEN opuind.civico is not null THEN coalesce(opuind.civico,'')
		  ELSE null
           END,    
           CASE
	    WHEN address_impresa.postalcode is not null THEN coalesce(address_impresa.postalcode,'')
	    WHEN opuind.cap is not null THEN coalesce(opuind.cap,'')
		  ELSE null
           END,
           CASE
	    WHEN address_impresa.city is not null THEN coalesce(address_impresa.city,'')
	    WHEN comuniOpu.nome is not null THEN coalesce(comuniOpu.nome,'')
		  ELSE null
           END,  
           CASE
	    WHEN lp.description is not null THEN coalesce(lp.description,'')
	    WHEN lpOpu.description is not null THEN coalesce(lpOpu.description,'') 
		  ELSE null
           END,
           CASE
	    WHEN lp.id_regione is not null THEN lp.id_regione
	    WHEN lpOpu.id_regione is not null THEN lpOpu.id_regione 
		  ELSE null
           END,
           CASE
	    WHEN address_impresa.toponimo is not null THEN address_impresa.toponimo
	    WHEN opuind.toponimo is not null THEN opuind.toponimo 
		  ELSE null
           END,
           CASE
	    WHEN comuneOrganization.id is not null THEN comuneOrganization.id
	    WHEN opuind.comune is not null THEN opuind.comune 
		  ELSE null
           END,
           CASE
	    WHEN ram.latitudine_leg is not null THEN ram.latitudine_leg
	    WHEN opuind.latitudine is not null THEN opuind.latitudine 
		  ELSE null
           END,
           CASE
	    WHEN ram.longitudine_leg is not null THEN ram.longitudine_leg
	    WHEN opuind.longitudine is not null THEN opuind.longitudine 
		  ELSE null
           END,
           CASE
	    WHEN address_impresa.country is not null THEN address_impresa.country
	    WHEN opuind.nazione is not null THEN opuind.nazione 
		  ELSE null
           END,
           CASE
	    WHEN comuneOrganization.cod_nazione is not null THEN comuneOrganization.cod_nazione
	    WHEN comuniOpu.cod_nazione is not null THEN comuniOpu.cod_nazione 
		  ELSE null
           END,
           CASE
	    WHEN lp.code is not null THEN lp.code
	    WHEN lpOpu.code is not null THEN lpOpu.code 
		  ELSE null
           END,
           CASE
	    WHEN o.luogo_nascita_rappresentante is not null THEN coalesce(o.luogo_nascita_rappresentante,'')
	    WHEN opusf.comune_nascita is not null THEN coalesce(opusf.comune_nascita,'')
		  ELSE null
           END,
           coalesce(opuindsf.via,''),
           coalesce(opuindsf.civico,''), 
           coalesce(lpindsf.description,''),
           coalesce(comuniOpusf.nome,''),
	   opuope.tipo_impresa,
	   lti.description
	   
    
	from (select ra.* from ricerche_anagrafiche_old_materializzata ra 
		where trim(ra.codice_fiscale) ilike trim(_codice_fiscale_impresa) 
			or trim(ra.partita_iva) ilike trim(_p_iva)) ram 
	      --join per lo schema vecchia anagrafica
	      left join organization o on o.org_id = ram.riferimento_id and trim(ram.riferimento_id_nome_tab) = 'organization'
	      left join organization_address address_impresa ON address_impresa.address_id = ram.id_indirizzo_impresa and trim(ram.riferimento_id_nome_tab) = 'organization'
	      left join lookup_province lp on lp.cod_provincia ilike address_impresa.state
	      left join comuni1 comuneOrganization on trim(comuneOrganization.nome) ilike address_impresa.city 

	      --join per lo schema opu
	      left join opu_operatore opuope on ram.id_impresa = opuope.id and trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento'
	      left join opu_rel_operatore_soggetto_fisico opurelsofis on opuope.id = opurelsofis.id_operatore
	      left join opu_soggetto_fisico opusf on opurelsofis.id_soggetto_fisico = opusf.id 
	      left join opu_indirizzo opuind on opuope.id_indirizzo = opuind.id 
	      left join lookup_toponimi lt on lt.code = opuind.toponimo 
	      left join comuni1 comuniOpu on comuniOpu.id = opuind.comune 
	      left join lookup_province lpOpu on lpOpu.code = trim(comuniOpu.cod_provincia)::integer 
	      left join opu_indirizzo opuindsf on opuindsf.id = opusf.indirizzo_id 
	      left join comuni1 comuniOpusf on comuniOpusf.id = opuindsf.comune
	      left join lookup_province lpindsf on lpindsf.code = trim(comuniOpusf.cod_provincia)::integer 
	      left join lookup_opu_tipo_impresa lti on lti.code = opuope.tipo_impresa 

	      where (address_impresa.trasheddate is null or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
		    and (lp.enabled or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
		    and (o.enabled or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
		    and (o.trashed_date is null or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
		    and (opurelsofis.enabled or trim(ram.riferimento_id_nome_tab) = 'organization')
		    and (opusf.trashed_date is null or trim(ram.riferimento_id_nome_tab) = 'organization')
		    and (opuope.trashed_date is null or trim(ram.riferimento_id_nome_tab) = 'organization')
		    --and (lt.enabled or trim(ram.riferimento_id_nome_tab) = 'organization')
		    --and (lpOpu.enabled or trim(ram.riferimento_id_nome_tab) = 'organization')
		    --and (lpindsf.enabled or trim(ram.riferimento_id_nome_tab) = 'organization')
		    --and (lti.enabled or trim(ram.riferimento_id_nome_tab) = 'organization')
		
       LOOP	 
	   RETURN NEXT;
	END LOOP;
	RETURN;
   
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION anagrafica.anagrafica_cerca_soggetto_fisico_da_impresa_old_anagrafica(character varying, character varying)
  OWNER TO postgres;
