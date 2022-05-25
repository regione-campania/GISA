-- script II per gestione ML


CREATE OR REPLACE VIEW linee_livelli_aggiuntivi AS 
 WITH RECURSIVE recursetree(id, id_linea_attivita, nonno, codice_univoco,id_padre, path_id, path_descrizione) AS (
         SELECT master_list_configuratore_livelli_aggiuntivi.id,
            master_list_configuratore_livelli_aggiuntivi.id_linea_attivita,
            master_list_configuratore_livelli_aggiuntivi.id_padre AS nonno,
            master_list_configuratore_livelli_aggiuntivi.codice_univoco,
            master_list_configuratore_livelli_aggiuntivi.id_padre,
            master_list_configuratore_livelli_aggiuntivi.id_padre::character varying(1000) AS path_id,
            master_list_configuratore_livelli_aggiuntivi.valore::character varying(1000) AS path_desc
           FROM master_list_configuratore_livelli_aggiuntivi
          WHERE master_list_configuratore_livelli_aggiuntivi.id_padre = '-1'::integer
        UNION ALL
         SELECT t.id,
            t.id_linea_attivita,
            t.id_padre AS nonno,
            t.codice_univoco,
            t.id_padre,
            (((rt.path_id::text || ';'::text) || t.id_padre))::character varying(1000) AS "varchar",
            (((rt.path_descrizione::text || '->'::text) || t.valore))::character varying(1000) AS path_desc
           FROM master_list_configuratore_livelli_aggiuntivi t
             JOIN recursetree rt ON rt.id = t.id_padre
        )
 SELECT DISTINCT recursetree.id AS id_foglia,
      recursetree.id_linea_attivita,
        CASE
            WHEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 2) IS NOT NULL AND split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 2) <> ''::text THEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 2)::integer
            ELSE '-1'::integer
        END AS id_quarto,
        CASE
            WHEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 3) IS NOT NULL AND split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 3) <> ''::text THEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 3)::integer
            ELSE '-1'::integer
        END AS id_quinto,
        CASE
            WHEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 4) IS NOT NULL AND split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 4) <> ''::text THEN split_part((recursetree.path_id::text || ';'::text) || recursetree.id, ';'::text, 4)::integer
            ELSE '-1'::integer
        END AS id_sesto,
        CASE
            WHEN split_part(recursetree.path_descrizione::text, '->'::text, 1) IS NOT NULL AND split_part(recursetree.path_descrizione::text, '->'::text, 1) <> ''::text THEN split_part(recursetree.path_descrizione::text, '->'::text, 1)
            ELSE 'N.D'::text
        END AS macroarea,
        CASE
            WHEN split_part(recursetree.path_descrizione::text, '->'::text, 2) IS NOT NULL AND split_part(recursetree.path_descrizione::text, '->'::text, 2) <> ''::text THEN split_part(recursetree.path_descrizione::text, '->'::text, 2)
            ELSE 'N.D'::text
        END AS aggregazione,
        CASE
            WHEN split_part(recursetree.path_descrizione::text, '->'::text, 3) IS NOT NULL AND split_part(recursetree.path_descrizione::text, '->'::text, 3) <> ''::text THEN split_part(recursetree.path_descrizione::text, '->'::text, 3)
            ELSE 'N.D'::text
        END AS attivita,
    recursetree.id_padre,
    recursetree.path_id,
    recursetree.path_descrizione,
    (recursetree.path_id::text || ';'::text) || recursetree.id
   FROM recursetree
  ORDER BY ((recursetree.path_id::text || ';'::text) || recursetree.id);


--1° giro di import
select 
distinct 'insert into master_list_configuratore_livelli_aggiuntivi_values(id_configuratore_livelli_aggiuntivi, id_istanza, checked) values('||t.id_foglia||','||o.id||',true);', 
o.id as idistanza, o.id_linea_produttiva_old, o.id_linea_produttiva as linea_new, o.codice_univoco_ml, 
l.path_descrizione, split_part(l.path_descrizione,'->',4) as livello_aggiuntivo_4, 
split_part(l.path_descrizione,'->',5) as livello_aggiuntivo_5, 
split_part(l.path_descrizione,'->',6) as livello_aggiuntivo_6
from 
opu_relazione_stabilimento_linee_produttive o
join opu_linee_attivita_nuove_materializzata l on o.id_linea_produttiva_old = l.id_nuova_linea_attivita
join linee_livelli_aggiuntivi t on t.id_linea_attivita = o.id_linea_produttiva
where o.codice_univoco_ml is not null and split_part(l.path_descrizione,'->',4) = t.path_descrizione 
and t.aggregazione ilike '%N.D%' and t.id_foglia not in (select id_padre from master_list_configuratore_livelli_aggiuntivi)

select * from master_list_configuratore_livelli_aggiuntivi  where codice_univoco  = 'MS.060-MS.060.200-852IT7A105' and valore ilike '%CON VENDITA DI FUNGHI EPIGEI SPONTANEI%'
--recupero l'istanza
insert into master_list_configuratore_livelli_aggiuntivi_values(id_configuratore_livelli_aggiuntivi, id_istanza, checked) values (126,222371,true);


update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'MS.060-MS.060.200-852IT7A105'), codice_univoco_ml = 'MS.060-MS.060.200-852IT7A105' where id_linea_produttiva_old  = 790; --senza vendita di funghi
--recupero l'istanza
select 'insert into master_list_configuratore_livelli_aggiuntivi_values(id_configuratore_livelli_aggiuntivi, id_istanza, checked) values 
((select id from master_list_configuratore_livelli_aggiuntivi  where codice_univoco  = ''MS.060-MS.060.200-852IT7A105'' and valore ilike ''%SENZA VENDITA DI FUNGHI EPIGEI SPONTANEI%''),
'||o.id||',true);'
from opu_relazione_stabilimento_linee_produttive o where o.id_linea_produttiva_old = 790;

update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco = 'MS.060-MS.060.100-852IT7A003'), codice_univoco_ml = 'MS.060-MS.060.100-852IT7A003' where id_linea_produttiva_old  = 767; 
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'MS.000-MS.000.600-018'), codice_univoco_ml = 'MS.000-MS.000.600-018' where id_linea_produttiva_old  = 33; 
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'MS.A-MS.A30.500-852ITBA'), codice_univoco_ml = 'MS.A-MS.A30.500-852ITBA' where id_linea_produttiva_old  = 6518; 
select 'insert into master_list_configuratore_livelli_aggiuntivi_values(id_configuratore_livelli_aggiuntivi, id_istanza, checked) values 
((select id from master_list_configuratore_livelli_aggiuntivi  where codice_univoco  = ''MS.A-MS.A30.500-852ITBA'' and valore ilike ''%BIBITE ANALCOLICHE, E ALTRE BEVANDE ADDIZIONATE DI VITAMINE E MINERALI%''),
'||o.id||',true);'
from opu_relazione_stabilimento_linee_produttive o where o.id_linea_produttiva_old = 6518;

update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'MS.A-MS.A30.500-852ITBA'), codice_univoco_ml = 'MS.A-MS.A30.500-852ITBA' where id_linea_produttiva_old  = 6517; 
select 'insert into master_list_configuratore_livelli_aggiuntivi_values(id_configuratore_livelli_aggiuntivi, id_istanza, checked) values 
((select id from master_list_configuratore_livelli_aggiuntivi  where codice_univoco  = ''MS.A-MS.A30.500-852ITBA'' and valore ilike ''%nca%''),
'||o.id||',true);'
from opu_relazione_stabilimento_linee_produttive o where o.id_linea_produttiva_old = 6517;

update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'MS.A-MS.A30.500-852ITBA'), codice_univoco_ml = 'MS.A-MS.A30.500-852ITBA' where id_linea_produttiva_old  = 6516;
select 'insert into master_list_configuratore_livelli_aggiuntivi_values(id_configuratore_livelli_aggiuntivi, id_istanza, checked) values 
((select id from master_list_configuratore_livelli_aggiuntivi  where codice_univoco  = ''MS.A-MS.A30.500-852ITBA'' and valore ilike ''%PRODOTTI DA FORNO%''),
'||o.id||',true);'
from opu_relazione_stabilimento_linee_produttive o where o.id_linea_produttiva_old = 6516;

update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'MS.A-MS.A30.300-011'), codice_univoco_ml = 'MS.A-MS.A30.300-011' where id_linea_produttiva_old  = 6521;
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'MS.A-MS.A11-PC-SG'), codice_univoco_ml = 'MS.A-MS.A11-PC-SG' where id_linea_produttiva_old  = 6510;
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = '1069-R-1-STORP_T'), codice_univoco_ml = '1069-R-1-STORP_T' where id_linea_produttiva_old  = 8101;
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'MS.090-TCT-T'), codice_univoco_ml = 'MS.090-TCT-T' where id_linea_produttiva_old  = 816;
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-NAZ-E-006'), codice_univoco_ml = 'CG-NAZ-E-006' where id_linea_produttiva_old  = 8260;
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-EST-E-0124'), codice_univoco_ml = 'CG-EST-E-0124' where id_linea_produttiva_old  = 1586;
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-NAZ-B'), codice_univoco_ml = 'CG-NAZ-B' where id_linea_produttiva_old  = 1570;
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-NAZ-E-006'), codice_univoco_ml = 'CG-NAZ-E-006' where id_linea_produttiva_old  = 8259;
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-EST-B-0124'), codice_univoco_ml = 'CG-EST-B-0124' where id_linea_produttiva_old  = 1587;
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-EST-E-0125'), codice_univoco_ml = 'CG-EST-E-0125' where id_linea_produttiva_old  = 1591;
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-NAZ-E-004'), codice_univoco_ml = 'CG-NAZ-E-004' where id_linea_produttiva_old  = 8261;
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-EST-E-0126'), codice_univoco_ml = 'CG-EST-E-0126' where id_linea_produttiva_old  = 1581;
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-EST-B-0125'), codice_univoco_ml = 'CG-EST-B-0125' where id_linea_produttiva_old  = 1592;

---flag pnaa

update ml8_master_list set flag_pnaa = true where codice = 'MS.080';
update ml8_master_list set flag_pnaa = true where codice = 'MS.070';
update ml8_master_list set flag_pnaa = true where codice = 'MS.060';
update ml8_master_list set flag_pnaa = true where codice = 'MS.040';
update ml8_master_list set flag_pnaa = true where codice = 'MS.020';
update ml8_master_list set flag_pnaa = true where codice = 'MS.030';
update ml8_master_list set flag_pnaa = true where codice = 'MS.050';
update ml8_master_list set flag_pnaa = true where codice = 'MS.090';
update ml8_master_list set flag_pnaa = true where codice = 'MS.010';
update ml8_master_list set flag_pnaa = true where codice = 'MG';
update ml8_master_list set flag_pnaa = true where codice = '1069-R';
update ml8_master_list set flag_pnaa = true where codice = 'MR';
update ml8_master_list set flag_pnaa = true where codice = 'MS.A';
update ml8_master_list set flag_pnaa = true where codice = 'MS.B';

--update ml8_master_list  set id_norma  = 41 where id_norma = 5;
--update ml8_master_list  set id_norma  = 39 where id_norma = 9;
--update opu_lookup_norme_master_list_rel_tipologia_organzation  set id_opu_lookup_norme_master_list  = 43 where id_opu_lookup_norme_master_list in (18,19,21,31,28);
--update opu_lookup_norme_master_list_rel_tipologia_organzation  set id_opu_lookup_norme_master_list  = 43 where id_opu_lookup_norme_master_list in (18,19,21,31,28,27,32);
--update opu_lookup_norme_master_list_rel_tipologia_organzation  set id_opu_lookup_norme_master_list  = 40 where id_opu_lookup_norme_master_list in (35);
--update opu_lookup_norme_master_list_rel_tipologia_organzation  set id_opu_lookup_norme_master_list  = 38 where id_opu_lookup_norme_master_list in (10);
--update master_list_macroarea set id_norma=46 where codice_norma='1069-09';
--insert into opu_lookup_norme_master_list (code,description,level,enabled, codice_norma) values (47,'362-91',99,true,'362-91');
--insert into opu_lookup_norme_master_list (code,description,level,enabled, codice_norma) values (48,'219-06',99,true,'219-06');
--update master_list_macroarea set id_norma=47 where codice_norma='362-91';
--update master_list_macroarea set id_norma=48 where codice_norma='219-06';

delete from ml8_linee_attivita_nuove_materializzata;
insert into ml8_linee_attivita_nuove_materializzata (select * from ml8_linee_attivita_nuove);

select count (id_stabilimento||'-'||id_linea_produttiva), id_stabilimento, id_linea_produttiva, max(id),
'update opu_relazione_stabilimento_linee_produttive set enabled = false, note_internal_use_hd_only = ''Disabilitata per duplicato dopo passaggio a ML8. Duplicata di: ' || max(id) ||''' where id_stabilimento = ' || id_stabilimento || ' and id_linea_produttiva = ' || id_linea_produttiva || ' and id <> ' || max(id)|| ';'
from opu_relazione_stabilimento_linee_produttive where enabled
group by id_stabilimento, id_linea_produttiva having count (id_stabilimento||'-'||id_linea_produttiva)>1
order by 1 desc;

select id, replace(note_internal_use_hd_only,
'Disabilitata per duplicato dopo passaggio a ML8. Duplicata di: ', '') as id_nuovo,
'update opu_linee_attivita_controlli_ufficiali set id_linea_attivita = ' || replace(note_internal_use_hd_only, 'Disabilitata per duplicato dopo passaggio a ML8. Duplicata di: ', '')  || ', note= ''Spostato da istanza ' || id ||''' where id_linea_attivita = ' || id || ';'
 from opu_relazione_stabilimento_linee_produttive where note_internal_use_hd_only ilike '%Disabilitata per duplicato dopo passaggio a ML8%';