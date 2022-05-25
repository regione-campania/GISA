<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ include file="../../../initPage.jsp"%>
<jsp:useBean id="listaAllegati" class="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleBachecaList" scope="request"/>
<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleBacheca"%>
<jsp:useBean id="downloadURL" class="java.lang.String" scope="request"/>
<jsp:useBean id="folderId" class="java.lang.String" scope="request"/>
<jsp:useBean id="parentId" class="java.lang.String" scope="request"/>
<jsp:useBean id="grandparentId" class="java.lang.String" scope="request"/>
<jsp:useBean id="storeId" class="java.lang.String" scope="request"/>
<jsp:useBean id="nomeCartella" class="java.lang.String" scope="request"/>
<jsp:useBean id="nomeArchivio" class="java.lang.String" scope="request"/>
<jsp:useBean id="pag" class="java.lang.String" scope="request"/>
<jsp:useBean id="pagTot" class="java.lang.String" scope="request"/>
<jsp:useBean id="pagine" class="java.lang.String" scope="request"/>
<jsp:useBean id="messaggioPost" class="java.lang.String" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
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
	  toRet = toRet.replaceAll("\\?","");
	
	  return toRet;
	  
  }%>
  
  	<%-- a href="#" onclick="openNewCartella('<%=storeId%>','<%=folderId%>','<%=parentId%>');"
				id="" target="_self">Crea nuova cartella</a--%>
	<% String param1 = "storeId=" + storeId;   
%>

		<table class="trails" cellspacing="0">
<tbody><tr>
<td>
<a href="GestioneBacheca.do?command=ListaApiari">Bacheca</a> &gt;
<a href="GestioneBacheca.do?command=ListaApiari?storeId=<%=storeId%>&storeArchiviato=true">Scheda archivio documenti</a>
</td>
</tr>
</tbody></table>

<%if (!folderId.equals("-1")){ %>
<a href="GestioneBacheca.do?command=ListaApiari&storeId=<%=storeId%>&folderId=<%=parentId%>&parentId=<%=grandparentId %>&storeArchiviato=true">
<img src="gestione_documenti/images/parent_folder_icon.png" width="30"/>
Cartella superiore</a> &nbsp;&nbsp;&nbsp;&nbsp;
<%} %>  

 
<br/><br/>
 
 <table border="1" cellspacing="0" align="center">
 <tr><td>
 <img src="gestione_documenti/images/archivio_icon.png" width="20"/>  
  <b><%=nomeArchivio.toUpperCase()%></b> (<a href="GestioneBacheca.do?command=GestisciArchivioApiari&operazione=dettaglioArchivio&storeId=<%=storeId%>">Scheda archivio</a>)<br/>
   <%=nomeCartella.toUpperCase() %></td></tr>
 </table>
<br/>


  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <col width="10%">
  <col width="30%">
  <col width="5%">
  <col width="10%">
  <col width="15%">
  

		<tr>
			<th><strong>Codice/ID</strong></th>
			<th><strong>Oggetto</strong></th>
			<th><strong>Tipo</strong></th>
			<th><strong>Data caricamento</strong></th>
			<th><strong>Caricato/creato da</strong></th>
			</tr>
	
			
			<%

	if (listaAllegati.size()>0)
		for (int i=0;i<listaAllegati.size(); i++){
			DocumentaleBacheca doc = (DocumentaleBacheca) listaAllegati.get(i);
				
			%>
			
			<%if (doc.isFolder()) {  //SE E UNA CARTELLA%> 
					<tr class="row<%=i%2%>">
			<td>&nbsp;</td> 
			<td>
			<img src="gestione_documenti/images/folder_icon.png" width="20"/> 
			<a href="GestioneBacheca.do?command=ListaApiari&storeId=<%=storeId%>&folderId=<%=doc.getIdCartella()%>&parentId=<%=folderId%>&storeArchiviato=true"><%= doc.getNomeDocumento() %></a> </td> 
			<td>CARTELLA</td>
			<td><%= fixData(doc.getDataCreazione()) %></td> 
			<td> <dhv:username id="<%= doc.getUtenteInserimento() %>" /></td> 
				</tr>
			
			<%} else {%>
			<tr class="row<%=i%2%>">
			<td><%= doc.getIdHeader() %> </td> 
			<td>
			<a href="GestioneBacheca.do?command=DownloadPDF&codDocumento=<%=doc.getIdHeader()%>&idDocumento=<%=doc.getIdDocumento() %>&tipoDocumento=<%=doc.getEstensione()%>&nomeDocumento=<%=fixStringa(doc.getNomeClient())%>">
			<% if (doc.getEstensione().equals("pdf")) {%>
			<img src="gestione_documenti/images/pdf_icon.png" width="20"/>
			<%} else if (doc.getEstensione().equals("csv")) { %>
			<img src="gestione_documenti/images/csv_icon.png" width="20"/>
			<%} else if (doc.getEstensione().equals("png") || doc.getEstensione().equals("gif") || doc.getEstensione().equals("jpg") || doc.getEstensione().equals("ico")) { %>
			<img src="gestione_documenti/images/img_icon.png" width="20"/>
			<%} else if (doc.getEstensione().equals("rar") || doc.getEstensione().equals("zip")) { %>
			<img src="gestione_documenti/images/rar_icon.png" width="20"/>
			<%} else { %>
			<img src="gestione_documenti/images/file_icon.png" width="20"/>
			<%} %> 
			<%= doc.getOggetto() %> 
			</a>
			</td> 
			<td><%=(doc.getEstensione()!=null && !doc.getEstensione().equals("null")) ? doc.getEstensione() : "&nbsp;"%></td>
			<td><%= fixData(doc.getDataCreazione()) %></td> 
			<td> <dhv:username id="<%= doc.getUtenteInserimento() %>" /></td> 
			
			</tr>
		<%} %>
			
		<%}  else {%>
					<tr>
			<td colspan="8">Non sono presenti file in questa cartella.</td> 
		</tr>
		<%}%>
		
		</table>
	

<table name="pagine" align="right">	
	<% int pagSel = 1;
		int pagTotali = 1;
	if (pag!=null && !pag.equals("null") && !pag.equals(""))
		pagSel = Integer.parseInt(pag);
	if (pagTot!=null && !pagTot.equals("null") && !pagTot.equals(""))
		pagTotali = Integer.parseInt(pagTot);
		%>
	<tr>
<td>Pagine </td>
	<td>
	<%if (pagSel>1){ %>
	<a href="GestioneBacheca.do?command=ListaApiari&storeId=<%=storeId%>&pag=<%=pagSel-1%>"><%= "<<" %></a>
	<%} %>
	</td>
	
	<td><%=pagSel %></td>
	
	<td>
	<%if (pagSel<pagTotali){ %>
	<a href="GestioneBacheca.do?command=ListaApiari&storeId=<%=storeId%>&pag=<%=pagSel+1%>"><%= ">>" %></a>
	<%} %>
	</td>
	
	<td>(<%=pagTotali%> <a href="GestioneBacheca.do?command=ListaApiari&storeId=<%=storeId%>&pagine=no"><%= "Tutto" %></a>)</td></tr></table>




<script type="text/javascript">
<!-- aggiornamento pagina per modifica indirizzo in request --> 
   if(window.location.href.substr(-9) !== "&def=true") {
	  loadModalWindow();
      window.location.href = 'GestioneBacheca.do?command=ListaApiari&storeId=<%=storeId%>&folderId=<%=folderId%>&parentId=<%=parentId%>&messaggioPost=<%=messaggioPost%>&storeArchiviato=true&&pag=<%=pag%>&pagTot=<%=pagTot%>&pagine=<%=pagine%>&def=true';
    }
</script>
