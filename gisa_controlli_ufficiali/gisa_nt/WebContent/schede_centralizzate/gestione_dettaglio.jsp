<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>

<jsp:useBean id="SchedaOperatore" class="org.aspcfs.modules.schedeCentralizzate.base.SchedaCentralizzata" scope="request"/>
<jsp:useBean id="tipoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="tipo" class="java.lang.String" scope="request"/>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>

<link rel="stylesheet" documentale_url="" href="schede_centralizzate/dettaglio_screen.css" type="text/css" media="screen" />

<%! public static String checkNull(String string)
  {
	  if (string == null)
		  return "";
	  else 
		  return string;
  }%>
<script> function sendForm (form){
	form.submit();
}
 function modifica (form){
	 form.operazione.value ='modifica';
	form.submit();
}
 
 function test(){
	  window.open('servizi/schedaOperatoreServizio.jsp','popupSelect',
         'height=800px,width=1280px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}
</script>

<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

<body onload="resizeGlobalItemsPane('hide')">

Info propagazione: Db origine: <b>select * from aggiorna_scheda_centralizzata(<%=tipo %>)</b> Db destinazione: copiare il risultato ed eseguirlo.<br/><br/>

<form method="post" name="form2" action="SchedaCentralizzataAction.do?command=DettaglioGestioneScheda">
<input type="hidden" id="operazione" name="operazione" value="dettaglio"/>

<table style="border:1px solid black" align="center">
<tr><td>
<input type="button" value="MODIFICA" onClick="modifica(this.form)"/> &nbsp;&nbsp;&nbsp;
</td><td>
&nbsp;&nbsp;&nbsp; <input type="button" value="Test Scheda" onClick="test()"/>
</td></tr>
</table>

<br/>



<table cellpadding='0' class="imagetable"> 
<thead>
<tr> 
<th>Tipo Operatore <br/> 
<% tipoList.setJsEvent("onChange=\"sendForm(this.form)\""); %>
<%=tipo%> - <%=tipoList.getHtmlSelect("tipo", tipo) %>
</th> 
<th>Label</th> <th>Campo da recuperare<br/> SELECT</th> <th>Tabella di origine<br/> FROM</th> <th> Condizione da applicare <br/> WHERE</th> <th>Tipo campo</th> <th>Destinazione</th> <th>Ordine</th> <th>Enabled</th></tr>
</thead>
<tbody>
<% 
LinkedHashMap<String,String[]> listaElementi = SchedaOperatore.getListaElementi();
for(Map.Entry<String, String[]> elemento : listaElementi.entrySet()){
	%>
<tr class="<%=(elemento.getValue()[7].equals("f")) ? "red" : "" %>">
<td><%=tipoList.getSelectedValue(elemento.getValue()[0]) %></td>
<td><%=checkNull(elemento.getValue()[4])%></td>
<td><%=checkNull(elemento.getValue()[1])%></td>
<td><%=checkNull(elemento.getValue()[2])%></td>
<td><%=checkNull(elemento.getValue()[3])%></td>
<td><%=checkNull(elemento.getValue()[5])%></td>
<td><%=checkNull(elemento.getValue()[8])%></td>
<td><%=checkNull(elemento.getValue()[6])%></td>
<td><%=checkNull(elemento.getValue()[7])%></td>
</tr>
<% } %>
</tbody>
</table>

</form>
</body>