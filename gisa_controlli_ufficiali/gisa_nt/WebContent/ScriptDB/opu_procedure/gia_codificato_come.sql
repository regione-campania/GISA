-- AGGIORNAMENTO "GIA' CODIFICATO COME" SU LINEE PRODUTTIVE

-- Selezione delle linee produttive associate allo stabilimento

select id_linea_attivita from opu_operatori_denormalizzati_view where id_stabilimento = [ID STABILIMENTO]

-- Aggiornamento dell'approval number sulle linee

update opu_relazione_stabilimento_linee_produttive set codice_ufficiale_esistente ='Esempio' where id = [ID LINEA];
