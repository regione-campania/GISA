<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.macellazioni.base.Campione"%>
<script type="text/javascript">
/* VECCHIA VERSIONE: nascondeva i DIV. Non va bene perchè i parametri partono comunque più volte
function switchMolecole( option, index )
{
	var i = option.value;
	document.getElementById( 'cmp_molecole_pnr_' + index ).style.display = ( i == 1) ? ('block') : ('none');
	document.getElementById( 'cmp_molecole_batteriologico_' + index ).style.display = ( i == 2 ) ? ('block') : ('none');
	document.getElementById( 'cmp_molecole_parassitologico_' + index ).style.display = ( i == 3 ) ? ('block') : ('none');
	document.getElementById( 'cmp_molecole_altro_' + index ).style.display = ( i == 4 ) ? ('block') : ('none');
	document.getElementById( 'cmp_molecole_listavuota_' + index ).style.display = ( i == -1 ) ? ('block') : ('none');
};
*/

//Nuova versione: cambia l'innerHTML di un div con la giusta select
function switchMolecole( option, index )
{
	i = ( option.value < 0 ) ? ( 0 ) : ( option.value );
	var selects = new Array(5);
	
	var hidden_molecola = '<input type="hidden" name="cmp_molecole_' + index + '" value="-1" />';
	var hidden_nota = '<input type="hidden" name="cmp_note_' + index + '" value="" />';
	
	selects[0] = '<select name="cmp_molecole_' + index + '"><option value="-1">-- Seleziona --</option></select>' + hidden_nota;
	selects[1] = "<%=MolecolePNR.getHtmlSelect("placeHolder", "-1") %>" + hidden_nota;
	selects[2] = "<%=MolecoleBatteriologico.getHtmlSelect("placeHolder", "-1") %>" + hidden_nota;
	selects[3] = "<%=MolecoleParassitologico.getHtmlSelect("placeHolder", "-1") %>" + hidden_nota;
	selects[4] = '<textarea rows="2" cols="40" name="cmp_note_' + index + '"></textarea>' + hidden_molecola;
	selects[5] = '<textarea rows="2" cols="40" name="cmp_note_' + index + '"></textarea>' + hidden_molecola;

	document.getElementById( 'cmp_molecole_div_' + index ).innerHTML = selects[i].replace( /placeHolder/g, 'cmp_molecole_' + index );
};
</script>
<table class="details" width="100%" border="0" cellpadding="5" cellspacing="0">
    			<tr>
    				<th colspan="6">Campioni</th>
    			</tr>
    			<tr>
    				<th>Motivo</th> <th>Matrice</th> <th>Tipo Analisi</th> <th>Molecole/Agente Etiologico</th>  <th>Esito</th> <th>Data Ricezione Esito</th>
    			</tr>
    			
    	<%	for( int index = 1; index <= 5; index++ )
    		{ 
    			Campione campione = (Campioni.size() >= index) ? ((Campione)Campioni.get( index -1 )) : (new Campione());
    	%>
    			
    			<tr class="containerBody">
    				<td>
    						<%=MotiviCampioni.getHtmlSelect( "cmp_id_motivo_" + index, campione.getId_motivo() )%>
                	</td>
                	
    				<td>
    					<input type="hidden" name="cmp_identifier_<%=index %>" value="<%=campione.getId() %>" />
    					<%=Matrici.getHtmlSelect( "cmp_matrice_" + index, campione.getMatrice() ) %>
    				</td>
          
    				<td>
    					<%TipiAnalisi.setJsEvent( "onchange=\"switchMolecole( this, " + index + ")\"" ); %>
    					<%=TipiAnalisi.getHtmlSelect( "cmp_tipo_" + index, campione.getId_tipo_analisi() ) %>
    				</td>
    				<td >
	    				<div id="cmp_molecole_div_<%=index %>" style="display: block;" >
	    				
	    				<%
	    					switch ( campione.getId_tipo_analisi() )
	    					{
	    					case 1:
	    				%>
	    					<%=MolecolePNR.getHtmlSelect( "cmp_molecole_" + index, campione.getId_molecole() ) %>
	    					<input style="width: 50p" type="hidden" name="cmp_note_<%=index %>" value="<%=toHtmlValue( campione.getNote() ) %>" />
	    				<%
	    						break;
	    					case 2:
	    				%>
	    					<%=MolecoleBatteriologico.getHtmlSelect( "cmp_molecole_" + index, campione.getId_molecole() ) %>
	    					<input "width: 50p" type="hidden" name="cmp_note_<%=index %>" value="<%=toHtmlValue( campione.getNote() ) %>" />
	    				<%
	    						break;
	    					case 3:
	    				%>
	    					<%=MolecoleParassitologico.getHtmlSelect( "cmp_molecole_" + index, campione.getId_molecole() ) %>
	    					<input type="hidden" name="cmp_note_<%=index %>" value="<%=toHtmlValue( campione.getNote() ) %>" />
	    				<%
	    						break;
	    					case 4:
	    					case 5:
	    				%>
	    					<textarea rows="2" cols="40" name="cmp_note_<%=index %>"><%=toHtmlValue( campione.getNote() ) %></textarea>
	    					<input type="hidden" name="cmp_molecole_<%=index %>" value="-1" />
	    				<%
	    						break;
	    					default:
	    				%>
	    					<select name="cmp_molecole_<%=index %>">
	    						<option value="-1">-- Seleziona --</option>
	    					</select>
	    					<input type="hidden" name="cmp_note_<%=index %>" value="" />
	    				<%
	    						break;
	    					}
	    				%>
	    					
	    					
	    				</div>
    				</td>
    				
                	<td>
                			<%=EsitiCampioni.getHtmlSelect( "cmp_id_esito_" + index, campione.getId_esito() )%>
    				</td>
    				
    				<td>
<%--    					<%
    						switch (index) {
    							case 1 : %> <zeroio:dateSelect field="cmp_data_ricezione_esito_1" form="main" showTimeZone="false" timestamp="<%=campione.getData_ricezione_esito() %>" />  <% break;
    							case 2 : %> <zeroio:dateSelect field="cmp_data_ricezione_esito_2" form="main" showTimeZone="false" timestamp="<%=campione.getData_ricezione_esito() %>" />  <% break;
    							case 3 : %> <zeroio:dateSelect field="cmp_data_ricezione_esito_3" form="main" showTimeZone="false" timestamp="<%=campione.getData_ricezione_esito() %>" />  <% break;
    							case 4 : %> <zeroio:dateSelect field="cmp_data_ricezione_esito_4" form="main" showTimeZone="false" timestamp="<%=campione.getData_ricezione_esito() %>" />  <% break;
    							case 5 : %> <zeroio:dateSelect field="cmp_data_ricezione_esito_5" form="main" showTimeZone="false" timestamp="<%=campione.getData_ricezione_esito() %>" />  <% break;
    						}
    					%>
 --%>
 						<%
	 						switch (index) {
		 	    				case 1 : %> <input readonly type="text" name="cmp_data_ricezione_esito_1" size="10" value="<%=DateUtils.timestamp2string(campione.getData_ricezione_esito())%>" />&nbsp;  
		 									<a href="#" onClick="cal19.select(document.forms[0].cmp_data_ricezione_esito_1,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		 									<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>  
		 									<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].cmp_data_ricezione_esito_1);"><img src="images/delete.gif" align="absmiddle"/></a>
		 									<% break;
		 	    				case 2 : %> <input readonly type="text" name="cmp_data_ricezione_esito_2" size="10" value="<%=DateUtils.timestamp2string(campione.getData_ricezione_esito())%>" />&nbsp;  
		 									<a href="#" onClick="cal19.select(document.forms[0].cmp_data_ricezione_esito_2,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		 									<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>  
		 									<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].cmp_data_ricezione_esito_2);"><img src="images/delete.gif" align="absmiddle"/></a>
		 									<% break;
		 	    				case 3 : %> <input readonly type="text" name="cmp_data_ricezione_esito_3" size="10" value="<%=DateUtils.timestamp2string(campione.getData_ricezione_esito())%>" />&nbsp;  
		 									<a href="#" onClick="cal19.select(document.forms[0].cmp_data_ricezione_esito_3,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		 									<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>  
		 									<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].cmp_data_ricezione_esito_3);"><img src="images/delete.gif" align="absmiddle"/></a>
		 									<% break;
		 	    				case 4 : %> <input readonly type="text" name="cmp_data_ricezione_esito_4" size="10" value="<%=DateUtils.timestamp2string(campione.getData_ricezione_esito())%>" />&nbsp;  
		 									<a href="#" onClick="cal19.select(document.forms[0].cmp_data_ricezione_esito_4,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		 									<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>  
		 									<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].cmp_data_ricezione_esito_4);"><img src="images/delete.gif" align="absmiddle"/></a>
		 									<% break;
		 	    				case 5 : %> <input readonly type="text" name="cmp_data_ricezione_esito_5" size="10" value="<%=DateUtils.timestamp2string(campione.getData_ricezione_esito())%>" />&nbsp;  
		 									<a href="#" onClick="cal19.select(document.forms[0].cmp_data_ricezione_esito_5,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		 									<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>  
		 									<a href="#" style="cursor: pointer;" onclick="svuotaData(document.forms[0].cmp_data_ricezione_esito_5);"><img src="images/delete.gif" align="absmiddle"/></a>
		 									<% break;
	 						}
 						%>
 						
 	                	
                	</td>
    				
    				
    			</tr>
    			
    		<%} %>
    			
</table>
