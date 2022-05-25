-- Chi: Bartolo Sansone
-- Cosa: Flusso 217 - PagoPA 3.0
-- Quando: 09/08/2021

alter table pagopa_anagrafica_pagatori add column telefono text;

 DROP FUNCTION public.pagopa_get_dati_pagatore_default_anagrafica(integer, integer, integer);

CREATE OR REPLACE FUNCTION public.pagopa_get_dati_pagatore_default_anagrafica(
    IN _orgid integer,
    IN _stabid integer,
    IN _altid integer)
  RETURNS TABLE(tipo_pagatore text, piva_cf text, ragione_sociale_nominativo text, indirizzo text, civico text, cap text, comune text, cod_provincia text, nazione text, domicilio_digitale text, telefono text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY
select 'G'::text as tipo_pagatore,
replace(COALESCE(opuo.partita_iva, org.partita_iva, sino.partita_iva), '''', '\''')::text as piva_cf, 
replace(coalesce(opuo.ragione_sociale, org.name, sino.ragione_sociale), '''', '\''')::text as ragione_sociale_nominativo, 
replace(coalesce(opui.via, orgi.addrline1, sini.via), '''', '\''')::text as indirizzo, 
replace(COALESCE(opui.civico, orgi.civico, sini.civico), '''', '\''')::text as civico, 
replace(COALESCE(opui.cap, orgi.postalcode, sini.cap), '''', '\''')::text as cap, 
replace(COALESCE(opuc.nome, orgi.city, sinc.nome), '''', '\''')::text as comune, 
replace(COALESCE(opup.cod_provincia, orgi.state, sinp.cod_provincia), '''', '\''')::text as cod_provincia, 
'IT'::text as nazione,
replace(COALESCE(opuo.domicilio_digitale, org.email_rappresentante, sino.domicilio_digitale), '''', '\''')::text as domicilio_digitale ,
''::text as telefono

from ricerche_anagrafiche_old_materializzata r 
left join organization org on org.org_id = r.riferimento_id and r.riferimento_id_nome_tab = 'organization'
left join opu_stabilimento opus on opus.id = r.riferimento_id and r.riferimento_id_nome_tab = 'opu_stabilimento'
left join sintesis_stabilimento sins on sins.alt_id = r.riferimento_id and r.riferimento_id_nome_tab = 'sintesis_stabilimento'

left join opu_operatore opuo on opuo.id = opus.id_operatore 
left join opu_indirizzo opui on opui.id = opus.id_indirizzo
left join comuni1 opuc on opuc.id = opui.comune
left join lookup_province opup on opup.code = opuc.cod_provincia::integer

left join organization_address orgi on orgi.org_id = org.org_id and orgi.address_type = 5

left join sintesis_operatore sino on sino.id = sins.id_operatore 
left join sintesis_indirizzo sini on sini.id = sins.id_indirizzo
left join comuni1 sinc on sinc.id = sini.comune
left join lookup_province sinp on sinp.code = sinc.cod_provincia::integer

where 1=1

and ((_orgid > 0 and org.org_id = _orgid) or _orgid <=0)
and ((_stabid > 0 and opus.id = _stabid) or _stabid <=0)
and ((_altid > 0 and sins.alt_id = _altid) or _altid <=0)
limit 1;
 
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.pagopa_get_dati_pagatore_default_anagrafica(integer, integer, integer)
  OWNER TO postgres;
  
  DROP FUNCTION public.pagopa_cerca_pagatore(text, text);

CREATE OR REPLACE FUNCTION public.pagopa_cerca_pagatore(
    IN _nome text,
    IN _piva text)
  RETURNS TABLE(origine text, piva_cf text, ragione_sociale_nominativo text, indirizzo text, civico text, cap text, comune text, cod_provincia text, nazione text, domicilio_digitale text, telefono text) AS
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
COALESCE(p.domicilio_digitale, '') as domicilio_digitale,
COALESCE(p.telefono, '') as telefono
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
'' as domicilio_digitale,
'' as telefono
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
ALTER FUNCTION public.pagopa_cerca_pagatore(text, text)
  OWNER TO postgres;

  DROP FUNCTION public.pagopa_get_dati_pagatore_default(integer);

CREATE OR REPLACE FUNCTION public.pagopa_get_dati_pagatore_default(IN _id_sanzione integer)
  RETURNS TABLE(tipo_pagatore text, piva_cf text, ragione_sociale_nominativo text, indirizzo text, civico text, cap text, comune text, cod_provincia text, nazione text, domicilio_digitale text, telefono text) AS
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
COALESCE(opuo.domicilio_digitale, org.email_rappresentante, sino.domicilio_digitale)::text as domicilio_digitale,
''::text as telefono 

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
ALTER FUNCTION public.pagopa_get_dati_pagatore_default(integer)
  OWNER TO postgres;

  
   DROP FUNCTION public.pagopa_get_dati_pagatore_default_anagrafica(integer, text);

CREATE OR REPLACE FUNCTION public.pagopa_get_dati_pagatore_default_anagrafica(
    IN _riferimento_id integer,
    IN _riferimento_id_nome_tab text)
  RETURNS TABLE(tipo_pagatore text, piva_cf text, ragione_sociale_nominativo text, indirizzo text, civico text, cap text, comune text, cod_provincia text, nazione text, domicilio_digitale text, telefono text) AS
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
end, '''', '\''')::text as domicilio_digitale,
''::text as telefono

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
ALTER FUNCTION public.pagopa_get_dati_pagatore_default_anagrafica(integer, text)
  OWNER TO postgres;

  
  -- Function: public.get_pagopa_dovuto(integer)

-- DROP FUNCTION public.get_pagopa_dovuto(integer);

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
		<anagraficaPagatore>', regexp_replace(paga.ragione_sociale_nominativo,  '[^\w]+',' ','g'), '</anagraficaPagatore>',
		case when paga.indirizzo <> '' then concat('<indirizzoPagatore>', paga.indirizzo, '</indirizzoPagatore>') else '' end,
		case when paga.civico <> '' then concat('<civicoPagatore>', paga.civico, '</civicoPagatore>') else '' end,
		case when paga.cap <> '' then concat('<capPagatore>', paga.cap, '</capPagatore>') else '' end,
		case when paga.comune <> '' then concat('<localitaPagatore>', paga.comune, '</localitaPagatore>') else '' end,
		case when paga.cod_provincia <> '' then concat('<provinciaPagatore>', paga.cod_provincia, '</provinciaPagatore>') else '' end,
		case when paga.nazione <> '' then concat('<nazionePagatore>', paga.nazione, '</nazionePagatore>') else '' end,
		case when paga.domicilio_digitale <> '' then concat('<e-mailPagatore>', paga.domicilio_digitale, '</e-mailPagatore>') else '' end,
        '</soggettoPagatore>
	<datiVersamento>',
	case when pa.data_pagamento <> '' then concat('<dataEsecuzionePagamento>', pa.data_pagamento, '</dataEsecuzionePagamento>') else '' end,
         '<tipoVersamento>', pa.tipo_versamento, '</tipoVersamento>
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


-- Function: public.get_pagopa_dovuto_aggiorna(integer)

-- DROP FUNCTION public.get_pagopa_dovuto_aggiorna(integer);

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
		<anagraficaPagatore>', regexp_replace(paga.ragione_sociale_nominativo,  '[^\w]+',' ','g'), '</anagraficaPagatore>',
		case when paga.indirizzo <> '' then concat('<indirizzoPagatore>', paga.indirizzo, '</indirizzoPagatore>') else '' end,
		case when paga.civico <> '' then concat('<civicoPagatore>', paga.civico, '</civicoPagatore>') else '' end,
		case when paga.cap <> '' then concat('<capPagatore>', paga.cap, '</capPagatore>') else '' end,
		case when paga.comune <> '' then concat('<localitaPagatore>', paga.comune, '</localitaPagatore>') else '' end,
		case when paga.cod_provincia <> '' then concat('<provinciaPagatore>', paga.cod_provincia, '</provinciaPagatore>') else '' end,
		case when paga.nazione <> '' then concat('<nazionePagatore>', paga.nazione, '</nazionePagatore>') else '' end,
		case when paga.domicilio_digitale <> '' then concat('<e-mailPagatore>', paga.domicilio_digitale, '</e-mailPagatore>') else '' end,
        '</soggettoPagatore>
	<datiVersamento>',
	case when pa.data_pagamento <> '' then concat('<dataEsecuzionePagamento>', pa.data_pagamento, '</dataEsecuzionePagamento>') else '' end,
         '<tipoVersamento>', pa.tipo_versamento, '</tipoVersamento>
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

-- Function: public.get_pagopa_dovuto_annulla(integer)

-- DROP FUNCTION public.get_pagopa_dovuto_annulla(integer);

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
		<anagraficaPagatore>', regexp_replace(paga.ragione_sociale_nominativo,  '[^\w]+',' ','g'), '</anagraficaPagatore>',
		case when paga.indirizzo <> '' then concat('<indirizzoPagatore>', paga.indirizzo, '</indirizzoPagatore>') else '' end,
		case when paga.civico <> '' then concat('<civicoPagatore>', paga.civico, '</civicoPagatore>') else '' end,
		case when paga.cap <> '' then concat('<capPagatore>', paga.cap, '</capPagatore>') else '' end,
		case when paga.comune <> '' then concat('<localitaPagatore>', paga.comune, '</localitaPagatore>') else '' end,
		case when paga.cod_provincia <> '' then concat('<provinciaPagatore>', paga.cod_provincia, '</provinciaPagatore>') else '' end,
		case when paga.nazione <> '' then concat('<nazionePagatore>', paga.nazione, '</nazionePagatore>') else '' end,
		case when paga.domicilio_digitale <> '' then concat('<e-mailPagatore>', paga.domicilio_digitale, '</e-mailPagatore>') else '' end,
        '</soggettoPagatore>
	<datiVersamento>',
	case when pa.data_pagamento <> '' then concat('<dataEsecuzionePagamento>', pa.data_pagamento, '</dataEsecuzionePagamento>') else '' end,
         '<tipoVersamento>', pa.tipo_versamento, '</tipoVersamento>
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
  
  
CREATE OR REPLACE FUNCTION public.is_cu_nc_sa_cancellabile_pagopa(_idcu integer, _idnc integer, _idsa integer)
  RETURNS text AS
$BODY$
DECLARE
	messaggio text;
BEGIN

messaggio := '';

select string_agg(a.errore,' ') into messaggio from (
select distinct
string_agg('Controllo ufficiale: '||cu.ticketid||' Non Conformita: ' || nc.identificativo ||' Sanzione: ' ||  sa.identificativo ||'; ', '') as errore
from ticket cu
left join ticket nc on cu.ticketid::text = nc.id_controllo_ufficiale and nc.trashed_date is null
left join ticket sa on nc.ticketid = sa.id_nonconformita and sa.trashed_date is null
left join pagopa_pagamenti pa on pa.id_sanzione = sa.ticketid and pa.trashed_date is null
where 1=1 
and pa.identificativo_univoco_versamento is not null
and ((_idcu>0 and cu.ticketid = _idcu) or (_idcu=-1))
and ((_idnc>0 and nc.ticketid = _idnc) or (_idnc=-1))
and ((_idsa>0 and sa.ticketid = _idsa) or (_idsa=-1))
) a ;

if messaggio <> '' then
messaggio := concat('Cancellazione non possibile a causa della presenza di sanzioni inviate a PagoPa. ', messaggio);
END IF;

 RETURN UPPER(messaggio);
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;

-- 4.0
  
alter table pagopa_pagamenti add column singolo_importo_pagato text;
alter table pagopa_pagamenti add column data_esito_singolo_pagamento text;
alter table pagopa_pagamenti add column data_generazione_iuv timestamp without time zone;
alter table pagopa_pagamenti add column tipo_riduzione text;
alter table pagopa_pagamenti add column tipo_notifica text;

DROP FUNCTION public.pagopa_genera_pagamento(integer, integer, text, text, text, integer, integer);

CREATE OR REPLACE FUNCTION public.pagopa_genera_pagamento(
    _idsanzione integer,
    _idpagatore integer,
    _importo text,
    _datapagamento text,
    _tipopagamento text,
    _tiporiduzione text,
    _indice integer,
    _idutente integer)
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

insert into pagopa_pagamenti(id_sanzione, id_pagatore, data_pagamento, importo_singolo_versamento, entered_by, indice, tipo_pagamento, tipo_riduzione, tipo_versamento, identificativo_univoco_dovuto, identificativo_tipo_dovuto, causale_versamento, dati_specifici_riscossione)
values (_idsanzione, _idpagatore, _datapagamento, _importo, _idutente, _indice, _tipopagamento, _tiporiduzione, _tipoversamento, _iud, _identificativotipodovuto, _causale, _datispecifici) returning id into idInserito;

return idInserito;

END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.pagopa_genera_pagamento(integer, integer, text, text, text, text, integer, integer)
  OWNER TO postgres;

CREATE table pagopa_storico(
id serial,
id_utente integer,
data_operazione timestamp without time zone default now(),
id_sanzione integer,
id_pagamento integer,
operazione text)


--- AGGIORNAMENTO GESTIONE PERMESSI
-- Da lanciare in col

-- Elimino i role permission vecchi
delete from role_permission where permission_id in (select permission_id from permission where permission ilike 'registro_trasgressori_invio_pagopa');
delete from role_permission_ext where permission_id in (select permission_id from permission_ext where permission ilike 'registro_trasgressori_invio_pagopa');

-- Elimino il permission vecchio
delete from permission where permission ilike 'registro_trasgressori_invio_pagopa';
delete from permission_ext where permission ilike 'registro_trasgressori_invio_pagopa';

-- Inserisco il permission nuovo
insert into permission(category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) values(31, 'gestione_pagopa', true, true, true, true, 'Gestione PagoPA', 0);

insert into permission_ext(category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level) values(31, 'gestione_pagopa', true, true, true, true, 'Gestione PagoPA', 0);

-- Inserisco i role permission nuovi
insert into role_permission (role_id, permission_id, role_view, role_add, role_edit, role_delete) 
select role_id, (select permission_id from permission where permission ilike 'gestione_pagopa'), true, true, true, true
from role where enabled;

insert into role_permission_ext (role_id, permission_id, role_view, role_add, role_edit, role_delete) 
select role_id, (select permission_id from permission_ext where permission ilike 'gestione_pagopa'), true, true, true, true
from role_ext where enabled;

-- Aggiornamento data pagamento -> scadenza
alter table pagopa_pagamenti rename column data_pagamento to data_scadenza;

DROP FUNCTION pagopa_genera_pagamento(integer,integer,text,text,text,text,integer,integer);

CREATE OR REPLACE FUNCTION public.pagopa_genera_pagamento(
    _idsanzione integer,
    _idpagatore integer,
    _importo text,
    _datascadenza text,
    _tipopagamento text,
    _tiporiduzione text,
    _indice integer,
    _idutente integer)
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

insert into pagopa_pagamenti(id_sanzione, id_pagatore, data_scadenza, importo_singolo_versamento, entered_by, indice, tipo_pagamento, tipo_riduzione, tipo_versamento, identificativo_univoco_dovuto, identificativo_tipo_dovuto, causale_versamento, dati_specifici_riscossione)
values (_idsanzione, _idpagatore, _datascadenza, _importo, _idutente, _indice, _tipopagamento, _tiporiduzione, _tipoversamento, _iud, _identificativotipodovuto, _causale, _datispecifici) returning id into idInserito;

return idInserito;

END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.pagopa_genera_pagamento(integer, integer, text, text, text, text, integer, integer)
  OWNER TO postgres;

  -- Function: public.get_pagopa_dovuto(integer)

-- DROP FUNCTION public.get_pagopa_dovuto(integer);

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
		<anagraficaPagatore>', regexp_replace(paga.ragione_sociale_nominativo,  '[^\w]+',' ','g'), '</anagraficaPagatore>',
		case when paga.indirizzo <> '' then concat('<indirizzoPagatore>', paga.indirizzo, '</indirizzoPagatore>') else '' end,
		case when paga.civico <> '' then concat('<civicoPagatore>', paga.civico, '</civicoPagatore>') else '' end,
		case when paga.cap <> '' then concat('<capPagatore>', paga.cap, '</capPagatore>') else '' end,
		case when paga.comune <> '' then concat('<localitaPagatore>', paga.comune, '</localitaPagatore>') else '' end,
		case when paga.cod_provincia <> '' then concat('<provinciaPagatore>', paga.cod_provincia, '</provinciaPagatore>') else '' end,
		case when paga.nazione <> '' then concat('<nazionePagatore>', paga.nazione, '</nazionePagatore>') else '' end,
		case when paga.domicilio_digitale <> '' then concat('<e-mailPagatore>', paga.domicilio_digitale, '</e-mailPagatore>') else '' end,
        '</soggettoPagatore>
	<datiVersamento>',
	case when pa.data_scadenza <> '' then concat('<dataEsecuzionePagamento>', pa.data_scadenza, '</dataEsecuzionePagamento>') else '' end,
         '<tipoVersamento>', pa.tipo_versamento, '</tipoVersamento>
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

  -- Function: public.get_pagopa_dovuto_aggiorna(integer)

-- DROP FUNCTION public.get_pagopa_dovuto_aggiorna(integer);

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
		<anagraficaPagatore>', regexp_replace(paga.ragione_sociale_nominativo,  '[^\w]+',' ','g'), '</anagraficaPagatore>',
		case when paga.indirizzo <> '' then concat('<indirizzoPagatore>', paga.indirizzo, '</indirizzoPagatore>') else '' end,
		case when paga.civico <> '' then concat('<civicoPagatore>', paga.civico, '</civicoPagatore>') else '' end,
		case when paga.cap <> '' then concat('<capPagatore>', paga.cap, '</capPagatore>') else '' end,
		case when paga.comune <> '' then concat('<localitaPagatore>', paga.comune, '</localitaPagatore>') else '' end,
		case when paga.cod_provincia <> '' then concat('<provinciaPagatore>', paga.cod_provincia, '</provinciaPagatore>') else '' end,
		case when paga.nazione <> '' then concat('<nazionePagatore>', paga.nazione, '</nazionePagatore>') else '' end,
		case when paga.domicilio_digitale <> '' then concat('<e-mailPagatore>', paga.domicilio_digitale, '</e-mailPagatore>') else '' end,
        '</soggettoPagatore>
	<datiVersamento>',
	case when pa.data_scadenza <> '' then concat('<dataEsecuzionePagamento>', pa.data_scadenza, '</dataEsecuzionePagamento>') else '' end,
         '<tipoVersamento>', pa.tipo_versamento, '</tipoVersamento>
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

-- Function: public.get_pagopa_dovuto_annulla(integer)

-- DROP FUNCTION public.get_pagopa_dovuto_annulla(integer);

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
		<anagraficaPagatore>', regexp_replace(paga.ragione_sociale_nominativo,  '[^\w]+',' ','g'), '</anagraficaPagatore>',
		case when paga.indirizzo <> '' then concat('<indirizzoPagatore>', paga.indirizzo, '</indirizzoPagatore>') else '' end,
		case when paga.civico <> '' then concat('<civicoPagatore>', paga.civico, '</civicoPagatore>') else '' end,
		case when paga.cap <> '' then concat('<capPagatore>', paga.cap, '</capPagatore>') else '' end,
		case when paga.comune <> '' then concat('<localitaPagatore>', paga.comune, '</localitaPagatore>') else '' end,
		case when paga.cod_provincia <> '' then concat('<provinciaPagatore>', paga.cod_provincia, '</provinciaPagatore>') else '' end,
		case when paga.nazione <> '' then concat('<nazionePagatore>', paga.nazione, '</nazionePagatore>') else '' end,
		case when paga.domicilio_digitale <> '' then concat('<e-mailPagatore>', paga.domicilio_digitale, '</e-mailPagatore>') else '' end,
        '</soggettoPagatore>
	<datiVersamento>',
	case when pa.data_scadenza <> '' then concat('<dataEsecuzionePagamento>', pa.data_scadenza, '</dataEsecuzionePagamento>') else '' end,
         '<tipoVersamento>', pa.tipo_versamento, '</tipoVersamento>
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

alter table pagopa_pagamenti add column note_hd text;

