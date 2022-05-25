<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
<jsp:include page="../templates/cssInclude.jsp" flush="true"/>
<%@page import="org.aspcfs.modules.richiestecontributi.base.*"%>
<%--
<head>
		<link rel="stylesheet"  type="text/css" href="css/jmesa.css"></link>
		<script type="text/javascript" src="javascript/jquery-1.3.min.js"></script>
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>
</head>--%>
	<p>
		<font color="red">
			<%=toHtmlValue( (String)request.getAttribute( "Error" ) ) %>
		</font>
	</p>
	
<% 
	ListaCani  listaCani2= (ListaCani)request.getAttribute( "listaCani" );
	List<Cane> listaCani=listaCani2.getListaCani();
	int numCani=listaCani.size();				
%>
			
		<form method="post" name="prova">
			<table class="trails" cellspacing="0">
			<tr>
				<td width="100%">
					  <a href="ReportPraticaContributi.do?command=Report"><dhv:label name="">Report Pratiche Contributi Sterilizzazione</dhv:label></a> >
					  <dhv:label name="">Elenco microchip inseriti nel Progetto</dhv:label>
				</td>
			</tr>
			</table>
<%	if (listaCani.size()==0) {%>

			<font color="red">
				Non sono stati aggiunti microchip nel progetto selezionato	
			</font>

	<% }  
	else  {%>
			
			<table  class="details"  cellspacing="0" cellpadding="4" border="0"  width="80%">
				<tr>
					<th colspan="14"> 
					 <dhv:label name="" ><font color="blue" size="2" ><i>Nel progetto sono stati inseriti i seguenti microchip </i></font></dhv:label>
					</th>
				</tr>
				<tr>
					<th ><font color="blue">MICROCHIP</font></th>
					<th ><font color="blue">PROPRIETARIO</font></th>
					<th ><font color="blue">TIPO ANIMALE</font></th>
					<th ><font color="blue">TIPOLOGIA</font></th>
					<th ><font color="blue">COMUNE CATTURA</font></th>
					<th ><font color="blue">COMUNE PROPRIETARIO</font></th>
					<th ><font color="blue">COMUNE COLONIA</font></th>
					<th ><font color="blue">DATA STERILIZZAZIONE</font></th>
				</tr>
				
				<%  Cane c;
					for (int i=0;i<listaCani.size();i++) {
					c=listaCani.get(i);
				%>	
				
				<tr>						
						<td>
						<dhv:evaluate if="<%= !("".equals(c.getMicrochip())) %>">
							<%=c.getMicrochip() %> 
						</dhv:evaluate>	
						<dhv:evaluate if="<%= ("".equals(c.getMicrochip())) %>">
							<%="  ----------------------" %> 
						</dhv:evaluate>	
						</td>
						<td><%=c.getProprietario() %></td>
						<td><%=c.getTipo_animale() %></td>
						<td><%=c.getTipologia() %></td>
						<%if  (c.getComuneCattura()==null || (c.getComuneCattura().equals(""))) {%>
						<td>   --- </td>
						<% } else {%>
						<td><%=c.getComuneCattura() %></td>
						<%} %>
						
						<%if  (c.getComune_proprietario()==null || (c.getComune_proprietario().equals(""))) {%>
						<td>   --- </td>
						<% } else {%>
						<td><%=c.getComune_proprietario() %></td>
						<%} %>
						
						<%if  (c.getComune_colonia()==null || (c.getComune_colonia().equals(""))) {%>
						<td>   --- </td>
						<% } else {%>
						<td><%=c.getComune_colonia()%></td>
						<%} %>
						<td><%=c.formatData(c.getDataSterilizzazione()) %></td>
						
					</tr>	
					<%}%>
			</table>
		</form>
<%} %>