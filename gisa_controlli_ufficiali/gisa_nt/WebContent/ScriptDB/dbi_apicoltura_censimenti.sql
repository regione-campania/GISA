-- Function: public_functions.dbi_get_apicoltura_censimenti()

-- DROP FUNCTION public_functions.dbi_get_apicoltura_censimenti();

CREATE OR REPLACE FUNCTION public_functions.dbi_get_apicoltura_censimenti()
  RETURNS TABLE(codice_azienda text, codice_apiario text, data_censimento timestamp without time zone, progressivo integer, numero_sciami integer, numero_alveari integer, data_cessazione_apiario timestamp without time zone) AS
$BODY$
DECLARE
BEGIN
FOR codice_azienda , codice_apiario , data_censimento, progressivo, numero_sciami, numero_alveari, data_cessazione_apiario
in 
    select distinct 
			apiari_1.codice_azienda,
			apiari_1.codice_apiario, 
			case when apiari_1.data_assegnazione_censimento='1800-01-01' then null else apiari_1.data_assegnazione_censimento end data_assegnazione_censimento,
			apiari_1.progressivo,
			apiari_1.num_sciami,
			apiari_1.num_alveari,
			apiari_1.data_cessazione_apiario 
		from 
			(SELECT 
				imp.codice_azienda,
				a.id as codice_apiario, 
				coalesce(c.data_assegnazione_censimento,'1800-01-01') data_assegnazione_censimento,
				a.num_sciami,
				a.num_alveari,
				a.progressivo,
				a.data_cessazione as data_cessazione_apiario
			FROM 
				apicoltura_apiari a
				left join apicoltura_imprese imp on imp.id=a.id_operatore
				join apicoltura_apiari_variazioni_censimenti c on c.id_apicoltura_apiario=a.id
			where 
				a.trashed_date is null and imp.trashed_date is null and imp.codice_azienda is not null and trim(imp.codice_azienda)!='') as apiari_1
		where data_assegnazione_censimento in
						(
							select max(data_assegnazione_censimento) 
							from ( SELECT 
									imp.codice_azienda,
									a.id codice_apiario, 
									coalesce(c.data_assegnazione_censimento,'1800-01-01') data_assegnazione_censimento,
									a.progressivo,
									a.num_sciami,
									a.num_alveari,
									a.data_cessazione
								FROM 
									apicoltura_apiari a 
									left join apicoltura_imprese imp on imp.id=a.id_operatore
									join apicoltura_apiari_variazioni_censimenti c on c.id_apicoltura_apiario=a.id
								where 
									imp.codice_azienda is not null and trim(imp.codice_azienda)!=''
							     ) apiari_2
						where 
							apiari_2.codice_azienda=apiari_1.codice_azienda and 
							apiari_2.codice_apiario=apiari_1.codice_apiario 
						group by apiari_1.codice_azienda,
							 apiari_1.codice_apiario, 
							  apiari_1.num_sciami,
							 apiari_1.num_alveari,
							 apiari_1.progressivo,
							 apiari_1.data_cessazione_apiario
						)
		
			order by 1,2,3
    
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_apicoltura_censimenti()
  OWNER TO postgres;




