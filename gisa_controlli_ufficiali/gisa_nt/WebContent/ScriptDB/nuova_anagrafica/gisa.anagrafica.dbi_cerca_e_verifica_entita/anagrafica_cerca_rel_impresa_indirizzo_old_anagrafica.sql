--chi: Antonio Riviezzo
--cosa: dbi per la ricerca di una relazione impresa - indirizzo nella vecchia anagrafica
--quando: 30/03/2018

-- Function: anagrafica.anagrafica_cerca_rel_impresa_indirizzo_old_anagrafica(integer, integer, date, date)

-- DROP FUNCTION anagrafica.anagrafica_cerca_rel_impresa_indirizzo_old_anagrafica(integer, integer, date, date);

CREATE OR REPLACE FUNCTION anagrafica.anagrafica_cerca_rel_impresa_indirizzo_old_anagrafica(
    IN _id_impresa integer DEFAULT NULL::integer,
    IN _id_indirizzo integer DEFAULT NULL::integer,
    IN _data_inserimento_1 date DEFAULT NULL::date,
    IN _data_inserimento_2 date DEFAULT NULL::date)
  RETURNS TABLE(id_impresa integer, id_indirizzo integer, data_inserimento timestamp without time zone) AS
$BODY$
DECLARE


BEGIN

FOR id_impresa, id_indirizzo, data_inserimento
in 

    select 
	   distinct(CASE
	    WHEN orgaddr.org_id is not null THEN orgaddr.org_id
	    WHEN oo.id is not null THEN oo.id
		  ELSE null
           END),

           CASE
	    WHEN orgaddr.address_id is not null THEN orgaddr.address_id
	    WHEN oi.id is not null THEN oi.id
		  ELSE null
           END,
           null
    from ricerche_anagrafiche_old_materializzata ram
	--caso organization 
	left join organization_address orgaddr on orgaddr.address_id = ram.id_indirizzo_impresa and trim(ram.riferimento_nome_tab_indirizzi) = 'organization_address'

	 --caso opu
	left join opu_operatore oo on oo.id_indirizzo = ram.id_indirizzo_impresa and trim(ram.riferimento_nome_tab_indirizzi) = 'opu_indirizzo'
	left join opu_indirizzo oi on oo.id_indirizzo = oi.id

	where ((_id_impresa is null or oo.id = _id_impresa) or orgaddr.org_id = _id_impresa)
		and ((_id_indirizzo is null or oi.id = _id_indirizzo) or orgaddr.address_id = _id_indirizzo)
		and (oo.trashed_date is null or trim(ram.riferimento_nome_tab_indirizzi) = 'organization_address')
		and (orgaddr.trasheddate is null or trim(ram.riferimento_nome_tab_indirizzi) = 'opu_indirizzo')
	
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;

     
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION anagrafica.anagrafica_cerca_rel_impresa_indirizzo_old_anagrafica(integer, integer, date, date)
  OWNER TO postgres;
