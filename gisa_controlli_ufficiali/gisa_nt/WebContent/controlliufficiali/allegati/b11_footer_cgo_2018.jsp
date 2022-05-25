<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<br/><br/>

<b>SI: IMPEGNO CONFORME ALLA NORMA<br/>NO: IMPEGNO NON CONFORME ALLA NORMA<br/>N.A.: NON APPLICABILE - VERIFICA DEL RISPETTO DI UN IMPEGNO AL QUALE L'AZIENDA NON E' TENUTA</b>

<div style="page-break-before: always">&nbsp;</div>

</div>


<br/><br/>


<table cellpadding="9" cellspacing="0" border="1" width="100%" class="details">
<tr><th style="font-weight: bold;background-color: gray;">PUNTO NOTE</th></tr>
<tr><td>(obbligatorio in caso di risposte NA)</td></tr>
<tr><td><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name ="puntoNote" id="puntoNote" class="editField" rows="6" cols="80" ><%=toHtml(ChecklistIstanzaCGO_2018.getPuntoNote())%></textarea></td></tr>
</table>

<br/><br/>

<table cellpadding="9" cellspacing="0" border="1" width="100%" class="tableClass">

<tr><td>Riscontro di elementi di non conformita' relativi al sistema di identificazione e registrazione animale, al benessere animale ovvero all'impiego di sostanze vietate**:</td> 
<td>
SI <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="riscontroNonConformita" id="riscontroNonConformita_si" value="si" <%= Boolean.TRUE.equals(ChecklistIstanzaCGO_2018.getRiscontroNonConformita()) ? "checked = \"checked\"" : "" %> type="radio">
NO <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="riscontroNonConformita" id="riscontroNonConformita_no" value="no" <%= Boolean.FALSE.equals(ChecklistIstanzaCGO_2018.getRiscontroNonConformita()) ? "checked = \"checked\"" : "" %> type="radio"> 
</td></tr>

<tr><td colspan="2">EVIDENZE: </td></tr> 
<td colspan="2">

<table>
<tr><td>Benessere animale</td> <td><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name ="evidenzeBenessere" id="evidenzeBenessere" class="editField" rows="6" cols="80" ><%=toHtml(ChecklistIstanzaCGO_2018.getEvidenzeBenessere()) %></textarea></td></tr>
<tr><td>Sistema di identificazione e registrazione animale</td> <td><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name ="evidenzeIdentificazione" id="evidenzeIdentificazione" class="editField" rows="6" cols="80" ><%=toHtml(ChecklistIstanzaCGO_2018.getEvidenzeIdentificazione()) %></textarea></td></tr>
<tr><td>Impiego di sostanze vietate</td> <td><textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name ="evidenzeSostanze" id="evidenzeSostanze" class="editField" rows="6" cols="80" ><%=toHtml(ChecklistIstanzaCGO_2018.getEvidenzeSostanze()) %></textarea></td></tr>
</table>


<tr><td colspan="2">
** Qualora, durante l'esecuzione del controllo, il Veterinario controllore rilevasse elementi di non conformita' relativi al sistema di
identificazione e registrazione animale, al benessere animale ovvero all'impiego di sostanze vietate, egli dovra' riportarne l'evenienza
flaggando il settore pertinente e specificare nell'apposito campo l'evidenza riscontrata. Al rientro presso la ASL, il Veterinario
controllore dovra' evidenziare al Responsabile della ASL quanto da lui rilevato e consegnare copia della check-list da lui compilata
in modo che il Responsabile stesso possa provvedere all'attivazione urgente dei relativi controlli. Il sistema inoltre segnalera'
opportunamente tale evenienza al fine dell'esecuzione obbligatoria dello specifico controllo.
</td></tr>


</table>

<br/><br/>

<table cellpadding="9" cellspacing="0" border="1" width="100%" class="tableClass">
<tr><th colspan="2" align="center">PRESCRIZIONI E SANZIONI</th></tr>
<tr><th colspan="2" align="center">PRESCRIZIONI</th></tr>

<tr><td>SONO STATE ASSEGNATE PRESCRIZIONI PER LA SICUREZZA ALIMENTARE?</td>
<td>
SI <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="prescrizioniSicurezza" id="prescrizioniSicurezza_si" value="S" <%= "S".equals(ChecklistIstanzaCGO_2018.getPrescrizioniSicurezza()) ? "checked = \"checked\"" : "" %> type="radio">
NO <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="prescrizioniSicurezza" id="prescrizioniSicurezza_no" value="N"  <%= "N".equals(ChecklistIstanzaCGO_2018.getPrescrizioniSicurezza()) ? "checked = \"checked\"" : "" %> type="radio"> 
</td> 
</tr>

<tr><td colspan="2">SE SI, QUALI<br/>
<textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name ="prescrizioniSicurezzaNote" id="prescrizioniSicurezzaNote" class="editField" rows="6" cols="80" ><%=toHtml(ChecklistIstanzaCGO_2018.getPrescrizioniSicurezzaNote()) %></textarea>
</td></tr>

<tr><td colspan="2">ENTRO QUALE DATA DOVRANNO ESSERE ESEGUITE: <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="date" id="prescrizioniSicurezzaData" name = "prescrizioniSicurezzaData" class="editField" value="<%=toHtml(ChecklistIstanzaCGO_2018.getPrescrizioniSicurezzaData()) %>"/>
</td></tr>

<tr><td>SONO STATE ASSEGNATE PRESCRIZIONI PER LE TSE?</td>
<td>
SI <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="prescrizioniTse" id="prescrizioniTse_si" value="S" <%= "S".equals(ChecklistIstanzaCGO_2018.getPrescrizioniTse()) ? "checked = \"checked\"" : "" %> type="radio">
NO <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>  name="prescrizioniTse" id="prescrizioniTse_no" value="N" <%= "N".equals(ChecklistIstanzaCGO_2018.getPrescrizioniTse()) ? "checked = \"checked\"" : "" %> type="radio"> 
</td> 
</tr>

<tr><td colspan="2">SE SI, QUALI<br/>
<textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name ="prescrizioniTseNote" id="prescrizioniTseNote" class="editField" rows="6" cols="80" ><%=toHtml(ChecklistIstanzaCGO_2018.getPrescrizioniTseNote()) %></textarea>
</td></tr>

<tr><td colspan="2">ENTRO QUALE DATA DOVRANNO ESSERE ESEGUITE: <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="date" id="prescrizioniTseData" name = "prescrizioniTseData" class="editField" value="<%=toHtml(ChecklistIstanzaCGO_2018.getPrescrizioniTseData()) %>"/>
</td></tr>
</table>

<br/><br/>

<div style="page-break-before: always">&nbsp;</div>

<table cellpadding="9" cellspacing="0" border="1" width="100%" class="tableClass">
<tr><th colspan="2" align="center">SANZIONI APPLICATE</th></tr>

<tr>
<td>Blocco movimentazioni <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="text" id="bloccoMovimentazioni" name = "bloccoMovimentazioni" class="editField" value="<%= toHtml(ChecklistIstanzaCGO_2018.getBloccoMovimentazioni())%>" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/></td>
<td>Amministrativa/pecuniaria<input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="text" id="amministrativaPecuniaria" name = "amministrativaPecuniaria" class="editField" value="<%= toHtml(ChecklistIstanzaCGO_2018.getAmministrativaPecuniaria())%>" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/></td>
</tr>

<tr>
<td>Abbattimento capi <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="text" id="abbattimentoCapi" name = "abbattimentoCapi" class="editField" value="<%= toHtml(ChecklistIstanzaCGO_2018.getAbbattimentoCapi())%>" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/></td>
<td>Sequestro capi<input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="text" id="sequestroCapi" name = "sequestroCapi" class="editField" value="<%= toHtml(ChecklistIstanzaCGO_2018.getSequestroCapi())%>" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/></td>
</tr>

<tr>
<td colspan="2">Altro (specificare) <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="text" id="altroCapi" name = "altroCapi" class="editField" value="<%= toHtml(ChecklistIstanzaCGO_2018.getAltroCapi())%>" onkeyup="this.value=this.value.replace(/[^0-9]+/,'')"/><br/>
<textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name ="altroSpecificareDesc" id="altroSpecificareDesc" class="editField" rows="6" cols="80" ><%=toHtml(ChecklistIstanzaCGO_2018.getAltroSpecificareDesc()) %></textarea>
</td></tr>

<tr>
<td colspan="2">NOTE/OSSERVAZIONI DEL CONTROLLORE :<br/>
<textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name ="noteControllore" id="noteControllore" class="editField" rows="6" cols="80" ><%=toHtml(ChecklistIstanzaCGO_2018.getNoteControllore()) %></textarea>
</td></tr>

<tr>
<td colspan="2">NOTE/OSSERVAZIONI DEL DETENTORE :<br/>
<textarea <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name ="noteDetentore" id="noteDetentore" class="editField" rows="6" cols="80" ><%=toHtml(ChecklistIstanzaCGO_2018.getNoteDetentore()) %></textarea>
</td></tr>

</table>

<br/><br/>

<table cellpadding="9" cellspacing="0" border="1" width="100%" class="tableClass">
<tr><td>
E' stata consegnata una copia della presente check list all'allevatore? <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="text" class="layout" size="2" readonly value="<%= (Ticket.getFlag_checklist() != null && Ticket.getFlag_checklist().equals("S")) ? "SI" : "NO"  %>">
</td></tr>

<tr><td>
Il risultato del presente controllo sara' utilizzato per verificare il rispetto degli impegni di condizionalita' alla base
dell'erogazione degli aiuti comunitari. Nel caso di presenza di non conformita' l'esito del controllo sara' elaborato
dall'Organismo Pagatore.
</td></tr>

<tr><td>
<mark><b>Data primo controllo in loco:</b></mark> <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="date" id="dataPrimoControlloLoco" name = "dataPrimoControlloLoco" class="editField" value="<%=toHtml(ChecklistIstanzaCGO_2018.getDataPrimoControlloLoco()) %>"/><br/><br/>
<mark><b>Nome e cognome del detentore/proprietario/altro responsabile dell'azienda presente</b> </mark><br/><br/>
<input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="text" id="nomeResponsabilePrimoControlloLoco" name = "nomeResponsabilePrimoControlloLoco" class="editField" maxlength="50" value="<%=toHtml(ChecklistIstanzaCGO_2018.getNomeResponsabilePrimoControlloLoco()) %>"/><br/><br/>
Firma del Detentore/Proprietario/altro Responsabile dell'azienda presente all'ispezione<br/><br/>
<mark><b>Nome e cognome del controllore</b></mark><br/><br/>
<input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="text" id="nomeControllorePrimoControlloLoco" name = "nomeControllorePrimoControlloLoco" class="editField" maxlength="50" value="<%=toHtml(ChecklistIstanzaCGO_2018.getNomeControllorePrimoControlloLoco()) %>"/><br/><br/>
Firma e timbro del controllore<br/><br/>
</td></tr>
</table>


<br/><br/>

<table cellpadding="9" cellspacing="0" border="1" width="100%" class="tableClass">
<tr><td>
VERIFICA ESECUZIONE DELLE PRESCRIZIONI<br/> (da effettuare dopo la scadenza del tempo assegnato)
</td></tr>

<tr><td>
PRESCRIZIONI ESEGUITE PER LA SICUREZZA ALIMENTARE:
<input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="prescrizioniEseguiteSicurezza" id="prescrizioniEseguiteSicurezza_si" value="S" <%= "S".equals(ChecklistIstanzaCGO_2018.getPrescrizioniEseguiteSicurezza()) ? "checked = \"checked\"" : "" %> type="radio"> SI
<input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="prescrizioniEseguiteSicurezza" id="prescrizioniEseguiteSicurezza_no" value="N" <%= "N".equals(ChecklistIstanzaCGO_2018.getPrescrizioniEseguiteSicurezza()) ? "checked = \"checked\"" : "" %> type="radio"> NO 
</td></tr>

<tr><td>
Data verifica in loco: <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="date" id="dataVerificaLoco" name = "dataVerificaLoco" class="editField" value="<%=toHtml(ChecklistIstanzaCGO_2018.getDataVerificaLoco()) %>"/><br/><br/>
Nome e cognome del detentore/proprietario/altro responsabile dell'azienda presente<br/><br/>
<input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="text" id="nomeResponsabileVerificaLoco" name = "nomeResponsabileVerificaLoco"  class="editField" maxlength="50"value="<%=toHtml(ChecklistIstanzaCGO_2018.getNomeResponsabileVerificaLoco()) %>"/><br/><br/>
Firma del Detentore/Proprietario/altro Responsabile dell'azienda presente all'ispezione<br/><br/>
Nome e cognome del controllore <br/><br/>
<input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="text" id="nomeControlloreVerificaLoco" name = "nomeControlloreVerificaLoco" class="editField" maxlength="50" value="<%=toHtml(ChecklistIstanzaCGO_2018.getNomeControlloreVerificaLoco()) %>"/><br/><br/>
Firma e timbro del controllore<br/><br/>
</td></tr>

<tr><td>
PRESCRIZIONI ESEGUITE PER LE TSE:
<input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="prescrizioniEseguiteTse" id="prescrizioniEseguiteTse_si" value="S" <%= "S".equals(ChecklistIstanzaCGO_2018.getPrescrizioniEseguiteTse()) ? "checked = \"checked\"" : "" %> type="radio"> SI
<input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="prescrizioniEseguiteTse" id="prescrizioniEseguiteTse_no" value="N" <%= "N".equals(ChecklistIstanzaCGO_2018.getPrescrizioniEseguiteTse()) ? "checked = \"checked\"" : "" %> type="radio"> NO 
</td></tr>

<tr><td>
Data verifica in loco: <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="date" id="dataVerificaLocoTse" name = "dataVerificaLocoTse" class="editField" value="<%=toHtml(ChecklistIstanzaCGO_2018.getDataVerificaLocoTse()) %>"/><br/><br/>
Nome e cognome del detentore/proprietario/altro responsabile dell'azienda presente<br/><br/>
<input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="text" id="nomeResponsabileVerificaLocoTse" name = "nomeResponsabileVerificaLocoTse" class="editField" maxlength="50" value="<%=toHtml(ChecklistIstanzaCGO_2018.getNomeResponsabileVerificaLocoTse()) %>"/><br/><br/>
Firma del Detentore/Proprietario/altro Responsabile dell'azienda presente all'ispezione<br/><br/>
Nome e cognome del controllore <br/><br/>
<input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="text" id="nomeControlloreVerificaLocoTse" name = "nomeControlloreVerificaLocoTse" class="editField" maxlength="50" value="<%=toHtml(ChecklistIstanzaCGO_2018.getNomeControlloreVerificaLocoTse()) %>"/><br/><br/>
Firma e timbro del controllore<br/><br/>
</td></tr>

<tr><td>
DATA CHIUSURA RELAZIONE DI CONTROLLO***:  <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="date" id="dataChiusura" name = "dataChiusura" class="editField" value="<%=toHtml(ChecklistIstanzaCGO_2018.getDataChiusura()) %>"/> <br/><br/>
***Ai sensi del Reg. 809-2014, articolo 72, paragrafo 4. Fatta salva ogni disposizione particolare della normativa che si applica ai
criteri e alle norme, la relazione di controllo e' ultimata entro un mese dal controllo in loco. Tale termine puo' essere tuttavia
prorogato a tre mesi in circostanze debitamente giustificate, in particolare per esigenze connesse ad analisi chimiche o fisiche.
</td></tr>

</table>


