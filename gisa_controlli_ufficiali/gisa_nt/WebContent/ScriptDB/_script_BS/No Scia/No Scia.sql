-- Chi: Bartolo Sansone
-- Quando: 11/12/2018
-- Cosa: Gestione NO SCIA

alter table master_list_flag_linee_attivita add column scia boolean default false;

update master_list_flag_linee_attivita set scia = true where codice_univoco in (select distinct codice from ml8_linee_attivita_nuove_materializzata where id_lookup_configurazione_validazione <> 5)
