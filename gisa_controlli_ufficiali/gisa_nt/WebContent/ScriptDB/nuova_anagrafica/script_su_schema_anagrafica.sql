-- View: public.-- View: public.ricerca_anagrafiche
-- 
insert into permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) values
(102, 'gestioneanagrafica', true, true, true, true, 'GESTIONE ANAGRAFICA REVOLUTION', 0);


DROP VIEW public.ricerca_anagrafiche;
-- View: anagrafica.anagrafica_operatori_view
DROP VIEW anagrafica.anagrafica_operatori_view;
-- View: anagrafica.anagrafica_operatori_view

-- DROP VIEW anagrafica.anagrafica_operatori_view;

CREATE OR REPLACE VIEW anagrafica.anagrafica_operatori_view AS
 SELECT s.alt_id AS riferimento_id,
    'altId'::text AS riferimento_id_nome,
    'alt_id'::text AS riferimento_id_nome_col,
    'stabilimenti'::text AS riferimento_id_nome_tab,
    s.data_inserimento,
    i.id AS id_impresa,
    i.ragione_sociale,
    s.id_asl AS asl_rif,
    la.description AS asl,
    i.codfisc AS codice_fiscale,
    sf_imp.id AS id_soggetto_fisico,
    sf_imp.codice_fiscale AS codice_fiscale_rappresentante,
    i.piva AS partita_iva,
    s.categoria_rischio,
    s.data_prossimo_controllo AS prossimo_controllo,
    rsla.cun AS num_riconoscimento,
    s.numero_registrazione::character varying(50) AS n_reg,
    rsla.numero_registrazione AS n_linea,
    concat(sf_imp.nome, ' ', sf_imp.cognome) AS nominativo_rappresentante,
    'Con Sede Fissa'::character varying(200) AS tipo_attivita_descrizione,
    1 AS tipo_attivita,
    rsla.data_inizio_attivita,
    rsla.data_fine_attivita,
    ls_att.description AS stato,
    rsla.id_stato,
    comuni_stab.nome AS comune,
    lp_stab.cod_provincia AS provincia_stab,
    concat(lt_stab.description, ' ', indirizzi_stab.via, ' ', indirizzi_stab.civico) AS indirizzo,
    indirizzi_stab.id AS id_sede_operativa,
    indirizzi_stab.cap AS cap_stab,
    indirizzi_stab.latitudine AS latitudine_stab,
    indirizzi_stab.longitudine AS longitudine_stab,
    comuni_imp.nome AS comune_leg,
    lp_imp.cod_provincia AS provincia_leg,
    concat(lt_imp.description, ' ', indirizzi_imp.via, ' ', indirizzi_imp.civico) AS indirizzo_leg,
    indirizzi_imp.id AS id_indirizzo_impresa,
    indirizzi_imp.cap AS cap_leg,
    indirizzi_imp.latitudine AS latitudine_leg,
    indirizzi_imp.longitudine AS longitudine_leg,
    NULL::integer AS sede_mobile_o_altro,
    COALESCE(mlm_c.macroarea, mlm.macroarea) AS macroarea,
    COALESCE(mla_c.aggregazione, mla.aggregazione) AS aggregazione,
    COALESCE(mlla_c.linea_attivita, mlla.linea_attivita) AS attivita,
    NULL::text AS path_attivita_completo,
    NULL::text AS gestione_masterlist,
    COALESCE(mlm_c.norma, mlm.norma) AS norma,
    COALESCE(mlm_c.id_norma, mlm.id_norma) AS id_norma,
    13 AS tipologia_operatore,
    NULL::text AS targa,
    NULL::integer AS tipo_ricerca_anagrafica,
    NULL::text AS color,
    NULL::text AS n_reg_old,
    NULL::integer AS id_tipo_linea_reg_ric,
    NULL::integer AS id_controllo_ultima_categorizzazione,
    COALESCE(mlla_c.id, mlla.id) AS id_linea,
    NULL::text AS matricola
   FROM anagrafica.imprese i
     JOIN anagrafica.rel_imprese_stabilimenti ris ON ris.id_impresa = i.id
     JOIN anagrafica.stabilimenti s ON s.id = ris.id_stabilimento
     LEFT JOIN anagrafica.lookup_asl la ON la.code = s.id_asl
     LEFT JOIN anagrafica.rel_imprese_soggetti_fisici risf ON risf.id_impresa = i.id
     LEFT JOIN anagrafica.soggetti_fisici sf_imp ON sf_imp.id = risf.id_soggetto_fisico
     LEFT JOIN anagrafica.rel_stabilimenti_linee_attivita rsla ON rsla.id_stabilimento = s.id
     LEFT JOIN anagrafica.lookup_tipo_attivita lta ON lta.code = rsla.id_tipo_attivita
     LEFT JOIN anagrafica.lookup_stati ls_att ON ls_att.code = rsla.id_stato
     LEFT JOIN anagrafica.rel_imprese_indirizzi rii ON rii.id_impresa = i.id
     LEFT JOIN anagrafica.indirizzi indirizzi_imp ON indirizzi_imp.id = rii.id_indirizzo
     LEFT JOIN anagrafica.comuni comuni_imp ON comuni_imp.id = indirizzi_imp.comune
     LEFT JOIN anagrafica.lookup_province lp_imp ON lp_imp.code = indirizzi_imp.provincia
     LEFT JOIN anagrafica.lookup_toponimi lt_imp ON lt_imp.code = indirizzi_imp.toponimo
     LEFT JOIN anagrafica.rel_stabilimenti_indirizzi rsi ON rsi.id_stabilimento = s.id
     LEFT JOIN anagrafica.indirizzi indirizzi_stab ON indirizzi_stab.id = rsi.id_indirizzo
     LEFT JOIN anagrafica.comuni comuni_stab ON comuni_stab.id = indirizzi_stab.comune
     LEFT JOIN anagrafica.lookup_province lp_stab ON lp_stab.code = indirizzi_stab.provincia
     LEFT JOIN anagrafica.lookup_toponimi lt_stab ON lt_stab.code = indirizzi_stab.toponimo
     LEFT JOIN master_list_linea_attivita mlla_c ON mlla_c.codice_univoco = rsla.codice_univoco
     LEFT JOIN master_list_aggregazione mla_c ON mla_c.id = mlla_c.id_aggregazione
     LEFT JOIN master_list_macroarea mlm_c ON mlm_c.id = mla_c.id_macroarea
     LEFT JOIN master_list_linea_attivita mlla ON mlla.id = rsla.id_attivita
     LEFT JOIN master_list_aggregazione mla ON mla.id = mlla.id_aggregazione
     LEFT JOIN master_list_macroarea mlm ON mlm.id = mla.id_macroarea
  WHERE i.data_cancellazione IS NULL AND s.data_cancellazione IS NULL AND sf_imp.data_cancellazione IS NULL AND ris.data_cancellazione IS NULL AND ris.data_scadenza IS NULL AND risf.data_cancellazione IS NULL AND risf.data_scadenza IS NULL AND rsla.data_cancellazione IS NULL AND rsla.data_scadenza IS NULL AND rii.data_cancellazione IS NULL AND rii.data_scadenza IS NULL AND rsi.data_cancellazione IS NULL AND rsi.data_scadenza IS NULL
UNION
 SELECT i.alt_id AS riferimento_id,
    'altId'::text AS riferimento_id_nome,
    'alt_id'::text AS riferimento_id_nome_col,
    'imprese'::text AS riferimento_id_nome_tab,
    i.data_inserimento,
    i.id AS id_impresa,
    i.ragione_sociale,
    comuni_imp.id_asl AS asl_rif,
    la.description AS asl,
    i.codfisc AS codice_fiscale,
    sf_imp.id AS id_soggetto_fisico,
    sf_imp.codice_fiscale AS codice_fiscale_rappresentante,
    i.piva AS partita_iva,
    NULL::integer AS categoria_rischio,
    NULL::date AS prossimo_controllo,
    rimla.cun AS num_riconoscimento,
    NULL::character varying(50) AS n_reg,
    rimla.numero_registrazione AS n_linea,
    concat(sf_imp.nome, ' ', sf_imp.cognome) AS nominativo_rappresentante,
    'Senza Sede Fissa'::character varying(200) AS tipo_attivita_descrizione,
    2 AS tipo_attivita,
    rimla.data_inizio_attivita,
    rimla.data_fine_attivita,
    ls_att.description AS stato,
    rimla.id_stato,
    comuni_lda.nome AS comune,
    lp_lda.cod_provincia AS provincia_stab,
    concat(lt_lda.description, ' ', indirizzi_lda.via, ' ', indirizzi_lda.civico) AS indirizzo,
    indirizzi_lda.id AS id_sede_operativa,
    indirizzi_lda.cap AS cap_stab,
    indirizzi_lda.latitudine AS latitudine_stab,
    indirizzi_lda.longitudine AS longitudine_stab,
    comuni_imp.nome AS comune_leg,
    lp_imp.cod_provincia AS provincia_leg,
    concat(lt_imp.description, ' ', indirizzi_imp.via, ' ', indirizzi_imp.civico) AS indirizzo_leg,
    indirizzi_imp.id AS id_indirizzo_impresa,
    indirizzi_imp.cap AS cap_leg,
    indirizzi_imp.latitudine AS latitudine_leg,
    indirizzi_imp.longitudine AS longitudine_leg,
    NULL::integer AS sede_mobile_o_altro,
    COALESCE(mlm_c.macroarea, mlm.macroarea) AS macroarea,
    COALESCE(mla_c.aggregazione, mla.aggregazione) AS aggregazione,
    COALESCE(mlla_c.linea_attivita, mlla.linea_attivita) AS attivita,
    NULL::text AS path_attivita_completo,
    NULL::text AS gestione_masterlist,
    COALESCE(mlm_c.norma, mlm.norma) AS norma,
    COALESCE(mlm_c.id_norma, mlm.id_norma) AS id_norma,
    13 AS tipologia_operatore,
    NULL::text AS targa,
    NULL::integer AS tipo_ricerca_anagrafica,
    NULL::text AS color,
    NULL::text AS n_reg_old,
    NULL::integer AS id_tipo_linea_reg_ric,
    NULL::integer AS id_controllo_ultima_categorizzazione,
    COALESCE(mlla_c.id, mlla.id) AS id_linea,
    NULL::text AS matricola
   FROM anagrafica.imprese i
     JOIN anagrafica.rel_imprese_mobili_linee_attivita rimla ON rimla.id_impresa = i.id
     LEFT JOIN anagrafica.rel_imprese_soggetti_fisici risf ON risf.id_impresa = i.id
     LEFT JOIN anagrafica.soggetti_fisici sf_imp ON sf_imp.id = risf.id_soggetto_fisico
     LEFT JOIN anagrafica.lookup_tipo_attivita lta ON lta.code = rimla.id_tipo_attivita
     LEFT JOIN anagrafica.lookup_stati ls_att ON ls_att.code = rimla.id_stato
     LEFT JOIN anagrafica.rel_lda_indirizzi_mobili rldaim ON rldaim.id_impresa_linea = rimla.id
     LEFT JOIN anagrafica.indirizzi indirizzi_lda ON indirizzi_lda.id = rldaim.id_indirizzo
     LEFT JOIN anagrafica.comuni comuni_lda ON comuni_lda.id = indirizzi_lda.comune
     LEFT JOIN anagrafica.lookup_province lp_lda ON lp_lda.code = indirizzi_lda.provincia
     LEFT JOIN anagrafica.lookup_toponimi lt_lda ON lt_lda.code = indirizzi_lda.toponimo
     LEFT JOIN anagrafica.rel_imprese_indirizzi rii ON rii.id_impresa = i.id
     LEFT JOIN anagrafica.indirizzi indirizzi_imp ON indirizzi_imp.id = rii.id_indirizzo
     LEFT JOIN anagrafica.comuni comuni_imp ON comuni_imp.id = indirizzi_imp.comune
     LEFT JOIN anagrafica.lookup_asl la ON la.code = comuni_imp.id_asl
     LEFT JOIN anagrafica.lookup_province lp_imp ON lp_imp.code = indirizzi_imp.provincia
     LEFT JOIN anagrafica.lookup_toponimi lt_imp ON lt_imp.code = indirizzi_imp.toponimo
     LEFT JOIN master_list_linea_attivita mlla_c ON mlla_c.codice_univoco = rimla.codice_univoco
     LEFT JOIN master_list_aggregazione mla_c ON mla_c.id = mlla_c.id_aggregazione
     LEFT JOIN master_list_macroarea mlm_c ON mlm_c.id = mla_c.id_macroarea
     LEFT JOIN master_list_linea_attivita mlla ON mlla.id = rimla.id_attivita
     LEFT JOIN master_list_aggregazione mla ON mla.id = mlla.id_aggregazione
     LEFT JOIN master_list_macroarea mlm ON mlm.id = mla.id_macroarea
  WHERE i.data_cancellazione IS NULL AND sf_imp.data_cancellazione IS NULL AND risf.data_cancellazione IS NULL AND risf.data_scadenza IS NULL AND rimla.data_cancellazione IS NULL AND rimla.data_scadenza IS NULL AND rii.data_cancellazione IS NULL AND rii.data_scadenza IS NULL;

ALTER TABLE anagrafica.anagrafica_operatori_view
  OWNER TO postgres;


  
CREATE OR REPLACE VIEW public.ricerca_anagrafiche AS 
 SELECT DISTINCT o.org_id AS riferimento_id,
    'orgId'::text AS riferimento_id_nome,
    'org_id'::text AS riferimento_id_nome_col,
    'organization'::text AS riferimento_id_nome_tab,
    oa1.address_id AS id_indirizzo_impresa,
    oa5.address_id AS id_sede_operativa,
    oa7.address_id AS sede_mobile_o_altro,
    'organization_address'::text AS riferimento_nome_tab_indirizzi,
    '-1'::integer AS id_impresa,
    '-'::text AS riferimento_nome_tab_impresa,
    '-1'::integer AS id_soggetto_fisico,
    '-'::text AS riferimento_nome_tab_soggetto_fisico,
    '-1'::integer AS id_attivita,
    true AS pregresso_o_import,
    o.org_id AS riferimento_org_id,
    o.entered AS data_inserimento,
    o.name AS ragione_sociale,
    o.site_id AS asl_rif,
    l_1.description AS asl,
    o.codice_fiscale,
    o.codice_fiscale_rappresentante,
    o.partita_iva,
        CASE
            WHEN o.tipologia = 97 AND o.categoria_rischio IS NULL THEN 3
            WHEN o.tipologia = 97 AND o.categoria_rischio = '-1'::integer THEN 3
            ELSE o.categoria_rischio
        END AS categoria_rischio,
    o.prossimo_controllo,
        CASE
            WHEN o.tipologia = ANY (ARRAY[3, 97]) THEN concat_ws(' '::text, o.numaut, o.tipo_stab)::character varying
            WHEN o.tipologia <> 1 AND COALESCE(o.numaut, o.account_number) IS NOT NULL THEN COALESCE(o.numaut, o.account_number)
            ELSE ''::character varying
        END AS num_riconoscimento,
        CASE
            WHEN o.tipologia = 1 THEN o.account_number
            ELSE ''::character varying
        END AS n_reg,
        CASE
            WHEN o.tipologia = 1 THEN o.account_number
            WHEN o.tipologia <> 1 AND COALESCE(o.numaut, o.account_number) IS NOT NULL THEN COALESCE(o.numaut, o.account_number)
            ELSE ''::character varying
        END AS n_linea,
        CASE
            WHEN o.tipologia = 2 THEN op_prop.nominativo
            WHEN o.nome_rappresentante IS NULL AND o.cognome_rappresentante IS NULL THEN 'Non specificato'::text
            WHEN o.nome_rappresentante::text = ' '::text AND o.cognome_rappresentante::text = ' '::text THEN 'Non specificato'::text
            ELSE (o.nome_rappresentante::text || ' '::text) || o.cognome_rappresentante::text
        END AS nominativo_rappresentante,
        CASE
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN 'ATTIVITA FISSA'::text::character varying
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Autoveicolo'::text THEN 'ATTIVITA MOBILE'::text::character varying
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Distributori'::text THEN 'Distributore'::text::character varying
            ELSE 'ATTIVITA FISSA'::character varying
        END AS tipo_attivita_descrizione,
        CASE
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN 1
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Autoveicolo'::text THEN 2
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Distributori'::text THEN 4
            ELSE 1
        END AS tipo_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.data_in_carattere IS NOT NULL AND o.data_fine_carattere IS NOT NULL THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = ANY (ARRAY[2, 9]) THEN to_date(o.date1::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = ANY (ARRAY[800, 801, 13, 802]) THEN o.date2
            WHEN o.tipologia = 152 AND o.stato ~~* '%attivo%'::text THEN to_date(o.data_cambio_stato::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = 17 THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            ELSE to_date(o.datapresentazione::text, 'yyyy/mm/dd'::text)::timestamp without time zone
        END AS data_inizio_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date THEN o.data_fine_carattere
            WHEN o.tipologia = 1 AND o.source <> 1 AND (o.cessato = 1 OR o.cessato = 2) THEN o.contract_end
            WHEN o.tipologia = ANY (ARRAY[2, 9]) THEN COALESCE(to_date(o.date2::text, 'yyyy/mm/dd'::text)::timestamp without time zone, o.data_cambio_stato)
            WHEN o.tipologia = 152 AND (o.stato ~~* '%sospeso%'::text OR o.stato ~~* '%cessato%'::text OR o.stato ~~* '%sospeso%'::text) THEN to_date(o.data_cambio_stato::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = ANY (ARRAY[800, 801, 13, 802]) THEN o.date1
            ELSE NULL::timestamp without time zone
        END AS data_fine_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < now() THEN 'Cessato'::text
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere > now() THEN 'Attivo'::text
            WHEN o.tipologia = 1 AND (o.source = 1 OR o.source IS NULL) AND o.data_fine_carattere IS NULL AND o.cessato IS NULL THEN 'Attivo'::text
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 0 THEN 'Attivo'::text
            WHEN o.tipologia = 1 AND (o.source = 2 OR o.source IS NULL) AND o.cessato = 1 THEN 'Cessato'::text
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 2 THEN 'Sospeso'::text
            WHEN o.tipologia = 3 AND o.direct_bill = true THEN 'Non specificato'::text
            WHEN o.tipologia = 2 THEN
            CASE
                WHEN o.date2 IS NOT NULL THEN 'Cessato'::text
                ELSE 'Attivo'::text
            END
            WHEN o.tipologia = 9 THEN o.stato_impresa
            WHEN o.stato_lab = 0 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Attivo'::text
            WHEN o.stato_lab = 1 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Revocato'::text
            WHEN o.stato_lab = 2 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Sospeso'::text
            WHEN o.stato_lab = 3 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'In Domanda'::text
            WHEN o.stato_lab = 4 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Cessato'::text
            WHEN o.stato_istruttoria IS NOT NULL AND (o.tipologia = ANY (ARRAY[3, 97])) THEN lss.description::text
            WHEN o.stato_lab IS NULL AND (o.tipologia = ANY (ARRAY[151, 152])) THEN o.stato
            ELSE 'N.D'::text
        END AS stato,
        CASE
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < now() THEN 4
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere > now() THEN 0
            WHEN o.tipologia = 1 AND (o.source = 1 OR o.source IS NULL) AND o.data_fine_carattere IS NULL AND o.cessato IS NULL THEN 0
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 0 THEN 0
            WHEN o.tipologia = 1 AND (o.source = 2 OR o.source IS NULL) AND o.cessato = 1 THEN 4
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 2 THEN 2
            WHEN o.tipologia = 3 AND o.direct_bill = true THEN '-1'::integer
            WHEN o.tipologia = 2 THEN
            CASE
                WHEN o.date2 IS NOT NULL THEN 4
                ELSE 0
            END
            WHEN o.stato_lab = 0 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 0
            WHEN o.stato_lab = 1 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 1
            WHEN o.stato_lab = 2 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 2
            WHEN o.stato_lab = 3 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 3
            WHEN o.stato_lab = 4 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 4
            WHEN o.stato_istruttoria IS NOT NULL AND (o.tipologia = ANY (ARRAY[3, 97])) THEN lss.code
            ELSE 0
        END AS id_stato,
        CASE
            WHEN o.tipologia = 3 OR o.tipologia = 97 THEN COALESCE(oa5.city, 'N.D.'::character varying)
            WHEN o.tipologia = 2 THEN COALESCE(c.nome, 'N.D'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.city, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.city, 'N.D.'::character varying)
            ELSE COALESCE(oa5.city, oa1.city, 'N.D.'::character varying)
        END AS comune,
        CASE
            WHEN o.tipologia = 2 THEN COALESCE(az.prov_sede_azienda, ''::text)::character varying
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.state, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.state, 'N.D.'::character varying)
            WHEN o.site_id = 201 AND o.tipologia <> 2 THEN 'AV'::text::character varying
            WHEN o.site_id = 202 AND o.tipologia <> 2 THEN 'BN'::text::character varying
            WHEN o.site_id = 203 AND o.tipologia <> 2 THEN 'CE'::text::character varying
            WHEN o.tipologia <> 2 AND (o.site_id = 204 OR o.site_id = 205 OR o.site_id = 206) THEN 'NA'::text::character varying
            WHEN o.site_id = 207 AND o.tipologia <> 2 THEN 'SA'::text::character varying
            ELSE COALESCE(oa5.state, oa1.state, 'N.D.'::character varying)
        END AS provincia_stab,
        CASE
            WHEN o.tipologia = 2 THEN COALESCE(az.indrizzo_azienda, ''::text)::character varying
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.addrline1, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.addrline1, 'N.D.'::character varying)
            ELSE COALESCE(oa5.addrline1, oa1.addrline1, 'N.D.'::character varying)
        END AS indirizzo,
        CASE
            WHEN o.tipologia = 2 THEN COALESCE(az.cap_azienda, ''::text)::character varying
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.postalcode, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.postalcode, 'N.D.'::character varying)
            ELSE COALESCE(oa5.postalcode, oa1.postalcode, 'N.D.'::character varying)
        END AS cap_stab,
    oa5.latitude AS latitudine_stab,
    oa5.longitude AS longitudine_stab,
        CASE
            WHEN o.tipologia = 3 OR o.tipologia = 97 THEN COALESCE(oa1.city, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa1.city, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.city, 'N.D.'::character varying)
            ELSE COALESCE(oa1.city, oa5.city, 'N.D.'::character varying)
        END AS comune_leg,
        CASE
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa1.state, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.state, 'N.D.'::character varying)
            ELSE COALESCE(oa1.state, oa5.state, 'N.D.'::character varying)
        END AS provincia_leg,
        CASE
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa1.addrline1, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.addrline1, 'N.D.'::character varying)
            ELSE COALESCE(oa1.addrline1, oa5.addrline1, 'N.D.'::character varying)
        END AS indirizzo_leg,
        CASE
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa1.postalcode, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.postalcode, 'N.D.'::character varying)
            ELSE COALESCE(oa1.postalcode, oa5.postalcode, 'N.D.'::character varying)
        END AS cap_leg,
    oa1.latitude AS latitudine_leg,
    oa1.longitude AS longitudine_leg,
    COALESCE(mltemp.macroarea, tsa.macroarea) AS macroarea,
    COALESCE(mltemp.aggregazione, tsa.aggregazione) AS aggregazione,
    COALESCE(mltemp.path_descrizione::text, (((tsa.macroarea || '->'::text) || tsa.aggregazione) || '->'::text) ||
        CASE
            WHEN tsa.attivita IS NOT NULL THEN concat_ws('->'::text, tsa.attivita, tsa.attivita_specifica)
            ELSE ''::text
        END) AS attivita,
        CASE
            WHEN mltemp.macroarea IS NOT NULL THEN mltemp.path_descrizione::text
            ELSE ''::text
        END AS path_attivita_completo,
        CASE
            WHEN mltemp.macroarea IS NOT NULL THEN NULL::text
            ELSE 'Non previsto'::text
        END AS gestione_masterlist,
    nm.description AS norma,
    nm.code AS id_norma,
    o.tipologia AS tipologia_operatore,
    o.nome_correntista AS targa,
        CASE
            WHEN o.trashed_date IS NULL THEN 0
            ELSE 3
        END AS tipo_ricerca_anagrafica,
    'red'::text AS color,
        CASE
            WHEN o.tipologia = 1 THEN o.account_number::text
            ELSE NULL::text
        END AS n_reg_old,
    tsa.id_tipo_linea_reg_ric,
    o.id_controllo_ultima_categorizzazione,
    tsa.id AS id_linea,
    NULL::text AS matricola
   FROM organization o
     LEFT JOIN la_imprese_linee_attivita lai ON lai.org_id = o.org_id AND lai.trashed_date IS NULL
     LEFT JOIN stabilimenti_sottoattivita ssa ON ssa.id_stabilimento = o.org_id
     LEFT JOIN soa_sottoattivita ssoa ON ssoa.id_soa = o.org_id
     LEFT JOIN aziende az ON az.cod_azienda = o.account_number::text
     LEFT JOIN comuni1 c ON c.istat::text = ('0'::text || az.cod_comune_azienda)
     LEFT JOIN opu_lookup_norme_master_list_rel_tipologia_organzation norme ON norme.tipologia_organization = o.tipologia
     LEFT JOIN opu_lookup_norme_master_list nm ON nm.code = norme.id_opu_lookup_norme_master_list
     LEFT JOIN ml8_linee_attivita_nuove_materializzata ml8 ON ml8.codice = norme.codice_univoco_ml
     LEFT JOIN linee_attivita_ml8_temp l ON l.org_id = o.org_id
     LEFT JOIN ml8_linee_attivita_nuove_materializzata mltemp ON mltemp.codice = l.codice_univoco_ml
     LEFT JOIN lookup_tipologia_operatore lto ON lto.code = o.tipologia
     LEFT JOIN lista_linee_vecchia_anagrafica_stabilimenti_soggetti_a_scia tsa ON tsa.org_id = o.org_id
     LEFT JOIN lookup_stati_stabilimenti lss ON lss.code = o.stato_istruttoria
     LEFT JOIN operatori_allevamenti op_prop ON o.codice_fiscale_rappresentante::text = op_prop.cf
     LEFT JOIN lookup_site_id l_1 ON l_1.code = o.site_id
     LEFT JOIN organization_address oa5 ON oa5.org_id = o.org_id AND oa5.address_type = 5 AND oa5.trasheddate IS NULL
     LEFT JOIN organization_address oa1 ON oa1.org_id = o.org_id AND oa1.address_type = 1 AND oa1.trasheddate IS NULL
     LEFT JOIN organization_address oa7 ON oa7.org_id = o.org_id AND oa7.address_type = 7 AND oa7.trasheddate IS NULL
  WHERE o.org_id <> 0 AND o.tipologia <> 0 AND (o.trashed_date IS NULL AND o.import_opu IS NOT TRUE OR o.trashed_date = '1970-01-01 00:00:00'::timestamp without time zone) AND ((o.tipologia = ANY (ARRAY[1, 151, 802, 152, 10, 20, 2, 800, 801])) OR o.tipologia = 3 AND o.direct_bill = false)
UNION
 SELECT global_org_view.riferimento_id,
    global_org_view.riferimento_id_nome,
    global_org_view.riferimento_id_nome_col,
    global_org_view.riferimento_id_nome_tab,
    global_org_view.id_indirizzo_impresa,
    global_org_view.id_sede_operativa,
    global_org_view.sede_mobile_o_altro,
    global_org_view.riferimento_nome_tab_indirizzi,
    global_org_view.id_impresa,
    global_org_view.riferimento_nome_tab_impresa,
    global_org_view.id_soggetto_fisico,
    global_org_view.riferimento_nome_tab_soggetto_fisico,
    global_org_view.id_attivita,
    true AS pregresso_o_import,
    global_org_view.riferimento_id AS riferimento_org_id,
    global_org_view.data_inserimento,
    global_org_view.ragione_sociale,
    global_org_view.asl_rif,
    global_org_view.asl,
    global_org_view.codice_fiscale,
    global_org_view.codice_fiscale_rappresentante,
    global_org_view.partita_iva,
    global_org_view.categoria_rischio,
    global_org_view.prossimo_controllo,
    global_org_view.num_riconoscimento,
    global_org_view.n_reg,
    global_org_view.n_linea,
    global_org_view.nominativo_rappresentante,
    global_org_view.tipo_attivita_descrizione,
    global_org_view.tipo_attivita,
    global_org_view.data_inizio_attivita,
    global_org_view.data_fine_attivita,
    global_org_view.stato,
    global_org_view.id_stato,
    global_org_view.comune,
    global_org_view.provincia_stab,
    global_org_view.indirizzo,
    global_org_view.cap_stab,
    global_org_view.latitudine_stab,
    global_org_view.longitudine_stab,
    global_org_view.comune_leg,
    global_org_view.provincia_leg,
    global_org_view.indirizzo_leg,
    global_org_view.cap_leg,
    global_org_view.latitudine_leg,
    global_org_view.longitudine_leg,
    global_org_view.macroarea,
    global_org_view.aggregazione,
    global_org_view.attivita,
    global_org_view.path_attivita_completo,
    global_org_view.gestione_masterlist,
    global_org_view.norma,
    global_org_view.id_norma,
    global_org_view.tipologia_operatore,
    global_org_view.targa,
    global_org_view.tipo_ricerca_anagrafica,
    global_org_view.color,
    global_org_view.n_reg_old,
    global_org_view.id_tipo_linea_produttiva AS id_tipo_linea_reg_ric,
    global_org_view.id_controllo_ultima_categorizzazione,
    global_org_view.id_linea,
    NULL::text AS matricola
   FROM global_org_view
UNION
 SELECT global_ric_view.riferimento_id,
    global_ric_view.riferimento_id_nome,
    global_ric_view.riferimento_id_nome_col,
    global_ric_view.riferimento_id_nome_tab,
    global_ric_view.id_indirizzo_impresa,
    global_ric_view.id_sede_operativa,
    global_ric_view.sede_mobile_o_altro,
    global_ric_view.riferimento_nome_tab_indirizzi,
    global_ric_view.id_impresa,
    global_ric_view.riferimento_nome_tab_impresa,
    global_ric_view.id_soggetto_fisico,
    global_ric_view.riferimento_nome_tab_soggetto_fisico,
    global_ric_view.id_attivita,
    true AS pregresso_o_import,
    global_ric_view.riferimento_id AS riferimento_org_id,
    global_ric_view.data_inserimento,
    global_ric_view.ragione_sociale,
    global_ric_view.asl_rif,
    global_ric_view.asl,
    global_ric_view.codice_fiscale,
    global_ric_view.codice_fiscale_rappresentante,
    global_ric_view.partita_iva,
    global_ric_view.categoria_rischio,
    NULL::timestamp without time zone AS prossimo_controllo,
    global_ric_view.num_riconoscimento,
    global_ric_view.n_reg,
    global_ric_view.n_linea,
    global_ric_view.nominativo_rappresentante,
    global_ric_view.tipo_attivita_descrizione,
    global_ric_view.tipo_attivita,
    global_ric_view.data_inizio_attivita,
    global_ric_view.data_fine_attivita,
    global_ric_view.stato,
    global_ric_view.id_stato,
    global_ric_view.comune,
    global_ric_view.provincia_stab,
    global_ric_view.indirizzo,
    global_ric_view.cap_stab,
    global_ric_view.latitudine_stab,
    global_ric_view.longitudine_stab,
    global_ric_view.comune_leg,
    global_ric_view.provincia_leg,
    global_ric_view.indirizzo_leg,
    global_ric_view.cap_leg,
    global_ric_view.latitudine_leg,
    global_ric_view.longitudine_leg,
    global_ric_view.macroarea,
    global_ric_view.aggregazione,
    global_ric_view.attivita,
    'Non presente'::text AS path_attivita_completo,
    'Non previsto'::text AS gestione_masterlist,
    global_ric_view.norma,
    global_ric_view.id_norma,
    global_ric_view.tipologia_operatore,
    global_ric_view.targa,
    global_ric_view.tipo_ricerca_anagrafica,
    global_ric_view.color,
    global_ric_view.n_reg_old,
    global_ric_view.id_tipo_linea_produttiva AS id_tipo_linea_reg_ric,
    global_ric_view.id_controllo_ultima_categorizzazione,
    global_ric_view.id_linea_attivita AS id_linea,
    NULL::text AS matricola
   FROM global_ric_view
  WHERE global_ric_view.id_stato = ANY (ARRAY[0, 7, 2])
UNION
 SELECT DISTINCT o.id_stabilimento AS riferimento_id,
    'stabId'::text AS riferimento_id_nome,
    'id_stabilimento'::text AS riferimento_id_nome_col,
    'opu_stabilimento'::text AS riferimento_id_nome_tab,
    o.id_indirizzo_operatore AS id_indirizzo_impresa,
    o.id_indirizzo AS id_sede_operativa,
    '-1'::integer AS sede_mobile_o_altro,
    'opu_indirizzo'::text AS riferimento_nome_tab_indirizzi,
    o.id_opu_operatore AS id_impresa,
    'opu_operatore'::text AS riferimento_nome_tab_impresa,
    o.id_soggetto_fisico,
    'opu_soggetto_fisico'::text AS riferimento_nome_tab_soggetto_fisico,
    o.id_linea_attivita_stab AS id_attivita,
    o.pregresso_o_import,
    o.riferimento_org_id,
    o.stab_entered AS data_inserimento,
    o.ragione_sociale,
    o.id_asl_stab AS asl_rif,
    o.stab_asl AS asl,
    o.codice_fiscale_impresa AS codice_fiscale,
    o.cf_rapp_sede_legale AS codice_fiscale_rappresentante,
    o.partita_iva,
    o.categoria_rischio,
    o.data_prossimo_controllo AS prossimo_controllo,
        CASE
            WHEN o.linea_codice_ufficiale_esistente ~~* 'U150%'::text THEN o.linea_codice_nazionale
            ELSE COALESCE(o.linea_codice_nazionale, o.linea_codice_ufficiale_esistente)
        END AS num_riconoscimento,
    o.numero_registrazione AS n_reg,
        CASE
            WHEN norme.codice_norma = '852-04'::text THEN COALESCE(NULLIF(o.linea_codice_nazionale, ''::text), NULLIF(o.linea_numero_registrazione, ''::text), NULLIF(o.linea_codice_ufficiale_esistente, ''::text))
            ELSE COALESCE(NULLIF(o.linea_codice_nazionale, ''::text), NULLIF(o.linea_codice_ufficiale_esistente, ''::text), NULLIF(o.linea_numero_registrazione, ''::text))
        END AS n_linea,
    concat_ws(' '::text, o.nome_rapp_sede_legale, o.cognome_rapp_sede_legale) AS nominativo_rappresentante,
    o.stab_descrizione_attivita AS tipo_attivita_descrizione,
    o.stab_id_attivita AS tipo_attivita,
    o.data_inizio_attivita,
    o.data_fine_attivita,
    o.linea_stato_text AS stato,
    o.linea_stato AS id_stato,
        CASE
            WHEN o.stab_id_attivita = 1 THEN o.comune_stab
            WHEN o.stab_id_attivita = 2 AND o.impresa_id_tipo_impresa = 1 THEN o.comune_residenza
            WHEN o.stab_id_attivita = 2 AND o.impresa_id_tipo_impresa <> 1 THEN o.comune_sede_legale
            ELSE NULL::character varying
        END AS comune,
    o.prov_stab AS provincia_stab,
    o.indirizzo_stab AS indirizzo,
    o.cap_stab,
    o.lat_stab AS latitudine_stab,
    o.long_stab AS longitudine_stab,
    o.comune_sede_legale AS comune_leg,
    o.prov_sede_legale AS provincia_leg,
    o.indirizzo_sede_legale AS indirizzo_leg,
    o.cap_sede_legale AS cap_leg,
    NULL::double precision AS latitudine_leg,
    NULL::double precision AS longitudine_leg,
    o.macroarea,
    o.aggregazione,
    o.attivita,
    o.path_attivita_completo,
        CASE
            WHEN o.flag_nuova_gestione IS NULL OR o.flag_nuova_gestione = false THEN 'LINEA NON AGGIORNATA SECONDO MASTER LIST.'::text
            ELSE NULL::text
        END AS gestione_masterlist,
    norme.description AS norma,
    o.id_norma,
    999 AS tipologia_operatore,
    m.targa,
    1 AS tipo_ricerca_anagrafica,
    'gray'::text AS color,
    o.linea_codice_ufficiale_esistente AS n_reg_old,
    o.id_tipo_linea_produttiva AS id_tipo_linea_reg_ric,
    o.id_controllo_ultima_categorizzazione,
    o.id_linea_attivita AS id_linea,
    d.matricola
   FROM opu_operatori_denormalizzati_view o
     LEFT JOIN opu_stabilimento_mobile m ON m.id_stabilimento = o.id_stabilimento
     LEFT JOIN opu_stabilimento_mobile_distributori d ON d.id_rel_stab_linea = o.id_linea_attivita
     LEFT JOIN opu_lookup_norme_master_list norme ON o.id_norma = norme.code
UNION
 SELECT DISTINCT o.id_stabilimento AS riferimento_id,
    'stabId'::text AS riferimento_id_nome,
    'id_stabilimento'::text AS riferimento_id_nome_col,
    'apicoltura_imprese'::text AS riferimento_id_nome_tab,
    o.id_indirizzo_operatore AS id_indirizzo_impresa,
    o.id_indirizzo AS id_sede_operativa,
    '-1'::integer AS sede_mobile_o_altro,
    'opu_indirizzo'::text AS riferimento_nome_tab_indirizzi,
    o.id_apicoltura_imprese AS id_impresa,
    'apicoltura_imprese'::text AS riferimento_nome_tab_impresa,
    COALESCE(o.id_soggetto_fisico, o.id_detentore) AS id_soggetto_fisico,
    'opu_soggetto_fisico'::text AS riferimento_nome_tab_soggetto_fisico,
    '-1'::integer AS id_attivita,
    false AS pregresso_o_import,
    '-1'::integer AS riferimento_org_id,
    o.entered AS data_inserimento,
    o.ragione_sociale,
    o.id_asl_apicoltore AS asl_rif,
    o.asl_apicoltore AS asl,
    o.codice_fiscale_impresa AS codice_fiscale,
    o.cf_rapp_sede_legale AS codice_fiscale_rappresentante,
    o.partita_iva,
    o.categoria_rischio,
    o.data_prossimo_controllo AS prossimo_controllo,
    COALESCE(o.codice_azienda, ''::text) AS num_riconoscimento,
    ''::text AS n_reg,
    COALESCE(o.codice_azienda, ''::text) AS n_linea,
    concat_ws(' '::text, o.nome_rapp_sede_legale, o.cognome_rapp_sede_legale) AS nominativo_rappresentante,
    'APICOLTURA'::text AS tipo_attivita_descrizione,
    '-1'::integer AS tipo_attivita,
    o.data_inizio_attivita::timestamp without time zone AS data_inizio_attivita,
    NULL::timestamp without time zone AS data_fine_attivita,
    o.stato_stab AS stato,
    0 AS id_stato,
    o.comune_stab AS comune,
    o.prov_stab AS provincia_stab,
    o.indirizzo_stab AS indirizzo,
    o.cap_stab,
    o.latitudine AS latitudine_stab,
    o.longitudine AS longitudine_stab,
    o.comune_sede_legale AS comune_leg,
    o.prov_sede_legale AS provincia_leg,
    o.indirizzo_sede_legale AS indirizzo_leg,
    o.cap_sede_legale AS cap_leg,
    NULL::double precision AS latitudine_leg,
    NULL::double precision AS longitudine_leg,
    o.macroarea,
    o.aggregazione,
    COALESCE(o.tipo_attivita, ''::text) AS attivita,
    'Non presente'::text AS path_attivita_completo,
    'Non previsto'::text AS gestione_masterlist,
    'APICOLTURA'::text AS norma,
    17 AS id_norma,
    1000 AS tipologia_operatore,
    NULL::text AS targa,
    1 AS tipo_ricerca_anagrafica,
    'gray'::text AS color,
    NULL::text AS n_reg_old,
    1 AS id_tipo_linea_reg_ric,
    o.id_controllo_ultima_categorizzazione,
    o.id_stabilimento AS id_linea,
    NULL::text AS matricola
   FROM apicoltura_apiari_denormalizzati_view o
UNION
 SELECT global_arch_view.riferimento_id,
    global_arch_view.riferimento_id_nome,
    global_arch_view.riferimento_id_nome_col,
    global_arch_view.riferimento_id_nome_tab,
    global_arch_view.id_indirizzo_impresa,
    global_arch_view.id_sede_operativa,
    global_arch_view.sede_mobile_o_altro,
    global_arch_view.riferimento_nome_tab_indirizzi,
    global_arch_view.id_impresa,
    global_arch_view.riferimento_nome_tab_impresa,
    global_arch_view.id_soggetto_fisico,
    global_arch_view.riferimento_nome_tab_soggetto_fisico,
    global_arch_view.id_attivita,
    true AS pregresso_o_import,
    global_arch_view.riferimento_id AS riferimento_org_id,
    global_arch_view.data_inserimento,
    global_arch_view.ragione_sociale,
    global_arch_view.asl_rif,
    global_arch_view.asl,
    global_arch_view.codice_fiscale,
    global_arch_view.codice_fiscale_rappresentante,
    global_arch_view.partita_iva,
    global_arch_view.categoria_rischio,
    global_arch_view.prossimo_controllo,
    global_arch_view.num_riconoscimento,
    global_arch_view.n_reg,
    global_arch_view.n_linea,
    global_arch_view.nominativo_rappresentante,
    global_arch_view.tipo_attivita_descrizione,
    global_arch_view.tipo_attivita,
    global_arch_view.data_inizio_attivita,
    global_arch_view.data_fine_attivita,
    global_arch_view.stato,
    global_arch_view.id_stato,
    global_arch_view.comune,
    global_arch_view.provincia_stab,
    global_arch_view.indirizzo,
    global_arch_view.cap_stab,
    global_arch_view.latitudine_stab,
    global_arch_view.longitudine_stab,
    global_arch_view.comune_leg,
    global_arch_view.provincia_leg,
    global_arch_view.indirizzo_leg,
    global_arch_view.cap_leg,
    global_arch_view.latitudine_leg,
    global_arch_view.longitudine_leg,
    global_arch_view.macroarea,
    global_arch_view.aggregazione,
    global_arch_view.attivita,
    'Non presente'::text AS path_attivita_completo,
    'Non previsto'::text AS gestione_masterlist,
    global_arch_view.norma,
    global_arch_view.id_norma,
    global_arch_view.tipologia_operatore,
    global_arch_view.targa,
    global_arch_view.tipo_ricerca_anagrafica,
    global_arch_view.color,
    global_arch_view.n_reg_old,
    global_arch_view.id_tipo_linea_produttiva AS id_tipo_linea_reg_ric,
    global_arch_view.id_controllo_ultima_categorizzazione,
    global_arch_view.id_linea,
    NULL::text AS matricola
   FROM global_arch_view
UNION
 SELECT DISTINCT o.alt_id AS riferimento_id,
    'altId'::text AS riferimento_id_nome,
    'alt_id'::text AS riferimento_id_nome_col,
    'sintesis_stabilimento'::text AS riferimento_id_nome_tab,
    o.id_indirizzo_operatore AS id_indirizzo_impresa,
    o.id_indirizzo AS id_sede_operativa,
    '-1'::integer AS sede_mobile_o_altro,
    'sintesis_indirizzo'::text AS riferimento_nome_tab_indirizzi,
    o.id_opu_operatore AS id_impresa,
    'sintesis_operatore'::text AS riferimento_nome_tab_impresa,
    o.id_soggetto_fisico,
    'sintesis_soggetto_fisico'::text AS riferimento_nome_tab_soggetto_fisico,
    o.id_linea_attivita_stab AS id_attivita,
    false AS pregresso_o_import,
    '-1'::integer AS riferimento_org_id,
    o.stab_entered AS data_inserimento,
    o.ragione_sociale,
    o.id_asl_stab AS asl_rif,
    o.stab_asl AS asl,
    o.codice_fiscale_impresa AS codice_fiscale,
    o.cf_rapp_sede_legale AS codice_fiscale_rappresentante,
    o.partita_iva,
    o.categoria_rischio,
    o.data_prossimo_controllo AS prossimo_controllo,
    o.approval_number AS num_riconoscimento,
    ''::text AS n_reg,
    o.approval_number AS n_linea,
    concat_ws(' '::text, o.nome_rapp_sede_legale, o.cognome_rapp_sede_legale) AS nominativo_rappresentante,
    o.stab_descrizione_attivita AS tipo_attivita_descrizione,
    o.stab_id_attivita AS tipo_attivita,
    o.data_inizio_attivita,
    o.data_fine_attivita,
    o.linea_stato_text AS stato,
    o.linea_stato AS id_stato,
    o.comune_stab AS comune,
    o.prov_stab AS provincia_stab,
    o.indirizzo_stab AS indirizzo,
    o.cap_stab,
    o.lat_stab AS latitudine_stab,
    o.long_stab AS longitudine_stab,
    o.comune_sede_legale AS comune_leg,
    o.prov_sede_legale AS provincia_leg,
    o.indirizzo_sede_legale AS indirizzo_leg,
    o.cap_sede_legale AS cap_leg,
    NULL::double precision AS latitudine_leg,
    NULL::double precision AS longitudine_leg,
    o.macroarea,
    o.aggregazione,
    o.attivita,
    o.path_attivita_completo,
    NULL::text AS gestione_masterlist,
    o.norma,
    o.id_norma,
    2000 AS tipologia_operatore,
    ''::text AS targa,
    1 AS tipo_ricerca_anagrafica,
    'gray'::text AS color,
    o.linea_codice_ufficiale_esistente AS n_reg_old,
    o.id_tipo_linea_produttiva AS id_tipo_linea_reg_ric,
    o.id_controllo_ultima_categorizzazione,
    o.id_linea_attivita AS id_linea,
    NULL::text AS matricola
   FROM sintesis_operatori_denormalizzati_view o
UNION
 SELECT DISTINCT anagrafica_operatori_view.riferimento_id,
    anagrafica_operatori_view.riferimento_id_nome,
    anagrafica_operatori_view.riferimento_id_nome_col,
    anagrafica_operatori_view.riferimento_id_nome_tab,
    anagrafica_operatori_view.id_indirizzo_impresa,
    anagrafica_operatori_view.id_sede_operativa,
    anagrafica_operatori_view.sede_mobile_o_altro,
    'indirizzi'::text AS riferimento_nome_tab_indirizzi,
    anagrafica_operatori_view.id_impresa,
    'imprese'::text AS riferimento_nome_tab_impresa,
    anagrafica_operatori_view.id_soggetto_fisico,
    'soggetti_fisici'::text AS riferimento_nome_tab_soggetto_fisico,
    anagrafica_operatori_view.id_linea AS id_attivita,
    NULL::boolean AS pregresso_o_import,
    NULL::integer AS riferimento_org_id,
    anagrafica_operatori_view.data_inserimento,
    anagrafica_operatori_view.ragione_sociale,
    anagrafica_operatori_view.asl_rif,
    anagrafica_operatori_view.asl,
    anagrafica_operatori_view.codice_fiscale,
    anagrafica_operatori_view.codice_fiscale_rappresentante,
    anagrafica_operatori_view.partita_iva,
    anagrafica_operatori_view.categoria_rischio,
    anagrafica_operatori_view.prossimo_controllo,
    anagrafica_operatori_view.num_riconoscimento,
    anagrafica_operatori_view.n_reg,
    NULL::character varying AS n_linea,
    anagrafica_operatori_view.nominativo_rappresentante,
    anagrafica_operatori_view.tipo_attivita_descrizione,
    anagrafica_operatori_view.tipo_attivita,
    anagrafica_operatori_view.data_inizio_attivita,
    anagrafica_operatori_view.data_fine_attivita,
    anagrafica_operatori_view.stato,
    anagrafica_operatori_view.id_stato,
    anagrafica_operatori_view.comune,
    anagrafica_operatori_view.provincia_stab,
    anagrafica_operatori_view.indirizzo,
    anagrafica_operatori_view.cap_stab,
    anagrafica_operatori_view.latitudine_stab,
    anagrafica_operatori_view.longitudine_stab,
    anagrafica_operatori_view.comune_leg,
    anagrafica_operatori_view.provincia_leg,
    anagrafica_operatori_view.indirizzo_leg,
    anagrafica_operatori_view.cap_leg,
    anagrafica_operatori_view.latitudine_leg,
    anagrafica_operatori_view.longitudine_leg,
    anagrafica_operatori_view.macroarea,
    anagrafica_operatori_view.aggregazione,
    anagrafica_operatori_view.attivita,
    anagrafica_operatori_view.path_attivita_completo,
    anagrafica_operatori_view.gestione_masterlist,
    anagrafica_operatori_view.norma,
    anagrafica_operatori_view.id_norma,
    anagrafica_operatori_view.tipologia_operatore,
    anagrafica_operatori_view.targa,
    anagrafica_operatori_view.tipo_ricerca_anagrafica,
    anagrafica_operatori_view.color,
    anagrafica_operatori_view.n_reg_old,
    anagrafica_operatori_view.id_tipo_linea_reg_ric,
    anagrafica_operatori_view.id_controllo_ultima_categorizzazione,
    anagrafica_operatori_view.id_linea,
    anagrafica_operatori_view.matricola
   FROM anagrafica.anagrafica_operatori_view;

-- Function: anagrafica.anagrafica_insert_into_ricerche_anagrafiche_old_materializzata(integer)

-- DROP FUNCTION anagrafica.anagrafica_insert_into_ricerche_anagrafiche_old_materializzata(integer);

CREATE OR REPLACE FUNCTION anagrafica.anagrafica_insert_into_ricerche_anagrafiche_old_materializzata(idstabilimento integer)
  RETURNS boolean AS
$BODY$
DECLARE
idRecord int ;
BEGIN

raise info 'sto eliminando dalla tabella ';
delete from ricerche_anagrafiche_old_materializzata 
where 
	riferimento_id_nome ='altId' and 
	riferimento_id_nome_tab = 'stabilimenti'  and 
	riferimento_id =idStabilimento ;

raise info 'Eliminazione Eseguita ';

insert into ricerche_anagrafiche_old_materializzata 
( riferimento_id, riferimento_id_nome, riferimento_id_nome_col, 
            riferimento_id_nome_tab, data_inserimento, ragione_sociale, asl_rif, 
            asl, codice_fiscale, codice_fiscale_rappresentante, partita_iva, 
            categoria_rischio, prossimo_controllo, num_riconoscimento, n_reg,n_linea,
            nominativo_rappresentante, tipo_attivita_descrizione, tipo_attivita, 
            data_inizio_attivita, data_fine_attivita, stato, id_stato, comune, 
            provincia_stab, indirizzo, cap_stab,  latitudine_stab, longitudine_stab, comune_leg, provincia_leg, 
            indirizzo_leg, cap_leg, latitudine_leg, longitudine_leg, macroarea, aggregazione, attivita, path_attivita_completo, 
            gestione_masterlist, norma, id_norma, tipologia_operatore, targa,
            tipo_ricerca_anagrafica, color, n_reg_old, id_tipo_linea_reg_ric, 
            id_controllo_ultima_categorizzazione,id_linea, matricola)
select 
	riferimento_id, riferimento_id_nome, riferimento_id_nome_col, 
       riferimento_id_nome_tab, data_inserimento, ragione_sociale, asl_rif, 
       asl, codice_fiscale, codice_fiscale_rappresentante, partita_iva, 
       categoria_rischio, prossimo_controllo, num_riconoscimento, n_reg, 
       n_linea, nominativo_rappresentante, tipo_attivita_descrizione, 
       tipo_attivita, data_inizio_attivita, data_fine_attivita, stato, 
       id_stato, comune, provincia_stab, indirizzo, cap_stab, latitudine_stab, 
       longitudine_stab, comune_leg, provincia_leg, indirizzo_leg, cap_leg, 
       latitudine_leg, longitudine_leg, macroarea, aggregazione, attivita, 
       path_attivita_completo, gestione_masterlist, norma, id_norma, 
       tipologia_operatore, targa, tipo_ricerca_anagrafica, color, n_reg_old, 
       id_tipo_linea_reg_ric, id_controllo_ultima_categorizzazione, 
       id_linea, matricola
from
	anagrafica.anagrafica_operatori_view a
where
	a.riferimento_id=idstabilimento;
	
raise info 'Nuovo Inserimento Effettuato ';

	return true ;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION anagrafica.anagrafica_insert_into_ricerche_anagrafiche_old_materializzata(integer)
  OWNER TO postgres;


delete from ricerche_anagrafiche_old_materializzata;
insert into ricerche_anagrafiche_old_materializzata (select * from ricerca_anagrafiche );

--select * from anagrafica.anagrafica_operatori_view  (140000007)