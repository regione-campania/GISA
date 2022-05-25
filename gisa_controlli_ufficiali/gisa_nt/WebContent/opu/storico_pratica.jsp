<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants" %>
<%@page import="java.sql.Timestamp"%>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>
<jsp:useBean id="op" class="java.lang.String" scope="request"/>

<%@ include file="../initPage.jsp"%>

<jsp:useBean id="OperazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaStorico" class="java.util.Vector" scope="request"/>
<%@page import="org.aspcfs.modules.opu.base.Storico"%>

<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>


 <script>$(document).ready(function() {
      // Initialize Smart Wizard
      loadModalWindowUnlock();
      
  }); 
</script>

<style>
.row3 {
	background-color : #b2d8b2 !important;
}
.row4 {
	background-color : #ffb2b2 !important;
}

</style>

<%@page import="java.util.ArrayList"%>


<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>



<%
String param = "stabId="+StabilimentoDettaglio.getIdStabilimento()+"&opId=" + StabilimentoDettaglio.getIdOperatore();
String container = "suap";
if (op!=null && !op.equals(""))
	container = op;

%>
<dhv:container name="<%=container %>"  selected="Storico Pratica" object="Operatore" param="<%=param%>"  hideContainer="false">


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">

<col width="1%">
<col width="30%">
<col width="20%">
<col width="8%">

<tr>
<th>#</th>
<th>Operazione</th>
<th>Utente</th>
<th>Data operazione</th>
<th>Note</th>

<%if (listaStorico.size()>0){
		for (int i=0;i<listaStorico.size(); i++){
			Storico sto = (Storico) listaStorico.get(i);
	%>

<tr class="row<%=sto.getIdOperazione()%>">
<td> <%=i+1 %> </td>
<td> <%=OperazioniList.getSelectedValue(sto.getIdOperazione())%> </td>
<td>  
<dhv:username id="<%=sto.getIdUtente() %>"/>
<% if (sto.getCodFiscaleUtenteRichiedente()!=null){ %>
<br/><b>CF Richiedente: </b> <%=sto.getCodFiscaleUtenteRichiedente() %>
<%} %>
<% if (sto.getCodFiscaleUtenteDelegato()!=null){ %>
<br/><b>CF Delegato: </b> <%=sto.getCodFiscaleUtenteDelegato() %>
<%} %>
</td>
<td> <%=toDateasString(sto.getDataOperazione()) %> </td>
<td> <%=sto.getNote()%> </td>
</tr>

<%}  } else {%>
<tr><td colspan="5">Nessuno storico disponibile per lo stabilimento corrente.</td></tr>
<%} %>

</table>



</dhv:container>




























