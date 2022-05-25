--CONTROLLI
insert into permission (category_id, permission, permission_view, permission_add,description) values (94, 'global-search-acquacoltura', true, true, 'Gestione Cu da inviare per acquacoltura');
--da lanciare solo se modulo da rilasciare
insert into role_permission (role_id, permission_id, role_view,role_add) values (1, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (32, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (44, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (40, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (221, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (222, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (42, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (21, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (45, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (97, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (43, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (19, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (46, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (98, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (31, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (56, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (41, (select permission_id from permission where permission ilike 'global-search-acquacoltura'), true,true);


--Funzione Ricerca CU da inviare
-- DROP FUNCTION public.lista_controlli_acquacoltura(integer, integer, text, text, text);
    
CREATE OR REPLACE FUNCTION public.lista_controlli_acquacoltura(
    IN _idcontrollo integer,
    IN _idasl integer,
    IN _datainizio text,
    IN _datafine text)
  RETURNS TABLE(id_controllo integer, id_asl integer, piano_monitoraggio text, data_controllo timestamp without time zone) AS
$BODY$
DECLARE
r RECORD;	

BEGIN

FOR 

id_controllo, id_asl, piano_monitoraggio, data_controllo

in


select distinct t.ticketid, t.site_id, piano.description, t.assigned_date
from ticket t
left join tipocontrolloufficialeimprese tcu on tcu.idcontrollo = t.ticketid
left join lookup_piano_monitoraggio piano on piano.code = tcu.pianomonitoraggio
left join acquacoltura_dati_controlli_bdn d on d.id_controllo = t.ticketid and d.enabled
left join organization org on org.org_id = t.org_id
where t.tipologia = 3 and 
org.tipologia = 2 and
piano.codice_esame = 'PMACQ' and 
(( _idcontrollo>0 and t.ticketid = _idcontrollo) or _idcontrollo = -1) and
((_idasl>0 and t.site_id = _idasl) or -1 = _idasl) and
((_datainizio is not null and _datainizio <>'' and t.assigned_date >= _datainizio::timestamp without time zone) or _datainizio = '') and 
((_datafine is not null and _datafine <>'' and t.assigned_date <= _datafine::timestamp without time zone) or _datafine = '') and
t.trashed_date is null and
org.trashed_date is null and
t.closed is not null and
t.status_id = 2
order by t.assigned_date desc

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.lista_controlli_acquacoltura(integer, integer, text, text)
  OWNER TO postgres;

CREATE SEQUENCE acquacoltura_dati_controlli_bdn_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 16
  CACHE 1;
ALTER TABLE public.acquacoltura_dati_controlli_bdn_id_seq
  OWNER TO postgres;

--drop table acquacoltura_dati_controlli_bdn
CREATE TABLE acquacoltura_dati_controlli_bdn
(
  id integer NOT NULL DEFAULT nextval('acquacoltura_dati_controlli_bdn_id_seq'::regclass),
  id_controllo integer,
  asl_codice text,
  azi_codice text,
  prop_id_fiscale character varying(16),
  gsp_codice integer,
  crit_codice integer,
  data_controllo text,
  enabled boolean DEFAULT true,
  entered timestamp without time zone DEFAULT now(),
  enteredby integer,
  data_invio timestamp without time zone,
  data_cancellazione timestamp without time zone,
  id_bdn text,
  CONSTRAINT acquacoltura_dati_controlli_bdn_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.acquacoltura_dati_controlli_bdn
  OWNER TO postgres;
GRANT ALL ON TABLE public.acquacoltura_dati_controlli_bdn TO postgres;


-- DROP FUNCTION public.is_controllo_amr(integer);

CREATE OR REPLACE FUNCTION public.is_controllo_acquacoltura(_idcontrollo integer)
  RETURNS boolean AS
$BODY$
DECLARE
	_ticketId integer;
	esito boolean;
BEGIN

_ticketId := -1;
esito:= false;

_ticketId := (select distinct id_controllo from lista_controlli_acquacoltura(_idcontrollo, -1, '', '') );

if (_ticketId) > 0 THEN
esito = true;
END IF;

 RETURN esito;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.is_controllo_acquacoltura(integer)
  OWNER TO postgres;



-- DROP FUNCTION public.get_chiamata_ws_invio_dati_cu_acquacoltura(integer,text,text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_invio_dati_cu_acquacoltura(_idcu integer, _password text, _username text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat(
'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://acquacoltura.izs.it/services">
    <soapenv:Header>
	<ser:SOAPAutorizzazione>
		<ruolo>regione</ruolo>
	</ser:SOAPAutorizzazione>
        <ser:SOAPAutenticazione>
		<username>' || _username || '</username>
		<password>' || _password || '</password>
        </ser:SOAPAutenticazione>
    </soapenv:Header>
    <soapenv:Body>
       <ser:insertControllo>',
'<controlloTO>
<ctrlId>0</ctrlId>
<aslCodice>' , dati_acquacoltura.asl_codice , '</aslCodice> 
<aziCodice>' , dati_acquacoltura.azi_codice , '</aziCodice> 
<propIdFiscale>' , dati_acquacoltura.prop_id_fiscale , '</propIdFiscale> 
<gspCodice>' , specie.alt_short_description , '</gspCodice> 
<critCodice>' , crit.short_description , '</critCodice> 
<dataControllo>' , dati_acquacoltura.data_controllo , '</dataControllo> 
</controlloTO>',
'</ser:insertControllo>
</soapenv:Body>
</soapenv:Envelope>') into ret
from acquacoltura_dati_controlli_bdn dati_acquacoltura
left join lookup_specie_allevata specie on specie.code = dati_acquacoltura.gsp_codice
left join lookup_codici_criterio crit on crit.code = dati_acquacoltura.crit_codice
where dati_acquacoltura.id_controllo = _idcu   and dati_acquacoltura.enabled;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_invio_dati_cu_acquacoltura(integer,text,text)
  OWNER TO postgres;





-- DROP FUNCTION public.get_chiamata_ws_invio_dati_cu_acquacoltura(integer,text,text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_update_dati_cu_acquacoltura(_idcu integer, _password text, _username text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat(
'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://acquacoltura.izs.it/services">
    <soapenv:Header>
	<ser:SOAPAutorizzazione>
		<ruolo>regione</ruolo>
	</ser:SOAPAutorizzazione>
        <ser:SOAPAutenticazione>
		<username>' || _username || '</username>
		<password>' || _password || '</password>
        </ser:SOAPAutenticazione>
    </soapenv:Header>
    <soapenv:Body>
       <ser:updateControllo>',
'<controlloTO>
<ctrlId>' , dati_acquacoltura.id_bdn , '</ctrlId>
<aslCodice>' , dati_acquacoltura.asl_codice , '</aslCodice> 
<aziCodice>' , dati_acquacoltura.azi_codice , '</aziCodice> 
<propIdFiscale>' , dati_acquacoltura.prop_id_fiscale , '</propIdFiscale> 
<gspCodice>' , specie.alt_short_description , '</gspCodice> 
<critCodice>' , crit.short_description , '</critCodice> 
<dataControllo>' , dati_acquacoltura.data_controllo , '</dataControllo> 
</controlloTO>',
'</ser:updateControllo>
</soapenv:Body>
</soapenv:Envelope>') into ret
from acquacoltura_dati_controlli_bdn dati_acquacoltura
left join lookup_specie_allevata specie on specie.code = dati_acquacoltura.gsp_codice
left join lookup_codici_criterio crit on crit.code = dati_acquacoltura.crit_codice
where dati_acquacoltura.id_controllo = _idcu   and dati_acquacoltura.enabled;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_update_dati_cu_acquacoltura(integer,text,text)
  OWNER TO postgres;


-- DROP FUNCTION public.get_chiamata_ws_delete_dati_cu_acquacoltura(text, text, integer, text, text, text, text, text, text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_delete_dati_cu_acquacoltura(
    _password text,
    _username text,
    _id_bdn text,
    _asl_codice text,
    _azi_codice text,
    _prop_id_fiscale text,
    _gsp_codice text,
    _crit_codice text,
    _data_controllo text)
RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN
select 
concat(
'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://acquacoltura.izs.it/services">
    <soapenv:Header>
	<ser:SOAPAutorizzazione>
		<ruolo>regione</ruolo>
	</ser:SOAPAutorizzazione>
        <ser:SOAPAutenticazione>
		<username>' || _username || '</username>
		<password>' || _password || '</password>
        </ser:SOAPAutenticazione>
    </soapenv:Header>
    <soapenv:Body>
       <ser:deleteControllo>',
'<controlloTO>
<ctrlId>' , _id_bdn , '</ctrlId>
<aslCodice>' , _asl_codice , '</aslCodice> 
<aziCodice>' , _azi_codice , '</aziCodice> 
<propIdFiscale>' || _prop_id_fiscale , '</propIdFiscale> 
<gspCodice>' , _gsp_codice , '</gspCodice> 
<critCodice>' , _crit_codice , '</critCodice> 
<dataControllo>' , _data_controllo , '</dataControllo> 
</controlloTO>',
'</ser:deleteControllo>
</soapenv:Body>
</soapenv:Envelope>') into ret;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_delete_dati_cu_acquacoltura(text, text, text, text, text, text, text, text, text)
  OWNER TO postgres;



--STATO SANITARIO
insert into permission (category_id, permission, permission_view, permission_add,description) values (94, 'global-search-statosanitario', true, true, 'Gestione Stato Sanitario da inviare per acquacoltura');
--da lanciare solo se modulo da rilasciare
insert into role_permission (role_id, permission_id, role_view,role_add) values (1,  (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (32, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (44, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (40, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (221, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (222, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (42, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (21, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (45, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (97, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (43, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (19, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (46, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (98, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (31, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (56, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);
insert into role_permission (role_id, permission_id, role_view,role_add) values (41, (select permission_id from permission where permission ilike 'global-search-statosanitario'), true,true);

CREATE SEQUENCE acquacoltura_dati_stato_sanitario_bdn_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 16
  CACHE 1;
ALTER TABLE public.acquacoltura_dati_stato_sanitario_bdn_id_seq
  OWNER TO postgres;

  
--drop table acquacoltura_dati_stato_sanitario_bdn
CREATE TABLE acquacoltura_dati_stato_sanitario_bdn
(
  id integer NOT NULL DEFAULT nextval('acquacoltura_dati_stato_sanitario_bdn_id_seq'::regclass),
  id_azienda integer,
  asl_codice text,
  azi_codice text,
  gsp_codice integer,
  mal_codice integer,
  qsa_codice integer,
  dt_inizio_validita text,
  enabled boolean DEFAULT true,
  entered timestamp without time zone DEFAULT now(),
  enteredby integer,
  data_invio timestamp without time zone,
  data_cancellazione timestamp without time zone,
  id_bdn text,
  CONSTRAINT acquacoltura_dati_stato_sanitario_bdn_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.acquacoltura_dati_stato_sanitario_bdn
  OWNER TO postgres;
GRANT ALL ON TABLE public.acquacoltura_dati_stato_sanitario_bdn TO postgres;


-- DROP FUNCTION public.get_chiamata_ws_invio_dati_stato_sanitario(integer,text,text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_invio_dati_stato_sanitario(_idaz integer, _password text, _username text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat(
'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://acquacoltura.izs.it/services">
    <soapenv:Header>
	<ser:SOAPAutorizzazione>
		<ruolo>regione</ruolo>
	</ser:SOAPAutorizzazione>
        <ser:SOAPAutenticazione>
		<username>' || _username || '</username>
		<password>' || _password || '</password>
        </ser:SOAPAutenticazione>
    </soapenv:Header>
    <soapenv:Body>
       <ser:insertStatoSanitario>',
'<statosanitarioTO>
<ssaId>0</ssaId>
<aslCodice>', dati_stato_sanitario.asl_codice , '</aslCodice> 
<aziCodice>' ,dati_stato_sanitario.azi_codice , '</aziCodice> 
<gspCodice>' ,specie.alt_short_description , '</gspCodice> 
<malCodice>' , mal.short_description , '</malCodice> 
<qsaCodice>' , qual.short_description , '</qsaCodice> 
<dtInizioValidita>' , dati_stato_sanitario.dt_inizio_validita , '</dtInizioValidita> 
</statosanitarioTO>',
'</ser:insertStatoSanitario>
</soapenv:Body>
</soapenv:Envelope>') into ret
from acquacoltura_dati_stato_sanitario_bdn dati_stato_sanitario
left join lookup_specie_allevata specie on specie.code = dati_stato_sanitario.gsp_codice
left join lookup_qualifica_sanitaria qual on qual.code = dati_stato_sanitario.qsa_codice
left join lookup_malattie mal on mal.code = dati_stato_sanitario.mal_codice
where dati_stato_sanitario.id_azienda = _idaz and dati_stato_sanitario.enabled;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_invio_dati_stato_sanitario(integer,text,text)
  OWNER TO postgres;





-- DROP FUNCTION public.get_chiamata_ws_invio_dati_cu_acquacoltura(integer,text,text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_update_dati_stato_sanitario(_idaz integer, _password text, _username text)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN

select 
concat(
'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://acquacoltura.izs.it/services">
    <soapenv:Header>
	<ser:SOAPAutorizzazione>
		<ruolo>regione</ruolo>
	</ser:SOAPAutorizzazione>
        <ser:SOAPAutenticazione>
		<username>' || _username || '</username>
		<password>' || _password || '</password>
        </ser:SOAPAutenticazione>
    </soapenv:Header>
    <soapenv:Body>
       <ser:updateStatoSanitario>',
'<statosanitarioTO>
<ctrlId>' , dati_stato_sanitario.id_bdn || '</ctrlId>
<aslCodice>' , dati_stato_sanitario.asl_codice , '</aslCodice> 
<aziCodice>' , dati_stato_sanitario.azi_codice , '</aziCodice> 
<gspCodice>' , specie.alt_short_description , '</gspCodice> 
<malCodice>' , mal.short_description , '</malCodice> 
<qsaCodice>' , qual.short_description , '</qsaCodice>  
<dtInizioValidita>' , dati_stato_sanitario.dt_inizio_validita , '</dtInizioValidita> 
</statosanitarioTO>',
'</ser:updateStatoSanitario>
</soapenv:Body>
</soapenv:Envelope>') into ret
from acquacoltura_dati_stato_sanitario_bdn dati_stato_sanitario
left join lookup_specie_allevata specie on specie.code = dati_stato_sanitario.gsp_codice
left join lookup_qualifica_sanitaria qual on qual.code = dati_stato_sanitario.qsa_codice
left join lookup_malattie mal on mal.code = dati_stato_sanitario.mal_codice
where dati_stato_sanitario.id_azienda = _idaz   and dati_stato_sanitario.enabled;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_update_dati_stato_sanitario(integer,text,text)
  OWNER TO postgres;


-- DROP FUNCTION public.get_chiamata_ws_delete_dati_stato_sanitario(text, text, integer, text, text, text, text, text, text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_delete_dati_stato_sanitario(
    _password text,
    _username text,
    _id_bdn text,
    _asl_codice text,
    _azi_codice text,
    _gsp_codice text,
    _mal_codice text,
    _qsa_codice text,
    _dt_inizio_validita text)
RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN
select 
concat(
'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://acquacoltura.izs.it/services">
    <soapenv:Header>
	<ser:SOAPAutorizzazione>
		<ruolo>regione</ruolo>
	</ser:SOAPAutorizzazione>
        <ser:SOAPAutenticazione>
		<username>' || _username || '</username>
		<password>' || _password || '</password>
        </ser:SOAPAutenticazione>
    </soapenv:Header>
    <soapenv:Body>
       <ser:deleteStatoSanitario>',
'<statosanitarioTO>
<ctrlId>' || _id_bdn || '</ctrlId>
<aslCodice>' , _asl_codice , '</aslCodice> 
<aziCodice>' , _azi_codice , '</aziCodice> 
<gspCodice>' , _gsp_codice , '</gspCodice> 
<malCodice>' , _mal_codice , '</malCodice> 
<qsaCodice>' , _qsa_codice , '</qsaCodice> 
<dtInizioValidita>' , _dt_inizio_validita , '</dtInizioValidita> 
</statosanitarioTO>',
'</ser:deleteStatoSanitario>
</soapenv:Body>
</soapenv:Envelope>') into ret;


 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_delete_dati_stato_sanitario(text, text, text, text, text, text, text, text, text)
  OWNER TO postgres;


CREATE SEQUENCE lookup_malattie_code_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 16
  CACHE 1;
ALTER TABLE public.lookup_malattie_code_seq
  OWNER TO postgres;

  
CREATE TABLE public.lookup_malattie
(
  code integer NOT NULL DEFAULT nextval('lookup_malattie_code_seq'::regclass),
  description character varying(300) NOT NULL,
  short_description character varying(300),
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_malattie_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_malattie
  OWNER TO postgres;

insert into lookup_malattie (short_description,description,code,level,enabled,default_item) values('ISA','ANEMIA INFETTIVA DEL SALMONE (ISA)',5,1,true,false);  
insert into lookup_malattie (short_description,description,code,level,enabled,default_item) values('IBE', 'INFEZIONE DA BONAMIA EXITIOSA',7,2,true,false);
insert into lookup_malattie (short_description,description,code,level,enabled,default_item) values('IBO', 'INFEZIONE DA BONAMIA OSTREAE', 17,3,true,false);
insert into lookup_malattie (short_description,description,code,level,enabled,default_item) values('IMR','INFEZIONE DA MARTEILIA REFRINGENS',6,4,true,false); 
insert into lookup_malattie (short_description,description,code,level,enabled,default_item) values('IMM','INFEZIONE DA MICROCYTOS MACKINI',13,5,true,false);        
insert into lookup_malattie (short_description,description,code,level,enabled,default_item) values('IPM','INFEZIONE DA PERKINSUS MARINUS',12,6,true,false);    
insert into lookup_malattie (short_description,description,code,level,enabled,default_item) values('MPB','MALATTIA DEI PUNTI BIANCHI',9,7,true,false);       
insert into lookup_malattie (short_description,description,code,level,enabled,default_item) values('MTG','MALATTIA DELLA TESTA GIALLA',15,8,true,false);       
insert into lookup_malattie (short_description,description,code,level,enabled,default_item) values('NEE','NECROSI EMATOPOIETICA EPIZOOTICA',10,9,true,false);
insert into lookup_malattie (short_description,description,code,level,enabled,default_item) values('IHN','NECROSI EMATOPOIETICA INFETTIVA (IHN)',3,10,true,false); 
insert into lookup_malattie (short_description,description,code,level,enabled,default_item) values('VHS','SETTICEMIA EMORRAGICA VIRALE (VHS)',2,11,true,false);  
insert into lookup_malattie (short_description,description,code,level,enabled,default_item) values('STA','SINDROME DI TAURA',14,12,true,false);       
insert into lookup_malattie (short_description,description,code,level,enabled,default_item) values('SUE','SINDROME ULCERATIVA EPIZOOTICA',11,13,true,false);       
insert into lookup_malattie (short_description,description,code,level,enabled,default_item) values('VPC','VIREMIA PRIMAVERILE DELLE CARPE',1,14,true,false);
insert into lookup_malattie (short_description,description,code,level,enabled,default_item) values('KHV','VIRUS ERPETICO (KHV)',4,15,true,false);    

CREATE SEQUENCE lookup_qualifica_sanitaria_code_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 16
  CACHE 1;
ALTER TABLE public.lookup_qualifica_sanitaria_code_seq
  OWNER TO postgres;

  
CREATE TABLE public.lookup_qualifica_sanitaria
(
  code integer NOT NULL DEFAULT nextval('lookup_qualifica_sanitaria_code_seq'::regclass),
  description character varying(300) NOT NULL,
  short_description character varying(300),
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_qualifica_sanitaria_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_qualifica_sanitaria
  OWNER TO postgres;

  

insert into lookup_qualifica_sanitaria (short_description,description,code,level,enabled,default_item) values('IND','INDENNE (CATEGORIA I)',1,1,true,false);
insert into lookup_qualifica_sanitaria (short_description,description,code,level,enabled,default_item) values('INS','INDENNE PER SPECIE NON SENSIBILI (CATEGORIA I)',0,2,true,false);
insert into lookup_qualifica_sanitaria (short_description,description,code,level,enabled,default_item) values('INF','INFETTO (CATEGORIA V)',5,3,true,false);
insert into lookup_qualifica_sanitaria (short_description,description,code,level,enabled,default_item) values('ERA','PROGRAMMA DI ERADICAZIONE (CATEGORIA IV)',4,4,true,false);
insert into lookup_qualifica_sanitaria (short_description,description,code,level,enabled,default_item) values('PRS','PROGRAMMA DI SORVEGLIANZA (CATEGORIA II)',2,5,true,false);
insert into lookup_qualifica_sanitaria (short_description,description,code,level,enabled,default_item) values('SIN','STATO INDETERMINATO (CATEGORIA III)',3,6,true,false);




update lookup_specie_allevata  set codice_categoria = 9 where code = 160;
update lookup_piano_monitoraggio set codice_esame = 'PMACQ'   where code in (7595,6378,6101);

--CRIT CODICE
CREATE SEQUENCE lookup_codici_criterio_code_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 16
  CACHE 1;
ALTER TABLE public.lookup_codici_criterio_code_seq
  OWNER TO postgres;

  
CREATE TABLE public.lookup_codici_criterio
(
  code integer NOT NULL DEFAULT nextval('lookup_codici_criterio_code_seq'::regclass),
  description character varying(300) NOT NULL,
  short_description character varying(300),
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_codici_criterio_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.lookup_codici_criterio
  OWNER TO postgres;

delete from lookup_codici_criterio;
insert into lookup_codici_criterio (short_description,description,code,level,enabled,default_item) values('002','CONTROLLO AI SENSI DELL''ART. 6 comma 1, lett. d del decreto',1,1,true,false); 
insert into lookup_codici_criterio (short_description,description,code,level,enabled,default_item) values('003','CONTROLLO UFFICIALE AI SENSI dell''art. 8 d.lvo 148/08',2,2,true,false); 
insert into lookup_codici_criterio (short_description,description,code,level,enabled,default_item) values('004','CAMBIAMENTI DELLA SITUAZIONE AZIENDALE',3,3,true,false); 
insert into lookup_codici_criterio (short_description,description,code,level,enabled,default_item) values('005','AUTOCONTROLLO AI SENSI DELL''ARTICOLO 11 d.lvo 148/08',4,4,true,false); 
insert into lookup_codici_criterio (short_description,description,code,level,enabled,default_item) values('006','CONTROLLO UFFICIALE AI SENSI dell''art. 11 d.lvo 148/08',5,5,true,false); 
insert into lookup_codici_criterio (short_description,description,code,level,enabled,default_item) values('009','ALTRE INDAGINI',7,7,true,false); 
insert into lookup_codici_criterio (short_description,description,code,level,enabled,default_item) values('010','MOVIMENTAZIONE',8,8,true,false); 
insert into lookup_codici_criterio (short_description,description,code,level,enabled,default_item) values('011','VALUTAZIONE DEL RISCHIO',9,9,true,false); 


