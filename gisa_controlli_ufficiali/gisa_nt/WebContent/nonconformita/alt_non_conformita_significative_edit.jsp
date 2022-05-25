<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.nonconformita.base.ElementoNonConformita"%>
<h2>Non Conformita Significative</h2>
<table>
  <tr  id = "show_significative">
    <td>
     <input type = "hidden" id = "elementi_nc_significative" name = "elementi_nc_significative" value = "<%=(TicketDetails.getNon_conformita_significative().size()) %>">
   		<input type = "hidden" id = "size_nc_significative" name = "size_nc_significative" value = "<%=(TicketDetails.getNon_conformita_significative().size()) %>">
      <table border="0" cellspacing="0" cellpadding="10" class="empty"  >
        <tr>
        <td>
        <table>
           <%
        String nc_sign_inserite_s = "";
        String note_s = "" ;
        for(ElementoNonConformita nc : TicketDetails.getNon_conformita_significative())
        {
        	nc_sign_inserite_s+=nc.getId_nc()+";";
        	note_s += nc.getNote()+";";
        }
        %>
        <tr><td colspan="3"><input type = "button" value = "Inserisci un'altra NC Significative" onclick="return clonaNC_significative()"><input type = "button" value = "Elimina NC Significative"  onclick="removeSignificative('false',<%=TicketDetails.getNon_conformita_significative().size() %>);"><input type = "button" value = "Reset NC Significative" onclick="resetSignificative_update('<%=CU.getId() %>',2,<%=TicketDetails.getNon_conformita_significative().size() %>,'<%=request.getAttribute("attivita_significative") %>','<%=nc_sign_inserite_s %>','<%=note_s %>')" ></td></tr>
        <%
      	int curr = 0 ;
        for (ElementoNonConformita elementoNc : TicketDetails.getNon_conformita_significative())
        {
        	curr++ ;
        	%>
        <tr id = "nc_significative_<%=elementoNc.getProgressivo_nc() %>">
         
         <td>
         <label><%="<b>"+elementoNc.getProgressivo_nc()+"</b>" %></label>
            <textarea name="nonConformitaSignificative_<%=elementoNc.getProgressivo_nc() %>"  id = "nonConformitaSignificative_<%=elementoNc.getProgressivo_nc() %>" onchange="abilitaFlagSignificative();abilitaStatoSignificative('<%=CU.getId()%>');calcolaPuntiNonConformita(document.getElementById('elementi_nc_significative').value,'Provvedimenti2_','puntiSignificativi','nonConformitaSignificative_',<%=CU.getTipoCampione() %>,'<%=CU.getAssignedDate() %>');" cols="55" rows="6" onclick="if(this.value=='INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\''){this.value=''};abilitaFlagSignificative();"><%=elementoNc.getNote().replaceAll("PER LA SINGOLA NON CONFORMITA","DELLA SINGOLA NON CONFORMITA'") %></textarea>
          </td>
        <td>
         
        <%
        	if ((CU.getTipoCampione()==4  || CU.getTipoCampione()==2 ||  (CU.getTipoCampione()==3 && CU.getAssignedDate().after(java.sql.Timestamp.valueOf(org.aspcf.modules.controlliufficiali.base.ApplicationProperties.getProperty("TIMESTAMP_NUOVA_GESTIONE_OGGETTO_DEL_CONTROLLO_AUDIT"))) )) && CU.getLisaElementi_Ispezioni().size()!=0)
        	{
        		Iterator<Integer> ite = CU.getLisaElementi_Ispezioni().keySet().iterator();
        		%>
        		<select name = "Provvedimenti2_<%=elementoNc.getProgressivo_nc() %>" id = "Provvedimenti2_<%=elementoNc.getProgressivo_nc() %>" size = "6" onchange="abilitaFlagSignificative();abilitaStatoSignificative('<%=CU.getId()%>');calcolaPuntiNonConformita(document.getElementById('elementi_nc_significative').value,'Provvedimenti2_','puntiSignificativi','nonConformitaSignificative_',<%=CU.getTipoCampione() %>,'<%=CU.getAssignedDate() %>');sevValueNcSignificativeModify(<%=curr %>); gestisciBenessereNonConformita(this)" size = "9">	
        		
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
        	Provvedimenti.setJsEvent("onchange=abilitaFlagSignificative();abilitaStatoSignificative('"+CU.getId()+"');calcolaPuntiNonConformita(document.getElementById('elementi_nc_significative').value,'Provvedimenti2_','puntiSignificativi','nonConformitaSignificative_',"+CU.getTipoCampione()+","+CU.getAssignedDate()+"');sevValueNcSignificativeModify("+curr+"); gestisciBenessereNonConformita(this)"); %>
         <%=Provvedimenti.getHtmlSelect("Provvedimenti2_"+elementoNc.getProgressivo_nc() ,elementoNc.getId_nc()) %>
         <%} %>
         
          
         <% if (elementoNc.getId_nc_benessere_macellazione()<=0) {ProvvedimentiBenessereMacellazione.setJsEvent("style=\"display:none\""); }%>
         <%=ProvvedimentiBenessereMacellazione.getHtmlSelect("ProvvedimentiBenessereMacellazione2_"+elementoNc.getProgressivo_nc(), elementoNc.getId_nc_benessere_macellazione()) %>
      	 <% if (elementoNc.getId_nc_benessere_trasporto()<=0) {ProvvedimentiBenessereTrasporto.setJsEvent("style=\"display:none\""); }%>
         <%=ProvvedimentiBenessereTrasporto.getHtmlSelect("ProvvedimentiBenessereTrasporto2_"+elementoNc.getProgressivo_nc(), elementoNc.getId_nc_benessere_trasporto()) %>
      
         <input type = "hidden" name = "Provvedimenti2_<%=elementoNc.getProgressivo_nc() %>_selezionato" id = "Provvedimenti2_<%=elementoNc.getProgressivo_nc() %>_selezionato" value = "<%=elementoNc.getId_nc() %>">
       
       <%if (listaLineeCU!=null && listaLineeCU.size()>0) { %>
        <br/>
          Linea sottoposta a non conformita'<br/>
          <select id="Linea2_<%=elementoNc.getProgressivo_nc() %>" name="Linea2_<%=elementoNc.getProgressivo_nc() %>">
          <%for (int k = 0; k<listaLineeCU.size(); k++) {
          LineeAttivita linea = (LineeAttivita) listaLineeCU.get(k);%>
          <option value="<%=linea.getId()%>" <%=elementoNc.getId_linea_nc()==linea.getId() ? "selected" : "" %>><%=(linea.getMacroarea()!= null ? linea.getMacroarea() + "->" : "") + (linea.getAggregazione()!=null ? linea.getAggregazione() + "->" : linea.getCategoria()!=null ? linea.getCategoria() + "->" : "") + (linea.getAttivita()!=null ? linea.getAttivita() + "->" : linea.getLinea_attivita()!=null ? linea.getLinea_attivita() : "") %></option>
          <% } %>
          </select>
          <br/><br/>
          <% } %>
          
           <%if (listaOperatoriMercatoCU!=null && listaOperatoriMercatoCU.size()>0) { %>
          <br/>
          Operatore mercato sottoposto a non conformita'<br/>
          <select id="OperatoreMercato2_<%=elementoNc.getProgressivo_nc() %>" name="OperatoreMercato2_<%=elementoNc.getProgressivo_nc() %>">
          <option value="-1" <%=elementoNc.getId_operatore_mercato()==-1 ? "selected" : "" %>>--Tutto il mercato--</option>
          <%
          	for (int k = 0; k<listaOperatoriMercatoCU.size(); k++) {
                    SintesisOperatoreMercato operatore = (SintesisOperatoreMercato) listaOperatoriMercatoCU.get(k);
          %>
          <option value="<%=operatore.getId()%>" <%=elementoNc.getId_operatore_mercato()==operatore.getId() ? "selected" : "" %>>Num. Box: <%=operatore.getNumBox()%> - <%= operatore.getOpuStabilimento()!=null ? operatore.getOpuStabilimento().getOperatore().getRagioneSociale() : operatore.getOrgStabilimento()!=null ? operatore.getOrgStabilimento().getName() : "" %></option>
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
            <textarea id = "valutazione_rischio_significative" name="nonConformitaSignificativeValuazione" cols="55" rows="6" onclick="if (this.value =='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' SIGNIFICATIVE RISCONTRATE'){this.value=''}"><%=toHtml(TicketDetails.getNcSignificativeValutazioni()) %></textarea>
          </td>
          
          <%if(CU.getTipoCampione()!=5){ %>
          <td>
           <center> Punteggio </center><br>
             <input type="text" value="<%=TicketDetails.getPuntiSignificativi() %>" name="puntiSignificativi" id="puntiSignificativi" readonly="readonly" onchange="calcolaTotale()">
             
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
        <a class="ovalbutton" href = "javascript: if (document.getElementById('abilita_significative').value == 'true' && trim(document.getElementById('valutazione_rischio_significative').value) != '' && trim(document.getElementById('valutazione_rischio_significative').value) != 'INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' SIGNIFICATIVE RISCONTRATE' &&  document.getElementById('descrizione_or_combo_sel_significative').value == 'false') { openNotePopupFollowup('<%=CU.getAltId() %>','<%=CU.getId() %>','<%=CU.getAssignedDate() %>',2)}else {alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo , inserita la relativa descrizione e aver compilato la valutazione del rischio !')}"  onclick="return !this.disabled;"><span> Inserisci Follow up</span></a>
        
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
		<td colspan="2"><b>Elenco dei follow up aggiunti (Inserire almeno un follow up se sono state riscontrate n.c. Significative)</b></td>
		</tr>
		
  </thead>
 <tr id = "lista_followup_significativi" style="display: none"><td><label></label></td><td><label></label></td></tr>
  
  </table>
  </td>
  </tr>
  
  </table>
   <input type = "hidden" name = "followup_inseriti_significativi" id = "followup_inseriti_significativi"  value = "<%=request.getAttribute("attivita_significative") %>">
  <input type = "hidden" name = "stato_significative" id = "stato_significative" value = "true">
  <input type = "hidden" name = "descrizione_or_combo_sel" id = "descrizione_or_combo_sel_significative" value = "false">