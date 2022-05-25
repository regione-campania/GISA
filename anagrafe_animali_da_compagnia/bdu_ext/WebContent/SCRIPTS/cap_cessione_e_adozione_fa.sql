insert into evento_html_fields (id_tipologia_evento,nome_campo,tipo_campo,label_campo,   tabella_lookup, ordine_campo,select_size,select_multiple,valore_campo) values 
                               (7,                       'cap','text',          'Cap',             null,           16,       null,false,'');

insert into evento_html_fields (id_tipologia_evento,nome_campo,tipo_campo,label_campo,   tabella_lookup, ordine_campo,select_size,select_multiple,valore_campo) values 
                               (46,                      'cap','text',          'Cap',             null,           16,       null,false,'');

                               
alter table evento_cessione add column cap_proprietario_a_cessione text;
alter table evento_adozione_fuori_asl add column cap_proprietario_a_adozione_fa text;
