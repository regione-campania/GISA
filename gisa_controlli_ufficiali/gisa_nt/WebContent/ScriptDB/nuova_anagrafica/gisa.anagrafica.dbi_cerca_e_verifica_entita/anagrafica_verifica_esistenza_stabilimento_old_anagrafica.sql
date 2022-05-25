--chi: Antonio Riviezzo
--cosa: dbi per la verifica dell'esistenza di uno stabilimento
--quando: 30/03/2018

-- Function: anagrafica.anagrafica_verifica_esistenza_stabilimento_old_anagrafica(text, text, integer, text, text, integer)

-- DROP FUNCTION anagrafica.anagrafica_verifica_esistenza_stabilimento_old_anagrafica(text, text, integer, text, text, integer);

CREATE OR REPLACE FUNCTION anagrafica.anagrafica_verifica_esistenza_stabilimento_old_anagrafica(
    _piva text DEFAULT NULL::text,
    _codfisc text DEFAULT NULL::text,
    _id_comune integer DEFAULT NULL::integer,
    _via text DEFAULT NULL::text,
    _civico text DEFAULT NULL::text,
    _toponimo integer DEFAULT NULL::integer)
  RETURNS integer AS
$BODY$
DECLARE
	idStabilimento integer;
	 	
BEGIN
	
	select 
		CASE
		  WHEN o.org_id is not null THEN o.org_id
		  WHEN os.id  is not null THEN os.id  
		ELSE null
           END

	into  idStabilimento
	      
	from ricerche_anagrafiche_old_materializzata ram
		--join per organization
	      left join organization o on o.org_id = ram.riferimento_id and trim(ram.riferimento_id_nome_tab) = 'organization'
	      left join organization_address address_impresa ON address_impresa.address_id = ram.id_indirizzo_impresa and trim(ram.riferimento_id_nome_tab) = 'organization'
	      left join comuni1 comuneOrganization on trim(comuneOrganization.nome) ilike address_impresa.city

	      --join per opu
	      left join opu_operatore oo on ram.id_impresa = oo.id and trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento'	
	      left join opu_stabilimento os on oo.id = os.id_operatore
	      left join opu_indirizzo oi on os.id_indirizzo = oi.id
	      
	where 
		case when (_piva is not null and _piva<>'') then 
			lower(trim(ram.partita_iva))=lower(trim(_piva)) 
		else
			case when (_codfisc is not null and _codfisc<>'') then 
				lower(trim(ram.codice_fiscale))=lower(trim(_codfisc)) 
			end
		end 

		and (( _id_comune is null or oi.comune = _id_comune) or comuneOrganization.id = _id_comune )
		and (( _via is null or public.levenshtein(lower(coalesce(trim(oi.via),'')),lower(trim(_via)))<=2)
		      or public.levenshtein(lower(coalesce(trim(address_impresa.addrline1),'')),lower(trim(_via)))<=2 )
		and (( _civico is null or lower(trim(oi.civico)) = lower(trim(_civico))) or address_impresa.civico = _civico )
		and ((_toponimo is null or oi.toponimo = _toponimo) or address_impresa.toponimo = _toponimo)
		
		and (os.trashed_date is null or trim(ram.riferimento_id_nome_tab) = 'organization')
		and (oo.trashed_date is null or trim(ram.riferimento_id_nome_tab) = 'organization')
		and (o.enabled or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
		and (o.trashed_date is null or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
		and (address_impresa.trasheddate is null or trim(ram.riferimento_id_nome_tab) = 'opu_stabilimento')
			limit 1;
			
		return idStabilimento ;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION anagrafica.anagrafica_verifica_esistenza_stabilimento_old_anagrafica(text, text, integer, text, text, integer)
  OWNER TO postgres;
