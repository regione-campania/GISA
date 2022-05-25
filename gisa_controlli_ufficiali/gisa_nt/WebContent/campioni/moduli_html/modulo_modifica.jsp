<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.Map.Entry"%>
<%@page import="org.aspcfs.modules.campioni.base.ModuliCampioniHtmlFields"%>
<%@page import="java.util.*"%>

<jsp:useBean id="campiHash" class="java.util.LinkedHashMap" scope="request"/>
<jsp:useBean id="messaggioOk" class="java.lang.String" scope="request"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../../initPage.jsp" %>
<link rel="stylesheet" href="css/template1-8pt.css" type="text/css" media="screen">
<link rel="stylesheet" href="css/template1.css?1" type="text/css">

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

<style>
input[type=text]{
 width: 100%;
}
</style>

<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script language="JavaScript">

function inizializzaForm () {
var inputs, index;
inputs = document.getElementById("modificaModulo").elements;
for (index = 0; index < inputs.length; ++index) {
	var id = inputs[index].id;
	
	if (inputs[index].onchange!=null)
		document.getElementById(id).onchange();
	
}
}

function setDataScadenza (name)
{
	
	//Vuoto (inutile in modifica)

}

	</script>

<script> function checkForm(form){
	loadModalWindow();
	return true;
	}
</script>
	
	<%if (messaggioOk!=null && !messaggioOk.equals("")){ %>
	<script>alert('<%=messaggioOk%>');
	window.close();
	</script>
	<font color="green"><b><%=messaggioOk %></b></font>
	<%} %>
	
<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>
</div>

<form method="post" name="modificaModulo" id="modificaModulo" action="CampioniReport.do?command=UpdateSchedaModulo">

<input type="hidden" id="idCampione" name="idCampione" value="<%=request.getParameter("idCampione") %>"/>
<input type="hidden" id="tipoModulo" name="tipoModulo" value="<%=request.getParameter("tipoModulo") %>"/>
<input type="hidden" id="orgId" name="orgId" value="<%=request.getParameter("orgId") %>"/>

<table width="100%">
<th align="center" colspan="2" bgcolor="#5CB8E6">
		Modifica Scheda 
</th>

<%
Set<Entry> entries = campiHash.entrySet();
for (Entry elemento : entries) 
{
%>
<tr><td style="border:1px solid black;"> 
<%=elemento.getKey() %>
</td>

<td style="border:1px solid black;" bgcolor="#E6E6E6">
<%=elemento.getValue() %>
</td></tr>

<% } %>

</table>

<input type="button" value="SALVA" onClick="if (checkForm(this.form)){this.form.submit();}"/>

<script>inizializzaForm();</script>
</form>



