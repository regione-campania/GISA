-- GENERO LA MACROAREA CON CODICE MS000 E NORMA 852
insert into master_list_macroarea (codice_sezione, codice_norma, norma, macroarea, id_norma) values ('MS.000', '852-04', 'REG CE 852-04', 'PRODUZIONE PRIMARIA', 1);

-- AGGIORNO LE AGGREGAZIONI CHE PUNTAVANO ALLA MACROAREA SBAGLIATA

update master_list_aggregazione set id_macroarea = (select id from master_list_macroarea where codice_sezione = 'MS.000' and id_norma=1) where id in (20080,
20088,
20089,
20080,
20087,
20091,
20174,
20081,
20082,
20083,
20084,
20085,
20086,
20090);



-- AGGIORNO ML8_LINEE_NUOVE_MATERIALIZZATA

update ml8_linee_attivita_nuove_materializzata set id_macroarea = (select id from master_list_macroarea where codice_sezione = 'MS.000' and id_norma=1), id_norma = 1 where id_aggregazione in (20080,
20088,
20089,
20080,
20087,
20091,
20174,
20081,
20082,
20083,
20084,
20085,
20086,
20090);

-- AGGIORNO RICERCHE

delete from ricerche_anagrafiche_old_materializzata;
insert into ricerche_anagrafiche_old_materializzata  (select * from ricerca_anagrafiche) ;
