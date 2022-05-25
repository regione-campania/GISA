--VERIFICO LA SANZIONE PARTENDO DALL'ID CONTROLLO UFFICIALE
select pagamento,trashed_date, * from ticket where id_controllo_ufficiale = '[idcu]' and tipologia =1

-- SE LA SANZIONE NON E' STATA CANCELLATA SI PROCEDE ALL'AGGIORNAMENTO
update ticket set pagamento=[importoNEW] where ticketid=[idSanzione]
