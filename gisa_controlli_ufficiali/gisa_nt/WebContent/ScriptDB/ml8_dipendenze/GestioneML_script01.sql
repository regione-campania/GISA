
alter table opu_relazione_stabilimento_linee_produttive add column codice_univoco_ml text;
alter table opu_relazione_stabilimento_linee_produttive add column id_linea_produttiva_old int;
alter table suap_ric_scia_relazione_stabilimento_linee_produttive  add column codice_univoco_ml text;
alter table suap_ric_scia_relazione_stabilimento_linee_produttive  add column id_linea_produttiva_old int;

update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva_old  = id_linea_produttiva;
update master_list_linea_attivita set codice_univoco =  'MS.060-MS.060.100-852IT7A002' where linea_attivita ilike '%INTERMEDIARI SENZA DEPOSITI%';

select distinct linee.id_linea_produttiva, l.macroarea, l.aggregazione, l.attivita, m.*,
'update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml='''||m.codice_univoco_ml6||''' where id_linea_produttiva='||linee.id_linea_produttiva||';'
from opu_linee_attivita_nuove_materializzata l
join mapping_orsa_ml5_ml6 m on m.aggregazione = l.aggregazione and m.attivita = l.attivita
join opu_relazione_stabilimento_linee_produttive linee on linee.id_linea_produttiva = l.id_nuova_linea_attivita
where (l.macroarea is not null and l.macroarea <> 'N.D') and (l.aggregazione is not null and l.aggregazione <> 'N.D') and 
(l.attivita is not null and l.attivita <> 'N.D') and linee.enabled


----------------------------------- vai
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = '1069-R-30-UFERT', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='1069-R-30-UFERT') where id_linea_produttiva_old in (8068,8069,8070);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MG-OPRA-M01', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MG-OPRA-M01') where id_linea_produttiva_old = 569;
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MG-OPRA-M02', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MG-OPRA-M02') where id_linea_produttiva_old = 577;
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MG-DG-M12', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MG-DG-M12') where id_linea_produttiva_old = 590;
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MG-DG-M17', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MG-DG-M17') where id_linea_produttiva_old = 598;
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.060-MS.060.200-852IT7A103', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MS.060-MS.060.200-852IT7A103') where id_linea_produttiva_old = 783; --> senza vendita
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.060-MS.060.200-852IT7A102', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MS.060-MS.060.200-852IT7A102') where id_linea_produttiva_old in (772,778,779); --> con vendita/senza vendita
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.060-MS.060.200-852IT7A101-B', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MS.060-MS.060.200-852IT7A101-B') where id_linea_produttiva_old in (781); --> con vendita/senza vendita

update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.010-MS.010.800-852IT2A701', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MS.010-MS.010.800-852IT2A701') where id_linea_produttiva_old in (704);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.010-MS.010.100-852IT2A003', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MS.010-MS.010.100-852IT2A003') where id_linea_produttiva_old in (
663,
678,
673,
679,
676,
672
);

update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.050-MS.050.200-007', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MS.050-MS.050.200-007') where id_linea_produttiva_old in (755);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.050-MS.050.200-006', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MS.050-MS.050.200-006') where id_linea_produttiva_old in (744,760,761);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'FITO-E500A-E510A', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='FITO-E500A-E510A') where id_linea_produttiva_old in (
1566,
8141,
1563,
1568,
1562);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'CG-NAZ-D-0126', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='CG-NAZ-D-0126') where id_linea_produttiva_old = 1565;
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'FVET-COMM-VDET', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='FVET-COMM-VDET') where id_linea_produttiva_old =632;
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'FVET-COMM-SVDET', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='FVET-COMM-SVDET') where id_linea_produttiva_old =633;
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.050-MS.050.100-852IT6A002', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MS.050-MS.050.100-852IT6A002') where id_linea_produttiva_old =738;
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.050-MS.050.200-003', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MS.050-MS.050.200-003') where id_linea_produttiva_old =756;
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.050-MS.050.200-004', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MS.050-MS.050.200-004') where id_linea_produttiva_old =758;
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.040-MS.040.200-852IT5A101', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MS.040-MS.040.200-852IT5A101') where id_linea_produttiva_old = 727;
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.060-MS.060.200-852IT7A103', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MS.060-MS.060.200-852IT7A103') where id_linea_produttiva_old = 780;
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MR-DR-M25', id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita='MR-DR-M25') where id_linea_produttiva_old = 7398;

update suap_ric_scia_relazione_stabilimento_linee_produttive set id_linea_produttiva_old  = id_linea_produttiva;

--aggiorno codice univoco per le richieste
select distinct id_linea_produttiva,
'update suap_ric_scia_relazione_stabilimento_linee_produttive set codice_univoco_ml='''||m.codice_univoco_ml6||''' where id_linea_produttiva='||linee.id_linea_produttiva||';'
from opu_linee_attivita_nuove_materializzata l
join mapping_orsa_ml5_ml6 m on m.aggregazione = l.aggregazione and m.attivita = l.attivita
join suap_ric_scia_relazione_stabilimento_linee_produttive linee on linee.id_linea_produttiva = l.id_nuova_linea_attivita
where (l.macroarea is not null and l.macroarea <> 'N.D') and (l.aggregazione is not null and l.aggregazione <> 'N.D') and 
(l.attivita is not null and l.attivita <> 'N.D') and linee.enabled

--aggiorno id_linea_produttiva con il nuovo id
select distinct id_linea_produttiva, 'update suap_ric_scia_relazione_stabilimento_linee_produttive set id_linea_produttiva = '||m.id||' where codice_univoco_ml='''||m.codice||''';' from ml8_master_list  m
join suap_ric_scia_relazione_stabilimento_linee_produttive s on s.codice_univoco_ml = m.codice
where s.enabled 

select distinct id_linea_produttiva, 'update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva = '||m.id||' where codice_univoco_ml='''||m.codice||''';' from ml8_master_list  m
join opu_relazione_stabilimento_linee_produttive s on s.codice_univoco_ml = m.codice
where s.enabled 

--aggiornamento id_tipologia scia (RICONOSCIUTI O REGISTRATI)
alter table suap_ric_scia_stabilimento add column tipo_reg_ric_old integer;
update suap_ric_scia_stabilimento set tipo_reg_ric_old  =tipo_reg_ric ;
update suap_ric_scia_stabilimento set tipo_reg_ric  = 4 where tipo_reg_ric = 1;
update suap_ric_scia_stabilimento set tipo_reg_ric = 6 where tipo_reg_ric = 2;
update suap_ric_scia_stabilimento set tipo_reg_ric = 2 where tipo_reg_ric = 3;

-- recupero linee di attività
select distinct(id_linea_produttiva), path_descrizione, o.attivita, 
'update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = '''||m.codice_univoco||''', 
id_linea_produttiva = '||m.id||' where id_linea_produttiva_old='||l.id_linea_produttiva_old||';'
from opu_relazione_stabilimento_linee_produttive l
join opu_linee_attivita_nuove o on o.id_nuova_linea_attivita = l.id_linea_produttiva
join master_list_linea_attivita m on m.linea_attivita ilike o.attivita 
where o.enabled and codice_univoco_ml is null and path_descrizione ilike '%mangimistica in genere%'

-- 2 giro
select distinct(id_linea_produttiva), path_descrizione, o.attivita, 
'update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = '''||m.codice_univoco||''', 
id_linea_produttiva = '||m.id||' where id_linea_produttiva_old='||l.id_linea_produttiva_old||';'
from opu_relazione_stabilimento_linee_produttive l
join opu_linee_attivita_nuove o on o.id_nuova_linea_attivita = l.id_linea_produttiva
join master_list_linea_attivita m on m.linea_attivita ilike o.attivita 
where o.enabled and codice_univoco_ml is null and l.id_linea_produttiva_old not in (1555,1556,1557,1559,1561,837,740,742)

--3 giro
select distinct(id_linea_produttiva), path_descrizione, o.attivita, 
'update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = '''||m.codice_univoco||''', 
id_linea_produttiva = '||m.id||' where id_linea_produttiva_old='||l.id_linea_produttiva_old||';'
from opu_relazione_stabilimento_linee_produttive l
join opu_linee_attivita_nuove o on o.id_nuova_linea_attivita = l.id_linea_produttiva
join master_list_linea_attivita m on m.linea_attivita ilike o.attivita 
where o.enabled and codice_univoco_ml is null

--4 giro
select distinct(id_linea_produttiva), path_descrizione, o.attivita, 
'update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = '''||m.codice_univoco||''', 
id_linea_produttiva = '||m.id||' where id_linea_produttiva_old='||l.id_linea_produttiva_old||';'
from opu_relazione_stabilimento_linee_produttive l
join opu_linee_attivita_nuove o on o.id_nuova_linea_attivita = l.id_linea_produttiva
join master_list_linea_attivita m on m.linea_attivita ilike '%'||o.attivita||'%'
where o.enabled and codice_univoco_ml is null and o.livello = 3

--5 giro (escludere i SOA)
select distinct(l.id_linea_produttiva) as id_linea_vecchia, o.path_descrizione, o.attivita, 
'update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = '''||m.codice||''', id_linea_produttiva = '||m.id_nuova_linea_attivita||' where id='||l.id||';'
from opu_relazione_stabilimento_linee_produttive l
join opu_linee_attivita_nuove o on o.id_nuova_linea_attivita = l.id_linea_produttiva
join ml8_linee_attivita_nuove_materializzata m on m.descrizione ilike '%'||o.attivita||'%' 
where o.enabled and codice_univoco_ml is null and o.livello > 3 

alter table linee_mobili_html_fields  add column id_linea_produttiva_old int;
update linee_mobili_html_fields set id_linea_produttiva_old = id_linea;

select distinct(o.id_linea_produttiva_old), 'update linee_mobili_html_fields set id_linea='||o.id_linea_produttiva||' where id_linea_produttiva_old='||l.id_linea||';' from linee_mobili_html_fields  l
join opu_relazione_stabilimento_linee_produttive o on o.id_linea_produttiva_old = l.id_linea
where o.enabled


update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'MS.A-MS.A30.300-011') where id_linea_produttiva_old  = 6521;
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'MS.A-MS.A11-PC-SG') where id_linea_produttiva_old  = 6510;
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = '1069-R-1-STORP_T') where id_linea_produttiva_old  = 8101;
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata  where codice_attivita  ='MS.M-MCA-A07PZ-1') ,codice_univoco_ml = 'MS.M-MCA-A07PZ-1' where id_linea_produttiva = 839; 
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata  where codice  ='MS.010.100') ,codice_univoco_ml = 'MS.010.100' where id_linea_produttiva = 656; 
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata  where codice  ='MS.B-MS.B30-MS.B30.100') ,codice_univoco_ml = 'MS.B-MS.B30-MS.B30.100' where id_linea_produttiva = 7079; 
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva = (select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata  where codice  ='MS.A-MS.A30.300-013') ,codice_univoco_ml = 'MS.A-MS.A30.300-013' where id_linea_produttiva = 6529; 
update linee_mobili_html_fields set id_linea= (select id from master_list_linea_attivita  where codice_univoco  = 'MS.090-TCT-T') where id_linea_produttiva_old  = 816;
update linee_mobili_html_fields set id_linea= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-NAZ-E-006') where id_linea_produttiva_old  = 8260;
update linee_mobili_html_fields  set id_linea = (select id from master_list_linea_attivita  where codice_univoco  = 'CG-EST-E-0124') where id_linea_produttiva_old  = 1586;
update linee_mobili_html_fields set id_linea= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-NAZ-B') where id_linea_produttiva_old  = 1570;
update linee_mobili_html_fields set id_linea= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-NAZ-E-006') where id_linea_produttiva_old  = 8259;
update linee_mobili_html_fields set id_linea= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-EST-B-0124')where id_linea_produttiva_old  = 1587;
update linee_mobili_html_fields set id_linea= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-EST-E-0125') where id_linea_produttiva_old  = 1591;
update linee_mobili_html_fields set id_linea= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-NAZ-E-004') where id_linea_produttiva_old  = 8261;
update linee_mobili_html_fields set id_linea= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-EST-E-0126') where id_linea_produttiva_old  = 1581;
update linee_mobili_html_fields set id_linea= (select id from master_list_linea_attivita  where codice_univoco  = 'CG-EST-B-0125') where id_linea_produttiva_old  = 1592;

--alter table knowledge_based_mapping add column id_linea_produttiva_old int;
--update knowledge_based_mapping set id_linea_produttiva_old = id_nuova_linea::int;

--select distinct 'update knowledge_based_mapping set id_nuova_linea ='||l.id_linea_produttiva||' where id_nuova_linea='''||k.id_nuova_linea||''';'
--from knowledge_based_mapping  k
--join opu_relazione_stabilimento_linee_produttive l on 
--k.id_nuova_linea::int = l.id_linea_produttiva_old::int 



--da recuperare le linee di attività
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MG', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MG') where id_linea_produttiva_old in(8082,8091);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MG-DG', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MG->DG') where id_linea_produttiva_old in(8093,594,592,8090,8093);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'ALT-FORM', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'ALT->FORM') where id_linea_produttiva_old in (1595,1601);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.060-MS.060.100', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.060->MS.060.100') where id_linea_produttiva_old in (762);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.060-MS.060.200', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.060->MS.060.200') where id_linea_produttiva_old in (764);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.060-MS.060.300', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.060->MS.060.300') where id_linea_produttiva_old in (782);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.060-MS.060.400', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.060->MS.060.400') where id_linea_produttiva_old in (788);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.080-MS.080.100', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.080->MS.080.100') where id_linea_produttiva_old in (8253,8254);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.000-ALPET', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.000->ALPET') where id_linea_produttiva_old in (635);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MR-DR-M28', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MR->DR->M28') where id_linea_produttiva_old in (7402);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MG-DG-M12', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MG->DG->M12') where id_linea_produttiva_old in (578);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MG-OPRZ-M02', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MG->OPRZ->M02') where id_linea_produttiva_old in (580);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MG-OSMM-M29', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MG->OSMM->M29')  where id_linea_produttiva_old in(609,8073,8078);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.040-MS.040.300-852IT5A201', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.040-MS.040.300-852IT5A201')  where id_linea_produttiva_old in(729);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MR-DR', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MR->DR')  where id_linea_produttiva_old in(8079);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MG-DG-M12', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MG->DG->M12')  where id_linea_produttiva_old in(8084,8089);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MG-DG-M19', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MG->DG->M19')  where id_linea_produttiva_old in(8094,8088);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.090-TCT-T', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.090->TCT->T') where id_linea_produttiva_old in(8083);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MG-OPRZ-M02', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MG->OPRZ->M02') where id_linea_produttiva_old in(8085);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.090-TCT-T', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.090->TCT->T') where id_linea_produttiva_old in(8086);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MG-OPRA-M01', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MG->OPRA->M01') where id_linea_produttiva_old in(8087);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MG-DG-M10', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MG->DG->M10') where id_linea_produttiva_old = 8095;
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MG-DG-M39', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MG->DG->M39') where id_linea_produttiva_old = 8097;
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.000', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.000') where id_linea_produttiva_old =4902;
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.000-MS.000.600-018', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.000->MS.000.600->018') where id_linea_produttiva_old =4907;
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.000-MS.000.400-852IT1A301', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.000->MS.000.400->852IT1A301') where id_linea_produttiva_old =26;
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'CG-NAZ', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'CG->NAZ') where id_linea_produttiva_old in(1560,8143,8144,8262);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.050-MS.050.200', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.050->MS.050.200') where id_linea_produttiva_old in(735);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.A-MS.A30.300-013', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.A->MS.A30.300->013') where id_linea_produttiva_old in(6259);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.A-MS.A30.300-011', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.A->MS.A30.300->011') where id_linea_produttiva_old in(6530);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'VET-AMBV', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'VET->AMBV') where id_linea_produttiva_old in(651);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'VET-CLIV', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'VET->CLIV') where id_linea_produttiva_old in(652);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'VET-OSPV', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'VET->OSPV') where id_linea_produttiva_old in(657);
  update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MS.090', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.090') where id_linea_produttiva_old in(797);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.090-MS.090.100', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.090->MS.090.100') where id_linea_produttiva_old in(301);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.050', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.050') where id_linea_produttiva_old in(724);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.020-MS.020.100-852IT3A001', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where path_codice = 'MS.020->MS.020.100->852IT3A001') where id_linea_produttiva_old in(706);

 --2 giro
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MR-DR-M28', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita = 'MR-DR-M28') where id_linea_produttiva_old in (7402);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MG-DG-M12', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita = 'MG-DG-M12') where id_linea_produttiva_old in (578);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml = 'MG-OPRZ-M02', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice = 'MG-OPRZ-M02') where id_linea_produttiva_old in (580,8085);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MG-OSMM-M29', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita = 'MG-OSMM-M29')  where id_linea_produttiva_old in(609,8073,8078);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MG-DG-M12', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita = 'MG-DG-M12')  where id_linea_produttiva_old in(8084,8089);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MG-DG-M19', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita = 'MG-DG-M19')  where id_linea_produttiva_old in(8094,8088);
update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.090-TCT-T', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice = 'MS.090-TCT-T') where id_linea_produttiva_old in(8086,8083);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MG-OPRA-M01', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita = 'MG-OPRA-M01') where id_linea_produttiva_old in(8087);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MG-DG-M10', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita = 'MG-DG-M10') where id_linea_produttiva_old = 8095;
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MG-DG-M39', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita = 'MG-DG-M39') where id_linea_produttiva_old = 8097;
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.000-MS.000.600-018', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita = 'MS.000-MS.000.600-018') where id_linea_produttiva_old =4907;
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.A-MS.A30.300-011', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita = 'MS.A-MS.A30.300-011') where id_linea_produttiva_old in(6530);
 update opu_relazione_stabilimento_linee_produttive set codice_univoco_ml  = 'MS.020-MS.020.100-852IT3A001', id_linea_produttiva=(select id_nuova_linea_attivita from ml8_linee_attivita_nuove_materializzata where codice_attivita = 'MS.020-MS.020.100-852IT3A001') where id_linea_produttiva_old in(729);
update opu_relazione_stabilimento_linee_produttive set id_linea_produttiva= (select id from master_list_linea_attivita  where codice_univoco  = 'MS.060-MS.060.200-852IT7A105'), codice_univoco_ml = 'MS.060-MS.060.200-852IT7A105' where id_linea_produttiva_old  = 789; --con vendita di funghi
