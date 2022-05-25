--verifico le linee riconosciute 
select * from master_list_suap where id_tipo_linea_produttiva  =2 and  flag_nuova_gestione = true 

--ES:
select * from master_list_suap where id_tipo_linea_produttiva  =2 and  flag_nuova_gestione = true where id=[ID]
select * from master_list_suap where id_tipo_linea_produttiva  =2 and  flag_nuova_gestione = true where descrizione ilike '%%'

--statement di inserimento della nuova linea SOA mancante di 2, 3, e 4 livello
insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values([ID],'[descrizione]',[livello],[id_padre],true,2,true,1,2,[BOOLEANO]);

--ESEMPI:
/*insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8099,'IMPIANTO CHE SVOLGE ATTIVITA'' INTERMEDIA E STOCCAGGIO DI SOA cat 1 (SEZ I)',2,7413,true,2,true,1,2,true);

insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8100,'TRANSITO SENZA MANIPOLAZIONE (STORP)',3,8099,true,2,true,1,2,true);

--
insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8101,'ALTRE CARCASSE ANIMALI',4,8100,true,2,true,1,2,true);

insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8102,'ALTRI SOTTOPRODOTTI NON TRASFORMATI',4,8100,true,2,true,1,2,true);

insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8103,'CARCASSE DI ANIMALI DA CIRCO',4,8100,true,2,true,1,2,true);

insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8104,'CARCASSE DI ANIMALI DA CIRCO',4,8100,true,2,true,1,2,true);

insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8105,'CARCASSE DI ANIMALI DA COMPAGNIA',4,8100,true,2,true,1,2,true);

insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8106,'CARCASSE DI ANIMALI DA ESPERIMENTO',4,8100,true,2,true,1,2,true);

insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8107,'CARCASSE DI ANIMALI DA ZOO',4,8100,true,2,true,1,2,true);

insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8108,'LANA, PELI, SETOLA DI MAIALE, PENNE',4,8100,true,2,true,1,2,true);

insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8109,'LATTE, PRODOTTI DEL LATTE E COLOSTRO',4,8100,true,2,true,1,2,true);

insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8110,'MATERIALE DA ACQUE REFLUE (MONDIGLIA)',4,8100,true,2,true,1,2,true);

insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8111,'OSSA, CORNA, ZOCCOLI E DERIVATI',4,8100,true,2,true,1,2,true);

insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8112,'PELLI GREZZE',4,8100,true,2,true,1,2,true);

insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8113,'RIFIUTI DI CUCINA E RISTORAZIONE PROVENIENTI DA MEZZI DI TRASPORTO CHE EFFETTUANO TRAGITTI INTERNAZIONALI',4,8100,true,2,true,1,2,true);

insert into master_list_suap (id, descrizione,livello, id_padre, flag_nuova_gestione, id_tipo_linea_produttiva,enabled,id_lookup_tipo_attivita,id_lookup_tipologia_scia, flag_pnaa) 
values(8114,'ALTRI PRODOTTI PREVISTI DALL''ART. 8 REG. CE 1069/09',4,8100,true,2,true,1,2,true);
--
*/

--verifico master_list_suap_allegati_procedure_relazione 
select * from master_list_suap_allegati_procedure_relazione  

--verifico i gruppi con gli allegati
select * from master_list_gruppi_allegati

--statement di inserimento
insert into master_list_suap_allegati_procedure_relazione(id, id_master_list_suap, id_master_list_suap_gruppo_allegati) values([ID],[ID LINEA ATTIVA],[ID_GRUPPO_ALLEGATI_INTERESSATO]);

-- inserimento master list suap allegati procedure relazione
--ES:
/*insert into master_list_suap_allegati_procedure_relazione(id, id_master_list_suap, id_master_list_suap_gruppo_allegati) values(7242,8101,30);
insert into master_list_suap_allegati_procedure_relazione(id, id_master_list_suap, id_master_list_suap_gruppo_allegati) values(7243,8102,30);
insert into master_list_suap_allegati_procedure_relazione(id, id_master_list_suap, id_master_list_suap_gruppo_allegati) values(7244,8103,30);
insert into master_list_suap_allegati_procedure_relazione(id, id_master_list_suap, id_master_list_suap_gruppo_allegati) values(7245,8104,30);
insert into master_list_suap_allegati_procedure_relazione(id, id_master_list_suap, id_master_list_suap_gruppo_allegati) values(7246,8105,30);
insert into master_list_suap_allegati_procedure_relazione(id, id_master_list_suap, id_master_list_suap_gruppo_allegati) values(7247,8106,30);
insert into master_list_suap_allegati_procedure_relazione(id, id_master_list_suap, id_master_list_suap_gruppo_allegati) values(7248,8107,30);
insert into master_list_suap_allegati_procedure_relazione(id, id_master_list_suap, id_master_list_suap_gruppo_allegati) values(7249,8108,30);
insert into master_list_suap_allegati_procedure_relazione(id, id_master_list_suap, id_master_list_suap_gruppo_allegati) values(7250,8109,30);
insert into master_list_suap_allegati_procedure_relazione(id, id_master_list_suap, id_master_list_suap_gruppo_allegati) values(7251,8110,30);
insert into master_list_suap_allegati_procedure_relazione(id, id_master_list_suap, id_master_list_suap_gruppo_allegati) values(7252,8111,30);
insert into master_list_suap_allegati_procedure_relazione(id, id_master_list_suap, id_master_list_suap_gruppo_allegati) values(7253,8112,30);
insert into master_list_suap_allegati_procedure_relazione(id, id_master_list_suap, id_master_list_suap_gruppo_allegati) values(7254,8113,30);
insert into master_list_suap_allegati_procedure_relazione(id, id_master_list_suap, id_master_list_suap_gruppo_allegati) values(7255,8114,30);
*/