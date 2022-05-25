insert into ws_oggetto values(9,'ApiAttivitaTO');
insert into ws_servizi(id,nome) values(10,'insertApiAttivita');
insert into ws_endpoint values(7, 'API ATTIVITA');
insert into ws_info_web_service(id,id_endpoint, id_azione, id_oggetto,id_Servizio) values(11, 7, 3, 9,10);

insert into ws_endpoint_info(id, id_endpoint, ambiente, url, username, password, ruolo, ruolo_codice, ruolo_valore_codice, xmlns) values(11,7,'DEMO',       'http://wstest.izs.it/j_test_apicoltura/ws/apicoltura/apianagrafica/apiattivita', 'campania_BDA', 'Izsm102018', null,'REG', '150', 'http://ws.apianagrafica.apicoltura.izs.it/');
insert into ws_endpoint_info(id, id_endpoint, ambiente, url, username, password, ruolo, ruolo_codice, ruolo_valore_codice, xmlns) values(12,7,'UFFICIALE',  'http://ws.izs.it/j6_apicoltura/ws/apicoltura/apianagrafica/apiattivita', 'campania_BDA', 'campania', null,'REG', '150', 'http://ws.apianagrafica.apicoltura.izs.it/' );

insert into ws_oggetto_campi(id,id_oggetto,campo) values(85,9,'apiattId');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(86,9,'denominazione');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(87,9,'aziendaCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(88,9,'aslCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(89,9,'aslDenominazione');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(90,9,'regSlCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(91,9,'regSlDescrizione');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(92,9,'propIdFiscale');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(93,9,'propCognNome');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(94,9,'comSlIstat');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(95,9,'comSlDescrizione');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(96,9,'comSlProSigla');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(97,9,'comSlCap');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(98,9,'localitaSl');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(99,9,'indirizzoSl');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(100,9,'apitipattCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(101,9,'apitipattDescrizione');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(102,9,'numTelFisso');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(103,9,'numTelMobile');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(104,9,'fax');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(105,9,'email');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(106,9,'dtInizioAttivita');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(107,9,'dtCessazione');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(108,9,'note');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(109,9,'apiattLabSmielatura');












		
		