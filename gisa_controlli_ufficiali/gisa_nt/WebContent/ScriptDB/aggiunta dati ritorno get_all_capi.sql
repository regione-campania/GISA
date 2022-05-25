drop view report_vista_capi;

CREATE OR REPLACE VIEW public.report_vista_capi AS 
 SELECT o.name AS descrizione_macello,
    o.numaut AS codice_macello,
    o.site_id AS id_asl,
    asl.description AS asl,
    c.cd_matricola AS matricola,
    c.cd_codice_azienda_provenienza AS codice_azienda_provenienza,
    specie_lookup.description AS specie,
    c.vpm_data AS data_macellazione,
    c.cd_veterinario_1,
    c.cd_veterinario_2,
    c.cd_veterinario_3,
    c.cd_data_nascita,
    case when c.cd_specie = 5 then cat_buf.description
    when c.cd_specie = 1 then cat_bov.description
    else '' end as categoria
   FROM m_capi c
    join organization o on c.id_macello = o.org_id
    join lookup_site_id asl on o.site_id = asl.code 
    join m_lookup_specie specie_lookup on specie_lookup.code = c.cd_specie
    left join m_lookup_specie_categorie_bufaline cat_buf on cat_buf.code = c.cd_categoria_bufalina
    left join m_lookup_specie_categorie_bovine  cat_bov on cat_bov.code = c.cd_categoria_bovina
  WHERE c.vpm_data IS NOT NULL  AND c.trashed_date IS NULL AND o.trashed_date IS NULL      
UNION
 SELECT o.ragione_sociale AS descrizione_macello,
    s.approval_number AS codice_macello,
    s.id_asl,
    asl.description AS asl,
    c.cd_matricola AS matricola,
    c.cd_codice_azienda_provenienza AS codice_azienda_provenienza,
    specie_lookup.description AS specie,
    c.vpm_data AS data_macellazione,
    c.cd_veterinario_1,
    c.cd_veterinario_2,
    c.cd_veterinario_3,
    c.cd_data_nascita,
    case when c.cd_specie = 5 then cat_buf.description
    when c.cd_specie = 1 then cat_bov.description
    else '' end as categoria
   FROM m_capi c
    join sintesis_stabilimento s on c.id_macello = s.alt_id
    join sintesis_operatore o on s.id_operatore = o.id
    join lookup_site_id asl on  s.id_asl = asl.code
    join m_lookup_specie specie_lookup on specie_lookup.code = c.cd_specie
    left join m_lookup_specie_categorie_bufaline cat_buf on cat_buf.code = c.cd_categoria_bufalina
    left join m_lookup_specie_categorie_bovine  cat_bov on cat_bov.code = c.cd_categoria_bovina
  WHERE c.vpm_data IS NOT NULL AND c.trashed_date IS NULL AND o.trashed_date IS NULL;
ALTER TABLE public.report_vista_capi
  OWNER TO postgres;
GRANT ALL ON TABLE public.report_vista_capi TO postgres;
GRANT SELECT ON TABLE public.report_vista_capi TO report;


DROP FUNCTION public_functions.dbi_get_allcapi(timestamp without time zone, timestamp without time zone);

CREATE OR REPLACE FUNCTION public_functions.dbi_get_allcapi(
    IN data_1 timestamp without time zone,
    IN data_2 timestamp without time zone)
  RETURNS TABLE(descrizione_macello character varying, codice_macello character varying, id_asl integer, asl character varying, matricola character varying, codice_azienda_provenienza character varying, specie character varying, data_macellazione timestamp without time zone, cd_veterinario_1 character varying, cd_veterinario_2 character varying, cd_veterinario_3 character varying, cd_data_nascita timestamp without time zone, categoria text) AS
$BODY$
DECLARE
	r RECORD;
	 	
BEGIN
		FOR descrizione_macello ,
                codice_macello ,
                id_asl ,
       asl  ,
       matricola ,
       codice_azienda_provenienza,
       specie,
       data_macellazione,
       cd_veterinario_1, 
       cd_veterinario_2, 
       cd_veterinario_3,
       cd_data_nascita,
       categoria
		in
		select distinct * from report_vista_capi as t
where t.data_macellazione >= data_1 and 
      t.data_macellazione <= data_2 
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
    
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_allcapi(timestamp without time zone, timestamp without time zone)
  OWNER TO report;








