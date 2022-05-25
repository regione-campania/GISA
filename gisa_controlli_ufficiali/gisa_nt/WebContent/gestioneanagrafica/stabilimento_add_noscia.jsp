<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="NazioniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ProvinceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="TipoImpresaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipoCarattere" class="org.aspcfs.utils.web.LookupList" scope="request" />


<jsp:useBean id="ListaStati" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
  <script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>
  


<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script f src="dwr/interface/SuapDwr.js"> </script>

<script>

function gestisciProvenienzaEstera(val){
	if (val.checked || val==true){
		document.getElementById("codice_fiscale").readOnly  = false;
		document.getElementById("calcoloCF").style.background = 'grey';
		document.getElementById("calcoloCF").disabled = 'disabled';
	}
	else {
		document.getElementById("codice_fiscale").readOnly  = true;
		document.getElementById("calcoloCF").style.background = '';
		document.getElementById("calcoloCF").disabled = '';
	}
	document.getElementById("codice_fiscale").value  = '';
}

function recuperaAslDaComune(idComune)
{
	PopolaCombo.getAslDaComune(idComune,{callback:recuperaAslDaComuneCallback,async:false}) ;
}

function recuperaAslDaComuneCallback(value)
{
	document.getElementById("idAslStabRecuperata").value = value;
}

function PopolaListaComuniOperativa(idAsl)
{
	PopolaCombo.getValoriComboComuni1Asl(idAsl,{callback:PopolaListaComuniOperativaCallback,async:false}) ;
	
}

function PopolaListaComuniOperativaCallback(value)
{
	var valueCodici = value[0];
	var valueNomi = value[1];
	
	var select = "<select id=\"idComuneStab\" name=\"idComuneStab\" onChange=\"resetCampiOperativa()\">";
	for (i = 1; i<valueCodici.length;i++){
		select= select+ "<option value=\""+valueCodici[i]+"\">"+valueNomi[i]+"</option>";
	}
	var select = select + "</select>";
	document.getElementById("dividComuneStab").innerHTML = select;
	sortSelect(document.getElementById("idComuneStab"));
	
	resetCampiOperativa();
}


function PopolaListaComuniLegale(idProvincia)
{
	PopolaCombo.getValoriComboComuni1Provincia(idProvincia,{callback:PopolaListaComuniLegaleCallback,async:false}) ;
	
}

function PopolaListaComuniLegaleCallback(value)
{
	var valueCodici = value[0];
	var valueNomi = value[1];
	
	var select = "<select id=\"idComune\" name=\"idComune\" onChange=\"resetCampiLegale()\">";
	for (i = 1; i<valueCodici.length;i++){
		select= select+ "<option value=\""+valueCodici[i]+"\">"+valueNomi[i]+"</option>";
	}
	var select = select + "</select>";
	document.getElementById("dividComune").innerHTML = select;
	sortSelect(document.getElementById("idComune"));
	
	resetCampiLegale();
}

function PopolaListaComuniRappresentanteResidenza(idProvincia)
{
	PopolaCombo.getValoriComboComuni1Provincia(idProvincia,{callback:PopolaListaComuniRappresentanteResidenzaCallback,async:false}) ;
	
}

function PopolaListaComuniRappresentanteResidenzaCallback(value)
{
	var valueCodici = value[0];
	var valueNomi = value[1];
	
	var select = "<select id=\"idComuneSoggetto\" name=\"idComuneSoggetto\" onChange=\"resetCampiRappresentanteResidenza()\">";
	select= select+ "<option value=\"-1\">---SELEZIONARE---</option>";
	for (i = 1; i<valueCodici.length;i++){
		select= select+ "<option value=\""+valueCodici[i]+"\">"+valueNomi[i]+"</option>";
	}
	var select = select + "</select>";
	document.getElementById("dividComuneSoggetto").innerHTML = select;
	sortSelect(document.getElementById("idComuneSoggetto"));
	
	resetCampiRappresentanteResidenza();
}

function calcolaCap(idComune, topon, indir, campo){	
	SuapDwr.getCap(idComune, topon, indir, campo,{callback:calcolaCapCallBack,async:false});
}

function showCoordinate(address,city,prov,cap,campo_lat,campo_long){
	campoLat = campo_lat;
	campoLong = campo_long;
	Geocodifica.getCoordinate(address,city,prov,cap,'','','',setGeocodedLatLonCoordinate);
}

function setGeocodedLatLonCoordinate(value){
	campoLat.value = value[1];;
	campoLong.value =value[0];
}


function getSelectedText(elementId) {
    var elt = document.getElementById(elementId);
    if (elt.selectedIndex == -1)
        return null;
    return elt.options[elt.selectedIndex].text;
}

function calcolaCapCallBack(val){
	var campo = val[1];
	var value = val[0];
	document.getElementById(campo).value = value;
}

function sortSelect(selElem) {
    var tmpAry = new Array();
    for (var i=0;i<selElem.options.length;i++) {
        tmpAry[i] = new Array();
        tmpAry[i][0] = selElem.options[i].text;
        tmpAry[i][1] = selElem.options[i].value;
    }
    tmpAry.sort();
    while (selElem.options.length > 0) {
        selElem.options[0] = null;
    }
    for (var i=0;i<tmpAry.length;i++) {
        var op = new Option(tmpAry[i][0], tmpAry[i][1]);
        selElem.options[i] = op;
    }
    return;
}

function checkForm(form){
	
	var ragione = form.ragione_sociale.value;
	var iva = form.piva.value;
// 	var codfisc = form.codfisc.value;
	var pec = form.pec.value;
	var provlegale = form.idProvincia.value;
	var vialegale = form.via.value;
	var caplegale = form.cap.value;
	var civicolegale = form.civico.value;
	var latlegale = form.latitudine.value;
	var lonlegale = form.longitudine.value;
	var cf = form.codice_fiscale.value;
	var viastab = form.viaStab.value;
	var capstab = form.capStab.value;
	var latstab = form.latitudineStab.value;
	var lonstab = form.longitudineStab.value;
	var civicostab= form.civicoStab.value;
	var nome = form.nome.value;
	var cognome = form.cognome.value;
	var provsoggetto = form.idProvinciaSoggetto.value;
	var viasoggetto = form.viaSoggetto.value;
	var capsoggetto = form.capSoggetto.value;
	var civicosoggetto = form.civicoSoggetto.value;
	var datanascitasoggetto = form.data_nascita.value;
	var idLinea = form.idLineaProduttiva.value;
	var dataInizio = form.dataInizioLinea.value;
	
	var esito = true;
	
	form.ragione_sociale.value = nome + " " + cognome;
	
	msg = 'Attenzione. Compilare i seguenti campi:';
	

	if (cf=='' && iva == ''){
		msg+="\nInserire almeno uno tra PARTITA IVA e CODICE FISCALE";
		esito = false;
	}
		
	if (viastab==''){
		msg+="\nLUOGO DEL CONTROLLO - Indirizzo";
		esito = false;
	}
	if (capstab==''){
		msg+="\nLUOGO DEL CONTROLLO - CAP";
		esito = false;
	}
	if (civicostab==''){
		msg+="\nLUOGO DEL CONTROLLO - Civico";
		esito = false;
	}
	
	if (nome==''){
		msg+="\nSOGGETTO - Nome";
		esito = false;
	}
	
	if (cognome==''){
		msg+="\nSOGGETTO - Cognome";
		esito = false;
	}
	
	if (cf==''){
		msg+="\nSOGGETTO - Codice fiscale";
		esito = false;
	}
	
	if (provsoggetto=='-1'){
		msg+="\nRESIDENZA - Provincia";
		esito = false;
	}
	if (viasoggetto==''){
		msg+="\nRESIDENZA - Indirizzo";
		esito = false;
	}
	if (capsoggetto==''){
		msg+="\nRESIDENZA - CAP";
		esito = false;
	}
	if (civicosoggetto==''){
		msg+="\nRESIDENZA - Civico";
		esito = false;
	}
	
	
	//CONTROLLO MAGGIORENNE
	var anni18fa = new Date();
	anni18fa.setFullYear(anni18fa.getFullYear()-18);
	var dateParts = datanascitasoggetto.split("/");
	var dateObject = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]); // month is 0-based
	var dataNascita = dateObject.toString();
	
	if( (new Date(dataNascita).getTime()) >= (new Date(anni18fa).getTime()) ){
		msg+="\nSOGGETTO - Età inferiore a 18 anni";
		esito = false;
	}

	if (esito==false){
		alert(msg);
		return false;
	}
	
	//CONTROLLO SE HA CAMBIATO I DATI DEL CODICE FISCALE
	var cfOld = cf;
	document.getElementById("calcoloCF").click();
	cf = form.codice_fiscale.value;
	
	if (cf!=cfOld){
		alert('ATTENZIONE. Il codice fiscale è stato modificato dal sistema. Ricontrollare i dati prima di proseguire.');
		return false;
	}
	
	
	if (confirm('PROSEGUIRE?')){
		loadModalWindow();
		form.submit();
	}
}


function popolaCampi(form){
	
	 var nomi = ["Rito", "Bartolo", "Stofano", "Alessandro", "Uolter", "Antonio", "Carmelo", "Viviano", "Valentino", "Tiziano", "Alfonso", "Domenico", "Antonio", "Mimmo", "Paolo", "Pippo", "Pluto", "Paperino", "Tizio", "Caio", "Sempronio", "Luca", "Mario", "Davide", "Giuseppe", "Ezio", "Vincenzo", "Rosario"];
	 var cognomi = ["Mele", "Sansone", "Squitiero", "Guida", "Amante", "Riviezzo", "Liguori", "Riviezzo-Liguori", "Paolillo", "Perropane", "Avallone", "De Simone", "Rossi", "Bianchi", "Carfora", "Rossi", "Bianchi", "Verdi", "Higuain", "Esposito", "Tizio", "Caio", "Sempronio", "Perrella", "Sito"];
	 var comuni = ["NAPOLI", "BACOLI", "SALERNO", "CAPRI", "TORRE ANNUNZIATA", "ROCCAPIEMONTE", "PORTICI", "CASERTA", "BENEVENTO", "FIRENZE", "SAPRI"];
	 
	 var ragione1 = ["Impresa", "Cooperativa", "Stabilimento", "Supermercato", "Angolo", "Caffe", "Bar", "Toasteria", "Rinfresco", "Dopolavoro", "Signor", "Attivita", "Polisportiva", "Ristoro", "Angolo", "Pizzeria", "Addu", "COOP", "Anagrafica", "Stanza", "Ufficio", "Palazzo", "Isola", "Distributore", "AB", "A pizza do"];
	 var ragione2 = ["Detail", "Ciao Domenico", "Direzionale", "Walter Table", "Digemon",  "Gisadev", "Martina", "Ciampa", "US", "Pomigliano",  "Marenna", "Sale e pepe", "Gigione", "Giuseppone", "Help Desk", "Uolter", "Riappallottolamelo",  "Cafesinho", "Hanny", "Starlight", "Peperoncino", "Sapori", "OVS", "IKEA", "Peperoncino", "Margherita", "Broccoli e salsiccia", "Ortolana", "Fiocco", "Brioche", "Wurstel e Patatine", "Allimited", "Cafone", "Grasso", "Tittore"];
	 var ragione3 = ["Altrove", "di Paperopoli", "in mezzo alle scale", "fuori la porta",  "al cancello di Arnone", "Dispersa", "Inc.", "SPA", "degli interni", "abusiva", "Del Centro",  "nel sacco", "Food", "a mare", "scondita", "Saporita", "Privata", "Con Sorpresa", "di Napoli", "Quarto Piano", "Softuer", "Birichino", "Coffee", "Col parmigiano", "Senza peperoni", "Senza broccoli", "Di colore rosso", "Di colore bianco", "Arcobaleno", "Sette stelle", "Maionese a parte", "Piccante", "Di Cerciello Carmine"];
	 	 
	 var via = ["roma", "degli aranci", "nolana", "della vittoria", "dei giardini", "stretto", "porzio", "duomo", "san gennaro", "san pasquale a chiaia", "chiusa", "Meridionale"];
	 
	 var toponimi = ["4","22","58","62","76","81","100","104","106","128","136","140","143","144","153","157","194","198","204","205","215"];
	 
	 var idComuniNapoli =["5231","5232","5233","5234","5235","5236","5237","5238","5239","5240","5241","5242","5243","5244","5245","5246","5247","5248","5249","5250","5251","5252","5253","5254","5255","5256","5257","5258","5259","5260","5261","5262","5263","5264","5265","5266","5267","5268","5269","5270","5271","5272","5273","5274","5275","5276","5277","5278","5279","5280","5281","5282","5283","5284","5285","5286","5287","5288","5289","5290","5291","5292","5293","5294","5295","5296","5297","5298","5299","5300","5301","5302","5303","5304","5305","5306","5307","5308","5309","5310","5311","5312","5313","5314","5315","5316","5317","5318","5319","5320","5321","5322"];
	 var idComuniAvellino = ["5323","5324","5325","5326","5327","5328","5329","5330","5331","5332","5333","5334","5335","5336","5337","5338","5339","5340","5341","5342","5343","5344","5345","5346","5347","5348","5349","5350","5351","5352"];	 
	 var randomiva = "";
	 var possible = "0123456789";

	 for (var i = 0; i < 11; i++)
		  randomiva += possible.charAt(Math.floor(Math.random() * possible.length));
	 
	 /* IMPRESA */
	 form.ragione_sociale.value=ragione1[Math.floor((Math.random() * ragione1.length-1) + 1)]+' '+ragione2[Math.floor((Math.random() * ragione2.length-1) + 1)]+' '+ragione3[Math.floor((Math.random() * ragione3.length-1) + 1)] + ' ' + Math.floor((Math.random() * 9998) + 1);
	 form.piva.value=randomiva;
// 	 form.codfisc.value=randomiva;
	 form.note.value='aaaaaaaa note '+ form.ragione_sociale.value + 'bla bla bla aaaaa';

	 /* SEDE LEGALE */	
	 form.idProvincia.value='63';
	 form.idComune.value=idComuniNapoli[Math.floor((Math.random() * idComuniNapoli.length-1) + 1)];
	 form.toponimo.value=toponimi[Math.floor((Math.random() * toponimi.length-1) + 1)];
	 form.via.value=via[Math.floor((Math.random() * via.length-1) + 1)];
	 form.civico.value=Math.floor((Math.random() * 100) + 1);
	 document.getElementById("bottoneCap").click();
	 form.latitudine.value='40.9452161';
	 form.longitudine.value='14.3695827';

	 /* LEGALE RAPPRESENTANTE */
	 form.nome.value=nomi[Math.floor((Math.random() * nomi.length-1) + 1)] + " " + nomi[Math.floor((Math.random() * nomi.length-1) + 1)];
	 form.cognome.value=cognomi[Math.floor((Math.random() * cognomi.length-1) + 1)];
	 form.nomeComuneNascita.value=comuni[Math.floor((Math.random() * comuni.length-1) + 1)];
	 form.data_nascita.value= Math.floor((Math.random() * 10) + 10)+"/0"+Math.floor((Math.random() * 8) + 1)+"/199"+Math.floor((Math.random() * 8) + 1);
	 form.telefono.value=randomiva;
	 form.email.value=form.cognome.value+'.'+form.nome.value+'@soggetto.email.com';
	 form.documento_identita.value='docidentaaaaa';
	 form.pec.value=form.cognome.value+'.'+form.nome.value+'@impresa.email.com';
	 document.getElementById("calcoloCF").click();

	 /* RESIDENZA RAPPRESENTANTE */
	 form.idProvinciaSoggetto.value='63';
	 form.idComuneSoggetto.value=idComuniNapoli[Math.floor((Math.random() * idComuniNapoli.length-1) + 1)];
	 form.toponimoSoggetto.value=toponimi[Math.floor((Math.random() * toponimi.length-1) + 1)];
	 form.viaSoggetto.value=via[Math.floor((Math.random() * via.length-1) + 1)];
	 form.civicoSoggetto.value=Math.floor((Math.random() * 100) + 1);
	 document.getElementById("bottoneCapSoggetto").click();

	 /* SEDE OPERATIVA */
	 form.idAslStab.value="201";
	 form.idAslStab.onchange();
	 form.idComuneStab.value=idComuniAvellino[Math.floor((Math.random() * idComuniAvellino.length-1) + 1)];
	 form.toponimoStab.value=toponimi[Math.floor((Math.random() * toponimi.length-1) + 1)];
	 form.viaStab.value=via[Math.floor((Math.random() * via.length-1) + 1)];
	 form.civicoStab.value=Math.floor((Math.random() * 100) + 1);
	 document.getElementById("bottoneCapStab").click();
	 form.latitudineStab.value='40.9452161';
	 form.longitudineStab.value='14.3695827';

	 /* LINEA PRODUTTIVA */
	 form.dataInizioLinea.value= Math.floor((Math.random() * 10) + 10)+"/0"+Math.floor((Math.random() * 8) + 1)+"/198"+Math.floor((Math.random() * 8) + 1);
	 //form.dataFineLinea.value= Math.floor((Math.random() * 10) + 10)+"/0"+Math.floor((Math.random() * 8) + 1)+"/199"+Math.floor((Math.random() * 8) + 1);
	 form.cun.value='CUN'+randomiva;
	

	 	
	
}

function resetCampiOperativa(){
	document.getElementById("viaStab").value='';
	document.getElementById("civicoStab").value='';
	document.getElementById("longitudineStab").value='';
	document.getElementById("latitudineStab").value='';
	document.getElementById("capStab").value='';
	document.getElementById("toponimoStab").value='100';

}

function resetCampiLegale(){
	document.getElementById("via").value='';
	document.getElementById("civico").value='';
	document.getElementById("longitudine").value='';
	document.getElementById("latitudine").value='';
	document.getElementById("cap").value='';
	document.getElementById("toponimo").value='100';
}

function resetCampiRappresentanteResidenza(){
	document.getElementById("viaSoggetto").value='';
	document.getElementById("civicoSoggetto").value='';
	document.getElementById("capSoggetto").value='';
	document.getElementById("toponimoSoggetto").value='100';

}

function copiaDaResidenzaALegale(form){
	var idProvinceCampania = ["62","61","64", "63","65"];

	if (!idProvinceCampania.includes(form.idProvinciaSoggetto.value)){ 
		alert('Comune non in Campania.');
		return false;
	}
	
	 form.idProvincia.value=form.idProvinciaSoggetto.value;
	 form.idComune.value=form.idComuneSoggetto.value;
	 form.toponimo.value=form.toponimoSoggetto.value;
	 form.via.value=form.viaSoggetto.value;
	 form.civico.value=form.civicoSoggetto.value;
	 form.cap.value = form.capSoggetto.value;
	 form.longitudine.value="";
	 form.latitudine.value="";
		
}

function copiaDaResidenzaAOperativa(form){
	
	var idProvinceCampania = ["62","61","64", "63","65"];

	if (!idProvinceCampania.includes(form.idProvinciaSoggetto.value)){ 
		alert('Comune non in Campania.');
		return false;
	}
	
		if (form.idComuneSoggetto.value!=''){
			recuperaAslDaComune(form.idComuneSoggetto.value);
			form.idAslStab.value = form.idAslStabRecuperata.value;
			form.idAslStab.onchange();
			form.idComuneStab.value=form.idComuneSoggetto.value;
	 		form.toponimoStab.value=form.toponimoSoggetto.value;
	 		form.viaStab.value=form.viaSoggetto.value;
	 		form.civicoStab.value=form.civicoSoggetto.value;
	 		form.capStab.value = form.capSoggetto.value;
	 		form.longitudineStab.value="";
	 		form.latitudineStab.value="";
		}
		
}


function ControllaPIVA(pi)
{
	var formtest=true;
	var msg = 'Attenzione!\n'.toUpperCase();
	
	piField=document.getElementById(pi);
	
	pi= piField.value;	
	
	if( pi == '' ){ 
		return true;
	} 
	if( pi.length != 11 ){
		msg+='Non corretta: la partita IVA dovrebbe essere lunga esattamente 11 caratteri.\n'.toUpperCase();
		formtest=false;
	}
validi = "0123456789";
for( i = 0; i < 11; i++ ){
    if( validi.indexOf( pi.charAt(i) ) == -1 ){
        msg+='Contiene un carattere non valido .\nI caratteri validi sono le cifre.\n'.toUpperCase();
        formtest=false;
        break;
    }
}
s = 0;
for( i = 0; i <= 9; i += 2 )
    s += pi.charCodeAt(i) - '0'.charCodeAt(0);
for( i = 1; i <= 9; i += 2 ){
    c = 2*( pi.charCodeAt(i) - '0'.charCodeAt(0) );
    if( c > 9 )  c = c - 9;
    s += c;
}
if( ( 10 - s%10 )%10 != pi.charCodeAt(10) - '0'.charCodeAt(0) ){
	msg+= 'Partita Iva non Valida secondo lo Standard.\n'.toUpperCase();
	formtest=false;
}


if(formtest==false){
	msg+='Vuoi continuare comunque ?'.toUpperCase();
	if(confirm(msg)==false)
		piField.value='';
}
	
}

</script>


<center>

<form id="addStabilimento" name="addStabilimento" action="GestioneAnagraficaAction.do?command=Insert&auto-populate=true" method="post">

<table class="details" cellpadding="10" cellspacing="10">
<col width="30%">

<tr style="display:<%=(User.getUserId()==5885) ? "table" : "none"%>"><td colspan="2"><input type="button" onClick="popolaCampi(this.form)" value="POPOLA"/></td></tr>

<tr><th colspan="2">Soggetto</th></tr>
<tr><td>Partita IVA</td> <td><input type="text" id="piva" name="piva" size="11" maxlength="11" onChange="ControllaPIVA('piva')"/>  </td></tr>
<tr><td>Nome</td> <td><input type="text" id="nome" name="nome"/>  <font color="red">*</font></td></tr>
<tr><td>Cognome</td> <td><input type="text" id="cognome" name="cognome"/>  <font color="red">*</font></td></tr>
<tr><td>Sesso</td> <td><input type="radio" id="sessoM" name="sesso" value="M" checked/> M <input type="radio" id="sessoF" name="sesso" value="F"/> F </td></tr>
<tr><td>Data nascita</td> <td> 	<input readonly type="text" id="data_nascita" name="data_nascita" size="10" /> <a href="#" onClick="cal19.select(document.forms[0].data_nascita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> </td></tr>
<tr><td>Nazionalità</td> <td><% NazioniList.setJsEvent("onChange=\"if(this.value==106) { gestisciProvenienzaEstera(false); } else {gestisciProvenienzaEstera(true); }\"");%>  <%= NazioniList.getHtmlSelect("rappresentanteNazione", 106) %></td></tr>
<tr><td>Comune nascita</td> <td><input type="text" id="nomeComuneNascita" name="nomeComuneNascita"/></td></tr>
<tr><td>Codice Fiscale</td> <td><input type="text" name="codice_fiscale" readonly="readonly" id="codice_fiscale" size="20" maxlength="20" />  <font color="red">*</font> 
<!-- <input type="checkbox" id="estera" name="estera" onClick="gestisciProvenienzaEstera(this)"/>Estero  -->
<input type="button" id="calcoloCF" value="CALCOLA CODICE FISCALE" onclick="javascript:CalcolaCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.forms[0].nomeComuneNascita,document.forms[0].data_nascita,'codice_fiscale')"/></td></tr>
<tr><td>Telefono</td> <td><input type="text" id="telefono" name="telefono"/></td></tr>
<tr><td>PEC</td> <td><input type="text" id="email" name="email" size="50"/></td></tr>
<tr><td>Documento identità</td> <td><input type="text" id="documento_identita" name="documento_identita"/></td></tr>
<tr><td>Note</td> <td><input type="text" id="note" name="note" size="100"/></td></tr>
 
<tr><th colspan="2">Residenza</th></tr> 

<tr style="display:none"><td>Nazionalità</td> <td><%=NazioniList.getHtmlSelect("idNazioneSoggetto", 106) %></td></tr>
<tr><td>Provincia</td> <td><% ProvinceList.setJsEvent("onChange=\"PopolaListaComuniRappresentanteResidenza(this.value)\"");%> <%= ProvinceList.getHtmlSelect("idProvinciaSoggetto", -1) %>  <font color="red">*</font> </td></tr>
<tr><td>Comune</td> <td> <table style="border:none"><tr><td><div id="dividComuneSoggetto"></div></td><td style="border:none"><font color="red"> *</font></td></tr></table> </td></tr>
<tr><td>Indirizzo</td> <td><%=ToponimiList.getHtmlSelect("toponimoSoggetto", 100)%> <input type="text" id="viaSoggetto" name="viaSoggetto"  placeholder="indirizzo"/> <input type="text" id="civicoSoggetto" name="civicoSoggetto" placeholder="CIVICO"/> <input type="text" readonly id="capSoggetto" name="capSoggetto" placeholder="CAP"/>  <font color="red">*</font> <input type="button" value="Calcola CAP" id="bottoneCapSoggetto" onclick="calcolaCap(document.getElementById('idComuneSoggetto').value, document.getElementById('toponimoSoggetto').value, document.getElementById('viaSoggetto').value, 'capSoggetto');" />  </td></tr>

<tr><th colspan="2">Luogo del controllo <br/><input type="button" id="checkOperativa" name="checkOperativa" onClick="copiaDaResidenzaAOperativa(this.form); return false" value="Copia da Residenza"/> </th></tr> 

<tr><td>ASL</td> <td><% AslList.setJsEvent("onChange=\"PopolaListaComuniOperativa(this.value)\""); %><%=AslList.getHtmlSelect("idAslStab", -1)%>  <font color="red">*</font></td></tr>
<tr><td>Comune</td> <td><div id="dividComuneStab"></div>  <font color="red">*</font></td></tr>
<tr><td>Indirizzo</td> <td><%=ToponimiList.getHtmlSelect("toponimoStab", 100)%> <input type="text" id="viaStab" name="viaStab" placeholder="indirizzo" /> <input type="text" id="civicoStab" name="civicoStab" placeholder="CIVICO"/> <input type="text" readonly id="capStab" name="capStab" placeholder="CAP" />  <font color="red">*</font> <input type="button" value="Calcola CAP" id="bottoneCapStab" onclick="calcolaCap(document.getElementById('idComuneStab').value, document.getElementById('toponimoStab').value, document.getElementById('viaStab').value, 'capStab');" />  </td></tr>
<tr><td>Coordinate</td> <td><input type="text" readonly id="latitudineStab" name="latitudineStab" placeholder="LAT" /> <input type="text" readonly id="longitudineStab" name="longitudineStab" placeholder="LON" /> <input id="coord1button" type="button" value="Calcola Coordinate" onclick="javascript:showCoordinate(getSelectedText('toponimoStab')+' '+document.getElementById('viaStab').value+', '+document.getElementById('civicoStab').value, getSelectedText('idComuneStab'), getSelectedText('idComuneStab'), document.getElementById('capStab').value, document.getElementById('latitudineStab'), document.getElementById('longitudineStab'));" /></td></tr>

<tr style="display:none"><th colspan="2">Impresa</th></tr>
 
<tr style="display:none"><td>Tipo Impresa</td> <td><input type="hidden" id="tipo_impresa" name="tipo_impresa" value="13"/> IMPRESA INDIVIDUALE</td></tr>
<tr style="display:none"><td>Ditta/Ragione sociale/Denominazione</td> <td><input type="text" id="ragione_sociale" name="ragione_sociale" size="100" /></td></tr>
<tr style="display:none"><td>PEC</td> <td><input type="text" id="pec" name="pec" size="50" /></td></tr>

<tr style="display:none"><th colspan="2">Sede Legale <br/><input type="checkbox" id="checkLegale" name="checkLegale" onClick="copiaDaResidenzaALegale(this.form); return false;"/> Copia da Residenza </th></tr> 

<tr style="display:none"><td>Nazionalità</td> <td><%=NazioniList.getHtmlSelect("idNazione", 106) %></td></tr>
<tr style="display:none"><td>Provincia</td> <td><% ProvinceList.setJsEvent("onChange=\"PopolaListaComuniLegale(this.value)\"");%> <%= ProvinceList.getHtmlSelect("idProvincia", 63) %></td></tr>
<tr style="display:none"><td>Comune</td> <td><div id="dividComune"></div></td></tr>
<tr style="display:none"><td>Indirizzo</td> <td><%=ToponimiList.getHtmlSelect("toponimo", 100)%> <input type="text" id="via" name="via"  placeholder="indirizzo" /> <input type="text" id="civico" name="civico" placeholder="CIVICO"/> <input type="text" readonly id="cap" name="cap" placeholder="CAP" />  <input type="button" value="Calcola CAP" id="bottoneCap" onclick="calcolaCap(document.getElementById('idComune').value, document.getElementById('toponimo').value, document.getElementById('via').value, 'cap');" /> </td></tr>
<tr style="display:none"><td>Coordinate</td> <td><input type="text" readonly id="latitudine" name="latitudine" placeholder="LAT" /> <input type="text" readonly id="longitudine" name="longitudine" placeholder="LON" /> <input id="coord1button" type="button" value="Calcola Coordinate" onclick="javascript:showCoordinate(getSelectedText('toponimo')+' '+document.getElementById('via').value+', '+document.getElementById('civico').value, getSelectedText('idComune'), getSelectedText('idComune'), document.getElementById('cap').value, document.getElementById('latitudine'), document.getElementById('longitudine'));" /></td></tr>

<tr><th colspan="2">Attivita</th></tr> 
<tr style="display:none"><td>Tipo Carattere</td> <td><input type="hidden" id="carattere" name="carattere" value="1"/> PERMANENTE</td></tr>
<tr> <td>Linea</td> <td>
<jsp:include page="../gestioneml/navigaml.jsp">
<jsp:param name="idFlussoOrig" value="5" />
</jsp:include>
<input type="hidden" id="idLineaProduttiva" name="idLineaProduttiva" value="40465"/> <input type="hidden" readonly size="70" id="nomeLineaProduttiva" name="nomeLineaProduttiva" value="OPERATORI PRIVATI"/>
</td></tr>

<tr><td>Data inizio attività</td> <td> 	<input readonly type="text" id="dataInizioLinea" name="dataInizioLinea" size="10"  /> <a href="#" onClick="cal19.select(document.forms[0].dataInizioLinea,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> </td></tr>
<tr><td>Data fine attività</td> <td> 	<input readonly type="text" id="dataFineLinea" name="dataFineLinea" size="10" /> <a href="#" onClick="cal19.select(document.forms[0].dataFineLinea,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> </td></tr>
<tr style="display:none"><td>CUN</td> <td><input type="text" id="cun" name="cun"/></td></tr>


<tr><td colspan="2"><input type="button" onClick="checkForm(this.form)" value="CONFERMA"/></td></tr>

<input type="hidden" id="idAslStabRecuperata" name="idAslStabRecuperata" value=""/>
</table>

<input type="hidden" id="codfisc" name="codfisc"/>


</center>


<script>
document.getElementById("idAslStab").onchange();
document.getElementById("idProvincia").onchange();
document.getElementById("idProvinciaSoggetto").onchange();
</script>