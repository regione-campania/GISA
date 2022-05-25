-- da lanciare in esercizio
delete from lista_linee_vecchia_anagrafica_stabilimenti_soggetti_a_scia; --270945 

insert into lista_linee_vecchia_anagrafica_stabilimenti_soggetti_a_scia (
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
select o.org_id,o.tipologia,id_opu_lookup_norme_master_list,'STABILIMENTI RICONOSCIUTI REG 853 PER LA PRODUZIONE E TRASFORMAZIONE ALIMENTI DI ORIGINE ANIMALE',cc.description,ssa.attivita,
null::text as att_spec,null::text as note
,o.org_id ,
2 as id_tipo_linea_reg_ric
from organization o
left join stabilimenti_sottoattivita ssa on ssa.id_stabilimento = o.org_id and ssa.trashed_date is null
left join lookup_categoria cc on cc.code = ssa.codice_sezione::int
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 3 and ( o.trashed_date is null and direct_bill=false)  
UNION
select o.org_id,o.tipologia,id_opu_lookup_norme_master_list,'STABILIMENTI RICONOSCIUTI REG CE 1069/06 PER SOTTOPRODOTTI DI ORIGINE ANIMALE (SOA)',cc.description,ssa.attivita,
null::text as att_spec,null::text as note
,o.org_id,
case when cc.code in(37,
38,
39) then 1 else 2 end as id_tipo_linea_reg_ric
from organization o
 join soa_sottoattivita ssa on ssa.id_soa = o.org_id and ssa.trashed_date is null
 join lookup_categoria_soa cc on cc.code = ssa.codice_sezione::int
 join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 97 and ( o.trashed_date is null ) 
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
UNION
select o.org_id,o.tipologia,id_opu_lookup_norme_master_list,'IGIENE URBANA VETERINARIA','ALLEVAMENTI/CONCENTRAZIONI ANIMALI NON DESTINATI ALLA PRODUZIONE DI ALIMENTI PER USO UMANO',
'CANILE/PENSIONE',
null::text as att_spec,null::text as note,o.org_id,1 as id_tipo_linea_reg_ric
from organization o
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 10 and ( o.trashed_date is null ) 
UNION
select o.org_id,o.tipologia,id_opu_lookup_norme_master_list,'IGIENE URBANA VETERINARIA','ALLEVAMENTI/CONCENTRAZIONI ANIMALI NON DESTINATI ALLA PRODUZIONE DI ALIMENTI PER USO UMANO','OPERATORE COMMERCIALE',
null::text as att_spec,null::text as note,o.org_id,1 as id_tipo_linea_reg_ric
from organization o
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 20 and ( o.trashed_date is null ) 
UNION
select o.org_id,o.tipologia,id_opu_lookup_norme_master_list,'MANGIMISTICA','MANGIMISTICA', coalesce(impianto.description,'OSM RICONOSCIUTI'),
null::text as att_spec,null::text as note,o.org_id,2 as id_tipo_linea_reg_ric
from organization o
left join lookup_impianto_osm impianto on impianto.code = o.impianto
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
where o.tipologia = 800 and ( o.trashed_date is null ) 
UNION
select o.org_id,o.tipologia,id_opu_lookup_norme_master_list,'MANGIMISTICA IN GENERE (STABILIMENTI REGISTRATI)','MANGIMISTICA IN GENERE (STABILIMENTI REGISTRATI)', coalesce(l.description,'OSM REGISTRATI'),
null::text as att_spec,null::text as note,o.org_id,1 as id_tipo_linea_reg_ric
from organization o
join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
left join  elenco_attivita_osm_reg e ON e.org_id = o.org_id 
left join lookup_attivita_osm_reg l on (e.id_attivita = l.code)
where o.tipologia = 801 and ( o.trashed_date is null ) 
)
