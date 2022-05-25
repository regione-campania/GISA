


select distinct(o.id) as id_stabilimento ,'update opu_stabilimento set tipo_attivita=1 where tipo_attivita = -1 and id='||o.id||';' from opu_stabilimento o
left join opu_relazione_stabilimento_linee_produttive op on o.id=op.id_stabilimento
left join master_list_suap m on m.id = op.id_linea_produttiva
where o.tipo_attivita = -1 and o.trashed_date is null  and m.enabled and m.flag_nuova_gestione 



