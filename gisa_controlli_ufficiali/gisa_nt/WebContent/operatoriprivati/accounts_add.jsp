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

<%
String siglaAsl = "";
switch (User.getSiteId()) {
case 201: siglaAsl = "AV"; break;
case 202: siglaAsl = "BN"; break;
case 203: siglaAsl = "CE"; break;
case 204: siglaAsl = "NA1C"; break;
case 205: siglaAsl = "NA2N"; break;
case 206: siglaAsl = "NA3S"; break;
case 207: siglaAsl = "SA"; break;
}

%>

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
	
	var select = "<select id=\"idComuneStab\" name=\"idComuneStab\" onChange=\"resetCampiOperativa(); visualizzaStradario(this.value, 'stradarioNapoliLuogoC')\">";
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
	
	var select = "<select onclick='selezionaIndirizzo('rappresentanteNazione', \"callBackSoggFisico\",document.getElementById(\"addressLegaleCountryinput\").value,this.value)' id=\"idComuneSoggetto\" name=\"idComuneSoggetto\" onChange=\"resetCampiRappresentanteResidenza(); visualizzaStradario(this.value, 'stradarioNapoliResidenza')\">";
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
	if (value == null){
		 alert("errore calcolo cap: consultare lo stradario ed inserire l'indirizzo corretto");
	}
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
	
	msg = 'Attenzione. Compilare i seguenti campi:';
	
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
	
	if (latstab==''){
		msg+="\nLUOGO DEL CONTROLLO - Latitudine";
		esito = false;
	}
	
	if (lonstab==''){
		msg+="\nLUOGO DEL CONTROLLO - Longitudine";
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
	
	var idProvinceCampania = ["AV","BN","CE", "NA","SA"];

	if (!idProvinceCampania.includes(form.provinciaSoggetto.value)){ 
		alert('Comune non in Campania.');
		return false;
	}
	
		if (form.idComuneSoggetto.value!=''){
			//recuperaAslDaComune(form.idComuneSoggetto.value);
			//form.idAslStab.value = form.idAslStabRecuperata.value;
			//form.idAslStab.onchange();
			form.idComuneStab.value=form.idComuneSoggetto.value;
	 		form.toponimoStab.value=form.toponimoSoggetto.value;
	 		form.toponimoStab.value = form.toponimoSoggetto.value ;
	 		form.toponimoStabId.value = form.toponimoSoggettoId.value  ;
	 		form.viaStab.value=form.viaSoggetto.value;
	 		form.civicoStab.value=form.civicoSoggetto.value;
	 		form.capStab.value = form.capSoggetto.value;
	 		form.idComuneStab.value = form.idComuneSoggetto.value ;
	 		form.comuneStab.value = form.comuneSoggetto.value ;
	 		form.longitudineStab.value="";
	 		form.latitudineStab.value="";
	 		document.getElementById('capStab').readOnly=true;
	 	    document.getElementById('civicoStab').readOnly=true;
	 	    document.getElementById('viaStab').readOnly=true;
	 		document.getElementById('toponimoStab').disabled=true;
	 		document.getElementById('bottoneCapStab').disabled=true;
		}
		
}


function visualizzaStradario(codComune, idButton){
	if (codComune != "5279"){
		document.getElementById(idButton).type = "hidden";
	} else {
		document.getElementById(idButton).type = "button";
	}
}

</script>


<center>
<table style="border: 3px solid black" cellpadding="10px" cellspacing="10px">
<tr><td style="background-color:#ffffbf" text-align="justify">
<font size="3px" color="red"><img src="images/post-it.png" width="50px"/> <b>GESTIONE CAVALIERE PRIVATI</b></font><br/>
<font size="3px">Il cavaliere "privati" e' adibito all'inserimento di quelle figure che esulano da tutte le possibilita' anagrafiche del sistema GISA, figure di comuni cittadini che non posseggono alcuna attivita' commerciale, ne' tantomeno una partita iva, ma che rientrano trasversalmente all'interno del PRI come soggetti sottoposti ad attivita' di controllo.<br/>
Ad esempio verranno inseriti nei privati le vittime di una morsicatura, persone  coinvolte in una intossicazione alimentare o chiunque non sia inquadrato in alcuna macroarea della master list al quale l'Autorità Competente attribuisca un Controllo Ufficiale.<br/>
L'ambito di inserimento potra' contemplare diversi casi, pertanto i soli campi obbligatori da compilare, necessari all'inserimento, saranno i dati anagrafici necessari a rendere valide le schede stesse.<br/><br/>
I cu per le attività AO9_B(EFFETTUAZIONE DI N ISPEZIONI PER OSSERVAZIONI SANITARIE SU ANIMALI DI PROPRIETA PRESSO IL DOMICILIO), AO9_C( EFFETTUAZIONE DI N ISPEZIONI PER OSSERVAZIONI SANITARIE SU ANIMALI DI PROPRIETA O RANDAGI IDENTIFICATI RICOVERATI PRESSO CANILI/STRUTTURE SANITARI) e B52(Valutazione comportamentale dei cani potenzialmente aggressivi) vanno inseriti in Macroare IUV -> Proprietari di Animali
</font>
</td></tr>
</table>

<form id="addStabilimento" name="addStabilimento" action="Operatoriprivati.do?command=Insert&auto-populate=true" method="post">

<table class="details" cellpadding="10" cellspacing="10">
<col width="30%">

<tr style="display:<%=(User.getUserId()==5885) ? "table" : "none"%>"><td colspan="2"><input type="button" onClick="popolaCampi(this.form)" value="POPOLA"/></td></tr>

<tr><th colspan="2">Soggetto</th></tr>
<tr><td>Nome</td> <td><input type="text" id="nome" name="nome"/>  <font color="red">*</font></td></tr>
<tr><td>Cognome</td> <td><input type="text" id="cognome" name="cognome"/>  <font color="red">*</font></td></tr>
<tr><td>Sesso</td> <td><input type="radio" id="sessoM" name="sesso" value="M" checked/> M <input type="radio" id="sessoF" name="sesso" value="F"/> F </td></tr>
<tr><td>Data nascita</td> <td> 	<input readonly type="text" id="data_nascita" name="data_nascita" size="10" /> <a href="#" onClick="cal19.select(document.forms[0].data_nascita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> </td></tr>
<tr><td>Nazione di nascita</td> <td><% NazioniList.setJsEvent("onChange=\"if(this.value==106) { gestisciProvenienzaEstera(false); } else {gestisciProvenienzaEstera(true); }\"");%>  <%= NazioniList.getHtmlSelect("rappresentanteNazione", 106) %></td></tr>
<tr><td>Comune nascita</td> <td><input type="text" id="nomeComuneNascita" name="nomeComuneNascita"/></td></tr>
<tr><td>Codice Fiscale</td> <td><input type="text" name="codice_fiscale" readonly="readonly" id="codice_fiscale" size="20" maxlength="20" />  <font color="red">*</font>
<!-- <input type="checkbox" id="estera" name="estera" onClick="gestisciProvenienzaEstera(this)"/>Estero  -->
<input type="button" id="calcoloCF" value="CALCOLA CODICE FISCALE" onclick="javascript:CalcolaCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.forms[0].nomeComuneNascita,document.forms[0].data_nascita,'codice_fiscale')"/>  <font size="1px">Attenzione! In caso di nazionalità diversa da ITALIA il codice fiscale deve essere inserito manualmente.</font> </td></tr>
<tr><td>Telefono</td> <td><input type="text" id="telefono" name="telefono"/></td></tr>
<tr><td>PEC</td> <td><input type="text" id="email" name="email" size="50"/></td></tr>
<tr><td>Documento identità</td> <td><input type="text" id="documento_identita" name="documento_identita"/></td></tr>
<tr><td>Note</td> <td><input type="text" id="note" name="note" size="100"/></td></tr>
 
<tr><th colspan="2">Residenza</th></tr> 

<tr style="display:none"><td>Nazionalità</td> <td><%=NazioniList.getHtmlSelect("idNazioneSoggetto", 106) %></td></tr>
<tr>
	<td colspan="2">
		<font color="red">ATTENZIONE! Posizionarsi sul campo comune per inserire tutto l'indirizzo</font>
	</td>
</tr>
<tr>
	<td>
		Comune
	</td> 
	<td> 
		<input type="hidden" readOnly="true" id="idComuneSoggetto" name="idComuneSoggetto"> 
		<input onclick='selezionaIndirizzo("idNazioneSoggetto","callBackSoggFisico")' id="comuneSoggetto" name="comuneSoggetto">
		<font color="red"> *</font>
	</td>
</tr>
<tr>
	<td>
		Provincia
	</td> 
	<td>
		<input type="text" readOnly="true" id="provinciaSoggetto" name="idProvinciaSoggetto">  
		<font color="red">*</font> 
	</td>
</tr>
<tr>
	<td>
		Indirizzo
	</td> 
	<td><input type="hidden" name="toponimoSoggettoId" id="toponimoSoggettoId" />
	<%=ToponimiList.getHtmlSelect("toponimoSoggetto", 100)%> 
	<input type="text" readonly id="viaSoggetto" name="viaSoggetto"  placeholder="indirizzo"/> 
	<input type="text" readonly id="civicoSoggetto" name="civicoSoggetto" placeholder="CIVICO"/> 
	<input type="text" readonly id="capSoggetto" name="capSoggetto" placeholder="CAP"/>  <font color="red">*</font> 
	<input type="hidden" value="Calcola CAP" id="bottoneCapSoggetto" onclick="calcolaCap(document.getElementById('idComuneSoggetto').value, document.getElementById('toponimoSoggetto').value, document.getElementById('viaSoggetto').value, 'capSoggetto');" />  
	<input type="hidden" id="stradarioNapoliResidenza" value="stradario" onClick="window.open('man/stradarioNapoli.pdf','window','width=1000,height=600')">
</td></tr>

<tr><th colspan="2">Luogo del controllo <br/>
<!-- <input type="button" id="checkOperativa" name="checkOperativa" onClick="copiaDaResidenzaAOperativa(this.form); return false" value="Copia da Residenza"/>  -->
</th></tr> 

<!-- tr><td>ASL</td> <td><% AslList.setJsEvent("onChange=\"PopolaListaComuniOperativa(this.value)\""); %><%=AslList.getHtmlSelect("idAslStab", -1)%>  <font color="red">*</font></td></tr -->

<tr>
	<td colspan="2">
		<font color="red">ATTENZIONE! Posizionarsi sul campo comune per inserire tutto l'indirizzo</font>
	</td>
</tr>
<tr>
	<td>
		Comune
	</td> 
	<td> 
		<input type="hidden" readOnly="true" id="idComuneStab" name="idComuneStab"> 
		<input onclick='selezionaIndirizzo("idNazioneSoggetto","callBackStabPrivati","",true, "<%=siglaAsl %>")' id="comuneStab" name="comuneStab">
		<font color="red"> *</font>
	</td>
</tr>

<tr><td>Indirizzo</td> 
<td>
	<input type="hidden" name="toponimoStabId" id="toponimoStabId" />
	<%=ToponimiList.getHtmlSelect("toponimoStab", 100)%> 
	<input type="text" readonly id="viaStab" name="viaStab" placeholder="indirizzo" /> 
	<input type="text" readonly id="civicoStab" name="civicoStab" placeholder="CIVICO"/> 
	<input type="text" readonly id="capStab" name="capStab" placeholder="CAP" />  <font color="red">*</font> 
	<input type="hidden" value="Calcola CAP" id="bottoneCapStab" onclick="calcolaCap(document.getElementById('idComuneStab').value, document.getElementById('toponimoStab').value, document.getElementById('viaStab').value, 'capStab');" />  
	<input type="hidden" id="stradarioNapoliLuogoC" value="stradario" onClick="window.open('man/stradarioNapoli.pdf','window','width=1000,height=600')">
</td></tr>
<tr><td>Coordinate</td> <td><input type="text" readonly id="latitudineStab" name="latitudineStab" placeholder="LAT" /> <input type="text" readonly id="longitudineStab" name="longitudineStab" placeholder="LON" /> <input id="coord1button" type="button" value="Calcola Coordinate" onclick="javascript:showCoordinate(getSelectedText('toponimoStab')+' '+document.getElementById('viaStab').value+', '+document.getElementById('civicoStab').value, document.getElementById('comuneStab').value, document.getElementById('comuneStab').value, document.getElementById('capStab').value, document.getElementById('latitudineStab'), document.getElementById('longitudineStab'));" /></td></tr>

<tr style="display:none"><th colspan="2">Impresa</th></tr>
 
<tr style="display:none"><td>Tipo Impresa</td> <td><input type="hidden" id="tipo_impresa" name="tipo_impresa" value="13"/> IMPRESA INDIVIDUALE</td></tr>
<tr style="display:none"><td>Ditta/Ragione sociale/Denominazione</td> <td><input type="text" id="ragione_sociale" name="ragione_sociale" size="100" /></td></tr>
<tr style="display:none"><td>PEC</td> <td><input type="text" id="pec" name="pec" size="50" /></td></tr>

<tr style="display:none"><th colspan="2">Sede Legale <br/><input type="checkbox" id="checkLegale" name="checkLegale" onClick="copiaDaResidenzaALegale(this.form); return false;"/> Copia da Residenza </th></tr> 

<tr style="display:none"><td>Nazionalità</td> <td><%=NazioniList.getHtmlSelect("idNazione", 106) %></td></tr>
<tr style="display:none"><td>Provincia</td> <td><% ProvinceList.setJsEvent("onChange=\"PopolaListaComuniLegale(this.value)\"");%> <%= ProvinceList.getHtmlSelect("idProvincia", 63) %></td></tr>
<tr style="display:none"><td>Comune</td> <td><div id="dividComune"></div></td></tr>
<tr style="display:none"><td>Indirizzo</td> <td><%=ToponimiList.getHtmlSelect("toponimo", 100)%> 
	<input type="text" id="via" name="via"  placeholder="indirizzo" /> 
	<input type="text" id="civico" name="civico" placeholder="CIVICO"/> 
	<input type="text" readonly id="cap" name="cap" placeholder="CAP" />  
	<input type="hidden" value="Calcola CAP" id="bottoneCap" onclick="calcolaCap(document.getElementById('idComune').value, document.getElementById('toponimo').value, document.getElementById('via').value, 'cap');" /> </td></tr>
<tr style="display:none"><td>Coordinate</td> <td><input type="text" readonly id="latitudine" name="latitudine" placeholder="LAT" /> <input type="text" readonly id="longitudine" name="longitudine" placeholder="LON" /> <input id="coord1button" type="button" value="Calcola Coordinate" onclick="javascript:showCoordinate(getSelectedText('toponimo')+' '+document.getElementById('via').value+', '+document.getElementById('civico').value, getSelectedText('idComune'), getSelectedText('idComune'), document.getElementById('cap').value, document.getElementById('latitudine'), document.getElementById('longitudine'));" /></td></tr>

<tr style="display:none"><th colspan="2">Attivita</th></tr> 
<tr style="display:none"><td>Tipo Carattere</td> <td><input type="hidden" id="carattere" name="carattere" value="1"/> PERMANENTE</td></tr>
<tr style="display:none"> <td>Linea</td> <td>
<input type="hidden" id="idLineaProduttiva" name="idLineaProduttiva" value="40465"/> <input type="text" readonly size="70" id="nomeLineaProduttiva" name="nomeLineaProduttiva" value="OPERATORI PRIVATI"/>
</td></tr>

<tr style="display:none"><td>Data inizio attività</td> <td> 	<input readonly type="text" id="dataInizioLinea" name="dataInizioLinea" size="10"  /> <a href="#" onClick="cal19.select(document.forms[0].dataInizioLinea,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> </td></tr>
<tr style="display:none"><td>Data fine attività</td> <td> 	<input readonly type="text" id="dataFineLinea" name="dataFineLinea" size="10" /> <a href="#" onClick="cal19.select(document.forms[0].dataFineLinea,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> </td></tr>
<tr style="display:none"><td>CUN</td> <td><input type="text" id="cun" name="cun"/></td></tr>


<tr><td colspan="2"><input type="button" onClick="checkForm(this.form)" value="CONFERMA"/></td></tr>

<input type="hidden" id="idAslStabRecuperata" name="idAslStabRecuperata" value=""/>
</table>

<input type="hidden" id="codfisc" name="codfisc"/>


</center>


<script>
document.getElementById("toponimoSoggetto").disabled=true;
document.getElementById("toponimoStab").disabled=true;
document.getElementById("idAslStab").onchange();
document.getElementById("idProvincia").onchange();
document.getElementById("idProvinciaSoggetto").onchange();
</script>