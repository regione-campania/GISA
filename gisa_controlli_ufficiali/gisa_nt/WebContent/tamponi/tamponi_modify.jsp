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

<% String esito1="";
	  String esito2="";
	  String esito3="";
	  String esito4="";
	  String esito5="";
	  
	  String esito1_t2="";
	  String esito2_t2="";
	  String esito3_t2="";
	  String esito4_t2="";
	  String esito5_t2="";
	  
	  String esito1_t3="";
	  String esito2_t3="";
	  String esito3_t3="";
	  String esito4_t3="";
	  String esito5_t3="";
	  
	  String esito1_t4="";
	  String esito2_t4="";
	  String esito3_t4="";
	  String esito4_t4="";
	  String esito5_t4="";
	  
	  String esito1_t5="";
	  String esito2_t5="";
	  String esito3_t5="";
	  String esito4_t5="";
	  String esito5_t5="";
	  
	  String esito1_t6="";
	  String esito2_t6="";
	  String esito3_t6="";
	  String esito4_t6="";
	  String esito5_t6="";
	  
	  String esito1_t7="";
	  String esito2_t7="";
	  String esito3_t7="";
	  String esito4_t7="";
	  String esito5_t7="";
	  
	  String esito1_t8="";
	  String esito2_t8="";
	  String esito3_t8="";
	  String esito4_t8="";
	  String esito5_t8="";
	  
	  String esito1_t9="";
	  String esito2_t9="";
	  String esito3_t9="";
	  String esito4_t9="";
	  String esito5_t9="";
	  
	  String esito1_t10="";
	  String esito2_t10="";
	  String esito3_t10="";
	  String esito4_t10="";
	  String esito5_t10="";
	
  
  
  
   
     Tampone t1=TicketDetails.getListaTamponi().get(1);
   Tampone t2=TicketDetails.getListaTamponi().get(2);
   Tampone t3=TicketDetails.getListaTamponi().get(3);
   Tampone t4=TicketDetails.getListaTamponi().get(4);
   Tampone t5=TicketDetails.getListaTamponi().get(5);
   Tampone t6=TicketDetails.getListaTamponi().get(6);
   Tampone t7=TicketDetails.getListaTamponi().get(7);
   Tampone t8=TicketDetails.getListaTamponi().get(8);
   Tampone t9=TicketDetails.getListaTamponi().get(9);
   Tampone t10=TicketDetails.getListaTamponi().get(10);
  
  
 

    
  
  
    
     
   
 Iterator<Integer> it=t1.getRicerca().keySet().iterator();
  while(it.hasNext()){
  int kiaveRicerca=it.next();
  if(kiaveRicerca==1){
	  if(t1.getEsiti().get(kiaveRicerca)!=null)
	  esito1=t1.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t1.getEsiti().get(kiaveRicerca)!=null)
	  esito2=t1.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t1.getEsiti().get(kiaveRicerca)!=null)
	  esito3=t1.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t1.getEsiti().get(kiaveRicerca)!=null)
	  esito4=t1.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==5){
	  if(t1.getEsiti().get(kiaveRicerca)!=null)
	  esito5=t1.getEsiti().get(kiaveRicerca);
  }
  }
  
  if(t2!=null){
  Iterator<Integer> it2=t2.getRicerca().keySet().iterator();
  while(it2.hasNext()){
  int kiaveRicerca=it2.next();
  if(kiaveRicerca==1){
	  if(t2.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t2=t2.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t2.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t2=t2.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t2.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t2=t2.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t2.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t2=t2.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==5){
	  if(t2.getEsiti().get(kiaveRicerca)!=null)
	  esito5_t2=t2.getEsiti().get(kiaveRicerca);
  }
  }}
  
  if(t3!=null){
  
  Iterator<Integer> it3=t3.getRicerca().keySet().iterator();
  while(it3.hasNext()){
  int kiaveRicerca=it3.next();
  if(kiaveRicerca==1){
	  if(t3.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t3=t1.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t3.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t3=t3.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t3.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t3=t3.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t3.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t3=t3.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==5){
	  if(t3.getEsiti().get(kiaveRicerca)!=null)
	  esito5_t3=t3.getEsiti().get(kiaveRicerca);
  }
  }
  }
  if(t4!=null){
  
  Iterator<Integer> it4=t4.getRicerca().keySet().iterator();
  while(it4.hasNext()){
  int kiaveRicerca=it4.next();
  if(kiaveRicerca==1){
	  if(t4.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t4=t4.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t4.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t4=t4.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t4.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t4=t4.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t4.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t4=t4.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==5){
	  if(t4.getEsiti().get(kiaveRicerca)!=null)
	  esito5_t4=t4.getEsiti().get(kiaveRicerca);
  }
  }
  }
  if(t5!=null){
	  
  Iterator<Integer> it5=t5.getRicerca().keySet().iterator();
  while(it5.hasNext()){
  int kiaveRicerca=it5.next();
  if(kiaveRicerca==1){
	  if(t5.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t5=t5.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t5.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t5=t5.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t5.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t5=t5.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t5.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t5=t5.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==5){
	  if(t5.getEsiti().get(kiaveRicerca)!=null)
	  esito5_t5=t5.getEsiti().get(kiaveRicerca);
  }
  }}
  
	  if(t6!=null){
  Iterator<Integer> it6=t6.getRicerca().keySet().iterator();
  while(it6.hasNext()){
  int kiaveRicerca=it6.next();
  if(kiaveRicerca==1){
	  if(t6.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t6=t6.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t6.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t6=t6.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t6.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t6=t6.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t6.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t6=t6.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==5){
	  if(t6.getEsiti().get(kiaveRicerca)!=null)
	  esito5_t6=t6.getEsiti().get(kiaveRicerca);
  }
  }}
	  
	  if(t7!=null){
  
  
  Iterator<Integer> it7=t7.getRicerca().keySet().iterator();
  while(it7.hasNext()){
  int kiaveRicerca=it7.next();
  if(kiaveRicerca==1){
	  if(t7.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t7=t7.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t7.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t7=t7.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t7.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t7=t7.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t7.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t7=t7.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==5){
	  if(t7.getEsiti().get(kiaveRicerca)!=null)
	  esito5_t7=t7.getEsiti().get(kiaveRicerca);
  }
  }
	  }
	  if(t8!=null){
  
  Iterator<Integer> it8=t8.getRicerca().keySet().iterator();
  while(it8.hasNext()){
  int kiaveRicerca=it8.next();
  if(kiaveRicerca==1){
	  if(t8.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t8=t8.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t8.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t8=t8.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t8.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t8=t8.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t8.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t8=t8.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==5){
	  if(t8.getEsiti().get(kiaveRicerca)!=null)
	  esito5_t8=t8.getEsiti().get(kiaveRicerca);
  }
  }}
	  if(t9!=null){
  
  
  Iterator<Integer> it9=t9.getRicerca().keySet().iterator();
  while(it9.hasNext()){
  int kiaveRicerca=it9.next();
  if(kiaveRicerca==1){
	  if(t9.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t9=t9.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t9.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t9=t9.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t9.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t9=t9.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t9.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t9=t9.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==5){
	  if(t9.getEsiti().get(kiaveRicerca)!=null)
	  esito5_t9=t9.getEsiti().get(kiaveRicerca);
  }
  }}
	  
	  if(t10!=null){
  
  
  Iterator<Integer> it10=t10.getRicerca().keySet().iterator();
  while(it10.hasNext()){
  int kiaveRicerca=it10.next();
  if(kiaveRicerca==1){
	  if(t10.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t10=t10.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t10.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t10=t10.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t10.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t10=t10.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t10.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t10=t10.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==5){
	  if(t10.getEsiti().get(kiaveRicerca)!=null)
	  esito5_t10=t10.getEsiti().get(kiaveRicerca);
  }
  }}
  
  
  %>
  
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
<%--</dhv:evaluate>  --%>
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
  <% }
	
	%>
    
  
   <tr class="containerBody" >
    <td valign="top" class="formLabel">
      Tamponi<br><br>
      (* In caso di selezione multipla tenere premuto il tasto Ctrl durante la Selezione)
    </td>
    <td>
    <table class="noborder">
     <tr>
    <td><b>Tampone 1 </b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td align="middle" ><input type="checkbox" value="1" name="check1_1" id="check1_1"  <%if(t1!=null){ if(TicketDetails.getListaTamponi().get(1).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone1()" ></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td align="middle" ><input type="checkbox" value="2" name="check2_1" id="check2_1"  <%if(t1!=null){ if(TicketDetails.getListaTamponi().get(1).getTipo()==2){ %> checked="checked" <%}} %> onclick="showRow1_tampone1()" ></td></tr>
     </table>
   </td>
    <%RicercaTamponi_1.setJsEvent("onChange=showEsitiTampone1('details')"); %>
    <td>
    <table>
    <tr valign="top" id="row1tampone1" style="display: none">
   <%
   int idsuperfice=-1;
   String superfice="";
   if(t1!=null){
	   idsuperfice=t1.getSuperfice();
	superfice=t1.getSuperficeStringa();   
   }
	   %>
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi1",idsuperfice)  %></td>
    <td>Tipo di Ricerca<br><%=RicercaTamponi_1.getHtmlSelect("RicercaTamponi_1",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esitiTampone1">
    <label id="etichetta1"  style="display: none">Esiti</label>
   <tr id="esitoTampone1_1" style="display: none"><td> 1. <input type="text" name="esitoTampone1_1" <%if(t1!=null){if(t1.getTipo()==1){ %> value="<%=esito1 %>" <%}} %>></td></tr>
    <tr id="esitoTampone1_2" style="display: none"><td>2. <input type="text" name="esitoTampone1_2" <%if(t1!=null){if(t1.getTipo()==1){ %> value="<%=esito2 %>" <%}} %>></td></tr>
    <tr id="esitoTampone1_3" style="display: none"><td>3. <input type="text" name="esitoTampone1_3" <%if(t1!=null){if(t1.getTipo()==1){ %> value="<%=esito3 %>" <%}} %>></td></tr>
    <tr id="esitoTampone1_4" style="display: none"><td>4. <input type="text" name="esitoTampone1_4" <%if(t1!=null){if(t1.getTipo()==1){ %> value="<%=esito4 %>" <%}} %>></td></tr>
	<tr id="esitoTampone1_5" style="display: none"><td>5. <input type="text" name="esitoTampone1_5" <%if(t1!=null){if(t1.getTipo()==1){ %> value="<%=esito5 %>" <%}} %>></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone1" style="display: none">
    <%RicercaTamponi2_1.setJsEvent("onChange=showEsitiTipo2_tampone1('details')"); %>
  
    <td>Superfice Testata<br> <textarea rows="5" cols="30" name="superfice1"><%=superfice %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_1.getHtmlSelect("RicercaTamponi2_1",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta12"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone1" style="display: none"><td>1. <input type="text" name="esito_1_tampone1" <%if(t1!=null){if(t1.getTipo()==2){ %> value="<%=esito1 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone1" style="display: none"><td>2. <input type="text" name="esito_2_tampone1" <%if(t1!=null){if(t1.getTipo()==2){ %> value="<%=esito2 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone1" style="display: none"><td>3. <input type="text" name="esito_3_tampone1" <%if(t1!=null){if(t1.getTipo()==2){ %> value="<%=esito3 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone1" style="display: none"><td>4. <input type="text" name="esito_4_tampone1" <%if(t1!=null){if(t1.getTipo()==2){ %> value="<%=esito4 %>" <%}} %>></td></tr>
        <tr id="esito_5_tampone1" style="display: none"><td>5. <input type="text" name="esito_5_tampone1" <%if(t1!=null){if(t1.getTipo()==2){ %> value="<%=esito5 %>" <%}} %>></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
   
    
    
     <tr id="tampone2" style="display: none">
    <td><b>Tampone 2 </b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td><input type="checkbox" value="1" name="check1_2" id="check1_2"  <%if(t2!=null){ if(TicketDetails.getListaTamponi().get(2).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone2()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td><input type="checkbox" value="2" name="check2_2" id="check2_2"  <%if(t2!=null){ if(TicketDetails.getListaTamponi().get(2).getTipo()==2){ %> checked="checked" <%}} %> onclick="showRow1_tampone2()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_2.setJsEvent("onChange=showEsitiTampone2('details')"); %>
    <td>
    <table >
   
    
    
    
    
    <tr valign="top" id="row1tampone2" style="display: none">
   <%
   int idsuperfice2=-1;
   String superfice2="";
   if(t2!=null){
	   idsuperfice2=t2.getSuperfice();
	superfice2=t2.getSuperficeStringa();   
   }
	   %>
    <td>Cercassa <br><%=Tamponi.getHtmlSelect("Tamponi2",idsuperfice2) %></td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi_2.getHtmlSelect("RicercaTamponi_2",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esitiTampone1">
    <label id="etichetta2"  style="display: none">Esiti</label>
    <tr id="esitoTampone2_1" style="display: none"><td>1. <input type="text" name="esitoTampone2_1" <%if(t2!=null){if(t2.getTipo()==1){ %> value="<%=esito1_t2 %>" <%}} %>></td></tr>
    <tr id="esitoTampone2_2" style="display: none"><td>2. <input type="text" name="esitoTampone2_2" <%if(t2!=null){if(t2.getTipo()==1){ %> value="<%=esito2_t2 %>" <%}} %>></td></tr>
    <tr id="esitoTampone2_3" style="display: none"><td>3. <input type="text" name="esitoTampone2_3" <%if(t2!=null){if(t2.getTipo()==1){ %> value="<%=esito3_t2 %>" <%}} %>></td></tr>
    <tr id="esitoTampone2_4" style="display: none"><td>4. <input type="text" name="esitoTampone2_4" <%if(t2!=null){if(t2.getTipo()==1){ %> value="<%=esito4_t2 %>" <%}} %>></td></tr>
    <tr id="esitoTampone2_5" style="display: none"><td>5. <input type="text" name="esitoTampone2_5" <%if(t2!=null){if(t2.getTipo()==1){ %> value="<%=esito5_t2 %>" <%}} %>></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone2" style="display: none">
    <%RicercaTamponi2_2.setJsEvent("onChange=showEsitiTipo2_tampone2('details')"); %>
   
    <td>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice2"><%=superfice2 %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_2.getHtmlSelect("RicercaTamponi2_2",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esiti_Tampone1">
    <label id="etichetta22"  style="display: none">Esiti</label>
    <tr id="esito_1_tampone2" style="display: none"><td>1. <input type="text" name="esito_1_tampone2" <%if(t2!=null){if(t2.getTipo()==2){ %> value="<%=esito1_t2 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone2" style="display: none"><td>2. <input type="text" name="esito_2_tampone2" <%if(t2!=null){if(t2.getTipo()==2){ %> value="<%=esito2_t2 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone2" style="display: none"><td>3. <input type="text" name="esito_3_tampone2" <%if(t2!=null){if(t2.getTipo()==2){ %> value="<%=esito3_t2 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone2" style="display: none"><td>4. <input type="text" name="esito_4_tampone2" <%if(t2!=null){if(t2.getTipo()==2){ %> value="<%=esito4_t2 %>" <%}} %>></td></tr>
    <tr id="esito_5_tampone2" style="display: none"><td>5. <input type="text" name="esito_5_tampone2" <%if(t2!=null){if(t2.getTipo()==2){ %> value="<%=esito5_t2 %>" <%}} %>></td></tr>

    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
     
    
      <tr id="tampone3" style="display: none">
    <td><b>Tampone 3 </b></td>
    <td>
     	<table>
     	<tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     	<tr><td><input type="checkbox" value="1" name="check1_3" id="check1_3" <%if(t3!=null){ if(TicketDetails.getListaTamponi().get(3).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone3()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     	<tr><td><input type="checkbox" value="2" name="check2_3" id="check2_3" <%if(t3!=null){ if(TicketDetails.getListaTamponi().get(3).getTipo()==2){ %> checked="checked" <%}} %> onclick="showRow1_tampone3()"></td></tr>
     	</table>
   	</td>
     
    <%RicercaTamponi_3.setJsEvent("onChange=showEsitiTampone3('details')"); %>
    <td>
    	<table >
   
    	<tr valign="top" id="row1tampone3" style="display: none">
   <%
    idsuperfice=-1;
    superfice="";
   if(t3!=null){
	   idsuperfice=t3.getSuperfice();
	superfice=t3.getSuperficeStringa();   
   }
	   %>
    	<td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi3",idsuperfice) %></td>
    	<td>Tipo di Ricerca <br><%=RicercaTamponi_3.getHtmlSelect("RicercaTamponi_3",TicketDetails.getRicercaTamponi()) %></td>
    	<td>
    	<label id="etichetta3"  style="display: none">Esiti</label>
    		<table id="esitiTampone1">
    		
    			<tr id="esitoTampone3_1" style="display: none"><td>1. <input type="text" name="esitoTampone3_1" <%if(t3!=null){if(t3.getTipo()==1){ %> value="<%=esito1_t3 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone3_2" style="display: none"><td>2. <input type="text" name="esitoTampone3_2" <%if(t3!=null){if(t3.getTipo()==1){ %> value="<%=esito2_t3 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone3_3" style="display: none"><td>3. <input type="text" name="esitoTampone3_3" <%if(t3!=null){if(t3.getTipo()==1){ %> value="<%=esito3_t3 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone3_4" style="display: none"><td>4. <input type="text" name="esitoTampone3_4" <%if(t3!=null){if(t3.getTipo()==1){ %> value="<%=esito4_t3 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone3_5" style="display: none"><td>5. <input type="text" name="esitoTampone3_5" <%if(t3!=null){if(t3.getTipo()==1){ %> value="<%=esito5_t3 %>" <%}} %>></td></tr>


    		</table>
    	</td>
    	</tr>
    
    	<tr valign="top" id="row2tampone3" style="display: none">
    <%RicercaTamponi2_3.setJsEvent("onChange=showEsitiTipo2_tampone3('details')"); %>
   		<td>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice3"><%=superfice %></textarea> </td>
    	<td>Tipo di Ricerca <br><%=RicercaTamponi2_3.getHtmlSelect("RicercaTamponi2_3",TicketDetails.getRicercaTamponi()) %></td>
    	<td>
    	<label id="etichetta31"  style="display: none">Esiti</label>
   			 <table id="esiti_Tampone1">
    			<tr id="esito_1_tampone3" style="display: none"><td>1. <input type="text" name="esito_1_tampone3" <%if(t3!=null){if(t3.getTipo()==2){ %> value="<%=esito1_t3 %>" <%}} %>></td></tr>
   				<tr id="esito_2_tampone3" style="display: none"><td>2. <input type="text" name="esito_2_tampone3" <%if(t3!=null){if(t3.getTipo()==2){ %> value="<%=esito2_t3 %>" <%}} %>></td></tr>
   				<tr id="esito_3_tampone3" style="display: none"><td>3. <input type="text" name="esito_3_tampone3" <%if(t3!=null){if(t3.getTipo()==2){ %> value="<%=esito3_t3 %>" <%}} %>></td></tr>
    			<tr id="esito_4_tampone3" style="display: none"><td>4. <input type="text" name="esito_4_tampone3" <%if(t3!=null){if(t3.getTipo()==2){ %> value="<%=esito4_t3 %>" <%}} %>></td></tr>
   			 	<tr id="esito_5_tampone3" style="display: none"><td>5. <input type="text" name="esito_5_tampone3" <%if(t3!=null){if(t3.getTipo()==2){ %> value="<%=esito5_t3 %>" <%}} %>></td></tr>
   			 	
   			 </table>
    	</td>
    
    	</tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    <tr id="tampone4" style="display: none">
    <td><b>Tampone 4 </b></td>
    <td>
     	<table>
     	<tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     	<tr><td><input type="checkbox" value="1" name="check1_4" id="check1_4" <%if(t4!=null){ if(TicketDetails.getListaTamponi().get(4).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone4()"></td></tr>
     	<tr><td align="middle"><label>Altro</label></td></tr>
     	<tr><td><input type="checkbox" value="2" name="check2_4" id="check2_4" <%if(t4!=null){ if(TicketDetails.getListaTamponi().get(4).getTipo()==2){ %> checked="checked" <%}} %> onclick="showRow1_tampone4()"></td></tr>
     	</table>
   	</td>
     
    <%RicercaTamponi_4.setJsEvent("onChange=showEsitiTampone4('details')"); %>
    <td>
    	<table >
   
    	<tr valign="top" id="row1tampone4" style="display: none">
   <%
    idsuperfice=-1;
    superfice="";
   if(t4!=null){
	   idsuperfice=t4.getSuperfice();
	superfice=t4.getSuperficeStringa();   
   }
	   %>
    	<td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi4",idsuperfice) %></td>
    	<td>Tipo di Ricerca <br> <%=RicercaTamponi_4.getHtmlSelect("RicercaTamponi_4",TicketDetails.getRicercaTamponi()) %></td>
    	<td>
    	<label id="etichetta4"  style="display: none">Esiti</label>
    		<table id="esitiTampone1">
    			<tr id="esitoTampone4_1" style="display: none"><td>1. <input type="text" name="esitoTampone4_1" <%if(t4!=null){if(t4.getTipo()==1){ %> value="<%=esito1_t4 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone4_2" style="display: none"><td>2. <input type="text" name="esitoTampone4_2" <%if(t4!=null){if(t4.getTipo()==1){ %> value="<%=esito2_t4 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone4_3" style="display: none"><td>3. <input type="text" name="esitoTampone4_3" <%if(t4!=null){if(t4.getTipo()==1){ %> value="<%=esito3_t4 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone4_4" style="display: none"><td>4. <input type="text" name="esitoTampone4_4" <%if(t4!=null){if(t4.getTipo()==1){ %> value="<%=esito4_t4 %>" <%}} %>></td></tr>
    		<tr id="esitoTampone4_5" style="display: none"><td>5. <input type="text" name="esitoTampone4_5" <%if(t4!=null){if(t4.getTipo()==1){ %> value="<%=esito5_t4 %>" <%}} %>></td></tr>
    		</table>
    	</td>
    	</tr>
    
    	<tr valign="top" id="row2tampone4" style="display: none">
    <%RicercaTamponi2_4.setJsEvent("onChange=showEsitiTipo2_tampone4('details')"); %>
   		<td>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice4"><%=superfice %></textarea> </td>
    	<td>Tipo di Ricerca <br><%=RicercaTamponi2_3.getHtmlSelect("RicercaTamponi2_4",TicketDetails.getRicercaTamponi()) %></td>
    	<td>
    	<label id="etichetta41"  style="display: none">Esiti</label>
   			 <table id="esiti_Tampone1">
    			<tr id="esito_1_tampone4" style="display: none"><td>1. <input type="text" name="esito_1_tampone4" <%if(t4!=null){if(t4.getTipo()==2){ %> value="<%=esito1_t4 %>" <%}} %>></td></tr>
   				<tr id="esito_2_tampone4" style="display: none"><td>2. <input type="text" name="esito_2_tampone4" <%if(t4!=null){if(t4.getTipo()==2){ %> value="<%=esito2_t4 %>" <%}} %>></td></tr>
   				<tr id="esito_3_tampone4" style="display: none"><td>3. <input type="text" name="esito_3_tampone4" <%if(t4!=null){if(t4.getTipo()==2){ %> value="<%=esito3_t4 %>" <%}} %>></td></tr>
    			<tr id="esito_4_tampone4" style="display: none"><td>4. <input type="text" name="esito_4_tampone4" <%if(t4!=null){if(t4.getTipo()==2){ %> value="<%=esito4_t4 %>" <%}} %>></td></tr>
   			     			<tr id="esito_5_tampone4" style="display: none"><td>5. <input type="text" name="esito_5_tampone4" <%if(t4!=null){if(t4.getTipo()==2){ %> value="<%=esito5_t4 %>" <%}} %>></td></tr>
   			 
   			 </table>
    	</td>
    
    	</tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
     <tr id="tampone5" style="display: none">
    <td><b>Tampone 5 </b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td><input type="checkbox" name="check1_5" value="1" id="check1_5" <%if(t5!=null){ if(TicketDetails.getListaTamponi().get(5).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone5()"></td></tr>
    <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td><input type="checkbox" name="check2_5" value="2" id="check2_5" <%if(t5!=null){ if(TicketDetails.getListaTamponi().get(5).getTipo()==2){ %> checked="checked" <%} }%> onclick="showRow1_tampone5()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_5.setJsEvent("onChange=showEsitiTampone5('details')"); %>
    <td>
    <table >
    <tr valign="top" id="row1tampone5" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t5!=null){
	   idsuperfice=t5.getSuperfice();
	superfice=t5.getSuperficeStringa();   
   }
	   %>
    <td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi5",idsuperfice) %></td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi_5.getHtmlSelect("RicercaTamponi_5",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta5"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
  

    <tr id="esitoTampone5_1" style="display: none"><td>1. <input type="text" name="esitoTampone5_1" <%if(t5!=null){if(t5.getTipo()==1){ %> value="<%=esito1_t5 %>" <%}} %>></td></tr>
    <tr id="esitoTampone5_2" style="display: none"><td>2. <input type="text" name="esitoTampone5_2" <%if(t5!=null){if(t1.getTipo()==1){ %> value="<%=esito2_t5 %>"> <%}} %></td></tr>
    <tr id="esitoTampone5_3" style="display: none"><td>3. <input type="text" name="esitoTampone5_3" <%if(t5!=null){if(t5.getTipo()==1){ %> value="<%=esito3_t5%>" <%}} %>></td></tr>
    <tr id="esitoTampone5_4" style="display: none"><td>4. <input type="text" name="esitoTampone5_4" <%if(t5!=null){if(t5.getTipo()==1){ %> value="<%=esito4_t5%>" <%}} %>></td></tr>
        <tr id="esitoTampone5_5" style="display: none"><td>5. <input type="text" name="esitoTampone5_5" <%if(t5!=null){if(t5.getTipo()==1){ %> value="<%=esito5_t5%>" <%}} %>></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone5" style="display: none">
    <%RicercaTamponi2_5.setJsEvent("onChange=showEsitiTipo2_tampone5('details')"); %>
   
    <td>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice5"><%=superfice %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_5.getHtmlSelect("RicercaTamponi2_5",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta51"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone5" style="display: none"><td>1. <input type="text" name="esito_1_tampone5" <%if(t5!=null){if(t5.getTipo()==2){ %> value="<%=esito1_t5 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone5" style="display: none"><td>2. <input type="text" name="esito_2_tampone5" <%if(t5!=null){if(t5.getTipo()==2){ %> value="<%=esito2_t5 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone5" style="display: none"><td>3. <input type="text" name="esito_3_tampone5" <%if(t5!=null){if(t5.getTipo()==2){ %> value="<%=esito3_t5 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone5" style="display: none"><td>4. <input type="text" name="esito_4_tampone5" <%if(t5!=null){if(t5.getTipo()==2){ %> value="<%=esito4_t5%>" <%}} %>></td></tr>
        <tr id="esito_5_tampone5" style="display: none"><td>5. <input type="text" name="esito_5_tampone5" <%if(t5!=null){if(t5.getTipo()==2){ %> value="<%=esito5_t5%>" <%}} %>></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
      
    
    
     <tr id="tampone6" style="display: none">
    <td><b>Tampone 6  </b></td>
     <td>
     <table>
    <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td><input type="checkbox" name="check1_6" value="1" <%if(t6!=null){ if(TicketDetails.getListaTamponi().get(6).getTipo()==1){ %> checked="checked" <%} }%> id="check1_6" onclick="showRow1_tampone6()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td><input type="checkbox" name="check2_6" value="2" <%if(t6!=null){ if(TicketDetails.getListaTamponi().get(6).getTipo()==2){ %> checked="checked" <%} }%> id="check2_6" onclick="showRow1_tampone6()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_6.setJsEvent("onChange=showEsitiTampone6('details')"); %>
    <td>
    <table >
    
    
    <tr valign="top" id="row1tampone6" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t6!=null){
	   idsuperfice=t6.getSuperfice();
	superfice=t6.getSuperficeStringa();   
   }
	   %>
    <td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi6",idsuperfice) %></td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi_6.getHtmlSelect("RicercaTamponi_6",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta6"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    
    
    <tr id="esitoTampone6_1" style="display: none"><td>1. <input type="text" name="esitoTampone6_1" <%if(t6!=null){if(t6.getTipo()==1){ %> value="<%=esito1_t6 %>" <%}} %>></td></tr>
    <tr id="esitoTampone6_2" style="display: none"><td>2. <input type="text" name="esitoTampone6_2" <%if(t6!=null){if(t6.getTipo()==1){ %> value="<%=esito2_t6 %>" <%}} %>></td></tr>
    <tr id="esitoTampone6_3" style="display: none"><td>3. <input type="text" name="esitoTampone6_3" <%if(t6!=null){if(t6.getTipo()==1){ %> value="<%=esito3_t6 %>" <%}} %>></td></tr>
    <tr id="esitoTampone6_4" style="display: none"><td>4. <input type="text" name="esitoTampone6_4" <%if(t6!=null){if(t6.getTipo()==1){ %> value="<%=esito4_t6 %>" <%}} %>></td></tr>
        <tr id="esitoTampone6_5" style="display: none"><td>5. <input type="text" name="esitoTampone6_5" <%if(t6!=null){if(t6.getTipo()==1){ %> value="<%=esito5_t6 %>" <%}} %>></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone6" style="display: none">
    <%RicercaTamponi2_6.setJsEvent("onChange=showEsitiTipo2_tampone6('details')"); %>
   
    <td>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice6"><%=superfice %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_6.getHtmlSelect("RicercaTamponi2_6",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta61"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone6" style="display: none"><td>1. <input type="text" name="esito_1_tampone6" <%if(t6!=null){if(t6.getTipo()==2){ %> value="<%=esito1_t6 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone6" style="display: none"><td>2. <input type="text" name="esito_2_tampone6"  <%if(t6!=null){if(t6.getTipo()==2){ %>value="<%=esito2_t6 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone6" style="display: none"><td>3. <input type="text" name="esito_3_tampone6" <%if(t6!=null){if(t6.getTipo()==2){ %> value="<%=esito3_t6 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone6" style="display: none"><td>4. <input type="text" name="esito_4_tampone6" <%if(t6!=null){if(t6.getTipo()==2){ %> value="<%=esito4_t6 %>" <%}} %>></td></tr>
        <tr id="esito_5_tampone6" style="display: none"><td>5. <input type="text" name="esito_5_tampone6" <%if(t6!=null){if(t6.getTipo()==2){ %> value="<%=esito5_t6 %>" <%}} %>></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
   
    
    
     
     <tr id="tampone7" style="display: none">
    <td><b>Tampone 7 </b></td>
     <td>
     <table>
     <%RicercaTamponi_7.setJsEvent("onChange=showEsitiTampone7('details')");  %>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td><input type="checkbox" name="check1_7" value="1" <%if(t7!=null){ if(TicketDetails.getListaTamponi().get(7).getTipo()==1){ %> checked="checked" <%}} %> id="check1_7" onclick="showRow1_tampone7()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td><input type="checkbox" name="check2_7" value="2" <%if(t7!=null){ if(TicketDetails.getListaTamponi().get(7).getTipo()==2){ %> checked="checked" <%} }%> id="check2_7" onclick="showRow1_tampone7()"></td></tr>
     </table>
   </td>
     
    
    <td>
    <table >
    <tr valign="top" id="row1tampone7" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t7!=null){
	   idsuperfice=t7.getSuperfice();
	superfice=t7.getSuperficeStringa();   
   }
	   %>
    <td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi7",idsuperfice) %></td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi_7.getHtmlSelect("RicercaTamponi_7",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta7"  style="display: none">Esiti</label>
    
    <table id="esitiTampone1">
    
    
    <tr id="esitoTampone7_1" style="display: none"><td>1. <input type="text" name="esitoTampone7_1" <%if(t7!=null){if(t7.getTipo()==1){ %> value="<%=esito1_t7 %>" <%}} %>></td></tr>
    <tr id="esitoTampone7_2" style="display: none"><td>2. <input type="text" name="esitoTampone7_2" <%if(t7!=null){if(t7.getTipo()==1){ %> value="<%=esito2_t7 %>" <%}} %>></td></tr>
    <tr id="esitoTampone7_3" style="display: none"><td>3. <input type="text" name="esitoTampone7_3" <%if(t7!=null){if(t1.getTipo()==1){ %> value="<%=esito3_t7 %>" <%}} %>></td></tr>
    <tr id="esitoTampone7_4" style="display: none"><td>4. <input type="text" name="esitoTampone7_4" <%if(t7!=null){if(t1.getTipo()==1){ %> value="<%=esito4_t7 %>" <%}} %>></td></tr>
        <tr id="esitoTampone7_5" style="display: none"><td>5. <input type="text" name="esitoTampone7_5" <%if(t7!=null){if(t1.getTipo()==1){ %> value="<%=esito5_t7 %>" <%}} %>></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone7" style="display: none">
    <%RicercaTamponi2_7.setJsEvent("onChange=showEsitiTipo2_tampone7('details')"); %>
   
    <td>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice7"><%=superfice %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_7.getHtmlSelect("RicercaTamponi2_7",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta71"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone7" style="display: none"><td>1. <input type="text" name="esito_1_tampone7" <%if(t7!=null){if(t7.getTipo()==2){ %> value="<%=esito1_t7 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone7" style="display: none"><td>2. <input type="text" name="esito_2_tampone7" <%if(t7!=null){if(t7.getTipo()==2){ %> value="<%=esito2_t7 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone7" style="display: none"><td>3. <input type="text" name="esito_3_tampone7" <%if(t7!=null){if(t7.getTipo()==2){ %> value="<%=esito3_t7 %>" <%}} %>> </td></tr>
    <tr id="esito_4_tampone7" style="display: none"><td>4. <input type="text" name="esito_4_tampone7" <%if(t7!=null){if(t7.getTipo()==2){ %> value="<%=esito4_t7%>" <%}} %>></td></tr>
        <tr id="esito_5_tampone7" style="display: none"><td>5. <input type="text" name="esito_5_tampone7" <%if(t7!=null){if(t7.getTipo()==2){ %> value="<%=esito5_t7%>" <%}} %>></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
 
    
   
    
     <tr id="tampone8" style="display: none">
    <td><b>Tampone 8 </b></td>
     <td>
     <table>
    <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td><input type="checkbox" name="check1_8" value="1" id="check1_8" <%if(t8!=null){ if(TicketDetails.getListaTamponi().get(8).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone8()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td><input type="checkbox" name="check2_8" value="2" id="check2_8" <%if(t8!=null){ if(TicketDetails.getListaTamponi().get(8).getTipo()==2){ %> checked="checked" <%}} %> onclick="showRow1_tampone8()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_8.setJsEvent("onChange=showEsitiTampone8('details')"); %>
    <td>
    <table >
    
       
    <tr valign="top" id="row1tampone8" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t8!=null){
	   idsuperfice=t8.getSuperfice();
	superfice=t8.getSuperficeStringa();   
   }
	   %>
    <td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi8",idsuperfice) %></td>
    <td>Tipo di Ricerca <br> <%=RicercaTamponi_8.getHtmlSelect("RicercaTamponi_8",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta8"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
     
    
    <tr id="esitoTampone8_1" style="display: none"><td>1. <input type="text" name="esitoTampone8_1" <%if(t8!=null){if(t8.getTipo()==1){ %> value="<%=esito1_t8 %>" <%}} %>></td></tr>
    <tr id="esitoTampone8_2" style="display: none"><td>2. <input type="text" name="esitoTampone8_2" <%if(t8!=null){if(t8.getTipo()==1){ %> value="<%=esito2_t8 %>" <%}} %>></td></tr>
    <tr id="esitoTampone8_3" style="display: none"><td>3. <input type="text" name="esitoTampone8_3" <%if(t8!=null){if(t8.getTipo()==1){ %> value="<%=esito3_t8 %>" <%}} %>></td></tr>
    <tr id="esitoTampone8_4" style="display: none"><td>4. <input type="text" name="esitoTampone8_4" <%if(t8!=null){if(t8.getTipo()==1){ %> value="<%=esito4_t8 %>" <%}} %>></td></tr>
        <tr id="esitoTampone8_5" style="display: none"><td>5. <input type="text" name="esitoTampone8_5" <%if(t8!=null){if(t8.getTipo()==1){ %> value="<%=esito5_t8 %>" <%}} %>></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone8" style="display: none">
    <%RicercaTamponi2_8.setJsEvent("onChange=showEsitiTipo2_tampone8('details')"); %>
   
    <td> Superfice Testata <br><textarea rows="5" cols="30" name="superfice8"><%=superfice %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_8.getHtmlSelect("RicercaTamponi2_8",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta81"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone8" style="display: none"><td>1. <input type="text" name="esito_1_tampone8" <%if(t8!=null){if(t8.getTipo()==2){ %> value="<%=esito1_t8 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone8" style="display: none"><td>2. <input type="text" name="esito_2_tampone8" <%if(t8!=null){if(t8.getTipo()==2){ %> value="<%=esito2_t8 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone8" style="display: none"><td>3. <input type="text" name="esito_3_tampone8" <%if(t8!=null){if(t8.getTipo()==2){ %> value="<%=esito3_t8 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone8" style="display: none"><td>4. <input type="text" name="esito_4_tampone8" <%if(t8!=null){if(t8.getTipo()==2){ %> value="<%=esito4_t8%>" <%}} %> ></td></tr>
        <tr id="esito_5_tampone8" style="display: none"><td>5. <input type="text" name="esito_5_tampone8" <%if(t8!=null){if(t8.getTipo()==2){ %> value="<%=esito5_t8%>" <%}} %> ></td></tr>
    
    </table>
    
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
     <tr id="tampone9" style="display: none">
    <td><b>Tampone 9 </b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td><input type="checkbox" name="check1_9" value="1" id="check1_9" <%if(t9!=null){ if(TicketDetails.getListaTamponi().get(9).getTipo()==1){ %> checked="checked" <%} }%> onclick="showRow1_tampone9()"></td></tr>
    <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td><input type="checkbox" name="check2_9" value="2" id="check2_9" <%if(t9!=null){if(TicketDetails.getListaTamponi().get(9).getTipo()==2){ %> checked="checked" <%} }%> onclick="showRow1_tampone9()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_9.setJsEvent("onChange=showEsitiTampone9('details')"); %>
    <td>
    <table >
    <tr valign="top" id="row1tampone9" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t9!=null){
	   idsuperfice=t9.getSuperfice();
	superfice=t9.getSuperficeStringa();   
   }
	   %>
    <td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi9",idsuperfice) %></td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi_9.getHtmlSelect("RicercaTamponi_9",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta9"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
     
    
    <tr id="esitoTampone9_1" style="display: none"><td>1. <input type="text" name="esitoTampone9_1" <%if(t9!=null){if(t9.getTipo()==1){ %> value="<%=esito1_t9 %>" <%}} %>></td></tr>
    <tr id="esitoTampone9_2" style="display: none"><td>2. <input type="text" name="esitoTampone9_2" <%if(t9!=null){if(t9.getTipo()==1){ %> value="<%=esito2_t9 %>" <%}} %>></td></tr>
    <tr id="esitoTampone9_3" style="display: none"><td>3. <input type="text" name="esitoTampone9_3" <%if(t9!=null){if(t9.getTipo()==1){ %> value="<%=esito3_t9 %>" <%}} %>></td></tr>
    <tr id="esitoTampone9_4" style="display: none"><td>4. <input type="text" name="esitoTampone9_4" <%if(t9!=null){if(t9.getTipo()==1){ %> value="<%=esito4_t9 %>" <%}} %>></td></tr>
        <tr id="esitoTampone9_5" style="display: none"><td>5. <input type="text" name="esitoTampone9_5" <%if(t9!=null){if(t9.getTipo()==1){ %> value="<%=esito5_t9 %>" <%}} %>></td></tr>
    
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone9" style="display: none">
    <%RicercaTamponi2_9.setJsEvent("onChange=showEsitiTipo2_tampone9('details')"); %>
   
    <td>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice9"><%=superfice %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_9.getHtmlSelect("RicercaTamponi2_9",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta91"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone9" style="display: none"><td>1. <input type="text" name="esito_1_tampone9" <%if(t9!=null){if(t9.getTipo()==2){ %> value="<%=esito1_t9 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone9" style="display: none"><td>2. <input type="text" name="esito_2_tampone9" <%if(t9!=null){if(t9.getTipo()==2){ %> value="<%=esito2_t9 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone9" style="display: none"><td>3. <input type="text" name="esito_3_tampone9" <%if(t9!=null){if(t9.getTipo()==2){ %> value="<%=esito3_t9 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone9" style="display: none"><td>4. <input type="text" name="esito_4_tampone9" <%if(t9!=null){if(t9.getTipo()==2){ %> value="<%=esito4_t9 %>" <%}} %>></td></tr>
     <tr id="esito_5_tampone9" style="display: none"><td>5. <input type="text" name="esito_5_tampone9" <%if(t9!=null){if(t9.getTipo()==2){ %> value="<%=esito5_t9 %>" <%}} %>></td></tr>
 
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
   
  
    
    
    
     <tr id="tampone10" style="display: none">
     	<td><b>Tampone 10 </b></td>
     	<td>
     		<table>
    		<tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
    			 <tr><td><input type="checkbox" name="check1_10" value="1" id="check1_10" <%if(t10!=null){if(TicketDetails.getListaTamponi().get(10).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone10()"></td></tr>
     			<tr><td align="middle"><label>Altro</label></td></tr>
     			<tr><td><input type="checkbox" name="check2_10" value="2" id="check2_10" <%if(t10!=null){if(TicketDetails.getListaTamponi().get(10).getTipo()==2){ %> checked="checked" <%} }%> onclick="showRow1_tampone10()"></td></tr>
     		</table>
  		 </td>
     
    <%RicercaTamponi_10.setJsEvent("onChange=showEsitiTampone10('details')"); %>
    	<td>
    		<table >
     
    		<tr valign="top" id="row1tampone10" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t10!=null){
	   idsuperfice=t10.getSuperfice();
	superfice=t10.getSuperficeStringa();   
   }
	   %>
    <td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi10",idsuperfice) %></td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi_10.getHtmlSelect("RicercaTamponi_10",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
    			<table id="esitiTampone1">
     
    
    <tr id="esitoTampone10_1" style="display: none"><td>1. <input type="text" name="esitoTampone10_1" <%if(t10!=null){if(t10.getTipo()==1){ %> value="<%=esito1_t10 %>" <%}} %>></td></tr>
    <tr id="esitoTampone10_2" style="display: none"><td>2. <input type="text" name="esitoTampone10_2" <%if(t10!=null){if(t10.getTipo()==1){ %> value="<%=esito2_t10 %>" <%}} %>></td></tr>
    <tr id="esitoTampone10_3" style="display: none"><td>3. <input type="text" name="esitoTampone10_3" <%if(t10!=null){if(t10.getTipo()==1){ %> value="<%=esito3_t10 %>" <%}} %>></td></tr>
    <tr id="esitoTampone10_4" style="display: none"><td>4. <input type="text" name="esitoTampone10_4" <%if(t10!=null){if(t10.getTipo()==1){ %> value="<%=esito4_t10 %>" <%}} %>></td></tr>
  	    <tr id="esitoTampone10_5" style="display: none"><td>5. <input type="text" name="esitoTampone10_5" <%if(t10!=null){if(t10.getTipo()==1){ %> value="<%=esito5_t10 %>" <%}} %>></td></tr>
  	
  			  </table>
   	 
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone10" style="display: none">
    <%RicercaTamponi2_10.setJsEvent("onChange=showEsitiTipo2_tampone10('details')"); %>
   
    <td>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice10"><%=superfice %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_10.getHtmlSelect("RicercaTamponi2_10",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta101"  style="display: none">Esiti</label>
   	 <table id="esiti_Tampone1">
    <tr id="esito_1_tampone10" style="display: none"><td>1. <input type="text" name="esito_1_tampone10" <%if(t10!=null){if(t10.getTipo()==2){ %> value="<%=esito1_t10 %>"<%}} %>></td></tr>
    <tr id="esito_2_tampone10" style="display: none"><td>2. <input type="text" name="esito_2_tampone10" <%if(t10!=null){if(t10.getTipo()==2){ %> value="<%=esito2_t10 %>"<%}} %>></td></tr>
    <tr id="esito_3_tampone10" style="display: none"><td>3. <input type="text" name="esito_3_tampone10" <%if(t10!=null){if(t10.getTipo()==2){ %> value="<%=esito3_t10 %>"<%}} %>></td></tr>
    <tr id="esito_4_tampone10" style="display: none"><td>4. <input type="text" name="esito_4_tampone10" <%if(t10!=null){if(t10.getTipo()==2){ %> value="<%=esito4_t10 %>" <%}} %>></td></tr>
  	    <tr id="esito_5_tampone10" style="display: none"><td>5. <input type="text" name="esito_5_tampone10" <%if(t10!=null){if(t10.getTipo()==2){ %> value="<%=esito5_t10 %>" <%}} %>></td></tr>
  	
  	  </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    

    
    </table>
    
    </td>
   </tr>
  
  
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
    Numero Verbale
    </td>
    <td>
	<%if((TicketDetails.getLocation() != "" && TicketDetails.getLocation() != null)){ %>
      <input type="text" name="location" id="location" value="<%= toHtmlValue(TicketDetails.getLocation()) %>" size="20" maxlength="256" />
    <%}else{%>
          <input type="text" name="location" id="location" value="" size="20" maxlength="256" />
    <%} %>
    </td>
  </tr>
 <tr class="containerBody">
      <td nowrap class="formLabel">
       Data Prelievo
      </td>
      <td>
      
       <input readonly type="text" id="assignedDate" name="assignedDate" size="10" value = "<%=toDateasString(TicketDetails.getAssignedDate()) %>"/>
		<a href="#" onClick="cal19.select(document.forms[0].assignedDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
    
        <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
      </td>
    </tr>
	

  
<dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td name="destinatarioTampone1" id="destinatarioTampone1" nowrap class="formLabel">
        <dhv:label name="">Laboratorio di Destinazione</dhv:label>
      </td>
    <td>
      <%= DestinatarioTampone.getHtmlSelect("DestinatarioTampone",TicketDetails.getDestinatarioTampone()) %>
  <%--   <input type="hidden" name="destinatarioTampone" value="<%=TicketDetails.getDestinatarioTampone()%>" > --%>
    </td>
  </tr>
</dhv:include>

 <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Codice Accettazione</dhv:label>
    </td>
    <td>
      <input type="text" name="cause" id="cause" value="<%= toHtmlValue(TicketDetails.getCause()) %>" size="20" maxlength="256" />
    </td>
  </tr>
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
      <dhv:label name="sanzionia.azioni">Punteggio</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <select name="punteggio">
            <option value="0" <%if(TicketDetails.getPunteggio()==0){ %> selected="selected" <%} %> >   0 </option>
            <option value="2" <%if(TicketDetails.getPunteggio()==2) {%> selected="selected" <%} %>>   2 </option>
            <option value="4" <%if(TicketDetails.getPunteggio()==4){ %> selected="selected" <%} %>>   4 </option>
            <option value="6" <%if(TicketDetails.getPunteggio()==6) {%> selected="selected" <%} %>>   6 </option>
            <option value="8" <%if(TicketDetails.getPunteggio()==8) {%> selected="selected" <%} %>>   8 </option>
            <option value="10" <%if(TicketDetails.getPunteggio()==10){ %> selected="selected" <%} %>> 10 </option> 
            </select>
            (Punteggio a scelta dell'Ispettore in funzione della gravità della n.c. rilevata)
          </td>
         
        </tr>
   </table></td></tr>
  
  <tr class="containerBody">
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
 