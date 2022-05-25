<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<h2>Raccomandazione Significativa</h2>
<table>
  <tr  id = "show_significative">
    <td>
     <input type = "hidden" id = "elementi_nc_significative" name = "elementi_nc_significative" value = "1">
   		<input type = "hidden" id = "size_nc_significative" name = "size_nc_significative" value = "1">
      <table border="0" cellspacing="0" cellpadding="10" class="empty"  >
        <tr>
        <td>
        <table>
<!--        <tr><td colspan="3"><input type = "button" value = "Inserisci un'altra Raccomandazione Significativa" onclick="return clonaNC_significative()"><input type = "button" value = "Elimina Raccomandazione Significativa"  onclick="removeSignificative('false');"><input type = "button" value = "Reset Raccomandazione Significativa" onclick="resetSignificative('<%=CU.getId() %>',2,true)"></td></tr>-->
        <tr id = "nc_significative_1">
        
        <td>
         
      <%
         		
        	if ( CU.getOggettoAudit().size()!=0)
        	{
        		
        		Iterator<Integer> ite = CU.getOggettoAudit().keySet().iterator();
        		%>
        		<select multiple="multiple" name = "Provvedimenti2_1"  id = "Provvedimenti2_1"  size = "6" onchange="calcolaPuntiSignificative(document.getElementById('elementi_nc_significative').value,7);">	
        		<option value = "-1" selected="selected">--  SELEZIONA UN OGGETTO DELL'AUDIT --</option>
        		<%
        		while(ite.hasNext())
        		{
        			int code = ite.next();
        			
        				
        				
        			%>
        			<option value ="<%=code %>"><%=CU.getOggettoAudit().get(code) %></option>
        			<%
            		
        		}
        		%>
        		</select>
        		
        		<%        	
        		Osservazioni.setSelectSize(6);
        		Osservazioni.setMultiple(true);
        		%>
         		<%= Osservazioni.getHtmlSelect("Osservazioni_Significative",-1) %>
        		<%        		
        	}
        	else
        	{
        	%>
        	
<!--         	<input type = "hidden" name = "Provvedimenti2_1" id = "Provvedimenti2_1" value = "-1"> -->
        	Selezionare Oggetto dell'audit dal controllo
        	
        	<%
        	
        	Osservazioni.setSelectSize(8);
        	Osservazioni.setMultiple(true);
        	Osservazioni.setJsEvent("onchange=calcolaPuntiSignificative(document.getElementById('elementi_nc_significative').value,7);"); %>
         <%=Osservazioni.getHtmlSelect("Provvedimenti2_1",TicketDetails.getProvvedimenti()) %>
         <%} %>
         <input type = "hidden" name = "Provvedimenti2_1_selezionato" id = "Provvedimenti2_1_selezionato" value = "-1">
        </td>
        
         
          </tr>
        
        
      </table>
      </td></tr>
      <tr>
            <td><textarea name = "note_significative" rows="5" cols="60"></textarea> </td>
         <td>
           <center> Punteggio </center><br>
             <input type="text" value="" name="puntiSignificativi" id="puntiSignificativi" readonly="readonly" onchange="calcolaTotale()">
             
          </td>
          
       
             <td>&nbsp;&nbsp;&nbsp;</td>
        </tr>
      
      </table>
      
    </td>
  </tr>
   <tr>
  <td>
     
  </td>
  </tr>
  
  </table>
    <input type = "hidden" name = "stato_significative" id = "stato_significative" value = "true">
  
    <input type = "hidden" name = "descrizione_or_combo_sel" id = "descrizione_or_combo_sel_significative" value = "false">