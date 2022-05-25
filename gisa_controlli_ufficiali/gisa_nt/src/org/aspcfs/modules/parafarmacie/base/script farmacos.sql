INSERT INTO permission_category (category_id, category, description, level, enabled, active, folders, lookups, viewpoints, categories, scheduled_events, object_events, reports, webdav, logos, constant, action_plans, custom_list_views) VALUES (50, 'Farmacosorveglianza', NULL, 800, true, true, true, true, false, false, false, false, true, true, false, 13144167, true, false);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active, viewpoints) VALUES (362, 50, 'parafarmacie-parafarmacie-history', true, true, true, true, 'Storia Farmacosorveglianza', 802, true, true, false);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active, viewpoints) VALUES (363, 50, 'parafarmacie', true, true, true, true, 'Accesso Farmacosorveglianza', 15, true, true, false);
INSERT INTO permission (permission_id, category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active, viewpoints) VALUES (364, 50, 'parafarmacie-parafarmacie', true, true, true, true, 'Farmacosorveglianza', 15, true, true, false);



CREATE TABLE farmaci
(
  id_farmaco serial NOT NULL,
  id integer NOT NULL,
  nome_commerciale character varying(50),
  principio_attivo character varying(50),
  tipo_somministrazione character varying(50),
  quantita integer,
  note1 text,
  note2 text,
  entered timestamp(3) without time zone NOT NULL DEFAULT now(),
  enteredby integer NOT NULL,
  modified timestamp(3) without time zone NOT NULL DEFAULT now(),
  modifiedby integer NOT NULL,
  trashed_date timestamp(3) without time zone,
  CONSTRAINT farmaci_pkey PRIMARY KEY (id_farmaco)
)
WITH (OIDS=FALSE);
ALTER TABLE farmaci OWNER TO postgres;


CREATE TABLE farmaci2
(
  id_farmaco integer NOT NULL DEFAULT nextval(('farmaco_id_seq'::text)::regclass),
  nome_commerciale character varying(50),
  principio_attivo character varying(50),
  tipo_somministrazione character varying(50),
  quantita integer,
  note1 text,
  note2 text,
  entered timestamp(3) without time zone NOT NULL DEFAULT now(),
  enteredby integer NOT NULL,
  modified timestamp(3) without time zone NOT NULL DEFAULT now(),
  modifiedby integer NOT NULL,
  trashed_date timestamp(3) without time zone,
  prezzo double precision DEFAULT 0,
  id_tiposomministrazione integer,
  id_confezione integer,
  ditta character varying(80),
  gruppo_merceologico character varying(80),
  id_prescrizione integer
)
WITH (OIDS=FALSE);
ALTER TABLE farmaci2 OWNER TO postgres;



CREATE TABLE farmacie
(
  id_farmacia integer NOT NULL DEFAULT nextval(('farmacia_id_seq'::text)::regclass),
  ragione_sociale character varying(50),
  site_id integer,
  indirizzo character varying(80),
  "citta'" character varying(80),
  provincia character varying(80),
  stato character varying(80),
  postalcode character varying(12),
  latitude double precision DEFAULT 0,
  longitude double precision DEFAULT 0,
  note1 text,
  note2 text,
  entered timestamp(3) without time zone NOT NULL DEFAULT now(),
  enteredby integer NOT NULL,
  modified timestamp(3) without time zone NOT NULL DEFAULT now(),
  modifiedby integer NOT NULL,
  trashed_date timestamp(3) without time zone,
  citta character varying(80),
  id_prescrizione integer
)
WITH (OIDS=FALSE);
ALTER TABLE farmacie OWNER TO postgres;



CREATE TABLE prescrizioni2
(
  id_prescrizione integer NOT NULL DEFAULT nextval(('prescrizione_id_seq'::text)::regclass),
  site_id integer,
  data_prescrizione timestamp(3) without time zone,
  data_dispensazione timestamp(3) without time zone,
  id_farmacia integer,
  id_farmaco1 integer,
  quantita_farmaco1 integer,
  id_farmaco2 integer,
  quantita_farmaco2 integer,
  id_veterinario integer,
  org_id integer,
  note1 text,
  note2 text,
  entered timestamp(3) without time zone NOT NULL DEFAULT now(),
  enteredby integer NOT NULL,
  modified timestamp(3) without time zone NOT NULL DEFAULT now(),
  modifiedby integer NOT NULL,
  trashed_date timestamp(3) without time zone
)
WITH (OIDS=FALSE);
ALTER TABLE prescrizioni2 OWNER TO postgres;



CREATE TABLE veterinari2
(
  id_veterinario integer NOT NULL DEFAULT nextval(('veterinario_id_seq'::text)::regclass),
  nome character varying(50),
  cognome character varying(50),
  codice_fiscale character varying(16),
  id_tipologia integer,
  indirizzo character varying(80),
  "citta'" character varying(80),
  provincia character varying(80),
  stato character varying(80),
  postalcode character varying(12),
  note1 text,
  note2 text,
  entered timestamp(3) without time zone NOT NULL DEFAULT now(),
  enteredby integer NOT NULL,
  modified timestamp(3) without time zone NOT NULL DEFAULT now(),
  modifiedby integer NOT NULL,
  trashed_date timestamp(3) without time zone,
  citta character varying(80),
  site_id integer
)
WITH (OIDS=FALSE);
ALTER TABLE veterinari2 OWNER TO postgres;
