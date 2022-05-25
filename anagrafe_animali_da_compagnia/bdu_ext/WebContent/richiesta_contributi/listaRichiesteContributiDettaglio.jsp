<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
	

<%@page import="org.aspcfs.modules.richiestecontributi.base.RichiestaContributi,org.aspcfs.modules.anagrafe_animali.base.*,org.aspcfs.modules.opu.base.*"%>

<jsp:useBean id="Asl" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>


	<head>
		<link rel="stylesheet"  type="text/css" href="css/jmesa.css"></link>
		
		<script type="text/javascript" src="javascript/jquery-1.3.min.js"></script>
		<script type="text/javascript" src="javascript/jquery.bgiframe.pack.js"></script>
		<script type="text/javascript" src="javascript/jquery.jmesa.js"></script>
		<script type="text/javascript" src="javascript/jmesa.js"></script>
	
	
	
	</head>
		<p>
			<font color="red">
				<%=toHtmlValue( (String)request.getAttribute( "Error" ) ) %>
			</font>
		</p>
		
		<%
		List<Animale> listaCani=(List)session.getAttribute( "listaCaniDettaglio" );
		int numCani=listaCani.size();
		if (numCani>0){
							
		%>
		<body>
		<form method="post" name="saveRichiesta" action="ContributiSterilizzazioni.do?command=ConfermaPagamenti&&sizeCani=<%=listaCani.size() %>">
		<input type="button" value="Conferma il pagamento dei contributi" onclick="javascript:this.form.action='ContributiSterilizzazioni.do?command=ConfermaPagamenti&&sizeCani=<%=listaCani.size() %>&&n_protocollo=<%=session.getAttribute("n_protocollo") %>';if (confirm('Si vuole proseguire con il pagamento dei contributi per i microchip indicati?')){submit()};"> 
			
	
 <%--<input type="submit" value="Conferma il pagamento per tutti gli animali selezionati" onclick="this.form.action='ContributiSterilizzazioni.do?command=ConfermaPagamenti&&sizeCani=<%=listaCani.size() %>'" />--%>
			
		    <%-- <input type="button" value="Rifiuta per modifica" onclick="this.form.action='ContributiSterilizzazioni.do?command=Respingi&&sizeCani=<%=listaCani.size() %>';if (confirm('Sei sicuro di voler rifiutare  il pagamento dei contributi per i microchip indicati per modifica?')){submit()};" />--%> 
			
			
			<input type="button" value="Rifiuta per Chiusura" onclick="this.form.action='ContributiSterilizzazioni.do?command=RifiutaPagamenti&&sizeCani=<%=listaCani.size() %>';if (confirm('Sei sicuro di voler rifiutare il pagamento dei contributi per i microchip indicati e chiudere il progetto?')){submit()};" />
				
			<input type="button" value="Rifiuta per modifica" onclick="this.form.action='ContributiSterilizzazioni.do?command=Respingi&&sizeCani=<%=listaCani.size() %>';if (confirm('Sei sicuro di voler rifiutare  il pagamento dei contributi per i microchip indicati per modifica?')){submit()};" />	
					
			<input type="button" value="Annulla" onclick="location.href='ContributiSterilizzazioni.do'" />
		
						
			<table class="details"  cellspacing="0" cellpadding="6" border="0" width="60%">
				<tr>
					<th colspan="11" align="center">Dettaglio Richiesta contributi su <%=listaCani.size() %> 
					<% 
					if (numCani==1) {
					%>
					Animale
					<% }
					else { %>
					Animali
					<%} %>
					</th>
				</tr>
								
				<tr>
					<th> Microchip</th>
					<th> Proprietario</th>
					<th> Tipo di Animale</th>
					<th> Asl richiedente</th>
					<th> Data Sterilizzazione</th>
					<th> Comune Proprietario </th>
					<th> Comune Cattura </th>
					<th> Comune Colonia </th>
				<%--	<th> Comune </th>--%>
					<th> Tipologia</th>
					<th width="20%"> Pagamento? </th>
					<th> Note </th>
					
				</tr>
				
				<%  Animale c;
					for (int i=0;i<listaCani.size();i++) {
					c=listaCani.get(i);
				%>
				
				<% if(c.isContributoPagato()) {%>
				<tr>
					<td>
					<dhv:evaluate if="<%= !("".equals(c.getMicrochip())) %>">
						<font color="red"><%=c.getMicrochip() %></font>
					</dhv:evaluate>
					<dhv:evaluate if="<%= ("".equals(c.getMicrochip())) %>">
						<font color="red"><%="  ----------------------" %></font>
					</dhv:evaluate>	
					</td>
					<td>
						<font color="red"><%=c.getNomeCognomeProprietario() %></font>
					</td>
						<dhv:evaluate if="<%=c.isFlagCattura()%>">
							<td>Catturato</td>
						</dhv:evaluate>
						<dhv:evaluate if="<%=!c.isFlagCattura()%>">
							<td>Padronale</td>
						</dhv:evaluate>
					
					
					<td>
						<font color="red"><%=Asl.getSelectedValue(c.getIdAslRiferimento()) %></font>
					</td>
					<td>
						<font color="red"><%=toDateasString(c.getDataSterilizzazione()) %></font>
					</td>
						<!-- Comune proprietario -->
						<%//Operatore proprietario = c.getProprietario();
						  //Stabilimento stab = (Stabilimento) proprietario.getListaStabilimenti().get(0);
						  //Indirizzo ind =  stab.getSedeOperativa();
						  //int idComune = ind.getComune();
							%>
						<td><%= comuniList.getSelectedValue(c.getIdComuneProprietario()) %></td>
						
						<!-- Comune cattura in caso di cane -->
					
						<%if (c.getIdSpecie() == Cane.idSpecie) {
							Animale thisCane = (Animale) listaCani.get(i);
							if  (thisCane.getIdComuneCattura() < 1 ) {%>
						<td>  --- </td>
						<% } else {%>
						<td><%= comuniList.getSelectedValue(thisCane.getIdComuneCattura()) %></td>
						<%}}else{ %> <td>  --- </td> <%} %>
					
				
						<!-- Comune colonia -->
						<%
						 int idComuneColonia = -1;
						if (c.getIdSpecie() == Gatto.idSpecie){
							//Gatto thisGatto  = (Gatto) listaCani.get(i);
							//Operatore detentore = thisGatto.getDetentore();
							
						//	if (detentore != null && detentore.getListaStabilimenti().size() > 0){
						  //		stab = (Stabilimento) detentore.getListaStabilimenti().get(0);
						  //		LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
						  //		if (lp.getIdAttivita() == 7){
						    //		ind =  stab.getSedeOperativa();
							
						 //  idComuneColonia = ind.getComune();
						 idComuneColonia = c.getIdComuneDetentore();
						  		}
							//}
						//}
							%>
							<td><%= comuniList.getSelectedValue(idComuneColonia) %> &nbsp; </td>
					
					<td>
						<font color="red"><%=specieList.getSelectedValue(c.getIdSpecie()) %></font>
					</td>
					
					<td>
						<font color="red">NO</font>
						<input type="hidden" name="pagamentoCane<%=i%>" value="false">  						
					</td>
					<td>
						<font color="red">Questo animale risulta già pagato precedentemente</font>
					</td>
				</tr>
				<%}
				else {%>
				<tr>
					<td><%=c.getMicrochip() %></td>
					
					<td><%=c.getNomeCognomeProprietario() %></td>
				
					<dhv:evaluate if="<%=c.isFlagCattura()%>">
						<td>Catturato</td>
					</dhv:evaluate>
					<dhv:evaluate if="<%=!c.isFlagCattura()%>">
						<td>Padronale</td>
					</dhv:evaluate>
					
					<td><%=Asl.getSelectedValue(c.getIdAslRiferimento()) %></td>
					<td><%=toDateasString(c.getDataSterilizzazione())%></td>

					<!-- Comune proprietario -->
					<%//Operatore proprietario = c.getProprietario();
					  //Stabilimento stab = (Stabilimento) proprietario.getListaStabilimenti().get(0);
					 // Indirizzo ind =  stab.getSedeOperativa();
					 // int idComune = ind.getComune();
						%>
					<td><%= comuniList.getSelectedValue(c.getIdComuneProprietario()) %></td>
					
						<%if (c.getIdSpecie() == Cane.idSpecie) {
							Animale thisCane = (Animale) listaCani.get(i);
							if  (thisCane.getIdComuneCattura() < 1 ) {%>
						<td>  --- </td>
						<% } else {%>
						<td><%= comuniList.getSelectedValue(thisCane.getIdComuneCattura()) %></td>
						<%}}else{ %> <td>  --- </td> <%} %>
						
						
					<!-- Comune colonia -->
						<%
						 int idComuneColonia = -1;
						//if (c.getIdSpecie() == Gatto.idSpecie){
						//	Gatto thisGatto  = (Gatto) listaCani.get(i);
						//	Operatore detentore = thisGatto.getDetentore();
							
						//	if (detentore != null && detentore.getListaStabilimenti().size() > 0){
						  //		stab = (Stabilimento) detentore.getListaStabilimenti().get(0);
						  //		LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
						  	//	if (lp.getIdAttivita() == 7){
						    //		ind =  stab.getSedeOperativa();
							
						 //  idComuneColonia = ind.getComune();
						 idComuneColonia = c.getIdComuneDetentore();
						  	//	}
							//}
						//}
							%>
							<td><%= comuniList.getSelectedValue(idComuneColonia) %> &nbsp; </td>

					<td><%=specieList.getSelectedValue(c.getIdSpecie()) %></td>
					<td>SI
						<input type="hidden" name="pagamentoCane<%=i%>" value="true">  
					</td>
					<td>
					-
					</td>
				</tr>
				<%} 
				}%>
				
			</table>
			
			
			
	    </form>
<%} else {%>
<label>
		<font color="red">
			Operazione non possibile perchè non ci sono animali per la richiesta corrente			</font>
		</label>
<%} %>
</body>