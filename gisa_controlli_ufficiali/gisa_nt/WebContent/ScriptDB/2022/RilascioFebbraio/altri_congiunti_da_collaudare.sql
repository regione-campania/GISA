-- prendere le versioni dal filesystem o dal db coll
-- Function: digemon.get_controlli_extra_asl(timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION digemon.get_controlli_extra_asl(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION digemon.get_controlli_extra_asl(
    IN _data_1 timestamp without time zone DEFAULT '2008-01-01 00:00:00'::timestamp without time zone,
    IN _data_2 timestamp without time zone DEFAULT NULL::timestamp without time zone)
  RETURNS TABLE(id_controllo_ufficiale integer, id_tecnica_cu integer, nome_componente_nucleo text, ruolo_componente_nucleo character varying, gruppo_ruolo_componente_nucleo text) AS
$BODY$
DECLARE
BEGIN
for
	id_controllo_ufficiale,
	id_tecnica_cu,
	nome_componente_nucleo,
	ruolo_componente_nucleo,
	gruppo_ruolo_componente_nucleo	
in 
select
distinct 
t.ticketid as id_controllo_ufficiale,
t.provvedimenti_prescrittivi as id_tecnica_cu
--,asl.description as asl
--,t.assigned_date as data_inizio_controllo
--,t.data_fine_controllo
, CASE WHEN c.contact_id > 0 then concat_ws(' ', c.namefirst, c.namelast)
       WHEN ce.contact_id > 0 then concat_ws(' ', ce.namefirst, ce.namelast) end as nome_componente_nucleo
,COALESCE(r.role, re.role) as ruolo_componente_nucleo
, string_agg(lgr.description, '; ') as gruppo_ruolo_componente_nucleo
from ticket t
left join lookup_site_id asl on asl.code = t.site_id
join cu_nucleo nucleo on t.ticketid = nucleo.id_controllo_ufficiale and nucleo.enabled
left join access_ a on a.user_id = nucleo.id_componente
left join access_ext_ ae on ae.user_id = nucleo.id_componente
left join contact_ c on c.contact_id = a.contact_id
left join contact_ext_ ce on ce.contact_id = ae.contact_id
left join role r on r.role_id = a.role_id
left join role_ext re on re.role_id = ae.role_id
left join rel_gruppo_ruoli rgr on rgr.id_ruolo = COALESCE(r.role_id, re.role_id)
left join lookup_gruppo_ruoli lgr on lgr.code = rgr.id_gruppo
where t.ticketid in (
select
t.ticketid
from ticket t
join cu_nucleo nucleo on t.ticketid = nucleo.id_controllo_ufficiale and nucleo.enabled
left join access_ a on a.user_id = nucleo.id_componente
left join access_ext_ ae on ae.user_id = nucleo.id_componente
join rel_gruppo_ruoli rgr on rgr.id_ruolo = COALESCE(a.role_id, ae.role_id)
join lookup_gruppo_ruoli lgr on lgr.code = rgr.id_gruppo
where t.trashed_date is null and t.tipologia = 3 
and (t.assigned_date between coalesce (_data_1,'2008-01-01') and coalesce (_data_2,now()))
and lgr.code = 12 -- TUTTO IL GRUPPO EXTRA ASL

except 

(select
t.ticketid
from ticket t
join cu_nucleo nucleo on t.ticketid = nucleo.id_controllo_ufficiale and nucleo.enabled
left join access_ a on a.user_id = nucleo.id_componente
left join access_ext_ ae on ae.user_id = nucleo.id_componente
join rel_gruppo_ruoli rgr on rgr.id_ruolo = COALESCE(a.role_id, ae.role_id)
join lookup_gruppo_ruoli lgr on lgr.code = rgr.id_gruppo
where t.trashed_date is null and t.tipologia = 3 
and (t.assigned_date between coalesce (_data_1,'2008-01-01') and coalesce (_data_2,now()))
and lgr.code <> 12)

)
group by t.ticketid, asl.description, c.contact_id, ce.contact_id, r.role, re.role
order by t.ticketid asc

   LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
  
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.get_controlli_extra_asl(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

  -- Function: digemon.get_controlli_congiunti_fdo_crr(timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION digemon.get_controlli_congiunti_fdo_crr(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION digemon.get_controlli_congiunti_fdo_crr(
    IN _data_1 timestamp without time zone DEFAULT '2008-01-01 00:00:00'::timestamp without time zone,
    IN _data_2 timestamp without time zone DEFAULT NULL::timestamp without time zone)
  RETURNS TABLE(id_controllo_ufficiale integer, id_tecnica_cu integer, nome_componente_nucleo text, ruolo_componente_nucleo character varying, gruppo_ruolo_componente_nucleo text) AS
$BODY$
DECLARE
BEGIN
for
	id_controllo_ufficiale,
	id_tecnica_cu,
	nome_componente_nucleo,
	ruolo_componente_nucleo,
	gruppo_ruolo_componente_nucleo	
in 
select
distinct 
t.ticketid as id_controllo_ufficiale,
t.provvedimenti_prescrittivi as id_tecnica_cu
--,asl.description as asl
--,t.assigned_date as data_inizio_controllo
--,t.data_fine_controllo
, CASE WHEN c.contact_id > 0 then concat_ws(' ', c.namefirst, c.namelast)
       WHEN ce.contact_id > 0 then concat_ws(' ', ce.namefirst, ce.namelast) end as nome_componente_nucleo
,COALESCE(r.role, re.role) as ruolo_componente_nucleo
, string_agg(lgr.description, '; ') as gruppo_ruolo_componente_nucleo
from ticket t
left join lookup_site_id asl on asl.code = t.site_id
join cu_nucleo nucleo on t.ticketid = nucleo.id_controllo_ufficiale and nucleo.enabled
left join access_ a on a.user_id = nucleo.id_componente
left join access_ext_ ae on ae.user_id = nucleo.id_componente
left join contact_ c on c.contact_id = a.contact_id
left join contact_ext_ ce on ce.contact_id = ae.contact_id
left join role r on r.role_id = a.role_id
left join role_ext re on re.role_id = ae.role_id
left join rel_gruppo_ruoli rgr on rgr.id_ruolo = COALESCE(r.role_id, re.role_id)
left join lookup_gruppo_ruoli lgr on lgr.code = rgr.id_gruppo
where t.ticketid in (
-- ID CONTROLLI CON RUOLI CRR NEL NUCLEO
(select
t.ticketid
from ticket t
join cu_nucleo nucleo on t.ticketid = nucleo.id_controllo_ufficiale and nucleo.enabled
left join access_ a on a.user_id = nucleo.id_componente
left join access_ext_ ae on ae.user_id = nucleo.id_componente
join rel_gruppo_ruoli rgr on rgr.id_ruolo = COALESCE(a.role_id, ae.role_id)
join lookup_gruppo_ruoli lgr on lgr.code = rgr.id_gruppo
where t.trashed_date is null and t.tipologia = 3 
and (t.assigned_date between coalesce (_data_1,'2008-01-01') and coalesce (_data_2,now()))
and lgr.code = 16

INTERSECT
-- ID CONTROLLI CON RUOLI forze dell'ordine
--GUARDIA DI FINANZA
--NOE
--POLIZIA MUNICIPALE
--NAS NA
--NAC
--NAS CE
--POLIZIA PROVINCIALE
--C.F.S. AV
--I.C.Q.
--Carabinieri di Comando/St
--CORPO FORESTALE DELLO STATO
--C.F.S. CE
--NAS SA
--AGENZIA DELLE DOGANE
--USMAF
--C.F.S. SA
--C.F.S. NA
--Polizia Stradale
--GUARDIA COSTIERA
--C.F.S. BN
--POLIZIA
--PIF
select
t.ticketid
from ticket t
join cu_nucleo nucleo on t.ticketid = nucleo.id_controllo_ufficiale and nucleo.enabled
left join access_ a on a.user_id = nucleo.id_componente
left join access_ext_ ae on ae.user_id = nucleo.id_componente
join rel_gruppo_ruoli rgr on rgr.id_ruolo = COALESCE(a.role_id, ae.role_id)
join lookup_gruppo_ruoli lgr on lgr.code = rgr.id_gruppo
where t.trashed_date is null and t.tipologia = 3 
and (t.assigned_date between coalesce (_data_1,'2008-01-01') and coalesce (_data_2,now()))
and lgr.code = 17)

except
(select
t.ticketid
from ticket t
join cu_nucleo nucleo on t.ticketid = nucleo.id_controllo_ufficiale and nucleo.enabled
left join access_ a on a.user_id = nucleo.id_componente
left join access_ext_ ae on ae.user_id = nucleo.id_componente
join rel_gruppo_ruoli rgr on rgr.id_ruolo = COALESCE(a.role_id, ae.role_id)
join lookup_gruppo_ruoli lgr on lgr.code = rgr.id_gruppo
where t.trashed_date is null and t.tipologia = 3 
and (t.assigned_date between coalesce (_data_1,'2008-01-01') and coalesce (_data_2,now()))
and lgr.code not in (16,17))

)
group by t.ticketid, asl.description, c.contact_id, ce.contact_id, r.role, re.role
order by t.ticketid asc

   LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
  
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.get_controlli_congiunti_fdo_crr(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

  -- Function: digemon.get_controlli_congiunti_crr_extra_asl(timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION digemon.get_controlli_congiunti_crr_extra_asl(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION digemon.get_controlli_congiunti_crr_extra_asl(
    IN _data_1 timestamp without time zone DEFAULT '2008-01-01 00:00:00'::timestamp without time zone,
    IN _data_2 timestamp without time zone DEFAULT NULL::timestamp without time zone)
  RETURNS TABLE(id_controllo_ufficiale integer, id_tecnica_cu integer, nome_componente_nucleo text, ruolo_componente_nucleo character varying, gruppo_ruolo_componente_nucleo text) AS
$BODY$
DECLARE
BEGIN
for
	id_controllo_ufficiale,
	id_tecnica_cu,
	nome_componente_nucleo,
	ruolo_componente_nucleo,
	gruppo_ruolo_componente_nucleo	
in 
select
distinct 
t.ticketid as id_controllo_ufficiale,
t.provvedimenti_prescrittivi as id_tecnica_cu
--,asl.description as asl
--,t.assigned_date as data_inizio_controllo
--,t.data_fine_controllo
, CASE WHEN c.contact_id > 0 then concat_ws(' ', c.namefirst, c.namelast)
       WHEN ce.contact_id > 0 then concat_ws(' ', ce.namefirst, ce.namelast) end as nome_componente_nucleo
,COALESCE(r.role, re.role) as ruolo_componente_nucleo
, string_agg(lgr.description, '; ') as gruppo_ruolo_componente_nucleo
from ticket t
left join lookup_site_id asl on asl.code = t.site_id
join cu_nucleo nucleo on t.ticketid = nucleo.id_controllo_ufficiale and nucleo.enabled
left join access_ a on a.user_id = nucleo.id_componente
left join access_ext_ ae on ae.user_id = nucleo.id_componente
left join contact_ c on c.contact_id = a.contact_id
left join contact_ext_ ce on ce.contact_id = ae.contact_id
left join role r on r.role_id = a.role_id
left join role_ext re on re.role_id = ae.role_id
left join rel_gruppo_ruoli rgr on rgr.id_ruolo = COALESCE(r.role_id, re.role_id)
left join lookup_gruppo_ruoli lgr on lgr.code = rgr.id_gruppo
where t.ticketid in (
-- ID CONTROLLI CON RUOLI EXTRA ASL NEL NUCLEO
(select
t.ticketid
from ticket t
join cu_nucleo nucleo on t.ticketid = nucleo.id_controllo_ufficiale and nucleo.enabled
left join access_ a on a.user_id = nucleo.id_componente
left join access_ext_ ae on ae.user_id = nucleo.id_componente
join rel_gruppo_ruoli rgr on rgr.id_ruolo = COALESCE(a.role_id, ae.role_id)
join lookup_gruppo_ruoli lgr on lgr.code = rgr.id_gruppo
where t.trashed_date is null and t.tipologia = 3 
and (t.assigned_date between coalesce (_data_1,'2008-01-01') and coalesce (_data_2,now()))
and lgr.code = 12

INTERSECT
select
t.ticketid
from ticket t
join cu_nucleo nucleo on t.ticketid = nucleo.id_controllo_ufficiale and nucleo.enabled
left join access_ a on a.user_id = nucleo.id_componente
left join access_ext_ ae on ae.user_id = nucleo.id_componente
join rel_gruppo_ruoli rgr on rgr.id_ruolo = COALESCE(a.role_id, ae.role_id)
join lookup_gruppo_ruoli lgr on lgr.code = rgr.id_gruppo
where t.trashed_date is null and t.tipologia = 3 
and (t.assigned_date between coalesce (_data_1,'2008-01-01') and coalesce (_data_2,now()))
and lgr.code = 16) --CRR
except
(select
t.ticketid
from ticket t
join cu_nucleo nucleo on t.ticketid = nucleo.id_controllo_ufficiale and nucleo.enabled
left join access_ a on a.user_id = nucleo.id_componente
left join access_ext_ ae on ae.user_id = nucleo.id_componente
join rel_gruppo_ruoli rgr on rgr.id_ruolo = COALESCE(a.role_id, ae.role_id)
join lookup_gruppo_ruoli lgr on lgr.code = rgr.id_gruppo
where t.trashed_date is null and t.tipologia = 3 
and (t.assigned_date between coalesce (_data_1,'2008-01-01') and coalesce (_data_2,now()))
and lgr.code not in (12,16))

)
group by t.ticketid, asl.description, c.contact_id, ce.contact_id, r.role, re.role
order by t.ticketid asc

   LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
  
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.get_controlli_congiunti_crr_extra_asl(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

  -- Function: digemon.get_controlli_congiunti_asl_extra_asl(timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION digemon.get_controlli_congiunti_asl_extra_asl(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION digemon.get_controlli_congiunti_asl_extra_asl(
    IN _data_1 timestamp without time zone DEFAULT '2008-01-01 00:00:00'::timestamp without time zone,
    IN _data_2 timestamp without time zone DEFAULT NULL::timestamp without time zone)
  RETURNS TABLE(id_controllo_ufficiale integer, id_tecnica_cu integer, nome_componente_nucleo text, ruolo_componente_nucleo character varying, gruppo_ruolo_componente_nucleo text) AS
$BODY$
DECLARE
BEGIN
for
	id_controllo_ufficiale,
	id_tecnica_cu,
	nome_componente_nucleo,
	ruolo_componente_nucleo,
	gruppo_ruolo_componente_nucleo	
in 
select
distinct 
t.ticketid as id_controllo_ufficiale,
t.provvedimenti_prescrittivi as id_tecnica_cu
, CASE WHEN c.contact_id > 0 then concat_ws(' ', c.namefirst, c.namelast)
       WHEN ce.contact_id > 0 then concat_ws(' ', ce.namefirst, ce.namelast) end as nome_componente_nucleo
,COALESCE(r.role, re.role) as ruolo_componente_nucleo
, string_agg(lgr.description, '; ') as gruppo_ruolo_componente_nucleo
from ticket t
left join lookup_site_id asl on asl.code = t.site_id
join cu_nucleo nucleo on t.ticketid = nucleo.id_controllo_ufficiale and nucleo.enabled
left join access_ a on a.user_id = nucleo.id_componente
left join access_ext_ ae on ae.user_id = nucleo.id_componente
left join contact_ c on c.contact_id = a.contact_id
left join contact_ext_ ce on ce.contact_id = ae.contact_id
left join role r on r.role_id = a.role_id
left join role_ext re on re.role_id = ae.role_id
left join rel_gruppo_ruoli rgr on rgr.id_ruolo = COALESCE(r.role_id, re.role_id)
left join lookup_gruppo_ruoli lgr on lgr.code = rgr.id_gruppo
where t.ticketid in (
-- ID CONTROLLI CON RUOLI ASL NEL NUCLEO
(
select
t.ticketid
from ticket t
join cu_nucleo nucleo on t.ticketid = nucleo.id_controllo_ufficiale and nucleo.enabled
left join access_ a on a.user_id = nucleo.id_componente
left join access_ext_ ae on ae.user_id = nucleo.id_componente
join rel_gruppo_ruoli rgr on rgr.id_ruolo = COALESCE(a.role_id, ae.role_id)
join lookup_gruppo_ruoli lgr on lgr.code = rgr.id_gruppo
where t.trashed_date is null and t.tipologia = 3 
--and (t.assigned_date between coalesce (_data_1,'2008-01-01') and coalesce (_data_2,now()))
and lgr.code = 11 -- GRUPPO ASL
INTERSECT
select
t.ticketid
from ticket t
join cu_nucleo nucleo on t.ticketid = nucleo.id_controllo_ufficiale and nucleo.enabled
left join access_ a on a.user_id = nucleo.id_componente
left join access_ext_ ae on ae.user_id = nucleo.id_componente
join rel_gruppo_ruoli rgr on rgr.id_ruolo = COALESCE(a.role_id, ae.role_id)
join lookup_gruppo_ruoli lgr on lgr.code = rgr.id_gruppo
where t.trashed_date is null and t.tipologia = 3 
--and (t.assigned_date between coalesce (_data_1,'2008-01-01') and coalesce (_data_2,now()))
and lgr.code = 12) -- GRUPPO EXTRA ASL


)
group by t.ticketid, asl.description, c.contact_id, ce.contact_id, r.role, re.role
order by t.ticketid asc

   LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
  
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.get_controlli_congiunti_asl_extra_asl(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

  
CREATE OR REPLACE FUNCTION digemon.get_controlli_altri_congiunti(
    IN _data_1 timestamp without time zone DEFAULT '2008-01-01 00:00:00'::timestamp without time zone,
    IN _data_2 timestamp without time zone DEFAULT NULL::timestamp without time zone)
  RETURNS TABLE(id_controllo_ufficiale integer, id_tecnica_cu integer, nome_componente_nucleo text, ruolo_componente_nucleo character varying, gruppo_ruolo_componente_nucleo text) AS
$BODY$
DECLARE
BEGIN

return query
	--select  * from digemon.get_controlli_congiunti_asl_extra_asl(_data_1,_data_2)
	--union
	select  * from digemon.get_controlli_congiunti_crr_extra_asl(_data_1,_data_2)
	union
	select  * from digemon.get_controlli_congiunti_fdo_crr(_data_1,_data_2)
	union
	select  * from digemon.get_controlli_extra_asl(_data_1,_data_2)
	order by id_controllo_ufficiale; 

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.get_controlli_altri_congiunti(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

 -- per il collaudo dbi finale
select * from digemon.get_controlli_altri_congiunti('2021-01-01','2021-12-31')

-- dbi singole per congiunti di vario tipo
select  * from digemon.get_controlli_congiunti_asl_extra_asl('2020-01-01','2021-12-31')
order by id_controllo_ufficiale

select  * from digemon.get_controlli_congiunti_crr_extra_asl('2018-01-01','2021-12-31')
order by id_controllo_ufficiale -- nessuno

select  * from digemon.get_controlli_congiunti_fdo_crr('2018-01-01','2021-12-31')
order by id_controllo_ufficiale 

select  * from digemon.get_controlli_extra_asl('2018-01-01','2021-12-31')
order by id_controllo_ufficiale 