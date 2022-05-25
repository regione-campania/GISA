CREATE OR REPLACE  FUNCTION public.get_informazioni_capo(IN _matricola text)
  RETURNS TABLE(esito text) AS
$BODY$
DECLARE
r RECORD;
BEGIN
FOR 
esito
in

select UPPER(CONCAT('NOME MACELLO VECCHIA ANAGRAFICA: ', nome_macello_vecchia_anagrafica, '; NOME MACELLO NUOVA ANAGRAFICA: ', nome_macello_nuova_anagrafica, '; APPROVAL NUMBER NUOVA ANAGRAFICA: ',  approval_number_nuova_anagrafica, '; DATA INSERIMENTO CAPO: ', data_inserimento_capo, '; DATA MACELLAZIONE CAPO: ', data_macellazione_capo, '; MATRICOLA CAPO: ', matricola_capo)) FROM (
select 
distinct
o.name as nome_macello_vecchia_anagrafica,
COALESCE(s.denominazione, s2.denominazione) as nome_macello_nuova_anagrafica,
COALESCE(s.approval_number, s2.approval_number) as approval_number_nuova_anagrafica,
to_char(c.entered, 'dd/MM/yyyy') as data_inserimento_capo, 
to_char(c.vpm_data, 'dd/MM/yyyy') as data_macellazione_capo, 
c.cd_matricola as matricola_capo
from m_capi c
left join organization o on c.id_macello = o.org_id
left join sintesis_stabilimenti_import si on si.riferimento_org_id = o.org_id
left join sintesis_stabilimento s on s.approval_number ilike si.approval_number
left join sintesis_stabilimento s2 on s2.alt_id = c.id_macello
where cd_matricola ilike _matricola
) aa

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_informazioni_capo(text)
  OWNER TO postgres;
