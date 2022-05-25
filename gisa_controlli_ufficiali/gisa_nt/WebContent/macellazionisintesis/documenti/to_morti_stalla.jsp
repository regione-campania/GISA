<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<%@page import="org.aspcfs.modules.macellazionisintesis.utils.MacelliUtil"%>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request" />

<%@ include file="../../initPage.jsp"%>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>

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
<!-- ******************************************************************** -->

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
			<a href="MacellazioniSintesis.do?command=List&altId=<%=OrgDetails.getAltId() %>">Macellazioni</a> > Anim. morti in stalla/trasporto
		</td>
	</tr>
</table>

<%
String param1 = "altId=" + OrgDetails.getAltId()+"&stabId=" + OrgDetails.getIdStabilimento(); request.setAttribute("Operatore",OrgDetails.getOperatore());
%>

<dhv:container 
	name="sintesismacelli"
	selected="macellazioni" 
	object="Operatore" 
	param="<%=param1 %>" 
	appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>' 
	>

<br/>

<font color="black">Attenzione: In questa sezione si intendono animali morti in stalla quelli con esito della Visita Post Mortem uguale a "Esclusione dal Consumo con Distruzione Carcassa"</font></br></br>
<br/>
<font color="red"> <%=toHtmlValue( (String)request.getAttribute( "messaggio" ) ) %> </font>
<br/><br/>
<form name="main" action="MacellazioniDocumentiSintesis.do?command=SpeditoriMortiStalla" method="post" >
<input type="hidden" name="altId" value="<%=OrgDetails.getAltId() %>" />
<input type="hidden" name="id_macello" value="<%=OrgDetails.getAltId() %>" />

<%--	<zeroio:dateSelect form="main" field="data" showTimeZone="false" timestamp="<%=new Timestamp( System.currentTimeMillis() ) %>" />  --%>
	
	<input readonly type="text" name="data" size="10" value="<%=DateUtils.timestamp2string(new Timestamp( System.currentTimeMillis() ))%>" />&nbsp;  
	<a href="#" onClick="cal19.select(document.forms[0].data,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
	<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
	
	<input type="submit" value="Procedi" />

</form>
 
</dhv:container>

