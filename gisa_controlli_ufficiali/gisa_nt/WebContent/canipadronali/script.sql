

-- Table: iuv_campioni_valutazione_comportamentale

-- DROP TABLE iuv_campioni_valutazione_comportamentale;

CREATE TABLE iuv_campioni_valutazione_comportamentale
(
  id serial NOT NULL,
  id_campione integer,
  id_stato_generale integer,
  id_tolleranza integer,
  id_adottabilita integer,
  CONSTRAINT iuv_campioni_valutazione_comportamentale_pkey PRIMARY KEY (id ),
  CONSTRAINT iuv_campioni_valutazione_comportamentale_id_campione_fkey FOREIGN KEY (id_campione)
      REFERENCES ticket (ticketid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE iuv_campioni_valutazione_comportamentale
  OWNER TO postgres;


-- Table: iuv_campioni_valutazione_comportamentale_anomalie

-- DROP TABLE iuv_campioni_valutazione_comportamentale_anomalie;

CREATE TABLE iuv_campioni_valutazione_comportamentale_anomalie
(
  id serial NOT NULL,
  id_iuv_valutazione integer,
  id_anomalia integer,
  CONSTRAINT iuv_campioni_valutazione_comportamentale_anomalie_pkey PRIMARY KEY (id ),
  CONSTRAINT iuv_campioni_valutazione_comportamental_id_iuv_valutazione_fkey FOREIGN KEY (id_iuv_valutazione)
      REFERENCES iuv_campioni_valutazione_comportamentale (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE iuv_campioni_valutazione_comportamentale_anomalie
  OWNER TO postgres;




CREATE TABLE lookup_campioni_iuv_stato_generale
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_campioni_iuv_stato_generale_pkey PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_campioni_iuv_stato_generale
  OWNER TO postgres;


CREATE TABLE lookup_campioni_iuv_anomalie_comportamentali
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_campioni_iuv_anomalie_comportamentali_pkey PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_campioni_iuv_anomalie_comportamentali
  OWNER TO postgres;

CREATE TABLE lookup_campioni_iuv_tolleranza_manipolazioni
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_campioni_iuv_tolleranza_manipolazioni_pkey PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_campioni_iuv_tolleranza_manipolazioni
  OWNER TO postgres;

CREATE TABLE lookup_campioni_iuv_adottabilita
(
  code serial NOT NULL,
  description character varying(300) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_campioni_iuv_adottabilita_pkey PRIMARY KEY (code )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_campioni_iuv_adottabilita
  OWNER TO postgres;

insert into lookup_campioni_iuv_stato_generale (description,level) values ('SCADENTE',0) ;
insert into lookup_campioni_iuv_stato_generale (description,level) values ('SUFFICIENTE',0) ;
insert into lookup_campioni_iuv_stato_generale (description,level) values ('BUONO',0) ;
insert into lookup_campioni_iuv_stato_generale (description,level) values ('OTTIMO',0) ;


insert into lookup_campioni_iuv_anomalie_comportamentali (description,level) values ('PRESENTI',0) ;
insert into lookup_campioni_iuv_anomalie_comportamentali (description,level) values ('PROBLEMI GESTIONALI',0) ;
insert into lookup_campioni_iuv_anomalie_comportamentali (description,level) values ('DISORDINI DI SOCIALIZZAZIONE VS UOMO',0) ;
insert into lookup_campioni_iuv_anomalie_comportamentali (description,level) values ('COPROFAGIA',0) ;
insert into lookup_campioni_iuv_anomalie_comportamentali (description,level) values ('RICHIESTA ECCESSIVA ATTENZIONI',0) ;
insert into lookup_campioni_iuv_anomalie_comportamentali (description,level) values ('PAURA/STRESS/ANSIA',0) ;
insert into lookup_campioni_iuv_anomalie_comportamentali (description,level) values ('FOBIE',0) ;
insert into lookup_campioni_iuv_anomalie_comportamentali (description,level) values ('STEREOTIPIE',0) ;
insert into lookup_campioni_iuv_anomalie_comportamentali (description,level) values ('AGGRESSIVITA-INTRASPECIFICA',0) ;
insert into lookup_campioni_iuv_anomalie_comportamentali (description,level) values ('AGGRESSIVITA-EXTRASPECIFICA',0) ;
insert into lookup_campioni_iuv_anomalie_comportamentali (description,level) values ('ASSENTI',0) ;

insert into lookup_campioni_iuv_tolleranza_manipolazioni (description,level) values ('TOLLERANTE',0) ;
insert into lookup_campioni_iuv_tolleranza_manipolazioni (description,level) values ('NON TOLLERANTE',0) ;
insert into lookup_campioni_iuv_tolleranza_manipolazioni (description,level) values ('SI IRRIGIDISCE',0) ;

insert into lookup_campioni_iuv_adottabilita (description,level) values ('IMMEDIATA',0) ;
insert into lookup_campioni_iuv_adottabilita (description,level) values ('CON RISERVA',0) ;
insert into lookup_campioni_iuv_adottabilita (description,level) values ('DIFFICILE',0) ;

  
