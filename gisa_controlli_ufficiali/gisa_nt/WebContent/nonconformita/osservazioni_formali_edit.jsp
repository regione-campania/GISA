<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.nonconformita.base.ElementoNonConformita"%>

<%@page import="org.aspcfs.apps.lookuplists.LookupLists"%>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@page import="org.aspcfs.modules.nonconformita.base.ElementoOsservazione"%><h2>Osservazione</h2>
<table>
<tr id = "show_formali">
    <td>
   <input type = "hidden" id = "elementi_nc_formali" name = "elementi_nc_formali" value = "<%=(TicketDetails.getNon_conformita_formali().size()) %>">
   <input type = "hidden" id = "size_nc_formali" name = "size_nc_formali" value = "<%=TicketDetails.getNon_conformita_formali().size() %>">
      <table border="0" cellspacing="0" cellpadding="10" class="empty" >
        <tr >
        <td>
        <table>
         <%
        String nc_formali_inserite_f = "";
        
     LookupList listaOggettoAudit = new LookupList();
     LookupList listaOsservazioni= new LookupList();
     ElementoOsservazione nc = null ;
        if(TicketDetails.getNon_conformita_formali().size()>0)
        {
        	 nc = TicketDetails.getNon_conformita_formali().get(0);
        	 
        	 listaOggettoAudit  = nc.getListaOggettiAudit();
        	 listaOsservazioni = nc.getListaOsservazioni();
        	
        }
        %>
       
        <%
        int i = 0;
      
        	i = i+1;
        	
        	%>
        <tr id = "nc_formali_<%="" %>" >
        
         <td>
         	<%
         		
        	if ( CU.getOggettoAudit().size()!=0)
        	{
        		
        		Iterator<Integer> ite = CU.getOggettoAudit().keySet().iterator();
        		%>
        		<select multiple="multiple" name = "Provvedimenti1_1"  id = "Provvedimenti1_1"  size = "6" onchange="calcolaPuntiFormali(1,1);">	
        		<option value = "-1" >-- SELEZIONA UNA OSSERVAZIONE --</option>
        		<%
        		while(ite.hasNext())
        		{
        			
        			int code = ite.next();
        			
        			
        				
        			%>
        			<option value ="<%=code %>" <%if (listaOggettoAudit.containsKey(code)){%>selected="selected" <%} %>><%=CU.getOggettoAudit().get(code) %></option>
        			<%
            		
        		}
        		%>
        		</select>
        		
        		<%        	
        		Osservazioni.setSelectSize(	6);
        		Osservazioni.setMultiple(true);
        		%>
         		
         		<%= Osservazioni.getHtmlSelect("Osservazioni_Formali",listaOsservazioni) %>
        		<%        		
        	}
        	else
        	{
        	%>
        		
        		<input type = "hidden" name = "Provvedimenti1_1" id = "Provvedimenti2_1" value = "-1">
        	Selezionare Oggetto dell'audit dal controllo
        	
        		
     
         <%} %>
         
        </td>
        
        </tr>
       
                 
      </table>
      </td></tr>
      <tr>
        <td><textarea name = "note_formali" rows="5" cols="60" value = "<%=(nc!=null)? nc.getNote() : "" %>"><%=(nc!=null)? nc.getNote() : "" %></textarea> </td>
          <td>
          <center>Punteggio</center>
           <br>
            <input type="text"  name="puntiFormali" readonly="readonly" id="puntiFormali" value="<%= TicketDetails.getPuntiFormali() %>" onchange="calcolaTotale()">
          </td>
        </tr>
        <tr>
<td >

</td>
</tr>
        
        
      </table>
    </td>
  </tr>
 
  </table>
  <input type = "hidden" name = "stato_formali" id = "stato_formali" value = "true">
  <input type = "hidden" name = "descrizione_or_combo_sel" id = "descrizione_or_combo_sel_formali" value = "false">