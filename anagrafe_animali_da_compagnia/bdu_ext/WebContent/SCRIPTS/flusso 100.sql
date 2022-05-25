--Req.1 - Scheda Adozione
CREATE TABLE lookup_scheda_adozione_cani_criteri
(
  id integer NOT NULL ,
  nome text,
  enabled boolean,
  level integer,
  CONSTRAINT lookup_scheda_adozione_cani_criteri_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_scheda_adozione_cani_criteri
  OWNER TO postgres;

CREATE TABLE lookup_scheda_adozione_cani_indici
(
  id integer NOT NULL ,
  nome text,
  punteggio integer,
  enabled boolean,
  level integer,
  CONSTRAINT lookup_scheda_adozione_cani_indici_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_scheda_adozione_cani_indici
  OWNER TO postgres;

CREATE SEQUENCE scheda_adozione_cani_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 999999999
  START 1
  CACHE 1;
ALTER TABLE scheda_adozione_cani_id_seq
  OWNER TO postgres;

  CREATE SEQUENCE scheda_adozione_cani_storico_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 999999999
  START 1
  CACHE 1;
ALTER TABLE scheda_adozione_cani_id_seq
  OWNER TO postgres;


CREATE TABLE scheda_adozione_cani
(
  id integer NOT NULL DEFAULT nextval('scheda_adozione_cani_id_seq'::regclass),
  id_animale integer,
  id_criterio integer,
  id_indice integer,
  entered timestamp without time zone,
  modified timestamp without time zone,
  entered_by integer,
  modified_by integer,
  CONSTRAINT scheda_adozione_cani_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE scheda_adozione_cani
  OWNER TO postgres;


ALTER TABLE scheda_adozione_cani
ADD CONSTRAINT fki_scheda_adozione_cani_fk1 FOREIGN KEY (id_animale)
      REFERENCES animale (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE scheda_adozione_cani
ADD CONSTRAINT fki_scheda_adozione_cani_fk2 FOREIGN KEY (id_criterio)
      REFERENCES lookup_scheda_adozione_cani_criteri(id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE scheda_adozione_cani
ADD CONSTRAINT fki_scheda_adozione_cani_fk3 FOREIGN KEY (id_indice)
      REFERENCES lookup_scheda_adozione_cani_indici(id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;


INSERT INTO lookup_scheda_adozione_cani_indici(id, nome, punteggio, enabled, level) VALUES (1, 'adeguato', 1, true, 10);
INSERT INTO lookup_scheda_adozione_cani_indici(id, nome, punteggio, enabled, level) VALUES (2, 'rivalutare', 2, true, 20);
INSERT INTO lookup_scheda_adozione_cani_indici(id, nome, punteggio, enabled, level) VALUES (3, 'inadeguato', 3, true, 30);

INSERT INTO lookup_scheda_adozione_cani_criteri(id, nome, enabled, level) VALUES (1, 'a) comportamento in ambiente sconosciuto', true, 10);
INSERT INTO lookup_scheda_adozione_cani_criteri(id, nome, enabled, level) VALUES (2, 'b) comportamento in ambiente conosciuto', true, 20);
INSERT INTO lookup_scheda_adozione_cani_criteri(id, nome, enabled, level) VALUES (3, 'c) socializzazione intraspecifica', true, 30);
INSERT INTO lookup_scheda_adozione_cani_criteri(id, nome, enabled, level) VALUES (4, 'd) socializzazione interspecifica', true, 40);
INSERT INTO lookup_scheda_adozione_cani_criteri(id, nome, enabled, level) VALUES (5, 'e) comportamento aggressivo', true, 50);
INSERT INTO lookup_scheda_adozione_cani_criteri(id, nome, enabled, level) VALUES (6, 'f) prove di tolleranza e gestione', true, 60);

create TABLE scheda_adozione_cani_storico
(
  id integer NOT NULL DEFAULT nextval('scheda_adozione_cani_storico_id_seq'::regclass),
  id_scheda integer,
  id_animale integer,
  id_criterio integer,
  id_indice integer,
  entered timestamp without time zone,
  modified timestamp without time zone,
  entered_by integer,
  modified_by integer,
  trashed_date timestamp without time zone,
  CONSTRAINT scheda_adozione_cani_storico_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE scheda_adozione_cani_storico
  OWNER TO postgres;

alter table scheda_adozione_cani add column trashed_date timestamp without time zone;

select * from permission where permission_id = 592


insert into permission(permission_id,                                 category_id,           permission, permission_view,permission_add,permission_edit,permission_delete,                                       description, enabled, active) 
                values((select max(permission_id)+1 from permission),          32, 'schedaadozionecani',            true,          true,          false,            false,'Permesso per gestire le schede adottabilità cani',    true,   true);
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (23, 592, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (28, 592, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (26, 592, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (25, 592, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (33, 592, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (27, 592, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (22, 592, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (34, 592, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (1, 592, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (21, 592, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (18, 592, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (6, 592, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (5, 592, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (20, 592, true, true );


update permission set permission_offline_delete = true, permission_offline_add = true, permission_offline_edit = true,permission_offline_view = true, viewpoints = true, permission_edit = true, permission_delete = true where permission_id = 592;




CREATE TABLE scheda_adozione_config_valutazione
(
  da integer,
  a integer,
  valutazione text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE scheda_adozione_config_valutazione
  OWNER TO postgres;


  
insert into scheda_adozione_config_valutazione values (0,7,'adottabile');
insert into scheda_adozione_config_valutazione values (8,12,'adottabile con riserva');
insert into scheda_adozione_config_valutazione values (13,1000000,'non adottabile');



CREATE OR REPLACE FUNCTION public.get_valutazione_scheda_adozione_cani(integer)
  RETURNS TABLE(punteggio integer, valutazione text) AS
$BODY$

 BEGIN
    
   RETURN QUERY  
   select (   
           select sum(indice.punteggio)::integer
           from scheda_adozione_cani sc 
           left join lookup_scheda_adozione_cani_indici indice on indice.id = sc.id_indice 
           where sc.id_animale = $1 and
                 sc.trashed_date is null
           ) as punteggio, 
           config.valutazione
           from scheda_adozione_config_valutazione config
           where ( 
                  select sum(indice.punteggio) 
                  from scheda_adozione_cani sc 
                  left join lookup_scheda_adozione_cani_indici indice on indice.id = sc.id_indice 
                  where sc.id_animale = $1 and
                        sc.trashed_date is null
                  ) between config.da and config.a;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_valutazione_scheda_adozione_cani(integer)
  OWNER TO postgres;




--Req.2 - Scheda Morsicatura
CREATE TABLE lookup_scheda_morsicatura_criteri
(
  id integer NOT NULL ,
  nome text,
  enabled boolean,
  level integer,
  formula_calcolo_punteggio text,
  CONSTRAINT lookup_scheda_morsicatura_criteri_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_scheda_morsicatura_criteri
  OWNER TO postgres;

CREATE TABLE lookup_scheda_morsicatura_indici
(
  id integer NOT NULL ,
  nome text,
  punteggio integer,
  enabled boolean,
  level integer,
  id_criterio integer,
  valore_manuale boolean,
  divisore boolean,
  CONSTRAINT lookup_scheda_morsicatura_indici_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_scheda_morsicatura_indici
  OWNER TO postgres;

CREATE SEQUENCE scheda_morsicatura_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 999999999
  START 1
  CACHE 1;
ALTER TABLE scheda_morsicatura_id_seq
  OWNER TO postgres;

  CREATE SEQUENCE scheda_morsicatura_storico_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 999999999
  START 1
  CACHE 1;
ALTER TABLE scheda_morsicatura_id_seq
  OWNER TO postgres;

CREATE SEQUENCE scheda_morsicatura_records_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 999999999
  START 1
  CACHE 1;
ALTER TABLE scheda_morsicatura_id_seq
  OWNER TO postgres;


CREATE TABLE scheda_morsicatura_records
(
  id integer NOT NULL DEFAULT nextval('scheda_morsicatura_records_id_seq'::regclass),
  id_scheda integer,
  id_indice integer,
  valore_manuale text,
  entered timestamp without time zone,
  modified timestamp without time zone,
  entered_by integer,
  modified_by integer,
  CONSTRAINT scheda_morsicatura_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE scheda_morsicatura_records
  OWNER TO postgres;


CREATE TABLE scheda_morsicatura
(
  id integer NOT NULL DEFAULT nextval('scheda_morsicatura_id_seq'::regclass),
  id_animale integer,
  entered timestamp without time zone,
  modified timestamp without time zone,
  entered_by integer,
  modified_by integer,
  trashed_date timestamp without time zone,
  CONSTRAINT scheda_morsicatura_ppkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE scheda_morsicatura
  OWNER TO postgres;



ALTER TABLE scheda_morsicatura
ADD CONSTRAINT fki_scheda_morsicatura_fk1 FOREIGN KEY (id_animale)
      REFERENCES animale (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;


INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale,divisore) VALUES (1, 'Peso del cane', null, true, 10, 1, true,false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale,divisore) VALUES (2, 'Peso della vittima', null, true, 20, 1, true,true);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (3, 'Uomini adulti', 1, true, 30, 2, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (4, 'Donne adulte, persone con handicap minore, persone timorose', 2, true, 40, 2, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (5, 'Bambini di età > 6 anni, persone anziane, persone con handicap minore', 3, true, 50, 2, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (6, 'Bambini tra i 3 e i 6 anni, persone con handicap serio', 4, true, 60, 2, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (7, 'Bambini di meno di 3 anni di età, persone con un handicap maggiore', 5, true, 70, 2, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (8, 'Aggressione difensiva: il cane reagisce quando la persona va verso di lui', 1, true, 80, 3, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (9, 'Aggressione offensiva: il cane va verso la persona per attaccarla', 2, true, 90, 3, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (10, 'Aggressione prevedibile', 1, true, 100, 4, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (11, 'Aggressione poco prevedibile', 2, true, 110, 4, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (12, 'Aggressione imprevedibile', 3, true, 120, 4, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (13, 'Morso in bocca', 1, true, 130, 5, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (14, 'Morso breve con i soli incisivi', 2, true, 140, 5, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (15, 'Morso controllato', 3, true, 150, 5, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (16, 'Morso controllato e tenuto', 4, true, 160, 5, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (17, 'Morso forte', 5, true, 170, 5, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (18, 'Morso forte e tenuto', 6, true, 180, 5, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (19, 'Morso dovuto ad un comportamento di aggressione predatoria', 7, true, 190, 5, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (20, 'Morso semplice', 1, true, 200, 6, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (21, 'Morso semplice e tenuto', 2, true, 210, 6, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (22, 'Morso multiplo', 3, true, 220, 6, false);
INSERT INTO lookup_scheda_morsicatura_indici(id, nome, punteggio, enabled, level,id_criterio,valore_manuale) VALUES (23, 'Morso multiplo e tenuto', 4, true, 230, 6, false);

INSERT INTO lookup_scheda_morsicatura_criteri(id, nome, enabled, level, formula_calcolo_punteggio) VALUES (1, 'a) peso e massa', true, 10, 'divisione_indice');
INSERT INTO lookup_scheda_morsicatura_criteri(id, nome, enabled, level, formula_calcolo_punteggio) VALUES (2, 'b) categorie a rischio', true, 20, 'indice');
INSERT INTO lookup_scheda_morsicatura_criteri(id, nome, enabled, level, formula_calcolo_punteggio) VALUES (3, 'c) tipo di aggressione (offensiva o difensiva)', true, 30, 'indice');
INSERT INTO lookup_scheda_morsicatura_criteri(id, nome, enabled, level, formula_calcolo_punteggio) VALUES (4, 'd) prevedibilita''', true, 40, 'indice');
INSERT INTO lookup_scheda_morsicatura_criteri(id, nome, enabled, level, formula_calcolo_punteggio) VALUES (5, 'e) controllo del morso', true, 50, 'indice');
INSERT INTO lookup_scheda_morsicatura_criteri(id, nome, enabled, level, formula_calcolo_punteggio) VALUES (6, 'f) tipo di morso', true, 60, 'indice');


CREATE TABLE scheda_morsicatura_records_storico
(
  id integer NOT NULL DEFAULT nextval('scheda_morsicatura_id_seq'::regclass),
  id_record integer,
  id_scheda integer,
  id_indice integer,
  valore_manuale text,
  CONSTRAINT scheda_morsicatura_storico_records_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE scheda_morsicatura_records
  OWNER TO postgres;

CREATE TABLE scheda_morsicatura_storico
(
  id integer NOT NULL DEFAULT nextval('scheda_morsicatura_id_seq'::regclass),
  id_scheda integer,
  id_animale integer,
  entered timestamp without time zone,
  modified timestamp without time zone,
  entered_by integer,
  modified_by integer,
  trashed_date timestamp without time zone,
  CONSTRAINT scheda_morsicatura_storico_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE scheda_morsicatura_storico
  OWNER TO postgres;


select * from permission order by permission_id desc limit 50

insert into permission(permission_id,                                 category_id,           permission, permission_view,permission_add,permission_edit,permission_delete,                                       description, enabled, active) 
                values((select max(permission_id)+1 from permission),          32, 'schedamorsicatura',            true,          true,          false,            false,'Permesso per gestire le schede morsicatura cani',    true,   true);
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (23, 593, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (28, 593, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (26, 593, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (25, 593, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (33, 593, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (27, 593, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (22, 593, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (34, 593, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (1, 593, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (21, 593, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (18, 593, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (6, 593, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (5, 593, true, true );
INSERT INTO role_permission(role_id, permission_id, role_view, role_add) VALUES (20, 593, true, true );


update permission set permission_offline_delete = true, permission_offline_add = true, permission_offline_edit = true,permission_offline_view = true, viewpoints = true, permission_edit = true, permission_delete = true where permission_id = 593;




CREATE TABLE scheda_morsicatura_config_valutazione
(
  da float,
  a float,
  rischio text,
  consiglio text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE scheda_morsicatura_config_valutazione
  OWNER TO postgres;


  
insert into scheda_morsicatura_config_valutazione values (0,10, 'basso', 'Potenzialmente non rischioso, da rivalutare in caso di nuovi episodi');
insert into scheda_morsicatura_config_valutazione values (10,14, 'medio', 'Prescrizioni al proprietario/detentore delle misure previste dalla normativa vigente, secondo valutazione del veterinario ASL');
insert into scheda_morsicatura_config_valutazione values (14,15.5, 'elevato', 'Prescrizioni al proprietario/detentore delle misure previste dalla normativa vigente, secondo valutazione del veterinario ASL');
insert into scheda_morsicatura_config_valutazione values (15.5,1000000, 'considerevole', 'Prescrizioni al proprietario/detentore delle misure previste dalla normativa vigente, secondo valutazione del veterinario ASL');


CREATE OR REPLACE FUNCTION public.get_valutazione_scheda_morsicatura(integer)
  RETURNS TABLE(punteggio double precision , rischio text, consiglio text) AS
$BODY$

 BEGIN
    
   RETURN QUERY 
   --Moltiplicazione *4 aggiunta come da richiesta della Pompameo - riferimento tt: 013398, Mail "scheda morsicatore: errore TT 013398" del 28/02/2019
	select (
            (   
		select sum(indice.punteggio)::integer
		from scheda_morsicatura sc 
		left join scheda_morsicatura_records record on record.id_scheda = sc.id
		left join lookup_scheda_morsicatura_indici indice on indice.id = record.id_indice and indice.valore_manuale is false
		where sc.id = $1 and sc.trashed_date is null
            ) 
            +
	    (
	       (   
		select record.valore_manuale::integer
		from scheda_morsicatura sc 
		left join scheda_morsicatura_records record on record.id_scheda = sc.id
		left join lookup_scheda_morsicatura_indici indice on indice.id = record.id_indice and indice.valore_manuale is true
		where sc.id = $1 and
			sc.trashed_date is null and
			indice.divisore is false
               ) 
               /
               (   
		select record.valore_manuale::integer
		from scheda_morsicatura sc 
		left join scheda_morsicatura_records record on record.id_scheda = sc.id
		left join lookup_scheda_morsicatura_indici indice on indice.id = record.id_indice and indice.valore_manuale is true
		where sc.id = $1 and
			sc.trashed_date is null and
			indice.divisore
               )::double precision * 4
           )
         )::double precision 
           as punteggio, 
           config.rischio,
           config.consiglio
           from scheda_morsicatura_config_valutazione config
           where (
            (   
		select sum(indice.punteggio)::integer
		from scheda_morsicatura sc 
		left join scheda_morsicatura_records record on record.id_scheda = sc.id
		left join lookup_scheda_morsicatura_indici indice on indice.id = record.id_indice and indice.valore_manuale is false
		where sc.id = $1 and sc.trashed_date is null
            )
            +
	    (
	       (   
		select record.valore_manuale::integer
		from scheda_morsicatura sc 
		left join scheda_morsicatura_records record on record.id_scheda = sc.id
		left join lookup_scheda_morsicatura_indici indice on indice.id = record.id_indice and indice.valore_manuale is true
		where sc.id = $1 and
			sc.trashed_date is null and
			indice.divisore is false
               ) 
               /
               (   
		select record.valore_manuale::integer
		from scheda_morsicatura sc 
		left join scheda_morsicatura_records record on record.id_scheda = sc.id
		left join lookup_scheda_morsicatura_indici indice on indice.id = record.id_indice and indice.valore_manuale is true
		where sc.id = $1 and
			sc.trashed_date is null and
			indice.divisore
               ) *4
           )
         )::double precision  > config.da and 
           (
            (   
		select sum(indice.punteggio)::integer
		from scheda_morsicatura sc 
		left join scheda_morsicatura_records record on record.id_scheda = sc.id
		left join lookup_scheda_morsicatura_indici indice on indice.id = record.id_indice and indice.valore_manuale is false
		where sc.id = $1 and sc.trashed_date is null
            )
            +
	    (
	       (   
		select record.valore_manuale::integer
		from scheda_morsicatura sc 
		left join scheda_morsicatura_records record on record.id_scheda = sc.id
		left join lookup_scheda_morsicatura_indici indice on indice.id = record.id_indice and indice.valore_manuale is true
		where sc.id = $1 and
			sc.trashed_date is null and
			indice.divisore is false
               ) 
               /
               (   
		select record.valore_manuale::integer
		from scheda_morsicatura sc 
		left join scheda_morsicatura_records record on record.id_scheda = sc.id
		left join lookup_scheda_morsicatura_indici indice on indice.id = record.id_indice and indice.valore_manuale is true
		where sc.id = $1 and
			sc.trashed_date is null and
			indice.divisore
               ) * 4
           )
         )::double precision   <= config.a;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_valutazione_scheda_morsicatura(integer)
  OWNER TO postgres;






--Req.3 - Estrazione
drop FUNCTION public_functions.get_cc(
    anno integer,
    asl_filtro integer,
    aperta boolean,
    necroscopica boolean);
drop type public_functions.cartella_clinica ;

CREATE TYPE public_functions.cartella_clinica AS
   (numero text,
    data_apertura date,
    data_chiusura date,
    destinazione_animale text,
    username_utente_dimissioni text,
    utente_dimissioni text,
    tipo_cc text,
    necroscopica boolean,
    day_hospital boolean,
    motivo_ricovero text,
    clinica text,
    asl text,
    asl_id integer,
    microchip text,
    deceduto_non_anagrafe boolean,
    asl_animale text,
    asl_animale_id integer,
    username_utente text,
    utente text,
    specie text,
    specie_id integer,
    razza text,
    numero_accettazione text,
    num_reg_sterilizzazione integer,
    data_reinvenimento_sinantropo timestamp without time zone,
    comune_reinvenimento_sinantropo text,
    luogo_reinvenimento_sinantropo text,
    data_rilascio_sinantropo timestamp without time zone,
    comune_rilascio_sinantropo text,
    luogo_rilascio_sinantropo text,
    data_autopsia timestamp without time zone,
    data_ecg date,
    data_eco_addome date,
    data_eco_cuore date,
    data_ehrlichiosi timestamp without time zone,
    data_esame_citologico timestamp without time zone,
    data_esame_coprologico timestamp without time zone,
    data_esame_istopatologico timestamp without time zone,
    data_esame_sangue timestamp without time zone,
    data_esame_urine timestamp without time zone,
    data_felv timestamp without time zone,
    data_fiv timestamp without time zone,
    data_fip timestamp without time zone,
    data_leishmaniosi timestamp without time zone,
    data_rabbia timestamp without time zone,
    data_rickettsiosi timestamp without time zone,
    data_rx date,
    data_sterilizzazione date,
    data_tac date,
    data_toxoplasmosi timestamp without time zone);
ALTER TYPE public_functions.cartella_clinica
  OWNER TO postgres;




CREATE OR REPLACE FUNCTION public_functions.get_cc(
    anno integer,
    asl_filtro integer,
    aperta boolean,
    necroscopica boolean)
  RETURNS SETOF public_functions.cartella_clinica AS
$BODY$SELECT cc.numero,
    cc.data_apertura,
    cc.data_chiusura,
    case when specie.id=3 then dest.description_sinantropo
         else dest.description
    end as destinazione_animale, 
    ut_dimissioni.username as username_utente_dimissioni,
    ut_dimissioni.nome || ' ' || ut_dimissioni.cognome as utente_dimissioni,
    case 
      when cc.cc_morto then 'Necroscopica'
      when cc.day_hospital and cc.cc_morto is false then 'Day Hospital'
      when cc.day_hospital is false and cc.cc_morto is false then 'Degenza'
    end as tipo_cc,
    cc.cc_morto as necroscopica,
    cc.day_hospital as day_hospital,
    cc.ricovero_motivo as motivo_ricovero,
    cl.nome AS clinica,
    upper(asl.description::text) AS asl,
    asl.id as asl_id,
    an.identificativo AS microchip,
    an.deceduto_non_anagrafe ,
     CASE 
      WHEN an.deceduto_non_anagrafe and not acc.randagio THEN (select asl.description 
							   from lookup_comuni lc, lookup_asl asl 
							   where asl.id = lc.asl and
								      upper(lc.description) = upper(acc.proprietario_comune)
							   order by lc.id desc limit 1)
      WHEN an.deceduto_non_anagrafe and acc.randagio  THEN (select asl.description 
							   from lookup_comuni lc, lookup_asl asl 
							   where asl.id = lc.asl and
								      upper(lc.description) = upper(replace(acc.proprietario_nome, 'Sindaco del comune di ', '') )
							   order by lc.id desc limit 1)
      WHEN an.specie=3              THEN (select asl.description 
                                         from sinantropo_catture sc, lookup_comuni lc, lookup_asl asl 
                                         where lc.id = sc.comune_cattura and 
                                               sc.sinantropo = sin.id and
                                               asl.id = lc.asl 
                                         order by lc.id desc limit 1)
      ELSE '-1'
    END as asl_animale,
    CASE 
      WHEN an.deceduto_non_anagrafe and not acc.randagio THEN (select asl.id 
							   from lookup_comuni lc, lookup_asl asl 
							   where asl.id = lc.asl and
								      upper(lc.description) = upper(acc.proprietario_comune)
							   order by lc.id desc limit 1)
      WHEN an.deceduto_non_anagrafe and acc.randagio  THEN (select asl.id 
							   from lookup_comuni lc, lookup_asl asl 
							   where asl.id = lc.asl and
								      upper(lc.description) = upper(replace(acc.proprietario_nome, 'Sindaco del comune di ', '') )
							   order by lc.id desc limit 1)
      WHEN an.specie=3              THEN (select asl.id
                                         from sinantropo_catture sc, lookup_comuni lc, lookup_asl asl 
                                         where lc.id = sc.comune_cattura and 
                                               sc.sinantropo = sin.id and
                                               asl.id = lc.asl 
                                         order by lc.id desc limit 1)
      ELSE '-1'
    END as asl_animale_id,
    ut.username as username_utente,
    ut.nome || ' ' || ut.cognome as utente,
    specie.description as specie,
    specie.id as specie_id,
    case 
	when specie.id=3 then (case 
				when an.specie_sinantropo='1' then 'Uccello'
				when an.specie_sinantropo='2' then 'Mammifero'
				else 'Rettile/Anfibio'
			       end) || ' ' || an.razza_sinantropo
	else (select description from lookup_razza raz where raz.code = an.razza)
    end as razza,
    (((('ACC-'::text || cl.nome_breve::text) || '-'::text) || to_char(acc.data::timestamp with time zone, 'yyyy'::text)) || '-'::text) || to_char(acc.progressivo, 'FM00000'::text) AS numero_accettazione,
    att.id_registrazione_bdr,
    catt.data_cattura as data_reinvenimento_sinantropo,
    com_catt.description as comune_reinvenimento_sinantropo,
    catt.luogo_cattura as luogo_reinvenimento_sinantropo,
    reimm.data_reimmissione as data_rilascio_sinantropo,
    com_reimm.description as comune_rilascio_sinantropo,
    reimm.luogo_reimmissione as luogo_rilascio_sinantropo,
    autopsia.data_autopsia,
  ecg.data_richiesta,
  eco_addome.data_richiesta, 
  eco_cuore.data_richiesta, 
  ehrlichiosi.data_richiesta,
  esame_citologico.data_richiesta, 
  esame_coprologico.data_richiesta,
  esame_istopatologico.data_richiesta,
  esame_sangue.data_richiesta, 
  esame_urine.data_richiesta,
  felv.data_richiesta,
  fiv.data_richiesta, 
  fip.data_richiesta, 
  leishmaniosi.data_prelievo_leishmaniosi, 
  rabbia.data_richiesta, 
  rickettsiosi.data_richiesta, 
  rx.data_richiesta, 
  sterilizzazione.data,
  tac.data_richiesta, 
  toxoplasmosi.data_richiesta
  FROM cartella_clinica cc

  left join utenti_ ut on ut.id = cc.entered_by 
  left join utenti_ ut_dimissioni on ut_dimissioni.id = cc.dimissioni_entered_by 
  left join clinica cl on ut.clinica = cl.id 
  left join lookup_asl asl on cl.asl = asl.id 
  left join accettazione acc on cc.accettazione = acc.id 
  left join animale an on  acc.animale = an.id  
  left join lookup_specie specie on an.specie = specie.id 
  left join attivita_bdr att on att.cc  = cc.id and att.trashed_date is null and att.tipo_operazione =  9
  left join lookup_destinazione_animale dest on dest.id  = cc.destinazione_animale 
  left join sinantropo sin on (sin.numero_automatico = an.identificativo or 
         sin.mc = an.identificativo or 
         sin.numero_ufficiale = an.identificativo or 
         codice_ispra = an.identificativo)
  left join sinantropo_catture catt on catt.trashed_date is null and catt.sinantropo = sin.id and (select count(*) from sinantropo_reimmissioni reimm_temp where reimm_temp.data_reimmissione = cc.data_chiusura and reimm_temp.trashed_date is null and reimm_temp.catture = catt.id)>0
  left join sinantropo_reimmissioni reimm on reimm.data_reimmissione = cc.data_chiusura and reimm.trashed_date is null and reimm.catture = catt.id
  left join lookup_comuni com_catt  on com_catt.id   = catt.comune_cattura 
  left join lookup_comuni com_reimm on com_reimm.id  = reimm.comune_reimmissione
  left join autopsia autopsia on autopsia.cartella_clinica = cc.id and autopsia.trashed_date is null
  left join ecg ecg on ecg.cartella_clinica = cc.id and ecg.trashed_date is null
  left join eco_addome eco_addome on eco_addome.id_cartella_clinica = cc.id and eco_addome.trashed_date is null
  left join eco_cuore eco_cuore on eco_cuore.id_cartella_clinica = cc.id and eco_cuore.trashed_date is null
  left join ehrlichiosi ehrlichiosi on ehrlichiosi.cartella_clinica = cc.id and ehrlichiosi.trashed_date is null
  left join esame_citologico esame_citologico on esame_citologico.cartella_clinica = cc.id and esame_citologico.trashed_date is null
  left join esame_coprologico esame_coprologico on esame_coprologico.cartella_clinica = cc.id and esame_coprologico.trashed_date is null
  left join esame_istopatologico esame_istopatologico on esame_istopatologico.cartella_clinica = cc.id and esame_istopatologico.trashed_date is null
  left join esame_sangue esame_sangue on esame_sangue.cartella_clinica = cc.id and esame_sangue.trashed_date is null
  left join esame_urine esame_urine on esame_urine.cartella_clinica = cc.id and esame_urine.trashed_date is null
  left join felv felv on felv.cartella_clinica = cc.id and felv.trashed_date is null
  left join fiv fiv on fiv.cartella_clinica = cc.id and fiv.trashed_date is null
  left join fip fip on fip.cartella_clinica = cc.id and fip.trashed_date is null
  left join leishmaniosi leishmaniosi on leishmaniosi.cartella_clinica = cc.id and leishmaniosi.trashed_date is null
  left join rabbia rabbia on rabbia.cartella_clinica = cc.id and rabbia.trashed_date is null
  left join rickettsiosi rickettsiosi on rickettsiosi.cartella_clinica = cc.id and rickettsiosi.trashed_date is null
  left join rx rx on rx.id_cartella_clinica = cc.id and rx.trashed_date is null
  left join sterilizzazione sterilizzazione on sterilizzazione.cartella_clinica = cc.id and sterilizzazione.trashed_date is null
  left join tac tac on tac.id_cartella_clinica = cc.id and tac.trashed_date is null
  left join toxoplasmosi toxoplasmosi on toxoplasmosi.cartella_clinica = cc.id and toxoplasmosi.trashed_date is null
  WHERE cc.trashed_date IS NULL AND 
	(asl_filtro=-1 or asl.id=asl_filtro) AND
	((aperta = true and data_chiusura is null) or aperta is null) AND
	(necroscopica is null or cc_morto=necroscopica) AND
        (anno=-1 or date_part('year',cc.data_apertura)=anno)$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.get_cc(integer, integer, boolean, boolean)
  OWNER TO postgres;



