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
<jsp:useBean id="storeId" class="java.lang.String" scope="request"/>
<jsp:useBean id="pag" class="java.lang.String" scope="request"/>
<jsp:useBean id="pagTot" class="java.lang.String" scope="request"/>
<jsp:useBean id="pagine" class="java.lang.String" scope="request"/>
<jsp:useBean id="nomeCartella" class="java.lang.String" scope="request"/>
<jsp:useBean id="messaggioPost" class="java.lang.String" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.opu.base.*, org.aspcfs.modules.base.*, org.aspcfs.modules.registrazioniAnimali.base.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<script type="text/javascript">
function gestioneBoxCreaArchivio(){
	var box = document.getElementById('boxCreaArchivio');
	if (box.style.display=='none')
		box.style.display='block';
	else
		box.style.display='none';
	}
</script> 

<script type="text/javascript">
function gestioneArchiviati(soloArchiviati){
	 loadModalWindow();
	if (soloArchiviati=='1')
	  window.location.href = 'GestioneBacheca.do?command=ListaApiari&storeId=<%=storeId%>&folderId=<%=folderId%>&parentId=<%=parentId%>&archiviati=true&def=true';
	else
	 window.location.href = 'GestioneBacheca.do?command=ListaApiari&storeId=<%=storeId%>&folderId=<%=folderId%>&parentId=<%=parentId%>&def=true';
	
	}
</script> 


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
  
  	<%-- a href="#" onclick="openNewCartella('<%=storeId%>','<%=folderId%>','<%=parentId%>');"
				id="" target="_self">Crea nuova cartella</a--%>
	<% String param1 = "storeId=" + storeId;   
%>

<table class="trails" cellspacing="0">
<tbody><tr>
<td>
<a href="GestioneBacheca.do?command=ListaApiari">Bacheca</a> &gt;
Lista
</td>
</tr>
</tbody></table>
	
 <dhv:permission name="documentale_bacheca_apiari-add">
<a href="javascript:gestioneBoxCreaArchivio()">
<img src="gestione_documenti/images/archivio_icon.png" width="30"/>  
Crea nuovo archivio di documenti</a>
</dhv:permission>

<br/><br/>
 
<div id="boxCreaArchivio" style="display:none">
<%@ include file="/gestione_documenti/bacheca/apiari/creaArchivioApiari.jsp" %> <br/><br/>
</div>


<!-- BOX MESSAGGIO -->
<%if (messaggioPost!=null && !messaggioPost.equals("null")) {
	String color="green";
	if (messaggioPost.startsWith("Errore"))
		color="red";
%>

<p style="text-align: center;"><span style="font-size: large; font-family: trebuchet ms,geneva; font-weight: bold; color: <%=color %>; background-color:#ff8">
<%=messaggioPost %>
</span></p>
<%} %>

<select name="archiviati">
<option value="" onClick="gestioneArchiviati(0)">Apri documenti in bacheca</option>
<option value="archiviati" onClick="gestioneArchiviati(1)" 
	<%if (request.getParameter("archiviati")!=null) {%>
	selected="selected"
	<%} %>
>Apri documenti archiviati</option>
</select>





  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <col width="10%">
  <col width="20%">
  <col width="40%">
  <col width="30%">
 

		<tr>
			<th><strong>Gestione archivio</strong></th>
			<th><strong>Titolo</strong></th>
			<th><strong>Descrizione</strong></th>
			<th><strong>Stato</strong></th>
		</tr>
	
			
			<%

	if (listaAllegati.size()>0)
		for (int i=0;i<listaAllegati.size(); i++){
			DocumentaleBacheca doc = (DocumentaleBacheca) listaAllegati.get(i);
				
			%>
			
			<tr class="row<%=i%2%>">
			<td>
			<%-- <a href="GestioneBacheca.do?command=GestisciArchivio&storeId=<%=split[8]%>&operazione=dettaglioArchivio">
 Dettaglio</a>
&nbsp;
<% if (split[7]==null || split[7].equals("null")) {%>
 <dhv:permission name="documentale_bacheca-edit">
			<a href="GestioneBacheca.do?command=GestisciArchivio&storeId=<%=split[8]%>&descrizioneArchivio=<%=split[1] %>&nomeArchivio=<%=split[0] %>&dataInizio=<%=split[2] %>&statoArchivio=<%=split[3] %>&operazione=modificaArchivio">
Modifica</a>
</dhv:permission>
&nbsp; --%>
<% if (doc.getDataArchiviazione()==null || doc.getDataArchiviazione().equals("null")) { %>
 <dhv:permission name="documentale_bacheca_apiari-delete">
 <a href="GestioneBacheca.do?command=GestisciArchivioApiari&storeId=<%=doc.getStoreId()%>&operazione=cancellaArchivio" onClick="if (confirm('ATTENZIONE! Stai per cancellare l\'archivio e tutto il suo contenuto. Sei sicuro di continuare?')) { loadModalWindow(); return true;}">
<img src="gestione_documenti/images/cestino_icon.png" width="20"/>
 Cancella</a>
 </dhv:permission>
<% } %>			
	
			
			&nbsp; </td>
			<td><a href="GestioneBacheca.do?command=ListaApiari&storeId=<%=doc.getStoreId()%>
			<% if (doc.getDataArchiviazione()!=null && !doc.getDataArchiviazione().equals("null")) {%>
			&storeArchiviato=true
			<%}%>
			"><b><%= doc.getFileTitolo() %></a></b>&nbsp;  <b>(<%=doc.getContaFile() %>)</b> </td>
			<td><%=doc.getFileDescrizione() %>&nbsp;</td>
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
	<a href="GestioneBacheca.do?command=ListaApiari&storeId=-1&pag=<%=pagSel-1%>"><%= "<<" %></a>
	<%} %>
	</td>
	
	<td><%=pagSel %></td>
	
	<td>
	<%if (pagSel<pagTotali){ %>
	<a href="GestioneBacheca.do?command=ListaApiari&storeId=-1&pag=<%=pagSel+1%>"><%= ">>" %></a>
	<%} %>
	</td>
	
	<td>(<%=pagTotali%> <a href="GestioneBacheca.do?command=ListaApiari&storeId=-1&pagine=no"><%= "Tutto" %></a>)</td></tr></table>

<script type="text/javascript">
<!-- aggiornamento pagina per modifica indirizzo in request --> 
   if(window.location.href.substr(-9) !== "&def=true") {
	  loadModalWindow();
	  window.location.href = 'GestioneBacheca.do?command=ListaApiari&storeId=<%=storeId%>&folderId=<%=folderId%>&parentId=<%=parentId%>&pag=<%=pag%>&pagTot=<%=pagTot%>&pagine=<%=pagine%>&messaggioPost=<%=messaggioPost%>&def=true';
	  
    }
</script>
