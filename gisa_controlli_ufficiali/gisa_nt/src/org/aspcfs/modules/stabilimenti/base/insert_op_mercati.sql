/*OPERATORI ITTICI NAPOLI 1*/




/*SOSTITUIRE L'ID DELL'ASL CON QUELLI DELLA NUOVAGESTIONE
 * 201;"AVELLINO"
   202;"BENEVENTO"
   203;"CASERTA"
   204;"NAPOLI 1 CENTRO"
   205;"NAPOLI 2 NORD"
   206;"NAPOLI 3 SUD"
   207;"SALERNO"
 * 
 * 
 * 
 *
 */
INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (100,'Euro Gelo Import srl',NULL,NULL,NULL,NULL,'3',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '05866600637',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'MRCFBA83L04F839Y','Fabio','Maraucci',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1983-07-04','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (101,'Euro Gelo Import srl',NULL,NULL,NULL,NULL,'29',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '05866600637',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'MRCFBA83L04F839Y','Fabio','Maraucci',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1983-07-04','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);


INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (102,'Luifo sas',NULL,NULL,NULL,NULL,'5',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '06918080638',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'RNLGPP46H30F839D','Giuseppe','rinaldi',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1946-06-30','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (103,'Sorian Pesca sas',NULL,NULL,NULL,NULL,'7',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '07845890636',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'DPRFNC45R03F839E','Francesco','Di Pierno',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1945-10-04','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (104,'Eurofish Napoli srl',NULL,NULL,NULL,NULL,'8',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '06997850638',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'VLAPTL70H10L259Q','Pietro','Avolio',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1970-06-10','Torre del Greco',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

 INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (105,'V. Pesca',NULL,NULL,NULL,NULL,'9',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '07876020632',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'VLNVCN83P26F839Y','Vincenzo','Vollaro',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1983-09-26','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (106,'Nadi Pesca',NULL,NULL,NULL,NULL,'10',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '07716050633',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'NPLNCL75T01F839W','Nicola','Napolano',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1975-12-01','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (107,'Coopesca s.a.s.',NULL,NULL,NULL,NULL,'11',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '0231610630',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'CZZGNR75S19F839S','Gennaro','Cozzolino',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1975-11-19','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (108,'Acquafish sas',NULL,NULL,NULL,NULL,'12',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '05485221211',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'CHRPTR70P29F839B','Pietro','Chiaro',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1970-09-29','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (109,'Sasa pesca sas',NULL,NULL,NULL,NULL,'13',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '0693254063',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'PPPPTR22P08H203P','Pietro','Pappagoda',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1922-09-08','Ercolano',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (110,'Manna Pesca srl',NULL,NULL,NULL,NULL,'14',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '07017240636',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'MNNFRC72R28F839S','Francesco','Manna',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1972-10-28','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (111,'Manna Pesca srl',NULL,NULL,NULL,NULL,'16',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '07017240636',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'MNNFRC72R28F839S','Francesco','Manna',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1972-10-28','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (112,'Testa Domenico',NULL,NULL,NULL,NULL,'19',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '07849210633',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'TSTDNC55E05F839D','Domenico','Testa',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1955-05-05','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (113,'Arin Pesca sas',NULL,NULL,NULL,NULL,'20',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '06920700637',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'RNASVT51L14F839P','Salvatore','Arino',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1951-07-14','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (114,'Ittica Vollaro sas',NULL,NULL,NULL,NULL,'21',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '07876010633',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'VLLVCN67A53F839R','Vincenza','Vollaro',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1965-01-13','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (115,'Tirrenia Ittica',NULL,NULL,NULL,NULL,'22',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '07324360630',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'SPSFNC77T16F839M','Francesco','Esposito',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1977-12-16','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (116,'Stella Marina sas',NULL,NULL,NULL,NULL,'23',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '06422561214',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'TCCNTN89C27F839F','Antonio','Tecchio',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1989-03-27','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (117,'Maragen sas',NULL,NULL,NULL,NULL,'24',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '06931590639',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'MRCGNR41C19F839M','Gennaro','Maraucci',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1941-03-19','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (118,'Marigliano Antonio',NULL,NULL,NULL,NULL,'25',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '00467200630',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'MRGNTN41C25F839M','Antonio','Marigliano',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1941-03-25','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (119,'Gruppo Mare sas',NULL,NULL,NULL,NULL,'26',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '04012371219',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'GRDRSR66M14F839A','Rosario','Giordano',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1966-08-14','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (120,'Pennino Giovanni srl',NULL,NULL,NULL,NULL,'30',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '00830571212',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'PNNDRA70P02F839Q','Rosario','Giordano',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1970-09-02','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (121,'Sarin Pesca sas',NULL,NULL,NULL,NULL,'31',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,6,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '0347020633',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'GRDRSR66M14F839A','Antonio','Sarnelli',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1963-06-03','Cerignola(FG)',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    /*Operatori Mercato Salerno*/
    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (122,'Stabile Aniello S.r.l.',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '04448110652',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.10',/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.20',/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'STBNLL59H06H703O','Aniello','Stabile',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1959-06-06','Salerno)',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (123,'Ittica Iacovazzo di Iacovazzo Francesco & C. s.a.s.',NULL,NULL,NULL,NULL,'2',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '02351880659',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.10',/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.20',/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'CVZFNC52T12H703M','Francesco','Iacovazzo',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1952-12-12','Salerno)',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (124,'Ittica D.&P. S.r.l.',NULL,NULL,NULL,NULL,'3',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '03712770654',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.10',/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.20',/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'DLRMCL66C71E839E','Maria Claudia','Di Lauro',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1966-03-31','Maiori',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (125,'Ditta D''Acunto Vincenzo',NULL,NULL,NULL,NULL,'4',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '04535960653',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.10',/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.20',/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'DCNVCN70L25H703F','Vincenzo','D''Acunto',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1970-07-25','Salerno',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (126,'Surgelati Autuori S.r,l.',NULL,NULL,NULL,NULL,'5',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '02147290650',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.10',/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.20',/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'TRAMRA62R08H703X','Mario','Autuori',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1962-10-08','Salerno',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (127,'Salerno Blu Mare S.r.l.',NULL,NULL,NULL,NULL,'6',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '04674390655',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.10',/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.20',/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'GRDGNN90E03H703Q','Giovanni','Giordano',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1990-05-03','Salerno',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (128,'Ditta Civale Vittorio',NULL,NULL,NULL,NULL,'7',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '03101260655',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.10',/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.20',/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'CVLVTR69L25C361K','Vittorio','Civale',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1969-07-25','Vietri S.M.',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (129,'Pesca Pronta S.p.a.',NULL,NULL,NULL,NULL,'8',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '08910621005',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.10',/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.20',/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'MRSNTN74M14L245N','Antonio','Amoruso',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1974-08-13','Torre Annunziata',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (130,'Ittica Salerno S.r.l.',NULL,NULL,NULL,NULL,'9',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,12,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '00858360654',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.10',/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '46.38.20',/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'TLGDRD58T23H703J','Eduardo','Taglialatela',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1958-12-23','Salerno',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    /*Operat mercato Pozzuoli*/
     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (131,'Ittica Ciro S.n.c. di  Restuccio Ciro & C.',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '6299390630',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Ciro','Restuccio',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1967-10-24','Pozzuoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (132,'P.L. Pesca S.r.l.',NULL,NULL,NULL,NULL,'2',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '7012870635',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Gennaro','Portanova',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1955-09-22','Pozzuoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

      INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (133,'Puteoli Pesca S.r.l.',NULL,NULL,NULL,NULL,'3',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'n.d.',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Vittorio','Lucignano',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1972-08-08','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (134,'Nino Pesca sas di D''auria Rosalia & C.',NULL,NULL,NULL,NULL,'4',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '4027451213',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Rosalia','D''auria',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1958-06-07','Pozzuoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (135,'Ittica Giosue'' srl di iencharelli Pasquale',NULL,NULL,NULL,NULL,'5',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '7446140639',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Giosue''','Iencharelli',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1970-08-18','Pozzuoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

      INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (136,'Civero Ittica sas di Civero Giosue''',NULL,NULL,NULL,NULL,'6',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '6393820631',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Giosue''','Civero',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1961-01-30','Pozzuoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (137,'Sudittica srl',NULL,NULL,NULL,NULL,'7',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'n.d.',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Antonietta','Rimoli',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1969-01-02','Pozzuoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

      INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (138,'Sud Pesca srl',NULL,NULL,NULL,NULL,'8',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '7684020634',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Giuseppe','Amoroso',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1965-04-28','Bacoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (139,'Ditta Individuale D''oriano Pasquale di D''oriano Pasquale',NULL,NULL,NULL,NULL,'9',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '5829130631',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Pasquale','D''oriano',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1964-02-29','Pozzuoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (140,'Corsar Pesca sas di corsaro Gioacchino & C.',NULL,NULL,NULL,NULL,'10',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '7203000638',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Gioacchino','Corsaro',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1972-02-28','Pozzuoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (141,'La Nuova Pesca snc di Lucignano Francesco & Salvatore',NULL,NULL,NULL,NULL,'11',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '6972070632',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Francesco','Lucignano',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1970-01-02','Pozzuoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (142,'Ditta Individuale Lombardi Angela di lombardi Angela',NULL,NULL,NULL,NULL,'12',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '7619210631',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Angela','Lombardi',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1974-09-21','Pozzuoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (143,'Davini Pesca  di Davini Angelo & C. snc',NULL,NULL,NULL,NULL,'13',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '3441350638',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Angelo','Davini',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1968-12-24','Pozzuoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (144,'Ditta Individuale Canfora Domenico',NULL,NULL,NULL,NULL,'14',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1882960632',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Domenico','Canfora',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1937-01-02','Pozzuoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (145,'Golfo di Pozzuoli',NULL,NULL,NULL,NULL,'16',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '7596110630',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Gaetano','Lanuto',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1962-03-10','Pozzuoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (146,'La Perla srl',NULL,NULL,NULL,NULL,'17',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '4305351217',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Francesco','Parisi',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1969-01-19','Pozzuoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (147,'Serapide snc di Lippa Margherita e Nisita Giuseppe',NULL,NULL,NULL,NULL,'18',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,9,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '5597850634',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,'Giuseppe','Nisita',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1964-05-20','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    /* operat mercato Mugnano*/
     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (148,'GI.RA. Mare srl',NULL,NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '04798841211',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'SZORFL63C02F799N','Raffaele','Sozio',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1963-03-02','Mugnano',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (149,'La castropesca snc',NULL,NULL,NULL,NULL,'2',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '04047701216',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'MNGCTR63R8F799U','Castrese','Manigiapili',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1963-10-08','Mugnano',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (150,'CIF Consorzio Ittico Familiare',NULL,NULL,NULL,NULL,'3',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '05833631210',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'NPLMRA83E31G309X','Mario','Napolano',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1983-05-31','Villaricca',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

      INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (151,'ATI WORKING FISH srl - Pentogel',NULL,NULL,NULL,NULL,'4',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '05166821214',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'PNTDVD70B03F839O','Raffaele','Pentoriero',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1970-02-03','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (152,'Nuova Ittica aversano',NULL,NULL,NULL,NULL,'5',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '02060980618',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'VRSMRT74M12D799R','Umberto','Aversano',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1974-08-12','Frignano',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (153,'NI.GI. & C. Frutti di Mare',NULL,NULL,NULL,NULL,'6',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '03982461216',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'NPLNCL80R03G309G','Nicola','Napolano',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1980-10-03','Villaricca',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (154,'SHA'' FISH sas',NULL,NULL,NULL,NULL,'7',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '05252471213',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'DVVDNC72E14G309F','Domenico','Di Vivo',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1972-05-14','Villaricca',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (155,'IPPOCAMPO Srl',NULL,NULL,NULL,NULL,'8',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '06494550632',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'PRRPQL84A30F839G','Pasquale','Prorogiglio',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1984-01-30','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (156,'ATI BAIA FISH sas + ACQUA AZZURRA SRL + FISH 2000SRL',NULL,NULL,NULL,NULL,'9',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '02854311210',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'BNAGNR68L20F799K','Gennaro','Baiano',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1968-07-30','Mugnano',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (157,'ITTICA CAPASSO srl',NULL,NULL,NULL,NULL,'10',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '02809651215',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'CPSFNC73R16F839V','Francesco','Capasso',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1973-10-16','Napoli',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (158,'SZIO MARE SRL',NULL,NULL,NULL,NULL,'11',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '05180341215',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'SZOLGU70E14F799A','LUIGI','SOZIO',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1970-05-14','MUGNANO',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (159,'AMOTRADE SRL',NULL,NULL,NULL,NULL,'12',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '04798781219',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'MRSSVR88A06H501Z','SAVERIO','AMORUSO',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1988-01-06','ROMA',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (160,'CAPASSO ARMANDO',NULL,NULL,NULL,NULL,'13',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '03318691213',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'CPSRND59H09F799P','ARMANDO','CAPASSO',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1959-07-09','MUGNANO',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (161,'JOLLY PESCA SRL',NULL,NULL,NULL,NULL,'14',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '03855781211',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'CRBCRI63C24F839S','CIRO','CERBONE',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1963-03-24','NAPOLI',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (162,'FIGI PESCA DI CAIANIELLO FILIPPO',NULL,NULL,NULL,NULL,'15',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '05058671214',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'CNLFPP58B24F799Z','FILIPPO','CAIANIELLO',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1958-02-24','MUGNANO',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (163,'OROPESCA SRL',NULL,NULL,NULL,NULL,'16',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '04903301218',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'SZOCMN65D05F799R','CARMINE','SOZIO',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1965-04-05','MUGNANO',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (164,'ITTICA D''ARBITRIO SAS',NULL,NULL,NULL,NULL,'17',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '06378640632',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'DRBCCMN74M02F839T','CARMINE','D''ARBITRIO',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1974-08-02','NAPOLI',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (165,'PESCA NAPOLI FISH SRL',NULL,NULL,NULL,NULL,'18',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '05546291211',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'BSLDNC75C07I293Q','DOMENICO','BASILE',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1975-03-07','SANT''ANTIMO',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (166,'MER CLAIRE SRL',NULL,NULL,NULL,NULL,'19',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '05564991213',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'CNLCMN61S16G309D','CARMINE','CAIANIELLO',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1961-11-16','VILLARICCA',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (167,'TOP SEA FISH SRL',NULL,NULL,NULL,NULL,'20',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '04232731218',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'TPONTN63S20F799J','ANTONIO','TOPO',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1963-11-20','MUGNANO',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (168,'MARE SUD SRL',NULL,NULL,NULL,NULL,'21',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '04730120633',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'MGLSTN59R05F799M','SABATINO','MIGLIACCIO',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1959-10-05','MUGNANO',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (169,'ITTICA AZZURRA SRL',NULL,NULL,NULL,NULL,'22',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '06553461218',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'MNGNLL67P06F799K','ANIELLO','MANGIAPILI',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1967-09-06','MUGNANO',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

      INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (170,'AMORUSO VITTORIO',NULL,NULL,NULL,NULL,'23',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '07130110633',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'MRSVTR54T23F799J','VITTORIO','AMORUSO',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1954-12-23','MUGNANO',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

     INSERT INTO organization(
            org_id, "name", account_number, account_group, url, revenue, 
            employees, notes, ticker_symbol, taxid, lead, sales_rep, miner_only, 
            defaultlocale, fiscalmonth, entered, enteredby, modified, modifiedby, 
            enabled, industry_temp_code, "owner", duplicate_id, custom1, 
            custom2, contract_end, alertdate, alert, custom_data, namesalutation, 
            namelast, namefirst, namemiddle, namesuffix, import_id, status_id, 
            alertdate_timezone, contract_end_timezone, trashed_date, source, 
            rating, potential, segment_id, sub_segment_id, direct_bill, account_size, 
            site_id, duns_type, duns_number, business_name_two, sic_code, 
            year_started, sic_description, stage_id, partita_iva, codice_fiscale, 
            abi, cab, cin, banca, conto_corrente, nome_correntista, cf_correntista, 
            date1, date2, tipologia, specie_allev, orientamento_prod, tipologia_strutt, 
            numero_capi, old_id, date3, date4, codice1, codice2, codice3, 
            codice4, codice5, codice6, codice7, codice8, codice9, codice10, 
            codice_cont, tipo_dest, tipo_stab, categoria, impianto, stato_lab, 
            numaut, codice_impianto, tipo_struttura, tipo_locale, data_in_carattere, 
            data_fine_carattere, cessazione, titolo_rappresentante, codice_fiscale_rappresentante, 
            nome_rappresentante, cognome_rappresentante, email_rappresentante, 
            telefono_rappresentante, prog_stab, tmp_import_id, tmp_import_impianto, 
            tmp_import_site_id, tmp_import_stato_lab, fax, codice_impresa_interno, 
            data_nascita_rappresentante, luogo_nascita_rappresentante, riti_religiosi, 
            imballata, tipo_aut, cessato, codice_impresa_generato_da, cambiato_in_osa_da, 
            inserisci_continua, tipo_locale3, tipo_locale2, follow_up, specie_allevamento, 
            acqua_classificata, voltura, tipo_soa, datapresentazione, categoria_rischio, 
            stato_impresa, data_attribuzione_codice, data_passaggio_impresa, 
            nato_come_dia, progressivo_stabilimento, fuori_regione, categoria_precedente, 
            progressivo_stabilimenti, prossimo_controllo)
    VALUES (171,'ATI LICCARDO LUISA + ONDA DEL MARE SAS',NULL,NULL,NULL,NULL,'24',NULL,NULL,NULL,NULL,0,FALSE,NULL,NULL,'2010-11-03 00:00:00',291,
    '2010-11-03 00:00:00',291,TRUE,NULL,NULL,-1,-1,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,
    TRUE,NULL,7,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '05415981215',/*p iva*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    NULL,/**/
    NULL,NULL,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

   NULL,/*cod sec*/

    NULL,NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    'LCCLSU57B47F799R','LUISA','LICCARDO',/*LEGALE RAPPRESENTANTE*/

    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,

    '1957-02-07','MUGNANO',/*DATA E LUOGO NASCITA*/

    NULL,NULL,
    NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,FALSE,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

    