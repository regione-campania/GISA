<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope = "request"/>

<jsp:useBean id="storicoList" class="java.util.ArrayList" scope="request"/>

<%@ page import="org.aspcfs.modules.gestioneanagrafica.base.*" %>

  
  <%@ include file="../initPage.jsp" %>
  
  <%-- Trails --%>
	<table class="trails" cellspacing="0">
	<tr>
	<td>
	<a href="OpuStab.do?command=SearchForm">ANAGRAFICA STABILIMENTI</a> >
	<a href="GestioneAnagraficaAction.do?command=Details&altId=<%=StabilimentoDettaglio.getAltId()%>"> SCHEDA</a> >
	Storico Modifiche
	</td>
	</tr>
	</table>
<%-- Trails --%>

<%
String nomeContainer = "gestioneanagrafica";
request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());
String param = "altId="+StabilimentoDettaglio.getAltId();
%>

<dhv:container name="<%=nomeContainer %>"  selected="modifiche" object="Operatore" param="<%=param%>"  hideContainer="false">

<table class="details" width="100%"cellpadding="10" cellspacing="10" style="border-collapse: collapse">


<tr><th>Utente</th> <th>Data</th> <th>Impresa pre-modifica</th> <th>Impresa post-modifica</th> <th>Indirizzo pre-modifica</th> <th>Indirizzo post-modifica</th> </tr>

<% for (int i = 0; i<storicoList.size(); i++){
	StoricoModifiche storico = (StoricoModifiche) storicoList.get(i);
%>
<tr>
<td><dhv:username id="<%=storico.getIdUtente() %>" /></td>

<td><%=toDateasString(storico.getData()) %></td>
<td>
<b>Ragione sociale</b>: <%=storico.getOperatorePrecedente().getRagioneSociale() %><br/>
<b>Partita IVA</b>: <%=storico.getOperatorePrecedente().getPartitaIva() %><br/>
<b>Sede legale</b>: <%=storico.getIndirizzoOperatorePrecedente().getDescrizioneToponimo() %> <%=storico.getIndirizzoOperatorePrecedente().getVia() %> <%=storico.getIndirizzoOperatorePrecedente().getCivico() %> <%=storico.getIndirizzoOperatorePrecedente().getDescrizioneComune()%><br/>
<b>Rappresentante legale</b>: <%=storico.getSoggettoPrecedente().getNome() %> <%=storico.getSoggettoPrecedente().getCognome() %> (<%=storico.getSoggettoPrecedente().getCodFiscale() %>)<br/>
<b>Residenza</b>: <%=storico.getIndirizzoSoggettoPrecedente().getDescrizioneToponimo() %> <%=storico.getIndirizzoSoggettoPrecedente().getVia() %> <%=storico.getIndirizzoSoggettoPrecedente().getCivico() %> <%=storico.getIndirizzoSoggettoPrecedente().getDescrizioneComune()%><br/>
</td>

<td>
<b>Ragione sociale</b>: <%=storico.getOperatoreNuovo().getRagioneSociale() %><br/>
<b>Partita IVA</b>: <%=storico.getOperatoreNuovo().getPartitaIva() %><br/>
<b>Sede legale</b>: <%=storico.getIndirizzoOperatoreNuovo().getDescrizioneToponimo() %> <%=storico.getIndirizzoOperatoreNuovo().getVia() %> <%=storico.getIndirizzoOperatoreNuovo().getCivico() %> <%=storico.getIndirizzoOperatoreNuovo().getDescrizioneComune()%><br/>
<b>Rappresentante legale</b>: <%=storico.getSoggettoNuovo().getNome() %> <%=storico.getSoggettoNuovo().getCognome() %> (<%=storico.getSoggettoNuovo().getCodFiscale() %>)<br/>
<b>Residenza</b>: <%=storico.getIndirizzoSoggettoNuovo().getDescrizioneToponimo() %> <%=storico.getIndirizzoSoggettoNuovo().getVia() %> <%=storico.getIndirizzoSoggettoNuovo().getCivico() %> <%=storico.getIndirizzoSoggettoNuovo().getDescrizioneComune()%><br/>
</td>

<td><%=storico.getIndirizzoStabilimentoPrecedente().getDescrizioneToponimo() %> <%=storico.getIndirizzoStabilimentoPrecedente().getVia() %> <%=storico.getIndirizzoStabilimentoPrecedente().getCivico() %> <%=storico.getIndirizzoStabilimentoPrecedente().getDescrizioneComune()%></td>
<td><%=storico.getIndirizzoStabilimentoNuovo().getDescrizioneToponimo() %> <%=storico.getIndirizzoStabilimentoNuovo().getVia() %> <%=storico.getIndirizzoStabilimentoNuovo().getCivico() %> <%=storico.getIndirizzoStabilimentoNuovo().getDescrizioneComune()%></td>
</tr>

<%} %>

</table>

















</dhv:container>

