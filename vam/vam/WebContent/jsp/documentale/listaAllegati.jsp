<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<jsp:useBean id="listaAllegati" class="it.us.web.bean.documentale.DocumentaleAllegatoList" scope="request"/>
<%@page import="it.us.web.bean.documentale.DocumentaleAllegato"%>


<jsp:useBean id="idAutopsia" class="java.lang.String" scope="request"/>
<jsp:useBean id="idAccettazione" class="java.lang.String" scope="request"/>
<jsp:useBean id="idIstopatologico" class="java.lang.String" scope="request"/>

<jsp:useBean id="readonly" class="java.lang.String" scope="request"/>

<jsp:useBean id="messaggioPost" class="java.lang.String" scope="request"/>


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
  
<% if (readonly!=null && readonly.equals("false")){ %>
<%@ include file="/jsp/documentale/uploadFile.jsp" %> <br/><br/>
<%} %>


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
			<th><strong>Codice/ID</strong></th>
			<th><strong>Oggetto</strong></th>
			<th><strong>Tipo</strong></th>
			<th><strong>Data caricamento</strong></th>
			<th><strong>Gestione</strong></th>
		</tr>
	
			
			<%
	
	if (listaAllegati.size()>0)
		for (int i=0;i<listaAllegati.size(); i++){
			DocumentaleAllegato doc = (DocumentaleAllegato) listaAllegati.get(i);
				
			%>
			
			<tr class="row<%=i%2%>">
			<td><%=doc.getIdHeader() %> </td> 
			<td>
			<a href="documentale.DownloadPdf.us?codDocumento=<%=doc.getIdHeader()%>&idDocumento=<%=doc.getIdDocumento() %>&tipoDocumento=<%=doc.getEstensione()%>&nomeDocumento=<%=fixStringa(doc.getNomeClient())%>">
			<% if (doc.getEstensione().equalsIgnoreCase("pdf")) {%>
			<img src="jsp/documentale/images/pdf_icon.png" width="20"/>
			<%} else if (doc.getEstensione().equalsIgnoreCase("csv")) { %>
			<img src="jsp/documentale/images/csv_icon.png" width="20"/>
			<%} else if (doc.getEstensione().equalsIgnoreCase("png") || doc.getEstensione().equals("gif") || doc.getEstensione().equals("jpg") || doc.getEstensione().equals("ico")) { %>
			<img src="jsp/documentale/images/img_icon.png" width="20"/>
			<%} else if (doc.getEstensione().equalsIgnoreCase("rar") || doc.getEstensione().equals("zip")) { %>
			<img src="jsp/documentale/images/rar_icon.png" width="20"/>
			<%} else if (doc.getEstensione().contains("xls")) { %>
			<img src="jsp/documentale/images/xls_icon.png" width="20"/>
			<%} else if (doc.getEstensione().contains("doc")) { %>
			<img src="jsp/documentale/images/doc_icon.png" width="20"/>
			<%} else if (doc.getEstensione().contains("p7m")) { %>
			<img src="jsp/documentale/images/p7m_icon.png" width="20"/>
			<%} else { %>
			<img src="jsp/documentale/images/file_icon.png" width="20"/>
			<%} %> 
			<%= doc.getOggetto() %> 
			</a>
			</td> 
			<td><%=(doc.getEstensione()!=null && !doc.getEstensione().equals("null")) ? doc.getEstensione() : "&nbsp;"%></td>
			<td><%= fixData(doc.getDataCreazione()) %></td> 
			<td>
			
			<% if (readonly!=null && readonly.equals("false")){ %>
			<a href="documentale.GestisciFile.us?&idAccettazione=<%=idAccettazione%>&idAutopsia=<%=idAutopsia%>&idIstopatologico=<%=idIstopatologico%>&codDocumento=<%=doc.getIdHeader() %>&operazione=CancellaFile&readonly=<%=readonly%>" onCLick="if(confirm('ATTENZIONE! Stai per cancellare definitivamente questo file. Sei sicuro di continuare?')) { attendere(); return true;} else {return false;}">
			<img src="jsp/documentale/images/delete_file_icon.png" width="20"/> Cancella</a>
			<%} %>
			
			</td>
			</tr>
			<%}  else {%>
					<tr>
			<td colspan="8">Non sono presenti file in questa cartella.</td> 
		</tr>
		<%}%>
		</table>
	
	