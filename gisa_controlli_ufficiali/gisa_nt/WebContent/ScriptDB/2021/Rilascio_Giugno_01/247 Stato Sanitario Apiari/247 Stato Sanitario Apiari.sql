-- Chi: Bartolo Sansone
-- Cosa: Flusso 247
-- Quando: 21/05/21

select * from lookup_eventi_motivi_cu;

insert into lookup_eventi_motivi_cu(description, codice_evento) values('Piano di monitoraggio Stato Sanitario Apiari', 'isStatoSanitarioApiari');

insert into rel_motivi_eventi_cu (cod_raggrup_ind, id_evento_cu, entered, enteredby)
select distinct cod_raggruppamento, (select code from lookup_eventi_motivi_cu where codice_evento = 'isStatoSanitarioApiari'), now(), 5885 from dpat_indicatore_new where alias_indicatore in ('B65_A') and anno = 2021 and data_scadenza is null;

select d.alias_indicatore, r.*
from rel_motivi_eventi_cu r
join dpat_indicatore_new d on d.cod_raggruppamento = r.cod_raggrup_ind
where id_evento_cu in (select code from lookup_eventi_motivi_cu where codice_evento = 'isStatoSanitarioApiari')
order by d.alias_indicatore asc