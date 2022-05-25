<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ include file="../../initPage.jsp"%>
<jsp:useBean id="listaAllegati" class="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoList" scope="request"/>
<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegato"%>
<jsp:useBean id="downloadURL" class="java.lang.String" scope="request"/>
<jsp:useBean id="idStruttura" class="java.lang.String" scope="request"/>
<jsp:useBean id="anno" class="java.lang.String" scope="request"/>

<jsp:useBean id="op" class="java.lang.String" scope="request"/>
<jsp:useBean id="messaggioPost" class="java.lang.String" scope="request"/>
<jsp:useBean id="pag" class="java.lang.String" scope="request"/>
<jsp:useBean id="pagTot" class="java.lang.String" scope="request"/>
<jsp:useBean id="pagine" class="java.lang.String" scope="request"/>

<jsp:useBean id="StrutturaAmbito" class="org.aspcfs.modules.oia.base.OiaNodo" scope="request"/>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>




<script type="text/javascript">
function gestioneBoxCaricaFile(){
	var box = document.getElementById('boxCaricaFile');
	if (box.style.display=='none')
		box.style.display='block';
	else
		box.style.display='none';
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
  
<% String param0 = "idAsl="+StrutturaAmbito.getId_asl()+"&anno="+StrutturaAmbito.getAnno()+"&combo_area="+StrutturaAmbito.getId() ;%>				
	
	



<dhv:container name="<%=op %>" selected="Allegati" object="" param="<%= param0 %>">

 <dhv:permission name="documentale_documents-add">

<%if(listaAllegati.size()!=4){ %>
<a href="javascript:gestioneBoxCaricaFile()">
<img src="gestione_documenti/images/new_file_icon.png" width="30"/>  
Carica file</a>
<%} %>
</dhv:permission>

<br/><br/>
 
<br/>



<div id="boxCaricaFile" style="display:none">
<%@ include file="/gestione_documenti/allegati/uploadFileDpat.jsp" %> <br/><br/>
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

  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <col width="10%">
  <col width="30%">
  <col width="5%">
  <col width="10%">
  <col width="15%">
  

		<tr>
			<th><strong>Oggetto</strong></th>
			<th><strong>Tipo Allegato</strong></th>
			<th><strong>Tipo</strong></th>
			<th><strong>Data caricamento</strong></th>
			<th><strong>Caricato/creato da</strong></th>
			<th><strong>Gestione</strong></th>
		</tr>
	
			
			<%
	
	if (listaAllegati.size()>0)
		for (int i=0;i<listaAllegati.size(); i++){
			DocumentaleAllegato doc = (DocumentaleAllegato) listaAllegati.get(i);
			 
			String permission = "server_documentale-dpat" ;
			if (doc.getTipoAllegato().toLowerCase().contains("mod1"))
					permission += "-mod1-delete";
			if (doc.getTipoAllegato().toLowerCase().contains("mod3"))
				permission += "-mod3-delete";
			if (doc.getTipoAllegato().toLowerCase().contains("mod4"))
				permission += "-mod4-delete";
			if (doc.getTipoAllegato().toLowerCase().contains("mod6"))
				permission += "-mod6-delete";
			
			%>
			
			
			<tr class="row<%=i%2%>">
			<td><%=doc.getOggetto() %></td>
			<td>
			<a href="GestioneAllegatiUpload.do?command=DownloadPDF&codDocumento=<%=doc.getIdHeader()%>&idDocumento=<%=doc.getIdDocumento() %>&tipoDocumento=<%=doc.getTipoAllegato()%>&nomeDocumento=<%=fixStringa(doc.getNomeClient())%>">
			<% if (doc.getEstensione().equalsIgnoreCase("pdf")) {%>
			<img src="gestione_documenti/images/pdf_icon.png" width="20"/>
			<%} else if (doc.getEstensione().equalsIgnoreCase("csv")) { %>
			<img src="gestione_documenti/images/csv_icon.png" width="20"/>
			<%} else if (doc.getEstensione().equalsIgnoreCase("png") || doc.getEstensione().equals("gif") || doc.getEstensione().equals("jpg") || doc.getEstensione().equals("ico")) { %>
			<img src="gestione_documenti/images/img_icon.png" width="20"/>
			<%} else if (doc.getEstensione().equalsIgnoreCase("rar") || doc.getEstensione().equals("zip")) { %>
			<img src="gestione_documenti/images/rar_icon.png" width="20"/>
			<%} else if (doc.getEstensione().contains("xls")) { %>
			<img src="gestione_documenti/images/xls_icon.png" width="20"/>
			<%} else if (doc.getEstensione().contains("doc")) { %>
			<img src="gestione_documenti/images/doc_icon.png" width="20"/>
			<%} else { %>
			<img src="gestione_documenti/images/file_icon.png" width="20"/>
			<%} %> 
			
			</a>
			</td> 
			<td><%=(doc.getEstensione()!=null && !doc.getEstensione().equals("null")) ? doc.getEstensione() : "&nbsp;"%></td>
			<td><%= fixData(doc.getDataCreazione()) %></td> 
			<td> <dhv:username id="<%= doc.getUserId() %>" /></td> 
			<td>
			<dhv:permission name="<%=permission %>">
				<a href="GestioneAllegatiUpload.do?command=GestisciFileDpat&combo_area=<%=idStruttura%>&anno=<%=anno%>&idFile=<%=doc.getIdDocumento() %>&operazione=cancella&op=<%=op%>" onCLick="if(confirm('ATTENZIONE! Stai per cancellare definitivamente questo file. Sei sicuro di continuare?')) { loadModalWindow(); return true;} else {return false;}">
			<img src="gestione_documenti/images/delete_file_icon.png" width="20"/> Cancella</a>
			</dhv:permission>
</td>
			</tr>
		
			
		<%}  else {%>
					<tr>
			<td colspan="8">Non sono presenti file in questa cartella.</td> 
		</tr>
		<%}%>
		
		</table>
	
</dhv:container>




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
	<a href="GestioneAllegatiUpload.do?command=ListaAllegatiDpat&combo_area=<%=idStruttura%>&anno=<%=anno%>&op=<%=op%>&pag=<%=pagSel-1%>"><%= "<<" %></a>
	<%} %>
	</td>
	
	<td><%=pagSel %></td>
	
	<td>
	<%if (pagSel<pagTotali){ %>
	<a href="GestioneAllegatiUpload.do?command=ListaAllegatiDpat&combo_area=<%=idStruttura%>&anno=<%=anno%>&op=<%=op%>&pag=<%=pagSel+1%>"><%= ">>" %></a>
	<%} %>
	</td>
	
	<td>(<%=pagTotali%> <a href="GestioneAllegatiUpload.do?command=ListaAllegatiDpat&combo_area=<%=idStruttura%>&anno=<%=anno%>&op=<%=op%>&pagine=no"><%= "Tutto" %></a>)</td></tr></table>

<script type="text/javascript">
<!-- aggiornamento pagina per modifica indirizzo in request --> 
//    if(window.location.href.substr(-9) !== "&def=true") {
// 	  loadModalWindow();
<%-- 	  window.location.href = 'GestioneAllegatiUpload.do?command=ListaAllegatiDpat&idStruttura=<%=idStruttura%>&anno=<%=anno%>&op=<%=op%>&messaggioPost=<%=messaggioPost%>&pag=<%=pag%>&pagTot=<%=pagTot%>&pagine=<%=pagine%>&def=true'; --%>
	  
//     }
</script>


