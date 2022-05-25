
alter table suap_master_list_6_linea_attivita add column note_hd text;
alter table suap_master_list_6_linea_attivita add column enabled boolean default true;
update suap_master_list_6_linea_attivita set enabled = true where enabled is null

update suap_master_list_6_linea_attivita set linea_attivita  ='PP STABILIMENTO DI PRODUZIONE', note_hd= concat_ws('***',note_hd,'Aggiornamento del codice univoco (OLD MS.A-MS.A30.500-852ITBA006) e della descrizione (OLD: PP STABILIMENTO DI PRODUZIONE - STABILIMENTO DI PRODUZIONE  - BIBITE ANALCOLICHE, E ALTRE BEVANDE ADDIZIONATE DI VITAMINE E MINERALI)
 a seguito dell''aggiornamento lda fornito da Diletta a mezzo mail del 31/07/17'),codice_univoco = 'MS.A-MS.A30.500-852ITBA' where codice_univoco = 'MS.A-MS.A30.500-852ITBA006';
update suap_master_list_6_linea_attivita set note_hd= concat_ws('***',note_hd,'Disabilita linea a seguito dell''aggiornamento lda fornito da Diletta a mezzo mail del 31/07/17'), enabled=false where codice_univoco = 'MS.A-MS.A30.500-852ITBA005';
update suap_master_list_6_linea_attivita set note_hd= concat_ws('***',note_hd,'Disabilita linea a seguito dell''aggiornamento lda fornito da Diletta a mezzo mail del 31/07/17'),enabled=false where codice_univoco = 'MS.A-MS.A30.500-852ITBA004';
update suap_master_list_6_linea_attivita set note_hd= concat_ws('***',note_hd,'Disabilita linea a seguito dell''aggiornamento lda fornito da Diletta a mezzo mail del 31/07/17'),enabled=false where codice_univoco = 'MS.A-MS.A30.500-852ITBA003'; 
