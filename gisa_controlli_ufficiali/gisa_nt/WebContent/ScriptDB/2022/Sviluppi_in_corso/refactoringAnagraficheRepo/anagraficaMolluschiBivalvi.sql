

CREATE OR REPLACE FUNCTION digemon.dbi_get_anagrafica_molluschi_bivalvi(
    IN _data_1 text default null::text,
    IN _data_2 text default null::text,
    IN _limit_record text DEFAULT 'true'::text)
  RETURNS TABLE(progressivo text, asl text, localita text, "taglia non commerciale" text, "stato classificazione" text, "numero decreto" text, "data classificazione" text, "data fine classificazione" text, "data sospensione" text, 
  "numero decreto sospensione revoca" text, "data provvedimento sospensione revoca" text, "zona di produzione" text, "cun" text, "classificazione" text, "specie molluschi" text, 
  "comune" text, "coordinate poligono" text, "coordinate punto di prelievo" text, "provvedimento restrittivo" text, "concessione numero" text, "concessione data" text, 
  "concessione data scadenza" text, "concessione denominazione" text, "concessione legale rappresentante" text, 
  "concessione stato" text, macroarea text, aggregazione text, attivita text, "codice macroarea" text, "codice aggregazione" text, "codice attivita" text, "data inserimento" text, 
  "riferimento_id" text, "riferimento_nome_tab" text, "id_linea" text, "id_norma" text ) AS
$BODY$
DECLARE
BEGIN
	IF ($1 IS NULL AND $2 IS NULL AND $3 IS NULL) THEN
		RETURN QUERY -- restituisce solo il record di intestazione
		select -- nome|tipo 
			'progressivo|text|dig' 
			, 'asl|text|dig' 
			, 'localita|text|dig' 
			, 'taglia_non_commerciale|text|dig' 
			, 'stato_classificazione|text|dig' 
			, 'numero_decreto|text|dig' 
			, 'data_classificazione|dateISO8601|dig' 
			, 'data_fine_classificazione|dateISO8601|dig' 
			, 'data_sospensione|dateISO8601|dig' 
			, 'numero_decreto_sospensione_revoca|text|dig'  
			, 'data_provvedimento_sospensione_revoca|dateISO8601|dig'  
			, 'zona_di_produzione|text|dig' 
			, 'cun|text|dig' 
			, 'classificazione|text|dig'  
			, 'specie_molluschi|text|dig'  
			, 'comune|text|dig'  
			, 'coord_poligono|text|dig' 
			, 'coord_punto_di_prelievo|text|dig' 
			, 'provvedimento_restrittivo |text|dig'  
			, 'concessione_num|text|dig' 
			, 'concessione_data|dateISO8601|dig' 
			, 'concessione_data_scadenza|dateISO8601|dig'  
			, 'concessione_denominazione|text|dig'  
			, 'concessione_legale_rappresentante|text|dig' 
			, 'concessione_stato|text|dig'  
			, 'macroarea|text|dig' 
			, 'aggregazione|text|dig'  
			, 'attivita|text|dig' 
			, 'codice_macroarea|text|dig|ra' 
			, 'codice_aggregazione|text|dig|ra' 
			, 'codice_attivita|text|dig|ra' 
			, 'data_inserimento|dateISO8601|dig'  
			, 'riferimento_id|text|ra'
			, 'riferimento_nome_tab|text|ra'
			, 'id_linea|text|ra'
			, 'id_norma|text|ra'; 
	ELSE 
		RETURN QUERY    
		with cte as (
			SELECT 
				row_number() OVER (ORDER BY concat(r.asl, r.comune, r.data_inserimento,current_timestamp) desc) AS n
				, r.asl
				, e.localita 
				, e.taglia_non_commerciale 
				, e.stato_classificazione 
				, e.numero_decreto 
				, e.data_classificazione 
				, e.data_fine_classificazione 
				, e.data_sospensione 
				, e.numero_decreto_sospensione_revoca  
				, e.data_provvedimento_sospensione_revoca  
				, e.zona_di_produzione 
				, e.cun 
				, e.classificazione  
				, e.specie_molluschi  
				, e.comune  
				, e.coord_poligono 
				, e.coord_punto_di_prelievo 
				, e.provvedimento_restrittivo   
				, e.concessione_num 
				, e.concessione_data 
				, e.concessione_data_scadenza  
				, e.concessione_denominazione  
				, e.concessione_legale_rappresentante 
				, e.concessione_stato  
				, l.macroarea
				, l.aggregazione
				, l.attivita
				, l.codice_macroarea
				, l.codice_aggregazione
				, l.codice_attivita
				 , r.data_inserimento
				, r.riferimento_id
				, r.riferimento_id_nome_tab
				, l.id_linea
				, l.id_norma
				
			FROM 
				digemon.dbi_get_all_stabilimenti(_data_1::date, _data_2::date) r
				join digemon.dbi_get_all_linee(_data_1::date, _data_2::date) l on r.riferimento_id = l.riferimento_id and r.riferimento_id_nome_tab = 'organization'
				left join digemon.dbi_get_campi_estesi_molluschi_bivalvi() e on r.riferimento_id = e.riferimento_id and r.riferimento_id_nome_tab = 'organization'
				 
			WHERE 
				r.data_inserimento between _data_1::date and _data_2::date and 
				l.codice_macroarea = 'MOLLBAN' and l.codice_aggregazione = 'MOLLBAN' and l.codice_attivita = 'MOLLBAN'	
				
			ORDER BY 
				r.asl, r.comune,  --[[#3]
				r.data_inserimento 
			desc
		)
		select -- nome|tipo 
			'progressivo|text|dig' 
			, 'asl|text|dig' 
			, 'localita|text|dig' 
			, 'taglia_non_commerciale|text|dig' 
			, 'stato_classificazione|text|dig' 
			, 'numero_decreto|text|dig' 
			, 'data_classificazione|dateISO8601|dig' 
			, 'data_fine_classificazione|dateISO8601|dig' 
			, 'data_sospensione|dateISO8601|dig' 
			, 'numero_decreto_sospensione_revoca|text|dig'  
			, 'data_provvedimento_sospensione_revoca|dateISO8601|dig'  
			, 'zona_di_produzione|text|dig' 
			, 'cun|text|dig' 
			, 'classificazione|text|dig'  
			, 'specie_molluschi|text|dig'  
			, 'comune|text|dig'  
			, 'coord_poligono|text|dig' 
			, 'coord_punto_di_prelievo|text|dig' 
			, 'provvedimento_restrittivo |text|dig'  
			, 'concessione_num|text|dig' 
			, 'concessione_data|dateISO8601|dig' 
			, 'concessione_data_scadenza|dateISO8601|dig'  
			, 'concessione_denominazione|text|dig'  
			, 'concessione_legale_rappresentante|text|dig' 
			, 'concessione_stato|text|dig'  
			, 'macroarea|text|dig' 
			, 'aggregazione|text|dig'  
			, 'attivita|text|dig' 
			, 'codice_macroarea|text|dig|ra' 
			, 'codice_aggregazione|text|dig|ra' 
			, 'codice_attivita|text|dig|ra' 
			, 'data_inserimento|dateISO8601|dig'  
			, 'riferimento_id|text|ra'
			, 'riferimento_nome_tab|text|ra'
			, 'id_linea|text|ra'
			, 'id_norma|text|ra'   
		UNION ALL
		select 
			lpad(n::text,6,'0') as progressivo --[[#4]]
			, cte.asl 
			, cte.localita 
			, cte.taglia_non_commerciale 
			, cte.stato_classificazione 
			, cte.numero_decreto 
			, to_char(cte.data_classificazione::date,'YYYY-MM-DD"T"HH24:MI:SSOF') 
			, to_char(cte.data_fine_classificazione::date,'YYYY-MM-DD"T"HH24:MI:SSOF') 
			, to_char(cte.data_sospensione::date,'YYYY-MM-DD"T"HH24:MI:SSOF') 
			, cte.numero_decreto_sospensione_revoca  
			, to_char(cte.data_provvedimento_sospensione_revoca::date,'YYYY-MM-DD"T"HH24:MI:SSOF') 
			, cte.zona_di_produzione 
			, cte.cun 
			, cte.classificazione  
			, cte.specie_molluschi  
			, cte.comune  
			, cte.coord_poligono 
			, cte.coord_punto_di_prelievo 
			, cte.provvedimento_restrittivo   
			, cte.concessione_num 
			, to_char(cte.concessione_data::date,'YYYY-MM-DD"T"HH24:MI:SSOF')  
			, to_char(cte.concessione_data_scadenza::date,'YYYY-MM-DD"T"HH24:MI:SSOF') 
			, cte.concessione_denominazione  
			, cte.concessione_legale_rappresentante 
			, cte.concessione_stato  
			, cte.macroarea 
			, cte.aggregazione  
			, cte.attivita 
			, cte.codice_macroarea 
			, cte.codice_aggregazione 
			, cte.codice_attivita 
			, to_char(cte.data_inserimento,'YYYY-MM-DD"T"HH24:MI:SSOF') as data_inserimento --[[#1]]
			, cte.riferimento_id::text
			, cte.riferimento_id_nome_tab
			, cte.id_linea::text
			, cte.id_norma::text
		from cte;
	END IF;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_anagrafica_molluschi_bivalvi(text, text, text)
  OWNER TO postgres;
  
  -- Function: digemon.dbi_get_campi_estesi_molluschi_bivalvi()

 --DROP FUNCTION digemon.dbi_get_campi_estesi_molluschi_bivalvi();

CREATE OR REPLACE FUNCTION digemon.dbi_get_campi_estesi_molluschi_bivalvi()
  RETURNS TABLE(riferimento_id integer, taglia_non_commerciale text, stato_classificazione text, numero_decreto text, data_classificazione text, data_fine_classificazione text, data_sospensione text, numero_decreto_sospensione_revoca text, data_provvedimento_sospensione_revoca text, zona_di_produzione text, cun text, classificazione text, specie_molluschi text, comune text, localita text, coord_poligono text, coord_punto_di_prelievo text, provvedimento_restrittivo text, concessione_num text, concessione_data text, concessione_data_scadenza text, concessione_denominazione text, concessione_legale_rappresentante text, concessione_stato text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY
select distinct 
o.org_id as riferimento_id
,case when o.taglia_non_commerciale then 'SI' else 'NO' end::text as taglia_non_commerciale
,case when o.stato_classificazione is null then 'ATTIVO' else l.description end::text as stato_classificazione
,o.numaut::text as numero_decreto
,to_char(o.data_classificazione, 'dd/mm/yy')::text as data_classificazione
,to_char(o.data_fine_classificazione, 'dd/mm/yy')::text as data_fine_classificazione
,to_char(o.data_sospensione, 'dd/mm/yy')::text as data_sospensione
,o.numero_decreto_sospensione_revoca::text as numero_decreto_sospensione_revoca
,to_char(o.data_provvedimento_sospensione_revoca, 'dd/mm/yy')::text as data_provvedimento_sospensione_revoca
,lzdp.description::text as zona_di_produzione
,o.cun::text as cun
,lca.description::text as classificazione
,string_agg(distinct tm.cammino, '; 
')::text as specie_molluschi
,oaope.city::text as comune
,o.name::text as localita
,string_agg(distinct concat('LAT: ', czp1.latitude, ' LONG: ', czp1.longitude), ';
')::text as coord_poligono
,string_agg(distinct concat('LAT: ', czp2.latitude, ' LONG: ', czp2.longitude), ';
 ')::text as coord_punto_di_prelievo
,case when o.tipologia_acque in (4,5) then 'N.D.' else 'Favorevole' end::text as provvedimento_restrittivo
,c_associati.num_concessione::text as concessione_num
,c_associati.data_concessione::text as concessione_data
,c_associati.data_scadenza::text as concessione_data_scadenza
,c_associati_org.name::text as concessione_denominazione
,c_associati_org.nome_rappresentante::text as concessione_legale_rappresentante
,case when c_associati.data_scadenza < now() then 'SCADUTA'
when c_associati.data_sospensione is null then 'IN CONCESSIONE'
when c_associati.data_sospensione is not null then c_associati_sosp.description
else 'IN CONCESSIONE' end::text as concessione_stato
from organization o
left join organization_address oaope on o.org_id = oaope.org_id and oaope.address_type = 5 and oaope.trasheddate is null
left join coordinate_zone_produzione czp1 on czp1.address_id = oaope.address_id and czp1.tipologia = 1 and oaope.org_id = o.org_id and czp1.latitude > 0
left join coordinate_zone_produzione czp2 on czp2.address_id = oaope.address_id and czp2.tipologia = 2 and oaope.org_id = o.org_id and czp2.latitude > 0
left join lookup_stato_classificazione l on l.code = o.stato_classificazione 
left join lookup_classi_acque lca on lca.code = o.classificazione 
left join lookup_zone_di_produzione lzdp on lzdp.code = o.tipologia_acque 
left join lookup_site_id asl on o.site_id = asl.code 
left join tipo_molluschi tm on tm.id_molluschi = o.org_id

left join concessionari_associati_zone_in_concessione c_associati on c_associati.id_zona = o.org_id and c_associati.enabled = true
left join organization c_associati_org on c_associati_org.org_id = c_associati.id_concessionario and c_associati_org.tipologia = 211 
left join lookup_classi_acque c_associati_sosp on c_associati_sosp.code = c_associati.id_sospensione

where o.trashed_date is null and o.tipologia =201

group by o.org_id, o.taglia_non_commerciale,o.stato_classificazione,l.description,o.numaut,o.data_classificazione,o.data_fine_classificazione,o.data_sospensione,o.numero_decreto_sospensione_revoca, o.data_provvedimento_sospensione_revoca,lzdp.description,o.cun,lca.description,asl.description,oaope.city,o.name,o.tipologia_acque,c_associati.num_concessione ,c_associati.data_concessione,c_associati.data_scadenza,c_associati_org.name,c_associati_org.nome_rappresentante,c_associati.data_sospensione,c_associati_sosp.description;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_campi_estesi_molluschi_bivalvi()
  OWNER TO postgres;

  select * from  digemon.dbi_get_anagrafica_molluschi_bivalvi()