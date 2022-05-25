<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.nonconformita.base.ElementoNonConformita"%>

<script> function gestioneSanzione(button){
	
	if (document.getElementById("id_impresa_sanzionata")!=null)
		{
		var impresa = document.getElementById("id_impresa_sanzionata").value;
		 if (document.getElementById('abilita_gravi').value == 'true'  && document.getElementById('descrizione_or_combo_sel_gravi').value == 'false') {
			 openNotePopupSanzioneTerzi(impresa, '<%=CU.getOrgId() %>', '<%=CU.getId() %>','<%=CU.getAssignedDate() %>',3);
		 }
	 else{
		 alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo ed inserita la relativa descrizione');
	 }
		}
	
	else 
		{
		 if (document.getElementById('abilita_gravi').value == 'true'  && document.getElementById('descrizione_or_combo_sel_gravi').value == 'false') {
			 openNotePopupSanzione('<%=CU.getOrgId() %>','<%=CU.getId() %>','<%=CU.getAssignedDate() %>',3);
		 }
	 else{
		 alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo ed inserita la relativa descrizione');
	 }
		}
	
	 return !button.disabled;
}



</script>


<h2>Non Conformita Gravi</h2>
  
  <table>
  <tr id = "show_gravi">
    
    <td colspan="3">
     <input type = "hidden" id = "elementi_nc_gravi" name = "elementi_nc_gravi" value = "<%=(TicketDetails.getNon_conformita_gravi().size()) %>">
   	<input type = "hidden" id = "size_nc_gravi" name = "size_nc_gravi"  value = "<%=(TicketDetails.getNon_conformita_gravi().size()) %>">
    <table border="0" cellspacing="0" cellpadding="10" class="empty"  >
        <tr >
        <td>
        <table >
        <%
        String nc_gravi_inserite = "";
        String note = "" ;
        for(ElementoNonConformita nc : TicketDetails.getNon_conformita_gravi())
        {
        	nc_gravi_inserite+=nc.getId_nc()+";";
        	note += nc.getNote().replaceAll("'"," ")+";";
        }
        %>
          <tr><td><input type = "button" value = "Inserisci un'altra NC Grave" onclick="return clonaNC_Gravi()"><input type = "button" value = "Elimina NC Grave" onclick="removeGravi('false',<%=TicketDetails.getNon_conformita_gravi().size() %>);"><input type = "button" value = "Reset Nc Gravi" onclick="resetGravi_update('<%=CU.getId() %>',3,<%=TicketDetails.getNon_conformita_gravi().size() %>,'<%=request.getAttribute("attivita_gravi") %>','<%=nc_gravi_inserite %>','<%=note.replaceAll("'","") %>')" ></td></tr>
         <%
        int numEl = 1 ;
        for (ElementoNonConformita elementoNc : TicketDetails.getNon_conformita_gravi()) 
        {
        	
        	%>
        
        <tr id = "nc_gravi_<%=numEl%>">
         
        <td>
        <label><%="<b>"+elementoNc.getProgressivo_nc()+"</b>" %></label>
           <textarea name="nonConformitaGravi_<%=elementoNc.getProgressivo_nc() %>" id = "nonConformitaGravi_<%=elementoNc.getProgressivo_nc() %>" onchange="abilitaFlagGravi();abilitaStatoGravi('<%=CU.getId()%>');
           calcolaPuntiNonConformita(document.getElementById('elementi_nc_gravi').value,'Provvedimenti3_','puntiGravi','nonConformitaGravi_',<%=CU.getTipoCampione() %>,'<%=CU.getAssignedDate() %>');" cols="55" rows="6" onclick="if(this.value=='INSERIRE QUI LA DESCRIZIONE DELLA LA SINGOLA NON CONFORMITA\''){this.value=''};abilitaFlagGravi();"><%=elementoNc.getNote().replaceAll("PER LA SINGOLA NON CONFORMITA","DELLA SINGOLA NON CONFORMITA'").replaceAll("'"," ") %></textarea>
          </td>
         <td>
        <center> &nbsp;</center>
           <br>
        <%
        	if ((CU.getTipoCampione()==4  || CU.getTipoCampione()==2 ||  (CU.getTipoCampione()==3 && CU.getAssignedDate().after(java.sql.Timestamp.valueOf(org.aspcf.modules.controlliufficiali.base.ApplicationProperties.getProperty("TIMESTAMP_NUOVA_GESTIONE_OGGETTO_DEL_CONTROLLO_AUDIT"))) )) && CU.getLisaElementi_Ispezioni().size()!=0)
        	{
        		Iterator<Integer> ite = CU.getLisaElementi_Ispezioni().keySet().iterator();
        		%>
        		<select name = "Provvedimenti3_<%=elementoNc.getProgressivo_nc() %>" size="6"  id = "Provvedimenti3_<%=elementoNc.getProgressivo_nc() %>"  onchange="abilitaFlagGravi();abilitaStatoGravi('<%=CU.getId()%>');calcolaPuntiNonConformita(document.getElementById('elementi_nc_gravi').value,'Provvedimenti3_','puntiGravi','nonConformitaGravi_',<%=CU.getTipoCampione() %>,'<%=CU.getAssignedDate() %>');sevValueNcGraviModify(<%=numEl %>); gestisciBenessereNonConformita(this)" size = "9">	
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
        			<option value ="<%=codice %>"  <%if (codice==elementoNc.getId_nc()){%>selected="selected"<%} %>><%=lista.get(codice) %></option>
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
        	Provvedimenti.setJsEvent("onchange=abilitaFlagGravi();abilitaStatoGravi('"+CU.getId()+"');sevValueNcGraviModify("+numEl+"); gestisciBenessereNonConformita(this)"); %>
         <%= Provvedimenti.getHtmlSelect("Provvedimenti3_"+elementoNc.getProgressivo_nc(),elementoNc.getId_nc())%>
         <%} %>
         
            
         <% if (elementoNc.getId_nc_benessere_macellazione()<=0) {ProvvedimentiBenessereMacellazione.setJsEvent("style=\"display:none\""); }%>
         <%=ProvvedimentiBenessereMacellazione.getHtmlSelect("ProvvedimentiBenessereMacellazione3_"+elementoNc.getProgressivo_nc(), elementoNc.getId_nc_benessere_macellazione()) %>
      	 <% if (elementoNc.getId_nc_benessere_trasporto()<=0) {ProvvedimentiBenessereTrasporto.setJsEvent("style=\"display:none\""); }%>
         <%=ProvvedimentiBenessereTrasporto.getHtmlSelect("ProvvedimentiBenessereTrasporto3_"+elementoNc.getProgressivo_nc(), elementoNc.getId_nc_benessere_trasporto()) %>
        
           <input type = "hidden" name = "Provvedimenti3_<%=elementoNc.getProgressivo_nc() %>_selezionato" id = "Provvedimenti3_<%=elementoNc.getProgressivo_nc() %>_selezionato" value = "<%=elementoNc.getId_nc() %>">
       
       
        <%if (listaLineeCU!=null && listaLineeCU.size()>0) { %>
        <br/>
          Linea sottoposta a non conformita'<br/>
          <select id="Linea3_<%=elementoNc.getProgressivo_nc() %>" name="Linea3_<%=elementoNc.getProgressivo_nc() %>">
          <%for (int k = 0; k<listaLineeCU.size(); k++) {
          LineeAttivita linea = (LineeAttivita) listaLineeCU.get(k);%>
          <option value="<%=linea.getId()%>" <%=elementoNc.getId_linea_nc()==linea.getId() ? "selected" : "" %>><%=(linea.getMacroarea()!= null ? linea.getMacroarea() + "->" : "") + (linea.getAggregazione()!=null ? linea.getAggregazione() + "->" : linea.getCategoria()!=null ? linea.getCategoria() + "->" : "") + (linea.getAttivita()!=null ? linea.getAttivita() + "->" : linea.getLinea_attivita()!=null ? linea.getLinea_attivita() : "") %></option>
          <% } %>
          </select>
          <br/><br/>
          <% } %>
           </td>
          </tr>
             <%
        numEl++;     
        } %>
          
          </table>
      </td></tr>
      <tr>
          <%-- <td>
            <textarea id = "valutazione_rischio_gravi" name="nonConformitaGraviValutazione" cols="55" rows="6" onclick="if (this.value =='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' GRAVI RISCONTRATE'){this.value=''}"><%=toHtml(TicketDetails.getNcGraviValutazioni()) %></textarea>
          </td>
          --%> 
          <%if(CU.getTipoCampione()!=5){ %>
          <td>Punteggio
          <input type="text" value="<%=TicketDetails.getPuntiGravi()%>" name="puntiGravi" id="puntiGravi" readonly="readonly" onchange="calcolaTotale()">
          </td>
          <%} else{%>
          <td>
          Punteggio già calcolato nella check list.
          </td>
          <%} %>
             <td>&nbsp;&nbsp;&nbsp;</td>

        </tr>
        <tr>
            <td>
            <div class="buttonwrapper">
    <table class = "noborder">
     <tr>
     
 <td><a href="#" class="ovalbutton"  onclick="gestioneSanzione(this);"><span> Inserisci Sanzione</span></a></td>
       <td><a  class="ovalbutton" href = "javascript: if (document.getElementById('abilita_gravi').value == 'true'  && document.getElementById('descrizione_or_combo_sel_gravi').value == 'false') {openNotePopupDiffida('<%=CU.getOrgId() %>','<%=CU.getId() %>','<%=CU.getAssignedDate() %>',3)}else{alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo ed inserita la relativa descrizione')}"  onclick="return !this.disabled;"><span> Inserisci Diffida</span></a></td> 
      <td><a  class="ovalbutton" href = "javascript: if (document.getElementById('abilita_gravi').value == 'true'  && document.getElementById('descrizione_or_combo_sel_gravi').value == 'false') {openNotePopupSequestro('<%=CU.getOrgId() %>','<%=CU.getId() %>','<%=CU.getAssignedDate() %>',3)}else{alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo ed inserita la relativa descrizione')}"  onclick="return !this.disabled;"> <span>Inserisci Sequestro</span></a></td>
      <td><a  class="ovalbutton" href = "javascript: if (document.getElementById('abilita_gravi').value == 'true'  && document.getElementById('descrizione_or_combo_sel_gravi').value == 'false') {openNotePopupReato('<%=CU.getOrgId() %>','<%=CU.getId() %>','<%=CU.getAssignedDate() %>',3)}else{alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo ed inserita la relativa descrizione')}"  onclick="return !this.disabled;"> <span>Inserisci Notizia di Reato</span></a></td>
     
      <%
    if(User.getUserRecord().getGruppo_ruolo()==1)
    {
    %>
     <td><a  class="ovalbutton" href = "javascript:  if (document.getElementById('abilita_gravi').value == 'true'  && document.getElementById('descrizione_or_combo_sel_gravi').value == 'false') {openNotePopupFollowup('<%=CU.getOrgId() %>','<%=CU.getId() %>','<%=CU.getAssignedDate() %>',3)}else{alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo ed inserita la relativa descrizione')}"  onclick="return !this.disabled;"><span> Inserisci altri Follow up</span></a></td>
    <%} %>
    </tr>
    </table>
    </div></td>
        
        </tr>
        
      </table>
    </td>
  </tr>
   <tr>
  <td colspan="3">
     <table cellpadding="4" cellspacing="0" width="100%" class = "noborder" >
		
		<thead><tr style="background-color: rgb(204, 255, 153);">
		<td colspan="2"><b>Elenco dei follow up aggiunti (Inserire almeno un follow up se sono state riscontrate n.c. Gravi)</b></td>
		</tr>
		
  </thead>
 <tr id = "lista_followup_gravi" style="display: none"><td><label></label></td><td><label></label></td></tr>
  
  </table>
  </td>
  </tr>
  
  </table>
   <input type = "hidden" name = "attivita_inseriti_gravi" id = "attivita_inseriti_gravi" value = "<%=request.getAttribute("attivita_gravi") %>">
   <input type = "hidden" name = "followup_gravi_inseriti" id = "followup_gravi_inseriti" value = "">
  
    <input type = "hidden" name = "stato_gravi" id = "stato_gravi" value = "true">
  <input type = "hidden" name = "descrizione_or_combo_sel_gravi" id = "descrizione_or_combo_sel_gravi" value = "false">