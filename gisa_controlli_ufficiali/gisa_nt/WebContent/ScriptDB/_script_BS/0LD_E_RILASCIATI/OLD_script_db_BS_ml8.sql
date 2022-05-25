CREATE TABLE public.master_list_macroarea
(
  id SERIAL,
  codice_sezione text,
  codice_norma text,
  norma text,
  macroarea text,
  CONSTRAINT master_list_macroarea_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.master_list_macroarea
  OWNER TO postgres;
GRANT ALL ON TABLE public.master_list_macroarea TO postgres;
GRANT SELECT ON TABLE public.master_list_macroarea TO report;



CREATE TABLE public.master_list_aggregazione
(
  id SERIAL,
  id_macroarea integer,
  codice_attivita text,
  aggregazione text,
  id_flusso_originale integer,
  CONSTRAINT master_list_aggregazione_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.master_list_aggregazione
  OWNER TO postgres;
GRANT ALL ON TABLE public.master_list_aggregazione TO postgres;
GRANT SELECT ON TABLE public.master_list_aggregazione TO report;





CREATE TABLE public.master_list_linea_attivita
(
  id serial,
  id_aggregazione integer,
  codice_prodotto_specie text,
  linea_attivita text,
  tipo text,
  scheda_supplementare text,
  note text,
  mapping_ateco text,
  codice_univoco text,
  codice_nazionale_richiesto text,
  chi_inserisce_pratica text,
  chi_valida text,
  ateco1 text,
  ateco2 text,
  ateco3 text,
  ateco4 text,
  ateco5 text,
  ateco6 text,
  ateco7 text,
  ateco8 text,
  ateco9 text,
  ateco10 text,
  ateco11 text,
  ateco12 text,
  ateco13 text,
  ateco14 text,
  ateco15 text,
  ateco16 text,
  ateco17 text,
  ateco18 text,
  note_hd text,
  enabled boolean default true,
  id_lookup_tipo_attivita integer,
  CONSTRAINT master_list_linea_attivita_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.master_list_linea_attivita
  OWNER TO postgres;
GRANT ALL ON TABLE public.master_list_linea_attivita TO postgres;
GRANT SELECT ON TABLE public.master_list_linea_attivita TO report;





CREATE TABLE public.master_list_allegati_procedure_relazione
(
  id SERIAL,
  id_master_list_linea_attivita integer,
  id_master_list_suap_gruppo_allegati integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.master_list_allegati_procedure_relazione
  OWNER TO postgres;
GRANT ALL ON TABLE public.master_list_allegati_procedure_relazione TO postgres;
GRANT SELECT ON TABLE public.master_list_allegati_procedure_relazione TO report;





CREATE TABLE public.master_list_livelli_aggiuntivi
(
  id SERIAL,
  id_linea_attivita integer,
  id_padre integer,
  nome text,
  enabled boolean DEFAULT true,
  CONSTRAINT master_list_livelli_aggiuntivi_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.master_list_livelli_aggiuntivi
  OWNER TO postgres;
GRANT ALL ON TABLE public.master_list_livelli_aggiuntivi TO postgres;
GRANT SELECT ON TABLE public.master_list_livelli_aggiuntivi TO report;


CREATE TABLE public.master_list_livelli_aggiuntivi_values
(
  id SERIAL,
  id_livello_aggiuntivo integer,
  valore text,
  enabled boolean DEFAULT true,
  CONSTRAINT master_list_livelli_aggiuntivi_values_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.master_list_livelli_aggiuntivi_values
  OWNER TO postgres;
GRANT ALL ON TABLE public.master_list_livelli_aggiuntivi_values TO postgres;
GRANT SELECT ON TABLE public.master_list_livelli_aggiuntivi_values TO report;




CREATE TABLE public.master_list_sk_elenco
(
  id SERIAL,
  id_scheda integer,
  tit_scheda text,
  nome text,
  testo_aggiuntivo boolean DEFAULT false,
  insert_date timestamp without time zone DEFAULT now(),
  disabled_date timestamp without time zone,
  CONSTRAINT master_list_sk_elenco_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.master_list_sk_elenco
  OWNER TO postgres;
GRANT ALL ON TABLE public.master_list_sk_elenco TO postgres;
GRANT SELECT ON TABLE public.master_list_sk_elenco TO report;

insert into master_list_allegati_procedure_relazione select * from suap_master_list_6_allegati_procedure_relazione;
insert into master_list_sk_elenco select * from suap_master_list_6_sk_elenco;


-- Table: public.ml_partizioni

-- DROP TABLE public.ml_partizioni;

CREATE TABLE public.ml_partizioni
(
  code SERIAL,
  description text,
  nome_campo text,
  nome_tabella text,
  valore_start integer NOT NULL,
  valore_end integer NOT NULL,
  CONSTRAINT ml_partizioni_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.ml_partizioni
  OWNER TO postgres;
GRANT ALL ON TABLE public.ml_partizioni TO postgres;
GRANT SELECT ON TABLE public.ml_partizioni TO report;

insert into ml_partizioni (code, description, nome_campo, nome_tabella, valore_start, valore_end) 
values (1, 'Id Macroarea', 'id', 'master_list_macroarea', 1, 19999);
insert into ml_partizioni (code, description, nome_campo, nome_tabella, valore_start, valore_end) 
values (2, 'Id Aggregazione', 'id', 'master_list_aggregazione', 20000, 39999);
insert into ml_partizioni (code, description, nome_campo, nome_tabella, valore_start, valore_end) 
values (3, 'Id Linea Attivita', 'id', 'master_list_linea_attivita', 40000, 59999);


CREATE OR REPLACE FUNCTION public.gestione_ml_id
(IN idinput integer,IN tipoinput integer)
  RETURNS TABLE(return_id integer, return_ml_id integer, return_code integer, return_nome_campo text, return_nome_tabella text) AS
$BODY$
DECLARE
	r RECORD;	
BEGIN
	FOR return_id, return_ml_id, return_code,  return_nome_campo, return_nome_tabella
	in
select 
case when tipoInput=-1 then (idInput - valore_start) 
else -1 end as id, 
case when tipoInput>-1 then (idInput + valore_start) 
else -1 end as alt_id, code, nome_campo, nome_tabella
from ml_partizioni where 1=1
and ((tipoInput=-1 and valore_start <= idInput and valore_end >= idInput)
or tipoInput>-1)
and ((tipoInput>-1 and code = tipoInput)
or tipoInput=-1)

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.gestione_ml_id(integer, integer)
  OWNER TO postgres;


  
  
  CREATE OR REPLACE FUNCTION public.ml_inserisci_id_macroarea()
  RETURNS trigger AS
$BODY$
   DECLARE
   mlId integer;
   
   BEGIN

   mlId = (select return_ml_id from  gestione_ml_id(NEW.id, 1));
   NEW.id := mlId;

    RETURN NEW;
   END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION  public.ml_inserisci_id_macroarea()
  OWNER TO postgres;

  CREATE TRIGGER ml_macroarea_id
  BEFORE INSERT
  ON public.master_list_macroarea
  FOR EACH ROW
  EXECUTE PROCEDURE public.ml_inserisci_id_macroarea();
  
  
  
  CREATE OR REPLACE FUNCTION public.ml_inserisci_id_aggregazione()
  RETURNS trigger AS
$BODY$
   DECLARE
   mlId integer;
   
   BEGIN

   mlId = (select return_ml_id from  gestione_ml_id(NEW.id, 2));
   NEW.id := mlId;

    RETURN NEW;
   END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION  public.ml_inserisci_id_aggregazione()
  OWNER TO postgres;

  CREATE TRIGGER ml_aggregazione_id
  BEFORE INSERT
  ON public.master_list_aggregazione
  FOR EACH ROW
  EXECUTE PROCEDURE public.ml_inserisci_id_aggregazione();
  
  
  CREATE OR REPLACE FUNCTION public.ml_inserisci_id_linea_attivita()
  RETURNS trigger AS
$BODY$
   DECLARE
   mlId integer;
   
   BEGIN

   mlId = (select return_ml_id from  gestione_ml_id(NEW.id, 3));
   NEW.id := mlId;

    RETURN NEW;
   END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION  public.ml_inserisci_id_linea_attivita()
  OWNER TO postgres;

  CREATE TRIGGER ml_linea_attivita_id
  BEFORE INSERT
  ON public.master_list_linea_attivita
  FOR EACH ROW
  EXECUTE PROCEDURE public.ml_inserisci_id_linea_attivita();


  
  
  -- import
  
 
create table mapping_ml6_ml8 (ml6_linea_id integer, ml6_codice_univoco text, ml8_linea_id integer, ml8_codice_univoco text);

insert into mapping_ml6_ml8 
select ml6.id, ml6.codice_univoco, ml8.id, ml8.codice_univoco
 from suap_master_list_6_linea_attivita ml6
 left join  master_list_linea_attivita ml8 on ml6.codice_univoco ilike ml8.codice_univoco


 update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-1-STORP_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-1-STORP_T') where ml6_codice_univoco = '1069-R-1-STORP';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-2-STORP_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-2-STORP_T') where ml6_codice_univoco = '1069-R-2-STORP';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-3-STORP_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-3-STORP_T') where ml6_codice_univoco = '1069-R-3-STORP';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-4-STORP_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-4-STORP_T') where ml6_codice_univoco = '1069-R-4-';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-5-STORP_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-5-STORP_T') where ml6_codice_univoco = '1069-R-5-';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-6-STORP_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-6-STORP_T') where ml6_codice_univoco = '1069-R-6-';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-28-OTHER_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-28-OTHER_T') where ml6_codice_univoco = '1069-R-28-OTHER';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-29-OTHER_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-29-OTHER_T') where ml6_codice_univoco = '1069-R-29-OTHER';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-30-OTHER_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-30-OTHER_T') where ml6_codice_univoco = '1069-R-30-OTHER';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-31-OTHER_U', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-31-OTHER_U') where ml6_codice_univoco = '1069-R-31-OTHER';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-32-OTHER_U', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-32-OTHER_U') where ml6_codice_univoco = '1069-R-32-OTHER';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-33-OTHER_U', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-33-OTHER_U') where ml6_codice_univoco = '1069-R-33-OTHER';
update mapping_ml6_ml8 set ml8_codice_univoco = 'MS.A-MS.A30.500-852ITBA', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = 'MS.A-MS.A30.500-852ITBA') where ml6_codice_univoco = 'MS.A-MS.A30.500-852ITBA005';
update mapping_ml6_ml8 set ml8_codice_univoco = 'MS.A-MS.A30.500-852ITBA', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = 'MS.A-MS.A30.500-852ITBA') where ml6_codice_univoco = 'MS.A-MS.A30.500-852ITBA004';
update mapping_ml6_ml8 set ml8_codice_univoco = 'MS.A-MS.A30.500-852ITBA', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = 'MS.A-MS.A30.500-852ITBA') where ml6_codice_univoco = 'MS.A-MS.A30.500-852ITBA003';



  select ml6_linea_id, 'update sintesis_relazione_stabilimento_linee_produttive set id_linea_produttiva = ' || ml8_linea_id || ', note=''Linea modificata ml6->ml8'', id_vecchia_linea =id_linea_produttiva where id_linea_produttiva = ' || ml6_linea_id || ';' from mapping_ml6_ml8; 
  
  update campi_estesi_templates_v2  set id_linea = 42519 where id_linea = 394;
update campi_estesi_templates_v2  set id_linea = 42551 where id_linea = 426;

  
  -- aggiornamenti sintesis
  
  
  -- View: public.sintesis_operatori_denormalizzati_view

-- DROP VIEW public.sintesis_operatori_denormalizzati_view;

CREATE OR REPLACE VIEW public.sintesis_operatori_denormalizzati_view AS 
 SELECT DISTINCT false AS flag_dia,
    o.id AS id_opu_operatore,
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
    o.flag_ric_ce,
    o.num_ric_ce,
    i.comune,
    stab.id_asl,
    stab.id AS id_stabilimento,
    stab.esito_import,
    stab.data_import,
    stab.cun,
    stab.id_sinvsa,
    stab.descrizione_errore,
    comuni1.nome AS comune_stab,
    comuni1.istat AS istat_operativo,
    ((
        CASE
            WHEN topsedestab.description IS NOT NULL THEN topsedestab.description
            ELSE ''::character varying
        END::text || ' '::text) || sedestab.via::text) || ' '::text AS indirizzo_stab,
    sedestab.cap AS cap_stab,
    provsedestab.description AS prov_stab,
    stab.data_fine_dia,
    stab.categoria_rischio,
    soggsl.codice_fiscale AS cf_rapp_sede_legale,
    soggsl.nome AS nome_rapp_sede_legale,
    soggsl.cognome AS cognome_rapp_sede_legale,
    stab.numero_registrazione AS codice_registrazione,
    n.description AS norma,
    lmac.codice_norma,
    stab.approval_number,
    n.code AS id_norma,
    ''::text AS cf_correntista,
    ''::text AS codice_attivita,
    true AS primario,
    (((lmac.macroarea || '->'::text) || lagg.aggregazione) || '->'::text) || latt.linea_attivita AS attivita,
    lps.data_inizio,
    lps.data_fine,
    stab.numero_registrazione,
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
    latt.linea_attivita AS solo_attivita,
    stab.data_inizio_attivita,
    stab.data_fine_attivita,
    stab.data_prossimo_controllo,
    stab.stato AS id_stato,
    (((lmac.macroarea || '->'::text) || lagg.aggregazione) || '->'::text) || latt.linea_attivita AS path_attivita_completo,
    stab.id_indirizzo,
    stab.linee_pregresse,
    true AS flag_nuova_gestione,
    loti.description AS tipo_impresa,
    lotis.description AS tipo_societa,
    stab.codice_ufficiale_esistente,
    stab.id_asl AS id_asl_stab,
    lps.id AS id_linea_attivita,
    lps.modified AS data_mod_attivita,
    stab.entered AS stab_entered,
    lps.numero_registrazione AS linea_numero_registrazione,
    lps.stato AS linea_stato,
    statilinea.description AS linea_stato_text,
    lps.codice_ufficiale_esistente AS linea_codice_ufficiale_esistente,
    stab.codice_ufficiale_esistente AS stab_codice_ufficiale_esistente,
    sedeop.id AS id_indirizzo_operatore,
    stab.import_opu,
    lps.id_linea_produttiva AS id_linea_attivita_stab,
    o.note AS note_operatore,
    stab.note AS note_stabilimento,
    lps.codice_nazionale AS linea_codice_nazionale,
    false AS flag_pnaa,
    (con.namefirst::text || ' '::text) || con.namelast::text AS stab_inserito_da,
    stab.tipo_attivita AS stab_id_attivita,
    lsa.description AS stab_descrizione_attivita,
    stab.tipo_carattere AS stab_id_carattere,
    lsc.description AS stab_descrizione_carattere,
    o.tipo_impresa AS impresa_id_tipo_impresa,
    asl.description AS stab_asl,
    o.flag_clean,
    stab.data_generazione_numero,
    sedestab.latitudine AS lat_stab,
    sedestab.longitudine AS long_stab,
    lps.entered AS linea_entered,
    lps.modified AS linea_modified,
    lmac.macroarea,
    lagg.aggregazione,
    latt.linea_attivita AS attivita_xml,
    comuni1.codiceistatasl_old,
    provsedestab.cod_provincia AS sigla_prov_operativa,
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
    sedestab.civico AS civico_sede_stab,
    sedestab.toponimo AS tiponimo_sede_stab,
    topsedestab.description AS toponimo_sede_stab,
    soggind.via AS via_rapp_sede_legale,
    sedeop.via AS via_sede_legale,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN comunisoggind.id
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN comunisl.id
            ELSE NULL::integer
        END AS id_comune_richiesta,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN comunisoggind.nome
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN comunisl.nome
            ELSE NULL::character varying
        END AS comune_richiesta,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.via::text)::character varying
            ELSE NULL::character varying
        END AS via_stabilimento_calcolata,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.civico)::character varying
            ELSE NULL::character varying
        END AS civico_stabilimento_calcolato,
    soggind.cap AS cap_residenza,
    soggind.nazione AS nazione_residenza,
    sedeop.nazione AS nazione_sede_legale,
    sedestab.nazione AS nazione_stab,
    '-1'::integer AS id_lookup_tipo_linee_mobili,
    '-1'::integer AS id_tipo_linea_produttiva,
    rels.id_soggetto_fisico,
    lps.pregresso_o_import,
    stab.riferimento_org_id,
    stab.id_controllo_ultima_categorizzazione,
    stab.alt_id
   FROM sintesis_operatore o
     LEFT JOIN sintesis_rel_operatore_soggetto_fisico rels ON rels.id_operatore = o.id AND rels.enabled
     LEFT JOIN sintesis_soggetto_fisico soggsl ON soggsl.id = rels.id_soggetto_fisico
     LEFT JOIN sintesis_indirizzo soggind ON soggind.id = soggsl.indirizzo_id
     LEFT JOIN comuni1 comunisoggind ON comunisoggind.id = soggind.comune
     LEFT JOIN sintesis_indirizzo sedeop ON sedeop.id = o.id_indirizzo
     LEFT JOIN comuni1 comunisl ON sedeop.comune = comunisl.id
     JOIN sintesis_stabilimento stab ON stab.id_operatore = o.id
     JOIN sintesis_relazione_stabilimento_linee_produttive lps ON lps.id_stabilimento = stab.id AND lps.enabled
     LEFT JOIN lookup_stato_stabilimento_sintesis stati ON stati.code = stab.stato
     JOIN master_list_linea_attivita latt ON latt.id = lps.id_linea_produttiva
     JOIN master_list_aggregazione lagg ON lagg.id = latt.id_aggregazione
     JOIN master_list_macroarea lmac ON lmac.id = lagg.id_macroarea
     JOIN sintesis_indirizzo sedestab ON sedestab.id = stab.id_indirizzo
     LEFT JOIN comuni1 ON sedestab.comune = comuni1.id
     LEFT JOIN sintesis_indirizzo i ON i.id = stab.id_indirizzo
     LEFT JOIN lookup_province provsedestab ON provsedestab.code::text = sedestab.provincia::text
     LEFT JOIN lookup_province provsedeop ON provsedeop.code::text = sedeop.provincia::text
     LEFT JOIN lookup_province provsoggind ON provsoggind.code::text = soggind.provincia::text
     LEFT JOIN lookup_toponimi topsedeop ON sedeop.toponimo = topsedeop.code AND topsedeop.enabled
     LEFT JOIN lookup_toponimi topsedestab ON sedestab.toponimo = topsedestab.code AND topsedestab.enabled
     LEFT JOIN lookup_toponimi topsoggind ON soggind.toponimo = topsoggind.code AND topsoggind.enabled
     LEFT JOIN lookup_opu_tipo_impresa loti ON loti.code = o.tipo_impresa
     LEFT JOIN lookup_opu_tipo_impresa_societa lotis ON lotis.code = o.tipo_societa
     LEFT JOIN lookup_stato_attivita_sintesis statilinea ON statilinea.code = lps.stato
     LEFT JOIN access acc ON acc.user_id = stab.entered_by
     LEFT JOIN contact con ON con.contact_id = acc.contact_id
     LEFT JOIN opu_lookup_tipologia_attivita lsa ON lsa.code = 1
     LEFT JOIN opu_lookup_tipologia_carattere lsc ON lsc.code = 1
     LEFT JOIN lookup_site_id asl ON asl.code = stab.id_asl
     LEFT JOIN opu_lookup_norme_master_list n ON n.codice_norma = lmac.codice_norma
  WHERE o.trashed_date IS NULL AND stab.trashed_date IS NULL AND stab.trashed_date IS NULL;

ALTER TABLE public.sintesis_operatori_denormalizzati_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.sintesis_operatori_denormalizzati_view TO postgres;
GRANT SELECT ON TABLE public.sintesis_operatori_denormalizzati_view TO usr_ro;
GRANT SELECT ON TABLE public.sintesis_operatori_denormalizzati_view TO report;


-- View: public.sintesis_linee_attivita_stabilimenti_view

-- DROP VIEW public.sintesis_linee_attivita_stabilimenti_view;

CREATE OR REPLACE VIEW public.sintesis_linee_attivita_stabilimenti_view AS 
 SELECT stab.alt_id,
    v1.id_linea_produttiva AS id_rel_ateco_attivita,
    v1.primario,
    v3.aggregazione AS categoria,
    v2.linea_attivita,
    ''::text AS codice_istat,
    v1.id,
    v1.enabled,
    v4.macroarea
   FROM sintesis_relazione_stabilimento_linee_produttive v1
     JOIN master_list_linea_attivita v2 ON v1.id_linea_produttiva = v2.id
     JOIN master_list_aggregazione v3 ON v2.id_aggregazione = v3.id
     JOIN master_list_macroarea v4 ON v3.id_macroarea = v4.id
     LEFT JOIN sintesis_stabilimento stab ON stab.id = v1.id_stabilimento;

ALTER TABLE public.sintesis_linee_attivita_stabilimenti_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.sintesis_linee_attivita_stabilimenti_view TO postgres;
GRANT SELECT ON TABLE public.sintesis_linee_attivita_stabilimenti_view TO report;


-- Function: public.mapping_linea_attivita(integer, text, text)

-- DROP FUNCTION public.mapping_linea_attivita(integer, text, text);

CREATE OR REPLACE FUNCTION public.mapping_linea_attivita(
    idflusso integer,
    attivita text,
    aggregazione text)
  RETURNS integer AS
$BODY$
DECLARE
inaggregazione text;
inattivita text;
id_linea_attivita integer;
BEGIN

inaggregazione = regexp_replace(aggregazione, '[^a-zA-Z0-9]', '', 'g');
inattivita = regexp_replace(attivita, '[^a-zA-Z0-9]', '', 'g');
 raise info 'aggregazione: % ', inaggregazione;
 raise info 'attivita: % ', inattivita;
id_linea_attivita := (select l.id from master_list_aggregazione a
join master_list_linea_attivita l on l.id_aggregazione = a.id
where a.id_flusso_originale = idflusso and regexp_replace(l.linea_attivita, '[^a-zA-Z0-9]', '', 'g') ilike inattivita and regexp_replace(a.aggregazione, '[^a-zA-Z0-9]', '', 'g') ilike inaggregazione);

return id_linea_attivita;


 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.mapping_linea_attivita(integer, text, text)
  OWNER TO postgres;


  
  
    drop table suap_master_list_6_aggregazione ;
  drop table suap_master_list_6_campi_estesi ;
  drop table suap_master_list_6_campi_estesi_values ;
  drop table suap_master_list_6_linea_attivita ;
  drop table suap_master_list_6_macroarea ;
  drop table suap_master_list_6_sk_elenco ;
  drop table suap_master_list_6_allegati_procedure_relazione ;
  
  
  
  
  
  
  -- passaggio
  
  
update opu_lookup_norme_master_list set enabled = false;

insert into opu_lookup_norme_master_list (description, level, codice_norma) 
select distinct norma, 99,codice_norma from master_list_macroarea ;


alter table master_list_macroarea add column id_norma integer;
select distinct 'update master_list_macroarea set id_norma = ' || n.code ||' where codice_norma = ''' || m.codice_norma || ''' ;'
from master_list_macroarea m join opu_lookup_norme_master_list n on n.codice_norma ilike m.codice_norma


  
--  create table ml_8_configura_livelli_attivita_produttiva as select * from opu_configura_livelli_attivita_produttiva_suap;

CREATE TABLE ml8_master_list
(
  id SERIAL ,
  descrizione text,
  livello integer,
  id_padre integer,
  codice text,
  versione text,
  id_norma integer,
  enabled boolean DEFAULT true,
  id_lookup_tipo_attivita integer,
  note_hd text,
  flag_pnaa boolean,
  id_flusso_origine integer,
  id_lookup_tipo_linee_mobili integer,
  decodifica_tipo_produzione_bdn text,
  decodifica_codice_orientamento_bdn text,
  decodifica_specie_bdn text,
  flag_b_e_b boolean,
  flag_bdu boolean,
    CONSTRAINT ml8_master_list_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public. ml8_master_list
  OWNER TO postgres;
GRANT ALL ON TABLE public.ml8_master_list TO postgres;
GRANT SELECT ON TABLE public.ml8_master_list TO usr_ro;
GRANT SELECT ON TABLE public.ml8_master_list TO report;



insert into ml8_master_list(id, descrizione, livello, id_padre, codice, id_norma, versione) (
select id, macroarea as descrizione, 1 as livello, -1 as id_padre, codice_sezione as codice, id_norma, '8' as versione from master_list_macroarea
UNION
select id, aggregazione as descrizione, 2 as livello, id_macroarea as id_padre, codice_attivita as codice, -1 as id_norma, '8' as versione from master_list_aggregazione
UNION
select id, linea_attivita as descrizione, 3 as livello, id_aggregazione as id_padre, codice_univoco as codice, -1 as id_norma, '8' as versione from master_list_linea_attivita
)



select distinct codice_attivita, id_flusso_originale,
'update ml8_master_list set id_flusso_origine = ' || id_flusso_originale || ' where livello = 2 and codice = ''' || codice_attivita || ''';'
 from master_list_aggregazione;

 
 select
'update ml8_master_list set id_flusso_origine = ' || id_flusso_origine || ' where livello = 3 and id_padre = ' || id || ';', 
id, id_flusso_origine from ml8_master_list where livello = 2

select
'update ml8_master_list set id_flusso_origine = ' || id_flusso_origine || ' where livello = 1 and id = ' || id_padre || ';', 
id_padre, id_flusso_origine from ml8_master_list where livello = 2


insert into lookup_flusso_originale_ml (code, description, level) values (6, 'RICONOSCIUTI', 6);
insert into lookup_flusso_originale_ml (code, description, level) values (7, 'SOA REGISTRATI', 7);

alter table lookup_flusso_originale_ml add column codice_ufficiale boolean default false;
update lookup_flusso_originale_ml set codice_ufficiale = true where code in (1,3,2,6,7)

--update ml_8_configura_livelli_attivita_produttiva set nome_tabella ='ml8_master_list m join  ml8_master_list_tipo_attivita conf on conf.id_master_list=m.id';


--create table ml8_master_list_tipo_attivita as select * from master_list_suap_tipo_attivita;
--alter table ml8_master_list_tipo_attivita rename column id_master_list_suap to id_master_list;

  
  
  
select distinct codice_univoco, id_lookup_tipo_attivita,
'update ml8_master_list set id_lookup_tipo_attivita = ' || id_lookup_tipo_attivita || ' where livello = 3 and codice = ''' || codice_univoco || ''';'
 from master_list_linea_attivita 

 
select m.id_norma, a.id, l.id, 'update ml8_master_list set id_norma = ' || m.id_norma || ' where id = '||a.id||'; update ml8_master_list set id_norma = ' || m.id_norma ||' where id = ' || l.id || ';' 
from master_list_macroarea m join master_list_aggregazione a on a.id_macroarea = m.id join master_list_linea_attivita l on l.id_aggregazione = a.id

 create table ml8_linee_attivita_nuove_materializzata as select * from ml8_linee_attivita_nuove

 
 
-- View: public.ml8_linee_attivita_nuove

-- DROP VIEW public.ml8_linee_attivita_nuove;

CREATE OR REPLACE VIEW public.ml8_linee_attivita_nuove AS 
 WITH RECURSIVE recursetree(id, flag_pnaa, nonno, descrizione, livello, id_padre, path_id, path_descrizione, codice, path_codice, id_norma,   id_lookup_configurazione_validazione, id_lookup_tipo_attivita, id_lookup_tipo_linee_mobili, decodifica_tipo_produzione_bdn, decodifica_codice_orientamento_bdn, decodifica_specie_bdn) AS (
         SELECT ml8_master_list.id,
            ml8_master_list.flag_pnaa,
            ml8_master_list.id_padre AS nonno,
            ml8_master_list.descrizione,
            ml8_master_list.livello,
            ml8_master_list.id_padre,
            ml8_master_list.id_padre::character varying(1000) AS path_id,
            ml8_master_list.descrizione::character varying(1000) AS path_desc,
            ml8_master_list.codice,
            ml8_master_list.codice::character varying(1000) AS path_codice,
            ml8_master_list.id_norma,
            ml8_master_list.id_flusso_origine,
            ml8_master_list.id_lookup_tipo_attivita,
            ml8_master_list.id_lookup_tipo_linee_mobili,
            ml8_master_list.decodifica_tipo_produzione_bdn,
            ml8_master_list.decodifica_codice_orientamento_bdn,
            ml8_master_list.decodifica_specie_bdn
           FROM ml8_master_list
          WHERE ml8_master_list.id_padre = '-1'::integer
        UNION ALL
         SELECT t.id,
            t.flag_pnaa,
            t.id_padre AS nonno,
            t.descrizione,
            t.livello,
            t.id_padre,
            (((rt.path_id::text || ';'::text) || t.id_padre))::character varying(1000) AS "varchar",
            (((rt.path_descrizione::text || '->'::text) || t.descrizione))::character varying(1000) AS path_desc,
            t.codice,
            (((rt.path_codice::text || '->'::text) || t.codice))::character varying(1000) AS path_codice,
            t.id_norma,
            t.id_flusso_origine,
            t.id_lookup_tipo_attivita,
            t.id_lookup_tipo_linee_mobili,
            t.decodifica_tipo_produzione_bdn,
            t.decodifica_codice_orientamento_bdn,
            t.decodifica_specie_bdn
           FROM ml8_master_list t
             JOIN recursetree rt ON rt.id = t.id_padre
        )
 SELECT DISTINCT recursetree.id AS id_nuova_linea_attivita,
    true AS enabled,
        CASE
            WHEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 2) IS NOT NULL AND split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 2) <> ''::text THEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 2)::integer
            ELSE '-1'::integer
        END AS id_macroarea,
        CASE
            WHEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 3) IS NOT NULL AND split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 3) <> ''::text THEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 3)::integer
            ELSE '-1'::integer
        END AS id_aggregazione,
        CASE
            WHEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 4) IS NOT NULL AND split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 4) <> ''::text THEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 4)::integer
            ELSE '-1'::integer
        END AS id_attivita,
        CASE
            WHEN split_part(recursetree.path_codice::text, '->'::text, 1) IS NOT NULL AND split_part(recursetree.path_codice::text, '->'::text, 1) <> ''::text THEN split_part(recursetree.path_codice::text, '->'::text, 1)
            ELSE '-1'::text
        END AS codice_macroarea,
        CASE
            WHEN split_part(recursetree.path_codice::text, '->'::text, 2) IS NOT NULL AND split_part(recursetree.path_codice::text, '->'::text, 2) <> ''::text THEN split_part(recursetree.path_codice::text, '->'::text, 2)
            ELSE 'N.D'::text
        END AS codice_aggregazione,
        CASE
            WHEN split_part(recursetree.path_codice::text, '->'::text, 3) IS NOT NULL AND split_part(recursetree.path_codice::text, '->'::text, 3) <> ''::text THEN split_part(recursetree.path_codice::text, '->'::text, 3)
            ELSE 'N.D'::text
        END AS codice_attivita,
        CASE
            WHEN split_part(recursetree.path_descrizione::text, '->'::text, 1) IS NOT NULL AND split_part(recursetree.path_descrizione::text, '->'::text, 1) <> ''::text THEN split_part(recursetree.path_descrizione::text, '->'::text, 1)
            ELSE 'N.D'::text
        END AS macroarea,
        CASE
            WHEN split_part(recursetree.path_descrizione::text, '->'::text, 2) IS NOT NULL AND split_part(recursetree.path_descrizione::text, '->'::text, 2) <> ''::text THEN split_part(recursetree.path_descrizione::text, '->'::text, 2)
            ELSE 'N.D'::text
        END AS aggregazione,
        CASE
            WHEN split_part(recursetree.path_descrizione::text, '->'::text, 3) IS NOT NULL AND split_part(recursetree.path_descrizione::text, '->'::text, 3) <> ''::text THEN split_part(recursetree.path_descrizione::text, '->'::text, 3)
            ELSE 'N.D'::text
        END AS attivita,
    recursetree.id_norma,
    ''::text AS norma,
    recursetree.descrizione,
    recursetree.livello,
    recursetree.id_padre,
    recursetree.path_id,
    recursetree.path_descrizione,
    (recursetree.path_id::text || ';'::text) || recursetree.id,
    recursetree.codice,
    recursetree.path_codice,
    recursetree.flag_pnaa,
    recursetree.id_lookup_configurazione_validazione,
    recursetree.id_lookup_tipo_attivita,
    recursetree.id_lookup_tipo_linee_mobili,
    recursetree.decodifica_tipo_produzione_bdn,
    recursetree.decodifica_codice_orientamento_bdn,
    recursetree.decodifica_specie_bdn
   FROM recursetree
  ORDER BY ((recursetree.path_id::text || ';'::text) || recursetree.id);

ALTER TABLE public.ml8_linee_attivita_nuove
  OWNER TO postgres;
GRANT ALL ON TABLE public.ml8_linee_attivita_nuove TO postgres;
GRANT ALL ON TABLE public.ml8_linee_attivita_nuove TO report;

-- View: public.suap_ric_scia_operatori_denormalizzati_view

-- DROP VIEW public.suap_ric_scia_operatori_denormalizzati_view;

CREATE OR REPLACE VIEW public.suap_ric_scia_operatori_denormalizzati_view AS 
 SELECT DISTINCT o.id AS id_opu_operatore,
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
    i.comune,
    stab.id_asl,
    stab.id AS id_stabilimento,
    comuni1.nome AS comune_stab,
    comuni1.istat AS istat_operativo,
    (((
        CASE
            WHEN topsedestab.description IS NOT NULL THEN topsedestab.description
            ELSE ''::character varying
        END::text || ' '::text) || sedestab.via::text) || ' '::text) ||
        CASE
            WHEN sedestab.civico IS NOT NULL THEN sedestab.civico
            ELSE ''::text
        END AS indirizzo_stab,
    sedestab.cap AS cap_stab,
    provsedestab.description AS prov_stab,
    soggsl.codice_fiscale AS cf_rapp_sede_legale,
    soggsl.nome AS nome_rapp_sede_legale,
    soggsl.cognome AS cognome_rapp_sede_legale,
    stab.numero_registrazione AS codice_registrazione,
    latt.id_norma,
    latt.codice_attivita AS cf_correntista,
    latt.codice_attivita,
    lps.primario,
    (((latt.macroarea || '->'::text) || latt.aggregazione) || '->'::text) || latt.attivita AS attivita,
    lps.data_inizio,
    lps.data_fine,
    stab.numero_registrazione,
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
        CASE
            WHEN lps.stato = 0 THEN 'DA VALIDARE'::character varying(50)
            WHEN lps.stato = 1 THEN 'VALIDATO'::character varying(50)
            WHEN lps.stato = 2 THEN 'RESPINTO'::character varying(50)
            WHEN stab.stato = 0 THEN 'DA VALIDARE'::character varying(50)
            WHEN stab.stato = 1 THEN 'VALIDATO'::character varying(50)
            WHEN stab.stato = 2 THEN 'RESPINTO'::character varying(50)
            ELSE ''::character varying(50)
        END AS stato,
    latt.attivita AS solo_attivita,
    stab.data_inizio_attivita,
    stab.data_fine_attivita,
    COALESCE(lps.stato, stab.stato) AS id_stato,
    latt.path_descrizione AS path_attivita_completo,
    stab.id_indirizzo,
    true as flag_nuova_gestione,
    loti.description AS tipo_impresa,
    lotis.description AS tipo_societa,
    stab.id_asl AS id_asl_stab,
    lps.id AS id_linea_attivita,
    lps.modified AS data_mod_attivita,
    stab.entered AS stab_entered,
    lps.numero_registrazione AS linea_numero_registrazione,
    lps.stato AS linea_stato,
    statilinea.description AS linea_stato_text,
    sedeop.id AS id_indirizzo_operatore,
    lps.id_linea_produttiva AS id_linea_attivita_stab,
    o.note AS note_operatore,
    stab.note AS note_stabilimento,
    latt.flag_pnaa,
    (con.namefirst::text || ' '::text) || con.namelast::text AS stab_inserito_da,
    stab.tipo_attivita AS stab_id_attivita,
    lsa.description AS stab_descrizione_attivita,
    stab.tipo_carattere AS stab_id_carattere,
    lsc.description AS stab_descrizione_carattere,
    o.tipo_impresa AS impresa_id_tipo_impresa,
    asl.description AS stab_asl,
    o.id_tipo_richiesta,
    suapr.description AS descrizione_tipo_richiesta,
    o.validato,
    soggsl.data_nascita,
    soggsl.comune_nascita,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa = 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisoggind.id
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa <> 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisl.id
            ELSE NULL::integer
        END AS id_comune_richiesta,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa = 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisoggind.nome
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa <> 1 AND (stab.tipo_attivita = 2 OR stab.tipo_attivita = 3) THEN comunisl.nome
            ELSE NULL::character varying
        END AS comune_richiesta,
    latt.macroarea,
    latt.aggregazione,
    latt.attivita AS linea_attivita,
    o.tipo_impresa AS id_tipo_impresa,
    o.tipo_societa AS id_topo_societa,
    sedestab.latitudine AS lat_stab,
    sedestab.longitudine AS long_stab,
    stab.cessazione_stabilimento AS stab_cessazione_stabilimento,
    stab.numero_registrazione_variazione,
    stab.partita_iva_variazione,
    stab.alt_id,
    ll.short_description AS permesso,
    i.via AS via_sede_stab,
    i.civico AS civico_sede_stab,
    i.toponimo AS toponimo_sede_stab,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.via::text)::character varying
            ELSE NULL::character varying
        END AS via_stabilimento_calcolata,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.civico)::character varying
            ELSE NULL::character varying
        END AS civico_stabilimento_calcolato,
    comunisoggind.nome AS comune_residenza,
    stab.note_validazione,
    stab.stato_validazione,
    stab.sospensione_stabilimento,
    stab.data_inizio_sospensione,
    latt.id_lookup_configurazione_validazione as id_tipo_linea_produttiva,
        CASE
            WHEN apicoltura.id <= 0 OR apicoltura.id IS NULL THEN true
            WHEN apicoltura.id > 0 AND apicoltura.sincronizzato_bdn = true THEN true
            ELSE false
        END AS validabile,
    apicoltura.codice_azienda AS codice_azienda_apicoltura,
    stab.cessazione_stabilimento,
    rels.id_soggetto_fisico,
    NULL::integer AS id_controllo_ultima_categorizzazione
   FROM suap_ric_scia_operatore o
     LEFT JOIN apicoltura_imprese apicoltura ON o.id = apicoltura.id_richiesta_suap
     LEFT JOIN suap_lookup_tipo_richiesta suapr ON suapr.code = o.id_tipo_richiesta
     JOIN suap_ric_scia_rel_operatore_soggetto_fisico rels ON rels.id_operatore = o.id AND rels.enabled
     JOIN suap_ric_scia_soggetto_fisico soggsl ON soggsl.id = rels.id_soggetto_fisico
     LEFT JOIN opu_indirizzo soggind ON soggind.id = soggsl.indirizzo_id
     LEFT JOIN comuni1 comunisoggind ON comunisoggind.id = soggind.comune
     LEFT JOIN opu_indirizzo sedeop ON sedeop.id = o.id_indirizzo
     LEFT JOIN comuni1 comunisl ON sedeop.comune = comunisl.id
     JOIN suap_ric_scia_stabilimento stab ON stab.id_operatore = o.id
     LEFT JOIN suap_ric_scia_relazione_stabilimento_linee_produttive lps ON lps.id_stabilimento = stab.id AND lps.enabled
     LEFT JOIN lookup_stato_lab stati ON stati.code = stab.stato
     LEFT JOIN ml8_linee_attivita_nuove_materializzata latt ON latt.id_nuova_linea_attivita = lps.id_linea_produttiva
     LEFT JOIN lookup_flusso_originale_ml lconf ON lconf.code = latt.id_lookup_configurazione_validazione
     LEFT JOIN lookup_ente_scia ll ON lconf.ente_validazione = ll.code
     LEFT join opu_lookup_norme_master_list nor on nor.code = latt.id_norma
     JOIN opu_indirizzo sedestab ON sedestab.id = stab.id_indirizzo
     LEFT JOIN comuni1 ON sedestab.comune = comuni1.id
     LEFT JOIN opu_indirizzo i ON i.id = stab.id_indirizzo
     LEFT JOIN lookup_province provsedestab ON provsedestab.code::text = sedestab.provincia::text
     LEFT JOIN lookup_province provsedeop ON provsedeop.code::text = sedeop.provincia::text
     LEFT JOIN lookup_province provsoggind ON provsoggind.code::text = soggind.provincia::text
     LEFT JOIN lookup_toponimi topsedeop ON sedeop.toponimo = topsedeop.code
     LEFT JOIN lookup_toponimi topsedestab ON sedestab.toponimo = topsedestab.code
     LEFT JOIN lookup_toponimi topsoggind ON soggind.toponimo = topsoggind.code
     LEFT JOIN lookup_opu_tipo_impresa loti ON loti.code = o.tipo_impresa
     LEFT JOIN lookup_opu_tipo_impresa_societa lotis ON lotis.code = o.tipo_societa
     LEFT JOIN lookup_stato_lab statilinea ON statilinea.code = lps.stato
     LEFT JOIN access acc ON acc.user_id = stab.entered_by
     LEFT JOIN contact con ON con.contact_id = acc.contact_id
     LEFT JOIN opu_lookup_tipologia_attivita lsa ON lsa.code = stab.tipo_attivita
     LEFT JOIN opu_lookup_tipologia_carattere lsc ON lsc.code = stab.tipo_carattere
     LEFT JOIN lookup_site_id asl ON asl.code = stab.id_asl
  WHERE o.trashed_date IS NULL AND stab.trashed_date IS NULL;

ALTER TABLE public.suap_ric_scia_operatori_denormalizzati_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.suap_ric_scia_operatori_denormalizzati_view TO postgres;
GRANT SELECT ON TABLE public.suap_ric_scia_operatori_denormalizzati_view TO usr_ro;
GRANT SELECT ON TABLE public.suap_ric_scia_operatori_denormalizzati_view TO report;


alter table suap_ric_scia_relazione_stabilimento_linee_produttive DROP CONSTRAINT suap_ric_scia_relazione_stabilimento_l_id_linea_produttiva_fkey;



-- Function: public_functions.suap_get_lista_linee_produttive(integer)

-- DROP FUNCTION public_functions.suap_get_lista_linee_produttive(integer);

CREATE OR REPLACE FUNCTION public_functions.suap_get_lista_linee_produttive(IN idstabilimentoscia integer)
  RETURNS TABLE(id_tipo_linea_produttiva integer, codice_nazionale text, id_linea_attivita integer, descrizione_linea_attivita text, data_fine timestamp without time zone, data_inizio timestamp without time zone, stato integer, primario boolean, id integer, id_rel_stab_lp integer, norma text, macro text, id_macrocategoria integer, codice text, id_norma integer, aggregazione text, id_categoria integer, attivita text, id_attivita integer, id_lookup_configurazione_validazione integer, descr_label text, permesso text) AS
$BODY$
DECLARE

BEGIN
FOR id_tipo_linea_produttiva,codice_nazionale,id_linea_attivita,descrizione_linea_attivita,data_fine,data_inizio,stato,
		primario,id,id_rel_stab_lp,norma, macro,id_macrocategoria,codice,id_norma,aggregazione,id_categoria,attivita,id_attivita,
		id_lookup_configurazione_validazione,descr_label,permesso
		in

		
	
select  distinct l.id_lookup_configurazione_validazione,rslp.codice_nazionale,rslp.id_linea_produttiva,
				l.path_descrizione , rslp.data_fine,rslp.data_inizio,rslp.stato,rslp.primario,
				l.id_nuova_linea_attivita,rslp.id ,l.norma , l.macroarea ,l.id_macroarea ,
				l.codice_attivita,l.id_norma,l.aggregazione ,l.id_aggregazione ,l.attivita ,
				l.id_attivita , l.id_lookup_configurazione_validazione,ll.description,ll.short_description as permesso 
				from ml8_linee_attivita_nuove_materializzata l 
				join suap_ric_scia_relazione_stabilimento_linee_produttive rslp on rslp.id_linea_produttiva = l.id_nuova_linea_attivita and rslp.enabled=true 

left join lookup_flusso_originale_ml lconf on lconf.code = l.id_lookup_configurazione_validazione 
				left join lookup_ente_scia ll on lconf.ente_validazione = ll.code 
				where rslp.id_stabilimento=idStabilimentoScia

		
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.suap_get_lista_linee_produttive(integer)
  OWNER TO postgres;


  
 -- View: public.opu_operatori_denormalizzati_view

-- DROP VIEW public.opu_operatori_denormalizzati_view;

CREATE OR REPLACE VIEW public.opu_operatori_denormalizzati_view AS 
 SELECT DISTINCT
        CASE
            WHEN stab.flag_dia IS NOT NULL THEN stab.flag_dia
            ELSE false
        END AS flag_dia,
    o.id AS id_opu_operatore,
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
    o.flag_ric_ce,
    o.num_ric_ce,
    i.comune,
    stab.id_asl,
    stab.id AS id_stabilimento,
    stab.esito_import,
    stab.data_import,
    stab.cun,
    stab.id_sinvsa,
    stab.descrizione_errore,
    comuni1.nome AS comune_stab,
    comuni1.istat AS istat_operativo,
    ((
        CASE
            WHEN topsedestab.description IS NOT NULL THEN topsedestab.description
            ELSE ''::character varying
        END::text || ' '::text) || sedestab.via::text) || ' '::text AS indirizzo_stab,
    sedestab.cap AS cap_stab,
    provsedestab.description AS prov_stab,
    stab.data_fine_dia,
    stab.categoria_rischio,
    soggsl.codice_fiscale AS cf_rapp_sede_legale,
    soggsl.nome AS nome_rapp_sede_legale,
    soggsl.cognome AS cognome_rapp_sede_legale,
    stab.numero_registrazione AS codice_registrazione,
    latt.id_norma,
    latt.codice_attivita AS cf_correntista,
    latt.codice_attivita,
    lps.primario,
    (((latt.macroarea || '->'::text) || latt.aggregazione) || '->'::text) || latt.attivita AS attivita,
    lps.data_inizio,
    lps.data_fine,
    stab.numero_registrazione,
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
    stab.data_inizio_attivita,
    stab.data_fine_attivita,
    stab.data_prossimo_controllo,
    stab.stato AS id_stato,
    latt.path_descrizione AS path_attivita_completo,
    stab.id_indirizzo,
    stab.linee_pregresse,
    true as flag_nuova_gestione,
    loti.description AS tipo_impresa,
    lotis.description AS tipo_societa,
    stab.codice_ufficiale_esistente,
    stab.id_asl AS id_asl_stab,
    lps.id AS id_linea_attivita,
    lps.modified AS data_mod_attivita,
    stab.entered AS stab_entered,
    lps.numero_registrazione AS linea_numero_registrazione,
    lps.stato AS linea_stato,
    statilinea.description AS linea_stato_text,
    lps.codice_ufficiale_esistente AS linea_codice_ufficiale_esistente,
    stab.codice_ufficiale_esistente AS stab_codice_ufficiale_esistente,
    sedeop.id AS id_indirizzo_operatore,
    stab.import_opu,
    lps.id_linea_produttiva AS id_linea_attivita_stab,
    o.note AS note_operatore,
    stab.note AS note_stabilimento,
    lps.codice_nazionale AS linea_codice_nazionale,
    latt.flag_pnaa,
    (con.namefirst::text || ' '::text) || con.namelast::text AS stab_inserito_da,
    stab.tipo_attivita AS stab_id_attivita,
    lsa.description AS stab_descrizione_attivita,
    stab.tipo_carattere AS stab_id_carattere,
    lsc.description AS stab_descrizione_carattere,
    o.tipo_impresa AS impresa_id_tipo_impresa,
    asl.description AS stab_asl,
    o.flag_clean,
    stab.data_generazione_numero,
    sedestab.latitudine AS lat_stab,
    sedestab.longitudine AS long_stab,
    lps.entered AS linea_entered,
    lps.modified AS linea_modified,
    latt.macroarea,
    latt.aggregazione,
    latt.attivita AS attivita_xml,
    comuni1.codiceistatasl_old,
    provsedestab.cod_provincia AS sigla_prov_operativa,
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
    sedestab.civico AS civico_sede_stab,
    sedestab.toponimo AS tiponimo_sede_stab,
    topsedestab.description AS toponimo_sede_stab,
    soggind.via AS via_rapp_sede_legale,
    sedeop.via AS via_sede_legale,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN comunisoggind.id
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.id
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN comunisl.id
            ELSE NULL::integer
        END AS id_comune_richiesta,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN comunisoggind.nome
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN comuni1.nome
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN comunisl.nome
            ELSE NULL::character varying
        END AS comune_richiesta,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.via::text)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.via::text)::character varying
            ELSE NULL::character varying
        END AS via_stabilimento_calcolata,
        CASE
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa = 1 AND stab.tipo_attivita = 2 THEN btrim(soggind.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 1 THEN btrim(sedestab.civico)::character varying
            WHEN o.tipo_impresa <> 1 AND stab.tipo_attivita = 2 THEN btrim(sedeop.civico)::character varying
            ELSE NULL::character varying
        END AS civico_stabilimento_calcolato,
    soggind.cap AS cap_residenza,
    soggind.nazione AS nazione_residenza,
    sedeop.nazione AS nazione_sede_legale,
    sedestab.nazione AS nazione_stab,
    latt.id_lookup_tipo_linee_mobili,
    -1 as id_tipo_linea_produttiva,
    rels.id_soggetto_fisico,
    lps.pregresso_o_import,
    stab.riferimento_org_id,
    stab.id_controllo_ultima_categorizzazione
   FROM opu_operatore o
     JOIN opu_rel_operatore_soggetto_fisico rels ON rels.id_operatore = o.id AND rels.enabled
     JOIN opu_soggetto_fisico soggsl ON soggsl.id = rels.id_soggetto_fisico
     LEFT JOIN opu_indirizzo soggind ON soggind.id = soggsl.indirizzo_id
     LEFT JOIN comuni1 comunisoggind ON comunisoggind.id = soggind.comune
     LEFT JOIN opu_indirizzo sedeop ON sedeop.id = o.id_indirizzo
     LEFT JOIN comuni1 comunisl ON sedeop.comune = comunisl.id
     JOIN opu_stabilimento stab ON stab.id_operatore = o.id
     JOIN opu_relazione_stabilimento_linee_produttive lps ON lps.id_stabilimento = stab.id AND lps.enabled
     LEFT JOIN lookup_stato_lab stati ON stati.code = stab.stato
     JOIN ml8_linee_attivita_nuove_materializzata latt ON latt.id_nuova_linea_attivita = lps.id_linea_produttiva
     JOIN opu_indirizzo sedestab ON sedestab.id = stab.id_indirizzo
     LEFT JOIN comuni1 ON sedestab.comune = comuni1.id
     LEFT JOIN opu_indirizzo i ON i.id = stab.id_indirizzo
     LEFT JOIN lookup_province provsedestab ON provsedestab.code::text = sedestab.provincia::text
     LEFT JOIN lookup_province provsedeop ON provsedeop.code::text = sedeop.provincia::text
     LEFT JOIN lookup_province provsoggind ON provsoggind.code::text = soggind.provincia::text
     LEFT JOIN lookup_toponimi topsedeop ON sedeop.toponimo = topsedeop.code
     LEFT JOIN lookup_toponimi topsedestab ON sedestab.toponimo = topsedestab.code
     LEFT JOIN lookup_toponimi topsoggind ON soggind.toponimo = topsoggind.code
     LEFT JOIN lookup_opu_tipo_impresa loti ON loti.code = o.tipo_impresa
     LEFT JOIN lookup_opu_tipo_impresa_societa lotis ON lotis.code = o.tipo_societa
     LEFT JOIN lookup_stato_lab statilinea ON statilinea.code = lps.stato
     LEFT JOIN access acc ON acc.user_id = stab.entered_by
     LEFT JOIN contact con ON con.contact_id = acc.contact_id
     LEFT JOIN opu_lookup_tipologia_attivita lsa ON lsa.code = stab.tipo_attivita
     LEFT JOIN opu_lookup_tipologia_carattere lsc ON lsc.code = stab.tipo_carattere
     LEFT JOIN lookup_site_id asl ON asl.code = stab.id_asl
  WHERE o.trashed_date IS NULL AND stab.trashed_date IS NULL AND stab.trashed_date IS NULL;

ALTER TABLE public.opu_operatori_denormalizzati_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.opu_operatori_denormalizzati_view TO postgres;
GRANT SELECT ON TABLE public.opu_operatori_denormalizzati_view TO usr_ro;
GRANT SELECT ON TABLE public.opu_operatori_denormalizzati_view TO report;


-- View: public.opu_linee_attivita_stabilimenti_view

-- DROP VIEW public.opu_linee_attivita_stabilimenti_view;

CREATE OR REPLACE VIEW public.opu_linee_attivita_stabilimenti_view AS 
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
   FROM opu_relazione_stabilimento_linee_produttive v1
     LEFT JOIN ml8_linee_attivita_nuove v2 ON v1.id_linea_produttiva = v2.id_nuova_linea_attivita
  WHERE v1.enabled;

ALTER TABLE public.opu_linee_attivita_stabilimenti_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.opu_linee_attivita_stabilimenti_view TO postgres;
GRANT SELECT ON TABLE public.opu_linee_attivita_stabilimenti_view TO report;




  alter table opu_stabilimento add column linee_pregresse_old boolean default false;
  update opu_stabilimento set linee_pregresse_old = linee_pregresse;
  update opu_stabilimento set linee_pregresse = false;
  update opu_stabilimento set linee_pregresse = true where id in (select s.id
 from opu_relazione_stabilimento_linee_produttive rel
join opu_stabilimento s on s.id = rel.id_stabilimento
  where rel.id_linea_produttiva <40000 and rel.enabled  and s.trashed_date is null)


