<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%-- reusable contract form --%>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popContractHours.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkInt.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></script>
<script language="JavaScript">
  onLoad = 1;
  
  function doCheck(form) {
    if (form.dosubmit.value == "false") {
      return true;
    } else {
      return(checkForm(form));
    }
  }
  
  function checkForm(form) {
    formTest = true;
    message = "";
    alertMessage = "";
    if (form.serviceContractNumber.value == "") { 
      message += label("check.servicecontract.number","- Service Contract Number is required\r\n");
      formTest = false;
    }
    if (form.assetId.value == "-1") { 
      message += label("check.asset","- Impianto is required\r\n");
      formTest = false;
    }
    if (form.initialStartDate.value == "") { 
      message += label("check.init.contract.date","- Initial Contract Date is required\r\n");
      formTest = false;
    }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }else{
      var test = form.selectedList;
      if (test != null) {
        return selectAllOptions(test);
      }
    }
  }
  
  function clearAdjustment(){
    <%if (serviceContract.getId() == -1){%>
      document.getElementById('hoursRemaining').value = '';
      document.getElementById('totalHoursRemaining').value = '';
    <%}else{%>
      document.getElementById('adjustmentHours').value = '';
      changeDivContent('hours',label("no.adjustment","No adjustment"));
      changeDivContent('netRemainingHours',label("no.adjustment","No adjustment"));
    <%}%>
    document.getElementById('adjustmentReason').value = '-1';
    changeDivContent('reason',label("no.adjustment","No adjustment"));
    document.getElementById('adjustmentNotes').value = '';
    changeDivContent('notes',label("no.adjustment","No adjustment"));
  }
  
  function setField(formField,thisValue,thisForm) {
	  var frm = document.forms[thisForm];
	  var len = document.forms[thisForm].elements.length;
	  var i=0;
	  for( i=0 ; i<len ; i++) {
	    if (frm.elements[i].name.indexOf(formField)!=-1) {
	      if(thisValue){
	        frm.elements[i].value = "1";
	      } else {
	        frm.elements[i].value = "0";
	      }
	    }
	  }
  }
  
  function changeDivContent(divName, divContents) {
    if(document.layers){
      // Netscape 4 or equiv.
      divToChange = document.layers[divName];
      divToChange.document.open();
      divToChange.document.write(divContents);
      divToChange.document.close();
    } else if(document.all){
      // MS IE or equiv.
      divToChange = document.all[divName];
      divToChange.innerHTML = divContents;
    } else if(document.getElementById){
      // Netscape 6 or equiv.
      divToChange = document.getElementById(divName);
      divToChange.innerHTML = divContents;
    }
    //when the content of any of the select items changes, do something here
    //reset the sc and asset
  }
  
  function resetNumericFieldValue(fieldId){
    document.getElementById(fieldId).value = -1;
  }
</script>
<%-- start details --%>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="documents.details.generalInformation">General Information</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel" nowrap>
      <dhv:label name="accounts.accountasset_include.ServiceContractNumber">Service Contract Number</dhv:label>
    </td>
    <td>
      <input type="text" size="10" name="serviceContractNumber" maxlength="30" value="<%= toHtmlValue(serviceContract.getServiceContractNumber()) %>"><font color="red">*</font>
      <%= showAttribute(request, "serviceContractNumberError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accounts_add.Type">Type</dhv:label>
    </td>
    <td>
    <%= serviceContractTypeList.getHtmlSelect("type",serviceContract.getType()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.contractValue">Contract Value</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="contractValue" size="15" value="<zeroio:number value="<%= serviceContract.getContractValue() %>" locale="<%= User.getLocale() %>" />">
      <%= showAttribute(request, "contractValueError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.locazione">Se locazione</dhv:label>
    </td>
    <td>
      <input type="checkbox" name="chkLocazione" value="true" onclick="javascript:setField('locazione',document.addServiceContract.chkLocazione.checked,'addServiceContract');" <%= serviceContract.getLocazione() ? " checked" : ""%>>
      <input type="hidden" name="locazione" value="<%= serviceContract.getLocazione() ? "1" : "0"%>" />
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.nomeLocatore">Nome locatore</dhv:label>
    </td>
    <td>
      <input type="text" name="nomeLocatore" size="50" value="<%= toString(serviceContract.getNomeLocatore()) %>"/> 
    </td>
  </tr>
  
  
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.importoRiscatto">Importo Riscatto</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="importoRiscatto" size="15" value="<zeroio:number value="<%= serviceContract.getImportoRiscatto() %>" locale="<%= User.getLocale() %>" />">
      <%= showAttribute(request, "importoRiscattoError") %>
    </td>
  </tr>  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="accounts.accountasset_include.Description">Description</dhv:label>
    </td>
    <td>
      <textarea name="description" rows="3" cols="50"><%= toString(serviceContract.getDescription()) %></textarea>
    </td>
  </tr>
  
  
 <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.durata">Durata</dhv:label>
    </td>
    <td>
      <input type="text" size="5" name="durata" value='<%= serviceContract.getDurata() == -1 ? "" : String.valueOf(serviceContract.getDurata()) %>' />
      Mesi
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.interventiAnnuiGratuiti">N. interventi annui gratuiti</dhv:label>
    </td>
    <td>
      <input type="text" size="5" name="numeroInterventiAnnuiGratuiti" value='<%= serviceContract.getNumeroInterventiAnnuiGratuiti() == -1 ? "" : String.valueOf(serviceContract.getNumeroInterventiAnnuiGratuiti()) %>' />
    </td>
  </tr>
  
  
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.numeroConsulenze">Numero consulenze</dhv:label>
    </td>
    <td>
      <input type="text" size="5" name="numeroConsulenze" value='<%= serviceContract.getNumeroConsulenze() == -1 ? "" : String.valueOf(serviceContract.getNumeroConsulenze()) %>' />
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.initialContractDate">Initial Contract Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addServiceContract" field="initialStartDate" timestamp="<%= serviceContract.getInitialStartDate() %>" timeZone="<%=serviceContract.getInitialStartDateTimeZone()%>" showTimeZone="true" />
      <font color="red">*</font>
      <%= showAttribute(request, "initialStartDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.currentEndDate">Current End Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addServiceContract" field="currentEndDate" timestamp="<%= serviceContract.getCurrentEndDate() %>" timeZone="<%=serviceContract.getCurrentEndDateTimeZone()%>" showTimeZone="true" />
      <%= showAttribute(request, "currentEndDateError") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.currentContractDate">Current Contract Date</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addServiceContract" field="currentStartDate" timestamp="<%= serviceContract.getCurrentStartDate() %>" timeZone="<%=serviceContract.getCurrentStartDateTimeZone()%>" showTimeZone="true" />
      <%= showAttribute(request, "currentStartDateError") %>
    </td>
  </tr>  
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.shift">Shift</dhv:label>
    </td>
    <td>
      <input type="text" size="5" name="shift" value='<%= serviceContract.getShift() == -1 ? "" : String.valueOf(serviceContract.getShift()) %>' />
      Mesi
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.inizioCopertura">Inizio Copertura</dhv:label>
    </td>
    <td>
      <input type="text" size="20" name="inizioCopertura" value="<%= toString(serviceContract.getInizioCopertura()) %>"/> 
    </td>
  </tr>    
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.fineCopertura">Fine Copertura</dhv:label>
    </td>
    <td>
      <input type="text" size="20" name="fineCopertura" value="<%= toString(serviceContract.getFineCopertura()) %>"/> 
    </td>
  </tr>  
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.codiceAdeguamento">Adeguamento</dhv:label>
    </td>
    <td>
      <%= adeguamentoList.getHtmlSelect("codiceAdeguamento", serviceContract.getCodiceAdeguamento()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.dataDecorrenzaAdeguamento">Data decorrenza adeguamento</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addServiceContract" field="dataDecorrenzaAdeguamento" timestamp="<%= serviceContract.getDataDecorrenzaAdeguamento() %>" showTimeZone="false" />
      <%= showAttribute(request, "dataDecorrenzaAdeguamentoError") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.spesaUnaTantum">Spesa una tantum</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="spesaUnaTantum" size="15" value="<zeroio:number value="<%= serviceContract.getSpesaUnaTantum() %>" locale="<%= User.getLocale() %>" />">
      <%= showAttribute(request, "spesaUnaTantum") %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.spesaIncasso">Spesa incasso</dhv:label>
    </td>
    <td>
      <%= applicationPrefs.get("SYSTEM.CURRENCY") %>
      <input type="text" name="spesaIncasso" size="15" value="<zeroio:number value="<%= serviceContract.getSpesaIncasso() %>" locale="<%= User.getLocale() %>" />">
      <%= showAttribute(request, "spesaIncasso") %>
    </td>
  </tr>
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="account.sc.etichettaFattura">Etichetta fattura</dhv:label>
    </td>
    <td>
      <textarea name="etichettaFattura" rows="3" cols="50"><%= toString(serviceContract.getEtichettaFattura()) %></textarea>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.codicePagamento">Tipo pagamento</dhv:label>
    </td>
    <td>
      <%= pagamentoList.getHtmlSelect("codicePagamento", serviceContract.getCodicePagamento()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.tipoArrotondamento">Tipo arrotondamento</dhv:label>
    </td>
    <td>
      <input type="text" size="50" name="tipoArrotondamento" value="<%= toString(serviceContract.getTipoArrotondamento()) %>"/> 
    </td>
  </tr>  
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.numeroRate">Numero rate</dhv:label>
    </td>
    <td>
      <input type="text" size="5" name="numeroRate" value='<%= serviceContract.getNumeroRate() == -1 ? "" : String.valueOf(serviceContract.getNumeroRate()) %>' />
    </td>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="account.sc.rinnovo">Rinnovabile</dhv:label>
    </td>
    <td>
      <input type="checkbox" name="chkRinnovo" value="true" onclick="javascript:setField('rinnovo',document.addServiceContract.chkRinnovo.checked,'addServiceContract');" <%= serviceContract.getRinnovo() ? " checked" : ""%>>
      <input type="hidden" name="rinnovo" value="<%= serviceContract.getRinnovo() ? "1" : "0"%>" />
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="account.sc.billingNotes">Billing Notes</dhv:label>
    </td>
    <td>
      <textarea name="contractBillingNotes" rows="3" cols="50"><%= toString(serviceContract.getContractBillingNotes()) %></textarea>
    </td>
  </tr>
</table>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="account.sc.blockHourInformation">Block Hour Information</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
    <dhv:label name="account.sc.totalHoursRemaining">Total Hours Remaining</dhv:label>
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <input type="text" disabled name="hoursRemaining" id="hoursRemaining" size="6" color="#cccccc" value="<%= ((serviceContract.getId() == -1) && (serviceContract.getTotalHoursRemaining() == 0))? "" : "" + serviceContract.getTotalHoursRemaining() %>"/>
        </td>
        <td>
          <input type="hidden" name="totalHoursRemaining" id="totalHoursRemaining" value="<%= ((serviceContract.getId() == -1) && (serviceContract.getTotalHoursRemaining() == 0)) ? "" : "" + serviceContract.getTotalHoursRemaining() %>"/>
          <dhv:evaluate if="<%= serviceContract.getId() == -1 %>">
          &nbsp [<a href="javascript:popContractHours('totalHoursRemaining','hoursRemaining', 'adjustmentReason','reason','adjustmentNotes','notes');"><dhv:label name="account.sc.adjust">Adjust</dhv:label></a>]
          &nbsp [<a href="javascript:clearAdjustment();"><dhv:label name="button.clear">Clear</dhv:label></a>]
          </dhv:evaluate>
          <dhv:evaluate if="<%= serviceContractHoursHistory.size() > 0 %>">
          &nbsp [<a href="javascript:popURL('AccountsServiceContracts.do?command=HoursHistory&id=<%= serviceContract.getId() %>&popup=true&popupType=inline','Details','650','500','yes','yes');"><dhv:label name="accountsassets.history.long_html">History</dhv:label></a>]
          </dhv:evaluate>&nbsp;
        </td>
        </tr>
      </table>
    </td>
  </tr>
  <dhv:evaluate if="<%= serviceContract.getId() != -1 %>">
    <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="account.sc.adjustmentHours">Adjustment Hours</dhv:label>
      </td>
      <td>
       <table cellspacing="0" cellpadding="0" border="0" class="empty">
        <tr>
          <td>
            <div id="hours">
<% if(serviceContract.getAdjustmentHours() != 0.0) {%>
  <%= serviceContract.getAdjustmentHours() %>
<%} else {%>
  <dhv:label name="account.sc.noAdjustment">No adjustment</dhv:label>
<%}%>
            </div>
          </td>
          <td>
            <input type="hidden" name="adjustmentHours" id="adjustmentHours" value="<%=serviceContract.getAdjustmentHours()%>" />
            &nbsp [<a href="javascript:popContractHours('adjustmentHours','hours', 'adjustmentReason','reason','adjustmentNotes','notes');"><dhv:label name="account.sc.adjust">Adjust</dhv:label></a>]
            &nbsp [<a href="javascript:clearAdjustment();"><dhv:label name="button.clear">Clear</dhv:label></a>]
          </td>
        </tr>
      </table>
     </td>
    </tr>
  </dhv:evaluate>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="account.sc.adjustmentReason">Adjustment Reason</dhv:label>
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="reason"><%= toHtml(hoursReasonList.getSelectedValue(serviceContract.getAdjustmentReason())) %></div>
        </td>
        <td>
          <input type="hidden" name="adjustmentReason" id="adjustmentReason" value="<%=serviceContract.getAdjustmentReason()%>" />
        </td>
      </tr>
    </table>
   </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
          <dhv:label name="account.sc.adjustmentNotes">Adjustment Notes</dhv:label>
    </td>
    <td>
     <table cellspacing="0" cellpadding="0" border="0" class="empty">
      <tr>
        <td>
          <div id="notes"><%=(("".equals(serviceContract.getAdjustmentNotes()) || (serviceContract.getAdjustmentNotes() == null)) ? "Nessun aggiustamento" : serviceContract.getAdjustmentNotes()) %></div>
        </td>
        <td>
          <input type="hidden" name="adjustmentNotes" id="adjustmentNotes" value="<%=(("".equals(serviceContract.getAdjustmentNotes()) || (serviceContract.getAdjustmentNotes() == null))? "" : serviceContract.getAdjustmentNotes()) %>" />
        </td>
      </tr>
    </table>
   </td>
  </tr>
  <dhv:evaluate if="<%= serviceContract.getId() != -1 %>">
    <tr class="containerBody">
      <td class="formLabel">
              <dhv:label name="account.sc.hoursAfterAdjustment">Hours after Adjustment</dhv:label>
      </td>
      <td>
        <div id="netRemainingHours">
<% if(serviceContract.getNetHours() != 0.0) {%>
  <%= serviceContract.getNetHours() %>
<%} else {%>
  <dhv:label name="account.sc.noAdjustment">No adjustment</dhv:label>
<%}%>
        </div>
         <input type="hidden" name="netHours" id="netHours" value="<%=((serviceContract.getNetHours() == 0.0) ? "" : "" + serviceContract.getNetHours())%>" />
     </td>
    </tr>
  </dhv:evaluate>
</table>
<br />
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
      <strong><dhv:label name="accounts.accountasset_include.ServiceModelOptions">Service Model Options</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="accounts.accountasset_include.ResponseTime">Response Time</dhv:label>
    </td>
    <td>
      <%= responseModelList.getHtmlSelect("responseTime", serviceContract.getResponseTime()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="accounts.accountasset_include.TelephoneService">Telephone Service</dhv:label>
    </td>
    <td>
      <%= phoneModelList.getHtmlSelect("telephoneResponseModel", serviceContract.getTelephoneResponseModel()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
    <dhv:label name="accounts.accountasset_include.OnsiteService">Onsite Service</dhv:label>
    </td>
    <td>
      <%= onsiteModelList.getHtmlSelect("onsiteResponseModel", serviceContract.getOnsiteResponseModel()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="account.sc.emailSercive">Email Service</dhv:label>
    </td>
    <td>
      <%= emailModelList.getHtmlSelect("emailResponseModel", serviceContract.getEmailResponseModel()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap valign="top" class="formLabel">
      <dhv:label name="account.sc.serviceModelNotes">Service Model Notes</dhv:label>
    </td>
    <td>
      <textarea name="serviceModelNotes" rows="3" cols="50"><%= toString(serviceContract.getServiceModelNotes()) %></textarea>
    </td>
  </tr>
   <input type="hidden" name="modified" value="<%= serviceContract.getModified() %>" />
   <input type="hidden" name="trashedDate" value="<%= serviceContract.getTrashedDate() %>" />
</table>
<%= addHiddenParams(request, "popup|popupType|actionId") %>

