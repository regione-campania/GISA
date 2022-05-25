DROP FUNCTION digemon.dbi_get_all_linee(timestamp without time zone,timestamp without time zone)
CREATE OR REPLACE FUNCTION digemon.dbi_get_all_linee(
    IN data_1 timestamp without time zone,
    IN data_2 timestamp without time zone)
  RETURNS TABLE(id_linea integer, riferimento_id integer, riferimento_id_nome_tab text, num_riconoscimento character varying, n_linea character varying, 
  data_inizio_attivita timestamp without time zone, data_fine_attivita timestamp without time zone, macroarea text, aggregazione text, attivita text, 
  path_attivita_completo text, norma text, id_norma integer, codice_macroarea text, codice_aggregazione text, codice_attivita text, stato text, id_stato integer, 
  miscela boolean, tipo_attivita_descrizione character varying, tipo_attivita integer, sintesis boolean) AS
$BODY$
DECLARE
	r RECORD; 	
BEGIN
     FOR 
	id_linea,    
	riferimento_id,
	riferimento_id_nome_tab,
	num_riconoscimento,
	n_linea,
	data_inizio_attivita ,
	data_fine_attivita ,
	macroarea ,
	aggregazione ,
	attivita ,
	path_attivita_completo ,
	norma ,
	id_norma ,
	codice_macroarea ,
	codice_aggregazione ,
	codice_attivita ,
	stato,
	id_stato,
	miscela,
	tipo_attivita_descrizione,
	tipo_attivita,
	sintesis 
  in
	select
	v.id_linea,    
	v.riferimento_id,
	v.riferimento_id_nome_tab,
	v.num_riconoscimento,
	v.n_linea ,
	v.data_inizio_attivita ,
	v.data_fine_attivita ,
	v.macroarea ,
	v.aggregazione ,
	-- nuova richiesta 18/12 per visualizzare il campo attivita splittato sempre 
	case when split_part(v.attivita, '->', 3) = '' or length(split_part(v.attivita, '->', 3)) =0 then v.attivita
	else split_part(v.attivita, '->', 3)
	end as attivita,
--	v.attivita ,
	v.path_attivita_completo ,
	v.norma ,
	v.id_norma ,
	v.codice_macroarea ,
	v.codice_aggregazione ,
	v.codice_attivita ,
	v.stato,
	v.id_stato,
	v.miscela,
	v.tipo_attivita_descrizione,
	v.tipo_attivita,
	(case when v.riferimento_id_nome_tab ilike '%sintesis%' then true else false end) 
	from ricerche_anagrafiche_old_materializzata v where v.data_inserimento between data_1 and data_2
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_all_linee(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;
  -- Function: digemon.dbi_get_stabilimenti_sintesis(timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION digemon.dbi_get_stabilimenti_sintesis(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION digemon.dbi_get_stabilimenti_sintesis(
    IN data_1 timestamp without time zone,
    IN data_2 timestamp without time zone)
  RETURNS TABLE(riferimento_id integer, tipo_impresa text, tipo_societa text, ragione_sociale text, partita_iva text, codice_fiscale_impresa text, 
  cf_rapp_sede_legale text, nome_rapp_sede_legale text, cognome_rapp_sede_legale text, indirizzo_sede_legale text, comune_sede_legale text, 
  cap_sede_legale text, prov_sede_legale text, comune_stab text, indirizzo_stab text, cap_stab text, prov_stab text, stab_descrizione_carattere text, 
  stab_descrizione_attivita text, stab_asl text, lat_stab text, long_stab text, approval_number text, norma text, codice_norma text, macroarea text, 
  codice_macroarea text, aggregazione text, codice_aggregazione text, attivita text, codice_attivita text, stato_linea text, 
  data_inizio_attivita timestamp without time zone, data_fine_attivita timestamp without time zone,
  categoria_rischio integer, data_prossimo_controllo timestamp without time zone, id_controllo_ultima_categorizzazione integer, data_controllo_ultima_categorizzazione timestamp without time zone,
  tipo_categorizzazione text, livello_rischio text
  ) AS
$BODY$
DECLARE
r RECORD;	
BEGIN

  RETURN QUERY 
	  select distinct s.alt_id as riferimento_id, s.tipo_impresa::text, s.tipo_societa::text, s.ragione_sociale::text, s.partita_iva::text, s.codice_fiscale_impresa::text, s.cf_rapp_sede_legale::text, 
	  s.nome_rapp_sede_legale, s.cognome_rapp_sede_legale, 
	  s.indirizzo_sede_legale, s.comune_sede_legale::text, s.cap_sede_legale::text, s.prov_sede_legale::text, s.comune_stab::text, s.indirizzo_stab, s.cap_stab::text, s.prov_stab, 
	  s.stab_descrizione_carattere::text, s.stab_descrizione_attivita::text, s.stab_asl::text, s.lat_stab::text, s.long_stab::text, s.approval_number, s.norma, 
	  s.codice_norma, s.macroarea, s.codice_macroarea, s.aggregazione, s.codice_aggregazione, s.attivita, s.codice_attivita, s.linea_stato_text::text,
	  s.data_inizio_attivita, s.data_fine_attivita, a.categoria_rischio, a.prossimo_controllo, a.id_controllo_ultima_categorizzazione, a.data_controllo_ultima_categorizzazione, a.tipo_categorizzazione,
          a.livello_rischio	 
	  from sintesis_operatori_denormalizzati_view s
	  left join digemon.dbi_get_all_stabilimenti_(data_1,data_2) a on a.riferimento_id = s.alt_id and a.riferimento_id_nome_tab='sintesis_stabilimento'
	  where stab.entered between  data_1 and data_2;
	 
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_stabilimenti_sintesis(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;
