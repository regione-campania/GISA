update evento_html_fields set tipo_controlli_date = concat(tipo_controlli_date, ',T21') where  id_tipologia_evento not in (2,21,22) and nome_campo ilike '%data%';
update evento_html_fields set tipo_controlli_date = concat(tipo_controlli_date, ',T22') where  id_tipologia_evento in (2,21,22) and nome_campo ilike '%data%';
update evento_html_fields set tipo_controlli_date = 'T21,T2,T20' where id = 328 ;
update evento_html_fields set tipo_controlli_date = 'T21,T2' where id = 168;