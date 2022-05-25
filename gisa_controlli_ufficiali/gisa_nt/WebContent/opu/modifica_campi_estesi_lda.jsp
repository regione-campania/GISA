<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="newStabilimento" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<%@page import="org.aspcfs.modules.lineeattivita.base.LineeCampiEstesi"%>
<%@page import="java.util.Map.Entry"%>
<jsp:useBean id="LineeCampiEstesi" class="java.util.LinkedHashMap" scope="request"/>
<jsp:useBean id="TipoAlimentoDistributore" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoMobili" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoSex" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<%@ include file="../initPage.jsp"%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

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
<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<script>function checkForm(form){
	if (confirm('Sei sicuro di voler salvare definitivamente i dati?')){
		loadModalWindow();
		form.submit();
	}
	
	
}</script>

<form name="modifyCampiLda" id="modifyCampiLda"
		action="OpuStab.do?command=UpdateLineeCampiEstesi2"
		method="post">
		
<center>Modifica campi estesi relativi alle linee di attività dello stabilimento:
<br/>
<b><%=newStabilimento.getOperatore().getRagioneSociale() %></b> 
<br/>
</center>
		
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">


<%

Iterator<Integer> itKeySet = LineeCampiEstesi.keySet().iterator();
String descrizioneLinea = "";
while (itKeySet.hasNext())
{
	%>
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	
	
<!-- 	<tr><th colspan="2" >DESCRIZIONE LINEA ATT.</th></tr> -->
	<%

	int idRelStabLp = itKeySet.next();
	LinkedHashMap entryLp = (LinkedHashMap ) LineeCampiEstesi.get(idRelStabLp);
	
	Set<Entry> entries =entryLp.entrySet();
	for (Entry elemento : entries) 
	{
		LineeCampiEstesi campo = (LineeCampiEstesi) elemento.getValue();

%>

<%if (!descrizioneLinea.equals(campo.getPath_descrizione())){ %>
	<tr><th colspan="2" ><%=campo.getPath_descrizione() %></th></tr>
<%
descrizioneLinea = campo.getPath_descrizione();
} %>


<tr><td class="formlabel"> 
<%=campo.getLabel_campo() %>
</td>

<td>

<input type="hidden" id="input_<%=campo.getIdValore() %>_valore" name="input_<%=campo.getIdValore() %>_valore" value="<%=campo.getIdValore()%>"/>

<%if (campo.getTipo_campo().equals("text")){ %>
<input id="input_<%=campo.getIdValore() %>" name="input_<%=campo.getIdValore() %>" <%=!campo.isModificabile() ? "disabled" : "" %> type="text" value="<%=(campo.getValore_campo()!=null) ? campo.getValore_campo() : "" %>" />

<%} else if (campo.getTipo_campo().equals("checkbox")){ %>
<input id="input_<%=campo.getIdValore() %>" name="input_<%=campo.getIdValore() %>" <%=!campo.isModificabile() ? "disabled" : "" %> type="checkbox" <%=(campo.getValore_campo()!=null && campo.getValore_campo().equals("true")) ? "checked" : ""%>/>

<%} else if (campo.getTipo_campo().equals("select")){ %>

<% if (campo.getTabellaLookup().equalsIgnoreCase("lookup_tipo_mobili")){ %>
<%=TipoMobili.getHtmlSelect("input_"+campo.getIdValore(), (campo.getValore_campo()!=null) ? campo.getValore_campo() : "-1")  %>
<%} %>
<% if (campo.getTabellaLookup().equalsIgnoreCase("lookup_tipo_alimento_distributore")){ %>
<%=TipoAlimentoDistributore.getHtmlSelect("input_"+campo.getIdValore(), (campo.getValore_campo()!=null) ? campo.getValore_campo() : "-1")  %>
<%} %>
<% if (campo.getTabellaLookup().equalsIgnoreCase("sex_table_lookup")){ %>
<%=TipoSex.getHtmlSelect("input_"+campo.getIdValore(), (campo.getValore_campo()!=null) ? campo.getValore_campo() : "-1")  %>
<%} %>

<%} else if (campo.getTipo_campo().equals("data")){ %>
<input id="input_<%=campo.getIdValore() %>" name="input_<%=campo.getIdValore() %>" <%=!campo.isModificabile() ? "disabled" : "" %>  readonly value="<%=(campo.getValore_campo()!=null) ? campo.getValore_campo() : "" %>"/>
<a href="#" onClick="cal19.select2(document.forms[0].input_<%=campo.getIdValore() %>,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
<%} else {%>
<input id="input_<%=campo.getIdValore() %>" name="input_<%=campo.getIdValore() %>" <%=!campo.isModificabile() ? "disabled" : "" %>  type="text" value="<%=(campo.getValore_campo()!=null) ? campo.getValore_campo() : "" %>" />
<%} %>

<%if (campo.getIdUtenteInserimento()>0) {%>
<i>Modificato da: <b> <dhv:username id="<%=campo.getIdUtenteInserimento() %>"/></b> in data <b><%=toDateasString(campo.getDataInserimento()) %></b></i>
<%} %>
</td></tr>

<% } %>
</table>
<%
}
%>


<input type="hidden" id="stabId" name="stabId" value="<%=newStabilimento.getIdStabilimento()%>"/>

<center><input type="button" value="SALVA" onClick="checkForm(this.form)"/></center>
