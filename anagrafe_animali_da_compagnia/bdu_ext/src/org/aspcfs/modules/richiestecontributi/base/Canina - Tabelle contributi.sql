

CREATE TABLE contributi_lista_univocita
(
  id serial NOT NULL,
  id_cane integer,
  microchip character varying(32),
  id_richiesta_contributi integer NOT NULL,
  data_approvazione timestamp with time zone,
  tipologia character varying(9),
  comune character varying,
  asl integer,
  data_sterilizzazione timestamp without time zone,
  CONSTRAINT contributi_lista_univocita_pkey PRIMARY KEY (id),
  CONSTRAINT contributi_lista_univocita_id_richiesta_contributi_fkey FOREIGN KEY (id_richiesta_contributi)
      REFERENCES contributi_richieste (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT contributi_lista_univocita_id_cane_key UNIQUE (id_cane),
  CONSTRAINT contributi_lista_univocita_microchip_key UNIQUE (microchip)
) 
WITHOUT OIDS;
ALTER TABLE contributi_lista_univocita OWNER TO postgres;





CREATE TABLE contributi_lista_cani
(
  id serial NOT NULL,
  id_cane integer,
  microchip character varying(32),
  id_richiesta_contributi integer NOT NULL,
  tipologia character varying(9),
  comune character varying,
  numero_protocollo character varying(100),
  data_sterilizzazione timestamp without time zone,
  asl integer,
  CONSTRAINT contributi_lista_cani_pkey PRIMARY KEY (id),
  CONSTRAINT contributi_lista_cani_id_richiesta_contributi_fkey FOREIGN KEY (id_richiesta_contributi)
      REFERENCES contributi_richieste (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITHOUT OIDS;
ALTER TABLE contributi_lista_cani OWNER TO postgres;

-- Table: contributi_richieste

-- DROP TABLE contributi_richieste;

CREATE TABLE contributi_richieste
(
  id serial NOT NULL,
  inserito_da integer NOT NULL,
  asl integer NOT NULL,
  data_richiesta timestamp without time zone DEFAULT now(),
  data_from timestamp without time zone,
  data_to timestamp without time zone,
  approvato_da integer,
  data_approvazione timestamp without time zone,
  respinto_da integer,
  data_respinta timestamp without time zone,
  tipologia character varying(9),
  numero_protocollo character varying,
  CONSTRAINT contributi_richieste_pkey PRIMARY KEY (id)
) 
WITHOUT OIDS;
ALTER TABLE contributi_richieste OWNER TO postgres;









