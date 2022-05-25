select * from ricerche_anagrafiche_old_materializzata where riferimento_id =  348345;
-- stab con 4 linee
select * from linee_attivita_controlli_ufficiali  where id_controllo_ufficiale  = 1621429;
-- verifico che tipo di checklist è prevista per quella linea di attività
-- select * from rel_checklist_sorv_ml 
insert into rel_checklist_sorv_ml(id_linea, id_checklist,enabled) values(40557, 2023, true) ; -- test su linea canile con checklist associata 'IUV-CAN-ALLCAN'

select * from audit where id_controllo  = '1621429'

select categoria_rischio from parametri_categorizzazzione_osa where tipo_operatore = 1 and (932 between range_da and range_a or (932 > range_da and range_a is null))

select * from public.get_categoria_rischio_(348345,'opu_stabilimento')


