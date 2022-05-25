<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

  <%SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy"); 
  String data = "" ;
  if (TicketDetails.getAssignedDate()!=null)
  {
	 data = sdfData.format(new Date (TicketDetails.getAssignedDate().getTime()));
  }
  
  %>
  <input type = "hidden" name = "data_iniziale"  id = "data_iniziale" value = "<%=data %>"/>
 <input type = "hidden" name = "dataNuovaGestioneOggettoControlloAudit"  id = "dataNuovaGestioneOggettoControlloAudit" value = "<%=toDateasString(java.sql.Timestamp.valueOf(org.aspcf.modules.controlliufficiali.base.ApplicationProperties.getProperty("TIMESTAMP_NUOVA_GESTIONE_OGGETTO_DEL_CONTROLLO_AUDIT"))) %>"/>
  
 <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Inizio Controllo</dhv:label>
      </td>
      <td>
      
      	<input readonly type="text" id="assignedDate" name="assignedDate" size="10" 
		value="<%= (TicketDetails.getAssignedDate()==null)?(""):(getLongDate(TicketDetails.getAssignedDate()))%>"/>
		<a href="#" onClick="cal19.select(document.forms[0].assignedDate,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
      	
 
        <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
      </td>
    </tr>
	<tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="">Data Fine Controllo</dhv:label>
      </td>
      <td>
      	<input readonly type="text" id="dataFineControllo" name="dataFineControllo" size="10" 
		value="<%= (TicketDetails.getDataFineControllo()==null)?(""):(getLongDate(TicketDetails.getDataFineControllo()))%>"/>
		<a href="#" onClick="cal19.select(document.forms[0].dataFineControllo,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
		
      </td>
    </tr>
 
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
	
	 <tr class="containerBody" id= "oggetto_controllo" style = "display:none">
    <td valign="top" class="formLabel">
      Oggetto Del Controllo
    </td>
    <td>
    <table class = "noborder">
    <tr>
    <td>
	<!-- onmouseout="abilitaNoteDescrizioni();abilitaSpecieTrasportata();" -->
	<select name = "ispezione"  <%if(TipoIspezione.size()>1){ %>size="10" multiple="multiple" <%} %> id = "ispezione" onmouseout="abilitaSpecieTrasportata();abilitaNoteDescrizioni();">
   <%
   

   
   Iterator<Integer> itLista = Ispezione.keySet().iterator();
   while (itLista.hasNext())
   {
	   int key = itLista.next();
	   %>
	   <optgroup label="<%=IspezioneMacrocategorie.getValueFromId(key)%>"  style="color: blue"></optgroup>
	   
	   <%
	   
	   HashMap<Integer,String> l = ( HashMap<Integer,String>) Ispezione.get(key);
	   Iterator<Integer> itL = l.keySet().iterator();
	   while (itL.hasNext())
	   {
		   int code = itL.next();
		   String desc = l.get(code);
		   boolean sel = false ;
		
			
			HashMap<Integer,String> lista = TicketDetails.getLisaElementi_Ispezioni().get(key);
			
			if (lista!=null)
			{
			Iterator<Integer> kiave1= lista.keySet().iterator();
			
			while(kiave1.hasNext()){
				
				int code2 = kiave1.next();
				if (code2 == code )
				{
					sel = true;
				}			
			}
			}
			
			
		   %>
		   
		   
		 <option value = "<%=code %>" <%if(sel==true) {%> selected="selected"<%} %>><%=desc %></option>
		   <%
		   	   }
	   
   }
   
   
   %>
	</select>
	</td>
	<td>&nbsp;</td>
	<td>
	<table>
			
			<tr id = "desc_note1" style = "display:none"><td><b>Settore Alimenti per il consumo Umano (Descrizione)</b> <br> <textarea rows = "3" cols = "20" name = "ispezioni_desc1"><%=toHtml2(TicketDetails.getIspezioni_desc1()) %></textarea></td></tr>
			<tr id = "desc_note2" style = "display:none"><td><b>Settore alimenti Zootecnici (Descrizione)</b><br><textarea rows = "3" cols = "20" name = "ispezioni_desc2" ><%=toHtml2(TicketDetails.getIspezioni_desc2()) %></textarea></td></tr>
			<tr id = "desc_note3" style = "display:none"><td><b>Settore Benessere Animale non durante il trasporto</b><br><textarea rows = "3" cols = "20" name = "ispezioni_desc3" ><%=toHtml2(TicketDetails.getIspezioni_desc3()) %></textarea></td></tr>
			<tr id = "desc_note4" style = "display:none"><td><b>Settore Sanita animale (Descrizione)</b><br><textarea rows = "3" cols = "20" name = "ispezioni_desc4" ><%=toHtml2(TicketDetails.getIspezioni_desc4()) %></textarea></td></tr>
			<tr id = "desc_note5" style = "display:none"><td><b>Settore S.O.A. negli Impianti di trasformazione (Descrizione)</b><br><textarea rows = "3" cols = "20" name = "ispezioni_desc5"><%=toHtml2(TicketDetails.getIspezioni_desc5()) %></textarea></td></tr>
			<tr id = "desc_note6" style = "display:none"><td><b>Settore Rifiuti S.O.A. nelle altre imprese (Descrizione)</b><br><textarea rows = "3" cols = "20" name = "ispezioni_desc6"><%=toHtml2(TicketDetails.getIspezioni_desc6()) %></textarea></td></tr>
			<tr id = "desc_note7" style = "display:none"><td><b>Altro (Descrizione)</b><br><textarea rows = "3" cols = "20" name = "ispezioni_desc7"><%=toHtml2(TicketDetails.getIspezioni_desc7()) %></textarea></td></tr>
			<tr id = "desc_note8" style = "display:none"><td><b>Settore Benessere Animale durante il trasporto</b><br><textarea rows = "3" cols = "20" name = "ispezioni_desc8"><%=toHtml2(TicketDetails.getIspezioni_desc8()) %></textarea></td></tr>
			
			
			</table>
	
	</td>
	</tr></table>
	</td>
	</tr>
	
	
		
	<tr id="specieT" style="display: none">
	<td nowrap class="formLabel">
		<dhv:label name="">Specie Animali Trasportati</dhv:label>
	</td>
	<td>
	<table border="0" cellspacing="0" cellpadding="0" class="empty">
		<tr>
			<td rowspan="3">
			
			
			</td>
			
			
				<%SpecieA.setJsEvent("onchange= abilitaNumCapi();"); %>
				<td><%=SpecieA.getHtmlSelect("animalitrasp",-1) %>
				&nbsp; <font color="red">*</font></td>
				
			<% if (TicketDetails.getNum_specie26() > 0){ %>
			<script>
			document.getElementById("animalitrasp").options[1].selected =true;
			</script>
			<% } %>
			
			<% if (TicketDetails.getNum_specie1() > 0){ %>
			<script>
			document.getElementById("animalitrasp").options[2].selected =true;
			</script>
			<% } %>
						
			<% if (TicketDetails.getNum_specie5() > 0){ %>
			<script>
			document.getElementById("animalitrasp").options[3].selected =true;
			</script>
			<% } %>
			
			<% if (TicketDetails.getNum_specie14() > 0){ %>
			<script>
			document.getElementById("animalitrasp").options[4].selected =true;
			</script>
			<% } %>
			
			<% if (TicketDetails.getNum_specie9() > 0){ %>
			<script>
			document.getElementById("animalitrasp").options[5].selected =true;
			</script>
			<% } %>
			
			<% if (TicketDetails.getNum_specie3() > 0){ %>
			<script>
			document.getElementById("animalitrasp").options[6].selected =true;
			</script>
			<% } %>
			
			<% if (TicketDetails.getNum_specie15() > 0){ %>
			<script>
			document.getElementById("animalitrasp").options[7].selected =true;
			</script>
			<% } %>
			
			<% if (TicketDetails.getNum_specie23() > 0){ %>
			<script>
			document.getElementById("animalitrasp").options[8].selected =true;
			</script>
			<% } %>
			
			<% if (TicketDetails.getNum_specie22() > 0){ %>
			<script>
			document.getElementById("animalitrasp").options[9].selected =true;
			</script>
			<% } %>
			
			<% if (TicketDetails.getNum_specie25() > 0){ %>
			<script>
			document.getElementById("animalitrasp").options[10].selected =true;
			</script>
			<% } %>
			
			<% if (TicketDetails.getNum_specie2() > 0){ %>
			<script>
			document.getElementById("animalitrasp").options[11].selected =true;
			</script>
			<% } %>
						
			<% if (TicketDetails.getNum_specie24() > 0){ %>
			<script>
			document.getElementById("animalitrasp").options[12].selected =true;
			</script>
			<% } %>
			
			<td>&nbsp;</td>
			<td>
			<table>               
			<tr>
					<td><b>Num. docum. di accompagnam. controllati</b><br>
				<input type="text" id="num_documento_accompagnamento" name="num_documento_accompagnamento" size="5" value="<%= TicketDetails.getNum_documento_accompagnamento() != null ? TicketDetails.getNum_documento_accompagnamento() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');" /></td>
			</tr>                                                
				<tr id="num_capo1" style="display: none">
					<td><b>Num. capi Bovini</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie1" name="num_specie1" size="5" value="<%= TicketDetails.getNum_specie1() > 0 ? TicketDetails.getNum_specie1() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo2" style="display: none">
				<td><b>Num. capi Suini</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie2" name="num_specie2" size="5" value="<%= TicketDetails.getNum_specie2() > 0 ? TicketDetails.getNum_specie2() : ""  %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo3" style="display: none">
					<td><b>Num. capi Equidi</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie4" name="num_specie3" size="5" value="<%= TicketDetails.getNum_specie3() > 0 ? TicketDetails.getNum_specie3() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo4" style="display: none">
					<td><b>Num. capi Altre Specie </b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie6" name="num_specie4" size="5" value="<%= TicketDetails.getNum_specie4() > 0 ? TicketDetails.getNum_specie4() : ""  %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo5" style="display: none">
					<td><b>Num. capi Bufali </b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie10" name="num_specie5" size="5" value="<%= TicketDetails.getNum_specie5() > 0 ? TicketDetails.getNum_specie5() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo6" style="display: none">
					<td><b>Num. Pesci acqua dolce</b><br>
					<input type="number" pattern="[0-9]" min="1"  id="num_specie11" name="num_specie6" size="5" value="<%= TicketDetails.getNum_specie6() > 0 ? TicketDetails.getNum_specie6() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo7" style="display: none">
					<td><b>Num. Pesci Ornamentali</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie12" name="num_specie7" size="5" value="<%= TicketDetails.getNum_specie7() > 0 ? TicketDetails.getNum_specie7() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo8" style="display: none">
					<td><b>Num. Oche</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie13" name="num_specie8" size="5" value="<%= TicketDetails.getNum_specie8() > 0 ? TicketDetails.getNum_specie8() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
 			<tr id="num_capo9" style="display: none">
					<td><b>Num. Conigli</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie14" name="num_specie9" size="5" value="<%= TicketDetails.getNum_specie9() > 0 ? TicketDetails.getNum_specie9() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo10" style="display: none">
					<td><b>Num. Ovaiole</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie15" name="num_specie10" size="5" value="<%= TicketDetails.getNum_specie10() > 0 ? TicketDetails.getNum_specie10() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo11" style="display: none">
					<td><b>Num. capi Broiler</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie16" name="num_specie11" size="5" value="<%= TicketDetails.getNum_specie11() > 0 ? TicketDetails.getNum_specie11() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo12" style="display: none">
					<td><b>Num. capi Vitelli</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie18" name="num_specie12" size="5"  value="<%= TicketDetails.getNum_specie12() > 0 ? TicketDetails.getNum_specie12() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo13" style="display: none">
					<td><b>Num. Struzzi</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie19" name="num_specie13" size="5" value="<%= TicketDetails.getNum_specie13() > 0 ? TicketDetails.getNum_specie13() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo14" style="display: none">
					<td><b>Num. Cani</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie20" name="num_specie14" size="5" value="<%= TicketDetails.getNum_specie14() > 0 ? TicketDetails.getNum_specie14() : "" %>"  onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');"/><font color="red">*</font></td>
			</tr>
			<tr id="num_capo15" style="display: none">
					<td><b>Num. capi di Ovicaprini</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie21" name="num_specie15" size="5" value="<%= TicketDetails.getNum_specie15() > 0 ? TicketDetails.getNum_specie15() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo puï¿½ contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo22" style="display: none">
					<td><b>Num. capi di Pollame</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie22" name="num_specie22" size="5" value="<%= TicketDetails.getNum_specie22() > 0 ? TicketDetails.getNum_specie22() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Pollame può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo23" style="display: none">
					<td><b>Num. Pesci</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie23" name="num_specie23" size="5" value="<%= TicketDetails.getNum_specie23() > 0 ? TicketDetails.getNum_specie23() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Pesci può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo24" style="display: none">
					<td><b>Num. Uccelli</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie24" name="num_specie24" size="5" value="<%= TicketDetails.getNum_specie24() > 0 ? TicketDetails.getNum_specie24() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Uccelli può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo25" style="display: none">
					<td><b>Num. Rettili</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie25" name="num_specie25" size="5" value="<%= TicketDetails.getNum_specie25() > 0 ? TicketDetails.getNum_specie25() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Num. Rettili può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			<tr id="num_capo26" style="display: none">
					<td><b>Num. capi Altre specie</b><br>
				<input type="number" pattern="[0-9]" min="1"  id="num_specie26" name="num_specie26" size="5" value="<%= TicketDetails.getNum_specie26() > 0 ? TicketDetails.getNum_specie26() : "" %>" onkeyup="javascript:if (isNaN(value)) alert ('Errore il campo Altro può contenere solo valori numerici');" /><font color="red">*</font></td>
			</tr>
			
			</table>
			</td>

		</tr>
	</table>
	</td>
</tr>

			<script>
			abilitaSpecieTrasportata();abilitaNoteDescrizioni();abilitaNumCapi();
			</script>
	
	
    <input type="hidden" name="ncrilevate" value="<%=(TicketDetails.isNcrilevate()==true)?("1"):("2")%>">
   
  
        <!-- nucleo ispettivo -->
   
   <%@ include file="nucleo_ispettivo_modify.jsp" %>