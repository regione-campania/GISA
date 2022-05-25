<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - ANY DAMAGES, INCLUDIFNG ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_details.jsp 19045 2007-02-07 18:06:22Z matt $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/taglib/systemcontext-taglib.tld" prefix="sc"%>
<%@ page
	import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>
<%@page import="com.darkhorseventures.framework.actions.ActionContext"%>
<%@page import="java.sql.*,java.util.HashMap,java.util.Map"%>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="mapAttributi" class="java.util.HashMap" scope="request" />

<%@page import="org.aspcfs.utils.ApplicationProperties"%><link
	rel="stylesheet"
	href="css/jquery-ui.css" />




<%@page import="org.aspcfs.modules.opu.base.ComuniAnagrafica"%>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="OperatoreDettagli"
	class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="nome_entered" class="java.lang.String" scope="request" />
<jsp:useBean id="nome_modified" class="java.lang.String" scope="request" />
<%@ include file="../initPage.jsp"%>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
</SCRIPT>

<script>
	$(document)
			.ready(
					function() {

						$("#dialog")
								.dialog(
										{
											autoOpen : false,
											resizable : false,
											modal : true,
											title : 'Attenzione!',
											close : function() {
												// $('#opener').button('refresh');
											},
											buttons : {
												'Prosegui' : function() {

													if (!$
															.trim($(
																	"#motivazioneModificaPopup")
																	.val())) {
														$(
																"#motivazioneModificaPopup")
																.css('border',
																		'1px solid red');
														$(
																"#motivazioneModificaPopup")
																.css(
																		'margin-top',
																		'0px');
														$("#testo").css(
																'color', 'red');

														return false;
													} else {
														$(this).dialog('close');
														//   alert( $("#motivazioneModificaPopup").val());
														$(
																"#motivazioneModifica")
																.val(
																		$(
																				"#motivazioneModificaPopup")
																				.val());
														//     alert( $("#motivazioneModifica").val());

													}

													loadModalWindow(); //ATTENDERE PREGO     
													$("#eliminaAnimale")
															.submit();
												},
												'Rinuncia' : function() {
													$(this).dialog('close');
													return false;
												}
											}
										});

						$("#dialogChiusura")
								.dialog(
										{
											autoOpen : false,
											resizable : false,
											modal : true,
											title : 'Attenzione!',
											close : function() {
												// $('#opener').button('refresh');
											},
											buttons : {
												'Prosegui' : function() {

													if (!$.trim($(
																	"#motivazioneChiusuraPopup")
																	.val()) || !$.trim($(
																	"#dataChiusuraPopup")
																	.val()) ) {
														$(
																"#motivazioneChiusuraPopup")
																.css('border',
																		'1px solid red');
														$(
																"#motivazioneChiusuraPopup")
																.css(
																		'margin-top',
																		'0px');
														$("#testo").css(
																'color', 'red');
														
														$(
														"#dataChiusuraPopup")
														.css('border',
																'1px solid red');

														return false;
													} else {
														$(this).dialog('close');
														//   alert( $("#motivazioneModificaPopup").val());
														var my_data = $(
																"#dialogChiusura")
																.data('action');
														//alert (my_data);
														if (my_data == 'chiusura') {
															$(
																	"#motivazioneChiusura")
																	.val(
																			$(
																					"#motivazioneChiusuraPopup")
																					.val());

															$("#dataChiusura")
																	.val(
																			$(
																					"#dataChiusuraPopup")
																					.val());
														} else {

															$(
																	"#motivazioneRiapertura")
																	.val(
																			$(
																					"#motivazioneChiusuraPopup")
																					.val());
															$("#dataApertura")
																	.val(
																			$(
																					"#dataChiusuraPopup")
																					.val());
														}

													}

													loadModalWindow(); //ATTENDERE PREGO     
													$("#chiudiStruttura")
															.submit();
												},
												'Rinuncia' : function() {
													$(this).dialog('close');
											 		return false;
												}
											}
										});

					});

	function setValuePopUp(idrelstablp) {
		if (window.opener != null) {
			alert('scelta operatore ');
			location.href = 'AnimaleAction.do?command=ScegliOperatore&tipologiaSoggetto=1&opId='
					+ idrelstablp;
			alert('scelta operatore ritorno');
		}

	}

	function continuaChiusura() {

		if (confirm('Attenzione. Chiudendo la struttura non saranno possibili più ingressi\n Sicuro di continuare?')) {

			$('#dialogChiusura').data('action', 'chiusura').dialog('open')

		}
	}
	
	function aggiornaTaglieMassivo() 
	{
		if(document.getElementById('motivazione').value=='')
		{
			alert('Inserire una motivazione');	
			return false;
		}
		if(document.getElementById('file').value=='' || !document.getElementById('file').value.endsWith(".csv")  )
		{
			alert('Caricare un file in formato csv');	
			return false;
		} 
		
		document.aggiornaTaglieMassivoForm.submit();
		
	}
</script>

<%
	Stabilimento stab = (Stabilimento) OperatoreDettagli.getListaStabilimenti().get(0);
	LineaProduttiva linea = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
	int idLinea = linea.getId();

	String param1 = "idLinea=" + idLinea;
%>
<body onload="">

	<dhv:evaluate if="<%=!isPopup(request)%>">
		<%-- Trails --%>

		<table class="trails" cellspacing="0">
			<tr>
				<td><a href="OperatoreAction.do"><dhv:label
							name="opu.operatore"></dhv:label></a> > <%
					if (request.getParameter("return") == null) {
				%> <a href="OperatoreAction.do?command=Search"><dhv:label
							name="accounts.SearchResults">Search Results</dhv:label></a> > <%
 	} else if (request.getParameter("return").equals("dashboard")) {
 %> <a href="OperatoreAction.do?command=Dashboard"><dhv:label
							name="communications.campaign.Dashboard">Dashboard</dhv:label></a> >
					<%
 	}
 %> <dhv:label name="">SCHEDA</dhv:label></td>
			</tr>
		</table>
		<%-- End Trails --%>
	</dhv:evaluate>
	


	<dhv:container name="anagrafica" selected="details" object="OperatoreDettagli" param="<%=param1%>" appendToUrl='<%=addLinkParams(request, "popup|popupType|actionId")%>'>
		<font color="red"> <%=showError(request, "Errore")%></font>
		<dhv:evaluate if="<%=!isPopup(request)%>">
			<dhv:evaluate if="<%=stab.checkAslStabilimentoUtenteOrRoleHd(User)%>">
				<dhv:permission name="proprietari_detentori_modulo-edit">

					<dhv:evaluate
						if="<%=(linea.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia)%>">
						<a
							href="OperatoreAction.do?command=PreparaModificaDimensioneColonia&opId=<%=idLinea%>">Registrazione
							di modifica dimensione della colonia</a>
					</dhv:evaluate>
<%
		boolean tuttiRuoli = User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD1")) || 
			    User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD2")) || 
			    User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_AMMINISTRATORE_ASL")) || 
			    User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_SYSADMINASL"))|| 
			    User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_REFERENTE_ASL")) || 
			    User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_ANAGRAFE_CANINA"));

		boolean lineaProduttivaTutti = linea.getIdRelazioneAttivita() == LineaProduttiva.idAggregazionePrivato ||
									   linea.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindaco ||
									   linea.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneColonia ||
									   linea.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneSindacoFR;
		
		boolean lineaProduttivaDaSuap = linea.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile ||
										linea.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale ||
										linea.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneImportatore;
		
		boolean ruoliHD = false;
				//User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD1")) || 
			    //User.getRoleId() == new Integer(ApplicationProperties.getProperty("ID_RUOLO_HD2"));
		
		
		
%>
					<dhv:evaluate
						if="<%=(tuttiRuoli && lineaProduttivaTutti) || (ruoliHD && lineaProduttivaDaSuap)%>">
						<p>
							<input type="button"
								value="<dhv:label name="">Modifica</dhv:label>"
								onClick="javascript:window.location.href='OperatoreAction.do?command=ModifyTotale&opId=<%=idLinea%><%=addLinkParams(request, "popup|actionplan")%>';">
						</p>
					</dhv:evaluate>
					
					<dhv:permission name="aggiorna_taglie_massivo-view">
					<dhv:evaluate if="<%=linea.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile%>">
						 <form action="OperatoreAction.do?command=AggiornaTaglieMassivo" method="post" name="aggiornaTaglieMassivoForm" id="aggiornaTaglieMassivoForm" enctype="multipart/form-data">
  							<input type="hidden" name="opId" value="<%=idLinea%>">
  							<input style="display:none;" type="text" name="motivazione" value="" id="motivazione"> <font style="display:none;" color="red" id="campiObbligatori1"> *</font>
  							<input style="display:none;" type="file" name="file" id="file"> <font color="red" style="display:none;" id="campiObbligatori2"> *</font>
  							<input type="button" onclick="this.style.display='none';document.getElementById('campiObbligatori1').style.display='block';document.getElementById('campiObbligatori2').style.display='block';document.getElementById('campiObbligatori').style.display='block';document.getElementById('salva').style.display='block';document.getElementById('motivazione').style.display='block';document.getElementById('file').style.display='block';" value="Aggiorna Taglie">
  							<input style="display:none;" type="button" value="Salva" id="salva" onclick="aggiornaTaglieMassivo()"><br/>
  							 <font style="display:none;" color="red" id="campiObbligatori"> * Campi obbligatori</font>
						 </form> 
					</dhv:evaluate>	
					</dhv:permission>
					
<%
if(linea.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile )
{
%>					
					<dhv:permission name="trasferimento_animali_massivo-view">
						<input type="button" value="<dhv:label name="">Trasferimento animali massivo</dhv:label>" onClick="javascript:loadModalWindow();window.location.href='LineaProduttivaAction.do?command=PrepareTrasferimentoAnimaliMassivo&idLinea=<%=idLinea%><%=addLinkParams(request, "popup|actionplan")%>';">
					</dhv:permission>
<%
}
%>					
					
<%
		if(lineaProduttivaTutti)
		{
%>
					<dhv:permission name="anagrafe_canina_operazioni_hd-add">
						<input type="button" value="<dhv:label name="">Elimina</dhv:label>" onClick="javascript: $('#dialog').dialog('open');">

						<form name="eliminaAnimale" id="eliminaAnimale"
							action="OperatoreAction.do?command=Delete&opId=<%=idLinea%><%=addLinkParams(request, "popup|actionplan")%>"
							method="post">
							<input type="hidden" name="motivazioneModifica"
								id="motivazioneModifica" value="" />
						</form>



										
											
											
											
						<dhv:evaluate
							if="<%=((linea.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneCanile)
											|| (linea.getIdRelazioneAttivita() == LineaProduttiva.IdAggregazioneOperatoreCommerciale) || (linea
											.getIdRelazioneAttivita() == LineaProduttiva.idAggregazioneImportatore))%>">
							<dhv:evaluate if="<%=(linea.getDataFine() == null)%>">
								<input type="button"
									value="<dhv:label name="">Chiudi struttura</dhv:label>"
									onClick="javascript:continuaChiusura();">
								<form name="chiudiStruttura" id="chiudiStruttura"
									action="StabilimentoAction.do?command=ChiudiLineaProduttiva&opId=<%=idLinea%>&auto-populate=true<%=addLinkParams(request, "popup|popupType|actionId")%>"
									method="post">
									<input type="hidden" name="motivazioneChiusura"
										id="motivazioneChiusura" value="" /> <input type="hidden"
										id="dataChiusura" name="dataChiusura" value="" />
								</form>
							</dhv:evaluate>

							<dhv:evaluate if="<%=!(linea.getDataFine() == null)%>">
								<input type="button"
									value="<dhv:label name="">Riapri struttura</dhv:label>"
									onClick="javascript: $('#dialogChiusura')
								.data('action', 'riapri')
								.dialog('open');">
								<form name="chiudiStruttura" id="chiudiStruttura"
									action="StabilimentoAction.do?command=ApriLineaProduttiva&opId=<%=idLinea%>&auto-populate=true<%=addLinkParams(request, "popup|popupType|actionId")%>"
									method="post">
									<input type="hidden" name="motivazioneRiapertura"
										id="motivazioneRiapertura" value="" /> <input type="hidden"
										id="dataApertura" name="dataApertura" value="" />
								</form>
							</dhv:evaluate>
						</dhv:evaluate>
					</dhv:permission>

<%
		}
%>


					<!-- 
	<input type="button" value="<dhv:label name="">Modifica Dati Legali</dhv:label>"
			onClick="javascript:window.location.href='OperatoreAction.do?command=Modify&opId=<%=idLinea%><%=addLinkParams(request, "popup|actionplan")%>';">
	
	
	<dhv:evaluate if="<%=((linea.getIdRelazioneAttivita() != LineaProduttiva.idAggregazionePrivato)
										&& (linea.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindacoFR) && (linea
										.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneSindaco))%>">
		<input type="button" value="<dhv:label name="">Modifica Dati Stabilimento</dhv:label>"
			onClick="javascript:window.location.href='OperatoreAction.do?command=SedeOperativaModify&opId=<%=idLinea%><%=addLinkParams(request, "popup|actionplan")%>';">
	
	<input type="button" value="<dhv:label name="">Modifica Responsabile Stabilimento</dhv:label>"
			onClick="javascript:window.location.href='OperatoreAction.do?command=ResponsabileStabilimentoModify&opId=<%=idLinea%><%=addLinkParams(request, "popup|actionplan")%>';">
	</dhv:evaluate>
	
	</dhv:permission><p></p>-->

					<div id="dialog" title="" style="display: none">
						<div style="">


							<br> <span id="testo" style="color: black;"> Per
								proseguire indica una motivazione alla tua modifica </span> <br> <br>
							<textarea rows="5" cols="50" id="motivazioneModificaPopup"
								name="motivazioneModificaPopup"></textarea>
							<br> <br>
						</div>

					</div>

					<div id="dialogChiusura" title="" style="display: none">
						<div style="">


							<br> <span id="testo" style="color: black;">
							 Per proseguire indica una motivazione alla
								chiusura. </span> <br> <br>
							<textarea rows="5" cols="50" id="motivazioneChiusuraPopup"
								name="motivazioneChiusuraPopup"></textarea>
							<br> <br> <input id="dataChiusuraPopup" readonly
								type="text" name="dataChiusuraPopup" size="10" value=""
								nomecampo="dataChiusuraPopup" tipocontrollo="T2"
								labelcampo="Data Chiusura" />&nbsp; <a href="#"
								onClick="cal19.select(document.getElementById('dataChiusuraPopup'),'anchor19','dd/MM/yyyy'); return false;"
								NAME="anchor19" ID="anchor19"><img
								src="images/icons/stock_form-date-field-16.gif" border="0"
								align="absmiddle"></a><font color="red">*</font>
						</div>

					</div>



					<sc:context id="bdu">
						<dhv:permission name="anagrafe_canina-add">
							<dhv:evaluate
								if="<%=!OperatoreDettagli.checkModificaResidenzaStabilimento()%>">
								<dhv:evaluate if="<%=linea.getDataFine() == null%>">
									<%
										if (linea.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneColonia) {
									%>
									<input type="button"
										value="<dhv:label name="">Aggiungi Cane</dhv:label>"
										onClick="javascript:window.location.href='AnimaleAction.do?command=Add&idSpecie=1&origine=operatore&idLinea=<%=linea.getId()%><%=addLinkParams(request, "popup|actionplan")%>';">
									<%
										//}
									%>

									<%
										//if (linea.getIdRelazioneAttivita() != LineaProduttiva.idAggregazioneCanile) {
									%>
									<input type="button"
										value="<dhv:label name="">Aggiungi Gatto</dhv:label>"
										onClick="javascript:window.location.href='AnimaleAction.do?command=Add&idSpecie=2&idLinea=<%=linea.getId()%>&origine=operatore<%=addLinkParams(request, "popup|actionplan")%>';">

									<%
										}
									%>

									<%
										if (linea.getIdRelazioneAttivita() == 1) {
									%>
									<input type="button"
										value="<dhv:label name="">Aggiungi Furetto</dhv:label>"
										onClick="javascript:window.location.href='AnimaleAction.do?command=Add&idSpecie=3&idLinea=<%=linea.getId()%><%=addLinkParams(request, "popup|actionplan")%>';">

									<%
										}
									%>
								</dhv:evaluate>
							</dhv:evaluate>
						</dhv:permission>


					</sc:context>
			</dhv:evaluate>
		</dhv:evaluate>
		</br>
		</br>

		<sc:namecontext></sc:namecontext>
		<sc:context id="opu;gisa">
			<%@ include file="operatore_details_generic.jsp"%>

		</sc:context>
		
		<sc:context id="bdu_ext">
			<%@ include file="operatore_dettails_bdu.jsp"%>

		</sc:context>


		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong>NOTE</strong></th>
			</tr>
			<tr class="containerBody">
				<td valign="top" class="formLabel"><dhv:label name="">Note</dhv:label>
				</td>
				<td><textarea name="note" rows="3" cols="50"
						disabled="disabled"><%=toString(OperatoreDettagli.getNote())%></textarea></td>
			</tr>
		</table>
		<br>


		<dhv:permission name="anagrafe_canina-note_internal_use_only-add">
			<table cellpadding="4" cellspacing="0" border="0" width="100%"
				class="details">
				<tr>
					<th colspan="2"><strong>NOTE USO INTERNO</strong></th>
				</tr>
				<tr class="containerBody">
					<td valign="top" class="formLabel"><dhv:label name="">Note</dhv:label>
					</td>
					<td><textarea name="noteInternalUseOnly" rows="3" cols="50"
							disabled="disabled"><%=toString(linea.getNoteInternalUseOnly())%></textarea></td>
				</tr>
			</table>
			<br>
		</dhv:permission>


		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="pagedList">
			<th colspan="2">Altre informazioni</th>
			<tr class="">
				<td><strong>Inserito</strong></td>
				<td nowrap><dhv:username
						id="<%=OperatoreDettagli.getEnteredBy()%>" /> il <%=toDateasString(OperatoreDettagli.getEntered())%>&nbsp;
				</td>
			</tr>

			<tr class="">
				<td><strong>Modificato</strong></td>

				<td nowrap><dhv:username
						id="<%=OperatoreDettagli.getModifiedBy()%>" /> il <%=(OperatoreDettagli.getModified() != null) ? toDateasString(OperatoreDettagli.getModified())
						: ""%>&nbsp;</td>
			</tr>

		</table>


	</dhv:container>
</body>
