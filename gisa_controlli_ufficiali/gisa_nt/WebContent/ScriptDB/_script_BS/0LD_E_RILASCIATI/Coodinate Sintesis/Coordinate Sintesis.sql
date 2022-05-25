-- CHI: Bartolo Sansone	
-- COSA: Coordinate e cap sintesis
-- QUANDO: 20/09/2018


alter table sintesis_stabilimenti_import add column latitudine double precision;
alter table sintesis_stabilimenti_import add column longitudine double precision;


--CAP
alter table sintesis_stabilimenti_import add column cap text;

-- SVUOTARE CAP NAPOLI???
 update sintesis_indirizzo set cap = null where cap ilike '%80100%' and id in(select id_indirizzo from sintesis_stabilimento);

--Schede
update scheda_operatore_metadati set enabled = false where tipo_operatore = 32;insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Rappresentante legale','',' concat_ws('' '', sogg.nome, sogg.cognome, sogg.codice_fiscale, ''<br/>'', top.description, ind.via, ind.civico, com.nome, prov.description)',' sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id join sintesis_rel_operatore_soggetto_fisico rel on rel.id_operatore = o.id and rel.enabled and rel.data_fine is null left join sintesis_soggetto_fisico sogg on sogg.id = rel.id_soggetto_fisico left join sintesis_indirizzo ind on ind.id = sogg.indirizzo_id
 left join lookup_toponimi top on top.code = ind.toponimo
  left join comuni1 com on com.id = ind.comune
  left join lookup_province prov on prov.code::text = ind.provincia','s.alt_id=#altid#','h','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','CATEGORIA DI RISCHIO','','coalesce(categoria_rischio,3)','sintesis_stabilimento s','s.alt_id=#altid# and s.id not in (select s.id from sintesis_relazione_stabilimento_linee_produttive rel
join sintesis_stabilimento s on s.id = rel.id_stabilimento
join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva
join opu_lookup_norme_master_list norme on norme.code = linee.id_norma
where norme.codice_norma ilike ''1069-09'' and s.alt_id = #altid# limit 1)','NA','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Codice Fiscale Impresa','','o.codice_fiscale_impresa','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id','s.alt_id=#altid#','e','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Domicilio digitale','','o.domicilio_digitale','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id','s.alt_id=#altid#','f','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Dati Stabilimento','capitolo','','','','i','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Denominazione','','s.denominazione','sintesis_stabilimento s','s.alt_id=#altid#','l','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','DATA PROSSIMO CONTROLLO ','','''NON PREVISTA''','sintesis_stabilimento s','s.alt_id=#altid# and s.id in (select s.id from sintesis_relazione_stabilimento_linee_produttive rel
join sintesis_stabilimento s on s.id = rel.id_stabilimento
join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva
join opu_lookup_norme_master_list norme on norme.code = linee.id_norma
where norme.codice_norma ilike ''1069-09'' and s.alt_id = #altid# limit 1)','ND','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','ASL','asl','asl.description','sintesis_stabilimento s join lookup_site_id asl on asl.code = s.id_asl','s.alt_id=#altid#','NN','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Lista linee produttive','capitolo','','','','p','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Lista prodotti','','concat_ws('' -> '', ''<b>''||tit_scheda||''<b/>'', lista_prodotti) from(
select distinct s.tit_scheda, string_agg(s.nome || case when p.valore_prodotto <>'''' then ''(''||p.valore_prodotto||'')'' else '''' end, ''; '') as lista_prodotti','master_list_sk_elenco s
join sintesis_prodotti p on s.id = p.id_prodotto
join sintesis_relazione_stabilimento_linee_produttive rel on rel.id = p.id_linea
join sintesis_stabilimento st on st.id = rel.id_stabilimento','st.alt_id = #altid# and p.unchecked_date is null
group by tit_scheda
) aa
','R','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Tipo Societa','','i.description','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id join lookup_opu_tipo_impresa_societa i on o.tipo_societa = i.code','s.alt_id=#altid#','c','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Partita IVA','','o.partita_iva','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id','s.alt_id=#altid#','d','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','CUN SINVSA','','c.cun_sinvsa',' sintesis_stabilimento s inner join cun_amr c on c.numero_registrazione ilike s.approval_number','s.alt_id=#altid#','N0','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Indirizzo','','concat(
top.description, 
'' '',
ind.via, 
'',  '',
ind.civico, 
'', '',
com.nome, 
'' ('',
prov.description, 
'') '',
ind.cap, 
''; Lat:  '',
ind.latitudine, 
'' Lon:  '',
ind.longitudine)','  sintesis_stabilimento s join sintesis_indirizzo ind on ind.id = s.id_indirizzo
  left join lookup_toponimi top on top.code = ind.toponimo
  left join comuni1 com on com.id = ind.comune
  left join lookup_province prov on prov.code::text = ind.provincia','s.alt_id=#altid#','o','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Linee produttive','','mac.macroarea || ''->'' || agg.aggregazione || ''->'' || linea.linea_attivita  || '' ['' || stati.description || '']''','sintesis_relazione_stabilimento_linee_produttive rel join sintesis_stabilimento s on s.id = rel.id_stabilimento 
join master_list_linea_attivita linea on linea.id = rel.id_linea_produttiva
join master_list_aggregazione agg on linea.id_aggregazione = agg.id
join master_list_macroarea mac on agg.id_macroarea = mac.id
left join lookup_stato_attivita_sintesis stati on stati.code = rel.stato','s.alt_id=#altid# and rel.enabled','q','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Stato','','stati.description','lookup_stato_stabilimento_sintesis stati left join sintesis_stabilimento s on s.stato = stati.code','s.alt_id=#altid#','m','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Approval number','barcode','s.approval_number','sintesis_stabilimento s','s.alt_id=#altid#','n','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Tipo Impresa','','i.description','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id join lookup_opu_tipo_impresa i on o.tipo_impresa = i.code','s.alt_id=#altid#','b','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Indirizzo sede legale','',' concat_ws('' '', top.description, ind.via, ind.civico, com.nome, prov.description)','  sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id join sintesis_indirizzo ind on ind.id = o.id_indirizzo
  left join lookup_toponimi top on top.code = ind.toponimo
  left join comuni1 com on com.id = ind.comune
  left join lookup_province prov on prov.code::text = ind.provincia','s.alt_id=#altid#','g','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','DATA PROSSIMO CONTROLLO','','case when s.data_prossimo_controllo is null then to_char(current_date,''dd-mm-yyyy'') else to_char(s.data_prossimo_controllo,''dd-mm-yyyy'') end ','sintesis_stabilimento s','s.alt_id=#altid# and s.id not in (select s.id from sintesis_relazione_stabilimento_linee_produttive rel
join sintesis_stabilimento s on s.id = rel.id_stabilimento
join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva
join opu_lookup_norme_master_list norme on norme.code = linee.id_norma
where norme.codice_norma ilike ''1069-09'' and s.alt_id = #altid# limit 1)','nb','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','CATEGORIA DI RISCHIO ','','soa.description','sintesis_stabilimento s left join lookup_categoriarischio_soa soa on soa.code= s.livello_rischio','s.alt_id=#altid# and s.id in (select s.id from sintesis_relazione_stabilimento_linee_produttive rel
join sintesis_stabilimento s on s.id = rel.id_stabilimento
join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva
join opu_lookup_norme_master_list norme on norme.code = linee.id_norma
where norme.codice_norma ilike ''1069-09'' and s.alt_id = #altid# limit 1)','NC','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Dati Impresa','capitolo','','','','A','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Ragione sociale impresa','','o.ragione_sociale','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id','s.alt_id=#altid#','AA','');