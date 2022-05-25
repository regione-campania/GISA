
-- verifica 
SELECT analiti_id,nome, nome::bytea ,regexp_replace(nome, '\260', '^', 'g') FROM analiti  where regexp_replace(nome, '\260', '^', 'g') != nome
 
--correzione
update analiti  set nome=regexp_replace(nome, '\260', 'e', 'g') where regexp_replace(nome, '\260', '^', 'g') != nome 