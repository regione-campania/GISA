
CREATE OR REPLACE FUNCTION public_functions.get_controlli_gestore_acque( data_1 timestamp without time zone,
    data_2 timestamp without time zone)
  RETURNS TABLE(demoninazione_ente_gestore text, asl_punto text, comune_punto text, indirizzo_punto text, demoninazione_punto text, tipologia_punto text, stato_punto text,data_inizio_controllo timestamp without time zone, data_fine_controllo timestamp without time zone,
temperatura text,  cloro text, ora text, protocollo_routine boolean, protocollo_verifica boolean,protocollo_replica_micro boolean,  protocollo_replica_chimica boolean,  protocollo_radioattivita boolean, protocollo_ricerca_fitosanitari boolean, esito_controllo text,  parametri_non_conformi text) AS
$BODY$
DECLARE
	r RECORD;
BEGIN
	FOR demoninazione_ente_gestore, asl_punto, comune_punto, indirizzo_punto, demoninazione_punto, tipologia_punto, stato_punto,data_inizio_controllo, data_fine_controllo,
temperatura,  cloro, ora, protocollo_routine, protocollo_verifica,protocollo_replica_micro,  protocollo_replica_chimica,  protocollo_radioattivita, protocollo_ricerca_fitosanitari, esito_controllo,  parametri_non_conformi
	
	in
       select gest.nome as demoninazione_ente_gestore, asl.description as asl_punto, comuni.nome as comune_punto, concat_ws(' ', ind.desc_toponimo, ind.desc_via, ind.desc_civico) as indirizzo_punto, punti.denominazione as demoninazione_punto, tipo.description as tipologia_punto, punti.stato as stato_punto,t.assigned_date as data_inizio_controllo, t.data_fine_controllo as data_fine_controllo,
pdp.temperatura as temperatura, pdp.cloro as cloro, pdp.ore as ora, pdp.prot_routine as protocollo_routine, pdp.prot_verifica as protocollo_verifica, pdp.prot_replica_micro as protocollo_replica_micro, pdp.prot_replica_chim as protocollo_replica_chimica, pdp.prot_radioattivita as protocollo_radioattivita, pdp.prot_ricerca_fitosanitari as protocollo_ricerca_fitosanitari, t.esito as esito_controllo, t.nonconformitaformali as parametri_non_conformi
 from gestori_acque_punti_prelievo punti
left join lookup_site_id asl on asl.code = punti.id_asl
left join gestori_acque_lookup_tipologie_punti_prelievo tipo on tipo.code = punti.id_lookup_tipologia
left join gestori_acque_indirizzi ind on ind.id = punti.id_indirizzo
left join comuni1 comuni on comuni.id = ind.id_comune
left join gestori_acque_gestori gest  on gest.id = punti.id_gestore
left join  gestori_acque_anag anag on gest.id = anag.id_gestore 
left join  controlli_punti_di_prelievo_acque_rete pdp on pdp.org_id_pdp = punti.id
left join ticket t on t.ticketid = pdp.id_controllo and t.tipologia = 3
where t.assigned_date between data_1 and data_2

        
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.get_controlli_gestore_acque(timestamp without time zone,timestamp without time zone)
  OWNER TO postgres;
