CREATE OR REPLACE FUNCTION public.is_farmacia(id_ integer)
  RETURNS boolean AS
$BODY$
   DECLARE
to_ret boolean;   
BEGIN
	to_ret := (select '47.73.10' in (select aggregazione from ricerche_anagrafiche_old_materializzata where riferimento_org_id = id_ ));
	if(to_ret is null) then to_ret :=false;
	end if;
	RETURN to_ret;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.is_farmacia(integer)
  OWNER TO postgres;



  
