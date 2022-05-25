<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
 

<%@page import="java.net.InetAddress"%>
<%@page import="org.aspcfs.modules.lineeattivita.base.*"%>
<%@page import="java.util.*" %>
<%@page import="org.aspcfs.modules.allerte.base.AllegatoF.Azione" %>
<%@page import="org.aspcfs.modules.allerte.base.AslCoinvolte" %>

<jsp:useBean id="scelta" class="java.lang.String" scope="request"/>
<jsp:useBean id="num_cu_regionali" class="java.lang.String" scope="request"/>
<jsp:useBean id="num_cu_pianificati" class="java.lang.String" scope="request"/>
<jsp:useBean id="id_allerta" class="java.lang.String" scope="request"/>
<jsp:useBean id="allerta" class="org.aspcfs.modules.allerte.base.Ticket" scope="request"/>
<jsp:useBean id="tipiAlimenti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="tipiAlimentiTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="aslCoinvolta" class="org.aspcfs.modules.allerte.base.AslCoinvolte" scope="request"/>
<jsp:useBean id="numCu" class="java.lang.String" scope="request"/>
<jsp:useBean id="num_cu_esito_partita" class="java.util.HashMap" scope="request"/>  
<jsp:useBean id="num_cu_esiti" class="java.util.HashMap" scope="request"/>  
<jsp:useBean id="num_cu_ritiro" class="java.util.HashMap" scope="request"/>  
<jsp:useBean id="num_cu_richiamo" class="java.util.HashMap" scope="request"/>  
<jsp:useBean id="num_cu_azioni" class="java.util.HashMap" scope="request"/>  
<jsp:useBean id="num_cu_comunicazione_rischio" class="java.util.HashMap" scope="request"/>  
<jsp:useBean id="note_lista" class="java.util.ArrayList" scope="request"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheda</title>
</head>
<link rel="stylesheet" type="text/css" media="all" documentale_url="" href="gestione_documenti/schede/schede_layout.css" />

<style>@media all{
.boxIdDocumento {
	left: 5px;
	top:90px;
	}
.boxOrigineDocumento {
	left: 75px;
	top:85px;
	}
}</style>
<body>

<div align="left"><img style="text-decoration: none;" width="80" height="80" documentale_url="" src="gestione_documenti/schede/images/regionecampania.jpg" /></div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

<div align="right">
<label class="titolo">ALLEGATO F - ESITI ACCERTAMENTI</label><br/>
REGIONE CAMPANIA<br/>
AGC ASSISTENZA SANITARIA<br/>
NODO REGIONALE ALLERTE<br/>
</div>

<br/>
OGGETTO : SISTEMA DI ALLERTA; COMUNICAZIONE ESITI ACCERTAMENTI ALLERTA<br/>
<%=allerta.getIdAllerta().toUpperCase() %><br/><br/><br/>

IN RELAZIONE ALLA COMUNICAZIONE PROT <%=allerta.getIdAllerta() %> DEL <%=toDateasString(allerta.getDataApertura()) %> RIGUARDANTE<br/>
L'ATTIVAZIONE DEL SISTEMA DI ALLERTA PER IL SEGUENTE PRODOTTO ( RIPORTARE LA<br/>
DENOMINAZIONE , IL NUMERO DI LOTTO , IL FABBRICANTE O DISTRIBUTORE) :<br/><br/><br/>

<% if (allerta.getAlimentiOrigineAnimaleNonTrasformati()>0) { %>
ALIMENTI DI ORIGINE ANIMALE NON TRASFORMATI: <%=tipiAlimenti.getSelectedValue(allerta.getAlimentiOrigineAnimaleNonTrasformati()).toUpperCase()%><br/>
<%} if (allerta.getAlimentiOrigineAnimaleTrasformati() >0 ) { %>
ALIMENTI DI ORIGINE ANIMALE TRASFORMATI: <%=tipiAlimentiTrasformati.getSelectedValue(allerta.getAlimentiOrigineAnimaleTrasformati()).toUpperCase()%><br/>
<%} %>
<% if (allerta.getAlimentiBevande() ) { %>
BEVANDE: <%=allerta.getNoteAlimenti().toUpperCase()%><br/>
<%} %>
DENOMINAZIONE PRODOTTO : <%=(allerta.getDenominazione_prodotto()!=null && !allerta.getDenominazione_prodotto().equals("null")) ? allerta.getDenominazione_prodotto().toUpperCase() : "--" %><br/><br/>
NUMERO LOTTO : <%=(allerta.getNumero_lotto()!=null && !allerta.getNumero_lotto().equals("null")) ? allerta.getNumero_lotto() : "--"%><br/><br/>
FABBRICANTE O PRODUTTORE : <%= (allerta.getFabbricante_produttore()!=null && !allerta.getFabbricante_produttore().equals("null")) ? allerta.getFabbricante_produttore().toUpperCase() : "--" %><br/><br/>
DATA SCADENZA/TERMINE MINIMO DI CONSERVAZIONE : <%=(allerta.getData_scadenza_allerta()!=null) ? toDateasString(allerta.getData_scadenza_allerta()) : "--" %> <br/><br/>
<br/><br/><br/>

<% if (request.getAttribute("scelta").equals("asl")) {%>
SU <%=aslCoinvolta.getControlliUfficialiRegionaliPianificati() %> CONTROLLI ASSEGNATI DALLA REGIONE, SUL TERRITORIO DI COMPETENZA DI<br/>
QUESTA ASL,SONO STATI PIANIFICATI <%=aslCoinvolta.getCu_pianificati() %> CONTROLLI DALL'ASL ED ESEGUITI <%=numCu %> CONTROLLI,<br/>
<% } else if (request.getAttribute("scelta").equals("globale")) {%>
SU <%=num_cu_regionali %> CONTROLLI ASSEGNATI DALLA REGIONE, SUL TERRITORIO DI COMPETENZA DI<br/>
TUTTE LE ASL,SONO STATI PIANIFICATI <%=num_cu_pianificati %> CONTROLLI DALL'ASL ED ESEGUITI <%=numCu %> CONTROLLI,<br/>
<%} %>
<br/><br/>
NEL CORSO DEI QUALI È STATO RISCONTRATO CHE :<br/><br/>

<table width="100%">
<col width="80%">
<tr><td><b>DISTRIBUZIONE DELLA PARTITA</b></td>
<td><b>NUMERO</b></td></tr>
 
 <%if (num_cu_esito_partita.isEmpty()) {%>
	
	<tr><td><b>IL PRODOTTO NON E' STATO ULTERIORMENTE DISTRIBUITO</b></td></tr>
	<%} else	
	{
		Iterator<String> itEsitiPartita = num_cu_esito_partita.keySet().iterator();
		while (itEsitiPartita.hasNext())
		{
			String azione_value = (String) itEsitiPartita.next();
			Azione azione = (Azione) num_cu_esito_partita.get(azione_value);
			
			String label_esito = (String) azione_value.toUpperCase();
			Integer num = (Integer) azione.getnumCu(); %>
			
			<tr><td><%=label_esito %></td><td><%=num %></td></tr>
		<% }  }%>
			 
 <tr><td><b>RICEVUTA COMUNICAZIONE DEL RISCHIO DAL PRODUTTORE/FORNITORE</b></td></tr>
 <%
 Iterator itcom_rischio= num_cu_comunicazione_rischio.keySet().iterator();
 	while (itcom_rischio.hasNext())
		{
			String com_rischio_val = (String) itcom_rischio.next();
			Integer num = (Integer) num_cu_comunicazione_rischio.get(com_rischio_val);	
	%>		

<tr><td><%=com_rischio_val.toUpperCase()  %></td><td> <%=num %></td></tr>
<%		}
%>
<tr><td><b>EVENTUALI INFORMAZIONI AGGIUNTIVE</b></td></tr>
<% if (! note_lista.isEmpty())
{
	int i = 0;
	while (i<=note_lista.size())
	{String eventuali_note = (String) note_lista.get(i);
		%>
		<tr><td> <%=i+1 %>) <%=eventuali_note.trim().toUpperCase()%></td></tr>
		<%
		i++;
		if (i==note_lista.size())
			break;
	}
}
%>
			

<tr><td><b>PROCEDURA DI RITIRO</b></td></tr>
<%Iterator<String> itRitiro = num_cu_ritiro.keySet().iterator();
		while (itRitiro.hasNext())
		{
			String esitValue = (String) itRitiro.next();
			Integer numCuEsiti = (Integer) num_cu_ritiro.get(esitValue);
			%>
<tr><td><%=esitValue.toUpperCase() %></td> <td><%=numCuEsiti %></td></tr>
		<% }%>

<tr><td><b>PROCEDURA DI RICHIAMO</b></td></tr>

<%Iterator<String> itRichiamo= num_cu_richiamo.keySet().iterator();
		while (itRichiamo.hasNext())
		{
			String esitValue = (String) itRichiamo.next();
			Integer numCuEsiti = (Integer) num_cu_richiamo.get(esitValue);%>
<tr><td><%=esitValue.toUpperCase() %></td><td><%=numCuEsiti %></td></tr>
			
		<%} %>

<tr><td><b>ESITO DEL CONTROLLO</b></td></tr>

<%Iterator<String> itEsiti = num_cu_esiti.keySet().iterator();
		while (itEsiti.hasNext())
		{
			String esitValue = (String) itEsiti.next();
			Integer numCuEsiti = (Integer) num_cu_esiti.get(esitValue);%>
			<tr><td><%=esitValue.toUpperCase() %></td><td><%=numCuEsiti %></td></tr>
			
			<%} %>

<tr><td><b>AZIONI ADOTTATE</b></td></tr>

<%Iterator<String> itAzioni= num_cu_azioni.keySet().iterator();
		while (itAzioni.hasNext())
		{
			String azione_value = (String) itAzioni.next();
			Azione azione = (Azione) num_cu_azioni.get(azione_value);
			
			String label_esito = (String) azione_value.toUpperCase();
			Integer num = (Integer) azione.getnumCu();%>
			<tr><td><%=label_esito.toUpperCase() %></td><td><%=num %></td></tr>
			
			<%
			if (!azione.getLista_sotto_azioni().isEmpty())
			{
				for (Azione azione2 :azione.getLista_sotto_azioni() )
				{
					
				String lab = "";
				if(azione2.getArticolo()!=null)
					lab+="					- di cui per Articolo "+azione2.getArticolo();
				if(azione2.getNorma_violata()!=null)
					lab+="					- di cui per Norma Violata "+azione2.getNorma_violata();
				
				if(!lab.equals(""))
				{
					String label_esito1 = (String) lab.toUpperCase();
					Integer num1 = (Integer) azione2.getnumCu();
					%>
					<tr><td><%=label_esito1.toUpperCase() %></td><td><%=num1 %></td></tr>
				<%
				if(azione2.getNote_eventuali()!=null && !azione2.getNote_eventuali().equals("") )
				{
				String label_esito2 = "Eventuali Informazioni Aggiuntive : "+azione2.getNote_eventuali();
				%>
				<tr><td><%=label_esito2.toUpperCase() %></td></tr>
					<%}
				
				}
				
				}
				
				
			}
			
		}%>




 </table>
 
 
<br/><br/><br/><br/><br/>

<%if (request.getAttribute("scelta").equals("asl")) { %>
<div align="left"><b>DATA</b></div> <div align="right"><b>IL REFERENTE ALLERTE ASL</b></div>
<% } else if (request.getAttribute("scelta").equals("globale")) { %>
<div align="left"><b>DATA</b></div> <div align="right"><b>IL REFERENTE</b></div>

<%} %>












</body>
