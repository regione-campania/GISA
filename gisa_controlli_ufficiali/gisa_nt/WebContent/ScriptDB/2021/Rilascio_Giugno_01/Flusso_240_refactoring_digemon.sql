-- Function: digemon.estrazione_campioni_pnaa(timestamp without time zone, timestamp without time zone)
--drop FUNCTION digemon.estrazione_campioni_pnaa_2021(timestamp without time zone, timestamp without time zone);
CREATE OR REPLACE FUNCTION digemon.estrazione_campioni_pnaa(IN _data_1 timestamp without time zone,
    IN _data_2 timestamp without time zone)
  RETURNS TABLE("id campione" integer 
	,"id controllo" integer
	, "numero scheda" text	
	, "Data campione" timestamp without time zone
	, "Verbale campione" text
	, "Codice preaccettazione campione" text
	, "Asl campione" text
	, "CU Per conto di" text
	, "Verbale Anno" text
	, "Verbale Mese" text
	, "Verbale giorno" text
	, "Verbale ore" text
	, "Verbale Presente al prelievo" text
	, "Verbale sottoscritti" text
	, "Verbale Numero campioni prelevati" text 
	, "Verbale DPA" text
	, "A1. Strategia di campionamento" text
	, "A2. Metodo di campionamento" text
	, "A3. Programma di Controllo" text
	, "A3. specifica principi farmacologicamente attivi" text
	, "A3. specifica additivi nutrizionali" text
	, "A3. specifica cocciodiostatici/istomonostatici" text
	, "A3. specifica additivi tecnologici" text
	, "A3. specifica Principi additivi organolettici" text
	, "A3. specifica additivi zootecnici" text
	, "A3. specifica principi farm. attivi carry over"  text
	, "A3. specifica coccidiostatici/istomonastici carry over"  text
	, "A3. specifica contamin. inorganici e composti azotati"  text
	, "A3. specifica radionuclidi"  text
	, "A3. specifica pesticidi"  text --35sima
	, "A3. specifica micotossine" text
	, "A3. specifica altro" text 
	, "A3. Principi farmacologicamente attivi" text --id_principi_additivi spostare
	, "A3. Principi farmacologicamente attivi carry over" text --id_principi_additivi_co
	, "A3. Quantità di P.A/Cocc." text
	, "A3. Contaminanti inorganici e composti az, pe, ra" text --id_contaminanti int
	, "A4. Prelevatore" text
	, "A5. Luogo di prelievo" text
	, "A6. Codice luogo di prelievo"  text
	, "A7. Targa mezzo di trasporto"  text
	, "A8. Indirizzo del luogo di prelievo" text
	, "A9. Comune" text
	, "A10. Provincia" text
	, "A11. Latitudine luogo" text
	, "A11. Longitudine luogo" text
	, "A12. Ragione sociale" text
	, "A13. Rappresentante legale" text
	, "A14. Codice fiscale" text
	, "A15. Detentore" text
	, "A16. Telefono" text
	, "B1. Matrice del campione" text --b1
        , "B1. Specie vegetale dichiarata" text  
	, "B1. Specifica materia prima/mangime semplice" text 
	, "B1. Mangime composto" text
	, "B1. Premiscela additivi" text
	, "B1. Categoria Sottoprodotti" text
	, "B2. Trattamento mangime" text --b2
	, "B3. Confezionamento" text
	, "B4. Ragione sociale ditta produttrice" text
	, "B5. Indrizzo ditta produttrice" text
	, "B6. Specie e categoria animale per alimento" text
	, "B7. Metodo di produzione" text
	, "B8. Nome commerciale mangime" text --b8
	, "B9. Stato prodotto" text
	, "B10. Ragione sociale responsabile etichettatura" text --b10
	, "B11. Indirizzo responsabile etichettatura" text --b11
	, "B12. Paese di produzione" text --b12
	, "B13. Data di produzione" text --b13
	, "B14. Data di scadenza" text --b14
	, "B15. Numero di lotto" text --b15
	, "B16. Dimensione del lotto" text --b16
	, "B17. Ingredienti" text -- b17
	, "B18. Commenti ulteriori sul mangime" text
	, "C1. Laboratorio destinazione" text
	, "D. Informazioni cartellino" text
	, "D. Descrizione attrezzature" text
	, "D. Num punti" text
	, "D. Num CE" text
	, "D. Peso Volume" text
	, "D. Operazioni"  text
	, "D. Kg/lt" text
	, "D. Operazioni campione globale" text
	, "D. CF" text
	, "D. g/ml" text
	, "D. Dichiarazione" text
	, "D. Conservazione campione" text 
	, "D. Numero copia/e" text
	, "D. Numero campioni finali" text
	, "D. Custode campione" text
	, "D. Per conto di" text --88sima colonna
	, "D. Rinuncia per controversia/controperizia" text
	, "D. Volume finale" text
	, "D. Numero campioni finali inviati" text
	, "D. Numero copie inviate" text
	, "D. Destinazione invio" text
	, "D. Data invio" text
	, "D. CG ridotto" text
	, "D. CG/CR" text
	, "D. Sequestro partita" text
	) AS
$func$
DECLARE
   rec integer;
BEGIN
     FOR rec IN
        SELECT t.ticketid from ticket t
		join dpat_indicatore_new dpat on dpat.id = t.motivazione_piano_campione and dpat.codice_esame in ('PNASV', 'PNAMT') -- valutare se mettere lookup_piano_monitoraggio?
		join ticket cu on cu.ticketid::text = t.id_controllo_ufficiale and cu.trashed_date is null and t.trashed_date is null
		and (t.assigned_date between _data_1 and _data_2) 
		--and (t.assigned_date between '2020-01-01' and '2020-12-31') --470 id_campione
     LOOP
        RETURN QUERY
        with cte as (SELECT 
		a.id_campione
		, (select id_controllo_ufficiale::int from ticket where ticketid = a.id_campione) as id_controllo
		, a.numero_scheda 	
		, a.campione_data 
		, a.campione_verbale 
		, a.campione_codice_preaccettazione 
		, a.campione_asl 
		, a.campione_percontodi 
		, a.campione_anno 
		, a.campione_mese 
		, a.campione_giorno 
		, a.campione_ore 
		, a.campione_presente 
		, a.campione_sottoscritto 
		, a.campione_num_prelevati  --20sima colonna
		, g.description::text as id_dpa 
		, f.description::text as id_strategia_campionamento 
		, h.description::text as id_metodo_campionamento 
		, a.campione_motivazione 
		, a.programma_controllo_fa_specifica 
		, a.programma_controllo_an_specifica 
		, a.programma_controllo_ci_specifica 
		, a.programma_controllo_at_specifica 
		, a.programma_controllo_ao_specifica 
		, a.programma_controllo_az_specifica 
		, a.programma_controllo_co_fa_specifica 
		, a.programma_controllo_co_ci_specifica 
		, a.programma_controllo_in_ci_specifica 
		, a.programma_controllo_in_ra_specifica 
		, a.programma_controllo_in_pe_specifica  --35sima
		, a.micotossine_specifica 
		, a.altro_specifica  
		, i.description::text as id_principi_additivi 
		, j.description::text as id_principi_additivi_co 
		, a.quantita_pa 
		, k.description::text as id_contaminanti 
		, a.prelevatore 
		, l.description::text as id_luogo_prelievo 
		, a.codice_luogo_prelievo 
		, a.targa_mezzo 
		, a.indirizzo_luogo_prelievo 
		, a.comune_luogo_prelievo 
		, a.provincia_luogo_prelievo 
		, a.lat_luogo_prelievo 
		, a.lon_luogo_prelievo 
		, a.ragione_sociale 
		, a.rappresentante_legale 
		, a.codice_fiscale 
		, a.detentore 
		, a.telefono 
		, m.description::text as id_matrice_campione  --b1
		, case when length(a.lista_specie_vegetale_dichiarata) > 0 then (select string_agg(description,' - ') from lookup_specie_vegetale_pnaa  where code in (
		   select unnest(string_to_array(substring(a.lista_specie_vegetale_dichiarata,1,length(a.lista_specie_vegetale_dichiarata)-1), ','))::int))  
		  else '' end as lista_specie_vegetale_dichiarata   
		, a.mangime_semplice_specifica  
		, c.description::text as id_mangime_composto 
		, d.description::text as id_premiscela_additivi 
		, u.description::text as id_categoria_sottoprodotto 
		, a.trattamento_mangime  --b2
		--, a.confezionamento 
		, q.description::text as confezionamento --confezionamento
		, a.ragione_sociale_ditta_produttrice 
		, a.indirizzo_ditta_produttrice 
		, case when length(a.lista_specie_alimento_destinato) > 0 then (select string_agg(description,' - ') from lookup_specie_alimento  where code in (
		   select unnest(string_to_array(substring(a.lista_specie_alimento_destinato,1,length(a.lista_specie_alimento_destinato)-1), ','))::int)) 
		   else '' end as lista_specie_alimento_destinato  
		, n.description::text as id_metodo_produzione 
		, a.nome_commerciale_mangime  --b8
		, case when length(a.lista_stato_prodotto_prelievo) > 0 then (select string_agg(description,' - ') from lookup_prodotti_pnaa where code in (
		   select unnest(string_to_array(substring(a.lista_stato_prodotto_prelievo,1,length(a.lista_stato_prodotto_prelievo)-1), ','))::int)) 
		   else '' end as lista_stato_prodotto_prelievo 
		, a.responsabile_etichettatura  --b10
		, a.indirizzo_responsabile_etichettatura  --b11
		, a.paese_produzione  --b12
		, a.data_produzione  --b13
		, a.data_scadenza  --b14
		, a.num_lotto  --b15
		, a.dimensione_lotto  --b16
		, a.ingredienti  -- b17
		, a.commenti_mangime_prelevato 
		, a.laboratorio_destinazione 
		, e.description::text as id_allega_cartellino 
		, a.descrizione_attrezzature 
		, a.num_punti 
		, a.num_ce 
		, a.volume 
		, a.operazioni 
		, a.volume_2 
		, a.operazioni_2 
		, a.numero_cf 
		, a.quantita_gml 
		, a.dichiarazione 
		, a.conservazione_campione  
		, a.num_copie 
		, a.num_campioni_finali 
		, a.custode 
		, o.description::text as id_campione_finale 
		, p.description:: text as id_rinuncia_campione 
		, a.volume_3
		, a.num_campioni_finali_invio
		, a.num_copie_invio
		, a.destinazione_invio 
		, a.data_invio
		, r.description::text as cg_ridotto
		, s.description::text as cg_cr
		, t.description::text as sequestro_partita
		FROM get_modello_pnaa(rec) a
		left join lookup_pnaa_mangime_composto c on c.code = a.id_mangime_composto
		left join lookup_pnaa_premiscela_additivi d on d.code = a.id_premiscela_additivi
		left join lookup_pnaa_si_no e on e.code = a.id_allega_cartellino
		left join lookup_pnaa_strategia_campionamento f on f.code = a.id_strategia_campionamento
		left join lookup_dpa g on g.code = a.id_dpa
		left join lookup_metodo_campionamento h on h.code = a.id_metodo_campionamento
		left join lookup_principi_farm_attivi_additivi i on i.code = a.id_principi_additivi
		left join lookup_principi_farm_attivi_additivi_carryover j on j.code = a.id_principi_additivi_co
		left join lookup_contaminanti k on k.code = a.id_contaminanti
		left join lookup_luogo_prelievo l on l.code = a.id_luogo_prelievo
		left join lookup_matrice_campione_sinvsa_new m on m.code =  a.id_matrice_campione
		left join lookup_circuito_pna n on n.code = a.id_metodo_produzione
		left join lookup_pnaa_campione_finale o on o.code = a.id_campione_finale
		left join lookup_pnaa_si_no p on p.code = a.id_rinuncia_campione 
		left join lookup_pnaa_confezionamento q on q.code = a.id_confezionamento
		left join lookup_pnaa_cg_ridotto r on r.code = a.id_cg_ridotto
		left join lookup_pnaa_cg_cr s on s.code = a.id_cg_cr
		left join lookup_pnaa_sequestro_partita t on t.code = a.id_sequestro_partita
		left join lookup_pnaa_categoria_sottoprodotti u on t.code = a.id_categoria_sottoprodotti
		)
	  select distinct 
		  cte.id_campione
		, cte.id_controllo
		, cte.numero_scheda 	
		, cte.campione_data 
		, cte.campione_verbale 
		, cte.campione_codice_preaccettazione 
		, cte.campione_asl 
		, cte.campione_percontodi 
		, cte.campione_anno 
		, cte.campione_mese 
		, cte.campione_giorno
		, cte.campione_ore 
		, cte.campione_presente 
		, cte.campione_sottoscritto 
		, cte.campione_num_prelevati  --20sima colonna
		, cte.id_dpa 
		, cte.id_strategia_campionamento 
		, cte.id_metodo_campionamento 
		, cte.campione_motivazione 
		, cte.programma_controllo_fa_specifica 
		, cte.programma_controllo_an_specifica 
		, cte.programma_controllo_ci_specifica 
		, cte.programma_controllo_at_specifica 
		, cte.programma_controllo_ao_specifica 
		, cte.programma_controllo_az_specifica 
		, cte.programma_controllo_co_fa_specifica 
		, cte.programma_controllo_co_ci_specifica 
		, cte.programma_controllo_in_ci_specifica 
		, cte.programma_controllo_in_ra_specifica 
		, cte.programma_controllo_in_pe_specifica  --35sima
		, cte.micotossine_specifica 
		, cte.altro_specifica  
		, cte.id_principi_additivi 
		, cte.id_principi_additivi_co 
		, cte.quantita_pa 
		, cte.id_contaminanti
		, cte.prelevatore 
		, cte.id_luogo_prelievo 
		, cte.codice_luogo_prelievo 
		, cte.targa_mezzo 
		, cte.indirizzo_luogo_prelievo 
		, cte.comune_luogo_prelievo 
		, cte.provincia_luogo_prelievo 
		, cte.lat_luogo_prelievo 
		, cte.lon_luogo_prelievo 
		, cte.ragione_sociale 
		, cte.rappresentante_legale 
		, cte.codice_fiscale 
		, cte.detentore 
		, cte.telefono 
		, cte.id_matrice_campione  --b1
		, cte.lista_specie_vegetale_dichiarata   
		, cte.mangime_semplice_specifica  
		, cte.id_mangime_composto 
		, cte.id_premiscela_additivi 
		, cte.id_categoria_sottoprodotto
		, cte.trattamento_mangime  --b2
		, cte.confezionamento 
		, cte.ragione_sociale_ditta_produttrice 
		, cte.indirizzo_ditta_produttrice 
		, cte.lista_specie_alimento_destinato 
		, cte.id_metodo_produzione 
		, cte.nome_commerciale_mangime  --b8
		, cte.lista_stato_prodotto_prelievo 
		, cte.responsabile_etichettatura  --b10
		, cte.indirizzo_responsabile_etichettatura  --b11
		, cte.paese_produzione  --b12
		, cte.data_produzione  --b13
		, cte.data_scadenza  --b14
		, cte.num_lotto  --b15
		, cte.dimensione_lotto  --b16
		, cte.ingredienti  -- b17
		, cte.commenti_mangime_prelevato 
		, cte.laboratorio_destinazione 
		, cte.id_allega_cartellino 
		, cte.descrizione_attrezzature 
		, cte.num_punti 
		, cte.num_ce 
		, cte.volume 
		, cte.operazioni 
		, cte.volume_2 
		, cte.operazioni_2 
		, cte.numero_cf 
		, cte.quantita_gml 
		, cte.dichiarazione 
		, cte.conservazione_campione  
		, cte.num_copie 
		, cte.num_campioni_finali 
		, cte.custode 
		, cte.id_campione_finale 
		, cte.id_rinuncia_campione
		, cte.volume_3
		, cte.num_campioni_finali_invio
		, cte.num_copie_invio
		, cte.destinazione_invio 
		, cte.data_invio 
		, cte.cg_ridotto
		, cte.cg_cr
		, cte.sequestro_partita
	 from cte; --rec in input dovrebbe essere il singolo campione
     END LOOP;
END
$func$  LANGUAGE plpgsql;


--select * from digemon.estrazione_campioni_pnaa_2021('2021-01-01', '2021-12-31') where "id campione" = 1609580
