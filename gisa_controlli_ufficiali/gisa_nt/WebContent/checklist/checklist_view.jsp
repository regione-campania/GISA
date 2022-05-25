<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr class="containerBody">
    <th colspan="2">
      <strong><dhv:label name="audit.Audit">Informazioni Check List</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="audit.categoriaOsaNN">Tipo Check List</dhv:label>
    </td>
    <td>
      <%= toHtml(OrgCategoriaRischioList.getSelectedValue(Audit.getTipoChecklist())) %>
    </td>
  </tr>
  <input type="hidden" id="TipoC" name="TipoC" value="<%=request.getAttribute("TipoC") %>"/>
  <!-- 
  	<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="audit.numeroRegistrazione">Progressivo</dhv:label>
    </td>
    <td>
      <label type="text" size="50" name="numeroRegistrazione" ><%= Audit.getNumeroRegistrazione()%></label>
    </td>
  </tr>
   -->
<dhv:evaluate if="<%= Audit.getIdControllo()!=null %>">
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="">Identificativo C.U.</dhv:label>
    </td>
   
     
      <td>
      		<%= toHtmlValue(Audit.getIdControllo()) %>
      		
      </td>
    
  </tr>
  </dhv:evaluate>
  <!--  
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="audit.componentiGruppo">Componenti Gruppo</dhv:label>
    </td>
    <td>
      <label type="text" size="50" name="componentiGruppo" /><%= toHtml(Audit.getComponentiGruppo())%>
    </td>
  </tr>
   -->
  <tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="audit.livelloRischio">Punteggio Check List</dhv:label>
    </td>
    <td>
     
       <label type="text" name="livelloRischio" /><%= Audit.getLivelloRischio() %>
     
    </td>
  </tr>
  <!-- 
  <tr class="containerBody" >
        <td nowrap class="formLabel">
          <dhv:label name="audit.data2">Data</dhv:label>
        </td>
        <td>
          <zeroio:tz timestamp="<%= Audit.getData1() %>" dateOnly="true" showTimeZone="false" default="&nbsp;"/>
          </td>
      </tr>
       -->
 
</table>
</br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<%
int domandaElementId = 0;
String scriptDichiar = "idTypeList = new Array(); operazioneList = new Array(); notaList = new Array(); valoreRangeList = new Array();"; 
scriptDichiar += " idList = new Array(); parentIdList = new Array(); rispostaList = new Array(); puntiList = new Array(); requiredList = new Array();";
String scriptFunc = "";
int indexChecklistList = 0;
Iterator itrTypeList = typeList.iterator();
Iterator auditCkList=auditChecklist.iterator();
Iterator auditCkListType=auditChecklistType.iterator();
Boolean abilitato;
String level = null;
String numero="";
String nota =  "";
if ( itrTypeList.hasNext() ) {
	Iterator auditCkListType1=auditChecklistType.iterator();
	
  while (itrTypeList.hasNext()) {
	abilitato = false;
	CustomLookupElement thisTypeList = (CustomLookupElement) itrTypeList.next();

	int checklistTypeId = Integer.valueOf(thisTypeList.getValue("code"));
    String checklistDescription = thisTypeList.getValue("description");

    AuditChecklistType a =null;
    if(auditCkListType1.hasNext())
    {
    	 a =	(AuditChecklistType)auditCkListType1.next();
    }
	
    if(a!=null)
    {
    	abilitato = a.isIs_abilitato();
    }
    String checklistRange = thisTypeList.getValue("range");
    CustomLookupList checklist = (CustomLookupList) checklistList.get(indexChecklistList);
    indexChecklistList++;
    scriptDichiar += "idTypeList[\""+ (indexChecklistList) +"\"] = " + checklistTypeId + ";";
    
%>

<%if (indexChecklistList>1) { %>
<tr class="containerBody" style="background-color: #ffff66; font-weight: bolder;">
  <td style="border: 1px solid black;"  colspan="1"><%=Integer.parseInt(level)+1 %></td>
  <td style="border: 1px solid black;"  colspan="3">Esistono delle condizioni particolari non contemplate sopra che possono diminuire o aumentare il punteggio di rischio? Se si, riportarle qui sotto aggiungendo o sottraendo un punteggio nel range +20, -20 da scrivere nella casella a lato.</br>
  <%
  	if(nota!=null && !nota.equals(""))
  	{
  		out.println("Nota: " + nota);	
  	}
  	%>
  </td>
      <td style="border: 1px solid black;"  align="center"> +
    <%--<input type="button" id="btnAggiungiPunti<%= indice%>" value="      +      " onclick="javascript:aggiornaTot(<%= indice%>, '+',punteggioUltimiAnni.value);"/>--%>
  </td>
       <td style="border: 1px solid black;"  align="center"> -
    <%--<input type="button" id="btnAggiungiPunti<%= indice%>" value="      +      " onclick="javascript:aggiornaTot(<%= indice%>, '+',punteggioUltimiAnni.value);"/>--%>
  </td>
  <td style="border: 1px solid black;" ><%=numero%>
    </td>
</tr>
<% } %>

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
			  
			  <tr class="row4">
    <td width="50%" colspan="4" align="center">  QUESTO CAPITOLO DEVE ESSERE COMPILATO ?</td>
    <td align="center">
    <input type="radio" disabled="disabled" <%if(abilitato == true){ %>checked="checked"<%} %> id ="risposta<%=checklistTypeId%>1" value ="1" name = "<%=checklistTypeId%>1" onclick="disabilitaCapitolo('<%=checklistTypeId %>','si')"/></td> 
	     <td align="center">
    	 	<input	type="radio" disabled="disabled" value = "2" id ="risposta<%=checklistTypeId%>2" <%if(abilitato == false){ %>checked="checked" <%} %>  name = "<%=checklistTypeId%>1" onclick="disabilitaCapitolo('<%=checklistTypeId %>','no')" />
    	 			
    	 </td>
    <td>&nbsp;</td>
  </tr>
			  
			  <%
			  
		  }
		  else
		  {
			  %>
			  <input type = "hidden" name = "disabilita<%=checklistTypeId %>1" value ="3">
			  <%
		  }
		
   %>
 
  <%
  Iterator itrChecklist = checklist.iterator();
  if ( itrChecklist.hasNext() ) {
	  
	String descrizione=null;
    int rowid = 0;
    int rowid2 = 5;
    int rowidcurr = 0 ;
    String last_gp = "" ;
    while (itrChecklist.hasNext()) {
      rowid = (rowid != 1?1:2);
      CustomLookupElement thisChecklist = (CustomLookupElement) itrChecklist.next();
      boolean enabled = thisChecklist.getValue("enabled") == "true" ? true : false;
      boolean defaultItem = thisChecklist.getValue("default_item") == "true" ? true : false;
      String id = thisChecklist.getValue("id");
      String parentId = thisChecklist.getValue("parent_id");
      String domanda = thisChecklist.getValue("domanda");
      descrizione = thisChecklist.getValue("descrizione");
      String puntiNo = thisChecklist.getValue("punti_no");
      String puntiSi = thisChecklist.getValue("punti_si");
      level = thisChecklist.getValue("level");
      int code = id.startsWith("--") ? -1 : Integer.parseInt(id);
      //Iterator auditCkList=auditChecklist.iterator();
      //Iterator auditCkListType=auditChecklistType.iterator();
      
     
      
    Boolean risposto	= false;
    Boolean risposta	= null;
    int punti			= 0;
   	String stato = "" ;
	AuditChecklist audiCkListTemp	= null;
	Iterator array					= auditChecklist.iterator();
	while( array.hasNext() && !risposto )
	{ 
		audiCkListTemp = (AuditChecklist)array.next();
		risposto = ( Integer.parseInt(id) == audiCkListTemp.getChecklistId() );
		
	}
	
	if( risposto )
	{
		
		risposta	= audiCkListTemp.getRisposta();
		code		= id.startsWith("--") ? -1 : Integer.parseInt(id);
		punti		= audiCkListTemp.getPunti();
		stato = audiCkListTemp.getStato();
	}
	
      scriptDichiar += "idList[\""+ (domandaElementId++) +"\"] = " + code + ";";
      //scriptDichiar += "notaList[\""+ (domandaElementId++) +"\"] = " + thisTypeList.getValue("range") + ";";
      //scriptDichiar += "aggiornaListElement("+code+","+parentId+"," + ((risposto) ? ( (risposta) ? (puntiSi) : (puntiNo) ) : ("0") ) + ",'" + ((risposto) ? ( (risposta) ? ("si") : ("no") ) : ("") ) + "');";
      scriptFunc += "function func"+code+"(risp){";
      scriptFunc += "  if(risp == 'si') {";
      scriptFunc += "    document.getElementById(\"punti"+code+"\").value = " + puntiSi + ";";
     // scriptFunc += "    aggiornaListElement("+code+","+parentId+","+puntiSi+",risp);";
      scriptFunc += "  }";
      scriptFunc += "  if(risp == 'no') {";
      scriptFunc += "    document.getElementById(\"punti"+code+"\").value = " + puntiNo + ";";
    //  scriptFunc += "    aggiornaListElement("+code+","+parentId+","+puntiNo+",risp);";
      scriptFunc += "  }";
      scriptFunc += "}";      
      
 
          if (parentId != null && !parentId.equals("-1") ) { 
            rowid = (rowid != 1?1:2);
           
          }
     
      
     
     
      auditCkList=auditChecklist.iterator();
      
     if(risposto==true || risposto==false){
        		  
        		  %>
        		   <tr class="row<%= rowidcurr %>" >
	    <td valign="center"><%= level %></td>
 <%if (parentId == null || parentId.equals("-1")) {%>
        <td valign="center" ><%= toHtml(domanda) %></td>
        <td>&nbsp;</td>
    
  <%} else {%>
  		
        <td>&nbsp;</td><td valign="center"><%= toHtml(domanda) %></td>
  <%}%>
        <td valign="center"><%= toHtml(descrizione) %></td>
        
	       	        
        	  <%//}
        	  
        	String siChecked="";
         	String noChecked="";
         	
          scriptFunc += "function func2"+code+"(risp){";
  	      scriptFunc += "  if(risp == 'si') {";
  	      scriptFunc += "    document.getElementById(\"punti"+code+"\").value = " + puntiSi + ";";
  	      //scriptFunc += "    aggiornaListElement("+code+","+parentId+","+puntiSi+",risp);";
  	      scriptFunc += "  }";
  	      scriptFunc += "  if(risp == 'no') {";
  	      scriptFunc += "    document.getElementById(\"punti"+code+"\").value = " + puntiNo + ";";
  	    //  scriptFunc += "    aggiornaListElement("+code+","+parentId+","+puntiNo+",risp);";
  	      scriptFunc += "  }";
  	      scriptFunc += "}"; 
			
     %>
     
    	 <td align="center">
    	 	<input	type="radio" disabled
    	 			id="risposta<%= code%>1"
    	 			name="risposta<%= code%>"
    	 			value="1"
    	 			<%=( ! stato.equalsIgnoreCase("non risposta"))?(risposto && risposta) ? ("checked=\"checked\"") : (""):("") %> />
    	 </td>
    	 
    	 <td align="center">
    	 	<input	type="radio" disabled
    	 			id="risposta<%= code%>2"
    	 			name="risposta<%= code%>"
    	 			value="0"
    	 			<%=(! stato.equalsIgnoreCase("non risposta"))?(risposto && !risposta) ? ("checked=\"checked\"") : (""):("") %>/>
    	 </td>
    	 
          <td align="center"><%if(puntiNo.equals(puntiSi) && puntiNo.equals("0")){ %>&nbsp; <input style="width: 30px; background-color: #cccccc"  type="hidden" id="punti<%= code%>" name="punti<%= code%>" /><%}else{ %><input style="width: 30px; background-color: #cccccc" readonly type="text" id="punti<%= code%>" name="punti<%= code%>" value = "<%=punti%>"/> <%} %></td>  
	  
  	 </tr>
 <%}
    
     else
     {
     	if(abilitato == false)
     	{
     		
     		%>
     		
     		  <tr class="row<%= rowid %>">
 	    <td valign="center"><%= level %></td>
   <%if (parentId == null || parentId.equals("-1")) {%>
         <td valign="center"><%= toHtml(domanda) %></td><td>&nbsp;</td>
   <%} else {%>
         <td>&nbsp;</td><td valign="center"><%= toHtml(domanda) %></td>
   <%}%>
         <td valign="center"><%= toHtml(descrizione) %></td>
         
 	       	        
         	  <%//}
         	  
         	String siChecked="";
          	String noChecked="";
          	
           scriptFunc += "function func2"+code+"(risp){";
   	      scriptFunc += "  if(risp == 'si') {";
   	      scriptFunc += "    document.getElementById(\"punti"+code+"\").value = " + puntiSi + ";";
   	      //scriptFunc += "    aggiornaListElement("+code+","+parentId+","+puntiSi+",risp);";
   	      scriptFunc += "  }";
   	      scriptFunc += "  if(risp == 'no') {";
   	      scriptFunc += "    document.getElementById(\"punti"+code+"\").value = " + puntiNo + ";";
   	    //  scriptFunc += "    aggiornaListElement("+code+","+parentId+","+puntiNo+",risp);";
   	      scriptFunc += "  }";
   	      scriptFunc += "}"; 
 			
      %>
      
     	 <td align="center">
     	 	<input	type="radio" disabled
     	 			id="risposta<%= code%>1"
     	 			name="risposta<%= code%>"
     	 			value="1"
     	 			 />
     	 </td>
     	 
     	 <td align="center">
     	 	<input	type="radio" disabled
     	 			id="risposta<%= code%>2"
     	 			name="risposta<%= code%>"
     	 			value="0"
     	 			/>
     	 </td>
     	 
          <td align="center"><%if(puntiNo.equals(puntiSi) && puntiNo.equals("0")){ %>&nbsp; <input style="width: 30px; background-color: #cccccc"  type="hidden" id="punti<%= code%>" name="punti<%= code%>" /><%}else{ %><input style="width: 30px; background-color: #cccccc" readonly type="text" id="punti<%= code%>" name="punti<%= code%>" /> <%} %></td>  
	   
   	 </tr>
     		
     		
     		<%
     		
     		
     	}
     	
     	
     }
          		
  %>    
<%}
%> 
<%
String add="";
String sub="";
String operazione=null;

if(auditCkListType.hasNext())
{
	
	AuditChecklistType act=(AuditChecklistType)auditCkListType.next();
	
	operazione=act.getOperazione();
		
	if(operazione!=null && (operazione.equals("+")||operazione.equals("-")))
	{	
		nota=act.getNota();
		
		numero=Integer.toString(act.getValoreRange());
		String oper = act.getOperazione(); 
		String nt = act.getNota();
		
	}
	else
	{
		numero = "0";
		nota = "";
	}
}
else
{
		numero = "0";
		nota = "";
}

if(operazione!=null)
{
	String color2= null;
	String color = null;
	if(operazione!=null && operazione.equals("+"))
		add="checked";
	    
	if(operazione!=null && operazione.equals("-"))
		sub="checked";
	    
	
} %>



<% int indice = 1 + indexChecklistList;
	  if(((checklistDescription.equals("CAPITOLO IX: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)"))||(checklistDescription.equals("CAPITOLO X: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)"))||(checklistDescription.equals("CAPITOLO VIII: DATI STORICI (DA ESTRAPOLARE PREVENTIVAMENTE DALLA SCHEDA DELL'IMPRESA PRESENTE SUL SITO DELL'O.R.S.A.)")))){%>
	
<tr class="row<%= rowid+1%>">
  <td colspan="1">&nbsp;</td>
  <td colspan="3">Punteggio storico delle non conformità (N.B. tale punteggio corrisponde alla somma dei punteggi delle non conformità rilevate durante i controlli ufficiali degli ultimi 5 anni dalla data di oggi, ovviamente con l'esclusione dei punteggi delle check list compilate nell'ambito della sorveglianza).</br></td>
  <td colspan="2">
    <%--<input type="button" id="btnAggiungiPunti<%= indice%>" value="      +      " onclick="javascript:aggiornaTot(<%= indice%>, '+',punteggioUltimiAnni.value);"/>--%>
  </td>
  <td>
    <input type="text" style="width: 80px; background-color: #cccccc" readonly id="punteggioUltimiAnni" name="punteggioUltimiAnni" value="<%= Audit.getPunteggioUltimiAnni() %>"/>
    </td>
</tr>
<%if (Audit.isAggiornaCategoria()==true) { %>

<tr class="row<%= rowid+1%>">
  <td colspan="1">&nbsp;</td>
  <td colspan="3">Punteggio Totale Check List </br></td>
  <td colspan="2">
      </td>
  <td>
    <input type="text" style="width: 80px; background-color: #cccccc" readonly  value="<%= request.getAttribute("punteggioCheckList") %>"/>
    </td>
</tr>


<% }}} else {%>
<tr class="containerBody">
<td colspan="7"><dhv:label name="">Nessuna domanda presente.</dhv:label></td>
</tr>

<%}%>

<%-- prova --%>


<%-- fine prova --%>

<%
}
}
else {%>
<tr class="containerBody">
<td colspan="7"><dhv:label name="">Nessuna Check List presente.</dhv:label></td>
</tr>
<%}
if ( Audit.isPrincipale()==true)
{
%>
<tr class="containerBody" style="background-color: #ffff66; font-weight: bolder;">
  <td colspan="1">&nbsp;</td>
  <td colspan="3">Punteggio storico delle non conformità (N.B. tale punteggio corrisponde alla somma dei punteggi delle non conformità rilevate durante i controlli ufficiali degli ultimi 5 anni dalla data di oggi, ovviamente con l'esclusione dei punteggi delle check list compilate nell'ambito della sorveglianza).</br></td>
  <td colspan="2">
    <%--<input type="button" id="btnAggiungiPunti<%= indice%>" value="      +      " onclick="javascript:aggiornaTot(<%= indice%>, '+',punteggioUltimiAnni.value);"/>--%>
  </td>
  <td>
    <input type="text" style="width: 80px; background-color: #cccccc" readonly id="punteggioUltimiAnni" name="punteggioUltimiAnni" value="<%= Audit.getPunteggioUltimiAnni() %>"/>
    </td>
</tr>
<%}
else
{
%>
<tr>
	  <td colspan="1">&nbsp;</td>
	  <td colspan="3">Punteggio delle Check List già Compilate nello Stesso Controllo</br></td>
	  <td colspan="2">
	      </td>
	  <td>
	    <input type="text" style="width: 80px; background-color: #cccccc" readonly <%if (request.getAttribute("punteggioCheckList")!=null){ %> value="<%= request.getAttribute("punteggioCheckList") %><%} %>"/>
	    </td>
	</tr>
<%	
}
%>

<tr class="containerBody" style="background-color: #ffff66; font-weight: bolder;">
  <td style="border: 1px solid black;"  colspan="1"><%=Integer.parseInt(level)+1 %></td>
  <td style="border: 1px solid black;"  colspan="3">Esistono delle condizioni particolari non contemplate sopra che possono diminuire o aumentare il punteggio di rischio? Se si, riportarle qui sotto aggiungendo o sottraendo un punteggio nel range +20, -20 da scrivere nella casella a lato.</br>
  <%
  	if(nota!=null && !nota.equals(""))
  	{
  		out.println("Nota: " + nota);	
  	}
  	%>
  </td>
     <td style="border: 1px solid black;"  align="center"> +
    <%--<input type="button" id="btnAggiungiPunti<%= indice%>" value="      +      " onclick="javascript:aggiornaTot(<%= indice%>, '+',punteggioUltimiAnni.value);"/>--%>
  </td>
       <td style="border: 1px solid black;"  align="center"> -
    <%--<input type="button" id="btnAggiungiPunti<%= indice%>" value="      +      " onclick="javascript:aggiornaTot(<%= indice%>, '+',punteggioUltimiAnni.value);"/>--%>
  </td>
  <td style="border: 1px solid black;" ><%=numero%>
    </td>
</tr

<tr class="containerBody" style="background-color: #ffff66; font-weight: bolder;">
  <td colspan="3">&nbsp;</td>
  <td nowrap>Punteggio Totale di Questa Check List</td>
  <%--if(Audit.getLivelloRischioFinale()== -1){ --%>
  <td colspan="3"><input style="width: 80px; background-color: #cccccc" type="text" readonly id="livelloRischio" name="livelloRischio" value=<%= Audit.getLivelloRischio() %>>
 <input type="hidden" id="livelloRischioFinale" name="livelloRischioFinale" value="<%= Audit.getLivelloRischio() %>"/>
  <%--}else{%>
	  <td colspan="3"><input style="width: 80px; background-color: #cccccc" type="text" readonly id="livelloRischio" name="livelloRischio" value=<%= Audit.getLivelloRischioFinale() %>>
 	<input type="hidden" id="livelloRischioFinale" name="livelloRischioFinale" value= <%= Audit.getLivelloRischioFinale() %>/>
  <%} --%></td>
</tr>




<tr class="containerBody" style="background-color: #ffff66; font-weight: bolder;">
  <td colspan="3">&nbsp;</td>
  <td nowrap>Nuova Categoria Rischio</td>
  <td colspan="3">
  <%if(TicketDetails.isCategoriaisAggiornata()){ %>
  <input style="width: 80px; background-color: #cccccc" type="text" readonly id="categoriaRischio" name="categoriaRischio" value="<%= (OrgDetails.getCategoriaRischio()) %>">
  <%-- <input style="width: 80px; background-color: #cccccc" type="hidden" readonly id="livelloRischioFinale" name="livelloRischioFinale" value=<%= Audit.getLivelloRischioFinale() %>>--%>
<%}else{ %> 
<b style="color: red;">Non Aggiornata</b>
<%} %>
 </td>
</tr>

</table>
<br />
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
      <label name="note" ROWS="3" COLS="50"><%= Audit.getNote()%></label>
    </td>
  </tr>
</table>
<br/>
  <%= addHiddenParams(request, "popup|popupType|actionId") %>
  <%-- end details --%>
  <input type="hidden" name="id" value="<%= Audit.getId() %>" />
  <br />
  

<script language="JavaScript">
<%= scriptDichiar %>
<%= scriptFunc %>


</script>

