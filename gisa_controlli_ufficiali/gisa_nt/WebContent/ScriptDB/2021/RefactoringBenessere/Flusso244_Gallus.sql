-- Function: public.get_chiamata_ws_insert_ba_v5_gallus(text, text, integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v5_gallus(text, text, integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v5_gallus(
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
      ',(select get_chiamata_ws_insert_ba_v5_gallus_controlli_allevamenti from get_chiamata_ws_insert_ba_v5_gallus_controlli_allevamenti(_id)),'
      ',(select get_chiamata_ws_insert_ba_v5_gallus_dettaglio_checklist from get_chiamata_ws_insert_ba_v5_gallus_dettaglio_checklist(_id)),'
      </ns2:insertControlliallevamentiBA>
      </S:Body> 
      </S:Envelope>');
    
    
 RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v5_gallus(text, text, integer)
  OWNER TO postgres;



CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v5_gallus_controlli_allevamenti(_id integer)
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
left join bdn_checklist_v5_galline galline on galline.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;

     
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v5_gallus_controlli_allevamenti(integer)
  OWNER TO postgres;

-- Function: public.get_chiamata_ws_insert_ba_v5_gallus_dettaglio_checklist(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v5_gallus_dettaglio_checklist(integer);

-- Function: public.get_chiamata_ws_insert_ba_v5_gallus_dettaglio_checklist(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v5_gallus_dettaglio_checklist(integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v5_gallus_dettaglio_checklist(_id integer)
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
	case when galline.esito_controllo <> 'A' then concat('<numPianiGabbie>',galline.num_piani_gabbie,'</numPianiGabbie>
	') else '' end, 
	case when galline.esito_controllo <> 'A' then concat('<numGallineGabbia>',galline.num_galline_gabbia,'</numGallineGabbia>
	') else '' end, 
	case when galline.esito_controllo <> 'A' then concat('<flagSeBatteria>',galline.batteria,'</flagSeBatteria>
	') else '' end, 
	case when galline.esito_controllo <> 'A' and galline.batteria = 'S' then 
		concat('<flagBattPianoUnico>',galline.batteria_unico_piano,'</flagBattPianoUnico>
			<flagBattPianoSfasato>', galline.batteria_piani_sfasati,'</flagBattPianoSfasato>
			<flagBattPianoSovra>',galline.batteria_piani_sovrapposti,'</flagBattPianoSovra>
			'
				) else '' end,
	case when galline.esito_controllo <> 'A' then concat('<veterinario>',galline.veterinario_ispettore,'</veterinario>
	') else '' end, 
	case when galline.esito_controllo <> 'A' then concat('<presenzaManuale>',galline.presenza_manuale,'</presenzaManuale>
	') else '' end,
	case when galline.esito_controllo <> 'A' then (select get_chiamata_ws_insert_ba_v5_requisiti from public.get_chiamata_ws_insert_ba_v5_requisiti(_id)) else '' 
	END,'
            </dettagliochecklist>
            ') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v5_galline galline on galline.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
   

    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v5_gallus_dettaglio_checklist(integer)
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
