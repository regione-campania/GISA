<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.sintesis.base.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<jsp:useBean id="listaOperatori" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="ricercaList" class="org.aspcfs.modules.ricercaunica.base.RicercaList" scope="request"/>
<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>
<jsp:useBean id="Relazione" class="org.aspcfs.modules.sintesis.base.SintesisRelazioneLineaProduttiva" scope="request"/>
<jsp:useBean id="NumBox" class="java.lang.String" scope="request"/>


<%@page import="org.aspcfs.modules.ricercaunica.base.*"%>

<%@ include file="../../initPage.jsp" %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script>

function listaOperatori(relid){
	loadModalWindow();
	window.location.href="StabilimentoSintesisMercatoAction.do?command=ListaOperatoriMercatoLinea&idRelazione="+relid;
}

function inserisciBox(relid, rifid, rifidnometab, box){
	var esistente = document.getElementById("operatore_"+rifid+"_"+rifidnometab);
	if (esistente!=null){
		alert("Quest'anagrafica risulta gia' presente come operatore in questo mercato al numero di box "+esistente.value);
		return false;
	}
	
	loadModalWindow();
	window.location.href="StabilimentoSintesisMercatoAction.do?command=ClonaOperatoreMercatoLinea&idRelazione="+relid+"&numBox="+box+"&riferimentoIdOperatoreDaClonare="+rifid+"&riferimentoIdNomeTabOperatoreDaClonare="+rifidnometab;
}
function ricerca(relid, box){
	loadModalWindow();
	window.location.href="StabilimentoSintesisMercatoAction.do?command=RicercaOperatoreMercatoLinea&idRelazione="+relid+"&numBox="+box;
}

</script>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>
  
<center>
<i>Operatori associati al Mercato:</i><br/>
<b><%=Relazione.getPathCompleto().replace("->", "-><br/>") %></b><br/>
<i>sullo stabilimento:</i> <br/>
<b><%=Stabilimento.getDenominazione() %></b><br/>
<i>all'indirizzo:</i> <br/>
<b><%=Stabilimento.getIndirizzo().getDescrizioneToponimo() %> <%=Stabilimento.getIndirizzo().getVia() %> <%=(Stabilimento.getIndirizzo().getCivico()!=null) ? ", "+Stabilimento.getIndirizzo().getCivico() : "" %>, <%=Stabilimento.getIndirizzo().getDescrizioneComune() %>, <%=Stabilimento.getIndirizzo().getDescrizione_provincia() %></b>
</center>

<br/><br/>
<center>
<a href="#" onClick="listaOperatori('<%=Relazione.getIdRelazione()%>')">Torna alla lista operatori</a><br/><br/>
<a href="#" onClick="ricerca('<%=Relazione.getIdRelazione()%>', '<%=NumBox%>')">Effettua una nuova ricerca</a></center>
<br/>
  	
<center><b>Lista operatori associabili nel box <%=NumBox %></b></center>
  		
<table  class="details" width="100%">

		<tr>
			<th>Stato</th>
			<th>Numero registrazione</th>
			<th>Ragione sociale</th>
			<th>Partita IVA</th>
			<th>Indirizzo</th>
			<th>Seleziona</th>
			
		</tr>
			<%

	if (ricercaList.size()>0) {
		for (int i=0;i<ricercaList.size(); i++){
			RicercaOpu stab = (RicercaOpu) ricercaList.get(i);
			
			%>
			
			<tr class="row<%=i%2%>">
			<td><%=stab.getStato() %></td> 
			<td><%=stab.getNumeroRegistrazione() %></td> 
			<td><%=stab.getRagioneSociale() %></td> 
			<td><%=stab.getPartitaIva() %></td> 
			<td><%=stab.getIndirizzoSedeProduttiva() %></td> 
			<td><a href="#" onClick="inserisciBox('<%=Relazione.getIdRelazione()%>', '<%=stab.getRiferimentoId()%>', '<%=stab.getRiferimentoIdNomeTab()%>', '<%=NumBox%>')">Seleziona</a></td>
		</tr>
		<%} } else {%>
		<tr><td colspan="6"> Non sono stati trovati operatori.</td></tr> 
		
		<% } %>
		
	
	</table>
	
	
<%
			for (int k = 0; k<listaOperatori.size(); k++) { 
			SintesisOperatoreMercato operatore = (SintesisOperatoreMercato) listaOperatori.get(k);
		%>
	<input type="hidden" id="operatore_<%=operatore.getRiferimentoIdOperatore() %>_<%=operatore.getRiferimentoIdNomeTabOperatore() %>" name="operatore_<%=operatore.getRiferimentoIdOperatore() %>_<%=operatore.getRiferimentoIdNomeTabOperatore() %>" value="<%=operatore.getNumBox() %>"/>
	<% } %>

	


</body>
</html>