<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.hibernate.validator.util.privilegedactions.GetAnnotationParameter"%>
<%@page import="it.us.web.constants.Specie"%>
<%@page import="it.us.web.bean.vam.lookup.LookupPersonaleInterno"%>
<%@page import="it.us.web.bean.SuperUtente"%>
<%@page import="it.us.web.bean.vam.lookup.LookupTipiRichiedente"%>
<%@page import="it.us.web.bean.BUtente"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn"%>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us"%>
<%@ taglib uri="/WEB-INF/vam.tld" prefix="vam"%>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa"%>
<%@page import="java.util.Date"%>

<script language="JavaScript" type="text/javascript" src="js/vam/accettazione/add.js"></script>

<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/Test.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>


<%@page import="it.us.web.constants.SpecieAnimali"%>
<%@page import="it.us.web.constants.IdOperazioniBdr"%>
<%@page import="it.us.web.constants.IdRichiesteVarie"%>

<%@page import="it.us.web.constants.IdOperazioniDM"%>
<%@page import="it.us.web.bean.vam.Accettazione"%>
<%@page import="it.us.web.bean.vam.AnimaleAnagrafica"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAsl"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAssociazioni"%>
<%@page import="it.us.web.bean.vam.lookup.LookupOperazioniAccettazione"%>
<%@page import="it.us.web.bean.vam.lookup.LookupTipoTrasferimento"%>
<%@page import="it.us.web.bean.vam.lookup.LookupAccettazioneAttivitaEsterna"%>
<%@page import="it.us.web.bean.vam.lookup.LookupComuni"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%
	Boolean modify = (Boolean) request.getAttribute("modify");
	Accettazione acc = (Accettazione) request.getAttribute("accettazione");
	IdOperazioniBdr idopsBdr = (IdOperazioniBdr)request.getAttribute("idOpsBdr");
	IdRichiesteVarie idricVarie = (IdRichiesteVarie)request.getAttribute("idRichiesteVarie");
	IdOperazioniDM idops = (IdOperazioniDM)request.getAttribute("idOps");
	SpecieAnimali specie = (SpecieAnimali) request.getAttribute("specie");
	Date dataDecesso = (Date)request.getAttribute("dataDecesso"); 
	String dataDecessoCertezza = (String)request.getAttribute("dataDecessoCertezza");
	String dataNascitaCertezza = (String)request.getAttribute("dataNascitaCertezza");
	String flagAnagrafe = (String)request.getAttribute("flagAnagrafe");

	AnimaleAnagrafica anagraficaAnimale = (AnimaleAnagrafica)request.getAttribute("anagraficaAnimale");
	
	ArrayList<LookupTipiRichiedente> richiedenti = (ArrayList<LookupTipiRichiedente>) request.getAttribute("richiedenti");
	ArrayList<LookupTipiRichiedente> richiedentiForzaPubblica = (ArrayList<LookupTipiRichiedente>) request.getAttribute("richiedentiForzaPubblica");
	ArrayList<LookupAsl> asl = (ArrayList<LookupAsl>) request.getAttribute("asl");
	ArrayList<LookupAsl> listaAltreAsl = (ArrayList<LookupAsl>) request	.getAttribute("listaAltreAsl");
	ArrayList<LookupAssociazioni> associazioni = (ArrayList<LookupAssociazioni>) request.getAttribute("associazioni");
	ArrayList<LookupOperazioniAccettazione> operazioniInBdr = (ArrayList<LookupOperazioniAccettazione>)request.getAttribute("operazioniInBdr");
	ArrayList<LookupTipoTrasferimento> tipiTrasferimenti = (ArrayList<LookupTipoTrasferimento>)request.getAttribute("tipiTrasferimenti");
	ArrayList<LookupOperazioniAccettazione> operazioniNonBdr =(ArrayList<LookupOperazioniAccettazione>)request.getAttribute("operazioniNonBdr");
	ArrayList<LookupOperazioniAccettazione> operazioniRichPrelieviInf = (ArrayList<LookupOperazioniAccettazione>)request.getAttribute("operazioniRichPrelieviInf");
	ArrayList<LookupOperazioniAccettazione> operazioniPsADM = (ArrayList<LookupOperazioniAccettazione>)request.getAttribute("operazioniPsADM");
	ArrayList<LookupOperazioniAccettazione> operazioniPsASC = (ArrayList<LookupOperazioniAccettazione>)request.getAttribute("operazioniPsASC");
	ArrayList<LookupOperazioniAccettazione> operazioniPsDSS = (ArrayList<LookupOperazioniAccettazione>)request.getAttribute("operazioniPsDSS");
	ArrayList<LookupOperazioniAccettazione> operazioniCovid = (ArrayList<LookupOperazioniAccettazione>)request.getAttribute("operazioniCovid");
	ArrayList<LookupAccettazioneAttivitaEsterna> lookupAttivitaEsterne  = (ArrayList<LookupAccettazioneAttivitaEsterna>)request.getAttribute("lookupAttivitaEsterne");
	ArrayList<LookupComuni> lookupComuni  = (ArrayList<LookupComuni>) request.getServletContext().getAttribute("listComuni");

%>


<%
	long memFreeStart = Runtime.getRuntime().freeMemory()
			/ (1024 * 1024);
	Date dataStart = new Date();
	long memFreeEnd = Runtime.getRuntime().freeMemory() / (1024 * 1024);
	Date dataEnd = new Date();
	BUtente utente = (BUtente) request.getSession().getAttribute(
			"utente");
	long timeExecution = (dataEnd.getTime() - dataStart.getTime()) / 1000;
	System.out.println("Jsp in esecuzione: "
			+ "vam.accettazione.add(inizio)"
			+ " - "
			+ ((timeExecution >= 5) ? ("(ATTENZIONE)") : (""))
			+ "Execution time:"
			+ timeExecution
			+ "s - "
			+ "Memoria usata: "
			+ (memFreeStart - memFreeEnd)
			+ ", utente: "
			+ ((utente == null) ? ("non loggato") : (utente
					.getUsername())));
	memFreeStart = Runtime.getRuntime().freeMemory() / (1024 * 1024);
	dataStart = new Date();

%>

<script language="JavaScript" type="text/javascript" src="js/azionijavascript.js"></script>
<script>
function test(){
	var mess="";
	
	var datiRic = controllaInfo();
	if (datiRic==1) {
		mess=mess+"\n -Inserire Nome e Cognome del richiedente";
	}
	
	var dataFutura = controllaDataFutura(document.getElementById("data").value);
	if (dataFutura==1){
		mess=mess+"\n -Impossibile inserire una data futura";
	}
	return mess;
}
</script>

<c:if test="<%=!modify%>">
	<form name="form1" action="vam.accettazione.Add.us?flagAnagrafe=<%=flagAnagrafe%>"
		method="post">
		<input type="submit" value="Salva"
			onclick="var mess=test(); if(mess==''){if(myConfirm('Si conferma la data accettazione ' + document.getElementById('data').value + '?')){return checkform('<%=utente.getId()%>','<%=acc.getAnimale().getId()%>','<%=utente.getClinica().getId() %>', <%=utente.getRuolo().equals("Referente Asl") || utente.getRuolo().equals("14")%>,false)}else{ return false;}}else{alert(mess); return false;}" />
		<c:if test="<%=flagAnagrafe==null%>">
			<input type="button" value="Annulla"
				onclick="if(myConfirm('Sicuro di voler annullare l\'inserimento dell\'accettazione?')){ location.href='vam.accettazione.FindAnimale.us?identificativo=<%=acc.getAnimale().getIdentificativo()%>' }" />
		</c:if>
</c:if>
<c:if test="<%=modify%>">
	<form name="form2" action="vam.accettazione.Add.us" method="post">
		<input type="submit" value="Salva Modifiche"
			onclick="var mess=test(); 
			         if(mess=='')
			         {
			         	if(myConfirm('Si conferma la data accettazione ' + document.getElementById('data').value + '?'))
			         	{
			         		if(checkform('<%=utente.getId()%>','<%=acc.getAnimale().getId()%>','<%=utente.getClinica().getId() %>', <%=utente.getRuolo().equals("Referente Asl") || utente.getRuolo().equals("14")%>,false))
			         		{
			         			document.form2.submit();
			         		}
			         		else
			         		{
			         			return false;
			         		}
			         	}
			         	else
			         	{ 
			         		return false;
			         	}
			         }
			         else
			         {
			         	alert(mess); 
			         	return false;
			         }" />
		<input type="button" value="Annulla"
			onclick="if( myConfirm('Sicuro di voler annullare le modifiche apportate?') ){ location.href='vam.accettazione.Detail.us?id=<%=acc.getId()%>' }" />
		<input type="hidden" value="<%=acc.getId()%>" name="id" /> <input
			type="hidden" value="true" name="modify" /> <input type="hidden"
			name="progressivo" value="<%=acc.getProgressivo()%>" />
</c:if>

<fieldset>
	<legend>
		Accettazione <%=acc.getAnimale().getLookupSpecie().getDescription()%>
		"<%=acc.getAnimale().getIdentificativo()%>" del <input type="text"
			<c:if test="<%=modify%>"> value="<fmt:formatDate type="date" value="<%=acc.getData()%>" pattern="dd/MM/yyyy" />" </c:if>
			<c:if test="<%=!modify%>"> value="<fmt:formatDate type="date" value="<%=new Date()%>" pattern="dd/MM/yyyy" />" </c:if>
			id="data" name="data" maxlength="10" size="10" readonly="readonly" />
		<img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
		<script type="text/javascript">
					Calendar.setup({
						inputField  :    "data",      // id of the input field
						ifFormat    :    "%d/%m/%Y",  // format of the input field
						button      :    "id_img_1",  // trigger for the calendar (button ID)
						// align    :    "rl,      // alignment (defaults to "Bl")
						singleClick :    true,
						timeFormat	:   "24",
						showsTime	:   false
					});					    

				</script>
	</legend>
	<table class="tabella" style="width: 99%">
		<tr>
			<th><%=acc.getAnimale().getLookupSpecie().getDescription()%></th>
			<!--c:if test="${!animale.decedutoNonAnagrafe }" -->

			<c:choose>
				<c:when test="<%=acc.getAnimale().getLookupSpecie().getId()==specie.getSinantropo()%>">
					<th>Detentore</th>
				</c:when>
				<c:otherwise>
					<c:set var="proprietarioCognome"
						value="<%=acc.getProprietarioCognome()%>" />
					<c:choose>
						<c:when test="<%=acc.getProprietarioCognome()!=null && acc.getProprietarioCognome().startsWith(\"<b>\") %>">
							<th>Colonia</th>
						</c:when>
						<c:otherwise>
							<th>Proprietario <c:if test="<%=acc.getProprietarioTipo()!=null && !acc.getProprietarioTipo().equals(\"null\")%>"><%=acc.getProprietarioTipo()%></c:if></th>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<!-- /c:if -->
		</tr>
		<tr>
			<td style="vertical-align: top;">
				<ul>
					<li><b>Identificativo:</b> <%=acc.getAnimale().getIdentificativo() %></li>
					<li><b>Tatuaggio/II MC:</b> <%=(acc.getAnimale().getTatuaggio() != null) ? acc.getAnimale().getTatuaggio() : "---" %></li>
					<c:choose>
						<c:when test="<%=(acc.getAnimale().getLookupSpecie().getId()==3) %>">
							<li><b>Et&agrave;:</b> 
								<fmt:formatDate type="date" value="<%=acc.getAnimale().getDataNascita()%>" pattern="dd/MM/yyyy" />
<%
  								if(acc.getAnimale().getEta()!=null && acc.getAnimale().getEta().getDescription()!=null)
  								{
%>
	  								<%=acc.getAnimale().getEta().getDescription() %>
<%
								}
%>
						</c:when>
						<c:otherwise>
							<li><b>Data nascita:</b> <fmt:formatDate type="date"
									value="<%=acc.getAnimale().getDataNascita()%>" pattern="dd/MM/yyyy" /> 
								<%if(acc.getAnimale().getDataNascita()!=null){%>- <%=dataNascitaCertezza%><%} %></li>
				</c:otherwise>
					</c:choose>
					<c:if test="<%=dataDecesso!=null%>">
						<li><b>Data Decesso:</b> <fmt:formatDate type="date"
								value="<%=dataDecesso %>" pattern="dd/MM/yyyy" />
							<%if(dataDecesso!=null && !dataDecesso.equals("")){%>- <%=dataDecessoCertezza %><%}%></li>
					</c:if>
					<c:if test="<%=acc.getAnimale().getCausaMorte().getDescription()!=null%>">
						<li><b>Probabile causa del Decesso:</b>
							<%=acc.getAnimale().getCausaMorte().getDescription() %></li>
					</c:if>

			<!-- 		<c:set scope="request" var="anagraficaAnimale"
						value="${anagraficaAnimale}" />  -->
					<c:import url="../vam/accettazione/anagraficaAnimaleAdd.jsp" />

					<c:if test="<%=acc.getAnimale().getClinicaChippatura()!=null %>">
						<li><b>Microchippato nella clinica
								<%=acc.getAnimale().getClinicaChippatura().getNome() %></b></li>
					</c:if>
				</ul>
			</td>
			<!--  c:if test="${!animale.decedutoNonAnagrafe }" -->
			<td style="vertical-align: top;">
				<ul>
					<c:choose>
						<c:when test="<%=(acc.getProprietarioCognome()!=null && acc.getProprietarioCognome().startsWith(\"<b>\"))%>">
							<li><%=acc.getProprietarioCognome()%></li>
							<li><b>Indirizzo:</b> <%=acc.getProprietarioIndirizzo()%>, <%=acc.getProprietarioComune()%>
								<c:if test="<%=acc.getProprietarioProvincia()!=null && !acc.getProprietarioProvincia().equals(\"\")%>">
												(<%=acc.getProprietarioProvincia()%>)						
											</c:if> <c:if test="<%=acc.getProprietarioCap()!=null && !acc.getProprietarioCap().equals(\"\")%>">
												- <%=acc.getProprietarioCap()%>
								</c:if></li>
							<li><b>Nominativo Referente:</b> <%=acc.getProprietarioNome()%></li>
							<li><b>Codice fiscale Referente:</b><% if (acc.getProprietarioCodiceFiscale()!=null){%><%=acc.getProprietarioCodiceFiscale()%><%} %></li>
							<li><b>Documento Identità Referente:</b><% if (acc.getProprietarioDocumento()!=null){%><%=acc.getProprietarioDocumento()%><%} %></li>
							<li><b>Telefono Referente:</b><% if (acc.getProprietarioTelefono()!=null){%><%=acc.getProprietarioTelefono()%><%} %></li>
						</c:when>
						
						<c:when test="<%=acc.getProprietarioCognome()!=null && acc.getProprietarioTipo()!=null && ( acc.getProprietarioTipo().equals(\"Importatore\") || acc.getProprietarioTipo().equals(\"Operatore Commerciale\"))%>">
							<li><b>Ragione Sociale: </b><% if (acc.getProprietarioNome()!=null){%><%=acc.getProprietarioNome()%><%} %></li>
							<li><b>Partita Iva: </b><% if (acc.getProprietarioCognome()!=null){%><%=acc.getProprietarioCognome()%><%} %></li>
							<li><b>Rappr. Legale: </b><% if (acc.getProprietarioCodiceFiscale()!=null){%><%=acc.getProprietarioCodiceFiscale()%><%} %></li>
							<li><b>Telefono struttura(principale): </b><% if (acc.getProprietarioTelefono()!=null){%><%=acc.getProprietarioTelefono()%><%} %></li>
							<li><b>Telefono struttura(secondario): </b><% if (acc.getProprietarioDocumento()!=null){%><%=acc.getProprietarioDocumento()%><%} %></li>
							<u>Sede operativa</u>
							<li><b>Indirizzo: </b><% if (acc.getProprietarioIndirizzo()!=null){%><%=acc.getProprietarioIndirizzo()%><%} %></li>
							<li><b>CAP: </b><% if (acc.getProprietarioCap()!=null){%><%=acc.getProprietarioCap()%><%} %></li>
							<li><b>Comune: </b><% if (acc.getProprietarioComune()!=null){%><%=acc.getProprietarioComune()%><%} %></li>
							<li><b>Provincia: </b><% if (acc.getProprietarioProvincia()!=null){%><%=acc.getProprietarioProvincia()%><%} %></li>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="<%=acc.getProprietarioTipo()!=null && acc.getProprietarioTipo().equals(\"Canile\") %>">
									<li><b>Ragione Sociale: </b><%if (acc.getProprietarioNome()!=null){%><%=acc.getProprietarioNome() %><%} %></li>
									<li><b>Partita Iva: </b><% if (acc.getProprietarioCognome()!=null){%><%=acc.getProprietarioCognome()%><%} %></li>
									<li><b>Rappr. Legale: </b><% if (acc.getProprietarioCodiceFiscale()!=null){%><%=acc.getProprietarioCodiceFiscale()%><%} %></li>
								</c:when>
								<c:when test="<%=acc.getProprietarioTipo()!=null && !acc.getProprietarioTipo().equals(\"Canile\") %>">
										<c:if test="<%=!acc.getRandagio()%>">
											<li><b>Cognome: </b><%if (acc.getProprietarioCognome()!=null && !acc.getProprietarioCognome().equals("null")){%><%=acc.getProprietarioCognome()%><%} %></li>
										</c:if>
										<li><b>Nome: </b><%if (acc.getProprietarioNome()!=null && !acc.getProprietarioNome().equals("null")){%><%=acc.getProprietarioNome() %><%} %></li>
								</c:when>	
								<c:when test="<%=acc.getProprietarioTipo()==null && acc.getRandagio()%>">
									<li><b>Nome: </b><%=request.getParameter("proprietarioNome")%></li>
								</c:when>
								<c:otherwise>
									<li><b>Cognome: </b><%if (acc.getProprietarioCognome()!=null){%><%=acc.getProprietarioCognome()%><%} %></li>
									<li><b>Nome: </b><%if (acc.getProprietarioNome()!=null){%><%=acc.getProprietarioNome() %><%} %></li>
								</c:otherwise>
							</c:choose>
							<c:if test="<%=!acc.getRandagio()%>">
							<c:if test="<%=acc.getProprietarioTipo()!=null && !acc.getProprietarioTipo().equals(\"Canile\") %>">
								<li><b>Codice Fiscale:</b> <% if (acc.getProprietarioCodiceFiscale()!=null && !acc.getProprietarioCodiceFiscale().equals("null")){%><%=acc.getProprietarioCodiceFiscale()%><%} %></li></c:if>
								<li><b>Documento:</b> <% if (acc.getProprietarioDocumento()!=null && !acc.getProprietarioDocumento().equals("null")){%><%=acc.getProprietarioDocumento()%><%} %></li>
								<li><b>Indirizzo:</b> <% if (acc.getProprietarioIndirizzo()!=null && !acc.getProprietarioIndirizzo().equals("null")){%><%=acc.getProprietarioIndirizzo()%><%} %></li>
								<li><b>CAP:</b> <% if (acc.getProprietarioCap()!=null && !acc.getProprietarioCap().equals("null")){%><%=acc.getProprietarioCap()%><%} %></li>
								<li><b>Comune:</b> <% if (acc.getProprietarioComune()!=null && !acc.getProprietarioComune().equals("null")){%><%=acc.getProprietarioComune()%><%} %></li>
								<li><b>Provincia:</b><% if (acc.getProprietarioProvincia()!=null && !acc.getProprietarioProvincia().equals("null")){%><%=acc.getProprietarioProvincia()%><%} %></li>
								<li><b>Telefono:</b> <% if (acc.getProprietarioTelefono()!=null && !acc.getProprietarioTelefono().equals("null")){%><%=acc.getProprietarioTelefono()%><%} %></li>
							</c:if>
						</c:otherwise>
					</c:choose>
				</ul>
			</td>
			<!--  /c:if-->
		</tr>
	</table>
	
	
	<input type="hidden" name="comuneIndirizzoAttEsternaEdit" value="<%=acc.getAnimale().getComuneRitrovamento()==null %>" /> 
	<input type="hidden" name="idAnimale"
		value="<%=acc.getAnimale().getId()%>" /> <input type="hidden"
		name="proprietarioCognome" value="${accettazione.proprietarioCognome}" />
	<input type="hidden" name="proprietarioTipo"
		<c:if test="${accettazione.proprietarioTipo!=null}"> value="${accettazione.proprietarioTipo}"</c:if>
		<c:if test="${accettazione.proprietarioTipo==null}"> value=<%=request.getParameter("proprietarioTipo")%></c:if> /> <input type="hidden"
		name="proprietarioNome" value="${accettazione.proprietarioNome}" /> <input
		type="hidden" name="proprietarioCodiceFiscale"
		value="${accettazione.proprietarioCodiceFiscale}" /> <input
		type="hidden" name="proprietarioDocumento"
		value="${accettazione.proprietarioDocumento}" /> <input type="hidden"
		name="proprietarioIndirizzo"
		value="${accettazione.proprietarioIndirizzo}" /> <input type="hidden"
		name="proprietarioCap" value="${accettazione.proprietarioCap}" /> <input
		type="hidden" name="proprietarioComune"
		value="${accettazione.proprietarioComune}" /> <input type="hidden"
		name="proprietarioProvincia"
		value="${accettazione.proprietarioProvincia}" /> <input type="hidden"
		name="proprietarioTelefono" value="${accettazione.proprietarioTelefono}" />
	<input type="hidden" name="randagio" value="<%=acc.getRandagio()%>" />
	<input type="hidden" name="sterilizzato"
		value="<%=acc.getSterilizzato()%>" />
</fieldset>

<fieldset>
	<legend>Dati Richiedente</legend>

	<fieldset>
		<legend>
			Tipo richiedente <select id="tipoRichiedente" name="tipoRichiedente"
				onchange="document.getElementById('idDipendenteAsl').value='';document.getElementById('idDipendenteAslTemp').value='';
			  document.getElementById('personaleAslSelezionato').innerHTML='';document.getElementById('personaleAslSelezionatoTemp').innerHTML='';
			  selezionaDivRichiedente(this.value);abilitazioneOperazione('<c:out value="${idTipiRichiedente.associazione}"/>','<c:out value="<%=idopsBdr.getRitrovamento()%>"/>',this)">

				<%
			LookupTipiRichiedente ltr = new LookupTipiRichiedente();
			Iterator i = richiedenti.iterator();
			while (i.hasNext()) {
				ltr = (LookupTipiRichiedente) i.next();
		%>
				<option value="<%=ltr.getId()%>"
					<c:if test="<%=(acc.getLookupTipiRichiedente()!=null && ltr.getId() == acc.getLookupTipiRichiedente().getId())%>">selected="selected"</c:if>><%=ltr.getDescription()%>
				</option>
				<%
		}
	%>


				<optgroup label="Altre Autorità">

					<%
				ltr = new LookupTipiRichiedente();
				i = richiedentiForzaPubblica.iterator();
				while (i.hasNext()) {
					ltr = (LookupTipiRichiedente) i.next();
			%>
					<option value="<%=ltr.getId()%>"
						<c:if test="<%=(ltr == acc.getLookupTipiRichiedente())%>">selected="selected"</c:if>><%=ltr.getDescription()%>
					</option>
					<%
			}
		%>
				</optgroup>
			</select>
		</legend>

		<div id="div_richiedente_privato" style="display: block">
			<c:if test="<%=!acc.getAnimale().getDecedutoNonAnagrafe()%>">
				<input type="checkbox" name="richiedenteProprietario"
					id="richiedenteProprietario"
					<c:if test="<%=(modify == false || acc.getRichiedenteProprietario() == true)%>">checked="checked"</c:if>
					onchange="toggleDiv('div_richiedente_privato_non_proprietario')" />
				<label for="richiedenteProprietario">Coincide con il <c:choose>
						<c:when
							test="<%=acc.getAnimale().getLookupSpecie().getId() == specie.getSinantropo()%>">
							<th>detentore</th>
						</c:when>
						<c:otherwise>
							<th>proprietario</th>
						</c:otherwise>
					</c:choose>
				</label>
			</c:if>
			<div id="div_richiedente_privato_non_proprietario"
				style="display: none;"><%@include
					file="form_richiedente_include.jsp"%></div>

			<c:if
				test="<%=(modify == true && acc.getRichiedenteProprietario() == false)%>">
				<script type="text/javascript">
					toggleDiv('div_richiedente_privato_non_proprietario');
				</script>
			</c:if>
		</div>

		<div id="div_richiedente_personale_asl" style="display: none;">
			Asl <font color="red">*</font> <select id="idRichiedenteAsl"
				name="idRichiedenteAsl" disabled="disabled"
				onchange="document.getElementById('idDipendenteAsl').value='';document.getElementById('idDipendenteAslTemp').value='';
			  document.getElementById('personaleAslSelezionato').innerHTML='';document.getElementById('personaleAslSelezionatoTemp').innerHTML='';">
				<option value="-1">- Seleziona -</option>
				<%
		LookupAsl la = new LookupAsl();
		i = asl.iterator();
		while (i.hasNext()) {
			la = (LookupAsl) i.next();
	%>
				<option value="<%=la.getId()%>"
					<c:if test="<%=(acc.getRichiedenteAsl()!=null && (la.getId() == acc.getRichiedenteAsl().getId()))%>">selected="selected"</c:if>><%=la.getDescription()%>
				</option>
				<%
		}
	%>
			</select> <a id="linkChoosePersAsl"
				style="cursor: pointer; text-decoration: underline; color: blue;"
				onclick="selezionaPersAsl();">Seleziona</a> <input type="hidden"
				name="idDipendenteAsl" id="idDipendenteAsl"
				value="<%=acc.getPersonaleAslId()%>" /> <input type="hidden"
				name="idDipendenteAslTemp" id="idDipendenteAslTemp"
				value="<%=acc.getPersonaleAslId()%>" />

<!-- VEDERE -->
<%
	Iterator<LookupAsl> asl2 = ((ArrayList<LookupAsl>)request.getAttribute("asl")).iterator();
	while(asl2.hasNext())
	{
		LookupAsl temp = asl2.next();
%>
	<div id="operatori_asl_<%=temp.getId()%>" style="display: none;">
		<table class="tabella" id="tabellaPersAsl">
		<tr>
	   		<th colspan="3">
			</th>
		</tr>
			<c:set var="aslId" value="<%=temp.getId()%>"/>
			<c:forEach items="<%=(ArrayList<BUtente>)request.getAttribute("personaleAsl"+temp.getId()) %>" var="utente">
				<tr class="${i % 2 == 0 ? 'odd' : 'even'}">
					<td colspan="3">
						<input type="checkbox" value="${utente.id}"  
											   id="persAsl${utente.id}"  
											   name="persAsl${utente}"
									   		   onclick="popolaPersAslSelezionati('${utente.id}','${utente}');"
							<us:contain collection="${accettazione.personaleAsl}" item="<%=(ArrayList<BUtente>)request.getAttribute("personaleAsl"+temp.getId()) %>">
								checked="checked"
							</us:contain>	
						/>
						${utente}
					</td>
				</tr>
				<c:set var="i" value='${i+1}'/>
			</c:forEach>
		</table>
	</div>
<%
	}
%>
<!--  -->

		</div>

		<table id="personaleAslSelezionato">
			<%
		i = acc.getPersonaleAsl().iterator();
		SuperUtente s = new SuperUtente();
		while (i.hasNext()) {
			s = (SuperUtente) i.next();
	%>
			<tr>
				<td><%=s%></td>
			</tr>
			<%
		}
	%>
		</table>

		<table id="personaleAslSelezionatoTemp" style="display: none;">
			<%
		i = acc.getPersonaleAsl().iterator();
		s = new SuperUtente();
		while (i.hasNext()) {
			s = (SuperUtente) i.next();
	%>
			<tr>
				<td><%=s%></td>
			</tr>
			<%
		}
	%>
		</table>

		<div id="div_richiedente_associazione" style="display: none;">
			Nome Associazione <font color="red">*</font> <select
				name="idAssociazione" id="idAssociazione" disabled="disabled">
				<option value="-1">- Seleziona -</option>
				<%
		LookupAssociazioni lass = new LookupAssociazioni();
		i = associazioni.iterator();
		while (i.hasNext()) {
			lass = (LookupAssociazioni) i.next();
	%>
				<option value="<%=lass.getId()%>"
					<c:if test="<%=(acc.getRichiedenteAssociazione()!=null && (lass.getId() == acc.getRichiedenteAssociazione().getId()) )%>">selected="selected"</c:if>><%=lass.getDescription()%>
				</option>
				<%
		}
	%>

			</select>
		</div>

		<div id="div_richiedente_forza_pubblica" style="display: none;">
			<table class="tabella" style="width: 99%">
				<tr>
					<th colspan="3">Dati Comando</th>
				</tr>
				<tr align="right">
					<td width="33%">Comune <input
					<% if (acc.getRichiedenteForzaPubblicaComune()!=null) {%>
						value="<%=acc.getRichiedenteForzaPubblicaComune()%>"<%} else {%>value=""<%} %>
						type="text" name="richiedenteForzaPubblicaComune" 
						maxlength="64" disabled="disabled"></td>
					<td width="33%">Provincia <input
						<% if (acc.getRichiedenteForzaPubblicaProvincia()!=null) {%>
						value="<%=acc.getRichiedenteForzaPubblicaProvincia()%>"<%} else {%>value=""<%} %>
						type="text" name="richiedenteForzaPubblicaProvincia"
						maxlength="64" disabled="disabled"></td>
					<td width="33%">&nbsp;</td>
				</tr>
				<tr align="right">
					<td width="33%">Comando <input
					<% if (acc.getRichiedenteForzaPubblicaComando()!=null) {%>
						value="<%=acc.getRichiedenteForzaPubblicaComando()%>"<%} else {%>value=""<%} %>
						type="text"	name="richiedenteForzaPubblicaComando" 
						maxlength="64"	disabled="disabled"></td>
					<td width="33%"></td>
					<td width="33%">&nbsp;</td>
				</tr>
			</table>
		</div>

		<div id="div_richiedente_altro" style="display: none;">
			Specificare tipologia richiedente <input 
			<% if (acc.getRichiedenteAltro()!=null) {%>
				value="<%=acc.getRichiedenteAltro()%>"<%} else {%>value=""<%} %> type="text"
				name="richiedenteAltro" maxlength="64" />
		</div>

	</fieldset>

	<fieldset>
		<legend>
			<input type="checkbox" name="interventoPersonaleInterno"
				id="interventoPersonaleInterno"
				onchange="document.getElementById('personaleInternoSelezionato').innerHTML='';document.getElementById('personaleInternoSelezionatoTemp').innerHTML='';
		   			 document.getElementById('idRichiedenteInterno').value='';document.getElementById('idRichiedenteInternoTemp').value='';"
				<c:if test="<%=(modify == true && !acc.getPersonaleInterno()
						.isEmpty())%>">
		checked="checked"
	</c:if>>
			Intervento Personale Interno
		</legend>
		<a id="linkChoosePersInt1"
			style="cursor: pointer; text-decoration: underline; color: blue;"
			onclick="selezionaPersInt();">Seleziona</a> <input type="hidden"
			name="idRichiedenteInterno" id="idRichiedenteInterno"
			value="<%=acc.getPersonaleInterno()%>" /> <input type="hidden"
			name="idRichiedenteInternoTemp" id="idRichiedenteInternoTemp"
			value="<%=acc.getPersonaleInterno()%>" />

		<div id="div_richiedente_personale_interno" style="display: none;">
			<!--  select id="idPersonaleInterno" multiple="multiple" size="10" name="idRichiedenteInterno"-->
			<table class="tabella" id="tabellaPersInt">
				<tr>
					<th colspan="3"></th>
				</tr>

				<%
		LookupPersonaleInterno lpi = new LookupPersonaleInterno();
		ArrayList<LookupPersonaleInterno> personaleInterno = (ArrayList<LookupPersonaleInterno>)request.getAttribute("personaleInterno");
		i = personaleInterno.iterator();
		while (i.hasNext()) {
			lpi = (LookupPersonaleInterno) i.next();%>
				<c:if test="<%=lpi.getEnabled() == true%>">
					<tr class="${i % 2 == 0 ? 'odd' : 'even'}"
						id="trTuttiEsiti${i}_${organoTipoEsito.id}">
						<td colspan="3"><input type="checkbox"
							value="<%=lpi.getId()%>" id="persInt<%=lpi.getId()%>"
							name="persInt<%=lpi.getNominativo()%>"
							onclick="popolaPersIntSelezionati('<%=lpi.getId()%>','<%=lpi.getNominativo()%>')"
							<us:contain collection="${accettazione.personaleInterno}" item="<%=lpi%>">
								checked="checked"
							</us:contain> />
							<%=lpi.getNominativo()%></td>
					</tr>
					<c:set var="i" value='${i+1}' />
				</c:if>

				<%  }	  %>
			</table>

			<!--/select-->
		</div>


		<table id="personaleInternoSelezionato">
			<%
		i = acc.getPersonaleInterno().iterator();
	 	lpi = new LookupPersonaleInterno();
		while (i.hasNext()) {
			lpi = (LookupPersonaleInterno) i.next();
	%>
			<tr>
				<td><%=lpi.getNominativo()%></td>
			</tr>
			<%
		}
	%>
		</table>
		<table id="personaleInternoSelezionatoTemp" style="display: none;">
			<%
		i = acc.getPersonaleInterno().iterator();
	 	lpi = new LookupPersonaleInterno();
		while (i.hasNext()) {
			lpi = (LookupPersonaleInterno) i.next();
	%>
			<tr>
				<td><%=lpi.getNominativo()%></td>
			</tr>
			<%
		}
	%>
		</table>
	</fieldset>

	<c:if
		test="<%=(modify == true && !acc.getPersonaleInterno().isEmpty())%>">
		<script type="text/javascript">
		document.getElementById('idRichiedenteInterno').value = document.getElementById('idRichiedenteInterno').value.replace("[","");
		document.getElementById('idRichiedenteInterno').value = document.getElementById('idRichiedenteInterno').value.replace("]","");
		while(document.getElementById('idRichiedenteInterno').value.indexOf(" ")>0)
		{
			document.getElementById('idRichiedenteInterno').value = document.getElementById('idRichiedenteInterno').value.replace(" ","");
		}
		document.getElementById('idRichiedenteInternoTemp').value = document.getElementById('idRichiedenteInternoTemp').value.replace("[","");
		document.getElementById('idRichiedenteInternoTemp').value = document.getElementById('idRichiedenteInternoTemp').value.replace("]","");
		while(document.getElementById('idRichiedenteInternoTemp').value.indexOf(" ")>0)
		{
			document.getElementById('idRichiedenteInternoTemp').value = document.getElementById('idRichiedenteInternoTemp').value.replace(" ","");
		}
	</script>
	</c:if>

	<c:if test="<%=(modify == true && !acc.getPersonaleAsl().isEmpty())%>">
		<script type="text/javascript">
		document.getElementById('idDipendenteAsl').value = document.getElementById('idDipendenteAsl').value.replace("[","");
		document.getElementById('idDipendenteAsl').value = document.getElementById('idDipendenteAsl').value.replace("]","");
		while(document.getElementById('idDipendenteAsl').value.indexOf(" ")>0)
		{
			document.getElementById('idDipendenteAsl').value = document.getElementById('idDipendenteAsl').value.replace(" ","");
		}
		document.getElementById('idDipendenteAslTemp').value = document.getElementById('idDipendenteAslTemp').value.replace("[","");
		document.getElementById('idDipendenteAslTemp').value = document.getElementById('idDipendenteAslTemp').value.replace("]","");
		while(document.getElementById('idDipendenteAslTemp').value.indexOf(" ")>0)
		{
			document.getElementById('idDipendenteAslTemp').value = document.getElementById('idDipendenteAslTemp').value.replace(" ","");
		}
</script>
	</c:if>

	<c:if test="<%=!modify%>">
		<script type="text/javascript">
		document.getElementById('idRichiedenteInterno').value = "";
		document.getElementById('idRichiedenteInternoTemp').value = "";
		document.getElementById('idDipendenteAsl').value = "";
		document.getElementById('idDipendenteAslTemp').value = "";
	</script>
	</c:if>

	<!--  ASL interna (dati in organigramma);
		ASL esterna (dati in organigramma);
		Privato (Nome, cognome, documento, codice fiscale), con un flag che identifica se è un proprietario;
		Altre Autorità  (Nome, cognome, documento, codice fiscale);
		Questura, 
		Polizia, 
		Carabinieri, 
		Vigili del fuoco, 
		Pubblica sicurezza, 
		Polizia Municipale
		Corpo forestale
		Associazioni (Nome Associazione (anagrafate in VAM), Nome, cognome, documento, codice fiscale).
		Altro, specificare cosa -->
</fieldset>

<fieldset>
	<legend>
		Motivazioni/Operazioni Richieste <font color="red">*</font>
	</legend>
	<c:if test="<%=!acc.getAnimale().getDecedutoNonAnagrafe()%>">

		<table class="tabella" style="width: 99%">
			<tr valign="top">
				<td>
					<table width="100%">
						<tr>
							<th>Anagrafi Regionali</th>
						</tr>
						<%
						    i = operazioniInBdr.iterator();
							LookupOperazioniAccettazione loa = new LookupOperazioniAccettazione();
							while (i.hasNext()){
								loa=(LookupOperazioniAccettazione)i.next(); %>
								<tr>
								<td><c:if test="<%=(loa.getId()!= idopsBdr.getIscrizione() && loa.getId()!=idopsBdr.getPrelievoDna()&& loa.getId()!=idopsBdr.getPrelievoLeishmania())%>">
										<%-- Se non è "iscrizione in anagrafe" --%>
										<input onchange="popolaOpSelezionate('<%=loa.getId()%>')"
											<c:if test="<%=loa.getId()==idopsBdr.getDecesso()%>">
								   				title="Operazione da effettuare in Vam solo in presenza dell'animale"
								   			</c:if>
											<c:if test="<%=!loa.getEnabledInPage()%>">
								   				disabled="disabled"
								   				title="Operazione senza animale: la registrazione deve essere effettuata esclusivamente in BDU"
								   			</c:if>
											type="checkbox" name="op_<%=loa.getId()%>" id="op_<%=loa.getId()%>"
											<c:if test="<%=loa.getId() == idopsBdr.getDecesso()%>">
								   				onclick="abilitaDisabilitaProntoSoccorso(this)"
								   			</c:if>
											<c:if test="<%=loa.getId() == idopsBdr.getTrasferimento()%>">
								   				onclick="abilitaDisabilitaTipoTrasferimento(this)"
								   			</c:if>
											<c:if test="<%=loa.getVersoAssocCanili()!=null && loa.getVersoAssocCanili() && (loa.getIntraFuoriAsl()==null ||  !loa.getIntraFuoriAsl())%>">
												onclick="abilitaDisabilitaVersoAssocCanili(this)"
											</c:if>
											<c:if test="<%=loa.getIntraFuoriAsl()!=null && loa.getIntraFuoriAsl() && (loa.getVersoAssocCanili()==null ||  !loa.getVersoAssocCanili())%>">
												onclick="abilitaDisabilitaIntraFuoriAsl(this)"
											</c:if>
											<c:if test="<%=loa.getIntraFuoriAsl()!=null && loa.getIntraFuoriAsl() && loa.getVersoAssocCanili()!=null && loa.getVersoAssocCanili()%>">
												onclick="abilitaDisabilitaIntraFuoriAsl(this), abilitaDisabilitaVersoAssocCanili(this)"
											</c:if>
											<us:contain collection="${operazioniRichiesteId }" item="<%=loa.getId()%>" >
								   				checked="checked"
								   			</us:contain> />
								   			
								   			
										<input type="hidden" id="opOriginalDisabled_<%=loa.getId()%>"
											name="opOriginalDisabled_<%=loa.getId()%>"></input>
										<label for="op_<%=loa.getId()%>"><%=loa.getDescription()%></label>
										
										<c:if test="<%=loa.getIntraFuoriAsl()!=null &&  loa.getIntraFuoriAsl()%>">
											<input type="checkbox" id="intraFuoriAsl" name="intraFuoriAsl" disabled="disabled"
												<us:contain collection="${operazioniRichiesteId }" item="<%=loa.getId()%>" >
													disabled=false
												</us:contain>
												<c:if test="<%=acc.getAdozioneFuoriAsl()!=null && acc.getAdozioneFuoriAsl()%>">
													checked="checked"
												</c:if>
											/> fuori Asl
										</c:if>
										<c:if test="<%=loa.getVersoAssocCanili()!=null &&  loa.getVersoAssocCanili() && (utente.getRuoloByTalos().equals("HD1") || utente.getRuoloByTalos().equals("HD2") || utente.getRuoloByTalos().equals("17") || utente.getRuoloByTalos().equals("16")  )%>">
											<input type="checkbox" id="versoAssocCanili" name="versoAssocCanili" disabled="disabled"
												<us:contain collection="${operazioniRichiesteId }" item="<%=loa.getId()%>" >
													disabled=false
												</us:contain>
												<c:if test="<%=acc.getAdozioneVersoAssocCanili()!=null && acc.getAdozioneVersoAssocCanili()%>">
													checked="checked"
												</c:if>
											/> verso Associazioni/Canili
										</c:if>
									
										<c:if test="<%=loa.getId()==idopsBdr.getTrasferimento()%>">
												<select id="idTipoTrasferimento" name="idTipoTrasferimento"
												disabled="disabled"
												<us:contain collection="${operazioniRichiesteId }" item="<%=loa.getId()%>" >
													checked="checked"
												</us:contain>>
												<option value="">- Seleziona -</option>
												    <% LookupTipoTrasferimento ltt = new LookupTipoTrasferimento();
												       Iterator j = tipiTrasferimenti.iterator();
												       while (j.hasNext()){
												    	   ltt = (LookupTipoTrasferimento)j.next();
												    	   if(ltt.getId()>2 || !anagraficaAnimale.getStatoAttuale().toLowerCase().contains("randagio"))
												    	   {									%> 
												    	   <option value="<%=ltt.getId()%>"
															<c:if test="<%=(acc.getTipoTrasferimento()!=null && (ltt.getId()==acc.getTipoTrasferimento().getId())) %>">
															selected="selected"
															</c:if>
															><%=ltt.getDescription()%></option>
												    <% }}%>
												</select>
										</c:if>
									</c:if>
										
						
						<c:if test="<%=loa.getId()==idopsBdr.getIscrizione()%>">
							<%-- per "iscrizione in anagrafe" non faccio scegliere all'utente se selezionare o meno --%>
							<input type="hidden" id="op_<%=loa.getId() %>" name="op_<%=loa.getId() %>" value="<%=flagAnagrafe%>"/>
							<input type="checkbox" disabled=false
								<c:if test="<%=flagAnagrafe!=null %>">
									checked="checked"
								</c:if>/>
								<%=loa.getDescription() %>		
						</c:if>
						<%	} %>
						</td>
						</tr>
					</table>
				</td>
				
				<td>
					<table width="100%">
						<tr>
							<th>Richieste Varie</th>
						</tr>
						<% i = operazioniNonBdr.iterator();
							while(i.hasNext()){
							    loa = (LookupOperazioniAccettazione)i.next(); %>
							<tr>
								<td><input type="checkbox"
									<c:if test="<%=!loa.getEnabledInPage() %>">
								   		disabled="disabled"
								   		title="Operazione senza animale: la registrazione deve essere effettuata esclusivamente in BDU"
								   </c:if>
									name="op_<%=loa.getId()%>"
									onchange="popolaOpSelezionate('<%=loa.getId()%>')"
									id="op_<%=loa.getId()%>"
									<c:if test="<%=loa.getSceltaAsl()!=null && loa.getSceltaAsl()%>">
										onclick="abilitaDisabilitaSceltaAsl(this)"
									</c:if>
									<c:if test="<%=(loa.getId()==idricVarie.getRicoveroInCanile() || loa.getId()==idricVarie.getIncompatibilitaAmbientale() || loa.getId()==idricVarie.getAltro())%>">
										onclick="abilitaDisabilitaNote(this)"
									</c:if>
									<c:if test="<%=loa.getId() == idricVarie.getSmaltimentoCarogna()%>">
								   				onclick="abilitaDisabilitaProntoSoccorso(this)"
								   	</c:if>
								   	<c:if test="<%=loa.getId() == idricVarie.getProntoSoccorso()%>">
								   				onclick="abilitaDisabilitaIdentificazioneCarcasse(this)"
								   	</c:if>
									<c:if test="<%=loa.getId()==idricVarie.getAttivitaEsterne() %>">
										onclick="abilitaDisabilitaAttivitaEsterne(this)"
									</c:if>
									<us:contain collection="${operazioniRichiesteId}" item="<%=loa.getId()%>" >
										checked="checked"
									</us:contain> />
									<label for="op_<%=loa.getId()%>"><%=loa.getDescription()%></label> 
									<c:if test="<%=(loa.getSceltaAsl()!=null && loa.getSceltaAsl()==true)%>">
										<select id="idAslRitrovamento" name="idAslRitrovamento"
											disabled="disabled"
											<us:contain collection="${operazioniRichiesteId }" item="<%=loa.getId()%>" >
												disabled=false
											</us:contain>>
											<option value="">- Seleziona -</option>
											<% Iterator j = listaAltreAsl.iterator(); 
												while (j.hasNext()){
													la = (LookupAsl)j.next();%>
													<option value="<%=la.getId()%>"
													<c:if test="<%=acc.getAslRitrovamento()!=null && (la.getId()==acc.getAslRitrovamento().getId()) %>">
														selected="selected"
													</c:if>>
													<%=la.getDescription()%></option>
										<% 		}%>
										</select>
									</c:if>
									
									<c:if test="<%=loa.getId()==idricVarie.getRicoveroInCanile() || loa.getId()==idricVarie.getIncompatibilitaAmbientale() || loa.getId()==idricVarie.getAltro() %>">
										<c:if test="<%=loa.getId()==idricVarie.getRicoveroInCanile()%>">
											<c:set var="idNomeOperazione" value="noteRicoveroInCanile" />
											<c:set var="value"	value="<%=acc.getNoteRicoveroInCanile() %>" />
										</c:if>
										<c:if
											test="<%=loa.getId()==idricVarie.getIncompatibilitaAmbientale()%>">
											<c:set var="idNomeOperazione" value="noteIncompatibilitaAmbientale" />
											<c:set var="value" value="<%=acc.getNoteIncompatibilitaAmbientale() %>" />
										</c:if>
										<c:if test="<%=loa.getId()==idricVarie.getAltro()%>">
											<c:set var="idNomeOperazione" value="noteAltro" />
											<c:set var="value" value="<%=acc.getNoteAltro() %>" />
										</c:if>
										<input type="text" id="${idNomeOperazione}"	name="${idNomeOperazione}" value="${value}"
											<us:contain collection="${operazioniRichiesteId }" item="<%=loa.getId()%>" >
												checked="checked"
											</us:contain> />
									</c:if></td>
							</tr>
					<%		}%>
					</table>
				</td>
				
				<td>
					<table width="100%">
						<tr>
							<th>Richiesta prelievi - Malattie infettive</th>
						</tr>
						<% 
						boolean notAlreadyInserted = true;
						i = operazioniRichPrelieviInf.iterator(); 
							while (i.hasNext()){
								loa = (LookupOperazioniAccettazione)i.next();
								if(loa.getCovid()!=null && loa.getCovid() && notAlreadyInserted)
								{
									notAlreadyInserted=false;
							%>
									<tr>
									<td><i><b>Test per SARS CoV 2</b></i></td>
								</tr>
								<%	
								}
								%>
								<tr>
								<td>
								<%
								if(loa.getCovid()!=null && loa.getCovid() )
								{
									
								%>
									&nbsp;&nbsp;&nbsp;&nbsp;
								<%
								}
								%>	
									<input type="checkbox" name="op_<%=loa.getId()%>"
									onchange="popolaOpSelezionate('<%=loa.getId()%>')"
									id="op_<%=loa.getId()%>"
									<c:if test="<%=!loa.getEnabledInPage()%>">
										disabled="disabled"
										title="Operazione senza animale: la registrazione deve essere effettuata esclusivamente in BDU"
									</c:if>
									<us:contain collection="${operazioniRichiesteId }" item="<%=loa.getId()%>" >
									checked="checked"
									</us:contain> />
									<label for="op_<%=loa.getId()%>"><%=loa.getDescription()%></label></td>
							</tr>
						<%	}%>
					</table>
				</td>
			</tr>
			<tr>
				<th colspan="3">Approfondimenti</th>
			</tr>
			<tr valign="top">
				<td>
					<table width="100%">
						<tr>
							<th>Approfondimento diagnostico medicina</th>
						</tr>
						<% i = operazioniPsADM.iterator();
						   while(i.hasNext()){
							   loa = (LookupOperazioniAccettazione)i.next();%>
							   <c:if test="<%=loa.getId()!=idops.getTerapiaDegenza()%>">
								<tr>
									<td><input type="checkbox" name="op_<%=loa.getId() %>"
										onchange="popolaOpSelezionate('<%=loa.getId() %>')"
										id="op_<%=loa.getId() %>"
										<c:if test="<%=!loa.getEnabledInPage()%>">
											disabled="disabled"
											title="Operazione senza animale: la registrazione deve essere effettuata esclusivamente in BDU"
										</c:if>
										<us:contain collection="${operazioniRichiesteId }" item="<%=loa.getId()%>" >checked="checked"</us:contain> />
										<label for="op_<%=loa.getId() %>"><%=loa.getDescription()%></label></td>
								</tr>
							</c:if>
						<% }%>
					</table>
				</td>
				<td>
					<table width="100%">
						<tr>
							<th>Alta specialità chirurgica</th>
						</tr>
						<% i = operazioniPsASC.iterator();
							while (i.hasNext()){
								loa = (LookupOperazioniAccettazione)i.next();%>
								<tr>
								<td><input type="checkbox" name="op_<%=loa.getId() %>"
									onchange="popolaOpSelezionate('<%=loa.getId() %>')"
									id="op_<%=loa.getId() %>"
									<c:if test="<%=!loa.getEnabledInPage()%>">
										disabled="disabled"
										title="Operazione senza animale: la registrazione deve essere effettuata esclusivamente in BDU"
								    </c:if>
									<us:contain collection="${operazioniRichiesteId }" item="<%=loa.getId()%>" >checked="checked"</us:contain> />
									<label for="op_<%=loa.getId() %>"><%=loa.getDescription()%></label></td>
							</tr>
					<% 		}%>
					</table>
				</td>
				<td>
					<table width="100%">
						<tr>
							<th>Diagnostica specialistica strumentale</th>
						</tr>
						<% i = operazioniPsDSS.iterator();
							while (i.hasNext()){
								loa = (LookupOperazioniAccettazione)i.next();%>
								<tr>
								<td><input type="checkbox" name="op_<%=loa.getId() %>"
									onchange="popolaOpSelezionate('<%=loa.getId() %>')"
									id="op_<%=loa.getId() %>"
									<c:if test="<%=!loa.getEnabledInPage()%>">
										disabled="disabled"
										title="Operazione senza animale: la registrazione deve essere effettuata esclusivamente in BDU"
								    </c:if>
									<us:contain collection="${operazioniRichiesteId }" item="<%=loa.getId()%>" >checked="checked"</us:contain> />
									<label for="op_<%=loa.getId() %>"><%=loa.getDescription()%></label></td>
							</tr>
					<% 		}%>
					</table>
				</td>
			</tr>
		</table>

	</c:if>
	<c:if test="<%=acc.getAnimale().getDecedutoNonAnagrafe()%>">

		<table class="tabella" style="width: 99%">
			<tr>
				<th>Operazioni disponibili</th>
			</tr>
			<!--  tr>
				<td><input type="checkbox"
					name="op_<%=idopsBdr.getEsameAutoptico()%>"
					id="op_<%=idopsBdr.getEsameAutoptico()%>"
					onchange="popolaOpSelezionate('<%=idopsBdr.getEsameAutoptico()%>')"
					<vam:checked idAccettazione="<%=acc.getId()%>" idLookupOperazione="<%=idopsBdr.getEsameAutoptico()%>" /> />
					<label for="op_<%=idopsBdr.getEsameAutoptico()%>">Esame
						Necroscopico</label></td>
			</tr-->
			<tr>
				<td><input type="checkbox"
					name="op_<%=idopsBdr.getSmaltimentoCarogna()%>"
					id="op_<%=idopsBdr.getSmaltimentoCarogna()%>"
					onchange="popolaOpSelezionate('<%=idopsBdr.getSmaltimentoCarogna()%>')"
					<vam:checked idAccettazione="<%=acc.getId()%>" idLookupOperazione="<%=idopsBdr.getSmaltimentoCarogna()%>" /> />
					<label for="op_<%=idopsBdr.getSmaltimentoCarogna()%>">Registrazione Trasporto Spoglie (Attività B6/B7)</label></td>
			</tr>
			<tr>
				<td><input type="checkbox" name="op_<%=idricVarie.getAttivitaEsterne()%>" id="op_<%=idricVarie.getAttivitaEsterne()%>"
						   onchange="popolaOpSelezionate('<%=idricVarie.getAttivitaEsterne()%>')"
						   onclick="abilitaDisabilitaAttivitaEsterne(this)"
						<vam:checked idAccettazione="<%=acc.getId()%>" idLookupOperazione="<%=idricVarie.getAttivitaEsterne()%>" /> 
					/>
					<label for="op_<%=idricVarie.getAttivitaEsterne()%>">Attivit&agrave; Esterne</label>
				</td>
			</tr>
			
<% 
			i = operazioniCovid.iterator();
			while (i.hasNext())
			{
				LookupOperazioniAccettazione loa = (LookupOperazioniAccettazione)i.next();%>
				<tr>
					<td>
						<input type="checkbox" name="op_<%=loa.getId() %>" onchange="popolaOpSelezionate('<%=loa.getId() %>')" id="op_<%=loa.getId() %>"
						<us:contain collection="${operazioniRichiesteId }" item="<%=loa.getId()%>" >checked="checked"</us:contain> />
						<label for="op_<%=loa.getId() %>"><%=loa.getDescription()%></label>
					</td>
				</tr>
	<% 		}%>
					
					
			
		</table>

	</c:if>
</fieldset>
<br />

<fieldset id="dettagliAttivitaEsterna" style="display: none;">
	<legend>Dettagli Attività Esterne</legend>
	Attività Svolta <font color="red">*</font> <select
		id="idAttivitaEsterna" name="idAttivitaEsterna" disabled="disabled"
		<us:contain collection="${operazioniRichiesteId }" item="${temp.getId()}" >
		disabled=false
	</us:contain>>
		<option value="">- Seleziona -</option>
	<%		
			i = lookupAttivitaEsterne.iterator();
			LookupAccettazioneAttivitaEsterna laae = new LookupAccettazioneAttivitaEsterna();
			boolean vivo = !acc.getAnimale().getDecedutoNonAnagrafe() && (dataDecesso==null || dataDecesso.equals(""));
			int specieId = acc.getAnimale().getLookupSpecie().getId();
			while (i.hasNext()){
				laae = (LookupAccettazioneAttivitaEsterna)i.next();
				if((laae.getId()==1 || laae.getId()==4) || (laae.getId()==2 && vivo) || (laae.getId()==3 && specieId==Specie.SINANTROPO && !vivo))
				{%>
				<option value="<%=laae.getId()%>" id="idAttivitaEsternaValue<%=laae.getId()%>"
				<c:if test="<%=acc.getAttivitaEsterna()!=null && (laae.getId()==acc.getAttivitaEsterna().getId()) %>">
					selected="selected"
				</c:if>>
				<%=laae.getDescription() %></option>
<% 		}}%>

	</select> <br /> 
	Comune <%=((acc.getAnimale().getComuneRitrovamento()!=null)?(acc.getAnimale().getComuneRitrovamento().getDescription()):("")) %> <font color="red">*</font> <select id="idComuneAttivitaEsterna" name="idComuneAttivitaEsterna" disabled="disabled"
									 
										<us:contain collection="${operazioniRichiesteId }" item="${temp.getId()}" >
											disabled=false
										</us:contain>
										<c:if test="<%=acc.getAnimale().getComuneRitrovamento()!=null %>">
											disabled="disabled"
										</c:if>
										>
									  <option value="">- Seleziona -</option>
<%									
										if (lookupComuni!=null)
										{
											LookupComuni lc = new LookupComuni();
											i = lookupComuni.iterator();
											while(i.hasNext())
											{
												lc = (LookupComuni)i.next();
%>
												<option value="<%=lc.getId() %>"
													<c:if test="<%=acc.getComuneAttivitaEsterna()!=null && (lc.getId()==acc.getComuneAttivitaEsterna().getId()) %>">
														selected="selected"
													</c:if>
													<c:if test="<%=acc.getAnimale().getComuneRitrovamento()!=null && (lc.getId()==acc.getAnimale().getComuneRitrovamento().getId()) %>">
														selected="selected"
													</c:if>
												>
				<%=lc.getDescription() %></option>
<%			}}%>

	</select> <br /> Indirizzo 
	<input type="text" id="indirizzoAttivitaEsterna" name="indirizzoAttivitaEsterna"
<% 
	if(acc.getAnimale().getComuneRitrovamento()!=null)
	{
%>
		disabled="disabled"
<%
	}
		if (acc.getIndirizzoAttivitaEsterna()!=null)
		{ 
%>
			value="<%=acc.getIndirizzoAttivitaEsterna()%>" 
<%
		} 
		else if(acc.getAnimale().getIndirizzoRitrovamento()!=null && !acc.getAnimale().getIndirizzoRitrovamento().equals(""))
		{
%>
			value="<%=acc.getAnimale().getIndirizzoRitrovamento()%>" 
<%
		}
		else 
		{ 
%> 
			value="" 
<%
		}
%> />
</fieldset>

<font color="red">* Campi obbligatori</font>
<br />
<c:if test="<%=!modify%>">
	<input type="submit" value="Salva"
			onclick="var mess=test(); if(mess==''){if(myConfirm('Si conferma la data accettazione ' + document.getElementById('data').value + '?')){return checkform('<%=utente.getId()%>','<%=acc.getAnimale().getId()%>','<%=utente.getClinica().getId() %>', <%=utente.getRuolo().equals("Referente Asl") || utente.getRuolo().equals("14")%>,false)}else{ return false;}}else{alert(mess); return false;}" />
	<c:if test="<%=flagAnagrafe==null%>">
		<input type="button" value="Annulla"
			onclick="if(myConfirm('Sicuro di voler annullare l\'inserimento dell\'accettazione?')){ location.href='vam.accettazione.FindAnimale.us?identificativo=<%=acc.getAnimale().getIdentificativo()%>' }" />
	</c:if>
</c:if>
<c:if test="<%=modify%>">
	<input type="submit" value="Salva Modifiche"
		onclick="var mess=test(); 
			         if(mess=='')
			         {
			         	if(myConfirm('Si conferma la data accettazione ' + document.getElementById('data').value + '?'))
			         	{
			         		if(checkform('<%=utente.getId()%>','<%=acc.getAnimale().getId()%>','<%=utente.getClinica().getId() %>', <%=utente.getRuolo().equals("Referente Asl") || utente.getRuolo().equals("14")%>,false))
			         		{
			         			document.form2.submit();
			         		}
			         		else
			         		{
			         			return false;
			         		}
			         	}
			         	else
			         	{ 
			         		return false;
			         	}
			         }
			         else
			         {
			         	alert(mess); 
			         	return false;
			         }" />
		<input type="button" value="Annulla"
		onclick="if( myConfirm('Sicuro di voler annullare le modifiche apportate?') ){ location.href='vam.accettazione.Detail.us?id=<%=acc.getId() %>' }" />
	<input type="hidden" value="<%=acc.getId()%>" name="id" />
	<input type="hidden" value="true" name="modify" />
	<input type="hidden" name="progressivo"
		value="<%=acc.getProgressivo()%>" />
</c:if>
</form>

<script type="text/javascript">

//questa variabile serve a determinare quale div va nascosto quando si seleziona un nuovo tipo di richiedente
			var richiedentePrecedente = 1;
			function selezionaDivRichiedente( idTipoRichiedente )
			{
			    //alert( 'Selezionato il valore: ' + idTipoRichiedente );
			    if(idTipoRichiedente!='personaleInterno')
			    {
					var divHash = new Array
						(
							"-none-",
							"div_richiedente_privato",
							"",
							"div_richiedente_personale_asl",
							"div_richiedente_associazione",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica",
							"div_richiedente_altro",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica",
							"div_richiedente_forza_pubblica"
						);
	
					toggleDiv( divHash[ richiedentePrecedente ] );
					toggleDiv( divHash[ idTipoRichiedente ] );
					
				<%	if (acc.getProprietarioTipo()!=null && acc.getProprietarioTipo().equalsIgnoreCase("colonia")){%>
					document.getElementById("richiedenteProprietario").checked="";
					document.getElementById("richiedenteProprietario").disabled="true";
					document.getElementById('div_richiedente_privato_non_proprietario').style.display='block';
				<%}%>
	
					richiedentePrecedente = idTipoRichiedente;
				}
				else
				{
					toggleDiv( "div_richiedente_personale_interno" );
				}
			};
	
			var idAslPrecedente = -1;
			function selezionaOrganigrammaAsl( idAsl )
			{
				if( idAslPrecedente > 0 )
				{
					toggleDiv( "operatori_asl_" + idAslPrecedente );
				}
				
				if( idAsl > 0 )
				{
					toggleDiv( "operatori_asl_" + idAsl );
					idAslPrecedente = idAsl;
				}
				else
				{
					idAslPrecedente = -1;
				}
	
			};

			function selezionaDipendenteAsl( idDipendente )
			{
				document.getElementById("idDipendenteAsl").value = idDipendente;
			}

			<c:if test="${animale.decedutoNonAnagrafe && !modify }" >
			setTimeout( "toggleDiv('div_richiedente_privato_non_proprietario')", 400 );
		</c:if>

		<c:if test="${modify }" >
			setTimeout( 'selezionaDivRichiedente( ${accettazione.lookupTipiRichiedente.id } )', 800 );
			///setTimeout( 'selezionaOrganigrammaAsl( ${accettazione.richiedenteAsl.id} )', 1200 );
			//setTimeout( 'selezionaDipendenteAsl( ${accettazione.richiedenteAslUser.id} )', 1200 );
		</c:if>


function mySubmit( messaggio )
{
	var test = true;
	var tipoRich = $( '#tipoRichiedente' )[0].value;
	var errore;
	
	if( tipoRich == 2 ) //personale interno
	{
		if( $( "#idPersonaleInterno" )[0].value < 0 )
		{
			test = false;
			errore = "Selezionare il personale richiedente";
		}
	}
	else if( tipoRich == 3 ) //personale asl
	{
		var asl = $( '#idRichiedenteAsl' )[0].value;
		if( asl < 0 )
		{
			test = false;
			errore = "Selezionare l'asl del richiedente";
		}
		else if( $( '#idPersonaleAsl' + asl )[0].value < 0 )
		{
			test = false;
			errore = "Selezionare il personale richiedente";
		}
	}
	else if( tipoRich ==  4) //associazione
	{
		var associazione = $( '#idAssociazione' )[0].value;
		if( associazione < 0 )
		{
			test = false;
			errore = "Selezionare l'associazione";
		}
	}

	if( test )
	{
		return myConfirm( messaggio );
	}
	else
	{
		alert( errore );
		return false;
	}
}

var opSelezionate = new Array();

function mostraPopupChoosePersInt(){

		$( "#choosePersInt" ).dialog({
			height: screen.height/2,
			modal: true,
			autoOpen: true,
			closeOnEscape: true,
			show: 'blind',
			resizable: true,
			draggable: true,
			title: 'Seleziona Personale Interno',
			width: screen.width/2,
			buttons: {
				"Annulla": function() {
					confermaModifiche(false);
					$( this ).dialog( "close" );
				},
				"Ok": function() {
					confermaModifiche(true);
					$( this ).dialog( "close" );
				}
			}
		});
	}
	

function mostraPopupChoosePersAsl(){

		$( "#choosePersAsl" ).dialog({
			height: screen.height/2,
			modal: true,
			autoOpen: true,
			closeOnEscape: true,
			show: 'blind',
			resizable: true,
			draggable: true,
			title: 'Seleziona Personale Asl',
			width: screen.width/2,
			buttons: {
				"Annulla": function() {
					confermaModificheAsl(false);
					$( this ).dialog( "close" );
				},
				"Ok": function() {
					confermaModificheAsl(true);
					$( this ).dialog( "close" );
				}
			}
		});
	}
	
abilitaDisabilitaAttivitaEsterne(document.getElementById('op_'+${idRichiesteVarie.attivitaEsterne}));
</script>

<c:forEach items="${operazioniRichiesteId}" var = "temp" >
<script type="text/javascript">
	popolaOpSelezionate('${temp}');
</script>
</c:forEach>

<%	if(flagAnagrafe!=null){ %>
<script type="text/javascript">
		popolaOpSelezionate('1');
</script>
<%	} %>
<%
	memFreeEnd = Runtime.getRuntime().freeMemory() / (1024 * 1024);
	dataEnd = new Date();
	timeExecution = (dataEnd.getTime() - dataStart.getTime()) / 1000;
	System.out.println("Jsp in esecuzione: "
			+ "vam.accettazione.add(fine)"
			+ " - "
			+ ((timeExecution >= 5) ? ("(ATTENZIONE)") : (""))
			+ "Execution time:"
			+ timeExecution
			+ "s - "
			+ "Memoria usata: "
			+ (memFreeStart - memFreeEnd)
			+ ", utente: "
			+ ((utente == null) ? ("non loggato") : (utente
					.getUsername())));
	memFreeStart = Runtime.getRuntime().freeMemory() / (1024 * 1024);
	dataStart = new Date();
%>

<script type="text/javascript">

<% if (acc.getProprietarioTipo()!=null && acc.getProprietarioTipo().equalsIgnoreCase("colonia")){%>
	document.getElementById("richiedenteProprietario").checked="";
	document.getElementById("richiedenteProprietario").disabled="true";
	toggleDiv('div_richiedente_privato_non_proprietario');
<%}%>

<%
if(acc.getAnimale().getDecedutoNonAnagrafe())	
{
%>
	document.getElementById('idAttivitaEsterna').value="1";
<%
}
%>

</script>


<script>
dwr.engine.setErrorHandler(errorHandler);
dwr.engine.setTextHtmlHandler(errorHandler);

function errorHandler(message, exception){
    //Session timedout/invalidated

    if(exception && exception.javaClassName== 'org.directwebremoting.impl.LoginRequiredException'){
        alert(message);
        //Reload or display an error etc.
        window.location.href=window.location.href;
    }
    else
        alert('Errore Nella Chiamata Remota : '+exception.javaClassName);
 }
</script>









