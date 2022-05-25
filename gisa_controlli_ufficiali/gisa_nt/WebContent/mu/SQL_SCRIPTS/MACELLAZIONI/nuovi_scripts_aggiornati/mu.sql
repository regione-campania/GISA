
CREATE TABLE mu_comunicazioni_destinatari
(
  id serial NOT NULL,
  id_capo integer,
  id_destinatario integer,
  note text,
  entered_by integer,
  modified_by integer,
  entered timestamp with time zone DEFAULT now(),
  modified timestamp with time zone DEFAULT now(),
  trashed_date timestamp with time zone,
  id_partita integer,
  id_seduta integer,
  CONSTRAINT mu_comunicazioni_destinatari_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_comunicazioni_destinatari
  OWNER TO postgres;
GRANT ALL ON TABLE mu_comunicazioni_destinatari TO postgres;
GRANT ALL ON TABLE mu_comunicazioni_destinatari TO report;
GRANT SELECT ON TABLE mu_comunicazioni_destinatari TO usr_ro;


-- Table: mu_comunicazioni_non_conformita_rilevate

-- DROP TABLE mu_comunicazioni_non_conformita_rilevate;

CREATE TABLE mu_comunicazioni_non_conformita_rilevate
(
  id serial NOT NULL,
  id_capo integer,
  id_non_conformita integer,
  note text,
  entered_by integer,
  modified_by integer,
  entered timestamp with time zone DEFAULT now(),
  modified timestamp with time zone DEFAULT now(),
  trashed_date timestamp with time zone,
  id_partita integer,
  id_seduta integer,
  CONSTRAINT mu_comunicazioni_non_conformita_rilevate_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_comunicazioni_non_conformita_rilevate
  OWNER TO postgres;
GRANT ALL ON TABLE mu_comunicazioni_non_conformita_rilevate TO postgres;
GRANT ALL ON TABLE mu_comunicazioni_non_conformita_rilevate TO report;
GRANT SELECT ON TABLE mu_comunicazioni_non_conformita_rilevate TO usr_ro;



-- Table: mu_comunicazioni_provvedimenti

-- DROP TABLE mu_comunicazioni_provvedimenti;

CREATE TABLE mu_comunicazioni_provvedimenti
(
  id serial NOT NULL,
  id_capo integer,
  id_provvedimento integer,
  note text,
  entered_by integer,
  modified_by integer,
  entered timestamp with time zone DEFAULT now(),
  modified timestamp with time zone DEFAULT now(),
  trashed_date timestamp with time zone,
  id_partita integer,
  id_seduta integer,
  CONSTRAINT mu_comunicazioni_provvedimenti_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_comunicazioni_provvedimenti
  OWNER TO postgres;
GRANT ALL ON TABLE mu_comunicazioni_provvedimenti TO postgres;
GRANT ALL ON TABLE mu_comunicazioni_provvedimenti TO report;
GRANT SELECT ON TABLE mu_comunicazioni_provvedimenti TO usr_ro;

