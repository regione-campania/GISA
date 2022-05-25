
CREATE SEQUENCE lookup_tipologia_opna_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE lookup_tipologia_opna_id_seq
  OWNER TO postgres;


CREATE TABLE lookup_tipologia_opna_id
(
  code integer NOT NULL DEFAULT nextval('lookup_tipologia_opna_id_seq'::regclass),
  description character varying(70) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_tipologia_opna_id_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_tipologia_opna_id
  OWNER TO postgres;

  
  INSERT INTO lookup_tipologia_opna_id(
            code, description, default_item, level, enabled)
    VALUES (nextval('lookup_tipologia_opna_id_seq'), 'Produzione e trasformazione di materiali e oggetti destinati a venire a contatto con alimenti ( Reg.2023/2006) - legno ', 
    false, 0, true);

    INSERT INTO lookup_tipologia_opna_id(
            code, description, default_item, level, enabled)
    VALUES (nextval('lookup_tipologia_opna_id_seq'), 'Produzione e trasformazione di materiali e oggetti destinati a venire a contatto con alimenti ( Reg.2023/2006) - carta e cartone', 
    false, 0, true);

