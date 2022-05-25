INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (1000,94,'global-search',TRUE,TRUE,TRUE,TRUE,'Ricerca Globale',20,TRUE,TRUE,FALSE);
    
INSERT INTO permission_category(
            category_id, category, description, "level", enabled, active, 
            folders, lookups, viewpoints, categories, scheduled_events, object_events, 
            reports, webdav, logos, constant, action_plans, custom_list_views)
    VALUES (94,'Ricerca Globale','Modulo Ricerca Globale',20,TRUE,TRUE,TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,TRUE,TRUE,FALSE,215454,TRUE,FALSE);

    
-- View: view_globale

-- DROP VIEW view_globale;

CREATE OR REPLACE VIEW view_globale AS 
 SELECT o.name AS ragione_sociale, o.org_id, o.site_id AS asl_rif, l.description AS asl, o.tipologia, o.categoria_rischio, o.codice_fiscale, t.ticketid, t.identificativo, t.location AS num_verbale, t.id_controllo_ufficiale AS id, t.tipologia AS tipo_attivita, o.codice_fiscale_rappresentante, o.partita_iva, o.alert AS descrizione, o.tipologia_acque, o.nome_correntista, 
        CASE
            WHEN o.tipologia = 1 THEN 'Impresa'::text
            WHEN o.tipologia = 2 THEN 'Allevamento'::text
            WHEN o.tipologia = 3 AND o.direct_bill = true THEN 'Mercati Ittici'::text
            WHEN o.tipologia = 3 THEN 'Stabilimento'::text
            WHEN o.tipologia = 9 THEN 'Trasporto Animali'::text
            WHEN o.tipologia = 97 THEN 'SOA'::text
            WHEN o.tipologia = 800 THEN 'OSM'::text
            WHEN o.tipologia = 201 THEN 'Molluschi Bivalvi'::text
            WHEN o.tipologia = 4 THEN 'Operatore Abusivo'::text
            WHEN o.tipologia = 13 THEN 'Operatore Privato'::text
            WHEN o.tipologia = 22 THEN 'Attivita' mobile fuori ambito Asl'::text
            WHEN o.tipologia = 29 THEN 'Operatore Esercente'::text
            ELSE 'Altro'::text
        END AS tipologia_operatore, 
        CASE
            WHEN o.numaut IS NULL OR o.numaut::text = ' '::text THEN 'Non specificato'::text::character varying
            ELSE o.numaut
        END AS num_aut, 
        CASE
            WHEN o.account_number IS NULL OR o.account_number::text = ' '::text THEN 'Non specificato'::text::character varying
            ELSE o.account_number
        END AS n_reg, 
        CASE
            WHEN o.nome_rappresentante IS NULL AND o.cognome_rappresentante IS NULL THEN 'Non specificato'::text
            WHEN o.nome_rappresentante::text = ' '::text AND o.cognome_rappresentante::text = ' '::text THEN 'Non specificato'::text
            ELSE (o.nome_rappresentante::text || ' '::text) || o.cognome_rappresentante::text
        END AS titolare, 
        CASE
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN 'Fissa'::text
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Autoveicolo'::text THEN 'Mobile'::text
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Distributori'::text THEN 'Distributore'::text
            ELSE 'Altro'::text
        END AS attivita, 
        CASE
            WHEN o.cessato = 0 AND o.tipologia = 1 THEN 'In Attivita'::text
            WHEN o.cessato = 1 AND o.tipologia = 1 THEN 'Cessato'::text
            WHEN o.cessato = 2 AND o.tipologia = 1 THEN 'Sospeso'::text
            WHEN o.cessato IS NULL AND o.tipologia = 1 OR o.tipologia = 3 AND o.direct_bill = true THEN 'Non specificato'::text
            ELSE NULL::text
        END AS stato_impresa, 
        CASE
            WHEN o.date2 IS NULL AND o.tipologia = 2 THEN 'In Attivita'::text
            WHEN o.date2 IS NOT NULL AND o.tipologia = 2 THEN 'Cessato'::text
            ELSE NULL::text
        END AS stato_allevamento, 
        CASE
            WHEN o.stato_lab = 0 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Autorizzato'::text
            WHEN o.stato_lab = 1 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Revocato'::text
            WHEN o.stato_lab = 2 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Sospeso'::text
            WHEN o.stato_lab = 3 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'In Domanda'::text
            ELSE NULL::text
        END AS stato, 
        CASE
            WHEN o.tipologia = 3 OR o.tipologia = 97 THEN COALESCE(oa5.city, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.city, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.city, 'N.D.'::character varying)
            WHEN o.tipologia = 2 THEN COALESCE(oa1.city, 'N.D.'::character varying)
            ELSE COALESCE(oa5.city, oa1.city, 'N.D.'::character varying)
        END AS comune, 
        CASE
            WHEN o.tipologia = 3 THEN COALESCE(oa5.state, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.state, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.state, 'N.D.'::character varying)
            WHEN o.site_id = 1 OR o.site_id = 2 THEN 'AV'::text::character varying
            WHEN o.site_id = 3 THEN 'BN'::text::character varying
            WHEN o.site_id = 4 OR o.site_id = 5 THEN 'CE'::text::character varying
            WHEN o.site_id = 6 OR o.site_id = 7 OR o.site_id = 8 OR o.site_id = 9 OR o.site_id = 10 THEN 'NA'::text::character varying
            WHEN o.site_id = 11 OR o.site_id = 12 OR o.site_id = 13 THEN 'SA'::text::character varying
            ELSE COALESCE(oa5.state, oa1.state, 'N.D.'::character varying)
        END AS provincia, 
        CASE
            WHEN t.tipologia = 1 THEN 'Sanzioni'::text
            WHEN t.tipologia = 2 THEN 'Campioni'::text
            WHEN t.tipologia = 6 THEN 'Notizie di reato'::text
            WHEN t.tipologia = 7 THEN 'Tamponi'::text
            WHEN t.tipologia = 8 THEN 'Non conformita''::text
            WHEN t.tipologia = 9 THEN 'Sequestri'::text
            WHEN t.tipologia = 15 THEN 'Follow up'::text
            WHEN t.tipologia = 3 THEN 'Controlli Ufficiali'::text
            ELSE 'Altro'::text
        END AS tipologia_campioni, to_date(t.assigned_date::text, 'yyyy/mm/dd'::text) AS data_inizio_controllo, to_date(t.closed::text, 'yyyy/mm/dd'::text) AS data_chiusura_controllo, 
        CASE
            WHEN t.closed IS NULL THEN 'N.D.'::text
            ELSE date_part('year'::text, t.closed)::text
        END AS anno_chiusura
   FROM organization o
   LEFT JOIN ticket t ON t.org_id = o.org_id
   LEFT JOIN lookup_site_id l ON l.code = o.site_id
   LEFT JOIN organization_address oa5 ON oa5.org_id = o.org_id AND oa5.address_type = 5
   LEFT JOIN organization_address oa1 ON oa1.org_id = o.org_id AND oa1.address_type = 1
  WHERE o.trashed_date IS NULL AND t.trashed_date IS NULL AND o.org_id <> 0 AND o.tipologia <> 0;

ALTER TABLE view_globale OWNER TO postgres;

