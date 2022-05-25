create view  verifica_cu_att_ao9bc_flusso214 as 
select t.ticketid as idcu, t.assigned_Date as data_inizio_controllo, lti.description, lti.alias,  
coalesce(rorg.riferimento_id, ropu.riferimento_id) as riferimento_id, coalesce(rorg.riferimento_id_nome_tab, ropu.riferimento_id_nome_tab) as riferimento_nome_tab,
coalesce(rorg.norma, ropu.norma) as norma,
coalesce(rorg.macroarea, ropu.macroarea) as macroarea,
coalesce(rorg.aggregazione, ropu.aggregazione) as aggregazione,
coalesce(rorg.attivita, ropu.attivita) as attivita
from tipocontrolloufficialeimprese tcu
join ticket t on tcu.idcontrollo= t.ticketid and t.tipologia=3 and t.trashed_date is null and tcu.enabled
join lookup_tipo_ispezione lti on lti.code = tcu.tipoispezione and lti.enabled
left join ricerche_anagrafiche_old_materializzata rorg on rorg.riferimento_id = t.org_id and rorg.riferimento_id_nome_tab='organization'
left join ricerche_anagrafiche_old_materializzata ropu on ropu.riferimento_id = t.id_stabilimento and ropu.riferimento_id_nome_tab='opu_stabilimento'
where tcu.tipoispezione > 0 and upper(lti.alias) in ('ATT AO9_B','ATT AO9_C')


