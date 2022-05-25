<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.modules.nonconformita.base.ElementoNonConformita"%><input type = "hidden" name = "idIspezione" value = "<%=request.getAttribute("idIspezione")%>">
<jsp:useBean id="DiffideList" class="org.aspcfs.modules.diffida.base.TicketList" scope="request"/>
<jsp:useBean id="ProvvedimentiBenessereMacellazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ProvvedimentiBenessereTrasporto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="listaLineeCU" class="java.util.ArrayList" scope="request"/>
<%@page import="org.aspcfs.modules.lineeattivita.base.LineeAttivita"%>

<input type="hidden" name = "identificativoNC" value = "<%=TicketDetails.getIdentificativo() %>">
<%if(TicketDetails.getNon_conformita_formali()!=null){

if(	TicketDetails.getNon_conformita_formali().size()!=0  && (TicketDetails.getNon_conformita_formali().get(0).getId_nc()!=-1))
{
	ArrayList<ElementoNonConformita> table=TicketDetails.getNon_conformita_formali();
	
	%>
	<tr class="details">
			<th nowrap  colspan="2" align="left"><strong>Non Conformita Formali</strong></th>
		</tr>
<tr class="containerBody">
    <td valign="top" class="formLabel">
     Non Conformità Selezionate
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
      <%
      int i = 1 ;
      for ( ElementoNonConformita nc : table)
  	  {
      	
    	 %> 
        <tr>
        <td width="4%" >
      
        	<%out.println("<b> Descrizione di ciascuna NC </b> "+"</br>"); %>
       
         <textarea cols="55" rows="6" disabled="disabled"> <%=nc.getNote().toUpperCase() %></textarea>
        
        </td>
           
           <td>&nbsp;</td>
           <td>&nbsp;</td>
          <td>
      <%
      	out.println("<b>N.C Formale</b> "+i+"</br>"); 
      	out.print(nc.getDescrizione_nc().toUpperCase()+"<br>");
      	
      	if (nc.getId_nc_benessere_macellazione()>0)
          	out.print(ProvvedimentiBenessereMacellazione.getSelectedValue(nc.getId_nc_benessere_macellazione()).toUpperCase()+"<br>");
      	if (nc.getId_nc_benessere_trasporto()>0)
          	out.print(ProvvedimentiBenessereTrasporto.getSelectedValue(nc.getId_nc_benessere_trasporto()).toUpperCase()+"<br>");
      		
      	if (listaLineeCU!=null && listaLineeCU.size()>0) { 
      	out.println("<b>Linea sottoposta a non conformita'</b></br>"); 
      	for (int k = 0; k<listaLineeCU.size(); k++) {
             LineeAttivita linea = (LineeAttivita) listaLineeCU.get(k);
             if (linea.getId() == nc.getId_linea_nc())
               	out.print((linea.getMacroarea()!= null ? linea.getMacroarea() + "->" : "") + (linea.getAggregazione()!=null ? linea.getAggregazione() + "->" : linea.getCategoria()!=null ? linea.getCategoria() + "->" : "") + (linea.getAttivita()!=null ? linea.getAttivita() + "->" : linea.getLinea_attivita()!=null ? linea.getLinea_attivita() : "")+"<br>");
      	} }
    	i++;
      } 
      %>     
          </td>
         </tr>
       </table>
    </td>
</tr>

<tr class="containerBody">
    <td valign="top" class="formLabel">
     Valutazione del Rischio
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
                   <textarea cols="55" rows="6" disabled="disabled"> <%=toHtml(TicketDetails.getNcFormaliValutazioni()) %></textarea>
          
        </td>
        </tr>
       </table>
    </td>
</tr>

<%

if(CU.getTipoCampione()!=5)
{
 	if(TicketDetails.getPuntiFormali()!=null && !TicketDetails.getPuntiFormali().equals(""))
  	{
    	   %>
       <tr  class="containerBody">
       <td class="formLabel">Punteggio Non Conformità <b>Formali</b>: </td>
        <td><%=TicketDetails.getPuntiFormali() %></td>
        </tr>
        <%
    }
  	
 }
%>
<tr class="containerBody">
    <td valign="top" class="formLabel">
    Elenco Follow Up
    </td>
    <td>
    

     <table cellpadding="4" cellspacing="0" width="100%" class = "noborder" >
		
		<thead><tr style="background-color: rgb(204, 255, 153);">
    	<td valign="center" align="left">
      		<strong><dhv:label name="">Codice Attività</dhv:label></strong>
    	</td>
     <td><b><dhv:label name="sanzionia.data_richiesta">Data Esecuzione Attività</dhv:label></b></td>
   
  </tr>
  </thead>
  <tr>
  <td colspan="2">
   <div style="overflow-x;hidden;overflow-y:auto;height: 100px;width: 100%">
  <table width="100%">
  
       <%
       Iterator f = FollowupList.iterator();
   	   if ( f.hasNext() ) {
       int rowid = 0;
        i =0;
       while (f.hasNext()) {      
           i++;
           rowid = (rowid != 1?1:2);
           org.aspcfs.modules.followup.base.Ticket thisSan = (org.aspcfs.modules.followup.base.Ticket)f.next();
         	if (thisSan.getTipo_nc()==  org.aspcfs.modules.followup.base.Ticket.NC_FORMALI)
         	{
     %>
     <tr >
     	<% if(thisSan.getIdStabilimento() > 0) { %>
         <td  valign="top" nowrap>
   			<a href="<%=OrgDetails.getAction() %>Followup.do?command=TicketDetails&id=<%= thisSan.getId() %>&idC=<%=request.getAttribute("idC")%>&idNC=<%=TicketDetails.getId()%>&idNodo=<%=request.getAttribute("idNodo") %>&stabId=<%= thisSan.getIdStabilimento()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisSan.getIdentificativo() %></a>
   		</td>
   		<% } else { %>
   		  <td  valign="top" nowrap>
   			<a href="<%=OrgDetails.getAction() %>Followup.do?command=TicketDetails&id=<%= thisSan.getId() %>&idC=<%=request.getAttribute("idC")%>&idNC=<%=TicketDetails.getId()%>&idNodo=<%=request.getAttribute("idNodo") %>&stabId=<%= thisSan.getIdApiario()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisSan.getIdentificativo() %></a>
   		</td>
   		<% } %>
        <td valign="top">
         <% if(!User.getTimeZone().equals(thisSan.getAssignedDate())){%>
         <zeroio:tz timestamp="<%= thisSan.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
         <% } else { %>
         <zeroio:tz timestamp="<%= thisSan.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisSan.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
         <% } %>
   		</td>
   	</tr>
   	<tr >
         <td colspan="2" valign="top">
         
           <%= toHtml(thisSan.getProblemHeader()) %>&nbsp;
           <% if (thisSan.getClosed() == null) {
        	   if(TicketDetails.isChiusura_attesa_esito()==true)
        	   {
        		   %>
        		   [<font color="orange">Pratica Temporaneamente Chiusa in Attesa di Esito di Campioni e/o Tamponi</font>]
        		   <%
        	   }
        	   else
        	   {
        		
        	   %>
             [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label><%=(thisSan.getDataChiusura()!=null)?(" e scaduta d'ufficio per cambio titolarità"):("")%></font>]
           <%} }else {%>
             [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label><%=(thisSan.getDataChiusura()!=null)?(" e scaduta d'ufficio per cambio titolarità"):("")%></font>]
           <%}%>
         </td>
       </tr>
       
     <%}}%>
   
     <%}%>
   
   </table>
   </div>
   </td>
   </tr>
     
     
        </table>
    
      
    </td>
</tr>

<% }}%>

     

<%
if(TicketDetails.getNon_conformita_significative()!=null) {

if(TicketDetails.getNon_conformita_significative().size()!=0 && ( TicketDetails.getNon_conformita_significative().get(0).getId_nc()!=-1))
{
	ArrayList<ElementoNonConformita> table = TicketDetails.getNon_conformita_significative();
	%>
	<tr class="details">
			<th nowrap colspan="2"  align="left"><strong>Non Conformita Significative</center></th>
		</tr>
<tr class="containerBody">
    <td valign="top" class="formLabel">
      Non Conformità Selezionate
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
      <%
      int i = 1;
      for ( ElementoNonConformita nc : table)
  	  {
      
    	 %>
        <tr>
        <td>
        <%out.println("<b> Descrizione di ciascuna NC </b> </br>"); %>
      
      <textarea cols="55" rows="6" disabled="disabled"> <%=nc.getNote() %></textarea>
        
        </td>
           <td>&nbsp;</td>
           <td>&nbsp;</td>
          <td>
      <%
      	out.println("<b>N.C Significative</b> "+i+"</br>"); 
     	out.print(nc.getDescrizione_nc().toUpperCase()+"<br>");
     	
     	if (nc.getId_nc_benessere_macellazione()>0)
          	out.print(ProvvedimentiBenessereMacellazione.getSelectedValue(nc.getId_nc_benessere_macellazione()).toUpperCase()+"<br>");
      	if (nc.getId_nc_benessere_trasporto()>0)
          	out.print(ProvvedimentiBenessereTrasporto.getSelectedValue(nc.getId_nc_benessere_trasporto()).toUpperCase()+"<br>");
      	
     	if (listaLineeCU!=null && listaLineeCU.size()>0) { 
     	out.println("<b>Linea sottoposta a non conformita'</b></br>"); 
      	for (int k = 0; k<listaLineeCU.size(); k++) {
             LineeAttivita linea = (LineeAttivita) listaLineeCU.get(k);
             if (linea.getId() == nc.getId_linea_nc())
               	out.print((linea.getMacroarea()!= null ? linea.getMacroarea() + "->" : "") + (linea.getAggregazione()!=null ? linea.getAggregazione() + "->" : linea.getCategoria()!=null ? linea.getCategoria() + "->" : "") + (linea.getAttivita()!=null ? linea.getAttivita() + "->" : linea.getLinea_attivita()!=null ? linea.getLinea_attivita() : "")+"<br>");
      	} }
    	  i++;
    	 %>     
          </td>
           
        </tr>
    <%} %>
    </table>
    </td>
</tr>

<tr class="containerBody">
    <td valign="top" class="formLabel">
     Valutazione del Rischio
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
     <textarea cols="55" rows="6" disabled="disabled"> <%=toHtml(TicketDetails.getNcSignificativeValutazioni()) %></textarea>   </td>
        
        </tr>
    </table>
    </td>
</tr>

<%
       if(CU.getTipoCampione()!=5)
       {
      
    	   if(TicketDetails.getPuntiSignificativi()!=null && !TicketDetails.getPuntiSignificativi().equals("")){
    	   %>
       <tr  class="containerBody">
       <td class="formLabel">Punteggio Non Conformità <b>Significative</b>: </td>
        <td><%=TicketDetails.getPuntiSignificativi() %></td>
        </tr>
        
        <%}
    }%>

<tr class="containerBody">
    <td valign="top" class="formLabel">
    Elenco Follow Up
    </td>
    <td>
    
     <table cellpadding="4" cellspacing="0" width="100%" class = "noborder" >
		<tr style="background-color: rgb(204, 255, 153);">
    	<td valign="center" align="left">
      		<strong><dhv:label name="">Codice Attività</dhv:label></strong>
    	</td>
     <td><b><dhv:label name="sanzionia.data_richiesta">Data Esecuzione Attività</dhv:label></b></td>
   
  </tr>
  <tr>
  <td colspan="2">
   <div style="overflow-x;hidden;overflow-y:auto;height: 100px;width: 100%">
  <table width="100%">
       <%
       Iterator f = FollowupList.iterator();
   	   if ( f.hasNext() ) {
       int rowid = 0;
       i =0;
       while (f.hasNext()) {      
           i++;
           rowid = (rowid != 1?1:2);
           org.aspcfs.modules.followup.base.Ticket thisSan = (org.aspcfs.modules.followup.base.Ticket)f.next();
         	if (thisSan.getTipo_nc()==  org.aspcfs.modules.followup.base.Ticket.NC_SIGNIFICATIVE)
         	{
     %>
     <tr >
     <% if(thisSan.getIdStabilimento() > 0) { %>
         <td  valign="top" nowrap>
   			<a href="<%=OrgDetails.getAction() %>Followup.do?command=TicketDetails&id=<%= thisSan.getId() %>&idC=<%=request.getAttribute("idC")%>&idNodo=<%=request.getAttribute("idNodo") %>&idNC=<%=TicketDetails.getId()%>&stabId=<%= thisSan.getIdStabilimento()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisSan.getIdentificativo() %></a>
   		</td>
   	<% } else { %>
   	 	<td  valign="top" nowrap>
   			<a href="<%=OrgDetails.getAction() %>Followup.do?command=TicketDetails&id=<%= thisSan.getId() %>&idC=<%=request.getAttribute("idC")%>&idNodo=<%=request.getAttribute("idNodo") %>&idNC=<%=TicketDetails.getId()%>&stabId=<%= thisSan.getIdApiario()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisSan.getIdentificativo() %></a>
   		</td>
   	<% } %>	
        <td valign="top">
         <% if(!User.getTimeZone().equals(thisSan.getAssignedDate())){%>
         <zeroio:tz timestamp="<%= thisSan.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
         <% } else { %>
         <zeroio:tz timestamp="<%= thisSan.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisSan.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
         <% } %>
   		</td>
   	</tr>
   	<tr >
         <td colspan="2" valign="top">
         
           <%= toHtml(thisSan.getProblemHeader()) %>&nbsp;
           <% if (thisSan.getClosed() == null) { 
        	   if(TicketDetails.isChiusura_attesa_esito()==true)
        	   {
        		   %>
        		   [<font color="orange">Pratica Temporaneamente Chiusa in Attesa di Esito di Campioni e/o Tamponi</font>]
        		   <%
        	   }
        	   else
        	   {
        		
        	   %>
             [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
           <%}} else {%>
             [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
           <%}%>
         </td>
       </tr>
       
     <%}}%>
   
     <%}%>
     </table></div></td></tr>
        </table>
     </div> 
      
    </td>
</tr>
    
<%
}
}
     %>

<%
if(TicketDetails.getNon_conformita_gravi()!=null && (TicketDetails.getNon_conformita_gravi().get(0).getId_nc()!=-1))
{

	ArrayList<ElementoNonConformita> table=TicketDetails.getNon_conformita_gravi();
	
	%>
	<tr class="details">
			<th nowrap  colspan="2"  align="left"><strong>Non Conformita Gravi</strong></th>
		</tr>
<tr class="containerBody">
    <td valign="top" class="formLabel">
  	Non Conformità Selezionate
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
      <%
      int i = 1 ;
      for ( ElementoNonConformita nc : table)
  	  {
      
    	 %>
        <tr>
        <td>
        <%out.println("<b> Descrizione di ciascuna NC </b> "+"</br>"); %>
        <textarea cols="55" rows="6" disabled="disabled"> <%=nc.getNote().toUpperCase() %></textarea></td>
           <td>&nbsp;</td>
           <td>&nbsp;</td>
          <td>
       
      <%
      	out.println("<b>N.C Grave </b> "+i+"</br>"); 
     	
    	 out.print(nc.getDescrizione_nc().toUpperCase()+"<br>");
    	 
    	 if (nc.getId_nc_benessere_macellazione()>0)
           	out.print(ProvvedimentiBenessereMacellazione.getSelectedValue(nc.getId_nc_benessere_macellazione()).toUpperCase()+"<br>");
       	if (nc.getId_nc_benessere_trasporto()>0)
           	out.print(ProvvedimentiBenessereTrasporto.getSelectedValue(nc.getId_nc_benessere_trasporto()).toUpperCase()+"<br>");
       	
    	 if (listaLineeCU!=null && listaLineeCU.size()>0) { 
    	 out.println("<b>Linea sottoposta a non conformita'</b></br>"); 
       	for (int k = 0; k<listaLineeCU.size(); k++) {
              LineeAttivita linea = (LineeAttivita) listaLineeCU.get(k);
              if (linea.getId() == nc.getId_linea_nc())
                	out.print((linea.getMacroarea()!= null ? linea.getMacroarea() + "->" : "") + (linea.getAggregazione()!=null ? linea.getAggregazione() + "->" : linea.getCategoria()!=null ? linea.getCategoria() + "->" : "") + (linea.getAttivita()!=null ? linea.getAttivita() + "->" : linea.getLinea_attivita()!=null ? linea.getLinea_attivita() : "")+"<br>");
       	} }
    	i++;
    	 %>     
          </td>
          
        </tr>
       
        
        <%} %>
    </table>
    </td>
</tr>

<%-- <tr class="containerBody">
    <td valign="top" class="formLabel">
     Valutazione del Rischio
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
     <textarea cols="55" rows="6" disabled="disabled"> <%=toHtml(TicketDetails.getNcGraviValutazioni()) %></textarea>   </td>
        
        </tr>
    </table>
    </td>
</tr>
--%>

<%

       if(CU.getTipoCampione()!=5){ 
       if(TicketDetails.getPuntiGravi()!=null && !TicketDetails.getPuntiGravi().equals("") ){
       
       %>
       <tr  class="containerBody">
       <td class="formLabel">Punteggio Non Conformità <b>Gravi</b>: </td>
        <td><%=TicketDetails.getPuntiGravi() %></td>
        </tr>
        
        <%}}%>
   
   <tr class="containerBody">
    <td valign="top" class="formLabel">
   Elenco Follow Up
    </td>
    <td>
   
     <table cellpadding="4" cellspacing="0" width="100%" class = "noborder" >
		<tr style="background-color: rgb(204, 255, 153);">
		<td valign="center" align="left">
      		<strong><dhv:label name="">Descrizione Attività</dhv:label></strong>
    	</td>
    	<td valign="center" align="left">
      		<strong><dhv:label name="">Codice Attività</dhv:label></strong>
    	</td>
     <td><b><dhv:label name="sanzionia.data_richiesta">Data Esecuzione Attività</dhv:label></b></td>
   
  </tr>
  <tr>
  <td colspan="3">
   <div style="overflow-x;hidden;overflow-y:auto;height: 100px;width: 100%">
  <table width="100%">
      <%
       Iterator f = SanzioniList.iterator();
   	   if ( f.hasNext() ) {
       int rowid = 0;
       i =0;
       while (f.hasNext()) {      
           i++;
           rowid = (rowid != 1?1:2);
           org.aspcfs.modules.sanzioni.base.Ticket thisSan = (org.aspcfs.modules.sanzioni.base.Ticket)f.next();
         	if (thisSan.getTipo_nc()==  org.aspcfs.modules.followup.base.Ticket.NC_GRAVI)
         	{
     %>
     <tr >
      <td  valign="top" nowrap>
     <b> Sanzione</b>
      </td>
      <%if(thisSan.getIdStabilimento() > 0) {%>
         <td  valign="top" nowrap>
   			<a href="<%=OrgDetails.getAction() %>Sanzioni.do?command=TicketDetails&id=<%= thisSan.getId() %>&idNodo=<%=request.getAttribute("idNodo") %>&idC=<%=request.getAttribute("idC")%>&idNC=<%=TicketDetails.getId()%>&orgId=<%= thisSan.getOrgId()%>&stabId=<%= thisSan.getIdStabilimento()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisSan.getIdentificativo() %></a>
   		</td>
   		<% } else { %>
   		  <td  valign="top" nowrap>
   			<a href="<%=OrgDetails.getAction() %>Sanzioni.do?command=TicketDetails&id=<%= thisSan.getId() %>&idNodo=<%=request.getAttribute("idNodo") %>&idC=<%=request.getAttribute("idC")%>&idNC=<%=TicketDetails.getId()%>&orgId=<%= thisSan.getOrgId()%>&stabId=<%= thisSan.getIdApiario()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisSan.getIdentificativo() %></a>
   		</td>
   		<% } %>
        <td valign="top">
         <% if(!User.getTimeZone().equals(thisSan.getAssignedDate())){%>
         <zeroio:tz timestamp="<%= thisSan.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
         <% } else { %>
         <zeroio:tz timestamp="<%= thisSan.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisSan.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
         <% } %>
   		</td>
   	</tr>
   	<tr >
         <td colspan="2" valign="top">
         
           <%= toHtml(thisSan.getProblemHeader()) %>&nbsp;
           <% if (thisSan.getClosed() == null) {
        	   if(TicketDetails.isChiusura_attesa_esito()==true)
        	   {
        		   %>
        		   [<font color="orange">Pratica Temporaneamente Chiusa in Attesa di Esito di Campioni e/o Tamponi</font>]
        		   <%
        	   }
        	   else
        	   {
        		
        	   %>
             [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
           <%}} else {%>
             [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
           <%}%>
         </td>
       </tr>
       
     <%}}%>
   
     <%}
   	 f = DiffideList.iterator();
	   if ( f.hasNext() ) {
		int rowid = 0;
    i =0;
    while (f.hasNext()) {   
  	  
        i++;
        rowid = (rowid != 1?1:2);
        org.aspcfs.modules.diffida.base.Ticket thisSan = (org.aspcfs.modules.diffida.base.Ticket)f.next();
        
      	if (thisSan.getTipo_nc()==  org.aspcfs.modules.followup.base.Ticket.NC_GRAVI)
      	{
  %>
  <tr >
   <td  valign="top" nowrap>
  <b> Diffida</b>
   </td>
  
      <td  valign="top" nowrap>
			<a href="<%=OrgDetails.getAction()%>Diffida.do?command=TicketDetails&id=<%= thisSan.getId() %>&idNodo=<%=request.getAttribute("idNodo") %>&idC=<%=request.getAttribute("idC")%>&idNC=<%=TicketDetails.getId()%>&orgId=<%= thisSan.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisSan.getIdentificativo() %></a>
		</td>
	
     <td valign="top">
      <% if(!User.getTimeZone().equals(thisSan.getAssignedDate())){%>
      <zeroio:tz timestamp="<%= thisSan.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
      <% } else { %>
      <zeroio:tz timestamp="<%= thisSan.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisSan.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
      <% } %>
		</td>
	</tr>
	<tr >
      <td colspan="2" valign="top">
      
        <%= toHtml(thisSan.getProblemHeader()) %>&nbsp;
        <% if (thisSan.getClosed() == null) { 
     	   if(TicketDetails.isChiusura_attesa_esito()==true)
     	   {
     		   %>
     		   [<font color="orange">Pratica Temporaneamente Chiusa in Attesa di Esito di Campioni e/o Tamponi</font>]
     		   <%
     	   }
     	   else
     	   {
     		
     	   %>
          [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label><%=(thisSan.getDataChiusura()!=null)?(" e scaduta d'ufficio per cambio titolarità"):("")%></font>]
        <%}} else {%>
          [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label><%=(thisSan.getDataChiusura()!=null)?(" e scaduta d'ufficio per cambio titolarità"):("")%></font>]
        <%}%>
      </td>
    </tr>
    
  <%}}%>

  <%}%>
      <%
        f = SequestriList.iterator();
   	   if ( f.hasNext() ) {
       int rowid = 0;
       i =0;
       while (f.hasNext()) {      
           i++;
           rowid = (rowid != 1?1:2);
           org.aspcfs.modules.sequestri.base.Ticket thisSan = (org.aspcfs.modules.sequestri.base.Ticket)f.next();
         	if (thisSan.getTipo_nc()==  org.aspcfs.modules.followup.base.Ticket.NC_GRAVI)
         	{
     %>
     <tr >
      <td  valign="top" nowrap>
      <b>Sequestro</b>
      </td>
      <%if(thisSan.getIdStabilimento() > 0) {%>
         <td  valign="top" nowrap>
   			<a href="<%=OrgDetails.getAction() %>Sequestri.do?command=TicketDetails&id=<%= thisSan.getId() %>&idNodo=<%=request.getAttribute("idNodo") %>&idC=<%=request.getAttribute("idC")%>&idNC=<%=TicketDetails.getId()%>&orgId=<%= thisSan.getOrgId()%>&stabId=<%= thisSan.getIdStabilimento()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisSan.getIdentificativo() %></a>
   		</td>
   		<% } else { %>
   		  <td  valign="top" nowrap>
   			<a href="<%=OrgDetails.getAction() %>Sequestri.do?command=TicketDetails&id=<%= thisSan.getId() %>&idNodo=<%=request.getAttribute("idNodo") %>&idC=<%=request.getAttribute("idC")%>&idNC=<%=TicketDetails.getId()%>&orgId=<%= thisSan.getOrgId()%>&stabId=<%= thisSan.getIdApiario()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisSan.getIdentificativo() %></a>
   		</td>
   		<% } %>
        <td valign="top">
         <% if(!User.getTimeZone().equals(thisSan.getAssignedDate())){%>
         <zeroio:tz timestamp="<%= thisSan.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
         <% } else { %>
         <zeroio:tz timestamp="<%= thisSan.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisSan.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
         <% } %>
   		</td>
   	</tr>
   	<tr >
         <td colspan="2" valign="top">
         
           <%= toHtml(thisSan.getProblemHeader()) %>&nbsp;
           <% if (thisSan.getClosed() == null) { 
        	   if(TicketDetails.isChiusura_attesa_esito()==true)
        	   {
        		   %>
        		   [<font color="orange">Pratica Temporaneamente Chiusa in Attesa di Esito di Campioni e/o Tamponi</font>]
        		   <%
        	   }
        	   else
        	   {
        		
        	   %>
             [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
           <%}} else {%>
             [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
           <%}%>
         </td>
       </tr>
       
     <%}}%>
   
     <%}%>
      <%
       f = ReatiList.iterator();
   	   if ( f.hasNext() ) {
       int rowid = 0;
       i =0;
       while (f.hasNext()) {      
           i++;
           rowid = (rowid != 1?1:2);
           org.aspcfs.modules.reati.base.Ticket thisSan = (org.aspcfs.modules.reati.base.Ticket)f.next();
         	if (thisSan.getTipo_nc()==  org.aspcfs.modules.followup.base.Ticket.NC_GRAVI)
         	{
     %>
     <tr >
      <td  valign="top" nowrap>
     <b> Notizia di Reato</b>
      </td>
      <% if(thisSan.getIdStabilimento() > 0) {%>
         <td  valign="top" nowrap>
   			<a href="<%=OrgDetails.getAction() %>Reati.do?command=TicketDetails&id=<%= thisSan.getId() %>&idNodo=<%=request.getAttribute("idNodo") %>&idC=<%=request.getAttribute("idC")%>&idNC=<%=TicketDetails.getId()%>&orgId=<%= thisSan.getOrgId()%>&stabId=<%= thisSan.getIdStabilimento()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisSan.getIdentificativo() %></a>
   		</td>
   		<% }else { %>
   		<td  valign="top" nowrap>
   			<a href="<%=OrgDetails.getAction() %>Reati.do?command=TicketDetails&id=<%= thisSan.getId() %>&idNodo=<%=request.getAttribute("idNodo") %>&idC=<%=request.getAttribute("idC")%>&idNC=<%=TicketDetails.getId()%>&orgId=<%= thisSan.getOrgId()%>&stabId=<%= thisSan.getIdApiario()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisSan.getIdentificativo() %></a>
   		</td>
   		<% } %>
        <td valign="top">
         <% if(!User.getTimeZone().equals(thisSan.getAssignedDate())){%>
         <zeroio:tz timestamp="<%= thisSan.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
         <% } else { %>
         <zeroio:tz timestamp="<%= thisSan.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisSan.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
         <% } %>
   		</td>
   	</tr>
   	<tr >
         <td colspan="2" valign="top">
         
           <%= toHtml(thisSan.getProblemHeader()) %>&nbsp;
           <% if (thisSan.getClosed() == null) { 
        	   if(TicketDetails.isChiusura_attesa_esito()==true)
        	   {
        		   %>
        		   [<font color="orange">Pratica Temporaneamente Chiusa in Attesa di Esito di Campioni e/o Tamponi</font>]
        		   <%
        	   }
        	   else
        	   {
        		
        	   %>
             [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
           <%}} else {%>
             [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
           <%}%>
         </td>
       </tr>
       
     <%}}%>
   
     <%}%>
      <%
       f = FollowupList.iterator();
   	   if ( f.hasNext() ) {
       int rowid = 0;
       i =0;
       while (f.hasNext()) {      
           i++;
           rowid = (rowid != 1?1:2);
           org.aspcfs.modules.followup.base.Ticket thisSan = (org.aspcfs.modules.followup.base.Ticket)f.next();
         	if (thisSan.getTipo_nc()==  org.aspcfs.modules.followup.base.Ticket.NC_GRAVI)
         	{
     %>
     <tr >
      <td  valign="top" nowrap>
      <b>Followup</b>
      </td>
        <% if(thisSan.getIdStabilimento() > 0) {%>
         <td  valign="top" nowrap>
   			<a href="<%=OrgDetails.getAction() %>Followup.do?command=TicketDetails&id=<%= thisSan.getId() %>&idNodo=<%=request.getAttribute("idNodo") %>&idC=<%=request.getAttribute("idC")%>&idNC=<%=TicketDetails.getId()%>&orgId=<%= thisSan.getOrgId()%>&stabId=<%= thisSan.getIdStabilimento()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisSan.getIdentificativo() %></a>
   		</td>
   		<%} else { %>
   		  <td  valign="top" nowrap>
   			<a href="<%=OrgDetails.getAction() %>Followup.do?command=TicketDetails&id=<%= thisSan.getId() %>&idNodo=<%=request.getAttribute("idNodo") %>&idC=<%=request.getAttribute("idC")%>&idNC=<%=TicketDetails.getId()%>&orgId=<%= thisSan.getOrgId()%>&stabId=<%= thisSan.getIdApiario()%><%= addLinkParams(request, "popup|popupType|actionId") %>"><%= thisSan.getIdentificativo() %></a>
   		</td>
   		<% } %>
        <td valign="top">
         <% if(!User.getTimeZone().equals(thisSan.getAssignedDate())){%>
         <zeroio:tz timestamp="<%= thisSan.getAssignedDate() %>" timeZone="<%= User.getTimeZone() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
         <% } else { %>
         <zeroio:tz timestamp="<%= thisSan.getAssignedDate() %>" dateOnly="true" timeZone="<%= thisSan.getAssignedDateTimeZone() %>" showTimeZone="false" default="&nbsp;"/>
         <% } %>
   		</td>
   	</tr>
   	<tr >
         <td colspan="2" valign="top">
         
           <%= toHtml(thisSan.getProblemHeader()) %>&nbsp;
           <% if (thisSan.getClosed() == null) {
        	   if(TicketDetails.isChiusura_attesa_esito()==true)
        	   {
        		   %>
        		   [<font color="orange">Pratica Temporaneamente Chiusa in Attesa di Esito di Campioni e/o Tamponi</font>]
        		   <%
        	   }
        	   else
        	   {
        		
        	   %>
             [<font color="green"><dhv:label name="project.open.lowercase1">open</dhv:label></font>]
           <%}} else {%>
             [<font color="red"><dhv:label name="project.closed.lowercase1">closed</dhv:label></font>]
           <%}%>
         </td>
       </tr>
       
     <%}}%>
   
     <%}%>
  	</table></div></td></tr> 
  
        </table>
   
      
    </td>
</tr>
        
        
        <% }
   
       
       if(CU.getTipoCampione()!=5){
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
        
        <%}
     %>



     

