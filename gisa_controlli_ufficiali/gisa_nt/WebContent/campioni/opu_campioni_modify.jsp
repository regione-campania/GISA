<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.modules.campioni.base.Analita"%>
<%@page import="org.aspcfs.modules.campioni.base.SpecieAnimali"%><jsp:useBean id="TipoCampione_istologico" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoCampione_sierologico" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Motivazione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Piani" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<dhv:include name="stabilimenti-sites" none="true">
 <%--  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>"> --%>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>
       <%=SiteIdList.getSelectedValue(TicketDetails
										.getSiteId())%>
          <input type="hidden" name="siteId" value="<%=TicketDetails.getSiteId()%>" >
      
      </td>
    </tr>
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
 </dhv:include>
	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
    
  </tr>
  <% }else{ %>
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
    <input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
  <% } %>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Identificativo C.U.</dhv:label>
    </td>
      <td>
      		<%= toHtmlValue(TicketDetails.getIdControlloUfficiale()) %>
      </td>
  </tr>	
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Codice Campione</dhv:label>
    </td>
   
     
      <td>
      		<%= toHtmlValue(TicketDetails.getIdentificativo()) %>
      </td>
    
  </tr>	
  
  
   <%if(TicketDetails.getMotivazione_campione()>0){ %>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Motivazione</dhv:label>
    </td>
    <td>
     
    
     <table class="noborder">
     <tr>
     <td>
    
     
      <%=Motivazione.getSelectedValue(TicketDetails.getMotivazione_campione()) %>
      
    
    </td><td id ="noteMotivazione">
    &nbsp; &nbsp; &nbsp; &nbsp;
     <%
     
     if(TicketDetails.getMotivazione_campione()==23){ %>
    Note <br> <%=TicketDetails.getNoteMotivazione() %>
    <%} %>
    
    </td>
    </tr></table>
  </tr>
  <%} %>

<%if (TicketDetails.getMotivazione_campione()==2)
	
{%>	
<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Piano di monitoraggio</dhv:label>
    </td>
    <td>
      <%= Piani.getSelectedValue(TicketDetails.getMotivazione_piano_campione()) %>
    </td>
</tr>

<%} %>
  
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Numero Verbale</dhv:label>
    </td>
    <td><%= toHtmlValue(TicketDetails.getLocation()) %></td>
    <td style="display:none">
	<%if((TicketDetails.getLocation() != "" && TicketDetails.getLocation() != null)){ %>
      <input type="text" readonly=readonly name="location" id="location" value="<%= toHtmlValue(TicketDetails.getLocation()) %>" size="20" maxlength="256" />
    <%}else{%>
          <input type="text" readonly=readonly name="location" id="location" value="" size="20" maxlength="256" />
    <%} %>
    </td>
  </tr>
 <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Prelievo</dhv:label>
      </td>
      <td>
      <zeroio:tz
				timestamp="<%= TicketDetails.getAssignedDate() %>" dateOnly="true"
				timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>"
				showTimeZone="false" default="&nbsp;" /> 
      </td>
      
      <td style="display:none">
      <input readonly type="text" id="assignedDate" name="assignedDate" size="10" value="<%= TicketDetails.getAssignedDate() %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].assignedDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
        <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
      </td>
    </tr>
	<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Matrice</dhv:label>
    </td>
    <td>
     <table class="noborder">
    
						<%
						HashMap<Integer,String> matrici= TicketDetails.getMatrici();
						Iterator<Integer> itMatrici = matrici.keySet().iterator();
						int i = 0 ;
						while(itMatrici.hasNext())
						{
							i++ ;
							int chiave = itMatrici.next();
							String descrizione = TicketDetails.getMatrici().get(chiave);
							out.print("<tr><td> "+i+") "+descrizione+"</td></tr>");
						}
						%>
			<tr><td><% if(TicketDetails.getNoteAlimenti()!=null && ! "".equals(TicketDetails.getNoteAlimenti()) ){%>NOTE : <%=TicketDetails.getNoteAlimenti() %> <%}%>
				</td></tr>
		</table>
    
   
    </td>
	</tr>
   <dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
        <dhv:label name="">Tipo di Analisi</dhv:label>
      </td>
    <td>
    <table class="noborder">
    		<input type="hidden" name="provvedimenti" value="<%=TicketDetails.getTipoCampione() %>">
					
				
						<%
						ArrayList<Analita> tipi= TicketDetails.getTipiCampioni();
						for(Analita a : tipi)
						{
							i++ ;
							int chiave = a.getIdAnalita();
							String descrizione = a.getDescrizione();
							out.print("<tr><td> "+i+") "+descrizione+"</td></tr>");
						}
						%>
			<tr><td><%="Note : "+TicketDetails.getNoteAnalisi() %></td></tr>
		</table>
    </td>
  </tr>
</dhv:include>


<%
if (TicketDetails.getCheck_circuito_ogm() != null && ! "".equals(TicketDetails.getCheck_circuito_ogm()))
{
%>
<tr>
<td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
      Circuito
      </td>
    <td>
   <%=TicketDetails.getCheck_circuito_ogm() %>
    </td>
    
</tr>
<%	
}
%>
<%
ArrayList<SpecieAnimali> listaSpecieSelezionate =  TicketDetails.getListaSpecieAnimali();
if (listaSpecieSelezionate.size()>0)
{
%>


<!-- GESTIONE CAMPI AGGIUNTIVI IN CASP DI PIANO PNAA MATRICE MANGIME -->
<tr>
<td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
      Mangime per piu specie
      </td>
    <td>
    <input type = "checkbox" disabled="disabled" <%if (TicketDetails.isCheck_specie_mangimi()){%>checked="checked"<%} %>>
    </td>
    
</tr>
<tr class="containerBody">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
      Specie Animali
      </td>
    <td>
    	<%
    	String categoriaSpecieBovina = "" ;
    	String categoriaSpecieBufalina= "" ;

    	for (SpecieAnimali specie : listaSpecieSelezionate)
    	{
    		out.print("- "+specie.getDescrizioneSpecie());
    		if (specie.getDescrizioneSpecie().equalsIgnoreCase("bovini"))
    		{
    			categoriaSpecieBovina = specie.getDescrizioneCategoria();
    		}
    		if (specie.getDescrizioneSpecie().equalsIgnoreCase("bufalini"))
    		{
    			categoriaSpecieBufalina = specie.getDescrizioneCategoria();
    		}
    		
    		
    	}
    	
    	%>	
    </td>
  </tr>
  <%
  if (!"".equals(categoriaSpecieBovina))
  {
	  %>
	  
<tr class="containerBody">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
      Categoria Specie Bovina
      </td>
    <td>
    <%=categoriaSpecieBovina %>
    </td>
    </tr>
	  <%
  }
  %>
  
    <%
  if (!"".equals(categoriaSpecieBufalina))
  {
	  %>
	  
<tr class="containerBody">
      <td name="tipoCampione1" id="tipoCampione1" nowrap class="formLabel">
      Categoria Specie Bufalina
      </td>
    <td>
    <%=categoriaSpecieBufalina %>
    </td>
    </tr>
	  <%
  }
  %>
<%	
}
%>
  
<dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td name="destinatarioCampione1" id="destinatarioCampione1" nowrap class="formLabel">
        <dhv:label name="">Laboratorio di Destinazione</dhv:label>
      </td>
      
      <td>
      <%= DestinatarioCampione.getSelectedValue(TicketDetails.getDestinatarioCampione()) %>
      </td>
      
    <td style="display:none">
      <%= DestinatarioCampione.getHtmlSelect("DestinatarioCampione",TicketDetails.getDestinatarioCampione()) %>
    </td>
  </tr>
</dhv:include>

  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Note</dhv:label>
    </td>
    <td><%= toString(TicketDetails.getProblem()) %>
    
    
      <table border="0" cellspacing="0" cellpadding="0" style="display:none" class="empty">
        <tr>
          <td>
            <textarea name="problem" cols="55" rows="8"><%= toString(TicketDetails.getProblem()) %></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
    </td>
	</tr>
<!--   <tr class="containerBody"> -->
<!--     <td valign="top" class="formLabel"> -->
<!--       <dhv:label name="sanzionia.importo">Codice Accettazione</dhv:label> -->
<!--     </td> -->
<%--     <td><%= toHtmlValue(TicketDetails.getCause()) %></td> --%>
<!--     <td> -->
<%--       <input type="text" name="cause" id="cause" value="<%= toHtmlValue(TicketDetails.getCause()) %>" size="20" maxlength="256" /> --%>
<!--     </td> -->
<!--   </tr> -->
<!--   <tr class="containerBody"> -->
<!--     <td nowrap class="formLabel"> -->
<!--       <dhv:label name="">Data Accettazione</dhv:label> -->
<!--     </td> -->
  
    
<!--     <td> -->
    
<!--     <input readonly type="text" id="dataAccettazione" name="dataAccettazione" size="10"  -->
<%-- 		value="<%= (TicketDetails.getDataAccettazione()==null)?(""):(getLongDate(TicketDetails.getDataAccettazione()))%>"/> --%>
<!-- 		<a href="#" onClick="cal19.select(document.forms[0].dataAccettazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19"> -->
<!-- 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a> -->
		
<!--     </td> -->
<!--   </tr> -->
  
  
  
  
     
