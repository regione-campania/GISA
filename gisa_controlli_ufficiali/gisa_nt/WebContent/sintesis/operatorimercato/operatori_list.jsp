<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<jsp:useBean id="listaOperatori" class="java.util.ArrayList" scope="request"/>
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

<script>

function storico (relid, box){
	loadModalWindow();
	window.location.href="StabilimentoSintesisMercatoAction.do?command=StoricoOperatoriMercatoLinea&idRelazione="+relid+"&numBox="+box;
}

function aggiungi (relid, box){
	loadModalWindow();
	window.location.href="StabilimentoSintesisMercatoAction.do?command=RicercaOperatoreMercatoLinea&idRelazione="+relid+"&numBox="+box;
}
function elimina (id){
	if (confirm('ATTENZIONE. Procedere alla CANCELLAZIONE?')) {
		loadModalWindow();
		window.location.href="StabilimentoSintesisMercatoAction.do?command=EliminaOperatoreMercatoLinea&idOperatore="+id;
	}
}
function cessazione (id){
	if (confirm('ATTENZIONE. Procedere alla CESSAZIONE?')) {
		loadModalWindow();
		window.location.href="StabilimentoSintesisMercatoAction.do?command=CessazioneOperatoreMercatoLinea&idOperatore="+id;
	}
}
</script>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>



<% if (Errore!=null && !Errore.equals("")) {%>
<script>
alert('<%=Errore%>');
window.close();
</script>
<% } %>

<center>
<i>Operatori associati al Mercato:</i><br/>
<b><%=Relazione.getPathCompleto().replace("->", "-><br/>") %></b><br/>
<i>sullo stabilimento:</i> <br/>
<b><%=Stabilimento.getDenominazione() %></b><br/>
<i>all'indirizzo:</i> <br/>
<b><%=Stabilimento.getIndirizzo().getDescrizioneToponimo() %> <%=Stabilimento.getIndirizzo().getVia() %> <%=(Stabilimento.getIndirizzo().getCivico()!=null) ? ", "+Stabilimento.getIndirizzo().getCivico() : "" %>, <%=Stabilimento.getIndirizzo().getDescrizioneComune() %>, <%=Stabilimento.getIndirizzo().getDescrizione_provincia() %></b>
</center>

<br/><br/>

<table id="operatori" class="details" width="60%"cellpadding="10" cellspacing="10" style="border-collapse: collapse">
<col width="10%"><col width="40%"><col width="30%">

<tr><th>Num. Box</th> <th>Ragione Sociale</th> <th>Identificativo</th> <th>Operazioni</th></tr>

<%
	for (int i = 0; i<30; i++){ //SCORRO TUTTI E 10 I BOX
	SintesisOperatoreMercato operatore = null;
	
	for (int k = 0; k<listaOperatori.size(); k++) { //SCORRO TUTTI GLI OPERATORI NEL MERCATO
		operatore = (SintesisOperatoreMercato) listaOperatori.get(k);
	

		if (operatore.getNumBox() == (i+1) ) //SE NE TROVO UNO NEL BOX CORRENTE MI FERMO
	break;
		else
	operatore = null;
	}
	
	if (operatore != null) { // SE IN QUEL BOX C'E' UN OPERATORE
%>
	
<tr>
<td><%=operatore.getNumBox() %></td>
<td><%= (operatore.getOpuStabilimento()!=null) ? operatore.getOpuStabilimento().getOperatore().getRagioneSociale() : (operatore.getOrgStabilimento()!=null) ? operatore.getOrgStabilimento().getName() : ""  %></td>
<td><%=operatore.getIdentificativo() %></td> 

<td align="center">

<dhv:permission name="sintesis-mercati-ittici-view">
<input type="button" value="Storico box" onClick="storico('<%=Relazione.getIdRelazione()%>', '<%=i+1%>')"/>
<br/><br/>
</dhv:permission>

<dhv:permission name="sintesis-mercati-ittici-edit">
<input type="button" value="Cessazione" onClick="cessazione('<%=operatore.getId()%>')"/>
<br/><br/>
</dhv:permission>

<dhv:permission name="sintesis-mercati-ittici-delete">
<input type="button" style="background:red" value="Elimina" onClick="elimina('<%=operatore.getId()%>')"/>
</dhv:permission>

</td>
</tr>

<% } else { // ALTRIMENTI MOSTRA BOX VUOTO  %>

<tr>
<td><%=(i+1) %></td>
<td></td>
<td></td> 

<td align="center">

<dhv:permission name="sintesis-mercati-ittici-view">
<input type="button" value="Storico box" onClick="storico('<%=Relazione.getIdRelazione()%>', '<%=i+1%>')"/>
<br/><br/>
</dhv:permission>

<dhv:permission name="sintesis-mercati-ittici-add">
<input type="button" value="Aggiungi" onClick="aggiungi('<%=Relazione.getIdRelazione()%>', '<%=i+1%>')"/>
</dhv:permission>
</td>
</tr>
<% } %>
<%} %>


</table>