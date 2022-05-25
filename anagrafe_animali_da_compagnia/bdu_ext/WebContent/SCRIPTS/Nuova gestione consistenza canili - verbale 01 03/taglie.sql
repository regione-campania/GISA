
alter table lookup_taglia add column description_estesa character varying(300);

update lookup_taglia set description = 'Piccola', description_estesa = 'Piccola: fino a Kg 2'  where code = 1;
update lookup_taglia set description = 'Media',   description_estesa = 'Media: da Kg 2 a 8'  where code = 2;
update lookup_taglia set description = 'Grande',  description_estesa = 'Grande: da Kg 8 a 15'  where code = 3;
update lookup_taglia set description = 'Gigante', description_estesa = 'Gigante: sopra ai Kg 15'  where code = 5;
update lookup_taglia set description_estesa = 'N.D.'  where code = 4;
