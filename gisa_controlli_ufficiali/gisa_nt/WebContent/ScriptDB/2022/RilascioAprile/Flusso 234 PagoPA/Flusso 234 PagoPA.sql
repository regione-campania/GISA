-- Creazione tabella delle notifiche

CREATE TABLE pagopa_sanzioni_pagatori_notifiche (id serial primary key, id_sanzione integer,  id_pagatore integer, entered timestamp without time zone default now(), enteredby integer, modified timestamp without time zone, modifiedby integer, trashed_date timestamp without time zone, tipo_notifica text, data_notifica text, data_scadenza_prorogata boolean default false, notifica_aggiornata boolean default false, note_hd text);

DROP FUNCTION public.pagopa_genera_pagamento(
    _idsanzione integer,
    _idpagatore integer,
    _importo text,
    _datascadenza text,
    _tipopagamento text,
    _tiporiduzione text,
    _tiponotifica text,
    _datanotifica text,
    _indice integer,
    _idutente integer);

    
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
--_identificativotipodovuto = '2040'; --era 1001
SELECT l.codice_tariffa into _identificativotipodovuto from norme_violate_sanzioni n join lookup_norme l on l.code = n.id_norma where n.idticket = _idsanzione;

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
  
  
alter table pagopa_pagamenti add column codice_avviso text;

alter table pagopa_pagamenti drop column data_notifica;
alter table pagopa_pagamenti drop column tipo_notifica;


create table pagopa_storico_operazioni_automatiche (id serial primary key, entered timestamp without time zone default now(), id_sanzione integer, id_pagamento integer, vecchia_data_scadenza text, nuova_data_scadenza text, messaggio text);

--test
select 
p.id_sanzione,
tnot.tipo_notifica,
tnot.data_notifica,
tnot.notifica_aggiornata,
tnot.data_scadenza_prorogata,
onot.tipo_notifica,
onot.data_notifica,
onot.notifica_aggiornata,
onot.data_scadenza_prorogata,
p.data_scadenza

from pagopa_pagamenti p 
left join pagopa_mapping_sanzioni_pagatori tmap on tmap.id_sanzione = p.id_sanzione and tmap.trashed_date is null and tmap.trasgressore_obbligato = 'T'
left join pagopa_mapping_sanzioni_pagatori omap on omap.id_sanzione = p.id_sanzione and omap.trashed_date is null and omap.trasgressore_obbligato = 'O'
left join pagopa_anagrafica_pagatori t on t.id = tmap.id_pagatore
left join pagopa_anagrafica_pagatori o on o.id = omap.id_pagatore 
left join pagopa_sanzioni_pagatori_notifiche tnot on tnot.id_sanzione = p.id_sanzione and tnot.id_pagatore = t.id and tnot.trashed_date is null
left join pagopa_sanzioni_pagatori_notifiche onot on onot.id_sanzione = p.id_sanzione and onot.id_pagatore = o.id and onot.trashed_date is null

where p.trashed_date is null
and p.id_sanzione in (select ticketid from ticket where id_controllo_ufficiale = '1710644')  
order by p.id_sanzione asc, p.id asc

-- Dbi verifica cancellazione controllo

-- Function: public.is_cu_nc_sa_cancellabile_pagopa(integer, integer, integer)

-- DROP FUNCTION public.is_cu_nc_sa_cancellabile_pagopa(integer, integer, integer);

CREATE OR REPLACE FUNCTION public.is_cu_nc_sa_cancellabile_pagopa(
    _idcu integer,
    _idnc integer,
    _idsa integer)
  RETURNS text AS
$BODY$
DECLARE
	messaggio text;
BEGIN

messaggio := '';

select string_agg(a.errore,' ') into messaggio from (
select distinct
string_agg(distinct 'Controllo ufficiale: '||cu.ticketid||' Non Conformita: ' || nc.identificativo ||' Sanzione: ' ||  sa.identificativo ||'; ', '') as errore
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
ALTER FUNCTION public.is_cu_nc_sa_cancellabile_pagopa(integer, integer, integer)
  OWNER TO postgres;
  
  
  alter table pagopa_pagamenti add column aggiornato_con_pagopa boolean default false;

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
  
  -- Post collaudo esterno 11/2
  
  -- Function: public.pagopa_cerca_pagatore(text, text)

DROP FUNCTION public.pagopa_cerca_pagatore(text, text);

CREATE OR REPLACE FUNCTION public.pagopa_cerca_pagatore(
    IN _nome text,
    IN _piva text)
  RETURNS TABLE(origine text, tipo_pagatore text, piva_cf text, ragione_sociale_nominativo text, indirizzo text, civico text, cap text, comune text, cod_provincia text, nazione text, domicilio_digitale text, telefono text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY
select 
distinct 'Anagrafe Pagatori' as origine, 
COALESCE(p.tipo_pagatore, '') as tipo_pagatore, 
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
'' as tipo_pagatore, 
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
and ((_piva <> '' and r.partita_iva ilike '%'||_piva||'%') or (_piva = ''))

order by 1 asc, 4 asc; 
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.pagopa_cerca_pagatore(text, text)
  OWNER TO postgres;

  
