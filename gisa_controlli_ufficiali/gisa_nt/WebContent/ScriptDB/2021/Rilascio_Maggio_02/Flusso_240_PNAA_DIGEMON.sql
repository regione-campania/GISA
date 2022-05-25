-- Function: digemon.estrazione_campioni_pnaa(timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION digemon.estrazione_campioni_pnaa(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION digemon.estrazione_campioni_pnaa(
    IN _data_1 timestamp without time zone DEFAULT '1900-01-01 00:00:00'::timestamp without time zone,
    IN _data_2 timestamp without time zone DEFAULT NULL::timestamp without time zone)
  RETURNS TABLE(asl text, "id controllo" text, "id campione" text, "Data controllo" timestamp without time zone, "Data prelievo" timestamp without time zone, "Alias indicatore" text, 
  "Motivazione campione" text, "Identificativo campione" text, esito text, "nucleo ispettivo" text, "Numero Scheda" text, "Tipo di Alimento" text, "A1 strategia di campionamento" text, 
  "Mangime per piu Specie (Valido per Matrice Mangimi)" text, "A2. Metodo di Campionamento" text, "A3 Attributi di Principi farmacologicamente attivi e additivi" text, 
  "A3. Principi farmacologicamente attivi e additivi CARRY OVER" text, "A3. Quantità di P.A./Coccidiostatico aggiunta in produzione" text, "A3. Contaminanti inorganici e composti az. pest. radio." text, 
  "A5. Luogo di Prelievo" text, "A6. Codice identificativo luogo di prelievo" text, "A8 indirizzo luogo prelievo" text, "A9 comune" text, "A10 provincia" text, "A11.1 latitudine" text, 
  "A11.2 longitudine" text, "A12 ragione_sociale" text, "A13 rappresentante legale" text, "A14 codice fiscale rappresentante" text, "A15 detentore" text, "B1. Matrice del campione" text, 
  "B1. Specifica Materia prima/mangime semplice" text, "B1. Specie vegetale dichiarata" text, "B2. Trattamento applicato al mangime prelevato" text, "B4. Ragione sociale ditta produttrice" text, "B5. Indirizzo ditta produttrice" text, "B6. Specie e categoria animale a cui l'alimento destinato" text, "B7. Metodo di produzione" text, "B8. Nome commerciale del mangime" text, "B9. Stato del prodotto al momento del prelievo" text, "B10. Ragione sociale responsabile etichettatura" text, "B11.Indirizzo responsabile etichettatura" text, "B12. Paese di produzione" text, "B14. Data di scadenza" text, "B15. Numero di lotto" text, "B16. Dimensione di lotto" text, "B17. Ingredienti" text) AS
$BODY$
DECLARE
BEGIN
	return QUERY
		
		select
		distinct
		asl.description::text,
		t.id_controllo_ufficiale::text,
		t.ticketid::text,
		cu.assigned_date,
		t.assigned_date,
	        dpat.alias_indicatore::text,
		dpat.descrizione::text as motivazione_campione,
		t.identificativo::text, 
		t.esito::text, 
		numeroscheda.valore_campione::text, 
		string_agg(distinct l_tipoalimento.description, '-')::text,
		string_agg(distinct case when c.contact_id> 0 then concat_ws(' ', c.namefirst, c.namelast) else concat_ws(' ', ce.namefirst, ce.namelast) end, '; ')::text as nucleo_ispettivo,
		CASE WHEN dpat.descrizione::text ~~* '%SORV%'::text THEN 'PIANO SORVEGLIANZA' WHEN dpat.descrizione::text ~~* '%MON%'::text THEN 'PIANO MONITORAGGIO'
		WHEN dpat.descrizione::text ~~* '%EXTRAPIANO%'::text THEN 'EXTRA PIANO' WHEN dpat.descrizione::text ~~* '%SOSPETT%'::text THEN 'SOSPETTO'
		ELSE 'N.D.'::text END,
		mangime.valore_campione::text,
		string_agg(distinct l_metodocampionamento.description, '-')::text,
		string_agg(distinct l_principifarmaco.description, '-')::text,
		string_agg(distinct l_principifarmacocarryover.description, '-')::text, 
		quantitapa.valore_campione::text,
		string_agg(distinct l_contaminanti.description, '-')::text,
		string_agg(distinct l_luogoprelievo.description, '-')::text,--a5
		codiceprelievo.valore_campione, --a6

		
	        CASE WHEN org.org_id > 0 THEN coalesce(az.indrizzo_azienda::text, orga.addrline1::text) WHEN opstab.id > 0 THEN opind.via::text WHEN sistab.id>0 THEN siind.via::text end, --a8
		CASE WHEN org.org_id > 0 THEN coalesce(azc.comune::text, orga.city::text) WHEN opstab.id > 0 THEN opc.nome::text WHEN sistab.id>0 THEN sic.nome::text end, --a9
		CASE WHEN org.org_id > 0 THEN 
		CASE when org.tipologia = 2 then coalesce(az.prov_sede_azienda::text,orga.city::text) 
		WHEN org.tipologia <> 2 and org.site_id = 201 then 'AV'
		WHEN org.tipologia <> 2 and org.site_id = 202 then 'BN'
		WHEN org.tipologia <> 2 and org.site_id = 203 then 'CE'
		WHEN org.tipologia <> 2 and org.site_id in (204,205,206) then 'NA'
		WHEN org.tipologia <> 2 and org.site_id = 207 then 'SA' END		
		WHEN opstab.id > 0 THEN opprov.description::text WHEN sistab.id>0 THEN siprov.description::text end, --a10
		CASE WHEN org.org_id > 0 THEN coalesce(az.latitudine_geo::text, latitude::text) WHEN opstab.id > 0 THEN opind.latitudine::text WHEN sistab.id>0 THEN siind.latitudine::text end, --a11_1
		CASE WHEN org.org_id > 0 THEN coalesce(az.longitudine_geo::text, longitude::text) WHEN opstab.id > 0 THEN opind.longitudine::text WHEN sistab.id>0 THEN siind.longitudine::text end, --a11_2
		CASE WHEN org.org_id > 0 THEN coalesce(op_prop.nominativo::text, org.name::text) WHEN opstab.id > 0 THEN opop.ragione_sociale::text WHEN sistab.id>0 THEN siop.ragione_sociale::text end, --a12
		CASE WHEN org.org_id > 0 THEN coalesce(op_prop.nominativo::text,concat(org.nome_rappresentante, ' ', org.cognome_rappresentante)::text) WHEN opstab.id > 0 THEN concat(opsf.nome, ' ', opsf.cognome)::text WHEN sistab.id>0 THEN concat(sisf.nome, ' ', sisf.cognome)::text end, --a13
		CASE WHEN org.org_id > 0 THEN COALESCE(op_prop.cf, org.codice_fiscale_rappresentante)::text WHEN opstab.id > 0 THEN opsf.codice_fiscale WHEN sistab.id>0 THEN sisf.codice_fiscale end, --a14
		CASE WHEN org.org_id > 0 THEN coalesce(op_detentore.nominativo::text,concat(org.nome_rappresentante, ' ', org.cognome_rappresentante)::text) WHEN opstab.id > 0 THEN opsf.codice_fiscale::text WHEN sistab.id>0 THEN sisf.codice_fiscale::text end, --a15
		

case when l_matricecampione.code > 0 then string_agg(distinct l_matricecampione.description, '-')
when matricecampione.valore_campione = 'm1' then 'Materia prima/mangime semplice' 
when matricecampione.valore_campione = 'm2' then 'Mangime composto' 
when matricecampione.valore_campione = 'm3' then '' 
when matricecampione.valore_campione = 'm4' then '' 
when matricecampione.valore_campione = 'm5' then 'Additivo per mangimi' 
when matricecampione.valore_campione = 'm6' then 'Premiscela di additivi' 
when matricecampione.valore_campione = 'm7' then 'Acqua di abbeverata' 
when matricecampione.valore_campione = 'm8' then '' 
when matricecampione.valore_campione = 'm9' then 'Mangime medicato/prodotto intermendio' 
end::text, --b1
		string_agg(distinct l_specievegetale.description, '-')::text, --specievegetale
		trattamentoapplicato.valore_campione::text, --b20
	
		codicematrice.valore_campione::text, 
		ragionesocialeditta.valore_campione::text,
		indirizzoditta.valore_campione::text,
		string_agg(distinct l_speciecategoria.description, '-')::text,
		string_agg(distinct l_metodoproduzione.description, '-')::text,
		nomecommerciale.valore_campione::text,
		string_agg(distinct l_statoprodotto.description, '-')::text,
		ragionesocialeresponsabile.valore_campione::text,
		indirizzoresponsabile.valore_campione::text,
		paeseproduzione.valore_campione::text, 
		datascadenza.valore_campione::text, 
		numerolotto.valore_campione::text, 
		dimensionelotto.valore_campione::text, 
		ingredienti.valore_campione::text
		

		from ticket t
		join dpat_indicatore_new dpat on dpat.id = t.motivazione_piano_campione and dpat.codice_esame in ('PNASV', 'PNAMT') -- valutare se mettere lookup_piano_monitoraggio?
		join ticket cu on cu.ticketid::text = t.id_controllo_ufficiale and cu.trashed_date is null and t.trashed_date is null
		and (t.assigned_date between _data_1 and _data_2)

		
left join organization org on cu.org_id > 0 and org.org_id = cu.org_id and org.trashed_date is null

left join organization_address orga on cu.org_id > 0 and orga.org_id = cu.org_id and org.trashed_date is null and orga.address_type=5

left join aziende az ON az.cod_azienda = org.account_number::text AND org.tipologia = 2
LEFT JOIN operatori_allevamenti op_detentore ON org.cf_correntista::text = op_detentore.cf
LEFT JOIN operatori_allevamenti op_prop ON org.codice_fiscale_rappresentante::text = op_prop.cf
LEFT JOIN comuni_old azc ON azc.codiceistatcomune::text = az.cod_comune_azienda
left join opu_stabilimento opstab on cu.id_stabilimento > 0 and opstab.id = cu.id_stabilimento and opstab.trashed_date is null
left join opu_operatore opop on opop.id = opstab.id_operatore
left join opu_rel_operatore_soggetto_fisico oprelos on oprelos.id_operatore = opop.id
left join opu_soggetto_fisico opsf on opsf.id = oprelos.id_soggetto_fisico
left join opu_indirizzo opind on opind.id = opstab.id_indirizzo
left join comuni1 opc on opc.id = opind.comune
left join lookup_province opprov on opprov.code = opc.cod_provincia::integer
left join sintesis_stabilimento sistab on cu.alt_id > 0 and sistab.alt_id = cu.alt_id and sistab.trashed_date is null
left join sintesis_operatore siop on siop.id = sistab.id_operatore
left join sintesis_rel_operatore_soggetto_fisico sirelos on sirelos.id_operatore = siop.id
left join sintesis_soggetto_fisico sisf on sisf.id = sirelos.id_soggetto_fisico
left join sintesis_indirizzo siind on siind.id = sistab.id_indirizzo
left join comuni1 sic on sic.id = siind.comune
left join lookup_province siprov on siprov.code = sic.cod_provincia::integer


		join lookup_site_id asl on asl.code = cu.site_id
		LEFT JOIN cu_nucleo nucleo on nucleo.id_controllo_ufficiale = cu.ticketid
		LEFT JOIN access_ a on a.user_id = nucleo.id_componente
		LEFT JOIN access_ext_ ae on ae.user_id = nucleo.id_componente
		LEFT JOIN contact_ c on c.contact_id = a.contact_id
		LEFT JOIN contact_ext_ ce on ce.contact_id = ae.contact_id

left JOIN campioni_fields_value mangime on mangime.id_campione = t.ticketid and mangime.id_campioni_html_fields = 56 and mangime.enabled
left JOIN campioni_fields_value numeroscheda on numeroscheda.id_campione = t.ticketid and numeroscheda.id_campioni_html_fields = 48 and numeroscheda.enabled
left JOIN campioni_fields_value tipoalimento on tipoalimento.id_campione = t.ticketid and tipoalimento.id_campioni_html_fields = 54 and tipoalimento.enabled
LEFT JOIN lookup_dpa l_tipoalimento on l_tipoalimento.short_description = tipoalimento.valore_campione and tipoalimento.id_campioni_html_fields = 54 and tipoalimento.enabled

LEFT JOIN campioni_fields_value metodocampionamento on metodocampionamento.id_campione = t.ticketid and metodocampionamento.id_campioni_html_fields = 50 and metodocampionamento.enabled
LEFT JOIN lookup_metodo_campionamento l_metodocampionamento on l_metodocampionamento.short_description = metodocampionamento.valore_campione and metodocampionamento.id_campioni_html_fields = 50 and metodocampionamento.enabled
		
left JOIN campioni_fields_value principifarmaco on principifarmaco.id_campione = t.ticketid and principifarmaco.id_campioni_html_fields = 1 and principifarmaco.enabled
LEFT JOIN lookup_principi_farm_attivi_additivi l_principifarmaco on l_principifarmaco.code = principifarmaco.valore_campione::int and principifarmaco.id_campioni_html_fields = 1 and principifarmaco.enabled

left JOIN campioni_fields_value principifarmacocarryover on principifarmacocarryover.id_campione = t.ticketid and principifarmacocarryover.id_campioni_html_fields = 605 and principifarmacocarryover.enabled
LEFT JOIN lookup_principi_farm_attivi_additivi_carryover l_principifarmacocarryover on l_principifarmacocarryover.code = principifarmacocarryover.valore_campione::int and principifarmacocarryover.id_campioni_html_fields = 605 and principifarmacocarryover.enabled

left JOIN campioni_fields_value quantitapa on quantitapa.id_campione = t.ticketid and quantitapa.id_campioni_html_fields = 606 and quantitapa.enabled

left JOIN campioni_fields_value contaminanti on contaminanti.id_campione = t.ticketid and contaminanti.id_campioni_html_fields = 607 and contaminanti.enabled
LEFT JOIN lookup_contaminanti l_contaminanti on l_contaminanti.code = contaminanti.valore_campione::int and contaminanti.id_campioni_html_fields = 607 and contaminanti.enabled

left JOIN campioni_fields_value luogoprelievo on luogoprelievo.id_campione = t.ticketid and luogoprelievo.id_campioni_html_fields = 51 and luogoprelievo.enabled
LEFT JOIN lookup_luogo_prelievo l_luogoprelievo on l_luogoprelievo.code = luogoprelievo.valore_campione::int and luogoprelievo.id_campioni_html_fields = 51 and luogoprelievo.enabled

left JOIN campioni_fields_value codiceprelievo on codiceprelievo.id_campione = t.ticketid and codiceprelievo.id_campioni_html_fields = 5 and codiceprelievo.enabled
left JOIN campioni_fields_value codicematrice on codicematrice.id_campione>0 and codicematrice.id_campione = t.ticketid and codicematrice.id_campioni_html_fields = 49 and codicematrice.enabled
left JOIN campioni_fields_value ragionesocialeditta on ragionesocialeditta.id_campione = t.ticketid and ragionesocialeditta.id_campioni_html_fields = 7 and ragionesocialeditta.enabled
LEFT JOIN campioni_fields_value indirizzoditta on indirizzoditta.id_campione = t.ticketid and indirizzoditta.id_campioni_html_fields = 8 and indirizzoditta.enabled

LEFT JOIN campioni_fields_value speciecategoria on speciecategoria.id_campione = t.ticketid and speciecategoria.id_campioni_html_fields = 3 and speciecategoria.enabled
LEFT JOIN lookup_specie_pnaa l_speciecategoria on l_speciecategoria.code = speciecategoria.valore_campione::int and speciecategoria.id_campioni_html_fields = 3 and speciecategoria.enabled

LEFT JOIN campioni_fields_value metodoproduzione on metodoproduzione.id_campione = t.ticketid and metodoproduzione.id_campioni_html_fields = 55 and metodoproduzione.enabled
LEFT JOIN lookup_circuito_pna l_metodoproduzione on l_metodoproduzione.description = metodoproduzione.valore_campione and metodoproduzione.id_campioni_html_fields = 55 and metodoproduzione.enabled

LEFT JOIN campioni_fields_value nomecommerciale on nomecommerciale.id_campione = t.ticketid and nomecommerciale.id_campioni_html_fields = 9 and nomecommerciale.enabled
LEFT JOIN campioni_fields_value statoprodotto on statoprodotto.id_campione = t.ticketid and statoprodotto.id_campioni_html_fields = 4 and statoprodotto.enabled
LEFT JOIN lookup_prodotti_pnaa l_statoprodotto on l_statoprodotto.code = statoprodotto.valore_campione::int and statoprodotto.id_campioni_html_fields = 4

LEFT JOIN campioni_fields_value ragionesocialeresponsabile on ragionesocialeresponsabile.id_campione = t.ticketid and ragionesocialeresponsabile.id_campioni_html_fields = 601 and ragionesocialeresponsabile.enabled
LEFT JOIN campioni_fields_value indirizzoresponsabile on indirizzoresponsabile.id_campione = t.ticketid and indirizzoresponsabile.id_campioni_html_fields = 602 and indirizzoresponsabile.enabled
LEFT JOIN campioni_fields_value paeseproduzione on paeseproduzione.id_campione = t.ticketid and paeseproduzione.id_campioni_html_fields = 16 and paeseproduzione.enabled
LEFT JOIN campioni_fields_value datascadenza on datascadenza.id_campione = t.ticketid and datascadenza.id_campioni_html_fields = 53 and datascadenza.enabled
LEFT JOIN campioni_fields_value numerolotto on numerolotto.id_campione = t.ticketid and numerolotto.id_campioni_html_fields = 603 and numerolotto.enabled
LEFT JOIN campioni_fields_value dimensionelotto on dimensionelotto.id_campione = t.ticketid and dimensionelotto.id_campioni_html_fields = 45 and dimensionelotto.enabled
LEFT JOIN campioni_fields_value ingredienti on ingredienti.id_campione = t.ticketid and ingredienti.id_campioni_html_fields = 47 and ingredienti.enabled
LEFT JOIN campioni_fields_value matricecampione on matricecampione.id_campione = t.ticketid and matricecampione.id_campioni_html_fields = 600 and matricecampione.enabled
LEFT JOIN lookup_matrice_campione_sinvsa_new l_matricecampione on l_matricecampione.code::text = matricecampione.valore_campione and matricecampione.id_campioni_html_fields = 600 and matricecampione.enabled

LEFT JOIN campioni_fields_value specievegetale on specievegetale.id_campione = t.ticketid and specievegetale.id_campioni_html_fields = 604 and specievegetale.enabled
LEFT JOIN lookup_specie_vegetale_pnaa l_specievegetale on l_specievegetale.code::text = specievegetale.valore_campione and specievegetale.id_campioni_html_fields = 604 and specievegetale.enabled

LEFT JOIN campioni_fields_value trattamentoapplicato on trattamentoapplicato.id_campione = t.ticketid and trattamentoapplicato.id_campioni_html_fields = 6 and trattamentoapplicato.enabled


group by 
		asl.description,
		t.id_controllo_ufficiale, 
		t.ticketid, 
		cu.assigned_date,
		dpat.alias_indicatore,
		dpat.descrizione,
		mangime.valore_campione,
		numeroscheda.valore_campione, 
		quantitapa.valore_campione,
		codiceprelievo.valore_campione, 
		trattamentoapplicato.valore_campione,
		az.indrizzo_azienda,opind.via, siind.via,
		azc.comune, opc.nome, sic.nome,
		az.prov_sede_azienda, opprov.description, siprov.description,
		az.latitudine_geo, az.longitudine_geo, opind.latitudine, opind.longitudine, siind.latitudine, siind.longitudine,
		orga.addrline1, orga.city, orga.state, orga.latitude, orga.longitude,
		op_prop.nominativo,
		op_prop.cf, org.codice_fiscale_rappresentante,
		op_detentore.nominativo,
		org.org_id, opstab.id, sistab.id,
		opop.ragione_sociale, siop.ragione_sociale,
		opsf.nome, opsf.cognome, sisf.nome, sisf.cognome,
		opsf.codice_fiscale, sisf.codice_fiscale,
		codicematrice.valore_campione, 
		ragionesocialeditta.valore_campione,
		indirizzoditta.valore_campione,
		nomecommerciale.valore_campione,
		ragionesocialeresponsabile.valore_campione,
		indirizzoresponsabile.valore_campione,
		paeseproduzione.valore_campione, 
		datascadenza.valore_campione, 
		numerolotto.valore_campione, 
		dimensionelotto.valore_campione, 
		ingredienti.valore_campione,
		l_matricecampione.code,
		matricecampione.valore_campione;


 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.estrazione_campioni_pnaa(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;
