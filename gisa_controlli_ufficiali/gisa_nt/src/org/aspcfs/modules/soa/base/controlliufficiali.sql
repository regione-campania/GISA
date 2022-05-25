
CREATE TABLE lookup_tipo_campione_batteri_soa (
    code integer NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    level integer DEFAULT 0,
    enabled boolean DEFAULT true
);


ALTER TABLE public.lookup_tipo_campione_batteri_soa OWNER TO postgres;



INSERT INTO lookup_tipo_campione_batteri_soa (code, description, default_item, level, enabled) VALUES (1, 'Salmonella', false, 0, true);
INSERT INTO lookup_tipo_campione_batteri_soa (code, description, default_item, level, enabled) VALUES (2, 'Enterobacter', false, 1, true);
INSERT INTO lookup_tipo_campione_batteri_soa (code, description, default_item, level, enabled) VALUES (3, 'Clastridium perfrigens', false, 2, true);


CREATE TABLE lookup_tipo_campione_chimico_soa (
    code integer NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    level integer DEFAULT 0,
    enabled boolean DEFAULT true
);



INSERT INTO lookup_tipo_campione_chimico_soa (code, description, default_item, level, enabled) VALUES (1, 'GHT sui Grassi', false, 0, true);

CREATE TABLE lookup_tipo_campione_fisico_soa (
    code integer NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    level integer DEFAULT 0,
    enabled boolean DEFAULT true
);



INSERT INTO lookup_tipo_campione_fisico_soa (code, description, default_item, level, enabled) VALUES (1, 'Impurita'' sui Grassi', false, 0, true);


CREATE TABLE lookup_altrialimenti_nonanimale_soa (
    code integer NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    level integer DEFAULT 0,
    enabled boolean DEFAULT true
);


INSERT INTO lookup_altrialimenti_nonanimale_soa (code, description, default_item, level, enabled) VALUES (1, 'Ciccioli Proteici', false, 0, true);
INSERT INTO lookup_altrialimenti_nonanimale_soa (code, description, default_item, level, enabled) VALUES (2, 'Farina', false, 1, true);
INSERT INTO lookup_altrialimenti_nonanimale_soa (code, description, default_item, level, enabled) VALUES (3, 'Grassi', false, 2, true);
INSERT INTO lookup_altrialimenti_nonanimale_soa (code, description, default_item, level, enabled) VALUES (4, 'Altro', false, 3, true);


CREATE TABLE lookup_sequestri_amministrative_soa (
    code integer NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    level integer DEFAULT 0,
    enabled boolean DEFAULT true
);



INSERT INTO lookup_sequestri_amministrative_soa (code, description, default_item, level, enabled) VALUES (1, 'Stabilimento', false, 0, true);
INSERT INTO lookup_sequestri_amministrative_soa (code, description, default_item, level, enabled) VALUES (2, 'Locali', false, 1, true);
INSERT INTO lookup_sequestri_amministrative_soa (code, description, default_item, level, enabled) VALUES (3, 'Attrezzature', false, 2, true);
INSERT INTO lookup_sequestri_amministrative_soa (code, description, default_item, level, enabled) VALUES (4, 'Alimenti Origine Animale', false, 3, false);
INSERT INTO lookup_sequestri_amministrative_soa (code, description, default_item, level, enabled) VALUES (5, 'Alimenti Origine Vegetale', false, 4, false);
INSERT INTO lookup_sequestri_amministrative_soa (code, description, default_item, level, enabled) VALUES (6, 'Alimenti Origine Animale e Vegetale', false, 5, false);
INSERT INTO lookup_sequestri_amministrative_soa (code, description, default_item, level, enabled) VALUES (7, 'Locali e Attrezzature', false, 6, true);
INSERT INTO lookup_sequestri_amministrative_soa (code, description, default_item, level, enabled) VALUES (8, 'Documenti', false, 7, true);
INSERT INTO lookup_sequestri_amministrative_soa (code, description, default_item, level, enabled) VALUES (9, 'Mezzo di Trasporto', false, 8, true);
INSERT INTO lookup_sequestri_amministrative_soa (code, description, default_item, level, enabled) VALUES (10, 'Materia Prima', false, 0, true);
INSERT INTO lookup_sequestri_amministrative_soa (code, description, default_item, level, enabled) VALUES (11, 'Prodotto Trasformato', false, 10, true);

CREATE TABLE lookup_provvedimenti_soa (
    code integer NOT NULL,
    description character varying(50) NOT NULL,
    default_item boolean DEFAULT false,
    level integer DEFAULT 0,
    enabled boolean DEFAULT true
);



INSERT INTO lookup_provvedimenti_soa (code, description, default_item, level, enabled) VALUES (1, 'Documentale', false, 0, true);
INSERT INTO lookup_provvedimenti_soa (code, description, default_item, level, enabled) VALUES (2, 'Materia Prima', false, 1, true);
INSERT INTO lookup_provvedimenti_soa (code, description, default_item, level, enabled) VALUES (3, 'Igiene Strutturale', false, 2, true);
INSERT INTO lookup_provvedimenti_soa (code, description, default_item, level, enabled) VALUES (4, 'Igiene Attrezzature', false, 3, true);
INSERT INTO lookup_provvedimenti_soa (code, description, default_item, level, enabled) VALUES (5, 'Personale', false, 4, true);
INSERT INTO lookup_provvedimenti_soa (code, description, default_item, level, enabled) VALUES (6, 'Prodotto Finito', false, 5, true);
INSERT INTO lookup_provvedimenti_soa (code, description, default_item, level, enabled) VALUES (7, 'Igiene del Personale', false, 6, true);
INSERT INTO lookup_provvedimenti_soa (code, description, default_item, level, enabled) VALUES (8, 'Sanificazione', false, 7, true);
INSERT INTO lookup_provvedimenti_soa (code, description, default_item, level, enabled) VALUES (9, 'Autocontrollo', false, 8, true);
INSERT INTO lookup_provvedimenti_soa (code, description, default_item, level, enabled) VALUES (10, 'Tracciabilita''/Rintracciabilita''', false, 9, true);
INSERT INTO lookup_provvedimenti_soa (code, description, default_item, level, enabled) VALUES (11, 'Trasporto', false, 10, true);
INSERT INTO lookup_provvedimenti_soa (code, description, default_item, level, enabled) VALUES (12, 'Emissioni Atmosferische', false, 11, true);
INSERT INTO lookup_provvedimenti_soa (code, description, default_item, level, enabled) VALUES (13, 'Gestione M.S.R', false, 12, true);
INSERT INTO lookup_provvedimenti_soa (code, description, default_item, level, enabled) VALUES (14, 'Modalita'' Magazzinaggio', false, 13, true);
INSERT INTO lookup_provvedimenti_soa (code, description, default_item, level, enabled) VALUES (15, 'Altro', false, 14, true);




