INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, "level", enabled, 
            active, viewpoints)
    VALUES (79138, 92, 'trasportoanimali-trasportoanimali-stato', TRUE, TRUE, 
            TRUE, TRUE, 'Cambia Stato', 7000, TRUE, 
            TRUE, FALSE);

ALTER TABLE organization ADD stato_impresa character varying(300);


CREATE TABLE stato_impresa
(
  id integer NOT NULL,
  org_id integer,
  stato character(100),
  entered timestamp(3) without time zone NOT NULL DEFAULT now(),
  enteredby integer NOT NULL,
  CONSTRAINT stato_pkey PRIMARY KEY (id)
)
WITH (OIDS=FALSE);
ALTER TABLE stato_impresa OWNER TO postgres;


CREATE SEQUENCE stato_impresa_id_seq
  INCREMENT 1
  MINVALUE 0
  MAXVALUE 9223372036854775807
  START 0
  CACHE 1;
ALTER TABLE stato_impresa_id_seq OWNER TO postgres;

