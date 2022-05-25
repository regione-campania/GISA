select * from lookup_gruppo_ruoli
select * from rel_gruppo_ruoli

delete from lookup_gruppo_ruoli where code = 16
insert into lookup_gruppo_ruoli(description, enabled, code) values('GRUPPO C.R.R.',TRUE,16);
insert into lookup_gruppo_ruoli(description, enabled) values('GRUPPO FORZE DELL''ORDINE',TRUE);
-- gestore schede supplementari ruolo 329
insert into rel_gruppo_ruoli (id_gruppo, id_ruolo) values (12,329) -- configurazione nuovo ruolo
insert into rel_gruppo_ruoli (id_gruppo, id_ruolo) values (11,120) -- configurazione ruolo infermiere


-- ottenere le descrizioni per forze dell'ordine
select description from role where role_id in (
115,-- fdo
114,-- fdo
10000005,--fdo
106,-- fdo
111,-- fdo
110,-- fdo
116,--fdo
224,--fdo
112,--fdo
1019,--fdo
1123,--fdo
223,--fdo
225,--fdo
113,-- fdo
228,--fdo
1020,--fdo
227,--fdo
226,--fdo
118,--fdo
10000000,--fdo
103, -- fdo
105, -- fdo
121,--fdo
122 --fdo
)

-- gruppo forze dell'ordine
update rel_gruppo_ruoli set id_gruppo =17 where id_ruolo in (115,-- fdo
114,-- fdo
10000005,--fdo
106,-- fdo
111,-- fdo
110,-- fdo
116,--fdo
224,--fdo
112,--fdo
1019,--fdo
1123,--fdo
223,--fdo
225,--fdo
113,-- fdo
228,--fdo
1020,--fdo
227,--fdo
226,--fdo
118,--fdo
10000000,--fdo
103, -- fdo
105, -- fdo
121,--fdo
122 --fdo
);

-- ottenere le descrizioni per i C.R.R.
select description from role where role_id in (
3366, --gruppo CRR
3368, --gruppo CRR
3369, --gruppo CRR
3370, --gruppo CRR
3367, --gruppo CRR
3365, --gruppo CRR
3373 --gruppo CRR
)
-- gruppo CRR
update rel_gruppo_ruoli set id_gruppo =16 where id_ruolo in (
3366, --gruppo CRR
3368, --gruppo CRR
3369, --gruppo CRR
3370, --gruppo CRR
3367, --gruppo CRR
3365, --gruppo CRR
3373 --gruppo CRR
);


select role, description from role where role_id in (31, --togliere orsa extra asl?
324, --nu re cu
54, -- criuv
109,--extra asl
107,--extra asl
117,--extra asl
104,--extra asl
108 --extra asl
)
union
select role, description from role_ext where role_id in (31, --togliere orsa extra asl?
324, --nu re cu
54, -- criuv
109,--extra asl
107,--extra asl
117,--extra asl
104,--extra asl
108 --extra asl
)

-- aggiornamenti dbi SOLO C.R.R.
CREATE OR REPLACE FUNCTION digemon.get_controlli_crr(
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
-- ID CONTROLLI CON RUOLI EXTRA ASL NEL NUCLEO
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
and lgr.code = 16 

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
and lgr.code <> 16 --rgr.id_ruolo not in (select role_id from role where description ilike '%polo%' and enabled) -- ID RUOLI DIVERSI DA POLI CRR
)
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
ALTER FUNCTION digemon.get_controlli_crr(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION digemon.get_controlli_extra_asl_fdo(
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
and lgr.code = 17 -- TUTTO IL GRUPPO EXTRA ASL FORZE DELL'ORDINE

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
and lgr.code <> 17)

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
ALTER FUNCTION digemon.get_controlli_extra_asl_fdo(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION digemon.get_controlli_congiunti_asl_extra_asl_fdo(
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
-- ID CONTROLLI CON RUOLI ASL NEL NUCLEO
--Amministrativi ASL
--Medico - Responsabile Struttura Complessa
--Medico - Responsabile Struttura Semplice
--Medico Veterinario - Responsabile Struttura Complessa
--Medico Veterinario - Responsabile Struttura Semplice
--Medico - Responsabile Struttura Semplice Dipartimentale
--Medico Veterinario - Responsabile Struttura Semplice Dipartimentale
--Medico
--Medico Veterinario
--Altro Funzionario Laureato
--MEDICI SPECIALISTI AMBULATORIALI
--medici veterinari specialisti
--T.P.A.L

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
and lgr.code = 11 -- GRUPPO ASL


INTERSECT
--FORZE DELL''ORDINE

-- ID CONTROLLI CON RUOLI EXTRA ASL NEL NUCLEO
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
and lgr.code = 17
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
ALTER FUNCTION digemon.get_controlli_congiunti_asl_extra_asl_fdo(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

  CREATE OR REPLACE FUNCTION digemon.get_controlli_congiunti_asl_crr(
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
-- ID CONTROLLI CON RUOLI ASL NEL NUCLEO
--Amministrativi ASL
--Medico - Responsabile Struttura Complessa
--Medico - Responsabile Struttura Semplice
--Medico Veterinario - Responsabile Struttura Complessa
--Medico Veterinario - Responsabile Struttura Semplice
--Medico - Responsabile Struttura Semplice Dipartimentale
--Medico Veterinario - Responsabile Struttura Semplice Dipartimentale
--Medico
--Medico Veterinario
--Altro Funzionario Laureato
--MEDICI SPECIALISTI AMBULATORIALI
--medici veterinari specialisti
--T.P.A.L

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
and lgr.code = 11 -- GRUPPO ASL


INTERSECT
--POLO DIDATTICO INTEGRATO-C.Ri.S.SA.P.
--POLO DIDATTICO INTEGRATO-CERVENE
--POLO DIDATTICO INTEGRATO-CRIPAT - PAT
--POLO DIDATTICO INTEGRATO-CRIPAT - RIST. COLL.
--POLO DIDATTICO INTEGRATO-CRESAN
--POLO DIDATTICO INTEGRATO-CRIUV
--POLO DIDATTICO INTEGRATO

-- ID CONTROLLI CON RUOLI C.R.R. NEL NUCLEO
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
and lgr.code = 16
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
ALTER FUNCTION digemon.get_controlli_congiunti_asl_crr(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

select * from digemon.get_controlli_congiunti_asl_crr('2021-01-01','2021-12-31')
select * from digemon.get_controlli_congiunti_asl_extra_asl_fdo('2021-01-01','2021-12-31')
select * from digemon.get_controlli_extra_asl_fdo('2021-01-01','2021-12-31')
select * from digemon.get_controlli_crr('2021-01-01','2021-12-31')