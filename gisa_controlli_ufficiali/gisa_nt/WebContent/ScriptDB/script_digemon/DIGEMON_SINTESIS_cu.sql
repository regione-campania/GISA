-- Function: public_functions.dbi_get_controlli_ufficiali_eseguitinew(timestamp without time zone, timestamp without time zone)

-- DROP FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguitinew(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguitinew(
    IN data_1 timestamp without time zone,
    IN data_2 timestamp without time zone)
  RETURNS TABLE(id_controllo_ufficiale integer, riferimento_id integer, riferimento_id_nome text, riferimento_id_nome_col text, riferimento_id_nome_tab text, id_asl integer, asl character varying, tipo_controllo character varying, tipo_ispezione_o_audit text, tipo_piano_monitoraggio text, id_piano integer, id_attivita integer, tipo_controllo_bpi text, tipo_controllo_haccp text, oggetto_del_controllo text, punteggio text, data_inizio_controllo date, anno_controllo text, data_chiusura_controllo date, aggiornata_cat_controllo text, categoria_rischio integer, prossimo_controllo timestamp with time zone, tipo_checklist text, linea_attivita_sottoposta_a_controllo text, unita_operativa text, id_struttura_uo integer, supervisionato_in_data timestamp without time zone, supervisionato_da character varying, supervisione_note text, congruo_supervisione text, note text, tipo_piano_monitoraggio_old text, codice_interno_univoco_uo integer, codice_interno_piano text, area_appartenenza_uo text, id_record_anagrafica integer) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR 
		id_controllo_ufficiale,
		riferimento_id,
		riferimento_id_nome,
		riferimento_id_nome_col,
		riferimento_id_nome_tab,
		id_asl,  
		asl , 
		tipo_controllo  , 
		tipo_ispezione_o_audit , 
		tipo_piano_monitoraggio ,
		id_piano, id_attivita,
		tipo_controllo_bpi , tipo_controllo_haccp , 
		oggetto_del_controllo , punteggio , 
		data_inizio_controllo , anno_controllo,  data_chiusura_controllo, aggiornata_cat_controllo, categoria_rischio, prossimo_controllo, 
		tipo_checklist, 
		linea_attivita_sottoposta_a_controllo,
		unita_operativa, id_struttura_uo, supervisionato_in_data, supervisionato_da, supervisione_note, congruo_supervisione,note,  
		tipo_piano_monitoraggio_old,
		codice_interno_univoco_uo,codice_interno_piano,
		area_appartenenza_uo,id_record_anagrafica
		in
		select distinct
v.id_controllo_ufficiale , v.riferimento_id , v.riferimento_id_nome , v.riferimento_id_nome_col , 
v.riferimento_id_nome_tab , v.id_asl , v.asl , v.tipo_controllo , v.tipo_ispezione_o_audit , v.tipo_piano_monitoraggio , v.id_piano , 
v.id_attivita , v.tipo_controllo_bpi , v.tipo_controllo_haccp , v.oggetto_del_controllo , v.punteggio , v.data_inizio_controllo , 

v.anno_controllo , v.data_chiusura_controllo , v.aggiornata_cat_controllo , v.categoria_rischio , v.prossimo_controllo, 
v.tipo_checklist , v.linea_attivita_sottoposta_a_controllo , v.unita_operativa , v.id_struttura_uo , v.supervisionato_in_data , 
v.supervisionato_da , v.supervisione_note , v.congruo_supervisione ,v.note , v.tipo_piano_monitoraggio_old , v.codice_interno_univoco_uo , 
v.codice_interno_piano , v.area_appartenenza_uo ,anag.id_record_anagrafica
		 from
		(
		select * from public_functions.dbi_get_controlli_ufficiali_eseguiti_vecchia_anagraficanew(data_1,data_2)
		UNION
		select * from public_functions.dbi_get_controlli_ufficiali_eseguiti_nuova_anagraficanew(data_1,data_2)
		UNION
		select * from public_functions.dbi_get_controlli_ufficiali_eseguiti_apicolturanew(data_1,data_2)
		UNION
		select * from public_functions.dbi_get_controlli_ufficiali_eseguiti_sintesisnew(data_1,data_2)
	)v
	left join  ricerche_anagrafiche_old_materializzata_temp anag on id_linea =id_linea_controllata and v.riferimento_id = anag.riferimento_id and anag.riferimento_id_nome_col = v.riferimento_id_nome_col
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_controlli_ufficiali_eseguitinew(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

  delete from ricerche_anagrafiche_old_materializzata;
  insert into ricerche_anagrafiche_old_materializzata  (select * from ricerca_anagrafiche);

select * from public_functions.dbi_initreportistica();