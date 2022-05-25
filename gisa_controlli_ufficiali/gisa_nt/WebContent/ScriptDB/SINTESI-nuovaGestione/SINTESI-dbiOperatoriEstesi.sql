CREATE OR REPLACE FUNCTION public_functions.dbi_get_operatori_mercato(idrelstab integer)
  RETURNS table (nome text, valore text) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR nome, valore
		in
		SELECT aa.nome, aa.valore from
		(select
		(json_array_elements(valori_json)->>'name')::text as nome,
		(json_array_elements(valori_json)->>'value')::text as valore
		FROM campi_estesi_valori_v2 v
		join sintesis_relazione_stabilimento_linee_produttive s on s.id= v.id_rel_stab_lp
		where v.id_rel_stab_lp = idrelstab and s.id_linea_produttiva in (394,426)	 
		) aa where aa.nome<>'-1' and (aa.nome ilike 'ragione sociale' or aa.nome ilike 'num. box')	
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_operatori_mercato(integer)
  OWNER TO postgres;


select * from public_functions.dbi_get_operatori_mercato(1959)