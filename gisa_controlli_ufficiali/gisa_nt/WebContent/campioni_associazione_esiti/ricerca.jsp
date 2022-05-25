<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/submit.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script type="text/javascript" src="javascript/ui.core.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
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
		<td>
			<a href="MyCFS.do?command=Home">
				<dhv:label name="My Home Page" mainMenuItem="true">La Mia Home Page</dhv:label></a> >
				<dhv:label name="">Associazione Esiti-Campioni</dhv:label>
		</td>
	</tr>
</table>

<%
	String data = (request.getAttribute("data")==null ? ("") : (String)request.getAttribute("data"));
	String matricola = (request.getAttribute("matricola")==null ? ("") : (String)request.getAttribute("matricola"));
%>

<form action="CampioniAssociazioneEsiti.do?command=Ricerca" name="main" method="post" onsubmit="return controllaForm();">
<%
	String errore = (request.getAttribute("Error")==null ? ("") : (String)request.getAttribute("Error"));
	String errore2 = (request.getAttribute("Error2")==null ? ("") : (String)request.getAttribute("Error2"));
	if(errore!=null && !errore.equals("") && errore2!=null && !errore2.equals("") )
		errore2 = errore2 + ". <br/>" + errore;
	else if(errore!=null && !errore.equals("") && (errore2==null || errore2.equals("")) )
		errore2 = errore;
	if(errore2!=null && !errore2.equals(""))
	{
%>
	<font color="red"><%=errore2%></font><br/>
<%
	}
%>

	Numero matricola/partita
	<input type="text" name="matricola" id="matricola" value="<%=matricola%>"/>
  		<br/>
  		Data Macellazione
  		<input readonly type="text" id="data" name="data" size="10" value="<%=data%>" />&nbsp;  
       <a href="#" onClick="cal19.select(document.forms[0].data,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
    <img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
    <a style="cursor: pointer;" onclick="svuotaData(document.forms[0].data);"><img src="images/delete.gif" align="absmiddle"/></a>
	<br/>
	
	<input type="submit" value="Ricerca">
</form>

<script type="text/javascript">
	function controllaForm(  )
	{
		var form = document.main;
		
		if( form.data.value=="" && form.matricola.value=="")
		{
			alert("Valorizzare almeno data o matricola/partita" );
			return false;
		}

		return true;
	}
	
	function svuotaData(input){
		input.value = '';
	}
		
</script>
