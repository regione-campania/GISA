<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*" %>
<%@page import="org.aspcfs.modules.allerte_new.base.*"%>

<jsp:useBean id="idAllerta" class="java.lang.String" scope="request"/>
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
	if (document.getElementById("data_trasmissione").value=='')
		alert("Inserire la data trasmissione!");
	else if (document.getElementById("note")!=null && document.getElementById("note").value=='')
		alert("Inserire le note!");
	else{
		loadModalWindow();
		form.submit();
	}
}
</script>

<form name="addticket"  action="TroubleTicketsAllerteNew.do?command=ChiusuraAllerta&auto-populate=true" method="post">


<form name ="addticket" id="addticket">
<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Chiusura Allerta</dhv:label></strong>
    </th>
	</tr>
	
  
  <tr>
  <td nowrap class="formLabel">Data trasmissione</td>
 <td>
 <input class="editField" type="text" readonly id="data_trasmissione" name="data_trasmissione" size="10" value=""/>
<a href="#" onClick="cal19.select(document.forms[0].data_trasmissione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> <font color="red">*</font>
 </td></tr>
 
 
 <%if (forzata!=null && forzata.equals("true")) {%>
 
 <tr>
 <td  nowrap class="formLabel">
 CHIUSURA FORZATA
 </td>
 
 <td>
 <input type="checkbox" id="chiusuraForzata" name="chiusuraForzata" readonly checked onClick="return false;"/>
 </td>
 </tr>
 
 <tr>
  <td  nowrap class="formLabel">
 Motivo/Note
 </td>
 
 
 <td> 
 <textarea name="note" id="note" cols="30" rows="3"></textarea>
 </td>
 </tr>
 
  <tr>
 <td colspan="2">
 <font color="red">ATTENZIONE! Per questa allerta non sono state chiuse tutte le liste di distribuzione associate.<br/>
 Le liste e l'allerta saranno chiuse in modo forzato con Data Chiusura uguale alla Data Trasmissione dell'allerta.</font>
 </td>
 </tr>
 
 <%} %>
 
 <input type="hidden" id="idAllerta" name="idAllerta" value="<%=idAllerta%>"/>
 

 
 <tr>
 <td colspan="2">
<input type="button" value="CHIUDI ALLERTA" name="Save" onClick="return checkForm(this.form)">
</td>
</tr>


</table>
</form>
</body>