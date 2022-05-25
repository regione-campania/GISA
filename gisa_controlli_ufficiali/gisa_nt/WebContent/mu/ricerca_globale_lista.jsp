<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.mu.base.Articolo17"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ include file="../../initPage.jsp"%>
<jsp:useBean id="listaCapi" class="java.util.Vector" scope="request"/>
<jsp:useBean id="listaArticoli17" class="org.aspcfs.modules.mu.base.Articolo17List" scope="request"/>


 <jsp:useBean id="specieList"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="specieBovine"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="razzeBovine"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="categorieBovine"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="categorieBufaline"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="catRischio"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 <jsp:useBean id="PianiRisanamento"			class="org.aspcfs.utils.web.LookupList" scope="request" />
 
 
<%@page import="org.aspcfs.modules.mu.base.CapoUnivoco"%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<script
	language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>

 <script>$(document).ready(function() {
      // Initialize Smart Wizard
      loadModalWindowUnlock();
      
    //  $('#wizard').smartWizard(); 
//       $('#wizard').smartWizard(    		  
//     		     {
//     		      divHeadPaddingLeft:  2,
//     		      divBodyPaddingLeft:   2,
//     		      fixedTypeNumber:  1
    		      			
//     		      	    }); 

      
  }); 
</script>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<script type="text/javascript">
	//loadModalWindow();
	loadModalWindowCustom('<div style="color:red; font-size: 40px;">Attendere prego.... </div>');
	</script>
<html>

<body>

<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="MacellazioneUnica.do?command=List&orgId=51251">Home macellazioni
		</td>
	</tr>
</table>


      <input type="hidden" id="dimensioneLista" name="dimensioneLista" value="<%=listaCapi.size() %>"/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr> <th colspan="8"> <center>Lista capi</center> </th></tr>
  
		<tr>
			<th><strong>MatricolaAAA</strong></th>
			<th><strong>Specie</strong></th>
			<th><strong>Categoria</strong></th>
			<th><strong>Razza</strong></th>
			<th><strong>Data di nascita</strong></th>
			<th><strong>Sesso</strong></th>
			<th><strong>Categoria di rischio</strong></th>
			<th><strong>Partita</strong></th>
		</tr>
	
			
			<%
	
	if (listaCapi.size()>0){
		for (int i=0;i<listaCapi.size(); i++){
			CapoUnivoco capo = (CapoUnivoco) listaCapi.get(i); %>
			
			<tr class="row<%=i%2%>">
			<td><%=capo.getMatricola() %></td>
			<td><%=specieList.getSelectedValue(capo.getSpecieCapo()) %></td>
			<td><%=specieBovine.getSelectedValue(capo.getCategoriaCapo()) %>  <%=categorieBovine.getSelectedValue(capo.getCategoriaBovina()) %> <%=categorieBufaline.getSelectedValue(capo.getCategoriaBufalina()) %></td>
			<td><%=razzeBovine.getSelectedValue(capo.getRazzaBovina()) %></td>
			<td><%=capo.getDataNascita() %></td>
			<td><%=capo.getSesso() %></td>
			<td><%=catRischio.getSelectedValue(capo.getCategoriaRischio()) %></td>
			<td><a href="javascript:popURL('MacellazioneUnica.do?command=DettaglioPartita&idPartita=<%=capo.getIdPartita()%>&popup=true', null, '800px', null, 'yes', 'yes')">Partita</a></td>
			
			</tr>
			<% } } else {%>
		<tr><td colspan="8"> Non sono stati trovati capi. &nbsp; </td></tr>
		
		<% } %>
		
		</table>
		
		
		</br></br>
		
		
		  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr> <th colspan="8"> <center>Lista Articoli 17</center> </th></tr>
  
		<tr>
			<th><strong>Descrizione</strong></th>
			<th><strong>Esercente</strong></th>
			
		</tr>
	
			
			<%
	
	if (listaArticoli17 != null  && listaArticoli17.size()>0){
		for (int k=0;k<listaArticoli17.size(); k++){
			Articolo17 articolo = (Articolo17) listaArticoli17.get(k); %>
			
			<tr class="row<%=k%2%>">
			<td><%=articolo.getDescrizione() %></td>
			<td><%=articolo.getIdEsercente() %></td>
			
			
			</tr>
			<% } } else {%>
		<tr><td colspan="8"> Non sono stati trovati articoli 17. &nbsp; </td></tr>
		
		<% } %>
		
		</table>


</body>
</html>