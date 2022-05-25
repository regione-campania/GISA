
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v4(
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
      ',(select get_chiamata_ws_insert_ba_v4_controlli_allevamenti from get_chiamata_ws_insert_ba_v4_controlli_allevamenti(_id)),'
      ',(select get_chiamata_ws_insert_ba_v4_dettaglio_checklist from get_chiamata_ws_insert_ba_v4_dettaglio_checklist(_id)),'
      </ns2:insertControlliallevamentiBA>
      </S:Body> 
      </S:Envelope>');
      
 RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v4(text, text, integer)
  OWNER TO postgres;

	
-- Function: public.get_chiamata_ws_insert_ba_v4_capannoni(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v4_capannoni(integer);
-- Function: public.get_chiamata_ws_insert_ba_v4_capannoni(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v4_capannoni(integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v4_capannoni(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
tag_preavviso text;
BEGIN

select string_agg(concat('<capannoni>
               <clBoxId>0</clBoxId>
               <clbId>0</clbId>
               <identificativo>',numero,'</identificativo>
               <capacita>',capacita,'</capacita>
               <animaliPresenti>',animali,'</animaliPresenti>
               <numeroBox>',num_totale_box,'</numeroBox>
               <numeroBoxAttivi>',num_totale_box_attivi,'</numeroBoxAttivi>
               <flagIspezionato>',ispezionato,'</flagIspezionato>
            </capannoni>'),'
            ') into msg 
   from
  chk_bns_capannoni_v4 where trashed_date is null and id_chk_bns_mod_ist = _id;
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v4_capannoni(integer)
  OWNER TO postgres;



CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v4_controlli_allevamenti(_id integer)
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
            <flagEsito>',v4.esito_controllo,'</flagEsito>
            <flagVitelli>',bdn.flag_vitelli,'</flagVitelli>
            <flagExtrapiano>',v4.extrapiano,'</flagExtrapiano>
            <detenIdFiscale>',id_fiscale_detentore,'</detenIdFiscale>
            ', case when bdn.codice_specie_chk_bns in ('0144','30','32','33','34','35','36','37','38','40','41','42','46','0128') then concat('<tipoProdCodice>',bdn.tipo_produzione_ba,'</tipoProdCodice>
            <tipoAllevCodice>',bdn.tipo_allevamento_codice,'</tipoAllevCodice>
            <orientamentoCodice>',bdn.codice_orientamento_ba,'</orientamentoCodice>') else '' end,
	     case when bdn.codice_specie_chk_bns in ('0144','30','32','33','34','35','36','37','38','40','41','42','46') then concat('<flagFaseProduttiva>',v4.flag_fase_produttiva,'</flagFaseProduttiva>
            ') else '' end,
            '<flagPreavviso>',bdn.flag_preavviso,'</flagPreavviso>
	    ',case when bdn.flag_preavviso <> 'N' then concat('<dataPreavviso>',to_char(bdn.data_preavviso, 'YYYY-MM-DD"T"00:00:00.000'),'</dataPreavviso>
	    ') else '' end,'            
            <primoControllore>',v4.nome_controllore,'</primoControllore>
            <tipoControllo>BA</tipoControllo>
            <flagCopiaCheckList>',bdn.flag_checklist,'</flagCopiaCheckList>

	    ',case when v4.esito_controllo = 'N' then concat(
	    '<sanzBloccoMov>',v4.sanz_blocco,'</sanzBloccoMov>
            <sanzAmministrativa>',v4.sanz_amministrativa,'</sanzAmministrativa>
            <sanzSequestro>',v4.sanz_sequestro,'</sanzSequestro>
            <sanzAbbattimentoCapi>',v4.sanz_abbattimento,'</sanzAbbattimentoCapi>
            <sanzAltro>',v4.sanz_altro,'</sanzAltro>
            ',case when v4.sanz_altro <> '0' then concat('<sanzAltroDesc>',v4.sanz_altro_desc,'</sanzAltroDesc>
            ')
	    else '' end,'
            <noteControllore>',v4.note_controllore,'</noteControllore>
            <noteDetentore>',v4.note_proprietario,'</noteDetentore>
            <flagEvidenze>',v4.evidenze,'</flagEvidenze>
            ',case when v4.evidenze = 'S' then 
						concat('<evidenzaIr>',v4.evidenze_ir,'</evidenzaIr>
						<evidenzaSv>',v4.evidenze_sv,'</evidenzaSv>
						<evidenzaSa>',v4.evidenze_tse,'</evidenzaSa>'
						)
						else '' end) 
	    else '' end,'
            <flagCondizionalita>',v4.appartenente_condizionalita,'</flagCondizionalita>
             ',case when v4.data_chiusura_relazione <> '' then concat('<dataChiusura>',concat(v4.data_chiusura_relazione,'T00:00.000'),'</dataChiusura>
             ')
	    else '' end,'    
         </controlliallevamenti>
         ') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v4 v4 on v4.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;

    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v4_controlli_allevamenti(integer)
  OWNER TO postgres;

  -- Function: public.get_chiamata_ws_insert_ba_v4_dettaglio_checklist(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v4_dettaglio_checklist(integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v4_dettaglio_checklist(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
tag_preavviso text;
BEGIN
select 
concat(
'<dettagliochecklist>
	<clbId>0</clbId>
	<flagVitelli>',bdn.flag_vitelli,'</flagVitelli>
	<critCodice>',v4.criteri_utilizzati,'</critCodice>
	',
	case when v4.criteri_utilizzati = '997' then concat('<criterioControlloAltro>',v4.criteri_utilizzati_altro_descrizione,'</criterioControlloAltro>
	') else '' end,
	case when v4.esito_controllo = 'N' then concat('<flagIntenzionalita>',v4.intenzionalita,'</flagIntenzionalita>
	') else '' end,
	'<nomeRappresentante>',v4.nome_proprietario,'</nomeRappresentante>
	',
	'<tipoAllegato>',bdn.tipo_allegato,'</tipoAllegato>
	',
	case when v4.esito_controllo <> 'A' then concat('<numCapannoni>',v4.num_capannoni,'</numCapannoni>
	<numCapannoniAttivi>',v4.num_capannoni_attivi,'</numCapannoniAttivi>',
		case when bdn.tipo_allegato = '1' then concat('<numUovaAnno>',v4.num_uova_anno,'</numUovaAnno>
		<flagSelImbal>',v4.selezione_imballaggio,'</flagSelImbal>
		<indirizzoSelImbal>',v4.destinazione_imballaggio,'</indirizzoSelImbal>
		<mutaInAllev>',v4.muta,'</mutaInAllev>') else '' end,
		case when bdn.tipo_allegato = '3' then concat('<numVitelliMax>',v4.num_vitelli_max,'</numVitelliMax>
		<numVitelliPresenti>',v4.num_vitelli,'</numVitelliPresenti>
		<numVitelli8Sett>',v4.num_vitelli_8_sett,'</numVitelli8Sett>') else '' end
						    ) else '' end,
	case when v4.esito_controllo <> 'A' then concat( 
	case when bdn.tipo_allegato = '5' then concat('<flagCapMaxAut>','','</flagCapMaxAut>') else '' end) else '' 
	end,
	case when v4.esito_controllo = 'N' then 
		concat('<prescrizioni>',v4.prescrizioni_descrizione,'</prescrizioni>
		', 
		case when v4.prescrizioni_descrizione <> '' then 
			concat('<dataScadPrescrizioni>',concat(v4.data_prescrizioni,'T00:00:00.000'),'</dataScadPrescrizioni>
			        <flagPrescrizioneEsitoBa>',v4.eseguite_prescrizioni,'</flagPrescrizioneEsitoBa>
			        ',	
				case when v4.data_verifica <> '' then 
					concat('<dataVerificaBa>',concat(v4.data_verifica,'T00:00:00.000'),'</dataVerificaBa>
						<secondoControllore>',v4.nome_controllore,'</secondoControllore>
						<nomeRappresentanteVer>',v4.nome_proprietario_prescrizioni_eseguite,'</nomeRappresentanteVer>
						'
					       ) else '' end
		               ) end
		      )else '' end,
	'<parametro>2018</parametro>',
	case when v4.esito_controllo = 'N' then (select get_chiamata_ws_insert_ba_v4_requisiti from public.get_chiamata_ws_insert_ba_v4_requisiti(_id)) else '' 
	END,
	case when v4.esito_controllo = 'N' then (select get_chiamata_ws_insert_ba_v4_capannoni from public.get_chiamata_ws_insert_ba_v4_capannoni(_id)) else '' 
	END,'
            </dettagliochecklist>
            ') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v4 v4 on v4.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
   

    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v4_dettaglio_checklist(integer)
  OWNER TO postgres;

-- Function: public.get_chiamata_ws_insert_ba_v4_requisiti(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v4_requisiti(integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v4_requisiti(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
tag_preavviso text;
BEGIN

select string_agg(concat('<requisiti>
               <cldbId>0</cldbId>
               <irrId>',dom.irr_id,'</irrId>
               <dettirrId>',dom.dettirrid,'</dettirrId>
               <numIrregolarita>',risp.num_irregolarita,'</numIrregolarita>
               <numProvvCatA>',risp.num_provv_a,'</numProvvCatA>
               <numProvvCatB>',risp.num_provv_b,'</numProvvCatB>
               <numProvvCatC>',risp.num_provv_c,'</numProvvCatC>
               <flagEsitoDett>',risp.risposta,'</flagEsitoDett>
               <osservazioni>',risp.evidenze,'</osservazioni>
               <operation>insert</operation>
            </requisiti>'),'
            ')
into msg
from
chk_bns_risposte_v4 risp  
left join chk_bns_domande_v4 dom on risp.id_domanda = dom.id
where risp.trashed_date is null and risp.id_chk_bns_mod_ist = _id;   

    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v4_requisiti(integer)
  OWNER TO postgres;


  -- Function: public.get_chiamata_ws_insert_ba_v5_bovini(text, text, integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v5_bovini(text, text, integer);
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v5_bovini(
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
      ',(select get_chiamata_ws_insert_ba_v5_bovini_controlli_allevamenti from get_chiamata_ws_insert_ba_v5_bovini_controlli_allevamenti(_id)),'
      ',(select get_chiamata_ws_insert_ba_v5_bovini_dettaglio_checklist from get_chiamata_ws_insert_ba_v5_bovini_dettaglio_checklist(_id)),'
      </ns2:insertControlliallevamentiBA>
      </S:Body> 
      </S:Envelope>');
    
 RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v5_bovini(text, text, integer)
  OWNER TO postgres;

  CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v5_bovini_controlli_allevamenti(_id integer)
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
left join bdn_checklist_v5_bovini_bufalini bovini on bovini.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
     
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v5_bovini_controlli_allevamenti(integer)
  OWNER TO postgres;

  -- Function: public.get_chiamata_ws_insert_ba_v5_bovini_dettaglio_checklist(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v5_bovini_dettaglio_checklist(integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v5_bovini_dettaglio_checklist(_id integer)
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
	case when bovini.esito_controllo <> 'A' then (select get_chiamata_ws_insert_ba_v5_requisiti from public.get_chiamata_ws_insert_ba_v5_requisiti(_id)) else '' 
	END,'
            </dettagliochecklist>
            ') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v5_bovini_bufalini bovini on bovini.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
   
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v5_bovini_dettaglio_checklist(integer)
  OWNER TO postgres;

  
  -- Function: public.get_chiamata_ws_insert_ba_v5_requisiti(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v5_requisiti(integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v5_requisiti(_id integer)
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
chk_bns_risposte_v5 risp  
left join chk_bns_domande_v5 dom on risp.id_domanda = dom.id
where risp.trashed_date is null and risp.id_chk_bns_mod_ist = _id;     
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v5_requisiti(integer)
  OWNER TO postgres;

  
  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v5_suini(
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
      ',(select get_chiamata_ws_insert_ba_v5_suini_controlli_allevamenti from get_chiamata_ws_insert_ba_v5_suini_controlli_allevamenti(_id)),'
      ',(select get_chiamata_ws_insert_ba_v5_suini_dettaglio_checklist from get_chiamata_ws_insert_ba_v5_suini_dettaglio_checklist(_id)),'
      </ns2:insertControlliallevamentiBA>
      </S:Body> 
      </S:Envelope>');
    
 RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v5_suini(text, text, integer)
  OWNER TO postgres;

 -- Function: public.get_chiamata_ws_insert_ba_v5_suini_box(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v5_suini_box(integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v5_suini_box(_id integer)
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
  chk_bns_boxes_v5 where trashed_date  is null and id_chk_bns_mod_ist = _id
  and (numero <> '' or larghezza <> '' or lunghezza <> '' or animali <> '' or peso <> '' or categoria <> '' or pavimento <> '' or travetti <> '' or fessure <> '' or regolare <> '') ;
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v5_suini_box(integer)
  OWNER TO postgres;

  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v5_suini_controlli_allevamenti(_id integer)
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
left join bdn_checklist_v5_suini suini on suini.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
     
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v5_suini_controlli_allevamenti(integer)
  OWNER TO postgres;

  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v5_suini_dettaglio_checklist(_id integer)
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
	case when suini.esito_controllo <> 'A' then (select get_chiamata_ws_insert_ba_v5_requisiti from public.get_chiamata_ws_insert_ba_v5_requisiti(_id)) else '' END,
	CASE     when suini.esito_controllo <> 'A' then (select get_chiamata_ws_insert_ba_v5_suini_box from public.get_chiamata_ws_insert_ba_v5_suini_box(_id)) else ''
	end,'
            </dettagliochecklist>') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v5_suini suini on suini.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
   
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v5_suini_dettaglio_checklist(integer)
  OWNER TO postgres;

  
  
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_sa_v4(
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
      <ns2:insertControlliallevamentiSA xmlns:ns2="http://ws.izs.it">','
      ',(select get_chiamata_ws_insert_sa_v4_controlli_allevamenti from get_chiamata_ws_insert_sa_v4_controlli_allevamenti(_id)),'
      ',(select get_chiamata_ws_insert_sa_v4_dettaglio_checklist from get_chiamata_ws_insert_sa_v4_dettaglio_checklist(_id)),'
      </ns2:insertControlliallevamentiSA>
      </S:Body> 
      </S:Envelope>');
       

 RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_sa_v4(text, text, integer)
  OWNER TO postgres;

  -- Function: public.get_chiamata_ws_insert_sa_v4_controlli_allevamenti(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_sa_v4_controlli_allevamenti(integer);

-- Function: public.get_chiamata_ws_insert_sa_v4_controlli_allevamenti(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_sa_v4_controlli_allevamenti(integer);
-- Function: public.get_chiamata_ws_insert_sa_v4_controlli_allevamenti(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_sa_v4_controlli_allevamenti(integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_sa_v4_controlli_allevamenti(_id integer)
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
	  <flagPreavviso>',bdn.flag_preavviso,'</flagPreavviso>
	  '
	  ,case when  bdn.flag_preavviso <> 'N' and bdn.data_preavviso is not null then concat('<dataPreavviso>',to_char(bdn.data_preavviso, 'YYYY-MM-DD"T"00:00:00.000'),'</dataPreavviso>
	    ') else '' end,'            
	    <dtControllo>', to_char(data_controllo, 'YYYY-MM-DD"T"00:00:00.000'),'</dtControllo>
            <aslCodice>',codice_asl,'</aslCodice>
            <aziendaCodice>',codice_azienda,'</aziendaCodice>
            <speCodice>',bdn.specie_allevamento,'</speCodice>
            ', case when bdn.codice_orientamento <> '' then concat('<orientamentoCodice>',bdn.codice_orientamento,'</orientamentoCodice>
            ') else '' end,'
            <tipoProdCodice>',bdn.tipo_produzione,'</tipoProdCodice>
            <allevIdFiscale>',id_fiscale_allevamento,'</allevIdFiscale>
            <ocCodice>',bdn.occodice,'</ocCodice>
            <flagEsito>',bdn.flag_esito_sa,'</flagEsito>
	    ',case when bdn.tipo_allevamento_codice <> '' then concat('<tipoAllevCodice>',bdn.tipo_allevamento_codice,'</tipoAllevCodice>
            ') else '' end,'
             <detenIdFiscale>',bdn.id_fiscale_detentore,'</detenIdFiscale>
	     ',case when bdn.flag_esito_sa <> '' and bdn.flag_esito_sa = 'N' then concat(
	     '<sanzSequestro>',bdn.sanz_sequestro,'</sanzSequestro>
	      <sanzAltro>',bdn.sanz_altro,'</sanzAltro>
	      <sanzAltroDesc>',bdn.sanz_altro_desc,'</sanzAltroDesc>
            <sanzAbbattimentoCapi>',bdn.sanz_abbattimento_capi,'</sanzAbbattimentoCapi>
          <sanzBloccoMov>',bdn.sanz_blocco_mov,'</sanzBloccoMov>
            ') else '' end,'
            <noteControllore>',bdn.note_controllore,'</noteControllore>
            <noteDetentore>',bdn.note_detentore,'</noteDetentore>
            <flagEvidenze>',bdn.flag_evidenze,'</flagEvidenze>
            ',case when bdn.flag_evidenze = 'S' then 
						concat('<evidenzaIr>',bdn.evidenza_ir,'</evidenzaIr>
						<evidenzaSv>',bdn.evidenza_sv,'</evidenzaSv>
						<evidenzaBa>',bdn.evidenza_ba,'</evidenzaBa>'
						)
						else '' end, 
	case when bdn.evidenza_sv <> '' and bdn.evidenza_sv = 'S' then concat('<sanzAmministrativa>',bdn.sanz_amministrativa,'</sanzAmministrativa>
	') else '' end,'
	   <primoControllore>',bdn.primo_controllore,'</primoControllore>
	   <tipoControllo>SA</tipoControllo>
           <flagCopiaCheckList>',bdn.flag_copia_checklist,'</flagCopiaCheckList>
           ',case when bdn.data_chiusura <> '' then concat('<dataChiusura>',concat(bdn.data_chiusura,'T00:00.000'),'</dataChiusura>
             ')
	    else '' end,'
	   <flagCondizionalita>',bdn.flag_condizionalita,'</flagCondizionalita>    
         </controlliallevamenti>
         ') into msg
from bdn_cu_chiusi_sicurezza_alimentare_b11  bdn
where bdn.id_chk_bns_mod_ist = _id;

    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_sa_v4_controlli_allevamenti(integer)
  OWNER TO postgres;


 -- Function: public.get_chiamata_ws_insert_sa_v4_dettaglio_checklist(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_sa_v4_dettaglio_checklist(integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_sa_v4_dettaglio_checklist(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
tag_preavviso text;
BEGIN
select 
concat(
'<dettagliochecklist>
	<flagCopiaCheckList>',bdn.flag_copia_checklist,'</flagCopiaCheckList>
	<clsaId>0</clsaId>
	<requisitiXml></requisitiXml>
	<secondoControllore>',bdn.secondo_controllore,'</secondoControllore>
	<parametro>2018</parametro>
	<critCodice>',bdn.crit_codice,'</critCodice>
	<criterioControlloAltro>',bdn.crit_controllo_altro,'</criterioControlloAltro>
        <numCapiPresenti>',bdn.num_capi_presenti,'</numCapiPresenti>
        <numCapiControllati>',bdn.num_capi_controllati,'</numCapiControllati>
        <nomeRappresentante>',bdn.nome_rappresentante,'</nomeRappresentante>
        <note>',bdn.punto_note,'</note>
        ',case when bdn.flag_esito_sa <> '' and bdn.flag_esito_sa = 'N' then concat('<flagIntenzionalitaSa>',bdn.flag_intenzionalita_sa,'</flagIntenzionalitaSa>
	<flagIntenzionalitaTse>',bdn.flag_intenzionalita_tse,'</flagIntenzionalitaTse>
	<prescrizioni>',bdn.prescrizioni,'</prescrizioni>
	<flagPrescrizioneEsitoSa>',bdn.flag_esito_sa,'</flagPrescrizioneEsitoSa>
	<prescrizioni2>',bdn.prescrizioni2,'</prescrizioni2>
	<flagPrescrizioneEsitoSa2>',bdn.flag_prescrizione_esito_sa2,'</flagPrescrizioneEsitoSa2>
        ') else '' end,'
         <nomeRappresentanteVer>',bdn.nome_rappresentante_ver,'</nomeRappresentanteVer>
         <secondoControllore2>',bdn.secondo_controllore2,'</secondoControllore2>
         <nomeRappresentanteVer2>',bdn.nome_rappresentante_ver2,'</nomeRappresentanteVer2>	
	', case when bdn.data_scad_prescrizioni is not null then concat('<dataScadPrescrizioni>',concat(bdn.data_scad_prescrizioni,'T00:00:00.000'),'</dataScadPrescrizioni>
	') else '' end,
	case when bdn.data_verifica_sa is not null then concat('<dataVerificaSa>',concat(bdn.data_verifica_sa,'T00:00:00.000'),'</dataVerificaSa>
	') else '' end,
	case when bdn.data_scad_prescrizioni2 is not null then concat('<dataScadPrescrizioni2>',concat(bdn.data_scad_prescrizioni2, 'T00:00:00.000'),'</dataScadPrescrizioni2>
	') else '' end,
	case when bdn.data_verifica_sa2 is not null then concat('<dataVerificaSa2>',concat(bdn.data_verifica_sa2, 'T00:00:00.000'),'</dataVerificaSa2>
	') else '' end,
	case when bdn.flag_esito_sa = 'N' then (select get_chiamata_ws_insert_sa_v4_requisiti from public.get_chiamata_ws_insert_sa_v4_requisiti(_id)) else '' 
	END,
	'</dettagliochecklist>
            ') into msg
from bdn_cu_chiusi_sicurezza_alimentare_b11 bdn
where bdn.id_chk_bns_mod_ist = _id;

    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_sa_v4_dettaglio_checklist(integer)
  OWNER TO postgres;

 
  -- Function: public.get_chiamata_ws_insert_sa_v4_requisiti(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_sa_v4_requisiti(integer);
-- Function: public.get_chiamata_ws_insert_sa_v4_requisiti(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_sa_v4_requisiti(integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_sa_v4_requisiti(_id integer)
  RETURNS text AS
$BODY$
   DECLARE
   msg text ;
BEGIN

select string_agg(concat('<requisiti>
               <clsadettId>0</clsadettId>
               <sairrdettId>',r.classe_ws,'</sairrdettId>
               <flagSN>',
               case when (r.esito = false and r.domanda_non_pertinente <>true) then 'N'
	       when r.esito is null then '-'  else 'S' end,'</flagSN>
               <operation>insert</operation>
            </requisiti>'),'
            ') into msg
         from chk_bns_mod_ist i  join
	chk_bns_risposte r on r.idmodist = i.id 
	where 
	i.id = _id;
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_sa_v4_requisiti(integer)
  OWNER TO postgres;

  
CREATE OR REPLACE FUNCTION public.chk_bns_mod_ist_aggiorna_invio(
    _id integer,
    _id_bdn integer,
    _esito_import text,
    _descrizione_errore text,
    _id_invio_massivo integer,
    _id_utente integer)
  RETURNS boolean AS
$BODY$
DECLARE
idAlleg integer;

BEGIN

select id_alleg into idAlleg from chk_bns_mod_ist where id = _id;

update chk_bns_mod_ist set note = concat_ws('*',note,'Aggiornamento dello stato tramite dbi chk_bns_aggiorna_invio(integer,integer, text, text, integer)'),
esito_import = _esito_import, descrizione_errore =_descrizione_errore, id_bdn= _id_bdn, modifiedby = _id_utente, modified=now(), data_import=now() where id=_id;

insert into log_invio_ba_sa(id_utente, tipo_invio, tipo_ba_sa, id_invio_massivo, esito, descrizione_errore, id_chk_bns_mod_ist, id_controllo, codice_azienda, tipo_checklist) values (_id_utente, case when _id_invio_massivo > 0 then 'massivo' else 'puntuale' end, case when idAlleg = 23 then 'sa' else 'ba' end, _id_invio_massivo, _esito_import, _descrizione_errore, _id, (select idcu from chk_bns_mod_ist where id = _id), (select account_number from organization where org_id in (select org_id from ticket where ticketid in (select idcu from chk_bns_mod_ist where id = _id))), (select description from lookup_chk_bns_mod where code in (select id_alleg from chk_bns_mod_ist where id = _id)))  ;

return true;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.chk_bns_mod_ist_aggiorna_invio(integer, integer, text, text, integer, integer)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.chk_bns_mod_ist_aggiorna_stato(
    _id integer,
    _bozza boolean,
    _id_utente integer)
  RETURNS boolean AS
$BODY$

BEGIN
update chk_bns_mod_ist set note = concat_ws('*',note,'Aggiornamento dello stato tramite dbi chk_bns_aggiorna_stato(integer,boolean, integer)'),
bozza= _bozza, modifiedby = _id_utente, modified=now() where id=_id;

return true;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.chk_bns_mod_ist_aggiorna_stato(integer, boolean, integer)
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


  -- Function: public.get_chiamata_ws_insert_ba_sa_header(text, text)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_sa_header(text, text);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_sa_header(
    _username text,
    _password text)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
id_tipo_chk integer;
result text;
BEGIN

msg :='<S:Header>
      <ns2:SOAPAutenticazione xmlns:ns2="http://ws.izs.it">
         <password>'||_password||'</password>
         <username>'||_username||'</username>
      </ns2:SOAPAutenticazione>
      <ns2:SOAPAutorizzazione xmlns:ns2="http://ws.izs.it">
         <codiceLingua>IT</codiceLingua>
         <grspeCodice />
         <ruolo>regione</ruolo>
      </ns2:SOAPAutorizzazione>
   </S:Header>';
     
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_sa_header(text, text)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v5_vitelli(
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
      ',(select get_chiamata_ws_insert_ba_v5_vitelli_controlli_allevamenti from get_chiamata_ws_insert_ba_v5_vitelli_controlli_allevamenti(_id)),'
      ',(select get_chiamata_ws_insert_ba_v5_vitelli_dettaglio_checklist from get_chiamata_ws_insert_ba_v5_vitelli_dettaglio_checklist(_id)),'
      </ns2:insertControlliallevamentiBA>
      </S:Body> 
      </S:Envelope>');
    
 RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v5_vitelli(text, text, integer)
  OWNER TO postgres;

  
  -- Function: public.get_chiamata_ws_insert_ba_v5_vitelli_dettaglio_checklist(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v5_vitelli_dettaglio_checklist(integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v5_vitelli_dettaglio_checklist(_id integer)
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
	case when vitelli.esito_controllo <> 'A' then (select get_chiamata_ws_insert_ba_v5_requisiti from public.get_chiamata_ws_insert_ba_v5_requisiti(_id)) else '' 
	END,'
            </dettagliochecklist>
            ') into msg
from bdn_cu_chiusi_benessere_animale bdn
left join bdn_checklist_v5_vitelli vitelli on vitelli.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
   
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v5_vitelli_dettaglio_checklist(integer)
  OWNER TO postgres;

  -- Function: public.get_chiamata_ws_insert_ba_v5_vitelli_controlli_allevamenti(integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v5_vitelli_controlli_allevamenti(integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v5_vitelli_controlli_allevamenti(_id integer)
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
left join bdn_checklist_v5_vitelli vitelli on vitelli.id_chk_bns_mod_ist = bdn.id_chk_bns_mod_ist
where bdn.id_chk_bns_mod_ist = _id;
     
    RETURN msg;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v5_vitelli_controlli_allevamenti(integer)
  OWNER TO postgres;
  
  -- Function: public.get_chiamata_ws_insert_ba_v5_suini(text, text, integer)

-- DROP FUNCTION public.get_chiamata_ws_insert_ba_v5_suini(text, text, integer);

CREATE OR REPLACE FUNCTION public.get_chiamata_ws_insert_ba_v5_suini(
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
      ',(select get_chiamata_ws_insert_ba_v5_suini_controlli_allevamenti from get_chiamata_ws_insert_ba_v5_suini_controlli_allevamenti(_id)),'
      ',(select get_chiamata_ws_insert_ba_v5_suini_dettaglio_checklist from get_chiamata_ws_insert_ba_v5_suini_dettaglio_checklist(_id)),'
      </ns2:insertControlliallevamentiBA>
      </S:Body> 
      </S:Envelope>');
    
 RETURN msg;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_insert_ba_v5_suini(text, text, integer)
  OWNER TO postgres;

  
-- Function: public.get_info_chk_bns_istanza(integer, integer)
-- DROP FUNCTION public.get_info_chk_bns_istanza(integer, integer);

CREATE OR REPLACE FUNCTION public.get_info_chk_bns_istanza(
    IN _idcu integer,
    IN _codice_specie integer)
  RETURNS TABLE(id integer, bozza boolean, esito_import text, descrizione_errore text, id_bdn integer, data_import timestamp without time zone) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
RETURN QUERY
select ist.id, ist.bozza, ist.esito_import, ist.descrizione_errore, ist.id_bdn, ist.data_import from chk_bns_mod_ist ist 
left join lookup_chk_bns_mod mod on ist.id_alleg = mod.code 
where ist.idcu = _idcu and mod.codice_specie = _codice_specie and ist.trashed_date is null;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_info_chk_bns_istanza(integer, integer)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION public.get_esistenza_ba_sa_istanza(_id_controllo integer, _ba_sa text)
RETURNS boolean AS
$BODY$
DECLARE
	num_chk_ba integer;
	num_chk_sa integer;
	esito boolean;
BEGIN
-- valori ammessi in input: isCondizionalita
select count(*) into num_chk_sa from chk_bns_mod_ist where trashed_date is null and idcu = _id_controllo and bozza=false and id_alleg in (
select code from lookup_chk_bns_mod where codice_checklist = _ba_sa);

 select count(*) into num_chk_ba from chk_bns_mod_ist where trashed_date is null and idcu = _id_controllo and bozza=false and id_alleg in (
select code from lookup_chk_bns_mod where codice_checklist <> _ba_sa);

if(num_chk_sa > 0 OR num_chk_ba > 0) then 
    esito = true;
else 
    esito = false;
end if;
	return esito;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
  
CREATE OR REPLACE FUNCTION public.get_esistenza_ba_sa_istanza(_id_controllo integer, _isCondizionalita boolean)
RETURNS boolean AS
$BODY$
DECLARE
	num_chk_ba integer;
	num_chk_sa integer;
BEGIN
-- valori ammessi in input: isCondizionalita
select count(*) into num_chk_sa from chk_bns_mod_ist where trashed_date is null and idcu = _id_controllo and bozza=false and id_alleg in (
select code from lookup_chk_bns_mod where codice_checklist = 'isCondizionalita');

select count(*) into num_chk_ba from chk_bns_mod_ist where trashed_date is null and idcu = _id_controllo and bozza=false and id_alleg in (
select code from lookup_chk_bns_mod where codice_checklist <> 'isCondizionalita');

raise info '[Variabile isCondizionalita] %', _isCondizionalita;

if(_isCondizionalita) then
	if(num_chk_sa > 0) then
raise info '[Num checklist SICUREZZA] %', num_chk_sa;
		return true;
	else 
		return false;
	end if;
end if;
if (_isCondizionalita = false) then
raise info '[Num checklist BENESSERE] %', num_chk_ba;
	if(num_chk_ba > 0) then
		return true;
	else 
		return false;
	end if;
end if;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;