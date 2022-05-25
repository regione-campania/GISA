insert into registrazioni_wk(id_prossimo_stato, id_registrazione, id_stato, only_hd) values(2, 17, 6, false);
delete from registrazioni_wk where id_registrazione = 8 and id_stato in ( 
select code from lookup_tipologia_stato where description ilike 'randagio%');
update evento_html_fields set label_campo_controlli_date = 'Data ' where tipo_campo = 'data' and label_campo_controlli_date is null;
insert into lookup_tipologia_stato (description,default_item,level,enabled,entered,modified,isgroup) values ('Import/Cessione/Deceduto', false,100,true, now(),now(),false);
insert into registrazioni_wk(id_stato,id_registrazione,id_prossimo_stato,only_hd) values(75,9,82,true);
insert into registrazioni_wk(id_stato,id_registrazione,id_prossimo_stato,only_hd) values(76,9,82,true);
update evento_html_fields set javascript_function = 'popUp(''OperatoreAction.do?command=SearchForm&tipologiaSoggetto=2&popup=true&tipoRegistrazione=12&idLineaProduttiva1=5'')' where id = 315;
update evento_html_fields set javascript_function = 'popUp(''OperatoreAction.do?command=SearchForm&tipologiaSoggetto=1&popup=true&tipoRegistrazione=58'')' where id = 316;


