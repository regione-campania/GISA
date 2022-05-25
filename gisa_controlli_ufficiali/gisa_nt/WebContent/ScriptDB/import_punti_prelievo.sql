INSERT INTO public.alt_partizioni(code, description, nome_campo, nome_tabella, valore_start, valore_end)
                          VALUES (9, 'id di gestori_acque', 'id', 'gestori_acque_gestori', '160000000', '179999999');

alter table gestori_acque_gestori add column alt_id integer;

CREATE OR REPLACE FUNCTION public.inserisci_id_alternativo_gestori_acque()
  RETURNS trigger AS
$BODY$
   DECLARE
   alternativeId integer;
   
   BEGIN

   alternativeId = (select return_alt_id from  gestione_id_alternativo(NEW.id, 9));
   update gestori_acque_gestori set alt_id = alternativeId where id = NEW.id;
     
    RETURN NEW;
   END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.inserisci_id_alternativo_gestori_acque()
  OWNER TO postgres;

-- Trigger: opu_id_alternativo on public.opu_stabilimento

-- DROP TRIGGER opu_id_alternativo ON public.opu_stabilimento;

CREATE TRIGGER gestori_acque_id_alternativo
  AFTER INSERT
  ON public.gestori_acque_gestori
  FOR EACH ROW
  EXECUTE PROCEDURE public.inserisci_id_alternativo_gestori_acque();


  update gestori_acque_gestori set alt_id = id + 160000000;

  alter table gestori_acque_log_import add column cod_documento text;

