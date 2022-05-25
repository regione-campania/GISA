update dpat_indicatore_new set codice_interno_piani_gestione_cu = 1409 where id = 7430;
select * from refresh_motivi_cu(2019,true);

CREATE TABLE dpat_lista_codice_interno_readonly
(
  codice_interno_univoco_attivita text,
  data_blocco timestamp without time zone,
  data_riattivazione timestamp without time zone,
  tipo_attivita text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.dpat_lista_codice_interno_readonly
  OWNER TO postgres;
GRANT ALL ON TABLE public.dpat_lista_codice_interno_readonly TO postgres;


insert into dpat_lista_codice_interno_readonly values('7a',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('9a',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('58a',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('14a',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('28a',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('1a',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('20a',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('30a',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('982',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('983',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('1483',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('1425',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('1409',now(),null,'I');

insert into dpat_lista_codice_interno_readonly values('1265',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1268',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1391',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1396',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1398',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1586',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1587',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1641',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1311',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1265',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1321',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1641',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1644',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1644',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1339',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1711',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1339',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1389',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1390',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1391',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1396',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1398',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1423',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1472',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1503',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1505',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1506',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1407',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1413',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1475',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1265',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1339',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1587',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1265',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1339',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1391',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1396',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1398',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1586',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1587',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1711',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1644',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1339',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1265',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1711',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1641',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1644',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1641',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1391',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1396',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1398',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1586',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1587',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1711',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1391',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1396',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1398',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1586',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1587',now(),null,'P');
insert into dpat_lista_codice_interno_readonly values('1586',now(),null,'P');






update dpat_indicatore_new set codice_interno_attivita_gestione_cu = 7612 where id = 7612;
update dpat_indicatore_new set codice_interno_attivita_gestione_cu = id where id in (
select id from dpat_indicatore_new where (codice_interno_attivita_gestione_cu in (select codice_interno_attivita_gestione_cu from dpat_indicatore_new where dpat_indicatore_new.id_piano_attivita = 2590 and (codice_interno_piani_gestione_cu <> 982 or codice_interno_piani_gestione_cu is null) 
) or codice_interno_piani_gestione_cu::text in (select codice_interno_attivita_gestione_cu from dpat_indicatore_new where dpat_indicatore_new.id_piano_attivita = 2590 and (codice_interno_piani_gestione_cu <> 982 or codice_interno_piani_gestione_cu is null) 
)) and anno = 2019 and id_piano_attivita = 2590);



insert into dpat_lista_codice_interno_readonly values('7097',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('7098',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('7099',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('7100',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('7101',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('7102',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('7103',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('7104',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('7105',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('7106',now(),null,'I');
insert into dpat_lista_codice_interno_readonly values('7612',now(),null,'I');
