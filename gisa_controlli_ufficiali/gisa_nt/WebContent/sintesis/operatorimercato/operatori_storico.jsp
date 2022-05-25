<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<jsp:useBean id="listaOperatoriStorico" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>
<jsp:useBean id="Relazione" class="org.aspcfs.modules.sintesis.base.SintesisRelazioneLineaProduttiva" scope="request"/>
<jsp:useBean id="Errore" class="java.lang.String" scope="request"/>

<%@ page import="org.aspcfs.modules.sintesis.base.*" %>

<%@ page import="org.aspcfs.modules.gestioneml.base.*" %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<%@ include file="../../initPage.jsp"%>

<script>
function listaOperatori(relid){
	loadModalWindow();
	window.location.href="StabilimentoSintesisMercatoAction.do?command=ListaOperatoriMercatoLinea&idRelazione="+relid;
}

</script>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>



<% if (listaOperatoriStorico.size()==0) {%>
<script>
alert('Non sono presenti operatori nello storico di questo box.');
listaOperatori('<%=Relazione.getIdRelazione()%>');
</script>
<% } %>

<center>
<a href="#" onClick="listaOperatori('<%=Relazione.getIdRelazione()%>')">Torna alla lista operatori</a><br/><br/>

<i>Storico operatori associati al Mercato:</i><br/>
<b><%=Relazione.getPathCompleto().replace("->", "-><br/>") %></b><br/>
<i>sullo stabilimento:</i> <br/>
<b><%=Stabilimento.getDenominazione() %></b><br/>
<i>all'indirizzo:</i> <br/>
<b><%=Stabilimento.getIndirizzo().getDescrizioneToponimo() %> <%=Stabilimento.getIndirizzo().getVia() %> <%=(Stabilimento.getIndirizzo().getCivico()!=null) ? ", "+Stabilimento.getIndirizzo().getCivico() : "" %>, <%=Stabilimento.getIndirizzo().getDescrizioneComune() %>, <%=Stabilimento.getIndirizzo().getDescrizione_provincia() %></b>
</center>

<br/><br/>

<table id="operatori" class="details" width="60%"cellpadding="10" cellspacing="10" style="border-collapse: collapse">
<col width="10%"><col width="40%"><col width="30%">

<tr><th>Num. Box</th> <th>Ragione Sociale</th> <th>Identificativo</th> <th>Stato</th></tr>

<%
	for (int i = 0; i<listaOperatoriStorico.size(); i++){ 
	SintesisOperatoreMercato operatore = null;
	operatore = (SintesisOperatoreMercato) listaOperatoriStorico.get(i);
	if (operatore != null) {
%>
	
<tr>
<td><%=operatore.getNumBox() %></td>
<td><%= (operatore.getOpuStabilimento()!=null) ? operatore.getOpuStabilimento().getOperatore().getRagioneSociale() : (operatore.getOrgStabilimento()!=null) ? operatore.getOrgStabilimento().getName() : ""  %></td>
<td><%=operatore.getIdentificativo() %></td> 
<td> 
<% if (operatore.getTrashedDate()!=null){  %>
CANCELLATO <br/>(<dhv:username id="<%= operatore.getTrashedBy() %>" /> <br/><%=toDateasString(operatore.getTrashedDate()) %>) 
<% } else if (operatore.getDataCessazione()!=null) { %>
CESSATO <br/>(<dhv:username id="<%= operatore.getCessatoBy() %>" /> <br/><%=toDateasString(operatore.getDataCessazione()) %>) 
<% } %>
</td> 
</td>
</tr>

<% }
} %>


</table>