alter table registro_trasgressori_pago_pa add column rata_numero integer;
alter table registro_trasgressori_pago_pa add column numero_rate integer;
alter table registro_trasgressori_pago_pa add column tipo_pagamento text;

-- aggiornamento vista registro

-- View: public.registro_trasgressori_vista

-- DROP VIEW public.registro_trasgressori_vista;

CREATE OR REPLACE VIEW public.registro_trasgressori_vista AS 
 SELECT controllo.ticketid AS idcontrollo,
    date_part('year'::text, controllo.assigned_date) AS anno_controllo,
    t.ticketid AS idsanzione,
    asl.description AS asl,
        CASE
            WHEN nucleo.in_dpat THEN 'ASL '::text || asl.description::text
            ELSE nucleo.description::text
        END AS ente1,
        CASE
            WHEN nucleo2.in_dpat THEN 'ASL '::text || asl.description::text
            ELSE nucleo2.description::text
        END AS ente2,
        CASE
            WHEN nucleo3.in_dpat THEN 'ASL '::text || asl.description::text
            ELSE nucleo3.description::text
        END AS ente3,
    t.tipo_richiesta AS numeroverbale,
    t.verbale_sequestro AS numeroverbalesequestri,
        CASE
            WHEN t.sequestro_riduzione > 0 THEN 'SI '::text || t.sequestro_riduzione
            ELSE 'NO'::text
        END AS importosequestroriduzione,
    controllo.assigned_date AS dataaccertamento,
    t.trasgressore,
    t.obbligatoinsolido AS obbligato,
    t.pagamento::text AS importosanzioneridotta,
    rt.id,
    rt.id_sanzione,
    rt.id_controllo_ufficiale,
    rt.anno,
    rt.data_prot_entrata,
    rt.pv_oblato_ridotta,
    rt.fi_assegnatario,
    rt.presentati_scritti,
    rt.presentata_richiesta_riduzione,
    rt.presentata_richiesta_audizione,
    rt.argomentazioni_accolte,
    rt.importo_sanzione_ingiunta,
    rt.data_emissione,
    rt.giorni_lavorazione,
    rt.ordinanza_ingiunzione_oblata,
    rt.sentenza_favorevole,
    rt.importo_stabilito,
    rt.ordinanza_ingiunzione_sentenza,
    rt.iscrizione_ruoli_sattoriali,
    rt.note1,
    rt.note2,
    rt.data_inserimento,
    rt.data_modifica,
    rt.id_utente_modifica,
    rt.presentata_opposizione,
    rt.trashed_date,
    rt.richiesta_rateizzazione,
    rt.rata1,
    rt.rata2,
    rt.rata3,
    rt.rata4,
    rt.rata5,
    rt.rata6,
    rt.rata7,
    rt.rata8,
    rt.rata9,
    rt.rata10,
    rt.allegato_documentale,
    rt.importo_effettivamente_versato1,
    rt.importo_effettivamente_versato2,
    rt.importo_effettivamente_versato3,
    rt.importo_effettivamente_versato4,
    rt.competenza_regionale,
    rt.pratica_chiusa,
    rt.data_ultima_notifica,
    rt.data_pagamento,
    rt.pagamento_ridotto_consentito,
    rt.data_ultima_notifica_ordinanza,
    rt.data_pagamento_ordinanza,
    rt.pagamento_ridotto_consentito_ordinanza,
        CASE
            WHEN rt.id IS NOT NULL THEN true
            ELSE false
        END AS esistente,
    rt.progressivo,
    rt.num_ordinanza,
     CASE
            WHEN pa.id IS NOT NULL THEN true
            ELSE false
        END AS esistente_pagopa
   FROM ticket t
     JOIN ticket controllo ON controllo.tipologia = 3 AND t.id_controllo_ufficiale::text = controllo.id_controllo_ufficiale::text
     LEFT JOIN nucleo_ispettivo_mv mv_nucleo ON mv_nucleo.id_controllo_ufficiale = controllo.ticketid
     LEFT JOIN lookup_qualifiche nucleo ON nucleo.description::text = mv_nucleo.nucleo_ispettivo::text
     LEFT JOIN lookup_qualifiche nucleo2 ON nucleo2.description::text = mv_nucleo.nucleo_ispettivo_due::text
     LEFT JOIN lookup_qualifiche nucleo3 ON nucleo3.description::text = mv_nucleo.nucleo_ispettivo_tre::text
     LEFT JOIN lookup_site_id asl ON asl.code = controllo.site_id
     LEFT JOIN registro_trasgressori_values rt ON t.ticketid = rt.id_sanzione
     LEFT JOIN registro_trasgressori_pago_pa pa ON pa.id_trasgressione = rt.id and pa.trashed_date is null
  WHERE t.trashed_date IS NULL AND t.tipologia = 1 AND controllo.trashed_date IS NULL AND controllo.assigned_date >= '2015-03-01 00:00:00'::timestamp without time zone AND controllo.status_id <> 1 AND t.id_nonconformita > 0
UNION
 SELECT NULL::integer AS idcontrollo,
    date_part('year'::text, t.assigned_date) AS anno_controllo,
    t.ticketid AS idsanzione,
    asl.description AS asl,
    ''::text AS ente1,
    ''::text AS ente2,
    ''::text AS ente3,
    t.tipo_richiesta AS numeroverbale,
    t.verbale_sequestro AS numeroverbalesequestri,
        CASE
            WHEN t.sequestro_riduzione > 0 THEN 'SI '::text || t.sequestro_riduzione
            ELSE 'NO'::text
        END AS importosequestroriduzione,
    t.assigned_date AS dataaccertamento,
    t.trasgressore,
    t.obbligatoinsolido AS obbligato,
    t.pagamento::text AS importosanzioneridotta,
    rt.id,
    rt.id_sanzione,
    rt.id_controllo_ufficiale,
    rt.anno,
    rt.data_prot_entrata,
    rt.pv_oblato_ridotta,
    rt.fi_assegnatario,
    rt.presentati_scritti,
    rt.presentata_richiesta_riduzione,
    rt.presentata_richiesta_audizione,
    rt.argomentazioni_accolte,
    rt.importo_sanzione_ingiunta,
    rt.data_emissione,
    rt.giorni_lavorazione,
    rt.ordinanza_ingiunzione_oblata,
    rt.sentenza_favorevole,
    rt.importo_stabilito,
    rt.ordinanza_ingiunzione_sentenza,
    rt.iscrizione_ruoli_sattoriali,
    rt.note1,
    rt.note2,
    rt.data_inserimento,
    rt.data_modifica,
    rt.id_utente_modifica,
    rt.presentata_opposizione,
    rt.trashed_date,
    rt.richiesta_rateizzazione,
    rt.rata1,
    rt.rata2,
    rt.rata3,
    rt.rata4,
    rt.rata5,
    rt.rata6,
    rt.rata7,
    rt.rata8,
    rt.rata9,
    rt.rata10,
    rt.allegato_documentale,
    rt.importo_effettivamente_versato1,
    rt.importo_effettivamente_versato2,
    rt.importo_effettivamente_versato3,
    rt.importo_effettivamente_versato4,
    rt.competenza_regionale,
    rt.pratica_chiusa,
    rt.data_ultima_notifica,
    rt.data_pagamento,
    rt.pagamento_ridotto_consentito,
    rt.data_ultima_notifica_ordinanza,
    rt.data_pagamento_ordinanza,
    rt.pagamento_ridotto_consentito_ordinanza,
        CASE
            WHEN rt.id IS NOT NULL THEN true
            ELSE false
        END AS esistente,
    rt.progressivo,
    rt.num_ordinanza,
     CASE
            WHEN pa.id IS NOT NULL THEN true
            ELSE false
        END AS esistente_pagopa
   FROM ticket t
     LEFT JOIN lookup_site_id asl ON asl.code = t.site_id
     LEFT JOIN registro_trasgressori_values rt ON t.ticketid = rt.id_sanzione
     LEFT JOIN registro_trasgressori_pago_pa pa ON pa.id_trasgressione = rt.id and pa.trashed_date is null

  WHERE t.trashed_date IS NULL AND t.tipologia = 1 AND t.assigned_date >= '2015-03-01 00:00:00'::timestamp without time zone AND t.note_internal_use_only ~~* '%RECORD GENERATO AUTOMATICAMENTE PER INSERIMENTO SANZIONE FORZATA NEL REGISTRO TRASGRESSORI%'::text;

ALTER TABLE public.registro_trasgressori_vista
  OWNER TO postgres;


-- Funzione di inserimento

CREATE TABLE pagopa_identificativo_dovuti_progressivi (id serial primary key, progressivo integer, identificativo_univoco_dovuto text, entered timestamp without time zone default now());


  CREATE OR REPLACE FUNCTION public.genera_pagopa_identificativo_dovuto()
  RETURNS text AS
$BODY$
DECLARE
identificativoUnivocoDovuto text;
maxProgressivo integer;
BEGIN

SELECT COALESCE(max(progressivo), 0)+1 into maxProgressivo from pagopa_identificativo_dovuti_progressivi;
select substring(date_part('year', now())::text, 3,2)||'0401'||'01'||( select LPAD(maxProgressivo::text, 7, '0')) into identificativoUnivocoDovuto;
insert into pagopa_identificativo_dovuti_progressivi(progressivo, identificativo_univoco_dovuto) values (maxProgressivo, identificativoUnivocoDovuto);
raise info 'pagopa nuovo identificativo univoco dovuto generato: %', identificativoUnivocoDovuto;
return identificativoUnivocoDovuto;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.genera_pagopa_identificativo_dovuto()
  OWNER TO postgres;



-- Funzione di inserimento

  CREATE OR REPLACE FUNCTION public.insert_registro_trasgressori_pago_pa(_id_trasgressione integer, _id_utente integer)
  RETURNS integer AS
$BODY$
DECLARE
idInserito integer;
riferimentoId integer;
riferimentoIdNomeTab text; 
idControllo integer;
idSanzione integer;
tipoIdentificativoUnivoco text;
codiceIdentificativoUnivoco text;
anagraficaPagatore text;
indirizzoPagatore text;
civicoPagatore text;
capPagatore text;
localitaPagatore text;
provinciaPagatore text;
nazionePagatore text;
emailPagatore text;
	
dataPagamento text;
tipoVersamento text;
identificativoUnivocoDovuto text;
importoSingoloVersamento text;
identificativoTipoDovuto text;
causaleVersamento text;
datiSpecificiRiscossione text;

enteAccertatore text;
numeroProcessoVerbale text;

rataNumero integer;
BEGIN

SELECT va.id_controllo_ufficiale, va.id_sanzione, va.data_pagamento_ordinanza, va.importo_sanzione_ingiunta, vi.ente1, vi.numeroverbale 
into idControllo, idSanzione, dataPagamento, importoSingoloVersamento, enteAccertatore, numeroProcessoVerbale
 from registro_trasgressori_values va
left join registro_trasgressori_vista vi on vi.id_sanzione = va.id_sanzione
 where  va.id = _id_trasgressione;

dataPagamento:='';
importoSingoloVersamento:='';
tipoIdentificativoUnivoco := 'G';
tipoVersamento := 'ALL';
identificativoTipoDovuto := '1001';
datiSpecificiRiscossione := '9/8711980576';
causaleVersamento := 'PV N: '||COALESCE(numeroProcessoVerbale, '')||'-'||COALESCE(enteAccertatore, '');
identificativoUnivocoDovuto := (select * from genera_pagopa_identificativo_dovuto());
nazionePagatore := 'IT'; 
rataNumero = 1;

select distinct riferimento_id, riferimento_id_nome_tab, ragione_sociale, partita_iva, indirizzo, comune, cap_stab 
into riferimentoId, riferimentoIdNomeTab, anagraficaPagatore, codiceIdentificativoUnivoco, indirizzoPagatore, localitaPagatore, capPagatore
from ricerche_anagrafiche_old_materializzata where (riferimento_id, riferimento_id_nome_tab) in (
select 
case when alt_id > 0 then alt_id 
when id_stabilimento > 0 then id_stabilimento 
when org_id > 0 then org_id 
end,
case when alt_id > 0 then (select return_nome_tabella from gestione_id_alternativo(alt_id, -1)) 
when id_stabilimento > 0 then 'opu_stabilimento' 
when org_id > 0 then 'organization' 
end
from ticket where ticketid = idControllo
);

IF riferimentoIdNomeTab = 'organization' THEN

select distinct oa.civico, oa.state, o.email_rappresentante into civicoPagatore, provinciaPagatore, emailPagatore  from 
organization  o
left join organization_address oa on oa.org_id = o.org_id and oa.address_type = 5
where o.org_id = riferimentoId;

END IF;

IF riferimentoIdNomeTab = 'opu_stabilimento' THEN

select distinct ind.civico, pr.cod_provincia, o.domicilio_digitale into civicoPagatore, provinciaPagatore, emailPagatore  from 
opu_stabilimento  s
left join opu_operatore o on o.id = s.id_operatore
left join opu_indirizzo ind on ind.id = s.id_indirizzo
left join lookup_province pr on pr.code = ind.provincia::integer
where s.id = riferimentoId;

END IF;

IF riferimentoIdNomeTab = 'sintesis_stabilimento' THEN

select distinct ind.civico, pr.cod_provincia, o.domicilio_digitale into civicoPagatore, provinciaPagatore, emailPagatore  from 
sintesis_stabilimento  s
left join sintesis_operatore o on o.id = s.id_operatore
left join sintesis_indirizzo ind on ind.id = s.id_indirizzo
left join lookup_province pr on pr.code = ind.provincia::integer
where s.id = riferimentoId;

END IF;

INSERT INTO registro_trasgressori_pago_pa
(id_trasgressione, id_sanzione , tipo_identificativo_univoco,  codice_identificativo_univoco, anagrafica_pagatore, indirizzo_pagatore,  civico_pagatore, cap_pagatore , localita_pagatore , provincia_pagatore ,  nazione_pagatore, email_pagatore , data_pagamento , tipo_versamento ,  identificativo_univoco_dovuto,  importo_singolo_versamento,  identificativo_tipo_dovuto,  causale_versamento, dati_specifici_riscossione , entered_by, rata_numero ) values (_id_trasgressione, idSanzione, tipoIdentificativoUnivoco, codiceIdentificativoUnivoco, anagraficaPagatore, indirizzoPagatore, civicoPagatore, capPagatore, localitaPagatore, provinciaPagatore, nazionePagatore, emailPagatore, dataPagamento, tipoVersamento, identificativoUnivocoDovuto, importoSingoloVersamento, identificativoTipoDovuto, causaleVersamento, datiSpecificiRiscossione, _id_utente, rataNumero) returning id into idInserito;

return idInserito;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_registro_trasgressori_pago_pa(integer, integer)
  OWNER TO postgres;
  
  
  --già in col
  alter table registro_trasgressori_pago_pa add column url_file_avviso text;

 DROP FUNCTION get_chiamata_ws_pagopa_importa_dovuto(text,text,integer);
 DROP FUNCTION get_pagopa_dovuto(integer);
 
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_pagopa_importa_dovuto(
    _username text,
    _password text,
    _id_pagopa integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ppt="http://www.regione.veneto.it/pagamenti/ente/ppthead" xmlns:ente="http://www.regione.veneto.it/pagamenti/ente/">
<soapenv:Header>
<ppt:intestazionePPT>
<codIpaEnte>', _username, '</codIpaEnte>
</ppt:intestazionePPT>
</soapenv:Header>
<soapenv:Body>
<ente:paaSILImportaDovuto>
<password>', _password, '</password>
<dovuto>', (select * from get_pagopa_dovuto(_id_pagopa)) , '</dovuto>
<flagGeneraIuv>true</flagGeneraIuv>
</ente:paaSILImportaDovuto>
</soapenv:Body>
</soapenv:Envelope>') into ret
from registro_trasgressori_pago_pa 
where id = _id_pagopa;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_pagopa_importa_dovuto(text, text, integer)
  OWNER TO postgres;

  CREATE OR REPLACE FUNCTION public.get_pagopa_dovuto(_id_pagopa integer)
  RETURNS text AS
$BODY$
DECLARE  
 ret text;  
 req text;
BEGIN 


SELECT regexp_replace(encode (request::bytea, 'base64'), E'[\\n\\r]+', '', 'g' ), request into ret, req FROM (

select distinct
concat(
'<?xml version="1.0" encoding = "UTF-8" standalone = "yes" ?>
<Versamento xmlns="http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/" xsi:schemaLocation="http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/schema.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<versioneOggetto>6.0</versioneOggetto>
	<soggettoPagatore>
		<identificativoUnivocoPagatore>
			<tipoIdentificativoUnivoco>', pa.tipo_identificativo_univoco,'</tipoIdentificativoUnivoco>
			<codiceIdentificativoUnivoco>',pa.codice_identificativo_univoco, '</codiceIdentificativoUnivoco>
		</identificativoUnivocoPagatore>
		<anagraficaPagatore>', regexp_replace(pa.anagrafica_pagatore,  '[^\w]+',' ','g'), '</anagraficaPagatore>
		<indirizzoPagatore>', pa.indirizzo_pagatore, '</indirizzoPagatore>
		<civicoPagatore>', pa.civico_pagatore, '</civicoPagatore>
		<capPagatore>', pa.cap_pagatore, '</capPagatore>
		<localitaPagatore>', pa.localita_pagatore, '</localitaPagatore>
		<provinciaPagatore>', pa.provincia_pagatore, '</provinciaPagatore>
		<nazionePagatore>', pa.nazione_pagatore, '</nazionePagatore>
      <e-mailPagatore>', pa.email_pagatore, '</e-mailPagatore>
	</soggettoPagatore>
	<datiVersamento>
		<dataEsecuzionePagamento>', pa.data_pagamento, '</dataEsecuzionePagamento>
		<tipoVersamento>', pa.tipo_versamento, '</tipoVersamento>
		<identificativoUnivocoDovuto>', pa.identificativo_univoco_dovuto, '</identificativoUnivocoDovuto>
		<importoSingoloVersamento>', pa.importo_singolo_versamento, '</importoSingoloVersamento>
		<identificativoTipoDovuto>', pa.identificativo_tipo_dovuto, '</identificativoTipoDovuto>
		<causaleVersamento>', pa.causale_versamento, '</causaleVersamento>
		<datiSpecificiRiscossione>', pa.dati_specifici_riscossione, '</datiSpecificiRiscossione>
	</datiVersamento>
	<azione>I</azione>
</Versamento>') as request

from registro_trasgressori_vista v
left join registro_trasgressori_pago_pa pa on v.id = pa.id_trasgressione and pa.trashed_date is null
where pa.id = _id_pagopa

) aa;

raise info '[PAGOPA REQUEST] %', req;

return ret;

END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_pagopa_dovuto(integer)
  OWNER TO postgres;

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  


CREATE OR REPLACE FUNCTION public.get_chiamata_ws_pagopa_chiedi_pagati(
    _username text,
    _password text,
    _id_pagopa integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ente="http://www.regione.veneto.it/pagamenti/ente/">
   <soapenv:Header/>
   <soapenv:Body>
      <ente:paaSILChiediPagatiConRicevuta>
         <codIpaEnte>', _username, '</codIpaEnte>
         <password>', _password, '</password>
         <identificativoUnivocoDovuto>', identificativo_univoco_dovuto, '</identificativoUnivocoDovuto>
      </ente:paaSILChiediPagatiConRicevuta>
   </soapenv:Body>
</soapenv:Envelope>') into ret
from registro_trasgressori_pago_pa 
where id = _id_pagopa and trashed_date is null;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_pagopa_chiedi_pagati(text, text, integer)
  OWNER TO postgres;
  
  
  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_pagopa_importa_dovuto_aggiorna(
    _username text,
    _password text,
    _id_pagopa integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ppt="http://www.regione.veneto.it/pagamenti/ente/ppthead" xmlns:ente="http://www.regione.veneto.it/pagamenti/ente/">
<soapenv:Header>
<ppt:intestazionePPT>
<codIpaEnte>', _username, '</codIpaEnte>
</ppt:intestazionePPT>
</soapenv:Header>
<soapenv:Body>
<ente:paaSILImportaDovuto>
<password>', _password, '</password>
<dovuto>', (select * from get_pagopa_dovuto_aggiorna(_id_pagopa)) , '</dovuto>
<flagGeneraIuv>true</flagGeneraIuv>
</ente:paaSILImportaDovuto>
</soapenv:Body>
</soapenv:Envelope>') into ret
from registro_trasgressori_pago_pa
where id = _id_pagopa;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_pagopa_importa_dovuto_aggiorna(text, text, integer)
  OWNER TO postgres;

  CREATE OR REPLACE FUNCTION public.get_pagopa_dovuto_aggiorna(_id_pagopa integer)
  RETURNS text AS
$BODY$
DECLARE  
 ret text;  
 req text;
BEGIN 


SELECT regexp_replace(encode (request::bytea, 'base64'), E'[\\n\\r]+', '', 'g' ), request into ret, req FROM (

select distinct
concat(
'<?xml version="1.0" encoding = "UTF-8" standalone = "yes" ?>
<Versamento xmlns="http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/" xsi:schemaLocation="http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/schema.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<versioneOggetto>6.0</versioneOggetto>
	<soggettoPagatore>
		<identificativoUnivocoPagatore>
			<tipoIdentificativoUnivoco>', pa.tipo_identificativo_univoco,'</tipoIdentificativoUnivoco>
			<codiceIdentificativoUnivoco>',pa.codice_identificativo_univoco, '</codiceIdentificativoUnivoco>
		</identificativoUnivocoPagatore>
		<anagraficaPagatore>', regexp_replace(pa.anagrafica_pagatore,  '[^\w]+',' ','g'), '</anagraficaPagatore>
		<indirizzoPagatore>', pa.indirizzo_pagatore, '</indirizzoPagatore>
		<civicoPagatore>', pa.civico_pagatore, '</civicoPagatore>
		<capPagatore>', pa.cap_pagatore, '</capPagatore>
		<localitaPagatore>', pa.localita_pagatore, '</localitaPagatore>
		<provinciaPagatore>', pa.provincia_pagatore, '</provinciaPagatore>
		<nazionePagatore>', pa.nazione_pagatore, '</nazionePagatore>
      <e-mailPagatore>', pa.email_pagatore, '</e-mailPagatore>
	</soggettoPagatore>
	<datiVersamento>
		<dataEsecuzionePagamento>', pa.data_pagamento, '</dataEsecuzionePagamento>
		<tipoVersamento>', pa.tipo_versamento, '</tipoVersamento>
               <identificativoUnivocoVersamento>', pa.identificativo_univoco_versamento, '</identificativoUnivocoVersamento>
               <identificativoUnivocoDovuto>', pa.identificativo_univoco_dovuto, '</identificativoUnivocoDovuto>
		<importoSingoloVersamento>', pa.importo_singolo_versamento, '</importoSingoloVersamento>
		<identificativoTipoDovuto>', pa.identificativo_tipo_dovuto, '</identificativoTipoDovuto>
		<causaleVersamento>', pa.causale_versamento, '</causaleVersamento>
		<datiSpecificiRiscossione>', pa.dati_specifici_riscossione, '</datiSpecificiRiscossione>
	</datiVersamento>
	<azione>M</azione>
</Versamento>') as request

from registro_trasgressori_vista v
left join registro_trasgressori_pago_pa pa on v.id = pa.id_trasgressione and pa.trashed_date is null
where pa.id = _id_pagopa

) aa;

raise info '[PAGOPA REQUEST] %', req;

return ret;

END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_pagopa_dovuto_aggiorna(integer)
  OWNER TO postgres;
  
  
  
  

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_pagopa_importa_dovuto_annulla(
    _username text,
    _password text,
    _id_pagopa integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat('<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ppt="http://www.regione.veneto.it/pagamenti/ente/ppthead" xmlns:ente="http://www.regione.veneto.it/pagamenti/ente/">
<soapenv:Header>
<ppt:intestazionePPT>
<codIpaEnte>', _username, '</codIpaEnte>
</ppt:intestazionePPT>
</soapenv:Header>
<soapenv:Body>
<ente:paaSILImportaDovuto>
<password>', _password, '</password>
<dovuto>', (select * from get_pagopa_dovuto_annulla(_id_pagopa)) , '</dovuto>
<flagGeneraIuv>false</flagGeneraIuv>
</ente:paaSILImportaDovuto>
</soapenv:Body>
</soapenv:Envelope>') into ret
from registro_trasgressori_pago_pa
where id = _id_pagopa;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_pagopa_importa_dovuto_annulla(text, text, integer)
  OWNER TO postgres;

  CREATE OR REPLACE FUNCTION public.get_pagopa_dovuto_annulla(_id_pagopa integer)
  RETURNS text AS
$BODY$
DECLARE  
 ret text;  
 req text;
BEGIN 


SELECT regexp_replace(encode (request::bytea, 'base64'), E'[\\n\\r]+', '', 'g' ), request into ret, req FROM (

select distinct
concat(
'<?xml version="1.0" encoding = "UTF-8" standalone = "yes" ?>
<Versamento xmlns="http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/" xsi:schemaLocation="http://www.regione.veneto.it/schemas/2012/Pagamenti/Ente/schema.xsd" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<versioneOggetto>6.0</versioneOggetto>
	<soggettoPagatore>
		<identificativoUnivocoPagatore>
			<tipoIdentificativoUnivoco>', pa.tipo_identificativo_univoco,'</tipoIdentificativoUnivoco>
			<codiceIdentificativoUnivoco>',pa.codice_identificativo_univoco, '</codiceIdentificativoUnivoco>
		</identificativoUnivocoPagatore>
		<anagraficaPagatore>', regexp_replace(pa.anagrafica_pagatore,  '[^\w]+',' ','g'), '</anagraficaPagatore>
		<indirizzoPagatore>', pa.indirizzo_pagatore, '</indirizzoPagatore>
		<civicoPagatore>', pa.civico_pagatore, '</civicoPagatore>
		<capPagatore>', pa.cap_pagatore, '</capPagatore>
		<localitaPagatore>', pa.localita_pagatore, '</localitaPagatore>
		<provinciaPagatore>', pa.provincia_pagatore, '</provinciaPagatore>
		<nazionePagatore>', pa.nazione_pagatore, '</nazionePagatore>
      <e-mailPagatore>', pa.email_pagatore, '</e-mailPagatore>
	</soggettoPagatore>
	<datiVersamento>
		<dataEsecuzionePagamento>', pa.data_pagamento, '</dataEsecuzionePagamento>
		<tipoVersamento>', pa.tipo_versamento, '</tipoVersamento>
               <identificativoUnivocoDovuto>', pa.identificativo_univoco_dovuto, '</identificativoUnivocoDovuto>
		<importoSingoloVersamento>', pa.importo_singolo_versamento, '</importoSingoloVersamento>
		<identificativoTipoDovuto>', pa.identificativo_tipo_dovuto, '</identificativoTipoDovuto>
		<causaleVersamento>', pa.causale_versamento, '</causaleVersamento>
		<datiSpecificiRiscossione>', pa.dati_specifici_riscossione, '</datiSpecificiRiscossione>
	</datiVersamento>
	<azione>A</azione>
</Versamento>') as request

from registro_trasgressori_vista v
left join registro_trasgressori_pago_pa pa on v.id = pa.id_trasgressione and pa.trashed_date is null
where pa.id = _id_pagopa

) aa;

raise info '[PAGOPA REQUEST] %', req;

return ret;

END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_pagopa_dovuto_annulla(integer)
  OWNER TO postgres;