<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="org.aspcfs.modules.stabilimenti.base.SottoAttivita,java.util.*,java.text.DateFormat,org.aspcfs.modules.stabilimenti.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>
<%@page import="java.util.Date"%>

<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />

<jsp:useBean id="SearchOrgListInfo"
	class="org.aspcfs.utils.web.PagedListInfo" scope="session" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="OrgCategoriaRischioList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.stabilimenti.base.Organization"
	scope="request" />
<jsp:useBean id="SICCodeList"
	class="org.aspcfs.modules.admin.base.SICCodeList" scope="request" />
<jsp:useBean id="applicationPrefs"
	class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit"
	scope="request" />
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="statiStabilimenti"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LookupClassificazione"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LookupProdotti" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="statoLabImpianti"
	class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="categoriaList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="imballataList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="tipoAutorizzazioneList"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request" />
<jsp:useBean id="elencoSottoAttivita" class="java.util.ArrayList"
	scope="request" />
<jsp:useBean id="impreseAssociateMercatoIttico"
	class="java.util.ArrayList" scope="request" />
<jsp:useBean id="stabilimentiAssociateMercatoIttico"
	class="java.util.ArrayList" scope="request" />
<jsp:useBean id="ControlloDocumentale"
	class="org.aspcfs.modules.stabilimenti.base.ControlloDocumentale"
	scope="request"></jsp:useBean>
<!-- 
<script>
function openPopupModulesPdf(orgId){
	var res;
	var result;
		window.open('ManagePdfModules.do?command=PrintSelectedModules&orgId='+orgId,'popupSelect',
		'height=300px,width=580px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=no,resizable=no ,modal=yes');
}
</script>-->

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
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript">
//PopolaCombo.getValoriComboComuniProvinciaOSM("NA",setComuniComboCallback);


	function doCheck(form) {

		if (checkForm(form)) {
			form.submit();
			return true;
		} else
			return false;

	}

	function checkForm(form) {
		formTest = true;
		msg = '';
		if (form.organization_name.value == '') {
			msg += ' - Controllare di aver inserito il nome Impresa\n';
			formTest = false;
		}

		if (form.codice_fiscale_impresa.value == '' && form.partita_iva.value == '') {
			msg += ' - Controllare di aver inserito Codice Fiscale Stabilimento o Partita Iva\n';
			formTest = false;
		}

		if (form.domicilio_digitale_impresa.value == '') {
			msg += ' - Controllare di aver inserito il Domicilio Digitale\n';
			formTest = false;
		}

		if (form.codice_fiscale_rappr.value == '') {
			msg += ' - Controllare di aver inserito il Cf Rappresentante\n';
			formTest = false;
		}
		if (form.nome.value == '') {
			msg += ' - Controllare di aver inserito il Nome Rappresentante\n';
			formTest = false;
		}
		if (form.cognome.value == '') {
			msg += ' - Controllare di aver inserito il Cognome Rappresentante\n';
			formTest = false;
		}
		
		if (form.comune_operativa.value == '-1' || form.comune_operativa.value == '' || form.comune_operativa.value == ' ') {
			msg += ' - Controllare di aver inserito il Comune per la sede operativa\n';
			formTest = false;
		}

		if (form.provincia_operativa.value == '-1' || form.provincia_operativa.value == '' || form.provincia_operativa.value == ' ') {
			msg += ' - Controllare di aver inserito la Provincia per la sede operativa\n';
			formTest = false;
		}

		if (form.indirizzo_operativa.value == '') {
			msg += ' - Controllare di aver inserito un indirizzo per la sede operativa\n';
			formTest = false;
		}

		if (form.cap_operativa.value == '') {
			msg += ' - Controllare di aver inserito il cap per la sede operativa\n';
			formTest = false;
		}

		

		if (formTest == false) {
			alert(msg);
		}
		return formTest;

	}

	function popolaComboProvincia(provincia_selezionata) {
		var p = null;
		provincia = document.getElementById(provincia_selezionata).value;
		if (provincia == "NAPOLI" || provincia == "NA")
			p = "NA";
		if (provincia == "AVELLINO" || provincia == "AV")
			p = "AV";
		if (provincia == "SALERNO" || provincia == "SA")
			p = "SA";
		if (provincia == "BENEVENTO" || provincia == "BN")
			p = "BN";
		if (provincia == "CASERTA" || provincia == "CE")
			p = "CE";
		//p=document.getElementById("provincia").options[provincia].text;
		//  alert("Ci sono "+p);
		//PopolaComuni a partire dalla provincia
		if (provincia != "-1") {
			if (provincia_selezionata == 'provincia')
				PopolaCombo.getValoriComboComuniProvinciaOSM(p,
						setComuniComboCallback);
			if (provincia_selezionata == 'provincia_legale')
				PopolaCombo.getValoriComboComuniProvinciaOSM(p,
						setComuniComboLegaleCallback);
			if (provincia_selezionata == 'provincia_operativa')
				PopolaCombo.getValoriComboComuniProvinciaOSM(p,
						setComuniComboOperativaCallback);
		}
	}

	function setComuniComboCallback(returnValue) {
		var select = document.getElementById('comune_di_residenza'); //Recupero la SELECT
		//Azzero il contenuto della seconda select
		for (var i = select.length - 1; i >= 0; i--)
			select.remove(i);

		indici = returnValue[0];
		valori = returnValue[1];
		//Popolo la seconda Select
		for (j = 0; j < indici.length; j++) {
			//Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
			var NewOpt = document.createElement('option');
			NewOpt.value = indici[j]; // Imposto il valore
			NewOpt.text = valori[j]; // Imposto il testo

			var element = document.getElementById("comune_di_residenza_hidden");
			if (typeof (element) != 'undefined' && element != null) {
				if (valori[j].toUpperCase() == element.value)
					NewOpt.selected = true;
			}
			//Aggiungo l'elemento option
			try {
				select.add(NewOpt, null); //Metodo Standard, non funziona con IE
			} catch (e) {
				select.add(NewOpt); // Funziona solo con IE
			}
		}
	}

	function setComuniComboLegaleCallback(returnValue) {
		var select = document.getElementById('comune_legale'); //Recupero la SELECT
		//Azzero il contenuto della seconda select
		for (var i = select.length - 1; i >= 0; i--)
			select.remove(i);

		indici = returnValue[0];
		valori = returnValue[1];
		//Popolo la seconda Select
		for (j = 0; j < indici.length; j++) {
			//Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
			var NewOpt = document.createElement('option');
			NewOpt.value = indici[j]; // Imposto il valore
			NewOpt.text = valori[j]; // Imposto il testo

			var element = document.getElementById("comune_legale_hidden");
			if (typeof (element) != 'undefined' && element != null) {
				if (valori[j].toUpperCase() == element.value)
					NewOpt.selected = true;
			}
			//Aggiungo l'elemento option
			try {
				select.add(NewOpt, null); //Metodo Standard, non funziona con IE
			} catch (e) {
				select.add(NewOpt); // Funziona solo con IE
			}
		}
	}

	function setComuniComboOperativaCallback(returnValue) {
		var select = document.getElementById('comune_operativa'); //Recupero la SELECT
		//Azzero il contenuto della seconda select
		for (var i = select.length - 1; i >= 0; i--)
			select.remove(i);

		indici = returnValue[0];
		valori = returnValue[1];
		//Popolo la seconda Select
		for (j = 0; j < indici.length; j++) {
			//Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
			var NewOpt = document.createElement('option');
			NewOpt.value = indici[j]; // Imposto il valore
			NewOpt.text = valori[j]; // Imposto il testo

			var element = document.getElementById("comune_operativa_hidden");
			if (typeof (element) != 'undefined' && element != null) {
				if (valori[j].toUpperCase() == element.value)
					NewOpt.selected = true;
			}
			//Aggiungo l'elemento option
			try {
				select.add(NewOpt, null); //Metodo Standard, non funziona con IE
			} catch (e) {
				select.add(NewOpt); // Funziona solo con IE
			}
		}
	}
</script>

<%@ include file="../initPage.jsp"%>
<%
	boolean  isMercatoIttico=false;
	Iterator iElencoAttivita_ = elencoSottoAttivita.iterator();
	if (iElencoAttivita_.hasNext()) {
		while (iElencoAttivita_.hasNext()) {
			SottoAttivita thisAttivita_ = (SottoAttivita) iElencoAttivita_.next();
			if ( (thisAttivita_.getCodice_impianto() == 15) && ( thisAttivita_.getCodice_sezione() == 8) ) {
				isMercatoIttico=true;
			}
		}
	}
 
	java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
	Timestamp d = new Timestamp (datamio.getTime()); 
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td><a href="Stabilimenti.do"><dhv:label
				name="stabilimenti.stabilimenti">Accounts</dhv:label></a> > 
			<a href="Stabilimenti.do?command=Details&orgId=<%=OrgDetails.getId()%>">Dettaglio</a> 
		
		</tr>
	</table>

<br>
<br>
<form id="modifica_stabilimento_form" name="modifica_stabilimento_form"
	action="Stabilimenti.do?command=AggiornaInfoStabilimento" method="post">
	<input type="hidden" id="orgId" name="orgId"
		value="<%=OrgDetails.getOrgId() %>" />
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
						name="stabilimenti.stabilimenti_details.PrimaryInformation">Primary Information</dhv:label></strong>
			</th>
		</tr>


		<dhv:evaluate if="<%= SiteList.size() > 1 %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label
						name="stabilimenti.site">Site</dhv:label></td>
				<td><%=SiteList.getSelectedValue(OrgDetails.getSiteId())%> <input
					type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>">
				</td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= SiteList.size() <= 1 %>">
			<input type="hidden" name="siteId" id="siteId" value="-1" />
		</dhv:evaluate>


		<tr class="containerBody">
			<td nowrap class="formLabel" name="orgname1" id="orgname1"><dhv:label
					name="stabilimenti.stabilimenti_add.OrganizationName">Organization Name</dhv:label>
			</td>
			<% String var_organization_name = "";
			if (hasText(OrgDetails.getName()))
				var_organization_name = toHtmlValue(OrgDetails.getName());
			%>
			<td><input size="150" type="text" id="organization_name"
				name="organization_name" value="<%=var_organization_name%>" /></td>
		</tr>


		<dhv:evaluate if="<%= hasText(OrgDetails.getAccountNumber()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label
						name="organization.accountNumber">Account Number</dhv:label></td>
				<td><input size="150" type="text" id="account_number"
					name="account_number" value="<%=OrgDetails.getAccountNumber()%>" /></td>
			</tr>
		</dhv:evaluate>

		<dhv:evaluate if="<%= hasText(OrgDetails.getNumAut())%>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Approval Number</dhv:label>
				</td>
				<td><input size="150" readonly="readonly" type="text"
					id="approval_number" name="approval_number"
					value="<%=OrgDetails.getNumAut()%>" /></td>
			</tr>
		</dhv:evaluate>
		<dhv:evaluate if="<%= hasText(OrgDetails.getCategoria()) %>">
			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Categoria</dhv:label>
				</td>

				<td><input size="150" type="text" id="categoria"
					name="categoria" value="<%=OrgDetails.getCategoria()%>" /></td>

			</tr>
		</dhv:evaluate>


		<tr class="containerBody">
			<td nowrap class="formLabel">Partita IVA
			</td>
			<td>
			<% String var_partita_iva = "";
			if (hasText(OrgDetails.getPartitaIva()))
				var_partita_iva = OrgDetails.getPartitaIva();
			%>
			<input size="150" type="text" maxlength="11" id="partita_iva"
				name="partita_iva" value="<%=var_partita_iva%>" /></td>
		</tr>

		<tr class="containerBody">
			<td nowrap class="formLabel">Codice Fiscale
			</td>
			<% String var_codice_fiscale_impresa = "";
			if (hasText(OrgDetails.getCodiceFiscale()))
				var_codice_fiscale_impresa = OrgDetails.getCodiceFiscale();
			%>

			<td><input size="150" type="text" maxlength="16" id="codice_fiscale_impresa"
				name="codice_fiscale_impresa"
				value="<%=var_codice_fiscale_impresa%>" /></td>
		</tr>

		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label name="">Domicilio Digitale</dhv:label>
			</td>
			<% String var_domicilio_digitale_impresa = "";
			if (hasText(OrgDetails.getDomicilioDigitale()))
				var_domicilio_digitale_impresa = OrgDetails.getDomicilioDigitale();
			%>

			<td><input size="150" type="text"
				id="domicilio_digitale_impresa" name="domicilio_digitale_impresa"
				value="<%=var_domicilio_digitale_impresa%>" /></td>
		</tr>



		<dhv:include name="organization.alert" none="true">
			<dhv:evaluate if="<%= hasText(OrgDetails.getAlertText()) %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label
							name="stabilimenti.stabilimenti_add.AlertDescription">Alert Description</dhv:label>
					</td>

					<td><input size="150" type="text" id="alert_text"
						name="alert_text" value="<%=OrgDetails.getAlertText()%>" /></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>

		<dhv:include name="organization.directBill" none="true">
			<dhv:evaluate if="<%= OrgDetails.getDirectBill() %>">
				<tr class="containerBody">
					<td nowrap class="formLabel">Direct Bill</td>
					<td><input type="checkbox" name="directBill" CHECKED DISABLED /></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>




		<dhv:include name="organization.rating" none="true">
			<dhv:evaluate if="<%= OrgDetails.getRating() != -1 %>">
				<tr class="containerBody">
					<td nowrap class="formLabel"><dhv:label name="sales.rating">Rating</dhv:label>
					</td>

					<td><input size="150" type="text" id="rating" name="rating"
						value="<%=RatingList.getSelectedValue(OrgDetails.getRating())%>" /></td>
				</tr>
			</dhv:evaluate>
		</dhv:include>










	</table>
	<br>
	<%
//			if(OrgDetails.getNomeRappresentante()!=null && ! "".equals(OrgDetails.getNomeRappresentante())) 		{
				%>
	<div id="tab2" class="tab">

		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong><dhv:label name="">Titolare o Legale Rappresentante</dhv:label></strong>

				</th>
			</tr>


			<tr class="containerBody">
				<td nowrap class="formLabel">Codice Fiscale
				</td>
							<% String var_codice_fiscale_rappr = "";
			if (hasText(OrgDetails.getCodiceFiscaleRappresentante()))
				var_codice_fiscale_rappr = OrgDetails.getCodiceFiscaleRappresentante();
			%>
				
				<td><input size="150" type="text" maxlength="16" id="codice_fiscale_rappr"
					name="codice_fiscale_rappr"
					value="<%=var_codice_fiscale_rappr%>" /></td>
			</tr>

			<tr class="containerBody">
				<td nowrap class="formLabel">Nome
				</td>
			<% String var_nome = "";
			if (hasText((OrgDetails.getNomeRappresentante())))
				var_nome = (OrgDetails.getNomeRappresentante());
			%>

				<td><input size="150" type="text" id="nome" name="nome"
					value="<%=var_nome%>" /></td>

			</tr>

			<tr class="containerBody">
				<td nowrap class="formLabel">Cognome
				</td>
							<% String var_cognome = "";
			if (hasText((OrgDetails.getCognomeRappresentante())))
				var_cognome = (OrgDetails.getCognomeRappresentante());
			%>
				
				<td><input size="150" type="text" id="cognome" name="cognome"
					value="<%=var_cognome%>" /></td>

			</tr>

			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Comune di nascita</dhv:label>
				</td>
				<% 
				String n = "";
				if (hasText(OrgDetails.getLuogoNascitaRappresentante()))
					n = OrgDetails.getLuogoNascitaRappresentante().toUpperCase()+" ";
					//n = n.substring(0,1).toUpperCase() + n.substring(1,n.length()).toLowerCase()+" ";
					
					%>
				<input type="hidden" id="comune_hidden" value="<%=n%>" />
				<td>
					<!-- <input size="150" type="text" id="comune_nascita" name="comune_nascita"	value="<=OrgDetails.getLuogoNascitaRappresentante()%>" /> -->

					<%= ComuniList.getHtmlSelectText("comune_nascita",SearchOrgListInfo.getSearchOptionValue("comune_nascita")) %>

				</td>

			</tr>

			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Comune di Residenza</dhv:label>
				</td>
				<% String comune_residenza="";
				if (hasText(OrgDetails.getCity_legale_rapp()))
					comune_residenza=OrgDetails.getCity_legale_rapp().toUpperCase();
				%>
				<input type="hidden" id="comune_di_residenza_hidden"
					value="<%=comune_residenza%>" />

				<td>
					<!-- <input size="150" type="text" id="comune_di_residenza" name="comune_di_residenza" value="<=OrgDetails.getCity_legale_rapp()%>"/> -->
					<select name="comune_di_residenza" value="-1"
					id="comune_di_residenza">
						<option value="-1">-- SELEZIONA VOCE --</option>
				</select>

				</td>
			</tr>

			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Provincia</dhv:label>
				</td>


				<td>
					<!-- 						<input size="150" type="text" id="provincia" name="provincia" value="<=OrgDetails.getProv_legale_rapp()%>"/>
--> <select name="provincia" value="-1" id="provincia"
					onchange="popolaComboProvincia('provincia');">
						<option value="-1">-- SELEZIONA VOCE --</option>
						<option
							<% if (hasText(OrgDetails.getProv_legale_rapp()))
							if(OrgDetails.getProv_legale_rapp().toUpperCase().equals("AVELLINO")) out.print(" selected "); %>
							value="AVELLINO">AV</option>
						<option
							<% if (hasText(OrgDetails.getProv_legale_rapp()))
							if(OrgDetails.getProv_legale_rapp().toUpperCase().equals("BENEVENTO")) out.print(" selected "); %>
							value="BENEVENTO">BN</option>
						<option
							<% if (hasText(OrgDetails.getProv_legale_rapp()))
							if(OrgDetails.getProv_legale_rapp().toUpperCase().equals("CASERTA")) out.print(" selected "); %>
							value="CASERTA">CE</option>
						<option
							<% if (hasText(OrgDetails.getProv_legale_rapp()))
							if(OrgDetails.getProv_legale_rapp().toUpperCase().equals("NAPOLI")) out.print(" selected "); %>
							value="NAPOLI">NA</option>
						<option
							<% if (hasText(OrgDetails.getProv_legale_rapp()))
							if(OrgDetails.getProv_legale_rapp().toUpperCase().equals("SALERNO")) out.print(" selected "); %>
							value="SALERNO">SA</option>
				</select> <script>
			popolaComboProvincia('provincia');
			var select = document.getElementById('comune_nascita'); //Recupero la SELECT
            var comune_hidden=document.getElementById('comune_hidden').value;
            if (comune_hidden!=""){
            	for (var i = select.length - 1; i >= 0; i--){
            		if (select.options[i].value == comune_hidden)
            			select.options[i].selected=true;
				}
            }
			</script>


				</td>

			</tr>

			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Indirizzo</dhv:label>
				</td>

			<% String var_indirizzo = "";
			if (hasText(OrgDetails.getAddress_legale_rapp()))
				var_indirizzo = OrgDetails.getAddress_legale_rapp();
			%>

				<td><input size="150" type="text" id="indirizzo"
					name="indirizzo" value="<%=var_indirizzo%>" /></td>

			</tr>



			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Domicilio digitale</dhv:label>
				</td>
			<% String var_domicilio_digitale_rappr = "";
			if (hasText(OrgDetails.getEmailRappresentante()))
				var_domicilio_digitale_rappr = OrgDetails.getEmailRappresentante();
			%>


				<td><input size="150" type="text" id="domicilio_digitale_rappr"
					name="domicilio_digitale_rappr"
					value="<%=var_domicilio_digitale_rappr%>" /></td>

			</tr>

			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Telefono</dhv:label>
				</td>
							<% String var_telefono = "";
			if (hasText(OrgDetails.getTelefonoRappresentante()))
				var_telefono = OrgDetails.getTelefonoRappresentante();
			%>
				
				<td><input size="150" type="text" id="telefono" name="telefono"
					value="<%=var_telefono%>" /></td>


			</tr>

			<tr class="containerBody">
				<td nowrap class="formLabel"><dhv:label name="">Fax</dhv:label>
				</td>

			<% String var_fax = "";
			if (hasText(OrgDetails.getFax()))
				var_fax = OrgDetails.getFax();
			%>

				<td><input size="150" type="text" id="fax" name="fax"
					value="<%=var_fax%>" /></td>

			</tr>


			<!--  fine delle modifiche -->

		</table>
	</div>
	<br>
	<%
				
	//		}
		%>


			
			<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label name="">Indirizzo sede legale</dhv:label></strong></th>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label name="">Indirizzo</dhv:label>
			</td>
			
			<td><input size="150" type="text" id="indirizzo_legale"
				name="indirizzo_legale"
				value="" /></td>
		</tr>

		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label name="">Comune</dhv:label>
			</td>
			<td><select name="comune_legale" value="-1"
					id="comune_legale">
						<option value="-1">-- SELEZIONA VOCE --</option>
				</select></tr>

		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label name="">Provincia</dhv:label>
			</td>
			<td>
<select name="provincia_legale" value="-1" id="provincia_legale" onchange="popolaComboProvincia('provincia_legale');">
	<option selected value="-1">-- SELEZIONA VOCE --</option>
	<option	value="AV">AV</option>
	<option value="BN">BN</option>
	<option value="CE">CE</option>
	<option value="NA">NA</option>
	<option value="SA">SA</option>
</select>
</td>
		</tr>

		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label name="">CAP</dhv:label>
			</td>
			<td><input size="150" type="text" id="cap_legale"
				name="cap_legale" value="" /></td>
		</tr>

	</table>
			
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label name="">Indirizzo sede operativa</dhv:label></strong></th>
		</tr>
		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label name="">Indirizzo</dhv:label>
			</td>
			<td><input size="150" type="text" id="indirizzo_operativa"
				name="indirizzo_operativa"
				value="" /></td>
		</tr>

		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label name="">Comune</dhv:label>
			</td>
			<td><select name="comune_operativa" value="-1"
					id="comune_operativa">
						<option value="-1">-- SELEZIONA VOCE --</option>
				</select></td>
		</tr>

		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label name="">Provincia</dhv:label>
			</td>
			<td>
<select name="provincia_operativa" value="-1" id="provincia_operativa" onchange="popolaComboProvincia('provincia_operativa');">
	<option selected value="-1">-- SELEZIONA VOCE --</option>
	<option	value="AV">AV</option>
	<option value="BN">BN</option>
	<option value="CE">CE</option>
	<option value="NA">NA</option>
	<option value="SA">SA</option>
</select></td>
		</tr>

		<tr class="containerBody">
			<td nowrap class="formLabel"><dhv:label name="">CAP</dhv:label>
			</td>
			<td><input size="150" type="text" id="cap_operativa"
				name="cap_operativa" value="" /></td>
		</tr>

	</table>
		<%
	Iterator iaddress = OrgDetails.getAddressList().iterator();
	if (iaddress.hasNext()) {
		while (iaddress.hasNext()) {
			OrganizationAddress thisAddress = (OrganizationAddress) iaddress.next();
		 if (thisAddress.getType()==1){	%>
		 	<% 
			String var_indirizzo_legale_hidden = "";
			if (hasText(thisAddress.getStreetAddressLine1()))  var_indirizzo_legale_hidden = thisAddress.getStreetAddressLine1();
			String var_comune_legale_hidden = "";
			if (hasText(thisAddress.getCity()))  var_comune_legale_hidden = thisAddress.getCity().toUpperCase();
			String var_provincia_legale_hidden = "";
			if (hasText(thisAddress.getState()))	var_provincia_legale_hidden = thisAddress.getState();
			String var_cap_legale_hidden = "";
			if (hasText(thisAddress.getZip()))	var_cap_legale_hidden = thisAddress.getZip();
			%>
    	<input type="hidden" id="indirizzo_legale_hidden" value="<%=var_indirizzo_legale_hidden%>"/>
		<input type="hidden" id="comune_legale_hidden" value="<%=var_comune_legale_hidden%>"/>
		<input type="hidden" id="provincia_legale_hidden" value="<%=var_provincia_legale_hidden%>"/>
		<input type="hidden" id="cap_legale_hidden" value="<%=var_cap_legale_hidden%>"/>
		<script>
		document.getElementById("indirizzo_legale").value=document.getElementById("indirizzo_legale_hidden").value;
		document.getElementById("comune_legale").value=document.getElementById("comune_legale_hidden").value;
		if (document.getElementById("provincia_legale_hidden").value != ""){
			var select = document.getElementById('provincia_legale'); //Recupero la SELECT
            var provincia=document.getElementById('provincia_legale_hidden').value.toUpperCase();
            	for (var i = select.length - 1; i >= 0; i--){
            		if (select.options[i].value == provincia){
            			select.options[i].selected=true;
            			popolaComboProvincia('provincia_legale');
            			break;
            	}
			}
		}
		document.getElementById("cap_legale").value=document.getElementById("cap_legale_hidden").value;
		</script>
	<% }else if (thisAddress.getType()==5){ %>
		 	<% 
		 	String var_indirizzo_operativa_hidden = "";
			if (hasText(thisAddress.getStreetAddressLine1()))  var_indirizzo_operativa_hidden = thisAddress.getStreetAddressLine1();
			String var_comune_operativa_hidden = "";
			if (hasText(thisAddress.getCity()))  var_comune_operativa_hidden = thisAddress.getCity().toUpperCase();
			String var_provincia_operativa_hidden = "";
			if (hasText(thisAddress.getState()))	var_provincia_operativa_hidden = thisAddress.getState();
			String var_cap_operativa_hidden = "";
			if (hasText(thisAddress.getZip()))	var_cap_operativa_hidden = thisAddress.getZip();
			%>
    	<input type="hidden" id="indirizzo_operativa_hidden" value="<%=var_indirizzo_operativa_hidden%>"/>
		<input type="hidden" id="comune_operativa_hidden" value="<%=var_comune_operativa_hidden%>"/>
		<input type="hidden" id="provincia_operativa_hidden" value="<%=var_provincia_operativa_hidden%>"/>
		<input type="hidden" id="cap_operativa_hidden" value="<%=var_cap_operativa_hidden%>"/>
		<script>
		document.getElementById("indirizzo_operativa").value=document.getElementById("indirizzo_operativa_hidden").value;
		document.getElementById("comune_operativa").value=document.getElementById("comune_operativa_hidden").value;
		if (document.getElementById("provincia_operativa_hidden").value != ""){
			var select = document.getElementById('provincia_operativa'); //Recupero la SELECT
            var provincia=document.getElementById('provincia_operativa_hidden').value.toUpperCase();
            	for (var i = select.length - 1; i >= 0; i--){
            		if (select.options[i].value == provincia){
            			select.options[i].selected=true;
            			popolaComboProvincia('provincia_operativa');
            			break;
            	}
			}
		}
		document.getElementById("cap_operativa").value=document.getElementById("cap_operativa_hidden").value;
		</script>
	<%} 
	}
					} 
				%>
	</div>
	<input type="button" value="AGGIORNA" onclick="doCheck(document.modifica_stabilimento_form)"/>&nbsp;&nbsp;&nbsp;
	<input type="button" value="ANNULLA"  onclick="location.href='Stabilimenti.do?command=Details&orgId=<%=OrgDetails.getId()%>'"/> 

</form>