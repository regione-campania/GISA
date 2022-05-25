
--delete from scheda_operatore_metadati where tipo_operatore = 51

--delete from lookup_tipo_scheda_operatore where code = 51

--indice scheda
INSERT INTO lookup_tipo_scheda_operatore VALUES (51, 'Anagrafica Stabilimenti - Stampa scheda Completa', false, 0, true, 'Scheda Completa', false, 'SchedaAnagraficaCompleta');

--inserimento metadati
INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'NUMERO REGISTRAZIONE GISA', true, '', 'distinct numero_registrazione ', 'opu_operatori_denormalizzati_view ', 'id_stabilimento =#stabid#', 'ee', 0, 'screen');
INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'CARATTERE', true, '', 'distinct(stab_descrizione_carattere)', 'opu_operatori_denormalizzati_view ', 'id_stabilimento =#stabid#', 'ggg', 0, '');
INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'DESCRIZIONE STORICA DELLE LINEE DI ATTIVITA'' ( MASTER LIST , ATECO, ECC.)', true, '', 'li.path_descrizione', 'opu_relazione_stabilimento_linee_produttive rel
 join opu_linee_attivita_nuove_materializzata li on rel.id_linea_produttiva_old = li.id_nuova_linea_attivita
 ', ' id_stabilimento = #stabid# and rel.enabled', 'M', 0, '');

INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'INSERITO DA', true, '', 'distinct(stab_inserito_da)', 'opu_operatori_denormalizzati_view', 'id_stabilimento =#stabid#', 'Z', 0, '');
INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'DOMICILIO DIGITALE', true, '', 'distinct 
(case when domicilio_digitale is not null then domicilio_digitale else '''' end )', 'opu_operatori_denormalizzati_view', 'id_stabilimento =#stabid#', 'cccc', 0, '');
INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'DATA CESSAZIONE', true, '', 'distinct( case when id_stato <> 4 then '''' 
else to_char(data_fine, ''dd/mm/yyyy'')  end )', 'opu_operatori_denormalizzati_view', 'id_stabilimento =#stabid#', 'GG', 0, '');


INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'DATI STABILIMENTO', true, 'capitolo', '', '', '', 'dd', 0, '');

INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'CUN SINVSA', true, '', 'distinct cun', 'sinvsa_osm_inviati s join ricerche_anagrafiche_old_materializzata r on s.numreg = r.n_reg', 'r.riferimento_id=#stabid# ', 'ZA', 0, '');
INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'DATA INVIO SINVSA', true, '', 'distinct(to_char(s.entered,''dd-mm-yyyy''))', 'sinvsa_osm_inviati s join ricerche_anagrafiche_old_materializzata r on s.numreg = r.n_reg', 'r.riferimento_id = #stabid#', 'zb', 0, '');


INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'PARTITA IVA', true, '', 'distinct partita_iva ', 'opu_operatori_denormalizzati_view ', 'id_stabilimento =#stabid#', 'bb', 0, '');


INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'CODICE FISCALE', true, '', 'distinct codice_fiscale_impresa ', 'opu_operatori_denormalizzati_view ', 'id_stabilimento =#stabid#', 'bbb', 0, '');
INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'GIA'' CODIFICATO COME:', true, '', 'distinct (case when stab_codice_ufficiale_esistente is not null then '' <span style="text-transform:none !important">'' ||  stab_codice_ufficiale_esistente || ''</span>'' else '''' end )', 'opu_operatori_denormalizzati_view ', 'id_stabilimento =#stabid#', 'f', 0, '');



INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'TIPO MASTER LIST', true, '', 'distinct l.description', 'opu_operatori_denormalizzati_view v left join lookup_tipologia_scia l on v.id_tipo_linea_produttiva = l.code', 'v.id_stabilimento =#stabid#', 'LL0', 0, '');

INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'NOTE', true, '', 'distinct(note_operatore)', 'opu_operatori_denormalizzati_view', 'id_stabilimento =#stabid#', 'd', 0, 'screen');


INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'STATO', true, '', 'distinct(stato)', 'opu_operatori_denormalizzati_view ', 'id_stabilimento =#stabid#', 'g', 0, '');
INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'LISTA LINEE PRODUTTIVE', true, 'capitolo', '', '', '', 'lll', 0, '');

INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'TIPO SOC./COOP.', true, '', 'distinct(case when coalesce(trim(tipo_societa),'''') not ilike coalesce(trim(tipo_impresa),'''') then
				trim(tipo_societa)
			end) tipo_impresa', 'opu_operatori_denormalizzati_view', 'id_stabilimento =#stabid#', 'aaa', 0, '');

INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'NOME/DITTA/RAGIONE SOCIALE/DENOMINAZIONE SOCIALE', true, '', 'distinct ragione_sociale ', 'opu_operatori_denormalizzati_view ', 'id_stabilimento =#stabid#', 'b', 0, '');

INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'TITOLARE/RAPPRESENTANTE LEGALE', true, '', 'distinct concat_ws('' '',nome_rapp_sede_legale, cognome_rapp_sede_legale, cf_rapp_sede_legale, ''<br/>'', 
''NAZIONE: '', nazione_residenza,''Residente in: '', coalesce (indirizzo_rapp_sede_legale, comune_residenza))', 'opu_operatori_denormalizzati_view ', 'id_stabilimento =#stabid#', 'c', 0, '');


INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'DATA FINE ATTIVITA''', true, '', 'distinct(case when stab_id_carattere = 2 then to_char(data_fine_attivita, ''dd/mm/yyyy'')  else '''' end)', 'opu_operatori_denormalizzati_view', 'id_stabilimento =#stabid#', 'hhh', 0, '');


INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'PROSSIMO CONTROLLO CON LA TECNICA DELLA SORVEGLIANZA', true, '', 'case when data_prossimo_controllo is null then '''' else to_char(data_prossimo_controllo, ''dd/mm/yyyy'') end as prossimo_controllo ', 'opu_stabilimento', 'opu_stabilimento.id=#stabid#', 'i', 0, '');
INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'INDIRIZZO SEDE LEGALE', false, '', 'distinct(
case when impresa_id_tipo_impresa = 1 then '''' 
else 
case when indirizzo_sede_legale is not null then indirizzo_sede_legale else '''' end  || '', '' || 
case when cap_sede_legale is not null then cap_sede_legale else '''' end  || '' '' || case when comune_sede_legale is not null then 
comune_sede_legale else '''' end  || '', '' || case when prov_sede_legale is not null then prov_sede_legale  else '''' end end
)', 'opu_operatori_denormalizzati_view ', 'id_stabilimento =#stabid#', 'cc', 0, '');


INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'LIVELLI AGGIUNTIVI', true, '', '''<b>'' || linea.path_descrizione || ''</b><br/>'' || concat_ws (''<br/>'', l3.valore, l2.valore, l.valore)
', 'master_list_configuratore_livelli_aggiuntivi_values v
join opu_relazione_stabilimento_linee_produttive rel on rel.id = v.id_istanza and rel.id_stabilimento = #stabid#
join ml8_linee_attivita_nuove_materializzata linea on linea.id_nuova_linea_attivita = rel.id_linea_produttiva
left join master_list_configuratore_livelli_aggiuntivi l on l.id = v.id_configuratore_livelli_aggiuntivi
left join master_list_configuratore_livelli_aggiuntivi l2 on l2.id = l.id_padre
left join master_list_configuratore_livelli_aggiuntivi l3 on l3.id = l2.id_padre', 'v.checked and rel.enabled', 'N', 0, '');
INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'DATI IMPRESA', true, 'capitolo', '', '', '', 'a', 0, '');

INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'TIPO IMPRESA', true, '', 'distinct(tipo_impresa)', 'opu_operatori_denormalizzati_view', 'id_stabilimento =#stabid#', 'aa', 0, '');
INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'INDIRIZZO SEDE LEGALE', true, '', 'distinct(
case when impresa_id_tipo_impresa = 1 then '''' 
else 
concat_ws('''',nazione_sede_legale, '' - INDIRIZZO: '', indirizzo_sede_legale, cap_sede_legale, comune_sede_legale, ''  '', prov_sede_legale) end )', 'opu_operatori_denormalizzati_view ', 'id_stabilimento =#stabid#', 'ccc', 0, '');


INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'ASL', true, 'asl', 'distinct stab_asl', 'opu_operatori_denormalizzati_view', 'id_stabilimento =#stabid#', 'ddd', 0, '');
INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'DATA INIZIO ATTIVITA''', true, '', 'distinct(case when data_inizio_attivita is not null then to_char(data_inizio_attivita, ''dd/mm/yyyy'')  else '''' end)', 'opu_operatori_denormalizzati_view', 'id_stabilimento =#stabid#', 'hh', 0, '');

INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'NOTE STABILIMENTO', true, '', 'distinct(note_stabilimento)', 'opu_operatori_denormalizzati_view', 'id_stabilimento =#stabid#', 'ii', 0, 'screen');
INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'DATA INSERIMENTO', true, '', 'distinct to_char(stab_entered, ''dd/mm/yyyy'')', 'opu_operatori_denormalizzati_view', 'id_stabilimento =#stabid# ', 'l', 0, '');



INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'INFORMAZIONI AGGIUNTIVE', true, 'json', 
'o.descrizione as linea, 
 case when label_campo is not null then label_campo else '''' end as info, 
 case when valore_campo is not null then 
	case WHEN tipo_campo ilike ''%checkbox%'' and valore_campo::text ilike ''%true%'' then ''SI'' 
	     WHEN tipo_campo ilike ''%checkbox%'' and (valore_campo::text not ilike ''%true%'' or valore_campo is null) then ''NO''


	     WHEN tipo_campo ilike ''%select%'' and label_campo ilike ''%Specie animali%'' then get_description_specie_animali_campi_estesi(valore_campo)
	     
	     WHEN ( ( label_campo is not null and ( label_campo ilike ''%sesso%'' or label_campo ilike ''%sex%'' )) and valore_campo::text = ''0'' ) THEN 
		''M'' 
	     WHEN ( ( label_campo is not null and (label_campo ilike ''%sesso%'' or label_campo ilike ''%sex%'' )) and valore_campo::text = ''1'' ) THEN 
		''F'' 
		ELSE replace(COALESCE(tipomobili.description, v.valore_campo, '''') , '','', '''') END 
	else
	     case WHEN tipo_campo ilike ''%checkbox%'' and (valore_campo::text not ilike ''true'' or valore_campo is null) then ''NO''
	     else '''' end
	end as valore', 

' linee_mobili_html_fields f join linee_mobili_fields_value v on f.id = v.id_linee_mobili_html_fields 
	join ml8_linee_attivita_nuove o on o.id_nuova_linea_attivita = f.id_linea 
	left join lookup_tipo_mobili tipomobili on tipomobili.code::text = v.valore_campo and f.tabella_lookup = ''lookup_tipo_mobili'' ', 

'( v.id_rel_stab_linea in (select rs.id from opu_relazione_stabilimento_linee_produttive rs join opu_stabilimento s on rs.id_stabilimento = s.id where s.id = #stabid# and enabled) or 
v.id_opu_rel_stab_linea in (select rs.id from opu_relazione_stabilimento_linee_produttive rs join opu_stabilimento s on rs.id_stabilimento = s.id where s.id = #stabid# and enabled) ) 
and v.enabled and f.enabled group by o.descrizione, f.id_linea, f.label_campo, valore, v.id , f.id order by f.id_linea, v.indice, f.id'
, 'zc', 0, 'print');

INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'NUMERO REGISTRAZIONE GISA ', true, 'barcode', 'distinct numero_registrazione ', 'opu_operatori_denormalizzati_view ', 'id_stabilimento =#stabid#', 'eee', 0, 'print');
INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'INDIRIZZO STABILIMENTO', true, '', 'distinct(
case when stab_id_attivita=2 then '''' else
 case when indirizzo_stab is not null then indirizzo_stab || '','' ||  case when civico_sede_stab is not null then civico_sede_stab else '''' end else '''' end || '', '' || 
case when cap_stab is not null then cap_stab else '''' end || '' '' || case when comune_stab  is not null then comune_stab else '''' end || '', '' ||
case when prov_stab is not null then prov_stab else '''' end ||'', Latitudine: '' || case when lat_stab is not null then lat_stab else 0  end
 ||'', Longitudine: '' ||case when  long_stab is not null then long_stab else  0 end end )', 'opu_operatori_denormalizzati_view ', 'id_stabilimento =#stabid#', 'ff', 0, '');


INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'CATEGORIA DI RISCHIO', true, '', ' distinct case when  categoria_rischio is not null then categoria_rischio 
end as categoria_rischio  ', 'opu_operatori_denormalizzati_view ', 'id_stabilimento =#stabid#', 'fff', 0, '');


INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'ATTIVITA', true, '', 'norme.description  || case when linea_codice_nazionale <> '''' then ''<br><b>Approval Number / CUN:</b><span style="text-transform:none !important">'' || linea_codice_nazionale || ''</span><br/>'' else '''' end 
,
string_agg(
case when linea_numero_registrazione <> '''' then ''<b>Num. Reg.:</b> '' || linea_numero_registrazione else ''NUMERO MANCANTE'' end || ''<br/>'' 
|| case when linea_codice_ufficiale_esistente <> '''' then ''<i>Gia  codificato come:</i><span style="text-transform:none !important"> '' || linea_codice_ufficiale_esistente || ''</span><br/>'' else '''' end 
|| case when path_attivita_completo <> '''' then replace(path_attivita_completo, ''->'', ''<br/>->'')  else '''' end || ''<br/>''
||
concat_ws('''',
case 
	when (pregresso_o_import =true and data_inizio is not null) or m.no_scia then concat_ws('': '',''<b>Data Inizio attivita''''</b>'',to_char(data_inizio, ''dd/mm/yyyy''))
	when (pregresso_o_import = false or pregresso_o_import is null) then concat_ws('' '', ''<b>Data validazione</b>:'', 
	coalesce(to_char(data_inizio,''dd/mm/yyyy''),to_char(linea_modified, ''dd/mm/yyyy'')), 
	E''(NOTA BENE: La data di inizio attività corrisponde alla data di presentazione SCIA, riportata sulla ricevuta rilasciata dal SUAP)\n'') 
else '''' end,
case when data_fine is not null then concat_ws('''',concat(''<b>Data fine</b>: '',to_char(data_fine, ''dd/mm/yyyy'')),concat(''<br/><b>Stato</b>: '',linea_stato_text))
else concat(''<br/><b>Stato</b>: '',linea_stato_text) end)
 ,''<br><hr>'')', 'opu_operatori_denormalizzati_view o
left join master_list_flag_linee_attivita m on m.codice_univoco= o.codice_attivita
left join opu_lookup_norme_master_list norme on norme.code = id_norma', 'id_stabilimento =#stabid#  
 group by   
norme.description  || case when linea_codice_nazionale <> '''' then ''<br><b>Approval Number / CUN:</b><span style="text-transform:none !important">'' || linea_codice_nazionale || ''</span><br/>'' else '''' end ', 'm', 0, '');
INSERT INTO scheda_operatore_metadati (tipo_operatore, sql_val, label, enabled, attributo, sql_campo, sql_origine, sql_condizione, ordine, ordine_int, destinazione)
VALUES (51, NULL, 'TIPO ATTIVITA''', true, '', 'distinct(stab_descrizione_attivita)', 'opu_operatori_denormalizzati_view ', 'id_stabilimento =#stabid#', 'gg1', 0, '');