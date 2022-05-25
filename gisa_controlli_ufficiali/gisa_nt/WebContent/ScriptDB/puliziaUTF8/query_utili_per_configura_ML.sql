select * from lookup_tipologia_scia

insert into lookup_configurazione_validazione_master_list(code, description,short_description, enabled) values(5,'VALIDAZIONE PER SOA REGISTRATI','"GENERAZIONE CODICE SINTESI',TRUE);
insert into lookup_configurazione_validazione_master_list(code, description,short_description, enabled) values(4,'VALIDAZIONE PER REGISTRATI','',TRUE);

select * from master_list_suap  where id_padre  in (833)

select * from lookup_configurazione_validazione_master_list  

4;"VALIDAZIONE PER REGISTRATI"
1;"VALIDAZIONE PER RICONOSCIMENTO"
3;"VALIDAZIONE PER AZIENDE ZOOOTECNICHE"
2;"VALIDAZIONE PER APICOLTURA"
5;"VALIDAZIONE PER SOA REGISTRATI"

--meccanismo di configurazione sui soa registrati
select * from opu_linee_attivita_nuove  where path_descrizione ilike '%SOA E PRODOTTI DERIVATI%' and livello=4

update master_list_suap set id_lookup_configurazione_validazione  = 5 where id in ()