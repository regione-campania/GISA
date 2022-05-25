<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.sintesis.base.SintesisRelazioneLineaProduttiva"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>



<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<jsp:useBean id="listaStabilimenti" class="java.util.ArrayList" scope="request"/>

<jsp:useBean id="ToponimiList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ProvinceList" class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="StatiStabilimento" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StatiLinea" class="org.aspcfs.utils.web.LookupList" scope="request" />


<%@page import="org.aspcfs.modules.sintesis.base.*"%>

<%@ include file="../initPage.jsp" %>


<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>

<script>function refreshLista(form){
	loadModalWindow();
	form.submit();
}

function differenzaDate(i, j, data1){
	var sdt = new Date(data1);
	var difdt = new Date(new Date() - sdt);
	
	var anni = (difdt.toISOString().slice(0, 4) - 1970);
	var mesi =   (difdt.getMonth()+1);
	var giorni =  difdt.getDate();
	mesi--;
	giorni--;
	document.getElementById("scarto_"+i+"_"+j).innerHTML= anni + " anni " + mesi + " mesi " + giorni + " giorni";
}

</script>

		
 <%! public static String fixData(String timestring)
  {
	  String toRet = "";
	  if (timestring == null)
		  return toRet;
	  String anno = timestring.substring(0,4);
	  String mese = timestring.substring(5,7);
	  String giorno = timestring.substring(8,10);
	  String ora = timestring.substring(11,13);
	  String minuto = timestring.substring(14,16);
	  String secondi = timestring.substring(17,19);
	  toRet =giorno+"/"+mese+"/"+anno+" "+ora+":"+minuto+":"+secondi;
	  return toRet;
	  
  }%>

    <br>
   
  <dhv:container name="sintesisimport" selected="Elenco Stabilimenti" object=""  param="">
  
  	
  	<center><b>Lista stabilimenti attualmente in stato ANAGRAFE, con data inizio linea produttiva piu' vecchia di 6 mesi (soglia di 15 giorni)</b></center>
  		
<table  class="details" width="100%">

		<tr>
			<th>Stato</th>
			<th>Approval number</th>
			<th>Denominazione</th>
			<th>Ragione sociale impresa</th>
			<th>Partita IVA</th>
			<th>Indirizzo</th>
			<th>Linea Attivita'</th>
		</tr>
			<%

	if (listaStabilimenti.size()>0) {
		for (int i=0;i<listaStabilimenti.size(); i++){
			SintesisStabilimento stab = (SintesisStabilimento) listaStabilimenti.get(i);
			
			%>
			
			<tr class="row<%=i%2%>">
			<td><%= StatiStabilimento.getSelectedValue(stab.getStato())%></td> 
			<td><%=stab.getApprovalNumber() %></td> 
			<td> <a href="StabilimentoSintesisAction.do?command=DettaglioStabilimento&id=<%=stab.getIdStabilimento()%>"><%=stab.getDenominazione() %></a></td> 
			<td><%=stab.getOperatore().getRagioneSociale() %> </td> 
			<td><%=stab.getOperatore().getPartitaIva() %> </td> 
			<td><%=ToponimiList.getSelectedValue(stab.getIndirizzo().getToponimo())%> <%=stab.getIndirizzo().getVia() %>  <%=toHtml(stab.getIndirizzo().getCivico()) %>, <%=ComuniList.getSelectedValue(stab.getIndirizzo().getComune())%>, <%=ProvinceList.getSelectedValue(stab.getIndirizzo().getIdProvincia())%></td> 
			<td>
			
			<% for (int j = 0; j<stab.getLinee().size(); j++){
				SintesisRelazioneLineaProduttiva rel = (SintesisRelazioneLineaProduttiva) stab.getLinee().get(j);
				%>
				
				<%=rel.getPathCompleto() %> 
				(<%=StatiStabilimento.getSelectedValue(rel.getStato()) %> )<br/>
				<b>Data Inizio</b>: <%=toDateasString(rel.getDataInizio()) %> 
				<b>Scarto: </b> <label id="scarto_<%=i %>_<%=j %>"></label> <script>differenzaDate('<%=i%>', '<%=j%>', '<%=rel.getDataInizio()%>');</script> 
				<br/><br/>

				<%
			}%>
			
			
			</td> 
			
			
		</tr>
		<%} } else {%>
		<tr><td colspan="6"> Non sono stati trovati stabilimenti.</td></tr> 
		
		<% } %>
		
	
	</table>
	
<br/><br/>




		</dhv:container>

</body>
</html>