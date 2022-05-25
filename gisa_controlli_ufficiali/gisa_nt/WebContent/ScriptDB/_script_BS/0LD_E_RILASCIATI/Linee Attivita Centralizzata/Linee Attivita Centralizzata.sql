--NON LANCIARE (so bber rit)


-- View: public.org_linee_attivita_view

-- DROP VIEW public.org_linee_attivita_view;

CREATE OR REPLACE VIEW public.org_linee_attivita_view AS 

select  DISTINCT

i.id, i.org_id, i.id_rel_ateco_attivita, i.id_attivita_masterlist, i.mappato, i.primario, i.entered, i.entered_by, i.modified, i.modified_by, i.trashed_date, opu.macroarea, opu.aggregazione, opu.attivita, la.categoria, la.linea_attivita, cod.description as codice_istat, cod.short_description as descrizione_codice_istat
FROM la_imprese_linee_attivita i   
left join ml8_linee_attivita_nuove opu on opu.id_attivita = i.id_attivita_masterlist,  la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod  
WHERE i.trashed_date IS NULL   
AND i.id_rel_ateco_attivita=rel.id  
AND rel.id_linee_attivita=la.id    
AND rel.id_lookup_codistat=cod.code  
and i.trashed_date is null


 






--NON LANCIARE (so bber rit)
-- Function: public.get_linee_attivita(integer, text)
-- DROP FUNCTION public.get_linee_attivita(integer, text, boolean, integer);


CREATE OR REPLACE FUNCTION public.get_linee_attivita(IN _riferimentoId integer, IN _riferimentoIdNomeTab text, IN _primario boolean, IN _idcu integer)
  RETURNS TABLE(id integer, riferimento_id integer, id_rel_ateco_attivita integer, id_attivita_masterlist integer, mappato boolean, primario boolean, entered timestamp without time zone, entered_by integer, modified timestamp without time zone, modified_by integer, trashed_date timestamp without time zone, macroarea text, aggregazione text, attivita text, categoria text, linea_attivita text, codice_istat text, descrizione_codice_istat text, id_attivita integer, enabled boolean) AS
$BODY$
DECLARE
r RECORD;	

BEGIN


IF (_riferimentoIdNomeTab = 'organization') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, trashed_date, macroarea , aggregazione , attivita , categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id , v.org_id , v.id_rel_ateco_attivita , v.id_attivita_masterlist , v.mappato, v.primario, v.entered, v.entered_by, v.modified, v.modified_by, v.trashed_date, v.macroarea , v.aggregazione , v.attivita , v.categoria , v.linea_attivita , v.codice_istat , v.descrizione_codice_istat, -1, true 
from org_linee_attivita_view v
left join linee_attivita_controlli_ufficiali lcu on v.id=lcu.id_linea_attivita
where 1=1
and ((_riferimentoId>0 and v.org_id = _riferimentoId) or (_riferimentoId=-1))
and ((_idcu>0 and lcu.id_controllo_ufficiale=_idcu) or (_idcu=-1))
and ((_primario is not null and v.primario = _primario) or (_primario is null))


LOOP
RETURN NEXT;
END LOOP;
END IF;

IF (_riferimentoIdNomeTab = 'opu_stabilimento') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, trashed_date, macroarea , aggregazione , attivita , categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id , v.org_id , v.id_rel_ateco_attivita , -1 , null, v.primario, null, -1, null, -1, null, v.macroarea , null , null , v.categoria , v.linea_attivita , v.codice_istat , null, v.id_attivita, v.enabled
from opu_linee_attivita_stabilimenti_view v
left join opu_linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita = v.id 
where 1=1
and ((_riferimentoId>0 and v.org_id = _riferimentoId and v.enabled) or (_riferimentoId=-1))
and ((_idcu>0 and lcu.id_controllo_ufficiale=_idcu) or (_idcu=-1))

LOOP
RETURN NEXT;
END LOOP;
END IF;

IF (_riferimentoIdNomeTab = 'sintesis_stabilimento') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, trashed_date, macroarea , aggregazione , attivita , categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id , v.alt_id , v.id_rel_ateco_attivita , -1 , null, v.primario, null, -1, null, -1, null, v.macroarea , null , null , v.categoria , v.linea_attivita , v.codice_istat , null, -1, v.enabled
from sintesis_linee_attivita_stabilimenti_view v
left join sintesis_linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita = v.id 
where 1=1
and ((_riferimentoId>0 and v.org_id = _riferimentoId and v.enabled) or (_riferimentoId=-1))
and ((_idcu>0 and lcu.id_controllo_ufficiale=_idcu) or (_idcu=-1))

LOOP
RETURN NEXT;
END LOOP;
END IF;


IF (_riferimentoIdNomeTab = 'suap_ric_scia_stabilimento') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, trashed_date, macroarea , aggregazione , attivita , categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id , v.alt_id , v.id_rel_ateco_attivita , -1 , null, v.primario, null, -1, null, -1, null, v.macroarea , null , null , v.categoria , v.linea_attivita , v.codice_istat , null, -1, v.enabled
from suap_ric_scia_linee_attivita_stabilimenti_view v
left join suap_ric_scia_linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita = v.id 
where 1=1
and ((_riferimentoId>0 and v.org_id = _riferimentoId and v.enabled) or (_riferimentoId=-1))
and ((_idcu>0 and lcu.id_controllo_ufficiale=_idcu) or (_idcu=-1))

LOOP
RETURN NEXT;
END LOOP;
END IF;


     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100
  ROWS 1000;







  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  

