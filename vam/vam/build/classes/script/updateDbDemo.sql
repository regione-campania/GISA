--Richieste Rosato 1 Marzo
update lookup_autopsia_organi set level_sde = 215, description = 'Miocardio' where description ilike '%cuore%';

INSERT INTO lookup_autopsia_organi(
            description, enabled, enabled_sde, level, level_sde, tessuto, 
            cani, gatti, uccelli, mammiferi, rettili)
    VALUES ('Epicardio', true, true, 421, 85, false, 
            true, true, false, true, false);
INSERT INTO lookup_autopsia_organi(
            description, enabled, enabled_sde, level, level_sde, tessuto, 
            cani, gatti, uccelli, mammiferi, rettili)
    VALUES ('Valvole', true, true, 422, 435 , false, 
            true, true, false, true, false);
INSERT INTO lookup_autopsia_organi(
            description, enabled, enabled_sde, level, level_sde, tessuto, 
            cani, gatti, uccelli, mammiferi, rettili)
    VALUES ('Arterie Coronarie', true, true, 423,5, false, 
            true, true, false, true, false);


insert into organi_patologieprevalenti values(1,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(2,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(3,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(4,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(5,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(6,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(7,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(8,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(9,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(10,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(1,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(2,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(3,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(4,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(5,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(6,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(7,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(8,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(9,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(10,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(1,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(2,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(3,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(4,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(5,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(6,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(7,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(8,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(9,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(10,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(135,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(136,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(135,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(136,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(135,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(136,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));

INSERT INTO lookup_autopsia_organi(
            description, enabled, enabled_sde, level, level_sde, tessuto, 
            cani, gatti, uccelli, mammiferi, rettili)
    VALUES ('Epicardio', true, true, 571, 85, false, 
            true, true, true, false, false);
INSERT INTO lookup_autopsia_organi(
            description, enabled, enabled_sde, level, level_sde, tessuto, 
            cani, gatti, uccelli, mammiferi, rettili)
    VALUES ('Valvole', true, true, 572, 435 , false, 
            true, true, true, false, false);
INSERT INTO lookup_autopsia_organi(
            description, enabled, enabled_sde, level, level_sde, tessuto, 
            cani, gatti, uccelli, mammiferi, rettili)
    VALUES ('Arterie Coronarie', true, true, 573,5, false, 
            true, true, true, false, false);


insert into organi_patologieprevalenti values(55,(select max(id) from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(56,(select max(id) from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(57,(select max(id) from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(131,(select max(id) from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(132,(select max(id) from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(135,(select max(id) from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(136,(select max(id) from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(55,(select max(id) from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(56,(select max(id) from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(57,(select max(id) from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(131,(select max(id) from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(132,(select max(id) from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(135,(select max(id) from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(136,(select max(id) from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(55,(select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(56,(select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(57,(select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(131,(select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(132,(select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(135,(select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(136,(select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(164,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(165,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(166,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(167,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(168,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(169,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(170,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(171,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(172,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(173,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(174,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(175,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(176,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(177,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(178,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(179,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(180,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(181,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(182,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));

delete from organi_patologieprevalenti where organo = 47 and patologia_prevalente in (1,2,3,4,5,6,7,8,9,10,135,136);














