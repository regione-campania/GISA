<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.nonconformita.base.ElementoNonConformita"%>

<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@page import="org.aspcfs.modules.nonconformita.base.ElementoOsservazione"%><h2>Raccomandazione Grave</h2>
  
  <table>
  <tr id = "show_gravi">
    
    <td colspan="3">
     <input type = "hidden" id = "elementi_nc_gravi" name = "elementi_nc_gravi" value = "<%=(TicketDetails.getNon_conformita_gravi().size()) %>">
   	<input type = "hidden" id = "size_nc_gravi" name = "size_nc_gravi"  value = "<%=(TicketDetails.getNon_conformita_gravi().size()) %>">
    <table border="0" cellspacing="0" cellpadding="10" class="empty"  >
        <tr >
        <td>
        <table >
        <%
        String nc_gravi_inserite = "";
       
        LookupList listaOggettoAuditGravi = new LookupList();
        LookupList listaOsservazioniGravi= new LookupList();
        ElementoOsservazione ncGrave = null ;
           if(TicketDetails.getNon_conformita_gravi().size()>0)
           {
        	   ncGrave = TicketDetails.getNon_conformita_gravi().get(0);
           	 
        	   listaOggettoAuditGravi  = ncGrave.getListaOggettiAudit();
        	   listaOsservazioniGravi = ncGrave.getListaOsservazioni();
           	
           }
        %>
<!--          <tr><td><input type = "button" value = "Inserisci un'altra Raccomandazione Grave" onclick="return clonaNC_Gravi()"><input type = "button" value = "Elimina Raccomandazione Grave" onclick="removeGravi('false',<%=TicketDetails.getNon_conformita_gravi().size() %>);"><input type = "button" value = "Reset Raccomandazione Grave" onclick="resetGravi_update('<%=CU.getId() %>',3,<%=TicketDetails.getNon_conformita_gravi().size() %>,'<%=request.getAttribute("attivita_gravi") %>','<%=nc_gravi_inserite %>','')" ></td></tr>-->
         <%
        int numEl = 1 ;
      
        	
        	%>
        
        <tr id = "nc_gravi_<%=numEl%>">
         
      
         <td>
         
         	<%
         		
        	if ( CU.getOggettoAudit().size()!=0)
        	{
        		
        		Iterator<Integer> ite = CU.getOggettoAudit().keySet().iterator();
        		%>
        		<select multiple="multiple" name = "Provvedimenti3_1"  id = "Provvedimenti3_1"  size = "6" onchange="calcolaPuntiGravi(document.getElementById('elementi_nc_gravi').value,25);">	
        		<option value = "-1" >-- SELEZIONA UNA OSSERVAZIONE --</option>
        		<%
        		while(ite.hasNext())
        		{
        			
        			int code = ite.next();
        			
        			
        				
        			%>
        			<option value ="<%=code %>" <%if (listaOggettoAuditGravi.containsKey(code)){%>selected="selected" <%} %>><%=CU.getOggettoAudit().get(code) %></option>
        			<%
            		
        		}
        		%>
        		</select>
        		
        		<%        	
        		Osservazioni.setSelectSize(	6);
        		Osservazioni.setMultiple(true);
        		%>
         		
         		<%= Osservazioni.getHtmlSelect("Osservazioni_Gravi",listaOsservazioniGravi) %>
        		<%        		
        	}
        	else
        	{
        	%>
         	<input type = "hidden" name = "Provvedimenti3_1" id = "Provvedimenti3_1" value = "-1">
        	Selezionare Oggetto dell'audit dal controllo
        	
        	<%--
        	Osservazioni.setSelectSize(8);
        	Osservazioni.setMultiple(true);
        	Osservazioni.setJsEvent("onchange=calcolaPuntiGravi(1,25);"); %>
         <%= Osservazioni.getHtmlSelect("Provvedimenti3_1",lista1)--%>
        <%} %>
        </td>
          </tr>
             <%
        numEl++;     
        %>
          
          </table>
      </td></tr>
      <tr>
                  <td><textarea name = "note_gravi" rows="5" cols="60" value = "<%=(ncGrave!=null)? ncGrave.getNote() : "" %>"><%=(ncGrave!=null)? ncGrave.getNote() : "" %></textarea> </td>
          
          <td>Punteggio
          <input type="text" value="<%=TicketDetails.getPuntiGravi()%>" name="puntiGravi" id="puntiGravi" readonly="readonly" onchange="calcolaTotale()">
          </td>
        
             <td>&nbsp;&nbsp;&nbsp;</td>

        </tr>
        
        
      </table>
    </td>
  </tr>
   
  
  </table>
  
    <input type = "hidden" name = "stato_gravi" id = "stato_gravi" value = "true">
  <input type = "hidden" name = "descrizione_or_combo_sel_gravi" id = "descrizione_or_combo_sel_gravi" value = "false">