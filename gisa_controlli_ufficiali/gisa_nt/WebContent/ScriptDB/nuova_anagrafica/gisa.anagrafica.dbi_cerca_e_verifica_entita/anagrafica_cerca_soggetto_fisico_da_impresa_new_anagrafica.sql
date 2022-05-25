--chi: Antonio Riviezzo
--cosa: dbi per la ricerca di un soggetto fisico a partire dai dati dell'impresa (codice_fiscale o partita iva)
--quando: 30/03/2018

-- Function: anagrafica.anagrafica_cerca_soggetto_fisico_da_impresa_new_anagrafica(character varying, character varying)

-- DROP FUNCTION anagrafica.anagrafica_cerca_soggetto_fisico_da_impresa_new_anagrafica(character varying, character varying);

CREATE OR REPLACE FUNCTION anagrafica.anagrafica_cerca_soggetto_fisico_da_impresa_new_anagrafica(
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
    --impresa
    "indirizzo.toponimo.description", "indirizzo.via", "indirizzo.civico", "indirizzo.cap", "indirizzo.comune.nome", 
    "indirizzo.provincia.description", "indirizzo.provincia.id_regione", "indirizzo.toponimo.code", "indirizzo.comune.id", 
    "indirizzo.latitudine", "indirizzo.longitudine", "indirizzo.nazione.description", "indirizzo.nazione.code",
    "indirizzo.provincia.code", 
    --soggetto fisico
    "soggettofisico.comune_nascita.nome", "soggettofisico.indirizzo.via", 
    "soggettofisico.indirizzo.civico", "soggettofisico.indirizzo.provincia.description", 
    "soggettofisico.indirizzo.comune.nome",
    --impresa 
    "tipo_impresa.code", "tipo_impresa.description"

    in
    select imp.ragione_sociale, imp.codfisc, imp.piva, imp.pec, imp.note, sf.cognome, sf.nome, sf.codice_fiscale,
	   lt.description, i.via, i.civico, i.cap, c.nome, lp.description, lp.id_regione, i.toponimo,
	   i.comune, i.latitudine, i.longitudine, ln.description, ln.code, lp.code,
	   sf.comune_nascita, ind_sog_fis.via, ind_sog_fis.civico, lp_sog_fis.description, c_sog_fis.nome,
	   imp.id_tipo_impresa_societa, ltis.description
	    
	from anagrafica.imprese imp
		left join anagrafica.rel_imprese_soggetti_fisici risf on imp.id = risf.id_impresa 
		left join anagrafica.soggetti_fisici sf on risf.id_soggetto_fisico = sf.id
		--join per impresa indirizzo
		left join anagrafica.rel_imprese_indirizzi rii on rii.id_impresa = imp.id
		left join anagrafica.indirizzi i on i.id = rii.id_indirizzo 
		left join anagrafica.lookup_toponimi lt on i.toponimo = lt.code
		left join anagrafica.comuni c on c.id = i.comune
		left join anagrafica.lookup_province lp on lp.code = c.id_provincia 
		left join anagrafica.lookup_nazioni ln on ln.code = lp.cod_nazione
		--join per soggetto fisico indirizzo
		left join anagrafica.rel_soggetti_fisici_indirizzi rsfi on sf.id = rsfi.id_soggetto_fisico
		left join anagrafica.indirizzi ind_sog_fis on ind_sog_fis.id = rsfi.id_indirizzo 
		left join anagrafica.lookup_toponimi lt_sog_fis on lt_sog_fis.code = ind_sog_fis.toponimo
		left join anagrafica.comuni c_sog_fis on c_sog_fis.id = ind_sog_fis.comune
		left join anagrafica.lookup_province lp_sog_fis on lp_sog_fis.code = c_sog_fis.id_provincia
		--join impresa tipo impresa
		left join anagrafica.lookup_tipo_impresa_soc ltis on ltis.code = imp.id_tipo_impresa_societa
		
	      where 
			(trim(imp.codfisc) ilike trim(_codice_fiscale_impresa) or trim(imp.piva) ilike trim(_p_iva))
			and imp.data_cancellazione is null
			and risf.data_cancellazione is null
			and sf.data_cancellazione is null
			and rii.data_cancellazione is null
			and lt.enabled
			and lp.enabled
			and ln.enabled
			and rsfi.data_cancellazione is null
			and lt_sog_fis.enabled
			and lp_sog_fis.enabled
			and ltis.enabled
		
       LOOP	 
	   RETURN NEXT;
	END LOOP;
	RETURN;
   
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION anagrafica.anagrafica_cerca_soggetto_fisico_da_impresa_new_anagrafica(character varying, character varying)
  OWNER TO postgres;
