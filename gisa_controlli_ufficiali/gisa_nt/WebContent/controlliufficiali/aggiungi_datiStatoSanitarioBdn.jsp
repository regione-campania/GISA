<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.allevamenti.base.Organization" scope="request"/>
<jsp:useBean id="DatiStatoSanitarioBdn" class="org.aspcf.modules.controlliufficiali.base.DatiStatoSanitarioBdn" scope="request"/>
<jsp:useBean id="Response" class="java.lang.String" scope="request"/>
<jsp:useBean id="qualificaSanitariaList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="malattieList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SpecieList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

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

<script>
function checkForm(form){
	var invio = form.inviosinvsa.value;
	
	var msg = "Salvare i dati inseriti?";
	
	if (invio =='invia')
		msg+="\n\n I dati saranno inviati automaticamente in BDN.";
	else
		msg+="\n\n ATTENZIONE. E' stato selezionato di NON inviare i dati in BDN.";
	
if (confirm(msg)){
	loadModalWindow();
	form.submit();
}
	
}

</script>


<% 
if (Response != null && !Response.equals("")) 
{ 
%>
<table width="100%">
<tr style="background:yellow"><th colspan="2">ESITO INVIO</th></tr>
<tr><td><%=Response %>
</td></tr>
</table>
<br/>
<% } %>	



<form id = "addAccount" name="addAccount" action="PrintReportVigilanza.do?command=SalvaDatiStatoSanitarioBdn&auto-populate=true" method="post">

<table class="details">
<tr><th colspan="2">DATI STATO SANITARIO</th></tr>

<tr>
	<td>
		ID ALLEVAMENTO
	</td> 
	<td> 
		<input type="text" readonly id="idAzienda" name="idAzienda" value="<%=OrgDetails.getOrgId() %>"/> 
	</td> 
</tr>
<tr>
	<td>
		CODICE ASL
	</td> 
	<td>
		<input type="text" id="aslCodice" name="aslCodice" value="<%=((DatiStatoSanitarioBdn.getAslCodice()!=null && !DatiStatoSanitarioBdn.getAslCodice().equals("")) ? (toHtml(DatiStatoSanitarioBdn.getAslCodice())) : ((OrgDetails.getSiteId()>0)?("R" + OrgDetails.getSiteId()):(""))) %>"/>
	</td>
</tr>
<tr>
	<td>
		CODICE AZIENDA 
	</td> 
	<td>
		<input type="text" id="aziCodice" name="aziCodice" value="<%=((DatiStatoSanitarioBdn.getAziCodice()!=null && !DatiStatoSanitarioBdn.getAziCodice().equals("")) ? (toHtml(DatiStatoSanitarioBdn.getAziCodice())) : (OrgDetails.getAccountNumber())) %>"/>
	</td>
</tr>
<tr>
	<td>
		CODICE GRUPPO SPECIE
	</td> 
	<td>
		<%=SpecieList.getHtmlSelect("gspCodice",DatiStatoSanitarioBdn.getGspCodice()) %>
	</td>
</tr>

<tr>
	<td>
		CODICE MALATTIA
	</td> 
	<td>
		<%=malattieList.getHtmlSelect("malCodice",DatiStatoSanitarioBdn.getMalCodice()) %>
	</td>
</tr>

<tr>
	<td>
		CODICE QUALIFICA SANITARIA
	</td> 
	<td>
		<%=qualificaSanitariaList.getHtmlSelect("qsaCodice",(DatiStatoSanitarioBdn.getQsaCodice()>0)?(DatiStatoSanitarioBdn.getQsaCodice()):(-1)) %>
	</td>
</tr>

<tr>
	<td>
		DATA INIZIO VALIDITA'
	</td> 
	<td>
	<%
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	%>
		<input type="text" id="dtInizioValidita" name="dtInizioValidita"" value="<%=((DatiStatoSanitarioBdn.getDtInizioValidita()!=null && !DatiStatoSanitarioBdn.getDtInizioValidita().equals("")) ? (toHtml(DatiStatoSanitarioBdn.getDtInizioValidita())) : (sdf.format(new java.util.Date(OrgDetails.getDate1().getTime())))) %>"/>
	</td>
</tr>

<tr>
	<td> 
		<input type="radio" id="invia" name="inviosinvsa" value="invia" checked>Inviare in Bdn
	</td> 
	<td>
		<input type="radio" id="noninvia" name="inviosinvsa" value="noninvia">NON inviare in Bdn
	</td>
</tr>

<tr>
	<td colspan="2">
		<input type="button" value="SALVA" onClick="checkForm(this.form)"/>
	</td>
</tr>
</table>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

