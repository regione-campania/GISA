<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoGinsList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="../../initPage.jsp"%>
<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoGins"%>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope = "request"/>
<jsp:useBean id="lista_schede_supplementari" class="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoGinsList" scope="request"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*, org.aspcfs.modules.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>


  <%! public static String fixData(String timestring)
  {
	  String toRet = "";
	  if (timestring == null || timestring.equals("null"))
		  return toRet;
	  String anno = timestring.substring(0,4);
	  String mese = timestring.substring(5,7);
	  String giorno = timestring.substring(8,10);
	  String ora = timestring.substring(11,13);
	  String minuto = timestring.substring(14,16);
	  String secondi = timestring.substring(17,19);
	  toRet =giorno+"/"+mese+"/"+anno+" "+ora+":"+minuto;
	  return toRet;
	  
  }%>
  <%! public static String fixStringa(String nome)
  {
	  String toRet = nome;
	  if (nome == null || nome.equals("null"))
		  return toRet;
	  toRet = nome.replaceAll("'", "");
	  toRet = toRet.replaceAll(" ", "_");
	  toRet = toRet.replaceAll("\\?","");
	
	  return toRet;
	  
  }%>

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

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="OpuStab.do?command=SearchForm">ANAGRAFICA STABILIMENTI</a> > 
			<a href="GestioneAnagraficaAction.do?command=Details&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>"> SCHEDA</a> >
			SCHEDE SUPPLEMENTARI		
		</td>
	</tr>
</table>
<br>
<dhv:container name="<%=nomeContainer %>"  selected="details" object="Operatore" param="<%=param%>" hideContainer="false">
<center>
	<h3>
	<b>LISTA SCHEDE SUPPLEMENTARI</b><br/>
	<i>In questa pagina sono elencate le schede supplementari di questo stabilimento.</i>
	</h3>
	<br>
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
			<th style="text-align:center;" colspan="5"><p>schede supplementari</p></th>
		</tr>
		<tr>
			<th style="text-align:center; width:35%;"><p>Oggetto</p></th>
			<th style="text-align:center">file</th>
			<th style="text-align:center">Tipo</th>
			<th style="text-align:center">Data caricamento</th>
			<th style="text-align:center">Caricato/creato da</th>
		</tr>
	
		<%if (lista_schede_supplementari.size()>0)
			for (int i=0;i<lista_schede_supplementari.size(); i++){
				
				DocumentaleAllegatoGins doc_schede_sup = (DocumentaleAllegatoGins) lista_schede_supplementari.get(i); %>
				
				<tr class="row<%=i%2%>">
					<td align="center">
						<p>
							<strong>SCHEDA SUPPLEMENTARE</strong> <br> 
				  		</p>
				  	</td> 
					<td align="center">
						<a href="GestioneAllegatiGins.do?command=DownloadPDF&codDocumento=<%=doc_schede_sup.getIdHeader()%>&idDocumento=<%=doc_schede_sup.getIdDocumento() %>&tipoDocumento=<%=doc_schede_sup.getTipoAllegato()%>&nomeDocumento=<%=fixStringa(doc_schede_sup.getNomeClient())%>">
						<% if (doc_schede_sup.getEstensione().equalsIgnoreCase("pdf")) {%>
						<img src="gestione_documenti/images/pdf_icon.png" width="20"/>
						<%} else if (doc_schede_sup.getEstensione().equalsIgnoreCase("csv")) { %>
						<img src="gestione_documenti/images/csv_icon.png" width="20"/>
						<%} else if (doc_schede_sup.getEstensione().equalsIgnoreCase("png") || doc_schede_sup.getEstensione().equals("gif") || doc_schede_sup.getEstensione().equals("jpg") || doc_schede_sup.getEstensione().equals("ico")) { %>
						<img src="gestione_documenti/images/img_icon.png" width="20"/>
						<%} else if (doc_schede_sup.getEstensione().equalsIgnoreCase("rar") || doc_schede_sup.getEstensione().equals("zip")) { %>
						<img src="gestione_documenti/images/rar_icon.png" width="20"/>
						<%} else if (doc_schede_sup.getEstensione().contains("xls")) { %>
						<img src="gestione_documenti/images/xls_icon.png" width="20"/>
						<%} else if (doc_schede_sup.getEstensione().contains("doc")) { %>
						<img src="gestione_documenti/images/doc_icon.png" width="20"/>
						<%} else if (doc_schede_sup.getEstensione().contains("p7m")) { %>
						<img src="gestione_documenti/images/p7m_icon.png" width="20"/>
						<%} else { %>
						<img src="gestione_documenti/images/file_icon.png" width="20"/>
						<%} %> 
						<%=fixStringa(doc_schede_sup.getNomeClient())%>
						</a>
					</td> 
					<td align="center"><%=(doc_schede_sup.getEstensione()!=null && !doc_schede_sup.getEstensione().equals("null")) ? doc_schede_sup.getEstensione() : "&nbsp;"%></td>
					<td align="center"><%= fixData(doc_schede_sup.getDataCreazione()) %></td> 
					<td align="center"> <dhv:username id="<%= doc_schede_sup.getUserId() %>" /></td> 
				</tr>
	
		<%}  else {%>
		<tr>
			<td colspan="5" align="center"><p>Non sono presenti schede supplementari per questo stabilimento.</p></td> 
		</tr>
		<%}%>
			
	</table>
</center>
<br>
</dhv:container>
<br><br>
