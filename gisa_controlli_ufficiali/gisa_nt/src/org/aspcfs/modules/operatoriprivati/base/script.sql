/*tipologia per organization e' 13*/

CREATE TABLE lookup_operatoriprivati_size
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_operatoriprivati_size_pkey PRIMARY KEY (code)
)
WITH (OIDS=FALSE);
ALTER TABLE lookup_operatoriprivati_size OWNER TO postgres;


CREATE TABLE lookup_operatoriprivati_stage
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  entered timestamp(3) without time zone NOT NULL DEFAULT now(),
  modified timestamp(3) without time zone NOT NULL DEFAULT now(),
  CONSTRAINT lookup_operatoriprivati_stage_pkey PRIMARY KEY (code)
)
WITH (OIDS=FALSE);
ALTER TABLE lookup_operatoriprivati_stage OWNER TO postgres;


CREATE TABLE lookup_operatoriprivati_types
(
  code serial NOT NULL,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_operatoriprivati_types_pkey PRIMARY KEY (code)
)
WITH (OIDS=FALSE);
ALTER TABLE lookup_operatoriprivati_types OWNER TO postgres;

CREATE TABLE operatoriprivati_type_levels
(
  org_id integer NOT NULL,
  type_id integer NOT NULL,
  "level" integer NOT NULL,
  entered timestamp(3) without time zone NOT NULL DEFAULT now(),
  modified timestamp(3) without time zone NOT NULL DEFAULT now(),
  CONSTRAINT operatoriprivati_type_levels_org_id_fkey FOREIGN KEY (org_id)
      REFERENCES organization (org_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT operatoriprivati_type_levels_type_id_fkey FOREIGN KEY (type_id)
      REFERENCES lookup_operatoriprivati_types (code) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (OIDS=FALSE);
ALTER TABLE operatoriprivati_type_levels OWNER TO postgres;

-- DROP TABLE lookup_abusivismi_size;

INSERT INTO permission_category(
            category_id, category, description, "level", enabled, active, 
            folders, lookups, viewpoints, categories, scheduled_events, object_events, 
            reports, webdav, logos, constant, action_plans, custom_list_views)
    VALUES (102,'Operatori Privati','Modulo Operatori Privati',222222,TRUE,TRUE,FALSE,
	FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,FALSE,5464,FALSE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889184,102,'operatoriprivati',TRUE,TRUE,TRUE,TRUE,'Accesso Modulo Operatori privati',10,TRUE,TRUE,FALSE);

 INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES ( 889409,102,'operatoriprivati-operatoriprivati',TRUE,TRUE,TRUE,TRUE,'Operatori privati',15,TRUE,TRUE,FALSE);

INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889410,102,'operatoriprivati-operatoriprivati-folders',TRUE,TRUE,TRUE,TRUE,'Cartelle',20,TRUE,TRUE,FALSE);

 INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889415,102,'operatoriprivati-operatoriprivati-documents',TRUE,TRUE,TRUE,TRUE,'Documenti',40,TRUE,TRUE,FALSE);

 INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889417,102,'operatoriprivati-operatoriprivati-history',TRUE,TRUE,TRUE,TRUE,'Storia',50,TRUE,TRUE,FALSE);

 INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889420,102,'operatoriprivati-operatoriprivati-campioni',TRUE,TRUE,TRUE,TRUE,'Campioni',63,TRUE,TRUE,FALSE);

 INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889421,102,'operatoriprivati-operatoriprivati-campioni-documents',TRUE,TRUE,TRUE,TRUE,'Documenti Campioni',65,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889422,102,'operatoriprivati-operatoriprivati-campioni-history',TRUE,TRUE,TRUE,TRUE,'Storia Campioni',70,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889423,102,'operatoriprivati-operatoriprivati-vigilanza',TRUE,TRUE,TRUE,TRUE,'Controlli Ufficiali',73,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889424,102,'operatoriprivati-operatoriprivati-vigilanza-documents',TRUE,TRUE,TRUE,TRUE,'Documenti Controlli Ufficiali',75,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889425,102,'operatoriprivati-operatoriprivati-vigilanza-history',TRUE,TRUE,TRUE,TRUE,'Storia Controlli Ufficiali',80,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889427,102,'operatoriprivati-operatoriprivati-sanzioni-documents',TRUE,TRUE,TRUE,TRUE,'Documenti Sanzioni',85,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889428,102,'operatoriprivati-operatoriprivati-sanzioni-history',TRUE,TRUE,TRUE,TRUE,'Storia Sanzioni',90,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889030,102,'operatoriprivati-operatoriprivati-nonconformita',TRUE,TRUE,TRUE,TRUE,'Non Conformita oeratori privati',180,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889031,102,'operatoriprivati-operatoriprivati-nonconformita-documents',TRUE,TRUE,TRUE,TRUE,'Documenti non conformita operatori privati',180,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889032,102,'operatoriprivati-operatoriprivati-nonconformita-history',TRUE,TRUE,TRUE,TRUE,'Storia operatori privati',180,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889033,102,'operatoriprivati-operatoriprivati-reati',TRUE,TRUE,TRUE,TRUE,'Reati operatori privati',180,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889034,102,'operatoriprivati-operatoriprivati-reati-documents',TRUE,TRUE,TRUE,TRUE,'Documenti Reati operatori privati',180,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889035,102,'operatoriprivati-operatoriprivati-reati-history',TRUE,TRUE,TRUE,TRUE,'Storia Reati operatori privati',180,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889036,102,'operatoriprivati-operatoriprivati-sequestri',TRUE,TRUE,TRUE,TRUE,'sequestri operativi privati',180,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889037,102,'operatoriprivati-operatoriprivati-sequestri-documents',TRUE,TRUE,TRUE,TRUE,'Documenti seaquestri opertori privati',180,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889038,102,'operatoriprivati-operatoriprivati-sequestri-history',TRUE,TRUE,TRUE,TRUE,'Storia sequestri operatori privati',180,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889039,102,'operatoriprivati-operatoriprivati-sanzioni',TRUE,TRUE,TRUE,TRUE,'Sanzioni operatori privati',180,FALSE,TRUE,FALSE);
    
    
    INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889040,102,'operatoriprivati-operatoriprivati-tamponi',TRUE,TRUE,TRUE,TRUE,'tamponi operativi privati',180,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889041,102,'operatoriprivati-operatoriprivati-tamponi-documents',TRUE,TRUE,TRUE,TRUE,'Documenti tamponi opertori privati',180,TRUE,TRUE,FALSE);
INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (889042,102,'operatoriprivati-operatoriprivati-tamponi-history',TRUE,TRUE,TRUE,TRUE,'Storia tamponi operatori privati',180,TRUE,TRUE,FALSE);

