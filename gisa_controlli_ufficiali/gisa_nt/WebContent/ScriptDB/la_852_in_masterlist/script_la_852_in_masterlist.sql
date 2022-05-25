ALTER TABLE la_imprese_linee_attivita ADD COLUMN mappato BOOLEAN DEFAULT FALSE;

-- Restore della tabella di mapping tramite il file "tabella_mapping.backup" o "tabella_iso_mapping.backup"

-- Modifica scheda centralizzata per l'operatore 852
-- LABEL: Codice Ateco/Linea attivita' principale
-- SELECT:
   	case when lcd.description is not null then lcd.description else '' end || ' ' || 
   	case when lcd.short_description is not null then lcd.short_description else '' end || ' ' || 
   	case when la.categoria is not null then la.categoria else '' end || ' - ' || 
   	case when la.linea_attivita is not null then la.linea_attivita else '' end || ' ' || 
   	case when opu.macroarea is not null then '<br><b>Macroarea: </b>' || opu.macroarea else '' end || ' ' || 
   	case when opu.aggregazione is not null then '<br><b>Aggregazione: </b>' || opu.aggregazione else '' end || ' ' || 
   	case when opu.attivita is not null then '<br><b>Attivita: </b>' || opu.attivita else '' end  || ' ' ||
   	case when opu.descrizione is not null then '<br><b>Descrizione: </b>' || opu.descrizione else '' end
-- FROM:
	la_imprese_linee_attivita i left join la_imprese_linee_attivita lda on (i.org_id = lda.org_id and lda.primario is 
   	true and lda.trashed_date is null) left join la_rel_ateco_attivita rat on (lda.id_rel_ateco_attivita = rat.id) left join 
   	lookup_codistat lcd on (rat.id_lookup_codistat = lcd.code) left join opu_linee_attivita_nuove opu 
   	on opu.id_nuova_linea_attivita = i.id_attivita_masterlist, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod 
-- WHERE:
   	i.trashed_date is null   
   	and i.org_id= #orgid# 
   	and i.id_rel_ateco_attivita=rel.id  
   	and rel.id_linee_attivita=la.id    
   	and rel.id_lookup_codistat=cod.code   
   	and i.primario = true    
   	and i.trashed_date is null

-- LABEL: Codici/Linee attivita' secondarie
-- SELECT: 
   	case when lcd.description is not null then lcd.description else '' end || ' ' || 
  	case when lcd.short_description is not null then lcd.short_description else '' end || ' ' || 
  	case when la.categoria is not null then la.categoria else '' end || ' - ' || 
  	case when la.linea_attivita is not null then la.linea_attivita else '' end || ' ' || 
  	case when opu.macroarea is not null then '<br><b>Macroarea: </b>' || opu.macroarea else '' end || ' ' || 
  	case when opu.aggregazione is not null then '<br><b>Aggregazione: </b>' || opu.aggregazione else '' end || ' ' || 
  	case when opu.attivita is not null then '<br><b>Attivita: </b>' || opu.attivita else '' end  || ' ' ||
	case when opu.descrizione is not null then '<br><b>Descrizione: </b>' || opu.descrizione else '' end
-- FROM:
	la_imprese_linee_attivita i left join la_rel_ateco_attivita rat on (i.id_rel_ateco_attivita = rat.id) left 
	join lookup_codistat lcd on (rat.id_lookup_codistat = lcd.code)  left join opu_linee_attivita_nuove 
	opu on opu.id_nuova_linea_attivita = i.id_attivita_masterlist, la_rel_ateco_attivita rel, la_linee_attivita la, 
	lookup_codistat cod 
-- WHERE:
	i.primario is false
	and i.trashed_date is null 
	and i.org_id=  #orgid#
	and i.id_rel_ateco_attivita=rel.id 
	and rel.id_linee_attivita=la.id 
	and rel.id_lookup_codistat=cod.code 
	and i.primario=false 
	and i.trashed_date is null
	
	
-- SETTAGGIO PERMESSI
INSERT INTO permission(category_id, permission, permission_view, permission_add, 
  permission_edit, permission_delete, description, level, enabled, active, viewpoints)
  VALUES (1,'accounts-accounts-linee-pregresse',true,true,true,true,'Controllo bottone per l''aggiornamento delle linee pregresse',0,true,true,false);

INSERT INTO permission(category_id, permission, permission_view, permission_add, 
  permission_edit, permission_delete, description, level, enabled, active, viewpoints)
  VALUES (1,'accounts-accounts-cu-linee-pregresse',true,true,true,true,'Controllo per l''inserimento di un controllo ufficiale in presenza di linee pregresse',0,true,true,false);

update la_imprese_linee_attivita set id_attivita_masterlist = -999 where trashed_date is null and mappato = false
select 'update la_imprese_linee_attivita set id_attivita_masterlist= 20121, mappato = false where org_id = '||o.org_id||';',* 
from organization o
join la_imprese_linee_attivita la on la.org_id = o.org_id
where tipo_dest ilike '%distributor%' and o.trashed_date is null and la.trashed_date is null

delete from ricerche_anagrafiche_old_materializzata where tipologia_operatore = 1;
insert into ricerche_anagrafiche_old_materializzata  (select * from ricerca_anagrafiche  where tipologia_operatore = 1) ;
