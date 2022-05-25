<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ include file="../initPage.jsp" %>
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
	var username = form.searchUsername.value;
	var date_start = form.searchtimestampDateStart.value;
	var date_end = form.searchtimestampDateEnd.value;
	var msg = '';
	var formTest=true;
		
	if (username=="" && date_start=="" && date_end==""){
		formTest=false;	
		msg = 'Nessun parametro di ricerca impostato';	
	}
	
	if (date_start!="" && date_end!=""){		
		var date_s = date_start.split('/');
		var date_e = date_end.split('/');
		var DS = new Date(date_s[2],date_s[1]-1,date_s[0]);
		var DE = new Date(date_e[2],date_e[1]-1,date_e[0]);
		
		if(DS > DE){
			formTest=false;
			msg = 'Data Fine minore di Data Inizio';
		} 
	}
	
	
	if (formTest==false){
		alert(msg);
	}
	return formTest;
}
</script>

<table class="trails" cellspacing="0">
	<tr>
		<td width="50%"><dhv:label name="">Log Operazioni</dhv:label></td>
	</tr>
</table>

<form action="LogOperazioni.do?command=Search" method="post" onSubmit="return checkForm(this);">
<table cellpadding="4" cellspacing="0" border="0" width="35%" class="details">
	<tr class="containerBody">
		<td class="formLabel"><dhv:label name="">Username</dhv:label></td>
		<td><input type="text" id="searchUsername" name="searchUsername" value="<%=""%>"/></td>
	</tr>
	<tr class="containerBody">
		<td class="formLabel"><dhv:label name="">Data</dhv:label></td>
		<td>DAL : <input readonly type="date" id="searchtimestampDateStart" name="searchtimestampDateStart" value="<%=""%>"/><a href="#"
				onClick="cal19.select(document.forms[0].searchtimestampDateStart,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a>
				<br>AL : <input type="date" id="searchtimestampDateEnd" name="searchtimestampDateEnd" value="<%=""%>"/><a href="#"
				onClick="cal19.select(document.forms[0].searchtimestampDateEnd,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a></td>
	</tr>
</table>
<input type="submit"/>
</form>

