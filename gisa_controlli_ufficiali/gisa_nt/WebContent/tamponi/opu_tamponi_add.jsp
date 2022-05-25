<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>

<dhv:include name="stabilimenti-sites" none="true">
 
    <tr>
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>

       <%=SiteIdList.getSelectedValue(CU.getSiteId())%>
          <input type="hidden" name="siteId" value="<%=CU.getSiteId()%>" >
     
      </td>
    </tr>

  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
 </dhv:include>
	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
  <tr>
    <td class="formLabel">
      <dhv:label name="sanzioni.richiedente">Impresa</dhv:label>
    </td>
   
     
      <td>
        <%= "" %>
        <input type="hidden" name="stabId" value="<%=OrgDetails.getIdStabilimento()%>">
                <input type="hidden" name="idStabilimento" value="<%= OrgDetails.getIdStabilimento() %>">
        
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getIdAsl() %>" />
      </td>
    
  </tr>
  <% }else{ %>
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="stabId" value="<%= OrgDetails.getIdStabilimento() %>">
        <input type="hidden" name="idStabilimento" value="<%= OrgDetails.getIdStabilimento() %>">
    
    
  <% } %>

   <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Identificativo C.U.</dhv:label>
    <td>
      <%= (String)request.getAttribute("idControllo") %>
      <input type="hidden" name="idControlloUfficiale" id="idControlloUfficiale" value="<%= (String)request.getParameter("idControllo") %>">
      <input type="hidden" name="idC" id="idC" value="<%= (String)request.getParameter("idC") %>">
    </td>
  </tr>
  
  
  
  
     <tr class="containerBody" >
    <td valign="top" class="formLabel">
      Tamponi<br><br>
      
      (* In caso di selezione multipla tenere premuto il tasto Ctrl durante la Selezione)
      
    </td>
    
    
    <td>
     <table class="noborder">
    <tr>
    <td><b>Tampone 1 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
	 <tr><td align="middle" ><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle" ><input type="checkbox" value="1" name="check1_1" id="check1_1" onclick="showRow1_tampone1()" ></td></tr>
     <tr><td align="middle" ><label>Altro</label></td></tr>
     <tr><td align="middle" ><input type="checkbox" value="2" name="check2_1" id="check2_1" onclick="showRow1_tampone1()" ></td></tr>

     </table>
   </td>
     
    <%RicercaTamponi_1.setJsEvent("onChange=showEsitiTampone1('addticket')"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone1" style="display: none">
    
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi1",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca </br><%=RicercaTamponi_1.getHtmlSelect("RicercaTamponi_1",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta1"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone1_1"  style="display: none"><td>1. <input type="text" name="esitoTampone1_1"></td></tr>
    <tr id="esitoTampone1_2"  style="display: none"><td>2. <input type="text" name="esitoTampone1_2"></td></tr>
    <tr id="esitoTampone1_3"  style="display: none"><td>3. <input type="text" name="esitoTampone1_3"></td></tr>
    <tr id="esitoTampone1_4"  style="display: none"><td>4. <input type="text" name="esitoTampone1_4"></td></tr>
   <tr id="esitoTampone1_5"  style="display: none"><td>5. <input type="text" name="esitoTampone1_5"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone1" style="display: none">
    <%RicercaTamponi2_1.setJsEvent("onChange=showEsitiTipo2_tampone1('addticket')"); %>
   
    <td> Superficie Testata</br><textarea rows="5" cols="30" name="superfice1"></textarea> </td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_1.getHtmlSelect("RicercaTamponi2_1",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta12"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone1" style="display: none"><td>1. <input type="text" name="esito_1_tampone1"></td></tr>
    <tr id="esito_2_tampone1" style="display: none"><td>2. <input type="text" name="esito_2_tampone1"></td></tr>
    <tr id="esito_3_tampone1" style="display: none"><td>3. <input type="text" name="esito_3_tampone1"></td></tr>
    <tr id="esito_4_tampone1" style="display: none"><td>4. <input type="text" name="esito_4_tampone1"></td></tr>
    <tr id="esito_5_tampone1" style="display: none"><td>5. <input type="text" name="esito_5_tampone1"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    <tr id="tampone2" style="display: none">
    <td><b>Tampone 2 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label><d>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" value="1" name="check1_2" id="check1_2" onclick="showRow1_tampone2()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" value="2" name="check2_2" id="check2_2" onclick="showRow1_tampone2()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_2.setJsEvent("onChange=showEsitiTampone2('addticket')"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone2" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi2",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_2.getHtmlSelect("RicercaTamponi_2",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta2"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone2_1" style="display: none"><td>1. <input type="text" name="esitoTampone2_1"></td></tr>
    <tr id="esitoTampone2_2" style="display: none"><td>2. <input type="text" name="esitoTampone2_2"></td></tr>
    <tr id="esitoTampone2_3" style="display: none"><td>3. <input type="text" name="esitoTampone2_3"></td></tr>
    <tr id="esitoTampone2_4" style="display: none"><td>4. <input type="text" name="esitoTampone2_4"></td></tr>
        <tr id="esitoTampone2_5" style="display: none"><td>5. <input type="text" name="esitoTampone2_5"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone2" style="display: none">
    <%RicercaTamponi2_2.setJsEvent("onChange=showEsitiTipo2_tampone2('addticket')"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice2"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_2.getHtmlSelect("RicercaTamponi2_2",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta21"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone2" style="display: none"><td>1. <input type="text" name="esito_1_tampone2"></td></tr>
    <tr id="esito_2_tampone2" style="display: none"><td>2. <input type="text" name="esito_2_tampone2"></td></tr>
    <tr id="esito_3_tampone2" style="display: none"><td>3. <input type="text" name="esito_3_tampone2"></td></tr>
    <tr id="esito_4_tampone2" style="display: none"><td>4. <input type="text" name="esito_4_tampone2"></td></tr>
        <tr id="esito_5_tampone2" style="display: none"><td>5. <input type="text" name="esito_5_tampone2"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
     <tr id="tampone3" style="display: none">
    <td><b>Tampone 3 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" value="1" name="check1_3" id="check1_3" onclick="showRow1_tampone3()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" value="2" name="check2_3" id="check2_3" onclick="showRow1_tampone3()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_3.setJsEvent("onChange=showEsitiTampone3('addticket')"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone3" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi3",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_3.getHtmlSelect("RicercaTamponi_3",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta3"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone3_1" style="display: none"><td>1. <input type="text" name="esitoTampone3_1"></td></tr>
    <tr id="esitoTampone3_2" style="display: none"><td>2. <input type="text" name="esitoTampone3_2"></td></tr>
    <tr id="esitoTampone3_3" style="display: none"><td>3. <input type="text" name="esitoTampone3_3"></td></tr>
    <tr id="esitoTampone3_4" style="display: none"><td>4. <input type="text" name="esitoTampone3_4"></td></tr>
        <tr id="esitoTampone3_5" style="display: none"><td>5. <input type="text" name="esitoTampone3_5"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone3" style="display: none">
    <%RicercaTamponi2_3.setJsEvent("onChange=showEsitiTipo2_tampone3('addticket')"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice3"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_3.getHtmlSelect("RicercaTamponi2_3",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta31"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone3" style="display: none"><td>1. <input type="text" name="esito_1_tampone3"></td></tr>
    <tr id="esito_2_tampone3" style="display: none"><td>2. <input type="text" name="esito_2_tampone3"></td></tr>
    <tr id="esito_3_tampone3" style="display: none"><td>3. <input type="text" name="esito_3_tampone3"></td></tr>
    <tr id="esito_4_tampone3" style="display: none"><td>4. <input type="text" name="esito_4_tampone3"></td></tr>
        <tr id="esito_5_tampone3" style="display: none"><td>5. <input type="text" name="esito_5_tampone3"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
     <tr id="tampone4" style="display: none">
    <td><b>Tampone 4 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check1_4" value="1" id="check1_4" onclick="showRow1_tampone4()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check2_4" value="2" id="check2_4" onclick="showRow1_tampone4()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_4.setJsEvent("onChange=showEsitiTampone4('addticket')"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone4" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi4",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_4.getHtmlSelect("RicercaTamponi_4",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta4"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone4_1" style="display: none"><td>1. <input type="text" name="esitoTampone4_1"></td></tr>
    <tr id="esitoTampone4_2" style="display: none"><td>2. <input type="text" name="esitoTampone4_2"></td></tr>
    <tr id="esitoTampone4_3" style="display: none"><td>3. <input type="text" name="esitoTampone4_3"></td></tr>
    <tr id="esitoTampone4_4" style="display: none"><td>4. <input type="text" name="esitoTampone4_4"></td></tr>
        <tr id="esitoTampone4_5" style="display: none"><td>5. <input type="text" name="esitoTampone4_5"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone4" style="display: none">
    <%RicercaTamponi2_4.setJsEvent("onChange=showEsitiTipo2_tampone4('addticket')"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice4"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_4.getHtmlSelect("RicercaTamponi2_4",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta41"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone4" style="display: none"><td>1. <input type="text" name="esito_1_tampone4"> </td></tr>
    <tr id="esito_2_tampone4" style="display: none"><td>2. <input type="text" name="esito_2_tampone4"></td></tr>
    <tr id="esito_3_tampone4" style="display: none"><td>3. <input type="text" name="esito_3_tampone4"></td></tr>
    <tr id="esito_4_tampone4" style="display: none"><td>4. <input type="text" name="esito_4_tampone4"></td></tr>
        <tr id="esito_5_tampone4" style="display: none"><td>5. <input type="text" name="esito_5_tampone4"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
     <tr id="tampone5" style="display: none">
    <td><b>Tampone 5 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check1_5" value="1" id="check1_5" onclick="showRow1_tampone5()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check2_5" value="2" id="check2_5" onclick="showRow1_tampone5()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_5.setJsEvent("onChange=showEsitiTampone5('addticket')"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone5" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi5",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_5.getHtmlSelect("RicercaTamponi_5",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta5"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone5_1" style="display: none"><td>1. <input type="text" name="esitoTampone5_1"></td></tr>
    <tr id="esitoTampone5_2" style="display: none"><td>2. <input type="text" name="esitoTampone5_2"></td></tr>
    <tr id="esitoTampone5_3" style="display: none"><td>3. <input type="text" name="esitoTampone5_3"></td></tr>
    <tr id="esitoTampone5_4" style="display: none"><td>4. <input type="text" name="esitoTampone5_4"></td></tr>
        <tr id="esitoTampone5_5" style="display: none"><td>5. <input type="text" name="esitoTampone5_5"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone5" style="display: none">
    <%RicercaTamponi2_5.setJsEvent("onChange=showEsitiTipo2_tampone5('addticket')"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice5"></textarea> </td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_5.getHtmlSelect("RicercaTamponi2_5",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta51"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone5" style="display: none"><td>1. <input type="text" name="esito_1_tampone5"></td></tr>
    <tr id="esito_2_tampone5" style="display: none"><td>2. <input type="text" name="esito_2_tampone5"></td></tr>
    <tr id="esito_3_tampone5" style="display: none"><td>3. <input type="text" name="esito_3_tampone5"></td></tr>
    <tr id="esito_4_tampone5" style="display: none"><td>4. <input type="text" name="esito_4_tampone5"></td></tr>
        <tr id="esito_5_tampone5" style="display: none"><td>5. <input type="text" name="esito_5_tampone5"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
     <tr id="tampone6" style="display: none">
    <td><b>Tampone 6 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check1_6" value="1" id="check1_6" onclick="showRow1_tampone6()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check2_6" value="2" id="check2_6" onclick="showRow1_tampone6()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_6.setJsEvent("onChange=showEsitiTampone6('addticket')"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone6" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi6",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_6.getHtmlSelect("RicercaTamponi_6",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta6"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone6_1" style="display: none"><td>1. <input type="text" name="esitoTampone6_1"></td></tr>
    <tr id="esitoTampone6_2" style="display: none"><td>2. <input type="text" name="esitoTampone6_2"></td></tr>
    <tr id="esitoTampone6_3" style="display: none"><td>3. <input type="text" name="esitoTampone6_3"></td></tr>
    <tr id="esitoTampone6_4" style="display: none"><td>4. <input type="text" name="esitoTampone6_4"></td></tr>
        <tr id="esitoTampone6_5" style="display: none"><td>5. <input type="text" name="esitoTampone6_5"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone6" style="display: none">
    <%RicercaTamponi2_6.setJsEvent("onChange=showEsitiTipo2_tampone6('addticket')"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice6"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_6.getHtmlSelect("RicercaTamponi2_6",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta61"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone6" style="display: none"><td>1. <input type="text" name="esito_1_tampone6"></td></tr>
    <tr id="esito_2_tampone6" style="display: none"><td>2. <input type="text" name="esito_2_tampone6">></td></tr>
    <tr id="esito_3_tampone6" style="display: none"><td>3. <input type="text" name="esito_3_tampone6">></td></tr>
    <tr id="esito_4_tampone6" style="display: none"><td>4. <input type="text" name="esito_4_tampone6">></td></tr>
        <tr id="esito_5_tampone6" style="display: none"><td>5. <input type="text" name="esito_5_tampone6">></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
    
     <tr id="tampone7" style="display: none">
    <td><b>Tampone 7 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check1_7" value="1" id="check1_7" onclick="showRow1_tampone7()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check2_7" value="2" id="check2_7" onclick="showRow1_tampone7()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_7.setJsEvent("onChange=showEsitiTampone7('addticket')"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone7" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi7",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_7.getHtmlSelect("RicercaTamponi_7",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta7"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone7_1" style="display: none"><td>1. <input type="text" name="esitoTampone7_1"></td></tr>
    <tr id="esitoTampone7_2" style="display: none"><td>2. <input type="text" name="esitoTampone7_2"></td></tr>
    <tr id="esitoTampone7_3" style="display: none"><td>3. <input type="text" name="esitoTampone7_3"></td></tr>
    <tr id="esitoTampone7_4" style="display: none"><td>4. <input type="text" name="esitoTampone7_4"></td></tr>
        <tr id="esitoTampone7_5" style="display: none"><td>5. <input type="text" name="esitoTampone7_5"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone7" style="display: none">
    <%RicercaTamponi2_7.setJsEvent("onChange=showEsitiTipo2_tampone7('addticket')"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice7"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_7.getHtmlSelect("RicercaTamponi2_7",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta71"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone7" style="display: none"><td>1. <input type="text" name="esito_1_tampone7"></td></tr>
    <tr id="esito_2_tampone7" style="display: none"><td>2. <input type="text" name="esito_2_tampone7"></td></tr>
    <tr id="esito_3_tampone7" style="display: none"><td>3. <input type="text" name="esito_3_tampone7"></td></tr>
    <tr id="esito_4_tampone7" style="display: none"><td>4. <input type="text" name="esito_4_tampone7"></td></tr>
        <tr id="esito_5_tampone7" style="display: none"><td>5. <input type="text" name="esito_5_tampone7"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
    
     <tr id="tampone8" style="display: none">
    <td><b>Tampone 8 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check1_8" value="1" id="check1_8" onclick="showRow1_tampone8()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check2_8" value="2" id="check2_8" onclick="showRow1_tampone8()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_8.setJsEvent("onChange=showEsitiTampone8('addticket')"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone8" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi8",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_8.getHtmlSelect("RicercaTamponi_8",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta8"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone8_1" style="display: none"><td>1. <input type="text" name="esitoTampone8_1"></td></tr>
    <tr id="esitoTampone8_2" style="display: none"><td>2. <input type="text" name="esitoTampone8_2"></td></tr>
    <tr id="esitoTampone8_3" style="display: none"><td>3. <input type="text" name="esitoTampone8_3"></td></tr>
    <tr id="esitoTampone8_4" style="display: none"><td>4. <input type="text" name="esitoTampone8_4"></td></tr>
        <tr id="esitoTampone8_5" style="display: none"><td>5. <input type="text" name="esitoTampone8_5"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone8" style="display: none">
    <%RicercaTamponi2_8.setJsEvent("onChange=showEsitiTipo2_tampone8('addticket')"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice8"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_8.getHtmlSelect("RicercaTamponi2_8",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta81"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone8" style="display: none"><td>1. <input type="text" name="esito_1_tampone8"></td></tr>
    <tr id="esito_2_tampone8" style="display: none"><td>2. <input type="text" name="esito_2_tampone8"></td></tr>
    <tr id="esito_3_tampone8" style="display: none"><td>3. <input type="text" name="esito_3_tampone8"></td></tr>
    <tr id="esito_4_tampone8" style="display: none"><td>4. <input type="text" name="esito_4_tampone8"></td></tr>
        <tr id="esito_5_tampone8" style="display: none"><td>5. <input type="text" name="esito_5_tampone8"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
    
     <tr id="tampone9" style="display: none">
    <td><b>Tampone 9 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05 </label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check1_9" value="1" id="check1_9" onclick="showRow1_tampone9()"></td></tr>
     <tr><td align="middle"><label>Altro </label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check2_9" value="2" id="check2_9" onclick="showRow1_tampone9()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_9.setJsEvent("onChange=showEsitiTampone9('addticket')"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone9" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi9",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca </br><%=RicercaTamponi_9.getHtmlSelect("RicercaTamponi_9",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta9"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone9_1" style="display: none"><td>1. <input type="text" name="esitoTampone9_1"></td></tr>
    <tr id="esitoTampone9_2" style="display: none"><td>2. <input type="text" name="esitoTampone9_2"></td></tr>
    <tr id="esitoTampone9_3" style="display: none"><td>3. <input type="text" name="esitoTampone9_3"></td></tr>
    <tr id="esitoTampone9_4" style="display: none"><td>4. <input type="text" name="esitoTampone9_4"></td></tr>
        <tr id="esitoTampone9_5" style="display: none"><td>5. <input type="text" name="esitoTampone9_5"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone9" style="display: none">
    <%RicercaTamponi2_9.setJsEvent("onChange=showEsitiTipo2_tampone9('addticket')"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice9"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_9.getHtmlSelect("RicercaTamponi2_9",TicketDetails.getRicercaTamponi()) %>;</td>
    <td>
    <label id="etichetta91"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone9" style="display: none"><td>1. <input type="text" name="esito_1_tampone9"></td></tr>
    <tr id="esito_2_tampone9" style="display: none"><td>2. <input type="text" name="esito_2_tampone9"></td></tr>
    <tr id="esito_3_tampone9" style="display: none"><td>3. <input type="text" name="esito_3_tampone9"></td></tr>
    <tr id="esito_4_tampone9" style="display: none"><td>4. <input type="text" name="esito_4_tampone9"></td></tr>
        <tr id="esito_5_tampone9" style="display: none"><td>5. <input type="text" name="esito_5_tampone9"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
    
    
     <tr id="tampone10" style="display: none">
    <td><b>Tampone 10 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check1_10" value="1" id="check1_10" onclick="showRow1_tampone10()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle"><input type="checkbox" name="check2_10" value="2" id="check2_10" onclick="showRow1_tampone10()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_10.setJsEvent("onChange=showEsitiTampone10('addticket')"); %>
    <td>
    <table cellpadding="15">
    <tr valign="top" id="row1tampone10" style="display: none">
   
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi10",TicketDetails.getTamponi()) %></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi_10.getHtmlSelect("RicercaTamponi_10",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    <tr id="esitoTampone10_1" style="display: none"><td>1. <input type="text" name="esitoTampone10_1"></td></tr>
    <tr id="esitoTampone10_2" style="display: none"><td>2. <input type="text" name="esitoTampone10_2"></td></tr>
    <tr id="esitoTampone10_3" style="display: none"><td>3. <input type="text" name="esitoTampone10_3"></td></tr>
    <tr id="esitoTampone10_4" style="display: none"><td>4. <input type="text" name="esitoTampone10_4"></td></tr>
        <tr id="esitoTampone10_5" style="display: none"><td>5. <input type="text" name="esitoTampone10_5"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone10" style="display: none">
    <%RicercaTamponi2_10.setJsEvent("onChange=showEsitiTipo2_tampone10('addticket')"); %>
   
    <td>Superficie Testata</br><textarea rows="5" cols="30" name="superfice10"></textarea></td>
    <td>Tipo di Ricerca</br><%=RicercaTamponi2_10.getHtmlSelect("RicercaTamponi2_10",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta101"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone10" style="display: none"><td>1. <input type="text" name="esito_1_tampone10"></td></tr>
    <tr id="esito_2_tampone10" style="display: none"><td>2. <input type="text" name="esito_2_tampone10"></td></tr>
    <tr id="esito_3_tampone10" style="display: none"><td>3. <input type="text" name="esito_3_tampone10"></td></tr>
    <tr id="esito_4_tampone10" style="display: none"><td>4. <input type="text" name="esito_4_tampone10"></td></tr>
        <tr id="esito_5_tampone10" style="display: none"><td>5. <input type="text" name="esito_5_tampone10"></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    

    
    </table>
    
    </td>
   </tr>

  
  <tr class="containerBody" >
    <td valign="top" class="formLabel">
      <dhv:label name="">Numero Verbale</dhv:label>
    </td>
    <td>
    <%if(TicketDetails.getLocation() != "" && TicketDetails.getLocation() != null){ %>
      <input type="text" name="location" id="location" value="<%= toHtmlValue(TicketDetails.getLocation()) %>" size="20" maxlength="256" /><font color="red">*</font>
    <%}else{%>
          <input type="text" name="location" id="location" value="" size="20" maxlength="256" /><font color="red">*</font>
    <%} %>
    </td>
  </tr>
   <% String dataC = request.getAttribute("dataC").toString(); %>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="sanzionia.data_richiesta">Data Prelievo</dhv:label>
    </td>
    <td>
    
    <input readonly type="text" id="assignedDate" name="assignedDate" size="10" value = "<%=toDateString(TicketDetails.getAssignedDate()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].assignedDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
    
      <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
    </td>
  </tr>
  <dhv:include name="organization.source" none="true">
   <tr>
      <td name="destinatarioTampone1" id="destinatarioTampone1" nowrap class="formLabel">
        <dhv:label name="">Laboratorio di Destinazione</dhv:label>
      </td>
    <td>
      <%= DestinatarioTampone.getHtmlSelect("DestinatarioTampone",TicketDetails.getDestinatarioTampone()) %>
    <font color="red">*</font>
    </td>
  </tr>
</dhv:include>
<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Accettazione</dhv:label>
    </td>
    <td>
    
     <input readonly type="text" id="dataAccettazione" name="dataAccettazione" size="10" value = "<%=toDateString(TicketDetails.getDataAccettazione()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataAccettazione,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
    
    
    
    </td>
  </tr>
  
    <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Codice Accettazione</dhv:label>
    </td>
    <td>
      <input type="text" name="cause" id="cause" value="<%= toHtmlValue(TicketDetails.getCause()) %>" size="20" maxlength="256" />
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
            <select name="punteggio">
            <option value="0">   0 </option>
            <option value="2">   2 </option>
            <option value="4">   4 </option>
            <option value="6">   6 </option>
            <option value="8">   8 </option>
            <option value="10"> 10 </option> 
            </select>
            (Punteggio a scelta dell'Ispettore in funzione della gravità della n.c. rilevata)
          </td>
         
        </tr>
   </table></td></tr>
    
    
  <tr>
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Note</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
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
  