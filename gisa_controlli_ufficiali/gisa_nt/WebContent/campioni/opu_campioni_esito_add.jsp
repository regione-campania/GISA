<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/interface/DwrPreaccettazione.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

 <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzionia.data_ispezione">Data</dhv:label>
    </td>
    <td>
    <input readonly type="text" id="estimatedResolutionDate" name="estimatedResolutionDate" size="10" />
		<a href="#" onClick="cal19.select(document.forms[0].estimatedResolutionDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
      <%= showAttribute(request, "estimatedResolutionDateError") %>
    </td>
  </tr>
  <dhv:include name="organization.source" none="true">
   <tr>
      <td name="esitoTampone1" id="esitoTampone1" nowrap class="formLabel">
        <dhv:label name="">Esito</dhv:label>
      </td>
    <td>
    <%EsitoCampione.setJsEvent("onChange=mostraFollowUP()"); %>
      <%= EsitoCampione.getHtmlSelect("EsitoTampone",-1) %>
      <input type="hidden" name="esitoTampone" value="<%=TicketDetails.getEsitoCampione()%>" >
    </td>
  </tr>
</dhv:include>

<tr id="followup" style="display:none" class="containerBody">
        <td valign="top" class="formLabel">
         Follow Up Positività
    	</td>
     <td >    
       
        <table >
         <tr>
           <td>
              <% ConseguenzePositivita.setJsEvent("onchange=\"javascript:abilitaNote(this.form);\"");%>
             <%= ConseguenzePositivita.getHtmlSelect("conseguenzePositivita",-1) %>
           </td>
           <td id="note_etichetta">Note per altro </td>
           <td id="note"><input type="text" name="noteEsito" id="noteEsito" value="<%= toHtmlValue(TicketDetails.getNoteEsito()) %>" size="60" maxlength="256" /></td>
         </tr>
         </table>
      </td>
     </tr>      
    

<tr>
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.azioni">Punteggio</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
   <select name="punteggio" onchange="">
   
   <option value="0">   0</option>
   <option value="1">   1 </option>
   <option value="7">  	7 </option>
   <option value="25">  25 </option>
   
   
   </select>
 
          </td>
         <td>
         &nbsp;
          <%= ResponsabilitaPositivita.getHtmlSelect("responsabilitaPositivita",-1) %> <%= showAttribute(request, "assignedDateError") %>
   
         </td>
         
        </tr>    
    </table>
    </td>
    </tr>
     
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Attivazione Sistema Allarme rapido</dhv:label>
    </td>
    <td>
       <input type="checkbox" name="allerta" id="allerta"  size="20" maxlength="256" onchange="abilitaCheckAllerta() " onclick="abilitaFlag()"/>
     </td>
  </tr>
  
  
   <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Segnalazione per Informazioni</dhv:label>
    </td>
    <td>
       <input type="checkbox" name="segnalazione" id="segnalazione"  size="20" maxlength="256" onchange="abilitaCheckAllerta()" onclick="abilitaFlag()"/>
     </td>
  </tr>


  
	<tr>
    	<td valign="top" class="formLabel">
      		<dhv:label name="sanzionia.azionia">Descrizione Risultato Esami</dhv:label>
    	</td>
    	<td>
      		<table border="0" cellspacing="0" cellpadding="0" class="empty">
        		<tr>
          			<td>
            			<textarea name="solution" cols="55" rows="8"><%= toString(TicketDetails.getSolution()) %></textarea>
          			</td>
          			<td valign="top">
            			<%= showAttribute(request, "problemError") %>
          			</td>
          		</tr>
			 </table>
    	</td>
	</tr>