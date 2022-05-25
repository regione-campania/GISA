
 
create table mapping_ml6_ml8 (ml6_linea_id integer, ml6_codice_univoco text, ml8_linea_id integer, ml8_codice_univoco text);

insert into mapping_ml6_ml8 
select ml6.id, ml6.codice_univoco, ml8.id, ml8.codice_univoco
 from suap_master_list_6_linea_attivita ml6
 left join  master_list_linea_attivita ml8 on ml6.codice_univoco ilike ml8.codice_univoco


 update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-1-STORP_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-1-STORP_T') where ml6_codice_univoco = '1069-R-1-STORP';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-2-STORP_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-2-STORP_T') where ml6_codice_univoco = '1069-R-2-STORP';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-3-STORP_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-3-STORP_T') where ml6_codice_univoco = '1069-R-3-STORP';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-4-STORP_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-4-STORP_T') where ml6_codice_univoco = '1069-R-4-';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-5-STORP_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-5-STORP_T') where ml6_codice_univoco = '1069-R-5-';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-6-STORP_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-6-STORP_T') where ml6_codice_univoco = '1069-R-6-';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-28-OTHER_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-28-OTHER_T') where ml6_codice_univoco = '1069-R-28-OTHER';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-29-OTHER_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-29-OTHER_T') where ml6_codice_univoco = '1069-R-29-OTHER';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-30-OTHER_T', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-30-OTHER_T') where ml6_codice_univoco = '1069-R-30-OTHER';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-31-OTHER_U', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-31-OTHER_U') where ml6_codice_univoco = '1069-R-31-OTHER';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-32-OTHER_U', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-32-OTHER_U') where ml6_codice_univoco = '1069-R-32-OTHER';
update mapping_ml6_ml8 set ml8_codice_univoco = '1069-R-33-OTHER_U', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = '1069-R-33-OTHER_U') where ml6_codice_univoco = '1069-R-33-OTHER';
update mapping_ml6_ml8 set ml8_codice_univoco = 'MS.A-MS.A30.500-852ITBA', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = 'MS.A-MS.A30.500-852ITBA') where ml6_codice_univoco = 'MS.A-MS.A30.500-852ITBA005';
update mapping_ml6_ml8 set ml8_codice_univoco = 'MS.A-MS.A30.500-852ITBA', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = 'MS.A-MS.A30.500-852ITBA') where ml6_codice_univoco = 'MS.A-MS.A30.500-852ITBA004';
update mapping_ml6_ml8 set ml8_codice_univoco = 'MS.A-MS.A30.500-852ITBA', ml8_linea_id = (select id from master_list_linea_attivita where codice_univoco = 'MS.A-MS.A30.500-852ITBA') where ml6_codice_univoco = 'MS.A-MS.A30.500-852ITBA003';



  select ml6_linea_id, 'update sintesis_relazione_stabilimento_linee_produttive set id_linea_produttiva = ' || ml8_linea_id || ', note=''Linea modificata ml6->ml8'', id_vecchia_linea =id_linea_produttiva where id_linea_produttiva = ' || ml6_linea_id || ';' from mapping_ml6_ml8; 
  
  update campi_estesi_templates_v2  set id_linea = 42519 where id_linea = 394;
update campi_estesi_templates_v2  set id_linea = 42551 where id_linea = 426;



---


  alter table opu_stabilimento add column linee_pregresse_old boolean default false;
  update opu_stabilimento set linee_pregresse_old = linee_pregresse;
  update opu_stabilimento set linee_pregresse = false;
  update opu_stabilimento set linee_pregresse = true where id in (select s.id
 from opu_relazione_stabilimento_linee_produttive rel
join opu_stabilimento s on s.id = rel.id_stabilimento
  where rel.id_linea_produttiva <40000 and rel.enabled  and s.trashed_date is null)


