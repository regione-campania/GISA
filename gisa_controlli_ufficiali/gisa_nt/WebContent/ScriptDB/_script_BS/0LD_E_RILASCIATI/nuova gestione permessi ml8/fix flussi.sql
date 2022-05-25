-- riproduzione animale (EST)

update master_list_aggregazione set id_flusso_originale = 6 where codice_attivita = 'EST';
update ml8_master_list set id_flusso_origine = 6 where codice = 'EST';
update ml8_master_list set id_flusso_origine = 6 where id_padre in (select id from  ml8_master_list where codice =  'EST');
update ml8_master_list set id_flusso_origine = 6 where id in (select id_padre from  ml8_master_list where codice =  'EST');
update ml8_linee_attivita_nuove_materializzata set id_lookup_configurazione_validazione = 6 where codice_aggregazione = 'EST';
update ml8_linee_attivita_nuove_materializzata set id_lookup_configurazione_validazione = 6 where id_nuova_linea_attivita in (select id_macroarea from ml8_linee_attivita_nuove_materializzata where codice_aggregazione = 'EST');

-- mangimistica (OSMM)

update master_list_aggregazione set id_flusso_originale = 6 where codice_attivita = 'OSMM' and id_flusso_originale = 4;
update ml8_master_list set id_flusso_origine = 6 where codice = 'OSMM';
update ml8_master_list set id_flusso_origine = 6 where id_padre in (select id from  ml8_master_list where codice =  'OSMM');
update ml8_master_list set id_flusso_origine = 6 where id in (select id_padre from  ml8_master_list where codice =  'OSMM');
update ml8_linee_attivita_nuove_materializzata set id_lookup_configurazione_validazione = 6 where codice_aggregazione = 'OSMM';
update ml8_linee_attivita_nuove_materializzata set id_lookup_configurazione_validazione = 6 where id_nuova_linea_attivita in (select id_macroarea from ml8_linee_attivita_nuove_materializzata where codice_aggregazione = 'OSMM');


-- latte crudo e derivati (sintesis)

update master_list_aggregazione set id_flusso_originale = 1 where  codice_attivita in ('MS.B90');

-- fix su tipo linea 3 (riconoscibili) -> 1 (fissa)

update ml8_linee_attivita_nuove_materializzata set id_lookup_tipo_attivita = 1 where id_lookup_tipo_attivita = 3;
update master_list_linea_attivita set id_lookup_tipo_attivita = 1 where id_lookup_tipo_attivita = 3;


