alter table  apicoltura_apiari_variazioni_censimenti rename to apicoltura_consistenza;

CREATE OR REPLACE VIEW apicoltura_apiari_variazioni_censimenti AS 
 SELECT *
   FROM apicoltura_consistenza
  WHERE id_bda >0 and data_assegnazione_censimento is not null;



--Recuperare id_bda dei censimenti
  select imp.codice_azienda, api.progressivo, api.entered, cens.data_censimento, cens.data_assegnazione_censimento, cens.id_bda from  
  apicoltura_imprese imp, apicoltura_apiari api, apicoltura_apiari_variazioni_censimenti cens
  where cens.id_apicoltura_apiario = api.id and
        api.id_operatore = imp.id and
        cens.trashed_by is null and
        api.trashed_date is null and
        imp.trashed_Date is null and
        cens.id_bda is null and
        to_char(cens.entered, 'dd/MM/yyyy') <> to_char(api.entered, 'dd/MM/yyyy') 
  order by imp.codice_azienda, api.progressivo, cens.entered



--Query update
select 'update apicoltura_apiari_variazioni_censimenti set id_bda = ' || cens_bdn.apicenid || ' where id = ' || cens_gisa.id,  * from  (
  select imp.codice_azienda, api.progressivo, api.entered, cens.data_censimento, cens.data_assegnazione_censimento, cens.id_bda, cens.num_alveari, cens.num_sciami, cens.id from  
  apicoltura_imprese imp, apicoltura_apiari api, apicoltura_apiari_variazioni_censimenti cens
  where cens.id_apicoltura_apiario = api.id and
        api.id_operatore = imp.id and
        cens.trashed_by is null and
        api.trashed_date is null and
        imp.trashed_Date is null and
        cens.id_bda is null and
        to_char(cens.entered, 'dd/MM/yyyy') <> to_char(api.entered, 'dd/MM/yyyy') 
  order by imp.codice_azienda, api.progressivo, cens.entered) as cens_gisa,
  censimenti_from_bdn cens_bdn
  where cens_bdn.apiattaziendacodice = cens_gisa.codice_azienda and 
        cens_bdn.apiprogressivo = cens_gisa.progressivo::text and
        cens_bdn.dtcensimento = to_char(cens_gisa.data_assegnazione_censimento,'yyyy-MM-dd')






        
        

