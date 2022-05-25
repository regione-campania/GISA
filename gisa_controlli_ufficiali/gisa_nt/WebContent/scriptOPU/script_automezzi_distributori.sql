/*************************************************************************/
/*************************************************************************/
/*******GESTIONI MOBILE DI AUTOMEZZI E DISTRIBUTORI (25/11/2015) *********/
/*************************************************************************/
/*************************************************************************/

ALTER TABLE public.master_list_suap ADD id_lookup_tipo_linee_mobili integer default 0;

update master_list_suap set id_lookup_tipo_linee_mobili  = 2  where id in (794,799,798);
update master_list_suap set id_lookup_tipo_linee_mobili  = 1  where id in (813,814,815,816,819,820,822,823,824,825);
------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO permission(category_id, permission, permission_view, permission_add, 
  permission_edit, permission_delete, description, level, enabled, active, viewpoints)
  VALUES (29,'opu-gestione-mobile',true,true,true,true,'Gestione Operatore Unico Mobile (automezzi e distributori)',0,true,true,false);
------------------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW opu_linee_attivita_nuove AS 
 WITH RECURSIVE recursetree(id, flag_pnaa, nonno, descrizione, livello, id_padre, path_id, path_descrizione, codice, path_codice, id_norma, flag_nuova_gestione, id_old, id_lookup_configurazione_validazione,id_lookup_tipo_attivita,id_tipo_linea_produttiva, id_lookup_tipo_linee_mobili) AS (
                 SELECT master_list_suap.id, 
                    master_list_suap.flag_pnaa, 
                    master_list_suap.id_padre AS nonno, 
                    master_list_suap.descrizione, 
                    master_list_suap.livello, 
                    master_list_suap.id_padre, 
                    master_list_suap.id_padre::character varying(1000) AS path_id, 
                    master_list_suap.descrizione::character varying(1000) AS path_desc, 
                    master_list_suap.codice, 
                    master_list_suap.codice::character varying(1000) AS path_codice, 
                    master_list_suap.id_norma, 
                    master_list_suap.flag_nuova_gestione, 
                    master_list_suap.id_old_la, 
                    master_list_suap.id_tipo_linea_produttiva, 
                    master_list_suap.id_lookup_configurazione_validazione, 
                    master_list_suap.id_lookup_tipo_attivita,
                    master_list_suap.id_lookup_tipo_linee_mobili
                   FROM master_list_suap
                  WHERE master_list_suap.id_padre = (-1)
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
                    t.flag_nuova_gestione, 
                    t.id_old_la, 
                    t.id_tipo_linea_produttiva, 
                    t.id_lookup_configurazione_validazione, 
                    t.id_lookup_tipo_attivita,
                    t.id_lookup_tipo_linee_mobili
                   FROM master_list_suap t
              JOIN recursetree rt ON rt.id = t.id_padre
        )
 SELECT DISTINCT recursetree.id AS id_nuova_linea_attivita, 
    true AS enabled, 
        CASE
            WHEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 2) IS NOT NULL AND split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 2) <> ''::text THEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 2)::integer
            ELSE (-1)
        END AS id_macroarea, 
        CASE
            WHEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 3) IS NOT NULL AND split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 3) <> ''::text THEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 3)::integer
            ELSE (-1)
        END AS id_aggregazione, 
        CASE
            WHEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 4) IS NOT NULL AND split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 4) <> ''::text THEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 4)::integer
            ELSE (-1)
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
    recursetree.flag_nuova_gestione, 
    recursetree.id_old, 
    recursetree.id_tipo_linea_produttiva, 
    recursetree.flag_pnaa, 
    recursetree.id_lookup_configurazione_validazione, 
    recursetree.id_lookup_tipo_attivita,
    recursetree.id_lookup_tipo_linee_mobili
   FROM recursetree
  ORDER BY (recursetree.path_id::text || ';'::text) || recursetree.id;

ALTER TABLE opu_linee_attivita_nuove
  OWNER TO postgres;
GRANT ALL ON TABLE opu_linee_attivita_nuove TO postgres;
GRANT ALL ON TABLE opu_linee_attivita_nuove TO report;
------------------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE lookup_tipo_linee_mobili
(
  code serial NOT NULL,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_tipo_linee_mobili
  OWNER TO postgres;
GRANT ALL ON TABLE lookup_tipo_linee_mobili TO postgres;
GRANT SELECT ON TABLE lookup_tipo_linee_mobili TO report;
------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO lookup_tipo_linee_mobili(code, description, default_item, level, enabled)  VALUES (0,'Non mobile', false, 0, true);
INSERT INTO lookup_tipo_linee_mobili(code, description, default_item, level, enabled)  VALUES (1,'Automezzi', false, 0, true);
INSERT INTO lookup_tipo_linee_mobili(code, description, default_item, level, enabled)  VALUES (2,'Distributori', false, 0, true);
------------------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE linee_mobili_html_fields
(
  id serial NOT NULL,
  id_linea integer NOT NULL,
  nome_campo character varying NOT NULL,
  tipo_campo character varying NOT NULL,
  label_campo character varying NOT NULL,
  tabella_lookup character varying,
  javascript_function character varying,
  javascript_function_evento character varying,
  link_value character varying,
  ordine_campo integer,
  maxlength integer,
  only_hd integer DEFAULT 0,
  label_link character varying,
  multiple boolean,
  obbligatorio boolean DEFAULT true,
  CONSTRAINT linee_mobili_html_fields_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE linee_mobili_html_fields
  OWNER TO postgres;
GRANT ALL ON TABLE linee_mobili_html_fields TO postgres;
GRANT SELECT ON TABLE linee_mobili_html_fields TO usr_ro;
------------------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE linee_mobili_fields_value
(
  id serial NOT NULL,
  id_rel_stab_linea integer,
  id_linee_mobili_html_fields integer,
  valore_campo text,
  indice integer,
  enabled boolean DEFAULT true,
  id_utente_inserimento integer,
  data_inserimento timestamp without time zone DEFAULT now(),
  CONSTRAINT linee_mobili_fields_value_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE linee_mobili_fields_value
  OWNER TO postgres;
GRANT ALL ON TABLE linee_mobili_fields_value TO postgres;
GRANT SELECT ON TABLE linee_mobili_fields_value TO usr_ro;
------------------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE lookup_tipo_alimento_distributore
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_tipo_alimento_distributore_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_tipo_alimento
  OWNER TO postgres;
------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO lookup_tipo_alimento_distributore(code, description, enabled) VALUES (1, 'Alimenti e/o bevande a temperatura ambiente o refrigerati', true);
INSERT INTO lookup_tipo_alimento_distributore(code, description, enabled) VALUES (2, 'Bevande calde', true);
------------------------------------------------------------------------------------------------------------------------------------------

INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (813, 'targa', 'text', 'targa', 1, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio, tabella_lookup, multiple)
  VALUES (813, 'tipo_veicolo', 'select', 'tipo veicolo', 2, true, 'lookup_tipo_mobili',false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (813, 'carta_circolazione', 'allegato', 'carta circolazione', 3, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (813, 'data_acquisto', 'data', 'data acquisto', 4, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (813, 'data_dismissione', 'data', 'data dismissione', 5, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (814, 'targa', 'text', 'targa', 1, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio, tabella_lookup, multiple)
  VALUES (814, 'tipo_veicolo', 'select', 'tipo veicolo', 2, true, 'lookup_tipo_mobili',false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (814, 'carta_circolazione', 'allegato', 'carta circolazione', 3, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (814, 'data_acquisto', 'data', 'data acquisto', 4, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (814, 'data_dismissione', 'data', 'data dismissione', 5, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (815, 'targa', 'text', 'targa', 1, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio, tabella_lookup, multiple)
  VALUES (815, 'tipo_veicolo', 'select', 'tipo veicolo', 2, true, 'lookup_tipo_mobili', false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (815, 'carta_circolazione', 'allegato', 'carta circolazione', 3, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (815, 'data_acquisto', 'data', 'data acquisto', 4, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (815, 'data_dismissione', 'data', 'data dismissione', 5, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (816, 'targa', 'text', 'targa', 1, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio, tabella_lookup, multiple)
  VALUES (816, 'tipo_veicolo', 'select', 'tipo veicolo', 2, true, 'lookup_tipo_mobili',false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (816, 'carta_circolazione', 'allegato', 'carta circolazione', 3, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (816, 'data_acquisto', 'data', 'data acquisto', 4, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (816, 'data_dismissione', 'data', 'data dismissione', 5, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (819, 'targa', 'text', 'targa', 1, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio, tabella_lookup, multiple)
  VALUES (819, 'tipo_veicolo', 'select', 'tipo veicolo', 2, true, 'lookup_tipo_mobili',false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (819, 'carta_circolazione', 'allegato', 'carta circolazione', 3, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (819, 'data_acquisto', 'data', 'data acquisto', 4, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (819, 'data_dismissione', 'data', 'data dismissione', 5, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (820, 'targa', 'text', 'targa', 1, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio, tabella_lookup, multiple)
  VALUES (820, 'tipo_veicolo', 'select', 'tipo veicolo', 2, true, 'lookup_tipo_mobili',false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (820, 'carta_circolazione', 'allegato', 'carta circolazione', 3, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (820, 'data_acquisto', 'data', 'data acquisto', 4, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (820, 'data_dismissione', 'data', 'data dismissione', 5, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (822, 'targa', 'text', 'targa', 1, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio, tabella_lookup, multiple)
  VALUES (822, 'tipo_veicolo', 'select', 'tipo veicolo', 2, true, 'lookup_tipo_mobili',false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (822, 'carta_circolazione', 'allegato', 'carta circolazione', 3, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (822, 'data_acquisto', 'data', 'data acquisto', 4, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (822, 'data_dismissione', 'data', 'data dismissione', 5, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (823, 'targa', 'text', 'targa', 1, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio, tabella_lookup, multiple)
  VALUES (823, 'tipo_veicolo', 'select', 'tipo veicolo', 2, true, 'lookup_tipo_mobili',false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (823, 'carta_circolazione', 'allegato', 'carta circolazione', 3, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (823, 'data_acquisto', 'data', 'data acquisto', 4, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (823, 'data_dismissione', 'data', 'data dismissione', 5, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (824, 'targa', 'text', 'targa', 1, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio, tabella_lookup, multiple)
  VALUES (824, 'tipo_veicolo', 'select', 'tipo veicolo', 2, true, 'lookup_tipo_mobili',false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (824, 'carta_circolazione', 'allegato', 'carta circolazione', 3, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (824, 'data_acquisto', 'data', 'data acquisto', 4, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (824, 'data_dismissione', 'data', 'data dismissione', 5, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (825, 'targa', 'text', 'targa', 1, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio, tabella_lookup, multiple)
  VALUES (825, 'tipo_veicolo', 'select', 'tipo veicolo', 2, true, 'lookup_tipo_mobili',false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (825, 'carta_circolazione', 'allegato', 'carta circolazione', 3, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (825, 'data_acquisto', 'data', 'data acquisto', 4, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (825, 'data_dismissione', 'data', 'data dismissione', 5, false);


INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (794, 'matricola', 'text', 'matricola', 1, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (794, 'data_installazione', 'data', 'data installazione', 2, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (794, 'ubicazione', 'text', 'ubicazione', 3, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (794, 'indirizzo', 'text', 'indirizzo', 4, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (794, 'comune', 'text', 'comune', 5, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (794, 'provincia', 'text', 'provincia', 6, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (794, 'cap', 'text', 'cap', 7, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (794, 'asl_distributore', 'text', 'asl distributore', 8, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio, tabella_lookup, multiple)
  VALUES (794, 'alimento_distribuito', 'select', 'alimento distribuito', 9, true, 'lookup_tipo_alimento_distributore', false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (798, 'matricola', 'text', 'matricola', 1, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (798, 'data_installazione', 'data', 'data installazione', 2, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (798, 'ubicazione', 'text', 'ubicazione', 3, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (798, 'indirizzo', 'text', 'indirizzo', 4, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (798, 'comune', 'text', 'comune', 5, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (798, 'provincia', 'text', 'provincia', 6, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (798, 'cap', 'text', 'cap', 7, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (798, 'asl_distributore', 'text', 'asl distributore', 8, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio, tabella_lookup, multiple)
  VALUES (798, 'alimento_distribuito', 'select', 'alimento distribuito', 9, true, 'lookup_tipo_alimento_distributore', false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (799, 'matricola', 'text', 'matricola', 1, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (799, 'data_installazione', 'data', 'data installazione', 2, true);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (799, 'ubicazione', 'text', 'ubicazione', 3, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (799, 'indirizzo', 'text', 'indirizzo', 4, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (799, 'comune', 'text', 'comune', 5, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (799, 'provincia', 'text', 'provincia', 6, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (799, 'cap', 'text', 'cap', 7, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio)
  VALUES (799, 'asl_distributore', 'text', 'asl distributore', 8, false);
INSERT INTO linee_mobili_html_fields(id_linea, nome_campo, tipo_campo, label_campo, ordine_campo, obbligatorio, tabella_lookup, multiple)
  VALUES (799, 'alimento_distribuito', 'select', 'alimento distribuito', 9, true, 'lookup_tipo_alimento_distributore', false);

------------------------------------------------------------------------------------------------------------------------------------------
CREATE EXTENSION tablefunc; 

DROP FUNCTION get_dettaglio_linea_mobile_automezzi(stabid integer);

CREATE OR REPLACE FUNCTION get_dettaglio_linea_mobile_automezzi(stabid integer)
  RETURNS TABLE(targa_ret text, descrizione_ret text, carta_circolazione_ret text) AS
$BODY$
DECLARE
	r RECORD;	
BEGIN
	FOR targa_ret, descrizione_ret, carta_circolazione_ret
	in

	select
distinct targa, ltm.description as descrizione, carta_circolazione 
from
crosstab(
  'select v.indice, f.nome_campo, v.valore_campo
   from linee_mobili_html_fields f
   inner join linee_mobili_fields_value v on v.id_linee_mobili_html_fields = f.id 
   join opu_relazione_stabilimento_linee_produttive rel on rel.id = v.id_rel_stab_linea 
   where (f.nome_campo = ''targa'' or f.nome_campo = ''tipo_veicolo'' or f.nome_campo = ''carta_circolazione'') and v.enabled and rel.id_stabilimento = ' || stabid || '
   order by 1',
   'select distinct f.nome_campo  from linee_mobili_html_fields f
   inner join linee_mobili_fields_value v on v.id_linee_mobili_html_fields = f.id 
   where (f.nome_campo = ''targa'' or f.nome_campo = ''tipo_veicolo'' or f.nome_campo = ''carta_circolazione'') '
   )
AS lista(indice integer, tipo_veicolo integer, targa text, carta_circolazione text) 
join lookup_tipo_mobili ltm on ltm.code = lista.tipo_veicolo::integer
LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION get_dettaglio_linea_mobile_automezzi(integer)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION get_dettaglio_linea_mobile_automezzi(integer) TO postgres;
GRANT EXECUTE ON FUNCTION get_dettaglio_linea_mobile_automezzi(integer) TO public;
---------------------------------------------------------------------------------------------------------------------------
DROP FUNCTION get_dettaglio_linea_mobile_distributori(stabid integer);

CREATE OR REPLACE FUNCTION get_dettaglio_linea_mobile_distributori(stabid integer)
  RETURNS TABLE(matricola_ret text, alimento_distribuito_ret text) AS
$BODY$
DECLARE
	r RECORD;	
BEGIN
	FOR matricola_ret, alimento_distribuito_ret
	in

	select
distinct matricola, ltm.description as alimento_distribuito 
from
crosstab(
  'select v.indice, f.nome_campo, v.valore_campo
   from linee_mobili_html_fields f
   inner join linee_mobili_fields_value v on v.id_linee_mobili_html_fields = f.id 
   join opu_relazione_stabilimento_linee_produttive rel on rel.id = v.id_rel_stab_linea 
   where (f.nome_campo = ''matricola'' or f.nome_campo = ''alimento_distribuito'') and v.enabled and rel.id_stabilimento = ' || stabid || '
   order by 1,2')
AS lista(indice integer, valore_tipo_alimento text,matricola text) join lookup_tipo_alimento_distributore ltm on ltm.code = lista.valore_tipo_alimento::integer
LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION get_dettaglio_linea_mobile_distributori(integer)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION get_dettaglio_linea_mobile_distributori(integer) TO postgres;
GRANT EXECUTE ON FUNCTION get_dettaglio_linea_mobile_distributori(integer) TO public;
---------------------------------------------------------------------------------------------------------------------------
-- CAMPI SCHEDA CENTRALIZZATA:
-- NOME LABEL:
Campi Att. Mobile Automezzi
-- CAMPO SELECT:
'<b>TARGA:</b> ' || targa_ret || '&nbsp;&nbsp;-&nbsp;&nbsp;<b>TIPO AUTOVEICOLO: </b>' ||  descrizione_ret || 
'&nbsp;&nbsp;-&nbsp;&nbsp;<b>CARTA DI CIRCOLAZIONE: </b>' || '<a href="GestioneAllegatiUploadSuap.do?command=DownloadPDF&codDocumento=' ||  
carta_circolazione_ret ||'&nomeDocumento='|| targa_ret ||'">Download</a>' 
-- CAMPO FROM
get_dettaglio_linea_mobile_automezzi(#stabid#)
-- CAMPO WHERE
1=1

-- NOME LABEL:
Campi Att. Mobile Distributori
-- CAMPO SELECET:
'<b>MATRICOLA:</b> ' || matricola_ret || '&nbsp;&nbsp;-&nbsp;&nbsp;<b>TIPO ALIMENTO DISTRIBUITO: </b>' ||  alimento_distribuito_ret  
-- CAMPO FROM:
get_dettaglio_linea_mobile_distributori(#stabid#)
-- CAMPO WHERE:
1=1
------------------------------------------------------------------------------------------------------------------------------
Rinominare la tabella opu_stabilimento_mobile in opu_stabilimento_mobile_
Settare il current_value di linee_mobili_fields_value_pkey a 1000
update linee_mobili_fields_value set id=(id+1000)
------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW opu_stabilimento_mobile AS 
(select l.id, rel.id_stabilimento, targa, tipo_veicolo as tipo, carta_circolazione as carta, l.enabled
from
crosstab(
  'select distinct(v.indice,v.id_rel_stab_linea), f.nome_campo, v.valore_campo
   from linee_mobili_html_fields f
   inner join linee_mobili_fields_value v on v.id_linee_mobili_html_fields = f.id  
   where (f.nome_campo = ''targa'' or f.nome_campo = ''tipo_veicolo'' or f.nome_campo = ''carta_circolazione'') and v.enabled 
   order by (v.indice,v.id_rel_stab_linea)',
   'select distinct f.nome_campo  from linee_mobili_html_fields f
   inner join linee_mobili_fields_value v on v.id_linee_mobili_html_fields = f.id 
   where (f.nome_campo = ''targa'' or f.nome_campo = ''tipo_veicolo'' or f.nome_campo = ''carta_circolazione'') '
   )
AS lista(indice text, tipo_veicolo integer, targa text, carta_circolazione text) 
join linee_mobili_fields_value l on (l.enabled and l.valore_campo = lista.targa) 
join opu_relazione_stabilimento_linee_produttive rel on rel.id = l.id_rel_stab_linea)
union 
(select id, id_stabilimento, targa, tipo, carta, enabled from opu_stabilimento_mobile_  where enabled and importato=false order by id desc);ALTER TABLE opu_stabilimento_mobile
  OWNER TO postgres;
GRANT ALL ON TABLE opu_stabilimento_mobile TO postgres;
GRANT SELECT ON TABLE opu_stabilimento_mobile TO usr_ro;
---------------------------------------------------------------------------------------------------------------------------------
Aggiungere la colonna "importato" di tipo boolean con valore di default false.
Lanciare lo script "ImportTarghrPregresse.jar" con il comando: java -jar ImportTarghrPregresse.jar IP_DATABASE
