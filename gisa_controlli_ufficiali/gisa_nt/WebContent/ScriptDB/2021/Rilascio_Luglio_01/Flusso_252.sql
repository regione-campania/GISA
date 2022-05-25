CREATE SCHEMA schede_supplementari
  AUTHORIZATION postgres;

CREATE TABLE schede_supplementari.lookup_schede_supplementari(
  code serial,
  description character varying(500) NOT NULL,
  num_scheda character varying(20),
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE schede_supplementari.lookup_schede_supplementari
  OWNER TO postgres;

insert into schede_supplementari.lookup_schede_supplementari(description, num_scheda, level, enabled) values('Scheda N. 125 per corso per imprese corsi di formazione IAA', '125',1,true);


--  drop TABLE schede_supplementari.lookup_tipo_schede_supplementari;
  CREATE TABLE schede_supplementari.lookup_tipo_schede_supplementari(
  code serial,
  description character varying(500) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE schede_supplementari.lookup_tipo_schede_supplementari
  OWNER TO postgres;

insert into schede_supplementari.lookup_tipo_schede_supplementari(description, level) values ('Tipo checkbox',1);

--drop table schede_supplementari.rel_tipo_schede_supplementari;

  --insert into schede_supplementari.rel_tipo_schede_supplementari (id_tipo, num_scheda, note_hd) values (1,1,'Esempio di test per scheda associato ad una linea ristorazione');
  --update master_list_linea_attivita set scheda_supplementare = '1' where id = 40707;

-------------
CREATE TABLE schede_supplementari.sede_lezioni
(
  id serial,
  comune text, 
  via text,
  presso text,
  datelezioni text,
  orari text,
  indice integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE schede_supplementari.sede_lezioni
  OWNER TO postgres;

CREATE TABLE schede_supplementari.componentesegreteria
(
  id serial,
  nome text,
  cognome text,
  indice integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE schede_supplementari.componentesegreteria
  OWNER TO postgres;

CREATE TABLE schede_supplementari.iscritti
(
  id serial,
  nome text,
  cognome text,
  codice_fiscale text,
  indice integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE schede_supplementari.iscritti
  OWNER TO postgres;

--drop table schede_supplementari.istanze cascade;
CREATE TABLE schede_supplementari.istanze
(
  id serial,
  riferimento_id integer,
  riferimento_id_nome_tab text,
  id_istanza_linea integer,
  num_scheda text,
  enteredby integer,
  modifiedby integer,
  entered timestamp,
  modified timestamp,
  trashed_date timestamp
  
 )
WITH (
  OIDS=FALSE
);
ALTER TABLE schede_supplementari.istanze
  OWNER TO postgres;

  --select * from schede_supplementari.istanze

--drop function schede_supplementari.get_scheda_supplementare(integer, text, integer, text);
CREATE OR REPLACE FUNCTION schede_supplementari.get_scheda_supplementare(
    IN _riferimento_id integer,
    IN _riferimento_id_nome_tab text,
    IN _id_istanza_linea integer DEFAULT NULL::integer,
    IN _num_scheda text DEFAULT ''::text)
  RETURNS TABLE(id integer, riferimento_id integer, riferimento_id_nome_tab text, ragione_sociale_impresa text, nome_linea text, id_istanza_linea integer, num_scheda character varying, return_view text, code_scheda integer, descrizione_scheda character varying, enteredby integer, modifiedby integer, entered timestamp without time zone, modified timestamp without time zone, trashed_date timestamp without time zone) AS
$BODY$
DECLARE
	
BEGIN
		return query
			select 
			s.id
			, r.riferimento_id
			, r.riferimento_id_nome_tab
			, r.ragione_sociale::text
			, r.path_attivita_completo
			, r.id_linea as id_istanza_linea
			, l.num_scheda
			, l.return_view
			, l.code as code_scheda
			, l.description as descrizione_scheda
			, s.enteredby
			, s.modifiedby
			, s.entered
			, s.modified
			, s.trashed_date 
			from ricerche_anagrafiche_old_materializzata r
			left join (select m.id, trim(unnest(string_to_array(m.scheda_supplementare, ','))) as num_scheda from master_list_linea_attivita m) att on att.id = r.id_attivita
			left join schede_supplementari.istanze s on s.riferimento_id = r.riferimento_id and s.riferimento_id_nome_tab = r.riferimento_id_nome_tab and s.trashed_date is null and s.id_istanza_linea = _id_istanza_linea and s.num_scheda = _num_scheda
			--and s.id = _id_istanza and s.num_scheda = _num_scheda 
			left join schede_supplementari.lookup_schede_supplementari l on l.num_scheda = att.num_scheda
			where  r.riferimento_id = _riferimento_id --277359
			and r.riferimento_id_nome_tab = _riferimento_id_nome_tab
			and l.code > 0
			and (( _id_istanza_linea is not null and r.id_linea = _id_istanza_linea) or _id_istanza_linea is null)
			and (( _num_scheda is not null and l.num_scheda = _num_scheda ) or _num_scheda is null)
			;
			
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION schede_supplementari.get_scheda_supplementare(integer, text, integer, text)
  OWNER TO postgres;

--select * from schede_supplementari.get_scheda_supplementare(277359, 'opu_stabilimento', 417528, '125')

alter table schede_supplementari.componentesegreteria add column id_istanza integer;
alter table schede_supplementari.sede_lezioni add column id_istanza integer;
alter table schede_supplementari.iscritti add column id_istanza integer;
alter table schede_supplementari.istanze alter column entered set default now()


CREATE TABLE schede_supplementari.dati_generici_formazione_iaa
(
  id serial NOT NULL,
  num_corso text,
  anno_corso text,
  tipo_corso text,
  nome_responsabile text,
  cognome_responsabile text,
  cf_responsabile text,
  id_istanza integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE schede_supplementari.dati_generici_formazione_iaa
  OWNER TO postgres;

------------------------------------------- funzioni -----------------------------
-- Function: schede_supplementari.insert_componenti_segreteria(integer, integer, text, text)

-- DROP FUNCTION schede_supplementari.insert_componenti_segreteria(integer, integer, text, text);

CREATE OR REPLACE FUNCTION schede_supplementari.insert_componenti_segreteria(
    _id_istanza integer,
    _indice integer,
    _nome text,
    _cognome text)
  RETURNS integer AS
$BODY$

  DECLARE ret_id integer;
  
  BEGIN
	INSERT INTO schede_supplementari.componentesegreteria(id, id_istanza, indice, nome, cognome) VALUES ((select nextval('schede_supplementari.componentesegreteria_id_seq')), _id_istanza, _indice, _nome, _cognome)
		RETURNING id into ret_id;
		  
 RETURN ret_id;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION schede_supplementari.insert_componenti_segreteria(integer, integer, text, text)
  OWNER TO postgres;

-- Function: schede_supplementari.insert_dati_generici_formazione_iaa(integer, text, text, text, text, text, text)

-- DROP FUNCTION schede_supplementari.insert_dati_generici_formazione_iaa(integer, text, text, text, text, text, text);

CREATE OR REPLACE FUNCTION schede_supplementari.insert_dati_generici_formazione_iaa(
    _id_istanza integer,
    _num_corso text,
    _anno_corso text,
    _tipo_corso text,
    _nome_responsabile text,
    _cognome_responsabile text,
    _cf_responsabile text)
  RETURNS integer AS
$BODY$

  DECLARE ret_id integer;
  
  BEGIN
	INSERT INTO schede_supplementari.dati_generici_formazione_iaa(id, id_istanza, num_corso, anno_corso, tipo_corso, nome_responsabile, cognome_responsabile, cf_responsabile) VALUES ((select nextval('schede_supplementari.dati_generici_formazione_iaa_id_seq')), _id_istanza, _num_corso, _anno_corso, _tipo_corso,  _nome_responsabile,   _cognome_responsabile,   _cf_responsabile)
		RETURNING id into ret_id;
		  
 RETURN ret_id;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION schede_supplementari.insert_dati_generici_formazione_iaa(integer, text, text, text, text, text, text)
  OWNER TO postgres;

-- Function: schede_supplementari.insert_iscritti(integer, integer, text, text, text)

-- DROP FUNCTION schede_supplementari.insert_iscritti(integer, integer, text, text, text);

CREATE OR REPLACE FUNCTION schede_supplementari.insert_iscritti(
    _id_istanza integer,
    _indice integer,
    _nome text,
    _cognome text,
    _codice_fiscale text)
  RETURNS integer AS
$BODY$

  DECLARE ret_id integer;
  
  BEGIN
	INSERT INTO schede_supplementari.iscritti(id, id_istanza, indice, nome, cognome, codice_fiscale) VALUES ((select nextval('schede_supplementari.iscritti_id_seq')), _id_istanza, _indice, _nome, _cognome, _codice_fiscale)
		RETURNING id into ret_id;
		  
 RETURN ret_id;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION schede_supplementari.insert_iscritti(integer, integer, text, text, text)
  OWNER TO postgres;

  -- Function: schede_supplementari.insert_istanza(integer, text, integer, text, integer)

-- DROP FUNCTION schede_supplementari.insert_istanza(integer, text, integer, text, integer);

CREATE OR REPLACE FUNCTION schede_supplementari.insert_istanza(
    _riferimento_id integer,
    _riferimento_id_nome_tab text,
    _id_istanza_linea integer,
    _num_scheda text,
    _enteredby integer)
  RETURNS integer AS
$BODY$

  DECLARE ret_id integer;
  
  BEGIN
	update schede_supplementari.istanze set trashed_date = now() where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab and
	id_istanza_linea = _id_istanza_linea and num_scheda = _num_scheda;
	INSERT INTO schede_supplementari.istanze(id, riferimento_id, riferimento_id_nome_tab, id_istanza_linea, num_scheda, enteredby) VALUES ((select nextval('schede_supplementari.istanze_id_seq')), _riferimento_id, _riferimento_id_nome_tab, _id_istanza_linea, _num_scheda,  _enteredby)
		RETURNING id into ret_id;
		  
 RETURN ret_id;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION schede_supplementari.insert_istanza(integer, text, integer, text, integer)
  OWNER TO postgres;

-- Function: schede_supplementari.insert_sede_lezioni(integer, integer, text, text, text, text, text)

-- DROP FUNCTION schede_supplementari.insert_sede_lezioni(integer, integer, text, text, text, text, text);

CREATE OR REPLACE FUNCTION schede_supplementari.insert_sede_lezioni(
    _id_istanza integer,
    _indice integer,
    _comune text,
    _via text,
    _presso text,
    _datelezioni text,
    _orari text)
  RETURNS integer AS
$BODY$

  DECLARE ret_id integer;
  
  BEGIN
	INSERT INTO schede_supplementari.sede_lezioni(id, id_istanza, indice, comune, via, presso, datelezioni, orari) VALUES ((select nextval('schede_supplementari.sede_lezioni_id_seq')), _id_istanza, _indice, _comune, _via, _presso, _datelezioni, _orari)		
	RETURNING id into ret_id;
		  
 RETURN ret_id;
  END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION schede_supplementari.insert_sede_lezioni(integer, integer, text, text, text, text, text)
  OWNER TO postgres;


-- Function: schede_supplementari.get_scheda_supplementare(integer, text, integer, text)

-- DROP FUNCTION schede_supplementari.get_scheda_supplementare(integer, text, integer, text);

CREATE OR REPLACE FUNCTION schede_supplementari.get_dati_generici_formazione_iaa(IN _id_istanza integer)
  RETURNS SETOF  schede_supplementari.dati_generici_formazione_iaa AS
$BODY$
DECLARE
	
BEGIN
		return query
			select * FROM schede_supplementari.dati_generici_formazione_iaa WHERE id_istanza = _id_istanza;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION schede_supplementari.get_dati_generici_formazione_iaa(integer)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION schede_supplementari.get_sede_lezioni(IN _id_istanza integer)
  RETURNS SETOF  schede_supplementari.sede_lezioni AS
$BODY$
DECLARE
	
BEGIN
		return query
			select * FROM schede_supplementari.sede_lezioni WHERE id_istanza = _id_istanza;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION schede_supplementari.get_sede_lezioni(integer)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION schede_supplementari.get_componente_segreteria(IN _id_istanza integer)
  RETURNS SETOF  schede_supplementari.componentesegreteria AS
$BODY$
DECLARE
	
BEGIN
		return query
			select * FROM schede_supplementari.componentesegreteria  WHERE id_istanza = _id_istanza;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION schede_supplementari.get_componente_segreteria(integer)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION schede_supplementari.get_iscritti(IN _id_istanza integer)
  RETURNS SETOF  schede_supplementari.iscritti AS
$BODY$
DECLARE
	
BEGIN
		return query
			select * FROM schede_supplementari.iscritti   WHERE id_istanza = _id_istanza;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION schede_supplementari.get_iscritti(integer)
  OWNER TO postgres;

  --select * from schede_supplementari.get_iscritti(4)

CREATE OR REPLACE FUNCTION schede_supplementari.get_istanze(_id_istanza integer)
  RETURNS SETOF schede_supplementari.istanze AS
$BODY$
DECLARE
	
BEGIN
		return query
			select * FROM schede_supplementari.istanze  WHERE id_istanza = _id_istanza and trashed_date is null;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION schede_supplementari.get_istanze(integer)
  OWNER TO postgres;

insert into permission (category_id,permission,permission_view,permission_add,permission_edit,permission_delete,description , level,enabled,active)
values (29,'gestione_schede_supplementari',true,true,true,false,'GESTIONE SCHEDE SUPPLEMENTARI',10,true,true)

--select * from role_permission where permission_id = 793

--insert into role_permission (role_id, permission_id, role_view, role_add) values (1,793,true,true);
--insert into role_permission (role_id, permission_id, role_view, role_add) values (31,793,true,true);
--insert into role_permission (role_id, permission_id, role_view, role_add) values (32,793,true,true);

-- gestione scheda_supplementare 1 di tipo checkbox
insert into schede_supplementari.lookup_schede_supplementari(description, num_scheda, level, enabled) values('Scheda n. 1 per laboratori prodotti a base di latte', '1',1,true);
--update master_list_linea_attivita set scheda_supplementare = '1' where id = 40504;

alter table schede_supplementari.lookup_schede_supplementari add column return_view text;
update schede_supplementari.lookup_schede_supplementari set return_view='ListaCheckbox' where num_scheda='1';
update schede_supplementari.lookup_schede_supplementari set return_view='125' where num_scheda='125';
--drop table schede_supplementari.lookup_checkbox cascade;
CREATE TABLE schede_supplementari.lookup_checkbox(
  code serial,
  num_scheda text,
  intestazione text,
  description character varying(500) NOT NULL,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true
)
WITH (
  OIDS=FALSE
);
ALTER TABLE schede_supplementari.lookup_checkbox
  OWNER TO postgres;


insert into schede_supplementari.lookup_checkbox (description, num_scheda) values('FORMAGGI DI LATTE BOVINO CON STAGIONATURA <60 GIORNI','1');
insert into schede_supplementari.lookup_checkbox (description, num_scheda) values('FORMAGGI DI LATTE BOVINO CON STAGIONATURA >60 GIORNI','1');
insert into schede_supplementari.lookup_checkbox (description, num_scheda) values('ALTRI PRODOTTI A BASE LATTE BOVINO','1');
insert into schede_supplementari.lookup_checkbox (description, num_scheda) values('ALTRI PRODOTTI A BASE LATTE BUFALINO','1');
insert into schede_supplementari.lookup_checkbox (description, num_scheda) values('FORMAGGI DI LATTE CAPRINO CON STAGIONATURA <60 GIORNI','1');
insert into schede_supplementari.lookup_checkbox (description, num_scheda) values('FORMAGGI DI LATTE CAPRINO CON STAGIONATURA >60 GIORNI','1');
insert into schede_supplementari.lookup_checkbox (description, num_scheda) values('FORMAGGI DI LATTE OVINO CON STAGIONATURA <60 GIORNI','1');
insert into schede_supplementari.lookup_checkbox (description, num_scheda) values('FORMAGGI DI LATTE OVINO CON STAGIONATURA >60 GIORNI','1');
insert into schede_supplementari.lookup_checkbox (description, num_scheda) values('ALTRI PRODOTTI A BASE LATTE OVINO','1');
insert into schede_supplementari.lookup_checkbox (description, num_scheda) values('FORMAGGIO PORZ.O GRATTUGGIATO','1');
insert into schede_supplementari.lookup_checkbox (description, num_scheda) values('BURRO','1');

update schede_supplementari.lookup_checkbox set level=code;
update schede_supplementari.lookup_checkbox set intestazione = 'ELENCO DEI PRODOTTI A BASE DI LATTE PRODOTTI NELLO STABILIMENTO';

-- Function: schede_supplementari.get_lista_checkbox(text, integer)

-- DROP FUNCTION schede_supplementari.get_lista_checkbox(text, integer);

CREATE OR REPLACE FUNCTION schede_supplementari.get_lista_checkbox(
    IN _num_scheda text,
    IN _id_istanza integer)
  RETURNS TABLE(code integer, num_scheda text, descrizione text, intestazione text, checked boolean, id_istanza integer) AS
$BODY$
DECLARE
	
BEGIN
		return query
			SELECT c.code, c.num_scheda, c.description::text as descrizione, c.intestazione, coalesce(ic.checked, false), coalesce(ic.id_istanza, -1)
			FROM schede_supplementari.lookup_checkbox c 
			left join schede_supplementari.checkbox ic on c.code = ic.code_lookup_checkbox and ic.id_istanza = _id_istanza			
			WHERE c.num_scheda = _num_scheda 
			order by level;
			
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION schede_supplementari.get_lista_checkbox(text, integer)
  OWNER TO postgres;


--select * from schede_supplementari.get_lista_checkbox('1',0)
--select * from schede_supplementari.get_lista_checkbox('1',14) 

--drop table  schede_supplementari.checkbox   
CREATE TABLE schede_supplementari.checkbox
(
  id serial,
  code_lookup_checkbox integer,
  checked boolean,
  id_istanza integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE schede_supplementari.checkbox
  OWNER TO postgres;
        

CREATE OR REPLACE FUNCTION schede_supplementari.insert_checkbox(IN _id_istanza integer, _code_checkbox integer, _checked boolean)
  RETURNS integer AS
$BODY$
	 DECLARE ret_id integer;
  BEGIN
	INSERT INTO schede_supplementari.checkbox(id, id_istanza, code_lookup_checkbox, checked) VALUES ((select nextval('schede_supplementari.checkbox_id_seq')), _id_istanza, _code_checkbox, _checked)		
	RETURNING id into ret_id;
		  
 RETURN ret_id;
		
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION schede_supplementari.insert_checkbox(integer, integer, boolean)
  OWNER TO postgres;


select * from schede_supplementari.checkbox


--select * from schede_supplementari.get_scheda_supplementare(312277, 'opu_stabilimento', 415692, '125')