<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page
	import="ext.aspcfs.modules.apicolture.actions.StabilimentoAction"%>
<%@page import="org.aspcfs.modules.system.base.SiteList"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,ext.aspcfs.modules.apiari.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>

<link rel="stylesheet" type="text/css" href="css/jmesa.css"></link>
<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
<script type="text/javascript" src="javascript/jmesa.js"></script>

<%@page import="java.sql.Timestamp"%>
<jsp:useBean id="StabilimentoDettaglio"
	class="ext.aspcfs.modules.apiari.base.Stabilimento" scope="request" />
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" /><jsp:useBean id="Operatore"
	class="ext.aspcfs.modules.apiari.base.Operatore" scope="request" />

<jsp:useBean id="TipoAttivitaApi"
	class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="LookupStati" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<jsp:useBean id="ApicolturaSottospecie"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ApicolturaModalita"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ApicolturaClassificazione"
	class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="noteEsitoKO" class="java.lang.String" scope="request" />

<jsp:useBean id="msgAggiornamento" class="java.lang.String" scope="request" />


<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>


<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/opu.css" />

<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/gestoreCodiceFiscale.js"></script>
<SCRIPT src="javascript/apiari.js"></SCRIPT>

<link rel="stylesheet" type="text/css"
	href="javascript/jquerypluginTableSorter/css/theme.css"></link>
<link rel="stylesheet" type="text/css"
	href="javascript/jquerypluginTableSorter/css/jquery.tablesorter.pages.css"></link>

<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/jquery.tablesorter.js"></script>
<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/jquery.tablesorter.pager.js"></script>

<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/jquery.tablesorter.widgets.js"></script>
<script type="text/javascript"
	src="javascript/jquerypluginTableSorter/tableJqueryFilterApi.js"></script>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
<%@ include file="../initPage.jsp"%>



<%!public static String fixNoteEsito(String note) {
		String toRet = "";
		if (note == null || note.equals(""))
			return toRet;

		if (note.contains("<errori>") && note.contains("</errori>")) {
			toRet = note.substring(note.indexOf("<errori>"), note.indexOf("</errori>"));
			toRet = toRet.replaceAll("</errore>", "<br/>");
		} else {
			toRet = note;
		}
		return toRet;

	}%>


<% if (msgAggiornamento!=null && !msgAggiornamento.equals("")){ %>
<script>
alert("<%=msgAggiornamento%>");
</script>
<%} %>

<table class="trails" cellspacing="0">
	<tr>
		<td>APICOLTURA -> <%=(application.getAttribute("SUFFISSO_TAB_ACCESSI")!=null && !"_ext".equalsIgnoreCase((String)application.getAttribute("SUFFISSO_TAB_ACCESSI"))) ? "<a href='ApicolturaAttivita.do?command=Search'>RISULTATI RICERCA</a> ->" : "a"+application.getAttribute("SUFFISSO_TAB_ACCESSI")%>

			SCHEDA APIARIO
		</td>
	</tr>
</table>



<%@ include file="../../controlliufficiali/diffida_list.jsp"%>


<script type="text/javascript">
	function checkFormValidazione() {
		form = document.validazione;

		formTest = true;
		message = "";
		alertMessage = "";

		if (form.idApicolturaClassificazione.value == "-1"
				|| form.idApicolturaClassificazione.value == "") {
			message += "- Classificazione richiesto\r\n";
			formTest = false;
		}

		if (form.idApicolturaSottospecie.value == "-1"
				|| form.idApicolturaSottospecie.value == "") {
			message += "- Sottospecie richiesto\r\n";
			formTest = false;
		}

		if (form.idApicolturaModalita.value == "-1"
				|| form.idApicolturaModalita.value == "	") {
			message += "- Modalita richiesto\r\n";
			formTest = false;
		}

		if (form.dataApertura.value == "") {
			message += "- Data Apertura richiesto\r\n";
			formTest = false;
		}

		if (form.numAlveari.value == "") {
			message += "- Num alverai richiesto\r\n";
			formTest = false;
		}
		if (form.numSciami.value == "") {
			message += "- Num Sciami/Nuclei richiesto\r\n";
			formTest = false;
		}

		if (form.comuneSL.value == "" || form.comuneSL.value == "-1") {
			message += "- Comune Sede Legale richiesto\r\n";
			formTest = false;
		}
		if (form.presso.value == "") {
			message += "- Cap Sede Legale richiesto\r\n";
			formTest = false;
		}
		if (form.searchcodeIdprovinciaTesto.value == "-1") {
			message += "- Provincia Sede Legale richiesto\r\n";
			formTest = false;
		}
		if (form.viaTestoSL.value == "") {
			message += "- Indirizzo Sede Legale richiesto\r\n";
			formTest = false;
		}

		if (form.comuneSO.value == "" || form.comuneSO.value == "-1") {
			message += "- Comune ubicazione Apiario richiesto\r\n";
			formTest = false;
		}
		if (form.pressoSO.value == "") {
			message += "- Cap Ubicazione Apiario richiesto\r\n";
			formTest = false;
		}
		if (form.searchcodeIdprovinciaTestoSO.value == "") {
			message += "- Provincia Ubicazione Apiario richiesto\r\n";
			formTest = false;
		}
		if (form.viaTestoSO.value == "") {
			message += "- Indirizzo Ubicazione Apiario richiesto\r\n";
			formTest = false;
		}

		if (form.idTipoAttivita.value == "-1"
				|| form.idTipoAttivita.value == "0") {
			message += "- Tipo Attivita richiesto\r\n";
			formTest = false;
		}
		if (form.domicilioDigitale.value == "") {
			message += "- Domicilio Digitale richiesto\r\n";
			formTest = false;
		}

		if (form.telefono1.value == "") {
			message += "- Telefono Fisso richiesto\r\n";
			formTest = false;
		}

		if (form.latitudine.value == "") {
			message += "- Latitudine richiesto\r\n";
			formTest = false;
		}
		if (form.longitudine.value == "") {
			message += "- Longitudine richiesto\r\n";
			formTest = false;
		}

		if ((form.latitudine.value < 39.988475)
				|| (form.latitudine.value > 41.503754)) {
			message += "- Valore errato per il campo Latitudine, il valore deve essere compreso tra 39.988475 e 41.503754  \r\n";
			formTest = false;
		}
		if ((form.longitudine.value < 13.7563172)
				|| (form.longitudine.value > 15.8032837)) {
			message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837  \r\n";
			formTest = false;
		}

		if (formTest == false) {
			alert(label("check.form",
					"Form could not be saved, please check the following:\r\n\r\n")
					+ message);
			return false;
		} else {

			loadModalWindow();
			return true;
		}

	}
	
	function checkFormRecapiti() {
	
	
	 	formTest = true;
	    message = "";
	    alertMessage = "";
	   
	    form = document.apicoltoreModificaRecapiti ;
	    
	    
	   
	    
	    if (form.domicilioDigitale.value == ""){
	        message += "- Domicilio Digitale(Pec) richiesto\r\n";
	        formTest = false;
	     }
	    if (form.telefono1.value == ""){
	        message += "- Telefono Fisso richiesto\r\n";
	        formTest = false;
	     }
	    if (form.telefono2.value == ""){
	        message += "- Telefono Cellulare richiesto\r\n";
	        formTest = false;
	     }
	    
	    
	    
	    
	    
	    if (formTest == false) {
	        alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
	        return false;
	      } else {
	      
	        loadModalWindow();
	        form.submit();
	        return true;
	      }

	}

	
	var campoLat;
	var campoLong;
	function showCoordinate(address, city, prov, cap, campo_lat, campo_long) {
		campoLat = campo_lat;
		campoLong = campo_long;
		Geocodifica.getCoordinate(address, city, prov, cap, '', '', '',
				setGeocodedLatLonCoordinate);

	}

	function setGeocodedLatLonCoordinate(value) {
		campoLat.value = value[1];
		;
		campoLong.value = value[0];

	}
	$(function() {
		
		$("#dialogModificaModalita").dialog({
			autoOpen : false,
			resizable : false,
			closeOnEscape : false,
			title : "MODIFICA MODILITA ALLEVAMENTO",
			width : 500,
			height : 300,
			position : 'top',
			draggable : false,
			modal : true,
			buttons : {
				"Salva" : function() {
					
					return checkFormModalita();
				},
				"Esci" : function() {
					$(this).dialog("close");
					$("html,body").animate({
						scrollTop : 0
					}, 500, function() {
					});
				}

			},
			show : {
				effect : "blind",
				duration : 1000
			},
			hide : {
				effect : "explode",
				duration : 1000
			}

		}).prev(".ui-dialog-titlebar").css("background", "#bdcfff");
	
		
		
		$("#dialogModificaApiario").dialog({
			autoOpen : false,
			resizable : false,
			closeOnEscape : false,
			title : "MODIFICA DATI APIARIO",
			width : 980,
			height : 700,
			position : 'top',
			draggable : false,
			modal : true,
			buttons : {
				"Salva" : function() {
					
					return checkForm();
				},
				"Esci" : function() {
					$(this).dialog("close");
					$("html,body").animate({
						scrollTop : 0
					}, 500, function() {
					});
				}

			},
			show : {
				effect : "blind",
				duration : 1000
			},
			hide : {
				effect : "explode",
				duration : 1000
			}

		}).prev(".ui-dialog-titlebar").css("background", "#bdcfff");
	
		
		$("#dialogModificaRecapiti").dialog({
			autoOpen : false,
			resizable : false,
			closeOnEscape : false,
			title : "MODIFICA RECAPITI",
			width : 500,
			height : 300,
			position : 'top',
			draggable : false,
			modal : true,
			buttons : {
				"Salva" : function() {
					
					return checkFormRecapiti();
				},
				"Esci" : function() {
					$(this).dialog("close");
					$("html,body").animate({
						scrollTop : 0
					}, 500, function() {
					});
				}

			},
			show : {
				effect : "blind",
				duration : 1000
			},
			hide : {
				effect : "explode",
				duration : 1000
			}

		}).prev(".ui-dialog-titlebar").css("background", "#bdcfff");

		
		$("#dialogModificaApicoltore").dialog({
			autoOpen : false,
			resizable : false,
			closeOnEscape : false,
			title : "MODIFICA DATI APICOLTORE",
			width : 980,
			height : 700,
			position : 'top',
			draggable : false,
			modal : true,
			buttons : {
				"Aggiorna Apicoltore" : function() {
					
					return checkFormApicoltore();
				},
				"Esci " : function() {
					$(this).dialog("close");
					$("html,body").animate({
						scrollTop : 0
					}, 500, function() {
					});
				}

			},
			show : {
				effect : "blind",
				duration : 1000
			},
			hide : {
				effect : "explode",
				duration : 1000
			}

		}).prev(".ui-dialog-titlebar").css("background", "#bdcfff");
		
		
		$("#dialogValidazione").dialog({
			autoOpen : false,
			resizable : false,
			closeOnEscape : false,
			title : "VALIDAZIONE IMPRESA",
			width : 980,
			height : 700,
			position : 'top',
			draggable : false,
			modal : true,
			buttons : {
				"Salva" : function() {
					if (checkFormValidazione()) {
						$("#validazione").submit();
					}
				},
				"Esci" : function() {
					$(this).dialog("close");
					$("html,body").animate({
						scrollTop : 0
					}, 500, function() {
					});
				}

			},
			show : {
				effect : "blind",
				duration : 1000
			},
			hide : {
				effect : "explode",
				duration : 1000
			}

		}).prev(".ui-dialog-titlebar").css("background", "#bdcfff");
	});
</script>

<%
	String param = "stabId=" + StabilimentoDettaglio.getIdStabilimento() + "&opId="
			+ StabilimentoDettaglio.getIdOperatore()+"&searchcodeidApiario="+StabilimentoDettaglio.getIdStabilimento()+"&searchcodeidAzienda="+StabilimentoDettaglio.getIdOperatore() +"&searchcodeCodiceAziendaSearch="+Operatore.getCodiceAzienda()+"&searchcodeProgressivoApiarioSearch="+StabilimentoDettaglio.getProgressivoBDA() ;
%>

<%
	String nomeContainer = "apiari";
	if ((User.getSiteId() > 0 && User.getSiteId() != StabilimentoDettaglio.getIdAsl())) {
		nomeContainer = "apiari-cu";
	}
// 	else
// 	{
// 		if (User.getRoleId()!=Role.RUOLO_APICOLTORE && User.getSiteId()<=0)
// 			nomeContainer = "apiari-cu";
// 	}
	request.setAttribute("Operatore", StabilimentoDettaglio.getOperatore());
%>
<script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	
	<%=showError(request, "esitoValidazione") %>

<div align="right">
	<%
		if (StabilimentoDettaglio.getStato() == 4) {
	%>
	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle"
		height="16" width="16" /> <input type="button"
		title="Certificato Cessazione" value="Certificato Cessazione"
		onClick="openRichiestaPDFOpu2('<%=StabilimentoDettaglio.getIdStabilimento()%>', '-1', '-1', '-1', 'modelloCessazioneOpu.xml', 'SchedaCessazione');">
	<%
		}
	%>



</div>


<!-- <div align="right"> -->
<!-- 	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" -->
<!-- 		height="16" width="16" /> <input type="button" title="Stampa Scheda" -->
<!-- 		value="Stampa Scheda" -->
<%-- 		onClick="openRichiestaPDFOpu('<%= StabilimentoDettaglio.getIdStabilimento() %>', '-1', '-1', '-1', '15');"> --%>
<!-- </div> -->

<%
if(StabilimentoDettaglio.isFlagLaboratorio()==false)
{
%>
<div align="right">
	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle"
		height="16" width="16" /> <input type="button" title="Stampa Scheda"
		value="Stampa Scheda"
		onClick="openRichiestaPDFOpu('<%=StabilimentoDettaglio.getIdStabilimento()%>', '-1', '-1', '-1', '20');">
</div>

<div align="right">
	<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle"
		height="16" width="16" /> <input type="button" title="Stampa Scheda"
		value="Stampa Riepilogo Completo"
		onClick="openRichiestaPDFOpu('<%=StabilimentoDettaglio.getIdStabilimento()%>', '-1', '-1', '-1', '21');">
</div>
<%} %>
<dhv:container name="<%=nomeContainer%>" selected="Scheda"
	object="Operatore" param="<%=param%>" hideContainer="false">

	<br>
	<%
		
	%>
	<br />

<%
if(StabilimentoDettaglio.isFlagLaboratorio() != true)
{
%>
	<center>
		<table class="details">
			<tr>
				<th colspan="2">STATO</th>
			<tr>
			<tr>
				<td>STATO SINCRONIZZAZIONE BDN</td>
				<td><%=StabilimentoDettaglio.isSincronizzatoBdn() == true ? "<img src=\"images/verde.gif\"> SINCRONIZZATO "
						: "<img src=\"images/rosso.gif\"> NON SINCRONIZZATO"%>
				</td>
			</tr>

			<%
				if (StabilimentoDettaglio.isSincronizzatoBdn() != true && StabilimentoDettaglio.isFlagLaboratorio()==false) {
			%>

			<tr>
				<td>MOTIVO ULTIMO KO</td>
				<td><%=fixNoteEsito(noteEsitoKO)%></td>
			</tr>

			<tr>
				<td>Operazioni</td>
				<td><dhv:permission name="apicoltura-sincronizza-view">
				<%if (StabilimentoDettaglio.getOperatore().isSincronizzatoBdn()==true)
					{%>
						<INPUT TYPE="button" value="Sincronizza con BDN"
							onClick="loadModalWindow();window.location.href='ApicolturaApiari.do?command=SendBdn&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>';" />
					<%}
				else
				{%>
				IMPRESA DA VALIDARE IN GESTIONE RICHIESTE
				<%} %>	
					</dhv:permission> 
 </td>
			</tr>

			<%
				}
			%>


			<tr>
				<td>STATO APICOLTURA</td>
				<td><%=LookupStati.getSelectedValue(Operatore.getStato())%> <%=Operatore.getDataChiusura() != null ? toDateasString(Operatore.getDataChiusura()) : ""%>
				</td>
			</tr>

			<tr>
				<td>STATO APIARIO</td>
				<td><%=LookupStati.getSelectedValue(StabilimentoDettaglio.getStato())%>
				</td>
			</tr>
			<dhv:permission name="apicoltura-modifica-apicoltore-view">
			<%
			if (StabilimentoDettaglio.isSincronizzatoBdn()==false)
			{
			%>
			<tr>
				<td colspan="2"><a href="#" onclick="javascript:$('#dialogModificaApiario').dialog('open');"><CENTER>MODIFICA DATI APIARIO</CENTER></a></td>
			</tr>
			<%} %>
			</dhv:permission>
			
			<dhv:permission name="apicoltura-modifica-apiario-view">
			<%
			if (StabilimentoDettaglio.getOperatore().isSincronizzatoBdn()==false)
			{
			%>
			<tr>
				<td colspan="2"><a href="#" onclick="javascript:$('#dialogModificaApicoltore').dialog('open');"><CENTER>MODIFICA DATI APICOLTORE</CENTER></a></td>
				
			</tr>
			<%} %>
			</dhv:permission>
			
			<dhv:permission name="apicoltura-modifica-modalita-view">
			<%
			if (StabilimentoDettaglio.getOperatore().getIdTipoAttivita()==1)
			{
			%>
			<tr>
				<td colspan="2"><a href="#" onclick="javascript:$('#dialogModificaModalita').dialog('open');"><CENTER>MODIFICA MODALITA' ALLEVAMENTO</CENTER></a></td>
			</tr>
			<%} %>
			</dhv:permission>
			
			<dhv:permission name="apicoltura-modifica-apicoltore-view">
			<tr>
				<td colspan="2"><a href="#" onclick="javascript:$('#dialogModificaRecapiti').dialog('open');"><CENTER>MODIFICA RECAPITI</CENTER></a></td>
			</tr>
			</dhv:permission>
		</table>
	</center>
	<br />
	<br />
	<%} %>
	
	<dhv:permission name="note_hd-view">
<jsp:include page="../note_hd/link_note_hd.jsp">
<jsp:param name="riferimentoId" value="<%=StabilimentoDettaglio.getIdStabilimento() %>" />
<jsp:param name="riferimentoIdNomeTab" value="apicoltura_apiari" />
</jsp:include> <br><br>
</dhv:permission>

<%-- <jsp:include page="../preaccettazionesigla/button_preaccettazione.jsp">
    <jsp:param name="riferimentoIdPreaccettazione" value="<%=StabilimentoDettaglio.getIdStabilimento() %>" />
    <jsp:param name="riferimentoIdNomePreaccettazione" value="stabId" />
    <jsp:param name="riferimentoIdNomeTabPreaccettazione" value="apicoltura_imprese" />
    <jsp:param name="userIdPreaccettazione" value="<%=User.getUserId() %>" />
</jsp:include> --%>

	<%
		if (1 == 1) {
	%>
	<jsp:include page="../schede_centralizzate/iframe.jsp">
		<jsp:param name="objectId"
			value="<%=StabilimentoDettaglio.getIdStabilimento()%>" />
		<jsp:param name="objectIdName" value="stab_id" />
		<jsp:param name="tipo_dettaglio" value="20" />
	</jsp:include>
	
	
	<%
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	String data  = dateFormat.format(Operatore.getEntered());
	
	%>
	
	<dhv:permission name="apicoltura-info-view">
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong>INFO</strong></th>
			</tr>
			<tr>
				<td nowrap class="formLabel">Inserito Da</td>
				<td><dhv:username id="<%=StabilimentoDettaglio.getEnteredBy() %>"/> in data <%=data%>
				</td>
				
			</tr>
			<tr>
				<td nowrap class="formLabel">Ruolo</td>
				<td><dhv:rolename id="<%=StabilimentoDettaglio.getEnteredBy() %>"/>
				</td>
			</tr>
			</table>
			</dhv:permission>
	
	<%
		} else {
	%>

	<fieldset>
		<legend>
			<b>ANAGRAFICA ATTIVITA</b>
		</legend>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong>ATTIVITA DI APICOLTURA</strong></th>
			</tr>


			<tr>
				<td nowrap class="formLabel">Stato BDN</td>
				<td><%=Operatore.isSincronizzatoBdn() == true ? "<img src=\"images/verde.gif\">"
							: "<img src=\"images/rosso.gif\">"%>
				</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">Comune Sede Legale</td>
				<td><%=Operatore.getSedeLegale() != null ? toHtml2(Operatore.getSedeLegale()
							.getDescrizioneComune()) : ""%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Cap</td>
				<td><%=Operatore.getSedeLegale() != null ? Operatore.getSedeLegale().getCap() : ""%></td>
			</tr>

			<tr id="searchcodeIdprovinciaTR">

				<td nowrap class="formLabel">Provincia</td>
				<td><%=Operatore.getSedeLegale() != null ? toHtml2(Operatore.getSedeLegale()
							.getDescrizione_provincia()) : ""%>
				</td>
			</tr>


			<tr>
				<td nowrap class="formLabel">Denominazione</td>
				<td><%=Operatore.getRagioneSociale()%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">STATO</td>
				<td><%=LookupStati.getSelectedValue(Operatore.getStato())%> <%=Operatore.getDataChiusura() != null ? toDateasString(Operatore.getDataChiusura()) : ""%>
				</td>
			</tr>




			<tr>
				<td class="formLabel" nowrap>Codice Fiscale Proprietario</td>
				<td><%=Operatore.getRappLegale() != null ? Operatore.getRappLegale().getCodFiscale() : ""%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Proprietario (cognome e nome)</td>
				<td><%=Operatore.getRappLegale() != null ? Operatore.getRappLegale().getCognome() + " "
							+ Operatore.getRappLegale().getNome() : ""%>

				</td>

			</tr>



			<tr>
				<td class="formLabel" nowrap>Codice Azienda</td>
				<td><%=toHtml2(StabilimentoDettaglio.getOperatore().getCodiceAzienda())%></td>
			</tr>


			<tr>
				<td class="formLabel" nowrap>Data Inizio Attivita</td>
				<td><%=toDateasString(Operatore.getDataInizio())%></td>
			</tr>


			<tr>
				<td nowrap class="formLabel">Indirizzo Sede Legale</td>
				<td><%=Operatore.getSedeLegale() != null ? Operatore.getSedeLegale().getVia() : ""%></td>
			</tr>




			<tr>
				<td nowrap class="formLabel">Tipo Attivita</td>
				<td><%=TipoAttivitaApi.getSelectedValue(Operatore.getIdTipoAttivita())%>

				</td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Domicilio Digitale<br>(Pec)
				</td>
				<td><%=toHtml2(Operatore.getDomicilioDigitale())%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Telefono Fisso</td>
				<td><%=toHtml2(Operatore.getTelefono1())%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Telefono Cellulare</td>
				<td><%=toHtml2(Operatore.getTelefono2())%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">Fax</td>
				<td><%=toHtml2(Operatore.getFax())%></td>
			</tr>



		</table>

	</fieldset>
	<br>
	<fieldset>
		<legend>
			<b>ANAGRAFICA APIARIO</b>
		</legend>
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong>APIARIO</strong></th>
			</tr>

			<tr>
				<td nowrap class="formLabel">Stato BDN</td>
				<td>
					<%-- 		<%=StabilimentoDettaglio.isSincronizzatoBdn()==true ? "<img src=\"images/verde.gif\">" : "<img src=\"images/rosso.gif\"> <a href=\"ApicolturaApiari.do?command=Sen	dBdn&stabId="+StabilimentoDettaglio.getIdStabilimento()+"\">SINCRONIZZA CON BDN</a>" %> --%>

				</td>
			</tr>
			<tr>
				<td nowrap class="formLabel">ASL di Competenza</td>
				<td><%=AslList.getSelectedValue(StabilimentoDettaglio.getIdAsl())%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">PROGRESSIVO</td>
				<td><%=StabilimentoDettaglio.getProgressivoBDA()%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">DETENTORE</td>
				<td><%=StabilimentoDettaglio.getDetentore() != null ? toHtml2(StabilimentoDettaglio
							.getDetentore().getCognome())
							+ " "
							+ toHtml2(StabilimentoDettaglio.getDetentore().getNome()) : ""%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">CODICE FISCALE DETENTORE</td>
				<td><%=StabilimentoDettaglio.getDetentore() != null ? toHtml2(StabilimentoDettaglio
							.getDetentore().getCodFiscale()) : ""%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">CLASSIFICAZIONE</td>
				<td><%=toHtml2(ApicolturaClassificazione.getSelectedValue(StabilimentoDettaglio
							.getIdApicolturaClassificazione()))%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">SOTTOSPECIE</td>
				<td><%=toHtml2(ApicolturaSottospecie.getSelectedValue(StabilimentoDettaglio
							.getIdApicolturaSottospecie()))%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">MODALITA</td>
				<td><%=toHtml2(ApicolturaModalita.getSelectedValue(StabilimentoDettaglio
							.getIdApicolturaModalita()))%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">NUMERO ALVEARI</td>
				<td><%=StabilimentoDettaglio.getNumAlveari()%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">NUMERO SCIAMI / NUCLEI</td>
				<td><%=StabilimentoDettaglio.getNumSciami()%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">NUMERO PACCHI API</td>
				<td><%=StabilimentoDettaglio.getNumPacchi()%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">NUMERO API REGINE</td>
				<td><%=StabilimentoDettaglio.getNumRegine()%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">DATA APERTURA</td>
				<td><%=toDateasString(StabilimentoDettaglio.getDataApertura())%></td>
			</tr>
			<tr>
				<td nowrap class="formLabel">DATA CHIUSURA</td>
				<td><%=toDateasString(StabilimentoDettaglio.getDataChiusura())%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">UBICAZIONE</td>
				<td><%=StabilimentoDettaglio.getSedeOperativa().getDescrizioneComune() + " - "
							+ StabilimentoDettaglio.getSedeOperativa().getVia() + " , "
							+ StabilimentoDettaglio.getSedeOperativa().getLatitudine() + " - "
							+ StabilimentoDettaglio.getSedeOperativa().getLongitudine()%></td>
			</tr>

			<tr>
				<td nowrap class="formLabel">COORDINATE APIARIO</td>
				<td></td>
			</tr>




		</table>
	</fieldset>



	



		<div id="dialogValidazione">
			<form name="validazione" id="validazione"
				action="ApicolturaApiari.do?command=Validate&autopopulate=true"
				method="POST">

				<table cellpadding="4" cellspacing="0" border="0" width="100%"
					class="details">


					<input type="hidden" name="idStabilimento"
						value="<%=StabilimentoDettaglio.getIdStabilimento()%>" />
					<input type="hidden" name="opiId"
						value="<%=StabilimentoDettaglio.getIdOperatore()%>" />

					<tr>
						<td nowrap class="formLabel">Ragione Sociale</td>
						<td>

							<div><%=StabilimentoDettaglio.getOperatore().getRagioneSociale()%></div>
						</td>

					</tr>
					<tr>
						<td nowrap class="formLabel">Proprietario</td>
						<td>

							<div><%=StabilimentoDettaglio.getOperatore().getRappLegale() != null ? StabilimentoDettaglio
							.getOperatore().getRappLegale().getNome()
							+ " " + StabilimentoDettaglio.getOperatore().getRappLegale().getCognome() : ""%></div>
						</td>

					</tr>
					<tr>
						<td nowrap class="formLabel">Codice Fiscale Proprietario</td>
						<td>

							<div><%=StabilimentoDettaglio.getOperatore().getRappLegale() != null ? StabilimentoDettaglio
							.getOperatore().getRappLegale().getCodFiscale() : ""%></div>
						</td>

					</tr>

					<tr>
						<td nowrap class="formLabel">Tipo Attivita</td>
						<td><%=TipoAttivitaApi.getHtmlSelect("idTipoAttivita", StabilimentoDettaglio.getOperatore()
							.getIdTipoAttivita())%>
							<font color="red">*</font> <%=showError(request, "tipoAttivitaError")%>


						</td>
					</tr>

					<tr>
						<td nowrap class="formLabel">Domicilio Digitale<br>(Pec)
						</td>
						<td><input type="text" size="70" name="domicilioDigitale"
							value="<%=toHtml2(StabilimentoDettaglio.getOperatore().getDomicilioDigitale())%>">
							<font color="red">*</font> <%=showError(request, "domicilioDigitaleError")%>

						</td>
					</tr>

					<tr>
						<td nowrap class="formLabel">Telefono Fisso</td>
						<td><input type="text" size="70" name="telefono1"
							value="<%=toHtml2(StabilimentoDettaglio.getOperatore().getTelefono1())%>">
							<font color="red">*</font> <%=showError(request, "telefonoFissoError")%>
						</td>
					</tr>

					<tr>
						<td nowrap class="formLabel">Telefono Cellulare</td>
						<td><input type="text" size="70" name="telefono2"
							value="<%=toHtml2(StabilimentoDettaglio.getOperatore().getTelefono2())%>">
							<font color="red">*</font> <%=showError(request, "telefonoCellulareError")%>
						</td>
					</tr>


					<tr>
						<td nowrap class="formLabel">Comune Sede Legale</td>
						<td>
							<%
								int comuneSL = -1;
										if (StabilimentoDettaglio.getOperatore().getSedeLegale() != null)
											comuneSL = StabilimentoDettaglio.getOperatore().getSedeLegale().getComune();
							%> <%=ComuniList.getHtmlSelect("comuneSL", comuneSL)%> <font
							color="red">*</font> <%=showError(request, "comuneSedeLegaleError")%>

						</td>
					</tr>


					<tr>
						<td nowrap class="formLabel">Cap Sede Legale</td>
						<td><input type="text" size="70" name="presso" maxlength="80"
							value="<%=(StabilimentoDettaglio.getOperatore().getSedeLegale() != null) ? toHtml2(StabilimentoDettaglio
							.getOperatore().getSedeLegale().getCap())
							: ""%>">
							<font color="red">*</font> <%=showError(request, "capSedeLegaleError")%>
						</td>
					</tr>



					<tr id="searchcodeIdprovinciaTR">

						<td nowrap class="formLabel">Provincia Sede Legale</td>
						<td><input type="text" required="required"
							name="searchcodeIdprovinciaTesto" id="searchcodeIdprovinciaTesto"
							value="<%=StabilimentoDettaglio.getOperatore().getSedeLegale().getProvincia()%>" />
							<input type="hidden" required="required"
							name="searchcodeIdprovincia" id="searchcodeIdprovincia" /> <font
							color="red">*</font> <%=showError(request, "provinciaSedeLegaleError")%>
						</td>
					</tr>

					<tr>
						<td nowrap class="formLabel">Indirizzo Sede Legale</td>
						<td><input type="text" name="viaTestoSL" id="viaTestoSL"
							value="<%=(StabilimentoDettaglio.getOperatore().getSedeLegale() != null) ? toHtml2(StabilimentoDettaglio
							.getOperatore().getSedeLegale().getVia())
							: ""%>" />
							<font color="red">*</font> <%=showError(request, "viaSedeLegaleError")%>
						</td>
					</tr>


					<tr>
						<td nowrap class="formLabel">Comune Ubicazione Apiario</td>
						<td>
							<%
								int comuneSO = -1;
										if (StabilimentoDettaglio.getSedeOperativa() != null)
											comuneSO = StabilimentoDettaglio.getSedeOperativa().getComune();
							%> <%=ComuniList.getHtmlSelect("comuneSO", comuneSO)%> <font
							color="red">*</font>

						</td>


					</tr>

					<tr>
						<td nowrap class="formLabel">Cap Ubicazione Apiario</td>
						<td><input type="text" size="70" name="pressoSO"
							id="pressoSO" style="width: 50px;"
							value=<%=StabilimentoDettaglio.getSedeOperativa() != null ? toHtml2(StabilimentoDettaglio
							.getSedeOperativa().getCap()) : ""%>>
						</td>
					</tr>

					<tr id="searchcodeIdprovinciaTR">

						<td nowrap class="formLabel">Provincia Ubicazione Apiario</td>
						<td><input type="text" required="required"
							name="searchcodeIdprovinciaTestoSO"
							id="searchcodeIdprovinciaTestoSO"
							value="<%=StabilimentoDettaglio.getSedeOperativa() != null ? StabilimentoDettaglio
							.getSedeOperativa().getProvincia() : ""%>" />

						</td>
					</tr>

					<tr>
						<td nowrap class="formLabel">Indirizzo Ubicazione Apiario</td>
						<td><input type="text" name="viaTestoSO" id="viaTestoSO"
							value="<%=StabilimentoDettaglio.getSedeOperativa() != null ? toHtml2(StabilimentoDettaglio
							.getSedeOperativa().getVia()) : ""%>" />
							<font color="red">*</font> <%=showError(request, "indirizzoError")%>
						</td>
					</tr>



					<tr>
						<td nowrap class="formLabel">Latitudine</td>
						<td><input type="text" name="latitudine" id="latitudine"
							value="<%=StabilimentoDettaglio.getSedeOperativa() != null
							&& StabilimentoDettaglio.getSedeOperativa().getLatitudine() > 0 ? ""
							+ StabilimentoDettaglio.getSedeOperativa().getLatitudine() : ""%>" />
							<font color="red">*</font> <%=showError(request, "latitudineError")%>
						</td>
					</tr>
					<tr>
						<td nowrap class="formLabel">Longitudine</td>
						<td><input type="text" name="longitudine" id="longitudine"
							value="<%=StabilimentoDettaglio.getSedeOperativa() != null
							&& StabilimentoDettaglio.getSedeOperativa().getLongitudine() > 0 ? ""
							+ StabilimentoDettaglio.getSedeOperativa().getLongitudine() : ""%>" />
							<font color="red">*</font> <%=showError(request, "longitudineError")%>
						</td>
					</tr>

					<tr style="display: block">
						<td colspan="2"><input id="coord2button" type="button"
							value="Calcola Coordinate"
							onclick="javascript:getCoordinateNoTemplate1Nav(document.getElementById('viaTestoSO').value, document.forms['validazione'].comuneSO.options[document.forms['validazione'].comuneSO.selectedIndex].text,document.forms['validazione'].searchcodeIdprovinciaTestoSO.value,document.forms['validazione'].longitudine, document.forms['validazione'].latitudine);" />
						</td>
					</tr>


					<tr>
						<td nowrap class="formLabel">Numero Alveari</td>
						<td><input type="text" name="numAlveari" id="numAlveari"
							style="width: 50px;"
							value="<%=StabilimentoDettaglio.getNumAlveari()%>"> <font
							color="red">*</font></td>
					</tr>
					<tr>
						<td nowrap class="formLabel">Numero Sciami / Nuclei</td>

						<td><input type="text" name="numSciami" id="numSciami"
							style="width: 50px;"
							value="<%=StabilimentoDettaglio.getNumSciami()%>"> <font
							color="red">*</font></td>

					</tr>

					<tr>
						<td class="formLabel" nowrap>Classificazione</td>
						<td><%=ApicolturaClassificazione.getHtmlSelect("idApicolturaClassificazione",
							StabilimentoDettaglio.getIdApicolturaClassificazione())%>
							<font color="red">*</font> <%=showError(request, "classificazioneError")%>
						</td>
					</tr>

					<tr>
						<td class="formLabel" nowrap>Sottospecie</td>
						<td><%=ApicolturaSottospecie.getHtmlSelect("idApicolturaSottospecie",
							StabilimentoDettaglio.getIdApicolturaSottospecie())%>
							<font color="red">*</font> <%=showError(request, "sottospecieError")%>
						</td>
					</tr>

					<tr>
						<td class="formLabel" nowrap>Modalita Allevamento</td>
						<td><%=ApicolturaModalita.getHtmlSelect("idApicolturaModalita",
							StabilimentoDettaglio.getIdApicolturaModalita())%>
							<font color="red">*</font> <%=showError(request, "modalitaError")%>
						</td>
					</tr>

					<tr>
						<td class="formLabel" nowrap>Data Apertura</td>
						<td><input type="text" size="70" name="dataApertura"
							id="dataInizioAttivita" class="required" placeholder="dd/MM/YYYY"
							readonly="readonly"
							<%=toDateasString(StabilimentoDettaglio.getDataApertura())%>>

							<a href="#"
							onClick="cal19.select(document.forms['validazione'].dataApertura,'anchor19','dd/MM/yyyy'); return false;"
							NAME="anchor19" ID="anchor19"> <img
								src="images/icons/stock_form-date-field-16.gif" border="0"
								align="absmiddle"></a> <font color="red">*</font> <%=showError(request, "dataAperturaError")%>
						</td>
					</tr>


				</table>
				
				

			</form>

		</div>

		<% } %>



		 
</dhv:container>






<div id = "dialogModificaApicoltore">
<jsp:include page="apicoltore_modifica.jsp"></jsp:include>
</div>

<div id = "dialogModificaApiario">
<jsp:include page="apiario_modifica.jsp"></jsp:include>
</div>

<div id = "dialogModificaModalita">
<jsp:include page="apiario_modifica_modalita.jsp"></jsp:include>
</div>

<div id = "dialogModificaRecapiti">
<jsp:include page="apicoltore_modifica_recapiti.jsp"></jsp:include>
</div>
