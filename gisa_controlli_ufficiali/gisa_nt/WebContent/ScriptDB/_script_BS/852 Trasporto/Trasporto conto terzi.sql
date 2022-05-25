-- CREO TABELLA CONTENENTE TUTTI GLI 852 MOBILI, CON LINEA ESCLUSIVAMENTE TRASPORTO CONTO TERZI, RAGGRUPPATI PER PARTITA IVA, NOME E INDIRIZZO SEDE LEGALE

create table organization_852_trasporto_conto_terzi as
select
count(concat(trim(o.partita_iva), ',', UPPER(o.name), ',', UPPER(regexp_replace(oa.addrline1, '\W+', '', 'g')) , ',',UPPER(regexp_replace(oa.city, '\W+', '', 'g')))) as tot,
trim(o.partita_iva) as partita_iva,
UPPER(o.name) as ragione_sociale,
UPPER(regexp_replace(oa.addrline1, '\W+', '', 'g')) as indirizzo_sede_legale,
UPPER(regexp_replace(oa.city, '\W+', '', 'g')) as comune_sede_legale,  
string_agg(o.org_id::text, ', ') as lista_org_id,
string_agg(o.nome_correntista::text, ',') as lista_targhe
from organization o
left join organization_address oa on oa.org_id = o.org_id and oa.address_type = 1
where o.org_id in (select org_id from organization_852_mobili where ateco = '49.41.00')
and o.trashed_date is null and (length(trim(partita_iva))=11)
and o.org_id not in (select org_id from organization_852_mobili where ateco <> '49.41.00')
group by trim(o.partita_iva), UPPER(o.name), UPPER(regexp_replace(oa.addrline1, '\W+', '', 'g')), UPPER(regexp_replace(oa.city, '\W+', '', 'g'))
order by count(concat(trim(o.partita_iva), ',', UPPER(o.name), ',',  UPPER(regexp_replace(oa.addrline1, '\W+', '', 'g')) , ',',UPPER(regexp_replace(oa.city, '\W+', '', 'g')))) desc;

-- ESTRAGGO LE ORGANIZATION DA IMPORTARE IN OPU (>2 TARGHE)
select * from organization_852_trasporto_conto_terzi where tot > 2;

-- ESTRAGGO LE ORGANIZATION DA CONVERTIRE IN TRASPORTO CONTO PROPRIO(<=2 TARGHE)
select * from organization_852_trasporto_conto_terzi where tot <= 2;

CREATE OR REPLACE FUNCTION public.get_852_tipo_trasporto(_orgid integer)
  RETURNS integer AS
$BODY$
   DECLARE
totLineeTrasporto integer;   
esitoTrasporto integer;
BEGIN
	totLineeTrasporto := -1;
	esitoTrasporto := -1;

	select tot into totLineeTrasporto 
	from organization_852_trasporto_conto_terzi 	
	where (lista_org_id ilike _orgid::text or lista_org_id ilike _orgid::text||',%' or lista_org_id ilike '%, '||_orgid::text||', %' or lista_org_id ilike '%, '||_orgid::text); 	
	
	IF totLineeTrasporto is null THEN
		esitoTrasporto := -1;
	ELSIF totLineeTrasporto>2 THEN
		esitoTrasporto := 3;
	ELSIF totLineeTrasporto<=2 THEN	
		esitoTrasporto := 1;
	
	END IF;
	
	RETURN esitoTrasporto;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_852_tipo_trasporto(integer)
  OWNER TO postgres;
