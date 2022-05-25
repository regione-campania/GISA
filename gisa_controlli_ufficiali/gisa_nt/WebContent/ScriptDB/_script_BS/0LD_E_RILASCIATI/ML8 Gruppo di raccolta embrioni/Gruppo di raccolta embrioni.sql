select * from master_list_linea_attivita where codice_univoco ='CG-NAZ-E-004'
--update master_list_linea_attivita set tipo= 'Con Sede Fissa', id_lookup_tipo_attivita = 1 where codice_univoco ='CG-NAZ-E-004';

select * from ml8_master_list where codice ='CG-NAZ-E-004'
--update ml8_master_list set id_lookup_tipo_attivita = 1 where codice ='CG-NAZ-E-004';

select * from ml8_linee_attivita_nuove_materializzata  where codice_attivita ='CG-NAZ-E-004'
--update ml8_linee_attivita_nuove_materializzata set id_lookup_tipo_attivita = 1 where codice_attivita ='CG-NAZ-E-004';