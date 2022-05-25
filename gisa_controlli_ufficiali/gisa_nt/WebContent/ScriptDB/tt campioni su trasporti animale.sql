select tipologia from organization where org_id = 1195201


select o.tipologia, count(*) from ticket t,organization o 
where t.trashed_Date is null and o.trashed_date is null and t.org_id = o.org_id  
and t.tipologia = 2
group by o.tipologia
order by 2


select distinct o.tipologia from organization o where o.tipologia not in (select o.tipologia from ticket t,organization o 
where t.trashed_Date is null and o.trashed_date is null and t.org_id = o.org_id  
and t.tipologia = 2
group by o.tipologia)



select * from lookup_tipologia_operatore where code in (select distinct o.tipologia from organization o where o.tipologia not in (select o.tipologia from ticket t,organization o 
where t.trashed_Date is null and o.trashed_date is null and t.org_id = o.org_id  
and t.tipologia = 2
group by o.tipologia))


select * from lookup_tipolo