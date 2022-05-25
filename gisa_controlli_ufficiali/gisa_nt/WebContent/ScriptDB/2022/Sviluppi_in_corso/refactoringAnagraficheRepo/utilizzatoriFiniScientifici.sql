


CREATE OR REPLACE FUNCTION digemon.dbi_get_utilizzatori_animali_fini_scientifici(
    IN _data_1 text default null::text,
    IN _data_2 text  default null::text,
    IN _limit_record text DEFAULT null::text)
  RETURNS TABLE("progressivo" text
			, "ragione sociale" text
			 , "partita iva" text
			 , "codice fiscale" text
			 , "tipo impresa" text
			 , "domicilio digitale" text
			 , "telefono impresa" text
			 , "nome rappresentante legale" text
			 , "cognome rappresentante legale" text
			 , "sesso rappresentante legale" text
			 , "nazione nascita rappresentante legale" text
			 , "comune nascita estero rappresentante legale" text
			 , "data nascita rappresentante legale" text
			 , "codice fiscale rappresentante legale" text
			 , "email rappresentante legale" text
			 , "telefono rappresentante legale" text
			 , "nazione residenza rappresentante legale" text
			 , "indirizzo residenza rappresentante legale " text
			 , "comune residenza rappresentante rapp legale" text
			 , "indirizzo sede legale " text
			 , "nazione sede legale" text
			 , "comune estero sede legale" text
			 , "latitudine sede legale " text
			 , "longitudine sede legale" text
			 , "nazione stabilimento" text
			 , "indirizzo stabilimento " text
			 , "coordinate stabilimento" text
			 , "numero registrazione stabilimento" text
			 , "asl" text
			 , "note stabilimento" text
			 , "tipo attivita descrizione" text
			 , "data inizio attivita" text
			 , "norma" text
			 , "codice macroarea" text
			 , "codice aggregazione" text
			 , "codice attivita" text
			 , "macroarea" text
			 , "aggregazione" text
			 , "attivita" text
			 , "data inserimento" text
			 , "riferimento_id" text
			 , "riferimento_nome_tab" text
			 , "id_linea" text
			 , "id_norma" text 
			 , "numero autorizzazione" text
			 , "nome medico veterinario designato" text
			 , "nome responsabile benessere animale" text
			 , "specie animali" text) AS
$BODY$
DECLARE
BEGIN
	IF ($1 IS NULL AND $2 IS NULL AND $3 IS NULL) THEN

		return QUERY select a."progressivo" 
			, a."ragione sociale" 
			 , a."partita iva" 
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
			 , a."indirizzo residenza rappresentante legale " 
			 , a."comune residenza rappresentante rapp legale" 
			 , a."indirizzo sede legale " 
			 , a."nazione sede legale" 
			 , a."comune estero sede legale" 
			 , a."latitudine sede legale " 
			 , a."longitudine sede legale" 
			 , a."nazione stabilimento" 
			 , a."indirizzo stabilimento " 
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
			 , a."data inserimento" 
			 , a."riferimento_id" 
			 , a."riferimento_nome_tab" 
			 , a."id_linea" 
			 , a."id_norma"
			 , 'numero autorizzazione|text|dig'
			 , 'nome medico veterinario designato|text|dig'
			 , 'nome responsabile benessere|text|dig'
			 , 'specie animali|text|dig'

			from digemon.dbi_get_anagrafica_noscia_new() a;
		
	ELSE
		RETURN QUERY select * 
				    , 'autorizzazione utilizzo autorizzazione|text|dig'
				    , 'medico veterinario designato nome|text|dig'
				    , 'responsabile benessere nome|text|dig'
				    , 'specie animali|text|dig' 

				FROM 
					digemon.dbi_get_anagrafica_noscia_new() 

				UNION ALL

			     select r.*
			            , e.autorizzazione_utilizzo_autorizzazione
			            , e.medico_veterinario_designato_nome
			            , e.responsabile_benessere_animale_nome
			            , e.specie_animali_specie 

				FROM 
					digemon.dbi_get_anagrafica_noscia_new(_data_1,_data_2) r
				-- campi extra per UTENSCE
					left join (
						       SELECT *
						       FROM crosstab(
							  'select alt_id, nome_campo, valore_campo
							   from get_valori_anagrafica_estesi() 
							   order by 1,2')
							AS ct(alt_id integer, autorizzazione_utilizzo_autorizzazione text, medico_veterinario_designato_nome text, responsabile_benessere_animale_nome text, specie_animali_specie text)
						   ) e
						  on r.riferimento_id::int = (select return_id from gestione_id_alternativo(e.alt_id, -1)) and r.riferimento_nome_tab = 'opu_stabilimento'
						   
				WHERE 
					r."codice macroarea"='UTANSCE' and r."codice aggregazione"='UTANSCE' and r."codice attivita"='UTANSCE'; 

	END IF;	
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_utilizzatori_animali_fini_scientifici(text,text, text)
  OWNER TO postgres;


--select * from digemon.dbi_get_utilizzatori_animali_fini_scientifici('2020-01-01','2021-01-31') limit 2
--select * from digemon.dbi_get_utilizzatori_animali_fini_scientifici()
--select * from get_valori_anagrafica_estesi(20344973)
