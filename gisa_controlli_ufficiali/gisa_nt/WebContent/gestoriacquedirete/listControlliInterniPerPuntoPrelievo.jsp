<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@page import="org.aspcfs.modules.gestoriacquenew.base.*"%>
<%@page import="java.net.URLEncoder" %>
<%@ include file="../initPage.jsp" %>
<script src="gestoriacquedirete/script.js"></script>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.sql.Date" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="GestoreAcque" class="org.aspcfs.modules.gestoriacquenew.base.GestoreAcque" scope="session" /> <!-- vuoto se non è stato trovato per l'user id quel gestore -->
<jsp:useBean id="PuntoPrelievoRichiesto" class="org.aspcfs.modules.gestoriacquenew.base.PuntoPrelievo" scope="request" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%
	ArrayList<ControlloInterno> controlli31 = (ArrayList<ControlloInterno>)request.getAttribute("controlli31");
	ArrayList<ControlloInterno> controlli28 = (ArrayList<ControlloInterno>)request.getAttribute("controlli28");
	
	int idPuntoPrelievo = PuntoPrelievoRichiesto.getId();
 	   String paramPerContainer = "idPuntoPrelievo="+idPuntoPrelievo;
 	   %>
	 <%if(GestoreAcque.getId() <= 0 && GestoreAcque.getId()!=-999) {%>
		<!-- non e' stato trovato gestore acque per l'user id dell'utente loggato -->
		<br>
		<br>
		<center>
		<font color="red"> Attenzione, per l'utente <%=User.getUsername() %> non e' stato trovato un Gestore Acque Di Rete Associato</font>
		</center>
	
	<%}
	  else 
	  {
	  
	  
	  %>
	  	<br>
		<br>
		<center><b>Lista Controlli per Punto di Prelievo : "<%=PuntoPrelievoRichiesto.getDenominazione() %>"  GESTORE <%=PuntoPrelievoRichiesto.getNomeGestore() %></b></center><br>
		
		<!-- e' stato trovato gestore acque per l'user id dell'utente loggato -->
		<center>
		<div style="max-width:1500px;">
		 <dhv:container name="puntodiprelievodetail_container" selected="Scheda Controlli Interni" object=""  param="<%=paramPerContainer %>">
<%
		if(controlli31.size() == 0)
		{
%>
		     	<br><br><br>
		    	 <center><font color="red">Per il Punto di Prelievo in questione non esistono Controlli Interni decreto 31</font></center>
<%
		}
	    else 
	    {
%>
			Controlli interni decreto 31
			<table class="details" width="100%;">
				<tr>
					<th>Data Prelievo</th>
					<th>Ora</th>
					<th>Controllo di Routine(Parte A)</th>
					<th>Controllo di Verifica(Parte B)</th>
					<th>Controllo di Replica</th>
					<th>Controllo di Ricerca Fitosanitari</th>
					<th>Esito</th>
					<th>Parametri Non Conformi</th>
				</tr>
<%
			    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    for(ControlloInterno ci : controlli31)
			    { 
%>
					<tr style ="cursor:pointer;" onclick="javascript: document.location.href='StabilimentoGestoriAcqueReteNewAction.do?command=DettaglioControlloInterno&idPuntoPrelievo=<%=PuntoPrelievoRichiesto.getId()%>&idControlloInterno=<%=ci.getId()%>';">
						<td><%=ci.getDataInizioControllo() != null ? sdf.format(new Date(ci.getDataInizioControllo().getTime() )) : ""%></td>
						<td><%=printSafe(ci.getOra())%></td>
						<td><b><%=  ci.isProtocolloRoutine() == true ? "&#10004;" : "" %></b></td>
						<td><b><%=  ci.isProtocollVerifica() == true ? "&#10004;" : "" %></b></td>
						<td><b><%=  ci.isProtocolloReplicaMicro() == true ? "&#10004;" : "" %></b></td>
						<td><b><%=  ci.isProtocolloRicercaFitosanitari() == true ? "&#10004;" : "" %></b></td>
						<td align="center"><b><%=  ci.getEsito().equalsIgnoreCase("conforme") ? "<font color=\"green\"> &#10004;</font>" : "<font color=\"red\">Non conforme</font>" %></b></td>
						<td><b><%=  ci.getNonConformita() != null ? ci.getNonConformita() : "" %></b></td>
					</tr>
<% 
				} 
%>
				</table>
<%
			  }
			if(controlli28.size() == 0)
			{
%>
			     	<br><br><br>
			    	 <center><font color="red">Per il Punto di Prelievo in questione non esistono Controlli Interni decreto 28</font></center>
<%
			}
		    else 
		    {
%>
				<br/>
				<br/>
				Controlli interni decreto 28
				<table class="details" width="100%;">
				 
					<tr>
					
						<th>Data Prelievo</th>
						<th>Ora</th>
						<th>Trizio</th>
						<th>Radon</th>
						<th>Dose indicativa</th>
						<th>Alfa</th>
						<th>Beta</th>
						<th>Esito</th>
						<th>Parametri Non Conformi</th>
					
					</tr>
<%
					   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					   for(ControlloInterno ci : controlli28)
					   { 
%>
							
							<tr style ="cursor:pointer;" onclick="javascript: document.location.href='StabilimentoGestoriAcqueReteNewAction.do?command=DettaglioControlloInterno&idPuntoPrelievo=<%=PuntoPrelievoRichiesto.getId()%>&idControlloInterno=<%=ci.getId()%>';">
								<td><%=ci.getDataInizioControllo() != null ? sdf.format(new Date(ci.getDataInizioControllo().getTime() )) : ""%></td>
								<td><%=printSafe(ci.getOra())%></td>
								<td><%=printSafe(ci.getTrizio())%>  <%=printSafe(ci.getTrizio_concentrazioneMar())%> </td>
								<td><%=printSafe(ci.getRadon())%> <%=printSafe(ci.getRadon_concentrazioneMar())%></td>
								<td><%=printSafe(ci.getDose())%></td>
								<td><%=printSafe(ci.getAlfa())%> <%=printSafe(ci.getDI_alfaTotaleMar())%></td>
								<td><%=printSafe(ci.getBeta())%> <%=printSafe(ci.getDI_betaTotaleMar())%></td>
								<%
								if(ci.getEsito()!=null)
								{
								%>
								<td align="center"><b><%=  ci.getEsito().equalsIgnoreCase("conforme") ? "<font color=\"green\"> &#10004;</font>" : "<font color=\"red\">Non conforme</font>" %></b></td>
								<%
								}
								else
								{
									%>
									<td align="center"></td>
									<%	
								}
								%>
								
								<td><b><%=  ci.getNonConformita() != null ? ci.getNonConformita() : "" %></b></td>
							</tr>
<% 
						} 
%>
						</table>
<%
				  }
%> 
		</dhv:container>
		</div>
		</center>
	
	<%} %>
	
	
	
	  <%! public String printSafe(String toPrint)
      {
      	if(toPrint == null )
      	{
      		toPrint = "";
      	}
      	return toPrint;
      	
      }%>

</body>
</html>