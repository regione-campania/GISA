<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.modules.nonconformita.base.ElementoNonConformita"%>
<%@page import="org.aspcfs.modules.nonconformita.base.ElementoOsservazione"%>
<%@page import="org.aspcfs.utils.web.LookupElement"%><input type = "hidden" name = "idIspezione" value = "<%=request.getAttribute("idIspezione")%>">
<jsp:useBean id="CU" class="org.aspcfs.modules.vigilanza.base.Ticket" scope="request"/>

<input type="hidden" name = "identificativoNC" value = "<%=TicketDetails.getIdentificativo() %>">
<%if(TicketDetails.getNon_conformita_formali()!=null){

if(	TicketDetails.getNon_conformita_formali().size()>0 && !TicketDetails.getPuntiFormali().equals("0") )
{
	ArrayList<ElementoOsservazione> table=TicketDetails.getNon_conformita_formali();
	
	%>
	<tr class="details">
			<th nowrap  colspan="2" align="left"><strong>Osservazioni</strong></th>
		</tr>
<tr class="containerBody">
    <td valign="top" class="formLabel">
	Osservazioni
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
      <%
      int i = 1 ;
      if (table.size()>0 )
  	  {	
    	  ElementoOsservazione nc = table.get(0);
    	 %> 
        <tr>
        
           
          <td>
      <%
      	out.println("<b>Oggetto Audit :</b> <br>"); 
      for (int j = 0 ; j < nc.getListaOggettiAudit().size();j++)
      {
      	out.print(((LookupElement)nc.getListaOggettiAudit().get(j)).getDescription().toUpperCase()+"<br>");
      }
    	i++;
      
      %>     
         <%
      	out.println("<br><b>Osservazioni : </b></br>"); 
      for (int j = 0 ; j < nc.getListaOsservazioni().size() ;j++)
      {
      	out.print(((LookupElement)nc.getListaOsservazioni().get(j)).getDescription().toUpperCase()+"<br>");
      }
    	i++;
       
      %>     
          </td>
          <td>&nbsp;</td>
          <td>
          
        
          </td>
           <td>&nbsp;</td>
           <td>Note : <%=toHtmlValue(nc.getNote()) %></td>
         </tr>
         
         <%} %>
       </table>
    </td>
</tr>



<%

 	if(TicketDetails.getPuntiFormali()!=null && !TicketDetails.getPuntiFormali().equals(""))
  	{
    	   %>
       <tr  class="containerBody">
  
       <td class="formLabel">Punteggio  <b>Osservazioni </b>: </td>
        <td><%=TicketDetails.getPuntiFormali() %></td>
        </tr>
        <%
    }
  	
%>


<% }}%>

     

<%

if(TicketDetails.getNon_conformita_significative()!=null) {

if(TicketDetails.getNon_conformita_significative().size()>0 && !TicketDetails.getPuntiSignificativi().equals("0"))
{
	ArrayList<ElementoOsservazione> table = TicketDetails.getNon_conformita_significative();
	%>
	<tr class="details">
			<th nowrap colspan="2"  align="left"><strong>Raccomandazioni Significative</center></th>
		</tr>
<tr class="containerBody">
    <td valign="top" class="formLabel">
     Raccomandazioni Selezionate
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
      <%
      int i = 1;
      if (table.size()>0 )
  	  {	
    	  ElementoOsservazione nc = table.get(0);
      	
    	 %> 
        <tr>
        
           
          <td>
      <%
      	out.println("<b>Oggetto Audit : </b> </br>"); 
      for (int j = 0 ; j < nc.getListaOggettiAudit().size();j++)
      {
      	out.print(((LookupElement)nc.getListaOggettiAudit().get(j)).getDescription().toUpperCase()+"<br>");
      }
    	i++;
      
      %>     
         <%
      	out.println("<br><b>Osservazioni : </b> </br>"); 
      for (int j = 0 ; j < nc.getListaOsservazioni().size() ;j++)
      {
      	out.print(((LookupElement)nc.getListaOsservazioni().get(j)).getDescription().toUpperCase()+"<br>");
      }
    	i++;
      
      %>     
          </td>
          <td>&nbsp;</td>
          <td>
          
        
          </td>
            <td>Note : <%=toHtmlValue(nc.getNote()) %></td>
         </tr>
         
         <%} %>
    </table>
    </td>
</tr>


<%
    
      
    	   if(TicketDetails.getPuntiSignificativi()!=null && !TicketDetails.getPuntiSignificativi().equals("")){
    	   %>
       <tr  class="containerBody">
       <td class="formLabel">Punteggio Raccomandazioni <b>Significative</b>: </td>
        <td><%=TicketDetails.getPuntiSignificativi() %></td>
        </tr>
        
        <%}
    %>


    
<%
}
}
     %>

<%
if(TicketDetails.getNon_conformita_gravi()!=null && !TicketDetails.getPuntiGravi().equals("0"))
{
	

	ArrayList<ElementoOsservazione> table=TicketDetails.getNon_conformita_gravi();
	
	
	%>
	<tr class="details">
			<th nowrap  colspan="2"  align="left"><strong>Raccomandazioni Gravi</strong></th>
		</tr>
<tr class="containerBody">
    <td valign="top" class="formLabel">
  	Raccomandazioni Selezionate
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
      <%
      int i = 1 ;
      if (table.size()>0 )
  	  {	
    	  ElementoOsservazione nc = table.get(0);
      	
    	 %> 
        <tr>
        
           
          <td>
      <%
      	out.println("<b>Oggetto Audit : </b></br>"); 
      for (int j = 0 ; j < nc.getListaOggettiAudit().size();j++)
      {
      	out.print(((LookupElement)nc.getListaOggettiAudit().get(j)).getDescription().toUpperCase()+"<br>");
      }
    	i++;
      
      %>     
          <%
      	out.println("<br><b>Osservazioni : </b> </br>"); 
      for (int j = 0 ; j < nc.getListaOsservazioni().size() ;j++)
      {
      	out.print(((LookupElement)nc.getListaOsservazioni().get(j)).getDescription().toUpperCase()+"<br>");
      }
    	i++;
       
      %>     
          </td>
          <td>&nbsp;</td>
          <td>
          
       
          </td>
            <td>Note : <%=toHtmlValue(nc.getNote()) %></td>
         </tr>
         
         <%} %>
    </table>
    </td>
</tr>


<%

       if(TicketDetails.getPuntiGravi()!=null && !TicketDetails.getPuntiGravi().equals("") ){
       
       %>
       <tr  class="containerBody">
       <td class="formLabel">Punteggio Raccomandazioni  <b>Gravi</b>: </td>
        <td><%=TicketDetails.getPuntiGravi() %></td>
        </tr>
        
        <%}%>
   
  
        
        
        <% }
   
       
  
    	   int formali=0;
    	   int gravi=0;
    	   int significativi=0;
    	   
    	   if(TicketDetails.getPuntiSignificativi()!=null && !TicketDetails.getPuntiSignificativi().equals("")){
    		   significativi=Integer.parseInt(TicketDetails.getPuntiSignificativi());
    		   
    	   }
    	   if(TicketDetails.getPuntiFormali()!=null && !TicketDetails.getPuntiFormali().equals("")){
    		   formali=Integer.parseInt(TicketDetails.getPuntiFormali());
    		   
    	   }
    	   
    	   if(TicketDetails.getPuntiGravi()!=null && !TicketDetails.getPuntiGravi().equals("")){
    		   gravi=Integer.parseInt(TicketDetails.getPuntiGravi());
    		   
    	   }
    	   
    	   %>
    	   <tr class="details">
			<th nowrap  colspan="2"  align="left"><strong>Punteggio Totale</strong></th>
		</tr>
       <tr  class="containerBody">
       <td class="formLabel">Punteggio <b>Accumulato</b>: </td>
        <td><%=formali+significativi+gravi %></td>
        </tr>
        


     

