<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.sql.Date"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="BloccoCu" class="org.aspcfs.modules.bloccocu.base.EventoBloccoCu" scope="request"/>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<table class="trails" cellspacing="0">
<tr>
<td width="100%">
Blocco Controlli
</td>
</tr>
</table>
<%-- End Trails --%>


<%
BloccoCu.setDataBloccoSblocco(new Date(System.currentTimeMillis()));


%>

<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

<%if (BloccoCu.getQuarter()!=0)
	{
	%>
<form method = "post" action = "BloccoCu.do?command=InsertBlocco&auto-populate=true">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">Aggiungi Blocco Per modifica Controlli</dhv:label></strong>
    </th>
  </tr>
  
 
   <tr>
  <td class="formLabel" nowrap> Note</td>
  <td >
       <textarea rows="5" cols="30" name = "note"></textarea>
       
  </tr>
   <tr>
  <td class="formLabel" nowrap>Tipo Blocco</td>
  <td>
     <select name="tipoBlocco" required>
    	 <option value="">Seleziona Tipo</option>
    	 <option value="IN" style="background-color: yellow"><u>NON CONSENTITA</u> MODIFICA/INSERIMENTO DI CONTROLLI CON DATA COMPRESA IN  DAL - AL</option>
    	 <option value="OUT" style="background-color: red">CONSENSITA MODIFICA/INSERIMENTO <u>ESCLUSIVAMENTE</u> DI CONTROLLI CON DATA COMPRESA IN DAL - AL</option>
     </select>
  </tr>
  
  <tr>
  <td class="formLabel" nowrap>Dal</td>
  <td >
     <input type = "text" name = "data_blocco" id = "data_blocco" value="<%=BloccoCu.getData_bloccoString()%>" required>
       <a href="#" onClick="cal19.select(document.forms[0].data_blocco,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a></td>
  </tr>
  <tr>
  <td class="formLabel" nowrap>Al</td>
  <td >
     <input type = "text" name = "data_sblocco" id = "data_sblocco" value="<%=BloccoCu.getData_sbloccoString()%>" required>
         <a href="#" onClick="cal19.select(document.forms[0].data_sblocco,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a></td>
  </tr>
 
  
  </table>
  <input type = "submit" value ="Attiva Blocco">
  </form>
  <%}
else{
	%>
	<p>	<font color = "red" style="font-size: 20pt;">
	Attenzione!Il blocco relativo all'ultimo trimestre è automaticamente gestito dal sistema con una tolleranza di 15 giorni.<br>
	Gli utenti possono inserire/modificare CU per l'anno precedente e fino ai primi 15 giorni del nuovo anno.
	
	</font></p>
	<%
}
  %>