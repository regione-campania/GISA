<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<%@page import="org.aspcfs.modules.allerte_new.base.*"%>

<jsp:useBean id="ListaDistribuzione" class="org.aspcfs.modules.allerte_new.base.ListaDistribuzione" scope="request"/>
<jsp:useBean id="forzata" class="java.lang.String" scope="request"/>


<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>


<script>
function checkForm(form){
	if (document.getElementById("data_chiusura").value=='')
		alert("Inserire la data chiusura!");
	else{
		loadModalWindow();
		form.submit();
	}
}
</script>

<form name="addticket"  action="TroubleTicketsAllerteNew.do?command=ChiusuraLista&auto-populate=true" method="post">


<form name ="addticket" id="addticket">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Chiusura Lista di distribuzione</dhv:label></strong>
    </th>
	</tr>
	
  
  <tr>
  <td nowrap class="formLabel">Data chiusura</td>
 <td>
 <input class="editField" type="text" readonly id="data_chiusura" name="data_chiusura" size="10" value=""/>
<a href="#" onClick="cal19.select(document.forms[0].data_chiusura,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> <font color="red">*</font>
 </td></tr>
 
 <%if (forzata!=null && forzata.equals("true")) {%>
 
 <tr><td nowrap class="formLabel">Chiusura forzata</td>
 <td>
 <input type="checkbox" id="chiusuraForzata" name="chiusuraForzata" readonly checked onClick="return false;"/>
 </td></tr>
 
 <tr>
  <td colspan="2">
 <font color="red">ATTENZIONE! Per questa lista di distribuzione non sono stati eseguiti tutti i CU pianificati.<br/>
 La lista sarà chiusa in modo forzato. </font>
 </td></tr>
 
 
 <%} %>
 

<tr><td colspan="2">
 <input type="hidden" id="idLista" name="idLista" value="<%=ListaDistribuzione.getId()%>"/>
<input type="button" value="CHIUDI LISTA DI DISTRIBUZIONE" name="Save" onClick="return checkForm(this.form)">
</td></tr>
</table>
</form>
</body>