SELECT nome, regexp_replace(nome, 'Ã©', 'e''', 'g') FROM comuni1  where nome ilike '%Ã©%'
 

 Update comuni1 set nome = regexp_replace(nome, 'Ã©', 'e''', 'g')  where nome ilike '%Ã©%'
