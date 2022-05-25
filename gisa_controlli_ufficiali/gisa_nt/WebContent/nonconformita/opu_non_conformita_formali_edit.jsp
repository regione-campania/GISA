<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcfs.modules.nonconformita.base.ElementoNonConformita"%>
<h2>Non Conformita Formali</h2>
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
        String note_f = "" ;
        for(ElementoNonConformita nc : TicketDetails.getNon_conformita_formali())
        {
        	nc_formali_inserite_f+=nc.getId_nc()+";";
        	note_f += nc.getNote()+";";
        }
        %>
        <tr><td colspan="3" ><input type = "button" value = "Inserisci un'altra NC Formale" onclick="return clonaNC_Formali()"><input type = "button" value = "Elimina NC Formale" onclick="return removeFormali('false',<%=TicketDetails.getNon_conformita_formali().size() %>);"><input type = "button" value = "Reset NC Formale" onclick="resetFormali_update('<%=CU.getId() %>',1,<%=TicketDetails.getNon_conformita_formali().size() %>,'<%=request.getAttribute("attivita_formali") %>','<%=nc_formali_inserite_f %>','<%=note_f %>')" ></td></tr>
       
        <%
        int i = 0;
        for (ElementoNonConformita elementoNc : TicketDetails.getNon_conformita_formali())
        {
        	i = i+1;
        	
        	%>
        <tr id = "nc_formali_<%=elementoNc.getProgressivo_nc() %>" >
        
          <td>
          <label><%="<b>"+elementoNc.getProgressivo_nc()+"</b>" %></label>
            <textarea name="nonConformitaFormali_<%=elementoNc.getProgressivo_nc() %>"   id = "nonConformitaFormali_<%=elementoNc.getProgressivo_nc() %>" onchange="abilitaFlagFormali();abilitaStatoFormali('<%=CU.getId()%>');calcolaPuntiNonConformita(document.getElementById('elementi_nc_formali').value,'Provvedimenti1_','puntiFormali','nonConformitaFormali_',<%=CU.getTipoCampione() %>,'<%=CU.getAssignedDate() %>');" cols="55" rows="6" onclick="if(this.value=='INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\''){this.value=''};abilitaFlagFormali();"><%=elementoNc.getNote() %></textarea>
          </td>
         <td>
         	<%
        	if ((CU.getTipoCampione()==4  || CU.getTipoCampione()==2 ||  (CU.getTipoCampione()==3 && CU.getAssignedDate().after(java.sql.Timestamp.valueOf(org.aspcf.modules.controlliufficiali.base.ApplicationProperties.getProperty("TIMESTAMP_NUOVA_GESTIONE_OGGETTO_DEL_CONTROLLO_AUDIT"))) )) && CU.getLisaElementi_Ispezioni().size()!=0)
        	{
        		Iterator<Integer> ite = CU.getLisaElementi_Ispezioni().keySet().iterator();
        		%>
        		<select name = "Provvedimenti1_<%=elementoNc.getProgressivo_nc() %>" id = "Provvedimenti1_<%=elementoNc.getProgressivo_nc() %>"  size = "6" onchange="abilitaFlagFormali();abilitaStatoFormali('<%=CU.getId() %>');calcolaPuntiNonConformita(document.getElementById('elementi_nc_formali').value,'Provvedimenti1_','puntiFormali','nonConformitaFormali_',<%=CU.getTipoCampione() %>,'<%=CU.getAssignedDate() %>');sevValueNcFormaliModify(<%=i %>); gestisciBenessereNonConformita(this)">	
        		<option value = "-1" selected="selected">-- SELEZIONA UNA TIPOLOGIA DI NC --</option>
        		<%
        		while(ite.hasNext())
        		{
        			int code = ite.next();
        			
        			HashMap<Integer, String> lista =  CU.getLisaElementi_Ispezioni().get(code);
        			Iterator<Integer> ite2 = lista.keySet().iterator();
        			%>
        			<optgroup label="<%=Macrocategorie.getValueFromId(code) %>" style="color: blue"></optgroup>
        			<%
        			
        			while(ite2.hasNext())
            		{
        				int codice = ite2.next();
        				
        			%>
        			<option value ="<%=codice %>" <%if (codice==elementoNc.getId_nc()){%>selected="selected"<%} %>><%=lista.get(codice) %></option>
        			<%
            		}
        		}
        		%>
        		</select>
        		<%        		
        	}
        	else
        	{
        	%>
        		<%
        		Provvedimenti.setSelectSize(8);
        		
        		Provvedimenti.setJsEvent("onchange=\"abilitaFlagFormali();abilitaStatoFormali('"+CU.getId()+"');calcolaPuntiNonConformita(document.getElementById('elementi_nc_formali').value,'Provvedimenti1_','puntiFormali','nonConformitaFormali_',"+CU.getTipoCampione()+",'"+CU.getAssignedDate()+"');sevValueNcFormaliModify("+i+"); gestisciBenessereNonConformita(this)\""); %>
         		<%= Provvedimenti.getHtmlSelect("Provvedimenti1_"+elementoNc.getProgressivo_nc() ,elementoNc.getId_nc()) %>
        		
         <%} %>
         
         <% if (elementoNc.getId_nc_benessere_macellazione()<=0) {ProvvedimentiBenessereMacellazione.setJsEvent("style=\"display:none\""); }%>
         <%=ProvvedimentiBenessereMacellazione.getHtmlSelect("ProvvedimentiBenessereMacellazione1_"+elementoNc.getProgressivo_nc(), elementoNc.getId_nc_benessere_macellazione()) %>
      	 <% if (elementoNc.getId_nc_benessere_trasporto()<=0) {ProvvedimentiBenessereTrasporto.setJsEvent("style=\"display:none\""); }%>
         <%=ProvvedimentiBenessereTrasporto.getHtmlSelect("ProvvedimentiBenessereTrasporto1_"+elementoNc.getProgressivo_nc(), elementoNc.getId_nc_benessere_trasporto()) %>
         
         <input type = "hidden" name = "Provvedimenti1_<%=elementoNc.getProgressivo_nc() %>_selezionato" id = "Provvedimenti1_<%=elementoNc.getProgressivo_nc() %>_selezionato" value = "<%=elementoNc.getId_nc() %>">
         
          <%if (listaLineeCU!=null && listaLineeCU.size()>0) { %>
          <br/>
          Linea sottoposta a non conformita'<br/>
          <select id="Linea1_<%=elementoNc.getProgressivo_nc() %>" name="Linea1_<%=elementoNc.getProgressivo_nc() %>">
          <%for (int k = 0; k<listaLineeCU.size(); k++) {
          LineeAttivita linea = (LineeAttivita) listaLineeCU.get(k);%>
          <option value="<%=linea.getId()%>" <%=elementoNc.getId_linea_nc()==linea.getId() ? "selected" : "" %>><%=(linea.getMacroarea()!= null ? linea.getMacroarea() + "->" : "") + (linea.getAggregazione()!=null ? linea.getAggregazione() + "->" : linea.getCategoria()!=null ? linea.getCategoria() + "->" : "") + (linea.getAttivita()!=null ? linea.getAttivita() + "->" : linea.getLinea_attivita()!=null ? linea.getLinea_attivita() : "") %></option>
          <% } %>
          </select>
          <br/><br/>
          <% } %>
        </td>
        
        </tr>
        <%} %>
                 
      </table>
      </td></tr>
      <tr>
        <td>
           <textarea id = "valutazione_rischio_formali" name="nonConformitaFormali_valutazione" cols="55" rows="6"><%=toHtml(TicketDetails.getNcFormaliValutazioni()) %></textarea>
          </td>
          
          
          <%if(CU.getTipoCampione()!=5){ %>
          <td>
          <center>Punteggio</center>
           <br>
            <input type="text"  name="puntiFormali" readonly="readonly" id="puntiFormali" value="<%= TicketDetails.getPuntiFormali() %>" onchange="calcolaTotale()">
          </td>
          <%} else{%>
          <td>
          Punteggio già calcolato nella check list.
          </td>
          <%} %>
          <td>&nbsp;&nbsp;&nbsp;</td>
        </tr>
        <tr>
<td >
<div class="buttonwrapper">
<% if(CU.getIdStabilimento() > 0) {%>
<a class="ovalbutton" href = "javascript: if (document.getElementById('abilita_formali').value == 'true' && trim(document.getElementById('valutazione_rischio_formali').value) != '' && trim(document.getElementById('valutazione_rischio_formali').value) != 'INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' FORMALI RISCONTRATE' && document.getElementById('descrizione_or_combo_sel_formali').value == 'false') { openNotePopupFollowup('<%=CU.getIdStabilimento() %>','<%=CU.getId() %>','<%=CU.getAssignedDate() %>',1) }else{ alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo , inserita la relativa descrizione e aver compilato la valutazione del rischio !') }"  onclick="return !this.disabled;"><span> Inserisci Follow up</span></a>
<!--<td><a href = "#dialog" name = "modal" > Aggiungi Follo wup da pop up modale</a>-->
<% } else { %>
<a class="ovalbutton" href = "javascript: if (document.getElementById('abilita_formali').value == 'true' && trim(document.getElementById('valutazione_rischio_formali').value) != '' && trim(document.getElementById('valutazione_rischio_formali').value) != 'INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' FORMALI RISCONTRATE' && document.getElementById('descrizione_or_combo_sel_formali').value == 'false') { openNotePopupFollowup('<%=CU.getIdApiario() %>','<%=CU.getId() %>','<%=CU.getAssignedDate() %>',1) }else{ alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo , inserita la relativa descrizione e aver compilato la valutazione del rischio !') }"  onclick="return !this.disabled;"><span> Inserisci Follow up</span></a>
<!--<td><a href = "#dialog" name = "modal" > Aggiungi Follo wup da pop up modale</a>-->
<% } %>
</div>
</td>
</tr>
        
        
      </table>
    </td>
  </tr>
   <tr>
  <td>
     <table cellpadding="4" cellspacing="0" width="100%" class = "noborder" >
		
		<thead><tr style="background-color: rgb(204, 255, 153);">
		<td colspan="2"><b>Elenco dei follow up aggiunti (Inserire almeno un follow up se sono state riscontrate n.c. Formali)</b></td>
		</tr>

  </thead>
 <tr id = "lista_followup_formali" style="display: none"><td><label></label></td><td><label></label></td></tr>
  
  </table>
  </td>
  </tr>
  </table>
  <input type = "hidden" name = "followup_gravi_inseriti" id = "followup_gravi_inseriti" value = "">
  <input type = "hidden" name = "followup_inseriti_formali" id = "followup_inseriti_formali" value = "<%=request.getAttribute("attivita_formali") %>">
  <input type = "hidden" name = "stato_formali" id = "stato_formali" value = "true">
  <input type = "hidden" name = "descrizione_or_combo_sel" id = "descrizione_or_combo_sel_formali" value = "false">
   
  
  
  
  
  