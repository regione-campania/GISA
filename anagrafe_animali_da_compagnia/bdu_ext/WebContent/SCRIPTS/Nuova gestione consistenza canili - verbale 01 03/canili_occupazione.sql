-- View: public.canili_occupazione_old

-- DROP VIEW public.canili_occupazione_old;

CREATE OR REPLACE VIEW public.canili_occupazione AS 
 SELECT DISTINCT o.id_rel_stab_lp,
    asl.code,
    asl.description,
    o.ragione_sociale,
    c.mq_disponibili,
    sum(case when (a.data_nascita + '8 months' < now()) then t.occupazione
	else (select t2.occupazione from lookup_taglia t2 where t2.code=1)
	end) AS sum,
    sum(case when (a.data_nascita + '8 months' < now()) then t.occupazione
	else (select t2.occupazione from lookup_taglia t2 where t2.code=1)
	end) / c.mq_disponibili::double precision * 100::double precision AS indice
   FROM animale a
     LEFT JOIN lookup_taglia t ON t.code = a.id_taglia
     LEFT JOIN opu_operatori_denormalizzati_view o ON o.id_rel_stab_lp = a.id_detentore
     LEFT JOIN opu_informazioni_canile c ON c.id_relazione_stabilimento_linea_produttiva = o.id_rel_stab_lp
     LEFT JOIN lookup_site_id asl ON asl.code = o.id_asl
  WHERE o.id_linea_produttiva = 5 AND a.trashed_date IS NULL AND a.flag_decesso IS NOT TRUE AND a.flag_smarrimento IS NOT TRUE AND c.abusivo IS NOT TRUE AND a.id > 0 AND a.id_specie = 1 AND c.mq_disponibili > 0
  GROUP BY o.id_rel_stab_lp, asl.code, asl.description, o.ragione_sociale, c.mq_disponibili;

ALTER TABLE public.canili_occupazione
  OWNER TO postgres;




