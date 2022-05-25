drop view elenco_canili_autorizzati3;
CREATE OR REPLACE VIEW public.elenco_canili_autorizzati3 AS 
 SELECT l.description,
    rel_stab_lp.id AS org_id,
    o.ragione_sociale AS name,
    rapp.cognome AS namelast,
    rapp.nome AS namefirst,
    ind.via AS addrline1,
    com.nome AS city,
    rel_stab_lp.telefono1 AS numero1,
    rel_stab_lp.telefono2 AS numero2,
    COALESCE(canile.centro_sterilizzazione, false) AS centro_sterilizzazione,
    canile.abusivo,
    stab.id_asl,
    o.org_id_old,
    case when (canile.mq_disponibili is not null and canile.mq_disponibili>0) then canile.mq_disponibili::text else 'N.D.' end as mq_disponibili,
    canili_occupazione.numero_cani_vivi,
    canili_occupazione.indice,
    rel_stab_lp.data_fine,
    bl.data_sospensione<=now() as bloccato
   FROM opu_relazione_stabilimento_linee_produttive rel_stab_lp
     LEFT JOIN opu_stabilimento stab ON rel_stab_lp.id_stabilimento = stab.id
     LEFT JOIN lookup_asl_rif l ON l.code = stab.id_asl
     LEFT JOIN opu_operatore o ON stab.id_operatore = o.id
     LEFT JOIN opu_rel_operatore_soggetto_fisico rel_rapp ON o.id = rel_rapp.id_operatore
     LEFT JOIN opu_soggetto_fisico rapp ON rel_rapp.id_soggetto_fisico = rapp.id
     LEFT JOIN opu_indirizzo ind ON stab.id_indirizzo = ind.id
     LEFT JOIN opu_informazioni_canile canile ON rel_stab_lp.id = canile.id_relazione_stabilimento_linea_produttiva
     LEFT JOIN comuni1 com ON com.id = ind.comune
     left join canili_occupazione on canili_occupazione.id_rel_stab_lp = rel_stab_lp.id
     left join blocco_sblocco_canile bl on (bl.id_canile = rel_stab_lp.id and bl.trashed = false and bl.trashed_date is null) 
  WHERE rel_stab_lp.id_linea_produttiva = 5 --AND rel_stab_lp.data_fine IS NULL 
  AND canile.data_chiusura IS NULL AND o.trashed_date IS NULL AND (canile.flag_clinica_ospedale IS FALSE OR canile.flag_clinica_ospedale IS NULL) AND (rel_rapp.data_fine IS NULL OR rel_rapp.data_fine > now())
  ORDER BY l.description, rel_stab_lp.id;

ALTER TABLE public.elenco_canili_autorizzati3
  OWNER TO postgres;
GRANT ALL ON TABLE public.elenco_canili_autorizzati3 TO postgres;
GRANT SELECT ON TABLE public.elenco_canili_autorizzati3 TO usr_ro;
GRANT SELECT ON TABLE public.elenco_canili_autorizzati3 TO report;


CREATE OR REPLACE FUNCTION public_functions.get_stato_canile(IN id_stabilimento_gisa_in integer, IN org_id integer)
  RETURNS TABLE(data_fine timestamp without time zone, data_operazione timestamp without time zone, bloccato boolean, data_riattivazione timestamp without time zone, mq_disponibili integer, occupazione_attuale float, numero_cani_vivi bigint, data_sospensione timestamp without time zone) AS
$BODY$

 BEGIN
    
   RETURN QUERY                                                                                                                                                                          
      SELECT rslp.data_fine, blocco.data_operazione, blocco.bloccato, blocco.data_riattivazione, info.mq_disponibili, sum as occupazione_attuale, occ.numero_cani_vivi ,    blocco.data_sospensione
      from opu_stabilimento stab
      left join opu_relazione_stabilimento_linee_produttive rslp on rslp.id_stabilimento = stab.id 
      left join opu_informazioni_canile info on info.id_relazione_stabilimento_linea_produttiva = rslp.id
      left join canili_occupazione occ on occ.id_rel_stab_lp = rslp.id
      left join blocco_sblocco_canile blocco on blocco.id_canile = rslp.id and blocco.trashed=false and blocco.trashed_date is null
      where (stab.id_stabilimento_gisa = id_stabilimento_gisa_in or rslp.id = org_id or (id_stabilimento_gisa_in = -1 and org_id = -1)) 
      order by blocco.id desc limit 1
      ;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.get_stato_canile(integer,integer)
  OWNER TO postgres;


