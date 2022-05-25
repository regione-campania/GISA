update opu_lookup_norme_master_list_rel_tipologia_organzation set macroarea_temp = null, aggregazione_temp = null, linea_temp = null where tipologia_organization  = 17;

select 'insert into linee_attivita_ml8_temp(org_id, id_norma, id_tipologia_operatore, codice_univoco_ml,map_completo) 
values('||org_id||',40,17,''MS.000.200'',false);',* 
from organization where tipologia  = 17;

-- Function: public.aggiorna_linea_imbarcazioni(integer, text)

-- DROP FUNCTION public.aggiorna_linea_imbarcazioni(integer, text);

CREATE OR REPLACE FUNCTION public.aggiorna_linea_imbarcazioni(
    orgid integer,
    codicelinea text)
  RETURNS text AS
$BODY$
DECLARE  
 msg text;  
BEGIN 

update linee_attivita_ml8_temp set map_completo = true, codice_univoco_ml = codicelinea where org_id = orgid;
delete from ricerche_anagrafiche_old_materializzata where riferimento_id  = orgid;
insert into ricerche_anagrafiche_old_materializzata  (select * from ricerca_anagrafiche  where riferimento_id = orgid);

msg:= 'OK';

return msg;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.aggiorna_linea_imbarcazioni(integer, text)
  OWNER TO postgres;
 

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
    oa5.latitude AS latitudine_stab,
    oa5.longitude AS longitudine_stab,
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
        END AS cap_leg,
    oa1.latitude AS latitudine_leg,
    oa1.longitude AS longitudine_leg,
    COALESCE(ml8.macroarea, norme.macroarea_temp, mltemp.macroarea) AS macroarea,
    COALESCE(ml8.aggregazione, norme.aggregazione_temp, mltemp.aggregazione) AS aggregazione,
    COALESCE(ml8.attivita, norme.linea_temp, mltemp.path_descrizione::text) AS attivita,
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
     LEFT JOIN opu_lookup_norme_master_list_rel_tipologia_organzation norme ON norme.tipologia_organization = o.tipologia AND norme.tipo_molluschi_bivalvi = o.tipologia_acque
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
     LEFT JOIN linee_attivita_ml8_temp l ON l.org_id = o.org_id
     LEFT JOIN ml8_linee_attivita_nuove_materializzata mltemp ON mltemp.codice = l.codice_univoco_ml
  WHERE --o.tipologia = 17 and 
  o.org_id <> 0 AND o.tipologia <> 0 AND o.trashed_date IS NULL AND o.import_opu IS NOT TRUE AND 
  ((o.tipologia <> ALL (ARRAY[6, 11, 211, 1, 151, 802, 152, 10, 20, 2, 800, 801])) OR o.tipologia = 3 AND o.direct_bill = true);
ALTER TABLE public.global_org_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.global_org_view TO postgres;

--select * from ricerca_anagrafiche  where tipologia_operatore = 17
delete from ricerche_anagrafiche_old_materializzata where tipologia_operatore = 17;
insert into ricerche_anagrafiche_old_materializzata  (select * from ricerca_anagrafiche  where tipologia_operatore  = 17);
  
