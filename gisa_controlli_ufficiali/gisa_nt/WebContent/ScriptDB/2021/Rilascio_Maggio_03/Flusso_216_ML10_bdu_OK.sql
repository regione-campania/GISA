
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

select ml.codice into codiceUnivoco from r_gisa_opu_relazione_stabilimento_linee_produttive rel join r_ml8_linee_attivita_nuove_materializzata ml on rel.id_linea_produttiva = ml.id_nuova_linea_attivita where rel.id = idlineaGisa;

raise info 'codiceUnivoco %',codiceUnivoco;


if codiceUnivoco in('IUV-CODAC-VEDCG','IUV-COIAC-VEICG', '349-93-ALPET-PRIV', '349-93-ALCAT-PRIV', '349-93-ALPET-ALTR', '349-93-ALCAT-ALTR', 
--new
'IUV-CODAC-DET', 'IUV-COIAC-INGINF', 'IUV-COIAC-INGSUP')
then
trasmettiBdu:=true ;
flagOpComm:=true;
end if;

--aggiornamento per ml10: sono canili tutti quelli che hanno come aggregazione CAN più quelli già contemplati precedentemente
if codiceUnivoco in('IUV-CAN-CAN','VET-AMBV-PU','VET-CLIV-PU', 'VET-OSPV-PU','IUV-CAN-ALLGAT', 'IUV-CAN-ALLCAN', 'IUV-CAN-RIFRIC', 'IUV-CAN-RIFSAN', 'IUV-CAN-PEN','IUV-CAN-AAF','IUV-ADCA-ADCA') 
then
trasmettiBdu:=true ;
flagCanile:=true;
end if;

/*if codiceUnivoco in('IUV-CODAC-VEDCG','IUV-COIAC-VEICG', '349-93-ALPET-ALTR', '349-93-ALCAT-ALTR')
then
trasmettiBdu:=true ;
flagOpComm:=true;
end if;

if codiceUnivoco in('IUV-CAN-CAN','VET-AMBV-PU','VET-CLIV-PU', 'VET-OSPV-PU') 
then
trasmettiBdu:=true ;
flagCanile:=true;
end if;*/

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
raise info 'select * from public_functions.suap_insert_linea_produttiva(%,%)', id_stab_out,idStabilimentoGisa;
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


