<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ include file="../../initPage.jsp"%>
    
    <jsp:useBean id="Animale" class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request"/>
    
<jsp:useBean id="listaAllegati" class="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoList" scope="request"/>
<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegato"%>
<jsp:useBean id="downloadURL" class="java.lang.String" scope="request"/>
<jsp:useBean id="folderId" class="java.lang.String" scope="request"/>
<jsp:useBean id="parentId" class="java.lang.String" scope="request"/>
<jsp:useBean id="grandparentId" class="java.lang.String" scope="request"/>
<jsp:useBean id="idAnimale" class="java.lang.String" scope="request"/>
<jsp:useBean id="nomeCartella" class="java.lang.String" scope="request"/>
<jsp:useBean id="messaggioPost" class="java.lang.String" scope="request"/>
<jsp:useBean id="pag" class="java.lang.String" scope="request"/>
<jsp:useBean id="pagTot" class="java.lang.String" scope="request"/>
<jsp:useBean id="pagine" class="java.lang.String" scope="request"/>
<jsp:useBean id="stati" class="java.util.HashMap" scope="request"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*" %>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<script type="text/javascript">
function openNewCartella(idAnimale, folderId, parentId){
	var res;
	var result;
	
		window.open('GestioneAllegatiUpload.do?command=CreaNuovaCartella&folderId='+folderId+'&parentId='+parentId+'&idAnimale=42&new=new','popupSelect',
		'height=410px,width=410px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
	}
</script>

<script type="text/javascript">
function gestioneBoxCaricaFile(){
	var box = document.getElementById('boxCaricaFile');
	if (box.style.display=='none')
		box.style.display='block';
	else
		box.style.display='none';
	}
</script> 
 
<script type="text/javascript">
function gestioneBoxCreaCartella(){
	var box = document.getElementById('boxCreaCartella');
	if (box.style.display=='none')
		box.style.display='block';
	else
		box.style.display='none';
	}
</script> 

<script type="text/javascript">
function deleteCartella(idAnimale, folderId, parentId, idCartella){
	var res;
	var result;
	
		window.open('GestioneAllegatiUpload.do?command=CreaNuovaCartella&folderId='+folderId+'&parentId='+parentId+'&ticketId='+ticketId+'&idAnimale=42&new=new','popupSelect',
		'height=410px,width=410px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
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
  
 
<% String param1 = "idAnimale=42&idSpecie=" + Animale.getIdSpecie();  %>
   
<%if (!folderId.equals("-1")) {%>		
<a href="GestioneAllegatiUpload.do?command=ListaFileCommissione&idAnimale=42&folderId=<%=parentId%>&parentId=<%=grandparentId %>">
<img src="gestione_documenti/images/parent_folder_icon.png" width="30"/>
Cartella superiore</a> &nbsp;&nbsp;&nbsp;&nbsp;
<% } %>  

 
 <% if (nomeCartella!=null && !nomeCartella.equals("")) {%>
 <table border="1">
 <tr><td><%=nomeCartella.toUpperCase() %></td></tr>
 </table>
<br/>
<%} %>





<div id="boxCaricaFile" >
      <%
       int maxFileSize=-1;
	   int mb1size = 1048576;
	    maxFileSize=Integer.parseInt(org.aspcfs.utils.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"));
	   	String maxSizeString = String.format("%.2f", (double) maxFileSize/ (double) mb1size);
       %>
       
       <script>
       function checkFormFile(){
	var fileCaricato = document.getElementById("file");
	var oggetto = document.getElementById("subject").value;
	var errorString = '';
	if (fileCaricato!=null && fileCaricato.value!='' && !fileCaricato.value.endsWith(".pdf"))
	{
		errorString+='Selezionare un file di tipo pdf';
	}
	if (fileCaricato==null || fileCaricato.value==''){// || (!fileCaricato.value.endsWith(".pdf") && !fileCaricato.value.endsWith(".csv"))){
		errorString+='Selezionare un file';
	}
	if (oggetto==''){
		if(errorString!='')
			errorString+=' e il nome';
		else
			errorString+='Il nome è obbligatorio';
		}
	if (fileCaricato!=null && fileCaricato.value!='' && !GetFileSize(document.getElementById("file")))
		errorString+='\nErrore! Selezionare un file con dimensione superiore a 0 ed inferiore a <%=maxSizeString%> MB';
	if (errorString!= '')
		alert(errorString)
	else
	{
	//form.filename.value = fileCaricato.value;	
	document.getElementById("uploadButton").hidden="hidden";
	document.getElementById("file").hidden="hidden";
	document.getElementById("image_loading").hidden="";
	document.getElementById("text_loading").hidden="";
	loadModalWindow();
	document.form2.submit();
	}
}</script>

<script>function GetFileSize(fileid) {
	var input = document.getElementById('file');
        file = input.files[0];
        if (file.size == 0 || file.size> <%=Integer.parseInt(org.aspcfs.utils.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"))%>)
      	 	return false;
        return true;
		}
</script>


<form id="form2" action="GestioneAllegatiUpload.do?command=AllegaFileCommissione" method="post" name="form2" enctype="multipart/form-data">
 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <img border="0" src="images/file.gif" align="absmiddle"><b><dhv:label name="accounts.accounts_documents_upload.UploadNewDocument">Upload a New Document</dhv:label></b>
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">
        Nome
      </td>
      <td>
        <input type="text" name="subject" id="subject" size="59" maxlength="255" value="<%= toHtmlValue((String)request.getAttribute("subject")) %>"><font color="red">*</font>
        <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
      <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="contacts.companydirectory_confirm_importupload.File">File</dhv:label>
       
   
       (Max. <%=maxSizeString %> MB)
       
      </td>
      <td>
        <input type="file" id="file" name="file" size="45">  <input type="button" id="uploadButton" name="uploadButton" value="UPLOAD" onclick="checkFormFile()" />
      
          <img id="image_loading" hidden="hidden" src="gestione_documenti/images/loading.gif" height="15"/>
          <input type="text" disabled id="text_loading" name="text_loading" hidden="hidden" value="Caricamento in corso..."  style="border: none"/>
          * Attendere il messaggio di completamento caricamento
      </td>
    </tr>
     <input type="hidden" name="idAnimale" id ="idAnimale" value="<%= (String)request.getAttribute("idAnimale") %>" />
    <input type="hidden" name="folderId" id="folderId" value="<%= (String)request.getAttribute("folderId") %>" />
<input type="hidden" name="parentId" id="parentId" value="<%= request.getParameter("parentId") %>" /> 
  </table>
   </form>
 
  <br/><br/>
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


<a href="guida_2016/Guida file commissione.pdf">
			<img src="gestione_documenti/images/pdf_icon.png" width="20"/>
			Guida al caricamento file 
			</a>
			
			
	
	
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <col width="10%">
  <col width="30%">
  <col width="5%">
  <col width="10%">
  <col width="15%">
  

		<tr>
			<th><strong>Nome</strong></th>
			<th><strong>Data caricamento</strong></th>
			<th><strong>Caricato/creato da</strong></th>
			<th><strong>Stato</strong></th>
			<th><strong>Gestione</strong></th>
		</tr>
	
			
			<%
	
	if (listaAllegati.size()>0)
		for (int i=0;i<listaAllegati.size(); i++){
			DocumentaleAllegato doc = (DocumentaleAllegato) listaAllegati.get(i);
			String stato = (String)stati.get(doc.getIdDocumento());
			if(stato==null)
				stato="Attivo";
			
			%>
			
			<tr class="row<%=i%2%>">
			<td>
			<a href="GestioneAllegatiUpload.do?command=DownloadPDF&fileCommissione=si&codDocumento=<%=doc.getIdHeader()%>&idDocumento=<%=doc.getIdDocumento() %>&tipoDocumento=<%=doc.getTipoAllegato()%>&nomeDocumento=<%=fixStringa(doc.getNomeClient())%>">
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
			<%} else if (doc.getEstensione().contains("p7m")) { %>
			<img src="gestione_documenti/images/p7m_icon.png" width="20"/>
			<%} else { %>
			<img src="gestione_documenti/images/file_icon.png" width="20"/>
			<%} %> 
			<%= doc.getOggetto() %> 
			</a>
			</td> 
			<td><%= fixData(doc.getDataCreazione()) %></td> 
			<td> <dhv:username id="<%= doc.getUserId() %>" /></td> 
			<td><%=stato%></td>
			
			<td>
				<a href="AnimaleAction.do?command=UpdateStatoDocumento&nuovoStato=<%=(stato.equals("Attivo"))?("Disabilitato"):("Attivo") %>&idFile=<%=doc.getIdDocumento()%>" onCLick="if(confirm('ATTENZIONE! Stai per <%=(stato.equals("Attivo"))?("disabilitare"):("attivare") %> questo file. Sicuro di continuare?')) { loadModalWindow(); return true;} else {return false;}"> <img src="gestione_documenti/images/file_icon.png" width="20"/><%=(stato.equals("Attivo"))?("Disabilita"):("Attiva") %></a>
				<a href="GestioneAllegatiUpload.do?command=GestisciFileCommissione&idAnimale=42&folderId=<%=folderId%>&parentId=<%=parentId%>&idFile=<%=doc.getIdDocumento() %>&operazione=cancella" onCLick="if(confirm('ATTENZIONE! Stai per cancellare definitivamente questo file. Sei sicuro di continuare?')) { loadModalWindow(); return true;} else {return false;}">
<img src="gestione_documenti/images/delete_file_icon.png" width="20"/> Cancella</a>
</td>
			</tr>
			
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
	<a href="GestioneAllegatiUpload.do?command=ListaFileCommissione&idAnimale=42&folderId=<%=folderId%>&parentId=<%=parentId%>&pag=<%=pagSel-1%>"><%= "<<" %></a>
	<%} %>
	</td>
	
	<td><%=pagSel %></td>
	
	<td>
	<%if (pagSel<pagTotali){ %>
	<a href="GestioneAllegatiUpload.do?command=ListaFileCommissione&idAnimale=42&folderId=<%=folderId%>&parentId=<%=parentId%>&pag=<%=pagSel+1%>"><%= ">>" %></a>
	<%} %>
	</td>
	
	<td>(<%=pagTotali%> <a href="GestioneAllegatiUpload.do?command=ListaFileCommissione&idAnimale=42&folderId=<%=folderId%>&parentId=<%=parentId%>&pagine=no"><%= "Tutto" %></a>)</td></tr></table>

<script type="text/javascript">
<!-- aggiornamento pagina per modifica indirizzo in request --> 
   if(window.location.href.substr(-9) !== "&def=true") {
	  loadModalWindow();
	  window.location.href = 'GestioneAllegatiUpload.do?command=ListaFileCommissione&idAnimale=42&folderId=<%=folderId%>&parentId=<%=parentId%>&messaggioPost=<%=messaggioPost%>&pag=<%=pag%>&pagTot=<%=pagTot%>&pagine=<%=pagine%>&def=true';
	  
    }
   
   
   $('input').on('keypress', function (event) {
	    var regex = new RegExp("^['-_+a-zA-Z0-9 /]+$");
	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
	    if (!regex.test(key)) {
	       event.preventDefault();
	       return false;
	    }
	});
</script>


