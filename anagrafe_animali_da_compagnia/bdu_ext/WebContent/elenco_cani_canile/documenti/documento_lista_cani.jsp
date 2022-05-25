<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>

<%@page import="org.aspcfs.modules.anagrafe_animali.base.Animale,org.aspcfs.modules.opu.base.*"%>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>	
<jsp:useBean id="listaAnimali" class="org.aspcfs.modules.anagrafe_animali.base.AnimaleList"	scope="request" />
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />


<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<%@ include file="../../initPage.jsp"%>

<link rel="stylesheet" type="text/css" media="screen"
	href="elenco_cani_canile/documenti/screen.css">
<link rel="stylesheet" documentale_url="" href="elenco_cani_canile/documenti/print.css"
	type="text/css" media="print" />


<script type="text/javascript">





$(document).ready(function() {
    $('#prova').submit(function() {
       // alert('ss');
        window.open('', 'formpopup', 'height=595px,width=842px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
        this.target = 'formpopup';
    });
});

</script>

<input type="submit" name="stampa" class="buttonClass"
	onclick="window.print();" value="Stampa" />
	
	<jsp:include page="../../gestione_documenti/boxDocumentale.jsp">
    <jsp:param name="idLinea" value="<%=request.getParameter("id") %>" />
    <jsp:param name="idTipo" value="PrintDocumentoListaCani" />
   </jsp:include>

 <div class="boxIdDocumento"></div>
 <div class="boxOrigineDocumento"><%@ include file="../../hostName.jsp" %></div>
 <% 

	int numCani=listaAnimali.size();	
	java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	Animale thisAnimale = (Animale) listaAnimali.get(0);
	%>
<div class="datavalore"><%=dataToString( timeNow ) %> </div> <br>
<div class="title1"><%=thisAnimale.getNomeCognomeDetentore() %></div>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<center>Sono presenti <%=numCani %> cani vivi.</center>

		<br />
			<br />
			<table cellspacing="0" cellpadding="4" border="0"  width="100%">
				
				<tr>
					<th width="15%"><strong>Microchip</strong></th>
					<th width="15%"><strong>Tatuaggio </strong></th>
					<th width="20%"><strong>Proprietario</strong></th>
					<th width="20%"><strong>Detentore</strong></th>
					<th width="20%"><strong>Comune Proprietario</strong></th>
					<th width="10%"><strong>ASL</strong></th>
						</tr>
				
				
				<%  Animale c;
					for (int i = 0;i < listaAnimali.size(); i++) {
					c = (Animale) listaAnimali.get(i);
					if ((i % 2)==0){ 
				%>					
								
					
				<tr>
				<%} else  {%>
				<tr bgcolor="white">
				<%} %>
					<td><center>
						<dhv:evaluate if="<%= ("".equals(c.getMicrochip()) || c.getMicrochip() == null) %>">
								<%=" --- " %> 
						</dhv:evaluate>	
						<dhv:evaluate if="<%= !("".equals(c.getMicrochip()) || c.getMicrochip() == null) %>">
								<%=c.getMicrochip() %> 
						</dhv:evaluate>
					</center>	</td>					
					<td><center>
						<dhv:evaluate if="<%= ("".equals(c.getTatuaggio()) || c.getTatuaggio() == null) %>">
								<%="  --- " %> 
						</dhv:evaluate>	
						<dhv:evaluate if="<%= !("".equals(c.getTatuaggio()) || c.getTatuaggio() == null) %>">
								<%=c.getTatuaggio() %> 
						</dhv:evaluate>
					</center></td>
					<td><center>&nbsp;
					
						<%=c.getNomeCognomeProprietario() %>
					
					</center></td>
					<td><center>&nbsp;
					
					<%=c.getNomeCognomeDetentore() %>
					
					</center></td>
					
					<dhv:evaluate if="<%= c.getNomeCognomeProprietario() == null %>">
								<td><center><%="  --- " %></center></td>
					</dhv:evaluate>
					
								<td><center>&nbsp; <%= (c.getIdComuneProprietario() > 0 ) ? comuniList .getSelectedValue(c.getIdComuneProprietario()) : "--" %></center></td>
					
					<td><center><%=AslList.getSelectedValue(c.getIdAslRiferimento() )  %></center></td>
			
				</tr>
				<%} %>
			</table>

<br><br><br>

<br/>