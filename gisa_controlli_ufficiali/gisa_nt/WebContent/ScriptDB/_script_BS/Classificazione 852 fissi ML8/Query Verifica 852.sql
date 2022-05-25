-- STABILIMENTI 852 FISSI (ES. COMMERCIALE)

select count(*) from organization
where tipologia = 1
and trashed_date is null
and tipo_dest = 'Es. Commerciale'

-- STABILIMENTI 852 MARCATI COME FISSI SECONDO IL MAPPING ORSA 

select count(distinct org_id) from (
select o.org_id as org_id,  id_opu_lookup_norme_master_list as id_norma, o.tipologia as id_tipologia_operatore, COALESCE(mapping.ml8_codice_univoco,ml8_codice_1,ml8_codice_2) as codice_univoco_ml,
mapping.map_completo as map_completo
, case when COALESCE(mapping.ml8_codice_univoco,ml8_codice_1,ml8_codice_2) = 'MS.010.100' then true else flag.fisso end
, case when COALESCE(mapping.ml8_codice_univoco,ml8_codice_1,ml8_codice_2) in ('MS.060.300', 'MS.060-MS.060.300', 'MS.090-MS.090.100') then true else flag.mobile end
, i.id as id_la
,ml8.id_nuova_linea_attivita as linea_id

from organization o
left join la_imprese_linee_attivita i on i.org_id = o.org_id
left join la_rel_ateco_attivita rel on i.id_rel_ateco_attivita=rel.id
left join la_linee_attivita la on rel.id_linee_attivita=la.id  
left join lookup_codistat cod on rel.id_lookup_codistat=cod.code
left join mapping_ateco_ml8 mapping on mapping.ateco ilike cod.description
left join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
left join ml8_linee_attivita_nuove_materializzata ml8 on ml8.codice_attivita = mapping.ml8_codice_univoco
left join master_list_flag_linee_attivita flag on flag.codice_univoco = ml8.codice_attivita
where o.tipologia = 1 and o.trashed_date is null 
and i.trashed_date is null and cod.enabled and cod.description <> '00.00.00'
and COALESCE(mapping.ml8_codice_univoco,ml8_codice_1,ml8_codice_2) not in ('FARM', '0124-AL', '0127-AL', 'MS.000', 'MS.000-0128-AL-C', 'MS.050-MS.050.200-007', 'MS.050-MS.050.200-009', 'MS.060-MS.060.400') )aa where fisso

-- STABILIMENTI 852 ESCLUSIVAMENTE FISSI - MARCATI COME FISSI SECONDO IL MAPPING ORSA E CHE NON HANNO LINEE MOBILI SECONDO IL MAPPING ORSA

select count (distinct org_id)  from
(select o.org_id as org_id,  id_opu_lookup_norme_master_list as id_norma, o.tipologia as id_tipologia_operatore, COALESCE(mapping.ml8_codice_univoco,ml8_codice_1,ml8_codice_2) as codice_univoco_ml,
mapping.map_completo as map_completo
, case when COALESCE(mapping.ml8_codice_univoco,ml8_codice_1,ml8_codice_2) = 'MS.010.100' then true else flag.fisso end
, case when COALESCE(mapping.ml8_codice_univoco,ml8_codice_1,ml8_codice_2) in ('MS.060.300', 'MS.060-MS.060.300', 'MS.090-MS.090.100') then true else flag.mobile end
, i.id as id_la
,ml8.id_nuova_linea_attivita as linea_id

from organization o
left join la_imprese_linee_attivita i on i.org_id = o.org_id
left join la_rel_ateco_attivita rel on i.id_rel_ateco_attivita=rel.id
left join la_linee_attivita la on rel.id_linee_attivita=la.id  
left join lookup_codistat cod on rel.id_lookup_codistat=cod.code
left join mapping_ateco_ml8 mapping on mapping.ateco ilike cod.description
left join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
left join ml8_linee_attivita_nuove_materializzata ml8 on ml8.codice_attivita = mapping.ml8_codice_univoco
left join master_list_flag_linee_attivita flag on flag.codice_univoco = ml8.codice_attivita
where o.tipologia = 1 and o.trashed_date is null 
and i.trashed_date is null and cod.enabled and cod.description <> '00.00.00'
and COALESCE(mapping.ml8_codice_univoco,ml8_codice_1,ml8_codice_2) not in ('FARM', '0124-AL', '0127-AL', 'MS.000', 'MS.000-0128-AL-C', 'MS.050-MS.050.200-007', 'MS.050-MS.050.200-009', 'MS.060-MS.060.400')
)aa where fisso
and org_id not in (select org_id from
(select o.org_id as org_id,  id_opu_lookup_norme_master_list as id_norma, o.tipologia as id_tipologia_operatore, COALESCE(mapping.ml8_codice_univoco,ml8_codice_1,ml8_codice_2) as codice_univoco_ml,
mapping.map_completo as map_completo
, case when COALESCE(mapping.ml8_codice_univoco,ml8_codice_1,ml8_codice_2) = 'MS.010.100' then true else flag.fisso end
, case when COALESCE(mapping.ml8_codice_univoco,ml8_codice_1,ml8_codice_2) in ('MS.060.300', 'MS.060-MS.060.300', 'MS.090-MS.090.100') then true else flag.mobile end

from organization o
left join la_imprese_linee_attivita i on i.org_id = o.org_id
left join la_rel_ateco_attivita rel on i.id_rel_ateco_attivita=rel.id
left join la_linee_attivita la on rel.id_linee_attivita=la.id  
left join lookup_codistat cod on rel.id_lookup_codistat=cod.code
left join mapping_ateco_ml8 mapping on mapping.ateco ilike cod.description
left join opu_lookup_norme_master_list_rel_tipologia_organzation norma on norma.tipologia_organization =o.tipologia
left join ml8_linee_attivita_nuove_materializzata ml8 on ml8.codice_attivita = mapping.ml8_codice_univoco
left join master_list_flag_linee_attivita flag on flag.codice_univoco = ml8.codice_attivita
where o.tipologia = 1 and o.trashed_date is null 
and i.trashed_date is null and cod.enabled and cod.description <> '00.00.00'
and COALESCE(mapping.ml8_codice_univoco,ml8_codice_1,ml8_codice_2) not in ('FARM', '0124-AL', '0127-AL', 'MS.000', 'MS.000-0128-AL-C', 'MS.050-MS.050.200-007', 'MS.050-MS.050.200-009', 'MS.060-MS.060.400')
)aa where mobile
)

-- STABILIMENTI 852 MAPPATI CON NUOVA MASTER LIST

select count(distinct o.org_id) from
organization o left join la_imprese_linee_attivita i on i.org_id = o.org_id
where o.tipologia = 1 and i.id_attivita_masterlist>40000
