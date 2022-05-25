
CREATE OR REPLACE FUNCTION preaccettazionesigla.search_preaccettazione(filtri hstore)
  RETURNS SETOF json AS
$BODY$
DECLARE

	
BEGIN

	return query
	select concat('[', string_agg(row_to_json(tab2)::text, ','), ']')::json as lista_preaccettazioni from
	(select * from
		(select distinct
		       ram.ragione_sociale::text, --character varying(300)
		       ram.partita_iva::text, --bpchar
		       coalesce(nullif(trim(ram.n_reg),''), ram.num_riconoscimento)::text num_registrazione,
		       ram.asl_rif::text, --integer
		       ram.asl::text, --character varying(300)
		       ram.comune::text, --character varying
		       ram.attivita::text, --text
		       concat(cp.prefix, cp.anno, cp.progres)::text as codPreacc, --text
		       --case when sp.id_stato = 1 then 'Incompleto: non associato al campione'::text
		        --	    when sp.id_stato = 2 then 'Completo'::text
			--    end 
		       l.descrizione as stato_preaccettazione, --stato della preaccettazione: completo sta per associato al campione
		       concat(c.namelast, ' ', c.namefirst)::text as utente,
		       t.identificativo::text as codice_campione,
		       t.id_controllo_ufficiale::text as id_cu,
		       t.location::text as numero_verbale,
		       to_char(cp.entered,'DD-MM-YYYY')::text as data_generazione,
		       sp.ip_lettura_sigla as ip_lettura,
		       to_char(sp.letto_da_sigla,'DD-MM-YYYY HH24:MI:SS')::text as data_lettura
			
		from preaccettazionesigla.associazione_preaccettazione_entita ap
			join ricerche_anagrafiche_old_materializzata ram 
				on concat(ram.riferimento_id, ram.riferimento_id_nome, ram.riferimento_id_nome_tab, ram.id_linea, ram.tipologia_operatore) = 
				concat(ap.lda_riferimento_id, ap.lda_riferimento_id_nome, ap.lda_riferimento_id_nome_tab, ap.lda_id_linea, ap.tipologia_operatore)
			join preaccettazionesigla.stati_preaccettazione sp on sp.id = ap.id_stati
			join preaccettazionesigla.lookup_stati_pa l on l.id = sp.id_stato
			join preaccettazionesigla.vw_ultimo_stato us on us.cod_stato = sp.id
			left join access_ ut on us.enteredby = ut.user_id
			left join contact c on ut.contact_id = c.contact_id and trim(concat(c.namelast, ' ', c.namefirst)::text) not ilike ''
			join preaccettazionesigla.codici_preaccettazione cp on cp.id = sp.id_preaccettazione
			--verbale
			left join ticket t on (ap.tipo_entita ilike 'C' and t.ticketid::character varying = ap.riferimento_entita)
			  
		where us.id_stato in (1,2)
			and ( (filtri -> 'codice_preaccettazione' not ilike '' and concat(cp.prefix, cp.anno, cp.progres)::text ilike trim(filtri -> 'codice_preaccettazione')) or (filtri -> 'codice_preaccettazione' ilike '') ) 
			and ( ((filtri -> 'asl')::integer > 0 and ram.asl_rif = (filtri -> 'asl')::integer) or ((filtri -> 'asl')::integer = -1) ) 
			and ( (filtri -> 'osa' not ilike '' and trim(ram.ragione_sociale) ilike concat('%',filtri -> 'osa','%')) or (filtri -> 'osa' ilike '') )
			and ( (filtri -> 'partita_iva' not ilike '' and trim(ram.partita_iva::text) ilike filtri -> 'partita_iva') or (filtri -> 'partita_iva' ilike '') )
			and ( (filtri -> 'data_generazione' not ilike '' and to_char(cp.entered, 'DD-MM-YYYY') ilike trim(filtri -> 'data_generazione')) or (filtri -> 'data_generazione' ilike '') )
			and ( (filtri -> 'utente' not ilike '' 
				and (concat(c.namelast,' ',c.namefirst) ilike concat('%',filtri -> 'utente','%')  
					or concat(c.namefirst,' ',c.namelast) ilike concat('%',filtri -> 'utente','%')) 
			       ) or (filtri -> 'utente' ilike ''))

		UNION 
		
		select distinct
			ra.ragione_sociale::text, --character varying(300)
			ra.partita_iva::text, --bpchar
			coalesce(nullif(trim(ra.n_reg),''), ra.num_riconoscimento)::text num_registrazione,
			ra.asl_rif::text, --integer
			ra.asl::text, --character varying(300)
			ra.comune::text, --character varying
			ra.attivita::text, --text
			concat(cp.prefix, cp.anno, cp.progres)::text as codPreacc, --text
			--case when us.id_stato = 5 then 
			--	'RISULTATO RICEVUTO'::text  --stato della preaccettazione: risultato ricevuto sta ricevuto esito esame inviato da sigla a gisa
			--    else 
			--	'LETTO DA SIGLA'::text  --stato della preaccettazione: completo sta per associato al campione
			--    end 
			l.descrizione as stato_preaccettazione,
			concat(c.namelast, ' ', c.namefirst)::text as utente,
			t.identificativo::text as codice_campione,
			t.id_controllo_ufficiale::text as id_cu,
			t.location::text as numero_verbale,
			to_char(cp.entered,'DD-MM-YYYY')::text as data_generazione,
			sp2.ip_lettura_sigla as ip_lettura,
			to_char(sp2.letto_da_sigla,'DD-MM-YYYY HH24:MI:SS')::text as data_lettura
			 
		from preaccettazionesigla.associazione_preaccettazione_entita ape join 
			     preaccettazionesigla.stati_preaccettazione sp on ape.id_stati = sp.id
			     join preaccettazionesigla.stati_preaccettazione sp2 on sp.id_preaccettazione = sp2.id_preaccettazione
			     join preaccettazionesigla.lookup_stati_pa l on l.id = sp2.id_stato -- è corretto?
			     join preaccettazionesigla.vw_ultimo_stato us on us.cod_stato = sp2.id
			     join preaccettazionesigla.codici_preaccettazione cp on cp.id = sp2.id_preaccettazione
			     left join access_ ut on cp.enteredby = ut.user_id
			     left join contact c on ut.contact_id = c.contact_id and trim(concat(c.namelast, ' ', c.namefirst)::text) not ilike ''
			     join ricerche_anagrafiche_old_materializzata ra on ra.riferimento_id = ape.lda_riferimento_id 
										and ra.riferimento_id_nome = ape.lda_riferimento_id_nome 
										and ra.riferimento_id_nome_tab = ape.lda_riferimento_id_nome_tab 
										and ra.id_linea = ape.lda_id_linea 
										and ra.tipologia_operatore = ape.tipologia_operatore
			     --verbale
			     left join ticket t on (ape.tipo_entita ilike 'C' and t.ticketid::character varying = ape.riferimento_entita)
			where ape.tipo_entita = 'C' and (us.id_stato in (3,5))
				and ( (filtri -> 'codice_preaccettazione' not ilike '' and concat(cp.prefix, cp.anno, cp.progres)::text ilike trim(filtri -> 'codice_preaccettazione')) or (filtri -> 'codice_preaccettazione' ilike '') ) 
				and ( ((filtri -> 'asl')::integer > 0 and ra.asl_rif = (filtri -> 'asl')::integer) or ((filtri -> 'asl')::integer = -1) ) 
				and ( (filtri -> 'osa' not ilike '' and trim(ra.ragione_sociale) ilike concat('%',filtri -> 'osa','%')) or (filtri -> 'osa' ilike '') )
				and ( (filtri -> 'partita_iva' not ilike '' and trim(ra.partita_iva::text) ilike filtri -> 'partita_iva') or (filtri -> 'partita_iva' ilike '') )
				and ( (filtri -> 'data_generazione' not ilike '' and to_char(cp.entered, 'DD-MM-YYYY') ilike trim(filtri -> 'data_generazione')) or (filtri -> 'data_generazione'  ilike '') )
				and ( (filtri -> 'utente' not ilike '' 
					and (concat(c.namelast,' ',c.namefirst) ilike concat('%',filtri -> 'utente','%')  
						or concat(c.namefirst,' ',c.namelast) ilike concat('%',filtri -> 'utente','%')) 
				       ) or (filtri -> 'utente' ilike ''))
				       
			) tab1  order by tab1.data_generazione::timestamp without time zone desc limit 100
		) tab2;
 
	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION preaccettazionesigla.search_preaccettazione(hstore)
  OWNER TO postgres;

  
