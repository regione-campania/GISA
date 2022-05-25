-- da rilasciare in esercizio
alter table lookup_specie_alimento add column selvaggia_lucarelli  text;
select * from lookup_specie_alimento  order by code
select * from lookup_specie_pnaa  order by code

update lookup_specie_alimento set selvaggia_lucarelli = '11' where code = 3; --Vitelli
update lookup_specie_alimento set selvaggia_lucarelli = '9' where code = 2; --Vacche da latte
update lookup_specie_alimento set selvaggia_lucarelli = '12' where code = 1; --Tori/vitelloni
update lookup_specie_alimento set selvaggia_lucarelli = '18' where code = 7;--Pecore/Capre
update lookup_specie_alimento set selvaggia_lucarelli = '8' where code = 15; --Altro pollame da carne
update lookup_specie_alimento set selvaggia_lucarelli = '28' where code = 10;--Animali da compagnia
update lookup_specie_alimento set selvaggia_lucarelli = '28' where code = 11;--Animali da compagnia
update lookup_specie_alimento set selvaggia_lucarelli = '15' where code = 8; --Conigli
update lookup_specie_alimento set selvaggia_lucarelli = '14' where code = 5;--Bufali
update lookup_specie_alimento set selvaggia_lucarelli = '1' where code = 13; -- galline ovaiole
update lookup_specie_alimento set selvaggia_lucarelli = '21' where code = 9; --Suini
update lookup_specie_alimento set selvaggia_lucarelli = '17' where code = 6; -- Equini
update lookup_specie_alimento set selvaggia_lucarelli = '6' where code = 14; -- broilers
update lookup_specie_alimento set selvaggia_lucarelli = '20' where code = 16;--Acquacoltura
update lookup_specie_alimento set selvaggia_lucarelli = '20' where code = 17;--Acquacoltura
update lookup_specie_alimento set selvaggia_lucarelli = '12' where code = 4; -->Tori/vitelloni

truncate lookup_specie_alimento;
insert into lookup_specie_alimento(code, description, level, enabled ) (select code, description, level, enabled from lookup_specie_pnaa); 

select "id campione","id controllo","B6. Specie e categoria animale per alimento"  from digemon.estrazione_campioni_pnaa('2021-01-01','2021-12-31');

update lookup_specie_alimento set enabled = false where description in ('Avicoli', 'Altri avicoli', 'Bovini', 'Bovini da carne');