DROP FUNCTION public.pagopa_genera_pagamento(integer, integer, text, text, text, text, integer, integer);

 
  
  -- Dbi estrazione avvisi in scadenza
  
  
CREATE OR REPLACE FUNCTION public.pagopa_get_avvisi_in_scadenza()
  RETURNS TABLE(id_pagamento integer, id_sanzione integer, data_scadenza text, importo_singolo_versamento text, identificativo_univoco_versamento text, stato_pagamento text, tipo_riduzione text, tipo_notifica text, data_notifica text, notifica_aggiornata text
) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
FOR 

id_pagamento, id_sanzione, data_scadenza, importo_singolo_versamento, identificativo_univoco_versamento, stato_pagamento, tipo_riduzione, tipo_notifica, data_notifica, notifica_aggiornata

in

select 
p.id, p.id_sanzione, p.data_scadenza, p.importo_singolo_versamento, p.identificativo_univoco_versamento, p.stato_pagamento, p.tipo_riduzione, n.tipo_notifica, n.data_notifica, n.notifica_aggiornata
from pagopa_pagamenti p join pagopa_sanzioni_pagatori_notifiche n on p.id_sanzione = n.id_sanzione and p.id_pagatore = n.id_pagatore where p.trashed_date is null and n.trashed_date is null and p.tipo_pagamento = 'PV' and p.stato_pagamento = 'PAGAMENTO NON INIZIATO' and p.data_scadenza::timestamp without time zone <= now() and n.notifica_aggiornata is not true order by p.id_sanzione asc, p.identificativo_univoco_versamento asc

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;

  
  -- Dbi estrazione avvisi scaduti
  
  
-- Function: public.pagopa_get_avvisi_scaduti()

-- DROP FUNCTION public.pagopa_get_avvisi_scaduti();

CREATE OR REPLACE FUNCTION public.pagopa_get_avvisi_scaduti()
  RETURNS TABLE(id_pagamento integer, id_sanzione integer, data_scadenza text, importo_singolo_versamento text, identificativo_univoco_versamento text, stato_pagamento text, tipo_riduzione text, tipo_notifica text, data_notifica text, notifica_aggiornata text) AS
$BODY$
DECLARE
r RECORD;   
BEGIN
FOR

id_pagamento, id_sanzione, data_scadenza, importo_singolo_versamento, identificativo_univoco_versamento, stato_pagamento, tipo_riduzione, tipo_notifica, data_notifica, notifica_aggiornata

in

select
p.id, p.id_sanzione, p.data_scadenza, p.importo_singolo_versamento, p.identificativo_univoco_versamento, p.stato_pagamento, p.tipo_riduzione, n.tipo_notifica, n.data_notifica, n.notifica_aggiornata
from pagopa_pagamenti p join pagopa_sanzioni_pagatori_notifiche n on p.id_sanzione = n.id_sanzione and p.id_pagatore = n.id_pagatore where p.trashed_date is null and n.trashed_date is null and p.tipo_pagamento = 'PV' and p.stato_pagamento = 'PAGAMENTO NON INIZIATO' and p.data_scadenza::timestamp without time zone < now()
and ((n.tipo_notifica='R' and n.notifica_aggiornata is NOT true))

order by p.id_sanzione asc, p.identificativo_univoco_versamento asc

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.pagopa_get_avvisi_scaduti()
  OWNER TO postgres;

  
  
CREATE OR REPLACE FUNCTION public.pagopa_get_avvisi_non_pagati()
  RETURNS TABLE(id_pagamento integer, id_sanzione integer, data_scadenza text, importo_singolo_versamento text, identificativo_univoco_versamento text, stato_pagamento text, tipo_riduzione text, tipo_notifica text, data_notifica text, notifica_aggiornata text
) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
FOR 

id_pagamento, id_sanzione, data_scadenza, importo_singolo_versamento, identificativo_univoco_versamento, stato_pagamento, tipo_riduzione, tipo_notifica, data_notifica, notifica_aggiornata

in

select 
p.id, p.id_sanzione, p.data_scadenza, p.importo_singolo_versamento, p.identificativo_univoco_versamento, p.stato_pagamento, p.tipo_riduzione, n.tipo_notifica, n.data_notifica, n.notifica_aggiornata
from pagopa_pagamenti p 
join pagopa_sanzioni_pagatori_notifiche n on p.id_sanzione = n.id_sanzione and p.id_pagatore = n.id_pagatore 
where p.trashed_date is null and n.trashed_date is null and p.stato_pagamento = 'PAGAMENTO NON INIZIATO' 

order by p.id_sanzione asc, p.identificativo_univoco_versamento asc

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
  
  -- Function: public.pagopa_genera_pagamento(integer, integer, text, text, text, text, integer, integer)

-- DROP FUNCTION public.pagopa_genera_pagamento(integer, integer, text, text, text, text, integer, integer);

 -- Function: public.pagopa_genera_pagamento(integer, integer, text, text, text, text, integer, integer)

-- DROP FUNCTION public.pagopa_genera_pagamento(integer, integer, text, text, text, text, integer, integer);

alter table pagopa_pagamenti add column numero_rate integer;

CREATE OR REPLACE FUNCTION public.pagopa_genera_pagamento(
    _idsanzione integer,
    _idpagatore integer,
    _importo text,
    _datascadenza text,
    _tipopagamento text,
    _tiporiduzione text,
    _indice integer,
    _numerorate integer,
    _idutente integer)
  RETURNS text AS
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

select concat('PV N. ', t.tipo_richiesta, ' - ', CASE WHEN nucleo.in_dpat THEN 'ASL '::text || asl.description::text ELSE nucleo.description::text END, case when _tipopagamento = 'PV' or _numerorate = 1 then ' - RATA UNICA' when _tipopagamento = 'NO' then ' - RATA '||_indice||' di '||_numerorate else '' end) into _causale from ticket t
left join ticket controllo on controllo.ticketid = t.id_controllo_ufficiale::integer
LEFT JOIN lookup_site_id asl ON asl.code = controllo.site_id
LEFT JOIN nucleo_ispettivo_mv mv_nucleo ON mv_nucleo.id_controllo_ufficiale = controllo.ticketid
LEFT JOIN lookup_qualifiche nucleo ON nucleo.description::text = mv_nucleo.nucleo_ispettivo::text
where t.ticketid = _idsanzione;

select pagopa_genera_identificativo_dovuto into _iud from pagopa_genera_identificativo_dovuto(_idsanzione);

insert into pagopa_pagamenti(id_sanzione, id_pagatore, data_scadenza, importo_singolo_versamento, entered_by, indice, numero_rate, tipo_pagamento, tipo_riduzione, tipo_versamento, identificativo_univoco_dovuto, identificativo_tipo_dovuto, causale_versamento, dati_specifici_riscossione, stato_pagamento)
values (_idsanzione, _idpagatore, _datascadenza, _importo, _idutente, _indice, _numerorate,_tipopagamento, _tiporiduzione, _tipoversamento, _iud, _identificativotipodovuto, _causale, _datispecifici, 'PAGAMENTO NON INIZIATO') returning id into idInserito;

return '{"idInserito" : '||idInserito||', "IUD" : "'||_iud||'"}';

END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;



