insert into ws_endpoint(id,nome) values(4, 'API MOVIMENTAZIONI');

insert into ws_oggetto(id,nome) values(2,'ApimodmovTO');

insert into ws_servizi(id,nome) values(2,'insertApimodmov');

insert into ws_info_web_service(id,id_endpoint, id_azione, id_oggetto,id_Servizio) values(2, 4, 3, 2,2);

insert into ws_endpoint_info(id, id_endpoint, ambiente, url, username, password, ruolo, ruolo_codice, ruolo_valore_codice, xmlns) values(3,4,'DEMO', 'http://wstest.izs.it/j_test_apicoltura/ws/apicoltura/apimovimentazione/modello', 'campania_BDA', 'Izsm102018', null,'REG', '150', 'http://ws.apimovimentazione.apicoltura.izs.it/');
insert into ws_endpoint_info(id, id_endpoint, ambiente, url, username, password, ruolo, ruolo_codice, ruolo_valore_codice, xmlns) values(4,4,'UFFICIALE', 'http://ws.izs.it/j6_apicoltura/ws/apicoltura/apimovimentazione/modello', 'campania_BDA', 'campania', null,'REG', '150', 'http://ws.apimovimentazione.apicoltura.izs.it/' );
		
insert into ws_oggetto_campi(id,id_oggetto,campo) values(5,2,'apiProgressivo');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(6,2,'apiattAziendaCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(7,2,'numModello');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(8,2,'attestazioneSanitaria');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(9,2,'destApiattAziendaCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(10,2,'dtModello');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(11,2,'dtUscita');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(12,2,'apimotuscCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(13,2,'destAziendaComIstat');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(14,2,'destAziendaComProSigla');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(15,2,'destAziendaIndirizzo');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(16,2,'destAziendaIdFiscale');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(17,2,'destAziendaDenominazione');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(18,2,'recuperoMaterialeBiologico');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(19,2,'xmlListaDettaglioModello');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(20,2,'numAlveari');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(21,2,'numSciami');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(22,2,'numPacchiDapi');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(23,2,'numApiRegine');

ALTER TABLE public.apicoltura_movimentazioni_storico ADD COLUMN modified timestamp without time zone;
ALTER TABLE public.apicoltura_movimentazioni_storico ADD COLUMN modified_by integer;
ALTER TABLE public.apicoltura_movimentazioni_storico ADD COLUMN denominazione_apicoltore text;
ALTER TABLE public.apicoltura_movimentazioni_storico ADD COLUMN cf_partita_iva_apicoltore text;
ALTER TABLE public.apicoltura_movimentazioni_storico ADD COLUMN recupero_materiale_biologico boolean;


insert into ws_servizi(id,nome) values(3,'updateApimodmov'); 
insert into ws_info_web_service(id,id_endpoint, id_azione, id_oggetto,id_Servizio) values(3, 4, 4, 2,3);
insert into ws_oggetto_campi(id,id_oggetto,campo) values(24,2,'apimodmovId');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(25,2,'statoRichiestaCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(26,2,'dtStatoRichiesta');


ALTER TABLE public.apicoltura_movimentazioni ADD COLUMN proprietario_destinazione text;
ALTER TABLE public.apicoltura_movimentazioni ADD COLUMN cf_proprietario_destinazione text;

CREATE TABLE apicoltura_lookup_stato_movimentazione_accettazione
(
  code integer NOT NULL ,
  description text,
  enabled boolean,
  level integer,
  default_item boolean,
  CONSTRAINT apicoltura_lookup_stato_movimentazione_accettazione_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);




insert into apicoltura_lookup_stato_movimentazione_accettazione values(1, 'Inviato',true,1,false);
insert into apicoltura_lookup_stato_movimentazione_accettazione values(2, 'Accettato invio',true,2,false);
insert into apicoltura_lookup_stato_movimentazione_accettazione values(3, 'Accettazione non necessaria',true,3,false);

ALTER TABLE public.apicoltura_movimentazioni ADD COLUMN accettazione_destinatario integer;
ALTER TABLE public.apicoltura_movimentazioni_storico ADD COLUMN proprietario_destinazione text;
ALTER TABLE public.apicoltura_movimentazioni_storico ADD COLUMN cf_proprietario_destinazione text;
ALTER TABLE public.apicoltura_movimentazioni_storico ADD COLUMN accettazione_destinatario integer;

ALTER TABLE public.apicoltura_movimentazioni ADD COLUMN utente_accettazione_destinatario integer;
ALTER TABLE public.apicoltura_movimentazioni_storico ADD COLUMN utente_accettazione_destinatario integer;
ALTER TABLE public.apicoltura_movimentazioni ADD COLUMN data_accettazione_destinatario timestamp without time zone;
ALTER TABLE public.apicoltura_movimentazioni_storico ADD COLUMN data_accettazione_destinatario timestamp without time zone;
ALTER TABLE public.apicoltura_movimentazioni ADD COLUMN attestazione_sanitaria boolean;
ALTER TABLE public.apicoltura_movimentazioni_storico ADD COLUMN attestazione_sanitaria boolean;



insert into ws_endpoint(id,nome) values(5, 'API MOVIMENTAZIONI INGRESSO');

insert into ws_oggetto(id,nome) values(3,'ApiingTO');

insert into ws_servizi(id,nome) values(4,'insertApiing');

insert into ws_info_web_service(id,id_endpoint, id_azione, id_oggetto,id_Servizio) values(4, 5, 3, 3,4);

insert into ws_endpoint_info(id, id_endpoint, ambiente, url, username, password, ruolo, ruolo_codice, ruolo_valore_codice, xmlns) values(5,5,'DEMO', 'http://wstest.izs.it/j_test_apicoltura/ws/apicoltura/apimovimentazione/ingresso', 'campania_BDA', 'Izsm102018', null,'REG', '150', 'http://ws.apimovimentazione.apicoltura.izs.it/');
insert into ws_endpoint_info(id, id_endpoint, ambiente, url, username, password, ruolo, ruolo_codice, ruolo_valore_codice, xmlns) values(6,5,'UFFICIALE', 'http://ws.izs.it/j6_apicoltura/ws/apicoltura/apimovimentazione/ingresso', 'campania_BDA', 'campania', null,'REG', '150', 'http://ws.apimovimentazione.apicoltura.izs.it/' );
		

insert into ws_oggetto_campi(id,id_oggetto,campo) values(27,3,'apiProgressivo');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(28,3,'apimodmovDtModello');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(29,3,'apimodmovNumModello');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(30,3,'provApiattDenominazione');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(31,3,'provApiattAziendaCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(32,3,'provApiProgressivo');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(33,3,'dtIngresso');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(34,3,'numAlveari');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(35,3,'numPacchiDapi');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(36,3,'numSciami');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(37,3,'numApiRegine');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(38,3,'apimotingCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(39,3,'numSciami');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(40,3,'apiattDenominazione');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(41,3,'apiattAziendaCodice');

ALTER TABLE public.apicoltura_movimentazioni ADD COLUMN data_ingresso timestamp without time zone;
ALTER TABLE public.apicoltura_movimentazioni_storico ADD COLUMN data_ingresso timestamp without time zone;
ALTER TABLE public.apicoltura_movimentazioni ADD COLUMN accettazione_sincronizzato_bdn boolean;
ALTER TABLE public.apicoltura_movimentazioni_storico ADD COLUMN accettazione_sincronizzato_bdn boolean;
ALTER TABLE public.apicoltura_movimentazioni ADD COLUMN accettazione_sincronizzata_da integer;
ALTER TABLE public.apicoltura_movimentazioni_storico ADD COLUMN accettazione_sincronizzata_da integer;
ALTER TABLE public.apicoltura_movimentazioni ADD COLUMN accettazione_data_sincronizzazione timestamp without time zone;
ALTER TABLE public.apicoltura_movimentazioni_storico ADD COLUMN accettazione_data_sincronizzazione timestamp without time zone;

insert into ws_oggetto_campi(id,id_oggetto,campo) values(42,2,'destApiProgressivo');
           
           
insert into ws_endpoint(id,nome) values(6, 'API MOVIMENTAZIONI DETTAGLIO MODELLO');
insert into ws_oggetto(id,nome) values(5,'ApidetmodTO');
insert into ws_servizi(id,nome) values(6,'updateApidetmod');

insert into ws_info_web_service(id,id_endpoint, id_azione, id_oggetto,id_Servizio) values(6, 6, 4, 5,6);

insert into ws_endpoint_info(id, id_endpoint, ambiente, url, username, password, ruolo, ruolo_codice, ruolo_valore_codice, xmlns) values(7,6,'DEMO',      'http://wstest.izs.it/j_test_apicoltura/ws/apicoltura/apimovimentazione/dettaglio', 'campania_BDA', 'Izsm102018', null,'REG', '150', 'http://ws.apimovimentazione.apicoltura.izs.it/');
insert into ws_endpoint_info(id, id_endpoint, ambiente, url, username, password, ruolo, ruolo_codice, ruolo_valore_codice, xmlns) values(8,6,'UFFICIALE', 'http://ws.izs.it/j6_apicoltura/ws/apicoltura/apimovimentazione/dettaglio',         'campania_BDA', 'campania', null,'REG', '150', 'http://ws.apimovimentazione.apicoltura.izs.it/' );


insert into ws_oggetto_campi(id,id_oggetto,campo) values(43,5,'apimodmovNumModello');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(44,5,'apimodmovApiProgressivo');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(45,5,'apimodmovProvApiattCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(46,5,'apimodmovDtModello');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(47,5,'apimodmovDtUscita');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(48,5,'apidetmodId');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(49,5,'apimodmovId');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(50,5,'destApiProgressivo');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(51,5,'destApiComIstat');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(52,5,'destApiProSigla');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(53,5,'destApiComDescrizione');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(54,5,'numSciami');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(55,5,'numAlveari');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(56,5,'numPacchiDapi');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(57,5,'numApiRegine');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(58,5,'destApiIndirizzo');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(59,5,'destApiLatitudine');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(60,5,'destApiLongitudine');




insert into ws_oggetto(id,nome) values(6,null);
insert into ws_servizi(id,nome) values(7,'searchApimodmovByPk');
insert into ws_info_web_service(id,id_endpoint, id_azione, id_oggetto,id_Servizio) values(8, 4, 2, 6,7);
insert into ws_oggetto_campi(id,id_oggetto,campo) values(62,6,'apimodmovId');


CREATE OR REPLACE FUNCTION public.ws_get_envelope(
    xmlns_input text,
    ruolo_in text,
    username_in text,
    password_in text,
    ruolo_codice_in text,
    ruolo_valore_codice_in text,
    nome_servizio_in text,
    nome_oggetto_in text,
    campi_envelope_in text)
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


                                        (select case when nome_oggetto_in is null then ''
                                                else '<' || nome_oggetto_in || '>'
                                                end)
			             	
			             		 ||
			             			campi_envelope_in ||
			             		(select case when nome_oggetto_in is null then ''
                                                else '</' || nome_oggetto_in || '>'
                                                end) ||
			             	'</ser:' || nome_servizio_in || '>' || 
			             '</soapenv:Body> ' || 
			             '</soapenv:Envelope>'$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
ALTER FUNCTION public.ws_get_envelope(text, text, text, text, text, text, text, text, text)
  OWNER TO postgres;



       
            insert into ws_oggetto_campi(id,id_oggetto,campo) values(63,5,'apimodmovApiApiattCodice');
 insert into ws_oggetto_campi(id,id_oggetto,campo) values(66,5,'apimodmovDestApiattCodice');


            


            

insert into ws_oggetto(id,nome) values(7,'ApimodmovSearch');
insert into ws_servizi(id,nome) values(8,'searchApimodmov');

insert into ws_info_web_service(id,id_endpoint, id_azione, id_oggetto,id_Servizio) values(9, 4, 1, 7,8);
insert into ws_oggetto_campi(id,id_oggetto,campo) values(67,7,'flagInEntrataDaApicoltoriFuoriRegione');





       