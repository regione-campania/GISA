-- Function: public.is_controllo_b26(integer)

-- DROP FUNCTION public.is_controllo_b26(integer);

CREATE OR REPLACE FUNCTION public.is_controllo_b26(_idcontrollo integer)
  RETURNS boolean AS
$BODY$
DECLARE
	_ticketId integer;
	esito boolean;
BEGIN

_ticketId := -1;
esito:= false;

_ticketId := (select distinct t.ticketid
from ticket t
left join tipocontrolloufficialeimprese tcu on tcu.idcontrollo = t.ticketid
left join lookup_piano_monitoraggio piano on piano.code = tcu.pianomonitoraggio
left join ricerche_anagrafiche_old_materializzata r on 
((r.riferimento_id = t.org_id and r.riferimento_id_nome_col = 'org_id') or (r.riferimento_id = t.id_stabilimento and r.riferimento_id_nome_col = 'id_stabilimento') or (r.riferimento_id = t.alt_id and r.riferimento_id_nome_col = 'alt_id'))
where t.tipologia = 3 and t.trashed_date is null and piano.codice_interno =1483 and t.ticketid = _idcontrollo and t.ticketid in (select idcu from chk_bns_mod_ist where idcu = _idcontrollo and id_alleg =22 and bozza is false));

if (_ticketId) > 0 THEN
esito = true;
END IF;

 RETURN esito;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.is_controllo_b26(integer)
  OWNER TO postgres;
  
  
  

CREATE OR REPLACE FUNCTION public.get_dati_b11(IN idcontrollo integer)
  RETURNS TABLE(cgo4_interventi boolean, cgo9_interventi boolean, cgo4_rispettato boolean, cgo9_rispettato boolean, flag_esito_sa text ) AS
$BODY$
	
BEGIN
	FOR cgo4_interventi, cgo9_interventi, cgo4_rispettato, cgo9_rispettato, flag_esito_sa 
	in

	  select cgo.sez1_cgo4_interventi, cgo.sez1_cgo9_interventi, cgo.cgo4_rispettato, cgo.cgo9_rispettato, bdn.flag_esito_sa from
   chk_bns_mod_ist ist 
   join bdn_cu_chiusi_sicurezza_alimentare_b11_org_opu bdn on bdn.id_controllo = ist.idcu and bdn.id_alleg = ist.id_alleg
   join chk_bns_mod_ist_cgo cgo on cgo.id_chk_bns_mod_ist = ist.id
   where ist.idcu = idcontrollo and ist.id_alleg = 22 and ist.trashed_date is null
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;


  
  
 CREATE  TABLE dati_b26 (id serial, 
		 flagCopiaCheckList text, 
	SanzBloccoMov  text,
	 SanzAmministrativa  text,
	 SanzSequestro  text,
	 sanzAltro  text,
	 sanzAltroDesc  text,
	 sanzAbbattimentoCapi  text,
	 noteControllore  text,
	 noteDetentore  text,
	 flagEvidenze text,
	 evidenzaIr  text,
	 evidenzaBa  text,
	 evidenzaTse  text,
	 flagCondizionalita  text,
	
	 enteredby integer,
	 entered  timestamp without time zone default now(),
	 enabled boolean default true,
	 id_controllo integer,
	 data_invio timestamp without time zone);
	 
	 alter table dati_b26 add column  critCodice  text ;
	alter table dati_b26 add column  criterioControlloAltro text ;
	alter table dati_b26 add column  numCapiPresenti text ;
	alter table dati_b26 add column  numCapiControllati text ;
	alter table dati_b26 add column  flagIntenzionalitaSa text ;
	alter table dati_b26 add column  flagIntenzionalitaTse text ; 
	alter table dati_b26 add column  prescrizioni  text ;
	alter table dati_b26 add column  dataScadPrescrizioni text ; 
	alter table dati_b26 add column  prescrizioni2  text ;
	alter table dati_b26 add column  dataScadPrescrizioni2  text ;
	alter table dati_b26 add column  flagPrescrizioneEsitoSa text ; 
	alter table dati_b26 add column  dataVerificaSa  text ;
	alter table dati_b26 add column  secondoControllore  text ;
	alter table dati_b26 add column  nomeRappresentanteVer  text ;
	alter table dati_b26 add column  flagPrescrizioneEsitoSa2 text ; 
	alter table dati_b26 add column  dataVerificaSa2  text ;
	alter table dati_b26 add column  secondoControllore2  text ;
	alter table dati_b26 add column  nomeRappresentanteVer2 text ; 
	alter table dati_b26 add column  parametro text ; 

	
	
CREATE TABLE public.lookup_criteri_controllo_sa
(
  code integer NOT NULL,
  description character varying(250) NOT NULL,
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT lookup_criteri_controllo_sa_pkey PRIMARY KEY (code)
)
WITH (
  OIDS=FALSE
);



insert into lookup_criteri_controllo_sa (code, description) values (002,'Altre indagini degli organi di polizia giudiziaria');
insert into lookup_criteri_controllo_sa (code, description) values (003,'Cambiamenti della situazione aziendale');
insert into lookup_criteri_controllo_sa (code, description) values (004,'Comunicazione dei dati dell''azienda all''Autorità competente');
insert into lookup_criteri_controllo_sa (code, description) values (005,'Implicazioni per la salute umana e animale, precedenti Focolai');
insert into lookup_criteri_controllo_sa (code, description) values (006, 'Indagine relativa all''igiene degli allevamenti');
insert into lookup_criteri_controllo_sa (code, description) values (007,'Indagine relativa alle frodi comunitarie');
insert into lookup_criteri_controllo_sa (code, description) values (008,'Infrazioni riscontrate negli anni precedenti'); 
insert into lookup_criteri_controllo_sa (code, description) values (009,'Numero di animali');
insert into lookup_criteri_controllo_sa (code, description) values (011, 'Segnalazione di irregolarità da impianto di macellazione');
insert into lookup_criteri_controllo_sa (code, description) values (012, 'Variazioni dell''entità dei premi');
insert into lookup_criteri_controllo_sa (code, description) values (997, 'Altro criterio di rischio ritenuto rilevante dall''Autorità competente, indicare quale');



-- invio


-- invio

-- Function: public.get_chiamata_ws_invio_b26(integer)
-- DROP FUNCTION public.get_chiamata_ws_invio_b26(integer);
CREATE OR REPLACE FUNCTION public.get_chiamata_ws_invio_b26(_idcu integer)
  RETURNS text AS
$BODY$
DECLARE
	ret text;
BEGIN
select 
concat(
concat(
'<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.izs.it"><soapenv:Header><ws:SOAPAutenticazione><password>na.izs34</password><username>izsna_006</username></ws:SOAPAutenticazione><ws:SOAPAutorizzazione><ruolo>regione</ruolo></ws:SOAPAutorizzazione></soapenv:Header>
   <soapenv:Body>
       <ws:insertControlliallevamentiSA>
          <controlliallevamenti>
             <allevIdFiscale>', bdn.id_fiscale_allevamento, '</allevIdFiscale>
             <aslCodice>', bdn.codice_asl, '</aslCodice>
             <aziendaCodice>', bdn.codice_azienda, '</aziendaCodice>
             <caId>', '0', '</caId>
             <dataChiusura>', '', '</dataChiusura>
             <dataPreavviso>', to_char(bdn.data_preavviso, 'yyyy-mm-dd'), '</dataPreavviso>
             <detenIdFiscale>', bdn.id_fiscale_detentore, '</detenIdFiscale>
<distrettoCodice>', '', '</distrettoCodice>
             <dtControllo>',  to_char(bdn.data_controllo, 'yyyy-mm-dd'), '</dtControllo>
             <evidenzaBa>', b26.evidenzaba, '</evidenzaBa>
             <evidenzaIr>', b26.evidenzair, '</evidenzaIr>
             <evidenzaSa>', '', '</evidenzaSa>
             <evidenzaSv>', '', '</evidenzaSv>
             <evidenzaTse>', case when b26.evidenzatse <> '' then to_char(to_date(b26.evidenzatse, 'DD/MM/YYYY'), 'YYYY-MM-DD') else '' end, '</evidenzaTse>
             <flagCee>', '', '</flagCee>
<flagCondizionalita>', b26.flagcondizionalita, '</flagCondizionalita>
<flagCopiaCheckList>', bdn.flag_copia_checklist, '</flagCopiaCheckList>
             <flagEsito>', bdn.flag_esito_sa, '</flagEsito>
             <flagEvidenze>', b26.flagevidenze, '</flagEvidenze>
             <flagExtrapiano>', '', '</flagExtrapiano>
<flagFaseProduttiva>', '', '</flagFaseProduttiva>
             <flagPreavviso>', bdn.flag_preavviso, '</flagPreavviso>
             <flagVitelli>', '', '</flagVitelli>
<noteControllore>', b26.notecontrollore, '</noteControllore>
             <noteDetentore>', b26.notedetentore, '</noteDetentore>
             <ocCodice>', bdn.occodice, '</ocCodice>
<orientamentoCodice>', bdn.codice_orientamento, '</orientamentoCodice>
<primoControllore>', '', '</primoControllore>
             <progrApiario>', '', '</progrApiario>
             <regCodice>', '', '</regCodice>
<sanzAbbattimentoCapi>', b26.sanzabbattimentocapi, '</sanzAbbattimentoCapi>
             <sanzAltro>', b26.sanzaltro, '</sanzAltro>
             <sanzAltroDesc>', b26.sanzaltrodesc, '</sanzAltroDesc>
<sanzAmministrativa>', b26.sanzamministrativa, '</sanzAmministrativa>
             <sanzBloccoMov>', b26.sanzbloccomov, '</sanzBloccoMov>
             <sanzSequestro>', b26.sanzsequestro, '</sanzSequestro>
             <speCodice>', '', '</speCodice>
<tipoAllevCodice>', bdn.tipo_allevamento_codice, '</tipoAllevCodice>
             <tipoControllo>', '', '</tipoControllo>
             <tipoProdCodice>', '', '</tipoProdCodice>
          </controlliallevamenti>'), concat('
          <dettagliochecklist>
             <caId>', '0', '</caId>
             <clsaId>', '0', '</clsaId>
             <critCodice>', b26.critcodice, '</critCodice>
<criterioControlloAltro>', b26.criteriocontrolloaltro, '</criterioControlloAltro>
<dataScadPrescrizioni>', case when b26.datascadprescrizioni <>'' then to_char(to_date(b26.datascadprescrizioni, 'DD/MM/YYYY'), 'YYYY-MM-DD') else '' end, '</dataScadPrescrizioni>
<dataScadPrescrizioni2>', case when b26.datascadprescrizioni2 <>'' then  to_char(to_date(b26.datascadprescrizioni2, 'DD/MM/YYYY'), 'YYYY-MM-DD') else '' end, '</dataScadPrescrizioni2>
<dataVerificaSa>', case when b26.dataverificasa <>'' then  to_char(to_date(b26.dataverificasa, 'DD/MM/YYYY') , 'YYYY-MM-DD') else '' end, '</dataVerificaSa>
<dataVerificaSa2>', case when b26.dataverificasa2 <>'' then  to_char(to_date(b26.dataverificasa2, 'DD/MM/YYYY'), 'YYYY-MM-DD') else '' end, '</dataVerificaSa2>
<flagIntenzionalitaSa>', b26.flagIntenzionalitaSa, '</flagIntenzionalitaSa>
<flagIntenzionalitaTse>',  b26.flagIntenzionalitaTse, '</flagIntenzionalitaTse>
<flagPrescrizioneEsitoSa>', b26.flagPrescrizioneEsitoSa, '</flagPrescrizioneEsitoSa>
<flagPrescrizioneEsitoSa2>', b26.flagPrescrizioneEsitoSa2, '</flagPrescrizioneEsitoSa2>
<nomeRappresentante>', '', '</nomeRappresentante>
<nomeRappresentanteVer>', b26.nomeRappresentanteVer, '</nomeRappresentanteVer>
<nomeRappresentanteVer2>', b26.nomeRappresentanteVer2, '</nomeRappresentanteVer2>
             <note>', '', '</note>
<numCapiControllati>', b26.numCapiControllati, '</numCapiControllati>
<numCapiPresenti>', b26.numCapiPresenti, '</numCapiPresenti>
             <parametro>', b26.parametro, '</parametro>
             <prescrizioni>', b26.prescrizioni, '</prescrizioni>
             <prescrizioni2>', b26.prescrizioni2, '</prescrizioni2>
             <requisitiXml>', '', '</requisitiXml>
<secondoControllore>', b26.secondoControllore, '</secondoControllore>
<secondoControllore2>', b26.secondoControllore2, '</secondoControllore2>
             <!--Zero or more repetitions:-->
             <requisiti>
                <clsadettId>', '', '</clsadettId>
                <descrizione>', '', '</descrizione>
                <flagSN>', '', '</flagSN>
                <ordine>', '', '</ordine>
                <parametro>', '', '</parametro>
                <sairrdettId>', '', '</sairrdettId>
                <tipo>', '', '</tipo>
<operation>insertControlliallevamentiSA</operation>
             </requisiti>
          </dettagliochecklist>
       </ws:insertControlliallevamentiSA>
    </soapenv:Body>
</soapenv:Envelope>')) into ret
from bdn_cu_chiusi_sicurezza_alimentare_b11 bdn 
left join dati_b26 b26 on b26.id_controllo = bdn.id_controllo and b26.enabled where bdn.id_controllo = _idcu;
 RETURN ret;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.get_chiamata_ws_invio_b26(integer)
  OWNER TO postgres;