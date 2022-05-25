<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<h2>Non Conformita Formali</h2>
<table>
<tr id = "show_formali">
    
    <td>
    
   <input type = "hidden" id = "elementi_nc_formali" name = "elementi_nc_formali" value = "1">
   <input type = "hidden" id = "size_nc_formali" name = "size_nc_formali" value = "1">
      <table border="0" width="100%" cellspacing="0" cellpadding="10" class="empty" >
        <tr >
        <td>
        <table>
        <tr><td colspan="3" ><input type = "button" value = "Inserisci un'altra NC Formale"  onclick="return clonaNC_Formali()"><input type = "button" value = "Elimina NC Formale" onclick="return removeFormali('false',1);"><input type = "button" value = "Reset NC Formale" onclick="resetFormali('<%=CU.getId() %>',1,1,true)" ></td></tr>
        <tr id = "nc_formali_1" >
          <td>
          <label>1</label>
            <textarea name="nonConformitaFormali_1" id = "nonConformitaFormali_1" onchange="abilitaFlagFormali();abilitaStatoFormali('<%=CU.getId()%>');calcolaPuntiNonConformita(document.getElementById('elementi_nc_formali').value,'Provvedimenti1_','puntiFormali','nonConformitaFormali_',<%=CU.getTipoCampione() %>,'<%=CU.getAssignedDate() %>');" cols="55" rows="6" onclick="if(this.value=='INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\''){this.value=''};abilitaFlagFormali();">INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA'</textarea>
          </td>
         <td>
         	<%
        	if ((CU.getTipoCampione()==4  || CU.getTipoCampione()==2 ||  (CU.getTipoCampione()==3 && CU.getAssignedDate().after(java.sql.Timestamp.valueOf(org.aspcf.modules.controlliufficiali.base.ApplicationProperties.getProperty("TIMESTAMP_NUOVA_GESTIONE_OGGETTO_DEL_CONTROLLO_AUDIT"))) )) && CU.getLisaElementi_Ispezioni().size()!=0)
        	{
        		
        		Iterator<Integer> ite = CU.getLisaElementi_Ispezioni().keySet().iterator();
        		%>
        		<select name = "Provvedimenti1_1"  id = "Provvedimenti1_1"  size = "6" onchange="abilitaFlagFormali();abilitaStatoFormali('<%=CU.getId() %>');calcolaPuntiNonConformita(document.getElementById('elementi_nc_formali').value,'Provvedimenti1_','puntiFormali','nonConformitaFormali_',<%=CU.getTipoCampione() %>,'<%=CU.getAssignedDate() %>'); gestisciBenessereNonConformita(this)">	
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
        			<option value ="<%=codice %>"><%=lista.get(codice) %></option>
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
        		Provvedimenti.setJsEvent("onchange=abilitaFlagFormali();abilitaStatoFormali('"+CU.getId()+"');calcolaPuntiNonConformita(document.getElementById('elementi_nc_formali').value,'Provvedimenti1_','puntiFormali','nonConformitaFormali_',"+CU.getTipoCampione()+","+CU.getAssignedDate() +"'); gestisciBenessereNonConformita(this)"); %>
         		<%= Provvedimenti.getHtmlSelect("Provvedimenti1_1",TicketDetails.getProvvedimenti()) %>
         <%} %>
         
         <%ProvvedimentiBenessereMacellazione.setJsEvent("style=\"display:none\"");%>
         <%=ProvvedimentiBenessereMacellazione.getHtmlSelect("ProvvedimentiBenessereMacellazione1_1", -1) %>
         <%ProvvedimentiBenessereTrasporto.setJsEvent("style=\"display:none\""); %>
         <%=ProvvedimentiBenessereTrasporto.getHtmlSelect("ProvvedimentiBenessereTrasporto1_1", -1) %>
         
         <input type = "hidden" name = "Provvedimenti1_1_selezionato" id = "Provvedimenti1_1_selezionato" value = "-1">
         <%if (listaLineeCU!=null && listaLineeCU.size()>0) { %>
          <br/>
          Linea sottoposta a non conformita'<br/>
          <select id="Linea1_1" name="Linea1_1">
          <%for (int k = 0; k<listaLineeCU.size(); k++) {
          LineeAttivita linea = (LineeAttivita) listaLineeCU.get(k);%>
          <option value="<%=linea.getId()%>"><%=(linea.getMacroarea()!= null ? linea.getMacroarea() + "->" : "") + (linea.getAggregazione()!=null ? linea.getAggregazione() + "->" : linea.getCategoria()!=null ? linea.getCategoria() + "->" : "") + (linea.getAttivita()!=null ? linea.getAttivita() + "->" : linea.getLinea_attivita()!=null ? linea.getLinea_attivita() : "") %></option>
          <% } %>
          </select>
          <br/><br/>
         <% } %>
        </td>
        
        </tr>
                
      </table>
      </td></tr>
      <tr>
        <td>
           <textarea id = "valutazione_rischio_formali" name="nonConformitaFormali_valutazione" cols="55" rows="6" onclick="if (this.value =='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' FORMALI RISCONTRATE'){this.value=''}">INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA' FORMALI RISCONTRATE</textarea>
          </td>
          
          
          <%if(CU.getTipoCampione()!=5){ %>
          <td>
          <center>Punteggio</center>
           <br>
            <input type="text" value="" name="puntiFormali" readonly="readonly" id="puntiFormali" onchange="calcolaTotale()">
          </td>
          <%} else{%>
          <td>
          Punteggio già calcolato nella check list.
          </td>
          <%} %>
          <td>&nbsp;</td>

 </tr>
<tr>
<td >
<div class="buttonwrapper">
<% if(CU.getIdStabilimento() > 0) {%>
<a class="ovalbutton" href = "javascript: if (document.getElementById('abilita_formali').value == 'true' && document.getElementById('descrizione_or_combo_sel_formali').value == 'false' && trim(document.getElementById('valutazione_rischio_formali').value) != 'INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' FORMALI RISCONTRATE' && trim(document.getElementById('valutazione_rischio_formali').value) != '') { openNotePopupFollowup('<%=CU.getIdStabilimento() %>','<%=CU.getId() %>','<%=CU.getAssignedDate() %>',1) }else{ alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo , e inserita la relativa descrizione, e compilata la valutazione del rischio !') }" onclick="return !this.disabled;" ><span >Inserisci Follow Up</span></a>
<!--<td><a href = "#dialog" name = "modal" > Aggiungi Follo wup da pop up modale</a>-->
<% } else { %>
<a class="ovalbutton" href = "javascript: if (document.getElementById('abilita_formali').value == 'true' && document.getElementById('descrizione_or_combo_sel_formali').value == 'false' && trim(document.getElementById('valutazione_rischio_formali').value) != 'INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' FORMALI RISCONTRATE' && trim(document.getElementById('valutazione_rischio_formali').value) != '') { openNotePopupFollowup('<%=CU.getIdApiario() %>','<%=CU.getId() %>','<%=CU.getAssignedDate() %>',1) }else{ alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo , e inserita la relativa descrizione, e compilata la valutazione del rischio !') }" onclick="return !this.disabled;" ><span >Inserisci Follow Up</span></a>
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
 <tr id = "lista_followup_formali"><td><label></label></td><td><label></label></td></tr>
  
  </table>
  </td>
  </tr>
  </table>
  <input type = "hidden" name = "followup_inseriti_formali" id = "followup_inseriti_formali" value = "">
  <input type = "hidden" name = "stato_formali" id = "stato_formali" value = "true">
  <input type = "hidden" name = "descrizione_or_combo_sel" id = "descrizione_or_combo_sel_formali" value = "false">
  
