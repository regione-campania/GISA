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
-- DROP FUNCTION public.get_gruppo_ruolo_utente(integer);
CREATE OR REPLACE FUNCTION public.get_gruppo_ruolo_utente(IN _id_utente integer)
  RETURNS TABLE(id_gruppo_ruolo integer, descrizione_gruppo_ruolo text) AS
$BODY$
select g.code as id_gruppo_ruolo, g.description as descrizione_gruppo_ruolo
FROM access a
LEFT JOIN rel_gruppo_ruoli rgr on rgr.id_ruolo = a.role_id
LEFT JOIN lookup_gruppo_ruoli g on g.code = rgr.id_gruppo
where a.user_id = _id_utente and g.code in (11,15,16) 
UNION
select g.code as id_gruppo_ruolo, g.description as descrizione_gruppo_ruolo
FROM access_ext a
LEFT JOIN rel_gruppo_ruoli rgr on rgr.id_ruolo = a.role_id
LEFT JOIN lookup_gruppo_ruoli g on g.code = rgr.id_gruppo
where a.user_id = _id_utente and g.code in (11,15,16)
--UNION
--select g.code as id_gruppo_ruolo, g.description as descrizione_gruppo_ruolo
--FROM lookup_gruppo_ruoli g
--where g.code = 11
limit 1;
    
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_gruppo_ruolo_utente(integer)
  OWNER TO postgres;
  
  
-- script per ruolo CRR + patch altristabilimenti/default.jsp
  select * from permission where permission ilike '%opu%' --446 add
select * from permission where permission ilike '%gestioneanagrafica%' --730 add e view
select * from permission where permission ilike '%pratiche_suap%' --727 view
select * from permission where permission ilike '%buffer-buffer%' --1051 add
select * from permission where permission ilike '%zonecontrollo%' --399,400 add
select * from permission where permission ilike '%abusivismi%' --408,409 add
select * from permission where permission ilike '%operatoriregione%' --79118 add


update role_permission set role_add = false where permission_id in (446,730,399,400,1051,3123123,409,408,79118) and role_id in (3365,
3366,
3367,
3368,
3369,
3370,
3373);
update role_permission set role_view = false where permission_id in (727,730) and role_id in (3365,
3366,
3367,
3368,
3369,
3370,
3373);


------------------20/09-----------------------------------------------------------> + patch default.jsp
select * from permission where permission ilike '%zonecontrollo%' --399,400 add
select * from permission where permission ilike '%abusivismi%' --408,409 add
select * from permission where permission ilike '%privati%' -- 889409,889184

update role_permission set role_view = true, role_add=true where permission_id in (399,400,408,409,889409,889184) and role_id in (3365,
3366,
3367,
3368,
3369,
3370,
3373);

select * from permission where permission ilike '%trasportoanimali%' -- 7001,7002
select * from permission where permission ilike '%sbarc%' -- 379
select * from permission where permission ilike '%molluschi%' -- 379

update role_permission set role_view = false, role_add=false where permission_id in (7001,7002,379,1212) and role_id in (3365,
3366,
3367,
3368,
3369,
3370,
3373);