select 
anmnome,anmtatuaggio,anmmicrochip,codicetipoevento,dtevento,dtcomunicazioneautorita,dtverbale,numverbale,ubixml,apmxml,traxml,cesxml,reixml,resxml,mrsxml,smaxml,ritxml,morxml,id_evento
from (
select 
coalesce(an.nome, '') as anmNome, 
coalesce(tatuaggio, '') as anmTatuaggio, 
coalesce(an.microchip, '') as anmMicrochip , 
case   
--when (e.id_tipologia_evento =......) then  'UBI' NO GESTIAMO QUESTI CASI IN BDU
when (e.id_tipologia_evento =38) then 'APM'
when  e.id_tipologia_evento in(18,45,31,56,24) then 'TRA'
when (e.id_tipologia_evento in ( 40,52,47,15,13,19,16,55,59,33,61) ) then 'CES'
when (e.id_tipologia_evento =23) then 'REI'
when (e.id_tipologia_evento =41) then 'RES'
when (e.id_tipologia_evento =4  or e.id_tipologia_evento = 11) then  'SMA'
when (e.id_tipologia_evento =12) then 'RIT'
when (e.id_tipologia_evento =9) then  'MOR'
when (e.id_tipologia_evento in (21,68)) then  'MRS'
--when (e.id_tipologia_evento =......) then  'USC' MANCA IL NODO NEL DOCUMENTO
else '' end as codiceTipoEvento,
case 
when  e.id_tipologia_evento =38 then coalesce(to_char(eim.data_inserimento_microchip , 'dd/mm/yyyy'),coalesce(to_char(e.entered, 'dd/mm/yyyy'),'') )   -- DATA APPL. MICROCHIP 
when  e.id_tipologia_evento in(18,45,31,56,24) then coalesce(to_char(evt_cambio_det.data_cambio_detentore , 'dd/mm/yyyy'), '')  -- DATA TRA 
when (e.id_tipologia_evento in (40,52,47,15,13,19,16,55,59,33,61) ) then  coalesce(to_char(evt_cambio_prop.data_cambio , 'dd/mm/yyyy'), '')-- DATA CAMBIO PROPRIETARIO
when  e.id_tipologia_evento =23 then coalesce(to_char(er.data_reimmissione , 'dd/mm/yyyy'), '')            -- DATA REIMISSIONE
when  e.id_tipologia_evento =41 then coalesce(to_char(erndt.data_ritrovamento_nd , 'dd/mm/yyyy'), '')      -- DATA RESTITUZIONE DIRETTA RITROVAMENTO NON DENUNCIATO
when  e.id_tipologia_evento =11 then coalesce(to_char(es.data_smarrimento, 'dd/mm/yyyy'), '')              -- DATA SMARRIMENTO
when  e.id_tipologia_evento =4  then coalesce(to_char(ef.data_furto, 'dd/mm/yyyy'), '')                    -- DATA FURTO
when  e.id_tipologia_evento =12 then coalesce(to_char(evt_rit.data_ritrovamento, 'dd/mm/yyyy'), '')        -- DATA RITROVAMENTO    
when  e.id_tipologia_evento =9  then coalesce(to_char(ed.data_decesso , 'dd/mm/yyyy'), '')                 -- DATA DECESSO   
when (e.id_tipologia_evento in (21,68) ) then  coalesce(to_char(evt_morsicatura.data_morsicatura , 'dd/mm/yyyy'), '')-- DATA MORSICATURA
else '' end as dtEvento,
case 
when  e.id_tipologia_evento =38 then coalesce(to_char(eim.data_inserimento_microchip , 'dd/mm/yyyy'), '')  -- DATA APPL. MICROCHIP 
when  e.id_tipologia_evento in(18,45,31,56,24) then coalesce(to_char(evt_cambio_det.data_cambio_detentore , 'dd/mm/yyyy'), '')  -- DATA TRA 
when (e.id_tipologia_evento in ( 40,52,47,15,13,19,16,55,59,33,61) ) then  coalesce(to_char(evt_cambio_prop.data_cambio , 'dd/mm/yyyy'), '')-- DATA CAMBIO PROPRIETARIO
when  e.id_tipologia_evento =23 then coalesce(to_char(er.data_reimmissione , 'dd/mm/yyyy'), '')            -- DATA REIMISSIONE
when  e.id_tipologia_evento =41 then coalesce(to_char(erndt.data_ritrovamento_nd , 'dd/mm/yyyy'), '')      -- DATA RESTITUZIONE DIRETTA RITROVAMENTO NON DENUNCIATO
when  e.id_tipologia_evento =11 then coalesce(to_char(es.data_smarrimento, 'dd/mm/yyyy'), '')              -- DATA SMARRIMENTO
when  e.id_tipologia_evento =4 then coalesce(to_char(ef.data_furto, 'dd/mm/yyyy'), '')                     -- DATA FURTO
when  e.id_tipologia_evento =12 then coalesce(to_char(evt_rit.data_ritrovamento, 'dd/mm/yyyy'), '')        -- DATA RITROVAMENTO    
when  e.id_tipologia_evento =9 then coalesce(to_char(ed.data_decesso , 'dd/mm/yyyy'), '')                  -- DATA DECESSO 
when (e.id_tipologia_evento in (21,68) ) then  coalesce(to_char(evt_morsicatura.data_morsicatura , 'dd/mm/yyyy'), '')-- DATA MORSICATURA   
else '' end as dtComunicazioneAutorita,
'' dtVerbale,
'' numVerbale,
'' ubiXml,
case when ( e.id_tipologia_evento = 38) then  -- INSERIMENTO SECONDO MICROCHIP -- APM
'<apmTO>'||
'<anmMicrochip>'||eim.numero_microchip_assegnato||'</anmMicrochip>'||
'<tzaCodice>Z99</tzaCodice>'||
'<idFiscaleVet>'||COALESCE(NULLIF(contact_utente_ins_microchip.codice_fiscale, ''),contact_ins_reg.codice_fiscale,'99999999909')||'</idFiscaleVet>'|| -- vetidfisc quale tipologia veterinario
'<note></note>'||
'</apmTO >' else '' END as apmXml,
case when ( e.id_tipologia_evento in ( 18,45,31,56,24)) then  --- TRA
'<traTO>' 
'<idFiscalePrp>'||case when l_det_evttra.id_linea_produttiva=1 then coalesce(osf_det_evttra.codice_fiscale,'') else '' end || '</idFiscalePrp>'|| -- PROPRIETARIO PRIVATO
'<canCodice>'||case  when (l_det_evttra.id_linea_produttiva=4 or l_det_evttra.id_linea_produttiva=5 or l_det_evttra.id_linea_produttiva=6 or l_det_evttra.id_linea_produttiva=8)then 'inserire codice della stuttura CODSINAFF' else '' end || '</canCodice>'|| -- canCodice  PROPRIETARIO STRUTTURA PRIVATA 
'<ubiVar>S</ubiVar>'||
'<ubiIndirizzo>'||  regexp_replace(public.unaccent(coalesce(oi_det_evttra.via,'') ),'[\°\\''\²\/]',' ','g') ||'</ubiIndirizzo>'||
'<ubiComIstat>'|| coalesce(c_det_evttra.cod_comune,'') ||'</ubiComIstat>'||
'<ubiProSigla>'||coalesce(prov_det_evttra.cod_provincia,'')||'</ubiProSigla>'||
'<ubiCap>'||       COALESCE(NULLIF(oi_det_evttra.cap, ''), c_det_evttra.cap,'') ||'</ubiCap>'||
'<causaleCodice>008</causaleCodice>' ||
'<note></note>' ||
'</traTO>' 
else '' end as traXml,
case when ( e.id_tipologia_evento in ( 40,52,47,15,13,19,16,55,59,33,61)) then  ---PASSAGGIO DI PROPRIEA'-- CES
'<cesTO>' 
'<idFiscalePrp>'||case when l_evtcsm.id_linea_produttiva=1 then coalesce(osf_evtcsm.codice_fiscale,'') else '' end || '</idFiscalePrp>'|| -- PROPRIETARIO PRIVATO
'<canCodice>'||case  when (l_evtcsm.id_linea_produttiva=4 or l_evtcsm.id_linea_produttiva=5 or l_evtcsm.id_linea_produttiva=6 or l_evtcsm.id_linea_produttiva=8)then 'inserire codice della stuttura CODSINAFF' else '' end || '</canCodice>'|| -- canCodice  PROPRIETARIO STRUTTURA PRIVATA 
'<comIstat>'||case  when (l_evtcsm.id_linea_produttiva=3 or l_evtcsm.id_linea_produttiva=7 )then coalesce(c_evtcsm.cod_comune,'')   else '' end || '</comIstat>'||-- PROPRIETARIO SINDACO e I SINDACI FUORI REGIONE
'<proSigla>'||case  when (l_evtcsm.id_linea_produttiva=3 or l_evtcsm.id_linea_produttiva=7 )then  coalesce(prov_evtcsm.cod_provincia,'')  else '' end || '</proSigla>' ||
 case
  when ( e.id_tipologia_evento  in ( 40,52)  ) then 
     '<ubiVar></ubiVar><ubiIndirizzo></ubiIndirizzo><ubiComIstat></ubiComIstat><ubiProSigla></ubiProSigla><ubiCap></ubiCap><causaleCodice>007</causaleCodice><detPerIdFiscale></detPerIdFiscale><detCanCodice></detCanCodice><note></note></cesTO>'
   when ( e.id_tipologia_evento  in ( 47,15,13,19,16,55,59,33,61)  ) and  (evt_cambio_prop.id_nuovo_detentore is not null or evt_cambio_prop.id_nuovo_detentore!=''  or evt_cambio_prop.id_nuovo_detentore!='-1') and ( e.id_detentore_corrente::text!=evt_cambio_prop.id_nuovo_detentore )then 
   '<ubiVar>S</ubiVar><ubiIndirizzo>'|| regexp_replace(public.unaccent(coalesce(oi_det_evtcsm.via,'') ),'[\°\\''\²\/]',' ','g') ||'</ubiIndirizzo><ubiComIstat>'|| coalesce( c_det_evtcsm.cod_comune,'')||'</ubiComIstat><ubiProSigla>'||coalesce( prov_det_evtcsm.cod_provincia,'')||'</ubiProSigla><ubiCap>'||      COALESCE(NULLIF(oi_det_evtcsm.cap, ''), c_det_evtcsm.cap,'')        ||'</ubiCap>'||
   '<causaleCodice>007</causaleCodice>' ||
   '<detPerIdFiscale>'||case when l_det_evtcsm.id_linea_produttiva=1 then coalesce(osf_det_evtcsm.codice_fiscale,'') else '' end || '</detPerIdFiscale> '
   '<detCanCodice>'||case  when (l_det_evtcsm.id_linea_produttiva=4 or l_det_evtcsm.id_linea_produttiva=5 or l_det_evtcsm.id_linea_produttiva=6 or l_det_evtcsm.id_linea_produttiva=8) then 'inserire codice della stuttura CODSINAFF' else '' END|| '</detCanCodice><note></note></cesTO>'
  else '<ubiVar></ubiVar><ubiIndirizzo></ubiIndirizzo><ubiComIstat></ubiComIstat><ubiProSigla></ubiProSigla><ubiCap></ubiCap><causaleCodice>007</causaleCodice><detPerIdFiscale></detPerIdFiscale><detCanCodice></detCanCodice><note></note></cesTO>'
 end 
else '' end as cesXml,
case when ( e.id_tipologia_evento = 23) then  -- REIMISSIONE -- REI
'<reiTO>'||
'<comIstat>'||coalesce(c2.cod_comune,'')||'</comIstat>'|| 
'<proSigla>'||coalesce(lp.cod_provincia,'')||'</proSigla>'||
'<localita>'||coalesce(er.luogo, '')||'</localita>'||
'<flagCollareFosforescente>'||''||'</flagCollareFosforescente>'||
'<note><note>'||
'</reiTO>' else '' END as reiXml,
case when ( e.id_tipologia_evento = 41  ) then -- RITROVAMENTO NON DENUNCIATO
'<resTO>>'||
'<comIstat>'||coalesce(c_ritnd.cod_comune,'')||'</comIstat>'|| 
'<proSigla>'||coalesce(lpritnd.cod_provincia,'')||'</proSigla>'||
'<localita>'||coalesce(erndt.luogo_ritrovamento_nd, '')||'</localita>'||
'<note><note>'||
'</resTO>>' else '' END as resXml,


case when ( e.id_tipologia_evento in (21,68)) then  -- MORSICATURA -- MRS
'<mrsTO>'||
'<comIstat></comIstat>'||
'<proSigla></proSigla>'||
'<localita></localita>'||
'<vittima>' || case when evt_morsicatura.tipologia = 1 then 'A' when evt_morsicatura.tipologia = 2 then 'P' else '' end || '</vittima>'||
'<vittimaIdFiscale></vittimaIdFiscale>'||
'<vittimaMicrochip></vittimaMicrochip>'||
'<vittimaTatuaggio></vittimaTatuaggio>'||
'<vittimaNome></vittimaNome>'||
'<flagVaccinazione>' || case when vacc.id_evento is null or vacc.id_evento <= 0 then 'N' else 'S' end  || '</flagVaccinazione>'||
'<flagRabido></flagRabido>'||
'<flagPericoloso></flagPericoloso>'||
'<flagSoloAggressione>' || case when e.id_tipologia_evento = 21 then 'N' when  e.id_tipologia_evento = 68 then 'S'  end || '</flagSoloAggressione>'||
'<tipoOsservazione></tipoOsservazione>'||
'<dtInizioOss></dtInizioOss>'||
'<dtFineOss></dtFineOss>'||
'<canCodiceOss></canCodiceOss>'||
'<perIdFiscaleOss></perIdFiscaleOss>'||
'<tipoUbicazione></tipoUbicazione>'||
'<esitoCodice></esitoCodice>'||
'<dtEsito></dtEsito>'||
'<visite>'||
'<visita>'||
'<vetIdFiscale></vetIdFiscale>'||
'<dtVisita></dtVisita>'||
'<dtComunicazione></dtComunicazione>'||
'<numVerbale></numVerbale>'||
'<dtVerbale></dtVerbale>'||
'</visita>'||
'</visite>'||
'<note></note>'||
'<valutazioneStatoPsicoFisico></valutazioneStatoPsicoFisico>'||
'<valutazioneGestioneProp></valutazioneGestioneProp>'||
'<valutazioneComplessiva></valutazioneComplessiva>'||
'<valutazioneDetenzione></valutazioneDetenzione>'||
'<carenzaRicovero></carenzaRicovero>'||
'<carenzaMovimento></carenzaMovimento>'||
'<carenzaContattiSociali></carenzaContattiSociali>'||
'<altreCarenze></altreCarenze>'||
'<dettaglioVittima></dettaglioVittima>'||
'<dettaglioAccaduto></dettaglioAccaduto>'||
'<contestoAccaduto></contestoAccaduto>'||
'</mrsTO>' else '' END as mrsXml,

case when ( e.id_tipologia_evento = 4 or  e.id_tipologia_evento = 11 ) then -- SMARRIMENTO  -- FURTO -- SMA
'<smaTO>'||
'<comIstat>'||''||'</comIstat>'|| --  NON ABBIAMO QUESTA INFORMAZIONE
'<proSigla>'||''||'</proSigla>'||
'<localita>'||case when e.id_tipologia_evento = 11 then coalesce(es.luogo_smarrimento, '')  when e.id_tipologia_evento = 4 then coalesce(ef.luogo_furto, '')else '' end ||'</localita>'||
'<flagFurto>'||case when e.id_tipologia_evento = 11 then 'N' when e.id_tipologia_evento = 4 then 'S'  else '' end ||'</flagFurto>'||
'<note>'||''||'</note>'||
'</smaTO>'
 else '' end as smaXml,
case when ( e.id_tipologia_evento = 12  ) then -- RITROVAMENTO
'<ritTO>'||
'<comIstat>'||coalesce(c_ritrov.cod_comune,'')||'</comIstat>'|| 
'<proSigla>'||coalesce(lprit.cod_provincia,'')||'</proSigla>'||
'<localita>'||coalesce(evt_rit.luogo_ritrovamento, '')||'</localita>'||
'<note><note>'||
'</ritTO>' else '' END as ritXml,
case when ( e.id_tipologia_evento =9) then  --- DECESSO -- MOR
'<morTO>'||
'<timCodice>TM'||ltd.code||'</timCodice>'|| --  VERIFICARE LE LOOKUP DELLE CODICIFICHE DI SINAAF E BDU
'<idFiscaleVet>'||''||'</idFiscaleVet>'|| --IN BDU NON GESTIAMO QUESTO CAMPO
'<note>'||''||'</note>'||
'</morTO>'
 else '' end as morXml,
 case   
when ( (e.id_tipologia_evento in ( 40,52,47,15,13,19,16,55,59,33,61) and  (evt_cambio_prop.id_nuovo_proprietario is  null or evt_cambio_prop.id_nuovo_proprietario='-1')) or
	   (e.id_tipologia_evento in ( 40,52,47,15,13,19,16,55,59,33,61) and  (evt_cambio_prop.id_evento is  null ) )
    ) then true
when ( e.id_tipologia_evento = 38 and (eim.numero_microchip_assegnato is null or eim.numero_microchip_assegnato ='')     ) then true
else false end as evento_da_eliminare,
e.id_evento
from  evento  e 
join animale an on e.id_animale =an.id
join lookup_tipologia_registrazione t on t.code = e.id_tipologia_evento 
left join evento_smarrimento es on es.id_evento =e.id_evento 
left join evento_furto ef  on ef.id_evento =e.id_evento 
left join evento_reimmissione er on er.id_evento =e.id_evento 
left join evento_inserimento_microchip eim on eim.id_evento =e.id_evento  and  (e.id_tipologia_evento = 38)
left join evento_decesso ed on ed.id_evento = e.id_evento 
left join evento_ritrovamento evt_rit on evt_rit.id_evento =e.id_evento
left join evento_ritrovamento_non_denunciato erndt on  erndt.id_evento =e.id_evento 
left join comuni1 c_ritnd on erndt.comune_ritrovamento_nd =c_ritnd.id  
left join lookup_province lpritnd on lpritnd.code=c_ritnd.cod_provincia::int 
left join comuni1 c_ritrov on evt_rit.comune_ritrovamento =c_ritrov.id  
left join lookup_province lprit on lprit.code =c_ritrov.cod_provincia::int 
left join comuni1 c2  on er.id_comune_reimmissione =c2.id 
left join lookup_province lp on lp.code =c2.cod_provincia::int 
left join access_ utente_ins_microchip on utente_ins_microchip.user_id= eim.id_veterinario_privato_inserimento_microchip
left join contact_ contact_utente_ins_microchip on contact_utente_ins_microchip.contact_id= utente_ins_microchip.contact_id

left join access_ utente_ins_reg on utente_ins_reg.user_id = e.id_utente_inserimento  and utente_ins_reg.role_id =24 
left join contact_ contact_ins_reg on contact_ins_reg.contact_id= utente_ins_reg.contact_id 

left join lookup_tipologia_decesso ltd on ed.id_causa_decesso =ltd.code and  ltd.enabled
LEFT join 
(
select  id_evento ,ecd.data_cambio_detentore data_cambio_detentore ,ecd.id_detentore::TEXT id_nuovo_detentore from evento_cambio_detentore ecd   -- tipologia 18
union 
select  id_evento ,erap.data_restituzione data_cambio_detentore ,erap.id_detentore::TEXT id_nuovo_detentore from evento_restituzione_a_proprietario erap   -- tipologia 45
union 
select id_evento ,etc.data_trasferimento_canile data_cambio_detentore ,etc.id_canile_detentore::TEXT id_nuovo_detentore from evento_trasferimento_canile etc  -- tipologia 31
union 
select id_evento ,ec.data_cattura data_cambio_detentore ,ec.id_detentore_cattura ::TEXT id_nuovo_detentore from evento_cattura  ec where flag_ricattura is true -- tipologia 24
union 
select id_evento ,erc.data_restituzione_canile  data_cambio_detentore ,erc.id_canile  ::TEXT id_nuovo_detentore from evento_restituzione_a_canile  erc -- tipologia 56
)
evt_cambio_det on evt_cambio_det.id_evento=e.id_evento 
left join opu_relazione_stabilimento_linee_produttive l_det_evttra  on l_det_evttra.id::text =evt_cambio_det.id_nuovo_detentore
left join opu_stabilimento os_det_evttra on os_det_evttra.id =l_det_evttra.id_stabilimento  
left join opu_soggetto_fisico osf_det_evttra on osf_det_evttra.id =os_det_evttra.id_soggetto_fisico 
left join opu_indirizzo oi_det_evttra on oi_det_evttra.id =os_det_evttra.id_indirizzo 
left join comuni1 c_det_evttra on c_det_evttra.id= oi_det_evttra.comune 
left join lookup_province prov_det_evttra on prov_det_evttra.code = c_det_evttra.cod_provincia ::int
LEFT join 
(
select id_evento , id_proprietario_fuori_regione_solo_prop  id_nuovo_proprietario ,data_trasferimento_fuori_regione_solo_proprietario data_cambio,'' as id_nuovo_detentore from 
 evento_trasferimento_fuori_regione_solo_proprietario etfrsp -- tipologia 40
union 
select id_evento ,ecpci.id_nuovo_proprietario_presa_cessione_import id_nuovo_proprietario ,ecpci.data_presa_in_carico_import data_cambio , '' as id_nuovo_detentore from evento_presa_in_carico_cessione_import ecpci -- tipologia 52
union 
select id_evento ,epcafa.id_nuovo_proprietario_presa_adozione_fa id_nuovo_proprietario ,epcafa.data_presa_in_carico_adozione_fa data_cambio, epcafa.id_nuovo_proprietario_presa_adozione_fa::TEXT id_nuovo_detentore
       from evento_presa_in_carico_adozione_fuori_asl epcafa-- tipologia 47
union 
select id_evento ,epcc.id_nuovo_proprietario_presa_cessione  id_nuovo_proprietario ,epcc.data_presa_in_carico data_cambio, epcc.id_nuovo_proprietario_presa_cessione::TEXT id_nuovo_detentore 
	   from evento_presa_in_carico_cessione epcc  
	   -- tipologia 15
union 
select id_evento ,eadc.id_proprietario_adozione  id_nuovo_proprietario ,eadc.data_adozione data_cambio ,eadc.id_proprietario_adozione::TEXT id_nuovo_detentore from evento_adozione_da_canile eadc -- tipologia 13
union 
select id_evento ,eadco.id_proprietario_adozione_colonia  id_nuovo_proprietario ,eadco.data_adozione_colonia data_cambio ,eadco.id_proprietario_adozione_colonia::TEXT id_nuovo_detentore from evento_adozione_da_colonia eadco -- tipologia 19
union 
select id_evento ,et.id_nuovo_proprietario  id_nuovo_proprietario ,et.data_trasferimento data_cambio ,et.id_nuovo_detentore::TEXT id_nuovo_detentore from evento_trasferimento et -- tipologia 16
union 
select id_evento ,ets.id_nuovo_proprietario  id_nuovo_proprietario ,ets.data_trasferimento data_cambio ,ets.id_nuovo_detentore::TEXT id_nuovo_detentore from evento_trasferimento_sindaco ets -- tipologia 55
union 
select  id_evento ,ec.id_nuovo_proprietario_cessione  id_nuovo_proprietario ,ec.data_cessione data_cambio ,ec.id_nuovo_detentore_cessione::TEXT id_nuovo_detentore from evento_cessione ec -- tipologia 59
)
evt_cambio_prop on evt_cambio_prop.id_evento=e.id_evento and evt_cambio_prop.id_nuovo_proprietario is not null and evt_cambio_prop.id_nuovo_proprietario!='-1'

LEFT join 
(
select id_evento , data_morso data_morsicatura, tipologia 
from evento_morsicatura emorsicatura 
union 
select id_evento , data_aggressione data_morsicatura, tipologia 
from evento_aggressione eggressione
)
evt_morsicatura on evt_morsicatura.id_evento=e.id_evento
left join evento e_vacc on e_vacc.id_animale = an.id and e_vacc.id_tipologia_evento = 36 and e_vacc.data_cancellazione is null and e_vacc.trashed_date is null 
left join evento_inserimento_vaccino vacc on e_vacc.id_evento = vacc.id_evento and vacc.id_tipo_vaccino = 1 and evt_morsicatura.data_morsicatura between vacc.data_inserimento_vaccinazione and vacc.data_scadenza_vaccino
left join opu_relazione_stabilimento_linee_produttive l_evtcsm on l_evtcsm.id =evt_cambio_prop.id_nuovo_proprietario 
left join opu_stabilimento os_evtcsm on os_evtcsm.id =l_evtcsm.id_stabilimento
left join opu_soggetto_fisico osf_evtcsm on osf_evtcsm.id =os_evtcsm.id_soggetto_fisico 
left join opu_indirizzo oi_prop_os_evtcsm on os_evtcsm.id_indirizzo =oi_prop_os_evtcsm.id 
left join comuni1 c_evtcsm on c_evtcsm.id= oi_prop_os_evtcsm.comune 
left join lookup_province prov_evtcsm on prov_evtcsm.code = c_evtcsm.cod_provincia ::int
left join opu_relazione_stabilimento_linee_produttive l_det_evtcsm on l_det_evtcsm.id::text =evt_cambio_prop.id_nuovo_detentore
left join opu_stabilimento os_det_evtcsm on os_det_evtcsm.id =l_det_evtcsm.id_stabilimento  
left join opu_soggetto_fisico osf_det_evtcsm on osf_det_evtcsm.id =os_det_evtcsm.id_soggetto_fisico 
left join opu_indirizzo oi_det_evtcsm on oi_det_evtcsm.id =os_det_evtcsm.id_indirizzo 
left join comuni1 c_det_evtcsm on c_det_evtcsm.id= oi_det_evtcsm.comune 
left join lookup_province prov_det_evtcsm on prov_det_evtcsm.code = c_det_evtcsm.cod_provincia ::int

where e.data_cancellazione is null and  e.trashed_date is null 
and  e.id_tipologia_evento in ( 23 ,11, 4,38,9,41,12 , 18,45,31,40,52,47 ,15,13,19,16,55,59,56,24,33,61,21,68) 
and an.id >= 0 and (an.flag_decesso = false or an.flag_decesso is null) --  si escludono animali i deceduti , si considerano  solo i vivi
and  (an.id_asl_riferimento!=14 and an.stato not IN(30,31,32,33,34,35,56,57,62,63,64,65,66,71,72,79,80,6)) -- si escludono i fuori regione, si considera sia la'sl del cane che il suo stato
--and e.id_evento not in (12578475,12679542,12569085,12242466,12578799,12265867,12515173,12378066,12554144,12515174,12632485,12578804,12578469,12578800)--evento_cattura ricattura tipologia 24 presente l’id_evento evento e non presente nella tabella evento_cattura ricattura 
) 
a
where evento_da_eliminare is false