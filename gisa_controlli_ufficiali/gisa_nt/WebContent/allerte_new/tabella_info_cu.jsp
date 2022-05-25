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
	       	title:" <input type=\"button\" onClick=\"closeDialog()\" style=\"background:transparent !important; color: black !important \" value=\"[X]\" /> CHIUDI LISTA DI DISTRIBUZIONE",
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
	if (data=='')
		alert ('Data chiusura obbligatoria');
	else{
		loadModalWindow();
		window.location.href='TroubleTicketsAllerteNew.do?command=ChiusuraListaAsl&dataChiusuraLista='+data+'&idAsl='+idAsl+'&idLista='+idLista;
	}
}
</script>


<div id="dialogChiudiLista">
<h3>Chiusura lista di distribuzione <input type="text" disabled id="indiceLista" name="indiceLista" size="3"/> per ASL <input type="text" disabled id="nomeAsl" name="nomeAsl"/> </h3>
<input readonly type="text" id="dataChiusuraLista" name="dataChiusuraLista" size="10" />
<a href="#" onClick="cal19.select(document.getElementById('dataChiusuraLista'),'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"/></a>
<input type="hidden" id="idAsl" name="idAsl"/> <input type="hidden" id="idLista" name="idLista"/>
<input type="button" onClick="checkFormChiudiLista()" value="CONFERMA"/>
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
		    	if( User.getSiteId() <= 0 )
		    	{
		    		
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
	    							
	    								<td>
	    								
	    									<%=toHtmlValue( asl ) %>
	    								</td>	
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
	    								<td>
	    						
	    									<%=toHtmlValue( cu_p ) %>
	    								</td>	
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
	    								<td>
	    									<%=toHtmlValue( cu_p ) %>
	    								</td>	
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
	    								<td>
	    									<%=toHtmlValue( cu_e ) %>
	    								</td>	
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
	    								<td>
	    									<%=toHtmlValue( cu_e ) %>
	    								</td>	
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
	    								<td>
	    									<%=toHtmlValue( cu_e ) %>
	    								</td>	
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
	    								
	    								if(id_asl==16)
	    								{
	    								%>
	    								<td>
	    								<%
	    									out.println(TicketDetails.ASL_NON_COINVOLTA);
	    									
	    									
	    									%>
	    								</td>
	    								
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
	    							}}}}
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
	    								<td>
	    									<%=toHtml(motivazione)%>
	    								</td>	
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
	    								<td>
	    									<%=toHtml(descrizione)%>
	    								</td>	
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
	    								<td>
	    									<%=( lista.getStato( id_asl ) ) %> 
	    									<dhv:permission name="allerte-allerte-chiudi-lista-add">
	    									<% if (lista.getStato(id_asl).contains("Controlli Completati") /*&& id_asl ==  User.getSiteId()*/) {%>
	    									<input type="button" value ="CHIUDI LISTA PER ASL" onclick="gestoreChiudiLista('<%=id_asl%>', '<%= SiteIdList.getSelectedValue(id_asl)%>', '<%=ldd%>', '<%=lista.getId()%>');"/>
	    									<%} %>
	    									</dhv:permission>	
	    								</td>	
	    							<%
	    							}
	    							%>
	    			
	    			
	    			<tr class="containerBody">
	    				<td  valign="top" class="formLabel">
	    					Data Ricezione Allegato F
	    				</td>
	    				
	    							<%
	    							for( int i = 1; i < size; i++)
	    							{
	    								int id_asl = SiteIdList.getIdFromLevel( i );
	    								AslCoinvolte ac1 = lista.getAslCoinvolta( id_asl );
	    							%>
	    								<td >
	    								<%if (ac1!=null)
	    									{
	    									if(ac1.isStato_allegatof()==true )
	    									{
	    										if(ac1.getData_invio_allegato()!=null)
	    											out.print(""+(new SimpleDateFormat("dd/MM/yyyy:hh:mm").format(new Date (ac1.getData_invio_allegato().getTime()))));
	    									}
	    									else
	    									{
	    										
	    										if(ac1.getData_chiusura()!=null)
	    										{
	    											out.print(""+(new SimpleDateFormat("dd/MM/yyyy:hh:mm").format(new Date (ac1.getData_chiusura().getTime()))));
	    										}
	    										else
	    										{
	    											out.print("Non Ricevuto");
	    										}
	    										
	    									}
	    								%>
	    								
	    									
	    									
	    								<%}else
	    									{
	    									out.print(Ticket.ASL_NON_COINVOLTA);
	    									}%>
					    
										</td> 
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
	    								<td>
	    									<%=toHtml( cu_e ) %>
	    								</td>	
	    							<%
	    							}}
	    							%>
 					</tr>
	    			
	    	<%
		    	}
		    	else
		    	{
	    	%>
	    	  <tr class="containerBody">
		    <td valign="top" class="formLabel">
				C.U. Pianificati dalla Regione
		    </td>
		    <td colspan="<%=size %>">
	    	<%=ac.getControlliUfficialiRegionaliPianificati() %>
	    	<input type="hidden" id="cuRegione" name="cuRegione" value="<%=ac.getControlliUfficialiRegionaliPianificati() %>">
	    	</td>
	    	
	    	</tr>
	    	
	    	
		  <tr class="containerBody">
		    <td valign="top" class="formLabel">
				C.U. Pianificati <%=SiteIdList.getValueFromId( User.getSiteId() ) %>
		    </td>
		    <td colspan="<%=size %>">
			    		
			    		
			    		<%
			    			String cup = ( (ac == null) ? ( TicketDetails.ASL_NON_COINVOLTA ) : ( (ac.getCu_pianificati() > -1) ? (ac.getCu_pianificati()) : (TicketDetails.CU_NON_PIANIFICATI) ) ) + "";
			    		
			    		
			    		%>
					
					<dhv:permission name="allerte-allerte-cu-view">
						<%
							if( (ac != null) && (ac.getCu_pianificati() < 0) )
							{
								cup = "";
			    		%>
			    		<table class="noborder">
			    		<tr>
			    		<td>
			    		
			    			<input size="5" maxlength="4" type="text" onchange="mostraMotivo()" id="cu_pianificati" name="cu"  />
			    			<input type="hidden" name="id_allerta"  value="<%=TicketDetails.getId() %>" />
			    		</td>
			    		<td id="descr" style="display:none">
			    	
			    		
			    		
			    		<textarea rows="5" name="motivazione" cols="30" id="motivazione1"></textarea>
			    	</td>
			    		<td>	
			    		<% if(lista.getAslCoinvolta(User.getSiteId() ).getData_chiusura()==null){%>
			    		<input type="button"
								value="Assegna"
								onClick="javascript:if(checkInt(this.form)){ this.form.action='TroubleTicketsAllerte.do?command=AssegnaNumeroCU'; submit(); }">
							<%} %>
	</td></tr></table>
			    		<%
							}else{
								%>
								<table class="noborder">
			    		<tr>
			    		<td>
								<input size="5" maxlength="4" id="cu_pianificati" onchange="mostraMotivo()" type="text" value="<%=cup %>" name="cu" />
				    			<input type="hidden" name="id_allerta" value="<%=TicketDetails.getId() %>" />
				    			</td>
			    		<td id="descr" style="display:none">
				    			&nbsp;&nbsp;
				    		<textarea rows="5" name="motivazione" value="<%=ac.getMotivazione() %>" cols="30"><%=ac.getMotivazione() %></textarea>
			    		</td>
			    		<td>
				    				<% if(lista.getAslCoinvolta(User.getSiteId() ).getData_chiusura()==null){%>
				    			<input type="button"
									value="Modifica"
									onClick="javascript:if(checkInt(this.form)){ this.form.action='TroubleTicketsAllerte.do?command=AssegnaNumeroCU'; submit(); }">
							<%} %>
		</td></tr></table>
								<% 
								
								
								
								
							}
			    		%>
					</dhv:permission>
			    		
			    	
			    </td> 
		    </tr>
 
			<%if( (ac != null) && (ac.getCu_pianificati() > -1) ){%>
				<tr class="containerBody">
					<td valign="top" class="formLabel">
						C.U. Residui da Eseguire <%=SiteIdList.getValueFromId( User.getSiteId() ) %>
					</td>
					<td colspan="<%=size %>">
					     <%= toHtmlValue( ac.getCUResidui() ) %>
					</td> 
				</tr>
			<% }%>
			
			<%if( (ac != null) ){%>
				<tr class="containerBody">
					<td valign="top" class="formLabel">
						C.U. Eseguiti (Aperti) <%=SiteIdList.getValueFromId( User.getSiteId() ) %>
					</td>
					<td colspan="<%=size %>">
					     <%= toHtmlValue(""+ ac.getNumCuEseguiti_aperti() ) %>
					</td> 
				</tr>
			<% }%>
	
			<tr class="containerBody">
				<td valign="top" class="formLabel">
					Stato Allerta <%=SiteIdList.getValueFromId( User.getSiteId() ) %>
				</td>
				<td colspan="<%=size %>">
				     <%= ( lista.getStato( User.getSiteId() ) ) %>
				</td> 
			</tr>
			
			<%if(ac.getMotivo_chiusura()!=null  && ! ac.getMotivo_chiusura().equals("null")&& ! ac.getMotivo_chiusura().equals("")){ %>
				<tr class="containerBody">
					<td valign="top" class="formLabel">
						Motivo Mancato Completamento Controlli
					</td>
					<td colspan="<%=size %>">
					     <%= toHtmlValue( ac.getMotivo_chiusura()) %>
					</td> 
				</tr>
				
				<%} %>
			
				<tr class="containerBody">
					<td valign="top" class="formLabel">
						Stato Allegato F
					</td>
					<td colspan="<%=size %>">
					    <%if(ac.isStato_allegatof()== true  ){ 
					   		 if (ac.getData_invio_allegato()!=null)
					   		 {
					    %>
					    Inviato in Data : <%=new SimpleDateFormat("dd/MM/yyyy:hh:mm").format(new Date (ac.getData_invio_allegato().getTime())) %>
					    <%
					   	}
					    }
					    else
					    {
					    	if (ac.getData_chiusura()!=null)
					   		 {
					    		%>
					     Inviato in Data : <%=new SimpleDateFormat("dd/MM/yyyy:hh:mm").format(new Date (ac.getData_chiusura().getTime())) %>		
					    		<%
					   		 }
					    	else
					    	{
					    %>
					    Non Inviato
					    <%}} %>
					</td> 
				</tr>	
	
			<%
				
		    	}
			%>
			
				
	
	
	</table>
		<br/>	