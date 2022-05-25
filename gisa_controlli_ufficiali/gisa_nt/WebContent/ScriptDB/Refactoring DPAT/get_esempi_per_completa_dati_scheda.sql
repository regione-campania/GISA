select * from opu_stabilimento where entered >= '19/06/2020'

select * from opu_relazione_stabilimento_linee_produttive where id_stabilimento = 316590


select * from linee_attivita_controlli_ufficiali where id_controllo_ufficiale = 1016757
select * from opu_relazione_stabilimento_linee_produttive order by id desc limit 5




select o.org_id, o.tipo_dest, o.name from organization o , ricerche_anagrafiche_old_materializzata r
where r.riferimento_id = o.org_id and  o.trashed_Date is null and o.tipologia = 1 and (select count(*) from ticket where org_id = o.org_id)>0 
and lower(tipo_dest) = 'autoveicolo' --and tipo_dest <> 'Es. Commerciale'
order by o.entered desc 
limit 100