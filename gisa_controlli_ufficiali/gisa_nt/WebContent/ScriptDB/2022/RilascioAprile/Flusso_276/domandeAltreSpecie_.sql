-- specie broiler e altre specie
insert into chk_bns_domande_v3  (select * from chk_bns_domande_v4);
--select * from lookup_chk_bns_mod  where code in (25,26,27,28)
update chk_bns_domande_v3 set idmod  =38  where idmod=25;
update chk_bns_domande_v3 set idmod  =37  where idmod=28;
delete from chk_bns_domande_v3  where idmod  in (26,27); -- elimino domande per specie vitelli e gallus gestite cn versione 6.
-- specie suini
insert into chk_bns_domande_v6 (select * from chk_bns_domande_v5  where idmod = 24); -- solo specie suini
update chk_bns_domande_v6 set idmod = 34 where idmod=24;
-- specie bovini e bufalini
insert into chk_bns_domande_v6 (select * from chk_bns_domande_v5  where idmod = 29); -- solo specie bovini e buf
update chk_bns_domande_v6 set idmod = 33 where idmod=29;
-- specie vitelli
insert into chk_bns_domande_v6 (select * from chk_bns_domande_v5  where idmod = 30); -- solo specie vitelli
update chk_bns_domande_v6 set idmod = 35 where idmod=30;
-- specie gallus
insert into chk_bns_domande_v6 (select * from chk_bns_domande_v5  where idmod = 31); -- solo specie gallus
update chk_bns_domande_v6 set idmod = 36 where idmod=31;
alter table chk_bns_mod_ist_v6_bovini_bufalini  add column num_fattrici text;
--bovini e vitelli
update chk_bns_domande_v6 set esito = 'S, NA, NB, NC, -, O'  where irr_id in (149, 198);

-- prima di ovicaprini
------------------------
select max(id) from chk_bns_domande_v6
alter SEQUENCE chk_bns_domande_v6_id_seq   RESTART  205;


--verificare che i due valori corrispondano
select 'seq', last_value from chk_bns_domande_v6_id_seq
UNION
select 'max id', max(id) from chk_bns_domande_v6


update chk_bns_domande_v6 set sottotitolo = '
Valutare unicamente gli animali con evidente zoppia tramite uno score di locomozione che va da 0 a 3; animale zoppo = score 2 e score 3. (Score 2 = L''animale zoppica visibilmente, minimo carico sull''arto interessato, ha una camminata asimmetrica; score 3 = Animale che non appoggia il peso su un arto o non in grado di camminare).

Nel caso delle bufale da latte, data la loro particolare conformazione ed abilita'' a modellare l''andatura, può essere piu''indicativo
rilevare la condizione degli unghioni, se lunghi e deformi, anziche'' la zoppia.

BOVINE DA LATTE (stabulazione libera): Più dell''8% di animali zoppi
BOVINE DA LATTE (stabulazione fissa): Più del 15% di animali zoppi
LINEA VACCA-VITELLO: Più del 10% di animali zoppi
BOVINI DA INGRASSO: Più del 6% di animali zoppi
BUFALE DA LATTE: Più del 10% di animali con unghioni lunghi e/o deformi
Livello della non conformita'': Insufficiente

BOVINE DA LATTE (stabulazione libera): Tra 4% e 8% di animali zoppi
BOVINE DA LATTE (stabulazione fissa): Tra il 10 e il 15% di animali zoppi
LINEA VACCA-VITELLO: Tra 5% e 10% di animali zoppi
BOVINI DA INGRASSO: Tra 2% e 6% di animali zoppi
BUFALE DA LATTE: Tra 5% e 10% di animali con unghioni lunghi e/o deformi
Livello della non conformita'': Adeguato

BOVINE DA LATTE (stabulazione libera): Meno del 4% di animali zoppi
BOVINE DA LATTE (stabulazione fissa): Meno del 10 di animali zoppi
LINEA VACCA-VITELLO: Meno del 5% di animali zoppi
BOVINI DA INGRASSO: Meno del 2% di animali zoppi
BUFALE DA LATTE: Meno del 5% di animali con unghioni lunghi e/o deformi
Livello della non conformita'': Ottimale
' where id= 100;
