select * from evento_html_fields where id_tipologia_evento = 7
select * from evento_html_fields where id_tipologia_evento = 46

-- drop sequence lookup_sesso_code_seq

CREATE SEQUENCE public.lookup_sesso_code_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9999999
  START 1
  CACHE 1;
ALTER TABLE public.lookup_regione_code_seq
  OWNER TO postgres;

-- drop table lookup_sesso

CREATE TABLE lookup_sesso
(
  code integer NOT NULL DEFAULT nextval('lookup_sesso_code_seq'::regclass),
  description character varying(50) NOT NULL,
  enabled boolean,
  level integer,
  default_item boolean,
  entered timestamp,
  modified timestamp,
  CONSTRAINT sesso_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);


insert into evento_html_fields (id_tipologia_evento,nome_campo,tipo_campo,label_campo,tabella_lookup,ordine_campo,select_size,select_multiple) values (7,'sesso','select','Sesso','lookup_sesso',7,1,false);
insert into evento_html_fields (id_tipologia_evento,nome_campo,tipo_campo,label_campo,tabella_lookup,ordine_campo,select_size,select_multiple) values (46,'sesso','select','Sesso','lookup_sesso',7,1,false);


insert into lookup_sesso(description, enabled, level, default_item, entered, modified) values ('M',true,1,false, now(), now());
insert into lookup_sesso(description, enabled, level, default_item, entered, modified) values ('F',true,2,false, now(), now());

alter table evento_cessione add column sesso_proprietario_a_cessione text;
alter table evento_adozione_fuori_asl  add column sesso_proprietario_a_adozione_fa text;

update evento_cessione set sesso_proprietario_a_cessione = 'M' where sesso_proprietario_a_cessione is null;
update evento_adozione_fuori_asl  set sesso_proprietario_a_adozione_fa = 'M' where sesso_proprietario_a_adozione_fa is null;

update evento_html_fields set tipo_controlli_date = null where id = 229;
update evento_html_fields set tipo_controlli_date = null where id = 266;