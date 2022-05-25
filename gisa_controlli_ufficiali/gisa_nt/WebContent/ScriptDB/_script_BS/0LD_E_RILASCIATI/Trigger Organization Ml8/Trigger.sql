-- CHI: Bartolo Sansone
--  COSA: Trigger organization
--  QUANDO: 31/10/2018

CREATE OR REPLACE FUNCTION public.insert_linee_attivita_ml8_temp_organization()
  RETURNS trigger AS
$BODY$
   DECLARE
   codiceUnivoco text;
   idNorma integer;
   mapCompleto boolean;
   BEGIN

idNorma := -1;
codiceUnivoco = '';
mapCompleto = false;

   if NEW.tipologia in(4,5,12,14,15,17,22,255) 
   then
   select id_norma, codice_univoco_ml, map_completo into idNorma, codiceUnivoco, mapCompleto from linee_attivita_ml8_temp where id_tipologia_operatore = NEW.tipologia limit 1;

   if idNorma > 0 and codiceUnivoco <> '' then
   insert into linee_attivita_ml8_temp (org_id, id_norma, id_tipologia_operatore, codice_univoco_ml, map_completo) values (NEW.org_id, idNorma, NEW.tipologia, codiceUnivoco, mapCompleto);
   end if;
   
   end if;
       
    RETURN NEW;
   END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.insert_linee_attivita_ml8_temp_organization()
  OWNER TO postgres;



CREATE TRIGGER organization_insert_linee_attivita_ml8_temp
  AFTER INSERT
  ON public.organization
  FOR EACH ROW
  EXECUTE PROCEDURE public.insert_linee_attivita_ml8_temp_organization();

