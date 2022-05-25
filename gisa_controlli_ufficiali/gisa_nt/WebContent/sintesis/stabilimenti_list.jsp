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

<jsp:useBean id="limit" class="java.lang.String" scope="request"/>
<jsp:useBean id="offset" class="java.lang.String" scope="request"/>
<jsp:useBean id="tot" class="java.lang.String" scope="request"/>

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

<script>function refreshLista(form, tipo){
	
	if (tipo=='Indietro'){
		if (parseInt(form.offset.value) - parseInt(form.limit.value) <0)
			form.offset.value = '0';
		else
			form.offset.value = parseInt(form.offset.value) - parseInt(form.limit.value);
	}
	if (tipo=='Avanti'){
		if (parseInt(form.offset.value) + parseInt(form.limit.value) >= parseInt(form.tot.value))
			form.offset.value = form.offset.value;
		else
			form.offset.value = parseInt(form.offset.value) + parseInt(form.limit.value);	
		}
	
	if (tipo=='Inizio'){
			form.offset.value = '0';
	}
	if (tipo=='Fine'){
		form.offset.value = parseInt(form.tot.value) - parseInt(form.limit.value);
	}
	
	if (tipo=='Refresh'){
		form.offset.value = '0';
		form.limit.value = form.limitSelect.value;
	}	
	
	loadModalWindow();
	form.submit();
}</script>

		
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
  
  	
  	<center><b>Lista stabilimenti</b></center>
  		
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
				
				<%if (rel.getPathCompletoLineaProduttivaOld()!=null) { %> <i><%=rel.getPathCompletoLineaProduttivaOld() %></i> <%} %>
				
				(<%=StatiStabilimento.getSelectedValue(rel.getStato()) %> )
				
				<%if (rel.getAutomezzi().size()>0){ %>
				<br/><b>Automezzi: </b> 
				<% for (int k=0; k<rel.getAutomezzi().size(); k++){  
				  SintesisAutomezzo automezzo = (SintesisAutomezzo) rel.getAutomezzi().get(k);%> 
				  <%=automezzo.getAutomezzoTarga() %> (<%=automezzo.getNumeroIdentificativo() %>); 
				  <% } %>
				<%} %>
				<br/><br/>
				<%}%>
			
			
			</td> 
			
			
		</tr>
		<%} } else {%>
		<tr><td colspan="6"> Non sono stati trovati stabilimenti.</td></tr> 
		
		<% } %>
		
	
	</table>
	
<br/><br/>


<!-- PAGINAZIONE -->
<div align="right">

<%
int numPagina = (Integer.parseInt(offset) / Integer.parseInt(limit) +1);
int totPagine = Integer.parseInt(tot) / Integer.parseInt(limit);

if (Integer.parseInt(limit) == -1)
	totPagine = 1;
%>

<form id="sel" name="sel" action="StabilimentoSintesisAction.do?command=ListaStabilimenti&auto-populate=true" method="post">

<input type="hidden" id="limit" name="limit" value="<%=limit%>"/>
<input type="hidden" id="offset" name="offset" value="<%=offset%>"/>
<input type="hidden" id="tot" name="tot" value="<%=tot%>"/>


<% if (numPagina>1 && totPagine > 1){ %>
<input type="button" style="width:40px; font-size: 15px; font-weight: bold" value=" << " onClick="refreshLista(this.form, 'Inizio')"/>
<%} %>

<% if (numPagina > 1){ %>
<input type="button" style="width:40px; font-size: 15px; font-weight: bold" value=" < " onClick="refreshLista(this.form, 'Indietro')"/>
<%} %>

<% if (numPagina < totPagine){ %>
<input type="button" style="width:40px; font-size: 15px; font-weight: bold" value=" > " onClick="refreshLista(this.form, 'Avanti')"/>
<%} %>

<% if (numPagina>1 && numPagina!=totPagine){ %>
<input type="button" style="width:40px; font-size: 15px; font-weight: bold" value=" >> " onClick="refreshLista(this.form, 'Fine')"/>
<%} %>

<br/><br/>

Pagina <b><%= numPagina %></b> di <b><%=totPagine %></b> 

<br/><br/>

Elementi per pagina :
<select id="limitSelect" name="limitSelect" onChange="refreshLista(this.form, 'Refresh')">
<option value="10" <%=limit.equals("10") ? "selected" : "" %>>10</option>
<option value="50" <%=limit.equals("50") ? "selected" : "" %>>50</option>
<option value="100" <%=limit.equals("100") ? "selected" : "" %>>100</option>
<option value="-1" <%=limit.equals("-1") ? "selected" : "" %>>Tutti (<%=tot %>)</option>
</select> 

</form>

</div>
<!-- PAGINAZIONE -->

<br/><br/>

</dhv:container>

</body>
</html>