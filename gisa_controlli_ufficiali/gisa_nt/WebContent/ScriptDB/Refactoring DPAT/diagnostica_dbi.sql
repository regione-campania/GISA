CREATE OR REPLACE FUNCTION public.dpat_get_piani_attivita(IN anno_input integer default null, IN stato_input text default null, IN id_sezione_input integer default null, IN sezione_input text default null)
  RETURNS TABLE(id_piano_attivita integer, id_indicatore integer,cod_raggruppamento text,anno integer,descrizione text, ordinamento_piano integer,ordinamento_indicatore integer,
  data_scadenza timestamp without time zone,stato integer,codice_esame text,tipo_attivita text,codice_interno_piano text,codice_interno_attivita text,alias_piano text, 
  alias_attivita text, codice_alias_attivita text, codice_interno_indicatore text, alias_indicatore text, codice_alias_indicatore text,id_sezione integer,sezione text) AS
$BODY$
DECLARE
	r RECORD;
BEGIN
	FOR id_piano_attivita, id_indicatore,cod_raggruppamento,anno,descrizione, ordinamento_piano,ordinamento_indicatore,data_scadenza,stato,codice_esame,tipo_attivita,
	codice_interno_piano,codice_interno_attivita,alias_piano, alias_attivita, codice_alias_attivita , codice_interno_indicatore, alias_indicatore, codice_alias_indicatore,
	id_sezione,sezione
      in

select * from
(
      select p.id as id_piano_attivita, null as id_indicatore,p.cod_raggruppamento, p.anno,p.descrizione as descrizione, p.ordinamento as ordinamento_piano,null as ordinamento_indicatore,p.data_scadenza,p.stato,p.codice_esame,p.tipo_attivita,p.codice_interno_piano,p.codice_interno_attivita,
             p.alias_piano, p.alias_attivita, p.codice_alias_attivita , null as codice_interno_indicatore, null as alias_indicatore,null as codice_alias_indicatore,s.id as id_sezione,s.descrizione as sezione
      from dpat_piano_attivita_new p 
      left join dpat_sez_new s on s.id = p.id_sezione 
      where (p.anno = anno_input or anno_input is null) and 
            (p.stato::text = ANY (string_to_array(stato_input, ',')) or stato_input is null) and 
            (p.id_sezione = id_sezione_input or id_sezione_input is null) and 
            (s.descrizione ilike '%' || sezione_input || '%' or sezione_input is null) and 
            (p.data_scadenza is null or p.data_scadenza>current_timestamp)
            
         UNION
         
      select i.id_piano_attivita, i.id as id_indicatore, i.cod_raggruppamento, i.anno,i.descrizione,p.ordinamento as ordinamento_piano,i.ordinamento as ordinamento_indicatore,i.data_scadenza,i.stato,i.codice_esame,p.tipo_attivita,p.codice_interno_piano,p.codice_interno_attivita,
             p.alias_piano, p.alias_attivita, p.codice_alias_attivita,i.codice_interno_indicatore ,i.alias_indicatore,i.codice_alias_indicatore, s.id as id_sezione,s.descrizione as sezione  
      from dpat_indicatore_new  i 
      left join dpat_piano_attivita_new p on i.id_piano_attivita = p.id
      left join dpat_sez_new s on s.id = p.id_sezione 
      where (p.anno = anno_input or anno_input is null) and 
            (i.stato::text = ANY (string_to_array(stato_input, ',')) or stato_input is null) and 
            (p.id_sezione = id_sezione_input or id_sezione_input is null) and 
            (s.descrizione ilike '%' || sezione_input || '%' or sezione_input is null) and 
            (i.data_scadenza is null or i.data_scadenza>current_timestamp)
) a

 LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100
  ROWS 1000;
ALTER FUNCTION dpat_get_piani_attivita(integer  , text  ,   integer  , text  )
  OWNER TO postgres;




CREATE OR REPLACE FUNCTION public.dpat_get_strutture(IN asl_input integer default null, IN anno_input integer default null, IN stato_semplici_input text default null, IN stato_complesse_input text default null, IN semplici boolean default null, IN id_struttura_complessa_input integer default null, IN descrizione_struttura_complessa_input text default null, IN inserita_dopo_congelamento boolean default null, IN modificata_dopo_congelamento boolean default null)
  RETURNS TABLE(id_struttura_complessa integer,descrizione_struttura_complessa text,id_asl_struttura_complessa integer,codice_interno_fk_struttura_complessa text,
                data_scadenza_struttura_complessa timestamp without time zone,tipologia_struttura_complessa text,anno integer,stato_strutt_complessa integer,
                codice_interno_univoco_struttura_complessa text,data_congelamento_struttura_complessa timestamp without time zone,entered_struttura_complessa timestamp without time zone,modified_struttura_complessa timestamp without time zone , id_struttura_semplice integer,descrizione_struttura_semplice text, id_asl_struttura_semplice integer,
                codice_interno_fk_struttura_semplice text,data_scadenza_struttura_semplice timestamp without time zone,codice_interno_univoco_struttura_semplice text, stato_strutt_semplice integer,data_congelamento_struttura_semplice timestamp without time zone,entered_struttura_semplice timestamp without time zone,modified_struttura_semplice timestamp without time zone) AS
$BODY$
DECLARE
	r RECORD;
BEGIN
	FOR id_struttura_complessa,descrizione_struttura_complessa,id_asl_struttura_complessa,codice_interno_fk_struttura_complessa,data_scadenza_struttura_complessa,
            tipologia_struttura_complessa,anno,stato_strutt_complessa ,codice_interno_univoco_struttura_complessa,data_congelamento_struttura_complessa,entered_struttura_complessa,modified_struttura_complessa,id_struttura_semplice,descrizione_struttura_semplice, id_asl_struttura_semplice,
            codice_interno_fk_struttura_semplice,data_scadenza_struttura_semplice,codice_interno_univoco_struttura_semplice, stato_strutt_semplice,data_congelamento_struttura_semplice,entered_struttura_semplice,modified_struttura_semplice	
      in

SELECT strutt_complessa.id as id_struttura_complessa, 
       strutt_complessa.descrizione as descrizione_struttura_complessa, 
       strutt_complessa.id_asl as id_asl_struttura_complessa,
       strutt_complessa.codice_interno_fk as codice_interno_fk_struttura_complessa,
       strutt_complessa.data_scadenza as data_scadenza_struttura_complessa,
       tipooia.description as tipologia_struttura_complessa,
       strutt_complessa.anno,
       strutt_complessa.stato as stato_strutt_complessa ,
       strutt_complessa.codice_interno_univoco as codice_interno_univoco_struttura_complessa,
       strutt_complessa.data_congelamento as data_congelamento_struttura_complessa,
       strutt_complessa.entered as entered_struttura_complessa,
       strutt_complessa.modified as modified_struttura_complessa,
       strutt_semplice.id as id_struttura_semplice, 
       strutt_semplice.descrizione as descrizione_struttura_semplice, 
       strutt_semplice.id_asl as id_asl_struttura_semplice,
       strutt_semplice.codice_interno_fk as codice_interno_fk_struttura_semplice,
       strutt_semplice.data_scadenza as data_scadenza_struttura_semplice,
       strutt_semplice.codice_interno_univoco as codice_interno_univoco_struttura_semplice,
       strutt_semplice.stato as stato_strutt_semplice ,
       strutt_semplice.data_congelamento as data_congelamento_struttura_semplice,
       strutt_semplice.entered as entered_struttura_semplice,
       strutt_semplice.modified as modified_struttura_semplice					
FROM dpat_strutture_asl strutt_complessa
LEFT JOIN dpat_strutture_asl strutt_semplice on strutt_semplice.id_padre = strutt_complessa.id and 
                                           strutt_semplice.disabilitato = false and 
                                           (semplici or semplici is null) and
                                           (strutt_semplice.stato::text = ANY (string_to_array(stato_semplici_input, ',')) or stato_semplici_input is null) AND
                                           (strutt_semplice.entered > strutt_semplice.data_congelamento or strutt_semplice.entered is null or strutt_semplice.data_congelamento is null or inserita_dopo_congelamento is null or inserita_dopo_congelamento is false) AND
                                           (strutt_semplice.modified > strutt_semplice.data_congelamento  or strutt_semplice.modified is null or strutt_semplice.data_congelamento is null or modificata_dopo_congelamento is null or modificata_dopo_congelamento is false)
LEFT JOIN lookup_tipologia_nodo_oia tipooia ON strutt_complessa.tipologia_struttura = tipooia.code 
where strutt_complessa.disabilitato = false and
      strutt_complessa.tipologia_struttura in( 13,14) and
      (strutt_semplice.id > 0 or semplici is null or semplici is false) and
      (strutt_complessa.id_asl = asl_input or asl_input is null) and 
      (strutt_complessa.id = id_struttura_complessa_input or id_struttura_complessa_input is null) and 
      (strutt_complessa.descrizione ilike '%' || descrizione_struttura_complessa_input || '%' or descrizione_struttura_complessa_input is null) and 
      (strutt_complessa.entered > strutt_complessa.data_congelamento  or strutt_complessa.entered is null or strutt_complessa.data_congelamento is null or inserita_dopo_congelamento is null or inserita_dopo_congelamento is false) AND
      (strutt_complessa.modified > strutt_complessa.data_congelamento  or strutt_complessa.modified is null or strutt_complessa.data_congelamento is null or modificata_dopo_congelamento is null or modificata_dopo_congelamento is false) AND
      (strutt_complessa.stato::text = ANY (string_to_array(stato_complesse_input, ',')) or stato_complesse_input is null) and 
      strutt_complessa.id_strumento_calcolo in (select id 
			                          from dpat_strumento_calcolo 
			                          where (id_asl = asl_input or asl_input is null) and 
			                                (strutt_complessa.anno = anno_input or anno_input is null) 
			                          ) 
order by strutt_complessa.anno, strutt_complessa.id_asl,strutt_complessa.id


        LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE 
  COST 100
  ROWS 1000;
ALTER FUNCTION public.dpat_get_strutture(integer,integer,text,text,boolean,integer,text,boolean,boolean)
  OWNER TO postgres;




--DROP FUNCTION public.dpat_get_nominativi(integer, integer, text, integer, text, integer, text);
CREATE OR REPLACE FUNCTION public.dpat_get_nominativi(
    IN asl_input integer DEFAULT NULL::integer,
    IN anno_input integer DEFAULT NULL::integer,
    IN stato_input text DEFAULT NULL::text,
    IN id_struttura_complessa_input integer DEFAULT NULL::integer,
    IN descrizione_struttura_complessa_input text DEFAULT NULL::text,
    IN id_struttura_semplice_input integer DEFAULT NULL::integer,
    IN descrizione_struttura_semplice_input text DEFAULT NULL::text)
  RETURNS TABLE(id_nominativo integer, id_anagrafica_nominativo integer, nominativo text, codice_fiscale text, qualifica text, data_scadenza_nominativo timestamp without time zone, id_struttura_semplice integer, desc_strutt_semplice text, stato_strutt_semplice integer, data_scadenza_strutt_semplice timestamp without time zone, id_strutt_complessa integer, desc_strutt_complessa text, data_scadenza_strutt_complessa timestamp without time zone, stato_strutt_complessa integer, id_asl integer, anno integer) AS
$BODY$
DECLARE
    r RECORD;
BEGIN
    FOR id_nominativo, id_anagrafica_nominativo, nominativo,  codice_fiscale, qualifica, data_scadenza_nominativo,id_struttura_semplice, desc_strutt_semplice,
       stato_strutt_semplice, data_scadenza_strutt_semplice, id_strutt_complessa, desc_strutt_complessa,data_scadenza_strutt_complessa, stato_strutt_complessa, 
       id_asl, anno   
      in

select n.id as id_nominativo, n.id_anagrafica_nominativo, concat(c.namefirst, ' ', c.namelast) as nominativo,  c.codice_fiscale, lq.description as qualifica, 
       n.data_scadenza as data_scadenza_nominativo,n.id_dpat_strumento_calcolo_strutture as id_struttura_semplice, strutt_semplice.descrizione as desc_strutt_semplice,
       strutt_semplice.stato as stato_strutt_semplice, strutt_semplice.data_scadenza as data_scadenza_strutt_semplice, strutt_complessa.id as id_strutt_complessa,
       strutt_complessa.descrizione as desc_strutt_complessa, strutt_complessa.data_scadenza as data_scadenza_strutt_complessa, strutt_complessa.stato as stato_strutt_complessa, 
       strutt_complessa.id_asl, strutt_complessa.anno
from dpat_strumento_calcolo_nominativi n
join dpat_strutture_asl strutt_semplice on strutt_semplice.id = n.id_struttura and strutt_semplice.disabilitato = false
join dpat_strutture_asl strutt_complessa on strutt_complessa.id = strutt_semplice.id_padre and strutt_complessa.disabilitato = false
join lookup_qualifiche lq on lq.code = id_lookup_qualifica
join access users on users.user_id =  n.id_anagrafica_nominativo
join contact c on c.contact_id = users.contact_id
where n.trashed_Date is null and
      (strutt_semplice.stato::text = ANY (string_to_array(stato_input, ',')) or stato_input is null) and
      (strutt_complessa.id = id_struttura_complessa_input or id_struttura_complessa_input is null) and
      (strutt_complessa.descrizione ilike '%' || descrizione_struttura_complessa_input || '%' or descrizione_struttura_complessa_input is null) and
      (strutt_semplice.id = id_struttura_semplice_input or id_struttura_semplice_input is null) and
      (strutt_semplice.descrizione ilike '%' || descrizione_struttura_semplice_input || '%' or descrizione_struttura_semplice_input is null) and
      strutt_complessa.id_strumento_calcolo in (select id
                        from dpat_strumento_calcolo
                        where (strutt_complessa.id_asl = asl_input or asl_input is null) and
                              (strutt_complessa.anno = anno_input or anno_input is null)
                        )
order by lq.livello_qualifiche_dpat


        LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.dpat_get_nominativi(integer, integer, text, integer, text, integer, text)
  OWNER TO postgres;