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
<jsp:useBean id="folderId" class="java.lang.String" scope="request"/>
<jsp:useBean id="parentId" class="java.lang.String" scope="request"/>
<jsp:useBean id="grandparentId" class="java.lang.String" scope="request"/>
<jsp:useBean id="storeId" class="java.lang.String" scope="request"/>
<jsp:useBean id="pag" class="java.lang.String" scope="request"/>
<jsp:useBean id="pagTot" class="java.lang.String" scope="request"/>
<jsp:useBean id="nomeCartella" class="java.lang.String" scope="request"/>
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
  
    <%! public static String nomeAsl(String nomeDoc)
  {
    	String asl =nomeDoc.toLowerCase();
    	asl = asl.replaceAll(" ", "");
		return asl;
  }%>
  
    <% 	int aslUtente = User.getSiteId();
	  	
		String aslUtenteString="";
		if (aslUtente==201)
			aslUtenteString = "avellino";
		else if (aslUtente==202)
			aslUtenteString = "benevento";
		else if (aslUtente==203)
			aslUtenteString = "caserta";
		else if (aslUtente==204)
			aslUtenteString = "napoli1centro";
		else if (aslUtente==205)
			aslUtenteString = "napoli2nord";
		else if (aslUtente==206)
			aslUtenteString = "napoli3sud";
		else if (aslUtente==207)
			aslUtenteString = "salerno";
	  %>
  
  	<%-- a href="#" onclick="openNewCartella('<%=storeId%>','<%=folderId%>','<%=parentId%>');"
				id="" target="_self">Crea nuova cartella</a--%>
	<% String param1 = "storeId=" + storeId;   
%>

<table class="trails" cellspacing="0">
<tbody><tr>
<td>
<a href="GestioneBacheca.do?command=ListaReperibilitaAsl">Lista reperibità ASL</a> &gt;
Scheda archivio documenti
</td>
</tr>
</tbody></table>
	
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <col width="20%">
  <col width="50%">
 
 
 

		<tr>
			<th><strong>Titolo</strong></th>
			<th><strong>Descrizione</strong></th>
			<th><strong>Stato</strong></th>
		</tr>
	
			
			<%

	if (listaAllegati.size()>0)
		for (int i=0;i<listaAllegati.size(); i++){
			DocumentaleBacheca doc = (DocumentaleBacheca) listaAllegati.get(i);
				
			%>
			
		<% if (aslUtente==-1 || aslUtenteString.equals(nomeAsl(doc.getFileTitolo()))){ %>
			<tr class="row<%=i%2%>">
			<td><a href="GestioneBacheca.do?command=ListaReperibilitaAsl&storeId=<%=doc.getStoreId()%>
			<% if (doc.getDataArchiviazione()!=null && !doc.getDataArchiviazione().equals("null")) {%>
			&storeArchiviato=true
			<%}%>
			"><b><%= doc.getFileTitolo() %></a></b>&nbsp; </td>
			<td><%=doc.getFileDescrizione() %>&nbsp; </td>
			<td>
			<% if (doc.getDataArchiviazione()!=null && !doc.getDataArchiviazione().equals("null")) {%>
			<font color="blue">QUESTO ARCHIVIO DOCUMENTI E' STATO ARCHIVIATO IL <%=fixData(doc.getDataArchiviazione()) %></font>
			<%} else  if (doc.getDataApprovazione()!=null && !doc.getDataApprovazione().equals("null")) {%>
			<font color="green">QUESTO ARCHIVIO DOCUMENTI E' STATO APPROVATO IL <%=fixData(doc.getDataApprovazione()) %></font>
			<%} else { %>
			<font color="red"> Questo archivio documenti è attualmente sotto revisione e non è stato approvato</font>
			<%} %>
			
			
			&nbsp; </td>
			
			
			</tr>
			<%} %>
		
		<%}  else {%>
					<tr>
			<td colspan="8">Non sono presenti archivi.</td> 
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
	<a href="GestioneBacheca.do?command=ListaReperibilitaAsl&storeId=-1&pag=<%=pagSel-1%>"><%= "<<" %></a>
	<%} %>
	</td>
	
	<td><%=pagSel %></td>
	
	<td>
	<%if (pagSel<pagTotali){ %>
	<a href="GestioneBacheca.do?command=ListaReperibilitaAsl&storeId=-1&pag=<%=pagSel+1%>"><%= ">>" %></a>
	<%} %>
	</td>
	
	<td>(<%=pagTotali%> <a href="GestioneBacheca.do?command=ListaReperibilitaAsl&storeId=-1&pagine=no"><%= "Tutto" %></a>)</td></tr></table>