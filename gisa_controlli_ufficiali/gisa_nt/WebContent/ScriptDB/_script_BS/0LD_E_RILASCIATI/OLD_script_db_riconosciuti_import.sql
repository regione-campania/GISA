-- Da lanciare PRIMA degli script delle funzioni

 

DROP INDEX public.sintesis_stab_evita_num_reg_duplicati;
  
CREATE UNIQUE INDEX unique_chiavi_sintesis_soggetto_fisico ON sintesis_soggetto_fisico (codice_fiscale) WHERE trashed_date IS NULL;
CREATE UNIQUE INDEX unique_chiavi_sintesis_operatore ON sintesis_operatore (codice_fiscale_impresa, partita_iva, ragione_sociale, id_indirizzo) WHERE trashed_date IS NULL;
CREATE UNIQUE INDEX unique_chiavi_sintesis_stabilimento ON sintesis_stabilimento (id_operatore, id_indirizzo, approval_number) WHERE trashed_date IS NULL;
CREATE UNIQUE INDEX unique_chiavi_sintesis_rel_operatore_soggetto_fisico ON sintesis_rel_operatore_soggetto_fisico (id_operatore, id_soggetto_fisico) ;
CREATE UNIQUE INDEX unique_chiavi_sintesis_indirizzo ON sintesis_indirizzo (comune, via, toponimo, civico);



-- Da lanciare DOPO aver fatto l'import


select 'insert into sintesis_linee_attivita_controlli_ufficiali (id_controllo_ufficiale, id_linea_attivita) values (' || id_controllo_ufficiale || ', ' || (select id from sintesis_relazione_stabilimento_linee_produttive where id_istanza_old = id_linea_attivita) ||');' 
'update opu_linee_attivita_controlli_ufficiali set flag_spostamento = true, note = ''importato in sintesis_linee_attivita_controlli_ufficiali'' where id_controllo_ufficiale =' || id_controllo_ufficiale || ' and id_linea_attivita= ' || id_linea_attivita ||';'
,id_controllo_ufficiale, id_linea_attivita,  (select id from sintesis_relazione_stabilimento_linee_produttive where id_istanza_old = id_linea_attivita)
from opu_linee_attivita_controlli_ufficiali
where id_linea_attivita in 
(select id_istanza_old from sintesis_relazione_stabilimento_linee_produttive)



select 'update ticket set id_stabilimento_old = id_stabilimento, id_stabilimento = null, note_internal_use_only = concat_ws('';'', note_internal_use_only, ''Spostato su sintesis''), alt_id = (select return_alt_id from gestione_id_alternativo('|| id_stabilimento_new ||', 6)) where id_stabilimento = ' || id_stabilimento_old ||';', *
 from log_import_opu_sintesis where importato = true




CREATE OR REPLACE  FUNCTION public.importa_cu_org_sintesis(org_id_old integer)
  RETURNS text AS
$BODY$
DECLARE
alt_id_new integer;
contatore integer;
msg text;
cursIdControllo integer;
cursLinea text; 
cursCodice text;
cursLineaDesc text;
cursore refcursor;
idLinea integer;
idRel integer;
idStabilimento integer;
BEGIN

contatore :=0;
alt_id_new := (select alt_id from sintesis_stabilimento where riferimento_org_id = org_id_old and entered > '2017-06-22');
idStabilimento := (select id from sintesis_stabilimento where riferimento_org_id = org_id_old and entered > '2017-06-22');

IF alt_id_new > 0 THEN 

update ticket set id_stabilimento_old = org_id, org_id = null, note_internal_use_only = concat_ws(';', note_internal_use_only, 'Spostato su sintesis (da organization)'), alt_id = alt_id_new where org_id = org_id_old;

open cursore for 

select id_controllo_ufficiale, linea_attivita_stabilimenti_soa, descrizione_codice, linea_attivita_stabilimenti_soa_desc
 from linee_attivita_controlli_ufficiali_stab_soa where id_controllo_ufficiale in (select ticketid from ticket where tipologia = 3 and id_stabilimento_old = org_id_old and note_internal_use_only ilike '%Spostato su sintesis (da organization)%' and ticketid not in (select id_controllo_ufficiale from sintesis_linee_attivita_controlli_ufficiali));

 LOOP
	fetch cursore into cursIdControllo, cursLinea, cursCodice, cursLineaDesc;
	EXIT WHEN NOT FOUND;
	 
idLinea = -1;
idRel = -1;
contatore = contatore+1;
idLinea = mapping_linea_attivita(1, cursLinea, COALESCE(cursCodice, cursLineaDesc));
idRel = (select id from sintesis_relazione_stabilimento_linee_produttive where id_stabilimento = idStabilimento and id_linea_produttiva = idLinea);

insert into sintesis_linee_attivita_controlli_ufficiali (id_controllo_ufficiale, id_linea_attivita) values (cursIdControllo,idRel);

update linee_attivita_controlli_ufficiali_stab_soa set flag_spostamento = true, note = 'importato in sintesis_linee_attivita_controlli_ufficiali' where id_controllo_ufficiale = cursIdControllo;

end loop; 

msg := 'OK: '||contatore;
ELSE
msg := 'KO: nessuno stabilimento SINTESIS trovato';
END IF; 


return msg;


 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.importa_cu_org_sintesis(integer)
  OWNER TO postgres;
  


CREATE OR REPLACE  FUNCTION public.importa_macelli_org_sintesis(org_id_old integer)
  RETURNS text AS
$BODY$
DECLARE
alt_id_new integer;
msg text;

BEGIN

alt_id_new := (select alt_id from sintesis_stabilimento where riferimento_org_id = org_id_old);

IF alt_id_new > 0 THEN 

 update m_capi set id_macello = alt_id_new, notes_hd = 'Spostato da macello '||org_id_old||' a macello '||alt_id_new ||' per import SINTESIS' where id_macello = org_id_old;
 update m_capi_sedute set id_macello =alt_id_new, notes_hd = 'Spostato da macello '||org_id_old||' a macello '||alt_id_new ||' per import SINTESIS' where id_macello = org_id_old;
 update m_vpm_tamponi set id_macello =alt_id_new, notes_hd = 'Spostato da macello '||org_id_old||' a macello '||alt_id_new ||' per import SINTESIS' where id_macello = org_id_old;
 update m_partite set id_macello =alt_id_new, notes_hd = 'Spostato da macello '||org_id_old||' a macello '||alt_id_new ||' per import SINTESIS' where id_macello = org_id_old;
update m_partite_sedute set id_macello =alt_id_new, notes_hd = 'Spostato da macello '||org_id_old||' a macello '||alt_id_new ||' per import SINTESIS' where id_macello = org_id_old;


msg := 'OK';
ELSE
msg := 'KO: nessuno stabilimento SINTESIS trovato';
END IF; 

return msg;


 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.importa_macelli_org_sintesis(integer)
  OWNER TO postgres;

  
  CREATE OR REPLACE  FUNCTION public.importa_macelli_opu_sintesis(alt_id_old integer, alt_id_new integer)
  RETURNS text AS
$BODY$
DECLARE
msg text;

BEGIN


IF alt_id_new > 0 THEN 

 update m_capi set id_macello = alt_id_new, notes_hd = 'Spostato da macello '||alt_id_old||' a macello '||alt_id_new ||' per import SINTESIS' where id_macello = alt_id_old;
 update m_capi_sedute set id_macello =alt_id_new, notes_hd = 'Spostato da macello '||alt_id_old||' a macello '||alt_id_new ||' per import SINTESIS' where id_macello = alt_id_old;
 update m_vpm_tamponi set id_macello =alt_id_new, notes_hd = 'Spostato da macello '||alt_id_old||' a macello '||alt_id_new ||' per import SINTESIS' where id_macello = alt_id_old;
 update m_partite set id_macello =alt_id_new, notes_hd = 'Spostato da macello '||alt_id_old||' a macello '||alt_id_new ||' per import SINTESIS' where id_macello = alt_id_old;
update m_partite_sedute set id_macello =alt_id_new, notes_hd = 'Spostato da macello '||alt_id_old||' a macello '||alt_id_new ||' per import SINTESIS' where id_macello = alt_id_old;


msg := 'OK';
ELSE
msg := 'KO: nessuno stabilimento SINTESIS trovato';
END IF; 

return msg;


 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.importa_macelli_opu_sintesis(integer, integer)
  OWNER TO postgres;
  
  
  
  


select  

path_completo_linea_produttiva_old,part1,part2,part3 ,mapping_linea_attivita_by_orsa(part1, part2, part3),
'update sintesis_relazione_stabilimento_linee_produttive set id_linea_produttiva = ' || mapping_linea_attivita_by_orsa(part1, part2, part3) ||' where id_linea_produttiva is null and path_completo_linea_produttiva_old = '''|| replace(path_completo_linea_produttiva_old, '''', '''''') ||''';'

 from

(select distinct(path_completo_linea_produttiva_old), 

split_part(path_completo_linea_produttiva_old, '->', 1) AS part1, 
split_part(path_completo_linea_produttiva_old, '->', 2)  AS part2,
split_part(path_completo_linea_produttiva_old, '->', 3)  AS part3 

from sintesis_relazione_stabilimento_linee_produttive
) aa
  

--test



select riferimento_id, ( btrim(num_riconoscimento) , ragione_sociale, btrim(partita_iva) , asl_rif)
, 'update sintesis_stabilimento set riferimento_org_id = '||riferimento_id||' where btrim(approval_number) = '''|| btrim(num_riconoscimento) ||''' ;'

 from ricerche_anagrafiche_old_materializzata where
id_norma in (5,6) 
and riferimento_id_nome ilike 'orgId' and
( btrim(num_riconoscimento) , ragione_sociale, btrim(partita_iva) , asl_rif) in
(select  btrim(s.approval_number) , o.ragione_sociale, btrim(o.partita_iva) , s.id_asl from sintesis_stabilimento s join sintesis_operatore o on o.id = s.id_operatore )



-- import mercati ittici opu

select 
'insert into campi_estesi_valori_v2 (id_rel_stab_lp, valori_json) values ('||log.id_rel_stab_lp_new ||', ''[{"name":"RAGIONE SOCIALE","value":"'|| replace(merc.name, '''', '''''') ||'"},{"name":"NUM. BOX","value":"'|| merc.employees || '"},{"name":"COMUNE","value":"'|| comuni.id||'"},{"name":"COMMAND","value":"SalvaValoriCampiEstesiv2"}]'' );'
 from operatori_associati_mercato_ittico rel
left join opu_stabilimento opustab on opustab.id = rel.id_mercato_ittico  
 left join opu_operatore opuop on opuop.id = opustab.id_operatore
left join organization merc on merc.org_id = rel.id_operatore
LEFT JOIN organization_address oa on oa.org_id = merc.org_id and oa.address_type = 1
left join comuni1 comuni on comuni.nome ilike oa.city
left join log_import_opu_sintesis log on log.id_stabilimento_old = opustab.id
 where rel.contenitore_mercato_ittico ='opu'

 -- import mercati ittici org

select 
'insert into campi_estesi_valori_v2 (id_rel_stab_lp, valori_json) values ('||log.id_rel_stab_lp_new ||', ''[{"name":"RAGIONE SOCIALE","value":"'|| replace(merc.name, '''', '''''') ||'"},{"name":"NUM. BOX","value":"'|| merc.employees || '"},{"name":"COMUNE","value":"'|| comuni.id||'"},{"name":"COMMAND","value":"SalvaValoriCampiEstesiv2"}]'' );'
 from operatori_associati_mercato_ittico rel
left join opu_stabilimento opustab on opustab.id = rel.id_mercato_ittico  
 left join opu_operatore opuop on opuop.id = opustab.id_operatore
left join organization merc on merc.org_id = rel.id_operatore
LEFT JOIN organization_address oa on oa.org_id = merc.org_id and oa.address_type = 1
left join comuni1 comuni on comuni.nome ilike oa.city
left join log_import_opu_sintesis log on log.id_stabilimento_old = opustab.id
 where rel.contenitore_mercato_ittico ='opu'
