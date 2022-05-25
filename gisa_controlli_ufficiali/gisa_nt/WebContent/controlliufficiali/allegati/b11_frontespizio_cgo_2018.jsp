<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="org.aspcf.modules.checklist_benessere.base.ChecklistIstanzaCGO_2018"%>
<br>
<center><b>CONTROLLI PER LA SICUREZZA ALIMENTARE IN AZIENDA E PER LA PREVENZIONE, IL CONTROLLO E <br/>
L'ERADICAZIONE DI ALCUNE ENCEFALOPATIE SPONGIFORMI TRASMISSIBILI - CGO 4 e CGO 9<br/>
CHECK-LIST N _________________ </b><br/>
Regolamento (CE) n. 178/2002 - Regolamenti (CE) n. 852/2004, 853/2004, 183/2005 - Regolamento (CE) n. 999/2001</center>

<b>REGIONE CAMPANIA<br>	ASL <%=Allevamento.getAsl() %></b><br/><br/>
	
<div style="border: 1px solid;">
Codice Azienda <input type="text" class="layout" size = "15" readonly value="<%=(Allevamento.getN_reg() != null ? Allevamento.getN_reg() :"") %>"> 
Codice fiscale <input type="text" class="layout" size="50" readonly value="<%= (Allevamento.getCodice_fiscale() != null ? Allevamento.getCodice_fiscale() :"") %>">
Specie <input type="text" class="layout" size="30" readonly value="<%= (Allevamento.getSpecie_allev() != null ? Allevamento.getSpecie_allev() :"")  %>">
<br/>
Denominazione <input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getName() != null ? Allevamento.getName() :"") %>">
<br/>
Indirizzo e numero civico <input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getComune_operativo() != null ? toHtml(Allevamento.getComune_operativo())+" "+ Allevamento.getIndirizzo_operativo() : toHtml(Allevamento.getComune())+" "+Allevamento.getIndirizzo())  %>">
<br/>
Proprietario <input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getProp() != null ? Allevamento.getProp() :"")  %>">
<br/>
Codice fiscale <input type="text" class="layout" size="50" readonly value="<%= (Allevamento.getCf_prop() != null ? Allevamento.getCf_prop() :"")  %>">
Tel. <input type="text" class="layout" size="30" readonly value="">
<br/>
Detentore <input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getDet() != null ? Allevamento.getDet() :"") %>"><br>
Codice fiscale detentore <input type="text" class="layout" size="50" readonly value="<%= Allevamento.getCf_det() %>">
Tel. <input type="text" class="layout" size="30" readonly value="">
<br/><br/>


<b><mark><b>Appartenente al Campione Condizionalita'?</b></mark></div>  
<input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="appartenenteCondizionalita" id="appartenenteCondizionalita_si" value="si" <%= Boolean.TRUE.equals(ChecklistIstanzaCGO_2018.getAppartenenteCondizionalita()) ? "checked = \"checked\"" : "" %> type="radio"> SI
<input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="appartenenteCondizionalita" id="appartenenteCondizionalita_no" value="no" <%= Boolean.FALSE.equals(ChecklistIstanzaCGO_2018.getAppartenenteCondizionalita()) ? "checked = \"checked\"" : "" %> type="radio"> NO

<br/><br/>

<table cellpadding="9" cellspacing="0" border="1" width="100%" class="details">
<tr><th style="font-weight: bold;background-color: gray;"><mark><b>Selezionare i criteri utilizzati per la selezione dell'allevamento sottoposto a controllo</b> </mark>: </th></tr>

<tr><td> <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> value="002" name="criteriUtilizzati" id="altreIndagini" <%= (ChecklistIstanzaCGO_2018.getCriteriUtilizzati()!=null && ChecklistIstanzaCGO_2018.getCriteriUtilizzati().equals("002") ) ? "checked = \"checked\"" : "" %> type="radio"/> Altre indagini degli organi di polizia giudiziaria</td></tr>
<tr><td> <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> value="003" name="criteriUtilizzati" id="cambiamentiSituazione" <%= (ChecklistIstanzaCGO_2018.getCriteriUtilizzati()!=null && ChecklistIstanzaCGO_2018.getCriteriUtilizzati().equals("003") ) ? "checked = \"checked\"" : "" %> type="radio"/> Cambiamenti della situazione aziendale</td></tr>
<tr><td> <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> value="004" name="criteriUtilizzati" id="comunicazioneDati" <%= (ChecklistIstanzaCGO_2018.getCriteriUtilizzati()!=null && ChecklistIstanzaCGO_2018.getCriteriUtilizzati().equals("004") ) ? "checked = \"checked\"" : "" %> type="radio"/> Comunicazione dei dati dell'azienda all'Autorita' competente</td></tr>
<tr><td> <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> value="005" name="criteriUtilizzati" id="implicazioniSalute" <%= (ChecklistIstanzaCGO_2018.getCriteriUtilizzati()!=null && ChecklistIstanzaCGO_2018.getCriteriUtilizzati().equals("005") ) ? "checked = \"checked\"" : "" %> type="radio"/> Implicazioni per la salute umana e animale, precedenti focolai</td></tr>
<tr><td> <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> value="006" name="criteriUtilizzati" id="indagineIgiene" <%= (ChecklistIstanzaCGO_2018.getCriteriUtilizzati()!=null && ChecklistIstanzaCGO_2018.getCriteriUtilizzati().equals("006") ) ? "checked = \"checked\"" : "" %> type="radio"/> Indagine relativa all'igiene degli allevamenti</td></tr>
<tr><td> <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> value="007" name="criteriUtilizzati" id="indagineFrodi" <%= (ChecklistIstanzaCGO_2018.getCriteriUtilizzati()!=null && ChecklistIstanzaCGO_2018.getCriteriUtilizzati().equals("007") ) ? "checked = \"checked\"" : "" %> type="radio"/> Indagine relativa alle frodi comunitarie</td></tr>
<tr><td> <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>value="008" name="criteriUtilizzati" id="infrazioni" <%= (ChecklistIstanzaCGO_2018.getCriteriUtilizzati()!=null && ChecklistIstanzaCGO_2018.getCriteriUtilizzati().equals("008") ) ? "checked = \"checked\"" : "" %> type="radio"/> Infrazioni riscontrate negli anni precedenti</td></tr>
<tr><td> <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>value="009" name="criteriUtilizzati" id="numeroAnimali" <%= (ChecklistIstanzaCGO_2018.getCriteriUtilizzati()!=null && ChecklistIstanzaCGO_2018.getCriteriUtilizzati().equals("009") ) ? "checked = \"checked\"" : "" %> type="radio"/> Numero di animali</td></tr>
<tr><td> <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>value="011" name="criteriUtilizzati" id="segnalazioneIrregolarita" <%= (ChecklistIstanzaCGO_2018.getCriteriUtilizzati()!=null && ChecklistIstanzaCGO_2018.getCriteriUtilizzati().equals("011") ) ? "checked = \"checked\"" : "" %> type="radio"/> Segnalazione di irregolarita' da impianto di macellazione</td></tr>
<tr><td> <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>value="012" name="criteriUtilizzati" id="variazioniEntita" <%= (ChecklistIstanzaCGO_2018.getCriteriUtilizzati()!=null && ChecklistIstanzaCGO_2018.getCriteriUtilizzati().equals("012") ) ? "checked = \"checked\"" : "" %> type="radio"/> Variazioni dell'entita' dei premi</td></tr>
<tr><td> <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>value="997" name="criteriUtilizzati" id="altroCriterio" <%= (ChecklistIstanzaCGO_2018.getCriteriUtilizzati()!=null && ChecklistIstanzaCGO_2018.getCriteriUtilizzati().equals("997") ) ? "checked = \"checked\"" : "" %> type="radio"/> Altro criterio di rischio ritenuto rilevante dall'Autorita' competente, indicare quale  <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="text" id="altroCriterioDescrizione" name="altroCriterioDescrizione" class="editField" value="<%=toHtml(ChecklistIstanzaCGO_2018.getAltroCriterioDescrizione())%>"/></td></tr>
<tr><td> <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%>value="999" name="criteriUtilizzati" id="random" <%= (ChecklistIstanzaCGO_2018.getCriteriUtilizzati()!=null && ChecklistIstanzaCGO_2018.getCriteriUtilizzati().equals("999") ) ? "checked = \"checked\"" : "" %> type="radio"/> Casuale (Random)</td></tr>
<tr><td><b>(*)Specificare il criterio di rischio ritenuto rilevante dall'Autorita' competente:</b><br/><br/><br/></td></tr>

</table>

<br/><br/>

<table cellpadding="9" cellspacing="0" border="1" width="100%" class="details">
<tr><td> <mark><b>Numero capi presenti in BDN (sulla base delle registrazioni effettuate nel sistema)</b></mark> </td> <td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="number" id="numeroCapiPresenti" name="numeroCapiPresenti" class="editField" value="<%=ChecklistIstanzaCGO_2018.getNumeroCapiPresenti()%>"/></td></tr>
<tr><td> <mark><b>Numero capi effettivamente presenti in allevamento:</b></mark></td> <td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="number" id="numeroCapiEffettivamentePresenti" name="numeroCapiEffettivamentePresenti" class="editField" value="<%=ChecklistIstanzaCGO_2018.getNumeroCapiEffettivamentePresenti()%>"/></td></tr>
<tr><td> <mark><b>Numero dei capi controllati:</b></mark></td> <td><input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> type="number" id="numeroCapiControllati" name="numeroCapiControllati" class="editField" value="<%=ChecklistIstanzaCGO_2018.getNumeroCapiControllati()%>"/></td></tr>
</table>

<br/><br/>


<div style="page-break-before: always">&nbsp;</div>



<table cellpadding="9" cellspacing="0" border="1" width="100%" class="tableClass">


<tr><td><mark><b>ESITO DEL CONTROLLO PER LA SICUREZZA ALIMENTARE:</b></mark><br/> (Se sfavorevole, i campi Intenzionalita' sono obbligatori)</td> 
<td>
FAVOREVOLE <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="esitoControllo" id="esitoControllo_si" value="si" onClick="gestisciEsito(this)" <%= Boolean.TRUE.equals(ChecklistIstanzaCGO_2018.getEsitoControllo()) ? "checked = \"checked\"" : "" %> type="radio">
SFAVOREVOLE <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="esitoControllo" id="esitoControllo_no" value="no" onClick="gestisciEsito(this)" <%= Boolean.FALSE.equals(ChecklistIstanzaCGO_2018.getEsitoControllo()) ? "checked = \"checked\"" : "" %> type="radio"> 
</td></tr>

<tr><td>Intenzionalita' (da valutare in caso di esito del controllo sfavorevole): </td> 
<td>
SI <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="intenzionalita" id="intenzionalita_si" value="S" <%= "S".equals(ChecklistIstanzaCGO_2018.getIntenzionalita()) ? "checked = \"checked\"" : "" %> type="radio">
NO <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="intenzionalita" id="intenzionalita_no" value="N" <%= "N".equals(ChecklistIstanzaCGO_2018.getIntenzionalita()) ? "checked = \"checked\"" : "" %> type="radio">
N.A. <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="intenzionalita" id="intenzionalita_na" value="-" <%= "-".equals(ChecklistIstanzaCGO_2018.getIntenzionalita()) ? "checked = \"checked\"" : "" %> type="radio"> 
</td></tr>


<tr><td>ESITO DEL CONTROLLO DELLE TSE:</td> 
<td>
FAVOREVOLE <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="esitoControlloTse" id="esitoControlloTse_si" value="si" <%= Boolean.TRUE.equals(ChecklistIstanzaCGO_2018.getEsitoControlloTse()) ? "checked = \"checked\"" : "" %> type="radio">
SFAVOREVOLE <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="esitoControlloTse" id="esitoControlloTse_no" value="no" <%= Boolean.FALSE.equals(ChecklistIstanzaCGO_2018.getEsitoControlloTse()) ? "checked = \"checked\"" : "" %> type="radio"> 
</td></tr>

<tr><td>Intenzionalita' (da valutare in caso di esito del controllo sfavorevole):</td> 
<td>
SI <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="intenzionalitaTse" id="intenzionalitaTse_si" value="S" <%= "S".equals(ChecklistIstanzaCGO_2018.getIntenzionalitaTse()) ? "checked = \"checked\"" : "" %> type="radio">
NO <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="intenzionalitaTse" id="intenzionalitaTse_no" value="N" <%= "N".equals(ChecklistIstanzaCGO_2018.getIntenzionalitaTse()) ? "checked = \"checked\"" : "" %> type="radio">
N.A. <input <%= (ChecklistIstanza!=null && ChecklistIstanza.isBozza()==false ) ? "disabled" : ""%> name="intenzionalitaTse" id="intenzionalitaTse_na" value="-" <%= "-".equals(ChecklistIstanzaCGO_2018.getIntenzionalitaTse()) ? "checked = \"checked\"" : "" %> type="radio"> 
</td></tr>

</table>






<div id="checklist" <%= Boolean.TRUE.equals(ChecklistIstanzaCGO_2018.getEsitoControllo()) ? "style = \"display:none\"" : "" %>>

<div style="page-break-before: always">&nbsp;</div>

<table cellpadding="9" cellspacing="0" border="1" width="100%" class="details">
<tr><th style="font-weight: bold;background-color: gray;">ELEMENTI DI VERIFICA<br/>(In caso di esito "N.A." specificare il motivo in campo note) </th></tr>
</table>

<br/><br/>

