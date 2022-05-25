<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 
<jsp:useBean id="listaAutomezzi" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="Stabilimento" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request"/>
<jsp:useBean id="Relazione" class="org.aspcfs.modules.sintesis.base.SintesisRelazioneLineaProduttiva" scope="request"/>
<jsp:useBean id="Errore" class="java.lang.String" scope="request"/>
<jsp:useBean id="CloseWindow" class="java.lang.String" scope="request"/>

<%@ page import="org.aspcfs.modules.sintesis.base.*" %>
<%@ page import="org.aspcfs.modules.gestioneml.base.*" %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<script language="JavaScript" TYPE="text/javascript" SRC="gestione_documenti/generazioneDocumentale.js"></script>


<script>
function visualizza(id){
	loadModalWindow();
	window.location.href='StabilimentoSintesisAction.do?command=DettaglioAutomezzo&id='+id;
}
function modifica(id){
	loadModalWindow();
	window.location.href='StabilimentoSintesisAction.do?command=ModificaAutomezzo&id='+id;
}
function elimina(id){
	if (confirm("Questo automezzo sara' eliminato. Proseguire?")){
	loadModalWindow();
	window.location.href='StabilimentoSintesisAction.do?command=EliminaAutomezzo&id='+id;
	}
}
function dismetti(id){
	if (confirm("Questo automezzo sara' dismesso. Proseguire?")){
	loadModalWindow();
	window.location.href='StabilimentoSintesisAction.do?command=DismettiAutomezzo&id='+id;
	}
}
function aggiungi(idRel){
	loadModalWindow();
	window.location.href='StabilimentoSintesisAction.do?command=AggiungiAutomezzo&idRelazione='+idRel;
}
</script>


<%if (Errore!=null && !Errore.equals("")){ %>
	<script>
	alert('<%=Errore%>');
	</script>
	<%if (CloseWindow!=null && CloseWindow.equals("si")){ %>
		<script>
		window.close();
		</script>
	<%}  %>
<%}  %>

<center>
<i>Automezzi associati alla linea:</i><br/>
<b><%=Relazione.getPathCompleto() %></b><br/>
<i>sullo stabilimento:</i> <br/>
<b><%=Stabilimento.getDenominazione() %></b></center>

<br/><br/>

<table class="details" width="100%"cellpadding="10" cellspacing="10" style="border-collapse: collapse">
<col width="20%"><col width="50%">

<tr><th>Progressivo</th> <th>Targa</th> <th>Operazioni</th></tr>

<% for (int i = 0; i<listaAutomezzi.size(); i++){
	SintesisAutomezzo automezzo = (SintesisAutomezzo) listaAutomezzi.get(i);
%>
<tr>
<td><%=automezzo.getNumeroIdentificativo() %></td>
<td><%=automezzo.getAutomezzoTarga() %></td>
<td>
<input type="button" value="visualizza" onClick="visualizza('<%=automezzo.getId()%>')"/>
<img src="images/icons/stock_print-16.gif" border="0" align="absmiddle" height="16" width="16"/>
<input type="button" title="Stampa" value="Stampa" onClick="openRichiestaPDFScarrabile('<%= automezzo.getId() %>', '<%=Stabilimento.getAltId()%>');">
<br/>

<dhv:permission name="sintesis-scarrabili-edit">
<input type="button" value="modifica" onClick="modifica('<%=automezzo.getId()%>')"/>
<input type="button" value="dismetti" onClick="dismetti('<%=automezzo.getId()%>')"/>
<br/>
</dhv:permission>

<dhv:permission name="sintesis-scarrabili-delete">
<input type="button" value="elimina" onClick="elimina('<%=automezzo.getId()%>')" style="background:red"/>
</dhv:permission>

</td>
</tr>

<%} %>

<tr>
<td colspan="4">
<dhv:permission name="sintesis-scarrabili-add">
<input type="button" value="Aggiungi nuovo" onClick="aggiungi('<%=Relazione.getIdRelazione()%>')"/>
</dhv:permission>
</td>
</tr>

</table>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>
