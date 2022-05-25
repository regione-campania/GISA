-- Table: public.canili_operazioni

-- DROP TABLE public.canili_operazioni;

CREATE TABLE public.canili_operazioni
(
  id serial,
  id_utente integer,
  id_rel_stab_lp integer,
  data timestamp without time zone,
  operazione text,
  note text,
  trashed_date timestamp without time zone
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.canili_operazioni
  OWNER TO postgres;

