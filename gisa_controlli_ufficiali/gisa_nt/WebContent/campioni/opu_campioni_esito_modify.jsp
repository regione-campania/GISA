<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
 
 <jsp:useBean id="TicketDetails" class="org.aspcfs.modules.campioni.base.Ticket" scope="request"/>
 <%@page import="org.aspcfs.utils.web.LookupList" %>

<jsp:useBean id="ConseguenzePositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ResponsabilitaPositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EsitoCampione" class="org.aspcfs.utils.web.LookupList" scope="request"/>
 
 <SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();



	
</SCRIPT>

<%
if (request.getAttribute("EsitoAggiornato")!=null)
{
%>
<script>

window.opener.location.reload();
window.close();
</script>
<%	
}
%>
 <script language="JavaScript" TYPE="text/javascript" SRC="javascript/controlli_campioni.js"></script>
 <%@ include file="../initPage.jsp" %>
 <form name="details" id="details" method = "post" action="Campioni.do?command=UpdateTicketEsito&auto-populate=true">
 <input type = "hidden" name = "id_analita" value="<%=request.getAttribute("IdAnalita") %>">
  <input type = "hidden" name = "id" value="<%=TicketDetails.getId() %>">
    <input type = "hidden" name = "idcontrolloufficiale" value="<%=TicketDetails.getIdControlloUfficiale() %>">
  
 
 <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Esito Campione</dhv:label></strong>
    </th>
	</tr>
 <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="">Data</dhv:label>
      </td>
      <td>

       <input readonly type="text" id="esito_data" name="esito_data" size="10" value = ""/>

		<a href="#" onClick="cal19.select(document.forms[0].esito_data,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      
        <%= showAttribute(request, "esito_data") %><FONT color="red">*</FONT>
      </td>
    </tr>
  <dhv:include name="organization.source" none="true">
  
   <tr class="containerBody">
      <td name="esitoCampione1" id="esitoCampione1" nowrap class="formLabel">
        <dhv:label name="">Esito</dhv:label>
      </td>
    <td>
      <% EsitoCampione.setJsEvent("onChange=\"javascript:mostraDescrizioneNonAccettato();javascript:nascondiResponsabilita();\""); %> 
      <%= EsitoCampione.getHtmlSelect("esito_id",-1) %><FONT color="red">*</FONT>
      <input type="hidden" name="esitoCampione" value="-1" >
    </td>
  </tr>
  
 
   <tr class="containerBody" id="campo_desc_non_accettato" style="display:none">
  <td  valign="top" class="formLabel" >
      Motivo Non Accettazione
    </td>
    <td>
         <textarea id="txt_desc_non_accettato" name="esito_motivazione_respingimento" cols="55" rows="8" ></textarea>
    </td>
    </tr>
  
  
</dhv:include>
<tr class="containerBody">
      <td name="punteggio" id="punteggio" nowrap class="formLabel">
        <dhv:label name="">Punteggio</dhv:label>
      </td>
    <td>
    <table>
    <tr><td>
     <select name="esito_punteggio" onchange="impostaResponsabilita('details')">
   
   
   <option value="0"  >   0</option>
   <option value="1" >   1 </option>
   <option value="7" > 7 </option>

   <option value="50"> 50 </option>
   
   </select>
   
    </td>
  <td id="campo_responsabilita">
  &nbsp;
  <%= ResponsabilitaPositivita.getHtmlSelect("esito_responsabilita_positivita",-1) %>
  
  </td>
  </tr></table>
  </tr>
  
  
    
     
     
      <!--responsabilità positività -->
  
    
    <!-- allerta -->
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Attivazione Sistema Allarme rapido</dhv:label>
    </td>
    <td>
    
         <input type="checkbox" name="esito_allarme_rapido" id="esito_allarme_rapido" size="20" maxlength="256" onchange="abilitaCheckAllerta('details')" onclick="abilitaFlag('details')"/>
       
     </td>
  </tr>
  
  
  
    <!-- allerta -->
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Segnalazione per Informazioni</dhv:label>
    </td>
    <td>
         <input type="checkbox" name="esito_segnalazione_informazioni" id="esito_segnalazione_informazioni" size="20" maxlength="256" onchange="abilitaCheckAllerta('details')" onclick="abilitaFlag('details')" />
     </td>
  </tr>
  <input type="hidden" name="idControlloUfficiale" value="<%=TicketDetails.getIdControlloUfficiale() %>">
    <input type="hidden" name="idAnalita" value="<%=request.getAttribute("idAnalita")%>">
  
  
<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.azioni">Descrizione Risultato Esame</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="esito_note_esame" cols="55" rows="8" onkeyup="this.value = this.value.replace(/[\W_]+/g,'')"></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
        </table>
        </td>
        </tr>
        
        </table>
        <input type = "button" value = "Aggiorna" name= "aggiorna" id="aggiorna" onclick="javascript:checkEsitoForm(this)">
        </form>