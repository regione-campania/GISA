CREATE OR REPLACE FUNCTION public.chk_bns_get_domande_v6(
    IN _codicespecie integer,
    IN _numallegato integer,
    IN _versione integer,
    IN _idcontrollo integer)
  RETURNS TABLE(id integer, level integer, irr_id integer, dettirrid integer, idmod integer, descrizione text, titolo text, sottotitolo text, quesito text, esito text, evidenze text) AS
$BODY$
DECLARE
BEGIN
	FOR id, level, irr_id , dettirrid , idmod , descrizione , titolo , sottotitolo, quesito , esito , evidenze
	in

	select distinct c.id, c.level, c.irr_id , c.dettirrid , c.idmod , c.descrizione , c.titolo , c.sottotitolo, c.quesito , c.esito , c.evidenze

from chk_bns_domande_v6 c where c.idmod in 
(select code from lookup_chk_bns_mod where codice_specie = _codiceSpecie and versione = _versione and num_allegato = _numAllegato and start_date < 
(select assigned_date from ticket where ticketid = _idcontrollo) and end_date >(select assigned_date from ticket where ticketid = _idcontrollo) ) 
order by c.level asc

LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.chk_bns_get_domande_v6(integer, integer, integer, integer)
  OWNER TO postgres;
 
  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_ovicaprini(
    _username_ws_bdn text,
    _password_ws_bdn text,
    _id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
BEGIN

  msg := concat('<?xml version="1.0" encoding="UTF-8"?>
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">','
',
(select get_chiamata_ws_insert_ba_sa_header from get_chiamata_ws_insert_ba_sa_header(_username_ws_bdn, _password_ws_bdn)),'
',
'<S:Body>
      <ns2:insertControlliallevamentiBA xmlns:ns2="http://ws.izs.it">','
      ',(select get_chiamata_ws_insert_ba_v6_ovicaprini_controlli_allevamenti from get_chiamata_ws_insert_ba_v6_ovicaprini_controlli_allevamenti(_id)),'
      ',(select get_chiamata_ws_insert_ba_v6_ovicaprini_dettaglio_checklist from get_chiamata_ws_insert_ba_v6_ovicaprini_dettaglio_checklist(_id)),'
      </ns2:insertControlliallevamentiBA>
      </S:Body> 
      </S:Envelope>');
    
    
 RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v6_ovicaprini(text, text, integer)
  OWNER TO postgres;

  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_ovicaprini_controlli_allevamenti(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
tag_preavviso text;
BEGIN
select 
concat('<controlliallevamenti>
	  <caId>0</caId>
           <dtControllo>', to_char(data_controllo, 'YYYY-MM-DD"T"00:00:00.000'),'</dtControllo>
            <aslCodice>',codice_asl,'</aslCodice>
            <aziendaCodice>',codice_azienda,'</aziendaCodice>
            <speCodice>',bdn.codice_specie_chk_bns,'</speCodice>
            <allevIdFiscale>',id_fiscale_allevamento,'</allevIdFiscale>
	    <tipoProdCodice>',tipo_produzione_ba,'</tipoProdCodice>
	    <tipoAllevCodice>',tipo_allevamento_codice,'</tipoAllevCodice>
	    <orientamentoCodice>',codice_orientamento_ba,'</orientamentoCodice>
            <ocCodice>',bdn.occodice,'</ocCodice>
            <flagEsito>',ovicaprini.esito_controllo,'</flagEsito>
            <flagVitelli>N</flagVitelli> 
            <flagExtrapiano>',ovicaprini.extrapiano,'</flagExtrapiano>
            <detenIdFiscale>',id_fiscale_detentore,'</detenIdFiscale>
            <flagPreavviso>',bdn.flag_preavviso,'</flagPreavviso>
	    ',case when bdn.flag_preavviso <> 'N' then concat('<dataPreavviso>',to_char(bdn.data_preavviso, 'YYYY-MM-DD"T"00:00:00.000'),'</dataPreavviso>') else '' end,'            
            <primoControllore>',ovicaprini.nome_controllore,'</primoControllore>
            <tipoControllo>BA</tipoControllo>
            <flagCopiaCheckList>',bdn.flag_checklist,'</flagCopiaCheckList>
	    ',case when ovicaprini.esito_controllo = 'N' then concat(
	    '<sanzBloccoMov>',ovicaprini.sanz_blocco,'</sanzBloccoMov>
            <sanzAmministrativa>',ovicaprini.sanz_amministrativa,'</sanzAmministrativa>
            <sanzSequestro>',ovicaprini.sanz_sequestro,'</sanzSequestro>
            <sanzAbbattimentoCapi>',ovicaprini.sanz_abbattimento,'</sanzAbbattimentoCapi>
            <sanzInformativa>',ovicaprini.sanz_informativa,'</sanzInformativa>
            <sanzAltro>',ovicaprini.sanz_altro,'</sanzAltro>
            ',case when ovicaprini.sanz_altro <> '0' then concat('<sanzAltroDesc>',ovicaprini.sanz_altro_desc,'</sanzAltroDesc>')
	    else '' end,'
            <noteControllore>',ovicaprini.note_controllore,'</noteControllore>
            <noteDetentore>',ovicaprini.note_proprietario,'</noteDetentore>')
	    else '' end,'             
	    ',case when ovicaprini.esito_controllo <> 'A' then 
	    concat('<flagEvidenze>',ovicaprini.evidenze,'</flagEvidenze>')
	    else '' end,
	    case when ovicaprini.evidenze = 'S' then 
	    concat('<evidenzaIr>',ovicaprini.evidenze_ir,'</evidenzaIr>
            <evidenzaSv>',ovicaprini.evidenze_sv,'</evidenzaSv>
            <evidenzaSa>',ovicaprini.evidenze_tse,'</evidenzaSa>')
            else '' end,'
            <flagCondizionalita>',ovicaprini.appartenente_condizionalita,'</flagCondizionalita>
             ',case when ovicaprini.data_chiusura_relazione <> '' then concat('<dataChiusura>',concat(ovicaprini.data_chiusura_relazione,'T00:00.000'),'</dataChiusura>')
	    else '' end,'    
         </controlliallevamenti>') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v6_ovicaprini ovicaprini on ovicaprini.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;

     
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

-- Function: public.get_chiamata_ws_insert_ba_v5_gallus_dettaglio_checklist(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v5_gallus_dettaglio_checklist(integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_ovicaprini_dettaglio_checklist(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
BEGIN

select 
concat(
'<dettagliochecklist>
	<clbId>0</clbId>
	<flagVitelli>N</flagVitelli>
	<critCodice>',ovicaprini.criteri_utilizzati,'</critCodice>
	',
	case when ovicaprini.criteri_utilizzati = 'CF13' then concat('<criterioControlloAltro>',ovicaprini.criteri_utilizzati_altro_descrizione,'</criterioControlloAltro>
	') else '' end,
	case when ovicaprini.esito_controllo = 'N' then concat('<flagIntenzionalita>',ovicaprini.intenzionalita,'</flagIntenzionalita>
	') else '' end,
	'<nomeRappresentante>',ovicaprini.nome_proprietario,'</nomeRappresentante>
	',
	'<tipoAllegato>',bdn.tipo_allegato,'</tipoAllegato>
	',
	case when ovicaprini.esito_controllo = 'N' then 
		concat('<prescrizioni>',ovicaprini.prescrizioni_descrizione,'</prescrizioni>
		') else '' end, 
		case when ovicaprini.prescrizioni_descrizione <> '' then 
			concat('<dataScadPrescrizioni>',concat(ovicaprini.data_prescrizioni,'T00:00:00.000'),'</dataScadPrescrizioni>
			        <flagPrescrizioneEsitoBa>',ovicaprini.eseguite_prescrizioni,'</flagPrescrizioneEsitoBa>
			        ',	
				case when ovicaprini.data_verifica <> '' then 
					concat('<dataVerificaBa>',concat(ovicaprini.data_verifica,'T00:00:00.000'),'</dataVerificaBa>
						<secondoControllore>',ovicaprini.nome_controllore,'</secondoControllore>
						<nomeRappresentanteVer>',ovicaprini.nome_proprietario_prescrizioni_eseguite,'</nomeRappresentanteVer>
						'
					       ) else '' end
		               
		      )else '' end,
	'<parametro>2022</parametro>
	<numClb>',ovicaprini.num_checklist,'</numClb>
	',
	case when ovicaprini.esito_controllo <> 'A' then concat('<kgLatte>',ovicaprini.kg_latte,'</kgLatte>							
	<numOviLattazione>',ovicaprini.num_ovi_lattazione, '</numOviLattazione> 
	<numCapLattazione>',ovicaprini.num_cap_lattazione, '</numCapLattazione>
	<numOviAsciutta>',ovicaprini.num_ovi_asciutta, '</numOviAsciutta>
	<numCapAsciutta>',ovicaprini.num_cap_asciutta, '</numCapAsciutta>
	<numArieti>',ovicaprini.num_arieti, '</numArieti>
	<numBecchi>',ovicaprini.num_becchi, '</numBecchi>
	<numOviRimonta>',ovicaprini.num_ovi_rimonta, '</numOviRimonta>
	<numCapRimonta>',ovicaprini.num_cap_rimonta, '</numCapRimonta>
	<numAgnelliRimonta>',ovicaprini.num_agnelli_rimonta, '</numAgnelliRimonta>
	<numCaprettiRimonta>',ovicaprini.num_capretti_rimonta, '</numCaprettiRimonta>
	<numAgnelli3m>',ovicaprini.num_agnelli_3m, '</numAgnelli3m>
	<numCapretti3m>',ovicaprini.num_capretti_3m, '</numCapretti3m>
	<qLatte>',ovicaprini.q_latte, '</qLatte>
	') else '' end,
	case when ovicaprini.esito_controllo <> 'A' then concat('<veterinario>',ovicaprini.veterinario_ispettore,'</veterinario>
	') else '' end, 
	case when ovicaprini.esito_controllo <> 'A' then concat('<presenzaManuale>',ovicaprini.presenza_manuale,'</presenzaManuale>
	') else '' end,
	case when ovicaprini.esito_controllo <> 'A' then (select get_chiamata_ws_insert_ba_v6_requisiti from public.get_chiamata_ws_insert_ba_v6_requisiti(_id)) else '' 
	END,'
</dettagliochecklist>
            ') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v6_ovicaprini ovicaprini on ovicaprini.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
   

    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

  -- Function: public.get_chiamata_ws_insert_ba_v5_requisiti(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v5_requisiti(integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_requisiti(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
BEGIN
select string_agg(concat('<requisiti>
               <cldbId>0</cldbId>
               <irrId>',dom.irr_id,'</irrId>
               <dettirrId>',dom.dettirrid,'</dettirrId>
               <flagEsitoDett>',risp.risposta,'</flagEsitoDett>
               <osservazioni>',risp.evidenze,'</osservazioni>
               <operation>insert</operation>
            </requisiti>'),'
            ')
into msg
from
chk_bns_risposte_v6 risp  
left join chk_bns_domande_v6 dom on risp.id_domanda = dom.id
where risp.trashed_date is null and risp.id_chk_bns_mod_ist = _id;     
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_suini_controlli_allevamenti(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
tag_preavviso text;
BEGIN

select 
concat('<controlliallevamenti>
            <allevIdFiscale>',id_fiscale_allevamento,'</allevIdFiscale>
            <aslCodice>',codice_asl,'</aslCodice>
            <aziendaCodice>',codice_azienda,'</aziendaCodice>
            <caId>0</caId>
            <detenIdFiscale>',id_fiscale_detentore,'</detenIdFiscale>
            <dtControllo>', to_char(data_controllo, 'YYYY-MM-DD"T"00:00:00.000'),'</dtControllo>
            <flagEsito>',suini.esito_controllo,'</flagEsito>
            <flagExtrapiano>',suini.extrapiano,'</flagExtrapiano>
            <flagPreavviso>',bdn.flag_preavviso,'</flagPreavviso>
	    ',case when bdn.flag_preavviso <> 'N' then concat('<dataPreavviso>',to_char(bdn.data_preavviso, 'YYYY-MM-DD"T"00:00:00.000'),'</dataPreavviso>') else '' end,'            
            <flagVitelli>',bdn.flag_vitelli,'</flagVitelli>
            <ocCodice>',bdn.occodice,'</ocCodice>
            <primoControllore>',suini.nome_controllore,'</primoControllore>
            <speCodice>',bdn.codice_specie_chk_bns,'</speCodice>
            <tipoControllo>BA</tipoControllo>
            <flagCopiaCheckList>',bdn.flag_checklist,'</flagCopiaCheckList>
	    ',case when suini.esito_controllo = 'N' then concat(
	    '<sanzBloccoMov>',suini.sanz_blocco,'</sanzBloccoMov>
            <sanzAmministrativa>',suini.sanz_amministrativa,'</sanzAmministrativa>
            <sanzSequestro>',suini.sanz_sequestro,'</sanzSequestro>
            <sanzAbbattimentoCapi>',suini.sanz_abbattimento,'</sanzAbbattimentoCapi>
            <sanzInformativa>',suini.sanz_informativa,'</sanzInformativa>
            <sanzAltro>',suini.sanz_altro,'</sanzAltro>
            ',case when suini.sanz_altro <> '0' then concat('<sanzAltroDesc>',suini.sanz_altro_desc,'</sanzAltroDesc>')
	    else '' end,'
            <noteControllore>',suini.note_controllore,'</noteControllore>
            <noteDetentore>',suini.note_proprietario,'</noteDetentore>')
	    else '' end,'             
	    ',case when suini.esito_controllo <> 'A' then 
	    concat('<flagEvidenze>',suini.evidenze,'</flagEvidenze>')
	    else '' end,
	    case when suini.evidenze = 'S' then 
	    concat('<evidenzaIr>',suini.evidenze_ir,'</evidenzaIr>
            <evidenzaSv>',suini.evidenze_sv,'</evidenzaSv>
            <evidenzaSa>',suini.evidenze_tse,'</evidenzaSa>')
            else '' end,'
            <flagCondizionalita>',suini.appartenente_condizionalita,'</flagCondizionalita>
             ',case when suini.data_chiusura_relazione <> '' then concat('<dataChiusura>',concat(suini.data_chiusura_relazione,'T00:00.000'),'</dataChiusura>')
	    else '' end,'    
         </controlliallevamenti>') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v6_suini suini on suini.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
     
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v6_suini_controlli_allevamenti(integer)
  OWNER TO postgres;

  CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_suini(
    _username_ws_bdn text,
    _password_ws_bdn text,
    _id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
BEGIN

  msg := concat('<?xml version="1.0" encoding="UTF-8"?>
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">','
',
(select get_chiamata_ws_insert_ba_sa_header from get_chiamata_ws_insert_ba_sa_header(_username_ws_bdn, _password_ws_bdn)),'
',
'<S:Body>
      <ns2:insertControlliallevamentiBA xmlns:ns2="http://ws.izs.it">','
      ',(select get_chiamata_ws_insert_ba_v6_suini_controlli_allevamenti from get_chiamata_ws_insert_ba_v6_suini_controlli_allevamenti(_id)),'
      ',(select get_chiamata_ws_insert_ba_v6_suini_dettaglio_checklist from get_chiamata_ws_insert_ba_v6_suini_dettaglio_checklist(_id)),'
      </ns2:insertControlliallevamentiBA>
      </S:Body> 
      </S:Envelope>');
    
 RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v6_suini(text, text, integer)
  OWNER TO postgres;

  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_suini_dettaglio_checklist(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
BEGIN
select 
concat(
'<dettagliochecklist>
	<clbId>0</clbId>
	<flagVitelli>',bdn.flag_vitelli,'</flagVitelli>
	<critCodice>',suini.criteri_utilizzati,'</critCodice>
	',
	case when suini.criteri_utilizzati = '997' then concat('<criterioControlloAltro>',suini.criteri_utilizzati_altro_descrizione,'</criterioControlloAltro>
	') else '' end,
	case when suini.esito_controllo = 'N' then concat('<flagIntenzionalita>',suini.intenzionalita,'</flagIntenzionalita>
	') else '' end,
	'<nomeRappresentante>',suini.nome_proprietario,'</nomeRappresentante>
	',
	'<tipoAllegato>',bdn.tipo_allegato,'</tipoAllegato>
	',
	case when suini.esito_controllo = 'N' then 
		concat('<prescrizioni>',suini.prescrizioni_descrizione,'</prescrizioni>
		', 
		case when suini.prescrizioni_descrizione <> '' then 
			concat('<dataScadPrescrizioni>',concat(suini.data_prescrizioni,'T00:00:00.000'),'</dataScadPrescrizioni>
			        <flagPrescrizioneEsitoBa>',suini.eseguite_prescrizioni,'</flagPrescrizioneEsitoBa>
			        ',	
				case when suini.data_verifica <> '' then 
					concat('<dataVerificaBa>',concat(suini.data_verifica,'T00:00:00.000'),'</dataVerificaBa>
						<secondoControllore>',suini.nome_controllore,'</secondoControllore>
						<nomeRappresentanteVer>',suini.nome_proprietario_prescrizioni_eseguite,'</nomeRappresentanteVer>
						'
					       ) else '' end
		               ) end
		      )else '' end,
	'<parametro>2019</parametro>
	<numClb>',suini.num_checklist,'</numClb>
	',
	case when suini.esito_controllo <> 'A' then concat('<veterinario>',suini.veterinario_ispettore,'</veterinario>
	',
	case when trim(suini.scrofe_morte_anno) <> '' then concat('<rScrofeMorteAnno>',suini.scrofe_morte_anno,'</rScrofeMorteAnno>
	') else '' end,
	case when trim(suini.suinetti_svezzati_anno) <> '' then concat('<rSuinettiSvezzatiAnno>',suini.suinetti_svezzati_anno,'</rSuinettiSvezzatiAnno>
	') else '' end,
	case when trim(suini.svezzamento_suini_presenti) <> '' then concat('<sSuiniPresenti>',suini.svezzamento_suini_presenti,'</sSuiniPresenti>
	') else '' end,
	case when trim(suini.svezzamento_suini_morti) <> '' then concat('<sSuiniMorti>',suini.svezzamento_suini_morti,'</sSuiniMorti>
	') else '' end,
	case when trim(suini.svezzamento_tutto) <> '' then concat('<sFlagTuttoPieno>',suini.svezzamento_tutto,'</sFlagTuttoPieno>
	') else '' end,
	case when trim(suini.svezzamento_num_cicli) <> '' then concat('<sNumCicli>',suini.svezzamento_num_cicli,'</sNumCicli>
	') else '' end,
	case when trim(suini.svezzamento_num_animali_ciclo) <> '' then concat('<sNumCapiCiclo>',suini.svezzamento_num_animali_ciclo,'</sNumCapiCiclo>
	') else '' end,
	case when trim(suini.svezzamento_fessurato) <> '' then concat('<sBoxTipo1>',suini.svezzamento_fessurato,'</sBoxTipo1>
	') else '' end,
	case when trim(suini.svezzamento_pieno) <> '' then concat('<sBoxTipo2>',suini.svezzamento_pieno,'</sBoxTipo2>
	') else '' end,
	case when trim(suini.svezzamento_pieno) <> '' then concat('<sBoxTipo2>',suini.svezzamento_pieno,'</sBoxTipo2>
	') else '' end,
	case when trim(suini.svezzamento_parzialmente_fessurato) <> '' then concat('<sBoxTipo3>',suini.svezzamento_parzialmente_fessurato,'</sBoxTipo3>
	') else '' end,
	case when trim(suini.svezzamento_parzialmente_grigliato) <> '' then concat('<sBoxTipo4>',suini.svezzamento_parzialmente_grigliato,'</sBoxTipo4>
	') else '' end,
	case when trim(suini.svezzamento_lettiera) <> '' then concat('<sBoxTipo5>',suini.svezzamento_lettiera,'</sBoxTipo5>
	') else '' end,
	case when trim(suini.ingrasso_suini_presenti) <> '' then concat('<iSuiniPresenti>',suini.ingrasso_suini_presenti,'</iSuiniPresenti>
	') else '' end,
	case when trim(suini.ingrasso_suini_morti) <> '' then concat('<iSuiniMorti>',suini.ingrasso_suini_morti,'</iSuiniMorti>
	') else '' end,
	case when trim(suini.ingrasso_tutto) <> '' then concat('<iFlagTuttoPieno>',suini.ingrasso_tutto,'</iFlagTuttoPieno>
	') else '' end,
	case when trim(suini.ingrasso_num_cicli) <> '' then concat('<iNumCicli>',suini.ingrasso_num_cicli,'</iNumCicli>
	') else '' end,
	case when trim(suini.ingrasso_num_animali_ciclo) <> '' then concat('<iNumCapiCiclo>',suini.ingrasso_num_animali_ciclo,'</iNumCapiCiclo>
	') else '' end,
	case when trim(suini.ingrasso_fessurato) <> '' then concat('<iBoxTipo1>',suini.ingrasso_fessurato,'</iBoxTipo1>
	') else '' end,
	case when trim(suini.ingrasso_pieno) <> '' then concat('<iBoxTipo2>',suini.ingrasso_pieno,'</iBoxTipo2>
	') else '' end,
	case when trim(suini.ingrasso_parzialmente_fessurato) <> '' then concat('<iBoxTipo3>',suini.ingrasso_parzialmente_fessurato,'</iBoxTipo3>
	') else '' end,
	case when trim(suini.ingrasso_parzialmente_grigliato) <> '' then concat('<iBoxTipo4>',suini.ingrasso_parzialmente_grigliato,'</iBoxTipo4>
	') else '' end,
	case when trim(suini.ingrasso_lettiera) <> '' then concat('<iBoxTipo5>',suini.ingrasso_lettiera,'</iBoxTipo5>
	') else '' end) else '' end,
	case when suini.esito_controllo <> 'A' then concat('<capiCodaTagliata>',suini.presenza_animali_coda_tagliata,'</capiCodaTagliata>
								<gruppiCodaTagliata>',suini.presenza_gruppi_animali_coda_tagliata,'</gruppiCodaTagliata>
								<produzioniTipiche>',suini.presenza_animali_tipiche,'</produzioniTipiche>
								<usoAnestetici>',suini.utilizzo_anestetici,'</usoAnestetici>
								<presenzaManuale>',suini.presenza_manuale,'</presenzaManuale>
							   ') else '' end,
	case when suini.esito_controllo <> 'A' then (select get_chiamata_ws_insert_ba_v6_requisiti from public.get_chiamata_ws_insert_ba_v6_requisiti(_id)) else '' END,
	CASE     when suini.esito_controllo <> 'A' then (select get_chiamata_ws_insert_ba_v6_suini_box from public.get_chiamata_ws_insert_ba_v6_suini_box(_id)) else ''
	end,'
            </dettagliochecklist>') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v6_suini suini on suini.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
   
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v6_suini_dettaglio_checklist(integer)
  OWNER TO postgres;

  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_suini_box(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
BEGIN
     
   select string_agg(concat('<box>
               <clBoxId>0</clBoxId>
               <clbId>0</clbId>
               <boxNum>',numero,'</boxNum>
               <larghezza>',larghezza,'</larghezza>
               <lunghezza>',lunghezza,'</lunghezza>
               <numeroAnimali>',animali,'</numeroAnimali>
               <peso>',peso,'</peso>
               <categoria>',categoria,'</categoria>
               <flagPavimento>',pavimento,'</flagPavimento>
               <travetti>',travetti,'</travetti>
               <fessure>',fessure,'</fessure>
               <regolare>',regolare,'</regolare>
            </box>'),'
            ') into msg 
   from
  chk_bns_boxes_v6 where trashed_date  is null and id_chk_bns_mod_ist = _id
  and (numero <> '' or larghezza <> '' or lunghezza <> '' or animali <> '' or peso <> '' or categoria <> '' or pavimento <> '' or travetti <> '' or fessure <> '' or regolare <> '') ;
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v6_suini_box(integer)
  OWNER TO postgres;
  
 -- Function: public.get_chiamata_ws_insert_ba_sa(text, text, integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_sa(text, text, integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_sa(
    _username_ws_bdn text,
    _password_ws_bdn text,
    _id integer)
  RETURNS text AS
$BODY$
   DECLARE
id_tipo_chk integer;
result text;
BEGIN

     select id_alleg into id_tipo_chk from chk_bns_mod_ist where id = _id;
     raise info '%', id_tipo_chk; 
     IF id_tipo_chk = 24  THEN
	select get_chiamata_ws_insert_ba_v5_suini into result from get_chiamata_ws_insert_ba_v5_suini(_username_ws_bdn, _password_ws_bdn, _id);
     ELSIF id_tipo_chk = 23 THEN
	select get_chiamata_ws_insert_sa_v4 into result from get_chiamata_ws_insert_sa_v4(_username_ws_bdn, _password_ws_bdn, _id);
     ELSIF id_tipo_chk = 29 THEN
	select get_chiamata_ws_insert_ba_v5_bovini into result from public.get_chiamata_ws_insert_ba_v5_bovini(_username_ws_bdn, _password_ws_bdn, _id);	
     ELSIF id_tipo_chk = 30 THEN	
	select get_chiamata_ws_insert_ba_v5_vitelli into result from public.get_chiamata_ws_insert_ba_v5_vitelli(_username_ws_bdn, _password_ws_bdn, _id);	
     ELSIF id_tipo_chk in (25, 27, 28) THEN 
     	select get_chiamata_ws_insert_ba_v4 into result from public.get_chiamata_ws_insert_ba_v4(_username_ws_bdn, _password_ws_bdn, _id);
     ELSIF id_tipo_chk in (31) THEN 
     	select get_chiamata_ws_insert_ba_v5_gallus into result from public.get_chiamata_ws_insert_ba_v5_gallus(_username_ws_bdn, _password_ws_bdn, _id);
     ELSIF id_tipo_chk in (32) THEN 
     	select get_chiamata_ws_insert_ba_v6_ovicaprini into result from public.get_chiamata_ws_insert_ba_v6_ovicaprini(_username_ws_bdn, _password_ws_bdn, _id);
     ELSIF id_tipo_chk in (33) THEN 
     	select get_chiamata_ws_insert_ba_v6_bovini into result from public.get_chiamata_ws_insert_ba_v6_bovini(_username_ws_bdn, _password_ws_bdn, _id);
     ELSIF id_tipo_chk in (34) THEN 
     	select get_chiamata_ws_insert_ba_v6_suini into result from public.get_chiamata_ws_insert_ba_v6_suini(_username_ws_bdn, _password_ws_bdn, _id);
     ELSIF id_tipo_chk in (35) THEN 
     	select get_chiamata_ws_insert_ba_v6_vitelli into result from public.get_chiamata_ws_insert_ba_v6_vitelli(_username_ws_bdn, _password_ws_bdn, _id);
     ELSIF id_tipo_chk in (36) THEN 
     	select get_chiamata_ws_insert_ba_v6_gallus into result from public.get_chiamata_ws_insert_ba_v6_gallus(_username_ws_bdn, _password_ws_bdn, _id);
     ELSIF id_tipo_chk in (37, 38) THEN 
     	select get_chiamata_ws_insert_ba_v3 into result from public.get_chiamata_ws_insert_ba_v3(_username_ws_bdn, _password_ws_bdn, _id);
     ELSE 
	result :='';
     END IF;
     
    RETURN result;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_sa(text, text, integer)
  OWNER TO postgres;




  -- bovini
  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_bovini(
    _username_ws_bdn text,
    _password_ws_bdn text,
    _id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
BEGIN

  msg := concat('<?xml version="1.0" encoding="UTF-8"?>
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">','
',
(select get_chiamata_ws_insert_ba_sa_header from get_chiamata_ws_insert_ba_sa_header(_username_ws_bdn, _password_ws_bdn)),'
',
'<S:Body>
      <ns2:insertControlliallevamentiBA xmlns:ns2="http://ws.izs.it">','
      ',(select get_chiamata_ws_insert_ba_v6_bovini_controlli_allevamenti from get_chiamata_ws_insert_ba_v6_bovini_controlli_allevamenti(_id)),'
      ',(select get_chiamata_ws_insert_ba_v6_bovini_dettaglio_checklist from get_chiamata_ws_insert_ba_v6_bovini_dettaglio_checklist(_id)),'
      </ns2:insertControlliallevamentiBA>
      </S:Body> 
      </S:Envelope>');
    
 RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v6_bovini(text, text, integer)
  OWNER TO postgres;

  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_bovini_controlli_allevamenti(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
tag_preavviso text;
BEGIN

select 
concat('<controlliallevamenti>
	  <caId>0</caId>
             <dtControllo>', to_char(data_controllo, 'YYYY-MM-DD"T"00:00:00.000'),'</dtControllo>
            <aslCodice>',codice_asl,'</aslCodice>
            <aziendaCodice>',codice_azienda,'</aziendaCodice>
            <speCodice>',bdn.codice_specie_chk_bns,'</speCodice>
            <allevIdFiscale>',id_fiscale_allevamento,'</allevIdFiscale>
            <ocCodice>',bdn.occodice,'</ocCodice>
            <flagEsito>',bovini.esito_controllo,'</flagEsito>
            <flagVitelli>',bdn.flag_vitelli,'</flagVitelli>
            <flagExtrapiano>',bovini.extrapiano,'</flagExtrapiano>
            <detenIdFiscale>',id_fiscale_detentore,'</detenIdFiscale>
            <flagPreavviso>',bdn.flag_preavviso,'</flagPreavviso>
	    ',case when bdn.flag_preavviso <> 'N' then concat('<dataPreavviso>',to_char(bdn.data_preavviso, 'YYYY-MM-DD"T"00:00:00.000'),'</dataPreavviso>') else '' end,'            
            <primoControllore>',bovini.nome_controllore,'</primoControllore>
            <tipoControllo>BA</tipoControllo>
            <flagCopiaCheckList>',bdn.flag_checklist,'</flagCopiaCheckList>
	    ',case when bovini.esito_controllo = 'N' then concat(
	    '<sanzBloccoMov>',bovini.sanz_blocco,'</sanzBloccoMov>
            <sanzAmministrativa>',bovini.sanz_amministrativa,'</sanzAmministrativa>
            <sanzSequestro>',bovini.sanz_sequestro,'</sanzSequestro>
            <sanzAbbattimentoCapi>',bovini.sanz_abbattimento,'</sanzAbbattimentoCapi>
            <sanzInformativa>',bovini.sanz_informativa,'</sanzInformativa>
            <sanzAltro>',bovini.sanz_altro,'</sanzAltro>
            ',case when bovini.sanz_altro <> '0' then concat('<sanzAltroDesc>',bovini.sanz_altro_desc,'</sanzAltroDesc>')
	    else '' end,'
            <noteControllore>',bovini.note_controllore,'</noteControllore>
            <noteDetentore>',bovini.note_proprietario,'</noteDetentore>')
	    else '' end,'             
	    ',case when bovini.esito_controllo <> 'A' then 
	    concat('<flagEvidenze>',bovini.evidenze,'</flagEvidenze>')
	    else '' end,
	    case when bovini.evidenze = 'S' then 
	    concat('<evidenzaIr>',bovini.evidenze_ir,'</evidenzaIr>
            <evidenzaSv>',bovini.evidenze_sv,'</evidenzaSv>
            <evidenzaSa>',bovini.evidenze_tse,'</evidenzaSa>')
            else '' end,'
            <flagCondizionalita>',bovini.appartenente_condizionalita,'</flagCondizionalita>
             ',case when bovini.data_chiusura_relazione <> '' then concat('<dataChiusura>',concat(bovini.data_chiusura_relazione,'T00:00.000'),'</dataChiusura>')
	    else '' end,'    
         </controlliallevamenti>') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v6_bovini_bufalini bovini on bovini.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
     
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v6_bovini_controlli_allevamenti(integer)
  OWNER TO postgres;

  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_bovini_dettaglio_checklist(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
BEGIN
select 
concat(
'<dettagliochecklist>
	<clbId>0</clbId>
	<flagVitelli>',bdn.flag_vitelli,'</flagVitelli>
	<critCodice>',bovini.criteri_utilizzati,'</critCodice>
	',
	case when bovini.criteri_utilizzati = '997' then concat('<criterioControlloAltro>',bovini.criteri_utilizzati_altro_descrizione,'</criterioControlloAltro>
	') else '' end,
	case when bovini.esito_controllo = 'N' then concat('<flagIntenzionalita>',bovini.intenzionalita,'</flagIntenzionalita>
	') else '' end,
	'<nomeRappresentante>',bovini.nome_proprietario,'</nomeRappresentante>
	',
	'<tipoAllegato>',bdn.tipo_allegato,'</tipoAllegato>
	',
	case when bovini.esito_controllo = 'N' then 
		concat('<prescrizioni>',bovini.prescrizioni_descrizione,'</prescrizioni>
		', 
		case when bovini.prescrizioni_descrizione <> '' then 
			concat('<dataScadPrescrizioni>',concat(bovini.data_prescrizioni,'T00:00:00.000'),'</dataScadPrescrizioni>
			        <flagPrescrizioneEsitoBa>',bovini.eseguite_prescrizioni,'</flagPrescrizioneEsitoBa>
			        ',	
				case when bovini.data_verifica <> '' then 
					concat('<dataVerificaBa>',concat(bovini.data_verifica,'T00:00:00.000'),'</dataVerificaBa>
						<secondoControllore>',bovini.nome_controllore,'</secondoControllore>
						<nomeRappresentanteVer>',bovini.nome_proprietario_prescrizioni_eseguite,'</nomeRappresentanteVer>
						'
					       ) else '' end
		               ) end
		      )else '' end,
	'<parametro>2020</parametro>
	<numClb>',bovini.num_checklist,'</numClb>
	',
	case when bovini.esito_controllo <> 'A' then concat('<veterinario>',bovini.veterinario_ispettore,'</veterinario>
	', 
	case when trim(bovini.num_bovine_lattazione) <> '' then concat('<numCapiLattazione>',bovini.num_bovine_lattazione,'</numCapiLattazione>
	') else '' end,
	case when trim(bovini.num_bovine_asciutta) <> '' then concat('<numCapiAsciutta>',bovini.num_bovine_asciutta,'</numCapiAsciutta>
	') else '' end,
	case when trim(bovini.num_manze) <> '' then concat('<numManze>',bovini.num_manze,'</numManze>
	') else '' end,
	case when trim(bovini.num_bovini_ingrasso) <> '' then concat('<numIngrasso>',bovini.num_bovini_ingrasso,'</numIngrasso>
	') else '' end,
	case when trim(bovini.num_tori) <> '' then concat('<numTori>',bovini.num_tori,'</numTori>
	') else '' end,
	case when trim(bovini.prod_latte) <> '' then concat('<kgLatte>',bovini.prod_latte,'</kgLatte>
	') else '' end,
	case when trim(bovini.tipo_stabulazione) <> '' then concat('<flagStabulazione>',bovini.tipo_stabulazione,'</flagStabulazione>
	') else '' 
							end) else '' end,
	case when bovini.esito_controllo <> 'A' then concat('<presenzaManuale>',bovini.presenza_manuale,'</presenzaManuale>
	') else '' end,
	case when bovini.esito_controllo <> 'A' then (select get_chiamata_ws_insert_ba_v6_requisiti from public.get_chiamata_ws_insert_ba_v6_requisiti(_id)) else '' 
	END,'
            </dettagliochecklist>
            ') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v6_bovini_bufalini bovini on bovini.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
   
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v6_bovini_dettaglio_checklist(integer)
  OWNER TO postgres;

  -- vitelli
  

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_vitelli(
    _username_ws_bdn text,
    _password_ws_bdn text,
    _id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
BEGIN

  msg := concat('<?xml version="1.0" encoding="UTF-8"?>
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">','
',
(select get_chiamata_ws_insert_ba_sa_header from get_chiamata_ws_insert_ba_sa_header(_username_ws_bdn, _password_ws_bdn)),'
',
'<S:Body>
      <ns2:insertControlliallevamentiBA xmlns:ns2="http://ws.izs.it">','
      ',(select get_chiamata_ws_insert_ba_v6_vitelli_controlli_allevamenti from get_chiamata_ws_insert_ba_v6_vitelli_controlli_allevamenti(_id)),'
      ',(select get_chiamata_ws_insert_ba_v6_vitelli_dettaglio_checklist from get_chiamata_ws_insert_ba_v6_vitelli_dettaglio_checklist(_id)),'
      </ns2:insertControlliallevamentiBA>
      </S:Body> 
      </S:Envelope>');
    
 RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v6_vitelli(text, text, integer)
  OWNER TO postgres;

  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_vitelli_controlli_allevamenti(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
tag_preavviso text;
BEGIN

select 
concat('<controlliallevamenti>
	  <caId>0</caId>
             <dtControllo>', to_char(data_controllo, 'YYYY-MM-DD"T"00:00:00.000'),'</dtControllo>
            <aslCodice>',codice_asl,'</aslCodice>
            <aziendaCodice>',codice_azienda,'</aziendaCodice>
            <speCodice>',bdn.codice_specie_chk_bns,'</speCodice>
            <allevIdFiscale>',id_fiscale_allevamento,'</allevIdFiscale>
            <ocCodice>',bdn.occodice,'</ocCodice>
            <flagEsito>',vitelli.esito_controllo,'</flagEsito>
            <flagVitelli>',bdn.flag_vitelli,'</flagVitelli>
            <flagExtrapiano>',vitelli.extrapiano,'</flagExtrapiano>
            <detenIdFiscale>',id_fiscale_detentore,'</detenIdFiscale>
            <flagPreavviso>',bdn.flag_preavviso,'</flagPreavviso>
	    ',case when bdn.flag_preavviso <> 'N' then concat('<dataPreavviso>',to_char(bdn.data_preavviso, 'YYYY-MM-DD"T"00:00:00.000'),'</dataPreavviso>') else '' end,'            
            <primoControllore>',vitelli.nome_controllore,'</primoControllore>
            <tipoControllo>BA</tipoControllo>
            <flagCopiaCheckList>',bdn.flag_checklist,'</flagCopiaCheckList>
	    ',case when vitelli.esito_controllo = 'N' then concat(
	    '<sanzBloccoMov>',vitelli.sanz_blocco,'</sanzBloccoMov>
            <sanzAmministrativa>',vitelli.sanz_amministrativa,'</sanzAmministrativa>
            <sanzSequestro>',vitelli.sanz_sequestro,'</sanzSequestro>
            <sanzAbbattimentoCapi>',vitelli.sanz_abbattimento,'</sanzAbbattimentoCapi>
            <sanzInformativa>',vitelli.sanz_informativa,'</sanzInformativa>
            <sanzAltro>',vitelli.sanz_altro,'</sanzAltro>
            ',case when vitelli.sanz_altro <> '0' then concat('<sanzAltroDesc>',vitelli.sanz_altro_desc,'</sanzAltroDesc>')
	    else '' end,'
            <noteControllore>',vitelli.note_controllore,'</noteControllore>
            <noteDetentore>',vitelli.note_proprietario,'</noteDetentore>')
	    else '' end,'             
	    ',case when vitelli.esito_controllo <> 'A' then 
	    concat('<flagEvidenze>',vitelli.evidenze,'</flagEvidenze>')
	    else '' end,
	    case when vitelli.evidenze = 'S' then 
	    concat('<evidenzaIr>',vitelli.evidenze_ir,'</evidenzaIr>
            <evidenzaSv>',vitelli.evidenze_sv,'</evidenzaSv>
            <evidenzaSa>',vitelli.evidenze_tse,'</evidenzaSa>')
            else '' end,'
            <flagCondizionalita>',vitelli.appartenente_condizionalita,'</flagCondizionalita>
             ',case when vitelli.data_chiusura_relazione <> '' then concat('<dataChiusura>',concat(vitelli.data_chiusura_relazione,'T00:00.000'),'</dataChiusura>')
	    else '' end,'    
         </controlliallevamenti>') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v6_vitelli vitelli on vitelli.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
     
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v6_vitelli_controlli_allevamenti(integer)
  OWNER TO postgres;

  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_vitelli_dettaglio_checklist(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
BEGIN
select 
concat(
'<dettagliochecklist>
	<clbId>0</clbId>
	<flagVitelli>',bdn.flag_vitelli,'</flagVitelli>
	<critCodice>',vitelli.criteri_utilizzati,'</critCodice>
	',
	case when vitelli.criteri_utilizzati = '997' then concat('<criterioControlloAltro>',vitelli.criteri_utilizzati_altro_descrizione,'</criterioControlloAltro>
	') else '' end,
	case when vitelli.esito_controllo = 'N' then concat('<flagIntenzionalita>',vitelli.intenzionalita,'</flagIntenzionalita>
	') else '' end,
	'<nomeRappresentante>',vitelli.nome_proprietario,'</nomeRappresentante>
	',
	'<tipoAllegato>',bdn.tipo_allegato,'</tipoAllegato>
	',
	case when vitelli.esito_controllo = 'N' then 
		concat('<prescrizioni>',vitelli.prescrizioni_descrizione,'</prescrizioni>
		', 
		case when vitelli.prescrizioni_descrizione <> '' then 
			concat('<dataScadPrescrizioni>',concat(vitelli.data_prescrizioni,'T00:00:00.000'),'</dataScadPrescrizioni>
			        <flagPrescrizioneEsitoBa>',vitelli.eseguite_prescrizioni,'</flagPrescrizioneEsitoBa>
			        ',	
				case when vitelli.data_verifica <> '' then 
					concat('<dataVerificaBa>',concat(vitelli.data_verifica,'T00:00:00.000'),'</dataVerificaBa>
						<secondoControllore>',vitelli.nome_controllore,'</secondoControllore>
						<nomeRappresentanteVer>',vitelli.nome_proprietario_prescrizioni_eseguite,'</nomeRappresentanteVer>
						'
					       ) else '' end
		               ) end
		      )else '' end,
	'<parametro>2021</parametro>
	<numClb>',vitelli.num_checklist,'</numClb>
	',
	case when vitelli.esito_controllo <> 'A' then concat('<veterinario>',vitelli.veterinario_ispettore,'</veterinario>
	') else '' end, 
	case when vitelli.esito_controllo <> 'A' then concat('<presenzaManuale>',vitelli.presenza_manuale,'</presenzaManuale>
	') else '' end,
	case when vitelli.esito_controllo <> 'A' then concat('<flagImpiantiElettrici>',vitelli.flag_impianti_elettrici,'</flagImpiantiElettrici>
	',
	case when vitelli.flag_impianti_elettrici = 'S' then concat('<flagDichiarazioneConformita>',vitelli.flag_dichiarazione_conformita,'</flagDichiarazioneConformita>
	') else '' end) else '' end,
	case when vitelli.esito_controllo <> 'A' then (select get_chiamata_ws_insert_ba_v6_requisiti from public.get_chiamata_ws_insert_ba_v6_requisiti(_id)) else '' 
	END,'
            </dettagliochecklist>
            ') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v6_vitelli vitelli on vitelli.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
   
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v6_vitelli_dettaglio_checklist(integer)
  OWNER TO postgres;

  -- gallus
  CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_gallus(
    _username_ws_bdn text,
    _password_ws_bdn text,
    _id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
BEGIN

  msg := concat('<?xml version="1.0" encoding="UTF-8"?>
<S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/">','
',
(select get_chiamata_ws_insert_ba_sa_header from get_chiamata_ws_insert_ba_sa_header(_username_ws_bdn, _password_ws_bdn)),'
',
'<S:Body>
      <ns2:insertControlliallevamentiBA xmlns:ns2="http://ws.izs.it">','
      ',(select get_chiamata_ws_insert_ba_v6_gallus_controlli_allevamenti from get_chiamata_ws_insert_ba_v6_gallus_controlli_allevamenti(_id)),'
      ',(select get_chiamata_ws_insert_ba_v6_gallus_dettaglio_checklist from get_chiamata_ws_insert_ba_v6_gallus_dettaglio_checklist(_id)),'
      </ns2:insertControlliallevamentiBA>
      </S:Body> 
      </S:Envelope>');
    
    
 RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v6_gallus(text, text, integer)
  OWNER TO postgres;

  CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_gallus_dettaglio_checklist(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
BEGIN

select 
concat(
'<dettagliochecklist>
	<clbId>0</clbId>
	<flagVitelli>N</flagVitelli>
	<critCodice>',galline.criteri_utilizzati,'</critCodice>
	',
	case when galline.criteri_utilizzati = '997' then concat('<criterioControlloAltro>',galline.criteri_utilizzati_altro_descrizione,'</criterioControlloAltro>
	') else '' end,
	case when galline.esito_controllo = 'N' then concat('<flagIntenzionalita>',galline.intenzionalita,'</flagIntenzionalita>
	') else '' end,
	'<nomeRappresentante>',galline.nome_proprietario,'</nomeRappresentante>
	',
	'<tipoAllegato>',bdn.tipo_allegato,'</tipoAllegato>
	',
	'<numUovaAnno>',galline.num_uova_anno,'</numUovaAnno>
	',
	case when galline.esito_controllo <> 'A' then 
		concat('<flagSelImbal>',galline.selezione_imballaggio,'</flagSelImbal>
	') else '' end, 

	case when galline.selezione_imballaggio = 'S' then 
		concat('<indirizzoSelImbal >',galline.selezione_imballaggio_destinazione,'</indirizzoSelImbal>
		') else '' end,
	'<mutaInAllev>',galline.muta,'</mutaInAllev>
	',
	case when galline.esito_controllo = 'N' then 
		concat('<prescrizioni>',galline.prescrizioni_descrizione,'</prescrizioni>
		') else '' end, 
		case when galline.prescrizioni_descrizione <> '' then 
			concat('<dataScadPrescrizioni>',concat(galline.data_prescrizioni,'T00:00:00.000'),'</dataScadPrescrizioni>
			        <flagPrescrizioneEsitoBa>',galline.eseguite_prescrizioni,'</flagPrescrizioneEsitoBa>
			        ',	
				case when galline.data_verifica <> '' then 
					concat('<dataVerificaBa>',concat(galline.data_verifica,'T00:00:00.000'),'</dataVerificaBa>
						<secondoControllore>',galline.nome_controllore,'</secondoControllore>
						<nomeRappresentanteVer>',galline.nome_proprietario_prescrizioni_eseguite,'</nomeRappresentanteVer>
						'
					       ) else '' end
		               --) end
		      )else '' end,
	'<parametro>2021</parametro>
	<numClb>',galline.num_checklist,'</numClb>
	',
	case when galline.esito_controllo <> 'A' then concat('<ibridoRazza>',galline.ibrido_razza,'</ibridoRazza>
	') else '' end, 
	case when galline.esito_controllo <> 'A' then concat('<veterinario>',galline.veterinario_ispettore,'</veterinario>
	') else '' end, 
	case when galline.esito_controllo <> 'A' then concat('<presenzaManuale>',galline.presenza_manuale,'</presenzaManuale>
	') else '' end,
	case when galline.esito_controllo <> 'A' then (select get_chiamata_ws_insert_ba_v6_requisiti from public.get_chiamata_ws_insert_ba_v6_requisiti(_id)) else '' 
	END,'
            </dettagliochecklist>
            ') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v6_galline galline on galline.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
   

    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v6_gallus_dettaglio_checklist(integer)
  OWNER TO postgres;

  

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v6_gallus_controlli_allevamenti(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
tag_preavviso text;
BEGIN
select 
concat('<controlliallevamenti>
	  <caId>0</caId>
           <dtControllo>', to_char(data_controllo, 'YYYY-MM-DD"T"00:00:00.000'),'</dtControllo>
            <aslCodice>',codice_asl,'</aslCodice>
            <aziendaCodice>',codice_azienda,'</aziendaCodice>
            <speCodice>',bdn.codice_specie_chk_bns,'</speCodice>
            <allevIdFiscale>',id_fiscale_allevamento,'</allevIdFiscale>
	    <tipoProdCodice>',tipo_produzione_ba,'</tipoProdCodice>
	    <tipoAllevCodice>',tipo_allevamento_codice,'</tipoAllevCodice>
	    <orientamentoCodice>',codice_orientamento_ba,'</orientamentoCodice>
	    <flagFaseProduttiva>D</flagFaseProduttiva>	    
            <ocCodice>',bdn.occodice,'</ocCodice>
            <flagEsito>',galline.esito_controllo,'</flagEsito>
            <flagVitelli>N</flagVitelli> 
            <flagExtrapiano>',galline.extrapiano,'</flagExtrapiano>
            <detenIdFiscale>',id_fiscale_detentore,'</detenIdFiscale>
            <flagPreavviso>',bdn.flag_preavviso,'</flagPreavviso>
	    ',case when bdn.flag_preavviso <> 'N' then concat('<dataPreavviso>',to_char(bdn.data_preavviso, 'YYYY-MM-DD"T"00:00:00.000'),'</dataPreavviso>') else '' end,'            
            <primoControllore>',galline.nome_controllore,'</primoControllore>
            <tipoControllo>BA</tipoControllo>
            <flagCopiaCheckList>',bdn.flag_checklist,'</flagCopiaCheckList>
	    ',case when galline.esito_controllo = 'N' then concat(
	    '<sanzBloccoMov>',galline.sanz_blocco,'</sanzBloccoMov>
            <sanzAmministrativa>',galline.sanz_amministrativa,'</sanzAmministrativa>
            <sanzSequestro>',galline.sanz_sequestro,'</sanzSequestro>
            <sanzAbbattimentoCapi>',galline.sanz_abbattimento,'</sanzAbbattimentoCapi>
            <sanzInformativa>',galline.sanz_informativa,'</sanzInformativa>
            <sanzAltro>',galline.sanz_altro,'</sanzAltro>
            ',case when galline.sanz_altro <> '0' then concat('<sanzAltroDesc>',galline.sanz_altro_desc,'</sanzAltroDesc>')
	    else '' end,'
            <noteControllore>',galline.note_controllore,'</noteControllore>
            <noteDetentore>',galline.note_proprietario,'</noteDetentore>')
	    else '' end,'             
	    ',case when galline.esito_controllo <> 'A' then 
	    concat('<flagEvidenze>',galline.evidenze,'</flagEvidenze>')
	    else '' end,
	    case when galline.evidenze = 'S' then 
	    concat('<evidenzaIr>',galline.evidenze_ir,'</evidenzaIr>
            <evidenzaSv>',galline.evidenze_sv,'</evidenzaSv>
            <evidenzaSa>',galline.evidenze_tse,'</evidenzaSa>')
            else '' end,'
            <flagCondizionalita>',galline.appartenente_condizionalita,'</flagCondizionalita>
             ',case when galline.data_chiusura_relazione <> '' then concat('<dataChiusura>',concat(galline.data_chiusura_relazione,'T00:00.000'),'</dataChiusura>')
	    else '' end,'    
         </controlliallevamenti>') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v6_galline galline on galline.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;

     
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v6_gallus_controlli_allevamenti(integer)
  OWNER TO postgres;
