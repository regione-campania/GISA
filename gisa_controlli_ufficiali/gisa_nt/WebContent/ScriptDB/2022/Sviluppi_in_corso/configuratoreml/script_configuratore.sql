
CREATE OR REPLACE FUNCTION public.configuratore_get_ml()
  RETURNS TABLE(codice_norma text, norma text, id_macroarea integer, macroarea text, codice_sezione text, id_aggregazione integer, aggregazione text, codice_attivita text, id_linea integer, linea_attivita text, codice_prodotto_specie text, codice_univoco text, categorizzabili boolean, categoria_rischio_default integer) AS
$BODY$
DECLARE
BEGIN
	return query
	select 

	m.codice_norma, m.norma,
	m.id, m.macroarea, m.codice_sezione,
	a.id, a.aggregazione, a.codice_attivita,
	l.id, l.linea_attivita, l.codice_prodotto_specie, l.codice_univoco,
	f.categorizzabili, f.categoria_rischio_default

	from 

	master_list_macroarea m 
	left join master_list_aggregazione a on m.id = a.id_macroarea and a.rev = 10 
	left join master_list_linea_attivita l on a.id = l.id_aggregazione and l.rev = 10
	left join master_list_flag_linee_attivita f on l.id = f.id_linea 

	where m.rev = 10

	order by  m.id asc, a.id asc, l.id asc;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.configuratore_get_ml()
  OWNER TO postgres;

select * from public.configuratore_get_ml()