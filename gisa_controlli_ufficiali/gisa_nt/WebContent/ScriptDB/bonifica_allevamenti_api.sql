--Allevamenti di api in aziende zootecniche
create view allevamenti_apiari as select o.org_id, o.entered, o.name, o.account_number, o.tipologia_strutt, o.specie_allev, o.orientamento_prod, t.ticketid, imp.codice_azienda,api.id as id_apiario, api.progressivo
from organization o
left join ticket t on t.org_id = o.org_id
left join apicoltura_imprese imp on imp.codice_azienda = o.account_number
left join apicoltura_apiari api on api.id_operatore = imp.id and api.progressivo::text = substring(o.name from POSITION('APIARIO N. ' in o.name)  + 11 for 1)
where o.tipologia  = 2 and o.trashed_date is null and o.specie_allev ilike '%api%'
order by o.org_id

--Cancellazione allevamenti di api senza cu
update organization set note_hd = concat(note_hd, '. Cancellato il ' || now() || ' perchè allevamento di api importato da BDN. Questi allevamenti devono essere inseriti in apicoltura.'), trashed_date = now() where org_id in (select org_id from allevamenti_apiari where org_id not in (select org_id from allevamenti_apiari where ticketid is not null));

--Spostamento cu e sottoattività da aziende zootecniche ad apicoltura
--Eseguire tutti gli update restituiti dalla seguente query
select 'update ticket set note_internal_use_only = ''Spostato riferimento da org_id ' || org_id || ' a id_apiario ' ||  id_apiario|| ' poichè l''''org_id a cui faceva riferimento era un allevamento di api importato da BDN. Questi allevamenti devono essere inseriti in apicoltura.'', org_id = null, id_apiario = ' || a.id_apiario || ' where ticketid = ' || a.ticketid || ' or id_controllo_ufficiale = ''' || a.ticketid || ''';', * from allevamenti_apiari a where ticketid is not null

--Cancellazione allevamenti di api con cu
update organization set note_hd = concat(note_hd, '. Cancellato il ' || now() || ' perchè allevamento di api importato da BDN. Questi allevamenti devono essere inseriti in apicoltura.'), trashed_date = now() where org_id in (select org_id from allevamenti_apiari);

--Query che ritorna gli allevamenti cancellati
select name, partita_iva,entered from organization where note_hd ilike '%perchè allevamento di api importato da BDN.%'

--Query che ritorna i controlli spostati in apicoltura   
select t.ticketid, provvedimenti_prescrittivi, tipo.pianomonitoraggio, imp.ragione_sociale,imp.codice_azienda, a.progressivo from ticket t, tipocontrolloufficialeimprese tipo, apicoltura_imprese imp, apicoltura_apiari a where imp.id = a.id_operatore and tipo.idcontrollo = t.ticketid and a.id = t.id_apiario and t.note_internal_use_only ilike '%Spostato riferimento da org_id%'