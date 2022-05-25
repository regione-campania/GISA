update la_imprese_linee_attivita set trashed_date = now(), note_ht = '[07/09/18]Istanza cancellata perche'' non appartenente ad 852' where id in (
select id from (
select o.org_id, o.tipologia, t.description, l.id
from la_imprese_linee_attivita l
left join organization o on o.org_id = l.org_id
left join lookup_tipologia_operatore t on t.code = o.tipologia
where o.trashed_date is null and l.trashed_date is null 
and o.tipologia not in (1,0)
order by o.tipologia 
) aa )

delete from ricerche_anagrafiche_old_materializzata;
insert into ricerche_anagrafiche_old_materializzata  (select * from ricerca_anagrafiche) ;
