<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
     <%@ page import="org.apache.commons.lang.StringEscapeUtils"%>

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
 
 function clonaNelPadre (label, sql_campo, sql_origine, sql_condizione, attributo, destinazione){
	window.opener.gestisciRiga (label, sql_campo, sql_origine, sql_condizione, attributo, destinazione, '-1');
	//window.close();
}

</script>



<form method="post" name="form2" action="SchedaCentralizzataAction.do?command=DettaglioGestioneScheda">
<input type="hidden" id="operazione" name="operazione" value="clona"/>

<table cellpadding='0' class="imagetable"> 
<thead>
<tr> 
<th>Tipo Operatore <br/> 
<% tipoList.setJsEvent("onChange=\"sendForm(this.form)\""); %>
<%=tipo%> - <%=tipoList.getHtmlSelect("tipo", tipo) %>
</th> 
<th>Label</th> <th>Campo da recuperare<br/> SELECT</th> <th>Tabella di origine<br/> FROM</th> <th> Condizione da applicare <br/> WHERE</th> <th>Tipo campo</th> <th>Destinazione</th> <th>Ordine</th> <th>Enabled</th> <th>Clona</th></tr>
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
<td><input type="button" value="CLONA" onClick="clonaNelPadre('<%=StringEscapeUtils.escapeJavaScript(checkNull(elemento.getValue()[4]))%>', '<%=StringEscapeUtils.escapeJavaScript(checkNull(elemento.getValue()[1]))%>', '<%=StringEscapeUtils.escapeJavaScript(checkNull(elemento.getValue()[2]))%>', '<%=StringEscapeUtils.escapeJavaScript(checkNull(elemento.getValue()[3]))%>', '<%=StringEscapeUtils.escapeJavaScript(checkNull(elemento.getValue()[5]))%>', '<%=StringEscapeUtils.escapeJavaScript(checkNull(elemento.getValue()[8]))%>')"/></td>
</tr>
<% } %>
</tbody>
</table>

</form>
