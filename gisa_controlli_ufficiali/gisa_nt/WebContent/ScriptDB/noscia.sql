-- Function: public_functions.cerca_verifica_esistenza_stabilimento(text, text, integer, text, text, integer)

-- DROP FUNCTION public_functions.cerca_verifica_esistenza_stabilimento(text, text, integer, text, text, integer);

CREATE OR REPLACE FUNCTION public_functions.cerca_verifica_esistenza_stabilimento(
    _piva text DEFAULT NULL::text,
    _codfisc text DEFAULT NULL::text,
    _id_comune integer DEFAULT NULL::integer,
    _via text DEFAULT NULL::text,
    _civico text DEFAULT NULL::text,
    _toponimo integer DEFAULT NULL::integer)
  RETURNS integer AS
$BODY$
DECLARE
    idStabilimento integer;
        
BEGIN
    
    select 
        CASE
          WHEN o.org_id is not null THEN o.org_id
          WHEN os.id  is not null THEN os.id  
        ELSE null
           END

    into  idStabilimento
          
    from ricerche_anagrafiche_old_materializzata ram
        --join per organization
          left join organization o on o.org_id = ram.riferimento_id and trim(ram.riferimento_id_nome_tab) = 'organization'
          left join organization_address address_impresa ON address_impresa.address_id = ram.id_indirizzo_impresa and trim(ram.riferimento_id_nome_tab) = 'organization'
          left join comuni1 comuneOrganization on trim(comuneOrganization.nome) ilike address_impresa.city

          --join per opu
          left join opu_operatore oo on ram.id_impresa = oo.id and trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento'   
          left join opu_stabilimento os on oo.id = os.id_operatore
          left join opu_indirizzo oi on os.id_indirizzo = oi.id
          
    where 
        case when (_piva is not null and _piva<>'') then 
            lower(trim(ram.partita_iva))=lower(trim(_piva)) 
        else
            case when (_codfisc is not null and _codfisc<>'') then 
                lower(trim(ram.codice_fiscale))=lower(trim(_codfisc)) 
            end
        end 

        and (( _id_comune is null or oi.comune = _id_comune) or comuneOrganization.id = _id_comune )
        and (( _via is null or public.levenshtein(lower(coalesce(trim(oi.via),'')),lower(trim(_via)))<=2)
              or public.levenshtein(lower(coalesce(trim(address_impresa.addrline1),'')),lower(trim(_via)))<=2 )
        and (( _civico is null or lower(trim(oi.civico)) = lower(trim(_civico))) or address_impresa.civico = _civico )
        and ((_toponimo is null or oi.toponimo = _toponimo) or address_impresa.toponimo = _toponimo)
        
        and (os.trashed_date is null or trim(ram.riferimento_id_nome_tab) = 'organization')
        and (oo.trashed_date is null or trim(ram.riferimento_id_nome_tab) = 'organization')
        and (o.enabled or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
        and (o.trashed_date is null or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
        and (address_impresa.trasheddate is null or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
            limit 1;
            
        return idStabilimento ;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.cerca_verifica_esistenza_stabilimento(text, text, integer, text, text, integer)
  OWNER TO postgres;

  
  -- Function: public_functions.cerca_verifica_impresa(integer, character varying, character varying, character varying, integer, date, date, character varying)

-- DROP FUNCTION public_functions.cerca_verifica_impresa(integer, character varying, character varying, character varying, integer, date, date, character varying);

CREATE OR REPLACE FUNCTION public_functions.cerca_verifica_impresa(
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
    
          --join per lo schema vecchia anagrafica
          left join organization o on o.org_id = ram.riferimento_id and trim(ram.riferimento_id_nome_tab) = 'organization'
          left join organization_address address_impresa ON address_impresa.address_id = ram.id_indirizzo_impresa and trim(ram.riferimento_id_nome_tab) = 'organization'
          left join lookup_province lpo on lpo.cod_provincia ilike address_impresa.state
          left join comuni1 comuneOrganization on trim(comuneOrganization.nome) ilike address_impresa.city   

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
    and (_data_inserimento_1 is null or oo.entered >= to_timestamp( to_char(_data_inserimento_1,'YYYY-MM-DD') || ' 00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US'))
    and (_data_inserimento_2 is null or oo.entered <= to_timestamp(to_char(_data_inserimento_2,'YYYY-MM-DD') || ' 23:59:59.999999','YYYY-MM-DD HH24:MI:SS.US'))
    and ((_pec is null or oo.domicilio_digitale = _pec) or o.domicilio_digitale = _pec)
    and oo.trashed_date is null
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
   
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.cerca_verifica_impresa(integer, character varying, character varying, character varying, integer, date, date, character varying)
  OWNER TO postgres;

  -- Function: public_functions.cerca_verifica_soggetto(integer, character, text, text, text, character varying, timestamp without time zone, timestamp without time zone, text, text, timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION public_functions.cerca_verifica_soggetto(integer, character, text, text, text, character varying, timestamp without time zone, timestamp without time zone, text, text, timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION public_functions.cerca_verifica_soggetto(
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
ALTER FUNCTION public_functions.cerca_verifica_soggetto(integer, character, text, text, text, character varying, timestamp without time zone, timestamp without time zone, text, text, timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

  
  -- Function: public_functions.cerca_verifica_soggetto_fisico(character varying, character varying)

-- DROP FUNCTION public_functions.cerca_verifica_soggetto_fisico(character varying, character varying);

CREATE OR REPLACE FUNCTION public_functions.cerca_verifica_soggetto_fisico(
    IN _codice_fiscale_impresa character varying DEFAULT NULL::character varying,
    IN _p_iva character varying DEFAULT NULL::character varying)
  RETURNS TABLE(ragione_sociale character varying, codfisc character varying, piva character varying, pec text, note text, "soggettofisico.sesso" text, "soggettofisico.cognome" text, "soggettofisico.nome" text, "soggettofisico.codice_fiscale" character varying, "indirizzo.toponimo.description" text, "indirizzo.via" text, "indirizzo.civico" text, "indirizzo.cap" text, "indirizzo.comune.nome" text, "indirizzo.provincia.description" text, "indirizzo.provincia.id_regione" integer, "indirizzo.toponimo.code" integer, "indirizzo.comune.id" integer, "indirizzo.latitudine" double precision, "indirizzo.longitudine" double precision, "indirizzo.nazione.description" text, "indirizzo.nazione.code" integer, "indirizzo.provincia.code" integer, "soggettofisico.comune_nascita.id" integer, "soggettofisico.comune_nascita.nome" text, "soggettofisico.data_nascita" timestamp without time zone, "soggettofisico.indirizzo.toponimo.code" integer, "soggettofisico.indirizzo.toponimo.description" character varying, "soggettofisico.indirizzo.via" character varying, "soggettofisico.indirizzo.civico" text, "soggettofisico.indirizzo.cap" text, "soggettofisico.indirizzo.nazione.code" integer, "soggettofisico.indirizzo.provincia.description" text, "soggettofisico.indirizzo.comune.nome" character varying, tipo_impresa integer, "tipo_impresa.description" text, id_impresa integer) AS
$BODY$
DECLARE

BEGIN

IF ( _codice_fiscale_impresa != null and _codice_fiscale_impresa != '') or trim(_codice_fiscale_impresa) ilike '' THEN
    _codice_fiscale_impresa := null;
END IF;

IF ( _p_iva != null and _p_iva != '') or trim(_p_iva) ilike '' THEN
    _p_iva := null;
END IF;
FOR ragione_sociale, codfisc, piva , pec, note,"soggettofisico.sesso", "soggettofisico.cognome", "soggettofisico.nome", "soggettofisico.codice_fiscale", 
    --impresa
    "indirizzo.toponimo.description", "indirizzo.via", "indirizzo.civico", "indirizzo.cap", "indirizzo.comune.nome", 
    "indirizzo.provincia.description", "indirizzo.provincia.id_regione", "indirizzo.toponimo.code", "indirizzo.comune.id", 
    "indirizzo.latitudine", "indirizzo.longitudine", "indirizzo.nazione.description", "indirizzo.nazione.code",
    "indirizzo.provincia.code", 
    --soggetto fisico
    "soggettofisico.comune_nascita.id","soggettofisico.comune_nascita.nome", 
    "soggettofisico.data_nascita", 
    "soggettofisico.indirizzo.toponimo.code", "soggettofisico.indirizzo.toponimo.description",
    "soggettofisico.indirizzo.via", 
    "soggettofisico.indirizzo.civico", 
    "soggettofisico.indirizzo.cap","soggettofisico.indirizzo.nazione.code",
    "soggettofisico.indirizzo.provincia.description", 
    "soggettofisico.indirizzo.comune.nome",
    --impresa 
    "tipo_impresa", "tipo_impresa.description",id_impresa



    in
    select distinct(ram.ragione_sociale), ram.codice_fiscale, ram.partita_iva, 
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
        WHEN opusf.sesso is not null THEN coalesce(opusf.sesso,'')
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
           --comune nascita id in caso di vecchia anagrafica
           -1,
           CASE
        WHEN o.luogo_nascita_rappresentante is not null THEN coalesce(o.luogo_nascita_rappresentante,'')
        WHEN opusf.comune_nascita is not null THEN coalesce(opusf.comune_nascita,'')
          ELSE null
           END,
           coalesce(opusf.data_nascita,null),  
           coalesce(opuindsf.toponimo,null),
           coalesce(opuindsf.topon,null),   
           coalesce(opuindsf.via,''),
           coalesce(opuindsf.civico,''),
           coalesce(opuindsf.cap,''),
           -1,--id_nazione vecchia anagrafica
           coalesce(lpindsf.description,''),
           coalesce(comuniOpusf.nome,''),
       coalesce(opuope.tipo_impresa,0),
       lti.description,
       -1::integer id_impresa   
    
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
        
       LOOP  
       RETURN NEXT;
    END LOOP;
    RETURN;
   
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.cerca_verifica_soggetto_fisico(character varying, character varying)
  OWNER TO postgres;

  
-- Function: public.get_comuni(integer, character varying, character varying, integer, integer)

-- DROP FUNCTION public.get_comuni(integer, character varying, character varying, integer, integer);

CREATE OR REPLACE FUNCTION public.get_comuni(
    IN _id integer DEFAULT NULL::integer,
    IN _nome character varying DEFAULT NULL::character varying,
    IN _cod_regione character varying DEFAULT NULL::character varying,
    IN _id_provincia integer DEFAULT NULL::integer,
    IN _id_asl integer DEFAULT NULL::integer)
  RETURNS TABLE(id integer, cod_comune character varying, cod_regione character varying, cod_provincia integer, nome character varying, istat character varying, cap character varying, cod_nazione integer, id_asl integer) AS
$BODY$
 SELECT id,
    cod_comune, 
    cod_regione, 
    cod_provincia::integer,
    nome,
    istat,
    cap,
    cod_nazione,
    codiceistatasl::integer as _id_asl
from public.comuni1 c
where 
        (_id is null or c.id = _id) 
    and (_nome is null or c.nome ilike _nome || '%')
    and (_cod_regione is null or c.cod_regione ilike _cod_regione || '%')
    and (_id_provincia::integer is null or c.cod_provincia::integer = _id_provincia)
    and (_id_asl is null or c.codiceistatasl::integer = _id_asl)

order by nome $BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_comuni(integer, character varying, character varying, integer, integer)
  OWNER TO postgres;

  
-- Function: public.get_linea_attivita_noscia()

-- DROP FUNCTION public.get_linea_attivita_noscia();

CREATE OR REPLACE FUNCTION public.get_linea_attivita_noscia()
  RETURNS TABLE(codice_attivita text, path_descrizione text, description text) AS
$BODY$
select codice_attivita, concat_ws(' -> ', path_descrizione, description) path_descrizione , description
from ml8_linee_attivita_nuove_materializzata ml8 
join master_list_no_scia_abilitate  mls on mls.codice_univoco_ml=ml8.codice_attivita
join opu_lookup_norme_master_list ln on code = ml8.id_norma
where mls.enabled
 $BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_linea_attivita_noscia()
  OWNER TO postgres;