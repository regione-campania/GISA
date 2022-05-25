
CREATE OR REPLACE FUNCTION public.is_administrator(
    input_username text,
    input_password text)
  RETURNS boolean AS
$BODY$
   DECLARE
output boolean;
role_id integer;
BEGIN

output := false;

select r.id into role_id from utenti u
join permessi_ruoli r on u.ruolo = r.nome
where u.username = input_username and u.password = md5(input_password) and u.enabled;

IF role_id = 1 OR role_id = 2 THEN
output := true;
END IF;

return output;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

