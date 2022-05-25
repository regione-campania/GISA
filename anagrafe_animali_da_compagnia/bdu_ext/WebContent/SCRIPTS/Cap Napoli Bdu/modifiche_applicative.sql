--Cessione
update evento_html_fields set javascript_function = 'onchange="DwrUtilComuni();DwrUtilProvincia();document.getElementById(''idComune'').disabled=''disabled'';document.getElementById(''idComune'').value='''';document.getElementById(''idProvincia'').value='''';document.getElementById(''idProvincia'').disabled=''disabled'';document.getElementById(''indirizzo'').disabled=''disabled'';document.getElementById(''indirizzo'').value='''';document.getElementById(''cap'').disabled=''disabled'';document.getElementById(''cap'').value='''';"' where id = 160;
insert into evento_html_fields (id_tipologia_evento, nome_campo, tipo_campo,label_campo,javascript_function,javascript_function_evento,ordine_campo,label_link) values(7,'selezionaIndirizzo', 'link', 'Seleziona', 'selezionaIndirizzo(''106'',''callbackResidenzaProprietarioCessione'','''',''CAMPANIA'',document.getElementById(''idAslNuovoProprietarioSelect'').value)','onclick',12,'Indirizzo residenza');

--Adozione fuori asl
update evento_html_fields set javascript_function = 'onchange="DwrUtilComuni();DwrUtilProvincia();document.getElementById(''idComune'').disabled=''disabled'';document.getElementById(''idComune'').value='''';document.getElementById(''idProvincia'').value='''';document.getElementById(''idProvincia'').disabled=''disabled'';document.getElementById(''indirizzo'').disabled=''disabled'';document.getElementById(''indirizzo'').value='''';document.getElementById(''cap'').disabled=''disabled'';document.getElementById(''cap'').value='''';"' where id = 258;
insert into evento_html_fields (id_tipologia_evento, nome_campo, tipo_campo,label_campo,javascript_function,javascript_function_evento,ordine_campo,label_link) values(46,'selezionaIndirizzo', 'link', 'Seleziona', 'selezionaIndirizzo(''106'',''callbackResidenzaProprietarioAdozioneFuoriAsl'','''',''CAMPANIA'',document.getElementById(''idAslNuovoProprietarioSelect'').value)','onclick',11,'Indirizzo residenza');


--Registrazione modifica residenza proprietario
insert into evento_html_fields (id_tipologia_evento, nome_campo, tipo_campo,label_campo,javascript_function,javascript_function_evento,ordine_campo,label_link) values(43,'selezionaIndirizzo', 'link', 'Seleziona', 'selezionaIndirizzo(''106'',''callbackResidenzaProprietarioModificaResidenza'','''','''','''')','onclick',4,'Indirizzo residenza');
insert into evento_html_fields (id_tipologia_evento, nome_campo, tipo_campo,label_campo, ordine_campo, valore_campo) values(43,'cap', 'text', 'Cap',7, '');


--Aggiornamento
update evento_html_fields set nome_campo = 'idProvinciaModificaResidenzaSelect' where id = 213;
update evento_html_fields set nome_campo = 'idComuneModificaResidenzaSelect' where id = 215;
insert into evento_html_fields values(366,43,'idProvinciaModificaResidenza','hidden','',null,null,null,null,4,null,null,null,null,null,0,null,1,false,false);
insert into evento_html_fields values(367,43,'idComuneModificaResidenza','hidden','',null,null,null,null,5,null,null,null,null,null,0,null,1,false,false);
insert into evento_html_fields values(368,43,'idAslNuovoProprietario','hidden','',null,null,null,null,5,null,null,null,null,null,0,null,1,false,false);




