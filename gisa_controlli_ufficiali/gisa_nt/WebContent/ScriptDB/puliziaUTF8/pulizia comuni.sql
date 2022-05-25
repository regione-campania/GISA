--------------------- pulizia comuni da lettere accentate ------------------------------

--SELECT id,nome FROM comuni1  where notused is null and  cod_regione != '15' order by nome

-- SELECT id,nome, nome::bytea ,regexp_replace(nome, '\303\271\250', 'u''', 'g') FROM comuni1  where regexp_replace(nome, '\303\271\250', 'u''', 'g') != nome


-- SELECT id, nome, nome::bytea FROM comuni1  where nome ilike '%Ã%' order by 1


 --update comuni1 set nome = regexp_replace(nome, 'Ã©', 'e''', 'g')  where nome ilike '%Ã©%'

-- è
update comuni1 set nome = regexp_replace(nome, 'Ã©', 'e''', 'g')  where nome ilike '%Ã©%'

-- ù
update comuni1  set nome=regexp_replace(nome, '\303\271\250', 'u''', 'g') where regexp_replace(nome, '\303\271\250', 'u''', 'g') != nome

-- ì
update comuni1  set nome=regexp_replace(nome, '\303\254', 'u''', 'g') where regexp_replace(nome, '\303\254', 'i''', 'g') != nome

-- à
update comuni1  set nome=regexp_replace(nome, '\303\240', 'a''', 'g') where regexp_replace(nome, '\303\240', 'a''', 'g') != nome

--ò
update comuni1  set nome=regexp_replace(nome, '\303\262', 'o''', 'g') where regexp_replace(nome, '\303\262', 'o''', 'g') != nome

--e
update comuni1  set nome=regexp_replace(nome, '\303\250', 'e', 'g') where regexp_replace(nome, '\303\250', 'e', 'g') != nome