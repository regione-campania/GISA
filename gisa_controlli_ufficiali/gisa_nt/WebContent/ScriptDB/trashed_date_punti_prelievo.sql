ALTER TABLE gestori_acque_punti_prelievo ADD COLUMN trashed_date timestamp(3) without time zone;
ALTER TABLE gestori_acque_punti_prelievo ADD COLUMN note_hd text;


CREATE OR REPLACE FUNCTION public_functions.get_controlli_gestore_acque(
    IN data_1 timestamp without time zone,
    IN data_2 timestamp without time zone)
  RETURNS TABLE(demoninazione_ente_gestore text, asl_punto text, comune_punto text, indirizzo_punto text, demoninazione_punto text, tipologia_punto text, stato_punto text, data_inizio_controllo timestamp without time zone, data_fine_controllo timestamp without time zone, temperatura text, cloro text, ora text, protocollo_routine boolean, protocollo_verifica boolean, protocollo_replica_micro boolean, protocollo_replica_chimica boolean, protocollo_radioattivita boolean, protocollo_ricerca_fitosanitari boolean, esito_controllo text, parametri_non_conformi text) AS
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
where punti.trashed_date is null and t.assigned_date between data_1 and data_2

        
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.get_controlli_gestore_acque(timestamp without time zone, timestamp without time zone)
  OWNER TO postgres;




CREATE OR REPLACE FUNCTION public_functions.dbi_get_punti_prelievo(IN id_asl_in integer)
  RETURNS TABLE(id integer, denominazione text, id_asl integer, code_tipologia integer, codice text, id_gestore integer, data_inserimento timestamp without time zone, stato text, codice_gisa text, nome_gestore text, indirizzo_via text, latitudine double precision, longitudine double precision, descrizione_tipologia text, id_comune integer, descrizione_comune text, descrizione_asl text) AS
$BODY$
BEGIN
		FOR id,
denominazione,
id_asl,
code_tipologia,
codice,
id_gestore,
data_inserimento,
stato,
codice_gisa ,
       nome_gestore , 
       indirizzo_via ,
       latitudine,
       longitudine , 
       descrizione_tipologia, 
       id_comune,
       descrizione_comune ,
       descrizione_asl
		in
		select pp.id,
pp.denominazione,
pp.id_asl,
pp.id_lookup_tipologia as code_tipologia,
pp.codice,
pp.id_gestore,
pp.data_inserimento,
pp.stato,
pp.codice_gisa ,
       gest.nome as nome_gestore , 
       ind.desc_via as indirizzo_via ,
       ind.latitudine,
       ind.longitudine , 
       tipologia.description as descrizione_tipologia, 
       comuni1.id as id_comune,
       comuni1.nome as descrizione_comune ,
       asl.description as descrizione_asl
from gestori_acque_punti_prelievo pp 
join gestori_acque_gestori gest on  gest.id = pp.id_gestore 
left join lookup_site_id asl on asl.code = pp.id_asl 
left join  gestori_acque_lookup_tipologie_punti_prelievo tipologia on tipologia.code = pp.id_lookup_tipologia 
left join gestori_acque_indirizzi ind on ind.id = pp.id_indirizzo 
left join comuni1 on ind.id_comune = comuni1.id 
where pp.trashed_date is null and (id_asl_in =-1 or id_asl_in = asl.code)	
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_punti_prelievo(integer)
  OWNER TO postgres;





ALTER TABLE public.gestori_acque_punti_prelievo
  drop CONSTRAINT gestori_acque_punti_prelievo_codice_key;

  ALTER TABLE public.gestori_acque_punti_prelievo
  ADD CONSTRAINT gestori_acque_punti_prelievo_codice_key UNIQUE(codice_gisa,trashed_date) ;