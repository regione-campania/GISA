select b.* 
				  --  , 'autorizzazione utilizzo autorizzazione|text|dig'
				   -- , 'medico veterinario designato nome|text|dig'
				   -- , 'responsabile benessere nome|text|dig'
				   -- , 'specie animali|text|dig' 
				   , 'autorizzazione|text|dig'
			           , 'desc_autorizzazione|text|dig'
			           , 'data_autorizzazione|text|dig'
			           , 'superficie|text|dig'
			           , 'referente_nome|text|dig'
			           , 'referente_cognome|text|dig'
			           , 'referente_cf|text|dig'

				FROM 
					digemon.dbi_get_anagrafica_scia_new() b

				UNION ALL

			      select lpad((row_number() OVER (ORDER BY concat(a."asl", a."indirizzo stabilimento", a."data inserimento",current_timestamp) desc))::text,6,'0')  as progressivo
					,  a."nome/ditta/ragione sociale/denominazione sociale" 
					,  a."partita iva" 
					 , a."codice fiscale" 
					 , a."tipo impresa" 
					 , a."domicilio digitale" 
					 , a."telefono impresa" 
					 , a."nome rappresentante legale" 
					 , a."cognome rappresentante legale" 
					 , a."sesso rappresentante legale" 
					 , a."nazione nascita rappresentante legale" 
					 , a."comune nascita estero rappresentante legale" 
					 , a."data nascita rappresentante legale" 
					 , a."codice fiscale rappresentante legale" 
					 , a."email rappresentante legale" 
					 , a."telefono rappresentante legale" 
					 , a."nazione residenza rappresentante legale" 
					 , a."indirizzo residenza rappresentante legale" 
					 , a."comune residenza rappresentante rapp legale" 
					 , a."indirizzo sede legale" 
					 , a."nazione sede legale" 
					 , a."comune estero sede legale" 
					 , a."latitudine sede legale" 
					 , a."longitudine sede legale" 
					 , a."nazione stabilimento" 
					 , a."indirizzo stabilimento" 
					 , a."coordinate stabilimento" 
					 , a."numero registrazione stabilimento" 
					 , a."asl" 
					 , a."note stabilimento" 
					 , a."tipo attivita descrizione" 
					 , a."data inizio attivita" 
					 , a."norma" 
					 , a."codice macroarea" 
					 , a."codice aggregazione" 
					 , a."codice attivita" 
					 , a."macroarea" 
					 , a."aggregazione" 
					 , a."attivita" 
					 , a."stato"
					 , a."data inserimento" 
					 , a."riferimento_id" 
					 , a."riferimento_nome_tab" 
					 , a."id_linea" 
					 , a."id_norma"
					, a.autorizzazione
					, a.desc_autorizzazione 
					, a.data_autorizzazione
					, a.superficie
					, a.referente_nome 
					, a.referente_cognome
					, a.referente_cf

				FROM 
				( select b.*
				, e.autorizzazione
					, e.desc_autorizzazione 
					, e.data_autorizzazione
					, e.superficie
					, e.referente_nome 
					, e.referente_cognome
					, e.referente_cf

					from
						digemon.dbi_get_anagrafica_scia_new('2020-01-01','2021-01-31') b
				--campi extra per CANILI
					left join (
						       SELECT * FROM crosstab(
								'select id_opu_rel_stab_linea, f.nome_campo, v.valore_campo from 
								linee_mobili_html_fields f join linee_mobili_fields_value v on v.id_linee_mobili_html_fields= f.id and v.enabled
								order by 1,2')
								AS ct(id_opu_rel_stab_linea integer, autorizzazione text, desc_autorizzazione text, data_autorizzazione text, superficie text, referente_nome text, referente_cognome text, referente_cf text)
						  ) e
						  on e.id_opu_rel_stab_linea = (select id from opu_relazione_stabilimento_linee_produttive where enabled and id_stabilimento= b.riferimento_id::int)
						   
				WHERE 
					--r."codice macroarea"='UTANSCE' and r."codice aggregazione"='UTANSCE' and r."codice attivita"='UTANSCE'; 
					( b."codice macroarea" in ('IUV') and b."codice aggregazione" in ('CAN')
					or   b."codice macroarea" in ('IUV') and b."codice aggregazione" in ('ADCA')
					or    b."codice macroarea" in ('VET') and b."codice aggregazione" in ('OSPV','CLIV','AMBV') and b."codice attivita" in ('PU') 
					)

				union 
				-- vecchia anagrafica CANILI
				select *,  'autorizzazione|text|dig'
			           , 'desc_autorizzazione|text|dig'
			           , 'data_autorizzazione|text|dig'
			           , 'superficie|text|dig'
			           , 'referente_nome|text|dig'
			           , 'referente_cognome|text|dig'
			           , 'referente_cf|text|dig'

					FROM digemon.dbi_get_canili_org('2020-01-01','2021-01-31')
				) a; --nuova anagrafica