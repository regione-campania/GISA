drop table mu_paths

CREATE TABLE mu_paths
(
  id serial NOT NULL,
  nr_path integer,
  descrizione character varying
  
 -- CONSTRAINT mu_paths_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_paths
  OWNER TO postgres;


  CREATE TABLE mu_steps
(
  id serial NOT NULL,
  descrizione character varying
  
 -- CONSTRAINT mu_paths_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_steps
  OWNER TO postgres;


--non serve
CREATE TABLE mu_step_fields
(
  id serial NOT NULL,
  id_step integer NOT NULL,
  nome_campo character varying NOT NULL,
  tipo_campo character varying NOT NULL,
  label_campo character varying NOT NULL,
  tabella_lookup character varying,
  javascript_function character varying,
  javascript_function_evento character varying,
  link_value character varying,
  ordine_campo integer,
  valore_campo character varying,
  tipo_controlli_date character varying,
  label_campo_controlli_date character varying,
  maxlength integer,
  label_link character varying
  
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mu_step_fields
  OWNER TO postgres;



  create table mu_lookup_stati (
	
  code serial NOT NULL,
  description character varying(50) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true

  )


  create table mu_paths_wkf
  (
  id serial not null,
  id_stato integer,
  id_path integer,
  id_prossimo_stato integer
  )



create table mu_path_steps_relazione
(
id serial not null,
id_path integer,
id_step integer,
flag_required boolean
)



 alter table mu_steps
 ADD flag_is_required boolean,
 ADD jsp_page_to_include character varying


 alter table mu_paths 
 add enabled boolean default true

 alter table mu_path_steps_relazione
 add ordine integer