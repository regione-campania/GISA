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
<%@page import="java.util.ArrayList" %>

<jsp:useBean id="schedaDetails" class="org.aspcf.modules.report.util.SchedaImpresa" scope="request"/>
<jsp:useBean id="OrgImbarcazioni" class="org.aspcfs.modules.imbarcazioni.base.Organization" scope="request" />
<jsp:useBean id="OrgOSM" class="org.aspcfs.modules.osmregistrati.base.Organization" scope="request" />
<jsp:useBean id="OrgMolluschi" class="org.aspcfs.modules.molluschibivalvi.base.Organization" scope="request" />
<jsp:useBean id="OrgCanili" class="org.aspcfs.modules.canili.base.Organization" scope="request" />
<jsp:useBean id="OrgOperatoriCommerciali" class="org.aspcfs.modules.operatori_commerciali.base.Organization" scope="request" />
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../../initPage.jsp" %>
<%@ include file="barcode.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Scheda</title>
</head>
<link rel="stylesheet" type="text/css" media="all" documentale_url="" href="gestione_documenti/schede/schede_layout.css" />

<body>

<table width="100%">
<col width="33%"><col width="33%"><col width="33%">
<tr>
<td><div align="left"><img style="text-decoration: none;" width="80" height="80" documentale_url="" src="gestione_documenti/schede/images/regionecampania.jpg" /></div>
<div class="boxIdDocumento"></div>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

</td>
<td><center><b><label class="titolo">Scheda Impresa</label></b></center>
</td>
<td>
<% if (schedaDetails.getAsl()!=null) { %>
<div align="right"><img style="text-decoration: none;" height="80" documentale_url="" src="gestione_documenti/schede/images/<%=schedaDetails.getAsl().toLowerCase() %>.jpg" /></div>
<%} %>
</td>
</tr>
</table>


<% if (schedaDetails.getOperatore().equals("Imbarcazioni")){ %>
<table cellpadding="5" style="border-collapse: collapse">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Informazione primaria</b></td></tr>
<tr><td class="blue" >Impresa</td>
<td class="layout"><%=schedaDetails.getRagioneSociale() %></td></tr>
<tr><td class="blue">Numero registrazione</td>
<td class="layout">
<%if (schedaDetails.getNumeroRegistrazione()!=null && !schedaDetails.getNumeroRegistrazione().equals("")){ %>
<img src="<%=createBarcodeImage(schedaDetails.getNumeroRegistrazione().replaceAll(" ", ""))%>" />
<%} else { %>
NON DEFINITO
<%} %>
</td></tr>
<tr><td class="blue">Ufficio marittimo di provenienza</td>
<td class="layout"><%=schedaDetails.getFuoriRegione() %></td></tr>
<tr><td class="blue">Targa</td>
<td class="layout"><%=schedaDetails.getTarga() %></td></tr>
<tr><td class="blue">Nome dell'imbarcazione</td>
<td class="layout"><%=(schedaDetails.getNomeImbarcazione()!=null) ? schedaDetails.getNomeImbarcazione() : "" %></td></tr>
<tr><td class="blue">Categoria di rischio</td>
<td class="layout"><%=schedaDetails.getCategoriaRischio() %></td></tr>
<tr><td class="blue">Prossimo controllo con la tecnica della sorveglianza</td>
<td class="layout"><%=toDateasString(schedaDetails.getProssimoControllo()) %></td></tr>
<tr><td class="blue">Partita IVA</td>
<td class="layout"><%=(schedaDetails.getPartitaIva() != null ) ? schedaDetails.getPartitaIva() : "" %></td></tr>
<tr><td class="blue">Tonnellate di stazza</td>
<td class="layout"><%=(schedaDetails.getCodiceInterno()!=null) ? schedaDetails.getCodiceInterno() : "" %></td></tr>
 <%if(OrgImbarcazioni.getTipoPesca().size()!=0){ 
	
	HashMap<Integer,String> tipiPesca =OrgImbarcazioni.getTipoPesca();
	Set<Integer> setkiavi = tipiPesca.keySet();
	Iterator<Integer> iteraTipi=setkiavi.iterator();
	
  %>
  <tr>
  
    <td class="blue">
      Tipo di pesca
    </td>
    <td class="layout">
    <% while(iteraTipi.hasNext()){
						int chiave = iteraTipi.next();
						String value = tipiPesca.get(chiave);
						
						%>
					<%="- "+tipiPesca.get(chiave)%><br> 

					<%} %>  
    	
      <%-- <%=(orgImbarcazioni.getTipo_struttura() != null && !OrgDetails.getTipo_struttura().equals("")) ? toHtmlValue(OrgDetails.getTipo_struttura()) : "N.D" %>--%>
    </td>
    
  </tr>
  <%} %>
  
  <%if(OrgImbarcazioni.getSistemaPesca().size()!=0){ 
	
	HashMap<Integer,String> sistemiPesca =OrgImbarcazioni.getSistemaPesca();
	Set<Integer> setkiavi = sistemiPesca.keySet();
	Iterator<Integer> iteraSistemi=setkiavi.iterator();
	
  %>
  <tr>
   
  
    <td class="blue" >
      Sistema di pesca
    </td>
    <td class="layout">
    <%
        	while(iteraSistemi.hasNext()){
						int chiave = iteraSistemi.next();
						String value = sistemiPesca.get(chiave);
						
						%>
					<%="- "+sistemiPesca.get(chiave)%><br> 

					<%} %>
					  
     <%--  <%= (OrgDetails.getDuns_type() != null && !OrgDetails.getDuns_type().equals("")) ? toHtmlValue(OrgDetails.getDuns_type()) : "N.D" %>--%>
    </td>
  </tr>
  <% } %>
  <tr><td class="blue">Data iscrizione agli uffici marittimi</td>
<td class="layout"><%=(schedaDetails.getDataPresentazione() != null) ? toDateasString(schedaDetails.getDataPresentazione()) : "" %></td></tr>
<tr><td class="blue">Numero iscrizione agli uffici marittimi</td>
<td class="layout"><%=(schedaDetails.getAutorizzazione() != null) ? schedaDetails.getAutorizzazione() : "" %></td></tr>
  <tr><td class="blue">Data cancellazione dagli uffici marittimi</td>
<td class="layout"><%=(schedaDetails.getData2() != null) ? toDateasString(schedaDetails.getData2()) : "" %></td></tr>
<tr><td class="blue">Presenza a bordo di un impianto di refrigerazione</td>
<td class="layout"><%=(schedaDetails.getFlagSelezione()!=null) ? schedaDetails.getFlagSelezione() : "" %></td></tr>
<tr><td class="blue">Note</td>
<td class="layout"> <%=(schedaDetails.getNote()!=null) ? schedaDetails.getNote() : "" %></td></tr>
</table>
<br/><br/>
<table cellpadding="5" style="border-collapse: collapse">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Indirizzi</b></td></tr>

<tr><td class="blue">Sede Legale armatore</td>
<td class="layout"><%= (schedaDetails.getSedeLegale()!=null) ? schedaDetails.getSedeLegale() : "" %></td></tr>
<tr><td class="blue">Ormeggio abituale</td>
<td class="layout"><%= (schedaDetails.getSedeOperativa()!=null) ?  schedaDetails.getSedeOperativa()  : "" %></td></tr>

</table>

<% } else if (schedaDetails.getOperatore().equals("OSM")) { %>

<table cellpadding="5" style="border-collapse: collapse">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Informazione primaria</b></td></tr>
<tr><td class="blue" >Impresa</td>
<td class="layout"><%=schedaDetails.getRagioneSociale() %></td></tr>
<tr><td class="blue">Numero registrazione</td>
<td class="layout">
<%if (schedaDetails.getNumeroRegistrazione()!=null && !schedaDetails.getNumeroRegistrazione().equals("")){ %>
<img src="<%=createBarcodeImage("AIT"+schedaDetails.getNumeroRegistrazione())%>" />
<%} else { %>
NON DEFINITO
<%} %>
</td></tr>
<tr><td class="blue">Attività</td>
<% if (OrgOSM==null){ %>
<td class="layout"><%=(schedaDetails.getTipoAttivita()!=null) ? schedaDetails.getTipoAttivita() : "" %></td>
<%} else { %>
<td class="layout">	
					
					 <% String  selezione2="";
			    		HashMap<Integer,String> lista2=OrgOSM.getListaAttivita();
			    		Iterator<Integer> valori2=OrgOSM.getListaAttivita().keySet().iterator();
			    		
			    		while(valori2.hasNext()){
			    			String Sel2=lista2.get(valori2.next());			    			
			    				selezione2=Sel2;
			    				out.print(selezione2);%></br>
			    				
			    			<%
			    			}			    		
			    		 %></td>
<% } %> </tr>
<tr><td class="blue">Decreto</td>
<td class="layout"><%=(schedaDetails.getDecreto()!=null) ? schedaDetails.getDecreto() : "" %></td></tr>
<tr><td class="blue">Partita IVA</td>
<td class="layout"><%=schedaDetails.getPartitaIva() %></td></tr>
<tr><td class="blue">Codice fiscale</td>
<td class="layout"><%=schedaDetails.getCodiceFiscale() %></td></tr>
<tr><td class="blue">Categoria di rischio</td>
<td class="layout"><%=schedaDetails.getCategoriaRischio() %></td></tr>
<tr><td class="blue">Prossimo controllo con la tecnica della sorveglianza</td>
<td class="layout"><%=toDateasString(schedaDetails.getProssimoControllo()) %></td></tr>
<tr><td class="blue">Stato OSM</td>
<td class="layout"><%=schedaDetails.getStatoImpresa() %></td></tr>
<tr><td class="blue">Data inizio attività</td>
<td class="layout"><%=(schedaDetails.getDataInizioAttivita()!= null) ? toDateasString(schedaDetails.getDataInizioAttivita()) :  "" %></td></tr>

</table>
<br/><br/>
<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Titolare o rappresentante legale</b></td></tr>

<tr><td class="blue">Nome</td>
<td class="layout"><%= (schedaDetails.getNomeRappresentante()!=null) ? schedaDetails.getNomeRappresentante() : "" %>&nbsp; </td></tr>
<tr><td class="blue">Cognome</td>
<td class="layout">	<%=(schedaDetails.getCognomeRappresentante()!=null) ? schedaDetails.getCognomeRappresentante() : "" %>&nbsp; </td></tr>
<tr><td class="blue">Luogo e data di nascita</td>
<td class="layout">	<%= (schedaDetails.getComuneNascitaRappresentante()!=null) ? schedaDetails.getComuneNascitaRappresentante() : ""%>&nbsp;     <%= toDateasString(schedaDetails.getDataNascitaRappresentante())%></td></tr>
<tr><td class="blue">Email</td>
<td class="layout"><%= (schedaDetails.getMailRappresentante()!=null) ? schedaDetails.getMailRappresentante() : "" %>&nbsp; </td></tr>
<tr><td class="blue">Telefono</td>
<td class="layout">	<%= (schedaDetails.getTelefonoRappresentante()!=null) ? schedaDetails.getTelefonoRappresentante() : "" %>&nbsp; </td></tr>
<tr><td class="blue">Fax</td>
<td class="layout"><%= ( schedaDetails.getFaxRappresentante()!=null ) ? schedaDetails.getFaxRappresentante() : ""%>&nbsp; </td></tr>

</table>
<br/><br/>
<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Indirizzi</b></td></tr>
<tr><td class="blue">Sede legale</td>
<td class="layout"><%= (schedaDetails.getSedeLegale()!=null) ?  schedaDetails.getSedeLegale()  : "" %></td></tr>

<tr><td class="blue">Sede operativa</td>
<td class="layout"><%= (schedaDetails.getSedeOperativa()!=null) ?  schedaDetails.getSedeOperativa()  : "" %></td></tr>

</table>
<br/><br/>
<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Dettagli addizionali</b></td></tr>
<tr><td class="blue">Note</td>
<td class="layout"><%= (schedaDetails.getNote()!=null) ?  schedaDetails.getNote()  : "" %></td></tr>
</table>

<% } else if (schedaDetails.getOperatore().equals("Molluschi")) { %>

<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Informazione primaria</b></td></tr>
<tr><td class="blue" >Impresa</td>
<td class="layout"><%=schedaDetails.getRagioneSociale() %></td></tr>
<tr><td class="blue">Zona di produzione</td>
<td class="layout"><%=schedaDetails.getZonaDiProduzione() %></td></tr>
<tr><td class="blue">Stato</td>
<td class="layout"><%=OrgMolluschi.getStato() %></td></tr>
<tr><td class="blue">Classificazione</td>
<td class="layout"><%=(schedaDetails.getClassificazione()!=null) ? schedaDetails.getClassificazione() : "" %></td></tr>
<tr><td class="blue">Specie Molluschi</td>
<td class="layout">
		<%
		Iterator<Integer> itKey = OrgMolluschi.getTipoMolluschiInZone().keySet().iterator();
		while (itKey.hasNext())
		{
		int key = itKey.next(); 
		String cammino = OrgMolluschi.getTipoMolluschiInZone().get(key);
		%>
		<%=cammino %>&nbsp; 
		<%
		}
		%>
<td></tr>

</table>
<br/><br/>
<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Dettagli classificazione</b></td></tr>

<tr><td class="blue">Numero decreto</td>
<td class="layout"><%= (schedaDetails.getDecreto()!=null) ? schedaDetails.getDecreto() : "" %>&nbsp; </td></tr>
<tr><td class="blue">Data classificazione</td>
<td class="layout">	<%=(schedaDetails.getDataClassificazione()!=null) ? toDateasString(schedaDetails.getDataClassificazione()) : "" %>&nbsp; </td></tr>
<tr><td class="blue">Data fine classificazione</td>
<td class="layout">	<%=(schedaDetails.getDataFineClassificazione()!=null) ? toDateasString(schedaDetails.getDataFineClassificazione()) : "" %>&nbsp; </td></tr>

</table>
<br/><br/>
<table cellpadding="5" style="border-collapse: collapse"  width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Sito</b></td></tr>
<tr><td class="blue">Comune</td>
<td class="layout"><%= (schedaDetails.getComuneSede()!=null) ?  schedaDetails.getComuneSede()  : "" %></td></tr>
<tr><td class="blue">Località</td>
<td class="layout"><%= (schedaDetails.getComuneSedeOp()!=null) ?  schedaDetails.getComuneSedeOp()  : "" %></td></tr>

</table>


<%if(OrgMolluschi.getTipoMolluschi()==4)  {	%>
<br/><br/>
<table cellpadding="5" style="border-collapse: collapse"  width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Zone non in concessione / Impianti abusivi</b></td></tr>

<tr>
		<td class="blue"> Nome</td>
		<td class="layout"><%=(schedaDetails.getNomeRappresentante()!=null) ? schedaDetails.getNomeRappresentante() : "" %></td>
	</tr>
	
	<tr>
		<td class="blue"> Cognome</td>
		<td class="layout"><%=toHtml2(schedaDetails.getCognomeRappresentante()) %></td>
	</tr>
	
	<tr>
		<td class="blue"> Note</td>
		<td class="layout"><%=toHtml2(schedaDetails.getNote()) %></td>
	</tr>
	
	</table>
	<%} %>










<% } else if (schedaDetails.getOperatore().equals("Canile")) { %>

<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Informazione primaria</b></td></tr>
<tr><td class="blue" >Nome canile</td>
<td class="layout"><%=schedaDetails.getRagioneSociale() %></td></tr>
<tr><td class="blue">Partita IVA</td>
<td class="layout"><%=(schedaDetails.getPartitaIva()!=null) ? schedaDetails.getPartitaIva() : "" %></td></tr>
<tr><td class="blue">Codice fiscale</td>
<td class="layout"><%=(schedaDetails.getCodiceFiscale()!=null) ? schedaDetails.getCodiceFiscale() : "" %></td></tr>
<tr><td class="blue">Categoria di rischio</td>
<td class="layout"><%=schedaDetails.getCategoriaRischio() %></td></tr>
<tr><td class="blue">Prossimo controllo con la tecnica della sorveglianza</td>
<td class="layout"><%=toDateasString(schedaDetails.getProssimoControllo()) %></td></tr>
<tr><td class="blue">Autorizzazione</td>
<td class="layout"><%=schedaDetails.getAutorizzazione() %></td></tr>

</table>
<br/><br/>


<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Indirizzi</b></td></tr>
<tr><td class="blue">Sede legale</td>
<td class="layout"><%= (schedaDetails.getSedeLegale()!=null) ?  schedaDetails.getSedeLegale()  : "" %></td></tr>

<tr><td class="blue">Sede operativa</td>
<td class="layout"><%= (schedaDetails.getSedeOperativa()!=null) ?  schedaDetails.getSedeOperativa()  : "" %></td></tr>

</table>
<br/><br/>
<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Dettagli addizionali</b></td></tr>
<tr><td class="blue">Note</td>
<td class="layout"><%= (schedaDetails.getNote()!=null) ?  schedaDetails.getNote()  : "" %></td></tr>
</table>

<% } else if (schedaDetails.getOperatore().equals("Colonia")) { %>


<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Informazione primaria</b></td></tr>
<tr><td class="blue" >Numero protocollo</td>
<td class="layout"><%=schedaDetails.getRagioneSociale() %></td></tr>
<tr><td class="blue">Categoria di rischio</td>
<td class="layout"><%=schedaDetails.getCategoriaRischio() %></td></tr>
<tr><td class="blue">Data registrazione</td>
<td class="layout"><%=toDateasString(schedaDetails.getDataPresentazione()) %></td></tr>
<tr><td class="blue">Referente</td>
<td class="layout"><%=schedaDetails.getNomeRappresentante() %></td></tr>
<tr><td class="blue">Codice Fiscale</td>
<td class="layout"><%=schedaDetails.getCodiceFiscaleRappresentante() %></td></tr>
</table>
<br/><br/>


<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Indirizzi</b></td></tr>
<tr><td class="blue">Sede legale</td>
<td class="layout"><%= (schedaDetails.getSedeLegale()!=null) ?  schedaDetails.getSedeLegale()  : "" %></td></tr>

<tr><td class="blue">Sede operativa</td>
<td class="layout"><%= (schedaDetails.getSedeOperativa()!=null) ?  schedaDetails.getSedeOperativa()  : "" %></td></tr>

</table>
<br/><br/>
<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Dettagli addizionali</b></td></tr>
<tr><td class="blue">Note</td>
<td class="layout"><%= (schedaDetails.getNote()!=null) ?  schedaDetails.getNote()  : "" %></td></tr>
</table>

<% } else if (schedaDetails.getOperatore().equals("OperatoreCommerciale")) { %>

<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Informazione primaria</b></td></tr>
<tr><td class="blue" >Impresa</td>
<td class="layout"><%=schedaDetails.getRagioneSociale() %></td></tr>
<tr><td class="blue" >Specie animali</td>
<td class="layout">
<%HashMap<Integer,String> listaTipi = OrgOperatoriCommerciali.getListatipologiaOperatoriCommerciali() ;
Iterator<Integer> itKiavi = listaTipi.keySet().iterator();
while (itKiavi.hasNext())
        	{%>
        		<b><%=listaTipi.get(itKiavi.next())%></b><br/>
        	<%} %>
</td></tr>
<tr><td class="blue">Categoria di rischio</td>
<td class="layout"><%=schedaDetails.getCategoriaRischio() %></td></tr>
<tr><td class="blue">Partita IVA</td>
<td class="layout"><%=schedaDetails.getPartitaIva() %></td></tr>
<tr><td class="blue">Autorizzazione</td>
<td class="layout"><%=(schedaDetails.getAutorizzazione()!=null) ? schedaDetails.getAutorizzazione() : "" %></td></tr>
</table>
<br/><br/>

<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Titolare o rappresentante legale</b></td></tr>
<tr><td class="blue" >Codice fiscale</td>
<td class="layout"><%=schedaDetails.getCodiceFiscaleRappresentante() %></td></tr>
<tr><td class="blue">Nome</td>
<td class="layout"><%=schedaDetails.getNomeRappresentante() %></td></tr>
<tr><td class="blue">Cognome</td>
<td class="layout"><%=schedaDetails.getCognomeRappresentante() %></td></tr>
<tr><td class="blue">Data nascita</td>
<td class="layout"><%=(schedaDetails.getDataNascitaRappresentante()!=null) ? toDateasString(schedaDetails.getDataNascitaRappresentante()) : "" %></td></tr>
<tr><td class="blue">Comune nascita</td>
<td class="layout"><%=schedaDetails.getComuneNascitaRappresentante() %></td></tr>

</table>
<br/><br/>

<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Indirizzi</b></td></tr>
<tr><td class="blue">legale</td>
<td class="layout"><%= (schedaDetails.getSedeLegale()!=null) ?  schedaDetails.getSedeLegale()  : "" %></td></tr>

<tr><td class="blue">Ausiliario</td>
<td class="layout"><%= (schedaDetails.getSedeOperativa()!=null) ?  schedaDetails.getSedeOperativa()  : "" %></td></tr>

</table>
<br/><br/>
<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Dettagli addizionali</b></td></tr>
<tr><td class="blue">Note</td>
<td class="layout"><%= (schedaDetails.getNote()!=null) ?  schedaDetails.getNote()  : "" %></td></tr>
</table>

<% } else if (schedaDetails.getOperatore().equals("Operatore193")) { %>


<% } else if (schedaDetails.getOperatore().equals("Farmacia")) { %>


<% } else if (schedaDetails.getOperatore().equals("OperatoreNonAltrove")) { %>


<% } else if (schedaDetails.getOperatore().equals("Privato")) { %>


<% } else if (schedaDetails.getOperatore().equals("AziendaAgricola")) { %>

<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Informazione primaria</b></td></tr>
<tr><td class="blue" >Denominazione</td>
<td class="layout"><%=schedaDetails.getRagioneSociale() %></td></tr>
<tr><td class="blue">Numero registrazione</td>
<td class="layout"><%=schedaDetails.getNumeroRegistrazione() %></td></tr>
<tr><td class="blue">Partita IVA</td>
<td class="layout"><%=(schedaDetails.getPartitaIva()!=null) ? schedaDetails.getPartitaIva() : "" %></td></tr>
<tr><td class="blue">Codice Fiscale</td>
<td class="layout"><%=(schedaDetails.getCodiceFiscale()!=null) ? schedaDetails.getCodiceFiscale() : "" %></td></tr>
<tr><td class="blue">Attività</td>
<td class="layout"><%=schedaDetails.getNote() %></td></tr>
<tr><td class="blue">Data inizio attività</td>
<td class="layout"><%=(schedaDetails.getData1()!=null) ? toDateasString(schedaDetails.getData1()) : "" %></td></tr>
</table>
<br/><br/>


<table cellpadding="5" style="border-collapse: collapse" width="100%">
<col width="33%">
<tr><td colspan="4" class="grey"><b>Indirizzi</b></td></tr>
<tr><td class="blue">Sede legale</td>
<td class="layout"><%= (schedaDetails.getSedeLegale()!=null) ?  schedaDetails.getSedeLegale()  : "" %></td></tr>

<tr><td class="blue">Sede operativa</td>
<td class="layout"><%= (schedaDetails.getSedeOperativa()!=null) ?  schedaDetails.getSedeOperativa()  : "" %></td></tr>

</table>

<% } else if (schedaDetails.getOperatore().equals("OperatoreSperimentazioneAnimale")) { %>


<% } else if (schedaDetails.getOperatore().equals("StrutturaRiproduzioneAnimale")) { %>

<%} %>



</body>
</html>