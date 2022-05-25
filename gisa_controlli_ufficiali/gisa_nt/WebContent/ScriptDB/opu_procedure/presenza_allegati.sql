
-- Recupero id linea produttiva
select * from master_list_suap where descrizione ilike '%commercio ambulante%'
--id: 782

-- Controllo allegati
select * from master_list_suap_allegati_linee_attivita  where id_nuova_linea_attivita =782

