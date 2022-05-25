<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>

<script>

function save(btn)
{ 
	setStato(btn);
	if (btn == 1)
	{
		risposta = checkForm() ;
		if(risposta==true)
		{
			setParametri();
			document.Objapplet.serializer();
		}
	}
	else
	{
		setParametri();
		document.Objapplet.serializer();
		addAccountAudit
	}
}
function setParametri()
{

	document.Objapplet.getFieldForm('addAccountAudit',<%=User.getUserId()%> , '<%=User.getUsername()%>');
	
}

</script>

<APPLET name="Objapplet" codebase="/centric_osa/applet" CODE="test.SerializerApplet.class"  width="0"  height="0" archive="SignedSerializerApplet.jar"></APPLET> 

<% if ( OrgDetails.getAccountSize() != -1 ) { %>

<%@page import="org.aspcfs.utils.web.CustomLookupElement"%><input type="hidden" name="aggiorna" value="" />
<input type = "hidden" name = "accountSize" value = "<%=request.getAttribute("TipoCheckList")%>" >
<input type = "hidden" name = "isPrincipale" value = "<%=request.getAttribute("isPrincipale")%>">

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr class="containerBody">
    <th colspan="2">
      <strong><dhv:label name="audit.nuovoAudit">Nuova Check List</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="audit.categoriaOsa">Tipo Check List</dhv:label>
    </td>
    <td>
      <%= toHtml(OrgCategoriaRischioList.getSelectedValue(OrgDetails.getAccountSize())) %>
    </td>
  </tr>
  <input type="hidden" id="TipoC" name="TipoC" value="<%=ControlloUfficiale.getTipoCampione() %>"/>
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="">Identificativo C.U.</dhv:label>
    </td>
    <td>
      <%= (String)request.getAttribute("idControllo") %>
      <input type="hidden" name="stato" value="<%="Definitiva" %>" />
      <input type="hidden" name="idControllo" id="idControllo" value="<%= (String)request.getAttribute("idControllo") %>">
      <input type="hidden" name="idC" id="idC" value="<%= (String)request.getAttribute("idControllo") %>">
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

<br/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<%
int domandaElementId = 0;
String scriptDichiar = "idTypeList = new Array(); operazioneList = new Array(); notaList = new Array(); valoreRangeList = new Array();"; 
scriptDichiar += " idList = new Array();grandParentIdList = new Array(); parentIdList = new Array(); rispostaList = new Array(); puntiList = new Array(); requiredList = new Array();";
String scriptFunc = "";
int indexChecklistList = 0;
Iterator itrTypeList = typeList.iterator();
int i=0;

if ( itrTypeList.hasNext() ) {
	int progressivoCapitolo = 0;
  while (itrTypeList.hasNext()) {
	  progressivoCapitolo = progressivoCapitolo+1 ;
	CustomLookupElement thisTypeList = (CustomLookupElement) itrTypeList.next();
	int checklistTypeId = Integer.valueOf(thisTypeList.getValue("code"));
    String checklistDescription = thisTypeList.getValue("description");
    String checklistRange = thisTypeList.getValue("range");
    CustomLookupList checklist = (CustomLookupList) checklistList.get(indexChecklistList);
    indexChecklistList++;
    scriptDichiar += "idTypeList[\""+ (indexChecklistList) +"\"] = " + checklistTypeId + ";";
    //scriptDichiar += "\naggiornaChecklistType("+indexChecklistList+",'x','','');";
%>
<script>

domandePerCapitolo[<%=checklistTypeId %> ] = new Array();
sottoDomandePerCapitolo[<%=checklistTypeId %> ] = new Array();

</script>

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
		  
		  if(thisTypeList.isDisabilitabile()==true)
		  {
			  %>
			  
			  <tr class="row4" >
    
    <td width="50%" colspan="4" align="center">  QUESTO CAPITOLO DEVE ESSERE COMPILATO ?</td>
   
    <td align="center"><input type="radio" id ="risposta<%=checklistTypeId%>1" value ="1" name = "disabilita<%=checklistTypeId%>1" onclick="disabilitaCapitolo('<%=checklistTypeId %>','si',<%=i %>,'<%=progressivoCapitolo %>')"/></td> 
	     <td align="center">
    	 	<input	type="radio" value = "2" id ="risposta<%=checklistTypeId%>2" name = "disabilita<%=checklistTypeId%>1" onclick="disabilitaCapitolo('<%=checklistTypeId %>','no',<%=i %>,'<%=progressivoCapitolo %>')" />
    	 			
    	 </td>
    <td>&nbsp;</td>
  </tr>
			  
			  <%
			  
		  }else
		  {
			  %>
			  <input type = "hidden" name = "disabilita<%=checklistTypeId %>1" value ="3">
			  <%
		  }
		  
		%>  
	
  
  
  
  
  <%
  Iterator itrChecklist = checklist.iterator();
  
  if ( itrChecklist.hasNext() ) {
    String level = null;
    int rowid = 0;
  	int rowid2 = 5;
    int rowidcurr = 0 ;
    String last_gp = "" ;
    int numDomanda =0;
    while (itrChecklist.hasNext()) {
    	i++;
      rowid = (rowid != 1?1:2);
      
      CustomLookupElement thisChecklist = (CustomLookupElement) itrChecklist.next();
      boolean enabled = thisChecklist.getValue("enabled") == "true" ? true : false;
      boolean defaultItem = thisChecklist.getValue("default_item") == "true" ? true : false;
      String id = thisChecklist.getValue("id");
      String parentId = thisChecklist.getValue("parent_id");
      String grandParentId = thisChecklist.getValue("grand_parents_id");
      String super_domanda = thisChecklist.getValue("super_domanda");
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
      //scriptDichiar += "aggiornaListElement("+code+","+parentId+"," + ((parentId.equals("-1")) ? (puntiNo) : ("0")) + ",'no');";
  
      //aggiunto
      scriptDichiar += "defaultValori("+code+","+parentId+",0,'',"+grandParentId+");";
      scriptFunc += "\nfunction func"+code+"(risp){";
      scriptFunc += "  if(risp == 'si') {";
      scriptFunc += "    document.getElementById(\"punti"+ code +"\").value = " + puntiSi + ";";
      scriptFunc += "    aggiornaListElement("+ code +"," + parentId + ","+grandParentId + "," + puntiSi + ",risp);";
      scriptFunc += "  }";
      scriptFunc += "  if(risp == 'no') {";
      scriptFunc += "    document.getElementById(\"punti"+ code +"\").value = " + puntiNo + ";";
      scriptFunc += "    aggiornaListElement("+ code +","+ parentId +","+grandParentId+","+ puntiNo +",risp);";
      scriptFunc += "  }";
      scriptFunc += "}";
      
      %>
      <script>
      
      <%if(parentId.equals("-1"))
  {
  %>
 

  	domandePerCapitolo[<%=checklistTypeId %> ][<%=numDomanda%> ]  = <%=code%>;
<%

      }
  else
  {
%>

  	sottoDomandePerCapitolo[<%=checklistTypeId %> ][<%=numDomanda%> ]  = <%=code%>;
<%
      }

      %>
      </script>
      <%
     if ( grandParentId != null && (!grandParentId.equals("-1") && !grandParentId.equals(last_gp) ) )
     {
    	 
    	 if (rowid2==5)
    		 rowid2 = 6;
    	 else
    		 rowid2 = 5;
    	 rowidcurr = rowid2;
    	 last_gp = grandParentId ;
     }
     else
      if (parentId != null && !parentId.equals("-1") && grandParentId.equals("-1")) { 
        rowid = (rowid != 1?1:2);
        rowidcurr = rowid;
      } else {
    	  if ( grandParentId != null && grandParentId.equals("-1"))
    	  {
    		  if(super_domanda!=null && super_domanda.equalsIgnoreCase("true")){
    			  if (rowid2==5)
    				  rowidcurr = 6;
    		    	 else
    		    		 rowidcurr = 5;
    		  }
    		  else
    		  {
    		  //rowid = (rowid != 1?1:2);
    	        rowidcurr =rowid;
    		  }
    	  }
      }
      
  %>
	  <tr  class="row<%= rowidcurr%>" id="<%=i %>" >
	    <td valign="center" ><%= level %></td>
  <%if (parentId == null || parentId.equals("-1")) {%>
        <td valign="center" <%if(super_domanda!=null && super_domanda.equalsIgnoreCase("true")){%>colspan="2"<%} %>><%= toHtml(domanda) %></td>
        <%if(super_domanda ==null || super_domanda.equalsIgnoreCase("false")){%>
        <td>&nbsp;</td>
        <%} %>
  <%} else {%>
  		
        <td>&nbsp;</td><td valign="center"><%= toHtml(domanda) %></td>
  <%}%>
        <td valign="center"><%= toHtml(descrizione) %></td>
	    <td align="center">
	    <input type="radio" <%=(parentId.equals("-1") && grandParentId !=null && grandParentId.equals("-1") ) ? ("") : ("disabled")%> id="risposta<%= code%>1" name="risposta<%= code%>" value="1" onclick="javascript:func<%= code%>('si'); lastDomanda(<%=i %>,<%=rowid %>,<%= code%>); "/></td> 
	     <td align="center">
    	 	<input	type="radio"
    	 			id="risposta<%= code%>2"
    	 			name="risposta<%= code%>"
    	 			value="0"
    	 			onclick="javascript:func<%= code%>('no'); lastDomanda(<%=i %>,<%=rowid %>,<%= code%>); "
    	 			<%if(! parentId.equals("-1") ||( grandParentId !=null && ! grandParentId.equals("-1"))){ %>disabled="disabled" <%}  %>/>
    	 </td>
	    <%--<td align="center"><input type="radio" id="risposta<%= code%>2" name="risposta<%= code%>" value="0" onclick="javascript:func<%= code%>('no');" <%=(parentId.equals("-1")) ? ("checked=\"checked\"") : ("")%>/></td>--%>
	    
	    <td align="center"><%if(puntiNo.equals(puntiSi) && puntiNo.equals("0")){ %>&nbsp; <input style="width: 30px; background-color: #cccccc"  type="hidden" id="punti<%= code%>" name="punti<%= code%>" /><%}else{ %><input style="width: 30px; background-color: #cccccc" readonly type="text" id="punti<%= code%>" name="punti<%= code%>" /> <%} %></td>  
	  </tr>
<%}
      numDomanda++;  
    }//fine ciclo while checklist%> 

<%-- aggiunto per evitare all'ultima domanda di porre il quesito --%>
<%if(indexChecklistList != typeList.size())  {
	

if(((checklistDescription.equals("CAPITOLO IX: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)"))||
			  (checklistDescription.equals("CAPITOLO X: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)"))||
			  (checklistDescription.equals("CAPITOLO VIII: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)")))){

	
}else{



%>




<tr class="row<%= rowid+1%>" >
  <td><%= Integer.parseInt(level)+1%></td>
  <td colspan="3">Esistono delle condizioni particolari non contemplate sopra che possono diminuire o aumentare il punteggio di rischio? 
  Se si, riportarle qui sotto aggiungendo o sottraendo un punteggio nel range +<%= checklistRange%>, -<%= checklistRange%> da scrivere nella casella a lato e cliccare sul pulsante + o - solo dopo aver inserito un valore</td>
  <td>
    <input type="button" id="btnAggiungiPunti<%= indexChecklistList%>" value="+"  onmouseover="ddrivetip('Inserire prima il numero e poi cliccare su + o - ')"
      onmouseout="hideddrivetip()" onclick="javascript:aggiornaChecklistType(<%= indexChecklistList%>,'+',valoreRange<%= indexChecklistList%>.value,'',<%=checklistRange %>);" disabled="disabled"/>
  </td>
  <td>
    <input type="button" id="btnSottraiPunti<%= indexChecklistList%>" value="-" onmouseover="ddrivetip('Inserire prima il numero e poi cliccare su + o - ')"
      onmouseout="hideddrivetip()" onclick="javascript:aggiornaChecklistType(<%= indexChecklistList%>,'-',valoreRange<%= indexChecklistList%>.value,'',<%=checklistRange %>);" disabled="disabled"/>
  </td>
  <td>
    <input type="text" style="width: 30px;" id="valoreRange<%= indexChecklistList%>"  name="valoreRange<%= indexChecklistList%>" onmouseover="ddrivetip('Inserire prima il numero e poi cliccare su + o - ')"
     onmouseout="hideddrivetip(); abilitaPulsanti(<%= indexChecklistList%>)"/>
    <input type="hidden" id="operazione<%= indexChecklistList%>" name="operazione<%= indexChecklistList%>"/>
  </td>
</tr>

<tr class="row<%= rowid+1%>" >
  <td colspan="7"><TEXTAREA ROWS="3" cols="80" id="nota<%= indexChecklistList%>" name="nota<%= indexChecklistList%>"></TEXTAREA></td>
</tr>
<%}}else{ 

if(((checklistDescription.equals("CAPITOLO IX: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)"))||
			  (checklistDescription.equals("CAPITOLO X: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)"))||
			  (checklistDescription.equals("CAPITOLO VIII: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)"))))
{
	
	
}else{

	
%>


<input type="hidden" name="lastdomanda" value="1" id="last">
 
  
  <%}
}
  if(  
		 
		  checklistDescription.toUpperCase().indexOf( "DATI STORICI" ) > -1
    )
  {

	int indice = indexChecklistList;
	
	%>
	    
	<%-- input type="hidden" id="operazione<%= indice%>" name="operazione<%= indice%>" value="+"/--%>
	
	<input type="hidden" id="operazione<%= indice%>" name="operazione<%= indice%>"/>
    
    <input type="hidden" style="width: 30px;" id="valoreRange<%= indexChecklistList%>" name="valoreRange<%= indexChecklistList%>"/>
    <input type="hidden" id="nota<%= indice%>" name="nota <%=indice%>" />
  
	<%}
  

  if(ControlloUfficiale.getNumeroAudit()==0){
  
  if((ControlloUfficiale.getTipoCampione()!=3)&&((checklistDescription.equals("CAPITOLO X: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)") || checklistDescription.equalsIgnoreCase("CAPITOLO IX: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)"))||(checklistDescription.equals("CAPITOLO X: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)"))||(checklistDescription.equals("CAPITOLO VIII: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)")) ||(checklistDescription.equals("CAPITOLO IX:DATI STORICI")))){%>
<tr class="row<%= rowid+1%>">
  <td colspan="1">&nbsp;</td>
  <td colspan="3">Punteggio storico delle non conformità (N.B. tale punteggio corrisponde alla somma dei punteggi delle non conformità rilevate durante i controlli ufficiali degli ultimi 5 anni dalla data di oggi, ovviamente con l'esclusione dei punteggi delle check list compilate nell'ambito della sorveglianza).</br></td>
  <td colspan="2">
    <%--<input type="button" id="btnAggiungiPunti<%= indice%>" value="      +      " onclick="javascript:aggiornaTot(<%= indice%>, '+',punteggioUltimiAnni.value);"/>--%>
  </td>
  <td>
    <input type="text" style="width: 80px; background-color: #cccccc" readonly id="punteggioUltimiAnni" name="punteggioUltimiAnni" value="<%= request.getAttribute("punteggioUltimiAnni") %>"/>
    </td>
</tr>

<%} 
}else{%>
 <%if((ControlloUfficiale.getTipoCampione()!=3)&&((checklistDescription.equalsIgnoreCase("CAPITOLO IX: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)"))||(checklistDescription.equals("CAPITOLO X: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)"))||(checklistDescription.equals("CAPITOLO VIII: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)")) ||(checklistDescription.equals("CAPITOLO IX:DATI STORICI")))){%>

<tr class="row<%= rowid+1%>">
  <td colspan="1">&nbsp;</td>
  <td colspan="3">Punteggio delle Check List già Compilate nello Stesso Controllo</br></td>
  <td colspan="2">
      </td>
  <td>
    <input type="text" style="width: 80px; background-color: #cccccc" readonly  value="<%= request.getAttribute("punteggioCheckList") %>"/>
    </td>
</tr>


<input type="hidden" style="width: 80px; background-color: #cccccc"  id="punteggioUltimiAnni" name="punteggioUltimiAnni" value="0"/>
    
<%} }%>

<%}//fine ciclo while lista checklist%>

    



<%}} else {%>
      <tr class="containerBody">
        <td colspan="7"><dhv:label name="">Nessuna Check List presente.</dhv:label></td>
      </tr>
<%}%>


<tr class="containerBody" style="background-color: #ffff66; font-weight: bolder;">
  <td colspan="3">&nbsp;</td>
  <td nowrap>Punteggio Totale di Questa Check List</td>
  <td colspan="3"><input style="width: 80px; background-color: #cccccc" type="text" readonly id="livelloRischio" name="livelloRischio" <%if(request.getAttribute("punteggioUltimiAnni")!=null && request.getAttribute("prima") != null){ %>value="<%=request.getAttribute("punteggioUltimiAnni") %>"<%}else {%>value = "0"<%} %> />
  <%-- <input style="width: 80px; background-color: #cccccc" type="hidden" readonly id="livelloRischioFinale" name="livelloRischioFinale" value=<%= Audit.getLivelloRischioFinale() %>>--%>
  </td>
</tr>
<tr class="containerBody" style="background-color: #ffff66; font-weight: bolder;">
  <td colspan="3">&nbsp;</td>
  <td nowrap>Categoria Rischio con il nuovo punteggio</td>
  <td colspan="3"><input style="width: 80px; background-color: #cccccc" type="text" readonly id="categoriaRischio" name="categoriaRischio" value=""/>
  <%-- <input style="width: 80px; background-color: #cccccc" type="hidden" readonly id="livelloRischioFinale" name="livelloRischioFinale" value=<%= Audit.getLivelloRischioFinale() %>>--%>
  </td>
</tr>
 <%--<tr class="containerBody" style="background-color: #ffff66; font-weight: bolder;">
  <td colspan="3">&nbsp;</td>
  <td nowrap>Categoria</td>
  <td colspan="3"><input style="width: 80px; background-color: #cccccc" type="text" readonly id="" name="categoriaRischio" value="<%= Audit.getCategoria() %>">
  </td>
</tr>--%>
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
<br />

<input type="submit" id="btnSave2" value="<dhv:label name="">Salva</dhv:label>" onclick="this.style.disabled='true'">

<input type="hidden" name="dosubmit" value="true" />
<input type="hidden" name="rowcount" value="0">
<input type="hidden" name="orgId" value="<%= OrgDetails.getOrgId() %>" />
<br />
<script language="JavaScript">
<%= scriptDichiar %>
<%= scriptFunc %>



var curr=-1;
var currow=-1;


</script>
<%} else {%>
    <br/>
    <dhv:label name="">Selezionare un Tipo Check List prima di aggiungere una Check List.</dhv:label>
<%}%>