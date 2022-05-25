-- Chi: Bartolo Sansone
-- Quando: 11/12/2018
-- Cosa: Gestione nuovo atto B26


-- Inserimento nuova checklist
insert into lookup_chk_bns_mod (code, description, enabled, codice_specie, nuova_gestione, versione, start_date, end_date, num_allegato)
select 23, 'CONDIZIONALITA CGO e CGO 9', enabled, codice_specie, nuova_gestione, 4, '2018-01-01', end_date, num_allegato from lookup_chk_bns_mod where code = 22;

-- Scadenza vecchia checklist
update lookup_chk_bns_mod set end_date = '2017-12-31' where code = 22;

-- Allineamento sequence tabella capitoli
select max(code) from chk_bns_cap;
--Risultato in (+1):
alter sequence chk_bns_cap_code_seq restart with --254;

-- Inserimento nuovi capitoli
insert into chk_bns_cap
(description, level, enabled, idmod, classe_nc_ws, tipo_capitolo)
select cap.description, cap.level,cap.enabled, 23,classe_nc_ws,tipo_capitolo from chk_bns_cap cap  left join lookup_chk_bns_mod la on la.code = cap.idmod where cap.enabled and la.codice_specie = -1 AND la.versione = 3 order by cap.code

-- Inserimento nuove domande
insert into chk_bns_dom (description, level, enabled, idcap, tipo_domanda, classe_ws)
select dom.description, dom.level, dom.enabled, (select code from chk_bns_cap where description = (select description from chk_bns_cap where code = dom.idcap) and idmod = 23), dom.tipo_domanda, dom.classe_ws from chk_bns_dom dom  where dom.idcap in (select cap.code from chk_bns_cap cap  left join lookup_chk_bns_mod la on la.code = cap.idmod where cap.enabled and la.codice_specie = -1 AND la.versione = 3 order by code)

-- Check domande
select * from chk_bns_dom where idcap in (select cap.code from chk_bns_cap cap  left join lookup_chk_bns_mod la on la.code = cap.idmod where cap.enabled and la.codice_specie = -1 AND la.versione = 4 order by code) order by idcap asc, classe_ws;

--assicurarsi che l'ultimo capitolo sia 258
insert into chk_bns_dom (description, level, enabled, idcap) values ('Non vi sono evidenze che non siano rispettate le misure di prevenzione di cui al DM 25 novembre 2015.', 44, true, 258);
insert into chk_bns_dom (description, level, enabled, idcap) values ('Non sono state rilevate non conformita'' nei requisiti previsti dall''art. 15, comma 1 e comma 2 del Reg. CE 999/2001 e ss. mm.e ii.', 45, true, 258);
 
--Aggiornamento classe_ws
update chk_bns_dom set classe_ws = level+78 where idcap in (select cap.code from chk_bns_cap cap  left join lookup_chk_bns_mod la on la.code = cap.idmod where cap.enabled and la.codice_specie = -1 AND la.versione = 4 order by code);

--Svuoto classe_ws per nocheck
update chk_bns_dom set classe_ws = null where idcap in (select cap.code from chk_bns_cap cap  left join lookup_chk_bns_mod la on la.code = cap.idmod where cap.enabled and la.codice_specie = -1 AND la.versione = 4 order by code) and tipo_domanda = 'nocheck' ;

--Aggiornamento funzione dettaglio b11

-- Function: public.get_dettaglio_non_conformita_b11(integer)

-- DROP FUNCTION public.get_dettaglio_non_conformita_b11(integer);

CREATE OR REPLACE FUNCTION public.get_dettaglio_non_conformita_b11(IN idcontrollo integer)
  RETURNS TABLE(sairrdettid integer, esito text) AS
$BODY$
	
BEGIN
	FOR sairrdettid, esito
	in
	

	select r.classe_ws,case when (r.esito = false and r.domanda_non_pertinente <>true) then 'N'
	when r.esito is null then '-'  else 'S' end
	from 
chk_bns_mod_ist i  join
chk_bns_risposte r on r.idmodist = i.id 
 join ticket t on t.ticketid = i.idcu and t.tipologia = 3
where 
 i.id_alleg in (20,23)
and t.trashed_date is null
and i.trashed_date is null
and r.tipo_domanda <> 'nocheck'
and idcu = idcontrollo

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_dettaglio_non_conformita_b11(integer)
  OWNER TO postgres;


-- Creazione tabella

-- Table: public.chk_bns_mod_ist_cgo_2018

-- DROP TABLE public.chk_bns_mod_ist_cgo_2018,

CREATE TABLE public.chk_bns_mod_ist_cgo_2018
(
  id serial,
  id_chk_bns_mod_ist integer,
  appartenenteCondizionalita boolean,
criteriUtilizzati text,
altroCriterioDescrizione text,
	
 numeroCapiPresenti integer,
	  numeroCapiEffettivamentePresenti integer,
	  numeroCapiControllati integer,
	
	  puntoNote text,
	  esitoControllo boolean,
	  intenzionalita boolean,
	  esitoControlloTse boolean,
	  intenzionalitaTse boolean,
	  riscontroNonConformita boolean,
	
	  evidenzeBenessere boolean,
	  evidenzeIdentificazione boolean,
	  evidenzeSostanze boolean,
	  prescrizioniSicurezza boolean,
	
	  prescrizioniSicurezzaNote text,
	  prescrizioniSicurezzaData  text,
	
	
	
	  prescrizioniTse boolean,
	  prescrizioniTseNote text,
	  prescrizioniTseData text,
	  bloccoMovimentazioni text,
	  amministrativaPecuniaria text,
	  abbattimentoCapi text,
	  sequestroCapi text,
	  altroCapi text,
          altroSpecificareDesc text,

	  noteControllore text,
	  noteDetentore text,
	
	  dataPrimoControlloLoco text,
	  nomeResponsabilePrimoControlloLoco text,
	  nomeControllorePrimoControlloLoco text,
	
	  prescrizioniEseguiteSicurezza boolean,
	  dataVerificaLoco text,
	  nomeResponsabileVerificaLoco text,
	  nomeControlloreVerificaLoco text,
	  prescrizioniEseguiteTse boolean,
	  dataVerificaLocoTse text,
	  nomeResponsabileVerificaLocoTse text,
	  nomeControlloreVerificaLocoTse text,
	  dataChiusura text,

  
  CONSTRAINT chk_bns_mod_ist_cgo_2018_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);


-- View: public.bdn_cu_chiusi_sicurezza_alimentare_b11

-- DROP VIEW public.bdn_cu_chiusi_sicurezza_alimentare_b11;

CREATE OR REPLACE VIEW public.bdn_cu_chiusi_sicurezza_alimentare_b11 AS 
 SELECT DISTINCT ticket.id_bdn,
    ticket.id_bdn_b11,
    o.name AS ragione_sociale,
    o.org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R202'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R204'::text
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
    '-1'::integer AS id_stabilimento,
    cgo2018.nomecontrolloreprimocontrolloloco as primo_controllore,
'SA' as tipo_controllo,
cgo2018.bloccomovimentazioni as sanz_blocco_mov,
cgo2018.amministrativapecuniaria as sanz_amministrativa,
cgo2018.sequestrocapi as sanz_sequestro,
cgo2018.altrocapi as sanz_altro,
cgo2018.altrospecificaredesc as sanz_altro_desc,
cgo2018.abbattimentocapi as sanz_abbattimento_capi,
cgo2018.notecontrollore as note_controllore,
cgo2018.notedetentore as note_detentore,
case when cgo2018.riscontrononconformita then 'S' else 'N' end as flag_evidenze,
case when cgo2018.evidenzeidentificazione then 'S' else 'N' end as evidenza_ir,
case when cgo2018.evidenzebenessere then 'S' else 'N' end as evidenza_ba,
case when cgo2018.evidenzesostanze then 'S' else 'N' end as evidenza_sv,
cgo2018.datachiusura as data_chiusura,
case when cgo2018.appartenentecondizionalita then 'S' else 'N' end as flag_condizionalita,
cgo2018.criteriutilizzati as crit_codice,
cgo2018.altrocriteriodescrizione as crit_controllo_altro,
cgo2018.numerocapieffettivamentepresenti as num_capi_presenti,
cgo2018.numerocapicontrollati as num_capi_controllati,
cgo2018.nomecontrolloreprimocontrolloloco as nome_rappresentante,
case when cgo2018.intenzionalita then 'S' else 'N' end as flag_intenzionalita_sa,
case when cgo2018.intenzionalitatse then 'S' else 'N' end as flag_intenzionalita_tse,
cgo2018.prescrizionisicurezzanote as prescrizioni,
cgo2018.prescrizionisicurezzadata as data_scad_prescrizioni,
cgo2018.prescrizionitsenote as prescrizioni2,
cgo2018.prescrizionitsedata as data_scad_prescrizioni2,
case when cgo2018.prescrizionieseguitesicurezza then 'S' else 'N' end as flag_prescrizione_esito_sa,
cgo2018.dataverificaloco as data_verifica_sa,
cgo2018.nomecontrolloreverificaloco as secondo_controllore,
cgo2018.nomeresponsabileverificaloco as nome_rappresentante_ver,
case when cgo2018.prescrizionieseguitetse then 'S' else 'N' end as flag_prescrizione_esito_sa2,
cgo2018.dataverificalocotse as data_verifica_sa2,
cgo2018.nomecontrolloreverificalocotse as secondo_controllore2,
cgo2018.nomeresponsabileverificalocotse as nome_rappresentante_ver2,
cgo2018.puntonote as punto_note

   FROM ticket
     JOIN organization o ON ticket.org_id = o.org_id
     LEFT JOIN organization_address oa ON oa.org_id = o.org_id AND oa.address_type = 1
     LEFT JOIN nucleo_ispettivo_mv nucleo ON nucleo.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = o.specie_allevamento
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     LEFT JOIN lookup_piano_monitoraggio lpiani ON lpiani.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = o.asl_old
     LEFT JOIN orientamenti_produttivi op ON op.specie_allevata = o.specie_allev::text AND op.descrizione_tipo_produzione = o.tipologia_strutt AND o.orientamento_prod = op.descrizione_codice_orientamento
     LEFT JOIN lookup_specie_allevata specie ON specie.description::text = o.specie_allev::text
     left join chk_bns_mod_ist_cgo_2018 cgo2018 on cgo2018.id_chk_bns_mod_ist = ist.id
  WHERE ticket.tipologia = 3 AND (ticket.closed IS NOT NULL OR ticket.closed_nolista) AND o.tipologia = 2 AND lpiani.codice_interno = 1483 AND (ticket.esito_import_b11 !~~* 'OK'::text OR ticket.esito_import_b11 IS NULL) AND ticket.trashed_date IS NULL AND o.trashed_date IS NULL AND ist.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) >= 2016::double precision AND ((( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 OR lcm.code IS NULL OR (lcm.code = ANY (ARRAY[20, 22, 23])))
  GROUP BY ticket.id_bdn, ticket.id_bdn_b11, o.name, o.org_id, ticket.ticketid, ticket.assigned_date, nucleo.nucleo_ispettivo, nucleo.nucleo_ispettivo_due, nucleo.nucleo_ispettivo_tre, nucleo.nucleo_ispettivo_quattro, nucleo.nucleo_ispettivo_cinque, nucleo.nucleo_ispettivo_sei, nucleo.nucleo_ispettivo_sette, nucleo.nucleo_ispettivo_otto, nucleo.nucleo_ispettivo_nove, nucleo.nucleo_ispettivo_dieci, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R202'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R204'::text
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
        END), lcm.code, o.specie_allev, o.codice_tipo_allevamento, cgo2018.nomecontrolloreprimocontrolloloco, cgo2018.bloccomovimentazioni,cgo2018.amministrativapecuniaria,cgo2018.sequestrocapi,cgo2018.altrocapi,cgo2018.altrospecificaredesc,cgo2018.abbattimentocapi,cgo2018.notecontrollore,cgo2018.notedetentore,cgo2018.riscontrononconformita,cgo2018.evidenzeidentificazione,cgo2018.evidenzebenessere,cgo2018.evidenzesostanze,cgo2018.datachiusura,cgo2018.appartenentecondizionalita,cgo2018.criteriutilizzati,cgo2018.altrocriteriodescrizione,cgo2018.numerocapieffettivamentepresenti,cgo2018.numerocapicontrollati,cgo2018.intenzionalita,cgo2018.intenzionalitatse,cgo2018.prescrizionisicurezzanote,cgo2018.prescrizionisicurezzadata,cgo2018.prescrizionitsenote,cgo2018.prescrizionitsedata,cgo2018.prescrizionieseguitesicurezza,cgo2018.dataverificaloco,cgo2018.nomecontrolloreverificaloco,cgo2018.nomeresponsabileverificaloco,cgo2018.prescrizionieseguitetse,cgo2018.dataverificalocotse,cgo2018.nomecontrolloreverificalocotse,cgo2018.nomeresponsabileverificalocotse, cgo2018.puntonote
UNION
 SELECT DISTINCT ticket.id_bdn,
    ticket.id_bdn_b11,
    o.ragione_sociale,
    '-1'::integer AS org_id,
    ticket.ticketid AS id_controllo,
    ticket.assigned_date AS data_controllo,
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R202'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R204'::text
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
    s.id AS id_stabilimento,
    cgo2018.nomecontrolloreprimocontrolloloco as primo_controllore,
'SA' as tipo_controllo,
cgo2018.bloccomovimentazioni as sanz_blocco_mov,
cgo2018.amministrativapecuniaria as sanz_amministrativa,
cgo2018.sequestrocapi as sanz_sequestro,
cgo2018.altrocapi as sanz_altro,
cgo2018.altrospecificaredesc as sanz_altro_desc,
cgo2018.abbattimentocapi as sanz_abbattimento_capi,
cgo2018.notecontrollore as note_controllore,
cgo2018.notedetentore as note_detentore,
case when cgo2018.riscontrononconformita then 'S' else 'N' end as flag_evidenze,
case when cgo2018.evidenzeidentificazione then 'S' else 'N' end as evidenza_ir,
case when cgo2018.evidenzebenessere then 'S' else 'N' end as evidenza_ba,
case when cgo2018.evidenzesostanze then 'S' else 'N' end as evidenza_sv,
cgo2018.datachiusura as data_chiusura,
case when cgo2018.appartenentecondizionalita then 'S' else 'N' end as flag_condizionalita,
cgo2018.criteriutilizzati as crit_codice,
cgo2018.altrocriteriodescrizione as crit_controllo_altro,
cgo2018.numerocapieffettivamentepresenti as num_capi_presenti,
cgo2018.numerocapicontrollati as num_capi_controllati,
cgo2018.nomecontrolloreprimocontrolloloco as nome_rappresentante,
case when cgo2018.intenzionalita then 'S' else 'N' end as flag_intenzionalita_sa,
case when cgo2018.intenzionalitatse then 'S' else 'N' end as flag_intenzionalita_tse,
cgo2018.prescrizionisicurezzanote as prescrizioni,
cgo2018.prescrizionisicurezzadata as data_scad_prescrizioni,
cgo2018.prescrizionitsenote as prescrizioni2,
cgo2018.prescrizionitsedata as data_scad_prescrizioni2,
case when cgo2018.prescrizionieseguitesicurezza then 'S' else 'N' end as flag_prescrizione_esito_sa,
cgo2018.dataverificaloco as data_verifica_sa,
cgo2018.nomecontrolloreverificaloco as secondo_controllore,
cgo2018.nomeresponsabileverificaloco as nome_rappresentante_ver,
case when cgo2018.prescrizionieseguitetse then 'S' else 'N' end as flag_prescrizione_esito_sa2,
cgo2018.dataverificalocotse as data_verifica_sa2,
cgo2018.nomecontrolloreverificalocotse as secondo_controllore2,
cgo2018.nomeresponsabileverificalocotse as nome_rappresentante_ver2,
cgo2018.puntonote as punto_note

   FROM ticket
     JOIN opu_stabilimento s ON ticket.id_stabilimento = s.id
     LEFT JOIN opu_operatore o ON o.id = s.id_operatore
     LEFT JOIN opu_indirizzo ind ON ind.id = s.id_indirizzo
     LEFT JOIN comuni1 c ON c.id = ind.comune
     JOIN opu_linee_attivita_controlli_ufficiali olacu ON olacu.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN opu_relazione_stabilimento_linee_produttive rel ON rel.id = olacu.id_linea_attivita
     LEFT JOIN ml8_linee_attivita_nuove l ON l.id_nuova_linea_attivita = rel.id_linea_produttiva
     LEFT JOIN nucleo_ispettivo_mv nucleo ON nucleo.id_controllo_ufficiale = ticket.ticketid
     LEFT JOIN lookup_qualifiche_nucleo_old_view qual ON qual.code = ticket.nucleo_ispettivo
     LEFT JOIN chk_bns_mod_ist ist ON ist.idcu = ticket.ticketid
     LEFT JOIN lookup_chk_bns_mod lcm ON lcm.code = ist.id_alleg
     LEFT JOIN chk_bns_risposte risp ON risp.idmodist = ist.id AND lcm.codice_specie = l.decodifica_specie_bdn::integer
     JOIN tipocontrolloufficialeimprese tipi ON ticket.ticketid = tipi.idcontrollo AND tipi.enabled
     LEFT JOIN lookup_piano_monitoraggio lpiani ON lpiani.code = tipi.pianomonitoraggio
     LEFT JOIN lookup_site_id asl ON asl.code = s.id_asl
     LEFT JOIN lookup_specie_allevata lsa ON lsa.short_description::text = l.decodifica_specie_bdn
     left join chk_bns_mod_ist_cgo_2018 cgo2018 on cgo2018.id_chk_bns_mod_ist = ist.id
  WHERE ticket.tipologia = 3 AND (ticket.closed IS NOT NULL OR ticket.closed_nolista) AND lpiani.codice_interno = 1483 AND (ticket.esito_import_b11 !~~* 'OK'::text OR ticket.esito_import_b11 IS NULL) AND ticket.trashed_date IS NULL AND o.trashed_date IS NULL AND s.trashed_date IS NULL AND ist.trashed_date IS NULL AND date_part('year'::text, ticket.assigned_date) >= 2016::double precision AND ((( SELECT get_non_conformita_b11(ticket.ticketid) AS get_non_conformita_b11)) = 0 OR lcm.code IS NULL OR lcm.code in(20, 22, 23))
  GROUP BY ticket.id_bdn, ticket.id_bdn_b11, o.ragione_sociale, s.id, ticket.ticketid, ticket.assigned_date, nucleo.nucleo_ispettivo, nucleo.nucleo_ispettivo_due, nucleo.nucleo_ispettivo_tre, nucleo.nucleo_ispettivo_quattro, nucleo.nucleo_ispettivo_cinque, nucleo.nucleo_ispettivo_sei, nucleo.nucleo_ispettivo_sette, nucleo.nucleo_ispettivo_otto, nucleo.nucleo_ispettivo_nove, nucleo.nucleo_ispettivo_dieci, (
        CASE
            WHEN ticket.site_id = 201 THEN 'R201'::text
            WHEN ticket.site_id = 202 THEN 'R202'::text
            WHEN ticket.site_id = 203 THEN 'R203'::text
            WHEN ticket.site_id = 204 THEN 'R204'::text
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
        END), lcm.code, cgo2018.nomecontrolloreprimocontrolloloco, cgo2018.bloccomovimentazioni,cgo2018.amministrativapecuniaria,cgo2018.sequestrocapi,cgo2018.altrocapi,cgo2018.altrospecificaredesc,cgo2018.abbattimentocapi,cgo2018.notecontrollore,cgo2018.notedetentore,cgo2018.riscontrononconformita,cgo2018.evidenzeidentificazione,cgo2018.evidenzebenessere,cgo2018.evidenzesostanze,cgo2018.datachiusura,cgo2018.appartenentecondizionalita,cgo2018.criteriutilizzati,cgo2018.altrocriteriodescrizione,cgo2018.numerocapieffettivamentepresenti,cgo2018.numerocapicontrollati,cgo2018.intenzionalita,cgo2018.intenzionalitatse,cgo2018.prescrizionisicurezzanote,cgo2018.prescrizionisicurezzadata,cgo2018.prescrizionitsenote,cgo2018.prescrizionitsedata,cgo2018.prescrizionieseguitesicurezza,cgo2018.dataverificaloco,cgo2018.nomecontrolloreverificaloco,cgo2018.nomeresponsabileverificaloco,cgo2018.prescrizionieseguitetse,cgo2018.dataverificalocotse,cgo2018.nomecontrolloreverificalocotse,cgo2018.nomeresponsabileverificalocotse, cgo2018.puntonote;

ALTER TABLE public.bdn_cu_chiusi_sicurezza_alimentare_b11
  OWNER TO postgres;

  --Aggiornamento funzione punteggio
  
  -- Function: public.get_non_conformita_b11(integer)

-- DROP FUNCTION public.get_non_conformita_b11(integer);

CREATE OR REPLACE FUNCTION public.get_non_conformita_b11(IN idcontrollo integer)
  RETURNS TABLE(totale integer) AS
$BODY$
	
BEGIN
	FOR totale
	in

	select  count(*) from 
chk_bns_mod_ist i left join
chk_bns_risposte r on r.idmodist = i.id 
left join ticket t on t.ticketid = i.idcu and t.tipologia = 3
where domanda_non_pertinente <> true
and r.esito = false
and i.id_alleg in (15,20,22, 23)
and t.trashed_date is null
and i.trashed_date is null
and idcu = idcontrollo

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_non_conformita_b11(integer)
  OWNER TO postgres;

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  -- Simulazione Invio
  
   
  
  CREATE OR REPLACE FUNCTION public.get_requisiti_invio_b26(
    _idcu integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN
select
 string_agg('<requisiti><clsadettId>0</clsadettId><operation>insert</operation><sairrdettId>'|| r.classe_ws || '</sairrdettId><flagSN>'|| case when r.esito = true then 'S' when r.esito = false then 'N' else 'N.A.' end ||'</flagSN></requisiti>
', '') into ret
from  chk_bns_mod_ist ist
left join chk_bns_risposte r on r.idmodist = ist.id
where ist.idcu = _idcu;

 RETURN e'\n' ||ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_requisiti_invio_b26(integer)
  OWNER TO postgres;

  
  CREATE OR REPLACE FUNCTION public.get_chiamata_ws_invio_b26(
    _idcu integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN
select 
'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.izs.it"><soapenv:Header><ws:SOAPAutenticazione><password>na.izs34</password><username>izsna_006</username></ws:SOAPAutenticazione><ws:SOAPAutorizzazione><ruolo>regione</ruolo></ws:SOAPAutorizzazione></soapenv:Header>
<soapenv:Body>
<ws:insertControlliallevamentiSA>
<controlliallevamenti>
<caId>0</caId>
<dtControllo>' || COALESCE(to_char(data_controllo, 'yyyy-mm-dd'),null) || '</dtControllo>
<aslCodice>' || COALESCE(codice_asl,'') || '</aslCodice>
<distrettoCodice></distrettoCodice>
<regCodice></regCodice>              
<aziendaCodice>' || COALESCE(codice_azienda,'') || '</aziendaCodice>
<speCodice>' || COALESCE(specie_allevamento ,'') || '</speCodice>
<allevIdFiscale>' || COALESCE(id_fiscale_allevamento,'') || '</allevIdFiscale>
<ocCodice>' || COALESCE(occodice,'') || '</ocCodice>
<flagCee></flagCee>              
<flagEsito>' || COALESCE(flag_esito_sa,'') || '</flagEsito>
<flagVitelli></flagVitelli>
<flagExtrapiano></flagExtrapiano>
<detenIdFiscale>' || COALESCE(id_fiscale_detentore,'') || '</detenIdFiscale>
<tipoProdCodice>' || COALESCE(tipo_produzione,'') || '</tipoProdCodice>
<tipoAllevCodice>' || COALESCE(tipo_allevamento_codice,'') || '</tipoAllevCodice>
<orientamentoCodice>' || COALESCE(codice_orientamento,'') || '</orientamentoCodice>
<flagPreavviso>' || COALESCE(flag_preavviso,'') || '</flagPreavviso>
<dataPreavviso>' || COALESCE(to_char(data_preavviso, 'yyyy-mm-dd'),null) || '</dataPreavviso>
<primoControllore>' || COALESCE(primo_controllore,'') || '</primoControllore>
<tipoControllo>SA</tipoControllo>
<!--<progrApiario></progrApiario>-->
<flagFaseProduttiva></flagFaseProduttiva>
<flagCopiaCheckList>' || COALESCE(flag_copia_checklist,'') || '</flagCopiaCheckList>
<sanzBloccoMov>' || COALESCE(sanz_blocco_mov,'') || '</sanzBloccoMov>
<sanzAmministrativa>' || COALESCE(sanz_amministrativa,'') || '</sanzAmministrativa>
<sanzSequestro>' || COALESCE(sanz_sequestro,'') || '</sanzSequestro>
<sanzAltro>' || COALESCE(sanz_altro,'') || '</sanzAltro>
<sanzAltroDesc>' || COALESCE(sanz_altro_desc,'') || '</sanzAltroDesc>
<sanzAbbattimentoCapi>' || COALESCE(sanz_abbattimento_capi,'') || '</sanzAbbattimentoCapi>
<noteControllore>' || COALESCE(note_controllore,'') || '</noteControllore>
<noteDetentore>' || COALESCE(note_detentore,'') || '</noteDetentore>
<flagEvidenze>' || COALESCE(flag_evidenze,'') || '</flagEvidenze>
<evidenzaIr>' || COALESCE(evidenza_ir,'') || '</evidenzaIr>
<evidenzaBa>' || COALESCE(evidenza_ba,'') || '</evidenzaBa>
<evidenzaSv>' || COALESCE(evidenza_sv,'') || '</evidenzaSv>
<evidenzaSa></evidenzaSa>
<evidenzaTse></evidenzaTse>
<dataChiusura>' || COALESCE(data_chiusura,'') || '</dataChiusura>
<flagCondizionalita>' || COALESCE(flag_condizionalita,'') || '</flagCondizionalita>    
</controlliallevamenti>
<dettagliochecklist>
<clsaId>0</clsaId>
<caId></caId>
<parametro>2018</parametro>
<critCodice>' || COALESCE(crit_codice,'') || '</critCodice>
<critDescrizione></critDescrizione>
<criterioControlloAltro>' || COALESCE(crit_controllo_altro,'') || '</criterioControlloAltro>
<numCapiPresenti>' || COALESCE(num_capi_presenti::text,'') || '</numCapiPresenti>
<numCapiControllati>' || COALESCE(num_capi_controllati::text,'') || '</numCapiControllati>
<flagCopiaCheckList>' || COALESCE(flag_copia_checklist,'') || '</flagCopiaCheckList>
<nomeRappresentante>' || COALESCE(nome_rappresentante,'') || '</nomeRappresentante>
<note></note>
<flagIntenzionalitaSa>' || COALESCE(flag_intenzionalita_sa,'') || '</flagIntenzionalitaSa>
<flagIntenzionalitaTse>' || COALESCE(flag_intenzionalita_tse,'') || '</flagIntenzionalitaTse>
<prescrizioni>' || COALESCE(prescrizioni,'') || '</prescrizioni>
<dataScadPrescrizioni>' || COALESCE(data_scad_prescrizioni,'') || '</dataScadPrescrizioni>
<prescrizioni2>' || COALESCE(prescrizioni2,'') || '</prescrizioni2>
<dataScadPrescrizioni2>' || COALESCE(data_scad_prescrizioni2,'') || '</dataScadPrescrizioni2>
<flagPrescrizioneEsitoSa>' || COALESCE(flag_prescrizione_esito_sa,'') || '</flagPrescrizioneEsitoSa>
<dataVerificaSa>' || COALESCE(data_verifica_sa,'') || '</dataVerificaSa>
<secondoControllore>' || COALESCE(secondo_controllore,'') || '</secondoControllore>
<nomeRappresentanteVer>' || COALESCE(nome_rappresentante_ver,'') || '</nomeRappresentanteVer>
<flagPrescrizioneEsitoSa2>' || COALESCE(flag_prescrizione_esito_sa2,'') || '</flagPrescrizioneEsitoSa2>
<dataVerificaSa2>' || COALESCE(data_verifica_sa2,'') || '</dataVerificaSa2>
<secondoControllore2>' || COALESCE(secondo_controllore,'') || '</secondoControllore2>
<nomeRappresentanteVer2>' || COALESCE(nome_rappresentante_ver2,'') || '</nomeRappresentanteVer2>
<requisitiXml></requisitiXml>'
|| (select get_requisiti_invio_b26 from get_requisiti_invio_b26(_idcu)) ||
'</dettagliochecklist>
</ws:insertControlliallevamentiSA>
</soapenv:Body>
</soapenv:Envelope>' into ret
from bdn_cu_chiusi_sicurezza_alimentare_b11 where id_controllo = _idcu;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_invio_b26(integer)
  OWNER TO postgres;




