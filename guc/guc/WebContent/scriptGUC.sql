CREATE TABLE log_utenti
(
  id serial NOT NULL,
  id_utente integer not null,
  username character varying(255),
  nome character varying(255),
  cognome character varying(255),
  password character varying(255),
  password_encrypted character varying(255),
  operazione character varying(255),
  data timestamp without time zone,
  
  
  CONSTRAINT log_utenti_pkey PRIMARY KEY (id ),
  CONSTRAINT log_utenti_fkey_guc_utenti FOREIGN KEY (id_utente)
      REFERENCES guc_utenti (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE log_utenti
  OWNER TO postgres;
  
----------------------------------------------------------------------------------------------------------------------  
  
CREATE TABLE log_ruoli_utenti
(
  id serial NOT NULL,
  id_log_utente integer not null,
  endpoint character varying(255),
  ruolo character varying(255),
  CONSTRAINT log_ruoli_utenti_pkey PRIMARY KEY (id ),
  CONSTRAINT log_ruoli_utenti_fkey_log_utenti FOREIGN KEY (id_log_utente)
      REFERENCES log_utenti (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE log_ruoli_utenti
  OWNER TO postgres;
  
----------------------------------------------------------------------------------------------------------------------
  
ALTER TABLE guc_utenti ADD COLUMN password_encrypted character varying(255);

----------------------------------------------------------------------------------------------------------------------


03/09/2013 Rita Mele

select 'delete from guc_ruoli where endpoint=''Digemon'' and ruolo_integer=-1 and id_utente= '||id_utente||';' ,
       'insert into guc_ruoli(endpoint,ruolo_integer,ruolo_string,id_utente) values (''Digemon'',10,''Statistiche\','||id_utente||');' ,* 
from guc_ruoli gr1 
where gr1.endpoint in ('Vam','Canina','Felina') and 
      gr1.ruolo_integer > 0 and (select count(*) from guc_ruoli gr where gr.endpoint='Digemon' and gr.id_utente = gr1.id_utente and gr.ruolo_integer>0 )=0
order by gr1.id_utente



--------------------------------------------------------------------------------------------------------------
GUC 2.1 25/09/2014 Salvatore Pane
CREATE TABLE guc_canili_bdu
(
  id serial NOT NULL,
  id_canile integer,
  descrizione_canile text,
  id_utente integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE guc_canili_bdu
  OWNER TO postgres;


CREATE TABLE guc_strutture_gisa
(
  id serial NOT NULL,
  id_struttura integer,
  descrizione_struttura text,
  id_utente integer,
  id_padre integer,
  n_livello integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE guc_strutture_gisa
  OWNER TO postgres;


CREATE TABLE guc_importatori
(
  id serial NOT NULL,
  id_importatore integer,
  descrizione_importatore text,
  id_utente integer,
  partita_iva text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE guc_importatori
  OWNER TO postgres;
  
alter table guc_utenti add column id_provincia_iscrizione_albo_vet_privato integer;
alter table guc_utenti add column nr_iscrione_albo_vet_privato text;  


insert into category  (name) values ('SuperAmministratore');

insert into secureobject (name) values ('2');

insert into category_secureobject  (categories_name, secureobjects_name ) values ('SuperAmministratore','2');

INSERT INTO subject(name) VALUES ('AMMINISTRAZIONE->UTENTI->MODIFICA AMMINISTRATORI');

INSERT INTO permessi_gui(nome, id_subfunzione) VALUES ('MODIFICA AMMINISTRATORI', 3);

insert into permessi_ruoli  (id,descrizione,nome) values(2,'Amministra tutto','SuperAmministratore')

select 'insert into capability  (id,category_name,subject_name) values ((select max(id)+1 from capability) , ''SuperAmministratore\',''' || subject.name || ''');'
from subject
group by  subject.name 

insert into capability_permission  (capabilities_id,permissions_name)  (select id, 'w' from capability  where id not in (select capabilities_id from capability_permission))

