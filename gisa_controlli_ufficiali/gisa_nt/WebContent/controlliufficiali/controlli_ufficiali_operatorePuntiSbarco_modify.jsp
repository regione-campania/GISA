<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>

<div align="left">
	<img src="images/tree0.gif" border="0" align="absmiddle" height="16" width="16" />
	<a href="javascript:popLookupSelectorCustomPuntiSbarco('17,4','<%=OrgDetails.getSiteId()%>');"><dhv:label name="">Seleziona Operatore</dhv:label></a>
</div>
<body onload="javascript:document.getElementById('dim').value="<%=OrgDetails.getOpControllatiList().size()%>">

</body>
<!-- Modificare--- -->
<input type ="hidden" name = "dim" id = "dim" value = "<%=OrgDetails.getOpControllatiList().size() %>">

<table cellpadding="6" cellspacing="0" width="70%" class="details" id="tblClone_ps">
		<th colspan="6" style="background-color: rgb(204, 255, 153);" >
			<strong>
				<dhv:label name=""><center>Lista Operatori Controllati</center></dhv:label>
		    </strong>
		</th>
	    <tr>
		   <th><b><dhv:label name="">Tipologia<br>Operatore </dhv:label></b></th>
   		   <th><b><dhv:label name="">Nome<br>Impresa/OSA</dhv:label></b></th>
   		   <th><b><dhv:label name="">Numero<br>Registrazione</dhv:label></b></th>
   		   <th>Numero<br>identificazione<br>barca</th>
		   <th>E' attrezzata<br>per conservare<br>prodotti<br>per 24 ore</th>
     
   		   <th><b><dhv:label name="">Azione</dhv:label></b></th>  	   
       </tr>
       
       <tr style="display: none">
	 	<td><p></p>
	 		<input type="hidden" name="org_id_op" id="org_id_op" value="" />	
	 	</td>
	 	
	 	<td><p></p>
	 	</td>
	 	
	 	<td><p></p>
	 	</td>
	 	
	 	<td><p></p>
	 	</td>
	 	
	 	<td><p>
	 		<input type="radio" id="conservazione" name="conservazione" value="Si" /> Si<br/>
	 		<input type="radio" id="conservazione" name="conservazione" value="No" checked="true" /> No<br/>
	 	</p>
	 	</td>
	 	
	 	<td>
	 	<p><a href="javascript:eliminaOperatore()" id="elimina">Elimina</a></p>
	 	</td>
	 	
	 </tr>
       
   <%
   			
   int cont_ps=0;
   Iterator op_ps = OrgDetails.getOpControllatiList().iterator();
   if (op_ps.hasNext()) {
     while (op_ps.hasNext()) {
    	 ++cont_ps;
    	 org.aspcfs.modules.punti_di_sbarco.base.Organization thisOpPs = (org.aspcfs.modules.punti_di_sbarco.base.Organization)op_ps.next();
    %> 
   <tr id="riga_ps<%=(cont_ps)%>" >
    <td>
    	<p>
    		<input type="hidden" name= "org_id_op_<%=(cont_ps) %>_<%=(cont_ps) %>" id="org_id_op_<%=(cont_ps) %>" value="<%=thisOpPs.getOrgId()%>" />
    	</p>
    	<% if(thisOpPs.getTipologia() == 1) { %>
       		<%= toHtml("IMPRESA") %>
       <% } else {
    	   
    	    if(thisOpPs.getTipologia() == 17) { %>
    	    
    	   
    	   <%= toHtml("IMBARCAZIONI") %>
    	   <%}else{ %>      
      	<%= toHtml("ABUSIVO") %>
       <% }} %>
    </td>
    <td>
	  <%= thisOpPs.getName() %>
	</td>
	<td>
	<% if(thisOpPs.getAccountNumber() != null && !thisOpPs.getAccountNumber().equals("")) { %>
       		<%= thisOpPs.getAccountNumber() %>
       <% } else { %>
      	<%= toHtml("ND") %>
       <% } %>
	</td>
	<td>
	<% if(thisOpPs.getNomeCorrentista() != null && !thisOpPs.getNomeCorrentista().equals("")) { %>
       		<%= thisOpPs.getNomeCorrentista() %>
       <% } else { %>
      	<%= toHtml("ND") %>
       <% } %>
	</td>
	<td>
	     <input type="radio" id="conservazione_<%=(cont_ps) %>" name="conservazione_<%=(cont_ps) %>_<%=(cont_ps) %>" value="Si" <%if(thisOpPs.getConservato()){ %> checked="checked" <%} %> >Si
	     <input type="radio" id="conservazione_<%=(cont_ps) %>" name="conservazione_<%=(cont_ps) %>_<%=(cont_ps) %>" value="No" <%if(!thisOpPs.getConservato()){ %> checked="checked" <%} %> >No
	</td>
	<td>
	 	<p><a href="javascript:eliminaOperatore('<%=(cont_ps) %>')" id="elimina_<%=(cont_ps) %>">Elimina</a></p>
	</td>
	
   </tr>
   <% } %>
       
  <% } %>
<%--    
  <% else { %>
   <tr class="containerBody">
      <td colspan="4">
        <dhv:label name="">Nessun Elenco Operatori Controllati.</dhv:label>
      </td>
   </tr>
   <%}%> 
  --%>
 </table>	  
	
  