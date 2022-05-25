-- Function: public_functions.getmicrochipsbycf(text, integer)

-- DROP FUNCTION public_functions.getmicrochipsbycf(text, integer);

CREATE OR REPLACE FUNCTION public_functions.getmicrochipsbycf(IN codice_fiscale_to_search text, IN id_comune integer)
  RETURNS TABLE(microchip_to_return text, flag_prelievo_dna_b boolean, esito integer) AS
$BODY$
DECLARE
        r RECORD;
        
        
BEGIN
        
        
                FOR microchip_to_return,  flag_prelievo_dna_b, esito
                in
        
select  coalesce(microchip, tatuaggio), cane.flag_prelievo_dna, 
CASE WHEN a.flag_decesso THEN 1  
when a.stato in
(select id_prossimo_stato from registrazioni_wk where id_registrazione in (4, 11)) then 2 --4: evento furto, 11: evento smarrimeento
WHEN p.comune <> id_comune then 4
else 3 END AS esito -- 1: animale deceduto 2: animale in furto o smarrimento 3: convocabile
from animale a
left join cane on (a.id = cane.id_animale)
left join opu_operatori_denormalizzati p on (a.id_proprietario = p.id_rel_stab_lp)
where p.codice_fiscale = codice_fiscale_to_search
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
 $BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.getmicrochipsbycf(text, integer)
  OWNER TO postgres;
