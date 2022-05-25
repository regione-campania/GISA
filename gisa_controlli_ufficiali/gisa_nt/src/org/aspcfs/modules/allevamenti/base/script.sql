CREATE TABLE import_capi
(
  data timestamp without time zone,
  riepilogo character varying,
  errori character varying,
  record_inseriti character varying,
  id serial NOT NULL,
  enabled boolean
)
WITH (OIDS=FALSE);
ALTER TABLE import_allevamenti OWNER TO postgres;


CREATE TABLE lookup_tipologia_struttura
(
  code serial NOT NULL,
  description character varying(300),
  default_item boolean DEFAULT false,
  "level" integer,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_tipo_allevamento_pkey PRIMARY KEY (code)
)
WITH (OIDS=FALSE);
ALTER TABLE lookup_tipologia_struttura OWNER TO postgres;
INSERT INTO lookup_tipologia_struttura(code, description, default_item, "level", enabled) VALUES (1,'ALLEVAMENTO',FALSE,0,TRUE);
INSERT INTO lookup_tipologia_struttura(code, description, default_item, "level", enabled) VALUES (2,'CENTRO MATERIALE GENETICO',FALSE,1,TRUE);
INSERT INTO lookup_tipologia_struttura(code, description, default_item, "level", enabled)VALUES (3,'CENTRO RACCOLTA',FALSE,2,TRUE); 
INSERT INTO lookup_tipologia_struttura(code, description, default_item, "level", enabled) VALUES (4,'PUNTO DI SOSTA',FALSE,3,TRUE);
INSERT INTO lookup_tipologia_struttura(code, description, default_item, "level", enabled) VALUES (5,'STABULARIO',FALSE,4,TRUE);
INSERT INTO lookup_tipologia_struttura(code, description, default_item, "level", enabled) VALUES (6,'STALLA DI SOSTA',FALSE,5,TRUE);


CREATE TABLE lookup_orientamento_produttivo
(
  code serial NOT NULL,
  description character varying(300),
  default_item boolean DEFAULT false,
  "level" integer,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_orientamento_produttivo_pkey PRIMARY KEY (code)
)

insert into lookup_orientamento_produttivo (code, description, default_item, "level", enabled) VALUES (1,'MISTO',FALSE,0,TRUE);
insert into lookup_orientamento_produttivo (code, description, default_item, "level", enabled) VALUES (2,'CARNE',FALSE,1,TRUE);
insert into lookup_orientamento_produttivo (code, description, default_item, "level", enabled) VALUES (3,'LATTE',FALSE,2,TRUE);

insert into lookup_orientamento_produttivo (code, description, default_item, "level", enabled) VALUES (4,'CENTRO RACCOLTA SPERMA',FALSE,3,TRUE);
insert into lookup_orientamento_produttivo (code, description, default_item, "level", enabled) VALUES (5,'GRUPPO RACCOLTA EMBRIONI',FALSE,4,TRUE);
insert into lookup_orientamento_produttivo (code, description, default_item, "level", enabled) VALUES (6,'CENTRO MAGAZZINAGGIO',FALSE,5,TRUE);

insert into lookup_orientamento_produttivo (code, description, default_item, "level", enabled) VALUES (7,'CENTRO GENETICO',FALSE,6,TRUE);
insert into lookup_orientamento_produttivo (code, description, default_item, "level", enabled) VALUES (8,'CENTRO GENETICO E QUARANTENA',FALSE,7,TRUE);
insert into lookup_orientamento_produttivo (code, description, default_item, "level", enabled) VALUES (9,'CENTRO QUARANTENA',FALSE,8,TRUE);



insert into lookup_orientamento_produttivo (code, description, default_item, "level", enabled) VALUES (10,'CENTRO RACCOLTA',FALSE,9,TRUE);
insert into lookup_orientamento_produttivo (code, description, default_item, "level", enabled) VALUES (11,'DA ALLEVAMENTO',FALSE,10,TRUE);
insert into lookup_orientamento_produttivo (code, description, default_item, "level", enabled) VALUES (12,'DA MACELLO',FALSE,11,TRUE);



insert into lookup_orientamento_produttivo (code, description, default_item, "level", enabled) VALUES (13,'PUNTO RACCOLTA',FALSE,12,TRUE);
insert into lookup_orientamento_produttivo (code, description, default_item, "level", enabled) VALUES (14,'DA ALLEVAMENTO/MACELLO',FALSE,13,TRUE);
insert into lookup_orientamento_produttivo (code, description, default_item, "level", enabled) VALUES (15,'STABULARIO',FALSE,14,TRUE);


------------------- 04 08 20010---------

CREATE TABLE lookup_categoria_specie_allevata
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  short_description character varying(300),
  default_item boolean DEFAULT false,
  "level" integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_categoria_specie_allevata_pkey PRIMARY KEY (code)
)
WITH (OIDS=FALSE);
ALTER TABLE lookup_categoria_specie_allevata OWNER TO postgres;


ALTER TABLE LOOKUP_SPECIE_ALLEVATA ADD codice_categoria integer


ALTER TABLE public.lookup_orientamento_produttivo ADD COLUMN tipo_struttura text;
ALTER TABLE public.lookup_orientamento_produttivo ADD COLUMN enabled_ovini_caprini boolean;
ALTER TABLE public.lookup_orientamento_produttivo ADD COLUMN enabled_bovini_bufalini boolean;
ALTER TABLE public.lookup_orientamento_produttivo ADD COLUMN enabled_equini boolean;
ALTER TABLE public.lookup_orientamento_produttivo ADD COLUMN enabled_avicoli boolean;
ALTER TABLE public.lookup_orientamento_produttivo ADD COLUMN enabled_suini boolean;


ALTER TABLE public.lookup_tipologia_struttura ADD COLUMN enabled_bovini_bufalini boolean;
ALTER TABLE public.lookup_tipologia_struttura ADD COLUMN enabled_equini boolean;
ALTER TABLE public.lookup_tipologia_struttura ADD COLUMN enabled_suini boolean;
ALTER TABLE public.lookup_tipologia_struttura ADD COLUMN enabled_avicoli boolean;
ALTER TABLE public.lookup_tipologia_struttura ADD COLUMN enabled_ovini_caprini boolean;