-- Function: digemon.dbi_get_campi_estesi_anagrafica()

-- DROP FUNCTION digemon.dbi_get_campi_estesi_anagrafica();

CREATE OR REPLACE FUNCTION digemon.dbi_get_campi_estesi_anagrafica()
  RETURNS TABLE(org_id integer, specie_macelli character varying, codifica_specie_sintesi character varying, data_ricezione_autorizzazione_ingrosso 
  timestamp without time zone, numero_autorizzazione_ingrosso text, data_ricezione_autorizzazione_dettaglio timestamp without time zone, 
  numero_autorizzazione_dettaglio text, tipo_trasporto character varying, animale_trasportato character varying, 
  descrizione_animale text, targa text, descrizione text, 
  stato_trasporto text, data_cambio_stato_trasporto timestamp without time zone, data_nascita_titolare_trasporto text, 
  luogo_nascita_titolare_trasporto character varying, email_titolare_trasporto character varying,telefono_titolare_trasporto character varying,
  fax_titolare_trasporto character varying, data_presentazione_richiesta_trasporto text,lavaggio_autorizzato_trasporto text, classe character varying, tipologia_acque integer, 
  codice_classe integer, dataprovvedimento timestamp without time zone, provvedimento text, data_classificazione timestamp without time zone, data_fine_classificazione timestamp without time zone, tipo_produzione text, 
  numero_decreto text, taglia_non_commerciale text, cun text, coordinate_poligono text, coordinate_punto_prelievo text, provvedimento_restrittivo character varying,
ente_gestore character varying, codice_punto_prelievo character varying, tipologia_punto_prelievo character varying, tipo_struttura character varying, codice_l30 character varying, razza character varying, provvedimento_autorizzazione character varying, scadenza_autorizzazione character varying, sede character varying, descrizione_punto_sbarco character varying, ignoto text, cognome_abusivo character varying, nome_abusivo character varying, luogo_di_nascita_abusivo character varying, comune_residenza_abusivo character varying, via_residenza_abusivo character varying, data_controllo_abusivo text, stato_abusivo text) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
     FOR org_id, specie_macelli, codifica_specie_sintesi, data_ricezione_autorizzazione_ingrosso, numero_autorizzazione_ingrosso, data_ricezione_autorizzazione_dettaglio,
     numero_autorizzazione_dettaglio, tipo_trasporto, animale_trasportato, descrizione_animale, targa, descrizione, stato_trasporto, data_cambio_stato_trasporto, data_nascita_titolare_trasporto, 
     luogo_nascita_titolare_trasporto, email_titolare_trasporto, telefono_titolare_trasporto, fax_titolare_trasporto, data_presentazione_richiesta_trasporto, lavaggio_autorizzato_trasporto,
     classe, tipologia_acque, codice_classe, dataprovvedimento, provvedimento, data_classificazione, data_fine_classificazione, tipo_produzione, numero_decreto, 
     -- new per molluschi
     taglia_non_commerciale, cun , coordinate_poligono , coordinate_punto_prelievo , provvedimento_restrittivo,
     ente_gestore, codice_punto_prelievo, tipologia_punto_prelievo, tipo_struttura, codice_l30, razza, provvedimento_autorizzazione, scadenza_autorizzazione, 
     sede, descrizione_punto_sbarco, ignoto, cognome_abusivo, nome_abusivo, luogo_di_nascita_abusivo, comune_residenza_abusivo, via_residenza_abusivo, 
     data_controllo_abusivo, stato_abusivo
  in
 select
o.org_id, 
l.description as specie_macelli, l.short_description as codifica_specie_sintesi,
o.data_ric_ingrosso AS data_ricezione_autorizzazione_ingrosso,
o.num_ric_ingrosso AS numero_autorizzazione_ingrosso,
o.data_ric_dettaglio AS data_ricezione_autorizzazzione_dettaglio,
o.num_ric_dettaglio AS numero_autorizzazzione_dettaglio,
o.duns_type AS tipo_trasporto,
lst.description AS animale_trasportato,
o.codice10 as descrizione_animale,
auto.targa,
auto.descrizione,
o.stato_impresa as stato_trasporto,
o.data_cambio_stato as data_cambio_stato_trasporto,
to_char(o.data_nascita_rappresentante, 'dd/mm/yyyy') as data_nascita_titolare_trasporto, 
o.luogo_nascita_rappresentante as luogo_nascita_titolare_trasporto, 
o.email_rappresentante as email_titolare_trasporto,
o.telefono_rappresentante as telefono_titolare_trasporto,
o.fax as fax_titolare_trasporto,
to_char(o.date1, 'dd/mm/yyyy') as data_presentazione_richiesta_trasporto,
concat_ws(',',oa7.addrline1,oa7.city,oa7.state,oa7.postalcode,oa7.latitude,oa7.longitude) as lavaggio_autorizzato_trasporto,
lc.description AS classe,
o.tipologia_acque,
lc.code AS codice_classe,
o.date2 AS dataprovvedimento,
o.note1 AS provvedimento,
o.data_classificazione,
o.data_fine_classificazione,
lz.description AS tipo_zona_produzione,
o.numaut AS numero_decreto,
case when o.taglia_non_commerciale then 'SI' else 'NO' end as taglia_non_commerciale,
o.cun, 
'LAT: ' || cc.latitude || '; LON: ' || cc.longitude as coordinate_poligono,
'LAT: ' || cc2.latitude || '; LON: ' || cc2.longitude as coordinate_punto_prelievo,
lca.description as provvedimento_restrittivo,
o.banca as ente_gestore, o.account_number as codice_punto_prelievo, 
lta.description as tipologia_punto_prelievo,
CASE
    	WHEN c.monta_equina_attive IS NOT NULL AND c.monta_equina_attive THEN 'STAZIONE DI MONTA NATURALE EQUINA'
    	WHEN c.monta_bovina_attive IS NOT NULL AND c.monta_bovina_attive THEN 'STAZIONE DI MONTA NATURALE BOVINA'
	WHEN c.stazione_inseminazione_equine IS NOT NULL AND c.stazione_inseminazione_equine THEN 'STAZIONE DI INSEMINAZIONE ARTIFICIALE EQUINA'
	WHEN c.centro_produzione_embrioni IS NOT NULL AND c.centro_produzione_embrioni THEN 'CENTRO DI PRODUZIONE EMBRIONI'
	WHEN c.centro_produzione_sperma IS NOT NULL AND c.centro_produzione_sperma THEN 'CENTRO DI PRODUZIONE SPERMA'
	WHEN c.gruppo_raccolta_embrioni IS NOT NULL AND c.gruppo_raccolta_embrioni THEN 'GRUPPO DI RACCOLTA EMBRIONI'
	WHEN c.recapiti_autorizzati IS NOT NULL AND c.recapiti_autorizzati THEN 'RECAPITI ATTIVI'
	ELSE null
END AS tipo_struttura,
c.codice_legge_30 as codice_l30,
c.razza,
c.provv_aut as provvedimento_autorizzazione,
c.scadenza_aut as scadenza_autorizzazione,
c.sede,
o.alert as descrizione_punto_sbarco,
case when o.cessato = 0 THEN 'NO' else 'SI' end as ignoto,
o.name as cognome_abusivo, o.banca as nome_abusivo, o.categoria as luogo_di_nascita_abusivo,
o.namefirst as comune_residenza_abusivo, 
o.namelast as via_residenza_abusivo, to_char(o.date2, 'dd/MM/yy') as data_controllo_abusivo,
case when o.stato_lab = 4 then 'CESSATO IN DATA ' || to_char(o.contract_end, 'dd/MM/yy') else '' end as stato_abusivo
from  
organization o
LEFT JOIN organization_address oa7 on (o.org_id = oa7.org_id and oa7.address_type = 7) 
LEFT JOIN organization_address oa on (o.org_id = oa.org_id and oa.address_type = 5 and oa.trasheddate is null)
LEFT JOIN organization_address oa2 on (o.org_id = oa2.org_id and oa2.address_type = 5 and oa2.trasheddate is null)
LEFT JOIN coordinate_zone_produzione cc2 on cc2.address_id = oa2.address_id and cc2.tipologia = 2
LEFT join coordinate_zone_produzione cc on cc.address_id = oa.address_id and cc.tipologia = 1
LEFT JOIN lookup_classi_acque lc ON lc.code = o.classificazione
LEFT JOIN animalitrasportati at ON at.org_id = o.org_id
LEFT JOIN lookup_specie_trasportata lst ON lst.code = at.tipo_animale
LEFT JOIN organization_autoveicoli auto ON o.org_id = auto.org_id AND auto.elimina IS NULL
left join macelli_specie m on m.org_id = o.org_id
left join lookup_specie_macelli l on l.code = m.id_specie_macelli and l.enabled 
left join lookup_tipo_acque lta on lta.code = o.tipo_struttura
left join lookup_zone_di_produzione lz on lz.code=o.tipologia_acque
left join molluschi_motivi m2 on m2.org_id=o.org_id and m2.enabled
left join lookup_classi_acque lca on lca.code = m2.motivo_id 
left join centri_riproduzione_animale c  ON o.org_id = c.org_id
where o.trashed_date is null and o.tipologia in (8,9,802,14,3,151,201,5,4)
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_campi_estesi_anagrafica()
  OWNER TO postgres;

--select * from digemon.dbi_get_campi_estesi_anagrafica() where org_id=1044111