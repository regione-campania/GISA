alter table lookup_categoriarischio_soa add column range_da integer;
alter table lookup_categoriarischio_soa add column range_a integer;

update lookup_categoriarischio_soa set range_a = 29 where code = 1;
update lookup_categoriarischio_soa set range_da = 30, range_a = 44 where code = 2;
update lookup_categoriarischio_soa set range_da = 45 where code = 3;

update lookup_categoriarischio_soa set code = code + 90; 

update lookup_categoriarischio_soa set description= concat(description, ' - ', code-90) ;

create VIEW lookup_categoria_rischio as
select id as code, id::text as description from parametri_categorizzazzione_osa where codice_norma ilike '%852%'
UNION
select code, description from lookup_categoriarischio_soa 

alter table sintesis_stabilimento add column livello_rischio integer;

CREATE OR REPLACE FUNCTION public.get_livello_rischio_soa(punteggio integer)
  RETURNS integer AS
$BODY$
   DECLARE
livellorischio integer;   

BEGIN
select code into livellorischio from lookup_categoriarischio_soa where (range_da <= punteggio or range_da is null) and (range_a >=punteggio or range_a is null);

	RETURN livellorischio;
	
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_livello_rischio_soa(integer)
  OWNER TO postgres;

  
  update scheda_operatore_metadati set enabled = false where tipo_operatore = 32;insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Dati Impresa','capitolo','','','','A','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Tipo Impresa','','i.description','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id join lookup_opu_tipo_impresa i on o.tipo_impresa = i.code','s.alt_id=#altid#','b','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Ragione sociale impresa','','o.ragione_sociale','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id','s.alt_id=#altid#','AA','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Tipo Societa','','i.description','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id join lookup_opu_tipo_impresa_societa i on o.tipo_societa = i.code','s.alt_id=#altid#','c','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Partita IVA','','o.partita_iva','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id','s.alt_id=#altid#','d','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Codice Fiscale Impresa','','o.codice_fiscale_impresa','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id','s.alt_id=#altid#','e','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Domicilio digitale','','o.domicilio_digitale','sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id','s.alt_id=#altid#','f','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Indirizzo sede legale','',' concat_ws('' '', top.description, ind.via, ind.civico, com.nome, prov.description)','  sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id join sintesis_indirizzo ind on ind.id = o.id_indirizzo
  left join lookup_toponimi top on top.code = ind.toponimo
  left join comuni1 com on com.id = ind.comune
  left join lookup_province prov on prov.code::text = ind.provincia','s.alt_id=#altid#','g','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Rappresentante legale','',' concat_ws('' '', sogg.nome, sogg.cognome, sogg.codice_fiscale, ''<br/>'', top.description, ind.via, ind.civico, com.nome, prov.description)',' sintesis_stabilimento s join sintesis_operatore o on s.id_operatore = o.id join sintesis_rel_operatore_soggetto_fisico rel on rel.id_operatore = o.id and rel.enabled and rel.data_fine is null left join sintesis_soggetto_fisico sogg on sogg.id = rel.id_soggetto_fisico left join sintesis_indirizzo ind on ind.id = sogg.indirizzo_id
 left join lookup_toponimi top on top.code = ind.toponimo
  left join comuni1 com on com.id = ind.comune
  left join lookup_province prov on prov.code::text = ind.provincia','s.alt_id=#altid#','h','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Dati Stabilimento','capitolo','','','','i','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Denominazione','','s.denominazione','sintesis_stabilimento s','s.alt_id=#altid#','l','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Stato','','stati.description','lookup_stato_stabilimento_sintesis stati left join sintesis_stabilimento s on s.stato = stati.code','s.alt_id=#altid#','m','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Approval number','barcode','s.approval_number','sintesis_stabilimento s','s.alt_id=#altid#','n','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','CATEGORIA DI RISCHIO','','soa.description','sintesis_stabilimento s left join lookup_categoriarischio_soa soa on soa.code= s.livello_rischio','s.alt_id=#altid# and s.id in (select s.id from sintesis_relazione_stabilimento_linee_produttive rel
join sintesis_stabilimento s on s.id = rel.id_stabilimento
join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva
join opu_lookup_norme_master_list norme on norme.code = linee.id_norma
where norme.codice_norma ilike ''1069-09'' and s.alt_id = #altid# limit 1)','NC','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','CATEGORIA DI RISCHIO','','coalesce(categoria_rischio,3)','sintesis_stabilimento s','s.alt_id=#altid# and s.id not in (select s.id from sintesis_relazione_stabilimento_linee_produttive rel
join sintesis_stabilimento s on s.id = rel.id_stabilimento
join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva
join opu_lookup_norme_master_list norme on norme.code = linee.id_norma
where norme.codice_norma ilike ''1069-09'' and s.alt_id = #altid# limit 1)','NA','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','DATA PROSSIMO CONTROLLO','','case when s.data_prossimo_controllo is null then to_char(current_date,''dd-mm-yyyy'') else to_char(s.data_prossimo_controllo,''dd-mm-yyyy'') end ','sintesis_stabilimento s','s.alt_id=#altid# and s.id not in (select s.id from sintesis_relazione_stabilimento_linee_produttive rel
join sintesis_stabilimento s on s.id = rel.id_stabilimento
join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva
join opu_lookup_norme_master_list norme on norme.code = linee.id_norma
where norme.codice_norma ilike ''1069-09'' and s.alt_id = #altid# limit 1)','nb','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','DATA PROSSIMO CONTROLLO','','''NON PREVISTA''','sintesis_stabilimento s','s.alt_id=#altid# and s.id in (select s.id from sintesis_relazione_stabilimento_linee_produttive rel
join sintesis_stabilimento s on s.id = rel.id_stabilimento
join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva
join opu_lookup_norme_master_list norme on norme.code = linee.id_norma
where norme.codice_norma ilike ''1069-09'' and s.alt_id = #altid# limit 1)','ND','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','ASL','asl','asl.description','sintesis_stabilimento s join lookup_site_id asl on asl.code = s.id_asl','s.alt_id=#altid#','NN','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Indirizzo','',' concat_ws('' '', top.description, ind.via, ind.civico, com.nome, prov.description)','  sintesis_stabilimento s join sintesis_indirizzo ind on ind.id = s.id_indirizzo
  left join lookup_toponimi top on top.code = ind.toponimo
  left join comuni1 com on com.id = ind.comune
  left join lookup_province prov on prov.code::text = ind.provincia','s.alt_id=#altid#','o','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Lista linee produttive','capitolo','','','','p','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Linee produttive','','mac.macroarea || ''->'' || agg.aggregazione || ''->'' || linea.linea_attivita  || '' ['' || stati.description || '']''','sintesis_relazione_stabilimento_linee_produttive rel join sintesis_stabilimento s on s.id = rel.id_stabilimento 
join master_list_linea_attivita linea on linea.id = rel.id_linea_produttiva
join master_list_aggregazione agg on linea.id_aggregazione = agg.id
join master_list_macroarea mac on agg.id_macroarea = mac.id
left join lookup_stato_attivita_sintesis stati on stati.code = rel.stato','s.alt_id=#altid# and rel.enabled','q','');insert into scheda_operatore_metadati(tipo_operatore, label, attributo, sql_campo, sql_origine, sql_condizione, ordine, destinazione) values ('32','Lista prodotti','','concat_ws('' -> '', ''<b>''||tit_scheda||''<b/>'', lista_prodotti) from(
select distinct s.tit_scheda, string_agg(s.nome || case when p.valore_prodotto <>'''' then ''(''||p.valore_prodotto||'')'' else '''' end, ''; '') as lista_prodotti','master_list_sk_elenco s
join sintesis_prodotti p on s.id = p.id_prodotto
join sintesis_relazione_stabilimento_linee_produttive rel on rel.id = p.id_linea
join sintesis_stabilimento st on st.id = rel.id_stabilimento','st.alt_id = #altid# and p.unchecked_date is null
group by tit_scheda
) aa
','R','');


--- PREGRESSO

create table sintesis_soa_max as
select  
s.alt_id, s.categoria_rischio, s.data_prossimo_controllo, s.livello_rischio, t.ticketid, t.assigned_date, t.punteggio
from sintesis_stabilimento s
left join ticket t on t.tipologia=3 and t.provvedimenti_prescrittivi=5 and t.alt_id = s.alt_id
where s.id in (select s.id from sintesis_relazione_stabilimento_linee_produttive rel join sintesis_stabilimento s on s.id = rel.id_stabilimento join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva join opu_lookup_norme_master_list norme on norme.code = linee.id_norma where norme.codice_norma ilike '1069-09')
order by s.alt_id, t.assigned_date desc;

select distinct on (alt_id) alt_id, assigned_date, punteggio, get_livello_rischio_soa(punteggio), 'update sintesis_stabilimento set livello_rischio = ' ||  get_livello_rischio_soa(punteggio) || ' , notes_hd = concat_ws('';'', notes_hd, ''Flusso 95: La categoria di rischio del SOA viene azzerata e il livello rischio viene calcolato a ' ||  get_livello_rischio_soa(punteggio) || ' sulla base del punteggio ultimo controllo in sorveglianza (' || punteggio || ')'') where alt_id = ' || alt_id ||';'
from sintesis_soa_max ;

drop table sintesis_soa_max;

-- digemon

-- View: public.sintesis_operatori_denormalizzati_view

-- DROP VIEW public.sintesis_operatori_denormalizzati_view;

CREATE OR REPLACE VIEW public.sintesis_operatori_denormalizzati_view AS 
 SELECT DISTINCT false AS flag_dia,
    o.id AS id_opu_operatore,
    o.ragione_sociale,
    o.partita_iva,
    o.codice_fiscale_impresa,
    (((
        CASE
            WHEN topsedeop.description IS NOT NULL THEN topsedeop.description
            ELSE ''::character varying
        END::text || ' '::text) || sedeop.via::text) || ' '::text) ||
        CASE
            WHEN sedeop.civico IS NOT NULL THEN sedeop.civico
            ELSE ''::text
        END AS indirizzo_sede_legale,
    comunisl.nome AS comune_sede_legale,
    comunisl.istat AS istat_legale,
    sedeop.cap AS cap_sede_legale,
    provsedeop.description AS prov_sede_legale,
    o.note,
    o.entered,
    o.modified,
    o.enteredby,
    o.modifiedby,
    o.domicilio_digitale,
    o.flag_ric_ce,
    o.num_ric_ce,
    i.comune,
    stab.id_asl,
    stab.id AS id_stabilimento,
    stab.esito_import,
    stab.data_import,
    stab.cun,
    stab.id_sinvsa,
    stab.descrizione_errore,
    comuni1.nome AS comune_stab,
    comuni1.istat AS istat_operativo,
    ((
        CASE
            WHEN topsedestab.description IS NOT NULL THEN topsedestab.description
            ELSE ''::character varying
        END::text || ' '::text) || sedestab.via::text) || ' '::text AS indirizzo_stab,
    sedestab.cap AS cap_stab,
    provsedestab.description AS prov_stab,
    stab.data_fine_dia,
    case when n.description ilike '%1069%' then stab.livello_rischio else stab.categoria_rischio end as categoria_rischio,
    soggsl.codice_fiscale AS cf_rapp_sede_legale,
    soggsl.nome AS nome_rapp_sede_legale,
    soggsl.cognome AS cognome_rapp_sede_legale,
    stab.numero_registrazione AS codice_registrazione,
    n.description AS norma,
    lmac.codice_norma,
    stab.approval_number,
    n.code AS id_norma,
    ''::text AS cf_correntista,
    ''::text AS codice_attivita,
    true AS primario,
    (((lmac.macroarea || '->'::text) || lagg.aggregazione) || '->'::text) || latt.linea_attivita AS attivita,
    lps.data_inizio,
    lps.data_fine,
    stab.numero_registrazione,
    (((((((
        CASE
            WHEN topsoggind.description IS NOT NULL THEN topsoggind.description
            ELSE ''::character varying
        END::text || ' '::text) ||
        CASE
            WHEN soggind.via IS NOT NULL THEN soggind.via
            ELSE ''::character varying
        END::text) || ', '::text) ||
        CASE
            WHEN soggind.civico IS NOT NULL THEN soggind.civico
            ELSE ''::text
        END) || ' '::text) ||
        CASE
            WHEN comunisoggind.nome IS NOT NULL THEN comunisoggind.nome
            ELSE ''::character varying
        END::text) || ' '::text) ||
        CASE
            WHEN provsoggind.description IS NOT NULL THEN provsoggind.description
            ELSE ''::text
        END AS indirizzo_rapp_sede_legale,
    stati.description AS stato,
    latt.linea_attivita AS solo_attivita,
    stab.data_inizio_attivita,
    stab.data_fine_attivita,
    case when n.description ilike '%1069%' then null else stab.data_prossimo_controllo end as data_prossimo_controllo,
    stab.stato AS id_stato,
    (((lmac.macroarea || '->'::text) || lagg.aggregazione) || '->'::text) || latt.linea_attivita AS path_attivita_completo,
    stab.id_indirizzo,
    stab.linee_pregresse,
    true AS flag_nuova_gestione,
    loti.description AS tipo_impresa,
    lotis.description AS tipo_societa,
    stab.codice_ufficiale_esistente,
    stab.id_asl AS id_asl_stab,
    lps.id AS id_linea_attivita,
    lps.modified AS data_mod_attivita,
    stab.entered AS stab_entered,
    lps.numero_registrazione AS linea_numero_registrazione,
    lps.stato AS linea_stato,
    statilinea.description AS linea_stato_text,
    lps.codice_ufficiale_esistente AS linea_codice_ufficiale_esistente,
    stab.codice_ufficiale_esistente AS stab_codice_ufficiale_esistente,
    sedeop.id AS id_indirizzo_operatore,
    stab.import_opu,
    lps.id_linea_produttiva AS id_linea_attivita_stab,
    o.note AS note_operatore,
    stab.note AS note_stabilimento,
    lps.codice_nazionale AS linea_codice_nazionale,
    false AS flag_pnaa,
    (con.namefirst::text || ' '::text) || con.namelast::text AS stab_inserito_da,
    stab.tipo_attivita AS stab_id_attivita,
    lsa.description AS stab_descrizione_attivita,
    stab.tipo_carattere AS stab_id_carattere,
    lsc.description AS stab_descrizione_carattere,
    o.tipo_impresa AS impresa_id_tipo_impresa,
    asl.description AS stab_asl,
    o.flag_clean,
    stab.data_generazione_numero,
    sedestab.latitudine AS lat_stab,
    sedestab.longitudine AS long_stab,
    lps.entered AS linea_entered,
    lps.modified AS linea_modified,
    lmac.macroarea,
    lagg.aggregazione,
    latt.linea_attivita AS attivita_xml,
    comuni1.codiceistatasl_old,
    provsedestab.cod_provincia AS sigla_prov_operativa,
    provsedeop.cod_provincia AS sigla_prov_legale,
    provsoggind.cod_provincia AS sigla_prov_soggfisico,
    comunisoggind.nome AS comune_residenza,
    soggsl.data_nascita AS data_nascita_rapp_sede_legale,
    lotis.code AS impresa_id_tipo_societa,
    soggsl.comune_nascita AS comune_nascita_rapp_sede_legale,
    soggsl.sesso,
    soggind.civico,
    topsoggind.description AS toponimo_residenza,
    soggind.toponimo AS id_toponimo_residenza,
    sedeop.civico AS civico_sede_legale,
    sedeop.toponimo AS tiponimo_sede_legale,
    topsedeop.description AS toponimo_sede_legale,
    sedestab.civico AS civico_sede_stab,
    sedestab.toponimo AS tiponimo_sede_stab,
    topsedestab.description AS toponimo_sede_stab,
    soggind.via AS via_rapp_sede_legale,
    sedeop.via AS via_sede_legale,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN comunisoggind.id
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN comunisl.id
            ELSE NULL::integer
        END AS id_comune_richiesta,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN comunisoggind.nome
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN comunisl.nome
            ELSE NULL::character varying
        END AS comune_richiesta,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.via::text)::character varying
            ELSE NULL::character varying
        END AS via_stabilimento_calcolata,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.civico)::character varying
            ELSE NULL::character varying
        END AS civico_stabilimento_calcolato,
    soggind.cap AS cap_residenza,
    soggind.nazione AS nazione_residenza,
    sedeop.nazione AS nazione_sede_legale,
    sedestab.nazione AS nazione_stab,
    '-1'::integer AS id_lookup_tipo_linee_mobili,
    '-1'::integer AS id_tipo_linea_produttiva,
    rels.id_soggetto_fisico,
    lps.pregresso_o_import,
    stab.riferimento_org_id,
    stab.id_controllo_ultima_categorizzazione,
    stab.alt_id
   FROM sintesis_operatore o
     LEFT JOIN sintesis_rel_operatore_soggetto_fisico rels ON rels.id_operatore = o.id AND rels.enabled
     LEFT JOIN sintesis_soggetto_fisico soggsl ON soggsl.id = rels.id_soggetto_fisico
     LEFT JOIN sintesis_indirizzo soggind ON soggind.id = soggsl.indirizzo_id
     LEFT JOIN comuni1 comunisoggind ON comunisoggind.id = soggind.comune
     LEFT JOIN sintesis_indirizzo sedeop ON sedeop.id = o.id_indirizzo
     LEFT JOIN comuni1 comunisl ON sedeop.comune = comunisl.id
     JOIN sintesis_stabilimento stab ON stab.id_operatore = o.id
     JOIN sintesis_relazione_stabilimento_linee_produttive lps ON lps.id_stabilimento = stab.id AND lps.enabled
     LEFT JOIN lookup_stato_stabilimento_sintesis stati ON stati.code = stab.stato
     JOIN master_list_linea_attivita latt ON latt.id = lps.id_linea_produttiva
     JOIN master_list_aggregazione lagg ON lagg.id = latt.id_aggregazione
     JOIN master_list_macroarea lmac ON lmac.id = lagg.id_macroarea
     JOIN sintesis_indirizzo sedestab ON sedestab.id = stab.id_indirizzo
     LEFT JOIN comuni1 ON sedestab.comune = comuni1.id
     LEFT JOIN sintesis_indirizzo i ON i.id = stab.id_indirizzo
     LEFT JOIN lookup_province provsedestab ON provsedestab.code::text = sedestab.provincia::text
     LEFT JOIN lookup_province provsedeop ON provsedeop.code::text = sedeop.provincia::text
     LEFT JOIN lookup_province provsoggind ON provsoggind.code::text = soggind.provincia::text
     LEFT JOIN lookup_toponimi topsedeop ON sedeop.toponimo = topsedeop.code AND topsedeop.enabled
     LEFT JOIN lookup_toponimi topsedestab ON sedestab.toponimo = topsedestab.code AND topsedestab.enabled
     LEFT JOIN lookup_toponimi topsoggind ON soggind.toponimo = topsoggind.code AND topsoggind.enabled
     LEFT JOIN lookup_opu_tipo_impresa loti ON loti.code = o.tipo_impresa
     LEFT JOIN lookup_opu_tipo_impresa_societa lotis ON lotis.code = o.tipo_societa
     LEFT JOIN lookup_stato_attivita_sintesis statilinea ON statilinea.code = lps.stato
     LEFT JOIN access acc ON acc.user_id = stab.entered_by
     LEFT JOIN contact con ON con.contact_id = acc.contact_id
     LEFT JOIN opu_lookup_tipologia_attivita lsa ON lsa.code = 1
     LEFT JOIN opu_lookup_tipologia_carattere lsc ON lsc.code = 1
     LEFT JOIN lookup_site_id asl ON asl.code = stab.id_asl
     LEFT JOIN opu_lookup_norme_master_list n ON n.codice_norma = lmac.codice_norma AND n.enabled
  WHERE o.trashed_date IS NULL AND stab.trashed_date IS NULL AND stab.trashed_date IS NULL;

ALTER TABLE public.sintesis_operatori_denormalizzati_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.sintesis_operatori_denormalizzati_view TO postgres;


delete from ricerche_anagrafiche_old_materializzata;
insert into ricerche_anagrafiche_old_materializzata  (select * from ricerca_anagrafiche) ;




