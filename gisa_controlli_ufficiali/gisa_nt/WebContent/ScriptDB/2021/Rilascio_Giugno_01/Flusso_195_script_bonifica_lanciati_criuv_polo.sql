
update access_ set role_id=3365 where username=
'a.cestaro'and enabled;update access_ set data_scadenza=now()   where username='a.cestaro_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'a.dimaggio.criuv'and enabled;update access_ set data_scadenza=now()   where username='a.dimaggio_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'a.petruccelli_criuv'and enabled;update access_ set data_scadenza=now()   where username='a.petruccelli_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'daciernoc.criuv'and enabled;update access_ set data_scadenza=now()   where username='c.dacierno_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'citarellad.criuv'and enabled;update access_ set data_scadenza=now()   where username='d.citarella_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'izzillona1.criuv'and enabled;update access_ set data_scadenza=now()   where username='d.izzillo_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'eudaniele.criuv'and enabled;update access_ set data_scadenza=now()   where username='e.daniele_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'battaglinina1.criuv'and enabled;update access_ set data_scadenza=now()   where username='f.battaglini_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'fattorif.criuv'and enabled;update access_ set data_scadenza=now()   where username='f.fattori_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'v.zinno.criuv'and enabled;update access_ set data_scadenza=now()   where username='f.zinno_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'alfierona1.criuv'and enabled;update access_ set data_scadenza=now()   where username='g.alfiero_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'rosatona1.criuv'and enabled;update access_ set data_scadenza=now()   where username='g.rosato_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'i.bongiorno'and enabled;update access_ set data_scadenza=now()   where username='i.bongiorno_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'i.maglione.criuv'and enabled;update access_ set data_scadenza=now()   where username='i.maglione_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'nettunol.criuv'and enabled;update access_ set data_scadenza=now()   where username='l.nettuno_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'bresciam.criuv'and enabled;update access_ set data_scadenza=now()   where username='m.brescia_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'canalena1.criuv'and enabled;update access_ set data_scadenza=now()   where username='m.canale_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'cortesena1.criuv'and enabled;update access_ set data_scadenza=now()   where username='m.cortese_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'm.pasqui.criuv'and enabled;update access_ set data_scadenza=now()   where username='m.pasqui_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'm.pompameo_asl.criuv'and enabled;update access_ set data_scadenza=now()   where username='m.pompameo_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'bontempop.criuv'and enabled;update access_ set data_scadenza=now()   where username='p.bontempo_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'cutillop.criuv'and enabled;update access_ set data_scadenza=now()   where username='p.cutillo_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'p.raia.criuv'and enabled;update access_ set data_scadenza=now()   where username='p.raia_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'p.romano.criuv'and enabled;update access_ set data_scadenza=now()   where username='p.romano_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'p.tuccillo.criuv'and enabled;update access_ set data_scadenza=now()   where username='p.tuccillo_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'pennacchio.criuv'and enabled;update access_ set data_scadenza=now()   where username='s.pennacchio_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'vivenziona1.criuv'and enabled;update access_ set data_scadenza=now()   where username='s.vivenzio_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'enzocaputo.criuv'and enabled;update access_ set data_scadenza=now()   where username='v.caputo_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'mizzonina1.criuv'and enabled;update access_ set data_scadenza=now()   where username='v.mizzoni_polo_criuv'and enabled;
update access_ set role_id=3365 where username=
'toscanona1.criuv'and enabled;update access_ set data_scadenza=now()   where username='v.toscano_polo_criuv'and enabled;

update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='a.cestaro') where id_componente = (select user_id from access_ where username = 'a.cestaro_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='a.dimaggio.criuv') where id_componente = (select user_id from access_ where username = 'a.dimaggio_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='a.petruccelli_criuv') where id_componente = (select user_id from access_ where username = 'a.petruccelli_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='daciernoc.criuv') where id_componente = (select user_id from access_ where username = 'c.dacierno_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='citarellad.criuv') where id_componente = (select user_id from access_ where username = 'd.citarella_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='izzillona1.criuv') where id_componente = (select user_id from access_ where username = 'd.izzillo_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='eudaniele.criuv') where id_componente = (select user_id from access_ where username = 'e.daniele_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='battaglinina1.criuv') where id_componente = (select user_id from access_ where username = 'f.battaglini_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='fattorif.criuv') where id_componente = (select user_id from access_ where username = 'f.fattori_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='v.zinno.criuv') where id_componente = (select user_id from access_ where username = 'f.zinno_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='alfierona1.criuv') where id_componente = (select user_id from access_ where username = 'g.alfiero_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='rosatona1.criuv') where id_componente = (select user_id from access_ where username = 'g.rosato_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='i.bongiorno') where id_componente = (select user_id from access_ where username = 'i.bongiorno_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='i.maglione.criuv') where id_componente = (select user_id from access_ where username = 'i.maglione_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='nettunol.criuv') where id_componente = (select user_id from access_ where username = 'l.nettuno_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='bresciam.criuv') where id_componente = (select user_id from access_ where username = 'm.brescia_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='canalena1.criuv') where id_componente = (select user_id from access_ where username = 'm.canale_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='cortesena1.criuv') where id_componente = (select user_id from access_ where username = 'm.cortese_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='m.pasqui.criuv') where id_componente = (select user_id from access_ where username = 'm.pasqui_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='m.pompameo_asl.criuv') where id_componente = (select user_id from access_ where username = 'm.pompameo_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='bontempop.criuv') where id_componente = (select user_id from access_ where username = 'p.bontempo_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='cutillop.criuv') where id_componente = (select user_id from access_ where username = 'p.cutillo_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='p.raia.criuv') where id_componente = (select user_id from access_ where username = 'p.raia_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='p.romano.criuv') where id_componente = (select user_id from access_ where username = 'p.romano_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='p.tuccillo.criuv') where id_componente = (select user_id from access_ where username = 'p.tuccillo_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='pennacchio.criuv') where id_componente = (select user_id from access_ where username = 's.pennacchio_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='vivenziona1.criuv') where id_componente = (select user_id from access_ where username = 's.vivenzio_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='enzocaputo.criuv') where id_componente = (select user_id from access_ where username = 'v.caputo_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='mizzonina1.criuv') where id_componente = (select user_id from access_ where username = 'v.mizzoni_polo_criuv' and role_id=3365 );
update cu_nucleo set notes_hd = 'Aggiornamento id componente nucleo ispettivo per convergenza ruolo CRIUV e CU 2021(ex id_componente='||id_componente||')', 
id_componente = (select user_id from access where username='toscanona1.criuv') where id_componente = (select user_id from access_ where username = 'v.toscano_polo_criuv' and role_id=3365 );

select * from access where role_id = 54 and enabled and data_scadenza is null
update access_ set enabled = false, note_hd = 'Disabilitazione utente associato a ruolo CRIUV per convergenza ruoli con POLO DIDATTICO INTEGRATO CRIUV. Richiesta ORSA del 10/06/2021', data_scadenza=now() where role_id = 54 and data_scadenza is null;
-- verifica che tt quelli con data_scadenza sn enabled=false??
update role set enabled = false, in_access=false, in_nucleo_ispettivo=false, note = 'Disabilitazione ruolo CRIUV per convergenza ruoli con POLO DIDATTICO INTEGRATO CRIUV. Richiesta ORSA del 10/06/2021' where role_id=54 

