CREATE OR REPLACE VIEW totali_canili AS 
 SELECT asl.description AS asl_canile, o.ragione_sociale AS canile, count(*) AS totale
   FROM animale a
   LEFT JOIN opu_operatori_denormalizzati o ON o.id_rel_stab_lp = a.id_detentore
   LEFT JOIN lookup_asl_rif asl ON asl.code = o.id_asl
   LEFT JOIN EVENTO on (a.id = evento.id_animale and id_tipologia_evento = 9)
  WHERE o.id_linea_produttiva = 5  AND a.data_cancellazione IS NULL AND evento.id_animale is null
  GROUP BY o.ragione_sociale, asl.description
  ORDER BY o.ragione_sociale;

ALTER TABLE totali_canili
  OWNER TO postgres;
GRANT ALL ON TABLE totali_canili TO postgres;
GRANT SELECT ON TABLE totali_canili TO usr_ro;



