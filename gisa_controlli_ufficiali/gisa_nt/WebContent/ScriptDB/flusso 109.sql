alter table apicoltura_movimentazioni add column denominazione_apicoltore text;
alter table  apicoltura_movimentazioni  add column  cf_partita_iva_apicoltore text;
alter table  apicoltura_movimentazioni  add column  recupero_materiale_biologico boolean;
update lookup_apicoltura_tipo_movimentazione set description = 'IMPOLLINAZIONE' where code = 3;
insert into lookup_apicoltura_tipo_movimentazione(code, description, enabled) values(4, 'COMPRAVENDITA API REGINE', true);

CREATE SEQUENCE public.apicoltura_movimentazioni_api_regine_log_import_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 999999999
  START 1
  CACHE 1;
ALTER TABLE public.apicoltura_movimentazioni_api_regine_log_import
  OWNER TO postgres;

  
alter table apicoltura_movimentazioni_api_regine_log_import add column id integer not null DEFAULT nextval('apicoltura_movimentazioni_api_regine_log_import_id_seq'::regclass);
alter table apicoltura_movimentazioni_api_regine_log_import add column id_operatore integer;
alter table apicoltura_movimentazioni_api_regine_log_import add column cod_documento text;
alter table apicoltura_movimentazioni_api_regine_log_import add column esito boolean;
alter table apicoltura_movimentazioni_api_regine_log_import add column errore_insert text;
alter table apicoltura_movimentazioni_api_regine_log_import add column errore_parsing_file text;
alter table apicoltura_movimentazioni_api_regine_log_import add constraint apicoltura_movimentazioni_api_regine_log_import_pkey PRIMARY KEY (id);

CREATE TABLE public.ws_endpoint
(
  id integer NOT NULL ,
  nome text,
  CONSTRAINT ws_endpoint_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.ws_endpoint
  OWNER TO postgres;

CREATE TABLE public.ws_info_web_service
(
  id integer NOT NULL ,
  id_endpoint integer not null,
  id_azione integer,
  id_servizio integer,
  id_oggetto integer,
  CONSTRAINT ws_info_web_service_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.ws_info_web_service
  OWNER TO postgres;

  CREATE TABLE public.ws_endpoint_info
(
  id integer NOT NULL ,
  id_endpoint integer NOT NULL ,
  ambiente text,
  url text,
  username text,
  password text,
  ruolo text,
  ruolo_codice text,
  ruolo_valore_codice text,
  xmlns text,
  CONSTRAINT ws_endpoint_info_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.ws_endpoint_info
  OWNER TO postgres;

CREATE TABLE public.ws_oggetto_campi
(
  id integer NOT NULL ,
  id_oggetto integer NOT NULL ,
  campo text,
  CONSTRAINT ws_oggetto_campi_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.ws_oggetto_campi
  OWNER TO postgres;

CREATE TABLE public.ws_azioni
(
  id integer NOT NULL ,
  nome text,
  CONSTRAINT ws_azioni_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.ws_azioni
  OWNER TO postgres;

  CREATE TABLE public.ws_oggetto
(
  id integer NOT NULL ,
  nome text,
  CONSTRAINT ws_oggetto_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.ws_oggetto
  OWNER TO postgres;

CREATE TABLE public.ws_servizi
(
  id integer NOT NULL ,
  nome text,
  CONSTRAINT ws_servizi_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.ws_servizi
  OWNER TO postgres;

insert into ws_endpoint(id,nome) values(1, 'API');
insert into ws_endpoint(id,nome) values(2, 'API REGINE');
insert into ws_endpoint(id,nome) values(3, 'ACQUACOLTURA');

insert into ws_azioni(id,nome) values(1, 'GET');
insert into ws_azioni(id,nome) values(2, 'GETBYPK');
insert into ws_azioni(id,nome) values(3, 'INSERT');
insert into ws_azioni(id,nome) values(4, 'UPDATE');
insert into ws_azioni(id,nome) values(5, 'DELETE');

insert into ws_oggetto(id,nome) values(1,'ApiMovimentazioneApiRegineTO');

insert into ws_servizi(id,nome) values(1,'insertApiMovimentazioneApiRegine');

insert into ws_info_web_service(id,id_endpoint, id_azione, id_oggetto,id_Servizio) values(1, 2, 3, 1,1);

insert into ws_endpoint_info(id, id_endpoint, ambiente, url, username, password, ruolo, ruolo_codice, ruolo_valore_codice, xmlns) values(1,2,'DEMO', 'http://wstest.izs.it/j_test_apicoltura/ws/apicoltura/apimovimentazione/regine', 'campania_BDA', 'Izsm102018', null,'REG', '150', 'http://ws.apimovimentazione.apicoltura.izs.it/');
insert into ws_endpoint_info(id, id_endpoint, ambiente, url, username, password, ruolo, ruolo_codice, ruolo_valore_codice, xmlns) values(2,2,'UFFICIALE', 'http://ws.izs.it/j6_apicoltura/ws/apicoltura/apimovimentazione/regine', 'campania_BDA', 'campania', null,'REG', '150', 'http://ws.apimovimentazione.apicoltura.izs.it/' );
		
insert into ws_oggetto_campi(id,id_oggetto,campo) values(1,1,'apiattAziendaCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(2,1,'dtUscita');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(3,1,'destApiattAziendaCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(4,1,'numApiRegine');


CREATE TYPE ws_endpoint_info_object AS
   (id integer  ,
  id_endpoint integer ,
  ambiente text,
  url text,
  username text,
  password text,
  ruolo text,
  ruolo_codice text,
  ruolo_valore_codice text,
  xmlns text);


  CREATE OR REPLACE FUNCTION ws_get_endpoint_info(
    IN id_endpoint_in integer,
    IN ambiente_in text)
  RETURNS SETOF ws_endpoint_info_object AS
$BODY$SELECT id, id_endpoint, ambiente , url , username , password, ruolo , ruolo_codice , ruolo_valore_codice, xmlns  FROM ws_endpoint_info e  
                WHERE e.id_endpoint = id_endpoint_in and ambiente = ambiente_in$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION ws_get_endpoint_info(integer,text)
  OWNER TO postgres;



CREATE TYPE ws_servizio_info_object AS
   (nome_servizio text, 
    nome_oggetto text, 
    nome_campo text);

   CREATE OR REPLACE FUNCTION ws_get_servizio_info(
    IN id_endpoint_in integer,
    IN id_azione_in integer)
  RETURNS SETOF ws_servizio_info_object AS
$BODY$select serv.nome as nome_servizio, ogg.nome as nome_oggetto, ogg_campi.campo as nome_campo 
from ws_info_web_service serv_info, ws_servizi serv, ws_oggetto ogg, ws_oggetto_campi ogg_campi
where serv_info.id_endpoint = id_endpoint_in and
      serv_info.id_azione = id_azione_in and
      serv_info.id_servizio = serv.id and
      serv_info.id_oggetto = ogg.id and 
      ogg.id = ogg_campi.id_oggetto$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION ws_get_servizio_info(integer,integer)
  OWNER TO postgres;




  CREATE OR REPLACE FUNCTION ws_get_envelope(xmlns_input text, ruolo_in text,username_in text, password_in text, ruolo_codice_in text, ruolo_valore_codice_in text, nome_servizio_in text, nome_oggetto_in text, campi_envelope_in text)
  RETURNS text AS
$BODY$select '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="'|| xmlns_input ||'"> ' ||
			             '<soapenv:Header>' ||
			             	'<ser:SOAPAutorizzazione> ' || 
						'<ruolo>' || ruolo_in || '</ruolo>' ||
			             	'</ser:SOAPAutorizzazione>' || 
			             	'<ser:SOAPAutenticazioneWS>' || 
			             		'<username>' || username_in || '</username>' ||
			             		'<password>' || password_in || '</password>' ||
			             		'<ruoloCodice>' || ruolo_codice_in || '</ruoloCodice>' ||
			             		'<ruoloValoreCodice>' || ruolo_valore_codice_in || '</ruoloValoreCodice>' || 
			             	'</ser:SOAPAutenticazioneWS>' || 
			             '</soapenv:Header>' || 
			             '<soapenv:Body>' || 
			             	'<ser:' || nome_servizio_in || '>' ||
			             		'<' || nome_oggetto_in || '>' ||
			             			campi_envelope_in ||
			             		'</' || nome_oggetto_in || '>' ||
			             	'</ser:' || nome_servizio_in || '>' || 
			             '</soapenv:Body> ' || 
			             '</soapenv:Envelope>'$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
ALTER FUNCTION ws_get_envelope( text,  text, text,  text,  text,  text,  text,  text,  text)
  OWNER TO postgres;








      
      

