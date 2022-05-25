<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<%boolean update = request.getParameter( "updateLiberoConsumo" ) != null && request.getParameter( "updateLiberoConsumo" ).equals("true") ;%>

<%@page import="org.aspcfs.modules.macellazionisintesis.utils.MacelliUtil"%>

<%@page import="org.aspcfs.modules.contacts.base.Contact"%><jsp:useBean id="OrgDetails"		class="org.aspcfs.modules.sintesis.base.SintesisStabilimento"	scope="request" />
<jsp:useBean id="Campioni"			class="java.util.ArrayList"									scope="request" />
<jsp:useBean id="Capo"				class="org.aspcfs.modules.macellazionisintesis.base.Capo"			scope="session" />
<jsp:useBean id="Campione"			class="org.aspcfs.modules.macellazionisintesis.base.Campione"		scope="request" />
<jsp:useBean id="Matrici"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Tampone"			class="org.aspcfs.modules.macellazionisintesis.base.Tampone"		scope="request" />
<jsp:useBean id="MatriciTamponi"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="AnalisiTamponi"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Nazioni"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Regioni"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Razze"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Specie"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PianiRisanamento"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiMacellazione"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EsitiVpm"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="BseList"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Patologie"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiEsame"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Azioni"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Gravita"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Stadi"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Organi"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiAnalisi"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Molecole"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="NonConformita"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Veterinari"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EsitiCampioni"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MotiviCampioni"	class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="MolecolePNR"           class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MolecoleBatteriologico"           class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MolecoleParassitologico"           class="org.aspcfs.utils.web.LookupList" scope="request" />

<%@ include file="../initPage.jsp"%>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>

<!-- RELATIVO AL NUOVO CALENDARIO CON MESE E ANNO FACILMENTE MODIFICABILI -->
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>

<script>

function gestisciObbligatorietaComunicazioniEsterne(){

	
	
}


function gestisciObbligatorietaMorteAnteMacellazione(){

	
}

function gestisciObbligatorietaVisitaAnteMortem(){

	
	
}
/*definito solo la firma della funzione */
function gestisciObbligatorietaVisitaPostMortem(){


	
	
}


</script>
<SCRIPT LANGUAGE="JavaScript" ID="js19">


var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
cal19.showNavigationDropdowns();
</SCRIPT>
<!-- ******************************************************************** -->


<script type="text/javascript">
//-------------------------------------------------------------------
//Confronta due date in formato stringa d/MM/y
//Ritorna :
//1 se dateA è maggiore di dateB
//0 se dateB è maggiore o uguale di dateA
//-1 se le date sono in formato errato
//-------------------------------------------------------------------
function confrontoAnni( dataA, dataB )
{
	var dataD_A = getDateFromFormat( dataA, 'd/MM/y' );
	var dataD_B = getDateFromFormat( dataB, 'd/MM/y' );

	if (dataD_A==0 || dataD_B==0) {
		return -1;
		}
	else if (dataD_A > dataD_B) {
		return 1;
		}
	return 0;
}

function confrontaDate(data1, data2) {

	var arr1 = data1.split("/");
	var arr2 = data2.split("/");

	var d1 = new Date(arr1[2], arr1[1] - 1, arr1[0]);
	var d2 = new Date(arr2[2], arr2[1] - 1, arr2[0]);

	var r1 = d1.getTime();
	var r2 = d2.getTime();

	if (r1 < r2)
		return -1;
	else if (r1 == r2)
		return 0;
	else
		return 1;
}

function toggleVetDiv( nomeCampo )
{
	var value = document.getElementById( nomeCampo ).value;

	if( value.length > 0 )
	{
		document.getElementById( nomeCampo + "_toggle" ).style.display = "block";
	}
};

function impostaDestinatarioMacelloCorrente(index){
	document.getElementById('destinatario_' + index + '_id').value = document.getElementById('id_macello').value;
	document.getElementById('destinatario_' + index + '_nome').value = "<%=OrgDetails.getName().replaceAll("\"","'")%>";
	document.getElementById('destinatario_label_' + index).innerHTML = "<%=OrgDetails.getName().replaceAll("\"","'")%>";
		
	document.getElementById('esercenteNoGisa' + index).style.display = 'none';
};

function set_vet( select, campo )
{
	var value_to_set = "";
	if( select.value != "-1" )
	{
		value_to_set = select.options[ select.selectedIndex ].text;
	}
	document.getElementById( campo ).value = value_to_set;
	toggleVetDiv( campo );
};

//index è l'indice del destinatario (1 se il primo 2 se il secondo)
function selectDestinazioneFromLinkTextarea( index )
{
	try {
		document.getElementById( 'destinatario_label_' + index ).innerHTML	= "-- Seleziona Destinatario --";
		if(document.getElementById( 'destinatario_' + index + "_id" ).value != "-999"){
			document.getElementById( 'destinatario_' + index + "_id" ).value	= "-1";
			document.getElementById( 'destinatario_' + index + "_nome" ).value	= "";
			document.getElementById('esercenteNoGisa' + index).value = '';
			document.getElementById('esercenteFuoriRegione' + index).value = '';
		}
		
		
		
		
		inReg = document.getElementById( "inRegione_" + index );
		fuoriReg = document.getElementById( "outRegione_" + index );
	
		if( inReg.checked )
		{
			document.getElementById( 'imprese_' + index ).style.display		= "block";
			document.getElementById( 'esercenti_' + index ).style.display	= "none";
			document.getElementById('esercenteFuoriRegione' + index).style.display = 'none';
			document.getElementById('esercenteFuoriRegione' + index).value = '';
		}
		else if( fuoriReg.checked )
		{
			document.getElementById( 'imprese_' + index ).style.display		= "none";
			document.getElementById( 'esercenti_' + index ).style.display	= "block";
			document.getElementById('esercenteNoGisa' + index).style.display = 'none';
			document.getElementById('esercenteNoGisa' + index).value = '';
		}
	}
	catch(err)
	{
		alert(err.description);
	}
}


function selectDestinazione( index )
{
	try {
		document.getElementById( 'destinatario_label_' + index ).innerHTML	= "-- Seleziona Destinatario --";
		
		document.getElementById( 'destinatario_' + index + "_id" ).value	= "-1";
		document.getElementById( 'destinatario_' + index + "_nome" ).value	= "";
		document.getElementById('esercenteFuoriRegione' + index).style.display = 'none';
		document.getElementById('esercenteFuoriRegione' + index).value = '';
		document.getElementById('esercenteNoGisa' + index).style.display = 'none';
		document.getElementById('esercenteNoGisa' + index).value = '';
		
		
		
		inReg = document.getElementById( "inRegione_" + index );
		fuoriReg = document.getElementById( "outRegione_" + index );
	
		if( inReg.checked )
		{
			document.getElementById( 'imprese_' + index ).style.display		= "block";
			document.getElementById( 'esercenti_' + index ).style.display	= "none";
			
		}
		else if( fuoriReg.checked )
		{
			document.getElementById( 'imprese_' + index ).style.display		= "none";
			document.getElementById( 'esercenti_' + index ).style.display	= "block";
			
		}
	}
	catch(err)
	{
		alert(err.description);
	}
}


function trim(str){
    return str.replace(/^\s+|\s+$/g,"");
} 


function mostraTextareaEsercente(idTextarea){
	document.getElementById(idTextarea).style.display = '';
}

function nascondiTextareaEsercente(idTextarea){
	document.getElementById(idTextarea).value = '';
	document.getElementById(idTextarea).style.display = 'none';
}

function valorizzaDestinatario(campoTextarea,idDestinatario){
	document.getElementById(idDestinatario + '_nome').value = campoTextarea.value;
	document.getElementById(idDestinatario + '_id').value = -999;
}

var ret = true;

function controllaForm( )
{
	var form = document.main;
	ret = true;
	message = "";

	try {

		if( trim(form.progressivo_macellazione.value).length == 0 ){
			form.progressivo_macellazione.value = 0;
		}
	  
		var progr = trim(document.getElementById('progressivo_macellazione').value);
		Geocodifica.controlloProgressivoMacellazioneBovini(document.getElementById('cd_seduta_macellazione').value,document.getElementById('id_macello').value, document.getElementById('vpm_data').value, progr, document.getElementById('cd_matricola').value, {callback: controlloProgressivoMacellazioneBoviniSalvaRet, async: false} );
		
		if ( form.vam_data.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.vam_data.value ) == 1 ) { 
				message += label("", "- [Evidenza Visita AM] : Inserire \"Data Visita Ante Mortem\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	
		
		if ( form.vam_data.value != '' && form.vpm_data.value != '' )
		{
			if ( confrontaDate( form.vam_data.value, form.vpm_data.value ) == 1 ) { 
				message += label("","- [Evidenza Visita AM] : Inserire \"Data Visita Ante Mortem\" minore o uguale alla \"Data Visita Post Mortem\" del capo" + " (" +  form.vpm_data.value + ")\r\n" );
				ret = false;
			}
		}
	
		if ( form.vpm_data.value != '' )
		{
			if ( confrontoAnni( form.vam_data.value, form.vpm_data.value ) == 1 ) { 
				message += label("", "- [Evidenza Visita PM] : Inserire \"Data Visita Post Mortem\" maggiore o uguale alla \"Data Visita Ante Mortem\" del capo" + " (" +  form.vam_data.value + ")\r\n" );
				ret = false;
			}
		}	
		
		if ( form.vpm_data.value != '' )
		{
			if ( confrontaDate( "<%=DateUtils.timestamp2string(Capo.getCd_data_arrivo_macello())%>", form.vpm_data.value ) == 1 ) { 
				message += label("", "- [Evidenza Visita PM] : Inserire \"Data Arrivo al macello\" antecedente o uguale alla \"Data Visita PM\" del capo("+form.vpm_data.value+")\r\n" );
				ret = false;
			}
		}
	
		if ( form.cmp_data_ricezione_esito_1.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.cmp_data_ricezione_esito_1.value ) == 1 ) { 
				message += label("", "- [Evidenza Visita PM] : Inserire \"Data Ricezione Esito Campione n°.1\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	
	
		if ( form.cmp_data_ricezione_esito_2.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.cmp_data_ricezione_esito_2.value ) == 1 ) { 
				message += label("", "- [Evidenza Visita PM] : Inserire \"Data Ricezione Esito Campione n°.2\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	
	
		if ( form.cmp_data_ricezione_esito_3.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.cmp_data_ricezione_esito_3.value ) == 1 ) { 
				message += label("", "- [Evidenza Visita PM] : Inserire \"Data Ricezione Esito Campione n°.3\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	
	
		if ( form.cmp_data_ricezione_esito_4.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.cmp_data_ricezione_esito_4.value ) == 1 ) { 
				message += label("", "- [Evidenza Visita PM] : Inserire \"Data Ricezione Esito Campione n°.4\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}	
	
		if ( form.cmp_data_ricezione_esito_5.value != '' )
		{
			if ( confrontoAnni( form.cd_data_nascita.value, form.cmp_data_ricezione_esito_5.value ) == 1 ) { 
				message += label("", "- [Evidenza Visita PM] : Inserire \"Data Ricezione Esito Campione n°.5\" maggiore o uguale alla \"Data di nascita\" del capo" + " (" +  form.cd_data_nascita.value + ")\r\n" );
				ret = false;
			}
		}


		
		//Controllo che almeno uno dei campi "Destinatario delle Carni" sia stato valorizzato
		/*
		if( trim(form.destinatario_1_nome.value).length == 0 && trim(form.destinatario_2_nome.value).length == 0 ){
			alert("[Evidenza Visita PM] : Inserire almeno un Destinatario delle Carni.\r\n" );
			ret = false;
		}
		*/

		if( trim(form.vpm_veterinario.value) == '' && trim(form.vpm_veterinario_2.value) == '' && trim(form.vpm_veterinario_3.value) == '' ){
			message += label("","- [Evidenza Visita PM] : Selezionare almeno un veterinario\r\n" );
			ret = false;
		}

		//Aggiunta
		if (ret == false) {
     		alert(label("", "La form non puo' essere salvata, verifica i seguenti errori:\r\n\r\n") + message);
   		}
   		else{
			 document.getElementById('submitOK').value = 'si';
		}
		

			
		//Run some code here
	} catch(err) {
	  	alert ('Attenzione: ' + err);
	  	ret = false;
	}
		
	return ret;
};

function checkNumerico(campo){

	campo.value = trim(campo.value);

	if (isNaN(campo.value)){
		alert('Il progressivo macellazione deve essere un valore numerico.');
		campo.value=0;
	}
	else if( parseInt(campo.value)<0 ){
		alert('Il progressivo macellazione non può avere un numero negativo.');
		campo.value=0;
	}

}

function gestisciUnload(){

	if(document.getElementById('submitOK').value == 'no'){
		if(confirm('Stai uscendo dalla scheda "<%= update ? "Modifica" : "Aggiungi"%> Capo". Cliccare su OK per salvare i dati, altrimenti le informazioni inserite verranno perse.')){
			if(controllaForm(false)){
				document.main.submit();
			}
			else{
				return false;
			}
		}
	}
	
}


function svuotaData(input){
	input.value = '';
}


function checkProgressivoMacellazione(){
	var progr = trim(document.getElementById('progressivo_macellazione').value);
	if (isNaN(progr)){
		alert('Il progressivo macellazione deve essere un valore numerico.');
		campo.value=0;
	}
	else if( parseInt(progr)<0 ){
		alert('Il progressivo macellazione non può avere un numero negativo.');
		campo.value=0;
	}
	else{
		Geocodifica.controlloProgressivoMacellazioneBovini(document.getElementById('cd_seduta_macellazione').value,document.getElementById('id_macello').value, document.getElementById('vpm_data').value, progr, document.getElementById('cd_matricola').value, {callback: controlloProgressivoMacellazioneBoviniRet, async: false} );
	}

}

function controlloProgressivoMacellazioneBoviniRet(ok){

	if(!ok){
		alert('Progressivo macellazione già esistente!');
		ret= false;
	}
	
	
}

function controlloProgressivoMacellazioneBoviniSalvaRet(ok){

	if(!ok){
		ret= false;
		message="Progressivo macellazione già esistente";
	}
	
	
}
</script>

<body onbeforeunload="return gestisciUnload();">
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<%
				if (request.getParameter("return") == null)
				{
			%>
					
					<a href="StabilimentoSintesisAction.do?command=DettaglioStabilimento&altId=<%=OrgDetails.getAltId() %>">Scheda Stabilimento</a> >
			<%
				}
				else if (request.getParameter("return").equals("dashboard"))
				{}
			%>
			<a href="MacellazioniSintesis.do?command=List&altId=<%=OrgDetails.getAltId() %>">Macellazioni</a> > <%= update ? "Modifica Capo" : "Aggiungi Capo" %>
		</td>
	</tr>
</table>

<%
String param1 = "altId=" + OrgDetails.getAltId()+"&stabId=" + OrgDetails.getIdStabilimento(); request.setAttribute("Operatore",OrgDetails.getOperatore());

String vpmData = "";
if(Capo!=null && Capo.getVpm_data()!=null)
	vpmData = DateUtils.timestamp2string(Capo.getVpm_data());
else if(request.getAttribute("vpmData")!=null)
	vpmData = (String)request.getAttribute("vpmData");
Integer cdSedutaMacellazione =0;
if(Capo!=null)
	 cdSedutaMacellazione = Capo.getCd_seduta_macellazione();
if(request.getAttribute("cdSedutaMacellazione")!=null)
	cdSedutaMacellazione = Integer.parseInt((String)request.getAttribute("cdSedutaMacellazione"));
%>

<dhv:container 
	name="sintesismacelli"
	selected="macellazioni" 
	object="Operatore" 
	param="<%=param1 %>" 
	appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' 
>

<br/><br/>

<form name="main" action="MacellazioniSintesis.do?command=<%=update ? "Update" : "Save" %>ToLiberoConsumo" method="post" onsubmit="return controllaForm( );">
<input type="hidden" name="altId" value="<%=OrgDetails.getAltId() %>" />
<input type="hidden" name="id_macello" id="id_macello" value="<%=OrgDetails.getAltId() %>" />
<input type="hidden" name="cd_matricola" id="cd_matricola" value="<%=Capo.getCd_matricola() %>" />
<input type="hidden" name="clone" value="no" />
<input type="hidden" id="submitOK" name="submitOK" value="no" />
<input type="hidden" name="id_capo" value="<%=request.getParameter("id_capo") %>" />
<input type="hidden" id="capo_in_bdn" name="capo_in_bdn" value="<%= request.getParameter("capo_in_bdn") %>" />
<input type="hidden" name="codice_azienda_nascita_from_bdn" value="<%= request.getParameter("codice_azienda_nascita_from_bdn") != null ? request.getParameter("codice_azienda_nascita_from_bdn") : "" %>" />
<input type="hidden" name="data_nascita_from_bdn" value="<%= request.getParameter("data_nascita_from_bdn") %>" />
<input type="hidden" name="specie_from_bdn" value="<%= request.getParameter("specie_from_bdn") %>" />
<input type="hidden" name="razza_from_bdn" value="<%= request.getParameter("razza_from_bdn") %>" />
<input type="hidden" name="asl_speditore_from_bdn" value="<%= request.getParameter("asl_speditore_from_bdn") %>" />
<input type="hidden" name="comune_speditore_from_bdn" value="<%= request.getParameter("comune_speditore_from_bdn") != null ? request.getParameter("comune_speditore_from_bdn") : "" %>" />
<input type="hidden" name="sesso_from_bdn" value="<%= request.getParameter("sesso_from_bdn") != null ? request.getParameter("sesso_from_bdn") : "" %>" />

<div class="demo">

<p style="font-weight: bold;background-color:yellow;text-align: center;font-size: 11pt;">Libero Consumo Carni e Visceri capo "<%=Capo.getCd_matricola() %>"</p>
<input type="submit" value="Salva" />
<input type="button" value="Salva e Prosegui" onclick="document.main.clone.value='ok';if(controllaForm()){document.main.submit();}" />
<table>
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="2" cellspacing="2">
			    <tbody>
			    <tr>
			        <td valign="top" width="55%">
			        <table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
			            <tbody>
			            <tr class="containerBody">
			               <td class="formLabel">Stato</td>
			               <td><%=Nazioni.getSelectedValue( Capo.getCd_provenienza_stato() ) %></td>
			            </tr>
			            <tr class="containerBody">
			               <td class="formLabel">Comunitario</td>
			               <td><%=Capo.isCd_prov_stato_comunitario() ? ("SI") : ("NO") %></td>
			             
			            </tr>
			            <%
				            //tolgo la voce "-- Seleziona --" dall'elenco delle regioni
				            Regioni.remove( 0 );
			            %>
			            <tr class="containerBody">
			            	<td class="formLabel">Regione</td>
			            	<td><%=toHtmlValue( Regioni.getSelectedValue( Capo.getCd_provenienza_regione() ) )%>&nbsp;</td>
			            </tr>
			            <tr class="containerBody">
			            	<td class="formLabel">Comune</td>
			            	<td><%=toHtmlValue( Capo.getCd_provenienza_comune() )%>&nbsp;</td>
			            </tr>
			            <tr class="containerBody">
			            	<td class="formLabel">Speditore</td>
			            	<td><%=toHtmlValue( Capo.getCd_speditore() )%>&nbsp;</td>
			            </tr>
			            <tr class="containerBody">
			                <td class="formLabel" >Matricola</td>
			                <td><%=toHtmlValue( Capo.getCd_matricola() )%>&nbsp;</td>
			            </tr>
			            <tr class="containerBody" >
			                <td class="formLabel" >Codice Azienda di Provenienza</td>
			                <td><%=toHtmlValue( Capo.getCd_codice_azienda() )%>&nbsp;</td>
			            </tr>
			            <%
				            //tolgo la voce "-- Seleziona --" dall'elenco delle specie
				            Specie.remove( 0 );
			            %>
			            <tr class="containerBody">
			                <td class="formLabel" >Specie<br></td>
			                <td><%=Specie.getSelectedValue( Capo.getCd_specie() ) %>&nbsp;</td>
			            </tr>
			            <tr class="containerBody">
			              <td class="formLabel" >Sesso</td>
			              <td><%=Capo.isCd_maschio() ? ("M") : ("F") %></td>
			            </tr>
			            <%
				            //tolgo la voce "-- Seleziona --" dall'elenco delle razze
				            Razze.remove( 0 );
			            %>
			            <tr class="containerBody">
			              <td class="formLabel" >Razza</td>
			             <td><%=Razze.getSelectedValue( Capo.getCd_id_razza() ) %>&nbsp;</td>
			            </tr>
			            <tr class="containerBody">
			              <td class="formLabel" >Data di nascita</td>
			              <td><dhv:tz timestamp="<%=Capo.getCd_data_nascita() %>" dateOnly="true" /></td>
			            </tr>	
			            <tr class="containerBody">
			              <td class="formLabel" >Vincolo sanitario</td>
			              <td valign="middle">
			              	<%=Capo.isCd_vincolo_sanitario() ? "SI" : "NO" %>
			                <%
                			String motivo = Capo.getCd_vincolo_sanitario_motivo();
                			if(motivo != null && !motivo.trim().equals("")){
                			%>
                			Motivo: <%= motivo %>
                			<%		
                			}
                			%>
			              </td>
			            </tr>
			            
			            <tr class="containerBody">
			              <td class="formLabel" >Mod. 4</td>
			              <td><%=Capo.getCd_mod4() %>&nbsp;</td>
			            </tr>
			            <tr class="containerBody">
			              <td class="formLabel" >Data Mod. 4</td>
			              <td><dhv:tz timestamp="<%=Capo.getCd_data_mod4() %>" dateOnly="true"/>&nbsp;</td>
			            </tr>
			            
			            <%
				            //tolgo la voce "-- Seleziona --" dall'elenco dei piani di risanamento
				            PianiRisanamento.remove( 0 );
			            %>
			            <tr class="containerBody">
			              <td class="formLabel" >Macell. differita piani risanamento</td>
			              <td><%=PianiRisanamento.getSelectedValue( Capo.getCd_macellazione_differita() ) %>&nbsp;</td>
			            </tr>
			            
						<tr class="containerBody">
			              <td class="formLabel" >Test BSE</td>
			              <td><%=( Capo.getCd_bse() >= 0 ) ? ( BseList.getSelectedValue( Capo.getCd_bse() ) ) : ( "NO" ) %></td>
			            </tr>
			            
			            <tr class="containerBody">
			              <td class="formLabel">Disponibili informazioni catena alimentare </td>
			              <td><%=Capo.isCd_info_catena_alimentare() ? "SI" : "NO" %></td>
			            </tr>
			        </tbody></table></td>
				</tr>
				</tbody>
			</table>
		</td>
		<td>
			<input readonly type="hidden" name="cd_data_nascita" id="cd_data_nascita" size="10" value="<%=DateUtils.timestamp2string(Capo.getCd_data_nascita())%>" />&nbsp;
			
			<table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
				<tr>
					<th colspan="3"><strong>Visita Ante Mortem </strong></th>
				</tr>
	            <tr class="containerBody">
	              <td class="formLabel" >Data</td>
	              <td colspan="2">
<%-- 	              		<zeroio:dateSelect timestamp="<%=new Timestamp( System.currentTimeMillis() ) %>" field="vam_data" form="main" showTimeZone="false"/> 	--%>
							<input readonly type="text" id="vam_data"  name="vam_data" size="10" value="<%=Capo.getVam_data() != null ? DateUtils.timestamp2string(Capo.getVam_data()) : DateUtils.timestamp2string(Capo.getCd_data_arrivo_macello()) %>"  />&nbsp;
			        		<a href="#" onClick="cal19.select(document.forms[0].vam_data,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 				<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 				<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].vam_data);"><img src="images/delete.gif" align="absmiddle"/></a>
	              </td>
	            </tr>
	            <tr class="containerBody">
	                <td class="formLabel">Esito</td>
	                <td colspan="2">
	                	<input type="hidden" NAME="vam_esito" VALUE="Favorevole" >Favorevole
					</P></td>               
           		 </tr>
	            
	           	<tr>
              		<th colspan="3"><strong>Macellazione</strong></th>
            	</tr>
<!--	            <tr class="containerBody">-->
<!--	              <td class="formLabel" >Progressivo</td>-->
<!--	              <td >-->
<!--	              	<input type="text" name="mac_progressivo" />-->
<!--	              </td>-->
<!--	            </tr>-->
	            <tr class="containerBody">
	              <td class="formLabel" >Tipo</td>
	              <td colspan="2"><%=TipiMacellazione.getHtmlSelect( "mac_tipo",  1 ) %></td>
	            </tr>
	            <tr class="containerBody">
	              <td class="formLabel" >Progressivo Macellazione</td>
	              <td colspan="2"><input type="text" id="progressivo_macellazione" name="progressivo_macellazione" value="<%=toHtmlValue( Capo.getProgressivo_macellazione() ) %>" onblur="checkNumerico(this);checkProgressivoMacellazione();"></input></td>
	            </tr>
	            	            
	            <tr>
              		<th colspan="3"><strong>Visita Post Mortem</strong></th>
	            </tr>
	            <tr class="containerBody">
	              <td class="formLabel" >Data Macellazione</td>
	              <td colspan="2">
<%--	              		<zeroio:dateSelect timestamp="<%=new Timestamp( System.currentTimeMillis() ) %>" field="vpm_data" form="main" showTimeZone="false" />  --%>
	              		<input readonly type="text" id="vpm_data" name="vpm_data" size="10" value="<%=vpmData%>" />&nbsp;
			        	<!-- a href="#" onClick="cal19.select(document.forms[0].vpm_data,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19" -->
			 			<!-- img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a -->
			 			<!-- a style="cursor: pointer;" onclick="svuotaData(document.forms[0].vpm_data);"><img src="images/delete.gif" align="absmiddle"/></a -->
	              		<input type="hidden" name="cd_seduta_macellazione" id="cd_seduta_macellazione" value="<%=cdSedutaMacellazione %>" />
	              </td>
	            </tr>
	            <tr class="containerBody">
	                <td class="formLabel">Esito</td>
	                <td colspan="2">
	                	<%=EsitiVpm.getSelectedValue( 1 ) %>
	                	<input type="hidden" name="vpm_esito" value="1" />
	                </td>
	            </tr>
	            
	            
	            <tr>
                	<td class="formLabel" colspan="3" align="left"><strong>Destinatario Carni</strong></td>
            	</tr>
            	
	            <tr class="containerBody">
			            	<td  class="formLabel">In Regione </td>
					        <td> 
						        Si <input 
							        	type="radio" 
							        	name="destinatario_1_in_regione" 
							        	value="si" 
							        	onclick="selectDestinazione(1)" 
							        	id="inRegione_1" 
							        	<%=(update && !Capo.isDestinatario_1_in_regione()) ? ("") : ("checked=\"checked\"") %> /> 
							        	
						        No <input 
						        		type="radio" 
						        		name="destinatario_1_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(1)" 
						        		id="outRegione_1"
						        		<%=(update && !Capo.isDestinatario_1_in_regione()) ? ("checked=\"checked\"") : ("") %> />
					        </td>
					        <td> 
						        Si <input 
						        		type="radio" 
						        		name="destinatario_2_in_regione" 
						        		value="si" 
						        		onclick="selectDestinazione(2)" 
						        		id="inRegione_2" 
						        		<%=(update && !Capo.isDestinatario_2_in_regione()) ? ("") : ("checked=\"checked\"") %> /> 
						        		
						        No <input 
						        		type="radio" 
						        		name="destinatario_2_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(2)" 
						        		id="outRegione_2"
						        		<%=(update && !Capo.isDestinatario_2_in_regione()) ? ("checked=\"checked\"") : ("") %> />
					        </td>
					    </tr>
					    <tr class="containerBody">
			            	<td  class="formLabel">Destinatario delle Carni </td>
					        <td>
						        <div 
						        	style="<%=(update && !Capo.isDestinatario_1_in_regione()) ? ("display:none") : ("") %>" 
						        	id="imprese_1">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 1, 'impresa' );" onclick="selectDestinazione(1);" >[Seleziona Impresa] </a><br/>
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 3, 'stab');" onclick="selectDestinazione(1);" >[Seleziona Stabilimento]  </a><br/>
						        	<a href="javascript:mostraTextareaEsercente('esercenteNoGisa1');" onclick="selectDestinazioneFromLinkTextarea(1);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						        	<a href="javascript:impostaDestinatarioMacelloCorrente(1);" onclick="" >[Macello corrente]</a>
						        	<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa1" name="esercenteNoGisa1" onchange="valorizzaDestinatario(this,'destinatario_1');" ><%=toHtmlValue( Capo.getDestinatario_1_nome() ) %></textarea>
						        </div>
						        <div  
						        	style="<%=(update && !Capo.isDestinatario_1_in_regione()) ? ("") : ("display:none") %>" 
						        	id="esercenti_1">
						       		<!--  <a href = "javascript:popLookupSelectorDestinazioneCarni( 'no', 1 );" >[Seleziona Esercente]</a> -->
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione1');" onclick="selectDestinazioneFromLinkTextarea(1);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione1" name="esercenteFuoriRegione1" onchange="valorizzaDestinatario(this,'destinatario_1');" ><%=toHtmlValue( Capo.getDestinatario_1_nome() ) %></textarea>
						        </div>
						        <br/>
						        <div id ="destinatario_label_1" align="center">
						        	<%=(Capo.getDestinatario_1_id() != -1) ? (toHtmlValue( Capo.getDestinatario_1_nome() )) : ("-- Seleziona Destinatario --") %>
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_1_id" 
					        		id="destinatario_1_id" 
					        		value="<%=(Capo.getDestinatario_1_id() != -1) ? (Capo.getDestinatario_1_id()) : ("-1") %>" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_1_nome" 
					        		id="destinatario_1_nome" 
					        		onchange=""
					        		value="<%=toHtmlValue( Capo.getDestinatario_1_nome() ) %>" />
					        		<p id="destinatarioCarni1" align="center" style="display: none;"><font color="red" >*</font></p>
							</td>
						    <td>
						    	<div style="" id="imprese_2">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 2, 'impresa' );" onclick="selectDestinazione(2);" >[Seleziona Impresa]</a><br/>
						      		<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 4, 'stab');" onclick="selectDestinazione(2);" >[Seleziona Stabilimento]</a><br/>
						      		<a href="javascript:mostraTextareaEsercente('esercenteNoGisa2');" onclick="selectDestinazioneFromLinkTextarea(2);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						      		<a href="javascript:impostaDestinatarioMacelloCorrente(2);" onclick="" >[Macello corrente]</a>
						      		<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa2" name="esercenteNoGisa2" onchange="valorizzaDestinatario(this,'destinatario_2');" ><%=toHtmlValue( Capo.getDestinatario_2_nome() ) %></textarea>

						        </div>
						        <div  style="display:none" id="esercenti_2">
						       		<!--  <a href = "javascript:popLookupSelectorDestinazioneCarni( 'no', 2 );" >[Seleziona Esercente]</a> -->
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione2');" onclick="selectDestinazioneFromLinkTextarea(2);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione2" name="esercenteFuoriRegione2" onchange="valorizzaDestinatario(this,'destinatario_2');" ><%=toHtmlValue( Capo.getDestinatario_2_nome() ) %></textarea>
						        </div>
						        <br/>
					        	<div id ="destinatario_label_2" align="center">
						        	<%=(Capo.getDestinatario_2_id() != -1) ? (toHtmlValue( Capo.getDestinatario_2_nome() )) : ("-- Seleziona Destinatario --") %>	
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_2_id" 
					        		id="destinatario_2_id" 
					        		value="<%=(Capo.getDestinatario_2_id() != -1) ? (Capo.getDestinatario_2_id()) : ("-1") %>" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_2_nome" 
					        		id="destinatario_2_nome" 
					        		onchange=""
					        		value="<%=toHtmlValue( Capo.getDestinatario_2_nome() ) %>" />
					        		<p id="destinatarioCarni2" align="center" style="display: none;"><font color="red" >*</font></p>
						    </td>
						</tr>
				
			            <tr class="containerBody">
			            	<td  class="formLabel">In Regione </td>
					        <td width="43%"> 
						        Si <input 
							        	type="radio" 
							        	name="destinatario_3_in_regione" 
							        	value="si" 
							        	onclick="selectDestinazione(3)" 
							        	id="inRegione_3" 
							        	<%=(update && !Capo.isDestinatario_3_in_regione()) ? ("") : ("checked=\"checked\"") %> /> 
							        	
						        No <input 
						        		type="radio" 
						        		name="destinatario_3_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(3)" 
						        		id="outRegione_3"
						        		<%=(update && !Capo.isDestinatario_3_in_regione()) ? ("checked=\"checked\"") : ("") %> />
					        </td>
					        <td> 
						        Si <input 
						        		type="radio" 
						        		name="destinatario_4_in_regione" 
						        		value="si" 
						        		onclick="selectDestinazione(4)" 
						        		id="inRegione_4" 
						        		<%=(update && !Capo.isDestinatario_4_in_regione()) ? ("") : ("checked=\"checked\"") %> /> 
						        		
						        No <input 
						        		type="radio" 
						        		name="destinatario_4_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(4)" 
						        		id="outRegione_4"
						        		<%=(update && !Capo.isDestinatario_4_in_regione()) ? ("checked=\"checked\"") : ("") %> />
					        </td>
					    </tr>
					    <tr class="containerBody">
			            	<td  class="formLabel">Destinatario delle Carni </td>
					        <td>
						        <div 
						        	style="<%=(update && !Capo.isDestinatario_3_in_regione()) ? ("display:none") : ("") %>" 
						        	id="imprese_3">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 5, 'impresa' );" onclick="selectDestinazione(3);" >[Seleziona Impresa] </a><br/>
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 5, 'stab');" onclick="selectDestinazione(3);" >[Seleziona Stabilimento]  </a><br/>
						        	<a href="javascript:mostraTextareaEsercente('esercenteNoGisa3');" onclick="selectDestinazioneFromLinkTextarea(3);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						        	<a href="javascript:impostaDestinatarioMacelloCorrente(3);" onclick="" >[Macello corrente]</a>
						        	<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa3" name="esercenteNoGisa3" onchange="valorizzaDestinatario(this,'destinatario_3');" ><%=toHtmlValue( Capo.getDestinatario_3_nome() ) %></textarea>
						        </div>
						        <div  
						        	style="<%=(update && !Capo.isDestinatario_3_in_regione()) ? ("") : ("display:none") %>" 
						        	id="esercenti_3">
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione3');" onclick="selectDestinazioneFromLinkTextarea(3);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione3" name="esercenteFuoriRegione3" onchange="valorizzaDestinatario(this,'destinatario_3');" ><%=toHtmlValue( Capo.getDestinatario_3_nome() ) %></textarea>
						        </div>
						        <br/>
						        <div id ="destinatario_label_3" align="center">
						        	<%=(Capo.getDestinatario_3_id() != -1) ? (toHtmlValue( Capo.getDestinatario_3_nome() )) : ("-- Seleziona Destinatario --") %>
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_3_id" 
					        		id="destinatario_3_id" 
					        		value="<%=(Capo.getDestinatario_3_id() != -1) ? (Capo.getDestinatario_3_id()) : ("-1") %>" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_3_nome" 
					        		id="destinatario_3_nome" 
					        		onchange=""
					        		value="<%=toHtmlValue( Capo.getDestinatario_3_nome() ) %>" />
					        		<p id="destinatarioCarni3" align="center" style="display: none;"><font color="red" >*</font></p>
							</td>
							
							
						    <td>
						    	<div style="" id="imprese_4">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 6, 'impresa' );" onclick="selectDestinazione(4);" >[Seleziona Impresa]</a><br/>
						      		<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 6, 'stab');" onclick="selectDestinazione(4);" >[Seleziona Stabilimento]</a><br/>
						      		<a href="javascript:mostraTextareaEsercente('esercenteNoGisa4');" onclick="selectDestinazioneFromLinkTextarea(4);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						      		<a href="javascript:impostaDestinatarioMacelloCorrente(4);" onclick="" >[Macello corrente]</a>
						      		<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa4" name="esercenteNoGisa4" onchange="valorizzaDestinatario(this,'destinatario_4');" ><%=toHtmlValue( Capo.getDestinatario_4_nome() ) %></textarea>

						        </div>
						        <div  style="display:none" id="esercenti_4">
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione4');" onclick="selectDestinazioneFromLinkTextarea(4);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione4" name="esercenteFuoriRegione4" onchange="valorizzaDestinatario(this,'destinatario_4');" ><%=toHtmlValue( Capo.getDestinatario_4_nome() ) %></textarea>
						        </div>
						        <br/>
					        	<div id ="destinatario_label_4" align="center">
						        	<%=(Capo.getDestinatario_4_id() != -1) ? (toHtmlValue( Capo.getDestinatario_4_nome() )) : ("-- Seleziona Destinatario --") %>	
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_4_id" 
					        		id="destinatario_4_id" 
					        		value="<%=(Capo.getDestinatario_4_id() != -1) ? (Capo.getDestinatario_4_id()) : ("-1") %>" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_4_nome" 
					        		id="destinatario_4_nome" 
					        		onchange=""
					        		value="<%=toHtmlValue( Capo.getDestinatario_4_nome() ) %>" />
					        		<p id="destinatarioCarni4" align="center" style="display: none;"><font color="red" >*</font></p>
						    </td>
						</tr>
					
					
	            
	            <tr>
                	<td class="formLabel" colspan="3" align="left"><strong>Veterinari addetti al controllo</strong></td>
            	</tr>
            	
	            <tr class="containerBody">
	            	<td colspan="3">
			            <table>
			            	<tr class="containerBody">
			                	<td>1. <input 
		                			value="<%=toHtmlValue( Capo.getVpm_veterinario() ) %>"
		                			onchange="javascript:toggleVetDiv('vpm_veterinario')" 
		                			size="35" id="vpm_veterinario" 
		                			name="vpm_veterinario" 
		                			type="text" />
		                	 		
		                	<%--  		
		                	<% Veterinari.setJsEvent( "onchange=\"set_vet( this, 'vpm_veterinario')\"" ); %>
							<%=Veterinari.getHtmlSelect( "veterinari_vpm", "-1" )%>
							--%>
							<% HashMap<String,ArrayList<Contact>> listaVeterinari = (HashMap<String,ArrayList<Contact>>)request.getAttribute("listaVeterinari"); %>
							<select id="veterinari_vpm" name="veterinari_vpm" onchange="set_vet( this, 'vpm_veterinario')">
									<option value="-1">Seleziona</option>
									<%for (String gruppo : listaVeterinari.keySet()){ %>
										<optgroup label="<%=gruppo %>"></optgroup>
										<%for(Contact vet : listaVeterinari.get(gruppo)){ %>
											<option <%if (Capo.getVpm_veterinario().trim().equals(vet.getNameLast().trim()) ){%>selected="selected" <%} %> value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
										<%} %>
									<%} %>
							</select>
							</td>
							<td style="background: none; border-right: none;">
		                		<font color="red">*</font>
		                	</td>
			                </tr>
				            <tr class="containerBody" id="vpm_veterinario_toggle" style="display: <%= Capo.getVpm_veterinario().equals("") ? "none" : "" %>" >
				                <td>2. <input 
		                			value="<%=toHtmlValue( Capo.getVpm_veterinario_2() ) %>"
			                		onchange="javascript:toggleVetDiv('vpm_veterinario_2')" 
			                		size="35" 
			                		id="vpm_veterinario_2" 
			                		name="vpm_veterinario_2" 
			                		type="text" />
			                <%-- 
			                <% Veterinari.setJsEvent( "onchange=\"set_vet( this, 'vpm_veterinario_2')\"" ); %>
			                <%=Veterinari.getHtmlSelect( "veterinari_vpm2", "-1" ) %>
			                --%>
			                <select id="veterinari_vpm2" name="veterinari_vpm2" onchange="set_vet( this, 'vpm_veterinario_2')">
									<option value="-1">Seleziona</option>
									<%for (String gruppo : listaVeterinari.keySet()){ %>
										<optgroup label="<%=gruppo %>"></optgroup>
										<%for(Contact vet : listaVeterinari.get(gruppo)){ %>
											<option <%if (Capo.getVpm_veterinario_2().trim().equals(vet.getNameLast().trim()) ){%>selected="selected" <%} %> value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
										<%} %>
									<%} %>
							</select>
			                </td>
				            </tr>
				            <tr class="containerBody" id="vpm_veterinario_2_toggle" style="display: <%= Capo.getVpm_veterinario_2().equals("") ? "none" : "" %>" >
				                <td>3. <input 
		                			value="<%=toHtmlValue( Capo.getVpm_veterinario_3() ) %>"
			                		size="35" 
			                		id="vpm_veterinario_3"  
			                		name="vpm_veterinario_3" 
			                		type="text" />
			                <%-- 		
			                <% Veterinari.setJsEvent( "onchange=\"set_vet( this, 'vpm_veterinario_3')\"" ); %>
			                <%=Veterinari.getHtmlSelect( "veterinari_vpm3", "-1" ) %>
			                --%>
			                <select id="veterinari_vpm3" name="veterinari_vpm3" onchange="set_vet( this, 'vpm_veterinario_3')">
									<option value="-1">Seleziona</option>
									<%for (String gruppo : listaVeterinari.keySet()){ %>
										<optgroup label="<%=gruppo %>"></optgroup>
										<%for(Contact vet : listaVeterinari.get(gruppo)){ %>
											<option <%if (Capo.getVpm_veterinario_3().trim().equals(vet.getNameLast().trim()) ){%>selected="selected" <%} %> value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
										<%} %>
									<%} %>
							</select>
			                
			                </td>
				            </tr>
			            </table>
		            </td>
	            </tr>
            	 
	              <tr class="containerBody">
	                <td class="formLabel">Note</td>
	                <td colspan="2"><textarea name="vpm_note" rows="2" cols="25"></textarea></td>
	            </tr> 
			</table>
			
			<!--  Inclusione tabella campione	-->
    		<%@include file="include_campioni_add.jsp" %>
    		<%@include file="include_tamponi_add.jsp" %>
			
		</td>
	</tr>
	
</table>

</div><!-- End demo -->

</form>

 
</dhv:container>
</body>
