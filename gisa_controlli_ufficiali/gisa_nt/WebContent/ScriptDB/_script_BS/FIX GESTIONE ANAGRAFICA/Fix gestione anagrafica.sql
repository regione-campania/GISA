-- View: anagrafica.anagrafica_operatori_view

-- DROP VIEW anagrafica.anagrafica_operatori_view;

CREATE OR REPLACE VIEW anagrafica.anagrafica_operatori_view AS 
 SELECT s.alt_id AS riferimento_id,
    'altId'::text AS riferimento_id_nome,
    'alt_id'::text AS riferimento_id_nome_col,
    'stabilimenti'::text AS riferimento_id_nome_tab,
    COALESCE(mapp.entered_old, ( SELECT min(imp.data_inserimento) AS min
           FROM anagrafica.imprese imp
          WHERE imp.id = i.id)) AS data_inserimento,
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
        CASE
            WHEN COALESCE(mlla_c.codice_univoco, mlla.codice_univoco) = 'OPR-OPR-X'::text THEN 13
            ELSE 3000
        END AS tipologia_operatore,
    NULL::text AS targa,
    1 AS tipo_ricerca_anagrafica,
    NULL::text AS color,
    NULL::text AS n_reg_old,
    NULL::integer AS id_tipo_linea_reg_ric,
    NULL::integer AS id_controllo_ultima_categorizzazione,
    COALESCE(mlla_c.id, mlla.id) AS id_linea,
    NULL::text AS matricola
   FROM anagrafica.imprese i
     JOIN anagrafica.rel_imprese_stabilimenti ris ON ris.id_impresa = i.id
     JOIN anagrafica.stabilimenti s ON s.id = ris.id_stabilimento
     LEFT JOIN anagrafica.anagrafica_mappatura_old_new mapp ON mapp.id_impresa_new = i.id
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
    COALESCE(mapp.entered_old, ( SELECT min(imp.data_inserimento) AS min
           FROM anagrafica.imprese imp
          WHERE imp.id = i.id)) AS data_inserimento,
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
        CASE
            WHEN COALESCE(mlla_c.codice_univoco, mlla.codice_univoco) = 'OPR-OPR-X'::text THEN 13
            ELSE 3000
        END AS tipologia_operatore,
    NULL::text AS targa,
    1 AS tipo_ricerca_anagrafica,
    NULL::text AS color,
    NULL::text AS n_reg_old,
    NULL::integer AS id_tipo_linea_reg_ric,
    NULL::integer AS id_controllo_ultima_categorizzazione,
    COALESCE(mlla_c.id, mlla.id) AS id_linea,
    NULL::text AS matricola
   FROM anagrafica.imprese i
     JOIN anagrafica.rel_imprese_mobili_linee_attivita rimla ON rimla.id_impresa = i.id
     LEFT JOIN anagrafica.anagrafica_mappatura_old_new mapp ON mapp.id_impresa_new = i.id
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

  
  
delete from ricerche_anagrafiche_old_materializzata;
insert into ricerche_anagrafiche_old_materializzata  (select * from ricerca_anagrafiche) ;
