alter table rel_checklist_sorv_ml  add column note_hd text; 

--82
select 'insert into rel_checklist_sorv_ml(id_linea, id_checklist, enabled, note_hd) values('||m8.id_linea||','||rel.id_checklist||',true,''Aggiornamento della associazione linea-checklist anche per ml8'');',
 m8.id_linea, m8.codice_univoco, m10.codice_univoco, rel.id_checklist, m10.id_linea 
from master_list_flag_linee_attivita m8
join master_list_flag_linee_attivita m10 on m8.codice_univoco = m10.codice_univoco and m8.rev=8 and m10.rev=10
left join rel_checklist_sorv_ml rel on rel.id_linea = m10.id_linea and rel.enabled
where id_checklist is not null


-------------------- bugfix---------------------------------------

select * from ml8_linee_attivita_nuove_materializzata  where path_descrizione ilike '%GERMOGLI->EPC CENTRO DI CONFEZIONAMENTO%' and livello = 3 
select * from rel_checklist_sorv_ml  where id_linea  in (40658,40666,40370,40366)

update rel_checklist_sorv_ml set note_hd = concat_ws('-', note_hd, 'Relazione disabilitata tra linea-chk a seguito di test'), enabled = false where  id_linea  in (40658,40666,40370,40366)
select * from giava.get_checklist_by_idlinea(40366)

select * from master_list_flag_linee_attivita where id_linea  in (40658,40666,40370,40366)
