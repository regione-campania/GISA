<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.modules.impiantimacellazione.base.*,org.aspcfs.modules.audit.base.*,org.aspcfs.utils.web.*,org.aspcfs.utils.*,java.text.DateFormat" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.impiantimacellazione.base.Organization" scope="request"/>
<jsp:useBean id="AuditDetails" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="checklistList" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="auditChecklist" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="typeList" class="org.aspcfs.utils.web.CustomLookupList" scope="request"/>
<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAttenzioneChecklist.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<%@ include file="../initPage.jsp" %>

<form name="addAccountAudit" method="post" action="AccountsAudit.do?command=Save&auto-populate=true" onSubmit="return checkForm();">

<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td width="100%">
  <a href="ImpiantiMacellazione.do"><dhv:label name="accounts.accounts">Accounts</dhv:label></a> > 
  <a href="ImpiantiMacellazione.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="ImpiantiMacellazione.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.details">Account Details</dhv:label></a> >
  <a href="ImpiantiMacellazione.do?command=ViewVigilanza&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
  <a href="AccountVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
  <a href="AccountsAudit.do?command=List&orgId=<%= OrgDetails.getOrgId() %>"><dhv:label name="accounts.Audit">Check List</dhv:label></a> >
  <dhv:label name="audit.aggiungiAudit">Aggiungi Check List</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>

<dhv:container name="impiantiMacellazione" selected="audit" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>'>
<% if ( OrgDetails.getAccountSize() != -1 ) { %>
<input type="hidden" name="aggiorna" value="" />
<input type="submit" id="btnSave" value="<dhv:label name="button.save">Save</dhv:label>">
<input type="button" onclick="if( confirm('Sei sicuro di voler anche aggiornare il livello di rischio?')  && checkForm() ){document.forms[0].aggiorna.value='yes'; document.forms[0].submit();}" id="btnSaveAggiorna" value="Salva e Aggiorna">
<input type="button" value="Annulla" onClick="window.location.href='AccountsAudit.do?command=List&orgId=<%=OrgDetails.getOrgId()%>';this.form.dosubmit.value='false';" />
<br/><br/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr class="containerBody">
    <th colspan="2">
      <strong><dhv:label name="audit.nuovoAudit">Nuova Check List</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="audit.categoriaOsa">Tipo Check List kkk</dhv:label>
    </td>
    <td>
      <%= toHtml(OrgCategoriaRischioList.getSelectedValue(OrgDetails.getAccountSize())) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Identificativo C.U.</dhv:label>
    </td>
    <td>
      <%= (String)request.getAttribute("idControllo") %>
      <input type="hidden" name="idControllo" id="idControllo" value="<%= (String)request.getAttribute("idControllo") %>">
      <input type="hidden" name="idC" id="idC" value="<%= (String)request.getAttribute("idControllo") %>">
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">

      <dhv:label name="audit.componentiGruppo">Componenti Gruppo</dhv:label>
    </td>
    <td>
      <input type="text" size="50" name="componentiGruppo"/>
    </td>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="audit.data1">Data</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addAccountAudit" field="data1" showTimeZone="false" timestamp="<%= new Timestamp(new java.util.Date().getTime()) %>" /> <font size="0.1" color="#FF0000">*Attenzione! Il punteggio ottenuto verrà sommato ai punteggi delle altre Chek List con stessa data</font>  
      <a href="javascript:popAttenzioneChecklist('addAccountAudit','data1');"><dhv:label name=""><font size="0.1" color="#FF0000">Scheda</font></dhv:label></a>
      <%= showAttribute(request, "data1Error") %>
    </td>
  </tr>
  <tr class="containerBody" style="display: none">
    <td nowrap class="formLabel">
      <dhv:label name="audit.data2">Data 2</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addAccountAudit" field="data2" showTimeZone="false" />
      <%= showAttribute(request, "data2Error") %>
    </td>
  </tr>
</table>
</br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th colspan="2">
	    <strong><dhv:label name="accounts.accounts_add.AdditionalDetails">Additional Details</dhv:label></strong>
	  </th>
  </tr>
  <tr class="containerBody">
    <td valign="top" nowrap class="formLabel">
      <dhv:label name="audit.note">Note</dhv:label>
    </td>
    <td>
      <TEXTAREA name="note" ROWS="3" COLS="50"></TEXTAREA>
    </td>
  </tr>
</table>
<br/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<%
int domandaElementId = 0;
String scriptDichiar = "idTypeList = new Array(); operazioneList = new Array(); notaList = new Array(); valoreRangeList = new Array();"; 
scriptDichiar += " idList = new Array(); parentIdList = new Array(); rispostaList = new Array(); puntiList = new Array(); requiredList = new Array();";
String scriptFunc = "";
int indexChecklistList = 0;
Iterator itrTypeList = typeList.iterator();
if ( itrTypeList.hasNext() ) {
  while (itrTypeList.hasNext()) {
	CustomLookupElement thisTypeList = (CustomLookupElement) itrTypeList.next();
	int checklistTypeId = Integer.valueOf(thisTypeList.getValue("code"));
    String checklistDescription = thisTypeList.getValue("description");
    String checklistRange = thisTypeList.getValue("range");
    
    CustomLookupList checklist = (CustomLookupList) checklistList.get(indexChecklistList);
    indexChecklistList++;
    scriptDichiar += "idTypeList[\""+ (indexChecklistList) +"\"] = " + checklistTypeId + ";";
    scriptDichiar += "\naggiornaChecklistType("+indexChecklistList+",'x','','');";
%>
  <tr class="containerBody">
    <th colspan="7" style="background-color: #ccff99; padding: 5px;"><%= checklistDescription%></th>
  </tr>
  <tr class="containerBody">
    <th>&nbsp;</th>
    <th width="50%">Domanda</th>
    <th width="50%">Ulteriore quesito in caso di risposta affermativa</th>
    <th>Modalità di controllo</th>
    <th>SI</th>
    <th>NO</th>
    <th>Punti</th>
  </tr>
  <%
  Iterator itrChecklist = checklist.iterator();
  
  if ( itrChecklist.hasNext() ) {
    String level = null;
    int rowid = 0;
    while (itrChecklist.hasNext()) {
      rowid = (rowid != 1?1:2);
      CustomLookupElement thisChecklist = (CustomLookupElement) itrChecklist.next();
      boolean enabled = thisChecklist.getValue("enabled") == "true" ? true : false;
      boolean defaultItem = thisChecklist.getValue("default_item") == "true" ? true : false;
      String id = thisChecklist.getValue("id");
      String parentId = thisChecklist.getValue("parent_id");
      String domanda = thisChecklist.getValue("domanda");
      String descrizione = thisChecklist.getValue("descrizione");
      String puntiNo = thisChecklist.getValue("punti_no");
      String puntiSi = thisChecklist.getValue("punti_si");
      level = thisChecklist.getValue("level");
      int code = id.startsWith("--") ? -1 : Integer.parseInt(id);
      
      int punti = 0;
      String punti_default = thisChecklist.getValue("punti_no");
      punti = Integer.parseInt(punti_default);
      if(enabled){
      scriptDichiar += "idList[\""+ (domandaElementId++) +"\"] = " + code + ";";
      //scriptDichiar += "aggiornaListElement("+code+","+parentId+",0,'');";
      scriptDichiar += "aggiornaListElement("+code+","+parentId+"," + ((parentId.equals("-1")) ? (puntiNo) : ("0")) + ",'no');";
      scriptFunc += "\nfunction func"+code+"(risp){";
    
      scriptFunc += "  if(risp == 'si') {";
      scriptFunc += "    document.getElementById(\"punti"+ code +"\").value = " + puntiSi + ";";
      scriptFunc += "    aggiornaListElement("+ code +"," + parentId + "," + puntiSi + ",risp);";
      scriptFunc += "  }";
      scriptFunc += "  if(risp == 'no') {";
      scriptFunc += "    document.getElementById(\"punti"+ code +"\").value = " + puntiNo + ";";
      
      scriptFunc += "    aggiornaListElement("+ code +","+ parentId +","+ puntiNo +",risp);";
      scriptFunc += "  }";
      scriptFunc += "}";
      
      if (parentId != null && !parentId.equals("-1")) { 
        rowid = (rowid != 1?1:2);
      } else {
      }
  %>
	  <tr class="row<%= rowid %>">
	    <td valign="center"><%= level %></td>
  <%if (parentId == null || parentId.equals("-1")) {%>
        <td valign="center"><%= toHtml(domanda) %></td><td>&nbsp;</td>
  <%} else {%>
        <td>&nbsp;</td><td valign="center"><%= toHtml(domanda) %></td>
  <%}%>
        <td valign="center"><%= toHtml(descrizione) %></td>
	    <td align="center"><input type="radio" <%=(parentId.equals("-1")) ? ("") : ("disabled")%> id="risposta<%= code%>1" name="risposta<%= code%>" value="1" onclick="javascript:func<%= code%>('si');"/></td> 
	     <td align="center">
    	 	<input	type="radio"
    	 			id="risposta<%= code%>2"
    	 			name="risposta<%= code%>"
    	 			value="0"
    	 			onclick="javascript:func<%= code%>('no');"
    	 			<%=(parentId.equals("-1")) ? ("checked=\"checked\"") : ("disabled") %>/>
    	 </td>
	    <%--<td align="center"><input type="radio" id="risposta<%= code%>2" name="risposta<%= code%>" value="0" onclick="javascript:func<%= code%>('no');" <%=(parentId.equals("-1")) ? ("checked=\"checked\"") : ("")%>/></td>--%>
	    
	    <td align="center"><input style="width: 30px; background-color: #cccccc" readonly type="text" id="punti<%= code%>" name="punti<%= code%>" <%= (parentId.equals("-1")) ? ("value=\""+ punti +"\"") : ("")%>/></td>  
	  </tr>
<%}}//fine ciclo while checklist%> 

<%-- aggiunto per evitare all'ultima domanda di porre il quesito --%>
<%if(indexChecklistList != typeList.size())  {%>


<tr class="row<%= rowid+1%>">
  <td><%= Integer.parseInt(level)+1%></td>
  <td colspan="3">Esistono delle condizioni particolari non contemplate sopra che possono diminuire o aumentare il punteggio di rischio? 
  Se si, riportarle qui sotto aggiungendo o sottraendo un punteggio nel range +<%= checklistRange%>, -<%= checklistRange%> da scrivere nella casella a lato</td>
  <td>
    <input type="button" id="btnAggiungiPunti<%= indexChecklistList%>" value="+" onclick="javascript:aggiornaChecklistType(<%= indexChecklistList%>,'+',valoreRange<%= indexChecklistList%>.value,'');"/>
  </td>
  <td>
    <input type="button" id="btnSottraiPunti<%= indexChecklistList%>" value="-" onclick="javascript:aggiornaChecklistType(<%= indexChecklistList%>,'-',valoreRange<%= indexChecklistList%>.value,'');"/>
  </td>
  <td>
    <input type="text" style="width: 30px;" id="valoreRange<%= indexChecklistList%>" name="valoreRange<%= indexChecklistList%>"/>
    <input type="hidden" id="operazione<%= indexChecklistList%>" name="operazione<%= indexChecklistList%>"/>
  </td>
</tr>

<tr class="row<%= rowid+1%>">
  <td colspan="7"><TEXTAREA ROWS="3" cols="80" id="nota<%= indexChecklistList%>" name="nota<%= indexChecklistList%>"></TEXTAREA></td>
</tr>
<%} %>


   <%--DEVO AGGIUSTARE QUESTO CONTROLLO --%>
  <% 
  if(  
		  /*
		  (checklistDescription.startsWith("Capitolo IX - DATI STORICI"))
		  || (checklistDescription.startsWith("DATI STORICI"))
		  || (checklistDescription.startsWith("Capitolo VIII - DATI STORICI"))
		  || (checklistDescription.startsWith("Capitolo VII - DATI STORICI"))
		  || (checklistDescription.startsWith("Capitolo VI - DATI STORICI"))
		  */
		  checklistDescription.toUpperCase().indexOf( "DATI STORICI" ) > -1
    )
  {
	   

	int indice = indexChecklistList;
	
	%>
<tr class="row<%= rowid+1%>">
  <td colspan="1">&nbsp;</td>
  <td colspan="3">Inserire di lato il punteggio storico delle non conformità (NB. tale punteggio corrisponde alla somma dei punteggi delle non conformità rilevate durante i controlli ufficiali degli ultimi 5 anni dalla data di oggi, ovviamente con l'esclusione dei punteggi delle check list compilate nell'ambito della sorveglianza)</br><font size="0.1" color="#FF0000">*Attenzione! Tale punteggio deve essere aggiunto solo dopo la compilazione della check list</font></td>
  <td colspan="2">
    <input type="button" id="btnAggiungiPunti<%= indice%>" value="      +      " onclick="javascript:aggiornaTot(<%= indice%>, '+',punteggioUltimiAnni.value);"/>
  </td>
  <td>
    <input type="text" style="width: 30px;" id="punteggioUltimiAnni" name="punteggioUltimiAnni"/>
    <input type="hidden" id="operazione<%= indice%>U" name="operazione<%= indice%>U"/>
    <input type="hidden" id="operazione<%= indice%>" name="operazione<%= indice%>" value="+"/>
    <input type="hidden" style="width: 30px;" id="valoreRange<%= indexChecklistList%>" name="valoreRange<%= indexChecklistList%>"/>
    <input type="hidden" id="nota<%= indice%>" name="nota <%=indice%>" />
  </td>
</tr>

<%}} else {%>
      <tr class="containerBody">
        <td colspan="7"><dhv:label name="">Nessuna domanda presente.</dhv:label></td>
      </tr>
<%}%>

<%}//fine ciclo while lista checklist%>


<%-- prova>
<% 
	int indice = 1 + indexChecklistList;%>
<tr >
  <td colspan="1">&nbsp;</td>
  <td colspan="3">Inserire di lato la somma dei punteggi delle non conformità rilevate durante gli ultimi 5 anni dalla data di oggi (N.B. ovviamente nella somma non vanno inclusi i punteggi delle check list compilate nell''ambito della sorveglianza) - documentale</br><font size="0.1" color="#FF0000">*Attenzione! Tale punteggio deve essere aggiunto solo dopo la compilazione della check list</font></td>
  <td colspan="2">
    <input type="button" id="btnAggiungiPunti<%= indice%>" value="      +      " onclick="javascript:aggiornaTot(<%= indice%>, '+',punteggioUltimiAnni.value);"/>
  </td>
  <td>
    <input type="text" style="width: 30px;" id="punteggioUltimiAnni" name="punteggioUltimiAnni"/>
  </td>
</tr>


<fine prova --%>

<%} else {%>
      <tr class="containerBody">
        <td colspan="7"><dhv:label name="">Nessuna Check List presente.</dhv:label></td>
      </tr>
<%}%>

<tr class="containerBody" style="background-color: #ffff66; font-weight: bolder;">
  <td colspan="3">&nbsp;</td>
  <td nowrap>Punteggio Totale</td>
  <td colspan="3"><input style="width: 80px; background-color: #cccccc" type="text" readonly id="livelloRischio" name="livelloRischio"/>
  <%-- <input style="width: 80px; background-color: #cccccc" type="hidden" readonly id="livelloRischioFinale" name="livelloRischioFinale" value=<%= Audit.getLivelloRischioFinale() %>>--%>
  </td>
</tr>

<tr class="containerBody" style="background-color: #ffff66; font-weight: bolder;">
  <td colspan="3">&nbsp;</td>
  <td nowrap>Categoria</td>
  <td colspan="3"><input style="width: 80px; background-color: #cccccc" type="text" readonly id="categoriaRischio" name="categoriaRischio"/>
  <%-- <input style="width: 80px; background-color: #cccccc" type="hidden" readonly id="livelloRischioFinale" name="livelloRischioFinale" value=<%= Audit.getLivelloRischioFinale() %>>--%>
  </td>
</tr>
</table>

<br />
<input type="submit" id="btnSave" value="<dhv:label name="button.save">Save</dhv:label>">
<input type="button" onclick="if( confirm('Sei sicuro di voler anche aggiornare il livello di rischio?') && checkForm() ){document.forms[0].aggiorna.value='yes'; document.forms[0].submit();}" id="btnSaveAggiorna" value="Salva e Aggiorna">
<input type="button" value="Annulla" onClick="window.location.href='AccountsAudit.do?command=List&orgId=<%=OrgDetails.getOrgId()%>';this.form.dosubmit.value='false';" />
<input type="hidden" name="dosubmit" value="true" />
<input type="hidden" name="rowcount" value="0">
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>" />
<br />
<script language="JavaScript">
<%= scriptDichiar %>
<%= scriptFunc %>

function aggiornaChecklistType(index, operazione, valoreRange, nota){
  if (operazione == '+'){
    document.getElementById('btnAggiungiPunti' + index).style.backgroundColor = '66ff00';
    document.getElementById('btnSottraiPunti' + index).style.backgroundColor = 'white';
  }
  if (operazione == '-'){
    document.getElementById('btnSottraiPunti' + index).style.backgroundColor = '66ff00';
    document.getElementById('btnAggiungiPunti' + index).style.backgroundColor = 'white';
  }

  operazioneList[index] = operazione;
  valoreRangeList[index] = valoreRange;
  notaList[index] = nota;
  document.getElementById('operazione' + index).value = operazione;
  document.getElementById('valoreRange' + index).value = valoreRange;
  document.getElementById('nota' + index).value = nota;

  aggiornaTotale();
}

function aggiornaTot(index, operazione, valoreRange){
	  
	if (operazione == '+'){
	    document.getElementById('btnAggiungiPunti' + index).style.backgroundColor = '66ff00';
	  }
	  
	  var op = operazione;
	  var valoreR = valoreRange;
	  var totale = Number(document.getElementById('livelloRischio').value);

	  if (valoreRange != ''){
	      if (operazione == '+'){
	    	  totale += Number(valoreR);
	      }
	      if (operazione == '-'){
	    	  totale -= Number(valoreR);
	      }
	    }
	  document.getElementById('livelloRischio').value = totale;
	    
	}

function aggiornaTotale(){  //in questa funzione entra ogni qualvolta clicco su un radio oltre ad eseguirla all'inizio
	
  var totRisposte = 0;
  for (n=0;n<idList.length;n++) { 
    if (rispostaList[idList[n]] != ''){
      totRisposte += puntiList[idList[n]];
    } else {
      document.getElementById('risposta' + idList[n] + '1').checked = false;
      document.getElementById('risposta' + idList[n] + '2').checked = false;
      document.getElementById('punti' + idList[n]).value = '';
    }
  }
  
  var totRange = 0;
  for (n=1;n<idTypeList.length;n++) { //ho messo 0 piuttosto che 1
    var valoreRange = valoreRangeList[n];
    var operazione = operazioneList[n];
    if (valoreRange != ''){
      if (operazione == '+'){
        totRange += Number(valoreRange);
      }
      if (operazione == '-'){
        totRange -= Number(valoreRange);
      }
    }
  }
   
  document.getElementById('livelloRischio').value = totRisposte + totRange;
}


function aggiornaCategoria(){
	 
	 
	 
	  var totale = Number(document.getElementById('livelloRischio').value);

	 
	  var categoria=0;
	  if((totale==null) || (totale<1 && totale<1) )
		   categoria=3;
	  else
	   if(totale >= 1 && totale <= 100)
	   categoria= 1 ;
	   else
	   if(totale <=201 && totale >= 101)
	   categoria= 2 ;
	   else
	   if(totale >= 201 && totale <= 300)
	   categoria= 3 ;
	   else
	   if(totale >= 301 && totale <= 400)
	   categoria= 4 ;
	   else
	   if(totale >= 401 )
	 	 categoria=5;
	   else
		   categoria="-";   
		   
	   	  
	  document.getElementById('categoriaRischio').value = categoria;
	    
	}

function aggiornaListElement(id, parentId, punti, risposta){
	//alert(id + " "+ parentId+ " "+punti+" "+risposta);
  rispostaList[id] = risposta; 
  puntiList[id] = punti;
  requiredList[id] = true;
  if (parentId == null || parentId == '-1'){
    for (n=0;n<idList.length;n++) {
      if (parentIdList[idList[n]] == id) {
        if (rispostaList[id] == 'si'){
          abilita(idList[n]);
        } else {
          disabilita(idList[n]);
        }
      }
    }
  } else {
    parentIdList[id] = parentId;
    if (risposta == ''){
      disabilita(id);
    }
  }
  aggiornaTotale();
  aggiornaCategoria();
}

function disabilita(id){
  requiredList[id] = false;
  rispostaList[id] = ''; 

  document.getElementById('risposta'+id+'1').checked = false;
  document.getElementById('risposta'+id+'2').checked = false;
  document.getElementById('risposta'+id+'1').disabled = true;
  document.getElementById('risposta'+id+'2').disabled = true;
  document.getElementById('punti'+id).value = '';
}

function abilita(id){
  requiredList[id] = true;
  rispostaList[id] = ''; 
  document.getElementById('risposta'+id+'1').disabled = false;
  document.getElementById('risposta'+id+'2').disabled = false;
}

function checkForm(){
  for (n=0;n<idList.length;n++) {
    if(rispostaList[idList[n]] == '' && requiredList[idList[n]] == true){
      alert('Prima di salvare rispondere a tutte le domande');
      return false;
    }
  }
  return true;
}
</script>
<%} else {%>
    <br/>
    <dhv:label name="">Selezionare un Tipo Check List prima di aggiungere una Check List.</dhv:label>
<%}%>
</dhv:container>
</form>


