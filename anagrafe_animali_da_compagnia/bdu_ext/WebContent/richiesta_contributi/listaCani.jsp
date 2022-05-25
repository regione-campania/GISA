<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ include file="../initPage.jsp" %>
	


<%@page import="org.aspcfs.modules.richiestecontributi.base.RichiestaContributi"%>
<%@page import="org.aspcfs.modules.anagrafe_animali.base.*,org.aspcfs.modules.opu.base.*"%>

<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<jsp:useBean id="Asl" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="nuova_pratica" class="org.aspcfs.modules.praticacontributi.base.Pratica" scope="request"/>
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@page import="org.aspcfs.modules.praticacontributi.base.Pratica"%>

<script>
function settaNo(size)
{
	for (var j=0; j<size;j++){
		var no='checkCane_no'+j;
		var si='checkCane_si'+j;
		document.getElementById(no).checked=true;
		document.getElementById(si).checked=false;
		}

	
}

function settaSi(size)
{
	for (var j=0; j<size;j++){
		var si='checkCane_si'+j;
		var no='checkCane_no'+j;
		document.getElementById(si).checked=true;
		document.getElementById(no).checked=false;
		}

	
}


</script>

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
		ContributiAnimaleList lc = (ContributiAnimaleList)request.getAttribute( "listaCani" );
		RichiestaContributi rc = (RichiestaContributi)request.getAttribute( "richiestaContributi" );
		String tipo=rc.getTipo_richiesta();
		
		int numCani=lc.size();				
		if (lc.size()==0) {
		
			%>
			<font color="red">
				Non ci sono animali su cui si può fare la richiesta per il progetto prescelto	
			</font>
			<%}			
				
		else {
		%>
	<script>	
		function avviaRichiesta(){
	document.saveRichiesta.action='ContributiSterilizzazioni.do?command=SaveRichiesta&sizeCani=<%=lc.size()%>'; 
	//Flusso 251
	if (confirm('Si è sicuri di voler proseguire?')){ 
		//Flusso 251
		if(true){
		//if (confirm('Cliccando su OK non sarà più possibile reinviare questo progetto')){ 
        	loadModalWindow(); //ATTENDERE PREGO     
        	this.saveRichiesta.submit();}
		}
}
	</script>
		
		<form method="post" name="saveRichiesta" action="ContributiSterilizzazioni.do?command=SaveRichiesta&sizeCani=<%=lc.size() %>" onSubmit="return verifica();">
			
			<input type="button" value="Conferma la richiesta" onclick="javascript:avviaRichiesta();">
			<%--<input type="button" value="Conferma il pagamento per tutti i cani selezionati" onclick="javascript:this.form.action='ContributiSterilizzazioni.do?command=ConfermaPagamenti&&sizeCani=<%=listaCani.size() %>&&n_protocollo=<%=session.getAttribute("n_protocollo") %>';if (confirm('Si vuole proseguire con il pagamento dei contributi per i cani selezionati?')){submit()};">--%> 
			<input type="button" value="Annulla" onclick="location.href='ContributiSterilizzazioni.do'" />
			<input type="button" value="Deseleziona tutti" onclick="javascript:settaNo(<%=lc.size() %>)" />
			<input type="button" value="Seleziona tutti" onclick="javascript:settaSi(<%=lc.size() %>)" />
		
			
			</br>
			
						
			<table class="details"  cellspacing="0" cellpadding="4" border="0"  width="80%">
				<tr>
					<th colspan="9"> Pratica N°
					<%Pratica p= (Pratica)request.getAttribute( "nuova_pratica" );%>
					<%=p.getNumeroDecretoPratica() %> 
					del <%=p.getDataDecretoFormattata() %>	
					<!-- &nbsp;-->	
					</br>
					</br>	
					Richiesta contributi su <%=lc.size() %> 
					<% 
					if (numCani==1) {
					%>
						Animale
					<% } else { %>
						Animali
					<%} %>
					
					</th>
				</tr>
				<tr>
					<%--<th >ID cane</th>--%>
					<th >microchip</th>
					<th >proprietario</th>
					<th >tipo animale</th>
					<th >tipologia</th>
					<th >comune cattura</th>
					<th >comune proprietario</th>
					<th >comune colonia</th>
					<th >data sterilizzazione</th>
					<th width="30%"> Richiesta? </th>
				</tr>
				
				<%  Animale thisAnimale;
					for (int i=0;i<lc.size();i++) {
						thisAnimale = (Animale) lc.get(i);
				%>					
					<tr>						
						<td>
						<dhv:evaluate if="<%= !("".equals(thisAnimale.getMicrochip())) %>">
							<%=thisAnimale.getMicrochip() %> 
						</dhv:evaluate>	
						<dhv:evaluate if="<%= ("".equals(thisAnimale.getMicrochip())) %>">
							<%="  ----------------------" %> 
						</dhv:evaluate>	
						</td>
						<td>
						<dhv:evaluate if="<%=(thisAnimale.getNomeCognomeProprietario() != null && !("").equals(thisAnimale.getNomeCognomeProprietario()))%>">
						<%=thisAnimale.getNomeCognomeProprietario() %>
						</dhv:evaluate>&nbsp;
						</td>
						<td><%=specieList.getSelectedValue(thisAnimale.getIdSpecie()) %></td>
						<dhv:evaluate if="<%=thisAnimale.isFlagCattura()%>">
							<td>Catturato</td>
						</dhv:evaluate>
						<dhv:evaluate if="<%=!thisAnimale.isFlagCattura()%>">
							<td>Padronale</td>
						</dhv:evaluate>
						
						<dhv:evaluate if="<%=thisAnimale.isFlagCattura()%>">
						<% 	if (thisAnimale.getIdComuneCattura() > 0) {
							%>					
						
						<td><%= comuniList.getSelectedValue(thisAnimale.getIdComuneCattura()) %></td>
						<%}else{ %> <td>  --- </td> <%} %>
						</dhv:evaluate>
						<dhv:evaluate if="<%=!thisAnimale.isFlagCattura()%>">
						<td>  --- </td>
						</dhv:evaluate>
						<!-- Comune proprietario -->
						<td>
						<dhv:evaluate if="<%=(thisAnimale.getIdComuneProprietario() > 0)%>">

						<%= comuniList.getSelectedValue(thisAnimale.getIdComuneProprietario()) %>
						</dhv:evaluate>&nbsp;
						</td>
						<!-- EVENTUALE COMUNE COLONIA-->
						
						<%
						// int idComuneColonia = -1;
						if (thisAnimale.getIdSpecie() == Gatto.idSpecie){
						//	Gatto thisGatto  = (Gatto) lc.get(i);
						//	Operatore detentore = thisGatto.getDetentore();
							
						//	if (detentore != null && detentore.getListaStabilimenti().size() > 0){
						 // 		Stabilimento stabDet = (Stabilimento) detentore.getListaStabilimenti().get(0);
						 // 		LineaProduttiva lp = (LineaProduttiva) stabDet.getListaLineeProduttive().get(0);
						  //		if (lp.getIdAttivita() == 7){
						  //  		Indirizzo indS =  stabDet.getSedeOperativa();
							
						 //  idComuneColonia = indS.getComune();
						  	//	}
							//}
						
							%>
							<td><%= comuniList.getSelectedValue(thisAnimale.getIdComuneDetentore()) %></td>
						 <%} else {%>
						 <td>  --- </td>
						 <%} %>
							<td><%=toDateasString(thisAnimale.getDataSterilizzazione()) %></td>
						

						<td>
						<input type="radio" id="checkCane_si<%=i%>" name="checkCane<%=i%>" value="true" checked> Si
						<input type="radio"  id="checkCane_no<%=i%>" name="checkCane<%=i%>" value="false" > No<br>
									
						</td>						
					</tr>	
					
				<%}%>
				
				
				<input type="hidden" name="id_pratica" value="<%=p.getId()%>" />
			</table>
			
	    </form>
<%}%>