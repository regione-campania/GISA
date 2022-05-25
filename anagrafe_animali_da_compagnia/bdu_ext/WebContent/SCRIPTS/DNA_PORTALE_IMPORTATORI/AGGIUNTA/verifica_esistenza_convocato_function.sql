alter table convocazioni 
ADD id_comune integer;

-- Function: verifica_esistenza_convocato(integer)

-- DROP FUNCTION verifica_esistenza_convocato(text, text, int);

CREATE OR REPLACE FUNCTION verifica_esistenza_convocato(IN cod_fiscale text, IN mc text, IN id_com integer)
  RETURNS TABLE(id integer) AS
$BODY$

 BEGIN
    
   RETURN QUERY     
 Select c.id from convocazioni c 
 where c.microchip = mc and codice_fiscale = cod_fiscale and id_comune = id_com ;
 
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION verifica_esistenza_convocato(text, text, int)
  OWNER TO postgres;

select * from verifica_esistenza_convocato('RDNMRT73D55F839H', '380260080021245', 5279  )
update convocazioni set id_comune = 5279 where id = 122536
select * from convocazioni limit 10


Select * from verifica_esistenza_convocato('BCCGPP75D09F839Z', '380260040259577', 5279)


select * from convocazioni where microchip = '981100000219429'

delete from convocazioni 