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
  

<style>
select {
    -webkit-appearance: button;
    -moz-appearance: button;
    -webkit-user-select: none;
    -moz-user-select: none;
    -webkit-padding-end: 20px;
    -moz-padding-end: 20px;
    -webkit-padding-start: 2px;
    -moz-padding-start: 2px;
    background-color: #FFFFFF; /* fallback color if gradients are not supported */
    background-image: url(../images/select-arrow.png), -webkit-linear-gradient(top, #E5E5E5, #F4F4F4); /* For Chrome and Safari */
    background-image: url(../images/select-arrow.png), -moz-linear-gradient(top, #E5E5E5, #F4F4F4); /* For old Fx (3.6 to 15) */
    background-image: url(../images/select-arrow.png), -ms-linear-gradient(top, #E5E5E5, #F4F4F4); /* For pre-releases of IE 10*/
    background-image: url(../images/select-arrow.png), -o-linear-gradient(top, #E5E5E5, #F4F4F4); /* For old Opera (11.1 to 12.0) */ 
    background-image: url(../images/select-arrow.png), linear-gradient(to bottom, #E5E5E5, #F4F4F4); /* Standard syntax; must be last */
    background-position: center right;
    background-repeat: no-repeat;
    border: 1px solid #AAA;
    border-radius: 2px;
    box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);
    color: #555;
    font-size: inherit;
    margin: 0;
    overflow: hidden;
    padding-top: 2px;
    padding-bottom: 2px;
    text-overflow: ellipsis;
    white-space: nowrap;
}

input[type="text"] {
   
    -webkit-padding-end: 20px;
    -moz-padding-end: 20px;
    -webkit-padding-start: 2px;
    -moz-padding-start: 2px;
    background-color: #FFFFFF; /* fallback color if gradients are not supported */
    background-image: url(../images/select-arrow.png), -webkit-linear-gradient(top, #E5E5E5, #F4F4F4); /* For Chrome and Safari */
    background-image: url(../images/select-arrow.png), -moz-linear-gradient(top, #E5E5E5, #F4F4F4); /* For old Fx (3.6 to 15) */
    background-image: url(../images/select-arrow.png), -ms-linear-gradient(top, #E5E5E5, #F4F4F4); /* For pre-releases of IE 10*/
    background-image: url(../images/select-arrow.png), -o-linear-gradient(top, #E5E5E5, #F4F4F4); /* For old Opera (11.1 to 12.0) */ 
    background-image: url(../images/select-arrow.png), linear-gradient(to bottom, #E5E5E5, #F4F4F4); /* Standard syntax; must be last */
    background-position: center right;
    background-repeat: no-repeat;
    border: 1px solid #AAA;
    border-radius: 2px;
    box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);
    color: #555;
    font-size: inherit;
    margin: 0;
    overflow: hidden;
    padding-top: 2px;
    padding-bottom: 2px;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.stabilimento table {  
    color: #333;
    font-family: Helvetica, Arial, sans-serif;
    width: 100%; 
    border-collapse: 
    collapse; border-spacing: 0; 
}

table.stabilimento td, th {  
    border: 1px solid transparent; /* No more visible border */
    height: 30px; 
    transition: all 0.3s;  /* Simple transition for hover effect */
}

table.stabilimento th {  
    background: #DFDFDF;  /* Darken header a bit */
    font-weight: bold;
}

table.stabilimento td {  
    background: #FAFAFA;
    text-align: center;
}

/* Cells in even rows (2,4,6...) are one color */        
table.stabilimento tr:nth-child(even) td { background: #F1F1F1; }   

/* Cells in odd rows (1,3,5...) are another (excludes header cells)  */        
table.stabilimento tr:nth-child(odd) td { background: #FEFEFE; }  

table.stabilimento tr td:hover { background: #666; color: #FFF; }  
/* Hover cell effect! */

</style>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script f src="dwr/interface/SuapDwr.js"> </script>

<script>

function PopolaListaComuniOperativa(idAsl)
{
	PopolaCombo.getValoriComboComuni1Asl(idAsl,{callback:PopolaListaComuniOperativaCallback,async:false}) ;
	
}

function PopolaListaComuniOperativaCallback(value)
{
	var valueCodici = value[0];
	var valueNomi = value[1];
	
	var select = "<select id=\"idComuneStab\" name=\"idComuneStab\" onChange=\"resetCampiOperativa(); visualizzaStradario(this.value, 'stradarioNapoliSede')\";>";
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
	
	var select = "<select id=\"idComune\" name=\"idComune\" onChange=\"resetCampiLegale(); visualizzaStradario(this.value,'stradarioNapoliLegale')\">";
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
	
	var select = "<select id=\"idComuneSoggetto\" name=\"idComuneSoggetto\" onChange=\"resetCampiRappresentanteResidenza(); visualizzaStradario(this.value,'stradarioNapoliResidenza')\">";
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
	SuapDwr.getCap(idComune, topon, indir,campo,{callback:calcolaCapCallBack,async:false});
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
	var pec = form.pec.value;
	var provlegale = form.idProvincia.value;
	var vialegale = form.via.value;
	var caplegale = form.cap.value;
	var latlegale = form.latitudine.value;
	var lonlegale = form.longitudine.value;
	var cf = form.codice_fiscale.value;
	var viastab = form.viaStab.value;
	var capstab = form.capStab.value;
	var latstab = form.latitudineStab.value;
	var lonstab = form.longitudineStab.value;
	var idLinea = form.idLineaProduttiva.value;
	var dataInizio = form.dataInizioLinea.value;
	
	var esito = true;
	msg = 'Attenzione. Compilare i seguenti campi:';
	
	if (ragione==''){
		msg+="\nIMPRESA - Ragione sociale";
		esito = false;
	}
	if (iva.length!=11){
		msg+="\nIMPRESA - Partita IVA di 11 caratteri";
		esito = false;
	}
	if (pec==''){
		msg+="\nIMPRESA - PEC";
		esito = false;
	}
	if (provlegale=='-1'){
		msg+="\nSEDE LEGALE - Provincia";
		esito = false;
	}
	if (vialegale==''){
		msg+="\nSEDE LEGALE - Indirizzo";
		esito = false;
	}
	if (caplegale==''){
		msg+="\nSEDE LEGALE - CAP";
		esito = false;
	}
	if (latlegale==''){
		msg+="\nSEDE LEGALE - Latitudine";
		esito = false;
	}
	if (lonlegale==''){
		msg+="\nSEDE LEGALE - Longitudine";
		esito = false;
	}
	
	if (viastab==''){
		msg+="\nSEDE OPERATIVA - Indirizzo";
		esito = false;
	}
	if (capstab==''){
		msg+="\nSEDE OPERATIVA - CAP";
		esito = false;
	}
	if (latstab==''){
		msg+="\nSEDE OPERATIVA - Latitudine";
		esito = false;
	}
	if (lonstab==''){
		msg+="\nSEDE OPERATIVA - Longitudine";
		esito = false;
	}
	if (cf==''){
		msg+="\nLEGALE RAPPRESENTANTE - Codice fiscale";
		esito = false;
	}

	if (idLinea=='?' || idLinea==''){
		msg+="\nLINEA PRODUTTIVA - Linea";
		esito = false;
	}
	if (dataInizio==''){
		msg+="\nLINEA PRODUTTIVA - Data inizio";
		esito = false;
	}

	if (esito==false){
		alert(msg);
		return false;
	}
	
	if (confirm('PROSEGUIRE?'))
		form.submit();
}


function popolaCampi(form){
	
	 var nomi = ["Rito", "Bartolo", "Stofano", "Alessandro", "Uolter", "Antonio", "Carmelo", "Viviano", "Valentino", "Tiziano", "Alfonso", "Domenico"];
	 var cognomi = ["Mele", "Sansone", "Squitiero", "Guida", "Amante", "Riviezzo-Liguori", "Paolillo", "Perropane", "Avallone", "De Simone", "Rossi", "Bianchi", "Carfora"];
	 var comuni = ["NAPOLI", "BACOLI", "SALERNO", "CAPRI", "TORRE ANNUNZIATA", "ROCCAPIEMONTE", "PORTICI", "CASERTA", "BENEVENTO", "FIRENZE", "SAPRI"];
	 
	 var ragione1 = ["Impresa", "Cooperativa", "Stabilimento", "Supermercato", "Angolo", "Caffe", "Bar", "Toasteria", "Rinfresco", "Dopolavoro", "Signor", "Attivita", "Polisportiva", "Ristoro", "Angolo", "Pizzeria", "Addu", "COOP", "Anagrafica", "Stanza", "Ufficio", "Palazzo", "Isola", "Distributore"];
	 var ragione2 = ["Detail", "Ciao Domenico", "Rita Ceccis", "Walter Table", "Digemon Pokemon",  "R per", "Martina", "Ciampa", "US", "Pomigliano Food",  "Marenna", "Sale e pepe", "Gigione", "Giuseppone", "Help Desk", "Uolter", "Riappallottolamelo",  "Cafesinho", "Hanny", "Starlight", "Peperoncino", "O' Zuzzus", "OVS", "IKEA", "Peperoncino", "Margherita", "Broccoli e salsiccia", "Ortolana", "Fiocco", "Brioche", "Wurstel e Patatine"];
	 var ragione3 = ["Altrove", "di Paperopoli", "in mezzo alle scale", "fuori la porta",  "al cancello di Arnone", "Dispersa", "Inc.", "SPA", "degli interni", "abusiva", "Del Centro",  "nel sacco", "D l criatur", "a mare", "scondita", "Saporita", "Privata", "Con Sorpresa", "di Napoli", "Quarto Piano", "D Ru Cuan", "Birichino", "Coffee", "Col parmigiano", "Senza peperoni", "Senza broccoli", "Di colore rosso", "Di colore bianco", "Arcobaleno", "Sette stelle", "Maionese a parte", "Piccante"];
	 	 
	 var via = ["roma", "degli aranci", "nolana", "della vittoria", "dei giardini", "stretto", "porzio", "duomo", "san gennaro", "san pasquale a chiaia", "chiusa", "Meridionale"];
	 
	 var toponimi = ["4","22","58","62","76","81","100","104","106","128","136","140","143","144","153","157","194","198","204","205","215"];
	 
	 var idComuniNapoli =["5231","5232","5233","5234","5235","5236","5237","5238","5239","5240","5241","5242","5243","5244","5245","5246","5247","5248","5249","5250","5251","5252","5253","5254","5255","5256","5257","5258","5259","5260","5261","5262","5263","5264","5265","5266","5267","5268","5269","5270","5271","5272","5273","5274","5275","5276","5277","5278","5279","5280","5281","5282","5283","5284","5285","5286","5287","5288","5289","5290","5291","5292","5293","5294","5295","5296","5297","5298","5299","5300","5301","5302","5303","5304","5305","5306","5307","5308","5309","5310","5311","5312","5313","5314","5315","5316","5317","5318","5319","5320","5321","5322"];
	 var idComuniAvellino = ["5323","5324","5325","5326","5327","5328","5329","5330","5331","5332","5333","5334","5335","5336","5337","5338","5339","5340","5341","5342","5343","5344","5345","5346","5347","5348","5349","5350","5351","5352"];	 
	 var randomiva = "";
	 var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	 for (var i = 0; i < 11; i++)
		  randomiva += possible.charAt(Math.floor(Math.random() * possible.length));
	 
	 /* IMPRESA */
	 form.ragione_sociale.value=ragione1[Math.floor((Math.random() * ragione1.length-1) + 1)]+' '+ragione2[Math.floor((Math.random() * ragione2.length-1) + 1)]+' '+ragione3[Math.floor((Math.random() * ragione3.length-1) + 1)] + ' ' + Math.floor((Math.random() * 9998) + 1);
	 form.piva.value=randomiva;
	 form.codfisc.value=randomiva;
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
	 form.nome.value=nomi[Math.floor((Math.random() * nomi.length-1) + 1)];
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
	 form.idComuneStab.value=idComuniAvellino[Math.floor((Math.random() * idComuniAvellino.length-1) + 1)];
	 form.toponimoStab.value=toponimi[Math.floor((Math.random() * toponimi.length-1) + 1)];
	 form.viaStab.value=via[Math.floor((Math.random() * via.length-1) + 1)];
	 form.civicoStab.value=Math.floor((Math.random() * 100) + 1);
	 document.getElementById("bottoneCapStab").click();
	 form.latitudineStab.value='40.9452161';
	 form.longitudineStab.value='14.3695827';

	 /* LINEA PRODUTTIVA */
	 form.dataInizioLinea.value= Math.floor((Math.random() * 10) + 10)+"/0"+Math.floor((Math.random() * 8) + 1)+"/198"+Math.floor((Math.random() * 8) + 1);
	 form.dataFineLinea.value= Math.floor((Math.random() * 10) + 10)+"/0"+Math.floor((Math.random() * 8) + 1)+"/199"+Math.floor((Math.random() * 8) + 1);
	 form.cun.value='CUN'+randomiva;
	 
	 form.idLineaProduttiva.value = "40465";
	 form.nomeLineaProduttiva.value ="Gestione Anagrafica - Gestione Anagrafica - Gestione Anagrafica";


	 	
	
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

function nascondiCap(codnaz, idcap, idbottone)
{
			if( codnaz != "106" ) 
			{  
				document.getElementById(idbottone).type = "hidden";
				document.getElementById(idcap).type = "hidden";
				} else {
					document.getElementById(idbottone).type = "button";
					document.getElementById(idcap).type = "text";
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

<form id="addStabilimento" name="addStabilimento" action="GestioneAnagraficaAction.do?command=Insert&auto-populate=true" method="post">

<table class="stabilimento" cellpadding="10" cellspacing="10">
<col width="30%">

<tr><td colspan="2"><input type="button" onClick="popolaCampi(this.form)" value="POPOLA"/></td></tr>


<tr><th colspan="2">Impresa</th></tr>
 
<tr><td>Tipo Impresa</td> <td><%=TipoImpresaList.getHtmlSelect("tipo_impresa", -1) %></td></tr>
<tr><td>Ditta/Ragione sociale/Denominazione</td> <td><input type="text" id="ragione_sociale" name="ragione_sociale" size="100" /></td></tr>
<tr><td>Partita IVA</td> <td><input type="text" id="piva" name="piva" size="11" maxlength="11" /></td></tr>
<tr><td>Codice Fiscale</td> <td><input type="text" id="codfisc" name="codfisc"/></td></tr>
<tr><td>PEC</td> <td><input type="text" id="pec" name="pec" size="50" /></td></tr>
<tr><td>Note</td> <td><input type="text" id="note" name="note" size="100"/></td></tr>

<tr><th colspan="2">Sede Legale</th></tr> 

<tr><td>Nazionalità</td> <td> 
<% NazioniList.setJsEvent("onChange=\"nascondiCap(this.value,'cap','bottoneCap')\""); %> 
<%=NazioniList.getHtmlSelect("idNazione", 106) %> 
</td></tr>

<tr><td>Provincia</td> <td><% ProvinceList.setJsEvent("onChange=\"PopolaListaComuniLegale(this.value)\"");%> <%= ProvinceList.getHtmlSelect("idProvincia", 63) %></td></tr>
<tr><td>Comune</td> <td><div id="dividComune"></div></td></tr>
<tr><td>Indirizzo</td> 
	<td><%=ToponimiList.getHtmlSelect("toponimo", 100)%> 
		<input type="text" id="via" name="via"  placeholder="indirizzo" /> 
		<input type="text" id="civico" name="civico" placeholder="CIVICO"/> 
		<input type="text" readonly id="cap" name="cap" placeholder="CAP"/>
		<input type="button" value="Calcola CAP" id="bottoneCap"  
		onclick="calcolaCap(document.getElementById('idComune').value, document.getElementById('toponimo').value, document.getElementById('via').value, 'cap');" />
		<input type="hidden" id="stradarioNapoliLegale" value="stradario" onClick="window.open('man/stradarioNapoli.pdf','window','width=1000,height=600')">
	</td>
</tr>
<tr><td>Coordinate</td> <td><input type="text" readonly id="latitudine" name="latitudine" placeholder="LAT" /> <input type="text" readonly id="longitudine" name="longitudine" placeholder="LON" /> <input id="coord1button" type="button" value="Calcola Coordinate" onclick="javascript:showCoordinate(getSelectedText('toponimo')+' '+document.getElementById('via').value+', '+document.getElementById('civico').value, getSelectedText('idComune'), getSelectedText('idComune'), document.getElementById('cap').value, document.getElementById('latitudine'), document.getElementById('longitudine'));" /></td></tr>


<tr><th colspan="2">Legale rappresentante</th></tr>

<tr><td>Nome</td> <td><input type="text" id="nome" name="nome"/></td></tr>
<tr><td>Cognome</td> <td><input type="text" id="cognome" name="cognome"/></td></tr>
<tr><td>Sesso</td> <td><input type="radio" id="sessoM" name="sesso" value="M" checked/> M <input type="radio" id="sessoF" name="sesso" value="F"/> F </td></tr>
<tr><td>Data nascita</td> <td> 	<input readonly type="text" id="data_nascita" name="data_nascita" size="10" /> <a href="#" onClick="cal19.select(document.forms[0].data_nascita,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> </td></tr>
<tr><td>Nazionalità</td> <td><%=NazioniList.getHtmlSelect("rappresentanteNazione", 106) %></td></tr>
<tr><td>Comune nascita</td> <td><input type="text" id="nomeComuneNascita" name="nomeComuneNascita"/></td></tr>
<tr><td>Codice Fiscale</td> <td><input type="text" name="codice_fiscale" readonly="readonly" id="codice_fiscale" size="50" /> <input type="button" id="calcoloCF" value="CALCOLA CODICE FISCALE" onclick="javascript:CalcolaCF(document.forms[0].sesso,document.forms[0].nome,document.forms[0].cognome,document.forms[0].nomeComuneNascita,document.forms[0].data_nascita,'codice_fiscale')"/></td></tr>
<tr><td>Telefono</td> <td><input type="text" id="telefono" name="telefono"/></td></tr>
<tr><td>PEC</td> <td><input type="text" id="email" name="email" size="50"/></td></tr>
<tr><td>Documento identità</td> <td><input type="text" id="documento_identita" name="documento_identita"/></td></tr>
 
<tr><th colspan="2">Residenza</th></tr> 

<tr><td>Nazionalità</td> <td>
<% NazioniList.setJsEvent("onChange=\"nascondiCap(this.value,'capSoggetto','bottoneCapSoggetto')\""); %>
<%=NazioniList.getHtmlSelect("idNazioneSoggetto", 106) %></td></tr>
<tr><td>Provincia</td> <td><% ProvinceList.setJsEvent("onChange=\"PopolaListaComuniRappresentanteResidenza(this.value)\"");%> <%= ProvinceList.getHtmlSelect("idProvinciaSoggetto", -1) %></td></tr>
<tr><td>Comune</td> <td><div id="dividComuneSoggetto"></div></td></tr>
<tr><td>Indirizzo</td> <td>
						<%=ToponimiList.getHtmlSelect("toponimoSoggetto", 100)%> 
						<input type="text" id="viaSoggetto" name="viaSoggetto"  placeholder="indirizzo"/> 
						<input type="text" id="civicoSoggetto" name="civicoSoggetto" placeholder="CIVICO"/> 
						<input type="text" readonly id="capSoggetto" name="capSoggetto" placeholder="CAP" />  
						<input type="button" value="Calcola CAP" id="bottoneCapSoggetto" onclick="calcolaCap(document.getElementById('idComuneSoggetto').value, document.getElementById('toponimoSoggetto').value, document.getElementById('viaSoggetto').value, 'capSoggetto');" /> 
						<input type="hidden" id="stradarioNapoliResidenza" value="stradario" onClick="window.open('man/stradarioNapoli.pdf','window','width=1000,height=600')"></td></tr>

<tr><th colspan="2">Sede operativa</th></tr> 

<tr><td>ASL</td> <td><% AslList.setJsEvent("onChange=\"PopolaListaComuniOperativa(this.value)\""); %><%=AslList.getHtmlSelect("idAslStab", -1)%></td></tr>
<tr><td>Comune</td> <td><div id="dividComuneStab"></div></td></tr>
<tr><td>Indirizzo</td> <td><%=ToponimiList.getHtmlSelect("toponimoStab", 100)%> 
	<input type="text" id="viaStab" name="viaStab" placeholder="indirizzo" />
	<input type="text" id="civicoStab" name="civicoStab" placeholder="CIVICO"/> 
	<input type="text" readonly id="capStab" name="capStab" placeholder="CAP" />  
 	<input type="button" value="Calcola CAP" id="bottoneCapStab" onclick="calcolaCap(document.getElementById('idComuneStab').value, document.getElementById('toponimoStab').value, document.getElementById('viaStab').value, 'capStab');" /> 
<input type="hidden" id="stradarioNapoliSede" value="stradario" onClick="window.open('man/stradarioNapoli.pdf','window','width=1000,height=600')"></td></tr>
<tr><td>Coordinate</td> <td><input type="text" readonly id="latitudineStab" name="latitudineStab" placeholder="LAT" /> <input type="text" readonly id="longitudineStab" name="longitudineStab" placeholder="LON" /> <input id="coord1button" type="button" value="Calcola Coordinate" onclick="javascript:showCoordinate(getSelectedText('toponimoStab')+' '+document.getElementById('viaStab').value+', '+document.getElementById('civicoStab').value, getSelectedText('idComuneStab'), getSelectedText('idComuneStab'), document.getElementById('capStab').value, document.getElementById('latitudineStab'), document.getElementById('longitudineStab'));" /></td></tr>

<tr><th colspan="2">Attivita</th></tr> 

<tr><td>Tipo Carattere</td> <td><%=TipoCarattere.getHtmlSelect("carattere", 1) %></td></tr>
<tr> <td>Linea</td> <td>
<%-- <jsp:include page="../gestioneml/navigaml.jsp"> --%>
<%-- <jsp:param name="idFlussoOrig" value="5" /> --%>
<%-- </jsp:include> --%>
<input type="hidden" id="idLineaProduttiva" name="idLineaProduttiva" value=""/> <input type="text" readonly size="70" id="nomeLineaProduttiva" name="nomeLineaProduttiva" value="Il pezzo sulla selezione linee è commentato!"/>
</td></tr>

<tr><td>Data inizio attività</td> <td> 	<input readonly type="text" id="dataInizioLinea" name="dataInizioLinea" size="10"  /> <a href="#" onClick="cal19.select(document.forms[0].dataInizioLinea,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> </td></tr>
<tr><td>Data fine attività</td> <td> 	<input readonly type="text" id="dataFineLinea" name="dataFineLinea" size="10" /> <a href="#" onClick="cal19.select(document.forms[0].dataFineLinea,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"><img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> </td></tr>

<tr><td>CUN</td> <td><input type="text" id="cun" name="cun"/></td></tr>


<tr><td colspan="2"><input type="button" onClick="checkForm(this.form)" value="CONFERMA"/></td></tr>


</table>




</center>


<script>
document.getElementById("idAslStab").onchange();
document.getElementById("idProvincia").onchange();
document.getElementById("idProvinciaSoggetto").onchange();
</script>