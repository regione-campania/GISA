<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<col width="33%">
  <tr class="containerBody">
    <th colspan="2">
      <strong><dhv:label name="audit.Audit">Informazioni Check List</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td style="border: 1px solid black;"  nowrap class="formLabel">
      <dhv:label name="audit.categoriaOsaNN">Ragione Sociale</dhv:label>
    </td>
    <td style="border: 1px solid black;" >
      <%= toHtml(OrgDetails.getName()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td style="border: 1px solid black;"  nowrap class="formLabel">
      <dhv:label name="audit.categoriaOsaNN">ASL</dhv:label>
    </td>
    <td style="border: 1px solid black;" >
      <%= SiteList.getSelectedValue(TicketDetails.getSiteId()) %>
    </td>
  </tr>
  <tr class="containerBody">
    <td style="border: 1px solid black;"  nowrap class="formLabel">
      <dhv:label name="audit.categoriaOsaNN">Categoria</dhv:label>
    </td>
    <td style="border: 1px solid black;" >
      <%= toHtml(OrgCategoriaRischioList.getSelectedValue(Audit.getTipoChecklist())) %>
    </td>
  </tr>
    <!--  tr class="containerBody">
    <td style="border: 1px solid black;"  nowrap class="formLabel">
      <dhv:label name="audit.categoriaOsaNN">Numero registrazione</dhv:label>
    </td>
    <td style="border: 1px solid black;" >
      <%= Audit.getNumeroRegistrazione() %>
    </td>
  </tr-->
    <!--  tr class="containerBody">
    <td style="border: 1px solid black;"  nowrap class="formLabel">
      <dhv:label name="audit.categoriaOsaNN">Componenti gruppo</dhv:label>
    </td>
    <td style="border: 1px solid black;" >
      <%=toHtml(Audit.getComponentiGruppo()) %>
    </td>
  </tr-->
    <tr class="containerBody">
    <td style="border: 1px solid black;"  nowrap class="formLabel">
      <dhv:label name="audit.categoriaOsaNN">Punteggio</dhv:label>
    </td>
    <td style="border: 1px solid black;" >
      <%= (Audit.getLivelloRischioFinale() == -1)	? (Audit.getLivelloRischio() + "") : (Audit.getLivelloRischioFinale() ) + "" %>
    </td>
  </tr>
  <input type="hidden" id="TipoC" name="TipoC" value="<%=request.getAttribute("TipoC") %>"/>
  </table>
  <br/>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <col width="33%">
  <tr class="containerBody">
    <th colspan="2">
      <strong><dhv:label name="audit.Audit">Dettagli addizionali</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBody">
    <td style="border: 1px solid black;"  nowrap class="formLabel">
      <dhv:label name="audit.categoriaOsaNN">Note</dhv:label>
    </td>
    <td style="border: 1px solid black;" >
      <%= toHtml(Audit.getNote()) %>
    </td>
  </tr>
  </table><br/>

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<col width="5%"><col width="35%"><col width="35%"><col width="10%"><col width="5%"><col width="5%"><col width="5%">

<%
String level = null;
int domandaElementId = 0;
String scriptDichiar = "idTypeList = new Array(); operazioneList = new Array(); notaList = new Array(); valoreRangeList = new Array();"; 
scriptDichiar += " idList = new Array(); parentIdList = new Array(); rispostaList = new Array(); puntiList = new Array(); requiredList = new Array();";
String scriptFunc = "";
int indexChecklistList = 0;
Iterator itrTypeList = typeList.iterator();
Iterator auditCkList=auditChecklist.iterator();
Iterator auditCkListType=auditChecklistType.iterator();
Boolean abilitato;
String numero="";
String nota = "";
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
  <td style="border: 1px solid black;" ><%=numero %>
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
    <td style="border: 1px solid black;"  width="50%" colspan="4" align="center">  QUESTO CAPITOLO DEVE ESSERE COMPILATO ?</td>
    <td style="border: 1px solid black;"  align="center">
    <% if(abilitato == true) {%> X <% }%>
   </td> 
	    <td style="border: 1px solid black;"  align="center">
    <% if(abilitato == false) {%> X <% }%>
   </td>
    <td style="border: 1px solid black;" >&nbsp;</td>
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
    level = null;
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
	    <td style="border: 1px solid black;"  valign="center"> <%= level %></td>
 <%if (parentId == null || parentId.equals("-1")) {%>
        <td style="border: 1px solid black;"  valign="center" ><%= toHtml(domanda) %></td>
        <td style="border: 1px solid black;" >&nbsp;</td>
    
  <%} else {%>
  		
        <td style="border: 1px solid black;" >&nbsp;</td><td style="border: 1px solid black;"  valign="center"><%= toHtml(domanda) %></td>
  <%}%>
        <td style="border: 1px solid black;"  valign="center"><%= toHtml(descrizione) %></td>
        
	       	        
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
     
    	 <td style="border: 1px solid black;"  align="center">
   
    <% if (( ! stato.equalsIgnoreCase("non risposta")) && (risposto && risposta)) {%> X <%} %>  &nbsp;
    </td>
    	 
    	 <td style="border: 1px solid black;"  align="center">
    	  <% if ((( ! stato.equalsIgnoreCase("non risposta")) && (risposto && !risposta))|| stato.equalsIgnoreCase("non risposta")) {%> X <%} %>  &nbsp;
   
    	 </td>
    	 
          <td style="border: 1px solid black;"  align="center"> <%=punti%> </td>  
	  
  	 </tr>
  	 
  	
 <%}
    
     else
     {
     	if(abilitato == false)
     	{
     		
     		%>
     		
     		  <tr class="row<%= rowid %>">
 	    <td style="border: 1px solid black;"  valign="center"><%= level %></td>
   <%if (parentId == null || parentId.equals("-1")) {%>
         <td style="border: 1px solid black;"  valign="center"><%= toHtml(domanda) %></td><td style="border: 1px solid black;" >&nbsp;</td>
   <%} else {%>
         <td style="border: 1px solid black;" >&nbsp;</td><td style="border: 1px solid black;"  valign="center"><%= toHtml(domanda) %></td>
   <%}%>
         <td style="border: 1px solid black;"  valign="center"><%= toHtml(descrizione) %></td>
         
 	       	        
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
      
     	 <td style="border: 1px solid black;"  align="center">
     	 	<input	type="radio" disabled
     	 			id="risposta<%= code%>1"
     	 			name="risposta<%= code%>"
     	 			value="1"
     	 			 />
     	 </td>
     	 
     	 <td style="border: 1px solid black;"  align="center">
     	 	<input	type="radio" disabled
     	 			id="risposta<%= code%>2"
     	 			name="risposta<%= code%>"
     	 			value="0"
     	 			/>
     	 </td>
     	 
          <td style="border: 1px solid black;"  align="center"><%if(puntiNo.equals(puntiSi) && puntiNo.equals("0")){ %>&nbsp; <input style="width: 30px; background-color: #cccccc"  type="hidden" id="punti<%= code%>" name="punti<%= code%>" /><%}else{ %><input style="width: 30px; background-color: #cccccc" readonly type="text" id="punti<%= code%>" name="punti<%= code%>" /> <%} %></td>  
	   
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
		numero="0";
		nota = "";
	}
}
else
{
	numero="0";
	nota="";
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
  <td style="border: 1px solid black;"  colspan="1">&nbsp;</td>
  <td style="border: 1px solid black;"  colspan="3">Punteggio storico delle non conformità (N.B. tale punteggio corrisponde alla somma dei punteggi delle non conformità rilevate durante i controlli ufficiali degli ultimi 5 anni dalla data di oggi, ovviamente con l'esclusione dei punteggi delle check list compilate nell'ambito della sorveglianza).</br></td>
  <td style="border: 1px solid black;"  colspan="2">
    <%--<input type="button" id="btnAggiungiPunti<%= indice%>" value="      +      " onclick="javascript:aggiornaTot(<%= indice%>, '+',punteggioUltimiAnni.value);"/>--%>
  </td>
  <td style="border: 1px solid black;" >
    <input type="text" style="width: 80px; background-color: #cccccc" readonly id="punteggioUltimiAnni" name="punteggioUltimiAnni" value="<%= Audit.getPunteggioUltimiAnni() %>"/>
    </td>
</tr>
<%if (Audit.isAggiornaCategoria()==true) { %>

<tr class="row<%= rowid+1%>">
  <td style="border: 1px solid black;"  colspan="1">&nbsp;</td>
  <td style="border: 1px solid black;"  colspan="3">Punteggio Totale Check List </br></td>
  <td style="border: 1px solid black;"  colspan="2">
      </td>
  <td style="border: 1px solid black;" >
    <input type="text" style="width: 80px; background-color: #cccccc" readonly  value="<%= request.getAttribute("punteggioCheckList") %>"/>
    </td>
</tr>


<% }}} else {%>
<tr class="containerBody">
<td style="border: 1px solid black;"  colspan="7"><dhv:label name="">Nessuna domanda presente.</dhv:label></td>
</tr>

<%}%>

<%-- prova --%>


<%-- fine prova --%>

<%}} else {%>
<tr class="containerBody">
<td style="border: 1px solid black;"  colspan="7"><dhv:label name="">Nessuna Check List presente.</dhv:label></td>
</tr>
<%}

if ( Audit.isPrincipale()==true)
{
%>
<tr class="containerBody" style="background-color: #ffff66; font-weight: bolder;">
  <td style="border: 1px solid black;"  colspan="1">&nbsp;</td>
  <td style="border: 1px solid black;" colspan="3">Punteggio storico delle non conformità (N.B. tale punteggio corrisponde alla somma dei punteggi delle non conformità rilevate durante i controlli ufficiali degli ultimi 5 anni dalla data di oggi, ovviamente con l'esclusione dei punteggi delle check list compilate nell'ambito della sorveglianza).</br></td>
  <td  style="border: 1px solid black;"colspan="2">
    <%--<input type="button" id="btnAggiungiPunti<%= indice%>" value="      +      " onclick="javascript:aggiornaTot(<%= indice%>, '+',punteggioUltimiAnni.value);"/>--%>
  </td>
  <td>
    <%= Audit.getPunteggioUltimiAnni() %>
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
</tr>

<tr class="containerBody" style="background-color: #ffff66; font-weight: bolder;">
  <td style="border: 1px solid black;"  colspan="3">&nbsp;</td>
  <td style="border: 1px solid black;"  nowrap>Punteggio Totale di Questa Check List</td>
  <%--if(Audit.getLivelloRischioFinale()== -1){ --%>
  <td style="border: 1px solid black;"  colspan="3"><%= Audit.getLivelloRischio() %>
 <input type="hidden" id="livelloRischioFinale" name="livelloRischioFinale" value="<%= Audit.getLivelloRischio() %>"/>
  <%--}else{%>
	  <td style="border: 1px solid black;"  colspan="3"><input style="width: 80px; background-color: #cccccc" type="text" readonly id="livelloRischio" name="livelloRischio" value=<%= Audit.getLivelloRischioFinale() %>>
 	<input type="hidden" id="livelloRischioFinale" name="livelloRischioFinale" value= <%= Audit.getLivelloRischioFinale() %>/>
  <%} --%></td>
</tr>

<tr class="containerBody" style="background-color: #ffff66; font-weight: bolder;">
  <td style="border: 1px solid black;" colspan="3">&nbsp;</td>
  <td style="border: 1px solid black;" nowrap>Nuova Categoria Rischio</td>
 <td style="border: 1px solid black;" nowrap colspan="3">
  <%if(TicketDetails.isCategoriaisAggiornata()){ %>
 <%= (TicketDetails.getCategoriaRischio()) %>
  <%-- <input style="width: 80px; background-color: #cccccc" type="hidden" readonly id="livelloRischioFinale" name="livelloRischioFinale" value=<%= Audit.getLivelloRischioFinale() %>>--%>
<%}else{ %> 
<b style="color: red;">Non Aggiornata</b>
<%} %>
 </td>


</table>
<br />

<table width="100%">
<tr><td>DATA <br/>
____________________________ </td> <td> FIRMA</td></tr>
<tr><td></td> 
<td><%=(TicketDetails.getComponenteNucleo()!=null ) ? TicketDetails.getComponenteNucleo().toUpperCase() : ""%><br/>
<%=(TicketDetails.getComponenteNucleoDue()!=null ) ? TicketDetails.getComponenteNucleoDue().toUpperCase() : ""%><br/>
<%=(TicketDetails.getComponenteNucleoTre()!=null ) ? TicketDetails.getComponenteNucleoTre().toUpperCase() : ""%><br/>
<%=(TicketDetails.getComponenteNucleoQuattro()!=null ) ? TicketDetails.getComponenteNucleoQuattro().toUpperCase() : ""%><br/>
<%=(TicketDetails.getComponenteNucleoCinque()!=null ) ? TicketDetails.getComponenteNucleoCinque().toUpperCase() : ""%><br/>
<%=(TicketDetails.getComponenteNucleoSei()!=null ) ? TicketDetails.getComponenteNucleoSei().toUpperCase() : ""%><br/>
<%=(TicketDetails.getComponenteNucleoSette()!=null ) ? TicketDetails.getComponenteNucleoSette().toUpperCase() : ""%><br/>
<%=(TicketDetails.getComponenteNucleoOtto()!=null ) ? TicketDetails.getComponenteNucleoOtto().toUpperCase() : ""%><br/>
<%=(TicketDetails.getComponenteNucleoNove()!=null ) ? TicketDetails.getComponenteNucleoNove().toUpperCase() : ""%><br/>

</td>
</tr>
<tr></tr>
<tr></tr>
<tr><td></td> <td>Per L'impresa/ stabilimento : ______________________</td></tr>
</table>

<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/>
  

<script language="JavaScript">
<%= scriptDichiar %>
<%= scriptFunc %>


</script>

