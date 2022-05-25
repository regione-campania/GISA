-- Function: digemon.dbi_get_all_stabilimenti(timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION digemon.dbi_get_all_stabilimenti(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION digemon.dbi_get_all_stabilimenti(
    IN data_1 timestamp without time zone,
    IN data_2 timestamp without time zone)
  RETURNS TABLE(riferimento_id integer, riferimento_id_nome_tab text, ragione_sociale text, asl_rif integer, asl text, codice_fiscale text, codice_fiscale_rappresentante text, partita_iva text, n_reg text, 
  nominativo_rappresentante text, comune text, provincia_stab text, indirizzo text, cap_stab text, comune_leg text, provincia_leg text, indirizzo_leg text, cap_leg text, 
  latitudine_leg double precision, longitudine_leg double precision, latitudine_stab double precision, longitudine_stab double precision, categoria_rischio integer, prossimo_controllo timestamp without time zone, 
  id_controllo_ultima_categorizzazione integer, data_controllo_ultima_categorizzazione timestamp without time zone, tipo_categorizzazione text, data_inserimento timestamp without time zone, livello_rischio text) AS
$BODY$

with cte_ext as (

	with cte_int as	(
		 select t.assigned_date, t.ticketid as id_controllo,
			t.categoria_rischio, (select description from lookup_categoria_rischio where code = t.livello_rischio) as livello_rischio, t.data_prossimo_controllo,
			case 	when t.alt_id > 0 then t.alt_id
				when t.id_stabilimento > 0 then t.id_stabilimento
				when t.id_apiario > 0 then t.id_apiario
				when t.org_id > 0 then t.org_id end as riferimento_id ,
		      case 	when t.alt_id > 0 then (select return_nome_tabella from gestione_id_alternativo(t.alt_id,-1))
				when t.id_stabilimento > 0 then 'opu_stabilimento'
				when t.id_apiario > 0 then 'apicoltura_imprese'
				when t.org_id > 0 then 'organization' end as
			riferimento_nome_tab
		 from ticket t
		 where t.tipologia=3 and trashed_date is null and t.provvedimenti_prescrittivi=5 and t.closed is not null
		 group by ticketid , riferimento_id, riferimento_nome_tab
		 order by riferimento_id, riferimento_nome_tab, assigned_date desc
	)
	      select distinct on (riferimento_id, riferimento_nome_tab)
	        id_controllo,
	        riferimento_id,
	        riferimento_nome_tab,
	        data_prossimo_controllo,
	        assigned_date,
		categoria_rischio,
		livello_rischio,
		id_controllo as id_controllo_ultima_categorizzazione,
		assigned_date as data_controllo_ultima_categorizzazione,
		case when id_controllo is null then 'EX ANTE' ELSE 'CATEGORIZZATO DA CU' end as tipo_categorizzazione
		from cte_int
	) 
    select distinct -- aggiunta della distinct 
	v.riferimento_id ,
	v.riferimento_id_nome_tab ,
	v.ragione_sociale,
	v.asl_rif ,
	v.asl ,
	v.codice_fiscale::text,
	v.codice_fiscale_rappresentante ,
	v.partita_iva::text,
	v.n_reg,
	v.nominativo_rappresentante ,
	v.comune,
	v.provincia_stab,
	v.indirizzo,
	v.cap_stab,
	v.comune_leg,
	v.provincia_leg ,
	v.indirizzo_leg,
	v.cap_leg,
	v.latitudine_leg,
	v.longitudine_leg,
	v.latitudine_stab,
	v.longitudine_stab,
	case when l.id_controllo is null then coalesce(l.categoria_rischio,v.categoria_rischio) else l.categoria_rischio end as categoria_rischio, -- integrazione per gestione categoria di rischio EX ANTE
	l.data_prossimo_controllo as prossimo_controllo,
	l.id_controllo as id_controllo_ultima_categorizzazione,
	l.assigned_date as data_controllo_ultima_categorizzazione,
        case when l.id_controllo is null then 'EX ANTE' ELSE 'CATEGORIZZATO DA CU' end as tipo_categorizzazione,
        v.data_inserimento,
       l.livello_rischio

    from ricerche_anagrafiche_old_materializzata v 
    left join cte_ext l on l.riferimento_id  = v.riferimento_id and l.riferimento_nome_tab = v.riferimento_id_nome_tab
    where v.data_inserimento between data_1 and data_2 
 
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_all_stabilimenti(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;
