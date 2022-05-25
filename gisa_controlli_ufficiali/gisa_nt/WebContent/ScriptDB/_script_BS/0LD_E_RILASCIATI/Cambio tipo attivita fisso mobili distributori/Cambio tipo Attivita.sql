select 
distinct
r.riferimento_id, r.path_attivita_completo, r.tipo_attivita_descrizione, flag.mobile, 'http://srv.gisacampania.it/gisa_nt/OpuStab.do?command=Details&idFarmacia='||r.riferimento_id||'&opId='||r.riferimento_id||'&stabId='||r.riferimento_id,
'update opu_stabilimento set tipo_attivita = 2, notes_hd = concat_ws('';'', notes_hd, ''01/08/18 Tipo attivita cambiata da fissa a mobile massivamente: stabilimento fisso con una sola linea mobile'') where id = '||r.riferimento_id||';', 'select * from refresh_anagrafica('||r.riferimento_id||', ''opu'');'
from
ricerche_anagrafiche_old_materializzata r
left join opu_relazione_stabilimento_linee_produttive rel on rel.id = r.id_linea
left join ml8_linee_attivita_nuove_materializzata ml8 on ml8.id_nuova_linea_attivita = rel.id_linea_produttiva
left join master_list_flag_linee_attivita flag on flag.codice_univoco = ml8.codice_attivita
where
r.path_attivita_completo ilike '%DISTRIBUTORI AUTOMATICI%'
and r.tipo_attivita_descrizione ilike '%FISSA%'
and flag.mobile

and riferimento_id in ( select riferimento_id from (select distinct riferimento_id, id_linea from ricerche_anagrafiche_old_materializzata) aa group by aa.riferimento_id having count(aa.riferimento_id) = 1)

order by riferimento_id asc
