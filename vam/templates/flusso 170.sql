alter table lookup_esame_istopatologico_tipo_prelievo add column deceduto boolean default false;
update lookup_esame_istopatologico_tipo_prelievo set deceduto = true where id = 3; 
insert into lookup_autopsia_sala_settoria values (17,'Effettuata In Situ',true,5,'Necroscopico',false);
insert into lookup_autopsia_sala_settoria values (18,'Asl SA Nocera Inferiore',true,45,'Necroscopico',false);
insert into lookup_autopsia_organi values(90,'Occhi',true,true,114,236,false,true,true,true,true,true);
insert into organi_patologieprevalenti values(1,90);
insert into organi_patologieprevalenti values(2,90);
insert into organi_patologieprevalenti values(3,90);
insert into organi_patologieprevalenti values(4,90);
insert into organi_patologieprevalenti values(5,90);
insert into organi_patologieprevalenti values(6,90);
insert into organi_patologieprevalenti values(7,90);
insert into organi_patologieprevalenti values(8,90);
insert into organi_patologieprevalenti values(9,90);
insert into organi_patologieprevalenti values(10,90);
insert into organi_patologieprevalenti values(135,90);
insert into organi_patologieprevalenti values(136,90);

insert into lookup_autopsia_organi(description, enabled,enabled_sde,level,level_sde,tessuto,cani,gatti,uccelli,mammiferi,rettili) 
values ('Valvola ileocecale/trachea/polmoni',false,true,510,435,false,false,false,true,false,false);
insert into lookup_autopsia_patologie_prevalenti  values (197,'Prova del coledoco positiva',true,200,true,true);
insert into lookup_autopsia_patologie_prevalenti  values (198,'Prova del coledoco negativa',true,200,true,true);
update lookup_autopsia_patologie_prevalenti set definitiva = false where id in(197,198); 
insert into organi_patologieprevalenti values(197,17);
insert into organi_patologieprevalenti values(198,17);

insert into lookup_detentori_sinantropi values(26,'Zoo delle Maitine',null,true,null,'via Fontana dell''Occhio 1', 15,null,348,true,now(),null);
update lookup_detentori_sinantropi set description  = 'Lo Zoo di Napoli srl – viale Kennedy 76 – 80125 Napoli' where description ilike '%zoo di napoli%';
update lookup_detentori_sinantropi set description  = 'Zoo delle Maitine – via Fontana dell''Occhio 1 – 82020 Pesco Sannita (BN)' where id = 23;
update lookup_detentori_sinantropi set description  = 'Zoo delle Maitine – via Fontana dell''Occhio 1 – 82020 Pesco Sannita (BN)' where id = 26;


update lookup_autopsia_organi set uccelli = false  where  id in( 39,66);
insert into lookup_autopsia_organi values(92,'Cuore/encefalo/rene/milza',false,true,null,47,false,false,false,true,false,false);
update lookup_autopsia_organi set enabled = false,level = 1 where id = 92;

select * from lookup_autopsia_organi where description ilike '%Cuore%' or description ilike '%encefalo%' or description ilike '%rene%' or description ilike '%milza%'
update lookup_autopsia_organi set enabled_sde = false where id in (33,26,10);
update lookup_autopsia_organi set uccelli = false where id = 77;
update lookup_autopsia_organi set uccelli = false where id = 78;
insert into lookup_autopsia_organi values (93, 'Valvola ileocecale', true,false,510,null,false,false,false,true,false,false);
update lookup_autopsia_organi set uccelli = true where id in (27, 37);
update lookup_autopsia_organi set enabled = true, enabled_sde = false where description in ( 'Trachea', 'Polmoni') and uccelli;
update lookup_autopsia_organi set enabled_sde = false where id = 73;

/*insert into utenti_  (cognome,enabled,nome, password, ruolo, username,clinica,superutente) values();
insert into utenti_  (cognome,enabled,nome, password, ruolo, username,clinica,superutente) values();
insert into utenti_  (cognome,enabled,nome, password, ruolo, username,clinica,superutente) values();
insert into utenti_  (cognome,enabled,nome, password, ruolo, username,clinica,superutente) values();
insert into utenti_  (cognome,enabled,nome, password, ruolo, username,clinica,superutente) values();
insert into utenti_  (cognome,enabled,nome, password, ruolo, username,clinica,superutente) values();
insert into utenti_  (cognome,enabled,nome, password, ruolo, username,clinica,superutente) values();
insert into utenti_  (cognome,enabled,nome, password, ruolo, username,clinica,superutente) values();
*/


