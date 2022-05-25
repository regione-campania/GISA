
CREATE OR REPLACE VIEW public.report_vista_stabilimenti_rendicontazionecu AS 
 SELECT r.org_id,
    r.asl,
    r.rag_soc,
    r.rag_soc_prec,
    r.approval_number,
    r.des_cat,
    r.des_impianto,
    r.descrizione_stato_attivita,
    r.anno_inserimento,
    r.anno_inizio_attivita,
    r.data_inizio_attivita,
    r.data_fine_attivita,
    r.indirizzo_op,
    r.comune_op,
    r.prov_op,
    r.categoria_rischio,
    r.prossimo_controllo,
    r.tipo_categorizzazzione,
    r.data_inizio_controllo,
    vr.anno AS anno_controlli,
    vr.numero_controlli,
    vr.numero_controlli_ispezione,
    vr.num_nc_gravi,
    vr.num_campioni,
    vr.campioni_non_regolamentari,
    vr.numero_reati,
    vr.numero_cu_con_nc_formali_o_sign
   FROM report_vista_stabilimenti_old r
     LEFT JOIN view_rendicontazione_controlli_ufficiali vr ON r.org_id::text = vr.org_id;

ALTER TABLE public.report_vista_stabilimenti_rendicontazionecu
  OWNER TO postgres;
GRANT ALL ON TABLE public.report_vista_stabilimenti_rendicontazionecu TO postgres;
GRANT SELECT ON TABLE public.report_vista_stabilimenti_rendicontazionecu TO report;
GRANT SELECT ON TABLE public.report_vista_stabilimenti_rendicontazionecu TO usr_ro;


CREATE OR REPLACE VIEW public.view_rendicontazione_non_conformita_gravi_settore_uso_umano_new AS 
 SELECT
        CASE
            WHEN v6.num_nc_gravi_altro > 0 THEN v6.org_id
            WHEN v5.num_nc_gravi_etichet_presentazione > 0 THEN v5.org_id
            WHEN v4.num_nc_gravi_contaminazione > 0 THEN v4.org_id
            WHEN v3.num_nc_gravi_composizione > 0 THEN v3.org_id
            WHEN v2.num_nc_gravi_igiene > 0 THEN v2.org_id
            WHEN v1.num_nc_gravi_igiene_generale > 0 THEN v1.org_id
            ELSE NULL::integer
        END AS org_id,
        CASE
            WHEN v6.num_nc_gravi_altro > 0 THEN v6.data_inserimento
            WHEN v5.num_nc_gravi_etichet_presentazione > 0 THEN v5.data_inserimento
            WHEN v4.num_nc_gravi_contaminazione > 0 THEN v4.data_inserimento
            WHEN v3.num_nc_gravi_composizione > 0 THEN v3.data_inserimento
            WHEN v2.num_nc_gravi_igiene > 0 THEN v2.data_inserimento
            WHEN v1.num_nc_gravi_igiene_generale > 0 THEN v1.data_inserimento
            ELSE NULL::timestamp without time zone
        END AS data_inserimento,
        CASE
            WHEN v6.num_nc_gravi_altro > 0 THEN v6.anno
            WHEN v5.num_nc_gravi_etichet_presentazione > 0 THEN v5.anno
            WHEN v4.num_nc_gravi_contaminazione > 0 THEN v4.anno
            WHEN v3.num_nc_gravi_composizione > 0 THEN v3.anno
            WHEN v2.num_nc_gravi_igiene > 0 THEN v2.anno
            WHEN v1.num_nc_gravi_igiene_generale > 0 THEN v1.anno
            ELSE NULL::text
        END AS anno,
    v6.num_nc_gravi_altro,
    v5.num_nc_gravi_etichet_presentazione,
    v4.num_nc_gravi_contaminazione,
    v3.num_nc_gravi_composizione,
    v2.num_nc_gravi_igiene,
    v1.num_nc_gravi_igiene_generale
   FROM ( SELECT cu.org_id,
            date_part('years'::text, cu.assigned_date)::text AS anno,
            cu.assigned_date AS data_inserimento,
            count(snc.id) AS num_nc_gravi_igiene_generale
           FROM ticket cu
             LEFT JOIN ticket nc ON cu.id_controllo_ufficiale::text = nc.id_controllo_ufficiale::text
             LEFT JOIN salvataggio_nc_note snc ON nc.ticketid = snc.idticket AND snc.tipologia = 3
          WHERE cu.trashed_date IS NULL AND nc.trashed_date IS NULL AND cu.tipologia = 3 AND nc.tipologia = 8 AND (snc.id_non_conformita = ANY (ARRAY[2, 19]))
          GROUP BY cu.org_id, (date_part('years'::text, cu.assigned_date)), cu.assigned_date
          ORDER BY cu.org_id) v1
     FULL JOIN ( SELECT cu.org_id,
            date_part('years'::text, cu.assigned_date)::text AS anno,
            cu.assigned_date AS data_inserimento,
            count(snc.id) AS num_nc_gravi_igiene
           FROM ticket cu
             LEFT JOIN ticket nc ON cu.id_controllo_ufficiale::text = nc.id_controllo_ufficiale::text
             LEFT JOIN salvataggio_nc_note snc ON nc.ticketid = snc.idticket AND snc.tipologia = 3
          WHERE cu.trashed_date IS NULL AND nc.trashed_date IS NULL AND cu.tipologia = 3 AND nc.tipologia = 8 AND (snc.id_non_conformita = ANY (ARRAY[7, 20, 21, 22]))
          GROUP BY cu.org_id, (date_part('years'::text, cu.assigned_date)), cu.assigned_date
          ORDER BY cu.org_id) v2 ON v1.org_id = v2.org_id
     FULL JOIN ( SELECT cu.org_id,
            date_part('years'::text, cu.assigned_date)::text AS anno,
            cu.assigned_date AS data_inserimento,
            count(snc.id) AS num_nc_gravi_composizione
           FROM ticket cu
             LEFT JOIN ticket nc ON cu.id_controllo_ufficiale::text = nc.id_controllo_ufficiale::text
             LEFT JOIN salvataggio_nc_note snc ON nc.ticketid = snc.idticket AND snc.tipologia = 3
          WHERE cu.trashed_date IS NULL AND nc.trashed_date IS NULL AND cu.tipologia = 3 AND nc.tipologia = 8 AND snc.id_non_conformita = 24
          GROUP BY cu.org_id, (date_part('years'::text, cu.assigned_date)), cu.assigned_date
          ORDER BY cu.org_id) v3 ON v2.org_id = v3.org_id
     FULL JOIN ( SELECT cu.org_id,
            date_part('years'::text, cu.assigned_date)::text AS anno,
            cu.assigned_date AS data_inserimento,
            count(snc.id) AS num_nc_gravi_contaminazione
           FROM ticket cu
             LEFT JOIN ticket nc ON cu.id_controllo_ufficiale::text = nc.id_controllo_ufficiale::text
             LEFT JOIN salvataggio_nc_note snc ON nc.ticketid = snc.idticket AND snc.tipologia = 3
          WHERE cu.trashed_date IS NULL AND nc.trashed_date IS NULL AND cu.tipologia = 3 AND nc.tipologia = 8 AND (snc.id_non_conformita = ANY (ARRAY[4, 8]))
          GROUP BY cu.org_id, (date_part('years'::text, cu.assigned_date)), cu.assigned_date
          ORDER BY cu.org_id) v4 ON v3.org_id = v4.org_id
     FULL JOIN ( SELECT cu.org_id,
            date_part('years'::text, cu.assigned_date)::text AS anno,
            cu.assigned_date AS data_inserimento,
            count(snc.id) AS num_nc_gravi_etichet_presentazione
           FROM ticket cu
             LEFT JOIN ticket nc ON cu.id_controllo_ufficiale::text = nc.id_controllo_ufficiale::text
             LEFT JOIN salvataggio_nc_note snc ON nc.ticketid = snc.idticket AND snc.tipologia = 3
          WHERE cu.trashed_date IS NULL AND nc.trashed_date IS NULL AND cu.tipologia = 3 AND nc.tipologia = 8 AND snc.id_non_conformita = 5
          GROUP BY cu.org_id, (date_part('years'::text, cu.assigned_date)), cu.assigned_date
          ORDER BY cu.org_id) v5 ON v4.org_id = v5.org_id
     FULL JOIN ( SELECT cu.org_id,
            date_part('years'::text, cu.assigned_date)::text AS anno,
            cu.assigned_date AS data_inserimento,
            count(snc.id) AS num_nc_gravi_altro
           FROM ticket cu
             LEFT JOIN ticket nc ON cu.id_controllo_ufficiale::text = nc.id_controllo_ufficiale::text
             LEFT JOIN salvataggio_nc_note snc ON nc.ticketid = snc.idticket AND snc.tipologia = 3
          WHERE cu.trashed_date IS NULL AND nc.trashed_date IS NULL AND cu.tipologia = 3 AND nc.tipologia = 8 AND (snc.id_non_conformita = ANY (ARRAY[23, 25]))
          GROUP BY cu.org_id, (date_part('years'::text, cu.assigned_date)), cu.assigned_date
          ORDER BY cu.org_id) v6 ON v5.org_id = v6.org_id;

ALTER TABLE public.view_rendicontazione_non_conformita_gravi_settore_uso_umano_new
  OWNER TO postgres;
GRANT ALL ON TABLE public.view_rendicontazione_non_conformita_gravi_settore_uso_umano_new TO postgres;
GRANT SELECT ON TABLE public.view_rendicontazione_non_conformita_gravi_settore_uso_umano_new TO report;
GRANT SELECT ON TABLE public.view_rendicontazione_non_conformita_gravi_settore_uso_umano_new TO usr_ro;

DROP VIEW public.report_view_imprese_rendicontazione_ncf_gravi;

CREATE OR REPLACE VIEW public.report_view_imprese_rendicontazione_ncf_gravi AS 
 SELECT r.org_id,
    r.asl,
    r.inserito_da,
    r.tipo_dia,
    r.ragione_sociale,
    r.denominazione,
    r.numero_registrazione,
    r.partitaiva,
    r.codice_istat_principale,
    r.codice_istat1,
    r.codice_istat2,
    r.codice_istat3,
    r.codice_istat4,
    r.codice_istat5,
    r.codice_istat6,
    r.codice_istat7,
    r.codice_istat8,
    r.codice_istat9,
    r.codice_istat10,
    r.descrizione_codice_istat,
    r.codicefiscalerappresentante,
    r.cognome_rappresentante,
    r.nome_rappresentante,
    r.indirizzo_sede_legale,
    r.comune_sede_legale,
    r.provincia_sede_legale,
    r.carattere,
    r.tipo_attivita,
    r.data_inizio_attivita,
    r.stato_impresa,
    r.data_fine_attivita,
    r.anno_inizio_attivita,
    r.anno_inserimento,
    r.indirizzo_sede_operativa,
    r.comune_sede_operativa,
    r.provincia_sede_operativa,
    r.data_presentazione_dia,
    r.anno_presentazione_dia,
    r.categoria_rischio,
    r.prossimo_controllo,
    r.data_prossimo_controllo,
    r.data_inizio_controllo,
    r.tipo_categorizzazzione,
    vr.data_inserimento,
    vr.anno,
    vr.num_nc_gravi_altro::text AS num_nc_gravi_altro,
    vr.num_nc_gravi_etichet_presentazione::text AS num_nc_gravi_etichet_presentazione,
    vr.num_nc_gravi_contaminazione::text AS num_nc_gravi_contaminazione,
    vr.num_nc_gravi_composizione::text AS num_nc_gravi_composizione,
    vr.num_nc_gravi_igiene::text AS num_nc_gravi_igiene,
    vr.num_nc_gravi_igiene_generale::text AS num_nc_gravi_igiene_generale
   FROM report_vista_imprese r
     LEFT JOIN view_rendicontazione_non_conformita_gravi_settore_uso_umano_new vr ON r.org_id = vr.org_id;

ALTER TABLE public.report_view_imprese_rendicontazione_ncf_gravi
  OWNER TO postgres;
GRANT ALL ON TABLE public.report_view_imprese_rendicontazione_ncf_gravi TO postgres;
GRANT SELECT ON TABLE public.report_view_imprese_rendicontazione_ncf_gravi TO report;
GRANT SELECT ON TABLE public.report_view_imprese_rendicontazione_ncf_gravi TO usr_ro;


CREATE OR REPLACE VIEW public.report_vista_stabilimenti AS 
 SELECT o.org_id,
    lst.description AS asl,
    o.name AS rag_soc,
    o.banca AS rag_soc_prec,
    o.numaut AS approval_number,
    lc.description AS des_cat,
    st.attivita AS des_impianto,
    st.descrizione_stato_attivita,
    date_part('years'::text, st.data_inizio_attivita) AS anno_inizio_attivita,
    st.data_inizio_attivita,
    st.data_fine_attivita,
    date_part('years'::text, st.data_fine_attivita) AS anno_fine_attivita,
    stato.description AS stato_stabilimento,
    oa.addrline1 AS indirizzo_op,
    oa.city AS comune_op,
    oa.state AS prov_op,
    o.categoria_rischio,
        CASE
            WHEN cu.isaggiornata_categoria = false THEN 'Categoria Ex ante'::text
            WHEN cu.isaggiornata_categoria = true THEN 'Categorizzata da cu'::text
            ELSE 'Categoria Ex ante'::text
        END AS tipo_categorizzazzione,
        CASE
            WHEN cu.isaggiornata_categoria = false THEN 'NON AGGIORNATA DA CU'::text
            WHEN cu.isaggiornata_categoria = true THEN cu.assigned_date::text
            ELSE 'NON AGGIORNATA DA CU'::text
        END AS data_inizio_controllo,
    o.prossimo_controllo AS data_prossimo_controllo
   FROM organization o
     LEFT JOIN organization_address oa ON o.org_id = oa.org_id
     JOIN stabilimenti_sottoattivita st ON o.org_id = st.id_stabilimento
     LEFT JOIN lookup_categoria lc ON st.codice_sezione = lc.code
     LEFT JOIN lookup_site_id lst ON o.site_id = lst.code
     LEFT JOIN lookup_stato_lab stato ON o.stato_lab = stato.code
     LEFT JOIN ticket cu ON cu.org_id = o.org_id AND cu.tipologia = 3 AND cu.trashed_date IS NULL AND o.prossimo_controllo = cu.data_prossimo_controllo
  WHERE o.tipologia = 3 AND o.trashed_date IS NULL
  ORDER BY lst.description, o.name;

ALTER TABLE public.report_vista_stabilimenti
  OWNER TO postgres;

  
CREATE OR REPLACE VIEW public.report_vista_allevamenti_rendicontazionecu AS 
 SELECT r.org_id,
    r.asl,
    r.inserito_da,
    r.ragione_sociale,
    r.denominazione,
    r.codice_aziendale,
    r.specie_allevate,
    r.orientamento_produttivo,
    r.tipologia_struttura,
    r.indirizzo_sede_operativa,
    r.comune_sede_operativa,
    r.provincia_sede_operativa,
    r.anno_inizio_attivita,
    r.data_inizio_attivita,
    r.data_fine_attivita,
    r.categoria_rischio,
    r.prossimo_controllo,
    r.tipo_categorizzazzione,
    r.data_inizio_controllo,
    vr.anno AS anno_controlli,
    vr.numero_controlli,
    vr.numero_controlli_ispezione,
    vr.num_nc_gravi,
    vr.num_campioni,
    vr.campioni_non_regolamentari,
    vr.numero_reati,
    vr.numero_cu_con_nc_formali_o_sign
   FROM report_vista_allevamenti_old_old r
     LEFT JOIN view_rendicontazione_controlli_ufficiali vr ON r.org_id::text = vr.org_id;

ALTER TABLE public.report_vista_allevamenti_rendicontazionecu
  OWNER TO postgres;
GRANT ALL ON TABLE public.report_vista_allevamenti_rendicontazionecu TO postgres;
GRANT SELECT ON TABLE public.report_vista_allevamenti_rendicontazionecu TO report;
GRANT SELECT ON TABLE public.report_vista_allevamenti_rendicontazionecu TO usr_ro;


CREATE OR REPLACE VIEW public.report_stabilimenti_ncfgravi_settoreusoumano AS 
 SELECT r.org_id,
    r.asl,
    r.rag_soc,
    r.rag_soc_prec,
    r.approval_number,
    r.des_cat,
    r.des_impianto,
    r.descrizione_stato_attivita,
    r.anno_inserimento,
    r.anno_inizio_attivita,
    r.data_inizio_attivita,
    r.data_fine_attivita,
    r.indirizzo_op,
    r.comune_op,
    r.prov_op,
    r.categoria_rischio,
    r.prossimo_controllo,
    r.tipo_categorizzazzione,
    r.data_inizio_controllo,
    vr.data_inserimento,
    vr.anno,
    vr.num_nc_gravi_altro::text AS num_nc_gravi_altro,
    vr.num_nc_gravi_etichet_presentazione::text AS num_nc_gravi_etichet_presentazione,
    vr.num_nc_gravi_contaminazione::text AS num_nc_gravi_contaminazione,
    vr.num_nc_gravi_composizione::text AS num_nc_gravi_composizione,
    vr.num_nc_gravi_igiene::text AS num_nc_gravi_igiene,
    vr.num_nc_gravi_igiene_generale::text AS num_nc_gravi_igiene_generale
   FROM report_vista_stabilimenti_old r
     LEFT JOIN view_rendicontazione_non_conformita_gravi_settore_uso_umano_new vr ON r.org_id = vr.org_id;

ALTER TABLE public.report_stabilimenti_ncfgravi_settoreusoumano
  OWNER TO postgres;
GRANT ALL ON TABLE public.report_stabilimenti_ncfgravi_settoreusoumano TO postgres;
GRANT SELECT ON TABLE public.report_stabilimenti_ncfgravi_settoreusoumano TO report;
GRANT SELECT ON TABLE public.report_stabilimenti_ncfgravi_settoreusoumano TO usr_ro;

-- View: public.report_imprese_ncfgravi_settoreusoumano

-- DROP VIEW public.report_imprese_ncfgravi_settoreusoumano;

CREATE OR REPLACE VIEW public.report_imprese_ncfgravi_settoreusoumano AS 
 SELECT r.org_id,
    r.asl,
    r.inserito_da,
    r.tipo_dia,
    r.ragione_sociale,
    r.denominazione,
    r.numero_registrazione,
    r.partitaiva,
    r.codice_istat_principale,
    r.codice_istat1,
    r.codice_istat2,
    r.codice_istat3,
    r.codice_istat4,
    r.codice_istat5,
    r.codice_istat6,
    r.codice_istat7,
    r.codice_istat8,
    r.codice_istat9,
    r.codice_istat10,
    r.descrizione_codice_istat,
    r.codicefiscalerappresentante,
    r.cognome_rappresentante,
    r.nome_rappresentante,
    r.indirizzo_sede_legale,
    r.comune_sede_legale,
    r.provincia_sede_legale,
    r.carattere,
    r.tipo_attivita,
    r.data_inizio_attivita,
    r.stato_impresa,
    r.data_fine_attivita,
    r.anno_inizio_attivita,
    r.anno_inserimento,
    r.indirizzo_sede_operativa,
    r.comune_sede_operativa,
    r.provincia_sede_operativa,
    r.data_presentazione_dia,
    r.anno_presentazione_dia,
    r.categoria_rischio,
    r.prossimo_controllo,
    r.data_prossimo_controllo,
    r.data_inizio_controllo,
    r.tipo_categorizzazzione,
    vr.data_inserimento,
    vr.anno,
    vr.num_nc_gravi_altro::text AS num_nc_gravi_altro,
    vr.num_nc_gravi_etichet_presentazione::text AS num_nc_gravi_etichet_presentazione,
    vr.num_nc_gravi_contaminazione::text AS num_nc_gravi_contaminazione,
    vr.num_nc_gravi_composizione::text AS num_nc_gravi_composizione,
    vr.num_nc_gravi_igiene::text AS num_nc_gravi_igiene,
    vr.num_nc_gravi_igiene_generale::text AS num_nc_gravi_igiene_generale
   FROM report_vista_imprese r
     LEFT JOIN view_rendicontazione_non_conformita_gravi_settore_uso_umano_new vr ON r.org_id = vr.org_id;

ALTER TABLE public.report_imprese_ncfgravi_settoreusoumano
  OWNER TO postgres;
GRANT ALL ON TABLE public.report_imprese_ncfgravi_settoreusoumano TO postgres;
GRANT SELECT ON TABLE public.report_imprese_ncfgravi_settoreusoumano TO report;
GRANT SELECT ON TABLE public.report_imprese_ncfgravi_settoreusoumano TO usr_ro;

-- View: public.report_allevamenti_ncfgravi_settoreusoumano

-- DROP VIEW public.report_allevamenti_ncfgravi_settoreusoumano;

CREATE OR REPLACE VIEW public.report_allevamenti_ncfgravi_settoreusoumano AS 
 SELECT r.org_id,
    r.asl,
    r.inserito_da,
    r.ragione_sociale,
    r.denominazione,
    r.codice_aziendale,
    r.specie_allevate,
    r.orientamento_produttivo,
    r.tipologia_struttura,
    r.indirizzo_sede_operativa,
    r.comune_sede_operativa,
    r.provincia_sede_operativa,
    r.anno_inizio_attivita,
    r.data_inizio_attivita,
    r.data_fine_attivita,
    r.categoria_rischio,
    r.prossimo_controllo,
    r.tipo_categorizzazzione,
    r.data_inizio_controllo,
    vr.data_inserimento,
    vr.anno,
    vr.num_nc_gravi_altro::text AS num_nc_gravi_altro,
    vr.num_nc_gravi_etichet_presentazione::text AS num_nc_gravi_etichet_presentazione,
    vr.num_nc_gravi_contaminazione::text AS num_nc_gravi_contaminazione,
    vr.num_nc_gravi_composizione::text AS num_nc_gravi_composizione,
    vr.num_nc_gravi_igiene::text AS num_nc_gravi_igiene,
    vr.num_nc_gravi_igiene_generale::text AS num_nc_gravi_igiene_generale
   FROM report_vista_allevamenti_old_old r
     LEFT JOIN view_rendicontazione_non_conformita_gravi_settore_uso_umano_new vr ON r.org_id = vr.org_id;

ALTER TABLE public.report_allevamenti_ncfgravi_settoreusoumano
  OWNER TO postgres;
GRANT ALL ON TABLE public.report_allevamenti_ncfgravi_settoreusoumano TO postgres;
GRANT SELECT ON TABLE public.report_allevamenti_ncfgravi_settoreusoumano TO report;
GRANT SELECT ON TABLE public.report_allevamenti_ncfgravi_settoreusoumano TO usr_ro;
