<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script>
function gestoreChiudiLista(idAsl, nomeAsl, indiceLista, idLista)
{	
	document.getElementById("idAsl").value = idAsl;
	document.getElementById("nomeAsl").value = nomeAsl;
	document.getElementById("indiceLista").value = indiceLista;
	document.getElementById("idLista").value = idLista;
	
	if (idLista=='-1'){
		document.getElementById("nomeChiusura").innerHTML = "Allerta";
		document.getElementById("indiceLista").style.display="none";
	}
	else{
		document.getElementById("nomeChiusura").innerHTML = "Lista di distribuzione";
		document.getElementById("indiceLista").style.display="block";
	}
	
	$( "#dialogChiudiLista" ).dialog('open');
}

function closeDialog(){
	  $('#dialogChiudiLista').dialog('close');
		
	}
	

$(function () {
	 $( "#dialogChiudiLista" ).dialog({
			autoOpen: false,
	        resizable: false,
	        closeOnEscape: true,
	       	title:" <input type=\"button\" onClick=\"closeDialog()\" style=\"background:transparent !important; color: black !important \" value=\"[X]\" /> CHIUDI ",
	        width:400,
	        height:300, 
	        position: 'top',
	        draggable: false,
	        modal: true,
	        open: function(){
	            jQuery('.ui-widget-overlay').bind('click',function(){
	                jQuery('#dialogChiudiLista').dialog('close');
	            })
	        },
	      show: {
	            effect: "blind",
	            duration: 1000
	        },
	        hide: {
	            effect: "explode",
	            duration: 1000
	        }
	    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
});
function checkFormChiudiLista(){
	var data = document.getElementById("dataChiusuraLista").value;
	var idAsl = document.getElementById("idAsl").value;
	var idLista = document.getElementById("idLista").value;
	var idAllerta = document.getElementById("idAllerta").value;
	if (data=='')
		alert ('Data chiusura obbligatoria');
	else{
		loadModalWindow();
		closeDialog();
		window.location.href='TroubleTicketsAllerteNew.do?command=ChiusuraListaAsl&dataChiusuraLista='+data+'&idAsl='+idAsl+'&idLista='+idLista+'&idAllerta='+idAllerta;
	}
}


function gestoreRipianificaCU(idAsl, nomeAsl, indiceLista, idLista, numCUPianificati)
{	
	document.getElementById("idAslRipianifica").value = idAsl;
	document.getElementById("nomeAslRipianifica").value = nomeAsl;
	document.getElementById("indiceListaRipianifica").value = indiceLista;
	document.getElementById("idListaRipianifica").value = idLista;
	document.getElementById("oldPianificati").value = numCUPianificati;
	
	if (idLista=='-1'){
		document.getElementById("nomeRipianificazione").innerHTML = "Allerta";
		if (document.getElementById("Ripianifica")!=null)
			document.getElementById("Ripianifica").style.display="none";
	}
	else{
		document.getElementById("nomeRipianificazione").innerHTML = "Lista di distribuzione";
		document.getElementById("indiceListaRipianifica").style.display="block";
	}
	
	$( "#dialogRipianificaCU" ).dialog('open');
}

function closeDialogRipianificaCU(){
	  $('#dialogRipianificaCU').dialog('close');
		
	}
	

$(function () {
	 $( "#dialogRipianificaCU" ).dialog({
			autoOpen: false,
	        resizable: false,
	        closeOnEscape: true,
	       	title:" <input type=\"button\" onClick=\"closeDialogRipianificaCU()\" style=\"background:transparent !important; color: black !important \" value=\"[X]\" /> CHIUDI ",
	        width:400,
	        height:500, 
	        position: 'top',
	        draggable: false,
	        modal: true,
	        open: function(){
	            jQuery('.ui-widget-overlay').bind('click',function(){
	                jQuery('#dialogRipianificaCU').dialog('close');
	            })
	        },
	      show: {
	            effect: "blind",
	            duration: 1000
	        },
	        hide: {
	            effect: "explode",
	            duration: 1000
	        }
	    }).prev(".ui-dialog-titlebar").css("background","#bdcfff");
});
function checkFormRipianificaCU(){
	var idAsl = document.getElementById("idAslRipianifica").value;
	var idLista = document.getElementById("idListaRipianifica").value;
	var idAllerta = document.getElementById("idAllertaRipianifica").value;
	var oldPianificati = document.getElementById("oldPianificati").value;
	var newPianificati = document.getElementById("newPianificati").value;
	var motivazione = document.getElementById("motivazione").value;

	if (newPianificati=='') {
		alert ('Num. CU pianificati obbligatorio.');
		}
	else {
		loadModalWindow();
		closeDialogRipianificaCU();
		window.location.href='TroubleTicketsAllerteNew.do?command=RipianificaCUAsl&newPianificati='+newPianificati+'&idAsl='+idAsl+'&idLista='+idLista+'&idAllerta='+idAllerta+'&motivazione='+motivazione;
	}
}

</script>


<div id="dialogChiudiLista">
<h3>Chiusura <label id="nomeChiusura"></label> <input type="text" disabled id="indiceLista" name="indiceLista" size="3"/> per ASL <input type="text" disabled id="nomeAsl" name="nomeAsl"/> </h3>
<input readonly type="text" id="dataChiusuraLista" name="dataChiusuraLista" size="10" />
<a href="#" onClick="cal19.select(document.getElementById('dataChiusuraLista'),'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>
<input type="hidden" id="idAsl" name="idAsl"/> <input type="hidden" id="idLista" name="idLista"/> <input type="hidden" id="idAllerta" name="idAllerta" value="<%=TicketDetails.getId()%>"/> 
<input type="button" onClick="checkFormChiudiLista()" value="CONFERMA"/>
</div>

<div id="dialogRipianificaCU">
<h3>Ripianificazione CU <br/>
<label id="nomeRipianificazione"></label> <input type="text" disabled id="indiceListaRipianifica" name="indiceListaRipianifica" size="3"/> per ASL <input type="text" disabled id="nomeAslRipianifica" name="nomeAslRipianifica"/> </h3>
<input type="hidden" id="idAslRipianifica" name="idAslRipianifica"/> <input type="hidden" id="idListaRipianifica" name="idListaRipianifica"/> <input type="hidden" id="idAllertaRipianifica" name="idAllertaRipianifica" value="<%=TicketDetails.getId()%>"/>
Num. CU Pianificati: <input type="text" disabled id="oldPianificati" name="oldPianificati" value="" size="3"/><br/>
Nuovo num. CU Pianificati: <input type="text" id="newPianificati" name="newPianificati" value="" size="3" maxlength="3"/><br/>
Motivazione: <textarea id="motivazione" name="motivazione" rows="3" cols="30" maxlength="100" onkeyup="this.value=this.value.replace(/[^a-zA-Z.,;'''\d ]/g, '')"></textarea><br/>
 <input type="button" onClick="checkFormRipianificaCU()" value="CONFERMA"/>
</div>

<br>



<%String cu_pp="";
	    		
				AslCoinvolte	ac	= lista.getAslCoinvolta( User.getSiteId() );
				
				int cuEseguiti = 0;
				int cupianificati = 0;
				if (ac !=null )
				{
					cuEseguiti = ac.getCu_eseguiti();
					cupianificati = ac.getCu_pianificati();
				}
				%>
				
				 	<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr><th colspan="8">
	<% if (ldd==-1) { %> INFO <% } else { %>
	
	SCHEDA LISTA DI DISTRIBUZIONE <%=ldd %> 
	
	<% if (lista.getData_chiusura()==null && !request.getQueryString().toString().contains("PrepareRipianifica")) { %>
		<dhv:permission name="allerte-allerte-chiudi-lista-edit">
	<input type="button" value="Chiusura Lista per Tutte le Asl" onclick="openchiusuraLista(<%=lista.getId() %>)"/>
	</dhv:permission>
	</th></tr>
	<%} else { %>
	CHIUSA <% } %>
	<% } %>
	<%
		  	%>
    			<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					A.S.L.
	    				</td>
	    							<%
	    							for( int i = 1; i < size; i++)
	    							{
	    								if(SiteIdList.getIdFromLevel(i)!=-1){
	    								String asl = SiteIdList.getSelectedValue( SiteIdList.getIdFromLevel(i));
	    							%>
	    							
	    							<% if (SiteIdList.getIdFromLevel(i) ==  User.getSiteId() ||User.getSiteId()==-1 ){ %>
	    								<td>
	    									<%=toHtmlValue( asl ) %>
	    								</td>	
	    								<%} %>
	    							<%
	    							}}
	    							
	    							%>
	    			</tr>
	    			
	    <tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					C.U. Pianificati dalla Regione
	    				</td>
	    				
	    							<%
	    							for( int i = 1; i < size; i++)
	    							{
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								if(id_asl!=-1){
	    								AslCoinvolte temp = lista.getAslCoinvolta( id_asl );
	    							String	 cu_p = ( (temp == null) ? ( TicketDetails.ASL_NON_COINVOLTA ) : ( (temp.getControlliUfficialiRegionaliPianificati() > -1) ? (temp.getControlliUfficialiRegionaliPianificati()) : (TicketDetails.CU_NON_PIANIFICATI) ) ) + "";
	    							cu_pp=cu_p;
	    							%>
	    								<% if (SiteIdList.getIdFromLevel(i) ==  User.getSiteId() ||User.getSiteId()==-1 ){ %>
	    								<td>
	    									<%=toHtmlValue( cu_p ) %>
	    								</td>
	    								<% } %>	
	    							<%
	    							}}
	    							%>
					</tr>
			
	<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					C.U. Pianificati da Asl
	    				</td>
	    				
	    							<%
	    							for( int i = 1; i < size; i++)
	    							{
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								if(id_asl!=-1){
	    								AslCoinvolte temp = lista.getAslCoinvolta( id_asl );
	    								String cu_p = ( (temp == null) ? ( TicketDetails.ASL_NON_COINVOLTA ) : ( (temp.getCu_pianificati() > -1) ? (temp.getCu_pianificati()) : (TicketDetails.CU_NON_PIANIFICATI) ) ) + "";
	    							%>
	    								<% if (SiteIdList.getIdFromLevel(i) ==  User.getSiteId() ||User.getSiteId()==-1 ){ %>
	    								<td>
	    									<%=toHtmlValue( cu_p ) %>
	    								</td>	
	    								<%} %>
	    							<%
	    							}}
	    							%>
					</tr>			
	
	<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					C.U. Eseguiti (Chiusi)
	    				</td>
	    				
	    							<%
	    							int col1=0;
	    							for( int i = 1; i < size; i++)
	    							{
	    								col1++;
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								if(id_asl!=-1){
	    								AslCoinvolte temp = lista.getAslCoinvolta( id_asl );
	    								String cu_e = ( (temp == null) ? ( TicketDetails.ASL_NON_COINVOLTA ) : ( temp.getCu_eseguiti()) ) + "";
	    							%>
	    							
	    								<% if (SiteIdList.getIdFromLevel(i) ==  User.getSiteId() ||User.getSiteId()==-1 ){ %>
	    								<td>
	    									<%=toHtmlValue( cu_e ) %>
	    								</td>	
	    								<%} %>
	    							<%
	    							}}
	    							%>
 					</tr>
 					
	<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					C.U. Residui da Eseguire
	    				</td>
	    				
	    							<%
	    							int col=0;
	    							for( int i = 1; i < size; i++)
	    							{
	    								col++;
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								if(id_asl!=-1){
	    								AslCoinvolte temp = lista.getAslCoinvolta( id_asl );
	    								String cu_e = ( (temp == null) ? ( TicketDetails.ASL_NON_COINVOLTA ) : ( temp.getCUResidui() ) ) + "";
	    							%>
	    								<% if (SiteIdList.getIdFromLevel(i) ==  User.getSiteId() ||User.getSiteId()==-1 ){ %>
	    								<td>
	    									<%=toHtmlValue( cu_e ) %>
	    								</td>	
	    								<%} %>
	    							<%
	    							}}
	    							%>
 					</tr>
 					
 					<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					C.U. Eseguiti (Aperti)
	    				</td>
	    				
	    							<%
	    							col=0;
	    							for( int i = 1; i < size; i++)
	    							{
	    								col++;
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								if(id_asl!=-1){
	    								AslCoinvolte temp = lista.getAslCoinvolta( id_asl );
	    								String cu_e = ( (temp == null) ? ( TicketDetails.ASL_NON_COINVOLTA ) : ( temp.getNumCuEseguiti_aperti() ) ) + "";
	    							%>
	    								<% if (SiteIdList.getIdFromLevel(i) ==  User.getSiteId() ||User.getSiteId()==-1 ){ %>
	    								<td>
	    									<%=toHtmlValue( cu_e ) %>
	    								</td>	
	    								<%} %>
	    							<%
	    							}}
	    							%>
 					</tr>
 					
					
					<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					Imprese e stabilimenti coinvolti
	    				</td>
	    				
	    				
	    							<%
	    							for( int i = 1; i < size; i++)
	    							{
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								if(id_asl!=-1){
	    								ImpreseCoinvolte temp = lista.getImpresaCoinvolta( id_asl );
	    								
	    								if (SiteIdList.getIdFromLevel(i) ==  User.getSiteId() ||User.getSiteId()==-1 ){ 
	    								
	    								if(id_asl==16)
	    								{
	    								%>
	    								
	    									<% if (SiteIdList.getIdFromLevel(i) ==  User.getSiteId() ||User.getSiteId()==-1 ){ %>
	    								<td>
	    								<%
	    									out.println(TicketDetails.ASL_NON_COINVOLTA);
	    									
	    									
	    									%>
	    								</td>
	    								<%} %>
	    								
	    								<%
	    								}
	    								else{
	    								if(temp==null)
	    									{
	    									%><td>
	    									<%
	    									out.println(TicketDetails.ASL_NON_COINVOLTA);
	    									
	    									
	    									%>
	    									</td>
	    								<%} 
	    								else{
	    								%>
	    								
	    								<td>
	    							<%
	    							
	    							int ii =0;
	    							for(String s : temp.getImpreseCoinvolte())
	    							{
	    								String indirizzo = temp.getIndirizziImpreseCoinvolte().get(ii);
	    								if(!s.equals(""))
	    								out.println("<b>Ragione Sociale :</b>"+s+" . <b>Indirizzo :</b> "+indirizzo+"<br>");
	    								ii++;
	    							}
	    							
	    							%>
	    						
	    									
	    								</td>	
	    							<%
	    							}}}}}
	    							%>
					</tr>
					
					<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					Note su Variazione dei C.U. Pianificati da Asl
	    				</td>
	    				
	    							<%
	    							
	    							for( int i = 1; i < size; i++)
	    							{
	    								int id_asl = SiteIdList.getIdFromLevel(i);
	    								AslCoinvolte temp = lista.getAslCoinvolta( id_asl );
	    								String motivazione="";
	    								if(temp==null || temp.equals(""))
	    									motivazione="";
	    									else
	    									{
	    										
	    									
	    										motivazione=temp.getMotivazione();
	    										
	    									}
	    										%>
	    									<% if (SiteIdList.getIdFromLevel(i) ==  User.getSiteId() ||User.getSiteId()==-1 ){ %>
	    								<td>
	    									<%=toHtml(motivazione)%>
	    								</td>	
	    								<%} %>
	    							<%
	    							}
	    							%>
					</tr>
					
					
					<tr class="containerBody">
	    			<td  valign="top" class="formLabel">
	    					Descrizione per Asl Fuori Regione
	    				</td>
	    				
	    							<%
	    							
	    							for( int i = 1; i < size; i++)
	    							{
	    								
	    								int id_asl = SiteIdList.getIdFromLevel(i);
	    								String descrizione="";
	    								if(id_asl == 16){
	    								AslCoinvolte temp = lista.getAslCoinvolta( id_asl );
	    								
	    								if(temp==null || temp.equals(""))
	    									descrizione="";
	    									else
	    									{
	    										
	    									descrizione = temp.getNoteFuoriRegione();
	    										
	    										
	    									}}
	    										%>
	    										
	    								<% if (SiteIdList.getIdFromLevel(i) ==  User.getSiteId() ||User.getSiteId()==-1 ){ %>			
	    								<td>
	    									<%=toHtml(descrizione)%>
	    								</td>	
	    								<%} %>
	    							<%
	    							}
	    							%>
					</tr>
					
					
 					
 					
 					<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					Stato Allerta
	    				</td>
	    				
	    							<%
	    							for( int i = 1; i < size; i++)
	    							{
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    							%>
	    								<% if (SiteIdList.getIdFromLevel(i) ==  User.getSiteId() ||User.getSiteId()==-1 ){ %>
	    								<td>
	    									<%=( lista.getStato( id_asl ) ) %> 
	    									<dhv:permission name="allerte-allerte-chiudi-lista-add">
	    									<% if (lista.getStato(id_asl).contains("Controlli Completati") && id_asl ==  User.getSiteId() ) {%>
	    									<input type="button" value ="CHIUDI LISTA PER ASL" onclick="gestoreChiudiLista('<%=id_asl%>', '<%= SiteIdList.getSelectedValue(id_asl)%>', '<%=ldd%>', '<%=lista.getId()%>');"/>
	    									<%} %>
	    									</dhv:permission>	
	    									
	    									<dhv:permission name="allerte-allerte-ripianifica-lista-add">
	    									<% if (lista.getStato(id_asl).contains("Controlli in Corso") && id_asl ==  User.getSiteId() ) {%>
	    									<input type="button" value ="MODIFICA CU" onclick="gestoreRipianificaCU('<%=id_asl%>', '<%= SiteIdList.getSelectedValue(id_asl)%>', '<%=ldd%>', '<%=lista.getId()%>', '<%=ac.getCu_pianificati()%>' );"/>
	    									<%} %>
	    									</dhv:permission>	
	    									
	    								</td>	
	    								<%} %>
	    							<%
	    							}
	    							%>
	    			
	    			
	    			<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					Data Chiusura C.U.
	    				</td>
	    				
	    							<%
	    							for( int i = 1; i < size; i++)
	    							{
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								AslCoinvolte ac1 = lista.getAslCoinvolta( id_asl );
	    							%>
	    								<% if (SiteIdList.getIdFromLevel(i) ==  User.getSiteId() ||User.getSiteId()==-1 ){ %>
	    								<td >
	    								<%if (ac1!=null)
	    									{
	    									if(ac1.isStato_allegatof()==true )
	    									{
	    										if(ac1.getData_invio_allegato()!=null)
	    											out.print(""+(new SimpleDateFormat("dd/MM/yyyy").format(new Date (ac1.getData_invio_allegato().getTime()))));
	    									}
	    									else
	    									{
	    										
	    										if(ac1.getData_chiusura()!=null)
	    										{
	    											out.print(""+(new SimpleDateFormat("dd/MM/yyyy").format(new Date (ac1.getData_chiusura().getTime()))));
	    										}
	    										else
	    										{
	    											out.print(" ");
	    										}
	    										
	    									}
	    								%>
	    								
	    									
	    									
	    								<%}else
	    									{
	    									out.print(Ticket.ASL_NON_COINVOLTA);
	    									}%>
					    
										</td>
										<%} %> 
								<%} %>
				</tr>	
	
			
	    			
	    			<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					Motivazione Mancato completamento controlli
	    				</td>
	    				
	    							<%
	    							
	    							for( int i = 1; i < size; i++)
	    							{
	    								col++;
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								if(id_asl!=-1){
	    								AslCoinvolte temp = lista.getAslCoinvolta( id_asl );
	    								String cu_e = ( (temp == null) ? ( TicketDetails.ASL_NON_COINVOLTA ) : ( temp.getMotivo_chiusura() ) ) + "";
	    							%>
	    								<% if (SiteIdList.getIdFromLevel(i) ==  User.getSiteId() ||User.getSiteId()==-1 ){ %>
	    								<td>
	    									<%=toHtml( cu_e ) %>
	    								</td>	
	    								<%} %>
	    							<%
	    							}}
	    							%>
 					</tr>
 					
	</table>
		<br/>	