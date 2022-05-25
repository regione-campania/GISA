



--digemon.dbi_get_farmacie_norma_362(IN _data1 text,  IN _data2 text, IN _limit_record text )

CREATE OR REPLACE FUNCTION digemon.dbi_get_no_scia_norma_193(
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
			 , "id_norma" text ) AS
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
				from digemon.dbi_get_anagrafica_noscia_new() a;
		
	ELSE
		RETURN QUERY select * 
				FROM 
					digemon.dbi_get_anagrafica_noscia_new() 
				UNION ALL
			     select * 
				FROM 
					digemon.dbi_get_anagrafica_noscia_new(_data_1,_data_2) r
				WHERE 
					--r."data inserimento"::date between _data_1::date and _data_2::date  
					r."codice macroarea"='FVET' and r."codice aggregazione"='COID' and r."codice attivita"='CI';
	END IF;	

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.dbi_get_no_scia_norma_193(text,text, text)
  OWNER TO postgres;

select * from digemon.dbi_get_no_scia_norma_193('2020-01-01','2021-01-31')
select * from digemon.dbi_get_no_scia_norma_193()


