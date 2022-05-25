DROP TABLE IF EXISTS oia_nodo;
CREATE TABLE oia_nodo (
    id serial PRIMARY KEY,
	id_padre integer,							-- FK a lookup_site_id
	id_asl integer NOT NULL,							-- FK a lookup_site_id
	nome varchar(50) NOT NULL,
	descrizione_lunga varchar(500),
	n_livello smallint NOT NULL,						-- Il dominio è : 1,2,3
	tipologia_nodo varchar(50) NOT NULL,				-- Il dominio è : "SIAM", "Veterinaria"
	id_utente integer,									-- FK alla vista "v_user_info"
	n_controlli_ufficiali integer,
	n_cu_campioni integer,
	
	entered timestamp with time zone DEFAULT now(),
	entered_by integer,
	modified timestamp with time zone DEFAULT now(),
	modified_by integer,
	trashed_date timestamp with time zone
);

DROP TABLE IF EXISTS oia_tipologia_competenza_nodo;
CREATE TABLE oia_tipologia_competenza_nodo (
	id serial PRIMARY KEY,
	id_nodo_oia integer NOT NULL,						-- FK a oia_tipologia_competenza_nodo
	id_lookup_tipologia_competenza integer NOT NULL, 	-- FK a "nodo_macrocategorie" o "comuni", in funzione dell'atributo discriminante
	discriminante integer NOT NULL,	 					-- (es. nome della tabella da cui prendere il valore. Per il momento può valere "nodo_macrocategorie" o "comuni")
	
	entered timestamp with time zone DEFAULT now(),
	entered_by integer,
	modified timestamp with time zone DEFAULT now(),
	modified_by integer,
	trashed_date timestamp with time zone
);

DROP TABLE IF EXISTS oia_cfg_ruoli_ammessi;
CREATE TABLE oia_cfg_ruoli_ammessi (
	id serial PRIMARY KEY,
	livello smallint NOT NULL, 							-- Il dominio è : 1,2,3
	id_ruolo_responsabile integer,						-- FK al campo role_id della tabella "role"
	id_ruolo_componente integer							-- FK al campo role_id della tabella "role"
);

DROP TABLE IF EXISTS lookup_tipologia_competenza;
CREATE TABLE lookup_tipologia_competenza
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_tipologia_competenza_pkey PRIMARY KEY (code)
);

insert into lookup_tipologia_competenza values(1,'Comune',FALSE,10,TRUE);
insert into lookup_tipologia_competenza values(2,'Macrocategorie',FALSE,20,TRUE);

DROP VIEW IF EXISTS v_comuni;
CREATE VIEW v_comuni AS (
	select codiceistatcomune as id, comune as descrizione
	from comuni
)


INSERT INTO public.permission_category(
            category_id, category, description, "level", enabled, active, 
            folders, lookups, viewpoints, categories, scheduled_events, object_events, 
            reports, webdav, logos, constant, action_plans, custom_list_views)
    VALUES (108, 'OIA', 'Organizzazione Interna ASL', 1000, true, TRUE,TRUE,TRUE,FALSE,FALSE,FALSE,FALSE,TRUE,TRUE,FALSE,215454,TRUE,FALSE);



INSERT INTO public.permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (1075,108,'oia',TRUE,FALSE,FALSE,FALSE,'OIA:Modulo',900,TRUE,FALSE,FALSE);


INSERT INTO public.permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (1089,108,'oia-espandi',TRUE,FALSE,FALSE,FALSE,'OIA:VISUALIZZAZZIONE STRUTTURA ASL',900,TRUE,FALSE,FALSE);



    

INSERT INTO public.permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (1090,108,'oia-modellazione_level1',TRUE,FALSE,FALSE,FALSE,'OIA:MODELLAZIONE DEL PRIMO LIVELLO DELLA STRUTTURA ASL',900,TRUE,FALSE,FALSE);

    
INSERT INTO public.permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (1091,108,'oia-modellazione_level2',TRUE,FALSE,FALSE,FALSE,'OIA:MODELLAZIONE DEL SECONDO LIVELLO DELLA STRUTTURA ASL',900,TRUE,FALSE,FALSE);



INSERT INTO public.permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (1092,108,'oia-modellazione_level3',TRUE,FALSE,FALSE,FALSE,'OIA:MODELLAZIONE DEL TERZO LIVELLO DELLA STRUTTURA ASL',900,TRUE,FALSE,FALSE);
INSERT INTO public.permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (1095,108,'oia-modellazione_level4',TRUE,TRUE,TRUE,FALSE,'OIA:MODELLAZIONE DEL quarto LIVELLO DELLA STRUTTURA ASL',900,TRUE,FALSE,FALSE);


