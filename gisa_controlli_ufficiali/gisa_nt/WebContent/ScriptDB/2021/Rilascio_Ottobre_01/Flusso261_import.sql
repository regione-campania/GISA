-- Aggiungo la colonna delle note

alter table modello_pnaa_values add column note_hd text;

-- Query esterna, costruisco update

SELECT 

pnaa3.id, pnaa3.id_campione, 
pnaa3.lista_specie_alimento_destinato_old, 
pnaa3.lista_specie_id_new,
pnaa3.lista_specie_description_old, 
pnaa3.lista_specie_description_new,
'update modello_pnaa_values set note_hd = concat_ws('';'', note_hd, ''Flusso 261 - Lista specie alimento destinato modificata da '|| pnaa3.lista_specie_description_old ||'- a -'|| pnaa3.lista_specie_description_new ||'''), lista_specie_alimento_destinato = ''' || pnaa3.lista_specie_id_new || ''' where id = ' || pnaa3.id ||';'

FROM (

-- Query 3, concateno i nuovi ID

SELECT 

pnaa2.id, pnaa2.id_campione, pnaa2.lista_specie_alimento_destinato_old, 
concat_ws(',', pnaa2.specieold_1_code, pnaa2.specieold_2_code, pnaa2.specieold_3_code, pnaa2.specieold_4_code, pnaa2.specieold_5_code, pnaa2.specieold_6_code, pnaa2.specieold_7_code, pnaa2.specieold_8_code, pnaa2.specieold_9_code, pnaa2.specieold_10_code) as lista_specie_id_old,
concat_ws(',', pnaa2.specieold_1_description, pnaa2.specieold_2_description, pnaa2.specieold_3_description, pnaa2.specieold_4_description, pnaa2.specieold_5_description, pnaa2.specieold_6_description, pnaa2.specieold_7_description, pnaa2.specieold_8_description, pnaa2.specieold_9_description, pnaa2.specieold_10_description) as lista_specie_description_old,
concat_ws(',', pnaa2.specienew_1_code, pnaa2.specienew_2_code, pnaa2.specienew_3_code, pnaa2.specienew_4_code, pnaa2.specienew_5_code, pnaa2.specienew_6_code, pnaa2.specienew_7_code, pnaa2.specienew_8_code, pnaa2.specienew_9_code, pnaa2.specienew_10_code) as lista_specie_id_new,
concat_ws(',', pnaa2.specienew_1_description, pnaa2.specienew_2_description, pnaa2.specienew_3_description, pnaa2.specienew_4_description, pnaa2.specienew_5_description, pnaa2.specienew_6_description, pnaa2.specienew_7_description, pnaa2.specienew_8_description, pnaa2.specienew_9_description, pnaa2.specienew_10_description) as lista_specie_description_new

FROM (

-- Query 2, estrazione dei nuovi ID a partire dai vecchi

SELECT 
pnaa.id, pnaa.id_campione, pnaa.lista_specie_alimento_destinato as lista_specie_alimento_destinato_old,

specieold_1.code as specieold_1_code, specieold_1.description as specieold_1_description,
specieold_2.code as specieold_2_code, specieold_2.description as specieold_2_description,
specieold_3.code as specieold_3_code, specieold_3.description as specieold_3_description,
specieold_4.code as specieold_4_code, specieold_4.description as specieold_4_description,
specieold_5.code as specieold_5_code, specieold_5.description as specieold_5_description,
specieold_6.code as specieold_6_code, specieold_6.description as specieold_6_description,
specieold_7.code as specieold_7_code, specieold_7.description as specieold_7_description,
specieold_8.code as specieold_8_code, specieold_8.description as specieold_8_description,
specieold_9.code as specieold_9_code, specieold_9.description as specieold_9_description,
specieold_10.code as specieold_10_code, specieold_10.description as specieold_10_description,

specienew_1.code as specienew_1_code, specienew_1.description as specienew_1_description,
specienew_2.code as specienew_2_code, specienew_2.description as specienew_2_description,
specienew_3.code as specienew_3_code, specienew_3.description as specienew_3_description,
specienew_4.code as specienew_4_code, specienew_4.description as specienew_4_description,
specienew_5.code as specienew_5_code, specienew_5.description as specienew_5_description,
specienew_6.code as specienew_6_code, specienew_6.description as specienew_6_description,
specienew_7.code as specienew_7_code, specienew_7.description as specienew_7_description,
specienew_8.code as specienew_8_code, specienew_8.description as specienew_8_description,
specienew_9.code as specienew_9_code, specienew_9.description as specienew_9_description,
specienew_10.code as specienew_10_code, specienew_10.description as specienew_10_description

 FROM (

-- Query 1, splitto le specie in colonne separate

SELECT 

id, id_campione, lista_specie_alimento_destinato,
split_part(lista_specie_alimento_destinato, ',', 1) as specieold_1,
split_part(lista_specie_alimento_destinato, ',', 2) as specieold_2,
split_part(lista_specie_alimento_destinato, ',', 3) as specieold_3,
split_part(lista_specie_alimento_destinato, ',', 4) as specieold_4,
split_part(lista_specie_alimento_destinato, ',', 5) as specieold_5,
split_part(lista_specie_alimento_destinato, ',', 6) as specieold_6,
split_part(lista_specie_alimento_destinato, ',', 7) as specieold_7,
split_part(lista_specie_alimento_destinato, ',', 8) as specieold_8,
split_part(lista_specie_alimento_destinato, ',', 9) as specieold_9,
split_part(lista_specie_alimento_destinato, ',', 10) as specieold_10

FROM 

modello_pnaa_values where lista_specie_alimento_destinato <> '' and lista_specie_alimento_destinato <> '-1,'

) pnaa

left join lookup_specie_alimento specieold_1 on specieold_1.code::text = pnaa.specieold_1
left join lookup_specie_alimento specieold_2 on specieold_2.code::text = pnaa.specieold_2
left join lookup_specie_alimento specieold_3 on specieold_3.code::text = pnaa.specieold_3
left join lookup_specie_alimento specieold_4 on specieold_4.code::text = pnaa.specieold_4
left join lookup_specie_alimento specieold_5 on specieold_5.code::text = pnaa.specieold_5
left join lookup_specie_alimento specieold_6 on specieold_6.code::text = pnaa.specieold_6
left join lookup_specie_alimento specieold_7 on specieold_7.code::text = pnaa.specieold_7
left join lookup_specie_alimento specieold_8 on specieold_8.code::text = pnaa.specieold_8
left join lookup_specie_alimento specieold_9 on specieold_9.code::text = pnaa.specieold_9
left join lookup_specie_alimento specieold_10 on specieold_10.code::text = pnaa.specieold_10

left join lookup_specie_pnaa specienew_1 on specienew_1.code::text = specieold_1.selvaggia_lucarelli
left join lookup_specie_pnaa specienew_2 on specienew_2.code::text = specieold_2.selvaggia_lucarelli
left join lookup_specie_pnaa specienew_3 on specienew_3.code::text = specieold_3.selvaggia_lucarelli
left join lookup_specie_pnaa specienew_4 on specienew_4.code::text = specieold_4.selvaggia_lucarelli
left join lookup_specie_pnaa specienew_5 on specienew_5.code::text = specieold_5.selvaggia_lucarelli
left join lookup_specie_pnaa specienew_6 on specienew_6.code::text = specieold_6.selvaggia_lucarelli
left join lookup_specie_pnaa specienew_7 on specienew_7.code::text = specieold_7.selvaggia_lucarelli
left join lookup_specie_pnaa specienew_8 on specienew_8.code::text = specieold_8.selvaggia_lucarelli
left join lookup_specie_pnaa specienew_9 on specienew_9.code::text = specieold_9.selvaggia_lucarelli
left join lookup_specie_pnaa specienew_10 on specienew_10.code::text = specieold_10.selvaggia_lucarelli

) pnaa2  

) pnaa3;

update modello_pnaa_values set lista_specie_alimento_destinato = concat(lista_specie_alimento_destinato, ',') where lista_specie_alimento_destinato not like '%,';

--update lookup_specie_alimento  set enabled = false where code in (4,5,2,3)

