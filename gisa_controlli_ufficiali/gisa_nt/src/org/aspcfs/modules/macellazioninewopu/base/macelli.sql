CREATE TABLE m_lookup_campioni_esiti
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_campioni_motivi
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_nazioni
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_regioni
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_razze
(
	code SERIAL    PRIMARY KEY,
	text_code      VARCHAR( 6 ),
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_specie
(
	code SERIAL    PRIMARY KEY,
	text_code      VARCHAR( 6 ),
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_piani_risanamento
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_tipi_macellazione
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_esiti_vpm
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_patologie
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_patologie_organi
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_tipi_esame
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_azioni
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_stadi
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_organi
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_tipo_analisi
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_bse
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_capi
(
	id SERIAL            PRIMARY KEY,
	id_macello           INT REFERENCES organization( org_id ),
	stato_macellazione text,
	
 -->> CONTROLLO DOCUMENTALE <<--
	cd_provenienza_stato      INT,-- REFERENCES m_lookup_nazioni, --VARCHAR( 64 ), --meglio lookup
	cd_prov_stato_comunitario BOOLEAN,       --non serve se stato = lookup
	cd_provenienza_regione    INT,-- REFERENCES m_lookup_regioni(code),
	cd_provenienza_comune     VARCHAR( 64 ),-- REFERENCES m_lookup_comuni(code),
	cd_speditore              VARCHAR( 64 ),
	--destinazione carni
	destinatario_1_id         INT,
	destinatario_1_in_regione BOOLEAN,  --true se e' in regione false altrimenti (impresa se in regione, esercente se fuori regione)
	destinatario_1_nome       TEXT,
	destinatario_2_id         INT,
	destinatario_2_in_regione BOOLEAN,  --true se e' in regione false altrimenti (impresa se in regione, esercente se fuori regione)
	destinatario_2_nome       TEXT,
	--proprietario
	cd_codice_azienda       VARCHAR( 64 ),
	--animale
	cd_matricola            VARCHAR( 64 ),
	cd_specie               INT,
	cd_data_nascita         TIMESTAMP WITH TIME ZONE,
	cd_maschio              BOOLEAN,
	cd_id_razza             INT,-- REFERENCES m_lookup_razze(code),
	cd_vincolo_sanitario        BOOLEAN,
	cd_vincolo_sanitario_motivo TEXT,
	cd_macellazione_differita   INT,-- REFERENCES m_lookup_piani_risanamento(code),
	cd_mod4                     VARCHAR(64),
	cd_data_mod4                TIMESTAMP WITH TIME ZONE,
	cd_data_arrivo_macello      TIMESTAMP WITH TIME ZONE,
	cd_info_catena_alimentare   BOOLEAN,
	cd_bse                      INT,
	cd_asl                      INT,
	
    cd_veterinario_1 VARCHAR(64),
    cd_veterinario_2 VARCHAR(64),
    cd_veterinario_3 VARCHAR(64),
        
 -->> TEST BSE <<--
	bse_data_prelievo        TIMESTAMP WITH TIME ZONE,
	bse_data_ricezione_esito TIMESTAMP WITH TIME ZONE,
	bse_esito                VARCHAR(32),
	bse_note                 TEXT,
        
 -->> VISITA ANTE MORTEM <<--
    vam_data                          TIMESTAMP WITH TIME ZONE,
    vam_favorevole                    BOOLEAN,
    vam_macellazione_con_osservazione BOOLEAN,
    vam_provvedimenti					INT,
    vam_esito							TEXT,
    
 -->> MACELLAZIONE <<--
	mac_progressivo   INT,
	mac_tipo          INT,-- REFERENCES m_lookup_tipi_macellazione(code),
	    
 -->> VISITA POST MORTEM <<--
    vpm_data                  TIMESTAMP WITH TIME ZONE,
    vpm_data_esito 			  timestamp with time zone,
    vpm_veterinario           VARCHAR(64),
    vpm_esito                 INT,-- REFERENCES m_lookup_esiti_vpm(code),
    vpm_note                  TEXT,
    vpm_causa_patologia       VARCHAR(64),
    vpm_veterinario_1         VARCHAR(64),
    vpm_veterinario_2         VARCHAR(64),
    vpm_veterinario_3         VARCHAR(64),
    
 -->> MORTE ANTECEDENTE VISITA AM <<--
	mavam_data   TIMESTAMP WITH TIME ZONE,
	mavam_luogo  INT,
	mavam_motivo TEXT,
	mavam_descrizione_luogo_verifica TEXT,
	mavam_impianto_termodistruzione  TEXT,
   	
 -->> COMUNICAZIONE ALL'ASL <<--
    casl_data           TIMESTAMP WITH TIME ZONE,
    casl_motivo         INT,
    casl_info_richiesta TEXT,
    casl_to_asl_origine          BOOLEAN,
	casl_to_proprietario_animale BOOLEAN,
	casl_to_azienda_origine      BOOLEAN,
	casl_to_proprietario_macello BOOLEAN,
	casl_note_prevvedimento		 TEXT,
    
 -->> RICEZIONE COMUNICAZIONE ALL'ASL <<--
    rca_data TIMESTAMP WITH TIME ZONE,
    rca_note TEXT,
    
 -->> SEQUESTRO ANIMALE <<--
    seqa_data                      TIMESTAMP WITH TIME ZONE,
    seqa_data_sblocco              TIMESTAMP WITH TIME ZONE,
    seqa_destinazione_allo_sblocco INT,
    
 -->> ABBATTIMENTO <<--
    abb_data          TIMESTAMP WITH TIME ZONE,
    abb_veterinario   VARCHAR(64),
    abb_motivo        TEXT,
    abb_dist_carcassa BOOLEAN,
    abb_veterinario_2 VARCHAR(64),
    abb_veterinario_3 VARCHAR(64),

 -->> LIBERO CONSUMO PREVIO RISANAMENTO <<--
    lcpr_data_prevista_liber  TIMESTAMP WITH TIME ZONE,
    lcpr_risanamento          VARCHAR(64),
    lcpr_data_effettiva_liber TIMESTAMP WITH TIME ZONE,
    
 -->> LIBERO CONSUMO CON SEQUESTRO ORGANI <<--
    lcso_data      TIMESTAMP WITH TIME ZONE,
    
 -->> DISTRUZIONE CARCASSA <<--
   	entered_by           INT,
	modified_by          INT,
	entered              TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	modified             TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	trashed_date         TIMESTAMP WITH TIME ZONE
);
CREATE UNIQUE INDEX m_evita_matricole_duplicate ON m_capi( upper( nullif( trim(cd_matricola), '' ) ), coalesce( trashed_date, '0000-01-01' ) );

CREATE TABLE m_capi_bdn
(
	id serial PRIMARY KEY,
	matricola VARCHAR( 64 ) UNIQUE,
	codice_azienda VARCHAR( 64 ),
	data_nascita TIMESTAMP WITH TIME ZONE,
	razza VARCHAR( 64 ),
	maschio BOOLEAN,
	specie VARCHAR( 64 )
);

CREATE TABLE m_lookup_tipi_non_conformita
(
	code SERIAL               PRIMARY KEY,
	description               VARCHAR( 100 ),
	default_item              BOOLEAN DEFAULT FALSE,
	level                     INT,
	enabled                   BOOLEAN, 
    malattie_infettive        BOOLEAN DEFAULT FALSE
);

CREATE TABLE m_lookup_provvedimenti_vam
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_provvedimenti_casl
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_capi_non_conformita
(
	id SERIAL       PRIMARY KEY,
	id_capo         INT,-- REFERENCES m_capi( id ),
	id_tipo         INT,-- REFERENCES m_lookup_non_conformita( code ),
	note            TEXT,
	entered_by      INT,
	modified_by     INT,
	entered         TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	modified        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	trashed_date    TIMESTAMP WITH TIME ZONE
);

CREATE TABLE m_vpm_patologie_rilevate
(
	id SERIAL       PRIMARY KEY,
	id_capo         INT,
	id_patologia    INT,
	note            TEXT,
	entered_by      INT,
	modified_by     INT,
	entered         TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	modified        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	trashed_date    TIMESTAMP WITH TIME ZONE
);

CREATE TABLE m_casl_provvedimenti
(
	id SERIAL      		PRIMARY KEY,
	id_capo         	INT,
	id_provvedimento    INT,
	note            	TEXT,
	entered_by      	INT,
	modified_by     	INT,
	entered         	TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	modified        	TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	trashed_date    	TIMESTAMP WITH TIME ZONE
);

CREATE TABLE m_casl_non_conformita_rilevate
(
	id SERIAL       		PRIMARY KEY,
	id_capo         		INT,
	id_casl_non_conformita  INT,
	note            		TEXT,
	entered_by      		INT,
	modified_by     		INT,
	entered         		TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	modified        		TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	trashed_date    		TIMESTAMP WITH TIME ZONE
);

CREATE TABLE m_vpm_campioni
(
	id SERIAL       PRIMARY KEY,
	id_capo         INT,
	matrice         INT,
	id_tipo_analisi INT,
	id_molecole		INT,
	id_motivo       INT,
	id_esito        INT,
	note            TEXT,
	entered_by      INT,
	modified_by     INT,
	entered         TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	modified        TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	trashed_date    TIMESTAMP WITH TIME ZONE
);

CREATE TABLE m_capi_storia
(
	id SERIAL    PRIMARY KEY,
	id_capo      INT REFERENCES m_capi( id ),
	description  TEXT,
	operazione   TEXT,
	note         TEXT,
	entered_by   INT,
	entered      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE m_lookup_stati
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_azioni 
(
	id SERIAL    PRIMARY KEY,
	nome         VARCHAR( 64 ), --equivalente al permesso associato all'azione es: "macello-capo-controllo-add"
	descrizione  TEXT,
	enabled      BOOLEAN
);

CREATE TABLE m_stati_azioni 
(
	id_azione    INT REFERENCES m_azioni( id ),
	id_stato     INT REFERENCES m_lookup_stati( code ),
	enabled      BOOLEAN,
	PRIMARY KEY( id_azione, id_stato )
);

CREATE TABLE m_stati_condizioni 
(
	id SERIAL    PRIMARY KEY,
	id_stato     INT REFERENCES m_lookup_stati( code ),
	tabelle      VARCHAR( 64 ),
	campi        VARCHAR( 64 ),
	condizioni   VARCHAR( 64 ),
	enabled      BOOLEAN
);

CREATE TABLE m_anagrafica_teramo
(
	id                     SERIAL PRIMARY KEY,
	codice_usl             VARCHAR( 4 ),
	codice_distretto       VARCHAR( 64 ),
	id_allevamento         VARCHAR( 64 ),
	data_estrazione_dati   VARCHAR( 64 ),
	inserimento_variazione VARCHAR( 64 ),
	codice_interno         VARCHAR( 64 ),
	codice_identificativo_capo   VARCHAR( 64 ),
	codice_azienda               VARCHAR( 64 ),
	identificativo_fiscale_allev VARCHAR( 64 ),
	specie_allevata              VARCHAR( 64 ),
	flag_inseminazione   VARCHAR( 64 ),
	codice_marchio_madre VARCHAR( 64 ),
	codice_precedente    VARCHAR( 64 ),
	codice_elettronico   VARCHAR( 64 ),
	rezza_capo           VARCHAR( 64 ),
	sesso_capo           VARCHAR( 64 ),
	data_nascita_capo    VARCHAR( 64 ),
	data_ingresso_stalla VARCHAR( 64 ),
	data_applicazione_marca   VARCHAR( 64 ),
	data_isccrizione_anagrafe VARCHAR( 64 ),
	origine_animale           VARCHAR( 64 ),
	paese_provenienza         VARCHAR( 64 ),
	codice_libro_genealogico  VARCHAR( 64 ),
	data_ultimo_aggior        VARCHAR( 64 ),
	numero_certif_sanitario   VARCHAR( 64 ),
	numero_riferim_locale     VARCHAR( 64 )
);


CREATE TABLE m_lookup_molecole
(
	code SERIAL     PRIMARY KEY,
	description     VARCHAR( 100 ),
	default_item    BOOLEAN DEFAULT FALSE,
	level           INT,
	enabled         BOOLEAN,
	pnr             BOOLEAN DEFAULT FALSE,
	batteriologico  BOOLEAN DEFAULT FALSE,
	parassitologico BOOLEAN DEFAULT FALSE
);

CREATE VIEW veterinari_view AS 
	select 
		a.user_id as code,
		upper( trim( c.namelast || ' ' || c.namefirst ) ) as description,
		false as default_item,
		a.site_id as level,
		a.enabled as enabled,
		a.site_id as asl
	from contact c, access a where c.user_id = a.user_id and a.role_id = 15
	order by description
;

CREATE VIEW m_lookup_molecole_pnr AS 
	select *
	from m_lookup_molecole
	where pnr
	order by level, description
;

CREATE VIEW m_lookup_molecole_batteriologico AS 
	select *
	from m_lookup_molecole
	where batteriologico
	order by level, description
;

CREATE VIEW m_lookup_molecole_parassitologico AS 
	select *
	from m_lookup_molecole
	where parassitologico
	order by level, description
;

CREATE VIEW m_lookup_tipi_non_conformita_normale AS 
	select *
	from m_lookup_tipi_non_conformita
	where malattie_infettive IS false
	order by level, description
;

CREATE VIEW m_lookup_tipi_non_conformita_malattie_infettive AS 
	select *
	from m_lookup_tipi_non_conformita
	where malattie_infettive
	order by level, description
;


-->> TABELLA ORGANI <<--
CREATE TABLE m_lcso_organi
(
  id serial NOT NULL,
  id_capo integer,
  lcso_patologia integer,
  lcso_stadio integer,
  lcso_organo integer,
  entered_by integer,
  modified_by integer,
  entered timestamp with time zone DEFAULT now(),
  modified timestamp with time zone DEFAULT now(),
  trashed_date timestamp with time zone,
  enabled boolean,
  trashed_by integer,
  CONSTRAINT m_lcso_organi_pkey PRIMARY KEY (id)
);


CREATE TABLE m_lookup_matrici
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_luoghi_verifica
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_lookup_motivi_comunicazioni_asl
(
	code SERIAL    PRIMARY KEY,
	description    VARCHAR( 100 ),
	default_item   BOOLEAN DEFAULT FALSE,
	level          INT,
	enabled        BOOLEAN 
);

CREATE TABLE m_art17
(
	id SERIAL       PRIMARY KEY,
	id_macello      INT,
	id_esercente    INT,
	anno            INT,
	progressivo     INT,
	data_modello    DATE,
	data_prima_generazione      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	data_ultima_generazione     TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	utente_prima_generazione    INT,
	utente_ultima_generazione   INT,
	num_capi_prima_generazione  INT,
	num_capi_ultima_generazione INT,
	num_generazioni             INT,
	trashed_by      INT,
	trashed_date    TIMESTAMP WITH TIME ZONE,
	UNIQUE( id_macello, id_esercente, data_modello, trashed_date )
);


-- CREATO DA ALBERTO CAMPANILE

-- VIEW per lesioni organi relativi alla milza
CREATE OR REPLACE VIEW m_lookup_lesione_milza AS 
 SELECT *
 FROM m_lookup_patologie_organi
 WHERE m_lookup_patologie_organi.code=1 OR
		m_lookup_patologie_organi.code=2 OR
		m_lookup_patologie_organi.code=3 OR
		m_lookup_patologie_organi.code=4 OR
		m_lookup_patologie_organi.code=5 OR
		m_lookup_patologie_organi.code=24 OR
		m_lookup_patologie_organi.code=25 
 ORDER BY m_lookup_patologie_organi.level;
 
 
 -- VIEW per lesioni organi relativi al cuore
CREATE OR REPLACE VIEW m_lookup_lesione_cuore AS 
 SELECT *
 FROM m_lookup_patologie_organi
 WHERE m_lookup_patologie_organi.code=6 OR
		m_lookup_patologie_organi.code=7 OR
		m_lookup_patologie_organi.code=8 OR
		m_lookup_patologie_organi.code=9 OR
		m_lookup_patologie_organi.code=10 OR
		m_lookup_patologie_organi.code=2 OR
		m_lookup_patologie_organi.code=5
 ORDER BY m_lookup_patologie_organi.level;
 
 

 -- VIEW per lesioni organi relativi al polmoni
CREATE OR REPLACE VIEW m_lookup_lesione_polmoni AS 
 SELECT *
 FROM m_lookup_patologie_organi
 WHERE m_lookup_patologie_organi.code=11 OR
		m_lookup_patologie_organi.code=4 OR
		m_lookup_patologie_organi.code=2 OR
		m_lookup_patologie_organi.code=9 OR
		m_lookup_patologie_organi.code=10 OR
		m_lookup_patologie_organi.code=5 OR
		m_lookup_patologie_organi.code=24 OR
		m_lookup_patologie_organi.code=25 
 ORDER BY m_lookup_patologie_organi.level;
 


  -- VIEW per lesioni organi relativi al fegato
CREATE OR REPLACE VIEW m_lookup_lesione_fegato AS 
 SELECT *
 FROM m_lookup_patologie_organi
 WHERE m_lookup_patologie_organi.code=12 OR
		m_lookup_patologie_organi.code=13 OR
		m_lookup_patologie_organi.code=04 OR
		m_lookup_patologie_organi.code=06 OR
		m_lookup_patologie_organi.code=02 OR
		m_lookup_patologie_organi.code=09 OR
		m_lookup_patologie_organi.code=14 OR
		m_lookup_patologie_organi.code=10 OR
		m_lookup_patologie_organi.code=5 OR
		m_lookup_patologie_organi.code=24 OR
		m_lookup_patologie_organi.code=25 
 ORDER BY m_lookup_patologie_organi.level;
 
 
  -- VIEW per lesioni organi relativi al rene
CREATE OR REPLACE VIEW m_lookup_lesione_rene AS 
 SELECT *
 FROM m_lookup_patologie_organi
 WHERE m_lookup_patologie_organi.code=15 OR
		m_lookup_patologie_organi.code=04 OR
		m_lookup_patologie_organi.code=06 OR
		m_lookup_patologie_organi.code=02 OR
		m_lookup_patologie_organi.code=09 OR
		m_lookup_patologie_organi.code=10 OR
		m_lookup_patologie_organi.code=5 OR
		m_lookup_patologie_organi.code=24 OR
		m_lookup_patologie_organi.code=25 
 ORDER BY m_lookup_patologie_organi.level;
 
 
 
  -- VIEW per lesioni organi relativi al mammella
CREATE OR REPLACE VIEW m_lookup_lesione_mammella AS 
 SELECT *
 FROM m_lookup_patologie_organi
 WHERE m_lookup_patologie_organi.code=16 OR
		m_lookup_patologie_organi.code=04 OR
		m_lookup_patologie_organi.code=02 OR
		m_lookup_patologie_organi.code=5 OR
		m_lookup_patologie_organi.code=24 OR
		m_lookup_patologie_organi.code=25 
 ORDER BY m_lookup_patologie_organi.level;
 

  -- VIEW per lesioni organi relativi al apparato_genitale
CREATE OR REPLACE VIEW m_lookup_lesione_apparato_genitale AS 
 SELECT *
 FROM m_lookup_patologie_organi
 WHERE m_lookup_patologie_organi.code=17 OR
		m_lookup_patologie_organi.code=04 OR
		m_lookup_patologie_organi.code=02 OR
		m_lookup_patologie_organi.code=5 OR
		m_lookup_patologie_organi.code=24 OR
		m_lookup_patologie_organi.code=25 
 ORDER BY m_lookup_patologie_organi.level;
 
 
   -- VIEW per lesioni organi relativi al stomaco
CREATE OR REPLACE VIEW m_lookup_lesione_stomaco AS 
 SELECT *
 FROM m_lookup_patologie_organi
 WHERE m_lookup_patologie_organi.code=18 OR
		m_lookup_patologie_organi.code=5 
 ORDER BY m_lookup_patologie_organi.level;

 
    -- VIEW per lesioni organi relativi al intestino
CREATE OR REPLACE VIEW m_lookup_lesione_intestino AS 
 SELECT *
 FROM m_lookup_patologie_organi
 WHERE m_lookup_patologie_organi.code=19 OR
		m_lookup_patologie_organi.code=5 
 ORDER BY m_lookup_patologie_organi.level;

 
     -- VIEW per lesioni organi relativi al parti osteomuscolari
CREATE OR REPLACE VIEW m_lookup_lesione_osteomuscolari AS 
 SELECT *
 FROM m_lookup_patologie_organi
 WHERE m_lookup_patologie_organi.code=20 OR
		m_lookup_patologie_organi.code=21 OR
		m_lookup_patologie_organi.code=06 OR
		m_lookup_patologie_organi.code=22 OR
		m_lookup_patologie_organi.code=08 OR
		m_lookup_patologie_organi.code=23 OR
		m_lookup_patologie_organi.code=10 OR
		m_lookup_patologie_organi.code=02 OR
		m_lookup_patologie_organi.code=04 OR
		m_lookup_patologie_organi.code=5 OR
		m_lookup_patologie_organi.code=24 OR
		m_lookup_patologie_organi.code=25 
 ORDER BY m_lookup_patologie_organi.level;

      -- VIEW per lesioni organi relativi a generici
CREATE OR REPLACE VIEW m_lookup_lesione_generici AS 
 SELECT *
 FROM m_lookup_patologie_organi
 WHERE m_lookup_patologie_organi.code=04 OR
		m_lookup_patologie_organi.code=24 OR
		m_lookup_patologie_organi.code=25 OR
		m_lookup_patologie_organi.code=05 OR
		m_lookup_patologie_organi.code=02 OR
		m_lookup_patologie_organi.code=26 OR
		m_lookup_patologie_organi.code=28 
 ORDER BY m_lookup_patologie_organi.level;
