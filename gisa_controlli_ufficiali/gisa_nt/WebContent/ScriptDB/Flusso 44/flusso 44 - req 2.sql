update scheda_operatore_metadati set enabled = false where tipo_operatore = 20;insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','P.IVA','','partita_iva','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid#','B1','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','DENOMINAZIONE','','ragione_sociale','apicoltura_apiari_denormalizzati_view ','id_apicoltura_apiari= #stabid#','a','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','STATO APICOLTURA','','stato_impresa','apicoltura_apiari_denormalizzati_view ','id_apicoltura_apiari= #stabid#','aa','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','Partita Iva','','partita_iva','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# ','aaA','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','ANAGRAFICA ATTIVITA APICOLTURA','capitolo','','','','-1','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','PROPRIETARIO (COGNOME E NOME)','','cognome_rapp_sede_legale || '' '' || nome_rapp_sede_legale','apicoltura_apiari_denormalizzati_view ','id_apicoltura_apiari= #stabid#','AB','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','CODICE FISCALE PROPRIETARIO','','cf_rapp_sede_legale as codice_fiscale_proprietario','apicoltura_apiari_denormalizzati_view ','id_apicoltura_apiari= #stabid#','ac','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','DATA REGISTRAZIONE BDN','','coalesce(to_char(data_sincronizzazione,''dd/mm/yyyy''),''DATA NON DISPONIBILE'') as data_sincronizzazione','apicoltura_apiari','id=#stabid#','AD','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','CODICE AZIENDA','','codice_azienda','apicoltura_apiari_denormalizzati_view ','id_apicoltura_apiari= #stabid#','b0','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','DATA INIZIO ATTIVITA','','data_inizio_attivita','apicoltura_apiari_denormalizzati_view ','id_apicoltura_apiari= #stabid#','ba','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','TIPO ATTIVITA','','tipo_attivita','apicoltura_apiari_denormalizzati_view ','id_apicoltura_apiari= #stabid#','bb','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','INDIRIZZO SEDE LEGALE','','case when upper(indirizzo_sede_legale) is not null then upper(indirizzo_sede_legale) else '' '' end || '',  ''|| case when cap_sede_legale is not null then cap_sede_legale else '' '' end || '', 
''|| case when upper(comune_sede_legale) is not null then upper(comune_sede_legale) else '' '' end || '',
 ''|| case when prov_sede_legale is not null then (select upper(description) from lookup_province where code::integer = prov_sede_legale::integer) else '' '' end','apicoltura_apiari_denormalizzati_view ','id_apicoltura_apiari= #stabid#','bc','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','DOMICILIO DIGITALE (PEC)','','domicilio_digitale','apicoltura_apiari_denormalizzati_view ','id_apicoltura_apiari= #stabid#','c','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','TELEFONO FISSO','','telefono_fisso','apicoltura_apiari_denormalizzati_view ','id_apicoltura_apiari= #stabid#','ca','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','ANAGRAFICA','capitolo','','','','D','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','DETENTORE APIARIO','','cognome_detentore || '' '' || nome_detentore','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# and flag_laboratorio != true','E','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','CODICE FISCALE DETENTORE APIARIO','','cf_detentore','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# and flag_laboratorio != true','EA','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','CLASSIFICAZIONE','','classificazione','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# and flag_laboratorio != true','EB','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','SOTTOSPECIE','','sottospecie','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# and flag_laboratorio != true','EC','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','MODALITA'' ','','modalita','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# and flag_laboratorio != true','F','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','NUMERO ALVEARI','','num_alveari','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# and flag_laboratorio != true','FA','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','Sciami/Nuclei','','num_sciami','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# and flag_laboratorio != true','FB','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','NUMERO PACCHI API','','num_pacchi','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# and flag_laboratorio != true','FC','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','NUMERO API REGINE','','num_regine','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# and flag_laboratorio != true','G','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','DATA CENSIMENTO','',' 	coalesce(to_char(data_assegnazione_censimento,''dd/mm/yyyy''),''DATA NON DISPONIBILE'') as data_assegnazione_censimento','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# ','GA','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','INFORMAZIONI INSERIMENTO','',' concat(''INSERITO DA '',(concat_ws('' '',concat_ws('' '', c.namelast, concat(c.namefirst,'' IN DATA'')), to_char(a.entered,''dd-mm-yyyy'') ) ))  
','apicoltura_apiari a
left join access_ext_ acc on acc.user_id = a.entered_by left join contact_ext_ c on c.contact_id = acc.contact_id','a.id= #stabid# ','gb','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','ELENCO DIFFIDE','capitolo','','','','h','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','TELEFONO CELLULARE','','telefono_cellulare','apicoltura_apiari_denormalizzati_view ','id_apicoltura_apiari= #stabid#','cb','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','FAX','','fax','apicoltura_apiari_denormalizzati_view ','id_apicoltura_apiari= #stabid#','cc','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','ASL DI COMPETENZA','ASL','asl_apiario','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid#','DA','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','PROGRESSIVO','','progressivo','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# and flag_laboratorio != true','DAB0','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','STATO APIARIO','','stato_stab','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# and flag_laboratorio != true','DAB00','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','DATA APERTURA APIARIO','','data_apertura_stab','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# and flag_laboratorio != true','DAB1','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','DATA CHIUSURA APIARIO','','data_chiusura_stab','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# and flag_laboratorio != true','DAB2','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','UBICAZIONE ','','case when flag_laboratorio=true then ''LABORATORIO DI SMIELATURA : '' ELSE '''' end ||
case when upper(indirizzo_stab) is not null then upper(indirizzo_stab) else '' '' end || '', ''|| case when cap_stab is not null then cap_stab else '' '' end || '', ''|| case when upper(comune_stab) is not null then upper(comune_stab) else '' '' end || '', 
''||case when prov_stab is not null then (select upper(description) from lookup_province where code::integer = prov_stab::integer) else '' '' end || '', LATITUDINE :''||latitudine || '' LONGITUDINE : ''||longitudine','apicoltura_apiari_denormalizzati_view','id_apicoltura_apiari= #stabid# ','GA','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('20','DIFFIDATO PER ','',' 	lookup_norme.description || '', ''|| CASE WHEN diffida.stato_diffida = ''0'' then ''[DIFFIDA ATTIVA PER I PROSSIMI 5 ANNI]'' WHEN diffida.stato_diffida = ''1'' then '' [DIFFIDA NON PIU'''' ATTIVA IN QUANTO L''''OSA E'''' STATO SANZIONATO PER QUESTA NORMA]'' end ||'', ''|| ''data diffida: ''|| to_char(t.assigned_date,''dd/mm/yyyy'') as assigned_date','ticket t JOIN ticket nonconf ON nonconf.ticketid = t.id_nonconformita AND nonconf.tipologia = 8 JOIN ticket cu ON cu.id_controllo_ufficiale::text = nonconf.id_controllo_ufficiale::text AND cu.tipologia = 3 left join norme_violate_sanzioni diffida on diffida.idticket=t.ticketid left join lookup_norme on lookup_norme.code=diffida.id_norma','t.ticketid > 0 AND t.tipologia = 11 and current_timestamp< (cu.assigned_date + interval ''5 years'') and t.trashed_date is null and cu.trashed_date is null and nonconf.trashed_date is null and t.id_apiario = #stabid# order by assigned_date DESC ','ha','');