alter table analiti add column note_hd text;
update analiti set enabled = false, note_hd = 'Analita disabilitato per revisione analiti flusso 274' where enabled = true and livello > 1;
select * from analiti where nome ilike '%DIAGNOSTICA%' and enabled
select * from analiti where analiti_id=2463
1898;"Altro"
2283;"ANALISI GENETICA"
1;"BATTERIOLOGICO"
2;"BIOTOSSICOLOGICO"
3;"CHIMICO"
4;"CITOLOGICO"
2463;"DIAGNOSTICA"
5;"FISICO"
7;"ISTOLOGICO"
8;"MICOLOGICO"
10;"PARASSITOLOGICO"
12;"VIROLOGICO" 

select 'insert into analiti(analiti_id, nome, id_padre, livello, nuova_gestione, enabled, multipla_sel, note_hd) values('||analiti_id||','''||nome||''', '||id_padre||','||livello||',true, true, true, ''Flusso 274''));' 
from analiti
where enabled and livello >1
order by id_padre, livello

--bugfix 
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),3,true,true,true,
'Flusso 274. Operazione effettuata da HD II livello.','CIMURRO',3448);

select * from analiti where nome ilike '%contaminanti%'

select * from analiti where id_padre=1898 and enabled
select * from analiti where id_padre=2817 and enabled
select * from analiti where id_padre=2283 and enabled
select * from analiti where id_padre in (2819,
2822,
2824,
2826
)
select * from analiti where id_padre=2 and enabled
select * from analiti where id_padre in (
2990,
3000
)
select * from analiti where id_padre=3 and enabled
select * from analiti where id_padre in (3523,
3524,
3525,
3526,
3527,
3528,
3529--micotossine
)
select * from analiti where id_padre =3538 ORDER BY NOME

select * from analiti where NOME ILIKE '%impurit%' 2815,2818
select * from analiti where analiti_id in (2817,2814)

update analiti set enabled = false where analiti_id = 2814


select * from analiti where ENABLED AND nome='NITRATI' ORDER BY NOME
select * from analiti where ENABLED AND nome='QUALITA'' ALIMENTO' ORDER BY NOME
select * from analiti where ENABLED AND nome='AMIDO' ORDER BY NOME
select * from analiti where ENABLED AND nome='AMMONIACA' ORDER BY NOME

select * from analiti where analiti_id in (3065,3002)ORDER BY NOME






select * from analiti where id_padre=4 and enabled
select * from analiti where id_padre in (
3283,
3286
)and enabled
update analiti set id_padre=null where analiti_id  in(3315,
3316,
3317,
3318,
3319)
select * from analiti where id_padre=2463 and enabled
select * from analiti where id_padre in (3288,
3310
)
select * from analiti where id_padre=5 and enabled
select * from analiti where id_padre in (3312,
3320,3326
)
select * from analiti where id_padre=7 and enabled
select * from analiti where id_padre in (3332,
3337,
3343
)
select * from analiti where id_padre=8 and enabled
select * from analiti where id_padre in (3345,
3349
)
select * from analiti where id_padre=10 and enabled 
select * from analiti where id_padre in (3357,
3361,
3399
)
select * from analiti where id_padre=12 and enabled 
select * from analiti where id_padre in (3407,
3448
)
update analiti set id_padre=3538 where analiti_id=3280;
UPDATE anAliti set id_padre=3532 where analiti_id = 3282
UPDATE anAliti set id_padre=3532 where analiti_id = 3166
UPDATE anAliti set id_padre=3532 where analiti_id = 3167


insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','DIAGNOSTICA',3);
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','ELEMENTI CHIMICI',3);
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','ENZIMI',3);
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','FANS',3);
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','INIBENTI',3);
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','METALLI PESANTI',3);
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','MICOTOSSINE',3);
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','QUALITA'' ALIMENTI',3);
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','QUALITA'' ALIMENTI ZOOTECNICI',3);
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','QUALITA'' ALIMENTO',3);
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','QUALITA'' MATERIALI A CONTATTO ALIMENTI',3);
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','RESIDUI FARMACI',3);
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','STATO DI CONSERVAZIONE',3);
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','TOSSICOLOGICO',3);
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','TRATTAMENTO ALIMENTI',3);
insert into analiti(analiti_id,livello, nuova_gestione,enabled, multipla_sel, note_hd, nome, id_padre) values((select max(analiti_id)+1 from analiti),2,true,true,true,'Inserito nuovo analita come richiesto da Flusso 274. Operazione effettuata da HD II livello.','TRATTAMENTO TERMICO',3);
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='DIAGNOSTICA' and id_padre=3) where enabled and id_padre is null and  nome='CUMARINICI';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='CLORO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='CLORURI';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='CROMO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='FERRO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='FOSFORO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='GERMANIO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='INDIO ';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='MAGNESIO ';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='MANGANESE ';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='METALLI';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='NICHEL ';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='POTASSIO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='RAME';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='SELENIO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='SODIO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='POTASSIO ';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='STAGNO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='TITANIO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='VANADIO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ELEMENTI CHIMICI' and id_padre=3) where enabled and id_padre is null and  nome='ZINCO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ENZIMI' and id_padre=3) where enabled and id_padre is null and  nome='CATALASI';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ENZIMI' and id_padre=3) where enabled and id_padre is null and  nome='COAGULASI';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ENZIMI' and id_padre=3) where enabled and id_padre is null and  nome='FOSFATASI';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ENZIMI' and id_padre=3) where enabled and id_padre is null and  nome='OSSIDASI';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ENZIMI' and id_padre=3) where enabled and id_padre is null and  nome='PEROSSIDASI';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='ENZIMI' and id_padre=3) where enabled and id_padre is null and  nome='SUCCINICO DEIDROGENASI';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='FANS' and id_padre=3) where enabled and id_padre is null and  nome='FANS';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='INIBENTI' and id_padre=3) where enabled and id_padre is null and  nome='INIBENTI';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='METALLI PESANTI' and id_padre=3) where enabled and id_padre is null and  nome='CADMIO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='METALLI PESANTI' and id_padre=3) where enabled and id_padre is null and  nome='COBALTO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='METALLI PESANTI' and id_padre=3) where enabled and id_padre is null and  nome='MERCURIO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='METALLI PESANTI' and id_padre=3) where enabled and id_padre is null and  nome='METALLI PESANTI';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='METALLI PESANTI' and id_padre=3) where enabled and id_padre is null and  nome='PIOMBO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='AFLATOSSINA B1';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='AFLATOSSINA M1';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='AFLATOSSINE B2';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='AFLATOSSINE G1';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='AFLATOSSINE G2';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='CITRININA';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='DEOSSINIVALENOLO';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='FUMONISINE B1';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='FUMONISINE B2';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='MICOTOSSINE';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='OCRATOSSINA A';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='OCRATOSSINA B';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='OCRATOSSINA C';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='PATULINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='TOSSINA HT2';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='TOSSINA T2 ';
update analiti set id_padre=(select analiti_id from analiti where enabled and id_padre=3 and nome='MICOTOSSINE' and id_padre=3) where enabled and id_padre is null and  nome='ZEARALENONE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTI' and id_padre=3) where enabled and id_padre is null and nome='IDROSSIMETILFURFURALE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTI ZOOTECNICI' and id_padre=3) where enabled and id_padre is null and nome='AMIDO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTI ZOOTECNICI' and id_padre=3) where enabled and id_padre is null and nome='AMMONIACA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTI ZOOTECNICI' and id_padre=3) where enabled and id_padre is null and nome='CALCIO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTI ZOOTECNICI' and id_padre=3) where enabled and id_padre is null and nome='CIANURI ISOTIOCIANATI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTI ZOOTECNICI' and id_padre=3) where enabled and id_padre is null and nome='GRASSI ESTRANEI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTI ZOOTECNICI' and id_padre=3) where enabled and id_padre is null and nome='PROTEINE GREZZE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTI ZOOTECNICI' and id_padre=3) where enabled and id_padre is null and nome='PROTEINE ORIGINE ANIMALE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='ACIDI GRASSI IDENTIFICAZIONE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='ACIDITA'' ';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='ACIDO BETA -IDROSSIBUTIRRICO (B-HBA)';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='ACQUA AGGIUNTA QUANTITA'' ';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='ACQUA CONTENUTO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='ACQUA DUREZZA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='ACQUA ESAME COMPLETO ';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='ACQUA MINERALE ESAME COMPLETO ';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='ACQUA POTABILE ESAME COMPLETO ';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='ACRILAMMIDE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='AMIDO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='AMMONIACA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='ASSORBIMENTO A 420 NM E A 453 NM';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='AW';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='AZOTO TOTALE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='BLU METILENE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='CARBAMMATO DI ETILE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='CASEINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='CASEINATO E LATTE VACCINO RIVELAZIONE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='CENERI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='CONNETTIVO PROTEINE RAPPORTO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='FUROSINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='GAMMA CASEINE IDENTIFICAZIONE ';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='GHT';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='GLUCOSIO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='GLUTINE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='GRASSO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='GRASSO, PROTEINE E LATTOSIO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='IDENTIFICAZIONE DI SPECIE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='IDROSSIPROLINA CONTENUTO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='K 232,K270 E DELTA K ';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='MATERIA GRASSA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='MATERIA SECCA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='PH';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='PROTEINE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='PROTEINE DI COLLAGENE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='PROTEINE DI SPECIE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='PUNTO CRIOSCOPICO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='QUANTIFICAZIONE LATTOSIO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='RICERCA LATTE BOVINO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='SIEROPROTEINE DI LATTE VACCINO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='SOSTANZE AZOTATE TOTALI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='UMIDITA''';--------------
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='UREA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' ALIMENTO' and id_padre=3) where enabled and id_padre is null and nome='ZUCCHERI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='QUALITA'' MATERIALI A CONTATTO ALIMENTI' and id_padre=3) where enabled and id_padre is null and nome='PROVE DI CESSIONE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='ACARICIDI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='AGENTI ANTITIROIDEI ';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='ALOFUGINONE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='AMITRAZ';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='AMMINOGLIGOSIDI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='AVERMECTINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='AVILAMICINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='AZAPERONE-AZAPEROLO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='BENZIMIDAZOLICI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='BENZODIAZEPINE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='BOLDENONE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='CAF';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='CARAZOLO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='CEFALOSPORINE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='CHINOLONICI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='CLENBUTEROLO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='CLORPROMAZINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='CLRAMFENICOLO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='COCCIDIOSTATICI ED HISTOMONOSTATICI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='COCCIDIOSTATICI-IONOFORI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='COLISTINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='CORTICOSTEROIDI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='CORTISONICI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='DAPSONE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='DIMETRIDAZOLO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='FARMACI E PROMOTORI DI CRESCITA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='FENICOLI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='FIPRONIL';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='FLAVOFOSFOLIPOLO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='FLAVOMICINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='FLUMEQUINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='FLURALANER';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='FURANICI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='GESTAGENI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='ISONIAZIDE ';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='ISOXSUPRINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='LEVAMISOLO ';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='LINCOMICINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='MACROLIDI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='METABOLITI DEI NITROFURANI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='METABOLITI DELLO STANOZOLOLO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='METILCLOPIDOLO ';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='NICARBAZINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='NIFURSOL';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='NITROFURANICI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='NITROIMIDAZOLICI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='PENICILLINE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='PLEUROMUTILINE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='PROMAZINE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='RACTOPAMINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='ROBENIDINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='SALBUTAMOLO-SIMILI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='STANOZANOLO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='STREPTOMICINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='SULFADIAZINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='SULFAMIDICI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='TETRACICLINE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='TIAMFENICOLO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='TIAMULINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='TILOSINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='VALNEMULINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='VERDE MALACHITE E VERDE LEUCOMALACHITE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='VIRGINIAMICINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='RESIDUI FARMACI' and id_padre=3) where enabled and id_padre is null and nome='ZINCOBACITRACINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='STATO DI CONSERVAZIONE' and id_padre=3) where enabled and id_padre is null and nome='ABVT';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='STATO DI CONSERVAZIONE' and id_padre=3) where enabled and id_padre is null and nome='ACIDITA''';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='STATO DI CONSERVAZIONE' and id_padre=3) where enabled and id_padre is null and nome='AMMINE AROMATICHE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='STATO DI CONSERVAZIONE' and id_padre=3) where enabled and id_padre is null and nome='AMMINE BIOGENE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='STATO DI CONSERVAZIONE' and id_padre=3) where enabled and id_padre is null and nome='FORMALDEIDE';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='STATO DI CONSERVAZIONE' and id_padre=3) where enabled and id_padre is null and nome='ISTAMINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='STATO DI CONSERVAZIONE' and id_padre=3) where enabled and id_padre is null and nome='METALDEIDE ';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='STATO DI CONSERVAZIONE' and id_padre=3) where enabled and id_padre is null and nome='NUMERO PEROSSODI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='TOSSICOLOGICO' and id_padre=3) where enabled and id_padre is null and nome='DICUMARINICI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='TOSSICOLOGICO' and id_padre=3) where enabled and id_padre is null and nome='FOSFURO DI ZINCO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='TOSSICOLOGICO' and id_padre=3) where enabled and id_padre is null and nome='SOSTANZE TOSSICHE IN  ESCHE E MATERIALE BIOLOGICO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='TOSSICOLOGICO' and id_padre=3) where enabled and id_padre is null and nome='STRICNINA';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='TOSSICOLOGICO' and id_padre=3) where enabled and id_padre is null and nome='WARFARIN';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='TRATTAMENTO ALIMENTI' and id_padre=3) where enabled and id_padre is null and nome='MONOSSIDO CARBONIO';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='TRATTAMENTO TERMICO' and id_padre=3) where enabled and id_padre is null and nome='FOSFATASI';
update analiti set id_padre=(select analiti_id from analiti where enabled and nome='TRATTAMENTO TERMICO' and id_padre=3) where enabled and id_padre is null and nome='IDROSSIMETILFURFUROLO';


select analiti_id,count(analiti_id), enabled from analiti 
group by analiti_id, enabled 
having count(analiti_id) > 1 
order by count(analiti_id) desc

select * from analiti where analiti_id = 2817
delete from analiti where analiti_id = 2817 and id_padre=14
