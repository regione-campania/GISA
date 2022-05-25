-- Function: public.recupero_pratiche_suap(integer, integer)

-- DROP FUNCTION public.recupero_pratiche_suap(integer, integer);

CREATE OR REPLACE FUNCTION public.recupero_pratiche_suap(
    _cod_comune integer,
    _id_stab integer)
  RETURNS SETOF json AS
$BODY$

BEGIN

 
return query
select concat('[', string_agg(row_to_json(tab)::text, ','), ']')::json as pratica from
(
select numero_pratica, tipo_pratica, comune_pratica, id_comune_pratica, data_pratica from
(
(select distinct 
	pg.numero_pratica as numero_pratica,
	ltr_pg.long_description as tipo_pratica,
	cpra.nome::text as comune_pratica,
	cpra.id::text as id_comune_pratica,
	to_char(pg.data_richiesta, 'DD/MM/YYYY')::text as data_pratica

	from pratiche_gins pg 
		left join rel_eventi_pratiche rep on pg.id = rep.id_pratica
		left join eventi_su_osa eo on rep.id_evento = eo.id 
		join suap_lookup_tipo_richiesta ltr_pg on ltr_pg.code = pg.id_tipo_operazione
		join comuni1 cpra on pg.id_comune_richiedente = cpra.id
		
		where pg.id_comune_richiedente = _cod_comune 
		      and (eo.id_stabilimento <> _id_stab or eo.id_stabilimento is null)
		      and pg.trashed_date is null 
		      and pg.id_causale = 1 
		      and pg.apicoltura is not true
		      and pg.data_richiesta > (now() - interval '30 days') )
except
(select distinct 
	pg.numero_pratica as numero_pratica,
	ltr_pg.long_description as tipo_pratica,
	cpra.nome::text as comune_pratica,
	cpra.id::text as id_comune_pratica,
	to_char(pg.data_richiesta, 'DD/MM/YYYY')::text as data_pratica
	from pratiche_gins pg 
		left join rel_eventi_pratiche rep on pg.id = rep.id_pratica
		left join eventi_su_osa eo on rep.id_evento = eo.id 
		join suap_lookup_tipo_richiesta ltr_pg on ltr_pg.code = pg.id_tipo_operazione
		join comuni1 cpra on pg.id_comune_richiedente = cpra.id
		
		where pg.id_comune_richiedente = _cod_comune 
			and eo.id_stabilimento = _id_stab 
		        and pg.trashed_date is null 
		        and pg.id_causale = 1 
		        and pg.apicoltura is not true
		        and pg.data_richiesta > (now() - interval '30 days'))

)
tab_interna order by tab_interna.data_pratica desc limit 100
) tab;

END;	
    
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.recupero_pratiche_suap(integer, integer)
  OWNER TO postgres;
