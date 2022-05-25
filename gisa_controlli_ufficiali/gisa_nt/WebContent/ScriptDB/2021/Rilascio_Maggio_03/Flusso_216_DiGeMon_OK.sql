
-- DROP FUNCTION digemon.get_norme_ml();

CREATE OR REPLACE FUNCTION digemon.get_norme_ml()
  RETURNS TABLE(code integer, description text, codice_norma text) AS
$BODY$
DECLARE

BEGIN
RETURN QUERY

	select l.code, l.description, l.codice_norma from opu_lookup_norme_master_list l where enabled;  
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.get_norme_ml()
  OWNER TO postgres;

 DROP FUNCTION digemon.get_info_masterlist(text);
CREATE OR REPLACE FUNCTION digemon.get_info_masterlist(IN _codice_univoco text DEFAULT NULL::text)
  RETURNS TABLE(codice_univoco text, id_norma integer, codice_norma text, id_macroarea integer, macroarea text, codice_macroarea text, id_aggregazione integer, aggregazione text, 
  codice_aggregazione text, id_linea integer, attivita text, codice_attivita text, mobile boolean, fisso boolean, apicoltura boolean, registrabili boolean, 
  riconoscibili boolean, sintesis boolean, bdu boolean, vam boolean, no_scia boolean, categorizzabili boolean, rev integer, categoria_rischio_default integer) AS
$BODY$
DECLARE

BEGIN

FOR  	 
        codice_univoco, id_norma, codice_norma, id_macroarea, macroarea, codice_macroarea, id_aggregazione, aggregazione, codice_aggregazione, id_linea, attivita, codice_attivita, mobile, fisso, apicoltura, 
        registrabili, riconoscibili, sintesis, bdu, vam, no_scia, categorizzabili, rev, categoria_rischio_default
in
        select distinct m.codice_univoco, 
        --se si gestrà il codice norma nella tabella materializzata delle linee si potrà rimuovere il left join con le norme..
	l.id_norma, l.codice_norma, l.id_macroarea, l.macroarea, l.codice_macroarea, l.id_aggregazione, l.aggregazione, l.codice_aggregazione,l.id_nuova_linea_attivita as id_linea, l.attivita, l.codice_attivita, 
        m.mobile, m.fisso, m.apicoltura, m.registrabili, m.riconoscibili, m.sintesis, m.bdu, m.vam, m.no_scia, m.categorizzabili, m.rev, m.categoria_rischio_default
        from master_list_flag_linee_attivita m 
        join ml8_linee_attivita_nuove_materializzata l on l.id_nuova_linea_attivita = m.id_linea and l.livello=3
        where 1=1 
        and ( _codice_univoco is null or m.codice_univoco = _codice_univoco)

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
  
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.get_info_masterlist(text)
  OWNER TO postgres;