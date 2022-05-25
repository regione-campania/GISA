delete from lista_linee_vecchia_anagrafica_stabilimenti_soggetti_a_scia; --248574

insert into lista_linee_vecchia_anagrafica_stabilimenti_soggetti_a_scia ( --245640
select la.id as id,o.tipologia as id_tipologia_operatore,id_opu_lookup_norme_master_list as id_norma,'STABILIMENTI REGISTRATI 852'::text as macroarea,
ist.description::text as aggregazione,
ist.short_description::text as attivita,ll.linea_attivita::text as attivita_specifica,null::text as note, o.org_id as org_id,
1 as id_tipo_linea_reg_ric
from organization o
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
join la_imprese_linee_attivita la on la.org_id = o.org_id
join la_rel_ateco_attivita r on r.id = la.id_rel_ateco_attivita
join lookup_codistat ist on ist.code = r.id_lookup_codistat
join la_linee_attivita ll on ll.id = id_linee_attivita
where o.tipologia = 1 and ( o.trashed_date is null or o.trashed_date='1970-01-01') and la.trashed_date is null
union
select o.org_id,o.tipologia,id_opu_lookup_norme_master_list,'PRODUZIONE PRIMARIA REGISTRABILI','ATTIVITA'' INERENTI GLI ANIMALI',
tipologia_strutt,specie_allev||case when orientamento_prod is not null then orientamento_prod else '' end,null::text as note
,o.org_id,1 as id_tipo_linea_reg_ric
from organization o
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 2 and ( o.trashed_date is null) 
UNION
select o.org_id,o.tipologia,id_opu_lookup_norme_master_list,'OPERATORI 193','OPERATORI 193','OPERATORI 193',
null::text as att_spec,null::text as note
,o.org_id,
1 as id_tipo_linea_reg_ric
from organization o
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 151 and ( o.trashed_date is null ) 
UNION
select o.org_id,o.tipologia,id_opu_lookup_norme_master_list,'FARMACIE/GROSSISTI/PARAFARMACIE','FARMACIE/GROSSISTI/PARAFARMACIE','FARMACIE/GROSSISTI/PARAFARMACIE',
null::text as att_spec,null::text as note,o.org_id,1 as id_tipo_linea_reg_ric
from organization o
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 802 and ( o.trashed_date is null ) 
UNION
select o.org_id,o.tipologia,id_opu_lookup_norme_master_list,'STRUTTURE VETERINARIE','STRUTTURE VETERINARIE','STRUTTURE VETERINARIE',
null::text as att_spec,null::text as note,o.org_id,1 as id_tipo_linea_reg_ric
from organization o
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 152 and ( o.trashed_date is null ) 


)