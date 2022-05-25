update role set role = 'CERVENE' WHERE role_id = 3368; --POLO DIDATTICO INTEGRATO-CERVENE
update role set role = 'CRESAN' WHERE role_id =  3367; --POLO DIDATTICO INTEGRATO-CRESAN
update role set role = 'CRIPAT - PAT' WHERE role_id =  3369; --POLO DIDATTICO INTEGRATO-CRIPAT - PAT
update role set role = 'CRIPAT - RIST. COLL.' WHERE role_id =  3370; --POLO DIDATTICO INTEGRATO-CRIPAT - RIST. COLL.
update role set role = 'C.Ri.S.SA.P.' WHERE role_id =  3366; --POLO DIDATTICO INTEGRATO-C.Ri.S.SA.P.
update role set role = 'CRIUV' WHERE role_id =  3365; --POLO DIDATTICO INTEGRATO-CRIUV
CREATE OR REPLACE VIEW public.lookup_qualifiche AS 
 SELECT a.code,
    a.description,
    a.short_description,
    a.default_item,
    a.level,
    a.enabled,
    a.carico_default,
    a.peso_per_somma_ui,
    a.in_dpat,
    a.view_lista_utenti_nucleo_ispettivo,
    a.gruppo,
    a.in_nucleo_ispettivo,
    a.livello_qualifiche_dpat,
    a.origine
   FROM ( SELECT role.role_id AS code,
            role.role AS description,
            role.description AS short_description,
            false AS default_item,
                CASE
                    WHEN role.in_dpat = true THEN 1
                    WHEN role.in_dpat = false AND role.super_ruolo = 1 THEN 10
                    WHEN role.super_ruolo = 2 THEN 0
                    ELSE NULL::integer
                END AS level,
            role.enabled_as_qualifica AS enabled,
            role.carico_default,
            role.peso_per_somma_ui,
            role.in_dpat,
            role.view_lista_utenti_nucleo_ispettivo,
            false AS gruppo,
            role.in_nucleo_ispettivo,
            role.livello_qualifiche_dpat,
            10 AS origine
           FROM role
          WHERE --role.role::text !~~* '%polo didattico integrato%'::text
          role.role_id not in (3365,3366,3367,3368,3369,3370,3373)
        UNION
         SELECT role.role_id AS code,
            role.role AS description,
            role.description AS short_description,
            false AS default_item,
                CASE
                    WHEN role.in_dpat = true THEN 21
                    WHEN role.in_dpat = false AND role.super_ruolo = 21 THEN 20
                    WHEN role.super_ruolo = 22 THEN 20
                    ELSE NULL::integer
                END + 20001 AS level,
            role.enabled_as_qualifica AS enabled,
            role.carico_default,
            role.peso_per_somma_ui,
            role.in_dpat,
            role.view_lista_utenti_nucleo_ispettivo,
            false AS gruppo,
            role.in_nucleo_ispettivo,
            role.livello_qualifiche_dpat,
            30 AS origine
           FROM role
          WHERE --role.role::text ~~* '%polo didattico integrato%'::text
          role.role_id in (3365,3366,3367,3368,3369,3370,3373)
        UNION
         SELECT role.role_id AS code,
            role.role AS description,
            role.description AS short_description,
            false AS default_item,
                CASE
                    WHEN role.in_dpat = true THEN 1
                    WHEN role.in_dpat = false AND role.super_ruolo = 1 THEN 10
                    WHEN role.super_ruolo = 2 THEN 0
                    ELSE NULL::integer
                END + 10001 AS level,
            role.enabled_as_qualifica AS enabled,
            role.carico_default,
            role.peso_per_somma_ui,
            role.in_dpat,
            role.view_lista_utenti_nucleo_ispettivo,
            false AS gruppo,
            role.in_nucleo_ispettivo,
            role.livello_qualifiche_dpat,
            11 AS origine
           FROM role_ext role
          WHERE role.enabled
        UNION
         SELECT 0 AS code,
            'UTENTI ASL'::character varying(80) AS description,
            'UTENTI ASL'::character varying(255) AS short_description,
            false AS default_item,
            0 AS level,
            true AS enabled,
            NULL::integer AS carico_default,
            NULL::integer AS aspeso_per_somma_ui,
            NULL::boolean AS in_dpat,
            NULL::boolean AS view_lista_utenti_nucleo_ispettivo,
            true AS gruppo,
            true AS in_nucleo_ispettivo,
            0 AS livello_qualifiche_dpat,
            0 AS origine
        UNION
         SELECT 1 AS code,
            'UTENTI EXTRA ASL'::character varying(80) AS description,
            'UTENTI EXTRA ASL'::character varying(255) AS short_description,
            false AS default_item,
            10000 AS level,
            true AS enabled,
            NULL::integer AS carico_default,
            NULL::integer AS aspeso_per_somma_ui,
            NULL::boolean AS in_dpat,
            NULL::boolean AS view_lista_utenti_nucleo_ispettivo,
            true AS gruppo,
            true AS in_nucleo_ispettivo,
            1 AS livello_qualifiche_dpat,
            1 AS origine
        UNION
         SELECT 1 AS code,
            'CENTRI DI RIFERIMENTO REGIONALI E POLO DIDATTICO'::character varying(80) AS description,
            'CENTRI DI RIFERIMENTO REGIONALI E POLO DIDATTICO'::character varying(255) AS short_description,
            false AS default_item,
            20000 AS level,
            true AS enabled,
            NULL::integer AS carico_default,
            NULL::integer AS aspeso_per_somma_ui,
            NULL::boolean AS in_dpat,
            NULL::boolean AS view_lista_utenti_nucleo_ispettivo,
            true AS gruppo,
            true AS in_nucleo_ispettivo,
            1 AS livello_qualifiche_dpat,
            1 AS origine) a
  ORDER BY a.code;

ALTER TABLE public.lookup_qualifiche
  OWNER TO postgres;
/*
select * from permission where permission ilike '%operatorinonaltrove-operatorinonaltrove%' --1955
update role_permission set role_view  = true where role_id in (3365,
3366,
3367,
3368,
3369,
3370,
3373) and permission_id = 1955
--in collaudo*/

update role_permission set role_view  = true where role_id in (3365,
3366,
3367,
3368,
3369,
3370,
3373) and permission_id in (7002);

--- db guc
update guc_ruoli set ruolo_string  = 'CERVENE' where ruolo_string = 'POLO DIDATTICO INTEGRATO-CERVENE';
update guc_ruoli set ruolo_string  = 'CRIUV' where ruolo_string = 'POLO DIDATTICO INTEGRATO-CRIUV';
update guc_ruoli set ruolo_string  = 'CRIPAT - PAT' where ruolo_string = 'POLO DIDATTICO INTEGRATO-CRIPAT - PAT';
update guc_ruoli set ruolo_string  = 'C.Ri.S.SA.P.' where ruolo_string = 'POLO DIDATTICO INTEGRATO-C.Ri.S.SA.P.';
update guc_ruoli set ruolo_string  = 'CRIPAT - RIST. COLL.' where ruolo_string = 'POLO DIDATTICO INTEGRATO-CRIPAT - RIST. COLL.';
update guc_ruoli set ruolo_string  = 'CRESAN' where ruolo_string = 'POLO DIDATTICO INTEGRATO-CRESAN';