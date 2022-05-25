-- Table: mu_visita_pm_patologie

-- DROP TABLE mu_visita_pm_patologie;

CREATE TABLE mu_visita_pm_patologie
(
  id serial NOT NULL,
  id_capo integer,
  id_patologia integer,
  note text,
  entered_by integer,
  modified_by integer,
  entered timestamp with time zone DEFAULT now(),
  modified timestamp with time zone DEFAULT now(),
  trashed_date timestamp with time zone,
  id_partita integer,
  id_seduta integer,
  CONSTRAINT mu_visita_pm_patologie_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_visita_pm_patologie
  OWNER TO postgres;
GRANT ALL ON TABLE mu_visita_pm_patologie TO postgres;
GRANT ALL ON TABLE mu_visita_pm_patologie TO report;




-- Table: mu_visita_pm_esiti

-- DROP TABLE mu_visita_pm_esiti;

CREATE TABLE mu_visita_pm_esiti
(
  id serial NOT NULL,
  id_capo integer,
  id_esito integer,
  note text,
  entered_by integer,
  modified_by integer,
  entered timestamp with time zone DEFAULT now(),
  modified timestamp with time zone DEFAULT now(),
  trashed_date timestamp with time zone,
  id_partita integer,
  id_seduta integer,
  CONSTRAINT mu_visita_pm_esiti_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_visita_pm_esiti
  OWNER TO postgres;
GRANT ALL ON TABLE mu_visita_pm_esiti TO postgres;
GRANT ALL ON TABLE mu_visita_pm_esiti TO report;
