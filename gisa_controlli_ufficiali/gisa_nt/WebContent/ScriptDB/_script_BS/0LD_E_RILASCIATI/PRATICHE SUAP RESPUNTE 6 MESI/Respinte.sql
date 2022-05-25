select o.validato, s.stato_validazione, o.entered, o.ragione_sociale, o.id, s.id, 'update suap_ric_scia_operatore set validato = true, note_internal_use_only_hd = ''HD 16/03 PRATICA RESPINTA AUTOMATICAMENTE PERCHE PIU VECCHIA DI 6 MESI'' where id = ' || o.id ||';', 'update suap_ric_scia_stabilimento set stato_validazione = 2, notes_hd = ''HD 16/03 PRATICA RESPINTA AUTOMATICAMENTE PERCHE PIU VECCHIA DI 6 MESI'' where id = ' || s.id || ';'
 from suap_ric_scia_operatore o
left join suap_ric_scia_stabilimento s
on s.id_operatore = o.id
 where o.validato is not true 
and o.trashed_date is null
and o.entered <  now() - interval '6 month'
order by o.entered desc


