
  
  
  
  
  

 ---IGNORARE DA QUESTO PUNTO IN POI
 
  
  
  -- Trigger: inserisci_id_alternativo on organization
-- DROP TRIGGER inserisci_id_alternativo ON organization;

CREATE TRIGGER org_id_alternativo
  AFTER INSERT
  ON organization
  FOR EACH ROW
  EXECUTE PROCEDURE inserisci_id_alternativo_org();



-- Function: inserisci_id_alternativo_org()
-- DROP FUNCTION inserisci_id_alternativo_org();
CREATE OR REPLACE FUNCTION inserisci_id_alternativo_org()
  RETURNS trigger AS
$BODY$
   DECLARE
   alternativeId integer;
   
   BEGIN

   alternativeId = (select return_alt_id from  gestione_id_alternativo(NEW.org_id, 1));
   update organization set alt_id = alternativeId where org_id = NEW.org_id;
     
    RETURN NEW;
   END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION inserisci_id_alternativo_org()
  OWNER TO postgres;

