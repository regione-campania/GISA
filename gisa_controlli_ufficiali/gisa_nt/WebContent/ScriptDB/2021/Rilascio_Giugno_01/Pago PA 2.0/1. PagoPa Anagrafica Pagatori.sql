-- Anagrafica dei pagatori

CREATE TABLE pagopa_anagrafica_pagatori(id serial primary key, tipo_pagatore text, piva_cf text, ragione_sociale_nominativo text, indirizzo text, civico text, cap text, comune text, cod_provincia text, nazione text, domicilio_digitale text, entered timestamp without time zone default now(), enteredby integer, trashed_date timestamp without time zone, note_hd text);

CREATE TABLE pagopa_mapping_sanzioni_pagatori(id serial primary key, id_sanzione integer, id_pagatore integer, trasgressore_obbligato text, entered timestamp without time zone default now(), trashed_date timestamp without time zone, enteredby integer, note_hd text);

CREATE OR REPLACE FUNCTION pagopa_get_dati_pagatore_default(
    IN _id_sanzione integer)
  RETURNS TABLE(tipo_pagatore text, piva_cf text, ragione_sociale_nominativo text, indirizzo text, civico text, cap text, comune text, cod_provincia text, nazione text, domicilio_digitale text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY
select 'G'::text as tipo_pagatore,
COALESCE(opuo.partita_iva, org.partita_iva, sino.partita_iva)::text as piva_cf, 
coalesce(opuo.ragione_sociale, org.name, sino.ragione_sociale)::text as ragione_sociale_nominativo, 
coalesce(opui.via, orgi.addrline1, sini.via)::text as indirizzo, 
COALESCE(opui.civico, orgi.civico, sini.civico)::text as civico, 
COALESCE(opui.cap, orgi.postalcode, sini.cap)::text as cap, 
COALESCE(opuc.nome, orgi.city, sinc.nome)::text as comune, 
COALESCE(opup.cod_provincia, orgi.state, sinp.cod_provincia)::text as cod_provincia, 
'IT'::text as nazione,
COALESCE(opuo.domicilio_digitale, org.email_rappresentante, sino.domicilio_digitale)::text as domicilio_digitale 

from ticket sanzione
join ticket cu on cu.ticketid = sanzione.id_controllo_ufficiale::integer

left join opu_stabilimento opus on opus.id = cu.id_stabilimento and cu.id_stabilimento>0
left join opu_operatore opuo on opuo.id = opus.id_operatore 
left join opu_indirizzo opui on opui.id = opus.id_indirizzo
left join comuni1 opuc on opuc.id = opui.comune
left join lookup_province opup on opup.code = opuc.cod_provincia::integer

left join organization org on org.org_id = cu.org_id and cu.org_id>0
left join organization_address orgi on orgi.org_id = org.org_id and orgi.address_type = 5

left join sintesis_stabilimento sins on sins.alt_id = cu.alt_id and cu.alt_id>0
left join sintesis_operatore sino on sino.id = sins.id_operatore 
left join sintesis_indirizzo sini on sini.id = sins.id_indirizzo
left join comuni1 sinc on sinc.id = sini.comune
left join lookup_province sinp on sinp.code = sinc.cod_provincia::integer

where sanzione.ticketid = _id_sanzione
limit 1;
 
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION pagopa_get_dati_pagatore_default(integer)
  OWNER TO postgres;
  
 

 

 CREATE OR REPLACE FUNCTION pagopa_get_dati_pagatore_default_anagrafica(
    IN _riferimento_id integer, IN _riferimento_id_nome_tab text)
  RETURNS TABLE(tipo_pagatore text, piva_cf text, ragione_sociale_nominativo text, indirizzo text, civico text, cap text, comune text, cod_provincia text, nazione text, domicilio_digitale text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY
select 'G'::text as tipo_pagatore,

replace(
case 
when r.partita_iva <> '' then r.partita_iva 
when r.codice_fiscale <> '' then r.codice_fiscale 
when r.codice_fiscale_rappresentante <> '' then r.codice_fiscale_rappresentante else '' 
end, '''', '\''')::text as piva_cf, 

r.ragione_sociale::text as ragione_sociale_nominativo, 

replace(
case 
when r.riferimento_id_nome_tab = 'organization' and r.tipologia_operatore <> 2 then orgi.addrline1
when r.riferimento_id_nome_tab = 'organization' and r.tipologia_operatore = 2 then orga.indrizzo_azienda
when r.riferimento_id_nome_tab = 'opu_stabilimento' then concat(oput.description, ' ', opui.via)
when r.riferimento_id_nome_tab = 'apicoltura_imprese' then concat(apit.description, ' ', apii.via)
when r.riferimento_id_nome_tab = 'sintesis_stabilimento' then concat(sint.description, ' ', sini.via)
end, '''', '\''')::text as indirizzo, 

replace(
case 
when r.riferimento_id_nome_tab = 'organization' and r.tipologia_operatore <> 2 then orgi.civico
when r.riferimento_id_nome_tab = 'organization' and r.tipologia_operatore = 2 then ''
when r.riferimento_id_nome_tab = 'opu_stabilimento' then opui.civico
when r.riferimento_id_nome_tab = 'apicoltura_imprese' then apii.civico
when r.riferimento_id_nome_tab = 'sintesis_stabilimento' then sini.civico
end, '''', '\''')::text as civico, 

replace(
case 
when r.riferimento_id_nome_tab = 'organization' and r.tipologia_operatore <> 2 then orgi.postalcode
when r.riferimento_id_nome_tab = 'organization' and r.tipologia_operatore = 2 then orga.cap_azienda
when r.riferimento_id_nome_tab = 'opu_stabilimento' then opui.cap
when r.riferimento_id_nome_tab = 'apicoltura_imprese' then apii.cap
when r.riferimento_id_nome_tab = 'sintesis_stabilimento' then sini.cap
end, '''', '\''')::text as cap, 

replace(
case 
when r.riferimento_id_nome_tab = 'organization' and r.tipologia_operatore <> 2 then orgi.city
when r.riferimento_id_nome_tab = 'organization' and r.tipologia_operatore = 2 then orgac.nome
when r.riferimento_id_nome_tab = 'opu_stabilimento' then opuc.nome
when r.riferimento_id_nome_tab = 'apicoltura_imprese' then apic.nome
when r.riferimento_id_nome_tab = 'sintesis_stabilimento' then sinc.nome
end, '''', '\''')::text as comune, 

replace(
case 
when r.riferimento_id_nome_tab = 'organization' and r.tipologia_operatore <> 2 then orgi.state
when r.riferimento_id_nome_tab = 'organization' and r.tipologia_operatore = 2 then orga.prov_sede_azienda
when r.riferimento_id_nome_tab = 'opu_stabilimento' then opup.cod_provincia
when r.riferimento_id_nome_tab = 'apicoltura_imprese' then apip.cod_provincia
when r.riferimento_id_nome_tab = 'sintesis_stabilimento' then sinp.cod_provincia
end, '''', '\''')::text as cod_provincia, 

'IT'::text as nazione,

replace(
case 
when r.riferimento_id_nome_tab = 'organization' then org.email_rappresentante
when r.riferimento_id_nome_tab = 'opu_stabilimento' then opuo.domicilio_digitale
when r.riferimento_id_nome_tab = 'apicoltura_imprese' then apio.domicilio_digitale
when r.riferimento_id_nome_tab = 'sintesis_stabilimento' then sino.domicilio_digitale
end, '''', '\''')::text as domicilio_digitale

from ricerche_anagrafiche_old_materializzata r 

left join organization org on org.org_id = r.riferimento_id and r.riferimento_id_nome_tab = 'organization'
left join organization_address orgi on orgi.org_id = org.org_id and orgi.address_type = 5
left join aziende orga on orga.cod_azienda = org.account_number
left join comuni1 orgac on orgac.istat::int = orga.cod_comune_azienda::int

left join opu_stabilimento opus on opus.id = r.riferimento_id and r.riferimento_id_nome_tab = 'opu_stabilimento'
left join opu_operatore opuo on opuo.id = opus.id_operatore 
left join opu_indirizzo opui on opui.id = opus.id_indirizzo
left join lookup_toponimi oput on oput.code = opui.toponimo
left join comuni1 opuc on opuc.id = opui.comune
left join lookup_province opup on opup.code = opuc.cod_provincia::integer

left join apicoltura_apiari apis on apis.id = r.riferimento_id and r.riferimento_id_nome_tab = 'apicoltura_imprese'
left join apicoltura_imprese apio on apio.id = apis.id_operatore 
left join opu_indirizzo apii on apii.id = apis.id_indirizzo
left join lookup_toponimi apit on apit.code = apii.toponimo
left join comuni1 apic on apic.id = apii.comune
left join lookup_province apip on apip.code = apic.cod_provincia::integer

left join sintesis_stabilimento sins on sins.alt_id = r.riferimento_id and r.riferimento_id_nome_tab = 'sintesis_stabilimento'
left join sintesis_operatore sino on sino.id = sins.id_operatore 
left join sintesis_indirizzo sini on sini.id = sins.id_indirizzo
left join lookup_toponimi sint on sint.code = sini.toponimo
left join comuni1 sinc on sinc.id = sini.comune
left join lookup_province sinp on sinp.code = sinc.cod_provincia::integer

where 1=1

and r.riferimento_id = _riferimento_id and r.riferimento_id_nome_tab = _riferimento_id_nome_tab
limit 1;
 
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION pagopa_get_dati_pagatore_default_anagrafica(integer, text)
  OWNER TO postgres;
  

CREATE OR REPLACE FUNCTION pagopa_cerca_pagatore(
    IN _nome text, _piva text)
  RETURNS TABLE(origine text, piva_cf text, ragione_sociale_nominativo text, indirizzo text, civico text, cap text, comune text, cod_provincia text, nazione text, domicilio_digitale text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY
select 
distinct 'Anagrafe Pagatori' as origine, 
COALESCE(p.piva_cf, '') as piva_cf, 
COALESCE(p.ragione_sociale_nominativo, '') as ragione_sociale_nominativo, 
COALESCE(p.indirizzo, '') as indirizzo, 
COALESCE(p.civico, '') as civico, 
COALESCE(p.cap, '') as cap, 
COALESCE(p.comune, '') as comune, 
COALESCE(p.cod_provincia, '') as cod_provincia, 
COALESCE(p.nazione, '') as nazione, 
COALESCE(p.domicilio_digitale, '') as domicilio_digitale 
from pagopa_anagrafica_pagatori p
where 1=1
and ((_nome <> '' and p.ragione_sociale_nominativo ilike '%'||_nome||'%') or (_nome = ''))
and ((_piva <> '' and p.piva_cf ilike '%'||_piva||'%') or (_piva = ''))
and p.trashed_date is null

UNION 

select 
distinct 'Anagrafe GISA' as origine, 
COALESCE(r.partita_iva, '') as piva_cf, 
COALESCE(r.ragione_sociale, '') as ragione_sociale_nominativo, 
COALESCE(r.indirizzo, '') as indirizzo, 
'' as civico, 
COALESCE(r.cap_stab, '') as cap, 
COALESCE(r.comune, '') as comune, 
COALESCE(p.cod_provincia, '') as cod_provincia, 
'IT' as nazione, 
'' as domicilio_digitale
from ricerche_anagrafiche_old_materializzata r
left join lookup_province p on p.description ilike r.provincia_stab
where 1=1
and ((_nome <> '' and r.ragione_sociale ilike '%'||_nome||'%') or (_nome = ''))
and ((_piva <> '' and r.partita_iva ilike '%'||_piva||'%') or (_piva = '')); 
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION pagopa_cerca_pagatore(text, text)
  OWNER TO postgres;

   
-- Tabella dei pagamenti
    
CREATE TABLE public.pagopa_pagamenti
(
  id SERIAL PRIMARY KEY,
  id_sanzione integer,
  id_pagatore integer,
  data_pagamento text,
  tipo_versamento text,
  identificativo_univoco_dovuto text,
  importo_singolo_versamento text,
  identificativo_tipo_dovuto text,
  causale_versamento text,
  dati_specifici_riscossione text,
  entered_by integer,
  entered timestamp without time zone DEFAULT now(),
  modified_by integer,
  inviato_by integer,
  inviato boolean DEFAULT false,
  data_invio timestamp without time zone,
  esito_invio text,
  trashed_date timestamp without time zone,
  identificativo_univoco_versamento text,
  indice integer,
  tipo_pagamento text,
  url_file_avviso text,
  descrizione_errore text,
  stato_pagamento text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.pagopa_pagamenti
  OWNER TO postgres;
  
alter table pagopa_pagamenti add column denominazione_attestante text;
alter table pagopa_pagamenti add column denominazione_beneficiario text;
alter table pagopa_pagamenti add column esito_singolo_pagamento text;
alter table pagopa_pagamenti add column identificativo_univoco_riscossione text;  
  
drop FUNCTION public.pagopa_genera_pagamento(_idsanzione integer, _idpagatore integer, _importo text, _datapagamento text, _tipopagamento text, _indice integer, _idutente integer);

CREATE OR REPLACE FUNCTION public.pagopa_genera_pagamento(_idsanzione integer, _idpagatore integer, _importo text, _datapagamento text, _tipopagamento text, _indice integer, _idutente integer)
  RETURNS integer AS
$BODY$
DECLARE  
idInserito integer;
_causale text;
_datispecifici text;
_identificativotipodovuto text;
_tipoversamento text;
_iud text;
BEGIN 

idInserito:= -1;

IF (_indice<0) THEN
select  COALESCE(max(indice), 0)+1 into _indice from pagopa_pagamenti where id_sanzione = _idsanzione and id_pagatore = _idpagatore and trashed_date is null;
END IF;

_tipoversamento = 'ALL';
_datispecifici = '9/8711980576';
_identificativotipodovuto = '1001';

select concat('PV N: ', t.tipo_richiesta, ' - ', CASE WHEN nucleo.in_dpat THEN 'ASL '::text || asl.description::text ELSE nucleo.description::text END) into _causale from ticket t
left join ticket controllo on controllo.ticketid = t.id_controllo_ufficiale::integer
LEFT JOIN lookup_site_id asl ON asl.code = controllo.site_id
LEFT JOIN nucleo_ispettivo_mv mv_nucleo ON mv_nucleo.id_controllo_ufficiale = controllo.ticketid
LEFT JOIN lookup_qualifiche nucleo ON nucleo.description::text = mv_nucleo.nucleo_ispettivo::text
where t.ticketid = _idsanzione;

select pagopa_genera_identificativo_dovuto into _iud from pagopa_genera_identificativo_dovuto(_idsanzione);

insert into pagopa_pagamenti(id_sanzione, id_pagatore, data_pagamento, importo_singolo_versamento, entered_by, indice, tipo_pagamento, tipo_versamento, identificativo_univoco_dovuto, identificativo_tipo_dovuto, causale_versamento, dati_specifici_riscossione)
values (_idsanzione, _idpagatore, _datapagamento, _importo, _idutente, _indice, _tipopagamento, _tipoversamento, _iud, _identificativotipodovuto, _causale, _datispecifici) returning id into idInserito;

return idInserito;

END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

  CREATE TABLE pagopa_identificativo_dovuti_progressivi (id serial primary key, progressivo integer, identificativo_univoco_dovuto text, entered timestamp without time zone default now());

  
drop FUNCTION public.pagopa_genera_identificativo_dovuto();

CREATE OR REPLACE FUNCTION public.pagopa_genera_identificativo_dovuto(IN _idsanzione integer)
  RETURNS text AS
$BODY$
DECLARE
codiceTariffa text;
identificativoUnivocoDovuto text;
maxProgressivo integer;
dataSanzione timestamp without time zone;
BEGIN

SELECT COALESCE(max(progressivo), 0)+1 into maxProgressivo from pagopa_identificativo_dovuti_progressivi;

SELECT assigned_date into dataSanzione from ticket where ticketid = _idsanzione;

SELECT l.codice_tariffa into codiceTariffa from norme_violate_sanzioni n join lookup_norme l on l.code = n.id_norma where n.idticket = _idsanzione;

select substring(date_part('year', dataSanzione)::text, 3,2)||codiceTariffa||'01'||( select LPAD(maxProgressivo::text, 7, '0')) into identificativoUnivocoDovuto;
insert into pagopa_identificativo_dovuti_progressivi(progressivo, identificativo_univoco_dovuto) values (maxProgressivo, identificativoUnivocoDovuto);
raise info 'pagopa nuovo identificativo univoco dovuto generato: %', identificativoUnivocoDovuto;
return identificativoUnivocoDovuto;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.pagopa_genera_identificativo_dovuto(integer)
  OWNER TO postgres;
  
  
  
  -- Invio pagamento WS

  
  DROP FUNCTION get_chiamata_ws_pagopa_importa_dovuto(text,text,integer);
 DROP FUNCTION get_pagopa_dovuto(integer);
 
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_pagopa_importa_dovuto(
    _username text,
    _password text,
    _id_pagamento integer)
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
<dovuto>', (select * from get_pagopa_dovuto(_id_pagamento)) , '</dovuto>
<flagGeneraIuv>true</flagGeneraIuv>
</ente:paaSILImportaDovuto>
</soapenv:Body>
</soapenv:Envelope>') into ret
from pagopa_pagamenti 
where id = _id_pagamento;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_pagopa_importa_dovuto(text, text, integer)
  OWNER TO postgres;

  CREATE OR REPLACE FUNCTION public.get_pagopa_dovuto(_id_pagamento integer)
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
			<tipoIdentificativoUnivoco>', paga.tipo_pagatore,'</tipoIdentificativoUnivoco>
			<codiceIdentificativoUnivoco>',paga.piva_cf, '</codiceIdentificativoUnivoco>
		</identificativoUnivocoPagatore>
		<anagraficaPagatore>', regexp_replace(paga.ragione_sociale_nominativo,  '[^\w]+',' ','g'), '</anagraficaPagatore>
		<indirizzoPagatore>', paga.indirizzo, '</indirizzoPagatore>
		<civicoPagatore>', paga.civico, '</civicoPagatore>
		<capPagatore>', paga.cap, '</capPagatore>
		<localitaPagatore>', paga.comune, '</localitaPagatore>
		<provinciaPagatore>', paga.cod_provincia, '</provinciaPagatore>
		<nazionePagatore>', paga.nazione, '</nazionePagatore>
      <e-mailPagatore>', paga.domicilio_digitale, '</e-mailPagatore>
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

from pagopa_pagamenti pa
join pagopa_anagrafica_pagatori paga on paga.id = pa.id_pagatore
where pa.id = _id_pagamento and pa.trashed_date is null

) aa;

raise info '[PAGOPA REQUEST] %', req;

return ret;

END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_pagopa_dovuto(integer)
  OWNER TO postgres;
  
  
    
  DROP FUNCTION get_chiamata_ws_pagopa_importa_dovuto_aggiorna(text,text,integer);
    DROP FUNCTION get_pagopa_dovuto_aggiorna(integer);
  DROP FUNCTION get_chiamata_ws_pagopa_importa_dovuto_annulla(text,text,integer);
    DROP FUNCTION get_pagopa_dovuto_annulla(integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_pagopa_importa_dovuto_aggiorna(
    _username text,
    _password text,
    _id_pagamento integer)
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
<dovuto>', (select * from get_pagopa_dovuto_aggiorna(_id_pagamento)) , '</dovuto>
<flagGeneraIuv>true</flagGeneraIuv>
</ente:paaSILImportaDovuto>
</soapenv:Body>
</soapenv:Envelope>') into ret
from pagopa_pagamenti
where id = _id_pagamento;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_pagopa_importa_dovuto_aggiorna(text, text, integer)
  OWNER TO postgres;

  CREATE OR REPLACE FUNCTION public.get_pagopa_dovuto_aggiorna(_id_pagamento integer)
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
			<tipoIdentificativoUnivoco>', paga.tipo_pagatore,'</tipoIdentificativoUnivoco>
			<codiceIdentificativoUnivoco>',paga.piva_cf, '</codiceIdentificativoUnivoco>
		</identificativoUnivocoPagatore>
		<anagraficaPagatore>', regexp_replace(paga.ragione_sociale_nominativo,  '[^\w]+',' ','g'), '</anagraficaPagatore>
		<indirizzoPagatore>', paga.indirizzo, '</indirizzoPagatore>
		<civicoPagatore>', paga.civico, '</civicoPagatore>
		<capPagatore>', paga.cap, '</capPagatore>
		<localitaPagatore>', paga.comune, '</localitaPagatore>
		<provinciaPagatore>', paga.cod_provincia, '</provinciaPagatore>
		<nazionePagatore>', paga.nazione, '</nazionePagatore>
      <e-mailPagatore>', paga.domicilio_digitale, '</e-mailPagatore>
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


from pagopa_pagamenti pa
join pagopa_anagrafica_pagatori paga on paga.id = pa.id_pagatore
where pa.id = _id_pagamento and pa.trashed_date is null

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
    _id_pagamento integer)
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
<dovuto>', (select * from get_pagopa_dovuto_annulla(_id_pagamento)) , '</dovuto>
<flagGeneraIuv>false</flagGeneraIuv>
</ente:paaSILImportaDovuto>
</soapenv:Body>
</soapenv:Envelope>') into ret
from pagopa_pagamenti 
where id = _id_pagamento;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_pagopa_importa_dovuto_annulla(text, text, integer)
  OWNER TO postgres;

  CREATE OR REPLACE FUNCTION public.get_pagopa_dovuto_annulla(_id_pagamento integer)
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
			<tipoIdentificativoUnivoco>', paga.tipo_pagatore,'</tipoIdentificativoUnivoco>
			<codiceIdentificativoUnivoco>',paga.piva_cf, '</codiceIdentificativoUnivoco>
		</identificativoUnivocoPagatore>
		<anagraficaPagatore>', regexp_replace(paga.ragione_sociale_nominativo,  '[^\w]+',' ','g'), '</anagraficaPagatore>
		<indirizzoPagatore>', paga.indirizzo, '</indirizzoPagatore>
		<civicoPagatore>', paga.civico, '</civicoPagatore>
		<capPagatore>', paga.cap, '</capPagatore>
		<localitaPagatore>', paga.comune, '</localitaPagatore>
		<provinciaPagatore>', paga.cod_provincia, '</provinciaPagatore>
		<nazionePagatore>', paga.nazione, '</nazionePagatore>
      <e-mailPagatore>', paga.domicilio_digitale, '</e-mailPagatore>
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


from pagopa_pagamenti pa
join pagopa_anagrafica_pagatori paga on paga.id = pa.id_pagatore
where pa.id = _id_pagamento and pa.trashed_date is null

) aa;

raise info '[PAGOPA REQUEST] %', req;

return ret;

END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_pagopa_dovuto_annulla(integer)
  OWNER TO postgres;
  
  DROP FUNCTION get_chiamata_ws_pagopa_chiedi_pagati(text,text,integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_pagopa_chiedi_pagati(
    _username text,
    _password text,
    _id_pagamento integer)
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
from pagopa_pagamenti  
where id = _id_pagamento and trashed_date is null;

 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_pagopa_chiedi_pagati(text, text, integer)
  OWNER TO postgres;
  
  
  -- Vista registro trasgressori (ripristinata. Se dà errore fare prima drop)
  
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
    rt.num_ordinanza
   FROM ticket t
     JOIN ticket controllo ON controllo.tipologia = 3 AND t.id_controllo_ufficiale::text = controllo.id_controllo_ufficiale::text
     LEFT JOIN nucleo_ispettivo_mv mv_nucleo ON mv_nucleo.id_controllo_ufficiale = controllo.ticketid
     LEFT JOIN lookup_qualifiche nucleo ON nucleo.description::text = mv_nucleo.nucleo_ispettivo::text
     LEFT JOIN lookup_qualifiche nucleo2 ON nucleo2.description::text = mv_nucleo.nucleo_ispettivo_due::text
     LEFT JOIN lookup_qualifiche nucleo3 ON nucleo3.description::text = mv_nucleo.nucleo_ispettivo_tre::text
     LEFT JOIN lookup_site_id asl ON asl.code = controllo.site_id
     LEFT JOIN registro_trasgressori_values rt ON t.ticketid = rt.id_sanzione
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
    rt.num_ordinanza
   FROM ticket t
     LEFT JOIN lookup_site_id asl ON asl.code = t.site_id
     LEFT JOIN registro_trasgressori_values rt ON t.ticketid = rt.id_sanzione
  WHERE t.trashed_date IS NULL AND t.tipologia = 1 AND t.assigned_date >= '2015-03-01 00:00:00'::timestamp without time zone AND t.note_internal_use_only ~~* '%RECORD GENERATO AUTOMATICAMENTE PER INSERIMENTO SANZIONE FORZATA NEL REGISTRO TRASGRESSORI%'::text;

ALTER TABLE public.registro_trasgressori_vista
  OWNER TO postgres;

  
  --Dbi controllo chiudibile
  
  
CREATE OR REPLACE FUNCTION public.is_controllo_chiudibile_pagopa(IN _idcu integer)
  RETURNS text AS
$BODY$
DECLARE
	messaggio text;
BEGIN

messaggio := '';

select string_agg(a.errore,' ') into messaggio from (
select distinct 
string_agg('Non Conformita: ' || s.identificativonc ||' Sanzione: ' ||  s.identificativo ||'; ', '') as errore, is_previsto_pagopa(s.ticketid) as previsto_pagopa
from ticket s
join ticket nc on nc.ticketid = s.id_nonconformita
join ticket cu on cu.ticketid = nc.id_controllo_ufficiale::integer
left join pagopa_pagamenti p on s.ticketid = p.id_sanzione and p.trashed_date is null
where cu.ticketid = _idcu and s.tipologia = 1 and s.trashed_date is null
and p.identificativo_univoco_versamento is null group by s.ticketid
) a where a.previsto_pagopa;

if messaggio <> '' then
messaggio := concat('Questo controllo non puo essere chiuso a causa della presenza di sanzioni non ancora inviate a PagoPa. ', messaggio);
END IF;

 RETURN UPPER(messaggio);
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.is_controllo_chiudibile_pagopa(integer)
  OWNER TO postgres;

  