<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ taglib uri="/WEB-INF/taglib/systemcontext-taglib.tld" prefix="sc"%>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>
<%@page import="com.darkhorseventures.framework.actions.ActionContext"%>
<%@page import="java.sql.*,java.util.HashMap,java.util.Map"%>
<%@page import="org.aspcfs.modules.opu.base.ComuniAnagrafica"%>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="mapAttributi" class="java.util.HashMap" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="OperatoreDettaglio" class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />


<%@ include file="../initPage.jsp"%>




<dhv:evaluate if="<%=!isPopup(request)%>">
	<table class="trails" cellspacing="0">
		<tr>
			<td>Impresa -> Variazioni Eseguite Su Impresa </td>
		</tr>
	</table>
</dhv:evaluate>



<%
String param = "stabId="+StabilimentoDettaglio.getIdStabilimento()+"&opId=" + StabilimentoDettaglio.getIdOperatore();
%>
<%
String nomeContainer = StabilimentoDettaglio.getContainer();

request.setAttribute("Operatore",StabilimentoDettaglio.getOperatore());
%>
<dhv:container name="<%=nomeContainer %>"  selected="details" object="Operatore" param="<%=param%>"  hideContainer="false">


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
	<tr>
		<th colspan="5">Variazioni Sede Legale</th>
	</tr>
	<tr>
		<th >Comune</th>
		<th >Provincia</th>
		<th >Indirizzo</th>
		<th >Data Modifica/Inserimento</th>
		<th >Utente di Modifica</th>
		
	</tr>
    
  <%
  
  Iterator<Indirizzo> itInd = OperatoreDettaglio.getListaSediOperatore().iterator();
  while (itInd.hasNext())
  {
	  Indirizzo ind = itInd.next();
	  %>
	  <tr>
	  <td><%=ind.getDescrizioneComune() %></td>
	  <td><%=ind.getDescrizione_provincia() %></td>
	  <td><%=ind.getVia() %></td>
	  <td><%=ind.getModified() %></td>
	  <td>
	 	 <dhv:username id="<%= ind.getModifiedBy() %>" /> - 
	 	 <dhv:asl id="<%= ind.getModifiedBy() %>" />
	 	 
	</td>
	  
	  </tr>
	  <%
	  
  }
  
  %>
  

</table>
<br>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
	<tr>
		<th colspan="7">Variazioni Soggetto Fisico</th>
	</tr>
	<tr>
		<th >Nome</th>
		<th >Cognome</th>
		<th >Codice Fiscale</th>
		<th>Comune / Provincia  Nascita </th>
		<th>Residenza</th>
		<th >Data Modifica/Inserimento</th>
		<th >Utente di Modifica</th>
		
	</tr>
    
  <%
  
  Iterator<SoggettoFisico> itsf = OperatoreDettaglio.getStoricoSoggettoFisico().iterator();
  while (itsf.hasNext())
  {
	  SoggettoFisico sf = itsf.next();
	  %>
	  <tr>
	  <td><%=sf.getNome() %></td>
	  <td><%=sf.getCognome()%></td>
	  <td><%=sf.getCodFiscale() %></td>
	  <td><%=sf.getComuneNascita() + "/"+ sf.getProvinciaNascita() %></td>
	  <td><%=sf.getIndirizzo().toString() %></td>
	  <td><%=sf.getModified() %></td>
	  <td>
	 	 <dhv:username id="<%= sf.getModifiedBy() %>" /> - 
	 	 <dhv:asl id="<%= sf.getModifiedBy() %>" />
	 	 
	</td>
	  
	  </tr>
	  <%
	  
  }
  
  %>
  

</table>


</dhv:container>
