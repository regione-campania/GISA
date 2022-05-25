CHI: Rita Mele
COSA: Inserimento generico linea CU
QUANDO: 18/04/2019

-- Lanciare gli script 
 
-- Function: public_functions.dbi_get_controlli_ufficiali_eseguiti_nuova_anagraficanew(timestamp without time zone, timestamp without time zone)
-- DROP FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_nuova_anagraficanew(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_nuova_anagraficanew(
    IN data_1 timestamp without time zone,
    IN data_2 timestamp without time zone)
  RETURNS TABLE(id_controllo_ufficiale integer, riferimento_id integer, riferimento_id_nome text, riferimento_id_nome_col text, riferimento_id_nome_tab text, id_asl integer, asl character varying, tipo_controllo character varying, tipo_ispezione_o_audit text, tipo_piano_monitoraggio text, id_piano integer, id_attivita integer, tipo_controllo_bpi text, tipo_controllo_haccp text, oggetto_del_controllo text, punteggio text, data_inizio_controllo date, anno_controllo text, data_chiusura_controllo date, aggiornata_cat_controllo text, categoria_rischio integer, prossimo_controllo timestamp with time zone, tipo_checklist text, linea_attivita_sottoposta_a_controllo text, unita_operativa text, id_struttura_uo integer, supervisionato_in_data timestamp without time zone, supervisionato_da character varying, supervisione_note text, congruo_supervisione text, note text, tipo_piano_monitoraggio_old text, codice_interno_univoco_uo integer, codice_interno_piano text, area_appartenenza_uo text, id_linea_controllata integer) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR 
		id_controllo_ufficiale,
		riferimento_id,
		riferimento_id_nome,
		riferimento_id_nome_col,
		riferimento_id_nome_tab,
		id_asl,  
		asl , 
		tipo_controllo  , 
		tipo_ispezione_o_audit , 
		tipo_piano_monitoraggio ,
		id_piano, id_attivita,
		tipo_controllo_bpi , tipo_controllo_haccp , 
		oggetto_del_controllo , punteggio , 
		data_inizio_controllo , anno_controllo,  data_chiusura_controllo, aggiornata_cat_controllo, categoria_rischio, prossimo_controllo, 
		tipo_checklist, 
		linea_attivita_sottoposta_a_controllo,
		unita_operativa, id_struttura_uo, supervisionato_in_data, supervisionato_da, supervisione_note, congruo_supervisione,note,  
		tipo_piano_monitoraggio_old,
		codice_interno_univoco_uo,codice_interno_piano,
		area_appartenenza_uo,id_linea_controllata
		in
		
		select t.ticketid,
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
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio > 0 then  (CASE WHEN dp.tipo_attivita_piano ilike '%attivit%' THEN 'ATTIVITA ' ELSE 'PIANO ' END ) || dp.alias_attivita || ' ' || dp.descrizione_attivita
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio <= 0 AND tcu.tipoispezione <> 89 AND tcu.tipoispezione <> 2 
                    THEN (CASE WHEN da.tipo_attivita_piano ilike '%attivit%' THEN 'ATTIVITA ' ELSE 'PIANO ' END ) || da.alias_attivita || ' ' || da.descrizione_attivita
                    ELSE 'N.D'::text
                END AS tipo_ispezione_o_audit,
                CASE
                    WHEN tcu.tipoispezione > 0 AND lti.codice_interno !~~* '2a'::text THEN concat_ws(': ',da.alias_indicatore,da.descrizione_indicatore)
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 THEN concat_ws(': ',dp.alias_indicatore,dp.descrizione_indicatore)
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio,
                dp.id_fisico_indicatore AS id_piano,
                case when dp.id_fisico_indicatore > 0 then null
                else da.id_fisico_indicatore
                end as id_attivita,
            CASE
            WHEN tcu.tipo_audit = 2 AND t.provvedimenti_prescrittivi = 3 THEN 'BPI-'::text || lbpi.description::text
            ELSE 'N.D'::text
        END AS tipo_controllo_bpi,
        CASE
            WHEN tcu.tipo_audit = 3 AND t.provvedimenti_prescrittivi = 3 THEN 'HACCP-'::text || lhaccp.description::text
            ELSE 'N.D'::text
        END AS tipo_controllo_haccp,
               CASE
            WHEN oggcu.ispezione > 0 THEN (lim.description_old::text || ': '::text) || lisp.description::text
            ELSE 'N.D'::text
        END  AS oggetto_del_controllo,
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
                    WHEN t.provvedimenti_prescrittivi <> 5 THEN 'NON PREVISTA CATEGORIZZAZIONE'::text
                    ELSE NULL::text
                END AS aggiornata_cat_controllo,
            t.categoria_rischio,
            t.data_prossimo_controllo,
                CASE
                    WHEN audit.c IS NOT NULL and audit.c = 'T' THEN 'Temporanea'
                    WHEN audit.c IS NOT NULL and audit.c = 'D' THEN 'Definitiva'
                    WHEN (audit.c is null or audit.c = '') and audit.id_controllo is not null THEN 'Presenti più checklist'
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
                    WHEN nn.codice_interno_fk > 0 THEN nn.codice_interno_fk
                    ELSE n.codice_interno_fk
                END AS codice_interno_univoco_uo,
            COALESCE(lpiani.codice, lti.codice) AS codice_interno_piano_attivita,
             CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.descrizione_area_struttura
                    ELSE NULL::character varying(50)
                END AS descrizione_area_struttura,lacc.id_linea_attivita as id_linea_controllo
 from ticket t              
 LEFT JOIN contact_ c ON c.user_id = t.supervisionato_da
             LEFT JOIN checklist_controlli audit ON audit.id_controllo::text = t.id_controllo_ufficiale::text
             JOIN lookup_site_id asl ON t.site_id = asl.code
             JOIN lookup_tipo_controllo ltc ON t.provvedimenti_prescrittivi = ltc.code
             LEFT JOIN tipocontrolloufficialeimprese tcu ON t.ticketid = tcu.idcontrollo AND (tcu.tipoispezione > 0 OR tcu.audit_tipo > 0 OR tcu.tipo_audit > 0) AND tcu.enabled = true
	     LEFT JOIN tipocontrolloufficialeimprese oggcu ON t.ticketid = oggcu.idcontrollo AND oggcu.ispezione > 0
	     LEFT JOIN lookup_ispezione lisp ON oggcu.ispezione = lisp.code
             LEFT JOIN lookup_ispezione_macrocategorie lim ON lim.code = lisp.level AND lim.enabled
	     LEFT JOIN lookup_bpi lbpi ON tcu.bpi = lbpi.code
	     LEFT JOIN lookup_haccp lhaccp ON tcu.haccp = lhaccp.code
             --LEFT JOIN opu_linee_attivita_controlli_ufficiali lacc ON lacc.id_controllo_ufficiale = t.ticketid
             LEFT JOIN linee_attivita_controlli_ufficiali lacc ON lacc.id_controllo_ufficiale = t.ticketid
             LEFT JOIN ml8_linee_attivita_nuove_materializzata lattcu ON lacc.codice_linea = lattcu.codice
	     --LEFT JOIN opu_relazione_stabilimento_linee_produttive orslp ON orslp.id = lacc.id_linea_attivita
             --LEFT JOIN ml8_linee_attivita_nuove lattcu ON orslp.id_linea_produttiva = lattcu.id_nuova_linea_attivita
             --LEFT JOIN ml8_linee_attivita_nuove latt ON latt.id_nuova_linea_attivita = orslp.id_linea_produttiva
             LEFT JOIN lookup_tipo_ispezione lti ON tcu.tipoispezione = lti.code
             LEFT JOIN lookup_audit_tipo lat ON tcu.audit_tipo = lat.code 
             LEFT JOIN lookup_tipo_audit lta ON tcu.tipo_audit = lta.code  
             LEFT JOIN lookup_piano_monitoraggio lpiani ON tcu.pianomonitoraggio = lpiani.code AND t.assigned_date <= COALESCE(lpiani.data_scadenza, 'now'::text::date::timestamp without time zone)
             LEFT JOIN lookup_piano_monitoraggio lpianipadre ON lpiani.id_padre = lpianipadre.code AND lpianipadre.id_padre <= 0
	    -- JOIN NEW 
	    LEFT JOIN view_motivi_linearizzati_dpat da on da.id_fisico_indicatore = tcu.tipoispezione 
	    LEFT JOIN view_motivi_linearizzati_dpat dp on dp.id_fisico_indicatore = tcu.pianomonitoraggio 
	    --fine join new
             LEFT JOIN dpat_strutture_asl n ON tcu.id_unita_operativa = n.id
             LEFT JOIN dpat_strutture_asl pp ON n.id_padre = pp.id
             LEFT JOIN unita_operative_controllo uoc ON uoc.id_controllo = t.ticketid
             LEFT JOIN dpat_strutture_asl nn ON nn.id = uoc.id_unita_operativa
             LEFT JOIN dpat_strutture_asl ppp ON nn.id_padre = ppp.id
	     WHERE t.tipologia = 3 
	     and lacc.trashed_date is null 
	     and t.assigned_date between data_1 and data_2  
	     AND t.trashed_date IS NULL 
	     AND t.id_stabilimento > 0 
	
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_nuova_anagraficanew(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

-- Function: public_functions.opu_aggiorna_cu_e_categoriarischio(integer, integer, integer, integer, integer)

-- DROP FUNCTION public_functions.opu_aggiorna_cu_e_categoriarischio(integer, integer, integer, integer, integer);

CREATE OR REPLACE FUNCTION public_functions.opu_aggiorna_cu_e_categoriarischio(
    orgid integer,
    idlineanuova integer,
    idstab integer,
    categoriarischio integer,
    idrelstablp integer)
  RETURNS text AS
$BODY$
DECLARE
esito text;
numero_aggiornamenti int;
BEGIN
--associo il nuovo controllo alla nuova linea di attivita
with t as(
 insert into linee_attivita_controlli_ufficiali(id_controllo_ufficiale,id_linea_attivita,id_vecchio_tipo_operatore)
(select ticketid,idLineaNuova,o.tipologia
from ticket t
join organization o on t.org_id=o.org_id
where t.tipologia=3 and t.trashed_date is null and t.org_id=orgId and t.provvedimenti_prescrittivi!=5)
 RETURNING id_linea_attivita
) 
select count(id_linea_attivita) into numero_aggiornamenti from t  where id_linea_attivita  = idLineaNuova;

update ticket SET id_stabilimento = idstab where org_id = orgId;
 --aggiorno la categoria di rischio sullo stabilimento e sulle linee di attività
 update opu_stabilimento set categoria_rischio = categoriaRischio where id = idstab;
 update opu_relazione_stabilimento_linee_produttive  set categoria_rischio = categoriaRischio where id = idrelstablp;

if numero_aggiornamenti > 0 
then
 esito := 'OK';
 else 
 esito := 'KO';
end if;

 return esito;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public_functions.opu_aggiorna_cu_e_categoriarischio(integer, integer, integer, integer, integer)
  OWNER TO postgres;

-- Function: public_functions.suap_validazione_scia_nuovo_stabilimento(integer, integer, integer, text)

-- DROP FUNCTION public_functions.suap_validazione_scia_nuovo_stabilimento(integer, integer, integer, text);

CREATE OR REPLACE FUNCTION public_functions.suap_validazione_scia_nuovo_stabilimento(
    id_rel_stab_lp integer,
    idutenteasl integer,
    statovalidazione integer,
    notevalidazione text)
  RETURNS esitovalidazione AS
$BODY$
DECLARE
operatore impresa;
stab stabilimento ;
esito esitovalidazione ;
idLog int;
idStabilimento int ;
numReg text;
id_richiesta_suap int ;
validazioneCompleta boolean ;
altId int;
 pivaSearch text;  cfSearch text; civicoSearch text; viaSearch  text ;comuneSearch text;
loginsert BOOLEAN ;
importAllegati boolean;
id_tipo_attivita int ;

id_linea_master_list int ;
BEGIN

idLog:=(select nextval('suap_log_validazioni_id_seq'));
insert into suap_log_validazioni (id,id_richiesta_operatore,nome_funzione,data_validazione) values(idLog,id_rel_stab_lp,'public_functions.suap_validazione_scia_nuovo_stabilimento',current_timestamp);


idStabilimento:=(select distinct id_Stabilimento from suap_ric_scia_relazione_stabilimento_linee_produttive where id = id_rel_stab_lp);
id_richiesta_suap:=(select id_operatore from suap_ric_scia_stabilimento where id = idStabilimento);
loginsert:=(select * from public_functions.suap_log_operazioni(id_richiesta_suap ,'public_functions.suap_validazione_scia_nuovo_stabilimento' ,'Assegnato Numero Registrazione Su Stabilimento: '||numReg ,'suap_ric_scia_stabilimento.id' ,idStabilimento||'',idLog ));

numReg:=(select * from public_functions.suap_genera_numero_registrazione(id_richiesta_suap, 'w'));
raise info 'numReg : -> %',numReg;
/*NUMERO REGISTRAZIONE SU STABILIMENTO*/
if statoValidazione=1 
then
select partita_iva, codice_fiscale_impresa, civico_stabilimento_calcolato , via_stabilimento_calcolata ,comune_richiesta,stab_id_attivita,id_linea_attivita_stab
into pivaSearch,cfSearch,civicoSearch,viaSearch,comuneSearch,id_tipo_attivita,id_linea_master_list
from suap_ric_scia_operatori_denormalizzati_view 
where     id_linea_attivita =id_rel_stab_lp ;

select id_opu_operatore,id_stabilimento,4 into esito
from opu_operatori_denormalizzati_view where 
case when (pivaSearch is not null and pivaSearch <>'') then partita_iva ilike pivaSearch else codice_fiscale_impresa ilike cfSearch end
and comune_richiesta ilike trim(comuneSearch)
and via_stabilimento_calcolata ilike trim(viaSearch)
and civico_stabilimento_calcolato ilike trim(civicoSearch)
and stab_id_attivita=id_tipo_attivita and 
case when id_tipo_attivita=2 then id_linea_attivita_stab = id_linea_master_list else 1=1 end
and id_stato !=4 ;

if esito.id_stabilimento_opu>0
then 
return esito;
end if;
end if ;


raise info 'statoValidazione dopo iff  %',statoValidazione;


numReg:=(select numReg ||lpad(dbi_opu_get_progressivo_linea_per_stabilimento(numReg)||'',3,'0') ) ;

raise info 'numReg linea  %',numReg;

if statoValidazione=1 
then
update suap_ric_scia_relazione_stabilimento_linee_produttive set stato = statoValidazione, 
validato_da =idutenteasl ,validato_in_data =current_timestamp ,validazione_note =noteValidazione 
,numero_registrazione = numReg   
where id = id_rel_stab_lp  ; 
else
update suap_ric_scia_relazione_stabilimento_linee_produttive set stato = statoValidazione, 
validato_da =idutenteasl ,validato_in_data =current_timestamp ,validazione_note =noteValidazione 
where id = id_rel_stab_lp  ; 
end if;


raise info 'id_rel_stab_lp  %',id_rel_stab_lp;

validazioneCompleta:=(select * from public_functions.suap_controlla_validazione_scia_nuovo_stabilimento_linee(id_richiesta_suap ,idLog ));

raise info 'validazioneCompleta : %',validazioneCompleta;
if validazioneCompleta=true
then

select * into operatore from public_functions.suap_insert_impresa(id_richiesta_suap,idLog,idutenteasl);
esito.id_impresa_opu:=operatore.id;
esito.id_esito:=operatore.id_esito;

select * into stab from  public_functions.suap_insert_stabilimento(id_richiesta_suap,operatore,idLog,idutenteasl);
esito.id_stabilimento_opu:=stab.id;

raise info 'id stabilimento %',stab.id;
update suap_ric_scia_operatore set validato = true where id=id_richiesta_suap;

altId:=(select alt_id from suap_ric_scia_stabilimento where id_operatore=id_richiesta_suap);

/*IMPORT CONTROLLI UFFICIALI*/
update ticket set id_stabilimento =stab.id where  alt_id =altId;
update audit set alt_id=id_stabilimento,id_stabilimento =stab.id where  id_stabilimento =stab.id;

select * from public_functions.insert_linee_attivita_controlli_ufficiali
((select rrslp.id_controllo_ufficiale,rslp.id
from linee_attivita_controlli_ufficiali rrslp
join opu_relazione_stabilimento_linee_produttive rslp on rrslp.id_linea_attivita = rslp.id_suap_rel_stab_lp
where rslp.id_stabilimento =stab.id and rrslp.trashed_date is null
));
 
--insert into opu_linee_attivita_controlli_ufficiali (id_controllo_ufficiale,id_linea_attivita)
--(select rrslp.id_controllo_ufficiale,rslp.id
--from linee_attivita_controlli_ufficiali rrslp
--join opu_relazione_stabilimento_linee_produttive rslp on rrslp.id_linea_attivita = rslp.id_suap_rel_stab_lp
--where rslp.id_stabilimento =stab.id
--);

return esito ;
else

raise info 'statoValidazione %',validazioneCompleta;
validazioneCompleta:=(select * from public_functions.suap_controlla_validazione_scia_nuovo_stabilimento_linee(id_richiesta_suap ,idLog ));

if validazioneCompleta=false
then
esito.id_esito:=7;
else
esito.id_esito:=1;
update suap_ric_scia_operatore set validato = true where id=id_richiesta_suap;

end if ;

esito.id_impresa_opu:=id_richiesta_suap;
esito.id_stabilimento_opu=idStabilimento;

update suap_ric_scia_relazione_stabilimento_linee_produttive set stato = statoValidazione, 
validato_da =idutenteasl ,validato_in_data =current_timestamp ,validazione_note =noteValidazione 
where id = id_rel_stab_lp and stato=0 ; 


return esito;
end if ;



 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.suap_validazione_scia_nuovo_stabilimento(integer, integer, integer, text)
  OWNER TO postgres;
  
  
-- Function: public_functions.dbi_get_controlli_ufficiali_eseguiti_vecchia_anagraficanew(timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_vecchia_anagraficanew(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_vecchia_anagraficanew(
    IN data_1 timestamp without time zone,
    IN data_2 timestamp without time zone)
  RETURNS TABLE(id_controllo_ufficiale integer, riferimento_id integer, riferimento_id_nome text, riferimento_id_nome_col text, riferimento_id_nome_tab text, id_asl integer, asl character varying, tipo_controllo character varying, tipo_ispezione_o_audit text, tipo_piano_monitoraggio text, id_piano integer, id_attivita integer, tipo_controllo_bpi text, tipo_controllo_haccp text, oggetto_del_controllo text, punteggio text, data_inizio_controllo date, anno_controllo text, data_chiusura_controllo date, aggiornata_cat_controllo text, categoria_rischio integer, prossimo_controllo timestamp with time zone, tipo_checklist text, linea_attivita_sottoposta_a_controllo text, unita_operativa text, id_struttura_uo integer, supervisionato_in_data timestamp without time zone, supervisionato_da character varying, supervisione_note text, congruo_supervisione text, note text, tipo_piano_monitoraggio_old text, codice_interno_univoco_uo integer, codice_interno_piano text, area_appartenenza_uo text, id_linea_controllata integer) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR 
		id_controllo_ufficiale,
		riferimento_id,
		riferimento_id_nome,
		riferimento_id_nome_col,
		riferimento_id_nome_tab,
		id_asl,  
		asl , 
		tipo_controllo  , 
		tipo_ispezione_o_audit , 
		tipo_piano_monitoraggio ,
		id_piano, id_attivita,
		tipo_controllo_bpi , tipo_controllo_haccp , 
		oggetto_del_controllo , punteggio , 
		data_inizio_controllo , anno_controllo,  data_chiusura_controllo, aggiornata_cat_controllo, categoria_rischio, prossimo_controllo, 
		tipo_checklist, 
		linea_attivita_sottoposta_a_controllo,
		unita_operativa, id_struttura_uo, supervisionato_in_data, supervisionato_da, supervisione_note, congruo_supervisione,note,  
		tipo_piano_monitoraggio_old,
		codice_interno_univoco_uo,codice_interno_piano,
		area_appartenenza_uo,id_linea_controllata 
		in

select 
t.ticketid AS id_controllo_ufficiale,
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
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio > 0 then 
			(CASE WHEN dp.tipo_attivita_piano ilike '%attivit%' THEN 'ATTIVITA ' ELSE 'PIANO ' END ) || dp.alias_attivita || ' ' || dp.descrizione_attivita
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio <= 0 AND tcu.tipoispezione <> 89 AND tcu.tipoispezione <> 2 
                    THEN (CASE WHEN da.tipo_attivita_piano ilike '%attivit%' THEN 'ATTIVITA ' ELSE 'PIANO ' END ) || da.alias_attivita || ' ' || da.descrizione_attivita
                    ELSE 'N.D'::text
                END AS tipo_ispezione_o_audit,
                CASE
                    WHEN tcu.tipoispezione > 0 AND lti.codice_interno !~~* '2a'::text THEN concat_ws(': ',da.alias_indicatore,da.descrizione_indicatore)
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 THEN concat_ws(': ',dp.alias_indicatore,dp.descrizione_indicatore)
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio,
                dp.id_fisico_indicatore AS id_piano,
                case when dp.id_fisico_indicatore > 0 then null
                else da.id_fisico_indicatore
                end as id_attivita,
            CASE
            WHEN tcu.tipo_audit = 2 AND t.provvedimenti_prescrittivi = 3 THEN 'BPI-'::text || lbpi.description::text
            ELSE 'N.D'::text
        END AS tipo_controllo_bpi,
        CASE
            WHEN tcu.tipo_audit = 3 AND t.provvedimenti_prescrittivi = 3 THEN 'HACCP-'::text || lhaccp.description::text
            ELSE 'N.D'::text
        END AS tipo_controllo_haccp,
               CASE
            WHEN oggcu.ispezione > 0 THEN (lim.description_old::text || ': '::text) || lisp.description::text
            ELSE 'N.D'::text
        END  AS oggetto_del_controllo,
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
                    WHEN t.provvedimenti_prescrittivi <> 5 THEN 'NON PREVISTA CATEGORIZZAZIONE'::text
                    ELSE NULL::text
                END AS aggiornata_cat_controllo,
            t.categoria_rischio,
            t.data_prossimo_controllo,
               CASE
                    WHEN audit.c IS NOT NULL and audit.c = 'T' THEN 'Temporanea'
                    WHEN audit.c IS NOT NULL and audit.c = 'D' THEN 'Definitiva'
                    WHEN (audit.c IS NULL or audit.c = '') and audit.id_controllo is not null THEN 'Presenti più checklist'
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
                    WHEN nn.codice_interno_fk > 0 THEN nn.codice_interno_fk
                    ELSE n.codice_interno_fk
                END AS codice_interno_univoco_uo,
            COALESCE(lpiani.codice, lti.codice) AS codice_interno_piano_attivita,
             CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.descrizione_area_struttura
                    ELSE NULL::character varying(50)
                END AS descrizione_area_struttura,
		lacc.id_linea_attivita as id_linea_controllata
               
 FROM ticket t
             LEFT JOIN contact_ c ON c.user_id = t.supervisionato_da
             JOIN organization o ON o.org_id = t.org_id
             LEFT JOIN checklist_controlli audit ON audit.id_controllo::text = t.id_controllo_ufficiale::text
             JOIN lookup_site_id asl ON t.site_id = asl.code
             JOIN lookup_tipo_controllo ltc ON t.provvedimenti_prescrittivi = ltc.code
             LEFT JOIN tipocontrolloufficialeimprese tcu ON t.ticketid = tcu.idcontrollo AND (tcu.tipoispezione > 0 OR tcu.audit_tipo > 0 OR tcu.tipo_audit > 0) AND tcu.enabled = true
	   LEFT JOIN tipocontrolloufficialeimprese oggcu ON t.ticketid = oggcu.idcontrollo AND oggcu.ispezione > 0
     LEFT JOIN lookup_ispezione lisp ON oggcu.ispezione = lisp.code
     LEFT JOIN lookup_ispezione_macrocategorie lim ON lim.code = lisp.level  AND lim.enabled
	     LEFT JOIN lookup_bpi lbpi ON tcu.bpi = lbpi.code
	     LEFT JOIN lookup_haccp lhaccp ON tcu.haccp = lhaccp.code
	     --LEFT JOIN lookup_ispezione_macrocategorie lim ON lim.code = lisp.level
	     LEFT JOIN linee_attivita_controlli_ufficiali lacc ON lacc.id_controllo_ufficiale = t.ticketid
	     LEFT JOIN ml8_linee_attivita_nuove_materializzata lattcu ON lacc.codice_linea = lattcu.codice
             LEFT JOIN la_imprese_linee_attivita lail ON lail.id = lacc.id_linea_attivita
             LEFT JOIN la_rel_ateco_attivita lar ON lar.id = lail.id_rel_ateco_attivita
             LEFT JOIN la_linee_attivita ll ON ll.id = lar.id_linee_attivita
             LEFT JOIN lookup_tipo_ispezione lti ON tcu.tipoispezione = lti.code
             LEFT JOIN lookup_audit_tipo lat ON tcu.audit_tipo = lat.code
             LEFT JOIN lookup_tipo_audit lta ON tcu.tipo_audit = lta.code
             LEFT JOIN lookup_piano_monitoraggio lpiani ON tcu.pianomonitoraggio = lpiani.code AND t.assigned_date <= COALESCE(lpiani.data_scadenza, 'now'::text::date::timestamp without time zone)
             LEFT JOIN lookup_piano_monitoraggio lpianipadre ON lpiani.id_padre = lpianipadre.code AND lpianipadre.id_padre <= 0
              -- JOIN NEW
	    LEFT JOIN view_motivi_linearizzati_dpat da on da.id_fisico_indicatore = tcu.tipoispezione 
	    LEFT JOIN view_motivi_linearizzati_dpat dp on dp.id_fisico_indicatore = tcu.pianomonitoraggio 
	    --fine join new
             LEFT JOIN dpat_strutture_asl n ON tcu.id_unita_operativa = n.id
             LEFT JOIN dpat_strutture_asl pp ON n.id_padre = pp.id
             LEFT JOIN unita_operative_controllo uoc ON uoc.id_controllo = t.ticketid
             LEFT JOIN dpat_strutture_asl nn ON nn.id = uoc.id_unita_operativa
             LEFT JOIN dpat_strutture_asl ppp ON nn.id_padre = ppp.id
		WHERE t.tipologia = 3 and lacc.trashed_date is null
		and t.assigned_date  between data_1 and data_2 AND  t.trashed_date IS NULL 
		AND (o.trashed_date IS NULL OR o.trashed_date = '1970-01-01 00:00:00'::timestamp without time zone)
			
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_vecchia_anagraficanew(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;


  -- Function: public.dbi_get_controlli_ufficiali_su_linee_produttive_old_anag_852(integer)

-- DROP FUNCTION public.dbi_get_controlli_ufficiali_su_linee_produttive_old_anag_852(integer);

CREATE OR REPLACE FUNCTION public.dbi_get_controlli_ufficiali_su_linee_produttive_old_anag_852(IN idstabilimento integer)
  RETURNS TABLE(idcontrollo integer, id_stabilimento integer, id_rel_stab_lp_out integer, id_linea_master_list_out integer, enabled_out boolean, descrizione_out text, id_norma_out integer, id_stato_out integer) AS
$BODY$
DECLARE
BEGIN
		FOR
		idcontrollo,id_stabilimento,id_rel_stab_lp_out ,id_linea_master_list_out ,enabled_out ,descrizione_out, id_norma_out, id_stato_out
		in

		select distinct cu.ticketid,cu.org_id,r.id as id_rel_stab_lp ,rel.id as id_linea_master_list,true,ci.description || ' '||ci.short_description || case when laa.linea_attivita is not null then laa.linea_attivita else '' end, 1 as id_norma, 0 as id_stato
 
from la_imprese_linee_attivita  r 
 join la_rel_ateco_attivita rel on rel.id = r.id_rel_ateco_attivita
 join lookup_codistat ci on ci.code = rel.id_lookup_codistat
 join la_linee_attivita laa on laa.id = rel.id_linee_attivita 
 left join linee_attivita_controlli_ufficiali lacuorg on lacuorg.id_linea_attivita=r.id
 left join ticket cu on cu.org_id = r.org_id and cu.ticketid =lacuorg.id_controllo_ufficiale and cu.trashed_date is null
 where r.org_id =idstabilimento and r.trashed_date is null and lacuorg.trashed_date is null

 
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.dbi_get_controlli_ufficiali_su_linee_produttive_old_anag_852(integer)
  OWNER TO postgres;

  --- VISTE
  -- View: public.bdn_cu_candidati_benessere_animale_org_opu

-- DROP VIEW public.bdn_cu_candidati_benessere_animale_org_opu;

CREATE OR REPLACE VIEW public.bdn_cu_candidati_benessere_animale_org_opu AS 
 SELECT DISTINCT sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) AS punteggio,
    ist.id_alleg,
    ticket.id_bdn,
    lcm.codice_specie AS codice_specie_chk_bns,
    o.name AS ragione_sociale,
    o.org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R207'::text
            ELSE NULL::text
        END AS codice_asl,
    o.account_number AS codice_azienda,
    btrim(o.partita_iva::text) AS id_fiscale_allevamento,
        CASE
            WHEN qual.description::text ~~* '%forestale% '::text THEN '002'::text
            WHEN qual.description::text ~~* '%NAS%'::text THEN '001'::text
            WHEN qual.description::text ~~* '%veterinari%'::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
    false AS flag_cee,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END AS flag_extra_piano,
    o.cf_detentore AS id_fiscale_detentore,
    op.tipo_produzione,
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END AS codice_orientamento,
    'N.D'::text AS num_capannoni_suini,
    'N.D'::text AS num_capannoni_attivi_suini,
    'N.D'::text AS num_box_suini,
    'N.D'::text AS num_box_attivi_suini,
    'N.D'::text AS num_vitelli_max,
    'N.D'::text AS num_vitelli_presenti,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
        CASE
            WHEN sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) <= 0 OR sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) IS NULL THEN 'S'::text
            ELSE 'N'::text
        END AS esito_controllo,
    o.specie_allev,
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN o.specie_allev::text ~~* '%suini%'::text THEN '2'::text
                WHEN o.specie_allev::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN o.specie_allev::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END AS tipo_allegato,
        CASE
            WHEN o.specie_allev::text !~~* 'pesci'::text THEN '0'::text || o.specie_allevamento::text
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END AS flag_vitelli,
        CASE
            WHEN (o.specie_allev::text ~~* 'gallus'::text OR o.specie_allev::text ~~* 'oche'::text OR o.specie_allev::text ~~* 'fagiani'::text) AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso = '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
    tipi.pianomonitoraggio,
    '-1'::integer AS id_stabilimento
   FROM ticket
     JOIN organization o ON ticket.org_id = o.org_id
     JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 1
     LEFT JOIN comuni1 ON comuni1.nome::text ~~* oa.city::text
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg AND (lcm.code <> ALL (ARRAY[20, 22, 23]))
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = o.specie_allevamento
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     JOIN lookup_piano_monitoraggio lp ON lp.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = o.asl_old
     LEFT JOIN orientamenti_produttivi op ON op.specie_allevata = o.specie_allev::text AND op.descrizione_tipo_produzione = o.tipologia_strutt AND o.orientamento_prod = op.descrizione_codice_orientamento
     LEFT JOIN lookup_specie_allevata specie ON specie.description::text = o.specie_allev::text
  WHERE ticket.tipologia = 3 AND ticket.trashed_date IS NULL AND o.tipologia = 2 AND o.trashed_date IS NULL AND (lp.codice_interno = ANY (ARRAY[982, 983])) AND date_part('year'::text, ticket.assigned_date) > 2015::double precision
  GROUP BY ticket.id_bdn, o.name, o.org_id, ticket.ticketid, ticket.assigned_date, qual.description, tipi.pianomonitoraggio, lcm.codice_specie, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R207'::text
            ELSE NULL::text
        END), o.account_number, ('0'::text || o.specie_allevamento::text), o.partita_iva, (
        CASE
            WHEN ticket.nucleo_ispettivo = 1 OR ticket.nucleo_ispettivo_due = 1 OR ticket.nucleo_ispettivo_tre = 1 OR ticket.nucleo_ispettivo_quattro = 1 OR ticket.nucleo_ispettivo_cinque = 1 OR ticket.nucleo_ispettivo_sei = 1 OR ticket.nucleo_ispettivo_sette = 1 OR ticket.nucleo_ispettivo_otto = 1 OR ticket.nucleo_ispettivo_nove = 1 OR ticket.nucleo_ispettivo_dieci = 1 THEN '003'::text
            WHEN ticket.nucleo_ispettivo = 8 OR ticket.nucleo_ispettivo_due = 8 OR ticket.nucleo_ispettivo_tre = 8 OR ticket.nucleo_ispettivo_quattro = 8 OR ticket.nucleo_ispettivo_cinque = 8 OR ticket.nucleo_ispettivo_sei = 8 OR ticket.nucleo_ispettivo_sette = 8 OR ticket.nucleo_ispettivo_otto = 8 OR ticket.nucleo_ispettivo_nove = 8 OR ticket.nucleo_ispettivo_dieci = 8 THEN '001'::text
            WHEN ticket.nucleo_ispettivo = 13 OR ticket.nucleo_ispettivo_due = 13 OR ticket.nucleo_ispettivo_tre = 13 OR ticket.nucleo_ispettivo_quattro = 13 OR ticket.nucleo_ispettivo_cinque = 13 OR ticket.nucleo_ispettivo_sei = 13 OR ticket.nucleo_ispettivo_sette = 13 OR ticket.nucleo_ispettivo_otto = 13 OR ticket.nucleo_ispettivo_nove = 13 OR ticket.nucleo_ispettivo_dieci = 13 THEN '002'::text
            ELSE 'n.d'::text
        END), false::boolean, (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), (
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END), o.cf_detentore, op.tipo_produzione, (
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END), 'N.D'::text, ticket.data_estrazione, ticket.data_import, ticket.esito_import, ticket.descrizione_errore, (
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN o.specie_allev::text ~~* '%suini%'::text THEN '2'::text
                WHEN o.specie_allev::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN o.specie_allev::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END), ist.id_alleg, (
        CASE
            WHEN o.specie_allev::text ~~* 'gallus'::text AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END), (
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END)
UNION
 SELECT DISTINCT sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) AS punteggio,
    ist.id_alleg,
    ticket.id_bdn,
    lcm.codice_specie AS codice_specie_chk_bns,
    o.ragione_sociale,
    '-1'::integer AS org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R207'::text
            ELSE NULL::text
        END AS codice_asl,
    COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente) AS codice_azienda,
    btrim(COALESCE(o.partita_iva::text, o.codice_fiscale_impresa::text)) AS id_fiscale_allevamento,
        CASE
            WHEN qual.description::text ~~* '%forestale% '::text THEN '002'::text
            WHEN qual.description::text ~~* '%NAS%'::text THEN '001'::text
            WHEN qual.description::text ~~* '%veterinari%'::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
    false AS flag_cee,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END AS flag_extra_piano,
    ''::text AS id_fiscale_detentore,
    l.decodifica_tipo_produzione_bdn AS tipo_produzione,
    l.decodifica_codice_orientamento_bdn AS codice_orientamento,
    'N.D'::text AS num_capannoni_suini,
    'N.D'::text AS num_capannoni_attivi_suini,
    'N.D'::text AS num_box_suini,
    'N.D'::text AS num_box_attivi_suini,
    'N.D'::text AS num_vitelli_max,
    'N.D'::text AS num_vitelli_presenti,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
        CASE
            WHEN sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) <= 0 OR sum(risp.punteggioa + risp.punteggiob + risp.punteggioc) IS NULL THEN 'S'::text
            ELSE 'N'::text
        END AS esito_controllo,
    lsa.description AS specie_allev,
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN lsa.description::text ~~* '%suini%'::text THEN '2'::text
                WHEN lsa.description::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN lsa.description::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END AS tipo_allegato,
        CASE
            WHEN lsa.description::text !~~* 'pesci'::text THEN '0'::text || l.decodifica_specie_bdn
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END AS flag_vitelli,
        CASE
            WHEN (lsa.description::text ~~* 'gallus'::text OR lsa.description::text ~~* 'oche'::text OR lsa.description::text ~~* 'fagiani'::text) AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso = '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
    tipi.pianomonitoraggio,
    s.id AS id_stabilimento
   FROM ticket
     JOIN opu_stabilimento s ON ticket.id_stabilimento = s.id
     LEFT JOIN opu_operatore o ON o.id = s.id_operatore
     LEFT JOIN opu_indirizzo ind ON ind.id = s.id_indirizzo
     LEFT JOIN comuni1 c ON c.id = ind.comune
     JOIN linee_attivita_controlli_ufficiali olacu ON olacu.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN opu_relazione_stabilimento_linee_produttive rel ON rel.id = olacu.id_linea_attivita
     LEFT JOIN ml8_linee_attivita_nuove_materializzata l ON l.id_nuova_linea_attivita = rel.id_linea_produttiva --andrebbe utilizzato il codice
     LEFT JOIN orientamenti_produttivi ori ON l.decodifica_tipo_produzione_bdn = ori.tipo_produzione
     LEFT JOIN lookup_specie_allevata lsa ON lsa.short_description::text = l.decodifica_specie_bdn
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg AND (lcm.code <> ALL (ARRAY[20, 22, 23]))
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = l.decodifica_specie_bdn::integer
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     JOIN lookup_piano_monitoraggio lp ON lp.code = tipi.pianomonitoraggio
  WHERE ticket.tipologia = 3 and olacu.trashed_date is null AND (lp.codice_interno = ANY (ARRAY[982, 983])) AND ticket.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) > 2015::double precision AND s.trashed_date IS NULL AND o.trashed_date IS NULL
  GROUP BY ticket.id_bdn, o.ragione_sociale, s.id, ticket.ticketid, ticket.assigned_date, qual.description, tipi.pianomonitoraggio, lcm.codice_specie, o.codice_fiscale_impresa, l.decodifica_codice_orientamento_bdn, lsa.description, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R207'::text
            ELSE NULL::text
        END), (COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente)), ('0'::text || l.decodifica_specie_bdn), o.partita_iva, (
        CASE
            WHEN ticket.nucleo_ispettivo = 1 OR ticket.nucleo_ispettivo_due = 1 OR ticket.nucleo_ispettivo_tre = 1 OR ticket.nucleo_ispettivo_quattro = 1 OR ticket.nucleo_ispettivo_cinque = 1 OR ticket.nucleo_ispettivo_sei = 1 OR ticket.nucleo_ispettivo_sette = 1 OR ticket.nucleo_ispettivo_otto = 1 OR ticket.nucleo_ispettivo_nove = 1 OR ticket.nucleo_ispettivo_dieci = 1 THEN '003'::text
            WHEN ticket.nucleo_ispettivo = 8 OR ticket.nucleo_ispettivo_due = 8 OR ticket.nucleo_ispettivo_tre = 8 OR ticket.nucleo_ispettivo_quattro = 8 OR ticket.nucleo_ispettivo_cinque = 8 OR ticket.nucleo_ispettivo_sei = 8 OR ticket.nucleo_ispettivo_sette = 8 OR ticket.nucleo_ispettivo_otto = 8 OR ticket.nucleo_ispettivo_nove = 8 OR ticket.nucleo_ispettivo_dieci = 8 THEN '001'::text
            WHEN ticket.nucleo_ispettivo = 13 OR ticket.nucleo_ispettivo_due = 13 OR ticket.nucleo_ispettivo_tre = 13 OR ticket.nucleo_ispettivo_quattro = 13 OR ticket.nucleo_ispettivo_cinque = 13 OR ticket.nucleo_ispettivo_sei = 13 OR ticket.nucleo_ispettivo_sette = 13 OR ticket.nucleo_ispettivo_otto = 13 OR ticket.nucleo_ispettivo_nove = 13 OR ticket.nucleo_ispettivo_dieci = 13 THEN '002'::text
            ELSE 'n.d'::text
        END), false::boolean, (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), (
        CASE
            WHEN lp.codice_interno = 983 THEN 'S - SI'::text
            ELSE 'N - NO'::text
        END), l.decodifica_tipo_produzione_bdn, (
        CASE
            WHEN l.decodifica_codice_orientamento_bdn IS NOT NULL THEN l.decodifica_codice_orientamento_bdn
            ELSE ''::text
        END), 'N.D'::text, ticket.data_estrazione, ticket.data_import, ticket.esito_import, ticket.descrizione_errore, (
        CASE
            WHEN ticket.data_estrazione IS NULL OR ticket.data_import IS NULL OR ticket.data_import IS NOT NULL AND ticket.esito_import = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione OR ticket.data_import < ticket.data_estrazione THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN lsa.description::text ~~* '%suini%'::text THEN '2'::text
                WHEN lsa.description::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN lsa.description::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lp.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END), ist.id_alleg, (
        CASE
            WHEN lsa.description::text ~~* 'gallus'::text AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END), (
        CASE
            WHEN lp.flag_vitelli = true THEN 'S'::text
            ELSE 'N'::text
        END);

ALTER TABLE public.bdn_cu_candidati_benessere_animale_org_opu
  OWNER TO postgres;
  

  -- View: public.bdn_cu_chiusi_sicurezza_alimentare_b11_org_opu

-- DROP VIEW public.bdn_cu_chiusi_sicurezza_alimentare_b11_org_opu;

CREATE OR REPLACE VIEW public.bdn_cu_chiusi_sicurezza_alimentare_b11_org_opu AS 
 SELECT DISTINCT ticket.id_bdn,
    ticket.id_bdn_b11,
    o.name AS ragione_sociale,
    o.org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R207'::text
            ELSE NULL::text
        END AS codice_asl,
    o.account_number AS codice_azienda,
    o.partita_iva AS id_fiscale_allevamento,
        CASE
            WHEN nucleo.nucleo_ispettivo::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%forestale% '::text THEN '002'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%NAS% '::text THEN '001'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%veterinari% '::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
    o.cf_detentore AS id_fiscale_detentore,
    op.tipo_produzione,
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END AS codice_orientamento,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
    ticket.data_estrazione_b11,
    ticket.data_import_b11,
    ticket.esito_import_b11,
    ticket.descrizione_errore_b11,
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
    'S'::text AS esito_controllo,
    o.specie_allev,
    '0'::text || o.specie_allevamento::text AS specie_allevamento,
        CASE
            WHEN (o.specie_allev::text ~~* 'gallus'::text OR o.specie_allev::text ~~* 'oche'::text OR o.specie_allev::text ~~* 'fagiani'::text) AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso ~~* '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END AS flag_copia_checklist,
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END AS flag_esito_sa,
    lcm.code AS id_alleg,
    '-1'::integer AS id_stabilimento
   FROM ticket
     JOIN organization o ON ticket.org_id = o.org_id
     LEFT JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 1
     LEFT JOIN comuni1 ON comuni1.nome::text ~~* oa.city::text
     LEFT JOIN nucleo_ispettivo_mv nucleo ON nucleo.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = o.specie_allevamento
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     LEFT JOIN lookup_piano_monitoraggio lpiani ON lpiani.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = o.asl_old
     LEFT JOIN orientamenti_produttivi op ON op.specie_allevata = o.specie_allev::text AND op.descrizione_tipo_produzione = o.tipologia_strutt AND o.orientamento_prod = op.descrizione_codice_orientamento
     LEFT JOIN lookup_specie_allevata specie ON specie.description::text = o.specie_allev::text
  WHERE ticket.tipologia = 3 AND (ticket.closed IS NOT NULL OR ticket.closed_nolista) AND o.tipologia = 2 AND lpiani.codice_interno = 1483 AND (ticket.esito_import_b11 !~~* 'OK'::text OR ticket.esito_import_b11 IS NULL) AND ticket.trashed_date IS NULL AND o.trashed_date IS NULL AND ist.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) >= 2015::double precision AND ((( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 OR lcm.code IS NULL OR lcm.code = 20)
  GROUP BY ticket.id_bdn, ticket.id_bdn_b11, o.name, o.org_id, ticket.ticketid, ticket.assigned_date, nucleo.nucleo_ispettivo, nucleo.nucleo_ispettivo_due, nucleo.nucleo_ispettivo_tre, nucleo.nucleo_ispettivo_quattro, nucleo.nucleo_ispettivo_cinque, nucleo.nucleo_ispettivo_sei, nucleo.nucleo_ispettivo_sette, nucleo.nucleo_ispettivo_otto, nucleo.nucleo_ispettivo_nove, nucleo.nucleo_ispettivo_dieci, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R207'::text
            ELSE NULL::text
        END), o.account_number, ('0'::text || o.specie_allevamento::text), o.partita_iva, (
        CASE
            WHEN nucleo.nucleo_ispettivo::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%forestale% '::text THEN '002'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%NAS% '::text THEN '001'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%veterinari% '::text THEN '003'::text
            ELSE 'n.d.'::text
        END), (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), o.cf_detentore, op.tipo_produzione, (
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END), ticket.data_estrazione_b11, ticket.data_import_b11, ticket.esito_import_b11, ticket.descrizione_errore_b11, (
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN o.specie_allev::text ~~* '%suini%'::text THEN '2'::text
            WHEN o.specie_allev::text ~~* '%broiler%'::text THEN '5'::text
            WHEN o.specie_allev::text ~~* '%gallus%'::text THEN '1'::text
            ELSE '4'::text
        END), 'N'::text, (
        CASE
            WHEN o.specie_allev::text ~~* 'gallus'::text AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END), (
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END), lcm.code
UNION
 SELECT DISTINCT ticket.id_bdn,
    ticket.id_bdn_b11,
    o.ragione_sociale,
    '-1'::integer AS org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R207'::text
            ELSE NULL::text
        END AS codice_asl,
    COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente) AS codice_azienda,
    btrim(COALESCE(o.partita_iva::text, o.codice_fiscale_impresa::text)) AS id_fiscale_allevamento,
        CASE
            WHEN qual.description::text ~~* '%forestale% '::text THEN '002'::text
            WHEN qual.description::text ~~* '%NAS%'::text THEN '001'::text
            WHEN qual.description::text ~~* '%veterinari%'::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
    ''::text AS id_fiscale_detentore,
    l.decodifica_tipo_produzione_bdn AS tipo_produzione,
    l.decodifica_codice_orientamento_bdn AS codice_orientamento,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
    ticket.data_estrazione_b11,
    ticket.data_import_b11,
    ticket.esito_import_b11,
    ticket.descrizione_errore_b11,
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
    'S'::text AS esito_controllo,
    lsa.description AS specie_allev,
        CASE
            WHEN lsa.description::text !~~* 'pesci'::text THEN '0'::text || l.decodifica_specie_bdn
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN (lsa.description::text ~~* 'gallus'::text OR lsa.description::text ~~* 'oche'::text OR lsa.description::text ~~* 'fagiani'::text) AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso ~~* '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END AS flag_copia_checklist,
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END AS flag_esito_sa,
    lcm.code AS id_alleg,
    s.id AS id_stabilimento
   FROM ticket
     JOIN opu_stabilimento s ON ticket.id_stabilimento = s.id
     LEFT JOIN opu_operatore o ON o.id = s.id_operatore
     LEFT JOIN opu_indirizzo ind ON ind.id = s.id_indirizzo
     LEFT JOIN comuni1 c ON c.id = ind.comune
     JOIN linee_attivita_controlli_ufficiali olacu ON olacu.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN opu_relazione_stabilimento_linee_produttive rel ON rel.id = olacu.id_linea_attivita
     LEFT JOIN ml8_linee_attivita_nuove_materializzata l ON l.id_nuova_linea_attivita = rel.id_linea_produttiva --andrebbe gestito il codice
     LEFT JOIN nucleo_ispettivo_mv nucleo ON nucleo.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = l.decodifica_specie_bdn::integer
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     LEFT JOIN lookup_piano_monitoraggio lpiani ON lpiani.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = s.id_asl
     LEFT JOIN lookup_specie_allevata lsa ON lsa.short_description::text = l.decodifica_specie_bdn
  WHERE ticket.tipologia = 3 AND olacu.trashed_date is null AND (ticket.closed IS NOT NULL OR ticket.closed_nolista) AND lpiani.codice_interno = 1483 AND (ticket.esito_import_b11 !~~* 'OK'::text OR ticket.esito_import_b11 IS NULL) AND ticket.trashed_date IS NULL AND o.trashed_date IS NULL AND s.trashed_date IS NULL AND ist.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) >= 2015::double precision AND ((( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 OR lcm.code IS NULL OR lcm.code = 20)
  GROUP BY ticket.id_bdn, ticket.id_bdn_b11, o.ragione_sociale, s.id, ticket.ticketid, ticket.assigned_date, nucleo.nucleo_ispettivo, nucleo.nucleo_ispettivo_due, nucleo.nucleo_ispettivo_tre, nucleo.nucleo_ispettivo_quattro, nucleo.nucleo_ispettivo_cinque, nucleo.nucleo_ispettivo_sei, nucleo.nucleo_ispettivo_sette, nucleo.nucleo_ispettivo_otto, nucleo.nucleo_ispettivo_nove, nucleo.nucleo_ispettivo_dieci, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R207'::text
            ELSE NULL::text
        END), (COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente)), ('0'::text || l.decodifica_specie_bdn), o.partita_iva, o.codice_fiscale_impresa, qual.description, l.decodifica_codice_orientamento_bdn, lsa.description, (
        CASE
            WHEN nucleo.nucleo_ispettivo::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%forestale% '::text THEN '002'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%NAS% '::text THEN '001'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%veterinari% '::text THEN '003'::text
            ELSE 'n.d.'::text
        END), (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), l.decodifica_tipo_produzione_bdn, (
        CASE
            WHEN l.decodifica_codice_orientamento_bdn IS NOT NULL THEN l.decodifica_codice_orientamento_bdn
            ELSE ''::text
        END), ticket.data_estrazione_b11, ticket.data_import_b11, ticket.esito_import_b11, ticket.descrizione_errore_b11, (
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN lsa.description::text ~~* '%suini%'::text THEN '2'::text
                WHEN lsa.description::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN lsa.description::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lpiani.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END), 'N'::text, (
        CASE
            WHEN lsa.description::text ~~* 'gallus'::text AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END), (
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END), lcm.code;

ALTER TABLE public.bdn_cu_chiusi_sicurezza_alimentare_b11_org_opu
  OWNER TO postgres;


CREATE OR REPLACE VIEW public.linee_attivita_controlli_ufficiali_view AS 
 SELECT l.id_controllo_ufficiale,
    l.id_linea_attivita,
    o.org_id,
    o.tipologia AS tipo_operatore,
    l.trashed_date
   FROM linee_attivita_controlli_ufficiali l
     LEFT JOIN ticket t ON t.ticketid = l.id_controllo_ufficiale AND t.tipologia = 3
     LEFT JOIN organization o ON o.org_id = coalesce(t.org_id, t.org_id_old)
  WHERE t.trashed_date IS NULL 
  --AND o.trashed_date IS NULL 
  AND l.trashed_date IS NULL;

ALTER TABLE public.linee_attivita_controlli_ufficiali_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.linee_attivita_controlli_ufficiali_view TO postgres;

-- View: public.bdn_candidati_sicurezza_alimentare_b11_org_opu

-- DROP VIEW public.bdn_candidati_sicurezza_alimentare_b11_org_opu;

CREATE OR REPLACE VIEW public.bdn_candidati_sicurezza_alimentare_b11_org_opu AS 
 SELECT DISTINCT ticket.id_bdn,
    ticket.id_bdn_b11,
    o.name AS ragione_sociale,
    o.org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R207'::text
            ELSE NULL::text
        END AS codice_asl,
    asl1.description AS asl,
    o.account_number AS codice_azienda,
    o.partita_iva AS id_fiscale_allevamento,
        CASE
            WHEN nucleo.nucleo_ispettivo::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%forestale% '::text THEN '002'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%NAS% '::text THEN '001'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%veterinari% '::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
    o.cf_detentore AS id_fiscale_detentore,
    op.tipo_produzione,
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END AS codice_orientamento,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
    ticket.data_estrazione_b11,
    ticket.data_import_b11,
    ticket.esito_import_b11,
    ticket.descrizione_errore_b11,
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
    'S'::text AS esito_controllo,
    o.specie_allev,
    '0'::text || o.specie_allevamento::text AS specie_allevamento,
        CASE
            WHEN (o.specie_allev::text ~~* 'gallus'::text OR o.specie_allev::text ~~* 'oche'::text OR o.specie_allev::text ~~* 'fagiani'::text) AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso ~~* '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END AS flag_copia_checklist,
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END AS flag_esito_sa,
    lcm.code AS id_alleg,
    '-1'::integer AS id_stabilimento,
    lpiani.description AS pianomonitoraggio
   FROM ticket
     JOIN organization o ON ticket.org_id = o.org_id
     LEFT JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 1
     LEFT JOIN comuni1 ON comuni1.nome::text ~~* oa.city::text
     LEFT JOIN nucleo_ispettivo_mv nucleo ON nucleo.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid AND ist.trashed_date IS NULL
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = o.specie_allevamento
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     LEFT JOIN lookup_piano_monitoraggio lpiani ON lpiani.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = o.asl_old
     LEFT JOIN lookup_site_id asl1 ON asl1.code = ticket.site_id
     LEFT JOIN orientamenti_produttivi op ON op.specie_allevata = o.specie_allev::text AND op.descrizione_tipo_produzione = o.tipologia_strutt AND o.orientamento_prod = op.descrizione_codice_orientamento
     LEFT JOIN lookup_specie_allevata specie ON specie.description::text = o.specie_allev::text
  WHERE ticket.tipologia = 3 AND o.tipologia = 2 AND lpiani.codice_interno = 1483 AND ticket.trashed_date IS NULL AND o.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) >= 2015::double precision AND ((( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 OR lcm.code IS NULL OR (lcm.code = ANY (ARRAY[20, 22, 23])))
  GROUP BY ticket.id_bdn, ticket.id_bdn_b11, o.name, o.org_id, ticket.ticketid, ticket.assigned_date, nucleo.nucleo_ispettivo, nucleo.nucleo_ispettivo_due, nucleo.nucleo_ispettivo_tre, nucleo.nucleo_ispettivo_quattro, nucleo.nucleo_ispettivo_cinque, nucleo.nucleo_ispettivo_sei, nucleo.nucleo_ispettivo_sette, nucleo.nucleo_ispettivo_otto, nucleo.nucleo_ispettivo_nove, nucleo.nucleo_ispettivo_dieci, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R207'::text
            ELSE NULL::text
        END), asl1.description, o.account_number, ('0'::text || o.specie_allevamento::text), o.partita_iva, (
        CASE
            WHEN nucleo.nucleo_ispettivo::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%forestale% '::text THEN '002'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%NAS% '::text THEN '001'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%veterinari% '::text THEN '003'::text
            ELSE 'n.d.'::text
        END), (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), o.cf_detentore, op.tipo_produzione, (
        CASE
            WHEN op.codice_orientamento IS NOT NULL THEN op.codice_orientamento
            ELSE ''::text
        END), ticket.data_estrazione_b11, ticket.data_import_b11, ticket.esito_import_b11, ticket.descrizione_errore_b11, (
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN o.specie_allev::text ~~* '%suini%'::text THEN '2'::text
            WHEN o.specie_allev::text ~~* '%broiler%'::text THEN '5'::text
            WHEN o.specie_allev::text ~~* '%gallus%'::text THEN '1'::text
            ELSE '4'::text
        END), 'N'::text, (
        CASE
            WHEN o.specie_allev::text ~~* 'gallus'::text AND o.codice_tipo_allevamento IS NOT NULL THEN o.codice_tipo_allevamento
            ELSE '4'::text
        END), (
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END), lcm.code, lpiani.description
UNION
 SELECT DISTINCT ticket.id_bdn,
    ticket.id_bdn_b11,
    o.ragione_sociale,
    '-1'::integer AS org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R207'::text
            ELSE NULL::text
        END AS codice_asl,
    asl1.description AS asl,
    COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente) AS codice_azienda,
    btrim(COALESCE(o.partita_iva::text, o.codice_fiscale_impresa::text)) AS id_fiscale_allevamento,
        CASE
            WHEN qual.description::text ~~* '%forestale% '::text THEN '002'::text
            WHEN qual.description::text ~~* '%NAS%'::text THEN '001'::text
            WHEN qual.description::text ~~* '%veterinari%'::text THEN '003'::text
            ELSE '003'::text
        END AS occodice,
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END AS stato,
    ''::text AS id_fiscale_detentore,
    l.decodifica_tipo_produzione_bdn AS tipo_produzione,
    l.decodifica_codice_orientamento_bdn AS codice_orientamento,
    ticket.data_estrazione,
    ticket.data_import,
    ticket.esito_import,
    ticket.descrizione_errore,
    ticket.data_estrazione_b11,
    ticket.data_import_b11,
    ticket.esito_import_b11,
    ticket.descrizione_errore_b11,
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END AS operazione,
    'S'::text AS esito_controllo,
    lsa.description AS specie_allev,
        CASE
            WHEN lsa.description::text !~~* 'pesci'::text THEN '0'::text || l.decodifica_specie_bdn
            ELSE 'PES'::text
        END AS specie_allevamento,
        CASE
            WHEN (lsa.description::text ~~* 'gallus'::text OR lsa.description::text ~~* 'oche'::text OR lsa.description::text ~~* 'fagiani'::text) AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END AS tipo_allevamento_codice,
        CASE
            WHEN ticket.flag_preavviso IS NULL OR ticket.flag_preavviso ~~* '-1'::text THEN 'N'::text
            ELSE ticket.flag_preavviso
        END AS flag_preavviso,
    ticket.data_preavviso_ba AS data_preavviso,
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END AS flag_copia_checklist,
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END AS flag_esito_sa,
    lcm.code AS id_alleg,
    s.id AS id_stabilimento,
    lpiani.description AS pianomonitoraggio
   FROM ticket
     JOIN opu_stabilimento s ON ticket.id_stabilimento = s.id
     LEFT JOIN opu_operatore o ON o.id = s.id_operatore
     LEFT JOIN opu_indirizzo ind ON ind.id = s.id_indirizzo
     LEFT JOIN comuni1 c ON c.id = ind.comune
     JOIN linee_attivita_controlli_ufficiali olacu ON olacu.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN opu_relazione_stabilimento_linee_produttive rel ON rel.id = olacu.id_linea_attivita
     LEFT JOIN ml8_linee_attivita_nuove_materializzata l ON l.id_nuova_linea_attivita = rel.id_linea_produttiva --andrebbe gestito il codice
     LEFT JOIN nucleo_ispettivo_mv nucleo ON nucleo.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid AND ist.trashed_date IS NULL
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = l.decodifica_specie_bdn::integer
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     LEFT JOIN lookup_piano_monitoraggio lpiani ON lpiani.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = s.id_asl
     LEFT JOIN lookup_site_id asl1 ON asl1.code = ticket.site_id
     LEFT JOIN lookup_specie_allevata lsa ON lsa.short_description::text = l.decodifica_specie_bdn
  WHERE ticket.tipologia = 3 AND olacu.trashed_date is null and lpiani.codice_interno = 1483 AND ticket.trashed_date IS NULL AND o.trashed_date IS NULL AND s.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) >= 2015::double precision AND ((( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 OR lcm.code IS NULL OR (lcm.code = ANY (ARRAY[20, 22, 23])))
  GROUP BY ticket.id_bdn, ticket.id_bdn_b11, o.ragione_sociale, s.id, ticket.ticketid, ticket.assigned_date, nucleo.nucleo_ispettivo, nucleo.nucleo_ispettivo_due, nucleo.nucleo_ispettivo_tre, nucleo.nucleo_ispettivo_quattro, nucleo.nucleo_ispettivo_cinque, nucleo.nucleo_ispettivo_sei, nucleo.nucleo_ispettivo_sette, nucleo.nucleo_ispettivo_otto, nucleo.nucleo_ispettivo_nove, nucleo.nucleo_ispettivo_dieci, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R103'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R106'::text
            WHEN ticket.site_id = 205 THEN 'R205'::text
            WHEN ticket.site_id = 206 THEN 'R206'::text
            WHEN ticket.site_id = 207 THEN 'R207'::text
            ELSE NULL::text
        END), asl1.description, (COALESCE(rel.codice_nazionale, rel.codice_ufficiale_esistente)), ('0'::text || l.decodifica_specie_bdn), o.partita_iva, o.codice_fiscale_impresa, qual.description, l.decodifica_codice_orientamento_bdn, lsa.description, (
        CASE
            WHEN nucleo.nucleo_ispettivo::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%forestale% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%forestale% '::text THEN '002'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%NAS% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%NAS% '::text THEN '001'::text
            WHEN nucleo.nucleo_ispettivo::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_due::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_tre::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_quattro::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_cinque::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sei::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_sette::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_otto::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_nove::text ~~* '%veterinari% '::text OR nucleo.nucleo_ispettivo_dieci::text ~~* '%veterinari% '::text THEN '003'::text
            ELSE 'n.d.'::text
        END), (
        CASE
            WHEN ticket.closed IS NULL THEN 'APERTO'::text
            WHEN ticket.closed_nolista THEN 'CHIUSO SENZA LISTA PER ASSENZA DI NON CONFORMITA'''::text
            ELSE 'CHIUSO'::text
        END), l.decodifica_tipo_produzione_bdn, (
        CASE
            WHEN l.decodifica_codice_orientamento_bdn IS NOT NULL THEN l.decodifica_codice_orientamento_bdn
            ELSE ''::text
        END), ticket.data_estrazione_b11, ticket.data_import_b11, ticket.esito_import_b11, ticket.descrizione_errore_b11, (
        CASE
            WHEN ticket.data_estrazione_b11 IS NULL OR ticket.data_import_b11 IS NULL OR ticket.data_import_b11 IS NOT NULL AND ticket.esito_import_b11 = 'KO'::text THEN 'I'::text
            WHEN ticket.modified > ticket.data_estrazione_b11 OR ticket.data_import_b11 < ticket.data_estrazione_b11 THEN 'V'::text
            ELSE NULL::text
        END), (
        CASE
            WHEN ticket.punteggio <= 3 OR ticket.punteggio IS NULL THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN lcm.tipo_allegato_bdn IS NOT NULL THEN lcm.tipo_allegato_bdn || ''::text
            ELSE
            CASE
                WHEN lsa.description::text ~~* '%suini%'::text THEN '2'::text
                WHEN lsa.description::text ~~* '%avicoli%'::text THEN '5'::text
                WHEN lsa.description::text ~~* '%gallus%'::text THEN '1'::text
                WHEN lpiani.flag_vitelli = true THEN '3'::text
                ELSE '4'::text
            END
        END), 'N'::text, (
        CASE
            WHEN lsa.description::text ~~* 'gallus'::text AND 'o.codice_tipo_allevamento da recuperare da bdn' IS NOT NULL THEN 'o.codice_tipo_allevamento da recuperare da bdn'::text
            ELSE '4'::text
        END), (
        CASE
            WHEN ist.mod_b11_flag_rilascio_copia OR ticket.flag_checklist::text = 'S'::text THEN 'S'::text
            ELSE 'N'::text
        END), (
        CASE
            WHEN (( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 THEN 'S'::text
            ELSE 'N'::text
        END), lcm.code, lpiani.description;

ALTER TABLE public.bdn_candidati_sicurezza_alimentare_b11_org_opu
  OWNER TO postgres;

  -- View: public.report_campioni_new

-- DROP VIEW public.report_campioni_new;

CREATE OR REPLACE VIEW public.report_campioni_new AS 
 SELECT DISTINCT asl.code AS id_asl,
    asl.description AS asl,
    COALESCE(COALESCE(o.name, aziende.ragione_sociale), api.ragione_sociale) AS ragione_sociale,
    COALESCE(COALESCE(o.partita_iva, aziende.partita_iva::bpchar), api.partita_iva::bpchar) AS partita_iva,
        CASE
            WHEN aziende.id_stabilimento > 0 THEN 'Stabilimenti Nuova Gestione'::character varying
            WHEN o.tipologia = 201 THEN 'ZONE DI MOLLUSCHICOLTURA'::character varying
            WHEN lo.code > 0 THEN lo.description
            WHEN c.id_apiario > 0 THEN 'APIARIO'::character varying
            ELSE ''::character varying
        END AS tipo_operatore,
        CASE
            WHEN o.tipologia = 1 THEN (lin.categoria::text || '-->'::text) || lin.linea_attivita::text
            WHEN o.tipologia = 3 OR o.tipologia = 97 THEN (lass.linea_attivita_stabilimenti_soa || '-->'::text) || lass.linea_attivita_stabilimenti_soa_desc
            WHEN o.tipologia = 201 THEN lzp.description
            WHEN aziende.id_stabilimento > 0 THEN aziende.path_attivita_completo::text
            ELSE 'N.D'::text
        END AS tipologia_impianto,
        CASE
            WHEN lti.codice_interno_univoco ~~* '2A'::text THEN ((((dpatatt.descrizione || ' '::text) || '-'::text) || (ind.alias_indicatore || ' : '::text)) || ind.descrizione)::character varying
            WHEN lti.code > 0 AND lti.id_indicatore > 0 THEN ((((dpatattti.descrizione || ' '::text) || '-'::text) || (indti.alias_indicatore || ' : '::text)) || indti.descrizione)::character varying
            ELSE lti.description
        END AS motivazione_campione,
        CASE
            WHEN lti.codice_interno_univoco ~~* '2A'::text THEN ((lti.description::text || ':'::text) || lpm.description::text)::character varying
            ELSE lti.description
        END AS tipo_piano_monitoraggio_old,
        CASE
            WHEN lti.codice_interno_univoco = '2A'::text AND c.assigned_date > '2015-01-01 00:00:00'::timestamp without time zone THEN lpm.code
            WHEN lti.codice_interno_univoco = '2A'::text AND c.assigned_date < '2015-01-01 00:00:00'::timestamp without time zone THEN lpm.code
            ELSE NULL::integer
        END AS id_piano,
        CASE
            WHEN c.assigned_date > '2015-01-01 00:00:00'::timestamp without time zone THEN lti.id_indicatore
            ELSE lti.code
        END AS id_attivita,
        CASE
            WHEN lti.codice_interno_univoco = '2A'::text THEN 'P'::text
            WHEN lti.description IS NOT NULL THEN 'A'::text
            ELSE 'N.D.'::text
        END AS piano_attivita,
    c.ticketid AS id_campione,
    c.assigned_date AS data_prelievo,
    c.identificativo AS identificativo_campione,
    cu.componente_nucleo AS prelevatore_1_a4,
    cu.componente_nucleo_due AS prelevatore_2_a4,
    cu.componente_nucleo_tre AS prelevatore_3_a4,
        CASE
            WHEN lpm.description::text ~~* '%MON%'::text THEN '003'::text
            WHEN lpm.description::text ~~* '%SORV%'::text THEN '007'::text
            WHEN lpm.description::text ~~* '%EXTRAPIANO%'::text THEN '005'::text
            WHEN lti.description::text ~~* '%SOSPETT%'::text THEN '001'::text
            ELSE 'N.D.'::text
        END AS strategia_campionamento_a1,
        CASE
            WHEN lpm.description::text ~~* '%BSE%'::text THEN '1'::text
            WHEN lpm.description::text ~~* '%SOSTANZE FARMACOLOGICHE%CARRY OVER)'::text THEN '8'::text
            WHEN lpm.description::text ~~* '%SOSTANZE FARMACOLOGICHE)'::text OR lpm.description::text ~~* '%MONIT. OLIGOELEMENTI%'::text THEN '2'::text
            WHEN lpm.description::text ~~* '%contaminanti%'::text THEN '3'::text
            WHEN lpm.description::text ~~* '%DIOSSINE%'::text THEN '4'::text
            WHEN lpm.description::text ~~* '%MICOTOSSINE%'::text THEN '5'::text
            WHEN lpm.description::text ~~* '%SALMONELLA%'::text THEN '6'::text
            WHEN lpm.description::text ~~* '%OGM%'::text THEN '7'::text
            ELSE '9'::text
        END AS capitoli_piani_a3,
    lsp.description AS specie_alimento_b6,
        CASE
            WHEN cfv.valore_campione IS NOT NULL AND chf.label_campo::text ~~* '%B7%'::text THEN cfv.valore_campione
            ELSE c.check_circuito_ogm
        END AS metodo_produzione_b7,
        CASE
            WHEN c.assigned_date IS NULL THEN 'N.D.'::text
            ELSE date_part('year'::text, c.assigned_date)::text
        END AS anno_campione,
    to_date(c.closed::text, 'yyyy/mm/dd'::text) AS data_chiusura_campione,
    c.id_controllo_ufficiale,
        CASE
            WHEN c.sanzioni_amministrative > 0 AND ac.esito_id IS NULL THEN esito.description::text
            WHEN ac.esito_id > 0 THEN esitonew.description::text
            WHEN c.tipologia = 2 AND c.sanzioni_amministrative < 0 AND ac.esito_id < 0 THEN 'Da Attendere'::text
            ELSE 'N.D'::text
        END AS esito,
    COALESCE(ac.esito_punteggio, c.punteggio) AS punteggio_campione,
    COALESCE(resp_new.description, responsabilita.description::text::character varying) AS responsabilita_positivita,
    COALESCE(ac.esito_data, c.est_resolution_date) AS data_esito_analita,
    ac.esito_motivazione_respingimento,
    c.solution AS note_esito_campione,
    c.cause AS codice_accettazione,
    c.location AS num_verbale,
    b.barcode AS barcode_scheda,
    split_part(ac.cammino, '->'::text, 1) AS analita_lev_1,
        CASE
            WHEN split_part(ac.cammino, '->'::text, 2) IS NOT NULL AND split_part(ac.cammino, '->'::text, 2) <> ''::text THEN split_part(ac.cammino, '->'::text, 2)
            ELSE 'N.D'::text
        END AS analita_lev_2,
        CASE
            WHEN split_part(ac.cammino, '->'::text, 3) IS NOT NULL AND split_part(ac.cammino, '->'::text, 3) <> ''::text THEN split_part(ac.cammino, '->'::text, 3)
            ELSE 'N.D'::text
        END AS analita_lev_3,
        CASE
            WHEN split_part(ac.cammino, '->'::text, 4) IS NOT NULL AND split_part(ac.cammino, '->'::text, 4) <> ''::text THEN split_part(ac.cammino, '->'::text, 4)
            ELSE 'N.D'::text
        END AS analita_lev_4,
        CASE
            WHEN split_part(mc.cammino, '->'::text, 1) IS NOT NULL AND split_part(mc.cammino, '->'::text, 1) <> ''::text THEN split_part(mc.cammino, '->'::text, 1)
            ELSE 'N.D'::text
        END AS matrice_lev_1,
        CASE
            WHEN split_part(mc.cammino, '->'::text, 2) IS NOT NULL AND split_part(mc.cammino, '->'::text, 2) <> ''::text THEN split_part(mc.cammino, '->'::text, 2)
            ELSE 'N.D'::text
        END AS matrice_lev_2,
        CASE
            WHEN split_part(mc.cammino, '->'::text, 3) IS NOT NULL AND split_part(mc.cammino, '->'::text, 3) <> ''::text THEN split_part(mc.cammino, '->'::text, 3)
            ELSE 'N.D'::text
        END AS matrice_lev_3,
    c.problem AS note_campione,
        CASE
            WHEN c.assigned_date IS NULL THEN 'N.D.'::text
            ELSE date_part('year'::text, c.assigned_date)::text
        END AS anno_controllo,
    COALESCE(lpm.codice, lti.codice) AS codice_interno_piano_attivita
   FROM ticket c
     LEFT JOIN ticket cu ON cu.ticketid::text = c.id_controllo_ufficiale::text AND cu.tipologia = 3
     LEFT JOIN lookup_site_id asl ON c.site_id = asl.code
     LEFT JOIN linee_attivita_controlli_ufficiali lacu ON lacu.id_controllo_ufficiale::text = c.id_controllo_ufficiale::text
     LEFT JOIN la_imprese_linee_attivita lla ON lla.id = lacu.id_linea_attivita
     LEFT JOIN la_rel_ateco_attivita rel ON rel.id = lla.id_rel_ateco_attivita
     LEFT JOIN la_linee_attivita lin ON rel.id_linee_attivita = lin.id
     LEFT JOIN linee_attivita_controlli_ufficiali_stab_soa lass ON lass.id_controllo_ufficiale::text = c.id_controllo_ufficiale::text
     LEFT JOIN campioni_fields_value cfv ON cfv.id_campione = c.ticketid
     LEFT JOIN campioni_html_fields chf ON chf.id = cfv.id_campioni_html_fields
     LEFT JOIN lookup_piano_monitoraggio lppna ON lppna.code = chf.id_piano_monitoraggio AND lppna.codice_interno = 370
     LEFT JOIN lookup_specie_pnaa lsp ON lsp.code::text = cfv.valore_campione
     LEFT JOIN lookup_esito_campione esito ON c.sanzioni_amministrative = esito.code
     LEFT JOIN lookup_responsabilita_positivita responsabilita ON responsabilita.code = c.responsabilita_positivita
     LEFT JOIN organization o ON o.org_id = c.org_id AND (o.trashed_date IS NULL OR o.trashed_date = '1970-01-01 00:00:00'::timestamp without time zone)
     LEFT JOIN lookup_zone_di_produzione lzp ON lzp.code = o.tipologia_acque
     LEFT JOIN opu_operatori_denormalizzati_view aziende ON aziende.id_stabilimento = c.id_stabilimento
     LEFT JOIN apicoltura_apiari_denormalizzati_view api ON api.id_apicoltura_apiari = c.id_apiario
     LEFT JOIN lookup_tipologia_operatore lo ON lo.code = o.tipologia
     LEFT JOIN lookup_piano_monitoraggio lpm ON lpm.code = c.motivazione_piano_campione
     LEFT JOIN lookup_tipo_ispezione lti ON lti.code = c.motivazione_campione
     LEFT JOIN dpat_indicatore_new ind ON ind.id = lpm.code
     LEFT JOIN dpat_piano_attivita_new dpatatt ON dpatatt.id = ind.id_piano_attivita AND (dpatatt.stato = ANY (ARRAY[0, 2])) AND dpatatt.data_scadenza IS NULL AND dpatatt.anno::double precision = date_part('years'::text, c.assigned_date)
     LEFT JOIN dpat_indicatore_new indti ON indti.id = lti.code
     LEFT JOIN dpat_piano_attivita_new dpatattti ON dpatattti.id = indti.id_piano_attivita AND (dpatattti.stato = ANY (ARRAY[0, 2])) AND dpatattti.data_scadenza IS NULL AND dpatattti.anno::double precision = date_part('years'::text, c.assigned_date)
     LEFT JOIN analiti_campioni ac ON c.ticketid = ac.id_campione
     LEFT JOIN matrici_campioni mc ON mc.id_campione = c.ticketid
     LEFT JOIN lookup_responsabilita_positivita resp_new ON resp_new.code = ac.esito_responsabilita_positivita
     LEFT JOIN lookup_esito_campione esitonew ON ac.esito_id = esitonew.code
     LEFT JOIN barcode_osa b ON b.id_campione::text = c.ticketid::text
  WHERE c.tipologia = 2 and lacu.trashed_date is null AND c.trashed_date IS NULL AND cu.trashed_date IS NULL AND c.assigned_date IS NOT NULL AND c.location IS NOT NULL AND c.location::text <> ''::text;

ALTER TABLE public.report_campioni_new
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_sintesisnew(
    IN data_1 timestamp without time zone,
    IN data_2 timestamp without time zone)
  RETURNS TABLE(id_controllo_ufficiale integer, riferimento_id integer, riferimento_id_nome text, riferimento_id_nome_col text, riferimento_id_nome_tab text, id_asl integer, asl character varying, tipo_controllo character varying, tipo_ispezione_o_audit text, tipo_piano_monitoraggio text, id_piano integer, id_attivita integer, tipo_controllo_bpi text, tipo_controllo_haccp text, oggetto_del_controllo text, punteggio text, data_inizio_controllo date, anno_controllo text, data_chiusura_controllo date, aggiornata_cat_controllo text, categoria_rischio integer, prossimo_controllo timestamp with time zone, tipo_checklist text, linea_attivita_sottoposta_a_controllo text, unita_operativa text, id_struttura_uo integer, supervisionato_in_data timestamp without time zone, supervisionato_da character varying, supervisione_note text, congruo_supervisione text, note text, tipo_piano_monitoraggio_old text, codice_interno_univoco_uo integer, codice_interno_piano text, area_appartenenza_uo text, id_linea_controllata integer) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR 
		id_controllo_ufficiale,
		riferimento_id,
		riferimento_id_nome,
		riferimento_id_nome_col,
		riferimento_id_nome_tab,
		id_asl,  
		asl , 
		tipo_controllo  , 
		tipo_ispezione_o_audit , 
		tipo_piano_monitoraggio ,
		id_piano, id_attivita,
		tipo_controllo_bpi , tipo_controllo_haccp , 
		oggetto_del_controllo , punteggio , 
		data_inizio_controllo , anno_controllo,  data_chiusura_controllo, aggiornata_cat_controllo, categoria_rischio, prossimo_controllo, 
		tipo_checklist, 
		linea_attivita_sottoposta_a_controllo,
		unita_operativa, id_struttura_uo, supervisionato_in_data, supervisionato_da, supervisione_note, congruo_supervisione,note,  
		tipo_piano_monitoraggio_old,
		codice_interno_univoco_uo,codice_interno_piano,
		area_appartenenza_uo,id_linea_controllata
		in
		
		select t.ticketid,
		t.alt_id AS riferimento_id,
             'altId'::text AS riferimento_id_nome,
            'alt_id'::text AS riferimento_id_nome_col,
            'sintesis_stabilimento'::text AS riferimento_id_nome_tab,
            asl.code AS id_asl,
            asl.description AS asl,
            ltc.description AS tipo_controllo,

    CASE
                    WHEN tcu.audit_tipo > 0 AND tcu.tipo_audit <= 0 THEN lat.description::text
                    WHEN tcu.tipo_audit > 0 THEN (lat.description::text || ' - '::text) || lta.description::text
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio > 0 then  (CASE WHEN dp.tipo_attivita_piano ilike '%attivit%' THEN 'ATTIVITA ' ELSE 'PIANO ' END ) || dp.alias_attivita || ' ' || dp.descrizione_attivita
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio <= 0 AND tcu.tipoispezione <> 89 AND tcu.tipoispezione <> 2 
                    THEN (CASE WHEN da.tipo_attivita_piano ilike '%attivit%' THEN 'ATTIVITA ' ELSE 'PIANO ' END ) || da.alias_attivita || ' ' || da.descrizione_attivita
                    ELSE 'N.D'::text
                END AS tipo_ispezione_o_audit,
                CASE
                    WHEN tcu.tipoispezione > 0 AND lti.codice_interno !~~* '2a'::text THEN concat_ws(': ',da.alias_indicatore,da.descrizione_indicatore)
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 THEN concat_ws(': ',dp.alias_indicatore,dp.descrizione_indicatore)
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio,
                dp.id_fisico_indicatore AS id_piano,
                case when dp.id_fisico_indicatore > 0 then null
                else da.id_fisico_indicatore
                end as id_attivita,
            CASE
            WHEN tcu.tipo_audit = 2 AND t.provvedimenti_prescrittivi = 3 THEN 'BPI-'::text || lbpi.description::text
            ELSE 'N.D'::text
        END AS tipo_controllo_bpi,
        CASE
            WHEN tcu.tipo_audit = 3 AND t.provvedimenti_prescrittivi = 3 THEN 'HACCP-'::text || lhaccp.description::text
            ELSE 'N.D'::text
        END AS tipo_controllo_haccp,
               CASE
            WHEN oggcu.ispezione > 0 THEN (lim.description_old::text || ': '::text) || lisp.description::text
            ELSE 'N.D'::text
        END  AS oggetto_del_controllo,
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
                    WHEN t.provvedimenti_prescrittivi <> 5 THEN 'NON PREVISTA CATEGORIZZAZIONE'::text
                    ELSE NULL::text
                END AS aggiornata_cat_controllo,
            t.categoria_rischio,
            t.data_prossimo_controllo,
                CASE
                    WHEN audit.c IS NOT NULL and audit.c = 'T' THEN 'Temporanea'
                    WHEN audit.c IS NOT NULL and audit.c = 'D' THEN 'Definitiva'
                    WHEN (audit.c is null or audit.c = '') and audit.id_controllo is not null THEN 'Presenti più checklist'
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
                    WHEN nn.codice_interno_fk > 0 THEN nn.codice_interno_fk
                    ELSE n.codice_interno_fk
                END AS codice_interno_univoco_uo,
            COALESCE(lpiani.codice, lti.codice) AS codice_interno_piano_attivita,
             CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.descrizione_area_struttura
                    ELSE NULL::character varying(50)
                END AS descrizione_area_struttura, lacc.id_linea_attivita as id_linea_controllo
 from ticket t              
 LEFT JOIN contact_ c ON c.user_id = t.supervisionato_da
             LEFT JOIN checklist_controlli audit ON audit.id_controllo::text = t.id_controllo_ufficiale::text
             JOIN lookup_site_id asl ON t.site_id = asl.code
             JOIN lookup_tipo_controllo ltc ON t.provvedimenti_prescrittivi = ltc.code
             LEFT JOIN tipocontrolloufficialeimprese tcu ON t.ticketid = tcu.idcontrollo AND (tcu.tipoispezione > 0 OR tcu.audit_tipo > 0 OR tcu.tipo_audit > 0) AND tcu.enabled = true
	     LEFT JOIN tipocontrolloufficialeimprese oggcu ON t.ticketid = oggcu.idcontrollo AND oggcu.ispezione > 0
	     LEFT JOIN lookup_ispezione lisp ON oggcu.ispezione = lisp.code
             LEFT JOIN lookup_ispezione_macrocategorie lim ON lim.code = lisp.level AND lim.enabled
	     LEFT JOIN lookup_bpi lbpi ON tcu.bpi = lbpi.code
	     LEFT JOIN lookup_haccp lhaccp ON tcu.haccp = lhaccp.code
             LEFT JOIN linee_attivita_controlli_ufficiali lacc ON lacc.id_controllo_ufficiale = t.ticketid
             LEFT JOIN ml8_linee_attivita_nuove_materializzata lattcu ON lacc.codice_linea = lattcu.codice
             --LEFT JOIN sintesis_relazione_stabilimento_linee_produttive orslp ON orslp.id = lacc.id_linea_attivita
             --LEFT JOIN opu_linee_attivita_nuove lattcu ON orslp.id_linea_produttiva = lattcu.id_nuova_linea_attivita
             --LEFT JOIN opu_linee_attivita_nuove latt ON latt.id_nuova_linea_attivita = orslp.id_linea_produttiva
             LEFT JOIN lookup_tipo_ispezione lti ON tcu.tipoispezione = lti.code
             LEFT JOIN lookup_audit_tipo lat ON tcu.audit_tipo = lat.code
             LEFT JOIN lookup_tipo_audit lta ON tcu.tipo_audit = lta.code
             LEFT JOIN lookup_piano_monitoraggio lpiani ON tcu.pianomonitoraggio = lpiani.code AND t.assigned_date <= COALESCE(lpiani.data_scadenza, 'now'::text::date::timestamp without time zone)
             LEFT JOIN lookup_piano_monitoraggio lpianipadre ON lpiani.id_padre = lpianipadre.code AND lpianipadre.id_padre <= 0
	    -- JOIN NEW
	    LEFT JOIN view_motivi_linearizzati_dpat da on da.id_fisico_indicatore = tcu.tipoispezione 
	    LEFT JOIN view_motivi_linearizzati_dpat dp on dp.id_fisico_indicatore = tcu.pianomonitoraggio 
	    --fine join new
             LEFT JOIN dpat_strutture_asl n ON tcu.id_unita_operativa = n.id
             LEFT JOIN dpat_strutture_asl pp ON n.id_padre = pp.id
             LEFT JOIN unita_operative_controllo uoc ON uoc.id_controllo = t.ticketid
             LEFT JOIN dpat_strutture_asl nn ON nn.id = uoc.id_unita_operativa
             LEFT JOIN dpat_strutture_asl ppp ON nn.id_padre = ppp.id
	     WHERE t.tipologia = 3 and t.assigned_date  
	     between data_1 and data_2  
	     AND t.trashed_date IS NULL and lacc.trashed_date is null
	     AND t.alt_id > 0 and (select return_code from gestione_id_alternativo(t.alt_id, -1))=6
	
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_sintesisnew(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

alter function sintesis_importa_cu_da_opu RENAME TO OBS_sintesis_importa_cu_da_opu;
alter function sintesis_importa_cu_da_organization RENAME TO OBS_sintesis_importa_cu_da_organization;


CREATE OR REPLACE FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_anagraficanew(
    IN data_1 timestamp without time zone,
    IN data_2 timestamp without time zone)
  RETURNS TABLE(id_controllo_ufficiale integer, riferimento_id integer, riferimento_id_nome text, riferimento_id_nome_col text, riferimento_id_nome_tab text, id_asl integer, asl character varying, tipo_controllo character varying, tipo_ispezione_o_audit text, tipo_piano_monitoraggio text, id_piano integer, id_attivita integer, tipo_controllo_bpi text, tipo_controllo_haccp text, oggetto_del_controllo text, punteggio text, data_inizio_controllo date, anno_controllo text, data_chiusura_controllo date, aggiornata_cat_controllo text, categoria_rischio integer, prossimo_controllo timestamp with time zone, tipo_checklist text, linea_attivita_sottoposta_a_controllo text, unita_operativa text, id_struttura_uo integer, supervisionato_in_data timestamp without time zone, supervisionato_da character varying, supervisione_note text, congruo_supervisione text, note text, tipo_piano_monitoraggio_old text, codice_interno_univoco_uo integer, codice_interno_piano text, area_appartenenza_uo text, id_linea_controllata integer) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR 
		id_controllo_ufficiale,
		riferimento_id,
		riferimento_id_nome,
		riferimento_id_nome_col,
		riferimento_id_nome_tab,
		id_asl,  
		asl , 
		tipo_controllo  , 
		tipo_ispezione_o_audit , 
		tipo_piano_monitoraggio ,
		id_piano, id_attivita,
		tipo_controllo_bpi , tipo_controllo_haccp , 
		oggetto_del_controllo , punteggio , 
		data_inizio_controllo , anno_controllo,  data_chiusura_controllo, aggiornata_cat_controllo, categoria_rischio, prossimo_controllo, 
		tipo_checklist, 
		linea_attivita_sottoposta_a_controllo,
		unita_operativa, id_struttura_uo, supervisionato_in_data, supervisionato_da, supervisione_note, congruo_supervisione,note,  
		tipo_piano_monitoraggio_old,
		codice_interno_univoco_uo,codice_interno_piano,
		area_appartenenza_uo,id_linea_controllata
		in
		
		select t.ticketid,
		t.alt_id AS riferimento_id,
             'altId'::text AS riferimento_id_nome,
            'alt_id'::text AS riferimento_id_nome_col,
            'stabilimenti'::text AS riferimento_id_nome_tab,
            asl.code AS id_asl,
            asl.description AS asl,
            ltc.description AS tipo_controllo,

    CASE
                    WHEN tcu.audit_tipo > 0 AND tcu.tipo_audit <= 0 THEN lat.description::text
                    WHEN tcu.tipo_audit > 0 THEN (lat.description::text || ' - '::text) || lta.description::text
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio > 0 then  (CASE WHEN dp.tipo_attivita_piano ilike '%attivit%' THEN 'ATTIVITA ' ELSE 'PIANO ' END ) || dp.alias_attivita || ' ' || dp.descrizione_attivita
                    WHEN tcu.tipoispezione > 0 AND tcu.pianomonitoraggio <= 0 AND tcu.tipoispezione <> 89 AND tcu.tipoispezione <> 2 
                    THEN (CASE WHEN da.tipo_attivita_piano ilike '%attivit%' THEN 'ATTIVITA ' ELSE 'PIANO ' END ) || da.alias_attivita || ' ' || da.descrizione_attivita
                    ELSE 'N.D'::text
                END AS tipo_ispezione_o_audit,
                CASE
                    WHEN tcu.tipoispezione > 0 AND lti.codice_interno !~~* '2a'::text THEN concat_ws(': ',da.alias_indicatore,da.descrizione_indicatore)
                    WHEN lti.codice_interno ~~* '2A'::text AND t.provvedimenti_prescrittivi = 4 THEN concat_ws(': ',dp.alias_indicatore,dp.descrizione_indicatore)
                    ELSE 'N.D'::text
                END AS tipo_piano_monitoraggio,
                dp.id_fisico_indicatore AS id_piano,
                case when dp.id_fisico_indicatore > 0 then null
                else da.id_fisico_indicatore
                end as id_attivita,
            CASE
            WHEN tcu.tipo_audit = 2 AND t.provvedimenti_prescrittivi = 3 THEN 'BPI-'::text || lbpi.description::text
            ELSE 'N.D'::text
        END AS tipo_controllo_bpi,
        CASE
            WHEN tcu.tipo_audit = 3 AND t.provvedimenti_prescrittivi = 3 THEN 'HACCP-'::text || lhaccp.description::text
            ELSE 'N.D'::text
        END AS tipo_controllo_haccp,
               CASE
            WHEN oggcu.ispezione > 0 THEN (lim.description_old::text || ': '::text) || lisp.description::text
            ELSE 'N.D'::text
        END  AS oggetto_del_controllo,
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
                    WHEN t.provvedimenti_prescrittivi <> 5 THEN 'NON PREVISTA CATEGORIZZAZIONE'::text
                    ELSE NULL::text
                END AS aggiornata_cat_controllo,
            t.categoria_rischio,
            t.data_prossimo_controllo,
                CASE
                    WHEN audit.c IS NOT NULL and audit.c = 'T' THEN 'Temporanea'
                    WHEN audit.c IS NOT NULL and audit.c = 'D' THEN 'Definitiva'
                    WHEN (audit.c is null or audit.c = '') and audit.id_controllo is not null THEN 'Presenti più checklist'
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
                    WHEN nn.codice_interno_fk > 0 THEN nn.codice_interno_fk
                    ELSE n.codice_interno_fk
                END AS codice_interno_univoco_uo,
            COALESCE(lpiani.codice, lti.codice) AS codice_interno_piano_attivita,
             CASE
                    WHEN t.provvedimenti_prescrittivi = 4 AND (pp.n_livello <= 0 OR pp.n_livello IS NULL) THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 1 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi = 4 AND pp.n_livello = 2 THEN n.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND (ppp.n_livello <= 0 OR ppp.n_livello IS NULL) THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 1 THEN nn.descrizione_area_struttura
                    WHEN t.provvedimenti_prescrittivi <> 4 AND ppp.n_livello = 2 THEN nn.descrizione_area_struttura
                    ELSE NULL::character varying(50)
                END AS descrizione_area_struttura,lacc.id_linea_attivita as id_linea_controllo
 from ticket t              
 LEFT JOIN contact_ c ON c.user_id = t.supervisionato_da
             LEFT JOIN checklist_controlli audit ON audit.id_controllo::text = t.id_controllo_ufficiale::text
             JOIN lookup_site_id asl ON t.site_id = asl.code
             JOIN lookup_tipo_controllo ltc ON t.provvedimenti_prescrittivi = ltc.code
             LEFT JOIN tipocontrolloufficialeimprese tcu ON t.ticketid = tcu.idcontrollo AND (tcu.tipoispezione > 0 OR tcu.audit_tipo > 0 OR tcu.tipo_audit > 0) AND tcu.enabled = true
	     LEFT JOIN tipocontrolloufficialeimprese oggcu ON t.ticketid = oggcu.idcontrollo AND oggcu.ispezione > 0
	     LEFT JOIN lookup_ispezione lisp ON oggcu.ispezione = lisp.code
             LEFT JOIN lookup_ispezione_macrocategorie lim ON lim.code = lisp.level AND lim.enabled
	     LEFT JOIN lookup_bpi lbpi ON tcu.bpi = lbpi.code
	     LEFT JOIN lookup_haccp lhaccp ON tcu.haccp = lhaccp.code
             LEFT JOIN linee_attivita_controlli_ufficiali lacc ON lacc.id_controllo_ufficiale = t.ticketid
             --LEFT JOIN anagrafica.rel_stabilimenti_linee_attivita orslp ON orslp.id = lacc.id_linea_attivita and orslp.data_scadenza is null
             LEFT JOIN ml8_linee_attivita_nuove_materializzata lattcu ON lacc.codice_linea = lattcu.codice
             --LEFT JOIN ml8_linee_attivita_nuove_materializzata latt ON latt.id_nuova_linea_attivita = orslp.id_attivita
             LEFT JOIN lookup_tipo_ispezione lti ON tcu.tipoispezione = lti.code
             LEFT JOIN lookup_audit_tipo lat ON tcu.audit_tipo = lat.code
             LEFT JOIN lookup_tipo_audit lta ON tcu.tipo_audit = lta.code
             LEFT JOIN lookup_piano_monitoraggio lpiani ON tcu.pianomonitoraggio = lpiani.code AND t.assigned_date <= COALESCE(lpiani.data_scadenza, 'now'::text::date::timestamp without time zone)
             LEFT JOIN lookup_piano_monitoraggio lpianipadre ON lpiani.id_padre = lpianipadre.code AND lpianipadre.id_padre <= 0

	    -- JOIN NEW
	    LEFT JOIN view_motivi_linearizzati_dpat da on da.id_fisico_indicatore = tcu.tipoispezione 
	    LEFT JOIN view_motivi_linearizzati_dpat dp on dp.id_fisico_indicatore = tcu.pianomonitoraggio 
	    --fine join new
             LEFT JOIN dpat_strutture_asl n ON tcu.id_unita_operativa = n.id
             LEFT JOIN dpat_strutture_asl pp ON n.id_padre = pp.id
             LEFT JOIN unita_operative_controllo uoc ON uoc.id_controllo = t.ticketid
             LEFT JOIN dpat_strutture_asl nn ON nn.id = uoc.id_unita_operativa
             LEFT JOIN dpat_strutture_asl ppp ON nn.id_padre = ppp.id
	     WHERE t.tipologia = 3  and lacc.trashed_date is null
	     and t.assigned_date 
	      between data_1 and data_2  
	     AND t.trashed_date IS NULL 
	     AND t.alt_id > 0 and (select return_code from gestione_id_alternativo(t.alt_id, -1))=8
	
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguiti_anagraficanew(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

-- Function: funzioni_hd_per_segnalazioni.opu_gestione_errore_42(text, text, integer, timestamp without time zone, text, text, integer)

-- DROP FUNCTION funzioni_hd_per_segnalazioni.opu_gestione_errore_42(text, text, integer, timestamp without time zone, text, text, integer);

CREATE OR REPLACE FUNCTION funzioni_hd_per_segnalazioni.opu_gestione_errore_42(
    _numero_registrazione_linea text DEFAULT NULL::text,
    _nota text DEFAULT NULL::text,
    _idstab integer DEFAULT NULL::integer,
    _datainizio timestamp without time zone DEFAULT NULL::timestamp without time zone,
    _codice_nazionale text DEFAULT NULL::text,
    _attivita text DEFAULT NULL::text,
    _orgid integer DEFAULT NULL::integer)
  RETURNS text AS
$BODY$
DECLARE
	idlinearelstablp integer;
	idLinea integer;
	countLinea integer;
	countCU integer;
	id_controllo integer;
	msg text;

BEGIN
	idlinearelstablp:=(select nextval('opu_relazione_stabilimento_linee_produttive_id_seq'));
	--per migliorare la funzione, si potrebbe passare direttamente l'id della linea
	countlinea:= (select count(id_nuova_linea_attivita) from 
	ml8_linee_attivita_nuove_materializzata where attivita ilike _attivita);
	idLinea:= (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where attivita ilike _attivita);
	countCU:= (select count(*) from ticket where trashed_date is null and org_id = _orgid);
	 
	if(countlinea = 1)
	then
		insert into opu_relazione_stabilimento_linee_produttive 
		(id,id_linea_produttiva,id_stabilimento,data_inizio,enabled,stato,numero_registrazione,codice_nazionale,entered,modified,enteredby,modifiedby,note_internal_use_hd_only)
		values
		(
			idlinearelstablp,idLinea,_idStab,_dataInizio,true,0,_numero_registrazione_linea,_codice_nazionale,current_timestamp,current_timestamp,291,291,_nota
		);

		msg:= 'Inserita la linea di attività';

		if (countCU = 0) then
			msg:= 'Nessun CU presente';
		else 

			FOR id_controllo
			in
				select ticketid from ticket where org_id = _orgid and trashed_date is null
			LOOP
				update ticket set id_stabilimento = _idStab where ticketid = id_controllo;
				--insert into opu_linee_attivita_controlli_ufficiali (id_controllo_ufficiale,id_linea_attivita) values (id_controllo,idlinearelstablp);
				select * from public_functions.insert_linee_attivita_controlli_ufficiali(id_controllo,idlinearelstablp,'opu_relazione_stabilimento_linee_produttive');
				
			END LOOP;
			msg:= concat_ws(' - ',msg,'Spostati i CU sulla nuova linea');
		end if;

		-- cancello la vecchia anagrafica
		update organization set note_hd = _nota, trashed_date = now() where org_id = _org_id;
		msg:= concat_ws(' - ',msg,'Cancellata vecchia anagrafica');
		--refresh tabella materializzata
		select * from opu_insert_into_ricerche_anagrafiche_old_materializzata(_idstab);
	else
		msg:= 'Attenzione: linea non identificata univocamente per effettuare l''inserimento. Si prega di specificarla meglio';
	end if;

	return msg;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION funzioni_hd_per_segnalazioni.opu_gestione_errore_42(text, text, integer, timestamp without time zone, text, text, integer)
  OWNER TO postgres;

  -- Function: funzioni_hd_per_segnalazioni.scorporamento_linea_da_stabilimento(integer, text, integer)

-- DROP FUNCTION funzioni_hd_per_segnalazioni.scorporamento_linea_da_stabilimento(integer, text, integer);

CREATE OR REPLACE FUNCTION funzioni_hd_per_segnalazioni.scorporamento_linea_da_stabilimento(
    _idistanza integer,
    _numregistrazione text,
    _idutente integer)
  RETURNS text AS
$BODY$
DECLARE
msg text;
idStabilimento integer;
tipoFissoMobile integer;
flagMobile boolean;
numLinee integer;
numLineeIbride integer;

numeroRegistrazione text ;
tipoImpresa int;
tipoAttivita int ;
idComune int ; 
codiceComune text;
codiceProvincia text;

dataAttuale timestamp without time zone;
idStabilimentoNuovo integer;
numTicket integer;
notesHd text;
notesHdOrigine text;
numeroRegistrazionePrecedente text;

BEGIN

msg := '';
dataAttuale := CURRENT_TIMESTAMP;

IF _idIstanza<=0 or _idIstanza is null THEN
IF _numRegistrazione is not null and _numRegistrazione <> '' THEN
--Controllo aggiuntivo per estrarre solo numeri registrazioni linea non duplicati
select rel.id into _idIstanza from opu_relazione_stabilimento_linee_produttive rel where trim(rel.numero_registrazione) ilike trim(_numRegistrazione) and trim(rel.numero_registrazione) not in (select trim(numero_registrazione) 
from opu_relazione_stabilimento_linee_produttive where id<>rel.id and trim(numero_registrazione) ilike trim(_numRegistrazione) ); 
raise info 'Id istanza recuperato: % %', _idIstanza, _numRegistrazione;
END IF;
END IF;

IF _idIstanza<=0 or _idIstanza is null THEN
msg= 'KO! Errore input istanza. Indicare un id istanza valido al parametro 1 o un numero registrazione valido al parametro 2.';
return msg;
END IF;

-- Controllo esistenza istanza
select id into _idIstanza from opu_relazione_stabilimento_linee_produttive where enabled and id = _idIstanza;

IF _idIstanza<=0 or _idIstanza is null THEN
msg= 'KO! Istanza inesistente.';
return msg;
END IF;

-- Recupero id stabilimento origine
select id_stabilimento into idStabilimento from opu_relazione_stabilimento_linee_produttive where id = _idIstanza;
raise info 'Id Stabilimento: %',idStabilimento;

-- Recupero stabilimento origine 
select tipo_attivita, numero_registrazione  into tipoFissoMobile, numeroRegistrazionePrecedente from opu_stabilimento  where id = idStabilimento;
raise info 'Tipo Fisso/Mobile: %',tipoFissoMobile;

-- Recupero tipologia linea da scorporare
select f.mobile into flagMobile from master_list_flag_linee_attivita f
join ml8_linee_attivita_nuove_materializzata ml8 on ml8.codice_attivita = f.codice_univoco
join opu_relazione_stabilimento_linee_produttive rel on rel.id_linea_produttiva = ml8.id_nuova_linea_attivita
where rel.id = _idIstanza;
raise info 'Flag Mobile: %',flagMobile;

-- Recupero numero linee stabilimento
select count(*) into numLinee from opu_relazione_stabilimento_linee_produttive where id_stabilimento = idStabilimento and enabled;
raise info 'Num Linee: %',numLinee;

-- Recupero tipologia linee stabilimento
select count (*) into numLineeIbride from (select distinct f.mobile, f.fisso from master_list_flag_linee_attivita f
join ml8_linee_attivita_nuove_materializzata ml8 on ml8.codice_attivita = f.codice_univoco
join opu_relazione_stabilimento_linee_produttive rel on rel.id_linea_produttiva = ml8.id_nuova_linea_attivita
where rel.id_stabilimento = idStabilimento and rel.enabled) aa;
raise info 'Num Linee Ibride: %',numLineeIbride;

if tipoFissoMobile <> 1 THEN
msg = concat_ws(';', msg, 'Lo stabilimento non risulta FISSO.');
END IF;

if flagMobile is not true THEN
msg = concat_ws(';', msg, 'La linea non risulta MOBILE.');
END IF;

if numLinee <2 THEN
msg = concat_ws(';', msg, 'Lo stabilimento di origine non avrebbe linee sopravvissute allo scorporamento.');
END IF;


if numLineeIbride <2 THEN
msg = concat_ws(';', msg, 'Lo stabilimento di origine non risulta ibrido e non contiene linee fisse e mobili contemporaneamente.');
END IF;


IF msg <> '' THEN
msg = 'KO! '||msg||'. Si ricordano le condizioni necessarie: linea da scorporare MOBILE, stabilimento di origine FISSO, stabilimento di origine AVENTE PIU'' DI DUE LINEE, stabilimento di origine AVENTE LINEE FISSE E LINEE MOBILI.';
return msg;
END IF;

-- Procedo con lo scorporamento


-- Recupero i dati necessari alla generazione del numero registrazione
tipoImpresa := (select tipo_impresa from opu_operatore where id in (select id_operatore from opu_stabilimento where id = idStabilimento));
tipoAttivita := ( select tipo_attivita from opu_stabilimento where id = idStabilimento);


if tipoImpresa=1
then
	if tipoAttivita=2
		then 
			idComune:=(select ind.comune from opu_indirizzo ind 
				join opu_soggetto_fisico sogg on sogg.indirizzo_id = ind.id
				join opu_rel_operatore_soggetto_fisico rel on rel.id_soggetto_fisico=sogg.id
				where rel.id_operatore in (select id_operatore from opu_stabilimento where id = idStabilimento) );
		else
		if tipoAttivita=1
		then
			idComune:=(select ind.comune from opu_indirizzo ind 
				join opu_stabilimento st on st.id_indirizzo = ind.id
				where st.id=idStabilimento  );
		end if ;
	end if ;
else
	if tipoAttivita=1
		then
			idComune:=(select ind.comune from opu_indirizzo ind 
				join opu_stabilimento st on st.id_indirizzo = ind.id
				where st.id =idStabilimento );
		end if ;
	if tipoAttivita=2
		then
			idComune:=(select ind.comune from opu_indirizzo ind 
				join opu_operatore st on st.id_indirizzo = ind.id
				where st.id in (select id_operatore from opu_stabilimento where id = idStabilimento) );
		end if ;
end if ;			

raise info 'Valore di comune : %',idComune;

select comuni1.cod_comune, lp.cod_provincia into codiceComune, codiceProvincia from comuni1 join lookup_province lp on lp.code=  comuni1.cod_provincia::int where comuni1.id = idComune;
numeroRegistrazione:= (select genera_numero_registrazione from anagrafica.genera_numero_registrazione(codiceComune, codiceProvincia));
raise info 'Numero registrazione generato : %',numeroRegistrazione;

notesHd:= 'Stabilimento scorporato da vecchio Stabilimento id '||idStabilimento||' da utente id '||_idUtente||' con linea istanza '||_idIstanza||' in data '||dataAttuale;

-- Inserisco nuovo stabilimento
insert into opu_stabilimento (entered, entered_by, id_operatore, id_asl, id_indirizzo, riferimento_org_id, cun, id_sinvsa, stato, numero_registrazione, data_generazione_numero, tipo_attivita, tipo_carattere, data_inizio_attivita, data_fine_attivita, notes_hd, numero_registrazione_precedente) 
select dataAttuale, _idUtente, id_operatore, id_asl, id_indirizzo, riferimento_org_id, cun, id_sinvsa, stato, numeroRegistrazione, dataAttuale, 2, tipo_carattere, data_inizio_attivita, data_fine_attivita, notesHd, numeroRegistrazionePrecedente from opu_stabilimento where id = idStabilimento returning id into idStabilimentoNuovo;
raise info 'Stabilimento inserito id: %',idStabilimentoNuovo;

IF idStabilimentoNuovo<=0 THEN
msg= 'KO! Errore inserimento stabilimento.';
return msg;
END IF;

notesHdOrigine:= 'Stabilimento scorporato verso nuovo Stabilimento id '||idStabilimentoNuovo||' da utente id '||_idUtente||' con linea istanza '||_idIstanza||' in data '||dataAttuale;

-- Aggiorno istanza
update opu_relazione_stabilimento_linee_produttive set numero_registrazione_old = numero_registrazione, id_stabilimento = idStabilimentoNuovo, numero_registrazione = numeroRegistrazione||'001', note_internal_use_hd_only = concat_ws(';', note_internal_use_hd_only, 'Linea spostata da stabilimento '||idStabilimento||' a stabilimento '||idStabilimentoNuovo||' dopo scorporamento.') where id = _idIstanza;

-- Sposto CU interessati da vecchio a nuovo stabilimento
WITH rows AS (
update ticket set id_stabilimento = idStabilimentoNuovo, note_internal_use_only = concat_ws(';', 'CU Spostato da stabilimento '||idStabilimento||' a stabilimento '||idStabilimentoNuovo||' dopo scorporamento linea.') where tipologia = 3 and ticketid in 
(select id_controllo_ufficiale from linee_attivita_controlli_ufficiali where id_linea_attivita = _idIstanza and trashed_date is null) RETURNING 1
)
SELECT count(*) into numTicket FROM rows;
raise info 'CU aggiornati num: %',numTicket;

-- Aggiorno stabilimento origine
update opu_stabilimento set notes_hd = concat_ws(';', notes_hd, notesHdOrigine) where id = idStabilimento;

-- Refresh ricerca materializzata
msg=concat_ws(';', msg, (select * from refresh_anagrafica(idStabilimento, 'opu')));
raise info 'Refresh ricerche materializzate Stabilimento id: %',idStabilimento;
msg=concat_ws(';', msg, (select * from refresh_anagrafica(idStabilimentoNuovo, 'opu')));
raise info 'Refresh ricerche materializzate Stabilimento id: %',idStabilimentoNuovo;

msg:=concat_ws(';', 'OK! Inserito stabilimento.', 'Id', idStabilimentoNuovo, 'Nuovo numero registrazione', numeroRegistrazione ,msg);

return msg;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION funzioni_hd_per_segnalazioni.scorporamento_linea_da_stabilimento(integer, text, integer)
  OWNER TO postgres;
  
alter function dbi_allineamento_gisa_con_gisa_ext rename to OBS_dbi_allineamento_gisa_con_gisa_ext;
ALTER FUNCTION dbi_conta_controlli_ufficiali_su_linee_produttive rename to OBS_dbi_conta_controlli_ufficiali_su_linee_produttive;
alter function public.opu_can_pnaa_solo_opu rename to OBS_opu_can_pnaa_solo_opu;


CREATE OR REPLACE FUNCTION public.dbi_get_controlli_ufficiali_su_linee_produttive(IN idstabilimento integer)
  RETURNS TABLE(idcontrollo integer, id_stabilimento integer, id_rel_stab_lp_out integer, id_linea_master_list_out integer, enabled_out boolean, descrizione_out text, id_norma_out integer, id_stato_out integer) AS
$BODY$
DECLARE
	
	 	
BEGIN
		FOR
		idcontrollo,id_stabilimento,id_rel_stab_lp_out ,id_linea_master_list_out ,enabled_out ,descrizione_out, id_norma_out, id_stato_out 
		in

		select distinct cu.ticketid,cu.id_stabilimento,r.id as id_rel_stab_lp ,r.id_linea_produttiva as id_linea_master_list,r.enabled,path_descrizione, ll.id_norma, r.stato

from opu_relazione_stabilimento_linee_produttive r 
left join linee_attivita_controlli_ufficiali on  r.id = linee_attivita_controlli_ufficiali.id_linea_attivita 
left join ticket cu on cu.ticketid = linee_attivita_controlli_ufficiali.id_controllo_ufficiale and cu.id_stabilimento = r.id_stabilimento and cu.trashed_date is null
left join ml8_linee_attivita_nuove ll on ll.id_nuova_linea_attivita=r.id_linea_produttiva
where r.id_stabilimento =idstabilimento and r.enabled and linee_attivita_controlli_ufficiali.trashed_date is null
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.dbi_get_controlli_ufficiali_su_linee_produttive(integer)
  OWNER TO postgres;
  
  
  -- Function: public.opu_errata_corrige_stabilimento_variazione_linee_attivita(integer, integer, integer, text)

-- DROP FUNCTION public.opu_errata_corrige_stabilimento_variazione_linee_attivita(integer, integer, integer, text);

CREATE OR REPLACE FUNCTION public.opu_errata_corrige_stabilimento_variazione_linee_attivita(
    idrelstablp integer,
    idlineaproduttivanuova integer,
    idutente integer,
    codicenazionale text)
  RETURNS integer AS
$BODY$
DECLARE
idRelslp integer;
BEGIN

insert into opu_gestione_errata_corrige_stabilimento (data_errata_corrige,tipo_errata_corrige,id_stabilimento_coinvolto,id_linea_precedente,id_controllo,id_utente)
  (
select current_timestamp,3,r.id_stabilimento,r.id,lcu.id_controllo_ufficiale,idUtente
from opu_relazione_stabilimento_linee_produttive  r
left join linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita=r.id
where r.id = idRelStabLp and lcu.trashed_date is null
);

idRelslp:= (select setval('opu_relazione_stabilimento_linee_produttive_id_seq',nextval('opu_relazione_stabilimento_linee_produttive_id_seq')));
	        
update opu_relazione_stabilimento_linee_produttive set enabled = false,modified = current_timestamp where id = idRelStabLp;

insert into opu_relazione_stabilimento_linee_produttive (id,id_linea_produttiva,id_stabilimento,stato,data_inizio,data_fine,enabled,modified,modifiedby,numero_registrazione,entered,enteredby,codice_ufficiale_esistente,codice_nazionale)
(
select idRelslp,
idLineaProduttivaNuova,id_stabilimento,stato,data_inizio,data_fine,true,current_timestamp,idUtente,numero_registrazione,current_timestamp,idUtente,codice_ufficiale_esistente::text,codiceNazionale
from opu_relazione_stabilimento_linee_produttive where id = idRelStabLp
);

update linee_attivita_controlli_ufficiali set id_linea_attivita =idRelslp where id_linea_attivita =idRelStabLp;

return idRelslp ;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.opu_errata_corrige_stabilimento_variazione_linee_attivita(integer, integer, integer, text)
  OWNER TO postgres;
  
-- Function: public.get_cu_richiede_checklist_macelli(integer)

-- DROP FUNCTION public.get_cu_richiede_checklist_macelli(integer);

CREATE OR REPLACE FUNCTION public.get_cu_richiede_checklist_macelli(idcu integer)
  RETURNS boolean AS
$BODY$
   DECLARE
flag boolean;   
idlinea integer;
idpiano integer;

altId integer;
stabId integer;
tipoPartizione integer;

BEGIN
	flag := false;
tipoPartizione := -1;
idlinea := -1;
idpiano := -1;

altId := (select alt_id from ticket where ticketid = idcu);
stabId := (select id_stabilimento from ticket where ticketid = idcu);

if altId > 0 THEN
tipoPartizione := (select return_code from gestione_id_alternativo(100000001, -1));
END IF;

if tipoPartizione = -1 and stabId > 0 THEN
tipoPartizione := 2;
END IF;

IF tipoPartizione = 6 or tipoPartizione = 2 

THEN
idlinea := (
--select slac.id_linea_attivita from sintesis_linee_attivita_controlli_ufficiali slac
--join sintesis_relazione_stabilimento_linee_produttive rel on slac.id_linea_attivita = rel.id
--join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva
--where slac.id_controllo_ufficiale = idcu  and linee.codice_attivita in ('MS.B-MS.B10-MS.B10.100', 'MS.040-MS.040.200-852IT5A101') 
select slac.id_linea_attivita from 
linee_attivita_controlli_ufficiali slac
where slac.id_controllo_ufficiale = idcu  
and slac.trashed_date is null and codice_linea in ('MS.B-MS.B10-MS.B10.100', 'MS.040-MS.040.200-852IT5A101') 
limit 1);
END IF;

--IF tipoPartizione = 2 THEN

--idlinea := (
--select olac.id_linea_attivita from opu_linee_attivita_controlli_ufficiali olac
--join opu_relazione_stabilimento_linee_produttive rel on olac.id_linea_attivita = rel.id
--join ml8_linee_attivita_nuove_materializzata linee on linee.id_nuova_linea_attivita = rel.id_linea_produttiva
--where olac.id_controllo_ufficiale = idcu and linee.codice_attivita in ('MS.B-MS.B10-MS.B10.100', 'MS.040-MS.040.200-852IT5A101') limit 1);

--END IF;

idpiano := (select tcu.pianomonitoraggio from tipocontrolloufficialeimprese tcu 
join dpat_indicatore_new ind on ind.id = tcu.pianomonitoraggio
where tcu.idcontrollo = idcu and tcu.enabled and ind.alias_indicatore ilike 'A13_U'
limit 1);

IF idlinea>0 and idpiano>0 THEN
flag := true;
END IF;
	RETURN flag;
	
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_cu_richiede_checklist_macelli(integer)
  OWNER TO postgres;

  

CREATE OR REPLACE FUNCTION public.opu_can_pnaa(id_cu integer)
  RETURNS boolean AS
$BODY$
DECLARE
org_id          int;
id_stabilimento_var int;
id_stabilimento_sin int;

tipologia       int;
conta integer;
--conta_sin integer;
risultato integer;
r RECORD;	
BEGIN
org_id := (select t.org_id 
	        from ticket t, organization o 
	        where o.org_id = t.org_id and 
	              t.ticketid = id_cu and 
	              t.tipologia = 3 and
	              t.trashed_date is null and
	              o.trashed_date is null
               );
               
       tipologia := (select o.tipologia 
	        from ticket t, organization o 
	        where o.org_id = t.org_id and 
	              t.ticketid = id_cu and 
	              t.tipologia = 3 and
	              t.trashed_date is null and
	              o.trashed_date is null
               );

       id_stabilimento_var := (select t.id_stabilimento
	        from ticket t, opu_stabilimento o 
	        where o.id = t.id_stabilimento and 
	              t.ticketid = id_cu and 
	              t.tipologia = 3 and
	              t.trashed_date is null and
	              o.trashed_date is null

                 );

                   id_stabilimento_sin := (select o.id
	        from ticket t, sintesis_stabilimento o 
	        where o.alt_id = t.alt_id and 
	              t.ticketid = id_cu and 
	              t.tipologia = 3 and
	              t.trashed_date is null and
	              o.trashed_date is null

	              
                 );
       --qui c'è il problema...stat non è una variabile ma un record..quindi non si può fare sta=true...
       conta := (select count(*) from linee_attivita_controlli_ufficiali ol
					  join ml8_linee_attivita_nuove_materializzata m on ol.codice_linea = m.codice
					  where ol.id_controllo_ufficiale = id_cu and m.flag_pnaa
					  and ol.trashed_date is null);                 

 --conta_sin := (select count(*) from sintesis_linee_attivita_controlli_ufficiali ol
--			           join sintesis_relazione_stabilimento_linee_produttive ol1 on ol1.id = ol.id_linea_attivita
--					   join ml8_linee_attivita_nuove o on o.id_nuova_linea_attivita = ol1.id_linea_produttiva
--			                   join ml8_master_list m on m.id = o.id_macroarea
--					   where ol.id_controllo_ufficiale = id_cu   and m.flag_pnaa);          
					   
IF (org_id is not null and org_id>0 and tipologia in (1,800,801,97,2) )  THEN
   risultato:= 1;
ELSIF(id_stabilimento_var > 0 and conta > 0) THEN
   risultato:= 1;
   --ELSIF(id_stabilimento_sin > 0 and conta_sin > 0) THEN
   --risultato:= 1;
ELSE
   risultato:= 0;
END IF;

if(risultato=1) then
	return true;
else
	return false;
end if;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.opu_can_pnaa(integer)
  OWNER TO postgres; 
  
DROP VIEW public.org_linee_attivita_view;

CREATE OR REPLACE VIEW public.org_linee_attivita_view AS 
 SELECT DISTINCT i.id,
    i.org_id,
    i.id_rel_ateco_attivita,
    i.id_attivita_masterlist,
    i.mappato,
    i.primario,
    i.entered,
    i.entered_by,
    i.modified,
    i.modified_by,
    i.trashed_date,
    opu.macroarea,
    opu.aggregazione,
    concat_ws('-'::text, cod.description, opu.attivita) AS attivita,
    opu.codice,
    la.categoria,
    la.linea_attivita,
    cod.description AS codice_istat,
    cod.short_description AS descrizione_codice_istat,
    1 AS tipo
   FROM la_imprese_linee_attivita i
     LEFT JOIN ml8_linee_attivita_nuove_materializzata opu ON opu.id_attivita = i.id_attivita_masterlist,
    la_rel_ateco_attivita rel,
    la_linee_attivita la,
    lookup_codistat cod
  WHERE i.trashed_date IS NULL AND i.id_rel_ateco_attivita = rel.id AND rel.id_linee_attivita = la.id AND rel.id_lookup_codistat = cod.code AND i.trashed_date IS NULL
UNION
 SELECT DISTINCT r.id_linea AS id,
    r.riferimento_id AS org_id,
    '-1'::integer AS id_rel_ateco_attivita,
    r.id_attivita AS id_attivita_masterlist,
        CASE
            WHEN r.tipologia_operatore <> 1 AND r.tipologia_operatore <> 2 AND (r.attivita IS NULL OR length(btrim(r.attivita)) <= 3 OR r.aggregazione IS NULL OR length(btrim(r.aggregazione)) <= 3 OR r.macroarea IS NULL OR length(btrim(r.macroarea)) <= 3) THEN false
            ELSE true
        END AS mappato,
    false AS primario,
    NULL::timestamp with time zone AS entered,
    '-1'::integer AS entered_by,
    NULL::timestamp with time zone AS modified,
    '-1'::integer AS modified_by,
    NULL::timestamp with time zone AS trashed_date,
    r.macroarea,
    r.aggregazione,
    r.attivita,
    concat_ws('-'::text, r.codice_macroarea, r.codice_aggregazione, r.codice_attivita) AS codice,
    NULL::text AS categoria,
    NULL::text AS linea_attivita,
    NULL::text AS codice_istat,
    NULL::text AS descrizione_codice_istat,
    r.tipologia_operatore AS tipo
   FROM ricerche_anagrafiche_old_materializzata r
  WHERE r.tipologia_operatore <> 1;

ALTER TABLE public.org_linee_attivita_view
  OWNER TO postgres;
  
  
DROP VIEW public.opu_linee_attivita_stabilimenti_view;

CREATE OR REPLACE VIEW public.opu_linee_attivita_stabilimenti_view AS 
 SELECT DISTINCT v2.id_attivita,
    v1.id_stabilimento AS org_id,
    v1.id_linea_produttiva AS id_rel_ateco_attivita,
    v1.primario,
    v2.aggregazione AS categoria,
    v2.attivita AS linea_attivita,
    v2.codice_attivita AS codice_istat,
    v2.codice,
    v1.id,
    v1.enabled,
    v2.macroarea
   FROM opu_relazione_stabilimento_linee_produttive v1
     LEFT JOIN ml8_linee_attivita_nuove_materializzata v2 ON v1.id_linea_produttiva = v2.id_nuova_linea_attivita
  WHERE v1.enabled;

ALTER TABLE public.opu_linee_attivita_stabilimenti_view
  OWNER TO postgres;
  
 DROP VIEW public.suap_ric_scia_linee_attivita_stabilimenti_view;
  -- View: public.suap_ric_scia_linee_attivita_stabilimenti_view

CREATE OR REPLACE VIEW public.suap_ric_scia_linee_attivita_stabilimenti_view AS 
 SELECT stab.alt_id,
    v1.id_linea_produttiva AS id_rel_ateco_attivita,
    v1.primario,
    v2.aggregazione AS categoria,
    v2.attivita AS linea_attivita,
    v2.codice_attivita AS codice_istat,
    v2.codice,
    v1.id,
    v1.enabled,
    v2.macroarea
   FROM suap_ric_scia_relazione_stabilimento_linee_produttive v1
     JOIN ml8_linee_attivita_nuove_materializzata v2 ON v1.id_linea_produttiva = v2.id_nuova_linea_attivita
     LEFT JOIN suap_ric_scia_stabilimento stab ON stab.id = v1.id_stabilimento;

ALTER TABLE public.suap_ric_scia_linee_attivita_stabilimenti_view
  OWNER TO postgres;

  drop view public.sintesis_linee_attivita_stabilimenti_view;

CREATE OR REPLACE VIEW public.sintesis_linee_attivita_stabilimenti_view AS
 SELECT stab.alt_id,
    v1.id_linea_produttiva AS id_rel_ateco_attivita,
    v1.primario,
    ml8.aggregazione AS categoria,
    ml8.attivita as linea_attivita,
    ml8.codice,
    ''::text AS codice_istat,
    v1.id,
    v1.enabled,
    ml8.macroarea
   FROM sintesis_relazione_stabilimento_linee_produttive v1
     JOIN ml8_linee_attivita_nuove_materializzata ml8 ON v1.id_linea_produttiva = ml8.id_nuova_linea_attivita
     LEFT JOIN sintesis_stabilimento stab ON stab.id = v1.id_stabilimento;

ALTER TABLE public.sintesis_linee_attivita_stabilimenti_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.sintesis_linee_attivita_stabilimenti_view TO postgres;

  
 DROP FUNCTION public.get_linee_attivita(integer, text, boolean, integer);
-- Function: public.get_linee_attivita(integer, text, boolean, integer)

-- DROP FUNCTION public.get_linee_attivita(integer, text, boolean, integer);
-- Function: public.get_linee_attivita(integer, text, boolean, integer)

-- DROP FUNCTION public.get_linee_attivita(integer, text, boolean, integer);

CREATE OR REPLACE FUNCTION public.get_linee_attivita(
    IN _riferimentoid integer,
    IN _riferimentoidnometab text,
    IN _primario boolean,
    IN _idcu integer)
  RETURNS TABLE(id integer, riferimento_id integer, id_rel_ateco_attivita integer, id_attivita_masterlist integer, mappato boolean, primario boolean, entered timestamp without time zone, entered_by integer, modified timestamp without time zone, modified_by integer, trashed_date timestamp without time zone, macroarea text, aggregazione text, attivita text, codice_linea text, categoria text, linea_attivita text, codice_istat text, descrizione_codice_istat text, id_attivita integer, enabled boolean) AS
$BODY$
DECLARE
r RECORD;	

BEGIN


IF (_riferimentoIdNomeTab = 'organization') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, 
trashed_date, macroarea , aggregazione , attivita , codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select distinct
v.id , v.org_id , v.id_rel_ateco_attivita , v.id_attivita_masterlist , v.mappato, v.primario, v.entered, v.entered_by, v.modified, v.modified_by, 
v.trashed_date, v.macroarea , v.aggregazione , v.attivita , v.codice, v.categoria , coalesce(v.linea_attivita, v.attivita) as linea_attivita , v.codice_istat , v.descrizione_codice_istat, -1, true 
from org_linee_attivita_view v
left join linee_attivita_controlli_ufficiali lcu on v.id=lcu.id_linea_attivita
--left join ticket t on t.org_id = v.org_id
where 1=1 
and ((_riferimentoId>0 and v.org_id = _riferimentoId) or (_riferimentoId=-1))
and ((_idcu>0 and (lcu.id_controllo_ufficiale=_idcu and lcu.trashed_date is null)) or (_idcu=-1))

LOOP
RETURN NEXT;
END LOOP;
END IF;

IF (_riferimentoIdNomeTab = 'opu_stabilimento') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, trashed_date, macroarea , aggregazione , 
attivita , codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id , v.org_id , v.id_rel_ateco_attivita , -1 , null, v.primario, null, -1, null, -1, null, v.macroarea , null , null , v.codice, v.categoria , v.linea_attivita , v.codice_istat , null, v.id_attivita, v.enabled
from opu_linee_attivita_stabilimenti_view v
left join linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita = v.id 
where 1=1 
and ((_riferimentoId>0 and v.org_id = _riferimentoId and v.enabled) or (_riferimentoId=-1))
and ((_idcu>0 and (lcu.id_controllo_ufficiale=_idcu and lcu.trashed_date is null)) or (_idcu=-1))

LOOP
RETURN NEXT;
END LOOP;
END IF;

IF (_riferimentoIdNomeTab = 'sintesis_stabilimento') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, 
trashed_date, macroarea , aggregazione , attivita , codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id , v.alt_id , v.id_rel_ateco_attivita , -1 , null, v.primario, null, -1, null, -1, null, v.macroarea , null , null , 
v.codice, v.categoria , v.linea_attivita , v.codice_istat , null, -1, v.enabled
from sintesis_linee_attivita_stabilimenti_view v
left join linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita = v.id 
where 1=1 
and ((_riferimentoId>0 and v.alt_id = _riferimentoId and v.enabled) or (_riferimentoId=-1))
and ((_idcu>0 and (lcu.id_controllo_ufficiale=_idcu and lcu.trashed_date is null)) or (_idcu=-1))

LOOP
RETURN NEXT;
END LOOP;
END IF;


IF (_riferimentoIdNomeTab = 'suap_ric_scia_stabilimento') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, 
trashed_date, macroarea , aggregazione , attivita , codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id , v.alt_id , v.id_rel_ateco_attivita , -1 , null, v.primario, null, -1, null, -1, null, v.macroarea , null , null , v.codice,
v.categoria , v.linea_attivita , v.codice_istat , null, -1, v.enabled
from suap_ric_scia_linee_attivita_stabilimenti_view v
left join linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita = v.id 
where 1=1 
and ((_riferimentoId>0 and v.alt_id = _riferimentoId and v.enabled) or (_riferimentoId=-1))
and ((_idcu>0 and (lcu.id_controllo_ufficiale=_idcu and lcu.trashed_date is null)) or (_idcu=-1))


LOOP
RETURN NEXT;
END LOOP;
END IF;


IF (_riferimentoIdNomeTab = 'apicoltura_imprese') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, 
trashed_date, macroarea , aggregazione , attivita ,codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in

select  distinct
v.id_linea , v.riferimento_id , -1 , -1 , null, false, null, -1, null, -1, null, v.macroarea , null , null , 
concat_ws('-',v.codice_macroarea,v.codice_aggregazione, v.codice_attivita), v.aggregazione as categoria , v.attivita , -1 , null, -1, true
from  ricerche_anagrafiche_old_materializzata  v
join ticket t on (t.id_apiario = v.riferimento_id and v.riferimento_id_nome_tab = 'apicoltura_imprese')
where 1=1
and ((_riferimentoId>0 and v.riferimento_id = _riferimentoId) or (_riferimentoId=-1))
and ((_idcu>0 and t.ticketid=_idcu and t.trashed_date is null) or (_idcu=-1))


LOOP
RETURN NEXT;
END LOOP;
END IF;

IF (_riferimentoIdNomeTab = 'anagrafica.stabilimenti') THEN
FOR 
id , riferimento_id , id_rel_ateco_attivita , id_attivita_masterlist , mappato, primario, entered, entered_by, modified, modified_by, trashed_date, 
macroarea , aggregazione , attivita , codice_linea, categoria , linea_attivita , codice_istat , descrizione_codice_istat, id_attivita, enabled
in
select  distinct
v.id , v.alt_id , -1 , -1 , null, false, null, -1, null, -1, null, v.macroarea , null , null , 'OPR-OPR-X', v.categoria , v.linea_attivita , -1 , null, -1, true
from  anagrafica.linee_attivita_stabilimenti_view  v
left join linee_attivita_controlli_ufficiali lcu on lcu.id_linea_attivita = v.id 
where 1=1 
and ((_riferimentoId>0 and v.alt_id = _riferimentoId) or (_riferimentoId=-1))
and ((_idcu>0 and lcu.id_controllo_ufficiale=_idcu and lcu.trashed_date is null ) or (_idcu=-1))

LOOP
RETURN NEXT;
END LOOP;
END IF;

     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_linee_attivita(integer, text, boolean, integer)
  OWNER TO postgres;

 
 
 -- Function: public.dbi_get_controlli_ufficiali_su_linee_produttive(integer)

-- DROP FUNCTION public.dbi_get_controlli_ufficiali_su_linee_produttive(integer);

CREATE OR REPLACE FUNCTION public.dbi_get_controlli_ufficiali_su_linee_produttive(IN idstabilimento integer)
  RETURNS TABLE(idcontrollo integer, id_stabilimento integer, id_rel_stab_lp_out integer, id_linea_master_list_out integer, enabled_out boolean, descrizione_out text, id_norma_out integer, id_stato_out integer) AS
$BODY$
DECLARE
	
	 	
BEGIN
		FOR
		idcontrollo,id_stabilimento,id_rel_stab_lp_out ,id_linea_master_list_out ,enabled_out ,descrizione_out, id_norma_out, id_stato_out 
		in

		select distinct cu.ticketid,cu.id_stabilimento,r.id as id_rel_stab_lp ,r.id_linea_produttiva as id_linea_master_list,r.enabled,path_descrizione, ll.id_norma, r.stato

from opu_relazione_stabilimento_linee_produttive r 
left join linee_attivita_controlli_ufficiali on  r.id = linee_attivita_controlli_ufficiali.id_linea_attivita 
left join ticket cu on cu.ticketid = linee_attivita_controlli_ufficiali.id_controllo_ufficiale and cu.id_stabilimento = r.id_stabilimento and cu.trashed_date is null
left join ml8_linee_attivita_nuove ll on ll.id_nuova_linea_attivita=r.id_linea_produttiva
where r.id_stabilimento =idstabilimento and r.enabled and linee_attivita_controlli_ufficiali.trashed_date is null
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.dbi_get_controlli_ufficiali_su_linee_produttive(integer)
  OWNER TO postgres;

-- View: public.suap_ric_scia_linee_attivita_stabilimenti_view

-- DROP VIEW public.suap_ric_scia_linee_attivita_stabilimenti_view;

CREATE OR REPLACE VIEW public.suap_ric_scia_linee_attivita_stabilimenti_view AS 
 SELECT stab.alt_id,
    v1.id_linea_produttiva AS id_rel_ateco_attivita,
    v1.primario,
    v2.aggregazione AS categoria,
    v2.attivita AS linea_attivita,
    v2.codice_attivita AS codice_istat,
    v2.codice,
    v1.id,
    v1.enabled,
    v2.macroarea
   FROM suap_ric_scia_relazione_stabilimento_linee_produttive v1
     JOIN ml8_linee_attivita_nuove_materializzata v2 ON v1.id_linea_produttiva = v2.id_nuova_linea_attivita
     LEFT JOIN suap_ric_scia_stabilimento stab ON stab.id = v1.id_stabilimento;

ALTER TABLE public.suap_ric_scia_linee_attivita_stabilimenti_view
  OWNER TO postgres;

  -- Function: public_functions.insert_linee_attivita_controlli_ufficiali(integer, integer, character varying, text, integer, integer)

-- DROP FUNCTION public_functions.insert_linee_attivita_controlli_ufficiali(integer, integer, character varying, text, integer, integer);
CREATE OR REPLACE FUNCTION public_functions.insert_linee_attivita_controlli_ufficiali(
    _id_controllo_ufficiale integer,
    _id_linea_attivita integer,
    _riferimento_nome_tab character varying,
    _note text DEFAULT NULL::text,
    _id_linea_attivita_old integer DEFAULT NULL::integer,
    _id_vecchio_tipo_operatore integer DEFAULT NULL::integer)
  RETURNS text AS
$BODY$
  DECLARE
     _codice_linea character varying;
  BEGIN

        update linee_attivita_controlli_ufficiali set trashed_date = now() where id_controllo_ufficiale = _id_controllo_ufficiale;
	-- recupero codice linea
        _codice_linea := (
           select codice from opu_relazione_stabilimento_linee_produttive rel 
           join ml8_linee_attivita_nuove_materializzata ml 
           on ml.id_nuova_linea_attivita = rel.id_linea_produttiva 
           where rel.id = _id_linea_attivita and _riferimento_nome_tab = 'opu_relazione_stabilimento_linee_produttive'
           union
           select distinct codice from org_linee_attivita_view  
	   where (id = _id_linea_attivita and ( _id_vecchio_tipo_operatore is null or tipo = _id_vecchio_tipo_operatore)) or
	   _id_linea_attivita = -1 and tipo = _id_vecchio_tipo_operatore 
	   union
           select codice from suap_ric_scia_linee_attivita_stabilimenti_view where id = _id_linea_attivita and _riferimento_nome_tab = 'suap_ric_scia_relazione_stabilimento_linee_produttive'
           union   
           select codice from sintesis_linee_attivita_stabilimenti_view where id = _id_linea_attivita and _riferimento_nome_tab = 'sintesis_relazione_stabilimento_linee_produttive'
           union
           select 'OPR-OPR-X' from anagrafica.linee_attivita_stabilimenti_view where id = _id_linea_attivita and _riferimento_nome_tab = 'anagrafica.rel_stabilimenti_linee_attivita'
           
	);

	insert into linee_attivita_controlli_ufficiali(id_controllo_ufficiale, id_linea_attivita, riferimento_nome_tab, codice_linea, note, id_linea_attivita_old, id_vecchio_tipo_operatore)
	values(_id_controllo_ufficiale,_id_linea_attivita,_riferimento_nome_tab, _codice_linea, _note, _id_linea_attivita_old, _id_vecchio_tipo_operatore);
	return 'OK';
  END;
 $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.insert_linee_attivita_controlli_ufficiali(integer, integer, character varying, text, integer, integer)
  OWNER TO postgres;

-- Function: preaccettazionesigla.dbi_get_campioni_da_stabilimento(integer, text, text, timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION preaccettazionesigla.dbi_get_campioni_da_stabilimento(integer, text, text, timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION preaccettazionesigla.dbi_get_campioni_da_stabilimento(
    IN _riferimento_id integer,
    IN _riferimento_id_nome text,
    IN _riferimento_id_nome_tab text,
    IN _data_inizio timestamp without time zone DEFAULT NULL::timestamp without time zone,
    IN _data_fine timestamp without time zone DEFAULT NULL::timestamp without time zone)
  RETURNS TABLE(id_cu character varying, motivo character varying, data_operazione text, attivita text) AS
$BODY$
DECLARE

_prefix text := $$
	
	select distinct tcmp.id_controllo_ufficiale,
	coalesce(lpm.description, lti.description)  AS motivo,
	to_char(tcmp.assigned_date, 'YYYY-MM-DD'),
	ram.attivita
	from  ricerche_anagrafiche_old_materializzata ram
		join preaccettazionesigla.vw_ticket_cmp  tcmp on tcmp.%s = ram.riferimento_id 
								and ram.riferimento_id_nome_tab = '%s' 
								and ram.riferimento_id_nome = '%s'	
								and ram.riferimento_id = %s
$$;

_join_tab_linee text := $$
		left join linee_attivita_controlli_ufficiali lacu on lacu.id_controllo_ufficiale::character varying = tcmp.id_controllo_ufficiale 
					and lacu.id_linea_attivita = ram.id_linea 
					and lacu.trashed_date is null
$$;

_join_tab text := $$	
		left join lookup_tipo_ispezione lti on lti.code = tcmp.motivazione_campione 
		left join lookup_piano_monitoraggio lpm on lpm.code = tcmp.motivazione_piano_campione
$$;

_suffix text := $$
	where (tcmp.assigned_date  BETWEEN '%s' AND '%s') 
	       and (ram.riferimento_id = ram.id_linea or lacu.id_linea_attivita is not null )
	order by to_char(tcmp.assigned_date, 'YYYY-MM-DD') desc
$$;

_query text;
_query_final text;

BEGIN

IF _riferimento_id_nome_tab = 'organization' THEN

	_query := concat(_prefix, _join_tab_linee, _join_tab, _suffix);
	_query_final := format(_query, 'org_id', _riferimento_id_nome_tab, _riferimento_id_nome, _riferimento_id, _data_inizio, _data_fine);

	
END IF;

IF _riferimento_id_nome_tab = 'opu_stabilimento' THEN

	_query := concat(_prefix, _join_tab_linee, _join_tab, _suffix);
	_query_final := format(_query,'id_stabilimento', _riferimento_id_nome_tab, _riferimento_id_nome, _riferimento_id, _data_inizio, _data_fine);

		
END IF;

IF _riferimento_id_nome_tab = 'apicoltura_imprese' THEN
	
	_query := concat(_prefix, _join_tab, _suffix);
	_query_final := format(_query, 'id_apiario', _riferimento_id_nome_tab, _riferimento_id_nome, _riferimento_id, _data_inizio, _data_fine);

 
END IF;

IF _riferimento_id_nome_tab = 'stabilimenti' THEN

	_query := concat(_prefix, _join_tab_linee, _join_tab, _suffix);
	_query_final := format(_query, 'alt_id', _riferimento_id_nome_tab, _riferimento_id_nome, _riferimento_id, _data_inizio, _data_fine);

	
END IF;

IF _riferimento_id_nome_tab = 'sintesis_stabilimento' THEN

	_query := concat(_prefix, _join_tab_linee, _join_tab, _suffix);
	_query_final := format(_query, 'alt_id', _riferimento_id_nome_tab, _riferimento_id_nome, _riferimento_id, _data_inizio, _data_fine);
		
END IF;

IF _riferimento_id_nome_tab = 'suap_ric_scia_stabilimento' THEN

	_query := concat(_prefix, _join_tab_linee, _join_tab, _suffix);
	_query_final := format(_query, 'alt_id', _riferimento_id_nome_tab, _riferimento_id_nome, _data_inizio, _data_fine);
		
END IF;

raise WARNING 'dbi: %', _query_final;
return query
		execute _query_final;


END;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION preaccettazionesigla.dbi_get_campioni_da_stabilimento(integer, text, text, timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

  -- Function: preaccettazionesigla.dbi_get_controlli_ufficiali_da_stabilimento(integer, text, text, timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION preaccettazionesigla.dbi_get_controlli_ufficiali_da_stabilimento(integer, text, text, timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION preaccettazionesigla.dbi_get_controlli_ufficiali_da_stabilimento(
    IN _riferimento_id integer,
    IN _riferimento_id_nome text,
    IN _riferimento_id_nome_tab text,
    IN _data_inizio timestamp without time zone DEFAULT NULL::timestamp without time zone,
    IN _data_fine timestamp without time zone DEFAULT NULL::timestamp without time zone)
  RETURNS TABLE(id_cu character varying, motivo character varying, data_operazione text, attivita text) AS
$BODY$
DECLARE

_prefix text := $$
	
	select distinct tcu.id_controllo_ufficiale,
	coalesce(lpm.description, lti.description)  AS motivo,
	to_char(tcu.assigned_date, 'YYYY-MM-DD'),
	ram.attivita
	from  ricerche_anagrafiche_old_materializzata ram
		join preaccettazionesigla.vw_ticket_cu  tcu on tcu.%s = ram.riferimento_id 
								and ram.riferimento_id_nome_tab = '%s' 
								and ram.riferimento_id_nome = '%s'	
								and ram.riferimento_id = %s	
$$;

_join_tab_linee text := $$
		left join linee_attivita_controlli_ufficiali lacu on lacu.id_controllo_ufficiale = tcu.ticketid 
					and lacu.id_linea_attivita = ram.id_linea
					and lacu.trashed_date is null
$$;

_join_tab text := $$
		join tipocontrolloufficialeimprese tcui on tcui.idcontrollo::character varying = tcu.id_controllo_ufficiale
		left join lookup_tipo_ispezione lti on lti.code = tcui.tipoispezione 
		left join lookup_piano_monitoraggio lpm on lpm.code = tcui.pianomonitoraggio
$$;

_suffix text := $$
	where (tcu.assigned_date  BETWEEN '%s' AND '%s') 
		and (tcui.tipoispezione <> -1 or tcui.pianomonitoraggio <> -1)
		and tcu.provvedimenti_prescrittivi = 4
		and (ram.riferimento_id = ram.id_linea or lacu.id_linea_attivita is not null ) 
	order by to_char(tcu.assigned_date, 'YYYY-MM-DD') desc 
$$;

_query text;
_query_final text;

BEGIN

IF _riferimento_id_nome_tab = 'organization' THEN

	_query := concat(_prefix, _join_tab_linee, _join_tab, _suffix);
	_query_final := format(_query, 'org_id', _riferimento_id_nome_tab, _riferimento_id_nome, _riferimento_id, _data_inizio, _data_fine);

	
END IF;

IF _riferimento_id_nome_tab = 'opu_stabilimento' THEN

	_query := concat(_prefix, _join_tab_linee, _join_tab, _suffix);
	_query_final := format(_query,'id_stabilimento', _riferimento_id_nome_tab, _riferimento_id_nome, _riferimento_id, _data_inizio, _data_fine);

		
END IF;

IF _riferimento_id_nome_tab = 'apicoltura_imprese' THEN
	
	_query := concat(_prefix, _join_tab, _suffix);
	_query_final := format(_query, 'id_apiario', _riferimento_id_nome_tab, _riferimento_id_nome, _riferimento_id, _data_inizio, _data_fine);

 
END IF;

IF _riferimento_id_nome_tab = 'stabilimenti' THEN

	_query := concat(_prefix, _join_tab_linee, _join_tab, _suffix);
	_query_final := format(_query, 'alt_id', _riferimento_id_nome_tab, _riferimento_id_nome, _riferimento_id, _data_inizio, _data_fine);

	
END IF;

IF _riferimento_id_nome_tab = 'sintesis_stabilimento' THEN

	_query := concat(_prefix, _join_tab_linee, _join_tab, _suffix);
	_query_final := format(_query, 'alt_id', _riferimento_id_nome_tab, _riferimento_id_nome, _riferimento_id, _data_inizio, _data_fine);
		
END IF;

IF _riferimento_id_nome_tab = 'suap_ric_scia_stabilimento' THEN

	_query := concat(_prefix, _join_tab_linee, _join_tab, _suffix);
	_query_final := format(_query, 'alt_id', _riferimento_id_nome_tab, _riferimento_id_nome, _riferimento_id,_data_inizio, _data_fine);
		
END IF;

raise WARNING 'dbi: %', _query_final;
return query
		execute _query_final;


END;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION preaccettazionesigla.dbi_get_controlli_ufficiali_da_stabilimento(integer, text, text, timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

  
  -- View: public.linee_attivita_controlli_ufficiali_view

-- DROP VIEW public.linee_attivita_controlli_ufficiali_view;

CREATE OR REPLACE VIEW public.linee_attivita_controlli_ufficiali_view AS 
 SELECT l.id_controllo_ufficiale,
    l.id_linea_attivita,
    o.org_id,
    o.tipologia AS tipo_operatore,
    l.trashed_date 
   FROM linee_attivita_controlli_ufficiali l
     LEFT JOIN ticket t ON t.ticketid = l.id_controllo_ufficiale AND t.tipologia = 3
     LEFT JOIN organization o ON o.org_id = t.org_id
  WHERE t.trashed_date IS NULL AND o.trashed_date IS NULL AND l.trashed_date IS NULL;

ALTER TABLE public.linee_attivita_controlli_ufficiali_view
  OWNER TO postgres;
GRANT ALL ON TABLE public.linee_attivita_controlli_ufficiali_view TO postgres;

  