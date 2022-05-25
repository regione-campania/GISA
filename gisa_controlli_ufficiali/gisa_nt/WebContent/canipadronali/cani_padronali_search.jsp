<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ include file="../initPage.jsp"%>
<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />


<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>
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
<script>
function openPopupAggiungiCane(link)
{

	window.open(link);

}

</script>

<form name="searchAccount" action="CaniPadronaliVigilanza.do?command=SearchVigilanza"
	method="post">
	
	<%
	UserBean user = (UserBean)session.getAttribute("User");
	%>

<br>
<br>
<table class = "noborder">
<tr>
<td>
<a class="ovalbutton" href = "#" onclick="javascript:openPopupAggiungiCane('CaniPadronali.do?command=LoginCanina')" ><span >Vai a BDR</span></a>
</td>
<td>&nbsp;</td>
<td>
<a class="ovalbutton" href = "#" onclick="javascript:window.open('http://www.salute.gov.it/anagcaninapublic_new/home.jsp')" ><span >Vai a BDN</span></a>

</td>
</tr>
</table>
<br>
<br>
<table class="trails" cellspacing="0">
	<tr>
		<td>Ricerca Controlli Ufficiali</td>
	</tr>
</table>
<%
UserBean utente = (UserBean) session.getAttribute("User");
if (utente.getSiteId()>0)
{
%>
<input type = "hidden" name = "siteId" value = "<%=utente.getSiteId() %>">
	<%
	
}
%>
<table cellpadding="2" cellspacing="2" border="0" width="100%">

	<tr>
		<td width="50%" valign="top">

		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2">Filtri Ricerca Controlli ufficiali su Anagrafica Cani di proprieta</th>
			</tr>
			<tr>
				<td class="formLabel">Codice Fiscale Proprietario</td>
				<td><input type="text" maxlength="70" size="50"
					name="searchcfprop" value=""></td>
			</tr>
			<tr>
				<td class="formLabel">Id Controllo</td>
				<td><input type="text" maxlength="70" size="50"
					name="searchidControllo" value=""></td>
			</tr>
			<tr>
				<td class="formLabel">Data Controllo</td>
				<td>
					<input readonly type="text" id="searchdataControllo" name="searchdataControllo" size="10" />
			        <a href="#" onClick="cal19.select(document.forms[0].searchdataControllo,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
					<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
					
		
				</td>
			</tr>

			<tr >
				<td class="formLabel">Microchip</td>
				<td><input type="text" maxlength="15" size="16"
					name="searchmicrochip" value=""></td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
		<input type = "submit" value = "Ricerca" >
</form>


