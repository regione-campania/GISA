<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.macellazioninewopu.utils.ConfigTipo"%>
<%@page import="org.aspcfs.modules.macellazioninewopu.utils.ReflectionUtil"%>
<%@page import="org.aspcfs.modules.macellazioninewopu.base.Partita"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<%boolean update = request.getAttribute( "updateLiberoConsumo" ) != null && ((Boolean)request.getAttribute( "updateLiberoConsumo" ));%>

<%@page import="org.aspcfs.modules.macellazioninewopu.utils.MacelliUtil"%>

<%@page import="org.aspcfs.modules.contacts.base.Contact"%><jsp:useBean id="OrgDetails"		class="org.aspcfs.modules.opu.base.Stabilimento"	scope="request" />
<jsp:useBean id="Campioni"			class="java.util.ArrayList"									scope="request" />
<jsp:useBean id="Partita"				class="org.aspcfs.modules.macellazioninewopu.base.Partita"			scope="session" />
<jsp:useBean id="Campione"			class="org.aspcfs.modules.macellazioninewopu.base.Campione"		scope="request" />
<jsp:useBean id="Matrici"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Tampone"			class="org.aspcfs.modules.macellazioninewopu.base.Tampone"		scope="request" />
<jsp:useBean id="MatriciTamponi"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ProvvedimentiVAM"	class="org.aspcfs.utils.web.LookupList" scope="request" />
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

<SCRIPT LANGUAGE="JavaScript" SRC="macellazioninew/cambio_specie.js"></SCRIPT>

<script type="text/javascript" >
var righeDest = 1;
var righeMax = 1; 

<!--var righeDest = 2;-->
<!--var righeMax = %=(Integer)request.getAttribute("righeDestDefault"); -->
function controllaObbligNumCapiDestCarni()
	{
		var i=0;
		while(i<destinatariSelezionati.length)
		{
			var numCapiOvini   = document.getElementById('num_capi_ovini_' + destinatariSelezionati[i]);
			var numCapiCaprini = document.getElementById('num_capi_caprini_' + destinatariSelezionati[i]);
			
			if(numCapiOvini!=null)
			{
				if(numCapiOvini.value=="")
				{
					if(numCapiCaprini!=null)
					{
						if(numCapiCaprini.value=="")
							return false;
					}
				}
			}
			else
			{
				if(numCapiCaprini.value=="")
					return false;
			}
			i++;
		}
		return true;
	}
	
</script>


<script>

function gestisciObbligatorietaComunicazioniEsterne(){

	
	
}


function gestisciObbligatorietaMorteAnteMacellazione(){

	
}

function gestisciObbligatorietaVisitaAnteMortem(){
	var dataVisitaAnteMortem;
	var provvedimentoVisitaAnteMortem;
	var numCapiOvini="";
	var numCapiCaprini="";
	
	dataVisitaAnteMortem = document.getElementById('vam_data').value != '';
	provvedimentoVisitaAnteMortem = document.getElementById('vam_provvedimenti').selectedIndex > 0;
	if(document.getElementById('vam_num_capi_ovini')!=null)
		numCapiOvini=document.getElementById('vam_num_capi_ovini').value;
	if(document.getElementById('vam_num_capi_caprini')!=null)
		numCapiCaprini=document.getElementById('vam_num_capi_caprini').value;
	
	if(dataVisitaAnteMortem || provvedimentoVisitaAnteMortem || numCapiOvini!='' || numCapiCaprini!=''){
		document.getElementById('dataVisitaAnteMortem').style.display = '';
		document.getElementById('provvedimentoVisitaAnteMortem').style.display = '';
		document.getElementById('vam_num_capi_ovini_asterisco').style.display = '';
		document.getElementById('vam_num_capi_caprini_asterisco').style.display = '';
	}
	else{
		document.getElementById('dataVisitaAnteMortem').style.display = 'none';
		document.getElementById('provvedimentoVisitaAnteMortem').style.display = 'none';
		document.getElementById('vam_num_capi_ovini_asterisco').style.display = 'none';
		document.getElementById('vam_num_capi_caprini_asterisco').style.display = 'none';
	}
	
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

destinatariSelezionati = new Array();

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
		document.getElementById( 'destinatario_label_' + index ).innerHTML	= "-- Seleziona Destinatario/Esercente --";
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
		document.getElementById( 'destinatario_label_' + index ).innerHTML	= "-- Seleziona Destinatario/Esercente --";
		
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

function calcolaNumCapiOviniDestCorrente()
{
	var toReturn = parseInt('0');
	var i=1;
	for(i=1;i<=20;i++)
	{
		if(document.getElementById('num_capi_ovini_'+i)!=null && document.getElementById('num_capi_ovini_'+i).value!="")
			toReturn += parseInt(document.getElementById('num_capi_ovini_'+i).value);
	}
	return toReturn;
}

function calcolaNumCapiCapriniDestCorrente()
{
	var toReturn = parseInt('0');
	var i=1;
	for(i=1;i<=20;i++)
	{
		if(document.getElementById('num_capi_caprini_'+i)!=null && document.getElementById('num_capi_caprini_'+i).value!="")
			toReturn += parseInt(document.getElementById('num_capi_caprini_'+i).value);
	}
	return toReturn;
}

function controllaForm( )
{
	var form = document.main;
	var ret = true;
	message = "";
	try {
		
		var dataVisitaAnteMortem = document.getElementById('vam_data').value != '';
		var provvedimentoVisitaAnteMortem = document.getElementById('vam_provvedimenti').selectedIndex > 0;
		
		var vpm_data_valorizzato = form.vpm_data!=null && form.vpm_data.value != '';
		var numCapiOvini="";
		var numCapiCaprini="";
		
		if(document.getElementById('vam_num_capi_ovini')!=null)
			numCapiOvini=document.getElementById('vam_num_capi_ovini').value;
		if(document.getElementById('vam_num_capi_caprini')!=null)
			numCapiCaprini=document.getElementById('vam_num_capi_caprini').value;
		
		if(dataVisitaAnteMortem || provvedimentoVisitaAnteMortem || vpm_data_valorizzato || numCapiOvini!="" || numCapiCaprini!=""){
			
			//...allora vedi se ce n'è almeno uno NON valorizzato
			if(!dataVisitaAnteMortem || !provvedimentoVisitaAnteMortem || !vpm_data_valorizzato){
				message += label("","- [Evidenza Visita AM] :\r\n \tUno dei seguenti campi è valorizzato:\r\n \t*Data \r\n \t*Provvedimento adottato\r\n \t*Data Visita PM\r\n \t*Numero capi\r\n \tValorizzare anche gli altri.\r\n"  );
				ret = false;
			}
			
		}
		
		if ( form.vam_data.value != '' && form.vpm_data.value != '' )
		{
			if ( confrontaDate( form.vam_data.value, form.vpm_data.value ) == 1 ) { 
				message += label("","- [Evidenza Visita AM] : Inserire \"Data Visita Ante Mortem\" minore o uguale alla \"Data Visita Post Mortem\"" + " (" +  form.vpm_data.value + ")\r\n" );
				ret = false;
			}
		}
		
		/*if( trim(form.destinatario_1_nome.value).length == 0 && trim(form.destinatario_2_nome.value).length == 0 )
		{
			message += label("", "[Evidenza Visita PM] : Inserire almeno un Destinatario delle Carni.\r\n" );
			ret = false;
		}*/
		
		if(!controllaObbligNumCapiDestCarni())
		{
			message += label("", "- [Evidenza Visita PM] : Inserire \"Numero Capi\" per ogni destinatario selezionato\r\n" );
			ret = false;	
		}
		
		var casl_num_capi_ovini = 0;
		var vam_num_capi_ovini = 0;
		if(form.casl_num_capi_ovini.value!="")
			casl_num_capi_ovini = parseInt(form.casl_num_capi_ovini.value);
		if(form.vam_num_capi_ovini.value!="")
			vam_num_capi_ovini = parseInt(form.vam_num_capi_ovini.value);
		
		if(form.numCapiOviniPartita_AltreSedute.value!="")
			numCapiOviniPartita_AltreSedute = parseInt(form.numCapiOviniPartita_AltreSedute.value);
		
		
		
		var casl_num_capi_caprini = parseInt('0');
		var vam_num_capi_caprini = parseInt('0');
		
		if(form.casl_num_capi_caprini.value!="")
			casl_num_capi_caprini = parseInt(form.casl_num_capi_caprini.value);
		if(form.vam_num_capi_caprini.value!="")
			vam_num_capi_caprini = parseInt(form.vam_num_capi_caprini.value);
		
		if(form.numCapiCapriniPartita_AltreSedute.value!="")
			numCapiCapriniPartita_AltreSedute = parseInt(form.numCapiCapriniPartita_AltreSedute.value);
		
		
		var numCapiOviniDestTotale = calcolaNumCapiOviniDestCorrente() + parseInt(numCapiOviniDestPartita_AltreSedute) + parseInt(numCapiOviniMavamPartita_AltreSedute);
		var numCapiOviniControlloDocumentale = parseInt(form.cd_num_capi_ovini.value);
		
		if(numCapiOviniDestTotale>numCapiOviniControlloDocumentale)
		{
				message += label("","- [Evidenza Visita AM] : \"Numero Capi Ovini\" specificati nella Visita AM e Morte Ant.macellazione non può essere superiore al numero dei capi ovini specificati nel controllo documentale\r\n" );
				ret = false;
		}
		
		var numCapiCapriniDestTotale = calcolaNumCapiCapriniDestCorrente() + parseInt(numCapiCapriniDestPartita_AltreSedute) + parseInt(numCapiCapriniMavamPartita_AltreSedute);
		var numCapiCapriniControlloDocumentale = parseInt(form.cd_num_capi_caprini.value);
		
		if(numCapiCapriniDestTotale>numCapiCapriniControlloDocumentale)
		{
				message += label("","- [Evidenza Visita AM] : \"Numero Capi Caprini\" specificati nella Visita AM e Morte Ant.macellazione non può essere superiore al numero dei capi caprini specificati nel controllo documentale\r\n" );
				ret = false;
		}
		var num_capi_ovini_esito_2_val = 0;
		if(document.getElementById('num_capi_ovini_esito_2')!=null && document.getElementById('num_capi_ovini_esito_2').value!='')
			num_capi_ovini_esito_2_val = parseInt(document.getElementById('num_capi_ovini_esito_2').value);
		if(calcolaNumCapiOviniDestCorrente()+num_capi_ovini_esito_2_val!=vam_num_capi_ovini)
		{
				message += label("","- [Evidenza Visita PM] : \"Numero Capi Ovini\" specificati nei destinatari delle carni non può essere diverso da quello specificato nella Visita AM\r\n" );
				ret = false;
		}
		
		var num_capi_caprini_esito_2_val = 0;
		if(document.getElementById('num_capi_caprini_esito_2')!=null && document.getElementById('num_capi_caprini_esito_2').value!='')
			num_capi_caprini_esito_2_val = parseInt(document.getElementById('num_capi_caprini_esito_2').value);
		if(calcolaNumCapiCapriniDestCorrente()+num_capi_caprini_esito_2_val!=vam_num_capi_caprini)
		{
				message += label("","- [Evidenza Visita PM] : \"Numero Capi Caprini\" specificati nei destinatari delle carni non può essere diverso da quello specificato nella Visita AM\r\n" );
				ret = false;
		}
		
		
		
// 		if ( form.vam_data.value != '' )
// 		{
// 			if ( confrontoAnni( form.cd_data_nascita.value, form.vam_data.value ) == 1 ) { 
// 				message += label("", "- [Evidenza Visita AM] : Inserire \"Data Visita Ante Mortem\" maggiore o uguale alla \"Data di nascita\" del partita" + " (" +  form.cd_data_nascita.value + ")\r\n" );
// 				ret = false;
// 			}
// 		}	

		if ( document.getElementById("vpm_data").value != '' )
		{
			if ( confrontoAnni( document.getElementById("vam_data").value, document.getElementById("vpm_data").value ) == 1 ) { 
				message += label("", "- [Evidenza Visita PM] : Inserire \"Data Visita Post Mortem\" maggiore o uguale alla \"Data Visita Ante Mortem\" del partita" + " (" +  document.getElementById("vam_data").value + ")\r\n" );
				ret = false;
			}
		}
		
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
		if(confirm('Stai uscendo dalla scheda "<%= update ? "Modifica" : "Aggiungi"%> Seduta". Stai uscendo dalla scheda Cliccare su OK per salvare i dati, altrimenti le informazioni inserite verranno perse.')){
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


</script>

<body onbeforeunload="return gestisciUnload();" onload="return gestisciObbligatorietaVisitaAnteMortem();">
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="OpuStab.do"><dhv:label name="stabilimenti.stabilimenti">Accounts</dhv:label></a> >
			<%
				if (request.getParameter("return") == null)
				{
			%>
					<a href="OpuStab.do?command=Search"><dhv:label
					name="stabilimenti.SearchResults">Search Results</dhv:label></a> >
					<a href="OpuStab.do?command=Details&altId=<%=OrgDetails.getAltId() %>">Scheda Stabilimento</a> >
			<%
				}
				else if (request.getParameter("return").equals("dashboard"))
				{
			%>
					<a href="OpuStab.do?command=Dashboard"><dhv:label
					name="communications.campaign.Dashboard">Dashboard</dhv:label></a> > <%
				}
			%>
			<a href="MacellazioniNewOpu.do?command=List&altId=<%=OrgDetails.getAltId() %>">Macellazioni</a> > <%= update ? "Modifica Seduta" : "Aggiungi Seduta" %>
		</td>
	</tr>
</table>

<%
String param1 = "altId=" + OrgDetails.getAltId()+"&stabId=" + OrgDetails.getIdStabilimento(); request.setAttribute("Operatore",OrgDetails.getOperatore());
%>

<dhv:container 
	name="suapmacelli"
	selected="macellazioni" 
	object="Operatore"  
	param="<%=param1 %>" 
	appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' 
	>

<br/><br/>

<form name="main" action="MacellazioniNewOpu.do?command=<%=update ? "Update" : "Save" %>ToLiberoConsumoSeduta&id=<%=Partita.getId()%>" method="post" onsubmit="return controllaForm( );">
<input type="hidden" name="altId" value="<%=OrgDetails.getAltId() %>" />
<input type="hidden" name="cd_partita" value="<%=Partita.getCd_partita()%>" />
<input type="hidden" name="id_macello" id="id_macello" value="<%=OrgDetails.getAltId() %>" />
<input type="hidden" name="clone" value="no" />
<input type="hidden" name="cd_num_capi_ovini" id="cd_num_capi_ovini" value="<%=Partita.getCd_num_capi_ovini() %>" />
<input type="hidden" name="cd_num_capi_caprini" id="cd_num_capi_caprini" value="<%=Partita.getCd_num_capi_caprini() %>" />
<input type="hidden" name="casl_num_capi_ovini" id="casl_num_capi_ovini" value="<%=Partita.getCasl_num_capi_ovini() %>" />
<input type="hidden" name="casl_num_capi_caprini" id="casl_num_capi_caprini" value="<%=Partita.getCasl_num_capi_caprini() %>" />
<input type="hidden" id="submitOK" name="submitOK" value="no" />
<input type="hidden" name="id_partita" value="<%=request.getParameter("id_partita") %>" />
<input type="hidden" id="numCapiPartita_AltreSedute" name="numCapiPartita_AltreSedute" value="<%=(String)request.getAttribute("numCapiPartita_AltreSedute")%>" />
<input type="hidden" id="numCapiOviniDestPartita_AltreSedute" name="numCapiOviniDestPartita_AltreSedute" value="<%=(String)request.getAttribute("numCapiOviniDestPartita_AltreSedute")%>" />
<input type="hidden" id="numCapiCapriniDestPartita_AltreSedute" name="numCapiCapriniDestPartita_AltreSedute" value="<%=(String)request.getAttribute("numCapiCapriniDestPartita_AltreSedute")%>" />
<input type="hidden" id="numCapiOviniMavamPartita_AltreSedute" name="numCapiOviniMavamPartita_AltreSedute" value="<%=(String)request.getAttribute("numCapiOviniMavamPartita_AltreSedute")%>" />
<input type="hidden" id="numCapiCapriniMavamPartita_AltreSedute" name="numCapiCapriniMavamPartita_AltreSedute" value="<%=(String)request.getAttribute("numCapiCapriniMavamPartita_AltreSedute")%>" />
<input type="hidden" id="numCapiOviniPartita_AltreSedute" name="numCapiOviniPartita_AltreSedute" value="<%=(String)request.getAttribute("numCapiOviniPartita_AltreSedute")%>" />
<input type="hidden" id="numCapiCapriniPartita_AltreSedute" name="numCapiCapriniPartita_AltreSedute" value="<%=(String)request.getAttribute("numCapiCapriniPartita_AltreSedute")%>" />

<div class="demo">

<p style="font-weight: bold;background-color:yellow;text-align: center;font-size: 11pt;">Libero Consumo Carni e Visceri partita "<%=Partita.getCd_partita() %>"</p>
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
			               <td><%=Nazioni.getSelectedValue( Partita.getCd_provenienza_stato() ) %></td>
			            </tr>
			            <tr class="containerBody">
			               <td class="formLabel">Comunitario</td>
			               <td><%=Partita.isCd_prov_stato_comunitario() ? ("SI") : ("NO") %></td>
			             
			            </tr>
			            <%
				            //tolgo la voce "-- Seleziona --" dall'elenco delle regioni
				            Regioni.remove( 0 );
			            %>
			            <tr class="containerBody">
			            	<td class="formLabel">Regione</td>
			            	<td><%=toHtmlValue( Regioni.getSelectedValue( Partita.getCd_provenienza_regione() ) )%>&nbsp;</td>
			            </tr>
			            <tr class="containerBody">
			            	<td class="formLabel">Comune</td>
			            	<td><%=toHtmlValue( Partita.getCd_provenienza_comune() )%>&nbsp;</td>
			            </tr>
			            <tr class="containerBody">
			            	<td class="formLabel">Speditore</td>
			            	<td><%=toHtmlValue( Partita.getCd_speditore() )%>&nbsp;</td>
			            </tr>
			            <tr class="containerBody">
			                <td class="formLabel" >Partita</td>
			                <td><%=toHtmlValue( Partita.getCd_partita() )%>&nbsp;</td>
			            </tr>
			            <tr class="containerBody" >
			                <td class="formLabel" >Codice Azienda di Provenienza</td>
			                <td><%=toHtmlValue( Partita.getCd_codice_azienda() )%>&nbsp;</td>
			            </tr>
			            <%
				            //tolgo la voce "-- Seleziona --" dall'elenco delle specie
				            Specie.remove( 0 );
			            %>
			            <tr class="containerBody">
			            
			    <input type="hidden" id="Specie1" name="Specie1" value="Ovini"/>
                <input type="hidden" id="Specie2" name="Specie2" value="Caprini"/>
                <input type="hidden" id="categoriaPartitaOvicaprini" name="categoriaPartita" value="1" <%= (!Partita.isSpecie_suina() ? "checked" : "") %> /> 
                <input type="hidden" id="categoriaPartitaSuini" name="categoriaPartita" value="2" <%= (Partita.isSpecie_suina() ? "checked" : "") %>/>
                <input type="hidden" id="specie_suina" name="specie_suina" value="<%=(Partita.isSpecie_suina() ? "true" : "false") %>"/>
                
                
			                <td class="formLabel" >Specie<br></td>
			                <td>
<% 
			                if(Partita.getCd_num_capi_ovini()>0)
		                		out.println("<label class=\"specie1\">Ovini</label>");	
							if(Partita.getCd_num_capi_caprini()>0)
							{
								if(Partita.getCd_num_capi_ovini()>0)
									out.println(", ");
								out.println("<label class=\"specie2\">Caprini</label>");
							}
%>	
							</td>
			            </tr>
			            <%
				            //tolgo la voce "-- Seleziona --" dall'elenco delle razze
				            Razze.remove( 0 );
			            %>
			            <tr class="containerBody">
			              <td class="formLabel" >Vincolo sanitario</td>
			              <td valign="middle">
			              	<%=Partita.isCd_vincolo_sanitario() ? "SI" : "NO" %>
			                <%
                			String motivo = Partita.getCd_vincolo_sanitario_motivo();
                			if(motivo != null && !motivo.trim().equals("")){
                			%>
                			Motivo: <%= motivo %>
                			<%		
                			}
                			%>
			              </td>
			            </tr>
			            
			            <tr class="containerBody">
			              <td class="formLabel" >Numero Mod. 4/ Cert.Sanit.</td>
			              <td><%=Partita.getCd_mod4() %>&nbsp;</td>
			            </tr>
			            <tr class="containerBody">
			              <td class="formLabel" >Data Mod. 4/ Cert.Sanit.</td>
			              <td><dhv:tz timestamp="<%=Partita.getCd_data_mod4() %>" dateOnly="true"/>&nbsp;</td>
			            </tr>
			            
			            <%
				            //tolgo la voce "-- Seleziona --" dall'elenco dei piani di risanamento
				            PianiRisanamento.remove( 0 );
			            %>
			            <tr class="containerBody">
			              <td class="formLabel" >Macell. differita piani risanamento</td>
			              <td><%=PianiRisanamento.getSelectedValue( Partita.getCd_macellazione_differita() ) %>&nbsp;</td>
			            </tr>
			            
						<tr class="containerBody">
			              <td class="formLabel" >Partita sottoposta a Test TSE</td>
			              <td><%=( ((Partita)Partita).getCd_bse() ) ? ( "SI" ) : ( "NO" ) %></td>
			            </tr>
			            
			            <tr class="containerBody">
			              <td class="formLabel">Disponibili informazioni catena alimentare </td>
			              <td><%=Partita.isCd_info_catena_alimentare() ? "SI" : "NO" %></td>
			            </tr>
			            <tr class="containerBody">
                <td class="formLabel" >Numero Capi <label class="specie1">Ovini</label><br/>Controllo documentale</td>
                <td>
                	<%=Partita.getCd_num_capi_ovini()%>
                </td>
            </tr>
            <tr class="containerBody">
                <td class="formLabel" >Numero Capi <label class="specie2">Caprini</label><br/>Controllo documentale</td>
                <td>
                	<%=Partita.getCd_num_capi_caprini()%>
                </td>
            </tr>
            <tr class="containerBody">
                <td class="formLabel" >Numero Capi <label class="specie1">Ovini</label><br/>Morte Ant.Macellazione</td>
                <td>
                	<%=Partita.getMavam_num_capi_ovini()%>
                </td>
            </tr>
            <tr class="containerBody">
                <td class="formLabel" >Numero Capi <label class="specie2">Caprini</label><br/>Morte Ant.Macellazione</td>
                <td>
                	<%=Partita.getMavam_num_capi_caprini()%>
                </td>
            </tr>
			        </tbody></table></td>
				</tr>
				</tbody>
			</table>
		</td>
		<td>
			<table class="details" width="100%" border="0" cellpadding="4" cellspacing="0">
				<tr>
					<th colspan="3"><strong>Visita Ante Mortem </strong></th>
				</tr>
	            <tr class="containerBody">
	              <td class="formLabel" >Data</td>
	              <td colspan="2">
<%-- 	              		<zeroio:dateSelect timestamp="<%=new Timestamp( System.currentTimeMillis() ) %>" field="vam_data" form="main" showTimeZone="false"/> 	--%>
							<input readonly type="text" id="vam_data"  name="vam_data" size="10" value="<%=Partita.getVam_data() != null ? DateUtils.timestamp2string(Partita.getVam_data()) : DateUtils.timestamp2string(Partita.getCd_data_arrivo_macello()) %>"  />&nbsp;
			        		<font color="red" id="dataVisitaAnteMortem" style="display: none;">*</font>
			        		<a href="#" onClick="cal19.select(document.forms[0].vam_data,'anchor19','dd/MM/yyyy'); gestisciObbligatorietaVisitaAnteMortem();return false;" NAME="anchor19" ID="anchor19">
			 				<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 				<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].vam_data);gestisciObbligatorietaVisitaAnteMortem();"><img src="images/delete.gif" align="absmiddle"/></a>
	              </td>
	            </tr>
	            <tr class="containerBody">
	                <td class="formLabel">Esito</td>
	                <td colspan="2">
	                	<input type="hidden" NAME="vam_esito" VALUE="Favorevole" >Favorevole
					</P></td>               
           		 </tr>
           		 
           		 <tr class="containerBody">
            	<td class="formLabel">Numero Capi <label class="specie1">Ovini</label></td>
                <td colspan="2">
<%
				String num_capi_ovini = "";
%>
                	<input type="text" <% if(Partita.getCd_num_capi_ovini()<=0){out.println("disabled=\"disabled\"");} %> id="vam_num_capi_ovini" name="vam_num_capi_ovini" onblur="gestisciObbligatorietaVisitaAnteMortem()" onchange="isIntPositivo(this.value,'Numero Capi Ovini',this);" value="<%=toHtmlValue(num_capi_ovini) %>" />
                	<font style="display:none;" id="vam_num_capi_ovini_asterisco" align="center" color="red" >*</font>
                </td>
            </tr>
            
            <tr class="containerBody">
            	<td class="formLabel">Numero Capi <label class="specie2">Caprini</label></td>
                <td colspan="2">
<%
				String num_capi_caprini = "";
%>
                	<input type="text" <% if(Partita.getCd_num_capi_caprini()<=0){out.println("disabled=\"disabled\"");} %> id="vam_num_capi_caprini" name="vam_num_capi_caprini" onblur="gestisciObbligatorietaVisitaAnteMortem()" onchange="isIntPositivo(this.value,'Numero Capi Caprini',this);" value="<%=toHtmlValue(num_capi_caprini) %>" />
                	<font style="display:none;" id="vam_num_capi_caprini_asterisco" align="center" color="red" >*</font>
                </td>
            </tr>
            
            <tr class="containerBody">
                <td class="formLabel">Provvedimento adottato</td>
                <td colspan="2">
					<%
						ProvvedimentiVAM.setJsEvent("onChange=\"javascript:gestisciObbligatorietaVisitaAnteMortem();\"");
					%>                               
                	
	               	<%=ProvvedimentiVAM.getHtmlSelect( "vam_provvedimenti", -1 ) %>
	               	<font color="red" id="provvedimentoVisitaAnteMortem" style="display: none;">*</font>
                </td>
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
	            <tr>
              		<th colspan="3"><strong>Visita Post Mortem</strong></th>
	            </tr>
	            <tr class="containerBody">
	              <td class="formLabel" >Data Macellazione</td>
	              <td colspan="2">
<%--	              		<zeroio:dateSelect timestamp="<%=new Timestamp( System.currentTimeMillis() ) %>" field="vpm_data" form="main" showTimeZone="false" />  --%>
	              		<input readonly type="text" id="vpm_data" name="vpm_data" size="10" value="<%=Partita.getVpm_data() != null ? DateUtils.timestamp2string(Partita.getVpm_data()) : DateUtils.timestamp2string(Partita.getCd_data_arrivo_macello()) %>" />&nbsp;
			        	<a href="#" onClick="cal19.select(document.forms[0].vpm_data,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
			 			<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
			 			<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].vpm_data);"><img src="images/delete.gif" align="absmiddle"/></a>
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
                	<td class="formLabel" colspan="3" align="left"><strong>Destinatari/Esercenti</strong><input onclick="javascript:gestioneAddDest();" type="button" name="addDest" id="addDest" value="Aggiungi" /> </td>
            	</tr>
            	<tr>
            		<td colspan="3" class="formLabel">
            			<i>Il numero capi non &egrave; selezionabile se tutte le carni e visceri degli animali macellati vanno al libero consumo, senza distruzione di organi</i>
            		</td>
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
							        	<%=(update && !Partita.isDestinatario_1_in_regione()) ? ("") : ("checked=\"checked\"") %> /> 
							        	
						        No <input 
						        		type="radio" 
						        		name="destinatario_1_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(1)" 
						        		id="outRegione_1"
						        		<%=(update && !Partita.isDestinatario_1_in_regione()) ? ("checked=\"checked\"") : ("") %> />
					        </td>
					        <td> 
						        Si <input 
						        		type="radio" 
						        		name="destinatario_2_in_regione" 
						        		value="si" 
						        		onclick="selectDestinazione(2)" 
						        		id="inRegione_2" 
						        		<%=(update && !Partita.isDestinatario_2_in_regione()) ? ("") : ("checked=\"checked\"") %> /> 
						        		
						        No <input 
						        		type="radio" 
						        		name="destinatario_2_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(2)" 
						        		id="outRegione_2"
						        		<%=(update && !Partita.isDestinatario_2_in_regione()) ? ("checked=\"checked\"") : ("") %> />
					        </td>
					    </tr>
					    <tr class="containerBody">
			            	<td  class="formLabel">Destinatari/Esercenti</td>
					        <td>
						        <div 
						        	style="<%=(update && !Partita.isDestinatario_1_in_regione()) ? ("display:none") : ("") %>" 
						        	id="imprese_1">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 1, 'impresa' );" onclick="selectDestinazione(1);gestioneObbligNumCapiDestCarni(1,1);" >[Seleziona Impresa] </a><br/>
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 3, 'stab');" onclick="selectDestinazione(1);gestioneObbligNumCapiDestCarni(1,2);" >[Seleziona Stabilimento]  </a><br/>
						        	<a href="javascript:mostraTextareaEsercente('esercenteNoGisa1');" onclick="selectDestinazioneFromLinkTextarea(1);gestioneObbligNumCapiDestCarni(1,3);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						        	<a href="javascript:impostaDestinatarioMacelloCorrente(1);" onclick="gestioneObbligNumCapiDestCarni(1,4);" >[Macello corrente]</a>
						        	<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa1" name="esercenteNoGisa1" onchange="valorizzaDestinatario(this,'destinatario_1');" ></textarea>
						        </div>
						        <div  
						        	style="<%=(update && !Partita.isDestinatario_1_in_regione()) ? ("") : ("display:none") %>" 
						        	id="esercenti_1">
						       		<!--  <a href = "javascript:popLookupSelectorDestinazioneCarni( 'no', 1 );" >[Seleziona Destinatario/Esercente]</a> -->
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione1');" onclick="selectDestinazioneFromLinkTextarea(1);gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(1,0);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione1" name="esercenteFuoriRegione1" onchange="valorizzaDestinatario(this,'destinatario_1');" ></textarea>
						        </div>
						        <br/>
						        <div id ="destinatario_label_1" align="center">
						        	-- Seleziona Destinatario/Esercente --
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_1_id" 
					        		id="destinatario_1_id" 
					        		value="" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_1_nome" 
					        		id="destinatario_1_nome" 
					        		onchange=""
					        		value="" />
					        		<p id="destinatarioCarni1" align="center" style="display: none;"><font color="red" >*</font></p>
							</td>
						    <td>
						    	<div style="" id="imprese_2">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 2, 'impresa' );" onclick="selectDestinazione(2);gestioneObbligNumCapiDestCarni(2,1);" >[Seleziona Impresa]</a><br/>
						      		<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 4, 'stab');" onclick="selectDestinazione(2);gestioneObbligNumCapiDestCarni(2,2);" >[Seleziona Stabilimento]</a><br/>
						      		<a href="javascript:mostraTextareaEsercente('esercenteNoGisa2');" onclick="selectDestinazioneFromLinkTextarea(2);gestioneObbligNumCapiDestCarni(2,3);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						      		<a href="javascript:impostaDestinatarioMacelloCorrente(2);" onclick="gestioneObbligNumCapiDestCarni(2,4);" >[Macello corrente]</a>
						      		<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa2" name="esercenteNoGisa2" onchange="valorizzaDestinatario(this,'destinatario_2');" ></textarea>

						        </div>
						        <div  style="display:none" id="esercenti_2">
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione2');" onclick="selectDestinazioneFromLinkTextarea(2);gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(2,0);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione2" name="esercenteFuoriRegione2" onchange="valorizzaDestinatario(this,'destinatario_2');" ></textarea>
						        </div>
						        <br/>
					        	<div id ="destinatario_label_2" align="center">
						        	-- Seleziona Destinatario/Esercente --	
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_2_id" 
					        		id="destinatario_2_id" 
					        		value="" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_2_nome" 
					        		id="destinatario_2_nome" 
					        		onchange=""
					        		value="" />
					        		<p id="destinatarioCarni2" align="center" style="display: none;"><font color="red" >*</font></p>
						    </td >
						</tr>
						<tr class="containerBody">
			            	<td  class="formLabel">Numero Capi <label class="specie1">Ovini</label></td>
					        <td> 
						        <input disabled="disabled" onchange="isIntPositivo(this.value,'Numero Capi',this);" type="text" name="destinatario_1_num_capi_ovini" value="" id="num_capi_ovini_1" /> 
					        	<font style="display:none;" id="num_capi_ovini_asterisco_1" color="red" >*</font>
					        </td>
					        <td> 
						        <input disabled="disabled"  onchange="isIntPositivo(this.value,'Numero Capi',this);" type="text" name="destinatario_2_num_capi_ovini" value="" id="num_capi_ovini_2" /> 
						    	<font style="display:none;" id="num_capi_ovini_asterisco_2" color="red" >*</font>
						    </td>
					    </tr>
					    <tr class="containerBody">
			            	<td  class="formLabel">Numero Capi <label class="specie2">Caprini</label></td>
					        <td> 
						        <input disabled="disabled" onchange="isIntPositivo(this.value,'Numero Capi',this);" type="text" name="destinatario_1_num_capi_caprini" value="" id="num_capi_caprini_1" /> 
					        	<font style="display:none;" id="num_capi_caprini_asterisco_1" color="red" >*</font>
					        </td>
					        <td> 
						        <input disabled="disabled" onchange="isIntPositivo(this.value,'Numero Capi',this);" type="text" name="destinatario_2_num_capi_caprini" value="" id="num_capi_caprini_2" /> 
						    	<font style="display:none;" id="num_capi_caprini_asterisco_2" color="red" >*</font>
						    </td >
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
							        	<%=(update && !Partita.isDestinatario_3_in_regione()) ? ("") : ("checked=\"checked\"") %> /> 
							        	
						        No <input 
						        		type="radio" 
						        		name="destinatario_3_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(3)" 
						        		id="outRegione_3"
						        		<%=(update && !Partita.isDestinatario_3_in_regione()) ? ("checked=\"checked\"") : ("") %> />
					        </td>
					        <td> 
						        Si <input 
						        		type="radio" 
						        		name="destinatario_4_in_regione" 
						        		value="si" 
						        		onclick="selectDestinazione(4)" 
						        		id="inRegione_4" 
						        		<%=(update && !Partita.isDestinatario_4_in_regione()) ? ("") : ("checked=\"checked\"") %> /> 
						        		
						        No <input 
						        		type="radio" 
						        		name="destinatario_4_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(4)" 
						        		id="outRegione_4"
						        		<%=(update && !Partita.isDestinatario_4_in_regione()) ? ("checked=\"checked\"") : ("") %> />
					        </td>
					    </tr>
					    <tr class="containerBody">
			            	<td  class="formLabel">Destinatario delle Carni </td>
					        <td>
						        <div 
						        	style="<%=(update && !Partita.isDestinatario_3_in_regione()) ? ("display:none") : ("") %>" 
						        	id="imprese_3">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 5, 'impresa' );" onclick="selectDestinazione(3);gestioneObbligNumCapiDestCarni(3,1);" >[Seleziona Impresa] </a><br/>
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 5, 'stab');" onclick="selectDestinazione(3);gestioneObbligNumCapiDestCarni(3,2);" >[Seleziona Stabilimento]  </a><br/>
						        	<a href="javascript:mostraTextareaEsercente('esercenteNoGisa3');" onclick="selectDestinazioneFromLinkTextarea(3);gestioneObbligNumCapiDestCarni(3,3);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						        	<a href="javascript:impostaDestinatarioMacelloCorrente(3);" onclick="gestioneObbligNumCapiDestCarni(3,4);" >[Macello corrente]</a>
						        	<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa3" name="esercenteNoGisa3" onchange="valorizzaDestinatario(this,'destinatario_3');" ></textarea>
						        </div>
						        <div  
						        	style="<%=(update && !Partita.isDestinatario_3_in_regione()) ? ("") : ("display:none") %>" 
						        	id="esercenti_3">
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione3');" onclick="selectDestinazioneFromLinkTextarea(3);gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(3,0);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione3" name="esercenteFuoriRegione3" onchange="valorizzaDestinatario(this,'destinatario_3');" ></textarea>
						        </div>
						        <br/>
						        <div id ="destinatario_label_3" align="center">
						        	-- Seleziona Destinatario/Esercente --
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_3_id" 
					        		id="destinatario_3_id" 
					        		value="" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_3_nome" 
					        		id="destinatario_3_nome" 
					        		onchange=""
					        		value="" />
					        		<p id="destinatarioCarni3" align="center" style="display: none;"><font color="red" >*</font></p>
							</td>
							
							
						    <td>
						    	<div style="" id="imprese_4">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 6, 'impresa' );" onclick="selectDestinazione(4);gestioneObbligNumCapiDestCarni(4,1);" >[Seleziona Impresa]</a><br/>
						      		<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', 6, 'stab');" onclick="selectDestinazione(4);gestioneObbligNumCapiDestCarni(4,2);" >[Seleziona Stabilimento]</a><br/>
						      		<a href="javascript:mostraTextareaEsercente('esercenteNoGisa4');" onclick="selectDestinazioneFromLinkTextarea(4);gestioneObbligNumCapiDestCarni(4,3);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						      		<a href="javascript:impostaDestinatarioMacelloCorrente(4);" onclick="gestioneObbligNumCapiDestCarni(4,4);" >[Macello corrente]</a>
						      		<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa4" name="esercenteNoGisa4" onchange="valorizzaDestinatario(this,'destinatario_4');" ></textarea>

						        </div>
						        <div  style="display:none" id="esercenti_4">
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione4');" onclick="selectDestinazioneFromLinkTextarea(4);gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(1,0);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione4" name="esercenteFuoriRegione4" onchange="valorizzaDestinatario(this,'destinatario_4');" ></textarea>
						        </div>
						        <br/>
					        	<div id ="destinatario_label_4" align="center">
						        	-- Seleziona Destinatario/Esercente --	
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_4_id" 
					        		id="destinatario_4_id" 
					        		value="" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_4_nome" 
					        		id="destinatario_4_nome" 
					        		onchange=""
					        		value="" />
					        		<p id="destinatarioCarni4" align="center" style="display: none;"><font color="red" >*</font></p>
						    </td>
						</tr>
						<tr class="containerBody">
			            	<td  class="formLabel">Numero Capi <label class="specie1">Ovini</label></td>
					        <td> 
						        <input disabled="disabled" onchange="isIntPositivo(this.value,'Numero Capi',this);" type="text" name="destinatario_3_num_capi_ovini" value="" id="num_capi_ovini_3" /> 
					        	<font style="display:none;" id="num_capi_ovini_asterisco_3" color="red" >*</font>
					        </td>
					        <td> 
						        <input disabled="disabled" onchange="isIntPositivo(this.value,'Numero Capi',this);" type="text" name="destinatario_4_num_capi_ovini" value="" id="num_capi_ovini_4" /> 
						    	<font style="display:none;" id="num_capi_ovini_asterisco_4" color="red" >*</font>
						    </td>
					    </tr>
					    <tr class="containerBody">
			            	<td  class="formLabel">Numero Capi <label class="specie2">Caprini</label></td>
					        <td> 
						        <input disabled="disabled" onchange="isIntPositivo(this.value,'Numero Capi',this);" type="text" name="destinatario_3_num_capi_caprini" value="" id="num_capi_caprini_3" /> 
					        	<font style="display:none;" id="num_capi_caprini_asterisco_3" color="red" >*</font>
					        </td>
					        <td> 
						        <input disabled="disabled" onchange="isIntPositivo(this.value,'Numero Capi',this);" type="text" name="destinatario_4_num_capi_caprini" value="" id="num_capi_caprini_4" /> 
						    	<font style="display:none;" id="num_capi_caprini_asterisco_4" color="red" >*</font>
						    </td>
					    </tr >
						
						<%
						//Disabilitata l'aggiunta di altri destinatari
						for(int i=5;i<=20;i+=2)
						{
							int j=i+1;
				        	Boolean isDestinatarioInRegione = (Boolean)ReflectionUtil.invocaMetodo(Partita, "isDestinatario_"+i+"_in_regione", null, null);
				        	Boolean isDestinatarioInRegione2 = (Boolean)ReflectionUtil.invocaMetodo(Partita, "isDestinatario_"+j+"_in_regione", null, null);
				        	String destinatarioNome  = (String)ReflectionUtil.invocaMetodo(Partita, "getDestinatario_"+i+"_nome", null, null);
				        	String destinatarioNome2 = (String)ReflectionUtil.invocaMetodo(Partita, "getDestinatario_"+j+"_nome", null, null);
				        	Integer destinatarioId  = (Integer)ReflectionUtil.invocaMetodo(Partita, "getDestinatario_"+i+"_id", null, null);
				        	Integer destinatarioId2 = (Integer)ReflectionUtil.invocaMetodo(Partita, "getDestinatario_"+j+"_id", null, null);
				        	String destinatarioNumCapiOvini  = ((Integer)ReflectionUtil.invocaMetodo(Partita, "getDestinatario_"+i+"_num_capi_ovini", null, null))+"";
				        	String destinatarioNumCapiOvini2  = ((Integer)ReflectionUtil.invocaMetodo(Partita, "getDestinatario_"+j+"_num_capi_ovini", null, null))+"";
				        	String destinatarioNumCapiCaprini  = ((Integer)ReflectionUtil.invocaMetodo(Partita, "getDestinatario_"+i+"_num_capi_caprini", null, null))+"";
				        	String destinatarioNumCapiCaprini2  = ((Integer)ReflectionUtil.invocaMetodo(Partita, "getDestinatario_"+j+"_num_capi_caprini", null, null))+"";
				        	
				        	//if(destinatarioNumCapiOvini2.equals("0") || destinatarioNumCapiOvini2.equals("-1"))
				        		destinatarioNumCapiOvini2="";
				        	//if(destinatarioNumCapiOvini.equals("0") || destinatarioNumCapiOvini.equals("-1"))
				        		destinatarioNumCapiOvini="";
				        	//if(destinatarioNumCapiCaprini2.equals("0") || destinatarioNumCapiCaprini2.equals("-1"))
				        		destinatarioNumCapiCaprini2="";
				        	//if(destinatarioNumCapiCaprini.equals("0") || destinatarioNumCapiCaprini.equals("-1"))
				        		destinatarioNumCapiCaprini="";
%>						
				<tr>
			            <tr class="containerBody">
			            	<td style="display:none;" id="rigaDestTd1<%=(i+1)/2 %>" class="formLabel">In Regione </td>
					        <td style="display:none;" id="rigaDestTd2<%=(i+1)/2 %>" > 
						        Si <input 
							        	type="radio" 
							        	name="destinatario_<%=i%>_in_regione" 
							        	value="si" 
							        	onclick="selectDestinazione(<%=i%>)" 
							        	id="inRegione_<%=i%>" 
							        	<%=(update &&  !isDestinatarioInRegione) ? ("") : ("checked=\"checked\"") %> /> 
							        	
						        No <input 
						        		type="radio" 
						        		name="destinatario_<%=i%>_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(<%=i%>)" 
						        		id="outRegione_<%=i%>"
						        		<%=(update && !isDestinatarioInRegione) ? ("checked=\"checked\"") : ("") %> />
					        </td>
					        <td style="display:none;" id="rigaDestTd3<%=(i+1)/2 %>" > 
						        Si <input 
						        		type="radio" 
						        		name="destinatario_<%=j%>_in_regione" 
						        		value="si"
						        		onclick="selectDestinazione(<%=j%>)" 
						        		id="inRegione_<%=j%>" 
						        		<%=(update && !isDestinatarioInRegione2) ? ("") : ("checked=\"checked\"") %> /> 
						        		
						        No <input 
						        		type="radio" 
						        		name="destinatario_<%=j%>_in_regione" 
						        		value="no" 
						        		onclick="selectDestinazione(<%=j%>)" 
						        		id="outRegione_<%=j%>"
						        		<%=(update && !isDestinatarioInRegione2) ? ("checked=\"checked\"") : ("") %> />
					        </td>
					    </tr>
					    <tr class="containerBody">
			            	<td  style="display:none;" id="rigaDestTd4<%=(i+1)/2 %>" class="formLabel">Destinatari/Esercenti</td>
					        <td style="display:none;" id="rigaDestTd5<%=(i+1)/2 %>" >
						        <div 
						        	style="<%=(update && !isDestinatarioInRegione) ? ("display:none") : ("") %>" 
						        	id="imprese_<%=i%>">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', <%=i+2%>, 'impresa' );" onclick="selectDestinazione(<%=i%>);gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(<%=i%>,1);" >[Seleziona Impresa] </a><br/>
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', <%=i+2%>, 'stab');" onclick="selectDestinazione(<%=i%>);gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(<%=i%>,2);" >[Seleziona Stabilimento]  </a><br/>
						        	<a href="javascript:mostraTextareaEsercente('esercenteNoGisa<%=i%>');" onclick="selectDestinazioneFromLinkTextarea(<%=i%>);gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(<%=i%>,3);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						        	<a href="javascript:impostaDestinatarioMacelloCorrente(<%=i%>);" onclick="gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(<%=i%>,4);" >[Macello corrente]</a>
						        	<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa<%=i%>" name="esercenteNoGisa<%=i%>" onchange="valorizzaDestinatario(this,'destinatario_<%=i%>');" ><%=toHtmlValue( destinatarioNome ) %></textarea>
						        </div>
						        <div  
						        	style="<%=(update && !isDestinatarioInRegione) ? ("") : ("display:none") %>" 
						        	id="esercenti_<%=i%>">
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione<%=i%>');" onclick="selectDestinazioneFromLinkTextarea(<%=i%>);gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(<%=i%>,0);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione<%=i%>" name="esercenteFuoriRegione<%=i%>" onchange="valorizzaDestinatario(this,'destinatario_<%=i%>');" ><%=toHtmlValue( destinatarioNome ) %></textarea>
						        </div>
						        <br/>
						        <div id ="destinatario_label_<%=i%>" align="center">
						        	<%=( destinatarioId != -1) ? (toHtmlValue( destinatarioNome )) : ("-- Seleziona Destinatario/Esercente --") %>
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_<%=i%>_id" 
					        		id="destinatario_<%=i%>_id" 
					        		value="<%=(destinatarioId != -1) ? (destinatarioId) : ("-1") %>" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_<%=i%>_nome" 
					        		id="destinatario_<%=i%>_nome" 
					        		onchange="gestisciObbligatorietaVisitaPostMortem();"
					        		value="<%=toHtmlValue( destinatarioNome ) %>" />
					        		<p id="destinatarioCarni3" align="center" style="display: none;"><font color="red" >*</font></p>
							</td>
							
							
						    <td style="display:none;" id="rigaDestTd6<%=(i+1)/2 %>" >
						    	<div style="" id="imprese_<%=j%>">
						        	<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', <%=j+2%>, 'impresa' );" onclick="selectDestinazione(<%=j%>);gestioneObbligNumCapiDestCarni(<%=j%>,1);" >[Seleziona Impresa]</a><br/>
						      		<a href = "javascript:popLookupSelectorDestinazioneCarni( 'si', <%=j+2%>, 'stab');" onclick="selectDestinazione(<%=j%>);gestioneObbligNumCapiDestCarni(<%=j%>,2);" >[Seleziona Stabilimento]</a><br/>
						      		<a href="javascript:mostraTextareaEsercente('esercenteNoGisa<%=j%>');" onclick="selectDestinazioneFromLinkTextarea(<%=j%>);gestioneObbligNumCapiDestCarni(<%=j%>,3);" >[Inserisci Esercente non in G.I.S.A.]</a><br/>
						      		<a href="javascript:impostaDestinatarioMacelloCorrente(<%=j%>);" onclick="gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(<%=j%>,4);" >[Macello corrente]</a>
						      		<textarea style="display: none;" rows="3" cols="36" id="esercenteNoGisa<%=j%>" name="esercenteNoGisa<%=j%>" onchange="valorizzaDestinatario(this,'destinatario_<%=j%>');" ><%=toHtmlValue( destinatarioNome2 ) %></textarea>

						        </div>
						        <div  style="display:none" id="esercenti_<%=j%>">
						       		<a href="javascript:mostraTextareaEsercente('esercenteFuoriRegione<%=j%>');" onclick="selectDestinazioneFromLinkTextarea(<%=j%>);gestisciObbligatorietaVisitaPostMortem();gestioneObbligNumCapiDestCarni(<%=j%>,0);" >[Inserisci Esercente fuori Regione]</a>
						       		<textarea style="display: none;" rows="3" cols="36" id="esercenteFuoriRegione<%=j%>" name="esercenteFuoriRegione<%=j%>" onchange="valorizzaDestinatario(this,'destinatario_<%=j%>');" ><%=toHtmlValue( destinatarioNome2 ) %></textarea>
						        </div>
						        <br/>
					        	<div id ="destinatario_label_<%=j%>" align="center">
						        	<%=(destinatarioId2 != -1) ? (toHtmlValue( destinatarioNome2 )) : ("-- Seleziona Destinatario/Esercente --") %>	
						        </div>
					        	<input 
					        		type="hidden" 
					        		name="destinatario_<%=j%>_id" 
					        		id="destinatario_<%=j%>_id" 
					        		value="<%=(destinatarioId2 != -1) ? (destinatarioId2) : ("-1") %>" />
					        	<input 
					        		type="hidden" 
					        		name="destinatario_<%=j%>_nome" 
					        		id="destinatario_<%=j%>_nome" 
					        		onchange="gestisciObbligatorietaVisitaPostMortem();"
					        		value="<%=toHtmlValue( destinatarioNome2 ) %>" />
					        		<p id="destinatarioCarni<%=j%>" align="center" style="display: none;"><font color="red" >*</font></p>
						    </td>
						</tr>
						<tr class="containerBody">
			            	<td class="formLabel" style="display:none;" id="rigaDestTd7<%=(i+1)/2 %>" >Numero Capi <label class="specie1">Ovini</label></td>
					        <td style="display:none;" id="rigaDestTd8<%=(i+1)/2 %>"> 
						        <input <%if(Partita.getCd_num_capi_ovini()<=0){out.println("disabled=\"disabled\"");}%> onchange="isIntPositivo(this.value,'Numero Capi',this);" type="text" name="destinatario_<%=i%>_num_capi_ovini" value="<%=destinatarioNumCapiOvini%>" id="num_capi_ovini_<%=i%>" /> 
					        	<font style="display:none;" id="num_capi_ovini_asterisco_<%=i%>" color="red" >*</font>
					        </td>
					        <td style="display:none;" id="rigaDestTd9<%=(i+1)/2 %>" > 
						        <input <%if(Partita.getCd_num_capi_ovini()<=0){out.println("disabled=\"disabled\"");}%> onchange="isIntPositivo(this.value,'Numero Capi',this);" type="text" name="destinatario_<%=j%>_num_capi_ovini" value="<%=destinatarioNumCapiOvini2%>" id="num_capi_ovini_<%=j%>" /> 
						    	<font style="display:none;" id="num_capi_ovini_asterisco_<%=j%>" color="red" >*</font>
						    </td>
					    </tr>
					    
					    <tr class="containerBody">
			            	<td class="formLabel" style="display:none;" id="rigaDestTd10<%=(i+1)/2 %>" >Numero Capi <label class="specie2">Caprini</label></td>
					        <td style="display:none;" id="rigaDestTd11<%=(i+1)/2 %>"> 
						        <input disabled="disabled" onchange="isIntPositivo(this.value,'Numero Capi',this);" type="text" name="destinatario_<%=i%>_num_capi_caprini" value="<%=destinatarioNumCapiCaprini%>" id="num_capi_caprini_<%=i%>" /> 
					        	<font style="display:none;" id="num_capi_caprini_asterisco_<%=i%>" color="red" >*</font>
					        </td>
					        <td style="display:none;" id="rigaDestTd12<%=(i+1)/2 %>" > 
						        <input disabled="disabled" onchange="isIntPositivo(this.value,'Numero Capi',this);" type="text" name="destinatario_<%=j%>_num_capi_caprini" value="<%=destinatarioNumCapiCaprini2%>" id="num_capi_caprini_<%=j%>" /> 
						    	<font style="display:none;" id="num_capi_caprini_asterisco_<%=j%>" color="red" >*</font>
						    </td>
					    </tr>
						
<%
						}
%>
					
					
	            
	            <tr>
                	<td class="formLabel" colspan="3" align="left"><strong>Veterinari addetti al controllo</strong></td>
            	</tr>
            	
	            <tr class="containerBody">
	            	<td colspan="3">
			            <table>
			            	<tr class="containerBody">
			                	<td>1. <input 
		                			value=""
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
											<option value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
										<%} %>
									<%} %>
							</select>
							</td>
							<td style="background: none; border-right: none;">
		                		<font color="red">*</font>
		                	</td>
			                </tr>
				            <tr class="containerBody" id="vpm_veterinario_toggle" style="display: <%= Partita.getVpm_veterinario()!=null && Partita.getVpm_veterinario().equals("") ? "none" : "" %>" >
				                <td>2. <input 
		                			value=""
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
											<option value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
										<%} %>
									<%} %>
							</select>
			                </td>
				            </tr>
				            <tr class="containerBody" id="vpm_veterinario_2_toggle" style="display: <%= Partita.getVpm_veterinario_2()!=null && Partita.getVpm_veterinario_2().equals("") ? "none" : "" %>" >
				                <td>3. <input 
		                			value=""
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
											<option value = "<%=vet.getUserId()%>" ><%=vet.getNameLast() %></option>
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
    		<%@include file="include_campioni_add_seduta.jsp" %>
<%
ConfigTipo configTipo3 = (ConfigTipo)request.getSession().getAttribute("configTipo");
String fileToInclude3 = "include_tamponi_add_seduta" + configTipo3.getIdTipo() + ".jsp";
%>

<jsp:include page="<%=fileToInclude3%>"/>
		</td>
	</tr>
	
</table>

<input type="submit" value="Completa Macellazione" />
<input type="button" value="Completa Macellazione e Prosegui" onclick="document.main.clone.value='ok';if(controllaForm()){document.main.submit();}" />

</div><!-- End demo -->

</form>

<script type="text/javascript">
function gestioneObbligNumCapiDestCarni(indice,tipoSelezione)
{
	if(tipoSelezione>2 || tipoSelezione==0)
	{
		if(document.getElementById( 'cd_num_capi_ovini').value!="" && document.getElementById( 'cd_num_capi_ovini').value!="0")
		{
			document.getElementById( 'num_capi_ovini_' + indice ).disabled="";
			if(document.getElementById( 'num_capi_ovini_asterisco_' + indice )!=null)
				document.getElementById( 'num_capi_ovini_asterisco_' + indice ).style.display = "block";
		}
		if(document.getElementById( 'cd_num_capi_caprini').value!="" && document.getElementById( 'cd_num_capi_caprini').value!="0")
		{
			document.getElementById( 'num_capi_caprini_' + indice ).disabled="";
			if(document.getElementById( 'num_capi_caprini_asterisco_' + indice )!=null)
				document.getElementById( 'num_capi_caprini_asterisco_' + indice ).style.display = "block";
		}
		if(destinatariSelezionati.indexOf(indice)==-1)
			destinatariSelezionati[destinatariSelezionati.length]=indice;
	}
	else
	{
		var displayOvini = null;
		var displayCaprini = null;
		if(document.getElementById( 'num_capi_ovini_asterisco_' + indice )!=null)
			displayOvini = document.getElementById( 'num_capi_ovini_asterisco_' + indice ).style.display;
		if(document.getElementById( 'num_capi_caprini_asterisco_' + indice )!=null)
			displayCaprini = document.getElementById( 'num_capi_caprini_asterisco_' + indice ).style.display;
		
		if(document.getElementById( 'num_capi_ovini_asterisco_' + indice )!=null)
		{
			if(displayOvini=="block" || displayOvini=="")
		    {
		    	document.getElementById( 'num_capi_ovini_asterisco_' + indice ).style.display = "none";
		    	document.getElementById( 'num_capi_ovini_' + indice ).disabled="disabled";
		    	document.getElementById( 'num_capi_ovini_' + indice ).value="";
		    	var index = destinatariSelezionati.indexOf(indice);
		    	if (index > -1) 
		    	{
		    		destinatariSelezionati.splice(index, 1);
		    	}
		    }
		}
		if(document.getElementById( 'num_capi_caprini_asterisco_' + indice )!=null)
		{
			if(displayCaprini=="block" || displayCaprini=="")
		    {
		    	document.getElementById( 'num_capi_caprini_asterisco_' + indice ).style.display = "none";
		    	document.getElementById( 'num_capi_caprini_' + indice ).disabled="disabled";
		    	document.getElementById( 'num_capi_caprini_' + indice ).value="";
		    	var index = destinatariSelezionati.indexOf(indice);
		    	if (index > -1) 
		    	{
		    		destinatariSelezionati.splice(index, 1);
		    	}
		    }
		}
	}
}

function gestioneObbligNumCapiDestCarniPostSelezione( indiceDestinatario, altId, ragioneSociale, tipo, hasNumCapi)
{
	var destinatariSelezionatiIncrementato=false;
	if(hasNumCapi)
	{
		var displayOvini = null;
		var displayCaprini = null;
		if(document.getElementById( 'num_capi_ovini_asterisco_' + indiceDestinatario )!=null)
		{
			displayOvini = document.getElementById( 'num_capi_ovini_asterisco_' + indiceDestinatario ).style.display;
			if(displayOvini=="block" || displayOvini=="")
		    {
		    	document.getElementById( 'num_capi_ovini_asterisco_' + indiceDestinatario ).style.display = "disabled";
		    	var index = destinatariSelezionati.indexOf(indiceDestinatario);
		    	if (index > -1) 
		    	{
		    		destinatariSelezionati.splice(index, 1);
		    	}
		    }
		    else
		    {
		    	if(document.getElementById('cd_num_capi_ovini').value!="" && document.getElementById('cd_num_capi_ovini').value!="0")
		    	{
		    		document.getElementById( 'num_capi_ovini_asterisco_' + indiceDestinatario ).style.display = "";
		    	}
		    	if(destinatariSelezionati.indexOf(indiceDestinatario)==-1)
					destinatariSelezionati[destinatariSelezionati.length]=indiceDestinatario;
	    		destinatariSelezionatiIncrementato=true;
		    		
		    }
		}
		if(document.getElementById( 'num_capi_caprini_asterisco_' + indiceDestinatario )!=null)
		{
			displayCaprini = document.getElementById( 'num_capi_caprini_asterisco_' + indiceDestinatario ).style.display;
			if(displayCaprini=="block" || displayCaprini=="")
		    {
		    	document.getElementById( 'num_capi_caprini_asterisco_' + indiceDestinatario ).style.display = "disabled";
		    	var index = destinatariSelezionati.indexOf(indiceDestinatario);
		    	if (index > -1) 
		    	{
		    		destinatariSelezionati.splice(index, 1);
		    	}
		    }
		    else
		    {
		    	if(document.getElementById('cd_num_capi_caprini').value!="" && document.getElementById('cd_num_capi_caprini').value!="0")
		    		document.getElementById( 'num_capi_caprini_asterisco_' + indiceDestinatario ).style.display = "";
		    	if(!destinatariSelezionatiIncrementato && destinatariSelezionati.indexOf(indiceDestinatario)==-1)
		    		destinatariSelezionati[destinatariSelezionati.length]=indiceDestinatario;
		    }
		}
    
    
	}
	if(ragioneSociale!="")
	{
  		if(document.getElementById('cd_num_capi_ovini').value!="" && document.getElementById('cd_num_capi_ovini').value!="0")
  			document.getElementById( 'num_capi_ovini_' + indiceDestinatario ).disabled = "";
  		if(document.getElementById('cd_num_capi_caprini').value!="" && document.getElementById('cd_num_capi_caprini').value!="0")
  			document.getElementById( 'num_capi_caprini_' + indiceDestinatario  ).disabled = "";
	}
	else
	{
		if(document.getElementById('cd_num_capi_ovini').value!="" && document.getElementById('cd_num_capi_ovini').value!="0")
		{
  			document.getElementById( 'num_capi_ovini_' + indiceDestinatario ).disabled = "none";
  			document.getElementById( 'num_capi_ovini_' + indiceDestinatario  ).value = "";
		}
  			if(document.getElementById('cd_num_capi_caprini').value!="" && document.getElementById('cd_num_capi_caprini').value!="0")
  		{
  			document.getElementById( 'num_capi_caprini_' + indiceDestinatario  ).disabled = "none";
  			document.getElementById( 'num_capi_caprini_' + indiceDestinatario  ).value = "";
  		}
	}
}

function gestioneAddDest()
{
	if(righeDest==10)
	{
		alert("Hai raggiunto il limite di destinatari selezionabili.");
		return false;
	}
	else
	{
		righeDest++;
		var i=1;
		while(i<=12)
		{
			document.getElementById('rigaDestTd'+i+righeDest).style.display="";
			i++;
		}
		
	}
}

function isIntPositivo(Expression,nomeCampo,campo)
{
    Expression = Expression.toLowerCase();
    RefString = "0123456789";
    
    if (Expression.length < 1) 
        return (false);
    
    for (var i = 0; i < Expression.length; i++) 
    {
        var ch = Expression.substr(i, 1);
        var a = RefString.indexOf(ch, 0);
        if (a < 0)
        {
            alert('Inserire un intero positivo nel campo ' + nomeCampo +'.');
            campo.value='';
            campo.focus();
        	return false;
        }
    }
    return(true);
}

for(var i=3;i<=righeMax;i++)
{
	gestioneAddDest();
}

</script>

<% if (Partita.isSpecie_suina()) { %>
<script>gestisciLabelPartite('2')</script>
<%}%>

</dhv:container>
</body>
