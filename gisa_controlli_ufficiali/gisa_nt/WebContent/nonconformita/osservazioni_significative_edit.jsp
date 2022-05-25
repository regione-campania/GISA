<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.nonconformita.base.ElementoNonConformita"%>

<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@page import="org.aspcfs.modules.nonconformita.base.ElementoOsservazione"%><h2>Raccomandazione Significativa</h2>
<table>
  <tr  id = "show_significative">
    <td>
     <input type = "hidden" id = "elementi_nc_significative" name = "elementi_nc_significative" value = "<%=(TicketDetails.getNon_conformita_significative().size()) %>">
   		<input type = "hidden" id = "size_nc_significative" name = "size_nc_significative" value = "<%=(TicketDetails.getNon_conformita_significative().size()) %>">
      <table border="0" cellspacing="0" cellpadding="10" class="empty"  >
        <tr>
        <td>
        <table>
           <%
        String nc_sign_inserite_s = "";
       
           LookupList listaOggettoAuditSign = new LookupList();
           LookupList listaOsservazioniSign= new LookupList();
           ElementoOsservazione ncSign = null ;
              if(TicketDetails.getNon_conformita_significative().size()>0)
              {
            	  ncSign = TicketDetails.getNon_conformita_significative().get(0);
              	 
            	  listaOggettoAuditSign  = ncSign.getListaOggettiAudit();
            	  listaOsservazioniSign = ncSign.getListaOsservazioni();
              	
              }
           %>
           
    
<!--        <tr><td colspan="3"><input type = "button" value = "Inserisci un'altra Raccomandazione Significativa" onclick="return clonaNC_significative()"><input type = "button" value = "Elimina Raccomandazione Significative"  onclick="removeSignificative('false',<%=TicketDetails.getNon_conformita_significative().size() %>);"><input type = "button" value = "Reset Raccomandazione Significativa" onclick="resetSignificative_update('<%=CU.getId() %>',2,<%=TicketDetails.getNon_conformita_significative().size() %>,'<%=request.getAttribute("attivita_significative") %>','<%=nc_sign_inserite_s %>','')" ></td></tr>-->
        <%
      	int curr = 0 ;
       
        	curr++ ;
        	%>
        <tr id = "nc_significative_<%="" %>">
         
       
        <td>
         
        <%
         		
        	if ( CU.getOggettoAudit().size()!=0)
        	{
        		
        		Iterator<Integer> ite = CU.getOggettoAudit().keySet().iterator();
        		%>
        		<select multiple="multiple" name = "Provvedimenti2_1"  id = "Provvedimenti2_1"  size = "6" onchange="calcolaPuntiSignificative(1,7)">	
        		<option value = "-1" >-- SELEZIONA UNA OSSERVAZIONE --</option>
        		<%
        		while(ite.hasNext())
        		{
        			
        			int code = ite.next();
        			
        			
        				
        			%>
        			<option value ="<%=code %>" <%if (listaOggettoAuditSign.containsKey(code)){%>selected="selected" <%} %>><%=CU.getOggettoAudit().get(code) %></option>
        			<%
            		
        		}
        		%>
        		</select>
        		
        		<%
        			Osservazioni.setSelectSize(	6);
        		Osservazioni.setMultiple(true);
        		%>
         		
         		<%= Osservazioni.getHtmlSelect("Osservazioni_Significative",listaOsservazioniSign) %>
        		<%        		
        	}
        	else
        	{
        	%>
        	
        	
    		<input type = "hidden" name = "Provvedimenti2_1" id = "Provvedimenti2_1" value = "-1">
    	Selezionare Oggetto dell'audit dal controllo
    <%} %>
        </td>
        
         
          </tr>
           
        
        
      </table>
      </td></tr>
      <tr>
                          <td><textarea name = "note_significative" rows="5" cols="60" value = "<%=(ncSign!=null)? ncSign.getNote() : "" %>"><%=(ncSign!=null)? ncSign.getNote() : "" %></textarea> </td>
        
          <td>
           <center> Punteggio </center><br>
             <input type="text" value="<%=TicketDetails.getPuntiSignificativi() %>" name="puntiSignificativi" id="puntiSignificativi" readonly="readonly" onchange="calcolaTotale()">
             
          </td>
         
             <td>&nbsp;&nbsp;&nbsp;</td>
       </tr>
   
      </table>
       
    </td>
  </tr>
   
  
  </table>
  <input type = "hidden" name = "stato_significative" id = "stato_significative" value = "true">
  <input type = "hidden" name = "descrizione_or_combo_sel" id = "descrizione_or_combo_sel_significative" value = "false">