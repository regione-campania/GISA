<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.sintesis.base.SintesisOperatoreMercato"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>

<script>
function gestioneSanzione(button){
	var relOperatoreMercato = -1;
	if (document.getElementById("OperatoreMercato3_1")!=null)
		relOperatoreMercato = document.getElementById("OperatoreMercato3_1").value;
	
	if (relOperatoreMercato>0){
		 openNotePopupSanzioneOperatoreMercato(relOperatoreMercato, '<%=CU.getAltId()%>', '<%=CU.getId()%>','<%=CU.getAssignedDate()%>',3);
	}
	else {	
		openNotePopupSanzione('<%=CU.getAltId()%>','<%=CU.getId()%>','<%=CU.getAssignedDate()%>',3);
		}
}
</script>


<h2>Non Conformita Gravi</h2>
  
  <table>
  <tr id = "show_gravi">
    
    <td>
     <input type = "hidden" id = "elementi_nc_gravi" name = "elementi_nc_gravi" value = "1">
   	<input type = "hidden" id = "size_nc_gravi" name = "size_nc_gravi" value = "1">
    <table border="0" cellspacing="0" cellpadding="10" class="empty"  >
        <tr >
        <td>
        <table >
          <tr><td colspan="3"><input type = "button" value = "Inserisci un'altra NC Grave" onclick="return clonaNC_Gravi()"><input type = "button" value = "Elimina NC Grave" onclick="removeGravi('false');"><input type = "button" value = "Reset NC Gravi" onclick="resetGravi('<%=CU.getId()%>',3,true)" ></td></tr>
        <tr id = "nc_gravi_1">
        <td>
        <label>1</label>
           <textarea name="nonConformitaGravi_1" id = "nonConformitaGravi_1" onchange="abilitaFlagGravi();abilitaStatoGravi('<%=CU.getId()%>');calcolaPuntiNonConformita(document.getElementById('elementi_nc_gravi').value,'Provvedimenti3_','puntiGravi','nonConformitaGravi_',<%=CU.getTipoCampione()%>,'<%=CU.getAssignedDate()%>');" cols="55" rows="6" onclick="if(this.value=='INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA\''){this.value=''};abilitaFlagGravi();">INSERIRE QUI LA DESCRIZIONE DELLA SINGOLA NON CONFORMITA'</textarea>
          </td>
         <td>
        <center> &nbsp;</center>
           <br>
        <%
        	if ((CU.getTipoCampione()==4  || CU.getTipoCampione()==2 ||  (CU.getTipoCampione()==3 && CU.getAssignedDate().after(java.sql.Timestamp.valueOf(org.aspcf.modules.controlliufficiali.base.ApplicationProperties.getProperty("TIMESTAMP_NUOVA_GESTIONE_OGGETTO_DEL_CONTROLLO_AUDIT"))) )) && CU.getLisaElementi_Ispezioni().size()!=0)
                	{
                		Iterator<Integer> ite = CU.getLisaElementi_Ispezioni().keySet().iterator();
        %>
        		<select name = "Provvedimenti3_1" size="6"  id = "Provvedimenti3_1"  onchange="abilitaFlagGravi();abilitaStatoGravi('<%=CU.getId()%>');calcolaPuntiNonConformita(document.getElementById('elementi_nc_gravi').value,'Provvedimenti3_','puntiGravi','nonConformitaGravi_',<%=CU.getTipoCampione()%>,'<%=CU.getAssignedDate()%>'); gestisciBenessereNonConformita(this)" size = "9">	
        		<option value = "-1" selected="selected">-- SELEZIONA UNA TIPOLOGIA DI NC --</option>
        		<%
        			while(ite.hasNext())
        		        		{
        		        			int code = ite.next();
        		        			
        		        			HashMap<Integer, String> lista =  CU.getLisaElementi_Ispezioni().get(code);
        		        			Iterator<Integer> ite2 = lista.keySet().iterator();
        		%>
        			<optgroup label="<%=Macrocategorie.getValueFromId(code)%>" style="color: blue"></optgroup>
        			<%
        				while(ite2.hasNext())
        			            		{
        			        				int codice = ite2.next();
        			%>
        			<option value ="<%=codice%>"><%=lista.get(codice)%></option>
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
        		Date dateAssignedDate = new Date();
        	        	dateAssignedDate.setTime(CU.getAssignedDate().getTime());
        	        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        	        	Provvedimenti.setSelectSize(8);
        	        	Provvedimenti.setJsEvent("onchange=abilitaFlagGravi();abilitaStatoGravi('"+CU.getId()+"');calcolaPuntiNonConformita(document.getElementById('elementi_nc_gravi').value,'Provvedimenti3_','puntiGravi','nonConformitaGravi_','"+CU.getTipoCampione()+"','"+CU.getAssignedDate()+"'); gestisciBenessereNonConformita(this)");
        	%>
         <%=Provvedimenti.getHtmlSelect("Provvedimenti3_1",TicketDetails.getProvvedimenti())%>
         <%
         	}
         %>
         
           <%
                    	ProvvedimentiBenessereMacellazione.setJsEvent("style=\"display:none\"");
                    %>
         <%=ProvvedimentiBenessereMacellazione.getHtmlSelect("ProvvedimentiBenessereMacellazione3_1", -1)%>
         <%
         	ProvvedimentiBenessereTrasporto.setJsEvent("style=\"display:none\"");
         %>
         <%=ProvvedimentiBenessereTrasporto.getHtmlSelect("ProvvedimentiBenessereTrasporto3_1", -1)%>
         
           <input type = "hidden" name = "Provvedimenti3_1_selezionato" id = "Provvedimenti3_1_selezionato" value = "-1">
       
         <%
                	if (listaLineeCU!=null && listaLineeCU.size()>0) {
                %>
         <br/>
          Linea sottoposta a non conformita'<br/>
          <select id="Linea3_1" name="Linea3_1">
          <%
          	for (int k = 0; k<listaLineeCU.size(); k++) {
                    LineeAttivita linea = (LineeAttivita) listaLineeCU.get(k);
          %>
          <option value="<%=linea.getId()%>"><%=(linea.getMacroarea()!= null ? linea.getMacroarea() + "->" : "") + (linea.getAggregazione()!=null ? linea.getAggregazione() + "->" : linea.getCategoria()!=null ? linea.getCategoria() + "->" : "") + (linea.getAttivita()!=null ? linea.getAttivita() + "->" : linea.getLinea_attivita()!=null ? linea.getLinea_attivita() : "")%></option>
          <%
          	}
          %>
          </select>
          <br/><br/> 
          <%
           	}
           %> 
          
                    
		  <%
                               		  	if (listaOperatoriMercatoCU!=null && listaOperatoriMercatoCU.size()>0) {
                               		  %>
          <br/>
          Operatore mercato sottoposto a non conformita'<br/>
          <select id="OperatoreMercato3_1" name="OperatoreMercato3_1">
          <option value="-1" selected>--Tutto il mercato--</option>
          <%
          	for (int k = 0; k<listaOperatoriMercatoCU.size(); k++) {
                    SintesisOperatoreMercato operatore = (SintesisOperatoreMercato) listaOperatoriMercatoCU.get(k);
          %>
          <option value="<%=operatore.getId()%>">Num. Box: <%=operatore.getNumBox()%> - <%= operatore.getOpuStabilimento()!=null ? operatore.getOpuStabilimento().getOperatore().getRagioneSociale() : operatore.getOrgStabilimento()!=null ? operatore.getOrgStabilimento().getName() : "" %></option>
          <% } %>
          </select>
          <br/><br/>
          <% } %>         
          
          
        </td>
          </tr>
          </table>
      </td></tr>
      <tr>
          <%-- <td>
            <textarea id = "valutazione_rischio_gravi" name="nonConformitaGraviValutazione" cols="55" rows="6" onclick="if (this.value =='INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA\' GRAVI RISCONTRATE'){this.value=''}">INSERIRE LA VALUTAZIONE DEL RISCHIO DELLE NON CONFORMITA' GRAVI RISCONTRATE</textarea>
          </td>
          --%>
          <%if(CU.getTipoCampione()!=5){ %>
          <td>Punteggio
          <input type="text" value="" name="puntiGravi" id="puntiGravi" readonly="readonly" onchange="calcolaTotale()">
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
      <td><a  class="ovalbutton" href = "javascript: if (document.getElementById('abilita_gravi').value == 'true'  && document.getElementById('descrizione_or_combo_sel_gravi').value == 'false') { gestioneSanzione(this); }else{alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo ed inserita la relativa descrizione')}"  onclick="return !this.disabled;"><span> Inserisci Sanzione</span></a></td>      
     <td><a  class="ovalbutton" href = "javascript: if (document.getElementById('abilita_gravi').value == 'true'  && document.getElementById('descrizione_or_combo_sel_gravi').value == 'false') {openNotePopupDiffida('<%=CU.getAltId() %>','<%=CU.getId() %>','<%=CU.getAssignedDate() %>',3)}else{alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo ed inserita la relativa descrizione')}"  onclick="return !this.disabled;"><span> Inserisci Diffida</span></a></td> 
      
      <td><a  class="ovalbutton" href = "javascript: if (document.getElementById('abilita_gravi').value == 'true'  && document.getElementById('descrizione_or_combo_sel_gravi').value == 'false') {openNotePopupSequestro('<%=CU.getAltId() %>','<%=CU.getId() %>','<%=CU.getAssignedDate() %>',3)}else{alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo ed inserita la relativa descrizione')}"  onclick="return !this.disabled;"> <span>Inserisci Sequestro</span></a></td>
      <td><a  class="ovalbutton" href = "javascript: if (document.getElementById('abilita_gravi').value == 'true'  && document.getElementById('descrizione_or_combo_sel_gravi').value == 'false') {openNotePopupReato('<%=CU.getAltId() %>','<%=CU.getId() %>','<%=CU.getAssignedDate() %>',3)}else{alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo ed inserita la relativa descrizione ')}"  onclick="return !this.disabled;"> <span>Inserisci Notizia di Reato</span></a></td>
     <td><a  class="ovalbutton" href =  "javascript: if (document.getElementById('abilita_gravi').value == 'true'  && document.getElementById('descrizione_or_combo_sel_gravi').value == 'false') {openNotePopupFollowup('<%=CU.getAltId() %>','<%=CU.getId() %>','<%=CU.getAssignedDate() %>',3)}else{alert('Controllare che per ogni non conformita\' aggiunta sia stato selezionato un tipo ed inserita la relativa descrizione')}"  onclick="return !this.disabled;"><span> Inserisci altri Follow up</span></a></td>
     
    </tr>
    </table>
    </div></td>
        </tr>
      </table>
    </td>
  </tr>
   <tr>
  <td>
     <table cellpadding="4" cellspacing="0" width="100%" class = "noborder" >
		
		<thead><tr style="background-color: rgb(204, 255, 153);">
		<td colspan="2"><b>Elenco dei follow up aggiunti (Inserire almeno un follow up se sono state riscontrate n.c. Gravi) </b></td>
		</tr>
		
  </thead>
 <tr id = "lista_followup_gravi"><td><label></label></td><td><label></label></td></tr>
  
  </table>
  </td>
  </tr>
  
  </table>
      <input type = "hidden" name = "attivita_inseriti_gravi" id = "attivita_inseriti_gravi" value = "">
     <input type = "hidden" name = "followup_gravi_inseriti" id = "followup_gravi_inseriti" value = "">
    <input type = "hidden" name = "stato_gravi" id = "stato_gravi" value = "true">
  <input type = "hidden" name = "descrizione_or_combo_sel_gravi" id = "descrizione_or_combo_sel_gravi" value = "false">