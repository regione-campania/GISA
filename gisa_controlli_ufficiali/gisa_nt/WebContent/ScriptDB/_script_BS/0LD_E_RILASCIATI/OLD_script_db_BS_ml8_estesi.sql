update master_list_linea_attivita set scheda_supplementare = '7171' where codice_univoco = 'MS.060-MS.060.200-852IT7A101';


insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','prodotti surgelati',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','frutta,verdura e lugumi freschi e essiccati',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','carne e prodotti a base di carne',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','prodotti della pesca,molluschi bivalvi e crostacei',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','prodotti di panetteria',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','prodotti di pasticceria',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','bevande alcoliche ed analcoliche',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO',' latte e prodotti lattiero caseari',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','prodotti di torrefazione e altro',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','prodotti macrobiotici e dietetici',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','prodotti vegetali trasformati,essiccati e raffinati',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','prodotti surgelati',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','frutta,verdura e lugumi freschi e essiccati',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','carne e prodotti a base di carne',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','prodotti della pesca,molluschi bivalvi e crostacei',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','prodotti di panetteria',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','prodotti di pasticceria',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','bevande alcoliche ed analcoliche',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO',' latte e prodotti lattiero caseari',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','prodotti di torrefazione e altro',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','prodotti macrobiotici e dietetici',FALSE); 
insert into master_list_sk_elenco(id_scheda, tit_scheda, nome, testo_aggiuntivo) values (7171,'SCHEDA PER COMMERCIO','prodotti vegetali trasformati,essiccati e raffinati',FALSE); 




  alter table sintesis_prodotti add column origine text;
  update sintesis_prodotti set origine = 'sintesis';




----
select linea.id, linea.codice_univoco, linea.linea_attivita, liv1.id, liv1.id_padre, liv1.id_linea_attivita, liv1.nome, val1.valore,  liv2.id, liv2.id_padre, liv2.id_linea_attivita,  liv2.nome, val2.valore ,  liv3.id, liv3.id_padre,  liv3.id_linea_attivita, liv3.nome, val3.valore  from master_list_linea_attivita linea
join master_list_livelli_aggiuntivi liv1 on liv1.id_linea_attivita = linea.id
join master_list_livelli_aggiuntivi_values val1 on val1.id_livello_aggiuntivo = liv1.id
left join master_list_livelli_aggiuntivi liv2 on liv2.id_padre = liv1.id
left join master_list_livelli_aggiuntivi_values val2 on val2.id_livello_aggiuntivo = liv2.id
left join master_list_livelli_aggiuntivi liv3 on liv3.id_padre = liv2.id
left join master_list_livelli_aggiuntivi_values val3 on val3.id_livello_aggiuntivo = liv3.id





