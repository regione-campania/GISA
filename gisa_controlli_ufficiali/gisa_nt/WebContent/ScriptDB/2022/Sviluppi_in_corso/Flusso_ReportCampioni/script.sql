-- DROP FUNCTION digemon.get_campioni(integer, timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION digemon.get_campioni(
    IN _asl integer,
    IN _data_1 timestamp without time zone,
    IN _data_2 timestamp without time zone)
  RETURNS TABLE(id_asl integer, asl character varying, motivazione_campione character varying, id_piano integer, id_attivita integer, id_campione integer, data_prelievo timestamp without time zone, 
  identificativo_campione character varying, prelevatore_1_a4 text, prelevatore_2_a4 text, prelevatore_3_a4 text, strategia_campionamento_a1 text, capitoli_piani_a3 text, specie_alimento_b6 character varying, 
  metodo_produzione_b7 text, anno_campione text, data_chiusura_campione timestamp without time zone, id_controllo_ufficiale character varying, esito text, punteggio_campione integer, responsabilita_positiva character varying,
   data_esito_analita timestamp without time zone, esito_motivazione_respingimento text, note_esito_campione text, codice_accettazione text, num_verbale character varying, barcode character varying, analita_lev_1 text, 
   analita_lev_2 text, analita_lev_3 text, analita_lev_4 text, matrice_lev_1 text, matrice_lev_2 text, matrice_lev_3 text, note_campione text, 
   anno_controllo text, codice_interno_piano text, descrizione_esito_esame text, motivazione_non_conformita text, rendicontabile boolean, codice_preaccettazione text) AS
$BODY$


DECLARE
	r RECORD;

BEGIN
FOR     id_asl, asl , motivazione_campione, id_piano, id_attivita, id_campione , data_prelievo ,  
                identificativo_campione, prelevatore_1_a4 , prelevatore_2_a4, prelevatore_3_a4 ,
                strategia_campionamento_a1, capitoli_piani_a3 , specie_alimento_b6,
                metodo_produzione_b7, 
                anno_campione, data_chiusura_campione, id_controllo_ufficiale, esito , 
                punteggio_campione , responsabilita_positiva  , data_esito_analita , 
                esito_motivazione_respingimento , note_esito_campione , codice_accettazione , num_verbale  , 
                barcode , 
                analita_lev_1 ,analita_lev_2 ,analita_lev_3 ,analita_lev_4 , matrice_lev_1 , matrice_lev_2 , matrice_lev_3  , note_campione , 
                anno_controllo ,codice_interno_piano,descrizione_esito_esame, motivazione_non_conformita, rendicontabile,
                codice_preaccettazione
in
 SELECT DISTINCT asl.code AS id_asl,
	asl.description AS asl,
	coalesce(lpm.description,lti.description) as motivazione_campione,
	lpm.code as id_piano,
	lti.id_indicatore as id_attivita, -- se si va sul code id_attivita risulta valorizzato anche quando id_piano > 0 in quanto assume il valore 89 che corrisponde a "PIANO DI MONITORAGGIO"
        c.ticketid AS id_campione,
	c.assigned_date AS data_prelievo,
	c.identificativo AS identificativo_campione,
	cu.componente_nucleo AS prelevatore_1_a4,
	cu.componente_nucleo_due AS prelevatore_2_a4,
	cu.componente_nucleo_tre AS prelevatore_3_a4,
	CASE
            WHEN lpm.description::text ~~* '%MON%'::text THEN '003'::text
            WHEN lpm.description::text ~~* '%SORV%'::text THEN '007'::text
            WHEN lpm.description::text ~~* '%EXTRAPIANO%'::text THEN '005'::text
            WHEN lti.description::text ~~* '%SOSPETT%'::text THEN '001'::text
            ELSE 'N.D.'::text
        END AS strategia_campionamento_a1,
        CASE
            WHEN lpm.description::text ~~* '%BSE%'::text THEN '1'::text
            WHEN lpm.description::text ~~* '%SOSTANZE FARMACOLOGICHE%CARRY OVER)'::text THEN '8'::text
            WHEN lpm.description::text ~~* '%SOSTANZE FARMACOLOGICHE)'::text OR lpm.description::text ~~* '%MONIT. OLIGOELEMENTI%'::text THEN '2'::text
            WHEN lpm.description::text ~~* '%contaminanti%'::text THEN '3'::text
            WHEN lpm.description::text ~~* '%DIOSSINE%'::text THEN '4'::text
            WHEN lpm.description::text ~~* '%MICOTOSSINE%'::text THEN '5'::text
            WHEN lpm.description::text ~~* '%SALMONELLA%'::text THEN '6'::text
            WHEN lpm.description::text ~~* '%OGM%'::text THEN '7'::text
            ELSE '9'::text
        END AS capitoli_piani_a3,
	string_agg(pnaa_specie.description, ',') AS specie_alimento_b6,
        string_agg(CASE
            WHEN pnaa_metodo.valore_campione IS NOT NULL THEN pnaa_metodo.valore_campione
            ELSE c.check_circuito_ogm
        END, ',') AS metodo_produzione_b7,
        CASE
            WHEN c.assigned_date IS NULL THEN 'N.D.'::text
            ELSE date_part('year'::text, c.assigned_date)::text
        END AS anno_campione,
	to_date(c.closed::text, 'yyyy/mm/dd'::text) AS data_chiusura_campione,
	c.id_controllo_ufficiale,
        CASE
            WHEN c.sanzioni_amministrative > 0 AND ac.esito_id IS NULL THEN esito.description::text
            WHEN ac.esito_id > 0 THEN esitonew.description::text
            WHEN c.tipologia = 2 AND c.sanzioni_amministrative < 0 AND ac.esito_id < 0 THEN 'Da Attendere'::text
            ELSE 'N.D'::text
        END AS esito,
	COALESCE(ac.esito_punteggio, c.punteggio) AS punteggio_campione,
	COALESCE(resp_new.description, responsabilita.description::text::character varying) AS responsabilita_positivita,
	COALESCE(ac.esito_data, c.est_resolution_date) AS data_esito_analita,
	ac.esito_motivazione_respingimento,
	c.solution AS note_esito_campione,
	c.cause AS codice_accettazione,
	c.location AS num_verbale,
	b.barcode AS barcode_scheda,
	split_part(ac.cammino, '->'::text, 1) AS analita_lev_1,
        CASE
            WHEN split_part(ac.cammino, '->'::text, 2) IS NOT NULL AND split_part(ac.cammino, '->'::text, 2) <> ''::text THEN split_part(ac.cammino, '->'::text, 2)
            ELSE 'N.D'::text
        END AS analita_lev_2,
        CASE
            WHEN split_part(ac.cammino, '->'::text, 3) IS NOT NULL AND split_part(ac.cammino, '->'::text, 3) <> ''::text THEN split_part(ac.cammino, '->'::text, 3)
            ELSE 'N.D'::text
        END AS analita_lev_3,
        CASE
            WHEN split_part(ac.cammino, '->'::text, 4) IS NOT NULL AND split_part(ac.cammino, '->'::text, 4) <> ''::text THEN split_part(ac.cammino, '->'::text, 4)
            ELSE 'N.D'::text
        END AS analita_lev_4,
        CASE
            WHEN split_part(mc.cammino, '->'::text, 1) IS NOT NULL AND split_part(mc.cammino, '->'::text, 1) <> ''::text THEN split_part(mc.cammino, '->'::text, 1)
            ELSE 'N.D'::text
        END AS matrice_lev_1,
        CASE
            WHEN split_part(mc.cammino, '->'::text, 2) IS NOT NULL AND split_part(mc.cammino, '->'::text, 2) <> ''::text THEN split_part(mc.cammino, '->'::text, 2)
            ELSE 'N.D'::text
        END AS matrice_lev_2,
        CASE
            WHEN split_part(mc.cammino, '->'::text, 3) IS NOT NULL AND split_part(mc.cammino, '->'::text, 3) <> ''::text THEN split_part(mc.cammino, '->'::text, 3)
            ELSE 'N.D'::text
        END AS matrice_lev_3,
    c.problem AS note_campione,
        CASE
            WHEN cu.assigned_date IS NULL THEN 'N.D.'::text
            ELSE date_part('year'::text, cu.assigned_date)::text
        END AS anno_controllo,
    COALESCE(lpm.codice, lti.codice) AS codice_interno_piano_attivita,
    regexp_replace(c.note_esito_esame, E'<[^>]+>', '-', 'gi') as descrizione_esito_esame,
    string_agg(cnc.description, ';'::text) AS motivazione_non_conformita,
    CASE
         when  ac.esito_id in (4,5) then false
           else true
    END AS rendicontabile,
    (select * from preaccettazione.get_codice_preaccettazione_da_campione(c.ticketid::character varying)) as codice_preaccettazione 
   FROM ticket c
     LEFT JOIN ticket cu ON cu.ticketid::text = c.id_controllo_ufficiale::text AND cu.tipologia = 3
     LEFT JOIN lookup_site_id asl ON c.site_id = asl.code
     LEFT JOIN lookup_esito_campione esito ON c.sanzioni_amministrative = esito.code
     LEFT JOIN lookup_responsabilita_positivita responsabilita ON responsabilita.code = c.responsabilita_positivita
     LEFT JOIN lookup_piano_monitoraggio lpm ON lpm.code = c.motivazione_piano_campione
     LEFT JOIN lookup_tipo_ispezione lti ON lti.code = c.motivazione_campione
     --pnaa??? ha ancora senso?
     
  LEFT JOIN (select cfv.id_campione, lsp.description from 
     campioni_fields_value cfv 
     LEFT JOIN campioni_html_fields chf ON chf.id = cfv.id_campioni_html_fields
     LEFT JOIN lookup_specie_pnaa lsp ON lsp.code::text = cfv.valore_campione 
     where lower(nome_campo) in ('b6')) pnaa_specie on pnaa_specie.id_campione = c.ticketid

  LEFT JOIN (select cfv.id_campione, cfv.valore_campione from 
     campioni_fields_value cfv 
     LEFT JOIN campioni_html_fields chf ON chf.id = cfv.id_campioni_html_fields
     where lower(nome_campo) in ('b7')) pnaa_metodo on pnaa_metodo.id_campione = c.ticketid
   ----
     LEFT JOIN analiti_campioni ac ON c.ticketid = ac.id_campione
     LEFT JOIN matrici_campioni mc ON mc.id_campione = c.ticketid
     LEFT JOIN lookup_responsabilita_positivita resp_new ON resp_new.code = ac.esito_responsabilita_positivita
     LEFT JOIN lookup_esito_campione esitonew ON ac.esito_id = esitonew.code
     LEFT JOIN barcode_osa b ON b.id_campione::text = c.ticketid::text
     LEFT JOIN analiti_campioni_nonconformita_view acnv ON acnv.id_campione = c.ticketid
     LEFT JOIN acquedirete_campioni_nonconformita cnc ON cnc.id = acnv.id_acquedirete_campioni_nonconformita
     WHERE c.tipologia = 2 
     AND c.trashed_date IS NULL AND cu.trashed_date IS NULL AND c.assigned_date IS NOT NULL AND c.location IS NOT NULL AND c.location::text <> ''::text
     and ( _asl is null or cu.site_id = _asl )
     and c.assigned_date  between coalesce (_data_1,'1900-01-01') and coalesce (_data_2,now()) 
     group by asl.code, 
     asl.description, coalesce(lpm.description,lti.description) ,lpm.code, lti.id_indicatore,
     c.ticketid ,
    c.assigned_date ,
    c.identificativo,
    cu.componente_nucleo ,
    cu.componente_nucleo_due ,
    cu.componente_nucleo_tre ,
    strategia_campionamento_a1, capitoli_piani_a3 ,  --new
        CASE
            WHEN c.assigned_date IS NULL THEN 'N.D.'::text
            ELSE date_part('year'::text, c.assigned_date)::text
        END,
    to_date(c.closed::text, 'yyyy/mm/dd'::text) ,
    c.id_controllo_ufficiale,
        CASE
            WHEN c.sanzioni_amministrative > 0 AND ac.esito_id IS NULL THEN esito.description::text
            WHEN ac.esito_id > 0 THEN esitonew.description::text
            WHEN c.tipologia = 2 AND c.sanzioni_amministrative < 0 AND ac.esito_id < 0 THEN 'Da Attendere'::text
            ELSE 'N.D'::text
        END,
    COALESCE(ac.esito_punteggio, c.punteggio),
    COALESCE(resp_new.description, responsabilita.description::text::character varying),
    COALESCE(ac.esito_data, c.est_resolution_date),
    ac.esito_motivazione_respingimento,
    c.solution ,
    c.cause ,
    c.location,
    b.barcode,
    split_part(ac.cammino, '->'::text, 1),
        CASE
            WHEN split_part(ac.cammino, '->'::text, 2) IS NOT NULL AND split_part(ac.cammino, '->'::text, 2) <> ''::text THEN split_part(ac.cammino, '->'::text, 2)
            ELSE 'N.D'::text
        END,
        CASE
            WHEN split_part(ac.cammino, '->'::text, 3) IS NOT NULL AND split_part(ac.cammino, '->'::text, 3) <> ''::text THEN split_part(ac.cammino, '->'::text, 3)
            ELSE 'N.D'::text
        END,
        CASE
            WHEN split_part(ac.cammino, '->'::text, 4) IS NOT NULL AND split_part(ac.cammino, '->'::text, 4) <> ''::text THEN split_part(ac.cammino, '->'::text, 4)
            ELSE 'N.D'::text
        END,
        CASE
            WHEN split_part(mc.cammino, '->'::text, 1) IS NOT NULL AND split_part(mc.cammino, '->'::text, 1) <> ''::text THEN split_part(mc.cammino, '->'::text, 1)
            ELSE 'N.D'::text
        END,
        CASE
            WHEN split_part(mc.cammino, '->'::text, 2) IS NOT NULL AND split_part(mc.cammino, '->'::text, 2) <> ''::text THEN split_part(mc.cammino, '->'::text, 2)
            ELSE 'N.D'::text
        END ,
        CASE
            WHEN split_part(mc.cammino, '->'::text, 3) IS NOT NULL AND split_part(mc.cammino, '->'::text, 3) <> ''::text THEN split_part(mc.cammino, '->'::text, 3)
            ELSE 'N.D'::text
        END,
    c.problem,
        CASE
            WHEN cu.assigned_date IS NULL THEN 'N.D.'::text
            ELSE date_part('year'::text, cu.assigned_date)::text
        END,
    COALESCE(lpm.codice, lti.codice),
    c.note_esito_esame, 
    CASE
         when  ac.esito_id in (4,5) then false
           else true
    END 
      LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
  
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.get_campioni(integer, timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;

--select * from digemon.get_campioni(null,'2021-01-01','2021-12-31')