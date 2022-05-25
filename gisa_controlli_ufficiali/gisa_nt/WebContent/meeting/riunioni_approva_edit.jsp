<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoRiunione"%>
<%@page import="java.util.Vector"%>
<%@page import="org.aspcfs.modules.meeting.base.Riunione"%>
<jsp:useBean id="Riunione" class="org.aspcfs.modules.meeting.base.Riunione" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="listaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ApprovazioneReferente" class="org.aspcfs.modules.meeting.base.ReferenteApprovazione" scope="request"/>


<jsp:useBean id="listaAllegati" class="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoRiunioneList" scope="request"/>




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


<%@ include file="../initPage.jsp" %>
      <%
       int maxFileSize=-1;
	   int mb1size = 1048576;
	    maxFileSize=Integer.parseInt(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"));
	   	String maxSizeString = String.format("%.2f", (double) maxFileSize/ (double) mb1size);
       %>
       
       <script>
       
       function rimuoviFile(){
    	   document.getElementById("fileRevisione").value="";
       }
       
       
function GetFileSize(fileid) {
	var input = document.getElementById('file2');
        file = input.files[0];
        if (file.size> <%=Integer.parseInt(org.aspcfs.modules.util.imports.ApplicationProperties.getProperty("MAX_SIZE_ALLEGATI"))%>)
      	 	return false;
        return true;
		}
</script>




<table class="trails" cellspacing="0">
<tr>
<td>
<a href="#">Gestione Riunioni</a> > 
Nuova Riunione 
</td>
</tr>
</table>
<br>


<form method="post" name="approvaForm" action = "GestioneRiunioni.do?command=CambiaStatoReferente&auto-populate=true" enctype="multipart/form-data" onsubmit="return checkFormApprovazione(this)">
<input type = "hidden" name = "id" value="<%=Riunione.getId()%>">
<input type = "hidden" name = "userIdReferente" value="<%=ApprovazioneReferente.getReferente().getUserId()%>">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="">APPROVAZIONE VERBALE</dhv:label></strong>
    </th>
  </tr>
 
    <tr>
      <td nowrap class="formLabel">
        COMMENTO
      </td>
      <td>
       <textarea rows="6" cols="40" name = "note"></textarea>
      </td>
    </tr>
    
     <tr>
      <td nowrap class="formLabel">
        STATO
      </td>
      <td>
      <%
      listaStati.setJsEvent("onchange='allegaRevisione()'");
      %>
       <%=listaStati.getHtmlSelect("stato", -1) %>
      </td>
    </tr>
    
    </table>
    
    <br>
    		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" style="display: none">
		<tr>
    <th colspan="4">
      <strong>Verbale Riunione</strong>
    </th>
  </tr>
  
  

		<tr>
			<th><strong>Codice/ID</strong></th>
			<th><strong>Revisione</strong></th>
			<th><strong>Oggetto</strong></th>
			<th><strong>Scelta</strong></th>
			
			
			
		</tr>
    <%
    DocumentaleAllegatoRiunione documentaleAllegatoRiunione = new DocumentaleAllegatoRiunione();
    if (listaAllegati.size()>0)
		for (int i=0;i<listaAllegati.size(); i++){
			DocumentaleAllegatoRiunione doc = (DocumentaleAllegatoRiunione) listaAllegati.get(i);
				if (doc.isPrincipale())
				{
					documentaleAllegatoRiunione = doc;
			%>
			
			
			<tr  <%=(doc.isRevisione()) ? "style=\"background-color: yellow;\"" :"" %>  >
			<td><%=doc.getIdHeader() %> </td> 
			<td><%=doc.getNumeroRevisione() %></td>
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
			<%= doc.getOggetto() %> 
			</a>
			</td> 
			
			<td><input type = "radio" name="sceltaVerbaleGisa" value = "<%="REV."+doc.getNumeroRevisione() %>" checked="checked">
			<input type = "radio" name="sceltaVerbaleDoc" value = "<%=doc.getIdHeader() %>" checked="checked">
			</td>
			
			</tr>
		
			
		<%}
				
		} else {%>
					<tr>
			<td colspan="8">Non sono presenti file in questa cartella.</td> 
		</tr>
		<%}%>
		
		</table>
		<br>
    
    
    
    <table id="tabAllegati" cellpadding="4" cellspacing="0" border="0" width="100%" class="details" style="display:none">
    <tr>
      <th colspan="2">
        <img border="0" src="images/file.gif" align="absmiddle"><b>Allega Revisione</b>
      </th>
    </tr>
    <tr class="containerBody" style="display: none">
      <td class="formLabel">
        Oggetto
      </td>
      <td>
        <input type="text" name="subjectRevisione"   size="59" maxlength="255" value="<%= documentaleAllegatoRiunione.getOggetto()%>"><font color="red">*</font>
        <%= showAttribute(request, "subjectError") %>
      </td>
    </tr>
      <tr class="containerBody">
      <td class="formLabel">
       File
       (Max. <%=maxSizeString %> MB)
       
      </td>
      <td>
        <input type="file" id="fileRevisione" name="fileRevisione" size="45"  >  <a href="#" onclick="rimuoviFile(); return false;"><img src="images/delete.gif"></a>
      
      </td>
    </tr>
    
  </table>
    
    
      <input type = "submit" value = "Salva" >
    <input type = "button" value = "Annulla" onclick="$('#approvazione').dialog('close'); $('#nuovaRevisioneVerbale').dialog('close');">
    </form>
   
    