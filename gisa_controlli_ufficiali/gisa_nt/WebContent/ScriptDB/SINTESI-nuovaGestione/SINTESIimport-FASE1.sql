--già lanciati in esercizio
update suap_master_list_6_aggregazione set id_flusso_originale  = 1 where  aggregazione ilike '%XIII%' 

--query di aggiornamento step 1 rev1
select 'update sintesis_stabilimenti_import set riferimento_org_id = '||o.org_id||' where id='||a.id_import||';',a.id_import, o.name, o.numaut as ap_org, a.num as sin_org, o.org_id from (
select 
regexp_replace(regexp_replace(upper(regexp_replace(approval_number, '[^a-zA-Z0-9]', '', 'g')),'ABP',''),'CEIT','' )
 as num,   
--btrim(regexp_replace(approval_number,'ABP','')) as num, 
s.id as id_import 
from sintesis_stabilimenti_import s
where riferimento_org_id <0 and 
riferimento_id_stabilimento is null
) a join organization o on regexp_replace(regexp_replace(upper(regexp_replace(o.numaut, '[^a-zA-Z0-9]', '', 'g')),'ABP',''),'CEIT','' ) = a.num and o.trashed_date is null and o.tipologia in (3,97)

--query di aggiornamento step 2 rev1
select o.num_riconoscimento, o.ragione_sociale,a.num, 'update sintesis_stabilimenti_import set riferimento_id_stabilimento = '||o.riferimento_id||' where id='||a.id_import||';'from (
select regexp_replace(regexp_replace(upper(regexp_replace(approval_number, '[^a-zA-Z0-9]', '', 'g')),'ABP',''),'CEIT','' )
 as num, s.denominazione_sede_operativa, s.id as id_import
from sintesis_stabilimenti_import s
where riferimento_org_id <0 and 
riferimento_id_stabilimento is null
) a join ricerche_anagrafiche_old_materializzata o on regexp_replace(regexp_replace(upper(regexp_replace(num_riconoscimento, '[^a-zA-Z0-9]', '', 'g')),'ABP',''),'CEIT','' ) = a.num  
and id_norma in (5,6) and o.num_riconoscimento <> '' and o.riferimento_id_nome <> 'orgId'

