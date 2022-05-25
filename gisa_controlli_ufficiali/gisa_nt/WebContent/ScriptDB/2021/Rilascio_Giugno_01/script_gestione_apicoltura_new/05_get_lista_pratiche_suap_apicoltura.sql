-- Function: public.get_lista_pratiche_suap_apicoltura(integer)

-- DROP FUNCTION public.get_lista_pratiche_suap_apicoltura(integer);

CREATE OR REPLACE FUNCTION public.get_lista_pratiche_suap_apicoltura(IN _altid integer)
  RETURNS TABLE(id_pratica integer, numero_pratica text, id_tipologia_pratica integer, data_operazione timestamp without time zone, id_stabilimento integer, alt_id integer, numero_registrazione text, ragione_sociale text, partita_iva text, indirizzo text, id_utente integer, stato_pratica integer, comune_richiedente text, site_id_stab integer, id_comune_richiedente integer, data_inserimento_pratica timestamp without time zone) AS
$BODY$
DECLARE

BEGIN

return query
select distinct
       pgins.id as id_pratica,
       pgins.numero_pratica as numero_pratica,
       pgins.id_tipo_operazione::integer as id_tipologia_pratica,
       to_char(pgins.data_richiesta, 'dd/MM/yyyy')::timestamp without time zone as data_operazione,

       CASE WHEN eso.id_stabilimento is not null AND suapop.validato THEN 
		api_api.id 
	    ELSE 
		eso.id_stabilimento
        END as id_stabilimento,

       eso.alt_id as alt_id,
       
       ''::text as numero_registrazione,
       suapop.ragione_sociale::text as ragione_sociale,

       concat(
		case coalesce(trim(suapop.partita_iva),'') when '' then '-' else trim(suapop.partita_iva) end,
		' / ',
		case coalesce(trim(suapop.codice_fiscale_impresa),'') when '' then '-' else trim(suapop.codice_fiscale_impresa) end
	      )::text as partita_iva,

	upper(concat(trim(operlt.description), ' ', 
		     trim(operind.via), ' ', 
		     trim(operind.civico), ' ', 
		     trim(operind.cap), ' ', 
		     trim(operc.nome), ' ', 
		     trim(operlp.description))::text) as indirizzo,   
	   
       pgins.enteredby as id_utente,

       CASE WHEN eso.alt_id is null OR rep.trashed_date is not null THEN 0 
	    WHEN suapop.validato THEN 4
		ELSE 1 END as stato_pratica,
       cpra.nome::text as comune_richiedente,
       trim(cpra.codiceistatasl)::integer as site_id_stab,
       pgins.id_comune_richiedente as id_comune_richiedente,
       to_char(pgins.entered, 'dd/MM/yyyy')::timestamp without time zone as data_inserimento_pratica
 from 
 
	pratiche_gins pgins
		left join rel_eventi_pratiche rep on pgins.id = rep.id_pratica
		left join eventi_su_osa eso on rep.id_evento = eso.id

		left join suap_ric_scia_stabilimento suapstab on eso.alt_id = suapstab.alt_id
		left join suap_ric_scia_operatore suapop on suapstab.id_operatore = suapop.id
		left join apicoltura_imprese api_imp on api_imp.id_richiesta_suap = suapop.id
		left join apicoltura_apiari api_api on api_api.id_operatore = api_imp.id
		
		join comuni1 cpra on pgins.id_comune_richiedente = cpra.id

		left join opu_indirizzo operind on suapop.id_indirizzo = operind.id
		left join lookup_toponimi operlt on operlt.code = operind.toponimo and operlt.enabled
		left join comuni1 operc on operind.comune = operc.id
		left join lookup_province operlp on operlp.code = operc.cod_provincia::integer

	where pgins.trashed_date is null and( 
		(_altid > -1 and eso.alt_id = _altid) or (_altid = -1)) 
		and pgins.id_causale = 1
		and pgins.apicoltura 
		order by data_inserimento_pratica  desc, stato_pratica, pgins.id_tipo_operazione  desc  limit 100;
		


 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_lista_pratiche_suap_apicoltura(integer)
  OWNER TO postgres;
