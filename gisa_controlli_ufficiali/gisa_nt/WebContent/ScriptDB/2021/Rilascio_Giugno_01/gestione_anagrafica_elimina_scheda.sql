-- Function: public.gestione_anagrafica_elimina_scheda(integer, integer)

-- DROP FUNCTION public.gestione_anagrafica_elimina_scheda(integer, integer);

CREATE OR REPLACE FUNCTION public.gestione_anagrafica_elimina_scheda(
    _userid integer,
    _id_stab integer)
  RETURNS boolean AS
$BODY$
DECLARE

BEGIN

update opu_stabilimento set trashed_date = current_date , trashed_by= _userid where id = _id_stab;

--se lo stabilimento ha una o piu pratiche con qualsiasi causale viene messa a trashed la relazione tra la pratica e l evento (escluse le pratiche apicoltura)
update rel_eventi_pratiche set trashed_date = current_date 
	where id = (select rep.id from pratiche_gins pg 
					join rel_eventi_pratiche rep on pg.id = rep.id_pratica
					join eventi_su_osa eso on rep.id_evento = eso.id
						where eso.id_stabilimento = _id_stab and pg.apicoltura is not true);

--se lo stabilimento ha una o piu pratiche con qualsiasi causale viene messo a trashed ogni evento ad esso collegato (escluse le pratiche apicoltura)
update eventi_su_osa set trashed_date = current_date
	where id = (select eso.id from pratiche_gins pg 
					join rel_eventi_pratiche rep on pg.id = rep.id_pratica
					join eventi_su_osa eso on rep.id_evento = eso.id
						where eso.id_stabilimento = _id_stab and pg.apicoltura is not true);


return true;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100;
ALTER FUNCTION public.gestione_anagrafica_elimina_scheda(integer, integer)
  OWNER TO postgres;
