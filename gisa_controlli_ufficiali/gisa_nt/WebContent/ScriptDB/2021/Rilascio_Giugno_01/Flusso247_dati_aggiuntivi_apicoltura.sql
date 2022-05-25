

alter table ticket ADD  column apiari_selezionati text;
alter table ticket ADD  column apiari_selezionati_motivo text;
alter table ticket ADD  column apiari_selezionati_motivo_altro text;
alter table ticket ADD  column apiari_selezionati_alveari_controllati int;
alter table ticket ADD  column apiari_selezionati_esito text;
alter table ticket ADD  column apiari_selezionati_esito_note text;

  
/*CREATE OR REPLACE FUNCTION digemon.estrazione_controlli_apicoltura_tipoA(
    IN _data_1 text,
    IN _data_2 text)
  RETURNS TABLE(id_controllo_ufficiale text, data_inizio_controllo text,
		alias_indicatore text,
		piano_monitoraggio text, 
		ragione_sociale text, 
		asl_apiario text,
		cun text,
		comune_apiario text, 
		provincia_apiario text, 
		indirizzo_apiario text,
		cap_apiario text, 
		num_alveari_presenti text,
		modalita_selezione_apiario text,
		classificazione text
		--numero_alveari_controllati text
		--esito text, 
		--note_esito text
		) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY

with cte AS(
	select  t.ticketid::text as id_controllo_ufficiale
		, t.assigned_date::text as data_inizio_controllo
		, lpm.alias as alias_indicatore
		, lpm.description::text as piano_monitoraggio 
		, a.ragione_sociale::text
		, a.asl_apiario::text
		, a.codice_azienda as cun
		, a.comune_stab::text as comune_apiario
		, case when a.prov_stab::text = '64' then 'AV'
		       when a.prov_stab::text = '62' then 'BN'
		       when a.prov_stab::text = '61' then 'CE'
		       when a.prov_stab::text = '63' then 'NA'
		       when a.prov_stab::text = '65' then 'SA'
		       else a.prov_stab::text
		  end  as provincia_apiario
		, a.indirizzo_stab::text as indirizzo_apiario
		, a.cap_stab::text as cap_apiario
		, a.num_alveari::text as num_alveari_presenti
		, t.apiari_selezionati::text as modalita_selezione_apiario
		, a.classificazione
		--, t.apiari_selezionati_alveari_controllati::text as numero_alveari_controllati
		--, t.apiari_selezionati_esito::text as esito
		--, t.apiari_selezionati_esito_note::text as note_esito
	from ticket t
		join tipocontrolloufficialeimprese tcu on tcu.idcontrollo = t.ticketid and tcu.enabled and tcu.pianomonitoraggio > 0
		join lookup_piano_monitoraggio lpm on lpm.code = tcu.pianomonitoraggio and lpm.enabled and lpm.codice_interno_univoco = 3532
		join apicoltura_apiari_denormalizzati_view a on a.id_apicoltura_apiari = t.id_apiario
	where t.trashed_date is null and t.tipologia = 3 
) select * 
from cte
where cte.data_inizio_controllo::date between _data_1::date and _data_2::date
and cte.modalita_selezione_apiario = 'A';

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.estrazione_controlli_apicoltura_tipoA(text, text)
  OWNER TO postgres;

select * from digemon.estrazione_controlli_apicoltura_tipoA('2021-01-01', '2021-05-31')
*/
 ------------------------------------- solo questa qui ---------------------------------------
CREATE OR REPLACE FUNCTION digemon.estrazione_controlli_apicoltura_tipoAB(
    IN _data_1 text,
    IN _data_2 text)
  RETURNS TABLE(id_controllo_ufficiale text, data_inizio_controllo text,
		alias_indicatore text,
		piano_monitoraggio text, 
		ragione_sociale text, 
		asl_apiario text,
		cun text,
		comune_apiario text, 
		provincia_apiario text, 
		indirizzo_apiario text,
		cap_apiario text, 
		num_alveari_presenti text,
		modalita_selezione_apiario text,
		classificazione text, 
		motivo_controllo_apiario text,
		specifica_altro_motivo_controllo text,
		numero_alveari_controllati text,
		esito text, 
		note_esito text) AS
$BODY$
DECLARE
BEGIN
RETURN QUERY

with cte AS(
	select  t.ticketid::text as id_controllo_ufficiale
		, t.assigned_date::text as data_inizio_controllo
		, lpm.alias as alias_indicatore
		, lpm.description::text as piano_monitoraggio 
		, a.ragione_sociale::text
		, a.asl_apiario::text
		, a.codice_azienda as cun
		, a.comune_stab::text as comune_apiario
		, case when a.prov_stab::text = '64' then 'AV'
		       when a.prov_stab::text = '62' then 'BN'
		       when a.prov_stab::text = '61' then 'CE'
		       when a.prov_stab::text = '63' then 'NA'
		       when a.prov_stab::text = '65' then 'SA'
		       else a.prov_stab::text
		  end  as provincia_apiario
		, a.indirizzo_stab::text as indirizzo_apiario
		, a.cap_stab::text as cap_apiario
		, a.num_alveari::text as num_alveari_presenti
		, t.apiari_selezionati::text as modalita_selezione_apiario
		, a.classificazione
		, case when t.apiari_selezionati_motivo::integer = 1 then 'nomadismo'
		       when t.apiari_selezionati_motivo::integer = 2 then 'acquisto materiale biologico'
		       when t.apiari_selezionati_motivo::integer = 3 then 'picoltori in possesso di diversi apiari stanziali in più regioni o province autonome'
		       when t.apiari_selezionati_motivo::integer = 4 then 'altro'
		end as motivo_controllo_apiario
		, t.apiari_selezionati_motivo_altro as specifica_altro_motivo_controllo
		, t.apiari_selezionati_alveari_controllati::text as numero_alveari_controllati
		, t.apiari_selezionati_esito::text as esito
		, t.apiari_selezionati_esito_note::text as note_esito
	from ticket t
		join tipocontrolloufficialeimprese tcu on tcu.idcontrollo = t.ticketid and tcu.enabled and tcu.pianomonitoraggio > 0
		join lookup_piano_monitoraggio lpm on lpm.code = tcu.pianomonitoraggio and lpm.enabled and lpm.codice_interno_univoco = 3532
		join apicoltura_apiari_denormalizzati_view a on a.id_apicoltura_apiari = t.id_apiario
	where t.trashed_date is null and t.tipologia = 3 
) select * 
from cte
where cte.data_inizio_controllo::date between _data_1::date and _data_2::date
and (cte.modalita_selezione_apiario = 'A' or cte.modalita_selezione_apiario = 'B');

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION digemon.estrazione_controlli_apicoltura_tipoAB(text, text)
  OWNER TO postgres;

select * from digemon.estrazione_controlli_apicoltura_tipoAB('2021-01-01', '2021-05-31')

