<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoRiunione"%>
<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoRiunioneList"%>
<%@page import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegato"%>
<%@page import="java.util.Vector"%>
<%@page import="org.aspcfs.modules.meeting.base.Riunione"%>

<jsp:useBean id="listaRevisioni" class="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoRiunioneList" scope="request"/>


		
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
		
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		<tr>
    <th colspan="7">
      <strong>Verbale Riunione</strong>
    </th>
  </tr>
  
  <col width="10%">
  <col width="30%">
  <col width="5%">
  <col width="10%">
  <col width="15%">
  

		<tr>
			<th><strong>Codice/ID</strong></th>
			<th><strong>Revisione</strong></th>
			<th><strong>Oggetto</strong></th>
			<th><strong>Verbale Principale</strong></th>
			<th><strong>Tipo</strong></th>
			<th><strong>Data caricamento</strong></th>
			<th><strong>Caricato/creato da</strong></th>
			
			
		</tr>
	
			
			<%
	
	if (listaRevisioni.size()>0)
		for (int i=0;i<listaRevisioni.size(); i++){
			DocumentaleAllegatoRiunione doc = (DocumentaleAllegatoRiunione) listaRevisioni.get(i);
				
			%>
			
			
			<tr>
			<td><%=doc.getIdHeader() %> </td> 
			<td><a href="#" onclick=""><%="Rev."+doc.getNumeroRevisione() %></a> </td>
			<td>
			<a href="GestioneAllegatiUpload.do?command=DownloadPDF&codDocumento=<%=doc.getIdHeader()%>&idDocumento=<%=doc.getIdDocumento() %>&tipoDocumento=<%=doc.getEstensione()%>&nomeDocumento=<%=fixStringa(doc.getNomeClient())%>">
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
			<td>
			<input type = "checkbox" disabled="disabled" <%=(doc.isPrincipale())? "checked" :"" %>/>
			</td>
			<td><%=(doc.getEstensione()!=null && !doc.getEstensione().equals("null")) ? doc.getEstensione() : "&nbsp;"%></td>
			<td><%=fixData( doc.getDataCreazione()) %></td> 
			<td> <dhv:username id="<%= doc.getUserId() %>" /> </td> 
			
			</tr>
		
			
		<%
				
		} else {%>
		<tr>
			<td colspan="8">Non sono presenti altre revisioni del Verbale.</td> 
		</tr>
		<%}%>
		
		</table>
		<br>
		<input type="button" value="ESCI" onclick="javascript:$('#listaRevisioniVerbale').dialog('close');" />
		
		
		 
       
       
      
	