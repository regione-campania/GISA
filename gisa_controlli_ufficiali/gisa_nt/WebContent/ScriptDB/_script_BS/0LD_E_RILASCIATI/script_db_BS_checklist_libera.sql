

-- Function: public.get_cu_richiede_checklist_libera(integer)

-- DROP FUNCTION public.get_cu_richiede_checklist_libera(integer);

CREATE OR REPLACE FUNCTION public.get_cu_richiede_checklist_libera(idcu integer)
  RETURNS boolean AS
$BODY$
   DECLARE
flag boolean;   

altId integer;
stabId integer;
provvedimenti integer;
idLinea integer;

BEGIN
flag := false;
provvedimenti := -1;
idLinea := -1;

altId := (select alt_id from ticket where ticketid = idcu);

if altId<=0 THEN
return false;
END IF;

stabId := (select id from sintesis_stabilimento where alt_id = altId);

provvedimenti := (select provvedimenti_prescrittivi from ticket where ticketid = idCu);
if provvedimenti <> 5 THEN
return false;
END IF; 

idLinea := (select rel.id_linea_produttiva from sintesis_relazione_stabilimento_linee_produttive rel
join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva
join opu_lookup_norme_master_list norme on norme.code = linee.id_norma
where norme.codice_norma ilike '1069-09' and rel.id_stabilimento = stabId limit 1);

if idLinea > 0 THEN
flag := true;
END IF;


	RETURN flag;
	
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_cu_richiede_checklist_libera(integer)
  OWNER TO postgres;
