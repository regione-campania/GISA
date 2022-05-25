<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope = "request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="ListaOperazioni" class="java.util.ArrayList" scope="request"/>

<%@ page import="org.aspcfs.modules.opu.base.LineaProduttiva" %>
<%@ page import="org.aspcfs.modules.gestioneanagrafica.base.*" %>
<%@ page import="org.aspcfs.modules.gestioneanagrafica.actions.*" %>
  
<%@ include file="../initPage.jsp" %>

<%
	boolean flagLineeScia = false;
	for(int i = 0; i < StabilimentoDettaglio.getListaLineeProduttive().size(); i++ ){
		LineaProduttiva l = (LineaProduttiva) StabilimentoDettaglio.getListaLineeProduttive().get(i);
		if(!l.getFlags().isNoScia())
		{
			flagLineeScia = true;
			break;
		}
	}


String nomeContainer = "gestioneanagrafica";

if(!flagLineeScia) { 
	nomeContainer = "gestioneanagraficanoscia";
}

StabilimentoDettaglio.getOperatore().setRagioneSociale(StabilimentoDettaglio.getOperatore().getRagioneSociale().toUpperCase() );
request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());
String param = "stabId="+StabilimentoDettaglio.getIdStabilimento();
%> 

<%-- Trails --%>
	<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="OpuStab.do?command=SearchForm">ANAGRAFICA STABILIMENTI</a> > 
			<a href="GestioneAnagraficaAction.do?command=Details&altId=${altId}">SCHEDA</a> > STORICO OPERAZIONI STABILIMENTO
		</td>
	</tr>
	</table>
<%-- Trails --%>



<dhv:container name="<%=nomeContainer %>"  selected="details" object="Operatore" param="<%=param%>" hideContainer="false">
	<center>
	<h3>
	<b>LISTA OPERAZIONI</b><br/>
	<i>In questa pagina sono elencate operazioni effettuate su questo stabilimento.</i>
	</h3>
	<br>
	<table class="table details" id="tabella_operazioni" style="border-collapse: collapse" border="1" width="100%" cellpadding="5"> 
	<tr>
		<th style="text-align:center">
			<p>DATA OPERAZIONE</p>
		</th>
		<th style="text-align:center">
			<p>UTENTE</p>
		</th>
		<th style="text-align:center">
			<p>TIPO OPERAZIONE</p>
		</th>
		<th style="text-align:center">
			<p>NUMERO PRATICA</p>
		</th>
		<th style="text-align:center">
			<p>CAUSALE PRATICA</p>
		</th>
		<th style="text-align:center">
			<p>TIPO PRATICA</p>
		</th>
		
	</tr>
	
	<% 
	for ( int i = 0; i<ListaOperazioni.size(); i++) {
		
		OperazioneSuOsa operazione = (OperazioneSuOsa) ListaOperazioni.get(i);
		%>
		
	<tr class="row<%=i%2%>">
	
	<td align="center"><p><%=toDateasString(operazione.getData_operazione()) %></p></td>
	<td align="center"><p><%=operazione.getUtente() %></p></td>
	<td align="center">
		<%if(operazione.getId_tipo_operazione_evento().equalsIgnoreCase("15")){ %>
			<p><%=operazione.getTipo_operazione() %></p>
		<%} else { %>
			<p>
				<a href="#" onclick="visualizza_storico_operazione('<%=operazione.getId_evento_osa() %>','<%=operazione.getId_tipo_operazione_evento() %>')" >
					<%=operazione.getTipo_operazione() %>
				</a>
			</p>
		<%} %>
	</td>
	<td align="center">
		<p>
			<a href="#" onclick="visualizza_dettaglio_pratica('<%=operazione.getId_pratica_gins() %>')">
				<%=operazione.getNumero_pratica() %>
	       	</a>
		</p>
	</td>
	<td align="center"><p><%=operazione.getCausale_pratica() %></p></td>
	<td align="center"><p><%=operazione.getTipo_pratica() %></p></td>
	
	</tr>
	
	<%} %>
	</table>
	<br>
	</center>
</dhv:container>
<br>

<script>

function visualizza_storico_operazione(id_evento, id_tipo_evento){
	window.open('GestioneAnagraficaAction.do?command=VisualizzaStoricoEvento&id_evento='+id_evento+'&id_tipo_evento='+id_tipo_evento,
				'popupSelect',
    'height=600px,width=1200px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

function visualizza_dettaglio_pratica(id_pratica){
	window.open('GestionePraticheAction.do?command=VisualizzaDettaglioPratica&id_pratica='+id_pratica,
			'popupSelect',
			'height=600px,width=1200px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
}

</script>
  	
 