update lookup_tipologia_stato  set title=regexp_replace(title, '\371', 'u''', 'g'); --where regexp_replace(nome, '\260', '^', 'g') != nome update analiti  set nome=regexp_replace(nome, '\260', 'e', 'g') where regexp_replace(nome, '\260', '^', 'g') != nome 
update lookup_tipologia_registrazione  set title=regexp_replace(title, '\340', 'a''', 'g');
update municipalita  set nome_municipalita=regexp_replace(nome_municipalita, '\340', 'a''', 'g');
