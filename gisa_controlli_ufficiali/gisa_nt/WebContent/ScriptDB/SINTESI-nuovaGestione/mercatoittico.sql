select distinct *,
'INSERT into campi_estesi_valori_v2 (id_rel_stab_lp, valori_json) values ('||id_rel||', ''[{"name":"RAGIONE SOCIALE","value":"'||name||'"},{"name":"NUM. BOX","value":"'||employees||'"},{"name":"COMUNE","value":"'||COALESCE(id_comune, -1)||'"},{"name":"command","value":"SalvaValoriCampiEstesiv2"}]'');'
 from (
select o.org_id,  o.name, o.employees, c.id as id_comune, oami.id_mercato_ittico
from operatori_associati_mercato_ittico oami
join organization o on oami.id_operatore = o.org_id and oami.contenitore_mercato_ittico = 'organization'
left join organization_address oa on oa.org_id = o.org_id and oa.address_type = 5
left join comuni1 c on oa.city ilike c.nome
where direct_bill = true and o.trashed_date is null and oami.importato_in_anagrafica = false
) a
left join (
select i.riferimento_org_id, s.id as id_import, rel.id_stabilimento, rel.id as id_rel
from sintesis_stabilimenti_import i
join sintesis_stabilimento s on btrim(s.approval_number) ilike btrim(i.approval_number)
join sintesis_relazione_stabilimento_linee_produttive rel on rel.id_stabilimento = s.id and rel.id_linea_produttiva in (select id from suap_master_list_6_linea_attivita where linea_attivita ilike '%wm%')
where i.riferimento_org_id > 0

) b

on a.id_mercato_ittico = b.riferimento_org_id

-----

select distinct *,
'INSERT into campi_estesi_valori_v2 (id_rel_stab_lp, valori_json) values ('||id_rel||', ''[{"name":"RAGIONE SOCIALE","value":"'||name||'"},{"name":"NUM. BOX","value":"'||employees||'"},{"name":"COMUNE","value":"'||COALESCE(id_comune, -1)||'"},{"name":"command","value":"SalvaValoriCampiEstesiv2"}]'');'
 from (
select o.org_id,  o.name, o.employees, c.id as id_comune, oami.id_mercato_ittico
from operatori_associati_mercato_ittico oami
join organization o on oami.id_operatore = o.org_id and oami.contenitore_mercato_ittico = 'opu'
left join organization_address oa on oa.org_id = o.org_id and oa.address_type = 5
left join comuni1 c on oa.city ilike c.nome
where direct_bill = true and o.trashed_date is null
) a
left join (
select i.riferimento_id_stabilimento, s.id as id_import, rel.id_stabilimento, rel.id as id_rel
from sintesis_stabilimenti_import i
join sintesis_stabilimento s on btrim(s.approval_number) ilike btrim(i.approval_number)
join sintesis_relazione_stabilimento_linee_produttive rel on rel.id_stabilimento = s.id and rel.id_linea_produttiva in (select id from suap_master_list_6_linea_attivita where linea_attivita ilike '%wm%')
where i.riferimento_id_stabilimento > 0 

) b

on a.id_mercato_ittico = b.riferimento_id_stabilimento
