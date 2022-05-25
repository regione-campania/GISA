<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<br/><br/>

<table cellpadding="9" cellspacing="0" border="1" width="100%" class="tableClass">
<col width="10%"><col width="70%"><col width="10%"><col width="10%">
<tr><td></td>
<td><b>CGO 4 RISPETTATO</b></td>
<td><input type="radio" <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name="cgo4rispettato" id="cgo4rispettato_si" value="si"<%=(ChecklistIstanzaCGO!=null && ChecklistIstanzaCGO.isCgo4Rispettato()) ? "checked=\"checked\"" : "" %>> SI</td>
<td><input type="radio" <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name="cgo4rispettato" id="cgo4rispettato_no" value="no" <%=(ChecklistIstanzaCGO!=null && !ChecklistIstanzaCGO.isCgo4Rispettato()) ? "checked=\"checked\"" : "" %>> NO</td>
</tr>

<tr><td></td>
<td><b>CGO 9 RISPETTATO</b></td>
<td><input type="radio" <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name="cgo9rispettato" id="cgo9rispettato_si" value="si" <%=(ChecklistIstanzaCGO!=null && ChecklistIstanzaCGO.isCgo9Rispettato()) ? "checked=\"checked\"" : "" %>> SI</td>
<td><input type="radio" <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name="cgo9rispettato" id="cgo9rispettato_no" value="no" <%=(ChecklistIstanzaCGO!=null && !ChecklistIstanzaCGO.isCgo9Rispettato()) ? "checked=\"checked\"" : "" %>> NO</td>
</tr>
</table>

<br/><br/>
<table cellpadding="9" cellspacing="0" border="1" width="100%" class="tableClass">
<col width="10%">
<tr><td></td><td><b>
SI: IMPEGNO CONFORME ALLA NORMA<br/>
NO: IMPEGNO NON CONFORME ALLA NORMA<br/>
N.A.: NON APPLICABILE - VERIFICA DEL RISPETTO DI UN IMPEGNO AL QUALE L'AZIENDA NON E' TENUTA - specificare il motivo nelle note.</b></td></tr></table>

<br/><br/>
<table cellpadding="9" cellspacing="0" border="1" width="100%" class="tableClass">
<col width="10%"><col width="70%"><col width="10%"><col width="10%">
<tr>
<th>PUNTO</th> 
<TH>NOTE</TH>
<th></th>
<th></th>
</tr>


<tr>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_1_1" name="cgo_puntonote_1_1" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_1_1()) : ""%>"/></td>
<td><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name ="cgo_puntonote_1_2" class="editField" rows="3" cols="80" ><%=(ChecklistIstanzaCGO!=null) ? (ChecklistIstanzaCGO.getPuntonote_1_2()) : "" %></textarea></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_1_3" name="cgo_puntonote_1_3" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_1_3()) : ""%>"/></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_1_4" name="cgo_puntonote_1_4" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_1_4()) : ""%>"/></td>
</tr><tr>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_2_1" name="cgo_puntonote_2_1" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_2_1()) : ""%>"/></td>
<td><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name ="cgo_puntonote_2_2" class="editField" rows="3" cols="80" ><%=(ChecklistIstanzaCGO!=null) ? (ChecklistIstanzaCGO.getPuntonote_2_2()) : "" %></textarea></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_2_3" name="cgo_puntonote_2_3" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_2_3()) : ""%>"/></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_2_4" name="cgo_puntonote_2_4" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_2_4()) : ""%>"/></td>
</tr><tr>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_3_1" name="cgo_puntonote_3_1" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_3_1()) : ""%>"/></td>
<td><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name ="cgo_puntonote_3_2" class="editField" rows="3" cols="80" ><%=(ChecklistIstanzaCGO!=null) ? (ChecklistIstanzaCGO.getPuntonote_3_2()) : "" %></textarea></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_3_3" name="cgo_puntonote_3_3" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_3_3()) : ""%>"/></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_3_4" name="cgo_puntonote_3_4" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_3_4()) : ""%>"/></td>
</tr><tr>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_4_1" name="cgo_puntonote_4_1" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_4_1()) : ""%>"/></td>
<td><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name ="cgo_puntonote_4_2" class="editField" rows="3" cols="80" ><%=(ChecklistIstanzaCGO!=null) ? (ChecklistIstanzaCGO.getPuntonote_4_2()) : "" %></textarea></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_4_3" name="cgo_puntonote_4_3" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_4_3()) : ""%>"/></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_4_4" name="cgo_puntonote_4_4" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_4_4()) : ""%>"/></td>
</tr><tr>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_5_1" name="cgo_puntonote_5_1" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_5_1()) : ""%>"/></td>
<td><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name ="cgo_puntonote_5_2" class="editField" rows="3" cols="80" ><%=(ChecklistIstanzaCGO!=null) ? (ChecklistIstanzaCGO.getPuntonote_5_2()) : "" %></textarea></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_5_3" name="cgo_puntonote_5_3" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_5_3()) : ""%>"/></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_5_4" name="cgo_puntonote_5_4" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_5_4()) : ""%>"/></td>
</tr><tr>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_6_1" name="cgo_puntonote_6_1" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_6_1()) : ""%>"/></td>
<td><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name ="cgo_puntonote_6_2" class="editField" rows="3" cols="80" ><%=(ChecklistIstanzaCGO!=null) ? (ChecklistIstanzaCGO.getPuntonote_6_2()) : "" %></textarea></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_6_3" name="cgo_puntonote_6_3" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_6_3()) : ""%>"/></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_6_4" name="cgo_puntonote_6_4" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_6_4()) : ""%>"/></td>
</tr><tr>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_7_1" name="cgo_puntonote_7_1" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_7_1()) : ""%>"/></td>
<td><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name ="cgo_puntonote_7_2" class="editField" rows="3" cols="80" ><%=(ChecklistIstanzaCGO!=null) ? (ChecklistIstanzaCGO.getPuntonote_7_2()) : "" %></textarea></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_7_3" name="cgo_puntonote_7_3" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_7_3()) : ""%>"/></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_7_4" name="cgo_puntonote_7_4" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_7_4()) : ""%>"/></td>
</tr><tr>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_8_1" name="cgo_puntonote_8_1" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_8_1()) : ""%>"/></td>
<td><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name ="cgo_puntonote_8_2" class="editField" rows="3" cols="80" ><%=(ChecklistIstanzaCGO!=null) ? (ChecklistIstanzaCGO.getPuntonote_8_2()) : "" %></textarea></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_8_3" name="cgo_puntonote_8_3" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_8_3()) : ""%>"/></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_8_4" name="cgo_puntonote_8_4" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_8_4()) : ""%>"/></td>
</tr><tr>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_9_1" name="cgo_puntonote_9_1" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_9_1()) : ""%>"/></td>
<td><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name ="cgo_puntonote_9_2" class="editField" rows="3" cols="80" ><%=(ChecklistIstanzaCGO!=null) ? (ChecklistIstanzaCGO.getPuntonote_9_2()) : "" %></textarea></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_9_3" name="cgo_puntonote_9_3" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_9_3()) : ""%>"/></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_9_4" name="cgo_puntonote_9_4" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_9_4()) : ""%>"/></td>
</tr><tr>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_10_1" name="cgo_puntonote_10_1" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_10_1()) : ""%>"/></td>
<td><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name ="cgo_puntonote_10_2" class="editField" rows="3" cols="80" ><%=(ChecklistIstanzaCGO!=null) ? (ChecklistIstanzaCGO.getPuntonote_10_2()) : "" %></textarea></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_10_3" name="cgo_puntonote_10_3" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_10_3()) : ""%>"/></td>
<td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField" id="cgo_puntonote_10_4" name="cgo_puntonote_10_4" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getPuntonote_10_4()) : ""%>"/></td>
</tr>



</table>

I risultati dei presenti controlli verranno utilizzati per verificare il rispetto degli impegni <u>di condizionalita'</u> alla base dell'erogazione degli aiuti comunitari.<br/>
Nel caso di presenza di non conformita' l'esito del controllo sara' elaborato dall'Organismo Pagatore.
<br/><br/>

<div style="page-break-before: always">&nbsp;</div>

<table cellpadding="9" cellspacing="0" border="1" width="100%" class="tableClass">
<tr><th colspan="2" align="center">SEZIONE I <br/>
DA COMPILARE AL TERMINE DELL'ISPEZIONE IN ALLEVAMENTO</th></tr>

<tr><td>DATA PRIMO (attuale) CONTROLLO:</td> <td><%=toDateasString(Ticket.getAssignedDate() )%></td></tr>

<tr><td>SONO STATI ASSEGNATI INTERVENTI CORRETTIVI PER IL CGO 4:</td> 
<td><input type="radio" <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name="sez1_cgo4interventi" id="sez1_cgo4interventi_si" value="si" <%=(ChecklistIstanzaCGO!=null && ChecklistIstanzaCGO.isSez1Cgo4Interventi()) ? "checked=\"checked\"" : "" %>> SI
<input type="radio" <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name="sez1_cgo4interventi" id="sez1_cgo4interventi_no" value="no" <%=(ChecklistIstanzaCGO!=null && !ChecklistIstanzaCGO.isSez1Cgo4Interventi()) ? "checked=\"checked\"" : "" %>>NO</td></tr>

<tr><td colspan="2">SE SI, QUALI:</td></tr>

<tr><td colspan="2"><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> id ="sez1_cgo4interventi_note" name ="sez1_cgo4interventi_note" class="editField"  rows="3" cols="80"><%=(ChecklistIstanzaCGO!=null) ? (ChecklistIstanzaCGO.getSez1Cgo4InterventiNote()) : "" %></textarea></td></tr>

<tr><td>ENTRO QUALE DATA DOVRANNO ESSERE ESEGUITI:</td> <td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text"  class="editField" id ="sez1_cgo4interventi_data" name ="sez1_cgo4interventi_data" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getSez1Cgo4InterventiData()) : "" %>"/></td></tr>

<tr><td>SONO ASSEGNATI INTERVENTI CORRETTIVI PER IL CGO 9:</td> 
<td><input type="radio" <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name="sez1_cgo9interventi" id="sez1_cgo9interventi_si" value="si" <%=(ChecklistIstanzaCGO!=null && ChecklistIstanzaCGO.isSez1Cgo9Interventi()) ? "checked=\"checked\"" : "" %>>SI
<input type="radio" <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name="sez1_cgo9interventi" id="sez1_cgo9interventi_no" value="no" <%=(ChecklistIstanzaCGO!=null && !ChecklistIstanzaCGO.isSez1Cgo9Interventi()) ? "checked=\"checked\"" : "" %>> NO</td></tr>

<tr><td colspan="2">SE SI, QUALI:</td></tr>

<tr><td colspan="2"><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> id ="sez1_cgo9interventi_note" name ="sez1_cgo9interventi_note" class="editField" rows="3" cols="80"><%=(ChecklistIstanzaCGO!=null) ? (ChecklistIstanzaCGO.getSez1Cgo9InterventiNote()) : "" %></textarea></td></tr>

<tr><td>ENTRO QUALE DATA DOVRANNO ESSERE ESEGUITI:</td> <td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text"  class="editField" id ="sez1_cgo9interventi_data" name ="sez1_cgo9interventi_data" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getSez1Cgo9InterventiData()) : "" %>"/></td></tr>


<tr><td>Firma Proprietario /Detentore/Conduttore/Presente all'Ispezione</td> <td>Firma del/i Controllore/i</td></tr>
<tr><td>___________________________________</td> <td>___________________________________</td> </tr>


</table>

<br/><br/>

<div style="page-break-before: always">&nbsp;</div>
<table cellpadding="9" cellspacing="0" border="1" width="100%" class="tableClass">
<tr><th colspan="2">SEZIONE II <br/>
DA COMPILARE AL MOMENTO DELLA VERIFICA DELL'ESECUZIONE DEGLI INTERVENTI CORRETTIVI PER CGO 4 e CGO 9<br/>
(da effettuare dopo la scadenza del tempo assegnato e prima di rendere definitivo il risultato del controllo)</th></tr>
<tr><td>DATA CONTROLLO INTERVENTI CORRETTIVI CGO 4:</td> <td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text"  class="editField" id ="sez2_cgo4interventi_data" name ="sez2_cgo4interventi_data" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getSez2Cgo4InterventiData()) : "" %>"/></td></tr>
<tr><td>INTERVENTI CORRETTIVI ESEGUITI CGO 4:</td> 
<td><input type="radio" <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name="sez2_cgo4interventi" id="sez2_cgo4interventi_si" value="si" <%=(ChecklistIstanzaCGO!=null && ChecklistIstanzaCGO.isSez2Cgo4Interventi()) ? "checked=\"checked\"" : "" %>> SI
<input type="radio" <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name="sez2_cgo4interventi" id="sez2_cgo4interventi_no" value="no" <%=(ChecklistIstanzaCGO!=null && !ChecklistIstanzaCGO.isSez2Cgo4Interventi()) ? "checked=\"checked\"" : "" %>>NO</td></tr>
</tr>
<tr><td>DATA CONTROLLO INTERVENTI CORRETTIVI CGO 9:</td> <td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  type="text" class="editField"  id ="sez2_cgo9interventi_data" name ="sez2_cgo9interventi_data" value="<%=(ChecklistIstanzaCGO!=null) ? toHtml(ChecklistIstanzaCGO.getSez2Cgo9InterventiData()) : "" %>"/></td></tr>
<tr><td>INTERVENTI CORRETTIVI ESEGUITI CGO 9:</td> 
<td><input type="radio" <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name="sez2_cgo9interventi" id="sez2_cgo9interventi_si" value="si" <%=(ChecklistIstanzaCGO!=null && ChecklistIstanzaCGO.isSez2Cgo9Interventi()) ? "checked=\"checked\"" : "" %>> SI
<input type="radio" <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name="sez2_cgo9interventi" id="sez2_cgo9interventi_no" value="no" <%=(ChecklistIstanzaCGO!=null && !ChecklistIstanzaCGO.isSez2Cgo9Interventi()) ? "checked=\"checked\"" : "" %>>NO</td></tr>
</tr>

<tr><td>Firma Proprietario /Detentore/Conduttore/Presente all'Ispezione</td> <td>Firma del/i Controllore/i</td></tr>
<tr><td>___________________________________</td> <td>___________________________________</td> </tr>
</table>


