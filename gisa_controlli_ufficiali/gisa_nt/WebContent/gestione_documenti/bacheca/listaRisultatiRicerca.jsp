<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ include file="../../initPage.jsp"%>
    <jsp:useBean id="listaAllegati" class="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleBachecaList" scope="request"/>
<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleBacheca"%>

<jsp:useBean id="downloadURL" class="java.lang.String" scope="request"/>
<jsp:useBean id="storeId" class="java.lang.String" scope="request"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*, org.aspcfs.modules.base.*, org.aspcfs.modules.registrazioniAnimali.base.*" %>
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
	 
	
	  return toRet;
	  
  }%>
  
  	<%-- a href="#" onclick="openNewCartella('<%=storeId%>','<%=folderId%>','<%=parentId%>');"
				id="" target="_self">Crea nuova cartella</a--%>
	<% String param1 = "storeId=" + storeId;   
%>

<table class="trails" cellspacing="0">
<tbody><tr>
<td>
<a href="GestioneBacheca.do">Bacheca</a> &gt;
Risultati ricerca
</td>
</tr>
</tbody></table>
	
 

<br/><br/>
 



  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <col width="10%">
  <col width="20%">
  <col width="40%">
  <col width="30%">
 

		<tr>
			<th><strong>Gestione</strong></th>
			<th><strong>Titolo</strong></th>
			<th><strong>Descrizione</strong></th>
			<th><strong>Stato</strong></th>
		</tr>
	
			
			<%

	if (listaAllegati.size()>0)
		for (int i=0;i<listaAllegati.size(); i++){
			DocumentaleBacheca doc = (DocumentaleBacheca) listaAllegati.get(i);
				
			%>
			<% if (doc.getTipoBacheca().equals("archivio")) {%>
			<tr class="row<%=i%2%>">
						<td>
			<a href="GestioneBacheca.do?command=GestisciArchivio&storeId=<%=doc.getStoreId()%>&operazione=dettaglioArchivio">
 Dettaglio</a>
&nbsp;</td>
			<td><a href="GestioneBacheca.do?command=ListaAllegati&storeId=<%=doc.getStoreId()%>
			<% if (doc.getDataArchiviazione()!=null && !doc.getDataArchiviazione().equals("null")) {%>
			&storeArchiviato=true
			<%}%>
			"><b><%= doc.getFileTitolo() %></a></b>&nbsp; </td>
			<td><%=doc.getFileDescrizione() %>&nbsp; </td>
			<td>
			<% if (doc.getDataArchiviazione()!=null && !doc.getDataArchiviazione().equals("null")) {%>
			<font color="blue">QUESTO ARCHIVIO DOCUMENTI E' STATO ARCHIVIATO IL <%=fixData(doc.getDataArchiviazione()) %></font>
			<%} else  if (doc.getDataInizio()!=null && !doc.getDataInizio().equals("null")) {%>
			<font color="green">QUESTO ARCHIVIO DOCUMENTI E' STATO APPROVATO IL <%=fixData(doc.getDataApprovazione()) %></font>
			<%} else { %>
			<font color="red"> Questo archivio documenti è attualmente sotto revisione e non è stato approvato</font>
			<%} %>
			
			&nbsp; </td>
			</tr>
			<%} else if (doc.getTipoBacheca().equals("documento")) { %>
			<tr class="row<%=i%2%>">
						<td>
			<a href="GestioneBacheca.do?command=DownloadPDF&codDocumento=<%=doc.getIdHeader()%>&tipoDocumento=<%=doc.getEstensione()%>&nomeDocumento=<%=fixStringa(doc.getNomeClient())%>">
 Download</a>
&nbsp;</td>
			<td><b><%=doc.getOggetto() %></b> </td>
			<td><% if (doc.getEstensione().equals("pdf")) {%>
			<img src="gestione_documenti/images/pdf_icon.png" width="20"/>
			<%} else if (doc.getEstensione().equals("csv")) { %>
			<img src="gestione_documenti/images/csv_icon.png" width="20"/>
			<%} else if (doc.getEstensione().equals("png") || doc.getEstensione().equals("gif") || doc.getEstensione().equals("jpg") || doc.getEstensione().equals("ico")) { %>
			<img src="gestione_documenti/images/img_icon.png" width="20"/>
			<%} else if (doc.getEstensione().equals("rar") || doc.getEstensione().equals("zip")) { %>
			<img src="gestione_documenti/images/rar_icon.png" width="20"/>
			<%} else { %>
			<img src="gestione_documenti/images/file_icon.png" width="20"/>
			<%} %>  <%=doc.getEstensione() %>
			&nbsp; </td>
			<td>
				<%="" %> &nbsp; </td>
			</tr>
			<%} %>
			
		
		<%}  else {%>
					<tr>
			<td colspan="8">Nessun risultato trovato.</td> 
		</tr>
		<%}%>
		
		</table>
	
