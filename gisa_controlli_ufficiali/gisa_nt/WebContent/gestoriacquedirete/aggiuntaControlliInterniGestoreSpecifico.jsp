<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@page import="org.aspcfs.modules.gestoriacquenew.base.*"%>
<%@ include file="../initPage.jsp" %>
<script src="gestoriacquedirete/script.js"></script>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="GestoreAcque" class="org.aspcfs.modules.gestoriacquenew.base.GestoreAcque" scope="session" /> <!-- vuoto se non è stato trovato per l'user id quel gestore -->

<head>

</head>
<body>
<script src="script.js"></script>

<%if(GestoreAcque.getId() <= 0) {%>
	<!-- non e' stato trovato gestore acque per l'user id dell'utente loggato -->
	<br>
	<br>
	<center>
	<font color="red"> Attenzione, per l'utente <%=User.getUsername() %> non e' stato trovato un Gestore Acque Di Rete Associato</font>
	</center>

<%}
  else 
  {%>
	
	<br>
	<br>
	<!-- e' stato trovato gestore acque per l'user id dell'utente loggato -->
	<center>
	<table class="details" width="60%">
		<tr>
			<th align="center" colspan="2"> <center> 
					Funzione Importazione Controlli Interni per i Punti di Prelievo del Gestore<br>
					<font color="red" size="1" ">
						* Attenzione : I controlli interni caricati da file, devono specificare correttamente il punto di prelievo associato, tramite il codice Gisa assegnato allo stesso.
					</font>
					&nbsp; <br/><br/>
					
				</center>
			</th>
		</tr>
		
		<form id="form2" action="StabilimentoGestoriAcqueReteNewAction.do?command=AggiuntaControlliInterniEffettivo" method="post" name="form2" enctype="multipart/form-data">
		
		<tr>
			<td>
				Scelta tipo controlli da inviare
			</td>
			<td>
				<input type="radio" id="tipo_decreto_31" name="tipo_decreto" value="31" checked="checked"><label for="tipo_decreto_31">DECRETO 31/2001</label> <input type="button" value="file d'esempio decreto 31/2001" onclick="apriTemplateEsempioCI('31')"/> <br/>
				<input type="radio" id="tipo_decreto_28" name="tipo_decreto" value="28"><label for="tipo_decreto_28">DECRETO 28/2017</label> <input type="button" value="file d'esempio decreto 28/2017 (radioattività)" onclick="apriTemplateEsempioCI('28')"/> 
			</td>
		</tr>
		<tr>
			<td colspan="2" style="width: 250px;">
			
			    		 <input id="file1" name="file1" size="45" type="file" title="Import Excel file">
			    		 
			        	<input id="uploadButton" name="uploadButton" value="UPLOAD" onclick="inviaFileExcel(document.forms['form2'])" type="button">
			      		<%if(request.getAttribute("msg_import") != null) {%>
							
							<div style="background-color: lightgreen;" >${msg_import}</div> 
						<%} %>
			      
			          <img id="image_loading" src="gestione_documenti/images/loading.gif" height="15" hidden="hidden">
			          <input disabled="" id="text_loading" name="text_loading" value="Caricamento in corso..." style="border: none" hidden="hidden" type="text">
			   
			    <input name="tipo_richiesta" id="tipo_richiesta" type="hidden" value="gestori_acque_rete_import_controlli_interni"/>
			    
			    <input id="id"     name="id" type="hidden" value="<%=GestoreAcque.getId()%>">
				<input id="alt_id" name="alt_id" type="hidden" value="<%=GestoreAcque.getAltId()%>">
				<input id="op" name="op" type="hidden" value="">
				<input id="tipoAllegato" name="tipoAllegato" type="hidden" value="ALLEGATO">
					
			 
			   </form>
				
				
				
				<%
			   
			   /*stampo gli esiti del parsing file e delle insert */
			   
			   
			   if(request.getAttribute("esitiErroriParsingFile") != null && request.getAttribute("esitiInsert") != null)
			   {%>
			   		 <br><font style="font-weight: bold;">Log file:</font>	
			   		 <%= ( (StringBuffer)request.getAttribute("esitiErroriParsingFile") ).toString() %>
			   		 <br>
			   		 <br><font style="font-weight: bold;">Log inserimenti:</font>
			   		 <%= ( (StringBuffer)request.getAttribute("esitiInsert") ).toString() %>
			   <%} %>
			
			</td>
		</tr>
	</table>
	</center>



<%} %>

 
 
  
 
   
	
<br/><br/>

 <script>
 
 	function apriTemplateEsempioCI(decreto)
 	{
 		var urlTemplate = "";

 		if (decreto=='28')
 			urlTemplate = "gestoriacquedirete/controlli_interni_"+decreto+"_data.xlsx";
 		else
 	 		var urlTemplate = "gestoriacquedirete/controlli_interni_"+decreto+"_data.xls";
 		window.open(urlTemplate);
 	}
 
 </script>

		 

</body>
</html>