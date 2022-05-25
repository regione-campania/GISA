alter table apicoltura_apiari add column asl_roma_bdn character varying(10);

insert into ws_oggetto values(8,'ApiarioTO');
insert into ws_servizi(id,nome) values(9,'insertApiario');
insert into ws_info_web_service(id,id_endpoint, id_azione, id_oggetto,id_Servizio) values(10, 1, 3, 8,9);

insert into ws_endpoint_info(id, id_endpoint, ambiente, url, username, password, ruolo, ruolo_codice, ruolo_valore_codice, xmlns) values(9,1,'DEMO',       'http://wstest.izs.it/j_test_apicoltura/ws/apicoltura/apianagrafica/apiario', 'campania_BDA', 'Izsm102018', null,'REG', '150', 'http://ws.apianagrafica.apicoltura.izs.it/');
insert into ws_endpoint_info(id, id_endpoint, ambiente, url, username, password, ruolo, ruolo_codice, ruolo_valore_codice, xmlns) values(10,1,'UFFICIALE', 'http://ws.izs.it/j6_apicoltura/ws/apicoltura/apianagrafica/apiario', 'campania_BDA', 'campania', null,'REG', '150', 'http://ws.apianagrafica.apicoltura.izs.it/' );
		
insert into ws_oggetto_campi(id,id_oggetto,campo) values(68,8,'apiattAziendaCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(69,8,'progressivo');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(70,8,'detenIdFiscale');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(71,8,'numAlveari');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(72,8,'numSciami');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(73,8,'comIstat');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(74,8,'comProSigla');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(75,8,'localita');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(76,8,'apiAslCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(77,8,'indirizzo');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(78,8,'latitudine');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(79,8,'longitudine');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(80,8,'cap');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(81,8,'dtApertura');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(82,8,'classificazione');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(83,8,'apisotspeCodice');
insert into ws_oggetto_campi(id,id_oggetto,campo) values(84,8,'apimodallCodice');








		
		