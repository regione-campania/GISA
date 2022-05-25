-- CHI: Bartolo Sansone	
-- COSA: Script qualita dati
-- QUANDO: 19/01/2018


----------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------- GISA -------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
 
insert into permission (category_id, permission, permission_view, permission_edit, permission_add, permission_delete, description) values (12, 'qualita_dati_asl', true, false, false, false, 'Visualizzazione Qualita Dati ASL');
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete) values (1, (select permission_id from permission where permission = 'qualita_dati_asl'), true, false, false, false)

insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete) values (21, (select permission_id from permission where permission = 'qualita_dati_asl'), true, false, false, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete) values (45, (select permission_id from permission where permission = 'qualita_dati_asl'), true, false, false, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete) values (19, (select permission_id from permission where permission = 'qualita_dati_asl'), true, false, false, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete) values (46, (select permission_id from permission where permission = 'qualita_dati_asl'), true, false, false, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete) values (97, (select permission_id from permission where permission = 'qualita_dati_asl'), true, false, false, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete) values (98, (select permission_id from permission where permission = 'qualita_dati_asl'), true, false, false, false);

--Versione: DB separato

-- Database: gwh

-- DROP DATABASE gwh;

CREATE DATABASE gwh
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = disco_f
       LC_COLLATE = 'it_IT.UTF-8'
       LC_CTYPE = 'it_IT.UTF-8'
       CONNECTION LIMIT = -1;

       
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------- GWH -------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------       
-- Extension: dblink

-- DROP EXTENSION dblink;

 CREATE EXTENSION dblink
  SCHEMA public
  VERSION "1.2";

       
CREATE TABLE qualita_dati_cu_aperti(
id_asl integer,
ticketid integer,
ragione_sociale text,
tecnica_controllo text,
data_controllo timestamp without time zone);

CREATE TABLE qualita_dati_cu_sorveglianza_senza_checklist(
id_asl integer,
ticketid integer,
ragione_sociale text,
tecnica_controllo text,
data_controllo timestamp without time zone);

CREATE TABLE qualita_dati_errata_corrige(
id_asl integer,
data timestamp without time zone,
ragione_sociale text,
num_registrazione text);

CREATE TABLE qualita_dati_errata_corrige_art17(
id_asl integer,
data_articolo timestamp without time zone,
ragione_sociale text,
approval_number text,
identificativo text,
tipo_macellazione text);


----------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------- GISA -------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION public_functions.qualita_dati_get_cu_aperti(idAsl integer)
  RETURNS int AS
$BODY$
   DECLARE
numCu integer;


BEGIN
numCu := 0;

numCu:= ( SELECT *  FROM   dblink('dbname=gwh port=5432 host=dbGWHL 
                user=postgres password=postgres',
                'SELECT count(*) FROM qualita_dati_cu_aperti where id_asl ='||idAsl||';') as p(conta int));

	RETURN numCu;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.qualita_dati_get_cu_aperti(integer)
  OWNER TO postgres;
  
  
  CREATE OR REPLACE FUNCTION public_functions.qualita_dati_get_cu_sorveglianza_senza_checklist(idAsl integer)
  RETURNS int AS
$BODY$
   DECLARE
numCu integer;


BEGIN
numCu := 0;

numCu:= ( SELECT *  FROM   dblink('dbname=gwh port=5432 host=dbGWHL
                user=postgres password=postgres',
                'SELECT count(*) FROM qualita_dati_cu_sorveglianza_senza_checklist where id_asl ='||idAsl||';') as p(conta int));

	RETURN numCu;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.qualita_dati_get_cu_sorveglianza_senza_checklist(integer)
  OWNER TO postgres;
  
  
  CREATE OR REPLACE FUNCTION public_functions.qualita_dati_get_errata_corrige(idAsl integer)
  RETURNS int AS
$BODY$
   DECLARE
numEc integer;


BEGIN
numEc := 0;

numEc:= ( SELECT *  FROM   dblink('dbname=gwh port=5432 host=dbGWHL
                user=postgres password=postgres',
                'SELECT count(*) FROM qualita_dati_errata_corrige where id_asl ='||idAsl||';') as p(conta int));

	RETURN numEc;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.qualita_dati_get_errata_corrige(integer)
  OWNER TO postgres;
  
  CREATE OR REPLACE FUNCTION public_functions.qualita_dati_get_errata_corrige_art17(idAsl integer)
  RETURNS int AS
$BODY$
   DECLARE
numEc integer;


BEGIN
numEc := 0;

numEc:= ( SELECT *  FROM   dblink('dbname=gwh port=5432 host=dbGWHL
                user=postgres password=postgres',
                'SELECT count(*) FROM qualita_dati_errata_corrige_art17 where id_asl ='||idAsl||';') as p(conta int));

	RETURN numEc;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.qualita_dati_get_errata_corrige_art17(integer)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION public_functions.qualita_dati_get_cu_aperti_lista(idAsl integer)
  RETURNS TABLE(ticketid integer, ragione_sociale text, tecnica_controllo text, data_controllo timestamp without time zone) AS
$BODY$
   DECLARE

BEGIN
FOR 

ticketid, ragione_sociale, tecnica_controllo, data_controllo
in
SELECT *  FROM   dblink('dbname=gwh port=5432 host=dbGWHL 
                user=postgres password=postgres',
                'SELECT ticketid, ragione_sociale, tecnica_controllo, data_controllo FROM qualita_dati_cu_aperti where id_asl ='||idAsl||' order by data_controllo desc;') as p(ticketid int, ragione_sociale text, tecnica_controllo text, data_controllo timestamp without time zone)
LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.qualita_dati_get_cu_aperti_lista(integer)
  OWNER TO postgres;
  
  
  CREATE OR REPLACE FUNCTION public_functions.qualita_dati_get_cu_sorveglianza_senza_checklist_lista(idAsl integer)
  RETURNS TABLE(ticketid integer, ragione_sociale text, tecnica_controllo text,data_controllo timestamp without time zone) AS
$BODY$
   DECLARE

BEGIN
FOR 

ticketid, ragione_sociale, tecnica_controllo, data_controllo
in
SELECT *  FROM   dblink('dbname=gwh port=5432 host=dbGWHL 
                user=postgres password=postgres',
                'SELECT ticketid, ragione_sociale, tecnica_controllo, data_controllo FROM qualita_dati_cu_sorveglianza_senza_checklist where id_asl ='||idAsl||' order by data_controllo desc;') as p(ticketid int, ragione_sociale text, tecnica_controllo text, data_controllo timestamp without time zone)
LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.qualita_dati_get_cu_sorveglianza_senza_checklist_lista(integer)
  OWNER TO postgres;
  
  
  CREATE OR REPLACE FUNCTION public_functions.qualita_dati_get_errata_corrige_lista(idAsl integer)
  RETURNS TABLE(data timestamp without time zone, ragione_sociale text, num_registrazione text) AS
$BODY$
   DECLARE

BEGIN
FOR 

data, ragione_sociale, num_registrazione
in
 SELECT *  FROM   dblink('dbname=gwh port=5432 host=dbGWHL
                user=postgres password=postgres',
                'SELECT data, ragione_sociale, num_registrazione FROM qualita_dati_errata_corrige where id_asl ='||idAsl||' order by data desc;') as p(data timestamp without time zone, ragione_sociale text, num_registrazione text)
LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.qualita_dati_get_errata_corrige_lista(integer)
  OWNER TO postgres;
  
  
  CREATE OR REPLACE FUNCTION public_functions.qualita_dati_get_errata_corrige_art17_lista(idAsl integer)
  RETURNS TABLE(data_articolo timestamp without time zone, identificativo text, tipo_macellazione text, ragione_sociale text, approval_number text) AS
$BODY$
   DECLARE

BEGIN
FOR 

data_articolo, identificativo, tipo_macellazione, ragione_sociale, approval_number
in
 SELECT *  FROM   dblink('dbname=gwh port=5432 host=dbGWHL
                user=postgres password=postgres',
                'SELECT data_articolo, identificativo, tipo_macellazione, ragione_sociale, approval_number
 FROM qualita_dati_errata_corrige_art17 where id_asl ='||idAsl||' order by data_articolo desc;') as p(data_articolo timestamp without time zone, identificativo text, tipo_macellazione text, ragione_sociale text, approval_number text)
LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.qualita_dati_get_errata_corrige_art17_lista(integer)
  OWNER TO postgres;
  
CREATE OR REPLACE FUNCTION public_functions.qualita_dati_get_cu_totali(idAsl integer)
  RETURNS int AS
$BODY$
   DECLARE
numCu integer;


BEGIN
numCu := 0;

numCu:= (select count(*) from ticket where tipologia = 3 and trashed_date is null and site_id = idAsl );

	RETURN numCu;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.qualita_dati_get_cu_totali(integer)
  OWNER TO postgres;
  
 
  CREATE OR REPLACE FUNCTION public_functions.qualita_dati_get_cu_sorveglianza_totali(idAsl integer)
  RETURNS int AS
$BODY$
   DECLARE
numCu integer;


BEGIN
numCu := 0;

numCu:= (select count(*) from ticket where tipologia = 3 and provvedimenti_prescrittivi = 5 and trashed_date is null and site_id = idAsl);

	RETURN numCu;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.qualita_dati_get_cu_sorveglianza_totali(integer)
  OWNER TO postgres;  
  
 CREATE OR REPLACE FUNCTION public_functions.qualita_dati_get_errata_corrige_mese_precedente(numMese integer, idAsl integer)
  RETURNS int AS
$BODY$
   DECLARE
numEc integer;

BEGIN
numEc := 0;

numEc:= ( select count(*) from richieste_errata_corrige
where data > now() - '1 month'::interval*numMese and data < now() - '1 month'::interval*(numMese-1) and id_asl = idAsl);

	RETURN numEc;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.qualita_dati_get_errata_corrige_mese_precedente(integer, integer)
  OWNER TO postgres;
  
  

CREATE OR REPLACE FUNCTION public_functions.qualita_dati_get_errata_corrige_art17_mese_precedente(numMese integer, idAsl integer)
  RETURNS int AS
$BODY$
   DECLARE
numEc integer;


BEGIN
numEc := 0;

numEc:= ( SELECT (select count(e.*)
from macelli_art17_errata_corrige e
left join ricerche_anagrafiche_old_materializzata r on r.riferimento_id = e.id_macello
where e.entered > now() - '1 month'::interval*numMese and  e.entered < now() - '1 month'::interval*(numMese-1)   and r.asl_rif = idAsl
) + 
(
select count(*)
from macelli_art17_errata_corrige_ovicaprini e
left join ricerche_anagrafiche_old_materializzata r on r.riferimento_id = e.id_macello
where e.entered > now() - '1 month'::interval*numMese and  e.entered < now() - '1 month'::interval*(numMese-1)   and r.asl_rif = idAsl));

	RETURN numEc;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.qualita_dati_get_errata_corrige_art17_mese_precedente(integer, integer)
  OWNER TO postgres;
  
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------- GWH -------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------------------------------------------------------  
  
  CREATE OR REPLACE FUNCTION qualita_dati_aggiorna_cu_aperti()
  RETURNS INT AS
$BODY$
   DECLARE
tot int;
BEGIN
tot:= 0;
raise info '%', 'cancello';
delete from qualita_dati_cu_aperti;
raise info '%', 'inserisco';
insert into qualita_dati_cu_aperti 
 SELECT *  FROM   dblink('dbname=gisa port=5432 host=dbGISAL 
                user=postgres password=postgres',
                'select distinct t.site_id, t.ticketid, r.ragione_sociale, tipo.description as tecnica, t.assigned_date as data_controllo 
from ticket t
join lookup_tipo_controllo tipo on tipo.code = t.provvedimenti_prescrittivi
join ricerche_anagrafiche_old_materializzata r on
(t.org_id = r.riferimento_id and r.riferimento_id_nome =''orgId'')
or (t.id_stabilimento = r.riferimento_id and r.riferimento_id_nome =''stabId'')
or (t.alt_id = r.riferimento_id and r.riferimento_id_nome =''altId'')
where t.tipologia = 3 and t.status_id in (1,3) and t.trashed_date is null') as p(site_id int, ticketid int, ragione_sociale text, tecnica_controllo text, data_controllo timestamp without time zone);

tot := (select count(*) from qualita_dati_cu_aperti);
	RETURN tot;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION qualita_dati_aggiorna_cu_aperti()
  OWNER TO postgres;
  
  CREATE OR REPLACE FUNCTION qualita_dati_aggiorna_cu_sorveglianza_senza_checklist()
  RETURNS INT AS
$BODY$
   DECLARE
tot int;
BEGIN
tot:= 0;
raise info '%', 'cancello';
delete from qualita_dati_cu_sorveglianza_senza_checklist;
raise info '%', 'inserisco';
insert into qualita_dati_cu_sorveglianza_senza_checklist 
 SELECT *  FROM   dblink('dbname=gisa port=5432 host=dbGISAL 
                user=postgres password=postgres',
                'select distinct t.site_id, t.ticketid, r.ragione_sociale, tipo.description as tecnica, t.assigned_date as data_controllo
from ticket t
join lookup_tipo_controllo tipo on tipo.code = t.provvedimenti_prescrittivi
join ricerche_anagrafiche_old_materializzata r on
(t.org_id = r.riferimento_id and r.riferimento_id_nome =''orgId'')
or (t.id_stabilimento = r.riferimento_id and r.riferimento_id_nome =''stabId'')
or (t.alt_id = r.riferimento_id and r.riferimento_id_nome =''altId'')
where t.tipologia = 3 and t.provvedimenti_prescrittivi = 5 and t.trashed_date is null and LPAD(t.ticketid::text, 6, ''0'') not in (select id_controllo from audit where trashed_date is null)') as p(site_id int, ticketid int, ragione_sociale text, tecnica_controllo text, data_controllo timestamp without time zone);

tot := (select count(*) from qualita_dati_cu_sorveglianza_senza_checklist);
	RETURN tot;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION qualita_dati_aggiorna_cu_sorveglianza_senza_checklist()
  OWNER TO postgres;
  
  CREATE OR REPLACE FUNCTION qualita_dati_aggiorna_errata_corrige()
  RETURNS INT AS
$BODY$
   DECLARE
tot int;
BEGIN
tot:= 0;
raise info '%', 'cancello';
delete from qualita_dati_errata_corrige;
raise info '%', 'inserisco';
insert into qualita_dati_errata_corrige 
 SELECT *  FROM   dblink('dbname=gisa port=5432 host=dbGISAL 
                user=postgres password=postgres',
                'select distinct e.id_asl, e.data, COALESCE(r.ragione_sociale, r2.ragione_sociale) as ragione_sociale, COALESCE(r.n_reg, r2.n_reg) as num_registrazione
from richieste_errata_corrige e
left join ricerche_anagrafiche_old_materializzata r on e.riferimento_id = r.riferimento_id and e.riferimento_id_nome_tab = ''opu_stabilimento'' and r.riferimento_id_nome =''stabId''
left join ricerche_anagrafiche_old_materializzata r2 on e.riferimento_id = r2.riferimento_id and e.riferimento_id_nome_tab = ''organization'' and r2.riferimento_id_nome =''orgId''
where e.data > now() - ''1 month''::interval') as p(id_asl_stabilimento integer, data timestamp without time zone, ragione_sociale text, num_registrazione text);

tot := (select count(*) from qualita_dati_errata_corrige);
	RETURN tot;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION qualita_dati_aggiorna_errata_corrige()
  OWNER TO postgres;
  
  
  CREATE OR REPLACE FUNCTION qualita_dati_aggiorna_errata_corrige_art17()
  RETURNS INT AS
$BODY$
   DECLARE
tot int;
BEGIN
tot:= 0;
raise info '%', 'cancello';
delete from qualita_dati_errata_corrige_art17;
raise info '%', 'inserisco';
insert into qualita_dati_errata_corrige_art17 
 SELECT *  FROM   dblink('dbname=gisa port=5432 host=dbGISAL 
                user=postgres password=postgres',
                '
select distinct r.asl_rif as id_asl,  e.entered as data_articolo, r.ragione_sociale,  r.num_riconoscimento as approval_number, c.cd_matricola as identificativo, ''CAPO'' as tipo_macellazione
from macelli_art17_errata_corrige e
left join ricerche_anagrafiche_old_materializzata r on r.riferimento_id = e.id_macello
left join m_capi c on c.id = e.id_capo
where e.entered > now() - ''1 month''::interval

UNION

select distinct r.asl_rif as id_asl,  e.entered as data_articolo, r.ragione_sociale,  r.num_riconoscimento as approval_number, p.cd_partita as identificativo, ''PARTITA'' as tipo_macellazione
from macelli_art17_errata_corrige_ovicaprini e
left join ricerche_anagrafiche_old_materializzata r on r.riferimento_id = e.id_macello
left join m_partite p on p.id = e.id_partita
where e.entered > now() - ''1 month''::interval

') as p(id_asl integer, data_articolo timestamp without time zone, ragione_sociale text, approval_number text, identificativo text, tipo_macellazione text);

tot := (select count(*) from qualita_dati_errata_corrige_art17);
	RETURN tot;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION qualita_dati_aggiorna_errata_corrige_art17()
  OWNER TO postgres;
  
  -- aggiungere nel file hosts del db:
172.16.3.247 dbGISAL dbGWHL

  --infasamento
  select * from qualita_dati_aggiorna_cu_aperti();
select * from qualita_dati_aggiorna_cu_sorveglianza_senza_checklist();
select * from qualita_dati_aggiorna_errata_corrige();
select * from qualita_dati_aggiorna_errata_corrige_art17();
  
  --query temp
  
  
  
  --cu aperti

insert into qualita_dati_cu_aperti 

 select t.site_id, t.ticketid, r.ragione_sociale, tipo.description as tecnica
from ticket t
join lookup_tipo_controllo tipo on tipo.code = t.provvedimenti_prescrittivi
join ricerche_anagrafiche_old_materializzata r on 
(t.org_id = r.riferimento_id and r.riferimento_id_nome ='orgId') 
or (t.id_stabilimento = r.riferimento_id and r.riferimento_id_nome ='stabId') 
or (t.alt_id = r.riferimento_id and r.riferimento_id_nome ='altId')
where t.tipologia = 3 and t.status_id in (1,3) and t.trashed_date is null 
order by t.assigned_date desc;

-- sorveglianza senza checklist

insert into qualita_dati_cu_sorveglianza_senza_checklist 

 select t.site_id, t.ticketid, r.ragione_sociale, tipo.description as tecnica
from ticket t
join lookup_tipo_controllo tipo on tipo.code = t.provvedimenti_prescrittivi
join ricerche_anagrafiche_old_materializzata r on 
(t.org_id = r.riferimento_id and r.riferimento_id_nome ='orgId') 
or (t.id_stabilimento = r.riferimento_id and r.riferimento_id_nome ='stabId') 
or (t.alt_id = r.riferimento_id and r.riferimento_id_nome ='altId')
where t.tipologia = 3 and t.provvedimenti_prescrittivi = 5 and t.trashed_date is null and LPAD(t.ticketid::text, 6, '0') not in (select id_controllo from audit where trashed_date is null)
order by t.assigned_date desc;


--errata corrige

insert into qualita_dati_errata_corrige 

 select e.id_asl_stabilimento, e.data, r.ragione_sociale, r.n_reg as num_registrazione
from opu_errata_corrige e
join ricerche_anagrafiche_old_materializzata r on e.id_stabilimento = r.riferimento_id and r.riferimento_id_nome ='stabId'
where e.data > now() - '1 month'::interval
order by e.data desc;
  
-- Fine versione DB separato

