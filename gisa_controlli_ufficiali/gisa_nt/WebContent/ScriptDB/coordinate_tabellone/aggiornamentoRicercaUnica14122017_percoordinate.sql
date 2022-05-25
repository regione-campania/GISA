drop table ricerche_anagrafiche_old_materializzata cascade;

--script aggiornato al 28/11/2017 per coordinate e campi di servizio
DROP VIEW ricerca_anagrafiche cascade;
-- View: public.global_arch_view

-- View: public.global_ric_view
DROP VIEW public.global_ric_view;
CREATE OR REPLACE VIEW public.global_ric_view AS 
SELECT o.alt_id AS riferimento_id,
    'altId'::text AS riferimento_id_nome,
    'alt_id'::text AS riferimento_id_nome_col,
    'suap_ric_scia_stabilimento'::text AS riferimento_id_nome_tab,
    o.id_indirizzo_operatore AS id_indirizzo_impresa,
    o.id_indirizzo AS id_sede_operativa,
    - 1 AS sede_mobile_o_altro,
    'opu_indirizzo'::text AS riferimento_nome_tab_indirizzi,
    o.id_opu_operatore AS id_impresa,
    'suap_ric_scia_operatore'::text AS riferimento_nome_tab_impresa,
    o.id_soggetto_fisico,
    'suap_ric_scia_soggetto_fisico'::text AS riferimento_nome_tab_soggetto_fisico,
    o.id_linea_attivita_stab AS id_attivita,
    o.stab_entered AS data_inserimento,
    o.ragione_sociale,
    o.id_asl_stab AS asl_rif,
    o.stab_asl AS asl,
    o.codice_fiscale_impresa AS codice_fiscale,
    o.cf_rapp_sede_legale AS codice_fiscale_rappresentante,
    o.partita_iva,
    NULL::integer AS categoria_rischio,
    NULL::text AS num_riconoscimento,
    NULL::text AS n_linea,
    o.numero_registrazione AS n_reg,
    concat_ws(' '::text, o.nome_rapp_sede_legale, o.cognome_rapp_sede_legale) AS nominativo_rappresentante,
    o.stab_descrizione_attivita AS tipo_attivita_descrizione,
    o.stab_id_attivita AS tipo_attivita,
    o.data_inizio_attivita,
    o.data_fine_attivita,
    'IN ITINERE'::character varying(50) AS stato,
    o.id_stato,
        CASE
            WHEN o.stab_id_attivita = 1 THEN o.comune_stab
            WHEN o.stab_id_attivita = 2 AND o.impresa_id_tipo_impresa = 1 THEN o.comune_residenza
            WHEN o.stab_id_attivita = 2 AND o.impresa_id_tipo_impresa <> 1 THEN o.comune_sede_legale
            ELSE NULL::character varying
        END AS comune,
    o.prov_stab AS provincia_stab,
    o.indirizzo_stab AS indirizzo,
    o.cap_stab, o.lat_stab as latitudine_stab, o.long_stab as longitudine_stab,
    o.comune_sede_legale AS comune_leg,
    o.prov_sede_legale AS provincia_leg,
    o.indirizzo_sede_legale AS indirizzo_leg,
    o.cap_sede_legale AS cap_leg, null::double precision as latitudine_leg, null::double precision as longitudine_leg,
    o.macroarea,
    o.aggregazione,
    o.attivita,
    nn.description AS norma,
    o.id_norma,
    1001 AS tipologia_operatore,
    NULL::text AS targa,
    2 AS tipo_ricerca_anagrafica,
    'blue'::text AS color,
    NULL::text AS n_reg_old,
    o.id_tipo_linea_produttiva,
    o.id_controllo_ultima_categorizzazione,
    o.id_linea_attivita
   FROM suap_ric_scia_operatori_denormalizzati_view o
     JOIN opu_lookup_norme_master_list nn ON nn.code = o.id_norma
  WHERE (o.validato = false OR o.validato IS NULL) AND (o.id_tipo_richiesta = ANY (ARRAY[1, 2]));

ALTER TABLE public.global_ric_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.global_ric_view TO postgres;
GRANT SELECT ON TABLE public.global_ric_view TO report;


DROP VIEW public.global_arch_view;

CREATE OR REPLACE VIEW public.global_arch_view AS 
 SELECT DISTINCT o.org_id AS riferimento_id,
    'orgId'::text AS riferimento_id_nome,
    'org_id'::text AS riferimento_id_nome_col,
    'organization'::text AS riferimento_id_nome_tab,
    oa1.address_id AS id_indirizzo_impresa,
    oa5.address_id AS id_sede_operativa,
    oa7.address_id AS sede_mobile_o_altro,
    'organization_address'::text AS riferimento_nome_tab_indirizzi,
    - 1 AS id_impresa,
    '-'::text AS riferimento_nome_tab_impresa,
    - 1 AS id_soggetto_fisico,
    '-'::text AS riferimento_nome_tab_soggetto_fisico,
    - 1 AS id_attivita,
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
            ELSE o.tipo_dest
        END AS tipo_attivita_descrizione,
        CASE
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN 1
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Autoveicolo'::text THEN 2
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Distributori'::text THEN 4
            ELSE '-1'::integer
        END AS tipo_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.data_in_carattere IS NOT NULL AND o.data_fine_carattere IS NOT NULL THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = ANY (ARRAY[2, 9]) THEN to_date(o.date1::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = ANY (ARRAY[800, 801, 13, 802]) THEN o.date2
            WHEN o.tipologia = 17 THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            ELSE to_date(o.datapresentazione::text, 'yyyy/mm/dd'::text)::timestamp without time zone
        END AS data_inizio_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date THEN o.data_fine_carattere
            WHEN o.tipologia = 1 AND o.source <> 1 AND (o.cessato = 1 OR o.cessato = 2) THEN o.contract_end
            WHEN o.tipologia = ANY (ARRAY[2, 9]) THEN COALESCE(to_date(o.date2::text, 'yyyy/mm/dd'::text)::timestamp without time zone, o.data_cambio_stato)
            WHEN o.tipologia = ANY (ARRAY[800, 801, 13, 802]) THEN o.date1
            ELSE NULL::timestamp without time zone
        END AS data_fine_attivita,
        CASE
            WHEN o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date OR o.source <> 1 AND o.cessato = 1 THEN 'Cessato'::text
            WHEN o.source = 1 AND (o.data_fine_carattere IS NULL OR o.data_fine_carattere > 'now'::text::date) OR o.source <> 1 AND o.cessato = 0 THEN 'Attivo'::text
            WHEN o.source <> 1 AND o.cessato <> 1 AND o.cessato <> 0 THEN 'Sospeso'::text
            ELSE 'Attivo'::text
        END AS stato,
        CASE
            WHEN o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date OR o.source <> 1 AND o.cessato = 1 THEN 4
            WHEN o.source = 1 AND (o.data_fine_carattere IS NULL OR o.data_fine_carattere > 'now'::text::date) OR o.source <> 1 AND o.cessato = 0 THEN 0
            WHEN o.source <> 1 AND o.cessato <> 1 AND o.cessato <> 0 THEN 2
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
    tsa.macroarea,
    tsa.aggregazione,
    (((tsa.macroarea || '->'::text) || tsa.aggregazione) || '->'::text) ||
        CASE
            WHEN tsa.attivita IS NOT NULL THEN tsa.attivita
            ELSE ''::text
        END AS attivita,
    nm.description AS norma,
    nm.code AS id_norma,
    o.tipologia AS tipologia_operatore,
    o.nome_correntista AS targa,
    3 AS tipo_ricerca_anagrafica,
    'purple'::text AS color,
        CASE
            WHEN o.tipologia = 1 THEN o.account_number::text
            ELSE NULL::text
        END AS n_reg_old,
    1 AS id_tipo_linea_produttiva,
    o.id_controllo_ultima_categorizzazione,
    '-1'::integer AS id_linea
   FROM organization o
     LEFT JOIN la_imprese_linee_attivita lai ON lai.org_id = o.org_id AND lai.trashed_date IS NULL
     LEFT JOIN stabilimenti_sottoattivita ssa ON ssa.id_stabilimento = o.org_id
     LEFT JOIN soa_sottoattivita ssoa ON ssoa.id_soa = o.org_id
     LEFT JOIN aziende az ON az.cod_azienda = o.account_number::text
     LEFT JOIN comuni1 c ON c.istat::text = ('0'::text || az.cod_comune_azienda)
     LEFT JOIN opu_lookup_norme_master_list_rel_tipologia_organzation norme ON norme.tipologia_organization = o.tipologia
     LEFT JOIN opu_lookup_norme_master_list nm ON nm.code = norme.id_opu_lookup_norme_master_list
     LEFT JOIN lookup_tipologia_operatore lto ON lto.code = o.tipologia
     LEFT JOIN lista_linee_vecchia_anagrafica_stabilimenti_soggetti_a_scia tsa ON tsa.org_id = o.org_id
     LEFT JOIN operatori_allevamenti op_prop ON o.codice_fiscale_rappresentante::text = op_prop.cf
     LEFT JOIN lookup_site_id l_1 ON l_1.code = o.site_id
     LEFT JOIN organization_address oa5 ON oa5.org_id = o.org_id AND oa5.address_type = 5
     LEFT JOIN organization_address oa1 ON oa1.org_id = o.org_id AND oa1.address_type = 1
     LEFT JOIN organization_address oa7 ON oa7.org_id = o.org_id AND oa7.address_type = 7
     JOIN ticket t ON t.org_id = o.org_id
  WHERE o.trashed_date = '1970-01-01 00:00:00'::timestamp without time zone AND o.tipologia = 1 AND o.import_opu = false AND t.trashed_date IS NULL AND t.tipologia = 3 AND o.name IS NOT NULL AND o.name::text <> ''::text AND o.partita_iva IS NOT NULL AND o.partita_iva <> ''::bpchar AND length(o.partita_iva::text) = 11 AND o.partita_iva::character varying::text ~ similar_escape('[0-9]{11}'::text, NULL::text) AND o.partita_iva::character varying::text !~ similar_escape('%[0]{5,11}%'::text, NULL::text) AND o.partita_iva::character varying::text !~ similar_escape('%[1]{5,11}%'::text, NULL::text) AND o.partita_iva::character varying::text !~ similar_escape('%[2]{5,11}%'::text, NULL::text) AND o.partita_iva::character varying::text !~ similar_escape('%[3]{5,11}%'::text, NULL::text) AND o.partita_iva::character varying::text !~ similar_escape('%[4]{5,11}%'::text, NULL::text) AND o.partita_iva::character varying::text !~ similar_escape('%[5]{5,11}%'::text, NULL::text) AND o.partita_iva::character varying::text !~ similar_escape('%[6]{5,11}%'::text, NULL::text) AND o.partita_iva::character varying::text !~ similar_escape('%[7]{5,11}%'::text, NULL::text) AND o.partita_iva::character varying::text !~ similar_escape('%[8]{5,11}%'::text, NULL::text) AND o.partita_iva::character varying::text !~ similar_escape('%[9]{5,11}%'::text, NULL::text)
  GROUP BY o.id_controllo_ultima_categorizzazione, o.org_id, 'orgId'::text, 'org_id'::text, 'organization'::text, o.entered, o.name, o.site_id, l_1.description, oa1.address_id, oa5.address_id, oa7.address_id, o.codice_fiscale, o.codice_fiscale_rappresentante, o.partita_iva, (
        CASE
            WHEN o.tipologia = 97 AND o.categoria_rischio IS NULL THEN 3
            WHEN o.tipologia = 97 AND o.categoria_rischio = '-1'::integer THEN 3
            ELSE o.categoria_rischio
        END), (
        CASE
            WHEN o.tipologia <> 1 AND COALESCE(o.numaut, o.account_number) IS NOT NULL THEN COALESCE(o.numaut, o.account_number)
            ELSE ''::character varying
        END), (
        CASE
            WHEN o.tipologia = 1 THEN o.account_number
            ELSE ''::character varying
        END), (
        CASE
            WHEN o.tipologia = 2 THEN op_prop.nominativo
            WHEN o.nome_rappresentante IS NULL AND o.cognome_rappresentante IS NULL THEN 'Non specificato'::text
            WHEN o.nome_rappresentante::text = ' '::text AND o.cognome_rappresentante::text = ' '::text THEN 'Non specificato'::text
            ELSE (o.nome_rappresentante::text || ' '::text) || o.cognome_rappresentante::text
        END), (
        CASE
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN 'ATTIVITA FISSA'::text::character varying
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Autoveicolo'::text THEN 'ATTIVITA MOBILE'::text::character varying
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Distributori'::text THEN 'Distributore'::text::character varying
            ELSE o.tipo_dest
        END), (
        CASE
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN 1
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Autoveicolo'::text THEN 2
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Distributori'::text THEN 4
            ELSE '-1'::integer
        END), (
        CASE
            WHEN o.tipologia = 3 OR o.tipologia = 97 THEN COALESCE(oa5.city, 'N.D.'::character varying)
            WHEN o.tipologia = 2 THEN COALESCE(c.nome, 'N.D'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.city, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.city, 'N.D.'::character varying)
            ELSE COALESCE(oa5.city, oa1.city, 'N.D.'::character varying)
        END), (
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
        END), (
        CASE
            WHEN o.tipologia = 2 THEN COALESCE(az.indrizzo_azienda, ''::text)::character varying
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.addrline1, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.addrline1, 'N.D.'::character varying)
            ELSE COALESCE(oa5.addrline1, oa1.addrline1, 'N.D.'::character varying)
        END), (
        CASE
            WHEN o.tipologia = 2 THEN COALESCE(az.cap_azienda, ''::text)::character varying
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa5.postalcode, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa7.postalcode, 'N.D.'::character varying)
            ELSE COALESCE(oa5.postalcode, oa1.postalcode, 'N.D.'::character varying)
        END),  oa5.latitude ,
    oa5.longitude,(
        CASE
            WHEN o.tipologia = 3 OR o.tipologia = 97 THEN COALESCE(oa1.city, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa1.city, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.city, 'N.D.'::character varying)
            ELSE COALESCE(oa1.city, oa5.city, 'N.D.'::character varying)
        END), (
        CASE
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa1.state, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.state, 'N.D.'::character varying)
            ELSE COALESCE(oa1.state, oa5.state, 'N.D.'::character varying)
        END), (
        CASE
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa1.addrline1, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.addrline1, 'N.D.'::character varying)
            ELSE COALESCE(oa1.addrline1, oa5.addrline1, 'N.D.'::character varying)
        END), (
        CASE
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa1.postalcode, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.postalcode, 'N.D.'::character varying)
            ELSE COALESCE(oa1.postalcode, oa5.postalcode, 'N.D.'::character varying)
        END), oa1.latitude,
    oa1.longitude,tsa.macroarea, tsa.aggregazione, ((((tsa.macroarea || '->'::text) || tsa.aggregazione) || '->'::text) ||
        CASE
            WHEN tsa.attivita IS NOT NULL THEN tsa.attivita
            ELSE ''::text
        END), nm.description, nm.code, o.tipologia, o.nome_correntista, (
        CASE
            WHEN lai.id > 0 THEN lai.id
            WHEN ssa.id > 0 THEN ssa.id
            WHEN ssoa.id > 0 THEN ssoa.id
            ELSE o.org_id
        END)
 HAVING count(t.ticketid) > 0;

ALTER TABLE public.global_arch_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.global_arch_view TO postgres;
GRANT SELECT ON TABLE public.global_arch_view TO report;

-- View: public.global_org_view
DROP VIEW public.global_org_view;

CREATE OR REPLACE VIEW public.global_org_view AS 
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
            WHEN o.tipologia <> 1 AND o.tipologia <> 201 AND COALESCE(btrim(o.numaut::text), btrim(o.account_number::text)) IS NOT NULL THEN COALESCE(o.account_number, o.numaut)
            WHEN o.tipologia = 201 THEN o.cun::character varying
            ELSE ''::character varying
        END AS num_riconoscimento,
        CASE
            WHEN o.tipologia = 1 THEN btrim(o.account_number::text)
            ELSE ''::text
        END AS n_reg,
        CASE
            WHEN o.tipologia = 1 THEN btrim(o.account_number::text)
            WHEN o.tipologia <> 1 AND o.tipologia <> 201 AND COALESCE(btrim(o.numaut::text), btrim(o.account_number::text)) IS NOT NULL THEN COALESCE(o.account_number, o.numaut)::text
            WHEN o.tipologia = 201 THEN o.cun::character varying::text
            ELSE NULL::text
        END AS n_linea,
        CASE
            WHEN o.tipologia = 2 THEN op_prop.nominativo
            WHEN o.nome_rappresentante IS NULL AND o.cognome_rappresentante IS NULL THEN 'Non specificato'::text
            WHEN o.nome_rappresentante::text = ' '::text AND o.cognome_rappresentante::text = ' '::text THEN 'Non specificato'::text
            ELSE (o.nome_rappresentante::text || ' '::text) || o.cognome_rappresentante::text
        END AS nominativo_rappresentante,
        CASE
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN 'ATTIVITA FISSA'::text::character varying
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Autoveicolo'::text OR o.tipologia = 17 THEN 'ATTIVITA MOBILE'::text::character varying
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Distributori'::text THEN 'Distributore'::text::character varying
            ELSE 'ATTIVITA FISSA'::character varying
        END AS tipo_attivita_descrizione,
        CASE
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN 1
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Autoveicolo'::text OR o.tipologia = 17 THEN 2
            WHEN o.tipologia = 1 AND o.tipo_dest::text = 'Distributori'::text THEN 4
            ELSE 1
        END AS tipo_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.data_in_carattere IS NOT NULL AND o.data_fine_carattere IS NOT NULL THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = ANY (ARRAY[2, 9]) THEN to_date(o.date1::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            WHEN o.tipologia = ANY (ARRAY[800, 801, 13, 802]) THEN o.date2
            WHEN o.tipologia = 17 THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            ELSE to_date(o.datapresentazione::text, 'yyyy/mm/dd'::text)::timestamp without time zone
        END AS data_inizio_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date THEN o.data_fine_carattere
            WHEN o.tipologia = 1 AND o.source <> 1 AND (o.cessato = 1 OR o.cessato = 2) THEN o.contract_end
            WHEN o.tipologia = ANY (ARRAY[2, 9]) THEN COALESCE(to_date(o.date2::text, 'yyyy/mm/dd'::text)::timestamp without time zone, o.data_cambio_stato)
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
            WHEN o.stato_lab IS NULL AND (o.tipologia = ANY (ARRAY[151, 152])) THEN o.stato
            WHEN o.tipologia = 201 AND o.stato_classificazione IS NOT NULL THEN lsc.description::text
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
            WHEN o.tipologia = 201 AND o.stato_classificazione IS NOT NULL THEN lsc.code
            ELSE 0
        END AS id_stato,
    COALESCE(oa5.city, oa1.city, oa7.city, oa.city, 'N.D.'::character varying) AS comune,
    COALESCE(oa5.state, oa1.state, oa7.state, oa.state, 'N.D.'::character varying) AS provincia_stab,
    COALESCE(oa5.addrline1, oa1.addrline1, oa7.addrline1, oa.addrline1, 'N.D.'::character varying) AS indirizzo,
    COALESCE(oa5.postalcode, oa1.postalcode, oa7.postalcode, oa.postalcode, 'N.D.'::character varying) AS cap_stab, 
    oa5.latitude as latitudine_stab, oa5.longitude as longitudine_stab,
        CASE
            WHEN o.tipologia = 3 OR o.tipologia = 97 THEN COALESCE(oa1.city, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa1.city, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.city, 'N.D.'::character varying)
            ELSE COALESCE(oa1.city, oa5.city, oa.city, 'N.D.'::character varying)
        END AS comune_leg,
        CASE
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa1.state, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.state, 'N.D.'::character varying)
            ELSE COALESCE(oa1.state, oa5.state, oa.state, 'N.D.'::character varying)
        END AS provincia_leg,
        CASE
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa1.addrline1, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.addrline1, 'N.D.'::character varying)
            ELSE COALESCE(oa1.addrline1, oa5.addrline1, oa.addrline1, 'N.D.'::character varying)
        END AS indirizzo_leg,
        CASE
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Es. Commerciale'::text THEN COALESCE(oa1.postalcode, 'N.D.'::character varying)
            WHEN o.tipologia <> 3 AND o.tipo_dest::text = 'Autoveicolo'::text THEN COALESCE(oa1.postalcode, 'N.D.'::character varying)
            ELSE COALESCE(oa1.postalcode, oa5.postalcode, oa.postalcode, 'N.D.'::character varying)
        END AS cap_leg, oa1.latitude as latitudine_leg, oa1.longitude as longitudine_leg,
    COALESCE(ml8.macroarea, norme.macroarea_temp) AS macroarea,
    COALESCE(ml8.aggregazione, norme.aggregazione_temp) AS aggregazione,
    COALESCE(ml8.attivita, norme.linea_temp) AS attivita,
    'Non previsto'::text AS gestione_masterlist,
    nm.description AS norma,
    nm.code AS id_norma,
    o.tipologia AS tipologia_operatore,
        CASE
            WHEN o.tipologia = 9 THEN o_a.targa::character varying
            ELSE o.nome_correntista
        END AS targa,
    0 AS tipo_ricerca_anagrafica,
    'red'::text AS color,
    NULL::text AS n_reg_old,
    '-1'::integer AS id_tipo_linea_produttiva,
    o.id_controllo_ultima_categorizzazione,
    o.org_id AS id_linea
   FROM organization o
     LEFT JOIN la_imprese_linee_attivita lai ON lai.org_id = o.org_id AND lai.trashed_date IS NULL
     LEFT JOIN stabilimenti_sottoattivita ssa ON ssa.id_stabilimento = o.org_id
     LEFT JOIN soa_sottoattivita ssoa ON ssoa.id_soa = o.org_id
     LEFT JOIN lookup_stato_classificazione lsc ON lsc.code = o.stato_classificazione
     LEFT JOIN organization_autoveicoli o_a ON o_a.org_id = o.org_id AND o_a.elimina IS NULL
     LEFT JOIN opu_lookup_norme_master_list_rel_tipologia_organzation norme ON norme.tipologia_organization = o.tipologia
     LEFT JOIN opu_lookup_norme_master_list nm ON nm.code = norme.id_opu_lookup_norme_master_list
     LEFT JOIN lookup_tipologia_operatore lto ON lto.code = o.tipologia
     LEFT JOIN aziende az ON az.cod_azienda = o.account_number::text AND o.tipologia = 2
     LEFT JOIN comuni_old caz ON caz.codiceistatcomune::text = az.cod_comune_azienda
     LEFT JOIN operatori_allevamenti op_detentore ON o.cf_correntista::text = op_detentore.cf
     LEFT JOIN operatori_allevamenti op_prop ON o.codice_fiscale_rappresentante::text = op_prop.cf
     LEFT JOIN lookup_site_id l_1 ON l_1.code = o.site_id
     LEFT JOIN organization_address oa5 ON oa5.org_id = o.org_id AND oa5.address_type = 5
     LEFT JOIN organization_address oa1 ON oa1.org_id = o.org_id AND oa1.address_type = 1
     LEFT JOIN organization_address oa7 ON oa7.org_id = o.org_id AND oa7.address_type = 7
     LEFT JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type IS NULL
     LEFT JOIN organization_autoveicoli ov ON ov.org_id = o.org_id
     LEFT JOIN ml8_linee_attivita_nuove_materializzata ml8 ON ml8.codice = norme.codice_univoco_ml
  WHERE o.org_id <> 0 AND o.tipologia <> 0 AND o.trashed_date IS NULL AND o.import_opu IS NOT TRUE AND ((o.tipologia <> ALL (ARRAY[6, 11, 211, 1, 151, 802, 152, 10, 20, 2, 800, 801])) OR o.tipologia = 3 AND o.direct_bill = true);

ALTER TABLE public.global_org_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.global_org_view TO postgres;
GRANT SELECT ON TABLE public.global_org_view TO report;

-- View: public.apicoltura_apiari_denormalizzati_view

--drop view report_campioni_new;
-- 
DROP VIEW public.apicoltura_apiari_denormalizzati_view cascade;

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
    sedeop.id AS id_indirizzo_operatore,
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
    sedestab.id AS id_indirizzo,
    sedestab.via AS indirizzo_stab,
    sedestab.cap AS cap_stab,
    sedestab.provincia AS prov_stab,
    stab.categoria_rischio,
    soggsl.id AS id_soggetto_fisico,
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
    detentore.id AS id_detentore,
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


--query di ricerca anagrafiche


CREATE OR REPLACE VIEW public.ricerca_anagrafiche AS 
SELECT DISTINCT o.org_id AS riferimento_id,
    'orgId'::text AS riferimento_id_nome,
    'org_id'::text AS riferimento_id_nome_col,
    'organization'::text AS riferimento_id_nome_tab,
    oa1.address_id AS id_indirizzo_impresa,
    oa5.address_id AS id_sede_operativa,
    oa7.address_id AS sede_mobile_o_altro,
    'organization_address'::text AS riferimento_nome_tab_indirizzi,
    - 1 AS id_impresa,
    '-'::text AS riferimento_nome_tab_impresa,
    - 1 AS id_soggetto_fisico,
    '-'::text AS riferimento_nome_tab_soggetto_fisico,
    - 1 AS id_attivita,
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
            WHEN o.tipologia = 17 THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            ELSE to_date(o.datapresentazione::text, 'yyyy/mm/dd'::text)::timestamp without time zone
        END AS data_inizio_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date THEN o.data_fine_carattere
            WHEN o.tipologia = 1 AND o.source <> 1 AND (o.cessato = 1 OR o.cessato = 2) THEN o.contract_end
            WHEN o.tipologia = ANY (ARRAY[2, 9]) THEN COALESCE(to_date(o.date2::text, 'yyyy/mm/dd'::text)::timestamp without time zone, o.data_cambio_stato)
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
        END AS cap_stab, oa5.latitude as latitudine_stab, oa5.longitude as longitudine_stab,
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
        END AS cap_leg, oa1.latitude as latitudine_leg, oa1.longitude as longitudine_leg,
    tsa.macroarea,
    tsa.aggregazione,
    (((tsa.macroarea || '->'::text) || tsa.aggregazione) || '->'::text) ||
        CASE
            WHEN tsa.attivita IS NOT NULL THEN concat_ws('->'::text, tsa.attivita, tsa.attivita_specifica)
            ELSE ''::text
        END AS attivita,
    'Non presente'::text AS path_attivita_completo,
    'Non previsto'::text AS gestione_masterlist,
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
    tsa.id AS id_linea
   FROM organization o
     LEFT JOIN la_imprese_linee_attivita lai ON lai.org_id = o.org_id AND lai.trashed_date IS NULL
     LEFT JOIN stabilimenti_sottoattivita ssa ON ssa.id_stabilimento = o.org_id
     LEFT JOIN soa_sottoattivita ssoa ON ssoa.id_soa = o.org_id
     LEFT JOIN aziende az ON az.cod_azienda = o.account_number::text
     LEFT JOIN comuni1 c ON c.istat::text = ('0'::text || az.cod_comune_azienda)
     LEFT JOIN opu_lookup_norme_master_list_rel_tipologia_organzation norme ON norme.tipologia_organization = o.tipologia
     LEFT JOIN opu_lookup_norme_master_list nm ON nm.code = norme.id_opu_lookup_norme_master_list
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
    'Non presente'::text AS path_attivita_completo,
    'Non previsto'::text AS gestione_masterlist,
    global_org_view.norma,
    global_org_view.id_norma,
    global_org_view.tipologia_operatore,
    global_org_view.targa,
    global_org_view.tipo_ricerca_anagrafica,
    global_org_view.color,
    global_org_view.n_reg_old,
    global_org_view.id_tipo_linea_produttiva AS id_tipo_linea_reg_ric,
    global_org_view.id_controllo_ultima_categorizzazione,
    global_org_view.id_linea
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
    global_ric_view.id_linea_attivita AS id_linea
   FROM global_ric_view
  WHERE global_ric_view.id_stato = ANY (ARRAY[0, 7, 2])
UNION
  SELECT DISTINCT o.id_stabilimento AS riferimento_id,
    'stabId'::text AS riferimento_id_nome,
    'id_stabilimento'::text AS riferimento_id_nome_col,
    'opu_stabilimento'::text AS riferimento_id_nome_tab,
    o.id_indirizzo_operatore AS id_indirizzo_impresa,
    o.id_indirizzo AS id_sede_operativa,
    - 1 AS sede_mobile_o_altro,
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
    o.cap_stab, o.lat_stab as latitudine_stab,
    o.long_stab as longitudine_stab, 
    o.comune_sede_legale AS comune_leg,
    o.prov_sede_legale AS provincia_leg,
    o.indirizzo_sede_legale AS indirizzo_leg,
    o.cap_sede_legale AS cap_leg,
    null::double precision as latitudine_leg,
    null::double precision as longitudine_leg,
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
    o.id_linea_attivita AS id_linea
   FROM opu_operatori_denormalizzati_view o
     LEFT JOIN opu_stabilimento_mobile m ON m.id_stabilimento = o.id_stabilimento
     LEFT JOIN opu_lookup_norme_master_list norme ON o.id_norma = norme.code
UNION
SELECT DISTINCT o.id_stabilimento AS riferimento_id,
    'stabId'::text AS riferimento_id_nome,
    'id_stabilimento'::text AS riferimento_id_nome_col,
    'apicoltura_imprese'::text AS riferimento_id_nome_tab,
    o.id_indirizzo_operatore AS id_indirizzo_impresa,
    o.id_indirizzo AS id_sede_operativa,
    - 1 AS sede_mobile_o_altro,
    'opu_indirizzo'::text AS riferimento_nome_tab_indirizzi,
    o.id_apicoltura_imprese AS id_impresa,
    'apicoltura_imprese'::text AS riferimento_nome_tab_impresa,
    COALESCE(o.id_soggetto_fisico, o.id_detentore) AS id_soggetto_fisico,
    'opu_soggetto_fisico'::text AS riferimento_nome_tab_soggetto_fisico,
    - 1 AS id_attivita,
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
    o.cap_stab, o.latitudine as latitudine_stab, o.longitudine as longitudine_stab,
    o.comune_sede_legale AS comune_leg,
    o.prov_sede_legale AS provincia_leg,
    o.indirizzo_sede_legale AS indirizzo_leg,
    o.cap_sede_legale AS cap_leg,
    null::double precision as latitudine_leg,
    null::double precision as longitudine_leg,
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
    o.id_stabilimento AS id_linea
   FROM apicoltura_apiari_denormalizzati_view o
union 
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
    global_arch_view.id_linea
   FROM global_arch_view
UNION
SELECT DISTINCT o.alt_id AS riferimento_id,
    'altId'::text AS riferimento_id_nome,
    'alt_id'::text AS riferimento_id_nome_col,
    'sintesis_stabilimento'::text AS riferimento_id_nome_tab,
    o.id_indirizzo_operatore AS id_indirizzo_impresa,
    o.id_indirizzo AS id_sede_operativa,
    - 1 AS sede_mobile_o_altro,
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
    o.lat_stab as latitudine_stab,
    o.long_stab as longitudine_stab,
    o.comune_sede_legale AS comune_leg,
    o.prov_sede_legale AS provincia_leg,
    o.indirizzo_sede_legale AS indirizzo_leg,
    o.cap_sede_legale AS cap_leg,
    null::double precision as latitudine_leg,
    null::double precision as longitudine_leg,
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
    o.id_linea_attivita AS id_linea
   FROM sintesis_operatori_denormalizzati_view o;

ALTER TABLE public.ricerca_anagrafiche
  OWNER TO postgres;
GRANT ALL ON TABLE public.ricerca_anagrafiche TO postgres;
GRANT SELECT ON TABLE public.ricerca_anagrafiche TO report;


--drop view osm_reg;
--drop view osm_reg_view;
--drop view view_globale_trashed_no_trashed_minimale;
--drop table ricerche_anagrafiche_old_materializzata;
create table ricerche_anagrafiche_old_materializzata as (select * from ricerca_anagrafiche);

-- View: public.view_globale_trashed_no_trashed_minimale

-- DROP VIEW public.view_globale_trashed_no_trashed_minimale;

CREATE OR REPLACE VIEW public.view_globale_trashed_no_trashed_minimale AS 
 SELECT DISTINCT controllo.num_verbale,
    controllo.orgidt,
    controllo.id_stabilimento,
    controllo.id_apiario,
    controllo.alt_id,
    controllo.ticketid,
    controllo.identificativo,
    controllo.id_nc,
    controllo.tipo_attivita,
    controllo.data_cancellazione_attivita,
    controllo.id_cu,
    controllo.tipologia_campioni,
    controllo.motivazione_piano_campione,
    controllo.motivazione_campione,
    controllo.tipo_piano_monitoraggio,
    controllo.sottopiano,
    controllo.esito,
    controllo.analita,
    controllo.matrice,
    controllo.data_inizio_controllo,
    controllo.data_chiusura_controllo,
    controllo.anno_chiusura,
    anagrafica.riferimento_id AS org_id,
    anagrafica.ragione_sociale,
    anagrafica.asl_rif,
    anagrafica.asl,
    anagrafica.tipologia_operatore AS tipologia,
    lto.description AS tipologia_operatore,
    NULL::timestamp without time zone AS data_cancellazione_operatore
   FROM ( SELECT
                CASE
                    WHEN b.barcode IS NOT NULL AND t.location IS NOT NULL THEN (t.location::text || ' - '::text) || b.barcode::text
                    WHEN b.barcode IS NULL THEN t.location::text
                    ELSE NULL::text
                END AS num_verbale,
            t.org_id AS orgidt,
            t.id_stabilimento,
            t.id_apiario,
            t.alt_id,
            t.ticketid,
            t.identificativo,
            t.id_nonconformita AS id_nc,
            t.tipologia AS tipo_attivita,
            t.trashed_date AS data_cancellazione_attivita,
                CASE
                    WHEN t.tipologia = 1 OR t.tipologia = 9 OR t.tipologia = 15 THEN ( SELECT tt.id_controllo_ufficiale
                       FROM ticket tt
                      WHERE tt.ticketid = t.id_nonconformita)
                    ELSE t.id_controllo_ufficiale
                END AS id_cu,
                CASE
                    WHEN t.tipologia = 1 THEN 'Sanzioni'::text
                    WHEN t.tipologia = 2 THEN 'Campioni'::text
                    WHEN t.tipologia = 6 THEN 'Notizie di reato'::text
                    WHEN t.tipologia = 7 THEN 'Tamponi'::text
                    WHEN t.tipologia = 8 THEN 'Non conformità'::text
                    WHEN t.tipologia = 9 THEN 'Sequestri'::text
                    WHEN t.tipologia = 15 THEN 'Follow up'::text
                    WHEN t.tipologia = 3 THEN 'Controlli Ufficiali'::text
                    ELSE 'Altro'::text
                END AS tipologia_campioni,
            t.motivazione_piano_campione,
                CASE
                    WHEN lti.code = 2 THEN lpiano.description::text::character varying
                    ELSE lti.description
                END AS motivazione_campione,
                CASE
                    WHEN lpianopadre.code > 0 THEN lpianopadre.description
                    ELSE lpiano.description
                END AS tipo_piano_monitoraggio,
            lpiano.description AS sottopiano,
                CASE
                    WHEN t.sanzioni_amministrative > 0 AND analiti.esito_id IS NULL THEN esito.description::text
                    WHEN analiti.esito_id > 0 THEN esitonew.description::text
                    WHEN t.tipologia = 2 AND t.sanzioni_amministrative < 0 AND analiti.esito_id < 0 THEN 'Da Attendere'::text
                    ELSE 'N.D'::text
                END AS esito,
            analiti.cammino AS analita,
            matrici.cammino AS matrice,
            to_date(t.assigned_date::text, 'yyyy/mm/dd'::text) AS data_inizio_controllo,
            to_date(t.closed::text, 'yyyy/mm/dd'::text) AS data_chiusura_controllo,
                CASE
                    WHEN t.closed IS NULL THEN 'N.D.'::text
                    ELSE date_part('year'::text, t.closed)::text
                END AS anno_chiusura
           FROM ticket t
             LEFT JOIN barcode_osa b ON b.id_campione::text = t.ticketid::text
             LEFT JOIN analiti_campioni analiti ON analiti.id_campione = t.ticketid
             LEFT JOIN matrici_campioni matrici ON matrici.id_campione = t.ticketid
             LEFT JOIN lookup_tipo_ispezione lti ON lti.code = t.motivazione_campione
             LEFT JOIN lookup_piano_monitoraggio lpiano ON lpiano.code = t.motivazione_piano_campione
             LEFT JOIN lookup_piano_monitoraggio lpianopadre ON lpianopadre.code = lpiano.id_padre
             LEFT JOIN lookup_esito_campione esito ON t.sanzioni_amministrative = esito.code
             LEFT JOIN lookup_esito_campione esitonew ON analiti.esito_id = esitonew.code) controllo
     JOIN ( SELECT ricerche_anagrafiche_old_materializzata.riferimento_id,
            ricerche_anagrafiche_old_materializzata.riferimento_id_nome,
            ricerche_anagrafiche_old_materializzata.riferimento_id_nome_col,
            ricerche_anagrafiche_old_materializzata.riferimento_id_nome_tab,
            ricerche_anagrafiche_old_materializzata.data_inserimento,
            ricerche_anagrafiche_old_materializzata.ragione_sociale,
            ricerche_anagrafiche_old_materializzata.asl_rif,
            ricerche_anagrafiche_old_materializzata.asl,
            ricerche_anagrafiche_old_materializzata.codice_fiscale,
            ricerche_anagrafiche_old_materializzata.codice_fiscale_rappresentante,
            ricerche_anagrafiche_old_materializzata.partita_iva,
            ricerche_anagrafiche_old_materializzata.categoria_rischio,
            ricerche_anagrafiche_old_materializzata.prossimo_controllo,
            ricerche_anagrafiche_old_materializzata.num_riconoscimento,
            ricerche_anagrafiche_old_materializzata.n_reg,
            ricerche_anagrafiche_old_materializzata.n_linea,
            ricerche_anagrafiche_old_materializzata.nominativo_rappresentante,
            ricerche_anagrafiche_old_materializzata.tipo_attivita_descrizione,
            ricerche_anagrafiche_old_materializzata.tipo_attivita,
            ricerche_anagrafiche_old_materializzata.data_inizio_attivita,
            ricerche_anagrafiche_old_materializzata.data_fine_attivita,
            ricerche_anagrafiche_old_materializzata.stato,
            ricerche_anagrafiche_old_materializzata.id_stato,
            ricerche_anagrafiche_old_materializzata.comune,
            ricerche_anagrafiche_old_materializzata.provincia_stab,
            ricerche_anagrafiche_old_materializzata.indirizzo,
            ricerche_anagrafiche_old_materializzata.cap_stab,
            ricerche_anagrafiche_old_materializzata.comune_leg,
            ricerche_anagrafiche_old_materializzata.provincia_leg,
            ricerche_anagrafiche_old_materializzata.indirizzo_leg,
            ricerche_anagrafiche_old_materializzata.cap_leg,
            ricerche_anagrafiche_old_materializzata.macroarea,
            ricerche_anagrafiche_old_materializzata.aggregazione,
            ricerche_anagrafiche_old_materializzata.attivita,
            ricerche_anagrafiche_old_materializzata.path_attivita_completo,
            ricerche_anagrafiche_old_materializzata.gestione_masterlist,
            ricerche_anagrafiche_old_materializzata.norma,
            ricerche_anagrafiche_old_materializzata.id_norma,
            ricerche_anagrafiche_old_materializzata.tipologia_operatore,
            ricerche_anagrafiche_old_materializzata.targa,
            ricerche_anagrafiche_old_materializzata.tipo_ricerca_anagrafica,
            ricerche_anagrafiche_old_materializzata.color,
            ricerche_anagrafiche_old_materializzata.n_reg_old,
            ricerche_anagrafiche_old_materializzata.id_tipo_linea_reg_ric,
            ricerche_anagrafiche_old_materializzata.id_controllo_ultima_categorizzazione,
            ricerche_anagrafiche_old_materializzata.id_linea
           FROM ricerche_anagrafiche_old_materializzata) anagrafica ON controllo.orgidt = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'organization'::text OR controllo.id_stabilimento = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'opu_stabilimento'::text OR controllo.id_apiario = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'apicoltura_apiari'::text OR controllo.alt_id = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'suap_ric_scia_stabilimento'::text OR controllo.alt_id = anagrafica.riferimento_id AND anagrafica.riferimento_id_nome_tab = 'sintesis_stabilimento'::text
     LEFT JOIN lookup_tipologia_operatore lto ON lto.code = anagrafica.tipologia_operatore;

ALTER TABLE public.view_globale_trashed_no_trashed_minimale
  OWNER TO postgres;


CREATE OR REPLACE VIEW public.osm_reg AS 
 SELECT ricerche_anagrafiche_old_materializzata.ragione_sociale,
    ricerche_anagrafiche_old_materializzata.stato,
    ricerche_anagrafiche_old_materializzata.asl,
    ricerche_anagrafiche_old_materializzata.num_riconoscimento AS numero_riconoscimento,
    (((ricerche_anagrafiche_old_materializzata.macroarea || ' '::text) || ricerche_anagrafiche_old_materializzata.aggregazione) || ' '::text) || ricerche_anagrafiche_old_materializzata.attivita AS attivita,
    ricerche_anagrafiche_old_materializzata.indirizzo AS indirizzo_so,
    ricerche_anagrafiche_old_materializzata.comune AS comune_so,
    ricerche_anagrafiche_old_materializzata.provincia_stab AS provincia_so
   FROM ricerche_anagrafiche_old_materializzata
  WHERE (ricerche_anagrafiche_old_materializzata.id_norma = ANY (ARRAY[4, '-1'::integer])) AND ricerche_anagrafiche_old_materializzata.id_tipo_linea_reg_ric = 1;

ALTER TABLE public.osm_reg
  OWNER TO postgres;
GRANT ALL ON TABLE public.osm_reg TO postgres;
GRANT SELECT ON TABLE public.osm_reg TO report;


CREATE OR REPLACE VIEW public.osm_reg_view AS 
 SELECT ricerche_anagrafiche_old_materializzata.ragione_sociale,
    ricerche_anagrafiche_old_materializzata.stato,
    ricerche_anagrafiche_old_materializzata.asl,
    ricerche_anagrafiche_old_materializzata.num_riconoscimento AS numero_riconoscimento,
    (((ricerche_anagrafiche_old_materializzata.macroarea || ' '::text) || ricerche_anagrafiche_old_materializzata.aggregazione) || ' '::text) || ricerche_anagrafiche_old_materializzata.attivita AS attivita,
    ricerche_anagrafiche_old_materializzata.indirizzo AS indirizzo_so,
    ricerche_anagrafiche_old_materializzata.comune AS comune_so,
    ricerche_anagrafiche_old_materializzata.provincia_stab AS provincia_so
   FROM ricerche_anagrafiche_old_materializzata
  WHERE (ricerche_anagrafiche_old_materializzata.id_norma = ANY (ARRAY[4, '-1'::integer])) AND ricerche_anagrafiche_old_materializzata.id_tipo_linea_reg_ric = 1;

ALTER TABLE public.osm_reg_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.osm_reg_view TO postgres;
GRANT SELECT ON TABLE public.osm_reg_view TO report;

-- funzioni di insert anagrafica


--org

-- Function: public.org_insert_into_ricerche_anagrafiche_old_materializzata(integer)

-- DROP FUNCTION public.org_insert_into_ricerche_anagrafiche_old_materializzata(integer);

CREATE OR REPLACE FUNCTION public.org_insert_into_ricerche_anagrafiche_old_materializzata(orgid integer)
  RETURNS boolean AS
$BODY$
DECLARE
idRecord int ;
BEGIN

delete from ricerche_anagrafiche_old_materializzata where riferimento_id_nome ilike'orgid' and riferimento_id_nome_tab ilike 'organization' and riferimento_id =orgId ;
insert into ricerche_anagrafiche_old_materializzata 
( riferimento_id, riferimento_id_nome, riferimento_id_nome_col, 
            riferimento_id_nome_tab, data_inserimento, ragione_sociale, asl_rif, 
            asl, codice_fiscale, codice_fiscale_rappresentante, partita_iva, 
            categoria_rischio, prossimo_controllo, num_riconoscimento, n_reg, n_linea,
            nominativo_rappresentante, tipo_attivita_descrizione, tipo_attivita, 
            data_inizio_attivita, data_fine_attivita, stato, id_stato, comune, 
            provincia_stab, indirizzo, cap_stab, latitudine_stab, longitudine_stab,
            comune_leg, provincia_leg,  indirizzo_leg, cap_leg,  latitudine_leg, longitudine_leg,

            macroarea, aggregazione, attivita, path_attivita_completo, 
            gestione_masterlist, norma, id_norma, tipologia_operatore, targa, 
            tipo_ricerca_anagrafica, color, n_reg_old, id_tipo_linea_reg_ric, 
            id_controllo_ultima_categorizzazione,id_linea)
(

 SELECT DISTINCT o.org_id AS riferimento_id,
    'orgId'::text AS riferimento_id_nome,
    'org_id'::text AS riferimento_id_nome_col,
    'organization'::text AS riferimento_id_nome_tab,
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
            WHEN o.tipologia = 17 THEN to_date(o.data_in_carattere::text, 'yyyy/mm/dd'::text)::timestamp without time zone
            ELSE to_date(o.datapresentazione::text, 'yyyy/mm/dd'::text)::timestamp without time zone
        END AS data_inizio_attivita,
        CASE
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < 'now'::text::date THEN o.data_fine_carattere
            WHEN o.tipologia = 1 AND o.source <> 1 AND (o.cessato = 1 OR o.cessato = 2) THEN o.contract_end
            WHEN o.tipologia = ANY (ARRAY[2, 9]) THEN COALESCE(to_date(o.date2::text, 'yyyy/mm/dd'::text)::timestamp without time zone, o.data_cambio_stato)
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
    tsa.macroarea,
    tsa.aggregazione,
    (((tsa.macroarea || '->'::text) || tsa.aggregazione) || '->'::text) ||
        CASE
            WHEN tsa.attivita IS NOT NULL THEN concat_ws('->'::text, tsa.attivita, tsa.attivita_specifica)
            ELSE ''::text
        END AS attivita,
    'Non presente'::text AS path_attivita_completo,
    'Non previsto'::text AS gestione_masterlist,
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
    tsa.id AS id_linea
   FROM organization o
     LEFT JOIN la_imprese_linee_attivita lai ON lai.org_id = o.org_id AND lai.trashed_date IS NULL
     LEFT JOIN stabilimenti_sottoattivita ssa ON ssa.id_stabilimento = o.org_id
     LEFT JOIN soa_sottoattivita ssoa ON ssoa.id_soa = o.org_id
     LEFT JOIN aziende az ON az.cod_azienda = o.account_number::text
     LEFT JOIN comuni1 c ON c.istat::text = ('0'::text || az.cod_comune_azienda)
     LEFT JOIN opu_lookup_norme_master_list_rel_tipologia_organzation norme ON norme.tipologia_organization = o.tipologia
     LEFT JOIN opu_lookup_norme_master_list nm ON nm.code = norme.id_opu_lookup_norme_master_list
     LEFT JOIN lookup_tipologia_operatore lto ON lto.code = o.tipologia
     LEFT JOIN lista_linee_vecchia_anagrafica_stabilimenti_soggetti_a_scia tsa ON tsa.org_id = o.org_id
     LEFT JOIN lookup_stati_stabilimenti lss ON lss.code = o.stato_istruttoria
     LEFT JOIN operatori_allevamenti op_prop ON o.codice_fiscale_rappresentante::text = op_prop.cf
     LEFT JOIN lookup_site_id l_1 ON l_1.code = o.site_id
     LEFT JOIN organization_address oa5 ON oa5.org_id = o.org_id AND oa5.address_type = 5 AND oa5.trasheddate IS NULL
     LEFT JOIN organization_address oa1 ON oa1.org_id = o.org_id AND oa1.address_type = 1 AND oa1.trasheddate IS NULL
     LEFT JOIN organization_address oa7 ON oa7.org_id = o.org_id AND oa7.address_type = 7 AND oa7.trasheddate IS NULL
  WHERE o.org_id <> 0 AND o.tipologia <> 0 AND (o.trashed_date IS NULL AND o.import_opu IS NOT TRUE OR o.trashed_date = '1970-01-01 00:00:00'::timestamp without time zone) AND ((o.tipologia = ANY (ARRAY[1, 151, 802, 152, 10, 20, 2, 800, 801])) OR o.tipologia = 3 AND o.direct_bill = false)
  and o.org_id = orgid
);
	return true ;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.org_insert_into_ricerche_anagrafiche_old_materializzata(integer)
  OWNER TO postgres;

  
  -- opu
  -- Function: public.opu_insert_into_ricerche_anagrafiche_old_materializzata(integer)

-- DROP FUNCTION public.opu_insert_into_ricerche_anagrafiche_old_materializzata(integer);

CREATE OR REPLACE FUNCTION public.opu_insert_into_ricerche_anagrafiche_old_materializzata(idstabilimento integer)
  RETURNS boolean AS
$BODY$
DECLARE
idRecord int ;
BEGIN

raise info 'sto eliminando dalla tabella ';
delete from ricerche_anagrafiche_old_materializzata where riferimento_id_nome ='stabId' and riferimento_id_nome_tab = 'opu_stabilimento'  and riferimento_id =idStabilimento ;
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
            id_controllo_ultima_categorizzazione,id_linea)
(
	

SELECT DISTINCT o.id_stabilimento AS riferimento_id,
    'stabId'::text AS riferimento_id_nome,
    'id_stabilimento'::text AS riferimento_id_nome_col,
    'opu_stabilimento'::text AS riferimento_id_nome_tab,
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
    o.id_linea_attivita AS id_linea
   FROM opu_operatori_denormalizzati_view o
     LEFT JOIN opu_stabilimento_mobile m ON m.id_stabilimento = o.id_stabilimento
     LEFT JOIN opu_lookup_norme_master_list norme ON o.id_norma = norme.code
   where o.id_stabilimento = idstabilimento   
   );
raise info 'Nuovo Inserimento Effettuato ';

	return true ;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.opu_insert_into_ricerche_anagrafiche_old_materializzata(integer)
  OWNER TO postgres;

  
  -- api
  
  -- Function: public.api_insert_into_ricerche_anagrafiche_old_materializzata(integer)

-- DROP FUNCTION public.api_insert_into_ricerche_anagrafiche_old_materializzata(integer);

CREATE OR REPLACE FUNCTION public.api_insert_into_ricerche_anagrafiche_old_materializzata(idstabilimento integer)
  RETURNS boolean AS
$BODY$
DECLARE
BEGIN

delete from ricerche_anagrafiche_old_materializzata where riferimento_id_nome ilike'stabid' and riferimento_id_nome_tab ilike 'apicoltura_imprese'  and riferimento_id =idStabilimento ;
insert into ricerche_anagrafiche_old_materializzata 
( riferimento_id, riferimento_id_nome, riferimento_id_nome_col, 
            riferimento_id_nome_tab, data_inserimento, ragione_sociale, asl_rif, 
            asl, codice_fiscale, codice_fiscale_rappresentante, partita_iva, 
            categoria_rischio, prossimo_controllo, num_riconoscimento, n_reg,  n_linea,
            nominativo_rappresentante, tipo_attivita_descrizione, tipo_attivita, 
            data_inizio_attivita, data_fine_attivita, stato, id_stato, comune, 
            provincia_stab, indirizzo, cap_stab,  latitudine_stab, longitudine_stab, comune_leg, provincia_leg, 
            indirizzo_leg, cap_leg,    latitudine_leg, longitudine_leg, macroarea, aggregazione, attivita, path_attivita_completo, 
            gestione_masterlist, norma, id_norma, tipologia_operatore, targa, 
            tipo_ricerca_anagrafica, color, n_reg_old, id_tipo_linea_reg_ric, 
            id_controllo_ultima_categorizzazione,id_linea)
(    SELECT DISTINCT o.id_stabilimento AS riferimento_id,
    'stabId'::text AS riferimento_id_nome,
    'id_stabilimento'::text AS riferimento_id_nome_col,
    'apicoltura_imprese'::text AS riferimento_id_nome_tab,
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
    o.id_stabilimento AS id_linea
   FROM apicoltura_apiari_denormalizzati_view o
    where id_stabilimento=idstabilimento
   

   );


	return true ;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.api_insert_into_ricerche_anagrafiche_old_materializzata(integer)
  OWNER TO postgres;

  --- RIC
  
  -- Function: public.ric_insert_into_ricerche_anagrafiche_old_materializzata(integer)

-- DROP FUNCTION public.ric_insert_into_ricerche_anagrafiche_old_materializzata(integer);

CREATE OR REPLACE FUNCTION public.ric_insert_into_ricerche_anagrafiche_old_materializzata(altid integer)
  RETURNS boolean AS
$BODY$
DECLARE
BEGIN

delete from ricerche_anagrafiche_old_materializzata where riferimento_id_nome ilike 'altId' and riferimento_id_nome_tab ilike 'suap_ric_scia_stabilimento' and riferimento_id =altId ;
insert into ricerche_anagrafiche_old_materializzata 
( riferimento_id, riferimento_id_nome, riferimento_id_nome_col, 
            riferimento_id_nome_tab, data_inserimento, ragione_sociale, asl_rif, 
            asl, codice_fiscale, codice_fiscale_rappresentante, partita_iva, 
            categoria_rischio, prossimo_controllo, num_riconoscimento, n_reg, n_linea,
            nominativo_rappresentante, tipo_attivita_descrizione, tipo_attivita, 
            data_inizio_attivita, data_fine_attivita, stato, id_stato, comune, 
            provincia_stab, indirizzo, cap_stab, latitudine_stab, longitudine_stab,
            comune_leg, provincia_leg,  indirizzo_leg, cap_leg,  latitudine_leg, longitudine_leg,

            macroarea, aggregazione, attivita, path_attivita_completo, 
            gestione_masterlist, norma, id_norma, tipologia_operatore, targa, 
            tipo_ricerca_anagrafica, color, n_reg_old, id_tipo_linea_reg_ric, 
            id_controllo_ultima_categorizzazione,id_linea)
            (
SELECT global_ric_view.riferimento_id,
    global_ric_view.riferimento_id_nome,
    global_ric_view.riferimento_id_nome_col,
    global_ric_view.riferimento_id_nome_tab,
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
    global_ric_view.id_linea_attivita AS id_linea
   FROM global_ric_view
  WHERE global_ric_view.id_stato = ANY (ARRAY[0, 7, 2])

 and global_ric_view.riferimento_id=altId
);

	return true ;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.ric_insert_into_ricerche_anagrafiche_old_materializzata(integer)
  OWNER TO postgres;

  
  --- SINTESIS
  
  -- Function: public.sintesis_insert_into_ricerche_anagrafiche_old_materializzata(integer)

-- DROP FUNCTION public.sintesis_insert_into_ricerche_anagrafiche_old_materializzata(integer);

CREATE OR REPLACE FUNCTION public.sintesis_insert_into_ricerche_anagrafiche_old_materializzata(idstabilimento integer)
  RETURNS boolean AS
$BODY$
DECLARE
idRecord int ;
altIdStabilimento int;
BEGIN

altIdStabilimento := (select alt_id from sintesis_stabilimento where id=idstabilimento);

raise info 'sto eliminando dalla tabella ';
delete from ricerche_anagrafiche_old_materializzata where riferimento_id_nome ='altId' and riferimento_id_nome_tab = 'sintesis_stabilimento'  and riferimento_id =altIdStabilimento ;
raise info 'Eliminazione Eseguita ';
insert into ricerche_anagrafiche_old_materializzata 
( riferimento_id, riferimento_id_nome, riferimento_id_nome_col, 
            riferimento_id_nome_tab, data_inserimento, ragione_sociale, asl_rif, 
            asl, codice_fiscale, codice_fiscale_rappresentante, partita_iva, 
            categoria_rischio, prossimo_controllo, num_riconoscimento, n_reg,n_linea,
            nominativo_rappresentante, tipo_attivita_descrizione, tipo_attivita, 
            data_inizio_attivita, data_fine_attivita, stato, id_stato, comune, 
            provincia_stab, indirizzo, cap_stab, latitudine_stab, longitudine_stab, comune_leg, provincia_leg, 
            indirizzo_leg, cap_leg, latitudine_leg, longitudine_leg, macroarea, aggregazione, attivita, path_attivita_completo, 
            gestione_masterlist, norma, id_norma, tipologia_operatore, targa, 
            tipo_ricerca_anagrafica, color, n_reg_old, id_tipo_linea_reg_ric, 
            id_controllo_ultima_categorizzazione,id_linea)
(
	

 SELECT DISTINCT o.alt_id AS riferimento_id,
    'altId'::text AS riferimento_id_nome,
    'alt_id'::text AS riferimento_id_nome_col,
    'sintesis_stabilimento'::text AS riferimento_id_nome_tab,
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
    o.id_linea_attivita AS id_linea
   FROM sintesis_operatori_denormalizzati_view o
   where o.alt_id = altidstabilimento   
   );
raise info 'Nuovo Inserimento Effettuato ';

	return true ;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.sintesis_insert_into_ricerche_anagrafiche_old_materializzata(integer)
  OWNER TO postgres;

 -- create report_campioni_new
 -- View: public.report_campioni_new

-- DROP VIEW public.report_campioni_new;

CREATE OR REPLACE VIEW public.report_campioni_new AS 
 SELECT DISTINCT asl.code AS id_asl,
    asl.description AS asl,
    COALESCE(COALESCE(o.name, aziende.ragione_sociale), api.ragione_sociale) AS ragione_sociale,
    COALESCE(COALESCE(o.partita_iva, aziende.partita_iva::bpchar), api.partita_iva::bpchar) AS partita_iva,
        CASE
            WHEN aziende.id_stabilimento > 0 THEN 'Stabilimenti Nuova Gestione'::character varying
            WHEN o.tipologia = 201 THEN 'ZONE DI MOLLUSCHICOLTURA'::character varying
            WHEN lo.code > 0 THEN lo.description
            WHEN c.id_apiario > 0 THEN 'APIARIO'::character varying
            ELSE ''::character varying
        END AS tipo_operatore,
        CASE
            WHEN o.tipologia = 1 THEN (lin.categoria::text || '-->'::text) || lin.linea_attivita::text
            WHEN o.tipologia = 3 OR o.tipologia = 97 THEN (lass.linea_attivita_stabilimenti_soa || '-->'::text) || lass.linea_attivita_stabilimenti_soa_desc
            WHEN o.tipologia = 201 THEN lzp.description
            WHEN aziende.id_stabilimento > 0 THEN aziende.path_attivita_completo::text
            ELSE 'N.D'::text
        END AS tipologia_impianto,
        CASE
            WHEN lti.codice_interno_univoco ~~* '2A'::text THEN ((((dpatatt.descrizione || ' '::text) || '-'::text) || (ind.alias_indicatore || ' : '::text)) || ind.descrizione)::character varying
            WHEN lti.code > 0 AND lti.id_indicatore > 0 THEN ((((dpatattti.descrizione || ' '::text) || '-'::text) || (indti.alias_indicatore || ' : '::text)) || indti.descrizione)::character varying
            ELSE lti.description
        END AS motivazione_campione,
        CASE
            WHEN lti.codice_interno_univoco ~~* '2A'::text THEN ((lti.description::text || ':'::text) || lpm.description::text)::character varying
            ELSE lti.description
        END AS tipo_piano_monitoraggio_old,
        CASE
            WHEN lti.codice_interno_univoco = '2A'::text AND c.assigned_date > '2015-01-01 00:00:00'::timestamp without time zone THEN lpm.code
            WHEN lti.codice_interno_univoco = '2A'::text AND c.assigned_date < '2015-01-01 00:00:00'::timestamp without time zone THEN lpm.code
            ELSE NULL::integer
        END AS id_piano,
        CASE
            WHEN c.assigned_date > '2015-01-01 00:00:00'::timestamp without time zone THEN lti.id_indicatore
            ELSE lti.code
        END AS id_attivita,
        CASE
            WHEN lti.codice_interno_univoco = '2A'::text THEN 'P'::text
            WHEN lti.description IS NOT NULL THEN 'A'::text
            ELSE 'N.D.'::text
        END AS piano_attivita,
    c.ticketid AS id_campione,
    c.assigned_date AS data_prelievo,
    c.identificativo AS identificativo_campione,
    cu.componente_nucleo AS prelevatore_1_a4,
    cu.componente_nucleo_due AS prelevatore_2_a4,
    cu.componente_nucleo_tre AS prelevatore_3_a4,
        CASE
            WHEN lpm.description::text ~~* '%MON%'::text THEN '003'::text
            WHEN lpm.description::text ~~* '%SORV%'::text THEN '007'::text
            WHEN lpm.description::text ~~* '%EXTRAPIANO%'::text THEN '005'::text
            WHEN lti.description::text ~~* '%SOSPETT%'::text THEN '001'::text
            ELSE 'N.D.'::text
        END AS strategia_campionamento_a1,
        CASE
            WHEN lpm.description::text ~~* '%BSE%'::text THEN '1'::text
            WHEN lpm.description::text ~~* '%SOSTANZE FARMACOLOGICHE%CARRY OVER)'::text THEN '8'::text
            WHEN lpm.description::text ~~* '%SOSTANZE FARMACOLOGICHE)'::text OR lpm.description::text ~~* '%MONIT. OLIGOELEMENTI%'::text THEN '2'::text
            WHEN lpm.description::text ~~* '%contaminanti%'::text THEN '3'::text
            WHEN lpm.description::text ~~* '%DIOSSINE%'::text THEN '4'::text
            WHEN lpm.description::text ~~* '%MICOTOSSINE%'::text THEN '5'::text
            WHEN lpm.description::text ~~* '%SALMONELLA%'::text THEN '6'::text
            WHEN lpm.description::text ~~* '%OGM%'::text THEN '7'::text
            ELSE '9'::text
        END AS capitoli_piani_a3,
    lsp.description AS specie_alimento_b6,
        CASE
            WHEN cfv.valore_campione IS NOT NULL AND chf.label_campo::text ~~* '%B7%'::text THEN cfv.valore_campione
            ELSE c.check_circuito_ogm
        END AS metodo_produzione_b7,
        CASE
            WHEN c.assigned_date IS NULL THEN 'N.D.'::text
            ELSE date_part('year'::text, c.assigned_date)::text
        END AS anno_campione,
    to_date(c.closed::text, 'yyyy/mm/dd'::text) AS data_chiusura_campione,
    c.id_controllo_ufficiale,
        CASE
            WHEN c.sanzioni_amministrative > 0 AND ac.esito_id IS NULL THEN esito.description::text
            WHEN ac.esito_id > 0 THEN esitonew.description::text
            WHEN c.tipologia = 2 AND c.sanzioni_amministrative < 0 AND ac.esito_id < 0 THEN 'Da Attendere'::text
            ELSE 'N.D'::text
        END AS esito,
    COALESCE(ac.esito_punteggio, c.punteggio) AS punteggio_campione,
    COALESCE(resp_new.description, responsabilita.description::text::character varying) AS responsabilita_positivita,
    COALESCE(ac.esito_data, c.est_resolution_date) AS data_esito_analita,
    ac.esito_motivazione_respingimento,
    c.solution AS note_esito_campione,
    c.cause AS codice_accettazione,
    c.location AS num_verbale,
    b.barcode AS barcode_scheda,
    split_part(ac.cammino, '->'::text, 1) AS analita_lev_1,
        CASE
            WHEN split_part(ac.cammino, '->'::text, 2) IS NOT NULL AND split_part(ac.cammino, '->'::text, 2) <> ''::text THEN split_part(ac.cammino, '->'::text, 2)
            ELSE 'N.D'::text
        END AS analita_lev_2,
        CASE
            WHEN split_part(ac.cammino, '->'::text, 3) IS NOT NULL AND split_part(ac.cammino, '->'::text, 3) <> ''::text THEN split_part(ac.cammino, '->'::text, 3)
            ELSE 'N.D'::text
        END AS analita_lev_3,
        CASE
            WHEN split_part(ac.cammino, '->'::text, 4) IS NOT NULL AND split_part(ac.cammino, '->'::text, 4) <> ''::text THEN split_part(ac.cammino, '->'::text, 4)
            ELSE 'N.D'::text
        END AS analita_lev_4,
        CASE
            WHEN split_part(mc.cammino, '->'::text, 1) IS NOT NULL AND split_part(mc.cammino, '->'::text, 1) <> ''::text THEN split_part(mc.cammino, '->'::text, 1)
            ELSE 'N.D'::text
        END AS matrice_lev_1,
        CASE
            WHEN split_part(mc.cammino, '->'::text, 2) IS NOT NULL AND split_part(mc.cammino, '->'::text, 2) <> ''::text THEN split_part(mc.cammino, '->'::text, 2)
            ELSE 'N.D'::text
        END AS matrice_lev_2,
        CASE
            WHEN split_part(mc.cammino, '->'::text, 3) IS NOT NULL AND split_part(mc.cammino, '->'::text, 3) <> ''::text THEN split_part(mc.cammino, '->'::text, 3)
            ELSE 'N.D'::text
        END AS matrice_lev_3,
    c.problem AS note_campione,
        CASE
            WHEN c.assigned_date IS NULL THEN 'N.D.'::text
            ELSE date_part('year'::text, c.assigned_date)::text
        END AS anno_controllo,
    COALESCE(lpm.codice, lti.codice) AS codice_interno_piano_attivita
   FROM ticket c
     LEFT JOIN ticket cu ON cu.ticketid::text = c.id_controllo_ufficiale::text AND cu.tipologia = 3
     LEFT JOIN lookup_site_id asl ON c.site_id = asl.code
     LEFT JOIN linee_attivita_controlli_ufficiali lacu ON lacu.id_controllo_ufficiale::text = c.id_controllo_ufficiale::text
     LEFT JOIN la_imprese_linee_attivita lla ON lla.id = lacu.id_linea_attivita
     LEFT JOIN la_rel_ateco_attivita rel ON rel.id = lla.id_rel_ateco_attivita
     LEFT JOIN la_linee_attivita lin ON rel.id_linee_attivita = lin.id
     LEFT JOIN linee_attivita_controlli_ufficiali_stab_soa lass ON lass.id_controllo_ufficiale::text = c.id_controllo_ufficiale::text
     LEFT JOIN campioni_fields_value cfv ON cfv.id_campione = c.ticketid
     LEFT JOIN campioni_html_fields chf ON chf.id = cfv.id_campioni_html_fields
     LEFT JOIN lookup_piano_monitoraggio lppna ON lppna.code = chf.id_piano_monitoraggio AND lppna.codice_interno = 370
     LEFT JOIN lookup_specie_pnaa lsp ON lsp.code::text = cfv.valore_campione
     LEFT JOIN lookup_esito_campione esito ON c.sanzioni_amministrative = esito.code
     LEFT JOIN lookup_responsabilita_positivita responsabilita ON responsabilita.code = c.responsabilita_positivita
     LEFT JOIN organization o ON o.org_id = c.org_id AND (o.trashed_date IS NULL OR o.trashed_date = '1970-01-01 00:00:00'::timestamp without time zone)
     LEFT JOIN lookup_zone_di_produzione lzp ON lzp.code = o.tipologia_acque
     LEFT JOIN opu_operatori_denormalizzati_view aziende ON aziende.id_stabilimento = c.id_stabilimento
     LEFT JOIN apicoltura_apiari_denormalizzati_view api ON api.id_apicoltura_apiari = c.id_apiario
     LEFT JOIN lookup_tipologia_operatore lo ON lo.code = o.tipologia
     LEFT JOIN lookup_piano_monitoraggio lpm ON lpm.code = c.motivazione_piano_campione
     LEFT JOIN lookup_tipo_ispezione lti ON lti.code = c.motivazione_campione
     LEFT JOIN dpat_indicatore_new ind ON ind.id = lpm.code
     LEFT JOIN dpat_piano_attivita_new dpatatt ON dpatatt.id = ind.id_piano_attivita AND (dpatatt.stato = ANY (ARRAY[0, 2])) AND dpatatt.data_scadenza IS NULL AND dpatatt.anno::double precision = date_part('years'::text, c.assigned_date)
     LEFT JOIN dpat_indicatore_new indti ON indti.id = lti.code
     LEFT JOIN dpat_piano_attivita_new dpatattti ON dpatattti.id = indti.id_piano_attivita AND (dpatattti.stato = ANY (ARRAY[0, 2])) AND dpatattti.data_scadenza IS NULL AND dpatattti.anno::double precision = date_part('years'::text, c.assigned_date)
     LEFT JOIN analiti_campioni ac ON c.ticketid = ac.id_campione
     LEFT JOIN matrici_campioni mc ON mc.id_campione = c.ticketid
     LEFT JOIN lookup_responsabilita_positivita resp_new ON resp_new.code = ac.esito_responsabilita_positivita
     LEFT JOIN lookup_esito_campione esitonew ON ac.esito_id = esitonew.code
     LEFT JOIN barcode_osa b ON b.id_campione::text = c.ticketid::text
  WHERE c.tipologia = 2 AND c.trashed_date IS NULL AND c.assigned_date IS NOT NULL AND c.location IS NOT NULL AND c.location::text <> ''::text;

ALTER TABLE public.report_campioni_new
  OWNER TO postgres;
GRANT ALL ON TABLE public.report_campioni_new TO postgres;
GRANT ALL ON TABLE public.report_campioni_new TO report;

