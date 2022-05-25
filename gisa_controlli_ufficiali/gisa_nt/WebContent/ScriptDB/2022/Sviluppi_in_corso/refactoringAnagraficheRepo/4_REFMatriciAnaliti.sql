DROP FUNCTION digemon.dbi_get_analiti();
CREATE OR REPLACE FUNCTION digemon.dbi_get_analiti()
  RETURNS TABLE(progressivo bigint, descrizione_analita_livello_uno character varying, descrizione_analita_livello_due character varying, descrizione_analita_livello_tre character varying,
  codice_analita_livello_uno text, codice_analita_livello_due text, codice_analita_livello_tre text, id_analita_livello_uno integer, 
  id_analita_livello_due integer, id_analita_livello_tre integer)AS
$BODY$
DECLARE
BEGIN
RETURN QUERY
SELECT ROW_NUMBER() OVER(ORDER BY m.nome asc, m2.nome asc, m3.nome asc) as progressivo, m.nome as descrizione_analita_livello_uno, m2.nome descrizione_analita_livello_due, 
m3.nome as descrizione_analita_livello_tre,
m.codice_esame as codice_analita_livello_uno, m2.codice_esame as codice_analita_livello_due, 
m3.codice_esame as codice_analita_livello_tre,
m.analiti_id as id_analita_livello_1, m2.analiti_id as id_analita_livello_2,
m3.analiti_id as id_analita_livello_3
from analiti m
join analiti m2 on m2.id_padre= m.analiti_id and m.id_padre=-1
left join analiti m3 on m3.id_padre = m2.analiti_id and m3.enabled and m3.nuova_gestione
where 
 --m.livello=0 --and m2.livello=1 
 m.enabled and m2.enabled and 
m.nuova_gestione and m2.nuova_gestione 
order by m.nome asc;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_analiti()
  OWNER TO postgres;
  
DROP FUNCTION digemon.dbi_get_matrici();
CREATE OR REPLACE FUNCTION digemon.dbi_get_matrici()
  RETURNS TABLE(progressivo bigint, descrizione_matrice_livello_uno character varying, descrizione_matrice_livello_due character varying, descrizione_matrice_livello_tre character varying,
  codice_matrice_livello_uno text, codice_matrice_livello_due text, codice_matrice_livello_tre text, id_matrice_livello_uno integer, 
  id_matrice_livello_due integer, id_matrice_livello_tre integer)AS
$BODY$
DECLARE
BEGIN
RETURN QUERY
SELECT ROW_NUMBER() OVER(ORDER BY m.nome asc, m2.nome asc, m3.nome asc) as progressivo, m.nome as descrizione_matrice_livello_uno, m2.nome descrizione_matrice_livello_due, 
m3.nome as descrizione_matrice_livello_tre,
m.codice_esame as codice_matrice_livello_uno, m2.codice_esame as codice_matrice_livello_due, 
m3.codice_esame as codice_matrice_livello_tre,
m.matrice_id as id_matrice_livello_1, m2.matrice_id as id_matrice_livello_2, 
m3.matrice_id as id_matrice_livello_3
--, m3.nome, m3.codice_esame 
from matrici m
join matrici m2 on m2.id_padre= m.matrice_id 
left join matrici m3 on m3.id_padre = m2.matrice_id and m3.enabled and m3.nuova_gestione
where 
 m.livello=0 --and m2.livello=1 
and m.enabled and m2.enabled and 
m.nuova_gestione and m2.nuova_gestione 
order by m.nome asc;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_matrici()
  OWNER TO postgres;
  
select * from digemon.dbi_get_matrici()