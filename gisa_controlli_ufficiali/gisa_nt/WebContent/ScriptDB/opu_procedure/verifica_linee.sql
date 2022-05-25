-- Presenza del bottone giallo "Aggiorna linee"
select linee_pregresse from opu_stabilimento where id = 162097
--true: il bottone appare; false: non appare

-- Incongruenza tra flag e linee

select rel.id_stabilimento, rel.id_linea_produttiva, ml.id_nuova_linea_attivita, ml.macroarea, ml.aggregazione, ml.attivita from 
opu_relazione_stabilimento_linee_produttive rel
left join opu_linee_attivita_nuove ml on ml.id_nuova_linea_attivita = rel.id_linea_produttiva
 where id_stabilimento in (
select s.id from opu_stabilimento s
inner join organization o on o.org_id = s.riferimento_org_id
where o.tipologia in (800, 801) and s.linee_pregresse = false and s.trashed_date is null
)
and ml.macroarea not ilike '%mangimistica%' and ml.macroarea not ilike '%TRASPORTO CONTO TERZI%' and rel.enabled

