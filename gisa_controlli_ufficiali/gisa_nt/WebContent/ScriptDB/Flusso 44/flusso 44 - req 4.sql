
DROP FUNCTION public_functions.dbi_get_apicoltura_lista_apiari();

CREATE OR REPLACE FUNCTION public_functions.dbi_get_apicoltura_lista_apiari()
  RETURNS TABLE(id_apicoltore integer, asl_apicoltore text, ragione_sociale text, codice_azienda text, codice_fiscale_impresa text,id_apiario integer, codice_fiscale_proprietario text, nome_proprietario text, cognome_proprietario text, flag_laboratorio_annesso boolean, tipo_attivita_apicoltura text, comune_sede_legale text,
   via_sede_legale text, stato_apicoltore text, apicoltore_inviato_bdn text, 
   apicoltore_data_registrazione_bdn timestamp without time zone,
   apicoltore_data_registrazione_bdar timestamp without time zone,
   data_inizio_attivita timestamp without time zone, codice_fiscale_detentore text, nome_detentore text, cognome_detentore text, asl_apiario text, progressivo integer, ubicazione_comune text, ubicazione_via text, latitudine double precision, longitudine double precision, specie text, modalita_allevamento text, classificazione text, data_inizio_attivita_apiario timestamp without time zone, num_alveari integer, num_sciami integer, stato_apiario text, 
   apiario_inviato_bdn text, apiario_data_registrazione_bdn timestamp without time zone, data_inserimento_apiario timestamp without time zone,id_asl integer, id_utente_access_ext_delegato integer,
    codice_fiscale_delegato text) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN


		FOR 
                id_apicoltore, asl_apicoltore  ,ragione_sociale ,codice_azienda ,codice_fiscale_impresa,		id_apiario,
		codice_fiscale_proprietario ,nome_proprietario ,cognome_proprietario ,
		flag_laboratorio_annesso ,
		tipo_attivita_apicoltura ,comune_sede_legale ,via_sede_legale ,stato_apicoltore ,apicoltore_inviato_bdn  ,
		apicoltore_data_registrazione_bdn ,
                apicoltore_data_registrazione_bdar ,
		data_inizio_attivita ,
		codice_fiscale_detentore  ,nome_detentore  ,
		cognome_detentore  ,asl_apiario ,progressivo ,ubicazione_comune ,ubicazione_via ,latitudine ,longitudine ,
		specie ,modalita_allevamento , classificazione ,data_inizio_attivita_apiario ,num_alveari ,num_sciami  ,
		stato_apiario ,apiario_inviato_bdn ,
		apiario_data_registrazione_bdn ,
                data_inserimento_apiario ,
                id_asl,
                id_utente_access_ext_delegato ,
                codice_fiscale_delegato 
		
		in
		select 
		*
		from apicoltura_lista_apiari a where a.id_asl !=16 
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
ALTER FUNCTION public_functions.dbi_get_apicoltura_lista_apiari()
  OWNER TO postgres;









  -- View: apicoltura_lista_apiari

DROP VIEW apicoltura_lista_apiari;

CREATE OR REPLACE VIEW apicoltura_lista_apiari AS 
 SELECT imp.id as id_apicoltore, aslimpresa.description AS asl_apicoltore,
    imp.ragione_sociale,
    imp.codice_azienda,
    imp.codice_fiscale_impresa,
    apiari.id AS id_stabilimento,
    sogg.codice_fiscale AS codice_fiscale_proprietario,
    sogg.nome AS nome_proprietario,
    sogg.cognome AS cognome_proprietario,
        CASE
            WHEN imp.flag_laboratorio_annesso IS NOT NULL THEN imp.flag_laboratorio_annesso
            ELSE false
        END AS flag_laboratorio_annesso,
    ta.description AS tipo_attivita_apicoltura,
    c.nome AS comune_sede_legale,
    sl.via AS via_sede_legale,
        CASE
            WHEN imp.stato = 1 THEN 'DA VALIDARE'::text
            WHEN imp.stato = 2 THEN 'ATTIVO'::text
            WHEN imp.stato = 3 THEN 'CESSATO'::text
            ELSE NULL::text
        END AS stato_apicoltore,
        CASE
            WHEN imp.sincronizzato_bdn = true THEN 'SI'::text
            ELSE 'NO'::text
        END AS apicoltore_inviato_bdn,
    imp.data_sincronizzazione as apicoltore_data_registrazione_bdn ,
    imp.entered as apicoltore_data_registrazione_bdar ,
    imp.data_inizio AS data_inizio_attivita_apicoltore,
    det.codice_fiscale AS codice_fiscale_detentore,
    det.nome AS nome_detentore,
    det.cognome AS cognome_detentore,
    aslap.description AS asl_apiario,
    apiari.progressivo,
    cso.nome AS ubicazione_comune,
    indso.via AS ubicazione_via,
    indso.latitudine,
    indso.longitudine,
    specie.description AS specie,
    mod.description AS mdalita_allevamento,
    clas.description AS classificazione,
    apiari.data_inizio AS data_inizio_attivita_apiario,
    apiari.num_alveari,
    apiari.num_sciami,
        CASE
            WHEN apiari.stato = 1 THEN 'DA VALIDARE'::text
            WHEN apiari.stato = 2 THEN 'ATTIVO'::text
            WHEN apiari.stato = 3 THEN 'CESSATO'::text
            ELSE NULL::text
        END AS stato_apiario,
        CASE
            WHEN apiari.sincronizzato_bdn = true THEN 'SI'::text
            ELSE 'NO'::text
        END AS apiario_inviato_bdn,
    apiari.data_sincronizzazione as apiario_data_registrazione_bdn ,
    apiari.entered AS data_inserimento_apiario,
    imp.id_asl,
    del.id_utente_access_ext_delegato,
    del.codice_fiscale_delegato
   FROM apicoltura_imprese imp
     JOIN apicoltura_rel_impresa_soggetto_fisico relsogg ON relsogg.id_apicoltura_imprese = imp.id
     JOIN apicoltura_relazione_imprese_sede_legale relsl ON relsl.id_apicoltura_imprese = imp.id and relsl.enabled
     JOIN opu_soggetto_fisico sogg ON sogg.id = relsogg.id_soggetto_fisico
     JOIN opu_indirizzo sl ON sl.id = relsl.id_indirizzo
     JOIN apicoltura_lookup_tipo_attivita ta ON ta.code = imp.tipo_attivita_apicoltura
     JOIN comuni1 c ON c.id = sl.comune
     LEFT JOIN lookup_site_id aslimpresa ON aslimpresa.code = imp.id_asl
     JOIN apicoltura_apiari apiari ON apiari.id_operatore = imp.id
     LEFT JOIN lookup_site_id aslap ON aslap.code = apiari.id_asl
     left join apicoltura_deleghe del on imp.codice_fiscale_impresa = del.codice_fiscale_delegante and del.trashed_date is null 
     JOIN opu_soggetto_fisico det ON det.id = apiari.id_soggetto_fisico
     JOIN opu_indirizzo indso ON indso.id = apiari.id_indirizzo
     JOIN comuni1 cso ON cso.id = indso.comune
     JOIN apicoltura_lookup_sottospecie specie ON specie.code = apiari.id_apicoltura_lookup_sottospecie
     JOIN apicoltura_lookup_modalita mod ON mod.code = apiari.id_apicoltura_lookup_modalita
     JOIN apicoltura_lookup_classificazione clas ON clas.code = apiari.id_apicoltura_lookup_classificazione
  WHERE imp.trashed_date IS NULL AND imp.stato <> 4 AND apiari.trashed_date IS NULL ;

ALTER TABLE apicoltura_lista_apiari
  OWNER TO postgres;
GRANT ALL ON TABLE apicoltura_lista_apiari TO postgres;
GRANT SELECT ON TABLE apicoltura_lista_apiari TO report;
GRANT SELECT ON TABLE apicoltura_lista_apiari TO usr_ro;
