--GISA

-- View: public.opu_operatori_denormalizzati_view_bdu

-- DROP VIEW public.opu_operatori_denormalizzati_view_bdu;

CREATE OR REPLACE VIEW public.opu_operatori_denormalizzati_view_bdu AS 
 SELECT opu_operatori_denormalizzati_view.flag_dia,
    opu_operatori_denormalizzati_view.id_opu_operatore,
    opu_operatori_denormalizzati_view.ragione_sociale,
    opu_operatori_denormalizzati_view.partita_iva,
    opu_operatori_denormalizzati_view.codice_fiscale_impresa,
    opu_operatori_denormalizzati_view.indirizzo_sede_legale,
    opu_operatori_denormalizzati_view.comune_sede_legale,
    opu_operatori_denormalizzati_view.istat_legale,
    opu_operatori_denormalizzati_view.cap_sede_legale,
    opu_operatori_denormalizzati_view.prov_sede_legale,
    opu_operatori_denormalizzati_view.note,
    opu_operatori_denormalizzati_view.entered,
    opu_operatori_denormalizzati_view.modified,
    opu_operatori_denormalizzati_view.enteredby,
    opu_operatori_denormalizzati_view.modifiedby,
    opu_operatori_denormalizzati_view.domicilio_digitale,
    opu_operatori_denormalizzati_view.flag_ric_ce,
    opu_operatori_denormalizzati_view.num_ric_ce,
    opu_operatori_denormalizzati_view.comune,
    opu_operatori_denormalizzati_view.id_asl,
    opu_operatori_denormalizzati_view.id_stabilimento,
    opu_operatori_denormalizzati_view.esito_import,
    opu_operatori_denormalizzati_view.data_import,
    opu_operatori_denormalizzati_view.cun,
    opu_operatori_denormalizzati_view.id_sinvsa,
    opu_operatori_denormalizzati_view.descrizione_errore,
    opu_operatori_denormalizzati_view.comune_stab,
    opu_operatori_denormalizzati_view.istat_operativo,
    opu_operatori_denormalizzati_view.indirizzo_stab,
    opu_operatori_denormalizzati_view.cap_stab,
    opu_operatori_denormalizzati_view.prov_stab,
    opu_operatori_denormalizzati_view.data_fine_dia,
    opu_operatori_denormalizzati_view.categoria_rischio,
    opu_operatori_denormalizzati_view.cf_rapp_sede_legale,
    opu_operatori_denormalizzati_view.nome_rapp_sede_legale,
    opu_operatori_denormalizzati_view.cognome_rapp_sede_legale,
    opu_operatori_denormalizzati_view.codice_registrazione,
    opu_operatori_denormalizzati_view.id_norma,
    opu_operatori_denormalizzati_view.cf_correntista,
    opu_operatori_denormalizzati_view.codice_attivita,
    opu_operatori_denormalizzati_view.primario,
    opu_operatori_denormalizzati_view.attivita,
    opu_operatori_denormalizzati_view.data_inizio,
    opu_operatori_denormalizzati_view.data_fine,
    opu_operatori_denormalizzati_view.numero_registrazione,
    opu_operatori_denormalizzati_view.indirizzo_rapp_sede_legale,
    opu_operatori_denormalizzati_view.stato,
    opu_operatori_denormalizzati_view.solo_attivita,
    opu_operatori_denormalizzati_view.data_inizio_attivita,
    opu_operatori_denormalizzati_view.data_fine_attivita,
    opu_operatori_denormalizzati_view.data_prossimo_controllo,
    opu_operatori_denormalizzati_view.id_stato,
    opu_operatori_denormalizzati_view.path_attivita_completo,
    opu_operatori_denormalizzati_view.id_indirizzo,
    opu_operatori_denormalizzati_view.linee_pregresse,
    opu_operatori_denormalizzati_view.flag_nuova_gestione,
    opu_operatori_denormalizzati_view.tipo_impresa,
    opu_operatori_denormalizzati_view.tipo_societa,
    opu_operatori_denormalizzati_view.codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.id_asl_stab,
    opu_operatori_denormalizzati_view.id_linea_attivita,
    opu_operatori_denormalizzati_view.data_mod_attivita,
    opu_operatori_denormalizzati_view.stab_entered,
    opu_operatori_denormalizzati_view.linea_numero_registrazione,
    opu_operatori_denormalizzati_view.linea_stato,
    opu_operatori_denormalizzati_view.linea_stato_text,
    opu_operatori_denormalizzati_view.linea_codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.stab_codice_ufficiale_esistente,
    opu_operatori_denormalizzati_view.id_indirizzo_operatore,
    opu_operatori_denormalizzati_view.import_opu,
    opu_operatori_denormalizzati_view.id_linea_attivita_stab,
    opu_operatori_denormalizzati_view.note_operatore,
    opu_operatori_denormalizzati_view.note_stabilimento,
    opu_operatori_denormalizzati_view.linea_codice_nazionale,
    opu_operatori_denormalizzati_view.flag_pnaa,
    opu_operatori_denormalizzati_view.stab_inserito_da,
    opu_operatori_denormalizzati_view.stab_id_attivita,
    opu_operatori_denormalizzati_view.stab_descrizione_attivita,
    opu_operatori_denormalizzati_view.stab_id_carattere,
    opu_operatori_denormalizzati_view.stab_descrizione_carattere,
    opu_operatori_denormalizzati_view.impresa_id_tipo_impresa,
    opu_operatori_denormalizzati_view.stab_asl,
    opu_operatori_denormalizzati_view.flag_clean,
    opu_operatori_denormalizzati_view.data_generazione_numero,
    opu_operatori_denormalizzati_view.lat_stab,
    opu_operatori_denormalizzati_view.long_stab,
    opu_operatori_denormalizzati_view.linea_entered,
    opu_operatori_denormalizzati_view.linea_modified,
    opu_operatori_denormalizzati_view.macroarea,
    opu_operatori_denormalizzati_view.aggregazione,
    opu_operatori_denormalizzati_view.attivita_xml,
    opu_operatori_denormalizzati_view.codiceistatasl_old,
    opu_operatori_denormalizzati_view.sigla_prov_operativa,
    opu_operatori_denormalizzati_view.sigla_prov_legale,
    opu_operatori_denormalizzati_view.sigla_prov_soggfisico,
    opu_operatori_denormalizzati_view.comune_residenza,
    opu_operatori_denormalizzati_view.data_nascita_rapp_sede_legale,
    opu_operatori_denormalizzati_view.impresa_id_tipo_societa,
    opu_operatori_denormalizzati_view.comune_nascita_rapp_sede_legale,
    opu_operatori_denormalizzati_view.sesso,
    opu_operatori_denormalizzati_view.civico,
    opu_operatori_denormalizzati_view.toponimo_residenza,
    opu_operatori_denormalizzati_view.id_toponimo_residenza,
    opu_operatori_denormalizzati_view.civico_sede_legale,
    opu_operatori_denormalizzati_view.tiponimo_sede_legale,
    opu_operatori_denormalizzati_view.toponimo_sede_legale,
    opu_operatori_denormalizzati_view.civico_sede_stab,
    opu_operatori_denormalizzati_view.tiponimo_sede_stab,
    opu_operatori_denormalizzati_view.toponimo_sede_stab,
    opu_operatori_denormalizzati_view.via_rapp_sede_legale,
    opu_operatori_denormalizzati_view.via_sede_legale,
    opu_operatori_denormalizzati_view.id_comune_richiesta,
    opu_operatori_denormalizzati_view.comune_richiesta,
    opu_operatori_denormalizzati_view.via_stabilimento_calcolata,
    opu_operatori_denormalizzati_view.civico_stabilimento_calcolato,
    opu_operatori_denormalizzati_view.cap_residenza,
    opu_operatori_denormalizzati_view.nazione_residenza,
    opu_operatori_denormalizzati_view.nazione_sede_legale,
    opu_operatori_denormalizzati_view.nazione_stab,
    opu_operatori_denormalizzati_view.id_lookup_tipo_linee_mobili,
    opu_operatori_denormalizzati_view.id_tipo_linea_produttiva,
    opu_operatori_denormalizzati_view.id_soggetto_fisico,
        CASE
            WHEN opu_operatori_denormalizzati_view.codice_attivita in ('IUV-CAN-CAN','VET-AMBV-PU','VET-CLIV-PU', 'VET-OSPV-PU') THEN 5
            WHEN opu_operatori_denormalizzati_view.codice_attivita in ('IUV-CODAC-VEDCG','IUV-COIAC-VEICG') THEN 6
            ELSE NULL::integer
        END AS id_linea_bdu
   FROM opu_operatori_denormalizzati_view
  WHERE opu_operatori_denormalizzati_view.codice_attivita in ('IUV-CAN-CAN','IUV-CODAC-VEDCG','IUV-COIAC-VEICG','VET-AMBV-PU','VET-CLIV-PU','VET-OSPV-PU');

ALTER TABLE public.opu_operatori_denormalizzati_view_bdu
  OWNER TO postgres;
GRANT ALL ON TABLE public.opu_operatori_denormalizzati_view_bdu TO postgres;
GRANT SELECT ON TABLE public.opu_operatori_denormalizzati_view_bdu TO report;


-- BDU

CREATE OR REPLACE FUNCTION public_functions.suap_inserisci_canile_bdu(idstabilimentogisa integer)
  RETURNS integer AS
$BODY$
DECLARE
id_impresa integer;
id_soggetto_out integer;
id_stab_out integer;
id_stabl_out integer;
esito text;
esito_estesi text;
id_rel_stab_lp_gisa int ;
idStabBdu int ;
idLineaBdu int ;
idlineaGisa int ;
specieAnimaliOpComm text;
trasmettiBdu boolean;

codiceUnivoco text;

flagOpComm boolean ;
flagCanile boolean;
BEGIN


select st.id into idStabBdu from opu_stabilimento st join opu_operatore op on op.id=st.id_operatore 
where id_stabilimento_gisa =idstabilimentogisa and op.trashed_date is null;

flagCanile:=false;
flagOpComm:=false;
trasmettiBdu:=false;

for idLineaBdu , idlineaGisa in
select distinct on (id_linea_bdu) id_linea_bdu,id_linea_attivita 
from opu_operatori_denormalizzati_canili_opc_gisa v 
where v.id_stabilimento = idstabilimentogisa

loop

raise info 'id_linea_bdu %',idLineaBdu;
raise info 'id_linea_attivita gisa  %',idlineaGisa;

select ml.codice_attivita into codiceUnivoco from r_gisa_opu_relazione_stabilimento_linee_produttive rel join r_ml8_linee_attivita_nuove_materializzata ml on rel.id_linea_produttiva = ml.id_nuova_linea_attivita where rel.id = idlineaGisa;

raise info 'codiceUnivoco %',codiceUnivoco;




if codiceUnivoco in('IUV-CODAC-VEDCG','IUV-COIAC-VEICG')
then
trasmettiBdu:=true ;
flagOpComm:=true;
end if;

if codiceUnivoco in('IUV-CAN-CAN','VET-AMBV-PU','VET-CLIV-PU', 'VET-OSPV-PU') 
then
trasmettiBdu:=true ;
flagCanile:=true;
end if;

end loop ;


raise info 'presenti cani gatti o furetti ? %',trasmettiBdu;
raise info 'idStabBdu: %', idStabBdu;
if idStabBdu is null or idStabBdu <=0
then


raise info 'flagCanile: %', flagCanile;
if flagCanile =true or (flagOpComm=true and trasmettiBdu=true)
then
-- 1. soggetto fisico ed indirizzo rappresentante legale che ritorna id_soggetto fisico
id_soggetto_out := (select * from public_functions.insert_soggetto_fisico_bdu(idStabilimentoGisa));

--2 impresa in relazione al soggetto fisico e all'indirizzo restituisce una tupla con id operatore partita_iva, ragione sociale e cf...
id_impresa := (select id from public_functions.suap_insert_impresa_bdu(idStabilimentoGisa,id_soggetto_out));
-- 3 stabilimento restituisce l'id dello stabilimento
id_stab_out := (select * from public_functions.suap_inserisci_stabilimento_bdu(id_impresa,idStabilimentoGisa));
-- 4 linea produttiva, esegue una insert e restituisce un booleano. 5 è un canile, 6 l'operatore commerciale
id_stabl_out := (select * from public_functions.suap_insert_linea_produttiva(id_stab_out,idStabilimentoGisa));
--aggiornamento del soggetto fisico responsabile della sede produttiva
--update opu_stabilimento set id_soggetto_fisico  = id_soggetto_out where id = id_stab_out;
--aggiornamento vista materializzato tramite id_impresa
--aggiornamento informazioni canile da gisa campi estesi a canili bdu



if  flagCanile =true 
then


id_rel_stab_lp_gisa:=(
select distinct on(id_linea_bdu) id_linea_attivita 
from opu_operatori_denormalizzati_canili_opc_gisa 
where id_linea_bdu = 5 
and id_stabilimento = idStabilimentoGisa
);



raise info '(select * from public_functions.aggiorna_info_canile(%,%,%))', id_rel_stab_lp_gisa::int, id_stab_out, id_stabl_out;
esito_estesi:= (select * from public_functions.aggiorna_info_canile(id_rel_stab_lp_gisa::int, id_stab_out, id_stabl_out));
end if;

if flagOpComm =true
then 
id_rel_stab_lp_gisa:=(select distinct on(id_linea_bdu) id_linea_attivita from opu_operatori_denormalizzati_canili_opc_gisa where id_linea_bdu=6 and id_stabilimento = idStabilimentoGisa);
esito_estesi:= (select * from public_functions.aggiorna_info_operatore(id_rel_stab_lp_gisa::int, id_stab_out));
end if;


delete from opu_operatori_denormalizzati where id_opu_operatore =id_impresa;
esito := (select * from public_functions.update_opu_materializato(id_impresa));

return id_impresa;
end if;
end if;
return -1;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public_functions.suap_inserisci_canile_bdu(integer)
  OWNER TO postgres;
