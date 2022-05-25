

--GISA
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
            provincia_stab, indirizzo, cap_stab, comune_leg, provincia_leg, 
            indirizzo_leg, cap_leg, macroarea, aggregazione, attivita, path_attivita_completo, 
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
            WHEN o.tipologia in (3,97) then concat_ws(' ', numaut, tipo_stab)
            WHEN o.tipologia <> 1 AND COALESCE(o.numaut, o.account_number) IS NOT NULL THEN COALESCE(o.numaut, o.account_number)
            ELSE ''::character varying
        END AS num_riconoscimento,
        CASE
            WHEN o.tipologia = 1 THEN o.account_number
            ELSE ''::character varying
        END AS n_reg,
        CASE
           WHEN o.tipologia in (3,97) then concat_ws(' ', numaut, tipo_stab)
           WHEN o.tipologia <> 1 AND COALESCE(o.numaut, o.account_number) IS NOT NULL THEN COALESCE(o.numaut, o.account_number)
           WHEN o.tipologia = 1 THEN o.account_number
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
            WHEN o.tipologia = 1 AND (o.source = 1 OR o.source IS NULL) AND o.data_fine_carattere IS NULL AND o.cessato is null THEN 'Attivo'::text
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 0 THEN 'Attivo'::text
            WHEN o.tipologia = 1 AND (o.source = 2 or o.source is null) AND o.cessato = 1 THEN 'Cessato'::text
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 2 THEN 'Sospeso'::text
            WHEN o.tipologia = 3 AND o.direct_bill = true THEN 'Non specificato'::text
            WHEN o.tipologia = 2 THEN
            CASE
                WHEN o.date2 IS NOT NULL THEN 'Cessato'::text
                ELSE 'Attivo'::text
            END
            WHEN o.tipologia = 9 THEN o.stato_impresa
            --Operatore Commerciale
            WHEN o.tipologia = 20 THEN 
            CASE
		WHEN o.data_chiusura_canile is not null THEN 'Cessato'::text
		ELSE 'Attivo'::text
            END
            --Canile
            WHEN o.tipologia = 10 THEN 
            CASE
		WHEN o.cessato=1 or o.data_fine_carattere is not null THEN 'Cessato'::text
		ELSE 'Attivo'::text
            END
            WHEN o.stato_lab = 0 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Attivo'::text
            WHEN o.stato_lab = 1 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Revocato'::text
            WHEN o.stato_lab = 2 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Sospeso'::text
            WHEN o.stato_lab = 3 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'In Domanda'::text
	    WHEN o.stato_lab = 4 AND o.tipologia <> 1 AND o.tipologia <> 2 THEN 'Cessato'::text	 
	    WHEN o.stato_istruttoria is not null AND o.tipologia in (3,97) THEN lss.description
            WHEN o.stato_lab IS NULL AND (o.tipologia = ANY (ARRAY[151, 152])) THEN o.stato
            ELSE 'N.D'::text
        END AS stato,
        CASE
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere < now() THEN 4
            WHEN o.tipologia = 1 AND o.source = 1 AND o.data_fine_carattere IS NOT NULL AND o.data_fine_carattere > now() THEN 0
            WHEN o.tipologia = 1 AND (o.source = 1 OR o.source IS NULL) AND o.data_fine_carattere IS NULL AND o.cessato is null THEN 0
            WHEN o.tipologia = 1 AND o.source = 2 AND o.cessato = 0 THEN 0
            WHEN o.tipologia = 1 AND (o.source = 2 or o.source is null) AND o.cessato = 1 THEN 4
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
	    WHEN o.stato_istruttoria is not null AND o.tipologia in (3,97) THEN lss.code
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
     LEFT JOIN operatori_allevamenti op_prop ON o.codice_fiscale_rappresentante::text = op_prop.cf
     LEFT JOIN lookup_site_id l_1 ON l_1.code = o.site_id
     LEFT JOIN lookup_stati_stabilimenti lss on lss.code = o.stato_istruttoria
     LEFT JOIN organization_address oa5 ON oa5.org_id = o.org_id AND oa5.address_type = 5 AND oa5.trasheddate IS NULL
     LEFT JOIN organization_address oa1 ON oa1.org_id = o.org_id AND oa1.address_type = 1 AND oa1.trasheddate IS NULL
     LEFT JOIN organization_address oa7 ON oa7.org_id = o.org_id AND oa7.address_type = 7 AND oa7.trasheddate IS NULL
  WHERE o.org_id <> 0 AND o.tipologia <> 0 AND (o.trashed_date IS NULL AND o.import_opu IS NOT TRUE 
  OR o.trashed_date = '1970-01-01 00:00:00'::timestamp without time zone) AND ((o.tipologia = ANY (ARRAY[1, 97, 151, 802, 152, 10, 20, 2, 800, 801])) 
  OR o.tipologia = 3 AND o.direct_bill = false)
  and o.org_id = orgid
);
	return true ;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.org_insert_into_ricerche_anagrafiche_old_materializzata(integer)
  OWNER TO postgres;
  
  

CREATE OR REPLACE VIEW opu_operatori_denormalizzati_view_bdu AS 
 SELECT opu_operatori_denormalizzati_view.flag_dia,
    opu_operatori_denormalizzati_view.id_opu_operatore,
    opu_operatori_denormalizzati_view.ragione_sociale,
    opu_operatori_denormalizzati_view.partita_iva,
    opu_operatori_denormalizzati_view.codice_fiscale_impresa,
    opu_operatori_denormalizzati_view.indirizzo_sede_legale,
    opu_operatori_denormalizzati_view.comune_sede_legale,
    opu_operatori_denormalizzati_view.istat_legale,
    opu_operatori_denormalizzati_view.cap_sede_legale,
    opu_operatori_denormalizzati_view.prov_sede_legale,
    opu_operatori_denormalizzati_view.note,
    opu_operatori_denormalizzati_view.entered,
    opu_operatori_denormalizzati_view.modified,
    opu_operatori_denormalizzati_view.enteredby,
    opu_operatori_denormalizzati_view.modifiedby,
    opu_operatori_denormalizzati_view.domicilio_digitale,
    opu_operatori_denormalizzati_view.flag_ric_ce,
    opu_operatori_denormalizzati_view.num_ric_ce,
    opu_operatori_denormalizzati_view.comune,
    opu_operatori_denormalizzati_view.id_asl,
    opu_operatori_denormalizzati_view.id_stabilimento,
    opu_operatori_denormalizzati_view.esito_import,
    opu_operatori_denormalizzati_view.data_import,
    opu_operatori_denormalizzati_view.cun,
    opu_operatori_denormalizzati_view.id_sinvsa,
    opu_operatori_denormalizzati_view.descrizione_errore,
    opu_operatori_denormalizzati_view.comune_stab,
    opu_operatori_denormalizzati_view.istat_operativo,
    opu_operatori_denormalizzati_view.indirizzo_stab,
    opu_operatori_denormalizzati_view.cap_stab,
    opu_operatori_denormalizzati_view.prov_stab,
    opu_operatori_denormalizzati_view.data_fine_dia,
    opu_operatori_denormalizzati_view.categoria_rischio,
    opu_operatori_denormalizzati_view.cf_rapp_sede_legale,
    opu_operatori_denormalizzati_view.nome_rapp_sede_legale,
    opu_operatori_denormalizzati_view.cognome_rapp_sede_legale,
    opu_operatori_denormalizzati_view.codice_registrazione,
    opu_operatori_denormalizzati_view.id_norma,
    opu_operatori_denormalizzati_view.cf_correntista,
    opu_operatori_denormalizzati_view.codice_attivita,
    opu_operatori_denormalizzati_view.primario,
    opu_operatori_denormalizzati_view.attivita,
    opu_operatori_denormalizzati_view.data_inizio,
    opu_operatori_denormalizzati_view.data_fine,
    opu_operatori_denormalizzati_view.numero_registrazione,
    opu_operatori_denormalizzati_view.indirizzo_rapp_sede_legale,
    opu_operatori_denormalizzati_view.stato,
    opu_operatori_denormalizzati_view.solo_attivita,
    opu_operatori_denormalizzati_view.data_inizio_attivita,
    opu_operatori_denormalizzati_view.data_fine_attivita,
    opu_operatori_denormalizzati_view.data_prossimo_controllo,
    opu_operatori_denormalizzati_view.id_stato,
    opu_operatori_denormalizzati_view.path_attivita_completo,
    opu_operatori_denormalizzati_view.id_indirizzo,
    opu_operatori_denormalizzati_view.linee_pregresse,
    opu_operatori_denormalizzati_view.flag_nuova_gestione,
    opu_operatori_denormalizzati_view.tipo_impresa,
    opu_operatori_denormalizzati_view.tipo_societa,
    opu_operatori_denormalizzati_view.codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.id_asl_stab,
    opu_operatori_denormalizzati_view.id_linea_attivita,
    opu_operatori_denormalizzati_view.data_mod_attivita,
    opu_operatori_denormalizzati_view.stab_entered,
    opu_operatori_denormalizzati_view.linea_numero_registrazione,
    opu_operatori_denormalizzati_view.linea_stato,
    opu_operatori_denormalizzati_view.linea_stato_text,
    opu_operatori_denormalizzati_view.linea_codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.stab_codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.id_indirizzo_operatore,
    opu_operatori_denormalizzati_view.import_opu,
    opu_operatori_denormalizzati_view.id_linea_attivita_stab,
    opu_operatori_denormalizzati_view.note_operatore,
    opu_operatori_denormalizzati_view.note_stabilimento,
    opu_operatori_denormalizzati_view.linea_codice_nazionale,
    opu_operatori_denormalizzati_view.flag_pnaa,
    opu_operatori_denormalizzati_view.stab_inserito_da,
    opu_operatori_denormalizzati_view.stab_id_attivita,
    opu_operatori_denormalizzati_view.stab_descrizione_attivita,
    opu_operatori_denormalizzati_view.stab_id_carattere,
    opu_operatori_denormalizzati_view.stab_descrizione_carattere,
    opu_operatori_denormalizzati_view.impresa_id_tipo_impresa,
    opu_operatori_denormalizzati_view.stab_asl,
    opu_operatori_denormalizzati_view.flag_clean,
    opu_operatori_denormalizzati_view.data_generazione_numero,
    opu_operatori_denormalizzati_view.lat_stab,
    opu_operatori_denormalizzati_view.long_stab,
    opu_operatori_denormalizzati_view.linea_entered,
    opu_operatori_denormalizzati_view.linea_modified,
    opu_operatori_denormalizzati_view.macroarea,
    opu_operatori_denormalizzati_view.aggregazione,
    opu_operatori_denormalizzati_view.attivita_xml,
    opu_operatori_denormalizzati_view.codiceistatasl_old,
    opu_operatori_denormalizzati_view.sigla_prov_operativa,
    opu_operatori_denormalizzati_view.sigla_prov_legale,
    opu_operatori_denormalizzati_view.sigla_prov_soggfisico,
    opu_operatori_denormalizzati_view.comune_residenza,
    opu_operatori_denormalizzati_view.data_nascita_rapp_sede_legale,
    opu_operatori_denormalizzati_view.impresa_id_tipo_societa,
    opu_operatori_denormalizzati_view.comune_nascita_rapp_sede_legale,
    opu_operatori_denormalizzati_view.sesso,
    opu_operatori_denormalizzati_view.civico,
    opu_operatori_denormalizzati_view.toponimo_residenza,
    opu_operatori_denormalizzati_view.id_toponimo_residenza,
    opu_operatori_denormalizzati_view.civico_sede_legale,
    opu_operatori_denormalizzati_view.tiponimo_sede_legale,
    opu_operatori_denormalizzati_view.toponimo_sede_legale,
    opu_operatori_denormalizzati_view.civico_sede_stab,
    opu_operatori_denormalizzati_view.tiponimo_sede_stab,
    opu_operatori_denormalizzati_view.toponimo_sede_stab,
    opu_operatori_denormalizzati_view.via_rapp_sede_legale,
    opu_operatori_denormalizzati_view.via_sede_legale,
    opu_operatori_denormalizzati_view.id_comune_richiesta,
    opu_operatori_denormalizzati_view.comune_richiesta,
    opu_operatori_denormalizzati_view.via_stabilimento_calcolata,
    opu_operatori_denormalizzati_view.civico_stabilimento_calcolato,
    opu_operatori_denormalizzati_view.cap_residenza,
    opu_operatori_denormalizzati_view.nazione_residenza,
    opu_operatori_denormalizzati_view.nazione_sede_legale,
    opu_operatori_denormalizzati_view.nazione_stab,
    opu_operatori_denormalizzati_view.id_lookup_tipo_linee_mobili,
    opu_operatori_denormalizzati_view.id_tipo_linea_produttiva,
    opu_operatori_denormalizzati_view.id_soggetto_fisico,
        CASE
            WHEN opu_operatori_denormalizzati_view.attivita ~~* '%strutture veterinarie%ospedale veterinario%'::text OR opu_operatori_denormalizzati_view.attivita ~~* '%strutture veterinarie%ambulatorio veterinario%'::text OR opu_operatori_denormalizzati_view.attivita ~~* '%canile%'::text OR opu_operatori_denormalizzati_view.attivita ~~* '%allevamento cani%'::text THEN 5
            WHEN opu_operatori_denormalizzati_view.attivita ~~* '%vendita al dettaglio animali da compagnia%'::text OR opu_operatori_denormalizzati_view.attivita ~~* '%ingrosso animali da compagnia%'::text THEN 6
            ELSE NULL::integer
        END AS id_linea_bdu
   FROM opu_operatori_denormalizzati_view
  WHERE opu_operatori_denormalizzati_view.attivita ~~* '%canile%'::text OR opu_operatori_denormalizzati_view.attivita ~~* '%vendita al dettaglio animali da compagnia%'::text OR opu_operatori_denormalizzati_view.attivita ~~* '%ingrosso animali da compagnia%'::text OR 
  opu_operatori_denormalizzati_view.attivita ~~* '%strutture veterinarie%ospedale veterinario%'::text OR 
  opu_operatori_denormalizzati_view.attivita ~~* '%strutture veterinarie%ambulatorio veterinario%'::text OR 
  opu_operatori_denormalizzati_view.attivita ~~* '%allevamento cani%'::text;

ALTER TABLE opu_operatori_denormalizzati_view_bdu
  OWNER TO postgres;
GRANT ALL ON TABLE opu_operatori_denormalizzati_view_bdu TO postgres;
GRANT SELECT ON TABLE opu_operatori_denormalizzati_view_bdu TO report;



--Bdu
ALTER TABLE opu_operatore_variazione_titolarita_stabilimenti ADD COLUMN id_operatore_old integer;
ALTER TABLE opu_operatore_variazione_titolarita_stabilimenti ADD COLUMN id_operatore_new integer;


--- cessazione VAM
--vam
alter table clinica add column data_cessazione timestamp without time zone;
alter table clinica add column data_cessazione_presunta boolean;
alter table clinica add column note_cessazione text;
alter table clinica add column note_hd text;

CREATE OR REPLACE FUNCTION public_functions.ricerca_clinica(
    inputNome text,
    inputAsl integer,
    inputStato integer)
  RETURNS text AS
$BODY$
DECLARE
outputJson text; 
BEGIN 


outputJson := (
select array_to_json(array_agg(t))
from (
  select c.id, c.nome, asl.description as asl, COALESCE(to_char(c.data_cessazione, 'dd/MM/yyyy'), '') as data_cessazione from clinica c
left join lookup_asl asl on asl.id = c.asl

where 1=1
and nome ilike '%'||inputNome||'%'
and ((inputAsl>1 and c.asl = inputAsl) or (inputAsl=-1))
and ((inputStato=0 and c.data_cessazione is null) or (inputStato=1 and c.data_cessazione is not null) or (inputStato=-1))
) t
);


return outputJson;
	

END 

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.ricerca_clinica(text, integer, integer)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION public_functions.cessazione_clinica(
    inputIdClinica integer,
    inputIdCanileBdu integer,
    inputData timestamp with time zone,
    inputNote text)
  RETURNS text AS
$BODY$
DECLARE
inputDataTimestamp timestamp without time zone;
outputJson text; 
BEGIN 

outputJson := 'Specificare idClinica o idCanileBdu';

if(inputIdClinica>0 or inputIdCanileBdu>0) then

update clinica set data_cessazione = inputData, data_cessazione_presunta = true, note_cessazione = inputNote, note_hd = 'Cessata tramite dbi in data '||now() 
where ((inputIdClinica > 0 and id = inputIdClinica) or (inputIdCanileBdu > 0 and canile_bdu = inputIdCanileBdu)) and data_cessazione is null;


outputJson := (
select array_to_json(array_agg(t))
from (
  select c.id, c.nome, asl.description as asl, COALESCE(to_char(c.data_cessazione, 'dd/MM/yyyy'), '') as data_cessazione, note_cessazione, note_hd from clinica c
left join lookup_asl asl on asl.id = c.asl

where 1=1
and c.id = inputIdClinica
) t
);
end if;

return outputJson;
	
END 

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.cessazione_clinica(integer, integer, timestamp  with time zone, text)
  OWNER TO postgres;


-- Function: public.dbi_get_cliniche_utente()

-- DROP FUNCTION public.dbi_get_cliniche_utente();

CREATE OR REPLACE FUNCTION public.dbi_get_cliniche_utente()
  RETURNS TABLE(asl_id integer, id_clinica integer, nome_clinica text) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
FOR asl_id, id_clinica, nome_clinica
in
select asl, id, concat(case when data_cessazione is not null then '(CESSATA) ' else '' end,  nome) as nome from clinica  where trashed_date is null order by asl,nome
LOOP
        RETURN NEXT;
END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.dbi_get_cliniche_utente()
  OWNER TO postgres;

  

--- cessazione automatica BDU
--bdu
alter table opu_relazione_stabilimento_linee_produttive add column data_fine_presunta boolean default false;


CREATE OR REPLACE FUNCTION public_functions.bdu_cessazione_automatica(
    orgIdC integer,
    dataCessazione timestamp without time zone,
    noteCessazione text)
  RETURNS text AS
$BODY$
DECLARE
idRelStabLp integer;
ragioneSociale text;
idOperatore integer;
msgTemp text;
msg text;
BEGIN

msg := '';
idRelStabLp := (select id from opu_relazione_stabilimento_linee_produttive where id = orgIdC);

IF idRelStabLp > 0 THEN

ragioneSociale:= (select ragione_sociale from opu_operatori_denormalizzati_view where id_rel_stab_lp = idRelStabLp);
idOperatore:= (select id_opu_operatore from opu_operatori_denormalizzati_view where id_rel_stab_lp = idRelStabLp);

UPDATE opu_relazione_stabilimento_linee_produttive set data_fine = dataCessazione, data_fine_presunta = true, note_internal_use_only = concat_ws('; CESSAZIONE DA GISA: ', note_internal_use_only, noteCessazione) where id = idRelStabLp;

msgTemp := (select * from public_functions.update_opu_materializato(idOperatore));

msg:= 'Cessato operatore in BDU: ' || ragioneSociale || ';';

ELSE
msg:= 'Nessun operatore da cessare in BDU;';

END IF;

     RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
  
  

  
  
  
--- inserimento clinica VAM
  
-- gisa
  
insert into permission (category_id, permission, permission_view, description) values (12, 'sincronizza-vam', true, 'SINCRONIZZAZIONI CLINICHE GISA-VAM');
insert into role_permission (role_id, permission_id, role_view) values (1, (select permission_id from permission where permission ilike 'sincronizza-vam'), true);
insert into role_permission (role_id, permission_id, role_view) values (32, (select permission_id from permission where permission ilike 'sincronizza-vam'), true);


-- vam
alter table clinica add column id_stabilimento_gisa integer;


		

CREATE OR REPLACE FUNCTION public_functions.inserimento_clinica(
    inputIdGisa integer,
    inputNome text,
    inputNomeBreve text,
    inputAsl text,
    inputComune text,
    inputIndirizzo text,
    inputEmail text,
    inputTelefono text,
    inputNoteHd text)
  RETURNS text AS
$BODY$
DECLARE
inputIdAsl integer;
inputIdComune integer;
outputIdClinica integer; 
outputMsg text; 
BEGIN 

outputIdClinica := (select id from clinica where id_stabilimento_gisa = inputIdGisa);

IF outputIdClinica >0 THEN

outputMsg := 'Esiste gia'' una clinica in VAM associata a questo stabilimento. Clinica non inserita.';

ELSE

inputIdAsl:= (select id from lookup_asl where description ilike inputAsl);
inputIdComune:= (select id from lookup_comuni where description ilike inputComune);

insert into clinica (nome, nome_breve, asl, comune, indirizzo, email, telefono, entered, note_hd, id_stabilimento_gisa) 
values (inputNome, inputNomeBreve, inputIdAsl, inputIdComune, inputIndirizzo, inputEmail, inputTelefono, now(), inputNoteHd, inputIdGisa);
outputMsg := 'Sincronizzazione effettuata. Clinica inserita in VAM.';

END IF;

return outputMsg;
	
END 

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

  
  --bdu
  
  --variazione titolarita
  -- Function: public_functions.suap_variazione_titolarita(character varying, character varying, text, text, text, text, timestamp without time zone, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, integer, integer, text, text, text, text)

-- DROP FUNCTION public_functions.suap_variazione_titolarita(character varying, character varying, text, text, text, text, timestamp without time zone, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, integer, integer, text, text, text, text);

CREATE OR REPLACE FUNCTION public_functions.suap_variazione_titolarita(
    partita_iva character varying,
    codice_fiscale_impresa character varying,
    ragione_sociale text,
    nome_rapp_sede_legale_out text,
    cognome_rapp_sede_legale_out text,
    cf_rapp_sede_legale_out text,
    data_nascita_rapp_sede_legale_out timestamp without time zone,
    sesso_out text,
    comune_nascita_rapp_sede_legale_out text,
    sigla_prov_soggfisico_out text,
    comune_residenza_out text,
    indirizzo_rapp_sede_legale_out text,
    civico_residenza_out text,
    cap_residenza text,
    nazione_residenza text,
    sigla_provincia_legale text,
    comune_legale text,
    indirizzo_legale text,
    civico_legale text,
    cap_legale text,
    nazione_legale text,
    sigla_provincia_stab text,
    comune_stab text,
    indirizzo_stab text,
    civico_stab text,
    cap_stab text,
    nazione_stab text,
    id_asl_in integer,
    idutente integer,
    toponimo_rapp_sede_legale text,
    toponimo_legale text,
    toponimo_stab text,
    id_rel_stab_lp_gisa integer) RETURNS integer AS $$
<< outerblock >>
DECLARE
id_impresa integer;
id_impresa_vecchia integer;
id_soggetto_out integer;
id_stab_out integer;
id_stabl_out integer;
esito text;
id_operatore_old_out integer;
BEGIN
id_stab_out := (select id from opu_stabilimento where id_stabilimento_gisa = id_rel_stab_lp_gisa );
raise info 'id_stab_out: %', id_stab_out;
id_operatore_old_out := (select id_operatore from opu_stabilimento where id = id_stab_out);
raise info 'id_operatore_old_out: %', id_operatore_old_out;
-- 1. soggetto fisico ed indirizzo rappresentante legale che ritorna id_soggetto fisico
id_soggetto_out := (select * from public_functions.insert_soggetto_fisico_bdu(nome_rapp_sede_legale_out,cognome_rapp_sede_legale_out, cf_rapp_sede_legale_out, data_nascita_rapp_sede_legale_out, 
sesso_out,comune_nascita_rapp_sede_legale_out, sigla_prov_soggfisico_out, comune_residenza_out, indirizzo_rapp_sede_legale_out,toponimo_rapp_sede_legale,civico_residenza_out, cap_residenza, nazione_residenza,idutente));
--2 impresa in relazione al soggetto fisico e all'indirizzo restituisce una tupla con id operatore partita_iva, ragione sociale e cf...
raise info 'id_soggetto_out: %', id_soggetto_out;
raise info 'Eseguo suap_insert_impresa_bdu: 
select * from public_functions.suap_insert_impresa_bdu(%,%,%,%,%,%,%,%,%,%,%); ', id_soggetto_out,partita_iva,codice_fiscale_impresa,ragione_sociale,sigla_provincia_legale,comune_legale,indirizzo_legale,toponimo_legale,
civico_legale,cap_legale,nazione_legale;
id_impresa := (select * from public_functions.suap_insert_impresa_bdu(id_soggetto_out,partita_iva,codice_fiscale_impresa,ragione_sociale,sigla_provincia_legale,comune_legale,indirizzo_legale,toponimo_legale,
civico_legale,cap_legale,nazione_legale));
raise info 'id_impresa: %', id_impresa;


id_impresa_vecchia := (select id_operatore from opu_stabilimento where id = id_stab_out );
raise info 'id_impresa_vecchia: %', id_impresa_vecchia;
--aggiornamento id_operatore su opu_stabilimento
update opu_stabilimento set id_operatore  = id_impresa where id = id_stab_out;
--aggiornamento storico



insert into opu_operatore_variazione_titolarita_stabilimenti (
id_stab_bdu ,
id_operatore_old ,
id_operatore_new ,
id_stab_gisa,
data_operazione )
values (id_stab_out,id_operatore_old_out,id_impresa, id_rel_stab_lp_gisa, current_timestamp);


--aggiornamento vista materializzato tramite id_impresa
--Viene fatto l'aggiornamento anche per il vecchio id impresa per rimuoverlo dal tabellone se no schiatta quando si va 
--ad inserire il nuovo id_impresa perchè trova un record già esistente con stesso id_rel_stab_lp
if(id_impresa_vecchia is not null) then
   raise info 'update su id_impresa_vecchia: % ', id_impresa_vecchia;
   esito := (select * from public_functions.update_opu_materializato(id_impresa_vecchia));
end if;
esito := (select * from public_functions.update_opu_materializato(id_impresa));
--aggiornamento informazioni canile da gisa campi estesi a canili bdu
--if id_stabl_out > 0 then
--  raise INFO '%', id_stabl_out;
--  esito_estesi := (select * from public_functions.aggiorna_info_canile(id_rel_stab_lp_gisa::int, id_stabl_out));
--end if;
   return id_impresa;
END;
$$ LANGUAGE plpgsql;
ALTER FUNCTION public_functions.suap_variazione_titolarita(character varying, character varying, text, text, text, text, timestamp without time zone, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, text, integer, integer, text, text, text, integer)
  OWNER TO postgres;


  
CREATE OR REPLACE FUNCTION public_functions.insert_soggetto_fisico_bdu(
    nome_rapp_sede_legale_out text,
    cognome_rapp_sede_legale_out text,
    cf_rapp_sede_legale_out text,
    data_nascita_rapp_sede_legale_out timestamp without time zone,
    sesso_out text,
    comune_nascita_rapp_sede_legale_out text,
    sigla_prov_soggfisico_out text,
    comune_residenza_out text,
    indirizzo_rapp_sede_legale_out text,
    toponimo_out text,
    civico_residenza_out text,
    cap_residenza text,
    nazione_residenza text,
    idutente integer)
  RETURNS integer AS
$BODY$
DECLARE
insert boolean;
id_soggetto_fisico int ;
nome_rapp_sede_legale_temp text ;
cognome_rapp_sede_legale_temp text;
cf_rapp_sede_legale_temp text;
data_nascita_rapp_sede_legale_temp timestamp;
sesso_temp text;
comune_nascita_rapp_sede_legale_temp text ;
id_indirizzo int ;
BEGIN
insert := false;
FOR
id_soggetto_fisico,
cognome_rapp_sede_legale_temp ,
nome_rapp_sede_legale_temp  ,
comune_nascita_rapp_sede_legale_temp  ,
data_nascita_rapp_sede_legale_temp ,
cf_rapp_sede_legale_temp ,
sesso_temp 
IN
/*RECUPERO SOGGETTO DA ANAGRAFICA UFFICIALE SE ESISTE*/
select s.id,cognome,nome,comune_nascita,data_nascita,codice_fiscale,sesso
from opu_soggetto_fisico s where s.codice_fiscale ilike trim( cf_rapp_sede_legale_out)
LOOP
if 	upper(trim(nome_rapp_sede_legale_temp))=upper(trim(nome_rapp_sede_legale_out)) and 
	upper(trim(cognome_rapp_sede_legale_temp))=upper(trim(cognome_rapp_sede_legale_out)) and
	data_nascita_rapp_sede_legale_temp=data_nascita_rapp_sede_legale_out and
	upper(trim(sesso_temp))=upper(trim(sesso_out)) and
	upper(trim(comune_nascita_rapp_sede_legale_temp))=upper(trim(comune_nascita_rapp_sede_legale_out)) 
	/* SE  TUTTI  CAMPI SONO UGUALI RITORNO QUELLO ESISTENTE */
then
raise info 'insert_soggetto_fisico_bdu - TUTTI  CAMPI SONO UGUALI AL SOGGETTO FISICO ESISTENTE';	
return id_soggetto_fisico ;
end if ;
end loop ;

raise info 'insert_soggetto_fisico_bdu - id_soggetto_fisico: %', id_soggetto_fisico;

if(id_soggetto_fisico is null) then
insert :=true;
id_soggetto_fisico:=(select nextval('opu_soggetto_fisico_id_seq'));
raise info 'insert_soggetto_fisico_bdu - id_soggetto_fisico: %', id_soggetto_fisico;
end if;

select * into id_indirizzo from public_functions.suap_convergenza_indirizzo(sigla_prov_soggfisico_out, comune_residenza_out, indirizzo_rapp_sede_legale_out, toponimo_out,
civico_residenza_out, cap_residenza, nazione_residenza) ;

--Se id soggetto fisico è valorizzato vuol dire che è stato recuperato nela select avente filtro il codice fiscale
if(insert) then
raise info 'insert_soggetto_fisico_bdu - INSERISCO SOGGETTO FISICO';
insert into opu_soggetto_fisico (id,cognome,nome,comune_nascita,data_nascita,codice_fiscale,indirizzo_id,sesso,enteredby,modifiedby)
values ( id_soggetto_fisico , cognome_rapp_sede_legale_out ,nome_rapp_sede_legale_out , comune_nascita_rapp_sede_legale_out
,data_nascita_rapp_sede_legale_out, cf_rapp_sede_legale_out,id_indirizzo,sesso_out,idutente,idutente);
return  id_soggetto_fisico;
else
raise info 'insert_soggetto_fisico_bdu - UPDATE SOGGETTO FISICO';
update opu_soggetto_fisico set cognome = cognome_rapp_sede_legale_out,
                               nome = nome_rapp_sede_legale_out, 
                               comune_nascita = comune_nascita_rapp_sede_legale_out,
                               data_nascita = data_nascita_rapp_sede_legale_out,
                               codice_fiscale = cf_rapp_sede_legale_out,
                               indirizzo_id = id_indirizzo,
                               sesso = sesso_out,
                               modifiedby = idutente
                               where id = id_soggetto_fisico;
return id_soggetto_fisico;

end if;	
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public_functions.insert_soggetto_fisico_bdu(text, text, text, timestamp without time zone, text, text, text, text, text, text, text, text, text, integer)
  OWNER TO postgres;





  CREATE OR REPLACE FUNCTION public_functions.suap_insert_impresa_bdu(
    id_soggetto_fisico_in integer,
    partita_iva_in character varying,
    codice_fiscale_impresa_in character varying,
    ragione_sociale_in character varying,
    sigla_prov_sede_legale text,
    comune_sede_legale text,
    indirizzo_legale text,
    toponimo text,
    civico_legale text,
    cap_legale text,
    nazione_legale text)
  RETURNS integer AS
$$
<< outerblock >>
DECLARE

opu_impresa impresa ;
occorrenzeEsistenti integer  ;
numIndirizzi integer; 
impresa_id integer;
suap_id_sede integer;
id_indirizzo integer;
BEGIN
occorrenzeEsistenti:=0;
opu_impresa.id_esito:=1; /*NON ESISTENTE*/
/*CONTROLLO SE L'IMPRESA ESISTE IN OPU*/


raise info 'suap_insert_impresa_bdu - query esistenza impresa:
select opu_operatore.id,partita_iva,codice_fiscale_impresa,ragione_sociale 
from  opu_operatore
join opu_rel_operatore_soggetto_fisico rel on rel.id_operatore=opu_operatore.id --and rel.enabled
where (opu_operatore.partita_iva= % or opu_operatore.codice_fiscale_impresa =  %) and opu_operatore.trashed_date is null 
and rel.tipo_soggetto_fisico=2 and rel.stato_ruolo=1 and rel.id_soggetto_fisico= % ', partita_iva_in, codice_fiscale_impresa_in,id_soggetto_fisico_in;


for 
opu_impresa.id, opu_impresa.partita_iva,opu_impresa.codice_fiscale_impresa,opu_impresa.ragione_sociale
 IN
select opu_operatore.id,partita_iva,codice_fiscale_impresa,ragione_sociale 
from  opu_operatore
join opu_rel_operatore_soggetto_fisico rel on rel.id_operatore=opu_operatore.id --and rel.enabled
where (opu_operatore.partita_iva=partita_iva_in or opu_operatore.codice_fiscale_impresa = codice_fiscale_impresa_in) and opu_operatore.trashed_date is null 
and rel.tipo_soggetto_fisico=2 and rel.stato_ruolo=1 and rel.id_soggetto_fisico=id_soggetto_fisico_in

LOOP
occorrenzeEsistenti:=occorrenzeEsistenti+1;
	numIndirizzi:=(select count(*) from(select  distinct upper (trim(via)) ,upper (trim(cap)),upper (trim(provincia)),comune
	from opu_indirizzo where id in(select sede.id_indirizzo from opu_relazione_operatore_sede sede where sede.id_operatore = opu_impresa.id ))a);
	
raise info 'numIndirizzi : %',numIndirizzi;
/*SE ESISTE UNA IMPRESACON GLI STESSI DATI NON LA  INSERISCO E RITORNO QUELLA ESISTENTE*/
raise info 'suap_insert_impresa_bdu - controllo uguaglianza:
%=% and % = % and %=% and %=1 ', partita_iva_in, opu_impresa.partita_iva, codice_fiscale_impresa_in, opu_impresa.codice_fiscale_impresa, 
ragione_sociale_in , opu_impresa.ragione_sociale, numIndirizzi;
if 	partita_iva_in=opu_impresa.partita_iva and 
	codice_fiscale_impresa_in = opu_impresa.codice_fiscale_impresa and 
	ragione_sociale_in=opu_impresa.ragione_sociale and 
        numIndirizzi=1
then	
	raise info 'suap_insert_impresa_bdu - Ritorno l''impresa con id: %, partita_iva: %, codice_fiscale_impresa: %, ragione_sociale: %', opu_impresa.id, opu_impresa.partita_iva,opu_impresa.codice_fiscale_impresa,opu_impresa.ragione_sociale;
	return opu_impresa.id ;
end if;	
numIndirizzi:=0;
	
	END LOOP;

impresa_id:=(select nextval('opu_operatore_id_seq'));

raise info 'suap_insert_impresa_bdu - Impresa non trovata con i dati passati. nextval impresa_id: %', impresa_id;


insert into opu_operatore (id,partita_iva,codice_fiscale_impresa,ragione_sociale,enteredby,modifiedby,entered,modified)
values (
impresa_id,
partita_iva_in,
codice_fiscale_impresa_in,
ragione_sociale_in,
11934,
11934,current_timestamp,current_timestamp
);

--inserimento del record di relazione tra soggetto_fisico e id_operatore
insert into opu_rel_operatore_soggetto_fisico (id_soggetto_fisico,id_operatore,tipo_soggetto_fisico,data_inizio,stato_ruolo)
values(id_soggetto_fisico_in,impresa_id,2,current_timestamp,1);	

--verifico esistenza id_indirizzo sede legale
select * into id_indirizzo from public_functions.suap_convergenza_indirizzo(sigla_prov_sede_legale, comune_sede_legale, indirizzo_legale, toponimo, civico_legale, cap_legale, nazione_legale);

suap_id_sede :=(select nextval('opu_relazione_operatore_sede_id_seq'));
--da approfondire stato e tipologia sede

insert into opu_relazione_operatore_sede(id,id_operatore,id_indirizzo,tipologia_sede,stato_sede)
values(suap_id_sede, impresa_id,id_indirizzo,1,1);	

return impresa_id;

 END;
$$ LANGUAGE plpgsql;
 
ALTER FUNCTION public_functions.suap_insert_impresa_bdu(integer, character varying, character varying, character varying, text, text, text, text, text, text, text)
  OWNER TO postgres;




--vam
CREATE OR REPLACE FUNCTION public_functions.suap_variazione_titolarita(
    ragione_sociale text,
    idutente integer,
    id_rel_stab_lp_gisa integer) RETURNS integer AS $$
<< outerblock >>
DECLARE
id_clinica integer;
BEGIN
id_clinica := (select id from clinica where id_stabilimento_gisa = id_rel_stab_lp_gisa and trashed_date is null);
raise info 'id_clinica: %', id_clinica;

if(id_clinica is not null and id_clinica >0) then
  update clinica set nome = ragione_sociale, modified_by = idutente where id = id_clinica;
end if;

   return id_clinica;
END;
$$ LANGUAGE plpgsql;
ALTER FUNCTION public_functions.suap_variazione_titolarita(text, integer, integer)
  OWNER TO postgres;





--modifica stato dei luoghi

--gisa
alter table suap_ric_scia_stabilimento add column superficie integer;


--bdu
--drop function public_functions.update_superficie_canile(integer, integer,integer)

CREATE OR REPLACE FUNCTION public_functions.update_superficie_canile(id_stabilimento_gisa_in integer, mq_new integer, idUtente integer)
  RETURNS text AS
$BODY$
DECLARE msg text;
        mq_old integer;
        note_in text;
        id_stab integer;
        id_rel_stab_lp integer;
 BEGIN
    id_stab := (select id from opu_stabilimento o where o.id_stabilimento_gisa = id_stabilimento_gisa_in );
    id_rel_stab_lp := (select id from opu_relazione_stabilimento_linee_produttive where id_linea_produttiva = 5 and id_stabilimento = id_stab);
    mq_old := (select mq_disponibili from opu_informazioni_canile where id_relazione_stabilimento_linea_produttiva = id_rel_stab_lp);
    if mq_old is null then
	mq_old := 'null';
    end if;
    note_in := '. ' || now() || ' - Modifica quadratura da ''Gisa Modifica Stato Dei Luoghi'' passando da mq ' || mq_old || ' a mq ' || mq_new || ' usando l''utente ' || idUtente;
    update opu_informazioni_canile set note_internal_use_only = concat_ws (';', note_internal_use_only, note_in),  mq_disponibili = mq_new where id_relazione_stabilimento_linea_produttiva = id_rel_stab_lp;
RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.update_superficie_canile(integer, integer,integer)
  OWNER TO postgres;


-- drop function public_functions.aggiorna_info_canile(    idRelStabLpGisa integer,    idstabilimentobdu integer);
    
CREATE OR REPLACE FUNCTION public_functions.aggiorna_info_canile(
    idRelStabLpGisa integer,
    idstabilimentobdu integer,
    idRelStabLpBdu integer)
  RETURNS text AS
$BODY$
DECLARE
autorizzazione_si_no text;
autorizzazione_note text;
data_autorizzazione_ text;
mq_specie_ text;
linea integer;
altro text;
cursore refcursor;
curs refcursor;
curso refcursor;
referente_cognome text;
referente_nome text;
referente_cf text;
idSoggettCanile int ;
id_stabilimento_gisa int ;
data_autorizzazione_date timestamp without time zone;
BEGIN

select id_stabilimento into id_stabilimento_gisa from opu_operatori_denormalizzati_canili_opc_gisa where id_linea_attivita = idRelStabLpGisa;

SELECT * FROM crosstab( 'select id_opu_rel_stab_linea, id_linee_mobili_html_fields,valore_campo
	from fg_linee_mobili_fields_value where id_opu_rel_stab_linea =' || idRelStabLpGisa ||' order by id_rel_stab_linea,id_linee_mobili_html_fields')
as ct( linea integer,autorizzazione_si_no text, autorizzazione_note text,data_autorizzazione_ text, mq_specie_ text, referente_cognome text,referente_nome text,referente_cf text)
into  linea ,autorizzazione_si_no , autorizzazione_note ,data_autorizzazione_ , mq_specie_ , referente_cognome ,referente_nome ,referente_cf ;


          raise info 'mq_specie = %', mq_specie_;
          raise info 'linea = %', linea;
          raise info 'autorizzazione_si_no = %', autorizzazione_si_no;
          raise info 'autorizzazione_note = %', autorizzazione_note;
          raise info 'data_autorizzazione_ = %', data_autorizzazione_;

        if(data_autorizzazione_!='') then
          insert into opu_informazioni_canile (id_relazione_stabilimento_linea_produttiva, abusivo, centro_sterilizzazione, autorizzazione, data_autorizzazione,mq_disponibili) values
		(idRelStabLpBdu,false,false,autorizzazione_note,to_date(data_autorizzazione_::text, 'dd/MM/yyyy'::text)::timestamp without time zone,mq_specie_::int );
		else
		insert into opu_informazioni_canile (id_relazione_stabilimento_linea_produttiva, abusivo, centro_sterilizzazione, autorizzazione, data_autorizzazione,mq_disponibili) values
		(idRelStabLpBdu,false,false,autorizzazione_note,null,mq_specie_::int );
		end if;
	
       idSoggettCanile:=(select * from public_functions.insert_soggetto_fisico_bdu(referente_nome , referente_cognome ,referente_cf));
       update opu_stabilimento set id_soggetto_fisico =idSoggettCanile where id =idstabilimentoBdu;

     RETURN 'OK';
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public_functions.aggiorna_info_canile(integer, integer,integer)
  OWNER TO postgres;




CREATE OR REPLACE FUNCTION public_functions.suap_inserisci_canile_bdu(idstabilimentogisa integer)
  RETURNS integer AS
$BODY$
DECLARE
id_impresa integer;
id_soggetto_out integer;
id_stab_out integer;
id_stabl_out integer;
esito text;
esito_estesi text;
id_rel_stab_lp_gisa int ;
idStabBdu int ;
idLineaBdu int ;
idlineaGisa int ;
specieAnimaliOpComm text;
trasmettiBdu boolean;

flagOpComm boolean ;
flagCanile boolean;
BEGIN


select st.id into idStabBdu from opu_stabilimento st join opu_operatore op on op.id=st.id_operatore 
where id_stabilimento_gisa =idstabilimentogisa and op.trashed_date is null;


flagCanile:=false;
flagOpComm:=false;
trasmettiBdu:=false;

for idLineaBdu , idlineaGisa in
select distinct on (id_linea_bdu) id_linea_bdu,id_linea_attivita 
from opu_operatori_denormalizzati_canili_opc_gisa v 
where v.id_stabilimento = idstabilimentogisa

loop

raise info 'id_linea_bdu %',idLineaBdu;
raise info 'id_linea_attivita gisa  %',idlineaGisa;
if idLineaBdu = 6
then
trasmettiBdu:=false;


SELECT specie_animali into specieAnimaliOpComm
FROM crosstab( 'select id_opu_rel_stab_linea, id_linee_mobili_html_fields,valore_campo
	from fg_linee_mobili_fields_value where id_opu_rel_stab_linea ='|| idlineaGisa||' order by id_rel_stab_linea,id_linee_mobili_html_fields')
as ct( linea integer,autorizzazione_si_no text, autorizzazione_note text,data_autorizzazione_ text, specie_animali text);

raise info 'specieAnimaliOp %',specieAnimaliOpComm;
if specieAnimaliOpComm ilike '%cani%' or specieAnimaliOpComm ilike '%gatti%' or specieAnimaliOpComm ilike '%furetti%'
then
trasmettiBdu:=true ;
end if;
flagOpComm:=true;
else
flagCanile:=true;
end if ; 
end loop ;


raise info 'presenti cani gatti o furetti ? %',trasmettiBdu;
raise info 'idStabBdu: %', idStabBdu;
if idStabBdu is null or idStabBdu <=0
then


raise info 'flagCanile: %', flagCanile;
if flagCanile =true or (flagOpComm=true and trasmettiBdu=true)
then
-- 1. soggetto fisico ed indirizzo rappresentante legale che ritorna id_soggetto fisico
id_soggetto_out := (select * from public_functions.insert_soggetto_fisico_bdu(idStabilimentoGisa));

--2 impresa in relazione al soggetto fisico e all'indirizzo restituisce una tupla con id operatore partita_iva, ragione sociale e cf...
id_impresa := (select id from public_functions.suap_insert_impresa_bdu(idStabilimentoGisa,id_soggetto_out));
-- 3 stabilimento restituisce l'id dello stabilimento
id_stab_out := (select * from public_functions.suap_inserisci_stabilimento_bdu(id_impresa,idStabilimentoGisa));
-- 4 linea produttiva, esegue una insert e restituisce un booleano. 5 è un canile, 6 l'operatore commerciale
id_stabl_out := (select * from public_functions.suap_insert_linea_produttiva(id_stab_out,idStabilimentoGisa));
--aggiornamento del soggetto fisico responsabile della sede produttiva
--update opu_stabilimento set id_soggetto_fisico  = id_soggetto_out where id = id_stab_out;
--aggiornamento vista materializzato tramite id_impresa
--aggiornamento informazioni canile da gisa campi estesi a canili bdu



if  flagCanile =true 
then


id_rel_stab_lp_gisa:=(
select distinct on(id_linea_bdu) id_linea_attivita 
from opu_operatori_denormalizzati_canili_opc_gisa 
where id_linea_bdu = 5 
and id_stabilimento = idStabilimentoGisa
);



raise info '(select * from public_functions.aggiorna_info_canile(%,%,%))', id_rel_stab_lp_gisa::int, id_stab_out, id_stabl_out;
esito_estesi:= (select * from public_functions.aggiorna_info_canile(id_rel_stab_lp_gisa::int, id_stab_out, id_stabl_out));
end if;

if flagOpComm =true
then 
id_rel_stab_lp_gisa:=(select distinct on(id_linea_bdu) id_linea_attivita from opu_operatori_denormalizzati_canili_opc_gisa where id_linea_bdu=6 and id_stabilimento = idStabilimentoGisa);
esito_estesi:= (select * from public_functions.aggiorna_info_operatore(id_rel_stab_lp_gisa::int, id_stab_out));
end if;


delete from opu_operatori_denormalizzati where id_opu_operatore =id_impresa;
esito := (select * from public_functions.update_opu_materializato(id_impresa));

return id_impresa;
end if;
end if;
return -1;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public_functions.suap_inserisci_canile_bdu(integer)
  OWNER TO postgres;



-- DROP FUNCTION public_functions.suap_insert_linea_produttiva(integer, integer);

CREATE OR REPLACE FUNCTION public_functions.suap_insert_linea_produttiva(
    id_stabilimento_in integer,
    idstabgisa integer)
  RETURNS integer AS
$BODY$
DECLARE
idRelStabLpBdu int;
idLineaBdu int ;
BEGIN

for idLineaBdu in 
select distinct id_linea_bdu from opu_operatori_denormalizzati_canili_opc_gisa v where v.id_stabilimento = idStabGisa 
loop
insert into opu_relazione_stabilimento_linee_produttive (id_linea_produttiva,id_stabilimento)
values(idLineaBdu,id_stabilimento_in);
end loop ;
idRelStabLpBdu := (select id from opu_relazione_stabilimento_linee_produttive rel where rel.id_linea_produttiva = idLineaBdu and rel.id_stabilimento = id_stabilimento_in);
return idRelStabLpBdu ;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.suap_insert_linea_produttiva(integer, integer)
  OWNER TO postgres;






