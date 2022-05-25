
CREATE OR REPLACE VIEW public.suap_ric_linee_attivita_stabilimenti_view AS 
 SELECT DISTINCT v2.id_attivita,
    v1.id_stabilimento AS org_id,
    v1.id_linea_produttiva AS id_rel_ateco_attivita,
    v1.primario,
    v2.aggregazione AS categoria,
    v2.attivita AS linea_attivita,
    v2.codice_attivita AS codice_istat,
    v1.id,
    v1.enabled,
    v2.macroarea
   FROM suap_ric_scia_relazione_stabilimento_linee_produttive v1
     LEFT JOIN ml8_linee_attivita_nuove v2 ON v1.id_linea_produttiva = v2.id_nuova_linea_attivita
  WHERE v1.enabled;

ALTER TABLE public.suap_ric_linee_attivita_stabilimenti_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.suap_ric_linee_attivita_stabilimenti_view TO postgres;
GRANT SELECT ON TABLE public.suap_ric_linee_attivita_stabilimenti_view TO report;

-- View: public.suap_ric_scia_linee_attivita_stabilimenti_view

-- DROP VIEW public.suap_ric_scia_linee_attivita_stabilimenti_view;

CREATE OR REPLACE VIEW public.suap_ric_scia_linee_attivita_stabilimenti_view AS 
 SELECT stab.alt_id,
    v1.id_linea_produttiva AS id_rel_ateco_attivita,
    v1.primario,
    v2.aggregazione AS categoria,
    v2.attivita AS linea_attivita,
    v2.codice_attivita AS codice_istat,
    v1.id,
    v1.enabled,
    v2.macroarea
   FROM suap_ric_scia_relazione_stabilimento_linee_produttive v1
     JOIN ml8_linee_attivita_nuove v2 ON v1.id_linea_produttiva = v2.id_nuova_linea_attivita
     LEFT JOIN suap_ric_scia_stabilimento stab ON stab.id = v1.id_stabilimento;

ALTER TABLE public.suap_ric_scia_linee_attivita_stabilimenti_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.suap_ric_scia_linee_attivita_stabilimenti_view TO postgres;
GRANT SELECT ON TABLE public.suap_ric_scia_linee_attivita_stabilimenti_view TO usr_ro;
GRANT SELECT ON TABLE public.suap_ric_scia_linee_attivita_stabilimenti_view TO report;


CREATE OR REPLACE VIEW public.master_list_suap_allegati_linee_attivita AS 
 SELECT DISTINCT allegato.id,
    allegato.identificativo_allegato,
    allegato.descrizione_allegato,
    allegato.tipo_linea_produttiva,
    gr.cod_gruppo,
    la.id_nuova_linea_attivita
   FROM ml8_linee_attivita_nuove la
     JOIN master_list_suap_allegati_procedure_relazione lag ON la.id_nuova_linea_attivita = lag.id_master_list_suap
     JOIN master_list_relazione_gruppi_allegati lagd ON lagd.id_gruppo = lag.id_master_list_suap_gruppo_allegati
     JOIN master_list_gruppi_allegati gr ON gr.id_gruppo = lagd.id_gruppo
     JOIN master_list_suap_allegati_procedure allegato ON allegato.id = lagd.id_allegato;
     
     

CREATE OR REPLACE VIEW public.bdn_cu_chiusi_sicurezza_alimentare_b11_org_opu AS 
 SELECT DISTINCT ticket.id_bdn,
    ticket.id_bdn_b11,
    o.name AS ragione_sociale,
    o.org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || comuni1.codiceistatasl_old::text
            ELSE NULL::text
        END AS codice_asl,
    o.account_number AS codice_azienda,
    o.partita_iva AS id_fiscale_allevamento,
        CASE
            WHEN nucleo.nucleo_ispettivo::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%forestale% '::text THEN '002'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%NAS% '::text THEN '001'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%veterinari% '::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
    o.cf_detentore AS id_fiscale_detentore,
    op.tipo_produzione,
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END AS codice_orientamento,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
    ticket.data_estrazione_b11,
    ticket.data_import_b11,
    ticket.esito_import_b11,
    ticket.descrizione_errore_b11,
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
    'S'::text AS esito_controllo,
    o.specie_allev,
    '0'::text || o.specie_allevamento::text AS specie_allevamento,
        CASE
            WHEN (o.specie_allev::text ~~* 'gallus'::text OR o.specie_allev::text ~~* 'oche'::text OR o.specie_allev::text ~~* 'fagiani'::text) AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso ~~* '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END AS flag_copia_checklist,
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END AS flag_esito_sa,
    lcm.code AS id_alleg,
    '-1'::integer AS id_stabilimento
   FROM ticket
     JOIN organization o ON ticket.org_id = o.org_id
     LEFT JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 1
     LEFT JOIN comuni1 ON comuni1.nome::text ~~* oa.city::text
     LEFT JOIN nucleo_ispettivo_mv nucleo ON nucleo.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = o.specie_allevamento
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     LEFT JOIN lookup_piano_monitoraggio lpiani ON lpiani.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = o.asl_old
     LEFT JOIN orientamenti_produttivi op ON op.specie_allevata = o.specie_allev::text AND op.descrizione_tipo_produzione = o.tipologia_strutt AND o.orientamento_prod = op.descrizione_codice_orientamento
     LEFT JOIN lookup_specie_allevata specie ON specie.description::text = o.specie_allev::text
  WHERE ticket.tipologia = 3 AND (ticket.closed IS NOT NULL OR ticket.closed_nolista) AND o.tipologia = 2 AND lpiani.codice_interno = 1483 AND (ticket.esito_import_b11 !~~* 'OK'::text OR ticket.esito_import_b11 IS NULL) AND ticket.trashed_date IS NULL AND o.trashed_date IS NULL AND ist.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) >= 2015::double precision AND ((( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 OR lcm.code IS NULL OR lcm.code = 20)
  GROUP BY ticket.id_bdn, ticket.id_bdn_b11, o.name, o.org_id, ticket.ticketid, ticket.assigned_date, nucleo.nucleo_ispettivo, nucleo.nucleo_ispettivo_due, nucleo.nucleo_ispettivo_tre, nucleo.nucleo_ispettivo_quattro, nucleo.nucleo_ispettivo_cinque, nucleo.nucleo_ispettivo_sei, nucleo.nucleo_ispettivo_sette, nucleo.nucleo_ispettivo_otto, nucleo.nucleo_ispettivo_nove, nucleo.nucleo_ispettivo_dieci, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || comuni1.codiceistatasl_old::text
            ELSE NULL::text
        END), o.account_number, ('0'::text || o.specie_allevamento::text), o.partita_iva, (
        CASE
            WHEN nucleo.nucleo_ispettivo::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%forestale% '::text THEN '002'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%NAS% '::text THEN '001'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%veterinari% '::text THEN '003'::text
            ELSE 'n.d.'::text
        END), (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), o.cf_detentore, op.tipo_produzione, (
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END), ticket.data_estrazione_b11, ticket.data_import_b11, ticket.esito_import_b11, ticket.descrizione_errore_b11, (
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN o.specie_allev::text ~~* '%suini%'::text THEN '2'::text
            WHEN o.specie_allev::text ~~* '%broiler%'::text THEN '5'::text
            WHEN o.specie_allev::text ~~* '%gallus%'::text THEN '1'::text
            ELSE '4'::text
        END), 'N'::text, (
        CASE
            WHEN o.specie_allev::text ~~* 'gallus'::text AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END), (
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END), lcm.code
UNION
 SELECT DISTINCT ticket.id_bdn,
    ticket.id_bdn_b11,
    o.ragione_sociale,
    '-1'::integer AS org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || c.codiceistatasl_old::text
            ELSE NULL::text
        END AS codice_asl,
    COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente) AS codice_azienda,
    btrim(COALESCE(o.partita_iva::text, o.codice_fiscale_impresa::text)) AS id_fiscale_allevamento,
        CASE
            WHEN qual.description::text ~~* '%forestale% '::text THEN '002'::text
            WHEN qual.description::text ~~* '%NAS%'::text THEN '001'::text
            WHEN qual.description::text ~~* '%veterinari%'::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
    ''::text AS id_fiscale_detentore,
    l.decodifica_tipo_produzione_bdn AS tipo_produzione,
    l.decodifica_codice_orientamento_bdn AS codice_orientamento,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
    ticket.data_estrazione_b11,
    ticket.data_import_b11,
    ticket.esito_import_b11,
    ticket.descrizione_errore_b11,
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
    'S'::text AS esito_controllo,
    lsa.description AS specie_allev,
        CASE
            WHEN lsa.description::text !~~* 'pesci'::text THEN '0'::text || l.decodifica_specie_bdn
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN (lsa.description::text ~~* 'gallus'::text OR lsa.description::text ~~* 'oche'::text OR lsa.description::text ~~* 'fagiani'::text) AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso ~~* '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END AS flag_copia_checklist,
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END AS flag_esito_sa,
    lcm.code AS id_alleg,
    s.id AS id_stabilimento
   FROM ticket
     JOIN opu_stabilimento s ON ticket.id_stabilimento = s.id
     LEFT JOIN opu_operatore o ON o.id = s.id_operatore
     LEFT JOIN opu_indirizzo ind ON ind.id = s.id_indirizzo
     LEFT JOIN comuni1 c ON c.id = ind.comune
     JOIN opu_linee_attivita_controlli_ufficiali olacu ON olacu.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN opu_relazione_stabilimento_linee_produttive rel ON rel.id = olacu.id_linea_attivita
     LEFT JOIN ml8_linee_attivita_nuove l ON l.id_nuova_linea_attivita = rel.id_linea_produttiva
     LEFT JOIN nucleo_ispettivo_mv nucleo ON nucleo.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = l.decodifica_specie_bdn::integer
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     LEFT JOIN lookup_piano_monitoraggio lpiani ON lpiani.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = s.id_asl
     LEFT JOIN lookup_specie_allevata lsa ON lsa.short_description::text = l.decodifica_specie_bdn
  WHERE ticket.tipologia = 3 AND (ticket.closed IS NOT NULL OR ticket.closed_nolista) AND lpiani.codice_interno = 1483 AND (ticket.esito_import_b11 !~~* 'OK'::text OR ticket.esito_import_b11 IS NULL) AND ticket.trashed_date IS NULL AND o.trashed_date IS NULL AND s.trashed_date IS NULL AND ist.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) >= 2015::double precision AND ((( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 OR lcm.code IS NULL OR lcm.code = 20)
  GROUP BY ticket.id_bdn, ticket.id_bdn_b11, o.ragione_sociale, s.id, ticket.ticketid, ticket.assigned_date, nucleo.nucleo_ispettivo, nucleo.nucleo_ispettivo_due, nucleo.nucleo_ispettivo_tre, nucleo.nucleo_ispettivo_quattro, nucleo.nucleo_ispettivo_cinque, nucleo.nucleo_ispettivo_sei, nucleo.nucleo_ispettivo_sette, nucleo.nucleo_ispettivo_otto, nucleo.nucleo_ispettivo_nove, nucleo.nucleo_ispettivo_dieci, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || c.codiceistatasl_old::text
            ELSE NULL::text
        END), (COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente)), ('0'::text || l.decodifica_specie_bdn), o.partita_iva, o.codice_fiscale_impresa, qual.description, l.decodifica_codice_orientamento_bdn, lsa.description, (
        CASE
            WHEN nucleo.nucleo_ispettivo::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%forestale% '::text THEN '002'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%NAS% '::text THEN '001'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%veterinari% '::text THEN '003'::text
            ELSE 'n.d.'::text
        END), (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), l.decodifica_tipo_produzione_bdn, (
        CASE
            WHEN l.decodifica_codice_orientamento_bdn IS NOT NULL THEN l.decodifica_codice_orientamento_bdn
            ELSE ''::text
        END), ticket.data_estrazione_b11, ticket.data_import_b11, ticket.esito_import_b11, ticket.descrizione_errore_b11, (
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN lsa.description::text ~~* '%suini%'::text THEN '2'::text
                WHEN lsa.description::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN lsa.description::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lpiani.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END), 'N'::text, (
        CASE
            WHEN lsa.description::text ~~* 'gallus'::text AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END), (
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END), lcm.code;

ALTER TABLE public.bdn_cu_chiusi_sicurezza_alimentare_b11_org_opu
  OWNER TO postgres;
GRANT ALL ON TABLE public.bdn_cu_chiusi_sicurezza_alimentare_b11_org_opu TO postgres;
GRANT SELECT ON TABLE public.bdn_cu_chiusi_sicurezza_alimentare_b11_org_opu TO usr_ro;
GRANT SELECT ON TABLE public.bdn_cu_chiusi_sicurezza_alimentare_b11_org_opu TO report;

-- View: public.bdn_cu_chiusi_sicurezza_alimentare_b11

-- DROP VIEW public.bdn_cu_chiusi_sicurezza_alimentare_b11;

CREATE OR REPLACE VIEW public.bdn_cu_chiusi_sicurezza_alimentare_b11 AS 
 SELECT DISTINCT ticket.id_bdn,
    ticket.id_bdn_b11,
    o.name AS ragione_sociale,
    o.org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || oa.codiceistatasl_old
            ELSE NULL::text
        END AS codice_asl,
    o.account_number AS codice_azienda,
    o.partita_iva AS id_fiscale_allevamento,
        CASE
            WHEN nucleo.nucleo_ispettivo::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%forestale% '::text THEN '002'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%NAS% '::text THEN '001'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%veterinari% '::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
    o.cf_detentore AS id_fiscale_detentore,
    op.tipo_produzione,
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END AS codice_orientamento,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
    ticket.data_estrazione_b11,
    ticket.data_import_b11,
    ticket.esito_import_b11,
    ticket.descrizione_errore_b11,
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
    'S'::text AS esito_controllo,
    o.specie_allev,
    '0'::text || o.specie_allevamento::text AS specie_allevamento,
        CASE
            WHEN (o.specie_allev::text ~~* 'gallus'::text OR o.specie_allev::text ~~* 'oche'::text OR o.specie_allev::text ~~* 'fagiani'::text) AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso ~~* '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END AS flag_copia_checklist,
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END AS flag_esito_sa,
    lcm.code AS id_alleg,
    '-1'::integer AS id_stabilimento
   FROM ticket
     JOIN organization o ON ticket.org_id = o.org_id
     LEFT JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 1
     LEFT JOIN nucleo_ispettivo_mv nucleo ON nucleo.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = o.specie_allevamento
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     LEFT JOIN lookup_piano_monitoraggio lpiani ON lpiani.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = o.asl_old
     LEFT JOIN orientamenti_produttivi op ON op.specie_allevata = o.specie_allev::text AND op.descrizione_tipo_produzione = o.tipologia_strutt AND o.orientamento_prod = op.descrizione_codice_orientamento
     LEFT JOIN lookup_specie_allevata specie ON specie.description::text = o.specie_allev::text
  WHERE ticket.tipologia = 3 AND (ticket.closed IS NOT NULL OR ticket.closed_nolista) AND o.tipologia = 2 AND lpiani.codice_interno = 1483 AND (ticket.esito_import_b11 !~~* 'OK'::text OR ticket.esito_import_b11 IS NULL) AND ticket.trashed_date IS NULL AND o.trashed_date IS NULL AND ist.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) >= 2016::double precision AND ((( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 OR lcm.code IS NULL OR (lcm.code = ANY (ARRAY[20, 22])))
  GROUP BY ticket.id_bdn, ticket.id_bdn_b11, o.name, o.org_id, ticket.ticketid, ticket.assigned_date, nucleo.nucleo_ispettivo, nucleo.nucleo_ispettivo_due, nucleo.nucleo_ispettivo_tre, nucleo.nucleo_ispettivo_quattro, nucleo.nucleo_ispettivo_cinque, nucleo.nucleo_ispettivo_sei, nucleo.nucleo_ispettivo_sette, nucleo.nucleo_ispettivo_otto, nucleo.nucleo_ispettivo_nove, nucleo.nucleo_ispettivo_dieci, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || oa.codiceistatasl_old
            ELSE NULL::text
        END), o.account_number, ('0'::text || o.specie_allevamento::text), o.partita_iva, (
        CASE
            WHEN nucleo.nucleo_ispettivo::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%forestale% '::text THEN '002'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%NAS% '::text THEN '001'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%veterinari% '::text THEN '003'::text
            ELSE 'n.d.'::text
        END), (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), o.cf_detentore, op.tipo_produzione, (
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END), ticket.data_estrazione_b11, ticket.data_import_b11, ticket.esito_import_b11, ticket.descrizione_errore_b11, (
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN o.specie_allev::text ~~* '%suini%'::text THEN '2'::text
            WHEN o.specie_allev::text ~~* '%broiler%'::text THEN '5'::text
            WHEN o.specie_allev::text ~~* '%gallus%'::text THEN '1'::text
            ELSE '4'::text
        END), 'N'::text, (
        CASE
            WHEN o.specie_allev::text ~~* 'gallus'::text AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END), (
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END), lcm.code, o.specie_allev, o.codice_tipo_allevamento
UNION
 SELECT DISTINCT ticket.id_bdn,
    ticket.id_bdn_b11,
    o.ragione_sociale,
    '-1'::integer AS org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || c.codiceistatasl_old::text
            ELSE NULL::text
        END AS codice_asl,
    COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente) AS codice_azienda,
    btrim(COALESCE(o.partita_iva::text, o.codice_fiscale_impresa::text)) AS id_fiscale_allevamento,
        CASE
            WHEN qual.description::text ~~* '%forestale% '::text THEN '002'::text
            WHEN qual.description::text ~~* '%NAS%'::text THEN '001'::text
            WHEN qual.description::text ~~* '%veterinari%'::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
    ''::text AS id_fiscale_detentore,
    l.decodifica_tipo_produzione_bdn AS tipo_produzione,
    l.decodifica_codice_orientamento_bdn AS codice_orientamento,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
    ticket.data_estrazione_b11,
    ticket.data_import_b11,
    ticket.esito_import_b11,
    ticket.descrizione_errore_b11,
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
    'S'::text AS esito_controllo,
    lsa.description AS specie_allev,
        CASE
            WHEN lsa.description::text !~~* 'pesci'::text THEN '0'::text || l.decodifica_specie_bdn
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN (lsa.description::text ~~* 'gallus'::text OR lsa.description::text ~~* 'oche'::text OR lsa.description::text ~~* 'fagiani'::text) AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso ~~* '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END AS flag_copia_checklist,
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END AS flag_esito_sa,
    lcm.code AS id_alleg,
    s.id AS id_stabilimento
   FROM ticket
     JOIN opu_stabilimento s ON ticket.id_stabilimento = s.id
     LEFT JOIN opu_operatore o ON o.id = s.id_operatore
     LEFT JOIN opu_indirizzo ind ON ind.id = s.id_indirizzo
     LEFT JOIN comuni1 c ON c.id = ind.comune
     JOIN opu_linee_attivita_controlli_ufficiali olacu ON olacu.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN opu_relazione_stabilimento_linee_produttive rel ON rel.id = olacu.id_linea_attivita
     LEFT JOIN ml8_linee_attivita_nuove l ON l.id_nuova_linea_attivita = rel.id_linea_produttiva
     LEFT JOIN nucleo_ispettivo_mv nucleo ON nucleo.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = l.decodifica_specie_bdn::integer
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     LEFT JOIN lookup_piano_monitoraggio lpiani ON lpiani.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = s.id_asl
     LEFT JOIN lookup_specie_allevata lsa ON lsa.short_description::text = l.decodifica_specie_bdn
  WHERE ticket.tipologia = 3 AND (ticket.closed IS NOT NULL OR ticket.closed_nolista) AND lpiani.codice_interno = 1483 AND (ticket.esito_import_b11 !~~* 'OK'::text OR ticket.esito_import_b11 IS NULL) AND ticket.trashed_date IS NULL AND o.trashed_date IS NULL AND s.trashed_date IS NULL AND ist.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) >= 2016::double precision AND ((( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 OR lcm.code IS NULL OR lcm.code = 20)
  GROUP BY ticket.id_bdn, ticket.id_bdn_b11, o.ragione_sociale, s.id, ticket.ticketid, ticket.assigned_date, nucleo.nucleo_ispettivo, nucleo.nucleo_ispettivo_due, nucleo.nucleo_ispettivo_tre, nucleo.nucleo_ispettivo_quattro, nucleo.nucleo_ispettivo_cinque, nucleo.nucleo_ispettivo_sei, nucleo.nucleo_ispettivo_sette, nucleo.nucleo_ispettivo_otto, nucleo.nucleo_ispettivo_nove, nucleo.nucleo_ispettivo_dieci, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || c.codiceistatasl_old::text
            ELSE NULL::text
        END), (COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente)), ('0'::text || l.decodifica_specie_bdn), o.partita_iva, o.codice_fiscale_impresa, qual.description, l.decodifica_codice_orientamento_bdn, lsa.description, (
        CASE
            WHEN nucleo.nucleo_ispettivo::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%forestale% '::text THEN '002'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%NAS% '::text THEN '001'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%veterinari% '::text THEN '003'::text
            ELSE 'n.d.'::text
        END), (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), l.decodifica_tipo_produzione_bdn, (
        CASE
            WHEN l.decodifica_codice_orientamento_bdn IS NOT NULL THEN l.decodifica_codice_orientamento_bdn
            ELSE ''::text
        END), ticket.data_estrazione_b11, ticket.data_import_b11, ticket.esito_import_b11, ticket.descrizione_errore_b11, (
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN lsa.description::text ~~* '%suini%'::text THEN '2'::text
                WHEN lsa.description::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN lsa.description::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lpiani.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END), 'N'::text, (
        CASE
            WHEN lsa.description::text ~~* 'gallus'::text AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END), (
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END), lcm.code;

ALTER TABLE public.bdn_cu_chiusi_sicurezza_alimentare_b11
  OWNER TO postgres;
GRANT ALL ON TABLE public.bdn_cu_chiusi_sicurezza_alimentare_b11 TO postgres;
GRANT SELECT ON TABLE public.bdn_cu_chiusi_sicurezza_alimentare_b11 TO usr_ro;
GRANT SELECT ON TABLE public.bdn_cu_chiusi_sicurezza_alimentare_b11 TO report;

-- View: public.bdn_cu_chiusi_benessere_animale

-- DROP VIEW public.bdn_cu_chiusi_benessere_animale;

CREATE OR REPLACE VIEW public.bdn_cu_chiusi_benessere_animale AS 
 SELECT DISTINCT sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) AS punteggio,
    ist.id_alleg,
    ticket.id_bdn,
        CASE
            WHEN lcm.codice_specie = 1211 THEN '0121'::text
            WHEN lcm.codice_specie = 1461 THEN '0146'::text
            WHEN lcm.codice_specie IS NOT NULL AND (lcm.codice_specie <> ALL (ARRAY[1211, 1461])) AND lcm.codice_specie > 0 THEN '0'::text || lcm.codice_specie::text
            ELSE '0'::text || o.specie_allevamento::text
        END AS codice_specie_chk_bns,
    o.name AS ragione_sociale,
    o.org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || oa.codiceistatasl_old
            ELSE NULL::text
        END AS codice_asl,
    upper(o.account_number::text) AS codice_azienda,
    btrim(o.partita_iva::text) AS id_fiscale_allevamento,
        CASE
            WHEN qual.description::text ~~* '%forestale% '::text THEN '002'::text
            WHEN qual.description::text ~~* '%NAS%'::text THEN '001'::text
            WHEN qual.description::text ~~* '%veterinari%'::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
    false AS flag_cee,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END AS flag_extra_piano,
    o.cf_detentore AS id_fiscale_detentore,
    op.tipo_produzione,
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END AS codice_orientamento,
    'N.D'::text AS num_capannoni_suini,
    'N.D'::text AS num_capannoni_attivi_suini,
    'N.D'::text AS num_box_suini,
    'N.D'::text AS num_box_attivi_suini,
    'N.D'::text AS num_vitelli_max,
    'N.D'::text AS num_vitelli_presenti,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
        CASE
            WHEN sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) <= 0 OR sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) IS NULL THEN 'S'::text
            ELSE 'N'::text
        END AS esito_controllo,
    o.specie_allev,
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN o.specie_allev::text ~~* '%suini%'::text THEN '2'::text
                WHEN o.specie_allev::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN o.specie_allev::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END AS tipo_allegato,
        CASE
            WHEN o.specie_allev::text !~~* 'pesci'::text THEN '0'::text || o.specie_allevamento::text
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END AS flag_vitelli,
        CASE
            WHEN (o.specie_allev::text ~~* 'gallus'::text OR o.specie_allev::text ~~* 'oche'::text OR o.specie_allev::text ~~* 'fagiani'::text) AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso = '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
    tipi.pianomonitoraggio,
    '-1'::integer AS id_stabilimento
   FROM ticket
     JOIN organization o ON ticket.org_id = o.org_id
     JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 1 AND oa.city IS NOT NULL
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = o.specie_allevamento
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     JOIN lookup_piano_monitoraggio lp ON lp.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = o.asl_old
     LEFT JOIN orientamenti_produttivi op ON op.specie_allevata = o.specie_allev::text AND op.descrizione_tipo_produzione = o.tipologia_strutt AND o.orientamento_prod = op.descrizione_codice_orientamento
     LEFT JOIN lookup_specie_allevata specie ON specie.description::text = o.specie_allev::text
  WHERE ticket.tipologia = 3 AND o.account_number IS NOT NULL AND (lp.codice_interno = ANY (ARRAY[982, 983])) AND (lcm.code IS NULL OR lcm.code <> 20) AND (ticket.closed IS NOT NULL OR ticket.closed_nolista) AND (ticket.esito_import IS NULL OR ticket.esito_import = ''::text OR ticket.esito_import ~~* '%KO%'::text) AND o.tipologia = 2 AND ticket.trashed_date IS NULL AND o.trashed_date IS NULL AND ticket.assigned_date > '2015-12-31 00:00:00'::timestamp without time zone
  GROUP BY ticket.id_bdn, o.name, o.org_id, ticket.ticketid, ticket.assigned_date, qual.description, tipi.pianomonitoraggio, (
        CASE
            WHEN lcm.codice_specie = 1211 THEN '0121'::text
            WHEN lcm.codice_specie = 1461 THEN '0146'::text
            WHEN lcm.codice_specie IS NOT NULL AND (lcm.codice_specie <> ALL (ARRAY[1211, 1461])) AND lcm.codice_specie > 0 THEN '0'::text || lcm.codice_specie::text
            ELSE '0'::text || o.specie_allevamento::text
        END), (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || oa.codiceistatasl_old
            ELSE NULL::text
        END), (upper(o.account_number::text)), ('0'::text || o.specie_allevamento::text), o.partita_iva, (
        CASE
            WHEN ticket.nucleo_ispettivo = 1 OR ticket.nucleo_ispettivo_due = 1 OR ticket.nucleo_ispettivo_tre = 1 OR ticket.nucleo_ispettivo_quattro = 1 OR ticket.nucleo_ispettivo_cinque = 1 OR ticket.nucleo_ispettivo_sei = 1 OR ticket.nucleo_ispettivo_sette = 1 OR ticket.nucleo_ispettivo_otto = 1 OR ticket.nucleo_ispettivo_nove = 1 OR ticket.nucleo_ispettivo_dieci = 1 THEN '003'::text
            WHEN ticket.nucleo_ispettivo = 8 OR ticket.nucleo_ispettivo_due = 8 OR ticket.nucleo_ispettivo_tre = 8 OR ticket.nucleo_ispettivo_quattro = 8 OR ticket.nucleo_ispettivo_cinque = 8 OR ticket.nucleo_ispettivo_sei = 8 OR ticket.nucleo_ispettivo_sette = 8 OR ticket.nucleo_ispettivo_otto = 8 OR ticket.nucleo_ispettivo_nove = 8 OR ticket.nucleo_ispettivo_dieci = 8 THEN '001'::text
            WHEN ticket.nucleo_ispettivo = 13 OR ticket.nucleo_ispettivo_due = 13 OR ticket.nucleo_ispettivo_tre = 13 OR ticket.nucleo_ispettivo_quattro = 13 OR ticket.nucleo_ispettivo_cinque = 13 OR ticket.nucleo_ispettivo_sei = 13 OR ticket.nucleo_ispettivo_sette = 13 OR ticket.nucleo_ispettivo_otto = 13 OR ticket.nucleo_ispettivo_nove = 13 OR ticket.nucleo_ispettivo_dieci = 13 THEN '002'::text
            ELSE 'n.d'::text
        END), false::boolean, (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), (
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END), o.cf_detentore, op.tipo_produzione, (
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END), 'N.D'::text, ticket.data_estrazione, ticket.data_import, ticket.esito_import, ticket.descrizione_errore, (
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN o.specie_allev::text ~~* '%suini%'::text THEN '2'::text
                WHEN o.specie_allev::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN o.specie_allev::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END), ist.id_alleg, (
        CASE
            WHEN o.specie_allev::text ~~* 'gallus'::text AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END), (
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END)
UNION
 SELECT DISTINCT sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) AS punteggio,
    ist.id_alleg,
    ticket.id_bdn,
        CASE
            WHEN lcm.codice_specie = 1211 THEN '0121'::text
            WHEN lcm.codice_specie = 1461 THEN '0146'::text
            WHEN lcm.codice_specie IS NOT NULL AND (lcm.codice_specie <> ALL (ARRAY[1211, 1461])) AND lcm.codice_specie > 0 THEN '0'::text || lcm.codice_specie::text
            ELSE ml.decodifica_specie_bdn
        END AS codice_specie_chk_bns,
    o.ragione_sociale,
    '-1'::integer AS org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || c.codiceistatasl_old::text
            ELSE NULL::text
        END AS codice_asl,
    upper(COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente)) AS codice_azienda,
    btrim(COALESCE(sf.codice_fiscale, COALESCE(o.partita_iva::text, o.codice_fiscale_impresa::text)::character varying)::text) AS id_fiscale_allevamento,
        CASE
            WHEN qual.description::text ~~* '%forestale% '::text THEN '002'::text
            WHEN qual.description::text ~~* '%NAS%'::text THEN '001'::text
            WHEN qual.description::text ~~* '%veterinari%'::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
    false AS flag_cee,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END AS flag_extra_piano,
    sf.codice_fiscale::text AS id_fiscale_detentore,
    ml.decodifica_tipo_produzione_bdn AS tipo_produzione,
    ml.decodifica_codice_orientamento_bdn AS codice_orientamento,
    'N.D'::text AS num_capannoni_suini,
    'N.D'::text AS num_capannoni_attivi_suini,
    'N.D'::text AS num_box_suini,
    'N.D'::text AS num_box_attivi_suini,
    'N.D'::text AS num_vitelli_max,
    'N.D'::text AS num_vitelli_presenti,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
        CASE
            WHEN sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) <= 0 OR sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) IS NULL THEN 'S'::text
            ELSE 'N'::text
        END AS esito_controllo,
    lsa.description AS specie_allev,
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN lsa.description::text ~~* '%suini%'::text THEN '2'::text
                WHEN lsa.description::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN lsa.description::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END AS tipo_allegato,
        CASE
            WHEN lsa.description::text !~~* 'pesci'::text THEN '0'::text || ml.decodifica_specie_bdn
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END AS flag_vitelli,
        CASE
            WHEN (lsa.description::text ~~* 'gallus'::text OR lsa.description::text ~~* 'oche'::text OR lsa.description::text ~~* 'fagiani'::text) AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso = '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
    tipi.pianomonitoraggio,
    s.id AS id_stabilimento
   FROM ticket
     JOIN opu_stabilimento s ON ticket.id_stabilimento = s.id
     LEFT JOIN opu_operatore o ON o.id = s.id_operatore
     LEFT JOIN opu_rel_operatore_soggetto_fisico rsf ON rsf.id_operatore = o.id
     LEFT JOIN opu_soggetto_fisico sf ON sf.id = rsf.id_soggetto_fisico
     LEFT JOIN opu_indirizzo ind ON ind.id = s.id_indirizzo
     LEFT JOIN comuni1 c ON c.id = ind.comune
     JOIN opu_linee_attivita_controlli_ufficiali olacu ON olacu.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN opu_relazione_stabilimento_linee_produttive rel ON rel.id = olacu.id_linea_attivita
     LEFT JOIN ml8_linee_attivita_nuove l ON l.id_nuova_linea_attivita = rel.id_linea_produttiva
     JOIN master_list_suap ml ON ml.id = l.id_attivita
     LEFT JOIN orientamenti_produttivi ori ON ml.decodifica_tipo_produzione_bdn = ori.tipo_produzione
     LEFT JOIN lookup_specie_allevata lsa ON lsa.short_description::integer = ml.decodifica_specie_bdn::integer
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = ml.decodifica_specie_bdn::integer
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     JOIN lookup_piano_monitoraggio lp ON lp.code = tipi.pianomonitoraggio
  WHERE ticket.tipologia = 3 AND COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente) IS NOT NULL AND (lcm.code IS NULL OR lcm.code <> 20) AND (ticket.closed IS NOT NULL OR ticket.closed_nolista) AND (ticket.esito_import IS NULL OR ticket.esito_import = ''::text OR ticket.esito_import ~~* '%KO%'::text) AND (lp.codice_interno = ANY (ARRAY[982, 983])) AND ticket.trashed_date IS NULL AND ticket.assigned_date > '2015-12-31 00:00:00'::timestamp without time zone AND s.trashed_date IS NULL AND o.trashed_date IS NULL AND l.id_aggregazione = 37
  GROUP BY sf.codice_fiscale, ticket.id_bdn, o.ragione_sociale, s.id, ticket.ticketid, ticket.assigned_date, qual.description, tipi.pianomonitoraggio, (
        CASE
            WHEN lcm.codice_specie = 1211 THEN '0121'::text
            WHEN lcm.codice_specie = 1461 THEN '0146'::text
            WHEN lcm.codice_specie IS NOT NULL AND (lcm.codice_specie <> ALL (ARRAY[1211, 1461])) AND lcm.codice_specie > 0 THEN '0'::text || lcm.codice_specie::text
            ELSE ml.decodifica_specie_bdn
        END), o.codice_fiscale_impresa, ml.decodifica_codice_orientamento_bdn, lsa.description, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || c.codiceistatasl_old::text
            ELSE NULL::text
        END), (upper(COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente))), ('0'::text || ml.decodifica_specie_bdn), o.partita_iva, (
        CASE
            WHEN ticket.nucleo_ispettivo = 1 OR ticket.nucleo_ispettivo_due = 1 OR ticket.nucleo_ispettivo_tre = 1 OR ticket.nucleo_ispettivo_quattro = 1 OR ticket.nucleo_ispettivo_cinque = 1 OR ticket.nucleo_ispettivo_sei = 1 OR ticket.nucleo_ispettivo_sette = 1 OR ticket.nucleo_ispettivo_otto = 1 OR ticket.nucleo_ispettivo_nove = 1 OR ticket.nucleo_ispettivo_dieci = 1 THEN '003'::text
            WHEN ticket.nucleo_ispettivo = 8 OR ticket.nucleo_ispettivo_due = 8 OR ticket.nucleo_ispettivo_tre = 8 OR ticket.nucleo_ispettivo_quattro = 8 OR ticket.nucleo_ispettivo_cinque = 8 OR ticket.nucleo_ispettivo_sei = 8 OR ticket.nucleo_ispettivo_sette = 8 OR ticket.nucleo_ispettivo_otto = 8 OR ticket.nucleo_ispettivo_nove = 8 OR ticket.nucleo_ispettivo_dieci = 8 THEN '001'::text
            WHEN ticket.nucleo_ispettivo = 13 OR ticket.nucleo_ispettivo_due = 13 OR ticket.nucleo_ispettivo_tre = 13 OR ticket.nucleo_ispettivo_quattro = 13 OR ticket.nucleo_ispettivo_cinque = 13 OR ticket.nucleo_ispettivo_sei = 13 OR ticket.nucleo_ispettivo_sette = 13 OR ticket.nucleo_ispettivo_otto = 13 OR ticket.nucleo_ispettivo_nove = 13 OR ticket.nucleo_ispettivo_dieci = 13 THEN '002'::text
            ELSE 'n.d'::text
        END), false::boolean, (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), (
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END), ml.decodifica_tipo_produzione_bdn, (
        CASE
            WHEN ml.decodifica_codice_orientamento_bdn IS NOT NULL THEN ml.decodifica_codice_orientamento_bdn
            ELSE ''::text
        END), 'N.D'::text, ticket.data_estrazione, ticket.data_import, ticket.esito_import, ticket.descrizione_errore, (
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN lsa.description::text ~~* '%suini%'::text THEN '2'::text
                WHEN lsa.description::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN lsa.description::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END), ist.id_alleg, (
        CASE
            WHEN lsa.description::text ~~* 'gallus'::text AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END), (
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END);

ALTER TABLE public.bdn_cu_chiusi_benessere_animale
  OWNER TO postgres;
GRANT ALL ON TABLE public.bdn_cu_chiusi_benessere_animale TO postgres;
GRANT SELECT ON TABLE public.bdn_cu_chiusi_benessere_animale TO report;
GRANT SELECT ON TABLE public.bdn_cu_chiusi_benessere_animale TO usr_ro;

-- View: public.bdn_cu_candidati_benessere_animale_org_opu

-- DROP VIEW public.bdn_cu_candidati_benessere_animale_org_opu;

CREATE OR REPLACE VIEW public.bdn_cu_candidati_benessere_animale_org_opu AS 
 SELECT DISTINCT sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) AS punteggio,
    ist.id_alleg,
    ticket.id_bdn,
    lcm.codice_specie AS codice_specie_chk_bns,
    o.name AS ragione_sociale,
    o.org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || comuni1.codiceistatasl_old::text
            ELSE NULL::text
        END AS codice_asl,
    o.account_number AS codice_azienda,
    btrim(o.partita_iva::text) AS id_fiscale_allevamento,
        CASE
            WHEN qual.description::text ~~* '%forestale% '::text THEN '002'::text
            WHEN qual.description::text ~~* '%NAS%'::text THEN '001'::text
            WHEN qual.description::text ~~* '%veterinari%'::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
    false AS flag_cee,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END AS flag_extra_piano,
    o.cf_detentore AS id_fiscale_detentore,
    op.tipo_produzione,
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END AS codice_orientamento,
    'N.D'::text AS num_capannoni_suini,
    'N.D'::text AS num_capannoni_attivi_suini,
    'N.D'::text AS num_box_suini,
    'N.D'::text AS num_box_attivi_suini,
    'N.D'::text AS num_vitelli_max,
    'N.D'::text AS num_vitelli_presenti,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
        CASE
            WHEN sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) <= 0 OR sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) IS NULL THEN 'S'::text
            ELSE 'N'::text
        END AS esito_controllo,
    o.specie_allev,
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN o.specie_allev::text ~~* '%suini%'::text THEN '2'::text
                WHEN o.specie_allev::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN o.specie_allev::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END AS tipo_allegato,
        CASE
            WHEN o.specie_allev::text !~~* 'pesci'::text THEN '0'::text || o.specie_allevamento::text
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END AS flag_vitelli,
        CASE
            WHEN (o.specie_allev::text ~~* 'gallus'::text OR o.specie_allev::text ~~* 'oche'::text OR o.specie_allev::text ~~* 'fagiani'::text) AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso = '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
    tipi.pianomonitoraggio,
    '-1'::integer AS id_stabilimento
   FROM ticket
     JOIN organization o ON ticket.org_id = o.org_id
     JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 1
     LEFT JOIN comuni1 ON comuni1.nome::text ~~* oa.city::text
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = o.specie_allevamento
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     JOIN lookup_piano_monitoraggio lp ON lp.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = o.asl_old
     LEFT JOIN orientamenti_produttivi op ON op.specie_allevata = o.specie_allev::text AND op.descrizione_tipo_produzione = o.tipologia_strutt AND o.orientamento_prod = op.descrizione_codice_orientamento
     LEFT JOIN lookup_specie_allevata specie ON specie.description::text = o.specie_allev::text
  WHERE ticket.tipologia = 3 AND ticket.trashed_date IS NULL AND o.tipologia = 2 AND o.trashed_date IS NULL AND (lp.codice_interno = ANY (ARRAY[982, 983])) AND (lcm.code IS NULL OR lcm.code <> 20) AND date_part('year'::text, ticket.assigned_date) > 2015::double precision
  GROUP BY ticket.id_bdn, o.name, o.org_id, ticket.ticketid, ticket.assigned_date, qual.description, tipi.pianomonitoraggio, lcm.codice_specie, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || comuni1.codiceistatasl_old::text
            ELSE NULL::text
        END), o.account_number, ('0'::text || o.specie_allevamento::text), o.partita_iva, (
        CASE
            WHEN ticket.nucleo_ispettivo = 1 OR ticket.nucleo_ispettivo_due = 1 OR ticket.nucleo_ispettivo_tre = 1 OR ticket.nucleo_ispettivo_quattro = 1 OR ticket.nucleo_ispettivo_cinque = 1 OR ticket.nucleo_ispettivo_sei = 1 OR ticket.nucleo_ispettivo_sette = 1 OR ticket.nucleo_ispettivo_otto = 1 OR ticket.nucleo_ispettivo_nove = 1 OR ticket.nucleo_ispettivo_dieci = 1 THEN '003'::text
            WHEN ticket.nucleo_ispettivo = 8 OR ticket.nucleo_ispettivo_due = 8 OR ticket.nucleo_ispettivo_tre = 8 OR ticket.nucleo_ispettivo_quattro = 8 OR ticket.nucleo_ispettivo_cinque = 8 OR ticket.nucleo_ispettivo_sei = 8 OR ticket.nucleo_ispettivo_sette = 8 OR ticket.nucleo_ispettivo_otto = 8 OR ticket.nucleo_ispettivo_nove = 8 OR ticket.nucleo_ispettivo_dieci = 8 THEN '001'::text
            WHEN ticket.nucleo_ispettivo = 13 OR ticket.nucleo_ispettivo_due = 13 OR ticket.nucleo_ispettivo_tre = 13 OR ticket.nucleo_ispettivo_quattro = 13 OR ticket.nucleo_ispettivo_cinque = 13 OR ticket.nucleo_ispettivo_sei = 13 OR ticket.nucleo_ispettivo_sette = 13 OR ticket.nucleo_ispettivo_otto = 13 OR ticket.nucleo_ispettivo_nove = 13 OR ticket.nucleo_ispettivo_dieci = 13 THEN '002'::text
            ELSE 'n.d'::text
        END), false::boolean, (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), (
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END), o.cf_detentore, op.tipo_produzione, (
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END), 'N.D'::text, ticket.data_estrazione, ticket.data_import, ticket.esito_import, ticket.descrizione_errore, (
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN o.specie_allev::text ~~* '%suini%'::text THEN '2'::text
                WHEN o.specie_allev::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN o.specie_allev::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END), ist.id_alleg, (
        CASE
            WHEN o.specie_allev::text ~~* 'gallus'::text AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END), (
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END)
UNION
 SELECT DISTINCT sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) AS punteggio,
    ist.id_alleg,
    ticket.id_bdn,
    lcm.codice_specie AS codice_specie_chk_bns,
    o.ragione_sociale,
    '-1'::integer AS org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || c.istat::text
            ELSE NULL::text
        END AS codice_asl,
    COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente) AS codice_azienda,
    btrim(COALESCE(o.partita_iva::text, o.codice_fiscale_impresa::text)) AS id_fiscale_allevamento,
        CASE
            WHEN qual.description::text ~~* '%forestale% '::text THEN '002'::text
            WHEN qual.description::text ~~* '%NAS%'::text THEN '001'::text
            WHEN qual.description::text ~~* '%veterinari%'::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
    false AS flag_cee,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END AS flag_extra_piano,
    ''::text AS id_fiscale_detentore,
    l.decodifica_tipo_produzione_bdn AS tipo_produzione,
    l.decodifica_codice_orientamento_bdn AS codice_orientamento,
    'N.D'::text AS num_capannoni_suini,
    'N.D'::text AS num_capannoni_attivi_suini,
    'N.D'::text AS num_box_suini,
    'N.D'::text AS num_box_attivi_suini,
    'N.D'::text AS num_vitelli_max,
    'N.D'::text AS num_vitelli_presenti,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
        CASE
            WHEN sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) <= 0 OR sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) IS NULL THEN 'S'::text
            ELSE 'N'::text
        END AS esito_controllo,
    lsa.description AS specie_allev,
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN lsa.description::text ~~* '%suini%'::text THEN '2'::text
                WHEN lsa.description::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN lsa.description::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END AS tipo_allegato,
        CASE
            WHEN lsa.description::text !~~* 'pesci'::text THEN '0'::text || l.decodifica_specie_bdn
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END AS flag_vitelli,
        CASE
            WHEN (lsa.description::text ~~* 'gallus'::text OR lsa.description::text ~~* 'oche'::text OR lsa.description::text ~~* 'fagiani'::text) AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso = '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
    tipi.pianomonitoraggio,
    s.id AS id_stabilimento
   FROM ticket
     JOIN opu_stabilimento s ON ticket.id_stabilimento = s.id
     LEFT JOIN opu_operatore o ON o.id = s.id_operatore
     LEFT JOIN opu_indirizzo ind ON ind.id = s.id_indirizzo
     LEFT JOIN comuni1 c ON c.id = ind.comune
     JOIN opu_linee_attivita_controlli_ufficiali olacu ON olacu.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN opu_relazione_stabilimento_linee_produttive rel ON rel.id = olacu.id_linea_attivita
     LEFT JOIN ml8_linee_attivita_nuove l ON l.id_nuova_linea_attivita = rel.id_linea_produttiva
     LEFT JOIN orientamenti_produttivi ori ON l.decodifica_tipo_produzione_bdn = ori.tipo_produzione
     LEFT JOIN lookup_specie_allevata lsa ON lsa.short_description::text = l.decodifica_specie_bdn
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = l.decodifica_specie_bdn::integer
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     JOIN lookup_piano_monitoraggio lp ON lp.code = tipi.pianomonitoraggio
  WHERE ticket.tipologia = 3 AND (lcm.code IS NULL OR lcm.code <> 20) AND (lp.codice_interno = ANY (ARRAY[982, 983])) AND ticket.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) > 2015::double precision AND s.trashed_date IS NULL AND o.trashed_date IS NULL
  GROUP BY ticket.id_bdn, o.ragione_sociale, s.id, ticket.ticketid, ticket.assigned_date, qual.description, tipi.pianomonitoraggio, lcm.codice_specie, o.codice_fiscale_impresa, l.decodifica_codice_orientamento_bdn, lsa.description, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || c.istat::text
            ELSE NULL::text
        END), (COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente)), ('0'::text || l.decodifica_specie_bdn), o.partita_iva, (
        CASE
            WHEN ticket.nucleo_ispettivo = 1 OR ticket.nucleo_ispettivo_due = 1 OR ticket.nucleo_ispettivo_tre = 1 OR ticket.nucleo_ispettivo_quattro = 1 OR ticket.nucleo_ispettivo_cinque = 1 OR ticket.nucleo_ispettivo_sei = 1 OR ticket.nucleo_ispettivo_sette = 1 OR ticket.nucleo_ispettivo_otto = 1 OR ticket.nucleo_ispettivo_nove = 1 OR ticket.nucleo_ispettivo_dieci = 1 THEN '003'::text
            WHEN ticket.nucleo_ispettivo = 8 OR ticket.nucleo_ispettivo_due = 8 OR ticket.nucleo_ispettivo_tre = 8 OR ticket.nucleo_ispettivo_quattro = 8 OR ticket.nucleo_ispettivo_cinque = 8 OR ticket.nucleo_ispettivo_sei = 8 OR ticket.nucleo_ispettivo_sette = 8 OR ticket.nucleo_ispettivo_otto = 8 OR ticket.nucleo_ispettivo_nove = 8 OR ticket.nucleo_ispettivo_dieci = 8 THEN '001'::text
            WHEN ticket.nucleo_ispettivo = 13 OR ticket.nucleo_ispettivo_due = 13 OR ticket.nucleo_ispettivo_tre = 13 OR ticket.nucleo_ispettivo_quattro = 13 OR ticket.nucleo_ispettivo_cinque = 13 OR ticket.nucleo_ispettivo_sei = 13 OR ticket.nucleo_ispettivo_sette = 13 OR ticket.nucleo_ispettivo_otto = 13 OR ticket.nucleo_ispettivo_nove = 13 OR ticket.nucleo_ispettivo_dieci = 13 THEN '002'::text
            ELSE 'n.d'::text
        END), false::boolean, (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), (
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END), l.decodifica_tipo_produzione_bdn, (
        CASE
            WHEN l.decodifica_codice_orientamento_bdn IS NOT NULL THEN l.decodifica_codice_orientamento_bdn
            ELSE ''::text
        END), 'N.D'::text, ticket.data_estrazione, ticket.data_import, ticket.esito_import, ticket.descrizione_errore, (
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN lsa.description::text ~~* '%suini%'::text THEN '2'::text
                WHEN lsa.description::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN lsa.description::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END), ist.id_alleg, (
        CASE
            WHEN lsa.description::text ~~* 'gallus'::text AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END), (
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END);

ALTER TABLE public.bdn_cu_candidati_benessere_animale_org_opu
  OWNER TO postgres;
GRANT ALL ON TABLE public.bdn_cu_candidati_benessere_animale_org_opu TO postgres;
GRANT SELECT ON TABLE public.bdn_cu_candidati_benessere_animale_org_opu TO report;

-- View: public.bdn_cu_candidati_benessere_animale

-- DROP VIEW public.bdn_cu_candidati_benessere_animale;

CREATE OR REPLACE VIEW public.bdn_cu_candidati_benessere_animale AS 
 SELECT DISTINCT sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) AS punteggio,
    ist.id_alleg,
    ticket.id_bdn,
    lcm.codice_specie AS codice_specie_chk_bns,
    o.name AS ragione_sociale,
    o.org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || comuni1.codiceistatasl_old::text
            ELSE NULL::text
        END AS codice_asl,
    o.account_number AS codice_azienda,
    btrim(o.partita_iva::text) AS id_fiscale_allevamento,
        CASE
            WHEN qual.description::text ~~* '%forestale% '::text THEN '002'::text
            WHEN qual.description::text ~~* '%NAS%'::text THEN '001'::text
            WHEN qual.description::text ~~* '%veterinari%'::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
    false AS flag_cee,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END AS flag_extra_piano,
    o.cf_detentore AS id_fiscale_detentore,
    op.tipo_produzione,
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END AS codice_orientamento,
    'N.D'::text AS num_capannoni_suini,
    'N.D'::text AS num_capannoni_attivi_suini,
    'N.D'::text AS num_box_suini,
    'N.D'::text AS num_box_attivi_suini,
    'N.D'::text AS num_vitelli_max,
    'N.D'::text AS num_vitelli_presenti,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
        CASE
            WHEN sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) <= 0 OR sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) IS NULL THEN 'S'::text
            ELSE 'N'::text
        END AS esito_controllo,
    o.specie_allev,
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN o.specie_allev::text ~~* '%suini%'::text THEN '2'::text
                WHEN o.specie_allev::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN o.specie_allev::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END AS tipo_allegato,
        CASE
            WHEN o.specie_allev::text !~~* 'pesci'::text THEN '0'::text || o.specie_allevamento::text
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END AS flag_vitelli,
        CASE
            WHEN (o.specie_allev::text ~~* 'gallus'::text OR o.specie_allev::text ~~* 'oche'::text OR o.specie_allev::text ~~* 'fagiani'::text) AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso = '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
    tipi.pianomonitoraggio,
    '-1'::integer AS id_stabilimento
   FROM ticket
     JOIN organization o ON ticket.org_id = o.org_id
     JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 1
     LEFT JOIN comuni1 ON comuni1.nome::text ~~* oa.city::text
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = o.specie_allevamento
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     JOIN lookup_piano_monitoraggio lp ON lp.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = o.asl_old
     LEFT JOIN orientamenti_produttivi op ON op.specie_allevata = o.specie_allev::text AND op.descrizione_tipo_produzione = o.tipologia_strutt AND o.orientamento_prod = op.descrizione_codice_orientamento
     LEFT JOIN lookup_specie_allevata specie ON specie.description::text = o.specie_allev::text
  WHERE ticket.tipologia = 3 AND ticket.trashed_date IS NULL AND o.tipologia = 2 AND o.trashed_date IS NULL AND (lp.codice_interno = ANY (ARRAY[982, 983])) AND (lcm.code IS NULL OR lcm.code <> 20) AND date_part('year'::text, ticket.assigned_date) > 2015::double precision
  GROUP BY ticket.id_bdn, o.name, o.org_id, ticket.ticketid, ticket.assigned_date, qual.description, tipi.pianomonitoraggio, lcm.codice_specie, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || comuni1.codiceistatasl_old::text
            ELSE NULL::text
        END), o.account_number, ('0'::text || o.specie_allevamento::text), o.partita_iva, (
        CASE
            WHEN ticket.nucleo_ispettivo = 1 OR ticket.nucleo_ispettivo_due = 1 OR ticket.nucleo_ispettivo_tre = 1 OR ticket.nucleo_ispettivo_quattro = 1 OR ticket.nucleo_ispettivo_cinque = 1 OR ticket.nucleo_ispettivo_sei = 1 OR ticket.nucleo_ispettivo_sette = 1 OR ticket.nucleo_ispettivo_otto = 1 OR ticket.nucleo_ispettivo_nove = 1 OR ticket.nucleo_ispettivo_dieci = 1 THEN '003'::text
            WHEN ticket.nucleo_ispettivo = 8 OR ticket.nucleo_ispettivo_due = 8 OR ticket.nucleo_ispettivo_tre = 8 OR ticket.nucleo_ispettivo_quattro = 8 OR ticket.nucleo_ispettivo_cinque = 8 OR ticket.nucleo_ispettivo_sei = 8 OR ticket.nucleo_ispettivo_sette = 8 OR ticket.nucleo_ispettivo_otto = 8 OR ticket.nucleo_ispettivo_nove = 8 OR ticket.nucleo_ispettivo_dieci = 8 THEN '001'::text
            WHEN ticket.nucleo_ispettivo = 13 OR ticket.nucleo_ispettivo_due = 13 OR ticket.nucleo_ispettivo_tre = 13 OR ticket.nucleo_ispettivo_quattro = 13 OR ticket.nucleo_ispettivo_cinque = 13 OR ticket.nucleo_ispettivo_sei = 13 OR ticket.nucleo_ispettivo_sette = 13 OR ticket.nucleo_ispettivo_otto = 13 OR ticket.nucleo_ispettivo_nove = 13 OR ticket.nucleo_ispettivo_dieci = 13 THEN '002'::text
            ELSE 'n.d'::text
        END), false::boolean, (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), (
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END), o.cf_detentore, op.tipo_produzione, (
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END), 'N.D'::text, ticket.data_estrazione, ticket.data_import, ticket.esito_import, ticket.descrizione_errore, (
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN o.specie_allev::text ~~* '%suini%'::text THEN '2'::text
                WHEN o.specie_allev::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN o.specie_allev::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END), ist.id_alleg, (
        CASE
            WHEN o.specie_allev::text ~~* 'gallus'::text AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END), (
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END)
UNION
 SELECT DISTINCT sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) AS punteggio,
    ist.id_alleg,
    ticket.id_bdn,
    lcm.codice_specie AS codice_specie_chk_bns,
    o.ragione_sociale,
    '-1'::integer AS org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || c.istat::text
            ELSE NULL::text
        END AS codice_asl,
    COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente) AS codice_azienda,
    btrim(COALESCE(o.partita_iva::text, o.codice_fiscale_impresa::text)) AS id_fiscale_allevamento,
        CASE
            WHEN qual.description::text ~~* '%forestale% '::text THEN '002'::text
            WHEN qual.description::text ~~* '%NAS%'::text THEN '001'::text
            WHEN qual.description::text ~~* '%veterinari%'::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
    false AS flag_cee,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END AS flag_extra_piano,
    ''::text AS id_fiscale_detentore,
    l.decodifica_tipo_produzione_bdn AS tipo_produzione,
    l.decodifica_codice_orientamento_bdn AS codice_orientamento,
    'N.D'::text AS num_capannoni_suini,
    'N.D'::text AS num_capannoni_attivi_suini,
    'N.D'::text AS num_box_suini,
    'N.D'::text AS num_box_attivi_suini,
    'N.D'::text AS num_vitelli_max,
    'N.D'::text AS num_vitelli_presenti,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
        CASE
            WHEN sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) <= 0 OR sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) IS NULL THEN 'S'::text
            ELSE 'N'::text
        END AS esito_controllo,
    lsa.description AS specie_allev,
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN lsa.description::text ~~* '%suini%'::text THEN '2'::text
                WHEN lsa.description::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN lsa.description::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END AS tipo_allegato,
        CASE
            WHEN lsa.description::text !~~* 'pesci'::text THEN '0'::text || l.decodifica_specie_bdn
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END AS flag_vitelli,
        CASE
            WHEN (lsa.description::text ~~* 'gallus'::text OR lsa.description::text ~~* 'oche'::text OR lsa.description::text ~~* 'fagiani'::text) AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso = '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
    tipi.pianomonitoraggio,
    s.id AS id_stabilimento
   FROM ticket
     JOIN opu_stabilimento s ON ticket.id_stabilimento = s.id
     LEFT JOIN opu_operatore o ON o.id = s.id_operatore
     LEFT JOIN opu_indirizzo ind ON ind.id = s.id_indirizzo
     LEFT JOIN comuni1 c ON c.id = ind.comune
     JOIN opu_linee_attivita_controlli_ufficiali olacu ON olacu.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN opu_relazione_stabilimento_linee_produttive rel ON rel.id = olacu.id_linea_attivita
     LEFT JOIN ml8_linee_attivita_nuove l ON l.id_nuova_linea_attivita = rel.id_linea_produttiva
     LEFT JOIN orientamenti_produttivi ori ON l.decodifica_tipo_produzione_bdn = ori.tipo_produzione
     LEFT JOIN lookup_specie_allevata lsa ON lsa.short_description::text = l.decodifica_specie_bdn
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = l.decodifica_specie_bdn::integer
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     JOIN lookup_piano_monitoraggio lp ON lp.code = tipi.pianomonitoraggio
  WHERE ticket.tipologia = 3 AND (lcm.code IS NULL OR lcm.code <> 20) AND (lp.codice_interno = ANY (ARRAY[982, 983])) AND ticket.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) > 2015::double precision AND s.trashed_date IS NULL AND o.trashed_date IS NULL
  GROUP BY ticket.id_bdn, o.ragione_sociale, s.id, ticket.ticketid, ticket.assigned_date, qual.description, tipi.pianomonitoraggio, lcm.codice_specie, o.codice_fiscale_impresa, l.decodifica_codice_orientamento_bdn, lsa.description, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || c.istat::text
            ELSE NULL::text
        END), (COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente)), ('0'::text || l.decodifica_specie_bdn), o.partita_iva, (
        CASE
            WHEN ticket.nucleo_ispettivo = 1 OR ticket.nucleo_ispettivo_due = 1 OR ticket.nucleo_ispettivo_tre = 1 OR ticket.nucleo_ispettivo_quattro = 1 OR ticket.nucleo_ispettivo_cinque = 1 OR ticket.nucleo_ispettivo_sei = 1 OR ticket.nucleo_ispettivo_sette = 1 OR ticket.nucleo_ispettivo_otto = 1 OR ticket.nucleo_ispettivo_nove = 1 OR ticket.nucleo_ispettivo_dieci = 1 THEN '003'::text
            WHEN ticket.nucleo_ispettivo = 8 OR ticket.nucleo_ispettivo_due = 8 OR ticket.nucleo_ispettivo_tre = 8 OR ticket.nucleo_ispettivo_quattro = 8 OR ticket.nucleo_ispettivo_cinque = 8 OR ticket.nucleo_ispettivo_sei = 8 OR ticket.nucleo_ispettivo_sette = 8 OR ticket.nucleo_ispettivo_otto = 8 OR ticket.nucleo_ispettivo_nove = 8 OR ticket.nucleo_ispettivo_dieci = 8 THEN '001'::text
            WHEN ticket.nucleo_ispettivo = 13 OR ticket.nucleo_ispettivo_due = 13 OR ticket.nucleo_ispettivo_tre = 13 OR ticket.nucleo_ispettivo_quattro = 13 OR ticket.nucleo_ispettivo_cinque = 13 OR ticket.nucleo_ispettivo_sei = 13 OR ticket.nucleo_ispettivo_sette = 13 OR ticket.nucleo_ispettivo_otto = 13 OR ticket.nucleo_ispettivo_nove = 13 OR ticket.nucleo_ispettivo_dieci = 13 THEN '002'::text
            ELSE 'n.d'::text
        END), false::boolean, (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), (
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END), l.decodifica_tipo_produzione_bdn, (
        CASE
            WHEN l.decodifica_codice_orientamento_bdn IS NOT NULL THEN l.decodifica_codice_orientamento_bdn
            ELSE ''::text
        END), 'N.D'::text, ticket.data_estrazione, ticket.data_import, ticket.esito_import, ticket.descrizione_errore, (
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN lsa.description::text ~~* '%suini%'::text THEN '2'::text
                WHEN lsa.description::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN lsa.description::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END), ist.id_alleg, (
        CASE
            WHEN lsa.description::text ~~* 'gallus'::text AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END), (
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END);

ALTER TABLE public.bdn_cu_candidati_benessere_animale
  OWNER TO postgres;
GRANT ALL ON TABLE public.bdn_cu_candidati_benessere_animale TO postgres;
GRANT SELECT ON TABLE public.bdn_cu_candidati_benessere_animale TO report;

-- View: public.bdn_candidati_sicurezza_alimentare_b11_org_opu

-- DROP VIEW public.bdn_candidati_sicurezza_alimentare_b11_org_opu;

CREATE OR REPLACE VIEW public.bdn_candidati_sicurezza_alimentare_b11_org_opu AS 
 SELECT DISTINCT ticket.id_bdn,
    ticket.id_bdn_b11,
    o.name AS ragione_sociale,
    o.org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || comuni1.codiceistatasl_old::text
            ELSE NULL::text
        END AS codice_asl,
    asl1.description AS asl,
    o.account_number AS codice_azienda,
    o.partita_iva AS id_fiscale_allevamento,
        CASE
            WHEN nucleo.nucleo_ispettivo::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%forestale% '::text THEN '002'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%NAS% '::text THEN '001'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%veterinari% '::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
    o.cf_detentore AS id_fiscale_detentore,
    op.tipo_produzione,
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END AS codice_orientamento,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
    ticket.data_estrazione_b11,
    ticket.data_import_b11,
    ticket.esito_import_b11,
    ticket.descrizione_errore_b11,
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
    'S'::text AS esito_controllo,
    o.specie_allev,
    '0'::text || o.specie_allevamento::text AS specie_allevamento,
        CASE
            WHEN (o.specie_allev::text ~~* 'gallus'::text OR o.specie_allev::text ~~* 'oche'::text OR o.specie_allev::text ~~* 'fagiani'::text) AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso ~~* '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END AS flag_copia_checklist,
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END AS flag_esito_sa,
    lcm.code AS id_alleg,
    '-1'::integer AS id_stabilimento,
    lpiani.description AS pianomonitoraggio
   FROM ticket
     JOIN organization o ON ticket.org_id = o.org_id
     LEFT JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 1
     LEFT JOIN comuni1 ON comuni1.nome::text ~~* oa.city::text
     LEFT JOIN nucleo_ispettivo_mv nucleo ON nucleo.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = o.specie_allevamento
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     LEFT JOIN lookup_piano_monitoraggio lpiani ON lpiani.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = o.asl_old
     LEFT JOIN lookup_site_id asl1 ON asl1.code = ticket.site_id
     LEFT JOIN orientamenti_produttivi op ON op.specie_allevata = o.specie_allev::text AND op.descrizione_tipo_produzione = o.tipologia_strutt AND o.orientamento_prod = op.descrizione_codice_orientamento
     LEFT JOIN lookup_specie_allevata specie ON specie.description::text = o.specie_allev::text
  WHERE ticket.tipologia = 3 AND o.tipologia = 2 AND lpiani.codice_interno = 1483 AND ticket.trashed_date IS NULL AND o.trashed_date IS NULL AND ist.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) >= 2015::double precision AND ((( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 OR lcm.code IS NULL OR lcm.code = 20)
  GROUP BY ticket.id_bdn, ticket.id_bdn_b11, o.name, o.org_id, ticket.ticketid, ticket.assigned_date, nucleo.nucleo_ispettivo, nucleo.nucleo_ispettivo_due, nucleo.nucleo_ispettivo_tre, nucleo.nucleo_ispettivo_quattro, nucleo.nucleo_ispettivo_cinque, nucleo.nucleo_ispettivo_sei, nucleo.nucleo_ispettivo_sette, nucleo.nucleo_ispettivo_otto, nucleo.nucleo_ispettivo_nove, nucleo.nucleo_ispettivo_dieci, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || comuni1.codiceistatasl_old::text
            ELSE NULL::text
        END), asl1.description, o.account_number, ('0'::text || o.specie_allevamento::text), o.partita_iva, (
        CASE
            WHEN nucleo.nucleo_ispettivo::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%forestale% '::text THEN '002'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%NAS% '::text THEN '001'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%veterinari% '::text THEN '003'::text
            ELSE 'n.d.'::text
        END), (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), o.cf_detentore, op.tipo_produzione, (
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END), ticket.data_estrazione_b11, ticket.data_import_b11, ticket.esito_import_b11, ticket.descrizione_errore_b11, (
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN o.specie_allev::text ~~* '%suini%'::text THEN '2'::text
            WHEN o.specie_allev::text ~~* '%broiler%'::text THEN '5'::text
            WHEN o.specie_allev::text ~~* '%gallus%'::text THEN '1'::text
            ELSE '4'::text
        END), 'N'::text, (
        CASE
            WHEN o.specie_allev::text ~~* 'gallus'::text AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END), (
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END), lcm.code, lpiani.description
UNION
 SELECT DISTINCT ticket.id_bdn,
    ticket.id_bdn_b11,
    o.ragione_sociale,
    '-1'::integer AS org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || c.codiceistatasl_old::text
            ELSE NULL::text
        END AS codice_asl,
    asl1.description AS asl,
    COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente) AS codice_azienda,
    btrim(COALESCE(o.partita_iva::text, o.codice_fiscale_impresa::text)) AS id_fiscale_allevamento,
        CASE
            WHEN qual.description::text ~~* '%forestale% '::text THEN '002'::text
            WHEN qual.description::text ~~* '%NAS%'::text THEN '001'::text
            WHEN qual.description::text ~~* '%veterinari%'::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
    ''::text AS id_fiscale_detentore,
    l.decodifica_tipo_produzione_bdn AS tipo_produzione,
    l.decodifica_codice_orientamento_bdn AS codice_orientamento,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
    ticket.data_estrazione_b11,
    ticket.data_import_b11,
    ticket.esito_import_b11,
    ticket.descrizione_errore_b11,
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
    'S'::text AS esito_controllo,
    lsa.description AS specie_allev,
        CASE
            WHEN lsa.description::text !~~* 'pesci'::text THEN '0'::text || l.decodifica_specie_bdn
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN (lsa.description::text ~~* 'gallus'::text OR lsa.description::text ~~* 'oche'::text OR lsa.description::text ~~* 'fagiani'::text) AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso ~~* '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END AS flag_copia_checklist,
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END AS flag_esito_sa,
    lcm.code AS id_alleg,
    s.id AS id_stabilimento,
    lpiani.description AS pianomonitoraggio
   FROM ticket
     JOIN opu_stabilimento s ON ticket.id_stabilimento = s.id
     LEFT JOIN opu_operatore o ON o.id = s.id_operatore
     LEFT JOIN opu_indirizzo ind ON ind.id = s.id_indirizzo
     LEFT JOIN comuni1 c ON c.id = ind.comune
     JOIN opu_linee_attivita_controlli_ufficiali olacu ON olacu.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN opu_relazione_stabilimento_linee_produttive rel ON rel.id = olacu.id_linea_attivita
     LEFT JOIN ml8_linee_attivita_nuove l ON l.id_nuova_linea_attivita = rel.id_linea_produttiva
     LEFT JOIN nucleo_ispettivo_mv nucleo ON nucleo.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = l.decodifica_specie_bdn::integer
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     LEFT JOIN lookup_piano_monitoraggio lpiani ON lpiani.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = s.id_asl
     LEFT JOIN lookup_site_id asl1 ON asl1.code = ticket.site_id
     LEFT JOIN lookup_specie_allevata lsa ON lsa.short_description::text = l.decodifica_specie_bdn
  WHERE ticket.tipologia = 3 AND lpiani.codice_interno = 1483 AND ticket.trashed_date IS NULL AND o.trashed_date IS NULL AND s.trashed_date IS NULL AND ist.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) >= 2015::double precision AND ((( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 OR lcm.code IS NULL OR lcm.code = 20)
  GROUP BY ticket.id_bdn, ticket.id_bdn_b11, o.ragione_sociale, s.id, ticket.ticketid, ticket.assigned_date, nucleo.nucleo_ispettivo, nucleo.nucleo_ispettivo_due, nucleo.nucleo_ispettivo_tre, nucleo.nucleo_ispettivo_quattro, nucleo.nucleo_ispettivo_cinque, nucleo.nucleo_ispettivo_sei, nucleo.nucleo_ispettivo_sette, nucleo.nucleo_ispettivo_otto, nucleo.nucleo_ispettivo_nove, nucleo.nucleo_ispettivo_dieci, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || c.codiceistatasl_old::text
            ELSE NULL::text
        END), asl1.description, (COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente)), ('0'::text || l.decodifica_specie_bdn), o.partita_iva, o.codice_fiscale_impresa, qual.description, l.decodifica_codice_orientamento_bdn, lsa.description, (
        CASE
            WHEN nucleo.nucleo_ispettivo::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%forestale% '::text THEN '002'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%NAS% '::text THEN '001'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%veterinari% '::text THEN '003'::text
            ELSE 'n.d.'::text
        END), (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), l.decodifica_tipo_produzione_bdn, (
        CASE
            WHEN l.decodifica_codice_orientamento_bdn IS NOT NULL THEN l.decodifica_codice_orientamento_bdn
            ELSE ''::text
        END), ticket.data_estrazione_b11, ticket.data_import_b11, ticket.esito_import_b11, ticket.descrizione_errore_b11, (
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN lsa.description::text ~~* '%suini%'::text THEN '2'::text
                WHEN lsa.description::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN lsa.description::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lpiani.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END), 'N'::text, (
        CASE
            WHEN lsa.description::text ~~* 'gallus'::text AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END), (
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END), lcm.code, lpiani.description;

ALTER TABLE public.bdn_candidati_sicurezza_alimentare_b11_org_opu
  OWNER TO postgres;
GRANT ALL ON TABLE public.bdn_candidati_sicurezza_alimentare_b11_org_opu TO postgres;
GRANT SELECT ON TABLE public.bdn_candidati_sicurezza_alimentare_b11_org_opu TO usr_ro;
GRANT SELECT ON TABLE public.bdn_candidati_sicurezza_alimentare_b11_org_opu TO report;

-- View: public.bdn_cu_candidati_benessere_animale

-- DROP VIEW public.bdn_cu_candidati_benessere_animale;

CREATE OR REPLACE VIEW public.bdn_cu_candidati_benessere_animale AS 
 SELECT DISTINCT sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) AS punteggio,
    ist.id_alleg,
    ticket.id_bdn,
    lcm.codice_specie AS codice_specie_chk_bns,
    o.name AS ragione_sociale,
    o.org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || comuni1.codiceistatasl_old::text
            ELSE NULL::text
        END AS codice_asl,
    o.account_number AS codice_azienda,
    btrim(o.partita_iva::text) AS id_fiscale_allevamento,
        CASE
            WHEN qual.description::text ~~* '%forestale% '::text THEN '002'::text
            WHEN qual.description::text ~~* '%NAS%'::text THEN '001'::text
            WHEN qual.description::text ~~* '%veterinari%'::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
    false AS flag_cee,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END AS flag_extra_piano,
    o.cf_detentore AS id_fiscale_detentore,
    op.tipo_produzione,
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END AS codice_orientamento,
    'N.D'::text AS num_capannoni_suini,
    'N.D'::text AS num_capannoni_attivi_suini,
    'N.D'::text AS num_box_suini,
    'N.D'::text AS num_box_attivi_suini,
    'N.D'::text AS num_vitelli_max,
    'N.D'::text AS num_vitelli_presenti,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
        CASE
            WHEN sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) <= 0 OR sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) IS NULL THEN 'S'::text
            ELSE 'N'::text
        END AS esito_controllo,
    o.specie_allev,
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN o.specie_allev::text ~~* '%suini%'::text THEN '2'::text
                WHEN o.specie_allev::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN o.specie_allev::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END AS tipo_allegato,
        CASE
            WHEN o.specie_allev::text !~~* 'pesci'::text THEN '0'::text || o.specie_allevamento::text
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END AS flag_vitelli,
        CASE
            WHEN (o.specie_allev::text ~~* 'gallus'::text OR o.specie_allev::text ~~* 'oche'::text OR o.specie_allev::text ~~* 'fagiani'::text) AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso = '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
    tipi.pianomonitoraggio,
    '-1'::integer AS id_stabilimento
   FROM ticket
     JOIN organization o ON ticket.org_id = o.org_id
     JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 1
     LEFT JOIN comuni1 ON comuni1.nome::text ~~* oa.city::text
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = o.specie_allevamento
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     JOIN lookup_piano_monitoraggio lp ON lp.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = o.asl_old
     LEFT JOIN orientamenti_produttivi op ON op.specie_allevata = o.specie_allev::text AND op.descrizione_tipo_produzione = o.tipologia_strutt AND o.orientamento_prod = op.descrizione_codice_orientamento
     LEFT JOIN lookup_specie_allevata specie ON specie.description::text = o.specie_allev::text
  WHERE ticket.tipologia = 3 AND ticket.trashed_date IS NULL AND o.tipologia = 2 AND o.trashed_date IS NULL AND (lp.codice_interno = ANY (ARRAY[982, 983])) AND (lcm.code IS NULL OR lcm.code <> 20) AND date_part('year'::text, ticket.assigned_date) > 2015::double precision
  GROUP BY ticket.id_bdn, o.name, o.org_id, ticket.ticketid, ticket.assigned_date, qual.description, tipi.pianomonitoraggio, lcm.codice_specie, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || comuni1.codiceistatasl_old::text
            ELSE NULL::text
        END), o.account_number, ('0'::text || o.specie_allevamento::text), o.partita_iva, (
        CASE
            WHEN ticket.nucleo_ispettivo = 1 OR ticket.nucleo_ispettivo_due = 1 OR ticket.nucleo_ispettivo_tre = 1 OR ticket.nucleo_ispettivo_quattro = 1 OR ticket.nucleo_ispettivo_cinque = 1 OR ticket.nucleo_ispettivo_sei = 1 OR ticket.nucleo_ispettivo_sette = 1 OR ticket.nucleo_ispettivo_otto = 1 OR ticket.nucleo_ispettivo_nove = 1 OR ticket.nucleo_ispettivo_dieci = 1 THEN '003'::text
            WHEN ticket.nucleo_ispettivo = 8 OR ticket.nucleo_ispettivo_due = 8 OR ticket.nucleo_ispettivo_tre = 8 OR ticket.nucleo_ispettivo_quattro = 8 OR ticket.nucleo_ispettivo_cinque = 8 OR ticket.nucleo_ispettivo_sei = 8 OR ticket.nucleo_ispettivo_sette = 8 OR ticket.nucleo_ispettivo_otto = 8 OR ticket.nucleo_ispettivo_nove = 8 OR ticket.nucleo_ispettivo_dieci = 8 THEN '001'::text
            WHEN ticket.nucleo_ispettivo = 13 OR ticket.nucleo_ispettivo_due = 13 OR ticket.nucleo_ispettivo_tre = 13 OR ticket.nucleo_ispettivo_quattro = 13 OR ticket.nucleo_ispettivo_cinque = 13 OR ticket.nucleo_ispettivo_sei = 13 OR ticket.nucleo_ispettivo_sette = 13 OR ticket.nucleo_ispettivo_otto = 13 OR ticket.nucleo_ispettivo_nove = 13 OR ticket.nucleo_ispettivo_dieci = 13 THEN '002'::text
            ELSE 'n.d'::text
        END), false::boolean, (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), (
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END), o.cf_detentore, op.tipo_produzione, (
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END), 'N.D'::text, ticket.data_estrazione, ticket.data_import, ticket.esito_import, ticket.descrizione_errore, (
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN o.specie_allev::text ~~* '%suini%'::text THEN '2'::text
                WHEN o.specie_allev::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN o.specie_allev::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END), ist.id_alleg, (
        CASE
            WHEN o.specie_allev::text ~~* 'gallus'::text AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END), (
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END)
UNION
 SELECT DISTINCT sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) AS punteggio,
    ist.id_alleg,
    ticket.id_bdn,
    lcm.codice_specie AS codice_specie_chk_bns,
    o.ragione_sociale,
    '-1'::integer AS org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || c.istat::text
            ELSE NULL::text
        END AS codice_asl,
    COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente) AS codice_azienda,
    btrim(COALESCE(o.partita_iva::text, o.codice_fiscale_impresa::text)) AS id_fiscale_allevamento,
        CASE
            WHEN qual.description::text ~~* '%forestale% '::text THEN '002'::text
            WHEN qual.description::text ~~* '%NAS%'::text THEN '001'::text
            WHEN qual.description::text ~~* '%veterinari%'::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
    false AS flag_cee,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END AS flag_extra_piano,
    ''::text AS id_fiscale_detentore,
    l.decodifica_tipo_produzione_bdn AS tipo_produzione,
    l.decodifica_codice_orientamento_bdn AS codice_orientamento,
    'N.D'::text AS num_capannoni_suini,
    'N.D'::text AS num_capannoni_attivi_suini,
    'N.D'::text AS num_box_suini,
    'N.D'::text AS num_box_attivi_suini,
    'N.D'::text AS num_vitelli_max,
    'N.D'::text AS num_vitelli_presenti,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
        CASE
            WHEN sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) <= 0 OR sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) IS NULL THEN 'S'::text
            ELSE 'N'::text
        END AS esito_controllo,
    lsa.description AS specie_allev,
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN lsa.description::text ~~* '%suini%'::text THEN '2'::text
                WHEN lsa.description::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN lsa.description::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END AS tipo_allegato,
        CASE
            WHEN lsa.description::text !~~* 'pesci'::text THEN '0'::text || l.decodifica_specie_bdn
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END AS flag_vitelli,
        CASE
            WHEN (lsa.description::text ~~* 'gallus'::text OR lsa.description::text ~~* 'oche'::text OR lsa.description::text ~~* 'fagiani'::text) AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso = '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
    tipi.pianomonitoraggio,
    s.id AS id_stabilimento
   FROM ticket
     JOIN opu_stabilimento s ON ticket.id_stabilimento = s.id
     LEFT JOIN opu_operatore o ON o.id = s.id_operatore
     LEFT JOIN opu_indirizzo ind ON ind.id = s.id_indirizzo
     LEFT JOIN comuni1 c ON c.id = ind.comune
     JOIN opu_linee_attivita_controlli_ufficiali olacu ON olacu.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN opu_relazione_stabilimento_linee_produttive rel ON rel.id = olacu.id_linea_attivita
     LEFT JOIN ml8_linee_attivita_nuove l ON l.id_nuova_linea_attivita = rel.id_linea_produttiva
     LEFT JOIN orientamenti_produttivi ori ON l.decodifica_tipo_produzione_bdn = ori.tipo_produzione
     LEFT JOIN lookup_specie_allevata lsa ON lsa.short_description::text = l.decodifica_specie_bdn
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = l.decodifica_specie_bdn::integer
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     JOIN lookup_piano_monitoraggio lp ON lp.code = tipi.pianomonitoraggio
  WHERE ticket.tipologia = 3 AND (lcm.code IS NULL OR lcm.code <> 20) AND (lp.codice_interno = ANY (ARRAY[982, 983])) AND ticket.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) > 2015::double precision AND s.trashed_date IS NULL AND o.trashed_date IS NULL
  GROUP BY ticket.id_bdn, o.ragione_sociale, s.id, ticket.ticketid, ticket.assigned_date, qual.description, tipi.pianomonitoraggio, lcm.codice_specie, o.codice_fiscale_impresa, l.decodifica_codice_orientamento_bdn, lsa.description, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R'::text || c.istat::text
            ELSE NULL::text
        END), (COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente)), ('0'::text || l.decodifica_specie_bdn), o.partita_iva, (
        CASE
            WHEN ticket.nucleo_ispettivo = 1 OR ticket.nucleo_ispettivo_due = 1 OR ticket.nucleo_ispettivo_tre = 1 OR ticket.nucleo_ispettivo_quattro = 1 OR ticket.nucleo_ispettivo_cinque = 1 OR ticket.nucleo_ispettivo_sei = 1 OR ticket.nucleo_ispettivo_sette = 1 OR ticket.nucleo_ispettivo_otto = 1 OR ticket.nucleo_ispettivo_nove = 1 OR ticket.nucleo_ispettivo_dieci = 1 THEN '003'::text
            WHEN ticket.nucleo_ispettivo = 8 OR ticket.nucleo_ispettivo_due = 8 OR ticket.nucleo_ispettivo_tre = 8 OR ticket.nucleo_ispettivo_quattro = 8 OR ticket.nucleo_ispettivo_cinque = 8 OR ticket.nucleo_ispettivo_sei = 8 OR ticket.nucleo_ispettivo_sette = 8 OR ticket.nucleo_ispettivo_otto = 8 OR ticket.nucleo_ispettivo_nove = 8 OR ticket.nucleo_ispettivo_dieci = 8 THEN '001'::text
            WHEN ticket.nucleo_ispettivo = 13 OR ticket.nucleo_ispettivo_due = 13 OR ticket.nucleo_ispettivo_tre = 13 OR ticket.nucleo_ispettivo_quattro = 13 OR ticket.nucleo_ispettivo_cinque = 13 OR ticket.nucleo_ispettivo_sei = 13 OR ticket.nucleo_ispettivo_sette = 13 OR ticket.nucleo_ispettivo_otto = 13 OR ticket.nucleo_ispettivo_nove = 13 OR ticket.nucleo_ispettivo_dieci = 13 THEN '002'::text
            ELSE 'n.d'::text
        END), false::boolean, (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), (
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END), l.decodifica_tipo_produzione_bdn, (
        CASE
            WHEN l.decodifica_codice_orientamento_bdn IS NOT NULL THEN l.decodifica_codice_orientamento_bdn
            ELSE ''::text
        END), 'N.D'::text, ticket.data_estrazione, ticket.data_import, ticket.esito_import, ticket.descrizione_errore, (
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN lsa.description::text ~~* '%suini%'::text THEN '2'::text
                WHEN lsa.description::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN lsa.description::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END), ist.id_alleg, (
        CASE
            WHEN lsa.description::text ~~* 'gallus'::text AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END), (
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END);

ALTER TABLE public.bdn_cu_candidati_benessere_animale
  OWNER TO postgres;
GRANT ALL ON TABLE public.bdn_cu_candidati_benessere_animale TO postgres;
GRANT SELECT ON TABLE public.bdn_cu_candidati_benessere_animale TO report;

-- View: public.apicoltura_apiari_denormalizzati_view

-- DROP VIEW public.apicoltura_apiari_denormalizzati_view;

CREATE OR REPLACE VIEW public.apicoltura_apiari_denormalizzati_view AS 
 SELECT DISTINCT o.id AS id_apicoltura_imprese,
    stab.id AS id_apicoltura_apiari,
        CASE
            WHEN o.sincronizzato_bdn THEN 'SI'::text
            ELSE 'NO'::text
        END AS sincronizzato_bdn,
    las.description AS stato_impresa,
    lastab.description AS stato_stab,
    o.ragione_sociale,
    o.partita_iva,
    o.codice_fiscale_impresa,
    to_char(o.data_inizio, 'dd/MM/yyyy'::text) AS data_inizio_attivita,
    ((sedeop.via::text ||
        CASE
            WHEN sedeop.civico IS NOT NULL THEN ' '::text || sedeop.civico
            ELSE ''::text
        END))::character varying(300) AS indirizzo_sede_legale,
    comunisl.nome AS comune_sede_legale,
    comunisl.istat AS istat_legale,
    sedeop.cap AS cap_sede_legale,
    sedeop.provincia AS prov_sede_legale,
    o.note,
    o.entered,
    o.modified,
    o.enteredby,
    o.modifiedby,
    o.domicilio_digitale,
    taa.description AS tipo_attivita,
    o.telefono_fisso,
    o.telefono_cellulare,
    o.faxt AS fax,
    i.comune,
    asl.description AS asl_apiario,
    stab.id AS id_stabilimento,
    stab.esito_import,
    stab.data_import,
    stab.id_sinvsa,
    stab.descrizione_errore,
    stab.progressivo,
    comuni1.nome AS comune_stab,
    comuni1.istat AS istat_operativo,
    sedestab.via AS indirizzo_stab,
    sedestab.cap AS cap_stab,
    sedestab.provincia AS prov_stab,
    stab.categoria_rischio,
    soggsl.codice_fiscale AS cf_rapp_sede_legale,
    soggsl.nome AS nome_rapp_sede_legale,
    soggsl.cognome AS cognome_rapp_sede_legale,
    (
        CASE
            WHEN soggind.via IS NOT NULL THEN soggind.via
            ELSE ''::character varying
        END::text || ', '::text) ||
        CASE
            WHEN comunisoggind.nome IS NOT NULL THEN comunisoggind.nome
            ELSE ''::character varying
        END::text AS indirizzo_rapp_sede_legale,
    detentore.codice_fiscale AS cf_detentore,
    detentore.nome AS nome_detentore,
    detentore.cognome AS cognome_detentore,
    lokmod.description AS modalita,
    lokcla.description AS classificazione,
    loksot.description AS sottospecie,
    stab.num_alveari,
    stab.num_sciami,
    stab.num_pacchi,
    stab.num_regine,
    to_char(stab.data_inizio, 'dd/MM/yyyy'::text) AS data_apertura_stab,
    to_char(stab.data_chiusura, 'dd/MM/yyyy'::text) AS data_chiusura_stab,
    stab.data_prossimo_controllo,
    asl.code AS id_asl_apiario,
    i.latitudine,
    i.longitudine,
    o.codice_azienda,
    stab.flag_laboratorio,
    ''::text AS macroarea,
    ''::text AS aggregazione,
    taa.description AS attivita,
    1 AS id_tipo_linea_produttiva,
    stab.id_controllo_ultima_categorizzazione,
    stab.data_assegnazione_censimento,
    aslperapicoltore.description AS asl_apicoltore,
    aslperapicoltore.code AS id_asl_apicoltore
   FROM apicoltura_imprese o
     LEFT JOIN suap_ric_scia_operatore scia ON scia.id = o.id_richiesta_suap
     LEFT JOIN suap_ric_scia_stabilimento sciastab ON sciastab.id_operatore = scia.id
     LEFT JOIN suap_ric_scia_relazione_stabilimento_linee_produttive lps ON lps.id_stabilimento = sciastab.id
     LEFT JOIN ml8_linee_attivita_nuove_materializzata latt ON latt.id_nuova_linea_attivita = lps.id_linea_produttiva
     JOIN apicoltura_rel_impresa_soggetto_fisico rels ON rels.id_apicoltura_imprese = o.id AND rels.enabled
     JOIN opu_soggetto_fisico soggsl ON soggsl.id = rels.id_soggetto_fisico
     LEFT JOIN opu_indirizzo soggind ON soggind.id = soggsl.indirizzo_id
     LEFT JOIN comuni1 comunisoggind ON comunisoggind.id = soggind.comune
     JOIN apicoltura_relazione_imprese_sede_legale ros ON ros.id_apicoltura_imprese = o.id AND ros.enabled
     JOIN opu_indirizzo sedeop ON sedeop.id = ros.id_indirizzo
     JOIN comuni1 comunisl ON sedeop.comune = comunisl.id
     JOIN apicoltura_apiari stab ON stab.id_operatore = o.id
     JOIN opu_indirizzo sedestab ON sedestab.id = stab.id_indirizzo
     LEFT JOIN comuni1 ON sedestab.comune = comuni1.id
     LEFT JOIN opu_indirizzo i ON i.id = stab.id_indirizzo
     LEFT JOIN lookup_apicoltura_stati_apiario las ON las.code = o.stato
     LEFT JOIN lookup_apicoltura_stati_apiario lastab ON lastab.code = stab.stato
     LEFT JOIN apicoltura_lookup_tipo_attivita taa ON taa.code = o.tipo_attivita_apicoltura
     LEFT JOIN lookup_site_id asl ON asl.code = stab.id_asl
     LEFT JOIN lookup_site_id aslperapicoltore ON aslperapicoltore.code = o.id_asl
     LEFT JOIN opu_soggetto_fisico detentore ON detentore.id = stab.id_soggetto_fisico
     LEFT JOIN apicoltura_lookup_modalita lokmod ON lokmod.code = stab.id_apicoltura_lookup_modalita
     LEFT JOIN apicoltura_lookup_classificazione lokcla ON lokcla.code = stab.id_apicoltura_lookup_classificazione
     LEFT JOIN apicoltura_lookup_sottospecie loksot ON loksot.code = stab.id_apicoltura_lookup_sottospecie
  WHERE o.trashed_date IS NULL AND stab.trashed_date IS NULL;

ALTER TABLE public.apicoltura_apiari_denormalizzati_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.apicoltura_apiari_denormalizzati_view TO postgres;
GRANT SELECT ON TABLE public.apicoltura_apiari_denormalizzati_view TO report;

-- View: public.apicoltura_operatori_denormalizzati_view

-- DROP VIEW public.apicoltura_operatori_denormalizzati_view;

CREATE OR REPLACE VIEW public.apicoltura_operatori_denormalizzati_view AS 
 SELECT DISTINCT false AS flag_dia,
    o.id AS id_api_operatore,
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
    false AS flag_ric_ce,
    false AS num_ric_ce,
    comunisl.nome AS comune,
    o.id_asl,
    o.id AS id_stabilimento,
    NULL::text AS esito_import,
    NULL::timestamp without time zone AS data_import,
    o.codice_azienda AS cun,
    NULL::integer AS id_sinvsa,
    NULL::text AS descrizione_errore,
    comunisl.nome AS comune_stab,
    comunisl.istat AS istat_operativo,
    (((
        CASE
            WHEN topsedeop.description IS NOT NULL THEN topsedeop.description
            ELSE ''::character varying
        END::text || ' '::text) || sedeop.via::text) || ' '::text) ||
        CASE
            WHEN sedeop.civico IS NOT NULL THEN sedeop.civico
            ELSE ''::text
        END AS indirizzo_stab,
    sedeop.cap AS cap_stab,
    provsedeop.description AS prov_stab,
    o.data_fine AS data_fine_dia,
    NULL::integer AS categoria_rischio,
    soggsl.codice_fiscale AS cf_rapp_sede_legale,
    soggsl.nome AS nome_rapp_sede_legale,
    soggsl.cognome AS cognome_rapp_sede_legale,
    o.numero_registrazione AS codice_registrazione,
    latt.id_norma,
    COALESCE(latt.codice_attivita, tipoattapi.codice_attivita) AS cf_correntista,
    COALESCE(latt.codice_attivita, tipoattapi.codice_attivita) AS codice_attivita,
    lps.primario,
    COALESCE((((latt.macroarea || '->'::text) || latt.aggregazione) || '->'::text) || latt.attivita, tipoattapi.description) AS attivita,
    COALESCE(lps.data_inizio, o.data_inizio) AS data_inizio,
    COALESCE(lps.data_fine, o.data_fine) AS data_fine,
    o.numero_registrazione,
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
    latt.attivita AS solo_attivita,
    o.data_inizio AS data_inizio_attivita,
    o.data_fine AS data_fine_attivita,
    NULL::timestamp without time zone AS data_prossimo_controllo,
    o.stato AS id_stato,
    COALESCE(latt.path_descrizione, tipoattapi.description::character varying) AS path_attivita_completo,
    rseleg.id_indirizzo,
    false AS linee_pregresse,
    true as flag_nuova_gestione,
    loti.description AS tipo_impresa,
    lotis.description AS tipo_societa,
    o.codice_azienda AS codice_ufficiale_esistente,
    o.id_asl AS id_asl_stab,
    lps.id AS id_linea_attivita,
    lps.modified AS data_mod_attivita,
    o.entered AS stab_entered,
    o.numero_registrazione AS linea_numero_registrazione,
    lps.stato AS linea_stato,
    stati.description AS linea_stato_text,
    o.codice_azienda AS linea_codice_ufficiale_esistente,
    o.codice_azienda AS stab_codice_ufficiale_esistente,
    sedeop.id AS id_indirizzo_operatore,
    false AS import_opu,
    lps.id_linea_produttiva AS id_linea_attivita_stab,
    o.note AS note_operatore,
    o.note AS note_stabilimento,
    o.codice_azienda AS linea_codice_nazionale,
    latt.flag_pnaa,
    (con.namefirst::text || ' '::text) || con.namelast::text AS stab_inserito_da,
    3 AS stab_id_attivita,
    'APICOLTURA'::text AS stab_descrizione_attivita,
    sciastab.tipo_carattere AS stab_id_carattere,
    lsc.description AS stab_descrizione_carattere,
    scia.tipo_impresa AS impresa_id_tipo_impresa,
    asl.description AS stab_asl,
    true AS flag_clean,
    o.data_validazione_scia AS data_generazione_numero,
    sedeop.latitudine AS lat_stab,
    sedeop.longitudine AS long_stab,
    sciastab.entered AS linea_entered,
    lps.modified AS linea_modified,
    latt.macroarea,
    latt.aggregazione,
    latt.attivita AS attivita_xml,
    comunisl.codiceistatasl_old,
    provsedeop.cod_provincia AS sigla_prov_operativa,
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
    sedeop.civico AS civico_sede_stab,
    sedeop.toponimo AS tiponimo_sede_stab,
    topsedeop.description AS toponimo_sede_stab,
    soggind.via AS via_rapp_sede_legale,
    sedeop.via AS via_sede_legale,
    comunisl.id AS id_comune_richiesta,
    comunisl.nome AS comune_richiesta,
    sedeop.via AS via_stabilimento_calcolata,
    sedeop.civico AS civico_stabilimento_calcolato,
    soggind.cap AS cap_residenza,
    soggind.nazione AS nazione_residenza,
    sedeop.nazione AS nazione_sede_legale,
    sedeop.nazione AS nazione_stab,
    latt.id_lookup_tipo_linee_mobili,
   -1 as id_tipo_linea_produttiva
   FROM apicoltura_imprese o
     LEFT JOIN lookup_apicoltura_stati_apiario stati ON stati.code = o.stato
     JOIN apicoltura_lookup_tipo_attivita tipoattapi ON tipoattapi.code = o.tipo_attivita_apicoltura
     LEFT JOIN suap_ric_scia_operatore scia ON scia.id = o.id_richiesta_suap
     LEFT JOIN suap_ric_scia_stabilimento sciastab ON sciastab.id_operatore = scia.id
     LEFT JOIN suap_ric_scia_relazione_stabilimento_linee_produttive lps ON lps.id_stabilimento = sciastab.id
     LEFT JOIN ml8_linee_attivita_nuove_materializzata latt ON latt.id_nuova_linea_attivita = lps.id_linea_produttiva
     JOIN apicoltura_relazione_imprese_sede_legale rseleg ON rseleg.id_apicoltura_imprese = o.id
     JOIN apicoltura_rel_impresa_soggetto_fisico rsf ON rsf.id_apicoltura_imprese = o.id
     JOIN opu_soggetto_fisico soggsl ON soggsl.id = rsf.id_soggetto_fisico
     LEFT JOIN opu_indirizzo soggind ON soggind.id = soggsl.indirizzo_id
     LEFT JOIN comuni1 comunisoggind ON comunisoggind.id = soggind.comune
     LEFT JOIN opu_indirizzo sedeop ON sedeop.id = rseleg.id_indirizzo
     LEFT JOIN comuni1 comunisl ON sedeop.comune = comunisl.id
     LEFT JOIN lookup_province provsedeop ON provsedeop.code::text = sedeop.provincia::text
     LEFT JOIN lookup_province provsoggind ON provsoggind.code::text = soggind.provincia::text
     LEFT JOIN lookup_toponimi topsedeop ON sedeop.toponimo = topsedeop.code
     LEFT JOIN lookup_toponimi topsoggind ON soggind.toponimo = topsoggind.code
     LEFT JOIN lookup_opu_tipo_impresa loti ON loti.code = scia.tipo_impresa
     LEFT JOIN lookup_opu_tipo_impresa_societa lotis ON lotis.code = scia.tipo_societa
     LEFT JOIN opu_lookup_tipologia_attivita lsa ON lsa.code = sciastab.tipo_attivita
     LEFT JOIN opu_lookup_tipologia_carattere lsc ON lsc.code = sciastab.tipo_carattere
     LEFT JOIN lookup_site_id asl ON asl.code = o.id_asl
     LEFT JOIN access acc ON acc.user_id = o.enteredby
     LEFT JOIN contact con ON con.contact_id = acc.contact_id
  WHERE o.trashed_date IS NULL AND o.stato <> 4 AND o.stato <> 5 AND
        CASE
            WHEN sciastab.id > 0 THEN scia.validato <> false
            ELSE 1 = 1
        END AND tipoattapi.code = 1;

ALTER TABLE public.apicoltura_operatori_denormalizzati_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.apicoltura_operatori_denormalizzati_view TO postgres;
GRANT SELECT ON TABLE public.apicoltura_operatori_denormalizzati_view TO report;

-- Function: public.proponi_candidati_mapping(integer)

-- DROP FUNCTION public.proponi_candidati_mapping(integer);

CREATE OR REPLACE FUNCTION public.proponi_candidati_mapping(IN orgid integer)
  RETURNS TABLE(idvecchialinea_out integer, idnuovalinea integer, id_macroareanuovalinea integer, macroareanuovalinea text, id_aggregazionenuovalinea integer, aggregazionenuovalinea text, id_attivitanuova integer, attivitanuova text, id_norma integer, ranking integer, idvecchialineaoriginale_out integer) AS
$BODY$
declare
	rec1 record; 
	idvecchialinea integer;
	idvecchialineaOriginale integer;
begin
	for idvecchialinea,idvecchialineaOriginale in select id_vecchia_linea,id_vecchia_originale from get_vecchie_linee_da_organization(orgId) --per ognuna delle linee vecchie possibili, per quella tipologia
	loop
		--ottengo tutti i mapping già effettuati con i ranking

		for rec1 in select la.id_nuova_linea_attivita as nuovalinea,
				   la.macroarea,la.id_macroarea,la.aggregazione,la.id_aggregazione
				   ,la.attivita,la.id_attivita,la.id_norma, kb.ranking 
			from knowledge_based_mapping kb  
			join ml8_linee_attivita_nuove la on kb.id_nuova_linea::integer = la.id_nuova_linea_attivita 
			where kb.id_vecchia_linea = idvecchialinea order by kb.ranking desc
		loop
			idvecchialinea_out := idvecchialinea;
			idnuovalinea := rec1.nuovalinea; 
			id_macroareanuovalinea := rec1.id_macroarea;
			macroareanuovalinea:= rec1.macroarea;
			id_aggregazionenuovalinea := rec1.id_aggregazione;
			aggregazionenuovalinea := rec1.aggregazione;
			id_attivitanuova := rec1.id_attivita;
			attivitanuova := rec1.attivita;
			id_norma := rec1.id_norma;
			ranking := rec1.ranking;
			idvecchialineaOriginale_out:=idvecchialineaOriginale;
			return next;

		end loop;
	end loop;
	
end;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.proponi_candidati_mapping(integer)
  OWNER TO postgres;

  -- Function: public.opu_can_pnaa(integer)

-- DROP FUNCTION public.opu_can_pnaa(integer);

CREATE OR REPLACE FUNCTION public.opu_can_pnaa(id_cu integer)
  RETURNS boolean AS
$BODY$
DECLARE
org_id          int;
id_stabilimento_var int;
tipologia       int;
conta integer;
risultato integer;
r RECORD;	
BEGIN
org_id := (select t.org_id 
	        from ticket t, organization o 
	        where o.org_id = t.org_id and 
	              t.ticketid = id_cu and 
	              t.tipologia = 3 and
	              t.trashed_date is null and
	              o.trashed_date is null
               );
               
       tipologia := (select o.tipologia 
	        from ticket t, organization o 
	        where o.org_id = t.org_id and 
	              t.ticketid = id_cu and 
	              t.tipologia = 3 and
	              t.trashed_date is null and
	              o.trashed_date is null
               );

       id_stabilimento_var := (select t.id_stabilimento
	        from ticket t, opu_stabilimento o 
	        where o.id = t.id_stabilimento and 
	              t.ticketid = id_cu and 
	              t.tipologia = 3 and
	              t.trashed_date is null and
	              o.trashed_date is null

                 );
       --qui c'è il problema...stat non è una variabile ma un record..quindi non si può fare sta=true...
       conta := (select count(*) from opu_linee_attivita_controlli_ufficiali ol
					   join opu_relazione_stabilimento_linee_produttive ol1 on ol1.id = ol.id_linea_attivita
					   join ml8_linee_attivita_nuove o on o.id_nuova_linea_attivita = ol1.id_linea_produttiva
			                   join ml8_master_list m on m.id = o.id_macroarea
					   where ol.id_controllo_ufficiale = id_cu and m.flag_pnaa);                 
        
IF (org_id is not null and org_id>0 and tipologia in (1,800,801,97,2) )  THEN
   risultato:= 1;
ELSIF(id_stabilimento_var > 0 and conta > 0) THEN
   risultato:= 1;
ELSE
   risultato:= 0;
END IF;

if(risultato=1) then
	return true;
else
	return false;
end if;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.opu_can_pnaa(integer)
  OWNER TO postgres;

  -- Function: public.opu_can_pnaa_solo_opu(integer)


  -- Function: public.proponi_candidatimaxrankmapping_dalinea(integer)

-- DROP FUNCTION public.proponi_candidatimaxrankmapping_dalinea(integer);

CREATE OR REPLACE FUNCTION public.proponi_candidatimaxrankmapping_dalinea(IN idvecchialinea integer)
  RETURNS TABLE(idvecchialinea_out integer, idnuovalinea integer, id_macroareanuovalinea integer, macroareanuovalinea text, id_aggregazionenuovalinea integer, aggregazionenuovalinea text, id_attivitanuova integer, attivitanuova text, id_norma integer, ranking integer) AS
$BODY$
declare
	rec1 record;   
begin

		 
		--ottengo tutti i mapping già effettuati con i ranking
		for rec1 in select la.id_nuova_linea_attivita as nuovalinea,
				   la.macroarea,la.id_macroarea,la.aggregazione,la.id_aggregazione
				   ,la.attivita,la.id_attivita,la.id_norma, kb.ranking 
			from knowledge_based_mapping kb  
			join ml8_linee_attivita_nuove la on kb.id_nuova_linea::integer = la.id_nuova_linea_attivita 
			where kb.id_vecchia_linea = idvecchialinea and kb.ranking = (select max(kb2.ranking) from knowledge_based_mapping kb2
				where kb2.id_vecchia_linea = idvecchialinea) order by kb.ranking desc
		loop
			 
			
			idvecchialinea_out := idvecchialinea;
			idnuovalinea := rec1.nuovalinea; 
			id_macroareanuovalinea := rec1.id_macroarea;
			macroareanuovalinea:= rec1.macroarea;
			id_aggregazionenuovalinea := rec1.id_aggregazione;
			aggregazionenuovalinea := rec1.aggregazione;
			id_attivitanuova := rec1.id_attivita;
			attivitanuova := rec1.attivita;
			id_norma := rec1.id_norma;
			ranking := rec1.ranking;
			
			return next;

		end loop;
	 
	
end;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.proponi_candidatimaxrankmapping_dalinea(integer)
  OWNER TO postgres;

  -- Function: public.get_esistenza_allevamento(text, text, text)

-- DROP FUNCTION public.get_esistenza_allevamento(text, text, text);

CREATE OR REPLACE FUNCTION public.get_esistenza_allevamento(
    codazienda text,
    codfiscale text,
    codspecie text)
  RETURNS boolean AS
$BODY$
   DECLARE
esiste boolean;
output_orgid integer;
output_stabid integer;

BEGIN

esiste:= false;


output_orgid := (select org_id from organization where trashed_date is null and account_number ilike codAzienda and codice_fiscale_rappresentante ilike codFiscale and specie_allevamento = codSpecie::integer);

if (output_orgid>0) THEN
esiste=true;
END IF;

if (esiste is false) THEN

output_stabid:= (
select s.id 
FROM opu_stabilimento s 
LEFT JOIN opu_operatore o on o.id = s.id_operatore
LEFT JOIN opu_rel_operatore_soggetto_fisico orosf on orosf.id_operatore = o.id
LEFT JOIN opu_soggetto_fisico sogg on sogg.id = orosf.id_soggetto_fisico
LEFT JOIN opu_relazione_stabilimento_linee_produttive rel on rel.id_stabilimento = s.id and rel.enabled
LEFT join ml8_linee_attivita_nuove l on l.id_nuova_linea_attivita = rel.id_linea_produttiva
LEFT join ml8_linee_attivita_nuove l_terzo on l_terzo.id_nuova_linea_attivita = l.id_attivita
WHERE 
(rel.codice_nazionale ilike codAzienda or rel.codice_ufficiale_esistente ilike codAzienda)
and sogg.codice_fiscale ilike codFiscale
and l_terzo.decodifica_specie_bdn = codSpecie
and s.trashed_date is null
and o.trashed_date is null
and sogg.trashed_date is null
);

END IF;

if (output_stabid>0) THEN
esiste=true;
END IF;
	
	RETURN esiste;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_esistenza_allevamento(text, text, text)
  OWNER TO postgres;

  -- Function: public.get_esistenza_allevamento(text, text, text, text, text)

-- DROP FUNCTION public.get_esistenza_allevamento(text, text, text, text, text);

CREATE OR REPLACE FUNCTION public.get_esistenza_allevamento(
    codazienda text,
    codfiscale text,
    codspecie text,
    codorientamento text,
    descorientamento text)
  RETURNS boolean AS
$BODY$
   DECLARE
esiste boolean;
output_orgid integer;
output_stabid integer;

BEGIN

esiste:= false;

output_orgid := (select org_id from organization where trashed_date is null and account_number ilike codAzienda and codice_fiscale_rappresentante ilike codFiscale and specie_allevamento = codSpecie::integer and orientamento_prod ilike descOrientamento limit 1);

if (output_orgid>0) THEN
esiste=true;
END IF;

if (esiste is false) THEN

output_stabid:= (
select s.id 
FROM opu_stabilimento s 
LEFT JOIN opu_operatore o on o.id = s.id_operatore
LEFT JOIN opu_rel_operatore_soggetto_fisico orosf on orosf.id_operatore = o.id
LEFT JOIN opu_soggetto_fisico sogg on sogg.id = orosf.id_soggetto_fisico
LEFT JOIN opu_relazione_stabilimento_linee_produttive rel on rel.id_stabilimento = s.id and rel.enabled
LEFT join ml8_linee_attivita_nuove l on l.id_nuova_linea_attivita = rel.id_linea_produttiva
LEFT join ml8_linee_attivita_nuove l_terzo on l_terzo.id_nuova_linea_attivita = l.id_attivita
LEFT join ml8_linee_attivita_nuove l_quarto on l_quarto.id_nuova_linea_attivita::text = split_part(l.path_id, ';', 5)

WHERE 
(rel.codice_nazionale ilike codAzienda or rel.codice_ufficiale_esistente ilike codAzienda)
and sogg.codice_fiscale ilike codFiscale
and l_terzo.decodifica_specie_bdn = codSpecie
and (l_quarto.id_nuova_linea_attivita is null or l_quarto.decodifica_codice_orientamento_bdn = 'M')
and s.trashed_date is null
and o.trashed_date is null
and sogg.trashed_date is null
limit 1
);

END IF;

if (output_stabid>0) THEN
esiste=true;
END IF;
	
	RETURN esiste;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_esistenza_allevamento(text, text, text, text, text)
  OWNER TO postgres;
-- Function: public.gestisci_mapping(integer, integer, integer, integer, integer, integer, boolean)

-- DROP FUNCTION public.gestisci_mapping(integer, integer, integer, integer, integer, integer, boolean);

CREATE OR REPLACE FUNCTION public.gestisci_mapping(
    org_idi integer,
    stabidi integer,
    tipologiai integer,
    id_vecchia_lineai integer,
    id_linea_produttiva_nuova integer,
    id_norma_nuova integer,
    inserisci_entry_perstab boolean)
  RETURNS void AS
$BODY$

declare
	tTemp integer;
	rec3 record;
	tId integer;
	
begin

	--ognuna di queste è un mapping da cui possiamo ottenere informazioni
	--controllo per prima cosa se non esisteva già entry per questo mapping in knowledge_based_mapping
	select count(*) into tTemp from knowledge_based_mapping  where id_vecchia_linea = id_vecchia_lineaI and id_nuova_linea::integer = id_linea_produttiva_nuova;

	if tTemp = 0 then --se non c'era già, aggiungo l'entry prima in knowledge_based_mapping
		select * into rec3 from ml8_linee_attivita_nuove where id_nuova_linea_attivita = id_linea_produttiva_nuova; --per le info su macroarea etc
					
		--raise info 'non esisteva ancora entry di mapping per nuova linea %, % , % , %',rec2.id_linea_produttiva, rec3.macroarea,rec3.aggregazione,rec3.attivita;
					
		insert into knowledge_based_mapping(id_tipologia_operatore,id_vecchia_linea,id_nuova_linea,id_norma,macroarea,
										id_macroarea,aggregazione,id_aggregazione,attivita
										,id_attivita,entered,enteredby,enabled,ranking,note,base_knowledge) 

						values(tipologiaI,id_vecchia_lineaI,id_linea_produttiva_nuova,id_norma_nuova,
						rec3.macroarea,
						rec3.id_macroarea,
						rec3.aggregazione
						,rec3.id_aggregazione
						,rec3.attivita
						,rec3.id_attivita
						,current_timestamp
						,6567
						,true
						,1 -- rank inizializzato a 1
						,null
						,false --non è base knowledge 
					);
					
					--poi nel ranking...
					--recupero id entry appena inserita
					
		select id into tId from knowledge_based_mapping where id_vecchia_linea = id_vecchia_lineaI and id_nuova_linea::integer = id_linea_produttiva_nuova;
		--raise info 'inserita nuova riga (id %) in knowledge_based_mapping per mapping %->%',tId,id_vecchia_lineaI,id_linea_produttiva_nuova;
		 
		 


	else	--allora aggiorno il rank
		select id into tId from knowledge_based_mapping where id_vecchia_linea = id_vecchia_lineaI and id_nuova_linea::integer = id_linea_produttiva_nuova;
		--raise info 'trovata riga esistente (id %) in knowledge_based_mapping per mapping %->%',tId,id_vecchia_lineaI, id_linea_produttiva_nuova;
		update knowledge_based_mapping set ranking = ranking +1 where id = tId;
		
		--raise info 'aggiornato ranking per id di knowledge based mapping %',tId;
	end if;
				
	--in ogni caso devo agganciare orgid e stabid alla nuova entry di mapping(o a quella che già esisteva ed e' stata incrementata)
	--se il flag inserisci_entry_perstab è true
	if inserisci_entry_perstab = true
	then			
		insert into org_importati_to_mapping values(org_idI,stabidI, tId);
		--raise info 'inserito entry in tabella orgid->mapping id %',tId;	
	end if;
	

end;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.gestisci_mapping(integer, integer, integer, integer, integer, integer, boolean)
  OWNER TO postgres;

  -- Function: public.dbi_get_controlli_ufficiali_su_linee_produttive(integer)

-- DROP FUNCTION public.dbi_get_controlli_ufficiali_su_linee_produttive(integer);

CREATE OR REPLACE FUNCTION public.dbi_get_controlli_ufficiali_su_linee_produttive(IN idstabilimento integer)
  RETURNS TABLE(idcontrollo integer, id_stabilimento integer, id_rel_stab_lp_out integer, id_linea_master_list_out integer, enabled_out boolean, descrizione_out text, id_norma_out integer, id_stato_out integer) AS
$BODY$
DECLARE
	
	 	
BEGIN
		FOR
		idcontrollo,id_stabilimento,id_rel_stab_lp_out ,id_linea_master_list_out ,enabled_out ,descrizione_out, id_norma_out, id_stato_out 
		in

		select distinct cu.ticketid,cu.id_stabilimento,r.id as id_rel_stab_lp ,r.id_linea_produttiva as id_linea_master_list,r.enabled,path_descrizione, ll.id_norma, r.stato

from opu_relazione_stabilimento_linee_produttive r 
left join opu_linee_attivita_controlli_ufficiali on  r.id = opu_linee_attivita_controlli_ufficiali.id_linea_attivita 
left join ticket cu on cu.ticketid = opu_linee_attivita_controlli_ufficiali.id_controllo_ufficiale and cu.id_stabilimento = r.id_stabilimento and cu.trashed_date is null
left join ml8_linee_attivita_nuove ll on ll.id_nuova_linea_attivita=r.id_linea_produttiva
where r.id_stabilimento =idstabilimento and r.enabled
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.dbi_get_controlli_ufficiali_su_linee_produttive(integer)
  OWNER TO postgres;

  -- Function: public.suap_query_richiesta(integer, integer)

-- DROP FUNCTION public.suap_query_richiesta(integer, integer);

CREATE OR REPLACE FUNCTION public.suap_query_richiesta(
    IN idtiporichiesta integer,
    IN idrichiesta integer)
  RETURNS TABLE(tipo_richiesta text, tipo_impresa text, tipo_societa text, id_richiesta integer, ragione_sociale text, partita_iva text, codice_fiscale_impresa text, comune_sede_legale text, istat_legale text, cap_sede_legale text, prov_sede_legale text, domicilio_digitale text, cf_rapp_sede_legale text, nome_rapp_sede_legale text, cognome_rapp_sede_legale text, indirizzo_rapp_sede_legale text, comune_stab text, indirizzo_stab text, cap_stab text, prov_stab text, tipo_attivita text, carattere text, data_inizio_attivita timestamp without time zone, data_fine_attivita timestamp without time zone, macroarea text, aggregazione text, linea_attivita text, indirizzo_sede_legale text, istat_operativo character varying, id_macroarea text, id_aggregazione text, id_nuova_linea_attivita text, lat_stab double precision, long_stab double precision, sesso_rappr character, data_nascita_rappr timestamp without time zone, nazione_nascita_rappr text, comune_nascita_rappr text, nazione_residenza_rappr character varying, provincia_residenza_rappr character, comune_residenza_rappr character varying, cap_residenza_rappr character) AS
$BODY$
BEGIN
FOR Tipo_richiesta, tipo_impresa,tipo_societa, id_richiesta, ragione_sociale, partita_iva, codice_fiscale_impresa,comune_sede_legale, istat_legale, cap_sede_legale, prov_sede_legale,domicilio_digitale, cf_rapp_sede_legale, nome_rapp_sede_legale, cognome_rapp_sede_legale, indirizzo_rapp_sede_legale,comune_stab, indirizzo_stab, cap_stab, prov_stab,tipo_attivita ,carattere, data_inizio_attivita,data_fine_attivita,macroarea,aggregazione,linea_attivita,indirizzo_sede_legale, istat_operativo, id_macroarea, id_aggregazione, id_nuova_linea_attivita, lat_stab, long_stab

	,sesso_rappr, data_nascita_rappr, nazione_nascita_rappr,comune_nascita_rappr, nazione_residenza_rappr, provincia_residenza_rappr, comune_residenza_rappr, cap_residenza_rappr
		in
--select * from ml8_linee_attivita_nuove_materializzata limit 1
--select * from master_list_suap limit 1  
--select * from suap_ric_scia_operatori_denormalizzati_view
select distinct op.description as Tipo_richiesta, s.tipo_impresa,s.tipo_societa, s.id_opu_operatore as id_richiesta, s.ragione_sociale, s.partita_iva, s.codice_fiscale_impresa,s.comune_sede_legale, s.istat_legale, s.cap_sede_legale, s.prov_sede_legale,s.domicilio_digitale, s.cf_rapp_sede_legale, s.nome_rapp_sede_legale, s.cognome_rapp_sede_legale, s.indirizzo_rapp_sede_legale
	,CASE WHEN s.comune_stab = 'n.d' THEN NULL END as comune_stab, s.indirizzo_stab, s.cap_stab, s.prov_stab,lta.description as tipo_attivita ,ltc.description as carattere, s.data_inizio_attivita,s.data_fine_attivita,s.macroarea,s.aggregazione,s.linea_attivita 
	,s.indirizzo_sede_legale
	,CASE WHEN s.istat_operativo::integer = -1 THEN NULL END as istat_operativo, linee.id_macroarea,linee.id_aggregazione, linee.id_nuova_linea_attivita
	,s.lat_stab
	,s.long_stab
	
	,osf.sesso
	,osf.data_nascita, 
	CASE WHEN osf.provenienza_estera = true THEN 'altro' ELSE 'Italia' END AS nazione_nascita_rappr
	,osf.comune_nascita
	,oi.nazione
	,lp.description --provincia residenza rapp legale
	,com.nome
	,oi.cap
 from  suap_ric_scia_operatori_denormalizzati_view s 
 left join suap_ric_scia_soggetto_fisico osf on s.cf_rapp_sede_legale = osf.codice_fiscale
 left join opu_indirizzo oi on osf.indirizzo_id = oi.id
 left join comuni1 com on oi.comune = com.id
 left join lookup_province lp on lp.code::text = oi.provincia
 join opu_lookup_tipologia_attivita lta on lta.code = s.stab_id_attivita 
 join opu_lookup_tipologia_carattere ltc on ltc.code = s.stab_id_carattere 
 join suap_lookup_tipo_richiesta op on op.code = s.id_tipo_richiesta
 left join ml8_linee_attivita_nuove linee on s.id_linea_attivita_stab = linee.id_nuova_linea_attivita
where 1=1

AND (
    (idRichiesta>-1 AND id_opu_operatore = idRichiesta)
     OR (idRichiesta=-1)
  )

AND (
    (idTipoRichiesta>-1 AND id_tipo_richiesta = idTipoRichiesta)
     OR (idTipoRichiesta=-1)
  )

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.suap_query_richiesta(integer, integer)
  OWNER TO postgres;

  -- Function: public.suap_query_validazione_scia_richiesta_perlinee(integer, integer)

-- DROP FUNCTION public.suap_query_validazione_scia_richiesta_perlinee(integer, integer);

CREATE OR REPLACE FUNCTION public.suap_query_validazione_scia_richiesta_perlinee(
    IN idstabilimento integer,
    IN idrichiestasuap integer)
  RETURNS TABLE(numero_registrazione text, codice_nazionale text, macroarea text, aggregazione text, attivita text, id_macroarea text, id_aggregazione text, id_nuova_linea_attivita text) AS
$BODY$
BEGIN
FOR  numero_registrazione, codice_nazionale, macroarea , aggregazione , attivita ,id_macroarea , id_aggregazione , id_nuova_linea_attivita 

IN


select distinct o.linea_numero_registrazione, o.linea_codice_nazionale, replace(o.macroarea,'->','-'), replace(o.aggregazione,'->','|'), replace(o.attivita,'->','|') , ola.id_macroarea, ola.id_aggregazione, o.id_linea_attivita_stab


from opu_operatori_denormalizzati_view o 
 left join opu_soggetto_fisico osf on o.cf_rapp_sede_legale = osf.codice_fiscale
 left join opu_indirizzo oi on osf.indirizzo_id = oi.id
 left join comuni1 com on oi.comune = com.id
 left join lookup_province lp on oi.provincia = lp.code::text
join suap_opu_relazione_richiesta_id_opu_rel_stab_lp rel on o.id_linea_attivita=rel.id_opu_rel_stab_lp
join suap_ric_scia_operatore ric on ric.id = rel.id_suap_ric_scia_operatore
join suap_lookup_tipo_richiesta tr on tr.code = ric.id_tipo_richiesta
join ml8_linee_attivita_nuove ola on ola.id_nuova_linea_attivita = o.id_linea_attivita_stab
--join opu_relazione_stabilimento_linee_produttive srsr on o.id_stabilimento = srsr.id_stabilimento
--where rel.id_suap_ric_scia_operatore=2801

and 1=1
--select * from opu_operatori_denormalizzati_view order by id_opu_operatore desc  
AND (
    (idStabilimento>-1 AND o.id_stabilimento = idstabilimento)
     OR (idStabilimento=-1)
  )

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.suap_query_validazione_scia_richiesta_perlinee(integer, integer)
  OWNER TO postgres;

  -- Function: public_functions.cu_dati_linea(integer)

-- DROP FUNCTION public_functions.cu_dati_linea(integer);

CREATE OR REPLACE FUNCTION public_functions.cu_dati_linea(IN id_controllo integer)
  RETURNS TABLE("Linea sottoposta a controllo" text) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
FOR "Linea sottoposta a controllo"
in
SELECT 
cod.description || ' : ' || cod.short_description || ' <br/> ' || la.categoria || ' : ' || la.linea_attivita as linea_sottoposta 
FROM la_imprese_linee_attivita i  
left join ml8_linee_attivita_nuove opu on opu.id_attivita = i.id_attivita_masterlist and opu.id_norma =1, linee_attivita_controlli_ufficiali lacu, la_rel_ateco_attivita rel, la_linee_attivita la, lookup_codistat cod  
WHERE i.id=lacu.id_linea_attivita  
AND i.id_rel_ateco_attivita=rel.id  
AND rel.id_linee_attivita=la.id  
AND rel.id_lookup_codistat=cod.code  
and(
  (id_controllo>-1 and lacu.id_controllo_ufficiale = id_controllo)
 or (id_controllo=-1)
  )

    UNION

  SELECT linea_attivita_stabilimenti_soa || ' <br/> ' ||  linea_attivita_stabilimenti_soa_desc FROM linee_attivita_controlli_ufficiali_stab_soa where 1=1
  and(
  (id_controllo>-1 and id_controllo_ufficiale = id_controllo)
 or (id_controllo=-1)
  )

  
 LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.cu_dati_linea(integer)
  OWNER TO postgres;

  -- Function: public_functions.dbi_get_controlli_ufficiali_eseguiti_nuova_anagrafica(timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_nuova_anagrafica(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_nuova_anagrafica(
    IN data_1 timestamp without time zone,
    IN data_2 timestamp without time zone)
  RETURNS TABLE(id_controllo_ufficiale integer, riferimento_id integer, riferimento_id_nome text, riferimento_id_nome_col text, riferimento_id_nome_tab text, id_asl integer, asl character varying, tipo_controllo character varying, tipo_ispezione_o_audit text, tipo_piano_monitoraggio text, id_piano integer, id_attivita integer, tipo_controllo_bpi text, tipo_controllo_haccp text, oggetto_del_controllo text, punteggio text, data_inizio_controllo date, anno_controllo text, data_chiusura_controllo date, aggiornata_cat_controllo text, categoria_rischio integer, prossimo_controllo timestamp with time zone, tipo_checklist text, linea_attivita_sottoposta_a_controllo text, unita_operativa text, id_struttura_uo integer, supervisionato_in_data timestamp without time zone, supervisionato_da character varying, supervisione_note text, congruo_supervisione text, note text, tipo_piano_monitoraggio_old text, codice_interno_univoco_uo integer, codice_interno_piano text, area_appartenenza_uo text, id_linea_controllata integer) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR 
		id_controllo_ufficiale,
		riferimento_id,
		riferimento_id_nome,
		riferimento_id_nome_col,
		riferimento_id_nome_tab,
		id_asl,  
		asl , 
		tipo_controllo  , 
		tipo_ispezione_o_audit , 
		tipo_piano_monitoraggio ,
		id_piano, id_attivita,
		tipo_controllo_bpi , tipo_controllo_haccp , 
		oggetto_del_controllo , punteggio , 
		data_inizio_controllo , anno_controllo,  data_chiusura_controllo, aggiornata_cat_controllo, categoria_rischio, prossimo_controllo, 
		tipo_checklist, 
		linea_attivita_sottoposta_a_controllo,
		unita_operativa, id_struttura_uo, supervisionato_in_data, supervisionato_da, supervisione_note, congruo_supervisione,note,  
		tipo_piano_monitoraggio_old,
		codice_interno_univoco_uo,codice_interno_piano,
		area_appartenenza_uo,id_linea_controllata
		in

select t.ticketid,
 t.id_stabilimento AS riferimento_id,
            'stabId'::text AS riferimento_id_nome,
            'id_stabilimento'::text AS riferimento_id_nome_col,
            'opu_stabilimento'::text AS riferimento_id_nome_tab,
            asl.code AS id_asl,
            asl.description AS asl,
            ltc.description AS tipo_controllo,
                CASE
                    WHEN tcu.audit_tipo > 0 AND tcu.tipo_audit <= 0 THEN lat.description::text
                    WHEN tcu.tipo_audit > 0 THEN (lat.description::text || ' - '::text) || lta.description::text
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio > 0 THEN (dpatp.description || ' '::text) || dpatatt.description
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio <= 0 AND tcu.tipoispezione <> 89 AND tcu.tipoispezione <> 2 THEN (dpatpti.description || ' '::text) || dpatattti.description
                    ELSE 'N.D'::text
                END AS tipo_ispezione_o_audit,
                CASE
                    WHEN tcu.tipoispezione > 0 AND lti.codice_interno !~~* '2a'::text THEN (indti.alias || ' : '::text) || indti.description
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 THEN (ind.alias || ' : '::text) || ind.description
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio,
               
                CASE
                    WHEN t.assigned_date > '2015-01-01 00:00:00'::timestamp without time zone THEN lpiani.code
                    ELSE lpiani.code
                END AS id_piano,
                CASE
                    WHEN t.assigned_date > '2015-01-01 00:00:00'::timestamp without time zone THEN lti.id_indicatore
                    ELSE lti.code
                END AS id_attivita,
            CASE
            WHEN tcu.tipo_audit = 2 AND t.provvedimenti_prescrittivi = 3 THEN 'BPI-'::text || lbpi.description::text
            ELSE 'N.D'::text
        END AS tipo_controllo_bpi,
        CASE
            WHEN tcu.tipo_audit = 3 AND t.provvedimenti_prescrittivi = 3 THEN 'HACCP-'::text || lhaccp.description::text
            ELSE 'N.D'::text
        END AS tipo_controllo_haccp,
               CASE
            WHEN oggcu.ispezione > 0 THEN (lim.description_old::text || ': '::text) || lisp.description::text
            ELSE 'N.D'::text
        END  AS oggetto_del_controllo,
                CASE
                    WHEN t.punteggio < 3 THEN 'Favorevole'::text
                    WHEN t.punteggio >= 3 THEN 'Non Favorevole'::text
                    ELSE 'N.D.'::text
                END AS punteggio,
            to_date(t.assigned_date::text, 'yyyy/mm/dd'::text) AS data_inizio_controllo,
                CASE
                    WHEN t.assigned_date IS NULL THEN 'N.D.'::text
                    ELSE date_part('year'::text, t.assigned_date)::text
                END AS anno_controllo,
            to_date(t.closed::text, 'yyyy/mm/dd'::text) AS data_chiusura_controllo,
                CASE
                    WHEN t.isaggiornata_categoria = true AND t.provvedimenti_prescrittivi = 5 THEN 'CONTROLLO CATEGORIZZATO'::text
                    WHEN t.isaggiornata_categoria = false AND t.provvedimenti_prescrittivi = 5 THEN 'CONTROLLO NON CATEGORIZZATO'::text
                    WHEN t.provvedimenti_prescrittivi <> 5 THEN 'NON PREVISTA CATEGORIZZAZIONE'::text
                    ELSE NULL::text
                END AS aggiornata_cat_controllo,
            t.categoria_rischio,
            t.data_prossimo_controllo,
                CASE
                    WHEN audit.c IS NOT NULL THEN audit.c
                    ELSE 'Non Presente'::text
                END AS tipo_checklist,
                   (((
                CASE
                    WHEN lattcu.macroarea IS NOT NULL THEN lattcu.macroarea
                    ELSE ''::text
                END || '|'::text) ||
                CASE
                    WHEN lattcu.aggregazione IS NOT NULL THEN lattcu.aggregazione
                    ELSE ''::text
                END) || '|'::text) || lattcu.attivita AS linea_attivita_sottoposta_a_controllo,
                CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura::text
                        ELSE ''::text
                    END || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura::text
                        ELSE ''::text
                    END || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    ELSE NULL::text
                END AS unita_operativa,
               
                CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.id
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.id
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.id
                    ELSE '-1'::integer
                END AS id_struttura_uo,
            t.supervisionato_in_data,
                CASE
                    WHEN c.namelast IS NOT NULL AND c.namefirst IS NOT NULL THEN (c.namelast::text || c.namefirst::text)::character varying
                    WHEN c.namelast IS NOT NULL AND c.namefirst IS NULL THEN c.namelast
                    ELSE c.namefirst
                END AS supervisionato_da,
            t.supervisione_note,
                CASE
                    WHEN t.supervisione_flag_congruo THEN 'SI'::text
                    WHEN t.supervisione_flag_congruo = false THEN 'NO'::text
                    ELSE 'N.D.'::text
                END AS congruo_supervisione,
            t.problem AS note,
 CASE
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 AND lpiani.enabled = false THEN
                    CASE
                        WHEN lpianipadre.description::text IS NOT NULL THEN lpianipadre.description::text
                        ELSE ''::text
                    END ||
                    CASE
                        WHEN lpiani.description::text IS NOT NULL THEN lpiani.description::text
                        ELSE ''::text
                    END
                    WHEN lti.codice_interno !~~* '2a'::text THEN lti.description::text
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio_old,
            
                CASE
                    WHEN nn.codice_interno_fk > 0 THEN nn.codice_interno_fk
                    ELSE n.codice_interno_fk
                END AS codice_interno_univoco_uo,
            COALESCE(lpiani.codice, lti.codice) AS codice_interno_piano_attivita,
             CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.descrizione_area_struttura
                    ELSE NULL::character varying(50)
                END AS descrizione_area_struttura,orslp.id as id_linea_controllo
               
 FROM ticket t
             LEFT JOIN contact_ c ON c.user_id = t.supervisionato_da
             LEFT JOIN checklist_controlli audit ON audit.id_controllo::text = t.id_controllo_ufficiale::text
             JOIN lookup_site_id asl ON t.site_id = asl.code
             JOIN lookup_tipo_controllo ltc ON t.provvedimenti_prescrittivi = ltc.code
             LEFT JOIN tipocontrolloufficialeimprese tcu ON t.ticketid = tcu.idcontrollo AND (tcu.tipoispezione > 0 OR tcu.audit_tipo > 0 OR tcu.tipo_audit > 0) AND tcu.enabled = true
	   LEFT JOIN tipocontrolloufficialeimprese oggcu ON t.ticketid = oggcu.idcontrollo AND oggcu.ispezione > 0
     LEFT JOIN lookup_ispezione lisp ON oggcu.ispezione = lisp.code
     LEFT JOIN lookup_ispezione_macrocategorie lim ON lim.code = lisp.level AND lim.enabled
	     LEFT JOIN lookup_bpi lbpi ON tcu.bpi = lbpi.code
	     LEFT JOIN lookup_haccp lhaccp ON tcu.haccp = lhaccp.code
	     --LEFT JOIN lookup_ispezione_macrocategorie lim ON lim.code = lisp.level
	
           LEFT JOIN opu_linee_attivita_controlli_ufficiali lacc ON lacc.id_controllo_ufficiale = t.ticketid
             LEFT JOIN opu_relazione_stabilimento_linee_produttive orslp ON orslp.id = lacc.id_linea_attivita
             LEFT JOIN ml8_linee_attivita_nuove lattcu ON orslp.id_linea_produttiva = lattcu.id_nuova_linea_attivita
             LEFT JOIN ml8_linee_attivita_nuove latt ON latt.id_nuova_linea_attivita = orslp.id_linea_produttiva
         
             LEFT JOIN lookup_tipo_ispezione lti ON tcu.tipoispezione = lti.code
             LEFT JOIN lookup_audit_tipo lat ON tcu.audit_tipo = lat.code
             LEFT JOIN lookup_tipo_audit lta ON tcu.tipo_audit = lta.code
             LEFT JOIN lookup_piano_monitoraggio lpiani ON tcu.pianomonitoraggio = lpiani.code AND t.assigned_date <= COALESCE(lpiani.data_scadenza, 'now'::text::date::timestamp without time zone)
             LEFT JOIN dpat_indicatore ind ON ind.id_ = lpiani.id_indicatore
             LEFT JOIN dpat_attivita dpatatt ON dpatatt.id_ = ind.id_attivita_
             LEFT JOIN dpat_piano dpatp ON dpatp.id_ = dpatatt.id_piano_
             LEFT JOIN dpat_indicatore indti ON indti.id_ = lti.id_indicatore
             LEFT JOIN dpat_attivita dpatattti ON dpatattti.id_ = indti.id_attivita_
             LEFT JOIN dpat_piano dpatpti ON dpatpti.id_ = dpatattti.id_piano_
             LEFT JOIN lookup_piano_monitoraggio lpianipadre ON lpiani.id_padre = lpianipadre.code AND lpianipadre.id_padre <= 0
             LEFT JOIN dpat_strutture_asl n ON tcu.id_unita_operativa = n.id
             LEFT JOIN dpat_strutture_asl pp ON n.id_padre = pp.id
             LEFT JOIN unita_operative_controllo uoc ON uoc.id_controllo = t.ticketid
             LEFT JOIN dpat_strutture_asl nn ON nn.id = uoc.id_unita_operativa
             LEFT JOIN dpat_strutture_asl ppp ON nn.id_padre = ppp.id
  WHERE t.tipologia = 3 and t.assigned_date  between data_1 and data_2  AND t.trashed_date IS NULL AND t.id_stabilimento > 0
		

		
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_nuova_anagrafica(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

  -- Function: public_functions.dbi_get_controlli_ufficiali_eseguiti_nuova_anagraficanew(timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_nuova_anagraficanew(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_nuova_anagraficanew(
    IN data_1 timestamp without time zone,
    IN data_2 timestamp without time zone)
  RETURNS TABLE(id_controllo_ufficiale integer, riferimento_id integer, riferimento_id_nome text, riferimento_id_nome_col text, riferimento_id_nome_tab text, id_asl integer, asl character varying, tipo_controllo character varying, tipo_ispezione_o_audit text, tipo_piano_monitoraggio text, id_piano integer, id_attivita integer, tipo_controllo_bpi text, tipo_controllo_haccp text, oggetto_del_controllo text, punteggio text, data_inizio_controllo date, anno_controllo text, data_chiusura_controllo date, aggiornata_cat_controllo text, categoria_rischio integer, prossimo_controllo timestamp with time zone, tipo_checklist text, linea_attivita_sottoposta_a_controllo text, unita_operativa text, id_struttura_uo integer, supervisionato_in_data timestamp without time zone, supervisionato_da character varying, supervisione_note text, congruo_supervisione text, note text, tipo_piano_monitoraggio_old text, codice_interno_univoco_uo integer, codice_interno_piano text, area_appartenenza_uo text, id_linea_controllata integer) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR 
		id_controllo_ufficiale,
		riferimento_id,
		riferimento_id_nome,
		riferimento_id_nome_col,
		riferimento_id_nome_tab,
		id_asl,  
		asl , 
		tipo_controllo  , 
		tipo_ispezione_o_audit , 
		tipo_piano_monitoraggio ,
		id_piano, id_attivita,
		tipo_controllo_bpi , tipo_controllo_haccp , 
		oggetto_del_controllo , punteggio , 
		data_inizio_controllo , anno_controllo,  data_chiusura_controllo, aggiornata_cat_controllo, categoria_rischio, prossimo_controllo, 
		tipo_checklist, 
		linea_attivita_sottoposta_a_controllo,
		unita_operativa, id_struttura_uo, supervisionato_in_data, supervisionato_da, supervisione_note, congruo_supervisione,note,  
		tipo_piano_monitoraggio_old,
		codice_interno_univoco_uo,codice_interno_piano,
		area_appartenenza_uo,id_linea_controllata
		in
		
		select t.ticketid,
		t.id_stabilimento AS riferimento_id,
             'stabId'::text AS riferimento_id_nome,
            'id_stabilimento'::text AS riferimento_id_nome_col,
            'opu_stabilimento'::text AS riferimento_id_nome_tab,
            asl.code AS id_asl,
            asl.description AS asl,
            ltc.description AS tipo_controllo,

  CASE
                    WHEN tcu.audit_tipo > 0 AND tcu.tipo_audit <= 0 THEN lat.description::text
                    WHEN tcu.tipo_audit > 0 THEN (lat.description::text || ' - '::text) || lta.description::text
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio > 0 then  (CASE WHEN dp.tipo_attivita_piano ilike '%attivit%' THEN 'ATTIVITA ' ELSE 'PIANO ' END ) || dp.alias_attivita || ' ' || dp.descrizione_attivita
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio <= 0 AND tcu.tipoispezione <> 89 AND tcu.tipoispezione <> 2 
                    THEN (CASE WHEN da.tipo_attivita_piano ilike '%attivit%' THEN 'ATTIVITA ' ELSE 'PIANO ' END ) || da.alias_attivita || ' ' || da.descrizione_attivita
                    ELSE 'N.D'::text
                END AS tipo_ispezione_o_audit,
                CASE
                    WHEN tcu.tipoispezione > 0 AND lti.codice_interno !~~* '2a'::text THEN concat_ws(': ',da.alias_indicatore,da.descrizione_indicatore)
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 THEN concat_ws(': ',dp.alias_indicatore,dp.descrizione_indicatore)
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio,
                dp.id_fisico_indicatore AS id_piano,
                case when dp.id_fisico_indicatore > 0 then null
                else da.id_fisico_indicatore
                end as id_attivita,
            CASE
            WHEN tcu.tipo_audit = 2 AND t.provvedimenti_prescrittivi = 3 THEN 'BPI-'::text || lbpi.description::text
            ELSE 'N.D'::text
        END AS tipo_controllo_bpi,
        CASE
            WHEN tcu.tipo_audit = 3 AND t.provvedimenti_prescrittivi = 3 THEN 'HACCP-'::text || lhaccp.description::text
            ELSE 'N.D'::text
        END AS tipo_controllo_haccp,
               CASE
            WHEN oggcu.ispezione > 0 THEN (lim.description_old::text || ': '::text) || lisp.description::text
            ELSE 'N.D'::text
        END  AS oggetto_del_controllo,
                CASE
                    WHEN t.punteggio < 3 THEN 'Favorevole'::text
                    WHEN t.punteggio >= 3 THEN 'Non Favorevole'::text
                    ELSE 'N.D.'::text
                END AS punteggio,
            to_date(t.assigned_date::text, 'yyyy/mm/dd'::text) AS data_inizio_controllo,
                CASE
                    WHEN t.assigned_date IS NULL THEN 'N.D.'::text
                    ELSE date_part('year'::text, t.assigned_date)::text
                END AS anno_controllo,
            to_date(t.closed::text, 'yyyy/mm/dd'::text) AS data_chiusura_controllo,
                CASE
                    WHEN t.isaggiornata_categoria = true AND t.provvedimenti_prescrittivi = 5 THEN 'CONTROLLO CATEGORIZZATO'::text
                    WHEN t.isaggiornata_categoria = false AND t.provvedimenti_prescrittivi = 5 THEN 'CONTROLLO NON CATEGORIZZATO'::text
                    WHEN t.provvedimenti_prescrittivi <> 5 THEN 'NON PREVISTA CATEGORIZZAZIONE'::text
                    ELSE NULL::text
                END AS aggiornata_cat_controllo,
            t.categoria_rischio,
            t.data_prossimo_controllo,
                CASE
                    WHEN audit.c IS NOT NULL and audit.c = 'T' THEN 'Temporanea'
                    WHEN audit.c IS NOT NULL and audit.c = 'D' THEN 'Definitiva'
                    WHEN audit.c IS NULL or audit.c = '' THEN 'Presenti più checklist'
                    ELSE 'Non Presente'::text
                END AS tipo_checklist,
                   (((
                CASE
                    WHEN lattcu.macroarea IS NOT NULL THEN lattcu.macroarea
                    ELSE ''::text
                END || '|'::text) ||
                CASE
                    WHEN lattcu.aggregazione IS NOT NULL THEN lattcu.aggregazione
                    ELSE ''::text
                END) || '|'::text) || lattcu.attivita AS linea_attivita_sottoposta_a_controllo,
                CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura::text
                        ELSE ''::text
                    END || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura::text
                        ELSE ''::text
                    END || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    ELSE NULL::text
                END AS unita_operativa,
               
                CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.id
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.id
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.id
                    ELSE '-1'::integer
                END AS id_struttura_uo,
            t.supervisionato_in_data,
                CASE
                    WHEN c.namelast IS NOT NULL AND c.namefirst IS NOT NULL THEN (c.namelast::text || c.namefirst::text)::character varying
                    WHEN c.namelast IS NOT NULL AND c.namefirst IS NULL THEN c.namelast
                    ELSE c.namefirst
                END AS supervisionato_da,
            t.supervisione_note,
                CASE
                    WHEN t.supervisione_flag_congruo THEN 'SI'::text
                    WHEN t.supervisione_flag_congruo = false THEN 'NO'::text
                    ELSE 'N.D.'::text
                END AS congruo_supervisione,
            t.problem AS note,
 CASE
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 AND lpiani.enabled = false THEN
                    CASE
                        WHEN lpianipadre.description::text IS NOT NULL THEN lpianipadre.description::text
                        ELSE ''::text
                    END ||
                    CASE
                        WHEN lpiani.description::text IS NOT NULL THEN lpiani.description::text
                        ELSE ''::text
                    END
                    WHEN lti.codice_interno !~~* '2a'::text THEN lti.description::text
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio_old,
            
                CASE
                    WHEN nn.codice_interno_fk > 0 THEN nn.codice_interno_fk
                    ELSE n.codice_interno_fk
                END AS codice_interno_univoco_uo,
            COALESCE(lpiani.codice, lti.codice) AS codice_interno_piano_attivita,
             CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.descrizione_area_struttura
                    ELSE NULL::character varying(50)
                END AS descrizione_area_struttura,orslp.id as id_linea_controllo
 from ticket t              
 LEFT JOIN contact_ c ON c.user_id = t.supervisionato_da
             LEFT JOIN checklist_controlli audit ON audit.id_controllo::text = t.id_controllo_ufficiale::text
             JOIN lookup_site_id asl ON t.site_id = asl.code
             JOIN lookup_tipo_controllo ltc ON t.provvedimenti_prescrittivi = ltc.code
             LEFT JOIN tipocontrolloufficialeimprese tcu ON t.ticketid = tcu.idcontrollo AND (tcu.tipoispezione > 0 OR tcu.audit_tipo > 0 OR tcu.tipo_audit > 0) AND tcu.enabled = true
	     LEFT JOIN tipocontrolloufficialeimprese oggcu ON t.ticketid = oggcu.idcontrollo AND oggcu.ispezione > 0
	     LEFT JOIN lookup_ispezione lisp ON oggcu.ispezione = lisp.code
             LEFT JOIN lookup_ispezione_macrocategorie lim ON lim.code = lisp.level AND lim.enabled
	     LEFT JOIN lookup_bpi lbpi ON tcu.bpi = lbpi.code
	     LEFT JOIN lookup_haccp lhaccp ON tcu.haccp = lhaccp.code
             LEFT JOIN opu_linee_attivita_controlli_ufficiali lacc ON lacc.id_controllo_ufficiale = t.ticketid
             LEFT JOIN opu_relazione_stabilimento_linee_produttive orslp ON orslp.id = lacc.id_linea_attivita
             LEFT JOIN ml8_linee_attivita_nuove lattcu ON orslp.id_linea_produttiva = lattcu.id_nuova_linea_attivita
             LEFT JOIN ml8_linee_attivita_nuove latt ON latt.id_nuova_linea_attivita = orslp.id_linea_produttiva
             LEFT JOIN lookup_tipo_ispezione lti ON tcu.tipoispezione = lti.code
             LEFT JOIN lookup_audit_tipo lat ON tcu.audit_tipo = lat.code 
             LEFT JOIN lookup_tipo_audit lta ON tcu.tipo_audit = lta.code  
             LEFT JOIN lookup_piano_monitoraggio lpiani ON tcu.pianomonitoraggio = lpiani.code AND t.assigned_date <= COALESCE(lpiani.data_scadenza, 'now'::text::date::timestamp without time zone)
             LEFT JOIN lookup_piano_monitoraggio lpianipadre ON lpiani.id_padre = lpianipadre.code AND lpianipadre.id_padre <= 0
		
	    -- JOIN NEW 
	    LEFT JOIN view_motivi_linearizzati_dpat da on da.id_fisico_indicatore = tcu.tipoispezione 
	    LEFT JOIN view_motivi_linearizzati_dpat dp on dp.id_fisico_indicatore = tcu.pianomonitoraggio 

	    --fine join new
             LEFT JOIN dpat_strutture_asl n ON tcu.id_unita_operativa = n.id
             LEFT JOIN dpat_strutture_asl pp ON n.id_padre = pp.id
             LEFT JOIN unita_operative_controllo uoc ON uoc.id_controllo = t.ticketid
             LEFT JOIN dpat_strutture_asl nn ON nn.id = uoc.id_unita_operativa
             LEFT JOIN dpat_strutture_asl ppp ON nn.id_padre = ppp.id
	     WHERE t.tipologia = 3 and t.assigned_date  
	     between data_1 and data_2  AND t.trashed_date IS NULL 
	     AND t.id_stabilimento > 0 
	
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_nuova_anagraficanew(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

  -- Function: public_functions.suap_aggiorna_categoria_default(integer)

-- DROP FUNCTION public_functions.suap_aggiorna_categoria_default(integer);

CREATE OR REPLACE FUNCTION public_functions.suap_aggiorna_categoria_default(idstab integer)
  RETURNS boolean AS
$BODY$
DECLARE
tipoLineaInStabilimento integer;

BEGIN
tipoLineaInStabilimento:=(select max(id_tipo_linea_produttiva) from opu_relazione_stabilimento_linee_produttive t
join ml8_linee_attivita_nuove l on l.id_nuova_linea_attivita = t.id_linea_produttiva
where t.id_stabilimento = idstab );

if tipoLineaInStabilimento=2
then
update opu_stabilimento set categoria_rischio = 3, data_prossimo_controllo=data_inizio_attivita + INTERVAL ' 36 MONTH' where id = idstab;
else
update opu_stabilimento set categoria_rischio = 2, data_prossimo_controllo=data_inizio_attivita + INTERVAL ' 48 MONTH' where id = idstab;

end if ;


return true ;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public_functions.suap_aggiorna_categoria_default(integer)
  OWNER TO postgres;

  -- Function: public_functions.suap_insert_attivita_apicoltura_da_richiesta(integer, integer)

-- DROP FUNCTION public_functions.suap_insert_attivita_apicoltura_da_richiesta(integer, integer);

CREATE OR REPLACE FUNCTION public_functions.suap_insert_attivita_apicoltura_da_richiesta(
    id_richiesta_suap integer,
    idutenteasl integer)
  RETURNS boolean AS
$BODY$
DECLARE
proprietarioapicoltore persona ;
partita_iva_ric text;
sedelegale integer ;
tipoimpresa integer;
idAsl integer;
idimpresaApicoltura integer;

idrelsedelegale integer;
idrelsf integer;

verificaesistenzaApicoltore integer;

BEGIN

/*A PARTIRE DALLA RICHIESTA RECUPERO/ INSERISCO IL SOGGETTOFISICO*/

proprietarioapicoltore:=(select   public_functions.suap_insert_soggetto_fisico(id_richiesta_suap, -1, idutenteasl))  ;

raise notice 'INSERIMENTO SOGGETTO FISICO : %',proprietarioapicoltore;


select partita_iva into partita_iva_ric from suap_ric_scia_operatore  where id = id_richiesta_suap ;

raise notice 'partita_iva : %',partita_iva_ric;

/*RECUPERO L'INDIRIZZO DELLA SEDE LEGALE A PARTIRE DALLA RICHIESTA :
	SE IL TIPO DI IMPRESA è INDIVIDUALE LA SEDE LEGALE è UGUALE ALLA RESIDENZA DEL PROPRIETARIO
	ALTRIMENTI LA RECUPERO DALLA SEDE LEGALE DELLA RICHIESA
*/
select tipo_impresa into tipoimpresa from suap_ric_scia_operatore where id = id_richiesta_suap;

raise notice '--> RECUPERO SEDE LEGALE IN BASE AL TIPO DI IMPRESA: %',tipoimpresa;

if tipoimpresa=1
then
sedelegale:=proprietarioapicoltore.id_indirizzo ;
else
sedelegale:=(select id_indirizzo from suap_ric_scia_operatore where id =id_richiesta_suap);
end if ;

raise info 'RECUPERO ID ASL';

idAsl:=(select codiceistatasl::int from comuni1 where id = (select comune from opu_indirizzo where id = sedelegale));


select count(*) from apicoltura_imprese where codice_fiscale_impresa ilike partita_iva_ric and stato !=4 and stato !=3 and trashed_date is null into verificaesistenzaApicoltore ;

raise notice  'VERIFICA ESISTENZA IN APICOLTURA IMPRESA - num_record: %   - CF: %'  , verificaesistenzaApicoltore, proprietarioapicoltore.cf ;

if verificaesistenzaApicoltore>0

then
raise info 'GIA'' ESISTE: NESSUN INSERIMENTO IN APICOLTURA_IMPRESA';
return false;
else
/*
INSERISCO IN APICOLTURA_IMPRESE
*/
raise info 'INSERIMENTO IN APICOLTURA_IMPRESA';
idimpresaApicoltura:=(select max(id)+1 from apicoltura_imprese);
insert into apicoltura_imprese ( id,
ragione_sociale, codice_fiscale_impresa,partita_iva,tipo_attivita_apicoltura,data_inizio,id_asl,stato,flag_scia,id_richiesta_suap,
domicilio_digitale,
enteredby,modifiedby,entered, modified,flag_laboratorio_annesso
)
(select idimpresaApicoltura,
op.ragione_sociale,op.partita_iva,op.partita_iva,1,st.data_ricezione_richiesta,idAsl,5,true,id_richiesta_suap,op.domicilio_digitale,
idutenteasl,idutenteasl,current_timestamp,current_timestamp,case when path_descrizione ilike '%con laboratorio%' then true else false end
from suap_ric_scia_operatore op
join suap_ric_scia_stabilimento st on op.id=st.id_operatore
join suap_ric_scia_relazione_stabilimento_linee_produttive rlp on rlp.id_stabilimento = st.id
join ml8_linee_attivita_nuove lp on lp.id_nuova_linea_attivita = rlp.id_linea_produttiva
where op.id =id_richiesta_suap and st.tipo_attivita=3
);

 
 
/*
INSERISCO IN APICOLTURA_IMPRESE
*/

idrelsedelegale:=(select max(id)+1 from apicoltura_relazione_imprese_sede_legale);

insert into apicoltura_relazione_imprese_sede_legale(id,id_apicoltura_imprese,id_indirizzo,tipologia_sede,stato_sede,enabled,modified_by)
values(idrelsedelegale,idimpresaApicoltura,sedelegale,1,1,true,idutenteasl);



/*
INSERISCO IN OPU_soggetto_fisico
*/

insert into opu_soggetto_fisico (cognome,--nome,
codice_fiscale,enteredby,modifiedby,indirizzo_id)
 (
select 
op.ragione_sociale,--op.ragione_sociale,
op.partita_iva,idutenteasl,idutenteasl,sedelegale
from suap_ric_scia_operatore op
join suap_ric_scia_stabilimento st on op.id=st.id_operatore
join suap_ric_scia_relazione_stabilimento_linee_produttive rlp on rlp.id_stabilimento = st.id
join ml8_linee_attivita_nuove lp on lp.id_nuova_linea_attivita = rlp.id_linea_produttiva
where op.id =id_richiesta_suap and st.tipo_attivita=3
) returning id into proprietarioapicoltore.id;

idrelsf:=(select max(id)+1 from apicoltura_rel_impresa_soggetto_fisico);
insert into apicoltura_rel_impresa_soggetto_fisico(id,id_apicoltura_imprese,id_soggetto_fisico,tipo_soggetto_fisico,enabled,data_inizio)
values(idrelsf,idimpresaApicoltura,proprietarioapicoltore.id,1,true,current_timestamp);


return true ;
end if;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.suap_insert_attivita_apicoltura_da_richiesta(integer, integer)
  OWNER TO postgres;
  
update opu_lookup_norme_master_list_rel_tipologia_organzation set codice_univoco_ml = 'OAB-OAB-X' where tipologia_organization = 4;
update opu_lookup_norme_master_list_rel_tipologia_organzation set codice_univoco_ml = 'AV-TAV-X' where tipologia_organization = 9;
update opu_lookup_norme_master_list_rel_tipologia_organzation set codice_univoco_ml = 'OFA-OFA-X' where tipologia_organization = 22;
update opu_lookup_norme_master_list_rel_tipologia_organzation set codice_univoco_ml = 'PSB-PSB-X' where tipologia_organization = 5;
update opu_lookup_norme_master_list_rel_tipologia_organzation set codice_univoco_ml = 'ONPA-ONPA-X' where tipologia_organization = 12;
update opu_lookup_norme_master_list_rel_tipologia_organzation set codice_univoco_ml = 'IUV' where tipologia_organization = 15;
update opu_lookup_norme_master_list_rel_tipologia_organzation set codice_univoco_ml = 'IUV' where tipologia_organization = 16;
update opu_lookup_norme_master_list_rel_tipologia_organzation set codice_univoco_ml = 'OPR-OPR-X' where tipologia_organization = 13;
update opu_lookup_norme_master_list_rel_tipologia_organzation set codice_univoco_ml = 'IUV-CAN-CAN' where tipologia_organization = 10;
update opu_lookup_norme_master_list_rel_tipologia_organzation set codice_univoco_ml = 'IUV' where tipologia_organization = 20;
update opu_lookup_norme_master_list_rel_tipologia_organzation set codice_univoco_ml = 'ALT-HAC-HAC' where tipologia_organization = 152;

