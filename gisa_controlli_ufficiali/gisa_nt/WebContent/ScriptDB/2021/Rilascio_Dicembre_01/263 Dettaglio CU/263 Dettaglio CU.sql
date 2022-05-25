-- Chi: Bartolo Sansone
-- Cosa: Flusso 263 Dettaglio CU
-- Quando: 15/12/21

CREATE OR REPLACE FUNCTION public.get_motivi_controllo_ufficiale(IN _idcu integer)
  RETURNS text AS
$BODY$
DECLARE  
	jsonOutput text;
BEGIN 
	select to_jsonb(array_agg(r))::text into jsonOutput
FROM (
select 
tcu.idcontrollo as id_controllo,
UPPER(case when tcu.tipoispezione = 89 then 'PIANO DI MONITORAGGIO' else 'ATTIVITA' end)::text as tipo_motivo,
UPPER(case when tcu.tipoispezione = 89 then indpia.alias_indicatore else indatt.alias_indicatore end)::text as alias_motivo,
UPPER(case when tcu.tipoispezione = 89 then indpia2.descrizione ||' >> '||indpia.descrizione else indatt2.descrizione||' >> '||indatt.descrizione end)::text as descrizione_motivo,
UPPER(str.pathdes)::text as per_conto_di,
UPPER(case when tcu.tipoispezione = 89 then indpia.codice_interno_attivita_gestione_cu else indatt.codice_interno_attivita_gestione_cu end)::text as cod_interno_motivo
from tipocontrolloufficialeimprese tcu
left join dpat_indicatore_new indatt on indatt.id = tcu.tipoispezione
left join dpat_piano_attivita_new indatt2 on indatt2.id = indatt.id_piano_attivita
left join dpat_indicatore_new indpia on indpia.id = tcu.pianomonitoraggio
left join dpat_piano_attivita_new indpia2 on indpia2.id = indpia.id_piano_attivita
left join dpat_strutture_asl str on str.id = tcu.id_unita_operativa
where tcu.idcontrollo = _idcu and tcu.enabled and (tcu.tipoispezione>0 or tcu.pianomonitoraggio>0)
order by tipo_motivo asc, alias_motivo asc) r;

	return jsonOutput;

END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;


