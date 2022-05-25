CREATE OR REPLACE FUNCTION public.dbi_monitoraggio_get_programmazioni()
  RETURNS TABLE(data1 timestamp without time zone, data2 timestamp without time zone, piano_monitoraggio integer, durata integer, id integer, num_tot_cu integer, entered timestamp without time zone, modified timestamp without time zone, enteredby integer, modifiedby integer, anno integer, trashed_date timestamp without time zone, id_nodo integer, id_asl integer, cu_pianificati integer, campioni_pianificati integer, enabled boolean, short_description text, livello integer) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
FOR 

data1 , 
data2 , 
piano_monitoraggio , 
durata , id , 
num_tot_cu , 
entered , 
modified , 
enteredby , 
modifiedby , 
anno , 
trashed_date , 
id_nodo , 
id_asl , 
cu_pianificati , 
campioni_pianificati , 
enabled , 
short_description , 
livello 


in

select 
v2.anno|| '-01-01',v2.anno|| '-12-31',v2.id_piano,1 as durata,v2.id_dpat ,v2.ui_cu as num_tot_cu,null::timestamp as entered ,
null::timestamp as modified , -1 as enteredby ,-1 as modifiedby,v2.anno,null::timestamp as trashed_date ,
v2.id_struttura as id_nodo , v2.id_asl_struttura as id_asl,
caSE WHEN v2.ui_cu >0 THEN v2.ui_cu ELSE 0 END as cu ,CASE WHEN v2.ui_camp>0 THEN v2.ui_camp ELSE 0 END as campioni,true as enabled ,null::text as short,
v2.n_livello

    from
(
(

SELECT DISTINCT 

v1.n_livello,
v1.id_dpat,  
    v1.id_piano, 
    v1.anno, 
    v1.nome_piano,
    v1.id_indicatore,
    v1.id_struttura, 
    v1.descrizione_struttura, 
    v1.id_asl_struttura, 


        CASE
            WHEN  (( v1.descr_indicatore not ilike '%campion%' or v1.descr_indicatore  ilike '%ispezion%')  and valori.ui > 0) THEN valori.ui
            ELSE 0
        END AS ui_cu,

        CASE
            WHEN  ((v1.descr_indicatore ilike '%campion%' and v1.descr_indicatore  not ilike '%ispezion%') and valori.ui > 0) THEN valori.ui
            ELSE 0
        END AS ui_camp
        
   FROM ( 


   SELECT strutt.n_livello,ind.id as id_indicatore,ind.description as descr_indicatore,
upper(regexp_replace(regexp_replace(strutt.descrizione_lunga::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text)) AS descrizione_struttura
   , 
            strutt.id_asl AS id_asl_struttura, 
            dpat.id_asl AS id_asl_dpat, 
            dpat.id AS id_dpat, 
            dpat.anno, 
            strutt.id AS id_struttura,

	    lp.code as id_piano ,
            lp.description as nome_piano
            
           FROM oia_nodo strutt
      JOIN dpat_strumento_calcolo_strutture scs ON scs.id_struttura = strutt.id
   JOIN dpat_strumento_calcolo sc ON sc.id = scs.id_strumento_calcolo AND sc.completo = true , 


   lookup_piano_monitoraggio lp 
    join 	dpat_indicatore ind on lp.id_indicatore = ind.id
    JOIN dpat_attivita a ON a.id = ind.id_attivita AND a.enabled
    JOIN dpat_piano piano ON piano.id = a.id_piano AND piano.enabled
    JOIN dpat_sezione sez ON sez.id = piano.id_sezione AND sez.enabled
    JOIN dpat ON dpat.anno = sez.anno  
   
  WHERE lp.enabled and strutt.enabled AND strutt.n_livello > 1 and strutt.id_asl = dpat.id_asl


  ) v1
   LEFT JOIN dpat_struttura_indicatore valori ON valori.id_indicatore = v1.id_indicatore AND valori.id_struttura = v1.id_struttura 
   AND valori.id_dpat = v1.id_dpat AND valori.enabled
 
  ORDER BY v1.id_piano
  )
  union 




(select * from
  (
  select distinct n_livello,dpat.id as id_dpat ,
  lp.code as id_piano,dpat.anno,lp.description as nome_piano,ind.id,n.id as id_struttura,n.descrizione_lunga as descr_strutt,
n.id_asl as asl_strutt ,

case when ind.description not ilike '%campion%'or ind.description  ilike '%ispezion%'  and sum(valori.ui)>0 and sum(valori.ui) is not null then sum(valori.ui) else 0 end as ui_cu,
case when ind.description  ilike '%campion%' and ind.description not ilike '%ispezion%' and sum(valori.ui)>0 and sum(valori.ui) is not null then sum(valori.ui) else 0 end as ui_camp
from
oia_nodo n,
 lookup_piano_monitoraggio lp 
    join 	dpat_indicatore ind on lp.id_indicatore = ind.id    JOIN dpat_attivita a ON a.id = ind.id_attivita AND a.enabled
    JOIN dpat_piano piano ON piano.id = a.id_piano AND piano.enabled
    JOIN dpat_sezione sez ON sez.id = piano.id_sezione AND sez.enabled
    JOIN dpat ON dpat.anno = sez.anno  
    LEFT JOIN dpat_struttura_indicatore valori ON valori.id_indicatore = ind.id AND valori.id_dpat = dpat.id AND valori.enabled
    where n.n_livello = 1 and n.enabled  and n.id_Asl = dpat.id_asl 
group by 
n_livello,
dpat.id  ,
dpat.anno,lp.code ,lp.description ,ind.id,n.id ,n.descrizione_lunga ,
n.id_asl
)a 
) 
)v2
------------- 
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.dbi_monitoraggio_get_programmazioni()
  OWNER TO postgres;







CREATE OR REPLACE VIEW public.dpat_programmazioni_piani AS 
 SELECT DISTINCT v1.n_livello,
    v1.id_dpat,
    v1.id_piano,
    v1.anno,
    v1.nome_piano,
    v1.id_struttura,
    v1.descrizione_struttura,
    v1.id_asl_dpat,
        CASE
            WHEN (v1.descr_indicatore !~~* '%campion%'::text OR v1.descr_indicatore ~~* '%ispezion%'::text) AND valori.ui::double precision > 0::double precision THEN valori.ui
            ELSE '0'::character varying
        END AS ui_cu,
        CASE
            WHEN v1.descr_indicatore ~~* '%campion%'::text AND v1.descr_indicatore !~~* '%ispezion%'::text AND valori.ui::double precision > 0::double precision THEN valori.ui
            ELSE '0'::character varying
        END AS ui_camp,
    v1.id_indicatore,
    v1.descr_indicatore,
    v1.ordinamento,
    v1.ordinamento_figli,
    v1.pathstrutt
   FROM ( SELECT strutt.n_livello,
            ind.id AS id_indicatore,
            upper(regexp_replace(regexp_replace((((((piano.description || ' '::text) || a.description) || ' '::text) ||
                CASE
                    WHEN ind.alias IS NOT NULL THEN ind.alias
                    ELSE ''::text
                END) || '-'::text) || ind.description, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text)) AS descr_indicatore,
            upper(regexp_replace(regexp_replace(strutt.pathdes::text, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text)) AS descrizione_struttura,
            strutt.id_asl AS id_asl_struttura,
            dpat.id_asl AS id_asl_dpat,
            dpat.id AS id_dpat,
            dpat.anno,
            strutt.id AS id_struttura,
            lp.id_indicatore AS id_piano,
            upper(regexp_replace(regexp_replace((((((piano.description || ' '::text) || a.description) || ' '::text) ||
                CASE
                    WHEN ind.alias IS NOT NULL THEN ind.alias
                    ELSE ''::text
                END) || '-'::text) || ind.description, '''''|,|\r|\n|"|-| {2,}'::text, ' '::text, 'g'::text), '°'::text, '.'::text, 'g'::text)) AS nome_piano,
            piano.ordinamento,
            ind.ordinamento AS ordinamento_figli,
            (strutt.pathid::text || ';'::text) || strutt.codice_interno_fk AS pathstrutt
           FROM dpat_strutture_asl strutt
             JOIN dpat_strumento_calcolo sc ON sc.id = strutt.id_strumento_calcolo AND (strutt.stato = ANY (ARRAY[1, 2])),
            dpat_indicatore ind
             LEFT JOIN lookup_piano_monitoraggio lp ON lp.id_indicatore = ind.id
             JOIN dpat_attivita a ON a.id = ind.id_attivita AND a.enabled AND (a.stato = ANY (ARRAY[1, 2]))
             JOIN dpat_piano piano ON piano.id = a.id_piano AND piano.enabled AND (piano.stato = ANY (ARRAY[1, 2]))
             JOIN dpat_sezione sez ON sez.id = piano.id_sezione AND sez.enabled
             JOIN dpat ON dpat.anno = sez.anno AND dpat.enabled
             JOIN dpat_istanza ist ON ist.id = dpat.id_dpat_istanza
          WHERE strutt.id_asl = dpat.id_asl AND strutt.anno = dpat.anno AND ind.tipo_attivita ~~* 'piano'::text AND dpat.enabled AND
                CASE
                    WHEN ist.stato = '2'::text THEN ind.stato = 2
                    ELSE ind.stato = 1 AND ind.disabilitato = false AND piano.disabilitato = false AND a.disabilitato = false
                END
          ORDER BY piano.ordinamento, ind.ordinamento, ((strutt.pathid::text || ';'::text) || strutt.codice_interno_fk)) v1
     LEFT JOIN dpat_struttura_indicatore valori ON valori.id_indicatore = v1.id_indicatore AND valori.id_struttura = v1.id_struttura AND valori.id_dpat = v1.id_dpat AND valori.enabled
  ORDER BY v1.ordinamento, v1.ordinamento_figli, v1.pathstrutt;

ALTER TABLE public.dpat_programmazioni_piani
  OWNER TO postgres;



  CREATE OR REPLACE VIEW public.report_vista_controlli_ufficiali AS 
 SELECT vv.id_controllo_ufficiale,
    vv.riferimento_id,
    vv.riferimento_id_nome,
    vv.riferimento_id_nome_col,
    vv.riferimento_id_nome_tab,
    vv.id_asl,
    vv.asl,
    vv.tipo_controllo,
    vv.tipo_ispezione_o_audit,
    vv.tipo_piano_monitoraggio,
    vv.id_piano,
    vv.id_attivita,
    vv.tipo_controllo_bpi,
    vv.tipo_controllo_haccp,
    vv.oggetto_del_controllo,
    vv.punteggio,
    vv.data_inizio_controllo,
    vv.anno_controllo,
    vv.data_chiusura_controllo,
    vv.aggiornata_cat_controllo,
    vv.categoria_rischio,
    vv.prossimo_controllo,
    vv.tipo_checklist,
    vv.linea_attivita_sottoposta_a_controllo,
    vv.unita_operativa,
    vv.id_struttura_uo,
    vv.supervisionato_in_data,
    vv.supervisionato_da,
    vv.supervisione_note,
    vv.congruo_supervisione,
    vv.note,
    vv.tipo_piano_monitoraggio_old,
    vv.codice_interno_univoco_uo,
    vv.codice_interno_piano_attivita,
    vv.descrizione_area_struttura
   FROM ( SELECT t.ticketid AS id_controllo_ufficiale,
            o.org_id AS riferimento_id,
            'orgId'::text AS riferimento_id_nome,
            'org_id'::text AS riferimento_id_nome_col,
            'organization'::text AS riferimento_id_nome_tab,
            asl.code AS id_asl,
            asl.description AS asl,
            ltc.description AS tipo_controllo,
                CASE
                    WHEN tcu.audit_tipo > 0 AND tcu.tipo_audit <= 0 THEN lat.description::text
                    WHEN tcu.tipo_audit > 0 THEN (lat.description::text || ' - '::text) || lta.description::text
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio > 0 THEN (dpatp.description || ' '::text) || dpatatt.description
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio <= 0 AND tcu.tipoispezione <> 89 AND tcu.tipoispezione <> 2 THEN (dpatpti.description || ' '::text) || dpatattti.description
                    ELSE 'N.D'::text
                END AS tipo_ispezione_o_audit,
                CASE
                    WHEN tcu.tipoispezione > 0 AND lti.codice_interno !~~* '2a'::text THEN (indti.alias || ' : '::text) || indti.description
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 THEN (ind.alias || ' : '::text) || ind.description
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio,
                CASE
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 AND lpiani.enabled = false THEN
                    CASE
                        WHEN lpianipadre.description::text IS NOT NULL THEN lpianipadre.description::text
                        ELSE ''::text
                    END ||
                    CASE
                        WHEN lpiani.description::text IS NOT NULL THEN lpiani.description::text
                        ELSE ''::text
                    END
                    WHEN lti.codice_interno !~~* '2a'::text THEN lti.description::text
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio_old,
                CASE
                    WHEN t.assigned_date > '2015-01-01 00:00:00'::timestamp without time zone THEN lpiani.code
                    ELSE lpiani.code
                END AS id_piano,
                CASE
                    WHEN t.assigned_date > '2015-01-01 00:00:00'::timestamp without time zone THEN lti.id_indicatore
                    ELSE lti.code
                END AS id_attivita,
            v.tipo_controllo_bpi,
            v.tipo_controllo_haccp,
                CASE
                    WHEN v.azione_non_conforme IS NULL THEN 'N.D.'::character varying::text
                    WHEN btrim(v.azione_non_conforme) = ''::text THEN 'N.D.'::character varying::text
                    ELSE v.azione_non_conforme
                END AS oggetto_del_controllo,
                CASE
                    WHEN t.punteggio < 3 THEN 'Favorevole'::text
                    WHEN t.punteggio >= 3 THEN 'Non Favorevole'::text
                    ELSE 'N.D.'::text
                END AS punteggio,
            to_date(t.assigned_date::text, 'yyyy/mm/dd'::text) AS data_inizio_controllo,
                CASE
                    WHEN t.assigned_date IS NULL THEN 'N.D.'::text
                    ELSE date_part('year'::text, t.assigned_date)::text
                END AS anno_controllo,
            to_date(t.closed::text, 'yyyy/mm/dd'::text) AS data_chiusura_controllo,
                CASE
                    WHEN t.isaggiornata_categoria = true AND t.provvedimenti_prescrittivi = 5 THEN 'CONTROLLO CATEGORIZZATO'::text
                    WHEN t.isaggiornata_categoria = false AND t.provvedimenti_prescrittivi = 5 THEN 'CONTROLLO NON CATEGORIZZATO'::text
                    WHEN t.provvedimenti_prescrittivi <> 5 THEN 'NON PREVISTA CATEGORIAAZZIONE'::text
                    ELSE NULL::text
                END AS aggiornata_cat_controllo,
            t.categoria_rischio,
            t.prossimo_controllo,
                CASE
                    WHEN audit.c IS NOT NULL THEN audit.c
                    ELSE 'Non Presente'::text
                END AS tipo_checklist,
                CASE
                    WHEN o.tipologia = 1 THEN
                    CASE
                        WHEN ll.categoria IS NOT NULL AND ll.categoria::text <> ''::text THEN ll.categoria::text || ' '::text
                        ELSE ''::text
                    END ||
                    CASE
                        WHEN ll.linea_attivita::text IS NOT NULL AND ll.linea_attivita::text <> ''::text THEN ll.linea_attivita
                        ELSE ''::character varying
                    END::text
                    WHEN o.tipologia = 3 AND o.direct_bill = false THEN
                    CASE
                        WHEN lcst.description IS NOT NULL AND lcst.description <> ''::text THEN lcst.description || '-'::text
                        ELSE ''::text
                    END ||
                    CASE
                        WHEN lass.linea_attivita_stabilimenti_soa IS NOT NULL AND lass.linea_attivita_stabilimenti_soa <> ''::text THEN lass.linea_attivita_stabilimenti_soa
                        ELSE ''::text
                    END
                    WHEN o.tipologia = 3 AND o.direct_bill = true THEN 'Mercato-Ittico'::text
                    WHEN o.tipologia = 97 THEN
                    CASE
                        WHEN lcsoa.description IS NOT NULL AND lcsoa.description <> ''::text THEN lcsoa.description || '-'::text
                        ELSE ''::text
                    END ||
                    CASE
                        WHEN lass.linea_attivita_stabilimenti_soa IS NOT NULL AND lass.linea_attivita_stabilimenti_soa <> ''::text THEN lass.linea_attivita_stabilimenti_soa
                        ELSE ''::text
                    END
                    ELSE 'N.D'::text
                END AS linea_attivita_sottoposta_a_controllo,
                CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura::text
                        ELSE ''::text
                    END || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura::text
                        ELSE ''::text
                    END || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    ELSE NULL::text
                END AS unita_operativa,
                CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.descrizione_area_struttura
                    ELSE NULL::character varying(50)
                END AS descrizione_area_struttura,
                CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.id
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.id
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.id
                    ELSE '-1'::integer
                END AS id_struttura_uo,
            t.supervisionato_in_data,
                CASE
                    WHEN c.namelast IS NOT NULL AND c.namefirst IS NOT NULL THEN (c.namelast::text || c.namefirst::text)::character varying
                    WHEN c.namelast IS NOT NULL AND c.namefirst IS NULL THEN c.namelast
                    ELSE c.namefirst
                END AS supervisionato_da,
            t.supervisione_note,
                CASE
                    WHEN t.supervisione_flag_congruo THEN 'SI'::text
                    WHEN t.supervisione_flag_congruo = false THEN 'NO'::text
                    ELSE 'N.D.'::text
                END AS congruo_supervisione,
            t.problem AS note,
                CASE
                    WHEN nn.codice_interno_fk > 0 THEN nn.codice_interno_fk
                    ELSE n.codice_interno_fk
                END AS codice_interno_univoco_uo,
            COALESCE(lpiani.codice, lti.codice) AS codice_interno_piano_attivita
           FROM ticket t
             LEFT JOIN contact_ c ON c.user_id = t.supervisionato_da
             JOIN organization o ON o.org_id = t.org_id
             LEFT JOIN checklist_controlli audit ON audit.id_controllo::text = t.id_controllo_ufficiale::text
             JOIN lookup_site_id asl ON t.site_id = asl.code
             JOIN lookup_tipo_controllo ltc ON t.provvedimenti_prescrittivi = ltc.code
             LEFT JOIN tipocontrolloufficialeimprese tcu ON t.ticketid = tcu.idcontrollo AND (tcu.tipoispezione > 0 OR tcu.audit_tipo > 0 OR tcu.tipo_audit > 0) AND tcu.enabled = true
             LEFT JOIN linee_attivita_controlli_ufficiali lacc ON lacc.id_controllo_ufficiale = t.ticketid
             LEFT JOIN la_imprese_linee_attivita lail ON lail.id = lacc.id_linea_attivita
             LEFT JOIN la_rel_ateco_attivita lar ON lar.id = lail.id_rel_ateco_attivita
             LEFT JOIN la_linee_attivita ll ON ll.id = lar.id_linee_attivita
             LEFT JOIN linee_attivita_controlli_ufficiali_stab_soa lass ON lass.id_controllo_ufficiale = t.ticketid
             LEFT JOIN stabilimenti_sottoattivita sts ON sts.id_stabilimento = o.org_id AND sts.attivita = lass.linea_attivita_stabilimenti_soa
             LEFT JOIN soa_sottoattivita sos ON sos.id_soa = o.org_id AND sos.attivita = lass.linea_attivita_stabilimenti_soa
             LEFT JOIN lookup_categoria_soa lcsoa ON lcsoa.code = sos.codice_sezione
             LEFT JOIN lookup_categoria lcst ON lcst.code = sts.codice_sezione
             LEFT JOIN lookup_tipo_ispezione lti ON tcu.tipoispezione = lti.code
             LEFT JOIN lookup_audit_tipo lat ON tcu.audit_tipo = lat.code
             LEFT JOIN lookup_tipo_audit lta ON tcu.tipo_audit = lta.code
             LEFT JOIN lookup_piano_monitoraggio lpiani ON tcu.pianomonitoraggio = lpiani.code AND t.assigned_date <= COALESCE(lpiani.data_scadenza, 'now'::text::date::timestamp without time zone)
             LEFT JOIN dpat_indicatore ind ON ind.id_ = lpiani.id_indicatore
             LEFT JOIN dpat_attivita dpatatt ON dpatatt.id_ = ind.id_attivita_
             LEFT JOIN dpat_piano dpatp ON dpatp.id_ = dpatatt.id_piano_
             LEFT JOIN dpat_indicatore indti ON indti.id_ = lti.id_indicatore
             LEFT JOIN dpat_attivita dpatattti ON dpatattti.id_ = indti.id_attivita_
             LEFT JOIN dpat_piano dpatpti ON dpatpti.id_ = dpatattti.id_piano_
             LEFT JOIN lookup_piano_monitoraggio lpianipadre ON lpiani.id_padre = lpianipadre.code AND lpianipadre.id_padre <= 0
             LEFT JOIN dpat_strutture_asl n ON tcu.id_unita_operativa = n.id
             LEFT JOIN dpat_strutture_asl pp ON n.id_padre = pp.id
             LEFT JOIN unita_operative_controllo uoc ON uoc.id_controllo = t.ticketid
             LEFT JOIN dpat_strutture_asl nn ON nn.id = uoc.id_unita_operativa
             LEFT JOIN dpat_strutture_asl ppp ON nn.id_padre = ppp.id
             LEFT JOIN report_visa_tipi_controlli_ufficiali_no_funzione v ON t.ticketid = v.ticketid
          WHERE t.tipologia = 3 AND t.trashed_date IS NULL AND (o.trashed_date IS NULL OR o.trashed_date = '1970-01-01 00:00:00'::timestamp without time zone)
        UNION
         SELECT t.ticketid AS id_controllo_ufficiale,
            t.id_stabilimento AS riferimento_id,
            'stabId'::text AS riferimento_id_nome,
            'id_stabilimento'::text AS riferimento_id_nome_col,
            'opu_stabilimento'::text AS riferimento_id_nome_tab,
            asl.code AS id_asl,
            asl.description AS asl,
            ltc.description AS tipo_controllo,
                CASE
                    WHEN tcu.audit_tipo > 0 AND tcu.tipo_audit <= 0 THEN lat.description::text
                    WHEN tcu.tipo_audit > 0 THEN (lat.description::text || ' - '::text) || lta.description::text
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio > 0 THEN (dpatp.description || ' '::text) || dpatatt.description
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio <= 0 AND tcu.tipoispezione <> 89 AND tcu.tipoispezione <> 2 THEN (dpatpti.description || ' '::text) || dpatattti.description
                    ELSE 'N.D'::text
                END AS tipo_ispezione_o_audit,
                CASE
                    WHEN tcu.tipoispezione > 0 AND lti.codice_interno !~~* '2a'::text THEN (indti.alias || ' : '::text) || indti.description
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 THEN (ind.alias || ' : '::text) || ind.description
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio,
                CASE
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 AND lpiani.enabled = false THEN
                    CASE
                        WHEN lpianipadre.description::text IS NOT NULL THEN lpianipadre.description::text
                        ELSE ''::text
                    END ||
                    CASE
                        WHEN lpiani.description::text IS NOT NULL THEN lpiani.description::text
                        ELSE ''::text
                    END
                    WHEN lti.codice_interno !~~* '2a'::text THEN lti.description::text
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio_old,
                CASE
                    WHEN t.assigned_date > '2015-01-01 00:00:00'::timestamp without time zone THEN lpiani.code
                    ELSE lpiani.code
                END AS id_piano,
                CASE
                    WHEN t.assigned_date > '2015-01-01 00:00:00'::timestamp without time zone THEN lti.id_indicatore
                    ELSE lti.code
                END AS id_attivita,
            v.tipo_controllo_bpi,
            v.tipo_controllo_haccp,
                CASE
                    WHEN v.azione_non_conforme IS NULL THEN 'N.D.'::character varying::text
                    WHEN btrim(v.azione_non_conforme) = ''::text THEN 'N.D.'::character varying::text
                    ELSE v.azione_non_conforme
                END AS oggetto_del_controllo,
                CASE
                    WHEN t.punteggio < 3 THEN 'Favorevole'::text
                    WHEN t.punteggio >= 3 THEN 'Non Favorevole'::text
                    ELSE 'N.D.'::text
                END AS punteggio,
            to_date(t.assigned_date::text, 'yyyy/mm/dd'::text) AS data_inizio_controllo,
                CASE
                    WHEN t.assigned_date IS NULL THEN 'N.D.'::text
                    ELSE date_part('year'::text, t.assigned_date)::text
                END AS anno_controllo,
            to_date(t.closed::text, 'yyyy/mm/dd'::text) AS data_chiusura_controllo,
                CASE
                    WHEN t.isaggiornata_categoria = true AND t.provvedimenti_prescrittivi = 5 THEN 'CONTROLLO CATEGORIZZATO'::text
                    WHEN t.isaggiornata_categoria = false AND t.provvedimenti_prescrittivi = 5 THEN 'CONTROLLO NON CATEGORIZZATO'::text
                    WHEN t.provvedimenti_prescrittivi <> 5 THEN 'NON PREVISTA CATEGORIAAZZIONE'::text
                    ELSE NULL::text
                END AS aggiornata_cat_controllo,
            t.categoria_rischio,
            t.prossimo_controllo,
                CASE
                    WHEN audit.c IS NOT NULL THEN audit.c
                    ELSE 'Non Presente'::text
                END AS tipo_checklist,
            (((
                CASE
                    WHEN lattcu.macroarea IS NOT NULL THEN lattcu.macroarea
                    ELSE ''::text
                END || '|'::text) ||
                CASE
                    WHEN lattcu.aggregazione IS NOT NULL THEN lattcu.aggregazione
                    ELSE ''::text
                END) || '|'::text) || lattcu.attivita AS linea_attivita_sottoposta_a_controllo,
                CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura::text
                        ELSE ''::text
                    END || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura::text
                        ELSE ''::text
                    END || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    ELSE NULL::text
                END AS unita_operativa,
                CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.descrizione_area_struttura
                    ELSE NULL::character varying(50)
                END AS descrizione_area_struttura,
                CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.id
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.id
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.id
                    ELSE '-1'::integer
                END AS id_struttura_uo,
            t.supervisionato_in_data,
                CASE
                    WHEN c.namelast IS NOT NULL AND c.namefirst IS NOT NULL THEN (c.namelast::text || c.namefirst::text)::character varying
                    WHEN c.namelast IS NOT NULL AND c.namefirst IS NULL THEN c.namelast
                    ELSE c.namefirst
                END AS supervisionato_da,
            t.supervisione_note,
                CASE
                    WHEN t.supervisione_flag_congruo THEN 'SI'::text
                    WHEN t.supervisione_flag_congruo = false THEN 'NO'::text
                    ELSE 'N.D.'::text
                END AS congruo_supervisione,
            t.problem,
                CASE
                    WHEN nn.codice_interno_fk > 0 THEN nn.codice_interno_fk
                    ELSE n.codice_interno_fk
                END AS codice_interno_univoco_uo,
            COALESCE(lpiani.codice, lti.codice) AS codice_interno_piano_attivita
           FROM ticket t
             LEFT JOIN contact_ c ON c.user_id = t.supervisionato_da
             LEFT JOIN "linee_attivita_controlli_ufficiali" lacc ON lacc.id_controllo_ufficiale = t.ticketid
             LEFT JOIN opu_relazione_stabilimento_linee_produttive orslp ON orslp.id = lacc.id_linea_attivita
             LEFT JOIN opu_linee_attivita_nuove lattcu ON orslp.id_linea_produttiva = lattcu.id_nuova_linea_attivita
             LEFT JOIN opu_linee_attivita_nuove latt ON latt.id_nuova_linea_attivita = orslp.id_linea_produttiva
             JOIN lookup_site_id asl ON t.site_id = asl.code
             LEFT JOIN report_visa_tipi_controlli_ufficiali_no_funzione v ON t.ticketid = v.ticketid
             LEFT JOIN tipocontrolloufficialeimprese tcu ON t.ticketid = tcu.idcontrollo AND (tcu.tipoispezione > 0 OR tcu.audit_tipo > 0 OR tcu.tipo_audit > 0) AND tcu.enabled = true
             LEFT JOIN dpat_strutture_asl n ON tcu.id_unita_operativa = n.id
             LEFT JOIN dpat_strutture_asl pp ON n.id_padre = pp.id
             LEFT JOIN unita_operative_controllo uoc ON uoc.id_controllo = t.ticketid
             LEFT JOIN dpat_strutture_asl nn ON nn.id = uoc.id_unita_operativa
             LEFT JOIN dpat_strutture_asl ppp ON nn.id_padre = ppp.id
             LEFT JOIN lookup_tipo_ispezione lti ON tcu.tipoispezione = lti.code
             LEFT JOIN lookup_audit_tipo lat ON tcu.audit_tipo = lat.code
             LEFT JOIN lookup_tipo_audit lta ON tcu.tipo_audit = lta.code
             LEFT JOIN lookup_piano_monitoraggio lpiani ON tcu.pianomonitoraggio = lpiani.code AND t.assigned_date <= COALESCE(lpiani.data_scadenza, 'now'::text::date::timestamp without time zone)
             LEFT JOIN dpat_indicatore ind ON ind.id_ = lpiani.id_indicatore
             LEFT JOIN dpat_attivita dpatatt ON dpatatt.id_ = ind.id_attivita_
             LEFT JOIN dpat_piano dpatp ON dpatp.id_ = dpatatt.id_piano_
             LEFT JOIN dpat_indicatore indti ON indti.id_ = lti.id_indicatore
             LEFT JOIN dpat_attivita dpatattti ON dpatattti.id_ = indti.id_attivita_
             LEFT JOIN dpat_piano dpatpti ON dpatpti.id_ = dpatattti.id_piano_
             LEFT JOIN lookup_piano_monitoraggio lpianipadre ON lpiani.id_padre = lpianipadre.code AND lpianipadre.id_padre <= 0
             JOIN lookup_tipo_controllo ltc ON t.provvedimenti_prescrittivi = ltc.code
             LEFT JOIN checklist_controlli audit ON audit.id_controllo::text = t.id_controllo_ufficiale::text
          WHERE t.tipologia = 3 AND t.trashed_date IS NULL AND t.id_stabilimento > 0
        UNION
         SELECT t.ticketid AS id_controllo_ufficiale,
            t.id_apiario AS riferimento_id,
            'stabId'::text AS riferimento_id_nome,
            'id_stabilimento'::text AS riferimento_id_nome_col,
            'apicoltura_imprese'::text AS riferimento_id_nome_tab,
            asl.code AS id_asl,
            asl.description AS asl,
            ltc.description AS tipo_controllo,
                CASE
                    WHEN tcu.audit_tipo > 0 AND tcu.tipo_audit <= 0 THEN lat.description::text
                    WHEN tcu.tipo_audit > 0 THEN (lat.description::text || ' - '::text) || lta.description::text
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio > 0 THEN (dpatp.description || ' '::text) || dpatatt.description
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio <= 0 AND tcu.tipoispezione <> 89 AND tcu.tipoispezione <> 2 THEN (dpatpti.description || ' '::text) || dpatattti.description
                    ELSE 'N.D'::text
                END AS tipo_ispezione_o_audit,
                CASE
                    WHEN tcu.tipoispezione > 0 AND lti.codice_interno !~~* '2a'::text THEN (indti.alias || ' : '::text) || indti.description
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 THEN (ind.alias || ' : '::text) || ind.description
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio,
                CASE
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 AND lpiani.enabled = false THEN
                    CASE
                        WHEN lpianipadre.description::text IS NOT NULL THEN lpianipadre.description::text
                        ELSE ''::text
                    END ||
                    CASE
                        WHEN lpiani.description::text IS NOT NULL THEN lpiani.description::text
                        ELSE ''::text
                    END
                    WHEN lti.codice_interno !~~* '2a'::text THEN lti.description::text
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio_old,
                CASE
                    WHEN t.assigned_date > '2015-01-01 00:00:00'::timestamp without time zone THEN lpiani.code
                    ELSE lpiani.code
                END AS id_piano,
                CASE
                    WHEN t.assigned_date > '2015-01-01 00:00:00'::timestamp without time zone THEN lti.id_indicatore
                    ELSE lti.code
                END AS id_attivita,
            v.tipo_controllo_bpi,
            v.tipo_controllo_haccp,
                CASE
                    WHEN v.azione_non_conforme IS NULL THEN 'N.D.'::character varying::text
                    WHEN btrim(v.azione_non_conforme) = ''::text THEN 'N.D.'::character varying::text
                    ELSE v.azione_non_conforme
                END AS oggetto_del_controllo,
                CASE
                    WHEN t.punteggio < 3 THEN 'Favorevole'::text
                    WHEN t.punteggio >= 3 THEN 'Non Favorevole'::text
                    ELSE 'N.D.'::text
                END AS punteggio,
            to_date(t.assigned_date::text, 'yyyy/mm/dd'::text) AS data_inizio_controllo,
                CASE
                    WHEN t.assigned_date IS NULL THEN 'N.D.'::text
                    ELSE date_part('year'::text, t.assigned_date)::text
                END AS anno_controllo,
            to_date(t.closed::text, 'yyyy/mm/dd'::text) AS data_chiusura_controllo,
                CASE
                    WHEN t.isaggiornata_categoria = true AND t.provvedimenti_prescrittivi = 5 THEN 'CONTROLLO CATEGORIZZATO'::text
                    WHEN t.isaggiornata_categoria = false AND t.provvedimenti_prescrittivi = 5 THEN 'CONTROLLO NON CATEGORIZZATO'::text
                    WHEN t.provvedimenti_prescrittivi <> 5 THEN 'NON PREVISTA CATEGORIAAZZIONE'::text
                    ELSE NULL::text
                END AS aggiornata_cat_controllo,
            t.categoria_rischio,
            t.prossimo_controllo,
                CASE
                    WHEN audit.c IS NOT NULL THEN audit.c
                    ELSE 'Non Presente'::text
                END AS tipo_checklist,
            'N.D.'::text AS linea_attivita_sottoposta_a_controllo,
                CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura::text
                        ELSE ''::text
                    END || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN (
                    CASE
                        WHEN pp.descrizione_struttura IS NOT NULL THEN pp.descrizione_struttura::text
                        ELSE ''::text
                    END || '-'::text) || n.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN (
                    CASE
                        WHEN ppp.descrizione_struttura IS NOT NULL THEN ppp.descrizione_struttura
                        ELSE ''::character varying
                    END::text || '-'::text) || nn.descrizione_struttura::text
                    ELSE NULL::text
                END AS unita_operativa,
                CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.descrizione_area_struttura
                    ELSE NULL::character varying(50)
                END AS descrizione_area_struttura,
                CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.id
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.id
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.id
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.id
                    ELSE '-1'::integer
                END AS id_struttura_uo,
            t.supervisionato_in_data,
                CASE
                    WHEN c.namelast IS NOT NULL AND c.namefirst IS NOT NULL THEN (c.namelast::text || c.namefirst::text)::character varying
                    WHEN c.namelast IS NOT NULL AND c.namefirst IS NULL THEN c.namelast
                    ELSE c.namefirst
                END AS supervisionato_da,
            t.supervisione_note,
                CASE
                    WHEN t.supervisione_flag_congruo THEN 'SI'::text
                    WHEN t.supervisione_flag_congruo = false THEN 'NO'::text
                    ELSE 'N.D.'::text
                END AS congruo_supervisione,
            t.problem,
                CASE
                    WHEN nn.codice_interno_fk > 0 THEN nn.codice_interno_fk
                    ELSE n.codice_interno_fk
                END AS codice_interno_univoco_uo,
            COALESCE(lpiani.codice, lti.codice) AS codice_interno_piano_attivita
           FROM ticket t
             LEFT JOIN contact_ c ON c.user_id = t.supervisionato_da
             JOIN lookup_site_id asl ON t.site_id = asl.code
             LEFT JOIN report_visa_tipi_controlli_ufficiali_no_funzione v ON t.ticketid = v.ticketid
             LEFT JOIN tipocontrolloufficialeimprese tcu ON t.ticketid = tcu.idcontrollo AND (tcu.tipoispezione > 0 OR tcu.audit_tipo > 0 OR tcu.tipo_audit > 0) AND tcu.enabled = true
             LEFT JOIN dpat_strutture_asl n ON tcu.id_unita_operativa = n.id
             LEFT JOIN dpat_strutture_asl pp ON n.id_padre = pp.id
             LEFT JOIN unita_operative_controllo uoc ON uoc.id_controllo = t.ticketid
             LEFT JOIN dpat_strutture_asl nn ON nn.id = uoc.id_unita_operativa
             LEFT JOIN dpat_strutture_asl ppp ON nn.id_padre = ppp.id
             LEFT JOIN lookup_tipo_ispezione lti ON tcu.tipoispezione = lti.code
             LEFT JOIN lookup_audit_tipo lat ON tcu.audit_tipo = lat.code
             LEFT JOIN lookup_tipo_audit lta ON tcu.tipo_audit = lta.code
             LEFT JOIN lookup_piano_monitoraggio lpiani ON tcu.pianomonitoraggio = lpiani.code AND t.assigned_date <= COALESCE(lpiani.data_scadenza, 'now'::text::date::timestamp without time zone)
             LEFT JOIN dpat_indicatore ind ON ind.id_ = lpiani.id_indicatore
             LEFT JOIN dpat_attivita dpatatt ON dpatatt.id_ = ind.id_attivita_
             LEFT JOIN dpat_piano dpatp ON dpatp.id_ = dpatatt.id_piano_
             LEFT JOIN dpat_indicatore indti ON indti.id_ = lti.id_indicatore
             LEFT JOIN dpat_attivita dpatattti ON dpatattti.id_ = indti.id_attivita_
             LEFT JOIN dpat_piano dpatpti ON dpatpti.id_ = dpatattti.id_piano_
             LEFT JOIN lookup_piano_monitoraggio lpianipadre ON lpiani.id_padre = lpianipadre.code AND lpianipadre.id_padre <= 0
             JOIN lookup_tipo_controllo ltc ON t.provvedimenti_prescrittivi = ltc.code
             LEFT JOIN checklist_controlli audit ON audit.id_controllo::text = t.id_controllo_ufficiale::text
          WHERE t.tipologia = 3 AND t.trashed_date IS NULL AND t.id_apiario > 0) vv;

ALTER TABLE public.report_vista_controlli_ufficiali
  OWNER TO postgres;
GRANT ALL ON TABLE public.report_vista_controlli_ufficiali TO postgres;
GRANT SELECT ON TABLE public.report_vista_controlli_ufficiali TO report;